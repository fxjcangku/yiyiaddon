package com.yiyiaddon.feature.stardew.profile;

/**
 * 作物成功收获后的生命周期。
 *
 * <p>{@link #UNKNOWN} 只允许出现在文档候选或人工仅标记成熟阶段的过渡规则中；
 * 自动学习必须观察到空盆或明确回退阶段后，才能保存为另外两种已确认状态。</p>
 */
public enum StardewCropLifecycle {

    /** 收获后植株消失，需要重新播种。 */
    ONE_SHOT,
    /** 收获后植株回退到其它生长阶段，不需要补种。 */
    REGROW,
    /** 尚未取得收获后世界状态证据。 */
    UNKNOWN
}
