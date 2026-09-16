package com.yiyiaddon.ui.screen;

import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.component.ListRow;
import com.yiyiaddon.ui.component.SplitPanels;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingTextBox;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.gui.screens.Screen;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 通用双栏选择器窗口：左栏候选、右栏已选。
 *
 * <p><b>交互与文字照旧项目：</b>顶部搜索框（旧项目该窗无标签无提示）打开即输入过滤，匹配显示名与技术 ID、
 * 忽略大小写；左栏按分组排列未选项，组内为空时显示 {@code §8无}；每条右侧一个加入按钮；
 * 右栏为已选项，每条右侧一个移除按钮。<b>分组标题由调用方给出完整原文</b>（含 {@code §} 颜色码与
 * {@code ▌} 前缀），本类不做任何拼接或配色，避免与旧项目原文产生差异。</p>
 *
 * <p>用户 2026-09-15 拍板的两处增强：<b>右栏同样按组排列</b>（旧项目右栏是混合列表，看不出条目
 * 属哪一组）；<b>添加 / 移除后给一条聊天回执</b>（旧项目只有计数变化，没有任何提示）。</p>
 *
 * <p>列表行支持物品 / 方块 / 实体贴图（由调用方通过 {@link Entry#drawIcon} 提供）：原版条目能取得贴图，
 * 服务器自定义条目与无贴图条目自动回退为纯文字。旧项目列表为纯文字，本项为展示增强（用户 2026-09-14 决定保留）。</p>
 *
 * <p><b>左栏分组标题可折叠、且默认收起</b>（用户 2026-09-16：「装备选择列表要按大类分组且可折叠」）：
 * 点标题整组收起 / 展开，标题右侧常显该组条目数（收起状态也看得出有多少项），折叠状态记在会话级集合
 * {@link #expandedGroups} 里，过滤词变化重建也不丢。依据《开发习惯》第二十九章第 195~196 条
 * （候选多到需要滚动必须先分类；每个分类必须可折叠且默认收起）。空分组保持旧行为（静态标题 +「§8无」）。</p>
 *
 * <p>本类不持有选中数据：读 {@code selectedKeys}、写走 {@code onAdd} / {@code onRemove}，
 * 数据源始终唯一在业务侧，避免出现第二份副本。</p>
 */
public final class SelectorScreen extends PanelScreen {

    /** 候选项。 */
    public interface Entry {

        /** 唯一键，选中状态以它为准。 */
        String key();

        /** 行标题，可含 {@code §} 颜色码。 */
        String title();

        /** 分组标题（完整原文，含颜色码与 {@code ▌} 前缀）；返回 {@code null} 或空表示不分组。 */
        String group();

        /** 图标绘制；坐标与尺寸由宿主给出，返回是否真的画出。 */
        boolean drawIcon(Canvas canvas, float x, float y, float size);
    }

    private static final float SPLIT_MIN_HEIGHT = 200f;
    private static final float SPLIT_ROW_GAP = 4f;
    private static final float GROUP_TITLE_HEIGHT = 22f;
    private static final float GROUP_TITLE_SIZE = 11f;
    private static final float EMPTY_HEIGHT = 20f;
    private static final float SEARCH_MAX_LENGTH = 64f;
    /** 分组标题的左内边距：与折叠前的 {@link TextLine} 一致（6），改标题写法不能把标题整列平移 */
    private static final float GROUP_TITLE_PAD_X = 6f;
    /** 标题基线比例：{@link TextLine} 用的是 {@code 行高/2 + 字号 × 0.36}，同一条标题折叠前后必须落同一高度 */
    private static final float GROUP_TITLE_BASELINE = 0.36f;
    /** 分组条目数的写法：{@code §8· §7N}（颜色码交给 {@link MinecraftText} 解析，浅色主题自动换深色版） */
    private static final String GROUP_COUNT_PREFIX = " §8· §7";
    /** 条目数与折叠箭头之间的留白 */
    private static final float GROUP_COUNT_GAP = 6f;
    /** 折叠指示箭头（Material Symbols：chevron_right），与 {@code FoldSection} 同一个码点 */
    private static final String GROUP_ARROW = "\uE5CC";
    private static final float GROUP_ARROW_SIZE = 13f;
    /** 展开时箭头右转 90°（朝下），收起时朝右——与 {@code FoldSection} 同一口径 */
    private static final float GROUP_ARROW_SPIN = 90f;
    private static final float GROUP_ARROW_CENTER_RATIO = 0.36f;
    /**
     * 左栏最多铺这么多行。
     *
     * <p>方块 / 物品注册表各有一千多项，全铺出来既没人看得到底、也要每帧绘制上千个行控件。
     * 超过就截断，剩下一律靠搜索收窄（截断处会写明还有多少没列）。</p>
     */
    private static final int MAX_ROWS = 200;

    /** Material Symbols：add / remove。 */
    private static final String GLYPH_ADD = "\uE145";
    private static final String GLYPH_REMOVE = "\uE15B";

    /** 左栏空态；与旧项目一致。 */
    private static final String EMPTY_TEXT = "  §8无";

    private final String windowTitle;
    private final List<Entry> entries;
    private final Supplier<List<String>> selectedKeys;
    private final Consumer<String> onAdd;
    private final Consumer<String> onRemove;
    /** 单选模式的选中回调；{@code null} = 常规多选（左加右减） */
    private final Consumer<String> onPick;

    private final SplitPanels split = new SplitPanels(SPLIT_MIN_HEIGHT, SPLIT_ROW_GAP);
    /**
     * <b>已展开</b>的分组标题（会话级：本窗口实例存活期间有效，过滤词变化触发 {@link #rebuild()} 也不丢）。
     *
     * <p><b>为什么记「展开」而不是记「收起」：</b>第 196 条要求默认收起——集合初始为空即全部收起；
     * {@code ConsoleWidgets.FoldSection} 那套是记「收起」（不记即展开），口径正好相反，不能照抄。
     * 键取分组标题原文（调用方给出的完整原文在候选表里唯一）。</p>
     */
    private final Set<String> expandedGroups = new HashSet<>();
    private String filter = "";

    public SelectorScreen(String windowTitle, Screen parent, List<Entry> entries,
                          Supplier<List<String>> selectedKeys,
                          Consumer<String> onAdd, Consumer<String> onRemove) {
        this(windowTitle, parent, entries, selectedKeys, onAdd, onRemove, null);
    }

    private SelectorScreen(String windowTitle, Screen parent, List<Entry> entries,
                           Supplier<List<String>> selectedKeys,
                           Consumer<String> onAdd, Consumer<String> onRemove,
                           Consumer<String> onPick) {
        super(windowTitle, parent);
        // 聊天回执用窗口标题作前缀：本窗口是通用选择器，没有所属模块名可借
        this.windowTitle = windowTitle;
        this.entries = entries == null ? List.of() : List.copyOf(entries);
        this.selectedKeys = selectedKeys;
        this.onAdd = onAdd;
        this.onRemove = onRemove;
        this.onPick = onPick;
        build();
    }

    /**
     * 单选模式：左栏点一行即选中并关窗，右栏只给一句怎么用的说明。
     *
     * <p>给「挑一个方块当映射键 / 当替代方块」这类场景用：那里要的是「选哪一个」而不是
     * 「维护一张名单」，因此不显示加减按钮，点行即定。</p>
     */
    public static SelectorScreen pick(String windowTitle, Screen parent, List<Entry> entries,
                                      Consumer<String> onPick) {
        return new SelectorScreen(windowTitle, parent, entries, () -> List.of(),
            key -> { }, key -> { }, onPick);
    }

    private void build() {
        // 搜索框：旧项目该窗为无标签输入框（ItemTargetSelectScreen:34），不加标签与提示
        content().add(new CompactRow("",
                new SettingTextBox(() -> filter, this::applyFilter, (int) SEARCH_MAX_LENGTH)));
        content().add(split);
        rebuild();
    }

    private void applyFilter(String value) {
        filter = value == null ? "" : value.trim().toLowerCase(Locale.ROOT);
        rebuild();
    }

    /** 按当前选中集合与过滤词重建两栏。 */
    private void rebuild() {
        split.reset();
        CompactStack left = split.left();
        CompactStack right = split.right();

        List<String> keys = selectedKeys.get();
        buildAvailable(left, keys == null ? List.of() : keys);
        buildSelected(right, keys == null ? List.of() : keys);
    }

    private void buildAvailable(CompactStack left, List<String> keys) {
        // 分组顺序按调用方给出的条目顺序固定：空分组同样保留标题（旧项目口径，见类注释），
        // 因此先按全部条目建组，再往组里填可见条目，不能反过来「有内容才建组」。
        Map<String, List<Entry>> groups = new LinkedHashMap<>();
        for (Entry entry : entries) {
            groups.computeIfAbsent(entry.group() == null ? "" : entry.group(), key -> new ArrayList<>());
        }
        int shown = 0;
        for (Entry entry : entries) {
            if (shown >= MAX_ROWS) break;
            if (keys.contains(entry.key()) || !matches(entry)) continue;
            groups.get(entry.group() == null ? "" : entry.group()).add(entry);
            shown++;
        }

        if (groups.isEmpty()) {
            left.add(new TextLine(EMPTY_TEXT).height(EMPTY_HEIGHT));
            return;
        }
        for (Map.Entry<String, List<Entry>> group : groups.entrySet()) {
            List<Entry> items = group.getValue();
            boolean titled = !group.getKey().isEmpty();
            if (titled) {
                // 标题原样显示调用方给的原文（旧项目：§a§l▌ 原版物品 / §d§l▌ 自定义物品）。
                // 有内容的分组标题可点折叠并带条目数；空分组保持旧行为（静态标题 + 下一行「无」，不给折叠交互）。
                left.add(items.isEmpty() ? groupTitle(group.getKey()) : new GroupHeader(group.getKey(), items.size()));
            }
            if (items.isEmpty()) {
                // 空分组补一行「无」，标题不重复（旧项目 addAvailableByType 返回 false 时的原文）
                left.add(new TextLine(EMPTY_TEXT).height(EMPTY_HEIGHT));
                continue;
            }
            // 收起的分组只留标题：条目数已写在标题右侧，展开后行数与之一致
            if (titled && !isGroupExpanded(group.getKey())) continue;
            for (Entry entry : items) left.add(row(entry, true));
        }
        if (shown >= MAX_ROWS) {
            left.add(new TextLine("  §8只列前 " + MAX_ROWS + " 个，再输入几个字缩小范围").height(EMPTY_HEIGHT));
        }
    }

    private void buildSelected(CompactStack right, List<String> keys) {
        // 单选模式没有「已选名单」这个概念，右栏只说明怎么操作
        if (onPick != null) {
            right.add(new TextLine("  §8点左栏任意一行即可选中").height(EMPTY_HEIGHT));
            return;
        }
        // 顺序以选中集合为准，不按候选顺序重排，避免用户看到的次序跳变
        List<Entry> chosen = new ArrayList<>();
        for (String key : keys) {
            for (Entry entry : entries) {
                if (entry.key().equals(key) && matches(entry)) {
                    chosen.add(entry);
                    break;
                }
            }
        }

        if (chosen.isEmpty()) {
            right.add(new TextLine(EMPTY_TEXT).height(EMPTY_HEIGHT));
            return;
        }

        // 右栏同样按原版 / 自定义分组（用户 2026-09-15 拍板的增强）：候选里出现过的组，
        // 右栏也给出同样的标题并按组排列，组内为空时与左栏一样补一行「无」。
        // 右栏标题<b>不做折叠</b>：这里是用户自己刚选中的东西（通常个位数），默认收起会把刚点进来的一批
        // 立刻藏起来；第 195~196 条针对的是「多到需要滚动的候选」，即左栏。
        Map<String, List<Entry>> groups = new LinkedHashMap<>();
        for (Entry entry : entries) {
            groups.computeIfAbsent(entry.group() == null ? "" : entry.group(), key -> new ArrayList<>());
        }
        for (Entry entry : chosen) {
            groups.get(entry.group() == null ? "" : entry.group()).add(entry);
        }

        for (Map.Entry<String, List<Entry>> group : groups.entrySet()) {
            if (!group.getKey().isEmpty()) {
                right.add(new TextLine(group.getKey())
                        .height(GROUP_TITLE_HEIGHT)
                        .size(GROUP_TITLE_SIZE)
                        .bold(true));
            }
            if (group.getValue().isEmpty()) {
                right.add(new TextLine(EMPTY_TEXT).height(EMPTY_HEIGHT));
                continue;
            }
            for (Entry entry : group.getValue()) right.add(row(entry, false));
        }
    }

    /** 静态分组标题（空分组用）：与折叠前的写法逐字一致。 */
    private static TextLine groupTitle(String title) {
        return new TextLine(title)
                .height(GROUP_TITLE_HEIGHT)
                .size(GROUP_TITLE_SIZE)
                .bold(true);
    }

    /** 分组是否展开（默认收起：不在 {@link #expandedGroups} 里即收起）。 */
    private boolean isGroupExpanded(String groupTitle) {
        return expandedGroups.contains(groupTitle);
    }

    /**
     * 可折叠的分组标题（左栏候选的分组）。
     *
     * <p><b>外观不改</b>：第 196 条要求「分类标题只作章节标记：不铺底色、不做成按钮样式、不与普通条目
     * 撞脸」，因此这里只有标题原文 + 条目数 + 一个 Material Symbols 小箭头，没有底色与悬停高亮；
     * 与折叠前的唯一差别就是标题后面多了「§8· 12」与箭头。</p>
     *
     * <p><b>点整行切换</b>（与 {@code FoldSection} 同一命中口径），切换后整页重建：
     * 行数与标题上的数字都要跟着变，写进 {@link #expandedGroups} 后由重建读回，过滤词变化也不会丢。</p>
     */
    private final class GroupHeader implements CompactElement {

        private final String title;
        private final int count;

        private GroupHeader(String title, int count) {
            this.title = title;
            this.count = count;
        }

        @Override
        public float height() {
            return GROUP_TITLE_HEIGHT;
        }

        @Override
        public void update(float dt) {
        }

        @Override
        public void draw(Canvas canvas, float x, float y, float width, float alpha,
                         float mouseX, float mouseY) {
            ClickGuiThemeColors tc = ClickGuiThemeColors.current();
            float centerY = y + GROUP_TITLE_HEIGHT / 2f;
            float baseline = centerY + GROUP_TITLE_SIZE * GROUP_TITLE_BASELINE;
            float cursor = x + GROUP_TITLE_PAD_X;
            cursor += MinecraftText.draw(canvas, title, cursor, baseline, GROUP_TITLE_SIZE,
                    tc.primaryText, alpha, true);
            // 条目数：收起 / 展开都显示（用户要求「收起状态也要能看出有多少项」）
            cursor += MinecraftText.draw(canvas, GROUP_COUNT_PREFIX + count, cursor, baseline,
                    GROUP_TITLE_SIZE, tc.primaryText, alpha, false);
            drawArrow(canvas, cursor + GROUP_COUNT_GAP + GROUP_ARROW_SIZE / 2f, centerY, tc, alpha);
        }

        /** 指示箭头：展开时朝下、收起时朝右（与 {@code FoldSection} 同一码点与旋转口径） */
        private void drawArrow(Canvas canvas, float centerX, float centerY, ClickGuiThemeColors tc, float alpha) {
            float arrowWidth = FontRenderer.measureTextWidth(GROUP_ARROW, GROUP_ARROW_SIZE,
                    FontRenderer.MATERIAL_SYMBOLS);
            canvas.save();
            canvas.translate(centerX, centerY);
            canvas.rotate(isGroupExpanded(title) ? GROUP_ARROW_SPIN : 0f);
            canvas.translate(-centerX, -centerY);
            FontRenderer.drawText(canvas, GROUP_ARROW, centerX - arrowWidth / 2f,
                    centerY + GROUP_ARROW_SIZE * GROUP_ARROW_CENTER_RATIO, GROUP_ARROW_SIZE,
                    GlassPanel.withAlpha(tc.mutedText, alpha), FontRenderer.MATERIAL_SYMBOLS);
            canvas.restore();
        }

        @Override
        public boolean onClick(float mx, float my, float x, float y, float width, int button) {
            if (button != 0) return false;
            if (mx < x || mx > x + width || my < y || my > y + GROUP_TITLE_HEIGHT) return false;
            if (!expandedGroups.remove(title)) expandedGroups.add(title);
            rebuild();
            return true;
        }

        @Override
        public boolean onDrag(float mx, float my, float x, float y, float width) {
            return false;
        }
    }

    private ListRow row(Entry entry, boolean adding) {
        // 单选模式：整行点一下即选中并关窗，不出现加减按钮
        if (onPick != null) {
            return new ListRow(entry.title())
                    .icon(entry::drawIcon)
                    .onActivate(() -> {
                        onPick.accept(entry.key());
                        requestClose();
                    });
        }
        IconButton action = new IconButton(adding ? GLYPH_ADD : GLYPH_REMOVE, () -> {
            if (adding) {
                onAdd.accept(entry.key());
            } else {
                onRemove.accept(entry.key());
            }
            notifyAction(adding, entry);
            rebuild();
        });
        action.danger();
        ListRow row = new ListRow(entry.title())
                .icon(entry::drawIcon)
                .action(action);
        return row;
    }

    /**
     * 添加 / 移除后的聊天回执。
     *
     * <p>用户 2026-09-15 拍板的增强：旧项目该窗口只有计数变化（{@code setting.refreshCount()}），
     * 没有任何提示，此处补一条回执。</p>
     */
    private void notifyAction(boolean adding, Entry entry) {
        String name = stripFormatting(entry.title()).trim();
        ClientChat.send(windowTitle, (adding ? "§7已添加 §a" : "§7已移除 §c") + name);
    }

    private boolean matches(Entry entry) {
        if (filter.isEmpty()) return true;
        String title = stripFormatting(entry.title()).toLowerCase(Locale.ROOT);
        return title.contains(filter) || entry.key().toLowerCase(Locale.ROOT).contains(filter);
    }

    /** 去掉 {@code §x} 颜色码后再参与过滤，否则输入的字会被颜色码打断。 */
    private static String stripFormatting(String text) {
        if (text == null || text.isEmpty()) return "";
        StringBuilder result = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '§' && i + 1 < text.length()) {
                i++;
                continue;
            }
            result.append(c);
        }
        return result.toString();
    }
}
