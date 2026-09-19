# 130 - 复盘：Baritone 全部世界渲染改由自研 ESP 接管

- 日期：2026-09-19
- 触发：用户「我想把男中音的 esp 渲染改成我自研的 esp 可以吗 可以实现吗」→ 范围问答选择
  「路径线全局化」+「允许改 Baritone 设置项」→ 「他的寻路渲染太垃圾了 你帮高级 esp 跟好看点配色
  可以吗 护眼的」→ 「那 baritone 的设置页面怎么办?」→ **「不不不我要接管 baritone 的所有 esp 渲染」**
- 涉及文件：`integration/baritone/BaritoneOverlay.java`（新增，前身 `BaritonePathOverlay.java` 已删）、
  `integration/baritone/BaritoneRenderTakeover.java`（新增）、
  `mixin/baritone/BaritoneSettingsSaveMixin.java`（新增）、`yiyiaddon.baritone.mixins.json`、
  `integration/baritone/BaritoneSettingsPage.java`、`ui/render/world/EspGlobalSettings.java`、
  `ui/page/EspSettingsPage.java`、`YiyiAddonClient.java`
- 验证：`.\gradlew.bat compileJava --console=plain --rerun-tasks` → **BUILD SUCCESSFUL（EXIT=0）**；**未实机**

---

## 1. 结论先说

**Baritone 的四条有开关的世界渲染链路全部接管**：寻路路径（含计算中的最优路径 / 最近考虑路径）、
寻路目标、待破坏 / 待放置 / 待进入的方块框、`/sel` 选区 —— 一律改由本项目的
`WorldOverlay` → `EspRenderer` → 原版 `Gizmos` 管线绘制，颜色 / 线宽 / 最远距离 / 淡出 /
图元预算全部走既有的 ESP 全局口径；Baritone 自己的渲染开关被强制关掉并记住原值，
关掉接管开关即原样还原。

**只有一处接管不到**：`ElytraBehavior#onRenderPass` 的鞘翅飞行渲染（红色路径线、绿色瞄准框、
清道 / 阻挡射线、模拟线）。它**没有任何设置开关**，数据也全在私有字段里，既关不掉也读不到；
本项目的用途（挖矿 / 附魔 / 农场 / 村民 / 箱子）不涉及鞘翅飞行，在此记一笔以免以后误以为漏了。

---

## 2. 取证：为什么「只压开关」就够，为什么要压第五个开关

逐条对照参考库 `baritone-26.1.2-source`：

| 事实 | 证据 |
|---|---|
| 四类渲染各自被一个总开关罩住 | `utils/PathRenderer#render`（路径 / 目标 / 方块框）、`selection/SelectionRenderer#renderSelections`（选区） |
| 总开关一关，从属项一次都不会被读到 | `PathRenderer#render` 在 `if (!settings.renderPath.value) return;` 之前只画目标；颜色、线宽、`fadePath`、`renderGoalAnimated`、`yLevelBoxSize` 等全在 return 之后 ⇒ **不需要逐个覆盖**（`api/Settings.java` 里这类渲染项共 30 个） |
| `renderPath` 顺带管住方块框 | 方块框的绘制语句排在 `renderPath` 的早退之后（`PathRenderer#render:96-106`） |
| **第五个开关不能漏** | `command/defaults/SelCommand` 里另注册了一个 `onRenderPass` 监听器，用 `renderSelectionCorners` 控制「只选了第一个角点时的预览框」，**它不检查 `renderSelection`** ⇒ 只关四个开关还会漏出这一个框 |
| 数据源全在公开 API 上 | `IPathingBehavior#getGoal()/getCurrent()/getNext()/getInProgress()`、`IPathExecutor`、`IPath#positions()/getDest()`、`IPathFinder#bestPathSoFar()/pathToMostRecentNodeConsidered()`、`IBaritone#getSelectionManager()` → `ISelection#aabb()/pos1()/pos2()` |
| 目标体系的类型分支就是画法分支 | `PathRenderer#drawGoal`：`IGoalRenderPos` → 方块框；`GoalXZ` → 通高框 + 信标柱；`GoalComposite` → 递归；`GoalInverted` → 换色重画 `origin`；`GoalYLevel` → 玩家周围 ±`yLevelBoxSize` 的框 |
| 方块框取的是真实碰撞形状 | `PathRenderer#drawManySelectionBoxes` 用 `BlockState#getShape(...).bounds()`，形状为空时退回整格 |
| 鞘翅渲染无开关 | `process/elytra/ElytraBehavior#onRenderPass:417-448` 的 `visiblePath` / `aimPos` 直接画，**没有任何 settings 判断** |

