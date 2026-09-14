# 31 · 阶段09 · stardew（星露谷农场）移植施工图

> 本文件为**开工前侦察产物**，只读侦察得出，不含任何代码改动。
> 旧项目事实取自 `D:\mcaddon\26.1.2`，新项目事实取自 `D:\mcaddon\yiyiaddon`，每条结论附「文件:行号」。
> 口径以 `01-总纲-迁移口径与复刻原则.md` 为唯一权威：功能、模块、互相关联全部复刻，一样不少；唯一变化是 UI 外壳。

---

## 0. 口径声明

- 本文只描述**怎么做**（落点、顺序、依赖），不新增任何旧项目没有的东西。
- 所有迁移对象均来自旧项目 `com.example.addon.stardew`，共 **67 个 .java 文件**（`Grep "^package "` 命中 67 行）。
- 文字与交互逐字照旧项目（按钮文字、播报、指令、自检、说明、数据结构）；壳换为本项目自研 Skija + 主题。
- 本文列出的一切「缺口」均为**待用户拍板项**，未获批准前不得动工。

---

## 1. 【阻塞】新项目当前工作树编译不过（22 个错误）

`.\gradlew.bat compileJava --offline` 实证输出 22 个 `找不到符号`。**HEAD 本身可编译**（`30-移植总览` 记录的「编译通过」对 HEAD 成立）；坏的是**当前工作树**——存在 25 个未提交改动，属「删类 / 删方法未清引用」的半成品重构。

| # | 缺失符号 | 引用点 | 成因（git 证据） |
|---|---|---|---|
| 1 | 类 `IdentityModule` | `module/AddonModules.java:9,56`；`feature/identity/ui/IdentityModulePage.java:4,42,44` | `6d9f48a` 已拆成 `IdIdentifyModule` / `IdConfigModule`，引用未清 |
| 2 | 类 `ResourceCommand` | `module/AddonModules.java:5,67` | `a6f486a`（删除越权指令 `.resource`）已删类，引用未清 |
| 3 | 类 `SettingSlider` | `ui/component/CompactRow.java:5,106,114` | 同批次删除，引用未清 |
| 4 | 类 `SettingButton` | `feature/identity/ui/IdentityModulePage.java:17,100,102` | 无此文件 |
| 5 | 方法 `SettingModule.releaseDrag()` | `ui/page/BasePage.java:110` | 控件已移除拖拽能力 |
| 6 | 方法 `CompactStack.enterAnimation(boolean)` | `ui/screen/PanelScreen.java:78` | 方法不存在 |
| 7 | 方法 `SettingTextBox.width(float)` | `feature/identity/ui/IdAddScreen.java:81` | 方法不存在 |
| 8 | 方法 `IdentityActions.itemCount/entityCount/blockCount/itemSnapshotCount/blockSnapshotCount/selectedTargetCount()` | `feature/identity/ui/IdentityModulePage.java:84-96` | 孤儿方法（与 `26-审计` 同类问题） |

**处置**：待用户批准「最小清除」修复清单后执行；本文件不预判修法。

---

## 2. 目标形态

新包 `com.yiyiaddon.feature.stardew`，子包**照旧 1:1**（用户已拍板）：

```
adapter/  command/  logistics/  memory/  navigation/  plan/  point/  profile/
recognition/  render/  scan/  season/  selector/  service/  status/  task/  ui/
+ 根 3 类：StardewFarmModule(2542 行) · StardewContext(57) · StardewFarmModule 内嵌类型
```

子包文件数（旧项目实测）：`profile` 18 · `selector` 12 · `season` 4 · `service` 4 · `recognition` 4 · `point` 3 · `ui` 3 · `adapter` 2 · `memory` 2 · `plan` 2 · `status` 2 · `task` 2 · 根 3 · `command`/`logistics`/`navigation`/`render`/`scan` 各 1 = **67**。

---

## 3. 文件分级（决定改造成本）

### 3.1 A 级 · 零改动（19 个）

只改 `package` 行。**11 个文件连 import 都没有**：

`plan/StardewAmountMode` · `point/StardewPointType` · `profile/RuleEvidence` · `profile/StardewCropLifecycle` · `profile/StardewHarvestAction` · `profile/StardewHarvestRule` · `recognition/CropState` · `recognition/PotState` · `service/StardewItemRole` · `status/StardewStatusSnapshot` · `task/TaskType`

