package com.yiyiaddon.feature.stardew.command;

import com.yiyiaddon.feature.stardew.StardewContext;
import com.yiyiaddon.feature.stardew.config.StardewSettings;
import com.yiyiaddon.feature.stardew.memory.FarmMemoryStore;
import com.yiyiaddon.feature.stardew.profile.CropDefinition;
import com.yiyiaddon.feature.stardew.profile.PotDefinition;
import com.yiyiaddon.feature.stardew.profile.RuleEvidence;
import com.yiyiaddon.feature.stardew.profile.StardewCropLifecycle;
import com.yiyiaddon.feature.stardew.profile.StardewCropNameStore;
import com.yiyiaddon.feature.stardew.profile.StardewHarvestAction;
import com.yiyiaddon.feature.stardew.profile.StardewHarvestRule;
import com.yiyiaddon.feature.stardew.profile.StardewResourceIndex;
import com.yiyiaddon.feature.stardew.profile.StardewResourceScanner;
import com.yiyiaddon.feature.stardew.profile.StardewToolDefinition;
import com.yiyiaddon.feature.stardew.recognition.CropRecognizer;
import com.yiyiaddon.feature.stardew.recognition.CropRuntimeStateResolver;
import com.yiyiaddon.feature.stardew.recognition.CropState;
import com.yiyiaddon.feature.stardew.recognition.PotState;
import com.yiyiaddon.feature.stardew.recognition.StardewCropDisplayProbe;
import com.yiyiaddon.feature.stardew.selector.StardewSelectorCategory;
import com.yiyiaddon.feature.stardew.service.StardewInventoryService;
import com.yiyiaddon.feature.stardew.service.StardewProfileAssembler;
import com.yiyiaddon.model.identity.BlockIdentity;
import com.yiyiaddon.platform.GameProbe;
import com.yiyiaddon.platform.container.ContainerAccess;
import com.yiyiaddon.platform.identity.BlockIdentifier;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Display;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * 星露谷指令查询与补全支撑：作物名解析、补全候选项、人工标记成熟，以及
 * 真实作物状态数据源 {@link CropRuntimeSource}。
 *
 * <p>逐字搬运自旧项目 {@code stardew/StardewFarmModule.java:1449-1828}。旧模块字段
 * {@code index / activeHarvestRules / activeCropSignatures / memory / 六类选择}
 * 由本类持有或经构造器注入；档案写盘统一走 {@link StardewProfileAssembler}。</p>
 */
public final class StardewQuerySupport {

    private final StardewResourceIndex index;
    private final StardewProfileAssembler assembler;
    private final FarmMemoryStore memory;
    private final StardewSettings settings;

    public StardewQuerySupport(StardewResourceIndex index, StardewProfileAssembler assembler,
                               FarmMemoryStore memory, StardewSettings settings) {
        this.index = index;
        this.assembler = assembler;
        this.memory = memory;
        this.settings = settings;
    }

    /** 作物 key 的中文显示名（指令播报用；找不到原样返回 key） */
    public String cropDisplayName(String cropKey) {
        return cropDisplayName(index, cropKey);
    }

    /** 作物 key 的中文显示名（资源索引口径的静态形式；档案装配层播报共用） */
    public static String cropDisplayName(StardewResourceIndex index, String cropKey) {
        CropDefinition crop = index.cropByKey(cropKey);
        return crop == null ? "未识别作物（作物 ID：" + cropKey + "）" : crop.chineseName();
    }

    /** 该 cropKey 是否属于当前服务器资源索引（指令前置校验用） */
    public boolean isKnownCrop(String cropKey) {
        return cropKey != null && index.cropByKey(cropKey) != null;
    }

    /**
     * 把玩家输入的作物名 / cropKey 解析为当前服务器唯一的 canonical cropKey。
     *
     * <p>只认「当前资源索引里真实存在」的作物；中文显示名重复时返回 null，
     * 强制玩家改用唯一 cropKey——绝不替玩家猜一个。</p>
     *
     * @return 唯一确定的 cropKey；不存在或不唯一返回 null
     */
    public String canonicalCropKey(String input) {
        if (input == null) return null;
        String text = stripQuotes(input.trim());
        if (text.isEmpty()) return null;

        CropDefinition direct = index.cropByKey(text);
        if (direct != null) return direct.cropKey();

        CropDefinition matched = null;
        for (CropDefinition crop : index.crops()) {
            if (!text.equals(crop.chineseName())) continue;
            if (matched != null) return null;   // 中文名重复：必须用唯一 cropKey
            matched = crop;
        }
        if (matched != null) return matched.cropKey();

        for (CropDefinition crop : index.crops()) {
            if (text.equalsIgnoreCase(crop.cropKey())) return crop.cropKey();
        }
        return null;
    }

