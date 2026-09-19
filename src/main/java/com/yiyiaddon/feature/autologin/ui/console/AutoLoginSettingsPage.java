package com.yiyiaddon.feature.autologin.ui.console;

import com.yiyiaddon.feature.autologin.AutoLoginModule;
import com.yiyiaddon.feature.autologin.config.AutoLoginSettings;
import com.yiyiaddon.feature.autologin.config.AutoLoginTexts;
import com.yiyiaddon.feature.autologin.model.LeyuanWelcomeEntryMode;
import com.yiyiaddon.feature.autologin.model.ServerEntryMode;
import com.yiyiaddon.feature.autologin.ui.AutoLoginConsoleScreen;
import com.yiyiaddon.feature.autologin.ui.AutoLoginSelectors;
import com.yiyiaddon.ui.component.CompactStack;
import com.yiyiaddon.ui.console.ConsoleWidgets;
import com.yiyiaddon.ui.console.ConsoleWidgets.ConsoleRow;
import com.yiyiaddon.ui.console.ConsoleWidgets.Ctl;
import com.yiyiaddon.ui.console.ConsoleWidgets.FoldSection;
import com.yiyiaddon.ui.console.ConsoleWidgets.Note;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingNumberBox;
import com.yiyiaddon.ui.widget.SettingPasswordBox;
import com.yiyiaddon.ui.widget.SettingSegmented;
import com.yiyiaddon.ui.widget.SettingTextBox;
import com.yiyiaddon.ui.widget.SettingToggle;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.DoubleConsumer;
import java.util.function.Supplier;

import static com.yiyiaddon.ui.console.ConsoleWidgets.COMMENT_CYCLE;

/**
 * 控制台设置页：旧项目原 5 个设置组各一页，每页一个折叠块
 * （登录认证 / 自动重连 / 自动执行指令 / 进服路线 / 自用配置 / 账号与调试）。
 *
 * <p><b>设置逐字</b>：名称 / 描述 / 默认值 / 取值域全部来自旧
 * {@code autologin/config/AutoLoginSettings.java} 的设置声明（集中在 {@link AutoLoginTexts} 与
 * {@link AutoLoginSettings}，同一句话只有一处）；分组名沿用旧组名与旧源码分节标题，
 * 不新造分类（第 183 条）。数字一律 {@link SettingNumberBox}（第 123 条禁滑块，整数步进 1）；
 * 枚举走 {@link SettingSegmented}；布尔走 {@link SettingToggle}；密码走 {@link SettingPasswordBox}
 * （打码显示、聚焦清空重输、失焦且有过改动才写回）；旧设置的描述原文留在行悬停提示里（第 213 条）。</p>
 *
 * <p><b>行级显隐照旧条件</b>：整页可重建，条件不满足的行压根不加入堆叠（等价旧设置的
 * {@code visible(...)}）；改开关 / 改进入方式会触发重建，行立刻出现或消失。逐条对应旧条件：
 * {@code GUI 自动登录 / 登录密码 / 登录延迟} 仅 {@code 自动登录}；
 * {@code 注册密码 / 注册延迟} 仅 {@code 自动注册}；
 * {@code 世界加载等待 / 认证检测超时} 仅认证三开关任一开启；
 * {@code 重连等待 / 无限重连} 仅 {@code 自动重连}，{@code 最大重连次数} 再要求未开 {@code 无限重连}；
 * {@code 执行指令 / 指令延迟} 仅 {@code 登录后执行指令} 且认证三开关任一开启；
 * {@code 进入方式} 仅 {@code 自动进入目标区域}；
 * 通用子服七项仅 {@code usesStandardMenuRoute()}；
 * {@code 到达稳定等待 / 到达目标后执行指令} 仅 {@code 自动进入目标区域}，
 * 其后两项再要求 {@code 到达目标后执行指令}；
 * 自用配置全部项仅 {@code usesLeyuanMode()}，{@code 挂机区菜单延迟（分钟）} 再要求 {@code 挂机区自动回服}。</p>
 *
 * <p><b>两个物品项</b>（{@code 菜单工具} / {@code 自用菜单工具}）：旧壳是旧框架的
 * {@code ItemSetting}，本项目落盘为物品登记 ID（见 {@link AutoLoginSettings} 类注释），
 * 行右侧「选择」按钮打开 {@link AutoLoginSelectors} 的通用选择器，行内左侧带物品图标。</p>
 *
 * <p><b>八个自由名单</b>（旧 {@code StringListSetting}）：旧框架的列表控件属框架层（第 34 条），
 * 控制台按既有「输入框 + 添加 / 逐项移除」的同一写法自建（与自动附魔「自定义附魔目标」同款），
 * 输入框草稿存在窗口侧（{@link AutoLoginConsoleScreen#draft(String)}），整页重建不丢未提交内容。</p>
 *
 * <p><b>两条开关联动播报</b>：旧设置的 {@code onChanged} 文案逐字保留——
 * 「自动登录 / 自动注册」互斥两条与「检测服务器子服」两条（第 216 条），经
 * {@link AutoLoginModule} 的播报口输出（正文与颜色码一字未改）。</p>
 *
 * <p>改动立即 {@link com.yiyiaddon.core.module.ModuleManager#saveSettings}
 * （第 172-175 条：改设置必须落盘、重启仍生效，读写键名对称）。</p>
 */