**8 个仅 `java.*` 或自包 import**：`service/StardewWaterBarProbe` · `ui/StardewConsoleData` · `profile/StardewDocumentedRules` · `profile/CropDefinition` · `profile/SimpleToolDefinition` · `profile/SprinklerDefinition` · `profile/WateringCanDefinition` · `profile/PotDefinition`

### 3.2 B 级 · 换包名 + 换日志门面（12 个）

含 `StardewPreview`（资源包贴图加载器）、`StardewContext`、`memory/FarmMemoryStore`、各 `*Store`。
`StardewPreview` 全文件可搬，仅需替换日志门面：旧 `AddonTemplate.LOG.warn`（`StardewPreview.java:73,91`）。

### 3.3 C 级 · 需框架适配（36 个）

旧包携带 **Meteor 残留 107 行 import / 16 文件**（`Grep "^import meteordevelopment\." count = 107`）。逐文件清单见 `06-基准-05` 附01；本文件只给映射口径。

---

## 4. 框架映射口径（C 级）

| 旧（Meteor / 旧框架） | 新（自研） | 证据 |
|---|---|---|
| `orbit.@EventHandler`、`TickEvent`/`GameJoinedEvent`/`GameLeftEvent`/`Render3DEvent`/`Render2DEvent`/`OpenScreenEvent`/`PacketEvent` | `core/event` → `ClientEventBus` + `ClientEventType` + `EventSubscription`；模块侧 `Module.subscribedEvents()` / `onEvent` | `AutoChestModule`、`IdIdentifyModule:114-123` |
| `settings.BoolSetting/IntSetting/EnumSetting/ColorSetting/StringListSetting/SettingGroup/IVisible` | 新项目**无 Setting 体系**：设置即普通配置类 + `save(JsonObject)`/`load(JsonObject)` | `feature/autochest/config/AutoChestSettings.java:181-242` |
| `gui.WindowScreen`（`StardewConsoleScreen`、`StardewTargetSelectScreen`、`StardewRenderObjectScreen`） | `ui/screen/PanelScreen`（独立窗口统一骨架）；模块页走 `ui/page/ModulePage` + `ui/screen/ModuleScreen` | `PanelScreen.java:51,90`；`feature/identity/ui/IdScreens.java:12-15` |
| 旧 GUI 控件：`WTable`/`WButton`/`WLabel`/`WCheckbox`/`WDropdown`/`WIntEdit`/`WPlus`/`WMinus`/`WTextBox`/`WQuad`/`WSection`/`WTriangle` | `ui/component/*` + `ui/widget/*`：`Button`·`SettingToggle`·`SettingSegmented`·`SettingCycle`·`SettingNumberBox`·`SettingTextBox`·`SettingColorPicker`·`ListSection`·`ListRow`·`KeyValueRow`·`ScrollViewport`·`SplitPanels`·`CompactRow`/`CompactStack` | 铁律：**禁用滑块**，数值一律 `SettingNumberBox`（`SettingNumberBox.java:17`） |
| `renderer.ShapeMode` | `ui/render/world/ShapeMode`（`Lines`/`Sides`/`Both` 完全同值，且已带 `labels() = {"线框","面","两者"}`） | `ShapeMode.java:11-28` |
| `utils.render.color.SettingColor` / `utils.render.color.RainbowColors` / `utils.render.color.Color` | `ui/render/world/EspColor`（含 `preset/applyHsv/load/save` 持久化）/ `Rainbow` / `EspRenderer` 的 `EspColor` 重载 | `EspColor.java:37-147`；`Rainbow.java:27-39` |
| `WWidget` 世界渲染（`render/SprinklerEspRenderer`、`autofarm/render/FarmRenderer`） | `WorldOverlay.register(String ownerId, Layer)` + `EspRenderer`（`box/box2D/blockBox/line/tracer/text/quad`） | `WorldOverlay.java:55`；`EspRenderer.java:183-460`；样例 `feature/visuals/EspTestModule.java:106-159` |
| `meteorclient.commands.Command` + `Commands.add(new StardewCommand())` | `command/ClientCommand` + `Module.commands()` 或 `CommandRegistry.register` | `ClientCommand.java:17-60`；`CommandManager.java:89-90` |
| `Modules.get().get(X.class)` | `ModuleRegistry` / 模块实例持有 | `module/ModuleRegistry.java:26` |
| `YiyiaddonModule` / `AddonTemplate` / `WorldContextFormatter` / `AddonTemplate.LOG` | `core/module/Module` · `ModuleManager` · `platform/world/WorldIdentity` · 新日志门面 | — |
| 旧 `HelpScreen`(104) / `ConfirmScreen`(48) | `ui/screen/HelpPanelScreen`（`buildHelpContent(HelpSection...)`）/ `ui/screen/ConfirmPanelScreen` | `HelpPanelScreen.java:50,75,98`；`ConfirmPanelScreen.java:28-36` |

