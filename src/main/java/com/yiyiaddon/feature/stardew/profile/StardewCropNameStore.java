package com.yiyiaddon.feature.stardew.profile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yiyiaddon.core.event.ClientEventBus;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.feature.stardew.service.StardewInventoryService;
import com.yiyiaddon.platform.container.ContainerAccess;
import com.yiyiaddon.platform.identity.ItemIdentifier;
import com.yiyiaddon.platform.resource.BlockStateModelResolver;
import com.yiyiaddon.platform.resource.ItemModelDispatchIndex;
import com.yiyiaddon.repository.JsonFileStore;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.trading.MerchantOffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 作物中文名「自动学名」存储。
 *
 * <p><b>为什么需要它：</b>部分服务器的资源包只翻译了一部分作物。真机实测（moexd）：
 * 56 种作物里只有 18 种在语言文件里有名字，另外 38 种（白菜 / 榴莲 / 柠檬 / 葡萄 / 香蕉 …）
 * 在任何语言文件里<b>根本不存在</b>，客户端无论怎么查都查不到——但服务器给每个物品下发了
 * 自己的名字（成熟产物「白菜」、种子「白菜种子」、阶段「白菜幼苗 / 白菜生长期 / 白菜舒展期」），
 * 这些名字是真实证据，学出来即可，绝不需要伪造。</p>
 *
 * <p><b>学习来源与可信度排序（高优先级不会被低优先级覆盖）：</b></p>
 * <ol>
 *   <li>成熟产物名（{@code customcrops:<作物>}，如「白菜」）——名字本身就是作物名，最准；</li>
 *   <li>种子名（{@code customcrops:<作物>_seeds}）去掉「种子 / seeds」后缀；</li>
 *   <li>阶段名（{@code customcrops:<作物>_stage_N}）取同一作物多个阶段的<b>公共前缀</b>
 *       （白菜幼苗 / 白菜生长期 / 白菜舒展期 → 白菜）。只有观测到 2 个以上阶段、且公共前缀
 *       严格短于每一个阶段名时才采用——这保证阶段词确实被切掉了，绝不把整串阶段名当作物名。</li>
 * </ol>
 *
 * <p><b>名字必须来自服务器：</b>只接受带 {@code item_name} / {@code custom_name} 组件的物品栈。
 * 没有该组件时 {@code getHoverName()} 会退化成载体物品（{@code minecraft:apple} → 「苹果」、
 * {@code minecraft:sugar} → 「糖」）的原版名，学下去会把白菜记成苹果——这是必须拦住的事。</p>
 *
 * <p><b>隔离与落盘：</b>物理隔离键为 {@code ServerKey/fingerprint}，与收获规则同口径；
 * 切服立即清空内存（磁盘保留），换包（指纹变化）重新学。文件名取不到指纹时只在内存里生效，
 * 绝不写出一份「没有资源版本」的档案。</p>
 */
public final class StardewCropNameStore {

    /** 专属命名空间：星露谷 = CustomCrops 盆栽系统 */
    private static final String NAMESPACE = "customcrops";

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/stardew");

    /** 事件订阅所有者（服务级：模块开关不影响学名，玩家没开模块也要能在选择器里看到中文名） */
    private static final String OWNER = "service.stardew-crop-names";

    /** 主动观察间隔（tick）：2 秒一次，只扫背包 / 掉落物 / 展示实体 / 打开的容器，开销可忽略 */
    private static final int OBSERVE_INTERVAL_TICKS = 40;

    /** 界面对象下探深度上限（界面 → 字段 → 集合元素 → 条目字段） */
    private static final int SCREEN_SCAN_DEPTH = 3;

    /** 单轮从界面对象里最多检查的节点数 */
    private static final int SCREEN_SCAN_LIMIT = 600;

    /** 下探时显式避开的类型前缀：世界 / 网络 / 渲染 / 文本这些对象图巨大且不可能持有商品 */
    private static final String[] SCREEN_SCAN_BLOCKED = {
        "net.minecraft.client.Minecraft",
        "net.minecraft.world.level.",
        "net.minecraft.client.multiplayer.",
        "net.minecraft.client.renderer.",
        "net.minecraft.network.chat.",
        "net.minecraft.server.",
        "com.mojang.blaze3d.",
        "org.lwjgl",
        "it.unimi",
        "java.",
        "javax.",
        "sun.",
        "jdk.",
    };

