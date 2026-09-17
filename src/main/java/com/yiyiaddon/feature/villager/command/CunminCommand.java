package com.yiyiaddon.feature.villager.command;

import com.yiyiaddon.command.ClientCommand;
import com.yiyiaddon.command.CommandContext;
import com.yiyiaddon.command.CommandManager;
import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.feature.villager.model.VillagerBinding;
import com.yiyiaddon.feature.villager.repository.VillagerBindingStore;
import com.yiyiaddon.platform.world.PlayerText;
import com.yiyiaddon.platform.world.WorldContextFormatter;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.Container;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;

/**
 * Cunmin 指令 - 自动村民交易点位管理
 *
 * <p><b>功能（旧项目注释保留）：</b></p>
 * <pre>
 * · .cunmin 设置 绿宝石箱   准星指向箱子绑定
 * · .cunmin 设置 成品交易箱     准星指向箱子绑定
 * · .cunmin 移除 &lt;目标&gt;  删除绑定
 * · .cunmin 清空          清空全部
 * · .cunmin 状态         查看状态
 * </pre>
 *
 * <p><b>用户交互资产（与旧项目逐字一致，禁止改写）：</b>指令名 {@code cunmin}、<b>无别名</b>、描述
 * {@code 村民交易点位绑定（绿宝石箱、成品交易箱）}（旧 {@code :48-50}）；子命令字面量
 * {@code 状态} / {@code 设置} / {@code 移除} / {@code 清空} 与目标字面量
 * {@code 绿宝石箱} / {@code 成品交易箱}（旧 {@code :52-86}，中文子命令直接用中文字面量，不改英文）；
 * 裸 {@code .cunmin} 与 {@code .cunmin 状态} 都输出「坐标绑定状态」（旧 {@code :55-63}）；全部回执
 * 走统一排版器 {@link CommandMessageFormatter}（旧 {@code core/CommandMessageFormatter}）。</p>
 *
 * <p><b>绑定 / 移除两条链路各自保留旧文案，禁止合并：</b>指令路径（{@code .cunmin 设置|移除}）走结构化
 * 回执（旧 {@code :135-172}）；配置页「设置 / 删除」按钮走 {@link #setBinding(String)} /
 * {@link #removeBinding(String)}，失败文案多出「请重新设置」、解绑成功走单行
 * {@code §c§l✗ 已删除 X 绑定}（旧 {@code :496-540}）。</p>
 *
 * <p><b>数据与读盘全部在仓库层：</b>键名（{@code emerald_chest} / {@code unload_chest}）、按服务器分文件、
 * 维度三值归一、读盘时机与写盘事务都由 {@link VillagerBindingStore} 负责（旧 {@code CunminData} +
 * {@code loadData/saveData :259-347} 的等价物，第 45 / 48 条）；本类只做校验与播报，不复制任何一条读写
 * 逻辑。落盘失败由仓库返回 {@code false}，本类按旧文案 {@code 保存失败：…} 播报（登记 D-14-08）。</p>
 *
 * <p><b>持久化落点已按第 45 条迁移：</b>旧注释写的是
 * {@code .minecraft/config/yiyiaddon/cunmin/<服务器>.json}，新项目由 {@link VillagerBindingStore}
 * 落在 {@code config/yiyiaddon/villager/bindings/<WorldIdentity.fileSafeServer()>.json}，旧文件在目标
 * 不存在时按旧键名复制过来（只复制不移动，见 D-14-01）；键名不变，旧档零迁移可读回。</p>
 *
 * <p><b>前缀唯一来源（第 94 条）：</b>文案里出现的 {@code .cunmin} 片段一律由
 * {@link CommandManager#prefix()} 拼接，禁止硬编码前缀字符；默认前缀 {@code .} 下输出与旧项目一致。</p>
 */
public final class CunminCommand extends ClientCommand {

    /** 播报使用的模块名（旧 {@code :39} 原文，回执前缀由排版器按第 110-113 条生成） */
    private static final String MODULE_NAME = "自动村民交易";

    /** 绿宝石箱中文名（旧 {@code :68} 等处的字面量，属用户交互资产） */
    private static final String EMERALD_NAME = "绿宝石箱";

    /** 成品交易箱中文名（旧 {@code :69} 等处的字面量，属用户交互资产） */
    private static final String UNLOAD_NAME = "成品交易箱";

    /** 根节点子命令（旧 {@code :60-85} 的 literal，顺序原样） */
    private static final List<String> SUBCOMMANDS = List.of("状态", "设置", "移除", "清空");

    /** {@code 设置} / {@code 移除} 下的目标字面量（旧 {@code :68-69}、{@code :76-77}） */
    private static final List<String> TARGET_NAMES = List.of(EMERALD_NAME, UNLOAD_NAME);

