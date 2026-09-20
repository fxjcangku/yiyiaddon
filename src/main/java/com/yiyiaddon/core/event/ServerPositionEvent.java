package com.yiyiaddon.core.event;

import net.minecraft.world.phys.Vec3;

/**
 * 服务端权威位置事件：把「位置纠正类」数据包换算成**只读的绝对坐标**后交给订阅者。
 *
 * <p><b>为什么要有这一层：</b>回弹 / 拉回判定必须读服务端下发的权威位置（含相对修正标志），
 * 而 {@link ClientEventType#PACKET_RECEIVE} 只给包类名。若把包对象交给模块，模块就能回写数据包、
 * 长期持有游戏对象；因此由核心侧（{@link EventDispatcher}）在收包瞬间完成原版同源换算
 * （{@code PositionMoveRotation.calculateAbsolute}），模块只拿到 {@link Vec3}。</p>
 *
 * <p><b>换算只在核心做一次</b>：传送的回弹验证与战术模块的拉回判定消费的是同一份结果
 * （旧项目里两处各自抄了一份逐字相同的换算，第 169 条要求同源逻辑只留一份）。</p>
 *
 * <p><b>换算时机</b>：在网络线程收包入队时立即完成，与旧实现（网络线程事件里读玩家
 * {@code PositionMoveRotation}）完全一致；派发仍在主线程每刻进行，模块侧不需要关心线程。</p>
 *
 * <p><b>为什么连距离一起算：</b>拉回判定要用「玩家当前位置到权威位置的距离」，
 * 而该距离必须在**包到达的同一刻**取（推迟到主线程时玩家已经移动，基准偏移会漏判或误判）。
 * 因此核心在入队时把距离一并算好，模块直接用。</p>
 *
 * <p><b>为什么还要带「换世界」标记：</b>原版跨维度传送与死亡重生的包序是
 * {@code ClientboundRespawnPacket} → {@code ClientboundPlayerPositionPacket}，
 * 而位置包的换算与距离是在收包瞬间（网络线程，早于主线程处理重生包）完成的——
 * 此刻客户端玩家实体仍在**旧世界**，{@code playerDistance} 是两个坐标系相减的无意义数值。
 * 跨世界 {@code /home} 的落点与旧坐标数值相近时，这个数值会落进拉回阈值区间造成误判，
 * 因此由核心按「重生包开启、维度切换或超时收尾」的静默窗口打标，判定层见到标记一律放行。</p>
 *
 * @param position       服务端权威位置（绝对坐标，已含相对修正换算）
 * @param vehicle        该位置来自载具包（{@code ClientboundMoveVehiclePacket}），否则来自玩家包
 * @param playerDistance 收包瞬间玩家位置到权威位置的距离（格）；玩家不在场时为 0
 * @param worldChange    该位置包落在重生 / 换世界静默窗口内，距离是跨世界的无意义数值，
 *                       不得参与拉回 / 回弹判定
 */
public record ServerPositionEvent(Vec3 position, boolean vehicle, double playerDistance, boolean worldChange) {
}
