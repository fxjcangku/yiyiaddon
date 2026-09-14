package com.yiyiaddon.repository.identity;

import com.google.gson.JsonObject;
import com.yiyiaddon.model.identity.ItemIdentity;
import com.yiyiaddon.platform.storage.GamePaths;
import com.yiyiaddon.repository.JsonFileStore;
import com.yiyiaddon.utils.FileNames;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 物品状态快照库：管理「同一物品在不同状态下」的完整快照（水量、耐久、动态自定义数据等）。
 *
 * <p>与物品身份库职责严格分离：身份库供匹配消费，快照库供调试与服务器机制研究。</p>
 *
 * <p><b>去重语义：</b></p>
 * <ul>
 *   <li>去重按状态指纹而非稳定身份键——水量变化即指纹变化，允许保存为新快照；</li>
 *   <li>{@code force=true}（用户明确「另存快照」）时用时间戳保证文件名唯一，绝不覆盖已有文件；</li>
 *   <li>{@code force=false}（自动保存）时状态完全相同即跳过，避免无限生成重复文件。</li>
 * </ul>
 */
public final class ItemSnapshotRepository {

    /** 已保存快照的状态指纹（用于自动保存去重） */
    private final Set<String> savedFingerprints = new LinkedHashSet<>();

    /** 快照根目录 */
    public Path directory() {
        return GamePaths.itemSnapshots();
    }

    /**
     * 保存一个完整状态快照。
     *
     * @param identity 识别出的完整物品身份（含当前组件数据）
     * @param force    是否强制另存
     * @return 保存后的文件名；自动保存且指纹已存在时返回 {@code null}
     */
    public String addSnapshot(ItemIdentity identity, boolean force) {
        if (identity == null) return null;
        String fingerprint = identity.stateFingerprint();
        if (!force && savedFingerprints.contains(fingerprint)) return null;

        String base = FileNames.sanitize(identity.displayName(), identity.itemId());
        String name = force
            ? base + "_" + fingerprint + "_" + System.currentTimeMillis()
            : base + "_" + fingerprint;
        Path path = JsonFileStore.uniquePath(directory(), name);

        JsonObject json = identity.toJsonObject();
        json.addProperty("状态指纹", fingerprint);
        json.addProperty("采集时间", System.currentTimeMillis());
        if (!JsonFileStore.writeAtomic(path, json)) return null;

        savedFingerprints.add(fingerprint);
        return path.getFileName().toString();
    }

    /** 加载全部快照的状态指纹；损坏文件跳过 */
    public void reload() {
        savedFingerprints.clear();
        Path dir = directory();
        if (!Files.isDirectory(dir)) return;
        for (JsonObject json : JsonFileStore.readAll(dir)) {
            if (json.has("状态指纹") && !json.get("状态指纹").isJsonNull()) {
                savedFingerprints.add(json.get("状态指纹").getAsString());
            }
        }
    }

    /** 清空全部快照；部分删除失败时以磁盘实际内容重建索引，不谎报已清空 */
    public boolean clear() {
        boolean ok = JsonFileStore.deleteAllJson(directory());
        if (ok) {
            savedFingerprints.clear();
        } else {
            reload();
        }
        return ok;
    }

    public int size() {
        return savedFingerprints.size();
    }

    public boolean hasAny() {
        return !savedFingerprints.isEmpty();
    }
}
