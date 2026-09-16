package com.yiyiaddon.feature.mining.navigation;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.Settings;
import baritone.api.pathing.goals.GoalTwoBlocks;
import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.platform.identity.BlockIdentifier;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

/**
 * Baritone 寻路中间件
 * 
 * 功能：
 * · 封装 BaritoneAPI 的 #mine 调用（普通模式）
 * · 种子模式提供逐块寻路接口 pathToOre（由 MinerFSM 采集循环驱动）
 * · 寻路异常处理（找不到目标、原地滞留）
 * · 地形防卡死心跳（3分钟位移<5格判定卡死）
 *
 * <p>对应旧项目 {@code mining/navigation/BaritoneExecutor.java}（337 行）；
 * 方法名与落点逐条对齐，Baritone 设置的读写走本项目既有入口
 * （{@code BaritoneAPI.getSettings()} 的强类型字段，与 {@code FarmNav} / {@code BaritoneSettingsPage} 同一入口）。</p>
 */
public final class MiningPathing {

    private final AutoMinerModule module;
    private final Minecraft mc;

    private BlockPos lastPos = BlockPos.ZERO;
    private int stuckTicks = 0;
    private int lastCheckTick = 0;

    private static final int STUCK_CHECK_INTERVAL = 3600; // 3分钟
    private static final int STUCK_DISTANCE_THRESHOLD = 5; // 5格

    public MiningPathing(AutoMinerModule module) {
        this.module = module;
        this.mc = Minecraft.getInstance();
    }

    /**
     * 启动 Baritone 挖矿（普通模式）。
     * 种子模式不经过这里：由 MinerFSM 的种子采集循环逐块 pathToOre + 破坏。
     */
    public void startMining(List<Block> targets) {
        if (targets == null || targets.isEmpty()) {
            module.error("未选择目标矿石，无法启动挖矿");
            return;
        }

        try {
            IBaritone baritone = getBaritone();
            if (baritone == null) {
                module.error("Baritone 未加载");
                return;
            }

            // 普通模式同时挖深层/浅层变种：mine 命令支持多 block 参数
            StringBuilder cmd = new StringBuilder("mine");
            for (Block b : targets) {
                cmd.append(" ").append(BuiltInRegistries.BLOCK.getKey(b));
            }
            baritone.getCommandManager().execute(cmd.toString());

            // 旧项目走 translations.BaritoneChatTranslations.translateBlockId，
            // 本项目等价入口是 platform/identity/BlockIdentifier.localizedBlockName
            String blockId = BuiltInRegistries.BLOCK.getKey(targets.get(0)).toString();
            String firstName = BlockIdentifier.localizedBlockName(blockId);
            if (firstName == null) firstName = blockId;
            String name = targets.size() > 1 ? firstName + " §8等 " + targets.size() + " 种变体" : firstName;
            module.info("§aBaritone 已启动挖掘：" + name);

            // 重置卡死检测
            if (mc.player != null) {
                lastPos = mc.player.blockPosition();
            }
            stuckTicks = 0;
            lastCheckTick = 0;

        } catch (Throwable e) {
            module.error("Baritone 调用失败：" + e.getMessage());
        }
    }

