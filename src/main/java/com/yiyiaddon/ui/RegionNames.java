package com.yiyiaddon.ui;

import java.util.Locale;
import java.util.Map;

/**
 * 网络归属的中文显示名：国家码 → 国名，一级行政区英文名 → 中文省/州名。
 *
 * <p>探测源（ip-api.com / ipapi.co）只给英文名（{@code Oregon}、{@code Guangdong}），界面按中文呈现。
 * 未收录的国家码与行政区名原样返回，不臆造译名；港澳台与国名同名时由调用方去重。</p>
 */
public final class RegionNames {

    private RegionNames() {
    }

    /** 常见出口地区码 → 中文名；未收录的码原样展示。 */
    private static final Map<String, String> COUNTRIES = Map.ofEntries(
            Map.entry("CN", "中国"), Map.entry("HK", "香港"), Map.entry("TW", "台湾"),
            Map.entry("MO", "澳门"), Map.entry("JP", "日本"), Map.entry("KR", "韩国"),
            Map.entry("SG", "新加坡"), Map.entry("MY", "马来西亚"), Map.entry("TH", "泰国"),
            Map.entry("VN", "越南"), Map.entry("PH", "菲律宾"), Map.entry("ID", "印度尼西亚"),
            Map.entry("IN", "印度"), Map.entry("US", "美国"), Map.entry("CA", "加拿大"),
            Map.entry("MX", "墨西哥"), Map.entry("BR", "巴西"), Map.entry("AR", "阿根廷"),
            Map.entry("GB", "英国"), Map.entry("DE", "德国"), Map.entry("FR", "法国"),
            Map.entry("NL", "荷兰"), Map.entry("BE", "比利时"), Map.entry("CH", "瑞士"),
            Map.entry("AT", "奥地利"), Map.entry("SE", "瑞典"), Map.entry("NO", "挪威"),
            Map.entry("FI", "芬兰"), Map.entry("DK", "丹麦"), Map.entry("PL", "波兰"),
            Map.entry("ES", "西班牙"), Map.entry("IT", "意大利"), Map.entry("PT", "葡萄牙"),
            Map.entry("IE", "爱尔兰"), Map.entry("CZ", "捷克"), Map.entry("RO", "罗马尼亚"),
            Map.entry("UA", "乌克兰"), Map.entry("TR", "土耳其"), Map.entry("RU", "俄罗斯"),
            Map.entry("AE", "阿联酋"), Map.entry("SA", "沙特阿拉伯"), Map.entry("IL", "以色列"),
            Map.entry("AU", "澳大利亚"), Map.entry("NZ", "新西兰"), Map.entry("ZA", "南非"));

