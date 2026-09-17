/**
 * 埋点修复 · 公共函数（零依赖，Node ≥ 18）
 *
 * 只放《采集服务.js》与《分析.js》都要用的东西，避免同源逻辑写两份（开发习惯.md 第 169 条）：
 *   - 命令行参数解析
 *   - 中日韩文字按两个字符宽计算的表格排版
 *   - ndjson 读取与日志文件枚举
 *   - 案例目录定位
 */

'use strict';

const fs = require('node:fs');
const path = require('node:path');

/** 04-埋点修复/（本文件在 工具/ 下，上一级即根）。 */
const 埋点根目录 = path.resolve(__dirname, '..');

/**
 * 解析 `--键 值` / `--键=值` / `--开关` 三种形式，并支持中英文别名。
 *
 * @param {string[]} 原始参数 process.argv.slice(2)
 * @param {Record<string, string>} [别名表] 形如 { location: '位置' }，用于把英文名并到中文名上
 * @returns {{开关: Set<string>} & Record<string, string>}
 */
function 解析参数(原始参数, 别名表 = {}) {
    const 结果 = { 开关: new Set() };
    for (let i = 0; i < 原始参数.length; i++) {
        const 当前 = 原始参数[i];
        if (!当前.startsWith('-')) continue;

        const 去横线 = 当前.replace(/^-+/, '');
        const 等号位 = 去横线.indexOf('=');
        let 键;
        let 值 = null;
        if (等号位 >= 0) {
            键 = 去横线.slice(0, 等号位);
            值 = 去横线.slice(等号位 + 1);
        } else {
            键 = 去横线;
            const 下一个 = 原始参数[i + 1];
            if (下一个 !== undefined && !下一个.startsWith('--')) {
                值 = 下一个;
                i++;
            }
        }

        if (值 === null) {
            结果.开关.add(键);
            continue;
        }
        // 同一个键出现多次时，用逗号拼起来（例如 --位置 A --位置 B）
        const 正式键 = 别名表[键] || 键;
        const 旧值 = 结果[正式键];
        结果[正式键] = 旧值 === undefined ? 值 : 旧值 === true ? 值 : `${旧值},${值}`;
    }
    return 结果;
}

/**
 * 取多值参数的数组形式：`--位置 A,B` 与 `--位置 A --位置 B` 等价。
 * @returns {string[]}
 */
function 取多值(参数, 键) {
    const 值 = 参数[键];
    if (值 === undefined || 值 === true || 值 === '') return [];
    return String(值).split(',').map(项 => 项.trim()).filter(项 => 项);
}

/** 中日韩文字按 2 个字符宽估算显示宽度。 */
function 显示宽度(文本) {
    let 宽 = 0;
    for (const 字符 of String(文本)) {
        const 码点 = 字符.codePointAt(0);
        const 是宽字符 =
            (码点 >= 0x1100 && 码点 <= 0x115f) ||
            (码点 >= 0x2e80 && 码点 <= 0xa4cf) ||
            (码点 >= 0xac00 && 码点 <= 0xd7a3) ||
            (码点 >= 0xf900 && 码点 <= 0xfaff) ||
            (码点 >= 0xfe30 && 码点 <= 0xfe4f) ||
            (码点 >= 0xff00 && 码点 <= 0xff60) ||
            (码点 >= 0xffe0 && 码点 <= 0xffe6);
        宽 += 是宽字符 ? 2 : 1;
    }
    return 宽;
}

/** 按显示宽度补空格（右对齐传 '右'）。 */
function 补齐(文本, 宽, 对齐 = '左') {
    const 内容 = String(文本);
    const 空格数 = Math.max(0, 宽 - 显示宽度(内容));
    return 对齐 === '右' ? ' '.repeat(空格数) + 内容 : 内容 + ' '.repeat(空格数);
}

/** 截断到指定显示宽度，超出补省略号。 */
function 截断(文本, 最大宽) {
    const 内容 = String(文本);
    if (显示宽度(内容) <= 最大宽) return 内容;
    let 结果 = '';
    let 宽 = 0;
    for (const 字符 of 内容) {
        const 字符宽 = 显示宽度(字符);
        if (宽 + 字符宽 > 最大宽 - 1) break;
        结果 += 字符;
        宽 += 字符宽;
    }
    return 结果 + '…';
}

/**
 * 排一张表。列对齐自动判定：整列都是数字（含 12.3% / 1,024）就右对齐，否则左对齐。
 * @param {string[]} 表头
 * @param {Array<Array<string|number>>} 行
 */
function 排表(表头, 行) {
    const 全部 = [表头, ...行.map(行内容 => 行内容.map(值 => String(值 ?? '')))];
    const 列数 = 表头.length;
    const 列宽 = [];
    const 该列右对齐 = [];
    for (let 列 = 0; 列 < 列数; 列++) {
        列宽[列] = Math.max(...全部.map(行内容 => 显示宽度(行内容[列] ?? '')));
        // 第一列固定左对齐（通常是名称）；其余列整列都像数字才右对齐
        该列右对齐[列] = 列 > 0 && 全部.slice(1).every(行内容 => 行内容[列] === '' || /^-?[\d.,%]+$/.test(行内容[列]));
    }
    return 全部
        .map(行内容 => 行内容
            .map((单元, 列) => {
                // 表头跟着它那一列的对齐方式走，视觉上才连成一条
                const 对齐 = 该列右对齐[列] ? '右' : '左';
                return 补齐(单元 ?? '', 列宽[列], 对齐);
            })
            .join('  ')
            .replace(/\s+$/, ''))
        .join('\n');
}

