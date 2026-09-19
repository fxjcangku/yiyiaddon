# 134-复盘-静默容器机制全模块同步（统一闸门 SilentContainer）

日期：2026-09-19
项目：yiyiaddon（新项目，Fabric 26.1.2 客户端模组）
参照实现：自动附魔（`EnchantModule#onEvent` + `EnchantStateMachine#onScreenOpen`）
用户原话：
> 「把 自动附魔的打开 gui 发包那些功能 不关闭菜单能打开箱子 不抢鼠标的 代码 全部同步到 所有模块里面
> 凡是有开箱子的模块 那些都要 今天新建了很多 mixin 自动附魔 你整理一下移植 同步」

落点：`platform/container/SilentContainer`（新增）与 8 个开箱模块 + 各自的容器相位复位口。

一句话结论：**把「自己发包开箱」这件事拆成四条固定动作，收进一份共用闸门**，
八个模块按同一口径接上：取消容器界面（不抢鼠标）、放行玩家背包、玩家开背包时收掉我方静默容器并复位相位、
拦掉原版传送无条件顶上来的「加载地形中」。

---

## 一、改造前的不一致（逐模块对照）

| 模块 | 订阅 `SCREEN_OPEN` | 放行生存背包 | 放行创造背包 | 玩家开背包时收容器 | 拦 `LevelLoadingScreen` |
| --- | --- | --- | --- | --- | --- |
| 自动附魔 | 是 | 是 | 是 | 是（`guiPhase/guiTick/gearAnvilPhase` 归零） | 否 |
| 自动挖矿 | 是 | 是 | **否** | 是 | 是（全项目唯一） |
| 自动农场 | 是 | 是 | **否** | **否** | **否** |
| 星露谷农场 | 是（命令式） | 是 | 是 | 是（`interruptSilentContainer`） | **否** |
| 自动村民交易 | 是 | 是 | 是 | **否** | **否** |
| 图书管理员 | 是 | 是 | 是 | **否** | **否** |
| 自动登入 | 是 | 是 | **否** | **否** | **否** |
| 自动箱子 | **否** | — | — | — | — |

> 自动箱子改造前是唯一「有开箱行为却完全不订阅 `SCREEN_OPEN`」的模块：开箱时界面照弹、鼠标被抢。

另外，同一份 `isContainerScreen(类名)` 在六个模块里各写了一遍（第 169 条：同类逻辑只留一份）。

## 二、共用闸门：`platform/container/SilentContainer`

```java
SilentContainer.isPlayerInventory(类名)      // 生存背包 + 创造背包（两个都必须放行）
SilentContainer.isContainerScreen(类名)      // 是否原版容器界面（AbstractContainerScreen 判归属）
SilentContainer.isLevelLoadingHijack(类名)   // 玩家开着界面时，拦掉原版传送顶上来的「加载地形中」
SilentContainer.releaseSilentContainer()     // 收掉我方静默容器（只关真容器，不动玩家界面）
```

各模块统一后的 `onOpenScreen` 骨架（顺序固定）：

```java
String cls = event.payload();
if (SilentContainer.isLevelLoadingHijack(cls)) { event.cancel(); return; }   // ① 传送不顶掉玩家界面
if (SilentContainer.isPlayerInventory(cls)) { 收容器 + 复位本模块相位; return; } // ② 背包永远放行
if (SilentContainer.isContainerScreen(cls) && 本模块正在做容器操作()) event.cancel(); // ③ 静默，不抢鼠标
```

**为什么玩家开背包必须收容器（不是「不关」）**：静默模式下容器界面被取消，走不到
`AbstractContainerScreen#onClose`，服务端会一直认为容器开着；此时玩家背包里的点击会按**容器的
`containerId`** 发出去 → 错位、丢物品、背包点不动。收容器本身不会动玩家界面，这一点由
`LocalPlayerScreenGuardMixin` + `EventDispatcher#closeScreenUnlessPlayerOwned`（见 128 号复盘）保证。

**为什么创造背包不能漏**：创造模式下 `InventoryScreen.init` 会立刻切到 `CreativeModeInventoryScreen`，
拦掉后者会让前者停在未初始化状态（`RecipeBookComponent.book` 为 null）而闪退。

## 三、逐模块改动

