package com.yiyiaddon.e.q.k;

import com.yiyiaddon.l.c.f;
import com.yiyiaddon.l.h.g;
import io.github.humbleui.skija.Canvas;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.CreativeModeTab.Type;

public final class d {
   private static final String zQ = (String)com.yiyiaddon.m.b.a<"s2l3tdqoiuv11c","NIFfyD5IAjTQJg2JMGNMHU5PRuJJd5Vk6bdJo+gsg3bvx9DURyD3Zw==",-1088753721726468164,-8482770658570642331,5821911970585176295,2119843534105857306>();
   private static final String zR = (String)com.yiyiaddon.m.b.a<"scexae5lj6rp","doak9IitvMzSOGnz6FHwRXuHIpKVv0ozaK5eH9Xgzx2m+OiJe6jNraNrJm0=",4478726558410809350,3848588860501926883,1727234481322183123,9035465164035612876>();
   private static final com.yiyiaddon.e.q.k.d.d a = new com.yiyiaddon.e.q.k.d.d(
      (String)com.yiyiaddon.m.b.a<"scexae5lj6rp","doak9IitvMzSOGnz6FHwRXuHIpKVv0ozaK5eH9Xgzx2m+OiJe6jNraNrJm0=",4478726558410809350,3848588860501926883,1727234481322183123,9035465164035612876>(),
      Integer.MAX_VALUE
   );
   private static final String zS = (String)com.yiyiaddon.m.b.a<"s30pqpcov2vxj4","t4VqBfLS6U3LzpUj3rJSwdxzpsk5bMGAhoxoQyfEAODcbCvF",-5476037581111258960,4907904618166886274,4874612451177050136,4964715848591301396>();
   private static final String zT = (String)com.yiyiaddon.m.b.a<"sru2fuxhira2u","1ZBnYzKMcMSR/hi6kXA1MULskbLpF9RxgQNLiQP/5WUljVgBtokLGOLXxdRmdmN7",3927573332956110728,-7841057822664457648,3819750824693366086,-3124621356339842761>();
   private static final String zU = (String)com.yiyiaddon.m.b.a<"s2y2yybwmbpvsg","FhcNHxRU8HxN8b3H8uyzvWhhuv+QlfPptMww1A4eYLQ4LGrONrtXA98R4WrQjLJRZljWWRcZkWU006xtCCTeX+ZUdPXMdTLSVwiTjQ==",-8993316565713955124,-4336733680417246135,6878206327599069860,-1969170591829242625>();
   private static final String zV = (String)com.yiyiaddon.m.b.a<"s1467vkbwoeckj","asnd4Zf1BZ1YnsFYFac+mBs5qvHecFKXsYjAiN2rTXxTdf28CwLmkMIHEqE=",4307687537950395150,6959132776827347328,4255440481468874476,-2763674329550136439>();
   private static final String zW = (String)com.yiyiaddon.m.b.a<"s21x46ca6f1x7x","5YD2hpnNOKaVvppf8psXeXPYNi0kA+85eom3TKZRGGJzrQ==",-8601879561544683687,8731369098304209478,4821102662451440609,8938368098912525920>();
   private static final String zX = (String)com.yiyiaddon.m.b.a<"s1p44v8g7p1kbw","CXi4T3vtXlYKxxhL5+t91bjRNZHEmolBGdQZll9o+mA=",-1144420701601805028,2540343598961320873,6375264100624769667,9026954272140835796>();
   private static final String zY = (String)com.yiyiaddon.m.b.a<"sycd24tvgztnu","quIWLqSfQtUJAMZRWbqvGuAXtRFNZ7Y/xZJ7EToC1Hz+nhSNetPPh9wN783fnM4r",-6692609522351558860,-536350516024254774,-8591746939640608513,-6279636559539276482>();
   private static final Collator e = Collator.getInstance(Locale.CHINA);
   private static final com.yiyiaddon.e.q.b.a b = new com.yiyiaddon.e.q.b.a();
   private static final List<com.yiyiaddon.e.q.k.d.b> cr = List.of(
      new com.yiyiaddon.e.q.k.d.b(
         (String)com.yiyiaddon.m.b.a<"s2hfmojd97pyuv","5jmiC+vQEHamdCQkp/+SFUr76NezfO3jbclZX6Hh4jwe9zo2",297775798036712348,-534204281189555822,-9186803454185876854,424892627013662963>(),
         List.of(
            (String)com.yiyiaddon.m.b.a<"s2tp35r0v7jnp3","ZJ/O7BxF4mykHn0Ov4t3I9BOjdWAFq1csV6Q79wcLO6bcFgkCI/ANPJ+QkuXoA==",7094784841335169857,904749305384762895,-6668203784216885055,-9050038915568208586>(),
            (String)com.yiyiaddon.m.b.a<"s2q0wgtzjp2p3l","Fj4W4CW8PvR4MYAYk8WUcZWT7Gejy0TnIl/qePGCNSA5/X/7g74=",-1206429860153447538,2434095593504105327,-4484904943687602200,3607329350938954194>(),
            (String)com.yiyiaddon.m.b.a<"s3n02npf3n45tu","cTKMe76SXm71igzn/aw16LeKwPblXmerPVayrMWjpUiin1IJvjwiUgKQpiNeu2KfeghKC4pO09XjPhh5nTo/GQ==",-7332528448352671451,898753752009223929,3089609860098011739,-1512049310860967363>(),
            (String)com.yiyiaddon.m.b.a<"s2fgle5fq2ak39","K11djEiL0ifWDS6wTHUJfgcFSdQHlWeSzQDFVdAZA+tVBlir95mR4h52zJzpSQ==",4535070462452081701,847989837314001195,5124329715259962621,7068619100778945425>(),
            (String)com.yiyiaddon.m.b.a<"sl4rv1lajctte","90ZuJ58ce30ePrjUiXqczgngSEvTKBsuKOBcfx4NBKr5khODOzN7PNunZBTMOQyTL1w=",1063051987456140866,6599129253912708366,7890207294688312300,5023270605414221820>(),
            (String)com.yiyiaddon.m.b.a<"s2imxgho3f9vm2","T2XUh6HTI0+VH3eQPdq2hEhDz8iN5afMcHdbBO53B1UUNjWy+oFZuUB2",-4980723503369927975,-617545708594417612,-8721904095522717589,-7221096993613640138>(),
            (String)com.yiyiaddon.m.b.a<"s3drofbyzmwsn7","ZkRhRJGzG/E6EEtaGRdM5iQVJ64ID9SlH/VjZPG1ys9jM8BJlJTqhx4g3Fp7Ev4kE11pe0Jo",5387265941276241476,-4024032254906177364,4745283394330062710,-3399721408110013901>()
         )
      ),
      new com.yiyiaddon.e.q.k.d.b(
         (String)com.yiyiaddon.m.b.a<"s3i7s627x4ak44","NX/HU8yTEp3PzGuKHk7hiGuZIF+G6yOCELAplHLualaPcRbM",4386005125669631803,7101707852326485113,-4991422046423380906,9207041126626195276>(),
         List.of(
            (String)com.yiyiaddon.m.b.a<"s2hpydmrffnsro","Fum8wQSXXeHK8M0RVZgujY7waISdmBVYZz5jc3KN6ykNlZ1E5Vv+XKRX4RQs1D73",-8903046325541932127,1100604501613298027,-5037122808757759325,3689583466487449352>(),
            (String)com.yiyiaddon.m.b.a<"s2274xnxrb8pd3","5aMN8wYNC4+NCiH35z+H7EI3yW551U4luXWX8EgN2HJxbf1TDKOnWPYKrQgcRWO6",-7136545798528142821,2986858367357138244,5795421790771096059,-3828870333094638717>(),
            (String)com.yiyiaddon.m.b.a<"s37i6sebxaaglt","IhST6gn13LuO6tWQvzHj3jm5IOhhAozJP+CCX595S14oJ+nZB02PsBbB",4195980263348663751,4403126892091865570,3829262339938113562,-2496825710874319446>()
         )
      ),
      new com.yiyiaddon.e.q.k.d.b(
         (String)com.yiyiaddon.m.b.a<"s6k8d63pbwl36","nNqNA7xV2aFTDqoyCnDpOr1m5J/DE0djPIDocxAm/hRmGT+W",8832257476351549121,8921698485817227875,-1358043316961746951,-7017612163178984843>(),
         List.of(
            (String)com.yiyiaddon.m.b.a<"sk8h8bq5vrjau","6yKwn7iLDzp34pCcvfgi7U4pWxgWkaxuUK4P5s01ojpmHAc86iQ=",3456056669880940380,-8534945004537365368,-5985952312624344567,7602595280124999329>(),
            (String)com.yiyiaddon.m.b.a<"s16psxdkvm04ro","r7fjOCEtOqxgUARugbYgTiyPI1qSjQxVj7LUtquZsWkjgpDUcbs=",-5752776303995950430,2588853849912093555,-961018597144987527,1950885882577248468>(),
            (String)com.yiyiaddon.m.b.a<"s1hsxout25swsb","8FzqSaBA/Pw5yKtUTQrUbj0EhTO5VugqkOhistBcIGHJyZ7LDrc=",-4669989243578426041,7770048039787429118,-3203208091910775052,-5349351848187958033>(),
            (String)com.yiyiaddon.m.b.a<"s54gwxpuyp5wv","FteN6ep2A8lHxxAJ2IZgyUORUhryRkqo1q8bwQn+N0dQ9/7Yx2jYc00Xpx0=",4190548492124823270,-6190324881675468632,-6265702114545827687,8424990225368081558>()
         )
      ),
      new com.yiyiaddon.e.q.k.d.b(
         (String)com.yiyiaddon.m.b.a<"s29v41uac6t8fy","VNHq6L10PlL4GUHN3G+9ZyM7ubwFu6uJfyNCuczOtDQ5Ag==",6459313122186836303,-2084686145038917858,-1744031957487501184,674162108724016614>(),
         List.of(
            (String)com.yiyiaddon.m.b.a<"s2th32utilqogb","4VBTYtE4MdLaTJHUGDgxoCfVTXKPs8WZ5YG9Wh3zuRxWTC5WN7jE4lzDRNo=",-7262114155168852010,6959793655113232313,4360400445770613452,8662370602588800020>(),
            (String)com.yiyiaddon.m.b.a<"s27hjpuyfq12ht","4yzJrvNMTAHdmvNmAHSp5sQ2iIwe+LgoC5DAbf/pKRzy0iD4LQOCHrpIexL/HQ==",7911457023940922828,8710696639190149746,537947327883792317,-3985777182838528163>(),
            (String)com.yiyiaddon.m.b.a<"sgwcughmcsxj2","DGcaURWNKKYTPS1ATqCQmJJ3WtfYPV/GeCyIi4/5KSTGxcjtnvfN7p7XKO+o18bXUjNEUw==",-6735447896224899415,4671262338485308615,89077643203884901,8130393809443520980>()
         )
      ),
      new com.yiyiaddon.e.q.k.d.b(
         (String)com.yiyiaddon.m.b.a<"s11de36glmz6l3","tzq+p8mgBVJakKgzUWdRudygZuBtVcOxXk0JRwtfTEKaVgUG",-417010658666804783,-1185576592122464307,6348315070893645345,9025603720404599070>(),
         List.of(
            (String)com.yiyiaddon.m.b.a<"s1n2qsm30umqlk","4G2w7L3KPhx3Jh9gVfckvfS2ff5c1qbeTqICVh6cIPX5G8UNqiTMqK02DumkwAIW",-2993552652490829772,-574631440111909502,5297475377811067332,1861499484797440951>(),
            (String)com.yiyiaddon.m.b.a<"s3agw8dlejojza","ijdLmFqGjljoznZXw7G9quntWd/z+ASlBpJA1L+FwN0yrKyi17DrwJDeA5O7/Rhi9nkwWXq896TLXw==",2005732614160908020,4804045335555914692,7913231298284618508,1126315542003276319>(),
            (String)com.yiyiaddon.m.b.a<"s2d6dkpzcajpgm","xurU+TbWym8+Bt0xk5+1bKPd64TSeWH14kaTxSePsFqb8VydFqqR3nZ0rPSEB2bjLCbZFsF38w5xHUDM",-1763055158382392041,5402155207745044796,3856056210560486977,3162538580150963250>(),
            (String)com.yiyiaddon.m.b.a<"sooyga7mi5zke","iSEkCQhLqUko2KBzGcCWGbNGK+3xL5IXcy6a2vApmSES8I9/BNx7GLdvCrIx8JX2GoU6EpX/hrIjfAXQtsin49CMUkAvfw==",570216270849550640,6831208647226879679,5368160502267457803,6333257533752044363>(),
            (String)com.yiyiaddon.m.b.a<"s2a8dw3tm5ydok","uCHYGNoPnu82jTrGLtVJYrJw5RaIdQ+EiPT3wX+6lipoM05sxkOQKKVXtJnWtiMrF4i5MK7kxFnICw==",4980226373871732337,-3815930267590109685,5090395267554387008,-1010201939588891745>(),
            (String)com.yiyiaddon.m.b.a<"s1bl0x3dpkwckg","J8U2ZZO/ghPkjdPubinRX0aDuiD5nNQuib8pZlwioo11xZRmP4t4mQ==",-4746316709895206716,2022175637450330832,1184625668849991862,4607235532132903300>(),
            (String)com.yiyiaddon.m.b.a<"s2mr11co4ydfap","WmRa3ws3xdmzw+h2z/VmcKktVPyqrAy/8dWb6DL3+RfV+87A8fXAgEQnPNkQWq9IZbY=",-3713912224277002469,5194919157583524261,-5666740167347468596,1250460125014670806>(),
            (String)com.yiyiaddon.m.b.a<"s1fb1dzixnvuod","NPtJv7I7MSUKw8qhWTrpHrja+uE1HRgRBRmgs2YABEYzRqKkQyV2DUGRUA4qmP9t8QRQZy9n",-629668769475482578,2035798344376441662,2453398976011341009,-4610221100826714576>(),
            (String)com.yiyiaddon.m.b.a<"swwa88rjzoitq","UB3PJmNHyCHYlgTmJ4sTyYUwRE4teVXlSwyjuGh4YTK66U1YR/s9UZ+KVw5QycbanZJ7gxW5",3653307815850806338,-4990700259394825946,7033482310108580189,-7476170725013896132>(),
            (String)com.yiyiaddon.m.b.a<"s3j45jcxs38zjz","j3MA9O5+0zEkCaQFCmWhhyXjSqlusYlEXxS5aJPgu2ooZJ8n3j9XamG5oOVpy2tDxRbcUQ==",1086751763907160601,-5137922383371368803,3163456922895037988,-8279410079781272075>()
         )
      ),
      new com.yiyiaddon.e.q.k.d.b(
         (String)com.yiyiaddon.m.b.a<"s3u6xlxrzg9pb3","uwRjfnPOhomRv3y33xXTXYjRbBF+nOr5Bb8fG3Anrzgynlqf",2949305527072246249,-548962295837310689,209996001941350519,8215106888087502761>(),
         List.of(
            (String)com.yiyiaddon.m.b.a<"s1w8z2hyrtpikj","qSFS+qqJPHG7aarsEBKy8U2b9JbueeBZu4B4dU4uV6p+3MmePa7DC6aHMJBX57ncLk6sPVPXlupUsg==",157744761506569149,7334646445196873991,-2483617048133450262,3079460650129172615>(),
            (String)com.yiyiaddon.m.b.a<"s1iye90j38de06","ZcMune4kCGJT5ZMibk8BCD6l6S+BcmcD808TlaXksndGvP6S",8321830914091548852,-6829358226973546542,8306400525480098253,-5708215256588194184>()
         )
      ),
      new com.yiyiaddon.e.q.k.d.b(
         (String)com.yiyiaddon.m.b.a<"s1m4tckx9gh73t","ipc0kuqOIFS+fVcffYiAV/ZacUJFpAVgeM5EJfbgO7qGEfBa",4076386777229818504,-4108787709654172397,5410219389159448365,-8085137514982196635>(),
         List.of(
            (String)com.yiyiaddon.m.b.a<"s307fk9kvra7dn","+0u8KhkOG6XLl792pjGy2TAh4e46rDGxyzGE/lmkYVknIuTX8qyID52Y",4263876369933184981,-6389701110699295009,-1740662039573840800,3338142560537469855>(),
            (String)com.yiyiaddon.m.b.a<"s30q7uvdjf17d","nD0XshxPicNz0QxcPr/zooHkp+y8bTqvnWLyNMCSl7qvfZsqTKunJneC8CGAkztv",4279979016263736628,6574098915597158322,1624067060629211981,783212812147288934>()
         )
      ),
      new com.yiyiaddon.e.q.k.d.b(
         (String)com.yiyiaddon.m.b.a<"s1gwa64ijyn5dd","rNKcOD1OsfksO+KrlFa5ehAnbgqhkAxG4bLzDemJ+uPfTds/aYk=",-1853848429486343058,2364358388824004493,-1177835203470022372,-9155467072863389491>(),
         List.of(
            (String)com.yiyiaddon.m.b.a<"s8zaqjjdqpdj3","pyEC8EtBPm8FbFQe9KHymxqQ1JbhfgVP95mcjx6Kfy8GTYqwJyGwsU6N",-8721661773995791905,-7911879818948981025,-3829349640925876309,-4505169536176913108>(),
            (String)com.yiyiaddon.m.b.a<"sxvjkhdo035xb","gLBSTgmopE3w2SDu146QIJxzUp7MLkwMO5eHtNpnFwku62K7ZvjeHg1FqoU=",804233820673225543,-6543539127889027494,-8109544094854114918,8607498400182146861>(),
            (String)com.yiyiaddon.m.b.a<"s3rpednw7q2bc2","/t079WGmpzU3lYB+PggYxe1n4inUoDskGULPyhg2gl72SuklXd/Sc6ua",5349579985090654649,6394754352018860273,7684152903690528338,-6126475191489245126>(),
            (String)com.yiyiaddon.m.b.a<"s1s8ci4ko3abi4","loKHa4TkRe8A1hYujDfqOwR7vgPYPLxx6CCipfMv9BN8/Gh4N3cuavCu9tSyXxVU",3406551181117828622,1683492238285215919,-7362499219077047131,-3583683314564674558>()
         )
      )
   );