public final class AutoLoginSettingsPage {

    // ── 折叠块的记忆键（= 旧项目设置组名 / 页签名，同源） ──

    private static final String SECTION_KEY_AUTH = "group:" + AutoLoginTexts.GROUP_AUTH;
    private static final String SECTION_KEY_RECONNECT = "group:" + AutoLoginTexts.GROUP_RECONNECT;
    private static final String SECTION_KEY_COMMANDS = "group:" + AutoLoginTexts.GROUP_COMMANDS;
    private static final String SECTION_KEY_ROUTE = "group:" + AutoLoginTexts.GROUP_ROUTE;
    private static final String SECTION_KEY_LEYUAN = "group:" + AutoLoginTexts.GROUP_LEYUAN;
    private static final String SECTION_KEY_ACCOUNT = "group:" + AutoLoginTexts.GROUP_ACCOUNT;

    /** 自由文本 / 名单输入框的宽度与长度上限（同本项目其它自由文本设置） */
    private static final float TEXT_BOX_WIDTH = 240f;
    private static final int TEXT_MAX_LENGTH = 64;

    /** 行内「移除这一项」（Material Symbols：remove，与选择器右栏同一字形） */
    private static final String GLYPH_REMOVE = "\uE15B";

    /** 枚举候选：顺序即枚举序（显示文案 = 枚举 {@code title()} = 落盘名之外的另一份，互不影响） */
    private static final List<String> ENTRY_MODE_LABELS = List.of(ServerEntryMode.labels());
    private static final List<String> WELCOME_ENTRY_LABELS = List.of(LeyuanWelcomeEntryMode.labels());

    /** 出厂设置：只作「行内恢复默认」的取值来源，与设置类字段初始化里的默认值同源 */
    private static final AutoLoginSettings DEFAULTS = new AutoLoginSettings();

    private final AutoLoginConsoleScreen host;
    private final AutoLoginModule module;

    public AutoLoginSettingsPage(AutoLoginConsoleScreen host, AutoLoginModule module) {
        this.host = host;
        this.module = module;
    }

    // ── 组① 登录认证（旧 grpAuth 的 11 项，顺序 = 旧声明顺序） ──