| 位置 | 改动 |
| --- | --- |
| `platform/container/SilentContainer`（新增） | 四条判据合一份，删掉六个模块里的私有 `isContainerScreen` |
| `feature/mining/AutoMinerModule#onOpenScreen` | 判据切共用件；补创造背包放行；玩家开背包 → `getContainer().closeContainer()`（走挖矿自己的收箱口，除关菜单外还要清开箱重试 / 精确补组相位） |
| `feature/mining/fsm/MiningStateMachine`（卸货 / 补给两处） | 补「玩家自己开着容器界面 → 只等不做」；非容器界面（游戏菜单 / 控制台）仍按原口径「周期收容器但不打断流程」 |
| `feature/autofarm/AutoFarmModule#onOpenScreen` | 判据切共用件；补创造背包放行、补 `LevelLoadingScreen` 拦截、补玩家开背包时收容器 |
| `feature/autofarm/task/ContainerTask#tick` | 玩家自己开着容器界面 → `IN_PROGRESS`（只等不做）：否则物流任务会在玩家看背包时静默重开箱子 |
| `feature/stardew/StardewFarmModule#onOpenScreen` | 判据切共用件；补 `LevelLoadingScreen` 拦截（`interruptSilentContainer` 保留原样） |
| `feature/stardew/task/StardewContainerLogistics#interactLogistics` | 玩家自己开着容器界面 → 直接返回（只等不做），不推进开箱事务 |
| `feature/villager/AutoVillagerTradeModule#onOpenScreen` | 判据切共用件；玩家开背包 → `fsm.onPlayerInventoryOpened()` |
| `feature/villager/fsm/VillagerTradeFSM#onPlayerInventoryOpened`（新增） | 收容器 + `supplyService/unloadService.reset()`；`SUPPLY_TAKE/UNLOAD_TAKE` 退回对应的 `*_OPEN`（不退回会一直等一个已被收掉的容器到超时停机） |
| `feature/villager/fsm/VillagerSupplyRunner#holdForPlayerScreen`（新增） | 补给 / 卸货的四个状态「只等不做」，等待期间**原状态重入只清计时**，玩家看背包不算卡住 |
| `feature/librarian/AutoLibrarianModule#onOpenScreen` | 判据切共用件；玩家开背包 → `orchestrator.onPlayerInventoryOpened()` |
| `feature/librarian/service/LibrarianOrchestrator#onPlayerInventoryOpened`（新增） | 收容器；`OPEN_TRADE/WAIT_TRADE_SCREEN/READ_TRADES/CHECK_ENCHANTMENT` → `RESET`，交易执行相位 → `END_VILLAGER_CYCLE`（都取自状态机既有合法转换表） |
| `feature/librarian/service/LibrarianOrchestrator#tick` | 玩家自己开着容器界面 → 冻住状态机：**状态计时一并冻住**，玩家看背包期间不会等到超时 `fail` 而把模块停掉 |
| `feature/autologin/AutoLoginModule#onOpenScreen` | 判据切共用件；补创造背包放行；自用路线运行中玩家开背包 → 收掉静默容器（路线各态自己会重开菜单） |
| `feature/autochest/AutoChestModule#onOpenScreen`（补齐缺失） | 三条一起补：拦 `LevelLoadingScreen`、玩家开背包收容器 + `interaction.reset()/stateMachine.reset()`、`isInteractingWithTarget()` 时才静默 |
| `feature/autochest/AutoChestModule#onTick` | 玩家自己开着容器界面 → 不推进状态机（计时冻住，不会把箱子误判成「开箱失败」而跳过） |
| `feature/enchant/EnchantModule#onEvent` | 补 `LevelLoadingScreen` 拦截（参照实现本身也漏了这条） |
| `feature/enchant/fsm/EnchantStateMachine` | 判据切共用件（相位复位逻辑原样保留：`guiPhase/guiTick/gearAnvilPhase` 归零） |
| `feature/mining/service/MiningContainer#isOperatingContainer`（新增） | 静默门控判据：`openingPos != null`（发包开箱后到收箱前）。不能用 `isContainerOpen()` —— 玩家自己开的箱子同样 `containerId != 0`，那样又把玩家的箱子拦掉 |
| `feature/mining/AutoMinerModule#onOpenScreen` | ③ 加门控：只有 `isOperatingContainer()` 时才静默 |
| `feature/villager/fsm/VillagerTradeFSM#isUsingContainerScreen`（新增） | 静默门控判据：`OPENING_MENU / TRADING / CLOSING_MENU / SUPPLY_OPEN / SUPPLY_TAKE / UNLOAD_OPEN / UNLOAD_TAKE`（「等待玩家」不含在内 —— 那个相位本就是等玩家自己补给 / 卸货） |
| `feature/villager/fsm/VillagerTradeFSM#tickOpeningMenu` | 补「玩家自己开着容器界面 → 只等不做」（原状态重入只清计时）：补门控后玩家能带着自己的箱子界面推进到本相位，此时若继续交互村民会把 `containerMenu` 换成村民菜单 → 玩家点击错位 |
| `feature/villager/AutoVillagerTradeModule#onOpenScreen` | ③ 加门控：只有 `fsm.isUsingContainerScreen()` 时才静默 |
| `feature/librarian/service/LibrarianOrchestrator#isUsingTradeScreen`（新增） | 静默门控判据：交易界面相关的 9 个相位 |
| `feature/librarian/AutoLibrarianModule#onOpenScreen` | ③ 加门控：只有 `orchestrator.isUsingTradeScreen()` 时才静默（模块只是启用、没在跑交易时不再拦玩家的箱子） |

