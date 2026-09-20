# 152-复盘-本地开发客户端「Sampler0 已被关闭」崩溃：把那次绘制跳过，而不是整局崩掉

日期：2026-09-20
分支：master（业务唯一真源，按第 231 条；26.2 分支另行 cherry-pick）
涉及文件：`compat/MissingSamplerFallback.java`、`mixin/client/GlCommandEncoderMixin.java`、
`src/main/resources/yiyiaddon.accesswidener`

---

## 一、症状

本机开发客户端（`.\gradlew.bat runClient`，26.1.2）启动后停在**没有世界**的界面上，
约 20-30 秒必崩，日志最后两行固定是：

```
[Render thread/ERROR] (Minecraft) Can't ping rubiksmp.net: 连接中断
[Render thread/ERROR] (Minecraft) Unreported exception thrown!
java.lang.IllegalStateException: Texture view Sampler0 (983) has been closed!
	at knot//com.mojang.blaze3d.opengl.GlCommandEncoder.trySetup(GlCommandEncoder.java:429)
	at knot//com.mojang.blaze3d.opengl.GlCommandEncoder.executeDraw(GlCommandEncoder.java:327)
	at knot//com.mojang.blaze3d.opengl.GlRenderPass.drawIndexed(GlRenderPass.java:143)
	at knot//net.minecraft.client.gui.render.GuiRenderer.executeDraw(GuiRenderer.java:502)
	at knot//net.minecraft.client.gui.render.GuiRenderer.draw(GuiRenderer.java:265)
	at knot//net.minecraft.client.gui.render.GuiRenderer.render(GuiRenderer.java:168)
```

次数：2026-09-20 五次（23:42、23:45、23:48、23:49、23:53），2026-09-16 四次，全是同一栈。
**栈里没有本模组任何一帧**，也不是 GL 回读越界（`nvoglv64.dll`）那一类。

## 二、取证

- 崩前时序固定：资源重载收尾（ModernUI / ETF / Punchy / ModernFix 同时重载，
  `Cleared mixin data structures`）→ 20-30 秒后服务器列表 ping `rubiksmp.net` 失败
  （`连接中断`）→ **同一个 tick** 里 GUI 线程拿着重载前那张已关闭的纹理视图去画。
- 崩的那一局**没有世界**（crash-report 里没有 Level 段），崩溃点全在 GUI 通道。
- 时间上早于本次任何改动（09-16 就有四次），所以不是新引入的。

## 三、原版机制（按 26.1.2 字节码核对，非猜测）

`GlCommandEncoder#trySetup` 每次 draw 前跑一遍校验，其中采样器这段是：

```java
for (Map.Entry<String, Uniform> e : pass.pipeline.program().getUniforms().entrySet()) {
    if (!(e.getValue() instanceof Uniform.Sampler)) continue;
    String name = e.getKey();
    TextureViewAndSampler bound = pass.samplers.get(name);
    if (bound == null) throw new IllegalStateException("Missing sampler " + name);
    GlTextureView view = bound.view();
    if (view.isClosed()) throw new IllegalStateException(name + " (" + view.texture().getLabel() + ") has been closed!");
    ...
}
```

即：**当前着色器程序声明的采样器槽位**，在 RenderPass 里绑着一张**已被删除**的纹理
（或采样器本身已关闭），原版的选择是抛异常、结束进程。

而 `trySetup` 的返回值是「这次绘制能不能做」：`executeDraw` 里是
`if (!trySetup(pass, emptyList)) return;` —— 返回 `false` 就是**只跳过这一次绘制**。

## 四、处置：把「必崩」降级为「少画一笔」

不追查「谁在关纹理」（那是重载/替换纹理的一方与 GUI 渲染之间的时序问题，本机 295 个模组
的环境，且与本模组无关），而是在原版校验之前按原版口径自己核一遍，命中就让这次绘制跳过。

| 文件 | 改动 |
| --- | --- |
| `compat/MissingSamplerFallback.java` | 由「只补绑」变为两条职责，`apply` 改为返回布尔：先两段式找「程序真要、但绑的纹理/采样器已关闭」的槽位（先扫绑定表，通常 1-3 条，空表直接过；命中后再按原版口径核对 `pipeline.program().getUniforms()` 里它是不是 `Uniform.Sampler`），命中则记一条带纹理标签的告警并返回 `false`；否则走原有的 Sampler1/Sampler2 补绑并返回 `true` |
| `mixin/client/GlCommandEncoderMixin.java` | `@Inject` 加 `cancellable = true`；`apply` 返回 `false` 时 `callbackInfo.setReturnValue(false)`（= 原版自身的「跳过本次绘制」语义） |
| `yiyiaddon.accesswidener` | 补 `accessible class GlRenderPass$TextureViewAndSampler` 与 `accessible field GlRenderPass pipeline`（读管线只为照原版口径核对槽位） |

**不变量（决定这不会改坏正常流程）**

1. 只有「当前程序声明的采样器槽位确实绑着已关闭的纹理/采样器」才跳过 —— 这条路径上原版必抛异常，
   所以不存在「本来能画却被我们跳过」的情况。
2. 程序用不到的槽位（例如自己多绑了一张废弃纹理）一律不理会，照原样继续画。
3. 两条兜底都没命中时，原版流程一字不改；不开光影、纹理正常时与原版完全一致。
4. 兜底自身抛任何异常：只记一条日志并永久停用，绝不再抛第二遍（原有边界保持）。
5. 告警按纹理标签去重、上限 16 条，不刷日志、不做无界累积。

## 五、验证

- `.\gradlew.bat compileJava --console=plain -q` → **EXIT=0**。
- 启动客户端后未再复现该崩溃；但**这一局进了单人世界，与崩溃场景（无世界停在服务器列表）
  不同**，因此本次启动不能算验收。
- 待验收：启动后**不进世界**，在多人游戏界面等到 `rubiksmp.net` ping 失败那一刻 ——
  预期是客户端不崩、日志出现
  `采样器 Sampler? 绑定的纹理（...）已被关闭，本次绘制按「跳过」处理`，
  画面最多少一笔。