    /** 一级行政区英文名（小写）→ 中文名：中国 34 个省级 + 美国 50 州与特区 + 港澳。 */
    private static final Map<String, String> REGIONS = Map.ofEntries(
            // —— 中国 ——
            Map.entry("beijing", "北京市"), Map.entry("tianjin", "天津市"),
            Map.entry("hebei", "河北省"), Map.entry("shanxi", "山西省"),
            Map.entry("liaoning", "辽宁省"), Map.entry("jilin", "吉林省"),
            Map.entry("heilongjiang", "黑龙江省"), Map.entry("shanghai", "上海市"),
            Map.entry("jiangsu", "江苏省"), Map.entry("zhejiang", "浙江省"),
            Map.entry("anhui", "安徽省"), Map.entry("fujian", "福建省"),
            Map.entry("jiangxi", "江西省"), Map.entry("shandong", "山东省"),
            Map.entry("henan", "河南省"), Map.entry("hubei", "湖北省"),
            Map.entry("hunan", "湖南省"), Map.entry("guangdong", "广东省"),
            Map.entry("hainan", "海南省"), Map.entry("chongqing", "重庆市"),
            Map.entry("sichuan", "四川省"), Map.entry("guizhou", "贵州省"),
            Map.entry("yunnan", "云南省"), Map.entry("shaanxi", "陕西省"),
            Map.entry("gansu", "甘肃省"), Map.entry("qinghai", "青海省"),
            Map.entry("taiwan", "台湾省"), Map.entry("hong kong", "香港"),
            Map.entry("macau", "澳门"), Map.entry("macao", "澳门"),
            // —— 美国 ——
            Map.entry("alabama", "阿拉巴马州"), Map.entry("alaska", "阿拉斯加州"),
            Map.entry("arizona", "亚利桑那州"), Map.entry("arkansas", "阿肯色州"),
            Map.entry("california", "加利福尼亚州"), Map.entry("colorado", "科罗拉多州"),
            Map.entry("connecticut", "康涅狄格州"), Map.entry("delaware", "特拉华州"),
            Map.entry("florida", "佛罗里达州"), Map.entry("georgia", "佐治亚州"),
            Map.entry("hawaii", "夏威夷州"), Map.entry("idaho", "爱达荷州"),
            Map.entry("illinois", "伊利诺伊州"), Map.entry("indiana", "印第安纳州"),
            Map.entry("iowa", "艾奥瓦州"), Map.entry("kansas", "堪萨斯州"),
            Map.entry("kentucky", "肯塔基州"), Map.entry("louisiana", "路易斯安那州"),
            Map.entry("maine", "缅因州"), Map.entry("maryland", "马里兰州"),
            Map.entry("massachusetts", "马萨诸塞州"), Map.entry("michigan", "密歇根州"),
            Map.entry("minnesota", "明尼苏达州"), Map.entry("mississippi", "密西西比州"),
            Map.entry("missouri", "密苏里州"), Map.entry("montana", "蒙大拿州"),
            Map.entry("nebraska", "内布拉斯加州"), Map.entry("nevada", "内华达州"),
            Map.entry("new hampshire", "新罕布什尔州"), Map.entry("new jersey", "新泽西州"),
            Map.entry("new mexico", "新墨西哥州"), Map.entry("new york", "纽约州"),
            Map.entry("north carolina", "北卡罗来纳州"), Map.entry("north dakota", "北达科他州"),
            Map.entry("ohio", "俄亥俄州"), Map.entry("oklahoma", "俄克拉何马州"),
            Map.entry("oregon", "俄勒冈州"), Map.entry("pennsylvania", "宾夕法尼亚州"),
            Map.entry("rhode island", "罗得岛州"), Map.entry("south carolina", "南卡罗来纳州"),
            Map.entry("south dakota", "南达科他州"), Map.entry("tennessee", "田纳西州"),
            Map.entry("texas", "得克萨斯州"), Map.entry("utah", "犹他州"),
            Map.entry("vermont", "佛蒙特州"), Map.entry("virginia", "弗吉尼亚州"),
            Map.entry("washington", "华盛顿州"), Map.entry("west virginia", "西弗吉尼亚州"),
            Map.entry("wisconsin", "威斯康星州"), Map.entry("wyoming", "怀俄明州"),
            Map.entry("district of columbia", "哥伦比亚特区"),
            Map.entry("washington, d.c.", "哥伦比亚特区"), Map.entry("washington dc", "哥伦比亚特区"));

    /** 国名：空码返回 null，未收录的码原样返回。 */
    public static String country(String code) {
        if (code == null || code.isBlank()) return null;
        String key = code.trim().toUpperCase(Locale.ROOT);
        return COUNTRIES.getOrDefault(key, key);
    }

    /** 行政区名：空值返回 null，未收录的英文名原样返回。 */
    public static String region(String raw) {
        if (raw == null || raw.isBlank()) return null;
        String trimmed = raw.trim();
        String key = normalize(trimmed);
        String mapped = REGIONS.get(key);
        if (mapped != null) return mapped;
        String autonomous = autonomous(key);
        return autonomous != null ? autonomous : trimmed;
    }

    /** 查表键：小写、分隔符统一为空格、压缩连续空白。 */
    private static String normalize(String raw) {
        return raw.toLowerCase(Locale.ROOT)
                .replace('_', ' ')
                .replace('-', ' ')
                .replaceAll("\\s+", " ")
                .trim();
    }

    /** 自治区：ip-api 与 ipapi.co 的写法不一致（如 Nei Mongol / Inner Mongolia），按关键词兜底。 */
    private static String autonomous(String key) {
        if (key.contains("mongol")) return "内蒙古自治区";
        if (key.contains("uygur") || key.contains("uyghur")) return "新疆维吾尔自治区";
        if (key.contains("zhuang")) return "广西壮族自治区";
        if (key.contains("ningxia")) return "宁夏回族自治区";
        if (key.contains("tibet") || key.contains("xizang")) return "西藏自治区";
        return null;
    }
}
