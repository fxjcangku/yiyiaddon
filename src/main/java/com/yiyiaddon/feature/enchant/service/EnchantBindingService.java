package com.yiyiaddon.feature.enchant.service;

import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.feature.enchant.EnchantModule;
import com.yiyiaddon.feature.enchant.model.EnchantPoint;
import com.yiyiaddon.feature.enchant.model.EnchantPointType;
import com.yiyiaddon.feature.enchant.repository.EnchantPointStore;
import com.yiyiaddon.platform.world.WorldIdentity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * 附魔点位绑定 / 移除 / 校验的<b>唯一实现</b>（旧项目 {@code command/FumoCommand} 的
 * {@code setPos:85-134} / {@code removePos:136-166} / {@code clearPoints:168-178}）。
 *
 * <p><b>为什么必须收敛到一处：</b>控制台「点位」页的「设置 / 删除」按钮与 {@code .fumo}
 * 指令读写的是同一份点位数据（{@link EnchantPointStore}），旧项目也把两者收敛成同一处
 * （旧 {@code FumoCommand.setPoint / removePoint:189-200} 就是 {@code new FumoCommand().setPos}）。
 * 因此本类不区分调用来源，页面与指令只调这里，不各自复制一条判断链。</p>
 *
 * <p><b>校验顺序逐条照旧</b>（顺序即回执优先级，不得调整）：模式门禁 → 世界绑定前置
 * → 已绑定覆盖保护 → 准星 / 挂机位取值 → 方块类型判定 → 写入落盘 → 成功回执。</p>
 *
 * <p><b>落盘</b>：{@link EnchantPointStore} 的每次写入都即时落盘（等价旧项目
 * {@code Modules.get().save()}），挂机视角与铁砧朝向随之一起写入。</p>
 *
 * <p><b>已知差异（登记）</b>：旧项目在 {@code 移除} 挂机位 / 铁砧时会连带清掉
 * {@code hangoutYaw/hangoutPitch/posAnvilFacing}；本项目的 {@link EnchantPointStore} 只提供
 * 「写入」而无「单字段清除」入口（不改既有文件），故这三个附加字段保留到下次写入或
 * {@code .fumo 清空}。取用它们的只有状态机（{@code restoreHangoutView} / 铁砧补放），
 * 而这两处都在对应点位已绑定的前提下才会执行，因此残留值不会被读到。</p>
 */
public final class EnchantBindingService {

    /** 回执前缀：与模块播报同一模块名（旧 {@code FumoCommand.MODULE_NAME}，逐字 {@code 自动附魔}） */
    private static final String MODULE_NAME = EnchantModule.MESSAGE_MODULE;

    private final EnchantModule module;
    private final Minecraft mc = Minecraft.getInstance();

    public EnchantBindingService(EnchantModule module) {
        this.module = module;
    }

    /**
     * 绑定一个点位（旧 {@code !fumo 设置 <节点>}）。
     *
     * @param type 点位类型
     * @return 是否真的写入了点位（控制台按它决定是否关闭界面，与旧卡片按钮同语义）
     */
    public boolean bind(EnchantPointType type) {
        if (type == null) return false;
        EnchantPointStore store = module.pointStore();

        // 模式门禁：当前目标模式不需要的点位禁止绑定，避免三模式点位混用（旧 :88-92）
        if (!EnchantPointType.requiredFor(module.settings().targetMode).contains(type)) {
            fail("设置点位失败", "当前为 [" + module.settings().targetMode.title() + "] 模式，无需绑定 ["
                + type.title() + "] 点位，请切换到对应模式后再设置");
            return false;
        }
        if (!preparePointContext(store)) return false;

        // 覆盖保护（旧 :95-98）
        if (store.has(type)) {
            fail("设置点位失败", "[" + type.title() + "] 已设置，请先执行 .fumo 移除 " + type.node()
                + " 后再重新设置");
            return false;
        }

        BlockPos pos;
        if (type == EnchantPointType.AFK) {
            // 挂机位记录玩家脚下站立位置（旧 :101-103）
            // 旧项目此处直接取 player 位置，未进入世界时不可达；本项目不新增文案，静默拒绝
            if (mc.player == null) return false;
            pos = mc.player.blockPosition();
        } else {
            BlockPos target = crosshairBlock();
            if (target == null) {
                fail("设置点位失败", "请将准星对准目标方块");
                return false;
            }
            if (!isValidTarget(type, target)) return false;
            pos = target;
        }

        store.bind(type, new EnchantPoint(pos.getX(), pos.getY(), pos.getZ()));
        if (type == EnchantPointType.AFK) {
            // 挂机位额外记视角（旧 :115-118）
            store.setHangoutView(mc.player.getYRot(), mc.player.getXRot());
        }
        if (type == EnchantPointType.ANVIL) {
            // 铁砧额外记朝向，用于铁砧损坏后原样恢复（旧 :119-122）
            store.setAnvilFacing(mc.level.getBlockState(pos).getValue(AnvilBlock.FACING).name());
        }

        CommandMessageFormatter.of(MODULE_NAME, "已设置" + type.title() + "点位")
            .world()
            .dimension(store.pointDimension())
            .coord(pos.getX(), pos.getY(), pos.getZ())
            .field("类型", type.title())
            .status(CommandMessageFormatter.Level.SUCCESS, "已保存")
            .send();
        return true;
    }

