package com.yiyiaddon.model.resource;

/**
 * 客户端资源分类：按资源路径首段划分。
 *
 * <p>枚举顺序即界面与播报的展示顺序，探针的 {@code categoryNames()} 直接使用本顺序。</p>
 *
 * <p>只按路径首段判定，不做命名空间特判：同一分类规则对原版、服务器资源包、本地资源包一致，
 * 因此本地包与服务器包的分析结果可以直接比对。</p>
 */
public enum ResourceCategory {

    /** {@code <ns>:blockstates/**} */
    BLOCKSTATES("方块状态", "blockstates"),

    /** {@code <ns>:models/**} */
    MODELS("模型", "models"),

    /** {@code <ns>:textures/**} */
    TEXTURES("贴图", "textures"),

    /** {@code <ns>:lang/**} */
    LANG("语言", "lang"),

    /** {@code <ns>:font/**} */
    FONT("字体", "font"),

    /** 其余路径：声音、着色器、粒子等，以及无法归类的自定义目录 */
    OTHER("其它", "");

    private final String label;
    private final String prefix;

    ResourceCategory(String label, String prefix) {
        this.label = label;
        this.prefix = prefix;
    }

    /** 中文名 */
    public String label() {
        return label;
    }

    /** 路径首段，{@link #OTHER} 为空表示兜底 */
    public String prefix() {
        return prefix;
    }

    /**
     * 按资源路径判定分类。
     *
     * @param path 资源标识的路径部分，形如 {@code blockstates/tomato_stage_0.json}
     */
    public static ResourceCategory ofPath(String path) {
        if (path == null || path.isBlank()) return OTHER;
        for (ResourceCategory category : values()) {
            if (category == OTHER) continue;
            if (path.startsWith(category.prefix + "/")) return category;
        }
        return OTHER;
    }
}
