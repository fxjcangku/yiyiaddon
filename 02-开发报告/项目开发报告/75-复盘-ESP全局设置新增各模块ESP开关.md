# 75 - 复盘：ESP 全局设置新增「各模块 ESP」总闸

- 日期：2026-09-17
- 触发：用户「我的 esp 全局设置是不是可以调的 没有你帮我加上 可以调这些插件模块的配置」
  （追问后裁定：**加「各模块 ESP 开关」区**，颜色仍归各模块自己）
- 涉及文件：`ui/render/world/EspGlobalSettings.java`、`ui/page/EspSettingsPage.java`、
  `feature/mining/render/MiningPointRenderer.java`、`feature/mining/render/MiningBreakProgressRenderer.java`、
  `feature/stardew/render/StardewRenderState.java`、`feature/autochest/render/AutoChestRenderer.java`、
  `feature/villager/render/ContainerESP.java`、`feature/admindetect/render/AdminThreatRenderer.java`
- 验证：`./gradlew build --console=plain` → **BUILD SUCCESSFUL（EXIT=0）**；**实机待确认**

---

## 1. 先澄清入口（用户以为没有）

`ESP 全局设置` 早就存在，且功能完整（总开关 / 外观 / 可见度 / 文字 / 性能 / 自己 共六组），
但它是 `ModuleCategory.settings(...)` 注册的**配置页面**，按该 API 的既定语义
「**入口挂在设置页里，不出现在模块中心**」——所以模块网格里永远找不到它。

**正确路径**：打开主界面 → 左侧「设置」分组 → 「ESP 全局设置」（与「Baritone设置」并列）。

顺手核对用户实际配置 `run/config/yiyiaddon/esp-global.json`：全部为默认值
（`thicknessScale 1.0`、`modeOverride FOLLOW`、`alphaScale 1.0`、`maxDistance 0`）——
用户从未调过，74 号报告里「星露谷看着更粗可能是线宽倍率叠加」的猜测就此否证（两处常量本来就都是 1.5）。

## 2. 本轮新增：各模块 ESP 总闸

**口径演进（已在类 javadoc 留痕）**：本类原口径是「不改任何颜色，**也不替模块决定画不画某一类目标**」。
新增总闸越过了后半句，故修订为：**模块自己仍决定「画什么、什么颜色、什么模式」，全局层只额外交一层
「允许 / 抑制」总闸**；抑制期间模块只是不画，其设置与运行状态一概不动，取消抑制即原样恢复。

**数据**（`EspGlobalSettings`）：

```java
public enum Layer { MINING("挖矿"), STARDEW("星露谷"), AUTO_CHEST("自动箱子"),
                    VILLAGER("村民容器"), ADMIN("管理员检测") }

private final boolean[] layerEnabled = {true, true, true, true, true};   // 下标与 ordinal 对齐
public boolean layerEnabled(Layer layer)
public void setLayerEnabled(Layer layer, boolean value)                  // 改动即时落盘
```

落盘键 `layer_<枚举名小写>`（如 `layer_mining`），**缺键回落 `true`**，因此旧配置文件行为不变。

**拦截点**（各渲染层入口，全局优先于模块自己的开关）：

| 模块层 | 拦截位置 |
| --- | --- |
| 挖矿 | `MiningPointRenderer.render`（点位框 + 岩浆框）、`MiningBreakProgressRenderer.render`（进度框 + 百分比） |
| 星露谷 | `StardewRenderState.draw`（放在 `draw` 而非两个 public 入口，`render` 与 `renderNearby` 一并拦住） |
| 自动箱子 | `AutoChestRenderer.render` |
| 村民容器 | `ContainerESP.render` |
| 管理员检测 | `AdminThreatRenderer.render` |

**界面**（`EspSettingsPage`）：在「总开关」之后、外观之前新增「各模块 ESP」分组，五行开关由
`Layer.values()` 循环生成，说明文案写清「关掉后这个模块的哪些绘制会消失」。分组图标 `\uE429`
取自 `InterfacePage.ICON_SCROLL`（同字体、已验证存在的码点），避免引入未验真的字形。

## 3. 待实机验证项

1. 设置 ▸ ESP 全局设置 ▸ 「各模块 ESP」可见五行开关，中文名与说明正常。
2. 关「挖矿 ESP」：点位框、岩浆框、进度框与百分比同时消失；挖矿行为（寻路、秒破、连锁）不受影响。
3. 关「星露谷 ESP」：整片农田与「预览范围」临时观察层同时消失。
4. 关掉后再打开：立刻恢复原样（模块自身设置未被改动）。
5. 重启游戏后开关状态保持（`esp-global.json` 里的 `layer_*` 键）。
6. 只关某一模块时，其它模块 ESP 不受影响。

## 4. 遗留与注意

- 「ESP 全局设置」的入口位置（藏在设置页里）容易被误认为「没有这个功能」——若实机仍觉得难找，
  可考虑在模块中心加一个直达入口（未做，等用户意见）。
- 附魔点位（`EnchantSettings.espPoints`）也是走 ESP 的一层，但用户本轮未点名，**未纳入**这五个开关。
- 改动未 commit；调试会话 `mining-stall-after-rtp` 仍在进行（插桩代码尚在树内）。
