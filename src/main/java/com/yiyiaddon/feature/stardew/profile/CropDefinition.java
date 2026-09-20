package com.yiyiaddon.feature.stardew.profile;

import java.util.List;

/**
 * 一种作物的资源索引定义。
 *
 * <p>只描述「资源包 / 客户端观察能证明什么」，不描述服务端私有收割逻辑。V1 在原有
 * 身份键基础上增加「资源模型」字段：没有真实 ItemIdentity（未 .id）的候选作物也能靠
 * itemModel 参与预览、匹配与执行，摆脱对逐个 .id 手工入库的依赖。</p>
 *
 * <ul>
 *   <li>{@code cropKey}        稳定键（如 {@code tomato}）；</li>
 *   <li>{@code seedModel}      种子 itemModel（如 {@code customcrops:item/tomato_seeds}）；</li>
 *   <li>{@code produceModels}  成熟产物 itemModel（普通 + 银星 + 金星）；</li>
 *   <li>{@code variantModels}  特殊变种 itemModel（金色番茄 / 巨型番茄等）；</li>
 *   <li>{@code seedKey}/{@code produceKeys}/{@code variantKeys} 真实 ItemIdentity 身份键（可空）。</li>
 * </ul>
 *
 * <p>身份键与动态状态严格分离：数量 / 水量 / 肥料次数等运行时状态绝不塞进本定义。</p>
 */
public record CropDefinition(
    String cropKey,
    String chineseName,
    String seedKey,
    List<String> produceKeys,
    List<String> variantKeys,
    String evidence,
    String seedModel,
    String seedName,
    List<String> produceModels,
    List<String> produceNames,
    List<String> variantModels,
    List<String> variantNames
) {

    /** 面向玩家的短描述（中文名 + 技术键） */
    public String displayLabel() {
        return chineseName + " (" + cropKey + ")";
    }

    /** 是否有成熟产物（真实身份键或资源模型任一存在即算） */
    public boolean hasProduce() {
        return (produceKeys != null && !produceKeys.isEmpty())
            || (produceModels != null && !produceModels.isEmpty());
    }

    /** 是否有种子资源模型（可用于预览与 item_model 匹配） */
    public boolean hasSeedModel() {
        return seedModel != null && !seedModel.isBlank();
    }

    /**
     * 本作物在图示上代表自己的模型键：<b>成熟产物优先，无产物回退种子</b>。
     *
     * <p>选择器的作物行、控制台「种植区域」列表行、世界里的区域字牌都读这一份 ——
     * 一处改了三个界面同时生效，不会出现「列表画番茄、字牌画番茄种子」。</p>
     */
    public String iconModel() {
        if (produceModels != null && !produceModels.isEmpty()) {
            return produceModels.get(0);
        }
        return seedModel;
    }

    /** 种子展示名（优先资源中文名，其次作物名） */
    public String seedDisplayName() {
        return seedName != null && !seedName.isBlank() ? seedName : chineseName + "种子";
    }
}
