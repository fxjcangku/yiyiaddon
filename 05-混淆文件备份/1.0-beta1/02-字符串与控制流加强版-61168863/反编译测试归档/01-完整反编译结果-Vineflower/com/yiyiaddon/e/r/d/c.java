package com.yiyiaddon.e.r.d;

import com.yiyiaddon.e.j.k.d;
import com.yiyiaddon.l.h.g;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class c {
   private static List<g.b> bn;
   private static List<g.b> ae;

   private c() {
   }

   public static void a(Screen var0, com.yiyiaddon.e.r.a var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3jqqpam88ftjn","KEF0nbPpr4IXlNFPvBdc05yo6H2VzmqELZHnwe8eekY=",-1113653117434452303,2551664791330775503,6390736569168334732,7627382025213214867>()) {
            case 1771518682:
               return;
            default:
               throw null;
         }
      } else {
         List var3 = var1.a().ct;
         var2.setScreen(
            new g(
               (String)com.yiyiaddon.m.b.a<"sjxzpme1hatxv","1YYC0Rr6bJ+MifiQyK1omd74x4/+52S8FdcUPLUNDtiw2fp+tbT0fg==",2134536324532219496,-898021011408453320,997273872749641285,1861127491919484563>(),
               var0,
               w(),
               () -> new ArrayList<>(var3),
               var2x -> a(var1, var3, var2x, true),
               var2x -> a(var1, var3, var2x, false)
            )
         );
      }
   }

   public static void b(Screen var0, com.yiyiaddon.e.r.a var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s17hd5lo77kkfh","NIdhcWmmmNzyoE7dV5izb9lM2XEs+kT3PF+nndkqc4Y=",-3773455337992116713,-2739114840378849788,-3486650516887928978,-3486880646836084661>()) {
            case -406087929:
               return;
            default:
               throw null;
         }
      } else {
         List var3 = var1.a().cu;
         var2.setScreen(
            new g(
               (String)com.yiyiaddon.m.b.a<"s1jr6qo3nmnh89","hjMf7bleE/ydMNDhEPLDnarvg/NOBggAZJpMf3fOZJDdiDlBsEYhGw==",8901821143405239087,-6574432905954788167,3455543063654539139,146349768983284503>(),
               var0,
               B(),
               () -> new ArrayList<>(var3),
               var2x -> a(var1, var3, var2x, true),
               var2x -> a(var1, var3, var2x, false)
            )
         );
      }
   }

   public static void a(com.yiyiaddon.e.r.a var0) {
      if (var0.a().ct.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3w42cln1cd4w1","PbWNP3J1l6/QEdJU1El5l6MA9ZZvjJvKy2P+0IwcmRA=",2840826691595468724,-2675793263218270932,-3561480398889279897,4560638159179724816>()) {
            case 1350356610:
               return;
            default:
               throw null;
         }
      } else {
         var0.a().ct.clear();
         var0.L();
      }
   }

   public static void b(com.yiyiaddon.e.r.a var0) {
      if (var0.a().cu.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1n71xjxgw6e2m","dIS0zaMwltRLfspCaSOYlfXK/6ykzcs59534zBsESUA=",5211389914086121321,8227414732682432482,-2988398525625024891,6176823171043200861>()) {
            case 1393006633:
               return;
            default:
               throw null;
         }
      } else {
         var0.a().cu.clear();
         var0.L();
      }
   }

   public static String a(com.yiyiaddon.e.r.a var0) {
      return b(var0.a().ct, w().size());
   }

   public static String b(com.yiyiaddon.e.r.a var0) {
      return b(var0.a().cu, B().size());
   }

   private static String b(List<String> var0, int var1) {
      if (var0.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ac73rl2f701x","5cEukyyNPdZ9+Sc1TvMtNRcf5Gnd1JR7szX6IYP4i1s=",1105858899341142920,-3740961025222057391,4245578230541709697,2633316684799426438>()) {
            case -1621425459:
               return var1 + "";
            default:
               throw null;
         }
      } else {
         return "" + var0.size() + var1;
      }
   }

   private static void a(com.yiyiaddon.e.r.a var0, List<String> var1, String var2, boolean var3) {
      boolean var10000;
      if (var3) {
         label35:
         switch ((int)com.yiyiaddon.m.b.a<"s2x3dadjm27862","KgGXhz1OxwHlmD0NvOsh6D1LHGYwFTLs3Uts35memDU=",-5790911208843331282,693361070013937086,-7201109731203590620,-3304058482965997718>()) {
            case -1444422216:
               if (!var1.contains(var2)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s313dnmpa55sg4","TU3RtTnOudPtuma/Qxlf74w75qUjVDXkH10Be2euuGE=",-406646221037441647,8346700109285226089,1190147472828019204,-2806529758401952282>()) {
                     case -317079046:
                        if (var1.add(var2)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s36ww27ft0ksn5","+bzpMDja7o/tmWCXLG+yT09JHRMa5yi+Ii6tpz3qQFQ=",542866956976513767,4524245580574523790,1367296244369847223,1824733346962149478>()) {
                              case 1259201581:
                                 var10000 = true;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1d9mobl81c5yo","XbYPntF17a1u3NitXGS/UkSWUHq+dXgDyMuwvaI0tsk=",8236576460249310516,-6219711513159544338,3079730012189465156,-753829458541453743>()) {
                                    case -246798254:
                                       break label35;
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

               var10000 = false;
               switch ((int)com.yiyiaddon.m.b.a<"s1r9n01rdyof6m","SNId5ODSIQ7Getim3bY4wI1RwPu1kCdfUtqmdrEdN7o=",-1998961540656173183,-5257729293592606396,6765277301411118419,-5538439564097582328>()) {
                  case 272520002:
                     break label35;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var1.remove(var2);
         switch ((int)com.yiyiaddon.m.b.a<"s2u9cjpthwqmyp","FVljHyGaSq/0OK7tacyFptZ2iEsOoIXwIBCpbDMFo84=",5635193511865237186,6935231418495189520,1615650784279837881,6573461501731868182>()) {
            case 1503086895:
               break;
            default:
               throw null;
         }
      }

      boolean var4 = var10000;
      if (!var4) {
         switch ((int)com.yiyiaddon.m.b.a<"s1bhfqdrwucjec","m8WmhUpSZttZ1lYG+lgCOBeqgI4gBsN8GzjoQqTpAjw=",-497127085000786801,-1678011759358572489,-5125277491134474943,6124243422578628573>()) {
            case 59512234:
               return;
            default:
               throw null;
         }
      } else {
         var0.L();
      }
   }

   private static List<g.b> w() {
      if (bn == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1bniri610e5ii","DVdhZx2NCXsTp0MNiYFoZ2RJ5MfhgCngJm/fdv+VfBw=",1782666730790752192,-1331744621357940244,-6430059857546915001,-4766032832084529323>()) {
            case 855737906:
               bn = d.a(
                  d.ap(),
                  var0 -> {
                     if (!d.I(var0)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3gmfk7eok7620","/t+tQGjK1IiyuFEbZy3fAIbtchOJ+/alcaVw9//8ASs=",-3653228486331633252,-8346742400657137503,-8669191592634525747,6855916590206360023>()) {
                           case 78625329:
                              switch ((int)com.yiyiaddon.m.b.a<"s3kx1ls542mxyp","XPQF5nkXUeAxr8lokn9PHMtwnudic6jAZ228qoNzAn0=",2541206482936222436,-6282768823930325504,-647846201840235961,6383847828270554679>()) {
                                 case -939442008:
                                    return true;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        switch ((int)com.yiyiaddon.m.b.a<"s10so2al2advmg","7sNAmcd5mw75Ld429MrIGN26qx2FMbIF/qbSEgjG8uM=",-6338099028798053817,-1982475073976543601,-6287447771479155026,-3400660153660336452>()) {
                           case -742061956:
                              return false;
                           default:
                              throw null;
                        }
                     }
                  }
               );
               switch ((int)com.yiyiaddon.m.b.a<"s2l4awamk5843m","vzk9Bs7PzuICuL7C9FASwN5M5JQZN9vYwQ2rL6s7++U=",-5721993185726113798,-4894156058127373843,-4059059357392784548,6034712229404394656>()) {
                  case -365391726:
                     return bn;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return bn;
      }
   }

   private static List<g.b> B() {
      if (ae == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s32xdvy67fp2gv","oVjPOri5YvSEqdUHw8jMD0MoCnLzWWl0wpflFtDfYtc=",-6619211190139370339,7235484284653682990,6507346266466480137,-394653977870865294>()) {
            case 1086999356:
               ArrayList var0 = new ArrayList();
               Iterator var1 = com.yiyiaddon.e.f.c.a.c.B().iterator();
               switch ((int)com.yiyiaddon.m.b.a<"s2jg0hnm22bwxk","L7S0+heHKMmlV71bH+mee7dfv7+SwrbTXLaO68gpKxc=",-3536634186299517826,-770431082102851223,3778883497233778379,3171976375832595742>()) {
                  case -2046985135:
                     while (var1.hasNext()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1xhuioay6wsfq","1OpklvcP6uCSpTL9sf4RsctZEkLLRggo/myuVaRQH9g=",8169386612499501598,4624946168880502938,-8050366596720048680,494248494029765712>()) {
                           case -62896529:
                              g.b var2 = (g.b)var1.next();
                              if (aC(var2.L())) {
                                 label29:
                                 switch ((int)com.yiyiaddon.m.b.a<"s258fs1xt4tr2k","1yQg6BeYZzDm6OEwy1zRxccJHPmhHoLaeN3EKRN6fi0=",-1284016462357006992,1223143901481105170,8850446137673663628,-7770329715452932778>()) {
                                    case 156615400:
                                       var0.add(var2);
                                       switch ((int)com.yiyiaddon.m.b.a<"sz2my3iwegr5","TsiGB6Q71kFIT2f2GXSm8pwIwcrQNmPsVn303Xm/Wjw=",2502604766094514834,7255011603555877840,-4286348619868867839,-266923207989250809>()) {
                                          case 671655484:
                                             break label29;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s22yamxse9lzyj","I2mrrWplYFzHRnVw3lBWJo4UZ6EaD2UZXhpM/DE26OE=",8544335606325588304,-7194772450520371345,6810881015845773278,5243212708825109090>()) {
                                 case 487266600:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     ae = List.copyOf(var0);
                     switch ((int)com.yiyiaddon.m.b.a<"sazyct71twzzh","bAA0OVlPI5gQ0Utvl4n+HkUD9IY2FYqmCrKhlDoXp3I=",-8433393296350977853,-3444249513352495742,-376842307975212300,-2032756697129088162>()) {
                        case -179051225:
                           return ae;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return ae;
      }
   }

   private static boolean aC(String var0) {
      Identifier var1 = Identifier.tryParse(var0);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2lt1016jjkclu","b4htb7OejyifqjUXoIdbORQchjm3MYaqNtYlD8ps5sU=",3300883387538220897,7780165298723384162,-5047237818207962640,-9044984714292019191>()) {
            case -57928205:
               return false;
            default:
               throw null;
         }
      } else {
         EntityType var2 = BuiltInRegistries.ENTITY_TYPE.getValue(var1);
         if (var2 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2znindimgmdug","KWlwZzWHyd7l3O/b/XGM/aabiUZDL37Dwh/TmiLEth8=",-1984114385300067070,-7787813907355394895,-2928420984057440550,1681308872256692758>()) {
               case 1289238814:
                  return false;
               default:
                  throw null;
            }
         } else if (var2 == EntityType.PLAYER) {
            switch ((int)com.yiyiaddon.m.b.a<"s3ott0bfz49eo8","Ix3Ac9MF+6dboB8a1au4rz7grvotZU4/8OsTABbUlGw=",-4083093248263514649,-1373115415296875137,8770804830430295793,-6283813012759883630>()) {
               case 1472922162:
                  return true;
               default:
                  throw null;
            }
         } else if (var2.getCategory() != MobCategory.MISC) {
            switch ((int)com.yiyiaddon.m.b.a<"s1txu3dofcy1c1","xghAZxJqLkvHLyItT51jx25qE8WhrHTE3FimAa7COPo=",-3303842815492614208,-8942724221256676777,-3572012475464056346,-5894480834198252450>()) {
               case 1433838257:
                  switch ((int)com.yiyiaddon.m.b.a<"s14jl0i2km80f1","s0lbw8JCTAA1wRuMcmQFTe/Lbvolu6ONhWCNwOmV4qg=",-2281496807692145070,-8301100643141077275,8787152098234265897,3767232629301012142>()) {
                     case 1251568905:
                        return true;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s1xyj6tsg6h3g1","pjN3GEPSm5VCqC3ak6No6920K1kWoFYgquiOXFwk5W8=",-7097279015336158509,-4987931714048271570,3494705842013893769,1184043572201375366>()) {
               case 1281200706:
                  return false;
               default:
                  throw null;
            }
         }
      }
   }
}
