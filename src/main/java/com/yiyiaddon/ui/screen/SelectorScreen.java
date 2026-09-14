package com.yiyiaddon.ui.screen;

import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.ListRow;
import com.yiyiaddon.ui.component.SplitPanels;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingTextBox;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.gui.screens.Screen;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 通用双栏选择器窗口：左栏候选、右栏已选。
 *
 * <p><b>交互口径照旧项目：</b>顶部搜索框打开即聚焦，边输边过滤（匹配显示名与技术 ID，忽略大小写）；
 * 左栏按分组标题排列未选项，组内为空时显示 {@code §8无}；每条右侧一个加入按钮；
 * 右栏为已选项，每条右侧一个移除按钮。分组标题用旧项目原文格式 {@code §a§l▌ 组名}。</p>
 *
 * <p><b>超越旧项目的地方：</b>每条带图标（旧项目用旧框架表格控件，列表无图标）。
 * 图标由调用方通过 {@link Entry#drawIcon} 提供，因此物品、方块、实体、附魔共用这一个窗口。</p>
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

        /** 分组标题；返回 {@code null} 表示不分组。 */
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

    /** Material Symbols：add / remove。 */
    private static final String GLYPH_ADD = "\uE145";
    private static final String GLYPH_REMOVE = "\uE15B";

    /** 左栏空态；与旧项目一致。 */
    private static final String EMPTY_TEXT = "  §8无";

    private final List<Entry> entries;
    private final Supplier<List<String>> selectedKeys;
    private final Consumer<String> onAdd;
    private final Consumer<String> onRemove;

    private final SplitPanels split = new SplitPanels(SPLIT_MIN_HEIGHT, SPLIT_ROW_GAP);
    private String filter = "";

    public SelectorScreen(String windowTitle, Screen parent, List<Entry> entries,
                          Supplier<List<String>> selectedKeys,
                          Consumer<String> onAdd, Consumer<String> onRemove) {
        super(windowTitle, parent);
        this.entries = entries == null ? List.of() : List.copyOf(entries);
        this.selectedKeys = selectedKeys;
        this.onAdd = onAdd;
        this.onRemove = onRemove;
        build();
    }

    private void build() {
        content().add(new CompactRow("§7搜索（显示名 / 技术ID）",
                () -> "输入后即时过滤两侧清单",
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
        Map<String, List<Entry>> groups = new LinkedHashMap<>();
        for (Entry entry : entries) {
            if (keys.contains(entry.key()) || !matches(entry)) continue;
            String group = entry.group() == null ? "" : entry.group();
            groups.computeIfAbsent(group, key -> new ArrayList<>()).add(entry);
        }

        if (groups.isEmpty()) {
            left.add(new TextLine(EMPTY_TEXT).height(EMPTY_HEIGHT));
            return;
        }
        for (Map.Entry<String, List<Entry>> group : groups.entrySet()) {
            if (!group.getKey().isEmpty()) {
                left.add(new TextLine("§a§l▌ " + group.getKey())
                        .height(GROUP_TITLE_HEIGHT)
                        .size(GROUP_TITLE_SIZE)
                        .bold(true));
            }
            for (Entry entry : group.getValue()) left.add(row(entry, true));
        }
    }

    private void buildSelected(CompactStack right, List<String> keys) {
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
        for (Entry entry : chosen) right.add(row(entry, false));
    }

    private ListRow row(Entry entry, boolean adding) {
        IconButton action = new IconButton(adding ? GLYPH_ADD : GLYPH_REMOVE, () -> {
            if (adding) {
                onAdd.accept(entry.key());
            } else {
                onRemove.accept(entry.key());
            }
            rebuild();
        });
        action.danger();
        ListRow row = new ListRow(entry.title())
                .icon(entry::drawIcon)
                .action(action);
        return row;
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
