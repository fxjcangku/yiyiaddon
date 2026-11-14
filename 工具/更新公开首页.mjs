import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import { createHash } from 'node:crypto';
import { buildReleaseHistory } from './生成历史版本.mjs';
import { heading, panel, navRow, ensureWebsiteEntry } from './发布页面组件.mjs';

/** 根据真实 Release 刷新正式版首页与分类归档；不读取模组源码，不发布或修改安装包。 */
const args = process.argv.slice(2);
const option = name => { const at = args.indexOf(name); return at < 0 ? null : args[at + 1]; };
const root = path.resolve(option('--root') || fileURLToPath(new URL('../', import.meta.url)));
const readJson = name => { const file = option(name); if (!file) throw new Error(`缺少参数 ${name}`); return JSON.parse(fs.readFileSync(file, 'utf8')); };
const ui = { root };
const latest = readJson('--latest');
const catalog = readJson('--catalog').flat();
const repository = 'https://github.com/fxjcangku/yiyiaddon';
if (!latest || latest.draft || latest.prerelease || !/^v\d/.test(latest.tag_name)) throw new Error('Latest 必须是真实正式版');
const version = latest.tag_name.slice(1);
const stable = catalog.filter(item => !item.draft && !item.prerelease && item.tag_name !== latest.tag_name)
  .sort((a, b) => Date.parse(b.published_at) - Date.parse(a.published_at));
const candidates = [latest, ...stable];
const games = ['1.21.11', '26.1.2', '26.2', '26.3'];
const baritoneNote = '已内置调教优化版 Baritone';
const baritoneWarning = '请卸载自行安装的 Baritone';
const baritoneSummary = `${baritoneNote} · ${baritoneWarning}`;
const xml = value => String(value).replaceAll('&', '&amp;').replaceAll('<', '&lt;').replaceAll('>', '&gt;').replaceAll('"', '&quot;');

/** 按表头识别兼容表，兼容旧四列与新三列排版；不从固定列号或其他游戏版本猜依赖。 */
function fabricDependency(body, game) {
  const clean = value => value.trim().replaceAll('**', '').replaceAll('__', '').replaceAll('`', '');
  const cells = line => {
    if (!line.includes('|')) return null;
    return line.trim().replace(/^\|/, '').replace(/\|$/, '').split('|').map(clean);
  };
  const lines = String(body || '').split('\n');
  for (let at = 0; at < lines.length - 1; at++) {
    const headers = cells(lines[at]);
    if (!headers) continue;
    const gameColumn = headers.findIndex(value => /^(?:Minecraft(?:\s*版本)?|游戏版本)$/i.test(value));
    const apiColumn = headers.findIndex(value => /^(?:配套\s*)?Fabric\s+API$/i.test(value));
    const separator = cells(lines[at + 1]);
    if (gameColumn < 0 || apiColumn < 0 || !separator
        || separator.length !== headers.length || !separator.every(value => /^:?-{3,}:?$/.test(value))) continue;
    for (let rowAt = at + 2; rowAt < lines.length; rowAt++) {
      const row = cells(lines[rowAt]);
      if (!row) break;
      if (row[gameColumn] !== game) continue;
      const dependency = row[apiColumn]?.match(/\b(\d+\.\d+\.\d+\+(\d+(?:\.\d+)+))\b/);
      return dependency?.[2] === game ? dependency[1] : null;
    }
  }
  return null;
}

/** 每条游戏版本独立选择最新正式附件；没有该线新包时不能把另一条线或预告冒充可下载包。 */
const downloads = games.map(game => {
  for (const release of candidates) {
    const number = release.tag_name.replace(/^v/, '');
    // 同一正式版本优先取真实 desktop 附件；历史无后缀包只表示 Windows x64。
    const names = [`yiyiaddon-${number}-${game}-desktop.jar`, `yiyiaddon-${number}-${game}.jar`];
    const asset = names.map(name => (release.assets || []).find(item => item.name === name
      && item.size > 0 && item.state === 'uploaded')).find(Boolean);
    if (!asset) continue;
    if (asset.browser_download_url !== [repository, "releases/download", release.tag_name, asset.name].join("/")) throw new Error('安装包链接不属于本仓库');
    const dependency = fabricDependency(release.body, game);
    const target = asset.name.endsWith('-desktop.jar') ? 'desktop' : 'windows-x64';
    return { game, version: number, url: asset.browser_download_url, dependency, tag: release.tag_name, target };
  }
  if (game === '1.21.11') return null;
  throw new Error(`未找到 Minecraft ${game} 的正式 JAR，保留现有首页`);
}).filter(Boolean);
const fixedPortAvailable = downloads.some(item => item.game === '1.21.11');
const latestGames = downloads.filter(item => item.tag === latest.tag_name).map(item => item.game);
if (!latestGames.length) throw new Error('最新正式版没有可核实安装包，保留现有首页');
const desktopGames = downloads.filter(item => item.target === 'desktop').map(item => item.game);
const platformLabel = item => item.target === 'desktop' ? 'Windows x64 与 Mac 系统 ARM64' : '仅 Windows x64';
const platformStatus = desktopGames.length === 0
  ? ''
  : desktopGames.length === downloads.length
    ? '下方正式附件均为 **desktop 通用电脑包** · Windows x64 与 Mac 系统 ARM64 共用同一游戏版本的 JAR'
    : `**Minecraft ${desktopGames.join(' / ')}** 已提供 Windows x64 与 Mac 共用的 desktop 包 其余下载仍仅适用于 Windows x64`;
