package com.yiyiaddon.module;

/**
 * 模块分类数据对象。
 *
 * <p>分类不持有模块列表：模块与分类的从属关系由 {@link ModuleRegistry} 单向维护，
 * 避免同一份数据存在两个来源。</p>
 *
 * @param id          分类 ID，全局唯一，作为模块归属的引用键
 * @param displayName 分类中文名
 * @param description 分类描述
 * @param icon        Material Symbols 图标码位
 * @param order       排序权重，越小越靠前
 */
public record ModuleCategory(String id, String displayName, String description, String icon, int order) {
}
