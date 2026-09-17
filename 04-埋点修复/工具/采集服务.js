/**
 * 埋点修复 · 采集服务（零依赖，Node ≥ 18）
 *
 * 干什么
 *   接收客户端埋点发来的事件，按「会话名」逐行落成 ndjson，并对外提供查询 / 导出接口，
 *   供《工具/分析.js》、浏览器或任意 HTTP 客户端读取。本服务与业务无耦合，随用随停。
 *
 * 用法
 *   node 04-埋点修复/工具/采集服务.js --案例 2026-09-17-卸货后RTP秒破连锁失效
 *   node 04-埋点修复/工具/采集服务.js --目录 04-埋点修复/进行中/某个案 --端口 7777
 *
 *   参数
 *     --案例 <文件夹名>   在 04-埋点修复/进行中/ 或 已修复/ 下找同名案例；找不到会自动创建（并复制模板）
 *     --目录 <路径>       直接指定案例目录（优先于 --案例）
 *     --端口 <数字>       默认 7777（与开发习惯.md 第三十三章的埋点契约一致）
 *     --详细              连心跳事件也逐条打印（默认每 100 条心跳汇总一行）
 *     --安静              完全不打事件行，只打启动 / 关闭摘要
 *
 * 事件契约（Java 侧按这个发包，骨架见 开发习惯.md 第三十三章）
 *   POST /event
 *     {"run":"修复前","tag":"A","location":"秒破.tick","msg":"heartbeat","data":{"tick":1200}}
 *   - run      会话名：同一次采集一个名字（修复前 / 修复后 / 复现2），落盘到 日志-<会话名>.ndjson
 *   - tag      探针代号（A / B / C …），用于快速筛选
 *   - location 埋点位置，建议「模块.方法」形式，便于按位置聚合
 *   - msg      事件名（英文短标识，脚本聚合友好）
 *   - data     任意 JSON 对象
 *   服务端会自动补 ts（毫秒时间戳）与 seq（每会话自增序号）。
 *
 * 路由
 *   POST /event                  单条事件
 *   POST /events                 批量（JSON 数组）
 *   POST /mark                   打标记，body {"run":"修复前","label":"复现开始"}
 *   POST /clear?run=<会话名>      清空该会话（默认先备份成 .bak-<时间戳>；?备份=0 则不备份）
 *   GET  /logs?run=&tag=&location=&msg=&since=&limit=&tail=&raw=1
 *   GET  /runs                   列出所有会话（条数 / 起止时间）
 *   GET  /export?run=<会话名>     导出原始 ndjson
 *   GET  /health                 存活与计数
 */

'use strict';

const http = require('node:http');
const fs = require('node:fs');
const path = require('node:path');
const 公共 = require('./公共.js');

// ════════════════════════════ 命令行参数 ════════════════════════════

const 参数 = 公共.解析参数(process.argv.slice(2), { port: '端口', dir: '目录', case: '案例', quiet: '安静', verbose: '详细' });
const 埋点根目录 = 公共.埋点根目录;                          // 04-埋点修复/
const 案例模板 = path.join(埋点根目录, '案例模板.md');
const 端口 = Number(参数.端口 || 7777);
const 端口上限 = 65535;

if (!Number.isInteger(端口) || 端口 <= 0 || 端口 > 端口上限) {
    console.error(`端口不合法：${参数.端口}（应为 1~${端口上限} 的整数）`);
    process.exit(1);
}

/** 列出某个分类目录下的案例文件夹名。 */
const 列出案例 = 公共.列出案例;

/** 只有一个进行中案例时不必再输 --案例。 */
function 自动挑案例() {
    const 进行中 = 列出案例('进行中');
    if (进行中.length === 1) return 进行中[0];
    console.error('无法确定案例目录，请用 --案例 <文件夹名> 指定。');
    console.error(`  进行中：${进行中.length ? 进行中.join('、') : '（无）'}`);
    console.error(`  已修复：${列出案例('已修复').join('、') || '（无）'}`);
    process.exit(1);
}

/** 定位（必要时创建）案例目录。 */
function 定位案例目录() {
    if (参数.目录) {
        const 目录 = path.resolve(参数.目录);
        if (!fs.existsSync(目录)) {
            console.error(`--目录 指向的路径不存在：${目录}`);
            process.exit(1);
        }
        return 目录;
    }

    const 案例名 = 参数.案例 || 自动挑案例();
    for (const 分类 of ['进行中', '已修复']) {
        const 候选 = path.join(埋点根目录, 分类, 案例名);
        if (fs.existsSync(候选)) return 候选;
    }

    // 没找到就按「进行中」建一个，顺手把案例模板复制成 README，省得每次手抄
    const 新目录 = path.join(埋点根目录, '进行中', 案例名);
    fs.mkdirSync(新目录, { recursive: true });
    if (fs.existsSync(案例模板)) {
        fs.copyFileSync(案例模板, path.join(新目录, 'README.md'));
    }
    console.log(`已创建案例目录：${新目录}`);
    return 新目录;
}

