package com.yiyiaddon.repository.identity;

import com.google.gson.JsonObject;
import com.yiyiaddon.model.identity.ItemIdentity;
import com.yiyiaddon.platform.storage.GamePaths;
import com.yiyiaddon.repository.JsonFileStore;
import com.yiyiaddon.utils.FileNames;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 物品身份库：维护「已识别 / 手动添加」的物品身份集合。
 *
 * <p>持久化为「一物一文件」：每个身份序列化到独立 JSON，文件名优先使用中文显示名
 * （重名自动追加 {@code _2}、{@code _3}），中文名只做文件名与展示，真正的身份判定以
 * 物品 ID + 自定义逻辑 ID + 身份显示名 + 附魔为准。</p>
 *
 * <p>本类只负责存储与去重，不持有监听器、不做业务判断。</p>
 */
public final class ItemIdentityRepository {

    private final Set<ItemIdentity> identities = new LinkedHashSet<>();

    /** 物品身份根目录 */
    public Path directory() {
        return GamePaths.itemIdentities();
    }

    /** 重新加载全部身份；单个文件损坏跳过，不阻塞整体加载 */
    public void reload() {
        identities.clear();
        for (JsonObject json : JsonFileStore.readAll(directory())) {
            ItemIdentity identity = ItemIdentity.fromJsonObject(json);
            if (identity != null) identities.add(identity);
        }
    }

    /**
     * 新增一个身份（内存去重 + 落盘为新文件）。
     *
     * @return 成功时返回文件名；已存在或写入失败返回 {@code null}
     */
    public String add(ItemIdentity identity) {
        if (identity == null) return null;
        if (identities.contains(identity)) return null;
        Path path = JsonFileStore.uniquePath(directory(), fileNameBase(identity));
        if (!JsonFileStore.writeAtomic(path, identity.toJsonObject())) return null;
        identities.add(identity);
        return path.getFileName().toString();
    }

    /** 删除一个身份（先改内存，落盘失败则回滚内存） */
    public boolean remove(ItemIdentity identity) {
        if (identity == null) return false;
        if (!identities.remove(identity)) return false;
        if (!sync()) {
            identities.add(identity);
            return false;
        }
        return true;
    }

    /** 清空全部身份（落盘失败则回滚内存） */
    public boolean clear() {
        Set<ItemIdentity> before = new LinkedHashSet<>(identities);
        identities.clear();
        if (sync()) return true;
        identities.addAll(before);
        return false;
    }

    /** 全部身份的只读快照 */
    public Set<ItemIdentity> all() {
        return new LinkedHashSet<>(identities);
    }

    /** 按身份键查找完整身份；未命中返回 {@code null} */
    public ItemIdentity findByKey(String key) {
        if (key == null) return null;
        for (ItemIdentity identity : identities) {
            if (identity.identityKey().equals(key)) return identity;
        }
        return null;
    }

    public boolean hasAny() {
        return !identities.isEmpty();
    }

    public int size() {
        return identities.size();
    }

    /** 整目录事务式重写，保持磁盘与内存一致 */
    private boolean sync() {
        List<JsonFileStore.NamedJson> entries = new ArrayList<>();
        for (ItemIdentity identity : identities) {
            entries.add(new JsonFileStore.NamedJson(fileNameBase(identity), identity.toJsonObject()));
        }
        return JsonFileStore.syncAll(directory(), entries, "items");
    }

    /** 文件名主体：中文显示名优先，回退物品 ID */
    private String fileNameBase(ItemIdentity identity) {
        return FileNames.sanitize(identity.displayName(), identity.itemId());
    }
}
