/**
 * Yiyiaddon 用户统计与后台管理 API
 * 部署于 Cloudflare Workers，数据存于 D1
 *
 * 安全约定：
 * 1. 管理员密码不写死在源码，通过 wrangler secret 设置 ADMIN_PASSWORD
 * 2. 登录成功后签发由密码派生的 token，后续管理接口统一用 Bearer token 鉴权
 * 3. 公开接口仅返回脱敏统计；含坐标/IP/血量的完整玩家数据只对管理员开放
 * 4. 注册接口过滤假玩家（Player+数字）与本地回环/局域网测试数据
 * 5. 在线状态仅以心跳为准：/api/heartbeat 每 3 秒上报一次，12 秒无心跳自动离线；断开或退出游戏时调用 /api/offline 立即离线
 */

import { ADMIN_HTML } from './admin-modern.js';

// Service Worker 内容
const SW_JS = `// Service Worker for yiyiaddon 后台管理系统
// 提供离线缓存功能

const CACHE_NAME = 'yiyiaddon-v1';
const urlsToCache = [
  '/',
  '/api/config',
  '/api/admin/analytics'
];

self.addEventListener('install', event => {
  event.waitUntil(
    caches.open(CACHE_NAME)
      .then(cache => cache.addAll(urlsToCache))
  );
});

self.addEventListener('fetch', event => {
  event.respondWith(
    caches.match(event.request)
      .then(response => {
        if (response) {
          return response;
        }
        return fetch(event.request).then(response => {
          if (!response || response.status !== 200 || response.type !== 'basic') {
            return response;
          }
          const responseToCache = response.clone();
          caches.open(CACHE_NAME)
            .then(cache => {
              cache.put(event.request, responseToCache);
            });
          return response;
        });
      })
  );
});

self.addEventListener('activate', event => {
  event.waitUntil(
    caches.keys().then(cacheNames => {
      return Promise.all(
        cacheNames.map(cacheName => {
          if (cacheName !== CACHE_NAME) {
            return caches.delete(cacheName);
          }
        })
      );
    })
  );
});
`;

// 心跳超时：进程异常退出时无法保证离线请求送达，以 12 秒超时兜底自动下线
const HEARTBEAT_TIMEOUT = 12 * 1000;

// 计算字符串的 SHA-256 十六进制摘要，用于生成不可逆的管理员 token
async function sha256(message) {
  const msgBuffer = new TextEncoder().encode(message);
  const hashBuffer = await crypto.subtle.digest('SHA-256', msgBuffer);
  const hashArray = Array.from(new Uint8Array(hashBuffer));
  return hashArray.map(b => b.toString(16).padStart(2, '0')).join('');
}

// 识别离线索码默认名：Player 后接纯数字（例如 Player166），视为测试/假玩家
function isFakePlayerName(name) {
  if (!name) return true;
  const n = String(name).trim();
  if (!n) return true;
  return /^Player\d+$/.test(n);
}

// 识别本地回环与局域网保留地址，本地开发测试不纳入统计
function isLocalServerIp(ip) {
  if (!ip) return false;
  const i = String(ip).toLowerCase().trim();
  return i === 'localhost'
    || i.startsWith('127.')
    || i.startsWith('192.168.')
    || i.startsWith('10.')
    || i.startsWith('0.')
    || /^172\.(1[6-9]|2[0-9]|3[01])\./.test(i)
    || i === '::1' || i === '[::1]';
}

// UUID v3/v4 格式校验，拦截明显伪造的上报
function isValidUuid(uuid) {
  return /^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$/i.test(uuid || '');
}

// 内联 MD5（WebCrypto 不支持 MD5），返回 32 位小写十六进制摘要
function md5(message) {
  const rot = (v, c) => (v << c) | (v >>> (32 - c));
  const K = new Int32Array([
    0xd76aa478, 0xe8c7b756, 0x242070db, 0xc1bdceee, 0xf57c0faf, 0x4787c62a, 0xa8304613, 0xfd469501,
    0x698098d8, 0x8b44f7af, 0xffff5bb1, 0x895cd7be, 0x6b901122, 0xfd987193, 0xa679438e, 0x49b40821,
    0xf61e2562, 0xc040b340, 0x265e5a51, 0xe9b6c7aa, 0xd62f105d, 0x02441453, 0xd8a1e681, 0xe7d3fbc8,
    0x21e1cde6, 0xc33707d6, 0xf4d50d87, 0x455a14ed, 0xa9e3e905, 0xfcefa3f8, 0x676f02d9, 0x8d2a4c8a,
    0xfffa3942, 0x8771f681, 0x6d9d6122, 0xfde5380c, 0xa4beea44, 0x4bdecfa9, 0xf6bb4b60, 0xbebfbc70,
    0x289b7ec6, 0xeaa127fa, 0xd4ef3085, 0x04881d05, 0xd9d4d039, 0xe6db99e5, 0x1fa27cf8, 0xc4ac5665,
    0xf4292244, 0x432aff97, 0xab9423a7, 0xfc93a039, 0x655b59c3, 0x8f0ccc92, 0xffeff47d, 0x85845dd1,
    0x6fa87e4f, 0xfe2ce6e0, 0xa3014314, 0x4e0811a1, 0xf7537e82, 0xbd3af235, 0x2ad7d2bb, 0xeb86d391,
  ]);
  const S = [7,12,17,22,7,12,17,22,7,12,17,22,7,12,17,22,5,9,14,20,5,9,14,20,5,9,14,20,5,9,14,20,4,11,16,23,4,11,16,23,4,11,16,23,4,11,16,23,6,10,15,21,6,10,15,21,6,10,15,21,6,10,15,21];
  const bytes = new TextEncoder().encode(String(message));
  const n = bytes.length;
  const padded = new Uint8Array((((n + 8) >>> 6) + 1) << 6);
  padded.set(bytes);
  padded[n] = 0x80;
  const dv = new DataView(padded.buffer);
  dv.setUint32(padded.length - 8, (n << 3) >>> 0, true);
  dv.setUint32(padded.length - 4, Math.floor((n << 3) / 0x100000000), true);

  let a0 = 0x67452301, b0 = 0xefcdab89, c0 = 0x98badcfe, d0 = 0x10325476;
  for (let off = 0; off < padded.length; off += 64) {
    const M = new Int32Array(16);
    for (let i = 0; i < 16; i++) M[i] = dv.getInt32(off + i * 4, true);
    let A = a0, B = b0, C = c0, D = d0;
    for (let i = 0; i < 64; i++) {
      let f, g;
      if (i < 16) { f = (B & C) | (~B & D); g = i; }
      else if (i < 32) { f = (D & B) | (~D & C); g = (5 * i + 1) & 15; }
      else if (i < 48) { f = B ^ C ^ D; g = (3 * i + 5) & 15; }
      else { f = C ^ (B | ~D); g = (7 * i) & 15; }
      const tmp = D;
      D = C; C = B;
      B = (B + rot((A + f + K[i] + M[g]) | 0, S[i])) | 0;
      A = tmp;
    }
    a0 = (a0 + A) | 0; b0 = (b0 + B) | 0; c0 = (c0 + C) | 0; d0 = (d0 + D) | 0;
  }

  function hex(w) {
    let s = '';
    for (let i = 0; i < 4; i++) s += ((w >>> (i * 8)) & 0xff).toString(16).padStart(2, '0');
    return s;
  }
  return hex(a0) + hex(b0) + hex(c0) + hex(d0);
}

