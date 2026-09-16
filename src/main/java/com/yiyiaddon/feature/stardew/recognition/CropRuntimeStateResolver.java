package com.yiyiaddon.feature.stardew.recognition;

import com.yiyiaddon.feature.stardew.profile.RuleEvidence;
import com.yiyiaddon.feature.stardew.profile.StardewCropLifecycle;
import com.yiyiaddon.model.resource.BlockSemantic;
import com.yiyiaddon.platform.resource.BlockStateModelResolver;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;
import java.util.Locale;

/**
 * 自定义作物「真实运行状态」统一解析器（全项目唯一判定入口）。
 *
 * <p><b>为什么必须只有一个：</b>过去「成熟 / 生长中」的判断分散在识别器（{@link CropRecognizer}）与
 * 星露谷决策层，而 {@code .id 方块} 完全不做作物状态判断，只把资源包原始名称
 * （如「番茄生长阶段 IV」）当结论展示。于是同一株已经成熟的番茄，星露谷认为成熟、
 * {@code .id 方块} 却只显示资源名，玩家看到两套说法。</p>
 *
 * <p>本类把判定收敛成唯一函数：输入「方块语义身份 + 当前 ServerKey 资源版本下的收获规则」，
 * 输出 {@link RuntimeState} 与证据链。{@code .id 方块}、星露谷农田扫描、收割决策、
 * {@code .stardew 标记成熟} 全部调用这里，禁止任何一方另写一套。</p>
 *
 * <p><b>判定规则（全部来自真实证据，绝不猜）：</b></p>
 * <ul>
 *   <li>身份含 {@code dead} → {@link RuntimeState#DEAD}（资源包自身标注）；</li>
 *   <li>阶段段含 {@code golden/giant/gigantic/variation} → {@link RuntimeState#SPECIAL}
 *       （特殊变种，需独立收割动作，绝不按普通成熟处理）；</li>
 *   <li>当前阶段 == 该作物成熟阶段 → {@link RuntimeState#MATURE}；</li>
 *   <li>保株作物（lifecycle = REGROW）当前阶段 == 已记录的回退阶段 → {@link RuntimeState#REGROWING}；</li>
 *   <li>已确认成熟阶段但当前阶段不是它 → {@link RuntimeState#GROWING}；</li>
 *   <li>没有成熟规则 → {@link RuntimeState#UNKNOWN}（<b>绝不猜</b>，
 *       也绝不把 {@code max(stage)} 或中文名里的「生长阶段」当成成熟依据）。</li>
 * </ul>
 *
 * <p><b>规则来源按 ServerKey + 资源指纹隔离：</b>数据由 {@link CropRuntimeSource} 提供，
 * 由星露谷模块在构造时注入（读的是它自己按 {@code ServerKey + fingerprint} 载入并复核过签名的规则表）。
 * 未注入时一律返回「无规则」，因此 A / B / C 服务器之间不可能互相串规则。</p>
 */
public final class CropRuntimeStateResolver {

    /** 真实运行状态（识别层唯一状态枚举，显示文案即玩家看到的中文） */
    public enum RuntimeState {
        /** 生长中：已确认成熟规则，当前阶段不是成熟阶段 */
        GROWING("生长中"),
        /** 成熟：当前阶段命中该作物在本服务器资源版本上的成熟阶段 */
        MATURE("成熟"),
        /** 再生中：保株作物收割后回退到的已记录阶段 */
        REGROWING("再生中"),
        /** 特殊变种（金色 / 巨大 / 变种形态），需要独立收割动作 */
        SPECIAL("特殊变种"),
        /** 已死亡（资源包身份明确标注） */
        DEAD("已死亡"),
        /** 未知：证据不足，绝不猜 */
        UNKNOWN("未知");

        private final String displayName;

        RuntimeState(String displayName) {
            this.displayName = displayName;
        }

        public String displayName() {
            return displayName;
        }
    }

    /** 自定义作物在物品侧的角色 */
    public enum CropRole {
        /** 种子 */
        SEED("种子"),
        /** 成熟产物 */
        PRODUCE("成熟产物"),
        /** 特殊产物（金色 / 巨大 / 变种） */
        VARIANT("特殊产物");

        private final String displayName;

        CropRole(String displayName) {
            this.displayName = displayName;
        }

        public String displayName() {
            return displayName;
        }
    }

    /** 物品 ↔ 作物归属：所属作物 + 角色 */
    public record CropRoleRef(String cropKey, String cropDisplayName, CropRole role) {
    }

