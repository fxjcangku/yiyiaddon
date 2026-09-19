package com.yiyiaddon.feature.identity.ui;

import com.yiyiaddon.feature.identity.IdConfigModule;
import com.yiyiaddon.model.identity.BlockIdentity;
import com.yiyiaddon.model.identity.EntityIdentity;
import com.yiyiaddon.model.identity.ItemIdentity;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.platform.storage.GamePaths;
import com.yiyiaddon.platform.world.WorldIdentity;
import com.yiyiaddon.service.identity.IdentityService;
import com.yiyiaddon.ui.component.ButtonRow;
import com.yiyiaddon.ui.component.CompactElement;
import com.yiyiaddon.ui.component.ListRow;
import com.yiyiaddon.ui.component.ListSection;
import com.yiyiaddon.ui.component.ModuleRow;
import com.yiyiaddon.ui.component.SearchRow;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.render.ItemIconCache;
import com.yiyiaddon.ui.render.MinecraftText;
import com.yiyiaddon.ui.screen.HelpPanelScreen;
import com.yiyiaddon.ui.theme.ClickGuiThemeColors;
import com.yiyiaddon.ui.widget.Button;
import com.yiyiaddon.ui.widget.IconButton;
import com.yiyiaddon.ui.widget.SettingTextBox;
import io.github.humbleui.skija.Canvas;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

/**
 * ID 配置管理模块的独立页面（旧项目 {@code IdConfigModule} 的完整管理面板）。
 *
 * <p><b>界面公式（第十九章第 128 条）</b>：按钮文字、字段标签、行渲染格式、筛选与分页口径全部
 * 逐字取自旧项目面板；主题、按钮样式、控件形态全部用本项目现有资产。</p>
 *
 * <p><b>与旧项目一致的口径</b>：每页 20 条；条目顺序固定为「物品 → 实体 → 方块」；分类筛选选中态为
 * 文字前缀 {@code §a✓ }、未选中为 {@code §7}；方块状态筛选只在「方块」分类下出现；搜索同时匹配
 * 中文名、技术 ID 与坐标（方块额外匹配语义字段）。</p>
 *
 * <p>独立入口「更多管理」由 {@link IdScreens} 打开，跳转关系集中在该类；使用说明正文按用户
 * 2026-09-17 口径<b>内嵌在本页末尾</b>，不再单摆「§e使用说明」按钮（正文逐字取自
 * {@link com.yiyiaddon.feature.identity.IdConfigModule#buildHelpContent()}）。</p>
 */
public final class IdConfigPage extends CompactModulePage implements ModulePage {

    /** 每页条数，与旧项目一致。 */
    private static final int PAGE_SIZE = 20;

    /** 行尾删除按钮图标（Material Symbols：remove）。 */
    private static final String GLYPH_REMOVE = "\uE15B";

    private static final float PAGINATION_HEIGHT = 26f;
    private static final float PAGE_INDICATOR_WIDTH = 72f;
    private static final float PAGE_INDICATOR_SIZE = 11f;
    /** 汇总文字行的行高：与模块页里同为纯文字行的其它行取同一档（模块中心的行高，24 → 原 22）。 */
    private static final float SUMMARY_HEIGHT = ModuleRow.HEIGHT;
    private static final float HEADER_HEIGHT = 24f;
    private static final float TEXT_SIZE = 11f;

    private final IdConfigModule module;
    private final ListSection listSection = new ListSection(() -> "§8无匹配记录，请调整搜索或筛选条件");
    private final List<Object> filtered = new ArrayList<>();

    /** 面板状态：跨列表重建保留。 */
    private String search = "";
    private int filterType = 0;   // 0=全部 1=物品 2=实体 3=方块
    private int blockStatus = 0;  // 0=全部 1=已确认 2=未知
    private int page = 0;
    /** 列表是否需要在下一帧重建。 */
    private boolean dirty = true;

    public IdConfigPage(IdConfigModule module) {
        this.module = module;
        build();
    }

    @Override
    public BasePage createPage(ModuleEntry entry) {
        return this;
    }

    @Override
    public String getTitle() {
        return module.displayName();
    }

    @Override
    public String getSubtitle() {
        return module.description();
    }

    @Override
    public void update(float dt) {
        // 行数随筛选变化，必须在推算布局之前重建，否则本帧高度与命中都会错位。
        if (dirty) {
            dirty = false;
            rebuildRows();
        }
        super.update(dt);
    }

    // ── 构建 ──

