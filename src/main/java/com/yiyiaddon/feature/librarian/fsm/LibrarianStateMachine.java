package com.yiyiaddon.feature.librarian.fsm;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * 自动图书管理员 · 状态机。
 *
 * <p>驱动 {@link LibrarianState} 状态流转：维护合法转换表、各状态处理器，
 * 通过 {@code transitionListener} 回调每次转换供播报 / 调试，处理器抛异常时
 * 统一转入 {@link LibrarianState#ERROR} 并通知 {@code errorHandler}。</p>
 *
 * <p><b>三条硬约束（照搬旧项目）</b>：① 重复进入当前状态直接抛异常（调用方写错就该炸，
 * 而不是静默空转）；② 转换必须先过白名单，非法转换抛异常；③ 处理器异常一律转 ERROR，
 * 从 ERROR 转出同样受表约束（表内 ERROR 只允许转到 ERROR）。</p>
 *
 * <p>迁移自旧项目 {@code librarian/fsm/AutoLibrarianStateMachine}（156 行），转换表逐条照搬。</p>
 */
public final class LibrarianStateMachine {
    /** 状态合法转换表（源状态 → 允许的目标状态集合） */
    private static final Map<LibrarianState, Set<LibrarianState>> ALLOWED_TRANSITIONS = createAllowedTransitions();
    /** 各状态的处理器（IDLE 不接受处理器） */
    private final Map<LibrarianState, Runnable> handlers = new EnumMap<>(LibrarianState.class);
    /** 状态转换监听器（用于播报 / 调试） */
    private final Consumer<StateTransition> transitionListener;
    /** 错误处理器（处理器抛异常时回调） */
    private final Consumer<RuntimeException> errorHandler;
    /** 当前状态 */
    private LibrarianState currentState = LibrarianState.IDLE;
    /** 累计 tick 计数 */
    private long currentTick;
    /** 进入当前状态的 tick */
    private long stateEnteredTick;

    public LibrarianStateMachine(
        Consumer<StateTransition> transitionListener,
        Consumer<RuntimeException> errorHandler
    ) {
        this.transitionListener = Objects.requireNonNull(transitionListener, "transitionListener");
        this.errorHandler = Objects.requireNonNull(errorHandler, "errorHandler");
    }

    /** 注册某状态的处理器（禁止重复注册，IDLE 不接受处理器） */
    public void register(LibrarianState state, Runnable handler) {
        Objects.requireNonNull(state, "state");
        Objects.requireNonNull(handler, "handler");
        if (state == LibrarianState.IDLE) throw new IllegalArgumentException("IDLE 不接受处理器");
        if (handlers.putIfAbsent(state, handler) != null) {
            throw new IllegalArgumentException("状态已注册: " + state);
        }
    }

    /** 启动状态机：进入 START 状态 */
    public void start() {
        transitionTo(LibrarianState.START, "模块启动");
    }

    /** 执行一次合法状态转换（校验转换表，回调监听器） */
    public void transitionTo(LibrarianState nextState, String reason) {
        Objects.requireNonNull(nextState, "nextState");
        if (nextState == currentState) throw new IllegalStateException("不允许重复进入当前状态: " + currentState);
        if (nextState != LibrarianState.IDLE && !ALLOWED_TRANSITIONS.get(currentState).contains(nextState)) {
            throw new IllegalStateException("非法状态转换: " + currentState + " -> " + nextState);
        }
        LibrarianState previousState = currentState;
        currentState = nextState;
        stateEnteredTick = currentTick;
        transitionListener.accept(new StateTransition(previousState, nextState, currentTick, reason));
    }

    /** 推进一 tick：执行当前状态处理器，异常时转入 ERROR */
    public void tick() {
        currentTick++;
        if (currentState == LibrarianState.IDLE) return;
        Runnable handler = handlers.get(currentState);
        if (handler == null) {
            fail(new IllegalStateException("状态没有处理器: " + currentState));
            return;
        }
        try {
            handler.run();
        } catch (RuntimeException exception) {
            fail(exception);
        }
    }

    /** 重置状态机到 IDLE */
    public void reset(String reason) {
        transitionTo(LibrarianState.IDLE, reason);
    }

    /** 处理器抛异常时转入 ERROR 并通知错误处理器 */
    private void fail(RuntimeException exception) {
        if (currentState != LibrarianState.ERROR) {
            transitionTo(LibrarianState.ERROR, exception.getMessage());
        }
        errorHandler.accept(exception);
    }

    /** 构建状态合法转换表：默认任意状态均可转 ERROR，再逐条声明业务转换 */
    private static Map<LibrarianState, Set<LibrarianState>> createAllowedTransitions() {
        Map<LibrarianState, Set<LibrarianState>> transitions = new EnumMap<>(LibrarianState.class);
        for (LibrarianState state : LibrarianState.values()) {
            transitions.put(state, EnumSet.of(LibrarianState.ERROR));
        }
        transitions.put(LibrarianState.IDLE, EnumSet.of(LibrarianState.START));
        allow(transitions, LibrarianState.START, LibrarianState.SEARCH_VILLAGER, LibrarianState.FINISH);
        allow(transitions, LibrarianState.SEARCH_VILLAGER, LibrarianState.SELECT_TARGET_VILLAGER);
        allow(transitions, LibrarianState.SELECT_TARGET_VILLAGER, LibrarianState.MOVE_TO_VILLAGER, LibrarianState.END_VILLAGER_CYCLE);
        allow(transitions, LibrarianState.MOVE_TO_VILLAGER, LibrarianState.FIND_LECTERN_POSITION, LibrarianState.END_VILLAGER_CYCLE);
        allow(transitions, LibrarianState.FIND_LECTERN_POSITION, LibrarianState.MOVE_TO_STAND_POSITION, LibrarianState.END_VILLAGER_CYCLE);
        allow(transitions, LibrarianState.MOVE_TO_STAND_POSITION, LibrarianState.BREAK_OBSTACLE, LibrarianState.PLACE_LECTERN, LibrarianState.END_VILLAGER_CYCLE);
        allow(transitions, LibrarianState.BREAK_OBSTACLE, LibrarianState.PLACE_LECTERN, LibrarianState.END_VILLAGER_CYCLE);
        allow(transitions, LibrarianState.PLACE_LECTERN, LibrarianState.BREAK_OBSTACLE, LibrarianState.WAIT_PROFESSION, LibrarianState.END_VILLAGER_CYCLE);
        allow(transitions, LibrarianState.WAIT_PROFESSION, LibrarianState.OPEN_TRADE, LibrarianState.RESET, LibrarianState.END_VILLAGER_CYCLE);
        allow(transitions, LibrarianState.OPEN_TRADE, LibrarianState.WAIT_TRADE_SCREEN, LibrarianState.RESET, LibrarianState.END_VILLAGER_CYCLE);
        allow(transitions, LibrarianState.WAIT_TRADE_SCREEN, LibrarianState.READ_TRADES, LibrarianState.RESET, LibrarianState.END_VILLAGER_CYCLE);
        allow(transitions, LibrarianState.READ_TRADES, LibrarianState.CHECK_ENCHANTMENT, LibrarianState.RESET, LibrarianState.END_VILLAGER_CYCLE);
        allow(transitions, LibrarianState.CHECK_ENCHANTMENT, LibrarianState.RESET, LibrarianState.SUCCESS_FOUND, LibrarianState.END_VILLAGER_CYCLE);
        allow(transitions, LibrarianState.RESET, LibrarianState.BREAK_LECTERN, LibrarianState.END_VILLAGER_CYCLE);
        allow(transitions, LibrarianState.BREAK_LECTERN, LibrarianState.WAIT_UNEMPLOYED, LibrarianState.END_VILLAGER_CYCLE);
        allow(transitions, LibrarianState.WAIT_UNEMPLOYED, LibrarianState.FIND_LECTERN_POSITION, LibrarianState.END_VILLAGER_CYCLE);
        allow(transitions, LibrarianState.SUCCESS_FOUND, LibrarianState.TRADE_PROCESS, LibrarianState.END_VILLAGER_CYCLE);
        allow(transitions, LibrarianState.TRADE_PROCESS, LibrarianState.SELECT_TRADE, LibrarianState.END_VILLAGER_CYCLE);
        allow(transitions, LibrarianState.SELECT_TRADE, LibrarianState.WAIT_TRADE_SYNC, LibrarianState.END_VILLAGER_CYCLE);
        allow(transitions, LibrarianState.WAIT_TRADE_SYNC, LibrarianState.TAKE_TRADE_OUTPUT, LibrarianState.END_VILLAGER_CYCLE);
        allow(transitions, LibrarianState.TAKE_TRADE_OUTPUT, LibrarianState.VERIFY_PURCHASE, LibrarianState.END_VILLAGER_CYCLE);
        allow(transitions, LibrarianState.VERIFY_PURCHASE, LibrarianState.COMPLETE_TARGET, LibrarianState.END_VILLAGER_CYCLE);
        allow(transitions, LibrarianState.COMPLETE_TARGET, LibrarianState.END_VILLAGER_CYCLE, LibrarianState.FINISH);
        allow(transitions, LibrarianState.END_VILLAGER_CYCLE, LibrarianState.SEARCH_VILLAGER);
        return Map.copyOf(transitions);
    }

    /** 向转换表添加「源状态 → 多个目标状态」的合法转换 */
    private static void allow(
        Map<LibrarianState, Set<LibrarianState>> transitions,
        LibrarianState source,
        LibrarianState... targets
    ) {
        transitions.get(source).addAll(EnumSet.of(targets[0], targets));
    }

    /** 返回当前状态 */
    public LibrarianState getCurrentState() {
        return currentState;
    }

    /** 返回累计 tick 计数 */
    public long getCurrentTick() {
        return currentTick;
    }

    /** 返回停留在当前状态的 tick 数 */
    public long getTicksInCurrentState() {
        return currentTick - stateEnteredTick;
    }
}
