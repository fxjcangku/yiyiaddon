package com.yiyiaddon.compat;

import com.mojang.blaze3d.vertex.VertexFormat;

/**
 * 顶点格式绑定数组的「补足到 16 槽」。
 *
 * <p><b>问题</b>：26.2 新增的 {@code GlCommandEncoder#validateDraw} 在开发环境
 * （{@code GlRenderPass.VALIDATION} 为真）会写死
 * {@code for (int i = 0; i < 16; i++) pipeline.info().getVertexFormatBindings()[i]}，
 * 直接假定这个数组有 16 个槽位。原版自己的 {@code RenderPipeline} 确实永远是 16 槽，
 * 但 Iris（1.11.2）为了按光影包的顶点布局重排缓冲区，给 {@code getVertexFormatBindings}
 * 注入了返回值——装包时返回的是长度只有 1 的数组（单条交错缓冲），于是上面那个循环
 * 一进到 {@code i = 1} 就 {@code ArrayIndexOutOfBoundsException}，正式版因为不跑校验
 * 而侥幸无事，开发端只要开了光影、一进世界就崩。</p>
 *
 * <p><b>兜底口径</b>：把短数组补成 16 槽、多出来的槽位留 {@code null}（= 该槽位没有声明顶点格式），
 * 与「16 槽数组里剩下的是 null」语义完全一致，因此校验的判断结果与 Iris 的本意一致；
 * 长度已经够 16 槽时原样返回，不复制、不分配。</p>
 *
 * <p>只在开发校验里用得到（生产环境不会走到），故这里不做任何缓存：短数组只会出现在
 * 「装了光影包」这一种情况下，每帧的分配量可以忽略。</p>
 */
public final class VertexFormatBindingsPad {

    /** 原版 {@code RenderPipeline#vertexFormatPerBuffer} 的固定槽位数。 */
    private static final int SLOT_COUNT = 16;

    private VertexFormatBindingsPad() {
    }

    /**
     * 按需补足槽位。
     *
     * @param bindings {@code RenderPipeline#getVertexFormatBindings()} 的原样返回值
     * @return 长度不小于 16 的数组；已够长或为 {@code null} 时原样返回
     */
    public static VertexFormat[] pad(final VertexFormat[] bindings) {
        if (bindings == null || bindings.length >= SLOT_COUNT) {
            return bindings;
        }
        VertexFormat[] padded = new VertexFormat[SLOT_COUNT];
        System.arraycopy(bindings, 0, padded, 0, bindings.length);
        return padded;
    }
}
