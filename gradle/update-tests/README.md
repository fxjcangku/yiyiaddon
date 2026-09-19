# 更新功能回归

日期：2026-09-19

在项目根目录执行：

```powershell
./gradlew -I gradle/update-tests/run.gradle checkUpdateFlow renderUpdateHeader --console=plain
```

使用项目已有依赖，测试代码不进入 JAR。`checkUpdateFlow` 检查版本排序、测试版标识、发布筛选、会话提示与跳过版本持久化（包含保存失败）。测试数据只写入 `build/update-test-*` 临时目录，不触碰玩家配置。

`renderUpdateHeader` 使用真实 Skija 页头与按钮生成 `build/update-header-apple_dark.png` 和 `build/update-header-white.png`，并验证三个按钮的命中路由。它是离屏控件验证，不代表真实客户端弹窗和系统浏览器跳转已通过。
