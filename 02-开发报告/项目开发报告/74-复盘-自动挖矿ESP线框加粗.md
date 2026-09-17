# 74 - 复盘：自动挖矿 ESP 线框加粗

- 日期：2026-09-17
- 触发：用户「把自动挖矿的 esp 加粗一下 跟男中音的做鲜明对比 我的 星露谷物语的就很粗」
- 涉及文件：`feature/mining/render/MiningPointRenderer.java`、`feature/mining/render/MiningBreakProgressRenderer.java`
- 验证：`./gradlew build --console=plain` → **BUILD SUCCESSFUL（EXIT=0）**；**实机待确认**

---

## 1. 取证（改动前的各处线宽）

线宽是各渲染类自己的常量，单位是 **GUI 缩放坐标**（`EspRenderer#pixelWidth` 再乘 GUI 缩放换成物理像素）：

| 渲染层 | 常量 | 改前 | 改后 |
| --- | --- | --- | --- |
| 挖矿点位框（矿点 / 容器 / 修复点） | `MiningPointRenderer.LINE_THICKNESS` | 1.5 | **3.0** |
| 挖矿岩浆框 | `MiningPointRenderer.LAVA_LINE_THICKNESS` | 2.5 | **4.5** |
| 挖矿进度框 | `MiningBreakProgressRenderer.LINE_THICKNESS` | 3.0 | **4.0** |
| 星露谷（农田 / 洒水器预览 / 错位格） | `StardewRenderState.LINE_THICKNESS`、`SprinklerEspRenderer.LINE_THICKNESS` | 1.5 | 未动 |
| 自动箱子 / 村民容器 / 管理员检测 | 各自 `LINE_THICKNESS` | 1.5 | 未动 |
| 男中音路径线 | Baritone `pathRenderLineWidthPixels` | **物理像素**口径 | 未动 |

**注意**：星露谷那两处常量与挖矿**完全相同（都是 1.5）**，所以「星露谷看起来更粗」不是常量差异造成的
（更可能是作物格子密集、框多且小造成的观感差异，或全局「线宽倍率」叠加）。无论如何，用户要的是
「挖矿 ESP 变粗、与男中音的细线形成对比」，这个改动与星露谷的值无关，故不动星露谷。

## 2. 改动

- 点位框 1.5 → **3.0**（翻倍）：与男中音路径线形成鲜明对比 —— 男中音那条按物理像素画（默认个位数），
  这里还要再乘 GUI 缩放，实际画面上明显更粗。
- 岩浆框 2.5 → **4.5**：维持「比点位框粗一档」的原有比例（1.5 : 2.5 ≈ 3.0 : 4.5）。
- 进度框 3.0 → **4.0**：点位框翻倍后同步上调，保持「进度框比点位框更醒目」的层次（该层次是同日早先
  用户反馈「框不明显」时建立的）。

三处都写了常量注释说明改动日期、原因与后续可调路径；**想再粗不必改代码**：
「ESP 全局设置 ▸ 外观 ▸ 线宽倍率」（0.5~3.0，默认 1.0）会在所有 ESP 层上再乘一档。

## 3. 待实机验证项

1. 矿点 / 容器 / 修复点框明显变粗，与男中音路径线一眼能区分。
2. 岩浆框仍比点位框粗一档；进度框仍比点位框粗。
3. 打开「线宽倍率」到 2.0 时整体继续变粗且不糊（倍率上限 3.0 未改）。

## 4. 遗留与注意

- 改动未 commit；调试会话 `mining-stall-after-rtp` 仍在进行（插桩代码尚在树内）。
- 重启开发客户端走优雅关闭（`taskkill /PID` 不带 `/F`），见 71 号第 3 节。
