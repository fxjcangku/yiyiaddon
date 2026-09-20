package com.yiyiaddon.feature.mining.service;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.chunk.LevelChunk;

/**
 * 指令管理与防卡死网络中心
 * 
 * 核心功能：
 * · 发送聊天指令（/rtp, /home 等）
 * · Anti-Lag & Loading Check（区块加载检测）
 * · 防虚空坠落保护（Y轴极速下降检测）
 * · 服务器 Tick 响应恢复检测
 * 
 * 阻塞机制：
 * 发送指令后，isCommandExecuting() 返回 true，阻止状态机推进。
 * 直到区块加载完成、玩家安全落地、服务器响应恢复，才返回 false。
 *
 * <p>对应旧项目 {@code mining/command/CommandManager.java}（326 行）逐条移植；
 * 唯一换掉的依赖是开箱式 GUI 点击（旧项目走 {@code Screen}）之外的原文一律保留。</p>
 */
public final class ServerCommandRunner {

    private final AutoMinerModule module;
    private final Minecraft mc;

    private boolean executing = false;
    private int executeTick = 0;
    private int maxWaitTicks = 600; // 动态设置，默认30秒
    private int stationaryTicks = 0; // 位置静止累计tick（真正的「静止超过5 tick」检测）
    /** 瞬移类指令：不等九宫格区块加载，位置跳变 + 落地稳定即视为传送完成 */
    private boolean quickTeleport = false;
    /** 瞬移类指令的落地稳定计数 */
    private int landedTicks = 0;
    /** 瞬移类指令：是否已经看到位置跳变（没跳变过就不允许解除阻塞） */
    private boolean teleportedSeen = false;

    // 区块加载检测
    private BlockPos lastPlayerPos = BlockPos.ZERO;
    private int chunksLoadedCount = 0;
    private static final int CHUNKS_LOADED_REQUIRED = 3;

    // 虚空坠落检测
    private double lastY = 0;
    private int rapidFallTicks = 0;
    private static final double RAPID_FALL_THRESHOLD = 2.0; // 每tick下降超过2格判定为快速坠落

    // GUI等待与自动点击
    private boolean waitingForGui = false;
    private int guiWaitTicks = 0;
    private static final int GUI_MAX_WAIT_TICKS = 100; // 5秒超时

    /** 本模块最后发出的服务器指令原文（不含前导斜杠）与它的 tick */
    private String lastOwnCommand = "";
    private int lastOwnCommandTick = -100000;

    /**
     * 「自己发的指令」认定窗口（刻）：发包观测到指令的时机与这里记录的时机最多差一刻
     * （网络线程入队 → 主线程每刻出队），留 5 刻已经绰绰有余。
     */
    private static final int OWN_COMMAND_WINDOW_TICKS = 5;

    public ServerCommandRunner(AutoMinerModule module) {
        this.module = module;
        this.mc = Minecraft.getInstance();
    }

    public void reset() {
        executing = false;
        executeTick = 0;
        stationaryTicks = 0;
        lastPlayerPos = BlockPos.ZERO;
        chunksLoadedCount = 0;
        lastY = 0;
        rapidFallTicks = 0;
        waitingForGui = false;
        guiWaitTicks = 0;
        quickTeleport = false;
        landedTicks = 0;
        teleportedSeen = false;
    }

    // ═══════════════════════════════════════════════════════════════════
    //  指令执行
    // ═══════════════════════════════════════════════════════════════════

    /**
     * 执行聊天指令（如 /rtp, /home kuang）
     * 
     * 发送后进入阻塞状态，直到传送完成并满足安全条件
     * 标点归一：全角空格/全角斜杠（中文输入法常见）→ 半角，
     * 自动补斜杠、去多斜杠、修正「/ rtp」这种斜杠后带空格的写法。
     */
    public void executeCommand(String command) {
        executeCommand(command, false);
    }

