package com.yiyiaddon.feature.villager.ui;

import com.yiyiaddon.feature.villager.AutoVillagerTradeModule;
import com.yiyiaddon.feature.villager.command.CunminCommand;
import com.yiyiaddon.feature.villager.repository.VillagerBindingStore;
import com.yiyiaddon.platform.world.WorldContextFormatter;
import com.yiyiaddon.ui.console.PointCardGrid;
import com.yiyiaddon.ui.widget.Button;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * 自动村民交易两张点位卡的唯一构建处：模块页与控制台（点位页）共用同一份数据与同一套按钮行为
 * （旧 {@code AutoVillagerTradeModule.buildLocationCard :770-826} 逐字换壳）。
 *
 * <p><b>用户交互资产（逐字，禁止改写）</b>：标题 {@code 绿宝石箱}（{@code §a}）/
 * {@code 成品交易箱}（{@code §6}）（旧 {@code :780-783}）、未绑定两行 {@code §8暂未绑定} /
 * {@code §8-}（旧 {@code :798-800}）、已绑定两行 {@code §7X§f… §7Y§f… §7Z§f…} 与 {@code §7<维度显示名>}
 * （旧 {@code :788-795}）、按钮 {@code 设置}（已绑定 {@code §a}、未绑定 {@code §8}，旧 {@code :805-806}）
 * 与 {@code §c删除}（旧 {@code :815}）。</p>
 *
 * <p><b>按钮行为（旧 {@code :807-821} 原样）</b>：{@code 设置} → {@link CunminCommand#setBinding(String)}
 * 后关界面；{@code 删除} → 仅在已绑定时调 {@link CunminCommand#removeBinding(String)} 再关界面
 * （未绑定时点了什么都不做，这是旧项目的守卫，不是本次漏写）。</p>
 *
 * <p><b>维度显示名</b>：旧 {@code getDimensionDisplayName :831-835}（{@code null} → 未知，
 * 否则走世界上下文格式化器）的等价实现，写法照 {@code FarmPointCards} 的维度列
 * （{@code AutoFarmModule.dimensionName}）。绑定存在时维度键恒非空
 * （{@link com.yiyiaddon.feature.villager.model.VillagerBinding#dimensionKey()} 三值映射有兜底），
 * 故与旧实现的「未知」分支只在坏档下才有别。</p>
 *
 * <p>本类不持模块实例：两箱绑定在仓库层 {@link VillagerBindingStore}（静态、按服务器分文件），
 * 卡片每帧现读，设置 / 删除后立刻反映真实状态。</p>
 */
public final class VillagerPointCards {

    /** 绿宝石箱数据键（与旧卡片的字面量一致） */
    private static final String KEY_EMERALD = VillagerBindingStore.KEY_EMERALD_CHEST;
    /** 成品交易箱数据键 */
    private static final String KEY_UNLOAD = VillagerBindingStore.KEY_UNLOAD_CHEST;

    private VillagerPointCards() {
    }

    /** 按旧模块页的配对顺序构建两张卡片：绿宝石箱 → 成品交易箱（旧 {@code :707-710}） */
    public static List<PointCardGrid.PointCard> all() {
        return List.of(
            card("绿宝石箱", KEY_EMERALD, "§a"),
            card("成品交易箱", KEY_UNLOAD, "§6"));
    }

    /**
     * 与 {@code FarmPointCards.all(module)} 同形签名的重载，供控制台按同一写法调用。
     *
     * <p>村民两箱绑定不在模块实例上（见类注释），参数只用于与既有调用点保持一致的写法，
     * 不读模块的任何状态；两张卡与 {@link #all()} 完全同一份实现，不是第二套卡片。</p>
     */
    public static List<PointCardGrid.PointCard> all(AutoVillagerTradeModule module) {
        return all();
    }

    /**
     * 单张点位卡：标题色 + 坐标 / 维度两行（未绑定时为 {@code §8暂未绑定} / {@code §8-}）+
     * {@code 设置} 与 {@code §c删除} 两行按钮。
     *
     * <p><b>为什么两行信息走 Supplier</b>：卡片只在页面构建时生成一次，写死字符串会让「设置 / 删除」
     * 之后卡片仍显示旧值（{@link PointCardGrid.PointCard} 的 Supplier 构造器就是为此而留），
     * 因此坐标行、维度行与 {@code 设置} 按钮的配色都按帧现读仓库。</p>
     *
     * @param title 卡片标题（中文名，逐字来自旧项目）
     * @param key   {@code emerald_chest} / {@code unload_chest}
     * @param color 标题色码（绿宝石箱 {@code §a}、成品交易箱 {@code §6}）
     */
    private static PointCardGrid.PointCard card(String title, String key, String color) {
        Button setButton = new Button(
            () -> (isBound(key) ? "§a" : "§8") + "设置",
            () -> {
                CunminCommand.setBinding(key);
                closeScreen();
            });
        Button deleteButton = new Button("§c删除", () -> {
            if (isBound(key)) {
                CunminCommand.removeBinding(key);
                closeScreen();
            }
        });

        return new PointCardGrid.PointCard(color + title,
            () -> coordLine(key), () -> dimensionLine(key),
            List.of(List.of(setButton), List.of(deleteButton)));
    }

    /** 坐标行：已绑定给 {@code §7X§f1 §7Y§f2 §7Z§f3}（旧 {@code :788-789}），未绑定给 {@code §8暂未绑定}（旧 {@code :798}） */
    private static String coordLine(String key) {
        BlockPos pos = posOf(key);
        if (pos == null) return "§8暂未绑定";
        return String.format("§7X§f%d §7Y§f%d §7Z§f%d", pos.getX(), pos.getY(), pos.getZ());
    }

    /** 维度行：已绑定给灰字维度显示名（旧 {@code :794-795}），未绑定给占位符 {@code §8-}（旧 {@code :800}） */
    private static String dimensionLine(String key) {
        if (posOf(key) == null) return "§8-";
        ResourceKey<Level> dimension = dimensionOf(key);
        return "§7" + WorldContextFormatter.dimensionSummary(
            dimension == null ? null : dimension.identifier().toString());
    }

    /** 该键是否已绑定（旧 {@code :777} 的 {@code pos != null} 判据原样） */
    private static boolean isBound(String key) {
        return posOf(key) != null;
    }

    /** 取该键的坐标（旧 {@code :775} 的三元表达式原样：非绿宝石箱键即成品交易箱） */
    private static BlockPos posOf(String key) {
        return KEY_EMERALD.equals(key)
            ? VillagerBindingStore.getEmeraldChestPos()
            : VillagerBindingStore.getUnloadChestPos();
    }

    /** 取该键的维度键（旧 {@code :776} 的三元表达式原样） */
    private static ResourceKey<Level> dimensionOf(String key) {
        return KEY_EMERALD.equals(key)
            ? VillagerBindingStore.getEmeraldChestDimension()
            : VillagerBindingStore.getUnloadChestDimension();
    }

    /** 绑定按钮点击后关界面（旧 {@code mc.setScreen(null)} 原样；客户端未就绪时不动） */
    private static void closeScreen() {
        Minecraft client = Minecraft.getInstance();
        if (client != null) client.setScreen(null);
    }
}
