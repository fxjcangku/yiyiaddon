package com.yiyiaddon.feature.librarian.platform;

import com.yiyiaddon.feature.librarian.model.TradeOfferSnapshot;
import com.yiyiaddon.feature.librarian.model.VillagerTarget;
import com.yiyiaddon.feature.librarian.service.ActionResult;
import com.yiyiaddon.feature.librarian.service.TradeService;
import com.yiyiaddon.platform.container.MerchantTradeAccess;
import com.yiyiaddon.platform.container.SilentContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 自动图书管理员 · 交易实现（Minecraft 适配）。
 *
 * <p>负责打开村民交易界面、扫描附魔书报价、选中并购买、取出成品。
 * 报价只能从 {@code MerchantMenu#getOffers()} 读取，客户端禁止直接读
 * {@code Villager#getOffers()}，否则会抛异常导致闪退。</p>
 *
 * <p><b>「静默」是怎么实现的</b>：本类照常走 {@code gameMode.interact} 让服务端
 * 打开交易菜单（协议要求），但模块层会取消容器屏的显示，所以玩家看不到界面；
 * 报价与槽位仍由 {@code player.containerMenu} 正常同步。</p>
 *
 * <p><b>成交判据不在本类</b>：这里只负责「选中 → 取出」两次发包，
 * 是否真买到由 {@code LibrarianContext} 的库存计数对比判定（报价的 uses 不作判据）。</p>
 *
 * <p>迁移自旧项目 {@code librarian/integration/FabricTradeService}（192 行），判据逐条照搬；
 * 其中「选中报价」「结果槽快速移动」「关界面」三步改调公共层
 * {@link MerchantTradeAccess}（D6 拍板：同源协议只留一份）。</p>
 */
public final class MerchantTradeOps implements TradeService {
    /** 结果槽下标（0/1 为付款槽，2 为成品槽） */
    private static final int RESULT_SLOT_INDEX = 2;
    /** UUID 兜底扫描半径（格） */
    private static final int UUID_FALLBACK_RADIUS = 64;

    /** 已选中的报价序号（-1 表示未选中） */
    private int selectedIndex = -1;
    /** 当前交易界面是否由本模块打开（关闭时只关自己开的） */
    private boolean ownedByPlugin;

    @Override
    public ActionResult open(VillagerTarget target) {
        Minecraft mc = Minecraft.getInstance();
        if (isTradeScreenReady()) {
            ownedByPlugin = true;
            return ActionResult.success();
        }
        if (mc.player == null || mc.level == null || mc.gameMode == null) return ActionResult.retry("游戏世界尚未就绪。");
        // 先用 entity ID 快速查，失败时用 UUID 扫描兜底（chunk 重载后 entity ID 会变）
        Villager villager = resolve(mc, target);
        if (villager == null) {
            return ActionResult.failed("当前村民实体不存在（entity ID 和 UUID 均未找到）。");
        }
        lookAt(mc, villager.getEyePosition());
        // 打点「我方刚开菜单」：界面创建时据此区分是我方开的（静默）还是玩家手动开的（静默 + 提示）
        SilentContainer.markOwnContainerOpen();
        mc.gameMode.interact(mc.player, villager, new EntityHitResult(villager), InteractionHand.MAIN_HAND);
        ownedByPlugin = true;
        return ActionResult.waiting();
    }

    @Override
    public boolean isTradeScreenReady() {
        return MerchantTradeAccess.isReady();
    }

    @Override
    public Optional<TradeOfferSnapshot> readFirstEnchantedBookTrade() {
        return scanTrades().stream().findFirst();
    }

    @Override
    public List<TradeOfferSnapshot> scanTrades() {
        MerchantMenu handler = MerchantTradeAccess.merchantMenu();
        if (handler == null) {
            return List.of();
        }
        List<TradeOfferSnapshot> snapshots = new ArrayList<>();
        for (int index = 0; index < handler.getOffers().size(); index++) {
            MerchantOffer offer = handler.getOffers().get(index);
            snapshot(index, offer, handler.containerId).ifPresent(snapshots::add);
        }
        return List.copyOf(snapshots);
    }

    @Override
    public ActionResult select(TradeOfferSnapshot offer) {
        Minecraft mc = Minecraft.getInstance();
        MerchantMenu handler = MerchantTradeAccess.merchantMenu();
        if (handler == null || mc.getConnection() == null) return ActionResult.retry("交易界面尚未同步。");
        if (!sameOffer(handler, offer)) return ActionResult.failed("交易报价身份已变化。");
        MerchantTradeAccess.selectTrade(offer.tradeIndex());
        selectedIndex = offer.tradeIndex();
        return ActionResult.success();
    }

    @Override
    public boolean isSelectedTradeSynchronized(TradeOfferSnapshot offer) {
        MerchantMenu handler = MerchantTradeAccess.merchantMenu();
        return handler != null && selectedIndex == offer.tradeIndex() && sameOffer(handler, offer)
            && !handler.getSlot(RESULT_SLOT_INDEX).getItem().isEmpty();
    }

    @Override
    public ActionResult takeOutput() {
        Minecraft mc = Minecraft.getInstance();
        MerchantMenu handler = MerchantTradeAccess.merchantMenu();
        if (mc.player == null || mc.gameMode == null || handler == null) return ActionResult.retry("交易界面不可用。");
        if (handler.getSlot(RESULT_SLOT_INDEX).getItem().isEmpty()) return ActionResult.waiting();
        MerchantTradeAccess.takeResult(handler);
        return ActionResult.success();
    }

    @Override
    public void close() {
        Minecraft mc = Minecraft.getInstance();
        selectedIndex = -1;
        if (ownedByPlugin && mc.player != null && isTradeScreenReady()) MerchantTradeAccess.close();
        ownedByPlugin = false;
    }

    /**
     * 解析目标村民实体：实体 ID 快路径 → UUID 兜底扫描。
     *
     * <p>与搜索 / 工位两层同一套解析口径（第 169 条）：区块重载后实体 ID 会变，
     * 只认 UUID 才算命中。</p>
     */
    private Villager resolve(Minecraft mc, VillagerTarget target) {
        if (mc.level.getEntity(target.entityId()) instanceof Villager v
                && target.uuid().equals(v.getUUID())) {
            return v;
        }
        if (mc.player == null) return null;
        AABB box = mc.player.getBoundingBox().inflate(UUID_FALLBACK_RADIUS);
        return mc.level.getEntitiesOfClass(Villager.class, box,
                e -> target.uuid().equals(e.getUUID()))
            .stream().findFirst().orElse(null);
    }

    /**
     * 把一条报价转成快照。
     *
     * <p>只取输出物上的<b>第一个</b>附魔词条（旧项目在词条循环内直接 return）：
     * 多附魔书在本模块的语义就是「按第一条判」，迁移保持该行为，不做"更完整"的实现。</p>
     */
    private Optional<TradeOfferSnapshot> snapshot(int index, MerchantOffer offer, int syncId) {
        ItemStack output = offer.getResult();
        if (!output.is(Items.ENCHANTED_BOOK)) return Optional.empty();
        ItemEnchantments enchantments = output.get(DataComponents.STORED_ENCHANTMENTS);
        if (enchantments == null) return Optional.empty();
        for (var entry : enchantments.entrySet()) {
            Holder<Enchantment> holder = entry.getKey();
            String id = holder.unwrapKey().map(key -> key.identifier().toString()).orElse(null);
            if (id == null) continue;
            int level = entry.getIntValue();
            int maxLevel = holder.value().getMaxLevel();
            ItemStack first = offer.getBaseCostA();
            ItemStack second = offer.getCostB();
            return Optional.of(new TradeOfferSnapshot(
                index,
                Integer.toString(syncId),
                "minecraft:enchanted_book",
                output.getCount(),
                id,
                level,
                maxLevel,
                first.getCount(),
                second.isEmpty() ? null : BuiltInRegistries.ITEM.getKey(second.getItem()).toString(),
                second.isEmpty() ? 0 : second.getCount(),
                !offer.isOutOfStock(),
                false
            ));
        }
        return Optional.empty();
    }

    /** 报价身份比对：容器同步标识 + 重新快照全等（防止界面在自己的 tick 之间被服务端刷新） */
    private boolean sameOffer(MerchantMenu handler, TradeOfferSnapshot snapshot) {
        if (!Integer.toString(handler.containerId).equals(snapshot.synchronizationId())) return false;
        if (snapshot.tradeIndex() < 0 || snapshot.tradeIndex() >= handler.getOffers().size()) return false;
        return snapshot(snapshot.tradeIndex(), handler.getOffers().get(snapshot.tradeIndex()), handler.containerId)
            .map(snapshot::equals)
            .orElse(false);
    }

    /** 强制玩家视角朝向目标坐标，并同步给服务端（开界面要求正对村民） */
    private void lookAt(Minecraft mc, Vec3 target) {
        if (mc.player == null || mc.getConnection() == null) return;
        Vec3 eye = mc.player.getEyePosition();
        double dx = target.x - eye.x;
        double dy = target.y - eye.y;
        double dz = target.z - eye.z;
        double hDist = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float)(Math.toDegrees(Math.atan2(dz, dx)) - 90.0);
        float pitch = (float)(-Math.toDegrees(Math.atan2(dy, hDist)));
        mc.player.setYRot(yaw);
        mc.player.setXRot(pitch);
        mc.getConnection().send(
            new ServerboundMovePlayerPacket.Rot(
                yaw, pitch, mc.player.onGround(), mc.player.horizontalCollision
            )
        );
    }
}
