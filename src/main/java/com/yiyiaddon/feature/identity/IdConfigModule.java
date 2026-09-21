package com.yiyiaddon.feature.identity;

import com.yiyiaddon.core.ClientChat;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.identity.service.IdentityActions;
import com.yiyiaddon.feature.identity.ui.IdConfigPage;
import com.yiyiaddon.feature.identity.ui.IdScreens;
import com.yiyiaddon.model.identity.BlockIdentity;
import com.yiyiaddon.model.identity.EntityIdentity;
import com.yiyiaddon.model.identity.IdentifyMode;
import com.yiyiaddon.model.identity.ItemIdentity;
import com.yiyiaddon.platform.identity.BlockIdentifier;
import com.yiyiaddon.platform.identity.ItemIdentifier;
import com.yiyiaddon.platform.storage.GamePaths;
import com.yiyiaddon.service.identity.IdentityService;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.screen.ConfirmPanelScreen;
import com.yiyiaddon.ui.screen.HelpPanelScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

import java.awt.Desktop;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * ID 配置管理模块（旧项目 {@code IdConfigModule}）：只管已识别的身份数据。
 *
 * <p><b>用户交互资产：</b>模块中文名 {@code ID配置管理}、分类 {@code 辅助}、description 沿用旧项目
 * 原文。旧项目该模块<b>不注册任何设置项</b>，功能全部以界面按钮与列表呈现，因此本类同样不定义设置。</p>
 *
 * <p><b>界面资产：</b>完整管理面板由 {@code feature/identity/ui/IdConfigPage} 承载，独立窗口
 * （更多管理 / 数据清理 / 添加物品 / 使用说明 / 识别结果 ×3 / 二次确认）由 {@code IdScreens}
 * 统一跳转，窗口内的按钮文字与层级关系全部按旧项目复刻。</p>
 */
public final class IdConfigModule extends Module {

    /** 模块 ID，同时作为状态文件键与快捷键键名后缀 */
    public static final String MODULE_ID = "id_config";

    /** 回执前缀使用的模块名：旧项目模块显示名原文 */
    public static final String MESSAGE_MODULE = "ID配置管理";

    /** 图标字形：齿轮（Material Symbols settings，已确认存在于所引字体） */
    private static final String ICON = "\uE8B8";

    public IdConfigModule() {
        super(MODULE_ID, MESSAGE_MODULE, "assist",
                "管理已识别的 ID，可搜索删除");
    }

    @Override
    public String name() {
        return "IdConfig";
    }

    @Override
    public String icon() {
        return ICON;
    }

    @Override
    public int order() {
        return 20;
    }

    @Override
    public String version() {
        return "1.0.0";
    }

    // ── 生命周期 ──

    @Override
    protected void onEnable() {
        IdentityService.shared().load();
    }

    // ── 界面 ──

    @Override
    public ModulePage page() {
        return new IdConfigPage(this);
    }

    // ── 供页面调用的功能入口 ──

    /** 刷新：重新读取磁盘上的身份数据（旧项目面板按钮原文：刷新（重读磁盘）） */
    public void reloadFromDisk() {
        IdentityService service = IdentityService.shared();
        service.reload();
        ClientChat.send(MESSAGE_MODULE, "§a§l✓ 已刷新 ID 配置 §8▸ §f" + service.itemCount()
                + " §a§l个物品，§f" + service.entityCount()
                + " §a§l个实体，§f" + service.blockCount() + " §a§l个方块");
    }

