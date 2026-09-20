package com.yiyiaddon.feature.mining.ui.console;

import com.yiyiaddon.feature.mining.config.MiningSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动挖矿设置的完整回显：把一份 {@link MiningSettings} 摊成「分组标题 + 字段行」的文本列表。
 *
 * <p><b>用途</b>（用户 2026-09-18：「还不够详细 保存的信息 我全配置页面的设置包括秒破 男中音设置的」）：
 * 配置记录窗的「详情」按它把整份快照写全 —— 控制台五个设置页（目标选择 / 传送指令 / 触发条件 /
 * 物品管理 / 男中音调优）+ 秒破与连锁 + ESP 显示，一个字段都不落下，玩家不用读取就能核对记录里存了什么。</p>
 *
 * <p><b>与设置页的分工</b>：这里只读、只排版，不写任何设置；字段名取 {@code MiningSettings} 各自的
 * 中文名（与该类里的字段注释、控制台各页的行标签同一口径），因此不存在第二套叫法。</p>
 *
 * <p><b>完整性口径</b>：字段逐项列出 {@code MiningSettings#save} 的全部键；{@code LootMode} 用它的
 * 中文名，颜色用 {@code #RRGGBB}，列表项用物品 / 方块显示名（取不到时回落到原始 ID），
 * 空指令显示「未设置」。</p>
 */
public final class MiningConfigDigest {

    /** 分组标题格式（与本项目其它回显一致：信息蓝加粗 + 前缀方块） */
    private static final String GROUP = "§b§l▌ §b";

    private MiningConfigDigest() {
    }

    /** 分组标题行（详情窗分组用；也供调用方在自定义段落前插入标题） */
    public static String group(String title) {
        return GROUP + title;
    }

    /**
     * 整份设置 → 文本行（含分组标题与空行）。
     *
     * @param settings 要回显的设置；{@code null} 返回单行「读不出来」
     */
    public static List<String> lines(MiningSettings s) {
        List<String> out = new ArrayList<>();
        if (s == null) {
            out.add("§c设置内容读不出来（记录文件可能已损坏）");
            return out;
        }

        out.add(group("目标选择"));
        out.add(field("采集模式", s.lootMode.toString()));
        out.add(target("主世界矿石", s.overworldOreTarget, false));
        out.add(target("下界矿石", s.netherOreTarget, false));
        out.add(target("普通方块", s.blockTarget, true));
        out.add("");

        out.add(group("传送指令"));
        out.add(command("前往挖矿指令", s.wildCommand));
        out.add(field("RTP需要GUI选择", onOff(s.rtpGuiEnabled)));
        out.add(field("GUI按钮关键词", text(s.rtpGuiKeyword)));
        out.add(command("返回卸货指令", s.unloadCommand));
        out.add(command("前往补给指令", s.supplyCommand));
        out.add(command("前往修复指令", s.afkCommand));
        out.add(command("死亡返回指令", s.respawnCommand));
        out.add(field("传送等待时长", s.teleportDelay + " §7秒"));
        out.add(field("RTP冷却时长", s.rtpCooldown + " §7秒"));
        out.add(field("传送失败自动重试", onOff(s.teleportRetryEnabled)));
        out.add("");

        out.add(group("触发条件"));
        out.add(field("满载组数", s.unloadThreshold + " §7组"));
        out.add(field("食物阈值", s.hungerThreshold + " §7个"));
        out.add(field("耐久阈值", s.durabilityThreshold));
        out.add(field("潜影盒打包机", onOff(s.shulkerPacker)));
        out.add(field("自动断线", onOff(s.autoDisconnect)));
        out.add(field("断线血量", s.autoDisconnectHealth + " §7格"));
        out.add("");

        out.add(group("自用模式"));
        out.add(field("自用模式", onOff(s.personalMode)));
        out.add(field("触发组数", s.personalSellStacks + " §7组"));
        out.add(command("出售流程指令", s.personalSellCommand));
        out.add(field("回城点击关键词", text(s.personalSellCityKeyword)));
        out.add(field("跨服点击关键词", text(s.personalSellCrossServerKeyword)));
        out.add(field("回程目标服", text(s.personalSellReturnServer)));
        out.add(field("出售数量关键词", text(s.personalSellPickKeyword)));
        out.add(field("确认出售关键词", text(s.personalSellConfirmKeyword)));
        out.add(field("NPC 名字关键词", text(s.personalSellNpcName)));
        out.add(field("NPC 坐标", "§f" + s.personalSellNpcX + " §7/ §f"
            + s.personalSellNpcY + " §7/ §f" + s.personalSellNpcZ));
        out.add(field("单步超时", s.personalSellStepTimeout + " §7秒"));
        out.add(field("单步重试次数", s.personalSellRetries));
        out.add("");

        out.add(group("物品管理"));
        out.add(field("保留白名单", items(s.keepWhitelist)));
        out.add(field("食物白名单", items(s.foodWhitelist)));
        out.add(field("搭路方块白名单", items(s.placeBlocks)));
        out.add("");

        out.add(group("秒破与连锁"));
        out.add(field("快速破坏（秒破）", onOff(s.fastBreak)));
        out.add(field("绕过反作弊", onOff(s.bypassAnticheat)));
        out.add(field("秒破间隔", s.breakInterval + " §7tick"));
        out.add(field("连锁挖矿", onOff(s.veinMiner)));
        out.add(field("连锁最大方块数", s.veinMaxBlocks));
        out.add(field("连锁搜索距离", s.veinRange + " §7格"));
        out.add(field("连锁对角相邻", onOff(s.veinDiagonal)));
        out.add(field("连锁仅同类矿物", onOff(s.veinFamilyOnly)));
        out.add(field("刷怪笼优先破坏", onOff(s.breakSpawner)));
        out.add("");

        out.add(group("男中音调优 · 开关"));
        out.add(field("破坏阻挡方块", onOff(s.allowBreak)));
        out.add(field("寻路物流破坏方块", onOff(s.logisticsBreakBlocks)));
        out.add(field("放置方块", onOff(s.allowPlace)));
        out.add(field("自动整理物品栏", onOff(s.allowInventory)));
        out.add(field("自动切换工具", onOff(s.autoTool)));
        out.add(field("避开岩浆", onOff(s.avoidLava)));
        out.add(field("岩浆透视", onOff(s.lavaEsp)));
        out.add(field("岩浆透视范围", s.lavaEspRange + " §7格"));
        out.add(field("岩浆安全距离", s.lavaAvoidRadius + " §7格"));
        out.add(field("怪物规避", onOff(s.mobAvoidance)));
        out.add(field("掉落方块暂停", onOff(s.pauseMiningForFallingBlocks)));
        out.add(field("寻路视角跟随", onOff(s.pathViewFollow)));
        out.add(field("疾跑上坡", onOff(s.sprintAscends)));
        out.add(field("允许跑酷", onOff(s.allowParkour)));
        out.add(field("跑酷搭桥", onOff(s.allowParkourPlace)));
        out.add(field("对角线上升", onOff(s.allowDiagonalAscend)));
        out.add(field("对角线下降", onOff(s.allowDiagonalDescend)));
        out.add(field("仅挖暴露矿石", onOff(s.allowOnlyExposedOres)));
        out.add("");

        out.add(group("男中音调优 · 数值"));
        out.add(field("矿点刷新间隔", s.mineGoalUpdateInterval + " §7tick"));
        out.add(field("矿点缓存数量", s.mineMaxOreLocationsCount));
        out.add(field("怪物规避半径", s.mobAvoidanceRadius + " §7格"));
        out.add(field("最大坠落高度", s.maxFallHeight + " §7格"));
        out.add(field("暴露矿石检测距离", s.allowOnlyExposedOresDistance + " §7格"));
        out.add(field("最低挖掘高度", s.minYLevelWhileMining));
        out.add(field("最高挖掘高度", s.maxYLevelWhileMining));
        out.add(field("合法挖掘模式", onOff(s.legitMine)));
        out.add(field("合法挖掘检测对角矿石", onOff(s.legitMineIncludeDiagonals)));
        out.add(field("合法挖掘高度", s.legitMineYLevel));
        out.add(field("失败目标暂时跳过", onOff(s.blacklistClosestOnFailure)));
        out.add("");

        out.add(group("ESP 显示"));
        out.add(field("ESP 字号倍率", s.espScale + " §7倍"));
        out.add(field("矿物箱 ESP 颜色", color(s.renderMineralBox.color.rgb())));
        out.add(field("食物箱 ESP 颜色", color(s.renderFoodBox.color.rgb())));
        out.add(field("挂机修复点 ESP 颜色", color(s.renderAfkPoint.color.rgb())));
        out.add(field("容器标签字号倍率", s.espContainerTextScale + " §7倍"));
        out.add(field("容器标签文字颜色", s.espContainerTextColor == 0
            ? "§8跟随各自方框色" : color(s.espContainerTextColor)));
        out.add(field("挖掘进度显示", onOff(s.breakProgressEsp)));
        out.add(field("挖掘进度颜色（挖掘中）", color(s.breakProgressBusyColor)));
        out.add(field("挖掘进度颜色（已完成）", color(s.breakProgressReadyColor)));
        out.add("");

        out.add(group("其它"));
        out.add(field("状态播报", onOff(s.statusBroadcast)));
        return out;
    }

    // ── 字段行 ──

    /** 常规字段行：{@code §7名称 §8▸ §f值} */
    private static String field(String name, Object value) {
        return "§7" + name + " §8▸ §f" + value;
    }

    /** 开关：开=绿、关=灰（与设置页的行注释同一套配色） */
    private static String onOff(boolean value) {
        return value ? "§a开" : "§8关";
    }

    /** 目标字段：显示名 + 原始 ID（未选择说成未选择，不留空） */
    private static String target(String name, String id, boolean block) {
        if (id == null || id.isBlank()) return field(name, "§8未选择");
        String display = block ? MiningTargetPage.blockDisplayName(id) : MiningTargetPage.itemDisplayName(id);
        return field(name, "§f" + display + " §8(" + id + ")");
    }

    /** 指令字段：空串说成「未设置」，否则照抄原文（不改写玩家填的指令） */
    private static String command(String name, String value) {
        return field(name, value == null || value.isBlank() ? "§8未设置" : value);
    }

    /** 文本字段：空串说成「未设置」 */
    private static String text(String value) {
        return value == null || value.isBlank() ? "§8未设置" : value;
    }

    /** 白名单：逐个显示名 + 项数（空表说成「无」） */
    private static String items(List<String> ids) {
        if (ids == null || ids.isEmpty()) return "§8无";
        List<String> names = new ArrayList<>();
        for (String id : ids) names.add(MiningTargetPage.itemDisplayName(id));
        return "§f" + String.join("§7/§f", names) + " §8(" + ids.size() + " 项)";
    }

    /** 颜色：ARGB 取 RGB 写成 {@code #RRGGBB}（与设置页色块的读数口径一致） */
    private static String color(int argb) {
        return String.format("§f#%06X", argb & 0xFFFFFF);
    }
}
