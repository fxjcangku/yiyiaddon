package com.yiyiaddon.platform.resource;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.model.resource.BlockSemantic;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.locale.Language;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 方块状态语义解析器：把 {@link BlockState} 交给当前生效资源包的 blockstates 映射
 * （{@code <命名空间>/blockstates/<方块名>.json}）解析为模型路径与自定义语义身份。
 *
 * <p><b>设计约束（均经 26.1.2 真实资源包实测）：</b></p>
 * <ul>
 *   <li>只读真实资源包，不伪造客户端读不到的服务器私有数据。</li>
 *   <li>只解析 {@code variants} 映射；遇到 {@code multipart} 或无法唯一确定时如实标注
 *       「未知 / 候选」，不假装支持无法可靠解析的格式。</li>
 *   <li>模型路径只是「模型引用」，不等于成熟 / 可交互状态，业务状态判定属上层业务层。</li>
 *   <li>识别属于按需一次性动作，非每 tick 调用，故不做缓存，每次按当前资源管理器实时读取，
 *       天然支持资源包重载。</li>
 * </ul>
 */
public final class BlockStateModelResolver {

    private BlockStateModelResolver() {
    }

    /** 开启 / 关闭语义解析追踪日志（默认关闭） */
    public static void setVerbose(boolean enabled) {
        ResourcePackAccess.verbose = enabled;
    }

    /**
     * 解析给定方块状态的资源包语义。
     *
     * <p>解析链：真实 BlockState → 资源包 blockstates 映射 → 模型路径 → 自定义语义身份。</p>
     * <ol>
     *   <li>命中多个模型候选：保留全部候选，确定性标为「候选」，不冒充确定结果；</li>
     *   <li>命中单个模型：从模型路径派生语义身份；</li>
     *   <li>模型为 {@code block/empty}（隐形载体）或无法派生：回退方块注册 ID
     *       （仅非 minecraft 命名空间），按语言键取中文名。</li>
     * </ol>
     *
     * @param state 目标方块状态
     * @return 解析结果；无命中或无法解析返回 {@link BlockSemantic#unknown()}
     */
    public static BlockSemantic resolve(BlockState state) {
        if (state == null) return BlockSemantic.unknown();

        // 原版技术 ID（如 minecraft:tripwire / craftengine:custom_874），用于定位 blockstates 资源
        Identifier blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        if (blockId == null) return BlockSemantic.unknown();

        ResourcePackAccess.trace("======== 开始解析 " + blockId + " / " + state + " ========");

        // blockstates 资源标识必须带 .json 后缀，与原版 FileToIdConverter.json("blockstates") 一致
        Identifier resourceId = Identifier.tryBuild(blockId.getNamespace(),
            "blockstates/" + blockId.getPath() + ".json");

        ResourcePackAccess.LoadedResource loaded = ResourcePackAccess.readJson(resourceId);
        if (!loaded.ok()) {
            return fallbackIdentity(blockId, null, "未知", loaded.reason());
        }

        JsonObject blockStateJson = loaded.json();
        JsonElement variants = blockStateJson.get("variants");

        // 只解析 variants；multipart 或缺少 variants 的写法如实标注，不假装支持
        if (variants == null || !variants.isJsonObject()) {
            boolean multipart = blockStateJson.has("multipart");
            String reason = multipart ? "资源为 multipart 格式，当前仅支持 variants" : "资源缺少 variants 定义";
            return fallbackIdentity(blockId, null, "未知", reason);
        }

        List<String> distinct = matchModels(state, variants.getAsJsonObject()).stream().distinct().toList();
        if (distinct.isEmpty()) {
            return fallbackIdentity(blockId, null, "未知", "variants 中无匹配当前状态的条目");
        }

        String joined = String.join(" | ", distinct);
        if (distinct.size() > 1) {
            // 多个候选：保留全部候选与来源，绝不随意选择一个冒充确定结果
            return new BlockSemantic(joined, null, null, "当前资源包", "候选", null);
        }

        String model = distinct.get(0);
        BlockSemantic derived = deriveFromModel(model, blockId);
        return derived != null ? derived
            : fallbackIdentity(blockId, joined, "未知", "模型无法派生语义身份");
    }

