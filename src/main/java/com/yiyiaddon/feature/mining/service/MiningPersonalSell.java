package com.yiyiaddon.feature.mining.service;

import com.yiyiaddon.core.net.ClientPacketSender;
import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.platform.container.ContainerAccess;
import com.yiyiaddon.platform.container.SilentContainer;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.phys.EntityHitResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
 * 悬停名剥掉颜色码与空格后做包含比对，名字全菜单都没有时再比一层附魔说明行（{@code lore}，
 * 见 {@link #findKeywordSlot}）。</p>
 *
 * <p><b>物品判据</b>：卖的是 {@code AutoMinerModule#sellItemFilter()} —— <b>目标三选一在当前采集模式下的
 * 产物</b>（用户 2026-09-21：「同步选择器 我选什么就显示出售什么」），所以默认状态下与正在挖的矿天然
 * 同源，不会卖错。匹配层同时认 ID 与显示名：服务器把商品改名（{@code §a钻石} 这类）时按名字包含比对
 * 仍能命中，改名前后的菜单都能点。</p>
 */
public final class MiningPersonalSell {

    /** 与状态机同一个日志名：出售链的所有行都落在 {@code latest.log} 的 {@code [卖矿流程]} 里 */
    private static final Logger MINING_LOG = LoggerFactory.getLogger("yiyiaddon/mining");

    private final AutoMinerModule module;
    private final Minecraft mc = Minecraft.getInstance();

    /** 「按名字找不到就按坐标就近选」的兜底半径（格）：配置坐标是玩家站在 NPC 旁边采的 */
    private static final double NPC_NEAR_RANGE = 3.0;

    /** 是否处于「我方菜单流程」中：从发流程指令起、到整条出售链结束（含中途停机）为止 */
    private boolean menuFlowActive;

    /** 本轮出售是否已播报过「名字没用上，改按坐标就近选」（每轮 beginFlow 重置，避免刷屏） */
    private boolean npcFallbackAnnounced;

    public MiningPersonalSell(AutoMinerModule module) {
        this.module = module;
    }

    // ── 流程标志（静默门控 + 停机清理） ──

    /** 进入出售流程：此后服务端推来的容器界面一律静默（不弹、不抢鼠标） */
    public void beginFlow() {
        menuFlowActive = true;
        npcFallbackAnnounced = false;
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

    /**
     * 菜单里那件商品是否已经带上了价格说明行（= 商店的商品列表刷出来了）。
     *
     * <p>本服商店的菜单是<b>先开空表格、隔一两刻再异步刷出商品列表</b>：列表没刷出来之前，
     * 唯一命中目标名的只有第 6 行 81..89 那排热键栏<b>快捷镜像格</b>，点它只算「选中」、
     * 不开选量菜单 —— 那一屏找不到「全部」，白等一个单步超时。用它当「列表已就绪」的判据
     * （列表格必带 {@code 上轮单价 / 仓库与背包可出售} 一类的说明行，镜像格没有）。</p>
     */
    public boolean hasListedTarget() {
        AbstractContainerMenu menu = menu();
        if (menu == null) return false;
        for (Slot slot : menu.slots) {
            ItemStack stack = slot.getItem();
            if (matchesMenuTarget(stack) && !loreText(stack).isEmpty()) return true;
        }
        return false;
    }

    /**
     * 菜单里那个「出售物品」的槽位（挑最准的一格）。
     *
     * <p><b>带附魔说明行（lore）的列表格优先</b>：实测本服 90 格收购菜单里，商品列表在 0..44，每格带
     * {@code 上轮单价 / 仓库与背包可出售 / 点击选择出售数量} 的 lore；第 6 行 81..89 是玩家背包的
     * <b>快捷镜像</b>，只有名字、没有 lore。点镜像格只算「选中」，不开选量菜单 ——
     * 用户 2026-09-22「半天才出售成功」就是列表还没刷出来时先点中了镜像格，白等一个单步超时，
     * 重试才点中列表格。</p>
     *
     * <p><b>但光有 lore 还不够</b>：按名字包含匹配时「圆石」会先撞上同屏的「深板岩圆石」
     * （2026-09-22 20:25 日志实锤点中的是 {@code #18 深板岩圆石 可出售:0个}），所以按
     * 「登记 ID 完全相等 &gt; 悬停名完全相等 &gt; 带 lore 的列表格」打分取最高分那一格。</p>
     */
    private Slot findTargetSlot() {
        AbstractContainerMenu menu = menu();
        if (menu == null) return null;
        String filter = module.sellItemFilter();
        String wanted = stripFormatting(module.getSellItemDisplayName());
        Slot best = null;
        int bestScore = 0;
        for (Slot slot : menu.slots) {
            if (isPlayerSlot(slot)) continue;
            ItemStack stack = slot.getItem();
            if (!matchesMenuTarget(stack)) continue;
            // 打分挑「最准的那一格」而不是「第一格」：按名字包含匹配时「圆石」会先撞上「深板岩圆石」，
            // 用户 2026-09-22 实测点中的就是 #18 深板岩圆石（可出售:0个，那一屏自然开不出选量菜单），
            // 真目标 #19 圆石（可出售:64个）被跳过。登记 ID 完全相等 4 分、悬停名完全相等 2 分、
            // 带 lore 的列表格 1 分（第 6 行是背包的快捷镜像格，点它只算选中、不开选量菜单）。
            int score = isTargetId(stack, filter) ? 4 : 0;
            String name = stripFormatting(stack.getHoverName().getString());
            if (!wanted.isEmpty() && name.equals(wanted)) score += 2;
            if (!loreText(stack).isEmpty()) score += 1;
            if (score > bestScore) {
                bestScore = score;
                best = slot;
            }
        }
        return best;
    }

    /**
     * 找「悬停名或附魔说明行包含关键词」的槽位。
     *
     * <p><b>两轮扫描</b>：先全菜单比悬停名，都没命中再比附魔说明行（{@code lore}）。顺序不能反 ——
     * 商店把商品自己的说明写进 lore（实测本服：{@code 点击选择出售数量} 就是一条 lore），
     * 混在一轮里扫会让商品格抢在「全部 / 确认出售」按钮前面被点到。</p>
     */
    private Slot findKeywordSlot(String keyword) {
        AbstractContainerMenu menu = menu();
        String wanted = stripFormatting(keyword);
        if (menu == null || wanted.isEmpty()) return null;
        Slot loreHit = null;
        for (Slot slot : menu.slots) {
            if (isPlayerSlot(slot)) continue;
            ItemStack stack = slot.getItem();
            if (stack.isEmpty()) continue;
            if (stripFormatting(stack.getHoverName().getString()).contains(wanted)) return slot;
            if (loreHit == null && loreText(stack).contains(wanted)) loreHit = slot;
        }
        return loreHit;
    }

    /**
     * 「关键词格」的指纹（登记 ID + 悬停名 + 附魔说明行）；没这一格给空串。
     *
     * <p>用途是判「插件已经把已选数量落进会话」：本服选量菜单里「确认出售」那一格在选量生效前是
     * {@code gray_dye} + lore「请先选择至少1个物品」，点完「全部」之后才变成 {@code emerald_block}
     * + 「数量:64个 …」（实测日志 2026-09-22 20:41:51）。点击前后各取一次指纹一比，变了就说明可以
     * 立刻点确认 —— 比盲等固定时长快，而且换别的服（不写 lore）也只是退化成原来的固定等待。</p>
     */
    public String keywordSlotFingerprint(String keyword) {
        Slot slot = findKeywordSlot(keyword);
        if (slot == null) return "";
        ItemStack stack = slot.getItem();
        return BuiltInRegistries.ITEM.getKey(stack.getItem()).toString() + '|'
            + stripFormatting(stack.getHoverName().getString()) + '|' + loreText(stack);
    }

    /**
     * 这一格是不是玩家自己背包里的格子（收购菜单 = 54 格容器 + 36 格背包，实测共 90 槽）。
     *
     * <p><b>背包那 36 格一律不点</b>：点自己的格子等于「把东西抓到手心」—— 客户端预测会立刻清空那一格
     * （背包读数因此瞬间变 0），服务端却只当成一次没头没尾的拾取。用户 2026-09-22 实测的
     * 「根本没卖出去 一直说我出售成功」就是这么来的：日志 {@code 点击槽位 #56（容器 #3 状态号 13）：圆石}，
     * 槽 56 正是自己背包，点完背包读数变 0 → 状态机当场判「卖完了」→ 报出售完成，实际一颗没卖。</p>
     */
    private boolean isPlayerSlot(Slot slot) {
        return mc.player != null && slot.container == mc.player.getInventory();
    }

    /** 附魔说明行（{@code lore}）剥掉颜色码与空白后拼成一串；没有 lore 返回空串 */
    private static String loreText(ItemStack stack) {
        ItemLore lore = stack.get(DataComponents.LORE);
        if (lore == null) return "";
        StringBuilder out = new StringBuilder();
        for (Component line : lore.lines()) {
            String text = stripFormatting(line.getString());
            if (text.isEmpty()) continue;
            if (out.length() > 0) out.append(' ');
            out.append(text);
        }
        return out.toString();
    }

    /**
     * 诊断文本：把当前菜单逐槽摊开（槽号 / 登记 ID / 悬停名 / 附魔说明行）。
     *
     * <p>用户 2026-09-22：「就叫钻石啊我选的也没错」—— 那句「收购菜单里没找到」是流程自己猜的，
     * 真正卡住的是后面某一步点不到按钮。这行日志把「菜单里到底有哪些格、每格叫什么」原样写进
     * {@code latest.log}，照着它把「出售数量 / 确认出售关键词」填对即可，不用再靠猜。</p>
     */
    public String menuDump() {
        AbstractContainerMenu menu = menu();
        if (menu == null) return "（当前没开菜单）";
        StringBuilder out = new StringBuilder("菜单#").append(menu.containerId)
            .append(" 状态号").append(menu.getStateId())
            .append(" 共").append(menu.slots.size()).append("槽");
        for (Slot slot : menu.slots) {
            ItemStack stack = slot.getItem();
            if (stack.isEmpty()) continue;
            out.append(" | ").append(slot.index).append(':')
                .append(BuiltInRegistries.ITEM.getKey(stack.getItem()))
                .append(" '").append(stripFormatting(stack.getHoverName().getString())).append('\'');
            String lore = loreText(stack);
            if (!lore.isEmpty()) out.append(" [").append(lore).append(']');
        }
        return out.toString();
    }

    /** 发包点一次槽位（原版语义：左键拾取），并把「点了哪一格、那一格是什么」写进日志 */
    private void click(int slotIndex) {
        AbstractContainerMenu menu = menu();
        if (menu == null || mc.gameMode == null || mc.player == null) return;
        MINING_LOG.info("[卖矿流程] 点击槽位 #{}（容器 #{} 状态号 {}）：{}", slotIndex, menu.containerId,
            menu.getStateId(), clickTargetName(menu, slotIndex));
        mc.gameMode.handleContainerInput(menu.containerId, slotIndex, 0, ContainerInput.PICKUP, mc.player);
    }

    /** 槽位在日志里的名字（越界或空格子给 {@code 空}） */
    private static String clickTargetName(AbstractContainerMenu menu, int slotIndex) {
        if (slotIndex < 0 || slotIndex >= menu.slots.size()) return "空";
        ItemStack stack = menu.slots.get(slotIndex).getItem();
        return stack.isEmpty() ? "空" : stripFormatting(stack.getHoverName().getString());
    }

    // ── 背包 ──

    /** 主背包（0~35）里命中「出售物品」的物品总数量（只认 ID，见 {@link #matchesBagTarget}） */
    public int countTargetInBag() {
        int total = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.getInventory().getItem(i);
            if (matchesBagTarget(stack)) total += stack.getCount();
        }
        // 手心里那一摞也要算：点自己背包的格子会把它挪到光标上（那格立刻变空、读数变 0），
        // 只看格子的读数会被当成「一颗不剩、卖完了」（用户 2026-09-22「根本没卖出去 一直说我出售成功」）
        if (mc.player.containerMenu != null) {
            ItemStack carried = mc.player.containerMenu.getCarried();
            if (matchesBagTarget(carried)) total += carried.getCount();
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
     * 在配置坐标附近找出收购 NPC。
     *
     * <p><b>三层判据，逐层放宽</b>（用户 2026-09-22 实机复现后按日志定案）：</p>
     * <ol>
     *     <li><b>名字命中玩家实体</b>：填的是实体真名（如 {@code CIT-a011feed8824}）时直接命中；</li>
     *     <li><b>名字命中全息</b>：头顶那行「黑市商人[收购]」若是 {@code text_display} 全息，就以它为中心
     *         取最近的假玩家 NPC —— 全息就浮在 NPC 头顶，这一步能把「按看得见的字填」也接上；</li>
     *     <li><b>只按坐标就近</b>：名字压根不是实体名时，取配置坐标最近的假玩家 NPC。</li>
     * </ol>
     *
     * <p>为什么必须兜底到坐标：插件 NPC 的真名是随机串（本服实测 {@code CIT-a011feed8824}），玩家不可能猜到，
     * 而配置坐标是他站在 NPC 旁边采的（实测距 NPC 0.0 格），所以「就近」比「猜名字」可靠得多。第 3 层是
     * <b>正常路径</b>，只在日志里写一行记下用了哪个锚点，不往聊天里刷提示（用户 2026-09-22 要求去掉）。</p>
     *
     * <p><b>只认假玩家</b>：候选必须是玩家实体、且不在 Tab 玩家名单里（插件 NPC 不下发列表项），
     * 这样就近兜底不会右键到路人身上；传送神兽那种 {@code slime} 也不会被选中。</p>
     *
     * @return 命中的实体；三层都没命中则 {@code null}（调用方按坐标重试）
     */
    public Entity findNpc() {
        if (mc.level == null || mc.player == null) return null;
        String wanted = stripFormatting(module.settings().personalSellNpcName);
        BlockPos center = npcPos();

        Entity named = null;
        double namedDist = Double.MAX_VALUE;
        Entity hologram = null;
        double hologramDist = Double.MAX_VALUE;
        List<Entity> nearby = new ArrayList<>();
        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity == mc.player) continue;
            nearby.add(entity);
            if (wanted.isEmpty()) continue;
            double dist = entity.blockPosition().distSqr(center);
            if (isNpcActor(entity)) {
                if (matchesNpcName(entity, wanted) && dist < namedDist) {
                    namedDist = dist;
                    named = entity;
                }
            } else {
                String text = hologramText(entity);
                if (text != null && text.contains(wanted) && dist < hologramDist) {
                    hologramDist = dist;
                    hologram = entity;
                }
            }
        }
        if (named != null) return named;

        BlockPos anchor = hologram != null ? hologram.blockPosition() : center;
        Entity best = null;
        double bestDist = Double.MAX_VALUE;
        for (Entity entity : nearby) {
            if (!isNpcActor(entity)) continue;
            double dist = entity.blockPosition().distSqr(anchor);
            if (dist < bestDist) {
                bestDist = dist;
                best = entity;
            }
        }
        if (best == null || bestDist > NPC_NEAR_RANGE * NPC_NEAR_RANGE) return null;
        if (!npcFallbackAnnounced) {
            npcFallbackAnnounced = true;
            // 只写日志不刷聊天：插件 NPC 的真名是随机串（本服 CIT-a011feed8824），玩家填的必然是头顶那行
            // 「黑市商人[收购]」，走坐标就近是正常路径而不是异常（用户 2026-09-22 要求去掉这条提示）
            MINING_LOG.info("[卖矿流程] 「{}」不是实体名，已就近选中收购 NPC {}（锚点：{}）",
                module.settings().personalSellNpcName, best.getName().getString(),
                hologram != null ? "头顶全息 " + hologram.blockPosition() : "配置坐标 " + center);
        }
        return best;
    }

    /**
     * 这个实体像不像「插件 NPC」：是玩家实体、但不在 Tab 玩家名单里。
     *
     * <p>假玩家 NPC 的实体类型就是 {@code minecraft:player}，服务端不会给它下发玩家列表项，
     * 所以「在 Tab 名单里」的才是真人；据此把真人排除，就近兜底时才不会右键到路人身上。
     * 名单尚未同步时（刚落地）会短暂把真人当成 NPC —— 代价只是多一次右键，无害。</p>
     */
    private boolean isNpcActor(Entity entity) {
        if (!(entity instanceof Player player)) return false;
        return mc.getConnection() == null || mc.getConnection().getPlayerInfo(player.getUUID()) == null;
    }

    /**
     * 全息展示实体的文本（{@code minecraft:text_display} 那类）；不是展示实体返回 {@code null}。
     *
     * <p>关键：这类实体的文本<b>不经过</b> {@code getName()} / {@code getDisplayName()}（实测两个都是空串），
     * 只能从 {@code Display.TextDisplay#getText()} 取 —— 否则「头顶明明写着黑市商人却扫不到」。</p>
     */
    private static String hologramText(Entity entity) {
        if (entity instanceof Display.TextDisplay text) return stripFormatting(text.getText().getString());
        return null;
    }

    /**
     * 名字是否对得上（用户 2026-09-21：「填名字跟 id 都能识别」）。
     *
     * <p>三个来源都要试，因为插件 NPC 的名字可能挂在任意一层：实体名（假玩家 NPC 就是它的档案名）、
     * 自定义显示名，以及<b>记分板队伍的前后缀</b>。用户 2026-09-22 复现：头顶明明写着「黑市商人」，
     * 按它匹配却扫不到实体 —— 那行字正是队伍装饰渲染出来的，既不进实体名、也不进显示名。</p>
     */
    private boolean matchesNpcName(Entity entity, String wanted) {
        return stripFormatting(entity.getName().getString()).contains(wanted)
            || stripFormatting(entity.getDisplayName().getString()).contains(wanted)
            || decoratedName(entity).contains(wanted);
    }

    /** 实体「眼睛看到的名字」：队伍前缀 + 显示名 + 队伍后缀（拿不到队伍时退化成显示名） */
    public String decoratedName(Entity entity) {
        String base = entity.getDisplayName().getString();
        if (mc.level == null) return stripFormatting(base);
        var team = mc.level.getScoreboard().getPlayersTeam(entity.getScoreboardName());
        if (team == null) return stripFormatting(base);
        return stripFormatting(team.getPlayerPrefix().getString() + base + team.getPlayerSuffix().getString());
    }

    /**
     * 诊断文本：列出配置坐标附近实体的真实名字（找不到收购 NPC 时写进 {@code latest.log}）。
     *
     * <p>用户 2026-09-22 复现「出售中」卡住：聊天栏每 5 秒报一次「附近找不到『黑市商人』」，可头顶
     * 明明写着这四个字。这行日志把附近实体的名字逐层摊开（类型 / name / displayName / 队伍装饰后的
     * 名字 / 距离），照着它把「收购 NPC 名字」填对即可；若连一个实体都没有，说明 NPC 不在客户端的
     * 追踪范围内（配置坐标填错，或人还没真正到 NPC 附近）。</p>
     *
     * @return 一行文本；附近没有可列实体时给出原因
     */
    public String nearbyEntityDump() {
        if (mc.level == null || mc.player == null) return "（世界未加载）";
        BlockPos center = npcPos();
        List<Entity> near = new ArrayList<>();
        for (Entity entity : mc.level.entitiesForRendering()) {
            if (entity == mc.player) continue;
            if (entity.blockPosition().distSqr(center) <= 32 * 32) near.add(entity);
        }
        if (near.isEmpty()) return "（32 格内没有任何实体：NPC 不在客户端追踪范围内，或坐标填错）";
        near.sort(Comparator.comparingDouble(entity -> entity.blockPosition().distSqr(center)));
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Math.min(8, near.size()); i++) {
            Entity entity = near.get(i);
            if (i > 0) sb.append(" | ");
            sb.append(String.format("%s name=%s display=%s 队伍装饰=%s 距%.1f",
                entity.getType().toString(),
                entity.getName().getString(),
                entity.getDisplayName().getString(),
                decoratedName(entity),
                Math.sqrt(entity.blockPosition().distSqr(center))));
        }
        if (near.size() > 8) sb.append(" …共 ").append(near.size()).append(" 个");
        return sb.toString();
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

    /**
     * 把视角转向实体眼睛位置，<b>并且当场把朝向包发出去</b>。
     *
     * <p>为什么必须显式补一个朝向包：原版把 {@code ServerboundMovePlayerPacket.Rot} 放在
     * {@code LocalPlayer#tick} 的末尾（{@code sendPosition()}）才发，而交互包是本刻立即发出的 ——
     * 服务端先收到交互包、后收到朝向包，处理交互时手上还是<b>上一刻的旧朝向</b>。商店插件按旧朝向
     * 判定「人没看着 NPC」就把这次点击丢掉，于是表现为<b>人已经站在 NPC 旁边了还必须自己用鼠标
     * 盯着 NPC 才给开菜单</b>（用户 2026-09-22「到 npc 旁边还要看着他」）。先把朝向包写进连接再发
     * 交互包，同一条 TCP 上顺序天然成立，服务端处理交互时朝向已经是对的。</p>
     */
    private void faceEntity(Entity entity) {
        if (mc.player == null) return;
        var eye = mc.player.getEyePosition();
        var target = entity.getEyePosition();
        double dx = target.x - eye.x;
        double dy = target.y - eye.y;
        double dz = target.z - eye.z;
        double horiz = Math.sqrt(dx * dx + dz * dz);
        float yaw = (float) Math.toDegrees(Math.atan2(-dx, dz));
        float pitch = (float) Math.max(-90, Math.min(90, Math.toDegrees(-Math.atan2(dy, horiz))));
        mc.player.setYRot(yaw);
        mc.player.setXRot(pitch);
        ClientPacketSender.sendMoveRotation(yaw, pitch, mc.player.onGround(), mc.player.horizontalCollision);
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
