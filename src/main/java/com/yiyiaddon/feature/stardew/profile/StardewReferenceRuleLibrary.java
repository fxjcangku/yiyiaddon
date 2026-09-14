package com.yiyiaddon.feature.stardew.profile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * JAR 内置星露谷参考规则库。
 *
 * <p>开发目录只负责离线生成 JSON；发布后本类只从类路径资源读取，因此朋友仅安装 addon JAR
 * 即可完成逐作物签名匹配，不需要复制参考 ZIP、外部 JSON 或开发者 Profile。</p>
 */
public final class StardewReferenceRuleLibrary {

    private static final String RESOURCE = "/assets/yiyiaddon/stardew/reference-harvest-rules.json";

    /** JAR 内单条参考规则。 */
    public record ReferenceRule(String cropKey, String matureStage,
                                StardewHarvestAction harvestAction,
                                StardewCropLifecycle documentedLifecycle,
                                boolean specialHarvest,
                                String cropResourceSignature) {
    }

    private static final Map<String, ReferenceRule> RULES = load();

    private StardewReferenceRuleLibrary() {
        // 工具类，禁止实例化
    }

    /** 按作物键查询 JAR 内置规则；资源缺失或该作物不存在时返回 null 并进入安全学习。 */
    public static ReferenceRule rule(String cropKey) {
        return cropKey == null ? null : RULES.get(cropKey);
    }

    /** 当前 JAR 实际成功载入的参考作物数量。 */
    public static int size() {
        return RULES.size();
    }

    /** 返回不可影响内部状态的规则快照。 */
    public static Map<String, ReferenceRule> snapshot() {
        return new LinkedHashMap<>(RULES);
    }

    /** 启动时一次读取类路径 JSON；任一单条损坏只跳过该条，整个资源损坏则安全返回空库。 */
    private static Map<String, ReferenceRule> load() {
        Map<String, ReferenceRule> rules = new LinkedHashMap<>();
        try (InputStream in = StardewReferenceRuleLibrary.class.getResourceAsStream(RESOURCE)) {
            if (in == null) return rules;
            JsonObject root = JsonParser.parseReader(new InputStreamReader(in, StandardCharsets.UTF_8)).getAsJsonObject();
            if (!StardewCropResourceSignature.ALGORITHM.equals(string(root, "签名算法"))) return rules;
            if (!root.has("作物规则") || !root.get("作物规则").isJsonObject()) return rules;
            for (var entry : root.getAsJsonObject("作物规则").entrySet()) {
                if (!entry.getValue().isJsonObject()) continue;
                JsonObject obj = entry.getValue().getAsJsonObject();
                try {
                    String mature = string(obj, "成熟阶段");
                    String action = string(obj, "收割动作");
                    String signature = string(obj, "作物资源签名");
                    if (mature == null || action == null || signature == null) continue;
                    String lifecycleName = string(obj, "文档生命周期");
                    StardewCropLifecycle lifecycle = lifecycleName == null
                        ? StardewCropLifecycle.UNKNOWN : StardewCropLifecycle.valueOf(lifecycleName);
                    rules.put(entry.getKey(), new ReferenceRule(entry.getKey(), mature,
                        StardewHarvestAction.valueOf(action), lifecycle,
                        obj.has("特殊收割") && obj.get("特殊收割").isJsonPrimitive()
                            && obj.get("特殊收割").getAsBoolean(), signature));
                } catch (Exception ignored) {
                    // 单作物规则损坏不能影响其它作物，未载入项会自动进入 LEARNING。
                }
            }
        } catch (Exception ignored) {
            // 内置资源缺失或损坏时绝不猜成熟阶段，整库退回 LEARNING。
        }
        return rules;
    }

    /** 安全读取非空字符串字段。 */
    private static String string(JsonObject obj, String key) {
        if (!obj.has(key) || obj.get(key).isJsonNull() || !obj.get(key).isJsonPrimitive()) return null;
        String value = obj.get(key).getAsString();
        return value == null || value.isBlank() ? null : value;
    }
}
