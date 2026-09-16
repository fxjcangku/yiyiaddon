package com.yiyiaddon.feature.mining.ui;

import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.feature.mining.config.MiningSettings;
import com.yiyiaddon.feature.mining.model.LootMode;
import com.yiyiaddon.feature.mining.model.MiningPoint;
import com.yiyiaddon.feature.mining.model.MiningPointType;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.ui.component.ButtonRow;
import com.yiyiaddon.ui.component.CollapsibleSection;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.KeybindBadge;
import com.yiyiaddon.ui.component.ListRow;
import com.yiyiaddon.ui.component.ModuleStatusBar;
import com.yiyiaddon.ui.console.ConsoleMetrics;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.screen.HelpPanelScreen;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingSegmented;
import com.yiyiaddon.ui.widget.SettingText;
import com.yiyiaddon.ui.widget.SettingTextBox;
import com.yiyiaddon.ui.widget.SettingToggle;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

import java.util.List;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.Supplier;

/**
 * 自动挖矿模块的独立配置页（旧项目 {@code AutoMinerModule.getWidget} + {@code buildLocationCard}
 * + {@code buildHelpContent}，{@code :1503-1683} 的移植）。
 *
 * <p><b>用户交互资产（逐字，禁止改写）：</b>按钮 {@code §e查看使用说明}、三个点位名
 * （{@code 矿物箱} / {@code 食物箱} / {@code 挂机修复点}）、点位卡片文案（{@code §a设置} /
 * {@code §8设置} / {@code §c删除} / {@code §8暂未绑定} / {@code §8-} 与坐标串）、
 * 5 个分组名（{@code 目标选择} / {@code 传送指令} / {@code 触发条件} / {@code 物品管理} /
 * {@code Baritone调优}）、50 个设置项的名称与描述、帮助页 8 个章节的全部正文，全部来自旧项目原文；
 * 主题、控件形态、排布方式用本项目现有体系。</p>
 *
 * <p><b>设置读写：</b>每行直接读写 {@link MiningSettings} 的字段（不建第二份数据），改动立即
 * {@link AutoMinerModule#persistSettings()} 落盘；旧项目有 {@code onChanged} 下调 Baritone 的设置项，
 * 按 40 号附录 B §2.6 表格逐项调 {@link com.yiyiaddon.feature.mining.navigation.MiningPathing#updateSetting}，
 * 键名与旧项目逐个对齐。数值项一律用数值输入框（旧项目全部 {@code noSlider}），无滑块。</p>
 *
 * <p><b>可见性联动 4 处</b>（旧 {@code visible(...)}）：{@code GUI按钮关键词} ← {@code RTP需要GUI选择}、
 * {@code 怪物规避半径} ← {@code 怪物规避}、{@code 暴露矿石检测距离} ← {@code 仅挖暴露矿石}、
 * {@code 合法挖掘高度} ← {@code 合法挖掘模式}；另有第 5 处联动是 {@code 采集模式} 切换后同步已选目标
 * （{@link AutoMinerModule#syncTargetsOnModeSwitch()}，候选集合在每次打开选择器时按当前模式重算）。</p>
 *
 * <p><b>点位绑定：</b>三行卡片与后续 {@code .wk} 指令共用唯一实现
 * {@link com.yiyiaddon.feature.mining.service.MiningBindingService}（GUI 路径的文案多「请重新设置」、
 * 解绑回执带 {@code §c§l✗}，与指令路径两套并存，禁止统一）。卡片状态在页面构建时读一次，
 * 每次重新进入页面都是一个新实例，因此删除 / 绑定后重开必然同步（旧项目靠每次 getWidget 重读）。</p>
 *
 * <p><b>与旧项目差异（登记）：</b></p>
 * <ol>
 *   <li>{@code 检测假矿} 按钮<b>不落地</b>（随种子模式留白，用户裁定）；帮助页「指令系统」章节里
 *       {@code §8> §3.wk 检测假矿 §8— §7检测周围假矿 §7(需启用种子挖矿)} 那一行按同一裁定<b>留白不写</b>，
 *       但「种子挖矿」章节标题与逐字正文作为帮助文案资产完整保留（对应功能留白）。</li>
 *   <li>旧项目「一行三列卡片」在本项目没有对应控件，改为三行 {@link ListRow}（用户 2026-09-16 拍板）：
 *       卡片的六段文案一字不改，坐标行与维度行合并到同一行明细里（原两行之间用两个空格分隔）。</li>
 *   <li>4 项隐藏 ESP 设置（{@code _esp_scale_internal} 与三色）不上页面（一比一），值仍落盘。</li>
 *   <li>列表类设置（保留白名单 / 食物白名单 / 搭路方块白名单）与单值目标（主世界矿石 / 下界矿石 /
 *       普通方块）都改成<b>选择器行</b>：名称 + 说明 …… [点击选择] [状态文字] [↻]，行样式照本项目
 *       星露谷控制台页 {@code StardewPlantingPage:69-85}（用户 2026-09-16 指定参照）。状态文字多选
 *       用星露谷口径 {@code 已选 N / M 项} / {@code 未选择（共 N 项）}；单值目标已选时显示当前产物名
 *       （旧项目该行本来就是显示名称）。↻ = 清空本行已选（与星露谷同一个图标、同一个动作）。
 *       旧项目「选中空气＝清空」的语义保留在模块判定层，但<b>不再把空气列成候选或显示值</b>。</li>
 *   <li>旧项目分组由框架渲染（可折叠、首组默认展开），本项目用 {@link CollapsibleSection} 对齐。</li>
 * </ol>
 */
