package com.yiyiaddon.feature.autochest;

import com.google.gson.JsonObject;
import com.yiyiaddon.command.ClientCommand;
import com.yiyiaddon.config.identity.IdentityTargetConfig;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.autochest.command.AutoChestCommand;
import com.yiyiaddon.feature.autochest.config.AutoChestSettings;
import com.yiyiaddon.feature.autochest.fsm.AutoChestStateMachine;
import com.yiyiaddon.feature.autochest.render.AutoChestRenderer;
import com.yiyiaddon.feature.autochest.scan.ContainerScanner;
import com.yiyiaddon.feature.autochest.service.ChestInteractionService;
import com.yiyiaddon.feature.autochest.service.PathingService;
import com.yiyiaddon.feature.autochest.ui.AutoChestPage;
import com.yiyiaddon.model.autochest.ChestTarget;
import com.yiyiaddon.model.autochest.ContainerType;
import com.yiyiaddon.model.autochest.ContainerTypeRegistry;
import com.yiyiaddon.model.autochest.ScanMode;
import com.yiyiaddon.model.autochest.WithdrawMode;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.repository.autochest.ChestPointStore;
import com.yiyiaddon.repository.autochest.ContainerRecordStore;
import com.yiyiaddon.service.identity.IdentityService;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.WorldOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 自动箱子模块（旧项目 {@code autochest/AutoChestModule.java} 的移植）。
 *
 * <p><b>用户交互资产：</b>模块中文名 {@code 自动箱子}、分类 {@code 辅助}、description、全部播报文案、
 * 自检缺项文案、面板按钮与说明章节均沿用旧项目原文，禁止改写。</p>
 *
 * <p>流程与旧项目一致：扫描合法容器 → 发现目标 → 判断是否已处理 → 锁定 → 移动/等待 → 开箱
 * → 读取真实 Slot → 精确识别 ItemStack → 按取物模式取物 → 关箱 → 保存记录 → ESP 变红 → 寻找下一个。
 * 业务实现复用本项目既有产物：状态机 {@link AutoChestStateMachine}、扫描 {@link ContainerScanner}、
 * 交互 {@link ChestInteractionService}、寻路 {@link PathingService}、点位 {@link ChestPointStore}、
 * 记录 {@link ContainerRecordStore}；目标物品统一来自 ID 配置管理（{@code IdentityTargetConfig}），
 * 本模块只消费，不建立第二套物品数据库。</p>
 *
 * <p>与旧项目的接口差异：旧项目用 Meteor 的事件与自检入口，本项目统一走
 * {@link Module} 生命周期（{@code selfCheck} 由 {@link ModuleManager} 在启用前调用并播报缺项）。</p>
 */
public final class AutoChestModule extends Module implements AutoChestStateMachine.Callbacks {

    /** 模块 ID，同时作为状态文件键、快捷键键名后缀与世界渲染层标识 */
    public static final String MODULE_ID = "autochest";

    /** 播报前缀使用的模块名：旧项目 {@code AutoChestModule} 的模块名为 {@code 自动箱子} */
    public static final String MESSAGE_MODULE = "自动箱子";

    /**
     * 视角同步：每 tick 最多转多少度（约 0.3 秒转 90°，不是瞬移式甩头）。
     */
    private static final float VIEW_TURN_STEP = 15.0f;

    /** 视角同步的作用距离：还没走到容器跟前就保持走路朝向，避免一路倒着走过去 */
    private static final double VIEW_SYNC_RANGE = 6.0;

    /** 开箱前的朝向容差（度）：进入这个范围才算「看着箱子」，允许发包开箱 */
    private static final float VIEW_ALIGN_TOLERANCE = 25.0f;

    /** 主背包 + 快捷栏格数（{@code Inventory#getItem} 的存储下标上界） */
    private static final int INVENTORY_STORAGE_SLOTS = 36;

    private final Minecraft mc = Minecraft.getInstance();