    /**
     * 统一解析结果。
     *
     * @param state           真实运行状态
     * @param cropKey         作物键（仅当资源身份结构上确认为阶段化作物流水线时非空）
     * @param cropDisplayName 作物中文名（索引未命中时为 null，显示层自行兜底，绝不伪造）
     * @param stageName       资源阶段名（如 {@code stage_4}）
     * @param matureStage     该作物在当前服务器资源版本上的成熟阶段（无规则为 null）
     * @param evidence        成熟规则来源（VERIFIED / DOCUMENTED / CANDIDATE / UNKNOWN）
     * @param lifecycle       生命周期（一次性 / 保株 / 未确认）
     * @param identity        资源身份（如 {@code customcrops:tomato_stage_4}）
     * @param resourceName    资源包原始名称（如「番茄生长阶段 IV」）
     * @param model           命中的资源模型路径
     */
    public record RuntimeResult(
        RuntimeState state,
        String cropKey,
        String cropDisplayName,
        String stageName,
        String matureStage,
        RuleEvidence evidence,
        StardewCropLifecycle lifecycle,
        String identity,
        String resourceName,
        String model
    ) {

        /** 无任何证据的结果 */
        public static final RuntimeResult UNKNOWN =
            new RuntimeResult(RuntimeState.UNKNOWN, null, null, null, null, null, null, null, null, null);

        /** 结构上是否确认为「阶段化自定义作物方块」（与是否有成熟规则无关） */
        public boolean isCrop() {
            return cropKey != null;
        }

        /**
         * 是否属于统一作物状态域。
         *
         * <p>通用枯死载体可能只能证明 {@code dead_crop}，无法证明死前 cropKey；它仍然是明确的
         * DEAD 作物状态，显示与清理链不能再用 cropKey 是否存在把它排除。</p>
         */
        public boolean isCropState() {
            return cropKey != null || state == RuntimeState.DEAD;
        }

        /** 是否有明确阶段（可用于人工校准成熟阶段） */
        public boolean hasStage() {
            return stageName != null && !stageName.isBlank();
        }

        /** 当前服务器是否已确认该作物的成熟阶段 */
        public boolean hasMatureRule() {
            return matureStage != null && !matureStage.isBlank();
        }

        /** 规则来源显示文案：中文 + 技术名（排查时两者都要看得到） */
        public String ruleSourceLabel() {
            return evidence == null ? "未知" : evidence.displayName() + "（" + evidence.name() + "）";
        }

        /** 生命周期显示文案：中文 + 技术名 */
        public String lifecycleLabel() {
            if (lifecycle == StardewCropLifecycle.ONE_SHOT) return "一次性（ONE_SHOT）";
            if (lifecycle == StardewCropLifecycle.REGROW) return "保株（REGROW）";
            return "未确认";
        }

        /** 成熟阶段显示文案：未确认时明确写「未确认」，绝不拿当前阶段冒充 */
        public String matureStageLabel() {
            return hasMatureRule() ? matureStage : "未确认";
        }
    }

    /**
     * 规则 / 资源数据源（由星露谷模块注入，按当前 ServerKey + 资源指纹隔离）。
     *
     * <p>只暴露「查询」，不暴露写入：人工校准与自动学习的写入口仍然唯一，
     * 防止出现第二套成熟规则库。</p>
     */
    public interface CropRuntimeSource {

        /** 该 cropKey 是否属于当前服务器资源索引 */
        boolean knownCrop(String cropKey);

        /** 作物中文名；索引未命中返回 null */
        String cropDisplayName(String cropKey);

        /** 该作物在当前服务器资源版本上的成熟阶段；无规则返回 null */
        String matureStage(String cropKey);

        /** 成熟规则的证据等级；无规则返回 null */
        RuleEvidence ruleEvidence(String cropKey);

        /** 生命周期；未确认返回 {@link StardewCropLifecycle#UNKNOWN} */
        StardewCropLifecycle lifecycle(String cropKey);

        /** 保株作物收割后的回退阶段；无证据返回 null */
        String afterHarvestStage(String cropKey);

        /** 该作物在当前服务器资源包里真实存在的阶段（资源扫描结果） */
        List<String> stagesOf(String cropKey);

        /** 物品模型 / 身份键所属的作物与角色；未命中返回 null */
        CropRoleRef roleOfItem(String itemModel, String identityKey);
    }

