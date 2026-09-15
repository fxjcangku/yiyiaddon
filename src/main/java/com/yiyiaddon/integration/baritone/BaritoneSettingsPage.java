package com.yiyiaddon.integration.baritone;

import baritone.api.BaritoneAPI;
import baritone.api.Settings;
import baritone.api.utils.SettingsUtil;
import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.widget.SettingColorPicker;
import com.yiyiaddon.ui.widget.SettingModule;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingText;
import com.yiyiaddon.ui.widget.SettingTextBox;
import com.yiyiaddon.ui.widget.SettingToggle;
import com.yiyiaddon.ui.widget.SettingWidget;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.awt.Color;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.DoubleFunction;

/**
 * Baritone设置：把 Baritone 本体的<b>全部</b>设置按类型分组建到本项目界面里。
 *
 * <p><b>数据来源：</b>反射 Baritone 的 {@link Settings}，逐字段取 {@code Setting} 对象——不是抄一份
 * 设置清单，因此 Baritone 更新后新增的设置会自动出现，也不会出现「界面一份、执行层另一份」。
 * 分组口径与 Meteor 的 {@code pathing/BaritoneSettings} 一致：开 / 关、带小数点的数字、整数、
 * 文字、颜色、方块名单、物品名单，界面还没有修改方式的类型（映射表等）落在「其它（只能看，不能改）」。</p>
 *
 * <p><b>中文：</b>名称与说明取 {@link BaritoneSettingTranslations}（与 Baritone 命令行汉化同一张表），
 * 表里没有的设置回退成英文键名——键名必须可见，它是 {@code #set} 命令与配置文件里唯一的标识。</p>
 *
 * <p><b>写回：</b>任何改动都立刻落到 Baritone 自己的配置（{@link SettingsUtil#save(Settings)}），
 * 与 Baritone 自带界面「改完即存」的口径一致，不存在「改了但没保存」。</p>
 */
public final class BaritoneSettingsPage extends BasePage {

    /** 数值框范围：Baritone 的数值设置量级都在这个区间内，实际上等价于不夹取。 */
    private static final double NUMBER_MIN = -1.0e7;
    private static final double NUMBER_MAX = 1.0e7;
    /** 文本框宽度与长度上限。 */
    private static final float TEXT_BOX_WIDTH = 220f;
    private static final int TEXT_MAX_LENGTH = 512;
    /** 列表框更宽：一张表可能有十几个登记名。 */
    private static final float LIST_BOX_WIDTH = 320f;
    private static final int LIST_MAX_LENGTH = 4096;
    /** 只读值最多显示多少字符（超出截断，避免一行把整页排版撑坏）。 */
    private static final int READONLY_MAX_LENGTH = 120;

    /** 页内搜索串（小写）：既过滤子项，也决定整组是否显示。 */
    private String query = "";

    /** 颜色项：调色板改的是 {@link EspColor}，需要每帧回写 Baritone 的 {@link Color}。 */
    private final List<ColorBinding> colorBindings = new ArrayList<>();

