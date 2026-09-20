package com.yiyiaddon.ui;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 网络归属的中文显示名：国家码 → 国名，一级行政区英文名 → 中文省/州名。
 *
 * <p>探测源（ip-api.com / ipapi.co）只给英文名（{@code Stockholm}、{@code Oregon}），界面按中文呈现。
 * 行政区查表分三步：<b>归一化后精确匹配</b> → <b>去掉首尾行政通用词再匹配</b>
 * （{@code Västra Götaland County} / {@code State of Berlin} / {@code Wellington Region} 这类缀词在源里很常见）
 * → <b>自治区关键词兜底</b>。三步都不中即为未收录，返回 {@code null}，界面只显示国名 ——
 * <b>宁可不显示，也不把英文原名丢到中文界面上</b>（用户 2026-09-20：「网络地区没有完全百分百汉化」，
 * 截图里是「瑞典 · Stockholm」）。</p>
 *
 * <p>国名按 ISO 3166-1 两位码取：常用地区用本表的短名（「阿联酋」而不是 CLDR 的「阿拉伯联合酋长国」，
 * 数据格宽度紧张），其余交给 JDK 的 CLDR 中文名兜底，任何合法国家码都能出中文。</p>
 */
public final class RegionNames {

    private RegionNames() {
    }

    /** 常用国家码 → 中文短名；未收录的码交给 {@link #cldrCountry(String)}。 */
    private static final Map<String, String> COUNTRIES = Map.ofEntries(
            Map.entry("CN", "中国"), Map.entry("HK", "香港"), Map.entry("TW", "台湾"),
            Map.entry("MO", "澳门"), Map.entry("JP", "日本"), Map.entry("KR", "韩国"),
            Map.entry("SG", "新加坡"), Map.entry("MY", "马来西亚"), Map.entry("TH", "泰国"),
            Map.entry("VN", "越南"), Map.entry("PH", "菲律宾"), Map.entry("ID", "印度尼西亚"),
            Map.entry("IN", "印度"), Map.entry("US", "美国"), Map.entry("CA", "加拿大"),
            Map.entry("MX", "墨西哥"), Map.entry("BR", "巴西"), Map.entry("AR", "阿根廷"),
            Map.entry("GB", "英国"), Map.entry("DE", "德国"), Map.entry("FR", "法国"),
            Map.entry("NL", "荷兰"), Map.entry("BE", "比利时"), Map.entry("CH", "瑞士"),
            Map.entry("AT", "奥地利"), Map.entry("SE", "瑞典"), Map.entry("NO", "挪威"),
            Map.entry("FI", "芬兰"), Map.entry("DK", "丹麦"), Map.entry("PL", "波兰"),
            Map.entry("ES", "西班牙"), Map.entry("IT", "意大利"), Map.entry("PT", "葡萄牙"),
            Map.entry("IE", "爱尔兰"), Map.entry("CZ", "捷克"), Map.entry("RO", "罗马尼亚"),
            Map.entry("UA", "乌克兰"), Map.entry("TR", "土耳其"), Map.entry("RU", "俄罗斯"),
            Map.entry("AE", "阿联酋"), Map.entry("SA", "沙特阿拉伯"), Map.entry("IL", "以色列"),
            Map.entry("AU", "澳大利亚"), Map.entry("NZ", "新西兰"), Map.entry("ZA", "南非"));