public final class AutoMinerPage extends CompactModulePage implements ModulePage {

    // ── 分组名（旧项目 :99-103 逐字） ──

    private static final String GROUP_TARGET = "目标选择";
    private static final String GROUP_COMMAND = "传送指令";
    private static final String GROUP_THRESHOLD = "触发条件";
    private static final String GROUP_ITEMS = "物品管理";
    private static final String GROUP_BARITONE = "Baritone调优";

    // ── 按钮与选择器的窗口标题（逐字） ──

    private static final String HELP_BUTTON = "§e查看使用说明";
    private static final String CONSOLE_BUTTON = "§b打开控制台";
    private static final String CONSOLE_HINT = "按用途分页：概览 / 点位 / 目标选择 / 传送指令 / 触发条件 / Baritone调优";
    private static final String BTN_SET_BOUND = "§a设置";
    private static final String BTN_SET_UNBOUND = "§8设置";
    private static final String BTN_DELETE = "§c删除";
    private static final String TITLE_OVERWORLD = "主世界矿石";
    private static final String TITLE_NETHER = "下界矿石";
    private static final String TITLE_BLOCK = "普通方块";
    private static final String TITLE_KEEP = "保留白名单";
    private static final String TITLE_FOOD = "食物白名单";
    private static final String TITLE_PLACE = "搭路方块白名单";

    /** 采集模式分段：顺序即 {@link LootMode} 的序数（精准采集 / 时运） */
    private static final List<String> LOOT_MODE_LABELS =
            List.of(LootMode.SILK_TOUCH.toString(), LootMode.FORTUNE.toString());

    /** 文本类设置（5 条传送指令）的输入框宽度与长度上限；同本项目其它自由文本设置 */
    private static final float TEXT_BOX_WIDTH = 220f;
    private static final int TEXT_MAX_LENGTH = 512;

    /** 选择器行的「点击选择」按钮（逐字照星露谷控制台页 {@code StardewPlantingPage:69}） */
    private static final String SELECT_LABEL = "点击选择";
    /**
     * 清空已选图标（Material Symbols {@code reset}，与星露谷控制台页同一字形、同一动作：
     * 清空本行已选，空则静默）；码点只在通用件 {@link ConsoleMetrics#GLYPH_RESET} 定义一处。
     */
    private static final String GLYPH_RESET = ConsoleMetrics.GLYPH_RESET;
    /** 状态文字字号：与 {@code SettingText} 内部字号一致，用于按文本宽度算列宽 */
    private static final float STATE_FONT_SIZE = 11f;

