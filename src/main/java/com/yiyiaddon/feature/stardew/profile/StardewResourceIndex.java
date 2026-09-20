package com.yiyiaddon.feature.stardew.profile;

import com.yiyiaddon.feature.stardew.recognition.CropPotGroups;
import com.yiyiaddon.feature.stardew.recognition.PotGroup;
import com.yiyiaddon.feature.stardew.selector.StardewSelectorCategory;
import com.yiyiaddon.model.identity.ItemIdentity;
import com.yiyiaddon.platform.resource.BlockStateModelResolver;
import com.yiyiaddon.service.identity.IdentityService;
import net.minecraft.locale.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * 星露谷动态资源索引（资源发现 → 语义解析 → 逻辑对象聚合 → 去重）。
 *
 * <p>最高原则：先看客户端实际有什么，再决定能识别 / 能配置 / 能执行什么。V1 起索引的
 * 事实来源升级为「资源包扫描 + IdentityService 双源合并」，并严格限定在 {@code customcrops}
 * 命名空间（星露谷 = CustomCrops 盆栽系统）。{@code default}（农夫乐事）、
 * {@code customfurniture}（家具）等命名空间一律不参与分类。</p>
 *
 * <p>资源文件不等于用户选择项：扫描到的每个 Item Model / Block Model 只是 Raw Resource Entry，
 * 必须先按「语义家族 + 数字索引」聚合成 {@link StardewToolDefinition} 逻辑对象，再去重展示。</p>
 */
public final class StardewResourceIndex {

    /** 星露谷唯一合法命名空间（CustomCrops 盆栽系统） */
    private static final String STARDEW_NAMESPACE = "customcrops";

    /**
     * 物品形态后缀：{@code sprinkler_1_item} 是 {@code sprinkler_1} 这件物品在背包里的形态。
     *
     * <p>真机取证（jmy.seasonmc.xyz，2026-09-20）：{@code sprinkler_1} 是带 {@code elements} 的
     * 3D 方块模型（世界形态），{@code sprinkler_1_item} 才是 {@code item/generated} 的 2D 背包图。
     * 先前把前者当物品键，选择器里画的是方块模型，与背包里的真实物品对不上。</p>
     *
     * <p>公开给展示实体识别（{@code StardewPointActions}）复用：世界里的洒水器展示物挂的可能是两种
     * 形态中的任一个，两边都按同一条「剥后缀」规则归一才能对上。</p>
     */
    public static final String ITEM_FORM_SUFFIX = "_item";

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/stardew");

    private final IdentityService identityService;

    /** 全部作物定义（种子 ↔ 成熟产物 ↔ 变种关联） */
    private final List<CropDefinition> crops = new ArrayList<>();
    /** 非作物工具类逻辑对象（种植盆 / 肥料 / 药剂 / 水壶 / 洒水器），键为稳定逻辑键 */
    private final Map<String, StardewToolDefinition> toolDefinitions = new LinkedHashMap<>();
    /**
     * 逐作物的真实阶段清单（cropKey → 资源包里实际存在的 stage 名，如 {@code stage_1..stage_4}）。
     *
     * <p>来源是同一个资源扫描结果里带 {@code _stage_} 的条目（{@code models/block/crop/tomato/stage_3}、
     * {@code models/item/crops/tomato/stage_3}、{@code items/tomato_stage_3.json} 三种形态取并集），
     * 与 {@code BlockStateModelResolver} 派生语义身份的规则完全一致，因此
     * {@code .stardew 标记成熟 tomato <TAB>} 补全出来的阶段就是世界里真实存在的阶段，
     * 既不生成 {@code stage_1~stage_10} 这种编造值，也不会混入其它作物的阶段。</p>
     */
    private final Map<String, List<String>> stagesByCrop = new LinkedHashMap<>();

    /** 上次重建的失败原因（{@code null} = 正常）；半成品索引必须能被诊断看到 */
    private volatile String lastFailure;

    /**
     * 键别名 → 正式定义。
     *
     * <p><b>只参与 {@link #entryByKey} 解析，绝不进 {@link #entriesFor} 列表</b>（否则选择器里会出现
     * 重复条目）。用途：盆的两套命名（无序号 {@code dry_pot} 与序号 {@code dry_pot_1}）归一到同一个
     * 正式键后，另一套命名仍必须能解析——玩家在别的服务器上保存的选择键就是
     * {@code customcrops:dry_pot_1}，不能因为命名归一让既有选择失效。</p>
     */
    private final Map<String, StardewToolDefinition> toolAliases = new LinkedHashMap<>();

    public StardewResourceIndex(IdentityService identityService) {
        this.identityService = identityService;
    }

    /**
     * 全量重建索引。
     *
     * <p>流程：资源包扫描 → 仅保留 customcrops → 工具类逻辑对象聚合去重 → 叠加真实身份升级 →
     * 种子 / 产物 / 变种关联生成 {@link CropDefinition}。重建不覆盖身份库与资源包数据。</p>
     */
    public void rebuild() {
        crops.clear();
        toolDefinitions.clear();
        toolAliases.clear();
        stagesByCrop.clear();
        try {
            rebuildInternal();
            lastFailure = null;
        } catch (Throwable t) {
            // 半成品索引比没有索引更危险：某一步抛异常时 crops 会留下「恰好先建成功的那些」，
            // 表现成「这个服只识别出一种作物」却毫无报错。记录失败位置 + 已完成数量，
            // `.stardew 诊断` 直接给出，同时把完整堆栈写日志；绝不让它静默降级。
            StackTraceElement[] trace = t.getStackTrace();
            String where = trace.length == 0 ? "" : " @ " + trace[0];
            lastFailure = t.getClass().getSimpleName()
                + (t.getMessage() == null ? "" : ": " + t.getMessage()) + where
                + "（失败前已建作物 " + crops.size() + "）";
            LOGGER.warn("星露谷资源索引重建失败（失败前已建作物 {}）", crops.size(), t);
        }
    }