    /**
     * 一级行政区 → 中文名。键已归一化（小写、去重音、去撇号逗号点号、分隔符转空格），
     * 见 {@link #normalize(String)}；改键前先跑 {@link #normalize(String)}，否则查不到。
     * 覆盖：中国 34 个省级 + 港澳台 + 常见出口国家/地区的一级行政区。
     */
    private static final Map<String, String> REGIONS = Map.ofEntries(
            // —— 中国 ——
            Map.entry("beijing", "北京市"), Map.entry("tianjin", "天津市"),
            Map.entry("hebei", "河北省"), Map.entry("shanxi", "山西省"),
            Map.entry("liaoning", "辽宁省"), Map.entry("jilin", "吉林省"),
            Map.entry("heilongjiang", "黑龙江省"), Map.entry("shanghai", "上海市"),
            Map.entry("jiangsu", "江苏省"), Map.entry("zhejiang", "浙江省"),
            Map.entry("anhui", "安徽省"), Map.entry("fujian", "福建省"),
            Map.entry("jiangxi", "江西省"), Map.entry("shandong", "山东省"),
            Map.entry("henan", "河南省"), Map.entry("hubei", "湖北省"),
            Map.entry("hunan", "湖南省"), Map.entry("guangdong", "广东省"),
            Map.entry("hainan", "海南省"), Map.entry("chongqing", "重庆市"),
            Map.entry("sichuan", "四川省"), Map.entry("guizhou", "贵州省"),
            Map.entry("yunnan", "云南省"), Map.entry("shaanxi", "陕西省"),
            Map.entry("gansu", "甘肃省"), Map.entry("qinghai", "青海省"),
            Map.entry("inner mongolia", "内蒙古自治区"), Map.entry("guangxi", "广西壮族自治区"),
            Map.entry("ningxia", "宁夏回族自治区"), Map.entry("xinjiang", "新疆维吾尔自治区"),
            Map.entry("tibet", "西藏自治区"), Map.entry("taiwan", "台湾省"),
            Map.entry("hong kong", "香港"), Map.entry("macau", "澳门"),
            Map.entry("macao", "澳门"),
            // —— 香港 18 区 ——
            Map.entry("central and western", "中西区"), Map.entry("wan chai", "湾仔区"),
            Map.entry("eastern", "东区"), Map.entry("southern", "南区"),
            Map.entry("yau tsim mong", "油尖旺区"), Map.entry("sham shui po", "深水埗区"),
            Map.entry("kowloon city", "九龙城区"), Map.entry("wong tai sin", "黄大仙区"),
            Map.entry("kwun tong", "观塘区"), Map.entry("kwai tsing", "葵青区"),
            Map.entry("tsuen wan", "荃湾区"), Map.entry("tuen mun", "屯门区"),
            Map.entry("yuen long", "元朗区"), Map.entry("north", "北区"),
            Map.entry("tai po", "大埔区"), Map.entry("sha tin", "沙田区"),
            Map.entry("sai kung", "西贡区"), Map.entry("islands", "离岛区"),
            // —— 台湾 ——
            Map.entry("taipei", "台北市"), Map.entry("new taipei", "新北市"),
            Map.entry("taoyuan", "桃园市"), Map.entry("taichung", "台中市"),
            Map.entry("tainan", "台南市"), Map.entry("kaohsiung", "高雄市"),
            Map.entry("keelung", "基隆市"), Map.entry("hsinchu", "新竹市"),
            Map.entry("chiayi", "嘉义市"), Map.entry("hsinchu county", "新竹县"),
            Map.entry("miaoli", "苗栗县"), Map.entry("changhua", "彰化县"),
            Map.entry("nantou", "南投县"), Map.entry("yunlin", "云林县"),
            Map.entry("chiayi county", "嘉义县"), Map.entry("pingtung", "屏东县"),
            Map.entry("yilan", "宜兰县"), Map.entry("hualien", "花莲县"),
            Map.entry("taitung", "台东县"), Map.entry("penghu", "澎湖县"),
            Map.entry("kinmen", "金门县"), Map.entry("lienchiang", "连江县"),
            // —— 日本 ——
            Map.entry("hokkaido", "北海道"), Map.entry("aomori", "青森县"),
            Map.entry("iwate", "岩手县"), Map.entry("miyagi", "宫城县"),
            Map.entry("akita", "秋田县"), Map.entry("yamagata", "山形县"),
            Map.entry("fukushima", "福岛县"), Map.entry("ibaraki", "茨城县"),
            Map.entry("tochigi", "栃木县"), Map.entry("gunma", "群马县"),
            Map.entry("saitama", "埼玉县"), Map.entry("chiba", "千叶县"),
            Map.entry("tokyo", "东京都"), Map.entry("kanagawa", "神奈川县"),
            Map.entry("niigata", "新潟县"), Map.entry("toyama", "富山县"),
            Map.entry("ishikawa", "石川县"), Map.entry("fukui", "福井县"),
            Map.entry("yamanashi", "山梨县"), Map.entry("nagano", "长野县"),
            Map.entry("gifu", "岐阜县"), Map.entry("shizuoka", "静冈县"),
            Map.entry("aichi", "爱知县"), Map.entry("mie", "三重县"),
            Map.entry("shiga", "滋贺县"), Map.entry("kyoto", "京都府"),
            Map.entry("osaka", "大阪府"), Map.entry("hyogo", "兵库县"),
            Map.entry("nara", "奈良县"), Map.entry("wakayama", "和歌山县"),
            Map.entry("tottori", "鸟取县"), Map.entry("shimane", "岛根县"),
            Map.entry("okayama", "冈山县"), Map.entry("hiroshima", "广岛县"),
            Map.entry("yamaguchi", "山口县"), Map.entry("tokushima", "德岛县"),
            Map.entry("kagawa", "香川县"), Map.entry("ehime", "爱媛县"),
            Map.entry("kochi", "高知县"), Map.entry("fukuoka", "福冈县"),
            Map.entry("saga", "佐贺县"), Map.entry("nagasaki", "长崎县"),
            Map.entry("kumamoto", "熊本县"), Map.entry("oita", "大分县"),
            Map.entry("miyazaki", "宫崎县"), Map.entry("kagoshima", "鹿儿岛县"),
            Map.entry("okinawa", "冲绳县"),
            // —— 韩国 ——
            Map.entry("seoul", "首尔特别市"), Map.entry("busan", "釜山广域市"),
            Map.entry("daegu", "大邱广域市"), Map.entry("incheon", "仁川广域市"),
            Map.entry("gwangju", "光州广域市"), Map.entry("daejeon", "大田广域市"),
            Map.entry("ulsan", "蔚山广域市"), Map.entry("sejong", "世宗特别自治市"),
            Map.entry("gyeonggi", "京畿道"), Map.entry("gyeonggi do", "京畿道"),
            Map.entry("gangwon", "江原道"), Map.entry("gangwon do", "江原道"),
            Map.entry("chungcheongbuk do", "忠清北道"), Map.entry("north chungcheong", "忠清北道"),
            Map.entry("chungcheongnam do", "忠清南道"), Map.entry("south chungcheong", "忠清南道"),
            Map.entry("jeollabuk do", "全罗北道"), Map.entry("north jeolla", "全罗北道"),
            Map.entry("jeollanam do", "全罗南道"), Map.entry("south jeolla", "全罗南道"),
            Map.entry("gyeongsangbuk do", "庆尚北道"), Map.entry("north gyeongsang", "庆尚北道"),
            Map.entry("gyeongsangnam do", "庆尚南道"), Map.entry("south gyeongsang", "庆尚南道"),
            Map.entry("jeju", "济州特别自治道"), Map.entry("jeju do", "济州特别自治道"),
            // —— 蒙古 / 中亚 ——
            Map.entry("ulaanbaatar", "乌兰巴托"), Map.entry("almaty", "阿拉木图"),
            Map.entry("astana", "阿斯塔纳"), Map.entry("nur sultan", "阿斯塔纳"),
            Map.entry("tashkent", "塔什干"),
            // —— 东南亚 ——
            Map.entry("singapore", "新加坡"),
            Map.entry("johor", "柔佛州"), Map.entry("kedah", "吉打州"),
            Map.entry("kelantan", "吉兰丹州"), Map.entry("melaka", "马六甲州"),
            Map.entry("malacca", "马六甲州"), Map.entry("negeri sembilan", "森美兰州"),
            Map.entry("pahang", "彭亨州"), Map.entry("penang", "槟城州"),
            Map.entry("pulau pinang", "槟城州"), Map.entry("perak", "霹雳州"),
            Map.entry("perlis", "玻璃市州"), Map.entry("sabah", "沙巴州"),
            Map.entry("sarawak", "砂拉越州"), Map.entry("selangor", "雪兰莪州"),
            Map.entry("terengganu", "登嘉楼州"), Map.entry("kuala lumpur", "吉隆坡联邦直辖区"),
            Map.entry("labuan", "纳闽联邦直辖区"), Map.entry("putrajaya", "布城联邦直辖区"),
            Map.entry("bangkok", "曼谷"), Map.entry("chiang mai", "清迈府"),
            Map.entry("chiang rai", "清莱府"), Map.entry("phuket", "普吉府"),
            Map.entry("chon buri", "春武里府"), Map.entry("chonburi", "春武里府"),
            Map.entry("rayong", "罗勇府"), Map.entry("samut prakan", "北榄府"),
            Map.entry("nonthaburi", "暖武里府"), Map.entry("pathum thani", "巴吞他尼府"),
            Map.entry("ayutthaya", "大城府"), Map.entry("khon kaen", "孔敬府"),
            Map.entry("udon thani", "乌隆府"), Map.entry("nakhon ratchasima", "呵叻府"),
            Map.entry("surat thani", "素叻他尼府"), Map.entry("songkhla", "宋卡府"),
            Map.entry("krabi", "甲米府"), Map.entry("prachuap khiri khan", "巴蜀府"),
            Map.entry("phitsanulok", "彭世洛府"), Map.entry("nakhon si thammarat", "洛坤府"),
            Map.entry("samut sakhon", "龙仔厝府"),
            Map.entry("hanoi", "河内市"), Map.entry("ha noi", "河内市"),
            Map.entry("ho chi minh", "胡志明市"), Map.entry("ho chi minh city", "胡志明市"),
            Map.entry("saigon", "胡志明市"), Map.entry("da nang", "岘港市"),
            Map.entry("hai phong", "海防市"), Map.entry("can tho", "芹苴市"),
            Map.entry("binh duong", "平阳省"), Map.entry("dong nai", "同奈省"),
            Map.entry("ba ria vung tau", "巴地头顿省"), Map.entry("quang ninh", "广宁省"),
            Map.entry("bac ninh", "北宁省"), Map.entry("hung yen", "兴安省"),
            Map.entry("hai duong", "海阳省"), Map.entry("thanh hoa", "清化省"),
            Map.entry("nghe an", "乂安省"), Map.entry("khanh hoa", "庆和省"),
            Map.entry("lam dong", "林同省"), Map.entry("thua thien hue", "顺化市"),
            Map.entry("hue", "顺化市"), Map.entry("quang nam", "广南省"),
            Map.entry("binh thuan", "平顺省"), Map.entry("long an", "隆安省"),
            Map.entry("metropolitan manila", "马尼拉大都会"),
            Map.entry("national capital region", "马尼拉大都会"),
            Map.entry("manila", "马尼拉大都会"),
            Map.entry("cebu", "宿务省"), Map.entry("davao del sur", "南达沃省"),
            Map.entry("davao city", "达沃市"), Map.entry("batangas", "八打雁省"),
            Map.entry("pampanga", "邦板牙省"), Map.entry("cavite", "甲米地省"),
            Map.entry("laguna", "拉古纳省"), Map.entry("rizal", "黎刹省"),
            Map.entry("bulacan", "布拉干省"), Map.entry("iloilo", "伊洛伊洛省"),
            Map.entry("negros occidental", "西内格罗斯省"), Map.entry("zambales", "三描礼士省"),
            Map.entry("quezon", "奎松省"), Map.entry("bohol", "保和省"),
            Map.entry("leyte", "莱特省"),
            Map.entry("jakarta", "雅加达首都特区"),
            Map.entry("west java", "西爪哇省"), Map.entry("central java", "中爪哇省"),
            Map.entry("east java", "东爪哇省"), Map.entry("banten", "万丹省"),
            Map.entry("bali", "巴厘省"), Map.entry("north sumatra", "北苏门答腊省"),
            Map.entry("south sumatra", "南苏门答腊省"), Map.entry("riau", "廖内省"),
            Map.entry("riau islands", "廖内群岛省"), Map.entry("west kalimantan", "西加里曼丹省"),
            Map.entry("east kalimantan", "东加里曼丹省"), Map.entry("south kalimantan", "南加里曼丹省"),
            Map.entry("south sulawesi", "南苏拉威西省"), Map.entry("yogyakarta", "日惹特区"),
            Map.entry("phnom penh", "金边"), Map.entry("siem reap", "暹粒"),
            Map.entry("sihanoukville", "西哈努克"), Map.entry("yangon", "仰光省"),
            Map.entry("mandalay", "曼德勒省"), Map.entry("shan", "掸邦"),
            Map.entry("vientiane", "万象"),
            // —— 南亚 / 西亚 ——
            Map.entry("delhi", "德里"), Map.entry("maharashtra", "马哈拉施特拉邦"),
            Map.entry("karnataka", "卡纳塔克邦"), Map.entry("tamil nadu", "泰米尔纳德邦"),
            Map.entry("uttar pradesh", "北方邦"), Map.entry("gujarat", "古吉拉特邦"),
            Map.entry("west bengal", "西孟加拉邦"), Map.entry("telangana", "特伦甘纳邦"),
            Map.entry("andhra pradesh", "安得拉邦"), Map.entry("kerala", "喀拉拉邦"),
            Map.entry("rajasthan", "拉贾斯坦邦"), Map.entry("madhya pradesh", "中央邦"),
            Map.entry("punjab", "旁遮普邦"), Map.entry("haryana", "哈里亚纳邦"),
            Map.entry("bihar", "比哈尔邦"), Map.entry("odisha", "奥里萨邦"),
            Map.entry("orissa", "奥里萨邦"), Map.entry("assam", "阿萨姆邦"),
            Map.entry("goa", "果阿邦"), Map.entry("jharkhand", "贾坎德邦"),
            Map.entry("chhattisgarh", "恰蒂斯加尔邦"), Map.entry("uttarakhand", "北阿坎德邦"),
            Map.entry("abu dhabi", "阿布扎比酋长国"), Map.entry("dubai", "迪拜酋长国"),
            Map.entry("sharjah", "沙迦酋长国"), Map.entry("ajman", "阿治曼酋长国"),
            Map.entry("umm al quwain", "乌姆盖万酋长国"), Map.entry("ras al khaimah", "拉斯海马酋长国"),
            Map.entry("fujairah", "富查伊拉酋长国"),
            Map.entry("jerusalem", "耶路撒冷区"), Map.entry("tel aviv", "特拉维夫区"),
            Map.entry("haifa", "海法区"), Map.entry("central district", "中央区"),
            Map.entry("southern district", "南部区"), Map.entry("northern district", "北部区"),
            Map.entry("riyadh", "利雅得省"), Map.entry("makkah", "麦加省"),
            Map.entry("mecca", "麦加省"), Map.entry("medina", "麦地那省"),
            Map.entry("eastern province", "东部省"), Map.entry("asir", "阿西尔省"),
            Map.entry("tabuk", "塔布克省"), Map.entry("qassim", "卡西姆省"),
            Map.entry("amman", "安曼省"), Map.entry("tehran", "德黑兰省"),
            Map.entry("istanbul", "伊斯坦布尔省"), Map.entry("ankara", "安卡拉省"),
            Map.entry("izmir", "伊兹密尔省"), Map.entry("antalya", "安塔利亚省"),
            Map.entry("bursa", "布尔萨省"), Map.entry("adana", "阿达纳省"),
            Map.entry("gaziantep", "加济安泰普省"), Map.entry("konya", "科尼亚省"),
            Map.entry("kayseri", "开塞利省"), Map.entry("mugla", "穆拉省"),
            Map.entry("denizli", "代尼兹利省"), Map.entry("mersin", "梅尔辛省"),
            Map.entry("trabzon", "特拉布宗省"), Map.entry("diyarbakir", "迪亚巴克尔省"),
            Map.entry("kocaeli", "科贾埃利省"), Map.entry("sakarya", "萨卡里亚省"),
            // —— 英国 / 爱尔兰 ——
            Map.entry("england", "英格兰"), Map.entry("scotland", "苏格兰"),
            Map.entry("wales", "威尔士"), Map.entry("northern ireland", "北爱尔兰"),
            Map.entry("greater london", "大伦敦"), Map.entry("london", "伦敦"),
            Map.entry("greater manchester", "大曼彻斯特"), Map.entry("west midlands", "西米德兰兹"),
            Map.entry("merseyside", "默西塞德"), Map.entry("west yorkshire", "西约克郡"),
            Map.entry("south yorkshire", "南约克郡"), Map.entry("kent", "肯特郡"),
            Map.entry("essex", "埃塞克斯郡"), Map.entry("hampshire", "汉普郡"),
            Map.entry("surrey", "萨里郡"), Map.entry("lancashire", "兰开夏郡"),
            Map.entry("hertfordshire", "赫特福德郡"), Map.entry("berkshire", "伯克郡"),
            Map.entry("devon", "德文郡"), Map.entry("cornwall", "康沃尔郡"),
            Map.entry("oxfordshire", "牛津郡"), Map.entry("cambridgeshire", "剑桥郡"),
            Map.entry("norfolk", "诺福克郡"), Map.entry("suffolk", "萨福克郡"),
            Map.entry("nottinghamshire", "诺丁汉郡"), Map.entry("derbyshire", "德比郡"),
            Map.entry("staffordshire", "斯塔福德郡"), Map.entry("west sussex", "西萨塞克斯郡"),
            Map.entry("east sussex", "东萨塞克斯郡"), Map.entry("gloucestershire", "格洛斯特郡"),
            Map.entry("bristol", "布里斯托尔"), Map.entry("edinburgh", "爱丁堡"),
            Map.entry("glasgow", "格拉斯哥"), Map.entry("cardiff", "加的夫"),
            Map.entry("belfast", "贝尔法斯特"),
            Map.entry("dublin", "都柏林郡"), Map.entry("cork", "科克郡"),
            Map.entry("galway", "戈尔韦郡"), Map.entry("limerick", "利默里克郡"),
            Map.entry("waterford", "沃特福德郡"), Map.entry("wexford", "韦克斯福德郡"),
            Map.entry("meath", "米斯郡"), Map.entry("kildare", "基尔代尔郡"),
            Map.entry("wicklow", "威克洛郡"), Map.entry("donegal", "多尼戈尔郡"),
            Map.entry("kerry", "凯里郡"), Map.entry("mayo", "梅奥郡"),
            Map.entry("clare", "克莱尔郡"), Map.entry("tipperary", "蒂珀雷里郡"),
            Map.entry("louth", "劳斯郡"), Map.entry("leinster", "伦斯特省"),
            Map.entry("munster", "芒斯特省"), Map.entry("connacht", "康诺特省"),
            Map.entry("ulster", "阿尔斯特省"),
            // —— 德国 ——
            Map.entry("baden wurttemberg", "巴登-符腾堡州"), Map.entry("bavaria", "巴伐利亚州"),
            Map.entry("bayern", "巴伐利亚州"), Map.entry("berlin", "柏林州"),
            Map.entry("brandenburg", "勃兰登堡州"), Map.entry("bremen", "不来梅州"),
            Map.entry("hamburg", "汉堡州"), Map.entry("hesse", "黑森州"),
            Map.entry("hessen", "黑森州"), Map.entry("mecklenburg vorpommern", "梅克伦堡-前波美拉尼亚州"),
            Map.entry("lower saxony", "下萨克森州"), Map.entry("niedersachsen", "下萨克森州"),
            Map.entry("north rhine westphalia", "北莱茵-威斯特法伦州"),
            Map.entry("rhineland palatinate", "莱茵兰-普法尔茨州"),
            Map.entry("rheinland pfalz", "莱茵兰-普法尔茨州"), Map.entry("saarland", "萨尔州"),
            Map.entry("saxony", "萨克森州"), Map.entry("sachsen", "萨克森州"),
            Map.entry("saxony anhalt", "萨克森-安哈尔特州"),
            Map.entry("schleswig holstein", "石勒苏益格-荷尔斯泰因州"),
            Map.entry("thuringia", "图林根州"), Map.entry("thuringen", "图林根州"),
            // —— 法国 ——
            Map.entry("ile de france", "法兰西岛大区"),
            Map.entry("auvergne rhone alpes", "奥弗涅-罗讷-阿尔卑斯大区"),
            Map.entry("hauts de france", "上法兰西大区"), Map.entry("grand est", "大东部大区"),
            Map.entry("occitanie", "奥克西塔尼大区"), Map.entry("nouvelle aquitaine", "新阿基坦大区"),
            Map.entry("normandy", "诺曼底大区"), Map.entry("normandie", "诺曼底大区"),
            Map.entry("brittany", "布列塔尼大区"), Map.entry("bretagne", "布列塔尼大区"),
            Map.entry("pays de la loire", "卢瓦尔河地区大区"),
            Map.entry("centre val de loire", "中央-卢瓦尔河谷大区"),
            Map.entry("bourgogne franche comte", "勃艮第-弗朗什-孔泰大区"),
            Map.entry("provence alpes cote d azur", "普罗旺斯-阿尔卑斯-蓝色海岸大区"),
            Map.entry("corsica", "科西嘉大区"), Map.entry("corse", "科西嘉大区"),
            Map.entry("reunion", "留尼汪"), Map.entry("la reunion", "留尼汪"),
            Map.entry("guadeloupe", "瓜德罗普"), Map.entry("martinique", "马提尼克"),
            Map.entry("french guiana", "法属圭亚那"), Map.entry("guyane", "法属圭亚那"),
            Map.entry("mayotte", "马约特"),
            // —— 荷兰 / 比利时 / 瑞士 / 奥地利 ——
            Map.entry("north holland", "北荷兰省"), Map.entry("south holland", "南荷兰省"),
            Map.entry("utrecht", "乌得勒支省"), Map.entry("gelderland", "海尔德兰省"),
            Map.entry("north brabant", "北布拉班特省"), Map.entry("limburg", "林堡省"),
            Map.entry("overijssel", "上艾瑟尔省"), Map.entry("flevoland", "弗莱福兰省"),
            Map.entry("zeeland", "泽兰省"), Map.entry("drenthe", "德伦特省"),
            Map.entry("friesland", "弗里斯兰省"), Map.entry("groningen", "格罗宁根省"),
            Map.entry("antwerp", "安特卫普省"), Map.entry("antwerpen", "安特卫普省"),
            Map.entry("east flanders", "东佛兰德省"), Map.entry("oost vlaanderen", "东佛兰德省"),
            Map.entry("west flanders", "西佛兰德省"), Map.entry("west vlaanderen", "西佛兰德省"),
            Map.entry("flemish brabant", "弗拉芒布拉班特省"),
            Map.entry("vlaams brabant", "弗拉芒布拉班特省"),
            Map.entry("walloon brabant", "瓦隆布拉班特省"),
            Map.entry("hainaut", "埃诺省"), Map.entry("henegouwen", "埃诺省"),
            Map.entry("liege", "列日省"), Map.entry("luik", "列日省"),
            Map.entry("luxembourg", "卢森堡省"), Map.entry("namur", "那慕尔省"),
            Map.entry("brussels", "布鲁塞尔首都大区"),
            Map.entry("brussels capital region", "布鲁塞尔首都大区"),
            Map.entry("flanders", "弗拉芒大区"), Map.entry("wallonia", "瓦隆大区"),
            Map.entry("zurich", "苏黎世州"), Map.entry("geneva", "日内瓦州"),
            Map.entry("basel stadt", "巴塞尔城市州"), Map.entry("basel landschaft", "巴塞尔乡村州"),
            Map.entry("bern", "伯尔尼州"), Map.entry("lucerne", "卢塞恩州"),
            Map.entry("vaud", "沃州"), Map.entry("valais", "瓦莱州"),
            Map.entry("ticino", "提契诺州"), Map.entry("st gallen", "圣加仑州"),
            Map.entry("aargau", "阿尔高州"), Map.entry("thurgau", "图尔高州"),
            Map.entry("solothurn", "索洛图恩州"), Map.entry("schaffhausen", "沙夫豪森州"),
            Map.entry("graubunden", "格劳宾登州"), Map.entry("neuchatel", "纳沙泰尔州"),
            Map.entry("fribourg", "弗里堡州"), Map.entry("zug", "楚格州"),
            Map.entry("schwyz", "施维茨州"), Map.entry("obwalden", "上瓦尔登州"),
            Map.entry("nidwalden", "下瓦尔登州"), Map.entry("glarus", "格拉鲁斯州"),
            Map.entry("appenzell innerrhoden", "内阿彭策尔州"),
            Map.entry("appenzell ausserrhoden", "外阿彭策尔州"),
            Map.entry("uri", "乌里州"), Map.entry("jura", "汝拉州"),
            Map.entry("vienna", "维也纳州"), Map.entry("wien", "维也纳州"),
            Map.entry("upper austria", "上奥地利州"), Map.entry("lower austria", "下奥地利州"),
            Map.entry("styria", "施泰尔马克州"), Map.entry("steiermark", "施泰尔马克州"),
            Map.entry("tyrol", "蒂罗尔州"), Map.entry("tirol", "蒂罗尔州"),
            Map.entry("carinthia", "克恩顿州"), Map.entry("karnten", "克恩顿州"),
            Map.entry("salzburg", "萨尔茨堡州"), Map.entry("vorarlberg", "福拉尔贝格州"),
            Map.entry("burgenland", "布尔根兰州"),
            // —— 北欧 ——
            Map.entry("stockholm", "斯德哥尔摩省"), Map.entry("uppsala", "乌普萨拉省"),
            Map.entry("sodermanland", "南曼兰省"), Map.entry("ostergotland", "东约特兰省"),
            Map.entry("jonkoping", "延雪平省"), Map.entry("kronoberg", "克鲁努贝里省"),
            Map.entry("kalmar", "卡尔马省"), Map.entry("gotland", "哥特兰省"),
            Map.entry("blekinge", "布莱金厄省"), Map.entry("skane", "斯科讷省"),
            Map.entry("halland", "哈兰省"), Map.entry("vastra gotaland", "西约塔兰省"),
            Map.entry("varmland", "韦姆兰省"), Map.entry("orebro", "厄勒布鲁省"),
            Map.entry("vastmanland", "西曼兰省"), Map.entry("dalarna", "达拉纳省"),
            Map.entry("gavleborg", "耶夫勒堡省"), Map.entry("vasternorrland", "西诺尔兰省"),
            Map.entry("jamtland", "耶姆特兰省"), Map.entry("vasterbotten", "西博滕省"),
            Map.entry("norrbotten", "北博滕省"),
            Map.entry("oslo", "奥斯陆"), Map.entry("viken", "维肯郡"),
            Map.entry("innlandet", "内陆郡"), Map.entry("vestfold og telemark", "西福尔-泰勒马克郡"),
            Map.entry("agder", "阿格德尔郡"), Map.entry("rogaland", "罗加兰郡"),
            Map.entry("vestland", "韦斯特兰郡"), Map.entry("more og romsdal", "默勒-鲁姆斯达尔郡"),
            Map.entry("trondelag", "特伦德拉格郡"), Map.entry("nordland", "诺尔兰郡"),
            Map.entry("troms", "特罗姆斯郡"), Map.entry("finnmark", "芬马克郡"),
            Map.entry("uusimaa", "新地区"), Map.entry("southwest finland", "西南芬兰区"),
            Map.entry("varsinais suomi", "西南芬兰区"), Map.entry("pirkanmaa", "皮尔卡区"),
            Map.entry("north ostrobothnia", "北博滕区"),
            Map.entry("central finland", "中芬兰区"), Map.entry("lapland", "拉普兰区"),
            Map.entry("lappi", "拉普兰区"), Map.entry("satakunta", "萨塔昆塔区"),
            Map.entry("kymenlaakso", "屈米河谷区"), Map.entry("north karelia", "北卡累利阿区"),
            Map.entry("south karelia", "南卡累利阿区"), Map.entry("paijat hame", "派亚特海梅区"),
            Map.entry("kanta hame", "坎塔海梅区"), Map.entry("south savo", "南萨沃区"),
            Map.entry("north savo", "北萨沃区"), Map.entry("ostrobothnia", "博滕区"),
            Map.entry("central ostrobothnia", "中博滕区"),
            Map.entry("south ostrobothnia", "南博滕区"), Map.entry("kainuu", "凯努区"),
            Map.entry("aland", "奥兰群岛"), Map.entry("capital region", "首都大区"),
            Map.entry("hovedstaden", "首都大区"), Map.entry("central denmark", "中日德兰大区"),
            Map.entry("midtjylland", "中日德兰大区"), Map.entry("south denmark", "南丹麦大区"),
            Map.entry("syddanmark", "南丹麦大区"), Map.entry("north denmark", "北日德兰大区"),
            Map.entry("nordjylland", "北日德兰大区"), Map.entry("zealand", "西兰大区"),
            Map.entry("sjaelland", "西兰大区"),
            // —— 南欧 ——
            Map.entry("madrid", "马德里自治区"), Map.entry("catalonia", "加泰罗尼亚自治区"),
            Map.entry("cataluna", "加泰罗尼亚自治区"), Map.entry("andalusia", "安达卢西亚自治区"),
            Map.entry("andalucia", "安达卢西亚自治区"), Map.entry("valencia", "瓦伦西亚自治区"),
            Map.entry("comunidad valenciana", "瓦伦西亚自治区"),
            Map.entry("galicia", "加利西亚自治区"),
            Map.entry("castile and leon", "卡斯蒂利亚-莱昂自治区"),
            Map.entry("castilla y leon", "卡斯蒂利亚-莱昂自治区"),
            Map.entry("basque country", "巴斯克自治区"), Map.entry("pais vasco", "巴斯克自治区"),
            Map.entry("aragon", "阿拉贡自治区"), Map.entry("murcia", "穆尔西亚自治区"),
            Map.entry("asturias", "阿斯图里亚斯自治区"), Map.entry("navarre", "纳瓦拉自治区"),
            Map.entry("navarra", "纳瓦拉自治区"), Map.entry("cantabria", "坎塔布里亚自治区"),
            Map.entry("la rioja", "拉里奥哈自治区"), Map.entry("extremadura", "埃斯特雷马杜拉自治区"),
            Map.entry("castile la mancha", "卡斯蒂利亚-拉曼恰自治区"),
            Map.entry("castilla la mancha", "卡斯蒂利亚-拉曼恰自治区"),
            Map.entry("canary islands", "加那利群岛自治区"), Map.entry("canarias", "加那利群岛自治区"),
            Map.entry("balearic islands", "巴利阿里群岛自治区"),
            Map.entry("baleares", "巴利阿里群岛自治区"),
            Map.entry("ceuta", "休达"), Map.entry("melilla", "梅利利亚"),
            Map.entry("lombardy", "伦巴第大区"), Map.entry("lombardia", "伦巴第大区"),
            Map.entry("lazio", "拉齐奥大区"), Map.entry("campania", "坎帕尼亚大区"),
            Map.entry("sicily", "西西里大区"), Map.entry("sicilia", "西西里大区"),
            Map.entry("veneto", "威尼托大区"), Map.entry("piedmont", "皮埃蒙特大区"),
            Map.entry("piemonte", "皮埃蒙特大区"), Map.entry("emilia romagna", "艾米利亚-罗马涅大区"),
            Map.entry("apulia", "普利亚大区"), Map.entry("puglia", "普利亚大区"),
            Map.entry("tuscany", "托斯卡纳大区"), Map.entry("toscana", "托斯卡纳大区"),
            Map.entry("calabria", "卡拉布里亚大区"), Map.entry("sardinia", "撒丁大区"),
            Map.entry("sardegna", "撒丁大区"), Map.entry("liguria", "利古里亚大区"),
            Map.entry("marche", "马尔凯大区"), Map.entry("abruzzo", "阿布鲁佐大区"),
            Map.entry("umbria", "翁布里亚大区"),
            Map.entry("friuli venezia giulia", "弗留利-威尼斯朱利亚大区"),
            Map.entry("trentino south tyrol", "特伦蒂诺-上阿迪杰大区"),
            Map.entry("bolzano", "博尔扎诺自治省"), Map.entry("south tyrol", "博尔扎诺自治省"),
            Map.entry("trento", "特伦托自治省"), Map.entry("basilicata", "巴斯利卡塔大区"),
            Map.entry("molise", "莫利塞大区"), Map.entry("aosta valley", "瓦莱达奥斯塔大区"),
            Map.entry("lisbon", "里斯本区"), Map.entry("lisboa", "里斯本区"),
            Map.entry("porto", "波尔图区"), Map.entry("braga", "布拉加区"),
            Map.entry("faro", "法鲁区"), Map.entry("aveiro", "阿威罗区"),
            Map.entry("coimbra", "科英布拉区"), Map.entry("leiria", "莱里亚区"),
            Map.entry("setubal", "塞图巴尔区"), Map.entry("santarem", "圣塔伦区"),
            Map.entry("beja", "贝雅区"), Map.entry("evora", "埃武拉区"),
            Map.entry("viseu", "维塞乌区"), Map.entry("guarda", "瓜尔达区"),
            Map.entry("braganca", "布拉干萨区"), Map.entry("viana do castelo", "维亚纳堡区"),
            Map.entry("vila real", "雷阿尔城区"), Map.entry("portalegre", "波塔莱格雷区"),
            Map.entry("castelo branco", "布朗库堡区"), Map.entry("azores", "亚速尔自治区"),
            Map.entry("acores", "亚速尔自治区"), Map.entry("madeira", "马德拉自治区"),
            Map.entry("attica", "阿提卡大区"), Map.entry("central macedonia", "中马其顿大区"),
            Map.entry("thessaloniki", "塞萨洛尼基"), Map.entry("budapest", "布达佩斯"),
            // —— 中东欧 ——
            Map.entry("prague", "布拉格"), Map.entry("praha", "布拉格"),
            Map.entry("central bohemian", "中波希米亚州"),
            Map.entry("south bohemian", "南波希米亚州"), Map.entry("plzen", "比尔森州"),
            Map.entry("karlovy vary", "卡罗维发利州"), Map.entry("usti nad labem", "乌斯季州"),
            Map.entry("liberec", "利贝雷茨州"), Map.entry("hradec kralove", "赫拉德茨-克拉洛韦州"),
            Map.entry("pardubice", "帕尔杜比采州"), Map.entry("vysocina", "维索基纳州"),
            Map.entry("south moravian", "南摩拉维亚州"), Map.entry("olomouc", "奥洛穆茨州"),
            Map.entry("zlin", "兹林州"), Map.entry("moravian silesian", "摩拉维亚-西里西亚州"),
            Map.entry("mazovia", "马佐夫舍省"), Map.entry("mazowieckie", "马佐夫舍省"),
            Map.entry("lesser poland", "小波兰省"), Map.entry("malopolskie", "小波兰省"),
            Map.entry("silesia", "西里西亚省"), Map.entry("silesian", "西里西亚省"),
            Map.entry("slaskie", "西里西亚省"), Map.entry("greater poland", "大波兰省"),
            Map.entry("wielkopolskie", "大波兰省"), Map.entry("lower silesia", "下西里西亚省"),
            Map.entry("dolnoslaskie", "下西里西亚省"), Map.entry("lodz", "罗兹省"),
            Map.entry("lodzkie", "罗兹省"), Map.entry("pomerania", "滨海省"),
            Map.entry("pomorskie", "滨海省"), Map.entry("west pomerania", "西波美拉尼亚省"),
            Map.entry("zachodniopomorskie", "西波美拉尼亚省"), Map.entry("lublin", "卢布林省"),
            Map.entry("lubelskie", "卢布林省"), Map.entry("lubusz", "卢布斯卡省"),
            Map.entry("lubuskie", "卢布斯卡省"),
            Map.entry("kuyavian pomerania", "库亚维-波美拉尼亚省"),
            Map.entry("kujawsko pomorskie", "库亚维-波美拉尼亚省"),
            Map.entry("warmia masuria", "瓦尔米亚-马祖里省"),
            Map.entry("warminsko mazurskie", "瓦尔米亚-马祖里省"),
            Map.entry("swietokrzyskie", "圣十字省"), Map.entry("holy cross", "圣十字省"),
            Map.entry("podlaskie", "波德拉谢省"), Map.entry("podlachia", "波德拉谢省"),
            Map.entry("opole", "奥波莱省"), Map.entry("opolskie", "奥波莱省"),
            Map.entry("bucharest", "布加勒斯特"), Map.entry("cluj", "克卢日县"),
            Map.entry("timis", "蒂米什县"), Map.entry("iasi", "雅西县"),
            Map.entry("constanta", "康斯坦察县"), Map.entry("brasov", "布拉索夫县"),
            Map.entry("prahova", "普拉霍瓦县"), Map.entry("dolj", "多尔日县"),
            Map.entry("arad", "阿拉德县"), Map.entry("sibiu", "锡比乌县"),
            Map.entry("bacau", "巴克乌县"), Map.entry("galati", "加拉茨县"),
            Map.entry("mures", "穆列什县"), Map.entry("ilfov", "伊尔福夫县"),
            Map.entry("arges", "阿尔杰什县"),
            // —— 俄罗斯 / 乌克兰 ——
            Map.entry("moscow", "莫斯科市"), Map.entry("saint petersburg", "圣彼得堡市"),
            Map.entry("st petersburg", "圣彼得堡市"), Map.entry("moscow oblast", "莫斯科州"),
            Map.entry("leningrad oblast", "列宁格勒州"),
            Map.entry("novosibirsk oblast", "新西伯利亚州"),
            Map.entry("sverdlovsk oblast", "斯维尔德洛夫斯克州"),
            Map.entry("krasnodar", "克拉斯诺达尔边疆区"),
            Map.entry("tatarstan", "鞑靼斯坦共和国"), Map.entry("bashkortostan", "巴什科尔托斯坦共和国"),
            Map.entry("chelyabinsk", "车里雅宾斯克州"), Map.entry("krasnoyarsk", "克拉斯诺亚尔斯克边疆区"),
            Map.entry("primorsky", "滨海边疆区"), Map.entry("irkutsk", "伊尔库茨克州"),
            Map.entry("kaliningrad", "加里宁格勒州"), Map.entry("sakhalin", "萨哈林州"),
            Map.entry("tyumen", "秋明州"), Map.entry("nizhny novgorod", "下诺夫哥罗德州"),
            Map.entry("samara", "萨马拉州"), Map.entry("rostov", "罗斯托夫州"),
            Map.entry("voronezh", "沃罗涅日州"), Map.entry("murmansk", "摩尔曼斯克州"),
            Map.entry("amur", "阿穆尔州"), Map.entry("khabarovsk", "哈巴罗夫斯克边疆区"),
            Map.entry("stavropol", "斯塔夫罗波尔边疆区"), Map.entry("perm", "彼尔姆边疆区"),
            Map.entry("omsk", "鄂木斯克州"), Map.entry("tomsk", "托木斯克州"),
            Map.entry("ulyanovsk", "乌里扬诺夫斯克州"), Map.entry("saratov", "萨拉托夫州"),
            Map.entry("volgograd", "伏尔加格勒州"), Map.entry("buryatia", "布里亚特共和国"),
            Map.entry("sakha", "萨哈共和国"), Map.entry("yakutia", "萨哈共和国"),
            Map.entry("karelia", "卡累利阿共和国"),
            Map.entry("kyiv", "基辅"), Map.entry("kiev", "基辅"),
            Map.entry("kharkiv", "哈尔科夫州"), Map.entry("kharkov", "哈尔科夫州"),
            Map.entry("odesa", "敖德萨州"), Map.entry("odessa", "敖德萨州"),
            Map.entry("dnipropetrovsk", "第聂伯罗彼得罗夫斯克州"),
            Map.entry("lviv", "利沃夫州"), Map.entry("lvov", "利沃夫州"),
            Map.entry("zaporizhzhia", "扎波罗热州"), Map.entry("zaporizhia", "扎波罗热州"),
            Map.entry("donetsk", "顿涅茨克州"), Map.entry("luhansk", "卢甘斯克州"),
            Map.entry("mykolaiv", "尼古拉耶夫州"), Map.entry("kherson", "赫尔松州"),
            Map.entry("poltava", "波尔塔瓦州"), Map.entry("vinnytsia", "文尼察州"),
            Map.entry("chernivtsi", "切尔诺夫策州"), Map.entry("zakarpattia", "外喀尔巴阡州"),
            Map.entry("ivano frankivsk", "伊万诺-弗兰科夫斯克州"),
            Map.entry("ternopil", "捷尔诺波尔州"), Map.entry("rivne", "罗夫诺州"),
            Map.entry("volyn", "沃伦州"), Map.entry("zhytomyr", "日托米尔州"),
            Map.entry("cherkasy", "切尔卡瑟州"), Map.entry("kirovohrad", "基洛沃格勒州"),
            Map.entry("sumy", "苏梅州"), Map.entry("chernihiv", "切尔尼戈夫州"),
            // —— 美国 ——
            Map.entry("alabama", "阿拉巴马州"), Map.entry("alaska", "阿拉斯加州"),
            Map.entry("arizona", "亚利桑那州"), Map.entry("arkansas", "阿肯色州"),
            Map.entry("california", "加利福尼亚州"), Map.entry("colorado", "科罗拉多州"),
            Map.entry("connecticut", "康涅狄格州"), Map.entry("delaware", "特拉华州"),
            Map.entry("florida", "佛罗里达州"), Map.entry("georgia", "佐治亚州"),
            Map.entry("hawaii", "夏威夷州"), Map.entry("idaho", "爱达荷州"),
            Map.entry("illinois", "伊利诺伊州"), Map.entry("indiana", "印第安纳州"),
            Map.entry("iowa", "艾奥瓦州"), Map.entry("kansas", "堪萨斯州"),
            Map.entry("kentucky", "肯塔基州"), Map.entry("louisiana", "路易斯安那州"),
            Map.entry("maine", "缅因州"), Map.entry("maryland", "马里兰州"),
            Map.entry("massachusetts", "马萨诸塞州"), Map.entry("michigan", "密歇根州"),
            Map.entry("minnesota", "明尼苏达州"), Map.entry("mississippi", "密西西比州"),
            Map.entry("missouri", "密苏里州"), Map.entry("montana", "蒙大拿州"),
            Map.entry("nebraska", "内布拉斯加州"), Map.entry("nevada", "内华达州"),
            Map.entry("new hampshire", "新罕布什尔州"), Map.entry("new jersey", "新泽西州"),
            Map.entry("new mexico", "新墨西哥州"), Map.entry("new york", "纽约州"),
            Map.entry("north carolina", "北卡罗来纳州"), Map.entry("north dakota", "北达科他州"),
            Map.entry("ohio", "俄亥俄州"), Map.entry("oklahoma", "俄克拉何马州"),
            Map.entry("oregon", "俄勒冈州"), Map.entry("pennsylvania", "宾夕法尼亚州"),
            Map.entry("rhode island", "罗得岛州"), Map.entry("south carolina", "南卡罗来纳州"),
            Map.entry("south dakota", "南达科他州"), Map.entry("tennessee", "田纳西州"),
            Map.entry("texas", "得克萨斯州"), Map.entry("utah", "犹他州"),
            Map.entry("vermont", "佛蒙特州"), Map.entry("virginia", "弗吉尼亚州"),
            Map.entry("washington", "华盛顿州"), Map.entry("west virginia", "西弗吉尼亚州"),
            Map.entry("wisconsin", "威斯康星州"), Map.entry("wyoming", "怀俄明州"),
            Map.entry("district of columbia", "哥伦比亚特区"),
            Map.entry("washington dc", "哥伦比亚特区"), Map.entry("puerto rico", "波多黎各"),
            // —— 加拿大 ——
            Map.entry("ontario", "安大略省"), Map.entry("quebec", "魁北克省"),
            Map.entry("british columbia", "不列颠哥伦比亚省"), Map.entry("alberta", "艾伯塔省"),
            Map.entry("saskatchewan", "萨斯喀彻温省"), Map.entry("manitoba", "曼尼托巴省"),
            Map.entry("nova scotia", "新斯科舍省"), Map.entry("new brunswick", "新不伦瑞克省"),
            Map.entry("newfoundland and labrador", "纽芬兰与拉布拉多省"),
            Map.entry("prince edward island", "爱德华王子岛省"), Map.entry("yukon", "育空地区"),
            Map.entry("northwest territories", "西北地区"), Map.entry("nunavut", "努纳武特地区"),
            // —— 大洋洲 ——
            Map.entry("new south wales", "新南威尔士州"), Map.entry("victoria", "维多利亚州"),
            Map.entry("queensland", "昆士兰州"), Map.entry("western australia", "西澳大利亚州"),
            Map.entry("south australia", "南澳大利亚州"), Map.entry("tasmania", "塔斯马尼亚州"),
            Map.entry("australian capital territory", "澳大利亚首都领地"),
            Map.entry("northern territory", "北领地"),
            Map.entry("auckland", "奥克兰大区"), Map.entry("wellington", "惠灵顿大区"),
            Map.entry("canterbury", "坎特伯雷大区"), Map.entry("otago", "奥塔哥大区"),
            Map.entry("waikato", "怀卡托大区"), Map.entry("bay of plenty", "丰盛湾大区"),
            Map.entry("northland", "北地大区"), Map.entry("taranaki", "塔拉纳基大区"),
            Map.entry("gisborne", "吉斯伯恩大区"), Map.entry("hawkes bay", "霍克斯湾大区"),
            Map.entry("manawatu whanganui", "马纳瓦图-旺加努伊大区"),
            Map.entry("marlborough", "马尔堡大区"), Map.entry("nelson", "尼尔森大区"),
            Map.entry("tasman", "塔斯曼大区"), Map.entry("southland", "南地大区"),
            Map.entry("west coast", "西海岸大区"),
            // —— 拉美 ——
            Map.entry("mexico city", "墨西哥城"), Map.entry("ciudad de mexico", "墨西哥城"),
            Map.entry("jalisco", "哈利斯科州"), Map.entry("nuevo leon", "新莱昂州"),
            Map.entry("state of mexico", "墨西哥州"), Map.entry("puebla", "普埃布拉州"),
            Map.entry("guanajuato", "瓜纳华托州"), Map.entry("chihuahua", "奇瓦瓦州"),
            Map.entry("baja california", "下加利福尼亚州"),
            Map.entry("baja california sur", "南下加利福尼亚州"),
            Map.entry("quintana roo", "金塔纳罗奥州"), Map.entry("yucatan", "尤卡坦州"),
            Map.entry("sonora", "索诺拉州"), Map.entry("veracruz", "韦拉克鲁斯州"),
            Map.entry("michoacan", "米却肯州"), Map.entry("oaxaca", "瓦哈卡州"),
            Map.entry("sinaloa", "锡那罗亚州"), Map.entry("tamaulipas", "塔毛利帕斯州"),
            Map.entry("coahuila", "科阿韦拉州"), Map.entry("queretaro", "克雷塔罗州"),
            Map.entry("sao paulo", "圣保罗州"), Map.entry("rio de janeiro", "里约热内卢州"),
            Map.entry("minas gerais", "米纳斯吉拉斯州"), Map.entry("bahia", "巴伊亚州"),
            Map.entry("parana", "帕拉纳州"), Map.entry("rio grande do sul", "南里奥格兰德州"),
            Map.entry("santa catarina", "圣卡塔琳娜州"), Map.entry("pernambuco", "伯南布哥州"),
            Map.entry("ceara", "塞阿拉州"), Map.entry("distrito federal", "联邦区"),
            Map.entry("federal district", "联邦区"), Map.entry("goias", "戈亚斯州"),
            Map.entry("amazonas", "亚马孙州"), Map.entry("para", "帕拉州"),
            Map.entry("espirito santo", "圣埃斯皮里图州"), Map.entry("maranhao", "马拉尼昂州"),
            Map.entry("mato grosso", "马托格罗索州"), Map.entry("mato grosso do sul", "南马托格罗索州"),
            Map.entry("paraiba", "帕拉伊巴州"), Map.entry("rio grande do norte", "北里奥格兰德州"),
            Map.entry("alagoas", "阿拉戈斯州"), Map.entry("sergipe", "塞尔希培州"),
            Map.entry("piaui", "皮奥伊州"), Map.entry("rondonia", "朗多尼亚州"),
            Map.entry("tocantins", "托坎廷斯州"), Map.entry("acre", "阿克里州"),
            Map.entry("amapa", "阿马帕州"), Map.entry("roraima", "罗赖马州"),
            Map.entry("buenos aires", "布宜诺斯艾利斯"),
            Map.entry("buenos aires province", "布宜诺斯艾利斯省"),
            Map.entry("cordoba", "科尔多瓦省"), Map.entry("santa fe", "圣菲省"),
            Map.entry("mendoza", "门多萨省"), Map.entry("tucuman", "图库曼省"),
            Map.entry("salta", "萨尔塔省"), Map.entry("entre rios", "恩特雷里奥斯省"),
            Map.entry("chaco", "查科省"), Map.entry("corrientes", "科连特斯省"),
            Map.entry("misiones", "米西奥内斯省"), Map.entry("neuquen", "内乌肯省"),
            Map.entry("rio negro", "内格罗河省"), Map.entry("chubut", "丘布特省"),
            Map.entry("santa cruz", "圣克鲁斯省"), Map.entry("tierra del fuego", "火地岛省"),
            Map.entry("san juan", "圣胡安省"), Map.entry("san luis", "圣路易斯省"),
            Map.entry("la pampa", "拉潘帕省"),
            Map.entry("santiago", "圣地亚哥首都大区"), Map.entry("valparaiso", "瓦尔帕莱索大区"),
            Map.entry("lima", "利马省"), Map.entry("bogota", "波哥大"),
            Map.entry("antioquia", "安蒂奥基亚省"),
            // —— 非洲 ——
            Map.entry("gauteng", "豪登省"), Map.entry("western cape", "西开普省"),
            Map.entry("eastern cape", "东开普省"), Map.entry("northern cape", "北开普省"),
            Map.entry("free state", "自由邦省"), Map.entry("kwazulu natal", "夸祖鲁-纳塔尔省"),
            Map.entry("north west", "西北省"), Map.entry("mpumalanga", "姆普马兰加省"),
            Map.entry("limpopo", "林波波省"),
            Map.entry("cairo", "开罗省"), Map.entry("alexandria", "亚历山大省"),
            Map.entry("giza", "吉萨省"), Map.entry("lagos", "拉各斯州"));

