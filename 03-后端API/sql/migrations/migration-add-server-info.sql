-- 添加服务器 IP 和服务器名称字段
ALTER TABLE users ADD COLUMN server_ip TEXT;
ALTER TABLE users ADD COLUMN server_name TEXT;
