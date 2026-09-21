package com.yiyiaddon.feature.autofarm.command;

import com.yiyiaddon.command.ClientCommand;
import com.yiyiaddon.command.CommandContext;
import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.core.module.ModuleManager;
import com.yiyiaddon.feature.autofarm.AutoFarmModule;
import com.yiyiaddon.feature.autofarm.model.FarmSite;
import com.yiyiaddon.feature.autofarm.model.SiteType;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import java.util.List;

/**
 * 自动农场锚点绑定指令 {@code .farm}（别名 {@code nongchang}）。
 *
 * <p><b>用户交互资产（与旧项目逐字一致，禁止改写）：</b>指令名 {@code farm}、别名
 * {@code nongchang}、描述 {@code 绑定自动农场的农田范围与物流箱子。}（旧 {@code NongChangCommand:42}）、
 * 子命令字面量 {@code 设置 / 移除 / 状态 / 清空 / 扩展} 与六个锚点中文名
 * （旧 {@code :50-66}）、全部成功与失败回执（旧 {@code :69-252} 逐字表见 50 号第四节）；
 * 回执排版走 {@link CommandMessageFormatter}。{@code 扩展} 参数域 1~128（旧 Brigadier
 * {@code integer(1,128)}）。</p>
 *
 * <p><b>静态工具：</b>模块页六卡片的「删除」按钮调用 {@link #removeBinding(SiteType)}
 * （旧 {@code :304-349} 的删除分支，文案逐字保留）。「设置」不再是「准星 + 按钮」——
 * 用户 2026-09-18 起改为游戏内左右键点选
 * （{@link com.yiyiaddon.feature.autofarm.region.FarmSiteSelector}），旧项目那条 GUI 设置文案
 * （{@code 请重新设置}）随旧交互一并退役；本文件的指令路径 {@code .farm 设置} <b>保持原样</b>
 * （照旧按准星绑定，文案与失败原因不变）。</p>
 */
public final class FarmCommand extends ClientCommand {

    /** 回执前缀：旧 {@code NongChangCommand.MODULE_NAME} 原文 */
    private static final String MODULE_NAME = AutoFarmModule.MESSAGE_MODULE;

    /** 根节点子命令（旧 {@code :60-62} 的 literal，顺序原样；设置 / 移除各自携带锚点参数） */
    private static final List<String> SUBCOMMANDS = List.of("设置", "移除", "状态", "清空", "扩展");

    private final Minecraft mc = Minecraft.getInstance();

    @Override
    public String name() {
        return "farm";
    }

    @Override
    public List<String> aliases() {
        // 旧 :42 的第三个参数（别名 nongchang），D5 拍板保留
        return List.of("nongchang");
    }

    @Override
    public String prefixName() {
        return MODULE_NAME;
    }

    @Override
    public String description() {
        return "绑定自动农场的农田范围与物流箱子。";
    }

    @Override
    public void execute(CommandContext context) {
        // 指令没有「打开界面」这种天然重读时机：先按当前服务器装载锚点与设置，
        // 否则换服后状态页显示上一服的锚点、覆盖保护也会拿着上一服的绑定拦人
        AutoFarmModule current = module();
        if (current != null) current.refreshScopedSettings();

        // 裸指令：打印绑定状态（旧 :48 executes → showStatus）
        if (context.isEmpty()) {
            showStatus();
            return;
        }
        switch (context.arg(0)) {
            case "设置" -> {
                SiteType type = siteTypeOf(context);
                if (type != null) bind(type);
            }
            case "移除" -> {
                SiteType type = siteTypeOf(context);
                if (type != null) unbind(type);
            }
            case "状态" -> showStatus();
            case "清空" -> clearAll();
            case "扩展" -> expand(context);
            default -> {
                // 旧项目是 Brigadier 解析失败，没有对应文案；沿用本项目未知子命令提示
                context.error("未知子命令：" + context.arg(0));
                context.usage(usage());
            }
        }
    }