    /** 最近一轮从当前界面对象里扫到的物品栈数量（诊断用） */
    private static volatile int lastScreenStacks;

    /** 本轮观察中学到 / 更新的名字（只为在日志里汇总成一条，不参与任何逻辑） */
    private static final Set<String> learnedThisCycle = new LinkedHashSet<>();

    /** 上一次报告过的界面签名；界面与计数都没变就不重复记日志 */
    private static String lastObservedScreen;

    /** 诊断用：最近一轮从当前界面对象里扫到的物品栈数量（0 说明商品不持有在客户端界面里） */
    public static int lastScreenStackCount() {
        return lastScreenStacks;
    }

    /** 单轮最多观察的掉落物数量（防止大范围掉落物刷屏） */
    private static final int DROP_LIMIT = 64;

    /** 掉落物观察半径（格） */
    private static final double DROP_RADIUS = 8.0;

    /** 展示实体（作物外观）观察半径（格）：农田通常就在玩家四周 */
    private static final double DISPLAY_RADIUS = 16.0;

    /** 单轮最多观察的展示实体数量 */
    private static final int DISPLAY_LIMIT = 256;

    private static final int PRIORITY_PRODUCE = 3;
    private static final int PRIORITY_SEED = 2;
    private static final int PRIORITY_STAGE = 1;

    /**
     * 界面里看到的物品名（最弱证据，与阶段名同级）。
     *
     * <p>界面上有拿真物品改名当装饰的条目，同名同级不覆盖，所以它既填不了空白，也改不动已有名字；
     * 但比什么都没有强——没见过实物的作物靠它至少能显示中文。一旦在世界里（背包 / 田地 / 掉落物）
     * 看到真种子或真产物，就会被更强的证据纠正。</p>
     */
    private static final int PRIORITY_UI_NAME = 1;

    private static final Path ROOT = Minecraft.getInstance().gameDirectory.toPath()
        .resolve("StardewFarm").resolve("crop-names");

    /** cropKey → 中文名 */
    private static final Map<String, String> names = new LinkedHashMap<>();
    /** cropKey → 该名字的来源优先级（决定能否被新证据覆盖） */
    private static final Map<String, Integer> priorityByCrop = new LinkedHashMap<>();
    /** cropKey → 名字来源（落盘 + 排查用） */
    private static final Map<String, String> sourceByCrop = new LinkedHashMap<>();
    /** cropKey → 已观测到的阶段名（阶段名去公共词这条兜底的输入） */
    private static final Map<String, Set<String>> stageNames = new LinkedHashMap<>();

    /**
     * cropKey → 本会话在世界 / 界面里实际见过的阶段键（如 {@code stage_1}）。
     *
     * <p>给「资源包里扫不出阶段清单」的服务器用：那些服务器把阶段模型打散 / 混淆，
     * {@code stagesOf} 于是为空，人工校准成熟阶段会被「资源里不存在该阶段」挡回去——
     * 而客户端明明刚在田里读到过这个阶段。这里记录的是服务器真实下发过的数据，
     * 只做「该阶段确实存在」的证据，不做任何推断，也绝不生成没见过的阶段名。</p>
     */
    private static final Map<String, Set<String>> observedStageKeys = new LinkedHashMap<>();

    /** 有新名字还没进过资源索引：置位后由订阅者重建索引 */
    private static boolean dirty;
    /** 有新名字还没落盘：攒到观察周期一次写一次（一轮农田扫描可能同时学到几十个名字） */
    private static boolean pendingSave;

    private static String loadedServerKey;
    private static String loadedFingerprint;
    private static int countdown;
    private static boolean initialized;

    /** 名字变化订阅者（星露谷模块注册：重建索引，让选择器立刻用上新名字） */
    private static final List<Runnable> CHANGE_LISTENERS = new CopyOnWriteArrayList<>();

    private StardewCropNameStore() {
    }

    /** 只注册一次：常驻观察玩家附近的自定义物品（模块未开启时同样生效） */
    public static synchronized void init() {
        if (initialized) return;
        initialized = true;
        ClientEventBus.subscribe(OWNER, ClientEventType.TICK, event -> tick());
    }

    /** 注册名字变化订阅者（模块构造时调用一次） */
    public static void addChangeListener(Runnable listener) {
        if (listener != null) CHANGE_LISTENERS.add(listener);
    }

