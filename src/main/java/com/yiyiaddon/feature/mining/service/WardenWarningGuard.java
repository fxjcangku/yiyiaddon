package com.yiyiaddon.feature.mining.service;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientChunkCache;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SculkShriekerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SculkShriekerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.status.ChunkStatus;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 坚守者预警：盯着玩家自身附近 15×15 范围里的幽匿尖啸体，它一「尖叫」就判定坚守者准备出现。
 *
 * <p><b>用户需求（2026-09-18）：</b>挖矿有时会挖进地下古城（深暗之域），惊动坚守者就来不及跑。
 * 要求在<b>坚守者准备出现、还没钻出来</b>的阶段就播报并立刻传送逃离——等他出来就跑不掉了。
 * 默认常开，不加配置项。范围口径当天改过一次：初版「身边 32 格」太大，用户改为
 * 「玩家自身附近 15x15」，即 {@link #SCAN_SIZE} 见方的立方体（水平 ±7 格、垂直 ±7 格）。</p>
 *
 * <p><b>为什么盯「尖啸中」这一个信号（26.1.2 源码取证）：</b>客户端拿不到「已经尖叫了几次」的真值，
 * 两个可能的地方都读不到：</p>
 * <ul>
 *   <li>{@code WardenSpawnTracker#warningLevel} 是服务端 {@code ServerPlayer} 的私有字段
 *       （{@code ServerPlayer.java:273}），基类 {@code Player#getWardenSpawnTracker()} 在客户端
 *       恒返回 {@code Optional.empty()}（{@code Player.java:1556-1558}），且<b>没有任何同步包</b>；</li>
 *   <li>{@code SculkShriekerBlockEntity#warningLevel} 不在 {@code getUpdateTag} / {@code getUpdatePacket}
 *       里（该类没覆写这两个方法，基类返回空 tag，见 {@code ClientboundLevelChunkPacketData.java:154-159}），
 *       客户端读到的恒为 0。</li>
 * </ul>
 *
 * <p>能拿到的最早信号是方块状态 {@code SculkShriekerBlock#SHRIEKING}：服务端
 * {@code SculkShriekerBlockEntity#shriek}（{@code :120-127}）用 flag 2 设
 * {@code SHRIEKING = true}（会发包）、并 {@code scheduleTick(..., 90)}；90 刻后
 * {@code SculkShriekerBlock#tick}（{@code :70-76}）才清掉它并调 {@code tryRespond} → 可能召唤坚守者。
 * 也就是说：看到 {@code SHRIEKING} 为真，距坚守者出现还有 <b>90 刻 = 4.5 秒</b>，正好是逃生窗口。</p>
 *
 * <p><b>为什么要求 {@code CAN_SUMMON} 也为真：</b>{@code tryShriek}（{@code :102-112}）在
 * {@code canRespond == false} 时照样会尖叫，而世界生成里存在 {@code CAN_SUMMON = false} 的尖啸体
 * （{@code SculkPatchFeature.java:57} 只把世界生成的那批设成 true），它们永远招不出坚守者——
 * 不滤掉就是误报。</p>
 *
 * <p><b>兜底信号（尖啸体漏检 / 是别人把坚守者叫出来的）：</b>范围内已经存在坚守者实体时同样报警
 * （见 {@link #wardenPresent}）。方块扫描有覆盖盲区（玩家刚进区块那一轮还没扫完、或尖啸体在范围外
 * 把人叫来了），这种时候还在原地挖就是等死，所以实体在范围内一律按「必须马上走」处理。</p>
 *
 * <p><b>扫描方式（性能口径）：</b>不逐格读方块。每刻只处理<b>一个</b>区块，用
 * {@code LevelChunk#getBlockEntities()} 遍历方块实体找尖啸体（客户端区块里的方块实体对象是齐的：
 * 区块包 {@code ClientboundLevelChunkPacketData:45-47} 把整份方块实体表都发下来，客户端在
 * {@code LevelChunk.replaceWithPacketData:548-555} 里以 IMMEDIATE 建好）。15 格见方只跨 3×3 = 9 个
 * 区块，一轮 9 刻（0.45 秒）—— 远短于 90 刻的逃生窗口。已知坐标每刻复查一次方块状态（只有几个坐标，
 * 常量级开销），因此从「开始尖叫」到报警最多迟 1 刻。</p>
 *
 * <p><b>上升沿而不是电平：</b>尖啸体的 {@code SHRIEKING} 会保持 90 刻，只看电平会连着 90 刻重复报警。
 * 这里按坐标记「上一刻是否在尖叫」，只在 false → true 那一次返回 true；同一坐标停止尖叫后重新尖叫
 * 会再次报警（那是一次新的警告等级提升）。</p>
 *
 * <p>该类只做检测，不含任何动作：逃离动作由状态机负责（复用「前往野外」那条既有传送链路）。</p>
 */
public final class WardenWarningGuard {

    /**
     * 警报类型。
     *
     * <ul>
     *   <li>{@link #SUMMONING} 尖啸体正在尖叫：坚守者<b>准备出现</b>，还有 90 刻（4.5 秒）才可能钻出来；</li>
     *   <li>{@link #PRESENT} 范围内已经有坚守者实体：预警没抢到（或不是我们触发的），已经在场。</li>
     * </ul>
     *
     * <p>两者都要立刻逃离，分开只为播报说实话（不把「已经在场」说成「准备出现」）。</p>
     */
    public enum Alarm {
        /** 无警报 */
        NONE,
        /** 尖啸体正在尖叫，坚守者准备出现 */
        SUMMONING,
        /** 坚守者已经在范围内 */
        PRESENT
    }

    /**
     * 扫描边长（格）：用户 2026-09-18 口径「玩家自身附近 15x15」。
     *
     * <p>以玩家所在方块为中心、每个方向 {@link #SCAN_HALF} 格的立方体。水平与垂直用同一个半边长，
     * 因为坚守者是<b>贴着自己脚下召唤</b>的（{@code SculkShriekerBlockEntity} 的
     * {@code WARDEN_SPAWN_RANGE_Y = 6}，见 {@code :46}），垂直方向不需要更大。</p>
     */
    public static final int SCAN_SIZE = 15;

    /** 扫描半边长（格）：15 / 2 = 7，即水平 ±7、垂直 ±7。 */
    private static final int SCAN_HALF = SCAN_SIZE / 2;

    /** 扫描覆盖的区块半径：15 格最多跨相邻两个区块，取 1 即够（{@code (7 + 15) / 16}）。 */
    private static final int CHUNK_RADIUS = (SCAN_HALF + 15) / 16;

    /** 一轮扫描的区块数：每刻扫一个，9 刻扫完一轮。 */
    private static final int CHUNK_COUNT = (CHUNK_RADIUS * 2 + 1) * (CHUNK_RADIUS * 2 + 1);

    private final Minecraft mc = Minecraft.getInstance();

    /**
     * 已知尖啸体：坐标 → 上一刻是否在尖叫（上升沿判定用）。
     *
     * <p>出圈或不再是尖啸体的坐标在复查时剔除，因此表的大小只与「身边实际有多少尖啸体」有关，
     * 不会随走动一直涨。</p>
     */
    private final Map<BlockPos, Boolean> tracked = new HashMap<>();

    /** 本轮扫描进度（0 .. {@link #CHUNK_COUNT} - 1）。 */
    private int scanCursor;

    /** 扫描轮次锚点：玩家所在区块坐标，换了就重开一轮（进度与坐标一一对应，不能跨区块续扫）。 */
    private int anchorChunkX = Integer.MIN_VALUE;
    private int anchorChunkZ = Integer.MIN_VALUE;

    /**
     * 每刻推进一次。
     *
     * @return 本刻的警报类型；{@link Alarm#NONE} 表示一切正常
     */
    public Alarm tick() {
        ClientLevel level = mc.level;
        LocalPlayer player = mc.player;
        if (level == null || player == null) {
            reset();
            return Alarm.NONE;
        }
        // 1) 补充名单：每刻扫一个区块（未加载的区块直接跳过，不用会生成空区块的那个入口）
        scanNextChunk(level, player);
        // 2) 复查名单：这一步才是报警来源，与扫描进度无关，因此新发现的尖啸体当刻就能报
        if (checkTracked(level, player)) return Alarm.SUMMONING;
        // 3) 兜底：坚守者已经在场（尖啸被漏检，或别人把它叫出来的）
        return wardenPresent(level, player) ? Alarm.PRESENT : Alarm.NONE;
    }

    /** 关模块 / 换世界时清空，避免带着上一处的坐标与状态回不来。 */
    public void reset() {
        tracked.clear();
        scanCursor = 0;
        anchorChunkX = Integer.MIN_VALUE;
        anchorChunkZ = Integer.MIN_VALUE;
    }

    /**
     * 扫一个区块的方块实体，把范围内的幽匿尖啸体补进名单。
     *
     * <p>用方块实体表而不是逐格读方块，是因为一个区块有 32768 个坐标，而方块实体通常只有个位数；
     * 逐格扫 15 格见方（3×3 区块）也要 11 万次读，方块实体表只要几十次键值遍历。</p>
     *
     * <p>新坐标一律先记为「上一刻没在尖叫」（{@link #tracked} 的默认值），这样紧接着的复查会把
     * 「发现时它已经在尖叫」当成一次上升沿报警 —— 否则刚好在尖叫中才被发现的尖啸体会被漏掉一轮。</p>
     */
    private void scanNextChunk(ClientLevel level, LocalPlayer player) {
        BlockPos origin = player.blockPosition();
        int originChunkX = SectionPos.blockToSectionCoord(origin.getX());
        int originChunkZ = SectionPos.blockToSectionCoord(origin.getZ());
        if (originChunkX != anchorChunkX || originChunkZ != anchorChunkZ) {
            anchorChunkX = originChunkX;
            anchorChunkZ = originChunkZ;
            scanCursor = 0;
        }
        int side = CHUNK_RADIUS * 2 + 1;
        if (scanCursor >= CHUNK_COUNT) scanCursor = 0;
        int chunkX = originChunkX - CHUNK_RADIUS + scanCursor % side;
        int chunkZ = originChunkZ - CHUNK_RADIUS + scanCursor / side;
        scanCursor++;

        // getChunk(..., loadOrGenerate = false)：未加载返回 null。不能用 Level#getChunk(x, z)，
        // 那个入口 loadOrGenerate = true，客户端会拿回一个空区块（看着像加载了，实则什么都没有）
        ClientChunkCache cache = level.getChunkSource();
        LevelChunk chunk = cache.getChunk(chunkX, chunkZ, ChunkStatus.FULL, false);
        if (chunk == null) return;

        for (BlockEntity blockEntity : chunk.getBlockEntities().values()) {
            // 用方块实体类型先筛，避免为每个方块实体都读一次方块状态
            if (!(blockEntity instanceof SculkShriekerBlockEntity)) continue;
            BlockPos pos = blockEntity.getBlockPos();
            if (!inRange(pos, origin)) continue;
            tracked.putIfAbsent(pos, Boolean.FALSE);
        }
    }

    /**
     * 复查名单里的每一个尖啸体，返回本刻是否出现「开始尖叫」的上升沿。
     *
     * <p>剔除放在同一个循环里：出范围、或被破坏（不再是尖啸体）的坐标直接移出名单。</p>
     */
    private boolean checkTracked(ClientLevel level, LocalPlayer player) {
        BlockPos origin = player.blockPosition();
        boolean alarming = false;
        Iterator<Map.Entry<BlockPos, Boolean>> iterator = tracked.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<BlockPos, Boolean> entry = iterator.next();
            BlockPos pos = entry.getKey();
            if (!inRange(pos, origin)) {
                iterator.remove();
                continue;
            }
            BlockState state = level.getBlockState(pos);
            if (state.getBlock() != Blocks.SCULK_SHRIEKER) {
                iterator.remove();
                continue;
            }
            if (!isWardenSummoning(state)) {
                entry.setValue(Boolean.FALSE);
                continue;
            }
            if (!entry.getValue()) {
                entry.setValue(Boolean.TRUE);
                alarming = true;
            }
        }
        return alarming;
    }

    /**
     * 范围内是否已经有坚守者实体。
     *
     * <p>用实体查找而不是方块扫描：实体存储是按区块段索引的，查询成本与半径无关，每刻查也无所谓；
     * 这一步是「尖啸漏检」的最后一道保险，宁可早走。</p>
     */
    private boolean wardenPresent(ClientLevel level, LocalPlayer player) {
        List<Warden> wardens = level.getEntitiesOfClass(Warden.class,
            player.getBoundingBox().inflate(SCAN_HALF),
            warden -> warden.isAlive() && !warden.isRemoved());
        return !wardens.isEmpty();
    }

    /**
     * 这个尖啸体此刻是不是「会招出坚守者的那种尖叫」。
     *
     * <p>两个方块状态都是同步给客户端的真属性（{@code SculkShriekerBlock.java:52-56} 进
     * {@code createBlockStateDefinition}），因此客户端读到的就是服务端那一刻的值。</p>
     */
    private static boolean isWardenSummoning(BlockState state) {
        if (!state.getValue(SculkShriekerBlock.CAN_SUMMON)) return false;
        return state.getValue(SculkShriekerBlock.SHRIEKING);
    }

    /** 以玩家所在方块为中心、每轴 ±{@link #SCAN_HALF} 格的立方体范围判定。 */
    private static boolean inRange(BlockPos pos, BlockPos origin) {
        return Math.abs(pos.getX() - origin.getX()) <= SCAN_HALF
            && Math.abs(pos.getY() - origin.getY()) <= SCAN_HALF
            && Math.abs(pos.getZ() - origin.getZ()) <= SCAN_HALF;
    }
}
