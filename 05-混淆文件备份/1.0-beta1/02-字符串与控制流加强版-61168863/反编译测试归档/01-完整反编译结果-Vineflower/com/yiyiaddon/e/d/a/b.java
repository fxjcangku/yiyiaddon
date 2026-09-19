package com.yiyiaddon.e.d.a;

import com.yiyiaddon.l.h.d;

public final class b {
   public static final String cx = "自动登入";
   public static final String cy = "自动注册、登录、断线重连、进服后执行指令序列。详细参考下面使用说明。";
   public static final String cz = "概览";
   public static final String cA = "登录认证";
   public static final String cB = "自动重连";
   public static final String cC = "自动执行指令";
   public static final String cD = "进服路线";
   public static final String cE = "自用配置";
   public static final String cF = "账号与调试";
   public static final String cG = "自动登录";
   public static final String cH = "GUI 自动登录";
   public static final String cI = "服务器免登录检测";
   public static final String cJ = "检测服务器子服";
   public static final String cK = "登录密码";
   public static final String cL = "登录延迟（tick）";
   public static final String cM = "自动注册";
   public static final String cN = "注册密码";
   public static final String cO = "注册延迟（tick）";
   public static final String cP = "世界加载等待（tick）";
   public static final String cQ = "认证检测超时（tick）";
   public static final String cR = "自动重连";
   public static final String cS = "重连等待（tick）";
   public static final String cT = "无限重连";
   public static final String cU = "最大重连次数";
   public static final String cV = "登录后执行指令";
   public static final String cW = "执行指令";
   public static final String cX = "指令延迟（tick）";
   public static final String cY = "自动进入目标区域";
   public static final String cZ = "进入方式";
   public static final String da = "菜单工具";
   public static final String db = "菜单物品关键词";
   public static final String dc = "菜单点击顺序";
   public static final String dd = "每步等待（tick）";
   public static final String de = "菜单超时（tick）";
   public static final String df = "目标区域关键词";
   public static final String dg = "必须命中目标关键词";
   public static final String dh = "到达稳定等待（tick）";
   public static final String di = "到达目标后执行指令";
   public static final String dj = "目标区域指令";
   public static final String dk = "额外指令延迟（tick）";
   public static final String dl = "启用自用配置";
   public static final String dm = "自用菜单工具";
   public static final String dn = "菜单工具关键词";
   public static final String do = "登录服按钮";
   public static final String dp = "欢迎页入口";
   public static final String dq = "主城世界传送按钮";
   public static final String dr = "第一层生存按钮";
   public static final String ds = "第二层生存按钮";
   public static final String dt = "最终目标子服按钮";
   public static final String du = "主城到达关键词";
   public static final String dv = "挂机区识别关键词";
   public static final String dw = "挂机区自动回服";
   public static final String dx = "挂机区菜单延迟（分钟）";
   public static final String dy = "返回主城按钮关键词";
   public static final String dz = "到达确认关键词";
   public static final String dA = "每步等待（tick）";
   public static final String dB = "单步超时（tick）";
   public static final String dC = "主城钟快捷键兜底（Shift＋F）";
   public static final String dD = "主城钟兜底等待（tick）";
   public static final String dE = "异常恢复等待上限（分钟）";
   public static final String dF = "异常恢复初始重试（秒）";
   public static final String dG = "异常恢复最大重试次数";
   public static final String dH = "进服时自动检测";
   public static final String dI = "立即检测账号";
   public static final String dJ = "调试模式";
   public static final String dK = "检测到登录提示时自动发送登录指令。";
   public static final String dL = "自动识别并填写 GUI 登录框（服务器弹出的密码输入界面）。";
   public static final String dM = "适用于服务器两小时免登录：每次进服先监听成功登录或已登入消息；命中时保持自动登录关闭，未命中或收到登录指令提示时自动开启登录。";
   public static final String dN = "大厅完成登录后，进入子服时沿用大厅登录状态，不重复发送登录指令。关闭后每个新服务器连接都重新检测登录。";
   public static final String dO = "自动登录使用的密码（不会显示在聊天栏）。";
   public static final String dP = "收到登录提示后等待多少 tick 再发送指令（20 tick = 1 秒）。";
   public static final String dQ = "检测到注册提示时自动发送注册指令。";
   public static final String dR = "自动注册使用的密码（不会显示在聊天栏）。";
   public static final String dS = "收到注册提示后等待多少 tick 再发送指令（20 tick = 1 秒）。";
   public static final String dT = "进入服务器后等待多少 tick 再开始认证流程（20 tick = 1 秒）。";
   public static final String dU = "开始监听后等待多少 tick 仍未收到登录/注册提示，则判定该服务器无需登录，直接进入就绪状态（20 tick = 1 秒）。";
   public static final String dV = "断线后在主菜单或断线界面等待指定时间，再自动连接上一次服务器。";
   public static final String dW = "断线后等待多少 tick 再重连（20 tick = 1 秒）。";
   public static final String dX = "开启后忽略最大重连次数，持续尝试连接，直到成功进服或关闭自动重连。";
   public static final String dY = "连续失败达到该次数后停止重连。";
   public static final String dZ = "仅在自动登录成功或认证检测超时后执行自定义指令；登录和注册均关闭时不会执行。";
   public static final String ea = "登录完成后执行的指令（含斜杠，如 /home）。";
   public static final String eb = "登录完成后等待多少 tick 再执行指令（20 tick = 1 秒）。";
   public static final String ec = "认证完成后按照选择的进入方式到达目标区域。";
   public static final String ed = "直接进入、菜单传送、子服网络或自用多阶段回服路线，四种模式互斥运行。";
   public static final String ee = "选择用于打开服务器菜单的快捷栏物品。";
   public static final String ef = "辅助匹配改名物品的名称或 Lore。";
   public static final String eg = "每一项是一步，按从上到下的顺序匹配物品名称或 Lore。";
   public static final String eh = "使用菜单物品或点击按钮后等待的时间。";
   public static final String ei = "等待菜单或目标关键词的最长时间，超时后停止，避免乱点。";
   public static final String ej = "扫描侧边栏确认到达目标区域。";
   public static final String ek = "开启后只有侧边栏命中目标区域关键词才确认到达。";
   public static final String el = "确认目标区域后等待多少 tick 再执行指令。";
   public static final String em = "确认进入目标区域并等待稳定后执行一次指令。";
   public static final String en = "进入目标区域后执行的指令，例如 /home。";
   public static final String eo = "到达稳定等待结束后额外等待多少 tick 再执行指令。";
   public static final String ep = "自用总开关：控制认证完成后的首次进服路线；关闭后仍可单独保留挂机区自动回服。";
   public static final String eq = "仅在快捷栏识别到该物品时，主城才会转向空气并右键打开菜单。";
   public static final String er = "菜单工具名称或 Lore 的辅助关键词。";
   public static final String es = "登录服菜单中传送到欢迎页的按钮关键词。";
   public static final String et = "选择扫描自用入口，或点击欢迎菜单中可直达主城的无名书本。";
   public static final String eu = "到达主城后重新打开菜单并点击的按钮关键词。";
   public static final String ev = "世界传送页面第一层资源大区按钮关键词。";
   public static final String ew = "下一页面再次出现的资源大区按钮关键词。";
   public static final String ex = "最后选择资源一区或资源二区的按钮关键词。";
   public static final String ey = "欢迎页点击后仅通过侧边栏确认已到达主城，避免误把其他子服当作主城。";
   public static final String ez = "侧边栏命中任一关键词时识别为挂机区；是否自动回服由「挂机区自动回服」单独控制。";
   public static final String eA = "挂机区独立开关：检测到挂机区后执行返回主城大区、世界传送、资源大区、资源大区、资源二区；不受自用总开关影响。";
   public static final String eB = "检测到挂机区后等待多久再打开菜单，避免服务器重启后菜单尚未准备好。默认 10 分钟，设置为 0 立即打开。";
   public static final String eC = "挂机区 Shift＋F 菜单中返回主城大区按钮的关键词。";
   public static final String eD = "最终进入目标子服后，侧边栏用于确认成功；应与最终目标子服按钮一致。";
   public static final String eE = "菜单打开、按钮点击和页面切换之间的等待时间。";
   public static final String eF = "每个路线阶段的最长等待时间，超时后停止以避免乱点。";
   public static final String eG = "主城快捷栏未识别到钟时，直接尝试 Shift＋F 快捷菜单。";
   public static final String eH = "已识别到钟但右键未打开菜单时，等待多久再触发 Shift＋F。20 tick = 1 秒。";
   public static final String eI = "自用路线断线后最多等待多久继续恢复当前步骤，按分钟设置，最大 30 分钟。";
   public static final String eJ = "自用路线异常恢复的首次重试等待，后续按次数递增退避。";
   public static final String eK = "自用路线异常恢复期间允许的最多连接尝试次数。";
   public static final String eL = "每次进入服务器后自动输出账号类型（正版 / 离线）；与自动登录、自动注册和自动指令相互独立。";
   public static final String eM = "点击后立即在聊天栏输出当前账号的检测结果（不发送任何网络请求）。结果仅供参考，不参与登录判断。";
   public static final String eN = "在聊天栏输出当前状态、识别到的指令等调试信息。";
   public static final String eO = "§b选择";
   public static final String eP = "打开物品选择器：条目带图标、分组可折叠、点任意一行即选中";
   public static final String eQ = "未选择";
   public static final String eR = "添加";
   public static final String eS = "把输入框里的内容加入名单（重复项跳过）";
   public static final String eT = "从名单里移除这一项";
   public static final String eU = "未填写";
   public static final String eV = "选择";
   public static final String eW = "§e重连倒计时：§f";
   public static final String eX = " 秒";
   public static final String eY = "§a正在连接...";
   public static final String eZ = "立即重连";
   public static final String fa = "§c停止重连";
   public static final String fb = "§a就绪";
   public static final String fc = "§a连接中";
   public static final String fd = "§8无";
   public static final String fe = "§f§l";
   public static final String ff = "快速上手";
   public static final String fg = "自动流程";
   public static final String fh = "自用配置";
   public static final String fi = "路线与指令";
   public static final String fj = "状态说明";
   public static final String fk = "注意事项";
   public static final String[] h = new String[]{
      (String)com.yiyiaddon.m.b.a<"s3qex8k2sgcatc","3TdgBcVjUNAFXEDB1N04LENECEMjBHbRyixTN/yNkyskZqelAw0e0BtpSwGmUGwGbCkxoi8Z4KNDpi9IfDAtC3+33aXrboIIvQBGHiuV9o43sRghfppVMnhpx1xnBOoz",7936508526270446727,3682494354403274661,-6846687520960725261,-9183717117804598347>(),
      (String)com.yiyiaddon.m.b.a<"s3epenwrvq1nzm","OfHotnYiORtzbvtP/6ySt3dacfwvkO0jn+0ZrO7sPrOoq1DN8UNqN0WY0xr19YpRqk3aB29NrkXO6yUizHItF3d7c3RyJS5YJmonYb3qm64y8H+E0cB3m0TCqfbFtZ1u9SPPjeKS3E4=",-8031143086569022189,3210708690461965618,-3402442401594455427,-385813324911283196>(),
      (String)com.yiyiaddon.m.b.a<"s3twu6hw0uzhwk","mzjNfn2tID6zd3Fp7wK9HYdbsVGhQVN1HSFVgI8AJE+W9OI1JDlJ8td3nWnAMuL3Z4OwhGINXDDYAW6wMQotM2j3I0aZ1T2gO/70Jky0m7uUkcUhkPxfdveG830=",694573178577544972,-7999388623700530552,8804994972755092224,7774608016487322485>()
   };
   public static final String[] i = new String[]{
      (String)com.yiyiaddon.m.b.a<"s1kpngeb0g3as4","7a2f9d6ExPx6rUKNKc2mIA/ImimA0+Q+zk6AvJr657mUyqfqHKCXshmeTibpDrKo3ZYbcTKFJ8Hfm2yrbTipbbz3NfzteRVE7bNEaW8RRPjHgE9g59UddQ==",-2261157559237058400,-6680455351698049194,2905821737869003310,7994980921395445010>(),
      (String)com.yiyiaddon.m.b.a<"s2mr523i2t5ojg","EePQC84HkvTGGxeS+txiKkFw+Rx+nqzV6V661Fy/Wz2rd602qFMOIIuFt/4BgFt43rcWh2bWo0l/5cCU3+tvZTs1vdFU+XrbjpgNm4oweE6/gQ==",-4194256515090623761,-8868572518866403401,-2961554694303613317,2810190818700000186>(),
      (String)com.yiyiaddon.m.b.a<"s3oie6fkf18ce8","7252aY1aqE4UfL40HUGFbXwbcn3ErfKhWDkpIHlPfKlZBwwGXhw2JFGSzWlT6lrDLJOQILv6s0SOZvJ7hvpcysydwmUbj4Dc0/Xg4ZR3AmAkMcFm",-4244146389201118632,1715715198887154396,3631111135196494242,-5563323295810118798>(),
      (String)com.yiyiaddon.m.b.a<"s2gi25uh31wqz3","PMhMT/BpWsrOLb/P2LQ4PLTtkfy72TCl6MyOpxrla9F+dXwWIYY/kkbc06q8l+q4zonXGxAqrhGkm1yUi4F+7+rrvZ7DEi7iW/+GOk1PYNB7YPaJfBBz1A==",-2624523320056007610,1991566124390131839,-518087448004055284,7485040174067529373>(),
      (String)com.yiyiaddon.m.b.a<"s2z8werpk2woyo","4zx/I/L2aVRNSFP917V5zjQg8xaB2zUWMS2jN491VHW6F4sqWslpWaUJ/kR405Flrwa7vH407xQj50GjrXibiNgeXjvophsSPS+k1nSsDCGlVpvs9bnr36YzHDZEDBuM",-7300794214466975373,6463176871553870114,3634954321614192442,-4475824828081698796>()
   };
   public static final String[] j = new String[]{
      (String)com.yiyiaddon.m.b.a<"s97o0ylwcahta","HEsWnEGr85EEQXU37BoiFOc75T2s++ZheOvJSPN+aecGQ6zsgQsqpyWZ6NK5EZRvvxU/DBf30yiKzNsHvVhiiKC4EhHQNPa/NdCGmZk5bGNxFK2VMyMgAHMkx3FrqvOr",-6182607172564864346,-5853257551654018325,4600563469484423563,-183406213923505132>(),
      (String)com.yiyiaddon.m.b.a<"s2jtndvl3wmzy0","WZgN6BVA0aJ9Gr7PlFyWv4g3uZjBMFqK7R6/Jj7iKjf/WMBrTrpTDi3wCWtHSYPgNy9H9/wo3m2Bwkk/hU+KVZoqct3L7POFxWxfwIsl2SgKIEF3jgfguYd8f4I9y6StgKTW+w==",7564269663382944065,-2242009105939892256,-5269716768041877233,-3419836443246438666>(),
      (String)com.yiyiaddon.m.b.a<"s89pjeu8vgh64","Jl2az+CfUIdj6lEBKfzfDbWLLrsJaAApqPeIvseE8ZaayEQHMYNrdl9jpaUigtKIHgej7GkcYaFj6i7s15pf4qiFnaJGcwEHD1imlO0OtmrzrD+/wOI3SFxClJf2MM13yTQyZkQW6gNLfAsbfnf6ePFlgdHco4yoE/jgOy/KcDyqle2xpD4eJg==",6038492099044083444,9020670623985621003,1001119177654712279,-1195930486668023621>(),
      (String)com.yiyiaddon.m.b.a<"s2ts3btr7jk5i2","adoZykIrLQjVPiZkdNnDxoJVqAVGgN2RDau9Kl/51TKqD8cnH9ybvd23JOJtjvMfXOuuxroOFpHFv38sTWl/DpsAnck9BGeH5BSjszKA//Os20e/XFEJmPzINP3AdTHh1RcE1A==",509875075770809200,-489131315706180158,1495154778449836305,-3199320810751389167>(),
      (String)com.yiyiaddon.m.b.a<"s2camlfke7a04j","vyevegpiV54b+WvWXarIiuX9Ox70vr+LuxVZL8/3tIfaRBJdeNgYH9e+ze5iGQyeTaZNxRqgInnAJwhj9bFXdcqKWO/LZ8OhS1QZuiDZh6EOK80YesRNEZjLYJ9d9t4jjdB1A7Bf",4845490348183877558,-1782634883251069537,6086519718911666228,-1114115486060091591>()
   };
   public static final String[] k = new String[]{
      (String)com.yiyiaddon.m.b.a<"s1eloox7n7hf4g","+cbwGo5vKP16YzmW7uF/oc1VDm9DiMEMAE1d2w9gDRwEo8bhpY4DZbEClwNaosGDxgG9XW8uenhERb658lXoOT2dkAM0pMN4167jvQ2pdjxe0thUzPkGe+0i4B0=",4803869432556880192,3352612470279269492,-6487474335713947111,-2802277647614085960>(),
      (String)com.yiyiaddon.m.b.a<"s2p8m0weq7vxrt","m/nY9lTPjNJAXeXij1JvBPqETWY6VrgCpG0l7qTcPQVxDhDWRETBqPZHPUCaZ0QtJv9Wsj0BSmHn9HWRwF0+pgSkmTPXTreIvDSy2mi7f0Py+VWbqwgb0sp5b04M+aHX",-4239523222375260987,8292416805757115530,592254906759157917,-5321721921643496558>(),
      (String)com.yiyiaddon.m.b.a<"sgq86ijy43b97","i2bcZN1dFsiFUkXwnWEvpzbw7R2Mb5vBG3anzbK4oO3fJljlE96IcnpyTon5Bh4vsvu7zRtwwaQjOj3JZUbUWbHvB1/y/IDz4uiMR8+cBqkYT8HCmkWwbuIpwIesXUFd",3062088714975182514,8748863431703590009,3844089209110628087,8612131505982652058>(),
      (String)com.yiyiaddon.m.b.a<"s1kw12vsfzsh6y","ZZG3ZhonvXE7CaaoMy5cbsGyjzk3OZIE7p6GM66EEsmBenKQY9SSxl/Mr97ksfhLFTjEaNo7vy5msHXxajEEZRHZoRE50lVYcc0KFgHm+zEGqVCftYPNFVY64uKhb9pk6fFtaA==",-8327762287503304952,5538031424415475181,2255481242078010831,-2085378083066206968>(),
      (String)com.yiyiaddon.m.b.a<"s2y6t8aiaj6tje","cRKegE4RYyHdEHYhYkSVPEbwbnapbzoimeBC3jQ2zBvZsi57MJLYPuVQn1OrN0JVdjrFAEXTk3jcC+NchATdvFbz3A0pgrOolbDtWdSKVx6VkKVKJQVnhjRe4buUQu7KZnL6eQeOy6HXLhMFmPM=",-4162258373083013025,-5127388095014710691,-7600704505983991386,-2725318021074046606>()
   };
   public static final String[] l = new String[]{
      (String)com.yiyiaddon.m.b.a<"s11tk3zak3ov5","Nd45pliA/sQb0CRTiK2vJFhv6Z34PdqhTPfVUEAsdgnrRQSoFNDrh7FksGG7dEoOd2b4UR+dfGirWkOzMbCl1GeT76/ZNWN0zy+Xsu+ko/vut4eJ/iU=",-6824090302484604712,-8515295993123839308,-126734641399769259,1679097551227798302>(),
      (String)com.yiyiaddon.m.b.a<"s51ujc7d2j85k","omvWHD9QYCM/oLX5EBWnEQvqJ8ekBBKtIEMlAHOjIjqOuEcirbQYVPuRYcm7iRmrTqMcn8LRMZnr4Cc6HB4w55Qk0RxRgmhCuKRGaQu6RMU=",-3042261405966702672,-7481075619247563403,4650074492434397243,-2666453314966055029>(),
      (String)com.yiyiaddon.m.b.a<"sjlnjclr9wfs1","OoVt/ih7+0cWYw9xIlXRbtfABjPvqM1GukdwsnqgkVextRtS7vbJu5TCpBUmPpLJ404pTJrjvDajNnpf8drZDR8biXwtUtRxFQJfWn3M",2024923006275064984,6322650781453577630,5249561349122830365,-7422866151925693447>()
   };
   public static final String[] m = new String[]{
      (String)com.yiyiaddon.m.b.a<"s3fge3de7kr3x8","hf6APXrzKC69Oez71Nh9Jb3IgYFBPrXwHGtmfVgMxZg8+sKIxiq+kt2ogHb8zlNKKa6Iu6OV062y2wsNuoXIwszK2ixfElfH9/JAcms5k5I0BkhOqA8eJze9PuqSX6JS",1070005485546036051,-4897638911337227489,3624803800627458162,-8101430770294635894>(),
      (String)com.yiyiaddon.m.b.a<"s21r5s3gk9pzh4","7ba4r0LCmrE6J4L53YOVGTk6ht9MkD0tRjKR3ybvN8F5ABJpQNzOIvL0c7Yq3ARfIGAWT5m3SAWQ2ZVcRYmQyaqYGggYajbjPcBEAU5LyokFyxn6Lrdyml/aB461cB+xpqQCySAlkU0aA2L2f/dAQZ+K",-39965576956852120,1031026033656338949,-7587606645472826686,3216023577700091769>(),
      (String)com.yiyiaddon.m.b.a<"s1fuxxk0xpoo4k","d9xaleNkRxmynTWy3NGms3MSQBVXKxlwLAeCS9IXRjAWQhPogOD2caspC3TtQuYvlZ6Ja3rjWWUCLIvFDP/NoDSB8WOPv4IWRnDBsGZ9hBOy2ft/eeNHr3w5+HI=",6869411342259883387,3868049907098764112,11180058274449237,2105840335831445464>(),
      (String)com.yiyiaddon.m.b.a<"s61e61t92ifia","R874WKyhE7mkKfSkfxlPmVjFMtkfNt3x+I9gO6yS+bcFd6qbKrwKcjY8UhBQFKYPGq/9wJKCgLY2U70Nodi4vgrBKjqYjYBkr2XOK1SAPZzVy8BIYE+hRQ==",-4919894643463299836,-44394666889396317,1239180686263747931,-8320981167378076786>()
   };

