import fs from 'node:fs';
import path from 'node:path';
import { heading, html, navRow, panel, repository, supportGrid, websiteEntry } from './发布页面组件.mjs';
import { previewCharacterCount } from './整理发布页面.mjs';

// 与 1.2 共用展示组件 仅整理 1.3 准备页 不创建 Release 或安装附件
// 内容边界保留完整记录 反复生成不会把外层折叠再次套进正文
const marker = name => `yiyiaddon:release-${name}`;
const wrap = (name, content) => `<!-- ${marker(name)}:start -->\n${content.trim()}\n<!-- ${marker(name)}:end -->`;
const fold = (label, content) => `<details>\n<summary>${label}</summary>\n\n${content.trim()}\n\n</details>`;

function requiredMatch(body, pattern, label) {
  const found = body.match(pattern);
  if (!found) throw new Error(`1.3 准备页缺少 ${label}`);
  return found[1].trim();
}

function markedOr(body, name, fallback) {
  const start = `<!-- ${marker(name)}:start -->`;
  const end = `<!-- ${marker(name)}:end -->`;
  const starts = body.split(start).length - 1;
  const ends = body.split(end).length - 1;
  if (!starts && !ends) return fallback();
  if (starts !== 1 || ends !== 1 || body.indexOf(end) < body.indexOf(start)) {
    throw new Error(`1.3 准备页 ${name} 内容边界异常`);
  }
  return body.slice(body.indexOf(start) + start.length, body.indexOf(end)).trim();
}

function highlightGrid(summary) {
  if (summary.version !== '1.3' || !Array.isArray(summary.items) || summary.items.length !== 4) {
    throw new Error('1.3 更新摘要应保留四项亮点');
  }
  const cells = summary.items.map(item => {
    const at = item.indexOf('：');
    if (at < 1) throw new Error('1.3 更新摘要缺少亮点标题');
    return `<td width="600" valign="top"><h3>${html(item.slice(0, at))}</h3><p>${html(item.slice(at + 1))}</p></td>`;
  });
  return `<!-- release:highlights-layout:start -->\n<table align="center">\n<tbody>\n<tr>\n${cells.slice(0, 2).join('\n')}\n</tr>\n<tr>\n${cells.slice(2).join('\n')}\n</tr>\n</tbody>\n</table>\n<!-- release:highlights-layout:end -->`;
}