    /** 帮助页 8 个章节（旧 {@code buildHelpContent :1618-1683} 逐字；框线与 {@code [#]} 格式由 HelpPanelScreen 生成） */
    private static final HelpPanelScreen.HelpSection[] HELP_SECTIONS = {
        new HelpPanelScreen.HelpSection("准备工作",
            "  §8├─ §f准备好挖矿工具 §7(推荐附魔耐久、效率)",
            "  §8├─ §f准备好武器 §7(修补耐久时用)",
            "  §8├─ §f放置矿物箱、食物箱 §7(装满食物)",
            "  §8├─ §f选好挂机修复点 §7(安全区域，怪物可到达)",
            "  §8└─ §f配置页面顶部点击卡片按钮设置三个点位"
        ),
        new HelpPanelScreen.HelpSection("点位设置 §7(两种方式)",
            "  §b▸ §e方式1 §8- §f配置页面按钮",
            "    §7准星对准箱子 §8→ §f点击卡片中的设置按钮",
            "    §7箱子类型：矿物箱、食物箱自动检测容器",
            "    §7挂机修复点：直接站在目标位置即可绑定",
            "",
            "  §b▸ §e方式2 §8- §f指令系统",
            "    §8> §3.wk 设置 矿物箱 §8— §7准星对准箱子，绑定矿物贮箱",
            "    §8> §3.wk 设置 食物箱 §8— §7准星对准箱子，绑定食物补给箱",
            "    §8> §3.wk 设置 挂机修复点 §8— §7站在目标位置后自动绑定 §7(含视角)",
            "",
            "  §7§o容器检测：箱子类点位会自动检测目标方块是否为容器",
            "  §7§o不是容器 §8→ §7自动拒绝并提示重新设置，避免卡死"
        ),
        new HelpPanelScreen.HelpSection("指令系统",
            "  §8> §3.wk 状态 §8— §7查看绑定状态 §7(含坐标、维度、视角)",
            "  §8> §3.wk 移除 §c<目标> §8— §7解绑单个坐标",
            "  §8> §3.wk 清空 §8— §7清空所有绑定"
        ),
        new HelpPanelScreen.HelpSection("状态机流程",
            "  §a[1] §f前往挖矿 §8→ §7发送挖矿指令，等区块加载完成",
            "  §a[2] §f采掘 §8→ §7Baritone自动挖矿，满载/饿/耐久触发转换",
            "  §a[3] §f卸货循环 §8→ §7传送到矿物箱，卸货，返回野外",
            "  §a[4] §f补给循环 §8→ §7传送到箱，拿食物，吃饱，返回"
        ),
        new HelpPanelScreen.HelpSection("物品管理 §7(默认全丢)",
            "  §c▸ §f丢弃逻辑：除保留项外，背包其余物品全部自动丢弃",
            "  §a▸ §f默认保留：任意品质工具 §7(镐/铲/斧/剑/锄)§f、白名单食物、目标矿物",
            "  §a▸ §f搭路方块 §7(圆石/地狱岩) §f只保留各一组，多余自动丢弃",
            "  §e▸ §f保留白名单：不想被扔的物品/方块加进去就不会丢",
            "  §6⚠ §f启动前记得把想留的东西加进「保留白名单」"
        ),
        new HelpPanelScreen.HelpSection("参数建议",
            "  §6▸ §f满载组数 §8= §e36 §7(标准背包容量)",
            "  §6▸ §f食物阈值 §8= §e14 §7(7格肉约14饱食度)",
            "  §6▸ §f耐久阈值 §8= §e50 §7(低于50时自动修复)",
            "  §6▸ §f传送等待 §8= §e10秒 §7(RTP加载缓冲)"
        ),
        new HelpPanelScreen.HelpSection("种子挖矿 §7(可选)",
            "  §d▸ §f启用后状态机切换采集流程 §7(两种模式)",
            "    §7普通模式：Baritone mine 挖视野内所有目标矿",
            "    §7种子模式：逐块寻路到预测真矿，原版合法破坏，无视假矿",
            "  §d▸ §f预测位置无矿自动跳过，附近挖完自动重新RTP换区",
            "  §d▸ §f检测假矿：对准可疑方块 §8→ §f点击「检测假矿」按钮",
            "  §d▸ §f假矿判定：预测无矿但显示有矿 §8= §c假矿",
            "  §d▸ §f适用场景：防止挖到管理员放置的诱饵矿"
        ),
        new HelpPanelScreen.HelpSection("注意事项",
            "  §c⚠ §f模块运行中无法修改点位，必须先关闭模块",
            "  §c⚠ §f已绑定点位不允许覆盖，必须先删除再重新设置",
            "  §c⚠ §f传送指令需服务器支持，否则无法自动返回",
            "  §c⚠ §f挂机修复点会记录视角，用于精准对准修补工作台",
            "  §c⚠ §f默认全丢垃圾！想留下的物品务必先加进「保留白名单」"
        )
    };

    private final AutoMinerModule module;
    private final MiningSettings settings;
    /** 目标选择 / 物品管理两组的数据与控制（与控制台「目标选择」页共用同一实现） */
    private final MiningTargetControls targetControls;

    /** 页面内容是否已构建（见 {@link #createPage(ModuleEntry)} 的时序说明） */
    private boolean built;

    public AutoMinerPage(AutoMinerModule module) {
        this.module = module;
        this.settings = module.settings();
        // 本页的行由 Supplier 每帧现读状态，切换采集模式不需要整页重建
        this.targetControls = new MiningTargetControls(module, null);
    }

    /**
     * 页面内容在「真正打开页面」时才构建。
     *
     * <p><b>时序约束（实测崩溃根因）</b>：{@code module.page()} 会在模组初始化阶段被调用一次
     * （{@code ModuleEntries.of} 用它判空，见 {@code ModuleEntries:35}），此时游戏注册表
     * 尚未绑定组件，任何构造 {@code ItemStack} 的行为都会抛
     * {@code NullPointerException: Components not bound yet}（2026-09-16 实机崩溃
     * {@code crash-2026-09-16_01.58.11-client.txt}）。本页的目标矿石 / 白名单候选需要物品与
     * 方块注册表，因此构建必须推迟到用户打开页面时（{@code ModuleScreen:84-91} 在打开时
     * 才调用 {@code createPage}）。</p>
     */
    @Override
    public BasePage createPage(ModuleEntry entry) {
        if (!built) {
            built = true;
            // 卡片必须显示磁盘上的真实绑定：点位装载只在自检 / 启用时发生，而玩家可能先进页面配点位
            module.reloadStore();
            build();
        }
        return this;
    }

