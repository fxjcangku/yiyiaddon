package com.yiyiaddon.platform.resource;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.ResourceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 资源包读取适配层：按原版资源优先级语义读取任意资源包内的 JSON 资源。
 *
 * <p><b>优先级铁律：</b>{@code ResourceManager#listPacks()} 的顺序是「低优先级在前」
 * （原版包最前，服务器资源包最后），因此必须<b>倒序</b>遍历，取第一个提供该资源的包，
 * 才能读到被服务器资源包覆盖后的定义。</p>
 *
 * <p>读取失败一律返回带原因的失败结果，不吞异常、不静默返回 {@code null}，便于真机定位。</p>
 *
 * <p>本层不做缓存：资源识别属于按需调用，且资源包重载后必须读到新内容。</p>
 */
public final class ResourcePackAccess {

    private static final Logger LOGGER = LoggerFactory.getLogger("yiyiaddon/resourcepack");

    /** 详细追踪开关（默认关闭，避免刷屏）；由上层解析器控制 */
    public static volatile boolean verbose = false;

    private ResourcePackAccess() {
    }

    /**
     * 资源读取结果。
     *
     * @param json   成功时的 JSON 对象
     * @param reason 失败时的可读原因
     */
    public record LoadedResource(JsonObject json, String reason) {

        public boolean ok() {
            return json != null;
        }

        static LoadedResource success(JsonObject json) {
            return new LoadedResource(json, null);
        }

        static LoadedResource failure(String reason) {
            return new LoadedResource(null, reason);
        }
    }

    /** 当前生效的资源管理器；未就绪返回 {@code null} */
    public static ResourceManager resourceManager() {
        return Minecraft.getInstance().getResourceManager();
    }

    /** 当前已加载的资源命名空间；资源管理器不可用时返回空集合 */
    public static Set<String> namespaces() {
        ResourceManager manager = resourceManager();
        return manager == null ? Set.of() : manager.getNamespaces();
    }

    /** 高优先级在前的资源包列表（对 {@code listPacks()} 倒序） */
    public static List<PackResources> packsByPriority() {
        ResourceManager manager = resourceManager();
        if (manager == null) return List.of();
        List<PackResources> packs = new ArrayList<>(manager.listPacks().toList());
        java.util.Collections.reverse(packs);
        return packs;
    }

    /**
     * 按优先级读取一个客户端资源 JSON。
     *
     * @param resourceId 完整资源标识，<b>必须带 {@code .json} 后缀</b>
     *                   （原版 {@code FileToIdConverter.json("blockstates")} 生成的 id 带后缀，
     *                   漏掉后缀会导致所有资源包都读不到）
     * @return 读取结果；失败时 {@code reason} 说明是资源管理器不可用、资源不存在还是读取异常
     */
    public static LoadedResource readJson(Identifier resourceId) {
        if (resourceId == null) return LoadedResource.failure("无效资源标识");

        ResourceManager manager = resourceManager();
        if (manager == null) {
            trace("资源管理器不可用 (RESOURCE_MANAGER_NULL)");
            return LoadedResource.failure("资源管理器不可用");
        }

        try {
            List<PackResources> packs = packsByPriority();
            trace("资源包数量: " + packs.size() + "，请求资源: " + resourceId);

            for (PackResources pack : packs) {
                String packId = pack.packId();
                IoSupplier<InputStream> supplier;
                try {
                    supplier = pack.getResource(PackType.CLIENT_RESOURCES, resourceId);
                } catch (Exception ex) {
                    trace("  -> MISS（读取异常）: " + packId + " -> " + ex);
                    continue;
                }
                if (supplier == null) {
                    trace("  -> MISS: " + packId);
                    continue;
                }

                trace("  -> HIT: " + packId);
                try (InputStream in = supplier.get()) {
                    JsonObject json = JsonParser
                        .parseReader(new InputStreamReader(in, StandardCharsets.UTF_8))
                        .getAsJsonObject();
                    return LoadedResource.success(json);
                } catch (Exception ex) {
                    // 命中但读取 / 解析失败：如实上报，不静默回退到低优先级包
                    trace("Resource read: FAIL -> " + ex);
                    return LoadedResource.failure("读取资源内容异常");
                }
            }
            trace("所有资源包均未命中: " + resourceId);
            return LoadedResource.failure("未找到资源定义");
        } catch (Exception ex) {
            trace("读取资源异常: " + ex);
            return LoadedResource.failure("读取资源异常");
        }
    }

    /** 可控追踪日志：仅 {@link #verbose} 开启时输出 */
    public static void trace(String message) {
        if (verbose) LOGGER.info("[ResourcePack] {}", message);
    }
}