**注意**：64 号复盘曾做过一版自绘路径线（`feature/mining/render/MiningPathRenderer.java`），
**已在 66 号复盘整段删除**（64 号结论作废）。本次是**重新实现**，且落点、范围、接管方式都不同：
那次是模块私有层、不接管 Baritone；本次是全局常驻层、并接管它全部渲染开关。

---

## 3. 实现

### 3.1 接管（`BaritoneRenderTakeover`）

- 接管期间强制写入的开关：`renderPath`、`renderGoal`、`renderSelectionBoxes`、`renderSelection`、
  `renderSelectionCorners` —— 一律 `false`，写前记下原值，关闭接管时逐个还原。
- **字段直取而不是按名字查表**：Baritone 改字段名会在编译期直接报错，不会静默失手。
- **只改运行期内存值，不调 `SettingsUtil#save`**，因此不会写盘污染 `run/baritone/settings.txt`
  （与 `MiningPathing` 改 `logger` 同款做法）。
- **同步放在主线程 tick**（`YiyiAddonClient` 注册 `END_CLIENT_TICK`）：Baritone 的静态
  `BaritoneAPI.getSettings()` 由它自己的模组初始化建立，客户端入口执行时可能尚未就绪（拿到 `null`），
  只在入口调一次会永久失手。每刻检查：未就绪静默跳过、下刻再试；就绪后只做一次写入，之后是纯引用比较。
- 接管期间若用户在 Baritone 设置页把它打开，tick 会关回去——「接管」的语义就是这些线只由本项目画。
- 接管条件只有一份：`BaritoneOverlay#syncTakeover()` 只把用户开关转成入参（第 169 条）。

### 3.2 绘制（`BaritoneOverlay#render`）

**为什么自研**：Baritone 那几条线的线宽是**物理像素**（默认 3~5，交给原版 `rendertype_lines`，
顶点着色器按 `LineWidth / ScreenSize` 展开，与 GUI 缩放无关），颜色也只有几个粗粒度设置项，
不参与本项目的线宽倍率 / 不透明度 / 最远距离 / 淡出 / 图元预算。改走自研层后全部统一。

**四类图形**（单帧上限：路径各 200 段、方块框合计 256 个、目标合计 128 个）：

- **路径**：当前段从 `getPosition() - 3` 起画（不留这一截，线会从脚底下开始、走过的地方瞬间消失）；
  规划段从头画；计算中的最优路径与最近考虑路径各降一档不透明度。四档由亮到暗对应
  「正在走 / 接下来走 / 算到最好的 / 刚看过一眼的」。
- **目标**：普通目标框两格高、`GoalGetToBlock` / `GoalTwoBlocks` 贴方块只框一格（与 Baritone 同口径）；
  `GoalYLevel` 画玩家周围 ±16 格的水平面；`GoalXZ` 画通高方框 + 两片交叉的渐隐光柱
  （**替代**原版那条贴图信标，见 §4 差异登记）；`GoalInverted` 换暖珊瑚色；`GoalComposite` 递归。
- **挖掘方块框**：`PathExecutor#toBreak()/toPlace()/toWalkInto()` 三个集合，各一色；
  取方块**真实碰撞形状**的包围盒（花草、半砖不会被画成整格）。
- **选区**：`ISelectionManager#getSelections()` 逐条画主体 + 两个角点。
- **只读**：不写视角、不动目标、不改寻路行为（66 号的分段视角归属表不受影响）。
- **只有接管成功才画**：`BaritoneRenderTakeover.active()` 为假（Baritone 还没就绪）时本层不画，
  免出现「两套渲染叠一帧」的闪烁。

### 3.3 设置项（「ESP 全局设置 ▸ 外观」）

| 设置项 | 默认 | 说明 |
|---|---|---|
| Baritone 渲染接管 | 开 | 四类图形改由本项目自绘；开启时把 Baritone 自己那几项渲染全部关掉，关掉即原样还回去 |
| Baritone 渲染颜色 | `0x63C9B8` 薄荷青 | 基准色：路径线由它派生明暗、目标框与选区主体同色；挖掘框与角点是固定语义色 |

落盘键 `baritoneOverlay` / `baritoneColor`（`config/yiyiaddon/esp-global.json`），旧存档缺键回落默认值。
**颜色刻意只留一个基准色**：全开放会调出「看不出哪个是挖、哪个是放」的组合；
挖掘框固定「破坏暖 / 放置冷 / 进入青绿」，语义仍然分得清。设置页「使用说明 ▸ 外观：怎么画」已同步（第 212 条）。

### 3.4 Baritone 设置页怎么办（用户追问项）

