package com.yiyiaddon.core.net;

/**
 * 发包处置：模块对「这个包该怎么办」的回答。
 *
 * <p>四种处置覆盖旧框架发包事件里的全部能力：放行、取消、改写包体（仅玩家输入）、延迟发出。</p>
 *
 * @param action     处置动作
 * @param inputFlags 改写后的输入标志位（仅 {@link Action#REPLACE_INPUT}）
 * @param delayMillis 延迟毫秒（仅 {@link Action#DELAY}）
 */
public record SendDecision(Action action, int inputFlags, long delayMillis) {

    public enum Action {
        /** 放行 */
        PASS,
        /** 取消（该包不再发出） */
        CANCEL,
        /** 改写为新的玩家输入标志位后发出 */
        REPLACE_INPUT,
        /** 延迟若干毫秒后发出 */
        DELAY
    }

    private static final SendDecision PASS = new SendDecision(Action.PASS, 0, 0L);

    public static SendDecision pass() {
        return PASS;
    }

    public static SendDecision cancel() {
        return new SendDecision(Action.CANCEL, 0, 0L);
    }

    /** 把玩家输入改写为给定标志位（其余字段由核心按当前包重建） */
    public static SendDecision replaceInput(int flags) {
        return new SendDecision(Action.REPLACE_INPUT, flags, 0L);
    }

    public static SendDecision delay(long millis) {
        return new SendDecision(Action.DELAY, 0, Math.max(0L, millis));
    }

    public boolean isPass() {
        return action == Action.PASS;
    }
}
