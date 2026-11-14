/**
 * yiyiaddon 首页动态封面：Mac / Windows 平台信息与官网导向。
 * 基于原「06-发布页面/工具/制作动态封面.cjs」的场景、布局和时间函数。
 * 使用 SVG/Sharp 直接渲染动画图层；不修改原始场景 PNG 或历史 1.2 GIF。
 *
 * 安装依赖：npm install --no-save sharp
 * 完整生成：node 工具/制作Mac首页动态封面.cjs --render /tmp/yiyiaddon-cover
 * 代表帧检查：node 工具/制作Mac首页动态封面.cjs --preview /tmp/yiyiaddon-cover
 * 已有帧编码：node 工具/制作Mac首页动态封面.cjs --encode /tmp/yiyiaddon-cover
 * 命令末尾可加 compact 或 wide，仅重建指定布局。
 *
 * 两种布局各 36 个渲染帧，每帧 140 ms；末帧增加 1000 ms，共 6040 ms 无限循环。
 * GIF 编码器会合并相同的末帧，所以输出帧数可少于 36，播放时长保持不变。
 * Linux 推荐安装 Noto Sans CJK SC；Windows 可使用 Microsoft YaHei。
 */
const fs = require('node:fs');
const path = require('node:path');
const sharp = require('sharp');
const mode = process.argv[2] || '--render';
if (!['--preview', '--encode', '--render'].includes(mode)) throw new Error('请选择 --preview / --encode / --render');
const workDir = path.resolve(process.argv[3] || path.join(__dirname, '..', '.cover-render'));
const assets = path.resolve(__dirname, '..', '展示资源');
const scenePath = path.join(assets, 'beta3-原创3D模块全景.png');
const frameCount = 36;
const variantName = process.argv[4];
if (variantName && !['wide', 'compact'].includes(variantName)) throw new Error('布局请选择 wide / compact');
const variants = [
  { name: 'wide', width: 1120, height: 520, output: 'yiyiaddon-module-cover-mac.gif' },
  { name: 'compact', width: 640, height: 720, output: 'yiyiaddon-module-cover-compact-mac.gif' }
].filter(variant => !variantName || variant.name === variantName);
const versions = '1.21.11 / 26.1.2 / 26.2 / 26.3';
const cards = [
  ['farm', '星露谷农场', '识别作物 · 四季规划 · 自动照料'],
  ['fish', '自动钓鱼', '状态预测 · 收竿判定 · 背包兼容'],
  ['mine', '自动挖矿', '路径执行 · 矿物目标 · 战斗协同'],
  ['store', '农场后勤', '容器整理 · 补给循环 · 自动存取']
];
const clamp = value => Math.max(0, Math.min(1, value));
const fmt = value => Number(value.toFixed(5));
const esc = value => String(value).replaceAll('&', '&amp;').replaceAll('<', '&lt;').replaceAll('"', '&quot;');
const text = (x, y, copy, size, fill = '#eefaff', extra = '') => `<text x="${fmt(x)}" y="${fmt(y)}" font-size="${size}" fill="${fill}" ${extra}>${esc(copy)}</text>`;
const rect = (x, y, width, height, fill, extra = '') => `<rect x="${fmt(x)}" y="${fmt(y)}" width="${fmt(width)}" height="${fmt(height)}" fill="${fill}" ${extra}/>`;
const rounded = (x, y, width, height, fill, radius, extra = '') => rect(x, y, width, height, fill, `rx="${radius}" ${extra}`);
const windowsIcon = '<path fill="currentColor" d="M2 3h9v8H2zm11 0h9v8h-9zM2 13h9v8H2zm11 0h9v8h-9z"/>';
// 图标保留平台轮廓；所有可见平台名称使用 Mac。
const macIcon = '<path fill="currentColor" d="M17.1 12.6c0-2 1.6-3 1.7-3.1-1-1.5-2.5-1.7-3-1.8-1.3-.1-2.6.8-3.2.8-.7 0-1.7-.8-2.8-.8-1.5 0-2.9.9-3.6 2.2-1.5 2.6-.4 6.4 1.1 8.5.7 1 1.5 2.1 2.6 2 1-.1 1.4-.7 2.7-.7s1.6.7 2.8.7c1.2 0 1.9-1 2.6-2 .8-1.1 1.1-2.2 1.2-2.3-.1 0-2.1-.8-2.1-3.5zM15 6.3c.6-.8 1.1-1.8 1-2.8-1 .1-2.1.7-2.8 1.5-.6.7-1.2 1.8-1 2.8 1.1.1 2.1-.6 2.8-1.5z"/>';
const platformIcon = (kind, x, y, size = 18) => `<svg x="${x}" y="${y}" width="${size}" height="${size}" viewBox="0 0 24 24" color="#9cf6ff">${kind === 'win' ? windowsIcon : macIcon}</svg>`;

