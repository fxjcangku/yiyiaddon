package com.yiyiaddon.feature.identity;

import com.google.gson.JsonObject;
import com.yiyiaddon.command.ClientCommand;
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
import com.yiyiaddon.feature.identity.ui.IdScreens;
import com.yiyiaddon.feature.identity.ui.IdentityModulePage;
import com.yiyiaddon.model.identity.BlockIdentity;
import com.yiyiaddon.model.identity.EntityIdentity;
import com.yiyiaddon.model.identity.IdentifyMode;
import com.yiyiaddon.model.identity.ItemIdentity;
import com.yiyiaddon.platform.GameProbe;
import com.yiyiaddon.platform.identity.BlockIdentifier;
import com.yiyiaddon.platform.identity.EntityIdentifier;
import com.yiyiaddon.platform.identity.ItemIdentifier;
import com.yiyiaddon.platform.resource.BlockStateModelResolver;
import com.yiyiaddon.platform.storage.GamePaths;
import com.yiyiaddon.service.identity.IdentityService;
import com.yiyiaddon.ui.page.ModulePage;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

/**
 * ID 识别模块（旧项目 {@code IdIdentifyModule}）：只管「识别」这一件事。
 *
 * <p><b>用户交互资产：</b>模块中文名 {@code ID识别}、分类 {@code 辅助}、description 与全部播报
 * 文本均沿用旧项目原文，禁止改写。数据管理相关功能由 {@link IdConfigModule} 独立承载，
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

    /** 「点击开启即识别」后等待自关的刻数：约 0.4 秒，足够开关滑块滑到位再回弹 */
    private static final int SELF_CLOSE_DELAY_TICKS = 8;

    private final IdentityModuleConfig config = new IdentityModuleConfig();

    /** 自关倒计时（刻）；归零即关闭自身。0 表示没有待执行的自关 */
    private int pendingSelfCloseTicks;

    public IdIdentifyModule() {
        super(MODULE_ID, MESSAGE_MODULE, "assist", "识别手持物或准星方块入库");
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
        // 旧项目「点击开启即识别」：本模块是一次性工具，开启即按当前模式识别一次，随后静默关闭自身。
        // 与旧项目 closeQuietly 同款——识别结果已经播报过，关闭时不再叠加一条「已关闭」。
        identifyByMode();
        // 自关延后几刻执行：识别是同步完成的，但开关滑块需要时间滑到「开」的位置；同一帧内立刻反向
        // 关闭会让弹簧两次目标相互抵消，开关看起来像没动。这里只拉开时序，行为仍是「识别一次后必定
        // 自关」，不额外播报任何东西。
        pendingSelfCloseTicks = SELF_CLOSE_DELAY_TICKS;
    }

    @Override
    public void onTick(Minecraft client) {
        if (pendingSelfCloseTicks <= 0) return;
        if (--pendingSelfCloseTicks == 0) closeQuietly();
    }

    @Override
    protected void onDisable() {
        // 延后期内被手动关闭：撤销待执行的自关，避免重复关闭
        pendingSelfCloseTicks = 0;
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
        // 初始同步一次：旧项目在构造末尾把开关当前值同步给解析器（onChanged 只在变更时触发）
        BlockStateModelResolver.setVerbose(config.blockSemanticDebug());
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

    /**
     * 识别手持物品。
     *
     * <p><b>分流口径与旧项目逐字一致：</b>只有「自动保存」写盘；「聊天复制/显示」与
     * 「准星方块识别」一律弹识别结果窗口、识别本身不写盘。</p>
     *
     * <p><b>不看模块开关：</b>旧项目 {@code IdCommand} 里没有任何启用状态判断，{@code .id} 指令随
     * 时可用；本模块是「点击开启即识别」的一次性工具，开启后立刻自关，若给指令入口加启用闸门，
     * 指令就会被永久挡住。</p>
     */
    public IdentitySummary identifyItem() {
        if (config.mode() != IdentifyMode.AUTO_SAVE) return openItemResultScreen();
        return finish(IdentityActions.identifyItem(true));
    }

    /**
     * 「聊天复制/显示」模式：识别手持物品并弹出识别结果窗口，识别本身不写盘。
     *
     * <p>与旧项目一致：失败时聊天栏给出中文原因；成功后直接开窗，不在聊天栏重复罗列字段。</p>
     */
    private IdentitySummary openItemResultScreen() {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) {
            ClientChat.send(MESSAGE_MODULE, "§6§l玩家未加载");
            return null;
        }
        ItemStack held = client.player.getItemInHand(InteractionHand.MAIN_HAND);
        if (held == null || held.isEmpty()) held = client.player.getItemInHand(InteractionHand.OFF_HAND);
        if (held == null || held.isEmpty()) {
            ClientChat.send(MESSAGE_MODULE, "§6§l没有可识别物品：主手和副手都是空的");
            return null;
        }
        ItemIdentity identity = ItemIdentifier.identifyItem(held);
        if (identity == null) {
            ClientChat.send(MESSAGE_MODULE, "§6§l识别失败");
            return null;
        }
        IdentitySummary summary = IdentitySummary.ok(IdentitySummary.Kind.ITEM,
                identity.displayName(), List.of(), null, null);
        client.execute(() -> IdScreens.openItemResult(identity, client.screen));
        return summary;
    }

    /**
     * 识别准星方块。
     *
     * <p><b>分流口径与旧项目逐字一致：</b>只有「自动保存」写盘；「聊天复制/显示」与
     * 「准星方块识别」一律弹方块结果窗口、识别本身不写盘。</p>
     */
    public IdentitySummary identifyBlock() {
        if (config.mode() != IdentifyMode.AUTO_SAVE) return openBlockResultScreen();
        return finish(IdentityActions.identifyBlock(true));
    }

    /** 识别准星实体并弹出实体结果窗口（旧项目 {@code .id 实体} 的行为）。 */
    public IdentitySummary identifyEntity() {
        return openEntityResultScreen();
    }

    /**
     * 「准星方块识别」模式：识别准星命中的方块并弹出方块结果窗口，识别本身不写盘。
     */
    private IdentitySummary openBlockResultScreen() {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) {
            ClientChat.send(MESSAGE_MODULE, "§6§l玩家未加载");
            return null;
        }
        BlockIdentity identity = BlockIdentifier.identify();
        if (identity == null) {
            ClientChat.send(MESSAGE_MODULE, "§6§l自动识别失败：准星当前没有指向有效方块");
            return null;
        }
        IdentitySummary summary = IdentitySummary.ok(IdentitySummary.Kind.BLOCK,
                identity.displayName(), List.of(), null, null);
        client.execute(() -> IdScreens.openBlockResult(identity, client.screen));
        return summary;
    }

    /**
     * 识别准星实体并弹出实体结果窗口，识别本身不写盘（结果窗口内可保存）。
     */
    private IdentitySummary openEntityResultScreen() {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) {
            ClientChat.send(MESSAGE_MODULE, "§6§l玩家未加载");
            return null;
        }
        if (client.crosshairPickEntity == null) {
            ClientChat.send(MESSAGE_MODULE, "§6§l当前准星未指向可识别实体");
            return null;
        }
        EntityIdentity identity = EntityIdentifier.identifyEntity(client.crosshairPickEntity);
        if (identity == null) {
            ClientChat.send(MESSAGE_MODULE, "§6§l无法解析该实体的稳定身份");
            return null;
        }
        IdentitySummary summary = IdentitySummary.ok(IdentitySummary.Kind.ENTITY,
                identity.displayName(), List.of(), null, null);
        client.execute(() -> IdScreens.openEntityResult(identity, client.screen));
        return summary;
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
    }

    public boolean blockSemanticDebug() {
        return config.blockSemanticDebug();
    }

    /** 方块语义调试：开关立即作用到解析器并持久化（旧项目同款即时生效） */
    public void setBlockSemanticDebug(boolean value) {
        config.setBlockSemanticDebug(value);
        BlockStateModelResolver.setVerbose(value);
        persist();
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

    private IdentitySummary finish(IdentitySummary summary) {
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
        } else {
            ClientChat.send(MESSAGE_MODULE, alreadyText(kind));
        }
        // 方块识别在自动保存下是两次落盘：稳定记录一条、状态快照一条（旧项目同一张卡里的两个字段）
        if (summary.snapshotSaved()) {
            ClientChat.send(MESSAGE_MODULE,
                    CommandMessageFormatter.line("状态快照", "§f" + summary.snapshotName()));
        }
    }

    /** 静默关闭自身（旧项目 {@code closeQuietly}）：不播报开关状态，避免与识别结果重复刷屏 */
    private void closeQuietly() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        // 延后一帧：本方法在 onEnable 回调内被调用，同步关闭会打断正在进行的启用流程
        client.execute(() -> ModuleManager.setEnabledSilently(MODULE_ID, false));
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
