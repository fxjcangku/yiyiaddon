import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import { createHash } from 'node:crypto';

/** 只根据真实正式 Release 与已上传附件刷新玩家首页；不读取模组源码，不发布或修改安装包。 */
const args = process.argv.slice(2);
const option = name => { const at = args.indexOf(name); return at < 0 ? null : args[at + 1]; };
const root = path.resolve(option('--root') || fileURLToPath(new URL('../', import.meta.url)));
const readJson = name => { const file = option(name); if (!file) throw new Error(`缺少参数 ${name}`); return JSON.parse(fs.readFileSync(file, 'utf8')); };
const latest = readJson('--latest');
const catalog = readJson('--catalog').flat();
const repository = 'https://github.com/fxjcangku/yiyiaddon';
if (!latest || latest.draft || latest.prerelease || !/^v\d/.test(latest.tag_name)) throw new Error('Latest 必须是真实正式版');
const version = latest.tag_name.slice(1);
const stable = catalog.filter(item => !item.draft && !item.prerelease && item.tag_name !== latest.tag_name)
  .sort((a, b) => Date.parse(b.published_at) - Date.parse(a.published_at));
const candidates = [latest, ...stable];
const games = ['1.21.11', '26.1.2', '26.2', '26.3'];
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
    const asset = (release.assets || []).find(item => item.name === `yiyiaddon-${number}-${game}.jar`
      && item.size > 0 && item.state === 'uploaded');
    if (!asset) continue;
    if (asset.browser_download_url !== [repository, "releases/download", release.tag_name, asset.name].join("/")) throw new Error('安装包链接不属于本仓库');
    const dependency = fabricDependency(release.body, game);
    return { game, version: number, url: asset.browser_download_url, dependency, tag: release.tag_name };
  }
  if (game === '1.21.11') return null;
  throw new Error(`未找到 Minecraft ${game} 的正式 JAR，保留现有首页`);
}).filter(Boolean);
const fixedPortAvailable = downloads.some(item => item.game === '1.21.11');
const latestGames = downloads.filter(item => item.tag === latest.tag_name).map(item => item.game);
if (!latestGames.length) throw new Error('最新正式版没有可核实安装包，保留现有首页');

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
};
const lightBrand = svg => svg.replace(/#[\da-f]{6}\b/gi, color => lightBrandColors[color.toLowerCase()] || color);
const themedBrandName = (compact, theme) => `brand${compact ? '-compact' : ''}-${theme}.svg`;
const themedDownloadName = (game, theme) => `download-${game}-${theme}.svg`;
// 首次正式发布四个版本时使用新路径，避开旧路径缓存。
const brandName = compact => 'brand' + (compact ? '-compact' : '') + (fixedPortAvailable ? '-release.svg' : '-compat.svg');
const downloadName = game => 'download-' + game + (fixedPortAvailable ? '-release.svg' : '.svg');
for (const compact of [false, true]) {
  const name = brandName(compact);
  const template = compact ? 'brand-compact-compat.svg' : 'brand-compat.svg';
  let svg = fs.readFileSync(path.join(folder, template), 'utf8');
  svg = textSlot(svg, 'release-version', compact ? '<text x="30" y="249"' : '<text x="850" y="120"', version);
  const summary = (latest.name || '').split(/[｜|]/).slice(1).join(' · ').trim() || '查看最新正式版更新';
  if (!compact) svg = textSlot(svg, 'release-title', '<text x="854" y="212"', Array.from(summary).slice(0, 14).join(''));
  svg = textSlot(svg, 'supported-games', compact ? '<text x="30" y="290"' : '<text x="854" y="173"',
    `${compact || latestGames.length === 4 ? '' : 'Minecraft '}${latestGames.join(' / ')}`);
  if (fixedPortAvailable) svg = svg.replace(/<text[^>]*>1\.21\.11 适配预告<\/text>/, '');
  const label = 'yiyiaddon ' + version + ' · 独立 Fabric 客户端模组 · Minecraft ' + latestGames.join(' / ')
    + (fixedPortAvailable ? '' : ' · 1.21.11 适配预告');
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
    let svg = fs.readFileSync(file, 'utf8');
    svg = textSlot(svg, 'game-version', null, item.game);
    svg = textSlot(svg, 'release-version', null, item.version);
    svg = textSlot(svg, 'fabric-api', null, item.dependency ? `Fabric API  ${item.dependency}` : '配套 Fabric API 见下载页');
    svg = textSlot(svg, 'download-note', null, 'Baritone 寻路已内置');
    const label = `下载 Minecraft ${item.game} JAR · 正式版 ${item.version} · Baritone 寻路已内置`;
    svg = svg.replace(/aria-label="[^"]*"/, `aria-label="${xml(label)}"`).replace(/<title>[^<]*<\/title>/, `<title>${xml(label)}</title>`);
    outputs.set(file, svg);
    // 保留旧图片 URL 的输出兼容；现行首页仅引用新的明暗组件。
    if (theme === 'dark') outputs.set(path.join(folder, downloadName(item.game)), svg);
  }
}
const readmePath = path.join(root, 'README.md');
let readme = fs.readFileSync(readmePath, 'utf8').replaceAll('\r\n', '\n');
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
const themedUrl = (name, theme, number) => imageUrl(path.join(themeFolder, `${name}-${theme}.svg`), number);
function themedPicture(name, number, width, label, compact = false) {
  const sources = compact ? [
    `  <source media="(max-width: 640px) and (prefers-color-scheme: dark)" srcset="${themedUrl(`${name}-compact`, 'dark', number)}">`,
    `  <source media="(max-width: 640px) and (prefers-color-scheme: light)" srcset="${themedUrl(`${name}-compact`, 'light', number)}">`,
  ] : [];
  sources.push(
    `  <source media="(prefers-color-scheme: dark)" srcset="${themedUrl(name, 'dark', number)}">`,
    `  <source media="(prefers-color-scheme: light)" srcset="${themedUrl(name, 'light', number)}">`,
    `  <img src="${themedUrl(name, 'light', number)}" width="${width}" alt="${xml(label)}">`,
  );
  return `<picture>\n${sources.join('\n')}\n</picture>`;
}
const brandLabel = `yiyiaddon · 独立 Fabric 客户端模组。最新正式版 ${version}；Minecraft ${latestGames.join(' / ')}。`
  + (fixedPortAvailable ? '' : '1.21.11 适配预告。');
