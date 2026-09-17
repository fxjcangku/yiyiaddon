package com.yiyiaddon.feature.villager.fsm;

/**
 * 村民交易状态机的 18 个状态。
 *
 * <p>逐字迁移自旧项目 {@code VillagerTradeFSM.State}，取值名称与顺序均未改动；旧项目把它写成
 * 状态机的内嵌枚举，这里独立成文件，原因是按第 38 条拆分超千行类时，多个协作器
 * （{@link VillagerSupplyRunner} 等）都要引用状态，内嵌会形成「协作器必须持有宿主类型」的循环引用。</p>
 *
 * <p>状态推进的语义（谁在什么条件下进入哪个状态）仍全部写在 {@link VillagerTradeFSM} 里，
 * 本枚举只承载状态标识与它的中文名。</p>
 *
 * <p><b>中文名 {@link #cn()} 的取词依据</b>：旧项目只有英文状态标识，没有中文名（旧
 * {@code :1178-1197}），控制台要显示「现在到底在干什么」必须有中文名；每个词都取自旧项目自己的用词，
 * 不另立说法，逐条依据如下（行号指旧 {@code VillagerTradeFSM}）：</p>
 * <ul>
 *   <li>{@code 待机} ← 旧 {@code :203}「当前任务未处于空闲状态」；</li>
 *   <li>{@code 搜索村民} ← 旧 {@code :341} 搜索超时 / {@code :370} 锁定村民；</li>
 *   <li>{@code 查找工作站} ← 旧 {@code :423}「村民附近未找到工作站」；</li>
 *   <li>{@code 寻路中} ← 旧帮助「Baritone 寻路到工作站自动交易」；</li>
 *   <li>{@code 打开交易界面} ← 旧 {@code :588}「交易界面已关闭」；</li>
 *   <li>{@code 交易中} ← 旧 {@code :710}「第 N 笔成功」；</li>
 *   <li>{@code 关闭界面} ← 旧 {@code :588}；</li>
 *   <li>{@code 前往补给} ← 旧 {@code :664}「绿宝石不足…前往补给」；</li>
 *   <li>{@code 打开绿宝石箱 / 取绿宝石} ← 旧 {@code :867}「补给完成」；</li>
 *   <li>{@code 前往卸货} ← 旧 {@code :626}「背包已满…前往卸货」；</li>
 *   <li>{@code 打开成品交易箱 / 卸货中} ← 旧 {@code UnloadService}「开始卸货」；</li>
 *   <li>{@code 等待补货} ← 旧 {@code :977 / :985}「等待村民补货」；</li>
 *   <li>{@code 等待玩家} ← 旧枚举注释「原地模式：等待玩家手动补给/卸货」；</li>
 *   <li>{@code 切换任务} ← 旧 {@code :1042 / :1054}「任务 N/M」；</li>
 *   <li>{@code 已完成} ← 旧 {@code :1048}「Pipeline 全部任务完成」；</li>
 *   <li>{@code 出错} ← 旧 {@code :1080}「运行出错」。</li>
 * </ul>
 *
 * <p>本项目既有先例：自动挖矿的 {@code MinerState#cn()} 同形（控制台状态条第 2 格读它），
 * 故中文名收敛到枚举上这一处，控制台与模块页共用同一份，避免两处各写一张表（第 169 条）。</p>
 */
public enum VillagerTradeState {
    IDLE("待机"),
    SEARCHING("搜索村民"),
    RESOLVING_WORKSTATION("查找工作站"),
    NAVIGATING("寻路中"),
    OPENING_MENU("打开交易界面"),
    TRADING("交易中"),
    CLOSING_MENU("关闭界面"),
    SUPPLY_NAV("前往补给"),
    SUPPLY_OPEN("打开绿宝石箱"),
    SUPPLY_TAKE("取绿宝石"),
    UNLOAD_NAV("前往卸货"),
    UNLOAD_OPEN("打开成品交易箱"),
    UNLOAD_TAKE("卸货中"),
    /** 挂机循环：等待村民补货倒计时 */
    WAITING_RESTOCK("等待补货"),
    /** 原地模式：等待玩家手动补给/卸货 */
    WAITING_PLAYER("等待玩家"),
    NEXT_TASK("切换任务"),
    DONE("已完成"),
    ERROR("出错");

    private final String cn;

    VillagerTradeState(String cn) {
        this.cn = cn;
    }

    /** 状态的中文名（控制台状态条与模块页状态行共用） */
    public String cn() {
        return cn;
    }
}
