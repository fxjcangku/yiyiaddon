package com.yiyiaddon.repository.identity;

import com.google.gson.JsonObject;
import com.yiyiaddon.model.identity.BlockIdentity;
import com.yiyiaddon.platform.storage.GamePaths;
import com.yiyiaddon.repository.JsonFileStore;
import com.yiyiaddon.utils.FileNames;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 方块身份库：维护每个「服务器 / 维度 / 坐标」上的当前稳定方块身份，一方块一文件。
 *
 * <p>与方块状态快照库职责分离：历史状态一律落到快照库，本库只保留当前记录。</p>
 *
 * <p><b>去重只看稳定键</b>（服务器 + 维度 + 方块 ID + 坐标），与文件名无关。同一稳定键再次保存时
 * 更新现有记录，绝不因状态变化（干盆 → 湿盆、作物不同阶段）无意义生成 {@code _2}、{@code _3}。</p>
 *
 * <p><b>历史数据迁移：</b>旧版按「方块 ID + 坐标」命名且同坐标不同状态会生成 {@code _2} 文件。
 * 加载时若检测到重复稳定键或文件名不符当前命名规则，先把整个目录备份到
 * {@code blocks-backup-<时间戳>/}，再按稳定键去重（保留修改时间最新的记录）并用新命名重写。
 * 备份不完整则放弃迁移，绝不静默覆盖用户数据。</p>
 */
public final class BlockIdentityRepository {

    /** 稳定键 → 方块身份（保持加载顺序） */
    private final Map<String, BlockIdentity> identities = new LinkedHashMap<>();

    /** 方块身份根目录 */
    public Path directory() {
        return GamePaths.blockIdentities();
    }

    /** 待处理记录：文件路径 + 解析出的身份 + 最后修改时间 */
    private record Entry(Path file, BlockIdentity identity, long modifiedAt) {
    }

    /** 重新加载全部身份；必要时先执行旧数据迁移 */
    public void reload() {
        identities.clear();
        Path dir = directory();
        if (!Files.isDirectory(dir)) return;

        List<Entry> entries = readEntries(dir);
        if (entries.isEmpty()) return;

        if (needsMigration(entries)) {
            migrate(entries);
            entries = readEntries(dir);
        }

        // 按稳定键去重：entries 已按修改时间降序，先遍历到的即最新
        for (Entry entry : entries) {
            identities.putIfAbsent(entry.identity().identityKey(), entry.identity());
        }
    }

    /**
     * 新增或更新一个方块身份。
     *
     * <p>稳定键已存在且内容完全相同 → 无变化返回 {@code null}；已存在但状态变化 → 更新并返回文件名；
     * 全新稳定键 → 新增并返回文件名。落盘失败时回滚内存。</p>
     */
    public String add(BlockIdentity identity) {
        if (identity == null) return null;
        String key = identity.identityKey();
        BlockIdentity existing = identities.get(key);
        if (existing != null && existing.equals(identity)) return null;

        identities.put(key, identity);
        if (!sync()) {
            if (existing == null) identities.remove(key);
            else identities.put(key, existing);
            return null;
        }
        return expectedFileName(identity);
    }

    /** 删除一个方块身份 */
    public boolean remove(BlockIdentity identity) {
        if (identity == null) return false;
        if (identities.remove(identity.identityKey()) == null) return false;
        if (!sync()) {
            identities.put(identity.identityKey(), identity);
            return false;
        }
        return true;
    }

    /** 清空全部方块身份 */
    public boolean clear() {
        Map<String, BlockIdentity> before = new LinkedHashMap<>(identities);
        identities.clear();
        if (sync()) return true;
        identities.putAll(before);
        return false;
    }

    /** 全部身份的只读快照（保持加载顺序） */
    public Set<BlockIdentity> all() {
        return new LinkedHashSet<>(identities.values());
    }

    /** 按稳定键查找完整身份 */
    public BlockIdentity findByKey(String key) {
        return key == null ? null : identities.get(key);
    }

    /** 移除注册表中已不存在的方块身份，返回移除数量 */
    public int pruneMissing(java.util.function.Predicate<String> existsCheck) {
        if (existsCheck == null) return 0;
        Set<String> invalid = new LinkedHashSet<>();
        for (Map.Entry<String, BlockIdentity> entry : identities.entrySet()) {
            if (!existsCheck.test(entry.getValue().blockId())) invalid.add(entry.getKey());
        }
        if (invalid.isEmpty()) return 0;
        for (String key : invalid) identities.remove(key);
        sync();
        return invalid.size();
    }

    public boolean hasAny() {
        return !identities.isEmpty();
    }

    public int size() {
        return identities.size();
    }

