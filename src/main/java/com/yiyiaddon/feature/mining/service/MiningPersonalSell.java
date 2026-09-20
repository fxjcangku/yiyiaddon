package com.yiyiaddon.feature.mining.service;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.platform.container.ContainerAccess;
import com.yiyiaddon.platform.container.SilentContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;

/**
 * 自用模式出售流程的底层动作（用户 2026-09-20 需求）。
 *
 * <p>只做「读菜单 / 点槽位 / 找 NPC / 数背包」这类具体动作，<b>不含任何流程阶段逻辑</b>——
 * 阶段推进与超时重试都在 {@code MiningStateMachine} 的四个 SELL_* 态里（与卸货 / 补给同构）。
 * 分开的理由是这两个关注点的变化频率完全不同：阶段逻辑跟着服务器菜单改，动作层跟着游戏 API 改。</p>
 *
 * <p><b>全程静默</b>：所有菜单都只读 {@code player.containerMenu} 的槽位内容、发包点击，
 * 界面一个都不弹（服务端推来的换菜单界面由 {@link #isMenuFlowActive()} 交给
 * {@code AutoMinerModule#onOpenScreen} 压掉，否则会弹出来抢鼠标）。槽位匹配沿用 RTP 选单那套口径：
 * 悬停名剥掉颜色码与空格后做包含比对（{@code ServerCommandRunner#handleGuiAutoClick}）。</p>
 *
 * <p><b>物品判据</b>：卖的是 {@code AutoMinerModule#sellItemFilter()} —— <b>目标三选一在当前采集模式下的
 * 产物</b>（用户 2026-09-21：「同步选择器 我选什么就显示出售什么」），所以默认状态下与正在挖的矿天然
 * 同源，不会卖错。匹配层同时认 ID 与显示名：服务器把商品改名（{@code §a钻石} 这类）时按名字包含比对
 * 仍能命中，改名前后的菜单都能点。</p>
 */
public final class MiningPersonalSell {

    private final AutoMinerModule module;
    private final Minecraft mc = Minecraft.getInstance();

    /** 是否处于「我方菜单流程」中：从发流程指令起、到整条出售链结束（含中途停机）为止 */
    private boolean menuFlowActive;

    public MiningPersonalSell(AutoMinerModule module) {
        this.module = module;
    }

    // ── 流程标志（静默门控 + 停机清理） ──

    /** 进入出售流程：此后服务端推来的容器界面一律静默（不弹、不抢鼠标） */
    public void beginFlow() {
        menuFlowActive = true;
    }

    /** 结束出售流程（离开整条链 / 停机时调用）：之后的容器界面交给既有门控判 */
    public void endFlow() {
        menuFlowActive = false;
    }

    /** 是否正在跑出售流程的菜单（给 {@code AutoMinerModule#onOpenScreen} 判「这个容器该不该压掉」） */
    public boolean isMenuFlowActive() {
        return menuFlowActive;
    }

    /** 全清（模块停机 / 新流程开始前调用） */
    public void reset() {
        endFlow();
    }

    // ── 菜单 ──

    /**
     * 当前打开的菜单；玩家自身背包（{@code containerId == 0}）与「没开菜单」都返回 {@code null}。
     *
     * <p>静默模式下没有 Screen，只能读 {@code player.containerMenu}（原版在 setScreen 之前
     * 就已把菜单赋好值，见 {@code AutoMinerModule#onOpenScreen} 的 RTP 选单注释）。</p>
     */
    public AbstractContainerMenu menu() {
        return ContainerAccess.openMenu();
    }

    /** 当前是否有一个可读的菜单（流程各阶段用它判「服务端推的界面到了没有 / 还在不在」） */
    public boolean hasMenu() {
        return menu() != null;
    }

    /** 菜单里是否存在「悬停名包含关键词」的非空槽位（判当前停在哪一级菜单） */
    public boolean menuHasKeyword(String keyword) {
        return findKeywordSlot(keyword) != null;
    }

    /**
     * 点菜单里第一个「悬停名包含关键词」的槽位（左键拾取，与原版点一下同语义）。
     *
     * @return true = 找到并已发包点击；false = 菜单没开 / 没有这个槽（调用方据此走超时重试）
     */
    public boolean clickKeyword(String keyword) {
        Slot slot = findKeywordSlot(keyword);
        if (slot == null) return false;
        click(slot.index);
        return true;
    }

    // ── 出售目标（跟随目标三选一在当前采集模式下的产物） ──

