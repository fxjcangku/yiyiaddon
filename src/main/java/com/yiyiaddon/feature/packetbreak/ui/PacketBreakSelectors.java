package com.yiyiaddon.feature.packetbreak.ui;

import com.yiyiaddon.feature.mining.ui.MiningRegistry;
import com.yiyiaddon.feature.packetbreak.PacketInstantBreakModule;
import com.yiyiaddon.feature.packetbreak.config.PacketBreakTexts;
import com.yiyiaddon.ui.SelectionReceipt;
import com.yiyiaddon.ui.screen.SelectorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

import java.util.ArrayList;
import java.util.List;

/**
 * 发包秒破「目标方块」名单的唯一接线处：打开选择器、名单增删、清空、状态文字。
 *
 * <p><b>用的是既有通用选择器</b>（{@link SelectorScreen}），本类不新造任何控件
 * （第 126 / 130 / 153 条）：条目带图标（{@code Entry#drawIcon}）、分组可折叠
 * （{@code Entry#group()}）、左候选 / 右已选两栏多选、组头「全选 / 清空」整组一键。</p>
 *
 * <p><b>方块候选表复用，不留两份</b>（第 169 条）：取自自动挖矿的
 * {@link MiningRegistry#blockEntries()}（含空气，界面层按 {@link MiningRegistry#isAirBlock} 剔除）；
 * 与 {@code feature/vision/ui/VisionSelectors} 同一来源、同一过滤口径。</p>
 *
 * <p><b>时序</b>：候选表的构建会访问方块注册表，只在「打开选择器」或「读状态文字」时惰性构建并缓存，
 * 不在模块构造或初始化阶段调用（第 180 条）。</p>
 */
public final class PacketBreakSelectors {

    /** 方块候选缓存（注册表运行期不变，构建一次即可；空气已剔除） */
    private static List<SelectorScreen.Entry> blockEntries;

    private PacketBreakSelectors() {
    }

    /** 打开目标方块选择器（左栏候选 / 右栏已选，多选） */
    public static void openBlockSelector(Screen parent, PacketInstantBreakModule module) {
        Minecraft client = Minecraft.getInstance();
        if (client == null || module == null) return;
        List<String> selected = module.settings().targetBlocks;
        client.gui.setScreen(new SelectorScreen(PacketBreakTexts.SELECT_TITLE, parent, blockCandidates(),
            () -> new ArrayList<>(selected),
            key -> change(module, selected, key, true),
            key -> change(module, selected, key, false)));
    }

    /** 一键清空（名单为空时无动作，语义同透视 / 杀戮光环） */
    public static void clearBlockTargets(PacketInstantBreakModule module) {
        if (module == null) return;
        List<String> targets = module.settings().targetBlocks;
        // 条数取清空前的真实 size：面板内回执要写「已清空 N 项」，空名单交给回执自报「本来就是空的」
        int count = targets.size();
        if (count > 0) {
            targets.clear();
            module.persistSettings();
        }
        SelectionReceipt.cleared(count);
    }

    /** 名单状态：未选 → {@code 未选择（共 N 项）}；已选 → {@code 已选 N / M 项}（与其它选择器同一写法） */
    public static String blockStatusText(PacketInstantBreakModule module) {
        if (module == null) return "";
        List<String> selected = module.settings().targetBlocks;
        int total = blockCandidates().size();
        if (selected.isEmpty()) return "未选择（共 " + total + " 项）";
        return "已选 " + selected.size() + " / " + total + " 项";
    }

    /** 名单增删：有实际变化才落盘（无变化不写文件） */
    private static void change(PacketInstantBreakModule module, List<String> target, String key, boolean add) {
        boolean changed = add ? !target.contains(key) && target.add(key) : target.remove(key);
        if (!changed) return;
        module.persistSettings();
    }

    /** 方块候选：全方块去掉空气（空气是「未选择」的占位项，不是可挖目标） */
    private static List<SelectorScreen.Entry> blockCandidates() {
        if (blockEntries == null) {
            blockEntries = MiningRegistry.filter(MiningRegistry.blockEntries(),
                id -> !MiningRegistry.isAirBlock(id));
        }
        return blockEntries;
    }
}
