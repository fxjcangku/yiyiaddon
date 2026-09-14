package com.yiyiaddon.model.identity;

/**
 * 识别模式：决定识别动作的行为。
 *
 * <ul>
 *   <li>聊天复制 / 显示：只展示完整识别结果，不自动落盘；</li>
 *   <li>自动保存：识别后直接写入对应身份库；</li>
 *   <li>准星方块识别：识别准星真实命中的方块（不读取手持物品）。</li>
 * </ul>
 */
public enum IdentifyMode {

    /** 只展示完整识别结果，不自动落盘 */
    CHAT_COPY("聊天复制/显示"),

    /** 识别后直接写入身份库 */
    AUTO_SAVE("自动保存"),

    /** 识别准星真实命中的方块 */
    CROSSHAIR_BLOCK("准星方块识别");

    private final String displayName;

    IdentifyMode(String displayName) {
        this.displayName = displayName;
    }

    /** 中文文案 */
    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