    /**
     * 菜单点选用的判据：登记 ID 精确命中，或<b>显示名包含</b>。
     *
     * <p>名字那条是给服务端改名留的后路（用户 2026-09-20：「填名字跟id都能识别」）：商品被改成
     * 「{@code §b高级钻石}」时 ID 不再是原物品，但剥掉颜色码后的名字里仍含着「钻石」。</p>
     */
    private boolean matchesMenuTarget(ItemStack stack) {
        String filter = module.sellItemFilter();
        if (filter == null || filter.isBlank() || stack.isEmpty()) return false;
        if (isTargetId(stack, filter)) return true;
        String wanted = stripFormatting(module.getSellItemDisplayName());
        return !wanted.isEmpty() && stripFormatting(stack.getHoverName().getString()).contains(wanted);
    }

    /**
     * 背包计数用的判据：<b>只认 ID</b>。
     *
     * <p>刻意不走名字包含：「钻石」是「钻石块」的子串，按名字数会把钻石块一起算进去 ——
     * 触发组数会提前达标，卖完真钻石后结算又清不了零，整条链在原地空转。</p>
     */
    private boolean matchesBagTarget(ItemStack stack) {
        String filter = module.sellItemFilter();
        return stack != null && !stack.isEmpty() && filter != null && !filter.isBlank()
            && isTargetId(stack, filter);
    }

    /** 这件物品的登记 ID 是否等于目标 ID（目标 ID 解析不出物品时恒为 {@code false}） */
    private static boolean isTargetId(ItemStack stack, String filter) {
        Identifier id = Identifier.tryParse(filter.trim());
        return id != null && id.equals(BuiltInRegistries.ITEM.getKey(stack.getItem()));
    }

    /** 点菜单里第一个命中「出售物品」的槽位（收购列表里那件商品的那一格） */
    public boolean clickTarget() {
        Slot slot = findTargetSlot();
        if (slot == null) return false;
        click(slot.index);
        return true;
    }

    /** 菜单里是否还有「出售物品」的槽位（判断当前是不是还停在商品列表） */
    public boolean menuHasTarget() {
        return findTargetSlot() != null;
    }

    private Slot findTargetSlot() {
        AbstractContainerMenu menu = menu();
        if (menu == null) return null;
        for (Slot slot : menu.slots) {
            if (matchesMenuTarget(slot.getItem())) return slot;
        }
        return null;
    }

    private Slot findKeywordSlot(String keyword) {
        AbstractContainerMenu menu = menu();
        String wanted = stripFormatting(keyword);
        if (menu == null || wanted.isEmpty()) return null;
        for (Slot slot : menu.slots) {
            ItemStack stack = slot.getItem();
            if (stack.isEmpty()) continue;
            if (stripFormatting(stack.getHoverName().getString()).contains(wanted)) return slot;
        }
        return null;
    }

    /** 发包点一次槽位（原版语义：左键拾取）。菜单 id 取自实况，静默模式下同样有效 */
    private void click(int slotIndex) {
        AbstractContainerMenu menu = menu();
        if (menu == null || mc.gameMode == null || mc.player == null) return;
        mc.gameMode.handleContainerInput(menu.containerId, slotIndex, 0, ContainerInput.PICKUP, mc.player);
    }

    // ── 背包 ──

