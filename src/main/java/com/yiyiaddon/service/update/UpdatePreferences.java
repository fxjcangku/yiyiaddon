package com.yiyiaddon.service.update;

import com.google.gson.JsonObject;
import com.yiyiaddon.repository.JsonFileStore;
import java.nio.file.Path;

/** 仅保存跳过的发布版本；复用原子写入，保存失败时不改变内存中的已保存状态。 */
public final class UpdatePreferences {
    private final Path file;
    private ReleaseVersion skipped;

    public UpdatePreferences(Path file) {
        this.file = file;
        JsonObject json = JsonFileStore.readJson(file);
        try {
            skipped = json == null ? null : ReleaseVersion.parse(json.get("跳过版本").getAsString());
        } catch (RuntimeException ignored) {
            skipped = null;
        }
    }

    public boolean isSkipped(ReleaseVersion version) {
        return skipped != null && skipped.compareTo(version) == 0;
    }

    public boolean skip(ReleaseCatalog.Release release) {
        JsonObject json = new JsonObject();
        json.addProperty("跳过版本", release.tag());
        if (!JsonFileStore.writeAtomic(file, json)) return false;
        skipped = release.version();
        return true;
    }
}