    public void buildAuth(CompactStack stack) {
        AutoLoginSettings settings = module.settings();
        FoldSection section = new FoldSection("§f" + AutoLoginTexts.GROUP_AUTH,
            SECTION_KEY_AUTH, host.collapsedSections());

        // 旧 onChanged：开启自动登录时关闭自动注册并播报（文案逐字）
        section.content().add(toggleRow(AutoLoginTexts.NAME_AUTO_LOGIN, AutoLoginTexts.DESC_AUTO_LOGIN,
            () -> settings.autoLogin, value -> {
                settings.autoLogin = value;
                if (value && settings.autoRegister) {
                    settings.autoRegister = false;
                    module.announceAutoLoginExclusive();
                }
            }, () -> DEFAULTS.autoLogin));

        if (settings.autoLogin) {
            section.content().add(toggleRow(AutoLoginTexts.NAME_GUI_LOGIN, AutoLoginTexts.DESC_GUI_LOGIN,
                () -> settings.guiLogin, value -> settings.guiLogin = value, () -> DEFAULTS.guiLogin));
        }

        section.content().add(toggleRow(AutoLoginTexts.NAME_NO_LOGIN_DETECTION,
            AutoLoginTexts.DESC_NO_LOGIN_DETECTION,
            () -> settings.noLoginDetection, value -> settings.noLoginDetection = value,
            () -> DEFAULTS.noLoginDetection));

        // 旧 onChanged：开 / 关各播报一句（文案逐字）
        section.content().add(toggleRow(AutoLoginTexts.NAME_DETECT_SUBSERVER,
            AutoLoginTexts.DESC_DETECT_SUBSERVER,
            () -> settings.detectSubserver, value -> {
                settings.detectSubserver = value;
                module.announceSubserverDetection(value);
            }, () -> DEFAULTS.detectSubserver));

        if (settings.autoLogin) {
            section.content().add(passwordRow(AutoLoginTexts.NAME_LOGIN_PASSWORD,
                AutoLoginTexts.DESC_LOGIN_PASSWORD,
                () -> settings.loginPassword, value -> settings.loginPassword = value));

            section.content().add(numberRow(AutoLoginTexts.NAME_LOGIN_DELAY, AutoLoginTexts.DESC_LOGIN_DELAY,
                AutoLoginSettings.DELAY_MIN, AutoLoginSettings.DELAY_MAX,
                () -> (double) settings.loginDelay,
                value -> settings.loginDelay = (int) Math.round(value),
                () -> (double) DEFAULTS.loginDelay));
        }

        // 旧 onChanged：开启自动注册时关闭自动登录并播报（文案逐字）
        section.content().add(toggleRow(AutoLoginTexts.NAME_AUTO_REGISTER, AutoLoginTexts.DESC_AUTO_REGISTER,
            () -> settings.autoRegister, value -> {
                settings.autoRegister = value;
                if (value && settings.autoLogin) {
                    settings.autoLogin = false;
                    module.announceAutoRegisterExclusive();
                }
            }, () -> DEFAULTS.autoRegister));

        if (settings.autoRegister) {
            section.content().add(passwordRow(AutoLoginTexts.NAME_REGISTER_PASSWORD,
                AutoLoginTexts.DESC_REGISTER_PASSWORD,
                () -> settings.registerPassword, value -> settings.registerPassword = value));

            section.content().add(numberRow(AutoLoginTexts.NAME_REGISTER_DELAY, AutoLoginTexts.DESC_REGISTER_DELAY,
                AutoLoginSettings.DELAY_MIN, AutoLoginSettings.DELAY_MAX,
                () -> (double) settings.registerDelay,
                value -> settings.registerDelay = (int) Math.round(value),
                () -> (double) DEFAULTS.registerDelay));
        }

        if (settings.anyAuthEnabled()) {
            section.content().add(numberRow(AutoLoginTexts.NAME_WORLD_LOAD_WAIT,
                AutoLoginTexts.DESC_WORLD_LOAD_WAIT,
                AutoLoginSettings.DELAY_MIN, AutoLoginSettings.DELAY_MAX,
                () -> (double) settings.worldLoadWait,
                value -> settings.worldLoadWait = (int) Math.round(value),
                () -> (double) DEFAULTS.worldLoadWait));

            section.content().add(numberRow(AutoLoginTexts.NAME_AUTH_DETECT_TIMEOUT,
                AutoLoginTexts.DESC_AUTH_DETECT_TIMEOUT,
                AutoLoginSettings.AUTH_DETECT_TIMEOUT_MIN, AutoLoginSettings.DELAY_MAX,
                () -> (double) settings.authDetectTimeout,
                value -> settings.authDetectTimeout = (int) Math.round(value),
                () -> (double) DEFAULTS.authDetectTimeout));
        }

        stack.add(section);
    }

    // ── 组② 自动重连（旧 grpReconnect 的 4 项，顺序 = 旧声明顺序） ──

    public void buildReconnect(CompactStack stack) {
        AutoLoginSettings settings = module.settings();
        FoldSection section = new FoldSection("§f" + AutoLoginTexts.GROUP_RECONNECT,
            SECTION_KEY_RECONNECT, host.collapsedSections());

        section.content().add(toggleRow(AutoLoginTexts.NAME_AUTO_RECONNECT, AutoLoginTexts.DESC_AUTO_RECONNECT,
            () -> settings.autoReconnect, value -> settings.autoReconnect = value,
            () -> DEFAULTS.autoReconnect));

        if (settings.autoReconnect) {
            section.content().add(numberRow(AutoLoginTexts.NAME_RECONNECT_DELAY,
                AutoLoginTexts.DESC_RECONNECT_DELAY,
                AutoLoginSettings.RECONNECT_DELAY_MIN, AutoLoginSettings.DELAY_MAX,
                () -> (double) settings.reconnectDelay,
                value -> settings.reconnectDelay = (int) Math.round(value),
                () -> (double) DEFAULTS.reconnectDelay));

            section.content().add(toggleRow(AutoLoginTexts.NAME_ALWAYS_RECONNECT,
                AutoLoginTexts.DESC_ALWAYS_RECONNECT,
                () -> settings.alwaysReconnect, value -> settings.alwaysReconnect = value,
                () -> DEFAULTS.alwaysReconnect));

            if (!settings.alwaysReconnect) {
                section.content().add(numberRow(AutoLoginTexts.NAME_MAX_RECONNECT_ATTEMPTS,
                    AutoLoginTexts.DESC_MAX_RECONNECT_ATTEMPTS,
                    AutoLoginSettings.MAX_RECONNECT_MIN, AutoLoginSettings.DELAY_MAX,
                    () -> (double) settings.maxReconnectAttempts,
                    value -> settings.maxReconnectAttempts = (int) Math.round(value),
                    () -> (double) DEFAULTS.maxReconnectAttempts));
            }
        }

        stack.add(section);
    }

    // ── 组③ 自动执行指令（旧 grpCommands 的 3 项，顺序 = 旧声明顺序） ──

