package com.yiyiaddon.feature.identity.config;

import com.google.gson.JsonObject;
import com.yiyiaddon.core.Json;
import com.yiyiaddon.model.identity.IdentifyMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * ID 模块配置：识别模式与方块语义调试开关。
 *
 * <p>字段与旧项目 {@code IdIdentifyModule} 的 {@code sgIdentify}（「识别」组）一致：
 * {@code 识别模式}（枚举）与 {@code 方块语义调试}（布尔）。只负责字段编解码，读写与落盘由模块运行时的
 * {@code loadSettings} / {@code saveSettings} 统一处理，因此本类不接触文件与路径。</p>
 */
public final class IdentityModuleConfig {

    private static final String KEY_MODE = "识别模式";
    private static final String KEY_BLOCK_SEMANTIC_DEBUG = "方块语义调试";

    private IdentifyMode mode = IdentifyMode.AUTO_SAVE;
    private boolean blockSemanticDebug;

    public IdentifyMode mode() {
        return mode;
    }

    public void setMode(IdentifyMode mode) {
        this.mode = mode == null ? IdentifyMode.AUTO_SAVE : mode;
    }

    /** 识别结果是否写入身份库：仅「聊天复制/显示」模式不落盘 */
    public boolean savesToLibrary() {
        return mode != IdentifyMode.CHAT_COPY;
    }

    /** 方块语义调试：开启后 {@code .id 方块} 把语义解析全过程输出到 latest.log */
    public boolean blockSemanticDebug() {
        return blockSemanticDebug;
    }

    public void setBlockSemanticDebug(boolean value) {
        this.blockSemanticDebug = value;
    }

    /** 模式下拉框文案 */
    public List<String> modeLabels() {
        List<String> labels = new ArrayList<>();
        for (IdentifyMode value : IdentifyMode.values()) labels.add(value.displayName());
        return labels;
    }

    public int modeIndex() {
        return mode.ordinal();
    }

    public void setModeIndex(int index) {
        IdentifyMode[] values = IdentifyMode.values();
        setMode(values[Math.floorMod(index, values.length)]);
    }

    /**
     * 模式说明，用于页面子行与模式卡片。
     *
     * <p>文本取自旧项目 {@code IdIdentifyModule} 的「识别模式」设置项描述原文，按模式拆分为三条，
     * 未做任何改写。旧项目原文为一条完整描述：
     * 「聊天复制/显示：识别手持物品后弹出结果屏幕；自动保存：识别手持物品后直接写入 ID 配置；
     * 准星方块识别：识别准星真实命中的方块。」</p>
     */
    public static String describe(IdentifyMode value) {
        return switch (value) {
            case CHAT_COPY -> "聊天复制/显示：识别手持物品后弹出结果屏幕";
            case AUTO_SAVE -> "自动保存：识别手持物品后直接写入 ID 配置";
            case CROSSHAIR_BLOCK -> "准星方块识别：识别准星真实命中的方块";
        };
    }

    public void load(JsonObject json) {
        mode = parseMode(Json.string(json, KEY_MODE, mode.name()));
        blockSemanticDebug = Json.bool(json, KEY_BLOCK_SEMANTIC_DEBUG, blockSemanticDebug);
    }

    public void save(JsonObject json) {
        json.addProperty(KEY_MODE, mode.name());
        json.addProperty(KEY_BLOCK_SEMANTIC_DEBUG, blockSemanticDebug);
    }

    private static IdentifyMode parseMode(String raw) {
        if (raw == null || raw.isBlank()) return IdentifyMode.AUTO_SAVE;
        try {
            return IdentifyMode.valueOf(raw.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException ignored) {
            return IdentifyMode.AUTO_SAVE;
        }
    }
}
