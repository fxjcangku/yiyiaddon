package com.yiyiaddon.ui.keybind;

import com.yiyiaddon.ui.widget.SettingKeybind;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 常驻功能键通道：模块把自己的「功能键」（非模块开关键）注册进来，
 * 由核心每刻轮询**松开边沿**并回调——**模块关闭时也照常工作**。
 *
 * <p><b>为什么需要它：</b>旧项目的功能键是框架 {@code KeybindSetting}，其 {@code action}
 * 只在模块开启时触发；而「模块未开启时按键要提醒玩家」这条行为，旧项目靠一个常驻事件总线
 * 监听器（{@code KeyRemindListener}）实现 —— 模块不 tick 也能收到按键。
 * 本项目模块不启用就不收事件、不 tick（第 89/92 条禁止模块自注册 Fabric 事件），
 * 因此需要一个挂在核心 tick 上的常驻通道（第 215 条：壳件缺失优先自研）。</p>
 *
 * <p><b>与旧项目的口径对齐</b>：</p>
 * <ul>
 *     <li>触发时机 = <b>松开边沿</b>（旧 {@code KeyAction.Release}）；</li>
 *     <li>只做检测与回调，<b>不在这里做节流</b>——旧实现的 3 秒节流是「未开启提醒」专用的，
 *         开启状态下连续按键不受节流限制；把节流放这里会改变开启状态的行为；</li>
 *     <li>守卫：界面打开 / 正在录制键位时不触发（旧框架的按键事件只在游戏内产生，
 *         本项目是轮询，必须显式补上，否则绑键时会顺手触发功能）。</li>
 * </ul>
 *
 * <p>注册是常驻的（不随模块开关注销）：模块在 {@code onInitialize} 注册一次即可。</p>
 */
public final class FunctionKeybinds {

    /**
     * 一条功能键登记。
     *
     * @param ownerId 所属模块 ID（用于注销）
     * @param name    键位名（播报里显示的键名，逐字 = 旧设置名）
     * @param key     键位值提供者（读模块设置里的绑定）
     * @param action  松开时的回调（模块自行裁决「开启=执行 / 关闭=提醒」）
     */
    private record Entry(String ownerId, String name, Supplier<AddonKeybind> key, Consumer<String> action) {
    }

    private static final List<Entry> ENTRIES = new ArrayList<>();

    /** 上一刻各键的按下状态（按引用标识，避免 Entry 值相等误判） */
    private static final Map<Entry, Boolean> PRESSED = new IdentityHashMap<>();

    private FunctionKeybinds() {
    }

    /**
     * 登记一条功能键。
     *
     * @param ownerId 模块 ID
     * @param name    键位名（播报用）
     * @param key     键位值提供者
     * @param action  松开时回调，参数为键位名
     */
    public static void register(String ownerId, String name, Supplier<AddonKeybind> key, Consumer<String> action) {
        if (ownerId == null || name == null || key == null || action == null) return;
        ENTRIES.add(new Entry(ownerId, name, key, action));
    }

    /** 注销某模块的全部功能键（模块销毁 / 重载时用；正常关闭模块不注销） */
    public static void unregister(String ownerId) {
        ENTRIES.removeIf(entry -> entry.ownerId().equals(ownerId));
    }

    /** 由核心 tick 每刻调用一次（不新增 Fabric 注册点） */
    public static void tick(Minecraft client) {
        if (client == null) return;
        boolean allowed = client.screen == null
            && !ModuleKeybindManager.isCapturing()
            && !SettingKeybind.isCapturing();

        for (Entry entry : ENTRIES) {
            AddonKeybind bind = entry.key().get();
            boolean down = bind != null && bind.isSet() && bind.isPressed();
            boolean previous = Boolean.TRUE.equals(PRESSED.get(entry));
            PRESSED.put(entry, down);

            // 只认「松开」边沿；守卫不通过时同样吞掉这次边沿，避免关界面后补触发
            if (down || !previous || !allowed) continue;
            entry.action().accept(entry.name());
        }
    }
}
