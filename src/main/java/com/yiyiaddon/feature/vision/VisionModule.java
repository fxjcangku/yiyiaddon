package com.yiyiaddon.feature.vision;

import com.google.gson.JsonObject;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.vision.config.VisionSettings;
import com.yiyiaddon.feature.vision.config.VisionTexts;
import com.yiyiaddon.feature.vision.render.VisionBlockRenderer;
import com.yiyiaddon.feature.vision.render.VisionEntityRenderer;
import com.yiyiaddon.feature.vision.scan.BlockTargetScanner;
import com.yiyiaddon.feature.vision.scan.EntityTargetScanner;
import com.yiyiaddon.feature.vision.ui.VisionPage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.WorldOverlay;
import net.minecraft.client.Minecraft;

/**
 * 透视：方块与实体两个模式各自独立，可同时开启互不干扰。
 *
 * <p><b>性质</b>：本模块是<b>用户点名新增</b>的功能，不是旧项目移植资产（旧项目没有「方块 / 实体透视」
 * 模块，取证见 92 号报告第二节）。因此文案与设置项由本项目自定，集中在
 * {@link VisionTexts}，并按《开发习惯》第 111~116 条的配色语义与第 128 条的外观边界执行。</p>
 *
 * <p><b>两个模式</b>（用户口径）：{@code 方块透视}（扫描目标方块并绘制，竖直方向固定上下各 64 格）与
 * {@code 实体透视}（遍历已加载实体并绘制，竖直不裁）。两者各有独立开关、独立目标名单、独立范围、
 * 独立的框 / 射线开关与颜色；默认都关闭、目标名单都为空（用户 2026-09-18 拍板）。</p>
 *
 * <p><b>渲染</b>：模块启用时向 {@link WorldOverlay} 注册一个世界渲染层，层内依次走
 * {@link VisionBlockRenderer} 与 {@link VisionEntityRenderer}；两者各自在入口查 ESP 全局总闸
 * （{@code Layer.VISION}，第 76 号三步）。模块关闭时注销并清空扫描快照。</p>
 *
 * <p><b>不做的事</b>（92 号 D-16-07~09）：不加指令、不加「被遮挡时隐藏」开关、不加名称 / 距离文字标签 ——
 * 用户未要求，按第 163 条不擅自新增。</p>
 */
public final class VisionModule extends Module {

    /** 模块 ID，同时作为状态文件键、快捷键键名后缀与世界渲染层标识 */
    public static final String MODULE_ID = "vision";

    /**
     * 图标字形（Material Symbols：{@code visibility}，即眼睛）。
     *
     * <p>按第 140 条验真：2026-09-18 用一次性脚本解析 {@code MaterialSymbolsRounded.ttf} 的
     * {@code cmap} 与 {@code post} 表，确认字形 {@code visibility} 映射到 U+E417，且与既有 12 个模块图标
     * 及 7 个分类图标无占用冲突；并渲染 PNG 目视确认为「眼睛」。同表的 {@code search} 解析为 U+E8B6
     * 与实际一致，可反证解析口径正确。</p>
     */
    private static final String ICON = "\uE417";

    private final VisionSettings settings = new VisionSettings();
    private final BlockTargetScanner blockScanner = new BlockTargetScanner();
    private final EntityTargetScanner entityScanner = new EntityTargetScanner();
    private final VisionBlockRenderer blockRenderer = new VisionBlockRenderer(this);
    private final VisionEntityRenderer entityRenderer = new VisionEntityRenderer(this);
    private final Minecraft mc = Minecraft.getInstance();

    public VisionModule() {
        super(MODULE_ID, VisionTexts.MODULE_NAME, "visuals", VisionTexts.DESCRIPTION);
    }

    @Override
    public String name() {
        return "Vision";
    }

    @Override
    public String icon() {
        return ICON;
    }

    @Override
    public int order() {
        return 10;
    }

    // ── 对外只读入口（渲染器与控制台读它们，不另建第二份状态） ──

    public VisionSettings settings() {
        return settings;
    }

    public BlockTargetScanner blockScanner() {
        return blockScanner;
    }

    public EntityTargetScanner entityScanner() {
        return entityScanner;
    }

    // ── 设置持久化 ──

    @Override
    public void loadSettings(JsonObject json) {
        settings.load(json);
    }

    @Override
    public void saveSettings(JsonObject json) {
        settings.save(json);
    }

    /** 立即写回设置（界面改动即时生效，第 173 条） */
    public void persistSettings() {
        ModuleManager.saveSettings(this);
    }

    /** 模块页：薄壳入口，设置项由控制台承载（第 181 / 182 / 210 条） */
    @Override
    public ModulePage page() {
        return new VisionPage(this);
    }

    // ── 生命周期 ──

    @Override
    protected void onEnable() {
        blockScanner.clear();
        entityScanner.clear();
        WorldOverlay.register(MODULE_ID, this::renderLayer);
    }

    @Override
    protected void onDisable() {
        WorldOverlay.unregister(MODULE_ID);
        // 关闭即清空结果：再次启用时不会先闪一帧旧框
        blockScanner.clear();
        entityScanner.clear();
    }

    /**
     * 每刻推进两个扫描器。
     *
     * <p>只在对应模式打开时推进；模式关闭后立刻清空该模式的扫描结果，避免关掉开关还留着上一帧的框。</p>
     */
    @Override
    public void onTick(Minecraft client) {
        if (client.player == null || client.level == null) return;

        if (settings.blockEnabled) {
            blockScanner.tick(client.level, client.player.blockPosition(), settings.blockRange, settings.blockTargets);
        } else if (blockScanner.total() != 0 || !blockScanner.visible().isEmpty()) {
            blockScanner.clear();
        }

        if (settings.entityEnabled) {
            entityScanner.tick(client.level, client.player, settings.entityRange, settings.entityTargets);
        } else if (!entityScanner.visible().isEmpty()) {
            entityScanner.clear();
        }
    }

    /** 世界渲染层入口：两条绘制路径各自在内部查 ESP 全局总闸 */
    private void renderLayer(EspRenderer renderer) {
        blockRenderer.render(renderer);
        entityRenderer.render(renderer);
    }
}
