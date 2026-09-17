/**
 * 埋点修复 · 日志分析（零依赖，Node ≥ 18）
 *
 * 干什么
 *   把《采集服务.js》落下来的 ndjson 事件读成可读结论：总览、时间线、分组计数、
 *   心跳节奏、空档定位、会话对比。全部只读，不改任何文件。
 *
 * 用法
 *   node 04-埋点修复/工具/分析.js                                 总览（自动挑最新日志）
 *   node 04-埋点修复/工具/分析.js --列表                          列出所有日志文件（编号 / 条数 / 时间）
 *   node 04-埋点修复/工具/分析.js --案例 <文件夹名> --会话 修复后
 *   node 04-埋点修复/工具/分析.js --事件 begin --时间线            按事件名过滤后逐条打印
 *   node 04-埋点修复/工具/分析.js --分组 data.pos --前N 10         按字段分组计数（去重排名）
 *   node 04-埋点修复/工具/分析.js --空档 ts --最小间隔 500         找相邻事件间隔过大的「空档」
 *
 * 通用筛选（各模式都可用）
 *   --探针 A,B           探针代号
 *   --位置 秒破.begin    埋点位置（子串匹配，可多值）
 *   --事件 begin         事件名（精确匹配，可多值）
 *   --从 22:40 --到 23:00 时间范围（HH:mm:ss[.SSS] 或毫秒时间戳）
 *
 * 输出控制
 *   --完整               打印 data 时不做长度截断
 *   --JSON               以 JSON 输出结论（便于二次处理）
 */

'use strict';

const path = require('node:path');
const 公共 = require('./公共.js');

const 数据截断长度 = 200;
const 表格默认条数 = 20;
const 空档默认条数 = 40;
const 空档默认阈值 = 1000;

const 参数 = 公共.解析参数(process.argv.slice(2), {
    list: '列表', file: '文件', dir: '目录', case: '案例', run: '会话',
    tag: '探针', location: '位置', msg: '事件', since: '从', until: '到',
    group: '分组', top: '前N', timeline: '时间线', gaps: '空档', min: '最小间隔',
    full: '完整', json: 'JSON',
});
const 是开关 = 键 => 参数.开关.has(键);

// ════════════════════════════ 选定日志文件 ════════════════════════════

/** 解析 --从 / --到：支持 HH:mm:ss[.SSS]（当天）与毫秒时间戳。 */
function 解析时刻(文本, 基准毫秒) {
    if (文本 === undefined || 文本 === true || 文本 === '') return null;
    const 内容 = String(文本).trim();
    if (/^\d{10,}$/.test(内容)) return Number(内容);

    const 匹配 = 内容.match(/^(\d{1,2}):(\d{2})(?::(\d{2}))?(?:\.(\d{1,3}))?$/);
    if (!匹配) {
        console.error(`时间格式看不懂：${内容}（应为 22:40:12.345 或毫秒时间戳）`);
        process.exit(1);
    }
    const 日期 = new Date(基准毫秒 || Date.now());
    日期.setHours(Number(匹配[1]), Number(匹配[2]), Number(匹配[3] || 0), Number((匹配[4] || '0').padEnd(3, '0')));
    return 日期.getTime();
}

/** 定位案例目录：--目录 > --案例 > 最新日志所在目录。 */
function 定位案例目录() {
    if (参数.目录) return path.resolve(参数.目录);
    if (参数.案例) {
        const 目录 = 公共.找案例目录(参数.案例);
        if (!目录) {
            console.error(`找不到案例：${参数.案例}`);
            console.error(`  进行中：${公共.列出案例('进行中').join('、') || '（无）'}`);
            console.error(`  已修复：${公共.列出案例('已修复').join('、') || '（无）'}`);
            process.exit(1);
        }
        return 目录;
    }
    const 最新 = 公共.找最新日志();
    if (!最新) {
        console.error('没有找到任何日志文件。先开《采集服务.js》跑一次复现，或先用 --文件 指定。');
        process.exit(1);
    }
    return path.dirname(最新);
}

/** 定位日志文件：--文件 > --案例/--目录 + --会话 > 案例内最新。 */
function 定位日志文件(案例目录) {
    if (参数.文件) return path.resolve(参数.文件);
    const 全部 = 公共.列出日志文件(案例目录);
    if (!全部.length) {
        console.error(`案例目录里没有日志文件：${案例目录}`);
        process.exit(1);
    }
    if (参数.会话) {
        const 命中 = 全部.find(项 => 项.会话名 === 参数.会话);
        if (!命中) {
            console.error(`该案例里没有会话「${参数.会话}」，现有：${全部.map(项 => 项.会话名).join('、')}`);
            process.exit(1);
        }
        return 命中.文件;
    }
    return 全部[全部.length - 1].文件;
}

