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
const games = ['26.1.2', '26.2', '26.3'];
const xml = value => String(value).replaceAll('&', '&amp;').replaceAll('<', '&lt;').replaceAll('>', '&gt;').replaceAll('"', '&quot;');

/** 每条游戏版本独立选择最新正式附件；没有该线新包时不能把另一条线或预告冒充可下载包。 */
const downloads = games.map(game => {
  for (const release of candidates) {
    const number = release.tag_name.replace(/^v/, '');
    const asset = (release.assets || []).find(item => item.name === `yiyiaddon-${number}-${game}.jar`
      && item.size > 0 && item.state === 'uploaded');
    if (!asset) continue;
    if (!asset.browser_download_url.startsWith(`${repository}/releases/download/`)) throw new Error('安装包链接不属于本仓库');
    const row = (release.body || '').split('\n').map(line => line.split('|').map(cell => cell.trim().replaceAll('**', '')))
      .find(cells => cells[1] === game);
    const dependency = row?.[3]?.match(/\d+\.\d+\.\d+\+\d+(?:\.\d+)+/)?.[0] || null;
    return { game, version: number, url: asset.browser_download_url, dependency, tag: release.tag_name };
  }
  throw new Error(`未找到 Minecraft ${game} 的正式 JAR，保留现有首页`);
});
const latestGames = downloads.filter(item => item.tag === latest.tag_name).map(item => item.game);
if (!latestGames.length) throw new Error('最新正式版没有可核实安装包，保留现有首页');

/** 原 SVG 保留几何与材质，只为已有文字槽增加稳定 ID 并更新对应文本。 */
function textSlot(svg, id, selector, value) {
  if (!svg.includes(`id="${id}"`)) {
    if (!svg.includes(selector)) throw new Error(`展示资源缺少文字槽 ${id}`);
    svg = svg.replace(selector, `${selector} id="${id}"`);
  }
  const pattern = new RegExp(`(<text\\b[^>]*\\bid="${id}"[^>]*>)[^<]*(</text>)`);
  if (!pattern.test(svg)) throw new Error(`无法更新文字槽 ${id}`);
  return svg.replace(pattern, (_, open, close) => `${open}${xml(value)}${close}`);
}
const folder = path.join(root, '展示资源/homepage-20261023');
const outputs = new Map();
for (const compact of [false, true]) {
  const name = compact ? 'brand-compact.svg' : 'brand.svg';
  let svg = fs.readFileSync(path.join(folder, name), 'utf8');
  svg = textSlot(svg, 'release-version', compact ? '<text x="30" y="249"' : '<text x="850" y="120"', version);
  const summary = (latest.name || '').split(/[｜|]/).slice(1).join(' · ').trim() || '查看最新正式版更新';
  if (!compact) svg = textSlot(svg, 'release-title', '<text x="854" y="212"', Array.from(summary).slice(0, 14).join(''));
  svg = textSlot(svg, 'supported-games', compact ? '<text x="30" y="290"' : '<text x="854" y="173"',
    `${compact ? '' : 'Minecraft '}${latestGames.join(' / ')}`);
  const label = `yiyiaddon ${version} · 独立 Fabric 客户端模组`;
  svg = svg.replace(/aria-label="[^"]*"/, `aria-label="${xml(label)}"`).replace(/<title>[^<]*<\/title>/, `<title>${xml(label)}</title>`);
  outputs.set(path.join(folder, name), svg);
}
for (const item of downloads) {
  const name = `download-${item.game}.svg`;
  let svg = fs.readFileSync(path.join(folder, name), 'utf8');
  svg = textSlot(svg, 'release-version', '<text x="688" y="55"', item.version);
  svg = textSlot(svg, 'fabric-api', '<text x="32" y="222"', item.dependency ? `Fabric API  ${item.dependency}` : '配套 Fabric API 见下载页');
  svg = textSlot(svg, 'download-note', '<text x="32" y="258"', '此游戏版本最新可用正式包');
  svg = svg.replace('下载 ZIP ↗', '下载 JAR ↗');
  const label = `下载 Minecraft ${item.game} JAR · 最新可用正式版 ${item.version}`;
  svg = svg.replace(/aria-label="[^"]*"/, `aria-label="${xml(label)}"`).replace(/<title>[^<]*<\/title>/, `<title>${xml(label)}</title>`);
  outputs.set(path.join(folder, name), svg);
}
const readmePath = path.join(root, 'README.md');
let readme = fs.readFileSync(readmePath, 'utf8').replaceAll('\r\n', '\n');
readme = readme.replace(/(src="展示资源\/homepage-20261023\/brand.svg(?:\?[^\"]*)?"[^>]*alt=")[^"]*/,
  `$1yiyiaddon · 自动化执行，专注你的世界。最新正式版 ${xml(version)}；Minecraft ${latestGames.join(' / ')}。`);
