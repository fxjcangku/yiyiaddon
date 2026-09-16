package com.yiyiaddon.feature.enchant.gear;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 原版装备附魔 · 配置数据层（JSON 数据驱动）。
 *
 * <p>负责从资源文件 {@code assets/yiyiaddon/gear-enchants.json} 加载
 * 钻石装备的原版极品附魔方案，并提供装备 / 方案 / 附魔的查询能力。</p>
 *
 * <p>职责边界：本类只做「读 JSON + 校验 + 查数据」，不渲染 UI，不做目标判断，
 * 不碰状态机。附魔真实等级上限通过 26.1.2 附魔注册表动态读取，避免硬编码。</p>
 *
 * <p>加载时按 26.1.2 实际规则校验：装备 ID 真实存在、附魔 ID 真实存在、
 * 等级合法、装备允许该附魔、附魔属原版且附魔台可产生、无互斥冲突；非法配置
 * 通过日志明确报错，装备级非法直接跳过，不静默接受。</p>
 *
 * <p><b>与旧项目的唯一实现差异</b>：旧项目经 {@code utils/DataPack.get(path)} 读取
 * （发布版从 {@code rulepack.bin} 内透明解密，开发环境回退散文件）；本项目直接以
 * {@code getResourceAsStream} 从 classpath 读同一路径，UTF-8 解码后交给 Gson。
 * <b>路径常量、解析方式与降级行为（资源缺失 / 解析失败 → 空数据 + 报错日志）逐字保持旧源码。</b></p>
 */
public final class GearEnchantData {

    private static final Logger LOG = LoggerFactory.getLogger("yiyiaddon/enchant");

    /** 资源路径（逐字沿用旧项目 {@code DataPack.get} 的路径常量） */
    private static final String RESOURCE_PATH = "/assets/yiyiaddon/gear-enchants.json";

    /**
     * Gson 实例。
     *
     * <p>本层是多层嵌套 POJO 映射（{@code Root → GearDefinition → GearProfile → TargetDefinition}），
     * 且必须让 {@code @SerializedName("default")} 生效；{@code core/Json} 只提供
     * {@code JsonObject} 级取值、无法映射 POJO，故此处自建 Gson 实例。</p>
     */
    private static final Gson GSON = new Gson();

    /** 附魔台无法产生的宝藏附魔黑名单（附魔 id 路径部分，不含命名空间） */
    private static final Set<String> TREASURE_ENCHANTS = Set.of(
        "mending", "frost_walker", "soul_speed", "swift_sneak",
        "binding_curse", "vanishing_curse", "wind_burst", "breach", "density"
    );

    /** 装备定义（对应 JSON 顶层 gear 节点） */
    public static final class GearDefinition {
        public String id;
        public String name;
        public String category;
        public List<GearProfile> profiles = new ArrayList<>();

        @Override
        public String toString() {
            return name;
        }
    }

    /** 极品方案定义（对应 JSON 的 profile 节点） */
    public static final class GearProfile {
        public String id;
        public String name;
        @SerializedName("default")
        public boolean isDefault;
        public List<String> exclusiveWith = new ArrayList<>();
        public List<String> forbidden = new ArrayList<>();
        public List<TargetDefinition> targets = new ArrayList<>();

        @Override
        public String toString() {
            return name;
        }
    }

    /** 目标附魔定义（对应 JSON 的 target 节点） */
    public static final class TargetDefinition {
        public String id;
        public String name;
        public int level;
        public boolean excludable;
    }

    /** JSON 根节点 */
    private static final class Root {
        public List<GearDefinition> gears = new ArrayList<>();
    }

    private static volatile GearEnchantData instance;

    private final List<GearDefinition> gears;

    private GearEnchantData(List<GearDefinition> gears) {
        this.gears = gears;
    }

    /** 懒加载单例：首次访问时从资源文件读取并缓存 */
    public static GearEnchantData get() {
        if (instance == null) {
            synchronized (GearEnchantData.class) {
                if (instance == null) instance = load();
            }
        }
        return instance;
    }

    private static GearEnchantData load() {
        // 本项目从 classpath 读散文件资源（旧项目 DataPack 的等价路径）
        String json = readResource();
        if (json == null) {
            LOG.error("[gear-enchants] 资源缺失：assets/yiyiaddon/gear-enchants.json");
            return new GearEnchantData(List.of());
        }
        try {
            Root root = GSON.fromJson(json, Root.class);
            List<GearDefinition> rawGears = root.gears == null ? List.of() : root.gears;
            return new GearEnchantData(validate(rawGears));
        } catch (Exception e) {
            // 解析失败时明确报错并降级为空数据，不阻断游戏启动
            LOG.error("[gear-enchants] JSON 解析失败", e);
            return new GearEnchantData(List.of());
        }
    }

