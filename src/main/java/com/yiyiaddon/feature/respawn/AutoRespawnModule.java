package com.yiyiaddon.feature.respawn;

import com.yiyiaddon.core.event.ClientEvent;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.core.module.Module;
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
 * <p>刻意不做的事：不做延迟 / 时机设置项、不做配置页面（{@link #page()} 返回 {@code null}）、
 * 不实现设置读写、不做播报。默认开启（{@link #enabledByDefault()}），状态文件里一旦有记录
 * （玩家手动关过）就尊重记录值。</p>
 */
public final class AutoRespawnModule extends Module {

    /** 模块 ID，同时作为状态文件键、快捷键键名后缀 */
    public static final String MODULE_ID = "autorespawn";

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
}
