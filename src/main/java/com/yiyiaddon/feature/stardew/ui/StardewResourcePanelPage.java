package com.yiyiaddon.feature.stardew.ui;

import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.stardew.StardewFarmModule;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.platform.GameProbe;
import com.yiyiaddon.platform.world.WorldContextFormatter;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import com.yiyiaddon.service.resourcepack.ResourcePackCache;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.KeybindBadge;
import com.yiyiaddon.ui.component.ModuleStatusBar;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.screen.HelpPanelScreen;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.SettingToggle;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 星露谷农场模块页：只保留旧项目设置页里的「服务器资源」面板这一张卡。
 *
 * <p><b>逐字搬运自旧项目</b> {@code stardew/selector/StardewResourceSetting.java}：
 * 六个字段标签（服务器名称 / 服务器地址 / 资源状态 / 资源来源 / 资源缓存 / 资源指纹）、
 * 全部动态文案分支与四个按钮（检测 / 提取、打开控制台、查看资源包、查看使用说明）的
 * 文案与 tooltip 一律照旧；模块内其余设置早已全部搬进控制台，不再在本页重复平铺。</p>
 *
 * <p><b>值列构造照旧项目格式</b>：{@code §7标签 §8▶ 值}——标签列与分隔符是旧项目
 * {@code WFixedCell(theme.label("§7"+label)) + WFixedCell(theme.label("§8▶"))} 的原样拼接，
 * 因此本页用 {@link TextLine}（内部经 MinecraftText 解析颜色码）而不是带 § 的
 * {@link CompactRow} 标签，避免壳里的标签不解析颜色码。</p>
 *
 * <p><b>LIVE 刷新等价物</b>：旧类靠常驻 tick 订阅把资源服务真实状态刷到已打开的面板上；
 * 本项目页面每帧 {@code draw} 都重新读取 {@link TextLine} 的取值来源，因此不需要任何订阅，
 * 状态天然实时。</p>
 *
 * <p><b>顶部信息块</b>：与其它模块页同一套壳——状态圆点 / 状态文字 + 快捷键徽章 + 模块开关
 * （旧项目该开关由 Meteor 的 {@code ModuleScreen} 统一画在面板顶部，迁移后由各页注入）。</p>
 */
public final class StardewResourcePanelPage extends CompactModulePage implements ModulePage {

    /** 播报与使用说明窗口使用的模块名（旧项目原文） */
    private static final String MODULE_NAME = "星露谷农场";

    /** 分组标题（旧项目 Meteor 分组名原文） */
    private static final String GROUP_TITLE = "服务器资源";

    private static final float GROUP_HEIGHT = 26f;
    private static final float GROUP_SIZE = 12f;
    private static final float FIELD_HEIGHT = 22f;

    /**
     * 字段字号：显式设定，保证与 {@link MinecraftText#measure} 的测量完全一致。
     * <p>与 {@code TextLine} 默认字号（11f）相同，因此不改变现有观感。</p>
     */
    private static final float FIELD_SIZE = 11f;

    /**
     * 标签列的固定像素宽度：由 {@link #addField} 构建时按最宽标签量出。
     *
     * <p>旧项目用两个固定宽度单元格（{@code WFixedCell(标签) + WFixedCell("§8▶")}）保证值列对齐；
     * 本页是单行文本，因此按最宽标签补空格来复刻同样的对齐效果。以 -1 表示尚未测量。</p>
     */
    private float labelColumnWidth = -1f;

    /** 一个半角空格的像素宽度（首次使用时才测量，避免类加载期触碰字体） */
    private static float spaceWidth = -1f;

    private static float spaceWidth() {
        if (spaceWidth < 0f) spaceWidth = MinecraftText.measure(" ", FIELD_SIZE, false);
        return spaceWidth;
    }

    /** 磁盘缓存探测节流：每 1 秒最多查一次目录，避免每帧都列目录（旧类同口径） */
    private static final long CACHE_PROBE_INTERVAL_MS = 1000L;
    private static long lastCacheProbeMs;
    private static String lastCacheProbeKey;
    private static File lastCacheProbeFile;

    private final Minecraft mc = Minecraft.getInstance();
    private final StardewFarmModule module;

    public StardewResourcePanelPage(StardewFarmModule module) {
        this.module = module;
        build();
    }

