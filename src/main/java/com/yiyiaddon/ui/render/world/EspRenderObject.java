package com.yiyiaddon.ui.render.world;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * 一类可配置的世界渲染对象：<b>显示开关 + 颜色（含彩虹）+ 渲染模式</b>。
 *
 * <p><b>为什么要有这个共用件（第 169 条）</b>：星露谷原先在 {@code StardewSettings} 里内嵌了一份
 * {@code RenderObject}，自动挖矿 / 自动农场 / 村民交易 / 自动箱子 / 自动附魔的点位页要「参照星露谷
 * 的点位设置」时，如果各自再抄一份，就会在六个模块里出现六份同样的「显示 / 颜色 / 模式」载体与
 * 编解码。本类把它抽到渲染层：模块只声明「我有哪几类渲染对象」，界面行、设置窗口与落盘口径全部共用。</p>
 *
 * <p><b>每类对象各自独立</b>：关掉一类不影响另一类；颜色与渲染模式互不牵连。这是星露谷点位渲染
 * 落地时定下的独立性铁律，本类是该口径的唯一载体。</p>
 *
 * <p><b>落盘键</b>：{@code <前缀>show} / {@code <前缀>mode} / {@code <前缀>colorRgb} 等
 * （颜色走 {@link EspColor#save(JsonObject, String)}），与星露谷历史键完全一致 —— 老存档原样读回。</p>
 *
 * <p><b>可变对象</b>：{@link #show} / {@link #color} / {@link #mode} 都是可变状态，调色板与设置行
 * 直接改它，渲染侧下一帧生效；出厂值单独留在 final 字段里，供行尾「恢复默认」就地写回
 * （颜色对象不可换引用，见 {@link #resetColor()}）。</p>
 */
public final class EspRenderObject {

    /** 对象显示名（界面行标题与「渲染设置」窗口标题都用它，同一模块内必须唯一） */
    private final String name;
    /** 对象说明（行 tooltip 与设置窗口底部说明用的那句话） */
    private final String description;
    /** 出厂默认 RGB */
    private final int defaultRgb;
    /** 出厂默认透明度 */
    private final int defaultAlpha;
    /** 出厂默认渲染模式；{@code null} 表示该类没有渲染模式 */
    private final ShapeMode defaultMode;
    /** 颜色是否可单独设置（点位字牌一类的「跟随方框」对象为 false） */
    private final boolean colorEditable;

    /** 显示开关 */
    public boolean show = true;
    /** 颜色（含透明度与彩虹开关） */
    public final EspColor color;
    /** 渲染模式；{@code null} 表示该类只有显示与颜色 */
    public ShapeMode mode;

    /** 有渲染模式、颜色可单独设置（最常用的一种）。 */
    public EspRenderObject(String name, String description, int rgb, int alpha, ShapeMode mode) {
        this(name, description, rgb, alpha, mode, true);
    }

    /**
     * @param colorEditable 颜色是否可单独设置；{@code false} 用于「颜色不由本对象决定」的字牌类对象
     *                      ——字牌的颜色跟随界面主题（见 {@link PointLabelText}），
     *                      摆一个点了没反应的色块只会误导玩家（星露谷实机反馈的口径）
     */
    public EspRenderObject(String name, String description, int rgb, int alpha, ShapeMode mode,
                           boolean colorEditable) {
        this.name = name;
        this.description = description;
        this.defaultRgb = rgb & 0xFFFFFF;
        this.defaultAlpha = alpha & 0xFF;
        this.defaultMode = mode;
        this.colorEditable = colorEditable;
        this.mode = mode;
        this.color = new EspColor(this.defaultRgb, this.defaultAlpha);
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    /** 是否有渲染模式（字牌类为 false） */
    public boolean hasMode() {
        return defaultMode != null;
    }

    /** 颜色是否可单独设置（字牌类为 false：颜色跟随对应方框） */
    public boolean colorEditable() {
        return colorEditable;
    }

    // ── 出厂值（行尾「恢复默认」的取值来源） ──

    public int defaultRgb() {
        return defaultRgb;
    }

    public int defaultAlpha() {
        return defaultAlpha;
    }

    /** 出厂渲染模式；无模式时为 {@code null} */
    public ShapeMode defaultMode() {
        return defaultMode;
    }

    /** 把颜色就地写回出厂值（{@link #color} 是 final，不能换引用） */
    public void resetColor() {
        color.rgb(defaultRgb).alpha(defaultAlpha).rainbow(false)
            .rainbowSpeed(Rainbow.DEFAULT_SPEED).rainbowOffset(0.0);
    }

    /** 把显示与渲染模式就地写回出厂值（颜色另走 {@link #resetColor()}） */
    public void resetShowAndMode() {
        show = true;
        mode = defaultMode;
    }

    /** 把本对象整体恢复出厂值 */
    public void resetAll() {
        resetShowAndMode();
        resetColor();
    }

    /** 把另一个对象的颜色就地写进本对象（用于「恢复默认」，不能换引用） */
    public void copyColorFrom(EspColor source) {
        if (source == null) return;
        color.rgb(source.rgb()).alpha(source.alpha()).rainbow(source.rainbow())
            .rainbowSpeed(source.rainbowSpeed()).rainbowOffset(source.rainbowOffset());
    }

    // ── 持久化 ──

    /**
     * 写入 JSON。
     *
     * @param prefix 键前缀（形如 {@code render.种子箱.}）；颜色键由 {@link EspColor#save} 拼出
     *               {@code <前缀>colorRgb / colorAlpha / colorRainbow / colorRainbowSpeed / colorRainbowOffset}
     */
    public void save(JsonObject json, String prefix) {
        if (json == null || prefix == null) return;
        json.addProperty(prefix + "show", show);
        if (hasMode()) json.addProperty(prefix + "mode", mode.name());
        color.save(json, prefix + "color");
    }

    /**
     * 读取 JSON；缺项保留当前值（构造函数里的默认值），非法渲染模式回退出厂模式。
     *
     * <p>颜色由 {@link EspColor#load} 逐分量读取，缺哪个分量就保留哪个分量的当前值，
     * 因此「只有 RGB 的老键」不会被整体覆盖成黑。</p>
     */
    public void load(JsonObject json, String prefix) {
        if (json == null || prefix == null) return;
        show = boolOf(json, prefix + "show", show);
        if (hasMode()) mode = shapeModeOf(json, prefix + "mode", mode);
        color.load(json, prefix + "color");
    }

    @Override
    public String toString() {
        return name;
    }

    private static boolean boolOf(JsonObject json, String key, boolean fallback) {
        try {
            JsonElement element = json.get(key);
            return element != null && element.isJsonPrimitive() ? element.getAsBoolean() : fallback;
        } catch (Exception ignored) {
            return fallback;
        }
    }

    private static ShapeMode shapeModeOf(JsonObject json, String key, ShapeMode fallback) {
        JsonElement element = json.get(key);
        if (element == null || !element.isJsonPrimitive()) return fallback;
        try {
            return ShapeMode.valueOf(element.getAsString());
        } catch (Exception ignored) {
            return fallback;
        }
    }
}
