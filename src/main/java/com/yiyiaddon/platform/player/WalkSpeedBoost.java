package com.yiyiaddon.platform.player;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

/**
 * 走路提速：给玩家挂一个瞬态移速修饰符（自动挖矿与星露谷农场共用同一份实现）。
 *
 * <p><b>来源</b>（用户 2026-09-18 原话）：<i>「在帮我默认加点移速 发点包也行 走快一点点就行了
 * 不用加设置 就默认写在代码里面」</i>。原实现只写在 {@code AutoMinerModule} 里（旧修饰符 id
 * {@code yiyiaddon:miner_walk_speed}）；用户 2026-09-21 要求把这份加速「一比一复刻」到星露谷农场，
 * 故抽到这里由两处共用 —— 各留一份实现会随档位调整走散，两个模块同时开着还会把加成叠成 +80%。</p>
 *
 * <p><b>档位口径</b>（照抄挖矿的实机定稿）：按药水那套命名，1 档 = 速度一（+20%）、
 * 2 档 = 速度二（+40%），现在上 2 档。要调就改 {@link #LEVEL} 一个数字 —— 客户端跑得比服务端认的
 * 快就会被拉回（「你移动得太快」），被拉回就往下调一档。</p>
 *
 * <p><b>为什么要每刻补挂：</b>这是<b>瞬态</b>修饰符：不落盘，但服务端下发属性同步包会整体重置
 * 客户端的属性实例、把修饰符冲掉，所以开启时挂一次、之后每刻补一次（已挂着时只是两次查表）。
 * 功能关闭时调 {@link #clear()}，玩家身上不留任何本模组的修饰符。</p>
 */
public final class WalkSpeedBoost {

    private static final Minecraft mc = Minecraft.getInstance();

    /** 移速修饰符 id：瞬态，只在该功能开启期间挂在玩家身上 */
    private static final Identifier MODIFIER_ID =
        Identifier.fromNamespaceAndPath("yiyiaddon", "walk_speed");

    /** 移速加成档位：1 = 速度一（+20%）、2 = 速度二（+40%） */
    private static final int LEVEL = 2;

    /** 加成值：每档 +20% 玩家基础移速 0.1（见 {@link #LEVEL}） */
    private static final double BONUS = 0.02 * LEVEL;

    private WalkSpeedBoost() {
    }

    /** 挂上走路提速；已经挂着、或没有玩家时什么都不做。 */
    public static void apply() {
        if (mc.player == null) return;
        AttributeInstance speed = mc.player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed == null || speed.getModifier(MODIFIER_ID) != null) return;
        speed.addTransientModifier(new AttributeModifier(
            MODIFIER_ID, BONUS, AttributeModifier.Operation.ADD_VALUE));
    }

    /** 摘掉走路提速；没有玩家时什么都不做。 */
    public static void clear() {
        if (mc.player == null) return;
        AttributeInstance speed = mc.player.getAttribute(Attributes.MOVEMENT_SPEED);
        if (speed == null) return;
        speed.removeModifier(MODIFIER_ID);
    }
}
