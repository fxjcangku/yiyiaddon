-- 删除「离线服务器密码」记录功能（2026-09-20）
--
-- 背景：该表用于存放客户端从玩家 /login、/register 指令里抓到的明文密码，
-- 配合后台「离线密码」页展示「哪个玩家、在哪个服务器、用了什么密码」。
-- 用户要求整体删掉这个功能，服务端不再收集、不再提供查询接口，表一并清除，
-- 以免历史明文密码继续留在库里。
--
-- 配套改动（同一次提交）：
--   后端：worker.js 删除 POST /api/offline-server-password 与 GET /api/admin/offline-passwords，
--         并从 DELETE /api/admin/players 的清理批次里移除本表；sql/schema.sql 不再建表；
--         后台页面 admin-modern.js 删除「密码」标签页与复制按钮。
--   客户端：删除 service/ServerPasswordService.java 与 model/ServerCredential.java。
--
-- 历史迁移（migration-2026-08-28-xuid-offline.sql 建表、migration-2026-08-28-password-type.sql 加列）
-- 保持原样：那是当时已执行的记录，不改写历史。

DROP TABLE IF EXISTS offline_server_passwords;
