package com.yiyiaddon.core.module;

import com.google.gson.JsonObject;
import com.yiyiaddon.command.ClientCommand;
import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.ui.page.ModulePage;
import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * 功能模块基类：所有模块共用的壳。
 *
 * <p><b>子类职责边界</b>：只实现自己的功能逻辑（{@link #onEnable} / {@link #onDisable} /
 * {@link #onTick} / {@link #onEvent}）与元数据，生命周期调度、状态持久化、事件订阅退订、
 * 异常隔离全部由 {@link ModuleManager} 负责。子类禁止自行注册 Fabric 事件、禁止自行读写状态文件、
 * 禁止自行捕获异常后静默吞掉。</p>
 *
 * <p>启用状态只能由运行时改写：{@link #isEnabled()} 对外只读，业务代码通过
 * {@code ModuleManager.setEnabled} 或功能界面切换。</p>
 */
public abstract class Module {

    private final String id;
    private final String displayName;
    private final String categoryId;
    private final String description;

    /** 启用状态；只由 {@link ModuleManager} 改写 */
    private volatile boolean enabled;

    protected Module(String id, String displayName, String categoryId, String description) {
        this.id = Objects.requireNonNull(id, "模块 ID 不能为空");
        this.displayName = displayName == null || displayName.isBlank() ? id : displayName;
        this.categoryId = categoryId == null ? "" : categoryId;
        this.description = description == null ? "" : description;
    }

    // ── 元数据（子类按需覆写） ──

    /** 模块 ID，全局唯一，同时作为状态文件键 */
    public final String id() {
        return id;
    }

    /** 英文名；默认与模块 ID 相同 */
    public String name() {
        return id;
    }

    /** 中文名 */
    public final String displayName() {
        return displayName;
    }

    /** 所属分类 ID，需在 {@code CategoryRegistry} 中存在 */
    public final String categoryId() {
        return categoryId;
    }

    /** 功能说明 */
    public final String description() {
        return description;
    }

    /** 图标字形；留空则由卡片显示默认样式 */
    public String icon() {
        return "";
    }

    /** 分类内排序权重 */
    public int order() {
        return 100;
    }

    /** 版本号 */
    public String version() {
        return "0.1.0";
    }

    // ── 状态 ──

    public final boolean isEnabled() {
        return enabled;
    }

    /** 快捷键键名 */
    public final String keybindId() {
        return ModuleKeybinds.BINDING_PREFIX + id;
    }

    // ── 生命周期（子类覆写；全部在主线程调用） ──

    /** 进程内一次性初始化；此时尚未启用，不要在此访问世界状态 */
    protected void onInitialize() {
    }

    /** 启用时调用 */
    protected void onEnable() {
    }

    /** 关闭时调用；必须能被重复调用而不出错 */
    protected void onDisable() {
    }

    /**
     * 是否抑制运行时的统一开关播报「已开启」。
     *
     * <p>默认 {@code false}，既有模块行为不变。模块在 {@link #onEnable()} 里已经用统一状态源给出
     * 完整启动结论时，可覆写为 {@code true}，避免同一次启用出现两条提示。</p>
     */
    protected boolean suppressEnableAnnounce() {
        return false;
    }

    /** 每刻调用，仅在启用状态下 */
    public void onTick(Minecraft client) {
    }

    /**
     * 启用前自检：返回全部缺项的中文描述，返回空列表表示可以启用。
     *
     * <p>自检由运行时在启用前调用一次；存在缺项时模块不会被启用，并由运行时向玩家说明原因。
     * 禁止在自检里修改游戏状态。</p>
     */
    public List<String> selfCheck() {
        return List.of();
    }

    // ── 事件（声明式） ──

    /**
     * 声明本模块需要接收的事件类型。
     *
     * <p>运行时在模块启用时按此清单订阅、关闭时整批退订，因此模块不需要（也不允许）自己调用
     * 事件总线。重复启用不会产生重复订阅。</p>
     */
    public Set<ClientEventType> subscribedEvents() {
        return Set.of();
    }

    /** 接收已声明的事件；仅在模块启用时被调用 */
    public void onEvent(ClientEvent event) {
    }

    // ── 设置持久化（运行时统一读写，模块只负责字段编解码） ──

    /** 从模块设置对象载入字段；对象缺失字段时应使用默认值 */
    public void loadSettings(JsonObject settings) {
    }

    /** 把字段写入模块设置对象 */
    public void saveSettings(JsonObject settings) {
    }

    // ── 界面与指令 ──

    /** 模块独立页面；返回 {@code null} 表示尚未接入，模块中心会落到占位页面 */
    public ModulePage page() {
        return null;
    }

    /** 模块自带指令；由运行时统一注册 */
    public List<ClientCommand> commands() {
        return List.of();
    }

    // ── 运行时内部 ──

    /** 由 {@link ModuleManager} 改写启用标志 */
    final void applyEnabled(boolean value) {
        enabled = value;
    }

    @Override
    public String toString() {
        return displayName + "(" + id + ")";
    }
}
