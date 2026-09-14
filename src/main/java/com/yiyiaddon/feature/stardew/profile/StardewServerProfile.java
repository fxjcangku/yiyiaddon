package com.yiyiaddon.feature.stardew.profile;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 单服务器的星露谷规则档案。
 *
 * <p>与「资源索引」（描述资源包里有什么）分离：本档案描述「该服务器怎么玩」，
 * 通过 JSON 持久化，切服 / 换档时按 {@code serverKey}（host:port 或 singleplayer）
 * 隔离，绝不写死在通用 Java 中。</p>
 *
 * <p>V1 承载三类可安全读取的规则提示：</p>
 * <ul>
 *   <li>{@code matureStages} 作物成熟阶段（cropKey → 成熟阶段名，如 {@code tomato → stage_4}）。
 *       由真机观察或攻略建立，识别器只在命中该阶段时判定成熟，绝不猜 {@code max(stage)}；</li>
 *   <li>{@code regrowCrops}   收割后回退继续生长的作物 cropKey（重复采摘）；</li>
 *   <li>{@code specialCrops}  需特殊收割动作的作物 cropKey（金色番茄 / 巨大菠萝等）。</li>
 * </ul>
 *
 * <p>规则列表只作「决策提示」：核心状态机始终以收割后的真实世界观察为准，
 * 空盆 → 补种、植株回退 → 不补种、仍存在 / 未知 → 重新观察，不因缺规则而乱动。</p>
 */
public record StardewServerProfile(
    String serverKey,
    Map<String, String> matureStages,
    List<String> regrowCrops,
    List<String> specialCrops,
    int version,
    Map<String, String> documentedStages
) {

    /** 旧档案不包含可归属当前服务器的攻略证据，禁止自动认领全局攻略。 */
    public StardewServerProfile(String serverKey, Map<String, String> matureStages,
        List<String> regrowCrops, List<String> specialCrops, int version) {
        this(serverKey, matureStages, regrowCrops, specialCrops, version, Map.of());
    }

    /** 当前档案格式版本 */
    public static final int VERSION = 1;

    /** 空档案（没有任何规则提示，全部走真实观察的保守路径） */
    public static StardewServerProfile empty(String serverKey) {
        return new StardewServerProfile(serverKey, new LinkedHashMap<>(), List.of(), List.of(), VERSION);
    }

    /** 某作物的成熟阶段名；档案未记录时回退到文档化规则（攻略 + 资源包阶段文件），
     *  两者都没有才返回 null。档案（mark-mature 人工校准）永远优先于文档化基线。 */
    public String matureStage(String cropKey) {
        if (matureStages != null && matureStages.containsKey(cropKey)) return matureStages.get(cropKey);
        return documentedStages == null ? null : documentedStages.get(cropKey);
    }

    /** 成熟规则的证据等级：档案命中 = 已确认（VERIFIED），文档化回退 = 攻略记载（DOCUMENTED）。 */
    public RuleEvidence matureEvidence(String cropKey) {
        if (matureStages != null && matureStages.containsKey(cropKey)) return RuleEvidence.VERIFIED;
        return documentedStages != null && documentedStages.containsKey(cropKey) ? RuleEvidence.DOCUMENTED : null;
    }

    /** 某作物是否标记为「收割后回退继续生长」（档案优先，其次文档化规则） */
    public boolean regrows(String cropKey) {
        if (regrowCrops != null && regrowCrops.contains(cropKey)) return true;
        return false;
    }

    /** 某作物是否标记为「需特殊收割动作」（档案优先，其次文档化规则） */
    public boolean special(String cropKey) {
        if (specialCrops != null && specialCrops.contains(cropKey)) return true;
        return false;
    }

    /** 档案里人工确认（mark-mature）的成熟规则条数 */
    public int verifiedMatureCount() {
        return matureStages == null ? 0 : matureStages.size();
    }
}
