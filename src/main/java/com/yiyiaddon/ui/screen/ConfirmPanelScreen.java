package com.yiyiaddon.ui.screen;

import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.widget.Button;
import net.minecraft.client.gui.screens.Screen;

import java.util.List;

/**
 * 通用二次确认窗口：正文逐行原样渲染，底部「确认 / 取消」等宽按钮。
 *
 * <p>用于不可逆操作（清空数据、删除记录）。正文支持 Minecraft 颜色码，因此旧项目的多行危险
 * 提示可以逐行照搬。确认按钮默认使用危险变体，取消按钮关闭窗口。</p>
 */
public final class ConfirmPanelScreen extends PanelScreen {

    private final List<String> lines;
    private final String confirmLabel;
    private final Runnable onConfirm;

    /**
     * @param windowTitle  窗口标题
     * @param lines        正文行，支持颜色码，逐行渲染
     * @param confirmLabel 确认按钮文案
     * @param onConfirm    确认后的动作；窗口随后自动关闭
     * @param parent       上级屏幕
     */
    public ConfirmPanelScreen(String windowTitle, List<String> lines, String confirmLabel,
                              Runnable onConfirm, Screen parent) {
        super(windowTitle, parent);
        exitToGame();
        this.lines = lines == null ? List.of() : List.copyOf(lines);
        this.confirmLabel = confirmLabel == null ? "确认" : confirmLabel;
        this.onConfirm = onConfirm;
        buildContent();
    }

    private void buildContent() {
        for (String line : lines) {
            content().add(new TextLine(line));
        }
        addGap();
        addDivider();
        addGap();
        addButtons(
                new Button(confirmLabel, this::confirm).danger(),
                new Button("§7取消", this::requestClose));
    }

    private void confirm() {
        Runnable action = onConfirm;
        requestClose();
        if (action != null) action.run();
    }
}