    public void buildCommands(CompactStack stack) {
        AutoLoginSettings settings = module.settings();
        FoldSection section = new FoldSection("§f" + AutoLoginTexts.GROUP_COMMANDS,
            SECTION_KEY_COMMANDS, host.collapsedSections());

        section.content().add(toggleRow(AutoLoginTexts.NAME_AUTO_COMMAND, AutoLoginTexts.DESC_AUTO_COMMAND,
            () -> settings.autoCommand, value -> settings.autoCommand = value,
            () -> DEFAULTS.autoCommand));

        // 旧条件：开启了「登录后执行指令」，且认证三开关任一开启（否则不会执行，不显示）
        if (settings.autoCommand && settings.anyAuthEnabled()) {
            section.content().add(textRow(AutoLoginTexts.NAME_AUTO_COMMAND_TEXT,
                AutoLoginTexts.DESC_AUTO_COMMAND_TEXT,
                () -> settings.autoCommandText, value -> settings.autoCommandText = value));

            section.content().add(numberRow(AutoLoginTexts.NAME_COMMAND_DELAY,
                AutoLoginTexts.DESC_COMMAND_DELAY,
                AutoLoginSettings.DELAY_MIN, AutoLoginSettings.DELAY_MAX,
                () -> (double) settings.commandDelay,
                value -> settings.commandDelay = (int) Math.round(value),
                () -> (double) DEFAULTS.commandDelay));
        }

        stack.add(section);
    }

    // ── 组④ 进服路线 · 通用子服（旧 grpRoute 前 13 项，顺序 = 旧声明顺序） ──

    public void buildRoute(CompactStack stack) {
        AutoLoginSettings settings = module.settings();
        FoldSection section = new FoldSection("§f" + AutoLoginTexts.GROUP_ROUTE,
            SECTION_KEY_ROUTE, host.collapsedSections());

        section.content().add(toggleRow(AutoLoginTexts.NAME_AUTO_ENTER_SUBSERVER,
            AutoLoginTexts.DESC_AUTO_ENTER_SUBSERVER,
            () -> settings.autoEnterSubserver, value -> settings.autoEnterSubserver = value,
            () -> DEFAULTS.autoEnterSubserver));

        if (settings.autoEnterSubserver) {
            section.content().add(enumRow(AutoLoginTexts.NAME_SERVER_ENTRY_MODE,
                AutoLoginTexts.DESC_SERVER_ENTRY_MODE, ENTRY_MODE_LABELS,
                () -> settings.serverEntryMode.ordinal(),
                index -> settings.serverEntryMode = ServerEntryMode.values()[index],
                () -> DEFAULTS.serverEntryMode.ordinal()));
        }

        if (settings.usesStandardMenuRoute()) {
            section.content().add(itemRow(AutoLoginTexts.NAME_MENU_TOOL, AutoLoginTexts.DESC_MENU_TOOL,
                () -> settings.menuToolId,
                () -> AutoLoginSelectors.openMenuToolSelector(host, settings),
                () -> settings.menuToolId = DEFAULTS.menuToolId));

            addListRows(section.content(), AutoLoginTexts.NAME_MENU_ITEM_KEYWORDS,
                AutoLoginTexts.DESC_MENU_ITEM_KEYWORDS, settings.menuItemKeywords);

            addListRows(section.content(), AutoLoginTexts.NAME_MENU_CLICK_STEPS,
                AutoLoginTexts.DESC_MENU_CLICK_STEPS, settings.menuClickSteps);

            section.content().add(numberRow(AutoLoginTexts.NAME_MENU_ACTION_DELAY,
                AutoLoginTexts.DESC_MENU_ACTION_DELAY,
                AutoLoginSettings.MENU_ACTION_DELAY_MIN, AutoLoginSettings.DELAY_MAX,
                () -> (double) settings.menuActionDelay,
                value -> settings.menuActionDelay = (int) Math.round(value),
                () -> (double) DEFAULTS.menuActionDelay));

            section.content().add(numberRow(AutoLoginTexts.NAME_MENU_TIMEOUT,
                AutoLoginTexts.DESC_MENU_TIMEOUT,
                AutoLoginSettings.MENU_TIMEOUT_MIN, AutoLoginSettings.DELAY_MAX,
                () -> (double) settings.menuTimeout,
                value -> settings.menuTimeout = (int) Math.round(value),
                () -> (double) DEFAULTS.menuTimeout));

            addListRows(section.content(), AutoLoginTexts.NAME_TARGET_AREA_KEYWORDS,
                AutoLoginTexts.DESC_TARGET_AREA_KEYWORDS, settings.targetAreaKeywords);

            section.content().add(toggleRow(AutoLoginTexts.NAME_REQUIRE_TARGET_KEYWORD,
                AutoLoginTexts.DESC_REQUIRE_TARGET_KEYWORD,
                () -> settings.requireTargetKeyword, value -> settings.requireTargetKeyword = value,
                () -> DEFAULTS.requireTargetKeyword));
        }

        if (settings.autoEnterSubserver) {
            section.content().add(numberRow(AutoLoginTexts.NAME_TARGET_STABLE_DELAY,
                AutoLoginTexts.DESC_TARGET_STABLE_DELAY,
                AutoLoginSettings.DELAY_MIN, AutoLoginSettings.DELAY_MAX,
                () -> (double) settings.targetStableDelay,
                value -> settings.targetStableDelay = (int) Math.round(value),
                () -> (double) DEFAULTS.targetStableDelay));

            section.content().add(toggleRow(AutoLoginTexts.NAME_SUBSERVER_COMMAND,
                AutoLoginTexts.DESC_SUBSERVER_COMMAND,
                () -> settings.subserverCommand, value -> settings.subserverCommand = value,
                () -> DEFAULTS.subserverCommand));

            if (settings.subserverCommand) {
                section.content().add(textRow(AutoLoginTexts.NAME_SUBSERVER_COMMAND_TEXT,
                    AutoLoginTexts.DESC_SUBSERVER_COMMAND_TEXT,
                    () -> settings.subserverCommandText, value -> settings.subserverCommandText = value));

                section.content().add(numberRow(AutoLoginTexts.NAME_SUBSERVER_COMMAND_DELAY,
                    AutoLoginTexts.DESC_SUBSERVER_COMMAND_DELAY,
                    AutoLoginSettings.DELAY_MIN, AutoLoginSettings.DELAY_MAX,
                    () -> (double) settings.subserverCommandDelay,
                    value -> settings.subserverCommandDelay = (int) Math.round(value),
                    () -> (double) DEFAULTS.subserverCommandDelay));
            }
        }

        stack.add(section);
    }