## 四、边界（明确写清，避免预期错位）

1. **只在「没有玩家容器界面」时才动容器**：玩家看背包（或自己开的箱子）期间，各模块一律「只等不做」，
   玩家关掉界面自然续上；游戏菜单 / 聊天 / 本模组控制台这些**非容器界面**不拦流程（静默开箱照跑）。
2. **背包永远放行**，包括创造背包；但放行的同时会把我们的静默容器收掉（只收我们的，玩家界面一动不动）。
3. **玩家按 ESC 关菜单不受影响**：走 `Screen#onClose`，不经过 `SCREEN_OPEN` 与两个 `@Redirect`（见 128）。
4. **「只等不做」不引入新的超时停机**：三处冻结（自动箱子 / 图书管理员）直接不推进状态机，
   计时一并冻住；村民的四个物流态用「原状态重入只清计时」；挖矿 / 星露谷 / 农场 / 自动附魔按各自
   原有的玩家界面守卫「只等不做」，不记失败、不累计超时。
5. **不改的地方**：星露谷仍用命令式 `ClientEventBus.subscribe` 订阅（与本次主题无关，不做重构）；
   自动附魔的相位复位项维持原样（它是本次的参照实现，不动其已验证行为）。
6. **自动村民交易的「交易中」相位例外**：交易依赖村民菜单（`MerchantMenu`）本身，菜单一收就无法继续，
   所以玩家在交易中开背包仍按模块既有语义播报「检测到玩家打开背包，交易已中断」并停机
   （旧代码里就有这条判定，本次只是让它真的能把菜单收干净）；只有补给 / 卸货两个物流相位是「只等不做」。

## 五、静默门控补齐（用户 2026-09-19 拍板「加上吧」）

**问题**：改造前挖矿 / 村民交易 / 图书管理员是「只要模块开着，任何容器界面一律拦掉」——
玩家挂机时手动去开自己的箱子，右键后**界面根本不出现**（被当成「模块自己在开箱」静默了）。
另外五个模块本来就有门控，不受影响。

**修法**：给这三个模块各加一个「本模块当前是否在做容器操作」的判据，③ 那一行改成
`涉及门控 && SilentContainer.isContainerScreen(cls)`：

| 模块 | 门控判据 | 覆盖范围 |
| --- | --- | --- |
| 自动挖矿 | `MiningContainer#isOperatingContainer()`（`openingPos != null`） | 卸货 / 补给的整段开箱事务 |
| 自动村民交易 | `VillagerTradeFSM#isUsingContainerScreen()` | 打开交易界面 / 交易中 / 关界面 / 补给 / 卸货 |
| 图书管理员 | `LibrarianOrchestrator#isUsingTradeScreen()` | 打开 / 等待同步 / 读报价 / 购买流程（9 个相位） |

**顺带补齐的一处相位守卫**：村民的「打开交易界面」相位补了「玩家自己开着容器界面 → 只等不做」——
补门控后玩家可以带着自己的箱子界面走到这个相位，那时若继续 `interact` 村民，服务端会把
`player.containerMenu` 换成村民菜单，而玩家看的还是自己那个箱子的界面，他接下来的点击就会按
村民菜单的 `containerId` 发出去（错位、丢物品）。挖矿 / 自动农场 / 星露谷 / 自动箱子 / 图书管理员
本来就有等价守卫（或整体冻结状态机），这次只有村民缺这一处。

**边界与优先级口径（用户 2026-09-19 拍板「模块运行优先」）**：

- 模块**没在**做容器操作 → 玩家手动开的箱子**照常显示**，模块不管不问、不停机、不播报；
- 模块**正在**用容器 / 菜单（③ 门控为真）→ 玩家手动开的箱子**不给打开**：界面压掉 + 容器收掉 +
  动作栏提示，**模块继续跑**（不会停机、不会报警）；