    /** 全部设置项的数据载体（21 项，文案与默认值来自旧项目） */
    private final AutoChestSettings settings = new AutoChestSettings();

    private final ContainerScanner scanner;
    private final ContainerRecordStore recordStore;
    private final ChestPointStore pointStore;
    private final ChestInteractionService interaction;
    private final PathingService pathing;
    private final AutoChestStateMachine stateMachine;
    private final AutoChestRenderer renderer;

    /** 颜色设置的取色载体：调色板直接改这三个对象，随后同步回设置项 */
    private final EspColor unprocessedColor = new EspColor();
    private final EspColor processedColor = new EspColor();
    private final EspColor processingColor = new EspColor();

    /** 扫描周期计数 */
    private int scanTick;

    /** 容器破坏对账缓存：上一轮仍见到、这一轮消失的位置视为被破坏 */
    private final Set<BlockPos> lastSeenContainers = new HashSet<>();

    public AutoChestModule() {
        super(MODULE_ID, MESSAGE_MODULE, "assist",
                "扫描并自动处理附近容器，取走ID配置中的目标物品。详细参考下面使用说明。");

        this.scanner = new ContainerScanner(mc, settings::enabledTypes);
        this.recordStore = new ContainerRecordStore(mc);
        this.pointStore = new ChestPointStore(mc);
        this.interaction = new ChestInteractionService(settings);
        this.pathing = new PathingService(mc);
        this.stateMachine = new AutoChestStateMachine(settings, scanner, interaction, pathing,
                pointStore, recordStore, this);
        this.renderer = new AutoChestRenderer(this);
        syncColorsFromSettings();

        // 监听 ID 配置变更：增删/清空/重载后，目标选择器实时联动（无需重启）
        IdentityService.shared().addListener(this::onIdentityChanged);
    }

    @Override
    public String name() {
        return "AutoChest";
    }

    @Override
    public int order() {
        return 30;
    }

    // ── 设置持久化 ──

    @Override
    public void loadSettings(JsonObject json) {
        settings.load(json);
        syncColorsFromSettings();
    }

    @Override
    public void saveSettings(JsonObject json) {
        settings.save(json);
    }

    /** 立即写回设置（界面改动即时生效，与旧项目 Meteor 设置自动保存一致） */
    public void persistSettings() {
        ModuleManager.saveSettings(this);
    }

    // ── 界面与指令 ──

    @Override
    public ModulePage page() {
        return new AutoChestPage(this);
    }

    @Override
    public List<ClientCommand> commands() {
        return List.of(new AutoChestCommand());
    }

    // ── 生命周期 ──

    /**
     * 启用前自检：收集全部缺项，交给运行时一次性多行播报。
     *
     * <p>收集全部而不是遇到第一个就返回，用户一次就能看到还差什么，配好一项下次启动就少一条。
     * 未进入世界时不做自检（旧项目此时直接提示「必须在进入世界后才能启动模块。」并关闭模块，
     * 该判断放在 {@link #onEnable()}，避免把世界状态混进自检缺项里）。</p>
     */
    @Override
    public List<String> selfCheck() {
        if (mc.player == null || mc.level == null || mc.gameMode == null) return List.of();

        // 自检依赖磁盘数据：先装载当前服务器的 ID 配置、已处理记录、标点
        refreshStores();

        List<String> missing = new ArrayList<>();

        // 容器类型：至少选择一种可识别容器
        if (settings.enabledTypes().isEmpty()) {
            missing.add("§a容器类型§f·未选择任何可识别的容器");
        }

        // 目标物品：非「全部拿空」模式必须有目标物品，否则无从取物
        if (settings.withdrawMode != WithdrawMode.TAKE_ALL
                && IdentityTargetConfig.selectedItems(IdentityService.shared()).isEmpty()) {
            missing.add("§a目标物品§f·当前取物模式需要目标物品，但未选择任何目标");
        }

        // 标点模式：必须有已保存点位，否则没有处理对象
        if (settings.scanMode == ScanMode.MARKER && !pointStore.hasPoints()) {
            missing.add("§a标点模式§f·尚未保存任何点位（用 .autochest 添加 指令添加）");
        }

        // 背包容量：没有任何空槽、也没有任何未满堆叠时，箱子里的东西一件都放不进去，
        // 启动后必然开箱即停机。这是开箱前就能说清的缺项，先列出来，别让玩家开完箱才发现。
        if (!hasInventoryRoom()) {
            missing.add("§a背包容量§f·36 格全满且无未满堆叠，取物会立刻停机（先清出空位）");
        }

        return missing;
    }