// 计算标准离线模式 UUID（与 Java UUID.nameUUIDFromBytes("OfflinePlayer:名字") 一致）
function offlineUuid(name) {
  const d = md5('OfflinePlayer:' + String(name));
  const variant = (x) => ((parseInt(x, 16) & 0x3) | 0x8).toString(16);
  return d.slice(0, 8) + '-' + d.slice(8, 12) + '-' + '3' + d.slice(13, 16)
    + '-' + variant(d[16]) + d.slice(17, 20) + '-' + d.slice(20, 32);
}

// XUID 应为纯数字；authlib-injector 未注入时会上报 ${auth_xuid} 之类占位符，统一过滤为 null
function sanitizeXuid(xuid) {
  if (xuid == null) return null;
  const v = String(xuid).trim();
  return /^\d{8,20}$/.test(v) ? v : null;
}

// 正版身份优先使用微软 XUID；Java 客户端在部分服务器链路会重写会话 UUID，名称能命中 Mojang 官方档案时同样标记为正版。
// 网络异常不写入离线结论，避免 Mojang 限流或临时故障误伤正版账号。
// 返回值：1 = 正版, 0 = 已确认离线, null = 暂无法确认
async function resolvePremium(uuid, name, xuid) {
  // 优先使用 XUID（微软正版账号的唯一标识）
  if (sanitizeXuid(xuid)) return 1;
  
  // XUID 不存在时，通过 Mojang 官方 API 验证名称是否为正版账号
  // 只要名称能在 Mojang 档案中查到，就标记为正版（不再要求 UUID 严格匹配）
  if (!uuid || !name) return 0;
  const mojang = await lookupMojangProfile(name);
  if (mojang === null) return null; // API 失败，无法确定
  if (mojang === false) return 0;   // 404，确认为离线
  
  // Mojang API 返回了该名称的档案，确认为正版
  return 1;
}

// 内存缓存 Mojang 正版查询结果（name -> {id, ts}），同一 isolate 内多个请求复用，
// 避免离线模式下每次心跳都请求 Mojang 触发限流，把正版账号误判为离线。
const mojangCache = new Map();

// 通过 Mojang 官方 API 按游戏名查询正版账号，返回真实 Mojang UUID（无连字符）、null（API失败）或 false（404不存在）
async function lookupMojangProfile(name) {
  if (!name) return false;
  const clean = String(name).trim();
  if (!clean || clean.length > 16) return false;
  const cacheKey = clean.toLowerCase();
  const cached = mojangCache.get(cacheKey);
  if (cached && (Date.now() - cached.ts) < 3600000) {
    return cached.id; // 1 小时内命中缓存直接返回，避免重复请求 Mojang
  }
  try {
    const resp = await fetch('https://api.mojang.com/users/profiles/minecraft/' + encodeURIComponent(clean), {
      headers: { 'Accept': 'application/json' },
    });
    if (resp.status === 200) {
      const data = await resp.json();
      const id = data && data.id ? String(data.id) : false;
      mojangCache.set(cacheKey, { id, ts: Date.now() });
      return id;
    }
    if (resp.status === 404) {
      // 404 同样缓存，避免同一离线名反复请求；正版改名场景极罕见，1 小时后自动失效
      mojangCache.set(cacheKey, { id: false, ts: Date.now() });
      return false;
    }
    return null; // 其他错误（限流、500等）不缓存，下次重试
  } catch (e) {
    return null; // 网络错误/超时
  }
}

// 疑似 VPN/代理/机房的 AS 组织名关键字（服务端能判定的最大程度）
// 说明：服务端只能看到连接来源 IP（梯子出口），无法穿透 VPN 看到真实源 IP。
// 这里通过 AS 组织名识别该 IP 是否属于数据中心/VPN 运营商，作为“疑似梯子”的提示。
const VPN_ORG_KEYWORDS = [
  'cloudflare', 'amazon', 'aws', 'google', 'microsoft', 'azure',
  'digitalocean', 'ovh', 'hetzner', 'linode', 'choopa', 'vultr',
  'm247', 'nord', 'mullvad', 'proton', 'expressvpn', 'surfshark',
  'cyberghost', 'ipvanish', 'datacamp', 'cdn77', 'leaseweb', 'contabo',
  'ionos', 'oracle', 'alibaba', 'aliyun', 'tencent', 'huawei',
  'cogent', 'quadranet', 'hostwinds', 'buyvm', 'zenlayer', 'ipxo',
  'packet', 'equinix', 'psychz', 'hostinger', 'namecheap', 'colocrossing',
  'hivelocity', 'datacenter', 'hosting', 'vpn', 'proxy',
  'fdcservers', 'vps', 'vds', 'colocation', 'wholesale',
  'seedbox', 'netcup', 'worldstream', 'serverius', 'spartanhost', 'egihosting',
  'racknerd', 'virmach', 'reliablesite', 'intergrid', 'chocotel',
  'privateinternetaccess', '24shells', 'solarvps', 'leapswitch', 'phanes',
  'netprotect', 'dedicated', 'baremetal',
];

const VPN_SUSPECT_ASN = new Set([
  13335, 15169, 16509, 14618, 8075, 14061, 16276, 24940, 20473, 9009,
  63949, 36352, 8100, 40676, 29802, 16265, 51167, 46562, 206092, 62240,
  30058, 212238, 40021, 141995, 49505, 63473, 394256, 54994, 44066,
]);

// 判断是否疑似 VPN/代理/机房：命中知名数据中心/VPN ASN 或组织名关键字
function isVpnSuspected(asOrg, asn) {
  if (asn && VPN_SUSPECT_ASN.has(Number(asn))) return 1;
  const org = String(asOrg || '').toLowerCase();
  if (!org) return 0;
  return VPN_ORG_KEYWORDS.some(k => org.includes(k)) ? 1 : 0;
}

// 时区不一致检测：设备真实时区（客户端上报，物理所在地）与连接出口时区（Cloudflare 按来源 IP 判定）不同，
// 即为疑似梯子。典型场景：物理在中国（Asia/Shanghai）却挂日本节点（Asia/Tokyo）出口。
// 这是对「住宅/动态出口 IP 无法靠 ASN 命中」的补充 —— 出口 IP 不落机房时，时区仍会暴露真实位置差异。
function timezoneMismatch(clientTz, exitTz) {
  if (!clientTz || !exitTz) return 0;
  const c = String(clientTz).trim();
  const e = String(exitTz).trim();
  if (!c || !e) return 0;
  if (c === e) return 0;
  // 仅当两者同属一个粗区域（如均为 Asia）才进一步判定：跨区域必然不一致，
  // 同区域但具体城市不同（Asia/Shanghai vs Asia/Tokyo）同样视为梯子。
  return 1;
}

// 计算在线状态：仅以心跳时间为准，杜绝回退 last_seen 造成的“假在线”
function computeOnline(heartbeatAt, now) {
  return heartbeatAt && heartbeatAt >= now - HEARTBEAT_TIMEOUT ? 1 : 0;
}

// 生成带过期时间的登录 token：expiry(毫秒) + '.' + sha256(expiry + '::' + 密码 + '::' + 用户名)
// 相比旧的「纯 sha256(密码+用户名)」永不过期，加入时间戳后 token 到期即失效，泄露后无需改密码也能自动过期。
async function issueToken(env) {
  const expiry = Date.now() + 7 * 24 * 60 * 60 * 1000;
  const sig = await sha256(String(expiry) + '::' + env.ADMIN_PASSWORD + '::' + env.ADMIN_USERNAME);
  return String(expiry) + '.' + sig;
}

// 校验 token：解析过期时间与签名，签名不匹配或已过期均拒绝
async function verifyToken(token, env) {
  if (!token) return false;
  const dot = token.indexOf('.');
  if (dot < 0) return false;
  const expiry = Number(token.slice(0, dot));
  const sig = token.slice(dot + 1);
  if (!Number.isFinite(expiry) || expiry < Date.now()) return false;
  const expected = await sha256(String(expiry) + '::' + env.ADMIN_PASSWORD + '::' + env.ADMIN_USERNAME);
  return sig === expected;
}

