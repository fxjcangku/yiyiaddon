package com.yiyiaddon.repository.identity;

import com.google.gson.JsonObject;
import com.yiyiaddon.model.identity.BlockIdentity;
import com.yiyiaddon.platform.storage.GamePaths;
import com.yiyiaddon.repository.JsonFileStore;
import com.yiyiaddon.utils.FileNames;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 方块状态快照库：管理「同一坐标同一方块在不同状态下」的完整快照（干湿、生长阶段、载体方块等）。
 *
 * <p>与方块身份库职责严格分离：身份库只保留当前稳定身份，历史状态一律落在这里。</p>
 *
 * <p>去重按状态指纹（服务器 + 维度 + 坐标 + 方块 ID + 完整方块状态 + 方块实体数据）。
 * 旧版指纹不含服务器与方块 ID，单独记录并按服务器隔离比对；无服务器字段的旧档不参与自动去重，
 * 避免跨服误判。</p>
 */
public final class BlockSnapshotRepository {

    /** 已保存快照的当前格式状态指纹 */
    private final Set<String> savedFingerprints = new LinkedHashSet<>();

    /** 旧格式指纹按服务器隔离保存（键为 {@code 服务器|旧指纹}） */
    private final Set<String> legacyFingerprints = new LinkedHashSet<>();

    /** 快照根目录 */
    public Path directory() {
        return GamePaths.blockSnapshots();
    }

    /**
     * 保存一个方块状态快照。
     *
     * @param identity 识别出的完整方块身份
     * @param force    是否强制另存
     * @return 保存后的文件名；自动保存且指纹已存在时返回 {@code null}
     */
    public String addSnapshot(BlockIdentity identity, boolean force) {
        if (identity == null) return null;
        String fingerprint = identity.stateFingerprint();
        if (!force && (savedFingerprints.contains(fingerprint) || legacyFingerprintMatches(identity))) {
            return null;
        }

        String base = baseName(identity);
        String coord = identity.x() + "," + identity.y() + "," + identity.z();
        String name = force
            ? base + "@" + coord + "_" + fingerprint + "_" + System.currentTimeMillis()
            : base + "@" + coord + "_" + fingerprint;
        Path path = JsonFileStore.uniquePath(directory(), name);

        JsonObject json = identity.toJsonObject();
        json.addProperty("状态指纹", fingerprint);
        json.addProperty("采集时间", System.currentTimeMillis());
        if (!JsonFileStore.writeAtomic(path, json)) return null;

        savedFingerprints.add(fingerprint);
        return path.getFileName().toString();
    }

    /** 加载全部快照指纹；损坏文件跳过 */
    public void reload() {
        savedFingerprints.clear();
        legacyFingerprints.clear();
        Path dir = directory();
        if (!Files.isDirectory(dir)) return;

        for (JsonObject json : JsonFileStore.readAll(dir)) {
            if (!json.has("状态指纹") || json.get("状态指纹").isJsonNull()) continue;
            String stored = json.get("状态指纹").getAsString();
            BlockIdentity identity = BlockIdentity.fromJsonObject(json);
            if (identity == null) continue;

            if (stored.equals(identity.stateFingerprint())) {
                savedFingerprints.add(stored);
            } else if (stored.equals(identity.legacyStateFingerprint())
                && identity.server() != null && !identity.server().isBlank()
                && !"unknown".equalsIgnoreCase(identity.server())) {
                legacyFingerprints.add(identity.server() + "|" + stored);
            }
        }
    }

    /** 清空全部快照；部分删除失败时以磁盘实际内容重建索引 */
    public boolean clear() {
        boolean ok = JsonFileStore.deleteAllJson(directory());
        if (ok) {
            savedFingerprints.clear();
            legacyFingerprints.clear();
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

    /** 旧快照去重判定：要求旧记录显式带服务器字段才允许命中 */
    private boolean legacyFingerprintMatches(BlockIdentity identity) {
        String server = identity.server();
        if (server == null || server.isBlank() || "unknown".equalsIgnoreCase(server)) return false;
        return legacyFingerprints.contains(server + "|" + identity.legacyStateFingerprint());
    }

    /** 文件名主体：已确认的中文语义名优先，其次语义身份，最后回退方块 ID */
    private String baseName(BlockIdentity identity) {
        if (identity.semanticName() != null && !identity.semanticName().isBlank()) {
            return FileNames.sanitize(identity.semanticName(), "block");
        }
        if (identity.semanticIdentity() != null && !identity.semanticIdentity().isBlank()) {
            return FileNames.sanitize(identity.semanticIdentity(), "block");
        }
        return FileNames.sanitize(identity.blockId(), "block");
    }
}
