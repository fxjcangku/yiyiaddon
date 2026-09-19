package com.yiyiaddon.platform;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 当前 BOSS 栏显示态读取（客户端已持有的文本，不依赖服务器再发一次包）。
 *
 * <p><b>为什么需要它：</b>BOSS 栏这类 HUD 文本原来只从服务器发包里拿到。服务器把 BOSS 栏设置一次之后
 * 就不再重发，或者那次包发生在进服之前——插件手里没有证据，屏幕上明明显示着季节也只能显示「未知」。
 * 读客户端已经持有的显示态，等于补上「服务器最后一次告诉客户端的内容」。</p>
 *
 * <p><b>为什么不按字段名取：</b>原版字段名（如 {@code events}）随版本 / 映射会变，写死名字在名字对不上时
 * 要么静默失效、要么直接崩。这里按「通用签名形如 {@code Map<UUID, ? extends BossEvent>}」来认字段，
 * 只解析一次并缓存；任何一步失败都只记一条日志并永久退化为「不轮询」，绝不影响主流程。</p>
 */
public final class BossBarProbe {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/bossbar");

    private static Field eventsField;
    private static boolean resolved;

    private BossBarProbe() {
    }

    /** 当前显示的 BOSS 栏名称文本（没有 BOSS 栏时返回空表）。 */
    public static List<Component> displayedNames() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.gui == null) return List.of();
        Object overlay = mc.gui.hud.getBossOverlay();
        if (overlay == null) return List.of();
        Map<?, ?> events = events(overlay);
        if (events == null || events.isEmpty()) return List.of();
        List<Component> names = new ArrayList<>(events.size());
        for (Object value : events.values()) {
            if (value instanceof BossEvent event && event.getName() != null) names.add(event.getName());
        }
        return names;
    }

    private static synchronized Map<?, ?> events(Object overlay) {
        Field field = eventsField(overlay);
        if (field == null) return null;
        try {
            Object value = field.get(overlay);
            return value instanceof Map<?, ?> map ? map : null;
        } catch (Throwable t) {
            if (eventsField != null) {
                LOGGER.warn("读取 BOSS 栏显示态失败，季节将只跟随服务器发包：{}", t.toString());
            }
            eventsField = null;
            resolved = true;
            return null;
        }
    }

    /** 解析并缓存「BOSS 栏事件表」字段：只按通用签名认，不认字段名 */
    private static Field eventsField(Object overlay) {
        if (resolved) return eventsField;
        for (Class<?> type = overlay.getClass(); type != null && type != Object.class; type = type.getSuperclass()) {
            for (Field field : type.getDeclaredFields()) {
                if (!Map.class.isAssignableFrom(field.getType())) continue;
                if (!(field.getGenericType() instanceof ParameterizedType parameterized)) continue;
                Type[] args = parameterized.getActualTypeArguments();
                if (args.length != 2 || !(args[1] instanceof Class<?> valueType)) continue;
                if (!BossEvent.class.isAssignableFrom(valueType)) continue;
                field.setAccessible(true);
                eventsField = field;
                break;
            }
            if (eventsField != null) break;
        }
        resolved = true;
        if (eventsField == null) LOGGER.warn("未找到 BOSS 栏事件表字段，季节将只跟随服务器发包");
        return eventsField;
    }
}
