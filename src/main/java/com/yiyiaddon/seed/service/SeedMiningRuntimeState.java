package com.yiyiaddon.seed.service;

/**
 * 种子挖矿正式模块 · <b>运行时状态</b>（正式化第四阶段）。
 *
 * <p><b>为什么要有它</b>：UI 不允许到处拼「{@code enabled && seed != null && dimension == …}」这种散装判据
 * —— 那种写法在维度切换、种子改写、世界退出这些交叉场景里必然出现「界面显示一套、实际行为另一套」。
 * 状态只有一个产生者（{@link SeedMiningService}），UI 只读它。</p>
 *
 * <p><b>状态序</b>（判据优先级即 {@code SeedMiningService#refreshState} 的分支顺序）：
 * 关闭 → 没进世界 → 种子没填 / 不合法 → 维度不支持 → 计算器启动中 → 预测中 → 计算器异常 →
 * 有结果（成功 / 失败） → 就绪。前面的条件一旦不满足就不再往后面看，
 * 因此「关闭」永远压过一切，「维度不支持」永远压过「就绪」。</p>
 *
 * <p><b>第四阶段的变化</b>：第三阶段的 {@code WORLDGEN_ENVIRONMENT_UNAVAILABLE}（「当前世界生成环境不可用」）
 * <b>已被删除</b> —— 世界生成宿主现在由本机隔离的 Worker 进程提供，因此不再存在「客户端拿不到宿主」
 * 这一状态；取而代之的是两个计算器进程状态（{@link #CALCULATOR_STARTING} / {@link #CALCULATOR_FAILED}）。
 * 这两个状态的含义是「本机的计算器出了问题」，<b>不是</b>「服务器不支持」——
 * 文案上必须分清，否则用户会去怪服务器（阶段 232 口径第二十五节）。</p>
 *
 * <p><b>「预测成不成立」与「有没有矿」是两件事</b>：{@link #SUCCESS} 只表示这一次预测<b>算出来了</b>，
 * 算出来 0 个矿也是成功；{@link #FAILED} 才表示这次预测<b>不成立</b>。</p>
 */
public enum SeedMiningRuntimeState {

    /** 未启用：完全不跑预测、不启动计算器。 */
    DISABLED("已关闭"),

    /** 已启用但还没进入世界（标题界面、连接中）。 */
    WAITING_FOR_WORLD("等待进入世界"),

    /** 已启用、也在世界里，但种子输入框是空的。 */
    WAITING_FOR_SEED("等待填写服务器种子"),

    /** 种子填了但不是合法 long。 */
    INVALID_SEED("种子格式无效"),

    /** 种子合法，但当前维度不在本阶段支持范围内（非主世界）。 */
    UNSUPPORTED_DIMENSION("当前维度暂不支持"),

    /**
     * 本地世界生成计算器正在启动（首次约十几秒：要跑一遍原版服务端的世界创建）。
     *
     * <p>它是「两段式 UX」的第一段：先把「正在启动…」显示出来，再切到「正在预测…」，
     * 避免用户以为卡死（阶段 232 口径第六十一节）。</p>
     */
    CALCULATOR_STARTING("正在启动本地世界生成计算器…"),

    /** 后台正在预测当前区块。 */
    PREDICTING("正在预测当前区块…"),

    /**
     * 本地世界生成计算器异常（启动失败 / 超时 / 崩溃 / 传输故障）。
     *
     * <p>它描述的是<b>本机</b>的问题，绝不是「服务器不支持」；详细原因在失败文案与日志里。</p>
     */
    CALCULATOR_FAILED("本地世界生成计算器异常"),

    /** 上一次预测算出来了（0 个矿也算成功）。 */
    SUCCESS("预测完成"),

    /** 上一次预测不成立（异常 / 维度或矿物越界 / 会话异常），失败原因见服务层的失败文案。 */
    FAILED("预测失败"),

    /** 已就绪：条件齐备，可以点「测试当前区块预测」。 */
    READY("已就绪");

    /** 玩家可见中文文案（界面与报告共用一份，禁止在 UI 里另写一套）。 */
    private final String displayNameCn;

    SeedMiningRuntimeState(String displayNameCn) {
        this.displayNameCn = displayNameCn;
    }

    /** 中文显示名。 */
    public String displayNameCn() {
        return displayNameCn;
    }
}
