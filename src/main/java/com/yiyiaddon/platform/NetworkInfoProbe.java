package com.yiyiaddon.platform;

import com.google.gson.JsonObject;
import com.yiyiaddon.core.HttpApi;
import com.yiyiaddon.core.Json;
import com.yiyiaddon.model.ClientNetworkInfo;

import java.time.Duration;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 连接出口归属探测：并行向三个公开接口查询本机公网 IP、国家与运营商，并据此判定是否疑似代理。
 *
 * <p>后端只能看到连接来源 IP，无法穿透代理看到真实位置，因此这里把三路结果与本地规则
 * （机房关键字、可疑 ASN、多源 IP 不一致）一并作为补充证据上报。</p>
 *
 * <p>结果缓存 10 分钟：注册与心跳共用同一份数据，避免高频轮询第三方接口被限流。</p>
 */
public final class NetworkInfoProbe {

    private static final Pattern ASN_DIGITS = Pattern.compile("(\\d{1,10})");
    private static final Pattern TRACE_IP = Pattern.compile("ip=([^\\s]+)");
    private static final Pattern TRACE_LOC = Pattern.compile("loc=([A-Z]{2})");

    private static final long CACHE_MILLIS = 10 * 60 * 1000L;
    private static final Duration SOURCE_TIMEOUT = Duration.ofSeconds(4);
    /**
     * 合并等待上限，必须<b>大于</b>单源超时：三路是并行的，但实测出口在境外时单源常在 2～3 秒才回
     * （本机实测 ip-api.com 2985ms、1.1.1.1 2115ms）。原来这里只等 2500ms，比单源超时还短，
     * 结果「已经拿到 200 的响应」也会被 {@code getNow(null)} 当成 null 丢掉 ——
     * 首页就表现为网络地区 / IP 空白、网络状态「异常」，而且每次结果时有时无。
     */
    private static final Duration TOTAL_TIMEOUT = Duration.ofMillis(5500);

    private static final List<String> VPN_ORG_KEYWORDS = List.of(
            "cloudflare", "amazon", "aws", "google", "microsoft", "azure",
            "digitalocean", "ovh", "hetzner", "linode", "choopa", "vultr",
            "m247", "nord", "mullvad", "proton", "expressvpn", "surfshark",
            "cyberghost", "ipvanish", "datacamp", "cdn77", "leaseweb", "contabo",
            "ionos", "oracle", "alibaba", "aliyun", "tencent", "huawei",
            "cogent", "quadranet", "hostwinds", "buyvm", "zenlayer", "ipxo",
            "packet", "equinix", "psychz", "hostinger", "namecheap", "colocrossing",
            "hivelocity", "datacenter", "hosting", "vpn", "proxy",
            "fdcservers", "vps", "vds", "colocation", "wholesale",
            "seedbox", "netcup", "worldstream", "serverius", "spartanhost", "egihosting",
            "racknerd", "virmach", "reliablesite", "intergrid", "chocotel",
            "privateinternetaccess", "24shells", "solarvps", "leapswitch", "phanes",
            "netprotect", "dedicated", "baremetal");

    private static final List<Integer> VPN_ASN = List.of(
            13335, 15169, 16509, 14618, 8075, 14061, 16276, 24940, 20473, 9009,
            63949, 36352, 8100, 40676, 29802, 16265, 51167, 46562, 206092, 62240,
            30058, 212238, 40021, 141995, 49505, 63473, 394256, 54994, 44066);

    private static volatile ClientNetworkInfo cached;
    private static volatile long cachedAt;

    private NetworkInfoProbe() {
    }

    /** 取缓存的归属信息；超过 10 分钟或从未查询过时重新拉取。 */
    public static ClientNetworkInfo resolve() {
        ClientNetworkInfo snapshot = cached;
        if (snapshot != null && System.currentTimeMillis() - cachedAt < CACHE_MILLIS) return snapshot;
        return resolve(true);
    }

    public static ClientNetworkInfo resolve(boolean refresh) {
        if (!refresh) return cached == null ? ClientNetworkInfo.unknown() : cached;
        ClientNetworkInfo snapshot = query();
        // 这一轮一个源都没答上（接口被墙 / 限流 / 超时）时不覆盖上一次成功的结果：
        // 否则首页的地区与 IP 会空着整整一个缓存周期（10 分钟），而网络其实是通的。
        if (snapshot.ip() != null || cached == null) cached = snapshot;
        cachedAt = System.currentTimeMillis();
        return cached;
    }

    public static void invalidate() {
        cached = null;
        cachedAt = 0L;
    }

    /** 三路并行查询并合并结果。 */
    private static ClientNetworkInfo query() {
        CompletableFuture<Probe> ipApiCo = CompletableFuture.supplyAsync(NetworkInfoProbe::fromIpApiCo);
        CompletableFuture<Probe> ipApiCom = CompletableFuture.supplyAsync(NetworkInfoProbe::fromIpApiCom);
        CompletableFuture<Probe> cloudflare = CompletableFuture.supplyAsync(NetworkInfoProbe::fromCloudflare);
        try {
            CompletableFuture.allOf(ipApiCo, ipApiCom, cloudflare).get(TOTAL_TIMEOUT.toMillis(), TimeUnit.MILLISECONDS);
        } catch (Exception ignored) {
            // 任一源超时都继续用已完成的部分，缺失字段留空。
        }
        return merge(orNull(ipApiCo), orNull(ipApiCom), orNull(cloudflare));
    }

