-- 添加玩家实时活动追踪字段
-- 执行时间: 2026-08-27

-- 玩家坐标和维度
ALTER TABLE users ADD COLUMN pos_x REAL;
ALTER TABLE users ADD COLUMN pos_y REAL;
ALTER TABLE users ADD COLUMN pos_z REAL;
ALTER TABLE users ADD COLUMN dimension TEXT; -- overworld, the_nether, the_end

-- 玩家状态
ALTER TABLE users ADD COLUMN health REAL;
ALTER TABLE users ADD COLUMN food_level INTEGER;
ALTER TABLE users ADD COLUMN game_mode TEXT; -- survival, creative, adventure, spectator
ALTER TABLE users ADD COLUMN is_online INTEGER DEFAULT 0;

-- 玩家活动
ALTER TABLE users ADD COLUMN current_activity TEXT; -- 当前在做什么
ALTER TABLE users ADD COLUMN last_death_time INTEGER; -- 最后死亡时间
ALTER TABLE users ADD COLUMN kill_count INTEGER DEFAULT 0; -- 击杀数
ALTER TABLE users ADD COLUMN death_count INTEGER DEFAULT 0; -- 死亡数

-- 在线时长（分钟）
ALTER TABLE users ADD COLUMN total_playtime INTEGER DEFAULT 0;
