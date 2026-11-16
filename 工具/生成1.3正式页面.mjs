#!/usr/bin/env node
/** 使用实际八包快照和既有 18 组正文生成本地交付目录，不调用发布接口。 */
import fs from 'node:fs';
import path from 'node:path';
import { fileURLToPath } from 'node:url';
import { execFileSync } from 'node:child_process';
import { composeRelease } from './整理发布页面.mjs';
import { games, repository, packageAssets, requireDualPlatformRelease, fabricDependency, assetDownloadUrl } from './发行附件.mjs';

const marker = (name, text) => `<!-- yiyiaddon:release-${name}:start -->\n${text.trim()}\n<!-- yiyiaddon:release-${name}:end -->`;
function section(body, name) {
  const start = `<!-- yiyiaddon:release-${name}:start -->`;
  const end = `<!-- yiyiaddon:release-${name}:end -->`;
  if (body.split(start).length !== 2 || body.split(end).length !== 2 || body.indexOf(start) > body.indexOf(end)) {
    throw new Error(`原 1.3 正文缺少唯一 ${name} 内容边界`);
  }
  return body.slice(body.indexOf(start) + start.length, body.indexOf(end)).trim();
}
function finalPlatformText(text) {
  return text.replaceAll('计划按每个 Minecraft 版本分别提供', '按每个 Minecraft 版本分别提供')
    .replaceAll('四个 Minecraft 版本计划各提供', '四个 Minecraft 版本各提供')
    .replaceAll('八个独立安装包尚未上传', '八个独立安装包分别对应游戏版本与运行系统');
}

