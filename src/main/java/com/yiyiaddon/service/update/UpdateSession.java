package com.yiyiaddon.service.update;

/** 当前进程的提示门闩。只存在内存中，退出服务器、重开界面和重置配置均不会清除它。 */
public final class UpdateSession {
    private boolean prompted;

    /** 只有允许显示且确有更新时才消耗提示机会；显示一次后本进程不再自动弹出。 */
    public boolean claimPrompt(boolean safeScreen, boolean hasUpdate) {
        if (prompted || !safeScreen || !hasUpdate) return false;
        prompted = true;
        return true;
    }
}
