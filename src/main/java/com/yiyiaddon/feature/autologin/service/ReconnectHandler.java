package com.yiyiaddon.feature.autologin.service;

import com.yiyiaddon.feature.autologin.config.AutoLoginSettings;
import com.yiyiaddon.service.reconnect.ReconnectEngine;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;

/**
 * 自动登入的断线重连（旧项目 {@code autologin/service/ReconnectHandler} 的等价物）。
 *
 * <p><b>实体逻辑已上提为共用引擎</b>：倒计时、服务器记录、失败计数、发起连接这一整套现在只有
 * {@link ReconnectEngine} 一份（第 169 条），本类退化为「把 {@link AutoLoginSettings} 的四项重连设置
 * 翻译成引擎策略」的适配层 —— 自动登入的公开方法与行为一字未变，调用点（模块与两个控制台页）无需改动。</p>
 *
 * <p><b>与旧项目的等价性</b>：旧 {@code startReconnect()} 读 {@code reconnectDelay} /
 * {@code maxReconnectAttempts}；旧 {@code startReconnect(delay, max)} 供自用路线异常恢复覆盖等待时间与
 * 上限，同时仍受 {@code 无限重连} 与 {@code 自动重连} 两个开关约束 —— 三条判据的读法逐条照旧。</p>
 *
 * <p><b>未保留的两个只读方法</b>：旧 {@code getLastAddress()} / {@code getLastServerData()}
 * 在本项目全树无调用点（界面只读倒计时与调度状态），按「死代码可删」不搬；引擎内部仍持有这两项。</p>
 */
public final class ReconnectHandler {

    private final ReconnectEngine engine;

    public ReconnectHandler(Minecraft mc, AutoLoginSettings settings, Runnable onReconnectStart) {
        this.engine = new ReconnectEngine(mc, new ReconnectEngine.Policy() {
            @Override
            public boolean enabled() {
                return settings.autoReconnect;
            }

            @Override
            public int delayTicks() {
                return settings.reconnectDelay;
            }

            @Override
            public int maxAttempts() {
                return settings.maxReconnectAttempts;
            }

            @Override
            public boolean unlimited() {
                return settings.alwaysReconnect;
            }
        }, onReconnectStart);
    }

    /** 每个 tick 调用一次（断线后也要调用，不受 {@code player == null} 拦截） */
    public void tick() {
        engine.tick();
    }

    /** 进入服务器时调用，记录连接信息 */
    public void recordServer(ServerAddress address, ServerData data) {
        engine.recordServer(address, data);
    }

    /**
     * 断线时调用，按设置启动 tick 倒计时重连。
     *
     * @return true = 已调度；false = 未启用 / 无服务器记录 / 已达上限
     */
    public boolean startReconnect() {
        return engine.startReconnect();
    }

    /** 指定等待时间与次数上限（自用路线异常恢复） */
    public boolean startReconnect(int delayTicks, int maxAttempts) {
        return engine.startReconnect(delayTicks, maxAttempts);
    }

    /** 取消待执行的重连并清空全部状态（模块关闭、停止重连时调用） */
    public void reset() {
        engine.reset();
    }

    /** 连接稳定后由模块调用，将重试计数归零 */
    public void markConnectionStable() {
        engine.markConnectionStable();
    }

    public int getReconnectAttempts() {
        return engine.getReconnectAttempts();
    }

    public boolean isScheduled() {
        return engine.isScheduled();
    }

    /** 剩余 tick 数，-1 表示未调度 */
    public int getTicksLeft() {
        return engine.getTicksLeft();
    }

    /** 立即执行重连（跳过倒计时） */
    public void reconnectNow() {
        engine.reconnectNow();
    }

    public void cancelScheduled() {
        engine.cancelScheduled();
    }
}
