package com.yiyiaddon.feature.identity;

import com.google.gson.JsonObject;
import com.yiyiaddon.command.ClientCommand;
import com.yiyiaddon.command.CommandManager;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.identity.command.IdentityCommand;
import com.yiyiaddon.feature.identity.config.IdentityModuleConfig;
import com.yiyiaddon.feature.identity.model.IdentitySummary;
import com.yiyiaddon.feature.identity.service.IdentityActions;
import com.yiyiaddon.feature.identity.ui.IdentityModulePage;
import com.yiyiaddon.model.identity.IdentifyMode;
import com.yiyiaddon.platform.GameProbe;
import com.yiyiaddon.service.identity.IdentityService;
import com.yiyiaddon.ui.page.ModulePage;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * ID 识别与配置管理模块：第六阶段的第一个真实模块，用于验证整套模块运行时。
 *
 * <p>覆盖的能力：模块注册与元数据、生命周期（初始化 / 启用 / 关闭 / 自检）、事件订阅、
 * 设置持久化、模块页面、模块指令、快捷键绑定。</p>
 *
 * <p>业务实现全部复用第五阶段产物：识别走 {@code ItemIdentifier} / {@code BlockIdentifier} /
 * {@code EntityIdentifier}，数据走 {@code IdentityService}，目标选择走 {@code IdentityTargetConfig}，
 * 本模块只负责把它们串起来并给出中文回显。</p>
 */
public final class IdentityModule extends Module {

    /** 模块 ID，同时作为状态文件键与快捷键键名后缀 */
    public static final String MODULE_ID = "identity";

    /** 图标字形，与界面搜索图标同一字形（已确认存在于所引字体） */
    private static final String ICON = "\uE8B6";

    private final IdentityModuleConfig config = new IdentityModuleConfig();

    /** 本会话最近一次识别结果；关闭模块时清空 */
    private volatile IdentitySummary latest;

    public IdentityModule() {
        super(MODULE_ID, "ID识别与配置管理", "tools", "识别物品、实体、方块，管理身份库与识别目标");
    }

    @Override
    public String name() {
        return "IdentityTool";
    }

    @Override
    public String icon() {
        return ICON;
    }

    @Override
    public int order() {
        return 10;
    }

    @Override
    public String version() {
        return "1.0.0";
    }

    // ── 生命周期 ──

    /**
     * 自检：未进入世界时不允许启用。
     *
     * <p>识别动作依赖主手物品、准星命中结果与世界数据，主菜单下启用只会得到连续的失败提示。</p>
     */
    @Override
    public List<String> selfCheck() {
        List<String> problems = new ArrayList<>();
        if (!GameProbe.inWorld()) problems.add("未进入世界");
        return problems;
    }

    @Override
    protected void onEnable() {
        IdentityService.shared().load();
        int pruned = IdentityActions.pruneInvalidTargets();
        if (pruned > 0) ClientChat.send("已清理 " + pruned + " 项失效的识别目标");
        ClientChat.send("识别模式：" + config.mode().displayName() + " ｜ " + IdentityActions.statsText());
    }

    @Override
    protected void onDisable() {
        latest = null;
    }

    /**
     * 订阅进服事件。
     *
     * <p>身份库是磁盘唯一事实来源，换服后必须重新读盘：否则会沿用上一台服务器载入时的内存集合，
     * 出现「已经删掉的身份仍然可选」这类残留。</p>
     */
    @Override
    public Set<ClientEventType> subscribedEvents() {
        return EnumSet.of(ClientEventType.JOIN_SERVER);
    }

    @Override
    public void onEvent(ClientEvent event) {
        if (event.type() != ClientEventType.JOIN_SERVER) return;
        IdentityService.shared().reload();
        int pruned = IdentityActions.pruneInvalidTargets();
        if (pruned > 0) ClientChat.send("已清理 " + pruned + " 项失效的识别目标");
    }

    // ── 设置 ──

