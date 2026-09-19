# 136-复盘-迁移到 Minecraft 26.2（双版本并行：26.1.2 留 master，26.2 走 port/26.2）

> 编号说明：本报告最初落盘为 135，与同日本项目另一条工作线的
> `135-复盘-自动附魔切模式仍跑旧模式词条与潜影盒成品箱反复打开.md` 撞号（那份 21:12 落盘在前，
> 本份 21:28 在后），故按编号接续规则改为 136。

日期：2026-09-19
项目：yiyiaddon（新项目，Fabric 客户端模组；本次新增第二条版本线）
参照实现：无（首条跨版本线）
用户原话：
> 「我想先适配26.2」
> 「等等我想说的是出2个版本 你是不是误会了 26.1.2 保留 出一个26.2的版本懂吗 不是把我的26.12升级成26.2」
> 「继续，全部按新口径适配」

落点：`master`（26.1.2 主线，未动）+ `port/26.2`（26.2 版本线，本次全部改动）
外部依赖：`maven-repo/baritone/baritone-fabric/1.19.0`（新增）、`gradle.properties`、`build.gradle`

一句话结论：**26.2 不是「小更新」**。官方只放开了 GL 的备用后端（默认仍是 OpenGL，Skija 链路可用），
但把「常量从哪来」重排了一遍：切屏入口从 `Minecraft` 搬进 `Gui`、16 色系字段收成 `ColorCollection`、
实体类型常量拆成 `EntityTypeIds` 的 `ResourceKey`、物品/方块标签拆出 `BlockItemTagId`。
编译期共 399 个错误，全部按新口径逐条改完，**0 残留**；`runClient` 已能在 26.2 上启动并自动进服，
Baritone 26.2 构件正常加载并写世界缓存，**用户实机确认界面与功能「都还在 完全没问题」（2026-09-19）**。

---

## 一、目标形态（用户拍板）

同一个仓库两条版本线，**不复制工程目录**：

| 线上 | 分支 | MC | Fabric API | Baritone | 状态 |
| --- | --- | --- | --- | --- | --- |
| 26.1.2 主线 | `master` | 26.1.2 | 0.155.2+26.1.2 | 1.18.0 | 本次**未动一行** |
| 26.2 版本线 | `port/26.2` | 26.2 | 0.161.0+26.2 | 1.19.0 | 本次全部改动在此 |

`maven-repo` 里两版 Baritone 并存，两条线各取各的。业务代码只有一份真源（在 `master`），
26.2 分支上只积累「版本适配差异」，以后修 bug 在 `master` 改完 cherry-pick 过来。

---

## 二、取证方法（本次能一次收敛的关键）

26.1 起官方不再混淆，**26.2 官方客户端 jar 里就是真名**。所以移植前先把
`client-26.2.jar`（39 MB，来自 piston-meta 版本清单）拉到 `_tmp262/`，用 `javap` 与
26.1.2 的 loom 缓存合并 jar **逐类逐成员对照**，再动手。这一步把「猜 API 变了什么」变成
「读字节码」，也是本次 399 条错误能一次全部归类的原因。

对照得出的结论里，有几条与 Fabric 官方博客**不一致**（博客只提了 `BlockIds`/`ItemIds`）：

- `EntityType` 在 26.2 只剩 `CODEC` / `STREAM_CODEC` 两个字段，158 个实体类型实例全搬到
  `net.minecraft.world.entity.EntityTypeIds`，且**类型是 `ResourceKey<EntityType<?>>` 而非实例**；
- 16 色系字段（`BANNER` / `BED` / `WOOL` / `CARPET` / `GLAZED_TERRACOTTA` / `DYED_TERRACOTTA`）
  收成 `ColorCollection<T>` record，按 `white()`…`black()` 取；
- `BlockTags` 里的煤/红石/青金石/钻石/绿宝石五个 `*_ores` 标签移到了
  `BlockItemTags`，值是 `BlockItemTagId`（方块侧 + 物品侧成对），要 `.block()` 取回 `TagKey<Block>`；
  铜/铁/金三个仍留在 `BlockTags`。

---

## 三、渲染链：5 处断点（全部有等价替代物）

