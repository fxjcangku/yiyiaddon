-- 创建消息队列表
-- 用于管理员从后台向玩家发送消息

CREATE TABLE IF NOT EXISTS messages (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    target_uuid TEXT,                    -- 目标玩家UUID，NULL表示发给所有人
    target_name TEXT,                    -- 目标玩家名称
    message TEXT NOT NULL,               -- 消息内容
    sender TEXT DEFAULT 'Admin',         -- 发送者名称
    from_uuid TEXT,                      -- 发送者UUID（玩家回复时）
    from_admin INTEGER DEFAULT 0,        -- 是否管理员发送（1=管理员，0=玩家）
    created_at INTEGER NOT NULL,         -- 创建时间（毫秒时间戳）
    delivered INTEGER DEFAULT 0,         -- 是否已送达（0=未送达，1=已送达）
    read_at INTEGER                      -- 读取时间
);

-- 索引：加速查询未送达的消息
CREATE INDEX IF NOT EXISTS idx_messages_target ON messages(target_uuid, delivered);
CREATE INDEX IF NOT EXISTS idx_messages_created ON messages(created_at);