**旧代码中不可渲染字符的处理必须保留**：`StardewStatusReporter.maskUnrenderable`（`:184-199`，折叠为 `[图标]`）与 `isRenderable`（`:208-216`）。

---

## 5. 出界依赖 17 类 → 新项目现状

| 旧外部类（行数） | 新项目现状 | 缺口 / 动作 |
|---|---|---|
| `core/CommandMessageFormatter`(263) | ✔ `core/CommandMessageFormatter.java` | 无 |
| `itemid/ItemIdentity`(529) | ✔ `model/identity/ItemIdentity.java`(372) | 需 API 对齐 |
| `itemid/ItemIdentifier`(359) | ✔ `platform/identity/ItemIdentifier.java`(370) | 需 API 对齐 |
| `itemid/ItemIdManager`(279) | ≈ `service/identity/IdentityService` + `repository/identity/*` | **API 对齐工作量大** |
| `resourcepack/BlockStateModelResolver`(368) | ✔ `platform/resource/BlockStateModelResolver.java`(226) | 需 API 对齐 |
| `mixin/ResourcePackPushMixin`(85) | ✔ `mixin/client/ResourcePackPushMixin.java`(61) | 无（已注册） |
| **`resourcepack/ServerResourceService`(1173)** | ≈ `service/resourcepack/ResourceExtractionService`(792) + `ResourcePackCache`(611) | **最大口子**：只读 getter、`requestExtract()`、`addReadyListener/addInvalidateListener` 已在，需逐 API 比对 |
| `resourcepack/ResourcePackDownloader`(603) | ≈ `ResourcePackCache`（`cachedZip:149`、`currentServerKey:89`） | 需 API 比对 |
| `autofarm/navigation/FarmNav`(168) | ✗ 不存在 | **已定：新建 `feature/stardew/navigation/FarmNav`**（逐字复刻，仅改包名与 import） |
| `farm/FarmPacketOps`(185) | ≈ `service/network/PacketService`（`breakBlock:29`、`useOnBlock:34`、`interactBlock:39`、`tillBlock:44`、`remainingDurability:53`） | **缺 2 项**：`ServerboundMovePlayerPacket.Rot`（同步朝向，旧 `DefaultStardewAdapter.java:68`）与 `ServerboundSetCarriedItemPacket`（切换手持同步，旧 `:112`） |
| `farm/ContainerBroker`(235) | ≈ `service/container/ContainerService` + `platform/container/ContainerAccess` + `model/container/ContainerTransferResult` | 需 API 对齐 |
| `autofarm/render/FarmRenderer`(67) | ≈ `WorldOverlay` + `EspRenderer` | 需重写为 `WorldOverlay.Layer` |
| `autochest/ui/ConfirmScreen`(48) | ✔ `ui/screen/ConfirmPanelScreen` | 文案逐字对齐 |
| `ui/HelpScreen`(104) | ✔ `ui/screen/HelpPanelScreen` | 17 节说明逐字搬 |
| `utils/SystemFileOpener`(92) | ✗ 不存在 | 缺口（打开资源包文件/目录） |
| `autofarm/controller/FarmController`(539) | — | **stardew 零引用，不属本批** |

依赖热度：`ServerResourceService` 被 10 处 import + `StardewCoordinator.java:346` 全限定调用；`ItemIdentity` 被 5 文件、`ItemIdManager` 被 5 文件引用。

---

## 6. 「服务器资源」面板口径（已查实）

旧项目就该面板是 **`selector/StardewResourceSetting`**（配置页内嵌设置卡，非指令、非独立窗口）：

- 字段 6 项（逐字）：`服务器名称`、`服务器地址`、`资源状态`、`资源来源`、`资源缓存`、`资源指纹`（`StardewResourceSetting.java:66-68`）
- 按钮 4 个（`§` 逐字，含动态文字）：
  - `:310-319` → `§8检测 / 提取当前服务器资源包` / `§7执行中… §f...` / `§a§l重新检测 / 更新资源` / `§e§l检测 / 提取当前服务器资源包`
  - `:191` → `§b打开控制台`
  - `:330` → `§b§l查看资源包`（无缓存时 `§8查看资源包`）
  - `:208` → `§e查看使用说明`
