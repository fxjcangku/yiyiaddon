-- 迁移：新增 status 字段，记录玩家当前状态
-- menu 主菜单 / singleplayer 单人世界 / multiplayer 多人服务器
ALTER TABLE users ADD COLUMN status TEXT DEFAULT 'multiplayer';