function platformPanel(x, y, width, compact, flat = false) {
  const height = compact ? 58 : (flat ? 66 : 76);
  const row = y + (compact ? 29 : 27);
  const font = compact ? 20 : (flat ? 13 : 14);
  const macX = x + (compact ? 249 : 174);
  return rounded(x, y, width, height, '#041119', 12, 'fill-opacity=".78" stroke="#8eedf6" stroke-opacity=".30"')
    + platformIcon('win', x + 13, row - (compact ? 19 : 15), compact ? 22 : 18)
    + text(x + (compact ? 44 : 39), row, 'Windows x64', font, '#e7faff', 'font-weight="600"')
    + platformIcon('mac', macX, row - (compact ? 19 : 15), compact ? 22 : 18)
    + text(macX + (compact ? 31 : 25), row, 'Mac 系统 ARM64', font, '#e7faff', 'font-weight="600"')
    + (compact
      ? text(x + 15, y + 49, '1.3 Mac 通用包待上传', 14, '#efc58c')
      : rect(x + 14, y + 39, width - 28, 1, '#8eedf6', 'opacity=".15"')
        + text(x + 15, y + height - 13, '1.3 Mac 通用包待上传', flat ? 10 : 11, '#efc58c', 'letter-spacing=".7"'));
}

function svgIcon(kind) {
  const common = 'fill="none" stroke="currentColor" stroke-width="2.2" stroke-linecap="round" stroke-linejoin="round"';
  if (kind === 'farm') return `<svg viewBox="0 0 48 48"><g ${common}><path d="M8 38h32M12 34c0-8 5-13 12-13s12 5 12 13"/><path d="M24 21V8M24 14c-6 0-9-3-10-7 6 0 9 2 10 7Zm0 2c6 0 9-3 10-7-6 0-9 2-10 7Z"/><path d="M17 38v-7m14 7v-7"/></g></svg>`;
  if (kind === 'fish') return `<svg viewBox="0 0 48 48"><g ${common}><path d="M8 26c8-9 19-10 28-2l6-5v14l-6-5C27 36 16 35 8 26Z"/><circle cx="29" cy="24" r="1.2" fill="currentColor"/><path d="M13 27c3 0 5 2 6 5"/></g></svg>`;
  if (kind === 'mine') return `<svg viewBox="0 0 48 48"><g ${common}><path d="M10 39 34 15m-9-5 13 13M7 35l6 6M18 30l-5-5 5-5 5 5"/><path d="M31 11c4-3 8-2 10 1l-6 6"/></g></svg>`;
  return `<svg viewBox="0 0 48 48"><g ${common}><path d="M8 17h32v22H8zM14 11h20v6H14zM13 25h9v9h-9zM27 25h8M27 31h8"/><path d="M24 11V7"/></g></svg>`;
}


