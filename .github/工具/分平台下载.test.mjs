import test from 'node:test';
import assert from 'node:assert/strict';
import fs from 'node:fs';
import path from 'node:path';
import os from 'node:os';
import { fileURLToPath } from 'node:url';
import { execFileSync } from 'node:child_process';
import { games, repository, packageAssets, releaseGamePairs, selectHomepageDownloads, requireDualPlatformRelease, fabricDependency, assetDownloadUrl } from './发行附件.mjs';
import { renderPlatformDownloadGrid } from './分平台下载组件.mjs';
import { buildReleaseHistory } from './生成历史版本.mjs';
import { composeRelease } from './整理发布页面.mjs';
import { flatBlockMapArtImage } from './发布页面组件.mjs';

const carpetMapArtImage = 'https://www.yiyiaddon.asia/assets/mapart-kobe-clean-1536.webp';
const root = fileURLToPath(new URL('../../', import.meta.url));
const real = JSON.parse(fs.readFileSync(new URL('./测试数据/1.2公开附件.json', import.meta.url), 'utf8'));
const clone = value => JSON.parse(JSON.stringify(value));
// 合成版本只在内存与测试临时目录使用；不访问网络，不写回仓库 README，不发布 Release。
function synthetic(version = '99.0.0', suffixes = ['win64', 'mac64']) {
  const release = { ...clone(real), id: 990000, tag_name: `v${version}`, name: `yiyiaddon ${version}｜本地测试`,
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
test('历史兼容系列缺 Mac 时不跨版本借用旧 desktop', () => {
  const latest = synthetic('1.2.1', ['win64']);
  const old = synthetic('1.2', ['desktop']); old.published_at = '2026-11-14T00:00:00Z';
  const selected = selectHomepageDownloads(latest, [old, latest]).downloads;
  assert.ok(selected.every(pair => pair.tag === latest.tag_name && pair.mac === null));
});
test('历史兼容系列只有 Mac 包时保留旧首页，不移除真实 Windows 入口', () => {
  assert.throws(() => selectHomepageDownloads(synthetic('1.2.1', ['mac64']), [real]), /最新正式版没有可核实 Windows/);
});
test('历史兼容系列某游戏没有新附件时保留该游戏真实旧版且不跨游戏取包', () => {
  const latest = synthetic('1.2.1'); latest.assets = latest.assets.filter(asset => !asset.name.includes('-26.3-'));
  const pairs = selectHomepageDownloads(latest, [real]).downloads;
  assert.equal(pairs.find(pair => pair.game === '26.3').version, '1.2');
  assert.ok(pairs.filter(pair => pair.game !== '26.3').every(pair => pair.version === '1.2.1'));
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
test('历史归档保留旧版说明并为已归档安装包指向当前正式版', () => {
  const latest = synthetic();
  const old = { ...real, assets: [], body: `本版安装附件已归档。请前往[当前正式版](${repository}/releases/latest)下载。\n\n${real.body}` };
  const { markdown } = buildReleaseHistory({ catalog: [latest, old], latest, repository, root });
  const oldRow = markdown.split('\n').find(line => line.includes('/releases/tag/v1.2'));
  assert.ok(oldRow.includes(`**安装附件已归档** · [下载当前正式版](${repository}/releases/latest)`));
  assert.doesNotMatch(oldRow, /\/releases\/download\/v1\.2\//);
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
    // 旧准备区只在隔离夹具中补入，验证后续版本不会重新生成1.3待发布状态。
    fs.appendFileSync(path.join(temp, 'README.md'), '\n<!-- homepage:mapart-status:start -->\n合成准备状态\n<!-- homepage:mapart-status:end -->\n');
    const run = latest => {
      fs.writeFileSync(path.join(temp, 'latest.json'), JSON.stringify(latest));
      fs.writeFileSync(path.join(temp, 'catalog.json'), JSON.stringify([latest, real]));
      return JSON.parse(execFileSync(process.execPath, [path.join(temp, '.github/工具/更新公开首页.mjs'), '--root', temp,
        '--latest', path.join(temp, 'latest.json'), '--catalog', path.join(temp, 'catalog.json')], { encoding: 'utf8', stdio: ['ignore', 'pipe', 'pipe'] }));
    };
    assert.equal(run(real).downloads.length, 4);
    const current = fs.readFileSync(path.join(temp, 'README.md'), 'utf8');
    assert.equal(hrefs(current).filter(url => /\/releases\/download\//.test(url)).length, 8); // README 两主题各一入口。
    assert.ok(current.includes('Mac x64 待发布，无下载链接'));
    const incomplete = synthetic('1.3'); incomplete.assets.shift();
    assert.throws(() => run(incomplete), /独立 JAR/);
    assert.equal(fs.readFileSync(path.join(temp, 'README.md'), 'utf8'), current);
    const latest = synthetic('1.3.1'); assert.equal(run(latest).downloads.length, 4);
    const paired = fs.readFileSync(path.join(temp, 'README.md'), 'utf8');
    assert.equal(hrefs(paired).filter(url => /\/releases\/download\//.test(url)).length, 16);
    assert.ok(paired.includes('下载 Mac x64'));
    assert.ok(paired.includes('全自动地图画已随 1.3 正式发布'));
    assert.doesNotMatch(paired, /功能已完成 正在准备 1\.3 发布|1\.3 独立包待上传/);
    const online = fs.readFileSync(path.join(root, 'README.md'), 'utf8').match(/<!-- homepage:online-count:start -->[\s\S]*?<!-- homepage:online-count:end -->/)[0];
    assert.ok(paired.includes(online), '保留原在线人数图片与两个主题入口');
    for (const theme of ['light', 'dark']) for (const compact of ['', '-compact']) {
      const brand = fs.readFileSync(path.join(temp, `展示资源/theme/brand${compact}-${theme}.svg`), 'utf8');
      const badges = brand.match(/<g id="platform-badges">([\s\S]*?)<\/g>\s*<path/)?.[1];
      assert.ok(badges);
      const badgeWidths = [...badges.matchAll(/<rect[^>]*width="(\d+)"/g)].map(match => Number(match[1]));
      assert.equal(badgeWidths.length, 2); assert.equal(badgeWidths[0], badgeWidths[1]);
      assert.doesNotMatch(badges, /可下载|待上传/);
      const environment = fs.readFileSync(path.join(temp, `展示资源/theme/panel-environment-platforms${compact}-${theme}.svg`), 'utf8');
      for (const platform of ['windows', 'mac']) {
        const labelY = environment.match(new RegExp(`id="platform-${platform}-label"[^>]*y="(\\d+)"`))?.[1];
        const statusY = environment.match(new RegExp(`id="platform-${platform}-status"[^>]*y="(\\d+)"`))?.[1];
        assert.ok(labelY); assert.equal(labelY, statusY);
        assert.doesNotMatch(environment.match(new RegExp(`<g id="platform-${platform}-row">([\\s\\S]*?)<\\/g>\\s*<\\/g>`))?.[1] || '', /<rect/);
      }
      assert.match(environment, /ARM64 Java · Intel Mac 暂不支持/);
    }
    run(latest);
    assert.equal(fs.readFileSync(path.join(temp, 'README.md'), 'utf8'), paired);
    assert.doesNotMatch(paired, /全自动地图画（地毯与平面方块）/);
    const version14 = synthetic('1.4');
    assert.equal(run(version14).downloads.length, 4);
    const latestReadme = fs.readFileSync(path.join(temp, 'README.md'), 'utf8');
    assert.match(latestReadme, /全自动地图画（地毯与平面方块）/);
    assert.doesNotMatch(latestReadme, /mapart-flat-blocks\.webp|mapart-kobe-clean-1536\.webp/);
    assert.equal(hrefs(latestReadme).filter(url => url.includes('/releases/download/v1.4/')).length, 16);
    for (const asset of version14.assets) {
      assert.equal(hrefs(latestReadme).filter(url => url.split('#')[0] === asset.browser_download_url).length, 2);
    }
    const badge = fs.readFileSync(path.join(temp, '展示资源/theme/brand-light.svg'), 'utf8');
    assert.match(badge, /yiyiaddon 1\.4/);
    run(version14);
    assert.equal(fs.readFileSync(path.join(temp, 'README.md'), 'utf8'), latestReadme);
    run(latest);
    assert.doesNotMatch(fs.readFileSync(path.join(temp, 'README.md'), 'utf8'), /全自动地图画（地毯与平面方块）/);
  } finally { fs.rmSync(temp, { recursive: true, force: true }); }
});

test('1.3 正式正文复用既定封面和 18 组 146 条，逐附件列出实际摘要', async () => {
  const { compose13FormalPage } = await import('./生成1.3正式页面.mjs');
  const original = fs.readFileSync(path.join(root, fs.existsSync(path.join(root, '发布说明/1.3.md')) ? '发布说明/1.3.md' : '发布说明/发布准备/1.3.md'), 'utf8');
  const release = synthetic('1.3');
  release.assets.forEach((asset, index) => { asset.digest = `sha256:${String(index).repeat(64)}`; });
  const result = compose13FormalPage(original, release, { root, assetRef: 'PUBLIC_ASSET_REF' });
  assert.equal(result.modules, 18); assert.equal(result.entries, 146); assert.equal(result.generatedAssets.size, 16);
  assert.equal((result.body.match(/mapart-kobe-clean-1536.webp/g) || []).length, 1);
  assert.equal((result.body.match(/<!-- yiyiaddon:update-summary/g) || []).length, 1);
  assert.ok(result.body.includes(`**${release.name}**`));
  assert.doesNotMatch(result.body, /安装包待上传|安装包尚未上传|当前正式下载仍为 1\.2|prepare=1\.3|计划按每个 Minecraft/);
  for (const asset of release.assets) assert.ok(result.body.includes(asset.digest.slice(7)) && result.body.includes(asset.browser_download_url));
  assert.equal(compose13FormalPage(original, release, { root, assetRef: 'PUBLIC_ASSET_REF' }).body, result.body);
});
test('已上传八包的 1.3 草稿仅在显式本地预览模式下生成，保留真实发布状态', async () => {
  const { compose13FormalPage } = await import('./生成1.3正式页面.mjs');
  const original = fs.readFileSync(path.join(root, fs.existsSync(path.join(root, '发布说明/1.3.md')) ? '发布说明/1.3.md' : '发布说明/发布准备/1.3.md'), 'utf8');
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
  const original = fs.readFileSync(path.join(root, fs.existsSync(path.join(root, '发布说明/1.3.md')) ? '发布说明/1.3.md' : '发布说明/发布准备/1.3.md'), 'utf8');
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
    const run = output => JSON.parse(execFileSync(process.execPath, [path.join(stagedRoot, '.github/工具/生成1.3正式页面.mjs'),
      '--root', stagedRoot, '--release', snapshot, '--output-dir', output, '--asset-ref', 'PUBLIC_ASSET_REF'],
      { encoding: 'utf8', stdio: ['ignore', 'pipe', 'pipe'] }));
    const output = path.join(temp, 'output'); assert.equal(run(output).mode, 'published');
    for (const file of ['安装指南.md', '版本说明.md', '功能指南.md']) {
      assert.doesNotMatch(fs.readFileSync(path.join(output, file), 'utf8'), /待上传|尚未上传|1\.3 计划/);
    }
    for (const file of ['安装指南.md', '版本说明.md', '功能指南.md', '发布说明/发布准备/1.3.md', '发布说明/1.3.md']) {
      const dest = path.join(stagedRoot, file); fs.mkdirSync(path.dirname(dest), { recursive: true });
      fs.copyFileSync(path.join(output, file), dest);
    }
    const next = path.join(temp, 'next'); run(next);
    assert.equal(fs.readFileSync(path.join(next, 'release-body.md'), 'utf8'), fs.readFileSync(path.join(output, 'release-body.md'), 'utf8'));
    assert.throws(() => execFileSync(process.execPath, [path.join(stagedRoot, '.github/工具/生成1.3准备按钮.mjs'), '--root', stagedRoot],
      { stdio: ['ignore', 'pipe', 'pipe'] }), /不能覆盖/);
  } finally { fs.rmSync(temp, { recursive: true, force: true }); }
});
test('原始草稿八包可绑定真实资源提交后生成原子发布正文，拒绝占位或错图', () => {
  const temp = fs.mkdtempSync(path.join(os.tmpdir(), 'yiyi-publish-body-13-'));
  try {
    const stagedRoot = path.join(temp, 'public');
    fs.cpSync(root, stagedRoot, { recursive: true, filter: src => !src.includes(`${path.sep}.git${path.sep}`)
      && (fs.statSync(src).isDirectory() || /\.(mjs|cjs|md|svg|json)$/.test(src)) });
    const draft = synthetic('1.3'); draft.draft = true; draft.published_at = null;
    draft.html_url = `${repository}/releases/tag/untagged-1234567890abcdef1234`;
    draft.assets.forEach(asset => { asset.browser_download_url = asset.browser_download_url.replace('/v1.3/', '/untagged-1234567890abcdef1234/'); });
    draft.assets.forEach(asset => { asset.digest = `sha256:${'c'.repeat(64)}`; });
    const snapshot = path.join(temp, 'draft.json'); fs.writeFileSync(snapshot, JSON.stringify(draft));
    const originalSnapshot = fs.readFileSync(snapshot, 'utf8');
    const run = (output, assetRef, publication = false) => JSON.parse(execFileSync(process.execPath,
      [path.join(stagedRoot, '.github/工具/生成1.3正式页面.mjs'), '--root', stagedRoot, '--release', snapshot,
        '--output-dir', output, '--asset-ref', assetRef, '--draft-preview', ...(publication ? ['--publication-body'] : [])],
      { encoding: 'utf8', stdio: ['ignore', 'pipe', 'pipe'] }));
    const preview = path.join(temp, 'preview'); run(preview, 'PUBLIC_ASSET_REF');
    fs.cpSync(path.join(preview, '展示资源'), path.join(stagedRoot, '展示资源'), { recursive: true });
    const git = args => execFileSync('git', ['-C', stagedRoot, ...args], { encoding: 'utf8', stdio: ['ignore', 'pipe', 'pipe'] }).trim();
    git(['init', '--quiet']);
    const imageFiles = fs.readdirSync(path.join(preview, '展示资源/theme')).map(file => `展示资源/theme/${file}`);
    assert.equal(imageFiles.length, 16); git(['add', '--', ...imageFiles]);
    git(['-c', 'user.name=Local fixture', '-c', 'user.email=fixture@example.invalid', 'commit', '--quiet', '-m', 'local generated image fixture']);
    const assetRef = git(['rev-parse', 'HEAD']);
    const intended = path.join(temp, 'intended'); assert.equal(run(intended, assetRef, true).publicationBody, true);
    const publication = fs.readFileSync(path.join(intended, 'release-publication-body.md'), 'utf8');
    assert.ok(publication.includes(`**${draft.name}**`)); assert.doesNotMatch(publication, /正式发布稿（草稿）|PUBLIC_ASSET_REF/);
    assert.ok(publication.includes(assetRef));
    assert.doesNotMatch(publication, /untagged-/);
    assert.match(publication, /releases\/download\/v1\.3\//);
    assert.match(publication, /既有功能回归记录/);
    assert.match(fs.readFileSync(path.join(intended, 'release-body.md'), 'utf8'), /正式发布稿（草稿）/);
    assert.equal(fs.existsSync(path.join(intended, '安装指南.md')), false);
    assert.equal(fs.readFileSync(snapshot, 'utf8'), originalSnapshot);
    assert.throws(() => run(path.join(temp, 'placeholder'), 'PUBLIC_ASSET_REF', true), /确切 40 位/);
    const altered = path.join(stagedRoot, imageFiles[0]); fs.appendFileSync(altered, '\n<!-- inconsistent fixture -->');
    git(['add', '--', imageFiles[0]]);
    git(['-c', 'user.name=Local fixture', '-c', 'user.email=fixture@example.invalid', 'commit', '--quiet', '-m', 'local inconsistent fixture']);
    assert.throws(() => run(path.join(temp, 'wrong-image'), git(['rev-parse', 'HEAD']), true), /SVG 与当前真实附件生成结果不同/);
    assert.equal(fs.existsSync(path.join(temp, 'wrong-image')), false);
  } finally { fs.rmSync(temp, { recursive: true, force: true }); }
});
test('GitHub untagged 草稿必须匹配本 Release 实际页面 slug，公开 URL 判据保持精确', () => {
  const draft = synthetic('1.3'); draft.draft = true; draft.published_at = null;
  const slug = 'untagged-1234567890abcdef1234';
  draft.html_url = `${repository}/releases/tag/${slug}`;
  draft.assets.forEach(asset => { asset.browser_download_url = `${repository}/releases/download/${slug}/${asset.name}`; });
  const original = JSON.stringify(draft);
  const parsed = packageAssets(draft, { draftPreview: true });
  assert.equal(parsed.length, 8); assert.equal(requireDualPlatformRelease(draft, undefined, { draftPreview: true }).length, 4);
  for (const asset of parsed) {
    assert.ok(assetDownloadUrl(asset).includes(slug));
    assert.equal(assetDownloadUrl(asset, { publicationBody: true }), `${repository}/releases/download/v1.3/${asset.name}`);
  }
  assert.equal(JSON.stringify(draft), original); assert.equal(packageAssets(draft).length, 0);
  for (const mutate of [
    release => { release.html_url = release.html_url.replace('/yiyiaddon/', '/other/'); },
    release => { release.html_url += '?redirect=other'; },
    release => { release.assets[0].browser_download_url = release.assets[0].browser_download_url.replace(slug, 'untagged-00000000000000000000'); },
    release => { release.assets[0].browser_download_url += '?redirect=other'; },
    release => { release.assets[0].browser_download_url = release.assets[0].browser_download_url.replace('/yiyiaddon/', '/other/'); },
    release => { release.draft = false; release.published_at = '2026-11-16T00:00:00Z'; },
  ]) {
    const changed = clone(draft); mutate(changed);
    assert.throws(() => packageAssets(changed, { draftPreview: true }), /不一致/);
  }
  const incomplete = clone(draft); incomplete.assets.pop();
  assert.throws(() => requireDualPlatformRelease(incomplete, undefined, { draftPreview: true }), /独立 JAR/);
});

function patchSource(version = '1.3.1') {
  const original = fs.readFileSync(path.join(root, '发布说明/1.3.md'), 'utf8');
  const cover = original.match(/^(<p align="center">[\s\S]*?<\/p>)/)[1];
  const marker = (name, text) => `<!-- yiyiaddon:release-${name}:start -->\n${text}\n<!-- yiyiaddon:release-${name}:end -->`;
  const dependencies = '| 游戏版本 | Java | Fabric API |\n| --- | --- | --- |\n'
    + games.map(game => `| ${game} | ${game === '1.21.11' ? 21 : 25} | ${fabricDependency(original, game)} |`).join('\n');
  return [cover, '## 本次亮点\n\n本段仅为隔离生成器夹具，不表示真实发布。',
    marker('notes', '## 详细更新记录\n\n<details>\n<summary><strong>合成测试分组</strong></summary>\n\n- **修复** 合成条目仅用于验证版本和附件隔离\n\n</details>'),
    marker('feedback', '仅隔离夹具。'), marker('install', '按实际游戏版本和系统选择本版独立 JAR。'),
    marker('compatibility', '## 兼容与运行\n\n本合成夹具没有执行游戏实机，不复用旧版29项断言。'), dependencies,
    `<!-- yiyiaddon:update-summary\n${JSON.stringify({ version, items: ['仅隔离生成器夹具'] })}\n-->`].join('\n\n');
}

test('1.4 使用独立方块封面与实际八包，发布图固定到资源提交', async () => {
  const { composeFormalPage } = await import('./生成1.3正式页面.mjs');
  const temp = fs.mkdtempSync(path.join(os.tmpdir(), 'yiyi-formal-14-'));
  try {
    for (const directory of ['theme', 'icons']) {
      const link = path.join(temp, '展示资源', directory);
      fs.mkdirSync(path.dirname(link), { recursive: true });
      fs.symlinkSync(path.join(root, '展示资源', directory), link);
    }
    const live = synthetic('1.4');
    live.assets.forEach(asset => { asset.digest = 'sha256:' + 'f'.repeat(64); });
    const assetRef = 'a'.repeat(40);
    const options = { root: temp, assetRef };
    const original = patchSource('1.4');
    const body = original.replace(carpetMapArtImage, '../../' + flatBlockMapArtImage);
    assert.throws(() => composeFormalPage(original, live, options), /本版平面方块地图画封面/);
    assert.throws(() => composeFormalPage(body, live, options), /缺少 1\.4 平面方块地图画封面/);
    const image = path.join(temp, flatBlockMapArtImage);
    fs.mkdirSync(path.dirname(image), { recursive: true });
    fs.writeFileSync(image, '仅测试封面文件引用');
    const release = composeFormalPage(body, live, options);
    assert.ok(release.body.includes('https://raw.githubusercontent.com/fxjcangku/yiyiaddon/' + assetRef + '/' + flatBlockMapArtImage));
    assert.ok(!release.body.includes(carpetMapArtImage));
    assert.equal(hrefs(release.body).filter(url => url.includes('/releases/download/v1.4/')).length, 8);
    assert.ok(live.assets.every(asset => release.body.includes(asset.digest.slice(7))));
    const repositoryPage = composeFormalPage(body, live, { ...options, release: false });
    assert.ok(repositoryPage.body.startsWith('<p align="center">'));
    assert.ok(repositoryPage.body.includes('src="../' + flatBlockMapArtImage + '"'));
    assert.ok(!repositoryPage.body.includes('../../' + flatBlockMapArtImage));
  } finally { fs.rmSync(temp, { recursive: true, force: true }); }
});

test('1.3.1 草稿和正式页使用本版正文及八包，不误用1.3历史或推定旧版实测', async () => {
  const { composeFormalPage } = await import('./生成1.3正式页面.mjs');
  const live = synthetic('1.3.1'); live.assets.forEach(asset => { asset.digest = `sha256:${'d'.repeat(64)}`; });
  live.body = ''; // 新草稿正文尚未提交时，卡片依赖从显式本版正文源提取。
  const original = patchSource(), historical = fs.readFileSync(path.join(root, '发布说明/1.3.md'), 'utf8');
  const result = composeFormalPage(original, live, { root, assetRef: 'PUBLIC_ASSET_REF' });
  assert.equal(result.modules, 1); assert.equal(result.entries, 1); assert.equal(result.generatedAssets.size, 16);
  assert.match(result.body, /yiyiaddon 1\.3\.1｜本地测试/);
  assert.match(result.body, /"version": "1\.3\.1"/);
  assert.ok([...result.generatedAssets.keys()].every(file => file.includes('release-1.3.1-')));
  for (const game of games) {
    const cards = [...result.generatedAssets.entries()].filter(([file]) => file.includes('-' + game + '-windows-'));
    assert.equal(cards.length, 2);
    assert.ok(cards.every(([, svg]) => svg.includes(fabricDependency(original, game))));
  }
  assert.equal((result.body.match(/mapart-kobe-clean-1536.webp/g) || []).length, 1);
  assert.doesNotMatch(result.body, /实际通过 29 项|原 18 组|修复版/);
  for (const asset of live.assets) assert.ok(result.body.includes(asset.browser_download_url) && result.body.includes(asset.digest.slice(7)));
  assert.throws(() => composeFormalPage(patchSource('1.3'), live, { root, assetRef: 'PUBLIC_ASSET_REF' }), /摘要版本/);
  for (const index of [0, 1, 6, 7]) {
    const incomplete = clone(live); incomplete.assets.splice(index, 1);
    assert.throws(() => selectHomepageDownloads(incomplete, [synthetic('1.3')]), /独立 JAR/);
  }
  const extra = clone(live); extra.assets.push({ name: 'extra.zip' });
  assert.throws(() => requireDualPlatformRelease(extra), /恰好/);
  const draft = clone(live); draft.draft = true; draft.published_at = null;
  const slug = 'untagged-1234567890abcdef1234'; draft.html_url = `${repository}/releases/tag/${slug}`;
  draft.assets.forEach(asset => { asset.browser_download_url = asset.browser_download_url.replace('/v1.3.1/', '/' + slug + '/'); });
  assert.equal(packageAssets(draft).length, 0);
  assert.throws(() => composeFormalPage(original, draft, { root, assetRef: 'PUBLIC_ASSET_REF' }), /真实已公开/);
  const preview = composeFormalPage(original, draft, { root, assetRef: 'PUBLIC_ASSET_REF', draftPreview: true });
  assert.match(preview.body, /正式发布稿（草稿）/);
  assert.ok(preview.body.includes('/' + slug + '/'));
  const publication = composeFormalPage(original, draft, { root, assetRef: 'a'.repeat(40), draftPreview: true, publicationBody: true });
  assert.ok(publication.body.includes('/releases/download/v1.3.1/'));
  assert.doesNotMatch(publication.body, /\/releases\/download\/v1\.3\//);
  assert.equal(draft.draft, true); assert.equal(draft.published_at, null);
  assert.equal(fs.readFileSync(path.join(root, '发布说明/1.3.md'), 'utf8'), historical);
});

test('1.3.1 CLI必须显式本版源，只在仓外暂存新正式页与当前指南，保留旧发布文件', () => {
  const temp = fs.mkdtempSync(path.join(os.tmpdir(), 'yiyi-formal-patch-'));
  try {
    const stagedRoot = path.join(temp, 'public');
    fs.cpSync(root, stagedRoot, { recursive: true, filter: src => !src.includes(`${path.sep}.git${path.sep}`)
      && (fs.statSync(src).isDirectory() || /\.(mjs|cjs|md|svg|json)$/.test(src)) });
    // 模拟尚未准备 1.3.1 正文的工作树，不依赖当前实际发布状态。
    for (const file of ['发布说明/1.3.1.md', '发布说明/发布准备/1.3.1.md']) {
      fs.rmSync(path.join(stagedRoot, file), { force: true });
    }
    const historical = fs.readFileSync(path.join(stagedRoot, '发布说明/1.3.md'), 'utf8');
    const live = synthetic('1.3.1'); live.assets.forEach(asset => { asset.digest = `sha256:${'e'.repeat(64)}`; });
    const snapshot = path.join(temp, 'snapshot.json'), source = path.join(temp, 'patch.md');
    fs.writeFileSync(snapshot, JSON.stringify(live)); fs.writeFileSync(source, patchSource());
    const run = (output, explicit = true) => JSON.parse(execFileSync(process.execPath,
      [path.join(stagedRoot, '.github/工具/生成1.3正式页面.mjs'), '--root', stagedRoot, '--release', snapshot,
        '--output-dir', output, '--asset-ref', 'PUBLIC_ASSET_REF', ...(explicit ? ['--source', source] : [])],
      { encoding: 'utf8', stdio: ['ignore', 'pipe', 'pipe'] }));
    assert.throws(() => run(path.join(temp, 'missing'), false), /需要本版正文源/);
    assert.equal(fs.existsSync(path.join(temp, 'missing')), false);
    const output = path.join(temp, 'output'); assert.equal(run(output).mode, 'published');
    assert.ok(fs.existsSync(path.join(output, '发布说明/1.3.1.md')));
    assert.equal(fs.existsSync(path.join(output, '发布说明/1.3.md')), false);
    assert.match(fs.readFileSync(path.join(output, '安装指南.md'), 'utf8'), /1\.3\.1 正式版提供八个/);
    assert.match(fs.readFileSync(path.join(output, '版本说明.md'), 'utf8'), /1\.3\.1 已正式发布/);
    assert.equal(fs.readFileSync(path.join(stagedRoot, '发布说明/1.3.md'), 'utf8'), historical);
    assert.equal(fs.existsSync(path.join(stagedRoot, '发布说明/1.3.1.md')), false);
    assert.throws(() => run(stagedRoot), /公开工作树之外/);
  } finally { fs.rmSync(temp, { recursive: true, force: true }); }
});