    /**
     * 删除一个点位（旧 {@code .fumo 移除 <节点>}）。
     *
     * <p>旧项目此分支<b>没有失败回执</b>：点位不存在同样报成功（{@code coord} 仅在原点位非 null 时追加）。
     * 控制台「删除」按钮按旧卡片写法先判 {@code has}，因此界面不会出现「删了不存在的东西」。</p>
     *
     * @return 是否真的删掉了点位
     */
    public boolean remove(EnchantPointType type) {
        if (type == null) return false;
        EnchantPointStore store = module.pointStore();

        EnchantPoint existing = store.get(type);
        store.unbind(type);

        // 旧 FumoCommand:143-149：移除挂机位 / 铁砧时连带清掉对应的附加数据
        if (type == EnchantPointType.AFK) store.clearHangoutView();
        if (type == EnchantPointType.ANVIL) store.clearAnvilFacing();

        CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, "已删除" + type.title() + "点位")
            .world()
            .dimension(store.pointDimension())
            .field("类型", type.title());
        if (existing != null) formatter.coord(existing.x(), existing.y(), existing.z());
        formatter.status(CommandMessageFormatter.Level.SUCCESS, "已删除");
        formatter.send();

        // 删光后连服务器 / 维度绑定一起清（旧 :161-165 的二次存档；点位已空，等价于只清附加数据）
        if (!store.hasAnyPoint()) store.clearAll();
        return existing != null;
    }

    /**
     * 清空全部点位 + 挂机视角 + 铁砧朝向 + 服务器维度绑定（旧 {@code .fumo 清空}，
     * {@code AutoEnchantBook.clearPoints:1701-1716} 清的就是这 14 项）。
     */
    public void clear() {
        module.pointStore().clearAll();
        CommandMessageFormatter.of(MODULE_NAME, "已清空全部点位")
            .world()
            .field("范围", "全部点位 + 挂机视角 + 服务器维度绑定")
            .status(CommandMessageFormatter.Level.SUCCESS, "已清空")
            .send();
    }

    // ── 校验（旧 :230-273） ──

    /** 准星命中的方块坐标；未命中 / 未进入世界返回 {@code null}（旧 :106-110） */
    private BlockPos crosshairBlock() {
        if (mc.level == null) return null;
        HitResult hit = mc.hitResult;
        if (hit == null || hit.getType() != HitResult.Type.BLOCK) return null;
        if (!(hit instanceof BlockHitResult blockHit)) return null;
        return blockHit.getBlockPos().immutable();
    }

    /** 方块类型判定（旧 {@code isValidTarget:230-250}）：附魔台 / 砂轮 / 铁砧各自文案，其余必须是容器 */
    private boolean isValidTarget(EnchantPointType type, BlockPos pos) {
        Block block = mc.level.getBlockState(pos).getBlock();
        if (type == EnchantPointType.ENCHANTING_TABLE) {
            if (block == Blocks.ENCHANTING_TABLE) return true;
            fail("设置点位失败", "准星指向的方块不是附魔台");
            return false;
        }
        if (type == EnchantPointType.GRINDSTONE) {
            if (block == Blocks.GRINDSTONE) return true;
            fail("设置点位失败", "准星指向的方块不是砂轮");
            return false;
        }
        if (type == EnchantPointType.ANVIL) {
            if (block == Blocks.ANVIL || block == Blocks.CHIPPED_ANVIL || block == Blocks.DAMAGED_ANVIL) return true;
            fail("设置点位失败", "准星指向的方块不是铁砧");
            return false;
        }
        if (mc.level.getBlockEntity(pos) instanceof Container) return true;
        fail("设置点位失败", "[" + type.title() + "] 必须设置为箱子、木桶或潜影盒容器");
        return false;
    }

    /**
     * 世界绑定前置（旧 {@code preparePointContext:252-273}）：首次绑定点位时记下所在服务器与维度，
     * 之后换服 / 换维度一律拒绝，切回原世界即可直接使用。
     */
    private boolean preparePointContext(EnchantPointStore store) {
        String server = WorldIdentity.server();
        String dimension = WorldIdentity.dimension();
        if (server == null || server.isBlank() || dimension == null || dimension.isBlank()) {
            fail("设置点位失败", "无法识别当前服务器或维度，点位未保存");
            return false;
        }
        if (!store.hasAnyPoint()) {
            store.bindContext(server, dimension);
            return true;
        }
        if (store.pointServer() == null || store.pointDimension() == null) {
            fail("设置点位失败", "旧版点位没有世界绑定，请先执行 .fumo 清空 后重新设置");
            return false;
        }
        if (!store.matchesCurrentContext()) {
            fail("设置点位失败", "现有点位属于其他服务器或维度，请切回原世界，或执行 .fumo 清空 后重设");
            return false;
        }
        return true;
    }

    /** 失败播报：统一「标题 + 原因 + 状态」中文格式（旧 {@code fail:286-291}） */
    private static void fail(String title, String reason) {
        CommandMessageFormatter.of(MODULE_NAME, title)
            .field("原因", reason)
            .status(CommandMessageFormatter.Level.FAILURE, "操作未完成")
            .send();
    }
}
