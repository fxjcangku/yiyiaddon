package com.yiyiaddon.feature.teleport.model;

/**
 * 传送状态机状态：Observe → Decide → Execute → Verify。
 * 观察与决策在触发瞬间同步完成，执行与验证为异步（等待服务端权威包或超时）。
 */
public enum TeleportState {
    /** 空闲：无进行中的传送 */
    IDLE("空闲"),
    /** 观察：锁定玩家位置与维度快照 */
    OBSERVE("观察"),
    /** 决策：按模式计算目标落点并做安全判据 */
    DECIDE("计算"),
    /** 执行：本地位移 + 发包同步 */
    EXECUTE("执行"),
    /** 验证：观察服务端位置权威包判定接受/回弹 */
    VERIFY("验证"),
    /** 成功：服务端接受或验证窗口内无回弹 */
    SUCCESS("成功"),
    /** 失败：决策失败/执行失败/回弹/环境变化中止 */
    FAILED("失败");

    private final String cn;

    TeleportState(String cn) {
        this.cn = cn;
    }

    /** 中文状态名（播报与调试档案用） */
    public String cn() {
        return cn;
    }
}
