package com.yiyiaddon.e.n.b;

import com.yiyiaddon.a.c;
import com.yiyiaddon.d.b.e;
import com.yiyiaddon.e.n.h.d;
import com.yiyiaddon.e.n.j.g;
import java.lang.constant.Constable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class a extends com.yiyiaddon.a.a {
   private static final Logger l = LoggerFactory.getLogger(
      (String)com.yiyiaddon.m.b.a<"s31wn8beq7yjva","ri4WA3r3nj7U4VNtRt0GeChw63pkKUhORU+cb9kj7pGdO78ZfbvNMs6N2mJw5/8p9mj4/FleO6Ec5tV+6Mg=",939262899872707604,2721844805569019038,-653885162277330368,6273683553736361143>()
   );
   private static final String qI = (String)com.yiyiaddon.m.b.a<"sqz7fubk633af","6+QZHPPK098aa0vAqUiyDSxxduhSjjne9OEY83Hv3vUO2fCcLn8=",-5840169825291205960,-6192287268964251247,5183046899392997009,-7287729381131471577>();
   private static final String qJ = (String)com.yiyiaddon.m.b.a<"s2o8evtsrzkltn","+4ZWN2nw6Ch0tXYfuZ68jNImdWkI0eMm0wwxosPwEPASvDnO",-3518815183402179120,7329300270208121925,8317931109489219302,-4414456011889674186>();
   private static final String qK = (String)com.yiyiaddon.m.b.a<"spnlr5kpcji99","Muwy9WFhLTvzvN2zbCQ5dmUiaIwGBf7o0d0bMIWUPIuvKA==",5274492091394214910,-4912172862124906870,-4876997363932326249,-7414987428055600928>();
   private static final String qL = (String)com.yiyiaddon.m.b.a<"s3lfxav9wba4b7","3qDV/2Aii8xS//mMUzINxACtaDSrxhlIRiNAlm48UXU=",2708705806062161140,-5841557380866759443,7852651637959763316,5781913304288759510>();
   private static final Minecraft X = Minecraft.getInstance();
   private static final List<String> bt = List.of(
      (String)com.yiyiaddon.m.b.a<"s3npsbkze0z6g0","Jlcy6aGXXUuh3i6XP9CrKVP6ZmBxJ6qNcS40M15EZu0=",-5551272369564446852,5236365058091944879,5041052238734086258,-7560761866399606141>(),
      (String)com.yiyiaddon.m.b.a<"s1q5r3nw86m681","2CHpc++B2RbqFAVEMjs8ApcOJ8V993fH/uLcVst9sJ5kGDexipU=",-2978995490170052914,5502611552896403745,8321276642616247233,-416168241073717643>(),
      (String)com.yiyiaddon.m.b.a<"s3pxm1udy94bcr","YbQdOztF8zXHHgETrJGtWhlz9hgpEnJNdly7Mk5eGDwbU1dcuqo=",7272463222560203487,-5450266939304110789,5028372135783943943,7155030101995037772>(),
      (String)com.yiyiaddon.m.b.a<"s105cx6kf76mqv","TVFewXlNR7N2Knd98YQoNb6h8a2Bdh0U/z6dgcl7B+w=",-314834312066137866,-4546091960071063244,-2946040797665398026,-7169585475093826816>(),
      (String)com.yiyiaddon.m.b.a<"s1pbigzf4opkhr","egu3cMKg/huI6FsEa9Bh8FLvdXWIYDaoBPm7Zd7k1TV18a5W",-2616425179220908252,3439436910542586451,-7102907178862761742,-2515908507255617588>(),
      (String)com.yiyiaddon.m.b.a<"s2wfgk4cm6u8zt","ov91TSp+JgiSGeDqWtBslAR9u2o6FuptQkJKo31EzoMlamQ1",-4265402979572844290,1878467059093814575,-6711384172587523999,6412288689087869078>(),
      (String)com.yiyiaddon.m.b.a<"spvxoyfbyq02m","x8kdkndp+DtaEZ2Jo731PouXqdXvVfgLcjZvSheQbG9pyw==",-2981468602277946567,6468809606077372330,-3148543470339836600,5796419549965913509>(),
      (String)com.yiyiaddon.m.b.a<"s4hagp40493ow","bhe7L7Y5uH0eV7LKb6WMiYLJk1KtELybzCNF+rN1Ysk=",2664531615798199755,-5156291423347645193,-646659242731952596,-765207919093522130>(),
      (String)com.yiyiaddon.m.b.a<"s37wavdg1orfgw","XFLyXnKQCWy5JrHwq5IrP6Usu8CGvzrLeJ3v/2Qkh/E=",-226962050453234693,-2994482755373158489,8007006419926555003,1596161246801331124>(),
      (String)com.yiyiaddon.m.b.a<"s2pw6m5x74atod","sQAE2/aUCdRJuv42kNJh4A/VlrDbunFLLTKgUxWymec=",2680787781626156659,-8793512122790004952,4271964021932873978,-4273290673923807722>(),
      (String)com.yiyiaddon.m.b.a<"s1b3jbk37snd1y","hE7f7aDkT1NmjcDL461GCdzGM5XH5mIwdA5sy2cBRuE=",7999623904928245112,-1320150878736543965,-3479503093347603642,62672731232362782>(),
      (String)com.yiyiaddon.m.b.a<"s3m7men01iinyf","MNBVjIcOoY6JIlkNltWXWsWKuV86GNdRRl59yhzgMPeJHnz4",-7779773238522707625,-7372955125383162980,-7028913877044346394,-4063319841092077453>()
   );
   private static final List<String> bu = List.of(
      (String)com.yiyiaddon.m.b.a<"s14h8v81np67c0","Z3Ye1nZbtmYCu2XWN5LFjxvBuKnbn5tpzyd3O6i4t0FX1g==",3046822374963697746,-1688513679744655714,4036794937601409496,5920764329472240351>(),
      (String)com.yiyiaddon.m.b.a<"s3sxbb81m51gvi","CTUAXdYYv+aTFV9SEIYamGOlgE1AFF9Abgr8q8z3JzAC4w==",2930798062011935248,-6995518132198992968,546751193290339920,-3308197978013727515>(),
      (String)com.yiyiaddon.m.b.a<"s2m8jlpu7023gm","mq9g72XcNdo1lIWC1IGb98GoparpkUDkZlHKCRLqpaH+Vg==",-4591889256145428328,-2341702815977296452,-5196711731464887476,5793387618174808639>()
   );
   private static final List<String> bv = List.of(
      (String)com.yiyiaddon.m.b.a<"s2792fo8lpppeb","/XaX+im6+xxgogeZjD/o7oHNo826UlNMktLBFOewLlA=",7321331569428824781,3747762963793150846,3227749346660513577,8713705364711105184>(),
      (String)com.yiyiaddon.m.b.a<"s38cp7k5twqyi1","IDkjgeH0ICeDP1HWE3d/K/6nrPONyCXeuJqOkZY7kD0=",-1634932372960291524,7149871859688571493,7224266377065995166,-6079965603936431041>(),
      (String)com.yiyiaddon.m.b.a<"s3kle7656nz9xe","ethBrfJ20GCOh8jeX193dODkXKco++LswDwUCoT3N2E=",2761385569709260787,5934831969200915616,-1079787739536779125,5102913609510164178>(),
      (String)com.yiyiaddon.m.b.a<"s1w3ksdumbi6tm","ZUxz4gZxQltmAWLOb1bUMmPnDXpE0yIHuG7C12m3ex8=",-5966378536111085168,5411513156889598171,-2543854331708638675,8640805943618465168>(),
      (String)com.yiyiaddon.m.b.a<"s2iivnxo5ul6cx","x2bqAJN6eNtDQaSrxTaYh8U8oTp9SRM1jTGa3dkawMs=",7253582403378916462,-449066580557230319,225045130113654137,-4810329004032759732>(),
      (String)com.yiyiaddon.m.b.a<"s1b3jbk37snd1y","hE7f7aDkT1NmjcDL461GCdzGM5XH5mIwdA5sy2cBRuE=",7999623904928245112,-1320150878736543965,-3479503093347603642,62672731232362782>()
   );
   private static final List<String> bw = List.of(
      (String)com.yiyiaddon.m.b.a<"s1wkbh7scozc6u","Y2ZjTbj65sf9aQhFUAYE4K/jz5RgcePV+1Bj/fmFvNU=",3181947516973187480,-323399372901716339,-1608875773680836937,-6056588649679756833>(),
      (String)com.yiyiaddon.m.b.a<"s33v3y34x2el0q","I2xtyvHL1zJK8feKo8AGLFRthN/mm/0fQGdHFKuA",-4170045692023888323,-960282397030097491,-7982670748288724231,-6962656672330989930>(),
      (String)com.yiyiaddon.m.b.a<"s1lhr9xg7xo38g","Vm+uYswtcOirrGRSa4kMeEg9gzpyE7QatWVTRjJv1eE=",5494614692180846902,4684255294437985323,-9081858755487667154,2329250462260680225>(),
      (String)com.yiyiaddon.m.b.a<"sfsa0qr599m79","C3I/jnqrvR5rE0cNika9EZypyEvKAjaIOiG8vicP",-5690844291519536942,2283259318699569416,5444849843619338125,8162800476082583877>(),
      (String)com.yiyiaddon.m.b.a<"s34izfi7lru1cl","hQyePxGoO3gRWwol60kR4j0CG8ghzYpXKgaRDxcO6wo=",-6593220804861837805,-7361308393767828750,3888111562363534835,4943225402580419254>(),
      (String)com.yiyiaddon.m.b.a<"s1lk59l95uulpa","LS4xS30IZ5gLkP2tMHCHbBSMJabjfRoowNNTrzwA",-4015096698699069496,-4982442265861312625,-6861652432458390884,5859644940685268694>(),
      (String)com.yiyiaddon.m.b.a<"suvv16hert5s1","Bry7ZI2MclnivQ/Yxy0B21Cw/OzOXyZwEBr7ytLIPn4=",-8042314877896611940,-4422249458768075075,8628718146936366176,-1656818667894808378>(),
      (String)com.yiyiaddon.m.b.a<"sl9r19tk6euq1","TNz8T8OvxdAQSrYnn1oSBFhJojFAnKa7aTj38rDU",8512666209875524965,-14683320357918003,-8480298397756667821,-1049182688320952750>(),
      (String)com.yiyiaddon.m.b.a<"s2scit7mpynlzh","7mrF2AODuPQGG078HkIsnvm93AQoaTD5jTLjNygtSyo=",7043119407346147153,-986816507590883921,6702905234217913418,6276221336372466679>()
   );

   @Override
   public String a() {
      return (String)com.yiyiaddon.m.b.a<"s3ay4qneyewd2j","w0vh31Jz/+wUyBym/xMGVJ+IApQfLmo2MIW6gKm9qWL4OBNdAPpo9+ux",5977986089021046368,2977633729040074907,4444463483065341828,6708931777353862945>();
   }

   @Override
   public String b() {
      return (String)com.yiyiaddon.m.b.a<"sqz7fubk633af","6+QZHPPK098aa0vAqUiyDSxxduhSjjne9OEY83Hv3vUO2fCcLn8=",-5840169825291205960,-6192287268964251247,5183046899392997009,-7287729381131471577>();
   }

   @Override
   public String c() {
      return (String)com.yiyiaddon.m.b.a<"s39oyu2wtisaq1","8fTzwQ4Wt1eDodTQfbFTog4ODTCogj8moIHla7sEPDNVxyvGTACoGODJbp7M7sBiHrc11JGORnR3qvFZ4Lza5ntm3n2t6Lab",3895746811501009242,1328879631029425002,4169424474725346827,-3968410265307745740>();
   }

   @Override
   public void a(c var1) {
      if (var1.a()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2jn4ehmpzp0t2","wF+XrUvMTUTQMkVabzoTdCfBmIl03mjXvY7IIu82BrQ=",-190593682651687307,5922269097160958864,8314399868044720001,-4169080530308273963>()) {
            case -243845444:
               var1.c(this.d());
               return;
            default:
               throw null;
         }
      } else {
         String var2 = var1.a(0);
         byte var3 = -1;
         switch (var2.hashCode()) {
            case -51313443:
               if (var2.equals(
                  (String)com.yiyiaddon.m.b.a<"s3pxm1udy94bcr","YbQdOztF8zXHHgETrJGtWhlz9hgpEnJNdly7Mk5eGDwbU1dcuqo=",7272463222560203487,-5450266939304110789,5028372135783943943,7155030101995037772>()
               )) {
                  label129:
                  switch ((int)com.yiyiaddon.m.b.a<"s2ryx2x9hh7mzn","AzyBv6AQ+BQgDAh2npataKPP7vE0XiC+ZTnTzqa+te4=",-501189255555113886,-2730114337227177019,-1217365501397861359,1807458591573133946>()) {
                     case 1786843070:
                        var3 = 2;
                        switch ((int)com.yiyiaddon.m.b.a<"s6ye5cljnlpyb","UqE51hcQEC+Kg94Ij0j2Zl6d10TItiBaUe1bQEttVPU=",-620280413442057342,2629448682131043143,-846940481313541119,-5662126623393837682>()) {
                           case -384478865:
                              break label129;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case 758655:
               if (var2.equals(
                  (String)com.yiyiaddon.m.b.a<"s4hagp40493ow","bhe7L7Y5uH0eV7LKb6WMiYLJk1KtELybzCNF+rN1Ysk=",2664531615798199755,-5156291423347645193,-646659242731952596,-765207919093522130>()
               )) {
                  label163:
                  switch ((int)com.yiyiaddon.m.b.a<"s1cm96t6lmp4d7","WEj1KaV6DwO8AstPl/yLAkNm6e1rdGHc3xSykoPd1P8=",8534572351486567983,7925745759441905426,2576439705059372524,-3984489353409702411>()) {
                     case -1976155065:
                        var3 = 7;
                        switch ((int)com.yiyiaddon.m.b.a<"s2u9sp8acivunh","aC85YB7YMjg2oQm9GchaOgQqCHM7u7JpaTM9jbvqt5c=",-5158254801748829803,-2703418962808654430,8664006656005491531,7169678775578603365>()) {
                           case 108535093:
                              break label163;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case 904469:
               if (var2.equals(
                  (String)com.yiyiaddon.m.b.a<"s1b3jbk37snd1y","hE7f7aDkT1NmjcDL461GCdzGM5XH5mIwdA5sy2cBRuE=",7999623904928245112,-1320150878736543965,-3479503093347603642,62672731232362782>()
               )) {
                  label169:
                  switch ((int)com.yiyiaddon.m.b.a<"s2mimq2j8xv4zf","5QS2mjUUpfDIoxxQZsi8RyvzaDVK72WZqnhGYZl0kB0=",-6426357183128247306,-2052367151934435842,4435404568982270352,-6110111512336135506>()) {
                     case 1968647958:
                        var3 = 10;
                        switch ((int)com.yiyiaddon.m.b.a<"s37c7y2x30xxd9","zDwK6prHlkrRZuTF0gTiANGGF2c7XFGIay75u3hvlJQ=",6738093411944994801,-7610204475385796984,-2486049267205511967,-5504857665905964350>()) {
                           case -26040844:
                              break label169;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case 934923:
               if (var2.equals(
                  (String)com.yiyiaddon.m.b.a<"s37wavdg1orfgw","XFLyXnKQCWy5JrHwq5IrP6Usu8CGvzrLeJ3v/2Qkh/E=",-226962050453234693,-2994482755373158489,8007006419926555003,1596161246801331124>()
               )) {
                  label132:
                  switch ((int)com.yiyiaddon.m.b.a<"s2d6m7wspw7mqe","XFB7zy6uHJVzUFbXbCojnasI5IUWMxonvQUfMrdZKkk=",-7531172383776538554,918351090305921317,6818722709399481324,-2839583992673489216>()) {
                     case 1587929076:
                        var3 = 8;
                        switch ((int)com.yiyiaddon.m.b.a<"s10xmhm8ywgbp1","uFZSkg82VkYrWsdgPrKPiuk9xoo5LlNl5l3u2n57jH0=",6223982555167384792,4001653421630169264,8519210305976135296,-5740144695225464052>()) {
                           case -1725089815:
                              break label132;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case 1006537:
               if (var2.equals(
                  (String)com.yiyiaddon.m.b.a<"s105cx6kf76mqv","TVFewXlNR7N2Knd98YQoNb6h8a2Bdh0U/z6dgcl7B+w=",-314834312066137866,-4546091960071063244,-2946040797665398026,-7169585475093826816>()
               )) {
                  label154:
                  switch ((int)com.yiyiaddon.m.b.a<"s39zy5jvhr0h66","mds3AO1xkpZJ3coC7uZNSaTM2CezmIGGblkahLaEqNc=",-4960928382970533092,1041308535111683888,2501876842167241405,1953867493019663509>()) {
                     case -1853864078:
                        var3 = 3;
                        switch ((int)com.yiyiaddon.m.b.a<"s1kjp0pycolyad","PPFMjmyDhN69vFnWKKsg1T96MEYgWnGziLcIQ/nMo1w=",-6572097722205059247,8702885386728995519,5143160360235856495,-3129869025648434608>()) {
                           case -1149831757:
                              break label154;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case 1029865:
               if (var2.equals(
                  (String)com.yiyiaddon.m.b.a<"s3npsbkze0z6g0","Jlcy6aGXXUuh3i6XP9CrKVP6ZmBxJ6qNcS40M15EZu0=",-5551272369564446852,5236365058091944879,5041052238734086258,-7560761866399606141>()
               )) {
                  label151:
                  switch ((int)com.yiyiaddon.m.b.a<"sgr15b9322w88","XW3HVA3VDTAv1FtCcGgxEeO95QXL6F8Q03Z54JE543c=",-1453733177515000801,4004859577114989346,-4253813350325376506,-6957879828828490399>()) {
                     case 92128736:
                        var3 = 0;
                        switch ((int)com.yiyiaddon.m.b.a<"s3oqd4niss0pu9","YaG257ri/71eEDTM9a4/OfotgXHqmkrX/XB5nm9pzAQ=",-8280736010855181729,3552113457482144129,3827675086038622382,1581792821364123087>()) {
                           case 1530049934:
                              break label151;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case 1135395:
               if (var2.equals(
                  (String)com.yiyiaddon.m.b.a<"s2pw6m5x74atod","sQAE2/aUCdRJuv42kNJh4A/VlrDbunFLLTKgUxWymec=",2680787781626156659,-8793512122790004952,4271964021932873978,-4273290673923807722>()
               )) {
                  label135:
                  switch ((int)com.yiyiaddon.m.b.a<"sr3eb2p8cipg3","s7dXJTGlRPq0VeJvrnfhhsD35jhWfRe/UCE62NMuk2Q=",-5357227425505831936,6995359866065950804,5557860673654598376,-2757508285707237221>()) {
                     case -621506011:
                        var3 = 9;
                        switch ((int)com.yiyiaddon.m.b.a<"s2hrk9huufo69d","9W29seWDLgq1GUCjsWbpxNTvY44wAqaRhOn9SRtNXCs=",-2896257349563325805,8244426128845130481,4888128166674767562,-4579019503481927250>()) {
                           case -1847634859:
                              break label135;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case 25189985:
               if (var2.equals(
                  (String)com.yiyiaddon.m.b.a<"spvxoyfbyq02m","x8kdkndp+DtaEZ2Jo731PouXqdXvVfgLcjZvSheQbG9pyw==",-2981468602277946567,6468809606077372330,-3148543470339836600,5796419549965913509>()
               )) {
                  label158:
                  switch ((int)com.yiyiaddon.m.b.a<"s2sktuigy33vp","Oxd4Y/X7Fc5dLFoO7+Dsoa6w3rtYP3ejQnRdI89k6KE=",1612594408957595813,-983703681699115537,-2740333715233554773,-4597690457962888006>()) {
                     case 1960609495:
                        var3 = 6;
                        switch ((int)com.yiyiaddon.m.b.a<"s3lv6qc1x4jql5","3QaQ880yYlpwMgPGlNGIVGeeaPeqNfkrLARspWVjVWQ=",-6923163922929027389,-6634948892676555821,-7580315703364032544,3756120908274937648>()) {
                           case 1101759375:
                              break label158;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case 828536792:
               if (var2.equals(
                  (String)com.yiyiaddon.m.b.a<"s2wfgk4cm6u8zt","ov91TSp+JgiSGeDqWtBslAR9u2o6FuptQkJKo31EzoMlamQ1",-4265402979572844290,1878467059093814575,-6711384172587523999,6412288689087869078>()
               )) {
                  label147:
                  switch ((int)com.yiyiaddon.m.b.a<"s2whvape731lac","AUyakc2nDYNCkWqBer2uKmrVHHMHhWOmvDV2oCb16zg=",-691317226988402175,6172309282502307265,-479726232762094673,4877578265832000378>()) {
                     case 1092472917:
                        var3 = 5;
                        switch ((int)com.yiyiaddon.m.b.a<"sgy8mmu0edxtk","BC6sI367s2d59XjM8baXD4B/24YbFHrf8NKYgMdD4ms=",-7908058299915146740,4539342960372846628,-3640011092612248234,6197171358194962491>()) {
                           case -88709084:
                              break label147;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case 889783073:
               if (var2.equals(
                  (String)com.yiyiaddon.m.b.a<"s1q5r3nw86m681","2CHpc++B2RbqFAVEMjs8ApcOJ8V993fH/uLcVst9sJ5kGDexipU=",-2978995490170052914,5502611552896403745,8321276642616247233,-416168241073717643>()
               )) {
                  label143:
                  switch ((int)com.yiyiaddon.m.b.a<"s2ga8m86lwkg5","3e2RQ6kWSytM2a/gNtIlT0iWkktl9tFLleDT7rX+Geg=",-7896041670520304564,5187820495859705370,6238756978757726306,2615306525953613514>()) {
                     case -1402977642:
                        var3 = 1;
                        switch ((int)com.yiyiaddon.m.b.a<"stvwmbfz6jnbp","DHNXELQci+FOG7c5XUn1sIzRMj5/lGva3IlG+w5YLOA=",4678158444099679932,4205619550617014544,5360997266056991960,5917126362114235976>()) {
                           case -1453437446:
                              break label143;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case 955440325:
               if (var2.equals(
                  (String)com.yiyiaddon.m.b.a<"s1pbigzf4opkhr","egu3cMKg/huI6FsEa9Bh8FLvdXWIYDaoBPm7Zd7k1TV18a5W",-2616425179220908252,3439436910542586451,-7102907178862761742,-2515908507255617588>()
               )) {
                  label138:
                  switch ((int)com.yiyiaddon.m.b.a<"s1mhqzzwk6hb2f","I3RlJBw3y5J1e1Lk1xncUcfbJ1dp8uX3Dg9UenG6COM=",3914369303862762263,8463718015422024986,-6770325333605954819,-7650313394586839533>()) {
                     case -632108985:
                        var3 = 4;
                        switch ((int)com.yiyiaddon.m.b.a<"s2xz6cyha9ppcq","kT9oa3OQBmh0IpeoBt+6JyCg6KHfkzkBOVCmG/vpeqs=",-8739294770899274927,-3008097522808126864,-8061966544226343940,-6923622537787783605>()) {
                           case -496025545:
                              break label138;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case 1198118165:
               if (var2.equals(
                  (String)com.yiyiaddon.m.b.a<"s3m7men01iinyf","MNBVjIcOoY6JIlkNltWXWsWKuV86GNdRRl59yhzgMPeJHnz4",-7779773238522707625,-7372955125383162980,-7028913877044346394,-4063319841092077453>()
               )) {
                  label173:
                  switch ((int)com.yiyiaddon.m.b.a<"s368rly9n9srph","aaYuglAVu5s+UqZmnSMkTT8SgJhAiRqv9R4bjogQngk=",-2230967630232730375,5678895560973658461,-4616152387505342933,8943372856874469703>()) {
                     case -2113337943:
                        var3 = 11;
                        switch ((int)com.yiyiaddon.m.b.a<"s1ot3zm0w8r3yy","hA2J49P5SbBR5vhPfTxuou4TcN2oEYnzeHCQw4cICP8=",-2740796447049808693,-5672927199600093670,3505303669038341428,5346052527652095023>()) {
                           case 768181864:
                              break label173;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
         }

         switch (var3) {
            case 0:
               this.g(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s3o04c3v3xg1tt","SB5Ao9abrhADdHGitJ1QjluqSJXQWSVwGL7irzplRRw=",-4519459015925049596,4901262495703986548,3966297698865414325,-5244586318837190399>()) {
                  case 360559835:
                     return;
                  default:
                     throw null;
               }
            case 1:
               this.l(var1);
               switch ((int)com.yiyiaddon.m.b.a<"szrorvitllgxm","29Rm4wkqDeSeIKmh/NC6g7X7ZFvC1CiAonZk3amhlEY=",193656953490472627,3680881319785313461,2569772107957385205,3237006328241088639>()) {
                  case -30512229:
                     return;
                  default:
                     throw null;
               }
            case 2:
               this.gw();
               switch ((int)com.yiyiaddon.m.b.a<"s2p2gj6jwqxcwu","1/WIMtMRpOX19eeHagDPBCBmCt+UsGbVCEB4XFwJmeE=",6415377918966298254,7259854944273587411,6605424113333993358,-7566941595440229791>()) {
                  case 1995398477:
                     return;
                  default:
                     throw null;
               }
            case 3:
               this.i(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s2dgzhat2ca9xc","hh5ydA4ul0jFtveeWdu0gLl9y5S6cn/F74tu9A3RyxU=",-5131622427588968562,8286879793585054344,-7047574790530336096,-198207545214982497>()) {
                  case -736062940:
                     return;
                  default:
                     throw null;
               }
            case 4:
               this.m(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s38p50o73uczg3","gvg7QA0ccD6HVJG0Yjaavfrx0sjkqCwbrWNr4bs8ms4=",-1627958187534526787,760088136256284040,-1159030791945521206,4005458221134614060>()) {
                  case 1852771797:
                     return;
                  default:
                     throw null;
               }
            case 5:
               this.j(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s1y762h5w18uxm","b0amkwnfxHpO9rQb40yVz0oXVOqs2kSSH44FKe1tEkc=",-67130579167412189,-5020319857588154176,3628117515489112138,4690680110867030187>()) {
                  case 1096304566:
                     return;
                  default:
                     throw null;
               }
            case 6:
               this.H();
               switch ((int)com.yiyiaddon.m.b.a<"s2zu5o25gpjgx0","Ek7rY3qqAox6sTncKP6FAq+HdH5cOmbIJEVKgNg3QGI=",-319405480996080759,5518408536434353985,3254105595731496107,-4851857652579175836>()) {
                  case -862269379:
                     return;
                  default:
                     throw null;
               }
            case 7:
               this.k(var1);
               switch ((int)com.yiyiaddon.m.b.a<"sato9xvwu0gvr","NC+0CM/QITXKPwe27sijC6usMEvjmVVUdLnfK6X6xJo=",-3494827270092773568,3448210085386697552,4765132198582495618,375407920275690312>()) {
                  case 1760883726:
                     return;
                  default:
                     throw null;
               }
            case 8:
               this.gx();
               switch ((int)com.yiyiaddon.m.b.a<"s2up4grsj73aad","pANg7EQzf/+W36zs/uh4S8mx7Rgz5mXir10bexr71xM=",-4304021437652067233,6731787631746593800,-1304946255320526637,4560106590546285999>()) {
                  case 906081956:
                     return;
                  default:
                     throw null;
               }
            case 9:
               this.gy();
               switch ((int)com.yiyiaddon.m.b.a<"s2ezi7ywu9swkf","YZXptECP8CniqpL/NfRfVFkCtwuh0KhZwSvwl0mkuPI=",-907433674882938857,9182092411770797442,5560618960961028666,192895607968896439>()) {
                  case 1075797933:
                     return;
                  default:
                     throw null;
               }
            case 10:
               this.b();
               switch ((int)com.yiyiaddon.m.b.a<"ssif4tfh8aq29","hSqU3jYK1C186B+JwDhBAlIsO1rHb1DgO77hhvbtiAU=",-2536924982832148758,226742692202182070,2430369103221773084,5588484531298159350>()) {
                  case 764336797:
                     return;
                  default:
                     throw null;
               }
            case 11:
               this.go();
               switch ((int)com.yiyiaddon.m.b.a<"s340n37mumwonc","i+2gTbhl1hoGALooxo8kxkI1sQ2XuUNN9WTRzV6pTbI=",4490008569990252986,1887694189637688528,-7504675193915057883,-7440242475924508355>()) {
                  case -491558310:
                     return;
                  default:
                     throw null;
               }
            default:
               var1.b(var1.a(0) + "");
               var1.c(this.d());
               switch ((int)com.yiyiaddon.m.b.a<"sl06py2dwsicd","BZHBv00ddrTkVcqtgxOPwO+xN1J5FC4T5lDldCNQSvw=",-945812654605580362,766190620410384615,88536923832181453,-3060954796343900055>()) {
                  case -293301214:
                     return;
                  default:
                     throw null;
               }
         }
      }
   }

   @Override
   public List<String> a(c var1) {
      if (var1.a()) {
         switch ((int)com.yiyiaddon.m.b.a<"scm0wrensdntd","GmEfNlABKjVi33RO746WfWqxf14Xo4K1Tgl/FazaNLE=",-4521819265492529723,5449390560221618015,-7531935612284491924,-3139459251936597625>()) {
            case 1766972339:
               return bt;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.n.b var2 = a();
         String var3 = var1.a(0);
         byte var4 = -1;
         switch (var3.hashCode()) {
            case 758655:
               if (var3.equals(
                  (String)com.yiyiaddon.m.b.a<"s4hagp40493ow","bhe7L7Y5uH0eV7LKb6WMiYLJk1KtELybzCNF+rN1Ysk=",2664531615798199755,-5156291423347645193,-646659242731952596,-765207919093522130>()
               )) {
                  label201:
                  switch ((int)com.yiyiaddon.m.b.a<"s1qjh22ba883rh","sV1fa2/N6RiFEv4DIIOmtmJ7sz429O/6v5go3LkqZq8=",3289934257076879606,-4903823859262803711,802656528566608757,-9188473306830724474>()) {
                     case -2097261992:
                        var4 = 3;
                        switch ((int)com.yiyiaddon.m.b.a<"s1hkho35y06yil","izwwXfsl7WE35E53z2GGZf4zi9DarlwKpcug3cUrGfI=",3415371742093231085,-9103072617444887969,1175290946030315025,2157414470755344873>()) {
                           case 299893227:
                              break label201;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case 1006537:
               if (var3.equals(
                  (String)com.yiyiaddon.m.b.a<"s105cx6kf76mqv","TVFewXlNR7N2Knd98YQoNb6h8a2Bdh0U/z6dgcl7B+w=",-314834312066137866,-4546091960071063244,-2946040797665398026,-7169585475093826816>()
               )) {
                  label190:
                  switch ((int)com.yiyiaddon.m.b.a<"s3d11s99tzoz7","/qXEGjHeLsjtPnIM5uI8bYTxSllxpTKEFvm3miTGqkc=",1617303899229163807,-8821818985218479432,7871103690960842117,-8597941133386136238>()) {
                     case 325442488:
                        var4 = 1;
                        switch ((int)com.yiyiaddon.m.b.a<"s11ovpvhbn2bka","fDva2HMkd06Hj6+plrqjmlxB6sn487IWr41+g0SDZsQ=",4191390616761084404,6176897492959137599,7371509916146172925,-6248808701739062869>()) {
                           case 1343476388:
                              break label190;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case 1029865:
               if (var3.equals(
                  (String)com.yiyiaddon.m.b.a<"s3npsbkze0z6g0","Jlcy6aGXXUuh3i6XP9CrKVP6ZmBxJ6qNcS40M15EZu0=",-5551272369564446852,5236365058091944879,5041052238734086258,-7560761866399606141>()
               )) {
                  label209:
                  switch ((int)com.yiyiaddon.m.b.a<"sxcjsf42du4c4","rXcmeDZ7KpLnpJuKOOqIcLEMXmpHzs6/lh/7EvuXsLY=",-1538773020721585453,6783029578023991415,7532289163545423048,3466134517637881382>()) {
                     case 1407457467:
                        var4 = 0;
                        switch ((int)com.yiyiaddon.m.b.a<"sg9ae0i2pyuqs","rbd2YllVDQCDXXtCDNf2jm39b6yF2/b9HzWyj5tzMe8=",431764111441812034,-9210983279849186648,-7745221273153430402,-1970032495178955123>()) {
                           case 771070326:
                              break label209;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case 828536792:
               if (var3.equals(
                  (String)com.yiyiaddon.m.b.a<"s2wfgk4cm6u8zt","ov91TSp+JgiSGeDqWtBslAR9u2o6FuptQkJKo31EzoMlamQ1",-4265402979572844290,1878467059093814575,-6711384172587523999,6412288689087869078>()
               )) {
                  label196:
                  switch ((int)com.yiyiaddon.m.b.a<"s36b7ixylrug80","K7tvI4BAhRjb800W87YBKJdhFw6Qu/alecO6eibYH3Q=",-4107245061564792100,-1556377120019793633,790359039409470734,4443358622487423436>()) {
                     case 512855555:
                        var4 = 2;
                        switch ((int)com.yiyiaddon.m.b.a<"stoo8nu5dptzt","TXCgAPsEyw/hiTZjQr6fGA2Y7XH1iF2opabry4x8WJA=",-438731411055419109,-1968836415515405528,-4268813984251425526,1974064578003357809>()) {
                           case 463761712:
                              break label196;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case 889783073:
               if (var3.equals(
                  (String)com.yiyiaddon.m.b.a<"s1q5r3nw86m681","2CHpc++B2RbqFAVEMjs8ApcOJ8V993fH/uLcVst9sJ5kGDexipU=",-2978995490170052914,5502611552896403745,8321276642616247233,-416168241073717643>()
               )) {
                  label205:
                  switch ((int)com.yiyiaddon.m.b.a<"s11dr6ffl59y17","HD3Sey4tB9y1k9qmBYRhoRuDRMZ1SZkavrH4nV3vtJo=",6834429326958259128,-2465017130993421572,-6209855571052582872,6273799392721562552>()) {
                     case 881673959:
                        var4 = 5;
                        switch ((int)com.yiyiaddon.m.b.a<"s1at4eoz4zut0y","U1hoXY3HLnAccJRzNV9TXbKNzsDy07rejm2PMyLokAY=",7815510294345533130,8840989114481816254,-4207249855434182353,-3028226066337987433>()) {
                           case 2127293802:
                              break label205;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case 955440325:
               if (var3.equals(
                  (String)com.yiyiaddon.m.b.a<"s1pbigzf4opkhr","egu3cMKg/huI6FsEa9Bh8FLvdXWIYDaoBPm7Zd7k1TV18a5W",-2616425179220908252,3439436910542586451,-7102907178862761742,-2515908507255617588>()
               )) {
                  label193:
                  switch ((int)com.yiyiaddon.m.b.a<"sy3mmjzlvu17r","iucrwg88kKaEcGISujX1QoKBnr1YunkjJmT0xeyIhTQ=",-920561243565336512,-2357833632627674804,-4811153910316635824,7179803986728519740>()) {
                     case 180092587:
                        var4 = 4;
                        switch ((int)com.yiyiaddon.m.b.a<"s1miav3jizns59","ueFNxukagofFdcXMYOrhtbU+lOHmj2pQrJz4twRSBTk=",2399680122247274554,3911760923922650875,-8703980601750292496,-4182732914379209691>()) {
                           case 1355281150:
                              break label193;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
         }

         switch (var4) {
            case 0:
            case 1:
               if (var1.a() == 1) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1i2fspqqvrdj1","yrIJfKQTagN3Jh8p3Dp/le0nCOC3i8uhfIzSTBDsTuQ=",601100684381182848,6879691066600687738,-1490741926963215335,-5053017714103486380>()) {
                     case -83678786:
                        List var16 = bu;
                        switch ((int)com.yiyiaddon.m.b.a<"s2oofutw1ya8rv","J2B4HYd9GKsek5dfhSQTRAPmAU8XrU98D8V9d0yTNrk=",2765667184170608590,9157667043140839935,7841613009901996843,1818541970164063365>()) {
                           case -1783870516:
                              return var16;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  List var17 = List.of();
                  switch ((int)com.yiyiaddon.m.b.a<"s2tvsnzqy6jrnq","CiisztgIymUuebjbsmaFfRGaDDpm67iHy0gd0/XRAuw=",-3949522632562521460,-2996597963573821161,-113319327577773615,-645471027280581718>()) {
                     case 1192857771:
                        return var17;
                     default:
                        throw null;
                  }
               }
            case 2:
               if (var1.a() == 1) {
                  switch ((int)com.yiyiaddon.m.b.a<"sizopnxrsalu","kOSqFDNPJU/Q9whPbtcMgUJnEQvL60UqS9IT/i5o06Y=",2190028216995347578,-3446187596499441570,1576079592606395836,-7969373178685384952>()) {
                     case -1495283661:
                        ArrayList var6 = new ArrayList();
                        var6.add(
                           (String)com.yiyiaddon.m.b.a<"s3d57ieqnmqadi","r25Q8S7E3zD9vhxF0xVlceliUVONlYiJTYfN1NjCWxc=",-3352484050759812477,-9140280027936423599,-7655284903350750966,-6798104046081692814>()
                        );
                        var6.add(
                           (String)com.yiyiaddon.m.b.a<"s1wkbh7scozc6u","Y2ZjTbj65sf9aQhFUAYE4K/jz5RgcePV+1Bj/fmFvNU=",3181947516973187480,-323399372901716339,-1608875773680836937,-6056588649679756833>()
                        );
                        if (var2 != null) {
                           label135:
                           switch ((int)com.yiyiaddon.m.b.a<"s3cilluiduxjcz","v8dwT0ey93odCbyMbIN9rszFGUYn9KynVBmP+7LD8jk=",-484747461975859198,2481909229489316341,-3981709609978455689,-3742490060427974481>()) {
                              case -2030326570:
                                 var6.addAll(
                                    var2.a(
                                       (String)com.yiyiaddon.m.b.a<"sgleyljfazh6j","mjYyNbDfi/4Eh/IJe5DMhxl98BybQatVV0aGCA==",3214615122877264650,-8968572304130150370,-6110085062431042952,-2250863065076701131>(),
                                       false
                                    )
                                 );
                                 var6.addAll(
                                    var2.a(
                                       (String)com.yiyiaddon.m.b.a<"sgleyljfazh6j","mjYyNbDfi/4Eh/IJe5DMhxl98BybQatVV0aGCA==",3214615122877264650,-8968572304130150370,-6110085062431042952,-2250863065076701131>(),
                                       true
                                    )
                                 );
                                 switch ((int)com.yiyiaddon.m.b.a<"s2gb6rxp61anp","HHfbKyR7VNnRKR9FKZKEvvde9JrqpYLlgog1pv4IrSo=",8391944734685138157,3822277431544427037,7495670551421372693,4892095020443888739>()) {
                                    case -1183526013:
                                       break label135;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"snycywzgw7dr8","5GNRdAsk9iz6aYDQ/yijUzrFaEYjQE3l76WqJc9UCi8=",-4306056803464898620,5974990680844132302,-3495739952927974948,7820159745363046157>()) {
                           case 1998305370:
                              return var6;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  if (var1.a() == 2) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2fuonapddlp0w","f/5ZXTvZUgOhPnPEQF8351LiCkUKdMcj6xU6oVqKdVE=",-7467270472578753068,8847983588093954468,2471563561386851092,6058420159113172310>()) {
                        case -1883338116:
                           if ((String)com.yiyiaddon.m.b.a<"s1wkbh7scozc6u","Y2ZjTbj65sf9aQhFUAYE4K/jz5RgcePV+1Bj/fmFvNU=",3181947516973187480,-323399372901716339,-1608875773680836937,-6056588649679756833>()
                              .equals(var1.a(1))) {
                              switch ((int)com.yiyiaddon.m.b.a<"seh2hbuwil6yb","5Dciohv8EbYNTXt+3SwERnE0jzeSjzGIL0hGxlh1PdQ=",-6908509714163789994,-6619832986949169077,393824101846718305,-4969201083688834675>()) {
                                 case -596933391:
                                    if (var2 != null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3e1uwiryhnzga","mELVZWiNoWodndnJmF+Lo/31DbuiEa0bI2Rw2LkBeX4=",-7109095410804787440,-2794022641913256037,-1667034797071407834,-9136643540183348874>()) {
                                          case 1847132338:
                                             List var15 = var2.aF();
                                             switch ((int)com.yiyiaddon.m.b.a<"s5fpr31fplz90","V3GTbtvos6zmL5Z4jB3iPnTUuq/YWao2AgnQVwGHajA=",2221642808712075968,-2497444094584451512,-8678174156708291902,-7527715169562196811>()) {
                                                case -2132416841:
                                                   return var15;
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
                           break;
                        default:
                           throw null;
                     }
                  }

                  if (var1.a() == 2) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1qhb7bv44gm5w","xyVawa3Z6qNjFR2aoRn8A7KRadvXsIUzzTahzkIQ3+k=",-4489831460700631478,8719358554435560084,-7741018788178357391,-129579960367897423>()) {
                        case -1374153854:
                           if (!(String)com.yiyiaddon.m.b.a<"s3d57ieqnmqadi","r25Q8S7E3zD9vhxF0xVlceliUVONlYiJTYfN1NjCWxc=",-3352484050759812477,-9140280027936423599,-7655284903350750966,-6798104046081692814>()
                              .equals(var1.a(1))) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1du7xxwd2id8y","zvLLHdB9XnRt32TNqFLXOcZPLPHRHCuU7alzuCgBeMA=",-4789489011627693550,-1919566334304117892,6696277865566490418,4689519262122760038>()) {
                                 case 1190499283:
                                    if (var2 != null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s227naja3gy78e","w54WNMuCO9DlHmrY2uGxl2LvpLjYjnYDTOAEAHUy0k4=",2132959485478899932,39226412791678938,7649418377992721285,7729722488995096831>()) {
                                          case 81598239:
                                             List var14 = var2.c(
                                                var1.a(1),
                                                (String)com.yiyiaddon.m.b.a<"sgleyljfazh6j","mjYyNbDfi/4Eh/IJe5DMhxl98BybQatVV0aGCA==",3214615122877264650,-8968572304130150370,-6110085062431042952,-2250863065076701131>()
                                             );
                                             switch ((int)com.yiyiaddon.m.b.a<"s1p9d65r10z5q3","dd8YaUZWrX7yYC3q1WFw4dQSgqB0shO17qBKl8FhIf0=",-5502368196320430350,2985697517890716472,-3999413953211566262,-3212800808641577872>()) {
                                                case -1052708301:
                                                   return var14;
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
                           break;
                        default:
                           throw null;
                     }
                  }

                  List var13 = List.of();
                  switch ((int)com.yiyiaddon.m.b.a<"s2v048pnbomjiw","Ks7jnPYenCFFFjqUwgxxlHcY7r820sQDCFBxGYDg+5s=",-1018385130016512298,2017230742511594456,-306322054109139067,2433423548683191809>()) {
                     case 648439732:
                        return var13;
                     default:
                        throw null;
                  }
               }
            case 3:
               if (var1.a() == 1) {
                  switch ((int)com.yiyiaddon.m.b.a<"sebhjzjod23qt","gmvcqrgbxUBK1OqeLeJbmov6V7Nf7qpEX3Cqiq9UrWk=",-5714154315015899116,5764032619758116049,5540869510971401182,-4082033040424648264>()) {
                     case 1255894527:
                        List var11 = bw;
                        switch ((int)com.yiyiaddon.m.b.a<"s30yt7r6nut4sg","dyjK8ksGdNYn9JsxNXjdtTrQGlZome5qSIW9KmajfXc=",3402369935057017856,6922985355088857794,-7304921150650586114,-6810863151503694025>()) {
                           case -99671137:
                              return var11;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  List var12 = List.of();
                  switch ((int)com.yiyiaddon.m.b.a<"s1s9dhdpoxaefc","gRmTp02FyqhGMSx2iFFLVNGI/Pn6FF0zkGuwLuqlPPw=",-8979402736619078831,-6268724643026395882,-4424190081686820879,-2352462994275178333>()) {
                     case -1678423647:
                        return var12;
                     default:
                        throw null;
                  }
               }
            case 4:
               if (var1.a() == 1) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1w6v2mvy0ww15","P123xtrCzKMVzYvC4kJa+qguR0IEhtZQ9I4Z0raLptI=",3703127163872464033,8086654834951167868,-3914174307383521820,-8645027061937694115>()) {
                     case -657409036:
                        ArrayList var5 = new ArrayList<>(bv);
                        if (var2 != null) {
                           label148:
                           switch ((int)com.yiyiaddon.m.b.a<"s2tno6sthhwiiv","ZZ/FIie55fgqu+rmXFKX/XMpJjtTvTDm7n15dxlOdPs=",3891404089397195014,3872610437961512592,4668098896391124788,-1092794362668670531>()) {
                              case 916815247:
                                 var5.addAll(var2.aE());
                                 switch ((int)com.yiyiaddon.m.b.a<"shs5exfq26vax","nY6KG4JT2PNgaDx7ZGD/6M7ymz+CY+As87jaktZQqBs=",-5835884383649302048,6602920947838197139,-1208215011176192115,841123846494953137>()) {
                                    case -1521079395:
                                       break label148;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1i6dix2bw615q","g6kM6ZFyxYbx7QA892IBoqt9WuhmYYz8Hqc5rNWyHXE=",-6542328678852026354,4580781796865423415,-3069979593165819835,-1600771942574340173>()) {
                           case 672750157:
                              return var5;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  if (var1.a() == 2) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2mk1rsqv35blh","vj48xc0CsKHfSPREKQZV92fhpC3Lnf2dkrZrf3smx2s=",4462657092512729076,9122391838309012482,-3652070729425221436,975134916436172325>()) {
                        case 2022668136:
                           if ((String)com.yiyiaddon.m.b.a<"s2iivnxo5ul6cx","x2bqAJN6eNtDQaSrxTaYh8U8oTp9SRM1jTGa3dkawMs=",7253582403378916462,-449066580557230319,225045130113654137,-4810329004032759732>()
                              .equals(var1.a(1))) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2yoeqzbosv8yc","N5c2MErEUj6uT0yBTI3sfQNA1WjbBQ+mK0AgN9ym9zg=",-8622731489793361029,-4926813977960591313,2788025741348104477,52048374975156042>()) {
                                 case 12378945:
                                    if (var2 != null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"sw52irkxgf5zb","JnQVK+naYwncZ69k1O1qYX6gi1TManYHRsn7H/WdCm0=",642851929189604845,-1520357143491533070,-8054064478733288420,3105357924533357085>()) {
                                          case 300990879:
                                             List var10 = var2.aH();
                                             switch ((int)com.yiyiaddon.m.b.a<"syctho5jc01pp","VBUrfU71aDqYrs+mtYx9b+quACT4yQceAmQmcGH2l5E=",5670010047311978972,-4160574796214931192,-1375214184999210938,4596334988860471221>()) {
                                                case -105646409:
                                                   return var10;
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
                           break;
                        default:
                           throw null;
                     }
                  }

                  List var9 = List.of();
                  switch ((int)com.yiyiaddon.m.b.a<"s3hpxdojj8nenh","NekDbUN8+XMZilqR9g7eDnubroER1aPyah22N/QApBY=",-4839105454621111845,1519184545399930361,-3124037485734169633,-3405160635202161206>()) {
                     case 321979690:
                        return var9;
                     default:
                        throw null;
                  }
               }
            case 5:
               if (var1.a() == 1) {
                  switch ((int)com.yiyiaddon.m.b.a<"sr184vzzat1vg","G09N9yd6exRqqzFWjawjV8N9/rRZd3N9z2cLodzj6Oo=",5216370525711548635,-2082932660129108586,6704038119676378162,5638312730962086846>()) {
                     case -2140735772:
                        if (var2 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3nnxdkrokixc2","JHYIDh73xhwLRFPwFYXDOAWCgN+n8DBCBJ4dTqCT6/E=",3053391205933907829,9182328059019719155,7135001014136081804,4897930422222885045>()) {
                              case -739058133:
                                 List var8 = var2.az();
                                 switch ((int)com.yiyiaddon.m.b.a<"sevcnaia9mdd6","I0wANk0yvaFcHM6vafvIEzrBJGu0NWPYV294b0c8J2w=",-3721900245439219072,-3108402920336511169,8581168169728568491,-4358538664727842967>()) {
                                    case 1673860192:
                                       return var8;
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

               List var7 = List.of();
               switch ((int)com.yiyiaddon.m.b.a<"s3a8cw5tvyotn1","O7rMeAHHwboXJuniDybD2W1K6V8ZGHhed1adGkHTd8I=",-9156886794271089643,-7001996420229329730,6080297541335308619,5066845060935996194>()) {
                  case -687585582:
                     return var7;
                  default:
                     throw null;
               }
            default:
               List var10000 = List.of();
               switch ((int)com.yiyiaddon.m.b.a<"s3edqbva4q6e2g","cqNJA2zSvutfpNSO1vWRjZnfXqP9FFOdu28O+v4tbno=",-8071015150647641467,-7414681917422057150,4564064357543263519,3962099077697276068>()) {
                  case -1187866422:
                     return var10000;
                  default:
                     throw null;
               }
         }
      }
   }

   private void g(c var1) {
      d var2 = a(var1.a(1));
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1m0f9m8c2rbar","Z+fUi9jbrDMqw63Dd+ZZtV/Nkyz5xRauajBesO4TsyE=",-3586252453790468579,5493173442401657443,4912164191523484233,5588005883678162772>()) {
            case -795341299:
               var1.b(var1.a(1) + "");
               var1.c(this.d());
               return;
            default:
               throw null;
         }
      } else {
         this.a(var2);
      }
   }

   private void i(c var1) {
      d var2 = a(var1.a(1));
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s13bcdmmbutaeh","ZqnG0r3JORbGrEVMNkiJkIMP2antHwZ9TxBxI2iQQ4I=",6069836839227031579,3974769269398171334,-5957761203978899377,3125022336618852087>()) {
            case 1204674848:
               var1.b(var1.a(1) + "");
               var1.c(this.d());
               return;
            default:
               throw null;
         }
      } else {
         this.b(var2);
      }
   }

   private void j(c var1) {
      if (var1.a() == 1) {
         switch ((int)com.yiyiaddon.m.b.a<"s1cmf3daj4iuqi","ZEF+wIb7W/9NqEe1+TTwWzzpJeXxOkZRqk32Je4BLTQ=",-2663668213189672014,2755602799727624655,5428431973734346337,-7815240478163490250>()) {
            case 2017343274:
               this.t(false);
               return;
            default:
               throw null;
         }
      } else {
         if (var1.a() == 2) {
            switch ((int)com.yiyiaddon.m.b.a<"s2azez4kdhng7m","cCPC0MRr6xcalIZxaZZs/rcRJnzV0p/TMcOSsD7lsfg=",-387067299254072637,3123535327078147693,-1926463514724627496,4982510178225047495>()) {
               case 1727432568:
                  if ((String)com.yiyiaddon.m.b.a<"s3d57ieqnmqadi","r25Q8S7E3zD9vhxF0xVlceliUVONlYiJTYfN1NjCWxc=",-3352484050759812477,-9140280027936423599,-7655284903350750966,-6798104046081692814>()
                     .equals(var1.a(1))) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3mh2vqibf7cmg","goPcc88rsXk0/lx8Lw6nUFylKGYE59TZDHGJulTIPbg=",8874226914654708359,-5546988263659576224,-5372177356024561370,718031488681670420>()) {
                        case -1740578853:
                           this.t(true);
                           return;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if ((String)com.yiyiaddon.m.b.a<"s1wkbh7scozc6u","Y2ZjTbj65sf9aQhFUAYE4K/jz5RgcePV+1Bj/fmFvNU=",3181947516973187480,-323399372901716339,-1608875773680836937,-6056588649679756833>()
            .equals(var1.a(1))) {
            switch ((int)com.yiyiaddon.m.b.a<"snfvqxcme07tu","hOjYHev9r7H0hAY2HVr0NxLJ/UfMISJtDthL+QBgGgs=",1929216276841298286,118313385864242042,3136565606558925437,-7244477731437663645>()) {
               case 986571003:
                  String var4 = var1.a(2);
                  if (var4 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"sxojm13voeqsv","GpQsAe/UGyBg1mXTo+fAPYcXWBNKSoaLlOlblAscLvk=",-6681985143708939380,7696981229972634238,-1879059325321287337,-4164787510012393243>()) {
                        case -1366017953:
                           com.yiyiaddon.d.d.a(
                                 (String)com.yiyiaddon.m.b.a<"sqz7fubk633af","6+QZHPPK098aa0vAqUiyDSxxduhSjjne9OEY83Hv3vUO2fCcLn8=",-5840169825291205960,-6192287268964251247,5183046899392997009,-7287729381131471577>(),
                                 (String)com.yiyiaddon.m.b.a<"sxd7hi5xyy93o","RcymXkdjKqJDFX5Xd9nm6aPIAVmtIQcNfNLl+ipvcjZcyPQw4k5DGXxvZlyGrgSPKbNWaLiLQc80AI9Q",-2857018642604722791,6120038970644678408,6283167034102917469,4289784442388981747>()
                              )
                              .b(
                                 (String)com.yiyiaddon.m.b.a<"s30omk3rqulxx6","Ns6yE1ct7pM6W4V7wT0+UVlldDZKDEnEOlnG+yvgGoGggleJ",-448959692225664358,6751851729315830442,9044140549627527870,1856316901077225928>(),
                                 (String)com.yiyiaddon.m.b.a<"stmf6069xh719","FF0l73ltL5JNDZt9FbJQoZ5YwcA+xIdVRgpJD1QBR8UQvqG1LVHfOaQWIagtXovpdTHpzmSmyJEd7H07ttvLLXEmTF2t7TwuVSy/ScTFPR2VB+2nXcMsupi4GEi8Al6f/FUfpq4kcbrH5ah+yX8m9xDq8LE6NfRoLDSUDyRAvy6GaKjCHZMgmQqW",9068936939347025808,2980266228684529364,878519122592781869,7447151469569293558>()
                              )
                              .b(
                                 (String)com.yiyiaddon.m.b.a<"s2f9cctnrhcxfi","zcKxp1FMqhnFjRWzIHutyNAstGNZPgQ7yyZx1bTTxCQ=",6272818146866268908,-7582954333259427457,-4275680525777432926,-2998150491988153831>(),
                                 (String)com.yiyiaddon.m.b.a<"s3f8w79m4jbz8o","KbWGGz/730IDXmBbTSJWxXc2SPE8Rtung/h1a7084f0cCgkw6oeDz/o7JIO1aNBpRdCDrwR+TBcT+31L2/qVGYloE7IqSn6rqhEx643uEBj2Rp09U43FQz1m",7981002033231963234,-911239122193337225,1144148997616720967,425885487962561482>()
                              )
                              .a(
                                 com.yiyiaddon.d.d.a.FAILURE,
                                 (String)com.yiyiaddon.m.b.a<"s1lqa3aedlbis6","UqAGee8IYLfGP8cIrgpFfZUe6qsuka3zXEolmUVKeG9aEw==",-7115298189385034512,5233781885210255437,3028147469778651481,-6030993933201413617>()
                              )
                              .g();
                           return;
                        default:
                           throw null;
                     }
                  }

                  this.as(var4);
                  return;
               default:
                  throw null;
            }
         } else {
            String var2 = var1.a(1);
            String var3 = var1.a(2);
            if (var2 != null) {
               label42:
               switch ((int)com.yiyiaddon.m.b.a<"s1de2b7oqwd0df","+kh8Y81AClABJS3WFR65e4jyAQOvGiC66qt1uHnHhwg=",-6974107082951306741,4066810554824401135,-6707475814287694955,-1641545010319624138>()) {
                  case 428599870:
                     if (var3 != null) {
                        this.a(var2, var3, false);
                        return;
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s2f5wlskqt2aft","hbCd3GRo+VKFLv6cxkng4ysSCgB7iC7rxM0aH4uVLmA=",2942602512626731558,-2772396112766158751,292898855653332403,-7613645488508498386>()) {
                        case -325014341:
                           break label42;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var1.b(
               (String)com.yiyiaddon.m.b.a<"siotui1b2ig6c","xSFuxZhxuVuDWCdBVia95EvOiEv6wPVoOaD9O35DwIox4xXk8mTmO+UUTMb6gXT3ZWuN+IsfU0ndJ0Yk8eE=",-7354193382265194114,-3670336210663440976,-685379234967103896,4768856420006698547>()
            );
            var1.c(this.d());
         }
      }
   }

   private void as(String var1) {
      com.yiyiaddon.e.n.b var2 = a();
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2d6df1zh3vos6","KC+g26w3S4oDrsPD0FfB9jyZ6EpmAc1QgmWlOEXK4jU=",1612340672145139615,-6985384114242284186,-5059498988852926695,-6763764682825563468>()) {
            case 1467329169:
               return;
            default:
               throw null;
         }
      } else if (!this.a(var2)) {
         switch ((int)com.yiyiaddon.m.b.a<"s3hdtgsrjna6rd","EGlk0rv7OiZOftoscJPVn3/GJrOv0mUP78JD7ESEsXk=",-1045305389577103305,7531651261028804154,7147951359118665287,-5420659118250453336>()) {
            case 1464408337:
               return;
            default:
               throw null;
         }
      } else {
         String var3 = var2.ax(var1);
         if (var3 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"st0jvujqri7xp","Q5uSOxAYflgFbH+Tu5gsa5omOyrpmbuOberItK6xQHQ=",-164211389672611390,-3669415507540722366,-9145461640482282078,8254057240616583795>()) {
               case 1486356835:
                  this.c(
                     (String)com.yiyiaddon.m.b.a<"s3v603xwob0qu3","5fB6M5W4LLtVCiaEyLfG5Ut40iCMHwDm+NWKo8oeM95Vh82R0dP+6U6WCw4=",3388149974257246798,-5619194667595625426,6670236643370074916,-8033039136911015054>(),
                     var1 + ""
                  );
                  return;
               default:
                  throw null;
            }
         } else {
            b.d var4 = var2.a(var3);
            if (var4 == null) {
               switch ((int)com.yiyiaddon.m.b.a<"s2yguuvfwco3wz","0TBRJJXipsxkBXO0G+egBpJWfWo4IARIafl8F+eNS7U=",-2514129692152738306,-8546120134129805554,-4982405723213915408,2076200229081705940>()) {
                  case 2101738102:
                     this.c(
                        (String)com.yiyiaddon.m.b.a<"s3v603xwob0qu3","5fB6M5W4LLtVCiaEyLfG5Ut40iCMHwDm+NWKo8oeM95Vh82R0dP+6U6WCw4=",3388149974257246798,-5619194667595625426,6670236643370074916,-8033039136911015054>(),
                        (String)com.yiyiaddon.m.b.a<"s3nzws5qm5tz5s","UkeWZEbt99+fpkSmrBm5xuWfVuFhM/AlJYLBx6TrvL3xr8pscll/Kv6ldJOvYWOkkJ/4Z/wOiWPMWA0uBGf2BiMgR+uokYUC",8059008038545985855,2641242526962380180,115559779660558978,4945607607621646040>()
                     );
                     return;
                  default:
                     throw null;
               }
            } else {
               String var10000 = (String)com.yiyiaddon.m.b.a<"sqz7fubk633af","6+QZHPPK098aa0vAqUiyDSxxduhSjjne9OEY83Hv3vUO2fCcLn8=",-5840169825291205960,-6192287268964251247,5183046899392997009,-7287729381131471577>();
               String var10001;
               if (var4.dm() == null) {
                  label79:
                  switch ((int)com.yiyiaddon.m.b.a<"scmz0p3w8sroi","+/pgsZYEgONoLSjwDw5/pjl14gKImPRJkjfO9jULIm0=",4630023703469846171,7612459936262205336,8357480063254755401,-3095422245707728094>()) {
                     case -2105492251:
                        var10001 = (String)com.yiyiaddon.m.b.a<"s1dxmjk5o2h8m1","3X5K3BNrqjGakFQOp0iaheUMpM14bTszm4odGptr0D8ZD46XBcNwnb2DrHvODCySNbc=",-7003871577462220496,5474028111146457685,5553912983917606642,1476379428229185901>();
                        switch ((int)com.yiyiaddon.m.b.a<"soswubppqg3m9","73onurPengD1VeNfnZHRB8CQs+8sHkVVQ8zAHasrZgA=",7057306888398659783,4895878853435032236,8561230696893229979,-728482906929663867>()) {
                           case -481900950:
                              break label79;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10001 = (String)com.yiyiaddon.m.b.a<"s2asvqz2ckjn1y","wrb3dWwyOBGKecHtoX2SNxJ3GhA5vSnmvEIZWkmtx2YPmuh2BfTk91+o",6466462610791266780,-8553711087931959900,-1526084944737070914,9131286877055364437>();
                  switch ((int)com.yiyiaddon.m.b.a<"s33m8yfx2fvpgi","C5WMjmidnDjylp6jgW6V32N/xEA9G43oD33U/xpa564=",3346873751211056091,2401981722788910892,1670313862310752913,-8125112573192142836>()) {
                     case 482507778:
                        break;
                     default:
                        throw null;
                  }
               }

               com.yiyiaddon.d.d var5 = com.yiyiaddon.d.d.a(var10000, var10001)
                  .d(
                     (String)com.yiyiaddon.m.b.a<"s2kzvbp52kgirp","dvNKp++75HBjFYt06jQ3zKCUEfAuQfVQqL5AMCCYfjs=",-901505182134927223,-8611408926587085932,-8029069632447620063,5505936193956503889>(),
                     var2.aw(var4.dk())
                  )
                  .c(
                     (String)com.yiyiaddon.m.b.a<"s253d52x94o1mv","RCpY4oBsgL0pjj5qPz3p7j/ON6xpON17JMeP8qP/TiddQnCmxuQhLZzC",-7563966088569937483,-7206198668365708895,-3394674626273371807,-6962824681205931077>(),
                     var4.dk()
                  );
               if (var4.dm() != null) {
                  label74:
                  switch ((int)com.yiyiaddon.m.b.a<"sm1f1082yu86s","79izj4664VTN/NuiHze16HuHmxUjGBlRPnjCzb8y7x4=",-2560629927355162553,6337765775554733775,8382733041938459115,-5648968685591985408>()) {
                     case 981555158:
                        var5.b(
                           (String)com.yiyiaddon.m.b.a<"s3v20c9jqh8s86","lUR4mp4zkbRPPYnzrL6R9Ba8k4KHp5ymxyrvemwwESCvR9JVGx9mf6fu",2737096351544019006,6487507523399467222,-810353660245911062,93156368834668960>(),
                           var4.dm()
                        );
                        var5.b(
                           (String)com.yiyiaddon.m.b.a<"s13ub2ysggzjm4","nU2ATgF1SKX1dIdKIxFKvO2lV1eMds890wK+ltyAXKaMT/alSPw=",2082624093582767030,317582640211255074,-8357712872276634747,5133208127735862586>(),
                           a(var4.a())
                        );
                        var5.b(
                           (String)com.yiyiaddon.m.b.a<"s2acz9r7inaig5","FH+v0LoGDuVAlJpmgLFJOeLPwXncCBDbJoBIPHtzg5EJ/g==",7647061906678561221,-454896479978713889,6575461363285076573,-5578803036969514839>(),
                           (String)com.yiyiaddon.m.b.a<"s661ffgpmj763","bwMh8SAnk+cwgwn26AEvmNHglzFw4t4HGbuILXmUtxXEyT8Hps9Vic8LYQ0PaqnOqw77EfjGdOAcohoNUbw=",2386723709761336836,-8583654869666376112,149957489932183733,1949137469945624961>()
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s1odbfsy8p1etd","bo8pKeKI1CGBezsTS4OEezGQEoqc9zuzJiwMRddfySQ=",8144741592635924706,-6061584343206249476,-9208514550458858189,-2549830894683146793>()) {
                           case 425104941:
                              break label74;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (!var4.be()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1zx6ve9eq37w3","JCmw/mOguay8PhOtvNBwDbri6k0tvd6kr7etmvQqlPo=",-5468999961764931001,-6406171725835718729,8340256148540269534,2492202203182422275>()) {
                     case 1917395488:
                        l.info(
                           (String)com.yiyiaddon.m.b.a<"szfnmxdc6xc62","osJsObnCk/Uj03cWyK13jyJImMQb9BjMTeJGfvBEbmn8OZof64QQKoteEO9RpXb4sK3i6n6SJHU6pUnag4OpyQ==",2398211735175381068,-5242093717754032870,4736635880066013699,-8059666474218732724>(),
                           var4.dk()
                        );
                        var5.b(
                              (String)com.yiyiaddon.m.b.a<"s3kl9hjq67gi6j","wVgk59k2czod1b7BM/infOcNgDu8XpI0yn9NMB+bdUU=",5464812756637643807,-4538333885441950261,-8553727997525455549,-7061260727369391346>(),
                              (String)com.yiyiaddon.m.b.a<"s3od5a8wne8hv9","dK7+bGY8BzS5aEEFJretD7bazU2Gklu6J3Dy5g/43iFiYGM0GOzJ/h5HVcZFLObK",4013700281685277908,5681011143181393516,5137002800925329456,5570613303599584771>()
                           )
                           .a(
                              com.yiyiaddon.d.d.a.FAILURE,
                              (String)com.yiyiaddon.m.b.a<"s1lqa3aedlbis6","UqAGee8IYLfGP8cIrgpFfZUe6qsuka3zXEolmUVKeG9aEw==",-7115298189385034512,5233781885210255437,3028147469778651481,-6030993933201413617>()
                           );
                        var5.g();
                        return;
                     default:
                        throw null;
                  }
               } else if (var4.dm() == null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1eoiqdwkz9obo","Xm92a9cBee7JvlPFLR1fmb10StrpW0NK3P8j8v4Yrx4=",2851115650391278294,2174094283309630981,-4767827406397580166,7513774082811012213>()) {
                     case 59127509:
                        var5.b(
                           (String)com.yiyiaddon.m.b.a<"s2ttwpubwom100","6aCGi3L6co54dUi3bb2IfNUKeykAmv517Wh8R5W886A=",-2656348470391690190,6850387114923916412,-5805745498915200595,8905495306722982346>(),
                           (String)com.yiyiaddon.m.b.a<"s2543ndktg4j56","hDFbiEyvSEIpXbcaPbJrw6a0+UxlYWugB9F0lmVM1U1HEg5oU+m+Pzx7kKAQJ+nmu6UTiFT+xKOSQyeGQ3uMnT9pSTfJITBN42kIeg==",-2433734632178897075,7802242940315101829,2364804582136149681,-8979328405352991850>()
                        );
                        List var6 = var2.aF();
                        if (!var6.isEmpty()) {
                           label59:
                           switch ((int)com.yiyiaddon.m.b.a<"s30knooa6j3t7","Nen+Saa/CahzJS2D/jws5zWWhYlbMplUF7YGkmAe85Y=",5687231924135215958,-4170309692426189023,-7930783957259254126,-2724281947972534816>()) {
                              case -1112903621:
                                 var5.b(
                                    (String)com.yiyiaddon.m.b.a<"s1zo992accr04c","heEtpiOv5etQXVJZym8VGTb45VDoJePpT6rLl5hHhoexkK61mTd10Ed5AI8=",-7810140436107476205,8475842462863645306,-9172405072174527226,828866814412741523>(),
                                    String.join(
                                       (String)com.yiyiaddon.m.b.a<"s2nb8sykxq7xwo","yXsN6QVD7cZQPS9GLLYoT4iWFKr3WZibMFBjd7sf",-391009157573186151,-6232577036565373639,4459403840164482883,4230277306878410429>(),
                                       var6
                                    )
                                 );
                                 switch ((int)com.yiyiaddon.m.b.a<"sanituga4alch","OviSnydwW71OqNiEEf/aOSGUcMEI7ayIsk6AS3vj9t0=",-4691360101594740194,2086339026729561050,50886846268418867,7462031360897887289>()) {
                                    case 505139336:
                                       break label59;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var5.a(
                              com.yiyiaddon.d.d.a.INFO,
                              (String)com.yiyiaddon.m.b.a<"s1lqa3aedlbis6","UqAGee8IYLfGP8cIrgpFfZUe6qsuka3zXEolmUVKeG9aEw==",-7115298189385034512,5233781885210255437,3028147469778651481,-6030993933201413617>()
                           )
                           .g();
                        return;
                     default:
                        throw null;
                  }
               } else {
                  l.info(
                     (String)com.yiyiaddon.m.b.a<"s2w5wdtt9wu1dq","kdMBFhyuHnWNddSq0gxOhvN4LAeai5NX6jo/Zx9/Hmr+Cm00Y/pmJHdfX6wMvCMmf1f4fMbZZVGwHKMaNmmbCYhZOpMiv+72QuxKCEEOzE/NjJjo",-6307880098768340726,-3318475716140122463,-8859036268213922557,-3058609280518687383>(),
                     var4.dk(),
                     var4.dm()
                  );
                  var5.b(
                     (String)com.yiyiaddon.m.b.a<"s185lmlx9u8t82","z/vizYc5TPyfADOqQW+jZNa//W1nCCnioSSSE8ZK2rI=",-3810101435659976899,7005993847171455593,-4612194164766956635,3244902763465313161>(),
                     (String)com.yiyiaddon.m.b.a<"s1mniz91n0d00i","5H7Qb/ApBMx0xCXHU99tDDQY/pyq9s6IPnxlIh1TexLqRw0Zm3RLXHXklrpwl46s4Zi7DMP4dQhxhwcie10aIHLElLXsX/ekuxvKaqjlgdqdyGcb",7219640655059295880,2516484236131417483,7464238209982661615,-6060585790528937158>()
                  );
                  if (var4.a() == com.yiyiaddon.e.n.i.c.DOCUMENTED) {
                     label65:
                     switch ((int)com.yiyiaddon.m.b.a<"s3a8q7ke7p8j1d","Dq3YEfmoiqZVEoa2BR4tt73/NwMm57uSgX4/diIMgu8=",2245382489359301450,-2924723602880719631,7324691091814361280,-5297827774906328299>()) {
                        case 106193442:
                           var5.b(
                              (String)com.yiyiaddon.m.b.a<"s3h0s8ajofg7yi","8NayHNkz9YST7exDjV2SML9qi2qUXmp11ZrzcVMqUSc=",4914675784182737214,-6355348779321136517,1367721111363543020,-688572789179785182>(),
                              (String)com.yiyiaddon.m.b.a<"s3o1wz92c5ov0b","yU5KIqSlXzsVLzaJTBUrdwgiA1Zhjk/R0Ze2kUT+LZK+OOPu1nuy+OHF+8k/0xp8sJk2MZcIQC30VPiTsfWhckUINK+guSomBinPbHEmjuJrVmdLLpbNuAUm9xjxtJhQzO9NPZREkVjhMb0JHM5slAwtZRxSiQxHuvo=",-1576080051366148780,4750954185482689405,-847988851460636752,8027161156469791757>()
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"s2xcdqer20f8qy","rdgGN5BhU2CbmXimxsV7C4M+S1/WAW/wOBd+OQ9cMI4=",-1179319535910031831,-3645816209098199006,5420826521251473068,-7298086925247192310>()) {
                              case -1773383244:
                                 break label65;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  var5.a(
                        com.yiyiaddon.d.d.a.SUCCESS,
                        (String)com.yiyiaddon.m.b.a<"s2r0lt4fg2ua1e","/Naj3bP9IyFMzXdUlw0BigASNhgJDreMzAW2fg9/rVWJVg==",7406282314227963396,2631797530855704445,5646388837691274674,5008117544759356846>()
                     )
                     .g();
               }
            }
         }
      }
   }

   private void k(c var1) {
      if (var1.a() == 1) {
         switch ((int)com.yiyiaddon.m.b.a<"s33iql7rd64hp8","/1aW2Kt28WeCPlFA2nGCFd8rlgctk0tb25gHu7R3c2U=",8642974158096621154,-1275290589048949419,-7292025462168147412,-6533498624425493768>()) {
            case -1127136897:
               this.gz();
               return;
            default:
               throw null;
         }
      } else {
         String var2 = var1.a(1);
         if ((String)com.yiyiaddon.m.b.a<"s1wkbh7scozc6u","Y2ZjTbj65sf9aQhFUAYE4K/jz5RgcePV+1Bj/fmFvNU=",3181947516973187480,-323399372901716339,-1608875773680836937,-6056588649679756833>()
            .equals(var2)) {
            switch ((int)com.yiyiaddon.m.b.a<"s3rtyrzlmwecrk","8ckjDY5PJC55G+FNztbajU+XYhV/kknHMQeyxg5v23Q=",-3377298906648032595,2717286024817026261,-7183646223762038565,7443386301006867256>()) {
               case 1208242006:
                  this.gr();
                  return;
               default:
                  throw null;
            }
         } else if (bw.contains(var2)) {
            switch ((int)com.yiyiaddon.m.b.a<"s3l7etx64gmpo2","dqUswatZjehLDXzB4lkAZuDzMPaZDddM5MlDjaic97c=",7542447021304868008,1053788605137688793,-943736713184226849,-2090567393062361104>()) {
               case 1154660970:
                  this.at(var2);
                  return;
               default:
                  throw null;
            }
         } else {
            var1.b(var2 + "");
            var1.c(this.d());
         }
      }
   }

   private void a(d var1) {
      com.yiyiaddon.e.n.b var2 = a();
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1kpr86mqmx95o","XCwmm2Mz7D2KXxrwBDX9Fa5sSrCAo6dC2ebAHEExbeE=",6392518320028218477,-6656853987141482201,-1511662344928081208,-700260159208319147>()) {
            case 4914345:
               return;
            default:
               throw null;
         }
      } else {
         var2.a(var1);
      }
   }

   private void l(c var1) {
      com.yiyiaddon.e.n.b var2 = a();
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3doo9bizypyhj","NfC1bO764n5mBvXjkgPTsJOB1KLHQTH2jgPasdibvlQ=",-8512265653396182444,8584365704628793499,9152001324293897008,-1233316851809605377>()) {
            case 1718799093:
               return;
            default:
               throw null;
         }
      } else if (!this.a(
         var2,
         (String)com.yiyiaddon.m.b.a<"s2839zv1zzkowo","DVXSkH6jmfZXtW9Ddf5njV+22/Ozezj9Vu6nuz5f2KyZmLI4F+wKLJRi",7314439165247593707,281351404632818924,5974160514375614703,-226280201202771072>(),
         (String)com.yiyiaddon.m.b.a<"s3byij5iel2062","BZ69buBHXmp21xNo9G47UYlyn59Ve4J0V8SNUCRxWasdFQ==",9151459354653124679,6843975542162487745,-4157971095900692998,2452592799202457557>()
      )) {
         switch ((int)com.yiyiaddon.m.b.a<"sfi41ff1fuhy3","SySP+F4Vh3fWJo8GktAh+q7gKe1i0MY+uKLmSx0mMYs=",6539919398523528399,-3116179558875335241,-6893772813451560205,3463288971240396326>()) {
            case 1476340192:
               return;
            default:
               throw null;
         }
      } else {
         String var10001;
         if (var1.a() > 1) {
            label25:
            switch ((int)com.yiyiaddon.m.b.a<"s1pak6858ca55g","OsA78mE52gl0zZLSTmOIPEU8GJMOSbNf3Ah4S0brpHY=",170182033425820601,8301111388364328648,-1782851548326884487,8423841184836789790>()) {
               case -698845627:
                  var10001 = var1.a(1);
                  switch ((int)com.yiyiaddon.m.b.a<"s2svxnw9qbsaeg","na4DpgcC6mqLUoTTC7wj0AVc0N/ZBS/PO+Y8yqJEuYE=",-3816649254649340405,-7854692993194038994,-2400688750388651991,-8813815924229529568>()) {
                     case 982579113:
                        break label25;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10001 = null;
            switch ((int)com.yiyiaddon.m.b.a<"sxbl6evwo1p8y","/yCGFsY5zbNT0EMZCO93Eh5WVgtibwZGKZPtpiFPEbI=",-4450092371054550521,-4453197221746147468,-2107839532321229387,7150125308799235592>()) {
               case -1599386366:
                  break;
               default:
                  throw null;
            }
         }

         var2.R(var10001);
      }
   }

   private void gw() {
      com.yiyiaddon.e.n.b var1 = a();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sml26r5mepznr","Qtx3p4dU9LoJYVZKiGpCQvPkRzmp6gfsnNVXB9a49n0=",2196756699745371418,-5890607927492646217,6346108519545999632,3359426195958260758>()) {
            case 88199772:
               return;
            default:
               throw null;
         }
      } else {
         var1.cM();
      }
   }

   private void b(d var1) {
      com.yiyiaddon.e.n.b var2 = a();
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2nzwr40r89feq","5RZBJG5FpIyfQxNOztyvUMvLuLSia3WRpn4uy3yzAzg=",-7511179788602116661,-3811169655235443412,-3114810359403891254,-6634469598575325139>()) {
            case 1719834711:
               return;
            default:
               throw null;
         }
      } else {
         var2.b(var1);
      }
   }

   private void m(c var1) {
      com.yiyiaddon.e.n.b var2 = a();
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s28xntqvw9sic7","BbcFSHatldaBmqlXCDiYBQtVv9cFwv4EpNaIBvbkLl8=",5362395292290372453,-640819124324531951,-8120229115313996855,2023534227437176102>()) {
            case 202824316:
               return;
            default:
               throw null;
         }
      } else {
         String var3 = var1.a(1);
         if (!(String)com.yiyiaddon.m.b.a<"s3kle7656nz9xe","ethBrfJ20GCOh8jeX193dODkXKco++LswDwUCoT3N2E=",2761385569709260787,5934831969200915616,-1079787739536779125,5102913609510164178>()
            .equals(var3)) {
            switch ((int)com.yiyiaddon.m.b.a<"s272hu8nyqc2s8","qwNFXCrYmJSuevHCAHHaghALUgjCJW5/wwGANa8X+HI=",-3758907838930240500,2102878390198026144,-1053316371971620250,-8412451889046641889>()) {
               case 1347701705:
                  if (!this.a(
                     var2,
                     (String)com.yiyiaddon.m.b.a<"s3lo1o5eyppv2q","aGOIHae84PPqXK5GXffBalBVe2lcp8plCMLwUomzvO+ID0cdgMX1nA==",5190494563090881623,275283831570396942,-6861860601414490104,7136249353495169854>(),
                     (String)com.yiyiaddon.m.b.a<"s1rdnei7tuasxi","pHpkWBM/FE4Y1/Cr2s6284gYc9iJ9NVEVOPnEHU6ievmig==",-7070387080086612770,525777181551800983,8409634178981689177,-7358869306438894224>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1zxykry8nei9x","U7mgLUI9QMXpF/3W0jlLpDBNms4+8Gphj6mgbqbQwmo=",7543334426635569735,6010960861764991246,-2198271106406291584,-5901020481636126910>()) {
                        case -1025176658:
                           return;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (var3 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"sdizcoo5qpdqr","1hL9KfoUwGLp2+DOJlVLwQuC/2zOllBXBbZoMOUBSJ8=",406553019818029532,-1723643135894442985,6986524659120715356,-2198457075817827413>()) {
               case 1176437573:
                  com.yiyiaddon.d.d.a(
                        (String)com.yiyiaddon.m.b.a<"sqz7fubk633af","6+QZHPPK098aa0vAqUiyDSxxduhSjjne9OEY83Hv3vUO2fCcLn8=",-5840169825291205960,-6192287268964251247,5183046899392997009,-7287729381131471577>(),
                        (String)com.yiyiaddon.m.b.a<"s1rf736tn1owaz","OBVueUyCPuBBAKIaEJ/vBAkSr07k4FTIgZMG466jW8dLILCRkZg/JbFcV/c1XMRNlSA=",5081507967475759744,-4159990451917139514,-7531794102753004786,-1146277794174218978>()
                     )
                     .b(
                        (String)com.yiyiaddon.m.b.a<"s30omk3rqulxx6","Ns6yE1ct7pM6W4V7wT0+UVlldDZKDEnEOlnG+yvgGoGggleJ",-448959692225664358,6751851729315830442,9044140549627527870,1856316901077225928>(),
                        (String)com.yiyiaddon.m.b.a<"s3vy4748ubgduy","mFYvvEA2LzdMLkZuVjXo9wo24BH2kEEUjtxdIkL8lb26nX/S7JeULpMcTDsyev3DHuJtyV1L6RuvZXOgdaWHT8lc2rb8dX/jXKBiL9iMQ38YH6O4SCnIyALitfo=",1602248707707705603,8562406200958634254,-5738920705736044528,1505872368301097400>()
                     )
                     .b(
                        (String)com.yiyiaddon.m.b.a<"s2792fo8lpppeb","/XaX+im6+xxgogeZjD/o7oHNo826UlNMktLBFOewLlA=",7321331569428824781,3747762963793150846,3227749346660513577,8713705364711105184>(),
                        (String)com.yiyiaddon.m.b.a<"s3pnsoxafenb6v","8SD7X4j82yv6m1G1er/hkZCcLUB9CwDm6X01IxK/YAMbVdNoDrhLWWaJJkrdK3kDWd0UGZUgbybJlPCBMaaBKRKNxDDvJJPoOv9rGpzY1yCvrLUE/T0=",1301879674117682737,4133067634853342654,3756757037349944337,-3891029794357856393>()
                     )
                     .b(
                        (String)com.yiyiaddon.m.b.a<"s38cp7k5twqyi1","IDkjgeH0ICeDP1HWE3d/K/6nrPONyCXeuJqOkZY7kD0=",-1634932372960291524,7149871859688571493,7224266377065995166,-6079965603936431041>(),
                        (String)com.yiyiaddon.m.b.a<"sukqo9wsamr1n","Pni+rTcAdOsBGf0Rp4TTb43X/YQxhJUsAb+KPcwH6/pGrqWWghKCZwxOF7o5ziOu7+dDFE85l08agl6mThp9A+XsiQdyhEC3qAr1LA4JJuV/TH79UTWnaM1cTUAxNHk4nda+S1CvMvsNdVE7PGvhhM1wNcjnErY8STYj8g==",-8725570454502798824,-1886657129021397076,1644503691752556096,875908832084132754>()
                     )
                     .b(
                        (String)com.yiyiaddon.m.b.a<"s1w3ksdumbi6tm","ZUxz4gZxQltmAWLOb1bUMmPnDXpE0yIHuG7C12m3ex8=",-5966378536111085168,5411513156889598171,-2543854331708638675,8640805943618465168>(),
                        (String)com.yiyiaddon.m.b.a<"s2d0ya93zukvmm","NciSzguJvoMkKi2ev61F2uaqP2QD7M2Fvyc4+qfWLg2jZeHJ+cuGZzGW7Rq+Eg==",3520507888135157024,-6788262742362807551,-3666430723027448462,4349792429024165687>()
                     )
                     .b(
                        (String)com.yiyiaddon.m.b.a<"s13d0yrazoplrh","Ahe508U1XTvH3hSO6tlHwKRRuhfqPb09/x1SvONrHshcvObRqSNBY5yk",4437088832742601393,-8004879458694008026,-3911918834262297818,2257824261143967116>(),
                        (String)com.yiyiaddon.m.b.a<"s10xwowmotz6qv","7qwpQCWMxaom7Ml1+5UiRRHl6NIk4t6jhntLxSt0rd4F3YJ9l6NsHtGVOrvfoCHLe/BkEDwUioM2AK8T5Qh0Hg==",-1644395437605792117,-5030744779661602953,-160951539932421580,1620082727178076123>()
                     )
                     .b(
                        (String)com.yiyiaddon.m.b.a<"s1b3jbk37snd1y","hE7f7aDkT1NmjcDL461GCdzGM5XH5mIwdA5sy2cBRuE=",7999623904928245112,-1320150878736543965,-3479503093347603642,62672731232362782>(),
                        (String)com.yiyiaddon.m.b.a<"s2005qkjnl699r","OZFB7+0QayqcLxPMT50TTypJ1krKfI/DRY2OniCV6ErD9fefGkpXDQ==",3873999390903079603,-8022353838831837655,-6822000783806383305,8313393365037003423>()
                     )
                     .b(
                        (String)com.yiyiaddon.m.b.a<"s3kle7656nz9xe","ethBrfJ20GCOh8jeX193dODkXKco++LswDwUCoT3N2E=",2761385569709260787,5934831969200915616,-1079787739536779125,5102913609510164178>(),
                        (String)com.yiyiaddon.m.b.a<"s3vrqdvmge0jtr","vfJYk0E/pb03Kndcl6cnSUrPhWJuxQa6c6fzi/7oJn0jTJfc1P8kFOZ2",6319403846584668647,-2926639097882332837,1261262707105346366,2887951908380808791>()
                     )
                     .a(
                        com.yiyiaddon.d.d.a.FAILURE,
                        (String)com.yiyiaddon.m.b.a<"s1rdnei7tuasxi","pHpkWBM/FE4Y1/Cr2s6284gYc9iJ9NVEVOPnEHU6ievmig==",-7070387080086612770,525777181551800983,8409634178981689177,-7358869306438894224>()
                     )
                     .g();
                  return;
               default:
                  throw null;
            }
         } else {
            String var4 = var3;
            byte var5 = -1;
            switch (var4.hashCode()) {
               case 686385:
                  if (var4.equals(
                     (String)com.yiyiaddon.m.b.a<"s1w3ksdumbi6tm","ZUxz4gZxQltmAWLOb1bUMmPnDXpE0yIHuG7C12m3ex8=",-5966378536111085168,5411513156889598171,-2543854331708638675,8640805943618465168>()
                  )) {
                     label143:
                     switch ((int)com.yiyiaddon.m.b.a<"sngv4h2ikpjno","UiNI7rnljsDtvQyvuLrKwKEg31V4gXqPvQVIe3C6c3A=",-1156555615845187575,-2538902899492181315,-3205086419874203892,-7089788842816819969>()) {
                        case 1549226118:
                           var5 = 1;
                           switch ((int)com.yiyiaddon.m.b.a<"sr8huv4vhg47g","GXOPy2QNJSQAZVw3AQgwSuAx3zm52i5kXJLhotrDzuc=",6812134509851172727,-2936295901110109843,-7590581702007319312,-4538333899376124282>()) {
                              case 905250654:
                                 break label143;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }
                  break;
               case 690244:
                  if (var4.equals(
                     (String)com.yiyiaddon.m.b.a<"s2iivnxo5ul6cx","x2bqAJN6eNtDQaSrxTaYh8U8oTp9SRM1jTGa3dkawMs=",7253582403378916462,-449066580557230319,225045130113654137,-4810329004032759732>()
                  )) {
                     label129:
                     switch ((int)com.yiyiaddon.m.b.a<"s2gzyocju3sk37","gvbZ6QrgkFTC5rZEy+vLnR7Y+lMPgNkitoYITFJapQI=",2176186969828614034,-557406882762586206,-9209733365186857402,-5163754725502627843>()) {
                        case -2040057835:
                           var5 = 2;
                           switch ((int)com.yiyiaddon.m.b.a<"s3f7k0r0klb4wt","rLHKIaZsMmqKJSwRRjXm9y83dzNHqKukc/l4lTUIPL0=",8924289987406280595,-4528191542440721384,2006543353073668585,-8700241637228382440>()) {
                              case -477834299:
                                 break label129;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }
                  break;
               case 693362:
                  if (var4.equals(
                     (String)com.yiyiaddon.m.b.a<"s3kle7656nz9xe","ethBrfJ20GCOh8jeX193dODkXKco++LswDwUCoT3N2E=",2761385569709260787,5934831969200915616,-1079787739536779125,5102913609510164178>()
                  )) {
                     label123:
                     switch ((int)com.yiyiaddon.m.b.a<"s25tz9jgwt6owp","AJnZ6SeexJAiL4inePoeG5yIdYLwad6ADUyvwdmsCa4=",5773013565383061850,-923244777518753892,-1833492935084655792,-1753789158296994052>()) {
                        case -836742561:
                           var5 = 0;
                           switch ((int)com.yiyiaddon.m.b.a<"s1h32swthe0rxc","K/f33BG1/VXGGuPP5pQPasWY+Qm2qX/oYz5J1ID0uMI=",2719474479820441873,-301689634332265087,3175210219608379844,5724358296804124971>()) {
                              case 2115951392:
                                 break label123;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }
                  break;
               case 903862:
                  if (var4.equals(
                     (String)com.yiyiaddon.m.b.a<"s38cp7k5twqyi1","IDkjgeH0ICeDP1HWE3d/K/6nrPONyCXeuJqOkZY7kD0=",-1634932372960291524,7149871859688571493,7224266377065995166,-6079965603936431041>()
                  )) {
                     label126:
                     switch ((int)com.yiyiaddon.m.b.a<"s3ry7nbgh9k6l0","gHx+qxT6Yv3Uo92IWBnZZwN5ay7aaADShS04ybsHHl4=",4378365340172634544,-4969396894924202817,-7793543626706250975,2556237840076637876>()) {
                        case 1188578763:
                           var5 = 4;
                           switch ((int)com.yiyiaddon.m.b.a<"s1eyp39wu7ykqa","/0c7NY1aMewQ+rj9PHT9itAYRfx4tTTUonGaCHqcioQ=",5309741186383234927,-3419277123240332351,-8265366816760131515,-6162254408882343743>()) {
                              case -891307951:
                                 break label126;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }
                  break;
               case 904469:
                  if (var4.equals(
                     (String)com.yiyiaddon.m.b.a<"s1b3jbk37snd1y","hE7f7aDkT1NmjcDL461GCdzGM5XH5mIwdA5sy2cBRuE=",7999623904928245112,-1320150878736543965,-3479503093347603642,62672731232362782>()
                  )) {
                     label139:
                     switch ((int)com.yiyiaddon.m.b.a<"swfwyn7d2qteq","hdCdN2j+n1Nki+LS/bbkP2KWhj8eF4ll6FcJmVQ64EM=",-6335011418844933870,-3443722607786169103,2113990395355276464,8196400354296417453>()) {
                        case -470804353:
                           var5 = 3;
                           switch ((int)com.yiyiaddon.m.b.a<"svoldtbs1x36o","ZsTzGYEmoagiKGgJhhiOzFhqzovEqDctLude/t8m5f8=",4133573434077286443,3967903429811073466,-5795837265428469847,-8299484661843283000>()) {
                              case 1231598415:
                                 break label139;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }
                  break;
               case 1168384:
                  if (var4.equals(
                     (String)com.yiyiaddon.m.b.a<"s2792fo8lpppeb","/XaX+im6+xxgogeZjD/o7oHNo826UlNMktLBFOewLlA=",7321331569428824781,3747762963793150846,3227749346660513577,8713705364711105184>()
                  )) {
                     label132:
                     switch ((int)com.yiyiaddon.m.b.a<"s3m90c436yperz","QEljDXZ6c5ZDKWwxC7z1v7nHCokvoSgnBmFAXH9qH5g=",-2230523812679637319,7040276540361077099,3577507983217919738,3691715777670335999>()) {
                        case -611131213:
                           var5 = 5;
                           switch ((int)com.yiyiaddon.m.b.a<"s3niwyka5chbv1","wR0e426aO54Gza3YMF8g3Do/hwvAW5W1K0lAmxzGpVo=",-7839154533196154451,3050570950738882772,4195832295834898021,2998792118398604747>()) {
                              case -1770695118:
                                 break label132;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }
            }

            switch (var5) {
               case 0:
                  if (!var2.cP()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s25uzjs08m472z","GZH9GP1YyV+nhNUPpDoDZVGlGtmg/bbx/wqqWr3dSgQ=",-2302814784897163887,6222682462313541659,8899693552720972456,-3392204809849337279>()) {
                        case 2022974789:
                           this.c(
                              (String)com.yiyiaddon.m.b.a<"s5uq58zm37x2p","p0yy0AXf8zTQmUVED6NxIYINVgYJ0RBiT0FwU1QRmoDk5Xz9IxhH3w==",-6022293585669464048,-3530596704923793988,-6897541689940287617,-2723984984402535090>(),
                              (String)com.yiyiaddon.m.b.a<"s2kh9j15nohb66","WSpbX1MhVxaZTRzq59gc4XXs5HuqvFFTiPQyFVOliNEi+2lGBcmGXn1mW06DzA==",-6477052047898800651,-4854561296496742104,-4660616794242317878,-1705701051800112952>()
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"sj7nfgq7wb5mc","KgpMEv7m66FQclpWJot8yzQvcmxiWTz7n/RGqYtEeug=",5907513056548282820,-5274939577848464635,2728995462653969169,-7337150603943367726>()) {
                              case 983034929:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }
                  break;
               case 1:
                  this.a(var2);
                  switch ((int)com.yiyiaddon.m.b.a<"s344b0mkwxdsff","ElbmqtED1Xq3+3vREX3sWiq+lrxrvRKl8AHTGxpBfGw=",-2429835508291229049,-582104055553252155,1060666788929085572,-452031906222532795>()) {
                     case 1203163048:
                        return;
                     default:
                        throw null;
                  }
               case 2:
                  this.a(var2, var1.a(2));
                  switch ((int)com.yiyiaddon.m.b.a<"s2h5jf3jkog9w1","Y8pI4xHKExF/h6JA9YDoMIOgpjeuIhtMt4pu0L3U+4I=",682640335660379921,-8586251524399747220,-159393163832537752,-8209838872877077805>()) {
                     case 2079115086:
                        return;
                     default:
                        throw null;
                  }
               case 3:
                  if (var2.bM() == 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2wqgz1n15oce","HOY2JjjFckC+aAg1Yfv8xUO+uh8rml4vR8j6hO2zuRM=",1155596966481785538,3674452807102637928,9058505869195273291,7523143543770675597>()) {
                        case -961029798:
                           this.c(
                              (String)com.yiyiaddon.m.b.a<"s2cqkg0chabdv9","DznTWyK2v4mIFcJ6pF6ScnR1TWDHdLlVG7pJO6b3YGadSA4jafM/Ug==",-9099288507893907006,-2326372396194005850,7269400590204972886,-9056061225631550091>(),
                              (String)com.yiyiaddon.m.b.a<"s86q5dmdegobd","YQWrDmcYmnIsfKxJlyIsQfuRt5+XxvqfCaMEFctC/ASt2z9r0vS5liIzkHKxMFArSGc=",-6398521423197921272,-3498211657387062625,6239565090163484043,6897736407885809322>()
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"s1wnydh2l8l64h","lY2w9dgf3wbcbky0jLqYljcvo+iTwfXbI2mFne3qNa4=",-6959495553109485944,74828574119504568,4503147854249399507,5367139536880413104>()) {
                              case 723672627:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }
                  break;
               case 4:
                  String var8 = var2.cZ();
                  if (var8 != null) {
                     label100:
                     switch ((int)com.yiyiaddon.m.b.a<"s1xqus0r6lvtsa","zlk5dYXmi2NdAv5i4ie+1usKa3K5ZeLD9FcjEy7f8lY=",1620474692052428349,-4226723292260650957,436707118037469191,-2821712818866842450>()) {
                        case 728219984:
                           this.c(
                              (String)com.yiyiaddon.m.b.a<"s1pbigzf4opkhr","egu3cMKg/huI6FsEa9Bh8FLvdXWIYDaoBPm7Zd7k1TV18a5W",-2616425179220908252,3439436910542586451,-7102907178862761742,-2515908507255617588>(),
                              var8
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"s2l7236ktyj1fd","XbgevO6UzqN8Ilqaezo3P2E760U+/e++USTpG20WzAo=",-8827639098124268295,896654808653411406,2945462636890077390,6451083467236517243>()) {
                              case 633252387:
                                 break label100;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s17hj0j26zgs2t","a96F1HLbIxmHXxajwkxxIMcfdwq+Mb1A7urWfAXwpww=",29726886043103479,6175218607412617024,4761148943370069178,-5075142296342438904>()) {
                     case 576329377:
                        return;
                     default:
                        throw null;
                  }
               case 5:
                  String var7 = var2.da();
                  if (var7 != null) {
                     label93:
                     switch ((int)com.yiyiaddon.m.b.a<"sv46smlb9fvl9","qS/cfyoTGf15NIp38/rW3fOVtdKIHPLLoF7BMVCkmEM=",-6580575606535672610,-8359117188351139906,8715693123169484130,3109559851941970206>()) {
                        case 1746916771:
                           this.c(
                              (String)com.yiyiaddon.m.b.a<"s1pbigzf4opkhr","egu3cMKg/huI6FsEa9Bh8FLvdXWIYDaoBPm7Zd7k1TV18a5W",-2616425179220908252,3439436910542586451,-7102907178862761742,-2515908507255617588>(),
                              var7
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"s34o8fn776nlu4","4xLzNL2kcFDLWP/QylbJeW18toSvJeGZMUiQLlvUxSg=",-4771127853809045960,5989621677264519018,-3301837610246724079,-1509176147002489620>()) {
                              case -181676708:
                                 break label93;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s2r7fl8p8fa3bu","LHvMZvkW5fYX+iHjNw1jSf3DARekYQ7ZMg30wnOFsqA=",-3730380932916512929,7975460476192439103,6182016400231554189,-6237781774253037578>()) {
                     case -61974795:
                        return;
                     default:
                        throw null;
                  }
               default:
                  String var6 = var2.av(var3);
                  if (var6 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2swf1prmwid5h","Rcxy40qdXcsiefO+bynLItHJdCSqqFK+0q9FhEIb+0E=",6774894169839158138,7315029101906625567,6238228622457689677,-3643825416165708039>()) {
                        case 258742958:
                           this.c(
                              (String)com.yiyiaddon.m.b.a<"s1pbigzf4opkhr","egu3cMKg/huI6FsEa9Bh8FLvdXWIYDaoBPm7Zd7k1TV18a5W",-2616425179220908252,3439436910542586451,-7102907178862761742,-2515908507255617588>(),
                              var6
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"s2uzpcbfrcekkb","/rNnUTJkqbWsQTloP2sW/rhcKOYZ7+IvluoAaMnS4Lg=",1434713155291221560,-2654097937018377788,-6752169520428531756,8151667406066697632>()) {
                              case 157805538:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }
            }
         }
      }
   }

   private void a(com.yiyiaddon.e.n.b var1) {
      List var2 = var1.aC();
      if (var2.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"shmpqcytd7cx6","HyyJ+LUTxSG47R7w7sxyUYEBOJTNqGRf9M5QCKenG1Y=",3566065009006396910,-7784549614959029954,408502760284814544,-4905134486100554334>()) {
            case -811537221:
               this.c(
                  (String)com.yiyiaddon.m.b.a<"s9sdksuo4dfn3","O0JzoCmB9cB+eF45SjHWejrpkMZSh+7FegED9uTJvm9VzhO+32PN5A==",8232662967927996004,5167815247643169095,-2762270938988999276,1738201434709130277>(),
                  (String)com.yiyiaddon.m.b.a<"s1met5563npdeo","AKiJ/ILNHy6sxHXqYanlTxhRwrjtw7L7AhgoYdf17pTvQn8Iaw3tyQ1tDttFVaeuPq3DmntmUDzlRuZQooUndXtL7umeUtmIOFKPmiQiicpHZ5gRs9KI0IoDaHa71dom+GnqAaWTia+wpHG9uML3jQ==",-7368082749603700737,4886048905333214339,-7310532023089548888,7482021396885228739>()
               );
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.d.d var3 = com.yiyiaddon.d.d.a(
            (String)com.yiyiaddon.m.b.a<"sqz7fubk633af","6+QZHPPK098aa0vAqUiyDSxxduhSjjne9OEY83Hv3vUO2fCcLn8=",-5840169825291205960,-6192287268964251247,5183046899392997009,-7287729381131471577>(),
            var2.size() + ""
         );
         Iterator var4 = var2.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s1ey5x157db9we","+VseeIbOJsTiiYVa0ozTV0lgEgjz59cq0UwFr1dwe60=",3908099041519746939,3418919697198137036,3732624674913326740,816680657393612914>()) {
            case -423182094:
               while (var4.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s34rqll9ceyg8n","SvtxWTtDH+N2Wn8Hyb/P//ZPV53ZPyjelafjJPYVCTQ=",-8621028572602509457,8244804638497784099,7401121336756207814,2969315127296278707>()) {
                     case 318872746:
                        com.yiyiaddon.e.n.k.a.b var5 = (com.yiyiaddon.e.n.k.a.b)var4.next();
                        var3.b(var5.cr() + "", var5.ef() + var5.ee() + var5.cq() + var1.a(var5));
                        switch ((int)com.yiyiaddon.m.b.a<"s1n8hn1rejqjp8","FzwDwbaqTXt3tdBMDWYgjbJug6rAM+r3+iBdHLDUUUY=",-8077591770533708726,6016498693509923302,8255288486107683852,-122719926066256306>()) {
                           case 1700685423:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               var3.a(
                     com.yiyiaddon.d.d.a.SUCCESS,
                     (String)com.yiyiaddon.m.b.a<"s16n8h8bqsnfwv","kWE5QPtnXtXIF/7ieA1724Y8KYo4hlLBP220VpKQYm9Qaw==",8189612216772637104,2771360144404324183,-5556087531158628710,-1002018886524178272>()
                  )
                  .g();
               return;
            default:
               throw null;
         }
      }
   }

   private void a(com.yiyiaddon.e.n.b var1, String var2) {
      int var3 = -1;

      try {
         if (var2 != null) {
            var3 = Integer.parseInt(var2.trim());
         }
      } catch (Exception var5) {
         var3 = -1;
      }

      if (var3 <= 0) {
         this.c(
            (String)com.yiyiaddon.m.b.a<"s3e9y3r7ij6htc","GWecPMesBmd+vwLLWpBcsaD3wUb8Ds4mX7OUCJ/uWVwRfHthCfohSw==",-9100690323782293129,6755098340701124929,-6040337289030462280,2044458289728583425>(),
            (String)com.yiyiaddon.m.b.a<"s2bwlmdu4ce5du","nvDw2OS8KcUUm2h96VkA7aSDSfcfPI/s+YE9Rj4GbaDBIyGKUVgATGpTSPBA4NwMMDjYTzOwUvVg+oqocy4kOojPILw/DeJOUj6/vvSSUa/Zws2qBBLGigN/zC3pd2LpS1uEPBvpRgQcDMbClxFnQg==",-2273351198542089626,140497737191965288,8215150319049668429,655145084355041925>()
         );
      } else {
         if (!var1.m(var3)) {
            this.c(
               (String)com.yiyiaddon.m.b.a<"s3e9y3r7ij6htc","GWecPMesBmd+vwLLWpBcsaD3wUb8Ds4mX7OUCJ/uWVwRfHthCfohSw==",-9100690323782293129,6755098340701124929,-6040337289030462280,2044458289728583425>(),
               var3 + ""
            );
         }
      }
   }

   private boolean a(com.yiyiaddon.e.n.b var1) {
      return this.a(
         var1,
         (String)com.yiyiaddon.m.b.a<"s2i9v7izwk3o36","lL6UfGIP8xmi5K42Tq33Pc45ZdN7ChYvlwQkFzVowDM19FWAJNq2MA==",6901108052720643635,849089806844826101,6098110154353890512,1663991319377718623>(),
         (String)com.yiyiaddon.m.b.a<"s3byij5iel2062","BZ69buBHXmp21xNo9G47UYlyn59Ve4J0V8SNUCRxWasdFQ==",9151459354653124679,6843975542162487745,-4157971095900692998,2452592799202457557>()
      );
   }

   private boolean a(com.yiyiaddon.e.n.b var1, String var2, String var3) {
      return var1.a(
         var2,
         var3,
         (String)com.yiyiaddon.m.b.a<"s2lqhcvmmj4ra1","R434FfWfvN79exIdkO1hxVzqwHj1ptfCIG9OEp7Y8gxVdQ==",1263612125265266451,-4490761283743479492,-5879108630069694851,-810859989514034680>()
      );
   }

   private void i(String var1, String var2) {
      com.yiyiaddon.e.n.b var3 = a();
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s34d3zwgk2ysuc","uvMzIxA7/2l00M7LLZYQbEyVB8bb6vi8uZBazs6YMmQ=",7040545825975348179,-3581273393441774921,2942764134764621755,-512216503831098717>()) {
            case -2090410802:
               var3.a(
                  var1,
                  var2,
                  (String)com.yiyiaddon.m.b.a<"s2lqhcvmmj4ra1","R434FfWfvN79exIdkO1hxVzqwHj1ptfCIG9OEp7Y8gxVdQ==",1263612125265266451,-4490761283743479492,-5879108630069694851,-810859989514034680>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s1i59rvdyg1jz3","XAcg5gBATZf/NlPCe/XzLF4clNeN7kK7NZXx5XJxnas=",138151883013503129,1324308000007818857,3497176588022598839,8470726389838329909>()) {
                  case -1469064691:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private void t(boolean var1) {
      com.yiyiaddon.e.n.b var2 = a();
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1m8ui80yx1hfr","iJGSiI9by35s+F2vMLDtK20Hh1ACPBAyEkVBKL8OSTM=",-1738025897660124141,-8939777681389145381,155924120205416284,2165752104220180924>()) {
            case 1575333255:
               return;
            default:
               throw null;
         }
      } else {
         l.info(
            (String)com.yiyiaddon.m.b.a<"s3la4ngy1mu9bh","97oI2V2dcm7E+p6SkrRnJ+oQyghGFq2BSbD9wfhO54yc8Gxhzz/S2/Wp8e7/Ai1j08dxeobfM1VBYezxmiAEIrGq05f8vQKWrfX0rplF",1834049835746940566,-1023448397221232384,6824913969585944672,-2887714200703259671>(),
            var1
         );
         if (!this.a(var2)) {
            switch ((int)com.yiyiaddon.m.b.a<"sivebwfnu2yw","14iWUr1v4LfHfGXTw/wTtS3NjOINYbvASOw8iqdNZUE=",4274715494707774446,-877683474645869881,6738243377303046581,-4803197940821746503>()) {
               case -355092022:
                  return;
               default:
                  throw null;
            }
         } else {
            com.yiyiaddon.e.n.b.a.a var3 = this.a();
            if (var3 == null) {
               switch ((int)com.yiyiaddon.m.b.a<"s35nws5cqwtuth","xwqmdAWB8kUD29MFgAlNg6wH42LrnYwlpsX1jv7w54Y=",-4436866461861500912,172749203200240804,-4557794531407119608,2919474181539018180>()) {
                  case 1774203362:
                     Logger var10000 = l;
                     String var10001 = (String)com.yiyiaddon.m.b.a<"s1xe3b33x3fghb","QSciha5LRz8humynEFogtPrSxZXMS9ijM4r+m+2zjbLZXd5vtzH4uGvi/yHNmdc5xe7B26nqPlkuNPU7I9AYVwRUu9i7boDPjLCRAftAPMLek0hE1IV7YruuOX2OS22CIU3t4O5IbEzjo+sgzqY=",1331628215254078574,3853159517991029743,-476798379183226284,-4528179315453725014>();
                     Constable var10002;
                     if (X.hitResult == null) {
                        label45:
                        switch ((int)com.yiyiaddon.m.b.a<"see9leeqbk10h","DBtjoQeh9YxlWia6GuKA2tBZCG2Mnw8nV9o/QHzY2Qk=",-1597205436330921082,8442806012792665341,1861603090321943888,1167213369778144758>()) {
                           case -1817882978:
                              var10002 = (String)com.yiyiaddon.m.b.a<"s372tb510zsysi","Uvy0nOl06l6Hcb2j2vTt3OdlM85IDyvidJit+iSU364gh9ya",-968298625591849645,4267172070341401097,3579561118761479764,8769064920606768627>();
                              switch ((int)com.yiyiaddon.m.b.a<"s343knko6ualyc","CjserhnjgbpzbVjTz82OKto3ALlM+yVIN5cbh33Xr0M=",3315506824557148401,4645954063923413400,2677599067709198701,-2966106468604728312>()) {
                                 case 359292520:
                                    break label45;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10002 = X.hitResult.getType();
                        switch ((int)com.yiyiaddon.m.b.a<"s1dnm2ynsmg94l","sEYGT6HrAHejFt9rFM0nv0JnSj3rN3z2LgkBwWA7sO4=",1862519556918669137,-6436379687520341053,2263421836683862010,-3351840354730876103>()) {
                           case 1471493396:
                              break;
                           default:
                              throw null;
                        }
                     }

                     var10000.info(var10001, var10002);
                     return;
                  default:
                     throw null;
               }
            } else {
               com.yiyiaddon.e.n.j.c.e var4 = this.a(var3);
               l.info(
                  (String)com.yiyiaddon.m.b.a<"s19tppuikv1byl","fT4OcL+6P/5T7c+3m791PucjmJ2Vpq5V+v+cfs4A2QD1oEnP0iyfsC2qJk+4enTDYVsGQ4iA33F7ryeB/4ZAhqAQs+MtstYJBc34ld7B3O3MxayNNoex/ENuj6g3sZv1P9Xw+fYw3V+93htneFLJFDNCHfybsnXM6AsFBs65jYz+hw==",144336968450382753,-7453150715654388544,-4533265775785331084,1419702132039284646>(),
                  var3.a().toShortString(),
                  var3.dc(),
                  var4.dk(),
                  var4.dl(),
                  var4.b().m()
               );
               if (var4.b() == com.yiyiaddon.e.n.j.c.f.DEAD) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2y45dgu4c007l","wxUI2o0YoUygCvELiyFUZSL1dNKTkJm7L6ycZNatijc=",5075623361570940913,2854787214533690484,3703531806943859925,-6894990626669391500>()) {
                     case -543597664:
                        com.yiyiaddon.d.d.a(
                              (String)com.yiyiaddon.m.b.a<"sqz7fubk633af","6+QZHPPK098aa0vAqUiyDSxxduhSjjne9OEY83Hv3vUO2fCcLn8=",-5840169825291205960,-6192287268964251247,5183046899392997009,-7287729381131471577>(),
                              (String)com.yiyiaddon.m.b.a<"s2i9v7izwk3o36","lL6UfGIP8xmi5K42Tq33Pc45ZdN7ChYvlwQkFzVowDM19FWAJNq2MA==",6901108052720643635,849089806844826101,6098110154353890512,1663991319377718623>()
                           )
                           .a()
                           .b(
                              (String)com.yiyiaddon.m.b.a<"s1hygc34djx69g","cL2G7ygWwhRXXcKIUbVGCVOHhU/E7x3ubRbnEDBE+xA7SFNUqDRXbg==",-4604751208970371592,8549213458272884418,4790879535620630508,-8456543911174909792>(),
                              (String)com.yiyiaddon.m.b.a<"sia20n2rcn3hp","blPdjf1MLEv1iJqEPaZ6dbBhNdA/DPMdVLJyViFBgQse2w==",7385234810645189544,657394685000690251,987043885426250497,-1402856776558574126>()
                           )
                           .b(
                              (String)com.yiyiaddon.m.b.a<"s3kl9hjq67gi6j","wVgk59k2czod1b7BM/infOcNgDu8XpI0yn9NMB+bdUU=",5464812756637643807,-4538333885441950261,-8553727997525455549,-7061260727369391346>(),
                              (String)com.yiyiaddon.m.b.a<"s1bc3xoeub6gt3","3mGxihrvsOZ9XZlsonoozDoej8dtvOskbdeHv5Ch16xUQy6fUUXxdlVflFkhTiI9VGqKxELndHgNK40D",-2642166658319415696,6373655037867402996,6525689008366202237,8210473380169798090>()
                           )
                           .a(
                              com.yiyiaddon.d.d.a.FAILURE,
                              (String)com.yiyiaddon.m.b.a<"s3byij5iel2062","BZ69buBHXmp21xNo9G47UYlyn59Ve4J0V8SNUCRxWasdFQ==",9151459354653124679,6843975542162487745,-4157971095900692998,2452592799202457557>()
                           )
                           .g();
                        return;
                     default:
                        throw null;
                  }
               } else if (!var4.do()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2veijjl5dtttd","sgNL3R35pyQdpxm9Qio5LXGGd0qTu9IODl0bum1QVMc=",-6343649626327409667,-4765876293142358835,4951822018169717499,-1035697003389255470>()) {
                     case 80664823:
                        this.c(
                           (String)com.yiyiaddon.m.b.a<"s2i9v7izwk3o36","lL6UfGIP8xmi5K42Tq33Pc45ZdN7ChYvlwQkFzVowDM19FWAJNq2MA==",6901108052720643635,849089806844826101,6098110154353890512,1663991319377718623>(),
                           a(var3) + ""
                        );
                        return;
                     default:
                        throw null;
                  }
               } else if (!var4.dq()) {
                  switch ((int)com.yiyiaddon.m.b.a<"snjivfocjx5in","BW3fRkCf66/0bw6wUXdn15t43WPORSgG1qyYWA7KDbI=",5067999212650030852,-9154669973523303159,5853427107891214671,8216388466304796630>()) {
                     case 95983629:
                        this.c(
                           (String)com.yiyiaddon.m.b.a<"s2i9v7izwk3o36","lL6UfGIP8xmi5K42Tq33Pc45ZdN7ChYvlwQkFzVowDM19FWAJNq2MA==",6901108052720643635,849089806844826101,6098110154353890512,1663991319377718623>(),
                           (String)com.yiyiaddon.m.b.a<"s1ohhre8lby135","QJozJBT0H0fI7jKNTft5q9LHFIIiPC5T2vpQz7Lp2Ivc8lRh/c7Ittg7QRooGR5ogMBMhvdGrACaqfFiFYlMIrAs/kLmdyrIxPU4XEGJWVApIdO6cwXwBir128U=",9067848348321651916,-488427383429787179,-285804876100851873,2145272248591877561>()
                        );
                        return;
                     default:
                        throw null;
                  }
               } else {
                  b.b var5 = var2.a(var4.dk(), var4.dl(), var1, var4.dv());
                  if (var5.a() != b.c.SAVED) {
                     switch ((int)com.yiyiaddon.m.b.a<"sh1621bor1127","GPOwcbPqoGieBIF4QXUN+9h8tVraPH0uCVooIvvXYJc=",-6977833454968214024,-5665848250816816998,429101255692723092,-1325727530131638128>()) {
                        case 208001075:
                           l.info(
                              (String)com.yiyiaddon.m.b.a<"s3i162sp45jvrx","seBcJN3M7aYx8otUwy1FCRjG+ZMCcdDYslbUX1kMGk1Paa8gI9PRcK7j3emAOoFmkwJkcyX/YCmQELTZUZ6GUhLiqig=",-5822751701760104795,5315173175754097117,-1548690493432188548,-7072492487897470693>(),
                              var5.a()
                           );
                           this.a(var2, var5, var4);
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     l.info(
                        (String)com.yiyiaddon.m.b.a<"s10ff5jgc1lm93","foaQUU8qkf/eBEwkkDHhIOW02WAK3afQw4if1yaZvxFgbF/kNHrTzKeGrT6davdo53gMz82KE9bJpp2MURN5MzPD4cX27doLV+F7ifF5",-2986342577278220234,-4079452031922803728,599195928542071816,583357552217367781>(),
                        var5.dk(),
                        var5.dl()
                     );
                     this.a(var2, var5, this.a(var3), var1);
                  }
               }
            }
         }
      }
   }

   private void a(String var1, String var2, boolean var3) {
      com.yiyiaddon.e.n.b var4 = a();
      if (var4 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sng3b82pmg4jd","W4m/kN5NmiBBaAJloBT9rvonTN/V/pS7eBqG7YnDv3Q=",4741976500148322588,-1168762493310887387,2342012423901941658,-1268855263431753194>()) {
            case 140338739:
               return;
            default:
               throw null;
         }
      } else if (!this.a(var4)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1dx0um5ja4hq4","VlMvDWwcV8WvGnmexbiQ2Cta533uB5WWG3Wik6hS160=",3747117511307723399,4954660559654195656,-2517169844220307619,-6476679761952811170>()) {
            case -1165352906:
               return;
            default:
               throw null;
         }
      } else {
         String var5 = var4.ax(var1);
         if (var5 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s30nh2lqed2m3d","Ql7XyvmWjCfEnHz6iue5JqRUsQa5FEDArULvTSEIJaQ=",-5384988912394493901,6726751852187954656,-3549657975468054450,5701803398656445705>()) {
               case -1449485644:
                  this.c(
                     (String)com.yiyiaddon.m.b.a<"s2i9v7izwk3o36","lL6UfGIP8xmi5K42Tq33Pc45ZdN7ChYvlwQkFzVowDM19FWAJNq2MA==",6901108052720643635,849089806844826101,6098110154353890512,1663991319377718623>(),
                     var1 + ""
                  );
                  return;
               default:
                  throw null;
            }
         } else {
            b.b var6 = var4.a(var5, var2, var3);
            if (var6.a() != b.c.SAVED) {
               switch ((int)com.yiyiaddon.m.b.a<"s25xsljcp6ffkq","FsUsOEl5Blw3301A5qXY1fU3M99RD6b55SFpQY8un5M=",4193839838939504854,2296045197685922744,8829404854122609915,8386576014568167708>()) {
                  case -1438453715:
                     this.a(var4, var6, null);
                     return;
                  default:
                     throw null;
               }
            } else {
               this.a(var4, var6, null, var3);
            }
         }
      }
   }

   private com.yiyiaddon.e.n.b.a.a a() {
      HitResult var1 = X.hitResult;
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ruv9rn3117wi","n+xZ2jEGFfw9WiDbghcAej7UkbGtsJLXCs3lFMZfdCw=",2719124478480835315,-7856731449817335429,-4138367134055674940,-7534556833528573418>()) {
            case 2122510100:
               this.c(
                  (String)com.yiyiaddon.m.b.a<"s2i9v7izwk3o36","lL6UfGIP8xmi5K42Tq33Pc45ZdN7ChYvlwQkFzVowDM19FWAJNq2MA==",6901108052720643635,849089806844826101,6098110154353890512,1663991319377718623>(),
                  (String)com.yiyiaddon.m.b.a<"s2dsq0h19pddhr","ZKngrpJlh6If+cJSRYBOV9IE8a2k12bsvgFaSThPu0HbrhKwoVLrlVhubzFl6JuFnxQ=",-7725532820842152035,5406247331390017396,7266547999959564801,-3612714904894707826>()
               );
               return null;
            default:
               throw null;
         }
      } else if (var1.getType() == Type.MISS) {
         switch ((int)com.yiyiaddon.m.b.a<"sc4j6zj1s1zgh","hDBO/+G09fxNR+Tb8QqPjpcUR06Ed4QBTOn481CdWH8=",6309928807548039993,5122960286711803563,8444759115013312293,4186936141780812037>()) {
            case -1137148630:
               com.yiyiaddon.d.d.a(
                     (String)com.yiyiaddon.m.b.a<"sqz7fubk633af","6+QZHPPK098aa0vAqUiyDSxxduhSjjne9OEY83Hv3vUO2fCcLn8=",-5840169825291205960,-6192287268964251247,5183046899392997009,-7287729381131471577>(),
                     (String)com.yiyiaddon.m.b.a<"s2i9v7izwk3o36","lL6UfGIP8xmi5K42Tq33Pc45ZdN7ChYvlwQkFzVowDM19FWAJNq2MA==",6901108052720643635,849089806844826101,6098110154353890512,1663991319377718623>()
                  )
                  .b(
                     (String)com.yiyiaddon.m.b.a<"s3kl9hjq67gi6j","wVgk59k2czod1b7BM/infOcNgDu8XpI0yn9NMB+bdUU=",5464812756637643807,-4538333885441950261,-8553727997525455549,-7061260727369391346>(),
                     (String)com.yiyiaddon.m.b.a<"s8c39m1bygf9r","feQ9JcJP/oZfG/kqBy4HMkruNF1Xlf2biz/TLLZhbyhspnQmIBlNIBHlQ14=",4204837604049370910,4874010482262327979,-5433070196357426110,-3794631047857702320>()
                  )
                  .b(
                     (String)com.yiyiaddon.m.b.a<"s1sdztxxayhksu","DfFs+d6CPVm7mJBNq1tc2YehMIpUUpuejyVP6B82irE=",7983427803050733114,-2423066587621332174,-3881150969575503625,1151005548890877601>(),
                     (String)com.yiyiaddon.m.b.a<"siz7610e6i8w","IExILWbMEz2wDbduT2HnFfcRWdJPWfupSbH+qU7eyzhuSlYf65YMWuCp9MIaVBSIVrtKd1LdGAIoGg4D+T0psm8ymoc=",-3230811969852471406,2756101882949522362,-1609257222498338266,6241267601024422030>()
                  )
                  .a(
                     com.yiyiaddon.d.d.a.FAILURE,
                     (String)com.yiyiaddon.m.b.a<"s3byij5iel2062","BZ69buBHXmp21xNo9G47UYlyn59Ve4J0V8SNUCRxWasdFQ==",9151459354653124679,6843975542162487745,-4157971095900692998,2452592799202457557>()
                  )
                  .g();
               return null;
            default:
               throw null;
         }
      } else if (var1 instanceof BlockHitResult) {
         switch ((int)com.yiyiaddon.m.b.a<"sfg3swj4vysus","SpJ+P7rTLgypttJDYQy//Kk5Ha2D5yB47KzqQAV2hfw=",-404960268852998432,1205492794500226635,4357960049106494939,-4824368416701831089>()) {
            case 1250555745:
               BlockHitResult var6 = (BlockHitResult)var1;
               BlockPos var7 = var6.getBlockPos();
               BlockState var10000;
               if (X.level == null) {
                  label66:
                  switch ((int)com.yiyiaddon.m.b.a<"s2y1g9x3n3lgru","m8JO1sHUtijwX/ynS7d3ka+xTNDNa/2xt1SABi6YfHk=",6420519411745362141,424507767622669461,8664687890623833312,-7770657507744883342>()) {
                     case 98657783:
                        var10000 = null;
                        switch ((int)com.yiyiaddon.m.b.a<"s2awh1wcb48rff","+2UZewl2FjFLNApMJfobGI+pcufYhXjGx08ag8ERKnU=",-8544509042340797534,1975857713092191394,-5766704070351357546,3182911595040360384>()) {
                           case -1758275610:
                              break label66;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10000 = X.level.getBlockState(var6.getBlockPos());
                  switch ((int)com.yiyiaddon.m.b.a<"s2igvf4siu63za","lW0+ikwUK3L9nU1duYu6LjqxT73AvvVI8hVEZZaOgLY=",2575616997935121986,6494181389139700171,9166707794391385976,-6492606168861811167>()) {
                     case 1408764368:
                        break;
                     default:
                        throw null;
                  }
               }

               BlockState var8 = var10000;
               if (var8 != null) {
                  label61:
                  switch ((int)com.yiyiaddon.m.b.a<"s2gziz9uh7ndek","LDp6JiPuEeySKkQIMSvktYhZwDt5WZdfYiI5GdD41Vk=",-2730596699612644514,2054566265466852822,321012032031873150,2743230043992026140>()) {
                     case 1118658994:
                        if (!var8.isAir()) {
                           return new com.yiyiaddon.e.n.b.a.a(var7, d(var7), null);
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3897jlty5f1p3","A+Txnv+prFu1XXAYBxZv7osHwMH6QaMoa5qBVtUD/Tc=",-7792137099206339466,-4316554200132934288,7353509389828380865,1462970076893653762>()) {
                           case -1005355116:
                              break label61;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               this.c(
                  (String)com.yiyiaddon.m.b.a<"s2i9v7izwk3o36","lL6UfGIP8xmi5K42Tq33Pc45ZdN7ChYvlwQkFzVowDM19FWAJNq2MA==",6901108052720643635,849089806844826101,6098110154353890512,1663991319377718623>(),
                  (String)com.yiyiaddon.m.b.a<"s8c39m1bygf9r","feQ9JcJP/oZfG/kqBy4HMkruNF1Xlf2biz/TLLZhbyhspnQmIBlNIBHlQ14=",4204837604049370910,4874010482262327979,-5433070196357426110,-3794631047857702320>()
               );
               return null;
            default:
               throw null;
         }
      } else if (var1 instanceof EntityHitResult) {
         switch ((int)com.yiyiaddon.m.b.a<"s29v59cz65vsex","inSvCe4umCai3SPpZYqEeznJFxdU3eIDeFwp9KZUZtw=",7349173921891744038,-8696417293234174087,6781445869651929340,-4624277532359768337>()) {
            case 480133325:
               EntityHitResult var2 = (EntityHitResult)var1;
               Entity var3 = var2.getEntity();
               if (var3 != null) {
                  label88:
                  switch ((int)com.yiyiaddon.m.b.a<"s25pf4mb7f4xhn","1CWhaeoF4/ZTyyjGXVAqzHGk9qW4S64XCNiaWSext0I=",-106715255898407246,-8129526418439426130,-3732240762141877637,566611913283518967>()) {
                     case -1295154160:
                        if (!(var3 instanceof LivingEntity)) {
                           BlockPos var4 = var3.blockPosition();
                           String var5 = g.a(var3);
                           if (var5 == null) {
                              switch ((int)com.yiyiaddon.m.b.a<"scd0kjchsj7eu","oIDxgl2hK4e8r7Kux9v9hJGRF313uB2i+eUWgqHrd08=",2274699784707624177,2403614541376455852,-6676882081407161817,-6034889109865148376>()) {
                                 case -2122763286:
                                    var5 = d(var4);
                                    switch ((int)com.yiyiaddon.m.b.a<"s3rv7q4nxk5w7t","vVvQuLzsUQzDuLl5RwHwKo0cPlJxpDtPW6Cv4awqA6k=",7507531984300155430,8404529991481864565,-325970725091455479,-6914918184288103827>()) {
                                       case 1374840094:
                                          return new com.yiyiaddon.e.n.b.a.a(var4, var5, var3.getName().getString());
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           return new com.yiyiaddon.e.n.b.a.a(var4, var5, var3.getName().getString());
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1sawnpaharbq7","+hUoRQFU9DRf6ZBlzAVt6HI+n5FnTZ0relsSDrQrk40=",-5713854726867168265,4721815974038558211,-8473220822046676511,5367507775507173712>()) {
                           case 2133581127:
                              break label88;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               String var10001 = (String)com.yiyiaddon.m.b.a<"s2i9v7izwk3o36","lL6UfGIP8xmi5K42Tq33Pc45ZdN7ChYvlwQkFzVowDM19FWAJNq2MA==",6901108052720643635,849089806844826101,6098110154353890512,1663991319377718623>();
               String var10002;
               if (var3 == null) {
                  label79:
                  switch ((int)com.yiyiaddon.m.b.a<"s2hz4cmd1q7216","VotNoUA+Hax8qmi33U8b7MyV62T/PBg1Z+hdvor7dcQ=",-4293636854043561747,-4933978325862748489,6423794150510822137,-7784836048650552245>()) {
                     case -269771831:
                        var10002 = (String)com.yiyiaddon.m.b.a<"sgleyljfazh6j","mjYyNbDfi/4Eh/IJe5DMhxl98BybQatVV0aGCA==",3214615122877264650,-8968572304130150370,-6110085062431042952,-2250863065076701131>();
                        switch ((int)com.yiyiaddon.m.b.a<"s2pp5q1szkfjjt","4An4wP8HwiMSVxXuxwBtlq9uuFYR7Vg7ePxST+bnR5Y=",-7829545552805405919,1164756763503754021,1093814211506711701,-6698272039033358770>()) {
                           case 1581455895:
                              break label79;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10002 = var3.getName().getString() + "";
                  switch ((int)com.yiyiaddon.m.b.a<"sple0if3fducb","hG+dMk+H2a4OiOGYF0KOFq6ONnrBZxhTw0z3KhNL89k=",1871958872007718182,-7186474139277355430,3322217831155624315,-922087981369873137>()) {
                     case -669107485:
                        break;
                     default:
                        throw null;
                  }
               }

               this.c(var10001, var10002 + "");
               return null;
            default:
               throw null;
         }
      } else {
         this.c(
            (String)com.yiyiaddon.m.b.a<"s2i9v7izwk3o36","lL6UfGIP8xmi5K42Tq33Pc45ZdN7ChYvlwQkFzVowDM19FWAJNq2MA==",6901108052720643635,849089806844826101,6098110154353890512,1663991319377718623>(),
            (String)com.yiyiaddon.m.b.a<"s2dsq0h19pddhr","ZKngrpJlh6If+cJSRYBOV9IE8a2k12bsvgFaSThPu0HbrhKwoVLrlVhubzFl6JuFnxQ=",-7725532820842152035,5406247331390017396,7266547999959564801,-3612714904894707826>()
         );
         return null;
      }
   }

   private com.yiyiaddon.e.n.j.c.e a(com.yiyiaddon.e.n.b.a.a var1) {
      if (var1.dc() != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sgxvbm20iwvsd","cl3bJrD6Wcl1TdlVILu3FJtrOL7CLuVmiTbxQmoyjj8=",-1270666466378251996,5071837736188243714,-6366547654843165747,-6326650618038695500>()) {
            case -529830520:
               com.yiyiaddon.e.n.j.c.e var2 = com.yiyiaddon.e.n.j.c.a(var1.dc());
               if (var2.do()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s27fxmkh5k0h7u","aeAPM2eMrV66OXfv6UlvFNrzDEs2fV/aKOgKZEnz6lQ=",3186929609871965411,-508548750291615331,7273820764337383264,-5136452902352691030>()) {
                     case 1835175507:
                        if (var2.dq()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3ixqc80djquar","qboZAgH02kN4qwWNUvm/DG4YOTO03mNFgySkx254yn4=",-2900992724038445001,-7974460036638461790,2768401008274095263,-5836384985688313673>()) {
                              case -1759734298:
                                 return var2;
                              default:
                                 throw null;
                           }
                        }
                        break;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.e.n.j.c.e var4 = com.yiyiaddon.e.n.j.c.a(var1.a());
      if (var4.do()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1e9dch23eajz3","zkrwNwpTzjB+7qCVo6s3612SFzJvyGFGl5H6lBWqHik=",1110495470604920572,8149852270954949681,647248599005953803,3788632468030604675>()) {
            case -138831201:
               if (var4.dq()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3tyiod3ev314p","HJL3JohwoLbaDdTwz5f5FRWDZTRKkDKL0cQLw5OF5cU=",8747432360378156531,-2764085981998339899,-627685732228094699,8172417815025353738>()) {
                     case 1364599445:
                        return var4;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.e.n.j.c.e var3 = com.yiyiaddon.e.n.j.c.a(var1.a().above());
      if (var3.do()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3c55hn4zdsypn","Vr2PVZVfIXqy+1q3jKlWdop6Xk8Oghln1dlHrbQj6Mk=",7305407105478200031,-2624866682776678478,-6364717002449118590,1395780410004282805>()) {
            case 2004167647:
               if (var3.dq()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1nzk4mn8zpejn","kXthOiYu4JQNkUag6GxAuvVZh1NoPqjxWjnM0QO9zbk=",-3698790875047485667,-5167985825742688450,-2051382981300909353,-3196773478681116487>()) {
                     case -561845656:
                        return var3;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (!var4.do()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3tp6uss0bp3xm","VIm5TxbVTqYZimFQv4Lel1fEJXm6LcnDyoMV3LJBF7Q=",-8045217531166443469,5767285830451729,-4512212136077932173,-7867976189828313768>()) {
            case 1716130505:
               if (!var4.dq()) {
                  return var3;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2kuxod1rsl9uh","RQoBJLGO/5+d5hBquMqwV5c9Z04Z6rhu8UHgURhENw0=",6592694576822886472,-1583558781322323330,8921930936110811840,8186882944427026390>()) {
                     case 1713977987:
                        return var4;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return var4;
      }
   }

   private static String d(BlockPos var0) {
      g.r(var0);
      Iterator var1 = List.of(var0, var0.above(), var0.below()).iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1dx2k4f5d0pqk","bEnQLZ/RwM+vUK3z7HSnMCwXF5chhoBrcQoFiT0m/mE=",-7792427260991010741,-941372339314300342,-5988360581711701097,1502197488145076785>()) {
         case 1207331869:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1g62x6ay31qag","tGwtAtm14LWplgK7DAG7gtqybAqp1s4742LMrKG8GRk=",7146146652827400643,6887938571796087777,1501749992978274460,-8268333665975831178>()) {
                  case 387941569:
                     BlockPos var2 = (BlockPos)var1.next();
                     Iterator var3 = g.b(var2).iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s3jtrze4hxmt6q","XPubRIeCwZAo+IL3oT9s6G8qgCX8lO/b0gMf1cUnOHg=",-3652392701434740757,-9029114474098616351,8306644616378021374,6653741466616166929>()) {
                        case 1558562598:
                           while (var3.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1rawfnfnjh1u8","s++uCY01wgkXYWv4toCywKxBMOKkMthj5CquQZR4P+Y=",6632174580787146747,-1445321396580095697,-4254968366587702576,6709309069802966635>()) {
                                 case -588838748:
                                    String var4 = (String)var3.next();
                                    com.yiyiaddon.e.n.j.c.e var5 = com.yiyiaddon.e.n.j.c.a(var4);
                                    if (var5.do()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3s1e3xsr9j2ap","2TsYYUpN3qiawW09bPICqrV2Zwf/WE4qUNBLiRNHMfM=",1767161887026268715,1808804087795295579,-929636625903894845,-1653767529335533769>()) {
                                          case -1765506416:
                                             if (var5.dq()) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s27om8pye5cmmf","PXEPYR59bODoKWfFxOD9ILYSh35yJafYFSmRA7LXum0=",734347882825350850,-7397569720067811178,-721205972091844875,-4077075696375749526>()) {
                                                   case -1092808394:
                                                      return var4;
                                                   default:
                                                      throw null;
                                                }
                                             }
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s12p3k79vui2nz","GnT3cUZrV1d0nH5NY6c+fnf1LZpyiMgal6OA6ZE/iE8=",-4422022750747621132,-4466881126844051217,-1852864519756037900,-2004096375327984517>()) {
                                       case -1727622357:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s28xi05682pfnh","X7bHYYDtw0uE/IXRM91ktaILSTJR6emFF2XsUQQYvV8=",-9147006631177714903,7978574277105939739,4342247987579871174,8051975010915071029>()) {
                              case -1866894535:
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
            }

            return null;
         default:
            throw null;
      }
   }

   private static String a(com.yiyiaddon.e.n.b.a.a var0) {
      if (var0.dd() == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1b992wfkwky1q","25tnZHu7ykbrc7uL47nznMmotqtpCDdS35XWbrErxq0=",-1795662228446619023,3267731501165617907,9195525609219869886,1284541777853866992>()) {
            case -150117865:
               String var10000 = (String)com.yiyiaddon.m.b.a<"s2ht1w1vgpyr6g","ejfCTg0Qjty61gIoAY4e7Gn8590MK2LXuWKHc7z5N9kZXuo7",2044121965825628488,-6760023516962994702,6994125641857309632,1218433431740337161>();
               switch ((int)com.yiyiaddon.m.b.a<"s2xo3uivttr6d0","AFsR7MI8kLikiYWTM6rYx2LeoQ2+pA5VJVcUoQbU5sA=",-2058597422350552212,7178843969499383996,3268555154326053735,8510782725947503650>()) {
                  case 32852718:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var1 = var0.dd() + "";
         switch ((int)com.yiyiaddon.m.b.a<"s3dmdwr3jldh0a","7ERSxR6wuujEtdLcz5qb+wtbX1y7rW0r96pWrqw3hbE=",8938063486223899060,4021228511697466850,6810362450983524181,2030099727889121571>()) {
            case 236756170:
               return var1;
            default:
               throw null;
         }
      }
   }

   private void a(com.yiyiaddon.e.n.b var1, b.b var2, com.yiyiaddon.e.n.j.c.e var3) {
      switch (var2.a()) {
         case UNCHANGED:
            com.yiyiaddon.d.d.a(
                  (String)com.yiyiaddon.m.b.a<"sqz7fubk633af","6+QZHPPK098aa0vAqUiyDSxxduhSjjne9OEY83Hv3vUO2fCcLn8=",-5840169825291205960,-6192287268964251247,5183046899392997009,-7287729381131471577>(),
                  (String)com.yiyiaddon.m.b.a<"s3pl0hr1v843cq","G7ZbjSk5R9Wfvbr6k6rwNhbu1TEaqE6d4TqjabGgkBNWHKgrVUWepe/w",7284672529169723156,-5004849975342602464,-4984037930460611727,8636371929382173641>()
               )
               .d(
                  (String)com.yiyiaddon.m.b.a<"s2kzvbp52kgirp","dvNKp++75HBjFYt06jQ3zKCUEfAuQfVQqL5AMCCYfjs=",-901505182134927223,-8611408926587085932,-8029069632447620063,5505936193956503889>(),
                  var1.aw(var2.dk())
               )
               .c(
                  (String)com.yiyiaddon.m.b.a<"s253d52x94o1mv","RCpY4oBsgL0pjj5qPz3p7j/ON6xpON17JMeP8qP/TiddQnCmxuQhLZzC",-7563966088569937483,-7206198668365708895,-3394674626273371807,-6962824681205931077>(),
                  var2.dk()
               )
               .b(
                  (String)com.yiyiaddon.m.b.a<"s79vjjqbsa2l3","DUm6HD6g//uGuOtOz3pr8Jfj64ZiEVpuGVaHYorabkMWpzxI",-4061244801703098640,48876430446666804,-4497788370651448904,2153831142987964623>(),
                  var2.dm()
               )
               .b(
                  (String)com.yiyiaddon.m.b.a<"s1wrhpxbmqmi09","Gc8ueL6xNZEomeHW9AwVSL7TjZjbj7KKQo2CHRXCgWIgThM9",-6963345684935918232,495622930400560890,-3775760861808791942,6450607255845996773>(),
                  a(var2.a())
               )
               .a(
                  com.yiyiaddon.d.d.a.SUCCESS,
                  (String)com.yiyiaddon.m.b.a<"s2e15oods83doe","rY7MfCnrUDL5yciXChWMeTYwjRTJAvyHVNmeX/ijX2sfeXOV/KxC6mu+nlnNNoasr4tXbkhjgd6i9wwl10XIJ+G8psVuBA==",-6804282190442422165,-5802546980847378077,6480680028750618696,-9072954376278793108>()
               )
               .g();
            switch ((int)com.yiyiaddon.m.b.a<"s6m49pftgxka7","T7acwqBTr9JsOXCwCRLkZa+bWG6jQPZwy3tzorSvTbk=",5716806666171076182,-1006657824050121428,-6301023668471693851,7021645202249695891>()) {
               case -330182872:
                  return;
               default:
                  throw null;
            }
         case CONFLICT:
            com.yiyiaddon.d.d var4 = com.yiyiaddon.d.d.a(
                  (String)com.yiyiaddon.m.b.a<"sqz7fubk633af","6+QZHPPK098aa0vAqUiyDSxxduhSjjne9OEY83Hv3vUO2fCcLn8=",-5840169825291205960,-6192287268964251247,5183046899392997009,-7287729381131471577>(),
                  (String)com.yiyiaddon.m.b.a<"s2i9v7izwk3o36","lL6UfGIP8xmi5K42Tq33Pc45ZdN7ChYvlwQkFzVowDM19FWAJNq2MA==",6901108052720643635,849089806844826101,6098110154353890512,1663991319377718623>()
               )
               .d(
                  (String)com.yiyiaddon.m.b.a<"s2kzvbp52kgirp","dvNKp++75HBjFYt06jQ3zKCUEfAuQfVQqL5AMCCYfjs=",-901505182134927223,-8611408926587085932,-8029069632447620063,5505936193956503889>(),
                  var1.aw(var2.dk())
               )
               .c(
                  (String)com.yiyiaddon.m.b.a<"s253d52x94o1mv","RCpY4oBsgL0pjj5qPz3p7j/ON6xpON17JMeP8qP/TiddQnCmxuQhLZzC",-7563966088569937483,-7206198668365708895,-3394674626273371807,-6962824681205931077>(),
                  var2.dk()
               )
               .b(
                  (String)com.yiyiaddon.m.b.a<"s2dvg1o9zyj9ea","6sL0y2PD8F9wwWHbYl8EOaNo7BS/Cc0cTKGTZbz3e8GwdMxY",-8693804313622159270,-2472503452325797246,299653461425657215,4873748090436423069>(),
                  var2.dl()
               );
            if (var3 != null) {
               label46:
               switch ((int)com.yiyiaddon.m.b.a<"s90x9jcd4k1iw","LWuTu0+jRlFH7VsCZ42maERqk0ykni2nZIhwjoexOgo=",-3127486436296595887,-4870899023231698787,-3350581251552612791,-3714406866034694961>()) {
                  case 1461809510:
                     var4.b(
                        (String)com.yiyiaddon.m.b.a<"s1hygc34djx69g","cL2G7ygWwhRXXcKIUbVGCVOHhU/E7x3ubRbnEDBE+xA7SFNUqDRXbg==",-4604751208970371592,8549213458272884418,4790879535620630508,-8456543911174909792>(),
                        var3.b().m()
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"s34v0sbz9y0w5v","0Zik0WlRJgmLvcszhrc6v5i5n0ZeNvd0os9j3ggTePQ=",6043680628749728029,298195781059232419,5327266221971319164,4050022093490667665>()) {
                        case 1475139163:
                           break label46;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var4.b(
                  (String)com.yiyiaddon.m.b.a<"ssr9n2bjxgi8d","pCIH/EETIEO3KrAWxDFeg2WqrafnV+jK+N4+NRuu9EJtZVi9BnYS8g==",4883024710928902813,8138966671038388272,-5499577477342869496,-6815190178926035716>(),
                  var2.dm()
               )
               .b(
                  (String)com.yiyiaddon.m.b.a<"si1ye3swhetq2","Y/EzHkUXPltEC5cXkiPbzzwiSX4YZajiaFeTYsHKswPY5VUfgQtAuQ==",-2817585898246654906,4149328796819116230,4641987669135486126,-3776423585274519054>(),
                  a(var2.a())
               )
               .b(
                  (String)com.yiyiaddon.m.b.a<"s3kl9hjq67gi6j","wVgk59k2czod1b7BM/infOcNgDu8XpI0yn9NMB+bdUU=",5464812756637643807,-4538333885441950261,-8553727997525455549,-7061260727369391346>(),
                  (String)com.yiyiaddon.m.b.a<"s1wn3wj7o8bpn8","rQsip2qKJhLj8642sXVlj5/7zLNnvxd/OIEhUlKXsvwJqMv70LLTzyS2ZC+B0f5RuIDqcsTe",-1658124017926392535,-1105592725587392986,1293357224496538385,-1903495071947094431>()
               )
               .b(
                  (String)com.yiyiaddon.m.b.a<"s1sdztxxayhksu","DfFs+d6CPVm7mJBNq1tc2YehMIpUUpuejyVP6B82irE=",7983427803050733114,-2423066587621332174,-3881150969575503625,1151005548890877601>(),
                  (String)com.yiyiaddon.m.b.a<"s2rt1ljpmburit","xBZsykFOLTwIaxXd8/ODoT9gdteZZ36xJ+cBO4LziM1IOUd9KvOUhS+Eanyye7sCoQ1yncnNZm1hvStiUtGKwKdqtvRGJSVyKc3IU0m5A5KnRiHxN+Lf2vwCZjyZk6VMdTlZkwyjn5hP3z1O3SANKM+bpLFomm1Fzei/ygv0kmiXO2CitCw=",-7111976124009300500,-3948722183655597887,-8962616196446739480,-1300927479545838440>()
               )
               .a(
                  com.yiyiaddon.d.d.a.FAILURE,
                  (String)com.yiyiaddon.m.b.a<"s3smw23ykpk43o","C8h29+QDd9wP4c5jP/MIBaJk9oZCxN1eX6Px09B085bEBYcxKOFu3wex",5857749480048567553,3966558169906386221,-6154000490457035058,-937926451914264791>()
               )
               .g();
            switch ((int)com.yiyiaddon.m.b.a<"s1hwzqlkj0w4q5","G8JadZll0WeuWSO3K3U0FKyMVb0jVxYL6jPrqztYU+4=",8300288122140618234,5504265603649172787,-3003736092290139718,4929130337564074493>()) {
               case 1448875206:
                  return;
               default:
                  throw null;
            }
         case SPECIAL_STAGE:
            this.c(
               (String)com.yiyiaddon.m.b.a<"s2i9v7izwk3o36","lL6UfGIP8xmi5K42Tq33Pc45ZdN7ChYvlwQkFzVowDM19FWAJNq2MA==",6901108052720643635,849089806844826101,6098110154353890512,1663991319377718623>(),
               (String)com.yiyiaddon.m.b.a<"s3rdiyop3k33js","8rhn3dOBXLQ3wV8PGa2j/BMZGOiYWcigTdvAcLLqGj1jhwLdIxVRijKjUX12Dvu5zRKZ2fj9zJdg0ZvE6E/PLL1mHFQArrAZ99AlFG+bdMCWqWb8ViMwMtA9ayJ1+YQ+9fnS72iNWVrLuA==",-4818467306312084849,2709667289817997194,3871869211099983663,-6034664919462930597>()
            );
            switch ((int)com.yiyiaddon.m.b.a<"s2r2gsumgiq6k4","4GXM0J3xajsHbz6j1O024BTAxXNd6A28yd0rV9aZw3k=",-6830203676943938792,-51087005961501225,1820503943886683130,-6949426223504329059>()) {
               case -1205923471:
                  return;
               default:
                  throw null;
            }
         case UNKNOWN_STAGE:
            com.yiyiaddon.d.d.a(
                  (String)com.yiyiaddon.m.b.a<"sqz7fubk633af","6+QZHPPK098aa0vAqUiyDSxxduhSjjne9OEY83Hv3vUO2fCcLn8=",-5840169825291205960,-6192287268964251247,5183046899392997009,-7287729381131471577>(),
                  (String)com.yiyiaddon.m.b.a<"s2i9v7izwk3o36","lL6UfGIP8xmi5K42Tq33Pc45ZdN7ChYvlwQkFzVowDM19FWAJNq2MA==",6901108052720643635,849089806844826101,6098110154353890512,1663991319377718623>()
               )
               .d(
                  (String)com.yiyiaddon.m.b.a<"s2kzvbp52kgirp","dvNKp++75HBjFYt06jQ3zKCUEfAuQfVQqL5AMCCYfjs=",-901505182134927223,-8611408926587085932,-8029069632447620063,5505936193956503889>(),
                  var1.aw(var2.dk())
               )
               .c(
                  (String)com.yiyiaddon.m.b.a<"s253d52x94o1mv","RCpY4oBsgL0pjj5qPz3p7j/ON6xpON17JMeP8qP/TiddQnCmxuQhLZzC",-7563966088569937483,-7206198668365708895,-3394674626273371807,-6962824681205931077>(),
                  var2.dk()
               )
               .b(
                  (String)com.yiyiaddon.m.b.a<"s3lfxav9wba4b7","3qDV/2Aii8xS//mMUzINxACtaDSrxhlIRiNAlm48UXU=",2708705806062161140,-5841557380866759443,7852651637959763316,5781913304288759510>(),
                  var2.dl()
               )
               .b(
                  (String)com.yiyiaddon.m.b.a<"s3kl9hjq67gi6j","wVgk59k2czod1b7BM/infOcNgDu8XpI0yn9NMB+bdUU=",5464812756637643807,-4538333885441950261,-8553727997525455549,-7061260727369391346>(),
                  (String)com.yiyiaddon.m.b.a<"skfx6teainbzr","YgVFRwG1XZ6mdkt9NyiG7ZlM51JbrUx43tjV3qPYNo0gsTy3133ojTfZJrdPD1ppHbmYaaRD/FQ=",-2412386978028431559,216003365585723941,8678073552746531873,-3855117457934279513>()
               )
               .b(
                  (String)com.yiyiaddon.m.b.a<"s1sdztxxayhksu","DfFs+d6CPVm7mJBNq1tc2YehMIpUUpuejyVP6B82irE=",7983427803050733114,-2423066587621332174,-3881150969575503625,1151005548890877601>(),
                  (String)com.yiyiaddon.m.b.a<"s3m2cq3i55feqj","tnN2jnWVh2+HDaDLRUJ4BPbS5EDna2+CG2E+hWcd6+UrBrobSLyfV3HMmEhIGSK1ZvyDrQLOoxTZZhFkwVz4Hh5cLymUJNcdsXX6RrUPmn6plbMooj2KOvaprlNj0wggoqKEnp4bPMjS+S6yJ0wbD8UTPnciLTGkGfb6yEFg2YbvtP87JCDwTIRRaAtm47gBdV7gIsFj4Kst+EUE4qPF6taNMV8=",-7771664915055079402,5001256462980134972,-22317314108391869,7540875824848067244>()
               )
               .a(
                  com.yiyiaddon.d.d.a.FAILURE,
                  (String)com.yiyiaddon.m.b.a<"s3byij5iel2062","BZ69buBHXmp21xNo9G47UYlyn59Ve4J0V8SNUCRxWasdFQ==",9151459354653124679,6843975542162487745,-4157971095900692998,2452592799202457557>()
               )
               .g();
            switch ((int)com.yiyiaddon.m.b.a<"s1b20k2lhds792","+pzDSnO9X4fQmuevl3YGcmsLHOYVKilXa0R5+ojXrDY=",3843377919995786584,7607668514662131478,-6847240221822019456,-7758065525398722621>()) {
               case 210743870:
                  return;
               default:
                  throw null;
            }
         case UNKNOWN_CROP:
            this.c(
               (String)com.yiyiaddon.m.b.a<"s2i9v7izwk3o36","lL6UfGIP8xmi5K42Tq33Pc45ZdN7ChYvlwQkFzVowDM19FWAJNq2MA==",6901108052720643635,849089806844826101,6098110154353890512,1663991319377718623>(),
               var2.dk() + ""
            );
            switch ((int)com.yiyiaddon.m.b.a<"s1tij4lba3yq28","5vvIXsr9dZoAITzuya3JOVRrUZpLbFqvYOvDoW1aciw=",-1022689143335867409,-5107141937625004290,-4641515555944085960,3732982272073906086>()) {
               case 1241079778:
                  return;
               default:
                  throw null;
            }
         case NOT_READY:
            this.i(
               (String)com.yiyiaddon.m.b.a<"s2i9v7izwk3o36","lL6UfGIP8xmi5K42Tq33Pc45ZdN7ChYvlwQkFzVowDM19FWAJNq2MA==",6901108052720643635,849089806844826101,6098110154353890512,1663991319377718623>(),
               (String)com.yiyiaddon.m.b.a<"s3byij5iel2062","BZ69buBHXmp21xNo9G47UYlyn59Ve4J0V8SNUCRxWasdFQ==",9151459354653124679,6843975542162487745,-4157971095900692998,2452592799202457557>()
            );
            switch ((int)com.yiyiaddon.m.b.a<"sq1s6bbutw9el","ZYcodpDe3/y3I8vA9vcZ0Ng0KcbCUUVkqnt80q8jCbA=",6970178663850720218,7750883357660477122,-8844608071956161313,400607332989493973>()) {
               case -1345091317:
                  return;
               default:
                  throw null;
            }
         case NO_STAGE_EVIDENCE:
            com.yiyiaddon.d.d.a(
                  (String)com.yiyiaddon.m.b.a<"sqz7fubk633af","6+QZHPPK098aa0vAqUiyDSxxduhSjjne9OEY83Hv3vUO2fCcLn8=",-5840169825291205960,-6192287268964251247,5183046899392997009,-7287729381131471577>(),
                  (String)com.yiyiaddon.m.b.a<"s2i9v7izwk3o36","lL6UfGIP8xmi5K42Tq33Pc45ZdN7ChYvlwQkFzVowDM19FWAJNq2MA==",6901108052720643635,849089806844826101,6098110154353890512,1663991319377718623>()
               )
               .d(
                  (String)com.yiyiaddon.m.b.a<"s2kzvbp52kgirp","dvNKp++75HBjFYt06jQ3zKCUEfAuQfVQqL5AMCCYfjs=",-901505182134927223,-8611408926587085932,-8029069632447620063,5505936193956503889>(),
                  var1.aw(var2.dk())
               )
               .c(
                  (String)com.yiyiaddon.m.b.a<"s253d52x94o1mv","RCpY4oBsgL0pjj5qPz3p7j/ON6xpON17JMeP8qP/TiddQnCmxuQhLZzC",-7563966088569937483,-7206198668365708895,-3394674626273371807,-6962824681205931077>(),
                  var2.dk()
               )
               .b(
                  (String)com.yiyiaddon.m.b.a<"s3lfxav9wba4b7","3qDV/2Aii8xS//mMUzINxACtaDSrxhlIRiNAlm48UXU=",2708705806062161140,-5841557380866759443,7852651637959763316,5781913304288759510>(),
                  var2.dl()
               )
               .b(
                  (String)com.yiyiaddon.m.b.a<"s3kl9hjq67gi6j","wVgk59k2czod1b7BM/infOcNgDu8XpI0yn9NMB+bdUU=",5464812756637643807,-4538333885441950261,-8553727997525455549,-7061260727369391346>(),
                  (String)com.yiyiaddon.m.b.a<"s3by3mr0g4zm8h","54bBUV6xVK/xfVZejNoupD00VhKBWx4vd0ouNn8JN7z1HsK4iAWP7ydU/xWWHfZoHYUxCvYwkQpllcLwJ+GDGUfdeXKwKjdOGnjsq89aRo5OX7hQgJo=",-2430961415794851843,6394723637053888570,113688492331193768,-7845373109266859248>()
               )
               .b(
                  (String)com.yiyiaddon.m.b.a<"s1sdztxxayhksu","DfFs+d6CPVm7mJBNq1tc2YehMIpUUpuejyVP6B82irE=",7983427803050733114,-2423066587621332174,-3881150969575503625,1151005548890877601>(),
                  (String)com.yiyiaddon.m.b.a<"s3sh26vy51spdb","/qEV7WmU5Hio6sApRYmOivjCL5TyjiuztKJ5j4T7PmA174QLVq2beRUHdPDiaf84pA+ZtDGo5VOSjOeuvsp5Q6ue5SgbMoAmYCGQCGssEtaCjzGe+RdMer87cC982l3S7qB3Vr9X6yKAsaQHvB0w31fdBjfOn7PSz2ew9Q==",-211856892077704989,-7694512694911195008,-1146748786340353245,960649184473320016>()
               )
               .a(
                  com.yiyiaddon.d.d.a.FAILURE,
                  (String)com.yiyiaddon.m.b.a<"s3byij5iel2062","BZ69buBHXmp21xNo9G47UYlyn59Ve4J0V8SNUCRxWasdFQ==",9151459354653124679,6843975542162487745,-4157971095900692998,2452592799202457557>()
               )
               .g();
            switch ((int)com.yiyiaddon.m.b.a<"s176lq77u23rra","dJXdXD8tyqJkluLCMOKV793ziHnftYZtcEikT25ZU+4=",-6994197471241990004,-8421877139138948991,-7512455879967386278,589082907902439670>()) {
               case -187242152:
                  return;
               default:
                  throw null;
            }
         case FAILED:
            this.c(
               (String)com.yiyiaddon.m.b.a<"s2i9v7izwk3o36","lL6UfGIP8xmi5K42Tq33Pc45ZdN7ChYvlwQkFzVowDM19FWAJNq2MA==",6901108052720643635,849089806844826101,6098110154353890512,1663991319377718623>(),
               (String)com.yiyiaddon.m.b.a<"s19k61j933efyo","3nti7yRhX/8wWBQrwgkfoS4N0HDtrwh+p0rKB1qRYm1BDOfDwPQgNMGxRYsHqNNqAEOJt74XnuhSvOeXJ4Wl9ndFGlcZGLt+o+PZjuwp0eUE5CCrT6nU+3sjP93H3w17exk=",3995702214575256295,-8366995795465200339,-4198255161997824079,3214608398953069033>()
            );
            switch ((int)com.yiyiaddon.m.b.a<"skcm58leqk5nu","Z8qo7jfZ8wKlVtqupTN69iytX1uw/rop1+88rKtvgKI=",-1440074189577189516,-6424757813911008788,7799460905215034498,-116634679883509477>()) {
               case -1908273226:
                  break;
               default:
                  throw null;
            }
         case SAVED:
      }
   }

   private void a(com.yiyiaddon.e.n.b var1, b.b var2, com.yiyiaddon.e.n.j.c.e var3, boolean var4) {
      String var10000 = (String)com.yiyiaddon.m.b.a<"sqz7fubk633af","6+QZHPPK098aa0vAqUiyDSxxduhSjjne9OEY83Hv3vUO2fCcLn8=",-5840169825291205960,-6192287268964251247,5183046899392997009,-7287729381131471577>();
      String var10001;
      if (var4) {
         label77:
         switch ((int)com.yiyiaddon.m.b.a<"s2mavwak2oelkg","3gAxWjJ7zpBkBzmsqVWvph/n2wdcjc8lvX+SfGQg9ZM=",341703402775459201,-7985515285121304676,7061685684644773460,1268558012120757618>()) {
            case -1467154657:
               var10001 = (String)com.yiyiaddon.m.b.a<"s2jeeaj0vxyzvp","kn+S/eC1Y+IoLLoM8b8Sv/hpZJjacbcRO9G7Xz15kHZTNIau/HoYvuBb",-7852186566961034046,-201911862423357816,-8107981090667668553,8168414884214317279>();
               switch ((int)com.yiyiaddon.m.b.a<"s3l56zucl6iata","kpq4vgR+cn7me7QhcG4LxoFLd343T+1tPuowPrtYi74=",6697145456625562346,-9161790125790825665,-7153586863976642048,9215317109414668383>()) {
                  case -705665782:
                     break label77;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = (String)com.yiyiaddon.m.b.a<"s2uok9fb041m26","z3pQ1FK0N2TBPcoD/0G7p2JrK9CrOquCCKnuJ+6+VwDC8mp6L//w9dQZNg4Fiw==",3892906507748553850,-2845481424721753446,5185692076042684005,-601560206882324566>();
         switch ((int)com.yiyiaddon.m.b.a<"s2ruivif430ros","SkyBuQsY7KYG9vF+AE/YEjAhdgmCczKYksXCLDhHhw0=",7939125442925989251,892353394541365443,-1008764418989279314,-3772924116356925922>()) {
            case -825247003:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.d.d var5 = com.yiyiaddon.d.d.a(var10000, var10001)
         .d(
            (String)com.yiyiaddon.m.b.a<"s2kzvbp52kgirp","dvNKp++75HBjFYt06jQ3zKCUEfAuQfVQqL5AMCCYfjs=",-901505182134927223,-8611408926587085932,-8029069632447620063,5505936193956503889>(),
            var1.aw(var2.dk())
         )
         .c(
            (String)com.yiyiaddon.m.b.a<"s253d52x94o1mv","RCpY4oBsgL0pjj5qPz3p7j/ON6xpON17JMeP8qP/TiddQnCmxuQhLZzC",-7563966088569937483,-7206198668365708895,-3394674626273371807,-6962824681205931077>(),
            var2.dk()
         );
      if (var4) {
         label70:
         switch ((int)com.yiyiaddon.m.b.a<"sdxnruy6k6hg3","pneo98BDhPTB9Bk0drQkQtD2wYrfV/yZCkAMRmsZ8Wo=",-3701626927514493968,8394640974366875602,-5419395677758019317,-328947534245155790>()) {
            case -1896713179:
               var10001 = (String)com.yiyiaddon.m.b.a<"s1hdgtc2sj6j0q","1Arbtz4/Rb7LFP5/IupMBU7cZ/gvBXzbJ6QRAGvcnmhF+UxlHp4=",5416135216804787841,301046545476022558,-5992256211284314827,-2593170248412195173>();
               String var10002;
               if (var2.dm() == null) {
                  label67:
                  switch ((int)com.yiyiaddon.m.b.a<"stf4s4ouonjit","+JEgTatyMJQYR7xR+j0rTqE1zNjuWQKbBhyLve0M3fg=",-3189366895959855520,-7238532232396617554,-4208832836193797377,735498611588731357>()) {
                     case -1125946511:
                        var10002 = (String)com.yiyiaddon.m.b.a<"s27yxax7try0e1","2aTwIf7rudgFP42h80Oc/llRt8ie9jO5B29tZusvaCIWuFOJYd0crW1CUOn3+/PsF1Y=",-3707550997231888392,1000625186538734523,-4279416757165809382,-3101388535835895661>();
                        switch ((int)com.yiyiaddon.m.b.a<"s3upqf9wk0hdg8","t45lwvRov8NGHnQV9YTFvLGsVDhCGZgXDulWwGNTmk8=",-3026310008650290140,-5044146771634791182,-3044675317874784717,8415948615431250449>()) {
                           case -542042989:
                              break label67;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10002 = var2.dm();
                  switch ((int)com.yiyiaddon.m.b.a<"s34ghtrs81i0t0","MZt6NF44vRYguXM+Fog0XOJM3OucHEDkJClhpsi3Ea4=",-5982749832818580671,-6563441349528819849,-3598870472068469943,-7318740833512115657>()) {
                     case -858017855:
                        break;
                     default:
                        throw null;
                  }
               }

               var5.b(var10001, var10002)
                  .b(
                     (String)com.yiyiaddon.m.b.a<"sfe6lep17tj7e","2PmzIqEQ2r8Zk1NLdI8j09vvGi6xPxo8UxAk0TfIRsaT8BBaY/Y=",5824822050702340246,-8943776812930301080,6813532470181939767,1197029180062444724>(),
                     var2.dl()
                  );
               switch ((int)com.yiyiaddon.m.b.a<"shbtmghrvkvzt","80eXcDbuCJ+94AqzecI/It+rLXXpvnkPEGBHDBr0SJM=",2292523625064296235,6515475469083916909,6158563521806967027,-8137777683252753482>()) {
                  case -883389970:
                     break label70;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var5.b(
               (String)com.yiyiaddon.m.b.a<"s3lfxav9wba4b7","3qDV/2Aii8xS//mMUzINxACtaDSrxhlIRiNAlm48UXU=",2708705806062161140,-5841557380866759443,7852651637959763316,5781913304288759510>(),
               var2.dl()
            )
            .b(
               (String)com.yiyiaddon.m.b.a<"s79vjjqbsa2l3","DUm6HD6g//uGuOtOz3pr8Jfj64ZiEVpuGVaHYorabkMWpzxI",-4061244801703098640,48876430446666804,-4497788370651448904,2153831142987964623>(),
               var2.dl()
            );
         switch ((int)com.yiyiaddon.m.b.a<"s1z6tau0zpj655","q4RQEFDcusp3RcnAUl5MEimk23uEU5VUQU9WRzAel8E=",-3439282541304965420,-99048520783550824,-2631766997773126789,7446369691183503419>()) {
            case 1715224835:
               break;
            default:
               throw null;
         }
      }

      if (var3 != null) {
         label58:
         switch ((int)com.yiyiaddon.m.b.a<"saqjz0z1rmtm8","IkjbWjKrDJRP3RTSPhfT/C6T6XkFiGc7R71t1mt9fuw=",-4034166595250627118,-2388771874507384058,2110929053155418758,3085504539585616189>()) {
            case -132233181:
               var5.b(
                  (String)com.yiyiaddon.m.b.a<"s1bjohpftd5ktj","h8aN4ptIVyY3CK6dF+4kklMULyIwPA56QEjwAzAz3iH7IB0r",-2400224031797054573,-3448685719734703820,-8381262858290990622,2989490687697794217>(),
                  var3.b().m()
               );
               var5.b(
                  (String)com.yiyiaddon.m.b.a<"s29hio0sply5jx","6Sl/R+ZYVmdfaz9PiXkfSLRWFGfFSjW8q2yEH2nb2ko889db",-9118827932976708888,-1390223204275636756,-3902669493979311218,1153866815017308962>(),
                  var3.dX()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s2nx7n1j4dmwza","LAIIuoDxNBKtAe2yvm4OEqQBsT4sro1NVzmq34QtFM0=",1778772774330736200,8346643269803017018,-5381043993157392279,-1557351342521663984>()) {
                  case -1728462863:
                     break label58;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (!var1.T(var2.dk())) {
         label53:
         switch ((int)com.yiyiaddon.m.b.a<"s3o2ot3oc6s4h1","NhrPSV3XTr9v+pSPAgS/2f7HfOyFcUe/flJ+PrZn6XE=",-4919318768962464907,4131018031077669019,-8007774215449840602,-3574023622566399406>()) {
            case 2049348708:
               var5.b(
                  (String)com.yiyiaddon.m.b.a<"s185lmlx9u8t82","z/vizYc5TPyfADOqQW+jZNa//W1nCCnioSSSE8ZK2rI=",-3810101435659976899,7005993847171455593,-4612194164766956635,3244902763465313161>(),
                  (String)com.yiyiaddon.m.b.a<"szq4y2geca3jo","4KmiyI9eRpPSWxE+5tihc0umsoIvCDwiRvgLHL8oHTq76sR7SZ1k3WzwSanoy6caB3G7fzAMvhTKptaHthV2a0M7cIWIbUgMkXhl/XmHc8NCCxEqTDiOogHHkdlQAubMX1ECsPQ/y3B1XYK2BtiGsNZT1p9jX4X1jXBMDAlognHrHHT1FjfIKvdmlDED9GBhtpBrRi+tycgtEK3nQ/Yu9z0aB5ohlTWD7LhkMw==",-3116930912436228374,-2634353129989594997,-5974464085543936274,4148938765534557935>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s3t7t9ksrjjorv","iSDzwX88KmfDqfbax2kC7N71EcVoZ0cPTXV78lz/RHE=",5955208217931962491,6707223927614675787,4492709663234437092,-8434608125504268434>()) {
                  case 1402764830:
                     break label53;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      var10001 = (String)com.yiyiaddon.m.b.a<"s1wrhpxbmqmi09","Gc8ueL6xNZEomeHW9AwVSL7TjZjbj7KKQo2CHRXCgWIgThM9",-6963345684935918232,495622930400560890,-3775760861808791942,6450607255845996773>();
      String var8 = com.yiyiaddon.e.n.i.c.VERIFIED.m();
      String var10003;
      if (var4) {
         label46:
         switch ((int)com.yiyiaddon.m.b.a<"s2k4fyz8937d82","FPA1inX4g2ghV8ga1cdCl0RbkZpUUY+Fg2EamE1/YhY=",3032507305517473523,721343662670498820,-7218022607116443529,-6242655214124604601>()) {
            case 840228935:
               var10003 = (String)com.yiyiaddon.m.b.a<"s19utfulnfocoj","nVPzQ7YMI9+4bN3d8q+KqR85mjar2k9yTAEmhSR6KGFDLkLaQ/KB8tJ/M6yrH6aP25/Z/Dx+xUoXsfhlTHw=",-8788338377898927491,8582437399461587464,8916993602643858992,6157253321401740265>();
               switch ((int)com.yiyiaddon.m.b.a<"s3iia48yq4msmp","HGHdWn2E0tnmr0nQa8swUUTCNrG8j3ZPfSiNkg4qjT4=",186670371313622970,-7688142928584583549,3039170189775672991,-4267459109364363264>()) {
                  case 655483178:
                     break label46;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10003 = (String)com.yiyiaddon.m.b.a<"s1etjuaj39wf83","7OIDMrZCDC/wtMuM/+a/135aXPbBR+u9H39sraVQ2uwvueaAWVyXYUSsp8cS1n2oZXmEyvJLO1qrmze9Z5Q=",-2788889284148412030,-288840500692461730,-8733472924988995962,-7633712996363092872>();
         switch ((int)com.yiyiaddon.m.b.a<"s2gvoeniyu16nm","+bMproKSR6LLSqa1qBuy/sHaghiaXssk+Qn713qWEY8=",4748887276429969988,-297915391113476635,-5176535043159902987,3630936937795224964>()) {
            case -703201917:
               break;
            default:
               throw null;
         }
      }

      var5.b(var10001, var8 + var10003)
         .b(
            (String)com.yiyiaddon.m.b.a<"s2acz9r7inaig5","FH+v0LoGDuVAlJpmgLFJOeLPwXncCBDbJoBIPHtzg5EJ/g==",7647061906678561221,-454896479978713889,6575461363285076573,-5578803036969514839>(),
            (String)com.yiyiaddon.m.b.a<"s3ac2bpckesg3t","BD4ZR593+DcemYD3j7O60EbfhYc1UwNhIgb2EBGFGwxVdcY5ygDXVHH96bCjfSGFjwyn9ZaNlVhh8rW7wdE=",-3012852697167981306,-4645834001132879923,5083892393986209937,-5481209979427477249>()
         )
         .a(
            com.yiyiaddon.d.d.a.SUCCESS,
            (String)com.yiyiaddon.m.b.a<"syjqri18mofjv","hwzoGlaDfCUuGVkwqGolpn712k/Aj6CC/Vw8WE5Eh06oTw==",7570480996258444448,4995991830987176633,8817993968230892971,8550479547235946151>()
         )
         .g();
   }

   private static String a(com.yiyiaddon.e.n.i.c var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2orpwkld70s0y","96eS6J6Kt8DeIefc5Ny1JKh77+rAsRBEx+FX5nV8AcM=",-5594682304766642087,-6213696649392697252,8449905336785939682,7798406991765785545>()) {
            case 104431814:
               String var10000 = (String)com.yiyiaddon.m.b.a<"s1wpw8puhy6f4t","wQO+g0MUdm7qVjb501LfbN4JKAypbxPCBuXKS/sgF+U=",-5923094747591221309,6558209064144694088,-4076384035400611398,1893129212892744115>();
               switch ((int)com.yiyiaddon.m.b.a<"s26e7o7iuugjf7","OTisnXaJaBUU5YNaWxBFKqr3xRDIjKvTk7k6X8/S5T4=",-4523435528487555122,-5504587992880168318,4084520493441972209,-5125013770857758263>()) {
                  case -1556087260:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var1 = var0.m() + var0.name();
         switch ((int)com.yiyiaddon.m.b.a<"s2owjybzhag3e0","2+XpKEcniIs5k1NAumexgmKJM9+M0heXiUmOWozX8Cc=",-4895859627841530031,-9099605685166580608,-4045851079961478099,-8030918860573196803>()) {
            case 1091630591:
               return var1;
            default:
               throw null;
         }
      }
   }

   private void gx() {
      com.yiyiaddon.e.n.b var1 = a();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s27sbprr8fmati","YUL95mGypN0FLuxBQ5ORU1Hq1Bw7+d2CsbCqvGmZNtI=",5162145274749626409,1958070852336846357,-8512003472690510728,3664002689563360628>()) {
            case -770822946:
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.d.d var2 = com.yiyiaddon.d.d.a(
            (String)com.yiyiaddon.m.b.a<"sqz7fubk633af","6+QZHPPK098aa0vAqUiyDSxxduhSjjne9OEY83Hv3vUO2fCcLn8=",-5840169825291205960,-6192287268964251247,5183046899392997009,-7287729381131471577>(),
            (String)com.yiyiaddon.m.b.a<"s2ik82f64wzeqz","rP7ZEt407t0ojG+f04+gYAE+bdHwQXOengqLuAaJfZLD/oL6",7252119293384985654,-4307608772070786773,2086933718236475548,9073474130374669482>()
         );
         Iterator var3 = var1.aI().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s285ezh7ck0lyc","3Fu495+vaDJkkaTD5XhIFAUTNZ+P617o0QRkHrqelPY=",6363220214092208538,-3915048831866637246,856068200636779961,2058716011214337294>()) {
            case -1261715404:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sp9htcwmwoe2t","M97SohF9s7OURWx9oQWVt4PVlQNKXXIQfAwmFrR4vqI=",-7013558699762627027,4794531593603567696,-6983826507896635618,8735695976261208275>()) {
                     case 909267325:
                        String var4 = (String)var3.next();
                        var2.b(var4);
                        switch ((int)com.yiyiaddon.m.b.a<"sw7ezgkw430mz","leWkPfiMonL7gQVjs6hlzMunWwU9Q2svAu6I7uu4WDk=",5875948956790311049,8197920102646310414,-649046642445495775,6071137285728931941>()) {
                           case 2099055522:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               var2.g();
               return;
            default:
               throw null;
         }
      }
   }

   private void gy() {
      com.yiyiaddon.e.n.b var1 = a();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2fimu9r4tbzqp","7SUFBGobjSm+LWC7J3ElTcI5wppLJrdueWD6DwUNnXQ=",-2402305215600470953,-8965486953643527466,5161483635081110687,8889701108081427794>()) {
            case 1153199424:
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.d.d var2 = com.yiyiaddon.d.d.a(
            (String)com.yiyiaddon.m.b.a<"sqz7fubk633af","6+QZHPPK098aa0vAqUiyDSxxduhSjjne9OEY83Hv3vUO2fCcLn8=",-5840169825291205960,-6192287268964251247,5183046899392997009,-7287729381131471577>(),
            (String)com.yiyiaddon.m.b.a<"smktzqk8fq0aq","Fr8A7SdHyopbeRGOvN5IExm1XA5HgxNIjgVLYICN9Vq+EaV6zhY=",8005460612184672052,8478090587236350838,7047894958465918382,-2299332207740354238>()
         );
         Iterator var3 = var1.aJ().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s1xlhgtfruyqzb","yjsip+JVyqK4G73gGl+yf9EsGlVeZjHs3lzEal8od6U=",1236381755992121374,-4439679671987376483,2409572995209972701,-7044190177558686058>()) {
            case -1446186890:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s28oywjb3bm87","nnYFG8htuui8+cZfEkWRJKlrJRAwOcWigvHPTQ2iQ0Y=",117647437825698570,-1995058754975087030,-6947779634558617554,-6414720101166341125>()) {
                     case -1488582409:
                        String var4 = (String)var3.next();
                        var2.b(var4);
                        l.info(
                           (String)com.yiyiaddon.m.b.a<"s3v69oxmt3327f","0LUBMuI1NdnQvNeMbuMxcQHI9l2gj7CctBe4EdYPCTUN5MUNRmBaownGu6QEdI65q1Y=",-6474055739874736,1960528254182568805,2501112633591903339,-6566262625879622616>(),
                           var4.replaceAll(
                              (String)com.yiyiaddon.m.b.a<"spykp7j2d3qza","PgjVc6DkpJi0UcyB/7PlLEGmWz+KyUbv4y+FUez6Ww7MtLsl/1E0YQNhPX7rN8s7JeguTD3NKnX+K6WwOFJ1tEqoYRA=",6945102127457684317,6000794557368467120,271091415954429336,-2683402287041716284>(),
                              (String)com.yiyiaddon.m.b.a<"sgleyljfazh6j","mjYyNbDfi/4Eh/IJe5DMhxl98BybQatVV0aGCA==",3214615122877264650,-8968572304130150370,-6110085062431042952,-2250863065076701131>()
                           )
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"sd49o3nga2o6o","rjPArbJoGlYMHKncT+S6ZWVy05hj8pIW1J6tKODkTiM=",-7915006764042178313,-3831401029838846118,9106249997440462385,3196359110685114724>()) {
                           case -1862289137:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               var2.g();
               return;
            default:
               throw null;
         }
      }
   }

   private void go() {
      com.yiyiaddon.e.n.b var1 = a();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2q0n144wfjvsi","r+BmAJ184aPQ19rampRde2OoIqty4M3Wk9jD3zbuixE=",9084766810800954234,-2667230960008652482,8771173167497098759,-4312441159477769469>()) {
            case -536891530:
               return;
            default:
               throw null;
         }
      } else {
         if (!var1.cU()) {
            switch ((int)com.yiyiaddon.m.b.a<"sejh1cgefx9b4","kQ3VzSyWPLWl+3ej1YY+/K+tBQIh+PN8qdCXKmOdII0=",-8848460125433260772,3902897280129751823,-2975312849205093250,2788826442629967795>()) {
               case 323458813:
                  if (!this.a(
                     var1,
                     (String)com.yiyiaddon.m.b.a<"stlf0u2b28ugu","a3P6sEgMmjBh8t45xUTw75eXns/ihgH1DgKzScn+LUUwVadR4pvaLQ==",1652485559764174570,3428326462682828135,-2600274619446882609,231855419675597244>(),
                     (String)com.yiyiaddon.m.b.a<"s31pvtigig20k4","ZGrS4wdG4l7ADzgC4Mro1yl4E6ZpXTb6MXdk0g2WT6R1Mw==",684013962569683933,1729639828172750480,1135587333869960955,-7248501627171470114>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"snrt13qyppgg2","ZOXyihKlZ5ggw3eh1/gzR+kwp98zcccZLAGF/t73mq4=",3458789115586315863,-4261997670130647057,5543644672503249292,-177330100789494452>()) {
                        case 1415342156:
                           return;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         var1.go();
      }
   }

   private void H() {
      com.yiyiaddon.e.n.b var1 = a();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2b9ivvs5fkcxz","lpiXCvGLolq8nGBg3T2XeLHBjqTRS6pLdPQXlirnsKc=",1395025637120175125,1548794800549330520,-3939826316661835491,-5130177393033638262>()) {
            case 752160239:
               return;
            default:
               throw null;
         }
      } else {
         X.execute(() -> X.setScreen(new com.yiyiaddon.e.n.s.b(X.screen, var1)));
      }
   }

   private void gz() {
      com.yiyiaddon.e.n.b var1 = a();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2cu3d73dj7su5","o1FslwkTLGgQwq2R/2FMOPw9DaL5FFEONfLWuwzrb8c=",7661777377685540987,8398744774752262732,8535130908106983966,4123868094289167090>()) {
            case -569529042:
               return;
            default:
               throw null;
         }
      } else {
         var1.gq();
      }
   }

   private void at(String var1) {
      com.yiyiaddon.e.n.b var2 = a();
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2q2fbzkwflcy","pKdfAv0IH0f/yVelG/wYlTfsL/G4yZvByoneU0lbIXg=",-259933685792765454,7887351332119793145,1985811309943497415,4641877774281220176>()) {
            case -1134169926:
               return;
            default:
               throw null;
         }
      } else {
         var2.ar(var1);
      }
   }

   private void gr() {
      com.yiyiaddon.e.n.b var1 = a();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3r9om9zk8sjb8","R7W8hKa5x+SynnT4DF4VJSHclju4kvkoM72LPfwRxTc=",2385018347011218702,4676641777724854240,-5012805594810305304,2293629421229273208>()) {
            case -559571807:
               return;
            default:
               throw null;
         }
      } else {
         var1.gr();
      }
   }

   private void b() {
      com.yiyiaddon.e.n.b var1 = a();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"snt8a71e76q9v","bIbX8H+jObsabG4b+eOsQhty1q2qNw1aP4iNy3Ge+cs=",90390382747248575,-6157293573398239483,-3408202484194846655,8910480366740159891>()) {
            case -942943010:
               return;
            default:
               throw null;
         }
      } else {
         var1.P();
      }
   }

   private static d a(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3o0z7iyjkp2dn","kA5pgnaOK1Y4Cevd9epi7iF60JSl6JG4Gs8jUWN5RyQ=",-4575880347907490688,-8341485556861206874,7009604921341729155,1119572427888756973>()) {
            case -1632067935:
               return null;
            default:
               throw null;
         }
      } else {
         String var1 = var0;
         byte var2 = -1;
         switch (var1.hashCode()) {
            case 23694740:
               if (var1.equals(
                  (String)com.yiyiaddon.m.b.a<"s2jejvrlwm0d1p","4qmA8r/XPFP6JqDOrlj7gZOy5FuEJCuZhSv29eEEdg2bZg==",8218061171009088085,-5064152269899743302,5565849656014802085,1278644389990857325>()
               )) {
                  label66:
                  switch ((int)com.yiyiaddon.m.b.a<"s3nceuaz4f8dum","pfCJEqPvfAKw3mfJgtEZrTjSew8AEjBdl2NlDYXngsQ=",-5147641780662446621,316745314996309656,3311429170457089405,4035827638173241532>()) {
                     case 274751945:
                        var2 = 3;
                        switch ((int)com.yiyiaddon.m.b.a<"sc37ng01sr1r0","IsImbiA0xuo3hseZvNBl915qbczgJxu2di5efIpykzY=",6551320997248713063,3354397025282928591,1176267766019857971,3893347365107919723>()) {
                           case -115325527:
                              break label66;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case 24829216:
               if (var1.equals(
                  (String)com.yiyiaddon.m.b.a<"s3sxbb81m51gvi","CTUAXdYYv+aTFV9SEIYamGOlgE1AFF9Abgr8q8z3JzAC4w==",2930798062011935248,-6995518132198992968,546751193290339920,-3308197978013727515>()
               )) {
                  label73:
                  switch ((int)com.yiyiaddon.m.b.a<"s1gk6kzaiihfmg","WwNumwyCt6Qg7Wp0EGa3GfFkZsVFrwhBigEyUabIQNM=",-2573309921278884066,-4189356523504490728,1919774561799108954,-7277321347809815939>()) {
                     case -358133376:
                        var2 = 1;
                        switch ((int)com.yiyiaddon.m.b.a<"sn026qxw573og","hVrVk79T3YVWs/c0LKGpbY24VYqi0p6/1AHpORCnLnA=",3597115700497526463,-6253486715696774891,4491354788885712625,7126075996575317291>()) {
                           case 427904472:
                              break label73;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case 30721262:
               if (var1.equals(
                  (String)com.yiyiaddon.m.b.a<"s14h8v81np67c0","Z3Ye1nZbtmYCu2XWN5LFjxvBuKnbn5tpzyd3O6i4t0FX1g==",3046822374963697746,-1688513679744655714,4036794937601409496,5920764329472240351>()
               )) {
                  label69:
                  switch ((int)com.yiyiaddon.m.b.a<"s2uhf9jngu2hot","nPBxACcqABWaw2rh8pEDcyB60pJ9lqWQUE6KU+Bo3yY=",2706436035780450732,-1484959545050619702,-5220881279150713154,3960605599763646216>()) {
                     case -883815844:
                        var2 = 0;
                        switch ((int)com.yiyiaddon.m.b.a<"s3ruejqwbrn4il","Yd8pE9XEfthNHYJ8zKcY30vkJGmU8PLUYBqLstfDDeg=",6914595802647536493,7530389369431888169,3580566058297861272,3614805288380016677>()) {
                           case 554624938:
                              break label69;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case 34442794:
               if (var1.equals(
                  (String)com.yiyiaddon.m.b.a<"s2m8jlpu7023gm","mq9g72XcNdo1lIWC1IGb98GoparpkUDkZlHKCRLqpaH+Vg==",-4591889256145428328,-2341702815977296452,-5196711731464887476,5793387618174808639>()
               )) {
                  label81:
                  switch ((int)com.yiyiaddon.m.b.a<"s3f8ly4yr3ghoi","QatK6smPeUhjH3pr0p07EEMLVqwVoNjP34DPy9mGyJE=",5083009805232217871,-1539983581330007161,-7150033652446706173,2799612778247222249>()) {
                     case -128995753:
                        var2 = 2;
                        switch ((int)com.yiyiaddon.m.b.a<"ssl414tq2m2lj","hrJXzMwZbvEiXRI1r1fB2WL6qtID0i0F8feWlwA0DR0=",5323404515831271173,5340948032176084648,681408598784703370,5084432322367835713>()) {
                           case -465744454:
                              break label81;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            case 40060539:
               if (var1.equals(
                  (String)com.yiyiaddon.m.b.a<"s2edmnjf8hbnh","Kb7U8waZRLl0HHx0P2mCcqslV8GzrD4yKMUhm/HquhT2JA==",-482139897019007661,-6877580677613863241,-5809425917397516779,8605099520307291481>()
               )) {
                  label77:
                  switch ((int)com.yiyiaddon.m.b.a<"s2ohkvyt3kt2js","qC8obYGLn0wEh/3h59I8nU/Vqz+XjrDfhMEEyUcaYiM=",8772050158386112650,-7050278895874477535,1592451925698941628,-6813383512682485969>()) {
                     case 431585302:
                        var2 = 4;
                        switch ((int)com.yiyiaddon.m.b.a<"s1nguxzu4oaqup","7HqhHBS/G07sY4p7zPTuIHHxq+IK2xexx7QqOSr5YZ0=",-5986174259234642244,6425106990242545009,4314828923210566386,8440292907455168149>()) {
                           case 1725400902:
                              break label77;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
         }

         switch (var2) {
            case 0:
               d var6 = d.SEED_BOX;
               switch ((int)com.yiyiaddon.m.b.a<"sg51qtz9h5lzl","6vcp8ShfpjA1LqQ36duFxdkGmtFh+zxr08lOBkjQwkk=",7897131804138360117,-1814037318258388056,-8728960269912284663,-181580835004333872>()) {
                  case -1112535194:
                     return var6;
                  default:
                     throw null;
               }
            case 1:
               d var5 = d.OUTPUT_BOX;
               switch ((int)com.yiyiaddon.m.b.a<"s38iprugsc66f4","MSsPVfl2fjwDm3pPlSG4fO8CtB2EWzTGr9UwAOS1H+w=",-1630404712192999870,-1007177963697905887,-3576997255512034039,-6726798758816338980>()) {
                  case 1463865968:
                     return var5;
                  default:
                     throw null;
               }
            case 2:
               d var4 = d.WATER_SOURCE;
               switch ((int)com.yiyiaddon.m.b.a<"stwmw4mneyk5d","I7Dk9S3cxdisv9bLdwcWQrJGccWYeBJRDGXq8A3ssxE=",-163632223621638562,1767360854028039265,2709706285396469581,-4505867529989933612>()) {
                  case -701913900:
                     return var4;
                  default:
                     throw null;
               }
            case 3:
               d var3 = d.LAVA_BOX;
               switch ((int)com.yiyiaddon.m.b.a<"s14rd1b0bfikds","d6Bp5G8JJD8otf5TpEfrbfZwmh6k9wUWE9PSvtMcxNI=",-5335432316629851102,7699633704737046946,-9152603221238500104,-3418327029876672132>()) {
                  case -1538062339:
                     return var3;
                  default:
                     throw null;
               }
            case 4:
               d var10000 = d.BREATH_BOX;
               switch ((int)com.yiyiaddon.m.b.a<"s1sgxtquu30c97","IkTSbkZre7v0KblOo7+MRs330x/aR7QnEjUFu7mfmdM=",-4737746796000269817,-5390384342486803033,-3287707276260724499,1815917677906913324>()) {
                  case 1334077550:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               switch ((int)com.yiyiaddon.m.b.a<"s384oqxxbacpzg","Yfmu29C/6n9u1/FAJRP2B9JJHqeyaursGqkCz4qL+I8=",6557980945682743834,2602095542621789368,-3694160821046625977,2444984405871914904>()) {
                  case -412625601:
                     return null;
                  default:
                     throw null;
               }
         }
      }
   }

   private static com.yiyiaddon.e.n.b a() {
      com.yiyiaddon.d.b.a var1 = e.b(
         (String)com.yiyiaddon.m.b.a<"s3ay4qneyewd2j","w0vh31Jz/+wUyBym/xMGVJ+IApQfLmo2MIW6gKm9qWL4OBNdAPpo9+ux",5977986089021046368,2977633729040074907,4444463483065341828,6708931777353862945>()
      );
      if (var1 instanceof com.yiyiaddon.e.n.b) {
         switch ((int)com.yiyiaddon.m.b.a<"saet5wi1wkokk","B9wftZEYDZPeHrRnnZshatRdtX6hbcyGwSPgQfhGqzA=",8529944568932033230,7942425980840561961,9203434315797700492,8682117980274071979>()) {
            case 707549886:
               com.yiyiaddon.e.n.b var0 = (com.yiyiaddon.e.n.b)var1;
               switch ((int)com.yiyiaddon.m.b.a<"seoqqchnv8p41","bT7HOK34GC3qdGCD++rnQf2E5yz08XT5/0sYA0zicl4=",-6182481055502783801,2446748967564244385,-7744745856095717933,-2655918041866264672>()) {
                  case 1579750330:
                     return var0;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2fodci6grussk","vPh9DDQ9ES6ARsc4TAhmzJMU59wStv742Bp8I/bYoR4=",-9206669437554247604,-3033449107153620962,2429341752238109003,5467432912750778399>()) {
            case 1443057070:
               return null;
            default:
               throw null;
         }
      }
   }

   private void c(String var1, String var2) {
      com.yiyiaddon.d.d.a(
            (String)com.yiyiaddon.m.b.a<"sqz7fubk633af","6+QZHPPK098aa0vAqUiyDSxxduhSjjne9OEY83Hv3vUO2fCcLn8=",-5840169825291205960,-6192287268964251247,5183046899392997009,-7287729381131471577>(),
            var1
         )
         .b(
            (String)com.yiyiaddon.m.b.a<"s3kl9hjq67gi6j","wVgk59k2czod1b7BM/infOcNgDu8XpI0yn9NMB+bdUU=",5464812756637643807,-4538333885441950261,-8553727997525455549,-7061260727369391346>(),
            var2
         )
         .a(
            com.yiyiaddon.d.d.a.FAILURE,
            (String)com.yiyiaddon.m.b.a<"s3byij5iel2062","BZ69buBHXmp21xNo9G47UYlyn59Ve4J0V8SNUCRxWasdFQ==",9151459354653124679,6843975542162487745,-4157971095900692998,2452592799202457557>()
         )
         .g();
   }

   private record a(BlockPos B, String qM, String qN) {
      public BlockPos a() {
         return this.B;
      }

      public String dc() {
         return this.qM;
      }

      public String dd() {
         return this.qN;
      }
   }
}
