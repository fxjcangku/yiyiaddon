package com.yiyiaddon.config.identity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yiyiaddon.model.identity.ItemIdentity;
import com.yiyiaddon.service.identity.IdentityService;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 身份目标选择配置：持久化「已选中的物品身份键」。
 *
 * <p>数据源唯一来自 {@link IdentityService}：本配置只保存身份键字符串，运行时按键回查完整身份，
 * 绝不复制第二套物品数据。身份被删除后由 {@link #pruneInvalid(IdentityService)} 同步移除失效项。</p>
 *
 * <p>本类是配置层：只做读写与失效清理，不含界面代码。界面在模块系统中按自身控件实现。</p>
 */
public final class IdentityTargetConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final String FILE_NAME = "yiyiaddon-identity-targets.json";
    private static final String KEY_ITEMS = "已选物品";

    private static final Set<String> SELECTED_ITEM_KEYS = new LinkedHashSet<>();
    private static boolean loaded;

    private IdentityTargetConfig() {
    }

    private static Path path() {
        return FabricLoader.getInstance().getConfigDir().resolve(FILE_NAME);
    }

    /** 幂等载入；文件缺失或损坏时保持空集合，不阻断启动 */
    public static void load() {
        if (loaded) return;
        loaded = true;
        Path file = path();
        if (!Files.isRegularFile(file)) return;
        try {
            String raw = Files.readString(file, StandardCharsets.UTF_8);
            JsonElement root = JsonParser.parseString(raw);
            if (root == null || !root.isJsonObject()) return;
            JsonObject json = root.getAsJsonObject();
            JsonElement keys = json.get(KEY_ITEMS);
            if (keys == null || !keys.isJsonArray()) return;
            SELECTED_ITEM_KEYS.clear();
            for (JsonElement element : keys.getAsJsonArray()) {
                if (element.isJsonPrimitive()) SELECTED_ITEM_KEYS.add(element.getAsString());
            }
        } catch (Exception ignored) {
            // 配置损坏：保持空集合
        }
    }

    /** 写回配置文件；失败静默忽略 */
    public static void save() {
        JsonObject json = new JsonObject();
        JsonArray array = new JsonArray();
        for (String key : SELECTED_ITEM_KEYS) array.add(key);
        json.add(KEY_ITEMS, array);
        try {
            Path file = path();
            Files.createDirectories(file.getParent());
            Files.writeString(file, GSON.toJson(json), StandardCharsets.UTF_8);
        } catch (Exception ignored) {
            // 磁盘不可写：忽略
        }
    }

    /** 已选物品身份键的只读快照 */
    public static Set<String> selectedItemKeys() {
        return new LinkedHashSet<>(SELECTED_ITEM_KEYS);
    }

    /** 覆盖全部已选身份键 */
    public static void setSelectedItemKeys(Iterable<String> keys) {
        SELECTED_ITEM_KEYS.clear();
        if (keys != null) {
            for (String key : keys) {
                if (key != null && !key.isBlank()) SELECTED_ITEM_KEYS.add(key);
            }
        }
        save();
    }

    /** 选中 / 取消选中单个身份键 */
    public static void setItemSelected(String key, boolean selected) {
        if (key == null || key.isBlank()) return;
        if (selected) SELECTED_ITEM_KEYS.add(key);
        else SELECTED_ITEM_KEYS.remove(key);
        save();
    }

    /** 清空选择 */
    public static void reset() {
        SELECTED_ITEM_KEYS.clear();
        save();
    }

    /** 解析为完整身份集合（唯一数据源，无副本） */
    public static List<ItemIdentity> selectedItems(IdentityService service) {
        return service.itemsOf(selectedItemKeys());
    }

    /**
     * 清理已失效的选中项（对应身份已从身份库删除），返回被移除数量。
     */
    public static int pruneInvalid(IdentityService service) {
        List<String> valid = new ArrayList<>();
        for (String key : SELECTED_ITEM_KEYS) {
            if (service.findItem(key) != null) valid.add(key);
        }
        int removed = SELECTED_ITEM_KEYS.size() - valid.size();
        if (removed > 0) setSelectedItemKeys(valid);
        return removed;
    }
}
