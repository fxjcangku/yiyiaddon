import fs from 'node:fs';
import path from 'node:path';
import { html as xml, themePair } from './发布页面组件.mjs';

const baritoneNote = '已内置调教优化版 Baritone';
const baritoneWarning = '请卸载自行安装的 Baritone';
const baritoneSummary = `${baritoneNote} · ${baritoneWarning}`;
const platformPaths = {
  windows: 'M2 2h9v9H2zm11 0h9v9h-9zM2 13h9v9H2zm11 0h9v9h-9z',
  mac: 'M12.152 6.896c-.948 0-2.415-1.078-3.96-1.04-2.04.027-3.91 1.183-4.961 3.014-2.117 3.675-.546 9.103 1.519 12.09 1.013 1.454 2.208 3.09 3.792 3.039 1.52-.065 2.09-.987 3.935-.987 1.831 0 2.35.987 3.96.948 1.637-.026 2.676-1.48 3.676-2.948 1.156-1.688 1.636-3.325 1.662-3.415-.039-.013-3.182-1.221-3.22-4.857-.026-3.04 2.48-4.494 2.597-4.559-1.429-2.09-3.623-2.324-4.39-2.376-2-.156-3.675 1.09-4.61 1.09zM15.53 3.83c.843-1.012 1.4-2.427 1.245-3.83-1.207.052-2.662.805-3.532 1.818-.78.896-1.454 2.338-1.273 3.714 1.338.104 2.715-.688 3.559-1.701',
};
const platformIcon = (name, x, y, size, color) => `<g transform="translate(${x} ${y}) scale(${size / 24})" fill="${color}"><path d="${platformPaths[name]}"/></g>`;

const octicon = (root, name, x, y, size, color) => {
  const source = fs.readFileSync(path.join(root, `展示资源/icons/octicons/${name}-24.svg`), 'utf8');
  const paths = source.match(/<svg\b[^>]*>([\s\S]*)<\/svg>/)?.[1];
  if (!paths) throw new Error(`无法读取图标 ${name}`);
  return `<g transform="translate(${x} ${y}) scale(${size / 24})" fill="${color}">${paths}</g>`;
};

export function downloadPlatformCard(source, item, theme, { root }) {
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
  const macStatus = item.mac ? (item.mac.suffix === 'desktop' ? '历史通用包可用' : '现可下载') : '尚未发布';
  const label = `下载 Windows x64 · Minecraft ${item.game} · 正式版 ${item.version} · Mac x64（ARM64）：${macStatus} · ${baritoneSummary}`;
  return `<svg xmlns="http://www.w3.org/2000/svg" width="480" height="459" viewBox="0 0 480 459" role="img" aria-label="${xml(label)}">
<title>${xml(label)}</title>
${definitions}
<g font-family="Segoe UI, Microsoft YaHei, Noto Sans CJK SC, PingFang SC, Arial, sans-serif">
<rect x="1" y="1" width="478" height="445" rx="24" fill="url(#bg)" stroke="${border}" stroke-width="1.5"/>
<rect x="1" y="1" width="478" height="445" rx="24" fill="url(#glow)"/>
<text x="28" y="45" font-size="21" fill="${muted}" font-weight="600" letter-spacing="1.7">MINECRAFT</text>
<text x="452" y="45" font-size="20" fill="${accent}" font-weight="650" id="release-version" text-anchor="end">${xml(item.version)}</text>
<text x="27" y="111" font-size="52" fill="${ink}" font-weight="700" id="game-version" letter-spacing="-1">${xml(item.game)}</text>
${platformIcon('windows', 28, 134, 22, accent)}<text x="62" y="154" font-size="22" font-weight="600" fill="${ink}">Windows x64</text><text x="452" y="153" text-anchor="end" font-size="18" fill="${accent}">现可下载</text>
${platformIcon('mac', 28, 170, 23, accent)}<text x="62" y="191" font-size="22" font-weight="600" fill="${ink}">Mac x64</text><text x="452" y="190" text-anchor="end" font-size="18" fill="${item.mac ? accent : warning}">${xml(macStatus)}</text>
<path d="M28 209H452" stroke="${border}"/>
<text x="28" y="241" font-size="25" fill="${accent}" font-weight="600" id="download-note">${xml(baritoneNote)}</text>
<text x="28" y="275" font-size="23" fill="${warning}" font-weight="650" id="baritone-warning">${xml(baritoneWarning)}</text>
<text x="28" y="309" font-size="22" fill="${muted}" font-weight="400" id="fabric-api">${xml(item.dependency ? `Fabric API  ${item.dependency}` : '配套 Fabric API 见下载页')}</text>
<rect x="25" y="331" width="430" height="96" rx="15" fill="${accent}"/>
${octicon(root, 'download', 44, 365, 28, onAccent)}<text x="88" y="391" font-size="30" fill="${onAccent}" font-weight="650">下载 Windows x64</text>
<g transform="translate(404 365) scale(1.1666666667)" fill="none" stroke="${onAccent}" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M5 12h14m-6-6 6 6-6 6"/></g>
</g></svg>\n`;
}