    @Override
    protected void onEnable() {
        // 世界就绪判断：自检会访问点位/记录，先确保已进入世界
        if (mc.player == null || mc.level == null || mc.gameMode == null) {
            notifyError("必须在进入世界后才能启动模块。");
            mc.execute(() -> ModuleManager.setEnabled(MODULE_ID, false));
            return;
        }

        refreshStores();

        // 同步扫描半径并清空破坏对账缓存
        scanner.setRadius(settings.scanRadius);
        lastSeenContainers.clear();

        stateMachine.reset();
        scanner.reset();
        scanTick = 0;

        // ESP：注册世界渲染层，关闭时注销
        WorldOverlay.register(MODULE_ID, renderer::render);
    }

    @Override
    protected void onDisable() {
        // 停止寻路、关闭容器、重置状态机与渲染层，避免残留
        pathing.stop();
        interaction.close();
        stateMachine.reset();
        lastSeenContainers.clear();
        WorldOverlay.unregister(MODULE_ID);
    }

    // ── 每刻 ──

    @Override
    public void onTick(Minecraft client) {
        if (client.player == null || client.level == null) return;

        // 状态机推进
        stateMachine.tick();

        // 视角同步：开箱走的是静默发包，不改玩家朝向；不补这一步就会出现「背对着箱子开箱」
        syncViewToTarget();

        BlockPos playerPos = client.player.blockPosition();

        // 扫描周期：每 scanInterval tick 请求一轮新扫描（缓存 + 分帧，不整世界全扫）
        if (++scanTick >= settings.scanInterval) {
            scanTick = 0;
            scanner.requestScan(playerPos);
        }
        // 分帧推进扫描；本轮完成时做容器破坏对账（旧记录失效）
        if (scanner.tick(playerPos)) {
            reconcileDestroyed(playerPos);
        }

        // 容器交互同步推进
        interaction.tick();
    }

    /**
     * 容器破坏对账：本轮扫描仍覆盖但已消失的容器位置，视为被破坏，使旧记录失效。
     *
     * <p>这样「破坏 → 重新放置同类容器」能被重新识别为未处理，ESP 恢复绿色。
     * 只对当前扫描范围（切比雪夫 ≤ radius）内的消失位置生效，避免移动导致的误判。</p>
     */
    private void reconcileDestroyed(BlockPos playerPos) {
        Set<BlockPos> seen = scanner.seen();
        String dim = WorldIdentity.dimension();
        int radius = settings.scanRadius;
        for (BlockPos old : lastSeenContainers) {
            if (seen.contains(old)) continue;
            if (withinCube(old, playerPos, radius)) {
                recordStore.invalidate(old, dim);
            }
        }
        lastSeenContainers.clear();
        lastSeenContainers.addAll(seen);
    }

    /** 坐标是否落在以玩家为中心的扫描立方体内（切比雪夫距离 ≤ radius） */
    private static boolean withinCube(BlockPos pos, BlockPos center, int radius) {
        return Math.abs(pos.getX() - center.getX()) <= radius
                && Math.abs(pos.getY() - center.getY()) <= radius
                && Math.abs(pos.getZ() - center.getZ()) <= radius;
    }

    // ── 状态机回调（Callbacks） ──

