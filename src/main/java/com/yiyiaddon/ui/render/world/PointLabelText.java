package com.yiyiaddon.ui.render.world;

import com.yiyiaddon.platform.world.WorldContextFormatter;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * 点位 ESP 字牌的统一样式与排版：<b>加粗 + 主题色 + 底板 + 居中</b>，容器点位额外带维度与距离。
 *
 * <p>用户 2026-09-19 两条指令合起来定下这里的三件事：</p>
 * <ol>
 *   <li><b>字体加粗、带背景、跟随主题色</b>：「字体加粗背景参考星露谷农场的…跟随我的主题颜色同步切换」——
 *       字牌统一走 {@code §l} 加粗（{@link MinecraftText} 会切到粗体字族，测量与绘制都认），
 *       颜色取当前 UI 主题的 {@link ClickGuiThemeColors#primaryText}（换主题当帧即变，不需要重启），
 *       底板走渲染器的 {@code shadow=true}（与星露谷字牌同一条绘制路径）；</li>
 *   <li><b>内容：一律写维度与距离</b>：用户 2026-09-19 定稿「全都要标上，除了那两个农场的选点区域之外
 *       都要标上」——除了自动农场那两个选点角（{@link #nameLabel}），其余所有点位字牌都用
 *       {@link #containerLabel} 写「[世界]名字[距离]」（含星露谷的补水点、自动挖矿的挂机修复点、
 *       自动附魔的附魔台 / 砂轮 / 挂机点）；</li>
 *   <li><b>居中</b>：{@link EspRenderer#text} 自身把文字水平垂直居中到传入锚点，调用点只要把锚点给成
 *       「实际画出来的方框中心」（大箱子用两格并集中心）即可。</li>
 * </ol>
 *
 * <p><b>为什么要共用</b>：六个点位模块的字牌若各拼一份，必然出现「有的写米、有的写格、有的颜色不受主题
 * 管控」。文案拼装、主题取色、加粗与距离口径只此一份。</p>
 *
 * <p><b>自动挖矿曾单列，现已并入</b>：它的字牌此前照旧项目原样输出（维度名重复两次、方括号包名），
 * 用户 2026-09-19 给出统一排版「[世界]名称[距离]」后，那边也改走 {@link #text}，
 * 重复的维度名随之去掉（{@link #rawLabel} 仍在，用于「容器标签文字颜色」覆盖色的那条路径）。</p>
 */
public final class PointLabelText {

    private PointLabelText() {
    }

    // ── 三种字牌 ──

    /**
     * 点位字牌：{@code [维度]名字[距离m]}。
     *
     * <p>名字里带 {@code 箱子 / 料箱 / 附魔台} 这类字样并不影响调用 —— 除自动农场那两个选点角外，
     * 所有点位都用本方法（用户 2026-09-19 定稿：全都要标上）。</p>
     *
     * @param size 字号（GUI 缩放坐标，由各模块的「字牌大小」设置给出）
     */
    public static void containerLabel(EspRenderer renderer, String name, String dimensionKey,
                                      double x, double y, double z, float size) {
        label(renderer, text(name, dimensionKey, x, y, z), x, y, z, size, 0);
    }

    /** 容器点位字牌的维度键重载（点位存的是 {@code ResourceKey<Level>}，这里统一转 ID） */
    public static void containerLabel(EspRenderer renderer, String name, ResourceKey<Level> dimension,
                                      double x, double y, double z, float size) {
        containerLabel(renderer, name, dimension == null ? null : dimension.identifier().toString(),
            x, y, z, size);
    }

    /** 不写维度与距离的字牌：目前只有自动农场那两个<b>选点角</b>（用户 2026-09-19 指定的唯一例外） */
    public static void nameLabel(EspRenderer renderer, String name, double x, double y, double z, float size) {
        label(renderer, name, x, y, z, size, 0);
    }

    /**
     * 整串原样字牌：文案由调用点自己给（自动挖矿的旧项目原文），样式与其它字牌完全一致。
     *
     * @param colorOverride 覆盖色（{@code 0} = 跟随 UI 主题；自动挖矿的「容器标签文字颜色」非 0 时用它）
     */
    public static void rawLabel(EspRenderer renderer, String text, double x, double y, double z, float size,
                                int colorOverride) {
        label(renderer, text, x, y, z, size, colorOverride);
    }

    // ── 文案拼装 ──

    /**
     * 点位字牌全文：{@code [维度]名字[距离m]}（例：{@code [主世界]种子箱[12m]}）。
     *
     * <p>用户 2026-09-19 定稿的排版：<b>只放世界 / 名称 / 距离三段，不加任何前缀词</b>
     * （不写「维度」「距离」这类标签），维度与距离各自用方括号包住，名称夹在中间；
     * 名字里自带的 {@code §} 色码会被剥掉（颜色由主题决定）。</p>
     *
     * <p><b>维度认不出来时写「自定义维度」</b>（用户 2026-09-19：「有一些服务器是自定义的，
     * 你就显示自定义维度就好了」）：名称规则委托 {@link WorldContextFormatter#dimensionDisplayName(String)}
     * —— 原版三维度精确匹配 → 语言文件里的中文名 → 兜底「自定义维度」，绝不把 {@code mypack:xxx}
     * 这类技术键甩到屏幕上。六个点位模块的字牌全部经本方法，改一处即全部生效。</p>
     */
    public static String text(String name, String dimensionKey, double x, double y, double z) {
        String clean = name == null ? "" : MinecraftText.strip(name);
        return "[" + WorldContextFormatter.dimensionDisplayName(dimensionKey) + "]"
            + clean + "[" + distanceText(x, y, z) + "]";
    }

    /** 距离后缀：玩家眼睛到该点的直线距离，取整加 {@code m}；未进世界返回 {@code ?m} */
    public static String distanceText(double x, double y, double z) {
        Minecraft client = Minecraft.getInstance();
        if (client == null || client.player == null) return "?m";
        double distance = client.player.getEyePosition().distanceTo(new Vec3(x, y, z));
        return Math.round(distance) + "m";
    }

    // ── 绘制 ──

    /**
     * 统一绘制：加粗 + 主题主文字色（或调用点覆盖色）+ 满不透明 + 底板，锚点即框中心（文字水平垂直居中）。
     *
     * <p><b>颜色为什么在这里定</b>：字牌的颜色跟随 UI 主题（用户 2026-09-19：「跟随我的主题颜色同步切换」），
     * 主题色每帧现读 {@link ClickGuiThemeColors#current()}，换主题立刻生效；点位自身的颜色设置仍然管
     * 那个点位的<b>方框</b>，两件事互不牵扯。</p>
     */
    private static void label(EspRenderer renderer, String text, double x, double y, double z, float size,
                              int colorOverride) {
        if (renderer == null || text == null || text.isEmpty()) return;
        int color = colorOverride != 0 ? (colorOverride & 0xFFFFFF)
            : (ClickGuiThemeColors.current().primaryText & 0xFFFFFF);
        // §l = 加粗（MinecraftText 的测量与绘制都认这个码）；先 strip 再拼，保证颜色不被色码盖掉
        renderer.text("§l" + MinecraftText.strip(text), x, y, z, size, color, 1f, true);
    }
}