| 26.1.2 | 26.2 | 影响面 |
| --- | --- | --- |
| `Minecraft#getMainRenderTarget` | `Minecraft.gameRenderer.mainRenderTarget()` | `SkiaGlBackend`、`SkiaBlurRenderer`（3 文件 6 处） |
| `GlTexture#getFbo(DirectStateAccess, GpuTexture)` | `GlDevice.frameBufferCache().getFbo(DirectStateAccess, List<FrameBufferAttachment>, FrameBufferAttachment)` | `SkiaGlBackend#mainFramebufferId` |
| `RenderTarget#blitToScreen()`（呈现） | `Minecraft#renderFrame` 里的 `windowSurface.blitFromTexture(命令编码器, 主颜色纹理视图)` + `GpuSurface#present` | `RenderTargetMixin` → **改名 `MinecraftFramePresentMixin`**，注入点改为该 `blitFromTexture` 调用之前 |
| `net.minecraft.client.gui.GuiRenderer#render(GpuBufferSlice)` | 同名方法**去掉参数** | `GuiRendererMixin`（包路径未变） |
| `Minecraft#setScreen` / `Minecraft.screen` | `Minecraft.gui` → `Gui#setScreen` / `Gui#screen()` | 123 处 `setScreen` + 111 处 `screen` 字段 + `MinecraftSetScreenMixin` + `LocalPlayerScreenGuardMixin` 两处重定向 |

**`RenderTargetMixin` 为什么必须改名**：26.2 的 `RenderTarget#blitAndBlendToTexture` **不是**呈现路径
（它由 `LevelRenderer` 用于半透明层回填主画面），拿它当注入点会画错帧。真正的呈现搬到了
`Minecraft#renderFrame`，注入目标类从 `RenderTarget` 变成 `Minecraft`，旧名字会变成谎话，
因此按第 158 / 208 条改名并同步 `yiyiaddon.mixins.json`。

**旧路径为什么仍然成立**：26.2 新增 Vulkan 只是**备选**后端。字节码显示
`PreferredGraphicsApi#getBackendsToTry()` 只在显式选 `VULKAN` 时才把 Vulkan 排前，
默认值 `DEFAULT` 与 `OPENGL` 都是 `GlBackend`（OpenGL）优先 —— 实测启动日志为
`Using graphics backend OpenGL`。因此 Skija 直连 GL 的整条链在默认配置下继续可用。

`mainFramebufferId` 新路径的等价性（静态证据）：`GlDevice#createTexture` 在 GL 后端造出的就是
`GlTexture`，而 `GlTexture extends GpuTexture implements FrameBufferAttachment`，
所以 `instanceof FrameBufferAttachment` 必然命中，`FrameBufferCache` 拿到的是同一组 attachment。

---

## 四、其余三类差异（编译期 399 条，全部改完）

**A. 切屏改名（机械，两遍正则）**：第一遍覆盖 `client` / `mc` / `minecraft` / `this.minecraft` /
`Minecraft.getInstance()` 五种接收者（96 文件 / 178 行）；第二遍补 `owner.client()`、`host.client()`
这类**方法调用接收者**（25 行）。注释行一律跳过人工复核 —— 其中 6 处是「旧项目 `mc.setScreen(null)`」
一类的**历史记录**，按第 158 条**不得改动**；另 5 处描述当前行为的已同步更新。

**B. 16 色系字段集合化（≈96 处引用）**：`Items.WHITE_BANNER` → `Items.BANNER.white()`；
`Items.WHITE_TERRACOTTA` → `Items.DYED_TERRACOTTA.white()`；`BED` / `WOOL` / `CARPET` /
`GLAZED_TERRACOTTA` 同理。

**C. 实体类型常量拆分（24 处 / 3 文件）**：`EntityType.XXX` → `EntityIdentifier.typeOf(EntityTypeIds.XXX)`。
解析入口收在既有适配层 `platform/identity/EntityIdentifier` 里（第 169 条：只留一份），
并另给批量入口 `typesOf(...)`（跳过未注册键，避免把 null 塞进 `Set.of` 这类拒空容器）。
31 个用到的常量**逐个核对过全部存在于 `EntityTypeIds`**，无一被删。

