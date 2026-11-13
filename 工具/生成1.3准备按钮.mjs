import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import { createHash } from 'node:crypto';
import { composePreparedRelease } from './1.3准备页布局.mjs';

// 仅生成 1.3 发布准备卡与其正文区块 不读取 Release 不创建版本或安装包
// 正式附件上传后的下载区继续由真实 Release 数据生成 不在此预造下载链接
const args = process.argv.slice(2);
const option = name => { const at = args.indexOf(name); return at < 0 ? null : args[at + 1]; };
const root = path.resolve(option('--root') || fileURLToPath(new URL('../', import.meta.url)));
const draftBody = option('--draft-body');
const check = args.includes('--check');
const website = 'https://www.yiyiaddon.asia/release.html?prepare=1.3';
const raw = 'https://raw.githubusercontent.com/fxjcangku/yiyiaddon/master/';
const xml = value => String(value).replaceAll('&', '&amp;').replaceAll('<', '&lt;').replaceAll('>', '&gt;').replaceAll('"', '&quot;');
const games = [
  { game: '1.21.11', java: 21, api: '0.141.6+1.21.11', light: ['#1D4ED8', '#C8D8F3'], dark: ['#93C5FD', '#34465D'] },
  { game: '26.1.2', java: 25, api: '0.155.2+26.1.2', light: ['#047857', '#B9DDCF'], dark: ['#6EE7B7', '#304E45'] },
  { game: '26.2', java: 25, api: '0.161.0+26.2', light: ['#B45309', '#FED7AA'], dark: ['#FDBA74', '#65452D'] },
  { game: '26.3', java: 25, api: '0.164.0+26.3', light: ['#6D28D9', '#D8CAEF'], dark: ['#C4B5FD', '#49405F'] },
];
const outputs = new Map();
const label = item => `Minecraft ${item.game} · yiyiaddon 1.3 电脑通用包 · Windows x64 与 Apple Silicon macOS ARM64 · Java ${item.java} · Fabric API ${item.api} · 安装包待上传 查看安装要求`;
const icon = (name, x, y, size, color) => {
  const original = fs.readFileSync(path.join(root, `展示资源/icons/octicons/${name}-24.svg`), 'utf8');
  const paths = original.match(/<path\b[^>]*\/>/g);
  if (!paths?.length) throw new Error(`缺少 Octicons 图标 ${name}`);
  return `<g transform="translate(${x} ${y}) scale(${size / 24})" fill="${color}">${paths.join('')}</g>`;
};

for (const item of games) {
  for (const theme of ['light', 'dark']) {
    const [accent, border] = item[theme];
    const dark = theme === 'dark';
    const title = xml(label(item));
    const ink = dark ? '#F3F6FC' : '#172437';
    const muted = dark ? '#A9B8CC' : '#526477';
    const buttonInk = dark ? '#101828' : '#FFFFFF';
    const svg = `<svg xmlns="http://www.w3.org/2000/svg" width="480" height="432" viewBox="0 0 480 432" role="img" aria-label="${title}">
<title>${title}</title>
<desc>1.3 安装包尚未上传 本卡仅打开安装要求 Intel Mac 不在支持范围 Fabric Loader 0.19.5 或以上 已内置调教优化版 Baritone</desc>
<!-- 图标沿用 GitHub 官方 Octicons 路径 来源与 MIT 许可见 展示资源/icons/octicons -->
<defs>
<linearGradient id="bg" x1="0" y1="0" x2="1" y2="1"><stop stop-color="${dark ? '#182331' : '#FFFFFF'}"/><stop offset="1" stop-color="${dark ? '#101720' : '#F8FAFD'}"/></linearGradient>
<radialGradient id="glow" cx="100%" cy="0%" r="110%"><stop stop-color="${accent}" stop-opacity="${dark ? '.09' : '.06'}"/><stop offset=".8" stop-color="${accent}" stop-opacity="0"/></radialGradient>
</defs>
<g font-family="Segoe UI, Microsoft YaHei, Noto Sans CJK SC, PingFang SC, Arial, sans-serif">
<rect x="1" y="1" width="478" height="419" rx="24" fill="url(#bg)" stroke="${border}" stroke-width="1.5"/>
<rect x="1" y="1" width="478" height="419" rx="24" fill="url(#glow)"/>
<text x="28" y="45" font-size="21" fill="${muted}" font-weight="600" letter-spacing="1.7">MINECRAFT</text>
<text x="452" y="45" font-size="20" fill="${accent}" font-weight="650" text-anchor="end">1.3</text>
<text x="27" y="111" font-size="52" fill="${ink}" font-weight="700" letter-spacing="-1">${item.game}</text>
${icon('device-desktop', 28, 136, 26, accent)}
<text x="65" y="158" font-size="25" fill="${accent}" font-weight="600">电脑通用包</text>
<text x="28" y="194" font-size="22" fill="${ink}" font-weight="500">Windows x64 + Mac ARM64</text>
<text x="28" y="224" font-size="20" fill="${muted}">macOS Apple Silicon</text>
<path d="M28 244H452" stroke="${border}"/>
${icon('cpu', 28, 262, 23, muted)}
<text x="65" y="281" font-size="22" fill="${ink}" font-weight="600">Java ${item.java}</text>
<text x="452" y="281" font-size="20" fill="${muted}" text-anchor="end">Loader 0.19.5+</text>
${icon('package', 28, 299, 23, muted)}
<text x="65" y="318" font-size="20" fill="${muted}">Fabric API  ${item.api}</text>
<rect x="25" y="342" width="430" height="60" rx="15" fill="${accent}"/>
${icon('book', 44, 359, 28, buttonInk)}
<text x="88" y="382" font-size="24" fill="${buttonInk}" font-weight="650">安装包待上传</text>
${icon('link-external', 404, 360, 26, buttonInk)}
</g></svg>
`;
    outputs.set(path.join(root, `展示资源/1.3/download-${item.game}-${theme}.svg`), svg);
  }
}

