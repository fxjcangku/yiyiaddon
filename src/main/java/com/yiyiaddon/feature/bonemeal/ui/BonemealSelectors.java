package com.yiyiaddon.feature.bonemeal.ui;

import com.yiyiaddon.feature.bonemeal.AutoBoneMealModule;
import com.yiyiaddon.feature.bonemeal.config.BonemealTexts;
import com.yiyiaddon.feature.bonemeal.config.TargetList;
import com.yiyiaddon.feature.mining.ui.MiningRegistry;
import com.yiyiaddon.ui.SelectionReceipt;
import com.yiyiaddon.ui.screen.SelectorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动骨粉五组「目标方块」名单的唯一接线处：打开选择器、增删、清空、状态文字。
 *
 * <p><b>用的是既有通用选择器</b>（{@link SelectorScreen}），本类不新造任何控件
 * （第 126 / 130 / 153 条）：条目带图标（{@code Entry#drawIcon}）、分组可折叠
 * （{@code Entry#group()}）、左候选 / 右已选两栏多选、组头「全选 / 清空」整组一键。</p>
 *
 * <p><b>候选表复用，不留两份</b>（第 169 条）：方块候选取自自动挖矿的
 * {@link MiningRegistry#blockEntries()}，与发包秒破 / 透视同一来源、同一过滤口径。</p>
 *
 * <p><b>旧 filter 的等价物</b>：旧项目 5 个 {@code BlockListSetting} 都带
 * {@code filter(block -> block instanceof BonemealableBlock)}，即候选窗口<b>只列可催熟方块</b>
 * （旧默认值里 {@code 海带} 这类不可催熟的项因此不出现在候选里，但仍留在默认名单中）。
 * 本类在候选表上做同一判据的等价过滤（空气天然被排除）。</p>
 *
 * <p><b>时序</b>：候选表的构建会访问方块注册表，只在「打开选择器」或「读状态文字」时惰性构建并缓存，
 * 不在模块构造或初始化阶段调用（第 180 条）。</p>
 */
public final class BonemealSelectors {

    /** 方块候选缓存（注册表运行期不变，构建一次即可；已按旧 filter 剔除不可催熟方块） */
    private static List<SelectorScreen.Entry> blockCandidates;

    private BonemealSelectors() {
    }

    /** 打开某一组目标方块的选择器（左栏候选 / 右栏已选，多选） */
    public static void openBlockSelector(Screen parent, AutoBoneMealModule module, TargetList group) {
        Minecraft client = Minecraft.getInstance();
        if (client == null || module == null || group == null) return;
        List<String> selected = group.of(module.settings());
        client.setScreen(new SelectorScreen(BonemealTexts.selectTitle(group.label()), parent, blockCandidates(),
            () -> new ArrayList<>(selected),
            key -> change(module, selected, key, true),
            key -> change(module, selected, key, false)));
    }

    /** 一键清空某一组（名单为空时无动作，语义同发包秒破 / 透视） */
    public static void clearTargets(AutoBoneMealModule module, TargetList group) {
        if (module == null || group == null) return;
        List<String> targets = group.of(module.settings());
        // 条数取清空前的真实 size：面板内回执要写「已清空 N 项」，空名单交给回执自报「本来就是空的」
        int count = targets.size();
        if (count > 0) {
            targets.clear();
            module.persistSettings();
        }
        SelectionReceipt.cleared(count);
    }

    /** 名单状态：未选 → {@code 未选择（共 N 项）}；已选 → {@code 已选 N / M 项}（与其它选择器同一写法） */
    public static String statusText(AutoBoneMealModule module, TargetList group) {
        if (module == null || group == null) return "";
        List<String> selected = group.of(module.settings());
        int total = blockCandidates().size();
        if (selected.isEmpty()) return "未选择（共 " + total + " 项）";
        return "已选 " + selected.size() + " / " + total + " 项";
    }

    /** 名单增删：有实际变化才落盘（无变化不写文件） */
    private static void change(AutoBoneMealModule module, List<String> target, String key, boolean add) {
        boolean changed = add ? !target.contains(key) && target.add(key) : target.remove(key);
        if (!changed) return;
        module.persistSettings();
    }

    /** 方块候选：全方块里只留可催熟方块（旧 filter 的等价物；空气因此天然被排除） */
    private static List<SelectorScreen.Entry> blockCandidates() {
        if (blockCandidates == null) {
            blockCandidates = MiningRegistry.filter(MiningRegistry.blockEntries(),
                BonemealSelectors::isBonemealable);
        }
        return blockCandidates;
    }

    /** 该登记 ID 对应的方块是否可催熟（旧 {@code filter(block -> block instanceof BonemealableBlock)}） */
    private static boolean isBonemealable(String blockId) {
        Block block = MiningRegistry.blockOf(blockId);
        return block instanceof BonemealableBlock;
    }
}