接管期间那 30 个渲染项改不出任何效果，因此 `BaritoneSettingsPage`：

- 逐项跳过 `BaritoneRenderTakeover#isSuppressed` 为真的键（清单在接管类里单点维护，与压开关的字段一一对应）；
- 页首新增「渲染接管」分组一行，写明「样式改在 ESP 全局设置 ▸ 外观 ▸ Baritone 渲染接管，
  本页已收起失效的 30 项，关掉总开关即原样还回来」；
- 页面在打开时构造，因此改完开关重开本页即看到最新的收起结果。

**常驻挂载**：不属于任何业务模块，由 `YiyiAddonClient#onInitializeClient` 注册一次 ⇒
自动挖矿、自动附魔、自动农场、村民交易、自动箱子等**所有用 Baritone 寻路的模块共用这一层**；
受「ESP 全局设置」总开关控制（第 76 条：ESP 层接入总闸）。

---

## 4. 差异登记（第 76 条：正文数字不逐次回改，差异在此单列）

| 项 | Baritone 原版 | 本项目自研 |
|---|---|---|
| 路径线宽 | 物理像素 5 | GUI 缩放 3.0（再乘全局线宽倍率） |
| 默认主色 | 红 / 品红 / 蓝 / 青各一色（按语义分） | **单一基准色 `0x63C9B8` 薄荷青**（护眼，可改） |
| 当前段 | 整段单色、alpha 102 | 逐段插值：起点端混向深青灰（`0x14343A` 40%）→ 车头端混向白（25%） |
| 规划段 | 同色、alpha 102、带 10→20 段距离淡出 | 混向冷灰蓝（`0x8FA6B8` 45%）再压暗（`0x1A2126` 22%）、alpha 90 |
| 共面闪烁 | 节点坐标额外 +0.03 | 同口径抬升 `0.03` |
| 目标框 | 上下两个水平四边形 + 四根通高棱线（`renderGoalAnimated` 让两个四边形来回扫），`GoalXZ` 画贴图信标光柱 | 整格方框 + 极淡填充，**整框沿 Y 正弦上下浮动 ±0.15 格 / 1.8 秒**（比原版那种「面来回扫」更安静）；`GoalXZ` 用通高方框 + 两片交叉渐隐光柱（**不引入贴图与深度态特殊处理**） |
| 目标框颜色 | 单一 `colorGoalBox`（默认绿）/ 反向目标 `colorInvertedGoalBox` | **按矿石种类上色**（10 种，见 §5）；非矿石目标用基准色；`GoalInverted` 仍单独换暖珊瑚色 |
| `GoalYLevel` | 玩家周围 ±`yLevelBoxSize`（默认 15）的 2 格高方框 | 玩家周围 ±16 格的一整张水平面（**面积大、再淡一档**；标记精确高度，故不浮动） |
| 穿透 / 遮挡 | `renderPathIgnoreDepth` 等四项各自可调 | 统一走本项目的「透视覆盖」（默认透视，与 Baritone 默认一致） |
| 挖掘框 | `blockBox` 三色（红 / 绿 / 品红，高饱和） | 破坏 `0xE0A08C` / 放置 `0x8EA9E0` / 进入 `0xA8C97E`（低饱和，同明度区间）；**矿石那一格改用它的矿石色**，与目标框同色 |
| `/sel` 预览框 | `renderSelectionCorners` 下由 `SelCommand` 画第一个角点的预览 | **不再有预览**（Baritone 无公开 API 读到待定角点）；两点选完后由自绘选区完整呈现 |

---

## 5. 观感追加（同日 · 用户第三轮口径）

用户 2026-09-19 追加：「baritone 会有那种 esp 上下浮动的效果你弄得没有 就是渲染矿石的 那个浮动的
然后 esp 什么矿石对应什么颜色的 esp 颜色帮我设置」→「不是让你设计高级一点的动画吗 寻路的颜色跟动态」。

**路径流光**（`drawCurrentPath` / `flowGlow`）：当前段底色（起点压暗 → 车头提亮）之上再叠一条
**朝目标流动的亮带** —— 段索引上一条正弦，相位随时间前移，波峰处颜色混向近白薄荷
（`0xEAFBF7` × 0.72）、线宽同时 `+35%`，于是亮带是一段「带厚度的光」；波谷取正弦的平方压暗，
亮带更窄、暗区更长。速度 = 波长 10 段 / 周期 900 ms ≈ **11 格/秒**。

**相位按「绝对段索引」算，不按窗口内相对索引**：段索引对应世界里的固定节点，亮带才是在世界空间里
朝目标走；用相对索引的话窗口随玩家前移，亮带会被玩家拖着走（走得快时看着像倒退），变成跑马灯。

