package com.yiyiaddon.feature.stardew.recognition;

import com.yiyiaddon.feature.stardew.profile.RuleEvidence;
import com.yiyiaddon.feature.stardew.profile.StardewCropLifecycle;
import com.yiyiaddon.feature.stardew.profile.StardewServerProfile;
import com.yiyiaddon.model.resource.BlockSemantic;
import com.yiyiaddon.platform.resource.BlockStateModelResolver;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Locale;

/**
 * 星露谷作物识别器（只做「识别」，不做「决策」）。
 *
 * <p><b>真实状态判定不在本类：</b>成熟 / 生长中 / 再生中 / 死亡 / 未知一律交给唯一入口
 * {@link CropRuntimeStateResolver}。本类只负责两件事：</p>
 * <ol>
 *   <li>把世界方块解析成语义身份（共享 {@link BlockStateModelResolver}）；</li>
 *   <li>把统一结果映射为状态机使用的 {@link CropState}（{@code REGROWING} 归入 {@code GROWING}：
 *       保株作物回退阶段仍是被管理的生长作物，需要正常浇水与观察）。</li>
 * </ol>
 *
 * <p>因此 {@code .id 方块} 与星露谷农场看到的是同一份判定结果，不可能出现「一方成熟、
 * 一方生长中」。成熟判定始终来自当前 ServerKey + 资源指纹下的收获规则，绝不猜
 * {@code max(stage)}。</p>
 */
public final class CropRecognizer {

    /** 识别结果：状态机状态 + 作物键 + 阶段名 + 原始语义身份 + 统一真实状态 */
    public record CropRecognition(CropState state, String cropKey, String stageName, String modelIdentity,
                                  CropRuntimeStateResolver.RuntimeResult runtime) {

        /** 空盆结果 */
        public static CropRecognition empty() {
            return new CropRecognition(CropState.EMPTY, null, null, null, CropRuntimeStateResolver.RuntimeResult.UNKNOWN);
        }

        /** 未知结果 */
        public static CropRecognition unknown() {
            return new CropRecognition(CropState.UNKNOWN, null, null, null, CropRuntimeStateResolver.RuntimeResult.UNKNOWN);
        }

        /** 统一真实状态（显示层 / 聊天提示 / 调试信息一律读它，不读 {@link #state()}） */
        public CropRuntimeStateResolver.RuntimeState runtimeState() {
            return runtime.state();
        }
    }

    private CropRecognizer() {
        // 工具类，禁止实例化
    }

    /** 种植盆识别结果：干湿状态 + 稳定盆键 + 原始语义身份 */
    public record PotRecognition(PotState state, String potKey, String modelIdentity) {
        /** 未知结果 */
        public static PotRecognition unknown() {
            return new PotRecognition(PotState.UNKNOWN, null, null);
        }
    }

    /**
     * 识别盆上方作物状态。
     *
     * @param cropState 作物方块状态（盆上方一格）
     * @param profile   当前服务器档案（提供成熟阶段 / 生命周期规则）
     * @return 识别结果（状态机状态 + 统一真实状态）
     */
    public static CropRecognition recognize(BlockState cropState, StardewServerProfile profile) {
        if (cropState == null || cropState.isAir()) return CropRecognition.empty();

        BlockSemantic semantic = BlockStateModelResolver.resolve(cropState);
        String identity = semantic.identity();
        if (identity == null || identity.isBlank()) return CropRecognition.unknown();

        CropRuntimeStateResolver.RuntimeResult runtime = CropRuntimeStateResolver.resolve(
            identity, semantic.name(), semantic.model(), sourceFor(profile));
        return new CropRecognition(toCropState(runtime), runtime.cropKey(), runtime.stageName(), identity, runtime);
    }

    /**
     * 按语义身份直接识别作物（不走方块状态）。
     *
     * <p>给「作物不是方块」的服务器用：CraftEngine 系服务端在没有客户端模组时把作物渲染成展示实体
     * （{@code item_display}），实体手里的物品模型就是 {@code customcrops:<作物>_stage_N}——与方块模型
     * 派生出的身份是同一个格式，因此走同一条判定入口（收窄 / 成熟规则 / 生命周期完全同源），
     * 不会出现两条链路结论不同。</p>
     *
     * @param identity 语义身份（如 {@code customcrops:chinese_cabbage_stage_3}），传物品模型即可
     */
    public static CropRecognition recognizeIdentity(String identity, String name, String model,
                                                    StardewServerProfile profile) {
        if (identity == null || identity.isBlank()) return CropRecognition.unknown();
        CropRuntimeStateResolver.RuntimeResult runtime = CropRuntimeStateResolver.resolve(
            identity, name, model, sourceFor(profile));
        return new CropRecognition(toCropState(runtime), runtime.cropKey(), runtime.stageName(), identity, runtime);
    }

