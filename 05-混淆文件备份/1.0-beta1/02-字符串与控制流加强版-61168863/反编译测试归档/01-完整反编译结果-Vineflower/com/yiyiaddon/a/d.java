package com.yiyiaddon.a;

import java.util.Arrays;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class d {
   public static final String h = ".";
   private static final int a = 1;
   public static final String i = "帮助";
   private static final Logger c = LoggerFactory.getLogger(
      (String)com.yiyiaddon.m.b.a<"s296pwtpchg0vx","D6GxuO0fVXcq8OnlKvRPUm0erpnmX/Td+5DGOUYhQnzHoiim2VU+ATiFwPOvgBhFf5gtXevE1lrowM6KXxI=",-3868826813601493052,8662552088955570882,-460359592279501109,2546894500027889490>()
   );
   private static boolean a;
   private static volatile String j = (String)com.yiyiaddon.m.b.a<"s2jf17ka2jfgld","gPALbmUovPbTFrHmmbnLVVtAKpZFl59aXWL6O0iV",-1269746454802327381,3804406166307633285,1602449119101239791,-7193591346621111329>();
   private static volatile String k;

   private d() {
   }

   public static String e() {
      String var0 = com.yiyiaddon.c.a.q;
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s249cmz3tqaio9","snO9sgrEbInFJvqTRbcDUjgE390srZlEQNfLVPLraB8=",-6574185135836647915,3677134588401958652,3325761858444467796,1944255230539091682>()) {
            case 1813393035:
               if (var0.equals(k)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2el4lnx8axgdy","T4FmrCRzhGkpldPF3doyGr1sDB8o/vdjtfxRiupis0E=",278041527296355049,7388158311517726215,2422165067494675599,-7142667499957762454>()) {
                     case 346544847:
                        return j;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      String var1 = a(var0);
      j = var1;
      k = var0;
      return var1;
   }

   public static String a(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1r5dmjczwffq6","9XBaG3FthcwJjmlkksQcmGZN2PKsE/uT2hEmqDjswVY=",1398423327957949907,7224381189690724489,-3811938773758211451,-6134656268548386518>()) {
            case -1823431928:
               return (String)com.yiyiaddon.m.b.a<"s2jf17ka2jfgld","gPALbmUovPbTFrHmmbnLVVtAKpZFl59aXWL6O0iV",-1269746454802327381,3804406166307633285,1602449119101239791,-7193591346621111329>();
            default:
               throw null;
         }
      } else {
         String var1 = var0.strip();
         if (var1.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s7rrsp8fxbyal","RiNd/XRNppNFPpwqXT/YEeLYEGThyk06UNtckb1DJIQ=",8087034637277677525,-8605913313259273895,-7836953920338243000,7691256940126987834>()) {
               case -1963089390:
                  return (String)com.yiyiaddon.m.b.a<"s2jf17ka2jfgld","gPALbmUovPbTFrHmmbnLVVtAKpZFl59aXWL6O0iV",-1269746454802327381,3804406166307633285,1602449119101239791,-7193591346621111329>();
               default:
                  throw null;
            }
         } else if (var1.charAt(0) == '/') {
            switch ((int)com.yiyiaddon.m.b.a<"s2gh040p6vmeyk","IR749xRpoVa41aW+taCJZvqMhMgYRfvlj3bem6Q4oCQ=",8646583757591980995,-1846707322923209312,-1950628097604989782,2163949085142675754>()) {
               case -51027117:
                  return (String)com.yiyiaddon.m.b.a<"s2jf17ka2jfgld","gPALbmUovPbTFrHmmbnLVVtAKpZFl59aXWL6O0iV",-1269746454802327381,3804406166307633285,1602449119101239791,-7193591346621111329>();
               default:
                  throw null;
            }
         } else if (var1.codePointCount(0, var1.length()) > 1) {
            switch ((int)com.yiyiaddon.m.b.a<"s360pfa9femomw","On2ovNFX+3Ntf9rQTQryQTp3Pw2kSMpxTVGXv41oLi8=",-7240791991011909148,-2830106234764936690,-9039758244953388773,3158029437419873687>()) {
               case -643007154:
                  return (String)com.yiyiaddon.m.b.a<"s2jf17ka2jfgld","gPALbmUovPbTFrHmmbnLVVtAKpZFl59aXWL6O0iV",-1269746454802327381,3804406166307633285,1602449119101239791,-7193591346621111329>();
               default:
                  throw null;
            }
         } else {
            int var2 = 0;
            switch ((int)com.yiyiaddon.m.b.a<"s2mv7o35lsyqg7","NyLGsNdVXq5TKV+yHrX5B2qDICr1ttOVD/8Sg/xT4+Q=",1518301254918403003,-3727042236972895160,-5350211068002644101,1934860754498913637>()) {
               case 1524562978:
                  while (var2 < var1.length()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1pa8ox9astq9w","L+ssB5dHoGQBI4HkQ1vujza7Mnp8dR8/GgzWJQ4Gx9s=",-4779433122614300368,1757935080239258472,6675122555163240476,3195333451967129477>()) {
                        case -1567543076:
                           char var3 = var1.charAt(var2);
                           if (var3 <= ' ') {
                              return (String)com.yiyiaddon.m.b.a<"s2jf17ka2jfgld","gPALbmUovPbTFrHmmbnLVVtAKpZFl59aXWL6O0iV",-1269746454802327381,3804406166307633285,1602449119101239791,-7193591346621111329>();
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"srb4t6ivqsq1y","By3/oZfnEADFPSD2OQVJDfA6+goCBpSx7S0ENGM9x7E=",-3516631851228190700,-6487819975727094102,-5198155553070230570,158756225287797345>()) {
                              case -1611739864:
                                 if (var3 == 167) {
                                    return (String)com.yiyiaddon.m.b.a<"s2jf17ka2jfgld","gPALbmUovPbTFrHmmbnLVVtAKpZFl59aXWL6O0iV",-1269746454802327381,3804406166307633285,1602449119101239791,-7193591346621111329>();
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s3encp6pbx8ayz","RzAX0mPkS81l+Wg1r6obdDggeMYC6yaM2Co3qQDdeMU=",-3409247274526334525,1608369064088480485,1797576322875420664,-9104735295561511062>()) {
                                    case 436420322:
                                       if (var3 == 127) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1wspxbjqlu0nk","yxmrqdfc0KXlje10/LH9WTwk6qbHMyDD9SNLDTAPUn8=",-7865586052448588867,978288166078832621,-1202093982362613991,452703083843995430>()) {
                                             case 1378858346:
                                                return (String)com.yiyiaddon.m.b.a<"s2jf17ka2jfgld","gPALbmUovPbTFrHmmbnLVVtAKpZFl59aXWL6O0iV",-1269746454802327381,3804406166307633285,1602449119101239791,-7193591346621111329>();
                                             default:
                                                throw null;
                                          }
                                       }

                                       var2++;
                                       switch ((int)com.yiyiaddon.m.b.a<"s3aoqwebimnzux","CrPBnnoWAARqEPM2ZnKOmO4Uu65wmf2Bwcyvp+V/GZU=",-4475437450047207035,8346582218671319002,-4643874665818230267,-2545976217700364070>()) {
                                          case 1654170563:
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

                  return var1;
               default:
                  throw null;
            }
         }
      }
   }

   public static void a() {
      if (a) {
         switch ((int)com.yiyiaddon.m.b.a<"s43leu3wcmqm7","sl2NpjqtZAbKsz+RWOfzJeYfL81d/np02R+eG0f+OGA=",-1715845081603495564,-8108756693320491090,3124386216728329792,3619939963788242267>()) {
            case -136172846:
               return;
            default:
               throw null;
         }
      } else {
         a = true;
         e.a(new f());
         e.a(new g());
         ClientSendMessageEvents.ALLOW_CHAT
            .register(
               var0 -> {
                  if (!c(var0)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1htr18xyw94w0","fzcy+l4QZLA3SvFMK35mzrnCzOt40GKypp5hc7t1eeQ=",-2084045339990650834,363144486243421311,7354195760164496843,8198731428173694357>()) {
                        case 1564861236:
                           switch ((int)com.yiyiaddon.m.b.a<"s3apegmok0yufj","Be94QMh0TZAHtY7UVLNHf0rj+KlN6VSNzsJgN3tIlrU=",-6225100748169619055,8458579994294983007,9006536079413263257,2790930567927535749>()) {
                              case 1555856272:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s3p31lpi9kor3q","+Ez5EgS/YbUDhNZjEVuqjPte1cLn1uye38lUoqFwkiU=",265345490042261603,-2817314183591845945,36726810022076119,-1088151132674469841>()) {
                        case -1004501036:
                           return false;
                        default:
                           throw null;
                     }
                  }
               }
            );
      }
   }

   public static boolean b(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sai0b2vpyulko","rBzNlXgfcxEz6GWSgbYVgYSKONiGikRQ6MepA+f6gjI=",2711728463156100947,-2411167208838892511,8776342859428988966,-2193312666556596965>()) {
            case -533348113:
               if (var0.startsWith(e())) {
                  switch ((int)com.yiyiaddon.m.b.a<"saluymj1aj3vu","lfED1lOZ0lb/ioV3X7tN0YK0x0o/1+az65P/Hp/rcMI=",-5554445263473813374,1654878393212707434,-8965085504660689350,-9191124608734488359>()) {
                     case 1264538310:
                        switch ((int)com.yiyiaddon.m.b.a<"s20gqy01rnezwd","uJbyDTa+i91KAm2H8MZ4GD44YniSScFZJzxGfExkHTg=",4857379631597553109,5386669022666878877,-3789544981709550754,-3228328714194729187>()) {
                           case -1292923282:
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

      switch ((int)com.yiyiaddon.m.b.a<"s2dewlp45bcf3a","oHsDk8guXgvpkmCIaQ/l0pdS3lO88QEBtXCLn76FF/I=",4680316017758736299,7648630684224636302,-6458978570032872666,1665070222948970821>()) {
         case 1129323533:
            return false;
         default:
            throw null;
      }
   }

   public static String b(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1jv8q2bgi3no3","KZhLr6iJr1StkyBcl6yHxAccwmg8QGpK8mvz0uKa7tI=",-5685984802261653120,8349650466386813454,2889268996683992350,40114917789565507>()) {
            case 361311707:
               if (!var0.isEmpty()) {
                  StringBuilder var1 = null;
                  int var2 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s1ofma0bvg1yvk","uyW24a++yZ6EL+ZOy3z341b3LAU28QIByMUAWH0T3Us=",7334963775315866781,-6767027054306108104,-4576720796394447259,7240261915279628532>()) {
                     case 746635381:
                        while (var2 < var0.length()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1rw0tqy5ld369","+7ceDwv92nCdEQN7sgUefUirH1uLK8F9l7hXXUlYNvA=",3758204744914360380,8822445972169564727,3053156145800427649,44563115329382156>()) {
                              case 1619274032:
                                 char var3 = var0.charAt(var2);
                                 if (var3 == ' ') {
                                    label76:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3vye0cupn8dav","TDUS8ZMQD+vcGL2Y/ER/jrIvT0D7cOE2MYft1Nr11R8=",2350970739781425748,-242552240712648088,-8657847456918668188,1347478332288942153>()) {
                                       case -358536871:
                                          switch ((int)com.yiyiaddon.m.b.a<"s3tgxen4uamsqj","92KOAioybHPRTtbmigeoW3qibLR9jtNzlg2lIk64UQk=",2244466995664174755,-2825697080068467683,-2048499944554103917,-3977712050034727959>()) {
                                             case -69393760:
                                                break label76;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    label112: {
                                       if (!Character.isWhitespace(var3)) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1c1teq9osr5gt","7L2RcQP120VuNABV+9kwOwohVegMHpFfumpqIojmMe0=",2052222323587944716,-5244994738232775560,1790002538561256130,-3827183235725303162>()) {
                                             case 2008811224:
                                                if (!Character.isSpaceChar(var3)) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"sxavrmj6kg4c4","9bqK3Hh2xPv+r9Nf/XLfqkXP16JfaV41ObxwRo4FrHs=",-6621050660240387592,2747053598480168511,-3079825388291865708,-4913832885835589345>()) {
                                                      case 739018769:
                                                         if (var3 != '\ufeff') {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s1uexlo1z47acy","nk55KEPhf0apkMVR0WxK7yW/v9VwpwjX5fqsTJg17GE=",2378002449260304772,-583173539851153706,3076410285859661773,-7400242146470758281>()) {
                                                               case -1722746593:
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s1umvo39b41om4","jlCZHiZjvkK8h9B7nJJc/tsVKfvmnvKBOvVbZy9on28=",1959172303203294382,-852928489674468933,-7823887546764148529,-7190697694262850472>()) {
                                                                     case -2064950147:
                                                                        break label112;
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

                                       if (var1 == null) {
                                          label64:
                                          switch ((int)com.yiyiaddon.m.b.a<"s132byr9up8601","WvKQZKuaN0V97sbsd6UBn8wUjWmDsUkfk/6+t3gbtTw=",8653453140855813706,8936238970058923170,-1171491429459539615,-3609758262603839543>()) {
                                             case 690451923:
                                                var1 = new StringBuilder(var0);
                                                switch ((int)com.yiyiaddon.m.b.a<"s2jwofo59v15xc","DUcMrI3hoFO8FbBPbbSDTHqHK8U62aPlPfg7SiEIpqQ=",-8784780123874973724,1016006237702179862,-419089517759733915,8776377017553391260>()) {
                                                   case -462821144:
                                                      break label64;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       var1.setCharAt(var2, ' ');
                                       switch ((int)com.yiyiaddon.m.b.a<"s2d7pxnrjdvr5p","dMLOG5ajcPZRAD0TGg9ijGSKyxyzwEtBRKS4q7x/i1o=",2059248146464493208,-778887019561047058,7726344111012889628,5178546747666119134>()) {
                                          case 1373463781:
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }
                                 }

                                 var2++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1o4wfrr21xj2f","qTRWwHwdTlyxBtzegnO6dPRejYWNB1AfuPvpg3d/d9I=",-3345102752891646373,8724273260985127092,146288309529775420,-7992536416746678411>()) {
                                    case 1358384923:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (var1 == null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1tx1axttr8w1a","B8TWul7Z43zoTzaxR0UoKbh/+jN9R3fYzbYs7W7LD/o=",5611227745677139334,-7336248079390949630,-540566158496139049,1129187325046932112>()) {
                              case 1351411924:
                                 switch ((int)com.yiyiaddon.m.b.a<"slj0plrilapx6","6rXmEiolSF0KwwMJw0OE4VQTPOHbMNPKIH3m8ZZlVAM=",-2742158583767939906,7253740594474951671,5864424803240017746,130458000003610813>()) {
                                    case 352010817:
                                       return var0;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           String var10000 = var1.toString();
                           switch ((int)com.yiyiaddon.m.b.a<"sb5u4wbpnt2k1","7KR4qb6ClTRf6Rpwf5RTDrEslwmma+MbHG4Q9Ly3vjA=",-6707459636662104053,8894955434790437561,8965312640636401338,-7551769320504380732>()) {
                              case 1686055197:
                                 return var10000;
                              default:
                                 throw null;
                           }
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sbjjtpjlcj0bc","jf5L7q4YjPgn4UKlemxhjy9Lc4QFDxbFhDuwRXVm5M0=",604002942395821741,5646165780332816778,5011926288148987681,2508593450794515263>()) {
                     case -270983071:
                        return var0;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return var0;
      }
   }

   public static boolean c(String var0) {
      if (!b(var0)) {
         return false;
      }

      String var1 = e();
      String var2 = b(var0.substring(var1.length())).strip();
      if (var2.isEmpty()) {
         com.yiyiaddon.d.c.a(
            (String)com.yiyiaddon.m.b.a<"s2mcv2nrfzigzr","pfzAUoVhqT5ZrTXvAaDRIIm/BoiMsxtCNu2xRWCBDPs=",2327101572085924087,404179702671087414,-3939457246587418352,-716793428002117940>(),
            e.b() + var1
         );
         return true;
      }

      String[] var3 = var2.split(
         (String)com.yiyiaddon.m.b.a<"s1f41cjmp2pv9k","RZQsBot6W78KTFpg3rl2H+43Ua8lkgz2CQnZyD1Xe3kOpQ==",6301874081040216968,4995663508970545033,8874996245987306774,-980785867273089881>()
      );
      a var4 = e.a(var3[0]);
      if (var4 == null) {
         com.yiyiaddon.d.c.a(
            (String)com.yiyiaddon.m.b.a<"s2mcv2nrfzigzr","pfzAUoVhqT5ZrTXvAaDRIIm/BoiMsxtCNu2xRWCBDPs=",2327101572085924087,404179702671087414,-3939457246587418352,-716793428002117940>(),
            var3[0] + var1
         );
         return true;
      }

      c var5 = var4.a(Arrays.copyOfRange(var3, 1, var3.length));

      try {
         var4.a(var5);
      } catch (Throwable var7) {
         c.error(
            (String)com.yiyiaddon.m.b.a<"s1dy5kbuzdb9cd","SzFHBIwPP//hG+KzoeMB1Rxx7Hbaqo/Mk+22L3XjgYTToTKi68g4VZifNggq2pq1",-8956747644567948264,5391643970143375357,52286150079632590,-8314127264307197746>(),
            var4.a(),
            var7
         );
         com.yiyiaddon.d.c.a(
            (String)com.yiyiaddon.m.b.a<"s2mcv2nrfzigzr","pfzAUoVhqT5ZrTXvAaDRIIm/BoiMsxtCNu2xRWCBDPs=",2327101572085924087,404179702671087414,-3939457246587418352,-716793428002117940>(),
            var1
               + var4.a()
               + var7.getClass().getSimpleName()
               + (
                  var7.getMessage() == null
                     ? (String)com.yiyiaddon.m.b.a<"s3bkv89zvsegmg","93tuxYfkSAaNAZCAKG2ceOeRIRByhIiCwbFw1A==",5711993363589196122,4690730986769469751,-2426152875888810161,3318063434924036656>()
                     : var7.getMessage() + ""
               )
         );
      }

      return true;
   }
}
