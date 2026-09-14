package com.yiyiaddon.feature.stardew.profile;

import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.ResourceManager;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HexFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 星露谷资源「内容指纹」。
 *
 * <p><b>为什么不能用资源 id 列表做指纹：</b>过去的运行时指纹只把
 * {@code customcrops:items/xxx.json} 这类**路径字符串**排序后哈希。两个服务器只要资源文件名
 * 相同（同款星露谷包改名、换贴图、改模型 JSON），路径集合完全一致，指纹就完全相同——
 * 真机已复现 A / B 两服指纹一致的串档事故。指纹必须反映**内容**，不能反映**名字**。</p>
 *
 * <p><b>纳入范围（当前实际生效的 {@code customcrops} 客户端资源，全部为真实字节）：</b></p>
 * <ul>
 *   <li>{@code items/*.json} —— 物品定义（{@code ITEM_MODEL} 组件取值来源）</li>
 *   <li>{@code models/item/*.json} —— 物品模型（含贴图引用与显示参数）</li>
 *   <li>{@code models/block/*.json} —— 方块世界模型（盆 dry/wet、洒水器识别依据）</li>
 *   <li>{@code blockstates/*.json} —— 方块状态定义（模型选择依据）</li>
 *   <li>{@code textures/**} —— 贴图**实际字节**（同路径换图必须改变指纹）</li>
 *   <li>{@code lang/*.json} —— 语言文件（直接决定 Profile 语义里的中文名，属语义相关资源）</li>
 * </ul>
 *
 * <p><b>资源覆盖语义：</b>{@link ResourceManager#listPacks()} 低优先级在前、高优先级（服务器资源包）
 * 在后，因此倒序遍历即高优先级优先；同一资源 id 只保留最高优先级包的字节，与
 * {@link StardewResourceScanner} 的取值口径一致——指纹必须描述「真正生效的那一份」。</p>
 *
 * <p><b>稳定性：</b>按 {@code namespace:path} 稳定排序后再做 SHA-256，输出前 12 位十六进制。
 * 同一份资源内容 → 指纹恒定；路径不变而 JSON / 模型 / 贴图字节变化 → 指纹必然变化；
 * 不同服务器的同名资源只要内容不同 → 指纹必然不同。指纹**不掺入 ServerKey**——
 * 那是隔离键，不是内容；掺进去只会掩盖内容指纹本身的错误。</p>
 *
 * <p>调用时机：玩家主动「检测 / 提取」完成、资源已真正进入 ResourceManager 之后各调用一次，
 * 不做轮询。条目上限 {@link #MAX_ENTRIES} 防止异常巨大的资源包拖垮客户端。</p>
 *
 * @author yiyijia
 */
public final class StardewResourceFingerprint {

    /** 纳入内容指纹的资源目录（均位于 {@code customcrops} 命名空间下） */
    private static final String[] DIRECTORIES = {
        "items",
        "models/item",
        "models/block",
        "blockstates",
        "textures",
        "lang"
    };

    /** 单次指纹计算的资源条目上限（防异常大包卡顿） */
    private static final int MAX_ENTRIES = 20000;

    /** 读取缓冲大小 */
    private static final int BUFFER_SIZE = 8192;

    private StardewResourceFingerprint() {
    }

    /**
     * 计算当前实际生效的星露谷资源内容指纹。
     *
     * @return 12 位十六进制指纹；当前没有任何星露谷资源可取时返回 {@code null}（绝不伪造）
     */
    public static String compute() {
        ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        if (resourceManager == null) return null;

        List<PackResources> packs;
        try {
            packs = resourceManager.listPacks().toList();
        } catch (Exception ignored) {
            return null;
        }

        // 高优先级包优先占位；同一 id 只保留最先生效的字节
        Map<String, String> entries = new LinkedHashMap<>();
        for (int i = packs.size() - 1; i >= 0 && entries.size() < MAX_ENTRIES; i--) {
            PackResources pack = packs.get(i);
            try {
                for (String namespace : pack.getNamespaces(PackType.CLIENT_RESOURCES)) {
                    if (!StardewResourceScanner.STARDEW_NAMESPACE.equals(namespace)) continue;
                    for (String directory : DIRECTORIES) {
                        if (entries.size() >= MAX_ENTRIES) break;
                        collect(pack, namespace, directory, entries);
                    }
                }
            } catch (Exception ignored) {
                // 单个包损坏：跳过该包，继续其它包
            }
        }

        if (entries.isEmpty()) return null;
        return digestOf(entries);
    }

    /** 枚举某个包 / 命名空间 / 目录下的资源，逐条读取真实字节并哈希 */
    private static void collect(PackResources pack, String namespace, String directory,
                                Map<String, String> entries) {
        try {
            pack.listResources(PackType.CLIENT_RESOURCES, namespace, directory, (id, supplier) -> {
                if (entries.size() >= MAX_ENTRIES) return;
                String key = id.toString();          // namespace:path —— 命名空间与路径都进指纹
                if (entries.containsKey(key)) return; // 已被更高优先级包定义，取生效的那一份
                entries.put(key, hashOf(supplier));
            });
        } catch (Exception ignored) {
            // 单个目录枚举失败不影响其它目录
        }
    }

    /** 读取资源真实字节并哈希；读取失败记 {@code unreadable}（路径仍参与指纹，不静默丢弃） */
    private static String hashOf(IoSupplier<InputStream> supplier) {
        try (InputStream in = supplier.get()) {
            if (in == null) return "unreadable";
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] buffer = new byte[BUFFER_SIZE];
            int read;
            while ((read = in.read(buffer)) != -1) {
                digest.update(buffer, 0, read);
            }
            return HexFormat.of().formatHex(digest.digest(), 0, 8);
        } catch (Exception ignored) {
            return "unreadable";
        }
    }

    /** 稳定排序后按「路径 + 内容哈希」做 SHA-256，输出前 12 位十六进制 */
    private static String digestOf(Map<String, String> entries) {
        List<String> keys = new ArrayList<>(entries.keySet());
        Collections.sort(keys);
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            for (String key : keys) {
                digest.update(key.getBytes(StandardCharsets.UTF_8));
                digest.update((byte) 0);
                digest.update(entries.get(key).getBytes(StandardCharsets.UTF_8));
                digest.update((byte) '\n');
            }
            return HexFormat.of().formatHex(digest.digest(), 0, 6);
        } catch (Exception ignored) {
            return null;
        }
    }
}
