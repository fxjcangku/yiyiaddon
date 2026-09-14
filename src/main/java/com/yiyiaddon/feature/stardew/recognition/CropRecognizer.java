package com.yiyiaddon.feature.stardew.recognition;

import com.yiyiaddon.feature.stardew.profile.RuleEvidence;
import com.yiyiaddon.feature.stardew.profile.StardewCropLifecycle;
import com.yiyiaddon.feature.stardew.profile.StardewServerProfile;
import com.yiyiaddon.model.resource.BlockSemantic;
import com.yiyiaddon.platform.resource.BlockStateModelResolver;
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

    /** 从语义身份末段提取稳定盆键（如 {@code customcrops:dry_pot_1} → {@code dry_pot_1}） */
    private static String potKeyOf(String identity) {
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