    private final Minecraft mc = Minecraft.getInstance();

    @Override
    public String name() {
        return "cunmin";
    }

    @Override
    public String prefixName() {
        return MODULE_NAME;
    }

    @Override
    public String description() {
        return "村民交易点位绑定（绿宝石箱、成品交易箱）";
    }

    @Override
    public void execute(CommandContext context) {
        // 根节点可直接执行：旧 Brigadier 根节点 executes 指向 showStatus（旧 :55-58）
        if (context.isEmpty()) {
            showStatus();
            return;
        }
        switch (context.arg(0)) {
            case "状态" -> showStatus();
            case "设置" -> bindTarget(context);
            case "移除" -> removeTarget(context);
            case "清空" -> clearAllBindings();
            default -> {
                // 旧项目是 Brigadier 解析失败，没有对应文案；沿用本项目其它指令的未知子命令提示
                context.error("未知子命令：" + context.arg(0));
                context.usage(usage());
            }
        }
    }

    /**
     * 参数补全。
     *
     * <p>旧项目全部是 Brigadier literal（无 {@code .suggests}），literal 自身即候选：根节点列
     * {@code 状态 / 设置 / 移除 / 清空}，{@code 设置} 与 {@code 移除} 下列两个目标名。本项目补全属
     * 框架能力（{@link ClientCommand#complete}），候选只列旧项目真实存在的字面量；内部数据键
     * {@code emerald_chest / unload_chest} 不参与补全（旧项目亦然）。</p>
     */
    @Override
    public List<String> complete(CommandContext context) {
        if (context.isEmpty()) return SUBCOMMANDS;
        if (context.size() == 1 && ("设置".equals(context.arg(0)) || "移除".equals(context.arg(0)))) {
            return TARGET_NAMES;
        }
        return List.of();
    }

    // ── 子命令分发（旧 :65-86） ──

    /** {@code .cunmin 设置 <目标>}：校验字面量后走对应的绑定实现 */
    private void bindTarget(CommandContext context) {
        String target = targetOf(context);
        if (target == null) return;
        if (EMERALD_NAME.equals(target)) bindEmeraldChest();
        else bindUnloadChest();
    }

    /** {@code .cunmin 移除 <目标>}：校验字面量后走同一条删除实现 */
    private void removeTarget(CommandContext context) {
        String target = targetOf(context);
        if (target == null) return;
        removeBindingCommand(target);
    }

    // ── 绑定（旧 :88-144） ──

    /**
     * 绑定绿宝石箱
     */
    private void bindEmeraldChest() {
        bindContainer(EMERALD_NAME, VillagerBindingStore.KEY_EMERALD_CHEST);
    }

    /**
     * 绑定成品交易箱
     */
    private void bindUnloadChest() {
        bindContainer(UNLOAD_NAME, VillagerBindingStore.KEY_UNLOAD_CHEST);
    }

    /**
     * 通用容器绑定逻辑
     *
     * <p>校验顺序与旧项目一致：玩家 / 世界可用 → 准星命中方块 → 目标是实现了 {@link Container} 的方块
     * 实体；任一条不通过即按旧文案报错并返回，不写内存也不落盘。通过后写入仓库、落盘，再输出结构化回执
     * （字段 {@code 类型}，状态 {@code 已保存}）。</p>
     */
    private void bindContainer(String displayName, String key) {
        if (mc.player == null || mc.level == null) {
            cunminError("§c玩家或世界无效");
            return;
        }

        // 获取准星指向方块
        HitResult hit = mc.hitResult;
        if (hit == null || hit.getType() != HitResult.Type.BLOCK) {
            cunminError("§c请将准星对准容器方块");
            return;
        }

        BlockPos pos = ((BlockHitResult) hit).getBlockPos();
        BlockEntity blockEntity = mc.level.getBlockEntity(pos);

        // 验证是否为有效容器
        if (!(blockEntity instanceof Container)) {
            cunminError("§c目标方块不是有效的容器（箱子/木桶/潜影盒）");
            return;
        }

        // 保存绑定
        ResourceKey<Level> dimension = mc.level.dimension();

        VillagerBindingStore.setBinding(key, pos, dimension);
        saveData();

        // 成功提示
        CommandMessageFormatter.of(MODULE_NAME, "已设置" + displayName)
            .world()
            .dimension(dimension == null ? null : dimension.identifier().toString())
            .coord(pos.getX(), pos.getY(), pos.getZ())
            .field("类型", displayName)
            .status(CommandMessageFormatter.Level.SUCCESS, "已保存")
            .send();
    }

    // ── 删除（旧 :146-172） ──