    private static Probe orNull(CompletableFuture<Probe> future) {
        try {
            return future.getNow(null);
        } catch (Exception e) {
            return null;
        }
    }

    private static ClientNetworkInfo merge(Probe primary, Probe secondary, Probe fallback) {
        Probe chosen = firstPresent(primary, secondary, fallback);
        if (chosen == null) return ClientNetworkInfo.unknown();

        boolean proxy = false;
        String proxyType = null;
        for (Probe probe : new Probe[]{primary, secondary, fallback}) {
            if (probe == null) continue;
            if (probe.proxy() && !proxy) {
                proxy = true;
                proxyType = probe.proxyType();
            }
        }

        // 两个来源给出的公网 IP 不一致，说明链路中存在转发节点，直接判定为代理。
        String probeIpA = primary == null ? null : primary.ip();
        String probeIpB = secondary == null ? null : secondary.ip();
        if (probeIpA != null && probeIpB != null && !probeIpA.equalsIgnoreCase(probeIpB)) {
            proxy = true;
            proxyType = "VPN";
        }

        if (looksLikeVpn(chosen.asOrg(), chosen.asn())) {
            proxy = true;
            if (proxyType == null) proxyType = "VPN";
        }

        return new ClientNetworkInfo(chosen.ip(), chosen.country(), chosen.isp(), chosen.asOrg(),
                chosen.asn(), proxy, proxyType);
    }

    private static Probe firstPresent(Probe... probes) {
        for (Probe probe : probes) {
            if (probe != null && probe.ip() != null && !probe.ip().isBlank()) return probe;
        }
        for (Probe probe : probes) {
            if (probe != null) return probe;
        }
        return null;
    }

    private static Probe fromIpApiCo() {
        try {
            HttpApi.Response response = HttpApi.getAbsolute("https://ipapi.co/json/", SOURCE_TIMEOUT);
            JsonObject json = response.json();
            if (json == null) return null;
            String ip = Json.string(json, "ip", null);
            int asn = asnNumber(Json.string(json, "asn", null));
            boolean proxy = Json.bool(json, "is_tor", false) || Json.bool(json, "tor", false);
            String type = Json.bool(json, "is_tor", false) || Json.bool(json, "tor", false) ? "TOR"
                    : Json.bool(json, "is_vpn", false) || Json.bool(json, "vpn", false) ? "VPN"
                    : Json.bool(json, "is_proxy", false) || Json.bool(json, "proxy", false) ? "PROXY" : null;
            return new Probe(ip, Json.string(json, "country_code", null), Json.string(json, "org", null),
                    Json.string(json, "org", null), asn, proxy || type != null, type);
        } catch (Exception e) {
            return null;
        }
    }

    private static Probe fromIpApiCom() {
        try {
            HttpApi.Response response = HttpApi.getAbsolute(
                    "http://ip-api.com/json/?fields=query,countryCode,proxy,mobile,hosting,isp,org,as,asname,timezone",
                    SOURCE_TIMEOUT);
            JsonObject json = response.json();
            if (json == null) return null;
            String isp = Json.string(json, "isp", null);
            String org = Json.string(json, "org", null);
            String asName = Json.string(json, "asname", null);
            String asOrg = org != null ? org : asName != null ? asName : isp;

            boolean proxied = Json.bool(json, "proxy", false);
            boolean hosting = Json.bool(json, "hosting", false);
            boolean mobile = Json.bool(json, "mobile", false);
            String type = proxied ? "PROXY" : hosting ? "VPN" : mobile ? "MOBILE" : null;
            return new Probe(Json.string(json, "query", null), Json.string(json, "countryCode", null),
                    isp, asOrg, asnNumber(Json.string(json, "as", null)), type != null, type);
        } catch (Exception e) {
            return null;
        }
    }

    private static Probe fromCloudflare() {
        try {
            HttpApi.Response response = HttpApi.getAbsolute("https://1.1.1.1/cdn-cgi/trace", SOURCE_TIMEOUT);
            String body = response.body();
            if (body == null || body.isBlank()) return null;
            Matcher ip = TRACE_IP.matcher(body);
            Matcher loc = TRACE_LOC.matcher(body);
            String address = ip.find() ? ip.group(1) : null;
            String country = loc.find() ? loc.group(1) : null;
            if (address == null && country == null) return null;
            return new Probe(address, country, null, null, -1, false, null);
        } catch (Exception e) {
            return null;
        }
    }

    /** 从 "AS9009" 这类文本中提取数字 ASN；无法解析返回 -1。 */
    private static int asnNumber(String raw) {
        if (raw == null) return -1;
        Matcher matcher = ASN_DIGITS.matcher(raw);
        if (!matcher.find()) return -1;
        try {
            return Integer.parseInt(matcher.group(1));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    /** 命中已知机房/VPN 组织的关键字或 ASN 即视为疑似代理。 */
    private static boolean looksLikeVpn(String asOrg, int asn) {
        if (asn > 0 && VPN_ASN.contains(asn)) return true;
        if (asOrg == null || asOrg.isBlank()) return false;
        String lower = asOrg.toLowerCase(Locale.ROOT);
        for (String keyword : VPN_ORG_KEYWORDS) {
            if (lower.contains(keyword)) return true;
        }
        return false;
    }

    private record Probe(String ip, String country, String isp, String asOrg, int asn, boolean proxy,
                         String proxyType) {
    }
}
