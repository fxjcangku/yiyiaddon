package com.yiyiaddon.feature.enchant;

import com.google.gson.JsonObject;
import com.yiyiaddon.command.ClientCommand;
import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.enchant.command.FumoCommand;
import com.yiyiaddon.feature.enchant.config.EnchantSettings;
import com.yiyiaddon.feature.enchant.fsm.EnchantStateMachine;
import com.yiyiaddon.feature.enchant.gear.GearEnchantConfig;
import com.yiyiaddon.feature.enchant.gear.TargetProfile;
import com.yiyiaddon.feature.enchant.model.EnchantPointType;
import com.yiyiaddon.feature.enchant.model.EnchantRunMode;
import com.yiyiaddon.feature.enchant.model.EnchantTargetMode;
import com.yiyiaddon.feature.enchant.navigation.EnchantPathing;
import com.yiyiaddon.feature.enchant.render.EnchantPointRenderer;
import com.yiyiaddon.feature.enchant.repository.EnchantPointStore;
import com.yiyiaddon.feature.enchant.service.EnchantBindingService;
import com.yiyiaddon.feature.enchant.service.EnchantContainer;
import com.yiyiaddon.feature.enchant.ui.EnchantPage;
import com.yiyiaddon.platform.container.SilentContainer;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.render.world.WorldOverlay;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 自动附魔模块（旧项目 {@code AutoEnchantBook}，3,230 行）。
 *
 * <p>三模式互斥：{@code GEAR} 原版装备附魔 / {@code BOOK} 原版附魔书 / {@code CUSTOM} 自定义附魔；
 * 每个模式有独立的运行模式开关（纯附魔 / 挂机循环）。</p>
 *
 * <p><b>批次进度</b>（施工批次见 {@code 38-附A-新窗口开工指令.md}）：</p>
 * <ul>
 *   <li>批次2（已完成）：设置载体、9 类点位落盘、两套资源；</li>
 *   <li>批次3（已完成）：附魔数据层（原版静态库 / 目标匹配 / 评分 / 铁砧规划 / 装备目录）；</li>
 *   <li>批次4（已完成）：30 态状态机 {@link EnchantStateMachine} + 容器发包 {@link EnchantContainer}
 *       + Baritone 寻路 {@link EnchantPathing} + 六点位 ESP {@link EnchantPointRenderer}
 *       + 挂机刷经验（联动杀戮光环）；</li>
 *   <li>批次5（本批次）：控制台（概览 / 点位 / 基础设置 / 模式专属页，
 *       {@link com.yiyiaddon.feature.enchant.ui.EnchantConsoleScreen}）+ 使用说明
 *       （{@link EnchantPage}）+ {@code .fumo} 指令
 *       （{@link com.yiyiaddon.feature.enchant.command.FumoCommand}）+ 点位绑定唯一实现
 *       （{@link EnchantBindingService}）。</li>
 * </ul>
 *
 * <p><b>播报口径</b>：旧基类 {@code YiyiaddonModule} 的 {@code notify} / {@code notifyError}
 * 在本项目对应 {@link #info(String)}（白色正文）/ {@link #error(String)}（橙色加粗正文），
 * 前缀由 {@link ClientChat} 统一拼装；状态机里的文案色码逐字照抄旧源码。</p>
 */
public final class EnchantModule extends Module {

    public static final String MODULE_ID = "enchant";

    /** 播报前缀里的模块名（旧项目 {@code FumoCommand.MODULE_NAME}、{@code :539}，逐字） */
    public static final String MESSAGE_MODULE = "自动附魔";

    /**
     * 模块卡片图标：Material 符号 {@code auto_fix_high}（魔棒）。
     *
     * <p>码点 U+E663 已用脚本解析 {@code MaterialSymbolsRounded.ttf} 的 cmap 子表（format 4）验真存在，
     * 满足开发习惯第 140 条；若实机字形不合意，同字体内的 {@code U+EA19}（menu_book 书）与
     * {@code U+E65F}（auto_awesome 星光）同为已验证码点，换这一行即可。</p>
     */
    private static final String ICON = "\uE663";

    @Override
    public String icon() {
        return ICON;
    }

    private final EnchantSettings settings = new EnchantSettings();
    private final EnchantPointStore pointStore = new EnchantPointStore();

    private final EnchantPathing pathing;
    private final EnchantContainer container;
    private final EnchantPointRenderer renderer;
    private final EnchantStateMachine fsm;

    /** 点位绑定 / 移除的唯一实现（控制台「点位」页与 {@code .fumo} 指令共用） */
    private final EnchantBindingService bindingService;

    public EnchantModule() {
        // 描述逐字来自旧项目 AutoEnchantBook:539
        super(MODULE_ID, MESSAGE_MODULE, "automation",
            "经验获取→定向附魔→极品剔除→洗练仓储全自动闭环。详细参考下面使用说明。");

        this.pathing = new EnchantPathing();
        this.container = new EnchantContainer(this);
        this.renderer = new EnchantPointRenderer(this);
        this.fsm = new EnchantStateMachine(this, container, pathing);
        this.bindingService = new EnchantBindingService(this);
    }

    public EnchantSettings settings() {
        return settings;
    }

    public EnchantPointStore pointStore() {
        return pointStore;
    }

    /** 分类内排序：自动化分类第五位（… → 自动挖矿 → 自动附魔 → 自动图书管理员 → …） */
    @Override
    public int order() {
        return 50;
    }

    // ── 设置持久化 ──

    @Override
    public void loadSettings(JsonObject json) {
        settings.load(json);
    }

    @Override
    public void saveSettings(JsonObject json) {
        settings.save(json);
    }

    /** 立即写回设置（控制台改动即时生效，与旧项目设置自动保存一致） */
    public void persistSettings() {
        ModuleManager.saveSettings(this);
    }

    // ── 当前模式的运行模式（旧项目 {@code 当前运行模式()}，{@code :499-505}） ──

    /** 三个模式各自独立的运行模式开关，只返回当前生效的那一个 */
    public EnchantRunMode currentRunMode() {
        return switch (settings.targetMode) {
            case GEAR -> settings.gearRunMode;
            case BOOK -> settings.bookRunMode;
            case CUSTOM -> settings.customRunMode;
        };
    }

    /**
     * 当前配置对应的原版装备极品方案快照（旧 {@code 装备附魔配置.currentProfile()}，{@code :868}）。
     *
     * <p>评分 / 规划 / 最终验收的唯一目标依据；未选装备时返回 {@code null}，
     * 由启动检查链给出「请先在「原版装备附魔」分类选择装备与极品方案！」。</p>
     */
    public TargetProfile gearProfile() {
        return new GearEnchantConfig(settings.gearEnchantConfig).currentProfile();
    }

    // ── 生命周期 ──

    @Override
    protected void onEnable() {
        // 点位重读：启动检查链读的就是这份点位，进世界后再读一次确保磁盘最新
        pointStore.reload();

        // ESP：注册世界渲染层，关闭时注销
        WorldOverlay.register(MODULE_ID, renderer::render);

        // 启动检查链（旧 onActivate:847-947）：不通过时状态机自己延后一帧关掉模块
        fsm.activate();
    }

    @Override
    protected void onDisable() {
        // 关闭清理（旧 onDeactivate:951-956，去掉日志功能那一句）
        fsm.deactivate();
        WorldOverlay.unregister(MODULE_ID);
    }

    // ── 事件 ──

    @Override
    public Set<ClientEventType> subscribedEvents() {
        return Set.of(ClientEventType.TICK, ClientEventType.SCREEN_OPEN);
    }

    /**
     * 事件分派：{@code SCREEN_OPEN} ← 旧 {@code onOpenScreen:986-1014}（静默容器）。
     *
     * <p>容器操作状态下模块自己发包打开的容器界面一律取消显示（不抢鼠标），
     * 数据仍由 {@code mc.player.containerMenu} 同步，状态机照常发包操作。
     * 玩家自己开着界面时，原版传送会把「加载地形中」无条件盖上来（用户 2026-09-19）：拦掉。</p>
     */
    @Override
    public void onEvent(ClientEvent event) {
        if (event == null || event.type() != ClientEventType.SCREEN_OPEN) return;
        if (SilentContainer.isLevelLoadingHijack(event.payload())) {
            event.cancel();
            return;
        }
        if (fsm.onScreenOpen(event.payload())) {
            // 玩家手动开的箱子：压掉 + 收掉那个容器（真不给开）+ 动作栏提示
            SilentContainer.rejectPlayerContainer();
            event.cancel();
        }
    }

    /** 每刻推进状态机（旧 {@code onTick:1036-1098} 的全部前置门控与 30 态分派都在状态机内） */
    @Override
    public void onTick(Minecraft client) {
        if (client.player == null || client.level == null) return;
        fsm.tick();
    }

    // ── 启用前自检 ──

    /**
     * 按当前目标模式只检查该模式需要的点位（旧项目 {@code selfCheck:1760-1772} 逐条一致）。
     *
     * <p>三种模式互不污染：装备模式不检查空白书箱，附魔书 / 自定义模式不检查铁砧与装备箱；
     * 挂机点仅在「挂机循环」下需要，纯附魔模式跳过。文案格式 {@code §6<点位名>§f·未绑定} 逐字照抄。</p>
     */
    @Override
    public List<String> selfCheck() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return List.of();

        pointStore.reload();

        List<String> missing = new ArrayList<>();
        EnchantTargetMode mode = settings.targetMode;
        EnchantRunMode runMode = currentRunMode();
        for (EnchantPointType type : EnchantPointType.requiredFor(mode)) {
            if (type == EnchantPointType.AFK && runMode == EnchantRunMode.DRAIN) continue;
            if (!pointStore.has(type)) missing.add("§6" + type.title() + "§f·未绑定");
        }
        return missing;
    }

    // ── 播报（配色语义与旧基类 YiyiaddonModule 一致） ──

    /** 普通信息（旧 {@code notify}：正文白色） */
    public void info(String message) {
        ClientChat.send(MESSAGE_MODULE, message);
    }

    /** 错误信息（旧 {@code notifyError}：正文橙色加粗） */
    public void error(String message) {
        ClientChat.send(MESSAGE_MODULE, "§6§l" + message);
    }

    // ── 界面与指令（批次5） ──

    /**
     * 模块独立页：② 打开控制台 → ④ 内嵌说明正文（顺序照开发习惯第 182 条不可颠倒）。
     *
     * <p>页面内容由 {@link com.yiyiaddon.feature.enchant.ui.EnchantPage#createPage} 在真正打开时构建
     * （开发习惯第 180 条）：本方法会在模组初始化阶段被调用一次用于判空，构造期不得读注册表 / 点位磁盘。</p>
     */
    @Override
    public ModulePage page() {
        return new EnchantPage(this);
    }

    /** {@code .fumo} 点位指令（点位绑定 / 移除与控制台共用 {@link #bindingService()}） */
    @Override
    public List<ClientCommand> commands() {
        return List.of(new FumoCommand());
    }

    /** 点位绑定 / 移除的唯一实现（控制台「点位」页与 {@code .fumo} 指令共用同一处） */
    public EnchantBindingService bindingService() {
        return bindingService;
    }

    /** 状态机只读访问（控制台概览页 / 状态条显示当前状态用，不对外暴露操作） */
    public EnchantStateMachine fsm() {
        return fsm;
    }
}