export function compose13FormalPage(original, catalogRelease, options) {
  const draftPreview = options.draftPreview === true;
  if (options.publicationBody && (!draftPreview || catalogRelease.draft !== true
      || !/^[a-f\d]{40}$/i.test(options.assetRef || '') || options.release === false)) {
    throw new Error('发布请求正文仅允许真实 1.3 草稿的本地预览，并须使用确切 40 位资源提交 SHA');
  }
  if (catalogRelease.tag_name !== 'v1.3') throw new Error('该入口只处理真实 v1.3 快照');
  requireDualPlatformRelease(catalogRelease, games, { draftPreview });
  const assets = packageAssets(catalogRelease, { draftPreview });
  for (const asset of assets) {
    if (!/^sha256:[a-f\d]{64}$/.test(asset.digest || '')) throw new Error(`缺少真实附件 SHA-256：${asset.name}`);
  }
  const body = original.replaceAll('\r\n', '\n');
  const cover = body.match(/^(<p align="center">[\s\S]*?<\/p>)/)?.[1];
  if (!cover?.includes('mapart-kobe-clean-1536.webp')) throw new Error('必须保留既定地图画封面');
  const notes = finalPlatformText(section(body, 'notes'));
  const modules = (notes.match(/<summary><strong>/g) || []).length;
  const entries = (notes.match(/^- \*\*(?:新增|优化|修复|说明)\*\*/gm) || []).length;
  if (modules !== 18 || entries !== 146) throw new Error(`原正文必须为 18 组 146 条，实际 ${modules} 组 ${entries} 条`);
  const comments = [...body.matchAll(/<!-- yiyiaddon:update-summary\s*([\s\S]*?)-->/g)];
  if (comments.length !== 1) throw new Error('必须保留一份更新摘要');
  const summary = JSON.parse(comments[0][1]);
  if (summary.version !== '1.3' || summary.items?.length !== 4) throw new Error('原更新摘要版本或四项亮点不一致');
  summary.items = summary.items.map(finalPlatformText);
  const updateSummaryComment = `<!-- yiyiaddon:update-summary\n${JSON.stringify(summary, null, 2)}\n-->`;
  const highlights = body.match(/(## 本次亮点\n[\s\S]*?)<!-- yiyiaddon:release-notes:start -->/)?.[1];
  if (!highlights) throw new Error('未找到既有亮点版式');
  const dependencies = games.map(game => {
    const api = fabricDependency(body, game);
    if (!api) throw new Error(`原正文缺少 Minecraft ${game} 的实际 Fabric API 依赖`);
    return { game, java: game === '1.21.11' ? 21 : 25, api };
  });
  const dependencyTable = ['| 游戏版本 | 1.3 安装包 | Java | Fabric API |', '| --- | --- | --- | --- |',
    ...dependencies.map(item => {
      const pair = assets.filter(asset => asset.game === item.game);
      const links = ['windows-x64', 'mac-arm64'].map(target => {
        const asset = pair.find(value => value.target === target);
        return `[下载 ${target === 'windows-x64' ? 'Windows x64' : 'Mac x64'}](${assetDownloadUrl(asset, options)})`;
      }).join(' · ');
      return `| ${item.game} | ${links} | ${item.java} | \`${item.api}\` |`;
    })].join('\n');
  const installation = section(body, 'install')
    .replace('计划按 1.21.11、26.1.2、26.2、26.3 四个游戏版本分别提供', '按 1.21.11、26.1.2、26.2、26.3 四个游戏版本分别提供')
    .replace('当前八包尚未上传，不能把历史 desktop 或 Windows 包改名充当分系统构建。', '本页提供八个独立混淆 JAR，请按 Minecraft 版本和运行系统选择。')
    .replace(/^当前没有可下载的 1\.3 附件[^\n]*$/m, '四个游戏版本各提供 Windows x64 与 Mac x64 两个独立下载入口，历史无平台后缀包继续保留其 Windows x64 兼容范围。')
    .replace('1. 正式附件提供后 先核对', '1. 先核对');
  const priorRegressionNote = '以下为发布准备期间的既有功能回归记录，最终分系统安装包的逐包验证结果单独记录。';
  const compatibility = section(body, 'compatibility').replaceAll(priorRegressionNote + '\n', '').replace(/\n{3,}/g, '\n\n')
    .replace('## 兼容与运行\n', `## 兼容与运行\n\n${priorRegressionNote}\n`)
    .replace('宣传封面为功能概念图 1.3 分系统安装包尚未上传', '宣传封面为功能概念图，请以具体版本、系统与场景的实际验收记录为准');
  const hashes = ['## 文件校验', '', '| Minecraft | 系统 | 文件 | 字节数 | SHA-256 |', '| --- | --- | --- | ---: | --- |',
    ...assets.sort((a, b) => games.indexOf(a.game) - games.indexOf(b.game) || ((a.target === 'mac-arm64') - (b.target === 'mac-arm64')))
      .map(asset => `| ${asset.game} | ${asset.target === 'windows-x64' ? 'Windows x64' : 'Mac x64（实际 ARM64）'} | [${asset.name}](${assetDownloadUrl(asset, options)}) | ${asset.size} | \`${asset.digest.slice(7)}\` |`)].join('\n');
  const publishedDate = draftPreview ? '' : ` · ${new Intl.DateTimeFormat('sv-SE', { timeZone: 'Asia/Shanghai', year: 'numeric', month: '2-digit', day: '2-digit' }).format(new Date(catalogRelease.published_at))}`;
  const release = {
    tag: 'v1.3', prerelease: false, releaseUrl: `${repository}/releases/tag/v1.3`,
    featureTitle: catalogRelease.name || 'yiyiaddon 1.3｜全自动地图画与 Mac 适配',
    summary: `全自动地图画与 Mac x64 原生适配${publishedDate}`,
    originalIntro: cover, coverImageBlocks: [cover], introNotices: [],
    downloadSegment: dependencyTable,
    highlightsSegment: finalPlatformText(highlights), detailsSegment: marker('notes', notes),
    originalFeedback: marker('feedback', section(body, 'feedback')),
    historicalEnvironment: [dependencyTable, marker('install', installation), marker('compatibility', compatibility), hashes].join('\n\n'),
    updateSummaryComment,
  };
  const generatedAssets = new Map();
  const releaseLayout = options.release !== false;
  const result = composeRelease(release, assets, { ...options, generatedAssets,
    release: releaseLayout, latestTag: 'v1.3', draftPreview, catalogRelease,
    assetPrefix: releaseLayout ? `https://raw.githubusercontent.com/fxjcangku/yiyiaddon/${options.assetRef}/` : '../',
    docPrefix: releaseLayout ? `${repository}/blob/master/` : '../',
  });
  if ((result.body.match(/<summary><strong>/g) || []).length !== 18
      || (section(result.body, 'notes').match(/^- \*\*(?:新增|优化|修复|说明)\*\*/gm) || []).length !== 146) {
    throw new Error('生成后完整更新条目丢失');
  }
  if (/安装包待上传|安装包尚未上传|当前正式下载仍为 1\.2|等待正式附件上传|prepare=1\.3/.test(result.body)) {
    throw new Error('正式正文仍含当前 1.3 准备状态');
  }
  return { ...result, generatedAssets, modules, entries, assets };
}

/** 仅在真实正式版模式下暂存当前指南；历史附件和验证记录原样保留。 */
function published13Guides(root) {
  const output = new Map();
  const changes = {
    '安装指南.md': [
      ['**Mac x64 原生适配已完成 当前为 1.3 发布准备** 计划提供四个游戏版本各自的 `win64` 与 `mac64` 独立混淆 JAR 共八包 当前均尚未上传 [现有正式下载](https://github.com/fxjcangku/yiyiaddon/releases/latest)仍为 Windows x64 的 1.2 附件', '**1.3 正式版提供八个独立混淆 JAR** 四个游戏版本各有 Windows x64 的 `win64` 与 Mac x64 的 `mac64` 两包 [下载正式版](https://github.com/fxjcangku/yiyiaddon/releases/tag/v1.3)'],
      ['当前可使用对应游戏版本的 1.2 正式 JAR；1.3 计划提供对应游戏版本的 `win64` 独立包', '下载对应游戏版本的 1.3 `win64` 独立 JAR'],
      ['1.3 计划提供对应游戏版本的 `mac64` 独立包，目前等待正式附件上传', '下载对应游戏版本的 1.3 `mac64` 独立 JAR'],
      ['1.3 计划分别使用 `-win64.jar` 与 `-mac64.jar` 后缀 当前八包尚未上传', '1.3 分别使用 `-win64.jar` 与 `-mac64.jar` 后缀'],
    ],
    '版本说明.md': [
      ['**1.3 页面已进入发布准备** 地图画与 Mac x64 的介绍见[准备页](发布准备/1.3.md) 新安装包尚未上传 当前公开正式版仍为 **1.2**', '**1.3 已正式发布** 四个游戏版本各提供 Windows x64 与 Mac x64 独立 JAR 详见[完整更新记录](发布说明/1.3.md)与[真实附件](https://github.com/fxjcangku/yiyiaddon/releases/tag/v1.3)'],
      ['1.3 `win64` 独立 JAR（待上传）', '1.3 `win64` 独立 JAR'],
      ['1.3 `mac64` 独立 JAR（待上传）', '1.3 `mac64` 独立 JAR'],
      ['| 支持目标 |', '| 可下载 |'], ['支持目标，需要原生 ARM64 Java', '可下载，需要原生 ARM64 Java'],
      ['1.3 计划按四个 Minecraft 版本', '1.3 按四个 Minecraft 版本'],
      ['当前八包尚未上传，不能把旧下载链接当作 Mac 安装入口；', '下载时选择对应游戏版本与系统的独立 JAR；'],
      ['[1.3 验证进度](发布准备/1.3.md#验证进度)', '[1.3 兼容与运行](发布说明/1.3.md#兼容与运行)'],
      ['## 1.3 准备版本的依赖', '## 1.3 正式版的依赖'],
      ['正式附件发布时按实际发布页再次核对依赖', '依赖与本次实际发布页一致'],
    ],
    '功能指南.md': [
      ['**1.3 发布准备**：全自动地图画与 Mac x64 原生适配已完成 新安装包尚未上传 当前 1.2 下载不会自动获得本节新功能 [查看完整准备说明](发布准备/1.3.md)', '**1.3 已正式发布**：全自动地图画与 Mac x64 原生适配已随对应独立安装包提供 [查看完整更新说明](发布说明/1.3.md)'],
      ['1.3 计划四版本各有 `win64` 与 `mac64` 两包，共八包，目前均未上传；', '1.3 四个版本各提供 `win64` 与 `mac64` 两包，共八包；'],
    ],
  };
  for (const [file, replacements] of Object.entries(changes)) {
    let content = fs.readFileSync(path.join(root, file), 'utf8');
    for (const [before, after] of replacements) content = content.replaceAll(before, after);
    if (/待上传|尚未上传|1\.3 计划|当前公开正式版仍为/.test(content)) throw new Error(`当前指南仍含 1.3 准备状态：${file}`);
    output.set(file, content);
  }
  return output;
}

function main() {
  const args = process.argv.slice(2);
  const option = name => { const at = args.indexOf(name); return at < 0 ? null : args[at + 1]; };
  const root = path.resolve(option('--root') || fileURLToPath(new URL('../', import.meta.url)));
  const releaseFile = option('--release'), output = option('--output-dir');
  const assetRef = option('--asset-ref');
  if (!releaseFile || !output || !/^(?:[a-f\d]{40}|PUBLIC_ASSET_REF)$/i.test(assetRef || '')) {
    throw new Error('需要 --release 实际快照 --output-dir 私有交付目录 --asset-ref 资源提交SHA或PUBLIC_ASSET_REF');
  }
  const outputDir = path.resolve(output);
  if (outputDir === root || outputDir.startsWith(root + path.sep)) throw new Error('交付目录必须位于公开工作树之外');
  const publicationBody = args.includes('--publication-body');
  if (publicationBody && !args.includes('--draft-preview')) throw new Error('--publication-body 必须配合 --draft-preview');
  const catalogRelease = JSON.parse(fs.readFileSync(releaseFile, 'utf8'));
  const formalSource = path.join(root, '发布说明/1.3.md');
  const original = fs.readFileSync(fs.existsSync(formalSource) ? formalSource : path.join(root, '发布准备/1.3.md'), 'utf8');
  const result = compose13FormalPage(original, catalogRelease, { root, assetRef, draftPreview: args.includes('--draft-preview') });
  let publication;
  if (publicationBody) {
    publication = compose13FormalPage(original, catalogRelease, { root, assetRef, draftPreview: true, publicationBody: true });
    if (execFileSync('git', ['-C', root, 'cat-file', '-t', assetRef], { encoding: 'utf8' }).trim() !== 'commit') {
      throw new Error('资源引用必须是真实 Git 提交');
    }
    for (const [relative, content] of publication.generatedAssets) {
      const committed = execFileSync('git', ['-C', root, 'show', `${assetRef}:${relative}`], { encoding: 'utf8' });
      if (committed !== content) throw new Error(`资源提交中的 SVG 与当前真实附件生成结果不同：${relative}`);
    }
  }
  for (const [relative, content] of result.generatedAssets) {
    const file = path.join(outputDir, relative);
    fs.mkdirSync(path.dirname(file), { recursive: true });
    fs.writeFileSync(file, content);
  }
  fs.writeFileSync(path.join(outputDir, 'release-body.md'), result.body);
  if (publication) fs.writeFileSync(path.join(outputDir, 'release-publication-body.md'), publication.body);
  if (!catalogRelease.draft) {
    const repositoryPage = compose13FormalPage(original, catalogRelease, { root, assetRef, release: false });
    const stagedDocs = published13Guides(root);
    stagedDocs.set('发布说明/1.3.md', repositoryPage.body);
    stagedDocs.set('发布准备/1.3.md', '# yiyiaddon 1.3 已正式发布\n\n[查看完整正式更新记录](../发布说明/1.3.md) · [下载 1.3](https://github.com/fxjcangku/yiyiaddon/releases/tag/v1.3) · [安装指南](../安装指南.md)\n\n四个 Minecraft 版本各提供 Windows x64 与 Mac x64 两份独立 JAR。Mac x64 为展示名，实际支持 macOS ARM64，使用 ARM64 Java，不支持 Intel Mac。\n');
    for (const [relative, content] of stagedDocs) {
      const file = path.join(outputDir, relative);
      fs.mkdirSync(path.dirname(file), { recursive: true });
      fs.writeFileSync(file, content);
    }
  }
  console.log(JSON.stringify({ mode: catalogRelease.draft ? 'draft-preview' : 'published', modules: result.modules,
    entries: result.entries, prefixCharacters: result.prefixCharacters, assets: result.assets.length,
    generatedImages: result.generatedAssets.size, publicationBody: !!publication, outputDir }, null, 2));
}

if (process.argv[1] && fs.realpathSync(process.argv[1]) === fs.realpathSync(fileURLToPath(import.meta.url))) main();
