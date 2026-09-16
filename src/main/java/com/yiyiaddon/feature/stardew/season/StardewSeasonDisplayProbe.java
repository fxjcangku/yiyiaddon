package com.yiyiaddon.feature.stardew.season;

import com.yiyiaddon.core.event.ClientEventBus;
import com.yiyiaddon.core.event.ClientEventType;
import com.yiyiaddon.platform.BossBarProbe;
import com.yiyiaddon.platform.GameProbe;
import com.yiyiaddon.service.resourcepack.ResourceExtractionService;
import net.minecraft.network.chat.Component;

/**
 * 季节「显示态」轮询：把屏幕上正显示的季节文本补进季节服务。
 *
 * <p><b>解决什么：</b>季节证据原来只来自服务器发包。服务器把带季节的 HUD（本服为顶部 BOSS 栏）
 * 设置一次后就不再重发，或那次包发生在进服之前；更常见的是「资源包检测 / 提取」把会话证据整体作废
 * （资源指纹变化即清空），此后如果服务器不再刷那条 HUD，季节就一直停在「未知」。轮询直接读客户端
 * 已持有的显示态，于是「屏幕上有什么就能读到什么」，不再依赖包何时再来一次。</p>
 *
 * <p><b>挂在服务侧而不是模块侧：</b>季节服务本身是常驻的（模块开关不影响证据收集），轮询同理——
 * 模块没开、控制台刚打开、刚提取完资源，这些时刻都需要它。故由
 * {@link StardewSeasonService#init()} 调一次 {@link #init()}，随后 TICK 与「资源就绪」两条入口
 * 都由本类自己订阅。</p>
 *
 * <p><b>开销与让位：</b>每秒一次读取，只做几次字符串比较；写入走
 * {@link StardewSeasonService#acceptDisplayed}，文本没变不会改代次、不会触发重规划。服务器仍在发
 * 季节包时，服务侧的静默窗口会让轮询直接返回，两个来源不会互相抢写。</p>
 */
public final class StardewSeasonDisplayProbe {

    /** 事件订阅所有者标识（服务级：模块开关不影响显示态轮询） */
    private static final String OWNER = "service.stardew-season-display";

    /** 轮询间隔（tick）：1 秒一次。季节是分钟级变化量，1 秒足够跟上屏幕刷新，开销可忽略。 */
    private static final int POLL_INTERVAL_TICKS = 20;

    /** 来源标注：与包证据的「BOSS 栏」区分开，`.stardew 季节` 的来源行能看出这条是读显示态来的。 */
    private static final String BOSS_BAR_SOURCE = "BOSS 栏（显示）";

    private static int countdown;
    private static boolean initialized;

    private StardewSeasonDisplayProbe() {
    }

    /** 只注册一次：TICK 定时轮询 + 资源就绪立即补读一次（指纹变化会清空会话证据，那正是最需要的时刻）。 */
    public static synchronized void init() {
        if (initialized) return;
        initialized = true;
        ClientEventBus.subscribe(OWNER, ClientEventType.TICK, event -> tick());
        ResourceExtractionService.addReadyListener(StardewSeasonDisplayProbe::poll);
    }

    /** 定时入口：每秒一次；模块未开启时也会走（服务级订阅，不随模块开关起落）。 */
    private static void tick() {
        if (--countdown > 0) return;
        countdown = POLL_INTERVAL_TICKS;
        poll();
    }

    /** 立即读一次当前显示态并喂给季节服务。 */
    private static void poll() {
        if (!GameProbe.isMultiplayer()) return;
        StardewSeasonService service = StardewSeasonService.instance();
        for (Component name : BossBarProbe.displayedNames()) {
            service.acceptDisplayed(name, BOSS_BAR_SOURCE);
        }
    }
}
