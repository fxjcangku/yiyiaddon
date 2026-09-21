-- Yiyiaddon 用户统计数据库表结构（与线上 D1 实际结构保持一致）

CREATE TABLE IF NOT EXISTS users (
    uuid TEXT PRIMARY KEY,                  -- 玩家 UUID（唯一标识）
    name TEXT NOT NULL,                     -- 玩家游戏名
    version TEXT NOT NULL,                  -- 客户端版本号
    minecraft_version TEXT DEFAULT 'unknown', -- Minecraft 版本
    first_seen INTEGER NOT NULL,            -- 首次使用时间（Unix 时间戳，毫秒）
    last_seen INTEGER NOT NULL,             -- 最后使用时间（Unix 时间戳，毫秒）
    usage_count INTEGER NOT NULL DEFAULT 1, -- 累计进入次数
    server_ip TEXT,                         -- 服务器地址
    server_name TEXT,                       -- 服务器名称
    client_ip TEXT,                         -- 客户端公网 IP
    client_country TEXT,                    -- 客户端国家代码
    is_premium INTEGER DEFAULT 0,           -- 是否正版账户
    pos_x REAL,                             -- 坐标 X
    pos_y REAL,                             -- 坐标 Y
    pos_z REAL,                             -- 坐标 Z
    dimension TEXT,                         -- 维度（主世界/下界/末地）
    health REAL,                            -- 生命值
    food_level INTEGER,                     -- 饥饿值
    game_mode TEXT,                         -- 游戏模式
    is_online INTEGER DEFAULT 0,            -- 是否在线
    current_activity TEXT,                  -- 当前活动
    last_death_time INTEGER,                -- 最后死亡时间
    kill_count INTEGER DEFAULT 0,           -- 击杀数
    death_count INTEGER DEFAULT 0,          -- 死亡数
    total_playtime INTEGER DEFAULT 0,       -- 累计游戏时长（毫秒）
    server_latency REAL,                    -- 到 Minecraft 服务器的延迟（毫秒）
    network_latency REAL,                   -- 到后台 API 的网络延迟（毫秒）
    gamertag TEXT,                          -- Xbox Gamertag（正版微软账户才有）
    xuid TEXT,                              -- 微软账号 XUID（Xbox User ID，正版唯一标识）
    enabled_modules TEXT,                   -- 已开启模块列表（JSON 数组）
    last_heartbeat INTEGER,                 -- 最近心跳时间（毫秒，在线状态依据）
    client_city TEXT,                       -- 客户端城市（Cloudflare 解析）
    client_region TEXT,                     -- 客户端地区
    client_timezone TEXT,                   -- 客户端时区
    client_asn INTEGER,                     -- 客户端 ASN（自治系统号）
    client_as_org TEXT,                     -- 客户端 AS 组织名（用于 VPN/机房识别）
    is_vpn_suspected INTEGER DEFAULT 0,     -- 是否疑似 VPN/代理/机房
    status TEXT DEFAULT 'multiplayer'        -- 玩家当前状态 menu 主菜单/singleplayer 单人/multiplayer 多人
);

CREATE INDEX IF NOT EXISTS idx_first_seen ON users(first_seen);
CREATE INDEX IF NOT EXISTS idx_last_seen ON users(last_seen);
CREATE INDEX IF NOT EXISTS idx_heartbeat ON users(last_heartbeat);

-- 每日活跃玩家日志：心跳/注册时写入（同一玩家同一天去重），用于后台「最近 14 天活跃」趋势
CREATE TABLE IF NOT EXISTS daily_active (
    day TEXT NOT NULL,          -- 日期 YYYY-MM-DD（UTC）
    uuid TEXT NOT NULL,         -- 玩家 UUID
    PRIMARY KEY (day, uuid)
);
CREATE INDEX IF NOT EXISTS idx_daily_active_day ON daily_active(day);

-- 消息表：管理员发给玩家 / 玩家回复管理员
CREATE TABLE IF NOT EXISTS messages (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    target_uuid TEXT,                       -- 目标玩家 UUID（NULL 表示广播）
    target_name TEXT,                       -- 目标玩家名
    message TEXT NOT NULL,                  -- 消息内容
    sender TEXT DEFAULT 'Admin',            -- 发送者名
    created_at INTEGER NOT NULL,            -- 发送时间
    delivered INTEGER DEFAULT 0,            -- 是否已投递（定向消息）
    read_at INTEGER,                        -- 读取时间
    from_uuid TEXT,                         -- 来源玩家 UUID（玩家回复时）
    from_admin INTEGER DEFAULT 0,           -- 是否管理员发送
    target_uuids TEXT                       -- 广播投递名单：发送时在线玩家的 uuid，逗号分隔带头尾逗号；NULL = 不限定（玩家的跨服频道消息，按聊天记录补收）
);

-- 广播消息已读记录：避免广播被单个玩家消费后其余玩家读不到
CREATE TABLE IF NOT EXISTS message_reads (
    message_id INTEGER NOT NULL,
    player_uuid TEXT NOT NULL,
    read_at INTEGER NOT NULL,
    PRIMARY KEY (message_id, player_uuid)
);

-- 崩溃上报表：按 fingerprint 聚合重复崩溃
CREATE TABLE IF NOT EXISTS crashes (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    fingerprint TEXT NOT NULL,
    message TEXT,
    stack_trace TEXT,
    version TEXT,
    minecraft_version TEXT,
    count INTEGER DEFAULT 1,
    first_seen INTEGER,
    last_seen INTEGER
);
CREATE INDEX IF NOT EXISTS idx_crashes_fingerprint ON crashes(fingerprint);

-- 远程配置表：客户端轮询读取，后台可实时修改
CREATE TABLE IF NOT EXISTS configs (
    key TEXT PRIMARY KEY,
    value TEXT,
    updated_at INTEGER
);

-- 异常行为检测表：客户端上报可疑行为（高速移动、瞬移等）
CREATE TABLE IF NOT EXISTS anomalies (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    fingerprint TEXT NOT NULL,
    type TEXT,
    severity TEXT,
    message TEXT,
    data TEXT,
    uuid TEXT,
    name TEXT,
    version TEXT,
    minecraft_version TEXT,
    count INTEGER DEFAULT 1,
    first_seen INTEGER,
    last_seen INTEGER
);
CREATE INDEX IF NOT EXISTS idx_anomalies_fingerprint ON anomalies(fingerprint);
CREATE INDEX IF NOT EXISTS idx_anomalies_last_seen ON anomalies(last_seen);