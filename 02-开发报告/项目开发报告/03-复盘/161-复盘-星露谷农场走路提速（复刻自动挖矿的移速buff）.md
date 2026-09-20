# 161 复盘：星露谷农场走路提速（复刻自动挖矿的移速 buff）

## 一、需求与口径

用户 2026-09-21 原话：

> 「自动挖矿的加速能不能帮我加到星露谷农场嘿嘿一比一复刻」
> （追问后明确）「就是那个赶路的加速走路」
> （再更正）「不是男中音的设置 是加速的代码 buff来的」

即：把**自动挖矿模块给玩家的那个移速加成**，1:1 复刻到星露谷农场。

**先说一次走错的路**：第一次按「挖矿的赶路加速」去查代码，找到的是 `MiningPathing#applySettings`
里写的那组 Baritone 设置（`allowSprint` / `sprintAscends` / `allowParkour` / `allowParkourPlace` /
`allowDiagonalAscend` / `allowDiagonalDescend`），并已在 `FarmNav` 里加好了「星露谷导航期间套用、
释放时还原」的覆盖。用户一句「不是男中音的设置 是加速的代码 buff 来的」把方向掰正 —— 那组设置
是「寻路风格」，真正让挖矿走路快的是**属性修饰符**。已全部撤回（`FarmNav` 恢复原样）。

**教训**：同一个「加速走路」在同一个模块里有两套实现（寻路风格 + 移速属性），问「是哪个」比猜哪个更省事；
用户描述里的名词（「buff」）就是最短的路径。

## 二、挖矿那份加速的真实实现（原样照抄的来源）

`AutoMinerModule`（旧位置 `:1007-1052`）：

```java
private static final Identifier WALK_SPEED_MODIFIER_ID =
    Identifier.fromNamespaceAndPath("yiyiaddon", "miner_walk_speed");
private static final int WALK_SPEED_LEVEL = 2;                 // 1 档 = 速度一(+20%)、2 档 = 速度二(+40%)
private static final double WALK_SPEED_BONUS = 0.02 * WALK_SPEED_LEVEL;

speed.addTransientModifier(new AttributeModifier(
    WALK_SPEED_MODIFIER_ID, WALK_SPEED_BONUS, AttributeModifier.Operation.ADD_VALUE));
```

要点三条：

1. **瞬态属性修饰符**（`addTransientModifier`）：不落盘、模块关掉就摘干净、不加任何设置项
   （用户 2026-09-18：「不用加设置 就默认写在代码里面」）；
2. **每刻补挂**：服务端下发属性同步包会整体重置客户端的属性实例，修饰符会被冲掉，
   所以启用时挂一次、每刻再补一次（已挂着时只是两次查表）；
3. 幅度 = 2 档 = +0.04 到玩家基础移速 0.1（即 +40%）；被服务端拉回（「你移动得太快」）就降 1 档。

## 三、落地方式（抽成一份共用实现）

新增 [`platform/player/WalkSpeedBoost`](../../../src/main/java/com/yiyiaddon/platform/player/WalkSpeedBoost.java)：
`apply()` / `clear()` + 档位常量（`LEVEL = 2`）+ 修饰符 id（`yiyiaddon:walk_speed`，
原 `miner_walk_speed` 并入改名）。两个模块共用这一份、也共用同一个 id。

- **为什么不各留一份**：（1）档位一调就会走散；（2）两个模块同时开着时同一份加成会被挂两次
  （+40% → +80%），很可能直接被服务端拉回；共用同一 id 时后挂的那次是空操作，摘除也自愈
  （一个模块关掉、另一个下一拍补挂回来）。
- **挖矿侧改动**：`applyWalkSpeed()` / `clearWalkSpeed()` 改为直接委托 `WalkSpeedBoost`，
  调用点（启用、每刻、关模块）一字未动；三个旧常量与随之空置的 import 删除。
  行为对玩家完全无差别 —— 仍是「模块开着就有 +40% 移速，关掉就摘掉」。

## 四、星露谷农场的挂点（与挖矿一一对应）

| 时机 | 位置 | 调用 |
| --- | --- | --- |
| 启用 | `StardewFarmModule#onEnable`（注册渲染之后、启动自检之前） | `WalkSpeedBoost.apply()` |
| 每刻补挂 | `StardewFarmModule#onTick`（`!isEnabled()` 之后、所有提前 return 之前） | `WalkSpeedBoost.apply()` |
| 停机 | `StardewFarmModule#onDisable`（选区取消之后） | `WalkSpeedBoost.clear()` |

两处位置是有意选的：

- 启用时**放在启动自检之前** —— 自检可能把模块自己关掉（非多人服等），那种情况下 `onDisable`
  才摘得干净，不会留下一个「模块已关、人还跑得快」的幽灵加成；
- 每刻补挂**放在所有提前 return 之前** —— 启动等待、区块未就绪这些分支也要保持移速，
  语义就是用户要的「模块开着就一直有这份加速」。

## 五、未做与待观察

- **未做**：没有加设置项（与挖矿同一口径：默认写在代码里）；没有动农场的寻路风格
  （Baritone 那组设置回到原样，农场寻路的 `allowBreak/allowPlace` 逻辑未受影响）。
- **待观察（要用户实机确认的两点）**：
  1. 进服开模块后走路是否明显变快、关模块是否立刻复原；
  2. **本服是否拉回**：星露谷那台服（jmy.seasonmc.xyz）如果出现「你移动得太快」被拉回 / 回弹，
     就把 `WalkSpeedBoost#LEVEL` 从 2 改成 1（+20%），改一处两个模块一起生效。

## 六、验证方式

1. `.\gradlew.bat build --console=plain -q` → **EXIT=0**（已过；中途一次 EXIT=1 是撤回 FarmNav
   那套时的残留调用，撤回后即通过）；
2. 实机：开星露谷农场 → 直着走几格对比关模块前后；开自动挖矿核对加成没有被叠成 +80%
   （两个模块同开时应与只开一个一样快）。

**状态**：代码完成、构建通过、客户端已重启（04:27），用户实机确认「完美」——**验收通过**。
两处待观察项在实测中都没有问题：本服不拉回（2 档 +40% 保持），关模块即复原。