    /**
     * 从一个种植盆位识别作物：方块侧优先，认不出时再看展示实体。
     *
     * <p>CraftEngine 系服务器把作物只做成展示实体——盆上方那格往往是空气，或一个永远不变的隐形
     * 载体方块（如 {@code minecraft:tripwire}）。只看方块状态不但读不到作物，更读不到「作物已经
     * 没了」这个收获验证最关键的变化。真机事故：收割明明成功（掉落物已出现、模块都去拾取了），
     * 验证却因方块侧无变化而判「成熟植株状态未发生可验证变化」，于是永远学不出收获规则、收了也不认。</p>
     *
     * @param potPos 种植盆坐标（作物在它上面一格）
     */
    public static CropRecognition recognizeAtPot(BlockPos potPos, StardewServerProfile profile) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || potPos == null) return CropRecognition.unknown();
        BlockPos cropPos = potPos.above();
        CropRecognition byBlock = recognize(mc.level.getBlockState(cropPos), profile);
        if (byBlock.state() != CropState.EMPTY && byBlock.state() != CropState.UNKNOWN) return byBlock;
        // 方块侧认不出（空气 / 隐形载体）→ 看展示实体：它可能贴在作物格，也可能落在盆格
        CropRecognition byDisplay = fromDisplay(cropPos, profile);
        if (byDisplay == null) byDisplay = fromDisplay(potPos, profile);
        return byDisplay != null ? byDisplay : byBlock;
    }

    /** 展示实体通道：该位置归档的作物模型逐个尝试，取第一个能确定身份的 */
    private static CropRecognition fromDisplay(BlockPos pos, StardewServerProfile profile) {
        for (String model : StardewCropDisplayProbe.modelsAt(pos)) {
            CropRecognition recognition = recognizeIdentity(model, null, model, profile);
            if (recognition.state() != CropState.UNKNOWN) return recognition;
        }
        return null;
    }

    /**
     * 统一真实状态 → 状态机状态。
     *
     * <p>{@code REGROWING} 与 {@code GROWING} 都归入 {@link CropState#GROWING}：保株作物回退阶段
     * 仍然是被管理的生长作物（该浇水就浇水），状态机不因「再生中」而停摆；
     * 显示层读 {@link CropRecognition#runtimeState()} 仍能看到「再生中」。</p>
     *
     * <p>{@code UNKNOWN} 分两种：结构上确是阶段化作物（只是还没有成熟规则）→ 保持
     * {@link CropState#GROWING} 的既有语义，让低风险自动学习继续探测；结构上不是作物
     * → {@link CropState#UNKNOWN}，绝不把普通方块当作物。</p>
     */
    private static CropState toCropState(CropRuntimeStateResolver.RuntimeResult runtime) {
        return switch (runtime.state()) {
            case MATURE -> CropState.MATURE;
            case SPECIAL -> CropState.SPECIAL;
            case DEAD -> CropState.DEAD;
            case GROWING, REGROWING -> CropState.GROWING;
            case UNKNOWN -> runtime.isCrop() ? CropState.GROWING : CropState.UNKNOWN;
        };
    }

    // ── 按 Profile 构造只读规则源（识别器与唯一判定入口共用同一份规则） ──

    /** Profile 与规则源成对缓存：扫描时每格都会调用，不能每次新建对象 */
    private record ProfileBinding(StardewServerProfile profile, CropRuntimeStateResolver.CropRuntimeSource source) {
    }

    private static volatile ProfileBinding binding;

    private static CropRuntimeStateResolver.CropRuntimeSource sourceFor(StardewServerProfile profile) {
        if (profile == null) return null;
        ProfileBinding current = binding;
        if (current == null || current.profile() != profile) {
            current = new ProfileBinding(profile, new ProfileSource(profile));
            binding = current;
        }
        return current.source();
    }

    /**
     * {@link StardewServerProfile} → 只读规则源。
     *
     * <p>Profile 只承载「成熟阶段 + 保株作物」，没有回退阶段与逐作物签名，因此
     * {@link CropRuntimeStateResolver.RuntimeState#REGROWING} 在识别层不出现（回退阶段证据只有
     * 完整 VERIFIED 规则才有，那部分由星露谷模块自己注入的数据源提供）。</p>
     */
    private static final class ProfileSource implements CropRuntimeStateResolver.CropRuntimeSource {

        private final StardewServerProfile profile;

        private ProfileSource(StardewServerProfile profile) {
            this.profile = profile;
        }

        @Override public boolean knownCrop(String cropKey) {
            return profile.matureStage(cropKey) != null || profile.regrows(cropKey);
        }

        @Override public String cropDisplayName(String cropKey) {
            return null;   // 识别层不持有资源索引，中文名由显示层用索引兜底，绝不伪造
        }

        @Override public String matureStage(String cropKey) {
            return profile.matureStage(cropKey);
        }

        @Override public RuleEvidence ruleEvidence(String cropKey) {
            return profile.matureStage(cropKey) == null ? null : profile.matureEvidence(cropKey);
        }

        @Override public StardewCropLifecycle lifecycle(String cropKey) {
            return profile.regrows(cropKey) ? StardewCropLifecycle.REGROW : StardewCropLifecycle.UNKNOWN;
        }

        @Override public String afterHarvestStage(String cropKey) {
            return null;   // Profile 不记录回退阶段
        }

        @Override public List<String> stagesOf(String cropKey) {
            return List.of();   // Profile 不含阶段清单（只有完整规则表才有）
        }

        @Override public CropRuntimeStateResolver.CropRoleRef roleOfItem(String itemModel, String identityKey) {
            return null;   // 物品归属由模块数据源回答
        }
    }

    /**
     * 识别种植盆干湿状态。
     *
     * @param potState 盆方块状态
     * @return 干 / 湿 / 未知
     */
    public static PotState recognizePot(BlockState potState) {
        return recognizePotDetailed(potState).state();
    }

    /**
     * 识别种植盆（含稳定盆键，供「选择什么盆就管理什么盆」的盆型过滤）。
     *
     * <p>盆键从语义身份末段派生（如 {@code customcrops:dry_pot_1} → {@code dry_pot_1}），
     * 与资源索引里 POT 类候选的稳定键对应。干湿仅凭语义身份里的 dry / wet / pot 判定，
     * WET 不等同满水，真实水量仍走库存 / 土壤检测仪读取。</p>
     */
    public static PotRecognition recognizePotDetailed(BlockState potState) {
        if (potState == null) return PotRecognition.unknown();
        BlockSemantic semantic = BlockStateModelResolver.resolve(potState);
        String identity = semantic.identity();
        if (identity == null) {
            // 隐形载体（craftengine 种植盆）回退名称判断
            identity = semantic.name() == null ? "" : semantic.name();
        }
        String lower = identity.toLowerCase(Locale.ROOT);
        PotState state;
        if (lower.contains("wet") || lower.contains("湿润")) {
            state = PotState.WET;
        } else if (lower.contains("dry") || lower.contains("干燥") || lower.contains("pot") || lower.contains("盆")) {
            state = PotState.DRY;
        } else {
            state = PotState.UNKNOWN;
        }
        String potKey = potKeyOf(identity);
        return new PotRecognition(state, potKey, identity);
    }

    /**
     * 从语义身份末段提取稳定盆键（如 {@code customcrops:dry_pot_1} → {@code dry_pot_1}）。
     *
     * <p>公开静态：世界识别链路与 {@code .stardew 诊断} 共用同一口径。**必须剥离命名空间**——
     * 语义身份是 {@code namespace:path} 形式（如 {@code customcrops:dry_pot}），直接取末段会得到
     * 带命名空间的整串，与 {@code PotDefinition} 的干湿模型末段永远对不上（诊断里就会显示「盆型命中：否」，
     * 而实际识别其实是命中的）。</p>
     */
    public static String potKeyOf(String identity) {
        if (identity == null || identity.isBlank()) return null;
        String path = identity.contains(":") ? identity.substring(identity.indexOf(':') + 1) : identity;
        int slash = path.lastIndexOf('/');
        if (slash >= 0) path = path.substring(slash + 1);
        // 去掉 _ 连接的分段噪声：只保留 dry_pot_N / wet_pot_N 这类可读键
        String lower = path.toLowerCase(Locale.ROOT);
        if (lower.contains("dry_pot") || lower.contains("wet_pot") || lower.contains("pot_")) {
            return path;
        }
        return null;
    }
}
