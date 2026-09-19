package com.yiyiaddon.feature.autofarm.ui;

import com.yiyiaddon.feature.autofarm.AutoFarmModule;
import com.yiyiaddon.feature.autofarm.ui.console.AutoFarmConsoleScreen;
import com.yiyiaddon.feature.autofarm.model.CropProfile;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.render.ItemIconCache;
import com.yiyiaddon.ui.screen.SelectorScreen;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.Button;
import io.github.humbleui.skija.Canvas;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * 自动农场四类作物选择器（双作物 / 单作物 / 柱状物 / 果实）。
 *
 * <p><b>候选集直接给定</b>（差异 D-13-04）：旧 BlockListSetting 的 {@code filter(...)} 白名单
 * 换成「按分类过滤器从十一种作物里筛出候选」，候选范围逐字一致。窗口是通用的
 * {@link SelectorScreen} 多选构造；<b>每行必须带图标</b>（D9：候选与已选两栏都有，
 * 图标取作物主产物的原版物品贴图，不得出现纯文字行）。</p>
 *
 * <p>选择集存方块注册表 ID（{@link AutoFarmSettings} 的四张表），改动即写盘（第 173 条）。</p>
 */
public final class FarmSelectors {

    private FarmSelectors() {
    }

    /**
     * 构建一行「作物分类选择器」：行标签 = 设置名（逐字），右侧按钮显示已选数量，
     * 点击弹出 {@link SelectorScreen}。
     *
     * <p>行尾 ↺ 把这一类作物选择恢复出厂值：出厂值就是空选择
     * （{@code AutoFarmSettings} 的四张表字段都初始化为空表），因此↺ 清空本行对应的那张表。</p>
     */
    public static ConsoleRow row(AutoFarmConsoleScreen host, AutoFarmModule module,
                                 String label, String hint,
                                 Predicate<Block> filter,
                                 Supplier<Map<String, Boolean>> selection) {
        // 行尾注释实时变化（已选数量随选择器增删），走 liveComment 工厂
        return ConsoleRow.liveComment(host, () -> label, hint, () -> "已选 " + selection.get().size(),
            List.of(new Ctl(new Button("§b配置", () -> open(host, module, label, filter, selection)),
                    "打开「" + label + "」选择器"),
                ConsoleWidgets.resetCtl(() -> {
                    selection.get().clear();
                    module.persistSettings();
                    host.reload();
                }, label)));
    }

    /** 打开分类选择器：候选 = 十一种作物中通过分类过滤的条目（每行带图标） */
    private static void open(AutoFarmConsoleScreen host, AutoFarmModule module,
                             String label, Predicate<Block> filter,
                             Supplier<Map<String, Boolean>> selection) {
        List<SelectorScreen.Entry> entries = new ArrayList<>();
        for (CropProfile profile : CropProfile.values()) {
            // 杂物（仙人掌花）不通过任何分类过滤，天然不会成为候选
            if (!matchesFilter(profile, filter)) continue;
            entries.add(new Entry(blockId(profile), profile));
        }

        host.client().gui.setScreen(new SelectorScreen(label, host, entries,
            () -> List.copyOf(selection.get().keySet()),
            key -> {
                if (candidateKeys(filter).contains(key)) {
                    selection.get().put(key, true);
                    module.persistSettings();
                }
            },
            key -> {
                selection.get().remove(key);
                module.persistSettings();
            }));
    }

    /** 作物是否通过分类过滤（图标与 key 也按过滤后的候选给出） */
    private static boolean matchesFilter(CropProfile profile, Predicate<Block> filter) {
        Block block = AutoFarmModule.blockOf(blockId(profile));
        return block != null && filter.test(block);
    }

    /** 通过过滤的候选键集（onAdd 时二次校验，防止手改存档越界） */
    private static List<String> candidateKeys(Predicate<Block> filter) {
        List<String> keys = new ArrayList<>();
        for (CropProfile profile : CropProfile.values()) {
            if (matchesFilter(profile, filter)) keys.add(blockId(profile));
        }
        return keys;
    }

    /** 作物的注册表方块 ID（图鉴常量方块的键，全部在原版命名空间） */
    private static String blockId(CropProfile profile) {
        return net.minecraft.core.registries.BuiltInRegistries.BLOCK.getKey(profile.block()).toString();
    }

    /** 选择器条目：标题 = 作物中文名，图标 = 主产物物品（D9），无分组（每类候选 ≤ 3，无需分组） */
    private record Entry(String id, CropProfile profile)
        implements SelectorScreen.Entry {

        @Override
        public String key() {
            return id;
        }

        @Override
        public String title() {
            return "§f" + profile.displayName();
        }

        @Override
        public String group() {
            return null;
        }

        @Override
        public boolean drawIcon(Canvas canvas, float x, float y, float size) {
            Block block = AutoFarmModule.blockOf(id);
            if (block == null) return false;
            ItemStack stack = new ItemStack(profile.harvestItem());
            return ItemIconCache.getInstance().draw(canvas, stack, x, y, size);
        }

        /** 与 drawIcon 同一份图标：作物主产物。 */
        @Override
        public ItemStack iconStack() {
            return profile.harvestItem().getDefaultInstance();
        }
    }
}