    @Override
    public String getTitle() {
        return module.displayName();
    }

    @Override
    public String getSubtitle() {
        return module.description();
    }

    // ── 构建 ──

    private void build() {
        setHeader(new ModuleStatusBar(
                () -> module.isEnabled() ? "运行中" : "未启用",
                module::isEnabled,
                new KeybindBadge(module.keybindId()),
                new SettingToggle(module::isEnabled,
                        value -> ModuleManager.setEnabled(module.id(), value))));

        // 顶部：控制台入口（星露谷同款形态）；下面一行仍是帮助入口
        addCore(new CompactRow("", () -> CONSOLE_HINT,
            new Button(CONSOLE_BUTTON, this::openConsole)).centeredControl());
        // 顶部：帮助入口（旧 :1508-1511；「检测假矿」按钮随种子模式留白，不落地）
        addCore(new ButtonRow(new Button(HELP_BUTTON, this::openHelp)));
        // 中部：三行点位卡片（旧 :1529-1535 的三张卡片）
        for (MiningPointType type : MiningPointType.values()) addCore(pointRow(type));
        // 下部：5 个设置分组（首组默认展开，其余折叠，旧 :99-103）
        buildSettings();
    }

    /**
     * 点位卡片行：标题 + 坐标与维度明细 + 「设置」「删除」。
     *
     * <p>已绑定时坐标与维度名逐字照旧（{@code §7X§f%d §7Y§f%d §7Z§f%d} / {@code §7维度名}），
     * 未绑定时两段占位为 {@code §8暂未绑定} 与 {@code §8-}；删除按钮无条件显示，条件门控在点击回调内
     * （旧 {@code :1601-1608}）。明细每帧现读：换维度 / 换服务器后不必重建整页就能看到本维度实况。</p>
     */
    private ListRow pointRow(MiningPointType type) {
        boolean bound = module.pointStore().has(type);

        return new ListRow(pointTitleColor(type) + type.displayName())
            .detail(() -> pointDetail(type))
            .action(new Button(bound ? BTN_SET_BOUND : BTN_SET_UNBOUND, () -> {
                // 绑定失败（准星未命中 / 非容器 / 该类型已绑）时留在页面：错误已经播报，玩家可当场重设
                if (module.bindingService().bind(type, true)) closeToGame();
            }))
            .action(new Button(BTN_DELETE, () -> {
                if (module.bindingService().remove(type, true)) closeToGame();
            }));
    }

    /**
     * 点位明细：已绑给坐标 + 维度名，未绑给 {@code §8暂未绑定  §8-}（与控制台点位页逐字同源）。
     */
    private String pointDetail(MiningPointType type) {
        MiningPoint point = module.pointStore().get(type);
        if (point == null) return "§8暂未绑定  §8-";
        return String.format("§7X§f%d §7Y§f%d §7Z§f%d  §7%s", point.x(), point.y(), point.z(),
            WorldIdentity.dimensionDisplayName(point.dimension()));
    }

    /** 卡片标题配色（旧 {@code :1561-1566}）：矿物箱金 / 食物箱绿 / 挂机修复点粉 */
    private static String pointTitleColor(MiningPointType type) {
        return switch (type) {
            case MINERAL -> "§6";
            case FOOD -> "§2";
            case AFK -> "§d";
        };
    }

    // ── 设置分组（顺序 = 旧项目 add 顺序 = 界面渲染顺序） ──

    private void buildSettings() {
        // 选择器行的候选总数：与各选择器实际列出的候选完全同源（同一表达式），只在页面构建时算一次
        targetControls.refreshTotals();

        buildTargetGroup();
        buildCommandGroup();
        buildThresholdGroup();
        buildItemsGroup();
        buildBaritoneGroup();
    }

