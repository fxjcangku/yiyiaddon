package com.yiyiaddon.service;

import com.google.gson.JsonObject;
import com.yiyiaddon.core.BackgroundTasks;
import com.yiyiaddon.core.HttpApi;
import com.yiyiaddon.platform.ClientIdentity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * 遥测：崩溃上报与异常行为上报。
 *
 * <p>崩溃按消息与栈顶聚合（后端计算指纹），异常行为按类型做 5 分钟节流，
 * 避免同一问题刷屏式上传。</p>
 */
public final class TelemetryService {

    private static final Duration TIMEOUT = Duration.ofSeconds(8);
    private static final long THROTTLE_MILLIS = 5 * 60 * 1000L;
    private static final int STACK_LIMIT = 4000;
    private static final double SPEED_LIMIT = 50.0;
    private static final double TELEPORT_LIMIT = 200.0;

    private static final Map<String, Long> LAST_REPORT = new ConcurrentHashMap<>();

    private static volatile boolean started;
    private static double lastX;
    private static double lastY;
    private static double lastZ;
    private static long lastSampleAt;
    private static boolean hasLastPosition;

    private TelemetryService() {
    }

    /** 安装崩溃钩子并启动异常行为采样；幂等。 */
    public static void start() {
        if (started) return;
        started = true;
        installCrashHook();
        BackgroundTasks.schedule("yiyiaddon-telemetry", 1L, TimeUnit.SECONDS, TelemetryService::sample);
    }

    private static void installCrashHook() {
        Thread.UncaughtExceptionHandler previous = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            try {
                String name = thread == null ? null : thread.getName();
                // 本模组自身的上报线程崩溃时不再上报，避免递归。
                if (name == null || !name.startsWith("yiyiaddon")) reportCrash(throwable);
            } catch (Exception ignored) {
                // 遥测失败绝不影响原有崩溃处理。
            }
            if (previous != null) previous.uncaughtException(thread, throwable);
        });
    }

    /** 上报一个崩溃。 */
    public static void reportCrash(Throwable throwable) {
        if (throwable == null) return;
        JsonObject body = new JsonObject();
        body.addProperty("message", throwable.getClass().getName() + ": " + throwable.getMessage());
        body.addProperty("stack_trace", stackTrace(throwable));
        body.addProperty("version", ClientIdentity.version());
        body.addProperty("minecraft_version", ClientIdentity.minecraftVersion());
        BackgroundTasks.run("yiyiaddon-crash-report",
                () -> HttpApi.post("/api/crash/report", body, TIMEOUT));
    }

    /**
     * 上报一次异常行为。
     *
     * @param type     类型标识，如 high_speed / teleport
     * @param severity low / medium / high
     */
    public static void reportAnomaly(String type, String severity, String message, String data) {
        long now = System.currentTimeMillis();
        Long last = LAST_REPORT.get(type);
        if (last != null && now - last < THROTTLE_MILLIS) return;
        LAST_REPORT.put(type, now);

        JsonObject body = new JsonObject();
        body.addProperty("type", type);
        body.addProperty("severity", severity);
        body.addProperty("message", message);
        body.addProperty("data", data);
        body.addProperty("uuid", ClientIdentity.uuidString());
        body.addProperty("name", ClientIdentity.name());
        body.addProperty("version", ClientIdentity.version());
        body.addProperty("minecraft_version", ClientIdentity.minecraftVersion());
        BackgroundTasks.run("yiyiaddon-anomaly-report",
                () -> HttpApi.post("/api/anomaly/report", body, TIMEOUT));
    }

    /** 每秒采样一次位移，识别高速移动与瞬移。 */
    private static void sample() {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) {
            hasLastPosition = false;
            lastSampleAt = 0L;
            return;
        }

        long now = System.currentTimeMillis();
        double x = player.getX();
        double y = player.getY();
        double z = player.getZ();
        if (!hasLastPosition) {
            lastX = x;
            lastY = y;
            lastZ = z;
            lastSampleAt = now;
            hasLastPosition = true;
            return;
        }

        double seconds = Math.max(0.05, (now - lastSampleAt) / 1000.0);
        double dx = x - lastX;
        double dy = y - lastY;
        double dz = z - lastZ;
        double horizontal = Math.sqrt(dx * dx + dz * dz);
        double speed = horizontal / seconds;

        if (speed > SPEED_LIMIT) {
            reportAnomaly("high_speed", "high",
                    String.format(Locale.ROOT, "高速移动 %.1f m/s", speed),
                    String.format(Locale.ROOT, "horizontal=%.1f, dt=%.2f", horizontal, seconds));
        }

        double total = Math.sqrt(dx * dx + dy * dy + dz * dz);
        if (total > TELEPORT_LIMIT) {
            reportAnomaly("teleport", "medium",
                    String.format(Locale.ROOT, "疑似瞬移 %.1f 格", total),
                    String.format(Locale.ROOT, "dx=%.1f, dy=%.1f, dz=%.1f", dx, dy, dz));
        }

        lastX = x;
        lastY = y;
        lastZ = z;
        lastSampleAt = now;
    }

    private static String stackTrace(Throwable throwable) {
        StringWriter writer = new StringWriter();
        throwable.printStackTrace(new PrintWriter(writer));
        String text = writer.toString();
        return text.length() > STACK_LIMIT ? text.substring(0, STACK_LIMIT) : text;
    }
}