    private void build() {
        // 头部动作区：旧项目面板的四个满宽按钮
        addCore(new ButtonRow(new Button("识别物品（主手→副手）", this::identifyItem)));
        addCore(new ButtonRow(new Button("识别准星方块", this::identifyBlock)));
        addCore(new ButtonRow(new Button("刷新（重读磁盘）", this::refreshFromDisk)));
        addCore(new ButtonRow(new Button("更多管理", this::openManagement)));

        // 汇总：旧项目面板原文格式
        addCore(new TextLine(summaryText()).height(SUMMARY_HEIGHT));

        // 搜索：旧项目标签原文（旧项目只有标签与输入框，无额外说明文字）；输入框铺满标签右侧
        addCore(new SearchRow("§7搜索（中文名 / 技术ID / 坐标）",
                new SettingTextBox(() -> search, this::applySearch, 64)));

        // 分类筛选：选中态为文字前缀，与旧项目一致
        addCore(new ButtonRow(
                filterButton("全部", 0),
                filterButton("物品", 1),
                filterButton("实体", 2),
                filterButton("方块", 3)));

        // 方块状态筛选：只在「方块」分类下出现
        addCore(blockStatusFilter());

        // 清单表头 + 清单 + 分页
        addCore(new TextLine(this::listHeaderText).height(HEADER_HEIGHT).size(TEXT_SIZE).bold(true));
        addCore(listSection);
        addCore(pagination());

        // 使用说明内嵌在分页之后（用户 2026-09-17 口径：不再单摆「§e使用说明」按钮，
        // 正文逐字来自 IdConfigModule.buildHelpContent，仅去掉独立窗口的外框三行）
        for (String line : HelpPanelScreen.inlineContent(module.buildHelpContent())) {
            addCore(new TextLine(line));
        }
    }

    /** 汇总行：旧项目面板原文格式。 */
    private Supplier<String> summaryText() {
        return () -> {
            IdentityService service = IdentityService.shared();
            return "§7物品 §f" + service.itemCount()
                    + " §8▸ §7实体 §f" + service.entityCount()
                    + " §8▸ §7方块 §f" + service.blockCount()
                    + " §8▸ §7快照 §f" + service.blockSnapshotCount();
        };
    }

    /** 清单表头：旧项目面板原文格式（标题 + 条数 + 筛选条件 + 页码）。 */
    private String listHeaderText() {
        String filter = switch (filterType) {
            case 1 -> "物品";
            case 2 -> "实体";
            case 3 -> "方块";
            default -> "全部";
        };
        if (filterType == 3 && blockStatus != 0) {
            filter += blockStatus == 1 ? "·已确认" : "·未知";
        }
        return "§b§l▌ 当前ID清单 §8▸ §e共 " + filtered.size() + " 条（筛选：" + filter + "）§8▸ 第 "
                + (page + 1) + "/" + totalPages() + " 页";
    }

    /** 分类筛选按钮：选中显示 {@code §a✓ }，未选中显示 {@code §7}。 */
    private Button filterButton(String title, int index) {
        return new Button(() -> (filterType == index ? "§a✓ " : "§7") + title, () -> selectType(index));
    }