const 案例目录 = 定位案例目录();

// ── --列表：把所有日志文件摆出来，方便挑 --会话 ──
if (是开关('列表')) {
    const 全部 = 公共.列出日志文件(案例目录);
    if (!全部.length) {
        console.log(`案例目录里没有日志文件：${案例目录}`);
        process.exit(0);
    }
    console.log(`案例目录：${案例目录}`);
    console.log(公共.排表(['会话', '条数', '起', '止', '文件'], 全部.map(项 => [
        项.会话名, 项.条数, 公共.格式化时刻(项.起点), 公共.格式化时刻(项.终点), path.basename(项.文件),
    ])));
    process.exit(0);
}

const 日志文件 = 定位日志文件(案例目录);
const 全部事件 = 公共.读事件文件(日志文件);
if (!全部事件.length) {
    console.error(`日志是空的：${日志文件}`);
    process.exit(1);
}

// ════════════════════════════ 筛选 ════════════════════════════

const 从 = 解析时刻(参数.从, 全部事件[0].ts);
const 到 = 解析时刻(参数.到, 全部事件[0].ts);
const 探针集合 = 公共.取多值(参数, '探针');
const 位置列表 = 公共.取多值(参数, '位置');
const 事件列表 = 公共.取多值(参数, '事件');

/** 按命令行筛选事件。 */
function 筛选出的事件() {
    return 全部事件.filter(事件 => {
        if (探针集合.length && !探针集合.includes(事件.tag)) return false;
        if (位置列表.length && !位置列表.some(位置 => 事件.location.includes(位置))) return false;
        if (事件列表.length && !事件列表.includes(事件.msg)) return false;
        if (从 !== null && 事件.ts < 从) return false;
        if (到 !== null && 事件.ts > 到) return false;
        return true;
    });
}

const 命中事件 = 筛选出的事件();
const 有无筛选 = 探针集合.length || 位置列表.length || 事件列表.length || 从 !== null || 到 !== null;

if (!命中事件.length) {
    console.error(`筛选之后没有任何事件：${path.basename(日志文件)}（会话「${全部事件[0].run}」，共 ${全部事件.length} 条）`);
    console.error('  检查一下 --探针 / --位置 / --事件 / 时间范围，或换成别的会话：--会话 <名>（--列表 看有哪些）');
    process.exit(1);
}

/** 数值统计：条数 + 占比 或 间隔指标。 */
function 计数排序(取键函数) {
    const 计数表 = new Map();
    for (const 事件 of 命中事件) {
        const 键 = 取键函数(事件);
        if (键 === undefined || 键 === null || 键 === '') continue;
        计数表.set(键, (计数表.get(键) || 0) + 1);
    }
    return [...计数表.entries()].sort((甲, 乙) => 乙[1] - 甲[1] || String(甲[0]).localeCompare(String(乙[0])));
}

/** 打印「名次 / 键 / 条数 / 占比」表。 */
function 打印计数表(标题, 计数, 上限 = 表格默认条数) {
    const 总数 = 命中事件.length;
    const 显示的 = 计数.slice(0, 上限);
    const 行 = 显示的.map(([键, 条数], 下标) => [
        下标 + 1, 公共.截断(键, 60), 条数, `${(条数 / 总数 * 100).toFixed(1)}%`,
    ]);
    console.log(`\n── ${标题} ──`);
    if (!计数.length) {
        console.log('  （无）');
        return;
    }
    console.log(公共.排表(['#', '名称', '条数', '占比'], 行));
    if (计数.length > 上限) console.log(`  …其余 ${计数.length - 上限} 项略（用 --前N 调整）`);
}

/** 中位数（已排序数组）。 */
function 中位数(数值列表) {
    if (!数值列表.length) return 0;
    const 中间 = Math.floor(数值列表.length / 2);
    return 数值列表.length % 2 ? 数值列表[中间] : (数值列表[中间 - 1] + 数值列表[中间]) / 2;
}

// ════════════════════════════ 模式一：时间线 ════════════════════════════

if (是开关('时间线')) {
    console.log(`${path.basename(日志文件)} → ${命中事件.length} 条${有无筛选 ? '（已筛选）' : ''}`);
    for (const 事件 of 命中事件) {
        const 数据 = JSON.stringify(事件.data ?? {});
        const 正文 = 是开关('完整') || 数据.length <= 数据截断长度 ? 数据 : `${数据.slice(0, 数据截断长度)}…`;
        console.log(`${公共.格式化时刻(事件.ts)} [${事件.tag}] ${公共.补齐(事件.location, 28)} ${公共.补齐(事件.msg, 22)} ${正文}`);
    }
    process.exit(0);
}

