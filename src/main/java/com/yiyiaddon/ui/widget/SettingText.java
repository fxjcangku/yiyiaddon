package com.yiyiaddon.ui.widget;

import com.yiyiaddon.ui.component.CardLayout;
import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.render.ItemIconCache;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

/**
 * 只读状态文本控件：在设置行右侧显示动态运行状态、计数一类的值。
 *
 * <p>不响应点击（不会看起来像按钮），文本每帧从 {@link Supplier} 读取，超宽时截断。
 * 取值异常时显示占位文案，不把异常抛进渲染循环。</p>
 */
public class SettingText extends SettingWidget {

    private static final float DEFAULT_WIDTH = 200f;
    private static final float FONT_SIZE = 11f;
    private static final String UNAVAILABLE = "状态不可用";
    /** 文字前物品图标的边长与它到文字的间距 */
    private static final float ICON_SIZE = 16f;
    private static final float ICON_GAP = 6f;

    private final Supplier<String> text;
    private final float width;
    /** 动态列宽：非 null 时 {@link #getWidth()} 以它为准（表格列口径，见构造器注释） */
    private final Supplier<Float> dynamicWidth;
    /** 文字左对齐；默认右对齐，贴住自身宽度区域的右边界（设置行右侧的状态值口径）。 */
    private boolean alignLeft;
    /** 文字前的物品图标（可选）：非 null 时与文字一起组成「图标 + 名字」的一格，画在文字正左方 */
    private Supplier<ItemStack> icon;

    public SettingText(Supplier<String> text) {
        this(text, DEFAULT_WIDTH, null);
    }

    public SettingText(Supplier<String> text, float width) {
        this(text, width, null);
    }

    /**
     * 列宽由外部每帧给出的「表格列」用法。
     *
     * <p>固定 200 宽的默认值在「一列状态文字」的场景里会留出大片空白，把同一行的按钮顶到行中间
     * 去（实机表现就是按钮看着没靠齐、位移了）。表格列应当按「同列最宽文本」定宽：
     * 文字左对齐后左右都贴住相邻控件，按钮也不再飘在行中间。</p>
     */
    public SettingText(Supplier<String> text, Supplier<Float> dynamicWidth) {
        this.text = text;
        this.width = DEFAULT_WIDTH;
        this.dynamicWidth = dynamicWidth;
    }

    private SettingText(Supplier<String> text, float width, Supplier<Float> dynamicWidth) {
        this.text = text;
        this.width = width;
        this.dynamicWidth = dynamicWidth;
    }

    /**
     * 改为左对齐。
     *
     * <p>整行状态行（如「已选 N / M 项」）用左对齐：默认右对齐会把文字推到自身宽度区域的右边界，
     * 于是整组内容看起来飘在行中间，与上下「左标签 ＋ 右控件」的行对不齐。旧项目该处是普通 label，
     * 即左对齐。</p>
     */
    public SettingText alignLeft() {
        this.alignLeft = true;
        return this;
    }

    /**
     * 在文字正左方挂一枚物品图标（链式），组成「图标 + 名字」的一格。
     *
     * <p><b>为什么要挂到状态值这一格里</b>：物品图标放在行首（{@code ConsoleRow#icon}）时，图标与
     * 「它是什么东西」的名字分处一行两端，中间隔着标签与整段空白；名字本身就是值的一部分时，
     * 图标应该跟名字贴在一起（用户 2026-09-21：「出售物品图标移动到右边的物品名字前面」）。</p>
     *
     * <p>图标由 {@link ItemIconCache} 绘制（走全屏统一的抽帧链路，未命中缓存的物品当帧不画）；
     * 文字可用宽度相应让出一格图标，宁可文字先截断，也不让图标溢出控件区。</p>
     *
     * @param icon 每帧取一次；返回 {@code null} 或空物品时既不画、也不占横向空间
     */
    public SettingText icon(Supplier<ItemStack> icon) {
        this.icon = icon;
        return this;
    }

    @Override
    public float getWidth() {
        if (dynamicWidth == null) return width;
        Float value = dynamicWidth.get();
        return value == null ? width : Math.max(1f, value);
    }

    @Override
    public float getHeight() {
        return 20f;
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float alpha) {
        String shown = resolve();
        if (shown.isEmpty()) return;
        ItemStack iconStack = icon == null ? null : icon.get();
        boolean drawIcon = iconStack != null && !iconStack.isEmpty();
        // 有图标时文字可用宽度让出一格图标：文字更宽时先截断文字，不让图标压出行外
        float textRoom = drawIcon ? Math.max(1f, width - ICON_SIZE - ICON_GAP) : width;
        float textWidth = MinecraftText.measure(shown, FONT_SIZE, false);
        if (textWidth > textRoom) {
            // 超宽：退化为纯文本截断，保证不溢出控件区域（颜色码在截断时丢弃）
            shown = CardLayout.ellipsize(MinecraftText.strip(shown), textRoom, FONT_SIZE);
            textWidth = FontRenderer.measureTextWidth(shown, FONT_SIZE);
        }
        float textX = alignLeft ? x : x + width - textWidth;
        if (drawIcon) {
            ItemIconCache.getInstance().draw(canvas, iconStack, textX - ICON_GAP - ICON_SIZE,
                y + (getHeight() - ICON_SIZE) / 2f, ICON_SIZE);
        }
        MinecraftText.draw(canvas, shown, textX, y + 14f, FONT_SIZE,
                ClickGuiThemeColors.current().secondaryText, alpha);
    }

    private String resolve() {
        try {
            String value = text.get();
            return value == null ? "" : value;
        } catch (Throwable ignored) {
            return UNAVAILABLE;
        }
    }
}
