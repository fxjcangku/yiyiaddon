package com.yiyiaddon.feature.autochest.command;

import com.yiyiaddon.command.ClientCommand;
import com.yiyiaddon.command.CommandContext;
import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.autochest.AutoChestModule;
import com.yiyiaddon.model.autochest.ChestTarget;
import com.yiyiaddon.model.autochest.ContainerType;
import com.yiyiaddon.model.autochest.ContainerTypeRegistry;
import com.yiyiaddon.platform.world.WorldIdentity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;

/**
 * AutoChest 指令 {@code .autochest}：自动箱子标点管理。
 *
 * <p><b>用户交互资产（与旧项目逐字一致）：</b>指令名 {@code autochest}，别名无，TAB 补全无；
 * 裸 {@code .autochest} 输出「标点状态」（不是帮助）；子命令主名为中文
 * {@code 添加} / {@code 移除} / {@code 清空} / {@code 状态}，全部无参；
 * 全部回执走统一卡片排版。禁止改写这些文本或另造替代指令。</p>
 *
 * <p>标点模式专用：用户通过指令或面板按钮保存容器点位，AutoChest 只处理这些点位。
 * 设置点位会对准星指向的方块做「合法容器」校验，非容器拒绝保存。</p>
 *
 * <p><b>与本项目排版的差异</b>：本项目 {@link CommandMessageFormatter} 没有旧版的
 * {@code world()} / {@code dimension()} / {@code coord()} / {@code key()} 等 API，
 * 环境字段、维度字段、坐标字段按旧项目逐字格式在此手工拼接（分隔符仍为 {@code §8▸ }，
 * 标签仍由 {@code field()} 补齐）。</p>
 */
public final class AutoChestCommand extends ClientCommand {

    /** 回执前缀：旧项目 {@code AutoChestCommand.MODULE_NAME} 原文 */
    private static final String MODULE_NAME = AutoChestModule.MESSAGE_MODULE;

    /** 子命令：旧项目用 Brigadier literal 注册，literal 本身即候选 */
    private static final List<String> SUBCOMMANDS = List.of("添加", "移除", "清空", "状态");

    private final Minecraft mc = Minecraft.getInstance();

    @Override
    public String name() {
        return "autochest";
    }

    @Override
    public String prefixName() {
        return MODULE_NAME;
    }

    @Override
    public String description() {
        return "自动箱子标点管理（添加/移除/清空/状态）";
    }

    @Override
    public void execute(CommandContext context) {
        // 根节点无参直接执行 showStatus（旧项目 Brigadier 根节点 executes 指向 showStatus）
        if (context.isEmpty()) {
            showStatus();
            return;
        }
        switch (context.arg(0)) {
            case "添加" -> addPoint();
            case "移除" -> removePoint();
            case "清空" -> clearPoints();
            case "状态" -> showStatus();
            default -> {
                context.error("未知子命令：" + context.arg(0));
                context.usage(usage());
            }
        }
    }

    /**
     * 子命令补全。
     *
     * <p>旧项目这四个子命令是 Brigadier literal，literal 自身就是候选（旧项目 {@code .autochest }
     * 按 Tab 同样列出这四个词）；旧项目只是没给参数挂 {@code .suggests}，所以除这四个词外没有别的
     * 候选。本方法即该事实的对应实现。</p>
     */
    @Override
    public List<String> complete(CommandContext context) {
        return context.isEmpty() ? SUBCOMMANDS : List.of();
    }

    // ── 子命令实现 ──

    /** 设置点位：校验准星指向方块为合法容器后保存（服务器/世界 + 维度 + 坐标 + 类型） */
    private void addPoint() {
        AutoChestModule module = module();
        if (module == null || mc.level == null) {
            fail("添加标点失败", "自动箱子模块未加载");
            return;
        }
        BlockPos target = getTargetBlock();
        if (target == null) {
            fail("添加标点失败", "准星未对准任何方块");
            return;
        }

        // 判断目标是否为启用的合法容器类型，非容器禁止保存
        ContainerType type = containerTypeAt(target, module);
        if (type == null) {
            fail("添加标点失败", "当前目标不是可绑定容器");
            return;
        }

        String dim = WorldIdentity.dimension();
        if (module.pointStore().add(target, dim, type.id())) {
            CommandMessageFormatter formatter = CommandMessageFormatter
                    .of(MODULE_NAME, "已设置" + type.displayName());
            appendWorld(formatter);
            formatter.field("坐标", AutoChestModule.formatCoords(target.getX(), target.getY(), target.getZ()))
                    .field("类型", "§f" + type.displayName())
                    .status(CommandMessageFormatter.Level.SUCCESS, "已保存")
                    .send();
        } else {
            fail("添加标点失败", "该坐标已存在标点");
        }
    }