    // ── 组⑤ 进服路线 · 自用配置（旧 grpRoute 后 22 项，顺序 = 旧声明顺序） ──

    public void buildLeyuan(CompactStack stack) {
        AutoLoginSettings settings = module.settings();
        FoldSection section = new FoldSection("§f" + AutoLoginTexts.GROUP_LEYUAN,
            SECTION_KEY_LEYUAN, host.collapsedSections());

        // 旧条件：进入方式 = 自用配置（且已开「自动进入目标区域」）；整组 22 项同一条件
        if (!settings.usesLeyuanMode()) {
            section.content().add(new Note(host,
                "  §8当前「进入方式」不是「自用配置」，本组设置暂不适用（旧项目同样整组隐藏）"));
            stack.add(section);
            return;
        }

        section.content().add(toggleRow(AutoLoginTexts.NAME_LEYUAN_ENABLED,
            AutoLoginTexts.DESC_LEYUAN_ENABLED,
            () -> settings.leyuanEnabled, value -> settings.leyuanEnabled = value,
            () -> DEFAULTS.leyuanEnabled));

        section.content().add(itemRow(AutoLoginTexts.NAME_LEYUAN_MENU_TOOL,
            AutoLoginTexts.DESC_LEYUAN_MENU_TOOL,
            () -> settings.leyuanMenuToolId,
            () -> AutoLoginSelectors.openLeyuanMenuToolSelector(host, settings),
            () -> settings.leyuanMenuToolId = DEFAULTS.leyuanMenuToolId));

        addListRows(section.content(), AutoLoginTexts.NAME_LEYUAN_MENU_ITEM_KEYWORDS,
            AutoLoginTexts.DESC_LEYUAN_MENU_ITEM_KEYWORDS, settings.leyuanMenuItemKeywords);

        section.content().add(textRow(AutoLoginTexts.NAME_LEYUAN_LOGIN_SURVIVAL_KEYWORD,
            AutoLoginTexts.DESC_LEYUAN_LOGIN_SURVIVAL_KEYWORD,
            () -> settings.leyuanLoginSurvivalKeyword,
            value -> settings.leyuanLoginSurvivalKeyword = value));

        section.content().add(enumRow(AutoLoginTexts.NAME_LEYUAN_WELCOME_ENTRY_MODE,
            AutoLoginTexts.DESC_LEYUAN_WELCOME_ENTRY_MODE, WELCOME_ENTRY_LABELS,
            () -> settings.leyuanWelcomeEntryMode.ordinal(),
            index -> settings.leyuanWelcomeEntryMode = LeyuanWelcomeEntryMode.values()[index],
            () -> DEFAULTS.leyuanWelcomeEntryMode.ordinal()));

        section.content().add(textRow(AutoLoginTexts.NAME_LEYUAN_WORLD_TRANSFER_KEYWORD,
            AutoLoginTexts.DESC_LEYUAN_WORLD_TRANSFER_KEYWORD,
            () -> settings.leyuanWorldTransferKeyword,
            value -> settings.leyuanWorldTransferKeyword = value));

        section.content().add(textRow(AutoLoginTexts.NAME_LEYUAN_SURVIVAL_FIRST_KEYWORD,
            AutoLoginTexts.DESC_LEYUAN_SURVIVAL_FIRST_KEYWORD,
            () -> settings.leyuanSurvivalFirstKeyword,
            value -> settings.leyuanSurvivalFirstKeyword = value));

        section.content().add(textRow(AutoLoginTexts.NAME_LEYUAN_SURVIVAL_SECOND_KEYWORD,
            AutoLoginTexts.DESC_LEYUAN_SURVIVAL_SECOND_KEYWORD,
            () -> settings.leyuanSurvivalSecondKeyword,
            value -> settings.leyuanSurvivalSecondKeyword = value));

        section.content().add(textRow(AutoLoginTexts.NAME_LEYUAN_TARGET_SERVER_KEYWORD,
            AutoLoginTexts.DESC_LEYUAN_TARGET_SERVER_KEYWORD,
            () -> settings.leyuanTargetServerKeyword,
            value -> settings.leyuanTargetServerKeyword = value));

        addListRows(section.content(), AutoLoginTexts.NAME_LEYUAN_MAIN_CITY_KEYWORDS,
            AutoLoginTexts.DESC_LEYUAN_MAIN_CITY_KEYWORDS, settings.leyuanMainCityKeywords);

        addListRows(section.content(), AutoLoginTexts.NAME_LEYUAN_AFK_AREA_KEYWORDS,
            AutoLoginTexts.DESC_LEYUAN_AFK_AREA_KEYWORDS, settings.leyuanAfkAreaKeywords);

        section.content().add(toggleRow(AutoLoginTexts.NAME_LEYUAN_AFK_RECOVERY_ENABLED,
            AutoLoginTexts.DESC_LEYUAN_AFK_RECOVERY_ENABLED,
            () -> settings.leyuanAfkRecoveryEnabled, value -> settings.leyuanAfkRecoveryEnabled = value,
            () -> DEFAULTS.leyuanAfkRecoveryEnabled));

        if (settings.leyuanAfkRecoveryEnabled) {
            section.content().add(numberRow(AutoLoginTexts.NAME_LEYUAN_AFK_MENU_DELAY_MINUTES,
                AutoLoginTexts.DESC_LEYUAN_AFK_MENU_DELAY_MINUTES,
                AutoLoginSettings.LEYUAN_AFK_MENU_DELAY_MIN, AutoLoginSettings.LEYUAN_AFK_MENU_DELAY_MAX,
                () -> (double) settings.leyuanAfkMenuDelayMinutes,
                value -> settings.leyuanAfkMenuDelayMinutes = (int) Math.round(value),
                () -> (double) DEFAULTS.leyuanAfkMenuDelayMinutes));
        }

        addListRows(section.content(), AutoLoginTexts.NAME_LEYUAN_RETURN_MAIN_CITY_KEYWORDS,
            AutoLoginTexts.DESC_LEYUAN_RETURN_MAIN_CITY_KEYWORDS, settings.leyuanReturnMainCityKeywords);

        addListRows(section.content(), AutoLoginTexts.NAME_LEYUAN_TARGET_AREA_KEYWORDS,
            AutoLoginTexts.DESC_LEYUAN_TARGET_AREA_KEYWORDS, settings.leyuanTargetAreaKeywords);

        section.content().add(numberRow(AutoLoginTexts.NAME_LEYUAN_STEP_DELAY,
            AutoLoginTexts.DESC_LEYUAN_STEP_DELAY,
            AutoLoginSettings.LEYUAN_STEP_DELAY_MIN, AutoLoginSettings.DELAY_MAX,
            () -> (double) settings.leyuanStepDelay,
            value -> settings.leyuanStepDelay = (int) Math.round(value),
            () -> (double) DEFAULTS.leyuanStepDelay));

        section.content().add(numberRow(AutoLoginTexts.NAME_LEYUAN_STEP_TIMEOUT,
            AutoLoginTexts.DESC_LEYUAN_STEP_TIMEOUT,
            AutoLoginSettings.LEYUAN_STEP_TIMEOUT_MIN, AutoLoginSettings.DELAY_MAX,
            () -> (double) settings.leyuanStepTimeout,
            value -> settings.leyuanStepTimeout = (int) Math.round(value),
            () -> (double) DEFAULTS.leyuanStepTimeout));

        section.content().add(toggleRow(AutoLoginTexts.NAME_LEYUAN_CITY_MENU_FALLBACK,
            AutoLoginTexts.DESC_LEYUAN_CITY_MENU_FALLBACK,
            () -> settings.leyuanCityMenuFallback, value -> settings.leyuanCityMenuFallback = value,
            () -> DEFAULTS.leyuanCityMenuFallback));

        section.content().add(numberRow(AutoLoginTexts.NAME_LEYUAN_CITY_MENU_FALLBACK_DELAY,
            AutoLoginTexts.DESC_LEYUAN_CITY_MENU_FALLBACK_DELAY,
            AutoLoginSettings.LEYUAN_CITY_FALLBACK_DELAY_MIN, AutoLoginSettings.DELAY_MAX,
            () -> (double) settings.leyuanCityMenuFallbackDelay,
            value -> settings.leyuanCityMenuFallbackDelay = (int) Math.round(value),
            () -> (double) DEFAULTS.leyuanCityMenuFallbackDelay));

        section.content().add(numberRow(AutoLoginTexts.NAME_LEYUAN_RECOVERY_WAIT_MINUTES,
            AutoLoginTexts.DESC_LEYUAN_RECOVERY_WAIT_MINUTES,
            AutoLoginSettings.LEYUAN_RECOVERY_WAIT_MIN, AutoLoginSettings.LEYUAN_RECOVERY_WAIT_MAX,
            () -> (double) settings.leyuanRecoveryWaitMinutes,
            value -> settings.leyuanRecoveryWaitMinutes = (int) Math.round(value),
            () -> (double) DEFAULTS.leyuanRecoveryWaitMinutes));

        section.content().add(numberRow(AutoLoginTexts.NAME_LEYUAN_RECOVERY_RETRY_SECONDS,
            AutoLoginTexts.DESC_LEYUAN_RECOVERY_RETRY_SECONDS,
            AutoLoginSettings.LEYUAN_RECOVERY_RETRY_MIN, AutoLoginSettings.LEYUAN_RECOVERY_RETRY_MAX,
            () -> (double) settings.leyuanRecoveryRetrySeconds,
            value -> settings.leyuanRecoveryRetrySeconds = (int) Math.round(value),
            () -> (double) DEFAULTS.leyuanRecoveryRetrySeconds));

        section.content().add(numberRow(AutoLoginTexts.NAME_LEYUAN_RECOVERY_MAX_RETRIES,
            AutoLoginTexts.DESC_LEYUAN_RECOVERY_MAX_RETRIES,
            AutoLoginSettings.LEYUAN_RECOVERY_RETRIES_MIN, AutoLoginSettings.LEYUAN_RECOVERY_RETRIES_MAX,
            () -> (double) settings.leyuanRecoveryMaxRetries,
            value -> settings.leyuanRecoveryMaxRetries = (int) Math.round(value),
            () -> (double) DEFAULTS.leyuanRecoveryMaxRetries));

        stack.add(section);
    }