    /** 重建正文；任何步骤异常由 {@link #rebuild()} 统一记录，不在此吞掉 */
    private void rebuildInternal() {
        // ── 1. 资源包扫描（仅保留 customcrops 命名空间） ──
        List<StardewResourceScanner.ScannedModel> scanned = StardewResourceScanner.scan();
        List<StardewResourceScanner.ScannedModel> stardewModels = new ArrayList<>();
        Map<String, StardewResourceScanner.ScannedModel> scannedByName = new LinkedHashMap<>();
        for (StardewResourceScanner.ScannedModel model : scanned) {
            if (!STARDEW_NAMESPACE.equals(namespaceOf(model.modelId()))) continue;
            stardewModels.add(model);
            scannedByName.putIfAbsent(model.modelName(), model);
            // 带 _stage_ 的资源名即真实存在的作物阶段（与语义身份派生规则一致）。
            // 三种资源都要看：部分服务器阶段定义在 models/item/ 下、甚至只有 items/<作物>_stage_N.json
            // 物品定义（世界模型被混淆成 UUID），只看 block 模型会得到「0 种作物有真实阶段」。
            collectStage(model, stagesByCrop);
        }
        stagesByCrop.replaceAll((key, value) -> normalizeStages(value));

        // ── 2. 身份映射（同样仅保留 customcrops） ──
        Map<String, String> identityKeyByModelName = new LinkedHashMap<>();
        Map<String, ItemIdentity> identityByModel = new LinkedHashMap<>();
        for (ItemIdentity id : identityService.allItems()) {
            String model = id.itemModel() != null && !id.itemModel().isBlank()
                ? id.itemModel() : id.customLogicId();
            if (!STARDEW_NAMESPACE.equals(namespaceOf(model))) continue;
            String modelName = modelNameOf(model);
            if (modelName == null) continue;
            identityKeyByModelName.putIfAbsent(modelName, id.identityKey());
            identityByModel.putIfAbsent(model, id);
        }

        // ── 3. 工具类逻辑对象聚合（资源包 + 身份双源） ──
        toolDefinitions.putAll(buildToolDefinitions(stardewModels, identityByModel));

        // 盆的两套命名并存：无序号 dry_pot 与序号 dry_pot_1 是同一个普通盆，canonical 已归一到
        // dry_pot_-1，这里把另一套命名登记成别名——玩家在其它服务器上存下的选择键
        //（long.kkwmc.cn 存的就是 customcrops:dry_pot_1）必须继续有效，绝不能因命名归一被清掉。
        // 别名只参与 entryByKey 解析，不进列表，因此选择器里不会出现重复条目。
        for (StardewToolDefinition def : toolDefinitions.values()) {
            if (def instanceof PotDefinition pot && pot.potIndex() == -1) {
                toolAliases.put("customcrops:dry_pot_1", def);
            }
        }

        // ── 4. 种子 → 产物 → 变种关联 ──
        buildCrops(scannedByName, identityKeyByModelName);

        // ── 5. 作物 × 盆型分组（从资源包声明的维度限制读出） ──
        installCropPotGroups();
    }

    /**
     * 从资源包语言文件读出「这种作物只能种在哪个维度」，写进 {@link CropPotGroups}。
     *
     * <p><b>键在哪：</b>服务端插件把限制写成了失败原因，而不是作物 json 的字段：</p>
     *
     * <pre>plugin.customcrops.crops.&lt;作物键&gt;.not_met_requirement.message
     *   = [X] 你只能在下界维度种植它 / [X] 你只能在末地维度种植它</pre>
     *
     * <p><b>为什么必须走这条：</b>这是服务器自己的规则，比手写攻略准；而且末地那两种作物
     * （{@code virelia} 堇幽果 / {@code virelo} 幽碧兰）以前在攻略里查不到，选择器上一直没有
     * 「末地」字眼（实机反馈）。整表替换，换包 / 换服不会残留旧声明。</p>
     */
    private void installCropPotGroups() {
        Language language = Language.getInstance();
        Map<String, PotGroup> declared = new LinkedHashMap<>();
        if (language != null) {
            for (CropDefinition crop : crops) {
                PotGroup group = declaredGroup(language, crop.cropKey());
                if (group != PotGroup.NORMAL) declared.put(crop.cropKey(), group);
            }
        }
        CropPotGroups.installPackGroups(declared);
    }

    /** 单种作物的资源包声明；没有声明、或声明里看不出维度时按通用处理（绝不猜） */
    private static PotGroup declaredGroup(Language language, String cropKey) {
        String key = "plugin.customcrops.crops." + cropKey + ".not_met_requirement.message";
        if (!language.has(key)) return PotGroup.NORMAL;
        String message = language.getOrDefault(key);
        if (message == null) return PotGroup.NORMAL;
        return dimensionIn(message);
    }