function defs(width, height) {
  return `<defs>
    <clipPath id="coverClip"><rect width="${width}" height="${height}"/></clipPath>
    <filter id="sceneTone" color-interpolation-filters="sRGB"><feColorMatrix type="saturate" values="1.08"/><feComponentTransfer><feFuncR type="linear" slope="1.02" intercept="-.01"/><feFuncG type="linear" slope="1.02" intercept="-.01"/><feFuncB type="linear" slope="1.02" intercept="-.01"/></feComponentTransfer></filter>
    <filter id="smallGlow" x="-100%" y="-100%" width="300%" height="300%"><feGaussianBlur stdDeviation="3" result="blur"/><feMerge><feMergeNode in="blur"/><feMergeNode in="SourceGraphic"/></feMerge></filter>
    <filter id="softGlow" x="-50%" y="-100%" width="200%" height="300%"><feGaussianBlur stdDeviation="6" result="blur"/><feMerge><feMergeNode in="blur"/><feMergeNode in="SourceGraphic"/></feMerge></filter>
    <radialGradient id="vignette"><stop offset=".1" stop-color="#03080c" stop-opacity="0"/><stop offset=".52" stop-color="#03080c" stop-opacity=".12"/><stop offset="1" stop-color="#03070c" stop-opacity=".78"/></radialGradient>
    <linearGradient id="shade" x1="0" y1="0" x2="0" y2="1"><stop offset="0" stop-color="#03080d" stop-opacity=".62"/><stop offset=".28" stop-color="#03080d" stop-opacity="0"/><stop offset="1" stop-color="#03080d" stop-opacity=".5"/></linearGradient>
    <radialGradient id="water"><stop stop-color="#64eeff" stop-opacity=".16"/><stop offset=".68" stop-color="#64eeff" stop-opacity="0"/></radialGradient>
    <radialGradient id="lantern"><stop stop-color="#ffb84f" stop-opacity=".15"/><stop offset="1" stop-color="#ffa341" stop-opacity="0"/></radialGradient>
    <linearGradient id="petal"><stop stop-color="#ffd3e8"/><stop offset="1" stop-color="#f083bd"/></linearGradient>
    <linearGradient id="wipe" x1="0" y1=".3" x2="1" y2=".7"><stop offset=".32" stop-color="#76f5ff" stop-opacity="0"/><stop offset=".33" stop-color="#76f5ff" stop-opacity=".92"/><stop offset=".35" stop-color="#081b27" stop-opacity=".98"/><stop offset=".63" stop-color="#081b27" stop-opacity=".98"/><stop offset=".64" stop-color="#ff89c8" stop-opacity=".88"/><stop offset=".66" stop-color="#ff89c8" stop-opacity="0"/></linearGradient>
    <linearGradient id="flatBg" x1="0" y1="0" x2="1" y2="1"><stop stop-color="#061017"/><stop offset=".55" stop-color="#09121d"/><stop offset="1" stop-color="#100a18"/></linearGradient>
    <radialGradient id="flatLight"><stop stop-color="#196070" stop-opacity=".28"/><stop offset="1" stop-color="#196070" stop-opacity="0"/></radialGradient>
    <linearGradient id="cardBg" x1="0" y1="0" x2="1" y2="1"><stop stop-color="#0d1f2a" stop-opacity=".88"/><stop offset="1" stop-color="#09121d" stop-opacity=".76"/></linearGradient>
    <linearGradient id="gridFade" x1="0" y1="0" x2="0" y2="1"><stop stop-color="white"/><stop offset=".8" stop-color="black"/></linearGradient>
    <mask id="gridMask"><rect width="100%" height="100%" fill="url(#gridFade)"/></mask>
    <pattern id="grid" width="32" height="32" patternUnits="userSpaceOnUse"><path d="M32 0H0V32" fill="none" stroke="#86ebff" stroke-opacity=".17"/></pattern>
  </defs>`;
}

