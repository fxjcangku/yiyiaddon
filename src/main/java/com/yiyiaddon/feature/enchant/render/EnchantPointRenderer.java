package com.yiyiaddon.feature.enchant.render;

import com.yiyiaddon.feature.enchant.EnchantModule;
import com.yiyiaddon.feature.enchant.model.EnchantPoint;
import com.yiyiaddon.feature.enchant.model.EnchantPointType;
import com.yiyiaddon.ui.render.world.EspRenderObject;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.PointLabelText;
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
 *   <li>标签落点为「方块中心 + 上方 1.5 格」（旧 {@code pos.getX()+0.5, pos.getY()+1.5, pos.getZ()+0.5}）。</li>
 * </ul>
 *
 * <p><b>显示 / 字号独立可配</b>（用户 2026-09-19：「所有标点选择点位位置的模块 参照星露谷农场的
 * 点位设置」）：六个点位各有自己的 {@link EspRenderObject}，先判它的 {@code show} 再画
 * （对象颜色管那个点位的方框）；字号取
 * {@link com.yiyiaddon.feature.enchant.config.EnchantSettings#labelSize}；<b>字牌本身</b>的样式统一走
 * {@link PointLabelText}（加粗 + UI 主题色 + 底板），内容一律「[世界]名字[距离]」。
 * {@code ESP标点} 仍是总开关：关掉它六个字牌一起不画。</p>
 *
 * <p><b>为什么字牌颜色不再取点位自己的颜色</b>：用户 2026-09-19 的口径是字牌跟随界面主题
 * （换主题同步切换）；点位颜色设置仍然决定那个点位的方框颜色，两件事互不牵扯。文案里自带的
 * {@code §} 色码同样在 {@link PointLabelText} 里被剥掉，否则会盖掉主题色。</p>
 *
 * <p>渲染通道沿用本项目 {@code ui/render/world} 的世界叠加层（与
 * {@code feature/mining/render/MiningPointRenderer} 同一做法）：其余（显示距离、文字底板、图元预算）
 * 走「ESP 全局设置」页，本类不另建第二套渲染配置。</p>
 */
public final class EnchantPointRenderer {

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
        EspRenderObject object = renderObjectOf(type);
        if (object == null || !object.show) return;
        EnchantPoint point = module.pointStore().get(type);
        if (point == null) return;

        // 样式统一走共用件 PointLabelText（加粗 + UI 主题色 + 底板 + 居中）；字号取设置里的「字牌大小」。
        // 内容一律「[世界]名字[距离]」—— 用户 2026-09-19 定稿：「全都要标上，除了那两个农场的选点区域
        // 之外都要标上」，本模块六个点位全部照标。
        double x = point.x() + 0.5;
        double y = point.y() + LABEL_Y_OFFSET;
        double z = point.z() + 0.5;
        PointLabelText.containerLabel(renderer, label, currentDimensionId(), x, y, z,
            (float) module.settings().labelSize);
    }

    /** 当前世界维度键（点位按当前上下文渲染，故维度就是玩家所在维度；未进世界返回 {@code null}） */
    private String currentDimensionId() {
        return mc.level == null ? null : mc.level.dimension().identifier().toString();
    }

    /**
     * 该点位类型对应的渲染对象：按对象名（= {@link EnchantPointType#title()}）在设置里取同一项。
     *
     * <p>对象名同时是落盘键前缀，模块内唯一且稳定，因此按名匹配不会取错；取不到时返回 {@code null}，
     * 该点位当帧不画（等价于关闭），不会因为缺一项就画出别的点位的颜色。</p>
     */
    private EspRenderObject renderObjectOf(EnchantPointType type) {
        for (EspRenderObject object : module.settings().renderObjects()) {
            if (object.name().equals(type.title())) return object;
        }
        return null;
    }
}
