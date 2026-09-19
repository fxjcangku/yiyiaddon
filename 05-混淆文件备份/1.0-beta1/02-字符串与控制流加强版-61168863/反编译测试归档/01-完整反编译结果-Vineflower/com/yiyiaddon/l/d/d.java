package com.yiyiaddon.l.d;

import com.yiyiaddon.l.j.h;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;

public final class d {
   public static final String Fn = "action.clickgui";
   public static final String Fo = "module.";
   private static final int tu = -1000;
   private static final Map<String, Integer> be = new LinkedHashMap<>();
   private static final Map<String, h> bf = new LinkedHashMap<>();
   private static final Map<String, Boolean> bg = new LinkedHashMap<>();
   private static final int tv = -1;
   private static final Set<String> aU = new HashSet<>();
   private static volatile String[] al;
   private static e a;
   private static Consumer<String> m;
   private static String Fp = (String)com.yiyiaddon.m.b.a<"s2xj3d0ffxe1fw","EnDN8p7fBL2gORGmh46fNpmdIBoovSSKPGf9gA==",6603009097229743253,3138365876805588582,6385159847024654180,3809163762203578385>();
   private static boolean h;

   private d() {
   }

   public static void a(e var0, Consumer<String> var1) {
      a = var0;
      m = var1;
      jT();
   }

   public static void jO() {
      if (h) {
         switch ((int)com.yiyiaddon.m.b.a<"s2l9srn9oof562","t8TKgaSAq9ebxaECd43UgmzzKMgjNdpliGIetvpV7T4=",1260040604035891776,5643525316746181632,-8291860918618383267,8698158772504072352>()) {
            case -315558759:
               return;
            default:
               throw null;
         }
      } else {
         h = true;
         jP();
      }
   }

