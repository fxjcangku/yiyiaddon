package com.yiyiaddon.feature.mining.render;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.fastbreak.MiningFastBreakController;
import com.yiyiaddon.ui.render.world.EspGlobalSettings;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.ShapeMode;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

/**
 * 挖掘进度 ESP：正在发包破坏的方块显示「百分比标签 + 随进度向中心收缩的框」。
 *
 * <p>用户 2026-09-17 需求（参考旧项目发包秒破模块的「进度显示」与旧框架的渲染语言）：
 * 挖掘时要看得出进度，百分比 + 方块缩放，直观、有高级感。数据源是本项目秒破状态机
 * {@link MiningFastBreakController} 对外只读暴露的 {@code targetPos() / progress()}，
 * 进度按<b>服务端同源公式</b>算出（不是客户端自己插值的动画），因此看到的百分比就是
 * 服务端此刻认定的破坏进度。</p>
 *
 * <p>观感口径（与旧项目秒破模块一致）：</p>
 * <ul>
 *   <li>未完成：红色系（面 {@code 0xCC2020 @40} / 线 {@code 0xCC2020 @255}，线+面），
 *       框从满格随进度向方块中心收缩，100% 时回到满格；</li>
 *   <li>已完成：绿色系（面 {@code 0x20CC50 @50} / 线 {@code 0x20CC50 @255}），不再收缩；</li>
 *   <li>标签：方块顶面以上 {@code 0.35} 格的居中百分比文字，字号 = 模块 ESP 字号
 *       （{@code MiningSettings#espScale}）× {@link #LABEL_SCALE}（1.5，用户 2026-09-18
 *       反馈「百分比太小不明显」后放大），带底板保证亮背景下可读。</li>
 * </ul>
 *
 * <p><b>范围（用户 2026-09-17 明确要求）</b>：自动挖矿期间<b>被破坏的每一个方块</b>都显示进度，
 * 不限于矿石——石头、泥土、垫脚方块、连锁目标一样显示，接管道只有一条：
 * 秒破的单槽发包状态机（原版 / Baritone 的破坏入口经 Mixin 接入）。</p>
 *
 * <p><b>完成残影</b>：软方块在秒破下常常 START / STOP 同刻完成，活跃窗口只有 1~2 刻
 * （约 50~100ms），渲染帧经常一帧都赶不上。所以破坏后再补显示 {@link #FINISH_LINGER_TICKS} 刻的
 * 完成态（绿色满格 100%）并线性淡出，确保每次破坏都看得见进度。</p>
 *
 * <p>渲染走本项目统一的世界 ESP 层（{@link EspRenderer}），因此线宽 / 不透明度 / 显示距离 /
 * 遮挡口径全部沿用「ESP 全局设置」页，本类不另建第二套渲染配置。</p>
 */
public final class MiningBreakProgressRenderer {

    /** 超过这个距离不画（格）：只在玩家视野附近的目标上有意义 */
    private static final double RENDER_DISTANCE = 64.0;

    /**
     * 线框线宽（GUI 缩放坐标）：比普通 ESP 粗一档，用户反馈「框不明显」（2026-09-18）。
     * 同日第二轮：点位框 1.5 → 3.0 后本项同步 3.0 → 4.0，保持「进度框比点位框更醒目」的层次。
     */
    private static final float LINE_THICKNESS = 4.0f;

    /** 标签高度偏移：方块顶面以上 0.35 格 */
    private static final double LABEL_Y_OFFSET = 1.35;

    /** 百分比字号在模块 ESP 字号上的额外倍率（用户反馈「百分比太小不明显」） */
    private static final float LABEL_SCALE = 1.5f;

    /** 挖掘中面的透明度（描边固定 {@link #LINE_ALPHA}）：数值偏亮，保证暗处/矿洞里也看得见 */
    private static final int BUSY_SIDE_ALPHA = 70;

    /** 完成态面的透明度 */
    private static final int READY_SIDE_ALPHA = 90;

    /** 描边透明度 */
    private static final int LINE_ALPHA = 255;

    /** 挖掘中标签文字色（固定白，红框上最清晰；框色可由用户设置） */
    private static final int BUSY_TEXT = 0xFFFFFFFF;

