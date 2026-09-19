package com.yiyiaddon.feature.librarian.service;

import com.yiyiaddon.feature.librarian.config.LibrarianConfig;
import com.yiyiaddon.feature.librarian.fsm.LibrarianState;
import com.yiyiaddon.feature.librarian.fsm.LibrarianStateMachine;
import com.yiyiaddon.feature.librarian.fsm.StateTransition;
import com.yiyiaddon.feature.librarian.model.EnchantmentTarget;
import com.yiyiaddon.feature.librarian.model.LibrarianContext;
import com.yiyiaddon.feature.librarian.model.StationValidationStatus;
import com.yiyiaddon.feature.librarian.model.TradeOfferSnapshot;
import com.yiyiaddon.feature.librarian.model.VillagerStation;
import com.yiyiaddon.feature.librarian.model.VillagerTarget;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * 自动图书管理员 · 核心业务编排器。
 *
 * <p>将状态机与各业务服务串联，为每个状态注册对应动作（搜索、移动、放置讲台、
 * 交易、验证、完成），是自动图书管理员的运行时大脑。由模块层按 tick 驱动。</p>
 *
 * <p><b>三条贯穿全流程的约定</b>：</p>
 * <ol>
 *   <li><b>每 tick 只做一件事</b>：状态处理器按 {@code ticks == 1} 启动动作、之后轮询结果，
 *       需要重复发包的动作（开交易、选交易）由 {@code actionSubmitted} 挡住重复提交；</li>
 *   <li><b>转换即重置提交位</b>：{@code transition(...)} 里 {@code actionSubmitted = false}，
 *       保证跨状态后不会沿用上一个动作的「已提交」状态；</li>
 *   <li><b>失败一律走 {@link #fail}</b>：写失败原因 + 播报 + 转 {@code ERROR}，
 *       由状态机保证不会从 ERROR 回到业务状态（表里 ERROR 只允许转到 ERROR）。</li>
 * </ol>
 *
 * <p><b>阈值来源</b>：可配项走 {@link LibrarianConfig}；硬编码阈值（工位探测 60 tick、
 * 清障 200 tick、停稳 15 tick、职业确认后 10 tick、开界面重试间隔 2 tick、空转提示 200 tick）
 * 逐条照搬旧项目，属行为的一部分，禁止调整。</p>
 *
 * <p>迁移自旧项目 {@code librarian/orchestrator/AutoLibrarianOrchestrator}（729 行），
 * 26 个状态处理器与全部判据逐条照搬（D7 拍板：未超第 38 条阈值，单文件迁移）。</p>
 */
public final class LibrarianOrchestrator {
    /** 工位探测超时（tick）：村民相邻 4 格内始终没有岩浆块即判失败 */
    private static final int FIND_STATION_TIMEOUT_TICKS = 60;
    /** 清障超时（tick）：给足够时间挖硬方块 */
    private static final int BREAK_OBSTACLE_TIMEOUT_TICKS = 200;
    /** 放置前停稳等待（tick）：防止从村民后方寻路过来时移动惯性导致放置方向偏差 */
    private static final int PLACE_SETTLE_TICKS = 15;
    /** 检测到图书管理员职业后再等（tick）：确保服务端生成了交易列表 */
    private static final int TRADE_DATA_READY_DELAY_TICKS = 10;
    /** 打开交易前等待（tick）：等村民 AI 稳定 */
    private static final int OPEN_TRADE_INITIAL_WAIT_TICKS = 3;
    /** 打开交易重试间隔（tick） */
    private static final int OPEN_TRADE_RETRY_INTERVAL_TICKS = 2;
    /** 无失业村民时的提示间隔（tick） */
    private static final int SEARCH_IDLE_NOTICE_INTERVAL_TICKS = 200;

    /** 业务配置 */
    private final LibrarianConfig config;
    /** 运行上下文 */
    private final LibrarianContext context;
    /** 状态机 */
    private final LibrarianStateMachine stateMachine;
    /** 村民搜索服务 */
    private final VillagerSearchService villagerSearchService;
    /** 固定交易位服务 */
    private final VillagerStationService villagerStationService;
    /** 移动服务 */
    private final MovementService movementService;
    /** 讲台放置服务 */
    private final LecternPlacementService lecternPlacementService;
    /** 交易服务 */
    private final TradeService tradeService;
    /** 库存服务 */
    private final InventoryService inventoryService;
    /** 附魔匹配服务 */
    private final EnchantmentService enchantmentService;
    /** 调试日志服务 */
    private final DebugLoggerService logger;
    /** 调试音效服务 */
    private final DebugSoundService sound;
    /** 本 tick 动作是否已提交（防止重复发包） */
    private boolean actionSubmitted;
    /** 记录检测到图书管理员职业时的 tick，用于等待交易数据初始化（-1 表示未检测到） */
    private long librarianDetectedTick = -1;

    public LibrarianOrchestrator(
        LibrarianConfig config,
        LibrarianContext context,
        VillagerSearchService villagerSearchService,
        VillagerStationService villagerStationService,
        MovementService movementService,
        LecternPlacementService lecternPlacementService,
        TradeService tradeService,
        InventoryService inventoryService,
        EnchantmentService enchantmentService,
        DebugLoggerService logger,
        DebugSoundService sound
    ) {
        this.config = Objects.requireNonNull(config, "config");
        this.context = Objects.requireNonNull(context, "context");
        this.villagerSearchService = Objects.requireNonNull(villagerSearchService, "villagerSearchService");
        this.villagerStationService = Objects.requireNonNull(villagerStationService, "villagerStationService");
        this.movementService = Objects.requireNonNull(movementService, "movementService");
        this.lecternPlacementService = Objects.requireNonNull(lecternPlacementService, "lecternPlacementService");
        this.tradeService = Objects.requireNonNull(tradeService, "tradeService");
        this.inventoryService = Objects.requireNonNull(inventoryService, "inventoryService");
        this.enchantmentService = Objects.requireNonNull(enchantmentService, "enchantmentService");
        this.logger = Objects.requireNonNull(logger, "logger");
        this.sound = Objects.requireNonNull(sound, "sound");
        this.stateMachine = new LibrarianStateMachine(this::onTransition, this::onStateFailure);
        registerHandlers();
    }

    /** 启动：清空残留村民周期后进入 START */
    public void start() {
        context.clearVillagerCycle();
        stateMachine.start();
    }

    /** 推进一 tick */
    public void tick() {
        stateMachine.tick();
    }

    /** 停止：取消移动、关闭交易、清空周期并复位状态机 */
    public void stop() {
        movementService.stop();
        tradeService.close();
        context.clearVillagerCycle();
        stateMachine.reset("模块停止");
    }

    /** 当前状态 */
    public LibrarianState getState() {
        return stateMachine.getCurrentState();
    }

    /** 运行上下文 */
    public LibrarianContext getContext() {
        return context;
    }

    /** 注册全部状态处理器（IDLE 不接受处理器；FINISH 为空实现） */
    private void registerHandlers() {
        stateMachine.register(LibrarianState.START, this::handleStart);
        stateMachine.register(LibrarianState.SEARCH_VILLAGER, this::handleSearchVillager);
        stateMachine.register(LibrarianState.SELECT_TARGET_VILLAGER, this::handleSelectTargetVillager);
        stateMachine.register(LibrarianState.MOVE_TO_VILLAGER, this::handleMoveToVillager);
        stateMachine.register(LibrarianState.FIND_LECTERN_POSITION, this::handleFindLecternPosition);
        stateMachine.register(LibrarianState.MOVE_TO_STAND_POSITION, this::handleMoveToStandPosition);
        stateMachine.register(LibrarianState.BREAK_OBSTACLE, this::handleBreakObstacle);
        stateMachine.register(LibrarianState.PLACE_LECTERN, this::handlePlaceLectern);
        stateMachine.register(LibrarianState.WAIT_PROFESSION, this::handleWaitProfession);
        stateMachine.register(LibrarianState.OPEN_TRADE, this::handleOpenTrade);
        stateMachine.register(LibrarianState.WAIT_TRADE_SCREEN, this::handleWaitTradeScreen);
        stateMachine.register(LibrarianState.READ_TRADES, this::handleReadTrades);
        stateMachine.register(LibrarianState.CHECK_ENCHANTMENT, this::handleCheckEnchantment);
        stateMachine.register(LibrarianState.RESET, this::handleReset);
        stateMachine.register(LibrarianState.BREAK_LECTERN, this::handleBreakLectern);
        stateMachine.register(LibrarianState.WAIT_UNEMPLOYED, this::handleWaitUnemployed);
        stateMachine.register(LibrarianState.SUCCESS_FOUND, this::handleSuccessFound);
        stateMachine.register(LibrarianState.TRADE_PROCESS, this::handleTradeProcess);
        stateMachine.register(LibrarianState.SELECT_TRADE, this::handleSelectTrade);
        stateMachine.register(LibrarianState.WAIT_TRADE_SYNC, this::handleWaitTradeSync);
        stateMachine.register(LibrarianState.TAKE_TRADE_OUTPUT, this::handleTakeTradeOutput);
        stateMachine.register(LibrarianState.VERIFY_PURCHASE, this::handleVerifyPurchase);
        stateMachine.register(LibrarianState.COMPLETE_TARGET, this::handleCompleteTarget);
        stateMachine.register(LibrarianState.END_VILLAGER_CYCLE, this::handleEndVillagerCycle);
        stateMachine.register(LibrarianState.FINISH, () -> {
        });
        stateMachine.register(LibrarianState.ERROR, this::handleError);
    }

    /** 启动：全完成即收尾，Baritone 不可用直接失败，否则开始搜索 */
    private void handleStart() {
        if (context.targetProgress().allCompleted()) {
            transition(LibrarianState.FINISH, "所有附魔目标均已完成");
            return;
        }
        if (!movementService.isAvailable()) {
            fail("Baritone 不可用，无法启动自动移动");
            return;
        }
        transition(LibrarianState.SEARCH_VILLAGER, "开始搜索未绑定职业村民");
    }

    /** 搜索失业村民：空结果每 200 tick 提示一次，命中即锁定 */
    private void handleSearchVillager() {
        Optional<VillagerTarget> target = villagerSearchService.findNearestUnemployedVillager(config.villagerSearchRadius());
        if (target.isEmpty()) {
            // 每200tick提示一次附近没有失业村民
            long ticks = stateMachine.getTicksInCurrentState();
            if (ticks == 1 || ticks % SEARCH_IDLE_NOTICE_INTERVAL_TICKS == 0) {
                logger.info("附近没有失业村民，等待中...");
            }
            return;
        }
        context.setVillagerTarget(target.get());
        transition(LibrarianState.SELECT_TARGET_VILLAGER, "发现并保存当前目标村民");
    }

    /** 校验候选村民：失效或已有职业即结束本周期，否则进入移动 */
    private void handleSelectTargetVillager() {
        VillagerTarget target = requireVillager();
        if (!villagerSearchService.isValid(target) || !villagerSearchService.isUnemployed(target)) {
            transition(LibrarianState.END_VILLAGER_CYCLE, "候选村民失效或已有职业");
            return;
        }
        transition(LibrarianState.MOVE_TO_VILLAGER, "候选村民验证通过");
    }

    /** 移动到村民附近：到达后停移动，超时 / 状态失败即报错 */
    private void handleMoveToVillager() {
        VillagerTarget target = requireVillager();
        if (!villagerSearchService.isValid(target)) {
            movementService.stop();
            transition(LibrarianState.END_VILLAGER_CYCLE, "移动期间村民失效");
            return;
        }
        if (movementService.hasArrived()) {
            movementService.stop();
            transition(LibrarianState.FIND_LECTERN_POSITION, "已到达村民附近");
            return;
        }
        if (stateMachine.getTicksInCurrentState() == 1) {
            // 移动阶段直接以村民当前坐标为目标，不做空间模型检测
            // 空间模型检测（岩浆块、讲台位置）延迟到到达后的 FIND_LECTERN_POSITION 执行
            logger.debug("[移动] 启动 Baritone，目标村民坐标: " + target.position());
            MovementStartResult result = movementService.gotoPosition(
                target.position(),
                config.movementArrivalRadius()
            );
            if (result != MovementStartResult.STARTED && result != MovementStartResult.ALREADY_RUNNING) {
                fail("Baritone 移动启动失败：" + result.displayName());
            }
            return;
        }
        if (stateMachine.getTicksInCurrentState() > config.movementTimeoutTicks()) {
            movementService.stop();
            fail("Baritone 移动超时");
            return;
        }
        MovementStatus status = movementService.getStatus();
        if (status == MovementStatus.FAILED || status == MovementStatus.CANCELED || status == MovementStatus.TIMED_OUT) {
            fail("Baritone 移动失败：" + status.displayName());
        }
    }

    /** 探测工位：60 tick 内找到岩浆块并静态校验，通过后转到放置站位 */
    private void handleFindLecternPosition() {
        VillagerTarget target = requireVillager();
        // 到达村民附近后，在此阶段才建立空间模型并做完整工位检测
        if (context.villagerStation().isEmpty()) {
            // 检测到60 tick 后仍没有找到岩浆块，放弃
            if (stateMachine.getTicksInCurrentState() > FIND_STATION_TIMEOUT_TICKS) {
                fail("工位检测超时：村民相邻4格内始终没有岩浆块，请检查场地布置");
                return;
            }
            Optional<VillagerStation> detectedStation = villagerStationService.detect(target);
            if (detectedStation.isEmpty()) {
                // 还没找到，等下一tick（村民可能还没转身）
                return;
            }
            context.setVillagerStation(detectedStation.get().withValidationStatus(StationValidationStatus.UNVALIDATED));
            logger.debug("[工位检测] 发现岩浆块，讲台位置: " + detectedStation.get().lecternPosition());
        }
        VillagerStation station = requireStation();
        MarkerBlockValidation validation = villagerStationService.validate(station);
        if (!validation.valid()) {
            fail("[工位检测] 验证失败: " + validation.reason());
            return;
        }
        context.setLecternPosition(station.lecternPosition());
        transition(LibrarianState.MOVE_TO_STAND_POSITION, "固定讲台位置验证通过，移动到放置位");
    }

    /** 移动到放置站位：到达后按有无障碍决定清障还是放置 */
    private void handleMoveToStandPosition() {
        VillagerStation station = requireStation();
        if (!villagerSearchService.isValid(requireVillager())) {
            movementService.stop();
            transition(LibrarianState.END_VILLAGER_CYCLE, "移动到站位期间村民失效");
            return;
        }
        if (movementService.hasArrived()) {
            movementService.stop();
            VillagerStation arrived = requireStation();
            if (lecternPlacementService.hasObstacle(arrived)) {
                logger.info("讲台位有障碍方块，进入清除流程");
                transition(LibrarianState.BREAK_OBSTACLE, "讲台位有障碍，清除后放置");
                return;
            }
            transition(LibrarianState.PLACE_LECTERN, "已到达讲台放置站位");
            return;
        }
        if (stateMachine.getTicksInCurrentState() == 1) {
            logger.debug("[移动到站位] 目标: " + station.playerStandPosition());
            MovementStartResult result = movementService.gotoPosition(
                station.playerStandPosition(), 1
            );
            if (result != MovementStartResult.STARTED && result != MovementStartResult.ALREADY_RUNNING) {
                fail("移动到讲台站位失败：" + result.displayName());
            }
            return;
        }
        if (stateMachine.getTicksInCurrentState() > config.movementTimeoutTicks()) {
            movementService.stop();
            fail("移动到讲台站位超时");
            return;
        }
        MovementStatus status = movementService.getStatus();
        if (status == MovementStatus.FAILED || status == MovementStatus.CANCELED || status == MovementStatus.TIMED_OUT) {
            fail("移动到讲台站位失败：" + status.displayName());
        }
    }

    /** 清除讲台位障碍方块：200 tick 超时，WAITING / RETRY 继续下一 tick */
    private void handleBreakObstacle() {
        VillagerStation station = requireStation();
        logger.debug("清除障碍状态 tick=" + stateMachine.getTicksInCurrentState()
            + " 位置=" + station.lecternPosition());
        if (!villagerSearchService.isValid(requireVillager())) {
            transition(LibrarianState.END_VILLAGER_CYCLE, "清除障碍期间村民失效");
            return;
        }
        // 超过200tick仍未清除，才判定失败（给足够时间挖硬方块）
        if (actionTimedOut(BREAK_OBSTACLE_TIMEOUT_TICKS)) {
            fail("清除障碍超时（200tick）");
            return;
        }
        ActionResult result = lecternPlacementService.breakObstacle(station);
        logger.debug("清除障碍结果=" + result.status().displayName() + " 原因=" + result.reason());
        if (result.status() == ActionStatus.SUCCESS) {
            logger.info("已清除障碍方块，恢复运行");
            transition(LibrarianState.PLACE_LECTERN, "障碍已清除，开始放置讲台");
        } else if (result.status() == ActionStatus.FAILED) {
            fail("清除障碍失败: " + result.reason());
        }
        // WAITING / RETRY 状态：继续下一tick执行，不做任何操作
    }

    /** 放置讲台：停稳 15 tick → 复检障碍 → 放置并校验朝向 */
    private void handlePlaceLectern() {
        VillagerStation station = requireStation();
        logger.debug("放置讲台状态 tick=" + stateMachine.getTicksInCurrentState()
            + " 位置=" + station.lecternPosition()
            + " 障碍=" + lecternPlacementService.hasObstacle(station));
        if (!validateCurrentStation("放置讲台前")) return;
        // 到达站位后等15tick让玩家完全停下，防止从村民后方寻路过来时移动惯性导致放置方向偏差
        if (stateMachine.getTicksInCurrentState() < PLACE_SETTLE_TICKS) return;
        // 重复检测：放置前再确认无障碍，如仍有则回到清除流程
        if (lecternPlacementService.hasObstacle(station)) {
            logger.info("放置讲台前检测到障碍仍存在，重新清除");
            transition(LibrarianState.BREAK_OBSTACLE, "讲台位仍有障碍，重新清除");
            return;
        }
        if (actionSubmitted) {
            if (lecternPlacementService.validatePlacement(station)) {
                transition(LibrarianState.WAIT_PROFESSION, "讲台精准放置及朝向验证成功");
            } else if (actionTimedOut(config.professionTimeoutTicks())) {
                fail("讲台放置结果确认超时");
            }
            return;
        }
        if (!actionDelayElapsed()) return;
        ActionResult result = lecternPlacementService.place(station);
        if (result.status() == ActionStatus.SUCCESS) {
            if (!lecternPlacementService.validatePlacement(station)) {
                fail("讲台放置验证失败：位置或阅读面朝向不正确。");
                return;
            }
            transition(LibrarianState.WAIT_PROFESSION, "讲台精准放置及朝向验证成功");
        } else if (result.status() == ActionStatus.WAITING) {
            actionSubmitted = true;
        } else if (result.status() == ActionStatus.FAILED) {
            fail("讲台放置失败: " + result.reason());
        } else if (actionTimedOut(config.professionTimeoutTicks())) {
            fail("讲台放置重试超时: " + result.reason());
        }
    }

    /** 等待村民成为图书管理员：检测到后再等 10 tick 让交易数据就绪 */
    private void handleWaitProfession() {
        VillagerTarget target = requireVillager();
        if (!villagerSearchService.isValid(target)) {
            librarianDetectedTick = -1;
            transition(LibrarianState.END_VILLAGER_CYCLE, "等待职业期间村民失效");
            return;
        }
        if (!validateCurrentStation("职业确认前")) return;
        if (villagerSearchService.isLibrarian(target)) {
            long now = stateMachine.getTicksInCurrentState();
            if (librarianDetectedTick < 0) {
                librarianDetectedTick = now;
                logger.debug("[职业] 检测到图书管理员，等待交易数据初始化...");
                return;
            }
            // 检测到职业后再等10tick，确保服务端生成了交易列表
            if (now - librarianDetectedTick >= TRADE_DATA_READY_DELAY_TICKS) {
                librarianDetectedTick = -1;
                transition(LibrarianState.OPEN_TRADE, "村民已获得图书管理员职业，交易数据就绪");
            }
            return;
        }
        librarianDetectedTick = -1;
        if (stateMachine.getTicksInCurrentState() > config.professionTimeoutTicks()) {
            transition(LibrarianState.RESET, "等待图书管理员职业超时");
        }
    }

    /** 打开交易界面：前 3 tick 等村民 AI，之后每 2 tick 重试一次 */
    private void handleOpenTrade() {
        if (!validateCurrentStation("交易前")) return;
        if (tradeService.isTradeScreenReady()) {
            transition(LibrarianState.WAIT_TRADE_SCREEN, "交易界面已打开");
            return;
        }
        if (actionTimedOut(config.tradeScreenTimeoutTicks())) {
            fail("交易界面打开超时");
            return;
        }
        // 前3tick等待村民AI稳定，之后每2tick重试一次，直到界面打开或超时
        long ticks = stateMachine.getTicksInCurrentState();
        if (ticks < OPEN_TRADE_INITIAL_WAIT_TICKS) return;
        if ((ticks - OPEN_TRADE_INITIAL_WAIT_TICKS) % OPEN_TRADE_RETRY_INTERVAL_TICKS != 0) return;
        ActionResult result = tradeService.open(requireVillager());
        if (result.status() == ActionStatus.FAILED) {
            fail("打开交易失败: " + result.reason());
        }
    }

    /** 等待交易界面同步：超时则回 RESET 拆台刷新 */
    private void handleWaitTradeScreen() {
        if (tradeService.isTradeScreenReady()) {
            transition(LibrarianState.READ_TRADES, "交易界面已同步");
        } else if (stateMachine.getTicksInCurrentState() > config.tradeScreenTimeoutTicks()) {
            transition(LibrarianState.RESET, "交易界面打开超时");
        }
    }

    /** 读取交易：调试模式逐条打印，取第一笔附魔书报价 */
    private void handleReadTrades() {
        if (!validateCurrentStation("读取交易前")) return;
        // 调试：输出全部交易列表
        if (config.debugLogging()) {
            List<TradeOfferSnapshot> all = tradeService.scanTrades();
            if (all.isEmpty()) {
                logger.debug("[交易扫描] 当前村民没有任何附魔书交易");
            } else {
                for (TradeOfferSnapshot t : all) {
                    logger.debug("[交易扫描] index=" + t.tradeIndex()
                        + " 附魔=" + t.enchantmentIdentifier()
                        + " 等级=" + t.enchantmentLevel() + "/" + t.maximumEnchantmentLevel()
                        + " 绿宝石=" + t.emeraldCost()
                        + " 书=" + t.bookCost()
                        + " 可交易=" + t.tradable()
                    );
                }
            }
        }
        Optional<TradeOfferSnapshot> offer = tradeService.readFirstEnchantedBookTrade();
        if (offer.isEmpty()) {
            transition(LibrarianState.RESET, "首轮交易没有附魔书");
            return;
        }
        TradeOfferSnapshot o = offer.get();
        logger.debug("[交易] 附魔=" + o.enchantmentIdentifier()
            + " 等级=" + o.enchantmentLevel()
            + " 绿宝石x" + o.emeraldCost()
            + " 书x" + o.bookCost()
        );
        context.setTradeOffer(o);
        transition(LibrarianState.CHECK_ENCHANTMENT, "已读取第一本附魔书交易");
    }

    /** 命中判定：只在未完成目标里找第一个命中项 */
    private void handleCheckEnchantment() {
        TradeOfferSnapshot offer = context.tradeOffer().orElseThrow();
        Optional<EnchantmentTarget> matchedTarget = context.targetProgress().incompleteTargets().stream()
            .filter(target -> enchantmentService.matches(offer, target, config.maximumEmeraldPrice()))
            .findFirst();
        if (matchedTarget.isEmpty()) {
            transition(LibrarianState.RESET, "附魔书不属于未完成目标");
            return;
        }
        EnchantmentTarget hit = matchedTarget.get();
        logger.info("§a✓ 命中目标附魔 §8▸ " + hit.displayName() + " Lv." + hit.level()
            + " §8▸ 绿宝石x" + offer.emeraldCost() + " §8▸ 书x" + offer.bookCost());
        context.setMatchedTarget(hit);
        transition(LibrarianState.SUCCESS_FOUND, "命中目标附魔，保留讲台、职业和交易");
    }

    /** 重置：停移动、关交易、清本轮刷新数据，随后拆讲台刷新职业 */
    private void handleReset() {
        movementService.stop();
        tradeService.close();
        context.resetRefreshAttempt();
        transition(LibrarianState.BREAK_LECTERN, "本轮刷新数据已清理，继续使用当前村民");
    }

    /** 拆除讲台：拆到空气为止，随后等村民恢复失业 */
    private void handleBreakLectern() {
        if (!villagerSearchService.isValid(requireVillager())) {
            transition(LibrarianState.END_VILLAGER_CYCLE, "拆除讲台期间村民失效");
            return;
        }
        tradeService.close();
        if (actionSubmitted) {
            // 已开始拆除：每 tick 继续攻击，直到方块消失或超时
            if (lecternPlacementService.validateRemoval(requireStation())) {
                transition(LibrarianState.WAIT_UNEMPLOYED, "讲台已拆除，等待村民解除职业");
                return;
            }
            if (actionTimedOut(config.professionTimeoutTicks())) {
                fail("讲台拆除结果确认超时");
                return;
            }
            lecternPlacementService.breakLectern(requireStation());
            return;
        }
        if (!actionDelayElapsed()) return;
        ActionResult result = lecternPlacementService.breakLectern(requireStation());
        if (result.status() == ActionStatus.SUCCESS) {
            transition(LibrarianState.WAIT_UNEMPLOYED, "讲台已拆除，等待村民解除职业");
        } else if (result.status() == ActionStatus.WAITING) {
            actionSubmitted = true;
        } else if (result.status() == ActionStatus.FAILED) {
            fail("讲台拆除失败: " + result.reason());
        } else if (actionTimedOut(config.professionTimeoutTicks())) {
            fail("讲台拆除重试超时: " + result.reason());
        }
    }

    /** 等待村民失业：失业且过了刷新延迟才回工位探测 */
    private void handleWaitUnemployed() {
        VillagerTarget target = requireVillager();
        if (!villagerSearchService.isValid(target)) {
            transition(LibrarianState.END_VILLAGER_CYCLE, "等待解除职业期间村民失效");
            return;
        }
        if (villagerSearchService.isUnemployed(target)
            && stateMachine.getTicksInCurrentState() > config.resetDelayTicks()) {
            context.completeLecternRemoval();
            transition(LibrarianState.FIND_LECTERN_POSITION, "当前村民已解除职业，重新寻找讲台位置");
            return;
        }
        if (stateMachine.getTicksInCurrentState() > config.professionTimeoutTicks()) {
            fail("等待村民解除职业超时");
        }
    }

    /** 命中后锁定交易上下文 */
    private void handleSuccessFound() {
        context.beginTradeProcess();
        transition(LibrarianState.TRADE_PROCESS, "目标交易状态已锁定，进入交易流程");
    }

    /** 交易前置检查：可交易、付得起、放得下，然后记录购买前库存 */
    private void handleTradeProcess() {
        TradeOfferSnapshot offer = context.tradeOffer().orElseThrow();
        if (offer.soldOut() || !inventoryService.canAfford(offer) || !inventoryService.hasOutputCapacity()) {
            fail("目标交易不可购买或库存空间不足");
            return;
        }
        EnchantmentTarget target = context.matchedTarget().orElseThrow();
        context.setMatchingBooksBeforePurchase(inventoryService.countMatchingBooks(target));
        transition(LibrarianState.SELECT_TRADE, "购买条件已确认，准备选择目标交易");
    }

    /** 选中目标交易：先确认已被服务端选中，否则等延迟后发包 */
    private void handleSelectTrade() {
        if (!validateCurrentStation("选择交易前")) return;
        TradeOfferSnapshot offer = context.tradeOffer().orElseThrow();
        if (tradeService.isSelectedTradeSynchronized(offer)) {
            transition(LibrarianState.WAIT_TRADE_SYNC, "目标交易已选择");
            return;
        }
        if (actionSubmitted) {
            if (actionTimedOut(config.tradeSyncTimeoutTicks())) fail("选择交易结果确认超时");
            return;
        }
        if (!actionDelayElapsed()) return;
        ActionResult result = tradeService.select(offer);
        if (result.status() == ActionStatus.SUCCESS) {
            actionSubmitted = true;
            transition(LibrarianState.WAIT_TRADE_SYNC, "已选择目标交易");
        } else if (result.status() == ActionStatus.WAITING) {
            actionSubmitted = true;
        } else if (result.status() == ActionStatus.FAILED) {
            fail("选择交易失败: " + result.reason());
        } else if (actionTimedOut(config.tradeSyncTimeoutTicks())) {
            fail("选择交易重试超时: " + result.reason());
        }
    }

    /** 等待目标交易同步 */
    private void handleWaitTradeSync() {
        if (tradeService.isSelectedTradeSynchronized(context.tradeOffer().orElseThrow())) {
            transition(LibrarianState.TAKE_TRADE_OUTPUT, "目标交易已同步");
        } else if (stateMachine.getTicksInCurrentState() > config.tradeSyncTimeoutTicks()) {
            fail("目标交易同步超时");
        }
    }

    /** 领取成品：结果槽就绪后一次性快速移动 */
    private void handleTakeTradeOutput() {
        if (!validateCurrentStation("领取交易前")) return;
        if (actionSubmitted) {
            if (actionTimedOut(config.tradeSyncTimeoutTicks())) fail("领取交易输出结果确认超时");
            return;
        }
        if (!actionDelayElapsed()) return;
        ActionResult result = tradeService.takeOutput();
        if (result.status() == ActionStatus.SUCCESS) {
            actionSubmitted = true;
            transition(LibrarianState.VERIFY_PURCHASE, "已领取交易输出");
        } else if (result.status() == ActionStatus.FAILED) {
            fail("领取交易输出失败: " + result.reason());
        } else if (actionTimedOut(config.tradeSyncTimeoutTicks())) {
            fail("领取交易输出等待超时: " + result.reason());
        }
    }

    /** 库存验证：数量增加即成交，超时则补记一次快照后判失败 */
    private void handleVerifyPurchase() {
        EnchantmentTarget target = context.matchedTarget().orElseThrow();
        int currentCount = inventoryService.countMatchingBooks(target);
        if (currentCount > context.matchingBooksBeforePurchase() && context.verifyCurrentPurchase(currentCount)) {
            transition(LibrarianState.COMPLETE_TARGET, "库存已确认新增目标附魔书");
            return;
        }
        if (stateMachine.getTicksInCurrentState() > config.tradeSyncTimeoutTicks()) {
            context.verifyCurrentPurchase(currentCount);
            fail("库存未确认目标附魔书，购买验证失败");
        }
    }

    /** 完成当前目标：全完成则 FINISH 并保留讲台 / 职业 / 交易结果 */
    private void handleCompleteTarget() {
        EnchantmentTarget completed = context.matchedTarget().orElseThrow();
        context.completeVerifiedCurrentTarget(config.removeCompletedTarget());
        tradeService.close();
        String completedName = completed.displayName() + " Lv." + completed.level();
        logger.info("§a✓ 目标完成 §8▸ " + completedName
            + (config.removeCompletedTarget() ? "（已移除）" : ""));
        if (context.targetProgress().allCompleted()) {
            logger.info("§a✓ 全部目标附魔已完成");
            transition(LibrarianState.FINISH, "全部目标附魔购买完成，保留讲台、职业和交易结果");
        } else {
            List<EnchantmentTarget> remaining = context.targetProgress().incompleteTargets();
            logger.info("§7剩余目标 §8▸ " + remaining.stream()
                .map(t -> t.displayName() + " Lv." + t.level())
                .reduce((a, b) -> a + ", " + b).orElse("无"));
            transition(LibrarianState.END_VILLAGER_CYCLE, "当前附魔目标已完成，结束当前村民周期");
        }
    }

    /** 结束村民周期：停移动、关交易、清上下文，回到搜索 */
    private void handleEndVillagerCycle() {
        movementService.stop();
        tradeService.close();
        context.clearVillagerCycle();
        transition(LibrarianState.SEARCH_VILLAGER, "当前村民周期已安全结束");
    }

    /** 错误态：收拾移动与交易，等待模块关闭 */
    private void handleError() {
        movementService.stop();
        tradeService.close();
    }

    /** 动作延迟是否已过（普通业务动作之间的节流） */
    private boolean actionDelayElapsed() {
        return stateMachine.getTicksInCurrentState() > config.actionDelayTicks();
    }

    /** 当前状态是否已超过给定 tick 数 */
    private boolean actionTimedOut(int timeoutTicks) {
        return stateMachine.getTicksInCurrentState() > timeoutTicks;
    }

    /**
     * 用已建立的空间模型做静态校验。
     *
     * <p>只检查岩浆块存在性与讲台位置状态，不重新 {@code detect()} ——
     * 否则村民临时转身会让每一步都重新算工位，造成误判与反复重置。</p>
     */
    private boolean validateCurrentStation(String phase) {
        VillagerStation station = requireStation();
        MarkerBlockValidation validation = villagerStationService.validate(station);
        if (!validation.valid()) {
            fail(phase + ": " + validation.reason());
            return false;
        }
        return true;
    }

    private VillagerTarget requireVillager() {
        return context.villagerTarget().orElseThrow(() -> new IllegalStateException("当前没有村民目标"));
    }

    private VillagerStation requireStation() {
        return context.villagerStation().orElseThrow(() -> new IllegalStateException("当前没有固定交易位"));
    }

    /** 统一失败出口：记录原因 + 播报 + 转 ERROR */
    private void fail(String reason) {
        context.setLastFailureReason(reason);
        logger.error(reason);
        transition(LibrarianState.ERROR, reason);
    }

    /** 统一转换出口：先校验上下文前置条件，再清动作提交位并转换 */
    private void transition(LibrarianState state, String reason) {
        validateContextFor(state);
        actionSubmitted = false;
        stateMachine.transitionTo(state, reason);
    }

    /** 转换前置条件：防止「带着上一位村民的上下文」进入需要干净上下文的状态 */
    private void validateContextFor(LibrarianState state) {
        switch (state) {
            case SEARCH_VILLAGER -> {
                if (context.villagerTarget().isPresent() || context.hasPendingLectern() || context.tradeProcessActive()) {
                    throw new IllegalStateException("当前村民周期未清理，不能搜索新村民");
                }
            }
            case MOVE_TO_VILLAGER, FIND_LECTERN_POSITION, PLACE_LECTERN, WAIT_PROFESSION, OPEN_TRADE,
                 WAIT_TRADE_SCREEN, READ_TRADES, CHECK_ENCHANTMENT, RESET, BREAK_LECTERN,
                 WAIT_UNEMPLOYED, SUCCESS_FOUND, TRADE_PROCESS, SELECT_TRADE, WAIT_TRADE_SYNC,
                 TAKE_TRADE_OUTPUT, VERIFY_PURCHASE, COMPLETE_TARGET, END_VILLAGER_CYCLE -> requireVillager();
            case FINISH -> {
                if (!context.targetProgress().allCompleted()) throw new IllegalStateException("仍有未完成目标，不能结束任务");
            }
            case IDLE, START, SELECT_TARGET_VILLAGER, ERROR -> {
            }
        }
        if ((state == LibrarianState.RESET || state == LibrarianState.BREAK_LECTERN) && !context.hasPendingLectern()) {
            throw new IllegalStateException("没有待处理讲台，不能进入职业刷新重置");
        }
        if ((state == LibrarianState.TRADE_PROCESS || state == LibrarianState.SELECT_TRADE
            || state == LibrarianState.WAIT_TRADE_SYNC || state == LibrarianState.TAKE_TRADE_OUTPUT
            || state == LibrarianState.VERIFY_PURCHASE || state == LibrarianState.COMPLETE_TARGET)
            && !context.tradeProcessActive()) {
            throw new IllegalStateException("目标交易尚未锁定，不能进入交易流程");
        }
    }

    /** 状态转换回调：调试模式播状态，调试音效开启时按状态分组发音 */
    private void onTransition(StateTransition transition) {
        if (config.debugLogging()) logger.state(transition.currentState(), context, movementService.getStatus());
        if (config.debugSound()) sound.play(soundEvent(transition.currentState()));
    }

    /** 状态处理器抛异常时的回调：记录原因并播报（状态机已转到 ERROR） */
    private void onStateFailure(RuntimeException exception) {
        context.setLastFailureReason(exception.getMessage());
        logger.error("状态执行异常: " + exception.getMessage());
    }

    /** 状态 → 音效事件映射（逐条照搬旧项目分组） */
    private DebugSoundEvent soundEvent(LibrarianState state) {
        return switch (state) {
            case START -> DebugSoundEvent.START;
            case SEARCH_VILLAGER, SELECT_TARGET_VILLAGER, FIND_LECTERN_POSITION -> DebugSoundEvent.SEARCH;
            case MOVE_TO_VILLAGER, MOVE_TO_STAND_POSITION -> DebugSoundEvent.MOVE;
            case PLACE_LECTERN, BREAK_OBSTACLE -> DebugSoundEvent.PLACE;
            case WAIT_PROFESSION, BREAK_LECTERN, WAIT_UNEMPLOYED -> DebugSoundEvent.REFRESH;
            case OPEN_TRADE, WAIT_TRADE_SCREEN, READ_TRADES, CHECK_ENCHANTMENT, SUCCESS_FOUND,
                 TRADE_PROCESS, SELECT_TRADE, WAIT_TRADE_SYNC, TAKE_TRADE_OUTPUT, VERIFY_PURCHASE -> DebugSoundEvent.TRADE;
            case COMPLETE_TARGET, FINISH -> DebugSoundEvent.SUCCESS;
            case ERROR -> DebugSoundEvent.ERROR;
            case IDLE, RESET, END_VILLAGER_CYCLE -> DebugSoundEvent.RESET;
        };
    }
}