   private d() {
   }

   public static f.b a(com.yiyiaddon.e.q.k.a.a var0, com.yiyiaddon.e.q.a var1, String var2) {
      String var3 = var2 + "";
      return f.b.a(
         var0,
         () -> var3,
         (String)com.yiyiaddon.m.b.a<"sru2fuxhira2u","1ZBnYzKMcMSR/hi6kXA1MULskbLpF9RxgQNLiQP/5WUljVgBtokLGOLXxdRmdmN7",3927573332956110728,-7841057822664457648,3819750824693366086,-3124621356339842761>(),
         () -> var1.a().i(var2).size() + "",
         List.of(
            new f.c(
               new com.yiyiaddon.l.j.a(
                  (String)com.yiyiaddon.m.b.a<"s30pqpcov2vxj4","t4VqBfLS6U3LzpUj3rJSwdxzpsk5bMGAhoxoQyfEAODcbCvF",-5476037581111258960,4907904618166886274,4874612451177050136,4964715848591301396>(),
                  () -> a(var0, var1, var2)
               ),
               var3 + ""
            ),
            f.b(() -> {
               var1.a().c(var2, new ArrayList<>(b.i(var2)));
               var1.L();
               var0.C();
            }, var3)
         )
      );
   }

   public static f.b a(com.yiyiaddon.e.q.k.a.a var0, com.yiyiaddon.e.q.a var1) {
      return f.b.a(
         var0,
         () -> (String)com.yiyiaddon.m.b.a<"s1467vkbwoeckj","asnd4Zf1BZ1YnsFYFac+mBs5qvHecFKXsYjAiN2rTXxTdf28CwLmkMIHEqE=",4307687537950395150,6959132776827347328,4255440481468874476,-2763674329550136439>(),
         (String)com.yiyiaddon.m.b.a<"s2y2yybwmbpvsg","FhcNHxRU8HxN8b3H8uyzvWhhuv+QlfPptMww1A4eYLQ4LGrONrtXA98R4WrQjLJRZljWWRcZkWU006xtCCTeX+ZUdPXMdTLSVwiTjQ==",-8993316565713955124,-4336733680417246135,6878206327599069860,-1969170591829242625>(),
         () -> var1.a().bp().size() + "",
         List.of(
            new f.c(
               new com.yiyiaddon.l.j.a(
                  (String)com.yiyiaddon.m.b.a<"s30pqpcov2vxj4","t4VqBfLS6U3LzpUj3rJSwdxzpsk5bMGAhoxoQyfEAODcbCvF",-5476037581111258960,4907904618166886274,4874612451177050136,4964715848591301396>(),
                  () -> a(var0, var1)
               ),
               (String)com.yiyiaddon.m.b.a<"s1pzm6anqwiwvt","/CFt58WzFUmIptZEVqYiFcyLY99zurHtSmqmZhYkmNBoiIEdvjLZBrevcaqu0xPCsXISxTUhfzN8FQ==",2498982466401721714,-8212452642321493005,8005765649125257093,5882614975584316892>()
            ),
            f.b(
               () -> {
                  var1.a().o(new ArrayList<>(b.bp()));
                  var1.L();
                  var0.C();
               },
               (String)com.yiyiaddon.m.b.a<"s1467vkbwoeckj","asnd4Zf1BZ1YnsFYFac+mBs5qvHecFKXsYjAiN2rTXxTdf28CwLmkMIHEqE=",4307687537950395150,6959132776827347328,4255440481468874476,-2763674329550136439>()
            )
         )
      );
   }

