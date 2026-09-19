package com.yiyiaddon.e.n.j;

import java.util.List;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public final class c {
   private static final com.yiyiaddon.e.n.j.c.c b = new com.yiyiaddon.e.n.j.c.c() {
      @Override
      public boolean U(String var1) {
         return false;
      }

      @Override
      public String aw(String var1) {
         return null;
      }

      @Override
      public String aA(String var1) {
         return null;
      }

      @Override
      public com.yiyiaddon.e.n.i.c a(String var1) {
         return null;
      }

      @Override
      public com.yiyiaddon.e.n.i.g a(String var1) {
         return com.yiyiaddon.e.n.i.g.UNKNOWN;
      }

      @Override
      public String aB(String var1) {
         return null;
      }

      @Override
      public List<String> d(String var1) {
         return List.of();
      }

      @Override
      public com.yiyiaddon.e.n.j.c.b a(String var1, String var2) {
         return null;
      }
   };
   private static volatile com.yiyiaddon.e.n.j.c.c a;

   private c() {
   }

   public static void a(com.yiyiaddon.e.n.j.c.c var0) {
      a = var0;
   }

   public static com.yiyiaddon.e.n.j.c.c b() {
      com.yiyiaddon.e.n.j.c.c var0 = a;
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2pt1m7ftmih7d","WmnIWvqkAj99Ic55h+oEIcFnsEk0HEvIqYg3dcXyFvw=",-8315375778827978049,-1283514808202933825,-3133027399620311673,7258790629966226984>()) {
            case 883838763:
               com.yiyiaddon.e.n.j.c.c var10000 = b;
               switch ((int)com.yiyiaddon.m.b.a<"suloh2qduqmcu","n5Lt5GjKAxQak978EiJeG/+fCcjPZVO4orogAb9+wew=",-2360232318247059559,-4731028661776590946,-7844929402953775186,-7084723175194660554>()) {
                  case 55725008:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s3bkyxnnyia5i1","bVgUlG2iV1ZTWz/p1fwvIxY/5U2EYYQb3ulm+c+mfKE=",6588992631757638781,4498101608581523521,-6657410875403523143,4186992856092421080>()) {
            case 771725246:
               return var0;
            default:
               throw null;
         }
      }
   }

   public static com.yiyiaddon.e.n.j.c.e a(BlockPos var0) {
      ClientLevel var1 = Minecraft.getInstance().level;
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s21e3apen43ejl","YNC5M7TuLHF/6MFR+4u+cwUV1oEg2WRkfmPniPp9Mfk=",-1978987981579552611,30202753400536192,-2450217139925934294,6726514822275311410>()) {
            case 1388601333:
               if (var0 != null) {
                  return a(var1.getBlockState(var0));
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3m4iynctlroex","eBo0wEfmNOP2ARwqO2X0n5+9iKK9WHKw8oUIZRwaauE=",2167810981166979295,2463061750858042621,-3549186018517538641,8590019311113422432>()) {
                     case -1339444686:
                        return com.yiyiaddon.e.n.j.c.e.c;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return com.yiyiaddon.e.n.j.c.e.c;
      }
   }

   public static com.yiyiaddon.e.n.j.c.e a(String var0) {
      return a(var0, null, var0, b());
   }

   public static com.yiyiaddon.e.n.j.c.e a(BlockState var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s37myupvuevosl","gOfW8EXqc8gv+56kDU5xO2ny1CZw71evnGZgZDD6AtI=",-2519773010076603551,-6706241798055552359,5369879935548318866,4466334526824906062>()) {
            case 849072986:
               if (!var0.isAir()) {
                  com.yiyiaddon.g.d.a var1 = com.yiyiaddon.i.e.a.b(var0);
                  return a(var1.dv(), var1.a(), var1.ea(), b());
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1lwsap7dkfknx","Fmh3vex1dYFkujhMfwFWn7s4ZkMYIztfK6PikCSIIUA=",-4059221242772381889,-6622339470599049733,-4925447589756087965,3609826160070144834>()) {
                     case -1583890347:
                        return com.yiyiaddon.e.n.j.c.e.c;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return com.yiyiaddon.e.n.j.c.e.c;
      }
   }

   public static com.yiyiaddon.e.n.j.c.d a() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s39fymd0f3gs54","ZfClTqRy5JC/Uexa4erORw8ichpHuoNsrAsGsImxOLM=",5037027479290461398,2831342520943744065,-6328262909764746506,-4259223789037261789>()) {
            case -638873485:
               if (var0.player != null) {
                  HitResult var1 = var0.hitResult;
                  if (var1 instanceof BlockHitResult) {
                     switch ((int)com.yiyiaddon.m.b.a<"s17n7oejpj2nfo","rh2IpmMLAF/NCQcHyRYidhdiVEaQ90Q1AIXzy5+MTlQ=",-241117352226199216,-1049739042812299881,-3410446977357226136,5655875675978670410>()) {
                        case -2007527106:
                           BlockHitResult var2 = (BlockHitResult)var1;
                           switch ((int)com.yiyiaddon.m.b.a<"s1t8y1kzuom94b","epizRjWpoW2VEj0rNQb6CPHajwN+491oHbhYy4m2Yz0=",8588415015505857800,-1043594975528466643,602213869122618355,-4136420163707101302>()) {
                              case -1135881047:
                                 BlockPos var3 = var2.getBlockPos();
                                 return new com.yiyiaddon.e.n.j.c.d(true, var3, a(var0.level.getBlockState(var3)));
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return new com.yiyiaddon.e.n.j.c.d(false, null, com.yiyiaddon.e.n.j.c.e.c);
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1vfntuz8lvjqg","DV8XD2Wtc88l0WFs23zL87WneWP3P+RmHE67D+5oV5M=",-8979976604617615212,-1610994080934052163,-6306628036214191352,-5636347114266936652>()) {
                     case -534750819:
                        return new com.yiyiaddon.e.n.j.c.d(false, null, com.yiyiaddon.e.n.j.c.e.c);
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return new com.yiyiaddon.e.n.j.c.d(false, null, com.yiyiaddon.e.n.j.c.e.c);
      }
   }

   public static com.yiyiaddon.e.n.j.c.e a(String var0, String var1, String var2, com.yiyiaddon.e.n.j.c.c var3) {
      com.yiyiaddon.e.n.j.c.c var10000;
      if (var3 == null) {
         label83:
         switch ((int)com.yiyiaddon.m.b.a<"s36cz1msck8hi3","p9h3xdpOPDJvvPRrpK8VAZllY6zbndCfWzGKk1WynZw=",-2097231132162003317,-8955478517674037520,-5872794773422097108,-6555731624910218699>()) {
            case -335880028:
               var10000 = b;
               switch ((int)com.yiyiaddon.m.b.a<"sxips9xiky6ku","dLG0a5K4j3y5TlsSIirGJSxSUW+EYZodT6UwDSvvyLU=",8584734164912224080,-8096514978130657608,-5441313119110431689,6781804804827018684>()) {
                  case 909555371:
                     break label83;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var3;
         switch ((int)com.yiyiaddon.m.b.a<"s22nlli4tlh8we","qZ74HfgqAlpJct4MLdg85qIOATj9Z/ODNzkx2TaWPVc=",-8284950184910201447,-6659576697050072664,5835530650052402732,-159738448502224423>()) {
            case 351826281:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.e.n.j.c.c var4 = var10000;
      String var5 = ad(var0);
      if (var5.isBlank()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3tpktu4xezza2","OI41x7QTMrcfF6tz+xEj2aVr4yxWrbmLH+LjzQ9gkhQ=",3064809626132324389,2814299083418932518,-4623808716529445494,8575348353590990447>()) {
            case -341990860:
               return new com.yiyiaddon.e.n.j.c.e(com.yiyiaddon.e.n.j.c.f.UNKNOWN, null, null, null, null, null, null, var0, var1, var2);
            default:
               throw null;
         }
      } else {
         String var6 = var5.toLowerCase(Locale.ROOT);
         String var7 = aQ(var6);
         if (var6.contains(
            (String)com.yiyiaddon.m.b.a<"s2aqtrb2fvw1x0","4Sp3w1MVluKZVZCSRDjPICYyZC1qFNNWMIPgP6nbvHfouVo1",-4707842753137247837,2192398870191625733,-8767122808490706808,3387001106074672839>()
         )) {
            switch ((int)com.yiyiaddon.m.b.a<"s23yn5fhvr54e2","quyOrf/8Rj//HF7X05l9i5RtbL0qucdlxCqlX2jDiLY=",8463046553802562823,5539209162394759430,-3968943144215133817,-798385383402014931>()) {
               case -1484163270:
                  String var11 = a(var6, var7, var4);
                  return a(com.yiyiaddon.e.n.j.c.f.DEAD, var4, var11, var7, var0, var1, var2);
               default:
                  throw null;
            }
         } else {
            String var8 = aP(var6);
            if (var8 != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s1zd1ddl1ai4uq","+zagNOeIL3fKSKAxLAg1QIj2iOThvIv9Sz3kNEb3Qgs=",-3117691829582007060,5846386091593742153,-6370035967640886700,8350256555183021699>()) {
                  case 657510288:
                     if (var7 != null) {
                        if (ai(var7)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1abewoqj1ztqo","6daD65gvv1PU9j/zxaJuGpAj7F8JIt5gm9c9cQZQ8NE=",6734106548898986923,2340295355119538755,5684799166691020115,7203047323658233016>()) {
                              case -981926256:
                                 return a(com.yiyiaddon.e.n.j.c.f.SPECIAL, var4, var8, var7, var0, var1, var2);
                              default:
                                 throw null;
                           }
                        } else {
                           String var9 = var4.aA(var8);
                           if (var9 != null) {
                              label73:
                              switch ((int)com.yiyiaddon.m.b.a<"sv4xa4z1eq7op","sNvam0Of7j6s7WqylAK7G6crBdegQqcCfUfYIf2QUPA=",4356196084443381099,2334222191607992471,7104583954074326591,-8584236528550922416>()) {
                                 case 1260467932:
                                    if (var9.equalsIgnoreCase(var7)) {
                                       return a(com.yiyiaddon.e.n.j.c.f.MATURE, var4, var8, var7, var0, var1, var2);
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s1l770vox34mz4","OPJWHos3oBuK5sQjrZS5wZuTBIWiPzdG84LBl6Ks42k=",-5168602321888043291,-1144493253426529739,6823064675477808642,7765661093462433700>()) {
                                       case 2114740549:
                                          if (var9.equalsIgnoreCase(var5)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3ilxqe0xlkv3c","88/8Fb6QauaMZ697jgCaNbTd3zEZkZGdbf7iMD9ugWc=",-814221951034856331,-2663400372985070515,312343276159597918,-517850553981359460>()) {
                                                case -1459314047:
                                                   return a(com.yiyiaddon.e.n.j.c.f.MATURE, var4, var8, var7, var0, var1, var2);
                                                default:
                                                   throw null;
                                             }
                                          }
                                          break label73;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           String var10 = var4.aB(var8);
                           if (var4.a(var8) == com.yiyiaddon.e.n.i.g.REGROW) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3i4s5qo3rcm6f","M0tzNCCapw4K6N7B6fP55Fq5ELT2Ebe11xOEN1sfZRU=",5961916071391631595,-6181296179406232953,8517696164861949349,3382987911171941059>()) {
                                 case 1334091244:
                                    if (var10 != null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s351rlwi7xg5r9","cKTCd6BRFvcQdYdy7vVftvT0q6Af9DJnvmOUs1nqWc0=",-8994945549626424931,-5189798608240773290,599247432852786176,-5577082171187833030>()) {
                                          case 193977965:
                                             if (var10.equalsIgnoreCase(var7)) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s3tgaj7ftyr3sl","eoXeaj5gbvxzcRi8oeLFowsiv9WL/Y9DdkWDMQpcTJE=",8338964523439440947,-6044721501701294783,5866347151837688471,-1937295388098401154>()) {
                                                   case 1732170319:
                                                      return a(com.yiyiaddon.e.n.j.c.f.REGROWING, var4, var8, var7, var0, var1, var2);
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

                           if (var9 != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3upuilqyt32cb","aCuVtI7BUpi3xKeHl8/1t27LsAK7XawFlVbcyNf49WQ=",2825327280250913840,-7934782798571563141,-5697321576228781469,5305135693593629062>()) {
                                 case -1766256974:
                                    return a(com.yiyiaddon.e.n.j.c.f.GROWING, var4, var8, var7, var0, var1, var2);
                                 default:
                                    throw null;
                              }
                           }

                           return a(com.yiyiaddon.e.n.j.c.f.UNKNOWN, var4, var8, var7, var0, var1, var2);
                        }
                     } else {
                        switch ((int)com.yiyiaddon.m.b.a<"s1gp1pk33shweg","HAZW+/p3iYou7hFW3OTIQUjQBo9Xa4OvvBMHuW2G02s=",-1745689600813136563,-2146928333123388345,8527915599661454014,-7714089976484888757>()) {
                           case -907959659:
                              return new com.yiyiaddon.e.n.j.c.e(com.yiyiaddon.e.n.j.c.f.UNKNOWN, null, null, null, null, null, null, var0, var1, var2);
                           default:
                              throw null;
                        }
                     }
                  default:
                     throw null;
               }
            } else {
               return new com.yiyiaddon.e.n.j.c.e(com.yiyiaddon.e.n.j.c.f.UNKNOWN, null, null, null, null, null, null, var0, var1, var2);
            }
         }
      }
   }

   private static com.yiyiaddon.e.n.j.c.e a(
      com.yiyiaddon.e.n.j.c.f var0, com.yiyiaddon.e.n.j.c.c var1, String var2, String var3, String var4, String var5, String var6
   ) {
      if (var2 == null) {
         return new com.yiyiaddon.e.n.j.c.e(var0, null, null, var3, null, null, null, var4, var5, var6);
      }

      String var7 = var1.aA(var2);
      return new com.yiyiaddon.e.n.j.c.e(var0, var2, var1.aw(var2), var3, var7, var7 == null ? null : var1.a(var2), var1.a(var2), var4, var5, var6);
   }

   public static String ad(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sbybehyirrvlr","RRiflRHNjCSLyIMgKgqXHuJxIAbmCCRXsvj3aJg7olo=",7589519460992735881,-8085041246045898925,7391888845485029917,3253041744009679000>()) {
            case 1143321668:
               if (!var0.isBlank()) {
                  int var1 = var0.indexOf(58);
                  if (var1 >= 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s11cknmjn6ra9j","cr/6m/wSNoKmSaXCQSsPMp1O1sCbrD0NqW87ah05Fcw=",-8958076841562194197,-3098734695653503329,-2998591296541216404,5094707885574893479>()) {
                        case 427938069:
                           String var10000 = var0.substring(var1 + 1);
                           switch ((int)com.yiyiaddon.m.b.a<"s3fh1e521ro7hn","wzpBMRcoeYQaJJ1A6oPiIOYiil1jroW9NOCldhNMIgc=",-1902498805202106325,-3195987883182113493,7210567954754822508,-4997035405779429414>()) {
                              case 1456637940:
                                 return var10000;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s21wv1xt03kihy","UdS4CzzWRT5kJ1lPab+Vcn5UPIWUNMgoGTWtdoIRVX4=",-7374405884089479116,-8457505655704622562,-3012761620863895346,3631845942359604335>()) {
                        case 179462765:
                           return var0;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3oii62md02670","bKQNqmB54uDpiyUUTFwg0Em11GHTupDrNB5fRkWzEiw=",8103982664186533568,-5008802773208217844,8158686998541543089,-7043496143090344216>()) {
                     case 1178581064:
                        return (String)com.yiyiaddon.m.b.a<"s2bl88l9ewx479","WLJKxdF8NP8PBLaY9iZOg44iaBg6/DdR3Y2A5g==",5550724928382773327,6072255929760257681,8144676598647286866,-5363666374932303030>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s2bl88l9ewx479","WLJKxdF8NP8PBLaY9iZOg44iaBg6/DdR3Y2A5g==",5550724928382773327,6072255929760257681,8144676598647286866,-5363666374932303030>();
      }
   }

   public static String aP(String var0) {
      int var1 = var0.indexOf(
         (String)com.yiyiaddon.m.b.a<"s127gbx3xed1be","sVIh/8X66L7qVtEoGMD8/jKMryLn9+iH/E7dEj6AgrdwS4N4Q4fFG0lS",5587138746198767987,7634566502498292006,200687830618140790,1572220240345952392>()
      );
      if (var1 > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s2vlxflae558gm","dm7ZM0DSEhm+1oKC/3dErQYDzGlfDDAA7oL5gDrGJnQ=",6711748635957238982,-5153552945742998,3961092291607283101,7839262415135605493>()) {
            case -1901843618:
               String var10000 = var0.substring(0, var1);
               switch ((int)com.yiyiaddon.m.b.a<"s774nnbdpgku1","3mdj7GHWm4xLc4ZdMtN6BJ9GZHNu1wc87HmS72kpJis=",5230025725722366306,-3847963812619674549,-274969602917750265,9177564253928319420>()) {
                  case 1379448470:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s34gdq9b5c7glo","CxUarAcWTbaTO+joYywSFEqOPENLBggOeBJVv1omqDo=",-8517255077901162269,4820788317651521596,-1880949783140513767,-5097160013983287439>()) {
            case -282756075:
               return null;
            default:
               throw null;
         }
      }
   }

   public static String aQ(String var0) {
      int var1 = var0.indexOf(
         (String)com.yiyiaddon.m.b.a<"s127gbx3xed1be","sVIh/8X66L7qVtEoGMD8/jKMryLn9+iH/E7dEj6AgrdwS4N4Q4fFG0lS",5587138746198767987,7634566502498292006,200687830618140790,1572220240345952392>()
      );
      if (var1 >= 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s15mwsmexyhx5","isuIIy8y2yvbUeMEAQ5GnuUcvWvTInvVBlqMFmpQcMU=",-5897875344182143017,8391566239203522104,-3202556555563386753,2870680965682134364>()) {
            case -318549031:
               String var10000 = var0.substring(var1 + 1);
               switch ((int)com.yiyiaddon.m.b.a<"s295wiobjhotfo","vWyS+DkguVvR7cLEEGfBDQKIkjAgDG6Iuzt37mbYnew=",-394814671437785407,-2391564395894556267,8748117689270295108,-4897199661669098263>()) {
                  case -2014181342:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1xmqrs0r93yow","DnkGmQ0bAPmPUCFiQL1PhSWEZyFHnFnBMewKwldOEIo=",5047970044418005447,-3944785918129065808,-4727213694885584809,-7505770095421352070>()) {
            case 516887792:
               return null;
            default:
               throw null;
         }
      }
   }

   public static boolean ai(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s24qr4twe7ol0","MDpOQYaFXOYVQmrgG/u1dqjolwsxi/kwDraczYUHwKQ=",-3404986809323328880,-3951074931703963704,-1359350405976028740,683885248635516943>()) {
            case 37226542:
               if (!var0.isBlank()) {
                  String var1 = var0.toLowerCase(Locale.ROOT);
                  if (!var1.contains(
                     (String)com.yiyiaddon.m.b.a<"s1swacxqths8eu","ra965jP96AZXjuatBi7nOYSKoY9vs6Ypv/8BQubJWj/Fh4Bgnc6s/g==",4223388667642156769,8838744565268540557,-218482928054250130,4434461674267072488>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"sapcj3t6yxodp","i8aXUjgsDsejuV5mZ23QWkxigGHwshzH9eFi9ei2bUw=",5572697021003836516,-7719696109217270666,3223556738676351122,6699831791003647942>()) {
                        case 756663262:
                           if (!var1.contains(
                              (String)com.yiyiaddon.m.b.a<"s3uzvsa1kof84u","HDsEWPCtvvlJdP+8EaWK5tR8x0OYy+Et+XCq9QJ3nA+ZSzrOK0E=",-8104767986517337156,5789006700860736421,13269568137859419,3055391444552796390>()
                           )) {
                              switch ((int)com.yiyiaddon.m.b.a<"s12he0oqpv23aw","lHdj221Ka90mbypB8Nd/XQdeTBJ8wHPX3VQyNz52rro=",243239837492845043,-3056094274159490989,6441512882036048641,6395813042728697427>()) {
                                 case 983159389:
                                    if (!var1.contains(
                                       (String)com.yiyiaddon.m.b.a<"s1zsi9p5vmg9ri","ysaRSeiq3oVISa9RgW2LpiU+vwr6eRVKEXssLOsJM8T0ncOW35DUJa+pMmk=",6210578097908933054,2154479922524126917,5145089957864671512,-894852470658209599>()
                                    )) {
                                       label35:
                                       switch ((int)com.yiyiaddon.m.b.a<"sdz21mhzccg0c","UPL8rndTQ53mOqTpu7P6tCUFVSO/bNt0lx6mzcXMKgE=",-7171202266907122254,83490282572164141,7250275661698552428,-2849686848261551072>()) {
                                          case 2002765986:
                                             if (!var1.contains(
                                                (String)com.yiyiaddon.m.b.a<"s1v18djmva7oe7","7Mh4LLYhGADrgK+ZHHaLVOmueHkDDPwl8idboc5xR/kh6iQQ3to/9BFTyLU43A==",-7150645218325614817,7482157881750516689,6445923627108235658,4564151039890910248>()
                                             )) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s3avcni7qy4e05","y5JF/m99/C2L/s8bBLklz9TtiOYWAfPVlr2Z1ggcwYk=",4233234423952993046,2510213063181941168,-3077322676275944248,-7776344305722571774>()) {
                                                   case -851178984:
                                                      return false;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s1s5pl4h2oqh3f","9GQ/eOjy8EPdN9bsRho0vwan5/ZOYJtDL+xognzpDQ8=",-47824719824894709,-8293672061590906729,-8821288707592127630,-3085747282672413253>()) {
                                                case -2664187:
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
                           break;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"sb1k6lracz2dt","HRfw41493pfz1GI9yuYslnp+LV1/3pGc7GU8nziHuDE=",8071075344089228600,5664887310558311461,3506386739846473461,-4568070065637588120>()) {
                     case -1294640390:
                        return true;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1h586d05tvha4","eguUygKtOl+zbpRevR+RPM0CKZLai/XoRbLJdsxZjgg=",-4169290760666985341,5358781984322756639,-2097750763098805260,3273527757965858047>()) {
                     case 813533827:
                        return false;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return false;
      }
   }

   private static String a(String var0, String var1, com.yiyiaddon.e.n.j.c.c var2) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s377dl89ps7bf2","94CGuEpPoJKZ3HpuKWlXbR57kVt+zLZcnx5e1iy59x0=",4446059295584006241,-5818745427323860218,-7024510151360983240,3322480840478604299>()) {
            case -1528715552:
               return aP(var0);
            default:
               throw null;
         }
      } else if (var2.U(var0)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1mnv5zbmmzya5","zsRBtiEghfd0mtfNYHVwP+kPpSb1Y3Bad5W9ot7BJo0=",6537170270383344144,4622672726191078739,5260680686947748958,994097714998727969>()) {
            case -18881799:
               return var0;
            default:
               throw null;
         }
      } else {
         String var3 = var0.replace(
               (String)com.yiyiaddon.m.b.a<"s1ekppiuyxet6v","4rgEN8LxH+b0vtvz42e3RlKM3AwJ1ACammt+W2q641GeE9upHyk=",6182893296967592646,1130148043494602664,7675083205403723881,-3604357476903863854>(),
               (String)com.yiyiaddon.m.b.a<"s2bl88l9ewx479","WLJKxdF8NP8PBLaY9iZOg44iaBg6/DdR3Y2A5g==",5550724928382773327,6072255929760257681,8144676598647286866,-5363666374932303030>()
            )
            .replace(
               (String)com.yiyiaddon.m.b.a<"s39d7wr6ynw70g","aOftucpk6yFD4dPmbEuawyshUuZ+CsF9vszh0kNyKytBKE1qvOE=",-7226546468098299136,-4839990143074203343,-1655541084060489724,4237975795305012872>(),
               (String)com.yiyiaddon.m.b.a<"s2bl88l9ewx479","WLJKxdF8NP8PBLaY9iZOg44iaBg6/DdR3Y2A5g==",5550724928382773327,6072255929760257681,8144676598647286866,-5363666374932303030>()
            );
         if (!var3.isBlank()) {
            switch ((int)com.yiyiaddon.m.b.a<"s36zjwkpt1nvre","b+1VksgRtnaed2PfkImJAyJ4Y+HfYmuPByQlEKwgyG8=",-9173482310161317044,-4047977487261345707,4783780150321065331,6635444721917400269>()) {
               case 1435506351:
                  if (!var3.equals(var0)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s22wrgvheos63n","XfB10xfHdaixM0LH5UkIo+wr5Fim0pDQ5EiZoGC2d1A=",1785744689087682877,4730627826159402330,-5420357073631639082,-3475528203323777071>()) {
                        case 482037437:
                           if (var2.U(var3)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s35xdxm81atk3r","82QM58vPKLqDfpiEubGKB8ebuOxZrmEr6ny+S8/X7M0=",8431655845227689940,-5865014955543333332,-4818839789227187183,-9041965675341475734>()) {
                                 case 1876170309:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3ajsllvh2t7cd","WHlYx5xN5coJ5GK8imSeC8YqSiAQnb/7wegUp0si2uE=",-2720145893664041281,7429563530660012194,-8879499441473237453,-1491668518202638770>()) {
                                       case -370740956:
                                          return var3;
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

         switch ((int)com.yiyiaddon.m.b.a<"s1iwe3asfzpt3h","hbjyPVOGVlmUVdqpWB9sOU0Q4NtLMqSJcSCsFI4EvAg=",-4032830138694925112,-2100594499321266252,8117958444314096730,4095241492724344398>()) {
            case -142133952:
               return null;
            default:
               throw null;
         }
      }
   }

   public static com.yiyiaddon.e.n.j.c.b a(String var0, String var1) {
      return b().a(var0, var1);
   }

   public enum a {
      SEED(
         (String)com.yiyiaddon.m.b.a<"s3c72ddnpl77bu","BcL1n0EWYb4holG9XIhOhoAwoclhGgzvpCcr4SrbaNw=",1219907024714609104,7261530977031564902,3333417261953189740,3551713352738144732>()
      ),
      PRODUCE(
         (String)com.yiyiaddon.m.b.a<"s3ok68p2uvr1kw","KuY0Ek+krnzl7a42Eh2r5WpqBzHnM7vZzhKzC3V9lluRCuwc",3072116244942566290,6465774761218996916,3905987202785690386,4400447455112017891>()
      ),
      VARIANT(
         (String)com.yiyiaddon.m.b.a<"s3k2hm4qr6vuuq","9unhIp51LgfSGXwYwJ+bWGxm9AHHHXiYEDuCkZNt4deCn6A/",1069768539519107850,-8100664743008665241,-7546918546956634,-2625769818330047443>()
      );

      private final String to;

      a(String var3) {
         this.to = var3;
      }

      public String m() {
         return this.to;
      }
   }

   public record b(String tp, String tq, com.yiyiaddon.e.n.j.c.a a) {
      public String dk() {
         return this.tp;
      }

      public String dV() {
         return this.tq;
      }
   }

   public interface c {
      boolean U(String var1);

      String aw(String var1);

      String aA(String var1);

      com.yiyiaddon.e.n.i.c a(String var1);

      com.yiyiaddon.e.n.i.g a(String var1);

      String aB(String var1);

      List<String> d(String var1);

      com.yiyiaddon.e.n.j.c.b a(String var1, String var2);
   }

   public record d(boolean dJ, BlockPos F, com.yiyiaddon.e.n.j.c.e b) {
      public boolean dn() {
         return this.dJ;
      }

      public BlockPos a() {
         return this.F;
      }
   }

   public record e(
      com.yiyiaddon.e.n.j.c.f a, String tr, String ts, String tt, String tu, com.yiyiaddon.e.n.i.c i, com.yiyiaddon.e.n.i.g c, String tv, String tw, String tx
   ) {
      public static final com.yiyiaddon.e.n.j.c.e c = new com.yiyiaddon.e.n.j.c.e(
         com.yiyiaddon.e.n.j.c.f.UNKNOWN, null, null, null, null, null, null, null, null, null
      );

      public boolean do() {
         if (this.tr != null) {
            switch ((int)com.yiyiaddon.m.b.a<"szhe7p8tsq0w2","WD8n7kV/+9LqIo3CwyE8j8fqX1hLZZE+d7Aaf3D31OI=",-7167130521152183433,2005350651919680493,8578835190261136064,866645710847842023>()) {
               case -640905727:
                  switch ((int)com.yiyiaddon.m.b.a<"s2gzsyr5s7q790","/fiHnlW4yThLIj/zFOJGF4CRVUtTgBIOllKsbNE5Txc=",-4142292024055565511,7728649514896284042,2851042309889355607,-4039215352811732978>()) {
                     case -770821263:
                        return true;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s3smuxfl6jqb6s","GrAoUm+o+bJRNb47zuJwjJF41y+wgcENc7G1Um9cd9U=",-6543012925607296617,-6813543492887312843,-8146201209672162940,-423855628749013654>()) {
               case 1954969905:
                  return false;
               default:
                  throw null;
            }
         }
      }

      public boolean dp() {
         if (this.tr == null) {
            label22:
            switch ((int)com.yiyiaddon.m.b.a<"s3vb68oemw0jbj","1YyI8bVgAM526l3LiTcKl8TvDpD0+Mb9eF1MdQXf8UY=",3855899387867969968,8802092704171391614,1589730176436066107,-250755191452716598>()) {
               case 221948108:
                  if (this.a != com.yiyiaddon.e.n.j.c.f.DEAD) {
                     switch ((int)com.yiyiaddon.m.b.a<"swneo164jxek8","xDgtnt4G/ZfC/dEXvTEXWSAwr8Qy/Z/mB6I7ZZwDtaU=",-1446571796777455678,3119906277212993877,-3290128788844690883,-3934335525476491358>()) {
                        case 899126361:
                           return false;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s8n03w93dmruh","e8TAKYGbDnSHl1FhKIHEyE6gHgNt7n6jmNRIvz9YD0I=",8559295966459336084,6074474692470110446,5368292350541550861,8728520058640104131>()) {
                     case -845223190:
                        break label22;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"s2spdyje46dnm8","wAItha4zgLbldkCgRgE05ZSkS3Fl8ipcVj04wv9DTO0=",-8284571301139438483,7396738977052427157,3364167115451716974,-3004592112581317141>()) {
            case -1162321092:
               return true;
            default:
               throw null;
         }
      }

      public boolean dq() {
         if (this.tt != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3n1pavodmylkr","kgAMcwoIS9seoR0OSngU70lQtu8SxIftmjNs6/z+/hw=",1438280943610084881,6546219828955179325,5941948741458025340,-6627935717458547454>()) {
               case 684704958:
                  if (!this.tt.isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"suq0xzbjka0i1","39QrzMpIc9F8UM3w01GBLqKM5eoLsJCF/qw8tXPtESs=",4006338827498369239,4710516723456451723,-4688992787990514009,2439349334774387306>()) {
                        case -1110018450:
                           switch ((int)com.yiyiaddon.m.b.a<"s3sgotveojy67v","6ru9aEfLXnikG89Rjiqh70M8jQggtnOE10NcZzaA3J0=",6517601011019062720,-1142461523248653715,-2534091701011379403,3860421225034608244>()) {
                              case -553559358:
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

         switch ((int)com.yiyiaddon.m.b.a<"s2b1qrgqy8hst1","K0ANeOmrk3RBLGImWSZWPMtCKeV56mK7aLJ94YQU3ZI=",-5089886770074933319,-4428731201458071675,-8687804363932688094,-5766650242060566640>()) {
            case 1465748386:
               return false;
            default:
               throw null;
         }
      }

      public boolean dr() {
         if (this.tu != null) {
            switch ((int)com.yiyiaddon.m.b.a<"slzg5d10622lo","mg5vBy6k5CitqRyqfoAWUHSdS/mq+wS3agb8Rh87Uwo=",-3636873449853509167,3609219472835185208,5160934066363183485,3932822574112534231>()) {
               case -658442788:
                  if (!this.tu.isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1zwlmke73nhzs","HCrUMdUzpPStzBj+mT6jCH36iUrH2kT7LGn3Di0d07U=",-7870479898276547508,7078448364395599619,-1814414553356172845,-7823582284048329807>()) {
                        case 1437154656:
                           switch ((int)com.yiyiaddon.m.b.a<"s2ul39awsp3k69","1KJ5VSn56C0oxvxlkehcp/kuO1OWX+1Z7lOdv4XLLc0=",-7068886409030849346,-1926441794388844932,5665999200410821831,5316215732082046119>()) {
                              case 1927515705:
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

         switch ((int)com.yiyiaddon.m.b.a<"s2v1wsfwnc98nf","OuZTDGlj5ejw3UX+OIwoxscBmnbS7uPlz+yYBtHzvAE=",-3933731006616951491,3415711090344711181,-674171833730555421,8266370716259933857>()) {
            case 1640317122:
               return false;
            default:
               throw null;
         }
      }

      public String dW() {
         if (this.i == null) {
            switch ((int)com.yiyiaddon.m.b.a<"sfnv22vtf4qlj","NdnM9H+OLg+2/O0kXz6phNk4tEquEymEVzuK7hHoUp4=",-7154360989025809477,-3843086339895197645,-7189209090194912460,-7595622295303792800>()) {
               case 1462606900:
                  String var10000 = (String)com.yiyiaddon.m.b.a<"sfajbcboemf5e","ecWhqrl/iq6lt328rVOjL1/I9FTTnJynF8rRZi6t2iQ=",-1966028290253798826,554936326665216765,2129953517095775797,887658673828431490>();
                  switch ((int)com.yiyiaddon.m.b.a<"s3dauvu99hyep9","iRyYLiYQy02kgkVmtxi7wQWTuT5o+ulC2LlcQRzDN6E=",7074139720916151385,-2606634398573402483,-7656051555106572972,-4947313356252314254>()) {
                     case -76111335:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var1 = this.i.m() + this.i.name();
            switch ((int)com.yiyiaddon.m.b.a<"s3isrt3lxe6f1y","hQtf54HFiC0kpWo6UiMt6J+NXP7n7ltvqIcEtchDzJg=",5449542676694277305,2525342657151350474,-3807537169537630347,-19066162987407790>()) {
               case 738957333:
                  return var1;
               default:
                  throw null;
            }
         }
      }

      public String dX() {
         if (this.c == com.yiyiaddon.e.n.i.g.ONE_SHOT) {
            switch ((int)com.yiyiaddon.m.b.a<"s320vri3xudo0f","DYd82iM8QEvL39WmT4fw/623Ot/tZiHljw1FdBWmIPQ=",-875660814337414081,2841863078355589116,6967324321109908411,-4561097310484788175>()) {
               case -1702208283:
                  return (String)com.yiyiaddon.m.b.a<"shw8gqnel7oe3","FC7VKq7on+c1yk70Hseco3YjcSKImnVqlaphMogl4V3DClX41P8yhZa4x5C87kTQhpcwSjHs",1954222342190137528,6790810096553575736,4320919299728662648,-8757595484108484555>();
               default:
                  throw null;
            }
         } else if (this.c == com.yiyiaddon.e.n.i.g.REGROW) {
            switch ((int)com.yiyiaddon.m.b.a<"s3m0fnsxxuirlb","PBHccucq9uN655EN+JFROFHvTJsUuZhd99JaB4ytwlo=",-2670958136123069874,7851316868574774385,-2094573530847260392,5552529497249042193>()) {
               case -724054571:
                  return (String)com.yiyiaddon.m.b.a<"s2wu0zf8ld0igu","BpBdQSAsfEN/8iiwmmyuxyH0kf51+4cKVxwcQen58dFg5zXH6AoKxxa+Lxf5IbY6",-2456742169673520357,253114528434777424,-1211379184645322058,4440504350879021532>();
               default:
                  throw null;
            }
         } else {
            return (String)com.yiyiaddon.m.b.a<"s2tg98zm9e1ziv","Y7gvZUFzTYHRH+7JrJuI0L87K8EyHqCbo4Hk66S5/446pw==",-676550723436727501,7941246965707531555,-2236395745333907097,4500283966693814403>();
         }
      }

      public String dY() {
         if (this.dr()) {
            switch ((int)com.yiyiaddon.m.b.a<"s3fx34ahpicdwp","K2mNtHm7/yHexyPBpRm3I8x0vK87glGaKpRhvNCvtNs=",1370117400278526835,3281340353308494860,2888443077911912621,-7475716020851313784>()) {
               case 1100118156:
                  String var10000 = this.tu;
                  switch ((int)com.yiyiaddon.m.b.a<"s26s2pxbq9ezn0","8MFWz4jJo6xMEN8enM/Ouz0wSt8J0VLVl7OAHjU0EJE=",1366682314568239114,5885705955864117107,-246448157010669377,1498156674997762478>()) {
                     case -1479049111:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var1 = (String)com.yiyiaddon.m.b.a<"s2tg98zm9e1ziv","Y7gvZUFzTYHRH+7JrJuI0L87K8EyHqCbo4Hk66S5/446pw==",-676550723436727501,7941246965707531555,-2236395745333907097,4500283966693814403>();
            switch ((int)com.yiyiaddon.m.b.a<"s16y73ffdodruv","RyaWuQEZub25IbNeupHcYqklNqqux/F7IDhrBo/TcQg=",-4470528912328389713,-7424674604515499118,-4279362060283418591,2953957575171892130>()) {
               case -1140119633:
                  return var1;
               default:
                  throw null;
            }
         }
      }

      public com.yiyiaddon.e.n.j.c.f b() {
         return this.a;
      }

      public String dk() {
         return this.tr;
      }

      public String dV() {
         return this.ts;
      }

      public String dl() {
         return this.tt;
      }

      public String dK() {
         return this.tu;
      }

      public com.yiyiaddon.e.n.i.c b() {
         return this.i;
      }

      public com.yiyiaddon.e.n.i.g a() {
         return this.c;
      }

      public String dv() {
         return this.tv;
      }

      public String dZ() {
         return this.tw;
      }

      public String ea() {
         return this.tx;
      }
   }

   public enum f {
      GROWING(
         (String)com.yiyiaddon.m.b.a<"s1297oiicibh55","NFaGJHM9MDEnvj0CTGyM0C94nQCy+Tf99fC0b1AGQeYxbA==",-1748004038209025909,3470562205144992127,-2672055139051345858,1633257967787886188>()
      ),
      MATURE(
         (String)com.yiyiaddon.m.b.a<"s1gb96932u5yae","bpFl5UHiyuCJmC7Wg5q+XnOqYemt8HAv/zRNKgUw5zQ=",-356651852144772115,7836788012780018169,840755365556362243,-2374082178473013273>()
      ),
      REGROWING(
         (String)com.yiyiaddon.m.b.a<"s31vzr92lyl9nl","skR8cwF6YYdyD/0eLRbFWDprBaknSJVtw95etAP5tn6DWQ==",9162800452197142315,-5700858041125040189,-7504383297778677594,705842356534080371>()
      ),
      SPECIAL(
         (String)com.yiyiaddon.m.b.a<"slu1b57g8qzun","n2GapN8BNharf6hAKvq59AjfFs/nU1D2BudOlIcRr1jCRYf7",6204004209571319724,8828242026604809392,1138479767454843286,-7496445858370691053>()
      ),
      DEAD(
         (String)com.yiyiaddon.m.b.a<"s1ujs64yfvkb03","47kpjBl15FNo2awMfvVMH6Ej87LP+dZL9/e0fp1rrc2Yhg==",-432753427617162577,8867911277914103220,-5576692703150372293,-1084896061611528927>()
      ),
      UNKNOWN(
         (String)com.yiyiaddon.m.b.a<"s12vnjc75000v0","JBe073G0gq0k11M4tWJvybrl3M3nFfMWbb+TMiZhg3g=",963683336297217343,8614675342199881041,8593376149075157928,-3448280424662341400>()
      );

      private final String ty;

      f(String var3) {
         this.ty = var3;
      }

      public String m() {
         return this.ty;
      }
   }
}