    /** 目标选择（旧 :218-243），默认展开 */
    private void buildTargetGroup() {
        CollapsibleSection group = group(GROUP_TARGET, true);

        group.content().add(new CompactRow("采集模式",
            () -> "精准采集：目标选择器显示原矿；时运：目标选择器显示掉落物（粗铁/粗金/粗铜等）。切换模式时自动同步目标",
            new SettingSegmented(LOOT_MODE_LABELS, () -> settings.lootMode.ordinal(), targetControls::pickLootMode)));

        group.content().add(selectorRow(TITLE_OVERWORLD,
            "时运模式选掉落物（粗铁/粗金/粗铜等），精准采集选原矿（铁矿石等）",
            () -> targetControls.oreStatus(false),
            () -> targetControls.openOreSelector(TITLE_OVERWORLD, false),
            () -> targetControls.clearOreTarget(false)));

        group.content().add(selectorRow(TITLE_NETHER,
            "时运模式选掉落物（下界残骸/金粒/石英），精准采集选原矿（下界残骸等）",
            () -> targetControls.oreStatus(true),
            () -> targetControls.openOreSelector(TITLE_NETHER, true),
            () -> targetControls.clearOreTarget(true)));

        group.content().add(selectorRow(TITLE_BLOCK,
            "选择普通方块（石头、泥土、原木等）",
            targetControls::blockStatus,
            () -> targetControls.openBlockSelector(TITLE_BLOCK),
            targetControls::clearBlockTarget));
    }

    /** 传送指令（旧 :249-308） */
    private void buildCommandGroup() {
        CollapsibleSection group = group(GROUP_COMMAND, false);

        group.content().add(new CompactRow("前往挖矿指令",
            () -> "传送到挖矿区域的指令（支持带/或不带/）",
            textBox(() -> settings.wildCommand, value -> settings.wildCommand = value)));

        group.content().add(new CompactRow("RTP需要GUI选择",
            () -> "指令后自动扫描GUI点击匹配按钮",
            toggle(() -> settings.rtpGuiEnabled, value -> settings.rtpGuiEnabled = value, null)));

        group.content().add(visible(() -> settings.rtpGuiEnabled, new CompactRow("GUI按钮关键词",
            () -> "输入纯文本（如'主世界'会匹配'§a主 §e世 §b界'），自动忽略颜色和空格",
            textBox(() -> settings.rtpGuiKeyword, value -> settings.rtpGuiKeyword = value))));

        group.content().add(new CompactRow("返回卸货指令",
            () -> "传送到卸货箱的指令",
            textBox(() -> settings.unloadCommand, value -> settings.unloadCommand = value)));

        group.content().add(new CompactRow("前往补给指令",
            () -> "传送到食物箱的指令",
            textBox(() -> settings.supplyCommand, value -> settings.supplyCommand = value)));

        group.content().add(new CompactRow("前往修复指令",
            () -> "传送到挂机修补点",
            textBox(() -> settings.afkCommand, value -> settings.afkCommand = value)));

        group.content().add(new CompactRow("死亡返回指令",
            () -> "复活后返回挂机点",
            textBox(() -> settings.respawnCommand, value -> settings.respawnCommand = value)));

        group.content().add(new CompactRow("传送等待时长",
            () -> "执行传送指令后等待秒数",
            intBox(1, 120, () -> settings.teleportDelay, value -> settings.teleportDelay = value, null)));

        group.content().add(new CompactRow("RTP冷却时长",
            () -> "服务器 RTP 传送冷却秒数：传送失败后等这么久再重试，避免冷却期空发指令",
            intBox(1, 3600, () -> settings.rtpCooldown, value -> settings.rtpCooldown = value, null)));
    }

    /** 触发条件（旧 :314-345） */
    private void buildThresholdGroup() {
        CollapsibleSection group = group(GROUP_THRESHOLD, false);

        group.content().add(new CompactRow("满载组数",
            () -> "背包矿物达到多少组时触发卸货",
            intBox(1, 36, () -> settings.unloadThreshold, value -> settings.unloadThreshold = value, null)));

        group.content().add(new CompactRow("食物阈值",
            () -> "背包食物少于此数量时触发补给",
            intBox(1, 64, () -> settings.hungerThreshold, value -> settings.hungerThreshold = value, null)));

        group.content().add(new CompactRow("耐久阈值",
            () -> "工具剩余耐久低于此值时前往挂机点修补（下界合金镐耐久 2031，上限已放宽）",
            intBox(1, 3000, () -> settings.durabilityThreshold,
                value -> settings.durabilityThreshold = value, null)));

        group.content().add(new CompactRow("潜影盒打包机",
            () -> "卸货时把矿物箱(潜影盒)填满，检测到满后等红石推盒换新盒，自动重开箱继续放，直到背包目标矿放完才RTP。给搭配潜影盒打包机的挂机用户使用。",
            toggle(() -> settings.shulkerPacker, value -> settings.shulkerPacker = value, null)));
    }