// 只兑现本次 1.3 准备状态：必须有该正式版真实上传的 desktop 附件。
const preparedRelease = candidates.find(item => item.tag_name === 'v1.3');
const mapartPublished = !!preparedRelease && games.some(game => (preparedRelease.assets || []).some(asset => {
  const name = `yiyiaddon-1.3-${game}-desktop.jar`;
  return asset.name === name && asset.state === 'uploaded' && asset.size > 0
    && asset.browser_download_url === `${repository}/releases/download/v1.3/${name}`;
}));
const macAvailability = desktopGames.length === downloads.length ? '通用包已提供'
  : desktopGames.length ? '部分版本已提供'
    : mapartPublished ? '通用包见 1.3 发布页' : '1.3 通用包待上传';
const platformOverview = `Windows x64 正式版可下载；Mac 系统 ARM64 · ${macAvailability}`;

/** 保留封面、按钮的排版，只更新稳定 ID 对应的发布文字；旧槽位兼容原有资源。 */
function textSlot(svg, id, selector, value) {
  if (!svg.includes(`id="${id}"`)) {
    if (!selector || !svg.includes(selector)) throw new Error(`展示资源缺少文字槽 ${id}`);
    svg = svg.replace(selector, `${selector} id="${id}"`);
  }
  const pattern = new RegExp(`(<text\\b[^>]*\\bid="${id}"[^>]*>)[^<]*(</text>)`);
  if (!pattern.test(svg)) throw new Error(`无法更新文字槽 ${id}`);
  return svg.replace(pattern, (_, open, close) => `${open}${xml(value)}${close}`);
}
const folder = path.join(root, '展示资源/homepage-20261023');
const themeFolder = path.join(root, '展示资源/theme');
const themes = ['light', 'dark'];
const outputs = new Map();
// 品牌版式继续复用已有模板；浅色只映射主题色，不改布局、文案槽和顶部 GIF。
const lightBrandColors = {
  '#182331': '#f7fafc', '#101720': '#ffffff', '#344253': '#d0d7de',
  '#334355': '#d8e1eb', '#91ebd4': '#087f65', '#f3f7fc': '#172b4d',
  '#e2eaf5': '#25364a', '#a7b8ca': '#536478', '#dfe7f2': '#34445a',
  '#20372f': '#e8f6ef', '#9eb0c4': '#526478',
  '#2b2a29': '#fff5e6', '#67523b': '#e3c49b', '#f4c585': '#86531b',
};
const lightBrand = svg => svg.replace(/#[\da-f]{6}\b/gi, color => lightBrandColors[color.toLowerCase()] || color);
const themedBrandName = (compact, theme) => `brand${compact ? '-compact' : ''}-${theme}.svg`;
const themedDownloadName = (game, theme) => `download-${game}-${theme}.svg`;
// 首次正式发布四个版本时使用新路径，避开旧路径缓存。
const brandName = compact => 'brand' + (compact ? '-compact' : '') + (fixedPortAvailable ? '-release.svg' : '-compat.svg');
const downloadName = game => 'download-' + game + (fixedPortAvailable ? '-release.svg' : '.svg');
// 平台标志沿用官网与后台的原生路径；所有读者可见标签只使用 Windows / Mac。
const platformPaths = {
  windows: 'M2 2h9v9H2zm11 0h9v9h-9zM2 13h9v9H2zm11 0h9v9h-9z',
  mac: 'M12.152 6.896c-.948 0-2.415-1.078-3.96-1.04-2.04.027-3.91 1.183-4.961 3.014-2.117 3.675-.546 9.103 1.519 12.09 1.013 1.454 2.208 3.09 3.792 3.039 1.52-.065 2.09-.987 3.935-.987 1.831 0 2.35.987 3.96.948 1.637-.026 2.676-1.48 3.676-2.948 1.156-1.688 1.636-3.325 1.662-3.415-.039-.013-3.182-1.221-3.22-4.857-.026-3.04 2.48-4.494 2.597-4.559-1.429-2.09-3.623-2.324-4.39-2.376-2-.156-3.675 1.09-4.61 1.09zM15.53 3.83c.843-1.012 1.4-2.427 1.245-3.83-1.207.052-2.662.805-3.532 1.818-.78.896-1.454 2.338-1.273 3.714 1.338.104 2.715-.688 3.559-1.701',
};
const platformIcon = (name, x, y, size, color) => `<g transform="translate(${x} ${y}) scale(${size / 24})" fill="${color}"><path d="${platformPaths[name]}"/></g>`;
const octicon = (name, x, y, size, color) => {
  const source = fs.readFileSync(path.join(root, `展示资源/icons/octicons/${name}-24.svg`), 'utf8');
  const paths = source.match(/<svg\b[^>]*>([\s\S]*)<\/svg>/)?.[1];
  if (!paths) throw new Error(`无法读取图标 ${name}`);
  return `<g transform="translate(${x} ${y}) scale(${size / 24})" fill="${color}">${paths}</g>`;
};
function addBrandPlatforms(svg, compact) {
  if (compact) {
    svg = svg.replace('height="442" viewBox="0 0 640 442"', 'height="520" viewBox="0 0 640 520"')
      .replaceAll('height="428"', 'height="506"').replace('M36 278h568', 'M36 354h568')
      .replace('x="38" y="320"', 'x="38" y="397"').replace('x="162" y="324"', 'x="162" y="401"')
      .replace('x="38" y="368"', 'x="38" y="446"').replaceAll('y="401" font-size="17"', 'y="478" font-size="17"');
    const badges = `<g id="platform-badges">
<rect x="38" y="258" width="236" height="44" rx="13" fill="#20372f" stroke="#344253"/>
${platformIcon('windows', 50, 269, 22, '#91ebd4')}<text x="84" y="288" font-size="22" font-weight="600" fill="#e2eaf5">Windows x64</text>
<rect x="288" y="258" width="314" height="44" rx="13" fill="#2b2a29" stroke="#67523b"/>
${platformIcon('mac', 300, 268, 24, '#f4c585')}<text x="336" y="288" font-size="22" font-weight="600" fill="#e2eaf5">Mac 系统 ARM64</text>
<text x="42" y="329" font-size="19" fill="#91ebd4">正式安装包可下载</text><text x="292" y="329" font-size="19" fill="#f4c585">${xml(macAvailability)}</text>
</g>`;
    return svg.replace('<path d="M36 354h568"', `${badges}<path d="M36 354h568"`);
  }
  const badges = `<g id="platform-badges">
<rect x="38" y="279" width="212" height="34" rx="11" fill="#20372f" stroke="#344253"/>
${platformIcon('windows', 50, 286, 20, '#91ebd4')}<text x="82" y="302" font-size="18" font-weight="600" fill="#e2eaf5">Windows x64</text>
<rect x="264" y="279" width="466" height="34" rx="11" fill="#2b2a29" stroke="#67523b"/>
${platformIcon('mac', 276, 285, 22, '#f4c585')}<text x="310" y="302" font-size="18" font-weight="600" fill="#e2eaf5">Mac 系统 ARM64</text>
<text x="715" y="301" text-anchor="end" font-size="16" fill="#f4c585">${xml(macAvailability)}</text>
</g>`;
  return svg.replace('<path d="M40 328h690"', `${badges}<path d="M40 328h690"`)
    .replace('x="840" y="292" width="146"', 'x="840" y="292" width="210"')
    .replace('>正式安装包可用</text>', `>${desktopGames.length ? '平台状态见下载区' : 'Windows 版可下载'}</text>`);
}
/** 沿用每条版本线的主题配色；明确区分当前可下载附件与 Mac 发布准备。 */
function downloadPlatformCard(source, item, theme) {
  const attribute = (selector, name) => {
    const element = source.match(selector)?.[0];
    const value = element?.match(new RegExp(`\\b${name}="([^"]+)"`))?.[1];
    if (!value) throw new Error(`下载卡缺少 ${item.game} ${name} 配色槽`);
    return value;
  };
  const accent = attribute(/<text\b[^>]*\bid="download-note"[^>]*>/, 'fill');
  const ink = attribute(/<text\b[^>]*\bid="game-version"[^>]*>/, 'fill');
  const muted = attribute(/<text\b[^>]*\bid="fabric-api"[^>]*>/, 'fill');
  const warning = attribute(/<text\b[^>]*\bid="baritone-warning"[^>]*>/, 'fill');
  const border = attribute(/<rect\b[^>]*\bfill="url\(#bg\)"[^>]*>/, 'stroke');
  const definitions = source.match(/<defs>[\s\S]*?<\/defs>/)?.[0];
  if (!definitions) throw new Error(`下载卡缺少渐变 ${item.game}`);
  const onAccent = theme === 'dark' ? '#101828' : '#FFFFFF';
  const macStatus = item.target === 'desktop' ? '同一 JAR 可用' : mapartPublished ? '请查看版本说明' : '1.3 通用包待上传';
  const label = `下载 Minecraft ${item.game} JAR · 正式版 ${item.version} · ${platformLabel(item)} · Mac 系统 ARM64：${macStatus} · ${baritoneSummary}`;
  return `<svg xmlns="http://www.w3.org/2000/svg" width="480" height="425" viewBox="0 0 480 425" role="img" aria-label="${xml(label)}">
<title>${xml(label)}</title>
${definitions}
<g font-family="Segoe UI, Microsoft YaHei, Noto Sans CJK SC, PingFang SC, Arial, sans-serif">
<rect x="1" y="1" width="478" height="411" rx="24" fill="url(#bg)" stroke="${border}" stroke-width="1.5"/>
<rect x="1" y="1" width="478" height="411" rx="24" fill="url(#glow)"/>
<text x="28" y="45" font-size="21" fill="${muted}" font-weight="600" letter-spacing="1.7">MINECRAFT</text>
<text x="452" y="45" font-size="20" fill="${accent}" font-weight="650" id="release-version" text-anchor="end">${xml(item.version)}</text>
<text x="27" y="111" font-size="52" fill="${ink}" font-weight="700" id="game-version" letter-spacing="-1">${xml(item.game)}</text>
${platformIcon('windows', 28, 134, 22, accent)}<text x="62" y="154" font-size="22" font-weight="600" fill="${ink}">Windows x64</text><text x="452" y="153" text-anchor="end" font-size="18" fill="${accent}">现可下载</text>
${platformIcon('mac', 28, 170, 23, accent)}<text x="62" y="191" font-size="22" font-weight="600" fill="${ink}">Mac 系统 ARM64</text><text x="452" y="190" text-anchor="end" font-size="18" fill="${item.target === 'desktop' ? accent : warning}">${xml(macStatus)}</text>
<path d="M28 209H452" stroke="${border}"/>
<text x="28" y="241" font-size="25" fill="${accent}" font-weight="600" id="download-note">${xml(baritoneNote)}</text>
<text x="28" y="275" font-size="23" fill="${warning}" font-weight="650" id="baritone-warning">${xml(baritoneWarning)}</text>
<text x="28" y="309" font-size="22" fill="${muted}" font-weight="400" id="fabric-api">${xml(item.dependency ? `Fabric API  ${item.dependency}` : '配套 Fabric API 见下载页')}</text>
<rect x="25" y="331" width="430" height="62" rx="15" fill="${accent}"/>
${octicon('download', 44, 349, 28, onAccent)}<text x="88" y="372" font-size="24" fill="${onAccent}" font-weight="650">${item.target === 'desktop' ? '下载电脑通用包' : '下载 Windows JAR'}</text>
<g transform="translate(404 349) scale(1.1666666667)" fill="none" stroke="${onAccent}" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M5 12h14m-6-6 6 6-6 6"/></g>
</g></svg>\n`;
}
function environmentPlatforms(theme, compact) {
  const color = theme === 'dark'
    ? { bg: '#161b22', border: '#30363d', ink: '#e6edf3', muted: '#b1bac4', accent: '#79c0ff', mac: '#e9c38e', tint: '#242322' }
    : { bg: '#ffffff', border: '#d0d7de', ink: '#1f2328', muted: '#59636e', accent: '#0969da', mac: '#86531b', tint: '#fff8ee' };
  const width = compact ? 640 : 1200, height = compact ? 488 : 268;
  const platformWidth = compact ? 638 : 474, secondaryWidth = compact ? 308 : 344;
  const secondX = compact ? 0 : 494, thirdX = compact ? 330 : 854, secondaryY = compact ? 278 : 0;
  const panelFrame = (x, y, w, h) => `<rect x="${x + 1}" y="${y + 1}" width="${w}" height="${h}" rx="18" fill="${color.bg}" stroke="${color.border}" stroke-width="1.5"/>`;
  const platformNote = desktopGames.length && desktopGames.length < downloads.length ? `Mac 可下载：${desktopGames.join(' / ')}` : 'Mac 使用 ARM64 Java · Intel Mac 暂不支持';
  return `<svg xmlns="http://www.w3.org/2000/svg" width="${width}" height="${height}" viewBox="0 0 ${width} ${height}" role="img" aria-labelledby="title desc">
<title id="title">Windows 与 Mac 系统 · 运行环境</title><desc id="desc">${xml(platformOverview)}。Fabric Loader 0.19.5+；Minecraft 1.21.11 使用 Java 21，26 系列使用 Java 25；Mac 需 ARM64 Java，暂不支持 Intel Mac。</desc>
<g font-family="Segoe UI, Microsoft YaHei, Noto Sans CJK SC, PingFang SC, Arial, sans-serif">
${panelFrame(0, 0, platformWidth, 266)}
${octicon('device-desktop', 24, 27, 28, color.accent)}<text x="66" y="50" font-size="28" font-weight="650" fill="${color.ink}">电脑平台</text><path d="M24 72H${platformWidth - 22}" stroke="${color.border}"/>
${platformIcon('windows', 24, 96, 28, color.accent)}<text x="68" y="118" font-size="25" font-weight="600" fill="${color.ink}">Windows x64</text><text x="${platformWidth - 23}" y="116" text-anchor="end" font-size="19" fill="${color.accent}">正式版可下载</text>
<rect x="16" y="141" width="${platformWidth - 30}" height="67" rx="12" fill="${color.tint}"/>
${platformIcon('mac', 25, 158, 30, color.mac)}<text x="68" y="179" font-size="25" font-weight="600" fill="${color.ink}">Mac 系统 ARM64</text><text x="${platformWidth - 23}" y="198" text-anchor="end" font-size="19" fill="${color.mac}">${xml(macAvailability)}</text>
<text x="24" y="241" font-size="18" fill="${color.muted}">${xml(platformNote)}</text>
${panelFrame(secondX, secondaryY, secondaryWidth, compact ? 208 : 266)}
${octicon('package', secondX + 24, secondaryY + 27, 28, color.accent)}<text x="${secondX + 66}" y="${secondaryY + 50}" font-size="28" font-weight="650" fill="${color.ink}">模组加载器</text><path d="M${secondX + 24} ${secondaryY + 72}H${secondX + secondaryWidth - 22}" stroke="${color.border}"/>
<text x="${secondX + 24}" y="${secondaryY + 115}" font-size="24" fill="${color.muted}">Fabric Loader</text><text x="${secondX + 24}" y="${secondaryY + 154}" font-size="29" font-weight="600" fill="${color.ink}">0.19.5+</text><text x="${secondX + 24}" y="${secondaryY + 190}" font-size="18" fill="${color.muted}">两种系统使用同一加载器</text>
${panelFrame(thirdX, secondaryY, secondaryWidth, compact ? 208 : 266)}
${octicon('cpu', thirdX + 24, secondaryY + 27, 28, color.accent)}<text x="${thirdX + 66}" y="${secondaryY + 50}" font-size="28" font-weight="650" fill="${color.ink}">Java 版本</text><path d="M${thirdX + 24} ${secondaryY + 72}H${thirdX + secondaryWidth - 22}" stroke="${color.border}"/>
<text x="${thirdX + 24}" y="${secondaryY + 115}" font-size="23" fill="${color.muted}">1.21.11 · Java 21</text><text x="${thirdX + 24}" y="${secondaryY + 154}" font-size="23" fill="${color.muted}">26 系列 · Java 25</text><text x="${thirdX + 24}" y="${secondaryY + 190}" font-size="18" fill="${color.mac}">Mac 请选 ARM64</text>
</g></svg>\n`;
}
for (const compact of [false, true]) {
  const name = brandName(compact);
  const template = compact ? 'brand-compact-compat.svg' : 'brand-compat.svg';
  let svg = fs.readFileSync(path.join(folder, template), 'utf8');
  svg = textSlot(svg, 'release-version', compact ? '<text x="30" y="249"' : '<text x="850" y="120"', version);
  const summary = (latest.name || '').split(/[｜|]/).slice(1).join(' · ').trim()
    .replace(/^(?:正式版|测试版)(?:\s*[·•—-]\s*|\s*$)/, '') || '查看最新正式版更新';
  // 版本支持短句完整显示，避免按字符截断把 1.21.11 写成 1；其余摘要保留原有卡宽限制。
  const shortTitle = summary.match(/^支持\s*(?:Minecraft\s*)?\d+(?:\.\d+)+/i)?.[0]
    || Array.from(summary).slice(0, 14).join('');
  if (!compact) svg = textSlot(svg, 'release-title', '<text x="854" y="212"', shortTitle);
  svg = textSlot(svg, 'supported-games', compact ? '<text x="30" y="290"' : '<text x="854" y="173"',
    `${compact || latestGames.length === 4 ? '' : 'Minecraft '}${latestGames.join(' / ')}`);
  if (fixedPortAvailable) svg = svg.replace(/<text[^>]*>1\.21\.11 适配预告<\/text>/, '');
  svg = addBrandPlatforms(svg, compact);
  const label = 'yiyiaddon ' + version + ' · 独立 Fabric 客户端模组 · Minecraft ' + latestGames.join(' / ')
    + ' · ' + platformOverview + (fixedPortAvailable ? '' : ' · 1.21.11 适配预告');
  svg = svg.replace(/aria-label="[^"]*"/, `aria-label="${xml(label)}"`).replace(/<title>[^<]*<\/title>/, `<title>${xml(label)}</title>`);
  outputs.set(path.join(folder, name), svg);
  for (const theme of themes) {
    outputs.set(path.join(themeFolder, themedBrandName(compact, theme)), theme === 'light' ? lightBrand(svg) : svg);
  }
}
for (const item of downloads) {
  for (const theme of themes) {
    // 每条游戏版本各读自己的明暗模板，避免 1.21.11 再次复制成 26.1.2 的颜色。
    const file = path.join(themeFolder, themedDownloadName(item.game, theme));
    const svg = downloadPlatformCard(fs.readFileSync(file, 'utf8'), item, theme);
    outputs.set(file, svg);
    // 保留旧图片 URL 的输出兼容；现行首页仅引用新的明暗组件。
    if (theme === 'dark') outputs.set(path.join(folder, downloadName(item.game)), svg);
  }
}
for (const theme of themes) {
  for (const compact of [false, true]) {
    outputs.set(path.join(themeFolder, `panel-environment-platforms${compact ? '-compact' : ''}-${theme}.svg`), environmentPlatforms(theme, compact));
  }
}
const readmePath = path.join(root, 'README.md');
let readme = fs.readFileSync(readmePath, 'utf8').replaceAll('\r\n', '\n');
const mapartStatus = mapartPublished
  ? `**全自动地图画已随 1.3 正式发布** · 安装包按游戏版本与实际平台提供\n\n[查看 1.3 更新记录](${repository}/releases/tag/v1.3) · [地图画使用说明](功能指南.md#全自动地图画) · [官网功能展示](https://www.yiyiaddon.asia/#mapart-preview)`
  : `**功能已完成 正在准备 1.3 发布** · 新安装包尚未上传 当前正式下载仍为 ${xml(version)}\n\n[查看 1.3 发布准备](发布准备/1.3.md) · [地图画使用说明](功能指南.md#全自动地图画) · [官网功能展示](https://www.yiyiaddon.asia/#mapart-preview) · [官网发布准备](https://www.yiyiaddon.asia/release.html?prepare=1.3)`;
const desktopStatus = `新增 **macOS ARM64 原生适配** 对应游戏版本的 **desktop 通用电脑包** 面向 Windows x64 与 Mac\n\n`
  + (mapartPublished
    ? 'Mac 请使用原生 ARM64 Java 下方按各游戏版本标明实际可下载平台 历史无平台后缀附件仍仅适用于 Windows x64'
    : `Mac 请使用原生 ARM64 Java 现有 ${xml(version)} 无平台后缀附件仍仅适用于 Windows x64 新 desktop 附件上传后提供真实下载入口`);
for (const [name, content] of [['mapart-status', mapartStatus], ['desktop-status', desktopStatus]]) {
  const pattern = new RegExp(`<!-- homepage:${name}:start -->[\\s\\S]*?<!-- homepage:${name}:end -->`);
  const starts = (readme.match(new RegExp(`<!-- homepage:${name}:start -->`, 'g')) || []).length;
  const ends = (readme.match(new RegExp(`<!-- homepage:${name}:end -->`, 'g')) || []).length;
  if (starts === 0 && ends === 0) continue; // 首页未展示准备模块时不强行补回。
  if (starts !== 1 || ends !== 1 || !pattern.test(readme)) throw new Error(`发布准备区域 ${name} 边界缺失或重复，停止覆盖`);
  readme = readme.replace(pattern, `<!-- homepage:${name}:start -->\n${content}\n<!-- homepage:${name}:end -->`);
}
// 固定适配预告独立于下一版摘要：正式版号递增不能被视为该游戏版本已经有公开安装包。
const fixedPortPattern = /<!-- homepage:fixed-port:start -->[\s\S]*?<!-- homepage:fixed-port:end -->/;
const fixedPort = !fixedPortAvailable && readme.match(fixedPortPattern)?.[0];
for (const marker of ['start', 'end']) {
  const count = (readme.match(new RegExp(`<!-- homepage:fixed-port:${marker} -->`, 'g')) || []).length;
  if (count > 1 || (fixedPort && count !== 1)) throw new Error('固定适配预告边界重复或不完整');
}
// 图片 URL 包含实际内容摘要，主题文件或发布文案变化时自动刷新缓存。
const imageUrl = (file, number) => {
  const content = outputs.get(file);
  if (content === undefined) throw new Error(`展示资源尚未生成：${file}`);
  const relative = path.relative(root, file).split(path.sep).join('/');
  return `${relative}?v=${encodeURIComponent(number)}-${createHash('sha256').update(content).digest('hex').slice(0, 8)}`;
};
// GitHub README 主题 CSS 匹配链接 href 的主题片段，不能仅在 img src 上标记。
const themedUrl = (name, theme, number) => imageUrl(path.join(themeFolder, `${name}-${theme}.svg`), number);
function responsiveBrand(theme, number, label) {
  return `<a href="${repository}/releases/latest#gh-${theme}-mode-only">\n  <picture>\n    <source media="(max-width: 640px)" srcset="${themedUrl('brand-compact', theme, number)}">\n    <img src="${themedUrl('brand', theme, number)}" width="100%" alt="${xml(label)}">\n  </picture>\n</a>`;
}
const brandLabel = `yiyiaddon · 独立 Fabric 客户端模组。最新正式版 ${version}；Minecraft ${latestGames.join(' / ')}。`
  + platformOverview + '。' + (fixedPortAvailable ? '' : '1.21.11 适配预告。');
const brandSection = `<!-- homepage:brand:start -->\n${themes.map(theme => responsiveBrand(theme, version, brandLabel)).join('\n')}\n<!-- homepage:brand:end -->`;
const brandPattern = /<!-- homepage:brand:start -->[\s\S]*?<!-- homepage:brand:end -->/;
const brandStartCount = (readme.match(/<!-- homepage:brand:start -->/g) || []).length;
const brandEndCount = (readme.match(/<!-- homepage:brand:end -->/g) || []).length;
if (brandStartCount === 0 && brandEndCount === 0) {
  // 首次迁移只替换已知品牌 picture；不误取前面的动态封面或其他图片。
  const brands = [...readme.matchAll(/<picture>[\s\S]*?<\/picture>/g)].filter(match =>
    /展示资源\/(?:homepage-20261023\/brand-(?:compact-)?(?:compat|release)|theme\/brand(?:-compact)?-(?:light|dark))\.svg/.test(match[0]));
  if (brands.length !== 1) throw new Error('首页品牌图片缺失或重复，停止覆盖');
  readme = readme.replace(brands[0][0], brandSection);
} else {
  if (brandStartCount !== 1 || brandEndCount !== 1 || !brandPattern.test(readme)) throw new Error('品牌区域边界缺失或重复，停止覆盖');
  readme = readme.replace(brandPattern, brandSection);
}
const card = item => {
  const label = `下载 Minecraft ${item.game} JAR · 正式版 ${item.version} · ${platformLabel(item)} · ${baritoneSummary}`;
  return themes.map(theme => `  <a href="${item.url}#gh-${theme}-mode-only"><img src="${themedUrl(`download-${item.game}`, theme, item.version)}" width="24%" alt="${xml(label)}"></a>`).join('\n');
};
// 四卡共用一个段落，桌面同排，较窄的阅读区由浏览器自然换行。
const cards = [...(fixedPort ? [readme.match(fixedPortPattern)[0]] : []), ...downloads.map(card)];
const cardStrip = `<p align="center">\n${cards.join('\n')}\n</p>`;
const environmentLabel = `${platformOverview}。Fabric Loader 0.19.5+；Minecraft 1.21.11 使用 Java 21，26 系列使用 Java 25；Mac 需 ARM64 Java。`;
const environment = `<p align="center">\n${themes.map(theme => `<a href="安装指南.md#gh-${theme}-mode-only"><picture><source media="(max-width: 640px)" srcset="${themedUrl('panel-environment-platforms-compact', theme, version)}"><img src="${themedUrl('panel-environment-platforms', theme, version)}" width="100%" alt="${xml(environmentLabel)}"></picture></a>`).join('\n')}\n</p>`;
const macEnvironmentNote = '**Mac 系统**请使用 **ARM64 Java**，Intel Mac 不在当前支持范围。请按上方各游戏版本标注的平台下载安装包；仅标注 Windows x64 的附件不适用于 Mac。完整要求见 [安装指南](安装指南.md)。';
const installation = panel("panel-install", "安装步骤：1 退出游戏，移出旧版 yiyiaddon，卸载自行安装的 Baritone；2 将对应版本 JAR 与 Fabric API 放入实例 mods 文件夹；3 启动游戏，按 G 打开界面并完成配置。", "安装指南.md", ui);
const installationLinks = navRow([
  { name: "install", href: "安装指南.md", label: "安装指南" },
  { name: "compat", href: "版本说明.md", label: "版本兼容" },
  { name: "release", href: `${repository}/releases/latest`, label: "正式版更新记录" },
], ui);
const section = `当前正式版 **${xml(version)}** · 选择与你的 Minecraft 版本一致的安装包。${fixedPort ? " 预告卡仅供查看。" : ""}\n\n${platformStatus ? platformStatus + '\n\n' : ''}> [!IMPORTANT]\n> **已内置经过调教优化的 Baritone。${baritoneWarning}，避免重复加载或冲突。**\n\n${cardStrip}\n\n${heading(4, "device-desktop", "运行环境", ui)}\n\n${macEnvironmentNote}\n\n${environment}\n\n${heading(4, "package", "三步完成安装", ui)}\n\n${installation}\n\n${installationLinks}`;

// 自动区域用明确边界，避免新增功能区、图标导航和人工文案被一次发布整段覆盖。
const sectionPattern = /<!-- homepage:downloads:start -->[\s\S]*?<!-- homepage:downloads:end -->/;
if ((readme.match(/<!-- homepage:downloads:start -->/g) || []).length !== 1
    || (readme.match(/<!-- homepage:downloads:end -->/g) || []).length !== 1
    || !sectionPattern.test(readme)) throw new Error('下载区域边界缺失或重复，停止覆盖');
readme = readme.replace(sectionPattern, `<!-- homepage:downloads:start -->\n${section}\n<!-- homepage:downloads:end -->`);
// 分类入口独立于下载区，保留手工内容；链接主题别名与归档锚点一起生成。
const historyButtons = ['stable', 'beta'].map(kind => themes.map(theme => {
  const file = path.join(themeFolder, `nav-${kind}-${theme}.svg`);
  const digest = createHash('sha256').update(fs.readFileSync(file)).digest('hex').slice(0, 8);
  const src = `展示资源/theme/nav-${kind}-${theme}.svg?v=${digest}`;
  const href = `发布说明/历史版本.md#user-content-${kind}#gh-${theme}-mode-only`;
  return `  <a href="${href}"><img src="${src}" width="160" alt="${kind === 'stable' ? '正式版本' : '测试版本'}"></a>`;
}).join('')).join('\n');
const historySection = `<!-- homepage:history:start -->\n${heading(3, "history", "历史版本", ui)}\n\n旧版安装包与测试记录分开归档；日常使用推荐最新正式版。\n\n<p align="center">\n${historyButtons}\n</p>\n<!-- homepage:history:end -->`;
const historyPattern = /<!-- homepage:history:start -->[\s\S]*?<!-- homepage:history:end -->/;
const historyStartCount = (readme.match(/<!-- homepage:history:start -->/g) || []).length;
const historyEndCount = (readme.match(/<!-- homepage:history:end -->/g) || []).length;
if (historyStartCount === 0 && historyEndCount === 0) {
  readme = readme.replace('<!-- homepage:downloads:end -->', `<!-- homepage:downloads:end -->\n\n${historySection}`);
} else {
  if (historyStartCount !== 1 || historyEndCount !== 1 || !historyPattern.test(readme)) throw new Error('历史入口边界缺失或重复，停止覆盖');
  readme = readme.replace(historyPattern, historySection);
}
const history = buildReleaseHistory({ catalog, latest, repository, root });
outputs.set(path.join(root, '发布说明/历史版本.md'), history.markdown);
// 下一版正式发布后，移除已兑现的旧预告，避免首页继续显示“尚无安装包”。
const previewPattern = /<!-- homepage:preview:start -->[\s\S]*?<!-- homepage:preview:end -->\n*/;
const preview = readme.match(previewPattern)?.[0];
const previewVersion = preview?.match(/releases\/tag\/v(\d+(?:\.\d+)*)-preview/)?.[1];
const compareVersion = (left, right) => {
  const a = left.split('.').map(Number), b = right.split('.').map(Number);
  for (let i = 0; i < Math.max(a.length, b.length); i++) {
    const difference = (a[i] || 0) - (b[i] || 0);
    if (difference) return difference;
  }
  return 0;
};
if (previewVersion && (!preview.includes('1.21.11') || fixedPortAvailable) && /^\d+(?:\.\d+)*$/.test(version) && compareVersion(version, previewVersion) >= 0) {
  readme = readme.replace(previewPattern, '');
}
// 只允许图片缓存摘要变化；预告必须仍在下载区首位，不能被发布刷新吞掉或补回顶部。
const withoutImageVersion = value => value?.replace(/\?v=[^"\s>]+/g, '');
if (fixedPort && withoutImageVersion(readme.match(fixedPortPattern)?.[0]) !== withoutImageVersion(fixedPort)) {
  throw new Error('固定适配预告被意外修改，停止覆盖');
}
if (fixedPort) {
  const finalSection = readme.match(sectionPattern)?.[0] || '';
  if (!finalSection.includes(readme.match(fixedPortPattern)[0])
      || finalSection.indexOf('homepage:fixed-port:start') > finalSection.indexOf(`href="${downloads[0].url}"`)) {
    throw new Error('适配预告没有保留在下载区第一张，停止覆盖');
  }
}
outputs.set(readmePath, ensureWebsiteEntry(readme, ui));
// 所有输入和槽位先验证，再写文件；工作流仅在全部成功后提交允许的玩家展示文件。
for (const [file, content] of outputs) {
  fs.mkdirSync(path.dirname(file), { recursive: true });
  fs.writeFileSync(file, content, 'utf8');
}
console.log(JSON.stringify({ latest: version, downloads, history: history.counts, files: outputs.size }, null, 2));
