package com.yiyiaddon.integration.baritone;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.behavior.IPathingBehavior;
import baritone.api.pathing.calc.IPathFinder;
import baritone.api.pathing.goals.Goal;
import baritone.api.pathing.goals.GoalComposite;
import baritone.api.pathing.goals.GoalGetToBlock;
import baritone.api.pathing.goals.GoalInverted;
import baritone.api.pathing.goals.GoalTwoBlocks;
import baritone.api.pathing.goals.GoalXZ;
import baritone.api.pathing.goals.GoalYLevel;
import baritone.api.pathing.path.IPathExecutor;
import baritone.api.selection.ISelection;
import baritone.api.selection.ISelectionManager;
import baritone.api.utils.BetterBlockPos;
import baritone.api.utils.interfaces.IGoalRenderPos;
import baritone.pathing.path.PathExecutor;
import com.yiyiaddon.ui.component.GlassPanel;
import com.yiyiaddon.ui.render.world.EspGlobalSettings;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.ShapeMode;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * 自研的 Baritone 世界渲染层：Baritone 的寻路路径、寻路目标、待破坏 / 待放置 / 待进入的方块框、
 * 以及 {@code /sel} 选区，全部改用本项目的 {@link EspRenderer} 画，Baritone 自己那几条一并关掉
 * （见 {@link BaritoneRenderTakeover}）。
 *
 * <p><b>为什么自研</b>（用户 2026-09-19：「他的寻路渲染太垃圾了」→「我要接管 baritone 的所有 esp 渲染」）：
 * Baritone 那几条线由它自己注入 {@code LevelRenderer.renderLevel} 画，颜色与线宽只有
 * {@code colorCurrentPath} / {@code pathRenderLineWidthPixels} 等几个粗粒度设置项，线宽还是
 * <b>物理像素</b>（默认 3~5，见 {@code MiningPointRenderer} 的线宽注释），观感跟本项目 ESP 差着一档。
 * 改由自研层绘制后：颜色、线宽、不透明度、最远距离、淡出、图元预算<b>全部与其它 ESP 共用同一套全局口径</b>
 * （{@link EspGlobalSettings}），并接入「ESP 全局设置」总闸。数据源一律取 Baritone 的公开 API
 * （{@code IPathingBehavior} / {@code IPathExecutor} / {@code ISelectionManager}），只读不写，
 * 不碰它的寻路状态。</p>
 *
 * <p><b>四类图形与配色</b>（护眼口径：低饱和、同一明度区间，逐条取自旧项目已验证的色板手感）：</p>
 * <ul>
 *   <li><b>路径</b>：主色默认低饱和薄荷青（可在设置里改）。当前段沿线从「起点压暗」过渡到「车头提亮」，
 *       并叠一条<b>朝目标流动的亮带</b>（流光，见 {@link #FLOW_WAVELENGTH_SEGMENTS}）——
 *       方向不用箭头也不用猜；规划段混向冷灰蓝并压暗一档；计算中的最优路径与最近考虑路径
 *       再各降一档不透明度 —— 四档由亮到暗正好对应「正在走 / 接下来走 / 算到最好的 / 刚看过一眼的」。</li>
 *   <li><b>目标</b>：方块框 + 极淡填充（填充压到几乎看不见，只把目标圈出来，不糊住方块），
 *       整框沿 Y <b>上下浮动</b>（Baritone 原版 {@code renderGoalAnimated} 的效果）；
 *       颜色按矿石种类取（见 {@link #ORE_COLORS}），非矿石目标用基准色。
 *       普通目标两格高、{@code GoalGetToBlock} / {@code GoalTwoBlocks} 贴方块只框一格（与 Baritone 同口径）；
 *       {@code GoalYLevel} 画玩家周围一整张水平面；{@code GoalXZ} 画通高方框 + 两片交叉的渐隐光柱，
 *       替代原版那条贴图信标；反向目标（{@code GoalInverted}）换成暖珊瑚色，语义上就和「要去」分开。</li>
 *   <li><b>挖掘方块框</b>：待破坏 / 待放置 / 待进入三色（暖珊瑚 / 冷蓝 / 青绿），且用方块<b>真实碰撞形状</b>
 *       的包围盒（花草、半砖不会被画成整格）；矿石那一格统一用它的矿石色
 *       （同一个矿石在「目标」与「要破坏」两处同色，不会糊成两色）。</li>
 *   <li><b>选区</b>：主体与两个角点各一圈描边，角点沿用 Baritone「pos1 暖 / pos2 冷」的语义。</li>
 * </ul>
 *
 * <p><b>接管与绘制的关系</b>：只有 {@link BaritoneRenderTakeover#active()} 为真才画 ——
 * 也就是 Baritone 确实已经不再画了才轮到本层；Baritone 未就绪（开关还没被压掉）时本层直接不画，
 * 免出现「两条线叠在一起」的瞬间。</p>
 *
 * <p><b>常驻挂载</b>：不属于任何业务模块，由 {@code YiyiAddonClient#onInitializeClient} 注册一次，
 * 因此所有用 Baritone 寻路的模块（自动挖矿、自动附魔、自动农场、村民交易、自动箱子…）都共用这一层；
 * 开关在「ESP 全局设置 ▸ 外观」里（{@link EspGlobalSettings#baritoneOverlay()}）。</p>
 */
public final class BaritoneOverlay {

    /** 绘制层 id（常驻注册，全局唯一）。 */
    public static final String LAYER_ID = "global:baritone";

    // ── 线宽 ───────────────────────────────────────────────────────────────

    /** 路径线宽（GUI 缩放坐标，再乘「ESP 全局设置 ▸ 线宽倍率」）。与自动挖矿的点位框同档。 */
    private static final float LINE_THICKNESS = 3.0f;
    /** 框类线宽：比路径线细半档，成片方块框不至于糊成一堵墙。 */
    private static final float BOX_THICKNESS = 2.5f;

    // ── 单帧上限（长距离寻路 / 大片矿区要挡住，否则会把全局图元预算吃光） ──────

    /** 单条路径最多画多少段（当前段 / 规划段 / 计算中路径各算一次）。 */
    private static final int MAX_NODES = 200;
    /** 单帧最多画多少个方块框（待破坏 + 待放置 + 待进入 + 最近考虑点合计）。 */
    private static final int MAX_BOXES = 256;
    /** 单帧最多画多少个目标（{@code GoalComposite} 可能含上千个候选方块）。 */
    private static final int MAX_GOALS = 128;

    // ── 几何口径 ───────────────────────────────────────────────────────────

    /** 当前段起点回退的节点数（Baritone {@code renderBegin = current.getPosition() - 3}）。 */
    private static final int BACKTRACK_NODES = 3;
    /** 连线画在方块中心：方块坐标 + 0.5。 */
    private static final double CENTER = 0.5d;
    /** 线段抬升：与 Baritone 的 {@code emitPathLine} 同口径（它在节点坐标上额外加 0.03），避免共面闪烁。 */
    private static final double LINE_LIFT = 0.03d;
    /** 目标框内缩一点，边框不与方块棱线重合（Baritone 用 0.002）。 */
    private static final double GOAL_INSET = 0.002d;
    /** {@code GoalXZ} 信标柱的半径（柱心到柱壁）。 */
    private static final double BEACON_RADIUS = 0.25d;
    /** {@code GoalYLevel} 水平面的半边长（格）：以玩家为中心铺 ±16 格。 */
    private static final double Y_LEVEL_HALF_SIZE = 16d;
    /** 选区框外扩一点，避免与方块棱线重合（Baritone 的 {@code SELECTION_BOX_EXPANSION}）。 */
    private static final double SELECTION_EXPAND = 0.005d;

    // ── 路径配色（相对用户设定的基准色，用 {@link GlassPanel#mix} 混合，不另写一套颜色工具） ──

    /**
     * 当前段两端配色：起点端混向深青灰（压暗、不抢眼），车头端混向白（提亮）。
     *
     * <p>逐段插值后一条线自带方向感；两端都是往固定色混，暗色端不会糊成一团（比直接乘亮度系数自然）。</p>
     */
    private static final int CURRENT_TAIL_TINT = 0x14343A;
    private static final float CURRENT_TAIL_MIX = 0.40f;
    private static final float CURRENT_HEAD_MIX = 0.25f;

    /**
     * 流光（当前段的动态）：沿路径朝目标流动的亮带。
     *
     * <p>用户 2026-09-19：「不是让你设计高级一点的动画吗 寻路的颜色跟动态」——底色渐变只是「好看」，
     * 真正让人觉得线是活的是<b>动</b>：一条亮带顺着路径往前跑，方向不用箭头也不用猜。</p>
     *
     * <p>参数关系：速度 = {@link #FLOW_WAVELENGTH_SEGMENTS} / {@link #FLOW_PERIOD_MS} ≈ 11 格/秒；
     * 波峰处颜色按 {@link #FLOW_PEAK_MIX} 混向 {@link #FLOW_HIGHLIGHT}、线宽按
     * {@link #FLOW_WIDTH_BOOST} 加粗，于是亮带是一段「带厚度的光」；波谷取正弦的平方压暗，
     * 亮带更窄、暗区更长，对比强又不吵。低于 {@link #FLOW_MIN_GLOW} 的段直接跳过混色。</p>
     */
    private static final float FLOW_WAVELENGTH_SEGMENTS = 10f;
    private static final long FLOW_PERIOD_MS = 900L;
    private static final float FLOW_PEAK_MIX = 0.72f;
    private static final float FLOW_WIDTH_BOOST = 0.35f;
    private static final float FLOW_MIN_GLOW = 0.02f;
    /** 亮带高光色：比车头提亮更亮一档的近白薄荷。 */
    private static final int FLOW_HIGHLIGHT = 0xEAFBF7;
    /** 规划段配色：混向冷灰蓝再压暗一档 —— 与当前段在色相与亮度上都分得开。 */
    private static final int PLANNED_TINT = 0x8FA6B8;
    private static final float PLANNED_MIX = 0.45f;
    private static final float PLANNED_DARKEN_MIX = 0.22f;
    /** 规划段不透明度：比当前段低一档，安静地待在那儿不抢注意力。 */
    private static final int PLANNED_ALPHA = 90;
    /** 计算中的最优路径：再降一档。 */
    private static final int SEARCHING_ALPHA = 70;
    /** 最近考虑过的路径：最暗，只作背景参考。 */
    private static final int CONSIDERED_ALPHA = 55;

    // ── 目标 / 方块框 / 选区配色 ────────────────────────────────────────────

    /** 目标框填充不透明度（整格框只描边太空，压一点点底即「圈住」又不糊方块）。 */
    private static final float GOAL_FILL_ALPHA = 0.14f;
    /** {@code GoalYLevel} 水平面的填充不透明度：面积大，再淡一档。 */
    private static final float Y_LEVEL_FILL_ALPHA = 0.10f;
    /** 信标柱柱底的强度（越往上越淡，见 {@link #drawGoalXZ}）。 */
    private static final float BEACON_ALPHA = 0.34f;
    /** 反向目标（{@code GoalInverted}）：低饱和暖珊瑚，语义上就是「要避开」。 */
    private static final int INVERTED_COLOR = 0xD98A7A;
    /** 待破坏的方块：暖陶土色。 */
    private static final int BREAK_COLOR = 0xE0A08C;
    /** 待放置的方块：冷蓝。 */
    private static final int PLACE_COLOR = 0x8EA9E0;
    /** 待进入的方块：青绿。 */
    private static final int WALK_COLOR = 0xA8C97E;
    /** 寻路中「最近考虑过」的那个方块：中性灰蓝，比上面三色都退后。 */
    private static final int CONSIDERED_COLOR = 0x9FB6C4;
    /** 选区第一个角点：暖。 */
    private static final int POS1_COLOR = 0xE8C07A;
    /** 选区第二个角点：冷。 */
    private static final int POS2_COLOR = 0x7AC0E8;

    /**
     * 目标框上下浮动的幅度（格）与周期（毫秒）。
     *
     * <p>用户 2026-09-19：「baritone 会有那种 esp 上下浮动的效果你弄得没有」——Baritone 原版的
     * {@code renderGoalAnimated} 就是让目标框动起来。这里用一条正弦把整个框沿 Y 抬落
     * ±{@link #GOAL_FLOAT_AMPLITUDE}，比原版那种「上下两个面来回扫」更安静，远看也不会糊。</p>
     */
    private static final double GOAL_FLOAT_AMPLITUDE = 0.15d;
    private static final long GOAL_FLOAT_PERIOD_MS = 1800L;

    /**
     * 矿石 → 专属色（键是方块注册名里的关键字，深层 / 下界变体自然命中同一个键）。
     *
     * <p>用户 2026-09-19：「esp 什么矿石对应什么颜色的 esp 颜色帮我设置」。口径：低饱和、护眼，
     * 但保留各矿石在玩家心里的固有印象色（钻青、金黄金、红石红、青金蓝…），
     * 而且彼此分得开 —— 挖一片混合矿区时一眼能看出哪些是哪种矿。</p>
     *
     * <p>只对<b>真矿石</b>生效：注册名必须以 {@code _ore} 结尾或等于 {@code ancient_debris}，
     * 所以 {@code coal_block} 这类同名非矿石不会被误染色（见 {@link #oreColor}）。</p>
     */
    private static final Map<String, Integer> ORE_COLORS = Map.ofEntries(
            Map.entry("coal", 0x8A8F99),      // 煤：石墨灰
            Map.entry("copper", 0xD08C6A),    // 铜：铜橙
            Map.entry("iron", 0xD9C7B4),      // 铁：铁米白
            Map.entry("gold", 0xE8C46A),      // 金：金黄（含下界金矿）
            Map.entry("redstone", 0xD96B6B),  // 红石：红
            Map.entry("lapis", 0x6B8CD9),     // 青金石：青金蓝
            Map.entry("diamond", 0x4FD8E0),   // 钻石：亮青（比基准薄荷青更偏蓝，两者不会看混）
            Map.entry("emerald", 0x6BD98A),   // 绿宝石：翠绿
            Map.entry("quartz", 0xE6E0D4),    // 下界石英：石英白
            Map.entry("debris", 0x9A7B6B)     // 远古残骸：焦褐
    );

    /**
     * 本帧剩余的目标 / 方块框配额。
     *
     * <p>只由 {@link #render} 在渲染线程（原版收集 gizmo 期间）读写，天然单线程，无需同步；
     * 每帧在 {@code render} 开头重置，因此不会有跨帧残留。</p>
     */
    private static int goalBudget;
    private static int boxBudget;

    private BaritoneOverlay() {
    }

    /**
     * 世界几何阶段回调，由 {@code WorldOverlay#collectGeometry()} 每帧驱动。
     *
     * <p>只读 Baritone 的状态、只提交线段与方框顶点：不写视角、不动目标、不改寻路行为
     * （构造上不可能引起视角抖动，见 66 号复盘的分段视角归属表）。</p>
     */
    public static void render(EspRenderer renderer) {
        EspGlobalSettings settings = EspGlobalSettings.get();
        if (!settings.baritoneOverlay()) return;
        // Baritone 的开关还没被压掉时不画：此刻它自己还在画，两条叠一起会闪
        if (!BaritoneRenderTakeover.active()) return;

        IPathingBehavior behavior = pathingBehavior();
        if (behavior == null) return;

        int base = settings.baritoneColor() & 0xFFFFFF;
        goalBudget = MAX_GOALS;
        boxBudget = MAX_BOXES;

        drawPaths(renderer, behavior, base);
        drawGoal(renderer, behavior.getGoal(), base);
        drawBlockBoxes(renderer, behavior);
        drawSelections(renderer, base);
    }

    // ── 路径 ────────────────────────────────────────────────────────────────

    /**
     * 与 Baritone {@code PathRenderer#render} 同口径的四条路径：正在走的段、预选好的下一段、
     * 计算中的最优路径、最近考虑过的路径。后两条只在路径还没算完时存在。
     */
    private static void drawPaths(EspRenderer renderer, IPathingBehavior behavior, int base) {
        IPathExecutor current = behavior.getCurrent();
        if (current != null && current.getPath() != null) {
            List<BetterBlockPos> positions = current.getPath().positions();
            drawCurrentPath(renderer, positions,
                    Math.max(current.getPosition() - BACKTRACK_NODES, 0), base);
        }

        IPathExecutor next = behavior.getNext();
        if (next != null && next.getPath() != null) {
            drawPlannedPath(renderer, next.getPath().positions(), base, PLANNED_ALPHA);
        }

        Optional<? extends IPathFinder> searching = behavior.getInProgress();
        if (searching.isEmpty()) return;
        IPathFinder finder = searching.get();
        finder.bestPathSoFar().ifPresent(path ->
                drawPlannedPath(renderer, path.positions(), base, SEARCHING_ALPHA));
        finder.pathToMostRecentNodeConsidered().ifPresent(path -> {
            drawPlannedPath(renderer, path.positions(), base, CONSIDERED_ALPHA);
            drawBlockBox(renderer, path.getDest(), CONSIDERED_COLOR);
        });
    }

    /**
     * 当前段：底色的逐段插值（起点端压暗 → 车头端提亮）+ 沿路径向目标流动的<b>流光</b>。
     *
     * <p><b>流光怎么做的</b>：在段索引上叠一条正弦波，波峰就是亮带；相位随<b>时间</b>前移，
     * 于是整条线上的亮带一起朝目标方向流动（速度 = 波长 / 周期 ≈ 11 格/秒）。
     * 波峰处颜色混向高光色、线宽同时加粗一档 —— 亮带看起来像一段带厚度的光，而不是一条变色的线。
     * 波谷处取正弦的平方压暗，亮带更窄、暗区更长，对比更强也更安静。</p>
     *
     * <p><b>为什么按「绝对段索引」而不是「窗口内相对索引」计算相位</b>：段索引对应世界里的固定节点，
     * 所以亮带是在世界空间里朝目标走的，速度不受玩家移动影响；用相对索引的话，窗口随玩家前移，
     * 亮带会被玩家自己「拖着走」（走得快时甚至看着在倒退），像跑马灯而不是流水。</p>
     *
     * <p><b>为什么逐段单色而不是段内渐变</b>：{@link EspRenderer} 的双色线一条会拆成 10 个图元，
     * 200 段就是 2000 个；逐段上色在观感上已经是连续渐变，图元数仍是 1 份/段。</p>
     */
    private static void drawCurrentPath(EspRenderer renderer, List<BetterBlockPos> positions, int begin,
                                        int base) {
        if (positions == null || positions.size() < 2) return;
        int start = Math.max(begin, 0);
        int end = Math.min(positions.size() - 1, start + MAX_NODES);
        int tail = GlassPanel.mix(base, CURRENT_TAIL_TINT, CURRENT_TAIL_MIX);
        int head = GlassPanel.mix(base, 0xFFFFFF, CURRENT_HEAD_MIX);
        float span = Math.max(end - start, 1);
        float phase = (System.nanoTime() / 1_000_000L % FLOW_PERIOD_MS) / (float) FLOW_PERIOD_MS;

        for (int i = start; i < end; i++) {
            int color = GlassPanel.mix(tail, head, (i - start) / span);
            float glow = flowGlow(i, phase);
            int argb = 0xFF000000 | (glow <= FLOW_MIN_GLOW
                    ? color : GlassPanel.mix(color, FLOW_HIGHLIGHT, glow * FLOW_PEAK_MIX));
            segment(renderer, positions, i, argb, LINE_THICKNESS * (1f + glow * FLOW_WIDTH_BOOST));
        }
    }

    /**
     * 第 {@code index} 段当前的流光强度（0 = 波谷，1 = 波峰）。
     *
     * <p>纯函数，只依赖段索引与传入的时间相位：不读实例状态，也没有跨帧缓存，
     * 所以路径重算、窗口平移都不会让它跳变（第 169 条：同一实现只留一份）。</p>
     */
    private static float flowGlow(int index, float phase) {
        double wave = index / (double) FLOW_WAVELENGTH_SEGMENTS - phase;
        float raw = (float) (0.5d + 0.5d * Math.sin(wave * Math.PI * 2d));
        return raw * raw;
    }

    /**
     * 规划段 / 计算中路径：整段单色（基准色混向冷灰蓝并压暗），不透明度由调用方分档。
     *
     * <p>这几条<b>不加流光</b>：流光在本层是「这段正在走」的信号，到处都在动就失去意义了。</p>
     */
    private static void drawPlannedPath(EspRenderer renderer, List<BetterBlockPos> positions, int base,
                                        int alpha) {
        if (positions == null || positions.size() < 2) return;
        int end = Math.min(positions.size() - 1, MAX_NODES);
        int color = GlassPanel.mix(GlassPanel.mix(base, PLANNED_TINT, PLANNED_MIX),
                0x1A2126, PLANNED_DARKEN_MIX);
        int argb = (alpha << 24) | color;
        for (int i = 0; i < end; i++) {
            segment(renderer, positions, i, argb, LINE_THICKNESS);
        }
    }

    /** 第 {@code i} 个节点到第 {@code i + 1} 个节点之间的一段；线抬升一点，避免与方块面共面闪烁。 */
    private static void segment(EspRenderer renderer, List<BetterBlockPos> positions, int i, int argb,
                                float thickness) {
        BetterBlockPos from = positions.get(i);
        BetterBlockPos to = positions.get(i + 1);
        renderer.line(from.x + CENTER, from.y + CENTER + LINE_LIFT, from.z + CENTER,
                to.x + CENTER, to.y + CENTER + LINE_LIFT, to.z + CENTER, argb, thickness);
    }

    // ── 目标 ────────────────────────────────────────────────────────────────

    /**
     * 寻路目标，按 Baritone {@code PathRenderer#drawGoal} 的类型分支逐条对口径：
     * 反向目标换色、复合目标递归、Y 层画水平面、XZ 目标画通高柱，其余按
     * {@link IGoalRenderPos} 取方块位置画框。
     */
    private static void drawGoal(EspRenderer renderer, Goal goal, int base) {
        if (goal == null || goalBudget <= 0) return;

        if (goal instanceof GoalInverted inverted) {
            drawGoal(renderer, inverted.origin, INVERTED_COLOR);
            return;
        }
        if (goal instanceof GoalComposite composite) {
            for (Goal child : composite.goals()) {
                if (goalBudget <= 0) return;
                drawGoal(renderer, child, base);
            }
            return;
        }
        if (goal instanceof GoalYLevel yLevel) {
            drawYLevel(renderer, yLevel.level, base);
            return;
        }
        if (goal instanceof GoalXZ xz) {
            drawGoalXZ(renderer, xz.getX(), xz.getZ(), base);
            return;
        }
        if (goal instanceof IGoalRenderPos renderPos) {
            drawGoalPos(renderer, renderPos.getGoalPos(),
                    goal instanceof GoalGetToBlock || goal instanceof GoalTwoBlocks, base);
        }
    }

    /**
     * 贴到某个方块的目标：普通目标框两格高，贴方块 / 两格高的目标只框一格（Baritone 同口径）；
     * 整框沿 Y 上下浮动（见 {@link #goalFloatOffset}），颜色按矿石种类取（见 {@link #goalColor}）。
     */
    private static void drawGoalPos(EspRenderer renderer, BlockPos pos, boolean blockSized, int base) {
        goalBudget--;
        double top = blockSized ? pos.getY() + 1 : pos.getY() + 2;
        double floatY = goalFloatOffset();
        AABB box = new AABB(pos.getX() + GOAL_INSET, pos.getY() + floatY, pos.getZ() + GOAL_INSET,
                pos.getX() + 1 - GOAL_INSET, top + floatY, pos.getZ() + 1 - GOAL_INSET);
        drawBox(renderer, box, goalColor(pos, base));
    }

    /** 目标框本帧的上下浮动偏移（一条正弦，周期 {@link #GOAL_FLOAT_PERIOD_MS}）。 */
    private static double goalFloatOffset() {
        double phase = (System.nanoTime() / 1_000_000L % GOAL_FLOAT_PERIOD_MS)
                / (double) GOAL_FLOAT_PERIOD_MS;
        return Math.sin(phase * Math.PI * 2d) * GOAL_FLOAT_AMPLITUDE;
    }

    /**
     * 目标框的颜色：目标位置是矿石就用它的专属色，否则用基准色。
     *
     * <p>还要看<b>目标上方一格</b>：{@code #mine} 的目标由 {@code MineProcess#coalesce} 生成，
     * 常出现 {@code GoalTwoBlocks} 落在矿石下面那一格（玩家站的位置），只看目标本身就漏了颜色。</p>
     */
    private static int goalColor(BlockPos pos, int base) {
        return colorFor(pos, colorFor(pos.above(), base));
    }

    /**
     * 该方块是矿石则返回它的专属色，否则返回 {@code fallback}。
     *
     * <p>判据取方块注册名：必须以 {@code _ore} 结尾或等于 {@code ancient_debris} ——
     * 只有真矿石才吃 {@link #ORE_COLORS}，{@code coal_block} 这类同名非矿石不会被误染色；
     * 深层（{@code deepslate_diamond_ore}）与下界（{@code nether_gold_ore} / {@code nether_quartz_ore}）
     * 变体因为都含同一关键字，自动命中同一个颜色。</p>
     */
    private static int colorFor(BlockPos pos, int fallback) {
        int ore = oreColor(pos);
        return ore >= 0 ? ore : fallback;
    }

    /** 该方块是不是矿石；是则返回专属色，否则 -1。 */
    private static int oreColor(BlockPos pos) {
        Level world = Minecraft.getInstance().level;
        if (world == null) return -1;
        Identifier id = BuiltInRegistries.BLOCK.getKey(world.getBlockState(pos).getBlock());
        if (id == null) return -1;
        String name = id.getPath();
        if (!name.endsWith("_ore") && !name.equals("ancient_debris")) return -1;
        for (Map.Entry<String, Integer> entry : ORE_COLORS.entrySet()) {
            if (name.contains(entry.getKey())) return entry.getValue();
        }
        return -1;
    }

    /** {@code GoalYLevel}：以玩家为中心铺一整张水平面，一眼看出「要挖到哪一层」。 */
    private static void drawYLevel(EspRenderer renderer, int level, int base) {
        goalBudget--;
        Vec3 player = playerPos();
        if (player == null) return;
        renderer.sideHorizontal(player.x - Y_LEVEL_HALF_SIZE, level, player.z - Y_LEVEL_HALF_SIZE,
                player.x + Y_LEVEL_HALF_SIZE, player.z + Y_LEVEL_HALF_SIZE,
                GlassPanel.withAlpha(base, Y_LEVEL_FILL_ALPHA), 0xFF000000 | base,
                ShapeMode.Both, BOX_THICKNESS);
    }

    /**
     * {@code GoalXZ}：通高方框 + 两片交叉的渐隐光柱。
     *
     * <p>Baritone 原版画的是一条贴图信标光柱；这里换成两片交叉的竖直渐变面（柱底浓、往上渐隐），
     * 观感同源但完全走本项目的渲染管线，且没有贴图与深度态的特殊处理。</p>
     */
    private static void drawGoalXZ(EspRenderer renderer, int x, int z, int base) {
        goalBudget--;
        Level world = Minecraft.getInstance().level;
        if (world == null) return;
        double minY = world.getMinY();
        double maxY = world.getMaxY();

        renderer.box(new AABB(x + GOAL_INSET, minY, z + GOAL_INSET,
                        x + 1 - GOAL_INSET, maxY, z + 1 - GOAL_INSET),
                0, 0xFF000000 | base, ShapeMode.Lines, BOX_THICKNESS);

        // 渐变面口径（见 EspRenderer#gradientQuadVertical）：第一个点取柱底、第二个点取柱顶，
        // 颜色参数对应「柱顶 / 柱底」，因此这里传「透明 → 浓」。
        int clear = base;
        int strong = GlassPanel.withAlpha(base, BEACON_ALPHA);
        double low = x + 0.5d - BEACON_RADIUS;
        double high = x + 0.5d + BEACON_RADIUS;
        double lowZ = z + 0.5d - BEACON_RADIUS;
        double highZ = z + 0.5d + BEACON_RADIUS;
        renderer.gradientQuadVertical(low, minY, lowZ, high, maxY, highZ, clear, strong);
        renderer.gradientQuadVertical(low, minY, highZ, high, maxY, lowZ, clear, strong);
    }

    // ── 挖掘方块框 ──────────────────────────────────────────────────────────

    /**
     * 当前执行路径上「要挖掉 / 要放下 / 要走进」的方块框（Baritone {@code PathExecutor} 的三个集合）。
     *
     * <p>{@code toBreak()} 一类只声明在实现类上，因此这里按下标强转；Baritone 是编译期依赖、
     * 版本与运行期一致，强转必成立（仍留 {@code instanceof} 兜底，不然升级 Baritone 会直接崩渲染线程）。</p>
     */
    private static void drawBlockBoxes(EspRenderer renderer, IPathingBehavior behavior) {
        IPathExecutor current = behavior.getCurrent();
        if (!(current instanceof PathExecutor executor)) return;
        drawBlockBoxes(renderer, executor.toBreak(), BREAK_COLOR);
        drawBlockBoxes(renderer, executor.toPlace(), PLACE_COLOR);
        drawBlockBoxes(renderer, executor.toWalkInto(), WALK_COLOR);
    }

    private static void drawBlockBoxes(EspRenderer renderer, Set<BlockPos> positions, int color) {
        if (positions == null) return;
        for (BlockPos pos : positions) {
            if (boxBudget <= 0) return;
            drawBlockBox(renderer, pos, color);
        }
    }

    /**
     * 单个方块框：只描边（成片框不填充，免得糊成一堵墙）；矿石按种类上色，其余用调用方给的语义色。
     *
     * <p>挖矿时「目标」与「要破坏的方块」常常就是同一格矿石，两处各画一个颜色会糊在一起，
     * 所以矿石这一格统一走矿石色，三色语义留给真正需要区分的非矿石方块（石头 / 泥土要挖、
     * 要垫的方块要放、要走进的空格）。</p>
     */
    private static void drawBlockBox(EspRenderer renderer, BlockPos pos, int color) {
        if (pos == null) return;
        boxBudget--;
        renderer.box(shapeBox(pos), 0, 0xFF000000 | colorFor(pos, color),
                ShapeMode.Lines, BOX_THICKNESS);
    }

    /**
     * 方块的真实碰撞形状包围盒。
     *
     * <p>与 Baritone 的 {@code drawManySelectionBoxes} 同口径：取 {@code VoxelShape} 的包围盒，
     * 形状为空（草、火把这类无碰撞方块）时退回整格 —— 这样花草不会被画成一个大方块。</p>
     */
    private static AABB shapeBox(BlockPos pos) {
        Level world = Minecraft.getInstance().level;
        if (world == null) {
            return new AABB(pos.getX(), pos.getY(), pos.getZ(),
                    pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
        }
        VoxelShape shape = world.getBlockState(pos).getShape(world, pos);
        AABB bounds = shape.isEmpty() ? new AABB(0, 0, 0, 1, 1, 1) : shape.bounds();
        return bounds.move(pos);
    }

    // ── 选区 ────────────────────────────────────────────────────────────────

    /** {@code /sel} 选区：主体一圈描边 + 两个角点各一圈（沿用 Baritone「pos1 暖 / pos2 冷」的语义）。 */
    private static void drawSelections(EspRenderer renderer, int base) {
        ISelectionManager manager = selectionManager();
        if (manager == null) return;
        ISelection[] selections = manager.getSelections();
        if (selections == null) return;

        for (ISelection selection : selections) {
            if (selection == null || boxBudget <= 0) return;
            boxBudget--;
            renderer.box(selection.aabb().inflate(SELECTION_EXPAND),
                    0, 0xFF000000 | base, ShapeMode.Lines, BOX_THICKNESS);
            renderer.box(blockBox(selection.pos1()), 0, 0xFF000000 | POS1_COLOR,
                    ShapeMode.Lines, BOX_THICKNESS);
            renderer.box(blockBox(selection.pos2()), 0, 0xFF000000 | POS2_COLOR,
                    ShapeMode.Lines, BOX_THICKNESS);
        }
    }

    /** 整格方框（选区角点用；这两处不取方块碰撞形状，角点就是「玩家点的那一格」）。 */
    private static AABB blockBox(BlockPos pos) {
        return new AABB(pos.getX(), pos.getY(), pos.getZ(),
                pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
    }

    // ── 目标框的统一画法 ────────────────────────────────────────────────────

    /** 目标框：同色系描边 + 极淡填充。 */
    private static void drawBox(EspRenderer renderer, AABB box, int base) {
        renderer.box(box, GlassPanel.withAlpha(base, GOAL_FILL_ALPHA), 0xFF000000 | base,
                ShapeMode.Both, BOX_THICKNESS);
    }

    // ── 接管同步 ────────────────────────────────────────────────────────────

    /**
     * 同步接管状态（<b>主线程每刻调用一次</b>，见 {@code YiyiAddonClient} 的 tick 注册）。
     *
     * <p>为什么放在 tick 而不是只在启动与设置变更时调用：Baritone 的静态
     * {@link BaritoneAPI#getSettings()} 由它自己的模组初始化建立，客户端入口
     * {@code onInitializeClient} 执行时它可能还没就绪（拿到 {@code null}），只在入口调一次会
     * 永久失手。这里每刻检查一次——未就绪就静默跳过、下刻再试，就绪后只做一次写入，
     * 之后是纯布尔判断，开销可忽略。</p>
     *
     * <p>接管逻辑本身只有一份，在 {@link BaritoneRenderTakeover}；本方法只是把「用户的开关」
     * 转成它的入参，避免两处各写一套接管条件（开发习惯第 169 条）。</p>
     */
    public static void syncTakeover() {
        BaritoneRenderTakeover.sync(EspGlobalSettings.get().baritoneOverlay());
    }

    /** 当前是否已接管 Baritone 的渲染（自检与设置页说明用）。 */
    public static boolean takeoverActive() {
        return BaritoneRenderTakeover.active();
    }

    /** Baritone 的寻路行为通道；Baritone 未就绪时返回 {@code null}。 */
    private static IPathingBehavior pathingBehavior() {
        IBaritone baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
        return baritone == null ? null : baritone.getPathingBehavior();
    }

    /** Baritone 的选区通道；Baritone 未就绪时返回 {@code null}。 */
    private static ISelectionManager selectionManager() {
        IBaritone baritone = BaritoneAPI.getProvider().getPrimaryBaritone();
        return baritone == null ? null : baritone.getSelectionManager();
    }

    /** 玩家当前坐标（{@code GoalYLevel} 水平面的中心）；不在世界里时返回 {@code null}。 */
    private static Vec3 playerPos() {
        return Minecraft.getInstance().player == null ? null : Minecraft.getInstance().player.position();
    }
}