    /** 主背包（0~35）里命中「出售物品」的物品总数量（只认 ID，见 {@link #matchesBagTarget}） */
    public int countTargetInBag() {
        int total = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (matchesBagTarget(stack)) total += stack.getCount();
        }
        return total;
    }

    /**
     * 主背包 36 格是否已满（用户 2026-09-20：「背包满了直接触发出售」）。
     *
     * <p>按「一格空位都没有」判：还有空位就还能捡矿石/掉落物，不算满。垃圾自动丢弃已经在跑，
     * 能走到这里说明丢弃也腾不出位置了（保留白名单与矿物把背包占满了）。</p>
     */
    public boolean isInventoryFull() {
        for (int i = 0; i < 36; i++) {
            if (mc.player.getInventory().getItem(i).isEmpty()) return false;
        }
        return true;
    }

    // ── 收购 NPC ──

    /** 配置里的收购 NPC 坐标（固定死的那一个） */
    public BlockPos npcPos() {
        return new BlockPos(module.settings().personalSellNpcX,
            module.settings().personalSellNpcY,
            module.settings().personalSellNpcZ);
    }

    /**
     * 出售时该站的那一格：<b>NPC 正面朝向前方一格</b>（用户 2026-09-20：「寻路到npc前面一格再发包出售」）。
     *
     * <p>为什么不站 NPC 脚下那一格：目标格被 NPC 实体的碰撞箱占着，Baritone 到了附近会被实体挤开、
     * 表现为在那一格旁边来回蹭；而且贴到零距离右键，命中射线容易打到 NPC 身后的方块或落空。
     * 站到正面一格既好走位，又稳稳落在原版交互距离内。</p>
     *
     * <p>朝向取实体自身的 {@code getYRot()}（假玩家 NPC 同样有朝向），按 Minecraft 的偏航角定义换算成
     * 水平方向：{@code x = -sin(yaw)}、{@code z = cos(yaw)}，四舍五入到整格（斜前方就是斜对角一格）。
     * 这里自己算而不调 {@code Vec3.directionFromRotation}：两条版本线的官方 API 坐标名不一致。</p>
     *
     * <p>附近扫不到 NPC（名字关键词没配对上）时退回配置坐标：那种情况本来也找不到交互对象，
     * 站在坐标上等重试即可。</p>
     */
    public BlockPos npcStandPos() {
        Entity npc = findNpc();
        if (npc == null) return npcPos();
        double yaw = Math.toRadians(npc.getYRot());
        int dx = (int) Math.round(-Math.sin(yaw));
        int dz = (int) Math.round(Math.cos(yaw));
        return npc.blockPosition().offset(dx, 0, dz);
    }

    /**
     * 在配置坐标附近找一个「显示名包含关键词」的实体（收购 NPC）。
     *
     * <p>NPC 是插件用假玩家实体伪装的（{@code minecraft:player}），所以<b>不能按实体类型找</b>：
     * 只能扫实体列表按名字匹配。<b>不能排除「玩家类型实体」</b>——伪装成玩家的 NPC 在客户端
     * 就是一个玩家实体，按类型排除会把 NPC 一起排掉；只排除自己（否则可能把自己当 NPC 去交互）。</p>
     *
     * @return 命中的实体；附近没有则 {@code null}（调用方退回「按坐标寻路」）
     */
    public Entity findNpc() {
        if (mc.level == null || mc.player == null) return null;
        String wanted = stripFormatting(module.settings().personalSellNpcName);
        if (wanted.isEmpty()) return null;

        BlockPos center = npcPos();
        Entity best = null;
        double bestDist = Double.MAX_VALUE;
        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity == mc.player) continue;
            if (stripFormatting(entity.getDisplayName().getString()).contains(wanted)
                || stripFormatting(entity.getName().getString()).contains(wanted)) {
                double dist = entity.blockPosition().distSqr(center);
                if (dist < bestDist) {
                    bestDist = dist;
                    best = entity;
                }
            }
        }
        return best;
    }

    /**
     * 发包与 NPC 交互（右键一次），打开收购菜单。
     *
     * <p>走 {@code gameMode.interact} + {@link SilentContainer#markOwnContainerOpen()}，
     * 与村民交易那条链完全同源：打点是让界面门控知道「这个容器是我方开的」；
     * 对假玩家实体右键，服务端插件拦的正是这个交互包。</p>
     */
    public boolean interactNpc(Entity npc) {
        if (npc == null || mc.player == null || mc.gameMode == null) return false;
        faceEntity(npc);
        SilentContainer.markOwnContainerOpen();
        mc.gameMode.interact(mc.player, npc, new EntityHitResult(npc), InteractionHand.MAIN_HAND);
        return true;
    }

    /** 把视角转向实体眼睛位置（只在交互发包瞬间调用，不持续覆盖玩家视角） */
    private void faceEntity(Entity entity) {
        if (mc.player == null) return;
        var eye = mc.player.getEyePosition();
        var target = entity.getEyePosition();
        double dx = target.x - eye.x;
        double dy = target.y - eye.y;
        double dz = target.z - eye.z;
        double horiz = Math.sqrt(dx * dx + dz * dz);
        mc.player.setYRot((float) Math.toDegrees(Math.atan2(-dx, dz)));
        mc.player.setXRot((float) Math.max(-90, Math.min(90, Math.toDegrees(-Math.atan2(dy, horiz)))));
    }

    // ── 文本 / 取值 ──

    /** 剥掉颜色码（{@code §x}）与全部空白后的小写比对串（槽位名匹配的统一口径） */
    public static String stripFormatting(String text) {
        if (text == null) return "";
        StringBuilder out = new StringBuilder(text.length());
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '§' || c == '\u00a7') {
                i++; // 跳过颜色码本体
                continue;
            }
            if (Character.isWhitespace(c)) continue;
            out.append(c);
        }
        return out.toString();
    }

    /** 单步超时（刻）：设置里是秒，下限 1 秒（服务器再快也得给人一拍反应） */
    public int stepTimeoutTicks() {
        return Math.max(1, module.settings().personalSellStepTimeout) * 20;
    }

    /** 单步重试次数（设置里 0 表示不重试，只要一次机会） */
    public int stepRetries() {
        return Math.max(0, module.settings().personalSellRetries);
    }
}
