package com.yiyiaddon.feature.librarian.config;

import com.yiyiaddon.feature.librarian.model.EnchantmentTarget;

import java.util.List;
import java.util.Objects;

/**
 * 自动图书管理员 · 配置模型。
 *
 * <p>汇聚自动图书管理员运行所需的全部业务参数（目标、半径、各类超时与延迟），
 * 与界面设置载体解耦，供编排器与状态机使用。</p>
 *
 * <p><b>四类参数不受设置项控制</b>（照搬旧项目 {@code AutoLibrarianConfig.defaults()}）：
 * 移动到达半径 3、交易界面超时 100 tick、交易同步超时 100 tick、移动超时 1200 tick。
 * 这三个超时与一个半径在旧项目里就是硬编码默认值，迁移后保持同一取值与同一「不可配」属性。</p>
 *
 * <p>迁移自旧项目 {@code librarian/config/AutoLibrarianConfig}（80 行），逐字照搬。</p>
 */
public record LibrarianConfig(
    /** 目标附魔列表（至少一个） */
    List<EnchantmentTarget> enchantmentTargets,
    /** 村民搜索半径（格） */
    int villagerSearchRadius,
    /** 移动到达判定半径（格） */
    int movementArrivalRadius,
    /** 允许的最高绿宝石价格 */
    int maximumEmeraldPrice,
    /** 等待村民职业同步的超时（tick） */
    int professionTimeoutTicks,
    /** 等待交易界面打开的超时（tick） */
    int tradeScreenTimeoutTicks,
    /** 等待交易同步的超时（tick） */
    int tradeSyncTimeoutTicks,
    /** 移动超时（tick） */
    int movementTimeoutTicks,
    /** 普通业务动作间隔（tick） */
    int actionDelayTicks,
    /** 拆除与重新放置讲台的间隔（tick） */
    int resetDelayTicks,
    /** 完成后是否移除目标 */
    boolean removeCompletedTarget,
    /** 是否输出调试日志 */
    boolean debugLogging,
    /** 是否播放调试音效 */
    boolean debugSound
) {
    public LibrarianConfig {
        enchantmentTargets = List.copyOf(Objects.requireNonNull(enchantmentTargets, "enchantmentTargets"));
        if (enchantmentTargets.isEmpty()) throw new IllegalArgumentException("至少需要一个目标附魔");
        requirePositive(villagerSearchRadius, "villagerSearchRadius");
        requirePositive(movementArrivalRadius, "movementArrivalRadius");
        requirePositive(maximumEmeraldPrice, "maximumEmeraldPrice");
        requirePositive(professionTimeoutTicks, "professionTimeoutTicks");
        requirePositive(tradeScreenTimeoutTicks, "tradeScreenTimeoutTicks");
        requirePositive(tradeSyncTimeoutTicks, "tradeSyncTimeoutTicks");
        requirePositive(movementTimeoutTicks, "movementTimeoutTicks");
        requirePositive(actionDelayTicks, "actionDelayTicks");
        requirePositive(resetDelayTicks, "resetDelayTicks");
    }

    /** 默认配置：目标修复（mending 最高等级），半径 32，价格上限 64 */
    public static LibrarianConfig defaults() {
        return new LibrarianConfig(
            List.of(new EnchantmentTarget("minecraft:mending", 1, true)),
            32,
            3,
            64,
            200,
            100,
            100,
            1200,
            2,
            10,
            false,
            true,
            true
        );
    }

    /** 校验数值必须大于 0 */
    private static void requirePositive(int value, String name) {
        if (value < 1) throw new IllegalArgumentException(name + " 必须大于 0");
    }
}
