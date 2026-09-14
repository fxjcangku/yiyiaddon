# Yiyiaddon 用户统计系统一键部署脚本（Windows PowerShell）

Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Yellow
Write-Host "  Yiyiaddon 用户统计系统部署" -ForegroundColor Cyan
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Yellow
Write-Host ""

# 检查 wrangler 是否安装
$wranglerInstalled = Get-Command wrangler -ErrorAction SilentlyContinue

if (-not $wranglerInstalled) {
    Write-Host "❌ 未检测到 wrangler CLI" -ForegroundColor Red
    Write-Host "正在安装 wrangler..." -ForegroundColor Yellow
    npm install -g wrangler
}

Write-Host "✅ wrangler CLI 已安装" -ForegroundColor Green
Write-Host ""

# 登录 Cloudflare
Write-Host "📝 请登录 Cloudflare 账号..." -ForegroundColor Cyan
wrangler login

Write-Host ""
Write-Host "📊 创建 D1 数据库..." -ForegroundColor Cyan
wrangler d1 create yiyiaddon-users

Write-Host ""
Write-Host "⚠️  请复制上面输出的 database_id，并填入 wrangler.toml 的 database_id 字段" -ForegroundColor Yellow
Write-Host "按回车继续..." -ForegroundColor Yellow
Read-Host

Write-Host ""
Write-Host "📦 初始化数据库表..." -ForegroundColor Cyan
wrangler d1 execute yiyiaddon-users --file=sql/schema.sql

Write-Host ""
Write-Host "🚀 部署到 Cloudflare Workers..." -ForegroundColor Cyan
wrangler deploy

Write-Host ""
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Yellow
Write-Host "  ✅ 部署成功！" -ForegroundColor Green
Write-Host "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━" -ForegroundColor Yellow
Write-Host ""
Write-Host "📋 下一步：" -ForegroundColor Cyan
Write-Host "1. 复制上面显示的 API 地址"
Write-Host "2. 若域名变化，修改 src/main/java/com/yiyiaddon/core/HttpApi.java 的 BASE_URL"
Write-Host "3. 重新编译：./gradlew.bat build"
Write-Host ""
Write-Host "🎮 测试 API：" -ForegroundColor Cyan
Write-Host 'curl -X POST 你的API地址/api/register `' -ForegroundColor Gray
Write-Host '  -H "Content-Type: application/json" `' -ForegroundColor Gray
Write-Host '  -d ''{"uuid":"test","name":"测试","version":"1.1-beta2"}''' -ForegroundColor Gray
Write-Host ""
