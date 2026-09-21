package com.yiyiaddon.feature.admindetect.ui.console;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.admindetect.AdminDetectorModule;
import com.yiyiaddon.feature.admindetect.config.AdminDetectorSettings;
import com.yiyiaddon.feature.admindetect.config.AdminDetectorTexts;
import com.yiyiaddon.feature.admindetect.service.PlayerListProbe;
import com.yiyiaddon.feature.admindetect.ui.AdminDetectorConsoleScreen;
import com.yiyiaddon.ui.SelectionReceipt;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.console.ConsoleStateColumn;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.render.PlayerFaceCache;
import com.yiyiaddon.ui.screen.SelectorScreen;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingText;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.PlayerSkin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 管理员检测控制台「名单」页：旧 {@code sgList} 组 2 项。
 *
 * <p><b>名称逐字保留</b>（{@code 白名单（来了不退出）} / {@code 黑名单（来了就退出）}）；描述按用户
 * 2026-09-16 拍板改写，理由见 {@link AdminDetectorSettings} 类注释。</p>
 *
 * <p><b>承载控件改成交互式选择器</b>（用户原话「在线玩家列表 选择器我直接可以添加 不用我输入id 麻烦」）：
 * 复用项目通用件 {@link SelectorScreen}（左栏候选 + 右栏已选 + 加减按钮 + 搜索），候选由
 * {@link PlayerListProbe} 采集（Tab 在线名单 + 当前世界实体 + 名单里已离线的人），每行带头像。</p>
 */
public final class AdminListPage {

    /** 名单行的「点击选择」按钮（逐字照本项目其它控制台页） */
    private static final String SELECT_LABEL = "点击选择";

    /**
     * 状态列的下界：按该列可能出现的最长文案量出（用户 2026-09-16：「点击选择 按钮不对齐」）。
     *
     * <p>计数进到 999 项已远超玩家名单的现实上限，再长就由 {@link ConsoleStateColumn} 按实测值加宽，
     * 列宽本身不会回落。</p>
     */
    private static final String STATE_LONGEST = "已选 999 项";

    /** 候选来源说明（新增文案：解释「已离线」分组从哪来，否则玩家不懂名单里为什么有人标已离线） */
    private static final String CANDIDATE_NOTE =
        "§8候选来自 Tab 在线名单与当前世界实体；已不在线的名单成员会列在「§8已离线§8」组里，便于移除。";

    private final AdminDetectorConsoleScreen owner;
    private final AdminDetectorModule module;
    /** 两行共用的状态列宽度（见 {@link ConsoleStateColumn}：浮动会把「点击选择」顶得左右移动） */
    private final ConsoleStateColumn stateColumn = new ConsoleStateColumn(STATE_LONGEST);

    public AdminListPage(AdminDetectorConsoleScreen owner, AdminDetectorModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        AdminDetectorSettings settings = module.settings();

        stack.add(listRow(AdminDetectorTexts.NAME_WHITELIST, AdminDetectorTexts.DESC_WHITELIST,
            () -> statusText(settings.whitelist),
            () -> openSelector(true), () -> clear(settings.whitelist),
            () -> settings.whitelist.isEmpty(), "清空本行已选白名单"));

        stack.add(listRow(AdminDetectorTexts.NAME_BLACKLIST, AdminDetectorTexts.DESC_BLACKLIST,
            () -> statusText(settings.blacklist),
            () -> openSelector(false), () -> clear(settings.blacklist),
            () -> settings.blacklist.isEmpty(), "清空本行已选黑名单"));

        stack.add(new Note(owner, CANDIDATE_NOTE));
    }

    // ── 行构件 ──

