package com.yiyiaddon.feature.vision.ui;

import com.yiyiaddon.feature.combat.ui.console.KillAuraTargetingPage;
import com.yiyiaddon.feature.mining.ui.MiningRegistry;
import com.yiyiaddon.feature.vision.VisionModule;
import com.yiyiaddon.feature.vision.config.VisionTexts;
import com.yiyiaddon.platform.identity.EntityIdentifier;
import com.yiyiaddon.ui.screen.SelectorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypeIds;
import net.minecraft.world.entity.MobCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * 透视模块两个目标选择器的唯一接线处：打开、名单增删、清空、状态文字。
 *
 * <p><b>用的是既有通用选择器</b>（{@link SelectorScreen}），本类不新造任何控件（第 126 / 130 / 153 条）。
 * 用户 2026-09-18 要的六条能力全部由它提供：条目带图标（{@code Entry#drawIcon}）、分组可折叠
 * （{@code Entry#group()}）、左候选 / 右已选两栏、多选、组头「全选 / 清空」整组一键。</p>
 *
 * <p><b>方块候选表复用，不留两份</b>（第 169 条）：取自自动挖矿的
 * {@link MiningRegistry#blockEntries()}（含空气，界面层按 {@link MiningRegistry#isAirBlock} 剔除）。</p>
 *
 * <p><b>实体候选表：复用杀戮光环那份，但按透视的用途过滤</b>（用户 2026-09-18 拍板 A 方案：
 * 「我不想要宠物蛋」）。杀戮光环的候选是「能打的都列」，其中船 / 竹筏 / 矿车 / 盔甲架 / 展示框 / 画 /
 * TNT / 掉落物 / 投射物 / 标记这些<b>非生物</b>没有实体模型渲染图，只能回退成刷怪蛋或静态物品图标 ——
 * 透视要的是「能看见的生物」，因此这里只保留<b>生物</b>（{@link MobCategory} 非 {@code MISC}）
 * 与<b>玩家</b>（玩家在原版属于 {@code MISC}，但透视玩家是本模块的常见诉求，单列保留）。
 * 过滤只发生在本模块的候选视图上：杀戮光环的列表与图标一行未改，也没有复制第二份条目实现。</p>
 *
 * <p><b>时序</b>：候选表的构建会访问实体注册表，只在「打开选择器」或「读状态文字」时惰性构建并缓存，
 * 不在模块构造或初始化阶段调用（第 180 条）。</p>
 */
public final class VisionSelectors {

    /** 方块候选缓存（注册表运行期不变，构建一次即可；空气已剔除） */
    private static List<SelectorScreen.Entry> blockEntries;

    /** 实体候选缓存（杀戮光环候选过滤掉非生物后的一份视图） */
    private static List<SelectorScreen.Entry> entityEntries;

    private VisionSelectors() {
    }

    // ── 打开选择器 ──

    /** 打开目标方块选择器（左栏候选 / 右栏已选，多选） */
    public static void openBlockSelector(Screen parent, VisionModule module) {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        List<String> selected = module.settings().blockTargets;
        client.gui.setScreen(new SelectorScreen(VisionTexts.SELECT_BLOCK_TITLE, parent,
            blockCandidates(),
            () -> new ArrayList<>(selected),
            key -> change(module, selected, key, true),
            key -> change(module, selected, key, false)));
    }

    /** 打开目标实体选择器（候选按生物分类分组） */
    public static void openEntitySelector(Screen parent, VisionModule module) {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        List<String> selected = module.settings().entityTargets;
        client.gui.setScreen(new SelectorScreen(VisionTexts.SELECT_ENTITY_TITLE, parent,
            entityCandidates(),
            () -> new ArrayList<>(selected),
            key -> change(module, selected, key, true),
            key -> change(module, selected, key, false)));
    }

    // ── 一键清空（名单为空时无动作，语义同杀戮光环 / 星露谷） ──

    public static void clearBlockTargets(VisionModule module) {
        if (module.settings().blockTargets.isEmpty()) return;
        module.settings().blockTargets.clear();
        module.persistSettings();
    }

    public static void clearEntityTargets(VisionModule module) {
        if (module.settings().entityTargets.isEmpty()) return;
        module.settings().entityTargets.clear();
        module.persistSettings();
    }

    // ── 状态文字（写法逐字照杀戮光环的目标实体行） ──

    /** 方块名单状态：未选 → {@code 未选择（共 N 项）}；已选 → {@code 已选 N / M 项} */
    public static String blockStatusText(VisionModule module) {
        return statusText(module.settings().blockTargets, blockCandidates().size());
    }

    /** 实体名单状态：同上（总数用过滤后的候选数，与选择器里看到的行数一致） */
    public static String entityStatusText(VisionModule module) {
        return statusText(module.settings().entityTargets, entityCandidates().size());
    }

    private static String statusText(List<String> selected, int total) {
        if (selected.isEmpty()) return "未选择（共 " + total + " 项）";
        return "已选 " + selected.size() + " / " + total + " 项";
    }

    // ── 内部 ──

    /** 名单增删：有实际变化才落盘（无变化不写文件） */
    private static void change(VisionModule module, List<String> target, String key, boolean add) {
        boolean changed = add ? !target.contains(key) && target.add(key) : target.remove(key);
        if (!changed) return;
        module.persistSettings();
    }

    /** 方块候选：全方块去掉空气（空气不是可透视的目标，且它是「未选择」的占位项） */
    private static List<SelectorScreen.Entry> blockCandidates() {
        if (blockEntries == null) {
            blockEntries = MiningRegistry.filter(MiningRegistry.blockEntries(),
                id -> !MiningRegistry.isAirBlock(id));
        }
        return blockEntries;
    }

    /** 实体候选：杀戮光环那份候选去掉非生物（保留全部生物 + 玩家），见类注释 */
    private static List<SelectorScreen.Entry> entityCandidates() {
        if (entityEntries == null) {
            List<SelectorScreen.Entry> kept = new ArrayList<>();
            for (SelectorScreen.Entry entry : KillAuraTargetingPage.entityCandidates()) {
                if (isLivingOrPlayer(entry.key())) kept.add(entry);
            }
            entityEntries = List.copyOf(kept);
        }
        return entityEntries;
    }

    /**
     * 是否保留该实体：生物（{@link MobCategory} 非 {@code MISC}）一律保留，玩家单独保留。
     *
     * <p>认不出的登记 ID 不保留（不猜，宁缺勿错）。</p>
     */
    private static boolean isLivingOrPlayer(String typeId) {
        Identifier id = Identifier.tryParse(typeId);
        if (id == null) return false;
        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.getValue(id);
        if (type == null) return false;
        if (type == EntityIdentifier.typeOf(EntityTypeIds.PLAYER)) return true;
        return type.getCategory() != MobCategory.MISC;
    }
}
