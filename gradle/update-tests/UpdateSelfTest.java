import com.yiyiaddon.service.update.*;
import java.nio.file.Files;
import java.nio.file.Path;

/** 不启动游戏的更新判据回归：版本排序、发布筛选、提示门闩、跳过版本持久化及失败回滚。 */
public final class UpdateSelfTest {
    private static int checks;
    private static void check(boolean condition, String name) {
        checks++;
        if (!condition) throw new AssertionError(name);
    }
    private static ReleaseVersion version(String value) { return ReleaseVersion.parse(value); }
    private static String release(String tag, boolean draft, boolean ready, boolean beta) {
        return "{\"tag_name\":\"" + tag + "\",\"draft\":" + draft + ",\"prerelease\":" + beta
                + ",\"published_at\":\"2026-09-19T00:00:00Z\",\"assets\":[{\"name\":\"yiyiaddon-"
                + tag.substring(1) + ".zip\",\"state\":\"" + (ready ? "uploaded" : "new") + "\",\"size\":100}]}";
    }
    public static void main(String[] args) throws Exception {
        check(version("v1.0-beta1").compareTo(version("1.0.0-beta.1")) == 0, "版本规范化");
        check(version("1.0-beta10").compareTo(version("1.0-beta2")) > 0, "Beta 数字顺序");
        check(version("1.0").compareTo(version("1.0-rc9")) > 0, "正式版晚于候选版");
        check(version("1.10").compareTo(version("1.9")) > 0, "次版本数字顺序");
        check(version("1.0+build2").compareTo(version("1.0+build1")) == 0, "构建元数据");
        check(version("unknown") == null && version("1.0/evil") == null, "非法版本");
        String beta2 = release("v1.0-beta2", false, true, true);
        var latest = ReleaseCatalog.newest("[" + beta2 + "," + release("v1.0-beta10", false, true, true)
                + "," + release("v2.0", true, true, false) + "," + release("v3.0", false, false, false) + "]");
        check(latest.tag().equals("v1.0-beta10"), "忽略草稿和未上传包并按版本排序");
        check(latest.channel().equals("测试版"), "明确测试版");
        check(latest.page().toString().equals("https://github.com/fxjcangku/yiyiaddon/releases/tag/v1.0-beta10"), "固定发布页");
        check(ReleaseCatalog.newest("[{}," + beta2 + "]").tag().equals("v1.0-beta2"), "坏条目隔离");
        check(ReleaseCatalog.newest("[]") == null, "空列表");
        check(ReleaseCatalog.newest("[" + beta2.replace(".zip", "-personal.jar") + "]") == null, "不提示个人包");
        UpdateSession session = new UpdateSession();
        check(!session.claimPrompt(false, true), "游戏中不弹窗");
        check(!session.claimPrompt(true, false), "没有更新不消耗提示");
        check(session.claimPrompt(true, true), "首次安全界面提示");
        check(!session.claimPrompt(true, true), "稍后更新后本进程不再提示");
        check(new UpdateSession().claimPrompt(true, true), "重启恢复提示机会");
        Path root = Files.createTempDirectory(Path.of("build"), "update-test-");
        UpdatePreferences preferences = new UpdatePreferences(root.resolve("config/update.json"));
        check(preferences.skip(latest), "跳过版本成功保存");
        UpdatePreferences restarted = new UpdatePreferences(root.resolve("config/update.json"));
        check(restarted.isSkipped(version("1.0.0-beta.10")), "跳过版本跨重启生效");
        check(!restarted.isSkipped(version("1.0-beta11")), "更高版本恢复提示");
        Path blocker = root.resolve("blocked");
        Files.writeString(blocker, "不能作为目录");
        UpdatePreferences failed = new UpdatePreferences(blocker.resolve("update.json"));
        check(!failed.skip(latest) && !failed.isSkipped(latest.version()), "保存失败不伪装成功");
        if (args.length > 0) {
            var online = ReleaseCatalog.newest(Files.readString(Path.of(args[0])));
            check(online != null, "真实发布列表解析");
            System.out.println("公开最新版本：" + online.tag() + " · " + online.channel());
        }
        System.out.println("更新行为检查通过：" + checks + " 项");
    }
}
