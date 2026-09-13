package com.yiyiaddon.mixin.client;

import com.mojang.blaze3d.systems.GpuDevice;
import com.mojang.blaze3d.systems.GpuDeviceBackend;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * 暴露 {@code GpuDevice} 的后端实现。
 *
 * <p>26.1.2 把 GpuDevice 拆成「API 包装类 + 后端实现」两层，{@code backend} 字段是私有的，
 * 而 Skija 需要从 GL 后端拿到 {@code DirectStateAccess} 才能取得主 RenderTarget 的真实
 * Framebuffer 名字（{@code GlTexture#getFbo}）。这里只读取字段，不改变任何原版行为。</p>
 *
 * <p>配套的可见性提升见 {@code src/main/resources/yiyiaddon.accesswidener}。</p>
 */
@Mixin(GpuDevice.class)
public interface GpuDeviceAccessor {

    @Accessor("backend")
    GpuDeviceBackend yiyiaddon$backend();
}
