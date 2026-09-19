package com.yiyiaddon.integration.baritone;

import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.ui.component.ButtonRow;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.component.ListRow;
import com.yiyiaddon.ui.component.SearchRow;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.render.ItemIconCache;
import com.yiyiaddon.ui.screen.PanelScreen;
import com.yiyiaddon.ui.screen.SelectorScreen;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingTextBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Baritone「方块映射」编辑器（{@code buildValidSubstitutes} / {@code buildSubstitutes}）。
 *
 * <p>这两项是 {@code Map<Block, List<Block>>}：「原理图里的某个方块 → 可以当成它 / 可以拿它替代的方块」。
 * 以前界面上只有一个 {@code {}}，连里面有什么都看不见，更没有改的办法（实机反馈「什么叫只能看不能改」）。
 * 这里给它一个能改的界面：</p>
 *
 * <ul>
 *   <li><b>映射键</b>一栏列出全部键，点一行把它选中（选中行右侧标「编辑中」），减号删掉这个键；</li>
 *   <li><b>替代方块</b>一栏列出当前选中键的替代列表，减号移出；</li>
 *   <li>两个「添加」按钮都走 {@link SelectorScreen#pick} 的挑块窗口——<b>全程不用手打 id</b>。</li>
 * </ul>
 *
 * <p>数据只有一份：打开时把当前值复制成工作副本，每次增删把整份映射写回 Baritone 设置，
 * 界面上不保存第二份真相。</p>
 */
public final class BaritoneBlockMapScreen extends PanelScreen {

    /** 行内减号（Material Symbols：remove） */
    private static final String GLYPH_REMOVE = "\uE15B";
    private static final float SECTION_HEIGHT = 24f;
    private static final int INPUT_MAX_LENGTH = 64;
    /** 选中键的徽标色（系统绿） */
    private static final int SELECTED_BADGE = 0x34C759;

    private final String module;
    private final Map<Block, List<Block>> values = new LinkedHashMap<>();
    private final Consumer<Map<Block, List<Block>>> write;

    /** 当前正在编辑替代列表的键 */
    private Block selected;
    private String filter = "";
    private final SettingTextBox search = new SettingTextBox(() -> filter, this::setFilter, INPUT_MAX_LENGTH);

    public BaritoneBlockMapScreen(Screen parent, String title, Supplier<Map<Block, List<Block>>> read,
                                  Consumer<Map<Block, List<Block>>> write) {
        super(title, parent);
        this.module = title;
        this.write = write;
        Map<Block, List<Block>> current = read.get();
        if (current != null) {
            current.forEach((key, list) -> values.put(key, list == null ? new ArrayList<>() : new ArrayList<>(list)));
        }
        selected = values.keySet().stream().findFirst().orElse(null);
        rebuild();
    }

    private void rebuild() {
        CompactStack stack = content();
        stack.clear();
        // 搜索框铺满标签右侧；原来挂在行内的说明改走悬停浮层（铺满后行内已没有空隙摆说明文字）
        stack.add(new SearchRow("筛选", search)
            .tooltip(() -> "按名称或登记名过滤，输入即过滤"));
        buildKeys(stack);
        buildSubstitutes(stack);
        stack.add(new ButtonRow(new Button("＋ 添加映射键", this::pickKey)));
        stack.add(new ButtonRow(new Button(selected == null
            ? "＋ 添加替代方块（先选中一个键）" : "＋ 给「" + selected.getName().getString() + "」添加替代方块",
            this::pickSubstitute)));
    }

    private void buildKeys(CompactStack stack) {
        stack.add(new TextLine(() -> "§b§l▌ 映射键 §7" + values.size() + " 项　§8点一行选中它")
            .height(SECTION_HEIGHT).size(12f).bold(true));
        if (values.isEmpty()) {
            stack.add(new TextLine("§8无（还没有任何映射）"));
            return;
        }
        for (Block key : values.keySet()) {
            if (!matches(key)) continue;
            IconButton remove = new IconButton(GLYPH_REMOVE, () -> {
                values.remove(key);
                if (key == selected) selected = values.keySet().stream().findFirst().orElse(null);
                push();
                rebuild();
            });
            remove.danger();
            ListRow row = blockRow(key).onActivate(() -> {
                selected = key;
                rebuild();
            }).action(remove);
            if (key == selected) row.badge(() -> "编辑中", SELECTED_BADGE);
            stack.add(row);
        }
    }

    private void buildSubstitutes(CompactStack stack) {
        String name = selected == null ? "未选中" : selected.getName().getString();
        List<Block> list = selected == null ? List.of() : values.getOrDefault(selected, List.of());
        stack.add(new TextLine(() -> "§a§l▌ 「" + name + "」的替代方块 §7" + list.size() + " 项")
            .height(SECTION_HEIGHT).size(12f).bold(true));
        if (selected == null) {
            stack.add(new TextLine("§8先在上面点一行，选中要编辑的映射键"));
            return;
        }
        if (list.isEmpty()) {
            stack.add(new TextLine("§8无（这个键还没有替代方块）"));
            return;
        }
        for (Block block : List.copyOf(list)) {
            if (!matches(block)) continue;
            IconButton remove = new IconButton(GLYPH_REMOVE, () -> {
                values.get(selected).remove(block);
                push();
                rebuild();
            });
            remove.danger();
            stack.add(blockRow(block).action(remove));
        }
    }

    // ── 挑块 ──

    private void pickKey() {
        Minecraft.getInstance().gui.setScreen(SelectorScreen.pick("选择映射键", this,
            BaritoneChoices.entries(BaritoneChoices.Kind.BLOCK), id -> {
                Block block = BaritoneChoices.blockOf(id);
                if (block == null) return;
                values.computeIfAbsent(block, key -> new ArrayList<>());
                selected = block;
                push();
                rebuild();
            }));
    }

    private void pickSubstitute() {
        if (selected == null) {
            ClientChat.send(module, "§e先在上面点一行，选中要编辑的映射键");
            return;
        }
        Block key = selected;
        Minecraft.getInstance().gui.setScreen(SelectorScreen.pick("给「" + key.getName().getString() + "」添加替代方块",
            this, BaritoneChoices.entries(BaritoneChoices.Kind.BLOCK), id -> {
                Block block = BaritoneChoices.blockOf(id);
                if (block == null) return;
                List<Block> list = values.computeIfAbsent(key, k -> new ArrayList<>());
                if (!list.contains(block)) list.add(block);
                push();
                rebuild();
            }));
    }

    // ── 行与取值 ──

    private ListRow blockRow(Block block) {
        Identifier id = BuiltInRegistries.BLOCK.getKey(block);
        String text = id == null ? "?" : id.toString();
        return new ListRow(block.getName().getString())
            .icon((canvas, x, y, size) -> ItemIconCache.getInstance().drawBlock(canvas, block, x, y, size))
            .detail(() -> "§8" + text);
    }

    private boolean matches(Block block) {
        if (filter.isEmpty()) return true;
        Identifier id = BuiltInRegistries.BLOCK.getKey(block);
        if (id != null && id.toString().contains(filter)) return true;
        return block.getName().getString().toLowerCase(Locale.ROOT).contains(filter);
    }

    private void setFilter(String text) {
        filter = text == null ? "" : text.strip().toLowerCase(Locale.ROOT);
        rebuild();
    }

    private void push() {
        write.accept(new HashMap<>(values));
    }
}
