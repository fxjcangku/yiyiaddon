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

/** 保留封面、按钮的排版，只更新稳定 ID 对应的发布文字；旧槽位兼容原有资源。 */
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
  const name = compact ? 'brand-compact-compat.svg' : 'brand-compat.svg';
  let svg = fs.readFileSync(path.join(folder, name), 'utf8');
  svg = textSlot(svg, 'release-version', compact ? '<text x="30" y="249"' : '<text x="850" y="120"', version);
  const summary = (latest.name || '').split(/[｜|]/).slice(1).join(' · ').trim() || '查看最新正式版更新';
  if (!compact) svg = textSlot(svg, 'release-title', '<text x="854" y="212"', Array.from(summary).slice(0, 14).join(''));
  svg = textSlot(svg, 'supported-games', compact ? '<text x="30" y="290"' : '<text x="854" y="173"',
    `${compact ? '' : 'Minecraft '}${latestGames.join(' / ')}`);
  const label = `yiyiaddon ${version} · 独立 Fabric 客户端模组 · 1.21.11 适配预告`;
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
// 固定适配预告独立于下一版摘要：正式版号递增不能被视为该游戏版本已经有公开安装包。
const fixedPortPattern = /<!-- homepage:fixed-port:start -->[\s\S]*?<!-- homepage:fixed-port:end -->/;
const fixedPort = readme.match(fixedPortPattern)?.[0];
for (const marker of ['start', 'end']) {
  const count = (readme.match(new RegExp(`<!-- homepage:fixed-port:${marker} -->`, 'g')) || []).length;
  if (count > 1 || (fixedPort && count !== 1)) throw new Error('固定适配预告边界重复或不完整');
}
// 预告与正式卡同一区域、同一尺寸；只保留查看预告入口，不加入 games 或生成下载地址。
const fixedPortCard = path.join(folder, 'preview-1.21.11-compat.svg');
if (fixedPort && fs.existsSync(fixedPortCard)) {
  outputs.set(fixedPortCard, fs.readFileSync(fixedPortCard, 'utf8'));
}
readme = readme.replace(/(src="展示资源\/homepage-20261023\/brand-compat.svg(?:\?[^\"]*)?"[^>]*alt=")[^"]*/,
  `$1yiyiaddon · 独立 Fabric 客户端模组。最新正式版 ${xml(version)}；Minecraft ${latestGames.join(' / ')}。1.21.11 适配预告。`);
// 图片 URL 随真实 SVG 内容变化，避免 GitHub 图片代理继续显示上一版宣传。
const imageUrl = (name, number) => `展示资源/homepage-20261023/${name}?v=${encodeURIComponent(number)}-${createHash('sha256').update(outputs.get(path.join(folder, name))).digest('hex').slice(0, 8)}`;
readme = readme.replace(/(src=")展示资源\/homepage-20261023\/brand-compat.svg(?:\?[^\"]*)?(\")/,
  (_, open, close) => open + imageUrl('brand-compat.svg', version) + close);
readme = readme.replace(/(srcset=")展示资源\/homepage-20261023\/brand-compact-compat.svg(?:\?[^\"]*)?(\")/,
  (_, open, close) => open + imageUrl('brand-compact-compat.svg', version) + close);
if (outputs.has(fixedPortCard)) {
  readme = readme.replace(/(src=")展示资源\/homepage-20261023\/preview-1\.21\.11.svg(?:\?[^\"]*)?(\")/,
    (_, open, close) => open + imageUrl('preview-1.21.11-compat.svg', version) + close);
}
const card = item => `  <a href="${item.url}"><img src="${imageUrl(`download-${item.game}.svg`, item.version)}" width="194" alt="下载 Minecraft ${item.game} JAR · 最新可用正式版 ${item.version}"></a>`;
// 四卡共用一个段落，桌面同排，较窄的阅读区由浏览器自然换行。
const cards = [...(fixedPort ? [readme.match(fixedPortPattern)[0]] : []), ...downloads.map(card)];
const cardStrip = `<p align="center">\n${cards.join('\n')}\n</p>`;
const section = `当前正式版 **${xml(version)}**。选择与你的 Minecraft 一致的版本；预告卡仅供查看，正式版按钮直接下载 JAR。\n\n${cardStrip}\n\n**运行环境** &nbsp; Windows x64 · Java 25 · Fabric Loader 0.19.5+\n\n1. 退出游戏，将对应版本的 JAR 放入游戏实例的 <code>mods</code> 文件夹。\n2. 安装配套 **Fabric API**，移出重复旧包；**Baritone 已内置**。\n3. 启动游戏，默认按 **G** 打开界面，按模块说明完成配置。\n\n[详细安装指南 →](安装指南.md) &nbsp; · &nbsp; [正式版更新记录 →](${repository}/releases/latest) &nbsp; · &nbsp; [版本与兼容 →](版本说明.md)`;
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
if (previewVersion && /^\d+(?:\.\d+)*$/.test(version) && compareVersion(version, previewVersion) >= 0) {
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
      || finalSection.indexOf('homepage:fixed-port:start') > finalSection.indexOf('download-26.1.2.svg')) {
    throw new Error('适配预告没有保留在下载区第一张，停止覆盖');
  }
}
outputs.set(readmePath, readme);
// 所有输入和槽位先验证，再写文件；工作流仅在全部成功后提交允许的玩家展示文件。
for (const [file, content] of outputs) fs.writeFileSync(file, content, 'utf8');
console.log(JSON.stringify({ latest: version, downloads, files: outputs.size }, null, 2));