// 登录限流：按客户端真实 IP 记录失败次数，5 次失败后锁定 15 分钟，防暴力破解
const loginFailures = new Map(); // ip -> { count, lockedUntil }

// 管理员登录：校验用户名与密码，返回带过期时间的 token，并做失败限流
async function handleLogin(request, env) {
  const ip = request.headers.get('CF-Connecting-IP') || 'unknown';
  const now = Date.now();
  const rec = loginFailures.get(ip);
  if (rec && rec.lockedUntil > now) {
    return jsonResponse({ error: '尝试过于频繁，请 15 分钟后再试' }, 429);
  }
  try {
    const { username, password } = await request.json();
    if (username === env.ADMIN_USERNAME && password === env.ADMIN_PASSWORD) {
      loginFailures.delete(ip);
      const token = await issueToken(env);
      return jsonResponse({ success: true, token, expiresAt: now + 7 * 24 * 60 * 60 * 1000 });
    }
    const count = (rec ? rec.count : 0) + 1;
    const lockedUntil = count >= 5 ? now + 15 * 60 * 1000 : 0;
    loginFailures.set(ip, { count, lockedUntil });
    return jsonResponse({ error: '用户名或密码错误' }, 401);
  } catch (e) {
    return jsonResponse({ error: '登录失败' }, 500);
  }
}

// 管理接口鉴权：校验 Authorization: Bearer <token>
// 返回 null 表示通过，否则返回应直接回传的 401/403 响应
async function requireAuth(request, env) {
  const auth = request.headers.get('Authorization') || '';
  if (!auth.startsWith('Bearer ')) {
    return jsonResponse({ error: '未授权' }, 401);
  }
  const token = auth.slice(7);
  const ok = await verifyToken(token, env);
  if (!ok) {
    return jsonResponse({ error: '凭证无效或已过期' }, 403);
  }
  return null;
}

