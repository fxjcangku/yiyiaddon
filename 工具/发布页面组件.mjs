import fs from 'node:fs';
import path from 'node:path';
import { createHash } from 'node:crypto';
import { fileURLToPath } from 'node:url';

export const repository = 'https://github.com/fxjcangku/yiyiaddon';
export const html = value => String(value).replaceAll('&', '&amp;').replaceAll('<', '&lt;').replaceAll('>', '&gt;').replaceAll('"', '&quot;');
const defaultRoot = fileURLToPath(new URL('../', import.meta.url));
function asset(relative, options = {}) {
  const file = path.join(options.root || defaultRoot, relative);
  const digest = createHash('sha256').update(fs.readFileSync(file)).digest('hex').slice(0, 8);
  return `${options.assetPrefix || ''}${relative}?v=${digest}`;
}
export function docLink(name, options = {}) {
  return `${options.docPrefix || ''}${name}`;
}
// GitHub 根据链接片段选择本站主题；不依赖操作系统的 prefers-color-scheme。
export function themePair(name, href, alt, width = '100%', options = {}) {
  return ['light', 'dark'].map(theme => `<a href="${html(href)}#gh-${theme}-mode-only"><img src="${html(asset(`展示资源/theme/${name}-${theme}.svg`, options))}" width="${html(width)}" alt="${html(alt)}"></a>`).join('');
}
export function icon(name, options = {}, size = 24) {
  return `<img src="${html(asset(`展示资源/icons/octicons/${name}-24.svg`, options))}" width="${size}" height="${size}" alt="">`;
}
export function heading(level, iconName, label, options = {}) {
  return `${'#'.repeat(level)} ${icon(iconName, options)} ${label}`;
}
export function navRow(items, options = {}) {
  const width = items.length >= 4 ? '23%' : '27%';
  return `<p align="center">\n${items.map(item => `  ${themePair(`nav-${item.name}`, item.href, item.label, item.width || width, options)}`).join('\n')}\n</p>`;
}
export function panel(name, alt, href, options = {}) {
  return `<p align="center">\n${themePair(name, href, alt, '100%', options)}\n</p>`;
}
export function supportGrid(options = {}) {
  const link = (file, label) => `<a href="${html(docLink(file, options))}">${label}</a>`;
  const cards = [
    ['book', '功能与界面', '按模块查看配置方法与界面示例。', `${link('功能指南.md', '功能指南')} · ${link('界面预览.md', '界面预览')}`],
    ['package', '安装与兼容', '核对游戏版本、Java 与 Fabric 依赖。', `${link('安装指南.md', '安装指南')} · ${link('版本说明.md', '版本兼容')}`],
    ['bug', '问题与反馈', '反馈时附上版本、复现步骤和截图。', `<a href="${repository}/issues">提交问题</a> · ${link('常见问题.md', '常见问题')}`],
    ['history', '版本与使用须知', '查看历次更新，并按服务器规则使用。', `${link('发布说明/历史版本.md', '历史版本')} · ${link('服务器使用注意.md', '使用须知')}`],
  ];
  const cell = ([name, title, description, links]) => `<td width="600" valign="top"><h4>${icon(name, options)} ${title}</h4><p>${description}</p><p>${links}</p></td>`;
  return `<table align="center">\n<tbody>\n<tr>${cards.slice(0, 2).map(cell).join('')}</tr>\n<tr>${cards.slice(2).map(cell).join('')}</tr>\n</tbody>\n</table>`;
}
