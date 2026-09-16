package com.yiyiaddon.feature.enchant.render;

import com.yiyiaddon.feature.enchant.EnchantModule;
import com.yiyiaddon.feature.enchant.model.EnchantPoint;
import com.yiyiaddon.feature.enchant.model.EnchantPointType;
import com.yiyiaddon.ui.render.world.EspRenderer;
import net.minecraft.client.Minecraft;

/**
 * 附魔 ESP：点位浮动标签。
 *
 * <p>门控与标签逐字来自旧项目 {@code onRender2D}（{@code :1594-1603}）：</p>
 * <ul>
 *   <li>门控 {@code !ESP标点.get() || !matchesCurrentPointContext()} → 不画；</li>
 *   <li><b>只画 6 个点位</b>（书本箱 / 青金石箱 / 成品箱 / 附魔台 / 砂轮 / 挂机位，顺序照旧）；
 *       <b>GEAR 专属的三个点位（工具护甲箱 / 铁砧 / 铁砧箱）旧项目不渲染</b>——
 *       {@link EnchantPointType#espLabel()} 为 {@code null} 的天然被跳过，不得自行补标签；</li>
 *   <li>文案与颜色走 {@link EnchantPointType#espLabel()} / {@link EnchantPointType#espColor()}
 *       （颜色 RGBA 与旧 {@code new Color(80,230,160,200)} 这一类逐字一致）；</li>
 *   <li>标签落点为「方块中心 + 上方 1.5 格」（旧 {@code pos.getX()+0.5, pos.getY()+1.5, pos.getZ()+0.5}）。</li>
 * </ul>
 *
 * <p>渲染通道沿用本项目 {@code ui/render/world} 的世界叠加层（与
 * {@code feature/mining/render/MiningPointRenderer} 同一做法）：字号固定，其余（显示距离、
 * 透明度、文字底板、图元预算）走「ESP 全局设置」页，本类不另建第二套渲染配置。</p>
 */
public final class EnchantPointRenderer {

    /** 标签字号（GUI 缩放坐标）；旧项目为固定 0.6 倍字号，本项目与挖矿 ESP 字牌同量级 */
    private static final float LABEL_SIZE = 2.0f;

    /** 标签高度偏移：方块顶面以上 1.5 格（旧项目 {@code pos.getY() + 1.5}） */
    private static final double LABEL_Y_OFFSET = 1.5;

    /** 旧项目 {@code onRender2D} 的渲染顺序：书本箱 → 青金石箱 → 成品箱 → 附魔台 → 砂轮 → 挂机位 */
    private static final EnchantPointType[] RENDERED = {
        EnchantPointType.BOOK_STORAGE,
        EnchantPointType.LAPIS_STORAGE,
        EnchantPointType.OUTPUT_STORAGE,
        EnchantPointType.ENCHANTING_TABLE,
        EnchantPointType.GRINDSTONE,
        EnchantPointType.AFK
    };

    private final Minecraft mc = Minecraft.getInstance();
    private final EnchantModule module;

    public EnchantPointRenderer(EnchantModule module) {
        this.module = module;
    }

    /** 每帧渲染（由模块注册到世界渲染层驱动） */
    public void render(EspRenderer renderer) {
        if (mc.player == null || mc.level == null) return;
        if (!module.settings().espPoints || !module.pointStore().matchesCurrentContext()) return;

        for (EnchantPointType type : RENDERED) {
            draw(renderer, type);
        }
    }

    private void draw(EspRenderer renderer, EnchantPointType type) {
        String label = type.espLabel();
        if (label == null) return;
        EnchantPoint point = module.pointStore().get(type);
        if (point == null) return;

        renderer.text(label,
            point.x() + 0.5, point.y() + LABEL_Y_OFFSET, point.z() + 0.5,
            LABEL_SIZE, type.espColor(), 1f, true);
    }
}
