package com.yiyiaddon.feature.autofarm.render;

import com.yiyiaddon.feature.autofarm.AutoFarmModule;
import com.yiyiaddon.feature.autofarm.model.FarmSite;
import com.yiyiaddon.feature.autofarm.model.SiteType;
import com.yiyiaddon.feature.autofarm.task.FarmTask;
import com.yiyiaddon.feature.autofarm.task.HarvestTask;
import com.yiyiaddon.feature.autofarm.task.PlantTask;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.ShapeMode;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

/**
 * 自动农场世界渲染层：只画农场边界、当前作业目标与六点位防呆字牌，不参与任何业务决策。
 *
 * <p>对应旧 {@code FarmRenderer} 的三项能力，绘制路线换成本项目既定的
 * 「投影 + Gizmos + Skija 字牌」基建（开发习惯第 147 条）：</p>
 * <ul>
 *   <li>边界：只画外框一圈（{@code Lines}，不填面），大农场也不卡（旧 {@code renderBorder}）；</li>
 *   <li>目标：当前任务的目标方块高亮（线 + 面，旧 {@code renderTarget}）；</li>
 *   <li>字牌：六点位头顶的防呆标签（白字 + 底板，旧 {@code renderLabel} 的
 *       {@code NametagUtils} 等价物，走 {@link EspRenderer#text}）。</li>
 * </ul>
 *
 * <p>颜色全部经模块的 {@code EspColor}（调色板唯一入口，第 146 条），RGB 解析在绘制瞬间完成
 * （彩虹色支持随 EspColor 免费获得）。</p>
 */
public final class FarmRenderer {

    /** 字牌字号（GUI 缩放坐标）：与挖矿点位字牌同一档 */
    private static final float LABEL_SIZE = 10f;

    private final AutoFarmModule module;

    public FarmRenderer(AutoFarmModule module) {
        this.module = module;
    }

    /** 由 {@link com.yiyiaddon.ui.render.world.WorldOverlay} 每帧回调（模块启用时注册） */
    public void renderLayer(EspRenderer renderer) {
        var scanner = module.scanner();
        var settings = module.settings();

        // 农场边界（只画外框一圈，不渲染面）
        if (settings.renderBounds && scanner.bounded()) {
            BlockPos min = scanner.min();
            BlockPos max = scanner.max();
            AABB box = new AABB(
                min.getX(), min.getY(), min.getZ(),
                max.getX() + 1.0, max.getY() + 1.0, max.getZ() + 1.0);
            int color = settings.boundsColor.argb();
            renderer.box(box, color, color, ShapeMode.Lines, 1f);
        }

        // 当前作业目标：收割定位作物本体；补种定位作物落点（底盘上方一格）
        if (settings.renderTarget) {
            FarmTask task = module.controller().currentTask();
            BlockPos target = null;
            if (task instanceof HarvestTask harvest) target = harvest.target().pos();
            else if (task instanceof PlantTask plant) target = plant.target().pos().above();
            if (target != null) {
                int argb = settings.targetColor.argb();
                renderer.blockBox(target.getX(), target.getY(), target.getZ(), argb, argb, ShapeMode.Both, 1f);
            }
        }

        // 六点位防呆字牌（只在绑定且位于当前维度时画，文案与颜色逐字照旧）
        if (settings.renderLabels) {
            label(renderer, SiteType.START, "§a农场点位1");
            label(renderer, SiteType.END, "§e农场点位2");
            label(renderer, SiteType.SINGLE_STORAGE, "§6单作物箱");
            label(renderer, SiteType.MULTI_STORAGE, "§d多作物箱");
            label(renderer, SiteType.SEED_STORAGE, "§b种子补货箱");
            label(renderer, SiteType.POISON_STORAGE, "§c杂物箱");
        }
    }

    /** 画单个点位头顶字牌：颜色固定白色（旧 renderLabel 传入 255,255,255） */
    private void label(EspRenderer renderer, SiteType type, String text) {
        FarmSite site = module.site(type);
        if (site == null || !site.inCurrentDimension()) return;
        BlockPos pos = site.pos();
        renderer.text(text, pos.getX() + 0.5, pos.getY() + 1.4, pos.getZ() + 0.5,
            LABEL_SIZE, 0xFFFFFF, 1f, true);
    }
}