    /** 未注入任何数据源时的空实现：一律「无规则」，绝不使用其它服务器的规则兜底 */
    private static final CropRuntimeSource EMPTY = new CropRuntimeSource() {
        @Override public boolean knownCrop(String cropKey) { return false; }
        @Override public String cropDisplayName(String cropKey) { return null; }
        @Override public String matureStage(String cropKey) { return null; }
        @Override public RuleEvidence ruleEvidence(String cropKey) { return null; }
        @Override public StardewCropLifecycle lifecycle(String cropKey) { return StardewCropLifecycle.UNKNOWN; }
        @Override public String afterHarvestStage(String cropKey) { return null; }
        @Override public List<String> stagesOf(String cropKey) { return List.of(); }
        @Override public CropRoleRef roleOfItem(String itemModel, String identityKey) { return null; }
    };

    private static volatile CropRuntimeSource source;

    private CropRuntimeStateResolver() {
        // 工具类，禁止实例化
    }

    /** 注入当前星露谷模块的运行时数据源（模块构造时调用一次） */
    public static void install(CropRuntimeSource runtimeSource) {
        source = runtimeSource;
    }

    /** 当前数据源；未注入时为零规则空实现 */
    public static CropRuntimeSource source() {
        CropRuntimeSource current = source;
        return current == null ? EMPTY : current;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  解析入口
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 解析某坐标方块的作物真实状态（世界方块 → 语义身份 → 状态） */
    public static RuntimeResult resolve(BlockPos pos) {
        var level = Minecraft.getInstance().level;
        if (level == null || pos == null) return RuntimeResult.UNKNOWN;
        return resolve(level.getBlockState(pos));
    }

    /**
     * 解析语义身份（不走方块状态）。给「作物不是方块」的服务器用：CraftEngine 系服务端把作物渲染成
     * 展示实体，物品模型就是 {@code customcrops:<作物>_stage_N}——与方块路径派生出的身份同格式，
     * 因此喂进同一条判定入口，成熟规则 / 生命周期结论与方块路径完全一致。
     *
     * @param identity 语义身份（如 {@code customcrops:chinese_cabbage_stage_3}）
     */
    public static RuntimeResult resolveIdentity(String identity) {
        return resolve(identity, null, identity, source());
    }

    /** 解析某方块状态的作物真实状态 */
    public static RuntimeResult resolve(BlockState cropState) {
        if (cropState == null || cropState.isAir()) return RuntimeResult.UNKNOWN;
        BlockSemantic semantic = BlockStateModelResolver.resolve(cropState);
        return resolve(semantic.identity(), semantic.name(), semantic.model(), source());
    }

    /** 准星探测结果：是否命中方块 + 该方块的作物解析结果 */
    public record CrosshairProbe(boolean hitBlock, BlockPos pos, RuntimeResult crop) {
    }

    /**
     * 用真实准星命中解析作物状态。
     *
     * <p>命中实体或空气时 {@link CrosshairProbe#hitBlock()} 为 false，调用方据此给出
     * 「准星未指向有效方块」；绝不使用上一次目标或附近作物代替。</p>
     */
    public static CrosshairProbe probeCrosshair() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return new CrosshairProbe(false, null, RuntimeResult.UNKNOWN);
        HitResult hit = mc.hitResult;
        if (!(hit instanceof BlockHitResult blockHit)) return new CrosshairProbe(false, null, RuntimeResult.UNKNOWN);
        BlockPos pos = blockHit.getBlockPos();
        return new CrosshairProbe(true, pos, resolve(mc.level.getBlockState(pos)));
    }

    /**
     * 核心判定：资源身份 + 规则数据源 → 真实状态。
     *
     * <p>公开给识别器复用（识别器传入按 Profile 构造的只读数据源），保证两边永远同源。</p>
     */
    public static RuntimeResult resolve(String identity, String resourceName, String model, CropRuntimeSource rules) {
        CropRuntimeSource src = rules == null ? EMPTY : rules;
        String path = pathOf(identity);
        if (path.isBlank()) {
            return new RuntimeResult(RuntimeState.UNKNOWN, null, null, null, null, null, null,
                identity, resourceName, model);
        }
        String lower = path.toLowerCase(Locale.ROOT);
        String stage = stageName(lower);

        // ── 死亡：资源包身份明确标注 dead，无需成熟规则即可确认 ──
        if (lower.contains("dead")) {
            String cropKey = deadCropKey(lower, stage, src);
            return build(RuntimeState.DEAD, src, cropKey, stage, identity, resourceName, model);
        }

        String cropKey = stageCropKey(lower);

        // ── 非阶段化方块（普通方块 / 非作物自定义方块）：证据不足，如实返回未知 ──
        if (cropKey == null || stage == null) {
            return new RuntimeResult(RuntimeState.UNKNOWN, null, null, null, null, null, null,
                identity, resourceName, model);
        }

        // ── 特殊变种阶段：需独立收割动作，绝不按普通成熟处理 ──
        if (isSpecialStage(stage)) {
            return build(RuntimeState.SPECIAL, src, cropKey, stage, identity, resourceName, model);
        }

        String mature = src.matureStage(cropKey);

        // ── 成熟：当前阶段命中本服务器资源版本上的成熟阶段 ──
        if (mature != null && (mature.equalsIgnoreCase(stage) || mature.equalsIgnoreCase(path))) {
            return build(RuntimeState.MATURE, src, cropKey, stage, identity, resourceName, model);
        }

        // ── 再生中：保株作物收割后回退到的已记录阶段（必须有明确回退阶段证据） ──
        String after = src.afterHarvestStage(cropKey);
        if (src.lifecycle(cropKey) == StardewCropLifecycle.REGROW
            && after != null && after.equalsIgnoreCase(stage)) {
            return build(RuntimeState.REGROWING, src, cropKey, stage, identity, resourceName, model);
        }

        // ── 生长中：已有成熟规则，当前阶段不是它 ──
        if (mature != null) {
            return build(RuntimeState.GROWING, src, cropKey, stage, identity, resourceName, model);
        }

        // ── 没有成熟规则：绝不猜，如实返回未知 ──
        return build(RuntimeState.UNKNOWN, src, cropKey, stage, identity, resourceName, model);
    }

