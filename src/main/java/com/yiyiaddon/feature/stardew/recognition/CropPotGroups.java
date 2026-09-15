package com.yiyiaddon.feature.stardew.recognition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 作物属于哪一组盆（通用 / 下界 / 末地）。
 *
 * <p><b>分组从哪来：</b>资源包里<b>有</b>这个信息，只是不在作物 json 里——服务端插件把限制写进了
 * 语言文件的失败原因：</p>
 *
 * <pre>plugin.customcrops.crops.&lt;作物键&gt;.not_met_requirement.message
 *   = [X] 你只能在下界维度种植它 / [X] 你只能在末地维度种植它</pre>
 *
 * <p>这是服务器自己的规则，比任何猜测都硬，所以索引重建时整表读进来（见
 * {@link #installPackGroups}）。这条通道也是「末地作物一直没被标出来」的根因修复：
 * 以前只靠手写攻略，而攻略里没有末地那两种。</p>
 *
 * <p>三层优先级（从高到低）：</p>
 * <ol>
 *   <li><b>资源包</b>：服务器插件自己声明的维度限制，权威；</li>
 *   <li><b>实测</b>：包里没声明时，用「某作物长在某盆型里」的现场观察兜底；</li>
 *   <li><b>默认</b>：其余一律通用（任何维度、普通盆）。</li>
 * </ol>
 */
public final class CropPotGroups {

    /** 资源包声明的分组：索引重建时整体替换，避免换包 / 换服后残留旧声明 */
    private static final Map<String, PotGroup> PACK = new ConcurrentHashMap<>();

    /** 实测兜底：作物键 → 盆型组（只在资源包没有声明该作物时生效） */
    private static final Map<String, PotGroup> LEARNED = new ConcurrentHashMap<>();

    private CropPotGroups() {
    }

    private static final PotGroup DEFAULT = PotGroup.NORMAL;

    /** 作物所属的盆型组；未知作物一律通用 */
    public static PotGroup of(String cropKey) {
        if (cropKey == null || cropKey.isBlank()) return DEFAULT;
        PotGroup declared = PACK.get(cropKey);
        if (declared != null) return declared;
        return LEARNED.getOrDefault(cropKey, DEFAULT);
    }

    /** 该分组是否来自实测（选择器用它标注依据，让玩家知道哪条是脚本自己看出来的） */
    public static boolean isLearned(String cropKey) {
        if (cropKey == null || PACK.containsKey(cropKey)) return false;
        return LEARNED.containsKey(cropKey);
    }

    /**
     * 用资源包声明的分组整体替换（索引重建时调用）。
     *
     * <p>整表替换而不是增量合并：换服务器 / 换资源包后，旧包里声明的分组必须一起消失。</p>
     */
    public static void installPackGroups(Map<String, PotGroup> groups) {
        PACK.clear();
        if (groups != null) PACK.putAll(groups);
    }

    /**
     * 世界观察：某作物正长在某盆型里 → 记为实测分组（资源包已声明的作物不参与）。
     *
     * <p>普通盆不参与学习：通用作物种在下界盆里是玩家放错了，不能因此把玉米判成下界作物。</p>
     *
     * @return 是否学到了新东西（用于播报一次）
     */
    public static boolean observe(String cropKey, PotGroup group) {
        if (cropKey == null || cropKey.isBlank() || group == null || group == DEFAULT) return false;
        if (PACK.containsKey(cropKey)) return false;
        PotGroup previous = LEARNED.put(cropKey, group);
        // 分组可能被改（同一作物换了盆型种植），以最新观察为准
        return previous != group;
    }

    /** 清空实测结果（切服 / 换资源包时调用） */
    public static void clearLearned() {
        LEARNED.clear();
    }
}