    /**
     * 名单行（行样式照本项目其它控制台页）：名称 + 说明 …… [点击选择] [状态文字] [↻]。
     *
     * @param empty     ↻ 的空态判据；与 {@link #clear(List)} 读的是同一份名单
     * @param clearHint ↻ 的悬停说明（空态禁用时仍可悬停看到「清空什么」）
     */
    private CompactElement listRow(String title, String description, Supplier<String> status,
                                   Runnable open, Runnable reset,
                                   Supplier<Boolean> empty, String clearHint) {
        return new ConsoleRow(owner, () -> title, description, null, List.of(
            new Ctl(new Button(SELECT_LABEL, open)),
            // 列宽走两行共用的固定列（`已选 10 项` 与 `未选择` 各自量宽会把「点击选择」顶得左右浮动）；
            // ↻ 是最后一个控件，仍然贴行右边界，位置不变
            new Ctl(new SettingText(status, () -> stateColumn.widthOf(status)).alignLeft()),
            // 空态禁用：判据与 clear(List) 同源（同一份名单），逐帧求值见 IconButton#disabledWhen(Supplier)；
            // 空名单时按钮是禁用态，而不是「点了没反应」
            new Ctl(new IconButton(ConsoleMetrics.GLYPH_RESET, reset).disabledWhen(empty), clearHint)));
    }

    /** 名单状态文字：未选 → {@code 未选择}；已选 → {@code 已选 N 项} */
    private static String statusText(List<String> names) {
        if (names.isEmpty()) return "未选择";
        return "已选 " + names.size() + " 项";
    }

    // ── 选择器 ──

    /**
     * 打开名单选择器（常规模式：左栏「+」加入 / 右栏「-」移除）。
     *
     * @param whitelist true = 白名单，false = 黑名单
     */
    private void openSelector(boolean whitelist) {
        Minecraft client = owner.client();
        if (client == null) return;

        List<String> selected = whitelist ? module.settings().whitelist : module.settings().blacklist;
        List<SelectorScreen.Entry> entries = new ArrayList<>();
        for (PlayerListProbe.Candidate candidate : PlayerListProbe.candidates(selected)) {
            // 皮肤在这里解析一次并缓存在条目上：drawIcon 每帧都被调用，逐帧解析会白白重算
            entries.add(new PlayerEntry(candidate, PlayerListProbe.skin(candidate)));
        }

        client.setScreen(new SelectorScreen(
            whitelist ? AdminDetectorTexts.NAME_WHITELIST : AdminDetectorTexts.NAME_BLACKLIST,
            client.screen,
            entries,
            () -> new ArrayList<>(selected),
            key -> change(selected, key, true),
            key -> change(selected, key, false)));
    }

    /** 清空名单（↻ 语义同其它控制台页：空则静默 return） */
    private void clear(List<String> names) {
        if (names.isEmpty()) return;
        // 回执的条数必须是清空前的真实值（清空后再读只会是 0），因此先读再清；
        // 本页的加减由选择器自己发回执，行尾 ↻ 是选择器覆盖不到的那一个
        int count = names.size();
        names.clear();
        persist();
        SelectionReceipt.cleared(count);
    }

    /** 名单增删：有实际变化才落盘 */
    private void change(List<String> names, String key, boolean add) {
        boolean changed = add ? !names.contains(key) && names.add(key) : names.remove(key);
        if (!changed) return;
        persist();
    }

    /** 立即落盘（走 ModuleManager 的统一入口） */
    private void persist() {
        ModuleManager.saveSettings(module);
    }

    // ── 候选条目 ──

    /**
     * 玩家候选条目：名称作 key（名单按名字比较，与旧 {@code containsName} 同口径），
     * 分组按在场形态（在线 / 隐藏 / 已离线），图标为 2D 玩家头像（皮肤脸 + 帽子层）。
     */
    private record PlayerEntry(PlayerListProbe.Candidate candidate, PlayerSkin skin)
        implements SelectorScreen.Entry {

        @Override
        public String key() {
            return candidate.name();
        }

        @Override
        public String title() {
            return candidate.name();
        }

        @Override
        public String group() {
            return candidate.presence().groupTitle();
        }

        @Override
        public boolean drawIcon(Canvas canvas, float x, float y, float size) {
            return PlayerFaceCache.draw(canvas, skin, x, y, size);
        }
    }
}
