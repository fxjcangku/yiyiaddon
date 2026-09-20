package com.yiyiaddon.feature.mining.ui;

import com.yiyiaddon.feature.mining.AutoMinerModule;
import com.yiyiaddon.module.ModuleEntry;
import com.yiyiaddon.ui.component.CompactRow;
import com.yiyiaddon.ui.component.TextLine;
import com.yiyiaddon.ui.page.BasePage;
import com.yiyiaddon.ui.page.CompactModulePage;
import com.yiyiaddon.ui.page.ModulePage;
import com.yiyiaddon.ui.screen.HelpPanelScreen;
import com.yiyiaddon.ui.widget.Button;
import net.minecraft.client.Minecraft;

/**
 * 自动挖矿模块页：只留控制台入口与使用说明，**全部设置与点位都在控制台里**。
 *
 * <p><b>形态照星露谷模块页</b>（{@code StardewResourcePanelPage}，用户 2026-09-16 指令
 * 「跟星露谷物语天差地别…已经做进控制台了下面还一堆设置」）：顶部信息块 → 控制台入口 →
 * 配置记录入口 → 使用说明正文（用户 2026-09-17 口径：不再单摆「查看使用说明」按钮，
 * 正文直接铺在入口正下方，超出一屏时由本页自身的滚动条上下查看）。
 * 模块的 5 个设置分组（{@code 目标选择 / 传送指令 / 触发条件 / 物品管理 / Baritone调优}）
 * 与三个点位卡片**只在控制台出现一次**，本页不再平铺 —— 两份控件写同一份 {@code MiningSettings}，
 * 同页并存只会互相看对方为旧值。</p>
 *
 * <p><b>用户交互资产（逐字，禁止改写）：</b>按钮 {@code §b打开控制台}、
 * 帮助页 8 个章节的全部正文，来自旧项目原文（{@code AutoMinerModule.getWidget} +
 * {@code buildHelpContent}，{@code :1503-1683}）。</p>
 *
 * <p><b>点位绑定：</b>控制台点位页与 {@code .wk} 指令共用唯一实现
 * {@link com.yiyiaddon.feature.mining.service.MiningBindingService}（GUI 路径的文案多「请重新设置」、
 * 解绑回执带 {@code §c§l✗}，与指令路径两套并存，禁止统一）。</p>
 *
 * <p><b>与旧项目差异（登记）：</b>{@code 检测假矿} 按钮<b>不落地</b>（随种子模式留白，用户裁定）；
 * 帮助页「指令系统」章节里 {@code §8> §3.wk 检测假矿…} 那一行按同一裁定留白不写，但「种子挖矿」
 * 章节标题与逐字正文作为帮助文案资产完整保留。用户 2026-09-16 指令「没打开控制台之前的那个界面的
 * 点位删掉」，故本页原有的三行点位卡片已删除，「准备工作」「点位设置」两节帮助文案里指向旧卡片的
 * 句子同步改写成指向控制台点位页。</p>
 */
public final class AutoMinerPage extends CompactModulePage implements ModulePage {

    // ── 入口按钮文案（逐字） ──

    private static final String CONSOLE_BUTTON = "§b打开控制台";
    private static final String CONSOLE_HINT =
        "按用途分页：概览 / 点位 / 目标选择 / 传送指令 / 触发条件 / 自用模式 / Baritone调优"
            + "（自用模式与「目标选择」「传送指令」互斥显示）";

    // ── 配置记录入口（用户 2026-09-18：「是控制台下面新建一个按钮 服务器记录复原」、「我说弄在控制台按钮下面」） ──

    private static final String RECORD_BUTTON = "§b服务器记录复原";
    private static final String RECORD_HINT = "整套配置（设置 + 三个点位）一键保存，按服务器读取 / 替换 / 详情 / 删除";

