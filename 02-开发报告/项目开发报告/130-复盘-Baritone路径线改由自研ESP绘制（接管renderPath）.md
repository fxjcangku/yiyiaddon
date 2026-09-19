# 130 - 复盘：Baritone 路径线改由自研 ESP 绘制（接管 `renderPath`）

- 日期：2026-09-19
- 触发：用户「我想把男中音的 esp 渲染改成我自研的 esp 可以吗 可以实现吗」→ 范围问答选择
  「路径线全局化」+「允许改 Baritone 设置项」
- 涉及文件：`integration/baritone/BaritonePathOverlay.java`（新增）、
  `ui/render/world/EspGlobalSettings.java`、`ui/page/EspSettingsPage.java`、`YiyiAddonClient.java`
- 验证：`.\gradlew.bat compileJava --console=plain --rerun-tasks` → **BUILD SUCCESSFUL（EXIT=0）**；**未实机**

---

## 1. 结论先说

**可以做，本次只做「路径线」这一项**：Baritone 的寻路路径改由本项目自研的世界渲染层绘制，
颜色 / 线宽 / 最远距离 / 淡出 / 图元预算全部走既有的 ESP 全局口径，并接管 Baritone 自带的
`renderPath`（开启时置 `false`、关闭时原样还原），两条线不会叠在一起。

目标渲染（`renderGoal`）与选区框（`renderSelectionBoxes`）本次**未做**——用户在多选范围里只勾了路径线。

---

## 2. 取证：为什么「自研」是可行且更合适的选择

| 事实 | 证据 |
|---|---|
| Baritone 那条线由它自己注入渲染 | `MixinWorldRenderer` → `PathingBehavior#onRenderPass:580-581` → `PathRenderer#render`（参考库 `baritone-26.1.2-source`） |
| 它的可调项很粗 | `renderPath` / `renderPathAsLine` / `colorCurrentPath` / `colorNextPath` / `pathRenderLineWidthPixels` 几项；颜色与线宽**不参与本项目 ESP 的全局口径**（线宽倍率、不透明度、最远距离、淡出、图元预算） |
| 它的线宽是物理像素 | `PathRenderer` 交给原版 `rendertype_lines`，顶点着色器按 `LineWidth / ScreenSize` 展开 ⇒ 与 GUI 缩放无关，默认个位数（本项目 `MiningPointRenderer` 线宽注释已记录这条对比） |
| 本项目已有成熟通路 | `WorldOverlay`（`LevelRenderEvents.BEFORE_GIZMOS` + `GuiRenderer` 双阶段）→ `EspRenderer` → 原版 `Gizmos`；点位框、岩浆透视、挖掘进度框、瞄准高亮都走它，**用户已实机验证可见** |
| 数据源是公开 API | `IPathingBehavior#getCurrent()/getNext()` → `IPathExecutor#getPath()/getPosition()` → `IPath#positions()`（`List<BetterBlockPos>`），全部在 `baritone.api` 下 |

**注意**：64 号复盘曾做过一版自绘路径线（`feature/mining/render/MiningPathRenderer.java`），
**已在 66 号复盘整段删除**（`MiningSettings#pathEsp/#pathColor`、控制台两行、模块注册全部撤掉，
64 号结论作废）。本次是**重新实现**，且落点、范围、接管方式都与那次不同：
那次是模块私有层、不接管 Baritone；本次是全局常驻层、并接管 `renderPath`。

---

## 3. 实现

### 3.1 绘制（`BaritonePathOverlay#render`）

- **当前段**：`getCurrent()` 的路径，从 `getPosition() - 3` 起画（与 Baritone 同口径：不留这一截，
  线会从脚底下开始、走过的地方瞬间消失），颜色**不透明**。
- **规划段**：`getNext()` 的路径，从头画，颜色**半透明 110/255**（两段同色会看成一条，目标一换会以为路径在乱跳）。
- **节点连线**：方块中心（坐标 `+0.5`）；单帧每段最多 **200 个节点**，避免长距离寻路把
  「ESP 全局设置 ▸ 单帧图元上限」吃光、挤掉其它层。
- **线宽**：`3.0`（GUI 缩放坐标），与自动挖矿的点位框同档，再乘全局「线宽倍率」——
  用户 2026-09-18 要求过「自动挖矿的 esp 加粗一下，跟男中音的做鲜明对比」，自研层观感统一。
- **只读**：不写视角、不动目标、不改寻路行为（66 号的分段视角归属表不受影响）。

### 3.2 接管（`syncTakeover` / `restore`）

- 开关开启 → 记住 Baritone `renderPath` 原值并置 `false`；关闭 → 原值还回去。
- **只改运行期内存值，不调 `SettingsUtil#save`**，因此不会写盘污染 `run/baritone/settings.txt`
  （与 `MiningPathing` 改 `logger` 同款做法）。