    /**
     * 作物补全候选项：canonical cropKey + 中文名（都只来自当前服务器资源索引）。
     *
     * <p>中文名重复的作物只给 cropKey，避免补全出一个解析不唯一的项。</p>
     *
     * @param prefix 已输入前缀（大小写不敏感；带引号时会自动剥离再过滤）
     * @param quoted 是否把候选项包成 Brigadier 字符串参数可解析的带引号形式
     */
    public List<String> cropCompletions(String prefix, boolean quoted) {
        String trimmed = prefix == null ? "" : prefix.trim();
        // 非引号分支（word 参数）无法解析带引号的输入：此时不能补全，否则补出来的文本一按下去就解析失败
        if (!quoted && !trimmed.isEmpty() && (trimmed.charAt(0) == '"' || trimmed.charAt(0) == '\'')) {
            return List.of();
        }
        String lower = stripQuotes(trimmed).toLowerCase(Locale.ROOT);

        Map<String, Integer> nameCounts = new LinkedHashMap<>();
        for (CropDefinition crop : index.crops()) {
            String name = crop.chineseName();
            if (name == null || name.isBlank()) continue;
            nameCounts.merge(name, 1, Integer::sum);
        }

        List<String> result = new ArrayList<>();
        for (CropDefinition crop : index.crops()) {
            addCompletion(result, crop.cropKey(), lower, quoted);
            String name = crop.chineseName();
            if (name == null || name.isBlank()) continue;
            if (nameCounts.getOrDefault(name, 0) > 1) continue;   // 重名只留 cropKey
            addCompletion(result, name, lower, quoted);
        }
        return result;
    }

    /**
     * 阶段补全候选项：只包含该作物在当前服务器资源包里真实存在的阶段。
     *
     * <p>数据来自资源扫描（{@link StardewResourceIndex#stagesOf(String)}），
     * 再加上本服务器档案里已记录过的人工成熟阶段（同样是该服务器的真实证据）。
     * 绝不生成 {@code stage_1~stage_10}，也不混入其它作物的阶段。</p>
     */
    public List<String> stageCompletions(String cropInput, String prefix) {
        String cropKey = canonicalCropKey(cropInput);
        if (cropKey == null) return List.of();

        List<String> stages = new ArrayList<>(index.stagesOf(cropKey));
        StardewHarvestRule rule = assembler.activeHarvestRules().get(cropKey);
        if (rule != null && rule.hasMatureStage() && !stages.contains(rule.matureStage())) {
            stages.add(rule.matureStage());
        }
        // 本会话在世界 / 界面里见过的阶段：阶段模型被混淆的服务器扫不出清单，补全只能靠这条实证
        for (String observed : StardewCropNameStore.observedStages(cropKey)) {
            if (!stages.contains(observed)) stages.add(observed);
        }

        String lower = prefix == null ? "" : prefix.trim().toLowerCase(Locale.ROOT);
        List<String> result = new ArrayList<>();
        for (String stage : stages) {
            if (lower.isEmpty() || stage.toLowerCase(Locale.ROOT).startsWith(lower)) result.add(stage);
        }
        return result;
    }

    /**
     * {@code .stardew 诊断} 的数据行：把「当前服务器的运行时真相」一次列清。
     *
     * <p>适配新服务器时最缺的就是这个：扫描到多少条资源、索引里究竟有几种作物、每种作物有没有真实
     * 阶段模型、索引构建有没有半途失败、准星方块的「方块 ID → 资源模型 → 语义身份 → 作物状态」这条
     * 链路断在哪一环。全部取运行时真实数据，缺哪项写哪项，绝不用推断值填空。</p>
     */
    /**
     * 只读诊断：把当前服务器资源包里「带特殊阶段」的作物全部列出来。
     *
     * <p>特殊阶段的判据与识别层同源（{@link CropRuntimeStateResolver#isSpecialStage}：阶段名含
     * {@code golden / giant / gigantic / variation}），不另立一套。这些阶段收的时候要手持金锄头右键，
     * 提前列出来玩家就知道田里可能冒出什么、要备几把金锄头；资源包里没有就如实报「无」，
     * 绝不编造阶段名。</p>
     */
    private String specialStageLine() {
        StringBuilder out = new StringBuilder();
        for (CropDefinition crop : index.crops()) {
            List<String> specials = new ArrayList<>();
            for (String stage : index.stagesOf(crop.cropKey())) {
                if (CropRuntimeStateResolver.isSpecialStage(stage)) specials.add(stage);
            }
            if (specials.isEmpty()) continue;
            if (out.length() > 0) out.append("§8、§f");
            out.append(crop.cropKey()).append('(').append(crop.chineseName()).append(")§8 ")
                .append(String.join("/", specials));
        }
        return "§f特殊阶段 §8▸ §f"
            + (out.length() == 0 ? "无（本服资源包里没有金色 / 巨大 / 变种阶段）" : out);
    }