    public BaritoneSettingsPage() {
        Catalog bools = new Catalog("开 / 关", "只有开和关两种状态，点一下切换");
        Catalog numbers = new Catalog("带小数点的数字", "可以填小数，例如 1.5");
        Catalog integers = new Catalog("整数", "只能填整数，不能带小数点（刻数 / 距离 / 高度 / 次数）");
        Catalog strings = new Catalog("文字", "填一段文字");
        Catalog colors = new Catalog("颜色", "渲染用颜色，点击色块打开调色板");
        Catalog blockLists = new Catalog("方块名单",
                "一串方块：英文逗号分隔登记名，例如 minecraft:stone, minecraft:dirt");
        Catalog itemLists = new Catalog("物品名单", "一串物品：英文逗号分隔登记名");
        Catalog others = new Catalog("其它（只能看，不能改）", "界面还没有对应的修改方式，只展示当前值");

        Settings settings = BaritoneAPI.getSettings();
        // 只遍历 Baritone 自己维护的清单：新增设置自动出现，且与 #set 命令看到的是同一份
        for (Settings.Setting<?> setting : settings.allSettings) {
            // 与 Baritone 的 #set 命令同口径：javaOnly 的设置不对用户开放，不在这里显示
            if (setting.isJavaOnly()) continue;

            String key = setting.getName();
            BaritoneSettingTranslations.Translation translation = BaritoneSettingTranslations.find(key);
            String label = translation == null ? key : translation.name();
            String description = translation == null ? key : translation.description();
            Object value = setting.value;

            if (value instanceof Boolean) {
                bools.add(label, description, new SettingToggle(
                        () -> Boolean.TRUE.equals(setting.value),
                        enabled -> write(setting, enabled)));
            } else if (value instanceof Double) {
                numbers.add(label, description, numberBox(setting, 0.01, "%.3f", v -> v));
            } else if (value instanceof Float) {
                numbers.add(label, description, numberBox(setting, 0.01, "%.3f", v -> (float) v));
            } else if (value instanceof Integer) {
                integers.add(label, description, numberBox(setting, 1, "%.0f", v -> (int) Math.round(v)));
            } else if (value instanceof Long) {
                integers.add(label, description, numberBox(setting, 1, "%.0f", v -> Math.round(v)));
            } else if (value instanceof String) {
                strings.add(label, description, new SettingTextBox(
                        () -> String.valueOf(setting.value),
                        text -> write(setting, text), TEXT_MAX_LENGTH).width(TEXT_BOX_WIDTH));
            } else if (value instanceof Color color) {
                EspColor display = new EspColor(color.getRGB() & 0xFFFFFF, color.getAlpha());
                colorBindings.add(new ColorBinding(display, setting));
                colors.add(label, description, new SettingColorPicker(label, display));
            } else if (value instanceof List<?> && listElementType(setting) == Block.class) {
                blockLists.add(label, description, new SettingTextBox(
                        () -> joinBlockIds(setting),
                        text -> writeBlocks(setting, text), LIST_MAX_LENGTH).width(LIST_BOX_WIDTH));
            } else if (value instanceof List<?> && listElementType(setting) == Item.class) {
                itemLists.add(label, description, new SettingTextBox(
                        () -> joinItemIds(setting),
                        text -> writeItems(setting, text), LIST_MAX_LENGTH).width(LIST_BOX_WIDTH));
            } else {
                others.add(label, description,
                        new SettingText(() -> summarize(setting.value), LIST_BOX_WIDTH));
            }
        }
    }

    @Override
    public String getTitle() {
        return UiText.t("Baritone设置", "Baritone Settings");
    }

    @Override
    public String getSubtitle() {
        return UiText.t("Baritone 全部设置，按类型分组；改动即时写回 Baritone 配置",
                "Every Baritone setting, grouped by type; changes are saved to Baritone immediately");
    }

    /**
     * 搜索：除交给基类做分组过滤外，本页自己再按「子项」过滤一次。
     *
     * <p>设置有两百多项，只按分组标题过滤的话搜「挖掘」会把整个「开 / 关」组全部留下，
     * 等于没搜。这里的查询串同时驱动子项可见性与分组可见性，基类的布局缓存在查询变化时失效，
     * 两者读的是同一次重建。</p>
     */
    @Override
    public void setSearchQuery(String query) {
        this.query = query == null ? "" : query.strip().toLowerCase(Locale.ROOT);
        super.setSearchQuery(query);
    }

    @Override
    public void update(float dt) {
        super.update(dt);
        for (ColorBinding binding : colorBindings) binding.push();
    }

    // ── 取值 / 写回 ──

    private static SettingNumberBox numberBox(Settings.Setting<?> setting, double step, String format,
                                              DoubleFunction<Object> converter) {
        return new SettingNumberBox(NUMBER_MIN, NUMBER_MAX, step, format,
                () -> setting.value instanceof Number number ? number.doubleValue() : 0d,
                value -> write(setting, converter.apply(value)));
    }

    /** 写回一个设置并立刻落盘（Baritone 的配置由它自己维护，这里只触发保存）。 */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void write(Settings.Setting<?> setting, Object value) {
        ((Settings.Setting) setting).value = value;
        SettingsUtil.save(BaritoneAPI.getSettings());
    }

    /**
     * 取列表型设置的元素类型。
     *
     * <p>{@link Settings.Setting#getType()} 给的就是值的类型（Baritone 在构造时从字段泛型里取出来的），
     * 例：{@code Setting<List<Block>>} → {@code List<Block>}，因此这里只需再脱一层 {@code List<…>}。
     * 不是列表（映射表等）时返回 {@code null}，由调用方落到「其它（只能看，不能改）」。</p>
     */
    private static Type listElementType(Settings.Setting<?> setting) {
        if (!(setting.getType() instanceof ParameterizedType parameterized)) return null;
        if (parameterized.getRawType() != List.class) return null;
        Type[] arguments = parameterized.getActualTypeArguments();
        return arguments.length == 1 ? arguments[0] : null;
    }

