package com.yiyiaddon.feature.stardew.selector;

import com.yiyiaddon.feature.stardew.config.StardewSettings;
import com.yiyiaddon.feature.stardew.profile.StardewResourceIndex;
import com.yiyiaddon.feature.stardew.profile.StardewToolDefinition;

import java.util.ArrayList;
import java.util.List;

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

    public StardewSelectionBinding(StardewResourceIndex index, StardewSettings settings) {
        this.index = index;
        // 六类选择器（数据源：资源包扫描 + ID 配置双源合并）
        crop = new Selection(StardewSelectorCategory.CROP, index, settings.selectedCropKeys);
        pot = new Selection(StardewSelectorCategory.POT, index, settings.selectedPotKeys);
        fertilizer = new Selection(StardewSelectorCategory.FERTILIZER, index, settings.selectedFertilizerKeys);
        potion = new Selection(StardewSelectorCategory.POTION, index, settings.selectedPotionKeys);
        can = new Selection(StardewSelectorCategory.WATERING_CAN, index, settings.selectedCanKeys);
        sprinkler = new Selection(StardewSelectorCategory.SPRINKLER, index, settings.selectedSprinklerKeys);
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

    /**
     * 六类选择的数据入口（界面壳使用）。
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
        };
    }

    /** 选择集合按服务器隔离：切服后这里会把 A 服的选择换成 B 服自己的 */
    public void bindServer(String serverKey) {
        crop.bindServer(serverKey);
        pot.bindServer(serverKey);
        fertilizer.bindServer(serverKey);
        potion.bindServer(serverKey);
        can.bindServer(serverKey);
        sprinkler.bindServer(serverKey);
    }

    /** 清理六类选择器里已不在当前索引中的失效键，并写回当前服务器档案 */
    public void pruneAndPersist() {
        if (index.isEmpty()) return;
        crop.pruneInvalid();
        pot.pruneInvalid();
        fertilizer.pruneInvalid();
        potion.pruneInvalid();
        can.pruneInvalid();
        sprinkler.pruneInvalid();
        crop.persist();
        pot.persist();
        fertilizer.persist();
        potion.persist();
        can.persist();
        sprinkler.persist();
        // 旧实现的 refreshCount() 六行是纯 GUI 计数标签刷新，随界面壳由界面批处理接入，此处不再搬运
    }

    /**
     * 六类选择器的数据与业务：内存镜像 + 按 ServerKey 隔离落盘。
     *
     * <p>旧项目这里是 Meteor 设置子类 {@code StardewTargetSetting}（含设置控件与计数标签）；
     * 界面壳由界面批处理另行接入，本类只保留数据语义：{@code bindServer / persist /
     * pruneInvalid / selectedKeys / selectedCropKeys}，逐字对应旧实现。</p>
     */
    public static final class Selection {

        private final StardewSelectorCategory category;
        private final StardewResourceIndex index;
        private final List<String> keys;
        private String boundServerKey;

        private Selection(StardewSelectorCategory category, StardewResourceIndex index, List<String> keys) {
            this.category = category;
            this.index = index;
            this.keys = keys;
        }

        private List<String> get() {
            return keys;
        }

        private void set(List<String> values) {
            keys.clear();
            keys.addAll(values);
        }

        private void bindServer(String serverKey) {
            if (serverKey == null || serverKey.isBlank()) return;
            if (serverKey.equals(boundServerKey)) return;
            boundServerKey = serverKey;
            set(StardewSelectionStore.load(serverKey).getOrDefault(category.name(), new ArrayList<>()));
        }

        /** 把当前选择写回已绑定服务器的档案（未绑定服务器时不做任何事） */
        public void persist() {
            if (boundServerKey == null) return;
            StardewSelectionStore.save(boundServerKey, category, get());
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