    /**
     * 识别手持物品并写入身份库（旧项目面板按钮原文：识别物品（主手→副手））。
     *
     * <p>主手优先，主手为空读副手；播报文案逐字沿用旧项目面板。识别模式跟随「ID识别」模块，
     * 详见 {@link #identifyMode()}。</p>
     */
    public void identifyItem() {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) {
            ClientChat.send(MESSAGE_MODULE, "§c§l✗ 玩家未加载");
            return;
        }
        ItemStack held = client.player.getItemInHand(InteractionHand.MAIN_HAND);
        if (held == null || held.isEmpty()) held = client.player.getItemInHand(InteractionHand.OFF_HAND);
        if (held == null || held.isEmpty()) {
            ClientChat.send(MESSAGE_MODULE, "§c§l✗ 没有可识别物品：主手和副手都是空的");
            return;
        }
        ItemIdentity identity = ItemIdentifier.identifyItem(held);
        if (identity == null) {
            ClientChat.send(MESSAGE_MODULE, "§c§l✗ 识别失败");
            return;
        }
        // 与「ID识别」模块保持同一模式口径：非「自动保存」时只弹结果窗口，识别本身不写盘
        if (identifyMode() != IdentifyMode.AUTO_SAVE) {
            client.execute(() -> IdScreens.openItemResult(identity, client.gui.screen()));
            return;
        }
        if (IdentityService.shared().addItem(identity) != null) {
            ClientChat.send(MESSAGE_MODULE, "§a§l✓ 已识别物品 §8▸ §a§l" + identity.displayName());
        } else {
            ClientChat.send(MESSAGE_MODULE, "§c§l✗ 该物品已在 ID 配置中");
        }
    }

    /**
     * 识别准星命中的方块并保存稳定记录与状态快照（旧项目面板按钮原文：识别准星方块）。
     *
     * <p>两条播报与旧项目一致：稳定记录一条、状态快照一条，各自区分「已保存 / 已存在」。
     * 识别模式跟随「ID识别」模块，详见 {@link #identifyMode()}。</p>
     */
    public void identifyBlock() {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) {
            ClientChat.send(MESSAGE_MODULE, "§c§l✗ 玩家未加载");
            return;
        }
        BlockIdentity identity = BlockIdentifier.identify();
        if (identity == null) {
            ClientChat.send(MESSAGE_MODULE, "§c§l✗ 自动识别失败：准星当前没有指向有效方块");
            return;
        }
        // 与「ID识别」模块保持同一模式口径：非「自动保存」时只弹结果窗口，识别本身不写盘
        if (identifyMode() != IdentifyMode.AUTO_SAVE) {
            client.execute(() -> IdScreens.openBlockResult(identity, client.gui.screen()));
            return;
        }
        IdentityService service = IdentityService.shared();
        if (service.addBlock(identity) != null) {
            ClientChat.send(MESSAGE_MODULE, "§a§l✓ 已识别方块 §8▸ §a§l" + identity.displayName()
                    + " §8▸ §f" + identity.blockId());
        } else {
            ClientChat.send(MESSAGE_MODULE, "§7该方块已在方块记录中（稳定身份相同）");
        }
        String snapshotName = service.addBlockSnapshot(identity, false);
        if (snapshotName != null) {
            ClientChat.send(MESSAGE_MODULE, "§a§l✓ 已保存方块状态快照 §8▸ §f" + snapshotName);
        } else {
            ClientChat.send(MESSAGE_MODULE, "§7该方块状态快照已存在，未重复保存");
        }
    }

    /**
     * 「ID识别」模块当前的识别模式：本面板的「识别物品」与「识别准星方块」与它保持同一口径。
     *
     * <p>面板不再无条件落盘——「聊天复制/显示」与「准星方块识别」下改为弹对应结果窗口，与
     * {@code .id 物品} / {@code .id 方块} 完全一致。取不到该模块时按默认值「自动保存」处理，
     * 绝不静默改成别的模式。</p>
     */
    private static IdentifyMode identifyMode() {
        return ModuleManager.byId(IdIdentifyModule.MODULE_ID) instanceof IdIdentifyModule identify
            ? identify.config().mode()
            : IdentifyMode.AUTO_SAVE;
    }

    // ── 逐条删除（旧项目面板每行行尾减号） ──

    public void removeItem(ItemIdentity identity) {
        boolean ok = IdentityService.shared().removeItem(identity);
        ClientChat.send(MESSAGE_MODULE, ok
                ? "§c§l✗ 已删除 ID §8▸ §c§l" + identity.displayName()
                : "§c§l✗ 删除物品失败");
    }

    public void removeEntity(EntityIdentity identity) {
        boolean ok = IdentityService.shared().removeEntity(identity);
        ClientChat.send(MESSAGE_MODULE, ok
                ? "§c§l✗ 已删除实体 §8▸ §c§l" + identity.displayName()
                : "§c§l✗ 删除实体失败");
    }

    public void removeBlock(BlockIdentity identity) {
        boolean ok = IdentityService.shared().removeBlock(identity);
        ClientChat.send(MESSAGE_MODULE, ok
                ? "§c§l✗ 已删除方块 §8▸ §c§l" + identity.displayName()
                : "§c§l✗ 删除方块失败");
    }

    // ── 数据目录（旧项目「ID 更多管理」的目录入口） ──

    /** 打开物品 ID 目录 */
    public void openItemsDirectory() {
        openDirectory(IdentityService.shared().itemDirectory());
    }

    /** 打开实体 ID 目录 */
    public void openEntitiesDirectory() {
        openDirectory(IdentityService.shared().entityDirectory());
    }

    /** 打开方块目录 */
    public void openBlocksDirectory() {
        openDirectory(IdentityService.shared().blockDirectory());
    }

    /** 打开 ID 总目录 */
    public void openRootDirectory() {
        openDirectory(GamePaths.identityRoot());
    }

    /**
     * 用系统文件管理器打开目录。
     *
     * <p>26.1.2 已移除 {@code net.minecraft.Util}，改用 AWT {@code Desktop}；在独立守护线程执行，
     * 避免阻塞渲染线程；不支持时回落到 Windows 的 {@code explorer.exe /select}。</p>
     */
    private void openDirectory(Path dir) {
        try {
            Files.createDirectories(dir);
        } catch (Exception ignored) {
            // 目录创建失败仍尝试打开
        }
        Thread opener = new Thread(() -> {
            try {
                if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) {
                    Desktop.getDesktop().open(dir.toFile());
                } else {
                    new ProcessBuilder("explorer.exe", "/select," + dir.toAbsolutePath()).start();
                }
            } catch (Exception e) {
                Minecraft client = Minecraft.getInstance();
                if (client != null) {
                    client.execute(() -> ClientChat.send(MESSAGE_MODULE, "§c§l✗ 打开目录失败：" + e.getMessage()));
                }
            }
        }, "yiyiaddon-OpenDir");
        opener.setDaemon(true);
        opener.start();
    }

    /** 统计目录内的 JSON 文件数量，用于清空确认文案中的删除条数。 */
    public int countJsonFiles(Path dir) {
        if (!Files.isDirectory(dir)) return 0;
        try (var stream = Files.list(dir)) {
            return (int) stream.filter(file -> file.getFileName().toString().endsWith(".json")).count();
        } catch (Exception ignored) {
            return 0;
        }
    }

    // ── 数据清理：二次确认 + 明确范围 + 磁盘成功才报成功 ──

    public void confirmClearItems() {
        int count = countJsonFiles(IdentityService.shared().itemDirectory());
        showConfirm("清空全部物品 ID", List.of(
                "§c§l将删除 " + count + " 条物品 ID",
                "§7目录：AutoChest/items/",
                "§7不涉及实体 / 方块 / 快照 / 箱子配置 / 农场点位",
                "§c此操作不可撤销"), "§c§l确认清空", this::clearItems);
    }

    public void confirmClearEntities() {
        int count = countJsonFiles(IdentityService.shared().entityDirectory());
        showConfirm("清空全部实体 ID", List.of(
                "§c§l将删除 " + count + " 条实体 ID",
                "§7目录：AutoChest/entities/",
                "§7不涉及物品 / 方块 / 快照 / 箱子配置 / 农场点位",
                "§c此操作不可撤销"), "§c§l确认清空", this::clearEntities);
    }

    public void confirmClearBlocks() {
        int count = countJsonFiles(IdentityService.shared().blockDirectory());
        showConfirm("清空方块稳定记录", List.of(
                "§c§l将删除 " + count + " 条方块稳定记录",
                "§7目录：AutoChest/blocks/",
                "§7不包含历史状态快照（block-snapshots/）",
                "§c此操作不可撤销"), "§c§l确认清空", this::clearBlocks);
    }

    public void confirmClearSnapshots() {
        int count = countJsonFiles(IdentityService.shared().blockSnapshotDirectory());
        showConfirm("清空方块历史快照", List.of(
                "§c§l将删除 " + count + " 条方块历史状态快照",
                "§7目录：AutoChest/block-snapshots/",
                "§7不包含方块稳定记录（blocks/）",
                "§c此操作不可撤销"), "§c§l确认清空", this::clearSnapshots);
    }

    public void confirmClearAllBlocks() {
        IdentityService service = IdentityService.shared();
        int blocks = countJsonFiles(service.blockDirectory());
        int snapshots = countJsonFiles(service.blockSnapshotDirectory());
        showConfirm("清空全部方块数据", List.of(
                "§c§l将删除 " + blocks + " 条方块稳定记录 + " + snapshots + " 条历史快照",
                "§7目录：AutoChest/blocks/ 和 AutoChest/block-snapshots/",
                "§7不涉及物品 / 实体 / AutoChest 配置 / 农场点位",
                "§c此操作不可撤销"), "§c§l确认清空", this::clearAllBlocks);
    }

    /** 最高危险：清空全部 ID 数据，范围严格限定在四个身份目录。 */
    public void confirmClearAllIds() {
        IdentityService service = IdentityService.shared();
        int items = countJsonFiles(service.itemDirectory());
        int entities = countJsonFiles(service.entityDirectory());
        int blocks = countJsonFiles(service.blockDirectory());
        int snapshots = countJsonFiles(service.blockSnapshotDirectory());
        showConfirm("清空全部 ID 数据", List.of(
                "§c§l【最高危险】将清空全部 ID 数据",
                "§7物品 " + items + " 条 + 实体 " + entities + " 条 + 方块 " + blocks + " 条 + 快照 " + snapshots + " 条",
                "§7范围：AutoChest/items/ + entities/ + blocks/ + block-snapshots/",
                "§7不删除：AutoChest 配置/点位、AutoFarm、.fumo、资源包、Profile 等",
                "§c此操作不可撤销"), "§c§l确认全部清空", this::clearAllIds);
    }

    private void showConfirm(String title, List<String> lines, String confirmLabel, Runnable action) {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        Screen parent = client.gui.screen();
        client.execute(() -> client.gui.setScreen(new ConfirmPanelScreen(title, lines, confirmLabel, action, parent)));
    }

    // ── 说明面板 ──

    /** 构建独立说明窗口内容，避免长文本挤占管理清单的可视区域。文字与旧项目逐字一致。 */
    public String[] buildHelpContent() {
        return HelpPanelScreen.buildHelpContent(
                new HelpPanelScreen.HelpSection("使用方法",
                        "  §8├─ §f识别物品：读取主手物品，主手为空时读取副手并写入 ID 配置",
                        "  §8├─ §f手动添加：输入物品 ID，经过注册表验证后保存",
                        "  §8├─ §f识别准星方块：采集准星命中的真实方块并自动保存到方块记录",
                        "  §8├─ §f搜索：按中文名、技术 ID 或坐标过滤当前清单",
                        "  §8├─ §f筛选：按物品、实体、方块分类，以及方块已确认 / 未知状态筛选",
                        "  §8├─ §f删除：点击清单右侧的「-」移除单条记录",
                        "  §8└─ §f刷新：重新读取磁盘上的全部 ID 与方块快照数据"
                ),
                new HelpPanelScreen.HelpSection("数据链",
                        "  §8├─ §fID 识别 §8→ §fID 配置管理 §8→ §f自动箱子选择器",
                        "  §8└─ §f物品、实体、方块使用各自唯一管理器，修改后即时联动"
                ),
                new HelpPanelScreen.HelpSection("清空说明",
                        "  §c⚠ §f清空操作需要二次确认，且删除后不可撤销",
                        "  §8├─ §f清空物品 ID：仅清空 AutoChest/items/",
                        "  §8├─ §f清空实体 ID：仅清空 AutoChest/entities/",
                        "  §8├─ §f方块稳定记录：仅清空 AutoChest/blocks/",
                        "  §8├─ §f方块历史快照：仅清空 AutoChest/block-snapshots/",
                        "  §8├─ §f全部方块数据：同时清空方块稳定记录与历史快照",
                        "  §8└─ §f清空全部 ID 数据：物品 + 实体 + 方块稳定记录 + 历史快照，不涉及箱子配置、农场点位或其他模块"
                ),
                new HelpPanelScreen.HelpSection("注意事项",
                        "  §c⚠ §f未知自定义方块始终保留真实载体 ID，不会伪造中文语义",
                        "  §c⚠ §f打开目录会调用系统文件管理器，路径创建和启动操作在独立线程执行"
                )
        );
    }

    public void clearItems() {
        boolean ok = IdentityService.shared().clearItems();
        ClientChat.send(MESSAGE_MODULE, ok
                ? "§a§l✓ 已清空全部物品 ID（items/）"
                : "§c§l✗ 清空全部物品 ID 失败：磁盘删除失败，已回滚内存");
    }

    public void clearEntities() {
        boolean ok = IdentityService.shared().clearEntities();
        ClientChat.send(MESSAGE_MODULE, ok
                ? "§a§l✓ 已清空全部实体 ID（entities/）"
                : "§c§l✗ 清空全部实体 ID 失败：磁盘删除失败，已回滚内存");
    }

    public void clearBlocks() {
        boolean ok = IdentityService.shared().clearBlocks();
        ClientChat.send(MESSAGE_MODULE, ok
                ? "§a§l✓ 已清空方块稳定记录（blocks/）"
                : "§c§l✗ 清空方块稳定记录失败：部分文件删除失败，请检查 blocks/ 目录");
    }

    public void clearSnapshots() {
        boolean ok = IdentityService.shared().clearBlockSnapshots();
        ClientChat.send(MESSAGE_MODULE, ok
                ? "§a§l✓ 已清空方块历史快照（block-snapshots/）"
                : "§c§l✗ 清空方块历史快照失败：部分文件删除失败，请检查 block-snapshots/ 目录");
    }

    public void clearAllBlocks() {
        IdentityService service = IdentityService.shared();
        boolean okBlocks = service.clearBlocks();
        boolean okSnapshots = service.clearBlockSnapshots();
        ClientChat.send(MESSAGE_MODULE, okBlocks && okSnapshots
                ? "§a§l✓ 已清空全部方块数据（blocks/ + block-snapshots/）"
                : "§c§l✗ 清空全部方块数据时部分文件删除失败");
    }

    public void clearAllIds() {
        IdentityService service = IdentityService.shared();
        // 用非短路与保证四类都执行，避免中途失败导致部分目录残留
        boolean okItems = service.clearItems();
        boolean okEntities = service.clearEntities();
        boolean okBlocks = service.clearBlocks();
        boolean okSnapshots = service.clearBlockSnapshots();
        ClientChat.send(MESSAGE_MODULE, okItems && okEntities && okBlocks && okSnapshots
                ? "§a§l✓ 已清空全部 ID 数据（物品 + 实体 + 方块稳定记录 + 历史快照）"
                : "§c§l✗ 清空全部 ID 数据部分失败：请检查各目录磁盘状态");
    }

    /** 清理失效的识别目标 */
    public void pruneTargets() {
        int pruned = IdentityActions.pruneInvalidTargets();
        ClientChat.send(MESSAGE_MODULE, pruned == 0 ? "§7没有失效的识别目标" : "§7已清理 " + pruned + " 项失效的识别目标");
    }
}