    /** 参数补全：根节点列五个子命令；设置 / 移除 下列六个锚点中文名（旧 Brigadier literal 即候选） */
    @Override
    public List<String> complete(CommandContext context) {
        if (context.isEmpty()) return SUBCOMMANDS;
        if (context.size() == 1 && ("设置".equals(context.arg(0)) || "移除".equals(context.arg(0)))) {
            return siteNames();
        }
        return List.of();
    }

    private static List<String> siteNames() {
        return List.of(SiteType.values()).stream().map(SiteType::cn).toList();
    }

    // ── 状态（旧 showStatus :69-89） ──

    /** 裸指令 / {@code 状态}：标题 + 六条锚点字段 + 绑定计数 */
    private void showStatus() {
        AutoFarmModule module = module();
        if (module == null) return;

        CommandMessageFormatter formatter = CommandMessageFormatter.of(MODULE_NAME, "锚点绑定状态")
            .world();

        int bound = 0;
        for (SiteType type : SiteType.values()) {
            FarmSite site = module.site(type);
            if (site == null) {
                formatter.field(type.cn(), "§8未绑定");
            } else {
                bound++;
                formatter.field(type.cn(), site.describe("§b"));
            }
        }
        formatter.status(CommandMessageFormatter.Level.INFO, bound + " / " + SiteType.values().length + " 已绑定");
        formatter.send();
    }

    // ── 清空（旧 clearAll :91-113） ──

    private void clearAll() {
        AutoFarmModule module = module();
        if (module == null) return;

        int bound = 0;
        for (SiteType type : SiteType.values()) {
            if (module.site(type) != null) bound++;
        }

        if (bound == 0) {
            farmError("六个锚点本来就都没有绑定。");
            return;
        }

        module.clearAllSites();
        CommandMessageFormatter.of(MODULE_NAME, "已清空全部锚点")
            .world()
            .field("数量", bound + " 个")
            .field("提示", "农田范围同时重置")
            .status(CommandMessageFormatter.Level.SUCCESS, "已清空")
            .send();
    }

    // ── 绑定（旧 bind :115-163） ──

    private void bind(SiteType type) {
        AutoFarmModule module = module();
        if (module == null) return;

        if (mc.level == null) {
            farmError("当前不在游戏世界中，无法绑定锚点");
            return;
        }

        if (module.isEnabled()) {
            farmError("模块运行中无法修改锚点，请先关闭模块");
            return;
        }

        // 覆盖保护
        if (module.site(type) != null) {
            farmError(type.cn() + "已绑定，请先删除旧绑定再重新设置");
            farmInfo("§7提示：使用 §e.farm 移除 " + type.cn() + " §7删除");
            return;
        }

        BlockPos target = targetBlock();
        if (target == null) {
            farmError("准星未对准任何方块，请将准星对准要绑定的方块");
            return;
        }

        if (type.requiresContainer() && !isContainer(target)) {
            farmError("该锚点需要指向容器方块（箱子/桶/潜影盒等），但准星对准的不是容器");
            return;
        }

        FarmSite site = FarmSite.here(target);
        if (site == null) {
            farmError("无法获取当前维度信息");
            return;
        }

        module.bindSite(type, site);

        CommandMessageFormatter.of(MODULE_NAME, "已绑定" + type.cn())
            .world()
            .coord(site.pos().getX(), site.pos().getY(), site.pos().getZ())
            .field("维度", site.dimension().identifier().toString())
            .field("类型", type.cn())
            .status(CommandMessageFormatter.Level.SUCCESS, "已保存")
            .send();
    }

    // ── 解绑（旧 unbind :165-184） ──

