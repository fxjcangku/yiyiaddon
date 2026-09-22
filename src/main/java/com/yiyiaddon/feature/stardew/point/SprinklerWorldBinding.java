package com.yiyiaddon.feature.stardew.point;

import com.yiyiaddon.feature.stardew.StardewContext;
import com.yiyiaddon.feature.stardew.recognition.StardewCropDisplayProbe;
import com.yiyiaddon.model.resource.BlockSemantic;
import com.yiyiaddon.platform.resource.BlockStateModelResolver;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

/**
 * 用户人工确认的服务器专属洒水器世界绑定。
 *
 * <p>绑定不会修改通用 BlockIdentity，也不会宣称 {@code sugar_cane[age=9]} 在所有服务器都是
 * 洒水器。它只在 ServerKey + 资源指纹 + 逻辑洒水器 + 稳定世界载体状态四项全部一致时有效。</p>
 *
 * <p><b>载体有两种形态：</b>① 方块形态——载体是那一格的 BlockState；② 贴图形态——洒水器由
 * {@code item_display} 渲染，载体就是展示实体本身，<b>它所在的方块格是空气</b>。后者由
 * {@link #displayCarrierAt} 识别，不是「准星对着空气」。</p>
 */
public record SprinklerWorldBinding(String serverKey, String fingerprint, String sprinklerKey,
                                    String carrierBlockId, String carrierState, String semanticIdentity,
                                    String semanticModel) {

    /**
     * 这一格上是否挂着展示实体（{@code item_display}）。
     *
     * <p><b>为什么它是共享判据：</b>贴图形态洒水器所在格永远是空气，本方法是唯一能区分
     * 「合法的展示实体载体」与「准星对着空气乱指」的依据。准星取坐标（{@code StardewPointActions}）
     * 与载体捕捉（{@link #capture}）必须用同一份搜索范围（{@code inflate(1.0)} + {@code blockPosition}
     * 相等），否则会出现「坐标取到了、载体却读不出来」这种自相矛盾的失败。</p>
     */
    public static boolean displayCarrierAt(BlockPos pos) {
        return displayCarrier(pos) != null;
    }

    /** 这一格上挂着的展示实体（没有则 {@code null}）；搜索范围与 {@link #displayCarrierAt} 同一个 */
    private static Display.ItemDisplay displayCarrier(BlockPos pos) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || pos == null || !mc.level.isLoaded(pos)) return null;
        for (Entity entity : mc.level.getEntities((Entity) null, new AABB(pos).inflate(1.0),
            e -> e instanceof Display.ItemDisplay && e.blockPosition().equals(pos))) {
            if (entity instanceof Display.ItemDisplay display) return display;
        }
        return null;
    }

    /**
     * 贴图实体这一格<b>此刻挂的是哪一款洒水器</b>（如 {@code customcrops:sprinkler_2}）。
     *
     * <p>读法与本模组的展示实体识别同一个入口（{@link StardewCropDisplayProbe#liveIdentityAt}）：
     * {@code item_model} 组件优先，缺失时按「基础物品 + {@code custom_model_data}」查资源包派发表。
     * 读不出来返回 {@code null} —— 上层一律「读不出就不比」，绝不把读不出来当成「型号不符」。</p>
     */
    public static String displayIdentityAt(BlockPos pos) {
        return StardewCropDisplayProbe.liveIdentityAt(pos);
    }

    /**
     * 贴图实体挂的型号与期望的不一致时，返回<b>当前挂着的那个型号</b>；一致 / 读不出来返回 {@code null}。
     *
     * <p><b>为什么必须比这一项</b>（用户 2026-09-22：「我刚刚放的是优质洒水器，假如我放一个高级洒水器
     * 也能识别，不能识别原来的物品」）：贴图形态原先只记「那一格是空气 + 挂着展示实体」，
     * 同格换成别的型号照样通过校验，于是被当成本来绑的那一款去维护（定位、范围、水位判断全错）。
     * 型号信息只在展示实体的物品上（方块是空气），所以只能从实体读。</p>
     */
    public static String displayTierMismatch(BlockPos pos, String expectedKey) {
        if (expectedKey == null) return null;
        String current = displayIdentityAt(pos);
        if (current == null) return null;
        return sameTier(current, expectedKey) ? null : current;
    }

    /**
     * 两个身份是不是同一款洒水器：只比「末段型号名」，并忽略物品形态后缀。
     *
     * <p>同一台洒水器在不同入口下写法不同（模型身份 {@code customcrops:sprinkler_2}、
     * 学名里的物品键 {@code sprinkler_2_item}、带子目录的 {@code customcrops:item/sprinkler_2}），
     * 照字面比会把同一款判成不同款，反而拦住正常维护。</p>
     */
    private static boolean sameTier(String a, String b) {
        return normalizeTier(a).equals(normalizeTier(b));
    }

    private static String normalizeTier(String identity) {
        String value = identity == null ? "" : identity.trim();
        int colon = value.indexOf(':');
        if (colon >= 0) value = value.substring(colon + 1);
        int slash = value.lastIndexOf('/');
        if (slash >= 0) value = value.substring(slash + 1);
        if (value.endsWith("_item")) value = value.substring(0, value.length() - "_item".length());
        return value.toLowerCase(java.util.Locale.ROOT);
    }

    /** 从准星当前真实 BlockState 建立一次人工确认绑定。 */
    public static SprinklerWorldBinding capture(BlockPos pos, String sprinklerKey) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || pos == null || sprinklerKey == null) return null;
        BlockState state = mc.level.getBlockState(pos);
        // 贴图形态洒水器的载体是展示实体而非方块，所在格本来就是空气：只在这一格确实挂着展示实体时
        // 放行。方块形态洒水器那一格非空气，与旧行为逐字相同。
        if (state.isAir() && !displayCarrierAt(pos)) return null;
        String serverKey = StardewContext.serverKey();
        String fingerprint = ResourceExtractionService.fingerprint();
        if (serverKey == null || fingerprint == null || fingerprint.isBlank()) return null;
        var blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        if (blockId == null) return null;
        BlockSemantic semantic = BlockStateModelResolver.resolve(state);
        return new SprinklerWorldBinding(serverKey, fingerprint,
            sprinklerKey, blockId.toString(), state.toString(), semantic.identity(), semantic.model());
    }

    /** 载体是<b>展示实体形态</b>：那一格本来就是空气，洒水器本体是 {@code item_display} 实体 */
    public boolean displayCarried() {
        return "minecraft:air".equals(carrierBlockId);
    }

    /** 当前这一格挂的型号与绑定不一致时返回当前型号（供报错文案用）；一致 / 读不出来返回 {@code null} */
    public String tierMismatchAt(BlockPos pos) {
        return displayTierMismatch(pos, sprinklerKey);
    }

    /**
     * 当前世界必须仍命中建立绑定时的完整隔离域与稳定载体状态。
     *
     * <p><b>贴图形态要额外查展示实体</b>（真机事故：洒水器被挖掉后，模块照常「维护」并报
     * 「本轮 1 台」，ESP 方框也照画）：它的载体格本来就永远是空气，被挖掉之后空气还是空气 ——
     * 只比 BlockState 的话这里会一直判定「绑定仍然有效」。而 {@link #capture} 当初正是靠
     * {@link #displayCarrierAt} 才认出这种形态的，两侧必须用同一份判据。</p>
     *
     * <p>展示实体还在时还要比<b>型号</b>（同格换成别的洒水器同样算绑定失效，见
     * {@link #displayTierMismatch}）。</p>
     */
    public boolean matches(BlockPos pos) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || pos == null || !mc.level.isLoaded(pos)) return false;
        if (!java.util.Objects.equals(serverKey, StardewContext.serverKey())
            || !java.util.Objects.equals(fingerprint, ResourceExtractionService.fingerprint())) return false;
        BlockState state = mc.level.getBlockState(pos);
        var blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        if (blockId == null || !java.util.Objects.equals(carrierBlockId, blockId.toString())
            || !java.util.Objects.equals(carrierState, state.toString())) return false;
        if (!state.isAir()) return true;
        // 展示实体形态：① 这一格还得挂着贴图实体（挖掉即失效）；② 型号还得是原来那一款
        return displayCarrierAt(pos) && displayTierMismatch(pos, sprinklerKey) == null;
    }
}