    /** 方块状态筛选行：非「方块」分类时高度为 0，不占布局空间。 */
    private CompactElement blockStatusFilter() {
        ButtonRow row = new ButtonRow(
                blockStatusButton("方块·全部", 0),
                blockStatusButton("方块·已确认", 1),
                blockStatusButton("方块·未知", 2));
        return new CompactElement() {
            @Override
            public float height() {
                return filterType == 3 ? row.height() : 0f;
            }

            @Override
            public void update(float dt) {
                row.update(dt);
            }

            @Override
            public void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY) {
                if (filterType == 3) row.draw(canvas, x, y, width, alpha, mouseX, mouseY);
            }

            @Override
            public boolean onClick(float mx, float my, float x, float y, float width, int button) {
                return filterType == 3 && row.onClick(mx, my, x, y, width, button);
            }

            @Override
            public boolean onDrag(float mx, float my, float x, float y, float width) {
                return filterType == 3 && row.onDrag(mx, my, x, y, width);
            }
        };
    }

    /**
     * 分页行：左侧「§7上一页」、中间「§eP / T」、右侧「§7下一页」，文案与旧项目一致。
     *
     * <p>自绘而非用控件行：页码需要在两侧按钮之间严格居中。</p>
     */
    private CompactElement pagination() {
        return new CompactElement() {
            private final Button prev = new Button("§7上一页", () -> turnPage(-1));
            private final Button next = new Button("§7下一页", () -> turnPage(1));

            @Override
            public float height() {
                return PAGINATION_HEIGHT;
            }

            @Override
            public void update(float dt) {
                prev.update(dt);
                next.update(dt);
            }

            @Override
            public void draw(Canvas canvas, float x, float y, float width, float alpha, float mouseX, float mouseY) {
                float buttonWidth = buttonWidth(width);
                float buttonY = y + (PAGINATION_HEIGHT - prev.getHeight()) / 2f;
                prev.hover(mouseX, mouseY, x, buttonY, buttonWidth);
                prev.drawAt(canvas, x, buttonY, buttonWidth, alpha);
                float nextX = x + width - buttonWidth;
                next.hover(mouseX, mouseY, nextX, buttonY, buttonWidth);
                next.drawAt(canvas, nextX, buttonY, buttonWidth, alpha);

                String indicator = "§e" + (page + 1) + " / " + totalPages();
                float textWidth = MinecraftText.measure(indicator, PAGE_INDICATOR_SIZE, false);
                float indicatorX = x + buttonWidth + (width - buttonWidth * 2f - textWidth) / 2f;
                MinecraftText.draw(canvas, indicator, indicatorX,
                        y + PAGINATION_HEIGHT / 2f + PAGE_INDICATOR_SIZE * 0.36f, PAGE_INDICATOR_SIZE,
                        ClickGuiThemeColors.current().primaryText, alpha);
            }

            @Override
            public boolean onClick(float mx, float my, float x, float y, float width, int button) {
                if (button != 0 || my < y || my > y + PAGINATION_HEIGHT) return false;
                float buttonWidth = buttonWidth(width);
                float buttonY = y + (PAGINATION_HEIGHT - prev.getHeight()) / 2f;
                if (prev.onClickAt(mx, my, x, buttonY, buttonWidth, button)) return true;
                return next.onClickAt(mx, my, x + width - buttonWidth, buttonY, buttonWidth, button);
            }

            @Override
            public boolean onDrag(float mx, float my, float x, float y, float width) {
                return false;
            }

            private float buttonWidth(float rowWidth) {
                return Math.max(24f, (rowWidth - PAGE_INDICATOR_WIDTH) / 2f);
            }
        };
    }

    private Button blockStatusButton(String title, int index) {
        return new Button(() -> (blockStatus == index ? "§a✓ " : "§7") + title, () -> selectBlockStatus(index));
    }

    // ── 交互 ──

    /** 切换主分类；离开「方块」时清除方块状态条件，避免统计与列表残留。 */
    private void selectType(int type) {
        if (filterType == type) return;
        filterType = type;
        if (type != 3) blockStatus = 0;
        page = 0;
        dirty = true;
    }

    private void selectBlockStatus(int status) {
        if (blockStatus == status) return;
        blockStatus = status;
        page = 0;
        dirty = true;
    }

    private void applySearch(String value) {
        search = value == null ? "" : value.trim();
        page = 0;
        dirty = true;
    }

    private void turnPage(int delta) {
        int target = page + delta;
        if (target < 0 || target >= totalPages()) return;
        page = target;
        dirty = true;
    }

    private void identifyItem() {
        module.identifyItem();
        dirty = true;
    }

    private void identifyBlock() {
        module.identifyBlock();
        dirty = true;
    }

    private void refreshFromDisk() {
        module.reloadFromDisk();
        page = 0;
        dirty = true;
    }

    private void openManagement() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        IdScreens.openManagement(client.gui.screen(), module);
    }

    // ── 列表重建 ──

    private int totalPages() {
        return Math.max(1, (int) Math.ceil(filtered.size() / (double) PAGE_SIZE));
    }

    /** 按分类 + 搜索 + 方块状态收集命中条目（顺序固定：物品 → 实体 → 方块），并生成当前页的行元素。 */
    private void rebuildRows() {
        filtered.clear();
        String query = search.toLowerCase(Locale.ROOT);
        IdentityService service = IdentityService.shared();

        if (filterType == 0 || filterType == 1) {
            for (ItemIdentity identity : service.allItems()) {
                if (matchesItem(identity, query)) filtered.add(identity);
            }
        }
        if (filterType == 0 || filterType == 2) {
            for (EntityIdentity identity : service.allEntities()) {
                if (matchesEntity(identity, query)) filtered.add(identity);
            }
        }
        if (filterType == 0 || filterType == 3) {
            for (BlockIdentity identity : service.allBlocks()) {
                if (matchesBlock(identity, query)) filtered.add(identity);
            }
        }

        int totalPages = totalPages();
        if (page >= totalPages) page = totalPages - 1;
        if (page < 0) page = 0;

        List<CompactElement> rows = new ArrayList<>();
        int from = page * PAGE_SIZE;
        int to = Math.min(from + PAGE_SIZE, filtered.size());
        for (int i = from; i < to; i++) {
            Object entry = filtered.get(i);
            if (entry instanceof ItemIdentity item) rows.add(itemRow(item));
            else if (entry instanceof EntityIdentity entity) rows.add(entityRow(entity));
            else if (entry instanceof BlockIdentity block) rows.add(blockRow(block));
        }
        listSection.setRows(rows);
    }

    private boolean matchesItem(ItemIdentity identity, String query) {
        if (query.isEmpty()) return true;
        return lower(identity.displayName()).contains(query) || lower(identity.itemId()).contains(query);
    }

    private boolean matchesEntity(EntityIdentity identity, String query) {
        if (query.isEmpty()) return true;
        return lower(identity.displayName()).contains(query) || lower(identity.entityId()).contains(query);
    }

    private boolean matchesBlock(BlockIdentity block, String query) {
        if (blockStatus == 1 && !block.isConfirmed()) return false;
        if (blockStatus == 2 && block.isConfirmed()) return false;
        if (query.isEmpty()) return true;
        return lower(block.displayName()).contains(query)
                || lower(block.blockId()).contains(query)
                || lower(block.semanticModel()).contains(query)
                || lower(block.semanticIdentity()).contains(query)
                || lower(block.semanticName()).contains(query)
                || lower(block.semanticSource()).contains(query)
                || lower(block.semanticReason()).contains(query)
                || lower(block.blockState()).contains(query)
                || (block.x() + "," + block.y() + "," + block.z()).contains(query);
    }

    private static String lower(String value) {
        return value == null ? "" : value.toLowerCase(Locale.ROOT);
    }

    // ── 行渲染（文案格式与旧项目一致） ──

    private CompactElement itemRow(ItemIdentity identity) {
        return new ListRow(() -> "§b[物品] §a" + identity.displayName())
                .icon((canvas, x, y, size) -> drawItemIcon(canvas, identity.itemId(), x, y, size))
                .detail(() -> "§7" + identity.itemId())
                .action(deleteButton(() -> {
                    module.removeItem(identity);
                    dirty = true;
                }));
    }

    private CompactElement entityRow(EntityIdentity identity) {
        return new ListRow(() -> "§b[实体] §a" + identity.displayName())
                .icon((canvas, x, y, size) -> drawEntityIcon(canvas, identity.entityId(), x, y, size))
                .detail(() -> "§7" + identity.entityId())
                .action(deleteButton(() -> {
                    module.removeEntity(identity);
                    dirty = true;
                }));
    }

    private CompactElement blockRow(BlockIdentity block) {
        String dimension = WorldIdentity.dimensionDisplayName(block.dimension());
        String coord = block.x() + "," + block.y() + "," + block.z();
        String carrier = "§7载体：" + block.blockId() + " · " + dimension + " · " + coord;
        Runnable remove = () -> {
            module.removeBlock(block);
            dirty = true;
        };

        if (block.isConfirmed()) {
            String semanticSuffix = block.semanticIdentity() == null || block.semanticIdentity().isBlank()
                    || block.semanticIdentity().equals(block.displayName())
                    ? "" : " §8▸ §b" + block.semanticIdentity();
            return new ListRow(() -> "§b[方块] §a" + block.displayName() + semanticSuffix)
                    .icon((canvas, x, y, size) -> drawBlockIcon(canvas, block.blockId(), x, y, size))
                    .detail(() -> carrier)
                    .action(deleteButton(remove));
        }
        return new ListRow("§e[方块] 未知语义（状态 ID：UNKNOWN）")
                .icon((canvas, x, y, size) -> drawBlockIcon(canvas, block.blockId(), x, y, size))
                .detail(() -> carrier)
                .action(deleteButton(remove));
    }

    private IconButton deleteButton(Runnable action) {
        return new IconButton(GLYPH_REMOVE, action).danger();
    }

    // ── 图标取值 ──

    private static boolean drawItemIcon(Canvas canvas, String itemId, float x, float y, float size) {
        Item item = itemById(itemId);
        return item != null && ItemIconCache.getInstance().draw(canvas, item.getDefaultInstance(), x, y, size);
    }

    private static boolean drawBlockIcon(Canvas canvas, String blockId, float x, float y, float size) {
        Identifier id = Identifier.tryParse(blockId);
        if (id == null) return false;
        Block block = BuiltInRegistries.BLOCK.getValue(id);
        return block != null && ItemIconCache.getInstance().drawBlock(canvas, block, x, y, size);
    }

    private static boolean drawEntityIcon(Canvas canvas, String entityId, float x, float y, float size) {
        Identifier id = Identifier.tryParse(entityId);
        if (id == null) return false;
        EntityType<?> type = BuiltInRegistries.ENTITY_TYPE.getValue(id);
        return type != null && ItemIconCache.getInstance().drawEntity(canvas, type, x, y, size);
    }

    private static Item itemById(String itemId) {
        Identifier id = Identifier.tryParse(itemId);
        if (id == null) return null;
        Item item = BuiltInRegistries.ITEM.getValue(id);
        return item == null || item == Items.AIR ? null : item;
    }
}
