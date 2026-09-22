package com.yiyiaddon.feature.bonemeal.ui;

import com.yiyiaddon.feature.bonemeal.AutoBoneMealModule;
import com.yiyiaddon.feature.bonemeal.config.BonemealTexts;
import com.yiyiaddon.feature.bonemeal.config.TargetList;
import com.yiyiaddon.feature.mining.ui.MiningRegistry;
import com.yiyiaddon.ui.SelectionReceipt;
import com.yiyiaddon.ui.screen.SelectorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 自动骨粉五组「目标方块」名单的唯一接线处：打开选择器、增删、清空、状态文字。
 *
 * <p><b>用的是既有通用选择器</b>（{@link SelectorScreen}），本类不新造任何控件
 * （第 126 / 130 / 153 条）：条目带图标（{@code Entry#drawIcon}）、分组可折叠
 * （{@code Entry#group()}）、左候选 / 右已选两栏多选、组头「全选 / 清空」整组一键。</p>
 *
 * <p><b>候选表复用，不留两份</b>（第 169 条）：方块候选取自自动挖矿的
 * {@link MiningRegistry#blockEntries()}，与发包秒破 / 透视同一来源、同一份显示名与图标口径。</p>
 *
 * <p><b>每组只列自己那类</b>（用户 2026-09-22 裁定：「按类拆开，每组只列自己那类」）：
 * 「农作物」的选择器只列农作物、「树苗」只列树苗…… <b>分类依据 = {@link TargetList#members()}</b>
 * （旧项目那份默认名单，11 / 9 / 8 / 4 / 6 项）—— 这一层数据本来就是「哪些方块属于这类」的唯一定义，
 * 不再另建分类表（第 169 条）。注意它只是<b>候选范围</b>：出厂五个名单都是空的，勾哪些由玩家自己点
 * （用户 2026-09-22：「默认自己选择 不要帮我全选」）。</p>
 *
 * <p><b>为什么不再叠一层「只列可催熟方块」</b>：旧项目 5 个 {@code BlockListSetting} 共用同一个
 * {@code filter(block -&gt; block instanceof BonemealableBlock)}，候选池因此五组相同（73 项），
 * 这也是用户 2026-09-22 反馈「五组都是 73 种」的由来。改成按类之后这层过滤失去意义，且留着有害：
 * 旧默认名单里的 {@code 海带} 不是 {@code BonemealableBlock}，留着过滤会让它「明明在已选里、
 * 却不在候选里」，出现「已选 6 / 5 项」这种自相矛盾的数字。判定层本来就会用
 * {@code isFertilizable} 再筛一遍，候选层不必重复。</p>
 *
 * <p><b>时序</b>：候选表的构建会访问方块注册表，只在「打开选择器」或「读状态文字」时惰性构建并缓存，
 * 不在模块构造或初始化阶段调用（第 180 条）。</p>
 */
public final class BonemealSelectors {

    /** 五组「本类方块」的登记 ID 集合（= 该组的出厂默认名单；注册表运行期不变，一组构建一次） */
    private static final Map<TargetList, Set<String>> CATEGORY_KEYS = new EnumMap<>(TargetList.class);

    private BonemealSelectors() {
    }

    /** 打开某一组目标方块的选择器（左栏候选 / 右栏已选，多选） */
    public static void openBlockSelector(Screen parent, AutoBoneMealModule module, TargetList group) {
        Minecraft client = Minecraft.getInstance();
        if (client == null || module == null || group == null) return;
        List<String> selected = group.of(module.settings());
        client.gui.setScreen(new SelectorScreen(BonemealTexts.selectTitle(group.label()), parent,
            candidates(group, selected),
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
        int total = candidates(group, selected).size();
        if (selected.isEmpty()) return "未选择（共 " + total + " 项）";
        return "已选 " + selected.size() + " / " + total + " 项";
    }

    /** 名单增删：有实际变化才落盘（无变化不写文件） */
    private static void change(AutoBoneMealModule module, List<String> target, String key, boolean add) {
        boolean changed = add ? !target.contains(key) && target.add(key) : target.remove(key);
        if (!changed) return;
        module.persistSettings();
    }

    /**
     * 某一组的候选：**只列本类方块**，外加当前已选里<b>不属于本类</b>的项。
     *
     * <p>为什么要把「不属于本类的已选」带上：右栏「已选」是拿候选表逐条渲染的
     * （{@code SelectorScreen#buildColumn}），候选表里没有的键就既不显示也删不掉，
     * 而模块判定层仍然按它催熟 —— 那是玩家看得见却管不着的幽灵条目。
     * 这类键来自本版之前从共用候选池（73 项）选进去的旧配置，带上之后就能正常取消勾选。</p>
     *
     * @param group    五组之一
     * @param selected 该组当前已选（判定候选是否属于本类；{@code null} 视为空）
     */
    public static List<SelectorScreen.Entry> candidates(TargetList group, List<String> selected) {
        Set<String> keys = categoryKeys(group);
        Set<String> selectedKeys = selected == null || selected.isEmpty() ? Set.of() : new HashSet<>(selected);
        return MiningRegistry.filter(MiningRegistry.blockEntries(),
            id -> keys.contains(id) || selectedKeys.contains(id));
    }

    /** 该组本类方块的登记 ID 集合（= {@link TargetList#members()}），惰性构建一次 */
    private static Set<String> categoryKeys(TargetList group) {
        return CATEGORY_KEYS.computeIfAbsent(group, key -> new HashSet<>(key.members()));
    }
}