    /** 帮助页 9 个章节（旧 {@code buildHelpContent :1618-1683} 逐字 + 2026-09-20 追加「自用模式」；
     *  框线与 {@code [#]} 格式由 HelpPanelScreen 生成） */
    private static final HelpPanelScreen.HelpSection[] HELP_SECTIONS = {
        new HelpPanelScreen.HelpSection("准备工作",
            "  §8├─ §f准备好挖矿工具 §7(推荐附魔耐久、效率)",
            "  §8├─ §f准备好武器 §7(修补耐久时用)",
            "  §8├─ §f放置矿物箱、食物箱 §7(装满食物)",
            "  §8├─ §f选好挂机修复点 §7(安全区域，怪物可到达)",
            "  §8└─ §f在控制台「点位」页设置三个点位"
        ),
        new HelpPanelScreen.HelpSection("点位设置 §7(两种方式)",
            "  §b▸ §e方式1 §8- §f控制台点位页",
            "    §7准星对准箱子 §8→ §f点击「设置」按钮",
            "    §7箱子类型：矿物箱、食物箱自动检测容器",
            "    §7挂机修复点：直接站在目标位置即可绑定",
            "",
            "  §b▸ §e方式2 §8- §f指令系统",
            "    §8> §3.wk 设置 矿物箱 §8— §7准星对准箱子，绑定矿物贮箱",
            "    §8> §3.wk 设置 食物箱 §8— §7准星对准箱子，绑定食物补给箱",
            "    §8> §3.wk 设置 挂机修复点 §8— §7站在目标位置后自动绑定 §7(含视角)",
            "",
            "  §7§o容器检测：箱子类点位会自动检测目标方块是否为容器",
            "  §7§o不是容器 §8→ §7自动拒绝并提示重新设置，避免卡死"
        ),
        new HelpPanelScreen.HelpSection("指令系统",
            "  §8> §3.wk 状态 §8— §7查看绑定状态 §7(含坐标、维度、视角)",
            "  §8> §3.wk 移除 §c<目标> §8— §7解绑单个坐标",
            "  §8> §3.wk 清空 §8— §7清空所有绑定"
        ),
        new HelpPanelScreen.HelpSection("状态机流程",
            "  §a[1] §f前往挖矿 §8→ §7发送挖矿指令，等区块加载完成",
            "  §a[2] §f采掘 §8→ §7Baritone自动挖矿，满载/饿/耐久触发转换",
            "  §a[3] §f卸货循环 §8→ §7传送到矿物箱，卸货，返回野外",
            "  §a[4] §f补给循环 §8→ §7传送到箱，拿食物，吃饱，返回",
            "  §c⚠ §f无经验修补的工具 §8→ §7修不了，不前往挂机点、只提示一次；"
                + "受损的是镐子且背包已无其余镐子才停机，否则继续挖矿",
            "  §c⚠ §f坚守者预警 §8→ §7挖到地下古城时，15×15 格内尖啸体一尖叫（判定坚守者准备出现，"
                + "或范围内已经有坚守者）就立刻按「前往挖矿指令」传送逃离，不等它钻出来 §7(默认开启，无需设置)",
            "  §e⚠ §f手动干预 §8→ §7你被传送走（自己敲 /home、/spawn，或点菜单、别人拉你，都算）时，"
                + "模块立刻暂停挖矿，不在你落地的地方继续挖 §7(判定看「位置是不是突然瞬移了很远」；"
                + "聊天 /w /msg 这类不移动的指令不打断；本模块自己发的传送不受影响；重开模块即恢复)",
            "  §c⚠ §f打怪要打死 §8→ §7自动打的怪（坚守者除外）必须确认已被打死或自爆了才回去挖矿，"
                + "躲开不算完 §7(检测范围 6 格内还有活怪就继续打；苦力怕走位特殊，确认半径放宽到 12 格；"
                + "它会自己炸掉，炸了同样算了结)；身边同时有苦力怕和其它怪时优先处理苦力怕"
                + "（点燃引信的先撤），不会被其它怪拖在原地挨炸"
        ),
        new HelpPanelScreen.HelpSection("物品管理 §7(默认全丢)",
            "  §c▸ §f丢弃逻辑：除保留项外，背包其余物品全部自动丢弃",
            "  §a▸ §f默认保留：任意品质工具 §7(镐/铲/斧/剑/锄)§f、白名单食物、目标矿物",
            "  §a▸ §f搭路方块 §7(圆石/地狱岩) §f只保留各一组，多余自动丢弃",
            "  §e▸ §f保留白名单：不想被扔的物品/方块加进去就不会丢",
            "  §6⚠ §f启动前记得把想留的东西加进「保留白名单」"
        ),
        new HelpPanelScreen.HelpSection("参数建议",
            "  §6▸ §f满载组数 §8= §e36 §7(标准背包容量)",
            "  §6▸ §f食物阈值 §8= §e14 §7(7格肉约14饱食度)",
            "  §6▸ §f耐久阈值 §8= §e50 §7(低于50且带经验修补时自动修复)",
            "  §6▸ §f传送等待 §8= §e10秒 §7(RTP加载缓冲)"
        ),
        new HelpPanelScreen.HelpSection("种子挖矿 §7(可选)",
            "  §d▸ §f启用后状态机切换采集流程 §7(两种模式)",
            "    §7普通模式：Baritone mine 挖视野内所有目标矿",
            "    §7种子模式：逐块寻路到预测真矿，原版合法破坏，无视假矿",
            "  §d▸ §f预测位置无矿自动跳过，附近挖完自动重新RTP换区",
            "  §d▸ §f检测假矿：对准可疑方块 §8→ §f本版本未落地 §7(随种子模式留白，无按钮、无指令)",
            "  §d▸ §f假矿判定：预测无矿但显示有矿 §8= §c假矿",
            "  §d▸ §f适用场景：防止挖到管理员放置的诱饵矿"
        ),
        new HelpPanelScreen.HelpSection("自用模式 §7(挖够就自己去卖)",
            "  §b▸ §f开关在控制台顶栏 §7(就在「启用」开关右边)，开关一开页签就换一套",
            "  §b▸ §f两种模式的页签互不干扰 §8— §7打开自用模式：只留「自用模式」页，"
                + "「目标选择」「传送指令」两页隐藏；关掉它反过来隐藏「自用模式」页",
            "  §b▸ §f自用模式页里就有目标三选一 §7(主世界矿石 / 下界矿石 / 普通方块，连物品管理名单一起)，"
                + "以及挖矿、补给、死亡返回那几行传送指令 §8— §7不用切回别的页",
            "  §b▸ §f「出售物品」不单独设项 §8— §7它就是你选的那个目标在当前采集模式下挖出来的产物"
                + "（时运是掉落物、精准是原矿物品；选石头没开精准就是圆石），那一栏只有图标和名字，"
                + "没有选择器也没有刷新按钮，选什么就卖什么",
            "  §b▸ §f纠错 §8— §7目标推导不出能卖的东西（落在水、岩浆这类没有物品形态的方块上）时："
                + "出售物品那一栏直接显示那句话、状态条同一句、启动自检拦住、切采集模式时弹渐入渐出提示",
            "  §b▸ §f卖不动会点名 §8— §7出售时连续几轮一颗未少就停机播报「收购菜单里没找到「XX」」，"
                + "多半是服主改过商品名或这个目标本服不收",
            "  §b▸ §f只绑食物箱 §8— §7矿物箱与挂机修复点不需要 §7(点位页自动隐藏这两行，已绑的不会丢)",
            "  §b▸ §f触发：背包里「出售物品」攒够设定组数就出发；背包先满也直接去卖",
            "  §b▸ §f死亡返回指令填 §f/back §8— §7落地后就地继续挖，不再重新 RTP",
            "",
            "  §a[1] §f回城 §8→ §7发「出售流程指令」，点「回城点击关键词」回主城大厅",
            "  §a[2] §f寻路 §8→ §7按「NPC 坐标」走到「NPC 名字关键词」那个收购 NPC 旁",
            "  §a[3] §f出售 §8→ §7右键 NPC 开收购菜单，循环：点出售物品 §8→ §f点「全部」 §8→ §f点「确认出售」，"
                + "直到背包该物品清零",
            "  §a[4] §f回服 §8→ §7再发流程指令，点「跨服点击关键词」打开服列表，勾选的「回程目标服」"
                + "落地后直接继续 RTP 挖矿",
            "",
            "  §e⚠ §f全程静默 §8— §7所有菜单都在后台点，不弹界面、不抢鼠标",
            "  §e⚠ §f服务器卡了就原地重试这一步 §7(单步超时 / 单步重试次数在自用模式页里调)，"
                + "重试用完才停机播报，不会带着矿乱走",
            "  §e⚠ §f耐久 §8— §7带经验修补的镐子不管 §7(挖矿自带经验，自己会修完)；没带经验修补的低于阈值时会自动"
                + "换一把能用的镐继续挖，镐子只剩 1 点耐久时也换 §7(不把它挖爆)，真换不到才停机播报",
            "  §e⚠ §f背包满不管 §8— §7满了直接触发出售 §7(垃圾自动丢弃照常在跑)"
        ),
        new HelpPanelScreen.HelpSection("注意事项",
            "  §c⚠ §f模块运行中无法修改点位，必须先关闭模块",
            "  §c⚠ §f已绑定点位不允许覆盖，必须先删除再重新设置",
            "  §c⚠ §f传送指令需服务器支持，否则无法自动返回",
            "  §c⚠ §f挂机修复点会记录视角，用于精准对准修补工作台",
            "  §c⚠ §f坚守者不参与自动击杀：它只由「坚守者预警」负责传送逃离，不会主动去打",
            "  §c⚠ §f苦力怕要确认被击杀或自爆才恢复挖矿：躲开不算完（不会躲完就低头继续挖）",
            "  §c⚠ §f默认全丢垃圾！想留下的物品务必先加进「保留白名单」",
            "  §c⚠ §f隔墙的矿不秒破：连锁与近矿清扫只挖看得见的目标矿，隔着方块的交给 Baritone 走过去挖",
            "  §c⚠ §f垫脚过岩浆：手长范围内的岩浆格边走边填（不停下来铺），搭路方块要放在白名单里才填得动",
            "  §c⚠ §f控制台「触发条件」页的「自动断线」开启时，血量掉到设定格数会立即退出服务器（防死亡掉落）",
            "  §c⚠ §f设置按服务器（单人按存档）分开保存，换服自动切换；上面「服务器记录复原」可一键保存整套配置（设置 + 三个点位），并按服务器读取 / 替换 / 详情 / 删除（读取与替换都只认本服 IP / 本存档）"
        )
    };

