package com.yiyiaddon.service.reconnect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;

/**
 * 自动重连引擎（唯一实现）：tick 倒计时驱动，不开线程、不 sleep。
 *
 * <p><b>为什么是 tick 驱动</b>：断线之后 {@code mc.player} / {@code mc.level} 都为 {@code null}，
 * 任何依赖玩家实体的调度都会失效；调用方必须在自己的 {@code player == null} 检查<b>之前</b>调用
 * {@link #tick()}，倒计时才能在断线界面阶段继续走完。连接动作本身仍在主线程发起。</p>
 *
 * <p><b>谁在用（第 169 条：同源逻辑只有一份）</b>：自动登入模块的断线重连（旧项目 1:1 行为）与
 * 独立的「自动重连」模块各持一个实例，通过 {@link Policy} 注入自己的设置。引擎<b>不认识任何模块</b>，
 * 也不读写状态文件；「谁在接管」由各模块自己判断并让路。</p>
 *
 * <p><b>状态归属</b>：最近一次服务器（地址 + {@link ServerData}）与失败计数都只存在于内存 ——
 * 重连的意义是回到「本次会话刚才那台服务器」，重启游戏后没有任何可用的连接凭据，因此不落盘。</p>
 */
public final class ReconnectEngine {

    /** 调度策略：由调用方（模块设置）提供，引擎只读它、不改它 */
    public interface Policy {

        /** 总开关关闭时 {@link #startReconnect} 直接返回 {@code false} */
        boolean enabled();

        /** 断线后等待多少 tick 再重连 */
        int delayTicks();

        /** 连续失败上限 */
        int maxAttempts();

        /** 无限重连：忽略 {@link #maxAttempts()} */
        boolean unlimited();
    }

    private final Minecraft mc;
    private final Policy policy;
    private final Runnable onReconnectStart;

    /** 最近一次进入的服务器（断线后靠它发起连接） */
    private ServerAddress lastAddress;
    private ServerData lastServerData;

    /** 已发起的重连次数（连上并稳定后归零） */
    private int reconnectAttempts;

    /** 剩余 tick；{@code -1} = 没有待执行的重连 */
    private int ticksLeft = -1;

    public ReconnectEngine(Minecraft mc, Policy policy, Runnable onReconnectStart) {
        this.mc = mc;
        this.policy = policy;
        this.onReconnectStart = onReconnectStart == null ? () -> { } : onReconnectStart;
    }

    /** 每个 tick 调用一次；断线后（{@code player == null}）也必须继续调用，否则倒计时停摆。 */
    public void tick() {
        if (ticksLeft < 0) return;
        if (ticksLeft == 0) {
            ticksLeft = -1;
            doReconnect();
            return;
        }
        ticksLeft--;
    }

    /**
     * 进入服务器时调用：记录连接信息，供断线后重连使用。
     *
     * <p>{@code data} 为空时用地址合成一条（与原版「直接连接」的 {@code ServerData.Type.OTHER}
     * 同型），保证 {@link ConnectScreen} 拿得到入参。</p>
     */
    public void recordServer(ServerAddress address, ServerData data) {
        if (address == null) return;
        this.lastAddress = address;
        this.lastServerData = data != null ? data
            : new ServerData("自动重连", address.getHost(), ServerData.Type.OTHER);
    }

    /**
     * 断线时调用：按 {@link Policy} 的等待时间与次数上限调度一次重连。
     *
     * @return {@code true} = 已调度；{@code false} = 设置关闭 / 无服务器记录 / 已达上限
     */
    public boolean startReconnect() {
        return startReconnect(policy.delayTicks(), policy.maxAttempts());
    }

    /**
     * 按指定等待时间与次数上限调度（自动登入的自用路线异常恢复要用它覆盖默认值）。
     *
     * @param delayTicks  等待 tick
     * @param maxAttempts 次数上限，仅当 {@link Policy#unlimited()} 为 {@code false} 时生效
     */
    public boolean startReconnect(int delayTicks, int maxAttempts) {
        if (!policy.enabled()) return false;
        if (!hasServer()) return false;
        if (!policy.unlimited() && reconnectAttempts >= maxAttempts) return false;

        reconnectAttempts++;
        ticksLeft = Math.max(0, delayTicks);
        onReconnectStart.run();
        return true;
    }

    /** 清空全部状态（含服务器记录）：模块关闭、或调用方明确要「彻底停下并忘掉服务器」时使用。 */
    public void reset() {
        ticksLeft = -1;
        lastAddress = null;
        lastServerData = null;
        reconnectAttempts = 0;
    }

    /** 连接稳定后由调用方调用，把失败计数归零（这样下一次断线又从第 1 次算起）。 */
    public void markConnectionStable() {
        reconnectAttempts = 0;
    }

    public int getReconnectAttempts() {
        return reconnectAttempts;
    }

    /** 是否有待执行的重连 */
    public boolean isScheduled() {
        return ticksLeft >= 0;
    }

    /** 剩余 tick；{@code -1} 表示没有待执行的重连 */
    public int getTicksLeft() {
        return ticksLeft;
    }

    /** 是否已记录服务器（没有记录时不可能重连成功，界面据此给出提示） */
    public boolean hasServer() {
        return lastAddress != null && lastServerData != null;
    }

    /** 最近一次服务器的显示地址；没有记录时返回空串 */
    public String serverAddressText() {
        if (lastAddress == null) return "";
        String host = lastAddress.getHost();
        return lastAddress.getPort() == 25565 ? host : host + ":" + lastAddress.getPort();
    }

    /** 跳过待执行的倒计时，立刻连接 */
    public void reconnectNow() {
        if (ticksLeft < 0) return;
        ticksLeft = -1;
        doReconnect();
    }

    /** 取消待执行的重连，但保留服务器记录（调用方若还要「立即重连」，记录必须还在） */
    public void cancelScheduled() {
        ticksLeft = -1;
    }

    // ── 私有 ────────────────────────────────────────────────────────────────

    private void doReconnect() {
        if (!hasServer()) return;
        ConnectScreen.startConnecting(
            new JoinMultiplayerScreen(new TitleScreen()),
            mc,
            lastAddress,
            lastServerData,
            false,
            null
        );
    }
}