    // ── 组⑥ 账号与调试（旧 grpAccount 的两项落盘设置，顺序 = 旧声明顺序） ──

    public void buildAccount(CompactStack stack) {
        AutoLoginSettings settings = module.settings();
        FoldSection section = new FoldSection("§f" + AutoLoginTexts.GROUP_ACCOUNT,
            SECTION_KEY_ACCOUNT, host.collapsedSections());

        section.content().add(toggleRow(AutoLoginTexts.NAME_AUTO_CHECK_ACCOUNT,
            AutoLoginTexts.DESC_AUTO_CHECK_ACCOUNT,
            () -> settings.autoCheckAccount, value -> settings.autoCheckAccount = value,
            () -> DEFAULTS.autoCheckAccount));

        // 旧设置的「立即检测账号」伪按钮按模块口径放在概览页（面板动作与「立即重连 / 停止重连」同处）
        section.content().add(new Note(host,
            "  §8「立即检测账号」在「" + AutoLoginTexts.TAB_OVERVIEW + "」页"));

        section.content().add(toggleRow(AutoLoginTexts.NAME_DEBUG_MODE, AutoLoginTexts.DESC_DEBUG_MODE,
            () -> settings.debugMode, value -> settings.debugMode = value,
            () -> DEFAULTS.debugMode));

        stack.add(section);
    }

