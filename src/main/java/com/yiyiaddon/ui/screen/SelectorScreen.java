package com.yiyiaddon.ui.screen;

import com.yiyiaddon.core.ClientChat;
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

    private final SplitPanels split = new SplitPanels(SPLIT_MIN_HEIGHT, SPLIT_ROW_GAP);
    private String filter = "";

    public SelectorScreen(String windowTitle, Screen parent, List<Entry> entries,
                          Supplier<List<String>> selectedKeys,
                          Consumer<String> onAdd, Consumer<String> onRemove) {
        super(windowTitle, parent);
        // 聊天回执用窗口标题作前缀：本窗口是通用选择器，没有所属模块名可借
        this.windowTitle = windowTitle;
        this.entries = entries == null ? List.of() : List.copyOf(entries);
        this.selectedKeys = selectedKeys;
        this.onAdd = onAdd;
        this.onRemove = onRemove;
        build();
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
        for (Entry entry : entries) {
            if (keys.contains(entry.key()) || !matches(entry)) continue;
            groups.get(entry.group() == null ? "" : entry.group()).add(entry);
        }

        if (groups.isEmpty()) {
            left.add(new TextLine(EMPTY_TEXT).height(EMPTY_HEIGHT));
            return;
        }
        for (Map.Entry<String, List<Entry>> group : groups.entrySet()) {
            if (!group.getKey().isEmpty()) {
                // 标题原样显示调用方给的原文（旧项目：§a§l▌ 原版物品 / §d§l▌ 自定义物品）
                left.add(new TextLine(group.getKey())
                        .height(GROUP_TITLE_HEIGHT)
                        .size(GROUP_TITLE_SIZE)
                        .bold(true));
            }
            if (group.getValue().isEmpty()) {
                // 空分组补一行「无」，标题不重复（旧项目 addAvailableByType 返回 false 时的原文）
                left.add(new TextLine(EMPTY_TEXT).height(EMPTY_HEIGHT));
                continue;
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

        // 右栏同样按原版 / 自定义分组（用户 2026-09-15 拍板的增强）：候选里出现过的组，
        // 右栏也给出同样的标题并按组排列，组内为空时与左栏一样补一行「无」。
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

    private ListRow row(Entry entry, boolean adding) {
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
