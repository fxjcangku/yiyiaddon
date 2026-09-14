package com.yiyiaddon.feature.identity.config;

import com.google.gson.JsonObject;
import com.yiyiaddon.core.Json;
import com.yiyiaddon.model.identity.IdentifyMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * ID 模块配置：识别模式与输出详细程度。
 *
 * <p>只负责字段编解码，读写与落盘由模块运行时的 {@code loadSettings} / {@code saveSettings} 统一处理，
 * 因此本类不接触文件与路径。</p>
 */
public final class IdentityModuleConfig {

    private static final String KEY_MODE = "识别模式";
    private static final String KEY_VERBOSE = "详细输出";

    private IdentifyMode mode = IdentifyMode.AUTO_SAVE;
    private boolean verbose;

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

    public boolean verbose() {
        return verbose;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
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

    /** 模式说明，用于页面子行 */
    public static String describe(IdentifyMode value) {
        return switch (value) {
            case CHAT_COPY -> "只展示识别结果，不写入身份库";
            case AUTO_SAVE -> "识别后直接写入身份库";
            case CROSSHAIR_BLOCK -> "识别准星命中的方块并写入身份库";
        };
    }

    public void load(JsonObject json) {
        mode = parseMode(Json.string(json, KEY_MODE, mode.name()));
        verbose = Json.bool(json, KEY_VERBOSE, verbose);
    }

    public void save(JsonObject json) {
        json.addProperty(KEY_MODE, mode.name());
        json.addProperty(KEY_VERBOSE, verbose);
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