    // ── 行构件组装 ──

    /** 开关行：改动即写盘；写盘后整页重建，跟随本开关显隐的行立刻出现或消失 */
    private ConsoleRow toggleRow(String label, String desc,
                                 Supplier<Boolean> getter, Consumer<Boolean> setter,
                                 Supplier<Boolean> defaultValue) {
        SettingToggle toggle = new SettingToggle(getter, value -> {
            setter.accept(value);
            persist();
            host.reload();
        });
        return new ConsoleRow(host, () -> label, desc, null, List.of(new Ctl(toggle, desc),
            ConsoleWidgets.resetCtl(() -> {
                // 走同一个 setter：互斥开关的播报等联动一并照原样执行
                setter.accept(defaultValue.get());
                persist();
                host.reload();
            }, label)));
    }

    /**
     * 枚举行：分段控件（互斥选项一眼可点），行尾带可见提示（第 213 条）。
     *
     * <p>改动后整页重建：进入方式决定通用子服 / 自用配置两组设置哪一组出现。</p>
     */
    private ConsoleRow enumRow(String label, String desc, List<String> options,
                               Supplier<Integer> getter, Consumer<Integer> setter,
                               Supplier<Integer> defaultValue) {
        SettingSegmented control = new SettingSegmented(options, getter, index -> {
            setter.accept(index);
            persist();
            host.reload();
        });
        return new ConsoleRow(host, () -> label, desc, COMMENT_CYCLE, List.of(new Ctl(control, desc),
            ConsoleWidgets.resetCtl(() -> {
                setter.accept(defaultValue.get());
                persist();
                host.reload();
            }, label)));
    }