    /**
     * 行政区名的首尾通用词：源里常带（{@code State of Berlin} / {@code Västra Götaland County} /
     * {@code Wellington Region} / {@code Amman Governorate}），精确匹配不到时去掉再查一次。
     */
    private static final List<String> REGION_PREFIXES = List.of(
            "state of ", "city of ", "province of ", "region of ", "prefecture of ",
            "county of ", "municipality of ", "autonomous region of ");

    /** 行政区名的尾缀通用词，与 {@link #REGION_PREFIXES} 配合使用。 */
    private static final List<String> REGION_SUFFIXES = List.of(
            "county", "prefecture", "province", "state", "region", "district", "governorate",
            "voivodeship", "oblast", "krai", "okrug", "canton", "borough", "municipality",
            "department", "emirate", "lan");

    /** 国名：空码返回 null；ISO 两位码查本表短名，查不到交给 CLDR 中文名。 */
    public static String country(String code) {
        if (code == null || code.isBlank()) return null;
        String key = code.trim().toUpperCase(Locale.ROOT);
        String shortName = COUNTRIES.get(key);
        if (shortName != null) return shortName;
        return key.length() == 2 ? cldrCountry(key) : null;
    }

    /**
     * JDK 自带的 CLDR 中文国名兜底：ISO 3166-1 全表都有中文名，省得手工维护两百多条。
     *
     * <p>两种「查不到」要挡掉：表外的码 CLDR 原样回码（{@code XX} → {@code XX}），
     * 以及 CLDR 对 {@code ZZ} 之类的占位码回「未知地区」—— 那都不是国名，回 null 让界面显示占位符。</p>
     */
    private static String cldrCountry(String code) {
        String name = Locale.forLanguageTag("und-" + code).getDisplayCountry(Locale.SIMPLIFIED_CHINESE);
        if (name.isBlank() || name.equalsIgnoreCase(code) || name.equals("未知地区")) return null;
        return name;
    }