    /**
     * 「完成残影」时长（刻）。
     *
     * <p>秒破对软方块是同刻完成（START / STOP 同一 tick），活跃窗口只有 1~2 刻，
     * 渲染帧经常一帧都赶不上——方块被挖掉了却看不到进度。破坏后再补显示这么久并淡出，
     * 保证「自动挖矿时不管挖什么方块都有进度显示」（用户 2026-09-17）。</p>
     */
    private static final int FINISH_LINGER_TICKS = 12;

    private final Minecraft mc = Minecraft.getInstance();
    private final AutoMinerModule module;

    public MiningBreakProgressRenderer(AutoMinerModule module) {
        this.module = module;
    }

    /** 每帧渲染（由模块注册到世界渲染层驱动） */
    public void render(EspRenderer renderer) {
        // 全局「各模块 ESP」总闸（用户 2026-09-18）：与上面那条模块自己的开关是「全局优先」关系
        if (!EspGlobalSettings.get().layerEnabled(EspGlobalSettings.Layer.MINING)) return;
        if (mc.player == null || mc.level == null) return;
        // 独立开关（用户 2026-09-18）：只控制这条进度显示，关掉不影响秒破与挖矿行为；
        // 也不依赖「秒破」开关本身，方便单独关掉视觉噪音
        MiningSettings settings = module.settings();
        if (!settings.breakProgressEsp) return;

        MiningFastBreakController controller = MiningFastBreakController.instance();

        BlockPos pos;
        float progress;
        boolean ready;
        float fade = 1f;
        if (controller.isActive() && controller.targetPos() != null) {
            // 正在发包破坏的方块：不管它是矿石、泥土还是垫脚石，全部显示进度
            pos = controller.targetPos();
            progress = controller.progress();
            ready = progress >= 1f;
        } else {
            // 完成残影：软方块被同刻挖掉时上面那段活跃窗口只有 1~2 刻，渲染帧常常赶不上，
            // 这里在破坏后补显示 FINISH_LINGER_TICKS 刻的完成态并线性淡出，保证每块都看得到
            pos = controller.lastBrokenPos();
            if (pos == null) return;
            int age = mc.player.tickCount - controller.lastBrokenTick();
            if (age < 0 || age > FINISH_LINGER_TICKS) return;
            progress = 1f;
            ready = true;
            fade = 1f - age / (float) (FINISH_LINGER_TICKS + 1);
        }

        double centerX = pos.getX() + 0.5;
        double centerZ = pos.getZ() + 0.5;
        double distance = mc.player.position().distanceTo(new Vec3(centerX, pos.getY() + 0.5, centerZ));
        if (distance > RENDER_DISTANCE) return;

        // 向中心收缩：0% 满格 → 100% 缩到中心（完成态回到满格并转色，与旧项目一致）
        double shrink = ready ? 0.0 : Math.max(0f, Math.min(1f, progress)) * 0.5;
        AABB box = new AABB(pos.getX(), pos.getY(), pos.getZ(),
            pos.getX() + 1.0, pos.getY() + 1.0, pos.getZ() + 1.0).deflate(shrink);

        // 颜色取用户设置（RGB），面 / 描边透明度由本类固定，fade 用于完成残影的淡出
        int rgb = (ready ? settings.breakProgressReadyColor : settings.breakProgressBusyColor) & 0xFFFFFF;
        renderer.box(box,
            argb(rgb, fade, ready ? READY_SIDE_ALPHA : BUSY_SIDE_ALPHA),
            argb(rgb, fade, LINE_ALPHA), ShapeMode.Both, LINE_THICKNESS);

        int percent = Math.round(Math.max(0f, Math.min(1f, progress)) * 100f);
        int textColor = ready ? (0xFF000000 | rgb) : BUSY_TEXT;
        renderer.text(percent + "%", centerX, pos.getY() + LABEL_Y_OFFSET, centerZ,
            (float) settings.espScale * LABEL_SCALE, textColor, fade, true);
    }

    /** ARGB：基色按 {@code fade} 缩放透明度（残影淡出用） */
    private static int argb(int rgb, float fade, int baseAlpha) {
        int alpha = Math.max(0, Math.min(255, Math.round(baseAlpha * fade)));
        return (alpha << 24) | rgb;
    }
}