   private static void a(com.yiyiaddon.e.q.k.a.a var0, com.yiyiaddon.e.q.a var1, String var2) {
      VillagerProfession var3 = com.yiyiaddon.e.q.c.a.a(var2);
      if (var3 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s26v9pivx35yqi","uvPjbHXa48CZvfKS2l0Wu1DZ17nDKw5CWxuI/dgv10w=",6870192156452895795,5074455896872116500,-170111338474101133,4671213759116281061>()) {
            case -1928438330:
               return;
            default:
               throw null;
         }
      } else {
         Set var4 = com.yiyiaddon.e.q.c.a.a(var3);
         if (var4.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"sot0xetym2cp5","uk1YRNRAbqHE8I+BVWI6OoJXFUn46Qxc8fXZ/bjG86E=",6420476052547620667,4669958778003041900,-7191820440266814622,-5921852690867169369>()) {
               case -715928292:
                  return;
               default:
                  throw null;
            }
         } else {
            Map var5 = v();
            ArrayList var6 = new ArrayList();
            HashSet var7 = new HashSet();
            Iterator var8 = var4.iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s2pmx1r6oqga6c","F4OE0PBCsgBGEqfrRGdVY+8PE+iEWoSKkvMEJS+Rde4=",-1858047527143040226,7708525944339936693,-2382603555915925889,2920306250494431523>()) {
               case 604318309:
                  while (var8.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3pt2imna34lof","P2dHS6hJm3Yi2r4rzSYyNJkcl0wgI1mCica/YgoMq7c=",4039347910290220544,2048463236546422843,-8366058413283151523,-6372062267333374289>()) {
                        case -159300397:
                           Item var9 = (Item)var8.next();
                           String var10 = a(var9);
                           if (var10 == null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s29vaiwm6n14ip","xUQ9W+rF1l8TPtWuPiz3xvNh/W95+36yLpAVF9Hpc0g=",157095295096006864,-2044275934347741540,7862987464819621511,-4415969690425467910>()) {
                                 case -228773882:
                                    switch ((int)com.yiyiaddon.m.b.a<"s33q7zjwp2irj2","8ui3VnylO8dwgDHWGJ+BU40Y3JO5nb857pIqnFZQkn0=",-5358164850359188426,941341761059192302,-7640120306500917604,-1579250568466775531>()) {
                                       case 798709986:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var7.add(var10);
                              var6.add(new com.yiyiaddon.e.q.k.d.c(var10, var9, b(var9) + "", var5.getOrDefault(var9, a)));
                              switch ((int)com.yiyiaddon.m.b.a<"swvqcdvqonocy","ouIr3MtK1k9MGEx9vqRiUS4TbaKb2wgEMauPJsOHUlY=",5046558039260526966,6074232880403164125,2809408631442843040,4300809907665648234>()) {
                                 case 968875465:
                                    continue;
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  }

                  var6.sort(Comparator.comparingInt(com.yiyiaddon.e.q.k.d.c::dh).thenComparing(com.yiyiaddon.e.q.k.d.c::D, e));
                  a(
                     var0,
                     new g(
                        var2 + "",
                        var0,
                        List.copyOf(var6),
                        () -> List.copyOf(var1.a().i(var2)),
                        var3x -> {
                           if (!var7.contains(var3x)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s19tuixvidkdkm","1aSF+MhbXE7VY0ipGKRKIJHkGrAOSWg/UR7htxfWl7w=",6446852895011182892,6565882933772385979,3085421310277527300,-533213573070226952>()) {
                                 case 1653381311:
                                    return;
                                 default:
                                    throw null;
                              }
                           } else {
                              ArrayList var4x = new ArrayList<>(var1.a().i(var2));
                              if (var4x.contains(var3x)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"szw44gvh52q44","ZWa+bqKnA5NrHDzWHwDI6M8tbSiK20sX7rbMwdAxNGU=",-4804636117672262269,-199305352319684609,-2603735055388959014,-5548986040096616879>()) {
                                    case 1481857640:
                                       return;
                                    default:
                                       throw null;
                                 }
                              } else {
                                 var4x.add(var3x);
                                 var1.a().c(var2, var4x);
                                 var1.L();
                              }
                           }
                        },
                        var2x -> {
                           ArrayList var3x = new ArrayList<>(var1.a().i(var2));
                           if (!var3x.remove(var2x)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1xuqunitsszm4","xZXzdYtAt4cQ0YsuzSvBS7UQk1Ja7x8w149MqEwNONs=",1681941467966985348,8643307311204124935,-7310899029909331712,-4897854130765356416>()) {
                                 case -1825804941:
                                    return;
                                 default:
                                    throw null;
                              }
                           } else {
                              var1.a().c(var2, var3x);
                              var1.L();
                           }
                        }
                     )
                  );
                  return;
               default:
                  throw null;
            }
         }
      }
   }

   private static Map<Item, com.yiyiaddon.e.q.k.d.d> v() {
      LinkedHashMap var0 = new LinkedHashMap();
      int var1 = 0;
      Iterator var2 = BuiltInRegistries.CREATIVE_MODE_TAB.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"szxma6970aads","/s6cwatkO56mfLpkvcKU5J2F6LiOZDjghcj37q/8a1s=",8695844955987621111,-7485326365548619009,-8372181878694526891,8646612198090869962>()) {
         case -1474910757:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1ydsjjn5d81gw","WylkZyeFF8W3AuIgcBxMo4mZ+oDUzb281qgRgs5yIeY=",-6226125688411091497,7239170791133649676,-6340373955818917945,2395534799451658217>()) {
                  case -641248148:
                     CreativeModeTab var3 = (CreativeModeTab)var2.next();
                     if (var3.getType() != Type.CATEGORY) {
                        switch ((int)com.yiyiaddon.m.b.a<"s30opktqdctk77","DLy3gZpCG0kIK6OvwSoiJ5S/kn7TJXIbdWRTUxpqqec=",-2692872658652620562,4123726284139968385,-3689775733423459529,4564897833213340233>()) {
                           case 871208235:
                              switch ((int)com.yiyiaddon.m.b.a<"s3c1rr1rfgx2ru","5J9xvNBS/eo6cmU4rbvlQ6VzSYY9iuiXNlXVkf/vVz4=",8282955489350096971,6096567706035577139,3162043691086166268,5092746298494730765>()) {
                                 case 325093049:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        com.yiyiaddon.e.q.k.d.d var4 = new com.yiyiaddon.e.q.k.d.d(var3.getDisplayName().getString() + "", var1++);
                        Iterator var5 = var3.getDisplayItems().iterator();
                        switch ((int)com.yiyiaddon.m.b.a<"s1zwmad2h3uoec","ANCLMtF5OWWVoiALi6HYtp/3XhdEpLU0oi/+h661z+k=",287125213369686902,6042336796302182999,8097857813418096133,4606992438871195831>()) {
                           case -1520521503:
                              while (var5.hasNext()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3ayyhwjpbtunz","r7rPVW9ZL6jphtQ3G/w5QaFIeS86A2afquSSglapuWU=",-2294222161194282607,4947153914057795281,-5974363883640243606,1309606402866238169>()) {
                                    case 1621843125:
                                       ItemStack var6 = (ItemStack)var5.next();
                                       Item var7 = var6.getItem();
                                       if (var7 != null) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1n5yz3ohkffg1","p7gd/zFYYTQjo2FcsUJ3xiJjpwV2FjyyCCBrD24gzKY=",-5199498724230631652,-7083734286535833453,1704019048349254290,-5085147108473491896>()) {
                                             case 640019964:
                                                if (var7 == Items.AIR) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1fu8dz61znl03","a2rtXzRRfTwlAZsufjt7fp71JJfGDY8WKVb4jITKXIA=",-7781719903851181818,2654977907108474630,-79047750251610080,-193216454670108888>()) {
                                                      case 1949044787:
                                                         switch ((int)com.yiyiaddon.m.b.a<"s15ez66vhn1vut","IvyY0iPysVeJ4C3o8U1uCYgz+fVKlLZo9ToC2HCCKNw=",5928096808858628095,8247456981311438009,7654495479856871187,-2600279452298896640>()) {
                                                            case 1489426459:
                                                               continue;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                } else {
                                                   var0.putIfAbsent(var7, var4);
                                                   switch ((int)com.yiyiaddon.m.b.a<"s17lnbwf3r4475","NLi2QnvPxZeco1qK39AQt0WM+R/2D7K1h+mWQmrEkok=",-8414375264541345230,-6809170918239215741,8668846628831312143,-8671089929356545891>()) {
                                                      case 534410072:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                }
                                             default:
                                                throw null;
                                          }
                                       }
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s3g4kgrd23c78i","aEdSuQvo3QrwULgFb5oD/6ibFdbR7B9EaiMTB2KTnDg=",-2016945518450562731,8007226402269131503,5200697363790214245,-2390122735407963471>()) {
                                 case 1711466426:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }
                  default:
                     throw null;
               }
            }

            return var0;
         default:
            throw null;
      }
   }

   private static void a(com.yiyiaddon.e.q.k.a.a var0, com.yiyiaddon.e.q.a var1) {
      Set var2 = com.yiyiaddon.e.q.c.a.y();
      if (var2.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3muuctldffk0b","cz4fZDheTUQwcyolrR0A11EjWfg+vvyDvptqxSElAts=",-6928702556288976249,8040425649430741487,-3482671519409619318,2609414920672846632>()) {
            case 138156999:
               return;
            default:
               throw null;
         }
      } else {
         Map var3 = a(var2);
         List var4 = b(var2, var3);
         HashSet var5 = new HashSet();
         Iterator var6 = var2.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s10tso4qg0xi3e","wbgigiK2noTbn0uBqrmPQG4VagwwLrXVhoLmwrXaJeQ=",-7696323518968879889,1284284550273634899,1437746427988022987,-6828189297243027943>()) {
            case -1953188898:
               while (var6.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2rcq73oaxrgps","1Vc4dfZZ9OHuftDHCNzyBvb4H9Er9CfjATFgdnCMTa0=",-1587089760715816894,4653658133335083414,7732990844929731211,-3078439134318463920>()) {
                     case -2136332320:
                        String var7 = (String)var6.next();
                        var5.add(var7 + "");
                        switch ((int)com.yiyiaddon.m.b.a<"s14otp95gmadsr","h6ZVwY/tF+2sKW7ebhllodTKZo+iLtCm50XnfOx52Ko=",-5864978557728183056,-1197461529929762118,117091764718780547,-6556217460465484259>()) {
                           case 412951743:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               a(
                  var0,
                  new g(
                     (String)com.yiyiaddon.m.b.a<"s1467vkbwoeckj","asnd4Zf1BZ1YnsFYFac+mBs5qvHecFKXsYjAiN2rTXxTdf28CwLmkMIHEqE=",4307687537950395150,6959132776827347328,4255440481468874476,-2763674329550136439>(),
                     var0,
                     List.copyOf(var4),
                     () -> List.copyOf(var1.a().bp()),
                     var2x -> {
                        if (!var5.contains(var2x)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3vw94533qn51m","uC7DQzX37n4WpKhKKs7msoHppqpT2kpeDzF9dLnXboM=",-8899003091248348743,-3843618686672700086,-7861273444406232475,-8357812603510067848>()) {
                              case 257808444:
                                 return;
                              default:
                                 throw null;
                           }
                        } else {
                           ArrayList var3x = new ArrayList<>(var1.a().bp());
                           if (var3x.contains(var2x)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s16etsjtwr7ua6","DOgjcMLZDrxQf9ZJdhrFt3/lsN9Dd4fCmwLN/LdT9Wo=",6946118026491964994,8251099528790370424,5951841884227921913,4514508426820710291>()) {
                                 case -356663331:
                                    return;
                                 default:
                                    throw null;
                              }
                           } else {
                              var3x.add(var2x);
                              var1.a().o(var3x);
                              var1.L();
                           }
                        }
                     },
                     var1x -> {
                        ArrayList var2x = new ArrayList<>(var1.a().bp());
                        if (!var2x.remove(var1x)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s7tnyqrn7822r","dskUgL9QxZ/0eeebLLBS3YbMrur3vK/KyQC6unsjJzI=",-7398770954215638864,-540208200191490281,-1649600759598772052,-7424934974714772000>()) {
                              case 1826934293:
                                 return;
                              default:
                                 throw null;
                           }
                        } else {
                           var1.a().o(var2x);
                           var1.L();
                        }
                     }
                  )
               );
               return;
            default:
               throw null;
         }
      }
   }

   private static Map<String, String> a(Set<String> var0) {
      HashMap var1;
      Registry var10000;
      label55: {
         var1 = new HashMap();
         Minecraft var2 = Minecraft.getInstance();
         if (var2 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2jxxzfx6gtlmt","DVnuVMQCKC1wYNC+LfsVXDg4LE7TA1+mPSUSN3ADFy8=",-2187051653587716084,4737109931309922273,-5765752552737042754,-7871378949590259273>()) {
               case 444581769:
                  if (var2.level != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s130qor7173fky","+BuRYFrJrEdia+iNyqb5hlJL4f0+goN2/x4HZRb5h20=",1658688239446682822,6357862745013189362,7361228329986555006,-2540803899367227661>()) {
                        case -225655314:
                           var10000 = var2.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
                           switch ((int)com.yiyiaddon.m.b.a<"s2i1756ihivhlx","zqsli+W0oNNQJbfu9Z9BCw5ntIfJVXPEDwOF8qBDdfw=",-5720313706174364780,-8370573632638061382,2456408303747475396,-3289018168464177690>()) {
                              case 2073421509:
                                 break label55;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         var10000 = null;
         switch ((int)com.yiyiaddon.m.b.a<"s2j9snnj96ndu0","59VeFKFDmvs55gwvfhiBpbOqsEKHsqLi0JQOp0SjtQE=",8026635376286135452,-1721260332838257937,8760330463635710398,2920418386058401647>()) {
            case -744604642:
               break;
            default:
               throw null;
         }
      }

      Registry var3 = var10000;
      Iterator var4 = var0.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2kvvpy6j2m9jl","ylJ7y7X3B0CQ2oqmYOBDBIHIKRYCWXPyId3V2Y+4DP0=",-887662510068222379,7962933743646321921,-5821110584909897490,-4720755328405340860>()) {
         case -1060509762:
            while (var4.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2w2vujk2guzec","AHYDpPeqhX6Cidy0qWMHAvJ+PhOOK6I7aDYHwZED/mQ=",845715155551180304,-7239398724775225722,-4811278775550618168,-4234808096075597107>()) {
                  case -1463587007:
                     String var5 = (String)var4.next();
                     String var6 = var5;
                     if (var3 != null) {
                        label33:
                        switch ((int)com.yiyiaddon.m.b.a<"s1yloz13oq4ds","3SqOaTknZAKm6ivBdnqLcdwLK//VxDTPXtQoH8fAY+o=",7421260037827463032,253373222457751045,-8572767034235172935,292122230179793904>()) {
                           case -1197420332:
                              var6 = var3.get(ResourceKey.create(Registries.ENCHANTMENT, Identifier.withDefaultNamespace(var5)))
                                 .map(var0x -> var0x.value().description().getString())
                                 .orElse(var5);
                              switch ((int)com.yiyiaddon.m.b.a<"s3ucr2nw2xjgln","fWwILvkpLAYYDFu6MavbqLJyIf2GtgSJAl1ZmNBRpXo=",-4613817630064089537,-1981373020834635032,7908955252084230335,1836366896597182259>()) {
                                 case 110118415:
                                    break label33;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var1.put(var5, var6);
                     switch ((int)com.yiyiaddon.m.b.a<"sjvhao3jsy3wc","34N8+JDDzV/rzEnPjcpSvN6soX7Po8teVjlJFDsM5j8=",-8777950947153052743,912872890581624080,1657319201019938589,7363204690230456492>()) {
                        case 1028687307:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var1;
         default:
            throw null;
      }
   }

   private static List<com.yiyiaddon.e.q.k.d.a> b(Set<String> var0, Map<String, String> var1) {
      ArrayList var2 = new ArrayList();
      HashSet var3 = new HashSet();
      Iterator var4 = cr.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3dtszxqykgwpa","hAZciFX7gAkXwmQHdC6zz4W8rPCAiLnWGVMXOnyGsDc=",-5387146005802773012,-7428994892089566331,7292050764702237477,-5272425261816707973>()) {
         case -56979633:
            while (var4.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"sqnqubbecrq2j","q773OteTeJ1V7pk+HQbLxDrULrE6NUPS3Fvyu0C0RXs=",3589965810547951982,-1841846966551066123,7065475911097820037,4652739987949773230>()) {
                  case -179596786:
                     com.yiyiaddon.e.q.k.d.b var5 = (com.yiyiaddon.e.q.k.d.b)var4.next();
                     ArrayList var6 = new ArrayList();
                     Iterator var7 = var5.ai().iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s1wf8eyo6joxhp","A7HZBzT19BbV7JjsLX4DfEb8WqNloWsz8+Om91UuiYI=",-2903051019578646925,-2738411310205733258,4613885244800085289,5469410078098346426>()) {
                        case -309976991:
                           while (var7.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"skqv6uprrdjil","ib6nzpf0W0LNoH2mfhpL1IrI+7saknX4ChctVljT1BI=",8197811657063477967,-3782700084118758260,9032476100064214677,3117532073137441390>()) {
                                 case -1106785645:
                                    String var8 = (String)var7.next();
                                    if (var0.contains(var8)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2sho8ff6o13qg","1vLutKWSzAynSdWdN16EKTuRpGSLoDtzx4DAwXetJcg=",-1563438722564594044,-6034371159073484618,3531104926845255831,121193538789334874>()) {
                                          case -843915556:
                                             if (var3.add(var8)) {
                                                label98:
                                                switch ((int)com.yiyiaddon.m.b.a<"s18t1qarmz1ooj","RBAtu8fd5UYRWmeb251D/vjGpf2vZsFCCGq2W+9ymME=",-953829099370407847,2666759984429791994,-5871858397513062108,3568546896292290124>()) {
                                                   case -35138739:
                                                      var6.add(var8);
                                                      switch ((int)com.yiyiaddon.m.b.a<"s28bzarqggkbud","EDfNwolq6gco7Sr7Frd33Y56ip0gQkekPImVQbpX9yU=",8852588723308342769,7937979665662045728,-1242249548044602642,-233356463395604143>()) {
                                                         case -430507081:
                                                            break label98;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s2j3qamadr4c3","Iy84Uav/4RNq0W1BAeISY0L3rcTksOnBAK6DWdDmfFA=",-7941053805804242857,8411048596952819096,-3890692775976100219,-396985447889117332>()) {
                                       case 980356446:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           var6.sort((var1x, var2x) -> e.compare(b(var1x, var1), b(var2x, var1)));
                           var7 = var6.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s17hyzqepwf8fq","UYdiD4kaK5IFpXiT8iVZr7d+ebKMGFo5O1iWqAm3qkg=",1337353915267893353,6076662001787104351,4612638131601962820,1698511903032087917>()) {
                              case 291195180:
                                 while (var7.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1tpyv3fw0gpn9","WptTNCgVfnrFjRLrIHu7hvU4q1Mxky7DYRlYSJBBhY0=",22717453606980659,-7007405983760513968,-6579286191009122138,-6289276931333125182>()) {
                                       case 111088074:
                                          String var15 = (String)var7.next();
                                          var2.add(new com.yiyiaddon.e.q.k.d.a(var15 + "", b(var15, var1), var5.D() + ""));
                                          switch ((int)com.yiyiaddon.m.b.a<"s39zthb7yfz82v","Cak5V3XGaojJwln5KeasnuN2Mbvf2s/uVXJEhnDpp/U=",-6910622157969482608,-9059187370135322882,760343940750086672,-4858336499976220768>()) {
                                             case 1658266879:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s3112ell5qngqa","NXWFFOFtaWrSNxkgHB1Sh8TeDR4ZKeoKS1Dd0wE1azg=",2256991031334391747,-2836276751307293768,4424501827010527802,-6653919256650055282>()) {
                                    case 108546008:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            ArrayList var9 = new ArrayList();
            Iterator var10 = var0.iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s1fyi1s6yzyvkc","bHuOMoxSGPS959Apodrs6VgS2wB5uWlo96S0TMOc25o=",5130441268171316248,6083230290565412643,-4540932411623365759,-3415886849391232203>()) {
               case 1447741735:
                  while (var10.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s12n9tshihphja","jM7vhBwjBB/2+oLHilfRJsYXZPhhmi+objdOgrnkbeM=",6281379658473786412,7835969447039798668,4796173197040586691,6237053037247810838>()) {
                        case 422744186:
                           String var12 = (String)var10.next();
                           if (!var3.contains(var12)) {
                              label68:
                              switch ((int)com.yiyiaddon.m.b.a<"sdr4t5n1wv3t6","UYcY6ZZwvHo9vOmw0ICwYQCw4o+gaD4aI7kTZzopvfA=",5700427136058362339,9149480208334777056,-1355234492858512815,-9132707403027770507>()) {
                                 case 1457058779:
                                    var9.add(var12);
                                    switch ((int)com.yiyiaddon.m.b.a<"s3jhepw3fy89v1","Qo7SW5p8RnQsktLxnqWnMBQCutwOZsnJ/IaaA72ayyk=",830919652635925218,-8051008315000719510,5058692869669133995,-5034904537151225469>()) {
                                       case 969661262:
                                          break label68;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s10dy5bdf0ve9x","s9ZSdntBQncoYKi82Cq4w8wgIgfPWcXq45EhnaaPHt4=",-4909070421860324190,-7074026387316512200,-7668976987383806506,4959965648826831487>()) {
                              case -2061761903:
                                 continue;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  var9.sort((var1x, var2x) -> e.compare(b(var1x, var1), b(var2x, var1)));
                  var10 = var9.iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s3v5kl2vrwam5","bwwcUNqiS92GLMPlfM1GvG0sGT/fx4mOCrAiznAmeHw=",-3403977124887739096,-1447025858081085617,-6994568837178188172,-2109979708712853094>()) {
                     case 934793230:
                        while (var10.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s20pg6sj6821nz","52jeOVExc6qVULz8mi98Md3VWdPGX3fvOMXTny9iFBs=",890859056610678178,5140536432878507128,-8642733712975971624,3972704642613424232>()) {
                              case -309561057:
                                 String var13 = (String)var10.next();
                                 var2.add(
                                    new com.yiyiaddon.e.q.k.d.a(
                                       var13 + "",
                                       b(var13, var1),
                                       (String)com.yiyiaddon.m.b.a<"scexae5lj6rp","doak9IitvMzSOGnz6FHwRXuHIpKVv0ozaK5eH9Xgzx2m+OiJe6jNraNrJm0=",4478726558410809350,3848588860501926883,1727234481322183123,9035465164035612876>()
                                    )
                                 );
                                 switch ((int)com.yiyiaddon.m.b.a<"sqj6yiad7mmxp","+tC3EKuZm6WR9VLTVz0imsxXyZv7MruTle/SyVv2Crg=",3753531752777752404,-843447106308352476,-5663149896701513053,-3846696981150708540>()) {
                                    case 54126252:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return var2;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         default:
            throw null;
      }
   }

   private static String b(String var0, Map<String, String> var1) {
      return var1.getOrDefault(var0, var0);
   }

   private static String a(Item var0) {
      Identifier var1 = BuiltInRegistries.ITEM.getKey(var0);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2x5eanl3whl3o","cN4Q2tBSNnm/qpI2t60R9UKMlYM+stWb17hy7Tm/OWs=",1789548362919016376,-9010111355172007133,1339152415066178043,-3660569596064505398>()) {
            case -2128502241:
               switch ((int)com.yiyiaddon.m.b.a<"ss0dwgcigpvsj","U7DotUIxkV620fYhqhIlBMdkrqj/h4hkUATyb8PwI34=",-8484930444701758085,5366310505315734632,-8388228042056852522,1708509253868773594>()) {
                  case 1193691038:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var10000 = var1.toString();
         switch ((int)com.yiyiaddon.m.b.a<"s4g9hb61sjt9e","9Zcu2TefCXnqL2DhdcU7ri+6m7l+zDCz6rQtG6Aakws=",-1392669012018755000,8244194001273629480,819961797561437649,-1300704756349500736>()) {
            case 1870069624:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private static String b(Item var0) {
      return var0.getDefaultInstance().getHoverName().getString();
   }

   private static void a(com.yiyiaddon.e.q.k.a.a var0, g var1) {
      Minecraft var2 = var0.a();
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"so28gt6b90h57","XC76vdOcEDnqTquCWRj7EKLRfnEbcaTdud+NpfPCk+Y=",9027846054833623622,-4872078371137123999,8737253389821104518,-8610456880748709039>()) {
            case 960003300:
               if (var1 != null) {
                  var2.setScreen(var1);
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s28n12qup73q7t","sJboAzlOVAPh0uwqVOxbGLgwhqKIdOGiD0k9d5wYenU=",2724100066926835297,-3650789067609619129,-5016412105242316145,6273958787089602870>()) {
                     case 1863225099:
                        return;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }
   }

   private static final class a implements g.b {
      private final String zZ;
      private final String Aa;
      private final String Ab;
      private ItemStack g;

      private a(String var1, String var2, String var3) {
         this.zZ = var1;
         this.Aa = var2;
         this.Ab = var3;
      }

      @Override
      public String L() {
         return this.zZ;
      }

      @Override
      public String D() {
         return this.Aa;
      }

      @Override
      public String M() {
         return this.Ab;
      }

      @Override
      public boolean a(Canvas var1, float var2, float var3, float var4) {
         if (this.g == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s368p4zqnzptnu","9zpbkZLdKLY51i0ZKF7rAfOgYkdd8w+hd3TrOjEuIh4=",-6740901704645584978,6603848679697815965,-3694888291471811803,-2566460305767306859>()) {
               case -147046864:
                  this.g = new ItemStack(Items.ENCHANTED_BOOK);
                  switch ((int)com.yiyiaddon.m.b.a<"s3tbjnxdzkvaer","y/UVSowX63pYzCQQirnEoYiZBongYMF2w1xcT6FLB5g=",-5394895229799726209,3306609134162063430,6275272493731481176,-8090532744384808647>()) {
                     case 1630807907:
                        return com.yiyiaddon.l.g.c.a().a(var1, this.g, var2, var3, var4);
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            return com.yiyiaddon.l.g.c.a().a(var1, this.g, var2, var3, var4);
         }
      }

      @Override
      public ItemStack a() {
         return new ItemStack(Items.ENCHANTED_BOOK);
      }
   }

   private record b(String Ac, List<String> cs) {
      public String D() {
         return this.Ac;
      }

      public List<String> ai() {
         return this.cs;
      }
   }

   private static final class c implements g.b {
      private final String Ad;
      private final Item m;
      private final String Ae;
      private final com.yiyiaddon.e.q.k.d.d b;
      private ItemStack g;

      private c(String var1, Item var2, String var3, com.yiyiaddon.e.q.k.d.d var4) {
         this.Ad = var1;
         this.m = var2;
         this.Ae = var3;
         this.b = var4;
      }

      @Override
      public String L() {
         return this.Ad;
      }

      @Override
      public String D() {
         return this.Ae;
      }

      @Override
      public String M() {
         return this.b.D();
      }

      private int dh() {
         return this.b.i();
      }

      @Override
      public boolean a(Canvas var1, float var2, float var3, float var4) {
         if (this.g == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3diti5kqe53nj","wZ1sBmbo6bZJ6JA6VN4EStAa4S36qmON0QUAukf5Npc=",-5933055655128860090,8952254290721537984,-1067114010276790013,2231947131465587219>()) {
               case 1699497902:
                  this.g = new ItemStack(this.m);
                  switch ((int)com.yiyiaddon.m.b.a<"s1l3d6j0ux8m3i","CmKBX/5AczMt8pOSwDOc/5xDD9TqRGTEIupMh0xy/xc=",-7703876826955018935,4379718686361823908,4361912277462928317,-3338646243010740278>()) {
                     case -91691753:
                        return com.yiyiaddon.l.g.c.a().a(var1, this.g, var2, var3, var4);
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            return com.yiyiaddon.l.g.c.a().a(var1, this.g, var2, var3, var4);
         }
      }

      @Override
      public ItemStack a() {
         return this.m.getDefaultInstance();
      }
   }

   private record d(String Af, int rg) {
      public String D() {
         return this.Af;
      }

      public int i() {
         return this.rg;
      }
   }
}
