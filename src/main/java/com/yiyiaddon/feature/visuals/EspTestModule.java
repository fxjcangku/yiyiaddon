package com.yiyiaddon.feature.visuals;

import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.feature.visuals.ui.EspTestPage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.render.world.ColorPresets;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.EspGlobalSettings;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.ShapeMode;
import com.yiyiaddon.ui.render.world.WorldOverlay;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * ESP 渲染测试模块：把世界渲染能力逐个挂进世界里做实测。
 *
 * <p><b>这是脚手架，不是业务模块。</b>每个测试项对应一层 {@link WorldOverlay} 绘制回调，
 * 再次点击即注销；模块停用时清空全部层，不留残留。业务模块移植完成后可整体删除。</p>
 *
 * <p>状态放在模块而不是页面上：模块页面每次打开都会重建，放页面上会丢。</p>
 *
 * <p>颜色一律取自 {@link ColorPresets}，不写字面量；需要精细取色时接调色板。
 * 每个测试项用不同颜色，便于在画面上区分是哪一项在生效。</p>
 */
public final class EspTestModule extends Module {

    public static final String MODULE_ID = "esp_test";

    /** 图标字形；与视觉分类预留的同一字形，已确认存在于所引字体。 */
    private static final String ICON = "\uE8F4";

    public static final String T_PLAYER_BOX = "esp-test-player-box";
    public static final String T_PLAYER_BOX2D = "esp-test-player-box2d";
    public static final String T_CROSSHAIR = "esp-test-crosshair";
    public static final String T_ENTITIES = "esp-test-entities";
    public static final String T_RAINBOW = "esp-test-rainbow";
    public static final String T_TEXT = "esp-test-text";
    public static final String T_GRADIENT = "esp-test-gradient";

    private static final int RED = ColorPresets.rgb(0);
    private static final int RED_FILL = ColorPresets.rgb(0, ColorPresets.DEFAULT_FILL_ALPHA);
    private static final int GREEN = ColorPresets.rgb(1);
    private static final int BLUE = ColorPresets.rgb(2);
    private static final int BLUE_FILL = ColorPresets.rgb(2, ColorPresets.DEFAULT_FILL_ALPHA);
    private static final int CYAN = ColorPresets.rgb(3);
    private static final int YELLOW = ColorPresets.rgb(4);
    private static final int PURPLE = ColorPresets.rgb(5);
    private static final int WHITE = ColorPresets.rgb(6);

    /** 实测范围：超过这个距离的目标不画，避免把画面糊满。 */
    private static final double RANGE = 48d;

    /** 浮空字的基准：该距离处用基准字号，其它距离按比例缩放。 */
    private static final double TEXT_BASE_DISTANCE = 8d;
    private static final float TEXT_BASE_SIZE = 12f;
    private static final float TEXT_MIN_SIZE = 6f;
    private static final float TEXT_MAX_SIZE = 28f;

    /** 已开启的测试项；用 LinkedHashSet 保证注销顺序稳定。 */
    private final Set<String> active = new LinkedHashSet<>();

    /** 彩虹颜色对象：同一个实例改相位偏移即可，避免逐目标新建。 */
    private final EspColor rainbow = EspColor.preset(3, 0xFF).rainbow(true).rainbowSpeed(0.5d);

    public EspTestModule() {
        super(MODULE_ID, "ESP测试", "tools", "世界渲染能力实测：框 / 线 / 面 / 射线 / 浮空字");
    }

    @Override
    public String icon() {
        return ICON;
    }

    @Override
    public int order() {
        return 900;
    }

    @Override
    protected void onDisable() {
        clearTests();
    }

    @Override
    public ModulePage page() {
        return new EspTestPage(this);
    }

    // ── 测试层开关（供页面调用） ──

    public boolean isTestActive(String id) {
        return active.contains(id);
    }

    public int activeCount() {
        return active.size();
    }

    public void toggleTest(String id) {
        if (active.remove(id)) {
            WorldOverlay.unregister(id);
            return;
        }
        WorldOverlay.register(id, layerOf(id));
        active.add(id);
    }

    /** 清空全部测试层；模块停用与页面按钮都走这里。 */
    public void clearTests() {
        for (String id : active) WorldOverlay.unregister(id);
        active.clear();
    }

    private WorldOverlay.Layer layerOf(String id) {
        return switch (id) {
            case T_PLAYER_BOX -> EspTestModule::drawPlayerBox;
            case T_PLAYER_BOX2D -> EspTestModule::drawPlayerBox2D;
            case T_CROSSHAIR -> EspTestModule::drawCrosshairBlock;
            case T_ENTITIES -> EspTestModule::drawEntities;
            case T_RAINBOW -> this::drawRainbowTracer;
            case T_TEXT -> EspTestModule::drawFloatingText;
            case T_GRADIENT -> EspTestModule::drawGradient;
            default -> renderer -> { };
        };
    }

    // ── 测试项实现 ──