    /** 行政区名：空值返回 null，未收录返回 null（界面只显示国名，不输出英文原名）。 */
    public static String region(String raw) {
        if (raw == null || raw.isBlank()) return null;
        String key = normalize(raw);
        String mapped = REGIONS.get(key);
        if (mapped != null) return mapped;
        String loose = looseKey(key);
        if (!loose.equals(key)) {
            mapped = REGIONS.get(loose);
            if (mapped != null) return mapped;
        }
        return autonomous(key);
    }

    /**
     * 查表键：小写、去重音与符号、分隔符统一为空格、压缩连续空白。
     *
     * <p>探测源的名字带变音符与符号（{@code Västra Götaland}、{@code São Paulo}、{@code Hawke's Bay}、
     * {@code Washington, D.C.}），键统一成 ASCII 才不会因为一个字母变体查不到；
     * {@code ø} / {@code æ} / {@code ł} / {@code đ} 这类拆不出重音的字母单独折叠。</p>
     */
    private static String normalize(String raw) {
        String decomposed = Normalizer.normalize(raw, Normalizer.Form.NFD);
        return decomposed.replaceAll("\\p{M}+", "")
                .replace('ø', 'o').replace('Ø', 'o')
                .replace('æ', 'a').replace('Æ', 'a')
                .replace('ł', 'l').replace('Ł', 'l')
                .replace('đ', 'd').replace('Đ', 'd')
                .toLowerCase(Locale.ROOT)
                .replaceAll("['’]", "")
                .replaceAll("[.,]", " ")
                .replace('_', ' ')
                .replace('-', ' ')
                .replaceAll("\\s+", " ")
                .trim();
    }

    /** 去掉首尾行政通用词后的变体；没有可去掉的词时原样返回。 */
    private static String looseKey(String key) {
        for (String prefix : REGION_PREFIXES) {
            if (key.startsWith(prefix)) return key.substring(prefix.length());
        }
        for (String suffix : REGION_SUFFIXES) {
            if (key.endsWith(" " + suffix)) return key.substring(0, key.length() - suffix.length() - 1);
        }
        return key;
    }

    /** 自治区：ip-api 与 ipapi.co 的写法不一致（如 Nei Mongol / Inner Mongolia），按关键词兜底。 */
    private static String autonomous(String key) {
        if (key.contains("mongol")) return "内蒙古自治区";
        if (key.contains("uygur") || key.contains("uyghur")) return "新疆维吾尔自治区";
        if (key.contains("zhuang")) return "广西壮族自治区";
        if (key.contains("ningxia")) return "宁夏回族自治区";
        if (key.contains("tibet") || key.contains("xizang")) return "西藏自治区";
        return null;
    }
}
