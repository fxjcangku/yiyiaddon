package com.yiyiaddon.feature.mining.service;

import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.model.MiningPoint;
import com.yiyiaddon.feature.mining.model.MiningPointType;
import com.yiyiaddon.feature.mining.repository.MiningPointStore;
import com.yiyiaddon.platform.world.WorldIdentity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * 点位绑定 / 移除的唯一实现（旧项目 {@code mining/command/WKCommand} 的绑定区段，{@code :127-301}）。
 *
 * <p><b>为什么必须收敛到一处：</b>配置页的「设置 / 删除」按钮与后续 {@code .wk} 指令改的是同一份
 * 点位数据（{@link MiningPointStore}），唯一性判据也必须是同一处实现。本类把「读准星 → 判容器 →
 * 写入 / 移除 → 播报」串成一条链路，页面与指令都只调这里，不各自复制一份判断。</p>
 *
 * <p><b>唯一性判据（用户 2026-09-16 裁定）：</b>覆盖保护只按<b>类型</b>判——同一类型不能绑两个
 * （不能有两个矿物箱），已绑则要求先删除；三个点位<b>各在不同维度是允许的</b>
 * （例如下界食物箱 + 主世界矿物箱 + 末地挂机修复点），因此<b>不做</b>旧项目的「跨维度绑定拒绝」，
 * 也<b>不做</b>旧项目的「三点位两两 32 格」限制。</p>
 *
 * <p><b>两套文案按来源分流（{@code fromGui}），此为旧项目现状，禁止统一：</b></p>
 * <ul>
 *   <li><b>GUI 路径</b>（{@code fromGui = true}，页面卡片按钮）：未命中与「非容器」两句多出
 *       「请重新设置」（旧 {@code :525 / :529 / :538 / :542}）；解绑成功后是一条
 *       {@code §c§l✗ 已删除 X 绑定} 单行回执（旧 {@code :573}）。</li>
 *   <li><b>指令路径</b>（{@code fromGui = false}，{@code .wk}）：未命中与「非容器」两句不带
 *       「请重新设置」（旧 {@code :141 / :146 / :186 / :191}）；绑定成功后走
 *       {@link CommandMessageFormatter} 结构化回执（旧 {@code wkBindSuccess :469-478}），
 *       解绑同样走结构化回执（旧 {@code unbind :293-299}）。</li>
 * </ul>
 *
 * <p>其余文案与两条链路共用：覆盖保护（旧 {@code :133-137}）、维度取不到（旧 {@code :152}）、
 * 挂机点「玩家不存在」（旧 {@code :231}）、解绑无绑定（旧 {@code :279}），
 * 以及绑定成功回执（旧 {@code :470-478} 的 GUI 与指令同源）。</p>
 *
 * <p>播报前缀：{@code wkError} = 内容前加 {@code §6}（旧 {@code :487-489}），
 * {@code wkInfo} = 不加前缀（旧 {@code :480-482}），两者都经
 * {@link AutoMinerModule#MESSAGE_MODULE} 的模块前缀。</p>
 */
public final class MiningBindingService {

    /** 绑定已完成提示前缀；旧项目 {@code WKCommand :135 / :180 / :226} 原文（{@code §7提示：使用 §e.wk 移除 X §7删除}） */
    private static final String COVER_HINT_PREFIX = "§7提示：使用 §e.wk 移除 ";

    private final AutoMinerModule module;
    private final Minecraft mc = Minecraft.getInstance();

    public MiningBindingService(AutoMinerModule module) {
        this.module = module;
    }

    /**
     * 绑定一个点位。
     *
     * <p>检查顺序：覆盖保护（只按类型）→ 准星命中 → 容器判定（仅矿物箱 / 食物箱）→ 维度可用 →
     * 写入落盘 → 回执。旧项目的「跨维度拒绝」与「32 格」两道校验按用户 2026-09-16 裁定去掉。</p>
     *
     * @param type   点位类型（矿物箱 / 食物箱 / 挂机修复点）
     * @param fromGui 调用来源；true = 配置页卡片（多「请重新设置」），false = {@code .wk} 指令
     * @return 是否真的写入了点位
     */
    public boolean bind(MiningPointType type, boolean fromGui) {
        if (type == null) return false;

        // 覆盖保护：该点位已有绑定，必须先删除（旧 :133-137 / :178-182 / :224-228）
        MiningPointStore store = module.pointStore();
        if (store.has(type)) {
            wkError(type.displayName() + "已绑定，请先删除旧绑定再重新设置");
            wkInfo(COVER_HINT_PREFIX + type.displayName() + " §7删除");
            return false;
        }

        return type == MiningPointType.AFK ? bindAfk() : bindContainer(type, fromGui);
    }

    /**
     * 解绑一个点位。
     *
     * @param type    点位类型
     * @param fromGui 调用来源；true = 配置页卡片（{@code §c§l✗ 已删除 X 绑定}），
     *                false = {@code .wk} 指令（结构化回执）
     * @return 是否真的删除了点位
     */
    public boolean remove(MiningPointType type, boolean fromGui) {
        if (type == null) return false;

        MiningPointStore store = module.pointStore();
        MiningPoint removed = store.get(type);
        if (removed == null) {
            wkError("该坐标本来就没有绑定");
            return false;
        }
        store.remove(type);

        if (fromGui) {
            wkInfo("§c§l✗ 已删除 " + type.displayName() + " 绑定");
            return true;
        }

        CommandMessageFormatter.of(AutoMinerModule.MESSAGE_MODULE, "已删除" + type.displayName())
            .world()
            .dimension(removed.dimension())
            .coord(removed.x(), removed.y(), removed.z())
            .field("类型", type.displayName())
            .status(CommandMessageFormatter.Level.SUCCESS, "已删除")
            .send();
        return true;
    }

    // ── 容器类点位（矿物箱 / 食物箱） ──

    private boolean bindContainer(MiningPointType type, boolean fromGui) {
        BlockPos target = crosshairBlock();
        if (target == null) {
            wkError(fromGui ? "准星未对准任何方块，请重新设置" : "准星未对准任何方块");
            return false;
        }
        if (!isContainer(target)) {
            wkError(fromGui
                ? "目标方块不是容器（箱子/桶/潜影盒等），请重新设置"
                : "目标方块不是容器（箱子/桶/潜影盒等）");
            return false;
        }

        String dimension = WorldIdentity.dimension();
        if (dimension.isEmpty()) {
            wkError("无法获取当前维度信息");
            return false;
        }

        // 容器类点位不记视角（旧 WKData.here：yaw / pitch 写 0）
        module.pointStore().set(type, new MiningPoint(
            target.getX(), target.getY(), target.getZ(), dimension, 0f, 0f));
        bindSuccess(type.displayName(), target.getX(), target.getY(), target.getZ(), dimension);
        return true;
    }

    // ── 挂机修复点 ──

    private boolean bindAfk() {
        if (mc.player == null) {
            wkError("玩家不存在");
            return false;
        }

        BlockPos pos = mc.player.blockPosition();
        // 挂机修复点额外记录当前视角：修补时用于精准对准工作台（旧 :236-239）
        float yaw = mc.player.getYRot();
        float pitch = mc.player.getXRot();

        String dimension = WorldIdentity.dimension();
        if (dimension.isEmpty()) {
            wkError("无法获取当前维度信息");
            return false;
        }

        module.pointStore().set(MiningPointType.AFK, new MiningPoint(
            pos.getX(), pos.getY(), pos.getZ(), dimension, yaw, pitch));

        // 视角字段只挂在挂机修复点上（旧 :256-263）
        CommandMessageFormatter.of(AutoMinerModule.MESSAGE_MODULE, "已设置挂机修复点")
            .world()
            .dimension(dimension)
            .coord(pos.getX(), pos.getY(), pos.getZ())
            .field("类型", MiningPointType.AFK.displayName())
            .field("视角", String.format("偏航角 %.1f° / 俯仰角 %.1f°", yaw, pitch))
            .status(CommandMessageFormatter.Level.SUCCESS, "已保存")
            .send();
        return true;
    }

    // ── 辅助 ──

    /** 绑定成功回执（容器类点位；旧 {@code wkBindSuccess :469-478}，GUI 与指令同源） */
    private void bindSuccess(String name, int x, int y, int z, String dimension) {
        CommandMessageFormatter.of(AutoMinerModule.MESSAGE_MODULE, "已设置" + name)
            .world()
            .dimension(dimension)
            .coord(x, y, z)
            .field("类型", name)
            .status(CommandMessageFormatter.Level.SUCCESS, "已保存")
            .send();
    }

    /** 准星命中的方块坐标；未命中返回 {@code null}（旧 {@code getTargetBlock :452-457}） */
    private BlockPos crosshairBlock() {
        if (mc.player == null || mc.level == null) return null;
        HitResult hit = mc.hitResult;
        if (hit == null || hit.getType() != HitResult.Type.BLOCK) return null;
        if (!(hit instanceof BlockHitResult blockHit)) return null;
        return blockHit.getBlockPos().immutable();
    }

    /** 目标方块是否为容器（箱子 / 桶 / 潜影盒 / 漏斗等，旧 {@code isContainer :463-467}） */
    private boolean isContainer(BlockPos pos) {
        if (mc.level == null) return false;
        BlockEntity blockEntity = mc.level.getBlockEntity(pos);
        return blockEntity instanceof Container;
    }

    /** 错误播报：内容前缀 {@code §6}（旧 {@code wkError :487-489}） */
    private void wkError(String message) {
        CommandMessageFormatter.sendLine(AutoMinerModule.MESSAGE_MODULE, "§6" + message);
    }

    /** 普通播报：不加前缀（旧 {@code wkInfo :480-482}） */
    private void wkInfo(String message) {
        CommandMessageFormatter.sendLine(AutoMinerModule.MESSAGE_MODULE, message);
    }
}
