package com.yiyiaddon.ui.console;

import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.render.world.EspRenderObject;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

/**
 * 「显示与颜色」小节的唯一装配处：一类点位渲染对象一行（显示开关 + 「设置」窗口 + 行尾 ↺），
 * 外加持一类对象的字牌大小行。
 *
 * <p><b>口径来自星露谷点位页</b>（用户 2026-09-19：「所有标点选择点位位置的模块 参照星露谷农场的
 * 点位设置」）：小节标题 {@code §7§l显示与颜色}，每类对象独立一行 —— 行标题是对象名、描述是对象说明、
 * 控件是「显示开关 + 设置」，行尾 ↺ 只把显示恢复出厂值（颜色 / 渲染模式在「设置」窗口里各自有 ↺）。
 * 设置窗口是共用件
 * {@link com.yiyiaddon.ui.screen.RenderObjectScreen}：显示 / 颜色（自定义）/ 彩虹 / 渲染模式（线框 / 面 / 两者）。</p>
 *
 * <p><b>为什么必须共用（第 169 条）</b>：自动挖矿 / 自动农场 / 村民交易 / 自动箱子 / 自动附魔 /
 * 星露谷六个点位页的这套行如果各写一份，就是六份同样的「显示开关 + 设置按钮 + ↺」组装与文案，
 * 任何一处改口径都要改六遍。</p>
 *
 * <p><b>落盘时机</b>：每个开关与数字框改动后立即调 {@code persist}（各模块自己的设置落盘链路）；
 * 行尾 ↺ 走 {@code persist + reload}（重建本页，让 ↺ 之外的显示态同步刷新）。</p>
 */
public final class PointRenderSection {

    /** 小节标题文案（星露谷与自动挖矿点位页同一句） */
    public static final String TITLE = "§7§l显示与颜色";

    /** 字牌大小行名 / 描述的默认取用方；各模块可传自己的文案 */
    public static final String NAME_LABEL_SIZE = "字牌大小";
    public static final String DESC_LABEL_SIZE = "点位头顶文字的字号（字越大越远也看得清，越容易挡住视线）";

    private final ConsoleHost host;
    private final Runnable persist;
    private final Runnable reload;
    /** 打开「渲染设置 · 对象名」窗口；由各模块控制台给出（它才知道自己的 client 与模块引用） */
    private final Consumer<EspRenderObject> openScreen;
    /** 出厂对象（按对象名匹配，行尾 ↺ 的取值来源） */
    private final List<EspRenderObject> factoryObjects;

    public PointRenderSection(ConsoleHost host, Runnable persist, Runnable reload,
                              Consumer<EspRenderObject> openScreen, List<EspRenderObject> factoryObjects) {
        this.host = host;
        this.persist = persist == null ? () -> { } : persist;
        this.reload = reload == null ? () -> { } : reload;
        this.openScreen = openScreen;
        this.factoryObjects = factoryObjects == null ? List.of() : factoryObjects;
    }

    /** 小节标题行（与星露谷、自动挖矿点位页同一行高与字号） */
    public static Note title(ConsoleHost host) {
        return new Note(host, TITLE, null, ConsoleMetrics.SECTION_HEIGHT, ConsoleMetrics.SECTION_SIZE);
    }

    /** 一批对象各一行（顺序即界面顺序） */
    public List<CompactElement> rows(List<EspRenderObject> objects) {
        List<CompactElement> rows = new ArrayList<>();
        for (EspRenderObject object : objects) rows.add(row(object));
        return rows;
    }

    /**
     * 单个渲染对象行：{@code 对象名 + 显示开关 + 「设置」+ ↺}。
     *
     * <p>行尾 ↺ 只恢复显示开关 —— 与星露谷点位页同口径（颜色 / 渲染模式在「设置」窗口里各自有 ↺）。</p>
     */
    public ConsoleRow row(EspRenderObject object) {
        EspRenderObject factory = factoryOf(object);
        SettingToggle toggle = new SettingToggle(() -> object.show, value -> {
            object.show = value;
            persist.run();
        });
        Button settings = new Button("设置", () -> {
            if (openScreen != null) openScreen.accept(object);
        });
        return new ConsoleRow(host, object::name, object.description(), null, List.of(
            new Ctl(toggle, "显示 / 隐藏「" + object.name() + "」"),
            new Ctl(settings, object.colorEditable()
                ? "打开「" + object.name() + "」的显示 / 颜色 / 渲染模式"
                : "打开「" + object.name() + "」的显示开关（字牌颜色跟随界面主题）"),
            ConsoleWidgets.resetCtl(() -> {
                object.show = factory.show;
                persist.run();
                reload.run();
            }, object.name())));
    }

    /**
     * 字牌大小行：字号是「一类排版参数」，不随单个点位类别变化，因此单独一行（与星露谷口径一致）。
     *
     * @param min / max / defaultValue 取值域与出厂值；{@code getter} / {@code setter} 读写模块自己的字段
     */
    public ConsoleRow labelSizeRow(String name, String description, int min, int max,
                                   Supplier<Integer> getter, IntConsumer setter, int defaultValue) {
        SettingNumberBox box = new SettingNumberBox(min, max, 1, "%.0f",
            () -> (double) getter.get(),
            value -> {
                setter.accept((int) Math.round(value));
                persist.run();
            });
        return new ConsoleRow(host, () -> name, description, null, List.of(new Ctl(box),
            ConsoleWidgets.resetCtl(() -> {
                setter.accept(defaultValue);
                persist.run();
                reload.run();
            }, name)));
    }

    /** 字牌大小行（用共用文案） */
    public ConsoleRow labelSizeRow(int min, int max, Supplier<Integer> getter, IntConsumer setter,
                                   int defaultValue) {
        return labelSizeRow(NAME_LABEL_SIZE, DESC_LABEL_SIZE, min, max, getter, setter, defaultValue);
    }

    /**
     * 该对象的出厂设置实例：按对象名在出厂清单里取同一项。
     *
     * <p>渲染对象是模块设置类里的固定字段（名字唯一且不变），取不到时返回自身（等价于 ↺ 不动作）。</p>
     */
    private EspRenderObject factoryOf(EspRenderObject object) {
        for (EspRenderObject candidate : factoryObjects) {
            if (candidate.name().equals(object.name())) return candidate;
        }
        return object;
    }
}
