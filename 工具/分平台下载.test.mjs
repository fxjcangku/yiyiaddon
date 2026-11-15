import test from 'node:test';
import assert from 'node:assert/strict';
import fs from 'node:fs';
import path from 'node:path';
import os from 'node:os';
import { fileURLToPath } from 'node:url';
import { execFileSync } from 'node:child_process';
import { games, repository, packageAssets, releaseGamePairs, selectHomepageDownloads, requireDualPlatformRelease, fabricDependency } from './发行附件.mjs';
import { renderPlatformDownloadGrid } from './分平台下载组件.mjs';
import { buildReleaseHistory } from './生成历史版本.mjs';
import { composeRelease } from './整理发布页面.mjs';

const root = fileURLToPath(new URL('../', import.meta.url));
const real = JSON.parse(fs.readFileSync(new URL('./测试数据/1.2公开附件.json', import.meta.url), 'utf8'));
const clone = value => JSON.parse(JSON.stringify(value));
// 合成版本只在内存与测试临时目录使用；不访问网络，不写回仓库 README，不发布 Release。
function synthetic(version = '99.0.0', suffixes = ['win64', 'mac64']) {
  const release = { ...clone(real), id: 990000, tag_name: `v${version}`, name: `本地测试 ${version}`,
    published_at: '2026-11-15T00:00:00Z', html_url: `${repository}/releases/tag/v${version}`, assets: [] };
  for (const game of games) for (const suffix of suffixes) {
    const name = `yiyiaddon-${version}-${game}${suffix ? `-${suffix}` : ''}.jar`;
    release.assets.push({ id: 990000 + release.assets.length + 1, name, state: 'uploaded', size: 1024,
      browser_download_url: `${repository}/releases/download/${release.tag_name}/${name}` });
  }
  return release;
}
function presentation(release, options = {}) {
  const generatedAssets = new Map();
  const pairs = releaseGamePairs(release).map(item => ({ ...item, dependency: fabricDependency(release.body, item.game) }));
  const html = renderPlatformDownloadGrid(pairs, { root, release: true, generatedAssets, ...options });
  return { html, generatedAssets, pairs };
}
const hrefs = text => [...text.matchAll(/<a href="([^"]+)"/g)].map(match => match[1]);