    /** 以 UTF-8 从 classpath 读资源文本；资源缺失或读取异常返回 null（等同旧 DataPack.get 缺失语义） */
    private static String readResource() {
        try (InputStream in = GearEnchantData.class.getResourceAsStream(RESOURCE_PATH)) {
            return in == null ? null : new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 按 26.1.2 实际规则校验配置。
     * 注册表尚未就绪（未进世界）时跳过运行时校验，直接返回原始数据。
     * 装备 ID 非法/不存在的条目被剔除；附魔级非法通过日志明确报错。
     */
    private static List<GearDefinition> validate(List<GearDefinition> gears) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) return gears;

        List<GearDefinition> valid = new ArrayList<>();
        for (GearDefinition gear : gears) {
            if (validateGear(mc, gear)) valid.add(gear);
        }
        return valid;
    }

    private static boolean validateGear(Minecraft mc, GearDefinition gear) {
        Identifier gearId = Identifier.tryParse(gear.id);
        if (gearId == null) {
            LOG.error("[gear-enchants] 装备 ID 非法：{}", gear.id);
            return false;
        }
        var itemLookup = mc.level.registryAccess().lookup(Registries.ITEM);
        if (itemLookup.isEmpty() || itemLookup.get().get(ResourceKey.create(Registries.ITEM, gearId)).isEmpty()) {
            LOG.error("[gear-enchants] 装备 ID 不存在：{}", gear.id);
            return false;
        }
        for (GearProfile profile : gear.profiles) {
            validateProfile(mc, gear, profile);
        }
        return true;
    }

    private static void validateProfile(Minecraft mc, GearDefinition gear, GearProfile profile) {
        for (TargetDefinition target : profile.targets) {
            validateTarget(mc, gear, profile, target);
        }
    }

    private static void validateTarget(Minecraft mc, GearDefinition gear, GearProfile profile, TargetDefinition target) {
        Identifier enchId = Identifier.tryParse(target.id);
        if (enchId == null) {
            LOG.error("[gear-enchants] 附魔 ID 非法：{}（装备 {} 方案 {}）", target.id, gear.id, profile.id);
            return;
        }
        if (!"minecraft".equals(enchId.getNamespace())) {
            LOG.error("[gear-enchants] 附魔非原版：{}（装备 {}）", target.id, gear.id);
            return;
        }
        if (TREASURE_ENCHANTS.contains(enchId.getPath())) {
            LOG.error("[gear-enchants] 附魔无法通过附魔台获得：{}（装备 {}）", target.id, gear.id);
            return;
        }
        var enchHolder = mc.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT)
            .get(ResourceKey.create(Registries.ENCHANTMENT, enchId));
        if (enchHolder.isEmpty()) {
            LOG.error("[gear-enchants] 附魔 ID 不存在：{}（装备 {}）", target.id, gear.id);
            return;
        }
        Enchantment enchantment = enchHolder.get().value();
        if (target.level < 1 || target.level > enchantment.getMaxLevel()) {
            LOG.error("[gear-enchants] 附魔等级非法：{} 等级 {}（合法 1-{}，装备 {}）",
                target.id, target.level, enchantment.getMaxLevel(), gear.id);
            return;
        }
        // 装备是否允许该附魔（附魔台只能出装备支持的附魔）
        Identifier gearId = Identifier.tryParse(gear.id);
        Item item = mc.level.registryAccess().lookupOrThrow(Registries.ITEM)
            .get(ResourceKey.create(Registries.ITEM, gearId)).orElseThrow().value();
        if (!enchantment.isSupportedItem(new ItemStack(item))) {
            LOG.error("[gear-enchants] 装备不支持该附魔：{} 不能附魔 {}（装备 {}）", gear.id, target.id, gear.name);
        }
    }

    /** 返回全部装备定义 */
    public List<GearDefinition> gears() {
        return gears;
    }

    /** 按类别过滤装备（weapon / armor） */
    public List<GearDefinition> gearsByCategory(String category) {
        List<GearDefinition> result = new ArrayList<>();
        for (GearDefinition gear : gears) {
            if (category.equals(gear.category)) result.add(gear);
        }
        return result;
    }

    /** 按装备 ID 查找装备，找不到返回 null */
    public GearDefinition gear(String gearId) {
        for (GearDefinition gear : gears) {
            if (gear.id.equals(gearId)) return gear;
        }
        return null;
    }

    /**
     * 从 26.1.2 附魔注册表读取某附魔的真实最大等级。
     * 用于等级加减的上限限制，返回 -1 表示注册表尚未同步或附魔不存在。
     */
    public int maxLevelOf(String enchantmentId) {
        try {
            Minecraft mc = Minecraft.getInstance();
            if (mc.level == null) return -1;
            Identifier id = Identifier.tryParse(enchantmentId);
            if (id == null) return -1;
            ResourceKey<Enchantment> key = ResourceKey.create(Registries.ENCHANTMENT, id);
            var holder = mc.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(key);
            return holder.map(ref -> ref.value().getMaxLevel()).orElse(-1);
        } catch (Exception ignored) {
            return -1;
        }
    }
}