function worldLayer(sceneUrl, p, variant) {
  const { width: w, height: h } = variant, compact = variant.name === 'compact';
  const sceneX = compact ? -310 : -65, sceneY = compact ? 0 : -90, sceneH = compact ? 720 : 700;
  const cx = sceneX + 625, cy = sceneY + sceneH / 2;
  const dx = Math.sin(p * Math.PI * 2) * 8, dy = Math.cos(p * Math.PI * 2) * 3;
  const scale = 1.015 + Math.min(1, p / .55) * .018;
  let svg = `<g transform="translate(${fmt(cx + dx)} ${fmt(cy + dy)}) scale(${fmt(scale)}) translate(${-cx} ${-cy})"><image href="${sceneUrl}" x="${sceneX}" y="${sceneY}" width="1250" height="${sceneH}" preserveAspectRatio="xMidYMid slice" filter="url(#sceneTone)"/></g>`;
  const waterDx = Math.sin(p * Math.PI * 2) * 15, waterScale = 1 + .07 * Math.sin(p * Math.PI * 4);
  svg += `<ellipse cx="595" cy="357.5" rx="190" ry="92.5" fill="url(#water)" transform="translate(${fmt(waterDx)} 0) translate(595 357.5) scale(${fmt(waterScale)}) translate(-595 -357.5)"/>`;
  svg += `<g opacity="${fmt(.68 + .3 * Math.sin(p * Math.PI * 10))}"><circle cx="${w * .28}" cy="${h * .6}" r="${w * .1}" fill="url(#lantern)"/><circle cx="${w * .74}" cy="${h * .67}" r="${w * .09}" fill="url(#lantern)"/></g>`;
  svg += `<path d="M250 301 Q296 263 342 301" fill="none" stroke="#a7f4ff" stroke-width="1" opacity="${fmt(.25 + .55 * Math.max(0, Math.sin(p * Math.PI * 8)))}" filter="url(#smallGlow)"/>`;
  for (const [x, y, s, sign] of [[625,397,1,1], [752,432,.72,-1]]) {
    const fx = x + sign * Math.sin(p * Math.PI * 2) * (sign === 1 ? 35 : 27);
    svg += `<g transform="translate(${fmt(fx)} ${y}) scale(${s})" fill="#031e2a" opacity=".56"><ellipse cx="14" cy="4" rx="14" ry="4"/><path d="M25 4 35-3V11Z"/></g>`;
  }
  for (let i = 0; i < 12; i++) {
    const q = (p * 1.8 + i * .083) % 1;
    const x = w * (4 + i * 3.7) / 100 + Math.sin(q * Math.PI * 4 + i) * 18;
    const y = h * (12 + (i * 17) % 51) / 100 + q * 170;
    svg += rounded(x, y, 7, 4, 'url(#petal)', 2, `opacity="${fmt(Math.sin(q * Math.PI))}" transform="rotate(${fmt(q * 260)} ${fmt(x + 3.5)} ${fmt(y + 2)})" filter="url(#smallGlow)"`);
  }
  for (let i = 0; i < 18; i++) {
    const x = w * (9 + (i * 23) % 84) / 100 + 1.5;
    const y = h * (17 + (i * 29) % 62) / 100 + 1.5 + Math.sin(p * Math.PI * 4 + i) * 8;
    svg += `<circle cx="${fmt(x)}" cy="${fmt(y)}" r="1.5" fill="#ffd68b" opacity="${fmt(.18 + .65 * Math.max(0, Math.sin(p * Math.PI * 6 + i * .9)))}" filter="url(#smallGlow)"/>`;
  }
  [[944,124],[1013,197],[1058,289],[956,362],[1026,437]].forEach(([x,y],i) => {
    const pulse = Math.max(0, Math.sin(p * Math.PI * 9 - i * .85));
    svg += `<g transform="translate(${x + 6.5} ${y + 6.5}) rotate(45) scale(${fmt(1 + pulse * .32)})" opacity="${fmt(.25 + .75 * pulse)}">${rect(-6.5,-6.5,13,13,'#4befff','fill-opacity=".12" stroke="#73f5ff" filter="url(#smallGlow)"')}</g>`;
  });
  const rippleScale = 1 + .16 * Math.sin(p * Math.PI * 10);
  svg += `<ellipse cx="673" cy="363.5" rx="44" ry="17.5" fill="none" stroke="#78efff" stroke-opacity=".65" opacity="${fmt(.45 + .35 * Math.cos(p * Math.PI * 10))}" transform="translate(673 363.5) scale(${fmt(rippleScale)}) translate(-673 -363.5)" filter="url(#smallGlow)"/>`;
  const by = 363 + Math.sin(p * Math.PI * 10) * 3;
  svg += `<circle cx="673" cy="${fmt(by)}" r="4" fill="none" stroke="white" stroke-width="2" filter="url(#smallGlow)"/><path d="M669 ${fmt(by)} A4 4 0 0 1 677 ${fmt(by)}" fill="none" stroke="#ff5964" stroke-width="2"/>`;
  svg += rect(0,0,w,h,'url(#shade)') + rect(0,0,w,h,'url(#vignette)');
  const bx = compact ? 24 : 42, by0 = compact ? 28 : 32;
  svg += text(bx, by0 + 10, 'AUTOMATION IN MOTION', compact ? 9 : 11, '#9eeef4', `letter-spacing="${compact ? 2 : 4}"`);
  svg += text(bx, by0 + 47, 'YIYIADDON', compact ? 30 : 36, '#ffffff', `font-family="Arial, sans-serif" font-weight="700" letter-spacing="${compact ? 4 : 7}"`);
  const tag = (x,y,width,copy) => rect(x,y,width,33,'#030c12','fill-opacity=".82"') + rect(x,y,2,33,'#7ff5ff') + text(x+11,y+22,copy,13,'#effdff','letter-spacing="1"');
  svg += tag(compact ? 24 : 66,compact ? 170 : 172,121,'星露谷农场系统');
  svg += tag(compact ? 24 : 434,compact ? 614 : 444,165,'自动钓鱼 · 状态预测');
  svg += tag(w-(compact ? 24 : 62)-153,compact ? 170 : 166,153,'种子预测与自动挖矿');
  return svg;
}

