package com.yiyiaddon.compat;

import com.mojang.blaze3d.opengl.GlRenderPass;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTextureView;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 「Missing sampler Sampler1」崩溃的兜底补绑。
 *
 * <p><b>问题</b>：原版 {@code GlCommandEncoder.trySetup} 每次 draw 前会拿编译好的着色器程序里
 * 的活跃 sampler 逐个到 RenderPass 里找绑定，找不到就抛 {@code IllegalStateException}。
 * 开了光影（Iris 1.11.x）时，程序是按光影包的 shader 变体编译的，而 RenderPass 里的纹理绑定
 * 仍由原版 {@code RenderSetup} 按渲染类型决定——两边偶发对不上：实体/物品一类渲染类型
 * （原版 {@code entity.vsh} 在未定义 {@code NO_OVERLAY} 时会 {@code texelFetch(Sampler1, UV1, 0)}）
 * 的程序要 Sampler1，绑定里却没有，于是进服务器/附近有实体手持物品时直接崩。</p>
 *
 * <p><b>兜底口径</b>：只在「程序需要的槽位确实没绑定」时，补上<b>原版自己会绑的那张纹理</b>——
 * 与原版 {@code RenderSetup.useOverlay()}/{@code useLightmap()} 用的完全相同
 * （Sampler1 = 叠加纹理、Sampler2 = 光照贴图，采样器都是 clamp-to-edge + 线性），
 * 因此补绑后的取值与「不开光影」时一致：UV1 为 0 的几何取到的就是叠加纹理里的白色，
 * 也就是原版「无叠加」的语义，不会引入错的亮度或颜色。</p>
 *
 * <p><b>边界</b>：已绑定的一律不动（不覆盖光影自己的绑定）；补绑失败只记一条日志并永久停用，
 * 绝不影响原版行为、也绝不再抛第二遍异常。</p>
 */
public final class MissingSamplerFallback {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/render-compat");

    /** 叠加（overlay）槽位：原版 {@code RenderSetup#useOverlay()} 绑的就是它。 */
    private static final String OVERLAY_SLOT = "Sampler1";
    /** 光照贴图槽位：原版 {@code RenderSetup#useLightmap()} 绑的就是它。 */
    private static final String LIGHTMAP_SLOT = "Sampler2";

    /** 补绑失败（或环境不满足）后置位，之后不再进这个方法做任何事。 */
    private static boolean disabled;
    /** 只播报一次，避免刷日志。 */
    private static boolean announced;

    private MissingSamplerFallback() {
    }

    /**
     * 在 draw 前的采样器校验之前调用：把程序需要、但当前 RenderPass 没绑的原版槽位补齐。
     *
     * <p>调用点每帧会走很多次，因此这里的第一件事是「两个槽位都已绑定就立刻返回」——
     * 不开光影时几乎每次都是这条路，开光影时也只有真正缺失的那几个 draw 才会补绑。</p>
     */
    public static void apply(GlRenderPass pass) {
        if (disabled) {
            return;
        }
        try {
            Map<String, ?> bound = pass.samplers;
            if (bound == null) {
                return;
            }
            boolean needOverlay = !bound.containsKey(OVERLAY_SLOT);
            boolean needLightmap = !bound.containsKey(LIGHTMAP_SLOT);
            if (!needOverlay && !needLightmap) {
                return;
            }

            Minecraft client = Minecraft.getInstance();
            if (client == null || client.gameRenderer == null) {
                return;
            }
            GpuSampler clamp = RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR);

            if (needOverlay) {
                OverlayTexture overlay = client.gameRenderer.overlayTexture();
                if (overlay != null) {
                    pass.bindTexture(OVERLAY_SLOT, overlay.getTextureView(), clamp);
                }
            }
            if (needLightmap) {
                GpuTextureView lightmap = client.gameRenderer.lightmap();
                if (lightmap != null) {
                    pass.bindTexture(LIGHTMAP_SLOT, lightmap, clamp);
                }
            }
            announce(needOverlay, needLightmap);
        } catch (Throwable throwable) {
            disabled = true;
            LOGGER.warn("渲染兼容兜底已停用（补绑原版采样器失败）；光影下的同类崩溃可能复现", throwable);
        }
    }

    private static void announce(boolean overlay, boolean lightmap) {
        if (announced) {
            return;
        }
        announced = true;
        StringBuilder slots = new StringBuilder();
        if (overlay) {
            slots.append(OVERLAY_SLOT).append('(').append("叠加纹理").append(')');
        }
        if (lightmap) {
            if (slots.length() > 0) {
                slots.append('、');
            }
            slots.append(LIGHTMAP_SLOT).append('(').append("光照贴图").append(')');
        }
        LOGGER.info("光影兼容兜底生效：已为缺失的原版采样器补绑 {}（上游 Iris 在 26.1.2 上偶发丢失绑定）",
                slots);
    }
}
