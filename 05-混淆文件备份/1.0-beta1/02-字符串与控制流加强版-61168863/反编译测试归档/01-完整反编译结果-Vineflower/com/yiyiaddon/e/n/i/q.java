package com.yiyiaddon.e.n.i;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.locale.Language;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;

public final class q {
   private static final int mB = 12000;
   public static final String sX = "customcrops";

   private q() {
   }

   public static List<q.b> aW() {
      ArrayList var0 = new ArrayList();
      ResourceManager var1 = Minecraft.getInstance().getResourceManager();
      if (var1 == null) {
         return var0;
      }

      LinkedHashMap var2 = new LinkedHashMap();

      List var3;
      try {
         var3 = var1.listPacks().toList();
      } catch (Exception var8) {
         return var0;
      }

      for (int var4 = var3.size() - 1; var4 >= 0 && var2.size() < 12000; var4--) {
         PackResources var5 = (PackResources)var3.get(var4);

         try {
            for (String var7 : var5.getNamespaces(PackType.CLIENT_RESOURCES)) {
               if ((String)com.yiyiaddon.m.b.a<"s2s1ugzm2qfqk0","/cS0k9guVGggWuortFHpteokUJHG+wLsPzWlpfLonMAQgZHRiuNgglQzE4TizE60XOQ=",691190665537868922,-7602590751728043469,-8099232144440044708,6353643991223960069>()
                  .equals(var7)) {
                  a(
                     var5,
                     var7,
                     (String)com.yiyiaddon.m.b.a<"s2kq26oijie0kc","aeb6PahqJcdXSL1c9mh/vInOgGQe9uK+qsGAoc9z12oU/psrOAM=",-1095944494546948358,-1866069796425920437,7668002561538364601,1287314890424987721>(),
                     q.a.ITEM_DEF,
                     var2
                  );
                  a(
                     var5,
                     var7,
                     (String)com.yiyiaddon.m.b.a<"s1uhtkxr87b1kb","KLhbdNmTpch5r2Hygxjp5JtMNmIo7GnImclaGzyFWstvucRVCde66lCQMDz0lhNH5OI=",-64172068286884910,5237688904043926036,3287510529342384700,5149206964564411709>(),
                     q.a.ITEM_MODEL,
                     var2
                  );
                  a(
                     var5,
                     var7,
                     (String)com.yiyiaddon.m.b.a<"sn27cjz2ccsx4","1a6Ril872HB4tuQXYSgj6Bmb8WXN0Kvue/m1gBbBGVAaJdQUsWZ+N+QAbld/AFN1Nz0WkA==",7693641011009122378,-4178418329215183168,-4526844459534189299,-1971463401895495247>(),
                     q.a.BLOCK_MODEL,
                     var2
                  );
               }
            }
         } catch (Exception var9) {
         }
      }

      var0.addAll(var2.values());
      return var0;
   }