    // ── 旧数据迁移 ──

    /** 读取目录下全部 JSON 并解析为身份；损坏文件跳过。结果按修改时间降序排列 */
    private List<Entry> readEntries(Path dir) {
        List<Path> files = new ArrayList<>(JsonFileStore.listJsonFiles(dir));
        List<Entry> entries = new ArrayList<>();
        for (Path file : files) {
            JsonObject json = JsonFileStore.readJson(file);
            if (json == null) continue;
            BlockIdentity identity = BlockIdentity.fromJsonObject(json);
            if (identity != null) {
                entries.add(new Entry(file, identity, JsonFileStore.lastModified(file)));
            }
        }
        entries.sort((a, b) -> Long.compare(b.modifiedAt(), a.modifiedAt()));
        return entries;
    }

    /** 是否需要迁移：存在重复稳定键，或任一文件名不符合当前命名规则 */
    private boolean needsMigration(List<Entry> entries) {
        Set<String> seen = new LinkedHashSet<>();
        for (Entry entry : entries) {
            if (!seen.add(entry.identity().identityKey())) return true;
            if (!entry.file().getFileName().toString().equals(expectedFileName(entry.identity()))) return true;
        }
        return false;
    }

    /**
     * 迁移：先整体备份，再按稳定键去重（保留最新），最后用新命名规则重写。
     */
    private void migrate(List<Entry> entries) {
        Path backup = GamePaths.identityRoot().resolve("blocks-backup-" + System.currentTimeMillis());
        boolean backupComplete = true;
        try {
            Files.createDirectories(backup);
        } catch (Exception ignored) {
            backupComplete = false;
        }
        if (backupComplete) {
            for (Entry entry : entries) {
                try {
                    Files.copy(entry.file(), backup.resolve(entry.file().getFileName().toString()),
                        java.nio.file.StandardCopyOption.COPY_ATTRIBUTES);
                } catch (Exception ignored) {
                    backupComplete = false;
                }
            }
        }
        if (!backupComplete) return;

        // 去重：每个稳定键保留修改时间最新的记录
        Map<String, Entry> canonical = new LinkedHashMap<>();
        for (Entry entry : entries) {
            canonical.putIfAbsent(entry.identity().identityKey(), entry);
        }

        Path dir = directory();
        Path temp = dir.resolveSibling("blocks-migrate-" + java.util.UUID.randomUUID());
        try {
            Files.createDirectories(temp);
            Set<String> used = new LinkedHashSet<>();
            for (Entry entry : canonical.values()) {
                String name = JsonFileStore.plannedFileName(fileBaseName(entry.identity()), used);
                if (!JsonFileStore.writeAtomic(temp.resolve(name), entry.identity().toJsonObject())) {
                    throw new java.io.IOException("迁移写入失败");
                }
            }
            Files.createDirectories(dir);
            for (Path staged : JsonFileStore.listJsonFiles(temp)) {
                JsonFileStore.moveReplace(staged, dir.resolve(staged.getFileName().toString()));
            }
            for (Entry entry : entries) {
                Path old = entry.file();
                if (!Files.exists(dir.resolve(old.getFileName().toString()))) Files.deleteIfExists(old);
            }
            JsonFileStore.deleteTree(temp);
        } catch (Exception ignored) {
            JsonFileStore.deleteTree(temp);
        }
    }

    // ── 持久化 ──

    /** 期望文件名（含后缀），与无冲突时的实际写入结果一致 */
    private String expectedFileName(BlockIdentity identity) {
        return fileBaseName(identity) + "@" + identity.x() + "," + identity.y() + "," + identity.z() + ".json";
    }

    /** 文件名主体：已确认的中文语义名优先，其次语义身份，最后回退方块 ID */
    private String fileBaseName(BlockIdentity identity) {
        if (identity.semanticName() != null && !identity.semanticName().isBlank()) {
            return FileNames.sanitize(identity.semanticName(), "block");
        }
        if (identity.semanticIdentity() != null && !identity.semanticIdentity().isBlank()) {
            return FileNames.sanitize(identity.semanticIdentity(), "block");
        }
        return FileNames.sanitize(identity.blockId(), "block");
    }

    /** 整目录事务式重写 */
    private boolean sync() {
        List<JsonFileStore.NamedJson> entries = new ArrayList<>();
        for (BlockIdentity identity : identities.values()) {
            String base = fileBaseName(identity) + "@" + identity.x() + "," + identity.y() + "," + identity.z();
            entries.add(new JsonFileStore.NamedJson(base, identity.toJsonObject()));
        }
        return JsonFileStore.syncAll(directory(), entries, "blocks");
    }
}