- 说明窗入口：`StardewResourceSetting.java:210` → `HelpScreen(theme, setting.module, setting.module.helpContent())`
- 动作直连：`ServerResourceService.requestExtract()`（旧 `:291,297~351`）

**新项目现状**：`service/resourcepack/*` 有完整链路，但**没有任何资源包 GUI**（全仓库 `ui` 下检索无引用；`ResourceExtractionService` 仅被 `YiyiAddonClient:31`、两个 Mixin、`AddonModules:33` 引用）。因此该面板需按 `PanelScreen` / 配置页新做，**文字逐字照旧**。

---

## 7. UI 重写清单（批 5）

| 旧类（行数） | 旧标题（逐字） | 新壳落点 |
|---|---|---|
| `ui/StardewConsoleScreen`(273) | `星露谷农场控制台`（`:58`） | `PanelScreen`；保留 6 页签 `概览/种植/运行/后勤/点位/日志`（`:33-51`） |
| `selector/StardewTargetSelectScreen`(369) | `选择<类别名>`（`:81`） | `PanelScreen`；保留左 `§a§l▌ 可添加…` / 右 `§b§l▌ 已选择`；可参考现成 `ui/screen/SelectorScreen` |
| `selector/StardewRenderObjectScreen`(103) | `渲染设置 · <对象名>`（`:37`） | `PanelScreen`；`§7显示 §8▶` / `§7颜色 §8▶` / `§7彩虹 §8▶` / `§7渲染模式 §8▶` |
| `ui/StardewSettingsRenderer`(40) | —（全量渲染契约，绕开 visible 过滤） | 对应新项目「设置卡全量渲染」写法 |
| `selector/StardewResourceSetting` | 见第 6 节 | 配置页内嵌 |
| `selector/StardewTargetSetting` | 按钮 `点击选择`（`:160`） | `SelectorScreen` 接线 |
| `selector/StardewCropLogisticsSetting` | `种子少于`/`种子补到`/`成品攒到`/`成品留底`（`:54-57`） | `SettingNumberBox` + `SettingSegmented` |
| `selector/StardewRenderSetting` | `WCheckbox` + 按钮 `设置`（`:66`） | `SettingToggle` + `Button` |
| `render/SprinklerEspRenderer`、`render/EspShapeMode` | — | `WorldOverlay` + `ui/render/world/ShapeMode` |

**可逐字照搬的 UI 逻辑**（与壳无关）：`SeasonColor`/`taskColor`（`StardewConsoleScreen.java:117-131`）、`Tab` 枚举与其标题、`StardewConsoleData`（整文件）、`StardewStatusSnapshot`（整文件）、`StardewPreview`（整文件）、控制台各页说明文案常量。`StardewTargetSelectScreen` 的列宽算法（`:135-175`）依赖旧字体度量 API，需换成 Skija 度量后保留算法。

---

## 8. 落盘顺序（6 批，逐批编译 + 逐批验收）

| 批 | 内容 | 文件数 | 前置 |
|---|---|---|---|
| 批1 | 纯数据/枚举/record（第 3.1 节 19 文件） | 19 | 无 |
| 批2 | 数据层：`profile`(18) · `recognition`(4) · `scan`(1) · `memory`(2) · `logistics`(1) · `plan`(1) · `point`(3) · `season`(4) · `service`(4) | 38 | 批1 + ID 三件套 + 资源包链路 API 对齐 |
| 批3 | `navigation/FarmNav`(新) · `adapter`(2) | 3 | 批1、批2；含待批准的 2 个发包缺口 |
| 批4 | `task/StardewCoordinator` · `status/StardewStatusReporter` | 2 | 批3 |
| 批5 | UI 全套（第 7 节） | — | 批4 |
| 批6 | `StardewContext` · `StardewFarmModule` · `command/StardewCommand` · 注册 | 3 | 批5 |

**批6 注册点**（新项目唯一引导入口）：`module/AddonModules.java` — `createModules()`（`:54-58`）补一行；`CategoryRegistry` 的 `stardew` 分类**已存在**（`AddonModules.java:76`：id `stardew`、名 `星露谷`、order 60）。

---

## 9. 持久化清单（8 个落盘类）