const brandSection = `<!-- homepage:brand:start -->\n${themedPicture('brand', version, '100%', brandLabel, true)}\n<!-- homepage:brand:end -->`;
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
  const label = `下载 Minecraft ${item.game} JAR · 正式版 ${item.version} · Baritone 寻路已内置`;
  return `  <a href="${item.url}">\n    ${themedPicture(`download-${item.game}`, item.version, '194', label).replaceAll('\n', '\n    ')}\n  </a>`;
};
// 四卡共用一个段落，桌面同排，较窄的阅读区由浏览器自然换行。
const cards = [...(fixedPort ? [readme.match(fixedPortPattern)[0]] : []), ...downloads.map(card)];
const cardStrip = `<p align="center">\n${cards.join('\n')}\n</p>`;
const section = `当前正式版 **${xml(version)}**。选择与你的 Minecraft 一致的版本，正式版按钮直接下载 JAR。${fixedPort ? " 预告卡仅供查看。" : ""}\n\n${cardStrip}\n\n**Baritone 寻路已整合进 yiyiaddon 的 JAR，无需另外下载或安装 Baritone。**\n\n**运行环境** &nbsp; Windows x64 · Fabric Loader 0.19.5+ · ${fixedPortAvailable ? "1.21.11 使用 Java 21，26 系列使用 Java 25" : "Java 25"}\n\n1. 退出游戏，将对应版本的 JAR 放入游戏实例的 <code>mods</code> 文件夹。\n2. 安装配套 **Fabric API**，移出重复旧包。\n3. 启动游戏，默认按 **G** 打开界面，按模块说明完成配置。\n\n[详细安装指南 →](安装指南.md) &nbsp; · &nbsp; [正式版更新记录 →](${repository}/releases/latest) &nbsp; · &nbsp; [版本与兼容 →](版本说明.md)`;
// 自动区域用明确边界，避免新增功能区、图标导航和人工文案被一次发布整段覆盖。
const sectionPattern = /<!-- homepage:downloads:start -->[\s\S]*?<!-- homepage:downloads:end -->/;
if ((readme.match(/<!-- homepage:downloads:start -->/g) || []).length !== 1
    || (readme.match(/<!-- homepage:downloads:end -->/g) || []).length !== 1
    || !sectionPattern.test(readme)) throw new Error('下载区域边界缺失或重复，停止覆盖');
readme = readme.replace(sectionPattern, `<!-- homepage:downloads:start -->\n${section}\n<!-- homepage:downloads:end -->`);
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
outputs.set(readmePath, readme);
// 所有输入和槽位先验证，再写文件；工作流仅在全部成功后提交允许的玩家展示文件。
for (const [file, content] of outputs) {
  fs.mkdirSync(path.dirname(file), { recursive: true });
  fs.writeFileSync(file, content, 'utf8');
}
console.log(JSON.stringify({ latest: version, downloads, files: outputs.size }, null, 2));

