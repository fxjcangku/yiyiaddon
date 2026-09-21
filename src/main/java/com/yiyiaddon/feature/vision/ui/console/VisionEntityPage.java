package com.yiyiaddon.feature.vision.ui.console;

import com.yiyiaddon.feature.vision.VisionModule;
import com.yiyiaddon.feature.vision.config.VisionSettings;
import com.yiyiaddon.feature.vision.config.VisionTexts;
import com.yiyiaddon.feature.vision.ui.VisionSelectors;
import com.yiyiaddon.ui.SelectionReceipt;
import com.yiyiaddon.ui.component.CompactStack;

/**
 * 透视控制台「实体」页：实体模式的全部设置（7 行）。
 *
 * <p>与 {@link VisionBlockPage} 一一对应，两页共用 {@link VisionConsoleScreen} 的行构件；
 * 用户口径「两个模式可以同时开启互不干扰」在这里体现为：各自的开关、名单、范围、框 / 射线与颜色
 * 互不影响。</p>
 */
public final class VisionEntityPage {

    private final VisionConsoleScreen owner;
    private final VisionModule module;

    public VisionEntityPage(VisionConsoleScreen owner, VisionModule module) {
        this.owner = owner;
        this.module = module;
    }

    /** 装配本页内容。 */
    public void build(CompactStack stack) {
        VisionSettings settings = module.settings();
        VisionSettings defaults = VisionConsoleScreen.DEFAULTS;

        stack.add(owner.toggleRow(VisionTexts.NAME_ENTITY_ENABLED, VisionTexts.DESC_ENTITY_ENABLED,
            () -> settings.entityEnabled, value -> settings.entityEnabled = value,
            () -> defaults.entityEnabled));

        stack.add(owner.listRow(VisionTexts.NAME_ENTITY_TARGETS, VisionTexts.DESC_ENTITY_TARGETS,
            () -> VisionSelectors.entityStatusText(module),
            () -> VisionSelectors.openEntitySelector(owner, module),
            () -> VisionSelectors.clearEntityTargets(module),
            () -> {
                settings.entityTargets.clear();
                settings.entityTargets.addAll(defaults.entityTargets);
                module.persistSettings();
                owner.reload();
                SelectionReceipt.reset(settings.entityTargets.size());
            }));

        stack.add(owner.rangeRow(VisionTexts.NAME_ENTITY_RANGE, VisionTexts.DESC_ENTITY_RANGE,
            () -> settings.entityRange, value -> settings.entityRange = value,
            () -> defaults.entityRange));

        stack.add(owner.toggleRow(VisionTexts.NAME_ENTITY_BOX, VisionTexts.DESC_ENTITY_BOX,
            () -> settings.entityBox, value -> settings.entityBox = value,
            () -> defaults.entityBox));

        stack.add(owner.toggleRow(VisionTexts.NAME_ENTITY_TRACER, VisionTexts.DESC_ENTITY_TRACER,
            () -> settings.entityTracer, value -> settings.entityTracer = value,
            () -> defaults.entityTracer));

        stack.add(owner.shapeRow(VisionTexts.NAME_ENTITY_SHAPE, VisionTexts.DESC_ENTITY_SHAPE,
            () -> settings.entityShapeMode, value -> settings.entityShapeMode = value,
            () -> defaults.entityShapeMode.index()));

        stack.add(owner.colorRow(VisionTexts.NAME_ENTITY_COLOR, VisionTexts.DESC_ENTITY_COLOR,
            settings.entityColor, defaults.entityColor));
    }
}
