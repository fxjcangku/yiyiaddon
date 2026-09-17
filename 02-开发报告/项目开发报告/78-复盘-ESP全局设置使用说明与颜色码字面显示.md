# 78 - 复盘：ESP 全局设置「使用说明」按钮与 §7 字面显示

- 日期：2026-09-17
- 触发：用户「我没看懂 esp设置怎么弄 帮我弄个使用说明按钮详细的 / 还有这里 &7 显示错误了 看见了吗」（附截图：`渲染模式覆盖`、`透视覆盖` 两行行尾直接显示 `§7点击切换` 字面）
- 涉及文件：
  - `ui/page/BasePage.java`
  - `integration/baritone/BaritoneSettingsPage.java`
  - `ui/page/EspSettingsPage.java`
- 验证：`.\gradlew build --console=plain -q` → **EXIT=0**；**实机待确认**

---

## 1. §7 字面显示：设置页的行说明不解析颜色码

### 根因（源码级）

设置页每一行的说明走的是 `SettingModule` 的子项绘制，最终落到 `ModuleRow.drawEntry` → `FontRenderer.drawText`——
**纯文本渲染**，不做 `§` 解析。而循环项为了提示「能点」，在说明末尾拼了 `HINT_CYCLE`，那常量里带 `§7`：

```java
// 改动前
protected static final String HINT_CYCLE = UiText.t("　§7点击切换", " · click to switch");
```

于是行尾把 `§7点击切换` 的 `§7` 原样画了出来（截图所见）。

能解析 `§` 的只有两处：控制台（`ConsoleWidgets`，走 `MinecraftText.draw`）与独立窗口（`PanelScreen` 系）。
`SettingText` 这类**控件**也走 `MinecraftText`，所以 `BaritoneSettingsPage.summarize()` 里的 `§8-` / `§f` 是安全的。

### 修复

| 文件 | 常量 | 改动 |
| --- | --- | --- |
| `BasePage.java` | `HINT_CYCLE` | `"　§7点击切换"` → `"　· 点击切换"` |
| `BaritoneSettingsPage.java` | `HINT_CYCLE` | `" §8· §7点击切换"` → `" · 点击切换"` |
| `BaritoneSettingsPage.java` | `HINT_COLOR` | `" §8· §7点击色块打开调色板"` → `" · 点击色块打开调色板"` |

`BasePage.HINT_CYCLE` 的 javadoc 里补了这条约束（为什么这里不能写 `§`，以及只有哪两个场合能写），
顺手合并了该常量上方相邻的重复 javadoc 块。

全量核查：`ui/page` 与 `integration` 下再没有第二处把 `§` 写进行说明/副标题的地方（剩余带 `§` 的行
全是 `TextLine` / 控制台 / `ClientChat` 这些确实支持颜色码的场合）。

## 2. ESP 全局设置「使用说明」按钮

`EspSettingsPage` 是 `BasePage`（不是 `CompactModulePage`），不能像模块页那样用 `addCore` 铺满宽行，
因此入口做成**首组 + 默认展开**的一行按钮：

```java
SettingModule help = group("使用说明", "看不懂每一项是干什么的点右边按钮，逐项讲清作用、取值与默认值", ICON_HELP);
help.addSub("打开逐项说明", "覆盖总开关 / 各模块 ESP / 外观 / 可见度 / 文字 / 性能 / 自己，以及常见问题",
    new Button("§e查看使用说明", this::openHelp));
help.setExpanded(true);
```

- 放在**最前**并 `setExpanded(true)`：组头默认是折叠的，唯一的「从哪下手」入口折起来等于没有；
- 按钮控件 `Button` 的标签走 `MinecraftText`（支持 `§`），与 `StardewResourcePanelPage` 的
  `§b打开控制台` 同一口径；
- 打开窗口固定走 `HelpPanelScreen`（开发习惯第 137 条），`parent` 取当前屏幕，返回即回到本设置页；
- 图标 `ICON_HELP = "\uE887"`（Material `help`）：用脚本解析 `MaterialSymbolsRounded.ttf` 的 cmap 验真存在
  （第 140 条：字形直接进字体绘制，字体里没有就是一块豆腐）。

### 说明内容（八章节，逐项对应实现）

| 章节 | 要点 |
| --- | --- |
| 这一页是干什么的 | 全局层 vs 模块自己那套的分工；默认值 = 什么都不改；落盘路径 |
| 三步上手 | ① 全关 → 总开关 ② 只关一个模块 → 各模块 ESP ③ 粗细/亮度/距离 → 外观 + 可见度 |
| 总开关与各模块 ESP | 五个层各关掉后具体少画哪些东西（与 `layerHint` 同一份事实） |
| 外观：怎么画 | 线宽倍率 0.5~3.0 / 渲染模式覆盖 / 透视覆盖 / 瞄准方块高亮，并单列一条：自动挖矿运行期间白框自动不画 |
| 可见度：画多少 | 不透明度 10%~100% / 最远距离 0~256（0 = 不限）/ 距离淡出与淡出起点 |
| 文字与性能 | 字号 0.5~2.5 / 文字底板 / 每帧图元上限 0~4096（每个框、线、面、一段字各算一个） |
| 自己 | 第一人称（默认开）与第三人称（默认关）隐藏自己 |
| 常见问题 | 颜色改不了 → 归各模块控制台；改了没反应 → 先看是否默认值再看层开关；关总开关不丢模块设置 |

取值域与默认值全部照抄 `EspGlobalSettings` 的常量与字段初值，不写「大概 / 支持」这类没信息量的说法
（第 212 条：说明必须与实现保持最新）。

## 3. 编译验证

```
.\gradlew build --console=plain -q   → EXIT=0
```

## 4. 待实机验证项

1. `渲染模式覆盖` / `透视覆盖` 行尾显示为 `· 点击切换`，不再出现 `§7` 字面。
2. Baritone 设置页循环项 / 颜色项的行尾提示同样不再出现 `§8· §7` 字面。
3. ESP 全局设置打开时首组「使用说明」是展开的，点 `查看使用说明` 弹出标题为
   `ESP 全局设置 - 使用说明` 的窗口；八章节排版与其它模块的说明窗口一致（框线 + `[#]` 标题）。
4. 说明窗口内返回 / 底部关闭都回到 ESP 全局设置页，而不是直接退回游戏。
5. ESP 总闸（各模块 ESP 五个开关）实际生效：关掉某一层后该模块的框与字牌消失，模块自身设置与运行状态不变。

## 5. 遗留与注意

- 改动**未 commit**；本轮全部改动**未实机验证**。
- 客户端（PID 23172）仍在跑**旧 jar**：ESP 总闸加固、§7 修复、使用说明按钮都还没生效，
  需按既有流程重启（`taskkill /PID <客户端PID>` 不带 `/F` → 等日志出现 `Stopping!` → `.\gradlew runClient`）。
- 待用户裁决（已提，未答）：① 是否把「新增模块接入 ESP 总闸三步清单」写进 `开发习惯.md`；
  ② 附魔点位 `EnchantSettings.espPoints` 是否纳入「各模块 ESP」；③ 是否在模块中心给
  「ESP 全局设置」加直达入口。
- 调试插桩会话 `mining-stall-after-rtp` 仍在树内未清理。