    /**
     * 执行聊天指令（如 /rtp, /home kuang）
     *
     * <p>发送后进入阻塞状态，直到传送完成并满足安全条件。
     * 标点归一：全角空格/全角斜杠（中文输入法常见）→ 半角，自动补斜杠、去多斜杠、
     * 修正「/ rtp」这种斜杠后带空格的写法。</p>
     *
     * @param allowGuiClick 是否启用 RTP 的 GUI 自动点击。只有 RTP 需要（插件会弹选单，得等它出现
     *                      再点关键词槽位）；/home、/res tp 这类是瞬移，等 GUI 纯属白等
     *                      （用户 2026-09-18：「传送回去卸货站在原地的时间太久了」——
     *                       旧实现无条件进 GUI 分支，每次卸货固定白站 {@code GUI_MAX_WAIT_TICKS} 刻）
     */
    public void executeCommand(String command, boolean allowGuiClick) {
        String cmd = normalize(command);
        if (cmd == null) {
            return;
        }

        // 去掉前缀/后发送
        markOwnCommand(cmd.substring(1));
        mc.player.connection.sendCommand(cmd.substring(1));

        // 从模块获取传送等待时长（秒转tick）
        maxWaitTicks = module.getTeleportDelay() * 20;

        executing = true;
        executeTick = 0;
        stationaryTicks = 0;
        lastPlayerPos = mc.player.blockPosition();
        lastY = mc.player.getY();
        chunksLoadedCount = 0;
        rapidFallTicks = 0;
        // 瞬移类指令（/home、/res tp）落地即到，跳过「九宫格区块加载」这道为 RTP 远距离传送
        // 准备的门槛：卸货点就在家，周围区块一直加载着，等它只是白等（见方法注释）
        quickTeleport = !allowGuiClick;
        landedTicks = 0;
        teleportedSeen = false;

        // 如果启用RTP GUI自动点击，进入GUI等待状态
        if (allowGuiClick && module.isRtpGuiEnabled()) {
            waitingForGui = true;
            guiWaitTicks = 0;
        }
    }

    /**
     * 只发指令、不进阻塞等待：给「发一条指令只为打开某个菜单」的流程用（自用模式的 {@code /cd}）。
     *
     * <p>{@link #executeCommand} 发完会进入阻塞态等传送落地，而 {@code /cd} 只是打开一个菜单、
     * 玩家一动不动，走那条路会一路等到超时。这里共用同一套标点归一与「自家指令」打点
     * （{@code CLIENT_COMMAND} 派发时不会把自己发的当成玩家手动干预），只是不发车。</p>
     */
    public void sendMenuCommand(String command) {
        String cmd = normalize(command);
        if (cmd == null) return;
        markOwnCommand(cmd.substring(1));
        mc.player.connection.sendCommand(cmd.substring(1));
    }

    /**
     * 指令标点归一：全角空格(　)/全角斜杠(／)转半角、补斜杠、去多斜杠、修正「/ rtp」这种
     * 斜杠后带空格的写法；无效指令返回 {@code null}。
     *
     * <p>{@link #executeCommand} 与 {@link #sendMenuCommand} 共用，两条路的指令口径因此完全一致。</p>
     */
    private String normalize(String command) {
        if (mc.player == null || command == null) {
            return null;
        }

        String cmd = command.replace('　', ' ').replace('／', '/').trim();
        if (cmd.isEmpty()) {
            return null;
        }

        // 自动补充斜杠：如果命令不以/开头，自动添加
        if (!cmd.startsWith("/")) {
            cmd = "/" + cmd;
        }

        // 移除多余的斜杠（如果有人输入 //rtp）
        while (cmd.startsWith("//")) {
            cmd = cmd.substring(1);
        }

        // 修正「/ rtp」斜杠后带空格的写法：服务器不接受斜杠与指令名之间有空格
        cmd = cmd.replaceFirst("^/\\s+", "/");
        if (cmd.length() <= 1) {
            return null;
        }
        return cmd;
    }

    /**
     * 记下「这条指令是我们自己发的」（发送前调用）。
     *
     * <p>用途：{@code CLIENT_COMMAND} 事件对所有出站指令一视同仁，模块得把自家指令（前往挖矿 /
     * 卸货 / 补给 / 挂机 / 死亡返回）从「玩家手动指令」里摘出去；否则自己发一条就被自己判成
     * 手动干预，两边无限互相触发。</p>
     */
    private void markOwnCommand(String command) {
        lastOwnCommand = command;
        lastOwnCommandTick = mc.player == null ? -100000 : mc.player.tickCount;
    }

