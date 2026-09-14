-- 记录客户端功能使用情况；不保存完整指令、聊天文本、密码或服务器原生命令。
CREATE TABLE IF NOT EXISTS command_activities (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    uuid TEXT NOT NULL,
    name TEXT NOT NULL,
    command_name TEXT NOT NULL,
    category TEXT NOT NULL,
    created_at INTEGER NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_command_activities_created ON command_activities(created_at DESC);
CREATE INDEX IF NOT EXISTS idx_command_activities_user_command ON command_activities(uuid, command_name, created_at DESC);