    private void unbind(SiteType type) {
        AutoFarmModule module = module();
        if (module == null) return;

        FarmSite site = module.site(type);
        if (site == null) {
            farmError(type.cn() + "本来就没有绑定。");
            return;
        }

        module.clearSite(type);
        CommandMessageFormatter.of(MODULE_NAME, "已解绑" + type.cn())
            .world()
            .coord(site.pos().getX(), site.pos().getY(), site.pos().getZ())
            .field("维度", site.dimension().identifier().toString())
            .field("类型", type.cn())
            .status(CommandMessageFormatter.Level.SUCCESS, "已删除")
            .send();
    }

    // ── 扩展（旧 expandFarm :186-268） ──

    /** 面朝方向把农田向外扩展 n 格（水平方向，仅改 X/Z，Y 保持锚点原值） */
    private void expand(CommandContext context) {
        AutoFarmModule module = module();
        if (module == null) return;

        Integer n = context.intArg(1);
        if (n == null || n < 1 || n > 128) {
            // 旧 Brigadier integer(1,128) 在解析层拒绝；此处给出同域提示
            context.usage(usage() + " 扩展 <格数 1~128>");
            return;
        }

        if (mc.player == null || mc.level == null) {
            farmError("当前不在游戏世界中，无法扩展农田");
            return;
        }

        if (module.isEnabled()) {
            farmError("模块运行中无法修改农田范围，请先关闭模块");
            return;
        }

        FarmSite start = module.site(SiteType.START);
        FarmSite end = module.site(SiteType.END);
        if (start == null || end == null) {
            farmError("农田范围未绑定完整，请先用 .farm 设置 农场点位1 / 农场点位2 框出范围");
            return;
        }

        if (!start.inCurrentDimension() || !end.inCurrentDimension()) {
            farmError("农田锚点不在当前维度，无法按面朝方向扩展");
            return;
        }

        Facing facing = facingOf(mc.player.getYRot());
        BlockPos startPos = start.pos();
        BlockPos endPos = end.pos();
        BlockPos newStart = startPos;
        BlockPos newEnd = endPos;

        // 面朝方向为正时，移动该方向坐标更大的一角；为负时移动更小的一角；
        // 两角同值时移动点位2，保证即使一宽农田也能正确扩展。
        if (facing.stepX() > 0) {
            if (startPos.getX() >= endPos.getX()) newStart = shifted(startPos, n, 0, 0);
            else newEnd = shifted(endPos, n, 0, 0);
        } else if (facing.stepX() < 0) {
            if (startPos.getX() <= endPos.getX()) newStart = shifted(startPos, -n, 0, 0);
            else newEnd = shifted(endPos, -n, 0, 0);
        } else if (facing.stepZ() > 0) {
            if (startPos.getZ() >= endPos.getZ()) newStart = shifted(startPos, 0, 0, n);
            else newEnd = shifted(endPos, 0, 0, n);
        } else {
            if (startPos.getZ() <= endPos.getZ()) newStart = shifted(startPos, 0, 0, -n);
            else newEnd = shifted(endPos, 0, 0, -n);
        }

        module.bindSite(SiteType.START, new FarmSite(newStart, start.dimension()));
        module.bindSite(SiteType.END, new FarmSite(newEnd, end.dimension()));

        FarmSite ns = module.site(SiteType.START);
        FarmSite ne = module.site(SiteType.END);
        int rangeX = Math.abs(ne.pos().getX() - ns.pos().getX()) + 1;
        int rangeZ = Math.abs(ne.pos().getZ() - ns.pos().getZ()) + 1;

        CommandMessageFormatter.of(MODULE_NAME, "已扩展农田")
            .field("方向", facing.cn())
            .field("格数", n + " 格")
            .field("新范围", rangeX + "×" + rangeZ)
            .field("农场点位1", ns.describe("§a"))
            .field("农场点位2", ne.describe("§e"))
            .status(CommandMessageFormatter.Level.SUCCESS, "已保存")
            .send();
    }

