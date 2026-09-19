package com.yiyiaddon.mixin.client;

import com.mojang.blaze3d.opengl.GlCommandEncoder;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.yiyiaddon.compat.VertexFormatBindingsPad;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * 把原版顶点格式校验里「写死按 16 槽索引」的那次取值，改成按需补足槽位后再索引，
 * 避免 Iris 装包时返回的短数组（长度 1）触发
 * {@code ArrayIndexOutOfBoundsException: Index 1 out of bounds for length 1} 崩客户端。
 *
 * <p><b>为什么只在本线存在</b>：这段校验（{@code GlCommandEncoder#validateDraw}）是 26.2 新加的，
 * 26.1.2 没有这个方法，因此本混入不入 master（详见 {@link VertexFormatBindingsPad}）。</p>
 *
 * <p><b>为什么改在这里</b>：{@code validateDraw} 整段被 {@code GlRenderPass.VALIDATION}
 * （= {@code SharedConstants.IS_RUNNING_IN_IDE}）包着，只在开发环境跑；把取值口径改成
 * 「短数组补齐 16 槽、多余槽位为 null」，与「16 槽数组里没声明的槽位是 null」语义相同，
 * 判断结果与原意一致，且长度已够时不复制、不分配。</p>
 */
@Mixin(GlCommandEncoder.class)
public abstract class GlCommandEncoderValidateMixin {

    @Redirect(
            method = "validateDraw",
            at = @At(
                    value = "INVOKE",
                    target = "Lcom/mojang/blaze3d/pipeline/RenderPipeline;getVertexFormatBindings()[Lcom/mojang/blaze3d/vertex/VertexFormat;"
            )
    )
    private VertexFormat[] yiyiaddon$padVertexFormatBindings(RenderPipeline pipeline) {
        return VertexFormatBindingsPad.pad(pipeline.getVertexFormatBindings());
    }
}
