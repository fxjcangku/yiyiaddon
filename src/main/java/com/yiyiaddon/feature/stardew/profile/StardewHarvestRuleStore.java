package com.yiyiaddon.feature.stardew.profile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yiyiaddon.repository.JsonFileStore;
import net.minecraft.client.Minecraft;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 收获学习结果持久化。
 *
 * <p>物理隔离键为 {@code ServerKey/fingerprint/cropKey}：服务器目录隔离 A/B，资源指纹文件
 * 隔离同服换包，JSON 内的作物键隔离每种作物。读取时还会由调用方复核逐作物资源签名，
 * 即使文件被误复制也不会跨资源复用。</p>
 */
public final class StardewHarvestRuleStore {

    private final Path root = Minecraft.getInstance().gameDirectory.toPath()
        .resolve("StardewFarm").resolve("harvest-rules");

    /** 读取当前服务器与资源指纹下的全部作物规则；损坏文件保持原样并返回空映射。 */
    public Map<String, StardewHarvestRule> load(String serverKey, String fingerprint) {
        Map<String, StardewHarvestRule> result = new LinkedHashMap<>();
        Path file = file(serverKey, fingerprint);
        if (file == null || !Files.isRegularFile(file)) return result;
        try {
            JsonObject rootObject = JsonParser.parseString(Files.readString(file, StandardCharsets.UTF_8)).getAsJsonObject();
            if (!serverKey.equals(string(rootObject, "服务器"))
                || !fingerprint.equals(string(rootObject, "资源指纹"))
                || !StardewCropResourceSignature.ALGORITHM.equals(string(rootObject, "作物签名算法"))) return result;
            if (!rootObject.has("作物规则") || !rootObject.get("作物规则").isJsonObject()) return result;
            for (var entry : rootObject.getAsJsonObject("作物规则").entrySet()) {
                if (!entry.getValue().isJsonObject()) continue;
                StardewHarvestRule rule = parseRule(entry.getValue().getAsJsonObject());
                if (rule != null) result.put(entry.getKey(), rule);
            }
        } catch (Exception ignored) {
            // 损坏档案不覆盖，保留现场供排查；执行层会安全进入 LEARNING。
        }
        return result;
    }

    /** 原子保存当前隔离域的全部作物规则，失败时保留最后一份可用文件。 */
    public boolean save(String serverKey, String fingerprint, Map<String, StardewHarvestRule> rules) {
        Path file = file(serverKey, fingerprint);
        if (file == null) return false;
        JsonObject rootObject = new JsonObject();
        rootObject.addProperty("服务器", serverKey);
        rootObject.addProperty("资源指纹", fingerprint);
        rootObject.addProperty("作物签名算法", StardewCropResourceSignature.ALGORITHM);
        JsonObject cropRules = new JsonObject();
        if (rules != null) {
            for (Map.Entry<String, StardewHarvestRule> entry : rules.entrySet()) {
                if (entry.getKey() == null || entry.getKey().isBlank() || entry.getValue() == null) continue;
                cropRules.add(entry.getKey(), toJson(entry.getValue()));
            }
        }
        rootObject.add("作物规则", cropRules);
        rootObject.addProperty("版本", 1);
        return JsonFileStore.writeAtomic(file, rootObject);
    }

    /** 构造严格落在模块目录内的隔离路径；关键参数缺失时拒绝产生文件。 */
    private Path file(String serverKey, String fingerprint) {
        if (serverKey == null || serverKey.isBlank() || fingerprint == null || fingerprint.isBlank()) return null;
        return root.resolve(safe(serverKey)).resolve(safe(fingerprint) + ".json");
    }

    /** Windows 非法路径字符统一替换，隔离键原值仍写入 JSON 二次校验。 */
    private static String safe(String value) {
        return value.replaceAll("[\\\\/:*?\"<>|]", "_");
    }

    /** 把一条规则转成用户可读中文 JSON。 */
    private static JsonObject toJson(StardewHarvestRule rule) {
        JsonObject obj = new JsonObject();
        obj.addProperty("成熟阶段", rule.matureStage());
        obj.addProperty("收割动作", rule.harvestAction() == null ? null : rule.harvestAction().name());
        obj.addProperty("生命周期", rule.lifecycle() == null ? null : rule.lifecycle().name());
        if (rule.afterHarvestStage() != null) obj.addProperty("收割后阶段", rule.afterHarvestStage());
        obj.addProperty("证据", rule.evidence() == null ? RuleEvidence.UNKNOWN.name() : rule.evidence().name());
        obj.addProperty("作物资源签名", rule.cropResourceSignature());
        return obj;
    }

    /** 宽容读取单条规则；枚举或关键字段损坏时丢弃该条，不影响其它作物。 */
    private static StardewHarvestRule parseRule(JsonObject obj) {
        try {
            String matureStage = string(obj, "成熟阶段");
            String actionName = string(obj, "收割动作");
            String lifecycleName = string(obj, "生命周期");
            String evidenceName = string(obj, "证据");
            String signature = string(obj, "作物资源签名");
            if (matureStage == null || signature == null) return null;
            StardewHarvestAction action = actionName == null
                ? StardewHarvestAction.RIGHT_CLICK : StardewHarvestAction.valueOf(actionName);
            StardewCropLifecycle lifecycle = lifecycleName == null
                ? StardewCropLifecycle.UNKNOWN : StardewCropLifecycle.valueOf(lifecycleName);
            RuleEvidence evidence = evidenceName == null
                ? RuleEvidence.UNKNOWN : RuleEvidence.valueOf(evidenceName);
            return new StardewHarvestRule(matureStage, action, lifecycle,
                string(obj, "收割后阶段"), evidence, signature);
        } catch (Exception ignored) {
            return null;
        }
    }

    /** 安全读取字符串字段，缺失、JSON null 或空白统一返回 null。 */
    private static String string(JsonObject obj, String key) {
        if (obj == null || !obj.has(key) || obj.get(key).isJsonNull() || !obj.get(key).isJsonPrimitive()) return null;
        String value = obj.get(key).getAsString();
        return value == null || value.isBlank() ? null : value;
    }
}
