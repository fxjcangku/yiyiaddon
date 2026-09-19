package com.yiyiaddon.l.b;

import io.github.humbleui.skija.Canvas;
import java.util.function.Supplier;

public final class h implements g {
   public static final float fy = 24.0F;
   public static final float fz = 14.0F;
   private static final float fA = 13.0F;
   private static final float fB = 10.0F;
   private static final float fC = 12.0F;
   private static final float fD = 28.0F;
   private static final float fE = 12.0F;
   private final String EO;
   private final Supplier<String> h;
   private final com.yiyiaddon.l.j.p c;
   private Supplier<String> i;
   private boolean eh;
   private float cD;
   private boolean fH;

   public h(String var1, Supplier<String> var2, com.yiyiaddon.l.j.p var3) {
      this.EO = var1 == null
         ? (String)com.yiyiaddon.m.b.a<"s3d8g88ro7judn","rK1J4WuYXppXwRMo2P4cxBP2a2uf12qClSlRZA==",-2294964883460011979,-8309630345079291790,3241819516999826242,626900645031616540>()
         : var1;
      this.h = var2;
      this.c = var3;
   }

   public h(String var1, com.yiyiaddon.l.j.p var2) {
      this(var1, null, var2);
   }

   public h a(Supplier<String> var1) {
      this.i = var1;
      return this;
   }

   public h a() {
      this.fH = true;
      return this;
   }

   @Override
   public float b() {
      return 24.0F;
   }

