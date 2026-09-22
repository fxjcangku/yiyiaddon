package com.yiyiaddon.feature.stardew.profile;

import com.google.gson.JsonElement;
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
 * 特殊变种收割口径的持久化。
 *
 * <p>物理隔离键为 {@code ServerKey/fingerprint/作物键}，与 {@link StardewHarvestRuleStore} 同一套口径：
 * 服务器目录隔离 A/B，资源指纹隔离同服换包，JSON 内的作物键隔离每种作物，
 * 读取时还会复核文件里的服务器与指纹，即使文件被误复制也不会跨服复用。</p>
 *
 * <p><b>为什么必须按服务器存</b>（用户 2026-09-22：「不会改了左键，我回去上个服会拿金锄头破坏左键
 * 金番茄吧？能不能不同的服务器各自学习记录」）：老服是右键口径、本服是左键破坏口径，
 * 口径写死在代码里就一定会砸坏另一边的巨型作物。</p>
 *
 * <p><b>版本 2</b>：一条口径从「动作」扩成「动作 + 手持」。手持三类取值 —— 不限 / 原版物品（只记本体 ID）/
 * 自定义道具（记身份键）。版本 1 的旧档照旧能读：只有动作时按旧语义补齐
 * （右键 = 原版金锄头、破坏 = 不限），行为与升级前完全一致。</p>
 */
public final class StardewSpecialHarvestStore {

    private final Path root = Minecraft.getInstance().gameDirectory.toPath()
        .resolve("StardewFarm").resolve("special-harvest");

    /** 读取当前服务器与资源指纹下的全部作物口径；损坏文件保持原样并返回空映射。 */
    public Map<String, StardewSpecialHarvestRecipe> load(String serverKey, String fingerprint) {
        Map<String, StardewSpecialHarvestRecipe> result = new LinkedHashMap<>();
        Path file = file(serverKey, fingerprint);
        if (file == null || !Files.isRegularFile(file)) return result;
        try {
            JsonObject rootObject = JsonParser.parseString(Files.readString(file, StandardCharsets.UTF_8))
                .getAsJsonObject();
            if (!serverKey.equals(string(rootObject, "服务器"))
                || !fingerprint.equals(string(rootObject, "资源指纹"))) return result;
            if (!rootObject.has("特殊收法") || !rootObject.get("特殊收法").isJsonObject()) return result;
            for (var entry : rootObject.getAsJsonObject("特殊收法").entrySet()) {
                if (entry.getKey() == null || entry.getKey().isBlank() || !entry.getValue().isJsonObject()) continue;
                StardewSpecialHarvestRecipe recipe = parseRecipe(entry.getValue().getAsJsonObject());
                if (recipe != null) result.put(entry.getKey(), recipe);
            }
        } catch (Exception ignored) {
            // 损坏档案不覆盖，保留现场供排查；执行层会安全退回默认口径（金锄头右键）
        }
        return result;
    }

    /** 原子保存当前隔离域的全部口径，失败时保留最后一份可用文件。 */
    public boolean save(String serverKey, String fingerprint, Map<String, StardewSpecialHarvestRecipe> rules) {
        Path file = file(serverKey, fingerprint);
        if (file == null) return false;
        JsonObject rootObject = new JsonObject();
        rootObject.addProperty("服务器", serverKey);
        rootObject.addProperty("资源指纹", fingerprint);
        rootObject.addProperty("说明", "特殊变种（金色 / 巨型 / 变种）在本服的收割口径（动作 + 手持），按作物分别记录；"
            + "\"*\" 是本服通用口径（玩家在本服第一次示范后写入，供本服其它还没学过的变种沿用）");
        JsonObject actions = new JsonObject();
        if (rules != null) {
            for (Map.Entry<String, StardewSpecialHarvestRecipe> entry : rules.entrySet()) {
                if (entry.getKey() == null || entry.getKey().isBlank() || entry.getValue() == null) continue;
                StardewSpecialHarvestRecipe recipe = entry.getValue();
                JsonObject one = new JsonObject();
                one.addProperty("动作", recipe.action().name());
                one.addProperty("动作说明", recipe.action().displayName());
                if (recipe.toolUnlimited()) {
                    one.addProperty("手持", "不限");
                } else {
                    JsonObject tool = new JsonObject();
                    tool.addProperty("物品ID", recipe.toolItemId());
                    if (recipe.toolIdentityKey() != null && !recipe.toolIdentityKey().isBlank()) {
                        tool.addProperty("身份键", recipe.toolIdentityKey());
                    }
                    if (recipe.toolLabel() != null && !recipe.toolLabel().isBlank()) {
                        tool.addProperty("显示名", recipe.toolLabel());
                    }
                    one.add("手持", tool);
                }
                one.addProperty("证据", "玩家实测（观察到那一格确实被收掉后才落盘）");
                one.addProperty("学习时间", System.currentTimeMillis());
                actions.add(entry.getKey(), one);
            }
        }
        rootObject.add("特殊收法", actions);
        rootObject.addProperty("版本", 2);
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

    /**
     * 宽容读取一条口径；动作缺失或损坏时丢弃该条，不影响其它作物。
     *
     * <p>手持字段三种写法：{@code "不限"}（字符串）/ 物品对象（物品 ID + 可选身份键、显示名）/
     * 整条缺失（版本 1 旧档，按动作补旧语义）。</p>
     */
    private static StardewSpecialHarvestRecipe parseRecipe(JsonObject obj) {
        StardewSpecialHarvestAction action = parseAction(obj);
        if (action == null) return null;
        JsonElement held = obj.get("手持");
        if (held == null || held.isJsonNull()) return legacy(action);
        if (!held.isJsonObject()) return StardewSpecialHarvestRecipe.anyTool(action);
        JsonObject tool = held.getAsJsonObject();
        String itemId = string(tool, "物品ID");
        if (itemId == null) return StardewSpecialHarvestRecipe.anyTool(action);
        return new StardewSpecialHarvestRecipe(action, itemId, string(tool, "身份键"), string(tool, "显示名"));
    }

    /** 版本 1 旧档（只记了动作）兼容：右键 = 原版金锄头（旧代码写死的那件），破坏 = 不限 */
    private static StardewSpecialHarvestRecipe legacy(StardewSpecialHarvestAction action) {
        return action == StardewSpecialHarvestAction.RIGHT_CLICK
            ? StardewSpecialHarvestRecipe.DEFAULT
            : StardewSpecialHarvestRecipe.anyTool(action);
    }

    /** 宽容读取动作；枚举缺失或损坏返回 {@code null} */
    private static StardewSpecialHarvestAction parseAction(JsonObject obj) {
        try {
            String actionName = string(obj, "动作");
            return actionName == null ? null : StardewSpecialHarvestAction.valueOf(actionName);
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
