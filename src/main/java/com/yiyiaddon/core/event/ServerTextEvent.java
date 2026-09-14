package com.yiyiaddon.core.event;

import net.minecraft.network.chat.Component;

/**
 * 服务器文本事件：从服务器下发的界面文本里抽取出的**只读文本**。
 *
 * <p><b>为什么要有这一层：</b>服务器 HUD 的当前季节、状态栏、记分板标题等语义只存在于原始
 * 数据包的文本里。若把数据包对象直接交给模块，模块就能回写数据包、长期持有游戏对象；
 * 因此这里由核心侧（{@link EventDispatcher}）在派发前把文本抠出来，模块只拿到
 * {@link Component} 文本与来源标识，拿不到包对象本身。</p>
 *
 * <p>{@link Component} 是不可变文本对象，模块只读；事件派发完毕后核心不再持有任何数据包引用。</p>
 *
 * @param channel 文本来源标识，取本类常量（{@link #TITLE} / {@link #SCORE_DISPLAY} 等）
 * @param text    该来源的原始文本
 * @param context 上下文标识；记分板相关事件为记分板目标名，其余为空串
 */
public record ServerTextEvent(String channel, Component text, String context) {

    // ── 全屏 / 动作栏 / 聊天 ──

    /** 动作栏文本（{@code ClientboundSetActionBarTextPacket}） */
    public static final String ACTION_BAR = "action_bar";
    /** 主标题（{@code ClientboundSetTitleTextPacket}） */
    public static final String TITLE = "title";
    /** 副标题（{@code ClientboundSetSubtitleTextPacket}） */
    public static final String SUBTITLE = "subtitle";
    /** 聊天栏消息（{@code ClientboundSystemChatPacket}，非 overlay） */
    public static final String CHAT = "chat";
    /** 动作栏消息（{@code ClientboundSystemChatPacket} 的 overlay 形态） */
    public static final String CHAT_OVERLAY = "chat_overlay";

    // ── 玩家列表 ──

    /** 玩家列表顶部（{@code ClientboundTabListPacket} header） */
    public static final String TAB_LIST_HEADER = "tab_list_header";
    /** 玩家列表底部（{@code ClientboundTabListPacket} footer） */
    public static final String TAB_LIST_FOOTER = "tab_list_footer";

    // ── 记分板 ──

    /** 记分板目标标题（{@code ClientboundSetObjectivePacket} 新增 / 更新）；{@code context} 为目标名 */
    public static final String OBJECTIVE_TITLE = "objective_title";
    /** 记分板目标被移除（{@code ClientboundSetObjectivePacket} 的 remove 方法）；{@code context} 为目标名 */
    public static final String OBJECTIVE_REMOVED = "objective_removed";
    /** 记分板条目自带显示文本（{@code ClientboundSetScorePacket} 的 display 存在）；{@code context} 为目标名 */
    public static final String SCORE_DISPLAY = "score_display";
    /** 记分板条目没有显示文本，仅有序条目名（{@code ClientboundSetScorePacket} 的 owner 兜底）；{@code context} 为目标名 */
    public static final String SCORE_OWNER = "score_owner";
    /** 记分板队伍显示名（{@code ClientboundSetPlayerTeamPacket}） */
    public static final String TEAM_DISPLAY = "team_display";
    /** 记分板队伍前缀（{@code ClientboundSetPlayerTeamPacket}） */
    public static final String TEAM_PREFIX = "team_prefix";
    /** 记分板队伍后缀（{@code ClientboundSetPlayerTeamPacket}） */
    public static final String TEAM_SUFFIX = "team_suffix";

    // ── BOSS 栏 ──

    /** BOSS 栏名称（{@code ClientboundBossEventPacket} 新增 / 改名） */
    public static final String BOSS_BAR = "boss_bar";
}
