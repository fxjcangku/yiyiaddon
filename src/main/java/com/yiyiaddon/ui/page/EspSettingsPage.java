package com.yiyiaddon.ui.page;

import com.yiyiaddon.ui.UiText;
import com.yiyiaddon.ui.render.world.EspGlobalSettings;
import com.yiyiaddon.ui.render.world.EspGlobalSettings.ModeOverride;
import com.yiyiaddon.ui.render.world.EspGlobalSettings.OcclusionOverride;
import com.yiyiaddon.ui.widget.SettingCycle;
import com.yiyiaddon.ui.widget.SettingModule;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.List;

/**
 * ESP 全局设置：一套作用于<b>所有</b>世界 ESP 的公共选项。
 *
 * <p><b>与模块自己那套设置的分工：</b>模块自己管「画什么、什么颜色、什么模式」（星露谷点位渲染、
 * 自动箱子、ESP 测试项各自的页面）；这里管全局排版与开销——线宽、整体不透明度、显示距离、
 * 字号、单帧图元上限、以及「自己的 ESP 要不要画」。<b>本页不改任何模块的颜色设置</b>，
 * 所有倍率默认 1.0、覆盖项默认跟随各模块，因此不动它就等于没有这一层。</p>
 *
 * <p>改动即时写回 {@code config/yiyiaddon/esp-global.json}，渲染线程下一帧生效。</p>
 */
public final class EspSettingsPage extends BasePage {

    private final EspGlobalSettings settings = EspGlobalSettings.get();

    public EspSettingsPage() {
        // ── 总开关 ──
        SettingModule master = group("总开关", "一处关掉全部世界 ESP");
        master.addSub("ESP 总开关", "关掉后所有 ESP 绘制层整帧跳过：星露谷点位 / 自动箱子 / ESP 测试项都会消失",
            new SettingToggle(settings::enabled, settings::setEnabled));

        // ── 外观：怎么画 ──
        SettingModule look = group("外观", "线宽、渲染模式与透视；只改「怎么画」，不改模块自己的颜色");
        look.addSub("线宽倍率", "所有线框的粗细统一乘这个系数（0.5~3.0，默认 1.0）",
            new SettingNumberBox(EspGlobalSettings.THICKNESS_MIN, EspGlobalSettings.THICKNESS_MAX, 0.1, "%.1fx",
                () -> settings.thicknessScale(), settings::setThicknessScale));
        look.addSub("渲染模式覆盖", "默认跟随各模块；覆盖后全部 ESP 统一成线框 / 面 / 两者",
            new SettingCycle(List.of(ModeOverride.labels()), () -> settings.modeOverride().ordinal(),
                index -> settings.setModeOverride(ModeOverride.values()[index])));
        look.addSub("透视覆盖", "默认跟随各模块；「全部透视」穿墙也画，「全部遮挡」被方块挡住就不画",
            new SettingCycle(List.of(OcclusionOverride.labels()), () -> settings.occlusionOverride().ordinal(),
                index -> settings.setOcclusionOverride(OcclusionOverride.values()[index])));

        // ── 可见度：整体透明度与距离 ──
        SettingModule visibility = group("可见度", "整体不透明度与显示距离");
        visibility.addSub("不透明度倍率", "所有 ESP 颜色统一按这个百分比变淡（10%~100%，默认 100%）",
            new SettingNumberBox(10, 100, 5, "%.0f%%",
                () -> (double) settings.alphaScale(), value -> settings.setAlphaScale(value / 100d)));
        visibility.addSub("最远显示距离", "超过这个距离的 ESP 一律不画（0 = 不限，单位格）",
            new SettingNumberBox(EspGlobalSettings.DISTANCE_MIN, EspGlobalSettings.DISTANCE_MAX, 1, "%.0f",
                () -> (double) settings.maxDistance(), settings::setMaxDistance));
        visibility.addSub("距离淡出", "开启后从「淡出起点」开始随距离变淡，到最远距离刚好淡到看不见",
            new SettingToggle(settings::fade, settings::setFade));
        visibility.addSub("淡出起点", "这个距离以内完全不透明（未设最远距离时以 64 格为最远）",
            new SettingNumberBox(EspGlobalSettings.DISTANCE_MIN, EspGlobalSettings.DISTANCE_MAX, 1, "%.0f",
                () -> (double) settings.fadeStart(), settings::setFadeStart));

        // ── 文字 ──
        SettingModule text = group("文字", "字牌字号与底板（模块自己设的颜色不受影响）");
        text.addSub("文字大小倍率", "所有 ESP 字牌的字号统一乘这个系数（0.5~2.5，默认 1.0）",
            new SettingNumberBox(EspGlobalSettings.TEXT_SCALE_MIN, EspGlobalSettings.TEXT_SCALE_MAX, 0.1, "%.1fx",
                () -> (double) settings.textScale(), settings::setTextScale));
        text.addSub("文字底板", "字牌是否压一块深色底板；关掉只剩纯色字，亮背景下更难读",
            new SettingToggle(settings::textPlate, settings::setTextPlate));

        // ── 性能 ──
        SettingModule performance = group("性能", "成片 ESP 拖慢帧率时的保底");
        performance.addSub("每帧最多画多少个图形", "一帧最多画多少个图形，超出后本帧不再画（0 = 不限）；每个框、每条线、每个面、每段字各算一个",
            new SettingNumberBox(EspGlobalSettings.BUDGET_MIN, EspGlobalSettings.BUDGET_MAX, 16, "%.0f",
                () -> (double) settings.primitiveBudget(), settings::setPrimitiveBudget));

        // ── 自己 ──
        SettingModule self = group("自己", "本机玩家的 ESP 是否显示");
        self.addSub("隐藏自己（第一人称）", "第一人称下不画自己的 ESP（默认开启，避免糊在准星上挡视野）",
            new SettingToggle(settings::hideSelfFirstPerson, settings::setHideSelfFirstPerson));
        self.addSub("隐藏自己（第三人称）", "第三人称（F5）下也不画自己（默认关闭）",
            new SettingToggle(settings::hideSelfThirdPerson, settings::setHideSelfThirdPerson));
    }

    /** 建一个只有子项的设置分组。 */
    private SettingModule group(String title, String subtitle) {
        SettingModule module = new SettingModule(UiText.t(title, title), UiText.t(subtitle, subtitle), null);
        modules.add(module);
        return module;
    }

    @Override
    public String getTitle() {
        return UiText.t("ESP 全局设置", "ESP Global Settings");
    }

    @Override
    public String getSubtitle() {
        return UiText.t("作用于全部世界 ESP 的公共选项：线宽 / 透明度 / 距离 / 字号 / 开销",
            "Shared options for every world ESP: thickness, opacity, distance, text and budget");
    }
}
