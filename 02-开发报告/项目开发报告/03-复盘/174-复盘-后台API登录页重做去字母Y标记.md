# 复盘：后台 API 登录页重做（去掉字母 Y 标记）

日期：2026-09-21

## 一、用户反馈

用户以手机浏览器实机截图指出：后台控制台登录页的方形「Y」字母标记难看，要求重做整个登录页。

## 二、取证

- 登录页全部在 `03-后端API/src/admin-modern.js` 的 `ADMIN_HTML` 模板里（`worker.js` 第 373 行内联返回，路径 `/` 与 `/admin`）；
- 原标记为纯文本字母：`<div class="logo-mark">Y</div>`（登录卡）与 `<div class="brand-mark">Y</div>`（桌面侧栏），
  样式为 `linear-gradient(135deg,var(--brand),var(--brand-l))` 方块；
- 原登录卡只有「标记 + 标题 + 副标题 + 两个裸输入框 + 按钮」，背景是纯色底加两团 `.glow`；
- 原登录逻辑只绑 `#lg-btn` 的 `onclick`，输入框回车不提交；加载态把「登录中...」写进红色报错框；
- `sw.js` 的 `CACHE_NAME='yiyiaddon-v2'` 对 `/` 走 cache-first，不换版本号用户会一直看到旧页面。

## 三、设计与修改

只改登录页与品牌标记，不动接口、路由、主题变量与其它页面。

1. **去掉字母 Y**，换成线性图标「中心控制台 + 四向节点」（`ICONS.mark`）：
   - 登录卡 60px 玻璃方块、侧栏 42px，同一份图标字符串，用 `[data-mark]` 注入，避免两处重复；
   - 方块渐变改为 `150deg #7FD4FF→#2E90FF→#0A57E0`，加内描边与顶高光。
2. **登录页背景分层**：`#login::before` 三团静态微光压近黑底，`#login::after` 64px 极细网格加径向遮罩淡出；
   两层均 `position:fixed`，卡片滚动时背景不动、不产生多余滚动条；亮色主题另给一套配色。
3. **卡片**：宽 412、圆角 24、顶部 1px 高光描边；副标题「远程管理控制台」两侧细线收束；
   底部加 `仅限授权管理员访问` 分隔页脚。`#login` 改 `overflow:auto` + 卡片 `margin:auto`，矮屏可滚动且仍居中。
4. **输入行**：前置线性图标（人像 / 锁），聚焦时图标转强调色；
   补 `-webkit-autofill` 暗色兜底（原实现自动填充会变白底黑字）；密码行加显示/隐藏按钮（`ICONS.eye/eyeOff`）。
5. **提交链路**：卡片本身改为 `<form id="lg-form">`，按钮 `type=submit`，
   回车与点击共用同一入口；加载态改为按钮内旋转指示器 + 文案「登录中」，不再占用红色报错框；
   报错改为 `.err.show`（带抖动动画，每次报错重放），空账号密码时聚焦到缺失的输入框。
6. **右上角主题切换**：登录页不经过 `init()`，故自带一枚 `#lg-theme`，写入同一个 `localStorage.theme`。
7. `sw.js` 缓存名 `yiyiaddon-v2` → `yiyiaddon-v3`，使新页面能真正到达用户浏览器。

## 三·补、部署（2026-09-21）

用用户提供的 Cloudflare API Token 执行 `wrangler deploy`，两次：

- 第一次（Version ID `6cd1d1b7-7b7c-40a4-91a2-0a51ed2d9a52`）：HTML 已更新，但**线上 `/sw.js` 仍是 v2**；
- 取证发现 `src/worker.js` 第 16 行把整份 Service Worker 作为模板字符串内联了一份（`const SW_JS = \`...\``），
  线上 `/sw.js` 走的是这份内联副本，`src/sw.js` 文件并不参与部署 —— **同源逻辑留了两份（第 169 条）**；
- 第二次只把 `worker.js` 内联副本的 `CACHE_NAME` 改为 `yiyiaddon-v3` 后重新部署（Version ID `7101651c-331b-4265-8814-331053dcd4c3`）。

两处 `CACHE_NAME` 现已同为 `yiyiaddon-v3`；**「worker.js 内联副本与 src/sw.js 重复」这一条未动**，等用户决定是否合并为单一来源。

## 四、验证结果

- 模块语法：`import('./admin-modern.mjs')` 求值成功，模板内联脚本 `new Function` 解析通过；
- 本地真实渲染（Chrome 打开导出的模板）：
  - 390×844 暗色 / 亮色、1280×820 桌面三张截图均正常，卡片几何为 412×480 且水平垂直居中（中心 411 vs 视口半高 410.5）；
  - 400px 窄屏下 `scrollHeight == innerHeight`，无溢出；
  - `.sp` 在 `#lg-btn.loading` 下 `display:block / animation:spin` 生效；按钮 `disabled` 时 `cursor:not-allowed`；
  - 眼睛按钮点击后输入框 `type` 在 `text`/`password` 间正确切换；
  - `[data-mark]` 两处（登录卡、桌面侧栏）均注入同一图标，侧栏原「Y」已消失；
- 线上验收（`https://yiyiaddon.asia/`，Chrome 无痕上下文 390×844）：
  - 首页返回新版登录页（`lg-form` / `data-mark` 命中，旧 `logo-mark>Y` 已无）；
  - `/sw.js` 返回 `yiyiaddon-v3`；
  - 截图与本地一致，控制台仅 `favicon.ico` 404（历史遗留，与本次改动无关）。

## 五、下一阶段开发规划

### 一、下一阶段目标

用户在手机上确认线上登录页观感；并决定是否把内联 Service Worker 合并为单一来源（`worker.js` 从 `src/sw.js` 导入）。

### 二、执行原因

本地渲染只能证明样式与交互闭合，真机字体渲染、浏览器地址栏占位与安全区仍需实机截图确认。

### 三、前置条件检查

- 已完成：登录页重做、缓存版本号提升（两处）、部署上线、线上验收；
- 依赖：用户手机实机截图。

### 四、任务拆分

1. 目标：消灭 Service Worker 双份来源；涉及文件：`src/worker.js`（删内联 `SW_JS`、改为 `import { SW_JS } from './sw.js'`）、`src/sw.js`（加 `export`）；影响范围：`/sw.js` 响应；预计修改内容：一行导入 + 一行导出。
2. 目标：若真机仍有比例问题，仅调整 `.login-card` / `.logo-mark` 尺寸常量；涉及文件：`src/admin-modern.js`。

### 五、验收标准

- 登录页不再出现字母「Y」；
- 手机端卡片居中、输入框聚焦与报错反馈正常；
- 当前状态：代码完成、本地渲染通过、已部署上线并线上验收通过。

### 六、禁止事项

不改登录接口与鉴权逻辑、不动其它页面结构、不改主题色变量、不在缺少实机截图时继续扩大调整范围。

## 项目迁移路线图

已完成：√ 后台 API 登录页重做（去字母 Y）+ 上线

当前：→ 等待用户手机实机确认

下一步：→ 按真机截图做最小化尺寸微调；可选合并 Service Worker 双份来源

未来：→ 按明确反馈继续最小化调整
