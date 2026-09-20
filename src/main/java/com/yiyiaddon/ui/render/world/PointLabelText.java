package com.yiyiaddon.ui.render.world;

import com.yiyiaddon.platform.world.WorldContextFormatter;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/**
 * 点位 ESP 字牌的统一样式与排版：<b>加粗 + 主题强调色 + 底板 + 居中</b>，点位写「[世界]名字」。
 *
 * <p>用户 2026-09-19 的几条指令合起来定下这里的三件事：</p>
 * <ol>
 *   <li><b>字体加粗、带背景</b>：「字体加粗背景参考星露谷农场的…」——
 *       字牌统一走 {@code §l} 加粗（{@link MinecraftText} 会切到粗体字族，测量与绘制都认），
 *       颜色默认取当前 UI 主题的<b>强调色</b> {@link ClickGuiThemeColors#accent}（换主题当帧即变，不需要重启；
 *       不是主文字色 —— 那是近白，会让六类字牌全变白，见 {@link #label} 的说明），
 *       调用点给了覆盖色时用覆盖色（用户 2026-09-21 起本类<b>所有</b>调用点都传：有方框的模块传各自的
 *       方框色、只画字牌的自动附魔传点位自己的颜色，见
 *       {@link #containerLabel(EspRenderer, String, String, double, double, double, float, int)}），
 *       底板走渲染器的 {@code shadow=true}（与星露谷字牌同一条绘制路径）；</li>
 *   <li><b>内容：一律「[世界]名字」</b>：用户 2026-09-19 定稿「全都要标上」+「[主世界]点位名字 距离不要了」
 *       —— 六个模块里凡是要画字牌的点位（自动挖矿三点、自动附魔六点、星露谷五点、村民交易两点、
 *       自动箱子的容器、自动农场的四个箱子）都用 {@link #containerLabel} 写「[世界]名字」，
 *       <b>不再带距离</b>；自动农场那两个选点角按用户 2026-09-19 后续定稿「这个不显示，
 *       默认渲染 ESP 就可以了」已整个不画字牌，因此本类不再有「只写名字」的旁路；</li>
 *   <li><b>居中</b>：{@link EspRenderer#text} 自身把文字水平垂直居中到传入锚点，调用点只要把锚点给成
 *       「实际画出来的方框中心」（大箱子用两格并集中心）即可。</li>
 * </ol>
 *
 * <p><b>为什么要共用</b>：六个点位模块的字牌若各拼一份，必然出现「有的带距离、有的不带、有的颜色不受主题
 * 管控」。文案拼装、主题取色、加粗与维度口径只此一份。</p>
 *
 * <p><b>自动挖矿曾单列，现已并入</b>：它的字牌此前照旧项目原样输出（维度名重复两次、方括号包名），
 * 用户 2026-09-19 给出统一排版后，那边也改走 {@link #text}，
 * 重复的维度名随之去掉（{@link #rawLabel} 仍在，用于「容器标签文字颜色」覆盖色的那条路径）。</p>
 */
public final class PointLabelText {

    private PointLabelText() {
    }

    // ── 三种字牌 ──

    /**
     * 点位字牌：{@code [世界]名字}（例：{@code [主世界]种子箱}）。
     *
     * <p>名字里带 {@code 箱子 / 料箱 / 附魔台} 这类字样并不影响调用 —— 六个模块里凡是要画字牌的点位
     * 都用本方法（用户 2026-09-19 定稿：全都要标上）。</p>
     *
     * @param size 字号（GUI 缩放坐标，由各模块的「字牌大小」设置给出）
     */
    public static void containerLabel(EspRenderer renderer, String name, String dimensionKey,
                                      double x, double y, double z, float size) {
        containerLabel(renderer, name, dimensionKey, x, y, z, size, 0);
    }

    /**
     * 带覆盖色的点位字牌：{@code colorOverride} 非 0 时用它，否则跟随界面主题强调色。
     *
     * <p><b>谁会传覆盖色</b>（用户 2026-09-21 之后是<b>全部</b>点位模块，一条口径「字牌颜色 = 它自己
     * 那一类的颜色设置」）：</p>
     * <ul>
     *   <li>有方框的五个模块（星露谷农场五点、自动农场四箱、自动挖矿三点、村民交易两箱、自动箱子）：
     *       传各自的方框色。星露谷那处是 2026-09-21「不同颜色合理分配」的直接产物；
     *       此前一律跟主题强调色，而三套内置主题的强调色都是蓝，实机表现就是「怎么都是蓝色」；</li>
     *   <li>自动附魔六个点位：「只画字牌、不画方框」的对象，字牌是这个设置项唯一能影响的东西 ——
     *       不传就成了一条点了没反应的死设置。</li>
     * </ul>
     *
     * <p>{@code colorOverride == 0} 的回落路径（跟主题强调色）仍在，供以后新增的模块选择「不与方框同色」
     * 时使用；本版没有任何调用点走它。</p>
     */
    public static void containerLabel(EspRenderer renderer, String name, String dimensionKey,
                                      double x, double y, double z, float size, int colorOverride) {
        label(renderer, text(name, dimensionKey), x, y, z, size, colorOverride);
    }

    /** 容器点位字牌的维度键重载（点位存的是 {@code ResourceKey<Level>}，这里统一转 ID） */
    public static void containerLabel(EspRenderer renderer, String name, ResourceKey<Level> dimension,
                                      double x, double y, double z, float size) {
        containerLabel(renderer, name, dimension == null ? null : dimension.identifier().toString(),
            x, y, z, size);
    }

