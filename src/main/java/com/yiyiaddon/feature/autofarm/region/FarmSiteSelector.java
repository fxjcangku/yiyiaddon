package com.yiyiaddon.feature.autofarm.region;

import com.yiyiaddon.core.CommandMessageFormatter;
import com.yiyiaddon.feature.autofarm.AutoFarmModule;
import com.yiyiaddon.feature.autofarm.model.FarmSite;
import com.yiyiaddon.feature.autofarm.model.SiteType;
import com.yiyiaddon.ui.render.world.EspColor;
import com.yiyiaddon.ui.render.world.EspRenderer;
import com.yiyiaddon.ui.render.world.ShapeMode;
import com.yiyiaddon.ui.render.world.WorldOverlay;
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;

/**
 * 自动农场锚点点选模式：<b>手持任意物品、在游戏里用左右键直接点方块</b>
 * （用户 2026-09-18 口径：照星露谷种植区域那套手感）。
 *
 * <p><b>两种模式，左右键含义不同：</b></p>
 * <ul>
 *     <li><b>农田范围</b>（{@link SiteType#START} / {@link SiteType#END}）：左键点第一个角 → 记「农场点位1」；
 *         右键点对角 → 记「农场点位2」并<b>立即成区退出</b>（与星露谷「左键一角、右键对角」完全一致）；
 *         还没点第一个角时按右键 = 取消退出。</li>
 *     <li><b>四个箱子</b>（{@link SiteType#SINGLE_STORAGE} 等）：左键点容器方块 → 绑定并退出；
 *         右键 = 取消退出。</li>
 * </ul>
 *
 * <p><b>为什么用 Fabric 事件而不是每 tick 读鼠标</b>（与
 * {@link com.yiyiaddon.feature.stardew.region.StardewRegionSelector} 同一套理由，那边有实机结论）：
 * 点选必须与「原版真的做了什么」对齐。左键用 {@link AttackBlockCallback}、右键用
 * {@link UseBlockCallback}，返回 {@link InteractionResult#FAIL} 时不会向服务器发包，模式内不可能挖到方块、
 * 浇水、开箱、播种。左键另挂 {@link ClientPreAttackCallback} 把「按住不放」（原版走
 * {@code continueAttack}，不经过方块回调）也一并取消。</p>
 *
 * <p><b>第一角为什么在 PreAttack 里记</b>：那条回调返回 true 会取消原版 {@code startAttack}，
 * 于是 {@code AttackBlockCallback} 根本收不到这一次点击（星露谷的实机反馈「左键点了没反应」）。
 * 因此左键落点与「取消原版行为」必须放在同一处完成，不能只靠方块回调。</p>
 *
 * <p><b>半成品不落盘</b>：只点了第一个角就切服 / 退出世界 / 关闭模块时，本次点选直接丢弃
 * （农田模式的两个角都点了才会写点位）。</p>
 *
 * <p><b>与旧项目的关系（有意差异，已在报告登记）</b>：旧项目绑定锚点是「准星对准方块 +
 * 卡片上的『设置』按钮 / {@code .farm 设置}」，本模式把<b>界面入口</b>换成游戏内左右键点选；
 * 指令路径 {@code .farm 设置} 保持原样（照旧按准星绑定），两者的校验文案与失败原因各自沿用原资产。</p>
 */
public final class FarmSiteSelector {

    /** 选区预览渲染层：只在点选模式内挂载（与星露谷选区的预览层同一手法） */
    private static final String LAYER_ID = "autofarm-site-select";

    private static final float LINE_THICKNESS = 1.5f;
    /** 第一个角：亮绿（与星露谷选区的第一角逐字同色，一眼看出已经记下了） */
    private static final EspColor FIRST_CORNER = new EspColor(0x00FF64, 200);
    /** 预览矩形：淡青，与最终农田边界框区分开 */
    private static final EspColor PREVIEW_SIDE = new EspColor(0x00C8FF, 40);
    private static final EspColor PREVIEW_LINE = new EspColor(0x00C8FF, 160);
    /** 容器模式的准星高亮：暖黄（与四个箱子的标题色同一族） */
    private static final EspColor CONTAINER_HINT = new EspColor(0xFFC800, 200);
    /** 预览字牌字号（与 {@code FarmRenderer} 的防呆字牌同档） */
    private static final float LABEL_SIZE = 10f;

    /** 当前实例：事件回调只装一次，永远转给最新的这个实例 */
    private static FarmSiteSelector instance;
    /** 事件回调是否已装（避免重复注册导致一次点击绑两个点位） */
    private static boolean installed;

