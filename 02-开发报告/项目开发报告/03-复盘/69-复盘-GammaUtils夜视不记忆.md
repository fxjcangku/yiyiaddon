# 69 - 复盘：GammaUtils 夜视（V 键）每次进游戏都要重按

- 日期：2026-09-17
- 触发：用户「还有开发客户端 mod 伽马值那个每次出现进都要按 v 重新设置 没记住没记忆」
- 涉及对象：第三方 mod `gammautils 3.0.0`（`io.github.sjouwer.gammautils`）的运行配置，非本项目源码
- 验证：配置级实验（重启客户端 + 对比启动前后文件），**未实机确认视觉效果**（需用户看一眼进游戏是否已有夜视）

---

## 1. 取证

| 证据 | 内容 |
| --- | --- |
| 键位 | `run/options.txt:176` `key_key.gammautils.nightVisionToggle:key.keyboard.v` → **V 键 = 夜视开关**（不是 gamma 数值） |
| 配置 | `run/config/gammautils.json` 里 `nightVision.enabled = false`、`value = 100.0`、`toggledNightVision = 100`、`resetOnClose = false` |
| 时间戳 | 客户端 20:35:26 启动 → 该文件在 **20:36:07** 被 gammautils 自己写入一次（同批次还有 malilib / litematica / minihud 等启动期保存的配置） |
| 内容 | 即那次写入的内容里 `nightVision.enabled` 仍是 `false` |

结论：**配置文件里夜视状态就是「关闭」**，gammautils 每次启动按配置应用，所以每次进游戏都是暗的。
（`resetOnClose=false` 已排除「退出时重置」这条路。）

## 2. 改了什么

把 `nightVision.enabled` 由 `false` 改为 `true`（`value`/`toggledNightVision` 本就是满值 100，不动）：

- `run/config/gammautils.json`
- `run/_开发端配置备份/config/gammautils.json`（同步，避免恢复流程把它改回去）

## 3. 实验验证（配置级，已成立）

改前先停客户端（避免退出时按内存里的 false 覆盖），改后重启：

| 时刻 | 事件 |
| --- | --- |
| 20:37:40 | 客户端启动 |
| 20:37:49 | gammautils **再次写入** `gammautils.json`，其中 `nightVision.enabled` **仍为 `true`** |

→ 说明 gammautils **不会**在启动时把夜视重置为关闭，它只是「读配置 → 应用」。所以这次进游戏应当已经是亮的（夜视为开），不需要按 V。

## 4. 为什么会「没记住」

gammautils 只在**启动/退出/配置界面**等时机落盘；游戏内按 V 的切换不即时写文件。而这个开发客户端最近几次都是被
`Stop-Process -Force` 强杀的（没有走正常退出），所以「按 V 开启」这件事从来没写进 json ——
下一次启动读到的仍是 `false`，于是又得重按。

这次直接把 json 里的状态写成 `true`，等于把「按 V 开启」这一步预先做掉，且它启动时不会重置，即长期有效。

## 5. 使用说明与注意

- 想临时关掉夜视照旧按 V；但**游戏内按 V 的改动不会自动落盘**，所以下次启动仍是「开」（按 json 里的 true 走）。
- 若哪天又变成「每次都要按 V」：把 `run/config/gammautils.json` 里 `nightVision.enabled` 置回 `true` 即可；
  备份目录那份要同步改，否则恢复流程会把它改回 `false`。
- 更彻底的做法是别用强杀退出客户端（正常关窗退出会走保存流程），但本项目日常是强杀重启，故按上面这条手工维护。
- 本项只动运行配置、未改源码，无需编译。