    /** 在 variants 映射中收集命中当前状态的模型路径（保序，可能多个候选） */
    private static List<String> matchModels(BlockState state, JsonObject variants) {
        List<String> models = new ArrayList<>();
        StateDefinition<Block, BlockState> definition = state.getBlock().getStateDefinition();
        for (Map.Entry<String, JsonElement> entry : variants.entrySet()) {
            if (!matchesVariant(state, definition, entry.getKey())) continue;
            collectModels(entry.getValue(), models);
            ResourcePackAccess.trace("Matched key: " + entry.getKey());
        }
        return models;
    }

    /**
     * 判断单个 variant 键（形如 {@code north=true,powered=false}）是否命中当前状态。
     *
     * <p>键内只列出部分属性时按「仅校验列出的属性」处理，与 Minecraft 原版
     * {@code VariantSelector} 语义一致；键含未知属性直接判不命中。</p>
     */
    private static boolean matchesVariant(BlockState state, StateDefinition<Block, BlockState> definition,
                                          String key) {
        for (String pair : key.split(",")) {
            int eq = pair.indexOf('=');
            if (eq <= 0) continue;
            String name = pair.substring(0, eq).trim();
            String value = pair.substring(eq + 1).trim();
            Property<?> property = definition.getProperty(name);
            if (property == null) return false;
            Optional<?> expected = property.getValue(value);
            if (expected.isEmpty()) return false;
            Comparable<?> actual = state.getValue(property);
            if (!expected.get().equals(actual)) return false;
        }
        return true;
    }

    /** 从单个 variant 值（单对象或加权数组）收集模型路径 */
    private static void collectModels(JsonElement value, List<String> out) {
        if (value.isJsonObject()) {
            appendModel(value.getAsJsonObject(), out);
        } else if (value.isJsonArray()) {
            for (JsonElement element : value.getAsJsonArray()) {
                if (element.isJsonObject()) appendModel(element.getAsJsonObject(), out);
            }
        }
    }

    /** 追加一个 variant 对象里的 model 字段 */
    private static void appendModel(JsonObject variant, List<String> out) {
        JsonElement model = variant.get("model");
        if (model != null && model.isJsonPrimitive()) {
            out.add(model.getAsString());
        }
    }

    /**
     * 从单个命中模型路径派生语义身份与中文名。
     *
     * <p>模型路径形如 {@code customcrops:block/crop/tomato/stage_3}，身份推导统一走
     * {@link #deriveIdentityPath(String)}（{@code item/} 前缀按同一规则处理）。该规则通用，
     * 不硬编码任何服务器。</p>
     *
     * <p>模型为 {@code block/empty}（隐形载体）时无法派生身份，回退方块注册 ID。</p>
     */
    private static BlockSemantic deriveFromModel(String model, Identifier blockId) {
        Identifier modelId = Identifier.tryParse(model);
        if (modelId == null) return null;

        String path = modelId.getPath();
        if (path.equals("empty") || path.equals("block/empty")) {
            return fallbackIdentity(blockId, model, "未知", "隐形载体模型（block/empty），无法派生语义身份");
        }
        String identityPath = deriveIdentityPath(path);
        if (identityPath == null || identityPath.isBlank()) return null;

        String identity = modelId.getNamespace() + ":" + identityPath;
        String name = resolveName("block." + modelId.getNamespace() + "." + identityPath, identityPath);
        return new BlockSemantic(model, identity, name, "当前资源包", "已确认", null);
    }

