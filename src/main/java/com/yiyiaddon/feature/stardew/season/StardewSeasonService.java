package com.yiyiaddon.feature.stardew.season;

import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventBus;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.event.ServerTextEvent;
import com.yiyiaddon.feature.stardew.StardewContext;
import com.yiyiaddon.feature.stardew.profile.CropDefinition;
import com.yiyiaddon.feature.stardew.service.StardewInventoryService;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemLore;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * 星露谷服务器季节服务。
 *
 * <p>服务常驻监听服务器原始文本包，不读取屏幕像素，也不依赖本模组 HUD。当前季节与种子允许季节
 * 都归一为「语义枚举或字体 + codepoint」令牌，因此自定义字体图标即使无法翻译成中文，也能通过
 * 同一令牌是否同时出现在服务器季节组件与种子 Tooltip 中完成可靠判断。</p>
 *
 * <p>当前季节只保存在本次服务器会话；种子规则缓存同时绑定 ServerKey 与资源指纹。换服、断线或
 * 资源指纹变化会清空旧令牌，绝不把 A 服字体语义套到 B 服。</p>
 *
 * <p><b>文本来源：</b>订阅 {@link ClientEventType#SERVER_TEXT}。核心在派发前已把
 * 标题 / 副标题 / 动作栏 / 聊天 / 玩家列表 / 记分板 / BOSS 栏的原文抽出为
 * {@link ServerTextEvent}，本服务拿不到数据包对象，只读文本。</p>
 */
public final class StardewSeasonService {

    /** 事件订阅所有者标识（服务级：模块开关不影响季节证据收集） */
    private static final String OWNER = "service.stardew-season";

    /** 可可靠识别时附带的四季语义；无法翻译的自定义 glyph 保持 UNKNOWN。 */
    public enum SeasonSemantic {
        SPRING("春"), SUMMER("夏"), AUTUMN("秋"), WINTER("冬"), UNKNOWN("未知");

        private final String displayName;

        SeasonSemantic(String displayName) {
            this.displayName = displayName;
        }

        /** 玩家可见中文名。 */
        public String displayName() {
            return displayName;
        }
    }

    /** 稳定季节令牌：key 参与匹配，semantic 仅作为可读附加语义。 */
    public record SeasonToken(String key, SeasonSemantic semantic, String displayName) {
    }

    /** 季节字段里的一个候选字符：字体 + 码位。人工绑定以它为定位依据。 */
    public record GlyphRef(String fontKey, int codepoint) {
        /** 便于播报的码位文本，例如 {@code U+B04A}。 */
        public String code() {
            return String.format("U+%04X", codepoint);
        }
    }

    /** 未来 HUD 或状态页可直接消费的只读会话状态。 */
    public record SeasonSnapshot(String serverKey, String fingerprint, Set<SeasonToken> tokens,
                                 String source, String rawText, long revision) {
        public SeasonSnapshot {
            tokens = tokens == null ? Set.of() : Set.copyOf(tokens);
        }

        /** 是否已经从服务器原始组件取得当前季节证据。 */
        public boolean known() {
            return !tokens.isEmpty();
        }

        /** 优先显示已知中文语义；只有 glyph 时也不能显示 U+/codepoint。 */
        public String displayName() {
            for (SeasonToken token : tokens) {
                if (token.semantic() != SeasonSemantic.UNKNOWN) return token.semantic().displayName();
            }
            return tokens.isEmpty() ? "未知" : "当前季节";
        }
    }

    /** 单个种子的允许季节证据。 */
    public record SeedSeasonRule(Set<SeasonToken> allowedTokens, String source) {
        public SeedSeasonRule {
            allowedTokens = allowedTokens == null ? Set.of() : Set.copyOf(allowedTokens);
        }

        /** Tooltip 没有可靠季节字段时保持未知。 */
        public boolean known() {
            return !allowedTokens.isEmpty();
        }
    }

    /** Plant 闸门只在 DISALLOWED 时阻止，UNKNOWN 绝不靠猜测拦截。 */
    public enum PlantingStatus {
        ALLOWED, DISALLOWED, UNKNOWN
    }

    private static final StardewSeasonService INSTANCE = new StardewSeasonService();
    private static final String[] LABELS = {"生长环境", "生长季节", "允许季节", "当前季节", "季节", "season"};

    private final Map<String, SeedSeasonRule> seedRules = new HashMap<>();
    /** 明确以“季节”为标题的记分板目标；仅它的分数值允许按无标签季节值解析。 */
    private final Set<String> seasonObjectives = new LinkedHashSet<>();
    private SeasonSnapshot snapshot = new SeasonSnapshot(null, null, Set.of(), null, null, 0L);
    /** 当前资源指纹对应的字体季节语义表；网络线程只读取 volatile 快照。 */
    private volatile StardewSeasonGlyphMap glyphMap = StardewSeasonGlyphMap.empty();
    /** 最近一次「疑似季节字段」的来源与原文，供启动自检未识别时给出可核对证据。 */
    private volatile String lastProbeSource;
    private volatile String lastProbeText;
    private volatile String lastProbeDetail;
    /** 最近一次季节字段的原始组件与其中候选字符，供人工绑定定位具体图标。 */
    private volatile Component lastProbeComponent;
    private volatile List<GlyphRef> lastProbeGlyphs = List.of();
    private boolean seasonTitleAwaitingSubtitle;
    private boolean initialized;

    private StardewSeasonService() {
    }

    /** 全局只注册一次，保证模块启用前收到的服务器季节组件也不会丢失。 */
    public static synchronized void init() {
        if (INSTANCE.initialized) return;
        INSTANCE.initialized = true;
        ClientEventBus.subscribe(OWNER, ClientEventType.SERVER_TEXT, INSTANCE::onServerText);
        ClientEventBus.subscribe(OWNER, ClientEventType.JOIN_SERVER, event -> INSTANCE.resetSession());
        ClientEventBus.subscribe(OWNER, ClientEventType.DISCONNECT, event -> INSTANCE.resetSession());
        ResourceExtractionService.addReadyListener(INSTANCE::onResourceReady);
        ResourceExtractionService.addInvalidateListener(INSTANCE::resetSession);
    }

    /** 协调器通过构造注入消费同一服务实例。 */
    public static StardewSeasonService instance() {
        return INSTANCE;
    }

    /** 当前只读状态。 */
    public synchronized SeasonSnapshot snapshot() {
        return snapshot;
    }

    /** 最近一次疑似季节字段的来源渠道（ActionBar / BossBar / TabList …）；无记录返回 null。 */
    public String probeSource() {
        return lastProbeSource;
    }

    /** 最近一次疑似季节字段的原文（截断）；无记录返回 null。 */
    public String probeText() {
        return lastProbeText;
    }

    /** 最近一次疑似季节字段的字符码位；仅用于未识别时定位，不参与判定。 */
    public String probeDetail() {
        return lastProbeDetail;
    }

    /** 最近一次季节字段里的候选字符（字体 + 码位），供人工绑定指定具体图标。 */
    public List<GlyphRef> probeGlyphs() {
        return lastProbeGlyphs;
    }

    /**
     * 人工绑定后刷新语义：丢弃字体语义表缓存，用最近一次真实季节组件重新提取并写入快照。
     *
     * <p>绑定只会影响「当前 ServerKey + fingerprint」，因此这里必须重算一次；快照 revision
     * 随之变化，协调器会自动 Replan，不需要玩家重开模块。</p>
     */
    public synchronized void refreshSemantics() {
        StardewSeasonGlyphMap.invalidate();
        glyphMap = StardewSeasonGlyphMap.current();

        // 用最近一次真实证据原地重算，保证与 acceptComponent 走同一条收窄 / 判重规则
        Component component = lastProbeComponent;
        if (component == null) return;
        acceptComponent(component, lastProbeSource);
    }

    /** 状态版本号；季节改变时协调器据此立即 Replan。 */
    public synchronized long revision() {
        return snapshot.revision();
    }

    /**
     * 判定真实种子在当前季节是否允许种植。
     *
     * <p>只比较真实服务器季节 token 与真实种子 Tooltip token；任一侧未知都返回 UNKNOWN。</p>
     */
    public synchronized PlantingStatus plantingStatus(CropDefinition crop, ItemStack seedStack) {
        // 客户端线程上确保字体语义表与当前资源指纹一致（缓存在同一指纹内只构建一次）。
        glyphMap = StardewSeasonGlyphMap.current();
        if (crop == null || !snapshot.known()) {
            return PlantingStatus.UNKNOWN;
        }
        SeedSeasonRule rule = seedStack == null || seedStack.isEmpty()
            ? cachedAllowedSeasons(crop) : allowedSeasons(crop, seedStack);
        if (!rule.known()) return PlantingStatus.UNKNOWN;

        // 允许播种：稳定 token key 完全相同（同一语义、同一字体同一 glyph、或同一 codepoint）。
        for (SeasonToken current : snapshot.tokens()) {
            for (SeasonToken allowed : rule.allowedTokens()) {
                if (current.key().equals(allowed.key())) return PlantingStatus.ALLOWED;
            }
        }

        // 禁止播种必须建立在「双方都有可靠、可比较的季节证据」之上：只有双方都解析出明确季节
        // 语义时，才允许用无交集证明禁种。纯 glyph 无法可靠比较（同季节可能用不同 glyph，
        // 不同字体的同一 codepoint 也可能语义不同），必须保持 UNKNOWN，绝不误判成 DISALLOWED。
        Set<SeasonSemantic> currentSemantics = semanticsOf(snapshot.tokens());
        Set<SeasonSemantic> allowedSemantics = semanticsOf(rule.allowedTokens());
        if (!currentSemantics.isEmpty() && !allowedSemantics.isEmpty()) {
            return java.util.Collections.disjoint(currentSemantics, allowedSemantics)
                ? PlantingStatus.DISALLOWED : PlantingStatus.ALLOWED;
        }
        return PlantingStatus.UNKNOWN;
    }

    /** 背包种子刚好耗尽后仍可复用本会话此前从同一隔离域真实种子读取的允许季节。 */
    private SeedSeasonRule cachedAllowedSeasons(CropDefinition crop) {
        String prefix = String.valueOf(snapshot.serverKey()) + '\u0000' + String.valueOf(snapshot.fingerprint())
            + '\u0000' + crop.cropKey() + '\u0000';
        for (Map.Entry<String, SeedSeasonRule> entry : seedRules.entrySet()) {
            if (entry.getKey().startsWith(prefix)) return entry.getValue();
        }
        return new SeedSeasonRule(Set.of(), "本会话尚无该种子的允许季节证据");
    }

    /** 提取一组令牌里的明确季节语义；没有可靠语义返回空集。 */
    private static Set<SeasonSemantic> semanticsOf(Set<SeasonToken> tokens) {
        Set<SeasonSemantic> result = new LinkedHashSet<>();
        for (SeasonToken token : tokens) {
            if (token.semantic() != SeasonSemantic.UNKNOWN) result.add(token.semantic());
        }
        return result;
    }

    /** 从真实 Data Components + 完整 Tooltip 读取种子允许季节。 */
    public synchronized SeedSeasonRule allowedSeasons(CropDefinition crop, ItemStack seedStack) {
        glyphMap = StardewSeasonGlyphMap.current();
        String serverKey = snapshot.serverKey();
        String fingerprint = snapshot.fingerprint();
        String model = StardewInventoryService.itemModelOf(seedStack);
        String cacheKey = String.valueOf(serverKey) + '\u0000' + String.valueOf(fingerprint) + '\u0000'
            + crop.cropKey() + '\u0000' + String.valueOf(model);
        SeedSeasonRule cached = seedRules.get(cacheKey);
        if (cached != null) return cached;

        LinkedHashSet<SeasonToken> tokens = new LinkedHashSet<>();
        ItemLore lore = seedStack.get(DataComponents.LORE);
        if (lore != null) {
            for (Component line : lore.lines()) tokens.addAll(extractFromLine(line));
        }
        Minecraft mc = Minecraft.getInstance();
        if (mc.player != null) {
            List<Component> tooltip = seedStack.getTooltipLines(
                Item.TooltipContext.of(mc.level), mc.player, TooltipFlag.NORMAL);
            for (Component line : tooltip) tokens.addAll(extractFromLine(line));
        }
        SeedSeasonRule result = new SeedSeasonRule(tokens,
            tokens.isEmpty() ? "种子 Tooltip 未提供可解析季节" : "ItemStack Data Components + Tooltip");
        seedRules.put(cacheKey, result);
        return result;
    }

    /** READY 后锁定当前资源指纹；指纹变化必须废弃旧 glyph 与种子规则。 */
    private synchronized void onResourceReady() {
        String serverKey = ResourceExtractionService.serverKey();
        String fingerprint = ResourceExtractionService.fingerprint();
        if (!java.util.Objects.equals(snapshot.serverKey(), serverKey)
            || snapshot.fingerprint() != null && !java.util.Objects.equals(snapshot.fingerprint(), fingerprint)) {
            long next = snapshot.revision() + 1;
            snapshot = new SeasonSnapshot(serverKey, fingerprint, Set.of(), null, null, next);
        } else {
            snapshot = new SeasonSnapshot(serverKey, fingerprint, snapshot.tokens(), snapshot.source(),
                snapshot.rawText(), snapshot.revision());
        }
        seedRules.clear();
        // 资源指纹已变：字体语义表必须按当前生效资源重建，旧 glyph→季节 映射一律作废。
        glyphMap = StardewSeasonGlyphMap.current();
    }

    /** 换服、断线和资源失效均清空会话证据。 */
    private synchronized void resetSession() {
        snapshot = new SeasonSnapshot(null, null, Set.of(), null, null, snapshot.revision() + 1);
        seedRules.clear();
        seasonObjectives.clear();
        seasonTitleAwaitingSubtitle = false;
        // 资源失效后旧 glyph 语义一律作废：绝不允许用旧映射继续判定 DISALLOWED。
        glyphMap = StardewSeasonGlyphMap.empty();
    }

    /**
     * 服务器文本事件入口；只取核心抽取出的只读文本，不接触数据包对象。
     *
     * <p>各来源的处理口径与原服务一致：标题 / 副标题成对出现时按「标题 / 副标题」取值，
     * 记分板只有「标题含季节标签」的目标才按季节值解析，其余一律走普通字段提取。</p>
     */
    private synchronized void onServerText(ClientEvent event) {
        ServerTextEvent text = event.text();
        if (text == null || text.text() == null) return;
        Component component = text.text();
        String objective = text.context() == null ? "" : text.context();
        switch (text.channel()) {
            case ServerTextEvent.ACTION_BAR -> acceptComponent(component, "动作栏");
            case ServerTextEvent.TITLE -> {
                seasonTitleAwaitingSubtitle = containsSeasonLabel(component) && !hasSeasonValue(component);
                acceptComponent(component, "标题");
            }
            case ServerTextEvent.SUBTITLE -> {
                if (seasonTitleAwaitingSubtitle) acceptSeasonValue(component, "标题 / 副标题");
                else acceptComponent(component, "副标题");
                seasonTitleAwaitingSubtitle = false;
            }
            case ServerTextEvent.CHAT -> acceptComponent(component, "聊天栏");
            case ServerTextEvent.CHAT_OVERLAY -> acceptComponent(component, "动作栏文本");
            case ServerTextEvent.TAB_LIST_HEADER -> acceptComponent(component, "玩家列表（顶部）");
            case ServerTextEvent.TAB_LIST_FOOTER -> acceptComponent(component, "玩家列表（底部）");
            case ServerTextEvent.OBJECTIVE_TITLE -> {
                if (containsSeasonLabel(component)) seasonObjectives.add(objective);
                else seasonObjectives.remove(objective);
                acceptComponent(component, "记分板（标题）");
            }
            case ServerTextEvent.OBJECTIVE_REMOVED -> {
                seasonObjectives.remove(objective);
                acceptComponent(component, "记分板（标题）");
            }
            case ServerTextEvent.SCORE_DISPLAY -> {
                if (seasonObjectives.contains(objective)) acceptSeasonValue(component, "记分板（季节值）");
                else acceptComponent(component, "记分板（分数）");
            }
            case ServerTextEvent.SCORE_OWNER -> {
                // 条目没有自带显示文本：仅当目标已被证明是季节目标时才用条目名兜底
                if (seasonObjectives.contains(objective)) acceptSeasonValue(component, "记分板（季节值）");
            }
            case ServerTextEvent.TEAM_DISPLAY -> acceptComponent(component, "记分板（队伍）");
            case ServerTextEvent.TEAM_PREFIX -> acceptComponent(component, "记分板（前缀）");
            case ServerTextEvent.TEAM_SUFFIX -> acceptComponent(component, "记分板（后缀）");
            case ServerTextEvent.BOSS_BAR -> acceptComponent(component, "BOSS 栏");
            default -> {
                // 未知来源不做任何处理
            }
        }
    }

    /** 只有明确含季节字段的组件才能更新当前季节，普通聊天中的单个季节词不会误触发。 */
    private void acceptComponent(Component component, String source) {
        if (component == null) return;
        String plain = component.getString();
        int start = labelEnd(plain);
        if (start < 0) return;
        recordProbe(source, plain, start);
        int end = valueEnd(plain, start);
        List<GlyphRef> glyphs = collectGlyphRefs(component, start, end);
        Set<SeasonToken> tokens = extractTokens(component, start, end);
        if (tokens.isEmpty()) {
            // 字段值区间一个字符都没取到：整组件再取一次。BOSS 栏、标题这类组件整体就是季节
            // 信息（分区名等无关字符只会变成无语义的稳定 glyph，不会造成禁种误判）。
            lastProbeDetail = "值区间为空 起" + start + " 止" + end + " 长度" + plain.length()
                + " 码位 " + codepoints(plain, 0, plain.length());
            tokens = extractTokens(component, 0, plain.length());
            if (glyphs.isEmpty()) glyphs = collectGlyphRefs(component, 0, plain.length());
        } else {
            lastProbeDetail = "码位 " + codepoints(plain, start, end);
        }
        // HUD 的季节字段值紧跟标签，第一个图标就是当前季节；后面若还有图标，属于同行其它字段
        // （金币、点券…）。只把第一个图标的语义当作当前季节，避免多语义互相冲突被判为未知。
        tokens = narrowToFirstGlyph(component, plain, start, end, glyphs, tokens);
        lastProbeComponent = component;
        lastProbeGlyphs = glyphs;
        acceptTokens(component, tokens, source);
    }

    /**
     * 当前季节语义只认字段值里的第一个候选图标；其余字符仍保留为稳定 glyph 证据。
     *
     * <p>区间里已经有明确文本语义（例如「春季」字样）时不收窄，文本优先。</p>
     */
    private Set<SeasonToken> narrowToFirstGlyph(Component component, String plain, int start, int end,
                                                List<GlyphRef> glyphs, Set<SeasonToken> tokens) {
        if (glyphs.isEmpty() || tokens.isEmpty()) return tokens;
        int from = Math.max(0, Math.min(start, plain.length()));
        int to = Math.max(from, Math.min(end, plain.length()));
        String valueText = plain.substring(from, to);

        // 文本 / translation 语义优先，且天然只描述一个季节，不需要收窄。
        LinkedHashSet<SeasonToken> textTokens = new LinkedHashSet<>();
        addKnownSemantics(valueText, textTokens);
        addTranslationSemantics(component, textTokens);
        if (!semanticsOf(textTokens).isEmpty()) return tokens;

        if (semanticsOf(tokens).size() <= 1) return tokens;

        // 多个图标语义：只保留第一个图标的语义，其余仅作稳定键，避免互相冲突被判为未知。
        LinkedHashSet<SeasonToken> narrowed = new LinkedHashSet<>(textTokens);
        addGlyphTokens(glyphs.get(0).codepoint(), glyphs.get(0).fontKey(), glyphMap, narrowed);
        for (int i = 1; i < glyphs.size(); i++) {
            narrowed.add(glyphToken(glyphs.get(i).fontKey(), glyphs.get(i).codepoint()));
        }
        return narrowed;
    }

    /** 已由标题/Objective 证明字段语义时，允许值组件本身不重复携带“季节”标签。 */
    private void acceptSeasonValue(Component component, String source) {
        if (component == null) return;
        String plain = component.getString();
        recordProbe(source, plain, 0);
        int end = valueEnd(plain, 0);
        Set<SeasonToken> tokens = extractTokens(component, 0, end);
        lastProbeDetail = "码位 " + codepoints(plain, 0, end);
        lastProbeComponent = component;
        lastProbeGlyphs = collectGlyphRefs(component, 0, end);
        acceptTokens(component, tokens, source);
    }

    /** 取出区间内的候选字符（字体 + 码位）；口径与 glyph 提取一致，排除空格与字段分隔符。 */
    private static List<GlyphRef> collectGlyphRefs(Component component, int start, int end) {
        if (component == null) return List.of();
        String plain = component.getString();
        int from = Math.max(0, Math.min(start, plain.length()));
        int to = Math.max(from, Math.min(end, plain.length()));
        LinkedHashSet<GlyphRef> refs = new LinkedHashSet<>();
        int offset = 0;
        for (Component flat : component.toFlatList()) {
            String text = flat.getString();
            int segmentStart = Math.max(0, from - offset);
            int segmentEnd = to - offset;
            if (segmentEnd > segmentStart && segmentStart < text.length()) {
                String part = text.substring(segmentStart, Math.min(segmentEnd, text.length()));
                String fontKey = fontKey(flat.getStyle().getFont());
                part.codePoints().forEach(codepoint -> {
                    if (isGlyphCandidate(codepoint)) refs.add(new GlyphRef(fontKey, codepoint));
                });
            }
            offset += text.length();
        }
        return List.copyOf(refs);
    }

    /** 把区间内字符列成 U+ 码位，供未识别时定位（不参与任何判定）。 */
    private static String codepoints(String text, int from, int to) {
        if (text == null || text.isEmpty()) return "无";
        int end = Math.min(Math.max(to, from), text.length());
        StringBuilder out = new StringBuilder();
        int count = 0;
        for (int i = Math.max(0, from); i < end && count < 8; ) {
            int codepoint = text.codePointAt(i);
            i += Character.charCount(codepoint);
            if (count > 0) out.append(' ');
            out.append(String.format("U+%04X", codepoint));
            count++;
        }
        return out.length() == 0 ? "无" : out.toString();
    }

    /**
     * 记录最近一次「疑似季节字段」的来源与<b>季节标签附近</b>的片段。
     *
     * <p>片段从季节标签本身开始截取，绝不向前带上 HUD 同行的分区名等无关信息；
     * 它只在季节未识别时用于让玩家核对，不参与任何判定。</p>
     */
    private void recordProbe(String source, String plain, int valueStart) {
        if (plain == null || plain.isBlank()) return;
        LabelHit hit = labelHit(plain);
        int from = hit != null ? hit.labelStart() : Math.max(0, valueStart - 12);
        int to = Math.min(plain.length(), Math.max(valueStart, from) + 24);
        String snippet = plain.substring(from, to).strip();
        lastProbeSource = source;
        lastProbeText = (from > 0 ? "…" : "") + snippet + (to < plain.length() ? "…" : "");
    }

    private void acceptTokens(Component component, Set<SeasonToken> tokens, String source) {
        if (tokens.isEmpty()) return;
        // 当前季节必须是唯一结论：同一行里出现多个季节（例如“下个季节”预告）时不可当作当前季节，
        // 否则会残留 {冬季, 春季} 这类历史/预告混合快照，绝不允许。
        if (distinctSeasons(tokens) > 1) return;
        String serverKey = StardewContext.serverKey();
        String fingerprint = ResourceExtractionService.fingerprint();
        if (serverKey == null) return;
        if (java.util.Objects.equals(snapshot.serverKey(), serverKey)
            && java.util.Objects.equals(snapshot.fingerprint(), fingerprint)
            && snapshot.tokens().equals(tokens)) return;
        snapshot = new SeasonSnapshot(serverKey, fingerprint, tokens, source,
            component.getString(), snapshot.revision() + 1);
    }

    /** 从含季节标签的一行里取出该字段值并生成令牌；没有季节标签返回空。 */
    private Set<SeasonToken> extractFromLine(Component line) {
        if (line == null) return Set.of();
        String plain = line.getString();
        int start = labelEnd(plain);
        if (start < 0) return Set.of();
        return extractTokens(line, start, valueEnd(plain, start));
    }

    /** 该组件是否已经在标签后给出了可解析的季节值。 */
    private boolean hasSeasonValue(Component component) {
        return !extractFromLine(component).isEmpty();
    }

    /**
     * 从 [start, end) 字符区间提取语义词、translation key 与稳定字体/codepoint 令牌。
     *
     * <p>该区间一定来自「季节字段值」（标签后或已证明语义的 Objective 值），因此区间内的
     * 非分隔字符全部保留为 glyph 证据：服务器用什么字体、什么字符表示季节图标都应能采到，
     * 识别不出语义时至少仍是稳定的 glyph/codepoint 令牌，绝不因为字体判定而整段丢失。</p>
     */
    private Set<SeasonToken> extractTokens(Component component, int start, int end) {
        if (component == null) return Set.of();
        String plain = component.getString();
        int from = Math.max(0, Math.min(start, plain.length()));
        int to = Math.max(from, Math.min(end, plain.length()));

        LinkedHashSet<SeasonToken> result = new LinkedHashSet<>();
        addKnownSemantics(plain.substring(from, to), result);
        addTranslationSemantics(component, result);

        StardewSeasonGlyphMap map = glyphMap;
        int offset = 0;
        for (Component flat : component.toFlatList()) {
            String text = flat.getString();
            int segmentStart = Math.max(0, from - offset);
            int segmentEnd = to - offset;
            if (segmentEnd > segmentStart && segmentStart < text.length()) {
                String part = text.substring(segmentStart, Math.min(segmentEnd, text.length()));
                String fontKey = fontKey(flat.getStyle().getFont());
                part.codePoints().forEach(codepoint -> addGlyphTokens(codepoint, fontKey, map, result));
            }
            offset += text.length();
        }
        return result;
    }

    /**
     * 单个 codepoint 的令牌生成：资源包字体语义优先，其次字体 + codepoint 稳定键。
     *
     * <p>能由资源贴图证据归一为四季时同时保留稳定 glyph 键，保证「同字体同 glyph」仍可精确匹配。</p>
     */
    private static void addGlyphTokens(int codepoint, String fontKey, StardewSeasonGlyphMap map,
                                       Set<SeasonToken> out) {
        SeasonSemantic mapped = map.semanticOf(fontKey, codepoint);
        if (mapped != SeasonSemantic.UNKNOWN) {
            out.add(semanticToken(mapped));
            out.add(glyphToken(fontKey, codepoint));
            out.add(codepointToken(codepoint));
            return;
        }
        if (!isGlyphCandidate(codepoint)) return;
        SeasonSemantic semantic = semanticOf(new String(Character.toChars(codepoint)));
        if (semantic != SeasonSemantic.UNKNOWN) {
            out.add(semanticToken(semantic));
            return;
        }
        out.add(glyphToken(fontKey, codepoint));
        // 同一 fingerprint 内，Objective owner 丢失 Style.font 时仍能与 Tooltip glyph 对齐。
        out.add(codepointToken(codepoint));
    }

    private static SeasonToken glyphToken(String fontKey, int codepoint) {
        String hex = Integer.toHexString(codepoint).toUpperCase(Locale.ROOT);
        return new SeasonToken("GLYPH:" + fontKey + ':' + hex, SeasonSemantic.UNKNOWN, "U+" + hex);
    }

    private static SeasonToken codepointToken(int codepoint) {
        String hex = Integer.toHexString(codepoint).toUpperCase(Locale.ROOT);
        return new SeasonToken("CODEPOINT:" + hex, SeasonSemantic.UNKNOWN, "U+" + hex);
    }

    /**
     * 一组令牌里出现了几个互不相同的季节身份。
     *
     * <p>只统计**明确语义**：只有语义才能证明「同一条消息里出现多个季节」（例如“下个季节”预告），
     * 才需要拒绝写入，避免残留 {冬季, 春季} 混合快照。纯 glyph 无法区分「一个季节图标」与
     * 「若干无关字符」，若按字符数拒绝会把整条季节证据丢掉（曾导致识别为「未知」），因此纯 glyph
     * 不做任何拒绝——反正没有语义时也不可能判定禁种。</p>
     */
    private static int distinctSeasons(Set<SeasonToken> tokens) {
        Set<String> semantics = new LinkedHashSet<>();
        for (SeasonToken token : tokens) {
            if (token.semantic() != SeasonSemantic.UNKNOWN) semantics.add(token.semantic().name());
        }
        return semantics.size();
    }

    /**
     * 字段值结束位置：遇到换行或项目统一字段分隔符 {@code | ｜ │ ▸ ▶} 停止。
     *
     * <p>不再按冒号截断：服务器 HUD 的字段排版不可预知，多取到的尾部字符只是无语义的稳定
     * glyph 噪声，不会造成误判；但过早截断会让季节图标整个丢失（曾导致识别为「未知」）。</p>
     */
    private static int valueEnd(String text, int start) {
        if (text == null) return Math.max(0, start);
        int i = Math.max(0, Math.min(start, text.length()));
        while (i < text.length()) {
            int cp = text.codePointAt(i);
            if (cp == '\n' || cp == '\r' || cp == '|' || cp == '｜' || cp == '│'
                || cp == '▸' || cp == '▶') break;
            i += Character.charCount(cp);
        }
        return i;
    }

    private static boolean containsSeasonLabel(Component component) {
        return component != null && labelEnd(component.getString()) >= 0;
    }

    /** 季节字段标签命中位置：标签起点与「字段值起点」。 */
    private record LabelHit(int labelStart, int valueStart) {
    }

    private static LabelHit labelHit(String text) {
        if (text == null || text.isBlank()) return null;
        String lower = text.toLowerCase(Locale.ROOT);
        int best = -1;
        int length = 0;
        for (String label : LABELS) {
            int at = lower.indexOf(label.toLowerCase(Locale.ROOT));
            if (at >= 0 && (best < 0 || at < best)) {
                best = at;
                length = label.length();
            }
        }
        if (best < 0) return null;
        int start = best + length;
        while (start < text.length()) {
            int cp = text.codePointAt(start);
            if (!Character.isWhitespace(cp) && "：:|｜-—>▸[]【】()（）".indexOf(cp) < 0) break;
            start += Character.charCount(cp);
        }
        return new LabelHit(best, start);
    }

    /** 返回季节字段值的起始字符位置；找不到标签返回 -1。 */
    private static int labelEnd(String text) {
        LabelHit hit = labelHit(text);
        return hit == null ? -1 : hit.valueStart();
    }

    /** 中文与英文季节词可以可靠归一；自定义图标绝不猜语义。 */
    private static void addKnownSemantics(String text, Set<SeasonToken> out) {
        String lower = text == null ? "" : text.toLowerCase(Locale.ROOT);
        if (lower.contains("春") || lower.contains("spring")) out.add(semanticToken(SeasonSemantic.SPRING));
        if (lower.contains("夏") || lower.contains("summer")) out.add(semanticToken(SeasonSemantic.SUMMER));
        if (lower.contains("秋") || lower.contains("autumn") || lower.contains("fall")) out.add(semanticToken(SeasonSemantic.AUTUMN));
        if (lower.contains("冬") || lower.contains("winter")) out.add(semanticToken(SeasonSemantic.WINTER));
    }

    /** 递归读取原始 translation key；只有 key 明确带四季词时才赋语义。 */
    private static void addTranslationSemantics(Component component, Set<SeasonToken> out) {
        if (component.getContents() instanceof TranslatableContents translatable) {
            SeasonSemantic semantic = semanticOf(translatable.getKey());
            if (semantic != SeasonSemantic.UNKNOWN) out.add(semanticToken(semantic));
        }
        for (Component sibling : component.getSiblings()) addTranslationSemantics(sibling, out);
    }

    /**
     * 季节字段值里的字符是否保留为 glyph 证据。
     *
     * <p>只排除空白、控制/格式字符与字段分隔符；其余一律保留。季节图标既可能是自定义字体、
     * 私有区或符号，也可能是默认字体下的普通字符，按字体“猜”会整段丢失图标（曾导致识别为
     * 「未知」）。多保留的噪声字符只是无语义的稳定 glyph，不会造成禁种误判。</p>
     */
    private static boolean isGlyphCandidate(int codepoint) {
        SeasonSemantic semantic = semanticOf(new String(Character.toChars(codepoint)));
        if (semantic != SeasonSemantic.UNKNOWN) return true;
        if (Character.isWhitespace(codepoint)) return false;
        int type = Character.getType(codepoint);
        if (type == Character.CONTROL || type == Character.FORMAT) return false;
        return "：:|｜│/-—>▸▶,，.。[]【】()（）".indexOf(codepoint) < 0;
    }

    /** FontDescription 结构化归一，资源字体保留完整 Identifier。 */
    private static String fontKey(FontDescription font) {
        if (font instanceof FontDescription.Resource resource) return resource.id().toString();
        return font == null ? "minecraft:default" : font.toString();
    }

    private static SeasonToken semanticToken(SeasonSemantic semantic) {
        return new SeasonToken("SEMANTIC:" + semantic.name(), semantic, semantic.displayName());
    }

    private static SeasonSemantic semanticOf(String text) {
        String lower = text == null ? "" : text.toLowerCase(Locale.ROOT);
        if (lower.contains("春") || lower.contains("spring")) return SeasonSemantic.SPRING;
        if (lower.contains("夏") || lower.contains("summer")) return SeasonSemantic.SUMMER;
        if (lower.contains("秋") || lower.contains("autumn") || lower.contains("fall")) return SeasonSemantic.AUTUMN;
        if (lower.contains("冬") || lower.contains("winter")) return SeasonSemantic.WINTER;
        return SeasonSemantic.UNKNOWN;
    }
}
