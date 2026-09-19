package com.yiyiaddon.compat;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlobalSettingsUniform;
import net.minecraft.world.phys.Vec3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 「Missing uniform Globals」崩溃的兜底补建。
 *
 * <p><b>问题</b>：26.2 把图集上传改成了走渲染管线——{@code TextureAtlas.uploadInitialContents}
 * 与 {@code uploadAnimationFrames} 都会先 {@code RenderSystem.bindDefaultUniforms(renderPass)}，
 * 由它把 {@code Globals}（全局设置 UBO）塞进 RenderPass；缺了它，
 * {@code GlCommandEncoder#trySetup} 在 IDE/开发环境（{@code GlRenderPass.VALIDATION} 为真）会直接抛
 * {@code IllegalStateException: Missing uniform Globals (should be UNIFORM_BUFFER)}。</p>
 *
 * <p>而这个 UBO 全游戏只有一个来源：{@code GameRenderer#render} 开头那句
 * {@code globalSettingsUniform.update(...)}。偏偏 26.2 的<b>首次</b>资源重载会在
 * {@code Minecraft.runTick} 的 {@code runAllTasks()} 里完成——排在同一个 tick 的
 * {@code renderFrame()} 之前，也就是<b>首帧还没画过</b>。于是只要首次重载恰好在第一个 tick 收尾
 * （模组越多、构造期越长越容易命中，本仓库的整合包 100% 命中），图集上传时 {@code Globals} 必然为空：
 * 正式版因为 {@code VALIDATION=false} 默默带病画过去，开发端则直接崩客户端。</p>
 *
 * <p><b>兜底口径</b>：在 {@code bindDefaultUniforms} 真正读这个 UBO 之前，用原版自己的
 * {@link GlobalSettingsUniform} 按「首帧、未进世界」的口径算一次（窗口尺寸、闪光强度 1.0、
 * 游戏时间 0、模糊半径 0、相机原点、RGSS 关），与原版首帧 {@code GameRenderer#render} 会写入的值同源；
 * 下一帧 {@code GameRenderer} 自己会重新写入覆盖，因此不改变任何后续帧的渲染结果。</p>
 *
 * <p><b>边界</b>：只在整个 UBO 确实缺失时补建一次；已存在时立即返回（正常游戏中每次都是这条路），
 * 绝不复写原版/光影写入的内容。补建失败只记一条日志并永久停用，绝不影响原版行为、
 * 也绝不再抛第二遍异常。</p>
 */
public final class MissingGlobalUniformFallback {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/render-compat");

    /** 自建的 UBO：一帧之后就会被 {@code GameRenderer} 的同名实例顶替，全程只建一份。 */
    private static GlobalSettingsUniform fallback;
    /** 补建失败（或环境不满足）后置位，之后不再进这个方法做任何事。 */
    private static boolean disabled;
    /** 只播报一次，避免刷日志。 */
    private static boolean announced;

    private MissingGlobalUniformFallback() {
    }

    /**
     * 在 {@code RenderSystem#bindDefaultUniforms} 读 {@code Globals} 之前调用：
     * 原版还没建出这个 UBO 的话，先按首帧口径补一份。
     *
     * <p>调用点每帧会走很多次，因此这里的第一件事是「原版已有 UBO 就立刻返回」——
     * 正常游戏中几乎每次都是这条路。</p>
     */
    public static void ensure() {
        if (disabled || RenderSystem.getGlobalSettingsUniform() != null) {
            return;
        }
        try {
            Minecraft client = Minecraft.getInstance();
            if (client == null || client.getWindow() == null) {
                return;
            }
            DeltaTracker deltaTracker = client.getDeltaTracker();
            if (deltaTracker == null) {
                return;
            }

            GlobalSettingsUniform uniform = fallback;
            if (uniform == null) {
                uniform = new GlobalSettingsUniform();
                fallback = uniform;
            }
            uniform.update(
                    client.getWindow().getWidth(),
                    client.getWindow().getHeight(),
                    1.0F,
                    0L,
                    deltaTracker,
                    0,
                    Vec3.ZERO,
                    false);
            announce();
        } catch (Throwable throwable) {
            disabled = true;
            LOGGER.warn("渲染兼容兜底已停用（补建全局设置 UBO 失败）；开发端首帧前的图集上传崩溃可能复现", throwable);
        }
    }

    private static void announce() {
        if (announced) {
            return;
        }
        announced = true;
        LOGGER.info("渲染兼容兜底生效：首帧之前先补建全局设置 UBO（26.2 首次资源重载早于首帧，图集上传取不到 Globals）");
    }
}