    private final Minecraft mc = Minecraft.getInstance();
    private final AutoFarmModule module;

    /** 是否在点选模式 */
    private boolean active;
    /** 当前要绑定的点位 */
    private SiteType pending;
    /** 农田模式下已记下的第一个角 */
    private BlockPos firstCorner;

    public FarmSiteSelector(AutoFarmModule module) {
        this.module = module;
        instance = this;
        if (installed) return;
        installed = true;

        // 左键点方块：农田模式记第一个角；箱子模式直接绑定（两处的「取消原版行为」都在 PreAttack 里完成）
        AttackBlockCallback.EVENT.register((player, level, hand, pos, direction) -> {
            FarmSiteSelector selector = instance;
            if (selector == null || !selector.shouldTakeOver(player, hand)) return InteractionResult.PASS;
            selector.onLeftClick(pos);
            return InteractionResult.FAIL;
        });
        // 右键点方块：农田模式记对角并成区；箱子模式取消退出（都返回 FAIL，不落进世界）
        UseBlockCallback.EVENT.register((player, level, hand, hitResult) -> {
            FarmSiteSelector selector = instance;
            if (selector == null || !selector.shouldTakeOver(player, hand)) return InteractionResult.PASS;
            selector.onRightClick(hitResult);
            return InteractionResult.FAIL;
        });
        // 右键点到空气走「使用物品」这条路，同样拦下，免得模式内把食物吃了
        UseItemCallback.EVENT.register((player, level, hand) -> {
            FarmSiteSelector selector = instance;
            return selector != null && selector.shouldTakeOver(player, hand)
                ? InteractionResult.FAIL : InteractionResult.PASS;
        });
        // 左键指到生物时原版会攻击，模式内一并拦下
        AttackEntityCallback.EVENT.register((player, level, hand, entity, hitResult) -> {
            FarmSiteSelector selector = instance;
            return selector != null && selector.shouldTakeOver(player, hand)
                ? InteractionResult.FAIL : InteractionResult.PASS;
        });
        // 左键按下（含按住不放）：取消原版破坏，并在这一条里完成农田模式的落点
        ClientPreAttackCallback.EVENT.register((client, player, clickCount) -> {
            FarmSiteSelector selector = instance;
            if (selector == null || !selector.shouldTakeOver(player, InteractionHand.MAIN_HAND)) return false;
            BlockPos pos = selector.crosshairBlock();
            if (pos != null) selector.onLeftClick(pos);
            return true;
        });
    }

    // ── 进入 / 退出 ──

    public boolean isActive() {
        return active;
    }

    /** 当前待绑定的点位（不在模式内返回 {@code null}） */
    public SiteType pending() {
        return active ? pending : null;
    }

    /**
     * 从控制台「点位」页的「设置」按钮进入点选模式。
     *
     * @return 失败原因；成功返回 {@code null}（已发出进入提示，调用方负责关窗回游戏）
     */
    public String enter(SiteType type) {
        if (type == null) return "未指定点位类型";

        if (module.isEnabled()) {
            return "模块运行中无法修改锚点，请先关闭模块";
        }
        if (mc.level == null || mc.player == null) {
            return "当前不在游戏世界中，无法设置锚点";
        }
        if (module.site(type) != null) {
            return type.cn() + "已绑定，请先删除旧绑定再重新设置";
        }
        if (type == SiteType.START || type == SiteType.END) {
            // 农田范围的两个角必须一起重设：任一角已绑定都先要求删除
            SiteType other = type == SiteType.START ? SiteType.END : SiteType.START;
            if (module.site(other) != null) {
                return other.cn() + "已绑定，请先删除旧绑定再重新设置";
            }
        }

        active = true;
        pending = type;
        firstCorner = null;
        // 实时预览层：第一个角记下之后，准星指哪就把预览框拉到哪（与星露谷选区同一套观感）
        WorldOverlay.register(LAYER_ID, this::render);

        if (type == SiteType.START || type == SiteType.END) {
            CommandMessageFormatter.of(AutoFarmModule.MESSAGE_MODULE, "农田范围选点")
                .field("怎么选", "左键点第一个角，右键点对角成区")
                .field("退出", "还没点第一个角时按右键取消")
                .status(CommandMessageFormatter.Level.SUCCESS, "等第一个角")
                .send();
        } else {
            CommandMessageFormatter.of(AutoFarmModule.MESSAGE_MODULE, type.cn() + "选点")
                .field("怎么选", "左键点容器方块（箱子 / 桶 / 潜影盒）")
                .field("退出", "按右键取消")
                .status(CommandMessageFormatter.Level.SUCCESS, "等左键点容器")
                .send();
        }
        return null;
    }

