package com.yiyiaddon.module;

import com.yiyiaddon.ui.page.ModulePage;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/**
 * 模块注册数据对象。
 *
 * <p>只承载「模块是什么、属于哪个分类、是否启用、点开后由谁渲染页面」，不包含任何功能实现。
 * 功能模块接入时提供 {@link #state()} 与 {@link #page()} 即可，模块中心不需要改动。</p>
 */
public final class ModuleEntry {

    private final String id;
    private final String name;
    private final String displayName;
    private final String categoryId;
    private final String description;
    private final String icon;
    private final int order;
    private final String version;
    private final BooleanSupplier state;
    private final Supplier<ModulePage> page;
    private final String statusText;

    private ModuleEntry(Builder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.displayName = builder.displayName;
        this.categoryId = builder.categoryId;
        this.description = builder.description;
        this.icon = builder.icon;
        this.order = builder.order;
        this.version = builder.version;
        this.state = builder.state;
        this.page = builder.page;
        this.statusText = builder.statusText;
    }

    public static Builder builder(String id) {
        return new Builder(id);
    }

    /** 模块 ID，全局唯一。 */
    public String id() {
        return id;
    }

    /** 模块英文名。 */
    public String name() {
        return name;
    }

    /** 模块中文名。 */
    public String displayName() {
        return displayName;
    }

    /** 所属分类 ID。 */
    public String categoryId() {
        return categoryId;
    }

    public String description() {
        return description;
    }

    public String icon() {
        return icon;
    }

    public int order() {
        return order;
    }

    public String version() {
        return version;
    }

    /** 当前是否启用；未接入功能的模块恒为 false。 */
    public boolean enabled() {
        return state.getAsBoolean();
    }

    /** 模块页面工厂；未接入页面时为 {@code null}。 */
    public Supplier<ModulePage> page() {
        return page;
    }

    /**
     * 模块行右侧状态标记的覆写文案；{@code null} = 按 {@link #enabled()} 显示「已启用 / 未启用」。
     *
     * <p>「只剪了入口、功能还没写」的模块（星露谷钓鱼）返回「无法使用」，免得玩家把「未启用」
     * 当成「点一下就能开」。来源见 {@code Module#statusBadgeText()}。</p>
     */
    public String statusText() {
        return statusText;
    }

    public static final class Builder {

        private final String id;
        private String name = "";
        private String displayName = "";
        private String categoryId = "";
        private String description = "";
        private String icon = "";
        private int order;
        private String version = "";
        private BooleanSupplier state = () -> false;
        private Supplier<ModulePage> page;
        private String statusText;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id, "模块 ID 不能为空");
        }

        public Builder name(String value) {
            this.name = value;
            return this;
        }

        public Builder displayName(String value) {
            this.displayName = value;
            return this;
        }

        public Builder category(String value) {
            this.categoryId = value;
            return this;
        }

        public Builder description(String value) {
            this.description = value;
            return this;
        }

        public Builder icon(String value) {
            this.icon = value;
            return this;
        }

        public Builder order(int value) {
            this.order = value;
            return this;
        }

        public Builder version(String value) {
            this.version = value;
            return this;
        }

        /** 启用状态来源；由功能模块提供。 */
        public Builder state(BooleanSupplier value) {
            this.state = value == null ? () -> false : value;
            return this;
        }

        /** 模块页面工厂；由功能模块提供。 */
        public Builder page(Supplier<ModulePage> value) {
            this.page = value;
            return this;
        }

        /** 模块行状态标记的覆写文案；不提供则按启用状态显示。 */
        public Builder statusText(String value) {
            this.statusText = value;
            return this;
        }

        public ModuleEntry build() {
            return new ModuleEntry(this);
        }
    }
}
