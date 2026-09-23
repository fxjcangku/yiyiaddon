package com.yiyiaddon.dev.seedpoc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;

/**
 * 影子 {@link WorldGenLevel} 工厂：把「只读世界」与「种子 / 写权限」两件事拆开。
 *
 * <p><b>为什么需要影子层</b>（本次实验要证的正是这一层能不能自己造出来）：</p>
 * <ol>
 *     <li><b>写权限必须和原版一致</b>。原版装饰阶段的写入被
 *         {@code WorldGenRegion#ensureCanWrite}（WorldGenRegion.java:230-248）限制在
 *         {@code ChunkStep#blockStateWriteRadius} 之内；而 {@code ServerLevel} 的实现是恒 true。
 *         若直接拿 {@code ServerLevel} 当 level，矿脉会在区块外多写两格，
 *         结果集必然与原版不一致。所以这里复刻写半径判据。</li>
 *     <li><b>种子必须由我们提供</b>。{@code getSeed()} 返回被试种子，
 *         而不是当前世界的种子——这样同一份代码可以对任意种子做离线推演。</li>
 *     <li><b>读路径借真实世界</b>。本阶段实验的输入地形/方块状态来自已生成区块（见报告
 *         「生成期数据从哪来」一节），因此除上面两项外全部方法直通真实世界。</li>
 * </ol>
 *
 * <p>实现上用 {@link Proxy} 而不是手写上百个接口方法：既省掉大量样板，
 * 又能顺手记录「装饰链路实际调用了哪些 WorldGenLevel 方法」——这份调用面清单就是
 * 「做一个完整离线上下文还差什么」的直接证据。</p>
 */
public final class ShadowLevelFactory {

    private ShadowLevelFactory() {
    }

    /**
     * 造一个影子世界访问层。
     *
     * @param delegate    只读来源（真实世界）
     * @param seed        被试种子，由 {@code getSeed()} 返回
     * @param center      本次装饰的区块中心（决定写半径掩码）
     * @param calledNames 出参：记录被调用过的方法名（去重）
     * @param callCount   出参：调用次数计数器（长度 1 的数组，避免额外类）
     * @param trace       候选面取证；null 表示本次不取证
     * @return 可交给 {@code PlacedFeature#placeWithBiomeCheck} 的 WorldGenLevel
     */
    public static WorldGenLevel create(WorldGenLevel delegate, long seed, ChunkPos center,
                                       Set<String> calledNames, int[] callCount, SeedPocTrace trace) {
        // 中心区块是否处于「升级中」：原版 WorldGenRegion#ensureCanWrite（:236-241）只在升级中的区块上
        // 才追加越界高度判定，普通生成期根本不判高度。这一位必须照原版读一次，不能写死。
        boolean[] centerUpgrading = {false};
        boolean[] upgradeResolved = {false};
        InvocationHandler handler = (proxy, method, args) -> {
            calledNames.add(method.getName());
            callCount[0]++;
            switch (method.getName()) {
                case "ensureCanWrite": {
                    if (!upgradeResolved[0]) {
                        upgradeResolved[0] = true;
                        try {
                            centerUpgrading[0] = delegate.getChunk(center.x(), center.z()).isUpgrading();
                        } catch (Throwable error) {
                            centerUpgrading[0] = false;
                        }
                    }
                    BlockPos pos = (BlockPos) args[0];
                    boolean allowed = canWrite(delegate, center, centerUpgrading[0], pos);
                    // 取证要连「判定那一刻这一格是什么方块」一起记：那决定了这次判定会不会消耗 nextFloat()
                    if (trace != null && trace.isRecording()) {
                        trace.noteCandidate(pos, delegate.getBlockState(pos));
                    }
                    return allowed;
                }
                case "getSeed":
                    return seed;
                case "getHeight": {
                    // 三参版本是「某一列的生成期高度」，原版装饰期读的是 WorldGenRegion#getHeight，
                    // 它等于 getChunk(...).getHeight(type, x&15, z&15) + 1（WorldGenRegion.java:397-399），
                    // 而 ChunkAccess#getHeight 是 getFirstAvailable - 1（ChunkAccess.java:190-202），
                    // 所以原版值 = getFirstAvailable。
                    //
                    // 服务端那条路径（本影子层直接代理到的 Level#getHeight，Level.java:341-345）**同样**是
                    // `getChunk(...).getHeight(...) + 1`，也就是同一个 getFirstAvailable。
                    // 因此这里必须原样透传，绝不能再加 1 —— 第二轮曾误判「影子层少 1」而在代理结果上又加了 1，
                    // 那会让 OreFeature.place:46 的 `yStart <= getHeight(...)` 闸门系统性放宽一格，
                    // 从而放出原版根本放不出的矿脉（错报的成因之一）。本次已按源码逐行核对并改正。
                    Object raw = invoke(delegate, method, args);
                    if (trace != null && trace.isRecording() && raw instanceof Integer height) {
                        trace.noteHeight(height);
                    }
                    return raw;
                }
                case "setCurrentlyGenerating":
                    // 原版用它给崩溃报告挂「正在生成哪个 feature」，实验里没有崩溃报告要写
                    return null;
                case "toString":
                    return "ShadowWorldGenLevel(seed=" + seed + ", center=" + center + ")";
                case "hashCode":
                    return System.identityHashCode(proxy);
                case "equals":
                    return proxy == args[0];
                default:
                    break;
            }
            return invoke(delegate, method, args);
        };
        ClassLoader loader = WorldGenLevel.class.getClassLoader();
        return (WorldGenLevel) Proxy.newProxyInstance(loader, new Class<?>[]{WorldGenLevel.class}, handler);
    }

