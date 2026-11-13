import { heading, navRow } from './发布页面组件.mjs';

/** 从 GitHub Release API 的真实分类、发布时间、正文兼容表和附件清单生成归档。 */
export function buildReleaseHistory({ catalog, latest, repository, root }) {
  const byTag = new Map();
  for (const release of [...catalog.flat(), latest]) {
    if (!release || release.draft) continue;
    if (!release.tag_name || typeof release.prerelease !== 'boolean') {
      throw new Error('历史版本缺少标签或正式／测试分类，停止猜测');
    }
    byTag.set(release.tag_name, release);
  }
  const releases = [...byTag.values()].sort((a, b) => Date.parse(b.published_at) - Date.parse(a.published_at)
    || a.tag_name.localeCompare(b.tag_name, 'en', { numeric: true }));
  const stable = releases.filter(release => !release.prerelease);
  const beta = releases.filter(release => release.prerelease);
  const html = value => String(value).replaceAll('&', '&amp;').replaceAll('<', '&lt;').replaceAll('>', '&gt;').replaceAll('"', '&quot;').replaceAll('|', '&#124;');
  const label = value => String(value).replace(/[\\[\]|]/g, '\\$&');
  const releaseUrl = release => `${repository}/releases/tag/${encodeURIComponent(release.tag_name)}`;
  const dateFormat = new Intl.DateTimeFormat('en-CA', {
    timeZone: 'Asia/Shanghai', year: 'numeric', month: '2-digit', day: '2-digit',
    hour: '2-digit', minute: '2-digit', hourCycle: 'h23',
  });
  const publishedAt = release => {
    if (!release.published_at || !Number.isFinite(Date.parse(release.published_at))) return '未提供';
    const parts = Object.fromEntries(dateFormat.formatToParts(new Date(release.published_at)).map(part => [part.type, part.value]));
    return `${parts.year}-${parts.month}-${parts.day} ${parts.hour}:${parts.minute}`;
  };
  // 仅提取兼容表的版本列，不把正文中的“暂无下载／测试中”版本算进支持范围。
  const declaredGames = body => {
    const clean = value => value.trim().replaceAll('**', '').replaceAll('__', '').replaceAll('`', '');
    const cells = line => line.includes('|') ? line.trim().replace(/^\|/, '').replace(/\|$/, '').split('|').map(clean) : null;
    const lines = String(body || '').split('\n');
    const result = new Set();
    for (let at = 0; at < lines.length - 1; at++) {
      const headers = cells(lines[at]);
      if (!headers) continue;
      const gameColumn = headers.findIndex(value => /^(?:Minecraft(?:\s*版本)?|游戏版本|版本)$/i.test(value));
      const separator = cells(lines[at + 1]);
      if (gameColumn < 0 || !headers.some(value => /Fabric\s+API|安装包|下载/i.test(value))
          || !separator || separator.length !== headers.length || !separator.every(value => /^:?-{3,}:?$/.test(value))) continue;
      for (let rowAt = at + 2; rowAt < lines.length; rowAt++) {
        const row = cells(lines[rowAt]);
        if (!row) break;
        if (/^\d+(?:\.\d+)+$/.test(row[gameColumn] || '')) result.add(row[gameColumn]);
      }
    }
    return [...result].sort((a, b) => a.localeCompare(b, 'en', { numeric: true }));
  };
  const packageAssets = release => (release.assets || []).filter(asset => /\.(?:jar|zip)$/i.test(asset.name)
    && asset.size > 0 && asset.state === 'uploaded').flatMap(asset => {
      const prefix = `yiyiaddon-${release.tag_name.replace(/^v/, '')}-`;
      const suffix = asset.name.startsWith(prefix) ? asset.name.slice(prefix.length) : '';
      const match = suffix.match(/^(\d+(?:\.\d+)+)(?:-(desktop))?\.(jar|zip)$/i);
      if (!match || (match[2] && match[3].toLowerCase() !== 'jar')) return []; // 日志或诊断 ZIP 不冒充按游戏版本分包的安装附件。
      const game = match[1];
      const plain = `${repository}/releases/download/${release.tag_name}/${asset.name}`;
      const encoded = `${repository}/releases/download/${encodeURIComponent(release.tag_name)}/${encodeURIComponent(asset.name)}`;
      if (asset.browser_download_url !== plain && asset.browser_download_url !== encoded) throw new Error(`历史附件链接不属于本仓库：${asset.name}`);
      return [{ ...asset, game, target: match[2] ? 'desktop' : 'windows-x64', format: match[3].toUpperCase() }];
    });
  const row = release => {
    const assets = packageAssets(release);
    const declared = declaredGames(release.body);
    const games = declared.length ? declared.join(' / ') : assets.some(asset => asset.game)
      ? `附件标注：${[...new Set(assets.map(asset => asset.game).filter(Boolean))].sort((a, b) => a.localeCompare(b, 'en', { numeric: true })).join(' / ')}` : '原说明未明确声明';
    const summary = String(release.name || '').split(/[｜|]/).slice(1).join(' · ').trim()
      .replace(/^(?:正式版|测试版)(?:\s*[·•—-]\s*|\s*$)/, '');
    const current = release.tag_name === latest.tag_name ? ' · 当前推荐' : '';
    const title = `[**${label(release.tag_name.replace(/^v/, ''))}${current}**](${releaseUrl(release)})`
      + (summary ? `<br><sub>${html(summary)}</sub>` : '');
    let attachment = '无附件 · **仅更新记录**';
    if (assets.length) {
      attachment = ['JAR', 'ZIP'].filter(format => assets.some(asset => asset.format === format)).map(format => {
        const group = assets.filter(asset => asset.format === format).sort((a, b) => (a.game || a.name).localeCompare(b.game || b.name, 'en', { numeric: true }));
        return `**${format} × ${group.length}**${format === 'ZIP' ? '（需解压）' : ''}<br>`
          + group.map(asset => `[${label(asset.game + (asset.target === 'desktop' ? ' · Windows / Mac' : ''))}](${asset.browser_download_url})`).join(' · ');
      }).join('<br>');
    } else if ((release.assets || []).length) {
      attachment = `无可用安装包 · **仅更新记录**（[查看 ${release.assets.length} 个附件](${releaseUrl(release)})）`;
    }
    return `| ${title} | ${publishedAt(release)} | ${games} | ${attachment} |`;
  };
  // GitHub 主题 CSS 匹配 href 后缀；别名让主题按钮与段落定位同时生效。
  const anchors = name => [`<a name="${name}"></a>`, ...['light', 'dark'].map(theme => `<a name="${name}#gh-${theme}-mode-only"></a>`)].join('\n');
  const table = list => list.length ? [
    '| 版本与更新记录 | 发布时间（上海） | Minecraft 范围 | 安装附件 |',
    '| --- | --- | --- | --- |',
    ...list.map(row),
  ].join('\n') : '暂无已发布版本。';
  const ui = { root, assetPrefix: "../", docPrefix: "../" };
  const markdown = `${heading(1, "history", "历史版本", ui)}\n\n` +
    `当前推荐 [**yiyiaddon ${label(latest.tag_name.replace(/^v/, ''))} 正式版**](${releaseUrl(latest)})。首次安装或日常更新，请优先使用当前正式版。\n\n` +
    `${navRow([{name:"stable",href:"#user-content-stable",label:"正式版本"},{name:"beta",href:"#user-content-beta",label:"测试版本"},{name:"download",href:`${repository}/releases/latest`,label:"当前正式版"}], ui)}\n\n` +
    `收录 **${stable.length} 个正式版** · **${beta.length} 个测试版**。时间按北京时间显示。下载仅列出当前仍可获取的真实 JAR 或 ZIP 附件。\n\n` +
    `<!-- 此页由 工具/更新公开首页.mjs 根据 GitHub Release API 自动生成；请在对应 Release 中维护分类、正文与附件。 -->\n\n` +
    `${anchors('stable')}\n\n${heading(2, "check-circle", "正式版本", ui)}\n\n` +
    `点击版本名查看更新记录，点击 Minecraft 版本下载安装包。ZIP 文件需要先解压。\n\n` +
    `${table(stable)}\n\n` +
    `${anchors('beta')}\n\n${heading(2, "versions", "测试版本", ui)}\n\n` +
    `**没有附件的测试版仅保留更新记录。** Minecraft 范围为当时发布说明的记录；日常使用推荐当前正式版。\n\n` +
    `${table(beta)}\n\n` +
    `[返回当前正式版 →](${repository}/releases/latest) &nbsp; · &nbsp; [查看 GitHub 全部发布 →](${repository}/releases)\n`;
  return { markdown, counts: { stable: stable.length, beta: beta.length } };
}