    // ── 列表值的文本化 ──

    private static String joinBlockIds(Settings.Setting<?> setting) {
        if (!(setting.value instanceof List<?> list)) return "";
        List<String> ids = new ArrayList<>(list.size());
        for (Object element : list) {
            if (element instanceof Block block) ids.add(BuiltInRegistries.BLOCK.getKey(block).toString());
        }
        return String.join(", ", ids);
    }

    private static String joinItemIds(Settings.Setting<?> setting) {
        if (!(setting.value instanceof List<?> list)) return "";
        List<String> ids = new ArrayList<>(list.size());
        for (Object element : list) {
            if (element instanceof Item item) ids.add(BuiltInRegistries.ITEM.getKey(item).toString());
        }
        return String.join(", ", ids);
    }

    private static void writeBlocks(Settings.Setting<?> setting, String text) {
        List<Block> parsed = new ArrayList<>();
        for (String token : text.split(",")) {
            Identifier id = Identifier.tryParse(token.strip());
            if (id == null) continue;
            Block block = BuiltInRegistries.BLOCK.getValue(id);
            if (block != null && !parsed.contains(block)) parsed.add(block);
        }
        writeParsed(setting, text, parsed);
    }

    private static void writeItems(Settings.Setting<?> setting, String text) {
        List<Item> parsed = new ArrayList<>();
        for (String token : text.split(",")) {
            Identifier id = Identifier.tryParse(token.strip());
            if (id == null) continue;
            Item item = BuiltInRegistries.ITEM.getValue(id);
            if (item != null && !parsed.contains(item)) parsed.add(item);
        }
        writeParsed(setting, text, parsed);
    }

    /**
     * 写回解析结果。
     *
     * <p>一个登记名都没解析出来（例如整串都是拼错的 ID）时不写回：手滑不该把整张表清空。
     * 真正要清空时把输入清空即可——空串解析出空表，是用户明确表达的「清空」。</p>
     */
    private static void writeParsed(Settings.Setting<?> setting, String text, List<?> parsed) {
        if (parsed.isEmpty() && !text.isBlank()) return;
        write(setting, parsed);
    }

    /** 只读值摘要：超长截断，空值显示成 {@code -}。 */
    private static String summarize(Object value) {
        if (value == null) return "§8-";
        String text = String.valueOf(value);
        if (text.isEmpty()) return "§8-";
        if (text.length() > READONLY_MAX_LENGTH) text = text.substring(0, READONLY_MAX_LENGTH) + "…";
        return "§f" + text;
    }

    // ── 分组（带子项级搜索过滤） ──

    private final class Catalog {

        private final String title;
        private final String subtitle;
        private final SettingModule module;
        private final List<String> entries = new ArrayList<>();

        private Catalog(String title, String subtitle) {
            this.title = title;
            this.subtitle = subtitle;
            this.module = new SettingModule(title, subtitle, null);
            this.module.visibleWhen(() -> headerMatches() || anyEntryMatches());
            modules.add(this.module);
        }

        /** 登记一个设置项；名称与说明都参与页内搜索。 */
        private void add(String label, String description, SettingWidget widget) {
            String haystack = (label + " " + description).toLowerCase(Locale.ROOT);
            entries.add(haystack);
            module.addSub(label, description, widget, () -> headerMatches() || haystack.contains(query));
        }

        private boolean headerMatches() {
            if (query.isEmpty()) return true;
            return title.toLowerCase(Locale.ROOT).contains(query)
                    || subtitle.toLowerCase(Locale.ROOT).contains(query);
        }

        private boolean anyEntryMatches() {
            for (String entry : entries) {
                if (entry.contains(query)) return true;
            }
            return false;
        }
    }

    /** 颜色项绑定：调色板改 {@link EspColor}，每帧把它同步回 Baritone 的 {@link Color}。 */
    private static final class ColorBinding {

        private final EspColor display;
        private final Settings.Setting<?> setting;

        private ColorBinding(EspColor display, Settings.Setting<?> setting) {
            this.display = display;
            this.setting = setting;
        }

        private void push() {
            if (!(setting.value instanceof Color current)) return;
            int rgb = display.rgb();
            int alpha = display.alpha();
            if ((current.getRGB() & 0xFFFFFF) == rgb && current.getAlpha() == alpha) return;
            write(setting, new Color((rgb >> 16) & 0xFF, (rgb >> 8) & 0xFF, rgb & 0xFF, alpha));
        }
    }
}