// ════════════════════════════ 模式二：分组计数 ════════════════════════════

/** `--分组 data.pos` 取该字段；裸 `--分组` 默认按位置分组。 */
const 分组字段 = 参数.分组 !== undefined ? String(参数.分组) : (是开关('分组') ? 'location' : null);
/** `--空档 ts` 取该字段；裸 `--空档` 默认按时间戳算间隔。 */
const 空档字段 = 参数.空档 !== undefined ? String(参数.空档) : (是开关('空档') ? 'ts' : null);

if (分组字段 !== null) {
    const 字段 = 分组字段;
    const 上限 = Number(参数.前N) > 0 ? Number(参数.前N) : 表格默认条数;
    const 取值 = 事件 => {
        const 值 = 公共.取字段(事件, 字段);
        return typeof 值 === 'object' ? JSON.stringify(值) : 值;
    };
    const 计数 = 计数排序(取值);
    if (是开关('JSON')) {
        console.log(JSON.stringify({ 文件: 日志文件, 字段, 命中: 命中事件.length, 分组: 计数.map(([键, 条数]) => ({ 键, 条数 })) }, null, 2));
        process.exit(0);
    }
    console.log(`${path.basename(日志文件)} · 按 ${字段} 分组（${命中事件.length} 条）`);
    打印计数表(`按 ${字段}`, 计数, 上限);
    process.exit(0);
}

// ════════════════════════════ 模式三：空档定位 ════════════════════════════

if (空档字段 !== null) {
    const 字段 = 空档字段;
    const 阈值 = Number(参数.最小间隔) > 0 ? Number(参数.最小间隔) : 空档默认阈值;

    // 同一个位置前后两次事件之间「该走的时间」没走完，就是空档（卡顿 / 停摆窗口）
    const 按位置 = new Map();
    for (const 事件 of 命中事件) {
        const 键 = 事件.location || '（无位置）';
        if (!按位置.has(键)) 按位置.set(键, []);
        按位置.get(键).push(事件);
    }

    const 空档列表 = [];
    for (const [位置, 事件组] of 按位置) {
        if (事件组.length < 2) continue;
        for (let i = 1; i < 事件组.length; i++) {
            const 前 = 事件组[i - 1];
            const 后 = 事件组[i];
            const 间隔 = Number(公共.取字段(后, 字段)) - Number(公共.取字段(前, 字段));
            if (!(间隔 > 阈值)) continue;
            空档列表.push({
                位置,
                间隔,
                起点时刻: 公共.格式化时刻(前.ts),
                终点时刻: 公共.格式化时刻(后.ts),
                起点事件: `${前.msg} ${JSON.stringify(前.data ?? {})}`.slice(0, 80),
                终点事件: `${后.msg} ${JSON.stringify(后.data ?? {})}`.slice(0, 80),
            });
        }
    }
    空档列表.sort((甲, 乙) => 乙.间隔 - 甲.间隔);

    if (是开关('JSON')) {
        console.log(JSON.stringify({ 文件: 日志文件, 字段, 阈值, 空档: 空档列表.slice(0, 空档默认条数) }, null, 2));
        process.exit(0);
    }

    console.log(`${path.basename(日志文件)} · 空档定位（字段 ${字段}，阈值 ${阈值}）`);
    if (!空档列表.length) {
        console.log('  没有超过阈值的空档。');
        process.exit(0);
    }
    console.log(公共.排表(
        ['间隔', '位置', '起', '止', '前一条', '后一条'],
        空档列表.slice(0, 空档默认条数).map(项 => [
            公共.格式化时长(项.间隔), 项.位置, 项.起点时刻, 项.终点时刻, 项.起点事件, 项.终点事件,
        ]),
    ));
    if (空档列表.length > 空档默认条数) console.log(`  …其余 ${空档列表.length - 空档默认条数} 条略`);
    process.exit(0);
}

// ════════════════════════════ 默认模式：总览 ════════════════════════════

const 起点 = 命中事件[0].ts;
const 终点 = 命中事件[命中事件.length - 1].ts;
const 时长 = 终点 - 起点;
const 按位置计数 = 计数排序(事件 => 事件.location);
const 按事件计数 = 计数排序(事件 => 事件.msg);
const 按探针计数 = 计数排序(事件 => 事件.tag);
const 标记列表 = 全部事件.filter(事件 => 事件.msg === 'mark');