    /**
     * 本地玩家相关测试项在第一人称下默认不画。
     *
     * <p>旧框架的 ESP 有两条自过滤判据（{@code ignore-self} 与「第一人称跳过相机实体」）。
     * 本模块这三项的主体就是自己（玩家线框盒 / 玩家屏幕框 / 眼前的渐变面），第一人称下画出来
     * 只会糊在准星上挡视野；切到第三人称（F5）才画，那时正好用来对比 3D 与 2D 两种形态。</p>
     *
     * <p>两条判据都可在「ESP 全局设置」里改：关掉第一人称隐藏就用原版观感，打开第三人称隐藏
     * 连 F5 下的自己也一起收掉。</p>
     */
    private static boolean selfHidden() {
        Minecraft client = Minecraft.getInstance();
        boolean firstPerson = client.options == null || client.options.getCameraType().isFirstPerson();
        return EspGlobalSettings.get().hideSelf(firstPerson);
    }

    /** 3D 线框盒：透视下应呈现立体缩形，不是屏幕对齐矩形。 */
    private static void drawPlayerBox(EspRenderer renderer) {
        if (selfHidden()) return;
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;
        renderer.box(client.player.getBoundingBox(), RED_FILL, RED, ShapeMode.Both, 2f);
    }

    /** 屏幕轴对齐包围框：与本项目独有的 2D 形态对比 3D 的差异。 */
    private static void drawPlayerBox2D(EspRenderer renderer) {
        if (selfHidden()) return;
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;
        renderer.box2D(client.player.getBoundingBox(), 0, GREEN, ShapeMode.Lines, 2f);
    }

    /** 准星指向的方块高亮。 */
    private static void drawCrosshairBlock(EspRenderer renderer) {
        Minecraft client = Minecraft.getInstance();
        if (!(client.hitResult instanceof BlockHitResult hit)) return;
        BlockPos pos = hit.getBlockPos();
        renderer.blockBox(pos.getX(), pos.getY(), pos.getZ(), BLUE_FILL, BLUE, ShapeMode.Both, 2f);
    }

    /** 实体线框 + 射线：验证批量绘制与 tracer 起点是否正确落在屏幕底部中心。 */
    private static void drawEntities(EspRenderer renderer) {
        Minecraft client = Minecraft.getInstance();
        if (client.level == null || client.player == null) return;

        Vec3 eye = client.player.getEyePosition();
        for (Entity entity : client.level.entitiesForRendering()) {
            if (entity == client.player) continue;
            AABB box = entity.getBoundingBox();
            Vec3 center = box.getCenter();
            if (eye.distanceToSqr(center) > RANGE * RANGE) continue;
            renderer.box(box, 0, YELLOW, ShapeMode.Lines, 1.5f);
            renderer.tracer(center, CYAN, 1.2f);
        }
    }

    /** 彩虹射线：每个目标错开相位，验证同一颜色对象能画出不同色。 */
    private void drawRainbowTracer(EspRenderer renderer) {
        Minecraft client = Minecraft.getInstance();
        if (client.level == null || client.player == null) return;

        Vec3 eye = client.player.getEyePosition();
        int index = 0;
        for (Entity entity : client.level.entitiesForRendering()) {
            if (entity == client.player) continue;
            Vec3 center = entity.getBoundingBox().getCenter();
            if (eye.distanceToSqr(center) > RANGE * RANGE) continue;
            rainbow.rainbowOffset((index++ * 0.12d) % 1d);
            renderer.tracer(center, rainbow, 1.5f);
        }
    }

    /** 浮空字：位置在碰撞箱上方，字号随距离缩放（近大远小）。 */
    private static void drawFloatingText(EspRenderer renderer) {
        Minecraft client = Minecraft.getInstance();
        if (client.level == null || client.player == null) return;

        Vec3 eye = client.player.getEyePosition();
        for (Entity entity : client.level.entitiesForRendering()) {
            if (entity == client.player) continue;
            AABB box = entity.getBoundingBox();
            Vec3 center = box.getCenter();
            double distance = eye.distanceTo(center);
            if (distance > RANGE) continue;

            float size = (float) (TEXT_BASE_SIZE * (TEXT_BASE_DISTANCE / Math.max(distance, 1d)));
            size = Math.max(TEXT_MIN_SIZE, Math.min(size, TEXT_MAX_SIZE));
            // §l = 加粗（用户 2026-09-19：「所有的点位模块都要字体加粗」，当时未覆盖本测试模块，后续补上）
            renderer.text("§l" + entity.getName().getString(), center.x, box.maxY + 0.35d, center.z,
                    size, WHITE, 1f, true);
        }
    }

    /** 渐变面 + 双色线：验证这两项分段近似实现的实际观感。 */
    private static void drawGradient(EspRenderer renderer) {
        // 渐变面就贴在眼前 1.5 格，第一人称下等于糊在准星上，切第三人称才看得出效果
        if (selfHidden()) return;
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) return;

        AABB box = client.player.getBoundingBox().inflate(0.4d);
        renderer.line(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, PURPLE, CYAN, 2f);

        Vec3 look = client.player.getLookAngle();
        Vec3 flat = new Vec3(look.x, 0d, look.z);
        double length = flat.length();
        // 垂直俯视/仰视时水平投影为零，此时没有「前方」可言，只留双色线
        if (length < 1.0E-4d) return;

        Vec3 base = client.player.position().add(flat.scale(1.5d / length));
        renderer.gradientQuadVertical(base.x - 0.5d, base.y, base.z - 0.5d,
                base.x + 0.5d, base.y + 2d, base.z + 0.5d, PURPLE, CYAN);
    }
}
