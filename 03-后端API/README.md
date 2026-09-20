# 03-后端API

yiyiaddon 的用户统计与远程管理后端。运行在 Cloudflare Workers 上，数据存于 D1（SQLite）。
代码与数据库均不依赖任何第三方客户端框架，客户端侧只通过 HTTPS + JSON 与本目录下的 Worker 通信。

## 一、目录结构

```
03-后端API/
├── wrangler.toml                  Workers 配置（main 指向 src/worker.js）
├── src/
│   ├── worker.js                  全部接口实现（约 24 个端点）
│   ├── admin-modern.js            后台管理页面（单文件 HTML，由 worker.js 内联引用）
│   └── sw.js                      Service Worker 可读副本；worker.js 以字符串内联返回，运行时不用此文件
├── sql/
│   ├── schema.sql                 完整建表脚本（与线上 D1 结构一致）
│   ├── migrations/                历史增量迁移，按文件名日期顺序执行
│   └── create_tokens_table.sql    早期 admin_tokens 表，已废弃（现为无状态 token），仅作历史留存
└── scripts/
    ├── deploy.ps1                 Windows 一键部署
    └── deploy.sh                  macOS / Linux 一键部署
```

执行 wrangler 命令时工作目录必须是 `03-后端API/`（`wrangler.toml` 所在目录）。

## 二、部署

```
cd 03-后端API
npm install -g wrangler
wrangler login
wrangler d1 create yiyiaddon-users          # 将输出的 database_id 填入 wrangler.toml
wrangler d1 execute yiyiaddon-users --file=sql/schema.sql
npx wrangler secret put ADMIN_PASSWORD      # 管理员密码，不写入源码
wrangler deploy
```

`wrangler.toml` 中的 `ADMIN_USERNAME` 为明文变量，`ADMIN_PASSWORD` 必须通过 `wrangler secret` 设置。
自定义域名为 `yiyiaddon.asia`，与客户端 `HttpApi.BASE_URL` 一致；换域名时两处需同步修改。

## 三、接口清单

公开接口（客户端调用）：

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| POST | `/api/register` | 注册/更新用户，返回排名与正版判定 |
| POST | `/api/heartbeat` | 心跳（客户端每 3 秒一次），12 秒无心跳判定离线 |
| POST | `/api/offline` | 断开连接时立即下线 |
| POST | `/api/command-activity` | 上报功能使用（同名 30 秒去重） |
| POST | `/api/crash/report` | 崩溃上报，按指纹聚合 |
| POST | `/api/anomaly/report` | 异常行为上报，按指纹聚合 |
| POST | `/api/messages/poll` | 拉取未读消息（定向 + 广播） |
| POST | `/api/messages/reply` | 玩家回复管理员 |
| POST | `/api/chat/send` | 发送频道消息或私聊（上限 300 字） |
| GET | `/api/chat/online` | 在线玩家名单（仅玩家名） |
| GET | `/api/stats` | 脱敏统计，供客户端展示 |
| GET | `/api/config` | 拉取远程配置（客户端每 60 秒轮询） |
| POST | `/api/admin/login` | 管理员登录，签发 7 天有效 token |

管理接口（需 `Authorization: Bearer <token>`）：`/api/admin/players`（GET/DELETE）、
`/api/admin/analytics`、`/api/admin/crashes`、`/api/admin/anomalies`、
`/api/admin/command-activities`、`/api/admin/config`（POST/DELETE）、
`/api/admin/refresh-premium`、`/api/admin/toggle-premium`、`/api/admin/clean-old-data`、
`/api/admin/broadcast-jobs`、`/api/admin/start-broadcast`、`/api/admin/stop-broadcast`、
`/api/messages/send`、`/api/messages/history`。

页面：`/` 与 `/admin` 返回后台管理页，`/sw.js` 返回 Service Worker。

## 四、数据表

`users`（玩家主表，含身份、地理位置、活动、在线状态）、`daily_active`（每日活跃去重日志）、
`messages` + `message_reads`（管理员消息与广播已读）、`crashes`、`anomalies`、
`configs`（远程配置键值）、`command_activities`、`broadcast_jobs`。

## 五、客户端对应实现

客户端调用方全部位于 `src/main/java/com/yiyiaddon/`：

| 端点 | 客户端位置 |
| --- | --- |
| `/api/register`、`/api/stats` | `service/RegisterService`、`service/StatsService` |
| `/api/heartbeat`、`/api/offline` | `service/HeartbeatService` |
| `/api/crash/report`、`/api/anomaly/report` | `service/TelemetryService` |
| `/api/config` | `service/RemoteConfigService` |
| `/api/command-activity` | `service/CommandActivityService` |
| `/api/chat/*`、`/api/messages/*` | `service/ChatService` |
| 传输层、延迟、后台调度 | `core/HttpApi`、`core/BackendLatency`、`core/BackgroundTasks` |
| 身份、游戏状态、归属地探测 | `platform/ClientIdentity`、`platform/GameProbe`、`platform/PlayerSampler`、`platform/NetworkInfoProbe` |

以下接入点需要在客户端侧接线（服务本身不自行注册）：

- 客户端初始化：`RemoteConfigService.start()`、`HeartbeatService.start()`、`TelemetryService.start()`
- 玩家进服：`RegisterService.register()`、`ChatService.start()`
- 玩家断开：`HeartbeatService.reportOffline()`、`ChatService.stop()`、`RegisterService.reset()`
- `ClientPacketListener.sendCommand(String)`：`CommandActivityService.onOutgoingCommand(...)`

## 六、备注

- `enabled_modules` 字段由客户端通过 `GameProbe.setModuleSource(...)` 提供数据源，功能系统接入前上报空数组。
