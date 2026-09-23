# 234 证据索引

对应报告：[234-追加-种子挖矿正式化第六阶段报告-SeedValidation与SUSPICIOUS语义定案.md](234-追加-种子挖矿正式化第六阶段报告-SeedValidation与SUSPICIOUS语义定案.md)（**正本只有本目录内这一份**；根目录旧副本已按项目规则「报告统一放 `02-开发报告/项目开发报告/`，禁止散落」清理）。

## 放在哪、为什么

| 位置 | 内容 | 说明 |
|---|---|---|
| `02-开发报告/截图/2026-09-23-种子挖矿第六阶段-ESP与验证UI/` | **定稿截图 15 张** | 按本项目截图画廊的既有命名（`日期-主题-NN-描述.png`）归档，和 `2026-09-16-光影实测`、`2026-09-21-主题改版-深空灰` 等同一层级 |
| `234-证据/E-原始日志/` | 原始 stdout / 对照文件 / 校验值 17 份 | 与 233-证据 同一约定：证据目录只放**原始数据**，不放精修图 |
| `234-证据/99-过程截图存档/` | 目视验收各轮次全量原始批次 267 张 | 含重复机位与早期迭代，**一张未删**；要复核像素时按文件名时间戳来这里取原图 |
| `234-证据/Z-证据索引.md` | 本文件 | 每个文件证明哪条口径、对应报告哪一节、怎么重跑 |

## 截图（`02-开发报告/截图/2026-09-23-种子挖矿第六阶段-ESP与验证UI/`）

| 文件 | 证明什么 | 报告 |
|---|---|---|
| `…-01-ESP-正坐标与负Y-绿框严丝合缝.png` | 画面 A：正坐标 + 玩家埋在 y=-40，绿框与真实钻石方块对齐、透墙可见 | 18、33、35、36 |
| `…-02-ESP-负X负Z-绿框.png` | 画面 B：负 X / 负 Z（区块 -3,-3）框体不镜像、不偏移 | 18、34 |
| `…-03-ESP-显示当前缺失-灰细框.png` | 画面 C：打开「显示当前缺失」后灰细框出现，与读数「缺失 12 / 调度敏感 26」一致 | 18、37 |
| `…-04-ESP-关闭显示预测钻石-世界已清空.png` | 画面 D：关闭显示后世界里一个框都不剩（关闭前渲染条目 4181 → 0） | 18、41 |
| `…-05-ESP-绿框与琥珀内圈-放大.png` | 调度敏感琥珀内圈内缩比例正确、无偏位 | 18、38 |
| `…-06-ESP-同机位调度敏感为0-内圈消失-放大.png` | 同机位、另一会话状态（调度敏感 0）下内圈消失 —— 内圈只由调度敏感驱动，不是贴图残留 | 18、38 |
| `…-07-ESP-被挖世界-灰细框与绿框-放大.png` | 被挖世界里灰框 + 绿框同屏（该世界出生点一圈已被 dev-only 移除约 40% 钻石） | 18、37 |
| `…-08-ESP-被挖世界-出生点一圈大面积缺失.png` | 同世界全景：候选 1097（已确认 658 / 缺失 439）与画面一致 | 18、37 |
| `…-09-验证UI-种子页顶部.png` | 面板顶部：状态条 / 页签条 / 配置 / 世界渲染，无重叠、无越界 | 18、38 |
| `…-10-验证UI-种子页中段-服务器种子验证.png` | **新增区块**：验证状态「已验证」/ 有效样本区块 49 / 独立确认样本 299 组 / 已确认候选 1165 / 当前缺失 9 + 「重新开始验证」+ 阈值说明 | 18.1、39 |
| `…-11-验证UI-种子页底部.png` | 面板底部（环境 / 预测状态 / 上一次预测结果）：长页用面板自身滚轮可达，无遮挡 | 18、38 |
| `…-12-光影开(BSL ULTRA)-正坐标绿框与琥珀内圈.png` | Iris 光影开（`[Iris] Using shaderpack: BSL_v10.1.3.zip`，PROFILE ULTRA）下方框不偏移、不穿屏、不闪深 | 18、37、39 |
| `…-13-光影开-显示当前缺失-灰细框.png` | 光影开启时缺失灰框同样贴合 | 18、37 |
| `…-14-光影关-正坐标绿框.png` | `enableShaders=false`（`Shaders are disabled because enableShaders is set to false`）下正常显示 | 18、37、40 |
| `…-15-光影关-显示当前缺失-灰细框.png` | 光影关闭时缺失灰框同样贴合 | 18、37 |

光影取证做法：用用户日常运行目录 `run-26.1.2`（103 Mods + Iris + Sodium + 9 个光影包）；
「关」= 备份 `config/iris.properties` 后临时把 `enableShaders` 改 false，跑完**已还原**（备份 `build/iris.properties.234bak`），未改动任何 Mod。

## 原始日志（`234-证据/E-原始日志/`）