    /** 退出点选模式；{@code silent} 为 true 时不播报（切服 / 关闭模块 / 绑定成功收口用） */
    public void cancel(boolean silent) {
        boolean wasActive = active;
        active = false;
        pending = null;
        firstCorner = null;
        WorldOverlay.unregister(LAYER_ID);
        if (!silent && wasActive) {
            CommandMessageFormatter.of(AutoFarmModule.MESSAGE_MODULE, "锚点点选已取消")
                .status(CommandMessageFormatter.Level.SUCCESS, "半成品已丢弃，没有写入任何点位")
                .send();
        }
    }

    /** 模块关闭 / 退出世界时清理模式（由 {@code AutoFarmModule} 调用） */
    public static void cancelIfActive(boolean silent) {
        FarmSiteSelector selector = instance;
        if (selector != null && selector.isActive()) selector.cancel(silent);
    }

    // ── 左右键落点 ──

    private void onLeftClick(BlockPos clicked) {
        if (!active || clicked == null) return;
        if (pending == SiteType.START || pending == SiteType.END) {
            BlockPos pos = clicked.immutable();
            // 鼠标这一击可能被两条路都看到（PreAttack 与 AttackBlock）：同一格只记一次，免得重复播报
            if (pos.equals(firstCorner)) return;
            firstCorner = pos;
            CommandMessageFormatter.of(AutoFarmModule.MESSAGE_MODULE, "已记下第一个角")
                .coord(pos.getX(), pos.getY(), pos.getZ())
                .status(CommandMessageFormatter.Level.SUCCESS, "右键点对角成区")
                .send();
            return;
        }
        bindContainer(clicked.immutable());
    }

    private void onRightClick(BlockHitResult hit) {
        if (!active) return;
        if (pending == SiteType.START || pending == SiteType.END) {
            if (firstCorner == null) {
                cancel(false);
                return;
            }
            bindFarmArea(hit.getBlockPos().immutable());
            return;
        }
        cancel(false);
    }

    /** 农田成区：一次写入两个角（点位1 = 左键那一角，点位2 = 右键那一角），成功即退出 */
    private void bindFarmArea(BlockPos second) {
        FarmSite start = FarmSite.here(firstCorner);
        FarmSite end = FarmSite.here(second);
        if (start == null || end == null) {
            fail("无法获取当前维度信息");
            return;
        }
        module.bindSite(SiteType.START, start);
        module.bindSite(SiteType.END, end);

        FarmSite boundStart = module.site(SiteType.START);
        FarmSite boundEnd = module.site(SiteType.END);
        int rangeX = Math.abs(boundEnd.pos().getX() - boundStart.pos().getX()) + 1;
        int rangeZ = Math.abs(boundEnd.pos().getZ() - boundStart.pos().getZ()) + 1;

        CommandMessageFormatter.of(AutoFarmModule.MESSAGE_MODULE, "已绑定农田范围")
            .field("农场点位1", boundStart.describe("§a"))
            .field("农场点位2", boundEnd.describe("§e"))
            .field("范围", rangeX + "×" + rangeZ)
            .status(CommandMessageFormatter.Level.SUCCESS, "已保存")
            .send();
        cancel(true);
    }

    /** 容器点位绑定：必须指向真正的容器方块（与 {@code .farm 设置} 同一判据） */
    private void bindContainer(BlockPos pos) {
        if (mc.level == null) return;
        BlockEntity blockEntity = mc.level.getBlockEntity(pos);
        if (!(blockEntity instanceof Container)) {
            fail("该锚点需要指向容器方块（箱子 / 桶 / 潜影盒等），请左键点容器");
            return;
        }
        FarmSite site = FarmSite.here(pos);
        if (site == null) {
            fail("无法获取当前维度信息");
            return;
        }
        SiteType type = pending;
        module.bindSite(type, site);

        CommandMessageFormatter.of(AutoFarmModule.MESSAGE_MODULE, "已绑定" + type.cn())
            .coord(pos.getX(), pos.getY(), pos.getZ())
            .field("维度", site.dimension().identifier().toString())
            .status(CommandMessageFormatter.Level.SUCCESS, "已保存")
            .send();
        cancel(true);
    }

    private void fail(String reason) {
        CommandMessageFormatter.of(AutoFarmModule.MESSAGE_MODULE, "锚点点选")
            .field("原因", reason)
            .status(CommandMessageFormatter.Level.FAILURE, "未保存")
            .send();
    }