    @Override
    public void loadSettings(JsonObject settings) {
        config.load(settings);
    }

    @Override
    public void saveSettings(JsonObject settings) {
        config.save(settings);
    }

    // ── 界面与指令 ──

    @Override
    public ModulePage page() {
        return new IdentityModulePage(this);
    }

    @Override
    public List<ClientCommand> commands() {
        return List.of(new IdentityCommand(this));
    }

    // ── 供页面与指令调用的功能入口 ──

    /** 按当前识别模式执行默认识别动作 */
    public IdentitySummary identifyByMode() {
        return config.mode() == IdentifyMode.CROSSHAIR_BLOCK ? identifyBlock() : identifyItem();
    }

    public IdentitySummary identifyItem() {
        if (!requireEnabled()) return null;
        return finish(IdentityActions.identifyItem(config.savesToLibrary()));
    }

    public IdentitySummary identifyBlock() {
        if (!requireEnabled()) return null;
        return finish(IdentityActions.identifyBlock(config.savesToLibrary()));
    }

    public IdentitySummary identifyEntity() {
        if (!requireEnabled()) return null;
        return finish(IdentityActions.identifyEntity(config.savesToLibrary()));
    }

    /** 输出身份库统计到聊天栏 */
    public void reportStats() {
        ClientChat.send("身份库统计：" + IdentityActions.statsText());
    }

    /** 清理失效的识别目标 */
    public void pruneTargets() {
        int pruned = IdentityActions.pruneInvalidTargets();
        ClientChat.send(pruned == 0 ? "没有失效的识别目标" : "已清理 " + pruned + " 项失效的识别目标");
    }

    public IdentityModuleConfig config() {
        return config;
    }

    public List<String> modeLabels() {
        return config.modeLabels();
    }

    public int modeIndex() {
        return config.modeIndex();
    }

    /** 切换识别模式并立即持久化 */
    public void setModeIndex(int index) {
        config.setModeIndex(index);
        persist();
        ClientChat.send("识别模式：" + config.mode().displayName() + "（" + IdentityModuleConfig.describe(config.mode()) + "）");
    }

    public boolean verbose() {
        return config.verbose();
    }

    /** 切换详细输出并立即持久化 */
    public void setVerbose(boolean value) {
        config.setVerbose(value);
        persist();
    }

    /** 最近一次识别结果的一行文案 */
    public String latestText() {
        IdentitySummary summary = latest;
        return summary == null ? "无" : summary.kind().displayName() + " ｜ " + summary.statusText();
    }

    public String statsText() {
        return IdentityActions.statsText();
    }

    // ── 内部 ──

    private void persist() {
        ModuleManager.saveSettings(this);
    }

    private boolean requireEnabled() {
        if (isEnabled()) return true;
        ClientChat.send("§c模块未启用：" + displayName() + "（" + CommandManager.PREFIX + "module on " + id() + "）");
        return false;
    }

    private IdentitySummary finish(IdentitySummary summary) {
        latest = summary;
        report(summary);
        return summary;
    }

    private void report(IdentitySummary summary) {
        if (!summary.success()) {
            String reason = summary.rows().isEmpty() ? "未知原因" : summary.rows().get(0).value();
            ClientChat.send("§c" + summary.kind().displayName() + "识别失败：" + reason);
            return;
        }
        ClientChat.send("§b" + summary.kind().displayName() + "识别：§f" + summary.title());
        if (summary.saved()) {
            ClientChat.send("§7已写入身份库：" + summary.fileName());
        } else if (config.savesToLibrary()) {
            ClientChat.send("§7未写入身份库（该身份已存在或写入失败）");
        } else {
            ClientChat.send("§7当前为只展示模式，未写入身份库");
        }
        if (!config.verbose()) return;
        for (IdentitySummary.Row row : summary.rows()) {
            ClientChat.send("§7" + row.label() + "：§f" + row.value());
        }
    }
}
