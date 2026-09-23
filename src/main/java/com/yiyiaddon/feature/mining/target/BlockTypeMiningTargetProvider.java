package com.yiyiaddon.feature.mining.target;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

import java.util.List;

/**
 * 自动挖矿 · <b>普通模式目标提供者</b>（旧行为的原样封装）。
 *
 * <p><b>本类的唯一职责是把既有调用原样搬进来</b>，让状态机不必知道「普通模式」这件事。
 * 每个方法的返回值都刻意与引入 {@link MiningTargetProvider} 之前的判据<b>逐条等价</b>：</p>
 *
 * <ul>
 *     <li>{@link #issue(boolean)} —— 等价于旧 {@code baritone.startMining(module.getMiningTargets()[,
 *         broadcast])}（含「未选择目标矿石，无法启动挖矿」这条旧播报）；</li>
 *     <li>{@link #engineActive()} —— 等价于旧 {@code isPathing() || isMiningActive()}；</li>
 *     <li>{@link #ready()} —— 等价于旧「目标能解析出方块」（旧判据 {@code getTargetBlocks().isEmpty()}）；</li>
 *     <li>{@link #exhausted()} —— 恒 false：普通模式「附近矿挖完了」由既有「mine 连续重启 3 次」兜底，
 *         不引入新判据；</li>
 *     <li>{@link #progressWatchdogEnabled()} —— 恒 true（普通模式的第三档自愈照旧生效）。</li>
 * </ul>
 */
public final class BlockTypeMiningTargetProvider implements MiningTargetProvider {

    private final AutoMinerModule module;

    public BlockTypeMiningTargetProvider(AutoMinerModule module) {
        this.module = module;
    }

    @Override
    public String modeNameCn() {
        return "普通模式";
    }

    @Override
    public String scanModeCn() {
        return "视野内所有目标矿";
    }

    @Override
    public boolean usesBlockTypeScan() {
        return true;
    }

    @Override
    public boolean ready() {
        // 与旧判据逐字同源：旧看门狗用的是 getTargetBlocks().isEmpty()
        return !module.getTargetBlocks().isEmpty();
    }

    @Override
    public String notReadyReasonCn() {
        // 文案逐字取自旧状态机的同一分支（旧 :1417），一个字都没改
        return "采掘目标解析不出方块 §8▸ 请在配置页重新选择目标";
    }

    @Override
    public boolean issue(boolean broadcast) {
        List<Block> targets = module.getMiningTargets();
        // 空表时由 startMining 自己播报旧文案（与旧调用点完全一致），这里只回报结果
        module.getBaritone().startMining(targets, broadcast);
        return !targets.isEmpty();
    }

    @Override
    public boolean reissue() {
        return issue(true);
    }

    @Override
    public void tick() {
        // 普通模式没有额外推进：男中音自己管 mine 进程
    }

    @Override
    public boolean engineActive() {
        return module.getBaritone().isPathing() || module.getBaritone().isMiningActive();
    }

    @Override
    public boolean exhausted() {
        // 普通模式不引入新判据：附近有没有矿由「mine 退出 → 重启 3 次」那条既有兜底回答
        return false;
    }

    @Override
    public boolean progressWatchdogEnabled() {
        return true;
    }

    @Override
    public void reset() {
    }

    @Override
    public void resetSession() {
    }

    @Override
    public String statusCn() {
        return "按矿物类型扫描（男中音 mine）";
    }

    @Override
    public BlockPos lockedTargetOrNull() {
        // 普通模式没有「精确坐标目标」这个概念：挖哪一格由男中音自己决定
        return null;
    }
}
