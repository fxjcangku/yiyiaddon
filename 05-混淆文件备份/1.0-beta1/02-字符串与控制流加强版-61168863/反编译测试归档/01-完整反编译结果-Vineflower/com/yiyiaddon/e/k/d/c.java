package com.yiyiaddon.e.k.d;

import com.yiyiaddon.e.j.k.d;
import com.yiyiaddon.l.h.g;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public final class c {
   private static List<g.b> bn;

   private c() {
   }

   public static void a(Screen var0, com.yiyiaddon.e.k.a var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"syez28uh58hty","PB7FS11nhNxzaSL4Bcla+V2vC3Qosj9T9Bm9CrOIYro=",1025705553266613489,-4384706425161062285,892420639644970035,1831079868257245028>()) {
            case 1825447612:
               if (var1 != null) {
                  List var3 = var1.a().bm;
                  var2.setScreen(
                     new g(
                        (String)com.yiyiaddon.m.b.a<"s2hyguqvbfxzo6","i+vqrF+jVGuZOajC/HpbEdBG/oYWzRD7552jMOawXiRLrRW9tSJKmw==",7075693732860400669,7594514346946149096,-4161474075086965159,-1771716642006356021>(),
                        var0,
                        w(),
                        () -> new ArrayList<>(var3),
                        var2x -> a(var1, var3, var2x, true),
                        var2x -> a(var1, var3, var2x, false)
                     )
                  );
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2r720o4wz2t45","a+vdae7balcEPn+7DZm36kGcq7uztULOpI8W11fzhnA=",4140868928075292350,1191188403340252622,3184390292973008659,-4572746851021920327>()) {
                     case 1883209285:
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

   public static void a(com.yiyiaddon.e.k.a var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2gel5xzlbg4xb","7yHg+VE0uJquiqlKbFlVUOmWkChvIbH1NfDEheDkv/c=",-8651842861206849597,2621059138328157074,6073687955141338419,7492848223126870075>()) {
            case 1318579289:
               if (!var0.a().bm.isEmpty()) {
                  var0.a().bm.clear();
                  var0.L();
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s35ye7yjj2yail","d/B/duoK7yI16TOvKFigkw0GxBcagqBMn7HODIj5tOg=",-8864137074478461315,-4645989541445939514,130068902210153701,-6989500875825722601>()) {
                     case 226376520:
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

   public static String b(com.yiyiaddon.e.k.a var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2wxegrrseirn5","VkMGVoij/WLH6iaVzqscUdpJY6RNiNDcAZkt7ba0KK8=",-8824990754967012407,-5670728275433734890,-7954280420545937227,-5505739754292345907>()) {
            case 789758068:
               return (String)com.yiyiaddon.m.b.a<"s2qx9xa77db8jb","ZbllkkBvio2sVIDNtRn5Ikq+xa7zKPUuqy/uZA==",-377143289812223185,-5163781865936818419,-1790483738090311377,105844405933229150>();
            default:
               throw null;
         }
      } else {
         List var1 = var0.a().bm;
         int var2 = w().size();
         if (var1.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2vg7mgfhzp4wi","TC582Rsk6EO6gzRJGzqhPIVcVUzjNKwo0PgimmfJtoo=",2454113035044190002,982283111939630971,8737771928763252048,-6525854694645209608>()) {
               case 1310573583:
                  return var2 + "";
               default:
                  throw null;
            }
         } else {
            return "" + var1.size() + var2;
         }
      }
   }

   private static void a(com.yiyiaddon.e.k.a var0, List<String> var1, String var2, boolean var3) {
      boolean var10000;
      if (var3) {
         label35:
         switch ((int)com.yiyiaddon.m.b.a<"s1x8kypykgbas4","sSIMxM4RJtU92lpUa2/XbLpzScEVoEIcwqxXW5oCTEs=",-1877159021662773707,6304840351570005259,1562058455351876743,8180647228068364706>()) {
            case -1107658786:
               if (!var1.contains(var2)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3s8itfnzay7w1","HlQRq/LSbUoWo6R93akBDnFuLWok2hMjlB7P0oEuwCA=",-402076053939986115,-5292239414957443275,-1964881416295599876,-7627153472029430481>()) {
                     case -179617779:
                        if (var1.add(var2)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s11udyjr8ma3lb","sbiZIz8VcF1qgKeWsQ9ax9nv8ANpwdq0Xg270WUCnhY=",-948983307515995448,6791910523876251906,8345519064453191942,-8878676658847437802>()) {
                              case -1858726722:
                                 var10000 = true;
                                 switch ((int)com.yiyiaddon.m.b.a<"s2v0b3uof8eird","XNmweMVvlgayyNK6vRl5RBusFgY5KZuPTuKY9CHFoQw=",8678585344800947885,285603844611440312,8379919080073255154,-739485586599552363>()) {
                                    case 148285291:
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
               switch ((int)com.yiyiaddon.m.b.a<"s1gcoi6s55y2gt","MMtqxGeMzk27rY1uAeAC3KYBtcFwN83i1HRrwddINR0=",-7083582607624353534,1972227304689422627,-8890075772607697664,-834991052882213153>()) {
                  case 1212138752:
                     break label35;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var1.remove(var2);
         switch ((int)com.yiyiaddon.m.b.a<"s3l7g8dxd6stni","KlU38q6tzCPUmcZs8hl5dhQMFkz1U/VoLe9p1RbHCqQ=",402162149918855032,-585831638400412878,-5275756393683478048,-52433619204439877>()) {
            case -1322380092:
               break;
            default:
               throw null;
         }
      }

      boolean var4 = var10000;
      if (!var4) {
         switch ((int)com.yiyiaddon.m.b.a<"s2zekysqccnfs9","23Iwzjxsx+z6Za6kEP82IG1umPNPNti+rWlYrfvrWcw=",1449318615958615861,-1066303952909949975,-6848073633146413781,6058982725630234681>()) {
            case -1016022497:
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
         switch ((int)com.yiyiaddon.m.b.a<"s3qk2ygi3h567m","dBuJOC4DlSmkK411aErcvu3Bbr7oMN1uAp4k5k3RZgw=",-5381484446550654454,2450451797396946543,-2965806669149324864,-1476704453177982257>()) {
            case 1578201363:
               bn = d.a(
                  d.ap(),
                  var0 -> {
                     if (!d.I(var0)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s27pxuif8bu3zj","9LlaCFFwOFICcjDC5llRwSQlPj0wk06+6rYhpWwmM2k=",-9025652553487419226,-1162495434548645706,-5878131094086474113,-2862469769234889570>()) {
                           case 906868665:
                              switch ((int)com.yiyiaddon.m.b.a<"sd8cc2qh9xit5","x4IJrkRvTyPNXuOfM3qAeIzdCEUIZzZtV6kDFMs1HXU=",-4368759488450865262,2959498214513411445,-7730545891791658772,-1983862070083937222>()) {
                                 case -1198717220:
                                    return true;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        switch ((int)com.yiyiaddon.m.b.a<"s18r7e213b7lr6","2aiVHIBO9lJHpJfVt7fYlysJeOqkHHvh6lr6QbiG6P4=",-8586259728108324748,6803123925440754319,-2217510907935090910,2415219895591996008>()) {
                           case -1554052298:
                              return false;
                           default:
                              throw null;
                        }
                     }
                  }
               );
               switch ((int)com.yiyiaddon.m.b.a<"s31pcv13wfte5n","9g2RT3N1DrkbgewMWKKHZXsYDR21yK21nT4UgAWKtUE=",-3916056824772009526,-7257396835075552206,-3944574114651480415,-8360214363238075429>()) {
                  case -127759025:
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
}
