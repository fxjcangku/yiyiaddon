package com.yiyiaddon.service.update;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.util.regex.Pattern;

/** GitHub 发布列表的纯解析层；只接受本仓库已上传安装包的公开版本，不执行远程正文。 */
public final class ReleaseCatalog {
    public static final URI REPOSITORY = URI.create("https://github.com/fxjcangku/yiyiaddon");
    public static final URI RELEASES = URI.create(REPOSITORY + "/releases");

    /** 弹窗只展示验证过的版本字段，链接由固定仓库与合法标签构造。 */
    public record Release(String tag, ReleaseVersion version, boolean prerelease) {
        public URI page() { return URI.create(RELEASES + "/tag/" + tag); }
        public String channel() { return prerelease || !version.qualifier().isEmpty() ? "测试版" : "正式版"; }
    }

    private ReleaseCatalog() { }

    /**
     * 发布包名：本线是 `yiyiaddon-<版本>-<本线 MC 版本>.zip|.jar`，或老口径的 `yiyiaddon-<版本>.zip|.jar`。
     * 个人版是 `...-personal+<MC 版本>.jar`，不是发布包，不能据此提示更新。
     *
     * <p><b>为什么必须按 MC 版本筛</b>：两条版本线的版本号相同、共用同一份发布列表，
     * 若不管附件属于哪条线，某次只挂了 26.2 的包时 26.1.2 的玩家也会被提示更新，
     * 点进去只拿得到一个 Fabric 会直接拒绝加载的包。`minecraftVersion` 为空（运行时取不到）时
     * 退回不筛，宁可多提示也不要漏提示。</p>
     */
    private static boolean isReleaseAsset(String name, String number, String minecraftVersion) {
        String own = minecraftVersion == null || minecraftVersion.isBlank()
                ? "-[0-9][0-9A-Za-z.]*"
                : "-" + Pattern.quote(minecraftVersion);
        return name.matches("yiyiaddon-" + Pattern.quote(number) + "(" + own + ")?\\.(zip|jar)");
    }

    /**
     * 按版本值选择最新包，不依赖 GitHub 返回顺序，忽略草稿、坏条目和未传完的包。
     *
     * @param minecraftVersion 运行中的 Minecraft 版本号（如 26.1.2 / 26.2），用于只认本线的附件
     */
    public static Release newest(String json, String minecraftVersion) {
        Release newest = null;
        for (JsonElement element : JsonParser.parseString(json).getAsJsonArray()) {
            try {
                JsonObject item = element.getAsJsonObject();
                if (item.get("draft").getAsBoolean() || item.get("published_at").isJsonNull()) continue;
                String tag = item.get("tag_name").getAsString();
                ReleaseVersion version = ReleaseVersion.parse(tag);
                if (version == null || !tag.matches("[vV]?[0-9A-Za-z.+-]+")) continue;
                String number = tag.replaceFirst("^[vV]", "");
                boolean ready = false;
                for (JsonElement asset : item.getAsJsonArray("assets")) {
                    JsonObject file = asset.getAsJsonObject();
                    String name = file.get("name").getAsString();
                    if (isReleaseAsset(name, number, minecraftVersion)
                            && "uploaded".equals(file.get("state").getAsString()) && file.get("size").getAsLong() > 0) {
                        ready = true;
                    }
                }
                if (ready && (newest == null || version.compareTo(newest.version()) > 0)) {
                    newest = new Release(tag, version, item.get("prerelease").getAsBoolean());
                }
            } catch (RuntimeException ignored) {
                // 单条发布元数据不完整时继续检查其余条目，不把坏条目当成可安装版本。
            }
        }
        return newest;
    }
}
