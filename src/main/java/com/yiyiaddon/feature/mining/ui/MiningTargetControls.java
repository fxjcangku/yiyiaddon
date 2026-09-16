package com.yiyiaddon.feature.mining.ui;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.model.LootMode;
import com.yiyiaddon.ui.screen.SelectorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 「目标选择 / 物品管理」两组的共享控制层。
 *
 * <p>配置页 {@code AutoMinerPage}（{@code buildTargetGroup} / {@code buildItemsGroup}）与控制台
 * 「目标选择」页 {@code MiningTargetPage} 是同一份逻辑的两套壳：设置名、说明文案、状态文字口径、
 * 候选剔除空气的表达式、↻ 的清空语义、采集模式切换后的失效与同步——原本各写一份，改一处漏一处。
 * 这里把<b>数据与控制</b>收拢到一处，两个页面只保留各自的「行容器」构造（{@code ListRow} /
 * {@code ConsoleRow}），不再持有任何状态。</p>
 *
 * <p>两块页面共用同一份 {@link MiningSettings}，写回后立即
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
     */
    public void pickLootMode(int index) {
        if (index < 0 || index >= LootMode.values().length) return;
        module.settings().lootMode = LootMode.values()[index];
        overworldOreTotal = -1;
        netherOreTotal = -1;
        module.syncTargetsOnModeSwitch();
        module.persistSettings();
        if (afterModeSwitch != null) afterModeSwitch.run();
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

    public void openFoodSelector(String title) {
        openListSelector(title, module.settings().foodWhitelist,
            MiningRegistry::foodEntries, false);
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
     * 单值设置因此表现为「左栏点加号即替换、右栏点减号即回到未选择」，候选里不需要「空气」占位。</p>
     */
    public void openOreSelector(String title, boolean nether) {
        // 候选 = 当前模式的矿石产物，并剔除空气（模块判定层为对齐旧 filter 会放行空气）
        List<SelectorScreen.Entry> entries = MiningRegistry.filter(MiningRegistry.itemEntries(),
            key -> !MiningRegistry.isAirItem(key) && module.isOreTargetCandidate(key, nether));
        openScreen(new SelectorScreen(title, currentScreen(), entries,
            () -> selectedOreTarget(nether),
            key -> setOreTarget(nether, key),
            key -> setOreTarget(nether, "")));
    }

    /** 普通方块选择器：常规模式（左加右减），候选为全部方块（不含空气） */
    public void openBlockSelector(String title) {
        openScreen(new SelectorScreen(title, currentScreen(),
            MiningRegistry.filter(MiningRegistry.blockEntries(), key -> !MiningRegistry.isAirBlock(key)),
            this::selectedBlockTarget,
            this::setBlockTarget,
            key -> setBlockTarget("")));
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