export function macDownloadButton(source, item, theme, { root }) {
  const dark = theme === 'dark';
  const accent = source.match(/<text\b[^>]*id="download-note"[^>]*fill="([^"]+)"/)?.[1]
    || source.match(/<text\b[^>]*fill="([^"]+)"[^>]*id="download-note"/)?.[1];
  if (!accent) throw new Error(`Mac 按钮缺少 ${item.game} 配色槽`);
  const enabled = !!item.mac;
  const fill = enabled ? accent : dark ? '#27313e' : '#e8edf2';
  const ink = enabled ? dark ? '#101828' : '#FFFFFF' : dark ? '#b6c2d2' : '#617084';
  const label = enabled ? '下载 Mac x64' : 'Mac x64 待发布';
  const alt = `${label} · Minecraft ${item.game} · yiyiaddon ${item.version} · 实际架构 ARM64，使用 ARM64 Java，不支持 Intel Mac`;
  return `<svg xmlns="http://www.w3.org/2000/svg" width="480" height="104" viewBox="0 0 480 104" role="img" aria-label="${xml(alt)}">
<title>${xml(alt)}</title><desc>${enabled ? '按钮链接来自本游戏版本、本 Release 的实际公开 Mac 附件' : '当前 Release 没有匹配的 Mac 安装包，本图没有下载链接'}</desc>
<g font-family="Segoe UI, Microsoft YaHei, Noto Sans CJK SC, PingFang SC, Arial, sans-serif">
<rect x="25" y="4" width="430" height="96" rx="15" fill="${fill}"/>
${octicon(root, enabled ? 'download' : 'device-desktop', 44, 38, 28, ink)}
<text x="88" y="64" font-size="30" fill="${ink}" font-weight="650">${xml(label)}</text>
${enabled ? `<g transform="translate(404 38) scale(1.1666666667)" fill="none" stroke="${ink}" stroke-width="1.8" stroke-linecap="round" stroke-linejoin="round"><path d="M5 12h14m-6-6 6 6-6 6"/></g>` : ''}
</g></svg>\n`;
}

/** 每个游戏版本保持 Windows 原入口，Mac 使用相邻的独立原生链接；缺包时只显示无链接状态图。 */
export function renderPlatformDownloadGrid(pairs, options) {
  if (!(options.generatedAssets instanceof Map)) throw new Error('下载组件需要生成资源 Map');
  // GitHub raw 的分支路径可能仍缓存旧内容；发行版本与平台进入真实路径，不能仅依赖 query。
  const names = options.names || (item => ({ windows: `download-${item.version}-${item.game}-windows`, mac: `download-${item.version}-${item.game}-mac` }));
  const cells = pairs.map(item => {
    if (!item.windows || item.windows.game !== item.game || item.windows.tag !== item.tag
        || item.windows.version !== item.version || item.windows.format !== 'JAR'
        || !['windows-x64', 'desktop'].includes(item.windows.target)
        || (item.mac && (item.mac.game !== item.game || item.mac.tag !== item.tag
          || item.mac.version !== item.version || item.mac.format !== 'JAR'
          || !['mac-arm64', 'desktop'].includes(item.mac.target)))) {
      throw new Error(`下载按钮必须使用 ${item.game} / ${item.tag} 的同版真实附件`);
    }
    const name = names(item);
    for (const theme of ['light', 'dark']) {
      const source = fs.readFileSync(path.join(options.root, `展示资源/theme/download-${item.game}-${theme}.svg`), 'utf8');
      options.generatedAssets.set(`展示资源/theme/${name.windows}-${theme}.svg`, downloadPlatformCard(source, item, theme, options));
      options.generatedAssets.set(`展示资源/theme/${name.mac}-${theme}.svg`, macDownloadButton(source, item, theme, options));
    }
    const windows = themePair(name.windows, item.windows.browser_download_url,
      `下载 Windows x64 · Minecraft ${item.game} · yiyiaddon ${item.version}`, '220', options);
    const mac = themePair(name.mac, item.mac?.browser_download_url || null,
      `${item.mac ? '下载 Mac x64' : 'Mac x64 待发布，无下载链接'} · Minecraft ${item.game} · yiyiaddon ${item.version} · 实际 ARM64，需要 ARM64 Java，不支持 Intel Mac`, '220', options);
    return `<td width="25%" valign="top">\n${windows}<br>\n${mac}\n</td>`;
  });
  return `<table align="center">\n<tbody><tr>\n${cells.join('\n')}\n</tr></tbody>\n</table>`;
}
