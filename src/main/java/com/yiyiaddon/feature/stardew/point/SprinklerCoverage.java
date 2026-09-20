package com.yiyiaddon.feature.stardew.point;

import com.yiyiaddon.feature.stardew.StardewContext;
import com.yiyiaddon.feature.stardew.recognition.CropRecognizer;
import com.yiyiaddon.feature.stardew.recognition.PotState;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 洒水器覆盖范围的<b>实测结论</b>（服务器专属 + 资源指纹专属）。
 *
 * <p><b>为什么要有它：</b>资源包里没有洒水器范围字段，{@code radiusOfLevel} 只能按等级硬猜
 * （初级 5×5 / 中级 9×9 / 高级 13×13），实机仍可能有出入；而本服（以及多数
 * customcrops 系服务器）的种植盆本身有干 / 湿两种方块：围着洒水器数一圈湿盆即可反推真实范围。
 * 这是唯一既不依赖资源包、也不需要服务端配合的取证手段。</p>
 *
 * <p><b>只记正向证据：</b>{@link #cells} 里只有<b>湿润</b>的盆。干燥盆可能只是「刚收完还没浇水」，
 * 把它当「没被覆盖」会把范围算小；因此干燥盆只统计一个数字（{@link #dryInside}）供播报参照，
 * <b>绝不参与范围推导</b>。</p>
 *
 * <p><b>矩形只是外接框：</b>世界里的 ESP 只画一个方框（保持旧观感），所以由实测格派生外接矩形；
 * 分区覆盖率统计逐格走 {@link #covered(int, int)}，不虚报面积。</p>
 *
 * <p><b>隔离域与人工绑定同源：</b>换服或重新提取资源（指纹变化）后，旧实测一律视为无效
 * （{@link #appliesNow()}），自动退回身份级来源：物品说明优先、等级估算兜底——
 * 绝不把上一台服务器的结论套到这一台。</p>
 */
public record SprinklerCoverage(String serverKey, String fingerprint, List<Long> cells, int dryInside) {

    /**
     * 实测扫描窗口半径（格）：15×15 窗口。
     *
     * <p><b>为什么是 7：</b>等级估算的上限是高级洒水器 13×13（半径 6，见
     * {@code StardewRenderState#radiusOfLevel}），窗口必须能整个装下它；再往外留整整一圈余量，
     * 这样 {@link #truncated()} 只在「湿盆真的超出了任何已知洒水器」时才报警，
     * 13×13 的洒水器不会再被误标成「顶到扫描边界」（实机：旧窗口 9×9 时四台全报顶边界）。</p>
     */
    public static final int SCAN_RADIUS = 7;

    /** 相对偏移 (dx,dz) → 打包键（各自加偏移量后打包，避免负数参与位运算） */
    public static long key(int dx, int dz) {
        return ((long) (dx + 64) << 32) | ((dz + 64) & 0xFFFFFFFFL);
    }

    /** 打包键 → dx */
    public static int dxOf(long cell) {
        return (int) (cell >>> 32) - 64;
    }

    /** 打包键 → dz */
    public static int dzOf(long cell) {
        return (int) (cell & 0xFFFFFFFFL) - 64;
    }

    /** 相对洒水器 (dx,dz) 那一格是否实测被浇到 */
    public boolean covered(int dx, int dz) {
        return cells.contains(key(dx, dz));
    }

    public boolean empty() {
        return cells.isEmpty();
    }

    /**
     * 相对洒水器的最小 dx（外接矩形左边）。
     *
     * <p>恒 ≤ 0：外接矩形一定包含洒水器自己那一格。洒水器常常就摆在一个盆上，那一格可能是干的
     * （盆被占用 / 刚换过土），把它排除会让框看起来「缺了中心」，因此按定义补进来。</p>
     */
    public int dxMin() {
        int min = 0;
        for (long cell : cells) min = Math.min(min, dxOf(cell));
        return min;
    }

    /** 相对洒水器的最大 dx（外接矩形右边）；恒 ≥ 0 */
    public int dxMax() {
        int max = 0;
        for (long cell : cells) max = Math.max(max, dxOf(cell));
        return max;
    }

    /** 相对洒水器的最小 dz（外接矩形前边）；恒 ≤ 0 */
    public int dzMin() {
        int min = 0;
        for (long cell : cells) min = Math.min(min, dzOf(cell));
        return min;
    }

    /** 相对洒水器的最大 dz（外接矩形后边）；恒 ≥ 0 */
    public int dzMax() {
        int max = 0;
        for (long cell : cells) max = Math.max(max, dzOf(cell));
        return max;
    }

    /** 外接矩形格数（X 方向） */
    public int width() {
        return dxMax() - dxMin() + 1;
    }

    /** 外接矩形格数（Z 方向） */
    public int depth() {
        return dzMax() - dzMin() + 1;
    }

    /**
     * 实测是否顶到了扫描窗口边界。
     *
     * <p>顶到边界意味着「外面可能还有被浇到的盆、但我们没看」，此时把矩形当成最终结论会把范围算小。
     * 该结论仍会保存（总比等级估算准），但播报必须如实说明。</p>
     */
    public boolean truncated() {
        for (long cell : cells) {
            if (Math.abs(dxOf(cell)) >= SCAN_RADIUS || Math.abs(dzOf(cell)) >= SCAN_RADIUS) return true;
        }
        return false;
    }

    /** 该结论是否仍适用当前服务器 + 当前资源指纹；换服 / 重新提取资源后必须重新实测 */
    public boolean appliesNow() {
        return Objects.equals(serverKey, StardewContext.serverKey())
            && Objects.equals(fingerprint, ResourceExtractionService.fingerprint());
    }

    /**
     * 围着洒水器实测一次覆盖范围。
     *
     * <p>扫 15×15 窗口里每个种植盆的干湿，只把<b>湿润</b>的盆记成覆盖证据。一盆湿的都没有时返回
     * {@code null}：那说明洒水器没工作 / 盆全空 / 区块没加载，此时宁可让调用方继续用等级估算，
     * 也绝不把「没测到」写成「实测是 1×1」。</p>
     *
     * <p><b>只算「离自己最近」的湿盆（2026-09-21 真机修正）：</b>一片田里往往有好几台洒水器，
     * 湿盆会连成一片。直接用窗口里所有湿盆反推范围，等于把隔壁那台浇的盆也算进自己头上——
     * 实机就是这个样子：三台洒水器的「实测范围」几乎一模一样，还都顶到扫描边界，等于没测、
     * 而且把初级洒水器写成了 7×7。因此按水平距离把每个盆归给最近的那台洒水器（等距时都算），
     * 只保留归属自己的格。</p>
     *
     * <p>代价：{@code peers} 要尽量完整（当前维度所有已绑定洒水器）。漏掉一台，它的湿盆就会被
     * 算到旁边这几台头上——这也是「先把洒水器都绑一遍，再实测范围」的原因。</p>
     *
     * @param origin 洒水器所在格（点位坐标）
     * @param peers  归属参照：当前维度所有已绑定洒水器坐标（含本台最好；null 表示不做归属过滤）
     * @return 实测结论；没有正向证据、世界未就绪或不在多人服务器资源会话里返回 {@code null}
     */
    public static SprinklerCoverage capture(BlockPos origin, List<BlockPos> peers) {
        Minecraft mc = Minecraft.getInstance();
        if (origin == null || mc.level == null) return null;
        String serverKey = StardewContext.serverKey();
        String fingerprint = ResourceExtractionService.fingerprint();
        if (serverKey == null || fingerprint == null || fingerprint.isBlank()) return null;

        // 同一片田里绝大多数格是同一个方块状态（干盆 / 湿盆 / 土），按 BlockState 记忆化解析结果：
        // 一次实测最多 15×15×2 格，若每格都去读一遍资源包 blockstates（无缓存），绑定瞬间会明显卡顿。
        Map<BlockState, PotState> memo = new HashMap<>();
        List<Long> wet = new ArrayList<>();
        List<Long> dry = new ArrayList<>();
        for (int dx = -SCAN_RADIUS; dx <= SCAN_RADIUS; dx++) {
            for (int dz = -SCAN_RADIUS; dz <= SCAN_RADIUS; dz++) {
                if (!nearest(origin, origin.getX() + dx, origin.getZ() + dz, peers)) continue;
                PotState state = potStateAt(mc, memo, origin, dx, dz);
                if (state == PotState.WET) wet.add(key(dx, dz));
                else if (state == PotState.DRY) dry.add(key(dx, dz));
            }
        }
        if (wet.isEmpty()) return null;

        List<Long> cells = List.copyOf(wet);
        SprinklerCoverage probe = new SprinklerCoverage(serverKey, fingerprint, cells, 0);
        int dryInside = 0;
        for (long cell : dry) {
            if (probe.inside(dxOf(cell), dzOf(cell))) dryInside++;
        }
        return new SprinklerCoverage(serverKey, fingerprint, cells, dryInside);
    }

    /**
     * 某一格是否该归给 {@code origin} 这台洒水器：按<b>水平距离</b>取最近的一台，等距时两边都算。
     *
     * <p>只比 XZ 距离：洒水器与盆可能差一层（盆层 = 洒水器层 - 1），垂直差不是归属依据。
     * {@code peers} 为空 / 为 {@code null} 时不做归属过滤（退回旧行为，宁可多算也不算漏）。</p>
     */
    private static boolean nearest(BlockPos origin, int x, int z, List<BlockPos> peers) {
        if (peers == null || peers.isEmpty()) return true;
        long self = distanceSquared(origin, x, z);
        for (BlockPos peer : peers) {
            if (peer == null) continue;
            if (distanceSquared(peer, x, z) < self) return false;
        }
        return true;
    }

    /** 到某一格的水平距离平方（只比 XZ） */
    private static long distanceSquared(BlockPos pos, int x, int z) {
        long dx = (long) pos.getX() - x;
        long dz = (long) pos.getZ() - z;
        return dx * dx + dz * dz;
    }

    /** 该偏移是否落在实测外接矩形内（含洒水器自己那一格） */
    private boolean inside(int dx, int dz) {
        return dx >= dxMin() && dx <= dxMax() && dz >= dzMin() && dz <= dzMax();
    }

    /**
     * 某一列（dx,dz）的种植盆干湿：先看洒水器<b>下一格</b>，再看洒水器<b>那一格</b>。
     *
     * <p>洒水器有两种摆法：放在盆上（customcrops 系，盆层 = 洒水器层 - 1，本服实测就是这种）与
     * 放在地上（盆层 = 洒水器层）。两格都试、先认出来的算数；都不是盆（土壤 / 空气 / 作物）返回未知。</p>
     *
     * <p>只读世界，不写任何状态；区块没加载的格直接跳过（算未知），绝不用「加载不到」当「干盆」。</p>
     */
    private static PotState potStateAt(Minecraft mc, Map<BlockState, PotState> memo, BlockPos origin, int dx, int dz) {
        int x = origin.getX() + dx;
        int z = origin.getZ() + dz;
        for (int y = origin.getY() - 1; y <= origin.getY(); y++) {
            BlockPos pos = new BlockPos(x, y, z);
            if (!mc.level.isLoaded(pos)) continue;
            BlockState state = mc.level.getBlockState(pos);
            if (state.isAir()) continue;
            PotState pot = memo.computeIfAbsent(state, s -> CropRecognizer.recognizePotDetailed(s).state());
            if (pot == PotState.DRY || pot == PotState.WET) return pot;
        }
        return PotState.UNKNOWN;
    }
}