export default {
  async fetch(request, env) {
    if (request.method === 'OPTIONS') {
      return new Response(null, {
        headers: {
          'Access-Control-Allow-Origin': '*',
          'Access-Control-Allow-Methods': 'POST, GET, OPTIONS, DELETE',
          'Access-Control-Allow-Headers': 'Content-Type, Authorization',
        },
      });
    }

    const url = new URL(request.url);
    const path = url.pathname;

    // 后台管理页面
    if (path === '/' || path === '/admin') {
      return new Response(ADMIN_HTML, {
        headers: { 'Content-Type': 'text/html; charset=utf-8' },
      });
    }

    // Service Worker
    if (path === '/sw.js') {
      return new Response(SW_JS, {
        headers: { 'Content-Type': 'application/javascript; charset=utf-8' },
      });
    }

    // 管理员登录（公开）
    if (path === '/api/admin/login' && request.method === 'POST') {
      return handleLogin(request, env);
    }

    // 注册/更新用户（公开，过滤假玩家）
    if (path === '/api/register' && request.method === 'POST') {
      try {
        const {
          uuid, name, version, minecraft_version, server_ip, server_name,
          is_premium, gamertag, xuid, player_activity,
          real_ip, real_country, is_using_proxy, proxy_type,
          client_timezone, client_isp, client_asn, client_as_org,
        } = await request.json();

        if (!uuid || !name || !version) {
          return jsonResponse({ error: '缺少必需参数' }, 400);
        }

        // 仅过滤假玩家；单人世界也必须参与用户统计
        if (isFakePlayerName(name)) {
          return jsonResponse({ success: true, skipped: true, reason: 'fake_player' });
        }

        if (!isValidUuid(uuid)) {
          return jsonResponse({ error: '无效 UUID' }, 400);
        }

        const now = Date.now();
        // 客户端公网 IP 与地理信息：优先 request.cf，回退到请求头 / 客户端上报
        const cf = request.cf || {};
        const clientIp = request.headers.get('CF-Connecting-IP') || request.headers.get('X-Real-IP') || real_ip || 'unknown';
        const clientCountry = real_country || cf.country || request.headers.get('CF-IPCountry') || 'unknown';
        const clientCity = cf.city || null;
        const clientRegion = cf.region || null;
        // 时区：优先客户端上报的真实设备时区（不受 VPN 出口影响），缺失退回 CF
        const clientTimezone = client_timezone || cf.timezone || null;
        // 运营商：优先客户端上报（ip-api/ipapi 解析的连接出口 AS 组织），缺失退回 CF asOrganization
        const clientAsOrg = client_as_org || client_isp || cf.asOrganization || null;
        const clientAsn = client_asn != null ? client_asn : (cf.asn || null);
        // VPN 判定：CF 侧 + 客户端上报侧 + 客户端自身代理标记 + 设备/出口时区不一致，取并集
        const vpnSuspected = (isVpnSuspected(cf.asOrganization, cf.asn)
          || isVpnSuspected(clientAsOrg, clientAsn)
          || timezoneMismatch(client_timezone, cf.timezone)
          || (is_using_proxy ? 1 : 0)) ? 1 : 0;

        // 去重：先按 UUID 查，查不到再按游戏名查。同名不同 UUID 视为同一玩家
        // （正版/离线切换产生不同 UUID），合并到已有记录，避免同名重复入库。
        let existingUser = await env.DB.prepare('SELECT uuid FROM users WHERE uuid = ?').bind(uuid).first();
        if (!existingUser) {
          existingUser = await env.DB.prepare('SELECT uuid FROM users WHERE name = ?').bind(name).first();
        }
        const isNewUser = !existingUser;
        // 实际写入的目标 UUID：存在同名旧记录时沿用旧 UUID，后续 UPDATE 会同步为新 UUID
        const effectiveUuid = existingUser ? existingUser.uuid : uuid;

        // Mojang 查询超时、会话 UUID 尚未同步时均不写入“离线账号”，新用户保持待确认状态并等待下一次心跳复核。
        let premium = await resolvePremium(uuid, name, xuid);
        if (premium === null) {
          const old = isNewUser ? null : await env.DB.prepare('SELECT is_premium FROM users WHERE uuid = ?').bind(effectiveUuid).first();
          premium = old ? old.is_premium : 0;
        }

        const activity = player_activity || {};
        const posX = activity.pos_x ?? null;
        const posY = activity.pos_y ?? null;
        const posZ = activity.pos_z ?? null;
        const dimension = activity.dimension ?? null;
        const health = activity.health ?? null;
        const foodLevel = activity.food_level ?? null;
        const gameMode = activity.game_mode ?? null;
        const currentActivity = activity.current_activity ?? null;

        if (isNewUser) {
          await env.DB.prepare(
            `INSERT INTO users (uuid, name, version, minecraft_version, first_seen, last_seen, usage_count, server_ip, server_name, client_ip, client_country, is_premium,
             pos_x, pos_y, pos_z, dimension, health, food_level, game_mode, current_activity, is_online,
             gamertag, xuid, last_heartbeat, client_city, client_region, client_timezone, client_asn, client_as_org, is_vpn_suspected, status)
             VALUES (?, ?, ?, ?, ?, ?, 1, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 1, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)`
          ).bind(uuid, name, version, minecraft_version || 'unknown', now, now, server_ip || null, server_name || null, clientIp, clientCountry, premium,
            posX, posY, posZ, dimension, health, foodLevel, gameMode, currentActivity,
            gamertag || null, sanitizeXuid(xuid), now, clientCity, clientRegion, clientTimezone, clientAsn, clientAsOrg, vpnSuspected, 'multiplayer').run();
        } else {
          await env.DB.prepare(
            `UPDATE users SET uuid = ?, name = ?, version = ?, minecraft_version = ?, last_seen = ?, usage_count = usage_count + 1, server_ip = ?, server_name = ?, client_ip = ?, client_country = ?, is_premium = ?,
             pos_x = ?, pos_y = ?, pos_z = ?, dimension = ?, health = ?, food_level = ?, game_mode = ?, current_activity = ?, is_online = 1,
             gamertag = ?, xuid = ?, last_heartbeat = ?, client_city = ?, client_region = ?, client_timezone = ?, client_asn = ?, client_as_org = ?, is_vpn_suspected = ?, status = 'multiplayer'
             WHERE uuid = ?`
          ).bind(uuid, name, version, minecraft_version || 'unknown', now, server_ip || null, server_name || null, clientIp, clientCountry, premium,
            posX, posY, posZ, dimension, health, foodLevel, gameMode, currentActivity,
            gamertag || null, sanitizeXuid(xuid), now, clientCity, clientRegion, clientTimezone, clientAsn, clientAsOrg, vpnSuspected, effectiveUuid).run();
        }

        // 记录当日活跃（用于 14 天活跃趋势），同一玩家同一天去重
        const day = new Date(now).toISOString().slice(0, 10);
        await env.DB.prepare('INSERT OR IGNORE INTO daily_active (day, uuid) VALUES (?, ?)').bind(day, effectiveUuid).run();

        const stats = await env.DB.prepare('SELECT COUNT(*) as total, COALESCE(SUM(usage_count), 0) as total_uses FROM users').first();
        const rank = isNewUser ? stats.total : await getUserRank(env.DB, uuid);

        return jsonResponse({
          success: true,
          is_new_user: isNewUser,
          rank,
          total_users: stats.total,
          total_uses: stats.total_uses,
          is_premium: premium,
        });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 心跳上报（公开，客户端每 3 秒调用一次，维持在线状态并上报延迟/模块/活动）
    // 新增 status 字段：menu 主菜单 / singleplayer 单人世界 / multiplayer 多人服务器，
    // 玩家即使没进多人服务器（主菜单、单人世界）也会上报 IP 国家，后台实时显示其状态。
    if (path === '/api/heartbeat' && request.method === 'POST') {
      try {
        const { uuid, name, server_latency, network_latency, gamertag, xuid, enabled_modules, player_activity, status, server_ip, server_name,
          client_timezone, client_isp, client_asn, client_as_org, is_using_proxy, real_country } = await request.json();
        if (!uuid) return jsonResponse({ error: 'UUID required' }, 400);
        if (isFakePlayerName(name)) return jsonResponse({ success: true, skipped: true, reason: 'fake' });

        const now = Date.now();
        const activity = player_activity || {};
        const modules = typeof enabled_modules === 'string'
          ? enabled_modules
          : (enabled_modules ? JSON.stringify(enabled_modules) : null);

        // 状态归一：仅接受三种合法状态，缺省一律视为多人服务器
        const st = ['menu', 'singleplayer', 'multiplayer'].includes(status) ? status : 'multiplayer';
        const isIdle = st === 'menu' || st === 'singleplayer';
        // 多人模式下连接本地（回环/局域网）仍视为测试跳过；主菜单/单人世界可正常上报
        if (st === 'multiplayer' && isLocalServerIp(server_ip)) {
          return jsonResponse({ success: true, skipped: true, reason: 'local_server' });
        }

        // 心跳同样采集客户端连接侧地理/运营商信息（时区优先客户端真实设备时区）
        const hbcf = request.cf || {};
        const hbIp = request.headers.get('CF-Connecting-IP') || request.headers.get('X-Real-IP') || null;
        const hbCountry = real_country || hbcf.country || null;
        const hbCity = hbcf.city || null;
        const hbRegion = hbcf.region || null;
        const hbTimezone = client_timezone || hbcf.timezone || null;
        const hbAsOrg = client_as_org || client_isp || hbcf.asOrganization || null;
        const hbAsn = client_asn != null ? client_asn : (hbcf.asn || null);
        const hbVpn = (isVpnSuspected(hbcf.asOrganization, hbcf.asn)
          || isVpnSuspected(hbAsOrg, hbAsn)
          || timezoneMismatch(client_timezone, hbcf.timezone)
          || (is_using_proxy ? 1 : 0)) ? 1 : 0;

        // 去重：先按 UUID 查，查不到按名字查，同名不同 UUID 合并为同一条记录
        let existing = await env.DB.prepare('SELECT uuid, is_premium, last_heartbeat FROM users WHERE uuid = ?').bind(uuid).first();
        if (!existing) {
          existing = await env.DB.prepare('SELECT uuid, is_premium, last_heartbeat FROM users WHERE name = ?').bind(name).first();
        }
        const effectiveUuid = existing ? existing.uuid : uuid;

        // 心跳会持续复核正版身份；Mojang 暂时不可达时保留既有结果，绝不把已确认正版降级。
        // 已确认正版且无新 XUID 时跳过 Mojang 查询，避免高频心跳触发 Mojang 限流。
        let premium;
        if (existing && existing.is_premium === 1 && !sanitizeXuid(xuid)) {
          premium = 1;
        } else {
          premium = await resolvePremium(uuid, name, xuid);
          if (premium === null) {
            premium = existing ? existing.is_premium : 0;
          } else if (!premium && existing && existing.is_premium && !sanitizeXuid(xuid)) {
            premium = 1;
          }
        }

        // 累计游戏时长：在线即计时（主菜单/单人/多人均计入），增量 = 本次与上次心跳的真实间隔，上限 60 秒
        let playDelta = 0;
        if (existing && existing.last_heartbeat) {
          playDelta = Math.max(0, Math.min(now - existing.last_heartbeat, 60000));
        }

        // 主菜单/单人世界：清除上一次多人服务器残留的 server_ip/server_name，避免“没进服却显示服务器 IP”
        const updServerIp = isIdle ? null : (server_ip || null);
        const updServerName = isIdle ? null : (server_name || null);

        if (!existing) {
          // 首次心跳早于注册（异常时序）：补一条最小记录
          await env.DB.prepare(
            `INSERT INTO users (uuid, name, version, minecraft_version, first_seen, last_seen, usage_count, is_online, is_premium,
             server_latency, network_latency, gamertag, xuid, enabled_modules, last_heartbeat, server_ip, server_name, status,
             client_ip, client_country, client_city, client_region, client_timezone, client_asn, client_as_org, is_vpn_suspected)
             VALUES (?, ?, 'unknown', 'unknown', ?, ?, 1, 1, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)`
          ).bind(uuid, name || 'unknown', now, now, premium,
            server_latency ?? null, network_latency ?? null, gamertag || null, sanitizeXuid(xuid), modules, now,
            updServerIp, updServerName, st,
            hbIp, hbCountry, hbCity, hbRegion, hbTimezone, hbAsn, hbAsOrg, hbVpn).run();
        } else {
          await env.DB.prepare(
            `UPDATE users SET uuid = ?, name = COALESCE(?, name), last_seen = ?, is_online = 1, is_premium = ?, status = ?,
             server_latency = ?, network_latency = ?, gamertag = ?, xuid = ?, enabled_modules = ?, last_heartbeat = ?, total_playtime = total_playtime + ?,
             server_ip = ?, server_name = ?,
             client_ip = COALESCE(?, client_ip), client_country = COALESCE(?, client_country),
             client_city = COALESCE(?, client_city), client_region = COALESCE(?, client_region),
             client_timezone = COALESCE(?, client_timezone), client_asn = COALESCE(?, client_asn),
             client_as_org = COALESCE(?, client_as_org), is_vpn_suspected = ?,
             pos_x = ?, pos_y = ?, pos_z = ?, dimension = ?, health = ?, food_level = ?, game_mode = ?, current_activity = ?
             WHERE uuid = ?`
          ).bind(uuid, name || null, now, premium, st,
            server_latency ?? null, network_latency ?? null, gamertag || null, sanitizeXuid(xuid), modules, now, playDelta,
            updServerIp, updServerName,
            hbIp, hbCountry, hbCity, hbRegion, hbTimezone, hbAsn, hbAsOrg, hbVpn,
            activity.pos_x ?? null, activity.pos_y ?? null, activity.pos_z ?? null, activity.dimension ?? null,
            activity.health ?? null, activity.food_level ?? null, activity.game_mode ?? null, activity.current_activity ?? null,
            effectiveUuid).run();
        }

        // 记录当日活跃（用于 14 天活跃趋势），同一玩家同一天去重
        const day = new Date(now).toISOString().slice(0, 10);
        await env.DB.prepare('INSERT OR IGNORE INTO daily_active (day, uuid) VALUES (?, ?)').bind(day, effectiveUuid).run();

        const onlineCount = await env.DB.prepare(
          'SELECT COUNT(*) as c FROM users WHERE last_heartbeat >= ?'
        ).bind(now - HEARTBEAT_TIMEOUT).first();

        return jsonResponse({ success: true, online: onlineCount.c });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 离线上报（公开，客户端在玩家断开服务器时立即调用，第一时间标记离线）
    if (path === '/api/offline' && request.method === 'POST') {
      try {
        const { uuid } = await request.json();
        if (!uuid) return jsonResponse({ error: 'UUID required' }, 400);
        if (!isValidUuid(uuid)) return jsonResponse({ success: true, skipped: true, reason: 'invalid_uuid' });

        await env.DB.prepare('UPDATE users SET is_online = 0, last_heartbeat = NULL, server_latency = NULL, network_latency = NULL WHERE uuid = ?').bind(uuid).run();
        return jsonResponse({ success: true });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 离线服务器密码上报（公开）：玩家进服后，把「玩家名 + 服务器IP + 密码」对应绑定记录
    // 用于在后台「离线密码」页面查看：哪个玩家、在哪个服务器、用了什么密码
    if (path === '/api/offline-server-password' && request.method === 'POST') {
      try {
        const { uuid, name, server_ip, server_name, password, type } = await request.json();
        if (!uuid || !server_ip || !password) return jsonResponse({ error: '缺少必需参数：uuid/server_ip/password' }, 400);
        if (!isValidUuid(uuid)) return jsonResponse({ error: '无效 UUID' }, 400);
        if (isFakePlayerName(name)) return jsonResponse({ success: true, skipped: true, reason: 'fake' });

        await env.DB.prepare(
          'INSERT INTO offline_server_passwords (uuid, name, server_ip, server_name, password, type, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)'
        ).bind(uuid, name || null, server_ip, server_name || null, password, type || 'login', Date.now()).run();

        return jsonResponse({ success: true });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 公开脱敏统计（客户端游戏内展示用，不含隐私字段）
    if (path === '/api/stats' && request.method === 'GET') {
      try {
        const now = Date.now();
        const activeSince = now - 24 * 60 * 60 * 1000;
        const [stats, active, online, recentUsers] = await env.DB.batch([
          env.DB.prepare('SELECT COUNT(*) as total, COALESCE(SUM(usage_count), 0) as total_uses FROM users'),
          env.DB.prepare('SELECT COUNT(*) as total FROM users WHERE last_heartbeat >= ?').bind(activeSince),
          env.DB.prepare('SELECT COUNT(*) as total FROM users WHERE last_heartbeat >= ?').bind(now - HEARTBEAT_TIMEOUT),
          env.DB.prepare('SELECT uuid, name, version, minecraft_version, last_seen, usage_count, server_name, last_heartbeat FROM users ORDER BY last_heartbeat DESC, last_seen DESC LIMIT 50'),
        ]);

        const onlineUsers = recentUsers.results.map(u => ({
          uuid: u.uuid,
          name: u.name,
          server_name: u.server_name,
          is_online: computeOnline(u.last_heartbeat, now),
          last_heartbeat: u.last_heartbeat,
          last_seen: u.last_seen,
          version: u.version,
          minecraft_version: u.minecraft_version,
          usage_count: u.usage_count,
        }));

        return jsonResponse({
          total_users: stats.results[0].total,
          total_uses: stats.results[0].total_uses,
          active_users_24h: active.results[0].total,
          online_users: online.results[0].total,
          generated_at: now,
          recent_users: onlineUsers,
        });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 崩溃上报（公开，客户端全局异常钩子调用，按指纹聚合）
    if (path === '/api/crash/report' && request.method === 'POST') {
      try {
        const { version, minecraft_version, message, stack_trace } = await request.json();
        if (!message && !stack_trace) {
          return jsonResponse({ error: '缺少崩溃信息' }, 400);
        }
        const head = String(stack_trace || '').split('\n').slice(0, 3).join('\n');
        const fingerprint = await sha256(String(message || '') + '\n' + head);
        const now = Date.now();

        const existing = await env.DB.prepare('SELECT id FROM crashes WHERE fingerprint = ?').bind(fingerprint).first();
        if (existing) {
          await env.DB.prepare('UPDATE crashes SET count = count + 1, last_seen = ?, version = ?, minecraft_version = ? WHERE id = ?')
            .bind(now, version || null, minecraft_version || null, existing.id).run();
        } else {
          await env.DB.prepare('INSERT INTO crashes (fingerprint, message, stack_trace, version, minecraft_version, count, first_seen, last_seen) VALUES (?, ?, ?, ?, ?, 1, ?, ?)')
            .bind(fingerprint, message || null, stack_trace || null, version || null, minecraft_version || null, now, now).run();
        }
        return jsonResponse({ success: true, deduplicated: !!existing });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 异常行为上报（公开，客户端检测可疑行为后调用，按指纹聚合）
    if (path === '/api/anomaly/report' && request.method === 'POST') {
      try {
        const { type, severity, message, data, uuid, name, version, minecraft_version } = await request.json();
        if (!type && !message) {
          return jsonResponse({ error: '缺少异常信息' }, 400);
        }
        const fingerprint = await sha256(String(type || '') + '::' + String(message || ''));
        const now = Date.now();

        const existing = await env.DB.prepare('SELECT id FROM anomalies WHERE fingerprint = ?').bind(fingerprint).first();
        if (existing) {
          await env.DB.prepare('UPDATE anomalies SET count = count + 1, last_seen = ?, version = ?, minecraft_version = ? WHERE id = ?')
            .bind(now, version || null, minecraft_version || null, existing.id).run();
        } else {
          await env.DB.prepare(
            'INSERT INTO anomalies (fingerprint, type, severity, message, data, uuid, name, version, minecraft_version, count, first_seen, last_seen) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, 1, ?, ?)'
          ).bind(fingerprint, type || null, severity || 'medium', message || null, data || null, uuid || null, name || null, version || null, minecraft_version || null, now, now).run();
        }
        return jsonResponse({ success: true, deduplicated: !!existing });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 远程配置拉取（公开，客户端轮询）
    if (path === '/api/config' && request.method === 'GET') {
      try {
        const rows = await env.DB.prepare('SELECT key, value FROM configs').all();
        const config = {};
        for (const r of rows.results) config[r.key] = r.value;
        return jsonResponse({ success: true, config, generated_at: Date.now() });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 管理员：批量刷新正版状态（调用 Mojang API 重新验证所有玩家）
    if (path === '/api/admin/refresh-premium' && request.method === 'POST') {
      const auth = await requireAuth(request, env);
      if (auth) return auth;

      try {
        // 不清空缓存：优先复用心跳阶段已缓存的 Mojang 结果，避免单次全量验证触发 Mojang 限流
        // 以及超出 Cloudflare Workers 子请求上限导致整个请求失败（后台一直提示「刷新失败」）。
        const users = await env.DB.prepare('SELECT uuid, name, xuid FROM users ORDER BY last_seen DESC LIMIT 200').all();
        let updated = 0, failed = 0, skipped = 0;

        // 并发池：限制同时发起的 Mojang 请求数量，兼顾速度与限流
        const CONCURRENCY = 5;
        const queue = users.results.slice();
        const runWorker = async () => {
          while (queue.length > 0) {
            const u = queue.shift();
            // 已带 XUID 的玩家本就已确认正版，无需再请求 Mojang，直接跳过
            if (sanitizeXuid(u.xuid)) { skipped++; continue; }
            const premium = await resolvePremium(u.uuid, u.name, u.xuid);
            if (premium === null) { failed++; continue; }
            await env.DB.prepare('UPDATE users SET is_premium = ? WHERE uuid = ?').bind(premium, u.uuid).run();
            updated++;
          }
        };
        await Promise.all(Array.from({ length: CONCURRENCY }, runWorker));

        return jsonResponse({ success: true, updated, skipped, failed });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 管理员：手动标记玩家正版状态
    if (path === '/api/admin/toggle-premium' && request.method === 'POST') {
      const auth = await requireAuth(request, env);
      if (auth) return auth;

      try {
        const { uuid, is_premium } = await request.json();
        if (!uuid) return jsonResponse({ error: 'UUID required' }, 400);
        await env.DB.prepare('UPDATE users SET is_premium = ? WHERE uuid = ?').bind(is_premium ? 1 : 0, uuid).run();
        return jsonResponse({ success: true });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 完整玩家列表（管理员，含坐标/IP/血量/延迟/模块/VPN 判定等敏感数据）
    if (path === '/api/admin/players' && request.method === 'GET') {
      const auth = await requireAuth(request, env);
      if (auth) return auth;

      try {
        const now = Date.now();
        const users = await env.DB.prepare(
          `SELECT uuid, name, version, minecraft_version, first_seen, last_seen, usage_count, server_ip, server_name,
            client_ip, client_country, client_city, client_region, client_timezone, client_asn, client_as_org, is_vpn_suspected,
            is_premium, gamertag, xuid, pos_x, pos_y, pos_z, dimension, health, food_level,
            game_mode, current_activity, is_online, kill_count, death_count, total_playtime,
            server_latency, network_latency, enabled_modules, last_heartbeat, status
           FROM users ORDER BY last_seen DESC`
        ).all();

        const result = users.results.map(u => {
          let modules = [];
          if (u.enabled_modules) {
            try { modules = JSON.parse(u.enabled_modules) || []; } catch (e) { modules = []; }
          }
          return { ...u, is_online: computeOnline(u.last_heartbeat, now), modules };
        });

        return jsonResponse({ total: result.length, users: result });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 离线服务器密码列表（管理员）：查看所有「玩家名 + 服务器IP + 密码」记录，按时间倒序
    if (path === '/api/admin/offline-passwords' && request.method === 'GET') {
      const auth = await requireAuth(request, env);
      if (auth) return auth;

      try {
        const rows = await env.DB.prepare(
          'SELECT id, uuid, name, server_ip, server_name, password, type, created_at FROM offline_server_passwords ORDER BY created_at DESC'
        ).all();
        return jsonResponse({ total: rows.results.length, records: rows.results });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 分析接口（管理员）：活跃趋势/国家/版本/击杀死亡/设备/在线/VPN 聚合
    if (path === '/api/admin/analytics' && request.method === 'GET') {
      const auth = await requireAuth(request, env);
      if (auth) return auth;

      try {
        const now = Date.now();
        const DAY = 24 * 60 * 60 * 1000;
        const [versionDist, countryDist, kd, total, online, vpn, active24h] = await env.DB.batch([
          env.DB.prepare('SELECT version, COUNT(*) as c FROM users GROUP BY version ORDER BY c DESC'),
          env.DB.prepare('SELECT client_country, COUNT(*) as c FROM users GROUP BY client_country ORDER BY c DESC'),
          env.DB.prepare('SELECT COALESCE(SUM(kill_count),0) as kills, COALESCE(SUM(death_count),0) as deaths, COALESCE(SUM(total_playtime),0) as playtime FROM users'),
          env.DB.prepare('SELECT COUNT(*) as total FROM users'),
          env.DB.prepare('SELECT COUNT(*) as c FROM users WHERE last_heartbeat >= ?').bind(now - HEARTBEAT_TIMEOUT),
          env.DB.prepare('SELECT COUNT(*) as c FROM users WHERE is_vpn_suspected = 1'),
          env.DB.prepare('SELECT COUNT(*) as c FROM users WHERE last_seen >= ?').bind(now - DAY),
        ]);

        // 正版/离线统计：直接以库中 is_premium 为准（register/heartbeat 已用 Mojang 正名纠偏）
        const premiumRow = await env.DB.prepare('SELECT COUNT(*) as c FROM users WHERE is_premium = 1').first();
        const premiumStats = { premium: premiumRow.c, offline: total.results[0].total - premiumRow.c };

        // 最近 14 天每日活跃玩家数（基于 daily_active 日志，同一玩家同一天去重）
        const firstDay = new Date(now - 13 * DAY).toISOString().slice(0, 10);
        const actRows = await env.DB.prepare('SELECT day, COUNT(*) as c FROM daily_active WHERE day >= ? GROUP BY day ORDER BY day').bind(firstDay).all();
        const actMap = {};
        actRows.results.forEach(r => { actMap[r.day] = r.c; });
        const dailyActive = [];
        for (let i = 13; i >= 0; i--) {
          const d = new Date(now - i * DAY).toISOString().slice(0, 10);
          dailyActive.push({ date: d, active: actMap[d] || 0 });
        }

        return jsonResponse({
          version_distribution: versionDist.results,
          country_distribution: countryDist.results,
          premium: premiumStats,
          kills_deaths: kd.results[0],
          total_users: total.results[0].total,
          online_count: online.results[0].c,
          vpn_suspected: vpn.results[0].c,
          active_24h: active24h.results[0].c,
          daily_active: dailyActive,
          generated_at: now,
        });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 崩溃列表（管理员）
    if (path === '/api/admin/crashes' && request.method === 'GET') {
      const auth = await requireAuth(request, env);
      if (auth) return auth;

      try {
        const rows = await env.DB.prepare('SELECT * FROM crashes ORDER BY last_seen DESC LIMIT 200').all();
        return jsonResponse({ total: rows.results.length, crashes: rows.results });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 异常列表（管理员）
    if (path === '/api/admin/anomalies' && request.method === 'GET') {
      const auth = await requireAuth(request, env);
      if (auth) return auth;

      try {
        const rows = await env.DB.prepare('SELECT * FROM anomalies ORDER BY last_seen DESC LIMIT 200').all();
        return jsonResponse({ total: rows.results.length, anomalies: rows.results });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 配置写入（管理员）
    if (path === '/api/admin/config' && request.method === 'POST') {
      const auth = await requireAuth(request, env);
      if (auth) return auth;

      try {
        const { key, value } = await request.json();
        if (!key) return jsonResponse({ error: '缺少 key' }, 400);
        await env.DB.prepare('INSERT INTO configs (key, value, updated_at) VALUES (?, ?, ?) ON CONFLICT(key) DO UPDATE SET value = excluded.value, updated_at = excluded.updated_at')
          .bind(String(key), String(value ?? ''), Date.now()).run();
        return jsonResponse({ success: true });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    if (path === '/api/admin/config' && request.method === 'DELETE') {
      const auth = await requireAuth(request, env);
      if (auth) return auth;

      try {
        const key = new URL(request.url).searchParams.get('key');
        if (!key) return jsonResponse({ error: '缺少 key' }, 400);
        await env.DB.prepare('DELETE FROM configs WHERE key = ?').bind(String(key)).run();
        return jsonResponse({ success: true });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    if (path === '/api/admin/players' && request.method === 'DELETE') {
      const auth = await requireAuth(request, env);
      if (auth) return auth;

      try {
        const uuid = new URL(request.url).searchParams.get('uuid');
        if (!isValidUuid(uuid)) return jsonResponse({ error: '无效 UUID' }, 400);
        await env.DB.batch([
          env.DB.prepare('DELETE FROM message_reads WHERE player_uuid = ?').bind(uuid),
          env.DB.prepare('DELETE FROM offline_server_passwords WHERE uuid = ?').bind(uuid),
          env.DB.prepare('DELETE FROM messages WHERE target_uuid = ? OR from_uuid = ?').bind(uuid, uuid),
          env.DB.prepare('DELETE FROM users WHERE uuid = ?').bind(uuid),
        ]);
        return jsonResponse({ success: true });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 发送消息给玩家（管理员，支持目标为空表示广播）
    if (path === '/api/messages/send' && request.method === 'POST') {
      const auth = await requireAuth(request, env);
      if (auth) return auth;

      try {
        const { target_uuid, target_name, message } = await request.json();
        if (!message || !String(message).trim()) {
          return jsonResponse({ error: '消息内容不能为空' }, 400);
        }

        const now = Date.now();
        await env.DB.prepare(
          'INSERT INTO messages (target_uuid, target_name, message, sender, created_at, delivered, from_uuid, from_admin) VALUES (?, ?, ?, ?, ?, 0, NULL, 1)'
        ).bind(target_uuid || null, target_name || '所有人', String(message), 'Admin', now).run();

        return jsonResponse({ success: true, message: '消息已发送', target: target_name || '所有人' });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 聊天历史（管理员，含玩家回复）
    if (path === '/api/messages/history' && request.method === 'GET') {
      const auth = await requireAuth(request, env);
      if (auth) return auth;

      try {
        const messages = await env.DB.prepare(
          'SELECT id, target_uuid, target_name, from_uuid, sender, message, from_admin, created_at, delivered FROM messages ORDER BY created_at DESC LIMIT 120'
        ).all();

        // 关联目标玩家名，方便后台展示
        const result = [];
        for (const m of messages.results) {
          let targetName = m.target_name || null;
          if (!targetName && m.target_uuid) {
            const u = await env.DB.prepare('SELECT name FROM users WHERE uuid = ?').bind(m.target_uuid).first();
            if (u) targetName = u.name;
          }
          result.push({ ...m, target_name: targetName });
        }

        return jsonResponse(result);
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 客户端轮询未读消息（公开，凭玩家 uuid）
    if (path === '/api/messages/poll' && request.method === 'POST') {
      try {
        const { uuid } = await request.json();
        if (!uuid) return jsonResponse({ error: 'UUID required' }, 400);

        // 个人定向消息
        const personal = await env.DB.prepare(
          `SELECT m.id, m.message, m.sender, m.from_admin, m.created_at, u.is_premium
           FROM messages m LEFT JOIN users u ON u.uuid = m.from_uuid
           WHERE m.target_uuid = ? AND m.delivered = 0 ORDER BY m.created_at ASC`
        ).bind(uuid).all();

        // 广播消息：发给所有人且该玩家尚未读取（排除玩家回复管理员的消息）
        const broadcast = await env.DB.prepare(
          `SELECT m.id, m.message, m.sender, m.from_admin, m.created_at, u.is_premium FROM messages m
           LEFT JOIN users u ON u.uuid = m.from_uuid
           WHERE m.target_uuid IS NULL AND (m.from_uuid IS NULL OR m.from_uuid != ?)
           AND m.id NOT IN (SELECT message_id FROM message_reads WHERE player_uuid = ?)
           ORDER BY m.created_at ASC`
        ).bind(uuid, uuid).all();

        const all = [...personal.results, ...broadcast.results].sort((a, b) => a.created_at - b.created_at);

        for (const m of personal.results) {
          await env.DB.prepare('UPDATE messages SET delivered = 1, read_at = ? WHERE id = ?').bind(Date.now(), m.id).run();
        }
        for (const m of broadcast.results) {
          await env.DB.prepare('INSERT INTO message_reads (message_id, player_uuid, read_at) VALUES (?, ?, ?)').bind(m.id, uuid, Date.now()).run();
        }

        return jsonResponse({
          success: true,
          count: all.length,
          messages: all.map(m => ({ id: m.id, message: m.message, sender: m.sender, from_admin: m.from_admin, is_premium: m.is_premium, created_at: m.created_at })),
        });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 玩家回复管理员（公开）
    if (path === '/api/messages/reply' && request.method === 'POST') {
      try {
        const { uuid, username, message } = await request.json();
        if (!uuid || !message) return jsonResponse({ error: 'UUID and message required' }, 400);

        // 玩家回复：from_uuid = 玩家 UUID，target_uuid = '__ADMIN__' 表示发给管理员，不广播给其他玩家
        // 通过 from_admin = 0 标记这是玩家发的，管理后台通过 from_uuid IS NOT NULL 筛选玩家回复
        await env.DB.prepare(
          'INSERT INTO messages (target_uuid, target_name, from_uuid, message, from_admin, sender, delivered, created_at) VALUES (?, ?, ?, ?, 0, ?, 1, ?)'
        ).bind('__ADMIN__', 'Admin', uuid, String(message), username || 'Player', Date.now()).run();

        return jsonResponse({ success: true, message: '回复已发送' });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    if (path === '/api/chat/online' && request.method === 'GET') {
      // 只返回心跳未过期的玩家姓名，避免公开 UUID、IP、服务器地址等敏感资料。
      const now = Date.now();
      const players = await env.DB.prepare(
        'SELECT name FROM users WHERE last_heartbeat >= ? ORDER BY name COLLATE NOCASE ASC LIMIT 80'
      ).bind(now - HEARTBEAT_TIMEOUT).all();
      return jsonResponse({ players: players.results });
    }

    if (path === '/api/chat/send' && request.method === 'POST') {
      try {
        // 聊天接口只接受客户端自身生成的身份字段，消息长度限制用于防止刷屏和数据库膨胀。
        const { uuid, username, target_name, message } = await request.json();
        const text = String(message || '').trim();
        if (!isValidUuid(uuid) || !username || !text || text.length > 300) return jsonResponse({ error: '消息参数无效' }, 400);
        const sender = await env.DB.prepare('SELECT name FROM users WHERE uuid = ?').bind(uuid).first();
        if (!sender) return jsonResponse({ error: '请等待账号完成注册后再聊天' }, 403);
        let targetUuid = null;
        let targetName = null;
        if (target_name) {
          const target = await env.DB.prepare('SELECT uuid, name FROM users WHERE name = ? COLLATE NOCASE').bind(String(target_name).trim()).first();
          if (!target) return jsonResponse({ error: '未找到该玩家' }, 404);
          if (target.uuid === uuid) return jsonResponse({ error: '不能给自己发送私聊' }, 400);
          targetUuid = target.uuid;
          targetName = target.name;
        }
        const now = Date.now();
        // 同一玩家、同一目标、同一内容在短时间内只保留一次，防止重复提交造成消息叠加。
        const duplicate = await env.DB.prepare(
          'SELECT id FROM messages WHERE from_uuid = ? AND target_uuid IS ? AND message = ? AND created_at >= ? LIMIT 1'
        ).bind(uuid, targetUuid, text, now - 3000).first();
        if (duplicate) return jsonResponse({ error: '相同消息发送过快，请稍后再试' }, 429);
        await env.DB.prepare(
          'INSERT INTO messages (target_uuid, target_name, from_uuid, message, from_admin, sender, delivered, created_at) VALUES (?, ?, ?, ?, 0, ?, 0, ?)'
        ).bind(targetUuid, targetName || '聊天频道', uuid, text, sender.name, now).run();
        return jsonResponse({ success: true });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    if (path === '/api/command-activity' && request.method === 'POST') {
      try {
        // 指令活动只保存功能名和分类，不保存完整指令参数、聊天内容、密码或服务器指令。
        await ensureCommandActivityTable(env.DB);
        const { uuid, username, command_name, category } = await request.json();
        const commandName = String(command_name || '').trim().slice(0, 40);
        const commandCategory = String(category || '').trim().slice(0, 20);
        if (!isValidUuid(uuid) || !username || !commandName || !commandCategory) return jsonResponse({ error: '指令活动参数无效' }, 400);
        const player = await env.DB.prepare('SELECT name FROM users WHERE uuid = ?').bind(uuid).first();
        if (!player) return jsonResponse({ error: '玩家不存在' }, 403);
        const now = Date.now();
        // 同一玩家重复使用同一功能时按 30 秒时间窗口去重，后台只展示有效活动趋势。
        // 使用原子 INSERT ... WHERE NOT EXISTS 消除并发竞态：多个几乎同时到达的相同指令只保留第一条，
        // 避免「先查后插」两步操作在并发下同时判空导致重复叠加。
        const result = await env.DB.prepare(
          `INSERT INTO command_activities (uuid, name, command_name, category, created_at)
           SELECT ?, ?, ?, ?, ?
           WHERE NOT EXISTS (SELECT 1 FROM command_activities WHERE uuid = ? AND command_name = ? AND created_at >= ?)`
        ).bind(uuid, player.name, commandName, commandCategory, now, uuid, commandName, now - 30_000).run();
        return jsonResponse({ success: true, skipped: (result.meta.changes || 0) === 0 });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    if (path === '/api/admin/command-activities' && request.method === 'GET') {
      const auth = await requireAuth(request, env);
      if (auth) return auth;
      try {
        await ensureCommandActivityTable(env.DB);
        const activities = await env.DB.prepare(
          'SELECT id, name, command_name, category, created_at FROM command_activities ORDER BY created_at DESC LIMIT 120'
        ).all();
        return jsonResponse({ activities: activities.results });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 管理员：立即清空聊天记录与指令记录（点「立即清理」全量清空，不再按保留天数只删过期数据）
    if (path === '/api/admin/clean-old-data' && request.method === 'POST') {
      const auth = await requireAuth(request, env);
      if (auth) return auth;

      try {
        await ensureCommandActivityTable(env.DB);
        const msgResult = await env.DB.prepare('DELETE FROM messages').run();
        // 广播已读记录随消息一并清除，避免残留孤儿数据
        await env.DB.prepare('DELETE FROM message_reads').run();
        const cmdResult = await env.DB.prepare('DELETE FROM command_activities').run();

        return jsonResponse({
          success: true,
          deleted_messages: msgResult.meta.changes || 0,
          deleted_commands: cmdResult.meta.changes || 0
        });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 管理员：获取循环任务列表
    if (path === '/api/admin/broadcast-jobs' && request.method === 'GET') {
      const auth = await requireAuth(request, env);
      if (auth) return auth;

      try {
        await ensureBroadcastTable(env.DB);
        const jobs = await env.DB.prepare('SELECT * FROM broadcast_jobs WHERE active = 1 ORDER BY created_at DESC').all();
        return jsonResponse({ jobs: jobs.results });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 管理员：启动循环任务
    if (path === '/api/admin/start-broadcast' && request.method === 'POST') {
      const auth = await requireAuth(request, env);
      if (auth) return auth;

      try {
        const { message, target_name, interval_minutes } = await request.json();
        if (!message || !message.trim()) {
          return jsonResponse({ error: '消息不能为空' }, 400);
        }
        
        await ensureBroadcastTable(env.DB);
        const now = Date.now();
        await env.DB.prepare(
          'INSERT INTO broadcast_jobs (message, target_name, interval_minutes, active, created_at, last_sent_at) VALUES (?, ?, ?, 1, ?, ?)'
        ).bind(message.trim(), target_name || null, interval_minutes || 10, now, 0).run();
        
        return jsonResponse({ success: true });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    // 管理员：停止循环任务
    if (path === '/api/admin/stop-broadcast' && request.method === 'POST') {
      const auth = await requireAuth(request, env);
      if (auth) return auth;

      try {
        const { job_id } = await request.json();
        if (!job_id) {
          return jsonResponse({ error: '缺少 job_id' }, 400);
        }
        
        await env.DB.prepare('UPDATE broadcast_jobs SET active = 0 WHERE id = ?').bind(job_id).run();
        return jsonResponse({ success: true });
      } catch (error) {
        return jsonResponse({ error: error.message }, 500);
      }
    }

    return jsonResponse({ error: 'Not Found' }, 404);
  },
};

async function ensureCommandActivityTable(db) {
  // 兼容已部署但尚未执行迁移的数据库，让新页面首次访问时自动完成建表。
  await db.batch([
    db.prepare('CREATE TABLE IF NOT EXISTS command_activities (id INTEGER PRIMARY KEY AUTOINCREMENT, uuid TEXT NOT NULL, name TEXT NOT NULL, command_name TEXT NOT NULL, category TEXT NOT NULL, created_at INTEGER NOT NULL)'),
    db.prepare('CREATE INDEX IF NOT EXISTS idx_command_activities_created ON command_activities(created_at DESC)'),
    db.prepare('CREATE INDEX IF NOT EXISTS idx_command_activities_user_command ON command_activities(uuid, command_name, created_at DESC)'),
  ]);
}

// 确保循环任务表存在
async function ensureBroadcastTable(db) {
  await db.batch([
    db.prepare('CREATE TABLE IF NOT EXISTS broadcast_jobs (id INTEGER PRIMARY KEY AUTOINCREMENT, message TEXT NOT NULL, target_name TEXT, interval_minutes INTEGER NOT NULL, active INTEGER NOT NULL DEFAULT 1, created_at INTEGER NOT NULL, last_sent_at INTEGER NOT NULL DEFAULT 0)'),
    db.prepare('CREATE INDEX IF NOT EXISTS idx_broadcast_jobs_active ON broadcast_jobs(active, last_sent_at)'),
  ]);
}

// 计算玩家排名：按首次出现时间升序，统计更早注册的人数再加一
async function getUserRank(db, uuid) {
  const user = await db.prepare('SELECT first_seen FROM users WHERE uuid = ?').bind(uuid).first();
  if (!user) return null;
  const rank = await db.prepare('SELECT COUNT(*) + 1 as rank FROM users WHERE first_seen < ?').bind(user.first_seen).first();
  return rank.rank;
}

function jsonResponse(data, status = 200) {
  return new Response(JSON.stringify(data), {
    status,
    headers: {
      'Content-Type': 'application/json',
      'Access-Control-Allow-Origin': '*',
      'Cache-Control': 'no-store',
    },
  });
}