    /** 整数数值行：步进 1（旧项目全部数字设置都是 noSlider，第 123 条禁滑块），改动即写盘 */
    private ConsoleRow numberRow(String label, String desc, int min, int max,
                                 Supplier<Double> getter, DoubleConsumer setter,
                                 Supplier<Double> defaultValue) {
        SettingNumberBox box = new SettingNumberBox(min, max, 1, "%.0f", getter, value -> {
            setter.accept(value);
            persist();
        });
        return new ConsoleRow(host, () -> label, desc, null, List.of(new Ctl(box, desc),
            ConsoleWidgets.resetCtl(() -> {
                setter.accept(defaultValue.get());
                persist();
                host.reload();
            }, label)));
    }

    /** 文本行：输入框直接读写设置字段（失焦提交 / 回车提交由控件负责），提交即写盘 */
    private ConsoleRow textRow(String label, String desc,
                               Supplier<String> getter, Consumer<String> setter) {
        SettingTextBox box = new SettingTextBox(getter, value -> {
            setter.accept(value);
            persist();
        }, TEXT_MAX_LENGTH).width(TEXT_BOX_WIDTH);
        return new ConsoleRow(host, () -> label, desc, null, List.of(new Ctl(box, desc)));
    }

    /**
     * 密码行：打码显示，聚焦时清空缓冲重新输入，失焦且有过改动才回调保存。
     *
     * <p>真实密码不回填控件（{@link SettingPasswordBox} 只管编辑期缓冲），因此显示链路里
     * 不出现明文；保存后立即落盘（第 172-175 条）。</p>
     */
    private ConsoleRow passwordRow(String label, String desc,
                                   Supplier<String> maskedGetter, Consumer<String> save) {
        SettingPasswordBox box = new SettingPasswordBox(maskedGetter, value -> {
            save.accept(value);
            persist();
        }, TEXT_MAX_LENGTH);
        box.width(TEXT_BOX_WIDTH);
        return new ConsoleRow(host, () -> label, desc, null, List.of(new Ctl(box, desc)));
    }

    /** 物品行：名称 + 当前物品 …… [选择] [↺]；行内左侧带物品图标（未选择时不占位） */
    private ConsoleRow itemRow(String label, String desc, Supplier<String> current, Runnable open,
                               Runnable restoreDefaults) {
        return new ConsoleRow(host,
            () -> label + " §8· §7" + AutoLoginSelectors.statusText(current.get()), desc, null,
            List.of(new Ctl(new Button(AutoLoginTexts.SELECT, open), AutoLoginTexts.SELECT_HINT),
                ConsoleWidgets.resetCtl(() -> {
                    restoreDefaults.run();
                    persist();
                    host.reload();
                }, label)))
            .icon(() -> AutoLoginSelectors.iconOf(current.get()));
    }

    /**
     * 自由名单：一行「输入框 + 添加」，下面逐项一行 + 移除。
     *
     * <p>旧 {@code StringListSetting} 的列表控件属框架层（第 34 条），这里按既有
     * 「输入框 + 添加 / 逐项移除」的同一写法自建（与自动附魔「自定义附魔目标」同款）；
     * 草稿存在窗口侧，整页重建不丢未提交内容。名单为空时只留一句灰字空态，
     * 不给「点了没反应」的悬案（第 214 条）。</p>
     */
    private void addListRows(CompactStack content, String label, String desc, List<String> target) {
        content.add(new ConsoleRow(host, () -> label, desc, null, List.of(
            new Ctl(new SettingTextBox(() -> host.draft(label),
                value -> host.draft(label, value), TEXT_MAX_LENGTH)
                .width(TEXT_BOX_WIDTH), desc),
            new Ctl(new Button(AutoLoginTexts.LIST_ADD, () -> addValue(label, target))
                    .disabledWhen(() -> host.draft(label).isBlank()),
                AutoLoginTexts.LIST_ADD_HINT))));

        if (target.isEmpty()) {
            content.add(new Note(host, "  §8" + AutoLoginTexts.LIST_EMPTY));
            return;
        }
        for (String item : target) {
            content.add(new ConsoleRow(host, () -> item, null, null,
                List.of(new Ctl(new IconButton(GLYPH_REMOVE, () -> removeValue(target, item)).danger(),
                    AutoLoginTexts.LIST_REMOVE_HINT))));
        }
    }

    /** 添加一项：去空白后入名单（重复项跳过），落盘并重建本页让新行立刻出现 */
    private void addValue(String key, List<String> target) {
        String value = host.draft(key) == null ? "" : host.draft(key).strip();
        if (value.isEmpty()) return;
        if (!target.contains(value)) {
            target.add(value);
            persist();
        }
        host.draft(key, "");
        host.reload();
    }

    /** 移除一项：整份名单落盘并重建本页 */
    private void removeValue(List<String> target, String value) {
        if (!target.remove(value)) return;
        persist();
        host.reload();
    }

    /** 改设置即落盘（第 172-175 条） */
    private void persist() {
        module.persistSettings();
    }
}
