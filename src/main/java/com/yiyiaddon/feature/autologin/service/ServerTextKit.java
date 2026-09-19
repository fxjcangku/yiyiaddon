package com.yiyiaddon.feature.autologin.service;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerScoreEntry;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 识别用的文本 / 侧边栏 / 物品关键词工具（逐字搬旧 {@code AutoLoginModule} 的同名私有方法）。
 *
 * <p><b>分层</b>：本类只做「把游戏里的原始文本变成可比较的字符串」这一件事，不含任何流程判断；
 * 通用子服路线与自用路线各自调用它，禁止各自复制一份（第 169 条）。</p>
 *
 * <p><b>为什么保留两套侧边栏匹配口径</b>：旧项目里 {@code findSidebarKeyword}（自用路线用：
 * 主城 / 挂机区 / 最终目标）先删掉全部空白再比较，而 {@code findTargetAreaKeyword}
 * （通用子服的目标区域用）只做大小写归一、保留空格。两者对含空格关键词的命中结果不同，
 * 属旧行为的一部分：合并成一个会改变识别结果，因此按 1:1 各留一份，只登记不合并。</p>
 */
public final class ServerTextKit {

    /** 侧边栏最多扫多少条（逐字 = 旧实现 {@code limit(15)}） */
    private static final int SIDEBAR_LINE_LIMIT = 15;

    /** 子服名识别（逐字 = 旧 {@code SUBSERVER_NAME_PATTERN}） */
    private static final Pattern SUBSERVER_NAME_PATTERN = Pattern.compile(
        "(?:当前)?(?:服务器|子服|分区|线路|频道|区域)\\s*[:：|]?\\s*([\\p{IsHan}A-Za-z0-9_-]{2,24})|([\\p{IsHan}A-Za-z]{1,12}[一二三四五六七八九十百0-9]+区)",
        Pattern.CASE_INSENSITIVE);

    /** 颜色码（逐字 = 旧 {@code MINECRAFT_FORMATTING_PATTERN}） */
    private static final Pattern MINECRAFT_FORMATTING_PATTERN =
        Pattern.compile("(?i)(?:§|&)(?:[0-9A-FK-ORX]|#[0-9A-F]{6})");

    /** 不可见字符（逐字 = 旧 {@code INVISIBLE_CHARACTER_PATTERN}） */
    private static final Pattern INVISIBLE_CHARACTER_PATTERN =
        Pattern.compile("[\\u00AD\\u034F\\u061C\\u180E\\u200B-\\u200F\\u202A-\\u202E\\u2060-\\u206F\\uFEFF]");

    private ServerTextKit() {
    }

    // ── 文本归一化 ──

    /** 归一化：NFKC → 去颜色码 → 去不可见字符 → 连续空白压成一个空格 → trim（逐字 = 旧 {@code normalizeText}） */
    public static String normalize(String value) {
        return INVISIBLE_CHARACTER_PATTERN.matcher(MINECRAFT_FORMATTING_PATTERN.matcher(
                Normalizer.normalize(value == null ? "" : value, Normalizer.Form.NFKC)).replaceAll(""))
            .replaceAll("")
            .replaceAll("\\s+", " ")
            .trim();
    }

    /** 归一化 + 去全部空白 + 小写（逐字 = 旧 {@code normalizeMatchText}） */
    public static String normalizeForMatch(String value) {
        return normalize(value)
            .replaceAll("[\\s\\p{Z}]+", "")
            .toLowerCase(Locale.ROOT);
    }

    // ── 物品关键词 ──

    /** 物品名称或 Lore 命中关键词（逐字 = 旧 {@code matchesItemKeyword}） */
    public static boolean matchesKeyword(ItemStack stack, String keyword) {
        if (stack == null || stack.isEmpty() || keyword == null || keyword.isBlank()) return false;
        String target = normalizeForMatch(keyword);
        if (target.isEmpty()) return false;
        if (normalizeForMatch(stack.getHoverName().getString()).contains(target)) return true;
        ItemLore lore = stack.get(DataComponents.LORE);
        if (lore == null) return false;
        for (Component line : lore.lines()) {
            if (normalizeForMatch(line.getString()).contains(target)) return true;
        }
        return false;
    }

    /** 任一关键词命中（逐字 = 旧 {@code matchesAnyItemKeyword}） */
    public static boolean matchesAnyKeyword(ItemStack stack, List<String> keywords) {
        if (keywords == null) return false;
        for (String keyword : keywords) {
            if (matchesKeyword(stack, keyword)) return true;
        }
        return false;
    }

    // ── 侧边栏 ──

