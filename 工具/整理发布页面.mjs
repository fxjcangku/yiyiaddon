#!/usr/bin/env node
/**
 * 用核实过的 Release 附件和原文生成紧凑的发布页；不调用 GitHub 写入接口。
 *
 * node 工具/整理发布页面.mjs --catalog ../catalog-before.json \
 *   --analysis ../release-analysis.json --root . --asset-ref <commit> \
 *   --output ../release-bodies-generated.json
 *
 * analysis 中的 highlightsSegment / detailsSegment / historicalEnvironment /
 * updateSummaryComment 是原文快照，生成时不改字、不套用其他版本的技术内容。
 * sourceDocumentSupplement 可保存仓库发布说明独有、实际 Release 缺少的技术记录。
 * 所有页面先在内存中生成并校验，随后才写发布说明和待发布 JSON。
 */
import fs from 'node:fs';
import path from 'node:path';
import { createHash } from 'node:crypto';
import { fileURLToPath } from 'node:url';
import { themePair, navRow, heading, supportGrid, panel, repository, ensureWebsiteEntry } from './发布页面组件.mjs';

const defaultRoot = fileURLToPath(new URL('../', import.meta.url));
const sha256 = text => createHash('sha256').update(text).digest('hex');
const unicodeLength = text => [...text].length;
const readJson = file => JSON.parse(fs.readFileSync(file, 'utf8'));

function parseArguments(argv) {
  const result = {};
  const allowed = new Set(['catalog', 'analysis', 'root', 'asset-ref', 'output']);
  for (let i = 0; i < argv.length; i += 2) {
    const key = argv[i]?.replace(/^--/, '');
    if (!argv[i]?.startsWith('--') || !allowed.has(key) || !argv[i + 1]) {
      throw new Error(`参数无效：${argv[i] || '(缺少参数)'}。需要 --catalog、--analysis、--asset-ref、--output；可选 --root。`);
    }
    if (result[key]) throw new Error(`参数重复：--${key}`);
    result[key] = argv[i + 1];
  }
  for (const key of ['catalog', 'analysis', 'asset-ref', 'output']) {
    if (!result[key]) throw new Error(`缺少 --${key}`);
  }
  result.root = path.resolve(result.root || defaultRoot);
  if (!/^(?:[a-f\d]{40}|PUBLIC_ASSET_REF)$/i.test(result['asset-ref'])) {
    throw new Error('--asset-ref 必须是 40 位提交 SHA，或生成草稿时的 PUBLIC_ASSET_REF。');
  }
  return result;
}