旧项目根目录统一 `<gameDirectory>/StardewFarm/`：

| 类 | 子目录 | 文件规则 | 引用情况 |
|---|---|---|---|
| `profile/StardewProfileStore`(125) | `profiles` | `<safe(serverKey)>.json` | **死代码：全项目从未实例化** |
| `selector/StardewSelectionStore`(120) | `selection` | `<safe(serverKey)>.json` | `StardewTargetSetting:65,71` |
| `memory/FarmMemoryStore`(117) | `memory` | `<safe(serverKey+"_"+dimension)>.json` | `StardewFarmModule:113`、`StardewCoordinator:129,334` |
| `point/StardewPointManager` | `points` | `<safe(serverKey)>.json` | `StardewFarmModule:112` |
| `logistics/StardewLogisticsStore` | `logistics` | `<safe(serverKey)>.json` | `StardewFarmModule:454,676` |
| `plan/StardewCropPlanStore` | `crop-plans` | `<safe(serverKey)>.json` | `StardewFarmModule:454,526,677` |
| `profile/StardewHarvestRuleStore` | `harvest-rules` | `<safe(serverKey)>/<safe(fingerprint)>.json` | `StardewFarmModule:111,1308,1353` |
| `season/StardewSeasonManualBinding` | — | `season-bindings.json` | `StardewFarmModule:2190,2233` |

写盘共性：临时文件 + `ATOMIC_MOVE` 替换，失败保留旧档。

**serverKey 两套口径（旧项目事实，须照旧）**：点位/记忆/季节用 `StardewContext.serverKey()`；选择器/后勤/计划/收获规则用 `ServerResourceService.serverKey()`。两者底层均为 `host:port`。新项目对应 `WorldIdentity.server()`（`:36-45`）与 `ResourceExtractionService.serverKey()`（`:158`）。

---

## 10. 自检与播报口径（批4/批6）

- 旧自检：`StardewFarmModule.collectStartupProblems()`（`:592-634`），逐条缺项文案逐字（如 `"当前环境不是多人服务器"`、`"未选择目标作物"`、`"背包缺少" + crop.seedDisplayName()`）。
- 旧报告器：`status/StardewStatusReporter`(583) —— `startupCheckFailed`（`:94-110`，标题 `§c§l启动自检未通过`、汇总行 `§e§lN 项`、状态 `禁止启动`）、`startupCheckPassed`（`:113-124`，`§a§l启动自检通过`）；字段标签 17 类（`appendSelfCheckFields:482-545`）；颜色规则 `selfCheckValue:548-562`（`背包种子`→`§6§l`，含 `未绑定/未选择/未匹配` 等→`§c§l`，否则 `§f`）。
- 旧失败动作：`StardewFarmModule.java:577-583` — 置 `startupStopPending` → `mc.execute(() -> { if (isActive()) toggle(); })` → `statusReporter.startupCheckFailed(missing)`。
- 新项目机制：`Module.selfCheck()` 返回缺项列表（`Module.java:119-121`），由 `ModuleManager` 做 `BLOCKED → PENDING → 自动开启`（`:194-239,139-161`），播报 `§6§l还差 N 项没配好，配完再开：`（`:222-225`）。
- **两套语义不同（旧：自检失败即关闭并报状态卡；新：自动等待并在就绪后自动开启）——按哪套写，属待拍板项。**

---

## 11. 待用户拍板项（未批准前不动工）

1. **编译阻塞**（第 1 节，22 个错误）——用户已授权出「修复清单」，待逐条确认后执行。
2. `FarmPacketOps` 缺的 2 个发包方法（朝向同步、手持同步）——属新增能力。
3. `SystemFileOpener` 缺失——属新增能力。
4. **自检体例**：按旧 stardew（失败即关闭 + 状态卡）还是按新项目运行时（BLOCKED→PENDING 自动开启）。
5. `StardewProfileStore` 在旧项目是**从未实例化的死代码**——照搬保留死类，还是剔除。
6. 「服务器资源」面板新壳的落点形态（配置页内嵌设置卡 vs 独立 `PanelScreen`）——旧项目是配置页内嵌。
7. 本文件是否需要在 `00-AI阅读指引.md` 的索引中登记（涉及改动另一份文档）。

---

## 12. 验收方式

每批落盘后：`.\gradlew.bat compileJava --offline` 通过 → 报用户验收 → 进入下一批。全部完成后写阶段报告（32- 编号）并更新 `30-移植总览`。