    /** 同 {@link #containerLabel(EspRenderer, String, String, double, double, double, float, int)}，维度给 {@code ResourceKey<Level>} */
    public static void containerLabel(EspRenderer renderer, String name, ResourceKey<Level> dimension,
                                      double x, double y, double z, float size, int colorOverride) {
        containerLabel(renderer, name, dimension == null ? null : dimension.identifier().toString(),
            x, y, z, size, colorOverride);
    }

    /**
     * 带图标的点位字牌：图标画在文字左侧，「图标 + 文字」整体居中于锚点。
     *
     * <p>用户 2026-09-22：「esp 点位也可以加一个农作物吗」——点位字牌从此带一张代表自己的贴图
     * （种子箱画种子、成品箱画作物…，映射见 {@code StardewPointType#iconItemId()}）。</p>
     *
     * @param iconTexture 贴图资源路径（如 {@code minecraft:textures/item/wheat.png}）；
     *                    为空 / 取不到时退化成纯文字字牌，字牌本身不会因此消失
     */
    public static void containerLabel(EspRenderer renderer, String name, String dimensionKey,
                                      double x, double y, double z, float size, int colorOverride,
                                      String iconTexture) {
        label(renderer, text(name, dimensionKey), x, y, z, size, colorOverride, iconTexture);
    }

    /** 同 {@link #containerLabel(EspRenderer, String, String, double, double, double, float, int, String)}，维度给 {@code ResourceKey<Level>} */
    public static void containerLabel(EspRenderer renderer, String name, ResourceKey<Level> dimension,
                                      double x, double y, double z, float size, int colorOverride,
                                      String iconTexture) {
        containerLabel(renderer, name, dimension == null ? null : dimension.identifier().toString(),
            x, y, z, size, colorOverride, iconTexture);
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
     * 点位字牌全文：{@code [世界]名字}（例：{@code [主世界]种子箱}）。
     *
     * <p>用户 2026-09-19 定稿的排版：<b>只有「世界 + 名称」两段，不带距离、不加任何前缀词</b>
     * （不写「维度」「距离」这类标签），世界用方括号包住放在最前；名字里自带的 {@code §} 色码会被剥掉
     * （颜色由主题或点位色决定）。</p>
     *
     * <p><b>距离为什么去掉</b>：用户 2026-09-19 定稿「[主世界]点位名字 距离不要了 所有模块更新」——
     * 上一版是 {@code [主世界]名字[12m]}，本次只砍掉尾部那段距离，其余一字未动。</p>
     *
     * <p><b>维度认不出来时写「自定义维度」</b>（用户 2026-09-19：「有一些服务器是自定义的，
     * 你就显示自定义维度就好了」）：名称规则委托 {@link WorldContextFormatter#dimensionDisplayName(String)}
     * —— 原版三维度精确匹配 → 语言文件里的中文名 → 兜底「自定义维度」，绝不把 {@code mypack:xxx}
     * 这类技术键甩到屏幕上。六个点位模块的字牌全部经本方法，改一处即全部生效。</p>
     */
    public static String text(String name, String dimensionKey) {
        String clean = name == null ? "" : MinecraftText.strip(name);
        return "[" + WorldContextFormatter.dimensionDisplayName(dimensionKey) + "]" + clean;
    }

    // ── 绘制 ──

    /**
     * 统一绘制：加粗 + 主题强调色（或调用点覆盖色）+ 满不透明 + 底板，锚点即框中心（文字水平垂直居中）。
     *
     * <p><b>颜色为什么在这里定</b>：字牌的颜色跟随 UI 主题（用户 2026-09-19：「跟随我的主题颜色同步切换」），
     * 主题色每帧现读 {@link ClickGuiThemeColors#current()}，换主题立刻生效；点位自身的颜色设置仍然管
     * 那个点位的<b>方框</b>，两件事互不牵扯。</p>
     *
     * <p><b>取的是 {@code accent}（强调色）而不是 {@code primaryText}（主文字色）</b>：后者在暗色主题下
     * 就是近白色（{@code DarkTheme} 为 {@code 0xFFF5F7FF}），于是六类点位字牌全是白的 ——
     * 实机表现即用户 2026-09-19 反馈的「怎么都没颜色」。强调色才是主题里那个真正的主题色
     * （Dark {@code #6D8CFF} / AppleDark {@code #0A84FF} / White {@code #2F54EB}），换主题同样当帧切换。</p>
     */
    private static void label(EspRenderer renderer, String text, double x, double y, double z, float size,
                              int colorOverride) {
        label(renderer, text, x, y, z, size, colorOverride, null);
    }

    /**
     * 统一绘制（带可选图标）：加粗 + 覆盖色 / 主题强调色 + 满不透明 + 底板，锚点即框中心。
     *
     * @param iconTexture 字牌左侧的贴图资源路径；{@code null} 即纯文字字牌
     */
    private static void label(EspRenderer renderer, String text, double x, double y, double z, float size,
                              int colorOverride, String iconTexture) {
        if (renderer == null || text == null || text.isEmpty()) return;
        int color = colorOverride != 0 ? (colorOverride & 0xFFFFFF)
            : (ClickGuiThemeColors.current().accent & 0xFFFFFF);
        // §l = 加粗（MinecraftText 的测量与绘制都认这个码）；先 strip 再拼，保证颜色不被色码盖掉
        String line = "§l" + MinecraftText.strip(text);
        if (iconTexture == null || iconTexture.isBlank()) {
            renderer.text(line, x, y, z, size, color, 1f, true);
            return;
        }
        renderer.textWithIcon(line, iconTexture, x, y, z, size, color, 1f, true);
    }
}