const asset = (game, theme, release) => {
  const relative = `展示资源/1.3/download-${game}-${theme}.svg`;
  const digest = createHash('sha256').update(outputs.get(path.join(root, relative))).digest('hex').slice(0, 8);
  return `${release ? raw : '../'}${relative}?v=${digest}`;
};
const card = (item, release) => {
  const alt = xml(label(item));
  if (release) {
    const light = asset(item.game, 'light', true);
    const dark = asset(item.game, 'dark', true);
    return `  <a href="${website}#prepare-install"><picture><source media="(prefers-color-scheme: dark)" srcset="${dark}"><source media="(prefers-color-scheme: light)" srcset="${light}"><img src="${light}" width="24%" alt="${alt}"></picture></a>`;
  }
  // 仓库 Markdown 继续沿用 GitHub 站点主题链接 Release 使用上方 picture
  return ['light', 'dark'].map(theme => `  <a href="${website}#gh-${theme}-mode-only"><img src="${asset(item.game, theme, false)}" width="24%" alt="${alt}"></a>`).join('');
};
const table = `| 游戏版本 | 1.3 安装包 | Java | Fabric API |\n| --- | --- | --- | --- |\n${games.map(item => `| ${item.game} | 等待正式附件上传 | ${item.java} | \`${item.api}\` |`).join('\n')}`;
const start = '<!-- yiyiaddon:prepared-downloads:start -->';
const end = '<!-- yiyiaddon:prepared-downloads:end -->';
const block = release => `${start}
<p align="center">
${games.map(item => card(item, release)).join('\n')}
</p>

安装包待上传 点选上方版本卡可查看安装要求

<details>
<summary><strong>查看完整版本依赖</strong></summary>

${table}

四个版本分别使用对应的 desktop JAR Fabric Loader 统一使用 0.19.5 或以上

</details>
${end}`;

function updateBody(file, release) {
  const body = fs.readFileSync(file, 'utf8');
  const pattern = /<!-- yiyiaddon:prepared-downloads:start -->[\s\S]*?<!-- yiyiaddon:prepared-downloads:end -->/;
  const starts = body.split(start).length - 1;
  const ends = body.split(end).length - 1;
  let next;
  if (starts || ends) {
    if (starts !== 1 || ends !== 1 || !pattern.test(body)) throw new Error(`准备卡区块边界异常 ${file}`);
    next = body.replace(pattern, block(release));
  } else {
    if (body.split(table).length !== 2) throw new Error(`未找到唯一 1.3 原始安装依赖表 ${file}`);
    next = body.replace(table, block(release));
  }
  outputs.set(file, composePreparedRelease(next, { root, release }));
}

updateBody(path.join(root, '发布准备/1.3.md'), false);
if (draftBody) updateBody(path.resolve(draftBody), true);
let changed = 0;
for (const [file, content] of outputs) {
  const current = fs.existsSync(file) ? fs.readFileSync(file, 'utf8') : null;
  if (current === content) continue;
  changed++;
  if (!check) {
    fs.mkdirSync(path.dirname(file), { recursive: true });
    fs.writeFileSync(file, content);
  }
}
if (check && changed) throw new Error(`${changed} 个 1.3 准备输出需要刷新`);
console.log(check ? '1.3 准备卡与正文一致' : `已刷新 ${changed} 个 1.3 准备输出 未创建下载附件`);