- **同步放在主线程 tick**（`YiyiAddonClient` 注册 `END_CLIENT_TICK`）：Baritone 的静态
  `BaritoneAPI.getSettings()` 由它自己的模组初始化建立，客户端入口执行时可能尚未就绪（拿到 `null`），
  只在入口调一次会永久失手。每刻检查：未就绪静默跳过、下刻再试；就绪后只做一次写入，之后是纯布尔判断。
- 接管期间若用户在 Baritone 设置页把 `renderPath` 又打开，tick 会把它关回去——
  「接管」的语义就是这条线只由本项目画；要原版那条线，把本开关关掉即可。

### 3.3 设置项（「ESP 全局设置 ▸ 外观」）

| 设置项 | 默认 | 说明 |
|---|---|---|
| Baritone 路径线 | 开 | 由本项目自绘；开启时关闭 Baritone 自带路径渲染，关掉即原样还回去 |
| 路径线颜色 | `(0,224,255)` 青 | 只存 RGB；当前段不透明 / 规划段半透明由渲染层固定 |

落盘键 `baritonePath` / `baritonePathColor`（`config/yiyiaddon/esp-global.json`），
旧存档缺键回落默认值。设置页「使用说明 ▸ 外观：怎么画」已同步补上这两条（第 212 条）。

**常驻挂载**：不属于任何业务模块，由 `YiyiAddonClient#onInitializeClient` 注册一次 ⇒
自动挖矿、自动附魔、自动农场、村民交易、自动箱子等**所有用 Baritone 寻路的模块共用这一层**；
受「ESP 全局设置」总开关控制（第 76 条：ESP 层接入总闸）。

---

## 4. 影响面与风险

- **受影响**：Baritone `renderPath` 的运行期值被接管（关闭开关即恢复）；不影响 `renderGoal`、
  `renderSelectionBoxes`、寻路算法、视角。
- **未做**：目标渲染（方块目标框 / `GoalXZ` 信标柱）、选区框（`toBreak`/`toPlace`/`toWalkInto`）——
  用户本次只选了路径线，需要时再做。
- **风险**：本次改动**未实机**；打包 `jar` 任务因开发客户端正在运行占用 `build/libs/*.jar` 而失败，
  `compileJava` 已用 `--rerun-tasks` 全量重编译通过。

---

## 5. 待实机验收项

1. 自动挖矿（或任意模块）寻路时能看见一条青色的线，沿路径节点延伸、走过即收回。
2. **只有一条线**：Baritone 自带那条已关（可在 Baritone 设置页确认 `renderPath` 为 false 且不会自己弹回 true）。
3. 规划段（暗一档）存在；目标切换时不会看成「路径乱跳」。
4. 关掉「Baritone 路径线」后：自研线立刻消失，且 `renderPath` 回到接管前的值（原版线恢复）。
5. 改「路径线颜色」立即生效；调「线宽倍率 / 不透明度 / 最远距离」对本层同样生效。
6. 长距离寻路（回补给点 / 卸货）不出现明显掉帧、不挤掉点位框与挖掘进度框。

---

## 6. 观感升级（同日追加 · 用户第二轮口径）

用户 2026-09-19 追加：「他的寻路渲染太垃圾了 你帮高级 esp 跟好看点配色 可以吗 护眼的」。
按第 76 条，正文 §3 的数字不逐次回改，差异在此登记。

| 项 | 第一版 | 现在 |
|---|---|---|
| 默认主色 | `0x00E0FF` 高饱和青 | **`0x63C9B8` 低饱和薄荷青**（护眼；仍是基准色，用户可自定） |
| 当前段 | 整段单色、不透明 | **逐段插值**：起点端混向深青灰（`0x14343A` 40%）→ 车头端混向白（25%），一条线自带方向感，不必再画箭头 |
| 规划段 | 同色、alpha 110 | **混向冷灰蓝**（`0x8FA6B8` 45%）再压暗（`0x1A2126` 22%）、alpha 90 —— 与当前段在色相与亮度上双重拉开 |
| 共面闪烁 | 无处理 | 线段整体抬升 `0.03`（与 Baritone `emitPathLine` 同口径），不与被穿越的方块面共面 |

**两条实现约束**：

- **逐段单色，不做段内渐变**：`EspRenderer` 的双色线一条会拆成 `GRADIENT_SEGMENTS`（10）个图元，
  200 段就是 2000 个；逐段缓慢插值在观感上已经是渐变，图元数仍是 1 份/段。
- **颜色混合复用 `GlassPanel#mix`**，不新写第三份 lerp 工具（第 169 条；`SettingToggle` /
  `SettingModule` / `ClickGuiScreen` 里已有的私有副本不在本次范围内）。

设置页与使用说明已同步改文案（默认值、配色来源、抬升说明），第 212 条。
