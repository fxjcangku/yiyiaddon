package com.yiyiaddon.repository.identity;

import com.google.gson.JsonObject;
import com.yiyiaddon.model.identity.EntityIdentity;
import com.yiyiaddon.platform.storage.GamePaths;
import com.yiyiaddon.repository.JsonFileStore;
import com.yiyiaddon.utils.FileNames;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 实体身份库：维护已识别的实体身份集合。
 *
 * <p>与物品身份库结构对称但数据源严格隔离：实体一实体一文件，绝不写入物品目录。去重以
 * 身份判据（实体类型 ID + 自定义名）为准，重复识别同一种实体不会无意义生成 {@code _2} 文件。</p>
 */
public final class EntityIdentityRepository {

    private final Set<EntityIdentity> identities = new LinkedHashSet<>();

    /** 实体身份根目录 */
    public Path directory() {
        return GamePaths.entityIdentities();
    }

    /** 重新加载全部身份；单个文件损坏跳过 */
    public void reload() {
        identities.clear();
        for (JsonObject json : JsonFileStore.readAll(directory())) {
            EntityIdentity identity = EntityIdentity.fromJsonObject(json);
            if (identity != null) identities.add(identity);
        }
    }

    /** 新增一个身份；已存在或写入失败返回 {@code null} */
    public String add(EntityIdentity identity) {
        if (identity == null) return null;
        if (identities.contains(identity)) return null;
        Path path = JsonFileStore.uniquePath(directory(), fileNameBase(identity));
        if (!JsonFileStore.writeAtomic(path, identity.toJsonObject())) return null;
        identities.add(identity);
        return path.getFileName().toString();
    }

    /** 删除一个身份（落盘失败回滚内存） */
    public boolean remove(EntityIdentity identity) {
        if (identity == null) return false;
        if (!identities.remove(identity)) return false;
        if (!sync()) {
            identities.add(identity);
            return false;
        }
        return true;
    }

    /** 清空全部身份（落盘失败回滚内存） */
    public boolean clear() {
        Set<EntityIdentity> before = new LinkedHashSet<>(identities);
        identities.clear();
        if (sync()) return true;
        identities.addAll(before);
        return false;
    }

    /** 全部身份的只读快照 */
    public Set<EntityIdentity> all() {
        return new LinkedHashSet<>(identities);
    }

    /** 按身份键查找完整身份 */
    public EntityIdentity findByKey(String key) {
        if (key == null) return null;
        for (EntityIdentity identity : identities) {
            if (identity.identityKey().equals(key)) return identity;
        }
        return null;
    }

    /** 移除注册表中已不存在的实体身份，返回移除数量 */
    public int pruneMissing(java.util.function.Predicate<String> existsCheck) {
        if (existsCheck == null) return 0;
        Set<EntityIdentity> invalid = new LinkedHashSet<>();
        for (EntityIdentity identity : identities) {
            if (!existsCheck.test(identity.entityId())) invalid.add(identity);
        }
        if (invalid.isEmpty()) return 0;
        identities.removeAll(invalid);
        sync();
        return invalid.size();
    }

    public boolean hasAny() {
        return !identities.isEmpty();
    }

    public int size() {
        return identities.size();
    }

    private boolean sync() {
        List<JsonFileStore.NamedJson> entries = new ArrayList<>();
        for (EntityIdentity identity : identities) {
            entries.add(new JsonFileStore.NamedJson(fileNameBase(identity), identity.toJsonObject()));
        }
        return JsonFileStore.syncAll(directory(), entries, "entities");
    }

    private String fileNameBase(EntityIdentity identity) {
        return FileNames.sanitize(identity.displayName(), identity.entityId());
    }
}