    /** 由偏航角换算水平朝向（东/南/西/北），Minecraft 0° 朝南、90° 朝西（旧 :255-261 逐字） */
    private Facing facingOf(float yaw) {
        float normalized = ((yaw % 360f) + 360f) % 360f;
        if (normalized >= 45f && normalized < 135f) return new Facing(-1, 0, "西");
        if (normalized >= 135f && normalized < 225f) return new Facing(0, -1, "北");
        if (normalized >= 225f && normalized < 315f) return new Facing(1, 0, "东");
        return new Facing(0, 1, "南");
    }

    private BlockPos shifted(BlockPos pos, int dx, int dy, int dz) {
        return new BlockPos(pos.getX() + dx, pos.getY() + dy, pos.getZ() + dz);
    }

    /** 水平朝向：步进向量 + 中文方位名（旧 :268 原样） */
    private record Facing(int stepX, int stepZ, String cn) {}

    // ── 取准星与容器判定（旧 :271-282 原样） ──

    /** 取准星命中的方块坐标，没命中方块返回 null */
    private BlockPos targetBlock() {
        HitResult hit = mc.hitResult;
        if (hit == null || hit.getType() != HitResult.Type.BLOCK) return null;
        if (!(hit instanceof BlockHitResult blockHit)) return null;
        return blockHit.getBlockPos().immutable();
    }

    /** 该坐标是否带有实现了 Container 的方块实体 */
    private boolean isContainer(BlockPos pos) {
        if (mc.level == null) return false;
        BlockEntity blockEntity = mc.level.getBlockEntity(pos);
        return blockEntity instanceof Container;
    }

    // ── 提示行（旧 :285-292 原样） ──

    /** 普通提示行（提示 / 说明类，不带状态行） */
    private void farmInfo(String message) {
        CommandMessageFormatter.sendLine(MODULE_NAME, message);
    }

    /** 失败 / 警告提示行（统一橙黄色） */
    private void farmError(String message) {
        CommandMessageFormatter.sendLine(MODULE_NAME, "§6" + message);
    }

    private static AutoFarmModule module() {
        AutoFarmModule module = ModuleManager.byId(AutoFarmModule.MODULE_ID) instanceof AutoFarmModule farm ? farm : null;
        // 模块未注册的兜底播报（旧 NongChangCommand :294-298 逐字；正常引导下不会触发）
        if (module == null) {
            CommandMessageFormatter.sendLine(MODULE_NAME, "§6自动农场模块未注册。");
        }
        return module;
    }

    // ═══════════════════════════════════════════════════════════════════
    //  静态工具（供模块页六卡片调用，旧 :304-349 原样；文案与指令路径按旧项目并存）
    // ═══════════════════════════════════════════════════════════════════

    /** 六卡片「删除」按钮：未绑定提示与删除回执按旧项目原文 */
    public static void removeBinding(SiteType type) {
        AutoFarmModule module = module();
        if (module == null) return;

        if (module.site(type) == null) {
            CommandMessageFormatter.sendLine(MODULE_NAME, "§6" + type.cn() + "本来就没有绑定");
        } else {
            module.clearSite(type);
            CommandMessageFormatter.sendLine(MODULE_NAME, "§c§l✗ 已删除 " + type.cn() + " 绑定");
        }
    }

    /** 六卡片状态：该锚点是否已绑定 */
    public static boolean hasBinding(SiteType type) {
        AutoFarmModule module = module();
        return module != null && module.site(type) != null;
    }

    // ── 解析辅助 ──

    /** 锚点中文字面量 → 类型；非法字面量给出参数提示（旧 Brigadier 在此处是解析失败） */
    private SiteType siteTypeOf(CommandContext context) {
        String input = context.arg(1);
        if (input == null) {
            context.usage(usage());
            return null;
        }
        for (SiteType type : SiteType.values()) {
            if (type.cn().equals(input)) return type;
        }
        context.error("未知锚点：" + input);
        context.usage(usage());
        return null;
    }
}
