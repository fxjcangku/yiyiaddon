-- 2026-08-28 后端功能增强：延迟 / 模块追踪 / 心跳 / 地理信息 / VPN 检测
-- 说明：D1 支持 ALTER TABLE ADD COLUMN，请对线上数据库执行一次。
-- 以上列均不存在于当前线上表（已通过 PRAGMA table_info 核对），可安全执行。

-- 服务器延迟（毫秒，客户端从 Minecraft 连接读取）
ALTER TABLE users ADD COLUMN server_latency REAL;

-- 网络延迟（毫秒，客户端测量到后台 API 的往返时间）
ALTER TABLE users ADD COLUMN network_latency REAL;

-- Xbox Gamertag（仅正版微软账户有值）
ALTER TABLE users ADD COLUMN gamertag TEXT;

-- 已开启模块列表（JSON 数组，包含客户端模块名）
ALTER TABLE users ADD COLUMN enabled_modules TEXT;

-- 最近心跳时间（毫秒时间戳，作为在线状态的唯一依据）
ALTER TABLE users ADD COLUMN last_heartbeat INTEGER;

-- 以下为 Cloudflare 解析的服务端地理信息与 VPN 检测字段
ALTER TABLE users ADD COLUMN client_city TEXT;
ALTER TABLE users ADD COLUMN client_region TEXT;
ALTER TABLE users ADD COLUMN client_timezone TEXT;
ALTER TABLE users ADD COLUMN client_asn INTEGER;
ALTER TABLE users ADD COLUMN client_as_org TEXT;
ALTER TABLE users ADD COLUMN is_vpn_suspected INTEGER DEFAULT 0;

-- 加速在线状态查询
CREATE INDEX IF NOT EXISTS idx_heartbeat ON users(last_heartbeat);