   private b() {
   }

   public static d.a[] a() {
      return new d.a[]{
         new d.a(
            (String)com.yiyiaddon.m.b.a<"s32ql69q9msou9","RtPN7qu+3enfygZ4rWz2MRpoS4FeCN12YmaAOnQ/2tO58hx1",-1167814242004993562,6829258630191823584,7615854846593276026,5275019750463387254>(),
            h
         ),
         new d.a(
            (String)com.yiyiaddon.m.b.a<"s1sr54lmcebcnx","r0uwIp/dwf/2DxmgJhBelD+jULKlHrnEhxu4VEdT3Obmt7jI",6185004258456767301,1939391924797799821,2151760275672368727,-6792159467107128197>(),
            i
         ),
         new d.a(
            (String)com.yiyiaddon.m.b.a<"s3roochm79rlji","i8uC2/X+EVdIkVKMCIoDj7ia1+O9lo8MKj2MIjINMnBC/9ZL",-8288069878275134666,-8042521491214573178,-1444658963543371177,6788198975264967728>(),
            j
         ),
         new d.a(
            (String)com.yiyiaddon.m.b.a<"s3dyoa56eo1519","OmbHGtHDBDEvV5B9V+jjKDDbW9/piOtNSu9JaGWrVP67el4Qks8=",3723416148040550867,-9185469644818153930,-2501572417246943112,1527756941800399521>(),
            k
         ),
         new d.a(
            (String)com.yiyiaddon.m.b.a<"s6y6qkvxwerbb","MVl2joTZdKLkuxZ54Erq9KbHVp3hTfqLhlwjJXAprewNlJME",4934257392373410845,4155453807013777727,-8342680326569183095,-7528884916208058257>(),
            l
         ),
         new d.a(
            (String)com.yiyiaddon.m.b.a<"s2ejc9ornbhai8","Baq46j3ZtfmCWEuqmwJ5DWpN7UuSNfdOOk2AForld8rSjM4s",1052186900967958785,-5382011329959644467,4927987825995447537,-5934208175292502742>(),
            m
         )
      };
   }
}