    @Override
    public BasePage createPage(ModuleEntry entry) {
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
        // 顶部信息块：状态圆点 + 快捷键徽章 + 模块开关（与其它模块页同一套壳）
        setHeader(new ModuleStatusBar(
            () -> module.isEnabled() ? "运行中" : "未启用",
            module::isEnabled,
            new KeybindBadge(module.keybindId()),
            new SettingToggle(module::isEnabled,
                value -> ModuleManager.setEnabled(module.id(), value))));

        addCore(new TextLine(GROUP_TITLE).height(GROUP_HEIGHT).size(GROUP_SIZE).bold(true));

        // 六行字段：顺序与标签逐字照旧项目 FIELD_LABELS
        addField("服务器名称", () -> "§b" + WorldContextFormatter.serverName());
        addField("服务器地址", () -> {
            String address = WorldContextFormatter.serverAddress();
            return "未连接".equals(address) ? "§8未连接" : "§b" + address;
        });
        addField("资源状态", StardewResourcePanelPage::stateText);
        addField("资源来源", () -> "§f" + ResourceExtractionService.resourceSource().label());
        addField("资源缓存", () -> "§f" + ResourceExtractionService.cacheLabel());
        addField("资源指纹", () -> "§f" + ResourceExtractionService.fingerprintLabel());

        // ① 检测 / 更新资源
        addCore(new CompactRow("", this::detectHint, new Button(this::detectText, () -> {
            if (!allowed() || ResourceExtractionService.isBusy()) return;
            ResourceExtractionService.requestExtract();
        })).centeredControl());

        // ② 打开控制台：整屏页面（概览 / 种植 / 运行 / 后勤 / 点位 / 日志），一屏只显示一类内容
        addCore(new CompactRow("",
            () -> "按用途分页看状态与改参数：概览只看数据，种植 / 运行 / 后勤 / 点位各自一页，还有最近播报",
            new Button("§b打开控制台", this::openConsole)).centeredControl());

        // ③ 查看资源包：只用当前完整 ServerKey 取真实缓存文件，禁止打开其它服务器的包
        addCore(new CompactRow("", this::packHint, new Button(this::packText, this::openCachedPack)).centeredControl());

        // ④ 查看使用说明
        addCore(new CompactRow("", () -> "打开星露谷农场的完整使用说明",
            new Button("§e查看使用说明",
                () -> mc.setScreen(new HelpPanelScreen(MODULE_NAME, module.helpContent(), mc.screen))))
            .centeredControl());
    }

    /**
     * 一行「标签 + {@code ▶} + 值」。
     *
     * <p>值与颜色分支在取值时实时计算（旧类每 tick 刷新 LIVE 面板的等价物）；
     * 值列不截断成省略号：{@link TextLine} 不做省略号截断，超长值由内容区自然裁切。</p>
     *
     * // TODO 待确认：旧项目值列上限 480、超出换行；新壳 TextLine 是单行文本元素，没有换行能力，
     * // 因此超长值（超长地址 / 指纹等）不会换行、会在内容区右边界被裁切。若必须逐字复刻换行行为，
     * // 需要给新壳补一个「可换行只读文本」元素。
     */
    private void addField(String label, Supplier<String> value) {
        // 构建期量出最宽标签，作为整页共用的固定标签列宽（旧 WFixedCell 的等价物）
        labelColumnWidth = Math.max(labelColumnWidth, MinecraftText.measure(label, FIELD_SIZE, false));
        addCore(new TextLine(() -> "§7" + padLabel(label) + " §8▶ " + value.get())
            .height(FIELD_HEIGHT).size(FIELD_SIZE));
    }

    /**
     * 把标签补齐到固定列宽（用半角空格按实测宽度补足）。
     *
     * <p>值列的起点因此只由列宽决定，与标签是 4 字还是 5 字无关——旧项目两个固定宽度单元格
     * 的对齐效果。</p>
     */
    private String padLabel(String label) {
        float space = spaceWidth();
        if (labelColumnWidth <= 0f || space <= 0f) return label;
        float width = MinecraftText.measure(label, FIELD_SIZE, false);
        int spaces = Math.max(0, Math.round((labelColumnWidth - width) / space));
        return spaces == 0 ? label : label + " ".repeat(spaces);
    }

    // ── 状态取值（逐字照旧 StardewResourceSetting.refresh / stateText） ──

    /** 环境闸门：旧 {@code ServerResourceService.resourceActionsAllowed()} 的等价物 */
    private static boolean allowed() {
        return GameProbe.isMultiplayer();
    }

    /** 检测按钮文案：环境 → 执行中 → 已就绪 → 待检测，四类分支逐字照旧 */
    private String detectText() {
        if (!allowed()) return "§8检测 / 提取当前服务器资源包";
        if (ResourceExtractionService.isBusy()) {
            return "§7执行中… §f" + ResourceExtractionService.statusLabel();
        }
        if (ResourceExtractionService.isReady()) return "§a§l重新检测 / 更新资源";
        return "§e§l检测 / 提取当前服务器资源包";
    }

    /** 检测按钮 tooltip：三种分支逐字照旧 */
    private String detectHint() {
        if (allowed()) return "点击后检测当前服务器资源包";
        return WorldContextFormatter.environment() == WorldContextFormatter.Environment.SINGLEPLAYER
            ? "星露谷资源检测仅支持多人服务器" : "仅多人服务器可检测服务器资源包";
    }

    /** 查看资源包按钮文案：有 ZIP 才高亮，逐字照旧 */
    private String packText() {
        return allowed() && probeCachedZip() != null ? "§b§l查看资源包" : "§8查看资源包";
    }