    // ── 接管判定 ──

    /**
     * 是否由本模式接管这一次左右键：只在点选模式内、且没有打开任何界面时接管。
     *
     * <p>手持什么物品都与接管无关（与星露谷同一口径：站在地里多半正拿着锄头或种子，
     * 不该逼人先切空手）；模式内的一切左右键都不落到世界里。</p>
     */
    private boolean shouldTakeOver(net.minecraft.world.entity.player.Player player, InteractionHand hand) {
        return active && hand == InteractionHand.MAIN_HAND && player != null && mc.gui.screen() == null;
    }

    /** 准星命中的方块坐标，没命中返回 {@code null}（{@link BlockHitResult} 本身即「命中方块」） */
    private BlockPos crosshairBlock() {
        return mc.hitResult instanceof BlockHitResult blockHit ? blockHit.getBlockPos() : null;
    }

    // ── 实时预览（与星露谷种植区域选区同一套观感） ──

    /**
     * 选区实时预览（用户 2026-09-18：「选地的时候没有 esp 星露谷农场选地的时候是有 esp 的」）。
     *
     * <p>农田模式：第一个角记下之后，准星指到哪就把预览矩形拉到哪，并在矩形顶上实时写「N × M」；
     * 还没记第一个角时只把准星指着的方块框出来，说明「这一下会点到哪」。</p>
     *
     * <p>箱子模式：只把准星指着的方块框出来（容器判据在左键那一刻才校验，预览不预先判错，
     * 免得玩家把准星从石头移到箱子上时颜色忽明忽暗）。</p>
     */
    private void render(EspRenderer renderer) {
        if (!active || mc.player == null || mc.level == null) return;
        BlockPos cursor = crosshairBlock();

        if (pending == SiteType.START || pending == SiteType.END) {
            if (firstCorner == null) {
                if (cursor != null) {
                    renderer.blockBox(cursor.getX(), cursor.getY(), cursor.getZ(),
                        FIRST_CORNER, FIRST_CORNER, ShapeMode.Lines, LINE_THICKNESS);
                }
                return;
            }
            // 第一个角已经记下：即使准星没指到方块，也要把那个角标住，免得玩家以为点位丢了
            renderer.blockBox(firstCorner.getX(), firstCorner.getY(), firstCorner.getZ(),
                FIRST_CORNER, FIRST_CORNER, ShapeMode.Lines, LINE_THICKNESS);
            if (cursor == null) return;

            renderer.box(rect(firstCorner, cursor), PREVIEW_SIDE, PREVIEW_LINE,
                ShapeMode.Lines, LINE_THICKNESS);
            int sizeX = Math.abs(firstCorner.getX() - cursor.getX()) + 1;
            int sizeZ = Math.abs(firstCorner.getZ() - cursor.getZ()) + 1;
            float centerX = (Math.min(firstCorner.getX(), cursor.getX())
                + Math.max(firstCorner.getX(), cursor.getX())) / 2.0f + 0.5f;
            float centerZ = (Math.min(firstCorner.getZ(), cursor.getZ())
                + Math.max(firstCorner.getZ(), cursor.getZ())) / 2.0f + 0.5f;
            // §l = 加粗（用户 2026-09-19：「所有的点位模块都要字体加粗」，MinecraftText 的测量与绘制都认）
            renderer.text("§l" + sizeX + " × " + sizeZ, centerX,
                Math.min(firstCorner.getY(), cursor.getY()) + 1.6, centerZ,
                LABEL_SIZE, PREVIEW_LINE, 1.0f, true);
            return;
        }

        if (cursor != null) {
            renderer.blockBox(cursor.getX(), cursor.getY(), cursor.getZ(),
                CONTAINER_HINT, CONTAINER_HINT, ShapeMode.Both, LINE_THICKNESS);
        }
    }

    /** 预览矩形的世界包围盒：两角取最小 / 最大，高度都取两角中较低的那一层（与星露谷 {@code rect} 同算法） */
    private static AABB rect(BlockPos a, BlockPos b) {
        int minX = Math.min(a.getX(), b.getX());
        int maxX = Math.max(a.getX(), b.getX());
        int minY = Math.min(a.getY(), b.getY());
        int minZ = Math.min(a.getZ(), b.getZ());
        int maxZ = Math.max(a.getZ(), b.getZ());
        return new AABB(minX, minY, minZ, maxX + 1.0, minY + 1.0, maxZ + 1.0);
    }
}