    /** 物品管理（旧 :351-378） */
    private void buildItemsGroup() {
        CollapsibleSection group = group(GROUP_ITEMS, false);

        group.content().add(selectorRow(TITLE_KEEP,
            "默认保留任意品质工具、白名单食物、目标矿物；此名单内的额外物品/方块也不会被丢弃",
            targetControls::keepStatus,
            () -> targetControls.openKeepSelector(TITLE_KEEP),
            targetControls::clearKeepList));

        group.content().add(selectorRow(TITLE_FOOD,
            "从食物箱只拿选中的食物（只显示能吃的食物，默认常用食物，可自由增删）",
            targetControls::foodStatus,
            () -> targetControls.openFoodSelector(TITLE_FOOD),
            targetControls::clearFoodList));

        group.content().add(selectorRow(TITLE_PLACE,
            "Baritone搭桥/填坑时使用这些方块，且只保留各一组（多余自动丢弃）",
            targetControls::placeStatus,
            () -> targetControls.openPlaceSelector(TITLE_PLACE),
            targetControls::clearPlaceList));
    }

    /** Baritone 调优（旧 :433-670）：秒破 3 项 + 开关类 19 项 + 数值类 8 项（种子挖矿 5 项留白） */
    private void buildBaritoneGroup() {
        CollapsibleSection group = group(GROUP_BARITONE, false);

        group.content().add(new CompactRow("快速破坏（秒破）",
            () -> "使用 START→服务端 0.7 最早阈值→STOP 的真实发包流程加速破坏；硬方块会等待服务端所需 tick，不提前制造客户端空气墙",
            toggle(() -> settings.fastBreak, value -> settings.fastBreak = value, null)));

        group.content().add(new CompactRow("绕过反作弊",
            () -> "兼容旧配置：STOP 后对相邻位置补发一次 ABORT；不能保证绕过服务器反作弊，异常时请关闭",
            toggle(() -> settings.bypassAnticheat, value -> settings.bypassAnticheat = value, null)));

        group.content().add(new CompactRow("秒破间隔（tick）",
            () -> "服务端确认方块变化后，开始下一块前的最小等待 tick；不会用于提前重复发送 STOP",
            intBox(0, 20, () -> settings.breakInterval, value -> settings.breakInterval = value, null)));

        // ── 开关类（除寻路物流破坏方块 / 岩浆透视外，逐项同键下调 Baritone） ──
        group.content().add(new CompactRow("破坏阻挡方块",
            () -> "挖掘时允许破坏阻挡路径的方块（石头、泥土等）",
            toggle(() -> settings.allowBreak, value -> settings.allowBreak = value, "allowBreak")));

        group.content().add(new CompactRow("寻路物流破坏方块",
            () -> "前往矿物箱/食物箱/挂机点寻路时，是否允许破坏阻挡方块抄近路（关闭后旁边有路就绕行，不再挖墙）",
            toggle(() -> settings.logisticsBreakBlocks, value -> settings.logisticsBreakBlocks = value, null)));

        group.content().add(new CompactRow("放置方块",
            () -> "允许搭桥或填坑（需要背包里有方块）",
            toggle(() -> settings.allowPlace, value -> settings.allowPlace = value, "allowPlace")));

        group.content().add(new CompactRow("自动整理物品栏",
            () -> "允许Baritone自动将物品从背包移到快捷栏（工具、方块等）",
            toggle(() -> settings.allowInventory, value -> settings.allowInventory = value, "allowInventory")));

        group.content().add(new CompactRow("自动切换工具",
            () -> "挖掘时自动选择最佳工具（镐子挖石头、铲子挖土等）",
            toggle(() -> settings.autoTool, value -> settings.autoTool = value, "autoTool")));

        group.content().add(new CompactRow("避开岩浆",
            () -> "禁止 Baritone 将岩浆作为正常寻路路径",
            toggle(() -> settings.avoidLava, value -> settings.avoidLava = value, "avoidLava")));

        group.content().add(new CompactRow("岩浆透视",
            () -> "高亮显示附近岩浆方块，挖矿时更直观看到岩浆位置",
            toggle(() -> settings.lavaEsp, value -> settings.lavaEsp = value, null)));

        group.content().add(new CompactRow("岩浆透视范围",
            () -> "透视岩浆的扫描半径（格）",
            intBox(2, 16, () -> settings.lavaEspRange, value -> settings.lavaEspRange = value, null)));

        group.content().add(new CompactRow("怪物规避",
            () -> "提高怪物附近路径代价，尽量绕开危险区域",
            toggle(() -> settings.mobAvoidance, value -> settings.mobAvoidance = value, "avoidance")));

        group.content().add(new CompactRow("掉落方块暂停",
            () -> "遇到沙子、沙砾等掉落方块时暂停挖掘。关闭后不掉方块不暂停，挖矿更流畅（会塌方区域建议手动开启）",
            toggle(() -> settings.pauseMiningForFallingBlocks,
                value -> settings.pauseMiningForFallingBlocks = value, "pauseMiningForFallingBlocks")));

        group.content().add(new CompactRow("疾跑上坡",
            () -> "上坡时提前一格疾跑+跳跃，提升速度",
            toggle(() -> settings.sprintAscends, value -> settings.sprintAscends = value, "sprintAscends")));

        group.content().add(new CompactRow("允许跑酷",
            () -> "允许跨越1-4格的跑酷跳跃（有一定风险）",
            toggle(() -> settings.allowParkour, value -> settings.allowParkour = value, "allowParkour")));

        group.content().add(new CompactRow("跑酷搭桥",
            () -> "跑酷跳跃中途放置方块来延长距离（需开启放置方块）",
            toggle(() -> settings.allowParkourPlace, value -> settings.allowParkourPlace = value, "allowParkourPlace")));

        group.content().add(new CompactRow("对角线上升",
            () -> "允许斜向上跳跃，速度更快但消耗更多饥饿值",
            toggle(() -> settings.allowDiagonalAscend, value -> settings.allowDiagonalAscend = value, "allowDiagonalAscend")));

        group.content().add(new CompactRow("对角线下降",
            () -> "允许斜向下降，速度更快但有一定风险（地狱慎用）",
            toggle(() -> settings.allowDiagonalDescend, value -> settings.allowDiagonalDescend = value, "allowDiagonalDescend")));

        group.content().add(new CompactRow("仅挖暴露矿石",
            () -> "只挖掘能从指定距离看到的矿石，减少无效挖掘",
            toggle(() -> settings.allowOnlyExposedOres, value -> settings.allowOnlyExposedOres = value, "allowOnlyExposedOres")));

        group.content().add(new CompactRow("失败目标暂时跳过",
            () -> "矿点无法到达时跳过最近目标，避免反复卡住",
            toggle(() -> settings.blacklistClosestOnFailure,
                value -> settings.blacklistClosestOnFailure = value, "blacklistClosestOnFailure")));

        group.content().add(new CompactRow("合法挖掘模式",
            () -> "启用合法挖掘限制（关闭可提启效率但可能被检测）",
            toggle(() -> settings.legitMine, value -> settings.legitMine = value, "legitMine")));

        group.content().add(new CompactRow("合法挖掘检测对角矿石",
            () -> "合法挖掘时检测与已发现矿石对角相邻的矿石",
            toggle(() -> settings.legitMineIncludeDiagonals,
                value -> settings.legitMineIncludeDiagonals = value, "legitMineIncludeDiagonals")));

        // ── 数值类 ──
        group.content().add(new CompactRow("矿点刷新间隔",
            () -> "每隔多少tick重新扫描矿点（值越小越优先挖近矿；过小会导致寻路线乱闪、人物频繁停顿，40tick约2秒最稳定）",
            intBox(1, 100, () -> settings.mineGoalUpdateInterval,
                value -> settings.mineGoalUpdateInterval = value, "mineGoalUpdateInterval")));

        group.content().add(new CompactRow("矿点缓存数量",
            () -> "Baritone一次缓存的最大矿点数量。太少会找不到矿（寻路失败），太多会路闪。64 缓存充足且稳定",
            intBox(1, 256, () -> settings.mineMaxOreLocationsCount,
                value -> settings.mineMaxOreLocationsCount = value, "mineMaxOreLocationsCount")));

        group.content().add(visible(() -> settings.mobAvoidance, new CompactRow("怪物规避半径",
            () -> "计算怪物危险区域的半径",
            intBox(1, 16, () -> settings.mobAvoidanceRadius,
                value -> settings.mobAvoidanceRadius = value, "mobAvoidanceRadius"))));

        group.content().add(new CompactRow("最大坠落高度",
            () -> "允许从多高的地方跳下（超过会绕路）",
            intBox(0, 20, () -> settings.maxFallHeight,
                value -> settings.maxFallHeight = value, "maxFallHeightNoWater")));

        group.content().add(visible(() -> settings.allowOnlyExposedOres, new CompactRow("暴露矿石检测距离",
            () -> "判断矿石是否暴露时使用的检测距离",
            intBox(1, 8, () -> settings.allowOnlyExposedOresDistance,
                value -> settings.allowOnlyExposedOresDistance = value, "allowOnlyExposedOresDistance"))));

        group.content().add(new CompactRow("最低挖掘高度",
            () -> "Baritone 挖矿时不会低于此高度",
            intBox(-64, 320, () -> settings.minYLevelWhileMining,
                value -> settings.minYLevelWhileMining = value, "minYLevelWhileMining")));

        group.content().add(new CompactRow("最高挖掘高度",
            () -> "Baritone 挖矿时不会高于此高度",
            intBox(-64, 320, () -> settings.maxYLevelWhileMining,
                value -> settings.maxYLevelWhileMining = value, "maxYLevelWhileMining")));

        group.content().add(visible(() -> settings.legitMine, new CompactRow("合法挖掘高度",
            () -> "合法挖掘模式进行条带探索时使用的高度",
            intBox(-64, 320, () -> settings.legitMineYLevel,
                value -> settings.legitMineYLevel = value, "legitMineYLevel"))));
    }

