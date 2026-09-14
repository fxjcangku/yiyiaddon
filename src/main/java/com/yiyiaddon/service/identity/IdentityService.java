package com.yiyiaddon.service.identity;

import com.yiyiaddon.model.identity.BlockIdentity;
import com.yiyiaddon.model.identity.EntityIdentity;
import com.yiyiaddon.model.identity.ItemIdentity;
import com.yiyiaddon.platform.identity.BlockIdentifier;
import com.yiyiaddon.platform.identity.EntityIdentifier;
import com.yiyiaddon.platform.identity.ItemIdentifier;
import com.yiyiaddon.repository.identity.BlockIdentityRepository;
import com.yiyiaddon.repository.identity.BlockSnapshotRepository;
import com.yiyiaddon.repository.identity.EntityIdentityRepository;
import com.yiyiaddon.repository.identity.ItemIdentityRepository;
import com.yiyiaddon.repository.identity.ItemSnapshotRepository;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 身份服务：物品 / 实体 / 方块三套身份数据与快照的统一入口。
 *
 * <p>业务模块只与本服务交互，不直接接触仓储层，保证「同一份数据只有一个事实来源」。</p>
 *
 * <p>本服务持有内存集合与变更监听器；仓储层只负责文件读写。三者数据目录严格隔离，
 * 互不写入。</p>
 */
public final class IdentityService {

    private static volatile IdentityService shared;

    private final ItemIdentityRepository items = new ItemIdentityRepository();
    private final EntityIdentityRepository entities = new EntityIdentityRepository();
    private final BlockIdentityRepository blocks = new BlockIdentityRepository();
    private final ItemSnapshotRepository itemSnapshots = new ItemSnapshotRepository();
    private final BlockSnapshotRepository blockSnapshots = new BlockSnapshotRepository();

    /** 数据变更监听器：增删 / 清空 / 重载后触发 */
    private final List<Runnable> listeners = new ArrayList<>();

    private boolean loaded;

    private IdentityService() {
    }

    /** 全局共享实例（懒加载，进程内唯一） */
    public static IdentityService shared() {
        IdentityService instance = shared;
        if (instance == null) {
            synchronized (IdentityService.class) {
                instance = shared;
                if (instance == null) {
                    instance = new IdentityService();
                    shared = instance;
                }
            }
        }
        return instance;
    }

    /**
     * 载入全部身份数据（幂等）。
     *
     * <p>载入后按注册表清理已失效条目：历史存档里可能残留已被移除的物品 / 实体 / 方块 ID，
     * 它们无法再被识别，继续保留只会污染选择列表。</p>
     */
    public void load() {
        if (loaded) return;
        loaded = true;
        items.reload();
        entities.reload();
        blocks.reload();
        itemSnapshots.reload();
        blockSnapshots.reload();

        items.all().stream()
            .filter(identity -> !ItemIdentifier.exists(identity.itemId()))
            .forEach(items::remove);
        entities.pruneMissing(EntityIdentifier::exists);
        blocks.pruneMissing(BlockIdentifier::exists);

        notifyChanged();
    }

    /** 强制重新载入 */
    public void reload() {
        loaded = false;
        load();
    }

    /** 注册数据变更监听器 */
    public void addListener(Runnable listener) {
        if (listener != null) listeners.add(listener);
    }

    // ── 物品身份 ──

    public Path itemDirectory() {
        return items.directory();
    }

    public Set<ItemIdentity> allItems() {
        return items.all();
    }

    public ItemIdentity findItem(String key) {
        return items.findByKey(key);
    }

    public int itemCount() {
        return items.size();
    }

    /** 按身份键集合解析出完整身份（唯一数据源，无副本） */
    public List<ItemIdentity> itemsOf(Set<String> keys) {
        List<ItemIdentity> result = new ArrayList<>();
        if (keys == null) return result;
        for (String key : new LinkedHashSet<>(keys)) {
            ItemIdentity identity = items.findByKey(key);
            if (identity != null) result.add(identity);
        }
        return result;
    }

    /** 新增物品身份；返回文件名，重复或失败返回 {@code null} */
    public String addItem(ItemIdentity identity) {
        String fileName = items.add(identity);
        if (fileName != null) notifyChanged();
        return fileName;
    }

    public boolean removeItem(ItemIdentity identity) {
        boolean ok = items.remove(identity);
        if (ok) notifyChanged();
        return ok;
    }

    /** 清空全部物品身份 */
    public boolean clearItems() {
        boolean ok = items.clear();
        if (ok) notifyChanged();
        return ok;
    }

    // ── 实体身份 ──

    public Path entityDirectory() {
        return entities.directory();
    }

    public Set<EntityIdentity> allEntities() {
        return entities.all();
    }

    public EntityIdentity findEntity(String key) {
        return entities.findByKey(key);
    }

    public int entityCount() {
        return entities.size();
    }

    public String addEntity(EntityIdentity identity) {
        String fileName = entities.add(identity);
        if (fileName != null) notifyChanged();
        return fileName;
    }

    public boolean removeEntity(EntityIdentity identity) {
        boolean ok = entities.remove(identity);
        if (ok) notifyChanged();
        return ok;
    }

    public boolean clearEntities() {
        boolean ok = entities.clear();
        if (ok) notifyChanged();
        return ok;
    }

    // ── 方块身份 ──

    public Path blockDirectory() {
        return blocks.directory();
    }

    public Set<BlockIdentity> allBlocks() {
        return blocks.all();
    }

    public BlockIdentity findBlock(String key) {
        return blocks.findByKey(key);
    }

    public int blockCount() {
        return blocks.size();
    }

    /** 新增或更新方块身份；返回文件名，无变化或失败返回 {@code null} */
    public String addBlock(BlockIdentity identity) {
        String fileName = blocks.add(identity);
        if (fileName != null) notifyChanged();
        return fileName;
    }

    public boolean removeBlock(BlockIdentity identity) {
        boolean ok = blocks.remove(identity);
        if (ok) notifyChanged();
        return ok;
    }

    public boolean clearBlocks() {
        boolean ok = blocks.clear();
        if (ok) notifyChanged();
        return ok;
    }

    // ── 快照 ──

    public Path itemSnapshotDirectory() {
        return itemSnapshots.directory();
    }

    public Path blockSnapshotDirectory() {
        return blockSnapshots.directory();
    }

    /**
     * 保存物品状态快照。
     *
     * @param force 是否强制另存（自动保存传 false，状态相同即跳过）
     * @return 文件名；跳过或失败返回 {@code null}
     */
    public String addItemSnapshot(ItemIdentity identity, boolean force) {
        return itemSnapshots.addSnapshot(identity, force);
    }

    /** 保存方块状态快照 */
    public String addBlockSnapshot(BlockIdentity identity, boolean force) {
        return blockSnapshots.addSnapshot(identity, force);
    }

    public int itemSnapshotCount() {
        return itemSnapshots.size();
    }

    public int blockSnapshotCount() {
        return blockSnapshots.size();
    }

    public boolean clearItemSnapshots() {
        return itemSnapshots.clear();
    }

    public boolean clearBlockSnapshots() {
        return blockSnapshots.clear();
    }

    /** 广播数据变更 */
    private void notifyChanged() {
        for (Runnable listener : listeners) {
            try {
                listener.run();
            } catch (Exception ignored) {
                // 单个监听器异常不阻断其余监听器
            }
        }
    }
}