    /**
     * 内嵌说明行（去掉窗口外框三行）：本页把使用说明直接铺在「打开控制台」入口下方
     * （用户 2026-09-17 口径：不再单摆「查看使用说明」按钮，超出可上下滚动查看）。
     *
     * <p>声明在 {@link #HELP_SECTIONS} 之后，静态初始化顺序才保证读到的不是 null。</p>
     */
    private static final String[] HELP_LINES =
        HelpPanelScreen.inlineContent(HelpPanelScreen.buildHelpContent(HELP_SECTIONS));

    private final AutoMinerModule module;

    /** 页面内容是否已构建（见 {@link #createPage(ModuleEntry)} 的时序说明） */
    private boolean built;

    public AutoMinerPage(AutoMinerModule module) {
        this.module = module;
    }

    /**
     * 页面内容在「真正打开页面」时才构建。
     *
     * <p><b>时序约束</b>：{@code module.page()} 会在模组初始化阶段被调用一次
     * （{@code ModuleEntries.of} 用它判空，见 {@code ModuleEntries:35}）。点位卡片要显示磁盘上的
     * 真实绑定，而点位装载只在自检 / 启用时发生 —— 玩家可能先进页面配点位，因此这里先
     * {@link AutoMinerModule#reloadStore()} 再构建；推迟到打开页面时也避免在初始化阶段读盘。</p>
     */
    @Override
    public BasePage createPage(ModuleEntry entry) {
        if (!built) {
            built = true;
            module.reloadStore();
            build();
        }
        return this;
    }

