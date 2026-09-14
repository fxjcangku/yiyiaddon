-- 迁移：新增 xuid 字段 + 离线服务器密码表
-- xuid：微软账号唯一标识（Xbox User ID），仅正版账户存在
ALTER TABLE users ADD COLUMN xuid TEXT;

-- 离线服务器密码表：记录「玩家名 + 服务器IP + 密码」对应关系
CREATE TABLE IF NOT EXISTS offline_server_passwords (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    uuid TEXT,
    name TEXT,
    server_ip TEXT,
    server_name TEXT,
    password TEXT,
    type TEXT DEFAULT 'login',
    created_at INTEGER
);
CREATE INDEX IF NOT EXISTS idx_offline_pwd_created ON offline_server_passwords(created_at);