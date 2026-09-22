package com.yiyiaddon.feature.stardew.profile;

import java.util.Map;

/**
 * 已实机确认过口径的服务器预置表（本服学习档案里没有该作物时的兜底）。
 *
 * <p><b>为什么要预置</b>：学习要求玩家先手动收一棵；已经确认过的服不必等玩家再示范一次
 * （用户 2026-09-22：「你把这个服的变种巨型作物都加进去」）。预置优先级最低：玩家实测学到的
 * 口径永远覆盖它，换服 / 换包互不影响。</p>
 *
 * <p><b>键只取服务器、不带资源指纹</b>：这里记的是「动作 + 工具是否受限」，与资源包换了哪张贴图无关；
 * 「必须手持某件自定义道具」这类带物品身份的口径只能靠学习落盘，不写进预置。</p>
 */
public final class StardewSpecialHarvestPresets {

    private static final StardewSpecialHarvestRecipe BREAK_ANY =
        StardewSpecialHarvestRecipe.anyTool(StardewSpecialHarvestAction.BREAK);

    /** 右键采收、工具不限（服务器只要求「对着它右键」，空手即可） */
    private static final StardewSpecialHarvestRecipe USE_ANY =
        StardewSpecialHarvestRecipe.anyTool(StardewSpecialHarvestAction.RIGHT_CLICK);

    /**
     * 服务器键 → 口径键 → 口径。
     *
     * <p><b>口径键是「作物」或「作物#变种标记」</b>：同一作物可能有两种变种、收法还不同 ——
     * 本服 jmy 的巨型番茄是左键破坏、黄金番茄是右键，只按作物键存会互相顶掉
     * （用户 2026-09-22：「黄金番茄是用右键收割的，不用指定工具」）。
     * 查表时先精确匹配 {@code 作物#变种}，没有再退回 {@code 作物}。</p>
     *
     * <p>jmy.seasonmc.xyz：
     * ① 巨型作物（承载体是 {@code minecraft:chorus_plant}，属于 {@code mineable/axe}）实测
     * 「左键破坏、工具不限」—— 空手也能挖，拿带效率附魔的斧头更快，三个巨型变种
     * （巨型卷心菜 / 巨型菠萝 / 巨型番茄）按同一口径预置；
     * ② 黄金番茄实测「右键采收、不需要任何工具」（用户 2026-09-22）。</p>
     */
    private static final Map<String, Map<String, StardewSpecialHarvestRecipe>> PRESETS = Map.of(
        "jmy.seasonmc.xyz:25565", Map.of(
            "cabbage", BREAK_ANY,
            "pineapple", BREAK_ANY,
            "tomato", BREAK_ANY,
            "tomato#golden", USE_ANY));

    private StardewSpecialHarvestPresets() {
    }

    /** 取某服某作物的预置口径；没有就返回 {@code null}（调用方回落到默认口径） */
    public static StardewSpecialHarvestRecipe find(String serverKey, String cropKey) {
        if (serverKey == null || cropKey == null) return null;
        Map<String, StardewSpecialHarvestRecipe> byCrop = PRESETS.get(serverKey);
        return byCrop == null ? null : byCrop.get(cropKey);
    }
}
