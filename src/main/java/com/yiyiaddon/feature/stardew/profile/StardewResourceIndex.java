package com.yiyiaddon.feature.stardew.profile;

import com.yiyiaddon.feature.stardew.recognition.CropPotGroups;
import com.yiyiaddon.feature.stardew.recognition.PotGroup;
import com.yiyiaddon.feature.stardew.selector.StardewSelectorCategory;
import com.yiyiaddon.model.identity.ItemIdentity;
import com.yiyiaddon.service.identity.IdentityService;
import net.minecraft.locale.Language;

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

    private final IdentityService identityService;

    /** 全部作物定义（种子 ↔ 成熟产物 ↔ 变种关联） */
    private final List<CropDefinition> crops = new ArrayList<>();
    /** 非作物工具类逻辑对象（种植盆 / 肥料 / 药剂 / 水壶 / 洒水器），键为稳定逻辑键 */
    private final Map<String, StardewToolDefinition> toolDefinitions = new LinkedHashMap<>();
    /**
     * 逐作物的真实阶段清单（cropKey → 资源包里实际存在的 stage 名，如 {@code stage_1..stage_4}）。
     *
     * <p>来源是同一个资源扫描结果里的方块模型（{@code models/block/crop/tomato/stage_3}），
     * 与 {@code BlockStateModelResolver} 派生语义身份的规则完全一致，因此
     * {@code .stardew 标记成熟 tomato <TAB>} 补全出来的阶段就是世界里真实存在的阶段，
     * 既不生成 {@code stage_1~stage_10} 这种编造值，也不会混入其它作物的阶段。</p>
     */
    private final Map<String, List<String>> stagesByCrop = new LinkedHashMap<>();

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
        stagesByCrop.clear();

        // ── 1. 资源包扫描（仅保留 customcrops 命名空间） ──
        List<StardewResourceScanner.ScannedModel> scanned = StardewResourceScanner.scan();
        List<StardewResourceScanner.ScannedModel> stardewModels = new ArrayList<>();
        Map<String, StardewResourceScanner.ScannedModel> scannedByName = new LinkedHashMap<>();
        for (StardewResourceScanner.ScannedModel model : scanned) {
            if (!STARDEW_NAMESPACE.equals(namespaceOf(model.modelId()))) continue;
            stardewModels.add(model);
            scannedByName.putIfAbsent(model.modelName(), model);
            // 方块模型里带 _stage_ 的即真实存在的作物阶段（与语义身份派生规则一致）
            if (model.kind() == StardewResourceScanner.Kind.BLOCK_MODEL) {
                collectStage(model.modelName(), stagesByCrop);
            }
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
     */
    private static void feedModel(Acc acc, StardewSelectorCategory cat, StardewResourceScanner.ScannedModel model) {
        // items/ 物品定义：唯一可渲染进 GUI 的模型键
        if (model.itemDef()) {
            if (acc.itemModel == null) acc.itemModel = model.modelId();
            return;
        }
        if (!model.block()) return; // models/item/** 只作兜底，不参与 itemModel

        String last = lastSegment(model.modelName());
        switch (cat) {
            case POT -> {
                if (last.startsWith("dry_pot")) acc.dryModel = model.modelId();
                else if (last.startsWith("wet_pot")) acc.wetModel = model.modelId();
            }
            case SPRINKLER -> acc.blockModel = model.modelId();
            default -> { /* 水壶 / 肥料 / 药剂没有方块世界模型 */ }
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
                } else if (cat == StardewSelectorCategory.SPRINKLER && acc.blockModel == null) {
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
        int idx = numericSuffix(last);
        return switch (cat) {
            case POT -> "dry_pot_" + idx;
            case WATERING_CAN -> "watering_can_" + idx;
            case SPRINKLER -> "sprinkler_" + idx;
            default -> last; // 肥料 / 药剂：末段名本身即逻辑名（含家族前缀）
        };
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
     * <p>仅覆盖 CustomCrops 标准层级（花盆 / 水壶 / 洒水器）；肥料 / 药剂的中文名一律来自
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
            if (modelName.endsWith("_seeds")) seedModelNames.add(modelName);
        }
        // 身份里也允许出现种子（玩家 .id 过但资源包扫描未命中时仍能建档）
        for (String modelName : identityKeyByModelName.keySet()) {
            if (modelName.endsWith("_seeds")) seedModelNames.add(modelName);
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

            addProduce(scannedByName, identityKeyByModelName, stem,
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

            // 作物名必须来自种子语义去后缀，成熟产物名绝不能覆盖作物名。
            // 例如 redpacket：作物=摇钱树、种子=摇钱树种子、产物=红包，三者必须始终分离。
            String chinese = cropNameFromSeed(seedName, stem);
            String evidence = (!produceModels.isEmpty() || !produceKeys.isEmpty())
                ? RuleEvidence.VERIFIED.displayName() : RuleEvidence.CANDIDATE.displayName();

            crops.add(new CropDefinition(stem, chinese, seedKey,
                List.copyOf(produceKeys), List.copyOf(variantKeys), evidence,
                seedModel, seedName, List.copyOf(produceModels), List.copyOf(produceNames),
                List.copyOf(variantModels), List.copyOf(variantNames)));
        }
    }

    /** 从真实种子名派生作物名；只剥离明确种子后缀，无法确认时保留技术 stem。 */
    private static String cropNameFromSeed(String seedName, String stem) {
        if (seedName == null || seedName.isBlank()) return stem;
        String value = seedName.trim();
        if (value.endsWith("种子")) {
            String crop = value.substring(0, value.length() - 2).trim();
            if (!crop.isBlank()) return crop;
        }
        String lower = value.toLowerCase(Locale.ROOT);
        if (lower.endsWith(" seeds")) return value.substring(0, value.length() - 6).trim();
        if (lower.endsWith(" seed")) return value.substring(0, value.length() - 5).trim();
        if (lower.endsWith("_seeds")) return value.substring(0, value.length() - 6);
        return value.equalsIgnoreCase(stem + "_seeds") ? stem : value;
    }

    /** 把某个逻辑名（产物或变种）关联进作物定义；资源与身份都命中才写入 */
    private static void addProduce(Map<String, StardewResourceScanner.ScannedModel> scannedByName,
                                   Map<String, String> identityByModelName, String modelName,
                                   List<String> models, List<String> names, List<String> keys,
                                   boolean variant) {
        StardewResourceScanner.ScannedModel scan = scannedByName.get(modelName);
        String identityKey = identityByModelName.get(modelName);
        if (scan != null || identityKey != null) {
            if (scan != null) {
                models.add(scan.modelId());
                names.add(scan.displayName());
            }
            if (identityKey != null) keys.add(identityKey);
        }
    }

    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    //  逐作物真实阶段（资源扫描结果）
    // ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

    /**
     * 从方块模型逻辑名收集一个真实阶段。
     *
     * <p>身份派生规则与 {@code BlockStateModelResolver.deriveFromModel} 完全一致：去掉
     * {@code models/block/} 后的首段类别（crop/misc/...），剩余段用下划线连接
     * （{@code crop/tomato/stage_3} → {@code tomato_stage_3}），再按 {@code _stage_} 切分。
     * 只有含该标记的才是阶段化作物，普通方块一律跳过。</p>
     */
    private static void collectStage(String modelName, Map<String, List<String>> out) {
        if (modelName == null || modelName.isBlank()) return;
        String path = modelName;
        int firstSlash = path.indexOf('/');
        if (firstSlash >= 0) path = path.substring(firstSlash + 1).replace('/', '_');
        String lower = path.toLowerCase(java.util.Locale.ROOT);
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

    public CropDefinition cropByKey(String cropKey) {
        for (CropDefinition crop : crops) {
            if (crop.cropKey().equals(cropKey)) return crop;
        }
        return null;
    }

    /**
     * 某作物在当前服务器资源包里真实存在的阶段（如 {@code [stage_1, stage_2, stage_3, stage_4]}）。
     *
     * <p>数据来自方块模型扫描，不是编造的区间：资源包没有的阶段绝不会出现在这里，
     * 也不会混入其它作物的阶段。未发现返回空列表。</p>
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

    /** 按稳定键查工具逻辑对象；未找到返回 null */
    public StardewToolDefinition entryByKey(String key) {
        return key == null ? null : toolDefinitions.get(key);
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