test('真实 1.2 四包仍是 Windows，Mac 不借用其 URL', () => {
  const { downloads } = selectHomepageDownloads(real, [real]);
  assert.equal(downloads.length, 4);
  for (const pair of downloads) {
    assert.equal(pair.target, 'windows-x64'); assert.equal(pair.mac, null);
    assert.equal(pair.url, real.assets.find(asset => asset.name.endsWith(`-${pair.game}.jar`)).browser_download_url);
  }
});
test('四版本八专用包不依赖 API 附件排列顺序', () => {
  const release = synthetic();
  const first = releaseGamePairs(release);
  release.assets.reverse();
  assert.deepEqual(releaseGamePairs(release), first);
  assert.deepEqual(first.map(item => item.game), games);
  assert.ok(first.every(item => item.windows.suffix === 'win64' && item.mac.suffix === 'mac64'));
});
test('专用包优先于真实历史 desktop 和无后缀包', () => {
  const release = synthetic('99.0.0', ['', 'desktop', 'mac64', 'win64']);
  assert.ok(releaseGamePairs(release).every(item => item.windows.suffix === 'win64' && item.mac.suffix === 'mac64'));
});
test('历史 desktop 可有两个独立入口，但不冒充八个专用构建', () => {
  const release = synthetic('99.0.0', ['desktop']);
  assert.ok(releaseGamePairs(release).every(item => item.windows.browser_download_url === item.mac.browser_download_url));
  assert.throws(() => requireDualPlatformRelease(release), /独立 JAR/);
  assert.equal(hrefs(presentation(release).html).length, 8);
});
test('草稿、未公开、测试版不进入正式下载', () => {
  for (const changes of [{ draft: true }, { published_at: null }, { prerelease: true }]) {
    assert.equal(packageAssets({ ...synthetic(), ...changes }).length, 0);
  }
});
test('未上传、空文件和诊断附件不作为安装包', () => {
  const release = synthetic();
  release.assets[0].state = 'new'; release.assets[1].size = 0;
  release.assets.push({ ...release.assets[2], name: 'diagnostic.zip' });
  assert.equal(packageAssets(release).length, 6);
});
test('文件名、游戏版本和真实 Release 必须匹配', () => {
  const release = synthetic();
  release.assets[0].name = release.assets[0].name.replace('99.0.0', '98.0.0');
  release.assets[1].name = release.assets[1].name.replace('1.21.11', '26.9');
  assert.equal(packageAssets(release).length, 6);
});
test('URL 不能跨仓库、跨 tag、跨游戏或附加跳转参数', () => {
  for (const mutate of [url => url.replace('/yiyiaddon/', '/other/'), url => url.replace('/v99.0.0/', '/v98.0.0/'),
    url => url.replace('1.21.11-win64.jar', '26.2-win64.jar'), url => url + '?redirect=other']) {
    const release = synthetic(); release.assets[0].browser_download_url = mutate(release.assets[0].browser_download_url);
    assert.throws(() => packageAssets(release), /不一致/);
  }
});
test('重复文件名与同平台大小写别名均拒绝', () => {
  const release = synthetic(); release.assets.push({ ...release.assets[0] });
  assert.throws(() => packageAssets(release), /重复/);
  release.assets.at(-1).name = release.assets.at(-1).name.replace('-win64.jar', '-WIN64.jar');
  release.assets.at(-1).browser_download_url = `${repository}/releases/download/${release.tag_name}/${release.assets.at(-1).name}`;
  assert.throws(() => packageAssets(release), /重复/);
});
test('ZIP 只保留无后缀历史 Windows 归档，不作为 Mac 或当前直接安装包', () => {
  const release = synthetic('99.0.0', ['']);
  for (const asset of release.assets) { asset.name = asset.name.replace('.jar', '.zip'); asset.browser_download_url = asset.browser_download_url.replace('.jar', '.zip'); }
  assert.equal(packageAssets(release).length, 0);
  assert.equal(packageAssets(release, { allowLegacyZip: true }).length, 4);
  assert.ok(releaseGamePairs(release, { allowLegacyZip: true }).every(pair => !pair.mac));
});
test('新版本缺 Mac 时不跨版本借用旧 desktop', () => {
  const latest = synthetic('99.0.0', ['win64']);
  const old = synthetic('98.0.0', ['desktop']); old.published_at = '2026-11-14T00:00:00Z';
  const selected = selectHomepageDownloads(latest, [old, latest]).downloads;
  assert.ok(selected.every(pair => pair.tag === latest.tag_name && pair.mac === null));
});
test('只有新 Mac 包时保留旧首页，不移除真实 Windows 入口', () => {
  assert.throws(() => selectHomepageDownloads(synthetic('99.0.0', ['mac64']), [real]), /最新正式版没有可核实 Windows/);
});
test('某游戏没有新版附件时保留该游戏真实旧版且不跨游戏取包', () => {
  const latest = synthetic(); latest.assets = latest.assets.filter(asset => !asset.name.includes('-26.3-'));
  const pairs = selectHomepageDownloads(latest, [real]).downloads;
  assert.equal(pairs.find(pair => pair.game === '26.3').version, '1.2');
  assert.ok(pairs.filter(pair => pair.game !== '26.3').every(pair => pair.version === '99.0.0'));
});
test('1.3 专用包门槛要求四个游戏版本各自的 Windows 与 Mac', () => {
  const release = synthetic('1.3'); assert.equal(requireDualPlatformRelease(release).length, 4);
  release.assets.pop(); assert.throws(() => requireDualPlatformRelease(release), /26.3.*独立 JAR/);
});
test('1.3 首页拒绝缺任一平台、旧版补位或 desktop 伪装完整八包', () => {
  const complete = synthetic('1.3');
  assert.equal(selectHomepageDownloads(complete, [real]).downloads.length, 4);
  for (const missing of complete.assets) {
    const incomplete = clone(complete); incomplete.assets = incomplete.assets.filter(asset => asset.id !== missing.id);
    assert.throws(() => selectHomepageDownloads(incomplete, [real]), /独立 JAR/);
  }
  for (const suffixes of [['desktop'], [''], ['mac64']]) {
    assert.throws(() => selectHomepageDownloads(synthetic('1.3', suffixes), [real]), /独立 JAR/);
  }
});
test('真实 1.2 展示四个 Windows 链接与四个无链接 Mac 状态', () => {
  const { html, generatedAssets } = presentation(real);
  assert.equal(hrefs(html).length, 4); assert.equal((html.match(/<td /g) || []).length, 4);
  assert.equal((html.match(/Mac x64 待发布，无下载链接/g) || []).length, 4);
  assert.ok([...generatedAssets.values()].some(svg => svg.includes('下载 Windows x64')));
  assert.ok([...generatedAssets.keys()].every(name => /^展示资源\/theme\/download-1\.2-(1\.21\.11|26\.1\.2|26\.2|26\.3)-(windows|mac)-(light|dark)\.svg$/.test(name)));
  assert.ok(html.includes('download-1.2-26.2-windows-light.svg'));
});
test('完整八包生成四组相邻原生双按钮，平台资源分别命名', () => {
  const release = synthetic(); const { html, generatedAssets, pairs } = presentation(release);
  const cells = [...html.matchAll(/<td\b[^>]*>([\s\S]*?)<\/td>/g)];
  assert.equal(cells.length, 4); assert.equal(generatedAssets.size, 16);
  cells.forEach((cell, index) => assert.deepEqual(hrefs(cell[1]), [pairs[index].windows.browser_download_url, pairs[index].mac.browser_download_url]));
  assert.ok([...generatedAssets.keys()].some(name => name.includes('-mac-')));
  assert.ok([...generatedAssets.values()].some(svg => svg.includes('下载 Mac x64')));
  assert.ok([...generatedAssets.keys()].every(name => name.startsWith(`展示资源/theme/download-${release.tag_name.slice(1)}-`)));
});
test('模板拒绝串游戏、串发布和交换平台对象', () => {
  const pairs = releaseGamePairs(synthetic());
  for (const mutate of [items => { items[0].mac = items[1].mac; }, items => { items[0].mac.tag = 'v98.0.0'; },
    items => { items[0].windows = items[0].mac; }]) {
    const copy = clone(pairs); mutate(copy);
    assert.throws(() => renderPlatformDownloadGrid(copy, { root, generatedAssets: new Map() }), /同版真实附件/);
  }
});
test('历史归档识别八个专用包并标出 Windows x64 与 Mac x64', () => {
  const latest = synthetic(); const result = buildReleaseHistory({ catalog: [latest, real], latest, repository, root });
  assert.match(result.markdown, /JAR × 8/);
  assert.match(result.markdown, /Windows x64/); assert.match(result.markdown, /Mac x64（ARM64）/);
  for (const asset of latest.assets) assert.ok(result.markdown.includes(asset.browser_download_url));
});
test('新的正式 Release 模板生成八个按钮、去重游戏版本并保持原记录', () => {
  const live = synthetic('1.3'); const assets = packageAssets(live); const generatedAssets = new Map();
  const release = { tag: live.tag_name, prerelease: false, releaseUrl: live.html_url, summary: '本地模板验证',
    originalIntro: '原始封面说明', coverImageBlocks: [], downloadSegment: live.body,
    highlightsSegment: '## 亮点\n\n原亮点\n', detailsSegment: '## 详细记录\n\n原完整记录\n', originalFeedback: '原反馈说明\n' };
  const result = composeRelease(release, assets, { root, release: true, generatedAssets, latestTag: live.tag_name,
    catalogRelease: live, assetPrefix: `${repository}/raw/fixture/`, docPrefix: `${repository}/blob/master/` });
  assert.match(result.body, /yiyiaddon 1.3 · 正式版/); assert.doesNotMatch(result.body, /1.3 · 历史正式版/);
  assert.ok(result.body.includes('Minecraft **1.21.11 / 26.1.2 / 26.2 / 26.3**'));
  assert.equal(hrefs(result.body).filter(url => url.includes('/releases/download/')).length, 8);
  assert.ok([...generatedAssets.keys()].every(name => /release-1\.3-.+-(windows|mac)-(light|dark)\.svg$/.test(name)));
  for (const value of [release.highlightsSegment, release.detailsSegment, release.originalFeedback]) assert.ok(result.body.includes(value));
  for (const suffixes of [['desktop'], [''], ['mac64']]) {
    const incomplete = synthetic('1.3', suffixes);
    assert.throws(() => composeRelease(release, packageAssets(incomplete), { root, generatedAssets: new Map(),
      catalogRelease: incomplete }), /独立 JAR/);
  }
});
test('完整 CLI 只在隔离目录生成，当前快照与模拟八包均可重复运行', () => {
  const temp = fs.mkdtempSync(path.join(os.tmpdir(), 'yiyi-dual-download-'));
  try {
    fs.cpSync(root, temp, { recursive: true, filter: src => !src.includes(`${path.sep}.git${path.sep}`)
      && (fs.statSync(src).isDirectory() || /\.(mjs|cjs|md|svg|json)$/.test(src)) });
    const run = latest => {
      fs.writeFileSync(path.join(temp, 'latest.json'), JSON.stringify(latest));
      fs.writeFileSync(path.join(temp, 'catalog.json'), JSON.stringify([latest, real]));
      return JSON.parse(execFileSync(process.execPath, [path.join(temp, '工具/更新公开首页.mjs'), '--root', temp,
        '--latest', path.join(temp, 'latest.json'), '--catalog', path.join(temp, 'catalog.json')], { encoding: 'utf8', stdio: ['ignore', 'pipe', 'pipe'] }));
    };
    assert.equal(run(real).downloads.length, 4);
    const current = fs.readFileSync(path.join(temp, 'README.md'), 'utf8');
    assert.equal(hrefs(current).filter(url => /\/releases\/download\//.test(url)).length, 8); // README 两主题各一入口。
    assert.ok(current.includes('Mac x64 待发布，无下载链接'));
    const incomplete = synthetic('1.3'); incomplete.assets.shift();
    assert.throws(() => run(incomplete), /独立 JAR/);
    assert.equal(fs.readFileSync(path.join(temp, 'README.md'), 'utf8'), current);
    const latest = synthetic(); assert.equal(run(latest).downloads.length, 4);
    const paired = fs.readFileSync(path.join(temp, 'README.md'), 'utf8');
    assert.equal(hrefs(paired).filter(url => /\/releases\/download\//.test(url)).length, 16);
    assert.ok(paired.includes('下载 Mac x64')); run(latest);
    assert.equal(fs.readFileSync(path.join(temp, 'README.md'), 'utf8'), paired);
  } finally { fs.rmSync(temp, { recursive: true, force: true }); }
});

test('1.3 正式正文复用既定封面和 18 组 146 条，逐附件列出实际摘要', async () => {
  const { compose13FormalPage } = await import('./生成1.3正式页面.mjs');
  const original = fs.readFileSync(path.join(root, fs.existsSync(path.join(root, '发布说明/1.3.md')) ? '发布说明/1.3.md' : '发布准备/1.3.md'), 'utf8');
  const release = synthetic('1.3');
  release.assets.forEach((asset, index) => { asset.digest = `sha256:${String(index).repeat(64)}`; });
  const result = compose13FormalPage(original, release, { root, assetRef: 'PUBLIC_ASSET_REF' });
  assert.equal(result.modules, 18); assert.equal(result.entries, 146); assert.equal(result.generatedAssets.size, 16);
  assert.equal((result.body.match(/mapart-kobe-clean-1536.webp/g) || []).length, 1);
  assert.equal((result.body.match(/<!-- yiyiaddon:update-summary/g) || []).length, 1);
  assert.match(result.body, /1.3 · 正式版/);
  assert.doesNotMatch(result.body, /安装包待上传|安装包尚未上传|当前正式下载仍为 1\.2|prepare=1\.3|计划按每个 Minecraft/);
  for (const asset of release.assets) assert.ok(result.body.includes(asset.digest.slice(7)) && result.body.includes(asset.browser_download_url));
  assert.equal(compose13FormalPage(original, release, { root, assetRef: 'PUBLIC_ASSET_REF' }).body, result.body);
});
test('已上传八包的 1.3 草稿仅在显式本地预览模式下生成，保留真实发布状态', async () => {
  const { compose13FormalPage } = await import('./生成1.3正式页面.mjs');
  const original = fs.readFileSync(path.join(root, fs.existsSync(path.join(root, '发布说明/1.3.md')) ? '发布说明/1.3.md' : '发布准备/1.3.md'), 'utf8');
  const release = synthetic('1.3'); release.draft = true; release.published_at = null;
  release.assets.forEach(asset => { asset.digest = `sha256:${'a'.repeat(64)}`; });
  assert.throws(() => compose13FormalPage(original, release, { root, assetRef: 'PUBLIC_ASSET_REF' }), /真实已公开/);
  const result = compose13FormalPage(original, release, { root, assetRef: 'PUBLIC_ASSET_REF', draftPreview: true });
  assert.match(result.body, /正式发布稿（草稿）/);
  assert.equal(release.draft, true); assert.equal(release.published_at, null);
  assert.equal(packageAssets(release).length, 0);
  assert.throws(() => selectHomepageDownloads(release, [real]), /真实已公开/);
});
test('1.3 真实八包门槛拒绝额外附件，正式正文拒绝无摘要文件', async () => {
  const { compose13FormalPage } = await import('./生成1.3正式页面.mjs');
  const release = synthetic('1.3'); release.assets.push({ name: 'diagnostic.zip' });
  assert.throws(() => requireDualPlatformRelease(release), /恰好/);
  release.assets.pop();
  const original = fs.readFileSync(path.join(root, fs.existsSync(path.join(root, '发布说明/1.3.md')) ? '发布说明/1.3.md' : '发布准备/1.3.md'), 'utf8');
  assert.throws(() => compose13FormalPage(original, release, { root, assetRef: 'PUBLIC_ASSET_REF' }), /SHA-256/);
});
test('正式 CLI 暂存指南与主题页，正式源可重复生成且准备工具拒绝覆盖', () => {
  const temp = fs.mkdtempSync(path.join(os.tmpdir(), 'yiyi-formal-13-'));
  try {
    const stagedRoot = path.join(temp, 'public');
    fs.cpSync(root, stagedRoot, { recursive: true, filter: src => !src.includes(`${path.sep}.git${path.sep}`)
      && (fs.statSync(src).isDirectory() || /\.(mjs|cjs|md|svg|json)$/.test(src)) });
    const live = synthetic('1.3'); live.assets.forEach(asset => { asset.digest = `sha256:${'b'.repeat(64)}`; });
    const snapshot = path.join(temp, 'snapshot.json'); fs.writeFileSync(snapshot, JSON.stringify(live));
    const run = output => JSON.parse(execFileSync(process.execPath, [path.join(stagedRoot, '工具/生成1.3正式页面.mjs'),
      '--root', stagedRoot, '--release', snapshot, '--output-dir', output, '--asset-ref', 'PUBLIC_ASSET_REF'],
      { encoding: 'utf8', stdio: ['ignore', 'pipe', 'pipe'] }));
    const output = path.join(temp, 'output'); assert.equal(run(output).mode, 'published');
    for (const file of ['安装指南.md', '版本说明.md', '功能指南.md']) {
      assert.doesNotMatch(fs.readFileSync(path.join(output, file), 'utf8'), /待上传|尚未上传|1\.3 计划/);
    }
    for (const file of ['安装指南.md', '版本说明.md', '功能指南.md', '发布准备/1.3.md', '发布说明/1.3.md']) {
      const dest = path.join(stagedRoot, file); fs.mkdirSync(path.dirname(dest), { recursive: true });
      fs.copyFileSync(path.join(output, file), dest);
    }
    const next = path.join(temp, 'next'); run(next);
    assert.equal(fs.readFileSync(path.join(next, 'release-body.md'), 'utf8'), fs.readFileSync(path.join(output, 'release-body.md'), 'utf8'));
    assert.throws(() => execFileSync(process.execPath, [path.join(stagedRoot, '工具/生成1.3准备按钮.mjs'), '--root', stagedRoot],
      { stdio: ['ignore', 'pipe', 'pipe'] }), /不能覆盖/);
  } finally { fs.rmSync(temp, { recursive: true, force: true }); }
});
