package com.yiyiaddon.feature.admindetect.service;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerSkin;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * 在线玩家采集：为白名单 / 黑名单选择器提供候选。
 *
 * <p><b>为什么不能只用 Tab 名单</b>：被 vanish 插件隐藏的管理员<b>不在 Tab 列表里</b>，
 * 只用 Tab 做候选连他的名字都看不到、根本加不进黑名单。因此候选合并两个来源：
 * Tab 在线名单（{@link PlayerInfo}）＋ 当前世界实体列表（能抓到 vanish 的人的名字）。</p>
 *
 * <p><b>为什么还要列「已离线」</b>：名单里已经存着的名字，玩家下线后既不在 Tab 名单、也不在世界实体
 * 列表里。若候选表里没有他，{@code SelectorScreen} 的右栏就找不到对应条目 —— 表现为「人不在线时
 * 删不掉他的名字」。所以凡在名单里而当前采集不到的一律补进候选，只是标注为已离线。</p>
 *
 * <p><b>旁观 / 创造的人不过滤</b>：他们正是最该被加进黑名单的对象，一视同仁地出现在候选里。</p>
 */
public final class PlayerListProbe {

    /** 候选玩家的在场形态；决定分组标题与头像能否拿到真皮肤。 */
    public enum Presence {

        /** 在 Tab 在线名单里：{@code PlayerInfo} 带皮肤属性，头像同步就是真皮肤 */
        LISTED("§a§l▌ 在线玩家"),

        /** 不在 Tab 名单、但当前世界里有实体：vanish 的典型形态；只有 UUID，头像退化成默认头 */
        HIDDEN("§d§l▌ 隐藏玩家（不在 Tab 列表）"),

        /** 只在名单里、当前采集不到：人已下线，列出来只为能把他从名单里删掉 */
        OFFLINE("§8§l▌ 已离线（仅名单里还有）");

        private final String groupTitle;

        Presence(String groupTitle) {
            this.groupTitle = groupTitle;
        }

        public String groupTitle() {
            return groupTitle;
        }
    }

    /**
     * 一条候选玩家。
     *
     * @param name    玩家名；名单按名字比较（旧项目 {@code containsName} 也是按名字），故它就是选择器的 key
     * @param id      在线时拿得到 UUID；已离线时为 {@code null}
     * @param profile 仅在 {@link Presence#LISTED} 时非空 —— 带皮肤属性，可直接 {@code createResolved}
     * @param presence 在场形态
     */
    public record Candidate(String name, UUID id, GameProfile profile, Presence presence) {
    }

    /** 候选排序用：中文/英文名统一按中文排序器，与项目其它选择器同一套口径 */
    private static final Collator COLLATOR = Collator.getInstance(Locale.CHINA);

    private PlayerListProbe() {
    }

    /**
     * 采集候选：Tab 名单 → 世界实体 → 名单里剩下的。
     *
     * @param selectedNames 当前已选名单（白名单或黑名单）；用于把已离线的人补进候选
     */
    public static List<Candidate> candidates(List<String> selectedNames) {
        Minecraft mc = Minecraft.getInstance();
        if (mc == null) return List.of();

        // key 用玩家名（忽略大小写），与名单的比较口径一致
        Map<String, Candidate> collected = new LinkedHashMap<>();

        // ① Tab 在线名单：带皮肤属性，含旁观 / 创造，不过滤
        if (mc.getConnection() != null) {
            for (PlayerInfo info : mc.getConnection().getOnlinePlayers()) {
                if (info == null) continue;
                GameProfile profile = info.getProfile();
                if (profile == null) continue;
                String name = profile.name();
                if (name == null || name.isBlank()) continue;
                collected.putIfAbsent(normalize(name), new Candidate(name, profile.id(), profile, Presence.LISTED));
            }
        }

        // ② 当前世界实体：补上 Tab 名单里看不到的 vanish 玩家
        if (mc.level != null) {
            for (Player player : mc.level.players()) {
                String name = player.getName().getString();
                if (name == null || name.isBlank()) continue;
                collected.putIfAbsent(normalize(name), new Candidate(name, player.getUUID(), null, Presence.HIDDEN));
            }
        }

        // ③ 名单里有、但上面两条都没采集到的：已离线
        if (selectedNames != null) {
            for (String name : selectedNames) {
                if (name == null || name.isBlank()) continue;
                collected.putIfAbsent(normalize(name), new Candidate(name, null, null, Presence.OFFLINE));
            }
        }

        List<Candidate> list = new ArrayList<>(collected.values());
        // 按「在场形态 → 名字」排序：分组天然连续，组内有序
        list.sort(Comparator.comparingInt((Candidate c) -> c.presence().ordinal())
            .thenComparing(Candidate::name, COLLATOR));
        return List.copyOf(list);
    }

    /**
     * 候选头像用的皮肤。
     *
     * <p>在 Tab 名单里的直接取 {@link PlayerInfo#getSkin()} —— 那是服务器已下发的皮肤，
     * <b>不走网络</b>；vanish / 已离线的人拿不到 PlayerInfo，退回原版按 UUID 派生的默认皮肤
     * （观感是 Steve/Alex 之一）。两者都不需要 Mojang 会话服，因此断网也有头像。</p>
     *
     * <p>绘制走 {@code PlayerFaceCache.draw}，本方法只负责给出皮肤，不做缓存。</p>
     */
    public static PlayerSkin skin(Candidate candidate) {
        if (candidate == null) return DefaultPlayerSkin.getDefaultSkin();
        GameProfile profile = candidate.profile();
        if (profile == null) profile = new GameProfile(candidate.id(), candidate.name());
        return skinOf(profile);
    }

    /** 按 GameProfile 取皮肤：优先 Tab 名单里已下发的那份，取不到退回默认皮肤。 */
    private static PlayerSkin skinOf(GameProfile profile) {
        Minecraft mc = Minecraft.getInstance();
        if (mc != null && mc.getConnection() != null) {
            PlayerInfo info = mc.getConnection().getPlayerInfo(profile.id());
            if (info != null) {
                PlayerSkin skin = info.getSkin();
                if (skin != null) return skin;
            }
        }
        return DefaultPlayerSkin.get(profile);
    }

    /** 名单比较用的归一化键：忽略大小写与首尾空格 */
    private static String normalize(String name) {
        return name.trim().toLowerCase(Locale.ROOT);
    }

    /** 在场形态计数（概览页与名单页的状态文字用） */
    public static Map<Presence, Integer> countByPresence(List<Candidate> candidates) {
        Map<Presence, Integer> counts = new EnumMap<>(Presence.class);
        for (Presence presence : Presence.values()) counts.put(presence, 0);
        if (candidates == null) return counts;
        for (Candidate candidate : candidates) {
            counts.merge(candidate.presence(), 1, Integer::sum);
        }
        return counts;
    }
}
