package com.yiyiaddon.ui.widget;

import com.yiyiaddon.ui.render.FontRenderer;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import io.github.humbleui.skija.Canvas;
import net.minecraft.core.Vec3i;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 三维整数输入（X / Y / Z 三个小框并排，带轴名）。
 *
 * <p>Baritone 的偏移类设置是 {@link Vec3i}（重复建造偏移等），原来在界面上没有任何修改方式，
 * 只能看到 {@code Vec3i{x=0, y=0, z=0}}。这里给它一个能改的控件：每个轴一个整数框，
 * 轴名单画在框左侧；聚焦时保留用户原文（允许 {@code -}、{@code 1} 这类中间态），
 * 失控时归一化并写回。</p>
 *
 * <p>宽度按「三格」算，因此折叠双列布局里会独占一行——三个框并排比拆成三行更好读。</p>
 */
public final class SettingVec3Box extends SettingWidget {

    /** 轴名宽度 */
    private static final float LETTER_W = 9f;
    /**
     * 单个整数框宽度。
     *
     * <p>三个框并排后总宽刻意超过紧凑双列单格的可用宽度（200），因此这一项<b>始终独占整行</b>：
     * 三个数值框挤进半列会把标题压掉（实机反馈「重复建造偏移」被值框压住），而且半列里三个框也太窄。</p>
     */
    private static final float BOX_W = 58f;
    /** 轴与轴之间的间距 */
    private static final float GAP = 10f;
    private static final float HEIGHT = 24f;
    private static final int EDIT_MAX_LENGTH = 12;
    private static final String[] AXES = {"X", "Y", "Z"};

    private final Supplier<Vec3i> getter;
    private final Consumer<Vec3i> setter;
    private final AxisBox[] boxes = new AxisBox[AXES.length];

    public SettingVec3Box(Supplier<Vec3i> getter, Consumer<Vec3i> setter) {
        this.getter = getter;
        this.setter = setter;
        for (int axis = 0; axis < AXES.length; axis++) {
            boxes[axis] = new AxisBox(axis);
        }
    }

    @Override public float getWidth() {
        return AXES.length * (LETTER_W + BOX_W) + (AXES.length - 1) * GAP;
    }

    @Override public float getHeight() {
        return HEIGHT;
    }

    @Override
    public void draw(Canvas canvas, float x, float y, float alpha) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        for (int axis = 0; axis < AXES.length; axis++) {
            float boxX = x + axis * (LETTER_W + BOX_W + GAP);
            FontRenderer.drawText(canvas, AXES[axis], boxX, y + 15.5f, 10f,
                    withAlpha(tc.labelTertiary, alpha));
            boxes[axis].draw(canvas, boxX + LETTER_W, y, alpha);
        }
    }

    @Override
    public void update(float dt) {
        for (AxisBox box : boxes) box.update(dt);
    }

    @Override
    public boolean isAnimating() {
        for (AxisBox box : boxes) {
            if (box.isAnimating()) return true;
        }
        return false;
    }

    @Override
    public boolean onClick(float mx, float my, float x, float y, int button) {
        for (int axis = 0; axis < AXES.length; axis++) {
            float boxX = x + axis * (LETTER_W + BOX_W + GAP) + LETTER_W;
            if (boxes[axis].onClick(mx, my, boxX, y, button)) return true;
        }
        return false;
    }

    @Override
    public boolean onDrag(float mx, float my, float x, float y) {
        for (int axis = 0; axis < AXES.length; axis++) {
            float boxX = x + axis * (LETTER_W + BOX_W + GAP) + LETTER_W;
            if (boxes[axis].onDrag(mx, my, boxX, y)) return true;
        }
        return false;
    }

    /**
     * 单轴整数框。
     *
     * <p>与 {@link SettingNumberBox} 同一套做法：聚焦时用私有 {@code edit} 存用户原文，
     * 因为「-」「1」这类中间态解析不出整数，直接回读设置值会让光标下的字自己跳回去。
     * 每次能解析出整数就立刻写回，失控时丢弃原文、回到设置值。</p>
     */
    private final class AxisBox extends SettingTextBox {

        private final int axis;
        private String edit;
        private boolean wasFocused;

        private AxisBox(int axis) {
            super(() -> "", value -> { }, EDIT_MAX_LENGTH);
            this.axis = axis;
            width(BOX_W);
        }

        private int current() {
            Vec3i value = getter.get();
            if (value == null) return 0;
            return switch (axis) {
                case 0 -> value.getX();
                case 1 -> value.getY();
                default -> value.getZ();
            };
        }

        private void push(int value) {
            Vec3i base = getter.get();
            if (base == null) base = Vec3i.ZERO;
            setter.accept(switch (axis) {
                case 0 -> new Vec3i(value, base.getY(), base.getZ());
                case 1 -> new Vec3i(base.getX(), value, base.getZ());
                default -> new Vec3i(base.getX(), base.getY(), value);
            });
        }

        @Override protected String getValue() {
            return edit != null ? edit : Integer.toString(current());
        }

        @Override protected void setValue(String value) {
            edit = trimToMaxLength(value == null ? "" : value);
            Integer parsed = parseInt(edit);
            if (parsed != null) push(parsed);
        }

        @Override
        public void update(float dt) {
            super.update(dt);
            boolean nowFocused = focused == this;
            if (nowFocused == wasFocused) return;
            wasFocused = nowFocused;
            edit = nowFocused ? Integer.toString(current()) : null;
        }

        /** 宽松解析：取串首可选符号 + 数字前缀，输入框里允许出现非数字尾巴时不写回。 */
        private static Integer parseInt(String text) {
            String source = text.strip();
            if (source.isEmpty()) return null;
            int index = 0;
            if (source.charAt(0) == '-' || source.charAt(0) == '+') index = 1;
            int start = index;
            while (index < source.length() && Character.isDigit(source.charAt(index))) index++;
            if (index == start) return null;
            try {
                return Integer.parseInt(source.substring(0, index));
            } catch (NumberFormatException ignored) {
                return null;
            }
        }
    }
}