    public List<String> diagnosticLines() {
        List<String> lines = new ArrayList<>();
        // 先把「眼里能看到的物品」学一遍：玩家常是把商店 / 种子箱开着直接跑诊断的，
        // 这一下既是刷新（学到就重建索引），也让下面的索引与学名行反映此刻的真实状态。
        StardewCropNameStore.observeNow();
        lines.add("§f会话 §8▸ §f" + text(StardewContext.serverKey()) + " §8· §f" + text(StardewContext.dimension()));
        lines.add("§f资源 §8▸ §f" + ResourceExtractionService.phase().label()
            + " §8· §f指纹 " + text(ResourceExtractionService.fingerprint()));

        // ── 扫描 / 索引 ──
        List<StardewResourceScanner.ScannedModel> scanned = StardewResourceScanner.scan();
        int itemDef = 0;
        int itemModel = 0;
        int blockModel = 0;
        Set<String> namespaces = new LinkedHashSet<>();
        for (StardewResourceScanner.ScannedModel model : scanned) {
            switch (model.kind()) {
                case ITEM_DEF -> itemDef++;
                case ITEM_MODEL -> itemModel++;
                case BLOCK_MODEL -> blockModel++;
            }
            String namespace = StardewResourceIndex.namespaceOf(model.modelId());
            if (namespace != null) namespaces.add(namespace);
        }
        lines.add("§f扫描 §8▸ §f" + scanned.size() + " 条 §8(items " + itemDef
            + " / item模型 " + itemModel + " / block模型 " + blockModel + ")");
        lines.add("§f命名空间 §8▸ §f" + (namespaces.isEmpty() ? "无" : String.join("、", namespaces)));

        int withStages = 0;
        for (CropDefinition crop : index.crops()) {
            if (!index.stagesOf(crop.cropKey()).isEmpty()) withStages++;
        }
        lines.add("§f索引 §8▸ §f作物 " + index.crops().size()
            + " §8· §f盆 " + index.entriesFor(StardewSelectorCategory.POT).size()
            + " §8· §f肥料 " + index.entriesFor(StardewSelectorCategory.FERTILIZER).size()
            + " §8· §f药剂 " + index.entriesFor(StardewSelectorCategory.POTION).size()
            + " §8· §f水壶 " + index.entriesFor(StardewSelectorCategory.WATERING_CAN).size()
            + " §8· §f洒水器 " + index.entriesFor(StardewSelectorCategory.SPRINKLER).size()
            + " §8· §f温室玻璃 " + index.entriesFor(StardewSelectorCategory.SHELTER).size());
        lines.add("§f阶段模型 §8▸ §f" + withStages + " 种作物有真实阶段（其余无法在世界里按阶段识别）");
        lines.add(specialStageLine());
        lines.add(index.lastFailure() == null
            ? "§a索引构建 §8▸ §a完整"
            : "§c索引构建 §8▸ §c失败：" + index.lastFailure());

        List<CropDefinition> crops = index.crops();
        StringBuilder sample = new StringBuilder();
        for (int i = 0; i < Math.min(10, crops.size()); i++) {
            if (i > 0) sample.append("§8、§f");
            sample.append(crops.get(i).cropKey()).append("(").append(crops.get(i).chineseName()).append(")");
        }
        lines.add("§f作物样本 §8▸ §f" + (sample.length() == 0 ? "无"
            : sample + (crops.size() > 10 ? " §8…共 " + crops.size() + " 种" : "")));

        // ── 自动学名：部分服务器的资源包只翻译了一部分作物，其余中文名来自服务器下发的物品名 /
        //    阶段名。这一行同时回答两件事：学名库有没有货、上面的「作物样本」该不该是中文名 ──
        lines.add("§f学名 §8▸ §f" + learnedNameSummary());
        lines.add("§f界面 §8▸ §f" + openScreenSummary());

        // ── 附近实体：CraftEngine 系服务器（客户端无 CE 模组）用展示实体渲染自定义方块/作物，
        //    展示实体没有碰撞箱，准星射线直接穿过——所以「无法选中、F3 无目标」时先看这里 ──
        lines.add("§f附近实体 §8▸ §f" + nearbyEntitySummary());
        lines.add("§f展示物 §8▸ §f" + nearbyDisplaySummary());
        lines.add("§f盆上作物 §8▸ §f" + potCropProbeLine());

        // ── 准星识别链路 ──
        BlockIdentity identity = BlockIdentifier.identify();
        if (identity == null) {
            lines.add("§f准星 §8▸ §8未指向方块（站到要排查的方块前再敲一次）");
            return lines;
        }
        lines.add("§f准星方块 §8▸ §f" + text(identity.blockId()) + " §8· §f" + text(identity.blockName())
            + " §8· §f" + clamp(identity.blockState(), 90));
        lines.add("§f资源模型 §8▸ §f" + text(identity.semanticModel()));
        lines.add("§f语义身份 §8▸ §f" + text(identity.semanticIdentity()) + " §8· §f"
            + text(identity.semanticCertainty())
            + (identity.semanticReason() == null ? "" : " §8· §c" + identity.semanticReason()));
        CropRuntimeStateResolver.RuntimeResult crop = CropRuntimeStateResolver.probeCrosshair().crop();
        lines.add("§f作物解析 §8▸ §f" + crop.state().displayName()
            + " §8· §f作物 " + text(crop.cropKey()) + " §8· §f阶段 " + text(crop.stageName())
            + " §8· §f规则 " + text(crop.evidence() == null ? null : crop.evidence().displayName()));
        lines.add("§f盆型命中 §8▸ §f" + (potHit(identity) ? "是" : "否")
            + " §8· §f方块实体 " + text(identity.blockEntityTypeId()));
        return lines;
    }

    /**
     * 自动学名状态：已学到的作物名数量 + 前几项（作物键 = 中文名）。
     *
     * <p>「作物样本」用的是索引里的名字，这一行用的是学名库里的名字。两者对照即可判定问题出在
     * 学名没学到，还是学到了但没进索引（后者只可能是索引没重建）。</p>
     */
    private static String learnedNameSummary() {
        Map<String, String> learned = StardewCropNameStore.learned();
        if (learned.isEmpty()) return "无（还没观察到服务器下发的物品名 / 阶段名）";
        StringBuilder out = new StringBuilder();
        int shown = 0;
        for (Map.Entry<String, String> entry : learned.entrySet()) {
            if (shown > 0) out.append("§8、§f");
            out.append(entry.getKey()).append("§8=§f").append(entry.getValue());
            if (++shown >= 4) break;
        }
        if (learned.size() > shown) out.append("§8、…共 ").append(learned.size()).append(" 种");
        else out.append(" §8（共 ").append(learned.size()).append(" 种）");
        return out.toString();
    }

