// yiyiaddon 远程管理控制台（单文件页面，由 worker.js 内联返回，线上挂在 yiyiaddon.asia）
//
// 设计语言与游戏内 ClickGUI「极夜霜玻璃」（AppleDarkTheme）同源：
//   近黑窗口底 0C0C10 / 侧栏 14141A / 内容 0E0E13 / 模块卡片 1A1A21 / 次级表面 22222A；
//   大圆角：窗口 22 / 卡片 16 / 行与控件 10；1px 边框 + 顶部 1px 高光描边；
//   强调蓝 0A84FF（亮色主题 007AFF）；状态绿 5BD37A / 状态红 FF6B6B（亮色 1E8E4A / CC2222）。
// 毛玻璃只用于悬浮层（登录卡 / 移动端底栏 / 详情抽屉），滚动内容卡片用近实底，避免长列表滚动掉帧。
// 桌面端整体呈现为一个居中悬浮的「窗口」，与模组内 GUI 的窗口结构一致：左栏导航 + 右侧内容。

export const ADMIN_HTML = `
<!DOCTYPE html>
<html lang="zh-CN">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width,initial-scale=1.0,viewport-fit=cover,user-scalable=no">
  <meta name="color-scheme" content="dark light">
  <meta name="apple-mobile-web-app-capable" content="yes">
  <meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
  <title>yiyiaddon 控制台</title>
  <script>document.documentElement.setAttribute('data-theme',localStorage.getItem('theme')||'dark')</script>
  <script src="https://cdn.jsdelivr.net/npm/chart.js@4.4.0/dist/chart.umd.min.js"></script>
  <style>
/* ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
   主题变量（暗色为默认，对应游戏内「极夜霜玻璃」；亮色为其镜像）
   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ */
:root{
  --t-1:#FFFFFF;--t-2:#9E9EA7;--t-3:#6E6E76;
  --bg-1:#0E0E13;--bg-2:#1A1A21;--bg-3:#22222A;
  --brand:#0A84FF;--brand-l:#64D2FF;
  --ok:#5BD37A;--warn:#F59E0B;--err:#FF6B6B;--purple:#AF52DE;
  --card:rgba(26,26,33,.92);--card-b:rgba(255,255,255,.07);
  --glass:rgba(20,20,26,.58);
  --win:#0C0C10;--side:#14141A;--content:#0E0E13;
  --line:#303038;--sep:#232329;
  --field:rgba(34,34,42,.85);
  --hover:rgba(10,132,255,.10);--ind:rgba(10,132,255,.20);--ind-b:rgba(10,132,255,.40);
  --page:#070709;
  --rim:inset 0 1px 0 rgba(255,255,255,.05);
  --shadow:0 18px 48px rgba(0,0,0,.55);
  --blur:saturate(160%) blur(24px);
  --ease:cubic-bezier(.16,1,.3,1);
  --r-s:10px;--r-m:14px;--r-l:16px;--r-x:22px;
  --gap:16px;--header:56px;--tab:62px;
}
[data-theme=light]{
  --t-1:#111114;--t-2:#6E6E73;--t-3:#9E9EA7;
  --bg-1:#F2F2F7;--bg-2:#FFFFFF;--bg-3:#F6F6F9;
  --brand:#007AFF;--brand-l:#5AC8FA;
  --ok:#1E8E4A;--warn:#B26A00;--err:#CC2222;--purple:#8944AB;
  --card:rgba(255,255,255,.94);--card-b:rgba(0,0,0,.07);
  --glass:rgba(255,255,255,.62);
  --win:#EDEDF1;--side:#F7F7FA;--content:#F2F2F7;
  --line:#E2E2E8;--sep:#EBEBEF;
  --field:#F6F6F9;
  --hover:rgba(0,122,255,.08);--ind:rgba(0,122,255,.12);--ind-b:rgba(0,122,255,.32);
  --page:#E6E6EC;
  --rim:inset 0 1px 0 rgba(255,255,255,.85);
  --shadow:0 18px 44px rgba(0,0,0,.16);
}

/* ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
   基础：近黑底 + 两团静态微光（模拟模组里霜玻璃后的暗景）
   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ */
*{box-sizing:border-box;-webkit-tap-highlight-color:transparent}
html,body{margin:0;padding:0;width:100%;min-height:100%}
body{
  font-family:-apple-system,BlinkMacSystemFont,"SF Pro","Helvetica Neue","PingFang SC","Microsoft YaHei",sans-serif;
  -webkit-font-smoothing:antialiased;
  background:var(--page);
  color:var(--t-1);
  overflow-x:hidden;
  position:relative;
}
.glow{position:fixed;border-radius:50%;filter:blur(120px);pointer-events:none;z-index:0}
.g1{width:560px;height:560px;top:-180px;left:-120px;background:radial-gradient(circle,rgba(10,132,255,.14),transparent 65%)}
.g2{width:520px;height:520px;bottom:-160px;right:-100px;background:radial-gradient(circle,rgba(100,210,255,.08),transparent 65%)}
[data-theme=light] .g1{background:radial-gradient(circle,rgba(0,122,255,.10),transparent 65%)}
[data-theme=light] .g2{background:radial-gradient(circle,rgba(90,200,250,.10),transparent 65%)}
a{color:var(--brand)}
button{font-family:inherit}
input,select,textarea{font-family:inherit}
input:focus,select:focus,textarea:focus{
  outline:none;border-color:var(--brand)!important;box-shadow:0 0 0 3px var(--ind)
}

/* ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
   登录：居中悬浮的霜玻璃卡片
   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ */
#login{position:fixed;inset:0;display:flex;align-items:center;justify-content:center;padding:24px;z-index:9999}
.login-card{
  width:100%;max-width:420px;padding:52px 44px;
  border-radius:var(--r-x);
  background:var(--glass);border:1px solid var(--card-b);
  box-shadow:var(--shadow),var(--rim);
  backdrop-filter:var(--blur);-webkit-backdrop-filter:var(--blur);
  animation:pop .5s var(--ease);
}
@keyframes pop{from{opacity:0;transform:translateY(24px) scale(.97)}to{opacity:1;transform:none}}
.logo-mark{
  width:76px;height:76px;border-radius:20px;margin:0 auto 22px;
  background:linear-gradient(135deg,var(--brand),var(--brand-l));
  display:flex;align-items:center;justify-content:center;
  font-size:36px;font-weight:800;color:#fff;
  box-shadow:0 12px 32px rgba(10,132,255,.35);
  position:relative;
}
.logo-mark::after{
  content:'';position:absolute;inset:7px;
  border-radius:14px;border:1px solid rgba(255,255,255,.25);
}
.login-card h1{
  margin:0 0 8px;font-size:27px;font-weight:800;
  text-align:center;color:var(--t-1);letter-spacing:.5px;
}
.login-card p{
  margin:0 0 34px;font-size:14px;color:var(--t-2);text-align:center;
}
.field{margin-bottom:16px}
.field input{
  width:100%;padding:14px 18px;border-radius:var(--r-m);
  border:1px solid var(--line);background:var(--field);
  font-size:15px;color:var(--t-1);font-weight:500;
  transition:border-color .2s var(--ease),box-shadow .2s var(--ease);
}
.field input::placeholder{color:var(--t-3)}
button.primary{
  width:100%;padding:14px;border:none;border-radius:var(--r-m);
  background:var(--brand);color:#fff;font-size:15px;font-weight:700;
  cursor:pointer;transition:filter .2s var(--ease),transform .15s var(--ease);
  touch-action:manipulation;
}
button.primary:hover{filter:brightness(1.1)}
button.primary:active{transform:scale(.98)}
.err{
  margin-top:18px;font-size:13px;color:var(--err);text-align:center;
  font-weight:600;padding:10px;border-radius:var(--r-s);
  background:rgba(255,107,107,.10);display:none;
}
.err:not(:empty){display:block}

/* ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
   主应用：桌面端为居中悬浮「窗口」（侧栏 + 内容），移动端全屏 + 底部导航
   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ */
#app{display:none;position:relative;z-index:1;min-height:100vh}
.shell{display:flex;flex-direction:column;min-height:100dvh}
.sidebar{display:none}
.main{flex:1;min-width:0;display:flex;flex-direction:column;min-height:100dvh;background:var(--content)}
.topbar{
  position:sticky;top:0;z-index:50;height:var(--header);
  display:flex;align-items:center;justify-content:space-between;
  padding:0 16px;background:var(--side);border-bottom:1px solid var(--line);
}
.topbar .title{font-size:16px;font-weight:700;color:var(--t-1);display:flex;align-items:baseline;gap:2px;min-width:0}
.tb-brand{font-weight:800}
.tb-brand::after{content:' · ';color:var(--t-3);font-weight:400}
.tb-view{color:var(--t-2);font-weight:600;white-space:nowrap;overflow:hidden;text-overflow:ellipsis}
.acts{display:flex;gap:8px;flex-shrink:0}
.icon-btn{
  width:36px;height:36px;border-radius:50%;
  background:var(--bg-3);border:1px solid var(--card-b);color:var(--t-2);
  display:flex;align-items:center;justify-content:center;cursor:pointer;
  transition:color .2s,background .2s;
}
.icon-btn:hover{color:var(--t-1);background:var(--hover)}
.icon-btn svg{width:17px;height:17px}
.container{
  flex:1;padding:16px 16px calc(var(--tab) + env(safe-area-inset-bottom) + 16px);
  max-width:1200px;width:100%;margin:0 auto;
}

/* 移动端底部导航（霜玻璃） */
.tabbar{
  position:fixed;left:0;right:0;bottom:0;
  height:calc(var(--tab) + env(safe-area-inset-bottom));
  padding-bottom:env(safe-area-inset-bottom);
  display:flex;z-index:100;
  background:var(--glass);border-top:1px solid var(--line);
  backdrop-filter:var(--blur);-webkit-backdrop-filter:var(--blur);
}
.tabbar button{
  flex:1;display:flex;flex-direction:column;
  align-items:center;justify-content:center;gap:3px;
  background:transparent;border:none;padding:8px;
  font-size:11px;font-weight:500;color:var(--t-2);cursor:pointer;
}
.tabbar button.on{color:var(--brand)}
.tabbar button .e{display:flex;align-items:center;justify-content:center}
.tabbar button svg{width:21px;height:21px}

/* ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
   桌面端：悬浮窗口
   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ */
@media(min-width:768px){
  body{overflow:hidden}
  .tabbar{display:none}
  .shell{
    flex-direction:row;
    height:calc(100vh - 56px);height:calc(100dvh - 56px);
    margin:28px auto;width:min(1280px,calc(100% - 56px));
    border-radius:var(--r-x);border:1px solid var(--line);
    box-shadow:var(--shadow);overflow:hidden;background:var(--win);
  }
  .sidebar{
    display:flex;flex-direction:column;width:216px;flex-shrink:0;
    background:var(--side);border-right:1px solid var(--line);
  }
  .brand{display:flex;align-items:center;gap:12px;padding:18px 18px 14px}
  .brand-mark{
    width:42px;height:42px;border-radius:12px;flex-shrink:0;
    background:linear-gradient(135deg,var(--brand),var(--brand-l));
    display:flex;align-items:center;justify-content:center;
    color:#fff;font-size:20px;font-weight:800;
    box-shadow:0 6px 18px rgba(10,132,255,.30);
  }
  .brand-name{font-size:16px;font-weight:800;color:var(--t-1);letter-spacing:.3px}
  .brand-sub{font-size:11px;color:var(--t-3);margin-top:2px}
  .side-nav{display:flex;flex-direction:column;gap:4px;padding:10px 12px;flex:1}
  .side-nav button{
    display:flex;align-items:center;gap:11px;width:100%;
    padding:11px 13px;border-radius:var(--r-s);
    background:transparent;border:1px solid transparent;
    color:var(--t-2);font-size:14px;font-weight:500;text-align:left;
    cursor:pointer;transition:background .18s,color .18s,border-color .18s;
  }
  .side-nav button svg{width:18px;height:18px;flex-shrink:0}
  .side-nav button:hover{background:var(--hover);color:var(--t-1)}
  .side-nav button.on{background:var(--ind);border-color:var(--ind-b);color:var(--t-1)}
  .side-foot{
    padding:14px 18px;border-top:1px solid var(--sep);
    font-size:11px;color:var(--t-3);letter-spacing:.03em;
  }
  .main{min-height:0;height:100%}
  .topbar{position:static;flex-shrink:0}
  .container{flex:1;overflow-y:auto;padding:22px 24px 32px}
  #app{min-height:0}
}

/* ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
   卡片 / KPI / 列表行（对应游戏内模块卡片与首页指标行）
   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ */
.card{
  background:var(--card);border:1px solid var(--card-b);
  border-radius:var(--r-l);box-shadow:var(--rim);
  margin-bottom:var(--gap);overflow:hidden;
}
.card-h{
  padding:13px 18px;font-weight:600;font-size:14px;color:var(--t-1);
  border-bottom:1px solid var(--sep);
  display:flex;justify-content:space-between;align-items:center;gap:12px;
}
.kpi-grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(140px,1fr));gap:14px;margin-bottom:var(--gap)}
.kpi{padding:16px 18px;text-align:left}
.kpi-num{font-size:26px;font-weight:700;color:var(--t-1);line-height:1.2;font-variant-numeric:tabular-nums}
.kpi-label{font-size:12px;color:var(--t-2);margin-top:4px;letter-spacing:.03em}

.row{
  padding:13px 18px;display:flex;align-items:center;gap:12px;
  border-bottom:1px solid var(--sep);cursor:pointer;
  transition:background .18s;touch-action:manipulation;
}
.row:last-child{border-bottom:none}
.row:hover{background:var(--hover)}
.row .info{flex:1;min-width:0}
.row .name{font-size:14px;font-weight:600;color:var(--t-1);margin-bottom:3px}
.row .sub{font-size:12.5px;color:var(--t-2);line-height:1.55}
.row .right{font-size:12.5px;color:var(--t-2);text-align:right;flex-shrink:0}

/* 玩家皮肤头像 */
.skin{
  width:44px;height:44px;border-radius:50%;
  background:transparent;flex-shrink:0;
  image-rendering:auto;
  box-shadow:inset 0 0 0 1px var(--card-b);
  object-fit:cover;
}

/* 徽标：正版 / 在线 / VPN 等（语义色与游戏内一致） */
.badge{
  display:inline-block;padding:2px 8px;border-radius:6px;
  font-size:11px;font-weight:600;margin-right:4px;vertical-align:middle;line-height:1.6;
}
.badge.green{background:rgba(91,211,122,.14);color:var(--ok)}
.badge.gray{background:rgba(158,158,167,.14);color:var(--t-2)}
.badge.red{background:rgba(255,107,107,.13);color:var(--err)}
.badge.blue{background:rgba(10,132,255,.14);color:var(--brand)}
[data-theme=light] .badge.green{background:rgba(30,142,74,.12)}
[data-theme=light] .badge.red{background:rgba(204,34,34,.10)}
[data-theme=light] .badge.blue{background:rgba(0,122,255,.12)}

/* 延迟颜色分级 */
.lat.good{color:var(--ok)}
.lat.mid{color:var(--warn)}
.lat.bad{color:var(--err)}
/* 等宽字 */
.mono{font-family:'SF Mono',Monaco,Consolas,monospace;font-size:12px}
/* 在线状态点 */
.dot{display:inline-block;width:8px;height:8px;border-radius:50%;margin-right:5px;vertical-align:middle}
.dot.on{background:var(--ok)}

/* 小按钮（行内操作） */
.btn-mini{
  padding:7px 14px;border-radius:var(--r-s);
  border:1px solid var(--line);background:var(--bg-2);color:var(--t-1);
  cursor:pointer;font-size:13px;font-weight:600;
  transition:filter .2s,background .2s;flex-shrink:0;
}
.btn-mini:hover{filter:brightness(1.12)}
.btn-mini.accent{background:var(--brand);border-color:transparent;color:#fff}
.btn-mini.ok{background:var(--ok);border-color:transparent;color:#fff}
[data-theme=dark] .btn-mini.ok{color:#0B2415}
.btn-mini.danger{background:transparent;border-color:var(--err);color:var(--err)}

/* 页内子标签（聊天 / 安全监控的分组切换） */
.tab-btn{
  padding:9px 16px;border-radius:var(--r-s);
  background:var(--bg-2);border:1px solid var(--card-b);
  color:var(--t-2);cursor:pointer;font-size:13px;font-weight:500;
  transition:all .18s;
}
.tab-btn:hover{background:var(--hover);color:var(--t-1)}
.tab-btn.active{background:var(--brand);color:#fff;border-color:var(--brand)}

/* 消息行 */
.msg-row{padding:12px 18px;border-bottom:1px solid var(--sep)}
.msg-row:last-child{border-bottom:none}
.msg-row.admin{background:var(--hover)}
.msg-meta{font-size:12px;color:var(--t-2);margin-bottom:5px}
.msg-text{font-size:14px;color:var(--t-1);line-height:1.6}

code{font-family:'SF Mono',Monaco,Consolas,monospace;font-size:13px}

/* ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
   玩家详情：底部抽屉（霜玻璃）
   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ */
.sheet-overlay{position:fixed;inset:0;background:rgba(0,0,0,.45);z-index:40;display:none;opacity:0;transition:opacity .3s ease}
[data-theme=light] .sheet-overlay{background:rgba(0,0,0,.25)}
.sheet-overlay.open{display:block;opacity:1}
.sheet{
  position:fixed;left:0;right:0;bottom:0;z-index:41;
  max-height:82vh;overflow-y:auto;
  background:var(--glass);border:1px solid var(--card-b);border-bottom:none;
  border-radius:var(--r-x) var(--r-x) 0 0;
  backdrop-filter:var(--blur);-webkit-backdrop-filter:var(--blur);
  padding:12px 22px calc(26px + env(safe-area-inset-bottom));
  transform:translateY(105%);transition:transform .36s cubic-bezier(.32,.72,.33,1);
}
.sheet.open{transform:translateY(0)}
.sheet .grab{width:36px;height:5px;border-radius:3px;background:var(--card-b);margin:4px auto 16px}
.sheet-head{display:flex;align-items:center;gap:10px;padding:4px 0 10px}
.sheet-close{
  min-width:34px;height:34px;padding:0 10px;border-radius:17px;
  border:1px solid var(--card-b);background:var(--bg-2);color:var(--t-1);
  font-size:15px;cursor:pointer;flex-shrink:0;
}
.sheet h2{margin:0 0 4px;font-size:21px;font-weight:700;letter-spacing:-.3px;display:flex;align-items:center;gap:10px;color:var(--t-1)}
.sheet .kv{display:flex;justify-content:space-between;padding:11px 0;border-bottom:1px solid var(--sep);font-size:14px;gap:12px}
.sheet .kv:last-child{border-bottom:none}
.sheet .kv .k{color:var(--t-2);flex-shrink:0}
.sheet .kv .val{font-weight:600;text-align:right;word-break:break-all;color:var(--t-1)}

/* ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
   移动端紧凑适配
   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ */
@media(max-width:640px){
  .container{padding:12px 12px calc(var(--tab) + env(safe-area-inset-bottom) + 12px)}
  .kpi-grid{grid-template-columns:repeat(auto-fit,minmax(96px,1fr));gap:10px}
  .kpi{padding:13px 14px}
  .kpi-num{font-size:21px}
  .kpi-label{font-size:11px}
  .row{padding:11px 14px;gap:10px}
  .skin{width:38px;height:38px}
  .row .name{font-size:13.5px}
  .row .sub{font-size:11.5px}
  .row .right{font-size:11.5px}
  .card-h{padding:11px 14px;font-size:13px}
  .sheet{padding:12px 16px calc(22px + env(safe-area-inset-bottom))}
  .sheet h2{font-size:19px}
  .sheet .kv{font-size:13.5px}
  .tabbar button{font-size:10px}
  .topbar .title{font-size:15px}
  .login-card{padding:44px 28px}
}
@media(prefers-reduced-motion:reduce){
  .login-card{animation:none}
  .sheet{transition:none}
}
  </style>
</head>
<body>

<div class="glow g1"></div>
<div class="glow g2"></div>

<div id="login">
  <div class="login-card">
    <div class="logo-mark">Y</div>
    <h1>yiyiaddon</h1>
    <p>远程管理控制台</p>
    <div class="field">
      <input id="lg-user" type="text" placeholder="用户名" autocomplete="username">
    </div>
    <div class="field">
      <input id="lg-pass" type="password" placeholder="密码" autocomplete="current-password">
    </div>
    <button class="primary" id="lg-btn">登 录</button>
    <div class="err" id="lg-err"></div>
  </div>
</div>

<div id="app">
  <div class="shell">
    <aside class="sidebar">
      <div class="brand">
        <div class="brand-mark">Y</div>
        <div>
          <div class="brand-name">yiyiaddon</div>
          <div class="brand-sub">远程管理控制台</div>
        </div>
      </div>
      <nav class="side-nav" id="segmented"></nav>
      <div class="side-foot">v1.0-beta1 · 控制台</div>
    </aside>
    <div class="main">
      <header class="topbar">
        <div class="title"><span class="tb-brand">yiyiaddon</span><span class="tb-view" id="topbar-title">仪表盘</span></div>
        <div class="acts">
          <button class="icon-btn" id="theme-btn" title="切换主题"></button>
          <button class="icon-btn" id="logout-btn" title="退出登录"></button>
        </div>
      </header>
      <main class="container" id="view"></main>
    </div>
  </div>
  <nav class="tabbar" id="tabbar"></nav>
</div>

<div class="sheet-overlay" id="sheet-overlay"></div>
<div class="sheet" id="sheet"></div>

<script>
// ─────────────────────────────────────────────────────────────────────
// 控制台核心逻辑
// ─────────────────────────────────────────────────────────────────────

const API = 'https://yiyiaddon.asia';
const state = {
  token: localStorage.getItem('admin_token'),
  tab: 'dashboard',
  players: [],
  cache: {},
  lastFetch: {},
  ws: null
};

// 线性图标（描边风格，随文字颜色着色；不使用 emoji，与游戏内 UI 一致）
const ICONS = {
  dash:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="3" width="7.5" height="7.5" rx="2"/><rect x="13.5" y="3" width="7.5" height="7.5" rx="2"/><rect x="3" y="13.5" width="7.5" height="7.5" rx="2"/><rect x="13.5" y="13.5" width="7.5" height="7.5" rx="2"/></svg>',
  players:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="7.5" r="3.8"/><path d="M4.5 20.5c.8-3.8 3.9-5.7 7.5-5.7s6.7 1.9 7.5 5.7"/></svg>',
  chat:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M21 14.5a2 2 0 0 1-2 2H8l-4.5 4V5.5a2 2 0 0 1 2-2H19a2 2 0 0 1 2 2z"/></svg>',
  shield:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M12 3l7.5 3v5.2c0 4.6-3.1 8.6-7.5 9.8-4.4-1.2-7.5-5.2-7.5-9.8V6z"/></svg>',
  gear:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="3"/><path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-4 0v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1 0-4h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 4 0v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 0 4h-.09a1.65 1.65 0 0 0-1.51 1z"/></svg>',
  moon:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z"/></svg>',
  sun:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="4"/><path d="M12 2v2M12 20v2M4.93 4.93l1.41 1.41M17.66 17.66l1.41 1.41M2 12h2M20 12h2M4.93 19.07l1.41-1.41M17.66 6.34l1.41-1.41"/></svg>',
  logout:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/><path d="M16 17l5-5-5-5"/><path d="M21 12H9"/></svg>'
};

const TABS = [
  {id:'dashboard',icon:ICONS.dash,label:'仪表盘'},
  {id:'players',icon:ICONS.players,label:'玩家管理'},
  {id:'chat',icon:ICONS.chat,label:'聊天消息'},
  {id:'security',icon:ICONS.shield,label:'安全监控'},
  {id:'settings',icon:ICONS.gear,label:'系统设置'}
];

// ─────────────────────────────────────────────────────────────────────
// 工具函数
// ─────────────────────────────────────────────────────────────────────
function $(id){return document.getElementById(id)}
function esc(s){return (s||'').replace(/[&<>"']/g,m=>({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[m]))}

// ─────────────────────────────────────────────────────────────────────
// 地区识别工具（国家/城市/地区/运营商/时区，统一中文名）
// ─────────────────────────────────────────────────────────────────────
// 国家/地区统一用中文名显示（不依赖国旗 emoji：Windows 等系统不渲染地区指示符旗帜，会显示为方框/叉号）
function countryName(code){
  if(!code)return '未知';
  const upper=code.toUpperCase();
  try{
    const t=new Intl.DisplayNames(['zh-CN'],{type:'region'}).of(upper);
    if(t&&t!==upper)return t;
  }catch(e){}
  // 备用手动翻译（兜底）
  const manual={'CN':'中国','US':'美国','JP':'日本','KR':'韩国','TW':'台湾','HK':'香港','MO':'澳门','SG':'新加坡','GB':'英国','DE':'德国','FR':'法国','CA':'加拿大','AU':'澳大利亚','RU':'俄罗斯','IN':'印度','BR':'巴西','MX':'墨西哥','ES':'西班牙','IT':'意大利','NL':'荷兰','SE':'瑞典','CH':'瑞士','TH':'泰国','VN':'越南','MY':'马来西亚','ID':'印度尼西亚','PH':'菲律宾','PL':'波兰','TR':'土耳其','AR':'阿根廷'};
  return manual[upper]||upper;
}
// 常见城市 -> 中文（VPN 出口高频城市优先）
const CITY_ZH={"Los Angeles":"洛杉矶","New York":"纽约","San Francisco":"旧金山","San Jose":"圣何塞","Seattle":"西雅图","Chicago":"芝加哥","Dallas":"达拉斯","Houston":"休斯顿","Atlanta":"亚特兰大","Miami":"迈阿密","Phoenix":"凤凰城","Denver":"丹佛","Ashburn":"阿什本","Buffalo":"布法罗","Fremont":"弗里蒙特","Santa Clara":"圣克拉拉","Boardman":"博德曼","London":"伦敦","Frankfurt":"法兰克福","Paris":"巴黎","Amsterdam":"阿姆斯特丹","Berlin":"柏林","Madrid":"马德里","Moscow":"莫斯科","Tokyo":"东京","Osaka":"大阪","Seoul":"首尔","Singapore":"新加坡","Hong Kong":"香港","Taipei":"台北","Sydney":"悉尼","Melbourne":"墨尔本","Toronto":"多伦多","Vancouver":"温哥华","Shanghai":"上海","Beijing":"北京","Guangzhou":"广州","Shenzhen":"深圳","Hangzhou":"杭州","Chengdu":"成都","Nanjing":"南京","Wuhan":"武汉"};
// 常见地区/州/省 -> 中文
const REGION_ZH={"California":"加利福尼亚州","New York":"纽约州","Texas":"得克萨斯州","Washington":"华盛顿州","Virginia":"弗吉尼亚州","Illinois":"伊利诺伊州","Florida":"佛罗里达州","Georgia":"佐治亚州","Massachusetts":"马萨诸塞州","Pennsylvania":"宾夕法尼亚州","Ohio":"俄亥俄州","Michigan":"密歇根州","Arizona":"亚利桑那州","Colorado":"科罗拉多州","Oregon":"俄勒冈州","Utah":"犹他州","Nevada":"内华达州","New Jersey":"新泽西州","North Carolina":"北卡罗来纳州","Missouri":"密苏里州","Ontario":"安大略省","Quebec":"魁北克省","British Columbia":"不列颠哥伦比亚省","England":"英格兰","Hesse":"黑森州","Hessen":"黑森州","Bavaria":"巴伐利亚州","North Rhine-Westphalia":"北莱茵-威斯特法伦州","Tokyo":"东京都","Osaka":"大阪府","Seoul":"首尔","Guangdong":"广东省","Zhejiang":"浙江省","Beijing":"北京市","Shanghai":"上海市","Jiangsu":"江苏省","Sichuan":"四川省","Fujian":"福建省","Shandong":"山东省"};
// 常见运营商/机房/云厂商 -> 中文（数据中心类标注"机房"，方便识别梯子出口）
const ISP_ZH={"fdcservers":"FDC 机房（数据中心）","m247":"M247 机房（数据中心）","choopa":"Choopa 机房（数据中心）","colocrossing":"ColoCrossing 机房","psychz":"Psychz 机房","quadranet":"QuadraNet 机房","ovh":"OVH 机房","hetzner":"Hetzner 机房","contabo":"Contabo 机房","leaseweb":"LeaseWeb 机房","digitalocean":"DigitalOcean 云","linode":"Linode 云","vultr":"Vultr 云","cloudflare":"Cloudflare","amazon":"亚马逊云（AWS）","google":"谷歌云（GCP）","microsoft":"微软云（Azure）","oracle":"甲骨文云（Oracle）","alibaba":"阿里云","aliyun":"阿里云","tencent":"腾讯云","huawei":"华为云","china telecom":"中国电信","china unicom":"中国联通","china mobile":"中国移动","comcast":"康卡斯特（Comcast）","verizon":"Verizon","deutsche telekom":"德国电信","ntt":"NTT（日本）","kddi":"KDDI（日本）","cogent":"Cogent（骨干网）"};
function fmtCity(c){return c?(CITY_ZH[c]||c):'';}
function fmtRegion(r){return r?(REGION_ZH[r]||r):'';}
function fmtIsp(org){
  if(!org)return '';
  const cleaned=String(org).replace(/\\(\\s*AS\\d+\\s*\\)/gi,'').replace(/[\\s,;]+$/,'').trim();
  if(!cleaned)return String(org).trim();
  const key=cleaned.toLowerCase();
  for(const k in ISP_ZH){if(key.indexOf(k)!==-1)return ISP_ZH[k];}
  return cleaned;
}
// 玩家当前状态徽标：主菜单 / 单人世界 / 多人服务器
function statusBadge(s){
  if(s==='menu')return '<span class="badge blue">主菜单</span>';
  if(s==='singleplayer')return '<span class="badge blue">单人世界</span>';
  return '<span class="badge green">多人服务器</span>';
}
function latClass(v){if(v===null||v===undefined)return '';if(v<60)return 'good';if(v<150)return 'mid';return 'bad';}
// XUID 仅接受纯数字；authlib 未注入时返回的 auth_xuid 占位符一律按空处理
function fmtXuid(x){
  if(x==null)return null;
  const v=String(x).trim();
  return /^\\d{8,20}$/.test(v)?v:null;
}
// 维度汉化
const DIM_ZH={overworld:'主世界',the_nether:'下界',the_end:'末地'};
function fmtDimension(d){
  if(!d)return null;
  const key=String(d).toLowerCase().replace(/^minecraft:/,'');
  return DIM_ZH[key]||d;
}
// 游戏模式汉化
const MODE_ZH={survival:'生存',creative:'创造',adventure:'冒险',spectator:'旁观'};
function fmtGameMode(m){
  if(!m)return null;
  return MODE_ZH[String(m).toLowerCase()]||m;
}
// 异常行为类型与严重级别汉化
const ANOMALY_TYPE_ZH={high_speed:'高速移动',teleport:'瞬移'};
const ANOMALY_SEVERITY_ZH={high:'高危',medium:'中危',low:'低危'};
function fmtAnomalyType(t){return ANOMALY_TYPE_ZH[t]||t;}
function fmtSeverity(s){return ANOMALY_SEVERITY_ZH[s]||s;}
// 时区显示：区域中文简称 + 城市中文名 + UTC 数字偏移（如 亚洲/上海（UTC+8））
const TZ_REGION_ZH={America:'北美',Asia:'亚洲',Europe:'欧洲',Africa:'非洲',Oceania:'大洋洲',Australia:'澳洲',Pacific:'太平洋',Atlantic:'大西洋',Indian:'印度洋',Antarctica:'南极洲',Arctic:'北极'};
const TZ_CITY_ZH={'New_York':'纽约','Los_Angeles':'洛杉矶','Chicago':'芝加哥','Denver':'丹佛','Phoenix':'凤凰城','Detroit':'底特律','Toronto':'多伦多','Vancouver':'温哥华','Sao_Paulo':'圣保罗','Mexico_City':'墨西哥城','Buenos_Aires':'布宜诺斯艾利斯','London':'伦敦','Paris':'巴黎','Berlin':'柏林','Madrid':'马德里','Rome':'罗马','Amsterdam':'阿姆斯特丹','Brussels':'布鲁塞尔','Vienna':'维也纳','Zurich':'苏黎世','Stockholm':'斯德哥尔摩','Moscow':'莫斯科','Istanbul':'伊斯坦布尔','Athens':'雅典','Warsaw':'华沙','Prague':'布拉格','Budapest':'布达佩斯','Dublin':'都柏林','Lisbon':'里斯本','Helsinki':'赫尔辛基','Oslo':'奥斯陆','Copenhagen':'哥本哈根','Shanghai':'上海','Beijing':'北京','Tokyo':'东京','Seoul':'首尔','Hong_Kong':'香港','Singapore':'新加坡','Taipei':'台北','Bangkok':'曼谷','Jakarta':'雅加达','Kuala_Lumpur':'吉隆坡','Manila':'马尼拉','Hanoi':'河内','Ho_Chi_Minh':'胡志明市','Dubai':'迪拜','Riyadh':'利雅得','Kolkata':'加尔各答','Mumbai':'孟买','Karachi':'卡拉奇','Tehran':'德黑兰','Sydney':'悉尼','Melbourne':'墨尔本','Brisbane':'布里斯班','Perth':'珀斯','Auckland':'奥克兰','Honolulu':'檀香山','Anchorage':'安克雷奇'};
function fmtTimezone(tz){
  if(!tz)return '';
  const seg=String(tz).split('/');
  const region=TZ_REGION_ZH[seg[0]]||seg[0]||'';
  // 取最后一段作为城市名（兼容 America/Argentina/Buenos_Aires 这类三层结构）
  const cityKey=seg[seg.length-1]||'';
  const city=TZ_CITY_ZH[cityKey]||(cityKey&&cityKey!==seg[0]?cityKey.replace(/_/g,' '):'');
  let off='';
  try{
    const parts=new Intl.DateTimeFormat('en-US',{timeZone:tz,timeZoneName:'shortOffset'}).formatToParts(new Date());
    const g=parts.find(p=>p.type==='timeZoneName');
    if(g&&g.value)off=String(g.value).replace('GMT','UTC');
  }catch(e){}
  const base=[region,city].filter(Boolean).join('/')||tz;
  return off?base+'（'+off+'）':base;
}
// 游戏时长格式化
function fmtDuration(ms){
  if(ms===null||ms===undefined)return '';
  const m=Math.floor(ms/60000);
  if(m<1)return '0 分钟';
  if(m<60)return m+' 分钟';
  return Math.floor(m/60)+' 小时 '+(m%60)+' 分';
}
// 空值统一显示「未知待刷新」
function park(v){
  if(v===undefined||v===null)return '未知待刷新';
  const s=String(v).trim();
  if(!s||s==='—'||s.toLowerCase()==='null'||s.toLowerCase()==='unknown')return '未知待刷新';
  return v;
}
function kv(k,v){return '<div class="kv"><span class="k">'+k+'</span><span class="val">'+esc(String(park(v)))+'</span></div>';}

async function api(url,opt={}){
  const headers={'Content-Type':'application/json'};
  if(state.token)headers.Authorization='Bearer '+state.token;
  if(opt.body)opt.body=JSON.stringify(opt.body);
  const res=await fetch(API+url,{...opt,headers});
  const data=await res.json();
  data._status=res.status;
  return data;
}

function apiCached(url,ttl=3000){
  const now=Date.now();
  if(state.cache[url]&&state.lastFetch[url]&&(now-state.lastFetch[url])<ttl){
    return Promise.resolve(state.cache[url]);
  }
  return api(url).then(res=>{
    state.cache[url]=res;
    state.lastFetch[url]=now;
    return res;
  });
}

function logout(){
  localStorage.removeItem('admin_token');
  location.reload();
}

// ─────────────────────────────────────────────────────────────────────
// 皮肤头像系统（mc-heads.net + Canvas 渲染，保留透明像素）
// ─────────────────────────────────────────────────────────────────────
function skin(p,size){
  const s=size||44;
  const name=(p&&p.name)?String(p.name):'MHF_Steve';
  const raw='https://mc-heads.net/skin/'+encodeURIComponent(name);
  const fb='https://mc-heads.net/avatar/'+encodeURIComponent(name)+'/'+s;
  return '<canvas class="skin" width="'+s+'" height="'+s+'" data-head-raw="'+raw+'" data-head-fallback="'+fb+'"></canvas>';
}

function drawHead(canvas,img){
  const c=canvas.getContext('2d');
  const s=canvas.width;
  c.clearRect(0,0,s,s);
  c.imageSmoothingEnabled=false;

  // 绘制基础皮肤层（8x8 头部区域）
  try{c.drawImage(img,8,8,8,8,0,0,s,s)}catch(e){}

  // 绘制外层（帽子/头盔层，40x8 区域）
  // 检测是否有非透明像素，如果全透明则不绘制
  try{
    const tempCanvas=document.createElement('canvas');
    tempCanvas.width=8;
    tempCanvas.height=8;
    const tempCtx=tempCanvas.getContext('2d');
    tempCtx.drawImage(img,40,8,8,8,0,0,8,8);
    const imgData=tempCtx.getImageData(0,0,8,8);
    let hasOpaque=false;
    for(let i=3;i<imgData.data.length;i+=4){
      if(imgData.data[i]>0){
        hasOpaque=true;
        break;
      }
    }
    if(hasOpaque){
      c.drawImage(img,40,8,8,8,0,0,s,s);
    }
  }catch(e){}
}

function renderHeadCanvases(root){
  const scope=root||document;
  scope.querySelectorAll('canvas[data-head-raw]').forEach(canvas=>{
    if(canvas.__headRendered)return;
    canvas.__headRendered=true;
    const raw=canvas.getAttribute('data-head-raw');
    const fb=canvas.getAttribute('data-head-fallback');
    const img=new Image();
    img.crossOrigin='anonymous';
    img.onload=()=>drawHead(canvas,img);
    img.onerror=()=>{
      const fbImg=new Image();
      fbImg.crossOrigin='anonymous';
      fbImg.onload=()=>{
        const c=canvas.getContext('2d');
        c.clearRect(0,0,canvas.width,canvas.height);
        c.imageSmoothingEnabled=false;
        c.drawImage(fbImg,0,0,canvas.width,canvas.height);
      };
      fbImg.src=fb;
    };
    img.src=raw;
  });
}

new MutationObserver(muts=>{
  muts.forEach(m=>{
    (m.addedNodes||[]).forEach(n=>{
      if(n.nodeType===1)renderHeadCanvases(n);
    });
  });
}).observe(document.body,{childList:true,subtree:true});

// ─────────────────────────────────────────────────────────────────────
// 初始化
// ─────────────────────────────────────────────────────────────────────

// 等待 DOM 加载完成
document.addEventListener('DOMContentLoaded',function(){

  // 登录功能
  const lgBtn=$('lg-btn');
  if(lgBtn){
    lgBtn.onclick=async()=>{
      const u=$('lg-user').value.trim();
      const p=$('lg-pass').value;
      const errEl=$('lg-err');
      const btnEl=$('lg-btn');

      if(!u||!p){
        errEl.textContent='请输入用户名和密码';
        return;
      }

      errEl.textContent='登录中...';
      btnEl.disabled=true;

      try{
        const res=await api('/api/admin/login',{method:'POST',body:{username:u,password:p}});
        if(res.success&&res.token){
          localStorage.setItem('admin_token',res.token);
          location.reload();
        }else{
          errEl.textContent=res.error||'登录失败';
          btnEl.disabled=false;
        }
      }catch(e){
        errEl.textContent='网络错误：'+e.message;
        btnEl.disabled=false;
      }
    };
  }

  // 已登录则初始化主应用
  if(state.token){
    $('login').style.display='none';
    $('app').style.display='block';
    init();
  }

});

function init(){
  // 主题：默认跟随游戏内「极夜霜玻璃」的暗色
  const theme=localStorage.getItem('theme')||'dark';
  document.documentElement.setAttribute('data-theme',theme);
  $('theme-btn').innerHTML=theme==='dark'?ICONS.sun:ICONS.moon;
  $('theme-btn').onclick=()=>{
    const t=document.documentElement.getAttribute('data-theme')==='dark'?'light':'dark';
    document.documentElement.setAttribute('data-theme',t);
    localStorage.setItem('theme',t);
    $('theme-btn').innerHTML=t==='dark'?ICONS.sun:ICONS.moon;
  };

  // 退出
  $('logout-btn').innerHTML=ICONS.logout;
  $('logout-btn').onclick=()=>{
    localStorage.removeItem('admin_token');
    location.reload();
  };

  // 底部详情抽屉遮罩点击关闭
  $('sheet-overlay').onclick=closeSheet;

  // 构建导航
  buildNav();
  switchTab('dashboard');

  // 注册 Service Worker（离线缓存）
  if('serviceWorker' in navigator){
    navigator.serviceWorker.register('/sw.js').then(()=>{
      console.log('Service Worker 已注册');
    }).catch(e=>console.warn('Service Worker 注册失败',e));
  }

  // WebSocket 实时推送（替代轮询）
  initWebSocket();

  // 降级方案：如果 WebSocket 断开，使用轮询
  setInterval(()=>{
    if(!state.ws||state.ws.readyState!==WebSocket.OPEN){
      if(state.tab==='dashboard')loadDashboard();
    }
  },5000);
}

// ─────────────────────────────────────────────────────────────────────
// WebSocket 实时推送
// ─────────────────────────────────────────────────────────────────────
function initWebSocket(){
  // Cloudflare Workers 不直接支持 WebSocket 服务端
  // 使用轮询模拟实时更新（已在上面降级方案中实现）

  console.log('轮询模式（每 5 秒刷新）');
}

function buildNav(){
  const seg=$('segmented');
  const tab=$('tabbar');
  seg.innerHTML=TABS.map(t=>'<button class="'+(state.tab===t.id?'on':'')+'" data-tab="'+t.id+'">'+t.icon+t.label+'</button>').join('');
  tab.innerHTML=TABS.map(t=>'<button class="'+(state.tab===t.id?'on':'')+'" data-tab="'+t.id+'"><div class="e">'+t.icon+'</div>'+t.label+'</button>').join('');

  document.querySelectorAll('[data-tab]').forEach(b=>{
    b.onclick=()=>switchTab(b.dataset.tab);
  });
}

function switchTab(id){
  state.tab=id;
  buildNav();
  const cur=TABS.find(t=>t.id===id);
  const tb=$('topbar-title');
  if(tb&&cur)tb.textContent=cur.label;
  if(id==='dashboard')loadDashboard();
  else if(id==='players')loadPlayers();
  else if(id==='chat')loadChat();
  else if(id==='security')loadSecurity();
  else if(id==='settings')loadSettings();
}

// ─────────────────────────────────────────────────────────────────────
// 仪表盘（KPI 指标行 + 图表）
// ─────────────────────────────────────────────────────────────────────
async function loadDashboard(){
  const [a,pr]=await Promise.all([
    apiCached('/api/admin/analytics',5000),
    apiCached('/api/admin/players',3000)
  ]);
  if(a._status===401||a._status===403){logout();return}

  state.players=pr.users||[];
  const online=state.players.filter(p=>p.is_online);

  let h='';
  h+='<div class="kpi-grid">';
  h+=kpi(a.total_users,'总玩家');
  h+=kpi(a.online_count,'在线');
  h+=kpi((a.premium&&a.premium.premium)||0,'正版');
  h+=kpi(a.vpn_suspected,'疑似 VPN');
  h+=kpi(a.active_24h,'24 小时活跃');
  h+='</div>';

  // 14天活跃趋势图表
  if(a.daily_active&&a.daily_active.length>0){
    h+='<div class="card">';
    h+='<div class="card-h">14 天活跃趋势</div>';
    h+='<div style="padding:18px"><canvas id="chart-daily" style="max-height:280px"></canvas></div>';
    h+='</div>';
  }

  // 国家分布图表
  if(a.country_distribution&&a.country_distribution.length>0){
    h+='<div class="card">';
    h+='<div class="card-h">国家分布 Top 10</div>';
    h+='<div style="padding:18px"><canvas id="chart-country" style="max-height:280px"></canvas></div>';
    h+='</div>';
  }

  // 版本分布
  if(a.version_distribution&&a.version_distribution.length>0){
    h+='<div class="card">';
    h+='<div class="card-h">版本分布</div>';
    a.version_distribution.slice(0,10).forEach(c=>{
      h+='<div class="row"><div class="info"><div class="name">'+esc(c.version)+'</div></div><div class="right">'+c.c+' 人</div></div>';
    });
    h+='</div>';
  }

  // 在线玩家列表
  h+='<div class="card">';
  h+='<div class="card-h">在线玩家</div>';
  if(!online.length){
    h+='<div class="row">暂无在线玩家</div>';
  }else{
    online.slice(0,10).forEach(p=>{
      const lat=(p.server_latency!=null&&p.server_latency>0)?Math.round(p.server_latency)+' ms':'';
      const loc=p.client_country?countryName(p.client_country):'';
      const sub=[lat,loc].filter(Boolean).join(' · ');
      h+='<div class="row">'+skin(p,44)+'<div class="info"><div class="name">'+esc(p.name)+(p.is_premium?'<span class="badge green" style="margin-left:6px">正版</span>':'')+'</div><div class="sub">'+(sub||'—')+'</div></div><div class="right">'+(lat||'在线')+'</div></div>';
    });
  }
  h+='</div>';

  $('view').innerHTML=h;

  // 渲染图表
  if(a.daily_active&&a.daily_active.length>0){
    renderDailyChart(a.daily_active);
  }
  if(a.country_distribution&&a.country_distribution.length>0){
    renderCountryChart(a.country_distribution.slice(0,10));
  }
}

// 当前主题的强调色（图表配色随主题走，与界面强调色同源）
function themeAccent(){
  const v=getComputedStyle(document.documentElement).getPropertyValue('--brand').trim();
  return v||'#0A84FF';
}

// 渲染14天活跃趋势图
function renderDailyChart(data){
  const ctx=document.getElementById('chart-daily');
  if(!ctx)return;
  const accent=themeAccent();

  new Chart(ctx,{
    type:'line',
    data:{
      labels:data.map(d=>new Date(d.date).toLocaleDateString('zh-CN',{month:'short',day:'numeric'})),
      datasets:[{
        label:'活跃玩家',
        data:data.map(d=>d.active),
        borderColor:accent,
        backgroundColor:accent+'1F',
        tension:0.4,
        fill:true
      }]
    },
    options:{
      responsive:true,
      maintainAspectRatio:false,
      plugins:{
        legend:{display:false}
      },
      scales:{
        y:{beginAtZero:true,ticks:{precision:0}}
      }
    }
  });
}

// 渲染国家分布图
function renderCountryChart(data){
  const ctx=document.getElementById('chart-country');
  if(!ctx)return;

  const colors=['#0A84FF','#5BD37A','#64D2FF','#FF6B6B','#F59E0B','#AF52DE','#30D158','#FF9F0A','#5E5CE6','#FF453A'];

  new Chart(ctx,{
    type:'doughnut',
    data:{
      labels:data.map(d=>countryName(d.client_country)),
      datasets:[{
        data:data.map(d=>d.c),
        backgroundColor:colors,
        borderWidth:0
      }]
    },
    options:{
      responsive:true,
      maintainAspectRatio:false,
      plugins:{
        legend:{position:'bottom'}
      }
    }
  });
}

function kpi(n,l){
  return '<div class="kpi card"><div class="kpi-num">'+(n||0)+'</div><div class="kpi-label">'+l+'</div></div>';
}

// ─────────────────────────────────────────────────────────────────────
// 玩家管理（带虚拟滚动优化）
// ─────────────────────────────────────────────────────────────────────
async function loadPlayers(){
  const pr=await apiCached('/api/admin/players',3000);
  state.players=pr.users||[];

  let h='';

  // 指标行
  const online=state.players.filter(p=>p.is_online).length;
  const premium=state.players.filter(p=>p.is_premium).length;
  const vpn=state.players.filter(p=>p.is_vpn_suspected).length;
  h+='<div class="kpi-grid">';
  h+=kpi(state.players.length,'总玩家');
  h+=kpi(online,'在线');
  h+=kpi(premium,'正版');
  h+=kpi(vpn,'疑似 VPN');
  h+='</div>';

  // 筛选和搜索
  const ctl='padding:10px 14px;border-radius:var(--r-s);border:1px solid var(--line);background:var(--field);color:var(--t-1);font-size:13px';
  h+='<div class="card" style="padding:14px 16px;margin-bottom:16px">';
  h+='<div style="display:flex;gap:10px;flex-wrap:wrap;align-items:center">';
  h+='<input id="search-player" type="text" placeholder="搜索名字 / UUID / 国家 / 服务器" style="flex:1;min-width:200px;'+ctl+'">';
  h+='<select id="filter-status" style="'+ctl+'">';
  h+='<option value="all">全部状态</option>';
  h+='<option value="online">仅在线</option>';
  h+='<option value="offline">仅离线</option>';
  h+='</select>';
  h+='<select id="filter-premium" style="'+ctl+'">';
  h+='<option value="all">全部类型</option>';
  h+='<option value="premium">仅正版</option>';
  h+='<option value="offline">仅离线</option>';
  h+='</select>';
  h+='<select id="filter-vpn" style="'+ctl+'">';
  h+='<option value="all">全部网络</option>';
  h+='<option value="vpn">仅 VPN</option>';
  h+='</select>';
  h+='<button class="btn-mini accent" onclick="exportPlayers()">导出</button>';
  h+='</div></div>';

  // 虚拟滚动容器
  h+='<div class="card">';
  h+='<div class="card-h">玩家列表</div>';
  h+='<div id="player-list" style="height:600px;overflow-y:auto"></div>';
  h+='</div>';

  $('view').innerHTML=h;

  // 初始渲染虚拟列表
  renderVirtualPlayerList(state.players);

  // 绑定搜索和筛选
  $('search-player').oninput=filterPlayers;
  $('filter-status').onchange=filterPlayers;
  $('filter-premium').onchange=filterPlayers;
  $('filter-vpn').onchange=filterPlayers;
}

// 虚拟滚动渲染（只渲染可见部分，性能优化）
function renderVirtualPlayerList(players){
  const container=$('player-list');
  if(!container)return;

  const ROW_HEIGHT=100;
  const BUFFER=5;

  let scrollTop=0;
  let visibleStart=0;
  let visibleEnd=0;

  function render(){
    const containerHeight=container.clientHeight;
    const totalHeight=players.length*ROW_HEIGHT;
    const visibleCount=Math.ceil(containerHeight/ROW_HEIGHT);

    visibleStart=Math.max(0,Math.floor(scrollTop/ROW_HEIGHT)-BUFFER);
    visibleEnd=Math.min(players.length,visibleStart+visibleCount+BUFFER*2);

    let h='';
    h+='<div style="height:'+totalHeight+'px;position:relative">';

    for(let i=visibleStart;i<visibleEnd;i++){
      const p=players[i];
      const top=i*ROW_HEIGHT;
      // 身份/在线/VPN 徽标
      let badges='';
      badges+=p.is_premium?'<span class="badge green">正版</span>':'<span class="badge gray">离线账号</span>';
      badges+=p.is_online?'<span class="badge green">在线</span>':'<span class="badge gray">离线</span>';
      if(p.is_online)badges+=statusBadge(p.status);
      if(p.is_vpn_suspected)badges+='<span class="badge red">VPN</span>';
      // 地区/运营商/服务器信息（全部缺失时统一显示「未知待刷新」）
      const subParts=[];
      if(p.client_country)subParts.push(countryName(p.client_country));
      if(p.client_city)subParts.push(esc(fmtCity(p.client_city)));
      if(p.client_timezone)subParts.push(esc(fmtTimezone(p.client_timezone)));
      if(p.client_as_org)subParts.push(esc(fmtIsp(p.client_as_org)));
      if(p.server_name)subParts.push(esc(p.server_name));
      let sub=subParts.join(' · ');
      if(!sub)sub='未知待刷新';
      // 延迟分项（仅在线显示；在线但延迟未上报时显示「延迟未知待刷新」）
      let lat='';
      if(p.is_online){
        if(p.server_latency!==null&&p.server_latency!==undefined&&p.server_latency>0)lat+='<span class="lat '+latClass(p.server_latency)+'">服务器 '+Math.round(p.server_latency)+'ms</span>';
        if(p.network_latency!==null&&p.network_latency!==undefined&&p.network_latency>0)lat+=(lat?' · ':'')+'<span class="lat '+latClass(p.network_latency)+'">网络 '+Math.round(p.network_latency)+'ms</span>';
        if(!lat)lat='延迟未知待刷新';
      }

      h+='<div class="row" style="position:absolute;top:'+top+'px;left:0;right:0;height:'+ROW_HEIGHT+'px;box-sizing:border-box" onclick="openSheetByUuid(&quot;'+esc(p.uuid)+'&quot;)">'+
        skin(p,48)+
        '<div class="info">'+
          '<div class="name">'+esc(p.name)+'</div>'+
          '<div class="sub" style="margin-bottom:3px">'+badges+'</div>'+
          (sub?'<div class="sub">'+sub+'</div>':'')+
          (lat?'<div class="sub">'+lat+'</div>':'')+
        '</div>'+
        '<div class="right">'+fmtTime(p.last_seen)+'</div>'+
      '</div>';
    }

    h+='</div>';
    container.innerHTML=h;
  }

  let ticking=false;
  container.onscroll=()=>{
    scrollTop=container.scrollTop;
    // 用 requestAnimationFrame 节流：滚动事件高频触发时只在下一次帧渲染时重绘一次，避免每帧都重算 DOM
    if(!ticking){
      ticking=true;
      requestAnimationFrame(()=>{ticking=false;render();});
    }
  };

  render();
}

// 筛选玩家
function filterPlayers(){
  const search=$('search-player').value.toLowerCase();
  const status=$('filter-status').value;
  const premium=$('filter-premium').value;
  const vpn=$('filter-vpn').value;

  let filtered=state.players;

  if(search){
    filtered=filtered.filter(p=>{
      const hay=(p.name||'')+' '+(p.uuid||'')+' '+(p.client_country||'')+' '+(p.server_name||'');
      return hay.toLowerCase().includes(search);
    });
  }

  if(status==='online'){
    filtered=filtered.filter(p=>p.is_online);
  }else if(status==='offline'){
    filtered=filtered.filter(p=>!p.is_online);
  }

  if(premium==='premium'){
    filtered=filtered.filter(p=>p.is_premium);
  }else if(premium==='offline'){
    filtered=filtered.filter(p=>!p.is_premium);
  }

  if(vpn==='vpn'){
    filtered=filtered.filter(p=>p.is_vpn_suspected);
  }

  renderVirtualPlayerList(filtered);
}

// 导出玩家数据为CSV
function exportPlayers(){
  const csv=['姓名,UUID,状态,类型,延迟,首次出现,最后出现'];
  state.players.forEach(p=>{
    csv.push([
      p.name||'',
      p.uuid||'',
      p.is_online?'在线':'离线',
      p.is_premium?'正版':'离线',
      p.ping||0,
      new Date(p.first_seen).toLocaleString('zh-CN'),
      new Date(p.last_seen).toLocaleString('zh-CN')
    ].join(','));
  });

  const blob=new Blob(['\\uFEFF'+csv.join('\\n')],{type:'text/csv;charset=utf-8'});
  const url=URL.createObjectURL(blob);
  const a=document.createElement('a');
  a.href=url;
  a.download='yiyiaddon-players-'+Date.now()+'.csv';
  a.click();
  URL.revokeObjectURL(url);
  alert('已导出 '+state.players.length+' 个玩家数据');
}

function openSheetByUuid(uuid){
  const p=state.players.find(x=>x.uuid===uuid);
  if(!p)return;
  const modules=(p.modules||[]).map(m=>'<span class="badge blue">'+esc(m)+'</span>').join('')||'<span class="badge gray">无</span>';
  let html='<div class="sheet-head"><div class="grab"></div><button class="sheet-close" onclick="closeSheet()" aria-label="关闭">×</button></div>';
  html+='<h2>'+skin(p,48)+'<span>'+esc(p.name)+'</span></h2>';
  html+='<div style="font-size:13px;color:var(--t-2);margin:6px 0 4px;">'+(p.is_online?'<span class="dot on"></span>在线':'离线')+' · '+fmtTime(p.last_seen)+'</div>';
  html+='<div style="margin:12px 0;display:flex;gap:8px;flex-wrap:wrap">';
  if(p.is_premium){
    html+='<button class="btn-mini" onclick="togglePremium(&quot;'+p.uuid+'&quot;,0)">标记为离线</button>';
  }else{
    html+='<button class="btn-mini ok" onclick="togglePremium(&quot;'+p.uuid+'&quot;,1)">标记为正版</button>';
  }
  html+='<button class="btn-mini danger" onclick="deletePlayer(&quot;'+p.uuid+'&quot;)">删除玩家</button>';
  html+='</div>';
  html+=kv('UUID',p.uuid);
  html+=kv('正版账户',p.is_premium?'是'+(p.gamertag?'（'+p.gamertag+'）':''):'离线');
  html+=kv('微软账号',fmtXuid(p.xuid)||(p.is_premium?p.name:'无'));
  html+=kv('IP',p.client_ip);
  const loc=[];
  if(p.client_country)loc.push(countryName(p.client_country));
  if(p.client_region)loc.push(fmtRegion(p.client_region));
  html+=kv('国家/地区',loc.join(' · ')||null);
  const ct=[];
  const city=fmtCity(p.client_city);
  if(city)ct.push(city);
  if(p.client_timezone)ct.push(fmtTimezone(p.client_timezone));
  html+=kv('城市/时区',ct.join(' · ')||null);
  html+=kv('运营商',p.client_as_org?fmtIsp(p.client_as_org):null);
  // 只有在线玩家才显示 VPN 判定、服务器、延迟等实时信息
  if(p.is_online){
    html+=kv('VPN 判定',p.is_vpn_suspected?'疑似 VPN/机房':'正常');
    const svr=p.server_name?p.server_name:'';
    let svrText=svr;
    if(svr&&p.server_ip)svrText+=' · '+p.server_ip;
    html+=kv('服务器',svrText||null);
    html+=kv('服务器延迟',p.server_latency!==null&&p.server_latency!==undefined?Math.round(p.server_latency)+' ms':null);
    html+=kv('网络延迟',p.network_latency!==null&&p.network_latency!==undefined?Math.round(p.network_latency)+' ms':null);
  }
  html+=kv('坐标',(p.pos_x!==null&&p.pos_x!==undefined)?Math.round(p.pos_x)+', '+Math.round(p.pos_y)+', '+Math.round(p.pos_z):null);
  html+=kv('维度',fmtDimension(p.dimension));
  html+=kv('游戏模式',fmtGameMode(p.game_mode));
  html+=kv('当前活动',p.current_activity);
  html+=kv('击杀/死亡',(p.kill_count||0)+' / '+(p.death_count||0));
  html+=kv('游戏时长',fmtDuration(p.total_playtime));
  html+=kv('使用次数',p.usage_count);
  const ver=[];
  if(p.version)ver.push(p.version);
  if(p.minecraft_version)ver.push('MC '+p.minecraft_version);
  html+=kv('版本',ver.join(' · ')||null);
  html+='<div class="kv" style="display:block;"><div class="k" style="margin-bottom:8px;">已开启模块</div><div>'+modules+'</div></div>';
  $('sheet').innerHTML=html;
  $('sheet-overlay').classList.add('open');
  requestAnimationFrame(()=>$('sheet').classList.add('open'));
}
function closeSheet(){
  $('sheet').classList.remove('open');
  $('sheet-overlay').classList.remove('open');
}
function togglePremium(uuid,isPremium){
  if(!confirm('确定要'+(isPremium?'标记为正版':'标记为离线')+'吗？'))return;
  api('/api/admin/toggle-premium',{method:'POST',body:{uuid:uuid,is_premium:isPremium}}).then(res=>{
    if(res.success){
      alert('已更新');
      loadPlayers();
      closeSheet();
    }else{
      alert('操作失败：'+(res.error||'未知错误'));
    }
  });
}

// 删除玩家：调用 DELETE 接口清除该玩家全部数据
function deletePlayer(uuid){
  const p=state.players.find(x=>x.uuid===uuid);
  const label=p?p.name:uuid;
  if(!confirm('确定要删除玩家「'+label+'」的全部数据吗？此操作不可恢复！'))return;
  api('/api/admin/players?uuid='+encodeURIComponent(uuid),{method:'DELETE'}).then(res=>{
    if(res.success){
      alert('已删除玩家「'+label+'」');
      closeSheet();
      loadPlayers();
    }else{
      alert('删除失败：'+(res.error||'未知错误'));
    }
  });
}

// ─────────────────────────────────────────────────────────────────────
// 聊天系统（玩家聊天 + 管理消息 + 设置）
// ─────────────────────────────────────────────────────────────────────
async function loadChat(){
  const [msgs,players]=await Promise.all([
    api('/api/messages/history'),
    apiCached('/api/admin/players',3000)
  ]);
  if(msgs._status===401||msgs._status===403){logout();return}

  state.players=players.users||[];
  const playerMsgs=(msgs||[]).filter(m=>m.from_admin!==1);
  const adminMsgs=(msgs||[]).filter(m=>m.from_admin===1);

  let h='';

  // 标签切换
  h+='<div style="display:flex;gap:8px;margin-bottom:16px;flex-wrap:wrap">';
  h+='<button class="tab-btn active" data-chat-tab="player">玩家聊天 ('+playerMsgs.length+')</button>';
  h+='<button class="tab-btn" data-chat-tab="admin">管理消息 ('+adminMsgs.length+')</button>';
  h+='<button class="tab-btn" data-chat-tab="settings">设置</button>';
  h+='</div>';

  // 玩家聊天
  h+='<div class="chat-panel" id="chat-player">';
  h+='<div class="card">';
  h+='<div class="card-h">玩家聊天记录</div>';
  if(!playerMsgs.length){
    h+='<div class="row">暂无聊天记录</div>';
  }else{
    playerMsgs.slice(0,50).forEach(m=>{
      const prefix=m.is_premium?'<span class="badge green">正版</span>':'<span class="badge gray">离线</span>';
      h+='<div class="msg-row"><div class="msg-meta">'+prefix+' '+esc(m.sender||'未知')+' → '+esc(m.target_name||'所有人')+' · '+fmtTime(m.created_at)+'</div><div class="msg-text">'+esc(m.message)+'</div></div>';
    });
  }
  h+='</div></div>';

  // 管理消息
  h+='<div class="chat-panel" id="chat-admin" style="display:none">';
  h+='<div class="card">';
  h+='<div class="card-h">管理消息</div>';
  if(!adminMsgs.length){
    h+='<div class="row">暂无管理消息</div>';
  }else{
    adminMsgs.slice(0,50).forEach(m=>{
      h+='<div class="msg-row admin"><div class="msg-meta">管理员 → '+esc(m.target_name||'所有人')+' · '+fmtTime(m.created_at)+'</div><div class="msg-text">'+esc(m.message)+'</div></div>';
    });
  }
  h+='</div>';

  // 发送消息
  h+='<div class="card" style="margin-top:16px">';
  h+='<div class="card-h">发送消息</div>';
  h+='<div style="padding:16px">';
  h+='<select id="msg-target" style="width:100%;padding:12px;border-radius:var(--r-s);border:1px solid var(--line);background:var(--field);color:var(--t-1);margin-bottom:12px">';
  h+='<option value="">广播所有人</option>';
  state.players.forEach(p=>{
    h+='<option value="'+esc(p.name)+'">'+esc(p.name)+(p.is_online?' · 在线':'')+'</option>';
  });
  h+='</select>';
  h+='<div style="display:flex;gap:8px">';
  h+='<input id="msg-text" type="text" placeholder="消息内容..." style="flex:1;padding:12px;border-radius:var(--r-s);border:1px solid var(--line);background:var(--field);color:var(--t-1)">';
  h+='<button class="btn-mini accent" onclick="sendMsg()" style="padding:12px 24px">发送</button>';
  h+='</div></div></div>';
  h+='</div>';

  // 设置
  h+='<div class="chat-panel" id="chat-settings" style="display:none">';
  h+='<div class="card">';
  h+='<div class="card-h">自动清理</div>';
  h+='<div style="padding:16px">';
  h+='<div style="margin-bottom:16px"><label style="display:block;margin-bottom:8px;font-weight:600">聊天记录保留天数</label><input id="chat-retention" type="number" value="7" min="1" max="365" style="width:100%;padding:12px;border-radius:var(--r-s);border:1px solid var(--line);background:var(--field);color:var(--t-1)"></div>';
  h+='<div style="margin-bottom:16px"><label style="display:block;margin-bottom:8px;font-weight:600">指令记录保留天数</label><input id="cmd-retention" type="number" value="30" min="1" max="365" style="width:100%;padding:12px;border-radius:var(--r-s);border:1px solid var(--line);background:var(--field);color:var(--t-1)"></div>';
  h+='<button onclick="saveRetention()" class="primary" style="width:100%;margin-bottom:8px">保存规则</button>';
  h+='<button onclick="cleanNow()" class="btn-mini danger" style="width:100%;padding:12px">清空聊天与指令记录</button>';
  h+='</div></div></div>';

  $('view').innerHTML=h;

  // 绑定标签切换
  document.querySelectorAll('[data-chat-tab]').forEach(btn=>{
    btn.onclick=()=>{
      document.querySelectorAll('.tab-btn').forEach(b=>b.classList.remove('active'));
      btn.classList.add('active');
      document.querySelectorAll('.chat-panel').forEach(p=>p.style.display='none');
      $('chat-'+btn.dataset.chatTab).style.display='block';
    };
  });
}

async function sendMsg(){
  const target=$('msg-target').value.trim();
  const text=$('msg-text').value.trim();
  if(!text)return alert('请输入消息内容');

  const body={message:text,target_name:target||'所有人'};
  if(target){
    const p=state.players.find(p=>p.name===target);
    if(p)body.target_uuid=p.uuid;
  }

  const res=await api('/api/messages/send',{method:'POST',body});
  if(res.success){
    $('msg-text').value='';
    alert('消息已发送');
    loadChat();
  }else{
    alert(res.error||'发送失败');
  }
}

async function saveRetention(){
  const chatDays=parseInt($('chat-retention').value)||7;
  const cmdDays=parseInt($('cmd-retention').value)||30;

  await Promise.all([
    api('/api/admin/config',{method:'POST',body:{key:'chat_retention_days',value:String(chatDays)}}),
    api('/api/admin/config',{method:'POST',body:{key:'command_retention_days',value:String(cmdDays)}})
  ]);

  alert('清理规则已保存');
}

async function cleanNow(){
  if(!confirm('确定要清空所有聊天记录和指令记录吗？此操作不可恢复！'))return;
  const res=await api('/api/admin/clean-old-data',{method:'POST'});
  if(res.success){
    alert('已清空：\\n聊天记录 '+(res.deleted_messages||0)+' 条\\n指令记录 '+(res.deleted_commands||0)+' 条');
  }else{
    alert('清空失败：'+(res.error||'未知错误'));
  }
}

// ─────────────────────────────────────────────────────────────────────
// 安全监控（正版账号 + 指令活动 + 异常）
// ─────────────────────────────────────────────────────────────────────
async function loadSecurity(){
  const [premium,commands,crashes,anomalies]=await Promise.all([
    api('/api/admin/players'),
    api('/api/admin/command-activities'),
    api('/api/admin/crashes'),
    api('/api/admin/anomalies')
  ]);
  if(premium._status===401||premium._status===403){logout();return}

  const premiumUsers=(premium.users||[]).filter(p=>p.is_premium);

  let h='';

  // 标签切换
  h+='<div style="display:flex;gap:8px;margin-bottom:16px;flex-wrap:wrap">';
  h+='<button class="tab-btn active" data-sec-tab="premium">正版 ('+premiumUsers.length+')</button>';
  h+='<button class="tab-btn" data-sec-tab="commands">指令 ('+((commands.activities||[]).length)+')</button>';
  h+='<button class="tab-btn" data-sec-tab="issues">异常</button>';
  h+='</div>';

  // 正版账号
  h+='<div class="sec-panel" id="sec-premium">';
  h+='<div class="card">';
  h+='<div class="card-h"><span>正版账号列表</span><button class="btn-mini accent" onclick="refreshPremium()">刷新</button></div>';
  if(!premiumUsers.length){
    h+='<div class="row">暂无正版玩家</div>';
  }else{
    premiumUsers.forEach(p=>{
      const msId=fmtXuid(p.xuid);
      h+='<div class="row">'+skin(p,44)+'<div class="info">'+
        '<div class="name">'+esc(p.name)+' <span class="badge green">正版</span></div>'+
        '<div class="sub">微软账户：<span class="mono">'+esc(p.gamertag||p.name)+'</span></div>'+
        (msId?'<div class="sub">XUID：<span class="mono">'+esc(msId)+'</span></div>':'')+
        (p.server_name?'<div class="sub">'+esc(p.server_name)+(p.server_ip?' · '+esc(p.server_ip):'')+'</div>':'')+
        '</div><div class="right">'+(p.is_online?'<span class="dot on"></span>在线':'离线')+'</div></div>';
    });
  }
  h+='</div></div>';

  // 指令活动
  h+='<div class="sec-panel" id="sec-commands" style="display:none">';
  h+='<div class="card">';
  h+='<div class="card-h">指令活动记录</div>';
  h+='<div style="padding:14px 18px;font-size:13px;color:var(--t-2);border-bottom:1px solid var(--sep)">仅记录指令名称，不含参数、密码或坐标。相同玩家相同指令 30 秒内自动去重。</div>';
  if(!(commands.activities||[]).length){
    h+='<div class="row">暂无指令记录</div>';
  }else{
    (commands.activities||[]).slice(0,100).forEach(cmd=>{
      h+='<div class="row"><div class="info"><div class="name">'+esc(cmd.name)+' · '+esc(cmd.command_name)+'</div><div class="sub">分类：'+esc(cmd.category)+' · '+fmtTime(cmd.created_at)+'</div></div></div>';
    });
  }
  h+='</div></div>';

  // 异常
  h+='<div class="sec-panel" id="sec-issues" style="display:none">';
  h+='<div class="card">';
  h+='<div class="card-h">崩溃记录 ('+((crashes.crashes||[]).length)+')</div>';
  if(!(crashes.crashes||[]).length){
    h+='<div class="row">暂无崩溃记录</div>';
  }else{
    (crashes.crashes||[]).slice(0,20).forEach(c=>{
      h+='<div class="row"><div class="info"><div class="name">'+esc((c.message||'').slice(0,60))+'</div><div class="sub">'+esc(c.version||'')+' · 出现 '+c.count+' 次 · '+fmtTime(c.last_seen)+'</div></div></div>';
    });
  }
  h+='</div>';

  h+='<div class="card" style="margin-top:16px">';
  h+='<div class="card-h">异常行为 ('+((anomalies.anomalies||[]).length)+')</div>';
  if(!(anomalies.anomalies||[]).length){
    h+='<div class="row">暂无异常行为</div>';
  }else{
    (anomalies.anomalies||[]).slice(0,20).forEach(a=>{
      h+='<div class="row"><div class="info"><div class="name">'+esc(fmtAnomalyType(a.type||'未知'))+' · '+esc(fmtSeverity(a.severity||'未知'))+'</div><div class="sub">'+esc((a.message||'').slice(0,60))+'</div></div></div>';
    });
  }
  h+='</div></div>';

  $('view').innerHTML=h;

  // 绑定标签切换
  document.querySelectorAll('[data-sec-tab]').forEach(btn=>{
    btn.onclick=()=>{
      document.querySelectorAll('.tab-btn').forEach(b=>b.classList.remove('active'));
      btn.classList.add('active');
      document.querySelectorAll('.sec-panel').forEach(p=>p.style.display='none');
      $('sec-'+btn.dataset.secTab).style.display='block';
    };
  });
}

async function refreshPremium(){
  if(!confirm('刷新正版状态将调用 Mojang API 重新验证玩家，确定继续？'))return;
  const res=await api('/api/admin/refresh-premium',{method:'POST'});
  if(res.success){
    alert('已刷新：\\n更新 '+(res.updated||0)+' 个玩家\\n跳过（已有XUID） '+(res.skipped||0)+' 个\\n失败 '+(res.failed||0)+' 个');
    loadSecurity();
  }else{
    alert('刷新失败：'+(res.error||'未知错误'));
  }
}

// ─────────────────────────────────────────────────────────────────────
// 系统设置（远程配置）
// ─────────────────────────────────────────────────────────────────────
async function loadSettings(){
  const cfg=await api('/api/config');
  const config=cfg.config||{};
  const keys=Object.keys(config);

  let h='';

  // 说明
  h+='<div class="card">';
  h+='<div class="card-h">远程配置说明</div>';
  h+='<div style="padding:16px;line-height:1.9;color:var(--t-2);font-size:14px">';
  h+='游戏内客户端每隔 <b style="color:var(--t-1)">60 秒</b> 自动读取一次配置，后台改完无需重启游戏即自动生效。<br><br>';
  h+='<b style="color:var(--t-1)">支持的配置项：</b><br>';
  h+='• <code>update_notice_enabled</code> - 进服时检测新版本并提示<br>';
  h+='• <code>stats_report_enabled</code> - 玩家注册数据是否上报<br>';
  h+='• <code>heartbeat_report_enabled</code> - 在线状态、延迟、模块和活动数据是否上报<br>';
  h+='• <code>anomaly_report_enabled</code> - 高速移动和瞬移异常是否上报<br>';
  h+='• <code>crash_report_enabled</code> - 崩溃信息是否上报<br>';
  h+='• <code>message_poll_enabled</code> - 是否接收后台发送的游戏内消息<br>';
  h+='• <code>chat_retention_days</code> - 聊天记录保留天数（默认 7）<br>';
  h+='• <code>command_retention_days</code> - 指令记录保留天数（默认 30）';
  h+='</div></div>';

  // 当前配置
  h+='<div class="card">';
  h+='<div class="card-h">当前配置</div>';
  if(!keys.length){
    h+='<div class="row">暂无配置项</div>';
  }else{
    keys.forEach(k=>{
      h+='<div class="row"><div class="info"><div class="name">'+esc(k)+'</div><div class="sub">当前值: <code style="background:var(--bg-2);padding:2px 6px;border-radius:4px;color:var(--brand)">'+esc(config[k])+'</code></div></div><button class="btn-mini danger" onclick="deleteConfig(&quot;'+esc(k)+'&quot;)">删除</button></div>';
    });
  }
  h+='</div>';

  // 添加配置
  h+='<div class="card">';
  h+='<div class="card-h">添加配置</div>';
  h+='<div style="padding:16px">';
  h+='<div style="margin-bottom:12px"><label style="display:block;margin-bottom:8px;font-weight:600">Key</label><input id="cfg-key" type="text" placeholder="例如: update_notice_enabled" style="width:100%;padding:12px;border-radius:var(--r-s);border:1px solid var(--line);background:var(--field);color:var(--t-1)"></div>';
  h+='<div style="margin-bottom:16px"><label style="display:block;margin-bottom:8px;font-weight:600">Value</label><input id="cfg-val" type="text" placeholder="例如: true / false" style="width:100%;padding:12px;border-radius:var(--r-s);border:1px solid var(--line);background:var(--field);color:var(--t-1)"></div>';
  h+='<button onclick="addConfig()" class="primary" style="width:100%">添加 / 更新</button>';
  h+='</div></div>';

  $('view').innerHTML=h;
}

async function addConfig(){
  const key=$('cfg-key').value.trim();
  const val=$('cfg-val').value.trim();
  if(!key)return alert('请输入 Key');

  const res=await api('/api/admin/config',{method:'POST',body:{key,value:val}});
  if(res.success){
    $('cfg-key').value='';
    $('cfg-val').value='';
    alert('配置已保存');
    loadSettings();
  }else{
    alert('保存失败');
  }
}

async function deleteConfig(key){
  if(!confirm('确定要删除配置 "'+key+'" 吗？'))return;
  // 通过设置为空值来删除
  const res=await api('/api/admin/config',{method:'POST',body:{key,value:''}});
  if(res.success){
    alert('配置已删除');
    loadSettings();
  }else{
    alert('删除失败');
  }
}

// ─────────────────────────────────────────────────────────────────────
// 工具函数：时间格式化
// ─────────────────────────────────────────────────────────────────────
function fmtTime(ts){
  if(!ts)return '—';
  const d=new Date(ts);
  const now=Date.now();
  const diff=now-ts;
  if(diff<60000)return '刚刚';
  if(diff<3600000)return Math.floor(diff/60000)+' 分钟前';
  if(diff<86400000)return Math.floor(diff/3600000)+' 小时前';
  return d.toLocaleDateString('zh-CN')+' '+d.toLocaleTimeString('zh-CN',{hour:'2-digit',minute:'2-digit'});
}
</script>
</body>
</html>
`;
