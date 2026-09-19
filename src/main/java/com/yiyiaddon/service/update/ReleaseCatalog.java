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
     * 发布包名：`yiyiaddon-<版本>.zip|.jar`，或带 MC 版本的 `yiyiaddon-<版本>-<MC 版本>.zip|.jar`
     * （两条版本线版本号相同，靠 MC 版本后缀区分）。个人版是 `...-personal+<MC 版本>.jar`，
     * 不是发布包，不能据此提示更新。
     */
    private static boolean isReleaseAsset(String name, String number) {
        return name.matches("yiyiaddon-" + Pattern.quote(number) + "(-[0-9][0-9A-Za-z.]*)?\\.(zip|jar)");
    }

    /** 按版本值选择最新包，不依赖 GitHub 返回顺序，忽略草稿、坏条目和未传完的包。 */
    public static Release newest(String json) {
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
                    if (isReleaseAsset(name, number)
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
