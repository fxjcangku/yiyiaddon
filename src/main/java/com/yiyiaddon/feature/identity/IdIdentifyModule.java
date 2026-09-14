package com.yiyiaddon.feature.identity;

import com.google.gson.JsonObject;
import com.yiyiaddon.command.ClientCommand;
import com.yiyiaddon.command.CommandManager;
import com.yiyiaddon.config.identity.IdentityTargetConfig;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.CommandMessageFormatter;
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
import com.yiyiaddon.module.CategoryRegistry;
import com.yiyiaddon.platform.GameProbe;
import com.yiyiaddon.platform.storage.GamePaths;
import com.yiyiaddon.service.identity.IdentityService;
import com.yiyiaddon.ui.page.ModulePage;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * ID 识别模块（旧项目 {@code IdIdentifyModule}）：只管「识别」这一件事。
 *
 * <p><b>用户交互资产：</b>模块中文名 {@code ID识别}、description 与全部播报文本均沿用旧项目原文，
 * 禁止改写。分类不属迁移资产：本模块与其余全部模块统一归入本项目唯一的模块分组
 * {@link CategoryRegistry#MODULE_GROUP_ID}。数据管理相关功能由 {@link IdConfigModule} 独立承载，
 * 与旧项目一样是两个可单独开关的模块。</p>
 *
 * <p>业务实现复用第五阶段产物：识别走 {@code ItemIdentifier} / {@code BlockIdentifier} /
 * {@code EntityIdentifier}，数据走 {@code IdentityService}，目标选择走 {@code IdentityTargetConfig}，
 * 本模块只负责把它们串起来并给出中文回显。</p>
 */
public final class IdIdentifyModule extends Module {

    /** 模块 ID，同时作为状态文件键与快捷键键名后缀 */
    public static final String MODULE_ID = "id_identify";

    /** 回执前缀使用的模块名：旧项目 {@code IdCommand.MODULE_NAME} 原文 */
    public static final String MESSAGE_MODULE = "ID识别";

    /** 图标字形，与界面搜索图标同一字形（已确认存在于所引字体） */
    private static final String ICON = "\uE8B6";

    private final IdentityModuleConfig config = new IdentityModuleConfig();

    /** 本会话最近一次识别结果；关闭模块时清空 */
    private volatile IdentitySummary latest;

    public IdIdentifyModule() {
        super(MODULE_ID, MESSAGE_MODULE, CategoryRegistry.MODULE_GROUP_ID,
                "识别手持物品或准星方块并加入ID配置。点击开启即识别。");
    }

    @Override
    public String name() {
        return "IdIdentify";
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
        pruneSilently();
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
        pruneSilently();
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
        IdentityService service = IdentityService.shared();
        CommandMessageFormatter.of(MESSAGE_MODULE, "身份库")
                .field("物品", "§f" + service.itemCount() + " 项")
                .field("实体", "§f" + service.entityCount() + " 项")
                .field("方块", "§f" + service.blockCount() + " 项")
                .field("物品快照", "§f" + service.itemSnapshotCount() + " 项")
                .field("方块快照", "§f" + service.blockSnapshotCount() + " 项")
                .field("已选目标", "§f" + IdentityTargetConfig.countText(service))
                .field("数据目录", "§f" + GamePaths.identityRoot())
                .status(CommandMessageFormatter.Level.INFO, "统计完成")
                .send();
    }

    /** 清理失效的识别目标 */
    public void pruneTargets() {
        int pruned = IdentityActions.pruneInvalidTargets();
        ClientChat.send(MESSAGE_MODULE, pruned == 0 ? "§7没有失效的识别目标" : "§7已清理 " + pruned + " 项失效的识别目标");
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
        CommandMessageFormatter.of(MESSAGE_MODULE, "识别模式")
                .field("当前模式", "§f" + config.mode().displayName())
                .field("说明", "§7" + IdentityModuleConfig.describe(config.mode()))
                .status(CommandMessageFormatter.Level.SUCCESS, "已切换").send();
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

    /** 清理失效的识别目标，仅在确有清理时提示 */
    private void pruneSilently() {
        int pruned = IdentityActions.pruneInvalidTargets();
        if (pruned > 0) ClientChat.send(MESSAGE_MODULE, "§7已清理 " + pruned + " 项失效的识别目标");
    }

    private boolean requireEnabled() {
        if (isEnabled()) return true;
        ClientChat.send(MESSAGE_MODULE, "§6§l模块未启用（" + CommandManager.PREFIX + "module on " + id() + " 可开启）");
        return false;
    }

    private IdentitySummary finish(IdentitySummary summary) {
        latest = summary;
        report(summary);
        return summary;
    }

    /**
     * 播报识别结果，文本与旧项目逐字一致。
     *
     * <p>成功沿用旧项目 {@code §a§l✓ 已识别物品 §8▸ <名>}；失败沿用旧项目 {@code notifyError}
     * 的橙色加粗单行；「已存在」分支沿用旧项目的稳定身份判定说明。</p>
     */
    private void report(IdentitySummary summary) {
        IdentitySummary.Kind kind = summary.kind();
        if (!summary.success()) {
            String reason = summary.rows().isEmpty() ? "未知原因" : summary.rows().get(0).value();
            ClientChat.send(MESSAGE_MODULE, "§6§l" + reason);
            return;
        }
        ClientChat.send(MESSAGE_MODULE, "§a§l✓ 已识别" + kind.displayName() + " §8▸ §a§l" + summary.title());
        if (summary.saved()) {
            ClientChat.send(MESSAGE_MODULE,
                    CommandMessageFormatter.line("保存文件", "§f" + summary.fileName()));
        } else if (config.savesToLibrary()) {
            ClientChat.send(MESSAGE_MODULE, alreadyText(kind));
        }
        if (!config.verbose()) return;
        for (IdentitySummary.Row row : summary.rows()) {
            ClientChat.send(MESSAGE_MODULE, CommandMessageFormatter.line(row.label(), "§f" + row.value()));
        }
    }

    /** 「已在配置中」文案：沿用旧项目原文 */
    private static String alreadyText(IdentitySummary.Kind kind) {
        return switch (kind) {
            case ITEM -> "§7该物品已在 ID 配置中（稳定身份相同）";
            case BLOCK -> "§7该方块已在方块记录中（稳定身份相同）";
            case ENTITY -> "§7该实体已在实体 ID 配置中";
        };
    }
}