    // ── 控件构造 ──

    /** 折叠分组；首组默认展开，其余折叠（旧项目分组渲染口径） */
    private CollapsibleSection group(String title, boolean expanded) {
        CollapsibleSection section = new CollapsibleSection(title, null).expanded(expanded);
        addCore(section);
        return section;
    }

    /**
     * 开关行：改动落盘；{@code baritoneKey} 非空时同步下调 Baritone（旧项目 {@code onChanged} 同键）。
     *
     * <p>不下调的三项：{@code 寻路物流破坏方块}（只由状态机在物流态压 {@code allowBreak}）、
     * {@code 岩浆透视} 与 {@code 岩浆透视范围}（模块自用）。</p>
     */
    private SettingToggle toggle(Supplier<Boolean> getter, Consumer<Boolean> setter, String baritoneKey) {
        return new SettingToggle(getter, value -> {
            setter.accept(value);
            module.persistSettings();
            if (baritoneKey != null) module.getBaritone().updateSetting(baritoneKey, value);
        });
    }

    /** 整数设置框：步进 1、无滑块（旧项目全部 {@code noSlider}），改动落盘并可按旧键下调 Baritone */
    private SettingNumberBox intBox(int min, int max, Supplier<Integer> getter, IntConsumer setter, String baritoneKey) {
        return new SettingNumberBox(min, max, 1, "%.0f",
            () -> (double) getter.get(),
            value -> {
                int next = (int) Math.round(value);
                setter.accept(next);
                module.persistSettings();
                if (baritoneKey != null) module.getBaritone().updateSetting(baritoneKey, next);
            });
    }