const 案例目录 = 定位案例目录();

// ════════════════════════════ 落盘 ════════════════════════════

/** 会话名 → { 文件, 流, 序号 }。一个会话一个文件，互不干扰。 */
const 会话表 = new Map();

/** 会话名只允许中英文、数字、下划线、连字符，避免拼出非法文件名。 */
function 规范化会话名(原始) {
    const 名 = String(原始 ?? '默认').trim() || '默认';
    return 名.replace(/[\\/:*?"<>|\s]+/g, '_');
}

/** 数一下已落盘多少行，让 seq 重启后接着往后编。 */
function 统计行数(文件) {
    if (!fs.existsSync(文件)) return 0;
    const 内容 = fs.readFileSync(文件, 'utf8');
    let 条数 = 0;
    for (const 行 of 内容.split('\n')) if (行.trim()) 条数++;
    return 条数;
}

function 取会话(原始名) {
    const 会话名 = 规范化会话名(原始名);
    let 会话 = 会话表.get(会话名);
    if (!会话) {
        const 文件 = path.join(案例目录, `日志-${会话名}.ndjson`);
        会话 = {
            会话名,
            文件,
            流: fs.createWriteStream(文件, { flags: 'a', encoding: 'utf8' }),
            序号: 统计行数(文件),
        };
        会话表.set(会话名, 会话);
        console.log(`[会话] ${会话名} → ${路径简称(文件)}（已有 ${会话.序号} 条）`);
    }
    return 会话;
}

/** 打印用短路径，太长时只留最后两段。 */
function 路径简称(文件) {
    const 相对 = path.relative(process.cwd(), 文件);
    const 段 = 相对.split(path.sep);
    return 段.length <= 3 ? 相对 : `…${path.sep}${段.slice(-2).join(path.sep)}`;
}

/** 写一条事件：补 ts / seq，落盘一行。 */
function 落一条(会话, 原始事件) {
    const 事件 = {
        seq: ++会话.序号,
        ts: Number(原始事件.ts) > 0 ? Number(原始事件.ts) : Date.now(),
        run: 会话.会话名,
        tag: String(原始事件.tag ?? ''),
        location: String(原始事件.location ?? ''),
        msg: String(原始事件.msg ?? ''),
        data: 原始事件.data && typeof 原始事件.data === 'object' ? 原始事件.data : {},
    };
    会话.流.write(JSON.stringify(事件) + '\n');
    事件计数++;
    最后事件时间 = 事件.ts;
    if (!参数.开关.has('安静')) 打印事件(事件);
    return 事件;
}

let 事件计数 = 0;
let 最后事件时间 = 0;
let 心跳计数 = 0;

/** 心跳事件默认不刷屏：每 100 条汇总一行（--详细 可逐条看）。 */
function 打印事件(事件) {
    const 时刻 = 公共.格式化时刻(事件.ts);
    if (事件.msg === 'heartbeat' && !参数.开关.has('详细')) {
        心跳计数++;
        if (心跳计数 % 100 === 0) console.log(`${时刻} …心跳累计 ${心跳计数} 条`);
        return;
    }
    const 摘要 = JSON.stringify(事件.data);
    console.log(`${时刻} [${事件.tag}] ${事件.location} ${事件.msg} ${摘要.length > 160 ? 摘要.slice(0, 160) + '…' : 摘要}`);
}

/** 读一个会话的全部事件（按需重读文件：查询总是拿到最新、最真实的内容）。 */
function 读会话事件(会话名) {
    return 公共.读事件文件(path.join(案例目录, `日志-${规范化会话名(会话名)}.ndjson`));
}

/** 列出所有会话：条数 + 起止时间。 */
function 列出全部会话() {
    if (!fs.existsSync(案例目录)) return [];
    return fs.readdirSync(案例目录)
        .filter(名 => 名.startsWith('日志-') && 名.endsWith('.ndjson'))
        .map(名 => {
            const 会话名 = 名.slice('日志-'.length, -'.ndjson'.length);
            const 事件列表 = 读会话事件(会话名);
            return {
                run: 会话名,
                条数: 事件列表.length,
                起点: 事件列表.length ? 事件列表[0].ts : 0,
                终点: 事件列表.length ? 事件列表[事件列表.length - 1].ts : 0,
                文件: 名,
            };
        })
        .sort((甲, 乙) => 甲.起点 - 乙.起点);
}

/** 按 query 过滤事件。 */
function 过滤事件(事件列表, 查询) {
    let 结果 = 事件列表;
    if (查询.tag) 结果 = 结果.filter(事件 => 事件.tag === 查询.tag);
    if (查询.location) 结果 = 结果.filter(事件 => 事件.location.includes(查询.location));
    if (查询.msg) 结果 = 结果.filter(事件 => 事件.msg === 查询.msg);
    if (查询.since) 结果 = 结果.filter(事件 => 事件.ts >= Number(查询.since));
    if (查询.until) 结果 = 结果.filter(事件 => 事件.ts <= Number(查询.until));

    const 取尾部 = Number(查询.tail) || 0;
    if (取尾部 > 0) 结果 = 结果.slice(-取尾部);
    const 取前部 = Number(查询.limit) || 0;
    if (取前部 > 0) 结果 = 结果.slice(0, 取前部);
    return 结果;
}

/** 清空一个会话：先把旧内容备份（默认），再重新开始。 */
function 清空会话(会话名, 是否备份) {
    const 名 = 规范化会话名(会话名);
    const 会话 = 会话表.get(名);
    const 文件 = path.join(案例目录, `日志-${名}.ndjson`);
    const 收尾 = () => {
        if (!fs.existsSync(文件)) return '文件不存在';
        if (是否备份) {
            const 备份 = `${文件}.bak-${Date.now()}`;
            fs.renameSync(文件, 备份);
            return `已备份到 ${path.basename(备份)}`;
        }
        fs.rmSync(文件);
        return '已删除（未备份）';
    };
    if (会话) {
        const { 流 } = 会话;
        会话表.delete(名);
        return new Promise(解决 => 流.end(() => 解决(收尾())));
    }
    return Promise.resolve(收尾());
}

// ════════════════════════════ HTTP ════════════════════════════

function 回JSON(应答, 状态码, 内容) {
    应答.writeHead(状态码, { 'Content-Type': 'application/json; charset=utf-8', 'Cache-Control': 'no-store' });
    应答.end(JSON.stringify(内容));
}

function 回文本(应答, 状态码, 内容, 类型 = 'text/plain; charset=utf-8') {
    应答.writeHead(状态码, { 'Content-Type': 类型, 'Cache-Control': 'no-store' });
    应答.end(内容);
}

/** 读完请求体（上限 8MB，防误发巨物把内存吃光）。 */
function 读请求体(请求) {
    return new Promise((解决, 拒绝) => {
        const 片段 = [];
        let 长度 = 0;
        请求.on('data', 块 => {
            长度 += 块.length;
            if (长度 > 8 * 1024 * 1024) {
                请求.destroy();
                拒绝(new Error('请求体过大（>8MB）'));
                return;
            }
            片段.push(块);
        });
        请求.on('end', () => 解决(Buffer.concat(片段).toString('utf8')));
        请求.on('error', 拒绝);
    });
}

const 启动时刻 = Date.now();
const 服务 = http.createServer(async (请求, 应答) => {
    const 地址 = new URL(请求.url, `http://${请求.headers.host || '127.0.0.1'}`);
    const 路由 = `${请求.method} ${地址.pathname}`;
    const 查询 = Object.fromEntries(地址.searchParams.entries());

    try {
        if (请求.method === 'OPTIONS') {
            应答.writeHead(204, { 'Access-Control-Allow-Origin': '*', 'Access-Control-Allow-Headers': '*', 'Access-Control-Allow-Methods': 'GET,POST,OPTIONS' });
            应答.end();
            return;
        }

        switch (路由) {
            case 'POST /event': {
                const 正文 = await 读请求体(请求);
                const 事件 = JSON.parse(正文 || '{}');
                const 会话 = 取会话(事件.run ?? 查询.run);
                落一条(会话, 事件);
                回JSON(应答, 200, { 成功: true, 会话: 会话.会话名, 序号: 会话.序号 });
                return;
            }

            case 'POST /events': {
                const 正文 = await 读请求体(请求);
                const 列表 = JSON.parse(正文 || '[]');
                if (!Array.isArray(列表)) {
                    回JSON(应答, 400, { 成功: false, 错误: '批量接口需要 JSON 数组' });
                    return;
                }
                for (const 事件 of 列表) 落一条(取会话(事件.run ?? 查询.run), 事件);
                回JSON(应答, 200, { 成功: true, 条数: 列表.length });
                return;
            }

            case 'POST /mark': {
                const 正文 = await 读请求体(请求);
                const 内容 = JSON.parse(正文 || '{}');
                const 会话 = 取会话(内容.run ?? 查询.run);
                const 事件 = 落一条(会话, { tag: 'M', location: '标记', msg: 'mark', data: { label: String(内容.label ?? '') } });
                console.log(`──────── 标记：${事件.data.label}（${会话.会话名} #${事件.seq}）────────`);
                回JSON(应答, 200, { 成功: true, 标记: 事件.data.label, 序号: 事件.seq });
                return;
            }

            case 'POST /clear': {
                const 会话名 = 查询.run || 查询.会话;
                if (!会话名) {
                    回JSON(应答, 400, { 成功: false, 错误: '需要 run 参数指定会话名' });
                    return;
                }
                const 说明 = await 清空会话(会话名, 查询.备份 !== '0');
                回JSON(应答, 200, { 成功: true, 会话: 规范化会话名(会话名), 说明 });
                return;
            }

            case 'GET /runs':
                回JSON(应答, 200, { 案例目录, 会话: 列出全部会话() });
                return;

            case 'GET /logs': {
                const 会话名 = 查询.run || 查询.会话 || 默认会话名();
                const 事件列表 = 过滤事件(读会话事件(会话名), 查询);
                if (查询.raw === '1') {
                    回文本(应答, 200, 事件列表.map(事件 => JSON.stringify(事件)).join('\n') + '\n');
                    return;
                }
                回JSON(应答, 200, 事件列表);
                return;
            }

            case 'GET /export': {
                const 会话名 = 查询.run || 查询.会话 || 默认会话名();
                const 文件 = path.join(案例目录, `日志-${规范化会话名(会话名)}.ndjson`);
                if (!fs.existsSync(文件)) {
                    回JSON(应答, 404, { 成功: false, 错误: `会话不存在：${会话名}` });
                    return;
                }
                回文本(应答, 200, fs.readFileSync(文件, 'utf8'), 'application/x-ndjson; charset=utf-8');
                return;
            }

            case 'GET /health':
                回JSON(应答, 200, {
                    成功: true,
                    端口,
                    案例目录,
                    运行毫秒: Date.now() - 启动时刻,
                    事件计数,
                    最后事件时间,
                    会话: 列出全部会话(),
                });
                return;

            default:
                回JSON(应答, 404, { 成功: false, 错误: `未知路由：${路由}`, 可用: 可用路由 });
        }
    } catch (异常) {
        回JSON(应答, 400, { 成功: false, 错误: String(异常 && 异常.message || 异常) });
    }
});

/** 只按已有会话挑一个默认会话名：优先「修复后」，否则最近一个。 */
function 默认会话名() {
    const 全部 = 列出全部会话();
    if (!全部.length) return '默认';
    const 修复后 = 全部.find(项 => 项.run === '修复后');
    return (修复后 || 全部[全部.length - 1]).run;
}

const 可用路由 = [
    'POST /event', 'POST /events', 'POST /mark', 'POST /clear',
    'GET /logs', 'GET /runs', 'GET /export', 'GET /health',
];

服务.on('error', 异常 => {
    if (异常.code === 'EADDRINUSE') {
        console.error(`端口 ${端口} 已被占用：可能已经有一个采集服务在跑。`);
        console.error('  换个端口：--端口 7788；或先停掉旧的那个（任务管理器里结束 node 进程）。');
    } else {
        console.error(`采集服务启动失败：${异常.message}`);
    }
    process.exit(1);
});

服务.listen(端口, '127.0.0.1', () => {
    console.log('埋点修复 · 采集服务已启动');
    console.log(`  监听      http://127.0.0.1:${端口}`);
    console.log(`  案例目录  ${案例目录}`);
    console.log(`  会话文件  日志-<会话名>.ndjson（会话名取自事件的 run 字段）`);
    console.log(`  常用接口  GET /health、GET /runs、GET /logs?run=修复后&tail=50`);
    console.log('  停止      在本窗口按 Ctrl+C');
});

/** 关服前把流刷干净，并打一份摘要（结案时直接抄进案例文档）。 */
function 收尾(信号) {
    console.log(`\n收到 ${信号}，正在收尾…`);
    const 待关闭 = [...会话表.values()].map(会话 => new Promise(解决 => 会话.流.end(解决)));
    Promise.all(待关闭).then(() => {
        console.log(`本次共采集 ${事件计数} 条事件，案例目录：${案例目录}`);
        for (const 项 of 列出全部会话()) {
            console.log(`  ${项.run}：${项.条数} 条（${公共.格式化时刻(项.起点)} → ${公共.格式化时刻(项.终点)}）`);
        }
        process.exit(0);
    });
}

process.on('SIGINT', () => 收尾('Ctrl+C'));
process.on('SIGTERM', () => 收尾('SIGTERM'));
