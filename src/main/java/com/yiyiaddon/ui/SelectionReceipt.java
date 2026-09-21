package com.yiyiaddon.ui;

import com.yiyiaddon.ui.render.TooltipLayer;

/**
 * 选择器「加 / 减」回执的<b>唯一出口与唯一文案</b>。
 *
 * <p><b>为什么有这个类</b>（用户 2026-09-21：「所有选择器都发送…同步添加或者减去」→ 拍板
 * 「全部做成弹窗的，跟状态栏一样提示过一会就消失」）：选择器是两栏式、玩家一次会连点十几下，
 * 原来加减往聊天框发一行（{@code ClientChat.send}），但<b>面板开着时 MC 会把整个 HUD 藏起来</b>
 * —— 聊天框与行动栏都看不见，那些回执实际是「关掉窗口之后才看到」，连点几下就刷一屏。
 * 现在统一改成面板内顶部弹窗（{@link TooltipLayer#notify}：1.9 秒自动消失、重复调用只刷新同一条、
 * 不堆叠），点了就看见，也不脏聊天记录。</p>
 *
 * <p><b>唯一文案</b>：所有选择器都调这里，不再各自拼字符串（第 169 条同源口径）——
 * 加一条 {@code §7已添加 §a<名>}、减一条 {@code §7已移除 §c<名>}、批量
 * {@code §7已添加 §aN 项} / {@code §7已移除 §cN 项}、整组清空 {@code §7已清空 §cN 项}、
 * 逐行开关 {@code §7已启用 §a<名>} / {@code §7已停用 §c<名>}、单选 {@code §7已选择 §a<名>}、
 * 复位 {@code §7已恢复默认}（整组带 {@code §aN 项}）、收藏 {@code §7已收藏 §a<名>} /
 * {@code §7已取消收藏 §8<名>}。</p>
 *
 * <p><b>颜色码会被去掉</b>：条目标题常带 {@code §} 配色（如 {@code §7名称 §8- §7描述}），
 * 直接回显会把弹窗染花，因此入口统一先剥掉颜色码。</p>
 */
public final class SelectionReceipt {

    /** 颜色代码，与 {@code ClientChat} 同一套规则 */
    private static final String COLOR_CODES = "§[0-9a-fk-orA-FK-OR]";

    private SelectionReceipt() {
    }

    /** 加入一条：{@code §7已添加 §a<名>} */
    public static void added(String name) {
        notify("§7已添加 §a" + clean(name));
    }

    /** 移除一条：{@code §7已移除 §c<名>} */
    public static void removed(String name) {
        notify("§7已移除 §c" + clean(name));
    }

    /** 批量加入（组头「全选」）：只发一条汇总，逐条发会把弹窗刷成走马灯。 */
    public static void addedMany(int count) {
        if (count <= 0) {
            notify("§e没有可添加的条目");
            return;
        }
        notify("§7已添加 §a" + count + " 项");
    }

    /** 批量移除（组头「清空」）：只发一条汇总。 */
    public static void removedMany(int count) {
        if (count <= 0) {
            notify("§e没有可移除的条目");
            return;
        }
        notify("§7已移除 §c" + count + " 项");
    }

    /** 清空某一行 / 某一组已选（行尾 ↻ 那类按钮）。 */
    public static void cleared(int count) {
        if (count <= 0) {
            notify("§e本来就是空的");
            return;
        }
        notify("§7已清空 §c" + count + " 项");
    }

    /** 逐行开关（容器类型、装备附魔这类「一行一个 ✓ / ✗」）。 */
    public static void toggled(boolean on, String name) {
        notify(on ? "§7已启用 §a" + clean(name) : "§7已停用 §c" + clean(name));
    }

    /** 单选确认（挑一件装备 / 换一种作物 / 挑一个菜单物品）。 */
    public static void selected(String name) {
        notify("§7已选择 §a" + clean(name));
    }

    /** 收藏 / 取消收藏（模块行右侧星标）。 */
    public static void favorited(boolean added, String name) {
        notify(added ? "§7已收藏 §a" + clean(name) : "§7已取消收藏 §8" + clean(name));
    }

    /** 恢复默认（行尾 ↺ 那类复位按钮，单值）。 */
    public static void reset() {
        notify("§7已恢复默认");
    }

    /** 恢复默认并说明涉及多少项（整组复位）。 */
    public static void reset(int count) {
        if (count <= 0) {
            notify("§7已恢复默认");
            return;
        }
        notify("§7已恢复默认 §a" + count + " 项");
    }

    /** 单一出口：将来换提示方式（加图标 / 加音效 / 换位置）只改这里。 */
    private static void notify(String text) {
        TooltipLayer.notify(text);
    }

    /** 剥掉颜色码并收掉首尾空白。 */
    private static String clean(String name) {
        if (name == null) return "";
        return name.replaceAll(COLOR_CODES, "").trim();
    }
}
