package com.yiyiaddon.model.autochest;

/**
 * 自动箱子取物模式。
 *
 * <p>决定打开容器后「按什么规则把物品取进背包」：</p>
 * <ul>
 *   <li>按目标数量取：每种目标物品单独配置目标数量，玩家已有部分则只补差额。</li>
 *   <li>目标物品拿空：只拿目标物品列表内的物品，且拿空容器中该类物品。</li>
 *   <li>全部拿空：忽略目标列表，取走容器内所有能合法放入背包的物品。</li>
 * </ul>
 *
 * <p>显示名逐字取自旧项目 {@code autochest/model/WithdrawMode.java:16,19,22}，禁止改写。</p>
 */
public enum WithdrawMode {

    /** 按目标数量取：每种目标物品单独配置目标数量 */
    TARGET_COUNT("按目标数量取"),

    /** 目标物品拿空：只拿空已加入目标列表的物品 */
    TARGET_EMPTY("目标物品拿空"),

    /** 全部拿空：忽略目标列表，取走容器内所有合法可取物品 */
    TAKE_ALL("全部拿空");

    private final String displayName;

    WithdrawMode(String displayName) {
        this.displayName = displayName;
    }

    /** 中文文案，供界面与提示使用 */
    public String displayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
