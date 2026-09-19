package com.yiyiaddon.feature.stardew.selector;

import com.yiyiaddon.feature.stardew.config.StardewSettings;
import com.yiyiaddon.feature.stardew.profile.StardewResourceIndex;
import com.yiyiaddon.feature.stardew.profile.StardewToolDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 六类选择器的数据与业务：内存镜像 + 按 ServerKey 隔离落盘。
 *
 * <p>逐字搬运自 {@code StardewFarmModule} 的同类职责：内部类 {@code Selection}、六类选择的装配、
 * {@code selection(StardewSelectorCategory)} 访问器与「按服务器绑定 / 清理失效键」的批量入口。
 * 模块只保留 1 行委托，界面壳的调用点不变。</p>
 */
public final class StardewSelectionBinding {

    private final StardewResourceIndex index;
    private final Selection crop;
    private final Selection pot;
    private final Selection fertilizer;
    private final Selection potion;
    private final Selection can;
    private final Selection sprinkler;
    private final Selection shelter;
    /** 当前绑定的服务器；盆型按维度分档时需要它来切档 */
    private String serverKey;

    public StardewSelectionBinding(StardewResourceIndex index, StardewSettings settings) {
        this.index = index;
        // 七类选择器（数据源：资源包扫描 + ID 配置双源合并）
        crop = new Selection(StardewSelectorCategory.CROP, index, settings.selectedCropKeys);
        // 盆型按维度分档：主世界记普通盆、下界记下界盆、末地记末地盆，换维度不必重新勾
        pot = new Selection(StardewSelectorCategory.POT, index, settings.selectedPotKeys, true);
        fertilizer = new Selection(StardewSelectorCategory.FERTILIZER, index, settings.selectedFertilizerKeys);
        potion = new Selection(StardewSelectorCategory.POTION, index, settings.selectedPotionKeys);
        can = new Selection(StardewSelectorCategory.WATERING_CAN, index, settings.selectedCanKeys);
        sprinkler = new Selection(StardewSelectorCategory.SPRINKLER, index, settings.selectedSprinklerKeys);
        shelter = new Selection(StardewSelectorCategory.SHELTER, index, settings.selectedShelterKeys);
    }

    public Selection crop() {
        return crop;
    }

    public Selection pot() {
        return pot;
    }

    public Selection fertilizer() {
        return fertilizer;
    }

    public Selection potion() {
        return potion;
    }

    public Selection can() {
        return can;
    }

    public Selection sprinkler() {
        return sprinkler;
    }

    /** 温室玻璃选择（盆上方 5 格内识别用；未勾选 = 不做季节豁免） */
    public Selection shelter() {
        return shelter;
    }

    /**
     * 七类选择的数据入口（界面壳使用）。
     *
     * <p>与旧项目的 {@code StardewTargetSetting} 一一对应：界面只负责把选中项加入 / 移除，
     * 写盘一律交回本对象，保证「选择的内存镜像」与「按 ServerKey 落盘」只有一份实现。</p>
     */
    public Selection selection(StardewSelectorCategory category) {
        if (category == null) return crop;
        return switch (category) {
            case CROP -> crop;
            case POT -> pot;
            case FERTILIZER -> fertilizer;
            case POTION -> potion;
            case WATERING_CAN -> can;
            case SPRINKLER -> sprinkler;
            case SHELTER -> shelter;
        };
    }

    /** 选择集合按服务器隔离：切服后这里会把 A 服的选择换成 B 服自己的 */
    public void bindServer(String serverKey, String dimension) {
        this.serverKey = serverKey;
        crop.bindTo(serverKey, dimension);
        pot.bindTo(serverKey, dimension);
        fertilizer.bindTo(serverKey, dimension);
        potion.bindTo(serverKey, dimension);
        can.bindTo(serverKey, dimension);
        sprinkler.bindTo(serverKey, dimension);
        shelter.bindTo(serverKey, dimension);
    }

    /**
     * 按当前维度切换盆型档位（主世界 / 下界 / 末地各一份）。
     *
     * <p>由模块每 tick 在装配协调器前调用；档位没变时只做两次字符串比较，不做任何 IO。</p>
     *
     * @return 档位是否真的换了
     */
    public boolean bindDimension(String dimension) {
        return pot.bindTo(serverKey, dimension);
    }

    /** 清理七类选择器里已不在当前索引中的失效键，并写回当前服务器档案 */
    public void pruneAndPersist() {
        if (index.isEmpty()) return;
        crop.pruneInvalid();
        pot.pruneInvalid();
        fertilizer.pruneInvalid();
        potion.pruneInvalid();
        can.pruneInvalid();
        sprinkler.pruneInvalid();
        shelter.pruneInvalid();
        crop.persist();
        pot.persist();
        fertilizer.persist();
        potion.persist();
        can.persist();
        sprinkler.persist();
        shelter.persist();
        // 旧实现的 refreshCount() 六行是纯 GUI 计数标签刷新，随界面壳由界面批处理接入，此处不再搬运
    }

