-- 迁移：离线服务器密码表新增 type 字段，区分登录(login)与注册(register)
-- 仅当线上已存在「不带 type 的旧表」时，手动执行一次本迁移。
-- 全新数据库直接用 schema.sql 会自动带 type 字段，无需执行本文件。
ALTER TABLE offline_server_passwords ADD COLUMN type TEXT DEFAULT 'login';