function flatLayer(flatPhase, variant) {
  const { width: w, height: h } = variant, compact = variant.name === 'compact';
  const x = compact ? 24 : 42;
  let svg = rect(0,0,w,h,'url(#flatBg)') + `<ellipse cx="${w/2}" cy="${h*.12}" rx="${w*.35}" ry="${h*.7}" fill="url(#flatLight)"/>`;
  svg += rect(0,0,w,h,'url(#grid)','mask="url(#gridMask)" opacity=".2"');
  svg += `<text x="${x}" y="${compact ? 55 : 66}" font-size="${compact ? 28 : 34}" fill="#eefaff" font-weight="700" letter-spacing="${compact ? 2 : 4}">从世界到<tspan fill="#7ff4ff">自动化</tspan></text>`;
  svg += text(x,compact ? 79 : 94,'四套核心系统 · 一个连续工作流',compact ? 12 : 14,'#b8ced9',`letter-spacing="${compact ? 1 : 2}"`);
  svg += rect(x,compact ? 153 : 113,w-x*2,1,'#8beaf5','opacity=".22"');
  cards.forEach(([icon,title,copy],i) => {
    const q = clamp((flatPhase-i*.08)/.68);
    const cardX = compact ? 24 : 42+(i%2)*525;
    const cardY = compact ? 165+i*118 : 137+Math.floor(i/2)*159;
    const cardW = compact ? 592 : 511, cardH = compact ? 108 : 145;
    const ix = cardX+(compact ? 18 : 22), iy = cardY+(cardH-58)/2;
    const accent = i%2 ? '#ff9cd1' : '#83f4ff';
    const line = i%2 ? '#ff83c4' : '#76f2ff';
    let card = rect(cardX,cardY,cardW,cardH,'url(#cardBg)','stroke="#7fdeed" stroke-opacity=".19"');
    card += rect(cardX-1,cardY+24,2,42,line,'filter="url(#smallGlow)"');
    card += rounded(ix,iy,58,58,accent,15,'fill-opacity=".08"');
    card += svgIcon(icon).replace('<svg viewBox="0 0 48 48">',`<svg x="${ix+10}" y="${iy+10}" width="38" height="38" viewBox="0 0 48 48" color="${accent}">`);
    card += text(ix+76,cardY+cardH/2-2,title,compact ? 22 : 18,'#eefaff','font-weight="700" letter-spacing="2"');
    card += text(ix+76,cardY+cardH/2+25,copy,14,'#b8ced9',`letter-spacing="${compact ? 0 : 1}"`);
    card += text(cardX+cardW-16,cardY+21,`0${i+1}`,10,'#40525c','font-weight="700" text-anchor="end"');
    svg += `<g opacity="${fmt(q)}" transform="translate(0 ${fmt((1-q)*22)})">${card}</g>`;
  });
  const scanOpacity = flatPhase > .05 ? Math.max(0,1-flatPhase) : 0;
  svg += rect(0,flatPhase*530,w,2,'#8bfbff',`opacity="${fmt(scanOpacity)}" filter="url(#softGlow)"`);
  return svg;
}