    /**
     * 这条指令是不是我们自己刚发出的（原文完全相同 + {@link #OWN_COMMAND_WINDOW_TICKS} 刻窗口内）。
     *
     * <p>比原文而不是「窗口内一律算自己的」：玩家完全可能在我们发指令的同一秒敲 /spawn，
     * 只按时间窗口会把它一起吞掉——那正是用户报的那个 bug（「输入了 /spawn 脚本还在那边挖」）。</p>
     */
    public boolean isOwnCommand(String command) {
        if (command == null || mc.player == null) return false;
        if (!command.equals(lastOwnCommand)) return false;
        return mc.player.tickCount - lastOwnCommandTick <= OWN_COMMAND_WINDOW_TICKS;
    }

    /**
     * 是否正在等 RTP 选单出现（等到了 / 超时后都为 false）。
     *
     * <p>给界面静默用：RTP 选单是<b>服务端推过来</b>的容器，既不是玩家手动开的、也不是本模块发包开的，
     * {@code MiningContainer#isOperatingContainer()}（只认「我方发包开箱」）认不出它，
     * 于是界面会真的弹出来抢走鼠标（用户 2026-09-19：「rtp 打开 gui 点击 之前打开 gui 是没动画的
     * 现在有动画还抢鼠标」）。</p>
     */
    public boolean isWaitingForGui() {
        return waitingForGui;
    }

    /**
     * 指令是否正在执行中（用于阻塞状态机）
     */
    public boolean isCommandExecuting() {
        if (!executing) return false;

        executeTick++;

        // 优先处理GUI自动点击
        if (waitingForGui) {
            return handleGuiAutoClick();
        }

        // 超时保护（使用动态设置的等待时长）
        if (executeTick > maxWaitTicks) {
            module.error("§c传送等待超时");
            executing = false;
            return false;
        }

        // 前 6 tick 等待服务器响应
        if (executeTick < 6) {
            return true;
        }

        // 瞬移类指令（/home、/res tp、挂机点）：位置跳变过 + 落地稳定 2 刻即算传送完成。
        // 不再等「九宫格区块加载」——那是给 RTP 远距离传送准备的（落地处可能还没加载），
        // 而卸货点就在家里、区块一直加载着，等它纯属白等（见方法参数注释）
        if (quickTeleport) {
            BlockPos now = mc.player.blockPosition();
            if (!now.equals(lastPlayerPos)) {
                lastPlayerPos = now;
                teleportedSeen = true;
                landedTicks = 0;
                return true; // 刚落点，给它 2 刻稳定（位置修正 / 客户端世界重建）
            }
            // 还没跳过位置：指令可能还在路上，继续等（绝不提前解除，否则会在旧位置往箱子那边走）
            if (!teleportedSeen) return true;
            if (checkLandingSafe() && ++landedTicks >= 2) {
                executing = false;
                return false;
            }
            return true;
        }

        // 三重检测：区块加载 + 安全落地 + 服务器响应
        boolean chunksReady = checkChunksLoaded();
        boolean landingSafe = checkLandingSafe();
        boolean serverResponsive = checkServerResponsive();

        if (chunksReady && landingSafe && serverResponsive) {
            executing = false;
            return false;
        }

        return true;
    }

    // ═══════════════════════════════════════════════════════════════════
    //  安全检测
    // ═══════════════════════════════════════════════════════════════════

