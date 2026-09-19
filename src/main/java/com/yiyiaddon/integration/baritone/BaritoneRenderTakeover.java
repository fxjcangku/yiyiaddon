package com.yiyiaddon.integration.baritone;

import baritone.api.BaritoneAPI;
import baritone.api.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 接管 Baritone 自带的<b>全部世界渲染</b>：接管期间把它自己的渲染开关强制置 false 并记住原值，
 * 关闭接管时原样还回去；被接管的那部分一律改由本项目自绘（{@link BaritoneOverlay}）。
 *
 * <p><b>为什么只压开关、不去逐个覆盖渲染设置项</b>（逐条对照 Baritone 26.1.2 的
 * {@code api/Settings.java} 与 {@code utils/PathRenderer} / {@code selection/SelectionRenderer}）：
 * Baritone 的渲染设置分两层——<b>总开关</b>与<b>从属项</b>（颜色、线宽、忽略深度、动画、
 * {@code fadePath}、{@code yLevelBoxSize}…）。总开关一关，整条渲染链路在入口就 return，
 * 从属项<b>一次都不会被读到</b>，因此不需要逐个覆盖；颜色与线宽改由本项目自己的口径出
 * （见 {@code EspGlobalSettings} 与 {@link BaritoneOverlay}），也不该被 Baritone 的值牵着走。</p>
 *
 * <p><b>五个开关各自管住哪条链路：</b></p>
 * <ul>
 *   <li>{@code renderPath} —— 路径线；并且 {@code PathRenderer#render} 在它之后才画挖掘框，
 *       所以它一关，{@code renderSelectionBoxes} 也顺带失效。</li>
 *   <li>{@code renderGoal} —— 目标框与 XZ 目标信标。</li>
 *   <li>{@code renderSelectionBoxes} —— 待破坏 / 待放置 / 待进入的方块框。</li>
 *   <li>{@code renderSelection} —— {@code SelectionRenderer} 画的 {@code /sel} 选区。</li>
 *   <li>{@code renderSelectionCorners} —— <b>唯一一个不在 PathRenderer / SelectionRenderer 里的渲染口</b>：
 *       {@code command/defaults/SelCommand} 用它控制「只选了第一个角点时的预览框」，那条监听器不检查
 *       {@code renderSelection}，只关上面四个还会漏出这一个框。把它一起关掉，选区的<b>全部</b>像素
 *       才都归本项目。代价是 {@code /sel 1} 之后、{@code /sel 2} 之前不再有预览框（Baritone 没有公开
 *       API 能读到那个待定角点，无法自绘），选好两点后由本项目的自绘选区完整呈现。</li>
 * </ul>
 *
 * <p><b>不在接管范围内的那一处</b>：{@code process/elytra/ElytraBehavior#onRenderPass} 画的鞘翅飞行
 * 路径（红色路径线、绿色瞄准框、清道 / 阻挡射线、模拟线）——它<b>没有任何设置开关</b>，
 * 且数据全在私有字段里，既关不掉也读不到。本项目的用途（挖矿 / 附魔 / 农场 / 交易）不涉及鞘翅飞行，
 * 这里明确记一笔，免得以后误以为漏了。</p>
 *
 * <p><b>只改运行期内存值，不写盘</b>：不调 {@code SettingsUtil#save}，所以不会污染
 * {@code run/baritone/settings.txt}（与 {@code MiningPathing} 临时改 {@code logger} 同款做法）。
 * 关闭接管（或关掉「ESP 全局设置 ▸ 外观 ▸ Baritone 渲染」）后，各开关回到接管前的样子。</p>
 *
 * <p><b>调用时机</b>：主线程 tick 每刻一次（见 {@link BaritoneOverlay#syncTakeover} 的说明——
 * Baritone 的静态 {@code Settings} 由它自己的模组初始化建立，客户端入口执行时可能还没就绪）。</p>
 */
public final class BaritoneRenderTakeover {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/baritone-render");

    /**
     * 被接管的设置项 —— 键名逐条对照 Baritone 26.1.2 的 {@code api/Settings.java}。
     *
     * <p>只用于「Baritone 设置页把已失效的项收起来」这一个用途；真正压开关时按下标直取字段
     * （见 {@link #targets}），不经过字符串查表，Baritone 改名会在编译期就报出来。</p>
     */
    private static final Set<String> SUPPRESSED = Set.of(
            // 路径线（renderPath 一个开关罩住整条链路）
            "renderPath", "renderPathAsLine", "renderPathIgnoreDepth", "pathRenderLineWidthPixels",
            "fadePath", "colorCurrentPath", "colorNextPath",
            "colorBestPathSoFar", "colorMostRecentConsidered",
            // 目标（renderGoal 之上还有从属的动画 / 信标 / 线宽 / 两色）
            "renderGoal", "renderGoalAnimated", "renderGoalIgnoreDepth", "renderGoalXZBeacon",
            "goalRenderLineWidthPixels", "yLevelBoxSize", "colorGoalBox", "colorInvertedGoalBox",
            // 挖掘方块框
            "renderSelectionBoxes", "renderSelectionBoxesIgnoreDepth",
            "colorBlocksToBreak", "colorBlocksToPlace", "colorBlocksToWalkInto",
            // /sel 选区
            "renderSelection", "renderSelectionIgnoreDepth", "renderSelectionCorners",
            "selectionOpacity", "selectionLineWidth",
            "colorSelection", "colorSelectionPos1", "colorSelectionPos2"
    );

    /** 接管期内的强制目标值与原值；空表示当前没在接管。 */
    private static final List<Owned> OWNED = new ArrayList<>();

    private static boolean active;

    private BaritoneRenderTakeover() {
    }

    /**
     * 同步接管状态：{@code takeover} 为真则把 Baritone 的渲染开关全部压掉并记录原值，
     * 为假则还原。每刻调用是幂等的——已接管且值没被改动时只做几次引用比较。
     *
     * @return 当前是否处于接管状态（Baritone 未就绪时返回 false，下刻会重试）
     */
    public static boolean sync(boolean takeover) {
        Settings settings = BaritoneAPI.getSettings();
        if (settings == null) return false;
        if (!takeover) {
            restore(settings);
            return false;
        }
        if (!active) {
            OWNED.clear();
            for (Map.Entry<Settings.Setting<?>, Object> entry : targets(settings).entrySet()) {
                OWNED.add(new Owned(entry.getKey(), entry.getValue(), entry.getKey().value));
            }
            active = true;
        }
        // 接管期间用户在 Baritone 设置页把它打开，这里会关回去：「接管」的语义就是这些线只由本项目画
        for (Owned owned : OWNED) {
            if (!owned.target().equals(owned.setting().value)) {
                assign(owned.setting(), owned.target());
            }
        }
        return true;
    }

    /** 还原全部被接管的开关到接管前的值；没接管过就什么也不做。 */
    public static void restore() {
        Settings settings = BaritoneAPI.getSettings();
        if (settings != null) {
            restore(settings);
        }
    }

    private static void restore(Settings settings) {
        if (!active) return;
        try {
            for (Owned owned : OWNED) {
                assign(owned.setting(), owned.original());
            }
        } catch (Throwable error) {
            // 还原失败不该冒泡到 tick（设置页与渲染都不该因此报错）
            LOGGER.warn("还原 Baritone 渲染开关失败", error);
        }
        OWNED.clear();
        active = false;
    }

    /** 当前是否处于接管状态（渲染层用它判断「Baritone 已经不再画了，轮到我画」）。 */
    public static boolean active() {
        return active;
    }

    /**
     * 该设置项是否已被接管、因而在 Baritone 设置页里失去意义（{@code BaritoneSettingsPage} 用）。
     *
     * <p>返回真时对应的渲染项已经不看这个值了，界面上还留着只会让人以为改了有用。</p>
     */
    public static boolean isSuppressed(String settingName) {
        return active && settingName != null && SUPPRESSED.contains(settingName);
    }

    /** 被接管的设置项条数（设置页说明用）。 */
    public static int suppressedCount() {
        return SUPPRESSED.size();
    }

    /**
     * 接管时要写死的设置项与目标值。
     *
     * <p>字段直取而不是按名字查表：Baritone 改字段名会在编译期直接报错，不会静默失手。</p>
     */
    private static Map<Settings.Setting<?>, Object> targets(Settings settings) {
        Map<Settings.Setting<?>, Object> targets = new LinkedHashMap<>();
        targets.put(settings.renderPath, false);
        targets.put(settings.renderGoal, false);
        targets.put(settings.renderSelectionBoxes, false);
        targets.put(settings.renderSelection, false);
        targets.put(settings.renderSelectionCorners, false);
        return targets;
    }

    /** {@code Setting<T>} 的泛型在运行时已擦除，这里按 Baritone 自己界面同款做法裸写值。 */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static void assign(Settings.Setting<?> setting, Object value) {
        ((Settings.Setting) setting).value = value;
    }

    /** 一条被接管的设置：接管期间强制写入 {@code target}，关闭接管时写回 {@code original}。 */
    private record Owned(Settings.Setting<?> setting, Object target, Object original) {
    }
}
