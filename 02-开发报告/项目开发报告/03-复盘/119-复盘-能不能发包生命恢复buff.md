# 119-复盘-能不能发包生命恢复 buff（结论：不能；曾加的丢药水已按用户要求回退）

日期：2026-09-19（2026-09-19 修订：回退丢药水实现）
项目：yiyiaddon（新项目，Fabric 26.1.2 客户端模组）
用户问题原话：「能不能发包生命恢复 buff？能的话帮我实现」

---

## 一、结论

**不能**。26.1.2 的 `net/minecraft/network/protocol/game/` 下全部 `Serverbound*` 包（55 个）逐条过了一遍，
没有「设置 / 添加药水效果」这一类包；`ServerGamePacketListenerImpl` 的 handler 清单里也没有对应入口
（`handleSetBeaconPacket` 只是信标界面里的效果选择，要求玩家当前开着信标菜单、且有满级信标与材料，
不是「凭手上一个包就能上 buff」的手段）。

生命恢复（Regeneration）只能由服务端在结算「你使用了某个自带效果的道具」时给出，
例如金苹果 / 附魔金苹果 / 再生药水；客户端能发的只有 `ServerboundUseItemPacket`（用道具），
没有「给我加效果」这一手。

## 二、本轮改动：把「丢药水」整段回退

用户 2026-09-19 明确「我要自动回血，不是丢药水」，因此上一版加的「发包朝脚下丢喷溅再生 / 瞬间治疗药水」
**整段删除**，恢复到「只做发包连吃（吃食物回血）」的状态：

| 文件 | 回退内容 |
| --- | --- |
| `feature/mining/service/MiningContainer.java` | 删除 `throwHealPotion()` / `findHealPotionSlot()` / `hasPotionEffect()`、`POTION_*` 三个常量、`potionThrowCooldown` 字段、`reset()` 里的清理行，以及随之引入的 7 个 import（`Holder`、`ServerboundSetCarriedItemPacket`、`MobEffect*`、`SplashPotionItem`、`PotionContents`） |
| `feature/mining/fsm/MiningStateMachine.java` | 优先级 2.6 恢复为单一分支（血量未满 + 饱食度未满 + 有食物 → 停 Baritone 进进食态发包连吃），删掉丢药水调用与 `hasEffect(REGENERATION)` 判据；删掉 `MobEffects` import；类注释差异清单同步恢复 |

回退后自动回血的实现与开发报告 118 一致：**血量未满 且 饱食度未满 且有食物 → 进进食态发包连吃**，
把饱食度顶满并带上饱和度，靠原版自然再生（饱食度 ≥ 18、饱和度 > 0 时 10 刻 1 点）把血补回来。

## 三、构建

- `.\gradlew.bat build --console=plain` → 见本轮构建结论（回退后重新构建通过）。