/** 心跳节奏：同一位置相邻心跳的间隔分布，用于发现「停摆」。 */
function 心跳节奏() {
    const 按心跳位置 = new Map();
    for (const 事件 of 命中事件) {
        if (事件.msg !== 'heartbeat') continue;
        const 键 = 事件.location;
        if (!按心跳位置.has(键)) 按心跳位置.set(键, []);
        按心跳位置.get(键).push(事件);
    }
    const 结论 = [];
    for (const [位置, 事件组] of 按心跳位置) {
        if (事件组.length < 3) continue;
        const 间隔列表 = [];
        let 最大间隔 = 0;
        let 最大间隔时刻 = 事件组[0].ts;
        for (let i = 1; i < 事件组.length; i++) {
            const 间隔 = 事件组[i].ts - 事件组[i - 1].ts;
            间隔列表.push(间隔);
            if (间隔 > 最大间隔) {
                最大间隔 = 间隔;
                最大间隔时刻 = 事件组[i].ts;
            }
        }
        间隔列表.sort((甲, 乙) => 甲 - 乙);
        结论.push({ 位置, 条数: 事件组.length, 中位: 中位数(间隔列表), 最大: 最大间隔, 最大时刻: 最大间隔时刻 });
    }
    return 结论.sort((甲, 乙) => 乙.最大 - 甲.最大);
}

const 节奏 = 心跳节奏();

/** 同一案例下的全部会话对比（修复前 / 修复后），结案判据常用。 */
const 会话对比 = 公共.列出日志文件(案例目录).map(项 => {
    const 事件组 = 公共.读事件文件(项.文件);
    const 位置数 = new Set(事件组.map(事件 => 事件.location)).size;
    return {
        会话: 项.会话名,
        条数: 项.条数,
        位置数,
        时长: 事件组.length ? 公共.格式化时长(事件组[事件组.length - 1].ts - 事件组[0].ts) : '-',
        起: 公共.格式化时刻(项.起点),
        止: 公共.格式化时刻(项.终点),
    };
});

if (是开关('JSON')) {
    console.log(JSON.stringify({
        文件: 日志文件,
        命中: 命中事件.length,
        总条数: 全部事件.length,
        起点: 公共.格式化时刻(起点),
        终点: 公共.格式化时刻(终点),
        时长毫秒: 时长,
        标记: 标记列表.map(事件 => ({ 时刻: 公共.格式化时刻(事件.ts), 内容: 事件.data.label })),
        按探针: 按探针计数.map(([键, 条数]) => ({ 探针: 键, 条数 })),
        按位置: 按位置计数.map(([键, 条数]) => ({ 位置: 键, 条数 })),
        按事件: 按事件计数.map(([键, 条数]) => ({ 事件: 键, 条数 })),
        心跳节奏: 节奏,
        会话对比,
    }, null, 2));
    process.exit(0);
}

console.log(`文件      ${path.basename(日志文件)}`);
console.log(`案例目录  ${案例目录}`);
console.log(`条数      ${命中事件.length}${有无筛选 ? ` / 共 ${全部事件.length}（已筛选）` : ''}`);
console.log(`时间      ${公共.格式化时刻(起点)} → ${公共.格式化时刻(终点)}（${公共.格式化时长(时长)}）`);
console.log(`速率      ${(命中事件.length / Math.max(1, 时长 / 1000)).toFixed(1)} 条/秒`);

if (标记列表.length) {
    console.log('\n── 标记 ──');
    for (const 事件 of 标记列表) console.log(`  ${公共.格式化时刻(事件.ts)}  ${事件.data.label}`);
}

打印计数表('按探针', 按探针计数);
打印计数表('按位置', 按位置计数);
打印计数表('按事件', 按事件计数);

if (节奏.length) {
    console.log('\n── 心跳节奏（每条位置相邻两次心跳的间隔）──');
    console.log(公共.排表(['位置', '心跳数', '中位间隔', '最大间隔', '最大间隔出现于'], 节奏.slice(0, 8).map(项 => [
        公共.截断(项.位置, 40), 项.条数, 公共.格式化时长(项.中位), 公共.格式化时长(项.最大), 公共.格式化时刻(项.最大时刻),
    ])));
    const 可疑 = 节奏.filter(项 => 项.最大 > Math.max(4 * 项.中位, 1000));
    console.log(可疑.length
        ? `  可疑停摆：${可疑.map(项 => `${项.位置}（${公共.格式化时长(项.最大)}，${公共.格式化时刻(项.最大时刻)}）`).join('；')}`
        : '  没有明显停摆（最大间隔都在中位数的 4 倍以内）。');
}

if (会话对比.length > 1) {
    console.log('\n── 会话对比（同案例）──');
    console.log(公共.排表(['会话', '条数', '位置数', '时长', '起', '止'], 会话对比.map(项 => [
        项.会话, 项.条数, 项.位置数, 项.时长, 项.起, 项.止,
    ])));
}