// 图片 URL 随真实 SVG 内容变化，避免 GitHub 图片代理继续显示上一版宣传。
const imageUrl = (name, number) => `展示资源/homepage-20261023/${name}?v=${encodeURIComponent(number)}-${createHash('sha256').update(outputs.get(path.join(folder, name))).digest('hex').slice(0, 8)}`;
readme = readme.replace(/(src=")展示资源\/homepage-20261023\/brand.svg(?:\?[^\"]*)?(\")/,
  (_, open, close) => open + imageUrl('brand.svg', version) + close);
readme = readme.replace(/(srcset=")展示资源\/homepage-20261023\/brand-compact.svg(?:\?[^\"]*)?(\")/,
  (_, open, close) => open + imageUrl('brand-compact.svg', version) + close);
const navigation = `<p align="center">\n  <a href="${repository}/releases/latest"><strong>下载最新版本 ↗</strong></a> &nbsp; · &nbsp;\n  <a href="#下载与安装"><strong>按游戏版本下载 ↗</strong></a> &nbsp; · &nbsp;\n  <a href="功能指南.md"><strong>功能指南 ↗</strong></a> &nbsp; · &nbsp;\n  <a href="界面预览.md"><strong>界面预览 ↗</strong></a>\n</p>`;
const navPattern = /<p align="center">\s*<a href="(?:#下载与安装|https:\/\/github.com\/fxjcangku\/yiyiaddon\/releases\/latest)"[\s\S]*?<\/p>/;
if (!navPattern.test(readme)) throw new Error('首页导航结构已改变，停止覆盖');
readme = readme.replace(navPattern, navigation);
const card = item => `  <a href="${item.url}"><img src="${imageUrl(`download-${item.game}.svg`, item.version)}" width="400" alt="下载 Minecraft ${item.game} JAR · 最新可用正式版 ${item.version}"></a>`;
const section = `### 下载与安装\n\n[下载最新版本](${repository}/releases/latest) &nbsp; · &nbsp; [正式版发布记录](${repository}/releases?q=prerelease%3Afalse) &nbsp; · &nbsp; [测试版历史](${repository}/releases?q=prerelease%3Atrue)\n\n当前最新正式版为 **${xml(version)}**。下面三个按钮直接下载对应 Minecraft 版本最新可用的混淆 JAR；发布新正式版后自动更新，预告与测试版不会替换正式包。\n\n<p align="center">\n${downloads.slice(0, 2).map(card).join('\n')}\n</p>\n\n<p align="center">\n${card(downloads[2])}\n</p>\n\n**运行环境** &nbsp; Windows x64 · Java 25 · Fabric Loader 0.19.5+\n\n将对应游戏版本的 JAR 放入 <code>mods</code>，并单独安装匹配的 Fabric API。Baritone 已内置；具体支持范围与依赖以对应下载页为准。\n\n[安装指南 →](安装指南.md) &nbsp; · &nbsp; [版本与兼容 →](版本说明.md) &nbsp; · &nbsp; [查看最新更新记录 →](${repository}/releases/latest)\n\n`;
const sectionPattern = /### 下载与安装\n[\s\S]*?(?=### 文档与支持)/;
if (!sectionPattern.test(readme)) throw new Error('下载区域结构已改变，停止覆盖');
outputs.set(readmePath, readme.replace(sectionPattern, section));
// 所有输入和槽位先验证，再写文件；工作流仅在全部成功后提交允许的玩家展示文件。
for (const [file, content] of outputs) fs.writeFileSync(file, content, 'utf8');
console.log(JSON.stringify({ latest: version, downloads, files: outputs.size }, null, 2));