    /** 状态机播报入口：统一走模块名前缀（旧项目 {@code notifyStatus} = {@code notify}） */
    @Override
    public void notifyStatus(String message) {
        ClientChat.send(MESSAGE_MODULE, message);
    }

    /**
     * 致命条件导致停止自动箱子：播报原因并关闭模块。
     *
     * <p>背包满等场景下继续取物无意义，关闭容器后停止模块，等玩家清理背包再手动开启。
     * 关闭走 {@code mc.execute} 延后到下一帧，避免在状态机 tick 内直接关闭造成重入。</p>
     */
    @Override
    public void stopAutomation(String reason) {
        notifyStatus("§c✗ " + reason + "，已停止自动箱子");
        mc.execute(() -> ModuleManager.setEnabled(MODULE_ID, false));
    }

    /** 错误播报：旧项目 {@code notifyError} 的橙色加粗前缀 */
    private void notifyError(String message) {
        ClientChat.send(MESSAGE_MODULE, "§6§l" + message);
    }

    /**
     * ID 配置变更回调：清理选择器里已失效的选中项并提示。
     *
     * <p>删除 ID 后若该 ID 正被选中，这里同步移除并提示「ID 已失效」，
     * 保证 AutoChest 不因数据消失而崩溃。</p>
     */
    private void onIdentityChanged() {
        int removed = IdentityTargetConfig.pruneInvalid(IdentityService.shared());
        if (removed > 0) {
            notifyError("有 " + removed + " 个目标 ID 已失效，已从选择器移除");
        }
    }

    // ── 视角同步：让模型真的「看着」正在处理的容器 ──

    /**
     * 把视线平滑转向正在处理的容器。
     *
     * <p>开箱用的是静默交互包，它只带方块命中信息，不动玩家朝向。于是「状态机自己走过去、
     * 自己开箱」时模型会保持走路时的朝向，别人看到的就是背对着箱子开箱。这里在处理期间每 tick
     * 朝容器中心转一小步：客户端改 {@code setYRot/setXRot} 后，原版下一帧就会发出朝向包，
     * 服务器与其他玩家看到的朝向与本机一致。</p>
     *
     * <p>只在「到达 → 关箱」之间、且已走到容器跟前才转头：寻路阶段不抢镜头，
     * 否则会变成一边走路一边扭头看箱子，同样不自然。</p>
     */
    private void syncViewToTarget() {
        if (!isInteractingWithTarget()) return;
        ChestTarget target = stateMachine.processingTarget();
        LocalPlayer player = mc.player;
        if (target == null || player == null) return;
        BlockPos pos = target.pos();
        if (horizontalDistance(player, pos) > VIEW_SYNC_RANGE) return;
        player.setYRot(turnToward(player.getYRot(), yawTo(player, pos), VIEW_TURN_STEP));
        player.setXRot(turnToward(player.getXRot(), pitchTo(player, pos), VIEW_TURN_STEP));
    }

    /**
     * 是否已大致面对指定坐标（容差内即视为「看着它」）。
     *
     * <p>只比 yaw——俯仰角偏一点不影响观感，也没必要为它多等。</p>
     */
    public boolean viewAlignedTo(BlockPos pos) {
        LocalPlayer player = mc.player;
        if (player == null) return true;
        return Math.abs(Mth.wrapDegrees(yawTo(player, pos) - player.getYRot())) <= VIEW_ALIGN_TOLERANCE;
    }

    /** 正在与锁定容器交互的状态（到达 → 关箱）：只有这些状态才允许动玩家视角 */
    private boolean isInteractingWithTarget() {
        return switch (stateMachine.state()) {
            case ARRIVED, OPENING, READING_SLOTS, MATCHING, TAKING, VERIFYING, CLOSING -> true;
            default -> false;
        };
    }

