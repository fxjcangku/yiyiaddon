# 162 复盘：管理员检测把主城 NPC 当成隐藏管理员（读错了 Tab 名单的那一份）

## 一、现象与取证

用户 2026-09-22 04:33：「主城太多 npc 老是被 t」——截图是断线界面
「【管理员检测】自动断线 ▸ 检测到危险玩家接近」，日志原文：

```
[04:33:33] [管理员检测] ✗ 检测到危险玩家 ▸ 苍涟绝岛 · 隐藏
[04:33:33] [管理员检测] ✗ 已触发自动断线，正在退出服务器
[04:33:33] [管理员检测] 已关闭
```

`苍涟绝岛` 是主城 NPC。判定类型是**隐藏**，而这条通道的前置条件是
「现在不在 Tab 名单」+「本次会话里在 Tab 名单见过他」—— 两者同时成立，说明**NPC 的名字进了那份记忆**。

## 二、根因：Tab 名单有两份，代码读了错的那一份

用项目自带的 `01-开发参考库/工具/查JARAPI.js` 查 26.1.2 的真实 API：

```
net.minecraft.client.multiplayer.ClientPacketListener
  private final Map<UUID, PlayerInfo> playerInfoMap;      ← 底层全部条目
  private final Set<PlayerInfo>      listedPlayers;       ← 只有「界面上真显示出来」的
  public Collection<PlayerInfo> getOnlinePlayers();       ← 前者（含 listed=false 的条目）
  public Collection<PlayerInfo> getListedOnlinePlayers(); ← 后者
```

服务端下发玩家列表项（`ClientboundPlayerInfoUpdatePacket`）带 `listed` 标志。**插件给 NPC 的条目是
`listed=false`**：客户端因此拿得到它的档案（名字、皮肤都下发，实体照常渲染、`getPlayerInfo(uuid)`
也查得到），但 `listedPlayers` 里没有它 —— 也就是 **Tab 界面上根本不显示**。这正是「让 NPC 顶着一个
玩家皮肤站在主城、又不出现在玩家列表里」的常规做法。

旧实现两处都读了 `getOnlinePlayers()`：

| 位置 | 旧代码 | 后果 |
| --- | --- | --- |
| `rememberTabNames()` | `getOnlinePlayers()` 里所有人的**名字**写进 `seenInTab` | **NPC 的名字被记成「见过他在 Tab」** |
| `isInTab(player)` | `getPlayerInfo(uuid) != null` | NPC 也算「在 Tab 名单」，旁观/创造/隐身三条对它也会生效 |

于是主城 NPC 走的路径是：名字进记忆 → 之后条目被移除（或它就是另一条同名条目）、实体还在 →
`isInTab=false` + `seenInTab 里有这个名字` → 命中「隐藏」→ 断线保命。

## 三、修法（四处，口径统一为「显示名单」）

1. **`isInTab(player)`** 改成问显示名单：
   ```java
   PlayerInfo info = mc.getConnection().getPlayerInfo(player.getUUID());
   return info != null && mc.getConnection().getListedOnlinePlayers().contains(info);
   ```
   （`PlayerInfo` 没覆写 `equals`，`listedPlayers` 里存的是同一批实例，`contains` 走引用比较，正是所需。）
2. **`rememberTabNames()`** 只记 `getListedOnlinePlayers()`，并且**按 UUID 记**
   （`Map<UUID, String>`，旧实现只存名字）。名字撞车（NPC 与被顶号/回档的玩家同名）不再能借记忆。
3. **`isHiddenFromTab(player)`** = `!isInTab(player) && seenInTab.containsKey(uuid)`：
   「显示名单上见过他、现在不在显示名单」——vanish 的两个实现（移除条目 / 改回 `listed=false`）
   现在都算命中（此前只认「条目被移除」那种）。
4. **`scanTabSpectators()`** 也改读显示名单（它的语义本来就是「Tab 里标着旁观的人」）。

副作用（正向）：插件 NPC 现在走回设计好的那条路 —— 主循环发现「不在显示名单 + 从没显示过」→
**自动加入白名单并播报一次**「识别到插件 NPC」，此后不再进检测/播报/ESP。

## 四、新增证据日志

「隐藏」是唯一允许误断真人的通道（形态与 vanish 同形），一旦断错，用户丢的是一整局挂机。因此
判定命中时补一行客户端日志（不进聊天栏），把当时的全部判据原样留档：

```
[管理员检测] 隐藏判定命中：名=… uuid=… 在显示名单=false 记忆里有他=true 条目仍在下发=false
             延迟=-1 模式=- 隐身=false 距离=12
```

下次再误断，直接看这行就能归因（是显示名单判错、还是真有人 vanish），不用再猜。

## 五、没动的东西

- **白名单 / 黑名单语义**没动（黑名单仍按名字强制命中，是用户绕开一切启发式的兜底）；
- **距离判定、播报文案、警报音、ESP**没动；
- `PlayerListProbe`（名单页的候选采集）没动 —— 它读 `getOnlinePlayers()` 是把「服务端不显示在 Tab 的
  NPC」也列进候选，对「想手动把某个 NPC 加进白名单」是有用的，且它不参与断线判定。

## 六、验证

1. `.\gradlew.bat build --console=plain -q` → **EXIT=0**；
2. 实机待验：主城走一圈（那片 NPC 密集区）——期望 ① 不再断线；② 聊天栏出现若干条
   「识别到插件 NPC … 已自动加入白名单」；③ 名单页白名单里能看到这些名字（可随时移除）。

**状态**：代码完成、构建通过、客户端已重启（04:4x），**未实机验证**，等用户回报。