    /**
     * 种子模式：寻路到指定预测矿位置（走到跟前，破坏由 MinerFSM 负责）
     *
     * @param pos 预测矿位置
     * @return 是否成功发起寻路
     */
    public boolean pathToOre(BlockPos pos) {
        try {
            IBaritone baritone = getBaritone();
            if (baritone == null) {
                return false;
            }
            baritone.getCustomGoalProcess().setGoalAndPath(new GoalTwoBlocks(pos));
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * 采集引擎是否活跃：普通模式 mine 进程运行中，或种子模式寻路目标激活。
     * 用于「进程意外退出才重启」的自愈判断，避免打断正常挖矿。
     */
    public boolean isMiningActive() {
        try {
            IBaritone baritone = getBaritone();
            if (baritone == null) return false;
            return baritone.getMineProcess().isActive() || baritone.getCustomGoalProcess().isActive();
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * 停止 Baritone
     */
    public void stop() {
        try {
            IBaritone baritone = getBaritone();
            if (baritone == null) return;

            baritone.getCommandManager().execute("stop");
            baritone.getPathingBehavior().cancelEverything();

        } catch (Throwable e) {
            // 停止失败不影响后续，忽略
        }
    }

    /**
     * 批量应用 Baritone 设置（模块启动时调用）
     */
    public void applySettings(boolean avoidLava, boolean mobAvoidance, int mobAvoidanceRadius,
                              boolean allowBreak, boolean allowPlace, int maxFallHeight,
                              boolean pauseMiningForFallingBlocks,
                              boolean allowInventory, boolean autoTool, boolean sprintAscends,
                              boolean allowParkour, boolean allowParkourPlace,
                              boolean allowDiagonalAscend, boolean allowDiagonalDescend,
                              boolean allowOnlyExposedOres,
                              int allowOnlyExposedOresDistance, int minYLevelWhileMining,
                              int maxYLevelWhileMining, int mineMaxOreLocationsCount,
                              boolean blacklistClosestOnFailure, boolean legitMine,
                              int legitMineYLevel, boolean legitMineIncludeDiagonals) {
        try {
            Settings settings = BaritoneAPI.getSettings();
            List<Block> blocksToAvoid = new ArrayList<>(settings.blocksToAvoid.value);
            if (avoidLava && !blocksToAvoid.contains(Blocks.LAVA)) blocksToAvoid.add(Blocks.LAVA);
            if (!avoidLava) blocksToAvoid.removeIf(block -> block == Blocks.LAVA);
            settings.blocksToAvoid.value = blocksToAvoid;
            
            // 已确认存在的设置（26.1.2 官方映射下逐个核实）
            settings.allowSprint.value = true;           // 启用疾跑
            settings.allowBreak.value = allowBreak;      // 是否破坏方块
            settings.allowPlace.value = allowPlace;      // 是否放置方块
            settings.avoidance.value = mobAvoidance;
            settings.mobAvoidanceRadius.value = mobAvoidanceRadius;
            settings.maxFallHeightNoWater.value = maxFallHeight;
            settings.pauseMiningForFallingBlocks.value = pauseMiningForFallingBlocks;
            settings.allowInventory.value = allowInventory;
            settings.autoTool.value = autoTool;
            settings.assumeExternalAutoTool.value = false; // 关闭「外部工具假设」，确保 Baritone 自身 autoTool 真正生效
            settings.sprintAscends.value = sprintAscends;
            settings.allowParkour.value = allowParkour;
            settings.allowParkourPlace.value = allowParkourPlace;
            settings.allowDiagonalAscend.value = allowDiagonalAscend;
            settings.allowDiagonalDescend.value = allowDiagonalDescend;
            settings.allowOnlyExposedOres.value = allowOnlyExposedOres;
            settings.allowOnlyExposedOresDistance.value = allowOnlyExposedOresDistance;
            settings.minYLevelWhileMining.value = minYLevelWhileMining;
            settings.maxYLevelWhileMining.value = maxYLevelWhileMining;
            settings.mineMaxOreLocationsCount.value = mineMaxOreLocationsCount;
            settings.blacklistClosestOnFailure.value = blacklistClosestOnFailure;
            settings.legitMine.value = legitMine;
            settings.legitMineYLevel.value = legitMineYLevel;
            settings.legitMineIncludeDiagonals.value = legitMineIncludeDiagonals;
            
            // 优先挖掘附近矿石的设置
            settings.mineGoalUpdateInterval.value = module.getMineGoalUpdateInterval();  // 使用配置值
            settings.blockReachDistance.value = 4.5f;    // 缩小交互距离，优先近处
            
        } catch (Throwable e) {
            // 设置应用失败不影响主体逻辑，下次启动重新应用
        }
    }

    /**
     * 动态更新单个设置
     */
    public void updateSetting(String key, Object value) {
        try {
            Settings settings = BaritoneAPI.getSettings();
            
            if (key.equals("allowBreak")) settings.allowBreak.value = (Boolean) value;
            else if (key.equals("allowPlace")) settings.allowPlace.value = (Boolean) value;
            else if (key.equals("avoidLava")) {
                List<Block> blocksToAvoid = new ArrayList<>(settings.blocksToAvoid.value);
                if ((Boolean) value && !blocksToAvoid.contains(Blocks.LAVA)) blocksToAvoid.add(Blocks.LAVA);
                if (!(Boolean) value) blocksToAvoid.removeIf(block -> block == Blocks.LAVA);
                settings.blocksToAvoid.value = blocksToAvoid;
            }
            else if (key.equals("avoidance")) settings.avoidance.value = (Boolean) value;
            else if (key.equals("mobAvoidanceRadius")) settings.mobAvoidanceRadius.value = (Integer) value;
            else if (key.equals("maxFallHeightNoWater")) settings.maxFallHeightNoWater.value = (Integer) value;
            else if (key.equals("pauseMiningForFallingBlocks")) settings.pauseMiningForFallingBlocks.value = (Boolean) value;
            else if (key.equals("allowInventory")) settings.allowInventory.value = (Boolean) value;
            else if (key.equals("autoTool")) {
                settings.autoTool.value = (Boolean) value;
                settings.assumeExternalAutoTool.value = false; // 关闭「外部工具假设」，确保 autoTool 真正生效
            }
            else if (key.equals("sprintAscends")) settings.sprintAscends.value = (Boolean) value;
            else if (key.equals("allowParkour")) settings.allowParkour.value = (Boolean) value;
            else if (key.equals("allowParkourPlace")) settings.allowParkourPlace.value = (Boolean) value;
            else if (key.equals("allowDiagonalAscend")) settings.allowDiagonalAscend.value = (Boolean) value;
            else if (key.equals("allowDiagonalDescend")) settings.allowDiagonalDescend.value = (Boolean) value;
            else if (key.equals("allowOnlyExposedOres")) settings.allowOnlyExposedOres.value = (Boolean) value;
            else if (key.equals("allowOnlyExposedOresDistance")) settings.allowOnlyExposedOresDistance.value = (Integer) value;
            else if (key.equals("minYLevelWhileMining")) settings.minYLevelWhileMining.value = (Integer) value;
            else if (key.equals("maxYLevelWhileMining")) settings.maxYLevelWhileMining.value = (Integer) value;
            else if (key.equals("mineMaxOreLocationsCount")) settings.mineMaxOreLocationsCount.value = (Integer) value;
            else if (key.equals("blacklistClosestOnFailure")) settings.blacklistClosestOnFailure.value = (Boolean) value;
            else if (key.equals("legitMine")) settings.legitMine.value = (Boolean) value;
            else if (key.equals("legitMineYLevel")) settings.legitMineYLevel.value = (Integer) value;
            else if (key.equals("legitMineIncludeDiagonals")) settings.legitMineIncludeDiagonals.value = (Boolean) value;
            else if (key.equals("mineGoalUpdateInterval")) settings.mineGoalUpdateInterval.value = (Integer) value;
            
        } catch (Throwable e) {
            // 单设置更新失败不影响整体
        }
    }

    /**
     * 更新搭路方块白名单
     */
    public void updatePlaceBlocks(List<Block> blocks) {
        try {
            Settings settings = BaritoneAPI.getSettings();
            settings.acceptableThrowawayItems.value = new ArrayList<>(
                blocks.stream()
                    .map(Block::asItem)
                    .filter(item -> item != Items.AIR)
                    .toList()
            );
        } catch (Throwable e) {
            // 忽略
        }
    }

    /**
     * 检测 Baritone 是否卡死（原地滞留超过3分钟且位移<5格）
     */
    public boolean isStuck() {
        if (mc.player == null) return false;

        try {
            IBaritone baritone = getBaritone();
            if (baritone == null) return false;

            // 只有在寻路状态下才检测卡死
            if (!baritone.getPathingBehavior().isPathing()) {
                return false;
            }

            stuckTicks++;

            // 每3分钟检测一次
            if (stuckTicks - lastCheckTick < STUCK_CHECK_INTERVAL) {
                return false;
            }

            BlockPos currentPos = mc.player.blockPosition();
            double distance = Math.sqrt(currentPos.distSqr(lastPos));

            lastCheckTick = stuckTicks;
            lastPos = currentPos;

            // 3分钟内位移小于5格，判定卡死
            return distance < STUCK_DISTANCE_THRESHOLD;

        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * Baritone 是否正在寻路
     */
    public boolean isPathing() {
        try {
            IBaritone baritone = getBaritone();
            if (baritone == null) return false;
            return baritone.getPathingBehavior().isPathing();
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * 自定义目标进程是否仍活跃（含寻路计算中）。
     * 用于物流寻路（卸货/补给/修补）的重发判断：
     * isPathing 在「路径计算中」为 false，若用它做重发条件，会在计算期间反复重发目标、重置计算，导致傻站。
     * isActive 从 setGoalAndPath 起即为 true，直到目标完成/取消才变 false，正好区分「算路中」与「真的断了」。
     */
    public boolean isCustomGoalActive() {
        try {
            IBaritone baritone = getBaritone();
            if (baritone == null) return false;
            return baritone.getCustomGoalProcess().isActive();
        } catch (Throwable e) {
            return false;
        }
    }

    /**
     * 获取 Baritone 实例（供状态机调用）。失败返回 null。
     */
    public IBaritone getBaritoneInstance() {
        try {
            return BaritoneAPI.getProvider().getPrimaryBaritone();
        } catch (Throwable e) {
            return null;
        }
    }

    /**
     * 获取 Baritone 实例（内部使用）。失败返回 null。
     */
    private IBaritone getBaritone() {
        try {
            return BaritoneAPI.getProvider().getPrimaryBaritone();
        } catch (Throwable e) {
            return null;
        }
    }
}