   public static void j(Minecraft var0) {
      jO();
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2h5t6x987to1u","up1fIFttpeqZLw3d3mEpDZncJqBUFHzPeVvjUku9pCg=",-1429666145945496391,-1162944833361380563,-5461176170610709102,-7898712076174270444>()) {
            case -544437896:
               return;
            default:
               throw null;
         }
      } else {
         String[] var1 = e();
         int var2 = var1.length;
         int var3 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s3i6r8d5pmov7f","dY3TU9NPb7UTBKYEj/Cu0rJffSTK/maq9Kvjbt0yPBk=",6436160736836860270,7950038245847106687,6368396287577880236,-4351786273788938539>()) {
            case 1241845005:
               while (var3 < var2) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ow54e0iu1v3l","eXjy8QloEXlcZphTlRbjnwoO/9LYyR2ndc97pmF27zQ=",7760188228328245368,-4078070082534750603,4760763219273560226,-8010130925354624473>()) {
                     case -764529879:
                        String var4 = var1[var3];
                        Integer var5 = b(var4);
                        if (var5 == null) {
                           label52:
                           switch ((int)com.yiyiaddon.m.b.a<"shxppr0e388xq","VW6Z28qi0PrQxrMQuyVZADLvWYFxoLqJEdqb/2JD+qA=",-9068448462882963785,-5833306663537630181,8056488993567206834,7898287367244442214>()) {
                              case -952673557:
                                 switch ((int)com.yiyiaddon.m.b.a<"s1xe1ry5kbelud","vSz2Nm/vSICVAq41mDjhU7pRT5pSao85sS00/0Y2KDc=",3952312212502775044,189783472920754906,5903766171283015205,2113891975789788397>()) {
                                    case 1183749147:
                                       break label52;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           boolean var6 = d(var0, var5);
                           boolean var7 = bg.getOrDefault(var4, false);
                           bg.put(var4, var6);
                           if (var6) {
                              switch ((int)com.yiyiaddon.m.b.a<"s13hryxol6nc72","7XE/cvUIck6TZpNk5yZ0uxKIRDOSWV86JeyLUm/kbxA=",2423073014943074555,-8427293754095317689,3337516591744613222,6099347403828414163>()) {
                                 case 793596920:
                                    if (!var7) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s37evjcku633hy","L4pTHv0m2yzm4UPGiaHvSoAIArrOATsWy27oFiCvy5w=",-6458921398330581390,8001665747711443498,7894859000474161448,-875518603368925842>()) {
                                          case -1255114224:
                                             if (var0.screen == null) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s2yrz5nuh5gx3h","hIbN//OK1IZ5wPAHbb5DfYNVwyAxMEgFQrmunf25pbg=",-2868157149670219475,-3784676208020335488,2345922357885189301,-2231805216707695909>()) {
                                                   case 1522802450:
                                                      if (!fX()) {
                                                         label48:
                                                         switch ((int)com.yiyiaddon.m.b.a<"s32am4kegg2ug1","hz+HZslQCqd1m/Hioum7uy3TZ9lBgJ1MIiN2yjY02YQ=",1762385260800618149,-5467711366148628406,6811799821521989832,-7258586269220384652>()) {
                                                            case -940466114:
                                                               b(var0, var4);
                                                               switch ((int)com.yiyiaddon.m.b.a<"s13ttuker18le3","LNJ5TMK4XR1ojbIdjdt8qKVbRqnEhChu4a40XXG2Lh0=",7030845926066140280,-6435639871509728380,-673830456393453839,4760934860533540481>()) {
                                                                  case -619158776:
                                                                     break label48;
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
                                    break;
                                 default:
                                    throw null;
                              }
                           }
                        }

                        var3++;
                        switch ((int)com.yiyiaddon.m.b.a<"s2onkq5qw1j7q0","vufMLID6tb707znscbs5HvU8GMtvDkspmM1B3ZxEggo=",3145848214691090212,-1711096699671817917,8862286359893139708,-8925943554814907867>()) {
                           case 1374446905:
                              continue;
                           default:
                              throw null;
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

   public static boolean aP(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1q2r577sq9f73","nT7j/yxqW6yWrxM6EARhjsMi1XiguyhXcHLzDwPxXvk=",-9073163851629423071,-5231837577540200527,6565824220047841577,-5592505376232138841>()) {
            case -797317850:
               if (!var0.isBlank()) {
                  Fp = var0;
                  return true;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sqxiffybbmmxo","pNUtV6tPLf/JHq1AA3ISAF65RW3/9sonK1l0IjBsnKI=",-8648646509784271161,-5527208521097771295,-5223880228028055015,3478285461191798402>()) {
                     case 695519500:
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

   public static boolean w(int var0) {
      if (!fX()) {
         switch ((int)com.yiyiaddon.m.b.a<"s18kxkowlmgan","MWFxRr020RqIHLiFWxHvqnUnPFvUG1GTDM0bKoIJLYU=",-2288750096263370663,-2820553626006120667,8371732274336022074,-6594492866119155303>()) {
            case -1927916419:
               return false;
            default:
               throw null;
         }
      } else {
         String var1 = Fp;
         Fp = (String)com.yiyiaddon.m.b.a<"s2xj3d0ffxe1fw","EnDN8p7fBL2gORGmh46fNpmdIBoovSSKPGf9gA==",6603009097229743253,3138365876805588582,6385159847024654180,3809163762203578385>();
         if (var0 == -1) {
            switch ((int)com.yiyiaddon.m.b.a<"s5m2qf7g9s6p2","D2ODYVNDLMcUebklt8qS52sbhej2SDSASEXFaW75W5w=",7421870490647487981,-6310799649474808750,-5759733561097788627,-513973124275866136>()) {
               case -750642682:
                  return true;
               default:
                  throw null;
            }
         } else {
            aU.remove(var1);
            if (aS(var1)) {
               label35:
               switch ((int)com.yiyiaddon.m.b.a<"sidguoudhaga","3E2QRKx6yy/rs+gEK/jC+DQrH6rRG9F7JeUCSfu/cZI=",1585563601945439720,-4624719440078717773,164203177463268388,-7031537614546021154>()) {
                  case 146255867:
                     e var2 = a;
                     if (var2 != null) {
                        label32:
                        switch ((int)com.yiyiaddon.m.b.a<"s1glflikpzaqhu","agZCq/qOfTsDFmOpK3ujIaDSPgPwsYon7hjNp99lLgg=",6651023379142783023,4345777095183576704,1390418931780456652,-5573669363469429892>()) {
                           case 2100274703:
                              var2.b(var1, var0);
                              switch ((int)com.yiyiaddon.m.b.a<"s3epbt9yj55xl0","PNwTEpNzAtkBI/ineer7AehG2JrgSXmAHwBSP7aDtx0=",-5429429696302992745,3791771308619441600,4305758610564220915,-2354543123552166447>()) {
                                 case -311181957:
                                    break label32;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"skoxh20x16n4","uqaZ4bsNU4x3j3wV21KFHJVK3PRneVDhmZBg0jnDFsE=",-3584013322375633998,1532582004478398087,4934034457324090746,7493656598576974712>()) {
                        case 1037301980:
                           break label35;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               be.put(var1, var0);
               jR();
               switch ((int)com.yiyiaddon.m.b.a<"s1ksrji4bputp4","Btzb316vrc6LPVDn3zAG5DbTP35lIf4L8oqkSKM6K8E=",8666329680184974076,8905865765105667376,-1280621645209136091,-2328163518873737644>()) {
                  case -1009867805:
                     break;
                  default:
                     throw null;
               }
            }

            bg.put(var1, true);
            jT();
            return true;
         }
      }
   }

   public static boolean x(int var0) {
      if (fX()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3useebi2uhpdz","AYi0Dwnfd6+dGgb7tt88RKjxpyXZk1eMR7UBgKIBT5I=",3455450239510685637,-8918058835010697116,7337648491812842131,8020704628077317681>()) {
            case 666623196:
               if (var0 >= 0) {
                  return w(-1000 - var0);
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2kom2bf8gfitb","8Irv3Tb/J0ooOu+86SECG1EQa2kvnH7M1CvnckM9YEI=",-336141117673205494,-7993698005067170299,-9152533816600863053,-1959026432411190761>()) {
                     case 1717454539:
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

   public static boolean aQ(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"srha92drhvrfc","3giBmDeWQMYoAfepvFWK/GBh3HRrS9ZzdzxzRmu92VA=",-4972646965742148608,-9022473297965128337,7489649535637364030,5796998770957032301>()) {
            case -813615234:
               if (!var0.isBlank()) {
                  if (var0.equals(Fp)) {
                     label34:
                     switch ((int)com.yiyiaddon.m.b.a<"s3kvrnzo0880mb","Neq7O8uD/4m5fCTq6ipguMyG8yvw33izqYG+/wGQbZ8=",-2990311359323519537,1023838563215896614,547002950619933461,-7411161136818167177>()) {
                        case 1111120589:
                           Fp = (String)com.yiyiaddon.m.b.a<"s2xj3d0ffxe1fw","EnDN8p7fBL2gORGmh46fNpmdIBoovSSKPGf9gA==",6603009097229743253,3138365876805588582,6385159847024654180,3809163762203578385>();
                           switch ((int)com.yiyiaddon.m.b.a<"s2pallhhl6uv7","89W1+2c4qDpwYjZahlc9Zg8FClh58RemuSeU7UGgo1E=",6419788902742670326,-4547636370651995404,7311257266074543667,334747515313325165>()) {
                              case -863784558:
                                 break label34;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (aS(var0)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3cglqz2pozs1l","2//1NMb0/gX0aLlwWriAoxJgBMlmVmVWd1glxlYdcJs=",2282678956201037123,-2927233368978367072,-1438318711072368920,8260661245390872238>()) {
                        case -775131401:
                           e var1 = a;
                           if (var1 == null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1v1nistwfnsud","v/7frv/kzKxSH9zd1Sux+HxIfDEgzUMICVa4jBJdEII=",6997042447306931457,-1958603458837880997,3245229902828679333,2952333216409552061>()) {
                                 case 1115740897:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           var1.j(var0);
                           bg.remove(var0);
                           jT();
                           return true;
                        default:
                           throw null;
                     }
                  }

                  be.remove(var0);
                  bg.remove(var0);
                  aU.add(var0);
                  jR();
                  jT();
                  return true;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2fzq6d8n2wi6r","FMI2a+EzDgvKwiF6iVmiHnxSto+fTv2iEx+sOBhPJ8U=",6411928198834971755,9195782641960732620,-6937198530515199528,4172374663883993972>()) {
                     case -1504168982:
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

   public static void y(boolean var0) {
      be.clear();
      bg.clear();
      aU.clear();
      Fp = (String)com.yiyiaddon.m.b.a<"s2xj3d0ffxe1fw","EnDN8p7fBL2gORGmh46fNpmdIBoovSSKPGf9gA==",6603009097229743253,3138365876805588582,6385159847024654180,3809163762203578385>();
      jQ();
      jS();
      if (var0) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"s1to11kadh43ie","EbXk5IfcNLUn2N3XlghxGZvETVbc9WERCHGH/fjUTDU=",4402383198171004862,6619929090213572736,-8189972003337420289,3031236261687729411>()) {
            case -506840246:
               com.yiyiaddon.c.a.e();
               switch ((int)com.yiyiaddon.m.b.a<"s2mprex29nztpo","NzT1YU5uxsBPuK2v2Vh7BuTGNK4aeoMLMt6rBRdawEY=",-3561662469204454194,460925973969802236,1593552620927993838,5833048647428526635>()) {
                  case 354318119:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      jT();
   }

   public static boolean aA(String var0) {
      if (b(var0) != null) {
         switch ((int)com.yiyiaddon.m.b.a<"swtpgwfoyk0k9","ZoCxdjHbMtXrNHyDZ/wId2dBrgDtCIe5NI2WGGx/OCc=",-1800225788408328948,-5984178811833065845,454591045068486940,5730591649059367609>()) {
            case -645542212:
               switch ((int)com.yiyiaddon.m.b.a<"sjvtbtlz87mdv","MobU72594veIAeh3tReqLHaA/N5ewgCgdCZyB6Z7eck=",-2687039837513467729,4701685466952694935,-5675855698122435768,-7196308424657617881>()) {
                  case 1167548922:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s3h3wlgs6db9n4","jEGXnzJ97r1fcC//xoc37mUS+RiqcM/iWr9asgXCP90=",-7095705389212239794,-3525962774138212299,-5934787435022014943,-5244166317655120647>()) {
            case 327322153:
               return false;
            default:
               throw null;
         }
      }
   }

   public static boolean fX() {
      if (!Fp.isBlank()) {
         switch ((int)com.yiyiaddon.m.b.a<"swwcdhfnybnle","7oVZ8FBdTrGqoZHVouoE8dvCbgcDZ5LwnGlN6yddmfs=",5147189421786929280,4399585030766580247,5248419194436614725,2355162567473294144>()) {
            case 698789339:
               switch ((int)com.yiyiaddon.m.b.a<"sbjrrazjeuc1l","HR8Pj6pq80grf6vhvulrlJKghE8lP2qzOz5iBGK+mx4=",-7212857747372400146,-1874098794621100647,-8549264789792143868,-5230244458674706600>()) {
                  case 255929520:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"sl0tw4mahxv8","5W62Z6EHL0vwUEhkfejGrblqA2zmfxcq542xPa4qtM0=",-3218073616916247098,-3705461136942841021,-6796636344874982962,-2650392252111823852>()) {
            case 1276571472:
               return false;
            default:
               throw null;
         }
      }
   }

   public static boolean aR(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"surwcjkf836du","KBozw18Z4vjJjgCUU6XgbtehR42AT6tvEsCBU5zLwk0=",-1062403999269431014,8606163321814754287,-5242250080295958491,2497859766418562138>()) {
            case 1965815366:
               if (var0.equals(Fp)) {
                  switch ((int)com.yiyiaddon.m.b.a<"sal02729r254q","UR4LhmJvF4VZV4ggB2v7JTBla45DHJGxmGkXv0s/F0k=",-909041688992897018,7698645025052067719,7533166347996444927,5698697367995528691>()) {
                     case 1724822677:
                        switch ((int)com.yiyiaddon.m.b.a<"s1wf2krg43wyfc","tBBpczE/eRntOsX/WeyGHRE4TAhFDNVOLm17i8UwxwE=",-815446866022544128,-433406476516909318,-3756786217014367669,5068570663243586108>()) {
                           case -1517528208:
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

      switch ((int)com.yiyiaddon.m.b.a<"s2jl9jj4kvuvkn","Ag92kQJvxhMRTmrMkcKhN/VsPOe1iJmZljcTnO1maGo=",-5461384937588753731,2667611967454332174,4783006733900354497,-8459712163243503421>()) {
         case -2134672438:
            return false;
         default:
            throw null;
      }
   }

   public static String ck(String var0) {
      Integer var1 = b(var0);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s27a4fwl367a9","FCHhGWbw8j0+4lD/Qnq4s4l1EAoYJu9lU4PLUaVaZGk=",6627119972228837441,2064666214928389845,3657365352245617405,5563457661827262182>()) {
            case -234616846:
               return (String)com.yiyiaddon.m.b.a<"s2xj3d0ffxe1fw","EnDN8p7fBL2gORGmh46fNpmdIBoovSSKPGf9gA==",6603009097229743253,3138365876805588582,6385159847024654180,3809163762203578385>();
            default:
               throw null;
         }
      } else {
         return i(var1);
      }
   }

   public static void a(h var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sptxi51lvad4x","bwZGg2qiG7O8kA5KZFeyx86ubMnVGSOSlOcGX06bpQA=",3819400818305983765,-3164729900343741396,8746803700900056195,7361634526133380064>()) {
            case -1722666459:
               if (var0.gE()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2xfy3uhs4hset","FV6dgsTL5z1NNrhBwShHzgRvqCzN3E9nHjQI4UjJcww=",4198421928700839322,6182568536667247516,-422386564906504277,-6305980789466986744>()) {
                     case 2128100397:
                        if (!var0.gF()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s35u0uxmz8tdjp","09PG32D5PuaPBxaS8xqD2H5Z5Ybftx+9yPZvsgezC4Y=",-4330152773848979274,9171805385161988137,4272336483069803269,2044636348888896986>()) {
                              case -1692401407:
                                 if (!var0.gS().isBlank()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sq5blzws36ier","2CprjYH2cJX65AYAezNH5ykpD3Ch3hvJjdmLZMKTPo0=",-7825333208490047611,-3738966955043864400,-7547327137435383745,4355701270611333891>()) {
                                       case 362049399:
                                          bf.put(var0.gS(), var0);
                                          switch ((int)com.yiyiaddon.m.b.a<"s3doim17u3gx3p","RgMrPSGRC7Qz+9y4dFX0GXV+2QGpSi/60IgwPm7kH8Y=",3885402869467274645,-5119905660870024674,5081712059087781752,-3250282034876106028>()) {
                                             case 1971574379:
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
   }

   public static boolean a(Minecraft var0, String var1) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2d297sxigriph","i1UBpGL37bolv5BCoxwz4Nl7y+SO650oUod3shNqdCA=",1932637428236877095,1758379447467198515,4627397493980602688,-1673371148254512664>()) {
            case 782261851:
               if (var1 != null) {
                  Integer var2 = b(var1);
                  if (var2 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"sg8gxk3i24efj","qJFC8EkDElkt5UX/bDqcqCTHIK58wFx4mo4KKTA4jaw=",-5369239802096718670,-3890674139244465757,-7357891270505278349,-2712497655151460859>()) {
                        case -197195537:
                           if (d(var0, var2)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2i1aajo3giiux","303NMnnx9+N8MAgSF1snmBqgycIqnBdrLGwejDts6hY=",-6177489536173596004,-7216279224000443696,8224774936379624657,1213393618638622847>()) {
                                 case -1785440690:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3c7antula04q4","r4jqu6IYd9e9mf7/4ihPMSoFBLicmBp+uVOEpkffbeA=",2597111126606802758,-1715360351093177938,-6530984774553588848,9051227996059306137>()) {
                                       case 135814841:
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

                  switch ((int)com.yiyiaddon.m.b.a<"s2okob39ll9na9","K84fMQ3hOlkQ6ys6GpzzxRqv+L4LwP5cE3hT3d9dpmw=",5787741550817612399,2491536047279430064,5836685699364864964,1981190470842437165>()) {
                     case 1918374950:
                        return false;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s10i8i6kq0w68b","JMElr7RUPOsEhCgrWfdEYUsP7OFkHKXfhARSbyQiGf8=",-5981604736554788176,-1626503660840681156,4982347544115395894,4432739491594436764>()) {
                     case -1196956040:
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

   private static boolean aS(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2962814032tp9","yjrH9gOHXoW6Of/D7aKNwaoazLB4l+KP0KTptUtUkFo=",-8159363468472009362,9056517936833274222,-1887698195898507374,1383891460990015880>()) {
            case -137808160:
               if (var0.startsWith(
                  (String)com.yiyiaddon.m.b.a<"s26lmuvl0xuc1f","UZPjqj1Cu3TBBIifLp21CZ1kVrJVxyVIa3nJRrKLC5mFUwWIdLwjR2im",-8221920458264727388,-3772731646798496398,-4297324008243730264,1591056853493018128>()
               )) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1r4kzx4cccutu","qj3u9NWp1ttLlfJ/W+YttZOydqmv5JTV2MlNFx0ZYgA=",4923344549868912978,-3559165004196027423,2064904482716152875,-8419998786350898509>()) {
                     case 1401589075:
                        switch ((int)com.yiyiaddon.m.b.a<"si8nr6dpybvrk","uNf3OVKdqJjgdmG1A5r7SZWvwRwrhaZm6Gwsb2ur7LA=",997834485385447148,-3842860735183187114,-8949961068510540329,8313586181713811701>()) {
                           case 1003647269:
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

      switch ((int)com.yiyiaddon.m.b.a<"s2o6ap8cyydzmi","S+DQENeYLkH+eWfH1t4H41xdqO5dY2DTSckyCMLl/vE=",-4238108344169903324,-7530486833341733049,-5468951396473783996,-2901681994448630660>()) {
         case -469393392:
            return false;
         default:
            throw null;
      }
   }

   private static Integer b(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s21beenmmad0aq","eeWfAnxPkxZmtYv6MVa1X6+gKaBRhsecGRdfeopROwg=",8846346430033992941,2246737991598688233,-6796725134999403772,-4726217586343548573>()) {
            case 2011810525:
               if (!var0.isBlank()) {
                  if (aS(var0)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s23g6cbgi4vfu6","vCHkXpBpuNVCeEHq83sIaWx9UsJ29KjcgLy3XunalH8=",-6254504153504727315,-4045591861237689170,-913503090064429566,4251586915547597654>()) {
                        case -859658243:
                           e var1 = a;
                           if (var1 == null) {
                              switch ((int)com.yiyiaddon.m.b.a<"sl422e6ywkdq5","neZg/inYIQ44me+NLMLRu4/dJd2+DrQi7S7dTXF9z+U=",575340190219442274,-8923384600438143620,1009781389482315835,8765333158528327955>()) {
                                 case 1433043763:
                                    switch ((int)com.yiyiaddon.m.b.a<"spj7475g0q5kk","3CrZNqdl0XhYCcChWUu8bpwnTphkY6cz71zwVdh2uhM=",-4699396639038503929,-3335990283543743135,3623948083419065955,-3236735275903107846>()) {
                                       case -738001179:
                                          return null;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              Integer var10000 = var1.b(var0);
                              switch ((int)com.yiyiaddon.m.b.a<"s1bk290y8idqxz","c6QxU4cM6mTc2pIoLN685TVCd8XZG6aP5OO3jc9cJJw=",-3978513141014166026,3043266883372140380,-1001301589955834176,5771366602914049556>()) {
                                 case 545438449:
                                    return var10000;
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  }

                  return be.get(var0);
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2v78vjl6putxd","Hrs36E7AUtGWmxjM84lv49HtjP4EyQ/UjGWI026gWOU=",-7657427389061910296,4073785790120138277,3347168085379399806,-3936727694406448559>()) {
                     case 1386546735:
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

   private static String i(int var0) {
      return c.h(var0);
   }

   private static boolean d(Minecraft var0, int var1) {
      return c.a(var0, var1);
   }

   private static void b(Minecraft var0, String var1) {
      if ((String)com.yiyiaddon.m.b.a<"s3cgearkcb9e3u","7DCqnd647UiRiRGxH8X47YxsLFFuSjRoJR8s0wtjatCvO8JstjB9RAOqcD51pm9sn0jBHqbwJTjg3Q==",8252277016263031040,-7737720083643385278,1483200245173860543,7791196215564542833>()
         .equals(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s89lai2rr62cg","3J2O7QkrIYKAnfFtAnqK4TogvkODixFTb5ZCVb1cFA4=",-5481833144484155008,-6850273055573784137,8466528965040918247,5294119282137021245>()) {
            case 1125311909:
               var0.setScreen(new com.yiyiaddon.l.h.a(null));
               return;
            default:
               throw null;
         }
      } else if (aS(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2vvxjkqunz3gl","6uYABU4zLuNXd1+HQ7WHx5rsj7cbF5c84/Y1hpOpaiU=",4488269016616193125,-9131633364325163626,-2518054071140094785,-7544244849433957362>()) {
            case 1031976692:
               Consumer var3 = m;
               if (var3 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"sghjzompxrs56","SvVMxTdBETHBE/9yZ2b9Et6eKpPCWDT88WD34+/C16g=",-7023095692588400285,-3017484667111091953,-5441705018557489659,9010692632564139953>()) {
                     case -1521305459:
                        var3.accept(
                           var1.substring(
                              (String)com.yiyiaddon.m.b.a<"s26lmuvl0xuc1f","UZPjqj1Cu3TBBIifLp21CZ1kVrJVxyVIa3nJRrKLC5mFUwWIdLwjR2im",-8221920458264727388,-3772731646798496398,-4297324008243730264,1591056853493018128>()
                                 .length()
                           )
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"soit4s2b6jbfd","pT6dN/2BVkwx2K1kJ1p+Pv+zhOFkYTomvlUxRBYkQZc=",-2893344429696279758,1934303723933155323,6388464923741414638,3139759953662440203>()) {
                           case -1307415738:
                              return;
                           default:
                              throw null;
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
         h var2 = bf.get(var1);
         if (var2 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1wbwhtwznzb66","T4sZDCMf9+QwW+ELKLXU9tKtW5ixyUPQVxljvwqNZH4=",721495428122263904,6166706554317777314,-7917628636439026968,7174989245158850561>()) {
               case -1653367258:
                  var2.kZ();
                  switch ((int)com.yiyiaddon.m.b.a<"s3b2r2qr92upu1","jcGrTLG4fBSXh7IXhE00fVhzUMPTeloaRXUOAcm1/jk=",6319040267330537213,8839201924886697556,-4789512304551213478,-5597321971558818524>()) {
                     case -1810763970:
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

   private static void jP() {
      String var0 = com.yiyiaddon.c.a.p;
      if (var0 != null && !var0.isBlank()) {
         for (String var4 : var0.split(
            (String)com.yiyiaddon.m.b.a<"s1qm1tbjo7sm4n","X1EwFMobm1cP0vFzDqRP6x/MQofn/cOsbNvLLa9w",6705923387427158671,7735973599647251010,-8934117649070650008,-1749895568515689134>()
         )) {
            int var5 = var4.lastIndexOf(61);
            if (var5 > 0 && var5 < var4.length() - 1) {
               try {
                  int var6 = Integer.parseInt(var4.substring(var5 + 1));
                  String var7 = var4.substring(0, var5);
                  if (var6 == -1) {
                     aU.add(var7);
                  } else if (var6 != -1) {
                     be.put(var7, var6);
                  }
               } catch (NumberFormatException var8) {
               }
            }
         }
      }

      jQ();
      jT();
   }

   private static void jQ() {
      if (aU.contains(
         (String)com.yiyiaddon.m.b.a<"s3cgearkcb9e3u","7DCqnd647UiRiRGxH8X47YxsLFFuSjRoJR8s0wtjatCvO8JstjB9RAOqcD51pm9sn0jBHqbwJTjg3Q==",8252277016263031040,-7737720083643385278,1483200245173860543,7791196215564542833>()
      )) {
         switch ((int)com.yiyiaddon.m.b.a<"s27ew3kb219o88","SkUvn4JpGhiX9wzJ7+jCQkpPxaQOCzmhHTtumqNFuDY=",4931643788648787450,7076335781361420150,8617459350116107413,2632828198499993057>()) {
            case 1929559080:
               return;
            default:
               throw null;
         }
      } else {
         be.putIfAbsent(
            (String)com.yiyiaddon.m.b.a<"s3cgearkcb9e3u","7DCqnd647UiRiRGxH8X47YxsLFFuSjRoJR8s0wtjatCvO8JstjB9RAOqcD51pm9sn0jBHqbwJTjg3Q==",8252277016263031040,-7737720083643385278,1483200245173860543,7791196215564542833>(),
            71
         );
      }
   }

   private static void jR() {
      jS();
      com.yiyiaddon.c.a.e();
   }

   private static void jS() {
      StringBuilder var0 = new StringBuilder();
      Iterator var1 = be.entrySet().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3udz5vt5fdc9o","3kiMagAsj2rFbx6lPRSu94xcvZPbkOiwQKK9DCKtfKI=",254236040668794512,-967674689219606231,-450977809062086285,-8090718455477927663>()) {
         case 1460026080:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1zi0tjv436sgt","d5VrwCSSE+xLiS9H8FDqeLx47cjTFlvsKFGCxwhZhQw=",796209409652568598,-797625172181698168,4898322094426769267,8546313267591150413>()) {
                  case 1118276059:
                     Entry var2 = (Entry)var1.next();
                     if (var0.length() > 0) {
                        label62:
                        switch ((int)com.yiyiaddon.m.b.a<"s2zrhcfdxbbdj5","QJW3Ov91dLEcC/F8YsES5w3htSd5Q7IQ4Vks3luXp78=",-4506454951691063067,9217770751776753513,-808482495819223199,394070902598807107>()) {
                           case -726728906:
                              var0.append(';');
                              switch ((int)com.yiyiaddon.m.b.a<"s135dlc2ped2it","lCBXKzd0ZAtMQRTaxbTQbKO3+jIzsBO9moOyemYxvvE=",1147548575862400279,-3389253584533057445,8138282210116317007,-2596004344189821098>()) {
                                 case 1026178909:
                                    break label62;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var0.append((String)var2.getKey()).append('=').append(var2.getValue());
                     switch ((int)com.yiyiaddon.m.b.a<"s2wea8x1mn3mjk","bsWt/egRYYZUBqW+UcegOXr6klrgXssUBmgZM4mf9xc=",2325663612796554832,-1370813142291163121,513905236698203349,-239522062862248432>()) {
                        case 1723518379:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var1 = aU.iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s3b05ujikms94m","ebwFBPTVyGB+DW9hSjSd3fw9/6bl+FJo+dkwB1lZD3I=",-4127869135704060853,4728318030073037049,5752350187012995391,6956299888762199678>()) {
               case -236746374:
                  while (var1.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"sy1scgh4pfc97","oSV59qyXPZnNC9oR/GjAGLbYqjV+EtJK6rQhnYjBIms=",6121899935759208960,-8827090995600557490,8635556126957057755,-2585866192285658709>()) {
                        case 2010513033:
                           String var4 = (String)var1.next();
                           if (be.containsKey(var4)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2hrejnympo84x","fAq4Pw2WQldsrdXwRMLu48opNUtB8hKpFddpooxE6Qc=",-3905414294242926175,1380340752628973673,2885561600795943536,-5822923295826003370>()) {
                                 case 1993170727:
                                    switch ((int)com.yiyiaddon.m.b.a<"s1oqzb1t1nldl1","kSURuINKbS8N8EUuJx+HaoMMUkKgWShVAwgc3JxoBco=",923636671269913914,-7961525652054193993,4995543531026624645,6053535867195528229>()) {
                                       case -806243845:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              if (var0.length() > 0) {
                                 label40:
                                 switch ((int)com.yiyiaddon.m.b.a<"s2i28ssp7r4dqh","QmKmHnnA9EDq+hZITw6Re8dEf7A4JTHFe5IHvpEA9W8=",8599575212260004101,-6621547883999256169,94368632884892450,-8687353383286215628>()) {
                                    case 650503679:
                                       var0.append(';');
                                       switch ((int)com.yiyiaddon.m.b.a<"s3514gqju91yj6","9WyvV81Dk/diwgWQOK5nyOALZK/LrKOZLtK4d0rj6xE=",1200056113825656780,-4450008105106806279,-845058636851345117,5092920205102439925>()) {
                                          case -819841779:
                                             break label40;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var0.append(var4).append('=').append(-1);
                              switch ((int)com.yiyiaddon.m.b.a<"s1nh5c55e14mwf","8C4Q+atV7PEUqTwzI9indz/iU/RWl5msoJoKaYLxV7c=",-4930575946655438591,2117385673182431861,4193181431693374719,-2697614186233357028>()) {
                                 case -37502500:
                                    continue;
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  }

                  com.yiyiaddon.c.a.p = var0.toString();
                  return;
               default:
                  throw null;
            }
         default:
            throw null;
      }
   }

   private static void jT() {
      al = null;
   }

   private static String[] e() {
      String[] var0 = al;
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3fwsiuzgnie9c","eGOL0PhdJsi4SavJxJuvq9jcybCMPZ9DYXYfaIlBozI=",-560307815871546162,-8370010027381734468,7447114215853393984,-8511872578951447216>()) {
            case 1683599625:
               return var0;
            default:
               throw null;
         }
      } else {
         ArrayList var1 = new ArrayList<>(be.keySet());
         e var2 = a;
         if (var2 != null) {
            label18:
            switch ((int)com.yiyiaddon.m.b.a<"sqkccd61utbig","yz1ME8IV0027VlNyV9hhNxKEpV8mm50/LYUbolcUMF8=",5939615204999932232,-5646185433730892798,1163015274331027034,-485776336710540623>()) {
               case 542042734:
                  var1.addAll(var2.d());
                  switch ((int)com.yiyiaddon.m.b.a<"s3hrnszh2808u0","q9R/hOAOhDNqpkgJVUX8xiwMmmaFMhDujZoNP8SHcyw=",-8261089955890538877,-4462804489909278317,-8884893636058664613,-8973588674716985784>()) {
                     case 802226203:
                        break label18;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         String[] var3 = var1.toArray(new String[0]);
         al = var3;
         return var3;
      }
   }
}
