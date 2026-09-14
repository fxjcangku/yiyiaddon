# 审计 A · 用户可见中文文本出处核查

> 审计对象：`d:\mcaddon\yiyiaddon\src\main\java\com\yiyiaddon\`（221 个 `.java`）
> 旧项目参照（只读）：`d:\mcaddon\26.1.2\src\main\java\com\example\addon\`（404 个 `.java`）
> 本次为只读审计：未修改任何源码，未创建除本文件外的任何文件。

## 0. 口径与检索方式

**文件总数**

| 项 | 数值 | 取得方式 |
| --- | --- | --- |
| 新项目 java 文件 | 221 | `Glob('src/main/java/com/yiyiaddon/**/*.java')` + 枚举计数（Glob 单次上限 200，故用递归枚举补齐） |
| 旧项目 java 文件 | 404 | 同上（`com/example/addon/**`） |
| 含中文字面量的新项目文件 | 94 | 下述正则命中文件数 |

**检索正则（两步）**

1. 抓「字符串字面量里的中文」：
   `"[^"]*\p{Han}[^"]*"`
   → 精确单一引号内、含汉字的字面量；Grep 工具命中 **2185 处 / 94 文件**。
2. 排除规则后再统计：整行以 `//` 或 `*` 开头（行注释 / javadoc 续行）一律跳过，其余行内字面量全量采集
   → **中文字面量总数 2167 条**（2185 与 2167 的差额 18 即被注释掉的整行）。

**旧项目语料与比对算法**

- 旧项目 404 个 java 文件全文（`ReadAllText`，UTF-8）拼接成 1 个语料串，长度 **2,127,515** 字符。
- 归一化：先按 `§.`（`§` + 任意 1 字符）删除全部颜色码，再删除全部空白（含全角空格），得 `OLD`。
- 逐条判定（结果由 PowerShell 在终端计算，`String.IndexOf` 判定，`IndexOf < 0` 记为未命中）：
  - **A** 字面量归一化后整串命中 `OLD` → 有出处（口径 1）；
  - **B** 未命中 → 再按 `[\p{Han}]{2,}` 逐个「中文片段」比对：
    - 全部片段命中 → 记 **装饰/拼接差异**（用词一致，仅多了 `§c§l✗ `、`§a§l✓ ` 之类装饰或改成分号拼接）；
    - 存在片段未命中 → 记 **无出处候选**。
- 无出处候选 **244 条**，按域切分：业务（`feature/identity` + `RegisterService`/`TelemetryService`）**59**、脚手架（`feature/visuals`）**14**、日志（行内含 `LOGGER`/`log.`）**19**、框架层（其余自研框架路径）**152**。
- 证据复核：对重点条目另用 `Grep` 在旧项目实搜整串，**「No matches found」即「旧项目 grep 结果为空」**（下文逐条给出）。

**判定口径**（严格照任务书）：① 旧项目同串 → 合规；② 本项目自研框架层必需文本（不冒充业务文案）→ 合规；③ 注释 / `LOGGER` 日志 / 开发期脚手架 → 合规；其余 → 无出处。

## 1. 结论摘要

- 中文字面量总数 **2167**
- **合规 2132 条**
  - ① 旧项目同串：**1923** 条（2167 − 244）
  - ② 框架层（口径 2）：**152** 条（含 6 条分类说明，见 §2 附）
  - ③ 日志（口径 3）：**19** 条；脚手架（口径 3）：**14** 条
  - ④ 业务文案「装饰/拼接差异」（用词与旧项目一致，仅加 `§c§l✗ ` / `§a§l✓ ` 或改分隔符）：**24** 条
- **无出处（疑似自造）20 条**
- **存疑 15 条**

**无出处清单一句话总览（按文件分组）**

| 文件 | 条数 | 内容性质 |
| --- | --- | --- |
| `feature/identity/ui/IdentityModulePage.java` | 10 | 模块页设置项名 / 设置行描述 / 折叠区标题（10 条全部为自造） |
| `feature/identity/IdConfigModule.java` | 2 | 播报字段标签 `已选目标`、状态文本 `统计完成` |
| `feature/identity/IdIdentifyModule.java` | 2 | 同上（与 `IdConfigModule` 重复实现） |
| `feature/identity/command/IdentityCommand.java` | 2 | 指令回执 `未知子命令：`、`未知识别模式：` |
| `feature/identity/ui/IdConfigPage.java` | 2 | 设置行描述 `输入后即时过滤当前 ID 清单`、`身份数据在磁盘上的位置` |
| `feature/identity/config/IdentityModuleConfig.java` | 1 | 设置项名 `详细输出` |
| `feature/identity/service/IdentityActions.java` | 1 | 统计文案分隔符 ` ｜ 已选目标 `（旧用 `§8▸`） |

> 一句话：无出处全部集中在 **ID（identity）模块的「模块页标签/描述 + 播报字段 + 指令回执」**，与开发报告 `08-规范-用户交互资产迁移.md:1480`（用户点名「ID 模块根本不是以前那一套」）指向同一处。

## 2. 无出处清单（需处理）

| # | 文件:行 | 中文文本（逐字，含 §） | 出现场景 | 旧项目是否有近似文本（旧原文与位置） | 建议处置 |
| --- | --- | --- | --- | --- | --- |
| 1 | `feature/identity/IdIdentifyModule.java:268` | `已选目标` | 播报卡片字段标签（`reportStats`） | 无。旧项目同类信息用 `§8▸ §7快照 §f` 字段流拼装（`itemid/IdConfigModule.java:221-224`） | 改回旧原文（改用 `§8▸ ` 字段流） |
| 2 | `feature/identity/IdIdentifyModule.java:270` | `统计完成` | 播报状态级文本（`Level.INFO`） | 无（旧项目该统计只改界面标签，不发状态句；`itemid/IdConfigModule.java:219-225` 仅 `summaryText()`） | 删（或改回旧原文） |
| 3 | `feature/identity/IdConfigModule.java:399` | `已选目标` | 同上（重复实现） | 同上 | 同 #1 |
| 4 | `feature/identity/IdConfigModule.java:401` | `统计完成` | 同上 | 同上 | 同 #2 |
| 5 | `feature/identity/service/IdentityActions.java:175` | `" ｜ 已选目标 "` | 一行式统计文案分隔 | 旧分隔符为 ` §8▸ `（`itemid/IdConfigModule.java:221-224`） | 改回旧原文（`§8▸`） |
| 6 | `feature/identity/command/IdentityCommand.java:71` | `未知子命令：` | 指令回执 | 无（旧 `.id` 仅有 `物品/实体/方块` 三分支，无子命令分派；`itemid/IdCommand.java:83-193`） | 删 / 改回旧原文 |
| 7 | `feature/identity/command/IdentityCommand.java:113` | `未知识别模式：` | 指令回执 | 无（旧项目只读取模块当前模式，无非法模式回执；`itemid/IdCommand.java:110`） | 删 / 改回旧原文 |
| 8 | `feature/identity/ui/IdentityModulePage.java:74` | `点击执行对应识别动作` | 模块页设置行描述 | 无 | 改回旧原文 / 删 |
| 9 | `feature/identity/ui/IdentityModulePage.java:76` | `开启后额外输出全部识别字段` | 模块页设置行描述 | 无 | 改回旧原文 / 删 |
| 10 | `feature/identity/ui/IdentityModulePage.java:76` | `详细输出` | 设置项名（显示） | 旧项目对应项为 `方块语义调试`，语义不同（`08-规范…迁移.md:1575` 已记为待单独迁移） | 待单独迁移（不得自造） |
| 11 | `feature/identity/config/IdentityModuleConfig.java:20` | `详细输出` | 设置项名（持久化键对应名） | 同上 | 待单独迁移 |
| 12 | `feature/identity/ui/IdentityModulePage.java:80` | `最近结果` | 分区分组名 | 旧项目仅有 `最近一轮`（`enchant/report/GearCraftReport.java:210`，语境不同） | 改回旧原文 / 删 |
| 13 | `feature/identity/ui/IdentityModulePage.java:81` | `数据统计` | 分区分组名 | 旧项目为注释 `汇总当前内存索引数量`（`itemid/IdConfigModule.java:219`） | 改回旧原文 / 删 |
| 14 | `feature/identity/ui/IdentityModulePage.java:87` | `快照与识别目标` | 折叠区标题 | 无 | 改回旧原文 / 删 |
| 15 | `feature/identity/ui/IdentityModulePage.java:89` | `已保存的物品状态快照数量` | 设置行描述 | 无（旧 `ItemSnapshotManager.java:37` 为注释 `已保存快照的状态指纹集合`） | 改回旧原文 / 删 |
| 16 | `feature/identity/ui/IdentityModulePage.java:91` | `已保存的方块状态快照数量` | 设置行描述 | 无（旧 `worldblock/BlockSnapshotManager.java:41` 为注释 `已保存快照的状态指纹集合`） | 改回旧原文 / 删 |
| 17 | `feature/identity/ui/IdentityModulePage.java:93` | `已选识别目标` | 设置项名 | 无 | 改回旧原文 / 删 |
| 18 | `feature/identity/ui/IdentityModulePage.java:93` | `识别目标配置中仍有效的选中项` | 设置行描述 | 无 | 改回旧原文 / 删 |
| 19 | `feature/identity/ui/IdConfigPage.java:135` | `输入后即时过滤当前 ID 清单` | 设置行描述 | 无。该模块旧界面文案未迁移，`08-规范…迁移.md:1576` 已记「文案随模块一并迁移」 | 随模块迁移时改回旧原文 |
| 20 | `feature/identity/ui/IdConfigPage.java:157` | `身份数据在磁盘上的位置` | 设置行描述 | 无（旧 `SystemFileOpener`/`GamePaths` 目录说明在注释中） | 同 #19 |

**「旧项目 grep 结果为空」证据**（Grep 工具，路径 `d:\mcaddon\26.1.2\src\main\java\com\example\addon`）：

- 正则 `已选目标|统计完成|详细输出|数据统计|最近结果|快照与识别目标|身份数据在磁盘上的位置|点击执行对应识别动作|识别目标配置中仍有效的选中项|已保存的物品状态快照数量|未知识别模式|输入后即时过滤当前` → **No matches found**
- 正则 `没有失效的识别目标|失效的识别|模块未启用|未知子命令|…` → 命中 19 行，**均不含** `失效的识别`、`模块未启用`、`未知子命令`
- 正则 `注册上报` → **No matches found**
- 归一化语料侧校验：`OLD.IndexOf("已选目标") = -1`（同时 `IndexOf("磁盘删除失败") = 680227`，证明语料有效、非空）

### §2 附：同属「旧项目无此串」但按口径 2 保留的框架层文本（**不需处理**）

以下 152 条同样「旧项目 grep 为空」，但均落在本项目自研框架路径（`command/`、`core/`、`config/`、`ui/`、`platform/resource/`、`repository/resource/`、`service/resourcepack/`、`model/`、`module/`），属「框架层必需文本」，与旧项目业务文案无冲突、不冒充业务文案；且 `08-规范…迁移.md:1555-1567`「11.5 保留的新功能提示」已逐条定稿保留。

| 文件 | 条数 | 逐字清单（`;; ` 分隔） |
| --- | --- | --- |
| `command/CommandManager.java` | 11 | 客户端指令共 ;; 个，输入 ;; help 查看全部 ;; §c未知客户端指令： ;; help 查看全部指令） ;; 执行异常： ;; §7补全候选（ ;; 项）：§f |
| `command/HelpCommand.java` | 6 | 列出全部客户端指令，或查看指定指令的用法 ;; help [指令名] ;; 未找到指令： ;; 客户端指令 ;; 个（前缀 ;; help 指令名 查看单条用法 |
| `command/ModuleCommand.java` | 14 | 查看模块列表与状态、开启或关闭模块 ;; module <list\|on\|off\|toggle\|status> [模块] ;; 未知子命令： ;; §7当前没有注册任何功能模块 ;; §c未找到分类： ;; 未分类 ;; §7该分类下没有模块 ;; 个，启用 ;; 操作未生效 ;; 未通过，条件满足后会自动开启 ;; 缺少模块参数 ;; 未找到模块： ;; （可用： |
| `config/ModuleStateConfig.java` | 3 | 记录了 ;; 个模块，其中 ;; 个启用 |
| `core/event/ClientEventType.java` | 2 | 收到数据包 ;; 发出数据包 |
| `core/module/Module.java` | 1 | 模块 ID 不能为空 |
| `core/module/ModuleManager.java` | 13 | 载入设置 ;; §c初始化失败，本次会话不可用 ;; 自检异常： ;; §c运行异常，已自动关闭 ;; §c事件处理异常，已自动关闭： ;; §c设置保存失败： ;; ，快捷键 ;; ，事件订阅 ;; ，等待进入世界 ;; ，初始化失败 ;; 个，启用 ;; 个，事件监听 ;; 未知异常 |
| `model/container/ContainerTransferResult.java` | 2 | 箱子已满 ;; 容器未同步 |
| `model/resource/ResourceParseState.java` | 2 | 解析成功 ;; 无目标资源 |
| `model/resource/ResourcePhase.java` | 1 | 未发现目标资源 |
| `module/AddonModules.java` | 6 | 管理自动执行类功能 ;; 管理辅助工具功能 ;; 管理工具类功能 ;; 管理路径与移动功能 ;; 管理装备强化功能 ;; 管理农场模拟功能（分类元数据，`08…迁移.md:1567` 明示保留） |
| `module/ModuleEntry.java` | 1 | 模块 ID 不能为空 |
| `platform/resource/BlockStateModelResolver.java` | 1 | `======== 开始解析 ` |
| `platform/resource/ResourceEnumerator.java` | 1 | 枚举资源异常： |
| `platform/resource/ResourcePackAccess.java` | 8 | 资源包数量: ;; ，请求资源: ;; -> MISS（读取异常）: ;; 读取资源内容异常 ;; 所有资源包均未命中: ;; 未找到资源定义 ;; 读取资源异常: |
| `repository/resource/ResourceAnalysisCache.java` | 8 | 资源数量 ;; 资源分类 ;; 目标命名空间 ;; 忽略命名空间 ;; 内容覆盖完整 ;; 记录时间 ;; 分析缓存 ;; 项（上限 |
| `service/resourcepack/ResourceExtractionService.java` | 2 | 不支持（单人世界） ;; 资源分类 |
| `service/resourcepack/ResourceIndexProbe.java` | 3 | 资源枚举结果为空，但命名空间中存在目标命名空间： ;; （命中缓存） ;; （完整分析） |
| `service/resourcepack/ResourcePackCache.java` | 1 | §7已存在同名缓存，保留既有文件 §8» §f |
| `ui/component/CategoryCard.java` | 1 | 暂无模块 |
| `ui/component/KeybindBadge.java` | 2 | 按下任意键... |
| `ui/component/KeyValueRow.java` | 1 | 状态不可用 |
| `ui/page/CardPage.java` | 1 | 暂无内容 |
| `ui/page/HomePage.java` | 4 | 首页 ;; yiyiaddon 客户端控制中心 ;; 首页内容待接入 ;; 功能模块接入后，概览与常用入口将显示在这里。 |
| `ui/page/InterfacePage.java` | 13 | 浏览并切换全部面板配色 ;; 主题选择 ;; 点击进入主题缩略图预览 ;; 界面大小 ;; 调整面板整体缩放 ;; 面板模糊 ;; 模糊面板后的游戏画面 ;; 模糊强度 ;; 调整高斯模糊半径 ;; 模糊底色 ;; 面板下方叠加的底色透明度 ;; 滚轮速度与长列表行为 ;; 面板外观、模糊与滚动参数 |
| `ui/page/ModuleCenterPage.java` | 3 | 模块中心 ;; 管理和配置客户端功能模块 ;; 还没有注册任何功能分类 |
| `ui/page/ModuleDetailPage.java` | 9 | 模块 ID ;; 未标注 ;; 所属分类 ;; 未分类 ;; 模块页面 ;; 已接入 ;; 独立页面 ;; 该模块尚未接入独立页面，接入后点击模块卡片将直接打开它自己的页面。 |
| `ui/page/ModuleListPage.java` | 1 | 该分类下还没有模块 |
| `ui/page/SearchResultsPage.java` | 1 | ”匹配的功能 |
| `ui/page/SettingsPage.java` | 8 | 指令前缀 ;; 客户端指令的起始符号，单个字符，默认 .　留空或填入 / 时回落到默认值 ;; GUI 快捷键 ;; 点击右侧按键块后按下任意键完成录入 ;; Baritone 汉化 ;; 把 Baritone 的命令与提示显示为中文 ;; 只读展示汉化开关 ;; 快捷键与全局开关 |
| `ui/screen/ClickGuiScreen.java` | 7 | 首页 ;; 在下方调整设置... ;; 再次点击以确认 ;; 重置界面设置 ;; × 关闭 ;; 点击缩略图切换面板配色 ;; 输入以查找... |
| `ui/screen/ColorPickerScreen.java` | 8 | §b§l▌ 预览 ;; §b§l▌ 色相 ;; §b§l▌ 饱和度与亮度 ;; §b§l▌ 数值 ;; 0 全透明，255 不透明 ;; §b§l▌ 彩虹 ;; 颜色随时间循环变化 ;; 每秒推进的色环数，越大变色越快 |
| `ui/screen/SelectorScreen.java` | 2 | §7搜索（显示名 / 技术ID） ;; 输入后即时过滤两侧清单 |
| `ui/theme/AppleDarkTheme.java` | 1 | 极夜霜玻璃 |
| `ui/widget/SettingModule.java` | 2 | 按下任意键... |
| `ui/widget/SettingText.java` | 1 | 状态不可用 |
| `ui/widget/SettingTextBox.java` | 1 | 点击输入 |

> 说明：上表全部条目在旧项目的实搜证据为「No matches found」（例：`模块中心|首页|主题选择|界面大小|面板模糊|…|极夜霜玻璃` 一次搜完，返回 **No matches found**）。判定为口径 2 的依据：`00-AI阅读指引.md:21,28`、`01-总纲-迁移口径与复刻原则.md:38,40`（界面外壳/控件本项目自研），`12-UI-03-界面自研铁律与配置页对齐.md`（界面自研铁律），`08…迁移.md:1555-1567`（新功能提示逐条定稿保留）。

## 3. 存疑清单（无法确定出处，需人工裁决）

| # | 文件:行 | 中文文本 | 为什么存疑 |
| --- | --- | --- | --- |
| 1 | `feature/identity/IdIdentifyModule.java:336` | `§6§l模块未启用（` | 旧项目无同串（grep 为空）；`08…迁移.md:1565` 已把它列入「保留的新功能提示（旧项目无 `.module`）」，但旧项目确实不存在对应文本，是否算「框架层必需」需人工确认 |
| 2 | `feature/identity/IdIdentifyModule.java:277` | `§7没有失效的识别目标` | 同上；`08…迁移.md:1566` 已记为「识别目标清理—旧项目无清理机制」保留项 |
| 3 | `feature/identity/IdIdentifyModule.java:277` | ` 项失效的识别目标` | 同上（同一保留项的拼接片段） |
| 4 | `feature/identity/IdIdentifyModule.java:331` | ` 项失效的识别目标` | 同上（`已清理 N 项失效的识别目标`） |
| 5 | `feature/identity/IdConfigModule.java:408` | `§7没有失效的识别目标` | 同上（重复实现） |
| 6 | `feature/identity/IdConfigModule.java:408` | ` 项失效的识别目标` | 同上 |
| 7 | `feature/identity/IdIdentifyModule.java:336` | ` 可开启）` | 整串无出处；仅 `可开启` 片段在旧项目命中，无法证明整句有出处 |
| 8 | `feature/identity/command/IdentityCommand.java:55` | `id [物品\|实体\|方块]` | 旧项目仅为「物品/实体/方块」三个子命令名，此「用法行」写法（`[A\|B\|C]`）旧项目无（`itemid/IdCommand.java:39-58` 的 `super("id", "识别物品、实体或方块并保存ID（…）")` 写法不同） |
| 9 | `feature/identity/command/IdentityCommand.java:107` | `id 模式 <模式> 切换` | 旧项目无 `模式` 子命令（旧靠模块设置项切换），字面无法溯源 |
| 10 | `feature/identity/command/IdentityCommand.java:113` | `（可选： ` | 旧项目无该提示句式，仅片段命中 |
| 11 | `feature/identity/service/IdentityActions.java:172` | ` ｜ 实体 ` | 分隔符 `｜` 为自造（旧项目用 `§8▸`，见 `itemid/IdConfigModule.java:224`），但单词 `实体` 有出处 |
| 12 | `feature/identity/service/IdentityActions.java:173` | ` ｜ 方块 ` | 同上 |
| 13 | `feature/identity/service/IdentityActions.java:174` | ` ｜ 快照 ` | 同上 |
| 14 | `service/RegisterService.java:39` | `§c注册上报失败（HTTP ` | 旧项目 `注册上报` 实搜为空；`08…迁移.md:1578` 明确「旧项目对应实现在 `YiyiaddonWelcomeService`，未确认其播报模块名」→ 需核对旧实现后定夺 |
| 15 | `service/RegisterService.java:48` | `§c注册上报异常：` | 同上 |

## 4. 合规清单（抽样证明，不必穷尽）

**① 旧项目同串（口径 1）——2167 条中 1923 条，抽样：**

| 类别 | 新项目位置 | 中文文本 | 旧项目位置 |
| --- | --- | --- | --- |
| 分类名 | `module/AddonModules.java:74-79`（名称字段） | `自动化` / `辅助` / `工具` / `星露谷` | `core/AddonTemplate.java:84,85,87,95`（`§c§lyiyiaddon §e§l自动化` 等） |
| 分类名 | 同上 | `导航` / `附魔` | 旧项目无同名分类，但词在旧项目大量出现（例 `autofarm/controller/FarmController.java:366`、`enchant/AutoEnchantBook.java` 全模块） |
| 模块名/描述 | `feature/identity/IdIdentifyModule.java`（`super(...)`） | 模块名与 description | `itemid/IdIdentifyModule.java:62`（`识别模式`）与 `08…迁移.md:1550-1551` 记录的旧原文（`ID识别` / `识别手持物品或准星方块并加入ID配置。点击开启即识别。`） |
| 设置项名 | `feature/identity/ui/IdentityModulePage.java:71` | `识别模式` | `itemid/IdIdentifyModule.java:62`（`.name("识别模式")`） |
| 播报 | `feature/identity/IdConfigModule.java:99,128` | `玩家未加载` | `itemid/IdConfigModule.java:394,449`（`notifyError("玩家未加载")`） |
| 播报 | `feature/identity/IdConfigModule.java:105` | `没有可识别物品：主手和副手都是空的` | `itemid/IdCommand.java:94` 与 `itemid/IdConfigModule.java:400` |
| 播报 | `feature/identity/IdConfigModule.java:133` | `自动识别失败：准星当前没有指向有效方块` | `itemid/IdConfigModule.java:454`、`IdIdentifyModule.java:157` |
| 播报 | `feature/identity/IdConfigModule.java:622 行体系` → 新 `:345` | `清空全部物品 ID 失败：磁盘删除失败，已回滚内存` | `itemid/IdConfigModule.java:622`（逐字相同） |
| 播报 | 新 `IdConfigModule.java:359/366/375/387` | `清空方块稳定记录失败：部分文件删除失败，请检查 blocks/ 目录` 等 4 条 | `itemid/IdConfigModule.java:554,565,577,647`（逐字相同） |
| 按钮/回执 | `feature/identity/ui/IdBlockResultScreen.java:76,81` | `已复制方块ID` / `已复制完整方块信息` | `worldblock/BlockResultScreen.java:91,92` |
| 按钮/回执 | `feature/identity/ui/IdResultScreen.java:75,80` | `已复制 Item ID` / `已复制完整识别信息` | `itemid/IdResultScreen.java:78,79` |
| 遥测 | `service/TelemetryService.java:135,142` | `高速移动 %.1f m/s` / `疑似瞬移 %.1f 格` | `utils/YiyiaddonTelemetryService.java:150,155`（`"高速移动 " + String.format("%.1f", speed) + " m/s"` 同词，仅拼接方式不同） |
| 翻译表 | `integration/baritone/*.java`（`BaritoneSettingTranslations` 484 条、`BaritoneChatTranslations` 381 条、`BaritoneCommandTranslations` 243 条） | 全部翻译条目 | 旧 `translations/BaritoneSettingTranslations.java`、`BaritoneChatTranslations.java`、`BaritoneCommandTranslations.java` —— **这 3 个文件 1108 条中文串，无一条被判为无出处** |

**② 框架层（口径 2）**：见 §2 附 152 条逐字清单；`08…迁移.md:1561-1567` 与 `00-AI阅读指引.md:28` 为定稿依据。
**③ 日志（口径 3）**：`command/CommandManager.java:126,210`、`command/CommandRegistry.java:37`、`config/ModuleStateConfig.java:69,92`、`core/module/ModuleManager.java:93,116,273,308,313,327,397`、`repository/resource/ResourceAnalysisCache.java:111,143`、`service/resourcepack/ResourceIndexProbe.java:91,111,236,239`、`ui/render/world/WorldOverlay.java:103`，共 **19** 条。
**④ 脚手架（口径 3）**：`feature/visuals/EspTestModule.java:73`（`ESP测试`、`世界渲染能力实测：框 / 线 / 面 / 射线 / 浮空字`）与 `feature/visuals/ui/EspTestPage.java:62-87` 共 **14** 条；依据 `EspTestModule.java:24` javadoc 原文「**这是脚手架，不是业务模块**……业务模块移植完成后可整体删除」。
**⑤ 装饰/拼接差异（口径 1 的变体，用词一致）24 条**：如 `§c§l✗ 玩家未加载`（旧 `玩家未加载`）、`§a§l✓ 已复制实体ID`（旧 `已复制实体ID`）、`§c§l✗ 删除方块失败`（旧 `删除方块失败`）——差异仅为新增的 `§c§l✗ ` / `§a§l✓ ` 装饰前缀（`§` 颜色码差异在口径允许范围内，用词未改）。

## 5. 重点区域结论

| 重点区域 | 有出处 | 无出处 | 代表性问题 |
| --- | --- | --- | --- |
| **模块名与描述** | 有（`IdIdentifyModule`/`IdConfigModule` 的名称与 description 归一化后均命中旧语料） | 0 | 无。`EspTestModule` 的 `ESP测试`/`世界渲染能力实测…` 为脚手架（口径 3，应随脚手架删除） |
| **分类名** | 6/6 名称有出处（旧 `core/AddonTemplate.java:84-95`；`导航`/`附魔` 为旧项目既有词） | 0（但 6 条**分类说明**无旧出处） | `管理自动执行类功能`/`管理辅助工具功能`/… 6 条说明新旧均无 → `08…迁移.md:1552,1567,1573` 已定稿「分类不属迁移范围」，按口径 2 保留 |
| **设置项名** | 部分有：`识别模式`（旧 `itemid/IdIdentifyModule.java:62`）、`方块快照`（旧 `itemid/IdConfigModule.java:702`）、`物品快照`（旧 `autochest/service/ChestInteractionService.java:332`） | `详细输出`（×2，新 `IdentityModuleConfig.java:20`、`IdentityModulePage.java:76`）、`已选识别目标`（新 `IdentityModulePage.java:93`） | `详细输出` 对应旧 `方块语义调试`，语义不同，`08…迁移.md:1575` 已列为「需单独迁移」，但当前仍是自造名 |
| **按钮文字** | 有：`复制 Item ID`/`复制完整信息`（旧 `itemid/IdResultScreen.java:78-79`）、`清空…` 类确认按钮（旧 `IdConfigModule.java:527-614` 的 `清空方块稳定记录`/`确认清空` 等） | 0 | 新增界面按钮（`× 关闭`、`再次点击以确认`、`重置界面设置`、`点击缩略图切换面板配色`、`清空全部测试层`）分别落框架层/脚手架，非业务按钮 |
| **页面标题** | 0（旧项目为 Meteor 界面，标题由旧框架生成） | 0（全部落框架层） | `首页`、`模块中心`、`模块页面`、`ColorPicker` 的 `预览/色相/饱和度与亮度/数值/彩虹`、`InterfacePage`/`SettingsPage` 各区块标题——旧项目实搜 **No matches found**，按口径 2 判定为自研界面文本 |
| **播报/提示** | 高：identity 播报 31 条中 **24** 条与旧项目逐字一致（仅 `§c§l✗ `/`§a§l✓ ` 装饰差异），例 `玩家未加载`、`清空全部物品 ID 失败：磁盘删除失败，已回滚内存` | 4（`已选目标`×2、`统计完成`×2）+ 1（` ｜ 已选目标 `） | 自造集中在「统计卡片字段」：旧项目用 `§8▸ §7物品/实体/方块/快照 §f` 字段流（`itemid/IdConfigModule.java:221-224`），新项目改成 `｜` 拼接并新增 `已选目标` |
| **指令回执** | 框架回执 31 条全部落口径 2（旧项目无自研指令框架，`08…迁移.md:1561`）；`id 模式 …`、`id [物品\|实体\|方块]`、`（可选： ` 片段命中旧语料 | 2（`未知子命令：`、`未知识别模式：`） | 旧 `.id` 只有 `物品/实体/方块` 三分支、无子命令分派（`itemid/IdCommand.java:39-58,83-193`），新项目的子命令/模式回执属自造句式 |

**总体比例**：中文字面量 2167 条中，合规 2132（98.4%）、无出处 20（0.9%）、存疑 15（0.7%）。无出处 100% 落在 `feature/identity` 与 `service/RegisterService`；`integration/baritone`（1108 条）、`ui/render`、`ui/theme`、`platform`、`repository` 无一条无出处。