    /** 玩家到容器中心的水平距离 */
    private static double horizontalDistance(LocalPlayer player, BlockPos pos) {
        double dx = pos.getX() + 0.5 - player.getX();
        double dz = pos.getZ() + 0.5 - player.getZ();
        return Math.sqrt(dx * dx + dz * dz);
    }

    /** 看向容器中心所需的 yaw（度） */
    private static float yawTo(LocalPlayer player, BlockPos pos) {
        double dx = pos.getX() + 0.5 - player.getX();
        double dz = pos.getZ() + 0.5 - player.getZ();
        return (float) Math.toDegrees(Math.atan2(-dx, dz));
    }

    /** 看向容器中心所需的 pitch（度） */
    private static float pitchTo(LocalPlayer player, BlockPos pos) {
        double dy = pos.getY() + 0.5 - player.getEyeY();
        double dx = pos.getX() + 0.5 - player.getX();
        double dz = pos.getZ() + 0.5 - player.getZ();
        return (float) Math.toDegrees(-Math.atan2(dy, Math.hypot(dx, dz)));
    }

    /** 朝目标角度靠拢，单 tick 最多转 step 度；wrapDegrees 处理 ±180° 环绕 */
    private static float turnToward(float current, float target, float step) {
        return current + Mth.clamp(Mth.wrapDegrees(target - current), -step, step);
    }

    // ── 标点 / 记录管理（面板按钮与指令共用） ──

    /** 读取准星指向的方块坐标，未对准方块返回 null */
    private BlockPos crosshairBlock() {
        if (mc.hitResult == null || mc.hitResult.getType() != HitResult.Type.BLOCK) return null;
        if (!(mc.hitResult instanceof BlockHitResult blockHit)) return null;
        return blockHit.getBlockPos().immutable();
    }

    /** 判定坐标是否为当前启用的合法容器类型，非容器返回 null */
    private ContainerType containerTypeAt(BlockPos pos) {
        if (mc.level == null) return null;
        Block block = mc.level.getBlockState(pos).getBlock();
        return ContainerTypeRegistry.match(block, settings.enabledTypes());
    }

    /** 设置箱子点位：校验准星方块为合法容器后保存（面板按钮与 {@code .autochest 添加} 共用） */
    public void addPointFromCrosshair() {
        if (mc.player == null || mc.level == null) {
            notifyError("玩家未加载");
            return;
        }
        BlockPos target = crosshairBlock();
        if (target == null) {
            notifyError("准星未对准任何方块");
            return;
        }
        ContainerType type = containerTypeAt(target);
        if (type == null) {
            notifyError("当前目标不是可绑定容器");
            return;
        }
        String dim = WorldIdentity.dimension();
        if (pointStore.add(target, dim, type.id())) {
            notifyStatus("§a§l✓ 已添加标点 §8▸ "
                    + formatCoords(target.getX(), target.getY(), target.getZ())
                    + " §8▸ §7维度 §8▸ §f" + WorldIdentity.dimensionDisplayName(dim)
                    + " §8▸ §a" + type.displayName());
        } else {
            notifyError("该标点已存在");
        }
    }

    /** 删除一个标点并播报（面板点位卡片行的「§c删除」按钮） */
    public void deletePoint(ChestTarget point) {
        String dim = WorldIdentity.dimensionDisplayName(point.dimension());
        if (pointStore.remove(point.pos(), point.dimension())) {
            notifyStatus("§c§l✗ 已删除标点 §8▸ "
                    + formatCoords(point.pos().getX(), point.pos().getY(), point.pos().getZ())
                    + " §8▸ §7维度 §8▸ §f" + dim);
        } else {
            notifyError("删除失败：该标点已不存在");
        }
    }

    /** 清空全部标点（确认后执行） */
    public void clearAllPoints() {
        int count = pointStore.size();
        pointStore.clear();
        notifyStatus("§c§l✗ 已清空全部标点 §8▸ 共 " + count + " 个");
    }

