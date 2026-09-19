package com.yiyiaddon.ui.component;

import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.Button;
import io.github.humbleui.skija.Canvas;

/** 首页右上角版本区：版本徽标与轻量入口；尺寸、悬停和按压复用统一按钮。 */
public final class VersionHeader {
    public static final float WIDTH = 252f;
    private static final float ACTION_Y = 23f;
    private static final float GAP = 6f;
    private final Button[] actions;

    public VersionHeader(Runnable openRepository, Runnable checkUpdate, Runnable feedback) {
        actions = new Button[] {
                new Button("项目仓库", openRepository).small().ghost(),
                new Button("检查更新", checkUpdate).small().secondary(),
                new Button("反馈问题", feedback).small().ghost()
        };
    }

    /** 与页面标题占据同一高度，右对齐按钮组；版本与检测状态分别限制可用宽度。 */
    public void draw(Canvas canvas, float x, float y, float alpha, float mx, float my, float dt,
                     String versionLabel, String status) {
        ClickGuiThemeColors tc = ClickGuiThemeColors.current();
        GlassPanel.fill(canvas, x, y, 151f, 19f, 6f, tc.accent, alpha * 0.10f);
        GlassPanel.stroke(canvas, x, y, 151f, 19f, 6f, tc.accent, alpha * 0.18f, 0.7f);
        MinecraftText.draw(canvas, MinecraftText.fit(versionLabel, 10f, 139f),
                x + 6f, y + 13f, 10f, tc.accent, alpha);
        MinecraftText.draw(canvas, MinecraftText.fit(status, 9f, 93f),
                x + 159f, y + 13f, 9f, tc.secondaryText, alpha);
        float bx = actionsX(x);
        for (Button action : actions) {
            action.hover(mx, my, bx, y + ACTION_Y, action.getWidth());
            action.update(dt);
            action.drawAt(canvas, bx, y + ACTION_Y, action.getWidth(), alpha);
            bx += action.getWidth() + GAP;
        }
    }

    public boolean onClick(float mx, float my, float x, float y, int button) {
        float bx = actionsX(x);
        for (Button action : actions) {
            if (action.onClick(mx, my, bx, y + ACTION_Y, button)) return true;
            bx += action.getWidth() + GAP;
        }
        return false;
    }

    private float actionsX(float x) {
        float total = GAP * (actions.length - 1);
        for (Button action : actions) total += action.getWidth();
        return x + WIDTH - total;
    }

}
