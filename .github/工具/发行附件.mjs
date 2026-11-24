/** 公开发行的安装附件识别；不请求网络，不创建附件或下载地址。 */
export const repository = 'https://github.com/fxjcangku/yiyiaddon';
export const games = Object.freeze(['1.21.11', '26.1.2', '26.2', '26.3']);
export const platformNames = Object.freeze({ 'windows-x64': 'Windows x64', 'mac-arm64': 'Mac x64' });

export function isPublishedRelease(release, { includePrerelease = false } = {}) {
  return !!release && release.draft === false && (includePrerelease || release.prerelease === false)
    && /^v\d+(?:\.\d+)*(?:-[a-z\d][a-z\d.-]*)?$/i.test(release.tag_name || '')
    && typeof release.published_at === 'string' && Number.isFinite(Date.parse(release.published_at));
}

/** 1.3 起的正式系列均使用四版本八专用包，历史 1.2 及更早入口保留原语义。 */
export function usesSplitPackages(tag) {
  const match = /^v(\d+)\.(\d+)(?:\.(\d+))?$/.exec(tag || '');
  return !!match && (Number(match[1]) > 1 || Number(match[1]) === 1 && Number(match[2]) >= 3);
}

function isDraftPreview(release, draftPreview) {
  return draftPreview === true && usesSplitPackages(release?.tag_name) && release.draft === true
    && release.prerelease === false && release.published_at === null;
}

/** 默认只读取已公开附件；draftPreview 仅供八包上传后的本地正文预览。 */
export function packageAssets(release, { includePrerelease = false, allowLegacyZip = false, draftPreview = false } = {}) {
  if (!isPublishedRelease(release, { includePrerelease }) && !isDraftPreview(release, draftPreview)) return [];
  const draftSlug = isDraftPreview(release, draftPreview)
    ? String(release.html_url || '').match(/^https:\/\/github[.]com\/fxjcangku\/yiyiaddon\/releases\/tag\/(untagged-[a-f\d]+)$/i)?.[1] : null;
  const version = release.tag_name.slice(1);
  const prefix = `yiyiaddon-${version}-`;
  const seen = new Set();
  const slots = new Set();
  const result = [];
  for (const asset of release.assets || []) {
    if (asset.state !== 'uploaded' || !Number.isFinite(asset.size) || asset.size <= 0
        || typeof asset.name !== 'string' || !asset.name.startsWith(prefix)) continue;
    const match = asset.name.slice(prefix.length).match(/^(\d+(?:\.\d+)+)(?:-(win64|mac64|desktop))?\.(jar|zip)$/i);
    if (!match || !games.includes(match[1])) continue;
    const game = match[1], suffix = (match[2] || '').toLowerCase(), format = match[3].toUpperCase();
    if (format !== 'JAR' && (!allowLegacyZip || suffix)) continue;
    const plain = `${repository}/releases/download/${release.tag_name}/${asset.name}`;
    const encoded = `${repository}/releases/download/${encodeURIComponent(release.tag_name)}/${encodeURIComponent(asset.name)}`;
    const draftUrl = draftSlug ? `${repository}/releases/download/${draftSlug}/${asset.name}` : null;
    const encodedDraftUrl = draftSlug ? `${repository}/releases/download/${draftSlug}/${encodeURIComponent(asset.name)}` : null;
    if (asset.browser_download_url !== plain && asset.browser_download_url !== encoded
        && (!draftSlug || (asset.browser_download_url !== draftUrl && asset.browser_download_url !== encodedDraftUrl))) {
      throw new Error(`安装附件链接与仓库、版本或文件名不一致：${asset.name}`);
    }
    if (seen.has(asset.name)) throw new Error(`同名安装附件重复：${asset.name}`);
    const slot = `${game}/${suffix}/${format}`;
    if (slots.has(slot)) throw new Error(`同一平台附件命名重复：${asset.name}`);
    seen.add(asset.name);
    slots.add(slot);
    result.push({ ...asset, ...(draftSlug ? { publication_download_url: plain } : {}), game, minecraftVersion: game, version, tag: release.tag_name, suffix, format,
      packageKind: format.toLowerCase(), target: suffix === 'mac64' ? 'mac-arm64' : suffix === 'desktop' ? 'desktop' : 'windows-x64' });
  }
  return result;
}