    @Override
    public String getTitle() {
        return module.displayName();
    }

    @Override
    public String getSubtitle() {
        return module.description();
    }

    // ── 构建 ──

    private void build() {
        // ① 控制台入口（星露谷同款：提示行 + 居中按钮）
        addCore(new CompactRow("", () -> CONSOLE_HINT,
            new Button(CONSOLE_BUTTON, this::openConsole)).centeredControl());

        // ①′ 配置记录入口：紧挨在「打开控制台」按钮下面（用户 2026-09-18 明确指定的位置；
        //     此前放在控制台页脚，六个页签底部各一份，被用户否掉：「你弄去每个页面干嘛」「按钮在哪里」）
        addCore(new CompactRow("", () -> RECORD_HINT,
            new Button(RECORD_BUTTON, this::openRecords)).centeredControl());

        // ② 使用说明内嵌在控制台入口下方（用户 2026-09-17 口径），章节标题与正文逐字不变
        //    （「检测假矿」按钮随种子模式留白，不落地，因此说明里那一行仍是原文）
        for (String line : HELP_LINES) addCore(new TextLine(line));
    }

    // ── 界面跳转 ──

    /** 打开控制台（整屏分页）；父屏是当前模块页，ESC 回来 */
    private void openConsole() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.gui.setScreen(new MiningConsoleScreen(client.gui.screen(), module));
    }

    /** 打开配置记录窗（一键保存 / 读取 / 替换 / 详情 / 删除）；父屏是当前模块页，ESC 回来 */
    private void openRecords() {
        Minecraft client = Minecraft.getInstance();
        if (client == null) return;
        client.gui.setScreen(new MiningRecordScreen(client.gui.screen(), module));
    }
}
