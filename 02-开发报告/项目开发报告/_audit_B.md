# 审计 B · 结构性新增项核查

> 只读审计。审计对象：`d:\mcaddon\yiyiaddon\src\main\java\com\yiyiaddon\`（221 个 .java）。
> 只读参照：`d:\mcaddon\26.1.2\src\main\java\com\example\addon\`（404 个 .java）。
> 本次未修改、未删除、未移动任何源码；结论全部带 `文件:行号` 或检索证据。

---

## 0. 覆盖口径

新项目 `.java` 总数 **221**（命令 `Get-ChildItem -Recurse -Filter *.java | Measure-Object` 结果 221）。
旧项目 `.java` 总数 **404**（同法计数，与任务书一致）。

按包分组文件数（新项目，`com/yiyiaddon` 之下）：

| 包 | 数 | 包 | 数 | 包 | 数 |
| --- | --- | --- | --- | --- | --- |
| `(root)` | 2 | `command` | 6 | `config` | 2 |
| `config/identity` | 1 | `core` | 6 | `core/event` | 5 |
| `core/module` | 5 | `feature/identity` | 2 | `feature/identity/command` | 1 |
| `feature/identity/config` | 1 | `feature/identity/model` | 1 | `feature/identity/service` | 1 |
| `feature/identity/ui` | 9 | `feature/visuals` | 1 | `feature/visuals/ui` | 1 |
| `integration/baritone` | 4 | `mixin/baritone` | 6 | `mixin/client` | 8 |
| `model` | 7 | `model/container` | 1 | `model/identity` | 4 |
| `model/resource` | 7 | `module` | 5 | `platform` | 4 |
| `platform/container` | 1 | `platform/identity` | 4 | `platform/network` | 1 |
| `platform/resource` | 4 | `platform/storage` | 1 | `platform/world` | 1 |
| `repository` | 1 | `repository/identity` | 5 | `repository/resource` | 1 |
| `service` | 9 | `service/container` | 1 | `service/identity` | 1 |
| `service/network` | 1 | `service/resourcepack` | 5 | `ui` | 1 |
| `ui/anim` | 4 | `ui/component` | 23 | `ui/keybind` | 2 |
| `ui/navigation` | 1 | `ui/page` | 11 | `ui/render` | 8 |
| `ui/render/world` | 11 | `ui/screen` | 7 | `ui/theme` | 11 |
| `ui/theme/vape` | 1 | `ui/widget` | 14 | `utils` | 1 |

合计 221。

**旧项目无对应包**（新项目独有顶层包）：`command/`、`core/event/`、`core/module/`、`module/`、`platform/`、`repository/`、`ui/anim`、`ui/component`、`ui/render`、`ui/render/world`、`ui/screen`、`ui/theme`、`ui/widget`、`ui/navigation`、`ui/page` 的大部分、`integration/`、`service/` 的全部。
旧项目的等价能力由第三方框架（Meteor 的 Modules/Commands/GuiThemes/Settings/EventBus）与其自带界面提供，新项目脱离该框架后必须自研，故上述包整体属框架层。

---

## 1. 结论摘要

| 类别 | 类数 | 说明 |
| --- | --- | --- |
| 框架层自研 | **141** | 事件总线、模块运行时、指令骨架、Skija UI/渲染、主题系统、配置持久化、平台抽象、Mixin 桥接、脚手架以外的入口类 |
| 复刻实现 | **75** | 与旧项目类一一对应（名字/拆分不同，职责对应），含身份识别全套、Baritone 汉化 + Mixin、后端账号/统计/聊天服务、资源包链路、容器/发包底座 |
| 开发期脚手架 | **2** | `feature/visuals/EspTestModule`、`feature/visuals/ui/EspTestPage`（源码自述为脚手架、可整体删除） |
| **多加的** | **3** | 三个**未被主题管理器注册、无任何引用**的主题类（详见第 2 节） |
| 合计 | 221 | 141 + 75 + 2 + 3 = 221 |

判定口径：不属于「框架必需 / 与旧类职责对应 / 明示可删的测试类」三类的，计为「多加的」。
另有一批**属框架或复刻、但当前零消费方**的类，以及**口径存疑项**，列在第 6 节，不计入「多加的」。

---

## 2. 「多加的」清单（重点）

| # | 类 | 路径（行数） | 作用 | 是否已进入用户可见入口（模块列表 / 指令 / 界面） | 引用点 | 建议处置 |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | `VapeTheme` | `src/main/java/com/yiyiaddon/ui/theme/vape/VapeTheme.java`（34） | 实现 `ClickGuiTheme` 的一套配色（id=`vape`，displayName=`Vape`），第三方面板外壳随附 | **否**。主题选择入口只列出已注册主题（`InterfacePage` 的「面板主题」→ `ClickGuiScreen.openThemePreview`，`ClickGuiThemeManager.themes()`），本类不在其中 | 全项目源码检索：仅自身文件命中（`VapeTheme.java:7`），无任何 `new VapeTheme` / `ClickGuiThemeManager.register`；仅文档/NOTICE 提及（`NOTICE:52,170`、`开发习惯.md:720`） | 保留或删除均可：属第三方面板外壳随附主题，已登记 NOTICE；若要「无无调用方的类」口径则删除 |
| 2 | `LightBlueTheme` | `src/main/java/com/yiyiaddon/ui/theme/LightBlueTheme.java`（34） | 淡蓝配色主题 | **否**（同上，未注册） | 仅自身文件命中（`LightBlueTheme.java:6`）；文档 `14-阶段01-项目初始化与界面基线.md:211,363` | 同上 |
| 3 | `DefaultClickGuiTheme` | `src/main/java/com/yiyiaddon/ui/theme/DefaultClickGuiTheme.java`（31） | 浅色默认配色主题（id=`default`） | **否**（同上，未注册） | 仅自身文件命中（`DefaultClickGuiTheme.java:3`）；`NOTICE:51,170` | 同上 |

证据与判据：
- 主题系统实际注册的只有 4 个：`ClickGuiThemeManager.java:12`（`FALLBACK = new AppleDarkTheme()`）、`:17-19`（`register(new WhiteTheme/DarkTheme/GrayTheme)`）；`themes()` 只回读该表（`:31-33`）。
- 上述 3 类既不在注册表，也无 `new`、无 import，全项目零引用（见上表「引用点」）。
- `NOTICE:167-171`（2.2(k)）原话：`PVPUtils registers only WhiteTheme, DarkTheme and GrayTheme ... The remaining theme classes (LightBlueTheme, DefaultClickGuiTheme, VapeTheme) are also unregistered upstream. This was deliberately left unchanged`；`14-阶段01-项目初始化与界面基线.md:211` 原话：`本轮**刻意保持原样**，未做任何视觉层面的增删改`。
- 判定：不满足「框架必需」（框架只需已注册的 4 套）、不属「复刻」（旧项目无主题系统，旧 `theme/ThemeModule.java` 是给 Meteor 换配色，与 ClickGUI 主题类无对应）、非「脚手架」（源码无测试/调试声明）。故计为**多加的**。
- 注意：这 3 类是随自研界面外壳一并引入的（整套 `ui/` 外壳来源见第 6 节 #9），删除后不牵连任何代码（零引用）；若保留，也已由 `NOTICE` 与阶段 01 报告登记，不构成隐瞒。

**除以上 3 类外，全项目再无可归为「多加的」的类。** 特别核查以下三处（结论：均不属于多加的）：

- `feature/` 下旧项目不存在的模块：仅 `feature/visuals/`（ESP 测试）→ 源码自述为**脚手架**，计入第 3 节，非多加的。
- `command/` 下旧项目不存在的指令：仅 `.help`、`.module` 两条（`CommandManager.java:89-90`），框架指令骨架，属框架必需。
- 历史遗留的多加指令 `.resource`：`20-阶段07-资源包识别闭环与基础验证.md:150` 记载曾新增 `ResourceCommand.java`（152 行），现源码树已**不存在**该文件（`command/` 目录仅 6 个文件；全项目检索 `ResourceCommand` 仅命中文档），已被 `08-规范-用户交互资产迁移.md:1019` 那句「`.resource` 属越权新增（待整改）…只能作附加调试入口或删除」处置掉；无需再算。

---

## 3. 开发期脚手架清单

| # | 类 | 路径 | 声明为脚手架的证据（文件:行 + 原话） | 当前是否在用户可见入口 | 建议 |
| --- | --- | --- | --- | --- | --- |
| 1 | `EspTestModule` | `src/main/java/com/yiyiaddon/feature/visuals/EspTestModule.java`（191） | `EspTestModule.java:24`：`<b>这是脚手架，不是业务模块。</b>每个测试项对应一层 {@link WorldOverlay} 绘制回调`；`:25`：`业务模块移植完成后可整体删除。` | **是**。已注册为用户可见模块：`module/AddonModules.java:57` `new EspTestModule()`（`createModules()` 内），分类 `tools`（`EspTestModule.java:73` `super(MODULE_ID, "ESP测试", "tools", …)`），权重 900（`:83`），有独立页面（`:92-94` `page()` 返回 `EspTestPage`）与快捷键键名（`Module.java:91-93` → `module.esp_test`） | 保留（世界渲染基建验证用，见 `21-追加-世界渲染基建与独立窗口体系.md:91`「7 项…实际功能验证」）。业务模块移植完成后按自述整体删除；删除时仅需去掉 `AddonModules.java:9,57` 两处引用 |
| 2 | `EspTestPage` | `src/main/java/com/yiyiaddon/feature/visuals/ui/EspTestPage.java`（74） | 与上同类；由 `EspTestModule.page()` 提供，无「可删」字样但整包 `feature/visuals/` 即测试包（`EspTestModule.java:24-25` 定义该包性质） | **是**。作为 `EspTestModule` 的独立页面进入模块中心（`EspTestModule.java:92-94`） | 随 `EspTestModule` 一并删除 |

删除牵连范围（检索结果）：`EspTestModule` 被 `AddonModules.java:9`（import）、`:57`（构造）引用；`EspTestPage` 被 `EspTestModule.java:4`（import）、`:93`（构造）引用。`EspTestModule` 反向引用 `ui/render/world/{ColorPresets,EspColor,EspRenderer,ShapeMode,WorldOverlay}`、`ui/page/ModulePage`、若干 `ui/widget`/`ui/component`；删除脚手架**不影响**这些框架类自身（它们仍被 `GuiRendererMixin.java:26` → `WorldOverlay.renderFrame()` 等使用）。

---

## 4. 框架层自研清单（证明必要性的简述）

总述（覆盖性判据）：旧项目 `com.example.addon` 无 `command/`、`core/event/`、`core/module/`、`module/`、`platform/`、`repository/`、`ui/{anim,component,navigation,render,screen,theme,widget}` 等包；其模块/指令/GUI/事件/设置由第三方客户端框架（Meteor）提供。新项目脱离该框架，下列类均为「旧项目由第三方框架提供、新项目必须自研」的框架层。
（唯一例外：`ui/` 外壳整体派生自第三方开源项目 PVPUtils，非纯自研，见 `NOTICE:31-92` 与第 6 节 #9。）

### 4.1 入口与配置（5）

| 类 | 作用 |
| --- | --- |
| `YiyiAddon` | Mod 主入口（`YiyiAddon.java:15-24`） |
| `YiyiAddonClient` | 客户端入口：主题恢复、模块引导、快捷键、身份/资源服务挂载（`YiyiAddonClient.java:16-32`） |
| `config/AddonConfig` | 界面配置读写（旧项目对应 Meteor 全局配置） |
| `config/ModuleStateConfig` | 模块状态持久化（`config/module-state.json`，旧项目无） |
| `utils/FileNames` | 文件名工具 |

### 4.2 事件与模块运行时（10）

| 类 | 作用 |
| --- | --- |
| `core/event/ClientEventType` | 七类事件枚举 |
| `core/event/ClientEvent` | 事件载荷 |
| `core/event/ClientEventBus` | 按所有者去重的订阅表与派发 |
| `core/event/EventSubscription` | 订阅句柄 |
| `core/event/EventDispatcher` | 唯一 Fabric 事件注册点 |
| `core/module/Module` | 模块基类（元数据/生命周期/事件/设置/界面/指令） |
| `core/module/ModuleManager` | 注册、开关、持久化、每刻派发、异常隔离 |
| `core/module/ModuleEventBridge` | 声明式订阅转换 |
| `core/module/ModuleEntries` | 模块→模块中心条目转换 |
| `core/module/ModuleKeybinds` | 模块快捷键存储键 |

### 4.3 模块/分类注册表（5）

| 类 | 作用 |
| --- | --- |
| `module/AddonModules` | 唯一装配入口（分类→事件→探针→模块→指令） |
| `module/ModuleRegistry` | 模块条目注册表与排序 |
| `module/ModuleEntry` | 界面只读条目 |
| `module/CategoryRegistry` | 分类注册表 |
| `module/ModuleCategory` | 分类元数据 |

### 4.4 指令骨架（6）

| 类 | 作用 |
| --- | --- |
| `command/ClientCommand` | 指令基类（名称/别名/用法/执行/补全） |
| `command/CommandContext` | 参数上下文与中文回显出口 |
| `command/CommandRegistry` | 名称/别名索引 | 
| `command/CommandManager` | 聊天拦截、派发、Tab 补全（`CommandManager.java:92` 只拦截 Fabric 聊天发送事件） |
| `command/HelpCommand` | `.help`（`CommandManager.java:89`） |
| `command/ModuleCommand` | `.module list/on/off/toggle/status`（`CommandManager.java:90`） |

### 4.5 平台抽象与存储（7）

| 类 | 作用 |
| --- | --- |
| `platform/GameProbe` | 运行环境探测 |
| `platform/NetworkInfoProbe` | 服务器地址/网络信息探测 |
| `platform/PlayerSampler` | 玩家状态采样 |
| `platform/storage/GamePaths` | 游戏目录路径根 |
| `repository/JsonFileStore` | 原子写 JSON 存储 |
| `core/{BackendLatency,BackgroundTasks}` | 延迟采样与后台线程池 |
| `core/{HttpApi,Json}` | 后端 HTTP 传输与 JSON 读取工具 |

### 4.6 Mixin 桥接（14）

| 类 | 作用 |
| --- | --- |
| `mixin/client/ChatScreenCompletionMixin` | 聊天框 `.` 前缀 Tab 补全 |
| `mixin/client/ConnectionPacketMixin` | 收发包观测入队 |
| `mixin/client/GpuDeviceAccessor` | 取主 RenderTarget 的 GL 名（`NOTICE:136-142`） |
| `mixin/client/GuiRendererMixin` | 世界叠加层注入点（`GuiRendererMixin.java:26`） |
| `mixin/client/RenderTargetMixin` | Skija 帧末绘制注入（`NOTICE:119-126`） |
| `mixin/client/{ClientLevelPredictionAccessor,ModelManagerReloadMixin,ResourcePackPushMixin}` | 预测访问、资源重载、资源包推送回调（职责与旧项目同名 Mixin 对应） |
| `mixin/baritone/BaritoneCommandLongDescMixin` | Baritone 命令长说明汉化 |
| `mixin/baritone/BaritoneCommandNamesMixin` | Baritone 命令名汉化 |
| `mixin/baritone/BaritoneHelpCommandMixin` | Baritone 帮助汉化 |
| `mixin/baritone/BaritoneHelperTranslationMixin` | Baritone 辅助文本汉化 |
| `mixin/baritone/BaritonePaginatorMixin` | Baritone 分页汉化 |
| `mixin/baritone/BaritoneSetCommandMixin` | Baritone set 命令汉化 |

（上述 14 个 Mixin 的「无源码引用」是正常现象：由 `yiyiaddon.mixins.json:6-15` 与 `yiyiaddon.baritone.mixins.json:6-13` 以类名注册。）

### 4.7 UI 外壳 · 基础与渲染（UiText / ui/anim / ui/render / ui/render/world，36）

| 类 | 作用 |
| --- | --- |
| `ui/UiText` | 中英文本包装 |
| `ui/anim/Easing` | 缓动函数 |
| `ui/anim/PressState` | 按压态动画 |
| `ui/anim/ScrollTracker` | 滚动惯性 |
| `ui/anim/Spring` | 弹簧动效 |
| `ui/render/FontRenderer` | 文字绘制 |
| `ui/render/ImeBridge` | 26.1.2 原生输入法桥接（`NOTICE:144-150`） |
| `ui/render/ItemIconCache` | 物品图标缓存 |
| `ui/render/MinecraftText` | 原版文本解析 |
| `ui/render/SkiaBlurRenderer` | 高斯模糊 |
| `ui/render/SkiaGlBackend` | Skija GL 后端 |
| `ui/render/SkiaGlState` | GL 状态管理 |
| `ui/render/SkiaScreen` | Skia 屏幕适配层 |
| `ui/render/world/ShapeMode` | 线框/面/两者 |
| `ui/render/world/Dir` | 面方向位掩码 |
| `ui/render/world/ColorPresets` | 预设色 |
| `ui/render/world/EspColor` | 颜色载体（含彩虹） |
| `ui/render/world/Rainbow` | 彩虹色相 |
| `ui/render/world/RenderCamera` | 单帧相机快照 |
| `ui/render/world/ScreenPoint` | 投影结果 |
| `ui/render/world/WorldProjector` | 世界→屏幕投影 |
| `ui/render/world/EspRenderer` | 世界绘制入口（框/面/线/射线/文字） |
| `ui/render/world/Visibility` | 遮挡判定 |
| `ui/render/world/WorldOverlay` | 层注册与每帧渲染入口 |
| `ui/component/BackButton` | 返回按钮 |
| `ui/component/ButtonRow` | 按钮行 |
| `ui/component/CardLayout` | 卡片栅格 |
| `ui/component/CategoryCard` | 分类卡片 |
| `ui/component/CollapsibleSection` | 折叠区 |
| `ui/component/ColorPreview` | 颜色预览 |
| `ui/component/CompactElement` | 紧凑元素接口 |
| `ui/component/CompactRow` | 紧凑行 |
| `ui/component/CompactStack` | 紧凑堆叠（含拖动锁定） |
| `ui/component/GlassPanel` | 玻璃底板 |
| `ui/component/HueStrip` | 色相条 |
| `ui/component/KeyValueRow` | 键值行 |
| `ui/component/KeybindBadge` | 快捷键徽章 |

### 4.8 UI 外壳 · 组件/控件续（ui/component 剩余 + ui/widget，33）

| 类 | 作用 |
| --- | --- |
| `ui/component/ListRow` | 列表行 |
| `ui/component/ListSection` | 列表分组 |
| `ui/component/ModuleCard` | 模块卡片 |
| `ui/component/ModuleStatusBar` | 模块状态栏 |
| `ui/component/PanelFrame` | 面板外框 |
| `ui/component/SaturationBrightnessPad` | 饱和/明度面板 |
| `ui/component/ScrollViewport` | 滚动视口 |
| `ui/component/SplitPanels` | 双栏 |
| `ui/component/StatusBadge` | 状态徽章 |
| `ui/component/TextLine` | 文本行 |
| `ui/widget/Button` | 按钮 |
| `ui/widget/IconButton` | 图标按钮 |
| `ui/widget/SettingColorPicker` | 取色控件 |
| `ui/widget/SettingColorPreview` | 颜色预览控件 |
| `ui/widget/SettingCycle` | 循环选择控件 |
| `ui/widget/SettingLink` | 链接控件 |
| `ui/widget/SettingModule` | 设置分组行 |
| `ui/widget/SettingNumberBox` | 数值框 |
| `ui/widget/SettingPasswordBox` | 密码框 |
| `ui/widget/SettingSegmented` | 分段控件 |
| `ui/widget/SettingText` | 只读文本控件 |
| `ui/widget/SettingTextBox` | 文本框 |
| `ui/widget/SettingToggle` | 开关 |
| `ui/widget/SettingWidget` | 控件接口 |

### 4.9 UI 外壳 · 页面/屏幕/主题/导航/键位（ui/page / ui/screen / ui/theme / ui/navigation / ui/keybind，30）

| 类 | 作用 |
| --- | --- |
| `ui/page/BasePage` | 页面基类 |
| `ui/page/CardPage` | 卡片页面基类 |
| `ui/page/CompactModulePage` | 紧凑模块页基类 |
| `ui/page/HomePage` | 首页（占位，`HomePage.java:50`「首页内容待接入」） |
| `ui/page/InterfacePage` | 界面设置页（主题/缩放/模糊/滚动） |
| `ui/page/SettingsPage` | 设置页（指令前缀/GUI 键位/Baritone 汉化） |
| `ui/page/ModuleCenterPage` | 模块中心 |
| `ui/page/ModuleListPage` | 模块列表 |
| `ui/page/ModuleDetailPage` | 模块占位页 |
| `ui/page/ModulePage` | 模块页面接口 |
| `ui/page/SearchResultsPage` | 搜索结果页 |
| `ui/navigation/PageRouter` | 页面路由 |
| `ui/keybind/ModuleKeybindManager` | 界面/模块快捷键管理 |
| `ui/keybind/ModuleKeybindStore` | 快捷键存储端口 |
| `ui/screen/ClickGuiScreen` | 主界面骨架 |
| `ui/screen/ColorPickerScreen` | 调色板窗 |
| `ui/screen/ConfirmPanelScreen` | 二次确认窗 |
| `ui/screen/HelpPanelScreen` | 使用说明窗 |
| `ui/screen/ModuleScreen` | 模块独立窗 |
| `ui/screen/PanelScreen` | 独立窗骨架 |
| `ui/screen/SelectorScreen` | 双栏选择器窗（当前零消费方，见第 6 节） |
| `ui/theme/ClickGuiTheme` | 主题接口 |
| `ui/theme/ClickGuiThemePalette` | 9 核心色调色板 |
| `ui/theme/ClickGuiThemeMetrics` | 度量令牌 |
| `ui/theme/ClickGuiThemeColors` | 颜色派生层 |
| `ui/theme/ClickGuiThemeManager` | 主题注册与选择 |
| `ui/theme/AppleDarkTheme` | 默认主题（fallback） |
| `ui/theme/WhiteTheme` | 白色主题 |
| `ui/theme/DarkTheme` | 深色主题 |
| `ui/theme/GrayTheme` | 灰色主题 |

（页面清单来源：`ui/screen/ClickGuiScreen.java:152` `List.of(new HomePage(), new ModuleCenterPage(…), new InterfacePage(), new SettingsPage())`。）

---

## 5. 复刻实现对照表（抽样）

| 新项目类 | 旧项目对应类（含路径） | 职责是否一致 |
| --- | --- | --- |
| `feature/identity/IdIdentifyModule` | `itemid/IdIdentifyModule.java` | 一致（识别入口/元数据/自检；新项目少 `stardew` 语义，见第 6 节 #7 无关项） |
| `feature/identity/IdConfigModule` | `itemid/IdConfigModule.java` | 一致 |
| `feature/identity/command/IdentityCommand` | `itemid/IdCommand.java` | 一致（子命令主名由中文改英文，别名保留——`08-规范:1016` 已记录） |
| `feature/identity/ui/IdAddScreen` | `itemid/IdAddScreen.java` | 一致 |
| `feature/identity/ui/IdResultScreen` | `itemid/IdResultScreen.java` | 一致 |
| `feature/identity/ui/IdEntityResultScreen` | `itemid/EntityResultScreen.java` | 一致 |
| `feature/identity/ui/IdBlockResultScreen` | `worldblock/BlockResultScreen.java` | 一致 |
| `feature/identity/ui/IdManagementScreen` | `itemid/IdManagementScreen.java` | 一致 |
| `feature/identity/ui/IdDataCleanScreen` | `itemid/IdDataCleanScreen.java` | 一致 |
| `platform/identity/ItemIdentifier` | `itemid/ItemIdentifier.java` | 一致 |
| `platform/identity/EntityIdentifier` | `itemid/EntityIdentifier.java` | 一致 |
| `platform/identity/BlockIdentifier` | `worldblock/BlockIdentifier.java` | 一致 |
| `platform/identity/ItemIdentityMatcher` | `itemid/ItemIdentityMatcher.java` | 一致（新项目当前零消费方） |
| `model/identity/ItemIdentity` | `itemid/ItemIdentity.java` | 一致 |
| `model/identity/BlockIdentity` | `worldblock/BlockIdentity.java` | 一致 |
| `platform/world/WorldIdentity` | `autochest/model/WorldIdentity.java` | 一致（共享底座） |
| `repository/identity/*Repository`、`service/identity/IdentityService` | `itemid/*IdManager`、`*SnapshotManager`（ItemIdManager/EntityIdManager/ItemSnapshotManager/BlockSnapshotManager） | 拆分迁移（`17-阶段05-基础设施迁移.md:245`「无对应物…统一门面」），职责覆盖一致 |
| `service/TelemetryService` | `utils/YiyiaddonTelemetryService.java` | 一致 |
| `service/HeartbeatService` | `utils/YiyiaddonHeartbeatService.java` | 一致 |
| `service/ServerPasswordService` | `utils/YiyiaddonPasswordInterceptorService.java` | 一致 |
| `service/CommandActivityService` | `utils/YiyiaddonCommandLogger.java` | 一致 |
| `service/RegisterService`（+`model/{RegisterOutcome,ServerCredential}`、`platform/ClientIdentity`） | `utils/YiyiaddonWelcomeService.java`、`utils/YiyiaddonIdentity.java` | 一致（拆分迁移） |
| `service/StatsService`（+`model/StatsSnapshot`） | `userstats/UserStatsModule.java` | 一致 |
| `service/ChatService`（+`model/ChatMessage`） | `commands/YiyiaddonChatCommand.java`、`commands/ReplyAdminCommand.java` | 一致（跨服聊天 + 回复管理员） |
| `service/RemoteConfigService`（+`model/RemoteFlags`） | `commands/YiyiaddonUpdateCommand.java`、`utils/YiyiaddonWelcomeService.java`（更新检查/开关） | 部分一致 |
| `integration/baritone/{BaritoneChatTranslations,BaritoneCommandTranslations,BaritoneSettingTranslations}` | `translations/{BaritoneChatTranslations,BaritoneCommandTranslations,BaritoneSettingTranslations}.java` | 一致 |
| `integration/baritone/BaritoneTranslationToggle` | `translations/YiyiaddonTranslationModule.java`（汉化开关） | 一致（由模块改为静态开关） |
| `mixin/baritone/*`（6） | `mixin/Baritone*.java`（6，同名） | 一致 |
| `mixin/client/{ClientLevelPredictionAccessor,ModelManagerReloadMixin,ResourcePackPushMixin}` | 旧同名文件 | 一致 |
| `platform/resource/BlockStateModelResolver` | `resourcepack/BlockStateModelResolver.java` | 一致 |
| `service/resourcepack/ResourceExtractionService` | `resourcepack/ServerResourceService.java` | 一致（生命周期状态机） |
| `service/resourcepack/ResourcePackCache` | `resourcepack/ResourcePackDownloader.java` | 部分一致（下载→缓存化重写） |
| `service/container/ContainerService`、`platform/container/ContainerAccess` | `autochest/service/*`、`farm/ContainerBroker.java`（共享底座） | 一致（泛化） |
| `service/network/PacketService`、`platform/network/BlockPacketSender` | `mining/fastbreak/AutoMinerPacketProtocol.java`、`autochest/adapter`（发包） | 一致（泛化） |
| `core/CommandMessageFormatter` | `core/CommandMessageFormatter.java` | 一致 |

（复刻类当前**全部无业务调用方**——处于「已迁移、未接线」状态，证据见第 6 节 #4、#5。）

---

## 6. 存疑项

| # | 项 | 事实（证据） | 存疑点 / 建议 |
| --- | --- | --- | --- |
| 1 | 3 个未注册主题 | `ClickGuiThemeManager.java:12,17-19` 仅注册 4 套；`VapeTheme`/`LightBlueTheme`/`DefaultClickGuiTheme` 零引用（第 2 节） | 若按「UI 外壳的一部分」口径则属框架；按「框架必需」口径则属多加的。本审计按后者计入多加的；处置建议见第 2 节 |
| 2 | `ui/screen/SelectorScreen`、`ui/component/SplitPanels` 零消费方 | 检索：`SelectorScreen` 仅自身定义、`SplitPanels` 仅被 `SelectorScreen.java:6,71` 引用；无外部构造点 | 属框架类（为 ID「选择目标物品」窗预留），但违反「无无调用方的类」口径。`21-追加-世界渲染基建与独立窗口体系.md:93,106` 已自述「零消费方，未验证…需随问题 1 接线」。建议随「ID 三件套收口」接线，勿删 |
| 3 | `ui/widget/SettingColorPicker`、`ui/widget/SettingPasswordBox` 零消费方 | 全项目检索：仅自身文件命中（`SettingColorPicker.java` 另引用 `ColorPickerScreen.java:97` 作为其下游；但自身无调用方） | 框架控件，待业务模块接线。建议保留（属 UI 外壳组件集） |
| 4 | 后端账号/统计/聊天子系统（8 服务 + `ReportPayload`）零消费方 | 检索 `RegisterService/HeartbeatService/TelemetryService/CommandActivityService/ServerPasswordService/RemoteConfigService/StatsService/ChatService/ReportPayload`：仅出现于自身文件与彼此互调（如 `RegisterService.java:30`→`RemoteConfigService`、`ChatService.java:42`→`RemoteConfigService`、`HeartbeatService.java:49`→`RemoteConfigService`）；`YiyiAddonClient.java:19-32` 未挂载 | 属复刻（旧 `utils/*`、`commands/*`、`userstats/*` 有对应），但整条后端链**当前完全未接线**。`19-阶段06-模块运行时与指令系统.md:46` 亦记载「全部无调用方」。建议：接线前不得声称可用；若长期不接线，评估是否移出主源码树 |
| 5 | 容器/发包底座零消费方 | 检索 `ContainerService/PacketService/ContainerAccess/BlockPacketSender`：仅彼此互调（`PacketService.java:30-45`→`BlockPacketSender`、`ContainerService.java:47-150`→`ContainerAccess`），无外部调用 | 同上（复刻的共享底座，待自动箱子/农场等模块迁移后接线） |
| 6 | `platform/identity/ItemIdentityMatcher` 零消费方 | 全项目检索仅自身文件命中 | 复刻类（旧 `itemid/ItemIdentityMatcher.java` 有对应），待接线；非多加的 |
| 7 | 资源「内容指纹 / 分析缓存」是否属新能力 | 类：`platform/resource/ResourceFingerprint`、`service/resourcepack/ResourceIndexProbe`、`service/resourcepack/ResourceContentProbe`、`repository/resource/ResourceAnalysisCache`；`08-规范-用户交互资产迁移.md:1563` 原话：`资源解析 / 资源状态 / 缓存统计卡片 … 旧项目无内容指纹与分析缓存` | 旧项目确有星露谷专用指纹/索引（`stardew/profile/StardewResourceFingerprint.java`、`StardewResourceIndex.java`、`profile/StardewResourceScanner.java`），但无通用「内容指纹 + 分析缓存」。本审计按「资源链路的泛化复刻」归入复刻；若按字面口径则「分析缓存」属新增，需产品确认 |
| 8 | `NOTICE` 已过期 | `NOTICE:110-117` 称页面为 `TestPage/InterfacePage/SettingsPage`，`:53-62` 称含 `SettingSlider/SettingButton`，`:169-171` 称主题未注册 | 与现状不符：源码无 `TestPage`（现为 `HomePage`）、无 `SettingSlider/SettingButton`（`21-追加:62`「移除控件」），主题仍未注册（此条相符）。建议同步 NOTICE |
| 9 | UI 外壳非「自研」，为派生自第三方 PVPUtils | `NOTICE:31-92`（2.1 明确「All PVPUtils-derived code … under the Java package `com.yiyiaddon.ui`」，逐文件对照）、`:94-171`（改写披露） | 与背景口径「新项目自研框架」存在张力：`ui/` 全线（含 `UiText`、`ui/theme/*`、`ui/widget/*`、`SkijaGlBackend`、`FontRenderer`、`BasePage`、`SearchResultsPage`、`ClickGuiScreen`、`ModuleKeybindManager`）为 PVPUtils 派生。**不属「多加的」**（属 UI 外壳），但「自研」表述应修正为「派生并改写」 |
| 10 | 分类 6 个含 `导航`/`附魔`（旧项目为 5 类且含 `绕过`） | `module/AddonModules.java:74-79` 注册 6 类；`08-规范:1042,1101,1629` 与 `开发习惯.md:122` 定稿：分类不属迁移范围 | 已由用户定稿为「本项目 UI 分组」，不构成多加的；此处仅登记 |

---

## 附：本次审计用到的检索/命令（可复现）

1. 计数：`Get-ChildItem -Recurse -Filter *.java | Measure-Object` → 新项目 221、旧项目 404。
2. 零引用类扫描（自写 PowerShell：对每个 `*.java` 取 `BaseName`，在其余全部文件内容中做 `\b名称\b` 匹配，命中数为 0 即输出）：得到 26 项，其中 14 个 Mixin 由 `*.mixins.json` 注册属正常，其余见第 6 节 #2~#6。
3. 主题/页面/服务引用检索：`Grep`（`VapeTheme|LightBlueTheme|DefaultClickGuiTheme`、`SelectorScreen|SplitPanels|…`、`RegisterService|TelemetryService|…`）。
4. 声明与口径检索：`Grep`（`脚手架|示例|可删除|占位`）与文档 `Grep`（`越权|旧项目无|不属迁移`）。
