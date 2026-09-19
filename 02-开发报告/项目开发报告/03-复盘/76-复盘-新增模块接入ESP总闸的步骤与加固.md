# 76 - 复盘：新增模块时如何接入「各模块 ESP」总闸（含一处脆弱点加固）

- 日期：2026-09-17
- 触发：用户「我以后新增模块怎么办？」
- 涉及文件：`ui/render/world/EspGlobalSettings.java`
- 验证：`./gradlew build --console=plain` → **BUILD SUCCESSFUL（EXIT=0）**；纯加固、行为不变，**未实机**

---

## 1. 先修掉实现里的脆弱点

75 号的 `layerEnabled` 是手写字面量数组：

```java
private final boolean[] layerEnabled = {true, true, true, true, true};
```

将来在 `Layer` 里加一项却忘了同步数组长度，`layerEnabled[layer.ordinal()]` 会在**渲染线程**上
抛 `ArrayIndexOutOfBoundsException`，而编译器不会报错。已改为按枚举长度自动生成：

```java
private final boolean[] layerEnabled = allLayersEnabled();   // 新增枚举项自动默认开启，不可能越界
```

## 2. 新增一个带 ESP 渲染层的模块：三步

| # | 改哪儿 | 内容 | 编译期保护 |
| --- | --- | --- | --- |
| 1 | `EspGlobalSettings.Layer` | 加一项，如 `MY_THING("我的模块")`；括号里就是开关行显示的中文名 | 无（但下一步会挡住） |
| 2 | `EspSettingsPage.layerHint` | 加一个 `case MY_THING -> "关掉后消失的绘制……"` | **有** —— 该 switch 是表达式且无 `default`，漏 case 直接编译失败 |
| 3 | 该模块的渲染层入口 | 加一行 `if (!EspGlobalSettings.get().layerEnabled(EspGlobalSettings.Layer.MY_THING)) return;` | 无 |

**第 3 步的要点（没有编译期保护，最容易漏）**：

- **一个模块有多条绘制路径时，要覆盖每条入口**。范例：星露谷的 `render`（整片农田）与
  `renderNearby`（`.stardew 预览范围` 的临时观察层）——因为两者共用一个私有 `draw`，
  所以只把检查放在 `draw` 里就够了，比放在两个 public 入口更省。
- 反过来：**不要**把检查放在"注册渲染层"的地方就想着一劳永逸——并非所有绘制都走注册，
  预览层、指令触发的临时绘制都可能由别的路径驱动。判据永远是「**每一条能进入绘制的路径**」。

漏了第 3 步的后果：新模块的 ESP **不受总闸控制**（关不掉）。是静默的、非致命的（不影响其它模块）。

## 3. 不用改的地方

- **落盘**：`layer_<枚举名小写>` 键由 `Layer.values()` 循环自动读写，新增项自动就有；缺键回落 `true`。
- **界面**：`EspSettingsPage` 的五行（以后 N 行）由 `Layer.values()` 循环生成，加枚举即多一行。
- **中文名**：`Layer` 构造参数即显示名，不必另开一份文案。

## 4. 待实机验证项

无行为变更，无需专门验证；下次重启客户端即随其它改动一并生效。
（新开关行为仍待验证：见 75 号第 3 节。）
