package com.yiyiaddon.platform.identity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.world.entity.EntityType;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 实体显示名：客户端没给出中文时，用随包中文表兜底。
 *
 * <p><b>为什么需要它</b>：界面里的实体名走 {@code EntityType#getDescription()}，取的是<b>客户端当前
 * 语言</b>的译名。有些客户端安装（改版 jar / 缺语言文件的整合包）根本没有 zh_cn 的实体译名，
 * 取到的就是英文原名或翻译键本身 —— 用户 2026-09-21 报的「杀截光环目标选择器没汉化」正是这一种
 * （实测那个客户端的客户端 jar 里只有 {@code en_us.json}）。本模组的界面本身是全中文硬编码，
 * 实体名混着英文没有任何道理，因此这里随包带一份官方中文表。</p>
 *
 * <p><b>什么时候改用随包中文</b> —— 只有下面两条，都是「客户端这一条确实没译」的直接证据，不靠猜：</p>
 * <ol>
 *   <li>客户端给的就是翻译键本身（{@code entity.minecraft.zombie}）；</li>
 *   <li>客户端给的与<b>官方英文原名</b>逐字相同（说明这条回落到了英文）。</li>
 * </ol>
 * <p>其余一律用客户端给的名字：资源包改名（服务器把某个生物改成别的叫法）、其它语言的译名、
 * 模组自定义实体，都不会被本表覆盖。</p>
 *
 * <p><b>两份表同时是合法的语言文件</b>（{@code assets/yiyiaddon/lang/}）：客户端语言为中文时，
 * 原版自己的界面也用得上这份中文（物品栏、聊天里的实体名等），不必只服务本模组的面板。</p>
 */
public final class EntityDisplayNames {

    /** 随包中文表（官方 zh_cn 的 entity.minecraft.* 部分） */
    private static final String ZH_RESOURCE = "/assets/yiyiaddon/lang/zh_cn.json";
    /** 随包英文原名表：用来判断「客户端这条是不是回落到了英文」 */
    private static final String EN_RESOURCE = "/assets/yiyiaddon/lang/en_us.json";

    private static final Gson GSON = new Gson();
    private static final java.lang.reflect.Type MAP_TYPE =
            TypeToken.getParameterized(Map.class, String.class, String.class).getType();

    /** 两张表都只读一次；读不到就是空表（照常回落到客户端语言，不报错、不阻断） */
    private static Map<String, String> zh = Map.of();
    private static Map<String, String> en = Map.of();
    private static boolean loaded;

    private EntityDisplayNames() {
    }

    /**
     * 实体显示名：客户端给了中文就用客户端的，否则用随包中文，都没有就原样返回客户端给的名字。
     *
     * @param type 实体类型；{@code null} 返回空串（调用方不必判空）
     */
    public static String display(EntityType<?> type) {
        if (type == null) return "";
        String key = type.getDescriptionId();
        String live = type.getDescription().getString();
        load();
        String translated = zh.get(key);
        if (translated == null || translated.isBlank()) return live;
        if (live.equals(key) || live.equals(en.get(key))) return translated;
        return live;
    }

    private static void load() {
        if (loaded) return;
        loaded = true;
        zh = read(ZH_RESOURCE);
        en = read(EN_RESOURCE);
    }

    private static Map<String, String> read(String resource) {
        try (InputStream in = EntityDisplayNames.class.getResourceAsStream(resource)) {
            if (in == null) return Map.of();
            Map<String, String> parsed = GSON.fromJson(
                    new String(in.readAllBytes(), StandardCharsets.UTF_8), MAP_TYPE);
            return parsed == null ? Map.of() : Map.copyOf(parsed);
        } catch (Exception ignored) {
            // 表读不出来只影响显示名（回落到客户端语言），不值得让界面或启动出问题
            return Map.of();
        }
    }
}
