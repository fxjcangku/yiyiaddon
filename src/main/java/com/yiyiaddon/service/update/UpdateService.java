package com.yiyiaddon.service.update;

import com.yiyiaddon.platform.ClientIdentity;
import com.yiyiaddon.ui.screen.ClickGuiScreen;
import com.yiyiaddon.ui.screen.UpdatePanelScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.util.Util;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/** 公开发布检查：后台请求、有限超时，游戏线程只读取完成结果及打开安全时机的提示窗。 */
public final class UpdateService {
    private static final URI API = URI.create("https://api.github.com/repos/fxjcangku/yiyiaddon/releases?per_page=100");
    private static final long CHECK_INTERVAL = Duration.ofMinutes(15).toNanos();
    private static final int MAX_RESPONSE = 2 * 1024 * 1024;
    private static final UpdateSession SESSION = new UpdateSession();
    private static CompletableFuture<ReleaseCatalog.Release> pending;
    private static long nextCheck;
    private static boolean started;
    private static ReleaseCatalog.Release latest;
    private static String status = "等待检查";
    private static UpdatePreferences preferences;
    private static ClickGuiScreen manualParent;
    private static long lastManualCheck;

    private UpdateService() { }

    /** 由唯一客户端事件入口调用；不在网络线程触碰游戏、界面或会话门闩。 */
    public static void tick(Minecraft client) {
        if (client == null) return;
        if (preferences == null) preferences = new UpdatePreferences(net.fabricmc.loader.api.FabricLoader
                .getInstance().getConfigDir().resolve("yiyiaddon/update.json"));
        long now = System.nanoTime();
        if (pending != null && pending.isDone()) {
            try {
                latest = pending.join();
                // 正式版客户端只认正式版发布：否则「1.1-beta1 的版本值高于 1.0」会把正式版玩家
                // 提示去装下一版测试包（用户 2026-09-22）。测试版客户端两类一起挑。
                status = latest == null ? (stableChannel() ? "暂无正式版本" : "暂无发布版本")
                    : hasUpdate() ? "发现新版本" : "已是最新版本";
                if (ReleaseVersion.parse(ClientIdentity.version()) == null) status = "开发版本";
            } catch (RuntimeException error) {
                status = "暂时无法检查";
            }
            pending = null;
            if (manualParent != null) {
                ClickGuiScreen parent = manualParent;
                manualParent = null;
                // 玩家已离开请求页面时不抢回界面；失败也只留状态，不复用旧结果冒充检查成功。
                if (client.screen == parent && parent.canShowUpdatePrompt()
                        && !status.equals("暂时无法检查") && hasUpdate()) {
                    SESSION.claimPrompt(true, true);
                    client.setScreen(new UpdatePanelScreen(latest, parent));
                }
            }
        }
        if (pending == null && (!started || now - nextCheck >= 0)) {
            started = true;
            nextCheck = now + CHECK_INTERVAL;
            status = "正在检查更新";
            // 请求与客户端创建均在后台执行；总超时还覆盖响应体读取阶段。
            pending = CompletableFuture.supplyAsync(UpdateService::fetch).orTimeout(15, TimeUnit.SECONDS);
        }
        boolean safe = client.getOverlay() == null && (client.screen instanceof TitleScreen
                || client.screen instanceof ClickGuiScreen gui && gui.canShowUpdatePrompt());
        if (SESSION.claimPrompt(safe, hasUpdate() && !preferences.isSkipped(latest.version()))) {
            client.setScreen(new UpdatePanelScreen(latest, client.screen));
        }
    }

    /** 仅读取公开元数据，不携带账户、服务器信息或访问令牌，也不下载或替换运行中的 JAR。 */
    private static ReleaseCatalog.Release fetch() {
        try (HttpClient http = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build()) {
            HttpRequest request = HttpRequest.newBuilder(API).timeout(Duration.ofSeconds(10))
                    .header("Accept", "application/vnd.github+json")
                    .header("User-Agent", "yiyiaddon-update-check")
                    .header("X-GitHub-Api-Version", "2022-11-28").GET().build();
            // 请求超时覆盖响应体接收；超过解析上限的列表不进入 JSON 解析。
            HttpResponse<byte[]> response = http.send(request, HttpResponse.BodyHandlers.ofByteArray());
            if (response.statusCode() != 200 || response.body().length > MAX_RESPONSE) {
                throw new IllegalStateException("发布检查响应不可用");
            }
            return ReleaseCatalog.newest(new String(response.body(), java.nio.charset.StandardCharsets.UTF_8),
                    ClientIdentity.gameVersion(), stableChannel());
        } catch (Exception error) {
            throw new IllegalStateException("发布检查失败", error);
        }
    }

    /** 本机是不是正式版（版本号不带 {@code -betaN} 这类后缀）；版本号认不出时不筛，两类都收。 */
    private static boolean stableChannel() {
        ReleaseVersion current = ReleaseVersion.parse(ClientIdentity.version());
        return current != null && current.qualifier().isEmpty();
    }

    public static boolean hasUpdate() {
        ReleaseVersion current = ReleaseVersion.parse(ClientIdentity.version());
        return current != null && latest != null && latest.version().compareTo(current) > 0;
    }

    public static String status() { return status; }

    /** 手动检查绕过已跳过版本，但限制连点频率；有在途请求时复用其结果。 */
    public static void checkManually(ClickGuiScreen parent) {
        long now = System.nanoTime();
        if (lastManualCheck != 0 && now - lastManualCheck < Duration.ofSeconds(10).toNanos()) return;
        lastManualCheck = now;
        manualParent = parent;
        if (pending == null) {
            started = false;
            status = "正在检查更新";
        }
    }

    public static boolean skip(ReleaseCatalog.Release release) {
        return preferences != null && preferences.skip(release);
    }

    /** 打开 GitHub 反馈草稿，用户在浏览器补充并提交；不读取日志、账号或服务器信息。 */
    public static void openFeedback() {
        String body = "### 插件版本\n" + versionLabel()
                + "\n\n### 问题描述\n请描述遇到的问题。\n\n### 复现步骤\n1. \n2. \n\n### 预期结果\n"
                + "\n\n### 实际结果\n\n\n### 截图或日志\n可选，请先移除个人敏感信息。\n";
        String query = java.net.URLEncoder.encode(body, java.nio.charset.StandardCharsets.UTF_8);
        Util.getPlatform().openUri(URI.create(ReleaseCatalog.REPOSITORY + "/issues/new?body=" + query));
    }

    /** 标签取当前真实版本，预发布标识统一以中文测试版展示；带上游戏版本，两条版本线模组版本号相同，靠它区分。 */
    public static String versionLabel() {
        String current = ClientIdentity.version();
        ReleaseVersion version = ReleaseVersion.parse(current);
        String game = ClientIdentity.gameVersion();
        return "v" + current + (game.isEmpty() ? "" : " · " + game) + " · "
                + (version == null ? "开发版" : version.qualifier().isEmpty() ? "正式版" : "测试版");
    }

    public static void openRepository() { Util.getPlatform().openUri(ReleaseCatalog.REPOSITORY); }
    public static void openRelease() { openRelease(latest); }
    public static void openRelease(ReleaseCatalog.Release release) {
        Util.getPlatform().openUri(release == null ? ReleaseCatalog.RELEASES : release.page());
    }
}
