-- 添加客户端 IP、国家代码和正版检测字段
ALTER TABLE users ADD COLUMN client_ip TEXT;
ALTER TABLE users ADD COLUMN client_country TEXT;
ALTER TABLE users ADD COLUMN is_premium INTEGER DEFAULT 0;
