package com.yiyiaddon.ui.render.world;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * 瞄准方块高亮：准星指向的方块画一圈整格描边（用户 2026-09-18：「瞄准方块默认带白色的框」）。
 *
 * <p>纯客户端视觉，与原版那圈细黑描边叠加——原版描边很细、暗背景下几乎看不见，
 * 这里补一圈可自定颜色的整格框，方便对准方块（搭路、开箱、挖矿时判断瞄的是哪一格）。</p>
 *
 * <p><b>常驻挂载</b>：不属于任何业务模块，由 {@code YiyiAddonClient#onInitializeClient} 注册一次
 * （不是模块开关驱动），因此模块全关时也能用；总开关在「ESP 全局设置 ▸ 外观」里，默认开。</p>
 *
 * <p><b>只画描边不画面</b>：填充面会挡住方块本体与准星，观感很差；颜色取设置里的 RGB，
 * alpha 固定不透明，透明度 / 线宽仍归 ESP 全局层统一管（{@link EspGlobalSettings}）。</p>
 */
public final class BlockOutlineRenderer {

    /** 绘制层 id（常驻注册，全局唯一） */
    public static final String LAYER_ID = "global:block-outline";

    /** 描边线宽（GUI 缩放坐标；实际线宽再乘全局「线宽倍率」） */
    private static final float LINE_THICKNESS = 2.0f;

    private BlockOutlineRenderer() {
    }

    /** 世界几何阶段回调（由 {@link WorldOverlay} 驱动） */
    public static void render(EspRenderer renderer) {
        EspGlobalSettings settings = EspGlobalSettings.get();
        if (!settings.blockOutline()) return;
        // 自动挖矿运行期间不画：准星随挖掘目标到处扫，白框会一直闪（用户 2026-09-18）
        if (settings.autoMinerRunning()) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;
        // 准星命中必须是方块：实体 / 未命中一律不画
        if (!(mc.hitResult instanceof BlockHitResult hit) || hit.getType() != HitResult.Type.BLOCK) return;

        BlockPos pos = hit.getBlockPos();
        if (mc.level.getBlockState(pos).isAir()) return;

        int argb = 0xFF000000 | (settings.blockOutlineColor() & 0xFFFFFF);
        renderer.blockBox(pos.getX(), pos.getY(), pos.getZ(), 0, argb, ShapeMode.Lines, LINE_THICKNESS);
    }
}