    /**
     * 从失败原因里认出维度。
     *
     * <p>中文包按服务器原文匹配；再留一套英文兜底——{@link Language} 装的是<b>当前语言</b>的语言文件，
     * 客户端切成英文时拿到的是 {@code en_us} 那份，只认中文会静默失效（分组全变通用，且没有任何报错）。</p>
     */
    private static PotGroup dimensionIn(String message) {
        String lower = message.toLowerCase(Locale.ROOT);
        if (message.contains("下界") || lower.contains("nether")) return PotGroup.NETHER;
        if (message.contains("末地") || lower.contains("the end") || lower.contains("end dimension")) {
            return PotGroup.END;
        }
        return PotGroup.NORMAL;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  工具逻辑对象聚合
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 聚合中间态：同一逻辑对象下的多个原始资源 / 身份证据 */
    private static final class Acc {
        final StardewSelectorCategory category;
        final String canonical;
        final int index;
        String itemModel;
        String blockModel;
        String dryModel;
        String wetModel;
        String identityKey;
        String boundName;
        String scannedName;
        RuleEvidence evidence = RuleEvidence.CANDIDATE;
        String source = "资源包扫描";

        Acc(StardewSelectorCategory category, String canonical, int index) {
            this.category = category;
            this.canonical = canonical;
            this.index = index;
        }
    }

    /** 从资源包模型 + 真实身份聚合出工具逻辑对象 */
    private static Map<String, StardewToolDefinition> buildToolDefinitions(
        List<StardewResourceScanner.ScannedModel> models, Map<String, ItemIdentity> identityByModel) {

        Map<String, Acc> accs = new LinkedHashMap<>();

        // 资源包模型
        for (StardewResourceScanner.ScannedModel model : models) {
            StardewSelectorCategory cat = StardewSelectorCategory.classify(
                namespaceOf(model.modelId()), model.modelName());
            if (cat == null || cat == StardewSelectorCategory.CROP) continue;
            // 派发表来源的"零件模型"（真机取证：某服把 sprinkler_1_item 与 sprinkler_1 一起派发）
            // 不构成本编号家族里的第二个对象，否则会聚合出 sprinkler_-1 这种伪条目
            if (model.dispatch() && !hasLegalSerial(cat, model.modelName())) continue;
            Acc acc = accFor(accs, cat, model.modelName());
            acc.scannedName = firstNonBlank(acc.scannedName, model.displayName());
            feedModel(acc, cat, model);
        }

        // 真实身份（升级证据 / 名称 / 身份键 / 模型）
        for (Map.Entry<String, ItemIdentity> e : identityByModel.entrySet()) {
            ItemIdentity id = e.getValue();
            String model = id.itemModel() != null && !id.itemModel().isBlank()
                ? id.itemModel() : id.customLogicId();
            String name = modelNameOf(model);
            StardewSelectorCategory cat = StardewSelectorCategory.classify(namespaceOf(model), name);
            if (cat == null || cat == StardewSelectorCategory.CROP) continue;
            Acc acc = accFor(accs, cat, name);
            feedIdentity(acc, cat, id);
        }

        // 物化为逻辑定义
        Map<String, StardewToolDefinition> result = new LinkedHashMap<>();
        for (Acc acc : accs.values()) {
            StardewToolDefinition def = materialize(acc);
            if (def != null) result.put(def.key(), def);
        }
        return result;
    }

    /** 取（或创建）某类别 + 逻辑名的聚合槽 */
    private static Acc accFor(Map<String, Acc> accs, StardewSelectorCategory cat, String name) {
        String canonical = canonicalOf(cat, name);
        String key = cat.name() + ":" + canonical;
        return accs.computeIfAbsent(key, k -> new Acc(cat, canonical, numericSuffix(lastSegment(canonical))));
    }

    /**
     * 把资源包模型回填进聚合槽。
     *
     * <p><b>关键约束（26.1.2 真实管线）：</b>{@code itemModel} 只能是
     * {@code items/<id>.json}（{@link StardewResourceScanner.Kind#ITEM_DEF}）的键，
     * 因为那才是 {@code DataComponents.ITEM_MODEL} 的合法取值、也才是
     * {@code ModelManager.getItemModel} 能查到的键。{@code models/item/**} 的模型引用
     * 与 {@code models/block/**} 的方块模型键只作世界识别与排查，绝不写进 itemModel，
     * 否则必然渲染黑紫。</p>
     *
     * <p><b>世界识别模型两个目录都要收：</b>部分服务器把盆 / 洒水器 / 温室玻璃的世界模型放在
     * {@code models/item/} 下，再让方块 blockstates 直接指向它——世界身份就是从那个 item 模型派生的
     * （如 {@code customcrops:item/basics/dry_pot} → 身份 {@code customcrops:dry_pot}）。
     * 若只收 {@code models/block/**}，索引里的盆就没有任何可比模型，
     * 表现为「地里 普通种植盆 ×8，已选 普通种植盆」左右同名却判定不匹配（真机事故：moexd 启动自检）。
     * item 模型只做兜底：已经有 block 模型时不覆盖。</p>
     */
    private static void feedModel(Acc acc, StardewSelectorCategory cat, StardewResourceScanner.ScannedModel model) {
        // items/ 物品定义：唯一可渲染进 GUI 的模型键，且不含世界语义，登记完即返回
        if (model.itemDef()) {
            if (acc.itemModel == null) acc.itemModel = model.modelId();
            return;
        }

        // 派发表给出的自定义模型：旧布局（ItemsAdder 等）里没有 items/ 定义，模型键由"基础物品 + 阈值"
        // 反查出预览栈（见 StardewPreview），因此同样可作物品键。登记后**不返回**：这类模型路径本身就是
        // 世界识别模型（盆 dry/wet、洒水器、温室玻璃的 blockstates 直接指向它），下面按末段名分流补齐。
        if (model.dispatch() && (isItemForm(model.modelName()) || acc.itemModel == null)) {
            acc.itemModel = model.modelId();
        }

        // 背包形态（`X_item`）是 2D 物品图，不是世界模型：不参与世界识别
        if (model.dispatch() && isItemForm(model.modelName())) return;

        String last = lastSegment(model.modelName());
        if (model.block()) {
            switch (cat) {
                case POT -> {
                    if (last.startsWith("dry_pot")) acc.dryModel = model.modelId();
                    else if (last.startsWith("wet_pot")) acc.wetModel = model.modelId();
                }
                case SPRINKLER, SHELTER -> acc.blockModel = model.modelId();
                default -> { /* 水壶 / 肥料 / 药剂没有方块世界模型 */ }
            }
            return;
        }

        // models/item/**：blockstates 可能直接指向它，因此同样参与世界识别；只在不覆盖已有值时补位
        switch (cat) {
            case POT -> {
                if (last.startsWith("dry_pot")) {
                    if (acc.dryModel == null) acc.dryModel = model.modelId();
                } else if (last.startsWith("wet_pot")) {
                    if (acc.wetModel == null) acc.wetModel = model.modelId();
                }
            }
            case SPRINKLER, SHELTER -> {
                if (acc.blockModel == null) acc.blockModel = model.modelId();
            }
            default -> { /* 水壶 / 肥料 / 药剂没有世界模型 */ }
        }
    }

    /** 把真实身份回填进聚合槽（证据升级为 VERIFIED） */
    private static void feedIdentity(Acc acc, StardewSelectorCategory cat, ItemIdentity id) {
        if (acc.identityKey == null) acc.identityKey = id.identityKey();
        acc.boundName = firstNonBlank(acc.boundName, id.displayName());

        String idModel = id.itemModel();
        if (idModel != null && !idModel.isBlank()) {
            if (idModel.contains(":block/")) {
                // 身份指向 block 模型：作为世界识别模型补充（种植盆 / 洒水器）
                if (cat == StardewSelectorCategory.POT) {
                    String last = lastSegment(idModel);
                    if (last.startsWith("dry_pot")) acc.dryModel = idModel;
                    else if (last.startsWith("wet_pot")) acc.wetModel = idModel;
                } else if ((cat == StardewSelectorCategory.SPRINKLER || cat == StardewSelectorCategory.SHELTER)
                    && acc.blockModel == null) {
                    acc.blockModel = idModel;
                }
            } else if (acc.itemModel == null) {
                acc.itemModel = idModel;
            }
        }
        acc.evidence = RuleEvidence.VERIFIED;
        if (!acc.source.contains("ItemIdManager")) acc.source = acc.source + " + ItemIdManager";
    }

    /** 把聚合槽物化为具体逻辑定义 */
    private static StardewToolDefinition materialize(Acc acc) {
        StardewSelectorCategory cat = acc.category;
        // itemModel 只能是 items/<id>.json 的键（ITEM_MODEL 组件的合法取值）。
        // 方块世界模型（block/misc/dry_pot_1、block/sprinkler/sprinkler_1）绝不能当物品图标，
        // 它们只通过 PotDefinition/SprinklerDefinition 的 dry/wet/block 字段参与世界识别。
        String itemModel = acc.itemModel;
        if (itemModel == null) itemModel = deriveItemModel(cat, acc.index);
        String displayName = resolveDisplayName(acc);
        String key = toolKey(cat, acc.canonical, itemModel);

        return switch (cat) {
            case POT -> new PotDefinition(key, displayName, itemModel, acc.identityKey, acc.evidence,
                acc.source, acc.index, acc.dryModel, acc.wetModel);
            case WATERING_CAN -> new WateringCanDefinition(key, displayName, itemModel, acc.identityKey,
                acc.evidence, acc.source, acc.index);
            case SPRINKLER -> new SprinklerDefinition(key, displayName, itemModel, acc.identityKey,
                acc.evidence, acc.source, acc.index, acc.blockModel);
            case SHELTER -> new ShelterDefinition(key, displayName, itemModel, acc.identityKey,
                acc.evidence, acc.source, acc.blockModel);
            case FERTILIZER, POTION -> new SimpleToolDefinition(key, displayName, itemModel, acc.identityKey,
                acc.evidence, acc.source, cat);
            default -> null;
        };
    }

    /**
     * 逻辑名归一化：{@code dry_pot_1} / {@code wet_pot_1} / {@code misc/dry_pot_1} 一律归一到
     * {@code dry_pot_1}，{@code wateringcan/watering_can_1} 归一到 {@code watering_can_1}，
     * {@code sprinkler/sprinkler_1} 归一到 {@code sprinkler_1}。
     *
     * <p>公开静态：资源生命周期服务（{@link com.yiyiaddon.service.resourcepack.ResourceExtractionService}）
     * 与索引层共用同一套归一化规则，保证两边统计出的逻辑项数完全一致。</p>
     */
    public static String canonicalOf(StardewSelectorCategory cat, String name) {
        String last = lastSegment(name);
        int idx = numericSuffix(serialBase(last));
        return switch (cat) {
            // 种植盆：无序号与序号 1 是同一个盆型（1 就是普通盆）。资源包常两套命名都留着
            // （moexd：blockstates 指向 item/basics/dry_pot，同时存在未被引用的 block/misc/dry_pot_1），
            // 不归一就会出现两个都叫「普通种植盆」的条目，其中一个没有 items/ 物品定义、图标缺失。
            // 归到 -1（而不是 1）：既有玩家选择（customcrops:dry_pot_-1）继续有效，不会因合并而失效。
            case POT -> "dry_pot_" + (idx == 1 ? -1 : idx);
            case WATERING_CAN -> "watering_can_" + idx;
            case SPRINKLER -> "sprinkler_" + idx;
            default -> last; // 肥料 / 药剂：末段名本身即逻辑名（含家族前缀）
        };
    }

    /**
     * 一条扫描结果是否构成一个星露谷逻辑对象；构成则返回它的类别，否则返回 {@code null}。
     *
     * <p><b>为什么必须公开且唯一：</b>资源生命周期服务（{@link com.yiyiaddon.service.resourcepack.ResourceExtractionService}）
     * 判定"是否已就绪"用的就是"逻辑对象数"，而索引层决定"选择器里有哪些条目"。两边若各写一份判据，
     * 就会出现"服务说已就绪、面板里却是空的"（第 169 条：同源逻辑禁止留两份）。</p>
     *
     * <p>口径：必须是可建逻辑对象的物品条目（{@code items/} 物品定义，或资源包派发表给出的自定义模型）、
     * 属于 {@code customcrops} 命名空间、能被分类器归类；派发表来源还必须是编号家族里的合法序号
     * （见 {@link #hasLegalSerial(StardewSelectorCategory, String)}）。</p>
     */
    public static StardewSelectorCategory logicalCategoryOf(StardewResourceScanner.ScannedModel model) {
        if (model == null || !model.logicalItem()) return null;
        if (!STARDEW_NAMESPACE.equals(namespaceOf(model.modelId()))) return null;
        StardewSelectorCategory category =
            StardewSelectorCategory.classify(STARDEW_NAMESPACE, model.modelName());
        if (category == null) return null;
        if (model.dispatch() && !hasLegalSerial(category, model.modelName())) return null;
        return category;
    }

    /**
     * 该逻辑名是否带合法序号（只对"按序号编号的家族"有意义）。
     *
     * <p>盆 / 水壶 / 洒水器按序号编号：无序号或非数字序号的名字（如 {@code water_effect}、
     * {@code stage_1}）不是这一家族的第二个对象，聚合它们只会得到 {@code sprinkler_-1}
     * 这种伪条目。物品形态后缀 {@code _item} 先剥掉再取序号，因此
     * {@code sprinkler_1_item} 与 {@code sprinkler_1} 归同一个对象
     * （前者是背包形态、后者是世界形态，见 {@link #ITEM_FORM_SUFFIX}）。盆是例外：无序号与序号 1
     * 本就被归一到同一个普通盆（见 {@link #canonicalOf}）。</p>
     */
    private static boolean hasLegalSerial(StardewSelectorCategory category, String modelName) {
        if (category == StardewSelectorCategory.POT || category == StardewSelectorCategory.SHELTER) return true;
        if (category == StardewSelectorCategory.WATERING_CAN || category == StardewSelectorCategory.SPRINKLER) {
            return numericSuffix(serialBase(lastSegment(modelName))) >= 0;
        }
        return true;
    }

    /** 末段名去掉物品形态后缀：{@code sprinkler_1_item} → {@code sprinkler_1} */
    private static String serialBase(String last) {
        return last.endsWith(ITEM_FORM_SUFFIX)
            ? last.substring(0, last.length() - ITEM_FORM_SUFFIX.length())
            : last;
    }

    /** 末段名是否是物品形态（以 {@code _item} 结尾） */
    private static boolean isItemForm(String name) {
        return lastSegment(name).endsWith(ITEM_FORM_SUFFIX);
    }

    /**
     * 无 items/ 命中时的兜底模型键（按资源包命名约定推导，仍必须是 {@code items/} 键）。
     *
     * <p>只有 {@code items/<id>.json} 的键才是 {@code DataComponents.ITEM_MODEL} 的合法取值。
     * 本方法只用于「资源扫描未命中」的极端情况，且下游 {@code StardewPreview} 会再做存在性
     * 校验——不存在就显示可读缺失提示，绝不渲染黑紫。</p>
     */
    private static String deriveItemModel(StardewSelectorCategory cat, int index) {
        if (index < 0) return null;
        return switch (cat) {
            case POT -> "customcrops:dry_pot_" + index;
            case WATERING_CAN -> "customcrops:watering_can_" + index;
            case SPRINKLER -> "customcrops:sprinkler_" + index;
            default -> null;
        };
    }

    /** 生成稳定逻辑键（选择器持久化此值） */
    private static String toolKey(StardewSelectorCategory cat, String canonical, String itemModel) {
        return switch (cat) {
            case POT, WATERING_CAN, SPRINKLER -> "customcrops:" + canonical;
            default -> itemModel != null ? itemModel : "customcrops:item:" + canonical;
        };
    }

    /** 显示名优先级：绑定身份 → 资源语言文件 → 文档化兜底 → 逻辑名 */
    private static String resolveDisplayName(Acc acc) {
        if (firstNonBlank(acc.boundName, null) != null) return acc.boundName;
        String scanned = firstNonBlank(acc.scannedName, null);
        if (scanned != null && !scanned.equals(lastSegment(acc.canonical))) return scanned;
        String documented = documentedName(acc.category, acc.index);
        return documented != null ? documented : acc.canonical;
    }

    /**
     * 文档化兜底名（攻略记载级 DOCUMENTED，非硬编码业务规则）。
     *
     * <p>仅覆盖 CustomCrops 标准层级（花盆 / 水壶 / 洒水器 / 温室玻璃）；肥料 / 药剂的中文名一律来自
     * 绑定身份或资源语言文件，绝不在通用 Java 里写死 12 / 3 这种数量语义。</p>
     */
    private static String documentedName(StardewSelectorCategory cat, int index) {
        return switch (cat) {
            case POT -> switch (index) {
                case 1 -> "普通种植盆";
                case 2 -> "下界种植盆";
                case 3 -> "末地种植盆";
                default -> null;
            };
            case WATERING_CAN -> switch (index) {
                case 1 -> "铜制浇水壶";
                case 2 -> "钢制浇水壶";
                case 3 -> "黄金浇水壶";
                case 4 -> "铱制浇水壶";
                default -> null;
            };
            case SPRINKLER -> switch (index) {
                case 1 -> "初级洒水器";
                case 2 -> "优质洒水器";
                case 3 -> "高级洒水器";
                case 4 -> "现代洒水器";
                default -> null;
            };
            // 温室玻璃只有一种，与等级无关（index 无意义），中文名以资源语言文件为准，这里只做兜底
            case SHELTER -> "温室玻璃";
            default -> null;
        };
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  作物关联
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 从种子集合反推作物定义（种子 ↔ 产物 ↔ 变种） */
    private void buildCrops(Map<String, StardewResourceScanner.ScannedModel> scannedByName,
                            Map<String, String> identityKeyByModelName) {
        Set<String> seedModelNames = new LinkedHashSet<>();
        for (String modelName : scannedByName.keySet()) {
            if (isSeedName(modelName)) seedModelNames.add(modelName);
        }
        // 身份里也允许出现种子（玩家 .id 过但资源包扫描未命中时仍能建档）
        for (String modelName : identityKeyByModelName.keySet()) {
            if (isSeedName(modelName)) seedModelNames.add(modelName);
        }

        for (String seedModelName : seedModelNames) {
            String stem = seedModelName.substring(0, seedModelName.length() - "_seeds".length());
            StardewResourceScanner.ScannedModel seedScan = scannedByName.get(seedModelName);
            String seedModel = seedScan != null ? seedScan.modelId() : modelIdFromName(seedModelName);
            String seedName = seedScan != null ? seedScan.displayName() : seedModelName;
            String seedKey = identityKeyByModelName.get(seedModelName);

            List<String> produceModels = new ArrayList<>();
            List<String> produceNames = new ArrayList<>();
            List<String> produceKeys = new ArrayList<>();
            List<String> variantModels = new ArrayList<>();
            List<String> variantNames = new ArrayList<>();
            List<String> variantKeys = new ArrayList<>();

            String produceName = addProduce(scannedByName, identityKeyByModelName, stem,
                produceModels, produceNames, produceKeys, false);
            addProduce(scannedByName, identityKeyByModelName, stem + "_silver_star",
                produceModels, produceNames, produceKeys, false);
            addProduce(scannedByName, identityKeyByModelName, stem + "_golden_star",
                produceModels, produceNames, produceKeys, false);

            addProduce(scannedByName, identityKeyByModelName, "golden_" + stem,
                variantModels, variantNames, variantKeys, true);
            addProduce(scannedByName, identityKeyByModelName, "giant_" + stem,
                variantModels, variantNames, variantKeys, true);
            addProduce(scannedByName, identityKeyByModelName, "gigantic_" + stem,
                variantModels, variantNames, variantKeys, true);
            addProduce(scannedByName, identityKeyByModelName, stem + "_variation",
                variantModels, variantNames, variantKeys, true);

            // 作物名优先级：种子名去后缀 → 资源包里的成熟产物名 → 从服务器下发物品名学到的 →
            // 文档化标准作物名 → 技术 stem。
            // 成熟产物名绝不能顶掉种子给出的作物名（redpacket：作物=摇钱树、种子=摇钱树种子、
            // 产物=红包，三者必须始终分离），只在种子拿不出名字时兜底。
            String chinese = cropName(seedName, produceName, stem);
            String evidence = (!produceModels.isEmpty() || !produceKeys.isEmpty())
                ? RuleEvidence.VERIFIED.displayName() : RuleEvidence.CANDIDATE.displayName();

            crops.add(new CropDefinition(stem, chinese, seedKey,
                List.copyOf(produceKeys), List.copyOf(variantKeys), evidence,
                seedModel, seedName, List.copyOf(produceModels), List.copyOf(produceNames),
                List.copyOf(variantModels), List.copyOf(variantNames)));
        }
    }

    /**
     * 资源名是否为一个「种子」逻辑对象（作物建档的唯一入口）。
     *
     * <p><b>必须排除带子目录的名字：</b>{@code models/item/crops/<目录>/<作物>_seeds.json} 这类条目
     * 只是那颗种子物品的<b>外观模型</b>，它的逻辑名带子目录（{@code crops/lentinus/…}）；
     * 若也当成种子，去掉 {@code _seeds} 后缀就会得到一个带斜杠的假作物键，
     * 于是「56 种作物的服务器」在索引里凭空多出几十种本服不存在的作物
     * （真机事故：moexd 索引显示作物 105，资源包里实际只有 56 个种子）。
     * 物品定义 {@code items/<作物>_seeds.json} 的逻辑名一定扁平，两者天然可分。</p>
     */
    private static boolean isSeedName(String modelName) {
        return modelName != null && !modelName.contains("/") && modelName.endsWith("_seeds");
    }

    /**
     * 作物中文名解析（索引层唯一入口）。
     *
     * <p>优先级：种子名去后缀 → 资源包里的成熟产物名 → {@link StardewCropNameStore 从服务器下发
     * 物品名学到的名字} → 技术 stem。任何一步拿不出名字都继续往下找，最后仍没有就如实退技术键——
     * 绝不编一个名字出来。</p>
     */
    private static String cropName(String seedName, String produceName, String stem) {
        String fromSeed = cropNameFromSeed(seedName, stem);
        if (fromSeed != null) return fromSeed;
        String fromProduce = meaningfulName(produceName, stem);
        if (fromProduce != null) return fromProduce;
        String learned = StardewCropNameStore.nameOf(stem);
        if (learned != null && !learned.isBlank()) return learned;
        String documented = documentedCropName(stem);
        return documented != null ? documented : stem;
    }

    /**
     * 文档化标准作物名（CustomCrops 本体自带作物集，插件记载级 DOCUMENTED）。
     *
     * <p><b>优先级最低</b>：资源包语言文件、种子名、产物名、以及从服务器下发物品名学到的名字全部先于它，
     * 因此任何服务器的改名都会盖掉这里——它只填补「该服资源包没有 lang 目录、又还没观察到实物」的空档
     * （真机场景：jmy.seasonmc.xyz 的包 {@code lang/} 有 0 个文件，刚进服时作物只有技术键，
     * 选择器里显示 {@code corn} / {@code corn种子}）。</p>
     *
     * <p>{@code redpacket}（红包作物）在各服叫法差异最大（摇钱树 / 红包 / 招财树…），但玩家侧最常
     * 见到的作物名就是「摇钱树」，且它同属 CustomCrops 本体作物，因此一并收进来；
     * 真名一旦被观察到就会立刻覆盖这里。其余服务器自建作物（本服不存在的 id）不写死，
     * 继续靠学名——绝不给不认识的作物编名字。</p>
     */
    private static String documentedCropName(String stem) {
        return switch (stem.toLowerCase(Locale.ROOT)) {
            case "cabbage" -> "卷心菜";
            case "chinese_cabbage" -> "大白菜";
            case "corn" -> "玉米";
            case "eggplant" -> "茄子";
            case "garlic" -> "大蒜";
            case "grape" -> "葡萄";
            case "hop" -> "啤酒花";
            case "pepper" -> "辣椒";
            case "pineapple" -> "菠萝";
            case "pitaya" -> "火龙果";
            case "redpacket" -> "摇钱树";
            case "tomato" -> "番茄";
            default -> null;
        };
    }

    /**
     * 从真实种子名派生作物名；只剥离明确种子后缀。
     *
     * <p>拿不到名字（没有翻译、或名字其实就是技术键）返回 {@code null}，由调用方继续找其它证据。</p>
     */
    private static String cropNameFromSeed(String seedName, String stem) {
        if (seedName == null || seedName.isBlank()) return null;
        String value = seedName.trim();
        if (value.endsWith("种子")) {
            String crop = value.substring(0, value.length() - 2).trim();
            if (!crop.isBlank()) return meaningfulName(crop, stem);
        }
        String lower = value.toLowerCase(Locale.ROOT);
        if (lower.endsWith(" seeds")) return meaningfulName(value.substring(0, value.length() - 6), stem);
        if (lower.endsWith(" seed")) return meaningfulName(value.substring(0, value.length() - 5), stem);
        if (lower.endsWith("_seeds")) return meaningfulName(value.substring(0, value.length() - 6), stem);
        return meaningfulName(value, stem);
    }

    /** 过滤掉「名字其实还是技术键」的伪翻译（无语言文件时 label() 回退的就是资源原始末段名） */
    private static String meaningfulName(String name, String stem) {
        if (name == null || name.isBlank()) return null;
        String value = name.trim();
        if (value.equalsIgnoreCase(stem) || value.equalsIgnoreCase(stem + "_seeds")) return null;
        return value;
    }

    /** 把某个逻辑名（产物或变种）关联进作物定义；资源与身份都命中才写入；返回该条目的展示名 */
    private static String addProduce(Map<String, StardewResourceScanner.ScannedModel> scannedByName,
                                     Map<String, String> identityByModelName, String modelName,
                                     List<String> models, List<String> names, List<String> keys,
                                     boolean variant) {
        StardewResourceScanner.ScannedModel scan = scannedByName.get(modelName);
        String identityKey = identityByModelName.get(modelName);
        if (scan == null && identityKey == null) return null;
        String label = null;
        if (scan != null) {
            models.add(scan.modelId());
            // 名称一律用 label()（无翻译时退原始末段名）：**绝不能把 null 放进列表**——
            // 下面 List.copyOf 遇到 null 会抛 NPE，一处翻译缺失就能让整个索引构建中断，
            // 表现为「这个服 56 种作物一种都没建成」（真机事故：资源包物品无语言文件）。
            label = scan.label();
            names.add(label);
        }
        // 同理：身份键可能为 null（未经 .id 的物品），放进去同样是 List.copyOf 的 NPE
        if (identityKey != null) keys.add(identityKey);
        return label;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  逐作物真实阶段（资源扫描结果）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 从一条资源收集一个真实阶段。
     *
     * <p><b>必须复用语义身份派生规则</b>（{@code BlockStateModelResolver.deriveIdentityPath}）：
     * 先得到身份路径，再按 {@code _stage_} 切出作物键与阶段名。若这里另写一套「去掉首段后全拼」，
     * 遇到 {@code item/crops/lentinus/lentinus_edodes_stage_3} 会得到作物键
     * {@code lentinus_lentinus_edodes}，而世界识别链给的是 {@code lentinus_edodes}——
     * 阶段清单与作物键对不上，表现为「这种作物没有真实阶段」。两处同源才可能对得上。</p>
     *
     * <p><b>输入必须用 {@code modelId}，不能用 {@code modelName}（2026-09-21 修正）：</b>
     * {@code modelName} 是「扁平逻辑名」，派发来源（{@code Kind.DISPATCH}）里它被刻意收敛成末段
     * （{@code customcrops:item/crops/chinese_cabbage/stage_1} → {@code stage_1}），于是这里拼出
     * {@code item/stage_1}、拿不到作物名，按规则如实返回 {@code null}，<b>整条阶段被丢掉</b>。
     * 真机表现：包里的派发表明明有 {@code chinese_cabbage/stage_1..4}，诊断却报「2 种作物有真实阶段」，
     * 其余 11 种作物在「标记成熟」时全被「找不到该作物的阶段证据」挡下。{@code modelId} 在三种来源里
     * 都是完整模型键（{@code <ns>:item/…}、{@code <ns>:block/…}、派发的 {@code customcrops:item/…}），
     * 派生结果与旧写法对前两种来源逐字相同，只补回派发来源丢掉的父目录。</p>
     *
     * <p>三种资源形态都支持：{@code models/block/crop/tomato/stage_3}、
     * {@code models/item/crops/tomato/stage_3}、{@code items/tomato_stage_3.json}。
     * 不含 {@code _stage_} 的资源一律跳过。</p>
     */
    private static void collectStage(StardewResourceScanner.ScannedModel source, Map<String, List<String>> out) {
        String identityPath = BlockStateModelResolver.deriveIdentityPath(source.modelId());
        if (identityPath == null) return;
        String lower = identityPath.toLowerCase(Locale.ROOT);
        int idx = lower.indexOf("_stage_");
        if (idx <= 0) return;
        String cropKey = lower.substring(0, idx);
        String stage = lower.substring(idx + 1);
        List<String> stages = out.computeIfAbsent(cropKey, key -> new ArrayList<>());
        if (!stages.contains(stage)) stages.add(stage);
    }

    /**
     * 阶段排序：数字阶段按编号升序在前（{@code stage_1 < stage_2 < stage_10}），
     * 非数字阶段（特殊变种）按字母序排在其后。
     */
    private static List<String> normalizeStages(List<String> stages) {
        List<String> sorted = new ArrayList<>(stages);
        sorted.sort((a, b) -> {
            int na = stageNumber(a);
            int nb = stageNumber(b);
            if (na >= 0 && nb >= 0) return Integer.compare(na, nb);
            if (na >= 0) return -1;
            if (nb >= 0) return 1;
            return a.compareTo(b);
        });
        return List.copyOf(sorted);
    }

    /** {@code stage_4} → 4；非纯数字阶段返回 -1 */
    private static int stageNumber(String stage) {
        int idx = stage.lastIndexOf('_');
        if (idx < 0 || idx == stage.length() - 1) return -1;
        try {
            return Integer.parseInt(stage.substring(idx + 1));
        } catch (NumberFormatException ignored) {
            return -1;
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  命名工具
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /** 提取模型引用的命名空间（如 {@code customcrops:item/x} → {@code customcrops}） */
    public static String namespaceOf(String model) {
        if (model == null || model.isBlank()) return null;
        int colon = model.indexOf(':');
        return colon >= 0 ? model.substring(0, colon) : null;
    }

    /** 提取身份的可归类逻辑名（剥离命名空间 + item/block/model 前缀 + 末段） */
    private static String modelNameOf(String model) {
        if (model == null || model.isBlank()) return null;
        String path = model;
        int colon = model.indexOf(':');
        if (colon >= 0) path = model.substring(colon + 1);
        path = path.replaceFirst("^(item|block|model|models)/", "");
        int lastSlash = path.lastIndexOf('/');
        if (lastSlash >= 0) path = path.substring(lastSlash + 1);
        return path.isBlank() ? null : path;
    }

    /** 提取身份的可归类末段名（公开静态，供分类器与索引共用） */
    public static String normalizeName(ItemIdentity id) {
        String raw = null;
        if (id.customLogicId() != null && !id.customLogicId().isBlank()) {
            raw = id.customLogicId();
        } else if (id.itemModel() != null && !id.itemModel().isBlank()) {
            raw = id.itemModel();
        } else if (!id.itemId().startsWith("minecraft:")) {
            raw = id.itemId();
        }
        if (raw == null) return null;
        return modelNameOf(raw);
    }

    /** 末段名（去掉子目录前缀） */
    private static String lastSegment(String name) {
        if (name == null) return "";
        int slash = name.lastIndexOf('/');
        return slash >= 0 ? name.substring(slash + 1) : name;
    }

    /** 提取末尾数字后缀（watering_can_1 → 1；无数字返回 -1） */
    private static int numericSuffix(String name) {
        if (name == null || name.isEmpty()) return -1;
        int i = name.length() - 1;
        while (i >= 0 && Character.isDigit(name.charAt(i))) i--;
        if (i < name.length() - 1) {
            try {
                return Integer.parseInt(name.substring(i + 1));
            } catch (NumberFormatException ignored) {
                return -1;
            }
        }
        return -1;
    }

    /** 首个非空字符串 */
    private static String firstNonBlank(String a, String b) {
        if (a != null && !a.isBlank()) return a;
        if (b != null && !b.isBlank()) return b;
        return null;
    }

    /**
     * 无资源包命中、仅身份命中的种子：按命名约定回填一个 {@code items/} 键。
     *
     * <p>必须回填 {@code items/} 键（{@code DataComponents.ITEM_MODEL} 的合法取值），
     * 不能回填 {@code item/...} 这种模型引用——那一定查不到模型，只会渲染黑紫。</p>
     */
    private static String modelIdFromName(String modelName) {
        return "customcrops:" + modelName;
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  只读访问器
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    public List<CropDefinition> crops() {
        return List.copyOf(crops);
    }

    /** 上次重建的失败原因；{@code null} 表示构建完整。供 {@code .stardew 诊断} 与状态面板使用 */
    public String lastFailure() {
        return lastFailure;
    }

    public CropDefinition cropByKey(String cropKey) {
        for (CropDefinition crop : crops) {
            if (crop.cropKey().equals(cropKey)) return crop;
        }
        return null;
    }

    /**
     * 某作物在当前服务器资源包里真实存在的阶段（如 {@code [stage_1, stage_2, stage_3, stage_4]}）。
     *
     * <p>数据来自资源扫描（方块模型 / 物品模型 / 物品定义三种形态取并集），不是编造的区间：
     * 资源包没有的阶段绝不会出现在这里，也不会混入其它作物的阶段。未发现返回空列表。</p>
     */
    public List<String> stagesOf(String cropKey) {
        if (cropKey == null) return List.of();
        List<String> stages = stagesByCrop.get(cropKey);
        return stages == null ? List.of() : stages;
    }

    /** 某类别的逻辑可选项（种植盆 / 肥料 / 药剂 / 水壶 / 洒水器；作物请用 {@link #crops()}） */
    public List<StardewToolDefinition> entriesFor(StardewSelectorCategory category) {
        if (category == null || category == StardewSelectorCategory.CROP) return List.of();
        List<StardewToolDefinition> result = new ArrayList<>();
        for (StardewToolDefinition entry : toolDefinitions.values()) {
            if (entry.category() == category) result.add(entry);
        }
        return result;
    }

    /** 按稳定键查工具逻辑对象；正式键查不到时再查别名（盆的两套命名），未找到返回 null */
    public StardewToolDefinition entryByKey(String key) {
        if (key == null) return null;
        StardewToolDefinition def = toolDefinitions.get(key);
        return def != null ? def : toolAliases.get(key);
    }

    /**
     * 盆型键 → 盆型组（普通 / 下界 / 末地）。
     *
     * <p>查不到定义时按普通盆处理：普通盆是唯一不限维度、走水壶链路的一种，
     * 退到它不会误把未知盆当成下界盆去要求岩浆。</p>
     */
    public PotGroup potGroupOf(String potKey) {
        return entryByKey(potKey) instanceof PotDefinition pot ? PotGroup.ofIndex(pot.potIndex()) : PotGroup.NORMAL;
    }

    /**
     * 一组已选盆型键所属的盆型组（取第一个能识别的）。
     *
     * <p>盆型是互斥单选，正常情况下这里只会有一个；取第一个是为了兼容「玩家在互斥校验
     * 上线之前就已经多选过」的旧存档，不让它退化成普通盆而把下界盆当普通盆处理。</p>
     *
     * <p>绑定校验 / 启动自检 / 选择器共用这一份实现，避免三处各写一遍而口径漂移。</p>
     */
    public PotGroup potGroupOfSelected(List<String> selectedPotKeys) {
        if (selectedPotKeys != null) {
            for (String key : selectedPotKeys) {
                if (entryByKey(key) instanceof PotDefinition pot) return PotGroup.ofIndex(pot.potIndex());
            }
        }
        return PotGroup.NORMAL;
    }

    /**
     * 清空索引（切换服务器 / 断线时调用）。
     *
     * <p>作用是「立刻停止展示上一服务器的候选」：下一个服务器的资源 READY 之前，
     * 选择器必须是空的，而不是残留旧服数据。</p>
     */
    public void clear() {
        crops.clear();
        toolDefinitions.clear();
        toolAliases.clear();
        stagesByCrop.clear();
    }

    /** 索引是否为空（无作物且无工具逻辑对象） */
    public boolean isEmpty() {
        return crops.isEmpty() && toolDefinitions.isEmpty();
    }

    /** 日志用：作物候选计数 */
    public int scannedCropCount() {
        return crops.size();
    }
}
