package com.yiyiaddon.seed.worker.client;

/**
 * 种子挖矿 · 本地世界生成计算器（Worker）· <b>客户端侧状态机</b>（阶段 232 口径第二十四节）。
 *
 * <p>它描述的是<b>进程与连接</b>的状态，不是界面的状态；界面上的「正在启动 / 已就绪 / 正在预测」
 * 由 {@code SeedMiningService} 把本状态与业务状态合成后再展示（口径第二十四、二十五节）。</p>
 */
public enum SeedWorkerState {

    /** 还没启动。 */
    STOPPED("未启动"),

    /** 进程已拉起，正在等宿主就绪与握手（这一段最慢，界面必须显示「正在启动…」）。 */
    STARTING("正在启动"),

    /** 握手成功、会话可用，可以接预测请求。 */
    READY("已就绪"),

    /** 有一个预测在跑（同一时刻只允许一个，口径第五十九节）。 */
    BUSY("正在预测"),

    /** 启动失败 / 握手失败 / 进程崩溃 / 超时（已停止，可由下一次请求尝试重启一次）。 */
    FAILED("异常"),

    /** 正在收尾（发停机指令 → destroy → destroyForcibly 的阶梯）。 */
    STOPPING("正在停止");

    /** 中文显示名（只进日志与开发诊断；正式界面另有文案，口径第七十四节）。 */
    private final String displayNameCn;

    SeedWorkerState(String displayNameCn) {
        this.displayNameCn = displayNameCn;
    }

    /** 中文显示名。 */
    public String displayNameCn() {
        return displayNameCn;
    }

    /** 是否处在「可以用」的状态。 */
    public boolean usable() {
        return this == READY || this == BUSY;
    }
}