// 旧导航仅是布局，不属于历史技术说明；保留封面、图注、概述和提醒。
function withoutOldNavigation(text) {
  return text
    .replace(/<p\b[^>]*>[\s\S]*?<\/p>/gi, block => /\/theme\/nav-[^\s"<>]+/.test(block) ? '' : block)
    .replace(/<a\s+(?:name|id)="(?:download|user-content-download)"\s*><\/a>/gi, '');
}

function historicalDownloadRecord(text) {
  return withoutOldNavigation(text)
    .replace(/<p\b[^>]*>[\s\S]*?<\/p>/gi, block => /\/theme\/(?:history\/)?download-[^\s"<>]+/.test(block) ? '' : block);
}

function compatibilityTables(release) {
  const tables = [...release.downloadSegment.matchAll(/^\|[^\r\n]*(?:\r?\n\|[^\r\n]*)+/gm)]
    .map(match => match[0])
    .filter(table => /Fabric API/.test(table));
  if (tables.length !== 1) throw new Error(`${release.tag} 应有一份原始 Fabric API 表，实际 ${tables.length} 份。`);
  return tables;
}

function packageAssets(release, catalogRelease) {
  const liveAssets = catalogRelease.assets || [];
  const verified = release.attachments || [];
  if (liveAssets.length !== verified.length) throw new Error(`${release.tag} 的附件清单已变化。`);
  for (const asset of verified) {
    const live = liveAssets.find(item => item.id === asset.id);
    if (!live || live.name !== asset.name || live.browser_download_url !== asset.browser_download_url) {
      throw new Error(`${release.tag} 附件与已核实快照不同：${asset.name}`);
    }
    if (!['jar', 'zip'].includes(String(asset.packageKind).toLowerCase()) || !asset.minecraftVersion) {
      throw new Error(`${release.tag} 未识别的安装附件：${asset.name}`);
    }
  }
  const order = ['1.21.11', '26.1.2', '26.2', '26.3'];
  return [...verified].sort((a, b) => order.indexOf(a.minecraftVersion) - order.indexOf(b.minecraftVersion));
}

function downloadCards(release, assets, options) {
  const version = release.tag.replace(/^v/, '');
  const width = release.tag === 'v1.2' ? '24%' : '32%';
  const cards = assets.map(asset => {
    const name = release.tag === 'v1.2'
      ? `download-${asset.minecraftVersion}`
      : `release-${version}-${asset.minecraftVersion}`;
    let alt = `下载 Minecraft ${asset.minecraftVersion} · yiyiaddon ${version} ${asset.packageKind.toUpperCase()} 安装包`;
    if (release.tag === 'v1.2') alt += ' · 已内置调教优化版 Baritone · 请卸载自行安装的 Baritone';
    return `  ${themePair(name, asset.browser_download_url, alt, width, options)}`;
  });
  return `<p align="center">\n${cards.join('\n')}\n</p>`;
}

function fold(label, content) {
  return `<details>\n<summary>${label}</summary>\n\n${content}\n</details>`;
}

// 原封面和相邻图注始终直接显示；兼容历史发布中的 HTML 与 Markdown 图片。
function originalCoverBlocks(release) {
  const markdownBlocks = [...release.originalIntro.matchAll(/^!\[[^\]\r\n]*\]\([^\r\n]+\)(?:\r?\n\*[^\r\n]+\*)?/gm)]
    .map(match => match[0]);
  return [...(release.coverImageBlocks || []), ...markdownBlocks]
    .sort((a, b) => release.originalIntro.indexOf(a) - release.originalIntro.indexOf(b));
}

function environmentAlt(release) {
  if (release.tag === 'v1.2') {
    return '运行环境：Windows x64；Fabric Loader 0.19.5+。Minecraft 1.21.11：Fabric API 0.141.6+1.21.11、Java 21；26.1.2：0.155.2+26.1.2、Java 25；26.2：0.161.0+26.2、Java 25；26.3：0.164.0+26.3、Java 25。';
  }
  return '运行环境：Windows x64、Java 25、Fabric Loader 0.19.5+。Minecraft 26.1.2：Fabric API 0.155.2+26.1.2；26.2：0.161.0+26.2；26.3：0.164.0+26.3。';
}

function installationPanel(release, options, guideUrl) {
  if (release.tag === 'v1.2') {
    return panel('panel-install', '安装步骤：1 退出游戏，移出旧版或重复的 yiyiaddon 和自行安装的 Baritone；2 将对应 JAR 与 Fabric API 放入 mods；3 启动后按 G 打开界面并配置。', guideUrl, options);
  }
  if (release.tag === 'v1.1') {
    return panel('panel-install-basic', '安装步骤：1 退出游戏，移出旧版或重复的 yiyiaddon；2 将对应 JAR 与 Fabric API 放入 mods；3 启动后按 G 打开界面并配置。', guideUrl, options);
  }
  return panel('panel-install-zip', '安装步骤：1 退出游戏，移出旧版或重复的 yiyiaddon 和自行安装的 Baritone；2 解压 ZIP，将其中 JAR 与 Fabric API 放入 mods；3 启动后按 G 打开界面并配置。', guideUrl, options);
}

/** 估算 GitHub 列表中图片和链接之外的原生正文；最终截断仍以 GitHub 渲染为准。 */
export function previewCharacterCount(text) {
  return unicodeLength(text
    .replace(/<!--[\s\S]*?-->/g, '')
    .replace(/!\[[^\]]*\]\([^)]*\)/g, '')
    .replace(/<[^>]*>/g, '')
    .replace(/\[([^\]]*)\]\([^)]*\)/g, '$1')
    .replace(/&(?:nbsp|amp|lt|gt|quot);/g, ' ')
    .replace(/[#*_`>]/g, '')
    .replace(/\s+/g, ' ')
    .trim());
}

function composeRelease(release, assets, options) {
  const version = release.tag.replace(/^v/, '');
  const guideUrl = `${options.docPrefix}安装指南.md`;
  const stableUrl = `${repository}/releases/latest`;
  const docsUrl = `${options.docPrefix}功能指南.md`;
  const status = release.prerelease ? '历史测试版' : (release.tag === 'v1.2' ? '正式版' : '历史正式版');
  const covers = originalCoverBlocks(release);
  const parts = [...covers, `**yiyiaddon ${version} · ${status}**\n\n${release.summary}`];

  if (assets.length) {
    const games = assets.map(asset => asset.minecraftVersion).join(' / ');
    const kinds = [...new Set(assets.map(asset => asset.packageKind.toUpperCase()))].join(' / ');
    parts.push(`Minecraft **${games}** · 本页提供 **${assets.length} 个 ${kinds} 安装包**。`);
    if (release.tag === 'v1.2') {
      const revision = release.introNotices.find(notice => notice.includes('2026-11-05'));
      if (!revision) throw new Error('v1.2 缺少 Minecraft 1.21.11 修订包通知。');
      parts.push(revision);
      parts.push('> [!IMPORTANT]\n> 已内置经过调教优化的 Baritone。**请卸载自行安装的 Baritone**，避免重复加载或冲突。');
    } else if (release.tag === 'v1.0') {
      const correction = release.introNotices.find(notice => notice.includes('发错版本'));
      if (!correction) throw new Error('v1.0 缺少原错包通知。');
      parts.push(correction);
      parts.push('已内置 Baritone，无需另装。ZIP 请先解压，再安装其中的 JAR。');
    }
    parts.push(heading(2, 'download', '下载与安装', options));
    parts.push(downloadCards(release, assets, options));
    parts.push(panel(release.tag === 'v1.2' ? 'panel-environment' : 'panel-environment-26', environmentAlt(release), guideUrl, options));
    parts.push(installationPanel(release, options, guideUrl));
    if (release.tag === 'v1.1') parts.push('聊天功能在聊天框的「设置」中调整。');
  } else {
    // 测试版已撤包：此处只说明历史范围，不借用新版环境或安装步骤。
    const games = [...new Set([...compatibilityTables(release)[0].matchAll(/\b(26\.1\.2|26\.2)\b/g)].map(match => match[1]))];
    if (!release.prerelease || !games.length) throw new Error(`${release.tag} 缺少已核实的历史支持范围。`);
    parts.push(`发布时支持 Minecraft **${games.join(' / ')}**。**当前未提供下载附件，仅保留历史更新记录。**`);
    parts.push(`需要安装时，请前往[当前正式版](${stableUrl})，并按当前版本说明选择安装包。`);
  }

  parts.push(navRow([
    { name: 'install', href: guideUrl, label: assets.length ? '安装指南' : '当前正式版安装指南' },
    { name: 'guide', href: docsUrl, label: '功能指南' },
    { name: 'release', href: release.releaseUrl, label: `${version} 完整更新记录` },
    { name: 'stable', href: stableUrl, label: '当前正式版本' },
  ], options));
  const prefix = ensureWebsiteEntry(parts.join('\n\n'), options);
  if (previewCharacterCount(prefix) > 400) throw new Error(`${release.tag} 首屏原生正文超过 400 字符。`);

  // 完整更新正文和原反馈信息均按原始字符保留，避免丢失特定旧版说明。
  parts.push(fold('本版更新亮点与完整记录', release.highlightsSegment + release.detailsSegment + release.originalFeedback));

  const historicalIntro = covers.reduce((text, cover) => text.replace(cover, ''), withoutOldNavigation(release.originalIntro));
  const historical = [historicalIntro];
  if (release.historicalEnvironment) historical.push(release.historicalEnvironment);
  else historical.push(historicalDownloadRecord(release.downloadSegment));
  if (release.sourceDocumentSupplement) historical.push(release.sourceDocumentSupplement);
  parts.push(fold(covers.length ? '发布时的补充说明' : '发布时的封面与说明', historical.join('\n\n')));
  parts.push(heading(2, 'book', '文档与支持', options));
  parts.push(supportGrid(options));

  // 保留解析器使用的原始表格，但不占列表预览的可见正文字符数。
  parts.push(`<!-- release:compatibility\n${compatibilityTables(release).join('\n\n')}\n-->`);
  let body = ensureWebsiteEntry(parts.join('\n\n') + '\n', options);
  if (release.updateSummaryComment) body += '\n' + release.updateSummaryComment;
  return { body, prefixCharacters: previewCharacterCount(prefix) };
}

function verifyPreservation(release, body) {
  for (const cover of originalCoverBlocks(release)) {
    if (!body.slice(0, body.indexOf('<details>')).includes(cover) || body.split(cover).length !== 2) {
      throw new Error(`${release.tag} 原封面与图注必须在折叠区之前完整显示一次。`);
    }
  }
  for (const name of ['highlightsSegment', 'detailsSegment', 'originalFeedback', 'historicalEnvironment', 'updateSummaryComment', 'sourceDocumentSupplement']) {
    if (release[name] && !body.includes(release[name])) throw new Error(`${release.tag} 丢失原始字段 ${name}。`);
  }
  if (release.updateSummaryComment && !body.endsWith(release.updateSummaryComment)) {
    throw new Error(`${release.tag} 更新摘要注释未原样置末尾。`);
  }
  if (release.prerelease && /\/releases\/download\/v1\.0-beta/.test(body)) {
    throw new Error(`${release.tag} 出现已不存在的测试版下载链接。`);
  }
  if (release.tag === 'v1.1' && /Baritone/.test(body)) {
    throw new Error('v1.1 原文未证实 Baritone 信息，不可从其他版本套用。');
  }
}

function main() {
  const args = parseArguments(process.argv.slice(2));
  const catalog = readJson(args.catalog);
  const analysis = readJson(args.analysis);
  if (!Array.isArray(catalog) || !Array.isArray(analysis.releases)) throw new Error('catalog 或 analysis 结构无效。');
  if (catalog.length !== analysis.releases.length) throw new Error('catalog 与 analysis 的 Release 数量不同。');
  const options = {
    root: args.root,
    assetPrefix: `https://raw.githubusercontent.com/fxjcangku/yiyiaddon/${args['asset-ref']}/`,
    release: true,
    docPrefix: `${repository}/blob/master/`,
  };
  const sourceOptions = { root: args.root, assetPrefix: '../', docPrefix: '../' };
  const prepared = analysis.releases.map(release => {
    const item = catalog.find(candidate => candidate.id === release.id && candidate.tag_name === release.tag);
    if (!item || sha256(item.body || '') !== release.originalBodySha256) throw new Error(`${release.tag} 原文与分析快照不一致。`);
    if (item.prerelease !== release.prerelease || item.draft) throw new Error(`${release.tag} 状态与快照不同，或仍为草稿。`);
    const assets = packageAssets(release, item);
    const rendered = composeRelease(release, assets, options);
    const source = composeRelease(release, assets, sourceOptions);
    verifyPreservation(release, rendered.body);
    verifyPreservation(release, source.body);
    const notesPath = release.notesPath || (release.tag === 'v1.1' ? '发布说明/1.1.md' : null);
    if (!notesPath || !/^发布说明\/[^/]+\.md$/.test(notesPath)) throw new Error(`${release.tag} 发布说明路径无效。`);
    return { id: release.id, tag: release.tag, body: rendered.body, notesPath, source: source.body, prefixCharacters: rendered.prefixCharacters };
  });
  if (new Set(prepared.map(item => item.notesPath)).size !== prepared.length) throw new Error('发布说明路径重复。');
  for (const item of prepared) {
    const destination = path.join(args.root, item.notesPath);
    fs.mkdirSync(path.dirname(destination), { recursive: true });
    fs.writeFileSync(destination, item.source);
  }
  const output = path.resolve(args.output);
  fs.mkdirSync(path.dirname(output), { recursive: true });
  fs.writeFileSync(output, JSON.stringify(prepared.map(({ id, tag, body, notesPath }) => ({ id, tag, body, notesPath })), null, 2) + '\n');
  process.stdout.write(JSON.stringify({ output, releases: prepared.length, assetRef: args['asset-ref'], prefixCharacters: Object.fromEntries(prepared.map(item => [item.tag, item.prefixCharacters])) }, null, 2) + '\n');
}

if (process.argv[1] && path.resolve(process.argv[1]) === fileURLToPath(import.meta.url)) {
  try { main(); } catch (error) { process.stderr.write(`${error.message}\n`); process.exitCode = 1; }
}