    /**
     * 自用路线的侧边栏关键词匹配：命中返回旧实现返回的关键词原文（trim 后），未命中 {@code null}。
     *
     * <p>口径 = {@code normalizeForMatch}（去全部空白 + 小写），逐字照旧 {@code findSidebarKeyword}。</p>
     */
    public static String findSidebarKeyword(Minecraft mc, List<String> keywords) {
        List<String> texts = sidebarLines(mc);
        if (texts.isEmpty() || keywords == null) return null;
        for (String keyword : keywords) {
            String target = normalizeForMatch(keyword);
            if (target.isEmpty()) continue;
            for (String text : texts) {
                if (normalizeForMatch(text).contains(target)) return keyword.trim();
            }
        }
        return null;
    }

    /**
     * 通用子服路线的目标区域匹配：口径 = {@code normalize} + 小写（保留空格），
     * 逐字照旧 {@code findTargetAreaKeyword}。
     */
    public static String findTargetAreaKeyword(Minecraft mc, List<String> keywords) {
        List<String> texts = sidebarLines(mc);
        if (texts.isEmpty() || keywords == null) return null;
        for (String keyword : keywords) {
            String target = normalize(keyword).toLowerCase(Locale.ROOT);
            if (target.isEmpty()) continue;
            for (String text : texts) {
                if (normalize(text).toLowerCase(Locale.ROOT).contains(target)) return keyword.trim();
            }
        }
        return null;
    }

    /** 侧边栏可见文本（标题 + 前 15 条计分项，逐字 = 旧实现的两处相同收集块），无侧边栏返回空表 */
    public static List<String> sidebarLines(Minecraft mc) {
        ClientLevel level = mc == null ? null : mc.level;
        if (level == null) return List.of();
        Scoreboard scoreboard = level.getScoreboard();
        Objective objective = scoreboard.getDisplayObjective(DisplaySlot.SIDEBAR);
        if (objective == null) return List.of();

        List<String> texts = new ArrayList<>();
        texts.add(objective.getDisplayName().getString());
        scoreboard.listPlayerScores(objective).stream()
            .filter(entry -> !entry.isHidden())
            .limit(SIDEBAR_LINE_LIMIT)
            .map(entry -> PlayerTeam.formatNameForTeam(scoreboard.getPlayersTeam(entry.owner()),
                Component.literal(entry.owner())).getString())
            .forEach(texts::add);
        return texts;
    }

    // ── 子服名识别 ──

    /**
     * 从侧边栏里认出当前子服名；认不出返回 {@code null}（逐字 = 旧 {@code detectSubserverName}）。
     *
     * <p>先看侧边栏标题，再按分值从高到低取前 15 条计分项。名字为「大厅 / lobby」时按旧实现
     * 视为大厅、返回 {@code null}。</p>
     */
    public static String detectSubserverName(Minecraft mc) {
        ClientLevel level = mc == null ? null : mc.level;
        if (level == null) return null;
        Scoreboard scoreboard = level.getScoreboard();
        Objective objective = scoreboard.getDisplayObjective(DisplaySlot.SIDEBAR);
        if (objective == null) return null;

        String name = extractSubserverName(objective.getDisplayName().getString());
        if (name != null) return name;

        return scoreboard.listPlayerScores(objective).stream()
            .filter(entry -> !entry.isHidden())
            .sorted(Comparator.comparingInt(PlayerScoreEntry::value).reversed())
            .limit(SIDEBAR_LINE_LIMIT)
            .map(entry -> PlayerTeam.formatNameForTeam(scoreboard.getPlayersTeam(entry.owner()),
                Component.literal(entry.owner())).getString())
            .map(ServerTextKit::extractSubserverName)
            .filter(value -> value != null)
            .findFirst()
            .orElse(null);
    }

    /** 从一行文本里抽出子服名（逐字 = 旧 {@code extractSubserverName}） */
    public static String extractSubserverName(String raw) {
        if (raw == null || raw.isBlank()) return null;
        String normalized = Normalizer.normalize(raw, Normalizer.Form.NFKC)
            .replaceAll("(?i)§[0-9A-FK-OR]", "")
            .replaceAll("[\\u200B-\\u200D\\u2060\\uFEFF]", "")
            .replaceAll("\\s+", " ")
            .trim();
        Matcher matcher = SUBSERVER_NAME_PATTERN.matcher(normalized);
        if (!matcher.find()) return null;
        String name = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
        if (name == null || name.equalsIgnoreCase("大厅") || name.equalsIgnoreCase("lobby")) return null;
        return name;
    }
}
