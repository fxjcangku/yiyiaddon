package com.yiyiaddon.feature.bonemeal.render;

import com.yiyiaddon.feature.bonemeal.AutoBoneMealModule;
import com.yiyiaddon.feature.bonemeal.config.BonemealSettings;
import com.yiyiaddon.ui.render.world.EspGlobalSettings;
import com.yiyiaddon.ui.render.world.EspRenderer;
import net.minecraft.core.BlockPos;

/**
 * 自动骨粉的候选目标 ESP：给本刻扫描到的全部候选方块画边界框。
 *
 * <p><b>逐字照旧项目</b> {@code bonemeal/AutoBoneMeal.java:634-640} 的 {@code onRender3D}：
 * 首判据（{@code 启用ESP} 关或候选为空即整层不画）、逐个候选画整格盒、盒色「填充在前、描边在后」、
 * 形状模式直接取设置值、不排除任何面（{@code excludeDir = 0}）——全部一字未改。</p>
 *
 * <p><b>框架适配（旧 → 新）</b>：旧的 {@code Render3DEvent.renderer.box(BlockPos, side, line,
 * shapeMode, 0)} → {@link EspRenderer#blockBox}（盒子类参数顺序仍是「先填充色、后描边色」，
 * 第 160 条）；旧的线宽参数 {@code 0}（旧框架默认线宽）→ 本项目的固定线宽常量，取值与本批其它
 * 模块（{@code PacketBreakRenderer} / {@code TeleportRenderer}）一致。</p>
 *
 * <p><b>颜色</b>：按第 146 条一律走 {@code ColorPresets} 预设（默认绿），模块设置里持有
 * {@code EspColor}，绘制瞬间解析，模块不需要额外的变色计时器（第 150 条）。</p>
 *
 * <p><b>注册 / 注销</b>：随模块开关走（模块 {@code onEnable} 注册、{@code onDisable} 注销），
 * 禁止常驻注册（第 142 条）。</p>
 *
 * <p><b>ESP 总闸</b>：本层已挂进「ESP 全局设置 → 各模块 ESP → 自动骨粉」
 * （{@code EspGlobalSettings.Layer.BONE_MEAL}，按 [76 号](../../../../../02-开发报告/项目开发报告/76-复盘-新增模块接入ESP总闸的步骤与加固.md)
 * 三步落地：枚举项 + {@code EspSettingsPage.layerHint} case + 渲染入口判断）。</p>
 */
public final class BoneMealEspRenderer {

    /** 线框线宽（GUI 缩放坐标）：与 {@code PacketBreakRenderer} / {@code TeleportRenderer} 同值 */
    private static final float LINE_THICKNESS = 1.5f;

    private final AutoBoneMealModule module;

    public BoneMealEspRenderer(AutoBoneMealModule module) {
        this.module = module;
    }

    /** 每帧渲染（由模块注册到世界渲染层驱动） */
    public void render(EspRenderer renderer) {
        // 全局「各模块 ESP」总闸（ESP 全局设置页可一处关掉）
        if (!EspGlobalSettings.get().layerEnabled(EspGlobalSettings.Layer.BONE_MEAL)) return;

        BonemealSettings settings = module.settings();
        // 旧 onRender3D 首判据：模块自己的开关关掉、或本刻没有候选目标，整层不画
        if (!settings.espEnabled || module.candidates().isEmpty()) return;
        if (renderer == null) return;

        for (BlockPos pos : module.candidates()) {
            renderer.blockBox(pos.getX(), pos.getY(), pos.getZ(),
                settings.fillColor, settings.lineColor, settings.shapeMode, LINE_THICKNESS, 0);
        }
    }
}
