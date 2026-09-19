package com.yiyiaddon.d.b;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class e {
   private static final Logger g = LoggerFactory.getLogger(
      (String)com.yiyiaddon.m.b.a<"s393muiw7ny8ka","6eAnnCBOwyiMVDD9zNzMqq7MzzprTpFmNXWEQXV7T2FermOQY5IY/zrAt2FZ0cAB22PEwS6vVws3Ijwy",1133447273591408018,6409787168075241972,4961111203900207340,4599188568821711144>()
   );
   private static final String an = (String)com.yiyiaddon.m.b.a<"s1930xjs277dhs","w74G5Y9MwXPZgTy0Svdi40vWk4roy3LXoCXRy5r0u3D7T2p23cwx+IgPaNoV+OC3nAfJ+wC19P9fAAktxPGv2swb",8527889091361268190,-7100970790595521511,-6007178279935387564,-638034779234929846>();
   private static final Map<String, com.yiyiaddon.d.b.a> e = new LinkedHashMap<>();
   private static final Set<String> c = new LinkedHashSet<>();
   private static final Set<String> d = new LinkedHashSet<>();
   private static final int l = 20;
   private static final Map<String, JsonObject> f = new LinkedHashMap<>();
   private static int m;
   private static boolean a;

   private e() {
   }

   public static void a(List<com.yiyiaddon.d.b.a> var0) {
      if (a) {
         switch ((int)com.yiyiaddon.m.b.a<"s1hbai2ctxt1om","3zBe+5CxMjqhZiPoWbQktqBnt11Pc0o0hBbphWFdMGE=",4922471487819874775,7081256895678951145,6544407321343014358,-7615133594158828155>()) {
            case -1795905025:
               return;
            default:
               throw null;
         }
      } else {
         a = true;
         com.yiyiaddon.c.b.d();
         b(var0);
         p();
         com.yiyiaddon.d.a.b.a(e::d);
         com.yiyiaddon.d.a.b.a(
            (String)com.yiyiaddon.m.b.a<"s1930xjs277dhs","w74G5Y9MwXPZgTy0Svdi40vWk4roy3LXoCXRy5r0u3D7T2p23cwx+IgPaNoV+OC3nAfJ+wC19P9fAAktxPGv2swb",8527889091361268190,-7100970790595521511,-6007178279935387564,-638034779234929846>(),
            com.yiyiaddon.d.a.c.TICK,
            var0x -> c(Minecraft.getInstance())
         );
         com.yiyiaddon.d.a.b.a(
            (String)com.yiyiaddon.m.b.a<"s1930xjs277dhs","w74G5Y9MwXPZgTy0Svdi40vWk4roy3LXoCXRy5r0u3D7T2p23cwx+IgPaNoV+OC3nAfJ+wC19P9fAAktxPGv2swb",8527889091361268190,-7100970790595521511,-6007178279935387564,-638034779234929846>(),
            com.yiyiaddon.d.a.c.JOIN_SERVER,
            var0x -> r()
         );
         com.yiyiaddon.d.a.b.a(
            (String)com.yiyiaddon.m.b.a<"s1930xjs277dhs","w74G5Y9MwXPZgTy0Svdi40vWk4roy3LXoCXRy5r0u3D7T2p23cwx+IgPaNoV+OC3nAfJ+wC19P9fAAktxPGv2swb",8527889091361268190,-7100970790595521511,-6007178279935387564,-638034779234929846>(),
            com.yiyiaddon.d.a.c.DISCONNECT,
            var0x -> com.yiyiaddon.d.a.d.l()
         );
         com.yiyiaddon.d.b.d.o();
         q();
         com.yiyiaddon.i.c.a(e::h);
      }
   }

   private static void b(List<com.yiyiaddon.d.b.a> var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1lr48pvz3ugo","TdMr2OWLruBZdYxbYvysHvGTa2IBrKsuBws/aBjgAKk=",-2685284015820655719,1952473172101606354,6194110939640309731,-8781391794007911619>()) {
            case -325225370:
               return;
            default:
               throw null;
         }
      } else {
         Iterator var1 = var0.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s3rey8x2px5mcq","OQuKHYc5OpM6NzgGnhQsYEd9PNEkfEdG7D788wl5ndo=",2742577464458919193,7464052969610990364,50672350026546090,4357002270697710627>()) {
            case -1082127155:
               while (var1.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1cdsb5susyict","VvvY0O2808TZV5d54g1yPD02Iiz/mPHAemh1OLp8zhA=",8744318298275158865,984956226724980933,6644741991594997253,1269495395691370997>()) {
                     case -10135070:
                        com.yiyiaddon.d.b.a var2 = (com.yiyiaddon.d.b.a)var1.next();
                        if (var2 == null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3s6tb7nd2gfug","h2YAoN406S6SoREF7RUotx2i+Do2y1GvgQbPPe0ZgnQ=",-5053400146339693306,6249264249971180050,-7959314188063144101,-2627150127394416204>()) {
                              case -439141036:
                                 switch ((int)com.yiyiaddon.m.b.a<"s2ay383n3l8wq5","AukdRi9YJSIFjyGtz2VRHGUBBPigefHYowdZH++mQnE=",5058466601159408613,5425761719438697614,4160836497130421448,5920112408536294419>()) {
                                    case -190585012:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           if (e.putIfAbsent(var2.s(), var2) != null) {
                              label33:
                              switch ((int)com.yiyiaddon.m.b.a<"s1n54risv5k7sw","YXPFNMy9n+lcj7kUpoxgEHBQDaB1qR3rJv0Zh5r3/G4=",-7229965356677940417,-6460427211888656862,1151818583905010342,-8179150773893706502>()) {
                                 case -1636375346:
                                    g.warn(
                                       (String)com.yiyiaddon.m.b.a<"s3k2b9mm0vzn1r","N1ILcY5g/RX2pdFDFrh+mii5MSGd0YyGgmoeYDdfML1M6MBohoEMgBa55w2Mn2Q2ffFRWNvsUuOuIFCpSt2HuV5vDos=",4430820649643447019,4664431708354071683,-6949241797730811767,8384818505065178354>(),
                                       var2.s()
                                    );
                                    switch ((int)com.yiyiaddon.m.b.a<"s1a0fj58pajvik","SV82CxxAfZcWUT6l/1j0zjVCx+hUHiGx0GAMMQ7RrvM=",6835453921569277258,-247164285412320622,-8476977775763211168,1718527894333875718>()) {
                                       case 1892075216:
                                          break label33;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           d(var2);
                           switch ((int)com.yiyiaddon.m.b.a<"snp89ciauygxe","3OMNqaUz4YA4gNWaLHPJIq+C0SoPbUxybiwf9o/n6aY=",376690529605406035,-3839298457182219752,-4923150094417117304,-4182271487362330318>()) {
                              case -1421801069:
                                 continue;
                              default:
                                 throw null;
                           }
                        }
                     default:
                        throw null;
                  }
               }

               return;
            default:
               throw null;
         }
      }
   }

   private static void d(com.yiyiaddon.d.b.a var0) {
      JsonObject var1 = new JsonObject();

      try {
         var0.b(var1);
      } catch (Throwable var3) {
         g.error(
            (String)com.yiyiaddon.m.b.a<"s2riilxyy7xp9f","AXbwjSsu5qeJ3VoOzhAowOTMYgbyHL9+1u7tKRoxM8TB9s+9fkF2MCRvblF4w4O8/uFC2ntzN9YGrPTPcfxkfk5GijSHVPBbBdNisIpvfoejXxZexE3pZA==",996752763684272011,4124959801416255252,-782791192532034648,6284698976599250229>(),
            var0.s(),
            var3
         );
         return;
      }

      f.put(var0.s(), var1);
   }

   public static boolean a(com.yiyiaddon.d.b.a var0) {
      if (var0 == null) {
         return false;
      }

      JsonObject var1 = f.get(var0.s());
      if (var1 == null) {
         g.warn(
            (String)com.yiyiaddon.m.b.a<"s2mvctjtvcskn4","WDEHCDm7TNS+XUT+bFiQzgZ1nt8cliBhsZvjwiU3yHMe4SF0nw6+aL1JGyZdVEgQBVOMAZdOOcw6VDSK6FIsrwgCfMLOJfrs1pE=",-8330985587052439514,7572840418732122684,-7993283775525732130,141111260848690071>(),
            var0.s()
         );
         return false;
      }

      try {
         var0.a(var1.deepCopy());
      } catch (Throwable var3) {
         g.error(
            (String)com.yiyiaddon.m.b.a<"s3dfhmrr8xgi41","bR6KyFjRvPLEkQvBH17zlD03+N392IKc2CtK9XE1RzbC1QWOCQcjDmVcEfM5EBH3Nq6dpA==",2908045837885657807,1179276656578581096,8739089491805984021,-655527207040425726>(),
            var0.s(),
            var3
         );
         com.yiyiaddon.d.c.a(var0.t(), a(var3) + "");
         return false;
      }

      boolean var2 = d(var0);
      if (var2) {
         com.yiyiaddon.d.c.a(
            var0.t(),
            (String)com.yiyiaddon.m.b.a<"s1pisr3rdi07fg","aD1iIhQt7TMF6KIKY4ylXjflwI1WfJU2GT6Mz5E1p91VCvrvFQyZRfscYpVxvA==",2182028414052414387,-1975630988334490123,-1575978738921936808,6386992907167717723>()
         );
      }

      return var2;
   }

   private static void p() {
      Iterator var0 = e.values().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"su7g8l6a48q1f","01yaLRVO5ReemmSxhsIvgyf9CEgZg7uZjRNtTjMgpaI=",-3685405553646942253,4066655403551440634,1554994741872524735,-4603978330211549502>()) {
         case 953201912:
            while (var0.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s8ouduk1r41p","iA3kEIjgorzmqvXcsadyK/ykOlaHmTM6EiuLWdD7rwo=",4799165563835609826,-6780780533536242952,4351570801905502391,-3583882428579032335>()) {
                  case -58520553:
                     com.yiyiaddon.d.b.a var1 = (com.yiyiaddon.d.b.a)var0.next();
                     if (!a(
                        var1,
                        (String)com.yiyiaddon.m.b.a<"s1229mo0678eu6","MYgTiBVZQEJ4nptF7tyyAfd9Xv1o4QdKe4AlI07cFQJkMw==",1031417670837672206,-1453519377518990164,4745116169192017799,5910273666765806935>(),
                        var1::onInitialize
                     )) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2jxaru8cdeb9r","3YMOe8+1j7TvzjSY4+/M4wZ5/eX6ri5Up0tXWMU85R4=",-1391086333877944919,-8602183990254461912,-6434233383842350882,8615762027347521860>()) {
                           case 221854713:
                              c.add(var1.s());
                              switch ((int)com.yiyiaddon.m.b.a<"s371l5qmzz2c17","LR7/ODq6V+4JOspixt7Dful7NYoAF0Ul7hDM1lqoi2s=",6551461026453847607,1637355461261618165,-4149216607404150647,-6938016038182221298>()) {
                                 case 818870482:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        b.a(var1);
                        Iterator var2 = a(var1).iterator();
                        switch ((int)com.yiyiaddon.m.b.a<"sr3j0fb74uvdr","mOX6K/xOvuT+/X91sZuZ4Fu90bOwT9HKyUGuwpbsXZk=",-6157026124900017302,6283651927116863036,4935321457541331798,-5950799365892343287>()) {
                           case 2125837191:
                              while (var2.hasNext()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2j59izp5grle6","2pj6QqQkfhkODB2nW0DV/ruEeUapJpUvYvaUm+kEfIM=",-4237548465535951161,-5231673444792116,7738543407380533571,8167556170795515445>()) {
                                    case 949668514:
                                       com.yiyiaddon.a.a var3 = (com.yiyiaddon.a.a)var2.next();
                                       com.yiyiaddon.a.e.a(var3);
                                       switch ((int)com.yiyiaddon.m.b.a<"s2zwmptzstuqzv","lZezC8v3ShkUkPLq19cUF1sDUX9JwBePTdShIygQdiA=",-8354669184734448518,-7453780079363598182,3673080170530207315,-9208548635368752192>()) {
                                          case 842425255:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s3kzywv2u70z8q","wlhrFY6y/5ELuq7Xt5TeaPxzlUUdIXYpc8GPhfgI7mA=",124521732219839226,3055572312574776763,-776042259461173153,-2267494277787743232>()) {
                                 case -1232313358:
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

            return;
         default:
            throw null;
      }
   }

   private static List<com.yiyiaddon.a.a> a(com.yiyiaddon.d.b.a var0) {
      try {
         List var1 = var0.g();
         return var1 == null ? List.of() : var1;
      } catch (Throwable var2) {
         g.error(
            (String)com.yiyiaddon.m.b.a<"s3861xojao7nqr","mLdyZ3OMb/ONR7w1KKZL8DQlic2TMf3ypfQ2+TMhesxyvQwogcG/4laPe0lc/CouWY2a7WLL",3631894677747204602,5613888632028160179,-8783083808898075684,2310093185344574241>(),
            var0.s(),
            var2
         );
         return List.of();
      }
   }

   private static void q() {
      Iterator var0 = e.values().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3tikszkg02ypo","Om8sz670x6qhemzc07uNlOqV2ZJEm1F0HT7hOXQlj2Q=",-6531019631862931899,-1272065347176995089,-2018242711534778222,-7167823468979265643>()) {
         case -957377686:
            while (var0.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s263o4v3owajef","b+5N3X1Ynjt/mgC1qA6/Jr39sKR3Z0uyvtEW2dhO/L0=",1521915006287145186,1959300360016600275,5809684988355820235,8576531108802312459>()) {
                  case -2129002746:
                     com.yiyiaddon.d.b.a var1 = (com.yiyiaddon.d.b.a)var0.next();
                     JsonObject var2 = com.yiyiaddon.c.b.a(var1.s(), var1.z());
                     a(
                        var1,
                        (String)com.yiyiaddon.m.b.a<"s1hyop41ofy363","YPltB2RFHDutQiyLgj0KSBObsfHPkpHo8IVZpE6c1ef39Zip",772685112936003163,-2772545609650749926,2660835602173343686,-4913104989554737576>(),
                        () -> var1.a(var2)
                     );
                     if (c.contains(var1.s())) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3hweac9kw7nrs","xnw12FAEG9gkxrpEiW5kUywNQ3ZC2aofBMcufK7DqjM=",-6326164726475285050,4846623635542695834,3484909883491858677,325275550639819844>()) {
                           case -1569356228:
                              switch ((int)com.yiyiaddon.m.b.a<"st7yky6dyx4g1","j5ZcmA54tCXdmbnk/Z4rEFxQ0Pjjl7TtIDlm8v/Bh8k=",-6998851111798230231,-451839450750528432,1794961511595830545,-8985369114691602989>()) {
                                 case -828778859:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else if (!b(var1)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s22nlo237c3h3j","qca6tKEq3R1cQ5x/cF5qEqWdTtgoczLufwlloCWhc5o=",3340819130278242940,-7325042880710597164,-2804221632546029247,-7361393882616475040>()) {
                           case 1961102302:
                              switch ((int)com.yiyiaddon.m.b.a<"s24s1kh9xumwph","GLaGdXuSAU7tjskjS5vuFftVmtqGHSbwEDCAsffghQA=",4774945298219872899,-788994216473388156,6634971861002650606,7869343301426165215>()) {
                                 case 691626362:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        label52:
                        switch (a(var1, false)) {
                           case SUCCESS:
                              d.remove(var1.s());
                              switch ((int)com.yiyiaddon.m.b.a<"s14s4rfxzcq0a3","ANnsa8KQFPB/CB2uOQFqDzyRaOlDs9JadVrPnyAQmvA=",7602063449213455490,2800615781558304207,18508163788690467,1762851643076250666>()) {
                                 case 1747648288:
                                    break label52;
                                 default:
                                    throw null;
                              }
                           case BLOCKED:
                              d.add(var1.s());
                              switch ((int)com.yiyiaddon.m.b.a<"s18ugqapkj4kn5","bULm/8Nnu0TRrYWV5N4uYCh6T8mloPs05dwdOJUwBRM=",-7108902639903783795,2963448908951177517,8950258603909233631,947775308035907719>()) {
                                 case 1807948434:
                                    break label52;
                                 default:
                                    throw null;
                              }
                           case FAILED:
                              d.remove(var1.s());
                              a(var1, false);
                              switch ((int)com.yiyiaddon.m.b.a<"s26bh0zm2g9t2w","/SBiNT2gKBU+lhMH/1vDmBkCeunXFe2gZUBE4/AXtOA=",5731047427728433600,-6524807085063132602,609143987285943833,1008993777881397909>()) {
                                 case 297535063:
                                    break;
                                 default:
                                    throw null;
                              }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"skdk8ynumly5i","AeRjZadJVRFaCK5Hlvpp0ZrL2duSUItCtU4EWb8zJOA=",4044059292372792945,5306822180281666854,-5097667842761384168,-8394118673076464390>()) {
                           case 1030928561:
                              continue;
                           default:
                              throw null;
                        }
                     }
                  default:
                     throw null;
               }
            }

            return;
         default:
            throw null;
      }
   }

   private static boolean b(com.yiyiaddon.d.b.a var0) {
      if (com.yiyiaddon.c.b.a().contains(var0.s())) {
         switch ((int)com.yiyiaddon.m.b.a<"s1pkrj27g7tdok","9zvTXwr2D2grW1KDLzeQMIxto0gGRQO4a1Q1zUVQ95M=",4800595425875018966,-9100804101122724148,29980919477722733,8719291057503259390>()) {
            case -1003894895:
               return com.yiyiaddon.c.b.g(var0.s());
            default:
               throw null;
         }
      } else if (!var0.h()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1lsxpmc8dzeqe","xAEI2ysgZpnBGoAIrxTXk/Xyo3Yd6t1ek0FGLVi/zWo=",-2650804994963963648,-3037125855389100043,3129113445053192694,-8382403603664397543>()) {
            case -848828635:
               return false;
            default:
               throw null;
         }
      } else {
         a(var0, true);
         return true;
      }
   }

   private static void r() {
      if (d.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"suthnjlu3yv8a","V/kIb5MG6y/tOgbRU3s30K9kjxmlQ8lW33uTWJQznUA=",6879861305828309635,2954174521994234962,-6365623618743855650,-6706795179070277730>()) {
            case -1193765488:
               return;
            default:
               throw null;
         }
      } else {
         Iterator var0 = List.copyOf(d).iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s31gnp4c5w15n9","iebOEBTUfqsW+QdLNtYmUYbo4YWE0TosP1GnrDkwHkw=",-7281816086600842957,-1589150231687416886,7431032713821845667,-571560091701008396>()) {
            case 1265054058:
               while (var0.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3pivmq7p6l3py","/ndmqUGq6nWn8PEguifAlpkW+IO5AfQWfRJ9GIkdkCc=",1663939302237850437,4112687028304455397,-199829946433041897,4595399308536136018>()) {
                     case 1652452751:
                        String var1 = (String)var0.next();
                        com.yiyiaddon.d.b.a var2 = e.get(var1);
                        if (var2 == null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1n7h07sacnz9u","+WUhDj7SarBCSZuU17MJBWdIpbaJKKwad1zN8w6NLP8=",1377633686992679707,-1341492855976043754,6369762366644809563,498444017988305739>()) {
                              case -1368235823:
                                 d.remove(var1);
                                 switch ((int)com.yiyiaddon.m.b.a<"s1q7drk8rs7yk9","ztrP/no8Op8tDPcAYkNnpcZLjVYcXbgeXuFJOsLMQtw=",7785860570013661404,8193073811919170885,7446369692079895719,-6431875745571625615>()) {
                                    case 1744060597:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           switch (a(var2, false)) {
                              case SUCCESS:
                                 d.remove(var1);
                                 com.yiyiaddon.d.c.a(
                                    var2.t(),
                                    (String)com.yiyiaddon.m.b.a<"s2xbkcvvb3gsdk","/bgorxrIXnYf3M+0K6j4DMv9mBFB8c7rcqM0vTMFdRI7s0W9u8iV3SbB",-1425470098738894411,-5206350066281262856,-998620964939137765,3099168607642278261>()
                                 );
                                 switch ((int)com.yiyiaddon.m.b.a<"s1ywqs23nq8ejv","uWBRSl437ezJHA2tUt+SjaJaJDwV+DbFZqtKtLJiVds=",-3350307892442195610,2160975754255574973,-8952207909546001218,-6448450053843580077>()) {
                                    case 1469885668:
                                       break;
                                    default:
                                       throw null;
                                 }
                              case BLOCKED:
                              default:
                                 break;
                              case FAILED:
                                 d.remove(var1);
                                 a(var2, false);
                                 switch ((int)com.yiyiaddon.m.b.a<"s2ksstb5dtsd1u","Pfr74HEI1WGF8qpTe4MIWOxSIWrllt9sqYHzAvJeLpA=",4055026429858746221,9020932064013992657,-7420087971819413370,6188719468486901060>()) {
                                    case 515824356:
                                       break;
                                    default:
                                       throw null;
                                 }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"sp5t0unwh7frt","ruACmWu1fWRA/u1TLmUHJdzEKpAvfo1nxPNA3AZzuWU=",3644740652865495380,-6354888444000105121,5438561810075611868,-2673793315028214374>()) {
                              case 72540975:
                                 continue;
                              default:
                                 throw null;
                           }
                        }
                     default:
                        throw null;
                  }
               }

               return;
            default:
               throw null;
         }
      }
   }

   public static boolean a(String var0, boolean var1) {
      com.yiyiaddon.d.b.a var2 = b(var0);
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3edzqamjmefnq","ukB8EQhYGfIqW64yNxyNjr2SIZpoBvLpKqVY3BPDvo0=",3119503892601927230,-7427465991096037922,5635259874205549385,4530004943617056764>()) {
            case 49788144:
               return false;
            default:
               throw null;
         }
      } else if (var1) {
         switch ((int)com.yiyiaddon.m.b.a<"s2puzr98kfsrhc","iyBz4byDUvxve4yJ4SASzlaMtysdm0pnsv7TKo7J7OQ=",-8294822384215560353,-3346995455376849546,70063208255252863,-2872079434983123252>()) {
            case -1313915969:
               boolean var10000 = c(var2);
               switch ((int)com.yiyiaddon.m.b.a<"s119wz9zmnjlkm","8LEMc+WK/RzSgpwAX/rMe6agiqf+5WYRmaTT47RndL8=",4632725344279372015,1690712231611748100,5160529360734663230,-7597050485848661415>()) {
                  case -1595348438:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         boolean var3 = b(var2, true);
         switch ((int)com.yiyiaddon.m.b.a<"sxc53pd4d7bk2","Rifm7BH3ncBRGZUhVoCqHkkcATrh6nBS82ls/Ynz8ZU=",-5794748378294837723,-8578921852725457301,-7417196200216124171,2093579068419027916>()) {
            case 1769239024:
               return var3;
            default:
               throw null;
         }
      }
   }

   public static boolean b(String var0, boolean var1) {
      com.yiyiaddon.d.b.a var2 = b(var0);
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s256oxo4f2qytu","SLmYjKW5/iezYFNLOnX0Z1Ekr7yfpfFDJq5UKaheqpY=",-8468080783953645530,-8121322258810533041,-9146229023899442010,6408752417607094642>()) {
            case -9033916:
               return false;
            default:
               throw null;
         }
      } else if (var1) {
         switch ((int)com.yiyiaddon.m.b.a<"s3datkb0f69ech","h9QdwBLwCkAYcrOA706aCeAN9BvJirlMHK5CMVIRJNM=",-3502074307966850680,-2422320194691980896,5494278067658326438,8493066486450215764>()) {
            case -992008033:
               boolean var10000 = a(var2, false);
               switch ((int)com.yiyiaddon.m.b.a<"sz7uxktdqvmpe","YTWHqYQlW3p/srQLCRxb5iM2DQiweQV/rHI2zVtXXj8=",-6207706637356420297,5413020042133595047,-8043826313031588697,-8630889468276748873>()) {
                  case 1144214344:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         boolean var3 = b(var2, false);
         switch ((int)com.yiyiaddon.m.b.a<"s1gvinrjx82a9n","Qn09c8amDkNHd36XR0T84UP4hDpRKOmlnuCYKtFItfA=",2641156439512036206,2180997963443538739,-5998114962775527072,5620777191521572575>()) {
            case -791616987:
               return var3;
            default:
               throw null;
         }
      }
   }

   public static boolean i(String var0) {
      com.yiyiaddon.d.b.a var1 = b(var0);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s10sgf6r9gjii3","okpEb9w39N48lVocojrpuN0B+3t8VdD8IUD9XzAU8ns=",-3830865677544168173,-8643830140717980785,6312866207992049458,7226331040705733171>()) {
            case 55006107:
               return false;
            default:
               throw null;
         }
      } else if (var1.g()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1pb1drqlpqa42","T0vIMzHNKJk2QPsbho/2du8ntgMeJMPU2l2m6cSVYcs=",-3180427297019214998,-3922368743897522490,-5438879964541078893,-575342587529873585>()) {
            case -2073309683:
               boolean var10000 = b(var1, true);
               switch ((int)com.yiyiaddon.m.b.a<"sy7dv4ia8eigp","SbhOQnJOUmRk5pqjdUsBipAJ/XUOLdzELo0U3XjuR/Y=",5279815299136243658,-4825497616741519862,-2060634423775977889,7745888388149905070>()) {
                  case -1474780697:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         boolean var2 = c(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s11mv05mlmotpb","j3VLa99MONrBXEwZsrcK71krvzI3juFVagWjD8q4rW8=",6931067030862247276,2694911541216212832,1565117428436917450,7380990046618586347>()) {
            case -527105702:
               return var2;
            default:
               throw null;
         }
      }
   }

   public static boolean g(String var0) {
      com.yiyiaddon.d.b.a var1 = b(var0);
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s33ixyltihz6ik","eUVg1zP4qGSvXfAa2Xxe0MV039s6b4Sv436jO42c49E=",210709503771924015,-2540290608957828020,8614088944662261521,5072572515719372250>()) {
            case -474449052:
               if (var1.g()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3ez39nbq8wm7t","/b5hyk4Q2a8Deim10VtUGWnNk8SdpwoYKyJU90A2iAU=",-8604525465512747567,-839795688573562705,-832214242361276008,1169281606773394979>()) {
                     case -797096365:
                        switch ((int)com.yiyiaddon.m.b.a<"s2f1ikdharoem0","JwTuo0dj3zTVrYJNwJM79MRDjXlcXR4Oq+FvlonBwMQ=",-5799271430352295404,4197082992130753453,-2883008532624923603,-5090315639107857197>()) {
                           case -1556200934:
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

      switch ((int)com.yiyiaddon.m.b.a<"s1f6vvarect1nd","X2/gwnYNbLfkW7JJRML60RP5NXEKapbB1hD/nClfTCY=",-5749544954832623566,834843416342731483,-2700225797253165693,8991337334294275906>()) {
         case -601590005:
            return false;
         default:
            throw null;
      }
   }

   private static boolean c(com.yiyiaddon.d.b.a var0) {
      return a(var0, true);
   }

   private static boolean a(com.yiyiaddon.d.b.a var0, boolean var1) {
      switch (a(var0, var1)) {
         case SUCCESS:
            d.remove(var0.s());
            switch ((int)com.yiyiaddon.m.b.a<"s14bcrm3hjiy37","RlsM3ZRzDnyjAwaeC8ND0yYvJMOc38LjROr/efSazFw=",-5579886086208297429,8030871203025226961,-8562360358324358295,-6206725003841346317>()) {
               case -2109172048:
                  return true;
               default:
                  throw null;
            }
         case BLOCKED:
            d.add(var0.s());
            switch ((int)com.yiyiaddon.m.b.a<"s2sge30wd5fia5","At6smOqQS456y+/PdbdI7XhUv2RanslnHRc4hWjT++M=",4226068340785350523,-3561542225046319501,-426014447945271929,4841310884047161453>()) {
               case -1840183097:
                  return false;
               default:
                  throw null;
            }
         case FAILED:
            d.remove(var0.s());
            switch ((int)com.yiyiaddon.m.b.a<"s2yfhwh2m1w24t","wvVhvQq5Rgve8u5BGG1WsggLjrdBKG2q1zLseQ4tg58=",8120174281889251861,-833399251535107821,3516180150577174767,-2474949415140707431>()) {
               case -1035220410:
                  return false;
               default:
                  throw null;
            }
         default:
            throw new MatchException(null, null);
      }
   }

   private static e.a a(com.yiyiaddon.d.b.a var0, boolean var1) {
      if (var0.g()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1rh5e7xoj0k0f","jb6fgD5j2gOpegg0a2vbfXsN1oncSKYemuHeOZanKc8=",2161202563469540465,-1618405535543603171,972780339357062555,-7221266742686223710>()) {
            case 1773999810:
               return e.a.SUCCESS;
            default:
               throw null;
         }
      } else if (c.contains(var0.s())) {
         switch ((int)com.yiyiaddon.m.b.a<"s10pdkytvsazbn","o9BqIFX703SsKOdOvQ6V/ooCUykAglcMugVxk8hmyGo=",-3397253898862238670,6671089856365064740,1479066924582617377,-8053033909103077725>()) {
            case 527937172:
               if (var1) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2bdhmcxqkun6e","cj3CgWFaa8KOTYsZ9ERhwAkDxKGlH+atMJMENpvrf8c=",-3664711910389270658,599083383537774003,6470021917737238858,8163205858781519054>()) {
                     case -1864551408:
                        com.yiyiaddon.d.c.a(
                           var0.t(),
                           (String)com.yiyiaddon.m.b.a<"ssejp5yzpfwdw","isnnjq/bFGuDfN3BtkzCzdalkDlCo00GGQre4RxNbMb3ifQI3ku/r2z30SRGpcRG3zAJo9OwsFtBPg==",4296390676575530491,3963352193671067311,4273870844389315883,7793346615456232096>()
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s3nwniwjvlukjo","IOQhLOAcl/4qCWZEAJA+DbdNNfoyd6XgXl3mgLB/ZPo=",723861001920436224,6671651055259308220,-4590226318220829787,8945034404795409262>()) {
                           case 988970176:
                              return e.a.FAILED;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return e.a.FAILED;
            default:
               throw null;
         }
      } else {
         List var2 = b(var0);
         if (!var2.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s12cw8i0cux7x5","3JJDrB+H0xnNNdRY42Gv/YT3UbdQ6rqgdcQtK0Lm9rA=",4375138561458293733,946726176430591534,4341429504981344902,3642855059714754302>()) {
               case 674447640:
                  if (var1) {
                     switch ((int)com.yiyiaddon.m.b.a<"sejwjghk2hq6d","tdhtwqXve/5Zijs3WPYB7Ezn1cwnPDK2zCJSW3457rc=",-4182205638102081377,-6514458223601898010,8747081062501496118,1091160958806182857>()) {
                        case 1207255277:
                           com.yiyiaddon.d.c.a(var0.t(), var2.size() + "");
                           int var3 = 0;
                           switch ((int)com.yiyiaddon.m.b.a<"s33jp7xehvim6","4SGGnXDKGbdn5klEF1onEdpWtVMUtnChkb8zUIINlqk=",-8098206483780473573,188929439956852571,4755867334086875036,-536106972802252940>()) {
                              case -42275055:
                                 while (var3 < var2.size()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1x3uxtkt1dy12","e4/kjZOR/fEnxp2zj6M/9iahCBgyTPRVA58S4sMg3ds=",-953404361589753229,-1331810139870823715,5296377915984236704,-5497775098673703134>()) {
                                       case 1582748922:
                                          com.yiyiaddon.d.c.a(var0.t(), var3 + 1 + (String)var2.get(var3));
                                          var3++;
                                          switch ((int)com.yiyiaddon.m.b.a<"s1aij4nvt0gbc0","xwdsL4UgdxZcXN+MMgQOq/s3kNJI0ZH1ebNhhdrI0G4=",469493830573248919,993290122187707047,6574971260364346034,5288512992618915608>()) {
                                             case 701576022:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 a(var0, var2);
                                 com.yiyiaddon.k.a.bk(var0.t() + var2.size());
                                 switch ((int)com.yiyiaddon.m.b.a<"s3f4dysmxtaf43","yF5/RMFa1bBJjNWMufo2yu84zmkyUJQzXAJ/VU2j+wI=",-5429551329366676243,-5991874592470677419,4928744480528122974,3608815396102651023>()) {
                                    case -1598350993:
                                       return e.a.BLOCKED;
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

                  return e.a.BLOCKED;
               default:
                  throw null;
            }
         } else {
            var0.a(true);
            com.yiyiaddon.d.b.c.b(var0);
            if (!a(
               var0,
               (String)com.yiyiaddon.m.b.a<"s39cyr2x437zr4","LhPZD6JdWlLCG3vzfdMxxKQkoCiLDgPzAOQB+oglxT8=",8337556120863381655,8328148975368576778,1686195399117250439,-5169280596210571218>(),
               var0::m
            )) {
               switch ((int)com.yiyiaddon.m.b.a<"s2diejxqx43lia","k5O/DWrRfxEShggxvIbsF5Cq7jrIVogi1by7wyWvnJE=",-6748734672987822235,-7872698242526207890,-2620043803915288727,-6632272929811277324>()) {
                  case -353834938:
                     e(var0);
                     return e.a.FAILED;
                  default:
                     throw null;
               }
            } else if (!var0.g()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1dwzvkxedeqtz","227x695AFREiSR5M9dyERjnlR23HnzgDb62Mseob60w=",5096537279323413839,1355385721470955625,-2799064005236101169,-2784059466332310781>()) {
                  case 119267460:
                     a(var0, false);
                     return e.a.SUCCESS;
                  default:
                     throw null;
               }
            } else {
               a(var0, true);
               if (var1) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ex2fh67gl8uv","a9Wd0BnJoIoew1Ta+EukaHnAS7/Y6KeER45xiT5Gu1A=",3988042954888687133,8850046428843842077,-21230997463909542,-5462915594009947024>()) {
                     case -352605276:
                        if (!var0.i()) {
                           label72:
                           switch ((int)com.yiyiaddon.m.b.a<"s3nw3vjboyfijl","fm1jP5cyZIGMFBhV4G8jDIybwZeSqKpsbzR0RaACcbE=",-1988126060014844174,-9172932295544965466,-6441964413870460424,2863715264490542676>()) {
                              case -922635239:
                                 com.yiyiaddon.d.c.a(
                                    var0.t(),
                                    (String)com.yiyiaddon.m.b.a<"s2xbkcvvb3gsdk","/bgorxrIXnYf3M+0K6j4DMv9mBFB8c7rcqM0vTMFdRI7s0W9u8iV3SbB",-1425470098738894411,-5206350066281262856,-998620964939137765,3099168607642278261>()
                                 );
                                 switch ((int)com.yiyiaddon.m.b.a<"s3ef85yx4d9f4g","BmMK3/w3G2HqFnQLVf8EI+WBm7llCqf2x0aPMtrzbgs=",4189509273730608172,3694386689183063509,1969345770746959071,-1244316569960339481>()) {
                                    case 1961492173:
                                       break label72;
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

               com.yiyiaddon.k.a.bk(var0.t() + "");
               return e.a.SUCCESS;
            }
         }
      }
   }

   private static boolean b(com.yiyiaddon.d.b.a var0, boolean var1) {
      if (!var0.g()) {
         switch ((int)com.yiyiaddon.m.b.a<"s21y5s6yzpru6j","6TI3+oMr9ypIgBvdGgQ338hnut7ddQREwIf2xG887tc=",565649506206360762,4649794683250776045,-8880067571557971254,-4397811392217884340>()) {
            case 1422518277:
               return true;
            default:
               throw null;
         }
      } else {
         e(var0);
         if (var1) {
            label18:
            switch ((int)com.yiyiaddon.m.b.a<"sv394sjq8plit","/iYqZz8XeHFkfjOmlYSYT9MGR/JIDZg5Ofe9DeUJgGo=",2482590076262812989,6046118867236717181,-7739183878791430016,3922155646471831916>()) {
               case -1388571597:
                  com.yiyiaddon.d.c.a(
                     var0.t(),
                     (String)com.yiyiaddon.m.b.a<"s16hcoj5q5196l","28H3BMOymRyqiK59PKi527w+kwXja39zAYyAcR5RtagYBaXNdJaL3qox",-1517500516118470570,9165401933014503817,4110029901978691985,-3974124012210164144>()
                  );
                  switch ((int)com.yiyiaddon.m.b.a<"s2rttx0p0gscr7","ofMQHcIzsRX0e7XZ9eBI21ryHVYGRfwkKnAKZrunYtY=",-6209236735604410897,9143035531722507792,-6383230464173486691,-3548935772355627072>()) {
                     case 1380449168:
                        break label18;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         com.yiyiaddon.k.a.bk(var0.t() + "");
         return true;
      }
   }

   private static void e(com.yiyiaddon.d.b.a var0) {
      com.yiyiaddon.d.b.c.c(var0);
      var0.a(false);
      a(
         var0,
         (String)com.yiyiaddon.m.b.a<"s2rho793oewcm","F4+Kg3LcG+u5JwULxAvwJi2dpSkjeK1x60PC8Ca8/hg=",1474071824166797815,-3193515312152303725,-493181024351701029,4600582665745312847>(),
         var0::n
      );
      a(var0, false);
   }

   private static void a(com.yiyiaddon.d.b.a var0, boolean var1) {
      com.yiyiaddon.c.b.a(var0.s(), var1);
      com.yiyiaddon.c.b.e();
   }

   public static List<String> b(com.yiyiaddon.d.b.a var0) {
      if (var0 == null) {
         return List.of(
            (String)com.yiyiaddon.m.b.a<"s2u57pddfk8ag8","INofbEcnuIENkAlELVWBL16Du8v/crmyDs7jw5kKYFGAZ9dq05c=",402189764253792468,3857222637023012814,-2604197262524601884,6015638073759763543>()
         );
      }

      try {
         List var1 = var0.f();
         if (var1 != null && !var1.isEmpty()) {
            ArrayList var2 = new ArrayList();

            for (String var4 : var1) {
               if (var4 != null && !var4.isBlank()) {
                  var2.add(var4);
               }
            }

            return var2;
         } else {
            return List.of();
         }
      } catch (Throwable var5) {
         g.error(
            (String)com.yiyiaddon.m.b.a<"sa2n8dhfxz7k1","p6XKFsGy7GedqIZKM1igsLAsB5K6GOYMWXZeLNoFqaWVX5NBX9osVR9Ly7B2AdH+",-6581468234540759266,-7576039803357440220,-5400293093259310682,-1385814038823634433>(),
            var0.s(),
            var5
         );
         return List.of(a(var5) + "");
      }
   }

   private static void a(com.yiyiaddon.d.b.a var0, List<String> var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2uud8qpq0g270","57OCbtIksZQXa31/GyyazEnuKoMyptFWMXmK4FrMUhU=",-566430858524488902,-5789455690411025988,-7861170645124487094,-3983773695587117967>()) {
            case -1993366096:
               return;
            default:
               throw null;
         }
      } else {
         var2.execute(
            () -> {
               if (var2.screen instanceof com.yiyiaddon.l.h.c) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3s7ewjgm7ap2t","pE6aneMDShrM3/fSi6kFBIHOdpuMjbusWsGDGitdJuo=",3580657342133637708,3845821579574397709,7763190420291796976,-7946564793917394552>()) {
                     case -100851826:
                        return;
                     default:
                        throw null;
                  }
               } else {
                  var2.setScreen(com.yiyiaddon.l.h.c.a(var0.t() + "", var1.size() + "", var1, var2.screen, () -> new com.yiyiaddon.l.h.e(b.a(var0), null)));
               }
            }
         );
      }
   }

   private static void c(Minecraft var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s248cnbrnuralh","DDVPrOBenBufpasUZmCpnHo4Yn/q96UGKd6igGgSU/k=",-2690422971893586831,6385814983914149588,-6267201543949610769,-4801055423046216361>()) {
            case -131241148:
               if (!e.isEmpty()) {
                  s();
                  Iterator var1 = e.values().iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"seuv8a22ak5wv","WGAN5h8UZqXyMHfDPZXMrIjDEcenHfuznAC4bpapolE=",4918946569229552582,1436338787407544828,701617984089785980,5540705068001318615>()) {
                     case 1149297015:
                        while (var1.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3ilzp1ezo6vg1","ixy1Bs7i7pdlhjOe9cwcLL9Kj76ijk+VMwsF1srq3bI=",-4269219652604571399,-8585785233092373833,4687637776482380442,-1236639889463339236>()) {
                              case -702457568:
                                 com.yiyiaddon.d.b.a var2 = (com.yiyiaddon.d.b.a)var1.next();
                                 if (!var2.g()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1jybnho7hdvkk","W8hxnkZSMD/Emby1X8sXT1IX3Zex/IiH+0CVQra+FxQ=",7800679507316225448,3379711921541155561,-1831015989628981524,5483264032099461756>()) {
                                       case 373382900:
                                          switch ((int)com.yiyiaddon.m.b.a<"s35jpso5alk8oq","LcSowEwOCK3TXLANHPMupy7dUJ/vF34jnGY2bOWG4I4=",6939812174875324576,6778850775006971475,3689054079945105373,4816666322549513754>()) {
                                             case -1037833054:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    if (!a(
                                       var2,
                                       (String)com.yiyiaddon.m.b.a<"slujoz8r64p43","bU3fGFqgnvN38m2jcydx5l3cGEN9CO1S1H4NBehsgOE=",705956458772502429,-1692783485092118288,-5445281886624103830,8838891009949993495>(),
                                       () -> var2.b(var0)
                                    )) {
                                       label35:
                                       switch ((int)com.yiyiaddon.m.b.a<"s1a50cc8enwds9","GroqatSLOdVIwhgFwtQXFcUwE9gL0HZUYqlMv+2uR/k=",3371081283343232825,-8393778338109073335,-729803135268002812,-3382870313971396373>()) {
                                          case -1473328269:
                                             e(var2);
                                             com.yiyiaddon.d.c.a(
                                                var2.t(),
                                                (String)com.yiyiaddon.m.b.a<"s1ru6psi8e42dy","mdWr8wHTpvzvREyYw33Dxu65FXqMZ8yqhzmeuhodRxOoybq15Voj8hP5c8viHMc3wPXq9Q==",-6792988780196820737,8588324819970649481,-180169124199690218,-1614581689443107406>()
                                             );
                                             com.yiyiaddon.k.a.bk(var2.t() + "");
                                             switch ((int)com.yiyiaddon.m.b.a<"s1jkbf9qyd6g0x","zwi9fYl4TUhsmm9aVw40UCipbLRZ/7d0cwMJ2Q2SqMw=",6530180348383924606,-2647974106986781995,-1102404643499897874,2100139887485659408>()) {
                                                case 1436295980:
                                                   break label35;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"sebktqv71xhwn","z1/yG97jiluL8hnWj0Y3wCxE1+kYzc4m+qwM4qG/RHI=",7515770945212860337,-1320119040613954513,5974151316496330223,2991900974604212718>()) {
                                       case -692773852:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sy6zigrb63475","JnpihYrFjXDvuwfD93as6eYHWQfJdx6GdgHrEV2hPU0=",-6011535722673145651,1293010908047870845,-2686082151929276095,-8773785432515201074>()) {
                     case 498177293:
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

   private static void s() {
      if (d.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2nncmhgusr1jb","Kfu9duZSCRETze2A26AxoBeAE0KLEYRg/lU2XISVjtE=",3181302906379680495,2622506998965907357,576699928735325759,4450946280986387860>()) {
            case -864108979:
               return;
            default:
               throw null;
         }
      } else if (++m < 20) {
         switch ((int)com.yiyiaddon.m.b.a<"s3aowl5cxjfj92","peAfbK2MA4Yjoec06QfnIap001grLpHgMSC3HqXlfVU=",3503529937389748291,8284264850494984237,2188323139996156817,1300156732511171858>()) {
            case -3593873:
               return;
            default:
               throw null;
         }
      } else {
         m = 0;
         r();
      }
   }

   private static void d(String var0, Throwable var1) {
      String var2 = com.yiyiaddon.d.b.c.h(var0);
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2sk5x3gw1y61v","oCuIh/LMDaQw6jsPRTvUbcmxqBOCqeXPRcon1VPqSCs=",1430191412213498870,8305452692437722666,4078628974719355321,8693085109224553965>()) {
            case 271764293:
               g.error(
                  (String)com.yiyiaddon.m.b.a<"s3cj8r7ffg2ony","TJgvpZWBCfN9kVsmbX+A1Dk+1yWFWAGn5P8TUAJmiUOlfYFMc2+clFSm4VxThTBGdNkGPXUrXeo=",8570198178897007275,-2210341526465672089,8934851682795104229,2743021775779518429>(),
                  var0,
                  var1
               );
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.d.b.a var3 = b(var2);
         if (var3 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s21noagnlk3bjj","ZiVujWAunJ+lkFXbs9ylBO2BPkYUjaqWC19CdXUrRKM=",-588911914299555922,-2781169609951510194,-8315109415743582790,-9069219937946763761>()) {
               case -1379440296:
                  return;
               default:
                  throw null;
            }
         } else {
            g.error(
               (String)com.yiyiaddon.m.b.a<"s1uiffgw1n3b6z","Gd1zy2qGp+ju2h2sLPJSHABb/OLcFbWT/WY6/ke331oRUOae3sKgOEhbpQAVQxB33VZyog==",-754574829674955499,-1729817966870143636,-3351207407714195778,-1307512134542547999>(),
               var2,
               var1
            );
            com.yiyiaddon.d.c.a(var3.t(), a(var1) + "");
            e(var3);
         }
      }
   }

   public static boolean d(com.yiyiaddon.d.b.a var0) {
      if (var0 == null) {
         return false;
      }

      String var1 = var0.z();
      JsonObject var2 = com.yiyiaddon.c.b.a(var0.s(), var1);

      try {
         var0.b(var2);
      } catch (Throwable var4) {
         g.error(
            (String)com.yiyiaddon.m.b.a<"s371s8ubt7dyar","7RsJVDQIfysjNr1BzKYR8MPEwbvDDfnbSN30vEXeWb9cnga10Sovyy0TXCFjHfgW28sDJA==",-873965954770706449,6752436250038209595,-5360013035342930249,-2446102550392495406>(),
            var0.s(),
            var4
         );
         com.yiyiaddon.d.c.a(var0.t(), a(var4) + "");
         return false;
      }

      com.yiyiaddon.c.b.a(var0.s(), var2);
      com.yiyiaddon.c.b.a(var0.s(), var1, var2);
      com.yiyiaddon.c.b.e();
      return true;
   }

   public static boolean e(com.yiyiaddon.d.b.a var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1g4ocf7gatvj1","WeFw2/TP0yNO6Yy+aa41Pqq6XqslaQO2qFkcd76AzrE=",-9004828134564868093,8110656289880822484,-2982907171007774702,6914156515186973610>()) {
            case -1425039927:
               return false;
            default:
               throw null;
         }
      } else {
         JsonObject var1 = com.yiyiaddon.c.b.a(var0.s(), var0.z());
         return a(
            var0,
            (String)com.yiyiaddon.m.b.a<"s1hyop41ofy363","YPltB2RFHDutQiyLgj0KSBObsfHPkpHo8IVZpE6c1ef39Zip",772685112936003163,-2772545609650749926,2660835602173343686,-4913104989554737576>(),
            () -> var0.a(var1)
         );
      }
   }

   public static com.yiyiaddon.d.b.a b(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2z4n1hvct4uj6","Lw/3kydmAYF7jc1qmI8nmGXQ4YZfjM/EcFwIHxh3Uio=",-7258104455109202785,1158571005908669909,2963807519629608317,2612690183353538524>()) {
            case -1138418281:
               switch ((int)com.yiyiaddon.m.b.a<"s2ywlwdllcova9","01bTEfm/EA6h9al4w+8z7romHwhtyzZu4T4YSIMrbDo=",-8374964221637930590,4690244725604499624,3416097397259278826,3348604080618362830>()) {
                  case -764262790:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.d.b.a var10000 = e.get(var0);
         switch ((int)com.yiyiaddon.m.b.a<"s2cr5cspx743wx","2zxUBzKPgbxnU0cvdTOIIQx455JzZAPTDL2KZiUWTnI=",-4131310835313633706,-7596737147901692322,2624798363857033854,-325439810463215412>()) {
            case -686845149:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public static List<com.yiyiaddon.d.b.a> c() {
      return List.copyOf(e.values());
   }

   public static int b() {
      return e.size();
   }

   public static Set<String> e() {
      LinkedHashSet var0 = new LinkedHashSet();
      Iterator var1 = e.values().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2nusei1dw8vij","KBhUo/EaTCyzRMFGsaQ0Pn3aJXKm9M6CzEuDIgmzBg0=",7529075530240557747,-2602081538307660056,6924708354846605273,-3038574854813059840>()) {
         case -1657393500:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1lnefyqwb7liz","jFml7hhQ7adbZFFoOTDvP2cvTvaRbobL5jmeuYyRumU=",-604916110777580114,-4726330360503004741,-1950093730622675417,1142728002827557263>()) {
                  case -773648511:
                     com.yiyiaddon.d.b.a var2 = (com.yiyiaddon.d.b.a)var1.next();
                     if (var2.g()) {
                        label23:
                        switch ((int)com.yiyiaddon.m.b.a<"s2y01vrg7mpc6m","is/4UoHp4l1MwR/TbCVqgwSrPFTtWUXfogXTuOU5nQA=",552168206186504238,-1928410388001855169,1667344459690951681,-3433389780436210461>()) {
                           case 935958122:
                              var0.add(var2.s());
                              switch ((int)com.yiyiaddon.m.b.a<"s3m6aim97648zd","XffSt8wjUstwdTo74E/fGmUxun9H4+pDNdaZ7OzT+mY=",3029672677245566540,4342023614754073821,5998045823235969487,-615303719782084112>()) {
                                 case -1114644172:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s1rt2742y575fr","vE3NaEQXMwGeiaem5V1QTqi9vhS94NU/eVrgDJaqUbk=",3550038616615273785,2094483219560867297,450501023977685542,-7582046391070079295>()) {
                        case -688443656:
                           continue;
                        default:
                           throw null;
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

   public static List<String> h() {
      ArrayList var0 = new ArrayList();
      Iterator var1 = e.values().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2be7n0ls8scp3","lCb24/laj/V9mdcPuHDEo/T4nlgmGBE0zv/15Wu99gA=",-4725488184103652845,7906607551392302117,-1245655343798678430,4406890471326809649>()) {
         case 508364291:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"sclvz3mo7gpws","T0sL4hZsDaZR+R9UlY9bej5VO8RGMLnD61GdCP+vusA=",5181456863655977044,7440842561242856391,-14698576865624610,1462576825019766747>()) {
                  case 1414695563:
                     com.yiyiaddon.d.b.a var2 = (com.yiyiaddon.d.b.a)var1.next();
                     if (var2.g()) {
                        label23:
                        switch ((int)com.yiyiaddon.m.b.a<"s3ao9n7a0jjvzv","ASSp9fXMObiWj/9iYI4lPSDBsJRgmYmiMzME+F8BbnA=",8912001548227212497,-611462841042402412,2192019876690856994,61303491023001580>()) {
                           case -910868207:
                              var0.add(var2.t());
                              switch ((int)com.yiyiaddon.m.b.a<"s1gbw8sh6ozkcn","/NXqjIt45X4dw1I8SK2I2bOscV3kuMvWG4m4jKPJX7c=",-6258597359554650581,-3659219006778086532,-789271367556394331,618494424712062619>()) {
                                 case 158470416:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"sgk8cwb539lh2","6fa7zKPeOP7AuYnkbWpS60wIKASZSLdqKnceCt7FODM=",4826910302361747774,2457222384950329916,3110678026425403349,-4731413430538760836>()) {
                        case -290693593:
                           continue;
                        default:
                           throw null;
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

   public static String b(com.yiyiaddon.d.b.a var0) {
      if (var0 == null) {
         return (String)com.yiyiaddon.m.b.a<"s2u57pddfk8ag8","INofbEcnuIENkAlELVWBL16Du8v/crmyDs7jw5kKYFGAZ9dq05c=",402189764253792468,3857222637023012814,-2604197262524601884,6015638073759763543>();
      }

      StringBuilder var1 = new StringBuilder(
         var0.g()
            ? (String)com.yiyiaddon.m.b.a<"s3f36zp6852s89","GYHQ2OieqcEIsjJ0P4WS9gjeXqZHBryFm/xwXRy4CaERIw==",-4428600549732284802,6664654973494570910,5944558636032263804,-7076181652729362186>()
            : (String)com.yiyiaddon.m.b.a<"s13pqp65vba3bf","SdnJnSebNPEfDA4WCXX0TOhKsxgZwgxC99ABZriyhqgzwA==",-2250038828779813249,-1915383390493243767,-4011404878541464165,-1381583588951111886>()
      );
      String var2 = com.yiyiaddon.l.d.d.ck(var0.y());
      var1.append(
            (String)com.yiyiaddon.m.b.a<"s36mciks9xfx2f","iUxC9Y4qKlRLCj1+gSe83pqVfMEmqp9OeRSRLa7wo9r4Zg1STU4=",7326631215828166362,-5072653617986985330,-4145374110266709481,-776476381994449688>()
         )
         .append(
            var2.isBlank()
               ? (String)com.yiyiaddon.m.b.a<"s2p4bt6ns4910x","TOXxkVrtR5MTmKO9o1M1mAScit8q9jxqH5ow73Wek8QgWA==",6998114999729813492,4764686067130937383,8812506222882043384,-2545912319517121015>()
               : var2
         );
      int var3 = 0;

      for (com.yiyiaddon.d.a.c var5 : var0.c()) {
         if (com.yiyiaddon.d.a.b.a(com.yiyiaddon.d.b.c.a(var0), var5)) {
            var3++;
         }
      }

      var1.append(
            (String)com.yiyiaddon.m.b.a<"s39z7sqcrdqv77","V8rUsyiYeQAl913jfpCkhOgJhvcxmuEXszYRWbqP1Zxv+V01ouUcTw==",4644021609329706841,6779164619354568047,945420789817109840,-4086813131633789857>()
         )
         .append(var3)
         .append(
            (String)com.yiyiaddon.m.b.a<"s2qastlj6porww","8vcxpgXbh9SyQDAZ1BtEcVjkj6A/loeRmQ6MEZhltek=",7341890835177420451,-1137923026717268184,-5096809382339722363,6077372107775409754>()
         );
      if (d.contains(var0.s())) {
         var1.append(
            (String)com.yiyiaddon.m.b.a<"s15gqe6unbclmm","NszR7vHWUvOMLt5hPeDwde2wes1sxjjnZfroEnyYbYXFZyorP+/rE4J+",7517966441510978374,823528783041105231,-890467017269539537,7962075060000960742>()
         );
      }

      if (c.contains(var0.s())) {
         var1.append(
            (String)com.yiyiaddon.m.b.a<"s33c2r515q5u9a","vu3opbcG0a8UlprlJgywWfxT92JRTPpNlDirh7CLoNvBT27d9pEzRg==",3815760642935771657,-326884375410376575,8290734834428643819,-2348643185444341464>()
         );
      }

      return var1.toString();
   }

   public static String f() {
      return "" + e.size() + e().size() + com.yiyiaddon.d.a.b.e() + com.yiyiaddon.c.b.f();
   }

   private static boolean a(com.yiyiaddon.d.b.a var0, String var1, Runnable var2) {
      try {
         var2.run();
         return true;
      } catch (Throwable var4) {
         g.error(
            (String)com.yiyiaddon.m.b.a<"svcdeub0jykxe","+ULtDXNi0OwkPlupIKo3SOlE4WVih8ZEG6N1IExDHJxDD+OIBlvj157hhG9lxnwS9mGv22DxXcA=",-4985174288683155280,8102349679157261788,7750824634184007228,-7268274114864695573>(),
            var0.s(),
            var1,
            var4
         );
         return false;
      }
   }

   private static String a(Throwable var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1scgf3lpkfyjj","+mStLx4RqDNi7HrCUTiJk1ip0VhfQ0AQVKqyQzMcrnE=",-7130728091528134865,5647673370948690023,1054271982713592473,-8931246093881550359>()) {
            case 194485763:
               return (String)com.yiyiaddon.m.b.a<"s2grmjdgsytakz","iI/f6ih9sX7P+KjHCspHyrkqliUOv0CnryLJP8Aa7YSfpFXZ",-5921014695577928341,-1605477950371936858,4087439921633806820,-7017482661726856189>();
            default:
               throw null;
         }
      } else {
         String var1 = var0.getMessage();
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s25dqoowqz1l79","cBoo9a2+Y0u7+2xxvJ6aPgvOui34Kkj8LwsmXl+Fktg=",7916768183598087037,3095589370708244644,-8409031401070430762,5041425640933548492>()) {
               case 558253413:
                  if (!var1.isBlank()) {
                     String var10000;
                     if (var1.length() > 80) {
                        label27:
                        switch ((int)com.yiyiaddon.m.b.a<"sfuonnc4jy7d4","vXGfYhCoRyBrLVt9FyyTG2hs9qKHrbBWsD4h2duzoDA=",2045034173960736683,-2494368038557106033,-3052437028079489103,7183276688682729999>()) {
                           case 1202744972:
                              var10000 = var1.substring(0, 80) + "";
                              switch ((int)com.yiyiaddon.m.b.a<"s1cv7ebtso6gsy","NUe7yPezQK/E3sq8M5Rl3CCyEhI6IAnmQYn6nPxrOG4=",148467176683363724,-7767324556477169016,-1673576895884961833,-6106774112361610801>()) {
                                 case -2030985729:
                                    break label27;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10000 = var1;
                        switch ((int)com.yiyiaddon.m.b.a<"s6cc6655em3ov","gkl3TMIKz9SDb/MerluWbm3ljp2+vpE8KAaCHND5ibY=",7522411378929522042,8332983213638712494,-3665142356409556259,1234761372374329233>()) {
                           case 332062864:
                              break;
                           default:
                              throw null;
                        }
                     }

                     String var2 = var10000;
                     return var0.getClass().getSimpleName() + var2;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s2omhbyb4g5p71","UDgyHH/2upvRryHYIuFyvmBreUH5sjZ2qOxH0m4DffY=",-6564875437612713537,2125552118383892239,-8363587479925882159,-7137782274963197833>()) {
                        case 1369320786:
                           return var0.getClass().getSimpleName();
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         } else {
            return var0.getClass().getSimpleName();
         }
      }
   }

   private enum a {
      SUCCESS,
      BLOCKED,
      FAILED;
   }
}
