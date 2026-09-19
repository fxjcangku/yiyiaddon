package com.yiyiaddon.e.d.c;

import java.util.Optional;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;

public final class a {
   private a() {
   }

   public static com.yiyiaddon.e.d.c.a.a a(Minecraft var0) {
      User var1 = var0.getUser();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s316rijy4xnvuz","gflfElEpEiVf02XkiC2x0irngN5OfBfZ/yQ6y03Y5dU=",-3399892582281115383,-8342515212987356397,-746689238666396531,-6554354974219953946>()) {
            case 1793386195:
               return com.yiyiaddon.e.d.c.a.a.UNKNOWN;
            default:
               throw null;
         }
      } else {
         UUID var2 = var1.getProfileId();
         if (var2 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s36l9sdp64db2w","UrkvujLGs/9fIYzV4afC4jo+BLwIzvKeBV2ZVjAGzVE=",-8609283000596425678,1680644952571494494,1637516528321618105,804425134778361486>()) {
               case 916761988:
                  return com.yiyiaddon.e.d.c.a.a.UNKNOWN;
               default:
                  throw null;
            }
         } else {
            boolean var3 = var1.getXuid()
               .filter(
                  var0x -> {
                     if (!var0x.isBlank()) {
                        switch ((int)com.yiyiaddon.m.b.a<"stszaudfclut3","+W/VNQ+wA9uKrK7qziUL5d0s33+FPvzmDMeDUXHhJLs=",-8938885535876665744,1941166293542414350,6661335291569949128,-6477543159464994985>()) {
                           case 1322083693:
                              switch ((int)com.yiyiaddon.m.b.a<"s3e07stm7xrgoc","uefeUxKnq5Pvs5WRjmduWUmoew1oqJRVfQ0AU2/GmTc=",-5680289733429672483,-8588546143846300637,-1121927235241168145,8221784928333287309>()) {
                                 case -1857641615:
                                    return true;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        switch ((int)com.yiyiaddon.m.b.a<"s5080uwaovs7u","aDusMtonXfShNC6R+0V6RwiB+W3rQRoy05cS+KvcO5w=",5543754520219933998,4657825439943382841,-4028420567448178466,2421289448350046572>()) {
                           case -1402971219:
                              return false;
                           default:
                              throw null;
                        }
                     }
                  }
               )
               .isPresent();
            boolean var4 = n(var1.getAccessToken());
            int var5 = var2.version();
            if (var5 == 3) {
               switch ((int)com.yiyiaddon.m.b.a<"s1et6imxd3e3q9","XIcRq7mahtf2aYn06sYTY9gSxCJdvlMem5f1E/yF9v8=",-6988882463119031384,3381286180171955368,-2971000736333482700,6382143791456492590>()) {
                  case 22590740:
                     return com.yiyiaddon.e.d.c.a.a.OFFLINE;
                  default:
                     throw null;
               }
            } else {
               if (var5 == 4) {
                  switch ((int)com.yiyiaddon.m.b.a<"s109n5ityy3ycf","dtIfDcm9zJghle+MQMGR37gwmvIQL45fvhyYLn6DfeU=",2309683041167682687,4774566774591890912,-6529559668233512037,4268074566639832018>()) {
                     case 713045879:
                        if (var3) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2t0ykez1ch9g2","ZSg5ciUoyWbULc+U06yQKdnn0B0dp+lePhMbxk/Sn0A=",-1843396916855608814,-1319503215835968825,-7105009559566273166,-268610115992390557>()) {
                              case -2059041355:
                                 if (var4) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3qitsx2pk6vs","xRJNDF78ySYD0rPqNIsBVDHEfeY7jzjFTHx+Ml1NXx4=",-6558394648752421082,6650007533756116625,303491086014395845,-6378750487698992752>()) {
                                       case 1260765765:
                                          return com.yiyiaddon.e.d.c.a.a.PREMIUM;
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

               return com.yiyiaddon.e.d.c.a.a.UNKNOWN;
            }
         }
      }
   }

   public static String a(Minecraft var0) {
      User var1 = var0.getUser();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sa2ltjzwcc3ln","KTtdKzGWAbQCzeEuLiKSbNM8v5w6WHjJpvoWu/Q9N6I=",-2210184888375455510,9135682120877695876,2556037187986453833,-9196290407761640271>()) {
            case -1861491931:
               return (String)com.yiyiaddon.m.b.a<"s229etqytnohmv","R9sxaTNrDx/uSWyEVeLEB7pdg8FV92G8IZAQ5LBwO3h+7FK4IKxmZI5EqUC0Zg==",9186552246402210842,8998417822084525043,2535783971313218045,8529651976142624533>();
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.d.c.a.a var2 = a(var0);
         UUID var3 = var1.getProfileId();
         UUID var10000;
         if (var0.player != null) {
            label89:
            switch ((int)com.yiyiaddon.m.b.a<"s2c7h5seqqyp06","YLhruARDOTBZ6so0NsboNqjzz9vYEZKz8Sgg+EZdKYw=",-3738908652435293702,-8514122595465575749,-4732970000094068306,505361667723813000>()) {
               case -1714443561:
                  var10000 = var0.player.getUUID();
                  switch ((int)com.yiyiaddon.m.b.a<"s78iqe76j60sd","aUBEmcI+RodOCcS0MqLM3KJ9ONSoArAny7+sN0gDi9o=",3782615402672543156,-4175042992195196542,5919327437943632265,3670613915623407927>()) {
                     case 326701257:
                        break label89;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = null;
            switch ((int)com.yiyiaddon.m.b.a<"sk055y5xho1kb","aFmIpFvXWjrcUhYZer6YxWRkIUH84cglMNFeRFNk8wo=",-7111917542680536630,5132348736495360559,8828252791895696326,3824040954424279311>()) {
               case -1477228925:
                  break;
               default:
                  throw null;
            }
         }

         UUID var4;
         var4 = var10000;
         label85:
         switch (var2) {
            case PREMIUM:
               var9 = (String)com.yiyiaddon.m.b.a<"s11zsm4vw3kldv","9eMszp6jRqPGWICv5KpMu/XIQ+pdy4/Y73b/VEZe+PmBXh4gZsBR8EvkYcg=",-2754692378354694535,-7019194639571199731,2957178625240334054,-2621973660080181536>();
               switch ((int)com.yiyiaddon.m.b.a<"s10d083ngemsc","1k6L/XEnGnRgLhEhD7BbE5/H+xMl3Ydi6Vmd4F3dSBY=",350955313358950084,-5208683760236087434,4021493170886228837,-5724338505908318766>()) {
                  case 2139864138:
                     break label85;
                  default:
                     throw null;
               }
            case OFFLINE:
               var9 = (String)com.yiyiaddon.m.b.a<"s2vb6a255x5v68","gpob2qjc0TmN2EMBYekfm8m3hI/E40m0X3NP2aPbXNA5fQbaSeg9QlI5RTM=",5338463216578668727,7419647000732711541,4839427370623985096,-6614217660178399220>();
               switch ((int)com.yiyiaddon.m.b.a<"s3k1hdzygo9241","/Um9tnBX004Y9GariS3eKtlNl1TC5fSfDYc0wTOIfvI=",-8254172535146098423,6614412834305479395,-3767199421030342496,1539514420853695820>()) {
                  case 1175758875:
                     break label85;
                  default:
                     throw null;
               }
            case UNKNOWN:
               var9 = (String)com.yiyiaddon.m.b.a<"s1zp37k3pcq6nm","gLUP+nFyInIpXc29C96na1K868ilo2p1jF3xfAhhFUTOdcMzaoIy5LOO0vY=",4679511513053487408,-901182320375089969,4570901583612260726,78367874213480854>();
               switch ((int)com.yiyiaddon.m.b.a<"s3ff6zi1teyiom","/N3CEVsDCYW9atpjzuabKct0T00PfFc1Pq8IlZWRAU4=",-1638649863693558091,8656843612604411228,5619517990119790381,-2262709915751832344>()) {
                  case 1049188594:
                     break label85;
                  default:
                     throw null;
               }
            default:
               throw new MatchException(null, null);
         }

         String var5 = var9;
         String var10;
         if (var3 != null) {
            label74:
            switch ((int)com.yiyiaddon.m.b.a<"s2b7tgl1vwotby","2n/IjwkzzkhnftpHSAqyFTttS3KwczI9UyKVnllgUjY=",4024691027988406596,-6838107333204845537,8183560086660078358,5523244184671474871>()) {
               case -1118798170:
                  var10 = var3.toString();
                  switch ((int)com.yiyiaddon.m.b.a<"s13bs3dmxnhrv1","I55fNkCw1vYJhSvxtrIjx2SE5KS++cwyMcMh9fNBuQ4=",1225439775459585598,6326513348827699643,-8927834390979120030,-5665400459112499021>()) {
                     case -954121161:
                        break label74;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10 = (String)com.yiyiaddon.m.b.a<"s29mt02p6112yq","ZDI8QOR9mXTN6Lmmha6fVZZEBVd2jUi+1p0y1fKYniw=",9190348871539604911,5487351898601988874,2907039987365479513,-2079992992381199004>();
            switch ((int)com.yiyiaddon.m.b.a<"s3lisjvj9sw2xa","V8BuAk7R+FaCRYwjkczpO4UzUO75m8N5sspoJRz6QaY=",3160671128531576756,2954819710205711836,-304603447432693039,1126573591283392202>()) {
               case 1594169279:
                  break;
               default:
                  throw null;
            }
         }

         String var6 = var10;
         String var11;
         if (var4 != null) {
            label68:
            switch ((int)com.yiyiaddon.m.b.a<"s25qohf2bnp0tz","KeTh0tFDxS3mm6qoKQZsd2T0xGgVA/xsWNKtE7lcfK8=",531513998334853353,4933071176576810355,632197724310042130,-647422477008283828>()) {
               case -1453297676:
                  var11 = var4.toString();
                  switch ((int)com.yiyiaddon.m.b.a<"s30ccoahztqtq0","x6MFgEX7Xjy1gOdYG29Lm0bQO/8/y2M8xmFIC0Oa3ro=",-4352130673828477183,-5677990401194877226,4438312124026672930,-265361535064649541>()) {
                     case -1522498890:
                        break label68;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var11 = (String)com.yiyiaddon.m.b.a<"sot642e285t2j","4z1oyqAZ7E0kmECg/BRqaGvtcEG6e7yqufxB91RLUlAj0heC5ZE=",6156018528593572627,-473851176844083932,748837660110562927,6590730767768567534>();
            switch ((int)com.yiyiaddon.m.b.a<"s31rpsj0q3yjjg","4ZMF80SMSm74svQfgrt8Z3hDsyw/GuFqClNsAqowaRk=",-1686412499221869633,-3634095420847971699,2404657384563050280,-8592284548282885274>()) {
               case 1199577830:
                  break;
               default:
                  throw null;
            }
         }

         String var7;
         label98: {
            var7 = var11;
            if (var3 != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s11ly1ts8aw4qy","i1sG9qwrboani1j00JhUPU6yI2j5jrPRanTaYmm7YQU=",3868499289604100551,709863482080663019,-111679029075071648,569804680957915374>()) {
                  case 968047892:
                     if (var4 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s18fn1s8odiegg","fVihSf08tfOMxyo2HMAm+vfOabGsDf0gEmfbiRSk2iI=",8613206220620568283,8008239586792736792,5242076477566007578,-6378194796280769717>()) {
                           case -327520970:
                              if (var3.equals(var4)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s18m7fhlaqme0t","nuI4WiT4R5QiaiTGE8iSX4cqoNU0p0BqU04QipA0mGA=",6856802200875725969,1572591218936702266,-3476377352247715763,-4698533812437336862>()) {
                                    case 1732865324:
                                       var12 = (String)com.yiyiaddon.m.b.a<"s2u2h1dtzs8ihp","MQCzit1F8/kP+3huEjlwVDOKggiBIOHAcvMOWq+ZaiE=",-8245804030701097215,-8466333633201498518,1187031124127675643,-4681797547377155738>();
                                       switch ((int)com.yiyiaddon.m.b.a<"s8mfvnmm2wzli","AfbbrNbee7fAMeSHatUzAfuyulavd5QHPSp/zpnjgwo=",5527140710672762775,-3151580515274427901,3266520692001415530,-7631393131765916676>()) {
                                          case 361479320:
                                             break label98;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 var12 = (String)com.yiyiaddon.m.b.a<"s2n3dscsh0vecj","A0gHDE8R1tUjnzxF7mNNCl8rF1RMgFSYl83C5dezDsJeAw==",792867467290555258,-6459107443302796024,-2421600036119149509,9012553209171617300>();
                                 switch ((int)com.yiyiaddon.m.b.a<"s280a0dn31mgmy","7vv+pMrqGsiU24sF8denyelW6mAp1kvYoRpK8R1wvXA=",-7668463884074638540,-6913195505100059784,-2307265033135986222,-3390975453650701817>()) {
                                    case -875437843:
                                       break label98;
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

            var12 = (String)com.yiyiaddon.m.b.a<"s7jeayhhnx7mf","X8fZB9Mt9AK2aD4TRdTy0m94lUr+aDgTGLYhYWPZk9+sHOgw",-6839555114608127022,394036837398845487,5155463689877960530,-5605400975460284200>();
            switch ((int)com.yiyiaddon.m.b.a<"s2iluz39716y5q","aJbvxVDqxTz08BZxqU/in+AeuU9yV4+2iPaPFpvpR+M=",827243479140630320,4974725843650188708,-2307251380831948842,-5570480777350949377>()) {
               case 1628864685:
                  break;
               default:
                  throw null;
            }
         }

         String var8 = var12;
         return var5 + var1.getName() + var6 + var7 + var8;
      }
   }

   public static String b(Minecraft var0) {
      User var1 = var0.getUser();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2v0b4r8o9pea0","uL27pWAr0f5grRXVBk8JfqvpmFKN2NCZCHjloKWu4RI=",139923941442817498,136030305998072641,3094097736805246265,2406181122074189721>()) {
            case -513091886:
               return (String)com.yiyiaddon.m.b.a<"s3m4zre230go1z","o3BtKs9KhphJ69Ts8htq+H+4fDA5AzrcVkSLq6HCQRbwR/r4B/E=",-7992976698396395211,5791555268433958328,-2066431837635130639,5344156111879137371>();
            default:
               throw null;
         }
      } else {
         Optional var2 = var1.getXuid();
         UUID var3 = var1.getProfileId();
         String var10000;
         if (var2.isPresent()) {
            label34:
            switch ((int)com.yiyiaddon.m.b.a<"s3n2vjkbhoaen2","sAxCncjsJWrRwbHuhX/T3Cl8oW/BfXR/VH2/yvsEO00=",-1516824200125455520,-5193753526019061287,2531066536318236633,4958016180052571328>()) {
               case -524311487:
                  var10000 = (String)com.yiyiaddon.m.b.a<"s35vya1p5b0dyv","nhRE7ABFP7DO7Z9FhQ5lm8+9vR0ad89wA6VUEwrp59o=",3949458223703679697,-1952637272871564517,-4876291328967031362,5316300276303906630>();
                  switch ((int)com.yiyiaddon.m.b.a<"s14skugdpcksma","Eko7cPFGLdZZ0OQs8/u+jGeFozZU9M8gfgt8t9++2FQ=",6313288074210899043,3038664451300352146,-4190725036661711075,5552205581950774130>()) {
                     case 1335911012:
                        break label34;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = (String)com.yiyiaddon.m.b.a<"s2eqnm8qharskj","k48ugmUNH8UpHaoJPOXW/5IUgdCFl9iazdJ2unvyLlk=",-1817923065170521425,6822492601295463844,-3825728080766869955,5596363242044646478>();
            switch ((int)com.yiyiaddon.m.b.a<"sa5468c6xtscm","kAs1Igtk8gTUAoPnqpmm3p1EBzfKUfMFEVY89TAy3L8=",2168179606530598879,-8224382915754399066,-3469832371694833419,-2848613765573683418>()) {
               case 16481665:
                  break;
               default:
                  throw null;
            }
         }

         if (var1.getClientId().isPresent()) {
            switch ((int)com.yiyiaddon.m.b.a<"s1irj617ebj6d7","WBkDJEPMFA5divcg7ZPmixhg+PQ/lypI2eYdq1WDEIU=",895336939185840776,-7226109238597920205,3599292127600794394,-3721408238731514489>()) {
               case -1299787718:
                  String var10001 = (String)com.yiyiaddon.m.b.a<"s35vya1p5b0dyv","nhRE7ABFP7DO7Z9FhQ5lm8+9vR0ad89wA6VUEwrp59o=",3949458223703679697,-1952637272871564517,-4876291328967031362,5316300276303906630>();
                  switch ((int)com.yiyiaddon.m.b.a<"s91qs0tsfmtyc","aSLcMpUo8BSiraE2oBSc3+6QEKQBsFY3GMVQoOJaL8Y=",1173756841854460592,6636389093535455368,-409909391714142244,-8828171884876937057>()) {
                     case 357547431:
                        return var10000 + var10001 + var1.getAccessToken().length() + var3;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var4 = (String)com.yiyiaddon.m.b.a<"s2eqnm8qharskj","k48ugmUNH8UpHaoJPOXW/5IUgdCFl9iazdJ2unvyLlk=",-1817923065170521425,6822492601295463844,-3825728080766869955,5596363242044646478>();
            switch ((int)com.yiyiaddon.m.b.a<"s1184fglauzla8","MxiwlG/HDJIQFgaS4AkgrBk0xlar268vCHZqeaKhRY0=",6120178553036553881,-7903897323207409726,-6515255184648108970,5544599098652297864>()) {
               case -2109778491:
                  return var10000 + var4 + var1.getAccessToken().length() + var3;
               default:
                  throw null;
            }
         }
      }
   }

   private static boolean n(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2u9193b0pamic","kjWoTuw02cxbBCMeQrpso2W7n22G/mDy/tmFsvSO0i0=",-601731611839160551,-2788661182044710031,-7501568549667728917,-3488972679959347021>()) {
            case 1946829856:
               if (!var0.isBlank()) {
                  if (!var0.equals(
                     (String)com.yiyiaddon.m.b.a<"s2rd4jz6jo87ht","ODwu5eoqS2Lr+Lm0WBSs8pjNM5dbOpxxle2blX3i",4865662055082662789,6737293543133507092,-4761502711567562771,-1915284455717581126>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"svl2ieba9zf5y","IsYCzqs4JDjIzlXgDgOTHTQaTTKBjD0HhdyG0chRKxY=",-8475019466594502157,-4752517762245468346,-2023507406200691934,-5371561229156774423>()) {
                        case -1654538749:
                           if (!var0.equalsIgnoreCase(
                              (String)com.yiyiaddon.m.b.a<"s2qrt56kbuwz6c","JWhrNmUthZPZbtg4JIgIw3GnrEiGFly+r/K/FznkAcGJK253",-4061621370656118331,-7058681846103473102,5566929515574498217,-4993792416761573939>()
                           )) {
                              if (var0.length() >= 16) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sqvyuoka4nwjp","mpek0a20qYo0PZCcACWkAHcaznYFTQlL5+43UEpGVd0=",-3112862103242343053,1771769448837315013,-1738019096239984421,5412392672289536846>()) {
                                    case 1287001883:
                                       switch ((int)com.yiyiaddon.m.b.a<"sfu263n61gzhj","v6F8aM+DR9E5AeSZWtXu66196x0I91G5UUuhE9r11nw=",4919518336549704992,94616416511417243,55953703915718820,-2593194452914783743>()) {
                                          case 897609456:
                                             return true;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 switch ((int)com.yiyiaddon.m.b.a<"s17tpqbjxua292","u7bzj9Ae/PobyDeVvNSEzoOMruwylVWUCtRFPp+zF3Y=",7949254667691954079,-4300288194698651511,-7788714082361751475,-4191508597233177649>()) {
                                    case -1760260542:
                                       return false;
                                    default:
                                       throw null;
                                 }
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s3pimmaez8p1ro","92+JtZAfFdohVPLryQELakiGLHV20mw9UMS7NyvGzoI=",-8631985572536636335,7623353007168499667,-8753298835290899769,6116230562096808886>()) {
                              case -1801411049:
                                 return false;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return false;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sr32x86c9zk5x","sviLkIMASbSHFSM5gn6wCYV5SnZfp7AxJw8HHbVQDg0=",-4832525394460114071,-5535140853223351905,-8188859323075847419,-348155863172490154>()) {
                     case 803604146:
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

   public enum a {
      PREMIUM,
      OFFLINE,
      UNKNOWN;
   }
}