    /**
     * 删除绑定（私有方法，给指令用）
     *
     * <p>未绑定时播报旧原文 {@code <名> 未绑定}；已绑定时先取快照（坐标 / 维度）再删除、落盘，最后按旧
     * 字段顺序（维度 → 类型 → 坐标）输出回执，状态 {@code 已删除}。</p>
     */
    private void removeBindingCommand(String displayName) {
        String key = keyOf(displayName);

        VillagerBinding removed = VillagerBindingStore.binding(key);
        if (removed == null) {
            cunminError(String.format("%s 未绑定", displayName));
            return;
        }

        BlockPos pos = removed.pos();
        ResourceKey<Level> dimension = removed.dimensionKey();

        VillagerBindingStore.removeBinding(key);
        saveData();

        CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, "已删除" + displayName)
            .world()
            .dimension(dimension == null ? null : dimension.identifier().toString())
            .field("类型", displayName);
        if (pos != null) formatter.coord(pos.getX(), pos.getY(), pos.getZ());
        formatter.status(CommandMessageFormatter.Level.SUCCESS, "已删除");
        formatter.send();
    }

    /**
     * 清空全部绑定
     *
     * <p>旧实现不判空：一个绑定都没有时同样给出 {@code 已清空全部绑定} 回执，此处保持原行为
     * （不套用 {@code .wk} 的「当前没有任何绑定」提前返回）。</p>
     */
    private void clearAllBindings() {
        VillagerBindingStore.clear();
        saveData();

        CommandMessageFormatter.of(MODULE_NAME, "已清空全部绑定")
            .world()
            .field("范围", EMERALD_NAME + " + " + UNLOAD_NAME)
            .status(CommandMessageFormatter.Level.SUCCESS, "已清空")
            .send();
    }

    // ── 状态显示（旧 :189-232） ──

    /**
     * 显示当前绑定状态
     */
    private void showStatus() {
        CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, "坐标绑定状态")
            .world();

        appendBindingStatus(formatter, EMERALD_NAME, VillagerBindingStore.KEY_EMERALD_CHEST);
        appendBindingStatus(formatter, UNLOAD_NAME, VillagerBindingStore.KEY_UNLOAD_CHEST);

        if (VillagerBindingStore.hasAnyBinding()) {
            formatter.status(CommandMessageFormatter.Level.INFO, "已绑定");
        } else {
            // 提示里的 `.cunmin` 由前缀唯一来源拼出（第 94 条），改前缀后文案仍自洽
            formatter.status(CommandMessageFormatter.Level.INFO,
                "暂无绑定点位，使用 " + CommandManager.prefix() + "cunmin 设置 <目标> 进行绑定");
        }
        formatter.send();
    }

    /**
     * 追加单个绑定状态到播报块
     *
     * <p>未绑定占位 {@code §8未绑定}；已绑定为
     * {@code §7X§f{x} §7Y§f{y} §7Z§f{z} §8▸ <维度显示名>}，维度显示名取
     * {@link WorldContextFormatter#dimensionSummary(String)}（旧 {@code :230-232} 的口径）。</p>
     */
    private void appendBindingStatus(CommandMessageFormatter formatter, String displayName, String key) {
        VillagerBinding binding = VillagerBindingStore.binding(key);
        if (binding == null) {
            formatter.field(displayName, "§8未绑定");
            return;
        }
        BlockPos pos = binding.pos();
        ResourceKey<Level> dimension = binding.dimensionKey();
        String value = pos == null
            ? getDimensionName(dimension)
            : "§7X§f" + pos.getX() + " §7Y§f" + pos.getY() + " §7Z§f" + pos.getZ()
                + " §8▸ " + getDimensionName(dimension);
        formatter.field(displayName, value);
    }

    /**
     * 获取维度显示名称
     */
    private static String getDimensionName(ResourceKey<Level> dimension) {
        return WorldContextFormatter.dimensionSummary(dimension == null ? null : dimension.identifier().toString());
    }

    // ── 落盘与提示行（旧 :312-361） ──

    /**
     * 落盘并把失败播报给玩家
     *
     * <p>旧实现在 {@code catch (IOException e)} 里播报 {@code 保存失败：<PlayerText.failure(e)>}；新仓库
     * 按第 48 条把写盘结果收敛成 {@code false}
     * （{@link com.yiyiaddon.repository.JsonFileStore#writeAtomic} 内部吞掉异常、不保留原因），因此这里保留
     * 旧文案前缀 {@code 保存失败：}，原因交给 {@link PlayerText#failure} 的通用兜底，不臆造技术细节。</p>
     */
    private static void saveData() {
        if (VillagerBindingStore.save()) return;
        cunminError("保存失败：" + PlayerText.failure(new IllegalStateException()));
    }

    /**
     * 输出消息（普通提示行，统一走 CommandMessageFormatter）
     */
    private static void cunminInfo(String message) {
        CommandMessageFormatter.sendLine(MODULE_NAME, message);
    }

    /**
     * 输出错误（统一橙黄 / 亮红配色）
     */
    private static void cunminError(String message) {
        CommandMessageFormatter.sendLine(MODULE_NAME, "§6" + message);
    }

    // ═══════════════════════════════════════════════════════════════════
    //  供模块配置页面的按钮调用（旧 :492-540 原样）
    // ═══════════════════════════════════════════════════════════════════

    /**
     * 设置绑定（供模块按钮调用）
     *
     * <p>GUI 路径的两句失败文案带「请重新设置」，与指令路径并存不合并（旧项目现状，旧 {@code :505-515}）；
     * 容器判定与写入仍走本类同一条 {@link #bindContainer}，不复制第二份判断。</p>
     *
     * @param key {@code emerald_chest} 或 {@code unload_chest}（见 {@link VillagerBindingStore} 键名常量）
     * @return true = 设置成功，false = 设置失败
     */
    public static boolean setBinding(String key) {
        CunminCommand command = new CunminCommand();

        // 检查准星是否对准方块
        BlockPos target = command.getTargetBlock();
        if (target == null) {
            cunminError("§c准星未对准任何方块，请重新设置");
            return false;
        }

        // 检查是否为容器
        if (!command.isContainer(target)) {
            cunminError("§c目标方块不是容器（箱子/桶/潜影盒等），请重新设置");
            return false;
        }

        // 调用绑定逻辑
        command.bindContainer(displayNameOf(key), key);
        return true;
    }

    /**
     * 删除绑定（供模块按钮调用）
     *
     * <p>删除成功走信息行 {@code §c§l✗ 已删除 X 绑定}（不是排版器块），无绑定走错误行；两句都是旧项目
     * 原文（旧 {@code :527-540}），与指令路径的结构化回执并存，禁止统一成一套。</p>
     *
     * @param key {@code emerald_chest} 或 {@code unload_chest}
     */
    public static void removeBinding(String key) {
        if (VillagerBindingStore.hasBinding(key)) {
            VillagerBindingStore.removeBinding(key);
            saveData();

            cunminInfo("§c§l✗ 已删除 " + displayNameOf(key) + " 绑定");
        } else {
            cunminError("§c该坐标本来就没有绑定");
        }
    }

    // ── 取准星与容器判定（旧 :542-559 原样） ──

    /**
     * 获取准星对准的方块（内部方法）
     */
    private BlockPos getTargetBlock() {
        if (mc.hitResult == null || mc.hitResult.getType() != HitResult.Type.BLOCK) {
            return null;
        }
        return ((BlockHitResult) mc.hitResult).getBlockPos();
    }

    /**
     * 检查方块是否为容器（内部方法）
     */
    private boolean isContainer(BlockPos pos) {
        if (mc.level == null) return false;
        BlockEntity blockEntity = mc.level.getBlockEntity(pos);
        return blockEntity instanceof Container;
    }

    // ── 解析辅助 ──

    /**
     * 取 {@code 设置} / {@code 移除} 的目标字面量并校验。
     *
     * <p>旧项目这两个位置是 Brigadier literal，只接受 {@code 绿宝石箱} / {@code 成品交易箱}；参数缺失或
     * 字面量非法在旧项目表现为解析失败（无对应文案），此处沿用本项目 {@code .wk} / {@code .farm} 同款用法提示。</p>
     *
     * @return 合法目标中文名；非法返回 {@code null}
     */
    private String targetOf(CommandContext context) {
        String input = context.arg(1);
        if (input == null) {
            context.usage(usage());
            return null;
        }
        if (TARGET_NAMES.contains(input)) return input;
        context.error("未知目标：" + input);
        context.usage(usage());
        return null;
    }

    /** 目标中文名 → 数据键（旧 {@code :150} 的三元表达式原样：非绿宝石箱即成品交易箱） */
    private static String keyOf(String displayName) {
        return EMERALD_NAME.equals(displayName)
            ? VillagerBindingStore.KEY_EMERALD_CHEST
            : VillagerBindingStore.KEY_UNLOAD_CHEST;
    }

    /** 数据键 → 目标中文名（旧 {@code :518} / {@code :535} 的三元表达式原样：非绿宝石箱键即成品交易箱） */
    private static String displayNameOf(String key) {
        return VillagerBindingStore.KEY_EMERALD_CHEST.equals(key) ? EMERALD_NAME : UNLOAD_NAME;
    }
}