**规划段与计算中的路径不加流光**：流光在本层是「这段正在走」的信号，到处都在动就失去意义。

**目标框浮动**：`drawGoalPos` 把整框沿 Y 按正弦抬落，幅度 `0.15` 格、周期 `1800 ms`
（`goalFloatOffset()`）。`GoalYLevel` 的水平面与 `GoalXZ` 的光柱<b>不浮动</b>——前者标记一个精确高度，
后者本来就是通高柱。

**矿石配色**（`ORE_COLORS`，键为方块注册名里的关键字）：

| 矿石 | 颜色 | 矿石 | 颜色 |
|---|---|---|---|
| 煤 | `0x8A8F99` 石墨灰 | 青金石 | `0x6B8CD9` 青金蓝 |
| 铜 | `0xD08C6A` 铜橙 | 钻石 | `0x4FD8E0` 亮青（比基准薄荷青偏蓝，不会看混） |
| 铁 | `0xD9C7B4` 铁米白 | 绿宝石 | `0x6BD98A` 翠绿 |
| 金（含下界金矿） | `0xE8C46A` 金黄 | 下界石英 | `0xE6E0D4` 石英白 |
| 红石 | `0xD96B6B` 红 | 远古残骸 | `0x9A7B6B` 焦褐 |

**判据只认真矿石**：注册名必须以 `_ore` 结尾或等于 `ancient_debris`，所以 `coal_block`
这类同名非矿石不会被误染色；深层（`deepslate_diamond_ore`）与下界（`nether_gold_ore` /
`nether_quartz_ore`）变体因为都含同一关键字，自动命中同一个颜色。

**生效范围**：目标框 + 挖掘框里那一格是矿石时（`colorFor`）。挖矿时「目标」与「要破坏的方块」
常常就是同一格矿石，两处各画一个颜色会糊在一起，所以矿石格统一走矿石色，
三色语义留给真正需要区分的非矿石方块。此外目标颜色还会看<b>目标上方一格</b>：
`#mine` 的目标由 `MineProcess#coalesce` 生成，常出现 `GoalTwoBlocks` 落在矿石下面那一格，
只看目标本身会漏色。

---

## 6. 影响面与风险

- **受影响**：Baritone 五个渲染开关的运行期值被接管（关闭开关即恢复）；
  Baritone 设置页少列 30 项（仅接管期间）；接管期间这几项不会被写进 `run/baritone/settings.txt`
  （`BaritoneSettingsSaveMixin` 把它们从 `SettingsUtil#modifiedSettings` 里剔除）。
- **不影响**：寻路算法、视角、`renderCachedChunks` 等非 ESP 渲染、任何模块的运行状态。
- **风险**：本次改动**未实机**；打包 `jar` 任务因开发客户端正在运行占用 `build/libs/*.jar` 而失败，
  `compileJava` 已用 `--rerun-tasks` 全量重编译通过。写盘闸门与渲染接管都要**重启客户端**才生效
  （mixin 只在启动期加载）。

---

## 7. 待实机验收项

1. 寻路时只有**一条**青色的线，沿节点延伸、走过即收回；Baritone 设置页里 `renderPath` 显示为 false 且不会弹回 true。
2. 规划段（更暗）、计算中的最优路径（再暗）、最近考虑路径（最暗）四档能分辨；
   当前段有一条**亮带朝目标流动**（约 11 格/秒，带处略粗），移动时不倒着走、不随视角抖动。
3. 目标框：整框在**上下浮动**；普通目标两格高、`GoalGetToBlock` 一格；`GoalYLevel` 出现一张水平面（不浮动）；
   `GoalXZ` 出现通高框 + 光柱。
4. `#mine diamond_ore`（或混合矿区）：目标框按矿石种类上色（钻石亮青 / 金金黄 / 铁米白…），
   深层与下界变体同色；非矿石目标用基准色。
5. 挖掘时待破坏 / 待放置 / 待进入三种颜色的方块框都在，且贴合真实形状；矿石那一格是它的矿石色。
6. `/sel 1` `2` 之后选区与两个角点由自绘层呈现；`/sel 1` 后不再有 Baritone 的预览框（已知代价）。
7. Baritone 设置页首出现「渲染接管」分组，且渲染相关的 30 项已收起；关掉「Baritone 渲染接管」后重开本页，
   30 项回来、原版渲染恢复。
8. 接管期间执行一次 `#set <任意项> <值>`，再查看 `run/baritone/settings.txt`：**不应出现** `renderPath` 等五项。
9. 长距离寻路（回补给点 / 卸货）不出现明显掉帧、不挤掉点位框与挖掘进度框。