/** 毫秒 → HH:mm:ss.SSS（本地时区）。 */
function 格式化时刻(毫秒) {
    if (!Number(毫秒)) return '--:--:--.---';
    const 日期 = new Date(Number(毫秒));
    const 补零 = (值, 位数 = 2) => String(值).padStart(位数, '0');
    return `${补零(日期.getHours())}:${补零(日期.getMinutes())}:${补零(日期.getSeconds())}.${补零(日期.getMilliseconds(), 3)}`;
}

/** 毫秒 → 人读时长（1 分 20 秒 / 3.5 秒）。 */
function 格式化时长(毫秒) {
    const 总秒 = 毫秒 / 1000;
    if (总秒 < 60) return `${总秒.toFixed(2)} 秒`;
    const 分 = Math.floor(总秒 / 60);
    const 秒 = 总秒 - 分 * 60;
    if (分 < 60) return `${分} 分 ${秒.toFixed(1)} 秒`;
    const 时 = Math.floor(分 / 60);
    return `${时} 时 ${分 - 时 * 60} 分`;
}

/** 按路径取值，支持 'data.pos' 这种点号路径；取不到返回 undefined。 */
function 取字段(对象, 路径) {
    if (!路径) return undefined;
    let 当前 = 对象;
    for (const 段 of String(路径).split('.')) {
        if (当前 === null || 当前 === undefined) return undefined;
        当前 = 当前[段];
    }
    return 当前;
}

/** 读 ndjson 事件文件；半行（进程被杀）自动跳过。 */
function 读事件文件(文件) {
    if (!fs.existsSync(文件)) return [];
    const 事件列表 = [];
    for (const 行 of fs.readFileSync(文件, 'utf8').split('\n')) {
        if (!行.trim()) continue;
        try {
            事件列表.push(JSON.parse(行));
        } catch {
            // 忽略半行
        }
    }
    事件列表.sort((甲, 乙) => (甲.ts - 乙.ts) || ((甲.seq || 0) - (乙.seq || 0)));
    return 事件列表;
}

/**
 * 列出案例目录下的全部日志文件。
 * @returns {Array<{文件:string, 会话名:string, 条数:number, 起点:number, 终点:number, 修改时间:number}>}
 */
function 列出日志文件(案例目录) {
    if (!fs.existsSync(案例目录)) return [];
    return fs.readdirSync(案例目录)
        .filter(名 => 名.startsWith('日志-') && 名.endsWith('.ndjson'))
        .map(名 => {
            const 文件 = path.join(案例目录, 名);
            const 事件列表 = 读事件文件(文件);
            return {
                文件,
                会话名: 名.slice('日志-'.length, -'.ndjson'.length),
                条数: 事件列表.length,
                起点: 事件列表.length ? 事件列表[0].ts : 0,
                终点: 事件列表.length ? 事件列表[事件列表.length - 1].ts : 0,
                修改时间: fs.statSync(文件).mtimeMs,
            };
        })
        .sort((甲, 乙) => 甲.起点 - 乙.起点);
}

/** 列出某个分类（进行中 / 已修复）下的案例名。 */
function 列出案例(分类) {
    const 目录 = path.join(埋点根目录, 分类);
    if (!fs.existsSync(目录)) return [];
    return fs.readdirSync(目录, { withFileTypes: true })
        .filter(项 => 项.isDirectory())
        .map(项 => 项.name);
}

/** 按名字找案例目录，先进行中后已修复；找不到返回 null。 */
function 找案例目录(案例名) {
    for (const 分类 of ['进行中', '已修复']) {
        const 候选 = path.join(埋点根目录, 分类, 案例名);
        if (fs.existsSync(候选)) return 候选;
    }
    return null;
}

/**
 * 全库找日志文件（截默认分析目标用）：优先「进行中」，其次按修改时间取最新。
 * @returns {string|null} 文件绝对路径
 */
function 找最新日志() {
    const 候选 = [];
    for (const 分类 of ['进行中', '已修复']) {
        for (const 案例 of 列出案例(分类)) {
            for (const 项 of 列出日志文件(path.join(埋点根目录, 分类, 案例))) {
                if (项.条数 === 0) continue;
                候选.push({ 文件: 项.文件, 修改时间: 项.修改时间, 优先: 分类 === '进行中' ? 1 : 0 });
            }
        }
    }
    if (!候选.length) return null;
    候选.sort((甲, 乙) => (乙.优先 - 甲.优先) || (乙.修改时间 - 甲.修改时间));
    return 候选[0].文件;
}

module.exports = {
    埋点根目录,
    解析参数,
    取多值,
    显示宽度,
    补齐,
    截断,
    排表,
    格式化时刻,
    格式化时长,
    取字段,
    读事件文件,
    列出日志文件,
    列出案例,
    找案例目录,
    找最新日志,
};
