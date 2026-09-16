package com.yiyiaddon.feature.enchant.ui.console;

import com.yiyiaddon.feature.enchant.EnchantModule;
import com.yiyiaddon.feature.enchant.ui.EnchantConsoleScreen;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingTextBox;

import java.util.List;

/**
 * 自动附魔控制台「自定义附魔」页：{@code 自定义附魔目标} 一项（旧
 * {@code CustomEnchantSetting}，{@code :145-149}，CUSTOM 模式专属）。
 *
 * <p>设置名与描述逐字沿旧：{@code 每行填写一个附魔名称和等级，例如：打雷 5。支持中文、阿拉伯数字、
 * 罗马数字和不带等级的附魔。}；设置本身是一份自由文本列表（旧壳是 {@code StringListSetting}，
 * 每行一项、行内可删），控制台按同一语义自建：一行输入框 + 「添加」，下方每项一行 + 删除。
 * 输入框草稿存在窗口侧（{@link EnchantConsoleScreen#customDraft()}），整页重建不会丢未提交内容。</p>
 *
 * <p>写入的是 {@code EnchantSettings.customEnchantTargets}（状态机
 * {@code rebuildActiveTasks} 逐项归一后并入目标词条），改完立即落盘。</p>
 */
public final class EnchantCustomPage {

    /** 输入框宽度与长度上限（同本项目其它自由文本设置） */
    private static final float TEXT_BOX_WIDTH = 240f;
    private static final int TEXT_MAX_LENGTH = 64;

    /** 行内「移除这一项」（Material Symbols：remove，与选择器右栏同一字形） */
    private static final String GLYPH_REMOVE = "\uE15B";

    private final EnchantConsoleScreen owner;
    private final EnchantModule module;

    public EnchantCustomPage(EnchantConsoleScreen owner, EnchantModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        List<String> targets = module.settings().customEnchantTargets;

        stack.add(new ConsoleRow(owner, () -> "自定义附魔目标",
            "每行填写一个附魔名称和等级，例如：打雷 5。支持中文、阿拉伯数字、罗马数字和不带等级的附魔。",
            null, List.of(
            new Ctl(new SettingTextBox(owner::customDraft, owner::customDraft, TEXT_MAX_LENGTH)
                .width(TEXT_BOX_WIDTH)),
            new Ctl(new Button("添加", this::add).disabledWhen(() -> owner.customDraft().isBlank())))));

        for (String target : targets) {
            stack.add(new ConsoleRow(owner, () -> target, null, null,
                List.of(new Ctl(new IconButton(GLYPH_REMOVE, () -> remove(target)).danger()))));
        }
    }

    /** 添加一项：去空白后入列表（重复项跳过），落盘并重建本页让新行立刻出现 */
    private void add() {
        String value = owner.customDraft() == null ? "" : owner.customDraft().strip();
        if (value.isEmpty()) return;
        List<String> targets = module.settings().customEnchantTargets;
        if (!targets.contains(value)) {
            targets.add(value);
            module.persistSettings();
        }
        owner.customDraft("");
        owner.reload();
    }

    /** 删除一项：整份列表落盘并重建本页 */
    private void remove(String target) {
        if (!module.settings().customEnchantTargets.remove(target)) return;
        module.persistSettings();
        owner.reload();
    }
}