    /** 单行文本设置（5 条传送指令与 GUI 按钮关键词） */
    private SettingTextBox textBox(Supplier<String> getter, Consumer<String> setter) {
        return new SettingTextBox(getter, value -> {
            setter.accept(value);
            module.persistSettings();
        }, TEXT_MAX_LENGTH).width(TEXT_BOX_WIDTH);
    }

    // ── 选择器行 ──

    /**
     * 选择器行的统一构造（行样式照星露谷控制台页 {@code StardewPlantingPage:69-85}）：
     * 名称 + 说明 …… [点击选择] [状态文字] [↻]。
     *
     * <p>状态文字用 {@link SettingText}，列宽按当前文本实测宽度给（与星露谷同一做法：固定宽度会在
     * 文案变长时把按钮挤到行中间）；↻ 的图标与动作都与星露谷一致——清空本行已选，空则静默。</p>
     */
    private ListRow selectorRow(String title, String description, Supplier<String> status,
                                Runnable open, Runnable reset) {
        return new ListRow(title)
            .detail(() -> description)
            .action(new Button(SELECT_LABEL, open))
            .action(new SettingText(status,
                () -> MinecraftText.measure(status.get(), STATE_FONT_SIZE, false)).alignLeft())
            .action(new IconButton(GLYPH_RESET, reset));
    }

    /** 条件元素：不满足可见性条件时高度为 0，绘制与命中全部跳过 */
    private static CompactElement visible(BooleanSupplier condition, CompactElement inner) {
        return new CompactElement() {
            @Override
            public float height() {
                return condition.getAsBoolean() ? inner.height() : 0f;
            }

            @Override
            public void update(float dt) {
                inner.update(dt);
            }

            @Override
            public void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY) {
                if (condition.getAsBoolean()) inner.draw(canvas, x, y, width, alpha, mouseX, mouseY);
            }

            @Override
            public boolean onClick(float mx, float my, float x, float y, float width, int button) {
                return condition.getAsBoolean() && inner.onClick(mx, my, x, y, width, button);
            }

            @Override
            public boolean onDrag(float mx, float my, float x, float y, float width) {
                return condition.getAsBoolean() && inner.onDrag(mx, my, x, y, width);
            }
        };
    }

    // ── 界面跳转 ──

    /** 打开使用说明（旧 {@code :1509-1511}）：窗口标题为「自动挖矿 - 使用说明」 */
    private void openHelp() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new HelpPanelScreen(AutoMinerModule.MESSAGE_MODULE,
            HelpPanelScreen.buildHelpContent(HELP_SECTIONS), client.screen));
    }

    /** 打开控制台（整屏分页）；父屏是当前模块页，ESC 回来 */
    private void openConsole() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.setScreen(new MiningConsoleScreen(client.screen, module));
    }

    private void openScreen(Screen screen) {
        Minecraft client = Minecraft.getInstance();
        if (client == null || screen == null) return;
        client.setScreen(screen);
    }

    /** 执行后直接回到游戏（旧项目 {@code mc.setScreen(null)}） */
    private void closeToGame() {
        Minecraft client = Minecraft.getInstance();
        if (client != null) client.setScreen(null);
    }
}
