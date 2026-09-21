package com.yiyiaddon.feature.enchant.ui.console;

import com.yiyiaddon.feature.enchant.EnchantModule;
import com.yiyiaddon.feature.enchant.config.EnchantSettings;
import com.yiyiaddon.feature.enchant.ui.EnchantConsoleScreen;
import com.yiyiaddon.ui.SelectionReceipt;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.render.TooltipLayer;
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

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final EnchantSettings DEFAULTS = new EnchantSettings();

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
            new Ctl(new Button("添加", this::add).disabledWhen(() -> owner.customDraft().isBlank())),
            // 出厂值 = 空列表；↺ 清掉本页已填的全部目标附魔
            ConsoleWidgets.resetCtl(() -> {
                // 回执的条数必须是清空前的真实值（清空后再读只会是 0），因此先读再清
                int count = targets.size();
                targets.clear();
                targets.addAll(DEFAULTS.customEnchantTargets);
                module.persistSettings();
                owner.reload();
                SelectionReceipt.cleared(count);
            }, "自定义附魔目标"))));

        // 填写规则说明（用户 2026-09-20 要求：这一行下面要讲清「服务器里的自定义附魔怎么写」）。
        // 判据出处：状态机 matchesEnchantmentTask / normalizeEnchantmentText，两种写法在匹配前都会归一。
        // 配色：§7 标签 / §8 分隔符 / §a 可直接照抄的示例 / §6 提示 / §c 注意（与本项目控制台口径一致）
        stack.add(new Note(owner, "§7格式 §8▸ §f一行一个目标：§a附魔名 §7空格 §a等级§f，例如 §a打雷 5"));
        stack.add(new Note(owner, "§7等级 §8▸ §f阿拉伯数字或罗马数字都行，§aV§f、§aⅡ§f、§aⅤ §f都当成 §a5"));
        stack.add(new Note(owner, "§7空格 §8▸ §f可有可无，两种都能识别，例如 §a打雷 5 §f与 §a打雷5 §8= §a永生 2 §f与 §a永生2"));
        stack.add(new Note(owner, "§6提示 §8▸ §7不写等级时只按名字匹配（名字含该词条就算命中），容易误收，建议带上等级"));
        stack.add(new Note(owner, "§c注意 §8▸ §7名字本身带数字（如 §c烈焰3型§7）必须用空格分开，否则结尾数字会被当等级"));

        for (String target : targets) {
            stack.add(new ConsoleRow(owner, () -> target, null, null,
                List.of(new Ctl(new IconButton(GLYPH_REMOVE, () -> remove(target)).danger()))));
        }
    }

    /** 添加一项：去空白后入列表（重复项跳过），落盘并重建本页让新行立刻出现 */
    private void add() {
        String value = owner.customDraft() == null ? "" : owner.customDraft().strip();
        if (value.isEmpty()) {
            // 空输入不再静默返回：与自动挖矿的自建文本列表页同一口径、逐字同一句提示
            TooltipLayer.notify("§e请先输入一项");
            return;
        }
        List<String> targets = module.settings().customEnchantTargets;
        if (targets.contains(value)) {
            // 重复项不改动名单：不谎报「已添加」，也不能点了没回音（与空输入同一句口径）
            TooltipLayer.notify("§e已经在列表里 §8▸ §f" + value);
        } else {
            targets.add(value);
            module.persistSettings();
            SelectionReceipt.added(value);
        }
        owner.customDraft("");
        owner.reload();
    }

    /** 删除一项：整份列表落盘并重建本页 */
    private void remove(String target) {
        if (!module.settings().customEnchantTargets.remove(target)) return;
        module.persistSettings();
        SelectionReceipt.removed(target);
        owner.reload();
    }
}
