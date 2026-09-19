package com.yiyiaddon.feature.respawn;

import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.module.Module;
import com.yiyiaddon.feature.respawn.ui.RespawnPage;
import com.yiyiaddon.ui.page.ModulePage;
import net.minecraft.client.Minecraft;

import java.util.List;
import java.util.Set;

/**
 * 自动重生：死亡后自动完成复活，无需手动点击复活按钮。
 *
 * <p>自研模块，替代旧项目挖矿流程里对第三方框架模块的调用（旧项目 {@code MinerFSM} 直接引用第三方
 * {@code AutoRespawn} 并 toggle；本项目零第三方依赖）。行为与该第三方实现的核心一致：只做一件事——
 * 玩家处于死亡状态时调用 {@code mc.player.respawn()}。</p>
 *
 * <p>刻意不做的事：不做延迟 / 时机设置项、不实现设置读写、不做播报。默认开启
 * （{@link #enabledByDefault()}），状态文件里一旦有记录（玩家手动关过）就尊重记录值。</p>
 *
 * <p>模块页是纯说明页（{@link RespawnPage}）：本模块没有设置可放，但不能不接页面——不接时模块中心
 * 点进去显示框架的「模块页面：未接入」（用户 2026-09-18 实机反馈）。</p>
 */
public final class AutoRespawnModule extends Module {

    /** 模块 ID，同时作为状态文件键、快捷键键名后缀 */
    public static final String MODULE_ID = "autorespawn";

    /**
     * 模块卡片图标：Material 符号 {@code auto_awesome}（星光，表示「自动」）。
     *
     * <p>码点 U+E65F 取自 {@code EnchantModule} 里已用 cmap 子表验真的备选集合（第 140 条），
     * 不再另做验真；此前本模块没有 icon() 覆盖，模块中心里图标位是空的（用户 2026-09-16 截图）。</p>
     */
    private static final String ICON = "\uE65F";

    @Override
    public String icon() {
        return ICON;
    }

    private final Minecraft mc = Minecraft.getInstance();

    public AutoRespawnModule() {
        super(MODULE_ID, "自动重生", "automation", "死亡后自动重生，无需手动点击复活按钮。");
    }

    @Override
    public int order() {
        return 10;
    }

    /** 必须常开：无任何历史记录时默认启用 */
    @Override
    public boolean enabledByDefault() {
        return true;
    }

    @Override
    public Set<ClientEventType> subscribedEvents() {
        return Set.of(ClientEventType.TICK);
    }

    @Override
    public void onEvent(ClientEvent event) {
        if (event == null || event.type() != ClientEventType.TICK) return;
        if (mc.player == null || mc.level == null) return;
        if (!mc.player.isDeadOrDying()) return;
        mc.player.respawn();
    }

    @Override
    public List<String> selfCheck() {
        return List.of();
    }

    /**
     * 模块页 = 纯说明页 {@link RespawnPage}。
     *
     * <p>本模块没有任何设置项，但页面不能空着：不接页面时模块中心点进去显示框架的元信息页
     * 「模块页面：未接入」，玩家会以为模块坏了（用户 2026-09-18 实机截图）。</p>
     */
    @Override
    public ModulePage page() {
        return new RespawnPage(this);
    }
}
