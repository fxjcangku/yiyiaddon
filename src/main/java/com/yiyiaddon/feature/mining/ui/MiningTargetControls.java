package com.yiyiaddon.feature.mining.ui;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.model.LootMode;
import com.yiyiaddon.ui.render.TooltipLayer;
import com.yiyiaddon.ui.screen.SelectorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 「目标选择 / 物品管理」两组的共享控制层。
 *
 * <p>唯一使用方是控制台「目标选择」页 {@code MiningTargetPage}（{@code 目标选择} 与
 * {@code 物品管理} 两组都在那一页）：设置名、说明文案、状态文字口径、候选剔除空气的表达式、
 * ↻ 的清空语义、采集模式切换后的失效与同步全部收拢在这里，页面只保留「行容器」构造
 * （{@code ConsoleRow}），不持有任何状态。</p>
 *
 * <p>（历史：本类抽出前，配置页 {@code AutoMinerPage} 与控制台各写一份同逻辑的行，改一处漏一处；
 * 2026-09-16 配置页精简为只留入口与点位卡片，配置页那份已随之下线。）</p>
 *
 * <p>写的是同一份 {@link MiningSettings}（与控制台其余页、模块本身共用），写回后立即
 * {@link AutoMinerModule#persistSettings()}。</p>
 */
public final class MiningTargetControls {

    /** 三个名单的候选总数：{@link #refreshTotals()} 时算一次（页面每次重建都重算，与点位状态同一口径） */
    private int keepCandidateTotal;
    private int foodCandidateTotal;
    private int placeCandidateTotal;
    /** 普通方块候选总数：与搭路方块白名单同一份候选（全部方块，排除空气） */
    private int blockCandidateTotal;
    /** 单值目标的候选总数：懒算，采集模式切换时失效（候选集合随模式变） */
    private int overworldOreTotal = -1;
    private int netherOreTotal = -1;

    private final AutoMinerModule module;
    /** 采集模式切换后的额外动作（控制台页用它触发整页重建，让「共 N 项」立刻刷新）；可为 {@code null} */
    private final Runnable afterModeSwitch;

    public MiningTargetControls(AutoMinerModule module, Runnable afterModeSwitch) {
        this.module = module;
        this.afterModeSwitch = afterModeSwitch;
    }

    /**
     * 重算三个名单与普通方块的候选总数。
     *
     * <p>与各选择器实际列出的候选完全同源（同一表达式），页面构建时调用一次。</p>
     */
    public void refreshTotals() {
        keepCandidateTotal = MiningRegistry.filter(MiningRegistry.itemEntries(), key -> !MiningRegistry.isAirItem(key)).size();
        foodCandidateTotal = MiningRegistry.foodEntries().size();
        placeCandidateTotal = MiningRegistry.filter(MiningRegistry.blockEntries(), key -> !MiningRegistry.isAirBlock(key)).size();
        blockCandidateTotal = placeCandidateTotal;
    }

    // ── 状态文字 ──

    /** 保留白名单行状态 */
    public String keepStatus() {
        return listStatusText(module.settings().keepWhitelist, keepCandidateTotal);
    }

    /** 食物白名单行状态 */
    public String foodStatus() {
        return listStatusText(module.settings().foodWhitelist, foodCandidateTotal);
    }

    /** 搭路方块白名单行状态 */
    public String placeStatus() {
        return listStatusText(module.settings().placeBlocks, placeCandidateTotal);
    }

    /**
     * 名单状态文字（逐字照星露谷控制台页）：未选 → {@code 未选择（共 N 项）}；
     * 已选 → {@code 已选 N / M 项}。
     */
    private static String listStatusText(List<String> target, int total) {
        if (target.isEmpty()) return "未选择（共 " + total + " 项）";
        return "已选 " + target.size() + " / " + total + " 项";
    }

    /**
     * 单值目标的状态文字：未选时同星露谷口径（空值不再显示成「空气」）；已选时显示产物名
     * （旧项目该行本来就是显示当前产物）。
     */
    public String oreStatus(boolean nether) {
        String current = oreTarget(nether);
        if (current == null || current.isBlank()) return "未选择（共 " + oreCandidateTotal(nether) + " 项）";
        return MiningRegistry.itemDisplayName(current);
    }

    /** 普通方块行状态 */
    public String blockStatus() {
        String current = module.settings().blockTarget;
        if (current == null || current.isBlank()) return "未选择（共 " + blockCandidateTotal + " 项）";
        return MiningRegistry.blockDisplayName(current);
    }

    /** 矿石候选总数：与 {@link #openOreSelector} 同一表达式，懒算一次（模式切换时置 -1 失效） */
    private int oreCandidateTotal(boolean nether) {
        if (nether) {
            if (netherOreTotal < 0) netherOreTotal = countOreCandidates(true);
            return netherOreTotal;
        }
        if (overworldOreTotal < 0) overworldOreTotal = countOreCandidates(false);
        return overworldOreTotal;
    }

    private int countOreCandidates(boolean nether) {
        int total = 0;
        for (SelectorScreen.Entry entry : MiningRegistry.itemEntries()) {
            if (!MiningRegistry.isAirItem(entry.key()) && module.isOreTargetCandidate(entry.key(), nether)) total++;
        }
        return total;
    }

    // ── 清空（↻ 语义同星露谷：空则静默 return） ──

    public void clearOreTarget(boolean nether) {
        String current = oreTarget(nether);
        if (current == null || current.isBlank()) return;
        setOreTarget(nether, "");
    }

    public void clearBlockTarget() {
        if (module.settings().blockTarget == null || module.settings().blockTarget.isBlank()) return;
        setBlockTarget("");
    }

    /** 清空名单；搭路方块清空同样要下发 Baritone */
    public void clearKeepList() {
        clearList(module.settings().keepWhitelist, false);
    }

    public void clearFoodList() {
        clearList(module.settings().foodWhitelist, false);
    }

    public void clearPlaceList() {
        clearList(module.settings().placeBlocks, true);
    }

    private void clearList(List<String> target, boolean blocks) {
        if (target.isEmpty()) return;
        target.clear();
        module.persistSettings();
        if (blocks) module.getBaritone().updatePlaceBlocks(MiningRegistry.blockList(target));
    }

    // ── 设置写回 ──

    /**
     * 切换采集模式：写回 → 落盘 → 同步已选目标；候选集合随模式变，行上的「共 N 项」跟着失效。
     *
     * <p>目标候选集合不在切换时缓存：每次打开选择器都按当前模式重算。控制台页额外触发整页重建，
     * 让「共 N 项」与新同步出来的目标立刻反映在行上。</p>
     *
     * <p>若自用模式手选的「出售物品」在新模式下已经掉不出来（如精准采集挖石头掉石头、卖不到圆石），
     * {@code syncTargetsOnModeSwitch} 会顺手把它纠成会掉的那一件并回一句文案，这里弹渐入渐出提示框
     * 让用户看得见（扫过场就懂自己设置被改了，也可以再改回去）。</p>
     */
    public void pickLootMode(int index) {
        if (index < 0 || index >= LootMode.values().length) return;
        module.settings().lootMode = LootMode.values()[index];
        overworldOreTotal = -1;
        netherOreTotal = -1;
        String notice = module.syncTargetsOnModeSwitch();
        module.persistSettings();
        if (afterModeSwitch != null) afterModeSwitch.run();
        if (notice != null) TooltipLayer.notify(notice);
    }

    /** 选中矿石产物；空串 = 未选择（旧 {@code ItemSetting} 的 {@code Items.AIR} 默认值语义） */
    private void setOreTarget(boolean nether, String itemId) {
        String stored = itemId == null || MiningRegistry.itemOf(itemId) == null ? "" : itemId;
        if (nether) {
            module.settings().netherOreTarget = stored;
        } else {
            module.settings().overworldOreTarget = stored;
        }
        module.persistSettings();
    }

    /** 普通方块写回；空串 = 未选择（旧 {@code BlockSetting} 的 {@code Blocks.AIR} 默认值语义） */
    private void setBlockTarget(String blockId) {
        module.settings().blockTarget = blockId == null || MiningRegistry.blockOf(blockId) == null ? "" : blockId;
        module.persistSettings();
    }

    /** 名单增删：有实际变化才落盘；搭路方块名单额外下发 Baritone（旧 {@code onChanged → updatePlaceBlocks}） */
    private void changeList(List<String> target, String key, boolean add, boolean blocks) {
        boolean changed = add ? !target.contains(key) && target.add(key) : target.remove(key);
        if (!changed) return;
        module.persistSettings();
        if (blocks) module.getBaritone().updatePlaceBlocks(MiningRegistry.blockList(target));
    }

    private String oreTarget(boolean nether) {
        MiningSettings settings = module.settings();
        return nether ? settings.netherOreTarget : settings.overworldOreTarget;
    }

    // ── 选择器 ──

    /**
     * 打开名单选择器：双栏，加一个减一个即时写回。
     *
     * <p>候选表必须是 {@link Supplier}：物品与方块的显示名要读语言文件，点击时再取（配置页在客户端
     * 初始化阶段就会构造，提前取会把翻译键缓存进静态表）。</p>
     */
    public void openKeepSelector(String title) {
        openListSelector(title, module.settings().keepWhitelist,
            () -> MiningRegistry.filter(MiningRegistry.itemEntries(), key -> !MiningRegistry.isAirItem(key)), false);
    }

    /**
     * 食物白名单选择器：<b>只能选一个</b>。
     *
     * <p><b>口径</b>（用户 2026-09-19：「为什么能选两个食物？只能选一个目标选择器 如果选两个弹个
     * 动态小框提示玩家」）：口粮同时只会用上一种 —— 副手常驻的那格就是它、吃完补同一种，名单里多出来的
     * 第二项只会让「吃哪一种」多一层按分数挑。因此第二条被拦下并在窗口里弹一句顶部提示，原选中项不动；
     * 要换，先在右栏点掉原来那条（或组头「清空」）。</p>
     *
     * <p>打开时把历史配置收敛成一条（早先允许选多个）：保留第一项、其余移除并提示一句，
     * 免得行上「已选 2 / 44 项」与单选口径打架。</p>
     */
    public void openFoodSelector(String title) {
        List<String> target = module.settings().foodWhitelist;
        boolean trimmed = trimFoodList(target);
        if (trimmed) module.persistSettings();
        openScreen(new SelectorScreen(title, currentScreen(), MiningRegistry.foodEntries(),
            () -> new ArrayList<>(target),
            key -> changeList(target, key, true, false),
            key -> changeList(target, key, false, false))
            .addGuard(key -> foodGuardReason(target, key)));
        if (trimmed) {
            TooltipLayer.notify("§e食物白名单只能选一个 §8▸ 已保留「§f"
                    + MiningRegistry.itemDisplayName(target.get(0)) + "§8」");
        }
    }

    /** 食物白名单的加入准入：已选着别的食物时拒绝，理由里带上当前那一条。 */
    private static String foodGuardReason(List<String> target, String key) {
        if (target.isEmpty() || target.contains(key)) return null;
        return "§e食物白名单只能选一个 §8▸ 先移除「§f"
                + MiningRegistry.itemDisplayName(target.get(0)) + "§8」";
    }

    /** 旧配置可能选了多个：只留第一条；发生过裁剪返回 true。 */
    private static boolean trimFoodList(List<String> target) {
        if (target.size() <= 1) return false;
        while (target.size() > 1) target.remove(target.size() - 1);
        return true;
    }

    public void openPlaceSelector(String title) {
        openListSelector(title, module.settings().placeBlocks,
            () -> MiningRegistry.filter(MiningRegistry.blockEntries(), key -> !MiningRegistry.isAirBlock(key)), true);
    }

    private void openListSelector(String title, List<String> target,
                                  Supplier<List<SelectorScreen.Entry>> candidates, boolean blocks) {
        openScreen(new SelectorScreen(title, currentScreen(), candidates.get(),
            () -> new ArrayList<>(target),
            key -> changeList(target, key, true, blocks),
            key -> changeList(target, key, false, blocks)));
    }

    /**
     * 矿石产物选择器：窗口标题 = 设置名逐字，候选按当前采集模式过滤。
     *
     * <p>用<b>常规模式</b>（左栏「+」加入 / 右栏「-」移除），与星露谷、自动箱子同一套可加减的形态。
     * 单值设置因此表现为「已选一条」，候选里不需要「空气」占位。</p>
     *
     * <p><b>只能选一个</b>（用户 2026-09-19：「所有选择器 如果只能是单选的 都要加上弹窗」）：
     * 选着钻石时再点第二条会被拦下，并在窗口顶部弹一句提示，原选中项不动；要换先点掉右栏那条。</p>
     */
    public void openOreSelector(String title, boolean nether) {
        // 候选 = 当前模式的矿石产物，并剔除空气（模块判定层为对齐旧 filter 会放行空气）
        List<SelectorScreen.Entry> entries = MiningRegistry.filter(MiningRegistry.itemEntries(),
            key -> !MiningRegistry.isAirItem(key) && module.isOreTargetCandidate(key, nether));
        openScreen(new SelectorScreen(title, currentScreen(), entries,
            () -> selectedOreTarget(nether),
            key -> setOreTarget(nether, key),
            key -> setOreTarget(nether, ""))
            .addGuard(key -> oreGuardReason(title, nether, key)));
    }

    /** 矿石产物的加入准入（单值）：已选着别的产物时拒绝，理由里带上当前那一条。 */
    private String oreGuardReason(String title, boolean nether, String key) {
        String current = oreTarget(nether);
        if (current == null || current.isBlank() || current.equals(key)) return null;
        return "§e" + title + "只能选一个 §8▸ 先移除「§f"
                + MiningRegistry.itemDisplayName(current) + "§8」";
    }

    /**
     * 普通方块选择器：常规模式（左加右减），候选为全部方块（不含空气）。
     *
     * <p>同样是<b>只能选一个</b>的单值设置，加入准入与矿石一致（见 {@link #openOreSelector}）。</p>
     */
    public void openBlockSelector(String title) {
        openScreen(new SelectorScreen(title, currentScreen(),
            MiningRegistry.filter(MiningRegistry.blockEntries(), key -> !MiningRegistry.isAirBlock(key)),
            this::selectedBlockTarget,
            this::setBlockTarget,
            key -> setBlockTarget(""))
            .addGuard(key -> blockGuardReason(title, key)));
    }

    /** 普通方块的加入准入（单值）：已选着别的方块时拒绝，理由里带上当前那一条。 */
    private String blockGuardReason(String title, String key) {
        String current = module.settings().blockTarget;
        if (current == null || current.isBlank() || current.equals(key)) return null;
        return "§e" + title + "只能选一个 §8▸ 先移除「§f"
                + MiningRegistry.blockDisplayName(current) + "§8」";
    }

    /** 已选矿石产物（单值 → 至多一项；未选择返回空列表） */
    private List<String> selectedOreTarget(boolean nether) {
        String current = oreTarget(nether);
        return current == null || current.isBlank() ? List.of() : List.of(current);
    }

    /** 已选普通方块（单值 → 至多一项；未选择返回空列表） */
    private List<String> selectedBlockTarget() {
        String current = module.settings().blockTarget;
        return current == null || current.isBlank() ? List.of() : List.of(current);
    }

    private void openScreen(Screen screen) {
        Minecraft client = Minecraft.getInstance();
        if (client == null || screen == null) return;
        client.setScreen(screen);
    }

    /** 上级屏幕（选择器关闭后回到来源页面） */
    private static Screen currentScreen() {
        Minecraft client = Minecraft.getInstance();
        return client == null ? null : client.screen;
    }
}