    /** 查询某作物的中文名；未学到返回 {@code null}（调用方自行退技术名，绝不伪造） */
    public static String nameOf(String cropKey) {
        if (cropKey == null || cropKey.isBlank()) return null;
        ensureLoaded();
        return names.get(cropKey);
    }

    /** 诊断用：已学到的名字快照（作物键 → 中文名），按录入顺序 */
    public static Map<String, String> learned() {
        ensureLoaded();
        return new LinkedHashMap<>(names);
    }

    /**
     * 观察一个「世界里的」物品栈并尝试学名：背包 / 副手 / 掉落物 / 展示实体。
     *
     * <p>只认「带服务器下发名字组件」的栈；身份优先取 {@code craftengine:id} 这类自定义逻辑 ID，
     * 其次取 {@code item_model}。两者都解析不出作物身份时直接忽略。</p>
     */
    public static void observe(ItemStack stack) {
        observe(stack, true);
    }

    /**
     * 观察一个「界面里的」物品栈：容器槽位、村民交易清单、界面对象自带的物品栈。
     *
     * <p>与 {@link #observe} 的唯一差别是<b>产物名的证据强度降一档</b>。原因：界面上经常有
     * 拿真物品改名当装饰的条目（真机事故：某商店 GUI 用番茄物品做招牌，命名「星露谷作物回收店铺」，
     * 结果把「番茄」覆盖成了招牌名）。界面里的名字仍然可用——它能让没见过实物的作物也有名字——
     * 但只能填补空白，不能盖掉背包 / 田里学到的真名。种子名不降档：{@code _seeds} 这个身份
     * 本身就是强证据，装饰物不会挂它。</p>
     */
    public static void observeUi(ItemStack stack) {
        observe(stack, false);
    }

    private static void observe(ItemStack stack, boolean worldEvidence) {
        if (stack == null || stack.isEmpty()) return;
        // 名字必须是服务器为这个物品单独下发的：否则 getHoverName() 是载体物品的原版名
        if (stack.get(DataComponents.ITEM_NAME) == null && stack.get(DataComponents.CUSTOM_NAME) == null) return;

        ensureLoaded();
        String identity = identityOf(stack);
        if (identity == null) return;
        String name = clean(stack.getHoverName().getString());
        if (name.isBlank()) return;

        if (identity.contains("_stage_")) {
            recordStageKey(identity);
            learnFromStageName(identity.substring(0, identity.indexOf("_stage_")), name);
            return;
        }
        if (identity.endsWith("_seeds")) {
            String cropKey = identity.substring(0, identity.length() - "_seeds".length());
            String crop = stripSeedSuffix(name);
            if (crop != null) learn(cropKey, crop, "种子名", PRIORITY_SEED);
            return;
        }
        if (isVariant(identity)) return;   // 金星 / 巨大 / 变种名不是作物名，绝不拿来学
        if (worldEvidence) {
            learn(identity, name, "产物名", PRIORITY_PRODUCE);
        } else {
            learn(identity, name, "界面名", PRIORITY_UI_NAME);
        }
    }