    /**
     * 从模型引用路径派生语义身份路径（不含命名空间），世界识别与阶段清单共用这一份规则。
     *
     * <p><b>为什么必须只有一份：</b>身份（世界里这株是什么）与阶段清单（这种作物有哪些阶段）
     * 若各写一套，同一株作物会出现两个作物键，识别链两侧对不上，表现成「把连作作物当错位作物去挖」。</p>
     *
     * <p><b>规则（均来自真实资源包实测，不硬编码任何服务器）：</b></p>
     * <ol>
     *   <li>剥掉 {@code block/} 或 {@code item/} 模型目录前缀（部分服务器把作物阶段定义在
     *       {@code models/item/} 下，再让原版载体方块指向它，路径结构与 {@code block/} 完全一致）；</li>
     *   <li>末段是纯阶段名（{@code stage_3}）→ 作物名在上一段，拼成 {@code tomato_stage_3}；</li>
     *   <li>末段自带完整身份（{@code lentinus_edodes_stage_3}、{@code dry_pot}、
     *       {@code greenhouse_glass}）→ 原样取末段，<b>不能再拼父目录</b>，否则会得到
     *       {@code lentinus_lentinus_edodes_stage_3} 这种带重复目录段的假身份；</li>
     *   <li>其余（无阶段标记的类别路径）→ 去掉首段类别后整串下划线连接
     *       （{@code misc/dry_pot_1} → {@code dry_pot_1}）。</li>
     * </ol>
     *
     * @param modelPath 模型引用路径（可带命名空间，如 {@code customcrops:item/crops/tomato/stage_3}）
     * @return 身份路径；无法派生返回 {@code null}
     */
    public static String deriveIdentityPath(String modelPath) {
        if (modelPath == null || modelPath.isBlank()) return null;
        String path = modelPath;
        int colon = path.indexOf(':');
        if (colon >= 0) path = path.substring(colon + 1);
        if (path.startsWith("block/")) path = path.substring("block/".length());
        else if (path.startsWith("item/")) path = path.substring("item/".length());
        if (path.isBlank()) return null;

        int lastSlash = path.lastIndexOf('/');
        String last = lastSlash >= 0 ? path.substring(lastSlash + 1) : path;
        if (last.isBlank()) return null;

        if (last.startsWith("stage")) {
            if (lastSlash < 0) return null;   // 只有 stage_N、没有作物名：如实派生不出身份
            int prevSlash = path.lastIndexOf('/', lastSlash - 1);
            String parent = path.substring(prevSlash + 1, lastSlash);
            return parent.isBlank() ? last : parent + "_" + last;
        }
        if (last.contains("_stage_")) return last;

        int firstSlash = path.indexOf('/');
        return firstSlash >= 0 ? path.substring(firstSlash + 1).replace('/', '_') : path;
    }

    /**
     * 回退：从方块注册 ID 派生身份（针对隐形载体方块）。
     *
     * <p>仅非 minecraft 命名空间有自定义语义：identity 取注册 ID，中文名查
     * {@code block.<命名空间>.<路径>}。minecraft 命名空间方块不冒充自定义身份。</p>
     */
    private static BlockSemantic fallbackIdentity(Identifier blockId, String model, String certainty,
                                                  String reason) {
        if (!"minecraft".equals(blockId.getNamespace())) {
            String identity = blockId.toString();
            String name = resolveName("block." + blockId.getNamespace() + "." + blockId.getPath(),
                blockId.getNamespace() + "." + blockId.getPath());
            return new BlockSemantic(model, identity, name, "当前资源包",
                name != null ? "已确认" : "未知", name != null ? null : reason);
        }
        return new BlockSemantic(model, null, null, "当前资源包", certainty, reason);
    }

    /**
     * 读取语言文件中的自定义方块名。
     *
     * <p>先查正式键 {@code block.<命名空间>.<身份路径>}，未命中再查 {@code block.default.<身份路径>}
     * 兜底（部分资源包中 minecraft 命名空间的 {@code block/custom/*} 模型使用该兜底键）。</p>
     */
    private static String resolveName(String primaryKey, String identityPath) {
        Language language = Language.getInstance();
        if (language == null) return null;
        if (language.has(primaryKey)) return clean(language.getOrDefault(primaryKey));
        String defaultKey = "block.default." + identityPath;
        if (language.has(defaultKey)) return clean(language.getOrDefault(defaultKey));
        return null;
    }

    /** 剥离颜色代码并去首尾空格 */
    private static String clean(String text) {
        if (text == null) return "";
        return text.replaceAll("§[0-9a-fk-orA-FK-ORx]", "").trim();
    }
}
