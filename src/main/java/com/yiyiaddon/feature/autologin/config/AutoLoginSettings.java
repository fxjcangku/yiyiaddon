package com.yiyiaddon.feature.autologin.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.feature.autologin.model.LeyuanWelcomeEntryMode;
import com.yiyiaddon.feature.autologin.model.ServerEntryMode;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动登入设置载体：55 项落盘设置 / 5 组（登录认证 / 自动重连 / 自动执行指令 / 进服路线 / 账号与调试）。
 *
 * <p><b>逐字资产</b>：设置名、默认值、取值域、可见性条件、分组全部来自旧项目
 * {@code autologin/config/AutoLoginSettings.java}；落盘键名直接用旧设置名本身，
 * 改一个键名都会让老档读不出来（开发习惯第二十五章第 172-175 条）。</p>
 *
 * <p><b>框架适配（旧 → 新，逐条）</b></p>
 * <ol>
 *     <li><b>两个物品项</b>（{@code 菜单工具} / {@code 自用菜单工具}）：旧项目是旧框架的
 *         {@code ItemSetting}（{@code Setting<Item>}，由框架序列化物品）；本项目按既有 ESP 类模块
 *         的做法改为<b>物品登记 ID 字符串</b>（{@code String}），界面走通用选择器
 *         （{@code SelectorScreen.pick}），判定处用 {@link #itemOf} 还原成物品比较，语义不变。
 *         默认值 {@code Items.CLOCK} 逐字对应 {@code minecraft:clock}。</li>
 *     <li><b>八个字符串名单</b>：旧 {@code StringListSetting} 由框架承载（列表控件属框架层，第 34 条）；
 *         本项目改为 {@code List<String>} + 控制台的自由名单编辑器（输入框 + 添加 / 逐项移除，
 *         与自动附魔「自定义附魔目标」同一写法），默认值逐条照旧。</li>
 *     <li><b>「立即检测账号」伪按钮</b>：旧项目用 {@code BoolSetting} 改值触发回调，值本身不入档；
 *         本项目由控制台上的一个按钮行承载（{@code ui/console/AutoLoginSettingsPage}），
 *         因此本类没有该字段，也没有对应的落盘键。</li>
 *     <li><b>死代码</b>：旧设置里的 {@code 隐藏断线页按钮}（{@code hideReconnectButton}）
 *         在旧项目全树只有声明、无任何使用点（{@code grep hideReconnectButton} 仅命中设置声明本身），
 *         按「死代码可删」口径不搬，也没有对应按键。</li>
 *     <li><b>可见性条件</b>：旧设置的 {@code visible(...)} 由控制台设置页按「条件不满足就不把该行
 *         加入堆叠」等价实现（整页可重建，改开关 / 改模式立刻增删行）。</li>
 * </ol>
 *
 * <p><b>重名键登记</b>：{@code 每步等待（tick）}在「进服路线」与「自用配置」各一项，
 * 旧框架设置为扁平名字表，同名两项共用同一个落盘键；本类按 1:1 保留同一行为
 * （{@link #menuActionDelay} 与 {@link #leyuanStepDelay} 读写同一个键），只登记不修改。</p>
 */
public final class AutoLoginSettings {

    // ── 取值域（逐字 = 旧设置声明；未给 max 的一律沿用旧框架默认上界 Integer.MAX_VALUE） ──

    public static final int DELAY_MIN = 0;
    public static final int DELAY_MAX = Integer.MAX_VALUE;
    public static final int AUTH_DETECT_TIMEOUT_MIN = 20;
    public static final int RECONNECT_DELAY_MIN = 20;
    public static final int MAX_RECONNECT_MIN = 1;
    public static final int MENU_ACTION_DELAY_MIN = 1;
    public static final int MENU_TIMEOUT_MIN = 20;
    public static final int LEYUAN_STEP_DELAY_MIN = 1;
    public static final int LEYUAN_STEP_TIMEOUT_MIN = 100;
    public static final int LEYUAN_CITY_FALLBACK_DELAY_MIN = 20;
    public static final int LEYUAN_AFK_MENU_DELAY_MIN = 0;
    public static final int LEYUAN_AFK_MENU_DELAY_MAX = 120;
    public static final int LEYUAN_RECOVERY_WAIT_MIN = 1;
    public static final int LEYUAN_RECOVERY_WAIT_MAX = 30;
    public static final int LEYUAN_RECOVERY_RETRY_MIN = 5;
    public static final int LEYUAN_RECOVERY_RETRY_MAX = 300;
    public static final int LEYUAN_RECOVERY_RETRIES_MIN = 1;
    public static final int LEYUAN_RECOVERY_RETRIES_MAX = 60;

    /** 两个物品项的默认值（旧 {@code Items.CLOCK}） */
    public static final String DEFAULT_MENU_TOOL = "minecraft:clock";

    // ── 组① 登录认证（旧 grpAuth，11 项落盘，顺序 = 旧声明顺序） ──

    /** 检测到登录提示时自动发送登录指令（默认开） */
    public boolean autoLogin = true;
    /** 自动识别并填写 GUI 登录框（默认关；仅「自动登录」开启时可见） */
    public boolean guiLogin;
    /** 服务器两小时免登录检测（默认关） */
    public boolean noLoginDetection;
    /** 大厅登录后进入子服沿用登录状态（默认开） */
    public boolean detectSubserver = true;
    /** 自动登录密码（默认空；仅「自动登录」开启时可见） */
    public String loginPassword = "";
    /** 收到登录提示后等待的 tick（默认 20；仅「自动登录」开启时可见） */
    public int loginDelay = 20;
    /** 检测到注册提示时自动发送注册指令（默认关） */
    public boolean autoRegister;
    /** 自动注册密码（默认空；仅「自动注册」开启时可见） */
    public String registerPassword = "";
    /** 收到注册提示后等待的 tick（默认 20；仅「自动注册」开启时可见） */
    public int registerDelay = 20;
    /** 进入服务器后等待的 tick（默认 200；认证三开关任一开启时可见） */
    public int worldLoadWait = 200;
    /** 认证监听超时（默认 200、最小 20；认证三开关任一开启时可见） */
    public int authDetectTimeout = 200;

    // ── 组② 自动重连（旧 grpReconnect，4 项，顺序 = 旧声明顺序） ──

    /** 断线后自动重连上一次服务器（默认开） */
    public boolean autoReconnect = true;
    /** 重连等待 tick（默认 100、最小 20；仅「自动重连」开启时可见） */
    public int reconnectDelay = 100;
    /** 无限重连（默认关；仅「自动重连」开启时可见） */
    public boolean alwaysReconnect;
    /** 最大重连次数（默认 5、最小 1；自动重连开且未开无限重连时可见） */
    public int maxReconnectAttempts = 5;

    // ── 组③ 自动执行指令（旧 grpCommands，3 项，顺序 = 旧声明顺序） ──

    /** 登录完成后执行一次自定义指令（默认关） */
    public boolean autoCommand;
    /** 登录完成后执行的指令（默认 /home） */
    public String autoCommandText = "/home";
    /** 指令延迟 tick（默认 60） */
    public int commandDelay = 60;

    // ── 组④ 进服路线 · 通用子服（旧 grpRoute 的「通用子服」分节，13 项） ──

    /** 认证完成后自动前往目标区域（默认开） */
    public boolean autoEnterSubserver = true;
    /** 进入方式（默认自用配置；仅「自动进入目标区域」开启时可见） */
    public ServerEntryMode serverEntryMode = ServerEntryMode.LEYUAN_CUSTOM;
    /** 菜单工具物品登记 ID（默认 minecraft:clock；仅通用子服路线可见） */
    public String menuToolId = DEFAULT_MENU_TOOL;
    /** 菜单物品关键词（默认「服务器选择」；仅通用子服路线可见） */
    public final List<String> menuItemKeywords = new ArrayList<>(List.of("服务器选择"));
    /** 菜单点击顺序（默认「生存区 / 生存二区」；仅通用子服路线可见） */
    public final List<String> menuClickSteps = new ArrayList<>(List.of("生存区", "生存二区"));
    /** 每步等待 tick（默认 20、最小 1；仅通用子服路线可见） */
    public int menuActionDelay = 20;
    /** 菜单超时 tick（默认 200、最小 20；仅通用子服路线可见） */
    public int menuTimeout = 200;
    /** 目标区域关键词（默认「生存二区」；仅通用子服路线可见） */
    public final List<String> targetAreaKeywords = new ArrayList<>(List.of("生存二区"));
    /** 必须命中目标关键词（默认开；仅通用子服路线可见） */
    public boolean requireTargetKeyword = true;
    /** 到达稳定等待 tick（默认 60；仅「自动进入目标区域」开启时可见） */
    public int targetStableDelay = 60;
    /** 到达目标后执行指令（默认关；仅「自动进入目标区域」开启时可见） */
    public boolean subserverCommand;
    /** 目标区域指令（默认 /home；到达目标后执行指令开启时可见） */
    public String subserverCommandText = "/home";
    /** 额外指令延迟 tick（默认 60；到达目标后执行指令开启时可见） */
    public int subserverCommandDelay = 60;

    // ── 组⑤ 进服路线 · 自用配置（旧 grpRoute 的「自用配置」分节，22 项） ──

    /** 自用总开关（默认开；仅「自动进入目标区域」关闭且进入方式为自用配置时……按旧条件：进入方式 = 自用配置） */
    public boolean leyuanEnabled = true;
    /** 自用菜单工具物品登记 ID（默认 minecraft:clock；自用模式可见） */
    public String leyuanMenuToolId = DEFAULT_MENU_TOOL;
    /** 菜单工具关键词（默认「进入服务器 / 菜单」；自用模式可见） */
    public final List<String> leyuanMenuItemKeywords = new ArrayList<>(List.of("进入服务器", "菜单"));
    /** 登录服按钮关键词（默认「生存服」；自用模式可见） */
    public String leyuanLoginSurvivalKeyword = "生存服";
    /** 欢迎页入口方式（默认书本直达主城；自用模式可见） */
    public LeyuanWelcomeEntryMode leyuanWelcomeEntryMode = LeyuanWelcomeEntryMode.BOOK_DIRECT;
    /** 主城世界传送按钮关键词（默认「世界传送」；自用模式可见） */
    public String leyuanWorldTransferKeyword = "世界传送";
    /** 第一层生存按钮关键词（默认「资源大区」；自用模式可见） */
    public String leyuanSurvivalFirstKeyword = "资源大区";
    /** 第二层生存按钮关键词（默认「资源大区」；自用模式可见） */
    public String leyuanSurvivalSecondKeyword = "资源大区";
    /** 最终目标子服按钮关键词（默认「资源二区」；自用模式可见） */
    public String leyuanTargetServerKeyword = "资源二区";
    /** 主城到达关键词（默认「当前位于主城大区 / 主城大区」；自用模式可见） */
    public final List<String> leyuanMainCityKeywords =
        new ArrayList<>(List.of("当前位于主城大区", "主城大区"));
    /** 挂机区识别关键词（默认「挂机区自动发放金币/经验」；自用模式可见） */
    public final List<String> leyuanAfkAreaKeywords =
        new ArrayList<>(List.of("挂机区自动发放金币/经验"));
    /** 挂机区自动回服（默认开；自用模式可见） */
    public boolean leyuanAfkRecoveryEnabled = true;
    /** 挂机区菜单延迟分钟（默认 10、0~120；自用模式且挂机区自动回服开启时可见） */
    public int leyuanAfkMenuDelayMinutes = 10;
    /** 返回主城按钮关键词（默认「返回主城大区」；自用模式可见） */
    public final List<String> leyuanReturnMainCityKeywords =
        new ArrayList<>(List.of("返回主城大区"));
    /** 到达确认关键词（默认四个区名；自用模式可见） */
    public final List<String> leyuanTargetAreaKeywords =
        new ArrayList<>(List.of("资源一区", "资源二区", "生存一区", "生存二区"));
    /** 每步等待 tick（默认 20、最小 1；自用模式可见） */
    public int leyuanStepDelay = 20;
    /** 单步超时 tick（默认 600、最小 100；自用模式可见） */
    public int leyuanStepTimeout = 600;
    /** 主城钟快捷键兜底（默认开；自用模式可见） */
    public boolean leyuanCityMenuFallback = true;
    /** 主城钟兜底等待 tick（默认 80、最小 20；自用模式可见） */
    public int leyuanCityMenuFallbackDelay = 80;
    /** 异常恢复等待上限分钟（默认 20、1~30；自用模式可见） */
    public int leyuanRecoveryWaitMinutes = 20;
    /** 异常恢复初始重试秒（默认 30、5~300；自用模式可见） */
    public int leyuanRecoveryRetrySeconds = 30;
    /** 异常恢复最大重试次数（默认 30、1~60；自用模式可见） */
    public int leyuanRecoveryMaxRetries = 30;

    // ── 组⑥ 账号与调试（旧 grpAccount，2 项落盘，顺序 = 旧声明顺序） ──

    /** 进服时自动检测账号类型（默认关） */
    public boolean autoCheckAccount;
    /** 调试模式（默认关） */
    public boolean debugMode;

    // ── 派生只读（逐字 = 旧设置类的同名方法） ──

    /** 通用子服路线：进入方式不是「直接进入」也不是「自用配置」 */
    public boolean usesStandardMenuRoute() {
        return autoEnterSubserver
            && serverEntryMode != ServerEntryMode.DIRECT
            && serverEntryMode != ServerEntryMode.LEYUAN_CUSTOM;
    }

    /** 自用回服路线（总开关打开） */
    public boolean usesLeyuanRoute() {
        return autoEnterSubserver
            && serverEntryMode == ServerEntryMode.LEYUAN_CUSTOM
            && leyuanEnabled;
    }

    /** 自用模式（进入方式 = 自用配置；不看总开关） */
    public boolean usesLeyuanMode() {
        return autoEnterSubserver
            && serverEntryMode == ServerEntryMode.LEYUAN_CUSTOM;
    }

    /** 认证三开关是否有任一开启（旧设置里多处 visible 条件的同一判据） */
    public boolean anyAuthEnabled() {
        return autoLogin || autoRegister || noLoginDetection;
    }

    /** 菜单工具物品；ID 非法 / 不存在 / 为空气返回 {@code null}（= 未选择） */
    public Item menuTool() {
        return itemOf(menuToolId);
    }

    /** 自用菜单工具物品；ID 非法 / 不存在 / 为空气返回 {@code null}（= 未选择） */
    public Item leyuanMenuTool() {
        return itemOf(leyuanMenuToolId);
    }

    /** 物品登记 ID → 物品；认不出的 ID 返回 {@code null}，不猜 */
    public static Item itemOf(String itemId) {
        if (itemId == null || itemId.isBlank()) return null;
        Identifier id = Identifier.tryParse(itemId);
        if (id == null) return null;
        Item item = BuiltInRegistries.ITEM.getValue(id);
        return item == null || item == Items.AIR ? null : item;
    }

    // ── 编解码（键名与旧设置名逐字对应，读写两端必须对称） ──

    /** 从模块设置对象载入；缺失字段保持默认值，数值收拢回取值域 */
    public void load(JsonObject json) {
        if (json == null) return;

        autoLogin = boolOf(json, AutoLoginTexts.NAME_AUTO_LOGIN, autoLogin);
        guiLogin = boolOf(json, AutoLoginTexts.NAME_GUI_LOGIN, guiLogin);
        noLoginDetection = boolOf(json, AutoLoginTexts.NAME_NO_LOGIN_DETECTION, noLoginDetection);
        detectSubserver = boolOf(json, AutoLoginTexts.NAME_DETECT_SUBSERVER, detectSubserver);
        loginPassword = stringOf(json, AutoLoginTexts.NAME_LOGIN_PASSWORD, loginPassword);
        loginDelay = clamp(intOf(json, AutoLoginTexts.NAME_LOGIN_DELAY, loginDelay), DELAY_MIN, DELAY_MAX);
        autoRegister = boolOf(json, AutoLoginTexts.NAME_AUTO_REGISTER, autoRegister);
        registerPassword = stringOf(json, AutoLoginTexts.NAME_REGISTER_PASSWORD, registerPassword);
        registerDelay = clamp(intOf(json, AutoLoginTexts.NAME_REGISTER_DELAY, registerDelay), DELAY_MIN, DELAY_MAX);
        worldLoadWait = clamp(intOf(json, AutoLoginTexts.NAME_WORLD_LOAD_WAIT, worldLoadWait), DELAY_MIN, DELAY_MAX);
        authDetectTimeout = clamp(intOf(json, AutoLoginTexts.NAME_AUTH_DETECT_TIMEOUT, authDetectTimeout),
            AUTH_DETECT_TIMEOUT_MIN, DELAY_MAX);

        autoReconnect = boolOf(json, AutoLoginTexts.NAME_AUTO_RECONNECT, autoReconnect);
        reconnectDelay = clamp(intOf(json, AutoLoginTexts.NAME_RECONNECT_DELAY, reconnectDelay),
            RECONNECT_DELAY_MIN, DELAY_MAX);
        alwaysReconnect = boolOf(json, AutoLoginTexts.NAME_ALWAYS_RECONNECT, alwaysReconnect);
        maxReconnectAttempts = clamp(intOf(json, AutoLoginTexts.NAME_MAX_RECONNECT_ATTEMPTS, maxReconnectAttempts),
            MAX_RECONNECT_MIN, DELAY_MAX);

        autoCommand = boolOf(json, AutoLoginTexts.NAME_AUTO_COMMAND, autoCommand);
        autoCommandText = stringOf(json, AutoLoginTexts.NAME_AUTO_COMMAND_TEXT, autoCommandText);
        commandDelay = clamp(intOf(json, AutoLoginTexts.NAME_COMMAND_DELAY, commandDelay), DELAY_MIN, DELAY_MAX);

        autoEnterSubserver = boolOf(json, AutoLoginTexts.NAME_AUTO_ENTER_SUBSERVER, autoEnterSubserver);
        ServerEntryMode entryMode = ServerEntryMode.ofName(stringOf(json, AutoLoginTexts.NAME_SERVER_ENTRY_MODE, null));
        if (entryMode != null) serverEntryMode = entryMode;
        menuToolId = stringOf(json, AutoLoginTexts.NAME_MENU_TOOL, menuToolId);
        replaceAll(menuItemKeywords, listOf(json, AutoLoginTexts.NAME_MENU_ITEM_KEYWORDS, menuItemKeywords));
        replaceAll(menuClickSteps, listOf(json, AutoLoginTexts.NAME_MENU_CLICK_STEPS, menuClickSteps));
        menuActionDelay = clamp(intOf(json, AutoLoginTexts.NAME_MENU_ACTION_DELAY, menuActionDelay),
            MENU_ACTION_DELAY_MIN, DELAY_MAX);
        menuTimeout = clamp(intOf(json, AutoLoginTexts.NAME_MENU_TIMEOUT, menuTimeout), MENU_TIMEOUT_MIN, DELAY_MAX);
        replaceAll(targetAreaKeywords, listOf(json, AutoLoginTexts.NAME_TARGET_AREA_KEYWORDS, targetAreaKeywords));
        requireTargetKeyword = boolOf(json, AutoLoginTexts.NAME_REQUIRE_TARGET_KEYWORD, requireTargetKeyword);
        targetStableDelay = clamp(intOf(json, AutoLoginTexts.NAME_TARGET_STABLE_DELAY, targetStableDelay),
            DELAY_MIN, DELAY_MAX);
        subserverCommand = boolOf(json, AutoLoginTexts.NAME_SUBSERVER_COMMAND, subserverCommand);
        subserverCommandText = stringOf(json, AutoLoginTexts.NAME_SUBSERVER_COMMAND_TEXT, subserverCommandText);
        subserverCommandDelay = clamp(intOf(json, AutoLoginTexts.NAME_SUBSERVER_COMMAND_DELAY, subserverCommandDelay),
            DELAY_MIN, DELAY_MAX);

        leyuanEnabled = boolOf(json, AutoLoginTexts.NAME_LEYUAN_ENABLED, leyuanEnabled);
        leyuanMenuToolId = stringOf(json, AutoLoginTexts.NAME_LEYUAN_MENU_TOOL, leyuanMenuToolId);
        replaceAll(leyuanMenuItemKeywords,
            listOf(json, AutoLoginTexts.NAME_LEYUAN_MENU_ITEM_KEYWORDS, leyuanMenuItemKeywords));
        leyuanLoginSurvivalKeyword =
            stringOf(json, AutoLoginTexts.NAME_LEYUAN_LOGIN_SURVIVAL_KEYWORD, leyuanLoginSurvivalKeyword);
        LeyuanWelcomeEntryMode welcomeMode =
            LeyuanWelcomeEntryMode.ofName(stringOf(json, AutoLoginTexts.NAME_LEYUAN_WELCOME_ENTRY_MODE, null));
        if (welcomeMode != null) leyuanWelcomeEntryMode = welcomeMode;
        leyuanWorldTransferKeyword =
            stringOf(json, AutoLoginTexts.NAME_LEYUAN_WORLD_TRANSFER_KEYWORD, leyuanWorldTransferKeyword);
        leyuanSurvivalFirstKeyword =
            stringOf(json, AutoLoginTexts.NAME_LEYUAN_SURVIVAL_FIRST_KEYWORD, leyuanSurvivalFirstKeyword);
        leyuanSurvivalSecondKeyword =
            stringOf(json, AutoLoginTexts.NAME_LEYUAN_SURVIVAL_SECOND_KEYWORD, leyuanSurvivalSecondKeyword);
        leyuanTargetServerKeyword =
            stringOf(json, AutoLoginTexts.NAME_LEYUAN_TARGET_SERVER_KEYWORD, leyuanTargetServerKeyword);
        replaceAll(leyuanMainCityKeywords,
            listOf(json, AutoLoginTexts.NAME_LEYUAN_MAIN_CITY_KEYWORDS, leyuanMainCityKeywords));
        replaceAll(leyuanAfkAreaKeywords,
            listOf(json, AutoLoginTexts.NAME_LEYUAN_AFK_AREA_KEYWORDS, leyuanAfkAreaKeywords));
        leyuanAfkRecoveryEnabled =
            boolOf(json, AutoLoginTexts.NAME_LEYUAN_AFK_RECOVERY_ENABLED, leyuanAfkRecoveryEnabled);
        leyuanAfkMenuDelayMinutes = clamp(
            intOf(json, AutoLoginTexts.NAME_LEYUAN_AFK_MENU_DELAY_MINUTES, leyuanAfkMenuDelayMinutes),
            LEYUAN_AFK_MENU_DELAY_MIN, LEYUAN_AFK_MENU_DELAY_MAX);
        replaceAll(leyuanReturnMainCityKeywords,
            listOf(json, AutoLoginTexts.NAME_LEYUAN_RETURN_MAIN_CITY_KEYWORDS, leyuanReturnMainCityKeywords));
        replaceAll(leyuanTargetAreaKeywords,
            listOf(json, AutoLoginTexts.NAME_LEYUAN_TARGET_AREA_KEYWORDS, leyuanTargetAreaKeywords));
        // 「每步等待（tick）」两处同名：先读通用子服，再用同一个键覆盖自用配置（旧行为保留，见类注释）
        leyuanStepDelay = clamp(intOf(json, AutoLoginTexts.NAME_LEYUAN_STEP_DELAY, leyuanStepDelay),
            LEYUAN_STEP_DELAY_MIN, DELAY_MAX);
        leyuanStepTimeout = clamp(intOf(json, AutoLoginTexts.NAME_LEYUAN_STEP_TIMEOUT, leyuanStepTimeout),
            LEYUAN_STEP_TIMEOUT_MIN, DELAY_MAX);
        leyuanCityMenuFallback =
            boolOf(json, AutoLoginTexts.NAME_LEYUAN_CITY_MENU_FALLBACK, leyuanCityMenuFallback);
        leyuanCityMenuFallbackDelay = clamp(
            intOf(json, AutoLoginTexts.NAME_LEYUAN_CITY_MENU_FALLBACK_DELAY, leyuanCityMenuFallbackDelay),
            LEYUAN_CITY_FALLBACK_DELAY_MIN, DELAY_MAX);
        leyuanRecoveryWaitMinutes = clamp(
            intOf(json, AutoLoginTexts.NAME_LEYUAN_RECOVERY_WAIT_MINUTES, leyuanRecoveryWaitMinutes),
            LEYUAN_RECOVERY_WAIT_MIN, LEYUAN_RECOVERY_WAIT_MAX);
        leyuanRecoveryRetrySeconds = clamp(
            intOf(json, AutoLoginTexts.NAME_LEYUAN_RECOVERY_RETRY_SECONDS, leyuanRecoveryRetrySeconds),
            LEYUAN_RECOVERY_RETRY_MIN, LEYUAN_RECOVERY_RETRY_MAX);
        leyuanRecoveryMaxRetries = clamp(
            intOf(json, AutoLoginTexts.NAME_LEYUAN_RECOVERY_MAX_RETRIES, leyuanRecoveryMaxRetries),
            LEYUAN_RECOVERY_RETRIES_MIN, LEYUAN_RECOVERY_RETRIES_MAX);

        autoCheckAccount = boolOf(json, AutoLoginTexts.NAME_AUTO_CHECK_ACCOUNT, autoCheckAccount);
        debugMode = boolOf(json, AutoLoginTexts.NAME_DEBUG_MODE, debugMode);
    }

    /** 把字段写入模块设置对象（与 {@link #load} 同一批键，两端必须对称） */
    public void save(JsonObject json) {
        json.addProperty(AutoLoginTexts.NAME_AUTO_LOGIN, autoLogin);
        json.addProperty(AutoLoginTexts.NAME_GUI_LOGIN, guiLogin);
        json.addProperty(AutoLoginTexts.NAME_NO_LOGIN_DETECTION, noLoginDetection);
        json.addProperty(AutoLoginTexts.NAME_DETECT_SUBSERVER, detectSubserver);
        json.addProperty(AutoLoginTexts.NAME_LOGIN_PASSWORD, loginPassword);
        json.addProperty(AutoLoginTexts.NAME_LOGIN_DELAY, loginDelay);
        json.addProperty(AutoLoginTexts.NAME_AUTO_REGISTER, autoRegister);
        json.addProperty(AutoLoginTexts.NAME_REGISTER_PASSWORD, registerPassword);
        json.addProperty(AutoLoginTexts.NAME_REGISTER_DELAY, registerDelay);
        json.addProperty(AutoLoginTexts.NAME_WORLD_LOAD_WAIT, worldLoadWait);
        json.addProperty(AutoLoginTexts.NAME_AUTH_DETECT_TIMEOUT, authDetectTimeout);

        json.addProperty(AutoLoginTexts.NAME_AUTO_RECONNECT, autoReconnect);
        json.addProperty(AutoLoginTexts.NAME_RECONNECT_DELAY, reconnectDelay);
        json.addProperty(AutoLoginTexts.NAME_ALWAYS_RECONNECT, alwaysReconnect);
        json.addProperty(AutoLoginTexts.NAME_MAX_RECONNECT_ATTEMPTS, maxReconnectAttempts);

        json.addProperty(AutoLoginTexts.NAME_AUTO_COMMAND, autoCommand);
        json.addProperty(AutoLoginTexts.NAME_AUTO_COMMAND_TEXT, autoCommandText);
        json.addProperty(AutoLoginTexts.NAME_COMMAND_DELAY, commandDelay);

        json.addProperty(AutoLoginTexts.NAME_AUTO_ENTER_SUBSERVER, autoEnterSubserver);
        json.addProperty(AutoLoginTexts.NAME_SERVER_ENTRY_MODE, serverEntryMode.name());
        json.addProperty(AutoLoginTexts.NAME_MENU_TOOL, menuToolId);
        json.add(AutoLoginTexts.NAME_MENU_ITEM_KEYWORDS, listToJson(menuItemKeywords));
        json.add(AutoLoginTexts.NAME_MENU_CLICK_STEPS, listToJson(menuClickSteps));
        json.addProperty(AutoLoginTexts.NAME_MENU_ACTION_DELAY, menuActionDelay);
        json.addProperty(AutoLoginTexts.NAME_MENU_TIMEOUT, menuTimeout);
        json.add(AutoLoginTexts.NAME_TARGET_AREA_KEYWORDS, listToJson(targetAreaKeywords));
        json.addProperty(AutoLoginTexts.NAME_REQUIRE_TARGET_KEYWORD, requireTargetKeyword);
        json.addProperty(AutoLoginTexts.NAME_TARGET_STABLE_DELAY, targetStableDelay);
        json.addProperty(AutoLoginTexts.NAME_SUBSERVER_COMMAND, subserverCommand);
        json.addProperty(AutoLoginTexts.NAME_SUBSERVER_COMMAND_TEXT, subserverCommandText);
        json.addProperty(AutoLoginTexts.NAME_SUBSERVER_COMMAND_DELAY, subserverCommandDelay);

        json.addProperty(AutoLoginTexts.NAME_LEYUAN_ENABLED, leyuanEnabled);
        json.addProperty(AutoLoginTexts.NAME_LEYUAN_MENU_TOOL, leyuanMenuToolId);
        json.add(AutoLoginTexts.NAME_LEYUAN_MENU_ITEM_KEYWORDS, listToJson(leyuanMenuItemKeywords));
        json.addProperty(AutoLoginTexts.NAME_LEYUAN_LOGIN_SURVIVAL_KEYWORD, leyuanLoginSurvivalKeyword);
        json.addProperty(AutoLoginTexts.NAME_LEYUAN_WELCOME_ENTRY_MODE, leyuanWelcomeEntryMode.name());
        json.addProperty(AutoLoginTexts.NAME_LEYUAN_WORLD_TRANSFER_KEYWORD, leyuanWorldTransferKeyword);
        json.addProperty(AutoLoginTexts.NAME_LEYUAN_SURVIVAL_FIRST_KEYWORD, leyuanSurvivalFirstKeyword);
        json.addProperty(AutoLoginTexts.NAME_LEYUAN_SURVIVAL_SECOND_KEYWORD, leyuanSurvivalSecondKeyword);
        json.addProperty(AutoLoginTexts.NAME_LEYUAN_TARGET_SERVER_KEYWORD, leyuanTargetServerKeyword);
        json.add(AutoLoginTexts.NAME_LEYUAN_MAIN_CITY_KEYWORDS, listToJson(leyuanMainCityKeywords));
        json.add(AutoLoginTexts.NAME_LEYUAN_AFK_AREA_KEYWORDS, listToJson(leyuanAfkAreaKeywords));
        json.addProperty(AutoLoginTexts.NAME_LEYUAN_AFK_RECOVERY_ENABLED, leyuanAfkRecoveryEnabled);
        json.addProperty(AutoLoginTexts.NAME_LEYUAN_AFK_MENU_DELAY_MINUTES, leyuanAfkMenuDelayMinutes);
        json.add(AutoLoginTexts.NAME_LEYUAN_RETURN_MAIN_CITY_KEYWORDS, listToJson(leyuanReturnMainCityKeywords));
        json.add(AutoLoginTexts.NAME_LEYUAN_TARGET_AREA_KEYWORDS, listToJson(leyuanTargetAreaKeywords));
        // 同名键：后写的值即最终值（旧行为保留，见类注释）
        json.addProperty(AutoLoginTexts.NAME_LEYUAN_STEP_DELAY, leyuanStepDelay);
        json.addProperty(AutoLoginTexts.NAME_LEYUAN_STEP_TIMEOUT, leyuanStepTimeout);
        json.addProperty(AutoLoginTexts.NAME_LEYUAN_CITY_MENU_FALLBACK, leyuanCityMenuFallback);
        json.addProperty(AutoLoginTexts.NAME_LEYUAN_CITY_MENU_FALLBACK_DELAY, leyuanCityMenuFallbackDelay);
        json.addProperty(AutoLoginTexts.NAME_LEYUAN_RECOVERY_WAIT_MINUTES, leyuanRecoveryWaitMinutes);
        json.addProperty(AutoLoginTexts.NAME_LEYUAN_RECOVERY_RETRY_SECONDS, leyuanRecoveryRetrySeconds);
        json.addProperty(AutoLoginTexts.NAME_LEYUAN_RECOVERY_MAX_RETRIES, leyuanRecoveryMaxRetries);

        json.addProperty(AutoLoginTexts.NAME_AUTO_CHECK_ACCOUNT, autoCheckAccount);
        json.addProperty(AutoLoginTexts.NAME_DEBUG_MODE, debugMode);
    }

    // ── 原语 ──

    /** 名单整表替换（保持同一实例，界面侧持有的引用不失效） */
    private static void replaceAll(List<String> target, List<String> source) {
        target.clear();
        target.addAll(source);
    }

    private static boolean boolOf(JsonObject json, String key, boolean fallback) {
        JsonElement element = json.get(key);
        return element != null && element.isJsonPrimitive() ? element.getAsBoolean() : fallback;
    }

    private static int intOf(JsonObject json, String key, int fallback) {
        JsonElement element = json.get(key);
        return element != null && element.isJsonPrimitive() ? element.getAsInt() : fallback;
    }

    private static String stringOf(JsonObject json, String key, String fallback) {
        JsonElement element = json.get(key);
        return element != null && element.isJsonPrimitive() ? element.getAsString() : fallback;
    }

    private static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    /** 读字符串名单：非数组或非字符串项一律跳过，不猜；键不存在时复制一份原名单（避免同实例被整表替换清空） */
    private static List<String> listOf(JsonObject json, String key, List<String> fallback) {
        JsonElement element = json.get(key);
        if (element == null || !element.isJsonArray()) return new ArrayList<>(fallback);
        List<String> values = new ArrayList<>();
        for (JsonElement item : element.getAsJsonArray()) {
            if (item != null && item.isJsonPrimitive()) values.add(item.getAsString());
        }
        return values;
    }

    private static JsonArray listToJson(List<String> values) {
        JsonArray array = new JsonArray();
        for (String value : values) {
            if (value != null && !value.isBlank()) array.add(value);
        }
        return array;
    }
}
