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
   登录：极夜光幕背景 + 居中悬浮的霜玻璃卡片
   ━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━ */
#login{
  position:fixed;inset:0;z-index:9999;
  display:flex;padding:24px;overflow:auto;
  overscroll-behavior:contain;
}
/* 背景一：三团静态微光压在近黑底上（fixed 使其不随卡片滚动） */
#login::before{
  content:'';position:fixed;inset:0;z-index:-2;
  background:
    radial-gradient(42% 34% at 20% 10%,rgba(10,132,255,.22),transparent 68%),
    radial-gradient(38% 32% at 84% 84%,rgba(100,210,255,.13),transparent 68%),
    radial-gradient(64% 46% at 50% 118%,rgba(10,132,255,.12),transparent 70%),
    linear-gradient(180deg,#0B0B10,#050507);
}
/* 背景二：极细网格，中心可见、四周淡出 */
#login::after{
  content:'';position:fixed;inset:0;z-index:-1;pointer-events:none;
  background-image:
    linear-gradient(to right,rgba(255,255,255,.030) 1px,transparent 1px),
    linear-gradient(to bottom,rgba(255,255,255,.030) 1px,transparent 1px);
  background-size:64px 64px;
  -webkit-mask-image:radial-gradient(74% 64% at 50% 42%,#000 18%,transparent 100%);
  mask-image:radial-gradient(74% 64% at 50% 42%,#000 18%,transparent 100%);
}
[data-theme=light] #login::before{
  background:
    radial-gradient(42% 34% at 20% 10%,rgba(0,122,255,.16),transparent 68%),
    radial-gradient(38% 32% at 84% 84%,rgba(90,200,250,.20),transparent 68%),
    linear-gradient(180deg,#FAFAFD,#E7E7EE);
}
[data-theme=light] #login::after{
  background-image:
    linear-gradient(to right,rgba(0,0,0,.035) 1px,transparent 1px),
    linear-gradient(to bottom,rgba(0,0,0,.035) 1px,transparent 1px);
}
/* 右上角主题切换（登录页独立于主应用，故自带一枚） */
.lg-theme{
  position:fixed;top:calc(16px + env(safe-area-inset-top));right:16px;z-index:2;
  width:40px;height:40px;border-radius:50%;padding:0;
  display:flex;align-items:center;justify-content:center;
  background:var(--card);border:1px solid var(--card-b);color:var(--t-2);
  cursor:pointer;transition:color .2s var(--ease),background .2s var(--ease),transform .15s var(--ease);
}
.lg-theme:hover{color:var(--t-1);background:var(--bg-3)}
.lg-theme:active{transform:scale(.94)}
.lg-theme svg{width:18px;height:18px}
/* 卡片 */
.login-card{
  position:relative;margin:auto;
  width:100%;max-width:412px;padding:42px 38px 28px;
  border-radius:24px;
  background:var(--glass);border:1px solid var(--card-b);
  box-shadow:var(--shadow),var(--rim);
  backdrop-filter:var(--blur);-webkit-backdrop-filter:var(--blur);
  animation:pop .5s var(--ease) both;
}
.login-card::before{                               /* 顶部高光描边 */
  content:'';position:absolute;left:12%;right:12%;top:-1px;height:1px;
  background:linear-gradient(90deg,transparent,rgba(255,255,255,.5),transparent);
  pointer-events:none;
}
@keyframes pop{from{opacity:0;transform:translateY(22px) scale(.975)}to{opacity:1;transform:none}}
/* 品牌标记：玻璃方块 + 网络拓扑图标（中心为控制台） */
.logo-mark{
  width:60px;height:60px;border-radius:18px;margin:0 auto 20px;
  display:flex;align-items:center;justify-content:center;
  color:#fff;position:relative;
  background:linear-gradient(150deg,#7FD4FF 0%,#2E90FF 46%,#0A57E0 100%);
  box-shadow:0 16px 34px -12px rgba(10,132,255,.75),0 4px 12px rgba(0,0,0,.22),inset 0 1px 0 rgba(255,255,255,.45);
}
.logo-mark::after{
  content:'';position:absolute;inset:6px;border-radius:13px;
  border:1px solid rgba(255,255,255,.20);pointer-events:none;
}
.logo-mark svg{width:28px;height:28px;position:relative;filter:drop-shadow(0 1px 2px rgba(0,20,60,.35))}
.login-card h1{
  margin:0;font-size:26px;font-weight:800;
  text-align:center;color:var(--t-1);letter-spacing:-.3px;
}
.lg-sub{                                           /* 副标题：两侧细线收束 */
  margin:9px 0 28px;display:flex;align-items:center;justify-content:center;gap:12px;
  font-size:12.5px;font-weight:500;color:var(--t-3);letter-spacing:.14em;
}
.lg-sub::before,.lg-sub::after{content:'';height:1px;flex:0 0 32px}
.lg-sub::before{background:linear-gradient(90deg,transparent,var(--line))}
.lg-sub::after{background:linear-gradient(90deg,var(--line),transparent)}
/* 输入行：前置图标 + 聚焦态 */
.field{position:relative;margin-bottom:14px}
.field .fi{
  position:absolute;left:15px;top:50%;transform:translateY(-50%);
  width:18px;height:18px;color:var(--t-3);pointer-events:none;
  transition:color .22s var(--ease);
}
.field .fi svg{width:18px;height:18px;display:block}
.field:focus-within .fi{color:var(--brand)}
.field input{
  width:100%;padding:15px 16px 15px 43px;border-radius:14px;
  border:1px solid var(--line);background:var(--field);
  font-size:15px;color:var(--t-1);font-weight:500;
  transition:border-color .22s var(--ease),box-shadow .22s var(--ease),background .22s var(--ease);
}
.field input::placeholder{color:var(--t-3);font-weight:400}
.field input:focus{background:var(--bg-2)}
.field input.has-eye{padding-right:48px}
/* 浏览器自动填充：暗色下强制保留输入框底色与字色 */
.field input:-webkit-autofill,
.field input:-webkit-autofill:hover,
.field input:-webkit-autofill:focus{
  -webkit-text-fill-color:var(--t-1);
  -webkit-box-shadow:0 0 0 100px var(--bg-3) inset;
  transition:background-color 9999s ease-out 0s;
}
.eye{
  position:absolute;right:8px;top:50%;transform:translateY(-50%);
  width:32px;height:32px;padding:0;border:none;border-radius:9px;
  background:transparent;color:var(--t-3);cursor:pointer;
  display:flex;align-items:center;justify-content:center;
  transition:color .2s var(--ease),background .2s var(--ease);
}
.eye:hover{color:var(--t-1);background:var(--hover)}
.eye svg{width:18px;height:18px}
/* 主按钮（登录页与设置有卡内的主操作共用） */
button.primary{
  position:relative;width:100%;padding:15px;border:none;border-radius:14px;
  background:linear-gradient(180deg,#2E90FF,var(--brand));
  color:#fff;font-size:15px;font-weight:700;letter-spacing:.06em;
  cursor:pointer;overflow:hidden;
  box-shadow:0 12px 26px -14px rgba(10,132,255,.95),inset 0 1px 0 rgba(255,255,255,.24);
  transition:filter .2s var(--ease),transform .15s var(--ease);
  touch-action:manipulation;
}
button.primary:hover{filter:brightness(1.08)}
button.primary:active{transform:scale(.985)}
button.primary:disabled{opacity:.75;cursor:not-allowed;filter:none;transform:none}
.lb{display:flex;align-items:center;justify-content:center;gap:9px}
.sp{                                                /* 提交中的旋转指示器 */
  display:none;width:16px;height:16px;border-radius:50%;flex-shrink:0;
  border:2px solid rgba(255,255,255,.32);border-top-color:#fff;
  animation:spin .7s linear infinite;
}
#lg-btn.loading .sp{display:block}
@keyframes spin{to{transform:rotate(360deg)}}
/* 报错 */
.err{
  margin-top:16px;font-size:13px;color:var(--err);text-align:center;
  font-weight:600;padding:11px 12px;border-radius:12px;
  background:rgba(255,107,107,.10);border:1px solid rgba(255,107,107,.22);
  display:none;
}
.err.show{display:block;animation:shake .42s var(--ease)}
@keyframes shake{0%,100%{transform:translateX(0)}18%{transform:translateX(-7px)}38%{transform:translateX(6px)}58%{transform:translateX(-4px)}78%{transform:translateX(3px)}}
/* 页脚 */
.lg-foot{
  margin-top:26px;padding-top:18px;border-top:1px solid var(--sep);
  text-align:center;font-size:11.5px;color:var(--t-3);letter-spacing:.05em;
}

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
    background:linear-gradient(150deg,#7FD4FF 0%,#2E90FF 46%,#0A57E0 100%);
    display:flex;align-items:center;justify-content:center;
    color:#fff;
    box-shadow:0 8px 20px -8px rgba(10,132,255,.70),inset 0 1px 0 rgba(255,255,255,.40);
  }
  .brand-mark svg{width:22px;height:22px}
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
.btn-mini svg{width:14px;height:14px;vertical-align:-2px;margin-right:5px}
.btn-mini.accent{background:var(--brand);border-color:transparent;color:#fff}
.btn-mini.ok{background:var(--ok);border-color:transparent;color:#fff}
[data-theme=dark] .btn-mini.ok{color:#0B2415}
.btn-mini.danger{background:transparent;border-color:var(--err);color:var(--err)}

/* 页内分段控件（iOS 胶囊，聊天 / 安全监控的分组切换） */
.seg{
  display:inline-flex;gap:2px;padding:3px;margin-bottom:var(--gap);
  background:var(--bg-2);border:1px solid var(--card-b);border-radius:13px;
  max-width:100%;overflow-x:auto;scrollbar-width:none;
}
.seg::-webkit-scrollbar{display:none}
.seg button{
  padding:8px 15px;border:none;border-radius:10px;background:transparent;
  color:var(--t-2);font-size:13px;font-weight:600;white-space:nowrap;cursor:pointer;
  transition:background .2s var(--ease),color .2s var(--ease),box-shadow .2s var(--ease);
}
.seg button:hover{color:var(--t-1)}
.seg button.on{background:var(--card);color:var(--t-1);box-shadow:0 2px 10px -2px rgba(0,0,0,.35)}

/* 图表容器：必须给固定高度，否则 canvas 会塌缩（国家分布曾缩成 30px） */
.chart-box{position:relative;height:230px;padding:16px 18px}
.chart-box.tall{height:260px}

/* 空状态 */
.empty{padding:34px 18px;text-align:center;color:var(--t-3);font-size:13px;line-height:1.7}
.empty svg{width:26px;height:26px;opacity:.45;display:block;margin:0 auto 9px}

/* ── 聊天：气泡流 + iOS 输入条 ───────────────────────────────────── */
.feed{padding:16px 18px;display:flex;flex-direction:column;gap:12px;max-height:56vh;overflow-y:auto}
.bub{max-width:min(80%,540px);display:flex;flex-direction:column;gap:5px}
.bub .who{font-size:11.5px;color:var(--t-3);letter-spacing:.02em;padding:0 4px}
.bub .txt{
  padding:10px 14px;border-radius:16px;word-break:break-word;
  background:var(--bg-2);border:1px solid var(--card-b);
  font-size:14px;line-height:1.6;color:var(--t-1);
}
.bub.other .txt{border-bottom-left-radius:6px}
.bub.me{align-self:flex-end;align-items:flex-end}
.bub.me .txt{
  background:linear-gradient(180deg,#2E90FF,var(--brand));
  border-color:transparent;color:#fff;border-bottom-right-radius:6px;
  box-shadow:0 8px 20px -14px rgba(10,132,255,.95);
}
.composer{display:flex;align-items:center;gap:10px;padding:12px;border-top:1px solid var(--sep)}
.composer input{
  flex:1;min-width:0;padding:11px 15px;border-radius:20px;
  border:1px solid var(--card-b);background:var(--field);color:var(--t-1);
  font-size:14px;outline:none;transition:border-color .2s var(--ease);
}
.composer input:focus{border-color:var(--brand)}
.send-btn{
  width:38px;height:38px;border-radius:50%;border:none;flex-shrink:0;
  background:var(--brand);color:#fff;cursor:pointer;
  display:flex;align-items:center;justify-content:center;
  box-shadow:0 8px 18px -10px rgba(10,132,255,.95);
  transition:filter .2s var(--ease),transform .15s var(--ease);
}
.send-btn:hover{filter:brightness(1.1)}
.send-btn:active{transform:scale(.94)}
.send-btn svg{width:17px;height:17px}

/* 跨服在线玩家胶囊 */
.chips{display:flex;flex-wrap:wrap;gap:8px;padding:14px 18px}
.chip{
  display:inline-flex;align-items:center;gap:8px;padding:5px 12px 5px 5px;
  border-radius:999px;background:var(--bg-2);border:1px solid var(--card-b);
  font-size:13px;font-weight:600;color:var(--t-1);
}
.chip .av{width:24px;height:24px;border-radius:50%;flex-shrink:0;box-shadow:inset 0 0 0 1px var(--card-b)}
.chip .lat{font-size:11.5px;font-weight:500;color:var(--t-3)}

/* ── 指令记录：时间线 ───────────────────────────────────────────── */
.tl{padding:8px 18px 12px}
.tl-i{position:relative;padding:10px 0 10px 21px;border-bottom:1px solid var(--sep)}
.tl-i:last-child{border-bottom:none}
.tl-i::before{
  content:'';position:absolute;left:2px;top:15px;width:7px;height:7px;border-radius:50%;
  background:var(--brand);box-shadow:0 0 0 3px var(--ind);
}
.tl-i::after{content:'';position:absolute;left:5px;top:26px;bottom:-5px;width:1px;background:var(--sep)}
.tl-i:last-child::after{display:none}
.tl-t{font-size:13.5px;font-weight:600;color:var(--t-1)}
.tl-s{font-size:12px;color:var(--t-3);margin-top:3px}

/* ── 设置：开关行（iOS toggle） ─────────────────────────────────── */
.sw{display:flex;align-items:center;gap:14px;padding:13px 18px;border-bottom:1px solid var(--sep)}
.sw:last-child{border-bottom:none}
.sw .info{flex:1;min-width:0}
.sw .name{font-size:13.5px;font-weight:600;color:var(--t-1)}
.sw .sub{font-size:12px;color:var(--t-2);margin-top:3px;line-height:1.5}
.toggle{
  position:relative;width:50px;height:30px;border-radius:15px;flex-shrink:0;
  background:var(--line);border:none;cursor:pointer;transition:background .24s var(--ease);
}
.toggle::after{
  content:'';position:absolute;top:2.5px;left:2.5px;width:25px;height:25px;border-radius:50%;
  background:#fff;box-shadow:0 2px 6px rgba(0,0,0,.32);
  transition:transform .24s var(--ease);
}
.toggle.on{background:var(--ok)}
.toggle.on::after{transform:translateX(20px)}
/* 只覆盖关闭态：写成 [data-theme=dark] .toggle 会把上面的开启态一并压掉 */
[data-theme=dark] .toggle:not(.on){background:#3A3A44}
.hint{padding:12px 18px;font-size:12.5px;color:var(--t-2);line-height:1.7;border-bottom:1px solid var(--sep)}

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
  .login-card{padding:36px 24px 24px;border-radius:22px}
  .logo-mark{width:56px;height:56px;margin-bottom:18px}
  .lg-sub{margin-bottom:24px;font-size:12px}
}
@media(prefers-reduced-motion:reduce){
  .login-card{animation:none}
  .err.show{animation:none}
  .sp{animation-duration:1.6s}
  .sheet{transition:none}
}
  </style>
</head>
<body>

<div class="glow g1"></div>
<div class="glow g2"></div>

<div id="login">
  <button class="lg-theme" id="lg-theme" type="button" aria-label="切换主题"></button>
  <form class="login-card" id="lg-form" novalidate>
    <div class="logo-mark" data-mark></div>
    <h1>yiyiaddon</h1>
    <p class="lg-sub">远程管理控制台</p>
    <div class="field">
      <span class="fi"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.7" stroke-linecap="round"><circle cx="12" cy="8" r="3.6"/><path d="M4.8 20.4c.9-3.6 3.9-5.4 7.2-5.4s6.3 1.8 7.2 5.4"/></svg></span>
      <input id="lg-user" type="text" placeholder="用户名" autocomplete="username" autocapitalize="none" spellcheck="false">
    </div>
    <div class="field">
      <span class="fi"><svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.7" stroke-linecap="round" stroke-linejoin="round"><rect x="4.5" y="10.5" width="15" height="10" rx="3"/><path d="M8 10.5V8a4 4 0 0 1 8 0v2.5"/><circle cx="12" cy="15.5" r="1.2" fill="currentColor" stroke="none"/></svg></span>
      <input id="lg-pass" class="has-eye" type="password" placeholder="密码" autocomplete="current-password">
      <button class="eye" id="lg-eye" type="button" aria-label="显示密码"></button>
    </div>
    <button class="primary" id="lg-btn" type="submit"><span class="lb"><span class="sp"></span><span id="lg-btn-txt">登 录</span></span></button>
    <div class="err" id="lg-err"></div>
    <div class="lg-foot">仅限授权管理员访问</div>
  </form>
</div>

<div id="app">
  <div class="shell">
    <aside class="sidebar">
      <div class="brand">
        <div class="brand-mark" data-mark></div>
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
  chatTop: 0          // 聊天页已渲染到的最新消息 id，用于判断增量刷新
};

// 图表实例（重建卡片时必须销毁）与聊天页轮询句柄
let chartDaily = null, chartCountry = null, chatTimer = null;

// 线性图标（描边风格，随文字颜色着色；不使用 emoji，与游戏内 UI 一致）
const ICONS = {
  dash:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><rect x="3" y="3" width="7.5" height="7.5" rx="2"/><rect x="13.5" y="3" width="7.5" height="7.5" rx="2"/><rect x="3" y="13.5" width="7.5" height="7.5" rx="2"/><rect x="13.5" y="13.5" width="7.5" height="7.5" rx="2"/></svg>',
  players:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="7.5" r="3.8"/><path d="M4.5 20.5c.8-3.8 3.9-5.7 7.5-5.7s6.7 1.9 7.5 5.7"/></svg>',
  chat:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M21 14.5a2 2 0 0 1-2 2H8l-4.5 4V5.5a2 2 0 0 1 2-2H19a2 2 0 0 1 2 2z"/></svg>',
  shield:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M12 3l7.5 3v5.2c0 4.6-3.1 8.6-7.5 9.8-4.4-1.2-7.5-5.2-7.5-9.8V6z"/></svg>',
  gear:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="3"/><path d="M19.4 15a1.65 1.65 0 0 0 .33 1.82l.06.06a2 2 0 0 1 0 2.83 2 2 0 0 1-2.83 0l-.06-.06a1.65 1.65 0 0 0-1.82-.33 1.65 1.65 0 0 0-1 1.51V21a2 2 0 0 1-4 0v-.09A1.65 1.65 0 0 0 9 19.4a1.65 1.65 0 0 0-1.82.33l-.06.06a2 2 0 0 1-2.83 0 2 2 0 0 1 0-2.83l.06-.06a1.65 1.65 0 0 0 .33-1.82 1.65 1.65 0 0 0-1.51-1H3a2 2 0 0 1 0-4h.09A1.65 1.65 0 0 0 4.6 9a1.65 1.65 0 0 0-.33-1.82l-.06-.06a2 2 0 0 1 0-2.83 2 2 0 0 1 2.83 0l.06.06a1.65 1.65 0 0 0 1.82.33H9a1.65 1.65 0 0 0 1-1.51V3a2 2 0 0 1 4 0v.09a1.65 1.65 0 0 0 1 1.51 1.65 1.65 0 0 0 1.82-.33l.06-.06a2 2 0 0 1 2.83 0 2 2 0 0 1 0 2.83l-.06.06a1.65 1.65 0 0 0-.33 1.82V9a1.65 1.65 0 0 0 1.51 1H21a2 2 0 0 1 0 4h-.09a1.65 1.65 0 0 0-1.51 1z"/></svg>',
  moon:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M21 12.79A9 9 0 1 1 11.21 3 7 7 0 0 0 21 12.79z"/></svg>',
  sun:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><circle cx="12" cy="12" r="4"/><path d="M12 2v2M12 20v2M4.93 4.93l1.41 1.41M17.66 17.66l1.41 1.41M2 12h2M20 12h2M4.93 19.07l1.41-1.41M17.66 6.34l1.41-1.41"/></svg>',
  logout:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"/><path d="M16 17l5-5-5-5"/><path d="M21 12H9"/></svg>',
  // 品牌标记：中心控制台 + 四向节点（登录卡与侧栏共用同一份）
  mark:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.7" stroke-linecap="round" stroke-linejoin="round"><path d="M12 6.2V9.2M17.8 12H14.8M12 17.8V14.8M6.2 12H9.2"/><circle cx="12" cy="4.2" r="2"/><circle cx="19.8" cy="12" r="2"/><circle cx="12" cy="19.8" r="2"/><circle cx="4.2" cy="12" r="2"/><rect x="9.2" y="9.2" width="5.6" height="5.6" rx="1.8" fill="currentColor" stroke="none"/></svg>',
  eye:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.7" stroke-linecap="round" stroke-linejoin="round"><path d="M2.6 12S6.1 5.6 12 5.6 21.4 12 21.4 12 17.9 18.4 12 18.4 2.6 12 2.6 12z"/><circle cx="12" cy="12" r="3.1"/></svg>',
  eyeOff:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.7" stroke-linecap="round" stroke-linejoin="round"><path d="M10.6 5.9A9.4 9.4 0 0 1 12 5.6c5.9 0 9.4 6.4 9.4 6.4a17.4 17.4 0 0 1-3.4 4.1M6.5 7.6A17.6 17.6 0 0 0 2.6 12S6.1 18.4 12 18.4c1.4 0 2.7-.4 3.8-.9"/><path d="M10 10a3.1 3.1 0 0 0 4.2 4.4"/><path d="M3.8 3.8l16.4 16.4"/></svg>',
  send:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M5 12h13"/><path d="M12.5 6l6 6-6 6"/></svg>',
  refresh:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.9" stroke-linecap="round" stroke-linejoin="round"><path d="M20.4 12a8.4 8.4 0 1 1-2.5-5.9"/><path d="M20.4 4.6v5.2h-5.2"/></svg>',
  inbox:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.7" stroke-linecap="round" stroke-linejoin="round"><path d="M3.5 12.8l2.6-7.3h11.8l2.6 7.3v4.7a2 2 0 0 1-2 2H5.5a2 2 0 0 1-2-2z"/><path d="M3.5 12.8h4.8l1 2.1h5.4l1-2.1h4.8"/></svg>',
  trash:'<svg viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M4.5 7h15"/><path d="M9.5 7V4.6h5V7"/><path d="M6.8 7l.9 12.4h8.6L17.2 7"/></svg>'
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

  // 品牌标记：登录卡与侧栏共用同一份图标
  document.querySelectorAll('[data-mark]').forEach(el=>{el.innerHTML=ICONS.mark});

  // 登录页：主题切换 / 密码显隐 / 提交（按钮点击与回车共用同一入口）
  const lgForm=$('lg-form');
  if(lgForm){
    const errEl=$('lg-err'),btnEl=$('lg-btn'),userEl=$('lg-user'),passEl=$('lg-pass');
    const eyeEl=$('lg-eye'),themeEl=$('lg-theme');

    // 主题切换（登录页不经过 init()，需自行绑定）
    const paintTheme=()=>{
      const dark=document.documentElement.getAttribute('data-theme')!=='light';
      themeEl.innerHTML=dark?ICONS.sun:ICONS.moon;
      themeEl.title=dark?'切换到亮色':'切换到暗色';
    };
    paintTheme();
    themeEl.onclick=()=>{
      const t=document.documentElement.getAttribute('data-theme')==='dark'?'light':'dark';
      document.documentElement.setAttribute('data-theme',t);
      localStorage.setItem('theme',t);
      paintTheme();
    };

    // 密码显隐
    let eyeOn=false;
    eyeEl.innerHTML=ICONS.eye;
    eyeEl.onclick=()=>{
      eyeOn=!eyeOn;
      passEl.type=eyeOn?'text':'password';
      eyeEl.innerHTML=eyeOn?ICONS.eyeOff:ICONS.eye;
      eyeEl.setAttribute('aria-label',eyeOn?'隐藏密码':'显示密码');
      passEl.focus();
    };

    // 报错：每次重新播放抖动动画
    const showErr=(msg)=>{
      errEl.textContent=msg;
      errEl.classList.remove('show');
      void errEl.offsetWidth;
      errEl.classList.add('show');
    };
    const setLoading=(on)=>{
      btnEl.disabled=on;
      btnEl.classList.toggle('loading',on);
      $('lg-btn-txt').textContent=on?'登录中':'登 录';
    };

    lgForm.onsubmit=async(e)=>{
      e.preventDefault();
      if(btnEl.disabled)return;
      const u=userEl.value.trim(),p=passEl.value;
      if(!u||!p){
        showErr('请输入用户名和密码');
        (!u?userEl:passEl).focus();
        return;
      }
      errEl.classList.remove('show');
      errEl.textContent='';
      setLoading(true);
      try{
        const res=await api('/api/admin/login',{method:'POST',body:{username:u,password:p}});
        if(res.success&&res.token){
          localStorage.setItem('admin_token',res.token);
          location.reload();
          return;
        }
        showErr(res.error||'登录失败');
      }catch(err){
        showErr('网络错误：'+err.message);
      }
      setLoading(false);
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

  // 面板刷新：Cloudflare Workers 没有 WebSocket 服务端，仪表盘固定 5 秒轮询一次
  setInterval(()=>{
    if(state.tab==='dashboard')loadDashboard();
  },5000);
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
  if(id!=='chat')stopChatPoll();   // 离开聊天页就停掉消息轮询
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
    h+='<div class="chart-box tall"><canvas id="chart-daily"></canvas></div>';
    h+='</div>';
  }

  // 国家分布图表
  if(a.country_distribution&&a.country_distribution.length>0){
    h+='<div class="card">';
    h+='<div class="card-h">国家分布 Top 10</div>';
    h+='<div class="chart-box"><canvas id="chart-country"></canvas></div>';
    h+='</div>';
  }

  // 版本分布
  if(a.version_distribution&&a.version_distribution.length>0){
    h+='<div class="card">';
    h+='<div class="card-h">版本分布</div>';
    h+='<div class="hint">版本号由玩家客户端上报，玩家用新版本进服后这一行会就地更新为新版本号。</div>';
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
// 仪表盘每 5 秒重建一次卡片，画布会跟着换新，因此每次渲染前必须销毁上一个实例，
// 否则 Chart.js 会不断堆积挂在已卸载画布上的图表对象。
function renderDailyChart(data){
  const ctx=document.getElementById('chart-daily');
  if(!ctx)return;
  if(chartDaily){chartDaily.destroy();chartDaily=null;}
  const accent=themeAccent();

  chartDaily=new Chart(ctx,{
    type:'line',
    data:{
      labels:data.map(d=>new Date(d.date).toLocaleDateString('zh-CN',{month:'numeric',day:'numeric'})),
      datasets:[{
        label:'活跃玩家',
        data:data.map(d=>d.active),
        borderColor:accent,
        backgroundColor:accent+'1F',
        borderWidth:2,
        tension:.38,
        fill:true,
        pointRadius:0,
        pointHoverRadius:4,
        pointHoverBackgroundColor:accent
      }]
    },
    options:{
      responsive:true,
      maintainAspectRatio:false,
      layout:{padding:{top:6,right:6}},
      plugins:{
        legend:{display:false},
        tooltip:{displayColors:false,padding:10,cornerRadius:10,titleFont:{size:12},bodyFont:{size:12}}
      },
      scales:{
        // 日期标签一律水平：交给 Chart.js 自动旋转会变成斜排数字
        x:{
          grid:{display:false},
          border:{display:false},
          ticks:{maxRotation:0,minRotation:0,autoSkip:true,maxTicksLimit:7,font:{size:11},color:chartMuted()}
        },
        y:{
          beginAtZero:true,
          grid:{color:chartGrid()},
          border:{display:false},
          ticks:{precision:0,maxTicksLimit:5,font:{size:11},color:chartMuted()}
        }
      }
    }
  });
}

// 渲染国家分布图
function renderCountryChart(data){
  const ctx=document.getElementById('chart-country');
  if(!ctx)return;
  if(chartCountry){chartCountry.destroy();chartCountry=null;}

  const colors=['#0A84FF','#5BD37A','#64D2FF','#FF6B6B','#F59E0B','#AF52DE','#30D158','#FF9F0A','#5E5CE6','#FF453A'];

  chartCountry=new Chart(ctx,{
    type:'doughnut',
    data:{
      labels:data.map(d=>countryName(d.client_country)),
      datasets:[{
        data:data.map(d=>d.c),
        backgroundColor:colors,
        borderWidth:0,
        hoverOffset:6
      }]
    },
    options:{
      responsive:true,
      maintainAspectRatio:false,
      cutout:'62%',
      layout:{padding:6},
      plugins:{
        legend:{
          position:'bottom',
          labels:{usePointStyle:true,pointStyle:'circle',boxWidth:7,boxHeight:7,padding:14,font:{size:11.5},color:chartMuted()}
        },
        tooltip:{displayColors:false,padding:10,cornerRadius:10}
      }
    }
  });
}

// 图表与当前主题一致的辅助线与文字色（跟随 CSS 变量，切主题后重渲染即同步）
function chartMuted(){
  return getComputedStyle(document.documentElement).getPropertyValue('--t-3').trim()||'#6E6E76';
}
function chartGrid(){
  const sep=getComputedStyle(document.documentElement).getPropertyValue('--sep').trim();
  return sep||'rgba(255,255,255,.06)';
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
// 聊天消息：跨服频道（玩家公屏 + 管理员广播）/ 私信 / 清理
// ─────────────────────────────────────────────────────────────────────
let chatMsgs=[];            // 最近一次拉到的消息，供分段过滤与增量刷新复用
let chatSeg='channel';      // 当前分段：channel 跨服频道 / dm 私信 / clean 清理

async function loadChat(){
  const [msgs,players]=await Promise.all([
    api('/api/messages/history'),
    apiCached('/api/admin/players',3000)
  ]);
  if(msgs._status===401||msgs._status===403){logout();return}

  state.players=players.users||[];
  chatMsgs=Array.isArray(msgs)?msgs:[];
  state.chatTop=chatMsgs.length?chatMsgs[0].id:0;

  const channel=chatMsgs.filter(m=>!m.target_uuid);
  const dm=chatMsgs.filter(m=>m.target_uuid);
  const online=state.players.filter(p=>p.is_online);

  let h='';

  h+='<div class="seg">';
  h+='<button data-chat-seg="channel" class="'+(chatSeg==='channel'?'on':'')+'">跨服频道 <span id="cnt-channel">'+channel.length+'</span></button>';
  h+='<button data-chat-seg="dm" class="'+(chatSeg==='dm'?'on':'')+'">私信 <span id="cnt-dm">'+dm.length+'</span></button>';
  h+='<button data-chat-seg="clean" class="'+(chatSeg==='clean'?'on':'')+'">清理</button>';
  h+='</div>';

  // ── 跨服频道：在线玩家 + 消息流 + 管理员发言
  h+='<div class="chat-panel" id="chat-channel">';
  h+='<div class="card">';
  h+='<div class="card-h">跨服在线 ('+online.length+')</div>';
  if(!online.length){
    h+='<div class="empty">'+ICONS.players+'当前没有在线玩家</div>';
  }else{
    h+='<div class="chips">';
    online.forEach(p=>{
      const lat=(p.server_latency!=null&&p.server_latency>0)?'<span class="lat">'+Math.round(p.server_latency)+' ms</span>':'';
      h+='<span class="chip">'+skin(p,24)+esc(p.name)+lat+'</span>';
    });
    h+='</div>';
  }
  h+='</div>';

  h+='<div class="card">';
  h+='<div class="card-h"><span>跨服频道</span><span style="font-size:12px;font-weight:500;color:var(--t-3)">玩家公屏与管理员广播共用这一条</span></div>';
  h+='<div class="feed" id="feed-channel"></div>';
  h+='<div class="composer"><input id="msg-channel" type="text" maxlength="300" placeholder="以管理员身份发到跨服频道…"><button class="send-btn" type="button" data-send="channel" aria-label="发送">'+ICONS.send+'</button></div>';
  h+='</div>';
  h+='</div>';

  // ── 私信：管理员与单个玩家之间
  h+='<div class="chat-panel" id="chat-dm" style="display:none">';
  h+='<div class="card">';
  h+='<div class="card-h"><span>私信</span><span style="font-size:12px;font-weight:500;color:var(--t-3)">玩家可在游戏内用 .回复 回你</span></div>';
  h+='<div class="hint">收件人<select id="dm-target" style="margin-left:10px;padding:7px 10px;border-radius:9px;border:1px solid var(--line);background:var(--field);color:var(--t-1);font-size:13px">';
  h+='<option value="">选择玩家…</option>';
  state.players.forEach(p=>{
    h+='<option value="'+esc(p.name)+'">'+esc(p.name)+(p.is_online?' · 在线':'')+'</option>';
  });
  h+='</select></div>';
  h+='<div class="feed" id="feed-dm"></div>';
  h+='<div class="composer"><input id="msg-dm" type="text" maxlength="300" placeholder="给选中的玩家发私信…"><button class="send-btn" type="button" data-send="dm" aria-label="发送">'+ICONS.send+'</button></div>';
  h+='</div>';
  h+='</div>';

  // ── 清理
  h+='<div class="chat-panel" id="chat-clean" style="display:none">';
  h+='<div class="card">';
  h+='<div class="card-h">清理记录</div>';
  h+='<div class="hint">后端没有定时任务，清理只在点下面这个按钮时发生，且为全量清空（不按保留天数删过期数据）。</div>';
  h+='<div style="padding:16px"><button class="btn-mini danger" type="button" data-clean style="width:100%;padding:12px">清空聊天与指令记录</button></div>';
  h+='</div>';
  h+='</div>';

  $('view').innerHTML=h;
  applyChatSeg();
  renderChatFeeds();
  bindChat();
  startChatPoll();
}

/** 只切分段显示，不重新拉数据 */
function applyChatSeg(){
  document.querySelectorAll('[data-chat-seg]').forEach(b=>b.classList.toggle('on',b.dataset.chatSeg===chatSeg));
  document.querySelectorAll('.chat-panel').forEach(p=>p.style.display='none');
  const cur=$('chat-'+chatSeg);
  if(cur)cur.style.display='block';
}

function bindChat(){
  document.querySelectorAll('[data-chat-seg]').forEach(b=>{
    b.onclick=()=>{chatSeg=b.dataset.chatSeg;applyChatSeg();};
  });
  document.querySelectorAll('[data-send]').forEach(b=>{
    b.onclick=()=>sendMsg(b.dataset.send);
  });
  ['channel','dm'].forEach(kind=>{
    const input=$('msg-'+kind);
    if(input)input.onkeydown=e=>{if(e.key==='Enter'){e.preventDefault();sendMsg(kind);}};
  });
  const clean=document.querySelector('[data-clean]');
  if(clean)clean.onclick=cleanNow;
}

/** 一条消息 → 一个气泡：管理员自己在右侧（蓝），其他人在左侧（灰） */
function bubble(m){
  const me=m.from_admin===1;
  const toAdmin=m.target_uuid==='__ADMIN__';
  const scope=toAdmin?' → 管理员':(m.target_uuid?' → '+esc(m.target_name||''):'');
  const title=(me?'管理员':esc(m.sender||'未知'))+scope;
  return '<div class="bub '+(me?'me':'other')+'">'
    +'<div class="who">'+title+' · '+fmtTime(m.created_at)+'</div>'
    +'<div class="txt">'+esc(m.message)+'</div></div>';
}

function renderChatFeeds(){
  // 历史按时间倒序返回，聊天流要「新的在下面」，所以反过来
  const channel=chatMsgs.filter(m=>!m.target_uuid).slice().reverse();
  const dm=chatMsgs.filter(m=>m.target_uuid).slice().reverse();
  fillFeed($('feed-channel'),channel,'暂无跨服聊天消息');
  fillFeed($('feed-dm'),dm,'暂无私信记录');
  const c1=$('cnt-channel');if(c1)c1.textContent=channel.length;
  const c2=$('cnt-dm');if(c2)c2.textContent=dm.length;
}

/** 重绘消息流：用户本来就在底部才继续贴底，翻旧消息时不打断 */
function fillFeed(el,list,emptyText){
  if(!el)return;
  const atBottom=el.scrollTop+el.clientHeight>=el.scrollHeight-60;
  el.innerHTML=list.length
    ?list.slice(-60).map(bubble).join('')
    :'<div class="empty">'+ICONS.inbox+emptyText+'</div>';
  if(atBottom)el.scrollTop=el.scrollHeight;
}

async function pollChat(){
  if(state.tab!=='chat'){stopChatPoll();return}
  const msgs=await api('/api/messages/history');
  if(!Array.isArray(msgs))return;
  const top=msgs.length?msgs[0].id:0;
  if(top===state.chatTop)return;   // 没有新消息就不重绘，避免打断正在输入的文本
  chatMsgs=msgs;
  state.chatTop=top;
  renderChatFeeds();
}

function startChatPoll(){stopChatPoll();chatTimer=setInterval(pollChat,5000)}
function stopChatPoll(){if(chatTimer){clearInterval(chatTimer);chatTimer=null}}

/**
 * 发送消息：channel 发到跨服频道（所有人可见），dm 发私信给选中玩家。
 * 发完只刷新消息流，不整页重绘，输入焦点与已输入的文字都不受影响。
 */
async function sendMsg(kind){
  const input=$('msg-'+kind);
  if(!input)return;
  const text=input.value.trim();
  if(!text){input.focus();return;}

  const body={message:text,target_name:'所有人'};
  if(kind==='dm'){
    const sel=$('dm-target');
    const name=sel?sel.value:'';
    if(!name)return alert('请先选择收件人');
    const p=state.players.find(x=>x.name===name);
    body.target_name=name;
    if(p)body.target_uuid=p.uuid;
  }

  input.disabled=true;
  const res=await api('/api/messages/send',{method:'POST',body});
  input.disabled=false;
  if(!res.success)return alert(res.error||'发送失败');
  // 广播只投给发送那一刻在线的玩家；一个在线都没有时明确告知，避免以为消息已送达
  if(kind==='channel'&&res.delivered===0)alert('当前无人在线，消息未投递');

  input.value='';
  const msgs=await api('/api/messages/history');
  if(Array.isArray(msgs)){
    chatMsgs=msgs;
    state.chatTop=msgs.length?msgs[0].id:0;
    renderChatFeeds();
  }
  input.focus();
}

async function cleanNow(){
  if(!confirm('确定要清空所有聊天记录和指令记录吗？此操作不可恢复！'))return;
  const res=await api('/api/admin/clean-old-data',{method:'POST'});
  if(res.success){
    alert('已清空：\\n聊天记录 '+(res.deleted_messages||0)+' 条\\n指令记录 '+(res.deleted_commands||0)+' 条');
    loadChat();
  }else{
    alert('清空失败：'+(res.error||'未知错误'));
  }
}

// ─────────────────────────────────────────────────────────────────────
// 安全监控（正版账号 + 指令活动 + 异常）
// ─────────────────────────────────────────────────────────────────────
let secSeg='account';        // 安全监控当前分段：account 账号安全 / commands 指令记录 / issues 崩溃与异常

async function loadSecurity(){
  const [players,commands,crashes,anomalies]=await Promise.all([
    api('/api/admin/players'),
    api('/api/admin/command-activities'),
    api('/api/admin/crashes'),
    api('/api/admin/anomalies')
  ]);
  if(players._status===401||players._status===403){logout();return}

  const users=players.users||[];
  const premiumUsers=users.filter(p=>p.is_premium);
  const vpnCount=users.filter(p=>p.is_vpn_suspected).length;
  const acts=commands.activities||[];
  const crashList=crashes.crashes||[];
  const anomalyList=anomalies.anomalies||[];
  // 列表接口按 120/200 条截断，总数一律用服务端 COUNT(*)：这里曾用列表长度当总数，558 条异常被显示成 200
  const actCount=commands.total!=null?commands.total:acts.length;
  const crashCount=crashes.total!=null?crashes.total:crashList.length;
  const anomalyCount=anomalies.total!=null?anomalies.total:anomalyList.length;
  const issueCount=crashCount+anomalyCount;

  let h='';

  h+='<div class="kpi-grid">';
  h+=kpi(premiumUsers.length,'正版账号');
  h+=kpi(users.length-premiumUsers.length,'离线账号');
  h+=kpi(vpnCount,'疑似 VPN');
  h+=kpi(actCount,'指令记录');
  h+=kpi(issueCount,'崩溃与异常');
  h+='</div>';

  h+='<div class="seg">';
  h+='<button data-sec-seg="account">账号安全 '+premiumUsers.length+'</button>';
  h+='<button data-sec-seg="commands">指令记录 '+actCount+'</button>';
  h+='<button data-sec-seg="issues">崩溃与异常 '+issueCount+'</button>';
  h+='</div>';

  // ── 账号安全：正版账号明细
  h+='<div class="sec-panel" id="sec-account">';
  h+='<div class="card">';
  h+='<div class="card-h"><span>正版账号 ('+premiumUsers.length+')</span><button class="btn-mini" type="button" data-refresh-premium>'+ICONS.refresh+'重新校验</button></div>';
  if(!premiumUsers.length){
    h+='<div class="empty">'+ICONS.shield+'暂无正版玩家</div>';
  }else{
    premiumUsers.forEach(p=>{
      const msId=fmtXuid(p.xuid);
      h+='<div class="row">'+skin(p,44)+'<div class="info">'
        +'<div class="name">'+esc(p.name)+' <span class="badge green">正版</span>'
        +(p.is_vpn_suspected?' <span class="badge red">疑似 VPN</span>':'')+'</div>'
        +'<div class="sub">微软账户 <span class="mono">'+esc(p.gamertag||p.name)+'</span>'
        +(msId?' · XUID <span class="mono">'+esc(msId)+'</span>':'')+'</div>'
        +((p.server_name||p.server_ip)?'<div class="sub">'+esc(p.server_name||'')+(p.server_ip?' · '+esc(p.server_ip):'')+'</div>':'')
        +'</div><div class="right">'+(p.is_online?'<span class="dot on"></span>在线':'离线')+'</div></div>';
    });
  }
  h+='</div></div>';

  // ── 指令记录：时间线
  h+='<div class="sec-panel" id="sec-commands" style="display:none">';
  h+='<div class="card">';
  h+='<div class="card-h">指令记录 ('+acts.length+')</div>';
  h+='<div class="hint">仅记录指令名称，不含参数、密码或坐标；同一玩家同一指令 30 秒内自动去重。</div>';
  if(!acts.length){
    h+='<div class="empty">'+ICONS.inbox+'暂无指令记录<br>玩家发出的指令会在这里按时间倒序出现</div>';
  }else{
    h+='<div class="tl">';
    acts.slice(0,80).forEach(c=>{
      h+='<div class="tl-i"><div class="tl-t"><span class="mono">'+esc(c.command_name||'')+'</span></div>'
        +'<div class="tl-s">'+esc(c.name||'未知玩家')+' · '+esc(c.category||'指令')+' · '+fmtTime(c.created_at)+'</div></div>';
    });
    h+='</div>';
  }
  h+='</div></div>';

  // ── 崩溃与异常
  h+='<div class="sec-panel" id="sec-issues" style="display:none">';
  h+='<div class="card">';
  h+='<div class="card-h">崩溃记录 ('+crashCount+')</div>';
  if(!crashList.length){
    h+='<div class="empty">'+ICONS.shield+'暂无崩溃记录</div>';
  }else{
    const shownCrash=crashList.slice(0,30);
    shownCrash.forEach(c=>{
      h+='<div class="row"><div class="info">'
        +'<div class="name">'+esc((c.message||'未记录异常信息').slice(0,80))+'</div>'
        +'<div class="sub">'+(c.version?esc(c.version)+' · ':'')+'出现 '+c.count+' 次 · '+fmtTime(c.last_seen)+'</div>'
        +'</div></div>';
    });
    if(crashCount>shownCrash.length)h+='<div class="hint">共 '+crashCount+' 条，仅列出最近 '+shownCrash.length+' 条</div>';
  }
  h+='</div>';

  h+='<div class="card">';
  h+='<div class="card-h">异常行为 ('+anomalyCount+')</div>';
  if(!anomalyList.length){
    h+='<div class="empty">'+ICONS.shield+'暂无异常行为</div>';
  }else{
    const shownAnomaly=anomalyList.slice(0,30);
    shownAnomaly.forEach(a=>{
      const sev=a.severity||'';
      const cls=sev==='high'?'red':(sev==='medium'?'blue':'gray');
      h+='<div class="row"><div class="info">'
        +'<div class="name">'+esc(fmtAnomalyType(a.type||'未知'))+' <span class="badge '+cls+'">'+esc(fmtSeverity(sev||'未知'))+'</span></div>'
        +'<div class="sub">'+esc((a.message||'').slice(0,80))+(a.name?' · '+esc(a.name):'')+' · 出现 '+a.count+' 次 · '+fmtTime(a.last_seen)+'</div>'
        +'</div></div>';
    });
    if(anomalyCount>shownAnomaly.length)h+='<div class="hint">共 '+anomalyCount+' 条，仅列出最近 '+shownAnomaly.length+' 条</div>';
  }
  h+='</div></div>';

  $('view').innerHTML=h;
  applySecSeg();

  document.querySelectorAll('[data-sec-seg]').forEach(b=>{
    b.onclick=()=>{secSeg=b.dataset.secSeg;applySecSeg();};
  });
  const refresh=document.querySelector('[data-refresh-premium]');
  if(refresh)refresh.onclick=refreshPremium;
}

function applySecSeg(){
  document.querySelectorAll('[data-sec-seg]').forEach(b=>b.classList.toggle('on',b.dataset.secSeg===secSeg));
  document.querySelectorAll('.sec-panel').forEach(p=>p.style.display='none');
  const cur=$('sec-'+secSeg);
  if(cur)cur.style.display='block';
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
// 面板可直接开关的远程配置：键名 / 中文名 / 说明（说明逐字对应客户端实际作用点）
const REMOTE_SWITCHES=[
  ['stats_report_enabled','数据上报','玩家注册与统计数据是否上报'],
  ['heartbeat_report_enabled','心跳上报','在线状态、延迟、模块与活动数据'],
  ['anomaly_report_enabled','异常上报','高速移动、瞬移等可疑行为'],
  ['crash_report_enabled','崩溃上报','崩溃堆栈与版本信息'],
  ['message_poll_enabled','消息接收','接收后台发送的游戏内消息']
];

/** 与客户端 RemoteFlags 同一口径：键没下发或值不是 false 都算开启 */
function isFlagOn(v){
  return v==null||String(v).trim().toLowerCase()!=='false';
}

async function loadSettings(){
  const cfg=await api('/api/config');
  const config=cfg.config||{};
  const known=REMOTE_SWITCHES.map(s=>s[0]);
  const custom=Object.keys(config).filter(k=>!known.includes(k));

  let h='';

  h+='<div class="card">';
  h+='<div class="card-h">远程开关</div>';
  h+='<div class="hint">游戏内客户端每 5 分钟读取一次，改完最多 5 分钟后生效、无需重启游戏；关掉后客户端停止对应上报。</div>';
  REMOTE_SWITCHES.forEach(([key,label,desc])=>{
    const on=isFlagOn(config[key]);
    h+='<div class="sw"><div class="info">'
      +'<div class="name">'+label+'</div>'
      +'<div class="sub">'+desc+'<br><span class="mono" style="color:var(--t-3)">'+key+'</span></div>'
      +'</div>'
      +'<button class="toggle'+(on?' on':'')+'" type="button" role="switch" aria-checked="'+on+'" data-toggle="'+key+'" aria-label="'+label+'"></button>'
      +'</div>';
  });
  h+='</div>';

  h+='<div class="card">';
  h+='<div class="card-h">其他配置 ('+custom.length+')</div>';
  if(!custom.length){
    h+='<div class="empty">'+ICONS.gear+'暂无自定义配置项<br>上面 6 个开关以外的键客户端不会读取</div>';
  }else{
    custom.forEach(k=>{
      h+='<div class="row"><div class="info">'
        +'<div class="name mono">'+esc(k)+'</div>'
        +'<div class="sub">当前值 <span class="mono">'+esc(config[k])+'</span></div>'
        +'</div>'
        +'<button class="btn-mini danger" type="button" data-del="'+esc(k)+'">删除</button></div>';
    });
  }
  h+='</div>';

  h+='<div class="card">';
  h+='<div class="card-h">添加配置</div>';
  h+='<div style="padding:16px">';
  h+='<input id="cfg-key" type="text" placeholder="键名，例如 crash_report_enabled" style="width:100%;padding:12px;border-radius:12px;border:1px solid var(--line);background:var(--field);color:var(--t-1);margin-bottom:10px">';
  h+='<input id="cfg-val" type="text" placeholder="值，例如 true / false" style="width:100%;padding:12px;border-radius:12px;border:1px solid var(--line);background:var(--field);color:var(--t-1);margin-bottom:14px">';
  h+='<button class="primary" type="button" data-add-cfg>添加 / 更新</button>';
  h+='</div></div>';

  $('view').innerHTML=h;

  document.querySelectorAll('[data-toggle]').forEach(btn=>{
    btn.onclick=()=>toggleFlag(btn);
  });
  document.querySelectorAll('[data-del]').forEach(b=>{
    b.onclick=()=>deleteConfig(b.dataset.del);
  });
  const add=document.querySelector('[data-add-cfg]');
  if(add)add.onclick=addConfig;
}

/** 拨动开关：先动 UI 再落库，失败回滚 */
async function toggleFlag(btn){
  const key=btn.dataset.toggle;
  const next=!btn.classList.contains('on');
  btn.classList.toggle('on',next);
  btn.setAttribute('aria-checked',String(next));

  const res=await api('/api/admin/config',{method:'POST',body:{key,value:next?'true':'false'}});
  if(!res.success){
    btn.classList.toggle('on',!next);
    btn.setAttribute('aria-checked',String(!next));
    alert('保存失败：'+(res.error||'未知错误'));
  }
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
  // 真删除走后端 DELETE（POST 是 upsert：写空值只会把值改成空串，行还在）
  const res=await api('/api/admin/config?key='+encodeURIComponent(key),{method:'DELETE'});
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