- 唯一会「播报 + 停机」的是村民「交易中」相位按 E 开**背包**（见上文边界第 6 条），与手动开箱无关。

**玩家手动开箱被拒（用户 2026-09-19 拍板）**：模块正在用容器 / 菜单时，**玩家手动开的箱子不给打开** ——
界面压掉、**容器本身也收掉**，动作栏打一行 `§e⚠ 容器界面发包无法打开 §8▸请关闭模块重试`。
我方自己开出来的界面静默压掉，不打提示。

- **为什么必须收掉容器，不能只压界面**：只压界面的话服务端那边玩家的箱子其实已经开了，
  `player.containerMenu` 被换成玩家的箱子，而模块的「容器已打开」判据只看 `containerId != 0` ——
  模块会把这个箱子当成自己的目标容器去搬东西（取物模式从玩家箱子里取、存物模式往玩家箱子里塞）。
  收掉之后模块下一 tick 自然重开自己的目标容器（各模块已有「容器意外关闭 / 未就绪 → 重开」路径）。
  **本次同时把第六节那条风险闭环掉了。**
- **怎么区分是谁开的**：`SilentContainer.markOwnContainerOpen()`（我方每次发包开容器 / 开菜单前打点）+
  40 刻窗口内算「我方开的」（`rejectPlayerContainer()` 里判）。窗口放宽是故意的：窗口内误判成
  「我方的」只是少一次拒绝，不会误收我方容器；反之漏打点才会「每开一次箱子弹一次提示」，
  所以**所有我方开容器 / 开菜单的入口都必须打点**（本次已打满：挖矿 `MiningContainer#openContainer`、
  自动箱子 `ChestInteractionService#open`、农场 `ContainerTask#tryOpen`、星露谷
  `StardewContainerLogistics` 开箱步、村民补给 / 卸货 / 打开交易界面、图书管理员
  `MerchantTradeOps#open`、自动附魔 `EnchantContainer#interactBlock`、自动登入
  `LeyuanRouteService#openMenu` 与 `SubserverRouteService`）。
- 通道：`ClientChat#overlay`（`Gui#setOverlayMessage`）—— 不刷聊天框、不进聊天历史。
- 文案逐字：`§e⚠ 容器界面发包无法打开 §8▸请关闭模块重试`（用户给定，禁止改写）。

## 六、原风险与闭环

**风险**：模块正在用容器时玩家手动开箱，服务端那边玩家的箱子其实已经开了，模块可能把它当成自己的
目标容器去搬东西。改造前就存在（挖矿 / 村民 / 图书管理员一直压掉所有容器界面；自动箱子 / 农场 /
星露谷在各自物流期间也压掉），**不是本次引入**。

**闭环方式**：第五节那条「玩家手动开的容器界面 → 压掉 + 收掉容器 + 提示」。
判定「是不是玩家开的」用「我方发包开箱打点 + 40 刻窗口」，判定为玩家的就当场把容器收掉，
所以模块再也不会拿到玩家的箱子当目标。**遗留风险只剩一个窄窗口**：我方刚发包、界面还没到的
那 1~3 刻内玩家也右键了箱子 —— 这种并发下界面仍按「我方的」处理（不打提示、不收容器）。

## 七、遗留与已知行为（按开发习惯第 230 条登记）

1. **自动村民交易「交易中」相位**在玩家开背包时仍按既有语义停机（见上文边界第 6 条），
   不是本次引入的行为，但本次改动让这条判定真的会触发（以前菜单收不掉、反而绕过它）。
2. **自动登入原有一个真 bug 被顺手修掉**：改造前只放行 `InventoryScreen`、没放行
   `CreativeModeInventoryScreen` → 创造模式下按 E 会因 `RecipeBookComponent.book` 未初始化而**闪退**；
   现已并入 `SilentContainer.isPlayerInventory` 统一放行。

## 八、验收

`.\gradlew.bat build --console=plain` → BUILD SUCCESSFUL（改动中发现并修掉一处
`VillagerTradeFSM#onPlayerInventoryOpened` 可见性问题：跨包调用需 `public`）。

实机待验（按用户口径逐条对照）：
- 八个模块自己开箱 / 开村民界面 / 开讲台菜单时**不抢鼠标、界面不弹**；
- 挂机时按 E 打开背包：背包正常可用（点击不错位），关掉后自动化自动续上；
- 挂机时按 ESC 开游戏菜单 / 打开本模组控制台：菜单不被关，自动化照跑；
- 被传送 / 重生时玩家界面不被「加载地形中」顶掉（沿用 128 号复盘的机制）。