**D. 零散 API 迁移**：

| 26.1.2 | 26.2 | 落点 |
| --- | --- | --- |
| `GameRenderer#getMainCamera()` | `mainCamera()` | `RenderCamera` |
| `Gui#setOverlayMessage(...)` | `Gui.hud.setOverlayMessage(...)` | `ClientChat` |
| `Minecraft#getOverlay()` | `Minecraft.gui.overlay()` | `UpdateService` |
| `Gui#getBossOverlay()` | `Gui.hud.getBossOverlay()` | `BossBarProbe` |
| `LevelChunkSection.SECTION_WIDTH/HEIGHT` | `SectionPos.SECTION_SIZE` | `BlockTargetScanner`（4 处，16³ 语义不变） |
| `I18n#exists(String)` | `Language.getInstance().has(String)` | `WorldContextFormatter` |
| `ClientboundSetPlayerTeamPacket$Parameters#getDisplayName/getPlayerPrefix/getPlayerSuffix` | record 访问器 `displayName()/playerPrefix()/playerSuffix()` | `EventDispatcher` |
| `Connection#isEncrypted()` | 见下 | `AutoLoginModule` |
| `Minecraft#getVersionType()` | 见下 | `ClientIdentity` |

**两处没有 1:1 替代物，按「同源同义」重建**（第 167 条：先取证再动手）：

1. **`Connection#isEncrypted()`（判断是否正版验证服）**：26.2 的 `Connection` 里已**不存在**
   表示加密状态的布尔字段，只剩 `setEncryptionKey`。取证 `setEncryptionKey` 字节码可知，
   它把 `CipherDecoder` 插在管线 `splitter` 之前、命名为 `decrypt`。
   于是改为「管线里有没有 `decrypt` 处理器」——与旧布尔值同源同义。
   读的是包私有字段 `channel`，已按本模组既有机制写进 `yiyiaddon.accesswidener`。

2. **`Minecraft#getVersionType()`（release / snapshot）**：26.2 已无 `VersionType`。
   改用 `SharedConstants.getCurrentVersion().stable()` 判定（正式版 → `release`，其余 → `snapshot`）。
   该方法当前**无调用方**，按第 163 条未删除，只做等价适配。

---

## 五、Baritone 26.2 构件（自建）

上游 `cabaletta/baritone` 存在 `26.2` 分支，`mod_version=1.19.0`。
按 `maven-repo` 里 1.18.0 的同一口径（POM 内已记载）自建：

```powershell
# 源码取自 GitHub 归档 zip（git 的 schannel 通道在本机握手失败）
#   https://github.com/cabaletta/baritone/archive/refs/heads/26.2.zip
#   commit 9fadf7cf95293d7f6678981e77a939b4a7978373
gradlew :fabric:remapJar -Pavailable_loaders=fabric
```

> `-Pavailable_loaders=fabric` 是必需的：默认会连带配置 `:neoforge` 子项目，本机解析不到它的
> 外部仓库而整场构建失败。**upstream 源码未做任何修改**，只是不让 Gradle 去配置那个子项目。
> `gradle.properties` 里 `available_loaders` 原本就支持这个开关。

产物 `baritone-fabric-1.19.0.jar`（5.04 MB，1.18.0 为 4.79 MB）落到
`maven-repo/baritone/baritone-fabric/1.19.0/`，POM 写明来源 commit 与构建命令。

---

## 六、验证状态