    /**
     * 七类选择器的数据与业务：内存镜像 + 按 ServerKey 隔离落盘。
     *
     * <p>旧项目这里是旧框架设置子类 {@code StardewTargetSetting}（含设置控件与计数标签）；
     * 界面壳由界面批处理另行接入，本类只保留数据语义：{@code bindServer / persist /
     * pruneInvalid / selectedKeys / selectedCropKeys}，逐字对应旧实现。</p>
     */
    public static final class Selection {

        private final StardewSelectorCategory category;
        private final StardewResourceIndex index;
        private final List<String> keys;
        /**
         * 是否按维度分档保存：只有盆型是 {@code true}。
         *
         * <p><b>为什么盆型要分档：</b>盆型是互斥单选，但一个存档里主世界用普通盆、下界用下界盆、
         * 末地用末地盆是常态。全局只记一份时，每换一个维度都得回选择器重勾，而且忘了就会被
         * 「盆型不匹配」拦在启动自检里（实机反馈）。分档后这一份选择跟着维度走，与点位同一口径。</p>
         */
        private final boolean perDimension;
        private String boundServerKey;
        /** 当前所属维度（仅 {@link #perDimension} 有意义） */
        private String dimension;
        /** 当前已加载档位；用于「服务器或档位变了才重载」 */
        private String boundStoreKey;

        private Selection(StardewSelectorCategory category, StardewResourceIndex index, List<String> keys) {
            this(category, index, keys, false);
        }

        private Selection(StardewSelectorCategory category, StardewResourceIndex index, List<String> keys,
                          boolean perDimension) {
            this.category = category;
            this.index = index;
            this.keys = keys;
            this.perDimension = perDimension;
        }

        private List<String> get() {
            return keys;
        }

        private void set(List<String> values) {
            keys.clear();
            keys.addAll(values);
        }

        /** 本选择在档案里的存储键：盆型带维度分档，其余就是类别名 */
        private String storeKey(String dimension) {
            if (!perDimension || dimension == null || dimension.isBlank()) return category.name();
            return category.name() + '@' + dimension;
        }

        /**
         * 绑定到「服务器 + 维度」档位；档位真的变了才重载。
         *
         * <p>换档前先写回旧档：界面每次改动都会 {@code persist}，这里是双保险，
         * 保证任何一条程序化改动路径也不会让旧维度的勾选丢掉。</p>
         *
         * @return 是否发生了切换（调用方可据此重扫 / 重播报）
         */
        private boolean bindTo(String serverKey, String dimension) {
            if (serverKey == null || serverKey.isBlank()) return false;
            String nextDimension = perDimension ? dimension : null;
            String nextStoreKey = storeKey(nextDimension);
            if (serverKey.equals(boundServerKey) && nextStoreKey.equals(boundStoreKey)) return false;
            persist();
            this.dimension = nextDimension;
            boundServerKey = serverKey;
            boundStoreKey = nextStoreKey;
            Map<String, List<String>> all = StardewSelectionStore.load(serverKey);
            List<String> loaded = all.get(nextStoreKey);
            // 该维度还没记过时沿用旧存档里的全局勾选：老玩家的选择不会因为这次分档而凭空消失
            if (loaded == null && !nextStoreKey.equals(category.name())) loaded = all.get(category.name());
            set(loaded == null ? List.of() : loaded);
            return true;
        }

        /** 把当前选择写回已绑定服务器的当前档位（未绑定服务器时不做任何事） */
        public void persist() {
            if (boundServerKey == null) return;
            StardewSelectionStore.save(boundServerKey, storeKey(dimension), get());
        }

        /** 清理已失效的选中项（对应作物 / 资源已不在索引中） */
        private int pruneInvalid() {
            List<String> current = new ArrayList<>(get());
            List<String> valid = new ArrayList<>();
            for (String key : current) {
                if (isValidKey(key)) valid.add(key);
            }
            int removed = current.size() - valid.size();
            if (removed > 0) set(valid);
            return removed;
        }

        /** 判断一个键是否仍属于本类别（作物键或工具资源键） */
        private boolean isValidKey(String key) {
            if (key == null) return false;
            if (category == StardewSelectorCategory.CROP) {
                return index.cropByKey(key) != null;
            }
            StardewToolDefinition entry = index.entryByKey(key);
            return entry != null && entry.category() == category;
        }

        /** 当前已选中的稳定键（作物键或资源键） */
        public List<String> selectedKeys() {
            return List.copyOf(get());
        }

        /** 已选作物键（仅 CROP 类别有效，已过滤失效项） */
        public List<String> selectedCropKeys() {
            List<String> result = new ArrayList<>();
            for (String key : get()) {
                if (index.cropByKey(key) != null) result.add(key);
            }
            return result;
        }
    }
}
