package com.yiyiaddon.integration.baritone;

import com.yiyiaddon.ui.SelectionReceipt;
import com.yiyiaddon.ui.component.ButtonRow;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.ListRow;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.render.TooltipLayer;
import com.yiyiaddon.ui.screen.PanelScreen;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingTextBox;
import net.minecraft.client.gui.screens.Screen;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Baritone「文字名单」编辑器（{@code buildIgnoreProperties} 这类 {@code List<String>} 设置）。
 *
 * <p>条目是自由文本（方块状态属性名，如 {@code waterlogged}），不像方块那样有登记表可挑，
 * 所以这一项必须是「输入一行 + 加号」而不是选择器；但它同样不该让人对着一个 320px 的
 * 手打文本框写一长串逗号——那是「其它（只能看，不能改）」里最后剩下的那类设置。</p>
 *
 * <p>列表整体写回：读一次当前值建工作副本，每次增删把整份列表写回 Baritone 设置，
 * 数据只有一份，界面上不会出现第二副本。</p>
 */
public final class BaritoneTextListScreen extends PanelScreen {

    /** 行内减号（Material Symbols：remove） */
    private static final String GLYPH_REMOVE = "\uE15B";
    private static final float SECTION_HEIGHT = 24f;
    private static final int INPUT_MAX_LENGTH = 64;

    private final List<String> values = new ArrayList<>();
    private final Consumer<List<String>> write;
    private String pending = "";
    private final SettingTextBox input = new SettingTextBox(() -> pending, this::setPending, INPUT_MAX_LENGTH);

    /**
     * @param title 窗口标题（设置的中文名）
     * @param read  读当前值
     * @param write 写回整份列表
     */
    public BaritoneTextListScreen(Screen parent, String title, Supplier<List<String>> read,
                                  Consumer<List<String>> write) {
        super(title, parent);
        this.write = write;
        List<String> current = read.get();
        if (current != null) {
            for (String value : current) {
                if (value != null && !value.isBlank()) values.add(value.strip());
            }
        }
        rebuild();
    }

    private void rebuild() {
        CompactStack stack = content();
        stack.clear();
        stack.add(new CompactRow("新增", () -> "输入一项后点右边的加号", input));
        stack.add(new ButtonRow(new Button("＋ 添加这一项", this::add)));
        stack.add(new TextLine(() -> "§b§l▌ 当前 §7" + values.size() + " 项")
            .height(SECTION_HEIGHT).size(12f).bold(true));
        if (values.isEmpty()) {
            stack.add(new TextLine("§8无（列表为空）"));
        } else {
            for (String value : values) stack.add(row(value));
        }
    }

    private ListRow row(String value) {
        IconButton remove = new IconButton(GLYPH_REMOVE, () -> {
            values.remove(value);
            push();
            rebuild();
            // 原来减号点下去毫无反馈；面板开着时 MC 会把 HUD 藏起来，聊天回执看不见，改面板内顶部弹窗
            SelectionReceipt.removed(value);
        });
        remove.danger();
        return new ListRow(value).action(remove);
    }

    private void add() {
        String value = pending == null ? "" : pending.strip();
        // 两处校验提示原来是聊天播报：本窗口开着时聊天框根本看不见，改成同一条顶部弹窗，文字逐字保留
        if (value.isEmpty()) {
            TooltipLayer.notify("§e请先输入一项");
            return;
        }
        if (values.stream().anyMatch(existing -> existing.equalsIgnoreCase(value))) {
            TooltipLayer.notify("§e已经在列表里 §8▸ §f" + value);
            return;
        }
        values.add(value);
        pending = "";
        push();
        rebuild();
        // 成功加入原来没有回执，补一条（面板内弹窗，唯一出口见 SelectionReceipt）
        SelectionReceipt.added(value);
    }

    private void setPending(String text) {
        pending = text == null ? "" : text;
    }

    private void push() {
        write.accept(new ArrayList<>(values));
    }
}
