package com.yiyiaddon.integration.baritone;

import baritone.api.BaritoneAPI;
import baritone.api.Settings;
import baritone.api.utils.SettingsUtil;
import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.widget.SettingColorPicker;
import com.yiyiaddon.ui.widget.SettingCycle;
import com.yiyiaddon.ui.widget.SettingLink;
import com.yiyiaddon.ui.widget.SettingModule;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingText;
import com.yiyiaddon.ui.widget.SettingTextBox;
import com.yiyiaddon.ui.widget.SettingToggle;
import com.yiyiaddon.ui.widget.SettingVec3Box;
import com.yiyiaddon.ui.widget.SettingWidget;
import com.yiyiaddon.ui.screen.SelectorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Vec3i;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.awt.Color;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.DoubleFunction;

/**
 * Baritone设置：把 Baritone 本体的<b>全部</b>设置按类型分组建到本项目界面里。
 *
 * <p><b>数据来源：</b>反射 Baritone 的 {@link Settings}，逐字段取 {@code Setting} 对象——不是抄一份
 * 设置清单，因此 Baritone 更新后新增的设置会自动出现，也不会出现「界面一份、执行层另一份」。
 * 分组口径与 Meteor 的 {@code pathing/BaritoneSettings} 一致：开 / 关、带小数点的数字、整数、
 * 文字、颜色、方块名单、物品名单。</p>
 *
 * <p><b>Meteor 没做的那几类这里也做了：</b>方块映射（{@code Map<Block, List<Block>>}）、
 * 文字名单（{@code List<String>}）、偏移（{@link Vec3i}）、枚举（原理图旋转 / 镜像）——
 * Meteor 直接跳过这四类，界面上根本没有入口（实机反馈「什么叫只能看不能改」）。</p>
 *
 * <p><b>不用手打登记名：</b>凡是要填方块 / 物品的地方一律走挑块窗口（搜索 + 图标 + 加减），
 * 只有命令前缀、文件后缀这类本来就不是登记名的文字设置才是输入框。</p>
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
    /** 只读展示更宽一格：兜底组里剩下的值（函数型设置等）要能看清。 */
    private static final float LIST_BOX_WIDTH = 320f;
    /** 只读值最多显示多少字符（超出截断，避免一行把整页排版撑坏）。 */
    private static final int READONLY_MAX_LENGTH = 120;

    /**
     * 可见提示（第 213 条）：循环项 / 色块看不出能点。
     *
     * <p>本页子项走紧凑行，只画标题、说明整段在悬停浮层里（{@code SettingModule} 的紧凑布局），
     * 所以提示只能拼在标题末尾；控制台行有独立注释位，那边用
     * {@code ConsoleWidgets.COMMENT_CYCLE} / {@code COMMENT_COLOR}。</p>
     */
    private static final String HINT_CYCLE = " · 点击切换";
    /** 同上，颜色块类（{@code SettingColorPicker}） */
    private static final String HINT_COLOR = " · 点击色块打开调色板";
    /**
     * 名单 / 映射入口按钮的宽度。
     *
     * <p>它显示的内容只是「N 项」这种极短文本，用默认的 150 会把紧凑双列里的标题挤成省略号；
     * 收窄到 96 后，像「禁止破坏方块列表」这样的长标题也能完整显示。</p>
     */
    private static final float LIST_LINK_WIDTH = 96f;

    /** 页内搜索串（小写）：既过滤子项，也决定整组是否显示。 */
    private String query = "";

    /** 颜色项：调色板改的是 {@link EspColor}，需要每帧回写 Baritone 的 {@link Color}。 */
    private final List<ColorBinding> colorBindings = new ArrayList<>();

    public BaritoneSettingsPage() {
        Catalog bools = new Catalog("开 / 关", "只有开和关两种状态，点一下切换");
        Catalog numbers = new Catalog("小数", "可以填小数，例如 1.5");
        Catalog integers = new Catalog("整数", "只能填整数，不能带小数点（刻数 / 距离 / 高度 / 次数）");
        Catalog strings = new Catalog("文字", "填一段文字");
        Catalog colors = new Catalog("颜色", "渲染用颜色，点击色块打开调色板");
        Catalog blockLists = new Catalog("方块名单", "点开选择实体方块（占满一格的那种）：搜索后加减，改动即时写回");
        Catalog itemLists = new Catalog("物品名单", "只列放得下去且占满一格的方块：搜索后加减，改动即时写回");
        Catalog maps = new Catalog("方块映射", "原理图方块 → 可替代方块；点开先选键，再加减替代方块");
        Catalog misc = new Catalog("其它可改项", "属性名单、重复偏移、原理图旋转与镜像");
        Catalog others = new Catalog("其它（只能看，不能改）", "函数型设置等没有修改方式的项，只展示当前值");
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
                // 行尾可见提示（第 213 条）：色块看不出能点；提示拼在标题上（与枚举项同一原因），
                // 调色板窗口的标题仍用不带提示的原名
                colors.add(label + HINT_COLOR, description, new SettingColorPicker(label, display));
            } else if (value instanceof List<?> && listElementType(setting) == Block.class) {
                blockLists.add(label, description, listButton(label, BaritoneChoices.Kind.BLOCK, setting));
            } else if (value instanceof List<?> && listElementType(setting) == Item.class) {
                itemLists.add(label, description, listButton(label, BaritoneChoices.Kind.ITEM, setting));
            } else if (value instanceof List<?> && listElementType(setting) == String.class) {
                misc.add(label, description, textListButton(label, setting));
            } else if (value instanceof Map<?, ?> && isBlockSubstituteMap(setting)) {
                maps.add(label, description, mapButton(label, setting));
            } else if (value instanceof Enum<?> constant) {
                // 行尾可见提示（第 213 条）：循环项看不出能点；本页子项走紧凑行、只画标题，
                // 说明整段只在悬停浮层里，故提示只能拼在标题上
                misc.add(label + HINT_CYCLE, description, enumCycle(setting, constant));
            } else if (value instanceof Vec3i) {
                misc.add(label, description, vec3Box(setting));
            } else {
                others.add(label, description,
                        new SettingText(() -> summarize(setting.value), LIST_BOX_WIDTH));
            }
        }

        // 分组模块在建完子项之后才创建：小标要写上「共 N 项」。
        // 分组默认折叠（200+ 项全展开要滚十几屏），首屏先给一张分类概览。
        for (Catalog catalog : List.of(bools, numbers, integers, strings, colors,
                blockLists, itemLists, maps, misc)) {
            catalog.finish();
        }
        // 只读兜底组：所有类型都有修改方式后它就该消失，一项都没有时不显示
        others.finishIfPresent();
    }

    @Override
    public String getTitle() {
        return UiText.t("Baritone设置", "Baritone Settings");
    }

    @Override
    public String getSubtitle() {
        return UiText.t("Baritone 全部设置，按类型分组；点标题展开分组，悬停看说明；改动即时写回配置",
                "Every Baritone setting, grouped by type; click a group to expand, hover for help");
    }

    /**
     * 搜索：除交给基类做分组过滤外，本页自己再按「子项」过滤一次。
     *
     * <p>设置有两百多项，只按分组标题过滤的话搜「挖掘」会把整个「开 / 关」组全部留下，
     * 等于没搜。这里的查询串同时驱动子项可见性与分组可见性，基类的布局缓存在查询变化时失效，
     * 两者读的是同一次重建。</p>
     *
     * <p>搜索时所有分组一律展开：分组默认是折叠的，不展开就等于「搜到了却看不见」；
     * 清空搜索回到全折叠的概览态。</p>
     */
    @Override
    public void setSearchQuery(String query) {
        this.query = query == null ? "" : query.strip().toLowerCase(Locale.ROOT);
        super.setSearchQuery(query);
        boolean searching = !this.query.isEmpty();
        for (SettingModule module : modules) module.setExpanded(searching);
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

    /**
     * 是不是「方块 → 方块列表」的映射（{@code Map<Block, List<Block>>}）。
     *
     * <p>只有这种形状才走映射编辑器：名字对得上、语义也对得上（原理图方块 → 可替代方块）。
     * 其它形状的映射表（Baritone 目前没有，但将来可能有）仍旧落进只读兜底组，
     * 免得拿一个语义不明的界面去改它。</p>
     */
    private static boolean isBlockSubstituteMap(Settings.Setting<?> setting) {
        if (!(setting.getType() instanceof ParameterizedType parameterized)) return false;
        if (parameterized.getRawType() != Map.class) return false;
        Type[] arguments = parameterized.getActualTypeArguments();
        if (arguments.length != 2 || arguments[0] != Block.class) return false;
        return arguments[1] instanceof ParameterizedType value
            && value.getRawType() == List.class
            && value.getActualTypeArguments().length == 1
            && value.getActualTypeArguments()[0] == Block.class;
    }

    // ── 列表值的文本化 ──

    /**
     * 名单类设置的入口控件：显示「N 项」并点开左右双栏选择器。
     *
     * <p>左栏是全部候选（搜索 + 真实贴图 + 加号），右栏是已选（减号）；加一个 / 减一个都立刻写回。
     * 打开界面时全部候选已经建好并缓存（见 {@link BaritoneChoices}），所以点开是瞬时的。</p>
     */
    private SettingLink listButton(String title, BaritoneChoices.Kind kind, Settings.Setting<?> setting) {
        return new SettingLink(() -> BaritoneChoices.ids(setting, kind).size() + " 项",
            () -> open(new SelectorScreen(title, Minecraft.getInstance().screen,
                BaritoneChoices.entries(kind),
                () -> BaritoneChoices.ids(setting, kind),
                id -> change(setting, kind, id, true),
                id -> change(setting, kind, id, false)))).width(LIST_LINK_WIDTH);
    }

    /** 加入 / 移出一个登记名并落盘；元素不认识或没有实际变化时不写回（不产生无意义的落盘） */
    private static void change(Settings.Setting<?> setting, BaritoneChoices.Kind kind, String id, boolean add) {
        List<?> next = BaritoneChoices.changed(setting, kind, id, add);
        if (next != null) write(setting, next);
    }

    /** 文字名单（{@code List<String>}）：点开一个「输入一行 + 减号」的编辑器 */
    private SettingLink textListButton(String title, Settings.Setting<?> setting) {
        return new SettingLink(() -> stringList(setting).size() + " 项",
            () -> open(new BaritoneTextListScreen(Minecraft.getInstance().screen, title,
                () -> stringList(setting), list -> write(setting, list)))).width(LIST_LINK_WIDTH);
    }

    /** 方块映射（{@code Map<Block, List<Block>>}）：点开一个「选键 → 加减替代方块」的编辑器 */
    private SettingLink mapButton(String title, Settings.Setting<?> setting) {
        return new SettingLink(() -> mapValue(setting).size() + " 组",
            () -> open(new BaritoneBlockMapScreen(Minecraft.getInstance().screen, title,
                () -> mapValue(setting), map -> write(setting, map)))).width(LIST_LINK_WIDTH);
    }

    /** 切换界面：只有主线程能调（界面切换由输入事件驱动，因此这里一定是主线程） */
    private static void open(Screen screen) {
        Minecraft.getInstance().setScreen(screen);
    }

    @SuppressWarnings("unchecked")
    private static Map<Block, List<Block>> mapValue(Settings.Setting<?> setting) {
        return setting.value instanceof Map<?, ?> map ? (Map<Block, List<Block>>) map : Map.of();
    }

    /** 文字名单的当前值；非字符串元素直接跳过（不会发生，只防手改配置写出脏数据） */
    private static List<String> stringList(Settings.Setting<?> setting) {
        List<String> result = new ArrayList<>();
        if (!(setting.value instanceof List<?> list)) return result;
        for (Object element : list) {
            if (element instanceof String text) result.add(text);
        }
        return result;
    }

    /** 枚举设置 → 循环选择：选项名用中文，写回枚举常量本身 */
    private static SettingCycle enumCycle(Settings.Setting<?> setting, Enum<?> current) {
        Object[] constants = current.getDeclaringClass().getEnumConstants();
        List<String> options = new ArrayList<>(constants.length);
        for (Object constant : constants) options.add(enumOptionName((Enum<?>) constant));
        return new SettingCycle(options, () -> indexOfConstant(setting, constants),
            index -> write(setting, constants[index]));
    }

    private static int indexOfConstant(Settings.Setting<?> setting, Object[] constants) {
        for (int index = 0; index < constants.length; index++) {
            if (constants[index] == setting.value) return index;
        }
        return 0;
    }

    /** 枚举选项的中文名；表里没有的常量回退成英文名，绝不显示成空白 */
    private static String enumOptionName(Enum<?> constant) {
        return switch (constant.name()) {
            case "NONE" -> "无";
            case "CLOCKWISE_90" -> "顺时针 90°";
            case "CLOCKWISE_180" -> "旋转 180°";
            case "COUNTERCLOCKWISE_90" -> "逆时针 90°";
            case "LEFT_RIGHT" -> "左右镜像";
            case "FRONT_BACK" -> "前后镜像";
            default -> constant.name();
        };
    }

    /** 三维偏移（{@link Vec3i}）→ X / Y / Z 三个整数框 */
    private static SettingVec3Box vec3Box(Settings.Setting<?> setting) {
        return new SettingVec3Box(
            () -> setting.value instanceof Vec3i vec ? vec : Vec3i.ZERO,
            vec -> write(setting, vec));
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

    /**
     * 一个分组：先攒子项，最后 {@link #finish()} 一次性建出模块。
     *
     * <p>攒完再建是为了把条数写进副标题——折叠态下玩家只能看到这一行，
     * 「开 / 关 · 共 96 项」让人一眼知道要不要点开。</p>
     */
    private final class Catalog {

        private final String title;
        private final String subtitle;
        private final List<Pending> pending = new ArrayList<>();
        private final List<String> entries = new ArrayList<>();

        private Catalog(String title, String subtitle) {
            this.title = title;
            this.subtitle = subtitle;
        }

        /** 登记一个设置项；名称与说明都参与页内搜索。 */
        private void add(String label, String description, SettingWidget widget) {
            pending.add(new Pending(label, description, widget));
            entries.add((label + " " + description).toLowerCase(Locale.ROOT));
        }

        /** 建模块并挂上全部子项（此时条数已确定） */
        private void finish() {
            SettingModule module = new SettingModule(title, subtitle, null).compact();
            if (!pending.isEmpty()) module.badge("共 " + pending.size() + " 项");
            module.visibleWhen(() -> headerMatches() || anyEntryMatches());
            modules.add(module);
            for (Pending entry : pending) {
                String haystack = (entry.label() + " " + entry.description()).toLowerCase(Locale.ROOT);
                module.addSub(entry.label(), entry.description(), entry.widget(),
                        () -> headerMatches() || haystack.contains(query));
            }
        }

        /** 只读兜底组用：一项都没有时整组不出现（全部类型都有修改方式后它就该消失） */
        private void finishIfPresent() {
            if (!pending.isEmpty()) finish();
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

    /** 尚未建模块的一个设置项 */
    private record Pending(String label, String description, SettingWidget widget) {
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
