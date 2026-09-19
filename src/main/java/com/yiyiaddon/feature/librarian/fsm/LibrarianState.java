package com.yiyiaddon.feature.librarian.fsm;

/**
 * 自动图书管理员 · 状态机状态定义。
 *
 * <p>描述「搜索失业村民 → 放置讲台刷新交易 → 命中目标附魔购买 → 完成」的
 * 完整自动化流程，每个状态由 {@link LibrarianStateMachine} 驱动。</p>
 *
 * <p><b>为什么枚举自带中文名</b>：本枚举会出现在模块列表信息串（{@code getInfoString()}）
 * 与调试播报里，直接输出 {@code IDLE / SEARCH_VILLAGER} 就是给玩家看英文。
 * 中文名统一定义在枚举上，模块与编排器共用同一份映射，不再各自维护一份 switch。</p>
 *
 * <p>迁移自旧项目 {@code librarian/fsm/AutoLibrarianState}（80 行），取值顺序与中文名逐字照搬。</p>
 */
public enum LibrarianState {
    /** 空闲（未运行） */
    IDLE("空闲"),
    /** 启动 */
    START("启动"),
    /** 搜索失业村民 */
    SEARCH_VILLAGER("搜索村民"),
    /** 选中目标村民 */
    SELECT_TARGET_VILLAGER("选择目标村民"),
    /** 寻路移动到村民附近 */
    MOVE_TO_VILLAGER("移动到村民"),
    /** 探测固定交易位（岩浆块 + 讲台位） */
    FIND_LECTERN_POSITION("检测工位"),
    /** 移动到玩家站位 */
    MOVE_TO_STAND_POSITION("移动到放置位"),
    /** 清除讲台位障碍方块 */
    BREAK_OBSTACLE("清除障碍方块"),
    /** 放置讲台 */
    PLACE_LECTERN("放置讲台"),
    /** 等待村民接受图书管理员职业 */
    WAIT_PROFESSION("等待成为图书管理员"),
    /** 打开村民交易界面 */
    OPEN_TRADE("打开交易界面"),
    /** 等待交易界面同步 */
    WAIT_TRADE_SCREEN("等待交易界面"),
    /** 读取交易报价列表 */
    READ_TRADES("读取交易"),
    /** 检查报价是否命中目标附魔 */
    CHECK_ENCHANTMENT("检查附魔"),
    /** 重置（未命中，拆除讲台刷新） */
    RESET("准备刷新"),
    /** 拆除讲台 */
    BREAK_LECTERN("拆除讲台"),
    /** 等待村民恢复失业状态 */
    WAIT_UNEMPLOYED("等待村民失业"),
    /** 成功命中目标附魔 */
    SUCCESS_FOUND("找到目标附魔"),
    /** 执行交易购买 */
    TRADE_PROCESS("处理交易"),
    /** 选中交易报价 */
    SELECT_TRADE("选择交易"),
    /** 等待交易同步（服务端确认） */
    WAIT_TRADE_SYNC("等待交易同步"),
    /** 取出交易成品（附魔书） */
    TAKE_TRADE_OUTPUT("领取物品"),
    /** 验证购买结果（库存对比） */
    VERIFY_PURCHASE("验证购买"),
    /** 完成当前目标 */
    COMPLETE_TARGET("完成目标"),
    /** 结束当前村民周期 */
    END_VILLAGER_CYCLE("结束村民周期"),
    /** 全部目标完成 */
    FINISH("全部完成"),
    /** 发生错误 */
    ERROR("错误");

    private final String displayName;

    LibrarianState(String displayName) {
        this.displayName = displayName;
    }

    /** 玩家可见中文名（HUD 信息串 / 聊天播报用；内部判断请直接用枚举常量） */
    public String displayName() {
        return displayName;
    }
}