    /**
     * 当前打开的界面是否能让学名读到商品。
     *
     * <p>三类界面：容器型（槽位里摆着实物）、村民交易（商品在交易清单里）、插件自绘 GUI（商品只
     * 以物品栈躺在界面对象字段里）。最后一种靠通用下探扫，这里报出扫到的物品栈数量——为 0 就
     * 说明商品在客户端根本不持有，学名只能靠买下来 / 种下去。</p>
     */
    private static String openScreenSummary() {
        Screen screen = Minecraft.getInstance().gui.screen();
        if (screen == null) return "无（没开界面）";
        AbstractContainerMenu menu = ContainerAccess.openMenu();
        int slots = 0;
        if (menu != null) {
            for (Slot slot : menu.slots) {
                if (!slot.getItem().isEmpty()) slots++;
            }
        }
        return screen.getClass().getSimpleName() + " §8· 容器槽内物品 §f" + slots
            + " §8· 界面内物品栈 §f" + StardewCropNameStore.lastScreenStackCount();
    }

    /**
     * 附近实体类型统计（半径 12 格，最多列 6 种）。
     *
     * <p>用来确认「作物到底是不是方块」：CraftEngine 系服务器在客户端没装 CE 模组时，会把自定义
     * 内容退化成展示实体渲染，而展示实体<b>没有碰撞箱</b>——准星射线直接穿过，于是 F3 看不到目标、
     * {@code .id 方块} 也报「未指向有效方块」。这时唯一能看到的证据就在这里。</p>
     */
    private static String nearbyEntitySummary() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return "无";
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (Entity entity : nearbyEntities(mc)) {
            Identifier id = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
            counts.merge(id == null ? "未知" : id.toString(), 1, Integer::sum);
        }
        if (counts.isEmpty()) return "无";
        StringBuilder text = new StringBuilder();
        int shown = 0;
        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            if (shown >= 6) break;
            if (shown > 0) text.append("§8、§f");
            text.append(entry.getKey()).append("×").append(entry.getValue());
            shown++;
        }
        return text.toString();
    }

    /**
     * 附近展示实体（{@code item_display} / {@code block_display}）承载的内容：物品模型 + 服务器下发的名字。
     *
     * <p>作物如果走展示实体渲染，这里就会出现 {@code customcrops:<作物>_stage_N}——它同时给出两条
     * 关键信息：世界识别该按什么键匹配，以及该作物在这一服的中文名（资源包无语言文件时唯一来源）。</p>
     */
    private static String nearbyDisplaySummary() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return "无";
        Map<String, String> seen = new LinkedHashMap<>();
        for (Entity entity : nearbyEntities(mc)) {
            String key = null;
            String label = null;
            if (entity instanceof Display.ItemDisplay display) {
                ItemStack stack = display.getItemStack();
                key = StardewInventoryService.itemModelOf(stack);
                if (key == null && !stack.isEmpty()) key = BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
                label = stack.isEmpty() ? null : stack.getHoverName().getString();
            } else if (entity instanceof Display.BlockDisplay display) {
                key = "block:" + display.getBlockState().getBlock();
            }
            if (key == null || key.isBlank()) continue;
            String name = label == null || label.isBlank() ? "无" : label;
            seen.putIfAbsent(key, name);
            if (seen.size() >= 8) break;
        }
        if (seen.isEmpty()) return "无";
        StringBuilder text = new StringBuilder();
        int i = 0;
        for (Map.Entry<String, String> entry : seen.entrySet()) {
            if (i++ > 0) text.append("§8、§f");
            text.append(entry.getKey()).append("(").append(entry.getValue()).append(")");
        }
        return text.toString();
    }

    /** 玩家周围 12 格内的实体（含无碰撞箱的展示实体） */
    private static List<Entity> nearbyEntities(Minecraft mc) {
        AABB box = mc.player.getBoundingBox().inflate(12.0);
        return mc.level.getEntities(mc.player, box);
    }

    /**
     * 「最近一格盆 + 展示实体」的完整识别结果：坐标 → 作物键 · 阶段 · 状态。
     *
     * <p>这是「作物不是方块的服务器」上唯一能验证整条通道的方式：盆是方块（能读到干湿），
     * 作物只在展示实体里——跑这行就知道展示实体有没有被正确接到作物键与阶段上，以及成熟规则认不认。</p>
     */
    private String potCropProbeLine() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return "无";
        BlockPos center = mc.player.blockPosition();
        // 补录而不是重建：诊断随时可能在收菜任务中途跑，重建会把农田归档削成一小块
        StardewCropDisplayProbe.archiveAround(center);

        BlockPos best = null;
        double bestDistance = Double.MAX_VALUE;
        for (BlockPos pos : BlockPos.betweenClosed(center.offset(-8, -4, -8), center.offset(8, 4, 8))) {
            BlockPos potPos = pos.immutable();
            if (!isConfirmedPot(mc, potPos)) continue;
            // 展示实体在两个位置都可能：贴在盆格上（同类服）、或悬在盆上方一格的作物格上
            // （隐形载体服，如 moexd：作物格是 minecraft:carrots 这类原版方块，模型却是 block/empty）
            if (StardewCropDisplayProbe.modelsAt(potPos).isEmpty()
                && StardewCropDisplayProbe.modelsAt(potPos.above()).isEmpty()) continue;
            double distance = potPos.distSqr(center);
            if (distance < bestDistance) {
                bestDistance = distance;
                best = potPos;
            }
        }
        if (best == null) {
            return StardewCropDisplayProbe.hasAny()
                ? "附近有展示实体，但都不在已确认的盆上"
                : "附近 8 格内没有展示实体";
        }
        List<String> models = new ArrayList<>(StardewCropDisplayProbe.modelsAt(best));
        for (String extra : StardewCropDisplayProbe.modelsAt(best.above())) {
            if (!models.contains(extra)) models.add(extra);
        }
        for (String model : models) {
            CropRecognizer.CropRecognition crop =
                CropRecognizer.recognizeIdentity(model, null, model, assembler.profile());
            if (crop.state() == CropState.UNKNOWN) continue;
            return best.toShortString() + " §8▸ §f" + text(crop.cropKey()) + " §8· §f" + text(crop.stageName())
                + " §8· §f" + crop.runtimeState().displayName();
        }
        return best.toShortString() + " §8▸ §f展示物存在但都不像作物（如稻草人）";
    }

    /** 这一格是否已确认的种植盆（干/湿） */
    private static boolean isConfirmedPot(Minecraft mc, BlockPos pos) {
        CropRecognizer.PotRecognition pot = CropRecognizer.recognizePotDetailed(mc.level.getBlockState(pos));
        return pot.state() == PotState.DRY || pot.state() == PotState.WET;
    }

    /** 准星方块是否命中已选盆型（复用识别器的盆键派生，避免诊断与识别两条口径不一致） */
    private boolean potHit(BlockIdentity identity) {
        String source = identity.semanticIdentity() != null && !identity.semanticIdentity().isBlank()
            ? identity.semanticIdentity() : identity.semanticModel();
        String potKey = CropRecognizer.potKeyOf(source);
        if (potKey == null) return false;
        for (StardewToolDefinition entry : index.entriesFor(StardewSelectorCategory.POT)) {
            if (entry instanceof PotDefinition pot && pot.matchesPotKey(potKey)) return true;
        }
        return false;
    }

    /** 诊断行取值：空白一律写「无」，绝不显示 null */
    private static String text(String value) {
        return value == null || value.isBlank() ? "无" : value;
    }

    /** 诊断行截断：方块状态串可能很长，只保留可读前缀 */
    private static String clamp(String value, int max) {
        if (value == null) return "无";
        return value.length() <= max ? value : value.substring(0, max) + "…";
    }

    /** 追加一个补全项（按前缀过滤；带引号模式包成 {@code "名称"}） */
    private static void addCompletion(List<String> out, String value, String lowerPrefix, boolean quoted) {
        if (value == null || value.isBlank()) return;
        if (!lowerPrefix.isEmpty() && !value.toLowerCase(Locale.ROOT).startsWith(lowerPrefix)) return;
        String text = quoted ? "\"" + value + "\"" : value;
        if (!out.contains(text)) out.add(text);
    }

    /**
     * 去掉 Brigadier 字符串参数的包裹引号。
     *
     * <p>已闭合（{@code "番茄"}）整体去掉；只敲了起始引号（{@code "番}）也去掉，
     * 这样玩家边打中文边按 TAB 时补全依然可用。</p>
     */
    private static String stripQuotes(String text) {
        if (text.isEmpty()) return text;
        char first = text.charAt(0);
        if (first != '"' && first != '\'') return text;
        String body = text.substring(1);
        if (!body.isEmpty() && body.charAt(body.length() - 1) == first) {
            body = body.substring(0, body.length() - 1);
        }
        return body.trim();
    }

    /**
     * 真实作物状态数据源：把本模块「当前 ServerKey + 指纹隔离域」里的规则与资源索引
     * 暴露给唯一判定组件 {@link CropRuntimeStateResolver}。
     *
     * <p>只读快照式查询，不提供任何写入方法：人工校准与自动学习的写入口依然唯一
     * （{@link #markMature(String, String, boolean)} 与
     * {@link StardewProfileAssembler#saveLearnedHarvestRule}），因此不可能出现第二套成熟规则库。</p>
     */
    public final class CropRuntimeSource implements CropRuntimeStateResolver.CropRuntimeSource {

        @Override public boolean knownCrop(String cropKey) {
            return cropKey != null && index.cropByKey(cropKey) != null;
        }

        @Override public String cropDisplayName(String cropKey) {
            CropDefinition crop = index.cropByKey(cropKey);
            return crop == null ? null : crop.chineseName();
        }

        @Override public String matureStage(String cropKey) {
            StardewHarvestRule rule = assembler.activeHarvestRules().get(cropKey);
            return rule == null || !rule.hasMatureStage() ? null : rule.matureStage();
        }

        @Override public RuleEvidence ruleEvidence(String cropKey) {
            StardewHarvestRule rule = assembler.activeHarvestRules().get(cropKey);
            return rule == null || !rule.hasMatureStage() ? null : rule.evidence();
        }

        @Override public StardewCropLifecycle lifecycle(String cropKey) {
            StardewHarvestRule rule = assembler.activeHarvestRules().get(cropKey);
            return rule == null || rule.lifecycle() == null ? StardewCropLifecycle.UNKNOWN : rule.lifecycle();
        }

        @Override public String afterHarvestStage(String cropKey) {
            StardewHarvestRule rule = assembler.activeHarvestRules().get(cropKey);
            return rule == null ? null : rule.afterHarvestStage();
        }

        @Override public List<String> stagesOf(String cropKey) {
            return index.stagesOf(cropKey);
        }

        /**
         * 物品 ↔ 作物归属：真实身份键优先，其次 item_model 组件值。
         *
         * <p>顺序必须是「种子 → 品质/特殊变种 → 普通产物」：种子最高优先级，变种先于
         * 普通产物，和库存后勤的唯一角色口径保持一致。</p>
         */
        @Override public CropRuntimeStateResolver.CropRoleRef roleOfItem(String itemModel, String identityKey) {
            for (CropDefinition crop : index.crops()) {
                if (matchesItem(crop.seedKey(), crop.seedModel(), itemModel, identityKey)) {
                    return role(crop, CropRuntimeStateResolver.CropRole.SEED);
                }
            }
            for (CropDefinition crop : index.crops()) {
                if (matchesAny(crop.variantKeys(), crop.variantModels(), itemModel, identityKey)) {
                    return role(crop, CropRuntimeStateResolver.CropRole.VARIANT);
                }
            }
            for (CropDefinition crop : index.crops()) {
                if (matchesAny(crop.produceKeys(), crop.produceModels(), itemModel, identityKey)) {
                    return role(crop, CropRuntimeStateResolver.CropRole.PRODUCE);
                }
            }
            return null;
        }

        private CropRuntimeStateResolver.CropRoleRef role(CropDefinition crop, CropRuntimeStateResolver.CropRole kind) {
            return new CropRuntimeStateResolver.CropRoleRef(crop.cropKey(), crop.chineseName(), kind);
        }

        private boolean matchesItem(String key, String model, String itemModel, String identityKey) {
            if (key != null && identityKey != null && key.equals(identityKey)) return true;
            return model != null && model.equals(itemModel);
        }

        private boolean matchesAny(List<String> keys, List<String> models, String itemModel, String identityKey) {
            if (keys != null && identityKey != null && keys.contains(identityKey)) return true;
            return models != null && itemModel != null && models.contains(itemModel);
        }
    }

    /** 供模块安装到唯一判定组件（旧模块 {@code new CropRuntimeSource()} 的落点） */
    public CropRuntimeStateResolver.CropRuntimeSource runtimeSource() {
        return new CropRuntimeSource();
    }

    /**
     * 人工标记成熟的结果码。
     *
     * <p>用结果码而不是 {@code boolean}：调用方必须能区分「阶段不存在」「特殊变种」
     * 「与已有规则冲突」「资源未就绪」这些完全不同的失败，才能给出正确的中文提示；
     * 也才能保证冲突时一个字节都没写盘。</p>
     */
    public enum MatureMarkStatus {
        /** 已保存（首次建立规则，或显式「强制」纠错后覆盖） */
        SAVED,
        /** 已有规则与本次阶段完全一致：幂等确认，未改动任何文件 */
        UNCHANGED,
        /** 与已有可靠成熟规则冲突（普通模式拒绝覆盖） */
        CONFLICT,
        /** 特殊变种阶段（金色 / 巨大 / 变种），不能作为普通成熟阶段 */
        SPECIAL_STAGE,
        /** 阶段不是该作物在当前服务器资源里真实存在的阶段 */
        UNKNOWN_STAGE,
        /** 资源未就绪（环境 / 检测 / 索引 / ServerKey 任一不满足） */
        NOT_READY,
        /** 该作物在当前服务器资源里没有任何真实阶段证据，无法建立逐作物签名（不写盘） */
        NO_STAGE_EVIDENCE,
        /** 作物不在当前服务器资源索引 */
        UNKNOWN_CROP,
        /** 落盘失败 */
        FAILED
    }

    /**
     * 人工标记成熟的结果。
     *
     * @param status           结果码
     * @param cropKey          作物键
     * @param stageName        本次要写入的阶段（已归一为资源阶段名）
     * @param previousStage    改动前已记录的成熟阶段；无规则为 null
     * @param previousEvidence 改动前的规则来源；无规则为 null
     */
    public record MatureMarkOutcome(MatureMarkStatus status, String cropKey, String stageName,
                                    String previousStage, RuleEvidence previousEvidence) {
    }

    /**
     * 人工标记成熟的前置闸门：环境 / 资源 / 索引 / ServerKey 任一未满足都不得进入解析。
     *
     * <p>资源尚未检测时真正的问题不是玩家输入，因此调用方必须先问这里，
     * 未通过就既不解析 crop 也不解析 stage，更不写盘。</p>
     */
    public boolean matureMarkAllowed() {
        return GameProbe.isMultiplayer()
            && ResourceExtractionService.isReady()
            && StardewContext.serverKey() != null
            && !index.isEmpty();
    }

    /**
     * 人工标记某作物的成熟阶段（全项目唯一写入口：准星模式 / 参数模式 / 「强制」纠错共用）。
     *
     * <p><b>写盘前必须依次通过：</b></p>
     * <ol>
     *   <li>环境与资源闸门（{@link #matureMarkAllowed()}）；</li>
     *   <li>作物必须存在于当前服务器资源索引；</li>
     *   <li>阶段必须属于该作物在当前服务器资源里真实存在的阶段（或与已记录成熟阶段一致）——
     *       绝不接受任意字符串，也绝不生成 {@code stage_1~stage_10}；</li>
     *   <li>特殊变种阶段（{@code golden/giant/gigantic/variation}）一律拒绝，{@code force} 也不例外：
     *       它们需要独立的收割 / 行为规则；</li>
     *   <li>冲突保护：已有成熟规则且与本阶段不同时，普通模式直接拒绝；
     *       只有显式 {@code force=true}（{@code .stardew 标记成熟 强制}）才允许覆盖。</li>
     * </ol>
     *
     * <p>任何一步失败都<b>不写盘、不覆盖旧规则、不创建规则文件</b>；
     * {@code ServerKey + fingerprint + cropKey} 三层隔离与自动
     * DOCUMENTED / LEARNING / VERIFIED 架构完全不受影响。</p>
     *
     * @param force 仅由显式「强制」分支传入 true（普通模式恒为 false）
     */
    public MatureMarkOutcome markMature(String cropKey, String stageName, boolean force) {
        return markMature(cropKey, stageName, force, null);
    }

    /**
     * 同 {@link #markMature(String, String, boolean)}，额外接受一条<b>世界实证身份</b>。
     *
     * <p><b>为什么需要它：</b>部分服务器把阶段模型打散 / 混淆（资源包里扫不出 {@code _stage_} 条目），
     * 阶段清单 {@link StardewResourceIndex#stagesOf(String)} 于是为空——准星模式明明刚在同一株作物上
     * 读出了 {@code customcrops:tomato_stage_4}，写盘时却被「资源里不存在该阶段」挡回去，
     * 表现为「作物就在眼前却不让校准」。世界实证是这台服务器真实下发的数据，与玩家手打字符串不同，
     * 因此只在准星模式（{@code observedIdentity != null}）时才认这条证据；参数模式依旧严格。</p>
     *
     * @param observedIdentity 准星模式解析出的作物身份；参数模式传 {@code null}
     */
    public MatureMarkOutcome markMature(String cropKey, String stageName, boolean force, String observedIdentity) {
        if (!matureMarkAllowed())
            return new MatureMarkOutcome(MatureMarkStatus.NOT_READY, cropKey, stageName, null, null);
        if (cropKey == null || index.cropByKey(cropKey) == null)
            return new MatureMarkOutcome(MatureMarkStatus.UNKNOWN_CROP, cropKey, stageName, null, null);

        String stage = normalizeStageName(stageName);
        if (stage == null)
            return new MatureMarkOutcome(MatureMarkStatus.UNKNOWN_STAGE, cropKey, stageName, null, null);

        // 冲突判断前必须先让规则表反映本隔离域的已存规则，否则会把已存规则当成「没有规则」而静默覆盖
        String serverKey = StardewContext.serverKey();
        if (assembler.profile() == null) assembler.reload(serverKey);

        StardewHarvestRule previous = assembler.activeHarvestRules().get(cropKey);
        boolean hasPrevious = previous != null && previous.hasMatureStage();
        String previousStage = hasPrevious ? previous.matureStage() : null;
        RuleEvidence previousEvidence = hasPrevious ? previous.evidence() : null;

        if (!isRealStage(cropKey, stage, previousStage, observedIdentity))
            return new MatureMarkOutcome(MatureMarkStatus.UNKNOWN_STAGE, cropKey, stage, previousStage, previousEvidence);

        if (CropRuntimeStateResolver.isSpecialStage(stage))
            return new MatureMarkOutcome(MatureMarkStatus.SPECIAL_STAGE, cropKey, stage, previousStage, previousEvidence);

        if (previousStage != null) {
            if (previousStage.equalsIgnoreCase(stage))
                return new MatureMarkOutcome(MatureMarkStatus.UNCHANGED, cropKey, stage, previousStage, previousEvidence);
            if (!force)
                return new MatureMarkOutcome(MatureMarkStatus.CONFLICT, cropKey, stage, previousStage, previousEvidence);
        }

        String fingerprint = ResourceExtractionService.fingerprint();
        String signature = assembler.activeCropSignatures().get(cropKey);
        if (fingerprint == null) return new MatureMarkOutcome(MatureMarkStatus.NOT_READY, cropKey, stage, previousStage, previousEvidence);
        // 逐作物签名建立不起来说明「这台服务器的这个作物没有任何可用资源证据」：写盘只会留下一条
        // 无据可依的规则，因此不写。旧实现把它并进 NOT_READY，玩家看到的是「资源未就绪」——
        // 与真实原因不符，排查时会一直往「再检测一次」上使劲。
        if (signature == null)
            return new MatureMarkOutcome(MatureMarkStatus.NO_STAGE_EVIDENCE, cropKey, stage, previousStage, previousEvidence);

        StardewHarvestRule manual = new StardewHarvestRule(stage,
            StardewHarvestAction.RIGHT_CLICK,
            previous == null ? StardewCropLifecycle.UNKNOWN : previous.lifecycle(),
            previous == null ? null : previous.afterHarvestStage(), RuleEvidence.VERIFIED, signature);
        Map<String, StardewHarvestRule> rules = new LinkedHashMap<>(assembler.activeHarvestRules());
        rules.put(cropKey, manual);
        if (!assembler.saveRulesAndRebuild(serverKey, fingerprint, rules))
            return new MatureMarkOutcome(MatureMarkStatus.FAILED, cropKey, stage, previousStage, previousEvidence);
        return new MatureMarkOutcome(MatureMarkStatus.SAVED, cropKey, stage, previousStage, previousEvidence);
    }

    /**
     * 阶段是否属于该作物在当前服务器资源里真实存在的阶段。
     *
     * <p>数据源是资源扫描结果 {@link StardewResourceIndex#stagesOf(String)}；另外允许
     * 「与该作物已记录的成熟阶段一致」——那是本服务器档案里的真实证据，不是玩家随便输入的字符串。
     * 准星模式还会带上刚读到的世界实证身份（见
     * {@link #markMature(String, String, boolean, String)}）。</p>
     */
    private boolean isRealStage(String cropKey, String stage, String recordedStage, String observedIdentity) {
        for (String real : index.stagesOf(cropKey)) {
            if (real != null && real.equalsIgnoreCase(stage)) return true;
        }
        if (recordedStage != null && recordedStage.equalsIgnoreCase(stage)) return true;
        // 本会话在世界 / 界面里真实见过该阶段（服务器下发过的数据）：资源包阶段清单为空也能校准
        if (StardewCropNameStore.hasObservedStage(cropKey, stage)) return true;
        if (observedIdentity == null) return false;
        // 世界实证：准星刚在同一株作物上读到这个阶段（如 customcrops:tomato_stage_4）。
        // 作物键必须对得上，绝不拿别的作物的阶段给这株作物背书。
        String observedPath = CropRuntimeStateResolver.pathOf(observedIdentity).toLowerCase(Locale.ROOT);
        String observedStage = CropRuntimeStateResolver.stageName(observedPath);
        String observedCrop = CropRuntimeStateResolver.stageCropKey(observedPath);
        return observedStage != null && observedStage.equals(stage)
            && (observedCrop == null || observedCrop.equalsIgnoreCase(cropKey));
    }

    /**
     * 该作物是否已有可直接执行并验证的完整收获规则（缺生命周期时仍需一次真实收割来确认）
     */
    public boolean harvestRuleComplete(String cropKey) {
        StardewHarvestRule rule = cropKey == null ? null : assembler.activeHarvestRules().get(cropKey);
        return rule != null && rule.completeVerified();
    }

    /**
     * 当前服务器资源版本下已有成熟阶段的作物键（「清除收获规则」只对这些作物有意义）。
     *
     * <p>给 TAB 补全用：清除是纠错动作，玩家要找的必然是「自己配过 / 模块学过的那几种」，
     * 而不是本服全部 56 种作物的技术键（实机反馈：一屏 agaricus_xxx 根本找不到自己要清的那个）。</p>
     */
    public List<String> ruledCropKeys() {
        List<String> keys = new ArrayList<>();
        for (Map.Entry<String, StardewHarvestRule> entry : assembler.activeHarvestRules().entrySet()) {
            StardewHarvestRule rule = entry.getValue();
            if (rule == null || !rule.hasMatureStage()) continue;
            if (index.cropByKey(entry.getKey()) == null) continue;
            keys.add(entry.getKey());
        }
        Collections.sort(keys);
        return keys;
    }

    /**
     * 人工删除某作物的收获规则（成熟阶段与生命周期一起清掉）。
     *
     * @param cropKey  canonical cropKey
     * @param previousStage 改动前记录的成熟阶段（无规则为 null）
     * @param previousEvidence 改动前的规则来源（无规则为 null）
     * @param saved    是否真的写盘成功
     */
    public record RuleClearOutcome(String cropKey, String previousStage, RuleEvidence previousEvidence, boolean saved) {
    }

    /**
     * 人工删除某作物在当前服务器资源版本下的收获规则。
     *
     * <p><b>为什么需要它：</b>人工校准写错一个阶段后，旧规则会把那一阶段当成成熟——真正成熟的作物反而
     * 永远不收，而模块会对着还没熟的植株反复试探。过去只能让玩家去改 JSON 文件，删完还得重启客户端，
     * 否则内存里的旧规则会在下一次保存时写回（实机事故）。这里走与人工标记<b>同一个</b>写入口
     * （{@code saveRulesAndRebuild}）：写盘成功即替换内存规则并重建 Profile，立刻生效，不需要重启。</p>
     *
     * <p>删除后该作物回到「无规则」：成熟植株由低风险空手试探重新学习生命周期，后续自动收菜。</p>
     *
     * @return 删除结果；资源未就绪 / 作物不在索引 / 写盘失败返回 {@code null}（调用方给中文原因）
     */
    public RuleClearOutcome clearHarvestRule(String cropKey) {
        if (!matureMarkAllowed()) return null;
        if (cropKey == null || index.cropByKey(cropKey) == null) return null;
        String serverKey = StardewContext.serverKey();
        String fingerprint = ResourceExtractionService.fingerprint();
        if (fingerprint == null) return null;
        if (assembler.profile() == null) assembler.reload(serverKey);

        StardewHarvestRule previous = assembler.activeHarvestRules().get(cropKey);
        Map<String, StardewHarvestRule> rules = new LinkedHashMap<>(assembler.activeHarvestRules());
        rules.remove(cropKey);
        boolean saved = assembler.saveRulesAndRebuild(serverKey, fingerprint, rules);
        return new RuleClearOutcome(cropKey,
            previous == null ? null : previous.matureStage(),
            previous == null ? null : previous.evidence(), saved);
    }

    /**
     * 把玩家输入的阶段统一成资源阶段名。
     *
     * <p>兼容误输入的完整身份（{@code customcrops:tomato_stage_4}）与大小写差异；
     * 只做字符串归一，不猜测、不生成任何阶段。</p>
     */
    private static String normalizeStageName(String input) {
        if (input == null) return null;
        String text = input.trim();
        if (text.isEmpty()) return null;
        String path = CropRuntimeStateResolver.pathOf(text).toLowerCase(Locale.ROOT);
        String stage = CropRuntimeStateResolver.stageName(path);
        return stage == null ? text : stage;
    }
}