    /** 删除点位：内存与磁盘同步 */
    private void removePoint() {
        AutoChestModule module = module();
        if (module == null || mc.level == null) {
            fail("删除标点失败", "自动箱子模块未加载");
            return;
        }
        BlockPos target = getTargetBlock();
        if (target == null) {
            fail("删除标点失败", "准星未对准任何方块");
            return;
        }
        String dim = WorldIdentity.dimension();
        if (module.pointStore().remove(target, dim)) {
            CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, "已删除标点");
            appendWorld(formatter);
            formatter.field("坐标", AutoChestModule.formatCoords(target.getX(), target.getY(), target.getZ()))
                    .status(CommandMessageFormatter.Level.SUCCESS, "已删除")
                    .send();
        } else {
            fail("删除标点失败", "该坐标没有标点");
        }
    }

    /** 清空全部标点 */
    private void clearPoints() {
        AutoChestModule module = module();
        if (module == null) {
            fail("清空标点失败", "自动箱子模块未加载");
            return;
        }
        int count = module.pointStore().size();
        module.pointStore().clear();
        CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, "已清空全部标点");
        appendWorld(formatter);
        formatter.field("数量", "§f" + count + " 个")
                .status(CommandMessageFormatter.Level.SUCCESS, "已清空")
                .send();
    }

    /** 查看当前维度点位信息：服务器/世界、维度、坐标、容器类型、处理状态 */
    private void showStatus() {
        AutoChestModule module = module();
        if (module == null || mc.level == null) {
            fail("标点状态", "自动箱子模块未加载");
            return;
        }

        CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, "标点状态");
        appendWorld(formatter);

        List<ChestTarget> points = module.pointStore().pointsInCurrentDimension();
        if (points.isEmpty()) {
            formatter.field("标点", "§f当前维度没有标点");
        } else {
            long expireMs = module.settings().recordExpireMinutes * 60_000L;
            int index = 1;
            for (ChestTarget point : points) {
                String typeName = containerTypeName(point.containerType());
                boolean processed = module.recordStore().isProcessed(
                        point.pos(), point.dimension(), point.containerType(), expireMs);
                String status = processed ? "§c已处理" : "§a未处理";
                formatter.field("标点 " + index,
                        "§f" + typeName + " §8· " + AutoChestModule.formatCoords(
                                point.pos().getX(), point.pos().getY(), point.pos().getZ())
                                + " §8· " + status);
                index++;
            }
        }
        formatter.status(CommandMessageFormatter.Level.INFO, "共 " + points.size() + " 个标点");
        formatter.send();
    }

    // ── 环境字段（旧项目 {@code world()} + {@code dimension()} 的逐字复刻） ──

    /**
     * 追加「世界 / 服务器 / 存档 + 维度」字段。
     *
     * <p>旧项目由 {@code WorldContextFormatter.appendTo} 产出，本项目缺失该 API，故在此按同一
     * 逐字格式与同一取值口径手工拼接。</p>
     */
    private void appendWorld(CommandMessageFormatter formatter) {
        if (mc.level == null) {
            formatter.field("世界", "未进入世界").field("服务器", "未连接");
        } else if (mc.hasSingleplayerServer()) {
            formatter.field("世界", "单人世界").field("存档", singleplayerWorldName());
        } else {
            formatter.field("服务器名称", "§b" + serverName())
                    .field("服务器地址", "§b" + serverAddress());
        }
        formatter.field("维度", "§f" + WorldIdentity.dimensionDisplayName(WorldIdentity.dimension()));
    }

    /** 展示名称取服务器列表；缺失回落旧项目文案 */
    private String serverName() {
        ServerData data = mc.getCurrentServer();
        return data == null || data.name == null || data.name.isBlank() ? "未命名服务器" : data.name;
    }

    /** 规范化地址（本项目唯一服务器键口径）；缺失回落旧项目文案 */
    private static String serverAddress() {
        String key = WorldIdentity.server();
        return key == null || key.isBlank() ? "暂不可用" : key;
    }

    /** 单人存档名；缺失回落旧项目文案 */
    private String singleplayerWorldName() {
        if (mc.getSingleplayerServer() == null) return "未进入存档";
        String name = WorldIdentity.singleplayerWorldName();
        return name == null || name.isBlank() ? "存档名称暂不可用" : name;
    }

    // ── 解析 ──

    /** 查找对准方块的启用容器类型，非容器返回 null */
    private ContainerType containerTypeAt(BlockPos pos, AutoChestModule module) {
        if (mc.level == null) return null;
        Block block = mc.level.getBlockState(pos).getBlock();
        return ContainerTypeRegistry.match(block, module.settings().enabledTypes());
    }

    /** 容器类型中文名，未知类型回退为原始键 */
    private String containerTypeName(String typeId) {
        if (typeId == null) return "未知容器";
        ContainerType type = ContainerTypeRegistry.byId(typeId);
        return type == null ? typeId : type.displayName();
    }

    private BlockPos getTargetBlock() {
        HitResult hit = mc.hitResult;
        if (hit == null || hit.getType() != HitResult.Type.BLOCK) return null;
        if (!(hit instanceof BlockHitResult blockHit)) return null;
        return blockHit.getBlockPos().immutable();
    }

    private AutoChestModule module() {
        return ModuleManager.byId(AutoChestModule.MODULE_ID) instanceof AutoChestModule autoChest ? autoChest : null;
    }

    private void fail(String title, String reason) {
        CommandMessageFormatter.of(MODULE_NAME, title)
                .field("原因", "§f" + reason)
                .status(CommandMessageFormatter.Level.FAILURE, "操作未完成")
                .send();
    }
}