    /** 查看资源包按钮 tooltip：三种分支逐字照旧 */
    private String packHint() {
        if (!allowed()) return "仅多人服务器可查看资源包";
        return probeCachedZip() != null
            ? "在文件管理器中定位当前服务器的资源包 ZIP" : "当前服务器尚无本地资源包缓存，请先检测 / 提取";
    }

    /** 资源状态中文文案（环境优先，其次才是阶段）——逐字照旧 stateText() */
    private static String stateText() {
        return switch (WorldContextFormatter.environment()) {
            case MAIN_MENU -> "§8未进入世界";
            case SINGLEPLAYER -> "§c不支持";
            case MULTIPLAYER -> switch (ResourceExtractionService.phase()) {
                case READY -> "§a已就绪";
                case FAILED -> "§c检测失败 §8(" + safe(ResourceExtractionService.failReason()) + ")";
                case NO_CONTENT -> "§6非星露谷资源";
                case NOT_CHECKED -> "§e未检测";
                default -> "§e" + ResourceExtractionService.phase().label();
            };
        };
    }

    // ── 四个按钮的行为 ──

    /**
     * 打开控制台。
     *
     * <p>控制台窗口（概览 / 种植 / 运行 / 后勤 / 点位 / 日志）由控制台批次的工程师实现，
     * 约定入口为 {@code new StardewConsoleScreen(minecraft.screen, module)}。</p>
     */
    private void openConsole() {
        mc.setScreen(new StardewConsoleScreen(mc.screen, module));
    }

    /**
     * 「查看资源包」：打开当前服务器缓存 ZIP 所在目录并选中它。
     *
     * <p>缓存来源严格是「当前完整 ServerKey」——{@link ResourcePackCache#cachedZip(String)}
     * 自带端口隔离铁律，端口不同绝不共用。只有临时 ResourceManager 资源（未落盘）时，
     * 明确播报「尚未建立本地资源包缓存」，引导玩家先检测 / 提取，绝不假装已缓存。</p>
     */
    private void openCachedPack() {
        if (!allowed()) {
            CommandMessageFormatter.of(MODULE_NAME, "当前环境无法查看资源包")
                .status(CommandMessageFormatter.Level.WARNING,
                    WorldContextFormatter.environment() == WorldContextFormatter.Environment.SINGLEPLAYER
                        ? "星露谷资源检测仅支持多人服务器" : "请先进入多人服务器")
                .send();
            return;
        }

        File zip = probeCachedZip();
        if (zip == null) {
            CommandMessageFormatter.of(MODULE_NAME, "当前服务器尚未建立本地资源包缓存")
                .status(CommandMessageFormatter.Level.WARNING, "请先检测 / 提取当前服务器资源")
                .send();
            return;
        }
        reveal(zip, message -> mc.execute(() ->
            CommandMessageFormatter.of(MODULE_NAME, "打开资源包失败")
                .status(CommandMessageFormatter.Level.FAILURE, message).send()));
    }

    /**
     * 取当前服务器的本地 ZIP 缓存；节流到 1 秒一次（与旧 {@code probeCachedZip} 同口径）。
     */
    private static File probeCachedZip() {
        String key = ResourceExtractionService.serverKey();
        if (key == null || key.isBlank()) return null;

        long now = System.currentTimeMillis();
        if (key.equals(lastCacheProbeKey) && now - lastCacheProbeMs < CACHE_PROBE_INTERVAL_MS) {
            return lastCacheProbeFile;
        }
        lastCacheProbeKey = key;
        lastCacheProbeMs = now;
        lastCacheProbeFile = ResourcePackCache.cachedZip(key);
        return lastCacheProbeFile;
    }

    /**
     * 在系统文件管理器中定位目标文件。
     *
     * <p>与旧项目 {@code utils/SystemFileOpener.reveal} 同语义：定位文件必须走系统命令
     * （{@code Desktop.open} 会用解压工具把 ZIP 打开，不是「定位」）；失败原因回调给调用方，
     * 由调用方切回渲染线程播报。</p>
     */
    private static void reveal(File target, Consumer<String> onError) {
        if (target == null) {
            if (onError != null) onError.accept("路径为空");
            return;
        }
        Thread opener = new Thread(() -> {
            try {
                String os = System.getProperty("os.name", "").toLowerCase(Locale.ROOT);
                if (os.contains("win")) {
                    new ProcessBuilder("explorer.exe", "/select," + target.getAbsolutePath()).start();
                } else if (os.contains("mac")) {
                    new ProcessBuilder("open", "-R", target.getAbsolutePath()).start();
                } else {
                    File dir = target.getParentFile() == null ? target : target.getParentFile();
                    new ProcessBuilder("xdg-open", dir.getAbsolutePath()).start();
                }
            } catch (Exception e) {
                if (onError != null) onError.accept(e.getMessage());
            }
        }, "yiyiaddon-OpenFolder");
        opener.setDaemon(true);
        opener.start();
    }

    /** 空值兜底：失败原因缺失时也要显示可读文本，绝不出现 {@code null} */
    private static String safe(String value) {
        return value == null || value.isBlank() ? "未知原因" : value;
    }
}