   @Override
   public void a(float var1) {
      if (this.c != null) {
         label27:
         switch ((int)com.yiyiaddon.m.b.a<"s150sfygj02kqk","PBWIZ3km/Llzdtw9b7ekp1UbWWxVZ3H9H1zJZpGKc2o=",3967169789556754074,1615825000742736508,1082448560866856316,-7933415682118177973>()) {
            case 1799729945:
               this.c.a(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s26v1chdrprjcw","hs++K1CeWINGX3QtcD4N/89AP8aOtj51YhuuVSx1oTo=",-2503344706061263911,644587009171425914,-8726575229089437853,247131970589627925>()) {
                  case 237324547:
                     break label27;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      float var10001 = this.cD;
      float var10002;
      if (this.eh) {
         label20:
         switch ((int)com.yiyiaddon.m.b.a<"s2oaqr86ahkv7q","ulVQdTZOJ62CFSEGWjc5XD+uBpMooHuLglRCHep/B3k=",1802389944746344523,9016100870903781139,-4677377233793811772,-3185278612459592729>()) {
            case -167509415:
               var10002 = 1.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s2pult4705cu5r","V1utn5sRagA7uE9qjabyfPRFKDdHk22mAM5e44EffjE=",6593356530121938713,-2823265879992162535,1211434926061576028,6017917206193945687>()) {
                  case 1186776748:
                     break label20;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10002 = 0.0F;
         switch ((int)com.yiyiaddon.m.b.a<"s3rt21xxtvvsnb","mhIBZ05IKwuzV3PtUEVZNfQqgBpoqw4SIpiDksTv6DE=",-6527364108025998324,-4996859725091156258,-5754689961632625336,-1764895866819218482>()) {
            case 1551424081:
               break;
            default:
               throw null;
         }
      }

      this.cD = var10001 + (var10002 - this.cD) * Math.min(1.0F, Math.max(0.0F, var1) * 12.0F);
   }

   @Override
   public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      com.yiyiaddon.l.i.c var8;
      float var9;
      float var10;
      boolean var10001;
      label86: {
         var8 = com.yiyiaddon.l.i.c.a();
         var9 = j.h(24.0F);
         var10 = com.yiyiaddon.l.i.c.y(var5);
         j.a(var1, var2, var3, var4, 24.0F, var9, var8.uQ, 0.7F, var10);
         j.c(var1, var2, var3, var4, 24.0F, var9, var8.uX, var5, 0.1F);
         if (var6 >= var2) {
            switch ((int)com.yiyiaddon.m.b.a<"sxnarkij920ig","1LLliFlxwX4naD+wX9j5cDCcGxywQKR77Vfc0Lg2Zac=",6841386640918281802,-3552084136728809946,-9091215977134441553,-5148167686830569091>()) {
               case -400540026:
                  if (var6 <= var2 + var4) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1iz8zhgg3l0tf","N4BBaW8ceSTW8A/8vxxnYE3PukOQkHa+PL6OqeJVJBU=",9033719651601572935,-7477401205425488915,4828834598408227174,-3643566842559247943>()) {
                        case 59451342:
                           if (var7 >= var3) {
                              switch ((int)com.yiyiaddon.m.b.a<"safdz4b0dwgnv","GygKCUBfYyeLT4dXArkg86DJiMZ2KVIodJsa/ZmABLk=",8950626632554878725,-9040561618390501361,2389173270930275857,-5950987496459417979>()) {
                                 case 508319088:
                                    if (var7 <= var3 + 24.0F) {
                                       switch ((int)com.yiyiaddon.m.b.a<"st8iuf1dm8xun","gzHO6IVqq/aZJ3amMuFTKcgan4dPyuK9wwmNlz4BoRw=",9207624586841591713,-6141879867430309483,1602770431684289107,-6791499278203290900>()) {
                                          case -544812599:
                                             var10001 = true;
                                             switch ((int)com.yiyiaddon.m.b.a<"s1zg9ciqp7ra7x","CxvqlBPlKo6zAFb95I0aqC9r5umjcYBkzbiVUQjzqNQ=",8370622233836671609,706470794200514912,4945945402845085617,3353070032419344801>()) {
                                                case -1292800737:
                                                   break label86;
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

         var10001 = false;
         switch ((int)com.yiyiaddon.m.b.a<"s3pnaewjtljvi0","HKYU3XzQ44qNpHacRSyGWj6prvr8fzM3BVOCugdNh+g=",-3217629266320930866,-5220886866133348441,5439628374472289584,8149382262831153132>()) {
            case 16353658:
               break;
            default:
               throw null;
         }
      }

      this.eh = var10001;
      if (this.cD > 0.01F) {
         label64:
         switch ((int)com.yiyiaddon.m.b.a<"s28ji24upcgm5i","VwQTwNkzGPz9tl/TLYvrs3BAbjskqnDjD3x4dOSYjAY=",-6815024840047514081,2360524741847131613,3647140875012235949,-5196134024460093387>()) {
            case -642954915:
               j.a(var1, var2, var3, var4, 24.0F, var9, var8.vc, var10 * this.cD);
               switch ((int)com.yiyiaddon.m.b.a<"s3o24wqf4dv0kr","Kr9NKZsfon4Cyhjt7+2l9/1DwfTizDtC9HXFM9Ptt7o=",-2675616592064658783,851576824066775486,-592783282939278354,-8704080650150019065>()) {
                  case 1292240361:
                     break label64;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (this.eh) {
         switch ((int)com.yiyiaddon.m.b.a<"skfzbebmjda8d","TyUpzo/4mDPZMgUem0/Nh+iZnDdRlvcFxp7gqmfaf1k=",2786829800003445157,2574805858919431381,3143589088229127770,-4132058136816622884>()) {
            case -736220937:
               if (this.i != null) {
                  label57:
                  switch ((int)com.yiyiaddon.m.b.a<"s1fodbav9cxs18","mHtuTfNJEjaggK06+Sm80Gdy6JL3HKf8TaEEBEU2wWk=",734092568801713273,-6603221648175988411,-1663133832902188626,-7467325966982623359>()) {
                     case 1486508164:
                        com.yiyiaddon.l.g.j.c(this.i.get(), var6, var7);
                        switch ((int)com.yiyiaddon.m.b.a<"s1n4j95jueei2n","yJtpmCbGokhwzBkK2/StGy159IrZBFi9vbOxxxDgJ7c=",-3187116964747237863,8595978046904726986,3758572403226581498,-6041120224974204169>()) {
                           case 1824910332:
                              break label57;
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

      float var11 = var3 + 12.0F;
      if (!this.EO.isEmpty()) {
         label52:
         switch ((int)com.yiyiaddon.m.b.a<"s1xa38wbw3fzni","n5k/JKwifCbr2uVOaEDNrcjRRma6KqlpwjpQ6joQrGY=",-6117337961577146990,8819091294515708152,-5940784276645022906,-128235638924793855>()) {
            case -930224534:
               com.yiyiaddon.l.g.d.a(var1, this.EO, var2 + 14.0F, d.c(var11, 13.0F), 13.0F, var8.uT, var5, true);
               switch ((int)com.yiyiaddon.m.b.a<"s34qvkc57mmh68","4evjN/fpj+p7EvEGOFTOrBpnogaLFf/Ts6D+TREKiQU=",3534310515554380791,225509641557201914,4533784459048328334,-1723588459614834499>()) {
                  case -443167103:
                     break label52;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.a(var1, var2, var3, var4, var5, var8);
      if (this.c != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1a9vmk344iyfp","ToGSU1M2fL1CN8kHYEwrM+pxiEEmb3q2mDhZ4n00ePM=",6641341858192607169,2581099595709008465,-9190496803260860013,-3437782760441880345>()) {
            case -1500324216:
               this.c.b(var1, this.d(var2, var4), this.g(var3), var5);
               switch ((int)com.yiyiaddon.m.b.a<"s474tnjomvnnj","2oxyM7Fd7bLcsHVB7AFzwhvvujhpky+/zG8/Ecd6A7Q=",1868088188572935686,6060656418213123410,-7522356898111165916,-5611505349514553461>()) {
                  case -561323983:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
      if (this.c != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2clt7tbzfsy7c","u1Xijkd4bGQPOr3omBsC2YCrm4ZCB5g7pI9VKqc/wPs=",-37927776355684897,2764364816495747171,-1272762744685450189,7218982058329713727>()) {
            case 1484407356:
               if (var6 == 0) {
                  if (!(var1 < var3)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3tbweavbsiimi","4DoTP6VLcilDRAeiZkCPh/L6FYwXr/dgWayV13fch5w=",6829460903322524082,6955814559036056909,541950845009327215,-6537737903533798804>()) {
                        case -280447340:
                           if (!(var1 > var3 + var5)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s21xog62kflvp8","CkK/aDCAx9ZN93D8kqtbj45v0hlWsNKnjkSEGBqh9vI=",1566077488335751450,-5275295203781303304,8985632599300503151,5939858025010494902>()) {
                                 case 1172396759:
                                    if (!(var2 < var4)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3pi3sqs6s8cyz","Ic5uuoAqWynbPfMhOO6S81ccY/GRwGhZaXD+p6i7B10=",-105834689613794779,-5325263319471679007,8244899003931535886,-5575113555826386969>()) {
                                          case 2193386:
                                             if (!(var2 > var4 + 24.0F)) {
                                                float var7 = this.d(var3, var5);
                                                float var8 = this.g(var4);
                                                if (!(var1 < var7)) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s36h1yt7n5vrnh","ED0gv9RL+SlYnomRhjSE0UjpvmiViU2CtGmUdVrO0dY=",3455317220393410518,-6128419901707393695,8646319490857654470,5690250412572541092>()) {
                                                      case 1516759304:
                                                         if (!(var1 > var7 + this.c.c())) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s3rrwo3t2j5xlb","OrXQaLIp19jVAGQqABosKjpXhq1Zq85NSqb42Jf9nU8=",-1485534766478346394,4645310490970631145,-5359719491728783591,2550291481995154679>()) {
                                                               case 651526863:
                                                                  if (!(var2 < var8)) {
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s1wvvjro02dojq","R1+3x3rymm1S96nhVnxfQjtn2fWNjcKPQfRb1n5S/LQ=",2844848709178327559,-1578577540060847503,-2947554135880755391,8602321989578184533>()) {
                                                                        case -1287035887:
                                                                           if (!(var2 > var8 + this.c.d())) {
                                                                              return this.c.a(var1, var2, var7, var8, var6);
                                                                           }

                                                                           switch ((int)com.yiyiaddon.m.b.a<"s2t1eyjnnyb52i","toXVdcV8t6UUse+qHu0NkKNW8blWaC0Um0ECtqt3wE4=",5916346078786361455,8019343676146762463,-5028678139760708889,-6433601510962206509>()) {
                                                                              case 735886105:
                                                                                 return false;
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

                                                return false;
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s2fasovp9myo2b","ye5ZvV5Yso65KZbIXrzQV+/mDHTdZXMfBqb0PTPl+yg=",4127312299120098723,-6143322585705700224,439377747923169244,-2102045892127719936>()) {
                                                case -1929384941:
                                                   return false;
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

                  return false;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"skl8yz6toqtue","BJLLC1VD8rf9DLq5nUxeT+d5I1zn61EZRFukrQQbfyA=",255712863429547161,-1895723076113476205,-2586253663486789951,692699350685912656>()) {
                     case -1426885866:
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

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5) {
      com.yiyiaddon.l.j.p var7 = this.c;
      if (var7 instanceof com.yiyiaddon.l.j.m) {
         switch ((int)com.yiyiaddon.m.b.a<"s3nwgyj7n8ujtt","wA0U+eZXfGJVEZlpKRLSf6A+pB0pdbVOHuAXNWkP3r4=",-4357171199834009328,-2158117132203144326,-6031302928807372364,-3700517339838685049>()) {
            case -797367970:
               com.yiyiaddon.l.j.m var6 = (com.yiyiaddon.l.j.m)var7;
               return var6.a(var1, var2, this.d(var3, var5), this.g(var4));
            default:
               throw null;
         }
      } else {
         return false;
      }
   }

   private float d(float var1, float var2) {
      if (this.c == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1s4pfgcde5dit","Myt8537bA/JsjdDqaFpW1OJ8c+58DnHQB69MzZE3NJ0=",4265408608211682834,-3586425827912776509,3989680596219542501,8451293287935097807>()) {
            case 424851325:
               return var1 + 14.0F;
            default:
               throw null;
         }
      } else if (this.fH) {
         switch ((int)com.yiyiaddon.m.b.a<"s2aivufjlfjvyb","Yuq7790Dq6pCAVQmqAWT5bqoXwg4cVbGOEkhrN/q1A0=",1259153190924842107,-7010075001376193382,-3630191773295442210,-890097492574131465>()) {
            case 232993268:
               return var1 + (var2 - this.c.c()) / 2.0F;
            default:
               throw null;
         }
      } else if (this.EO.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s13xxp7ky45q2d","Q/gRFLSsXei5MVDnl4TH6hDShnsUWtJkM/bgYG6l3eU=",1622628282573546508,3315002059450819086,51087777429774047,-2940402769989623162>()) {
            case 558904302:
               float var10000 = var1 + 14.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s24biftmhg2fn2","CKRh2dfJ9ReUx4s85xcL9AiN4ysKjfnEBuBo+JnJpJQ=",8281746621334013910,-7710153214968431783,-510649448206747902,-149732709103058512>()) {
                  case 1102002586:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         float var3 = var1 + var2 - 14.0F - this.c.c();
         switch ((int)com.yiyiaddon.m.b.a<"s2su4nghw91ozx","lOGN27fezkTey9uHQI6qkNybMh24GUyp2x7nCnYCjcQ=",6364505432466950023,4084249475787304627,7836160052527860147,-3233938702902418117>()) {
            case -864209657:
               return var3;
            default:
               throw null;
         }
      }
   }

   private float g(float var1) {
      return var1 + (24.0F - this.c.d()) / 2.0F;
   }

   private void a(Canvas var1, float var2, float var3, float var4, float var5, com.yiyiaddon.l.i.c var6) {
      if (this.h != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3743zsakryrhq","ZqMYzFfn/ILUzf193WW1a6SSsimSJMemLCoEepzx7eY=",6054825733369684420,6453756739035773688,3025008248605797548,5094705892020497034>()) {
            case -779317258:
               if (!(this.cD < 0.02F)) {
                  String var7 = this.h.get();
                  if (var7 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s68weuwe59lnu","UcYcUgQNcv7ZE8Xi8oibpZOyBujn72eQyrxexMp8G+s=",6909099194940692065,-7483210409668777584,-3990541010174341545,6217093962807679552>()) {
                        case -391666848:
                           if (!var7.isBlank()) {
                              float var8;
                              float var9;
                              if (this.fH) {
                                 label69:
                                 switch ((int)com.yiyiaddon.m.b.a<"s3nq0gdvp7rkbr","mxbttVwArW2Lhvmc8TSIC65/J2lzCoNtuwFbGBUcjrs=",7916177995989199771,-1867561747337239742,-3494709601978923682,-2812400056640330075>()) {
                                    case -2043779487:
                                       var8 = var2 + 14.0F;
                                       float var10000;
                                       if (this.c == null) {
                                          label66:
                                          switch ((int)com.yiyiaddon.m.b.a<"s2kp7flukwr1d6","FGcP/pfib6YE57kd83DSifpyn41INfRjA5weIpuDbKE=",-6948401599404502571,-1520120066554045095,8546239633763488189,-1633667776100322043>()) {
                                             case 653251272:
                                                var10000 = var2 + var4 - 14.0F;
                                                switch ((int)com.yiyiaddon.m.b.a<"s3hsd1mkcqy15i","hrcVZHviPdZQhcVNrdQIrNe5wxkxeFMPXDnyUsI7gMA=",2269352434048759987,9142764483005486021,-1029001398370087162,3414278323398735866>()) {
                                                   case -2058467244:
                                                      break label66;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          var10000 = this.d(var2, var4) - 12.0F;
                                          switch ((int)com.yiyiaddon.m.b.a<"s30xajn085epd","RFyu7uAPuituhrcYCN+gK/fsXPLAQL06fFVFC8xnYhM=",7209042887672760138,-7202880600645460958,2786414199497423435,-7872641617276431558>()) {
                                             case -243726201:
                                                break;
                                             default:
                                                throw null;
                                          }
                                       }

                                       var9 = var10000;
                                       switch ((int)com.yiyiaddon.m.b.a<"s2g0aztbuo3ctw","Sw7HpL5F+xyyv63OUhELaiKao7FuaF/dJlUtmGoxMO4=",-3335441928643785422,-18508893876469562,-457970319970318854,5056553961283289924>()) {
                                          case 754092621:
                                             break label69;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else if (this.EO.isEmpty()) {
                                 label79:
                                 switch ((int)com.yiyiaddon.m.b.a<"s1lpxgahtzud7q","Psshv9TwIQc/fMpnx5MlBSm12FaTZw385DBMyN+6Kik=",-577084553824553817,3081926677637898704,5376064914709792066,8264944746402695232>()) {
                                    case -1939662339:
                                       float var11;
                                       if (this.c == null) {
                                          label74:
                                          switch ((int)com.yiyiaddon.m.b.a<"s1ttk4n8sslvb3","ZcfLIkjidavo6/0CIod6rqjCCDZ1Yb0nGmtzWdR0QgQ=",5617933123699099441,-8331859547112090349,2350009475909834747,2192918708823866551>()) {
                                             case -473505221:
                                                var11 = var2 + 14.0F;
                                                switch ((int)com.yiyiaddon.m.b.a<"s393ykd66qh2m0","oa2wT8vCcwGbuUqKX3IZNtYK1+5oB2X5XL01WsgHSpk=",-8306726838828741629,-6556281721633977590,-4281213130898333868,5563303216394474142>()) {
                                                   case 1656208808:
                                                      break label74;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          var11 = this.d(var2, var4) + this.c.c() + 12.0F;
                                          switch ((int)com.yiyiaddon.m.b.a<"s1wcmroz2waf3v","n7IU2KD7RQ5N/5GrQ5Nq76ehysYPp7ezEleg13nIxEA=",9076914574556024200,-746736832670244759,8195941948760321909,3515285669229602167>()) {
                                             case 221916986:
                                                break;
                                             default:
                                                throw null;
                                          }
                                       }

                                       var8 = var11;
                                       var9 = var2 + var4 - 14.0F;
                                       switch ((int)com.yiyiaddon.m.b.a<"sfkc654k1nflj","x0wr0o3H1CIZ6zaxCYKHRDmNH/9ruFfwbBfR6RJF5F8=",-8308941947797136250,1245477433841244309,-590604832139054483,-8078250239499844095>()) {
                                          case -609887632:
                                             break label79;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 var8 = var2 + 14.0F + com.yiyiaddon.l.g.d.a(this.EO, 13.0F, true) + 12.0F;
                                 float var12;
                                 if (this.c == null) {
                                    label84:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3lbf37jsx6e6o","6c1OKaic4sHfsS6XdK+ML2+MeODAOWHVhcJHsARXNmU=",6066089698795290563,6972360021964166414,4502553485901642908,668555412481462615>()) {
                                       case -837149559:
                                          var12 = var2 + var4 - 14.0F;
                                          switch ((int)com.yiyiaddon.m.b.a<"s23qi7zzpc67wq","RbJ5s4SNgFBZjFZB+huf1b911IIjIprPWkNmzSC4qgY=",-3517191409966243697,-7643994786167281170,7127067558379920801,1104228733188607058>()) {
                                             case -478415309:
                                                break label84;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    var12 = this.d(var2, var4) - 12.0F;
                                    switch ((int)com.yiyiaddon.m.b.a<"s24cp6co8vuaon","6jjF37z94FXj/X4d5CHJnBZGiFfJI3K+l1TevqfJAGY=",2489179317566352124,-4125572109575964882,-6183464333950682177,-7751160679809805074>()) {
                                       case -287195368:
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 var9 = var12;
                                 switch ((int)com.yiyiaddon.m.b.a<"s2m7brfcd6rtg0","CN45OWmYvqcPFPObKLfu9dbuzGFO+dkDj2k6qQ4BVD0=",5604008446708980616,1731279819357826789,-2411971867430237152,-6636080402693687531>()) {
                                    case -914790748:
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              float var10 = var9 - var8;
                              if (var10 < 28.0F) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1o1qjjvqygxy7","wmKjf5BY6eMwEhmQEF8Ww0t5+5EFUZYSGkJOo7yOKkU=",-8485556034518297866,3376135789004275755,4377955749907704795,5043199217794622142>()) {
                                    case -1600917952:
                                       return;
                                    default:
                                       throw null;
                                 }
                              }

                              com.yiyiaddon.l.g.a.b(
                                 var1, d.a(com.yiyiaddon.l.g.d.cn(var7), var10, 10.0F), var8, d.c(var3 + 12.0F, 10.0F), 10.0F, j.a(var6.va, var5 * this.cD)
                              );
                              return;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"sim6zt3vd4z70","+SkEwk3fOH3Ud91i1iI7oAdPoOeMbcpn1l1m6L7Pw38=",-1319120305615870931,927183101252157124,-906962085861628998,-7087182746001089625>()) {
                              case -1058670631:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s8h2xad67bdjl","ASsCEoKPO9ECD7yThUP98q+fXPsKuTsf9APl8FE2or8=",2488647243024933526,7449446411300769066,4933110199285467018,6445635015735158942>()) {
                     case -1045769274:
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
}