    /**
     * 反射调用真实世界。
     *
     * <p>反射会把业务异常包一层 {@link InvocationTargetException}，这里剥掉，
     * 保证调用方看到的是原版原样的异常（与直接用 {@code ServerLevel} 时一致）。</p>
     */
    private static Object invoke(WorldGenLevel delegate, Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(delegate, args);
        } catch (InvocationTargetException wrapped) {
            throw wrapped.getCause();
        }
    }

    /**
     * 写权限判据：逐行复刻 {@code WorldGenRegion#ensureCanWrite}（WorldGenRegion.java:228-258）。
     *
     * <p>原版只有两条判据，顺序不能颠倒：</p>
     * <ol>
     *     <li>目标方块所在区块与中心区块的<b>切比雪夫距离 ≤ 本步骤写半径</b>
     *         （{@code FEATURES} 是 1，{@code ChunkPyramid.java:29-35}）——不满足直接 false；
     *     <li><b>仅当中心区块处于「升级中」</b>（{@code ChunkAccess#isUpgrading()} =
     *         {@code getBelowZeroRetrogen() != null}，ChunkAccess.java:476-478）时，
     *         才追加一条「越界高度即拒」的判定。普通生成期这一条<b>根本不存在</b>。
     * </ol>
     *
     * <p><b>这里踩过的坑（第三轮实测定位到的第一处分叉）</b>：本类最初无条件加了
     * {@code !isOutsideBuildHeight(y)}。而钻石四条的高度修饰符是
     * {@code HeightRangePlacement.triangle(aboveBottom(-80), aboveBottom(80))}
     * （{@code OrePlacements.java:184-204}，主世界即 y ∈ [-144, 16]），
     * 算出来的 placement origin 经常低于世界底部 -64。原版对这些 origin 照样放行
     * （因为区块不在升级中，不判高度），于是 {@code Feature#place(5 参重载)}
     * （{@code Feature.java:180-182}）里的 {@code ensureCanWrite(origin)} 通过、
     * 继续调用 {@code OreFeature#place}；而我们这边被拒，整次放置被跳过 ⇒ 少消耗一整段随机数
     * ⇒ 同一条 feature 之后所有矿脉一起漂移。这就是「单 viewer 事件时间线在第 6 个事件处
     * 判定—放置交错顺序不同」的直接成因。</p>
     *
     * <p>回放的区块都是已完整生成并加载的区块，所以 {@code isUpgrading()} 实际为 false；
     * 但判据仍按原版写成条件式，不把「本实验恰好不升级」当成「原版不判」。</p>
     */
    private static boolean canWrite(WorldGenLevel delegate, ChunkPos center, boolean centerUpgrading,
                                    BlockPos pos) {
        int dx = Math.abs(SectionPos.blockToSectionCoord(pos.getX()) - center.x());
        int dz = Math.abs(SectionPos.blockToSectionCoord(pos.getZ()) - center.z());
        if (dx > SeedPocConstants.FEATURE_WRITE_RADIUS || dz > SeedPocConstants.FEATURE_WRITE_RADIUS) {
            return false;
        }
        return !(centerUpgrading && delegate.isOutsideBuildHeight(pos.getY()));
    }
}