    /** 清除当前维度处理记录（确认后执行），ESP 恢复绿色 */
    public void clearCurrentDimensionRecords() {
        String dim = WorldIdentity.dimension();
        int removed = recordStore.clearDimension(dim);
        notifyStatus("§c§l✗ 已清除当前维度处理记录 §8▸ 共 " + removed + " 条");
    }

    /** 清除全部服务器处理记录（确认后执行） */
    public void clearAllRecords() {
        int files = recordStore.clearAllServers();
        notifyStatus("§c§l✗ 已清除全部处理记录 §8▸ 共 " + files + " 个文件");
    }

    /** 坐标排版，逐字复刻旧项目 {@code YiyiaddonModule.formatCoords} */
    public static String formatCoords(int x, int y, int z) {
        return "§7X§f" + x + " §7Y§f" + y + " §7Z§f" + z;
    }

    // ── 颜色设置：调色板载体与设置项的双向同步 ──

    public EspColor unprocessedColor() {
        return unprocessedColor;
    }

    public EspColor processedColor() {
        return processedColor;
    }

    public EspColor processingColor() {
        return processingColor;
    }

    /** 设置项的 ARGB 值 → 调色板载体（载入设置、构造时调用） */
    private void syncColorsFromSettings() {
        applyToEsp(unprocessedColor, settings.unprocessedColor);
        applyToEsp(processedColor, settings.processedColor);
        applyToEsp(processingColor, settings.processingColor);
    }

    /**
     * 调色板载体 → 设置项的 ARGB 值；有变化立即落盘。
     *
     * <p>由模块页面每帧调用：调色板窗口直接改载体，关闭后页面下一帧把 RGB 与透明度写回设置项，
     * 彩虹相位不参与（设置项只承载 ARGB 整数）。</p>
     */
    public void syncColorsToSettings() {
        int unprocessed = pack(unprocessedColor);
        int processed = pack(processedColor);
        int processing = pack(processingColor);
        if (unprocessed == settings.unprocessedColor
                && processed == settings.processedColor
                && processing == settings.processingColor) {
            return;
        }
        settings.unprocessedColor = unprocessed;
        settings.processedColor = processed;
        settings.processingColor = processing;
        persistSettings();
    }

    private static void applyToEsp(EspColor color, int argb) {
        color.rgb(argb & 0xFFFFFF);
        color.alpha((argb >>> 24) & 0xFF);
    }

    /** 调色板载体打包为设置项使用的 ARGB */
    private static int pack(EspColor color) {
        return ((color.alpha() & 0xFF) << 24) | (color.rgb() & 0xFFFFFF);
    }

    // ── 供面板 / 渲染器 / 指令读取 ──

    public AutoChestSettings settings() {
        return settings;
    }

    public ContainerScanner scanner() {
        return scanner;
    }

    public ChestPointStore pointStore() {
        return pointStore;
    }

    public ContainerRecordStore recordStore() {
        return recordStore;
    }

    public AutoChestStateMachine stateMachine() {
        return stateMachine;
    }

    // ── 内部 ──

    /** 装载当前服务器的 ID 配置、已处理记录、标点（自检与启用都要用） */
    private void refreshStores() {
        IdentityService.shared().reload();
        recordStore.reload();
        pointStore.reload();
    }

    /**
     * 玩家背包是否还有一点容纳空间：存在空槽，或存在未满的可堆叠堆叠。
     *
     * <p>只统计主背包 + 快捷栏共 36 格（{@code Inventory#getItem} 的下标 0~35）；
     * 36 以上是装备槽，不是存储，不能算作可用空间。</p>
     */
    private boolean hasInventoryRoom() {
        if (mc.player == null) return true;
        Inventory inventory = mc.player.getInventory();
        for (int i = 0; i < INVENTORY_STORAGE_SLOTS; i++) {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty()) return true;
            if (stack.isStackable() && stack.getCount() < stack.getMaxStackSize()) return true;
        }
        return false;
    }
}
