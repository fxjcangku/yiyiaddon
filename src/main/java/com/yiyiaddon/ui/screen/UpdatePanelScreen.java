package com.yiyiaddon.ui.screen;

import com.yiyiaddon.service.update.ReleaseCatalog;
import com.yiyiaddon.service.update.UpdateService;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.widget.Button;
import net.minecraft.client.gui.screens.Screen;

/** 版本更新提示：复用玻璃面板和开合动画；两个选项均保留原界面并结束本次自动提醒。 */
public final class UpdatePanelScreen extends PanelScreen {
    private String saveStatus = "";
    public UpdatePanelScreen(ReleaseCatalog.Release release, Screen parent) {
        super("发现新版本", parent);
        setSubtitle(() -> "yiyiaddon · 版本更新");
        addSectionTitle(release.tag() + " · " + release.channel());
        addField("当前版本", UpdateService.versionLabel());
        addGap();
        content().add(new TextLine("新版本已发布，前往发布页查看更新并下载。"));
        content().add(new TextLine("选择稍后更新，本次启动不再提醒。"));
        content().add(new TextLine("跳过此版本后，仅在发布更高版本时提醒。"));
        addGap();
        addDivider();
        addGap();
        addButtons(new Button("立即更新", () -> {
            UpdateService.openRelease(release);
            requestClose();
        }).primary(), new Button("稍后更新", this::requestClose).secondary());
        addButtons(new Button("跳过此版本", () -> {
            if (UpdateService.skip(release)) requestClose();
            else saveStatus = "§c保存失败，请检查配置目录权限后重试。";
        }).ghost());
        content().add(new TextLine(() -> saveStatus).height(18f));
    }

    /** 紧凑通知尺寸；仍由公共面板统一计算屏幕缩放与输入坐标。 */
    @Override
    protected void applyDesignSize() {
        setPanelDesignSize(440f, 362f);
    }
}
