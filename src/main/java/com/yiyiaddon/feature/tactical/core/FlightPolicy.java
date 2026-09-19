package com.yiyiaddon.feature.tactical.core;

/**
 * 飞行绕过模式与决策（纯数据，不含任何执行逻辑）。
 *
 * <p>逐字移植旧项目 {@code tactical/core/FlightPolicy.java}（74 行），
 * 唯一差异是包名。</p>
 *
 * @author yiyijia
 */
public final class FlightPolicy {

    private FlightPolicy() {
    }

    /** 五种飞行模式；显示名与配置存档名保持一致，迁移时用户配置不丢失 */
    public enum FlightMode {

        /** 发包飞行：需服务端授予飞行能力，置位飞行态并同步服务端 */
        PACKET_FLY("发包飞行"),

        /** 原版连跳（非飞行）：落地即跳的连续兔子跳，非飞行安全降级档 */
        VANILLA_MIMIC("原版连跳（非飞行）"),

        /** 安全滑翔：自动换鞘翅 + 官方起伞，fallFlying 豁免浮空判定 */
        SAFE_GLIDE("安全滑翔"),

        /** 烟花火箭：滑翔中周期性使用烟花推进，服务端完全合法 */
        FIREWORK_BOOST("烟花火箭"),

        /** 序列垫脚：真实放置方块提供物理支撑，延迟拆除并周期性留痕 */
        SEQUENCE_SCAFFOLD("序列垫脚");

        /** 中文显示名（同时用于配置存档） */
        public final String displayName;

        FlightMode(String displayName) {
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    /** 一次飞行准入裁决的原因 */
    public enum FlightReason {

        /** 允许执行当前模式 */
        GRANTED,

        /** 拉回冷却期：全模式统一暂停 2 秒（静默，不播报） */
        COOLDOWN,

        /** 命中高风险反作弊：发包飞行自动降级 */
        HIGH_RISK_AC,

        /** 服务器未授予飞行能力：发包飞行不可用，回落可执行模式 */
        NO_FLY_ABILITY,

        /** 连续拉回触发降级：沿降级链走靶 */
        DEGRADED
    }

    /** 裁决结果：目标模式 + 原因 */
    public record FlightDecision(FlightMode mode, FlightReason reason) {

        /** 是否放行执行（仅冷却期为 false） */
        public boolean granted() {
            return reason != FlightReason.COOLDOWN;
        }
    }
}
