-- 广播投递名单：管理员广播只投给「发送那一刻在线」的玩家（2026-09-21 定稿口径）。
-- 逗号分隔并带头尾逗号（如 ",u1,u2,"），便于 INSTR 精确匹配；NULL 表示不限定接收人，
-- 只用于玩家的跨服频道消息（仍按聊天记录补收）。
--
-- worker.js 的 ensureMessagesTargets() 会在消息接口首次被调用时自动补这一列，
-- 这里保留同一条语句供新库初始化 / 手工迁移使用（SQLite 的 ADD COLUMN 不支持 IF NOT EXISTS，
-- 重复执行会报 duplicate column name，属预期）。
ALTER TABLE messages ADD COLUMN target_uuids TEXT;
