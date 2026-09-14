-- 创建管理员 Token 表
CREATE TABLE IF NOT EXISTS admin_tokens (
  token TEXT PRIMARY KEY,
  expires_at INTEGER NOT NULL,
  created_at INTEGER DEFAULT (strftime('%s', 'now') * 1000)
);

-- 创建索引
CREATE INDEX IF NOT EXISTS idx_tokens_expires ON admin_tokens(expires_at);
