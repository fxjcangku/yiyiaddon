package com.yiyiaddon.service.resourcepack;

import com.yiyiaddon.model.resource.ResourceScanResult;

import java.util.List;
import java.util.Set;

/**
 * 资源内容探针：由具体业务层实现，向资源生命周期服务描述「当前已加载资源里有什么」。
 *
 * <p>生命周期服务只负责「缓存 → 下载 → 等待生效 → 通知订阅者」这条通用链路，
 * 不认识任何具体服务器的资源结构；识别内容由探针提供。未注册探针时解析结果为空，
 * 生命周期按「未发现目标资源」收尾。</p>
 *
 * <p>实现必须是只读的：不得触发资源重载，不得修改游戏状态。</p>
 */
public interface ResourceContentProbe {

    /**
     * 当前客户端已加载的目标资源标识集合。
     *
     * <p>双重用途：判断「客户端现在有没有目标资源」；以及切服时比对「资源到底换没换」，
     * 防止把上一台服务器的资源当成当前服务器解析。</p>
     */
    Set<String> loadedContentIds();

    /** 解析当前已加载资源，返回分类计数与参与解析的资源 id */
    ResourceScanResult analyze();

    /**
     * 当前实际生效资源的内容指纹（12 位十六进制）。
     *
     * <p>要求按内容计算而非按文件名：同名不同内容必须得到不同指纹。不可用时返回 {@code null}，
     * 由生命周期服务退回 ZIP 整包哈希。</p>
     */
    String contentFingerprint();

    /** 分类名列表，用于固定展示顺序；无分类返回空列表 */
    default List<String> categoryNames() {
        return List.of();
    }
}