/** 草稿仍保存 API 原 URL；仅本地正式发布请求正文使用同 tag 的预备目标。 */
export function assetDownloadUrl(asset, { publicationBody = false } = {}) {
  return publicationBody && asset.publication_download_url ? asset.publication_download_url : asset.browser_download_url;
}

export function choosePlatformPackage(assets, platform) {
  if (!Object.hasOwn(platformNames, platform)) throw new Error(`不支持的平台：${platform}`);
  const rank = asset => asset.suffix === (platform === 'windows-x64' ? 'win64' : 'mac64') ? 0
    : asset.suffix === 'desktop' ? 1 : asset.format === 'JAR' ? 2 : 3;
  return assets.filter(asset => asset.target === platform || asset.target === 'desktop')
    .sort((a, b) => rank(a) - rank(b) || a.name.localeCompare(b.name))[0] || null;
}

export function releaseGamePairs(release, options = {}) {
  const assets = packageAssets(release, options);
  return games.map(game => {
    const matching = assets.filter(asset => asset.game === game);
    const windows = choosePlatformPackage(matching, 'windows-x64');
    const mac = choosePlatformPackage(matching, 'mac-arm64');
    return windows || mac ? { game, version: release.tag_name.slice(1), tag: release.tag_name, windows, mac } : null;
  }).filter(Boolean);
}

/** 每游戏沿用最新可核实 Windows 版本，Mac 必须来自同一 Release，不能跨版拼成一对。 */
export function selectHomepageDownloads(latest, catalog) {
  if (!isPublishedRelease(latest)) throw new Error('Latest 必须是真实已公开正式版');
  // 新分包系列不能靠历史 Windows 回退掩盖漏包。
  if (usesSplitPackages(latest.tag_name)) requireDualPlatformRelease(latest);
  const stable = catalog.filter(item => isPublishedRelease(item) && item.tag_name !== latest.tag_name)
    .sort((a, b) => Date.parse(b.published_at) - Date.parse(a.published_at));
  const candidates = [latest, ...stable];
  const parsed = candidates.map(release => ({ release, pairs: releaseGamePairs(release) }));
  const downloads = games.map(game => {
    for (const { pairs } of parsed) {
      const pair = pairs.find(item => item.game === game && item.windows?.format === 'JAR');
      if (pair) return { ...pair, url: pair.windows.browser_download_url, target: pair.windows.target };
    }
    if (game === '1.21.11') return null;
    throw new Error(`未找到 Minecraft ${game} 的正式 Windows JAR，保留现有首页`);
  }).filter(Boolean);
  if (!downloads.some(item => item.tag === latest.tag_name)) throw new Error('最新正式版没有可核实 Windows 安装包，保留现有首页');
  return { candidates, downloads };
}

/** 分包发版门槛：四版本八个专用 JAR；历史 desktop 不计作两份独立构建。 */
export function requireDualPlatformRelease(release, requiredGames = games, options = {}) {
  if (!isPublishedRelease(release) && !isDraftPreview(release, options.draftPreview)) {
    throw new Error('双平台正式页必须使用真实已公开正式版；草稿只允许显式本地预览');
  }
  const pairs = releaseGamePairs(release, options);
  for (const game of requiredGames) {
    const pair = pairs.find(item => item.game === game);
    if (pair?.windows?.suffix !== 'win64' || pair?.mac?.suffix !== 'mac64'
        || pair.windows.format !== 'JAR' || pair.mac.format !== 'JAR') {
      throw new Error(`${release.tag_name} / ${game} 缺少同版 win64 与 mac64 独立 JAR`);
    }
  }
  if (release.assets.length !== requiredGames.length * 2) {
    throw new Error(`${release.tag_name} 发布附件必须恰好为四版本八个独立 JAR`);
  }
  return pairs.filter(pair => requiredGames.includes(pair.game));
}

export function fabricDependency(body, game) {
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