| 文件 | 证明什么 | 报告 |
|---|---|---|
| `V1-验证回归-开发端-客户端stdout.txt` | 开发端全量验证回归：正确种子→已验证、被挖矿容错（20%/40%）、策略级用例、错误种子矩阵、清理矩阵 | 9、10、11、12、13 |
| `M1-233遗留MISSING定位-客户端stdout.txt` | 那 1 个 MISSING 的逐字段输出 + 卸载重载 / 断开重连两次复现 | 16.1、16.2 |
| `L1-…12345冷会话(-1,-1)=25候选9敏感.txt` | 233 遗留 #2 冷会话读数（25 / 9） | 17.1 |
| `L2-…12345预热(0,0)后=29候选7敏感.txt` | 同一目标预热邻居后变 29 / 7 → 根因 = **会话内装饰历史** | 17.1 |
| `L3-…区块(-402,382)冷会话=19候选.txt` | MISSING 所在区块冷会话候选数（19 / 调度敏感 1） | 16.2 |
| `L4-…观察格(-6417,-48,6123)冷会话=在候选集且调度敏感.txt` | 该格在冷会话下**是** SCHEDULE_SENSITIVE 候选 | 16.2 |
| `L5-…观察格预热(-401,382)后=不在候选集.txt` | 预热邻区块后该格**不在**候选集（19 → 18）→ A 类：合法调度 / 生成历史差异 | 16.2、16.3 |
| `A1-目视验收-ESP五画面-客户端stdout.txt` | 画面 A~F 时间戳与每屏读数（含帧级渲染采样） | 18、19.1 |
| `A2-目视验收-被挖世界与UI三段-客户端stdout.txt` | 被挖世界读数 + UI 三段滚动时间戳与验证读数 | 18、19.1 |
| `C1-光影开(BSL ULTRA)-客户端stdout.txt` | 光影开启环境日志（含 `Using shaderpack`） | 18、19.1 |
| `D1-光影关-客户端stdout.txt` | 光影关闭环境日志（含 `Shaders are disabled`） | 18、19.1 |
| `P1-开发parity-固定集对照.txt` | 开发侧权威门禁 `runClientSeedWorkerParityTest`（243 / 110 / 29+7 / Host Query 0） | 20 |
| `P2-生产smoke-worker对照-脚本输出.txt` | 正式 Jar 生产冒烟（workerParity）：Worker 启动 / 无开发目录泄漏 / 无残留 | 20.1 |
| `P3-生产smoke-验证回归-脚本输出.txt` | 正式 Jar 生产冒烟（validationRegression）脚本侧输出 | 20.1、20.2 |
| `P4-生产smoke-验证回归-客户端stdout.txt` | **正式发行产物**上的验证回归全文（全部判定：通过） | 20.2 |
| `P5-发行产物-校验值.txt` | `yiyiaddon-1.0-beta2-26.1.2.jar` 的 SHA-256 | 20.1 |
| `R1-半径6烟测-未完成-客户端stdout.txt` | 半径 6 烟测失败现场（客户端停在「加载地形」），如实留证，未伪造读数 | 19.2、22 |

## 复现命令

```powershell
# 验证回归（开发端，需先 runSeedWorkerServer；正式产物版见 production-smoke.ps1）
.\gradlew.bat runClientSeedValidationTest --console=plain

# 冻结数字权威门禁（单机即可）
.\gradlew.bat runClientSeedWorkerParityTest --console=plain

# 233 遗留：#1 MISSING 定位与复现 / #2 legacy 探针冷热对照
.\gradlew.bat runClientSeedMissingTest --console=plain
.\gradlew.bat seedWorkerLegacyProbe -PprobeArgs="build/probe-cold -1 -1 12345"        --console=plain
.\gradlew.bat seedWorkerLegacyProbe -PprobeArgs="build/probe-warm -1 -1 12345 - 0,0"  --console=plain
.\gradlew.bat seedWorkerLegacyProbe -PprobeArgs="build/probe-cold-w -402 382 20260922 - - -6417,-48,6123" --console=plain
.\gradlew.bat seedWorkerLegacyProbe -PprobeArgs="build/probe-warm-w -402 382 20260922 - -401,382 -6417,-48,6123" --console=plain

# 目视验收（截图由外部脚本 build/capture-seed-visual.ps1 按日志时间戳抓窗口）
.\gradlew.bat runClientSeedVisualTest       --console=plain   # 25565
.\gradlew.bat runClientSeedVisualMinedTest  --console=plain   # 25567（被挖世界）
.\gradlew.bat runClientSeedVisualShaderTest --console=plain   # run-26.1.2（100+ Mods + Iris）

# 发行链
.\gradlew.bat obfuscatedJar verifyObfuscatedJar --console=plain
powershell -NoProfile -File gradle/production-smoke.ps1 -ModJar build/release/yiyiaddon-1.0-beta2-26.1.2.jar `
    -SeedPocJvmArgs '-Dyiyiaddon.seedpoc.enabled=1','-Dyiyiaddon.seedpoc.workerParity=1','-Dyiyiaddon.seedpoc.exit=1'
```