export function composePreparedRelease(original, { root, release }) {
  const body = original.replace(/\r\n/g, '\n');
  const options = {
    root,
    release,
    assetPrefix: release ? 'https://raw.githubusercontent.com/fxjcangku/yiyiaddon/master/' : '../',
    docPrefix: release ? `${repository}/blob/master/` : '../',
  };
  const guideUrl = `${options.docPrefix}安装指南.md`;
  const prepareUrl = 'https://www.yiyiaddon.asia/release.html?prepare=1.3';
  const cover = requiredMatch(body, /^(<p align="center">[\s\S]*?<\/p>)/, '地图画封面');
  if (!cover.includes('mapart-kobe-clean-1536.webp')) throw new Error('准备页必须保留干净科比地图画封面');
  const summaryComment = requiredMatch(body, /(<!-- yiyiaddon:update-summary\s*[\s\S]*?-->)/, '更新摘要');
  const summary = JSON.parse(requiredMatch(summaryComment, /<!-- yiyiaddon:update-summary\s*([\s\S]*?)-->/, '更新摘要数据'));
  const overview = markedOr(body, 'overview', () => requiredMatch(body,
    /\*\*全自动地图画 与 Mac 原生适配\*\*\s*\n\s*\n([\s\S]*?)\n\s*\n\*\*/, '概述'));
  const notes = markedOr(body, 'notes', () => requiredMatch(body,
    /(^## 详细更新记录\n[\s\S]*?)(?=^## 兼容与运行\n)/m, '18 模块完整更新记录'));
  const install = markedOr(body, 'install', () => requiredMatch(body,
    /<!-- yiyiaddon:prepared-downloads:end -->\s*([\s\S]*?)(?=^## 本次亮点\n)/m, '完整安装说明'));
  const compatibility = markedOr(body, 'compatibility', () => requiredMatch(body,
    /(^## 兼容与运行\n[\s\S]*?)(?=^## 使用与反馈\n)/m, '兼容与使用边界'));
  const feedback = markedOr(body, 'feedback', () => requiredMatch(body,
    /(^## 使用与反馈\n[\s\S]*?)(?=^<sub>|^<!-- yiyiaddon:update-summary)/m, '使用与反馈'));
  const prepared = requiredMatch(body,
    /<!-- yiyiaddon:prepared-downloads:start -->\s*([\s\S]*?)\s*<!-- yiyiaddon:prepared-downloads:end -->/, '版本卡');
  const cards = requiredMatch(prepared, /(<p align="center">[\s\S]*?<\/p>)/, '四个版本卡');
  // 卡片生成器会先刷新这里 原始依赖表移入补充说明 不占首屏
  const dependencyTable = requiredMatch(prepared,
    /(\| 游戏版本 \| 1\.3 安装包 \| Java \| Fabric API \|\n(?:\|[^\n]*\n?)+)/, '完整版本依赖');
  const moduleCount = (notes.match(/<summary><strong>/g) || []).length;
  const entryCount = (notes.match(/^- \*\*(?:新增|优化|修复|说明)\*\*/gm) || []).length;
  if (moduleCount !== 18 || entryCount !== 146) {
    throw new Error(`完整更新记录数量变化 预期 18 模块 146 条 实际 ${moduleCount} 模块 ${entryCount} 条`);
  }

  const preparedCards = `<!-- yiyiaddon:prepared-downloads:start -->\n${cards}\n<!-- yiyiaddon:prepared-downloads:end -->`;
  const prefix = [
    cover,
    '**yiyiaddon 1.3 · 全自动地图画与 Mac 原生适配**',
    '导入投影 连续铺设 自动补货 成图锁定 同步完善农场 附魔 聊天与运行反馈',
    'Minecraft **1.21.11 / 26.1.2 / 26.2 / 26.3** · Windows x64 与 Mac',
    '**安装包待上传** · 下方版本卡查看安装要求 当前正式下载仍为 1.2',
    '> [!IMPORTANT]\n> 已内置经过调教优化的 Baritone **请卸载自行安装的 Baritone** 避免重复加载或冲突',
    websiteEntry(options),
    heading(2, 'download', '下载与安装', options),
    preparedCards,
    panel('panel-environment-desktop', '运行环境：Windows x64 与 Mac 系统 ARM64；Fabric Loader 0.19.5+；1.21.11 使用 Java 21，26 系列使用 Java 25；Mac 使用原生 ARM64 Java。1.3 安装包待上传。', guideUrl, options),
    panel('panel-install', '正式附件上传后的安装步骤：1 退出游戏，移出旧版 yiyiaddon 与自行安装的 Baritone；2 将对应版本 desktop JAR 与 Fabric API 放入 mods；3 启动后按 G 打开界面并完成配置。', guideUrl, options),
    navRow([
      { name: 'install', href: guideUrl, label: '安装指南' },
      { name: 'guide', href: `${options.docPrefix}功能指南.md`, label: '功能指南' },
      { name: 'release', href: prepareUrl, label: '1.3 完整更新记录' },
      { name: 'stable', href: `${repository}/releases/latest`, label: '当前正式版本' },
    ], options),
  ].join('\n\n');
  if (previewCharacterCount(prefix) > 400) throw new Error('1.3 首屏原生正文超过 1.2 使用的 400 字符限制');
  const updates = [
    '## 本次亮点',
    wrap('overview', overview),
    highlightGrid(summary),
    wrap('notes', notes),
    wrap('feedback', feedback),
  ].join('\n\n');
  const supplementary = [
    '## 下载与安装',
    fold('查看可复制的版本依赖表', `${dependencyTable}\n\n四个版本分别使用对应的 desktop JAR Fabric Loader 统一使用 0.19.5 或以上`),
    wrap('install', install),
    wrap('compatibility', compatibility),
    '<sub>封面为功能概念图 不作为实机验收截图</sub>',
  ].join('\n\n');
  const result = [
    prefix,
    fold('本版更新亮点与完整记录', updates),
    fold('发布时的补充说明', supplementary),
    heading(2, 'book', '文档与支持', options),
    supportGrid(options),
    `<!-- release:compatibility\n${dependencyTable}\n-->`,
    summaryComment,
  ].join('\n\n') + '\n';

  // 外观重排不得改变逐条更新说明 或生成未上传的 1.3 安装地址
  for (const originalContent of [notes, overview, install, compatibility, feedback, summaryComment]) {
    if (!result.includes(originalContent)) throw new Error('页面整理丢失原始说明');
  }
  if (/\/releases\/download\/v?1\.3\//.test(result)) throw new Error('准备页不能生成 1.3 安装包链接');
  for (const assetName of ['panel-environment-desktop', 'panel-install', 'nav-install', 'nav-guide', 'nav-release', 'nav-stable']) {
    for (const theme of ['light', 'dark']) {
      if (!fs.existsSync(path.join(root, `展示资源/theme/${assetName}-${theme}.svg`))) throw new Error(`缺少主题资源 ${assetName}-${theme}`);
    }
  }
  return result;
}