// 平台状态和官网入口置于转场上层，在光幕扫过时也保持可读。
function persistentInfo(variant) {
  const compact = variant.name === 'compact';
  let svg = compact ? platformPanel(24,94,592,true) : platformPanel(687,30,391,false);
  if (compact) {
    svg += rounded(24,661,592,47,'#031219',9,'fill-opacity=".76" stroke="#8eedf6" stroke-opacity=".16"');
    svg += text(37,680,'官网 · 下载与安装指南',11,'#d5edf3');
    svg += text(183,680,'www.yiyiaddon.asia ↗',12,'#bafaff','font-weight="600"');
    svg += text(37,698,`MINECRAFT ${versions}`,10,'#72eef8');
  } else {
    svg += rounded(42,470,1036,36,'#031219',9,'fill-opacity=".76" stroke="#8eedf6" stroke-opacity=".16"');
    svg += text(55,493,'官网 · 下载与安装指南',11,'#d5edf3');
    svg += text(201,493,'www.yiyiaddon.asia ↗',12,'#bafaff','font-weight="600"');
    svg += text(1065,493,`MINECRAFT ${versions}`,11,'#72eef8','text-anchor="end"');
  }
  return svg;
}

function renderFrame(sceneUrl, index, variant) {
  const p = index/(frameCount-1), transition = clamp((p-.52)/.18), flatPhase = clamp((p-.66)/.18);
  const { width:w,height:h } = variant;
  const wipeOpacity = transition < .04 ? 0 : 1-Math.abs(transition-.5)*2;
  return `<svg xmlns="http://www.w3.org/2000/svg" width="${w}" height="${h}" viewBox="0 0 ${w} ${h}" font-family="Noto Sans CJK SC, Microsoft YaHei, sans-serif">${defs(w,h)}<g clip-path="url(#coverClip)">${rect(0,0,w,h,'#071018')}
    <g opacity="${fmt(1-transition)}">${worldLayer(sceneUrl,p,variant)}</g>
    ${rect((-90+transition*180)*w/100,0,w,h,'url(#wipe)',`opacity="${fmt(wipeOpacity)}"`)}
    <g opacity="${fmt(flatPhase)}">${flatLayer(flatPhase,variant)}</g>
    ${persistentInfo(variant)}
  </g></svg>`;
}

async function encode() {
  const results = [];
  for (const variant of variants) {
    const frames = [];
    for (let index = 0; index < frameCount; index++) {
      const file = path.join(workDir, variant.name, `frame-${String(index).padStart(2,'0')}.png`);
      const data = fs.readFileSync(file), metadata = await sharp(data).metadata();
      if (metadata.width !== variant.width || metadata.height !== variant.height) throw new Error(`${file} 尺寸不正确`);
      frames.push(data);
    }
    const output = path.join(assets,variant.output);
    const delay = frames.map((_,index) => 140+(index === frameCount-1 ? 1000 : 0));
    await sharp(frames,{join:{animated:true}}).gif({delay,loop:0,colours:256,dither:.25,interFrameMaxError:0,effort:7}).toFile(output);
    const metadata = await sharp(output,{animated:true}).metadata();
    results.push({file:variant.output,width:metadata.width,height:metadata.pageHeight,frames:metadata.pages,
      durationMs:metadata.delay.reduce((sum,value)=>sum+value,0),loop:metadata.loop,bytes:fs.statSync(output).size});
  }
  console.log(JSON.stringify(results,null,2));
}

(async () => {
  if (mode === '--encode') { await encode(); return; }
  if (!fs.existsSync(scenePath)) throw new Error('缺少原始场景：'+scenePath);
  const scene = await sharp(scenePath).resize(1250,700,{fit:'cover'}).png().toBuffer();
  const sceneUrl = `data:image/png;base64,${scene.toString('base64')}`;
  fs.mkdirSync(workDir,{recursive:true});
  fs.writeFileSync(path.join(workDir,'manifest.json'),JSON.stringify({frameCount,durationMs:6040,variants},null,2));
  for (const variant of variants) {
    const directory = path.join(workDir,variant.name);
    fs.mkdirSync(directory,{recursive:true});
    const indexes = mode === '--preview' ? [0,18,25,35] : Array.from({length:frameCount},(_,i)=>i);
    for (const index of indexes) {
      const file = path.join(directory,`frame-${String(index).padStart(2,'0')}`);
      const svg = renderFrame(sceneUrl,index,variant);
      await sharp(Buffer.from(svg)).png().toFile(file+'.png');
      if (mode === '--preview') fs.writeFileSync(file+'.svg',svg);
      if (index%6 === 0 || index === frameCount-1) console.log(`${variant.name} ${index+1}/${frameCount}`);
    }
  }
  if (mode === '--render') await encode();
})().catch(error => { console.error(error); process.exit(1); });