    /** 观察玩家背包（含副手）、附近掉落物与附近展示实体；由常驻 tick 调用，也可被界面 / 指令按需调用 */
    public static void observeNearby() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        Inventory inventory = mc.player.getInventory();
        for (int slot = 0; slot < 36; slot++) {
            observe(inventory.getItem(slot));
        }
        observe(mc.player.getOffhandItem());
        if (mc.level == null) return;
        int seen = 0;
        for (Entity entity : mc.level.getEntities(mc.player, mc.player.getBoundingBox().inflate(DROP_RADIUS))) {
            if (!(entity instanceof ItemEntity drop)) continue;
            observe(drop.getItem());
            if (++seen >= DROP_LIMIT) break;
        }
        // 农田里的作物阶段名是这类服上作物中文名的主证据：资源包语言文件里往往根本没有这些作物，
        // 只有服务器给每个阶段起的名字（白菜幼苗 / 白菜生长期 / 白菜舒展期 → 白菜）。
        int displays = 0;
        for (Entity entity : mc.level.getEntities(mc.player, mc.player.getBoundingBox().inflate(DISPLAY_RADIUS))) {
            if (!(entity instanceof Display.ItemDisplay display)) continue;
            observe(display.getItemStack());
            if (++displays >= DISPLAY_LIMIT) break;
        }
        // 打开着的容器：玩家手动打开的种子箱、或模块静默打开的物料箱。这是覆盖度最高的一条——
        // 箱里通常存着全部作物的种子，一次就能把几十种作物的名字全拿到（资源包没翻译时唯一来源）。
        AbstractContainerMenu menu = ContainerAccess.openMenu();
        observeMenu(menu);
        // 自绘 GUI（插件商店常见）：商品既不在容器槽位、也不在交易清单里，只存在于界面对象自己的字段里
        observeOpenScreen();
        reportObservation(menu);
    }

    /**
     * 把本轮的观察结果写进日志——只在界面 / 计数发生变化时记一条。
     *
     * <p>排查必需：玩家在商店或箱子界面里没法输入指令、也不方便截图回报，日志是唯一能远程看到
     * 「这个界面到底吃不吃得到商品」的地方。每个界面只记一次，不会刷屏。</p>
     */
    private static void reportObservation(AbstractContainerMenu menu) {
        Screen screen = Minecraft.getInstance().gui.screen();
        if (screen == null) {
            lastObservedScreen = null;
        } else {
            int slots = 0;
            if (menu != null) {
                for (Slot slot : menu.slots) {
                    if (!slot.getItem().isEmpty()) slots++;
                }
            }
            String report = screen.getClass().getSimpleName() + "#" + slots + "#" + lastScreenStacks;
            if (!report.equals(lastObservedScreen)) {
                lastObservedScreen = report;
                LOGGER.info("[星露谷] 界面观察：{} —— 容器槽内物品 {} 个 / 界面对象内物品栈 {} 个",
                    screen.getClass().getSimpleName(), slots, lastScreenStacks);
            }
        }
        if (!learnedThisCycle.isEmpty()) {
            LOGGER.info("[星露谷] 学名本轮新增/更新 {} 个：{}（累计 {} 个）",
                learnedThisCycle.size(), String.join("、", learnedThisCycle), names.size());
            learnedThisCycle.clear();
        }
    }

    /**
     * 扫当前界面对象自带的物品栈。
     *
     * <p>有些商店界面是插件自绘的：槽位是空的、也没有交易清单，商品只以 {@code ItemStack} 的形式
     * 躺在界面对象自己的字段里（列表 / 数组 / 自定义条目对象）。这里按「类型认物品栈」做通用下探，
     * 不绑定任何具体类名——同一思路与 BossBar 探测一致。深度与节点数都有硬上限，且显式避开
     * 世界 / 渲染器 / 网络这些巨大的对象图，绝不无限递归。</p>
     */
    private static void observeOpenScreen() {
        Screen screen = Minecraft.getInstance().gui.screen();
        if (screen == null) {
            lastScreenStacks = 0;
            return;
        }
        lastScreenStacks = 0;
        scanForStacks(screen, 0, new int[] {SCREEN_SCAN_LIMIT}, new IdentityHashMap<>());
    }

    private static void scanForStacks(Object node, int depth, int[] budget, Map<Object, Boolean> seen) {
        if (node == null || budget[0] <= 0 || depth > SCREEN_SCAN_DEPTH) return;
        if (node instanceof ItemStack stack) {
            budget[0]--;
            lastScreenStacks++;
            observeUi(stack);
            return;
        }
        if (node instanceof Collection<?> collection) {
            for (Object element : collection) scanForStacks(element, depth + 1, budget, seen);
            return;
        }
        if (node instanceof Map<?, ?> map) {
            for (Object value : map.values()) scanForStacks(value, depth + 1, budget, seen);
            return;
        }
        if (node.getClass().isArray()) {
            int length = Array.getLength(node);
            for (int i = 0; i < length; i++) scanForStacks(Array.get(node, i), depth + 1, budget, seen);
            return;
        }
        if (node instanceof String || node instanceof Number || node instanceof Boolean
            || node instanceof Enum<?> || node instanceof Class<?> || node instanceof Thread) {
            return;
        }
        String type = node.getClass().getName();
        for (String blocked : SCREEN_SCAN_BLOCKED) {
            if (type.startsWith(blocked)) return;
        }
        if (seen.put(node, Boolean.TRUE) != null) return;   // 同一对象只下探一次，防环
        for (Class<?> type0 = node.getClass(); type0 != null && type0 != Object.class; type0 = type0.getSuperclass()) {
            for (Field field : type0.getDeclaredFields()) {
                if (Modifier.isStatic(field.getModifiers()) || field.getType().isPrimitive()) continue;
                try {
                    field.setAccessible(true);
                    scanForStacks(field.get(node), depth + 1, budget, seen);
                } catch (Throwable ignored) {
                    // 单字段不可读不影响其它字段
                }
            }
        }
    }

    /**
     * 观察一个已打开的容器菜单；为空时什么也不做。
     *
     * <p>覆盖两类界面：箱子 / 木桶 / 潜影盒型商店与种子箱（槽位里直接摆着实物），以及村民交易
     * ——后者的商品<b>不在槽位里</b>（槽位只有玩家自己放进去的东西和结果槽），必须读交易清单本身，
     * 否则「去商店看一圈」这条路学不到任何名字。</p>
     */
    public static void observeMenu(AbstractContainerMenu menu) {
        if (menu == null) return;
        for (Slot slot : menu.slots) {
            // 玩家自己的背包槽也在这个菜单里：那是世界证据（同一轮已经按世界证据观察过），
            // 不能因为「界面开着」就被降档成界面名
            if (slot.container instanceof Inventory) continue;
            observeUi(slot.getItem());
        }
        if (menu instanceof MerchantMenu merchant) {
            for (MerchantOffer offer : merchant.getOffers()) {
                observeUi(offer.getResult());
                observeUi(offer.getBaseCostA());
                observeUi(offer.getCostB());
            }
        }
    }

    /**
     * 立刻观察一次，学到新名字时立刻通知订阅者（重建索引）。
     *
     * <p>用于「马上要读名字」的时刻（打开选择器）。常驻 tick 每 2 秒才跑一轮，界面打开那一刻若不
     * 主动刷一次，玩家看到的仍是打开前那一刻的旧名字——真机事故：档案里已学到「白菜」，
     * 选择器里还显示技术键。</p>
     */
    public static void observeNow() {
        observeNearby();
        if (!consumeDirty()) return;
        notifyChangeListeners();
    }

    /** 切服 / 断线 / 资源失效：清空内存视图（磁盘档案保留，回到原服务器原资源时照旧复用） */
    public static void reset() {
        if (pendingSave) {
            pendingSave = false;
            save();   // 还没落盘的名字先写回它所属的隔离域，再清内存
        }
        loadedServerKey = null;
        loadedFingerprint = null;
        names.clear();
        priorityByCrop.clear();
        sourceByCrop.clear();
        stageNames.clear();
        observedStageKeys.clear();
        dirty = false;
        countdown = 0;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  常驻观察
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    private static void tick() {
        if (--countdown > 0) return;
        countdown = OBSERVE_INTERVAL_TICKS;
        // 这里不设「资源已就绪」闸门：学名只用物品自带的组件与命名空间前缀，与资源包解析无关。
        // 真机事故：刚进服还没跑「检测」时，闸门把整轮观察挡掉，玩家在商店 / 种子箱里看了一圈
        // 什么也没学到。
        observeNearby();
        if (pendingSave) {
            pendingSave = false;
            save();
        }
        if (!consumeDirty()) return;
        notifyChangeListeners();
    }

    /** 通知订阅者：索引里该用上新学到的名字了 */
    private static void notifyChangeListeners() {
        for (Runnable listener : new ArrayList<>(CHANGE_LISTENERS)) {
            try {
                listener.run();
            } catch (Exception ignored) {
                // 单个订阅者异常不影响其它订阅者
            }
        }
    }

    private static boolean consumeDirty() {
        boolean value = dirty;
        dirty = false;
        return value;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  身份与名字解析
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 物品栈 → 作物身份路径（如 {@code chinese_cabbage} / {@code chinese_cabbage_stage_3}）。
     *
     * <p>先取自定义逻辑 ID（{@code craftengine:id}，最权威，不受外观模型混淆影响），
     * 再退 {@code item_model}。两条都走同一个身份派生规则，因此
     * {@code item/crops/chinese_cabbage/stage_1} 这种模型路径也能得到
     * {@code chinese_cabbage_stage_1}。非 {@code customcrops} 命名空间一律不认。</p>
     *
     * <p><b>第三条来源（ItemsAdder 旧布局）：</b>这类物品既没有自定义逻辑 ID、也没有
     * {@code item_model} 组件，身份只存在于资源包的 {@code custom_model_data} 派发表里
     * （真机取证：{@code minecraft:paper} + 阈值 10579 → {@code customcrops:item/crops/corn/corn_seeds}）。
     * 少了这一条，这类服务器上的作物中文名一个都学不到——服务器明明下发了「玉米种子」，
     * 面板里却只有技术键。派发模型键按末段压平后再交给同一套身份派生规则，绝不改派生规则本身。</p>
     */
    private static String identityOf(ItemStack stack) {
        String itemModel = StardewInventoryService.itemModelOf(stack);
        CompoundTag data = customDataOf(stack);
        String logical = ItemIdentifier.extractCustomLogicId(itemModel, data);
        String fromLogical = cropIdentity(logical);
        if (fromLogical != null) return fromLogical;
        String fromModel = cropIdentity(itemModel);
        if (fromModel != null) return fromModel;
        String dispatched = ItemModelDispatchIndex.flatIdentityOf(StardewInventoryService.resolvedModelOf(stack));
        return cropIdentity(dispatched);
    }

    /** 单个原始身份串 → {@code customcrops} 命名空间下的作物身份路径；不适用返回 null */
    private static String cropIdentity(String raw) {
        if (raw == null || raw.isBlank()) return null;
        int colon = raw.indexOf(':');
        if (colon <= 0 || !NAMESPACE.equals(raw.substring(0, colon))) return null;
        String path = BlockStateModelResolver.deriveIdentityPath(raw);
        if (path == null || path.isBlank()) return null;
        // 带子目录的形态已被身份派生规则归一；这里再取末段兜住「目录 + 末段」的残留形态
        int slash = path.lastIndexOf('/');
        String last = slash >= 0 ? path.substring(slash + 1) : path;
        return last.isBlank() ? null : last;
    }

    /** 物品栈的自定义数据标签（可能没有 / 为空） */
    private static CompoundTag customDataOf(ItemStack stack) {
        CustomData data = stack.get(DataComponents.CUSTOM_DATA);
        return data == null || data.isEmpty() ? null : data.copyTag();
    }

    /** 是否为品质 / 特殊变种身份（它们的名字是产物名的派生，不是作物名） */
    private static boolean isVariant(String identity) {
        String key = identity.toLowerCase(Locale.ROOT);
        return key.endsWith("_silver_star") || key.endsWith("_golden_star") || key.endsWith("_variation")
            || key.startsWith("golden_") || key.startsWith("giant_") || key.startsWith("gigantic_");
    }

    /**
     * 记录一个在世界 / 界面里见到的阶段身份（如 {@code customcrops:chinese_cabbage_stage_3}）。
     *
     * <p>只记「见过」这一事实，不推断、不排序、不生成。</p>
     */
    private static void recordStageKey(String identity) {
        String path = com.yiyiaddon.feature.stardew.recognition.CropRuntimeStateResolver
            .pathOf(identity).toLowerCase(Locale.ROOT);
        String cropKey = com.yiyiaddon.feature.stardew.recognition.CropRuntimeStateResolver.stageCropKey(path);
        String stage = com.yiyiaddon.feature.stardew.recognition.CropRuntimeStateResolver.stageName(path);
        if (cropKey == null || stage == null) return;
        observedStageKeys.computeIfAbsent(cropKey, key -> new LinkedHashSet<>()).add(stage);
    }

    /**
     * 该作物的这个阶段是否在本会话里真实出现过（世界 / 界面观察到的服务器下发数据）。
     *
     * <p>供人工校准成熟阶段时使用：资源包阶段清单为空但世界里确实有该阶段时，仍然允许校准。</p>
     */
    public static boolean hasObservedStage(String cropKey, String stage) {
        if (cropKey == null || stage == null) return false;
        Set<String> stages = observedStageKeys.get(cropKey);
        return stages != null && stages.contains(stage.toLowerCase(Locale.ROOT));
    }

    /** 本会话见过的该作物阶段键快照（补全候选用；未见过的阶段绝不出现） */
    public static List<String> observedStages(String cropKey) {
        if (cropKey == null) return List.of();
        Set<String> stages = observedStageKeys.get(cropKey);
        return stages == null ? List.of() : List.copyOf(stages);
    }

    /** 阶段名去公共词：观测到 2 个以上阶段且公共前缀严格短于每一个阶段名时才采用 */
    private static void learnFromStageName(String cropKey, String stageName) {
        if (cropKey.isBlank()) return;
        Set<String> observed = stageNames.computeIfAbsent(cropKey, key -> new LinkedHashSet<>());
        if (!observed.add(stageName)) return;
        if (observed.size() < 2) return;

        String prefix = commonPrefix(observed).trim();
        if (prefix.isBlank()) return;
        // 公共前缀必须严格短于每一个观测名：否则说明阶段词还没被切掉，那串不是作物名
        for (String value : observed) {
            if (prefix.length() >= value.trim().length()) return;
        }
        // 纯技术标记（stage1 / stage2 → 公共前缀 stage）不是名字：要求含非 ASCII 或含空格
        if (!looksLikeWord(prefix)) return;
        learn(cropKey, prefix, "阶段名去公共词", PRIORITY_STAGE);
    }

    /** 名字是否像「词」而不是技术标记 */
    private static boolean looksLikeWord(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) > 0x7F) return true;
        }
        return value.indexOf(' ') >= 0;
    }

    /** 多个字符串的公共前缀（逐个比较字符，任一为空即返回空串） */
    private static String commonPrefix(Collection<String> values) {
        String prefix = null;
        for (String value : values) {
            if (value == null) return "";
            if (prefix == null) {
                prefix = value;
                continue;
            }
            int max = Math.min(prefix.length(), value.length());
            int i = 0;
            while (i < max && prefix.charAt(i) == value.charAt(i)) i++;
            prefix = prefix.substring(0, i);
            if (prefix.isEmpty()) return "";
        }
        return prefix == null ? "" : prefix;
    }

    /** 种子名去后缀；没有「种子」语义后缀时返回 null（那多半是载体名，绝不当作作物名） */
    private static String stripSeedSuffix(String name) {
        String value = name.trim();
        if (value.endsWith("种子")) return trimOrNull(value.substring(0, value.length() - 2));
        String lower = value.toLowerCase(Locale.ROOT);
        if (lower.endsWith(" seeds")) return trimOrNull(value.substring(0, value.length() - 6));
        if (lower.endsWith(" seed")) return trimOrNull(value.substring(0, value.length() - 5));
        if (lower.endsWith("_seeds")) return trimOrNull(value.substring(0, value.length() - 6));
        return null;
    }

    /** 写入一个候选名：优先级更高或相等才写入，同值时以先到的为准；写入即落盘 */
    private static void learn(String cropKey, String name, String source, int priority) {
        if (cropKey == null || cropKey.isBlank()) return;
        String value = clean(name);
        if (value.isBlank()) return;
        // 学到的就是技术键本身：没有任何信息量，不写（避免把 stem 当成「学到了」）
        if (value.equalsIgnoreCase(cropKey)) return;

        String existing = names.get(cropKey);
        if (value.equals(existing)) return;
        Integer current = priorityByCrop.get(cropKey);
        if (current != null && current > priority) return;
        if (current != null && current == priority && existing != null) return;

        names.put(cropKey, value);
        priorityByCrop.put(cropKey, priority);
        sourceByCrop.put(cropKey, source);
        dirty = true;
        pendingSave = true;
        learnedThisCycle.add(cropKey + "=" + value);
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  会话隔离与落盘
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 按当前 ServerKey + 资源指纹对齐内存。
     *
     * <p>换服：清空内存（磁盘保留）。同服补上指纹（自动检测完成）：把磁盘里已有的名字并进来，
     * 内存里刚学到的保留——绝不因为一次指纹就绪把已经学到的名字打回原点。</p>
     */
    private static void ensureLoaded() {
        String serverKey = ResourceExtractionService.serverKey();
        String fingerprint = ResourceExtractionService.fingerprint();
        if (Objects.equals(serverKey, loadedServerKey) && Objects.equals(fingerprint, loadedFingerprint)) return;
        // 会话键未就绪（还在主菜单）：什么都不做，绝不用空值抹掉已学到的名字
        if (serverKey == null || serverKey.isBlank()) return;

        boolean sameServer = serverKey.equals(loadedServerKey);
        // 换服前先把上一个服务器的名字写回它自己的隔离域：save() 用的是 loadedServerKey，
        // 一旦先改键就会把 A 服的名字写进 B 服的档案（跨服串名）。
        if (!sameServer && pendingSave) {
            pendingSave = false;
            save();
        }
        loadedServerKey = serverKey;
        loadedFingerprint = fingerprint;
        if (!sameServer) {
            names.clear();
            priorityByCrop.clear();
            sourceByCrop.clear();
            stageNames.clear();
            dirty = false;
        }
        if (fingerprint == null || fingerprint.isBlank()) return;
        load();
    }

    private static Path file() {
        String serverKey = loadedServerKey;
        String fingerprint = loadedFingerprint;
        if (serverKey == null || serverKey.isBlank() || fingerprint == null || fingerprint.isBlank()) return null;
        return ROOT.resolve(safe(serverKey)).resolve(safe(fingerprint) + ".json");
    }

    /** 已有名字不会被覆盖（换包后的合并、同服补指纹都走这里） */
    private static void load() {
        Path file = file();
        if (file == null || !Files.isRegularFile(file)) return;
        try {
            JsonObject root = JsonParser.parseString(Files.readString(file, StandardCharsets.UTF_8)).getAsJsonObject();
            if (!Objects.equals(loadedServerKey, string(root, "服务器"))
                || !Objects.equals(loadedFingerprint, string(root, "资源指纹"))) return;
            if (!root.has("作物名称") || !root.get("作物名称").isJsonObject()) return;
            for (var entry : root.getAsJsonObject("作物名称").entrySet()) {
                if (!entry.getValue().isJsonObject()) continue;
                JsonObject obj = entry.getValue().getAsJsonObject();
                String name = string(obj, "名称");
                if (name == null || names.containsKey(entry.getKey())) continue;
                names.put(entry.getKey(), name);
                priorityByCrop.put(entry.getKey(), integer(obj, "优先级"));
                sourceByCrop.put(entry.getKey(), string(obj, "来源"));
            }
        } catch (Exception ignored) {
            // 损坏档案不覆盖，保留现场供排查；学名失败只是名字退回技术键，不影响识别与执行
        }
    }

    /** 原子保存当前隔离域的全部名字；文件名取不到指纹时只在内存生效（不落盘） */
    private static void save() {
        Path file = file();
        if (file == null) return;
        JsonObject root = new JsonObject();
        root.addProperty("服务器", loadedServerKey);
        root.addProperty("资源指纹", loadedFingerprint);
        root.addProperty("版本", 1);
        JsonObject crops = new JsonObject();
        for (Map.Entry<String, String> entry : names.entrySet()) {
            JsonObject obj = new JsonObject();
            obj.addProperty("名称", entry.getValue());
            obj.addProperty("来源", sourceByCrop.get(entry.getKey()));
            Integer priority = priorityByCrop.get(entry.getKey());
            obj.addProperty("优先级", priority == null ? 0 : priority);
            crops.add(entry.getKey(), obj);
        }
        root.add("作物名称", crops);
        JsonFileStore.writeAtomic(file, root);
    }

    /** Windows 非法路径字符统一替换，隔离键原值仍写入 JSON 二次校验 */
    private static String safe(String value) {
        return value.replaceAll("[\\\\/:*?\"<>|]", "_");
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  小工具
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 剥离颜色代码并去首尾空格 */
    private static String clean(String text) {
        if (text == null) return "";
        return text.replaceAll("§[0-9a-fk-orA-FK-ORx]", "").trim();
    }

    private static String trimOrNull(String value) {
        String trimmed = value == null ? "" : value.trim();
        return trimmed.isBlank() ? null : trimmed;
    }

    private static String string(JsonObject obj, String key) {
        if (obj == null || !obj.has(key) || obj.get(key).isJsonNull() || !obj.get(key).isJsonPrimitive()) return null;
        String value = obj.get(key).getAsString();
        return value == null || value.isBlank() ? null : value;
    }

    private static int integer(JsonObject obj, String key) {
        if (obj == null || !obj.has(key) || obj.get(key).isJsonNull() || !obj.get(key).isJsonPrimitive()) return 0;
        try {
            return obj.get(key).getAsInt();
        } catch (Exception ignored) {
            return 0;
        }
    }
}