| 项 | 结论 | 证据 |
| --- | --- | --- |
| 编译 | ✅ 0 错误 | `gradlew compileJava` → `BUILD SUCCESSFUL` |
| 启动 | ✅ 到主界面 | `runClient`：`Loading Minecraft 26.2 with Fabric Loader 0.19.5`，加载 58 个模组 |
| Mixin | ✅ 全部应用 | 无 `InjectionError`（首轮曾因 `Minecraft.setScreen` 重定向失败，已按上文改挂 `Gui#setScreen`） |
| 图形后端 | ✅ OpenGL | 日志 `Using graphics backend OpenGL`（印证 DEFAULT 不走 Vulkan） |
| 采样器兜底 | ✅ 生效 | `yiyiaddon/render-compat 光影兼容兜底生效：已为缺失的原版采样器补绑 Sampler1/Sampler2` |
| Baritone | ✅ 工作 | `baritone 1.19.0` 加载成功，正常写 `run/baritone/.../cache` 区域缓存 |
| 自动进服 + 模块 tick | ✅ | 自动进服到目标服务器；`副手口粮`、`星露谷 界面观察` 等模块日志持续输出 |
| 自绘界面 | ✅ 实机通过 | 用户 2026-09-19 实机确认：控制台排版 / 字体 / 圆角、模糊、颜色选择器与物品图标（含箱子 / 床 / 羊毛 / 地毯 / 玻璃这些色系图标）、层层退回全部正常 |
| 世界渲染 | ✅ 实机通过 | 透视 / ESP 的方块框、连线、字牌叠在世界之上且被 GUI 正确遮挡；杀戮光环目标列表与图标正常 |
| 静默容器与切屏 | ✅ 实机通过 | 挂机中按 E 开背包可正常点击、不错位；被传送 / 过门时界面不被顶掉 |
| 状态栏与文案 | ✅ 实机通过 | 动作栏提示、Boss 血条探测、矿脉连挖（矿石族别连通）正常 |
| Baritone Mixin 注入点 | ✅ 全部匹配 | 7 个 Baritone Mixin（含 `lambda$execute$4`、字符串常量替换这些**编译器查不到**的注入点）在 baritone 1.19.0 上全部匹配成功 —— 运行日志里出现汉化输出「已取消」即为证据 |
| 26.2 功能回归 | ✅ 核心通过，⚠️ 有边界 | 用户实机原话「都还在 完全没问题功能也还在」；本次验证环境**未加载第三方模组**，与 Xaero / Sodium / Iris / Meteor 等的联动未覆盖 |
| Vulkan 后端 | ⚠️ 已知限制 | 玩家把 Graphics API 切成 Vulkan 时，Skija 直连 GL 的 UI 会失效（本次未做兜底，属新增项，未获授权） |

**测试环境说明**：`run/mods` 里 104 个第三方模组全是 26.1.2 版，声明 `>=26.1 <26.2`，
Fabric Loader 直接拒绝启动。编译验证与实机核对两次都按「临时移开该目录」的口径跑，
**两次都在跑完后原样还原（104 个文件）**。`run/` 在 `.gitignore` 内，不影响仓库与产物。
因此本次实机结论只覆盖本模组自身与 Fabric API，**不含**那批第三方模组参与的联动路径。

---

## 七、与第 169 条的关系（双轨的两份逻辑）

双版本并行本身会让「判据 / 文案 / 渲染 / 组件」存在两份物理副本。本次为把这条风险压到最低，做了两件事：

1. **两条线只差「API 口径」，不差业务逻辑**：26.2 分支上的改动全部是常量来源、切屏入口、
   注入点、依赖版本，判定与状态机一行未动；
2. **解析类差异收进单点**：实体类型解析进 `EntityIdentifier`、FBO 取值进 `SkiaGlBackend`、
   切屏入口进 `Gui` 的新调用点，不散落在业务里。

后续维护口径：**业务改动一律在 `master` 落地，再 cherry-pick 到 `port/26.2`**，
不直接在 26.2 分支上改业务代码。

---

## 八、遗留与待确认

1. **自绘 UI 的视觉核对** —— **已完成（2026-09-19）**：用户实机确认「都还在 完全没问题功能也还在」，
   覆盖自绘界面、世界渲染、静默容器与切屏、状态栏与文案，逐项见第六节验证表。
   注意验证环境未加载第三方模组（见下第 2 条），与那些模组的联动仍属未覆盖面。
2. **26.2 的第三方模组**：用户实际游玩需要的那批模组（Xaero 系列、Sodium/Iris、Meteor 等）
   都还没有 26.2 版本，这是能不能真正上手 26.2 的外部前提，不是本模组能解决的。
3. **`ClientIdentity#minecraftVersion()`**：当前无调用方，按第 163 条未删除，只做了等价适配；
   如需清理请另行拍板。
4. **未做的兜底**：Vulkan 后端下的自绘 UI 降级/提示（第 163 条意义上的新增，未获授权）。
