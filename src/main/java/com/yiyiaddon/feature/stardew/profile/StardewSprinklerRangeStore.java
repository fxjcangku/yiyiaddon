package com.yiyiaddon.feature.stardew.profile;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yiyiaddon.repository.JsonFileStore;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemLore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 洒水器「工作范围」自学表：从物品说明里读「工作范围 A * B」，按服务器 + 资源指纹隔离落盘。
 *
 * <p><b>为什么要有它（2026-09-21 用户实机取证）：</b>资源包 JSON 里没有范围字段，服务端插件也不下发
 * 任何客户端接口，过去只能照《星露谷物语》攻略硬编码一张等级表
 * （{@code StardewRenderState#radiusOfLevel} 的 5×5 / 9×9 / 13×13 就是这么反推出来的）。
 * 而服务器其实把范围<b>写在物品说明里</b>（真机：初级「工作范围 5 * 5」/ 中级「9 * 9」/ 高级「13 * 13」）。
 * 只要见过一次洒水器实物（世界里的展示实体、背包里、手上），这段文字就能读出来 ——
 * 换服、换包自动跟上，不再靠一张写死的表。</p>
 *
 * <p><b>三个来源的可信度：</b>本表（服务器声明的工作范围，真机试验确认过就是<b>真实覆盖</b>）
 * &gt; 点位实测（数湿盆得来的<b>下限</b>）&gt; 等级表（写死的兜底）。实测之所以只能当证据、不能当
 * 第一来源：它只能在「盆已经铺到」的地方证明浇到了 —— 真机（2026-09-21 04:2x）一块 5×5 的盆群里，
 * 13×13 的高级洒水器也只测出 {@code 5×5 · 湿盆 24 格}，直到用户把盆摆到 13×13 的边角、
 * 等清晨洒水看到远处那盆湿了，才确认真实范围就是说明里的 13×13。</p>
 *
 * <p><b>隔离域：</b>一服务器一目录、一资源指纹一文件
 * （{@code StardewFarm/sprinkler-ranges/<服务器>/<指纹>.json}）——换服 / 换包各读各的，
 * 绝不把上一台服务器的范围套到这一台。</p>
 *
 * <p><b>只学不猜：</b>说明里读不出「工作范围 A * B」就什么都不写（不落盘、不返回结论），
 * 由调用方退回等级表。已知的身份不再重复解析：拼 tooltip 不便宜。</p>
 */
public final class StardewSprinklerRangeStore {

    /** 说明行的锚点：只有含「范围」的行才允许取数字，别的数字（储量 / 单次加水）绝不当作范围 */
    private static final String RANGE_ANCHOR = "范围";

    /** 「A * B」；分隔符兼容 {@code * × x X ＊ 乘}，数字限定 1~2 位（范围不可能三位数） */
    private static final Pattern RANGE_PAIR = Pattern.compile("(\\d{1,2})\\s*[*×xX＊乘]\\s*(\\d{1,2})");

    /** 合法单边：小于 3 格说明这行没写范围，大于 31 格说明读错了行 */
    private static final int MIN_SIDE = 3;
    private static final int MAX_SIDE = 31;

    private static final Path ROOT = Minecraft.getInstance().gameDirectory.toPath()
        .resolve("StardewFarm").resolve("sprinkler-ranges");

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/stardew");

    /** 洒水器逻辑键 → {X 方向边长, Z 方向边长} */
    private static final Map<String, int[]> sides = new LinkedHashMap<>();

    /** 本会话认出过实物的洒水器身份（无论有没有读到范围）：诊断口径，「未学到」时用来分清是没见到还是没说明 */
    private static final Set<String> observedKeys = new LinkedHashSet<>();

    /** 已经为哪些身份留过「自学未命中」日志：一条足够，不能变成每 2 秒一次的刷屏 */
    private static final Set<String> missLogged = new LinkedHashSet<>();

    private static String loadedServerKey;
    private static String loadedFingerprint;

    private StardewSprinklerRangeStore() {
    }

    /**
     * 观察一个「已认出是洒水器」的物品栈并尝试学范围。
     *
     * <p>调用方必须先用洒水器身份判据确认它是哪一种洒水器（{@code sprinklerKey} 取
     * {@code SprinklerDefinition#key()}），本表只负责读说明、记数字。</p>
     */
    public static void observe(String sprinklerKey, ItemStack stack) {
        if (sprinklerKey == null || sprinklerKey.isBlank() || stack == null || stack.isEmpty()) return;
        if (!ensureLoaded()) return;
        observedKeys.add(sprinklerKey);
        if (sides.containsKey(sprinklerKey)) return;
        int[] side = parseSide(stack);
        if (side == null) {
            // 见到实物但读不出范围：这一行是「换服后为什么还是等级估算」的唯一答案，
            // 每个身份每会话只留一条（真机：展示实体上的物品栈可能是服务端的精简副本，压根没带说明）。
            // 注意这里不放弃：背包 / 箱子里那一份可能带说明，下次见到还会再试。
            if (missLogged.add(sprinklerKey)) {
                LOGGER.info("[星露谷] 洒水器范围自学未命中：{} 的物品说明里没有「工作范围 A * B」（LORE 与 tooltip 都没有）",
                    sprinklerKey);
            }
            return;
        }
        sides.put(sprinklerKey, side);
        save();
        LOGGER.info("[星露谷] 洒水器范围自学：{} → {}×{}（物品说明）", sprinklerKey, side[0], side[1]);
    }

    /**
     * 该洒水器的覆盖范围偏移：{@code {dxMin, dxMax, dzMin, dzMax}}（相对洒水器那一格，含 0）。
     *
     * <p>没学到 / 不在服务器会话里返回 {@code null}。说明只给边长，这里按「以洒水器为中心」
     * 折算成半宽（{@code 边长 / 2}，与「放置在盆栽中心达到最佳效果」的说法一致）。</p>
     */
    public static int[] rangeOf(String sprinklerKey) {
        if (sprinklerKey == null || sprinklerKey.isBlank()) return null;
        if (!ensureLoaded()) return null;
        int[] side = sides.get(sprinklerKey);
        if (side == null) return null;
        int halfX = side[0] / 2;
        int halfZ = side[1] / 2;
        return new int[]{-halfX, halfX, -halfZ, halfZ};
    }

    /** 学到的边长文案（{@code 5×5}）；没学到 / 不在服务器会话里返回 {@code null} */
    public static String sideText(String sprinklerKey) {
        if (sprinklerKey == null || sprinklerKey.isBlank()) return null;
        if (!ensureLoaded()) return null;
        int[] side = sides.get(sprinklerKey);
        return side == null ? null : side[0] + "×" + side[1];
    }

    /** 诊断用：已学到的范围快照（洒水器逻辑键 → {@code 5×5}），按录入顺序 */
    public static Map<String, String> learned() {
        Map<String, String> result = new LinkedHashMap<>();
        if (!ensureLoaded()) return result;
        for (Map.Entry<String, int[]> entry : sides.entrySet()) {
            result.put(entry.getKey(), entry.getValue()[0] + "×" + entry.getValue()[1]);
        }
        return result;
    }

    /** 诊断用：本会话认出过实物的洒水器身份（无论有没有读到范围） */
    public static Set<String> observedKeys() {
        return new LinkedHashSet<>(observedKeys);
    }

    /** 切服 / 断线 / 资源失效：清空内存视图（磁盘档案保留，回到原服务器原资源时照旧复用） */
    public static void reset() {
        loadedServerKey = null;
        loadedFingerprint = null;
        sides.clear();
        observedKeys.clear();
        missLogged.clear();
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  读取与匹配
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 从物品说明里读「工作范围 A * B」。
     *
     * <p>先看渲染出来的 tooltip（部分服务器的数值是动态替换的，只有 tooltip 里才是最终文本），
     * 再看 {@code LORE} 组件（没有玩家 / 不在世界里时的唯一来源）。两条都读不出返回 {@code null}。</p>
     */
    private static int[] parseSide(ItemStack stack) {
        for (Component line : lines(stack)) {
            String text = line.getString();
            if (text == null || !text.contains(RANGE_ANCHOR)) continue;
            Matcher matcher = RANGE_PAIR.matcher(text);
            while (matcher.find()) {
                int x = Integer.parseInt(matcher.group(1));
                int z = Integer.parseInt(matcher.group(2));
                if (sane(x) && sane(z)) return new int[]{x, z};
            }
        }
        return null;
    }

    /** tooltip 行 + LORE 行；与 {@code StardewInventoryService#readWaterCapacity} 同一口径 */
    private static List<Component> lines(ItemStack stack) {
        Minecraft mc = Minecraft.getInstance();
        List<Component> result = new ArrayList<>();
        if (mc.player != null) {
            result.addAll(stack.getTooltipLines(Item.TooltipContext.of(mc.level), mc.player, TooltipFlag.NORMAL));
        }
        ItemLore lore = stack.get(DataComponents.LORE);
        if (lore != null) result.addAll(lore.lines());
        return result;
    }

    private static boolean sane(int side) {
        return side >= MIN_SIDE && side <= MAX_SIDE;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  隔离域读写
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 保证内存视图属于「当前服务器 + 当前资源指纹」。
     *
     * @return 是否处于已就绪的服务器会话；未就绪（主菜单 / 断线）一律返回 {@code false}，
     *         调用方按「没学到」处理 —— 绝不拿上一台服务器的范围给这一台画框
     */
    private static boolean ensureLoaded() {
        String serverKey = ResourceExtractionService.serverKey();
        String fingerprint = ResourceExtractionService.fingerprint();
        if (serverKey == null || serverKey.isBlank()) {
            if (loadedServerKey != null) reset();
            return false;
        }
        if (Objects.equals(serverKey, loadedServerKey) && Objects.equals(fingerprint, loadedFingerprint)) return true;
        loadedServerKey = serverKey;
        loadedFingerprint = fingerprint;
        sides.clear();
        load();
        return true;
    }

    private static void load() {
        Path file = file();
        if (file == null || !Files.isRegularFile(file)) return;
        try {
            JsonElement root = JsonParser.parseString(Files.readString(file, StandardCharsets.UTF_8));
            if (!root.isJsonObject()) return;
            JsonElement ranges = root.getAsJsonObject().get("范围");
            if (ranges == null || !ranges.isJsonObject()) return;
            for (Map.Entry<String, JsonElement> entry : ranges.getAsJsonObject().entrySet()) {
                if (!entry.getValue().isJsonArray()) continue;
                JsonArray pair = entry.getValue().getAsJsonArray();
                if (pair.size() < 2) continue;
                int x = pair.get(0).getAsInt();
                int z = pair.get(1).getAsInt();
                if (sane(x) && sane(z)) sides.put(entry.getKey(), new int[]{x, z});
            }
        } catch (Exception ignored) {
            // 损坏档案不覆盖：留空视图，保留原始文件供玩家排查
        }
    }

    /** 原子保存当前隔离域的全部范围；隔离键不完整时只在内存生效（不落盘） */
    private static void save() {
        Path file = file();
        if (file == null) return;
        JsonObject ranges = new JsonObject();
        for (Map.Entry<String, int[]> entry : sides.entrySet()) {
            JsonArray pair = new JsonArray();
            pair.add(entry.getValue()[0]);
            pair.add(entry.getValue()[1]);
            ranges.add(entry.getKey(), pair);
        }
        JsonObject root = new JsonObject();
        root.addProperty("服务器", loadedServerKey);
        root.addProperty("资源指纹", loadedFingerprint);
        root.addProperty("版本", 1);
        root.add("范围", ranges);
        JsonFileStore.writeAtomic(file, root);
    }

    private static Path file() {
        if (loadedServerKey == null || loadedFingerprint == null || loadedFingerprint.isBlank()) return null;
        return ROOT.resolve(safe(loadedServerKey)).resolve(safe(loadedFingerprint) + ".json");
    }

    /** Windows 非法路径字符统一替换，隔离键原值仍写入 JSON 二次校验 */
    private static String safe(String value) {
        return value.replaceAll("[\\\\/:*?\"<>|]", "_");
    }
}