   private static void a(PackResources var0, String var1, String var2, q.a var3, Map<String, q.b> var4) {
      if (var4.size() < 12000) {
         try {
            var0.listResources(
               PackType.CLIENT_RESOURCES,
               var1,
               var2,
               (var3x, var4x) -> {
                  if (var4.size() >= 12000) {
                     switch ((int)com.yiyiaddon.m.b.a<"s34auhdd5u2ncw","btfdO3opuDzqHEur+X3I7DOjGrqiuGLjLNuGQSAbkwQ=",2425562548496705530,3472408997267383461,-7319818812013255189,5863406630689359966>()) {
                        case -1739482798:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     q.b var5 = a(var3x, var2, var3);
                     if (var5 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s25foryi1lj11i","cjQTP5SlGn8oIbo1l23w7bKkD2YxR5/Bt8WEDAnsRiw=",-4300651642926705856,-9149190757848414181,-3776049031833459067,6876940013621663304>()) {
                           case 1212790614:
                              if (!var4.containsKey(var5.dP())) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s33kq5b8l3x8sg","I0xLb3Ha8usS8SaypBYlhzg0uEtWi9XzN1nOqVwoefM=",-6763998782700038976,3618362822991103206,-686584695623809493,-4820814447726167371>()) {
                                    case 1423520317:
                                       var4.put(var5.dP(), var5);
                                       switch ((int)com.yiyiaddon.m.b.a<"s1izbkyvd3ygvx","ZkAXDd87cPpQHt4r5Mwg457GL25ArUFMHIWUHZAaEio=",4356678841061274227,-8141076050520417686,-7939482895376640619,9073832724637635313>()) {
                                          case -911039028:
                                             return;
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
                  }
               }
            );
         } catch (Exception var6) {
         }
      }
   }

   private static q.b a(Identifier var0, String var1, q.a var2) {
      String var3 = var0.getPath();
      String var4 = var1 + "";
      if (var3.startsWith(var4)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2mc3jnpxsh1qc","YwwMMpBFQH3hvPxaSuVppudDduDGZZWvDzAmEJa1NPo=",9030593126502482838,-7400444472595632314,6105335735282729644,603861921921889776>()) {
            case 1309322159:
               if (var3.endsWith(
                  (String)com.yiyiaddon.m.b.a<"s1tjejrb3iptbn","lmExGt6hdWOzGmlGM7U5pCnKb6PEc2jyREm6u//4f+gg4UyVQZI=",-4829981852945449479,4311184307836121648,-6598199215917836231,6200965225510117237>()
               )) {
                  String var5 = var3.substring(
                     var4.length(),
                     var3.length()
                        - (String)com.yiyiaddon.m.b.a<"s1tjejrb3iptbn","lmExGt6hdWOzGmlGM7U5pCnKb6PEc2jyREm6u//4f+gg4UyVQZI=",-4829981852945449479,4311184307836121648,-6598199215917836231,6200965225510117237>()
                           .length()
                  );
                  if (!var5.isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1uztfw7fvxh5r","O/bZRkoATLH/ZjO/9HLuCxpbcu/QC0cNlijR0o71/UM=",2175420953385113453,-1320382456095898274,1468382532498312072,-1242563690277714952>()) {
                        case 558295403:
                           if (!var5.contains(
                              (String)com.yiyiaddon.m.b.a<"s26hvsect84qia","6sE+apx48O511AuusZ1nx1JmljnMtAo/5mIOjdhC/cY=",-7743037211891187522,7876297613912958664,3412260367485081773,-9104143150942595106>()
                           )) {
                              String var6 = var0.getNamespace();
                              String var7;
                              String var8;
                              if (var2 == q.a.ITEM_DEF) {
                                 label29:
                                 switch ((int)com.yiyiaddon.m.b.a<"s17g6vp04vmbat","gwEjhicucyhG/JvQEEFcvWYA83Arca0ESMmGc9raGDs=",-3210800886626173355,5102105424632686814,7307910801265356045,-4444863070934732802>()) {
                                    case 72915068:
                                       var7 = var6 + var5;
                                       var8 = var5;
                                       switch ((int)com.yiyiaddon.m.b.a<"snkbe7le79tjv","oTpAF46n3zLIChpwFoJkD+Fhxho77j5W/xDmyUZzTe8=",-5028120200178416494,-7064372698748414513,7352000665324812339,-1852379537025265418>()) {
                                          case 86609763:
                                             break label29;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 String var9 = var1.substring(
                                    (String)com.yiyiaddon.m.b.a<"s2ns6r9enq3bk7","VolV6Z/0oIGqrcA57G6DM0kWGqCVHYkazwzmw65Vgz3dGsmSWlOWQAdO",-8464031410652180679,7489434003263638402,589574703967481987,2942718640715092011>()
                                       .length()
                                 );
                                 var7 = var6 + var9 + var5;
                                 var8 = var5;
                                 switch ((int)com.yiyiaddon.m.b.a<"s14thyc0a7mu6b","NOUnOCwT09+Q1V5xsolRJ4jUn7S6QUqT5jtMqZ8BFfw=",6695828646572916581,516213377303056208,-6683476307189438321,-1444898373696427576>()) {
                                    case -1065435642:
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              String var12 = aD(var5);
                              String var10 = i(var6, var8);
                              String var11 = var3 + "";
                              return new q.b(var7, var8, var10, var12, var11, var2);
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s2puisxxbjvzhf","eDW/BVZYy2Vg1iaBGR/WPzDTtrPijIA7xZnCRP7d7P0=",-8726009030085412421,351022023900864194,2256311199524526883,8713460310617020898>()) {
                              case 1726669542:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return null;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s36t2leqof5ugr","s4/0S9ewVcMwEE2Qg5m3lU1NAkLyeY05FikXqthZ/iY=",316446316719523881,-1948444626561425432,-7132069987927753592,-6774356643410492713>()) {
                     case 134953718:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private static String i(String var0, String var1) {
      Language var2 = Language.getInstance();
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s9n71ijc7qevq","+pb0kNRZuRwM2ek6YUsc5iXq0FLln08Ib3Ilv6qJuVU=",-2461084600152041416,-3922595320163410761,6839582837422263409,7815294411847928990>()) {
            case -1716290172:
               return null;
            default:
               throw null;
         }
      } else {
         String var3 = var1.replace('/', '.');
         String var4 = var0 + var3;
         if (var2.has(var4)) {
            switch ((int)com.yiyiaddon.m.b.a<"s3tgto0wku3wg5","HxMxVayCjzrxWgcdO7muPTrO+wyKKf8G8r1UTCZw990=",2963980727026741180,-2896178748122810637,-5741656362617973800,8391512157385838652>()) {
               case -1551543630:
                  return aj(var2.getOrDefault(var4));
               default:
                  throw null;
            }
         } else {
            String var5 = var0 + var3;
            if (var2.has(var5)) {
               switch ((int)com.yiyiaddon.m.b.a<"s3p3u22llibnap","YgA0sSGIREpm/ohDWBOatZKyETd7BLdL7ZMXk1t/h1E=",4290772867979872070,-6879654095172515400,4288357247181398878,-5775690095921634723>()) {
                  case 197336252:
                     return aj(var2.getOrDefault(var5));
                  default:
                     throw null;
               }
            } else {
               return null;
            }
         }
      }
   }

   private static String aD(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s7vn27c4vj0rj","jHY4YKDBdPHqSZaYBuVTNLq9qjT2KtbhB2ZvYXl5bhM=",9161960419163699390,3546711864508260091,6899743227365013969,-3928012687838825562>()) {
            case 1502120263:
               return (String)com.yiyiaddon.m.b.a<"s3c76xqp9ciir1","ve5IMXM9H/y3sU2Vy9AkIfxnV9k79roe7ccFPw==",5136650365572463142,-4413928410410553901,4387328190867106617,3195139636076845536>();
            default:
               throw null;
         }
      } else {
         int var1 = var0.lastIndexOf(47);
         if (var1 >= 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s386ppws9u94fk","UQMPEfG8fTlhzeo49i7OdnHpLpndJblQIWSnz8IONkY=",-4615611472591432536,5017227652254232165,3614488648658650339,3115544931376729685>()) {
               case -540921320:
                  String var10000 = var0.substring(var1 + 1);
                  switch ((int)com.yiyiaddon.m.b.a<"st4qze2bp70aa","1QYOisoaJ5f6sPjmT9jfEYcS6W/ET48BJdmBml4rNFw=",-8401567716684916133,-2421025611025939664,6687038220735937944,3223272856496542642>()) {
                     case -710541630:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s31cmjvxirrk6d","ZiDbhfDOWCh6LONsK7BVBF/5M5qUIMQTbAR4neSuqGU=",88249898987488568,-4858251553237055091,726508757946625469,1098384177751930767>()) {
               case 1175926797:
                  return var0;
               default:
                  throw null;
            }
         }
      }
   }

   private static String aj(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ws8y1pciqra4","RpcqwBsOLz8kgk3Y6DWkhNrk386MtFcE5gve6bvMBWE=",2220598520609033662,-7061644338458528622,4938231363681327352,-3160596352106602748>()) {
            case -1201153404:
               return (String)com.yiyiaddon.m.b.a<"s3c76xqp9ciir1","ve5IMXM9H/y3sU2Vy9AkIfxnV9k79roe7ccFPw==",5136650365572463142,-4413928410410553901,4387328190867106617,3195139636076845536>();
            default:
               throw null;
         }
      } else {
         return var0.replaceAll(
               (String)com.yiyiaddon.m.b.a<"s3v1ck1jr6mtka","/t70PcS/z0bok5XFexYpYq8DQZoVrAXBUKotjRtu8rzjQiidgP1R2yu0moWLbfupJpiZQgxhHbpclaO53TkllBR62zmATg==",-7885452535238287908,7784078541394974642,79808050485377234,2631508752049982475>(),
               (String)com.yiyiaddon.m.b.a<"s3c76xqp9ciir1","ve5IMXM9H/y3sU2Vy9AkIfxnV9k79roe7ccFPw==",5136650365572463142,-4413928410410553901,4387328190867106617,3195139636076845536>()
            )
            .trim();
      }
   }

   public static boolean ag(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ht3lm85jrcy3","oZTXj6I+ER1ACptkhlkPyRCHM0Gl3HO5xg3sRG3huu0=",3321785004758038151,-2273932464430063886,8675370949350209411,3368299340527556994>()) {
            case -510927954:
               if (var0.toLowerCase(Locale.ROOT)
                  .startsWith(
                     (String)com.yiyiaddon.m.b.a<"s3amm59rxjeza5","TtcMeRRlDysTg5sDlD7YSEEppBkWIY/FapsbwyHTjRqdz14s9LVOhF1k/zLaUI97IzrbTw==",99786104214592328,-9157383623045480766,7282585935184577257,-1357886820389875689>()
                  )) {
                  switch ((int)com.yiyiaddon.m.b.a<"s19z695b22iniz","pW3E8DQdjAxAmhiyLaBhk5z9izm/ctWQsnLNlQLbIQQ=",6754660955082407389,-7999552254353706330,-9184567524881483680,-1314506997142745782>()) {
                     case 939229608:
                        switch ((int)com.yiyiaddon.m.b.a<"s355z522kc9bwy","NLccW9VqvxIcC6V6CQNYeNgT2FpFSxvPhWerHKZUX+E=",6602248851755578162,8442934044041657527,-6473915007040567807,-5668847948719011311>()) {
                           case 1169114866:
                              return true;
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

      switch ((int)com.yiyiaddon.m.b.a<"s29hg212cl1ju9","7jZDpmYemoGKP6caGQeftfpJ9ZM8a3t2nPe1vEUSG1o=",-5257026193233461455,2888028165119051780,-6734874057856565613,7231400705680632577>()) {
         case 1679490753:
            return false;
         default:
            throw null;
      }
   }

   public enum a {
      ITEM_DEF,
      ITEM_MODEL,
      BLOCK_MODEL;
   }

   public record b(String sY, String sZ, String ta, String tb, String tc, q.a a) {
      public boolean dk() {
         if (this.a == q.a.BLOCK_MODEL) {
            switch ((int)com.yiyiaddon.m.b.a<"s1zvka2crlqf9o","2f8naGo+vK0Q0jdhCbek+TRH5hG4D5gEjpagljZAtd0=",-3299864369769156674,-3255189118676780824,-1378623630512284671,-4660935357541704544>()) {
               case -2048499911:
                  switch ((int)com.yiyiaddon.m.b.a<"s2bziwtdlrruji","go35q+uZ9uCYwhkgl1E1NadFMLKkeSzDXM7UqpwVEKo=",6092169879888950659,2891150579895315927,8849162581617848439,8261515203012599399>()) {
                     case 1660036300:
                        return true;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s38qkftkglvieu","2xUy2rdUVQGsn2KhXaALcmQtjj950uUw704T0J5tDkM=",4578304693784750461,740753198443558746,-4344945429729788689,-1857661390597735355>()) {
               case 1829603915:
                  return false;
               default:
                  throw null;
            }
         }
      }

      public boolean dl() {
         if (this.a == q.a.ITEM_DEF) {
            switch ((int)com.yiyiaddon.m.b.a<"sgf8on4yscwv5","VSRbMVS3hsdxgo7RhzjYJkRmPyzbLbsq1XbPrXY1INU=",317018825746810495,6323614374603851085,6709244562604629083,8948439236711617486>()) {
               case 952118589:
                  switch ((int)com.yiyiaddon.m.b.a<"s2ycx3ohpd51u7","hg1Nl2sVQMPno8zfAojaMnp5sUCYHLgzx0bN/pe1dy0=",6161860387401888425,-6202907184470124821,7894937437071902934,-6107447222157811238>()) {
                     case -1713850466:
                        return true;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s26msecn2guach","q2L8omp8zplDSXYC6KNAl7TEYUCocxIOJ5GPqxk7R+A=",-1532686308044264764,-8155244664852397197,-662845174669661965,-8299173362563648817>()) {
               case -921418342:
                  return false;
               default:
                  throw null;
            }
         }
      }

      public String h() {
         if (this.ta != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s22osinkucirkf","ovw+I/yX6++golwPuUBnxLz9NknXN7lNc00PcPJfUng=",-4861346430255854189,-5148837897601889106,6225394111905501766,-2926818850292520389>()) {
               case 1245173907:
                  if (!this.ta.isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"sfmgkducbg8w6","8YAEkB8X3+40FkcmHkXkhrsDvhwyoJ9bKS0s3OCzqbg=",-1572506334632500972,9216332952602809485,-717165828560175054,2441186428063257436>()) {
                        case 1237751505:
                           String var1 = this.ta;
                           switch ((int)com.yiyiaddon.m.b.a<"slf2di45dca19","gn7207Jmc/EAVkbl083AMq7NWu8o9z9sfuFjHr1SZIg=",7306565386431657209,-2744475774785226833,5205609667275589103,-941843969548072228>()) {
                              case 781424112:
                                 return var1;
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

         String var10000 = this.tb;
         switch ((int)com.yiyiaddon.m.b.a<"s1um6p5mk460xc","B6IimWgCme+LR5kHy2d23qtghHnQCSS92fGPVgNZMp8=",1731632494257414320,8998564206012737382,7691383678195131467,-5254794235726659015>()) {
            case -796108807:
               return var10000;
            default:
               throw null;
         }
      }

      public String dP() {
         return this.sY;
      }

      public String dQ() {
         return this.sZ;
      }

      public String m() {
         return this.ta;
      }

      public String dR() {
         return this.tb;
      }

      public String dG() {
         return this.tc;
      }
   }
}