    /** 组装结果（统一补齐中文名 / 成熟阶段 / 证据 / 生命周期） */
    private static RuntimeResult build(RuntimeState state, CropRuntimeSource src, String cropKey, String stage,
                                       String identity, String resourceName, String model) {
        if (cropKey == null) {
            return new RuntimeResult(state, null, null, stage, null, null, null, identity, resourceName, model);
        }
        String mature = src.matureStage(cropKey);
        return new RuntimeResult(state, cropKey, src.cropDisplayName(cropKey), stage,
            mature,
            mature == null ? null : src.ruleEvidence(cropKey),
            src.lifecycle(cropKey),
            identity, resourceName, model);
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  身份解析（与识别器共用同一套规则）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 资源身份路径（去掉命名空间前缀，如 {@code customcrops:tomato_stage_4} → {@code tomato_stage_4}） */
    public static String pathOf(String identity) {
        if (identity == null || identity.isBlank()) return "";
        int colon = identity.indexOf(':');
        return colon >= 0 ? identity.substring(colon + 1) : identity;
    }

    /**
     * 阶段化作物键：仅当身份路径含 {@code _stage_} 标记时返回其前缀。
     *
     * <p>没有该标记说明它结构上不是「阶段化作物流水线方块」，返回 null，
     * 绝不把任意自定义方块当成作物（旧实现会把整段路径当作物键）。</p>
     */
    public static String stageCropKey(String lowerPath) {
        int idx = lowerPath.indexOf("_stage_");
        return idx > 0 ? lowerPath.substring(0, idx) : null;
    }

    /** 阶段名：{@code tomato_stage_4} → {@code stage_4}；无阶段标记返回 null */
    public static String stageName(String lowerPath) {
        int idx = lowerPath.indexOf("_stage_");
        return idx >= 0 ? lowerPath.substring(idx + 1) : null;
    }

    /** 是否为特殊变种阶段（金色 / 巨大 / 变种）——只看阶段段，避免把名为 golden_xxx 的普通作物误判 */
    public static boolean isSpecialStage(String stage) {
        if (stage == null || stage.isBlank()) return false;
        String s = stage.toLowerCase(Locale.ROOT);
        return s.contains("golden") || s.contains("giant") || s.contains("gigantic") || s.contains("variation");
    }

    /**
     * 死亡态方块的作物键。
     *
     * <p>优先用 {@code _stage_} 前缀；死亡方块通常没有阶段段（如 {@code tomato_dead}），
     * 此时只在「去掉 dead 标记后的名字能被当前服务器资源索引确认」时才认，
     * 否则返回 null——绝不凭字符串猜作物。</p>
     */
    private static String deadCropKey(String lowerPath, String stage, CropRuntimeSource src) {
        if (stage != null) return stageCropKey(lowerPath);
        if (src.knownCrop(lowerPath)) return lowerPath;
        String stripped = lowerPath.replace("_dead", "").replace("dead_", "");
        return !stripped.isBlank() && !stripped.equals(lowerPath) && src.knownCrop(stripped) ? stripped : null;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  物品侧归属（.id 物品 用）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 物品模型 / 身份键所属的作物与角色（种子 / 成熟产物 / 特殊产物）；未命中返回 null */
    public static CropRoleRef roleOfItem(String itemModel, String identityKey) {
        return source().roleOfItem(itemModel, identityKey);
    }
}
