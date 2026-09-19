package com.yiyiaddon.feature.vision.ui.console;

import com.yiyiaddon.feature.vision.VisionModule;
import com.yiyiaddon.feature.vision.config.VisionSettings;
import com.yiyiaddon.feature.vision.config.VisionTexts;
import com.yiyiaddon.feature.vision.ui.VisionSelectors;
import com.yiyiaddon.ui.component.CompactStack;

/**
 * 透视控制台「方块」页：方块模式的全部设置（7 行）。
 *
 * <p>行构件由 {@link VisionConsoleScreen} 统一提供（开关 / 范围 / 颜色 / 样式 / 名单），本页只负责
 * 「搬哪几行、按什么顺序排」，不新增任何设置项、不改默认值与取值域（第 181 / 183 条）。</p>
 */
public final class VisionBlockPage {

    private final VisionConsoleScreen owner;
    private final VisionModule module;

    public VisionBlockPage(VisionConsoleScreen owner, VisionModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        VisionSettings settings = module.settings();
        VisionSettings defaults = VisionConsoleScreen.DEFAULTS;

        stack.add(owner.toggleRow(VisionTexts.NAME_BLOCK_ENABLED, VisionTexts.DESC_BLOCK_ENABLED,
            () -> settings.blockEnabled, value -> settings.blockEnabled = value,
            () -> defaults.blockEnabled));

        stack.add(owner.listRow(VisionTexts.NAME_BLOCK_TARGETS, VisionTexts.DESC_BLOCK_TARGETS,
            () -> VisionSelectors.blockStatusText(module),
            () -> VisionSelectors.openBlockSelector(owner, module),
            () -> VisionSelectors.clearBlockTargets(module),
            () -> {
                settings.blockTargets.clear();
                settings.blockTargets.addAll(defaults.blockTargets);
                module.persistSettings();
                owner.reload();
            }));

        stack.add(owner.rangeRow(VisionTexts.NAME_BLOCK_RANGE, VisionTexts.DESC_BLOCK_RANGE,
            () -> settings.blockRange, value -> settings.blockRange = value,
            () -> defaults.blockRange));

        stack.add(owner.toggleRow(VisionTexts.NAME_BLOCK_BOX, VisionTexts.DESC_BLOCK_BOX,
            () -> settings.blockBox, value -> settings.blockBox = value,
            () -> defaults.blockBox));

        stack.add(owner.toggleRow(VisionTexts.NAME_BLOCK_TRACER, VisionTexts.DESC_BLOCK_TRACER,
            () -> settings.blockTracer, value -> settings.blockTracer = value,
            () -> defaults.blockTracer));

        stack.add(owner.shapeRow(VisionTexts.NAME_BLOCK_SHAPE, VisionTexts.DESC_BLOCK_SHAPE,
            () -> settings.blockShapeMode, value -> settings.blockShapeMode = value,
            () -> defaults.blockShapeMode.index()));

        stack.add(owner.colorRow(VisionTexts.NAME_BLOCK_COLOR, VisionTexts.DESC_BLOCK_COLOR,
            settings.blockColor, defaults.blockColor));
    }
}
