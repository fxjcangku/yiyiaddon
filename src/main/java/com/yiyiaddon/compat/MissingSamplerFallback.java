package com.yiyiaddon.compat;

import com.mojang.blaze3d.opengl.GlRenderPass;
import com.mojang.blaze3d.opengl.GlRenderPipeline;
import com.mojang.blaze3d.opengl.GlSampler;
import com.mojang.blaze3d.opengl.GlTextureView;
import com.mojang.blaze3d.opengl.Uniform;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import com.mojang.blaze3d.textures.GpuSampler;
import com.mojang.blaze3d.textures.GpuTextureView;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.OverlayTexture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * {@code GlCommandEncoder#trySetup}（每次 draw 前的状态校验）的两条兜底。
 *
 * <p><b>职责一：补绑缺失的原版采样器。</b>原版每次 draw 前会拿编译好的着色器程序里的活跃
 * sampler 逐个到 RenderPass 里找绑定，找不到就抛 {@code IllegalStateException: Missing sampler
 * Sampler1}。开了光影（Iris 1.11.x）时，程序是按光影包的 shader 变体编译的，而 RenderPass 里的
 * 纹理绑定仍由原版 {@code RenderSetup} 按渲染类型决定——两边偶发对不上：实体/物品一类渲染类型
 * （原版 {@code entity.vsh} 在未定义 {@code NO_OVERLAY} 时会 {@code texelFetch(Sampler1, UV1, 0)}）
 * 的程序要 Sampler1，绑定里却没有，于是进服务器/附近有实体手持物品时直接崩。补绑的纹理
 * <b>就是原版自己会绑的那张</b>（Sampler1 = 叠加纹理、Sampler2 = 光照贴图，采样器都是
 * clamp-to-edge + 线性），因此取值与「不开光影」时一致：UV1 为 0 的几何取到的就是叠加纹理里的
 * 白色，也就是原版「无叠加」的语义，不会引入错的亮度或颜色。</p>
 *
 * <p><b>职责二：把「绑定已被关闭」的绘制跳过，别让客户端整个炸掉。</b>原版同一个循环里还有一条
 * 检查（{@code GlCommandEncoder} 26.1.2 第 429 行）：
 * <pre>
 *   if (view.isClosed()) throw new IllegalStateException(name + " (" + texture.getLabel() + ") has been closed!");
 * </pre>
 * 即「当前程序要的采样器槽位绑着一张已经被删除的纹理」。这是<b>别处（重载/替换纹理的一方）的
 * 时序问题</b>，不是本模组能修的：本机开发环境里稳定复现（2026-09-16 四次、2026-09-20 五次），
 * 每次都是资源重载后 20-30 秒、服务器列表 ping 失败那一瞬间，栈全在
 * {@code GlCommandEncoder.trySetup ← GuiRenderer.executeDraw}，没有本模组任何一帧。原版的处理
 * 方式是抛异常结束进程，于是整局游戏没了；这里改成<b>只跳过这一次绘制</b>——{@code trySetup}
 * 返回 false 的原版语义就是「本次绘制不执行」（{@code executeDraw} 收到 false 直接 return），
 * 画面少一笔，进程活着，并且留一条带纹理标签的告警便于定位是谁在关纹理。</p>
 *
 * <p><b>边界</b>：① 已绑定且可用的槽位一律不动（不覆盖光影自己的绑定）；② 只有「当前程序的
 * 采样器槽位确实绑着已关闭的纹理/采样器」才跳过绘制，程序用不到的槽位一律照原样继续，绝不因为
 * 多绑了一张废弃纹理而少画；③ 兜底自身出任何异常只记一条日志并永久停用，绝不再抛第二遍异常。</p>
 */
public final class MissingSamplerFallback {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/render-compat");

    /** 叠加（overlay）槽位：原版 {@code RenderSetup#useOverlay()} 绑的就是它。 */
    private static final String OVERLAY_SLOT = "Sampler1";
    /** 光照贴图槽位：原版 {@code RenderSetup#useLightmap()} 绑的就是它。 */
    private static final String LIGHTMAP_SLOT = "Sampler2";

    /** 「绑定已被关闭」告警的去重上限：只为留痕，不做无界累积、也不刷日志。 */
    private static final int MAX_CLOSURE_REPORTS = 16;

    /** 补绑失败（或环境不满足）后置位，之后不再进这个方法做任何事。 */
    private static boolean disabled;
    /** 只播报一次，避免刷日志。 */
    private static boolean announced;
    /** 已经告警过的「已关闭纹理」标签。 */
    private static final Set<String> reportedClosures = new HashSet<>();

    private MissingSamplerFallback() {
    }

    /**
     * 在 draw 前的采样器校验之前调用。
     *
     * <p>调用点每帧会走很多次：先做一次「绑定的纹理/采样器还活着吗」的扫描（通常只有 1-3 条
     * 绑定，空表直接返回），确认有问题的才去核对当前程序的采样器槽位；再走原有的补绑逻辑
     * （两个槽位都已绑定就立刻返回）。</p>
     *
     * @return {@code false}：本次绘制引用了已关闭的纹理或采样器，原版随后必抛异常 —— 调用方应
     *         让这次绘制直接跳过；{@code true}：一切照原版继续
     */
    public static boolean apply(GlRenderPass pass) {
        if (disabled) {
            return true;
        }
        try {
            String unusableSlot = unusableSlot(pass);
            if (unusableSlot != null) {
                reportClosure(unusableSlot, pass);
                return false;
            }
            topUpMissingSamplers(pass);
            return true;
        } catch (Throwable throwable) {
            disabled = true;
            LOGGER.warn("渲染兼容兜底已停用（补绑原版采样器失败）；光影下的同类崩溃可能复现", throwable);
            return true;
        }
    }

    /**
     * 找出「本次绘制真的要、但绑的纹理/采样器已经关掉了」的采样器槽位。
     *
     * <p>两段式：先扫绑定表（空表/没有死绑定就直接结束，绝大多数 draw 都止步于此），
     * 真发现死绑定后，再照原版口径核对它是不是当前着色器程序声明的采样器 —— 不是的话原版
     * 根本不会检查它，我们也不能因此少画一笔。</p>
     *
     * @return 有问题的槽位名；没有问题返回 null
     */
    private static String unusableSlot(GlRenderPass pass) {
        HashMap<String, GlRenderPass.TextureViewAndSampler> bound = pass.samplers;
        if (bound == null || bound.isEmpty()) {
            return null;
        }
        String closedSlot = null;
        for (Map.Entry<String, GlRenderPass.TextureViewAndSampler> entry : bound.entrySet()) {
            GlRenderPass.TextureViewAndSampler binding = entry.getValue();
            if (binding == null) {
                continue;
            }
            GlTextureView view = binding.view();
            GlSampler sampler = binding.sampler();
            if ((view != null && view.isClosed()) || (sampler != null && sampler.isClosed())) {
                closedSlot = entry.getKey();
                break;
            }
        }
        if (closedSlot == null) {
            return null;
        }
        GlRenderPipeline pipeline = pass.pipeline;
        if (pipeline == null) {
            // 没有管线时原版抛的是「Can't draw without a render pipeline」，不归这里管
            return null;
        }
        Uniform uniform = pipeline.program().getUniforms().get(closedSlot);
        return uniform instanceof Uniform.Sampler ? closedSlot : null;
    }

    /**
     * 补上程序需要、但当前 RenderPass 没绑的原版槽位（职责一）。
     *
     * <p>不开光影时几乎每次都是「两个槽位都已绑定」这条路，开光影时也只有真正缺失的
     * 那几个 draw 才会补绑。</p>
     */
    private static void topUpMissingSamplers(GlRenderPass pass) {
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
    }

    /** 把「跳了哪一次绘制、倒在哪张纹理上」写进日志，同一张纹理只报一次。 */
    private static void reportClosure(String slot, GlRenderPass pass) {
        GlRenderPass.TextureViewAndSampler binding = pass.samplers.get(slot);
        GlTextureView view = binding == null ? null : binding.view();
        String label = view == null ? "?" : String.valueOf(view.texture().getLabel());
        if (!reportedClosures.add(label) || reportedClosures.size() > MAX_CLOSURE_REPORTS) {
            return;
        }
        LOGGER.warn("采样器 {} 绑定的纹理（{}）已被关闭，本次绘制按「跳过」处理；"
                + "这是纹理重载/替换与 GUI 渲染之间的时序问题，不是本模组的资源", slot, label);
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