    /**
     * 检测区块是否加载完成
     * 
     * 策略：连续5 tick周围9x9区块都已加载
     */
    private boolean checkChunksLoaded() {
        LocalPlayer player = mc.player;
        ClientLevel level = mc.level;
        if (player == null || level == null) return false;

        BlockPos pos = player.blockPosition();
        int chunkX = pos.getX() >> 4;
        int chunkZ = pos.getZ() >> 4;

        int loadedCount = 0;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                int cx = chunkX + dx;
                int cz = chunkZ + dz;
                LevelChunk chunk = level.getChunk(cx, cz);
                
                if (chunk != null && !chunk.isEmpty()) {
                    loadedCount++;
                }
            }
        }

        chunksLoadedCount = (loadedCount >= 9) ? chunksLoadedCount + 1 : 0;
        return chunksLoadedCount >= CHUNKS_LOADED_REQUIRED;
    }

    /**
     * 检测玩家是否安全落地（防虚空坠落）
     * 
     * 策略：
     * · 连续3 tick 不再极速下降（每tick下降<2格）
     * · 且玩家在地面上或在水中
     */
    private boolean checkLandingSafe() {
        LocalPlayer player = mc.player;
        if (player == null) return false;

        double currentY = player.getY();
        double deltaY = lastY - currentY;
        lastY = currentY;

        // 极速下降检测
        if (deltaY > RAPID_FALL_THRESHOLD) {
            rapidFallTicks++;
        } else {
            rapidFallTicks = 0;
        }

        // 如果连续极速下降超过10 tick，判定为掉虚空
        if (rapidFallTicks > 10) {
            return false;
        }

        // 玩家在地面或水中
        boolean onGround = player.onGround() || player.isInWater() || player.isInLava();

        return onGround && rapidFallTicks == 0;
    }

    /**
     * 检测服务器是否响应正常
     * 
     * 策略：
     * · 玩家位置发生变化（说明服务器在同步位置）→ 清除静止计数，继续等
     * · 玩家落地且连续静止超过5 tick → 判定传送流程结束
     */
    private boolean checkServerResponsive() {
        LocalPlayer player = mc.player;
        if (player == null) return false;

        BlockPos currentPos = player.blockPosition();

        // 位置变化说明服务器在响应，重置静止计数
        if (!currentPos.equals(lastPlayerPos)) {
            lastPlayerPos = currentPos;
            stationaryTicks = 0;
            return false; // 还在移动，继续等
        }

        // 位置静止超过 2 tick，且玩家在地面
        stationaryTicks++;
        return stationaryTicks >= 2 && player.onGround();
    }

    // ═══════════════════════════════════════════════════════════════════
    //  GUI自动点击
    // ═══════════════════════════════════════════════════════════════════

    /**
     * 处理RTP GUI自动点击
     * 
     * 策略：
     * · 等待GUI打开（检测mc.screen不为null）
     * · 遍历所有按钮，查找文本包含关键词的按钮
     * · 纯文本匹配：移除所有颜色代码（§x）和空格后进行比对
     * · 找到后模拟点击并关闭GUI
     * 
     * @return true=继续阻塞，false=GUI处理完毕
     */
    private boolean handleGuiAutoClick() {
        guiWaitTicks++;

        // 超时保护
        if (guiWaitTicks > GUI_MAX_WAIT_TICKS) {
            waitingForGui = false;
            return true; // 继续等待传送完成
        }

        String keyword = module.getRtpGuiKeyword();
        if (keyword == null || keyword.isEmpty()) {
            waitingForGui = false;
            return true;
        }

        // 静默容器模式下没有 Screen，改为检测 containerMenu 是否已同步为容器；
        // containerId == 0 是玩家自身背包，说明传送菜单还没打开，继续等。
        if (mc.player == null || mc.player.containerMenu == null
            || mc.player.containerMenu.containerId == 0 || mc.gameMode == null) {
            return true;
        }

        // 标准化关键词（移除颜色和空格）
        String normalizedKeyword = stripFormatting(keyword);

        AbstractContainerMenu menu = mc.player.containerMenu;

        for (Slot slot : menu.slots) {
            ItemStack stack = slot.getItem();
            if (stack.isEmpty()) continue;

            String itemText = stripFormatting(stack.getHoverName().getString());
            if (!itemText.contains(normalizedKeyword)) continue;

            mc.gameMode.handleContainerInput(menu.containerId, slot.index, 0, ContainerInput.PICKUP, mc.player);
            waitingForGui = false;
            return true;
        }

        // 未找到匹配槽位，继续等待
        return true;
    }

    /**
     * 移除Minecraft颜色代码（§x）和所有空格
     */
    private String stripFormatting(String text) {
        if (text == null) return "";
        return text.replaceAll("§.", "").replaceAll("\\s+", "");
    }
}
