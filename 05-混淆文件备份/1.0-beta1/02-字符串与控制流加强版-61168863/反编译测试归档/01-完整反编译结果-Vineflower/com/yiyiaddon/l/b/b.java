package com.yiyiaddon.l.b;

import io.github.humbleui.skija.Canvas;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class b implements g {
   public static final float fg = 8.0F;
   private static final float fh = 24.0F;
   private final List<com.yiyiaddon.l.j.p> cZ;
   private final float fi;
   private final boolean fF;

   public b(com.yiyiaddon.l.j.p... var1) {
      this(8.0F, false, var1);
   }

   public b(float var1, com.yiyiaddon.l.j.p... var2) {
      this(var1, false, var2);
   }

   private b(float var1, boolean var2, com.yiyiaddon.l.j.p... var3) {
      this.fi = var1 > 0.0F ? var1 : 8.0F;
      this.fF = var2;
      this.cZ = Arrays.asList(var3);
   }

   public static b a(com.yiyiaddon.l.j.p var0, com.yiyiaddon.l.j.p var1) {
      return new b(8.0F, true, var0, var1);
   }

   @Override
   public float b() {
      float var1 = 0.0F;
      Iterator var2 = this.cZ.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3w3ly6evlq39x","StKllRoqo1+fES+LEXhAeBTIhITOL/tnPC2ls8WEooY=",8559560838829513373,5542523263357786249,3532783876229560512,-2010342849989752870>()) {
         case -1968604128:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3mb2hv8hzxqv0","UwJUP3p/U6rSMwOLUAas16bWdYCuo6YW1yCwntBmf7A=",-1797388086130015173,5614569744178694117,-7706338489817185549,-6075754438125552571>()) {
                  case -607388180:
                     com.yiyiaddon.l.j.p var3 = (com.yiyiaddon.l.j.p)var2.next();
                     var1 = Math.max(var1, var3.d());
                     switch ((int)com.yiyiaddon.m.b.a<"s36y6aapdvr1k2","fEKCMY6FzA2rGXfMqatmUSAnPHJRw38Uo8sCvh9/gko=",5551739647460365133,1346908475602598924,-3908963664010151082,-966412586880926873>()) {
                        case -843225792:
                           continue;
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

   @Override
   public void a(float var1) {
      Iterator var2 = this.cZ.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"scizu4sx5h57r","qGBwKijoIQJroS9UKmqFffTLXbwHPHW0zP/zqnVRGyk=",-776879041095592378,3803689535177623707,114583986793603728,4248394740321726564>()) {
         case 2037831996:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s28bhybj7vxwj","dwfX5o9wUOEfKhmI48fIMEsELzQ+6O4qt7Kwx1j1ays=",6836927819831619424,4122209395818560188,-1254828916408852396,-3509764188795001415>()) {
                  case 1441524897:
                     com.yiyiaddon.l.j.p var3 = (com.yiyiaddon.l.j.p)var2.next();
                     var3.a(var1);
                     switch ((int)com.yiyiaddon.m.b.a<"s266ax6xtshqvb","pQDlQBm8Jys60IIFVzue5vmxWl6KbwX/SMGiNgAVoxQ=",-2766233181304266714,6992851053036730745,-7061842203302096605,-1571411629160485559>()) {
                        case 1491425701:
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

   @Override
   public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      if (this.cZ.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1a573x1um5bv3","P4taF2Y2+p6agwaID3kBPto2mDfDIlbMXkD/KrITKYA=",2798217029141607747,3552276903425272313,7569305104183412611,7876271271421122119>()) {
            case -1549406180:
               return;
            default:
               throw null;
         }
      } else {
         float[] var8 = this.a(var4);
         float[] var9 = this.a(var2, var4, var8);
         float var10 = this.b();
         int var11 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s2blytvwe6lvrg","JUkT71IXCaABQDobtqa7lawtcrvZMpxnUwUR1cy7LhA=",4143285949202111979,-935582406847479394,7556883800161438418,-2527960791095595940>()) {
            case 1912616185:
               while (var11 < this.cZ.size()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1744qdazxgohs","ddfoJCb/k3g85G6i9bbc0JAffEfB8L9VUuvWNOxfXy0=",5120230459318931819,7526496158832354799,-1497116746947031311,7506185485806121593>()) {
                     case 377843344:
                        com.yiyiaddon.l.j.p var12 = this.cZ.get(var11);
                        float var13 = var12.d();
                        float var14 = var3 + (var10 - var13) * 0.5F;
                        var12.a(var6, var7, var9[var11], var14, var8[var11]);
                        if (var12 instanceof com.yiyiaddon.l.j.a) {
                           label32:
                           switch ((int)com.yiyiaddon.m.b.a<"so1wd9ft0drz2","AMLtjxkTmwPFXhy5d/YPW88tGzZghNLmCA5hsl6sujM=",-5950702225679828702,-7599249763756283443,7305057994445363458,-1935501719349131961>()) {
                              case 132522018:
                                 com.yiyiaddon.l.j.a var15 = (com.yiyiaddon.l.j.a)var12;
                                 var15.b(var1, var9[var11], var14, var8[var11], var5);
                                 switch ((int)com.yiyiaddon.m.b.a<"s8akuv1nu4s5f","80Sj9NsyeVnj9jTmmQy0esb1y9DUevs8K6RGqpzZ2Nk=",5072204208534965415,-5862283216784048751,-6665100194395235474,144865896761970285>()) {
                                    case -579989511:
                                       break label32;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var12.b(var1, var9[var11], var14, var5);
                           switch ((int)com.yiyiaddon.m.b.a<"swoabcrzgt6wy","ZBAyy3xnXsYxMaEckRKn1l+eLEgSbNYMUn65ASwu3GU=",-3275656331387088185,894683899562833643,-1697674936711580923,9158948931820185390>()) {
                              case 1725824580:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        var11++;
                        switch ((int)com.yiyiaddon.m.b.a<"s1o71k30qe0pw","Awj+cSo2CZcrK/TYfSQKvCam7TW9aOtJiHj9kkNv2Mo=",-2472835618129758876,-4350297811574981390,4069389046885696570,-6120851805933171469>()) {
                           case 1930776055:
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

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5) {
      return false;
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
      if (!this.cZ.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3baeihl3acb5d","9x8L5wvICv4Ha0RI827w1/yWC9mll3kg7KdbkU2uTSc=",283359055843310067,-6683750336263935230,923006546835052238,8920933381934029531>()) {
            case -633110097:
               if (var6 == 0) {
                  float[] var7 = this.a(var5);
                  float[] var8 = this.a(var3, var5, var7);
                  float var9 = this.b();
                  int var10 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s2jx3j5kez4aj7","xQVG0Zu293CA58PvyrvJoJVCkuMXtnmYXRne7VZxF7Q=",-765478182957099991,6253203483367754609,8264192431762292100,-5433132651604735966>()) {
                     case -1981015875:
                        while (var10 < this.cZ.size()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2b3blcdpy4t61","kIbAkEu6ZaOM1eRKPv92g/elw3c2Bv32ZAqwe2BEfdg=",-587163748927991494,-4703329985751462104,-7542234281443169786,-9102121041111622888>()) {
                              case 2139775460:
                                 com.yiyiaddon.l.j.p var11 = this.cZ.get(var10);
                                 float var12 = var11.d();
                                 float var13 = var4 + (var9 - var12) * 0.5F;
                                 if (var11 instanceof com.yiyiaddon.l.j.a) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sjxytrw8riodj","q8pCTqOkyhRq1eputpwZwyf1mBRuZ9U1EYufWqd1nek=",2233992732866063782,-8242825990269709431,-5478098173902543425,-6940898354384457262>()) {
                                       case 2091216932:
                                          com.yiyiaddon.l.j.a var14 = (com.yiyiaddon.l.j.a)var11;
                                          if (var14.b(var1, var2, var8[var10], var13, var7[var10], var6)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1jujrai17mx70","v6xSxEXI7CqOkAcwgdNqIAr1t4vBq2mpzxZAoTy+BUY=",3341629102296131283,-7565025046441435954,-7535182924224123034,3185488505813091128>()) {
                                                case 258373925:
                                                   return true;
                                                default:
                                                   throw null;
                                             }
                                          }
                                          break;
                                       default:
                                          throw null;
                                    }
                                 } else if (var11.a(var1, var2, var8[var10], var13, var6)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3auf6wgcpl6n9","VuliezVOOWO8sGXVY2LIDUKO/QECDZ1llQv5XjaKvxQ=",-2142741866890208311,-3912687236800881628,9142498125580178955,7753126224846467932>()) {
                                       case 1122293018:
                                          return true;
                                       default:
                                          throw null;
                                    }
                                 }

                                 var10++;
                                 switch ((int)com.yiyiaddon.m.b.a<"srwsxf9x1khpn","1JmHaX/bhyqqBfjv/54tMSf/6vLV1ZSiq2NVwwueHTA=",3652030749704606973,2670705113352055904,-7641023944976145317,8464522872089696745>()) {
                                    case -169372732:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return false;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1fq21gezrkklt","6NWADLECbUtHarfTffYpkzc/uE0ymxo0E6bwY2Pt4E8=",-8348016670308730581,-9184470218890971251,-932551290505118839,-2296111757923087885>()) {
                     case 640300458:
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

   private float[] a(float var1) {
      int var2 = this.cZ.size();
      float[] var3 = new float[var2];
      if (this.fF) {
         switch ((int)com.yiyiaddon.m.b.a<"s2h3xtspdoi9wc","Bo5US/FZUOI2s0k9MKDbwluPQbnY2qF/QqZKuB2xdWM=",-1093988608625014419,-9212506578637085160,2888314745166244135,-3169426646483807549>()) {
            case -1457002828:
               int var10 = 0;
               switch ((int)com.yiyiaddon.m.b.a<"s2ylfftk4qv0w4","UGtiYkg6f8lpFO124JqJDfw6YRSdUmJ+/yxOVviR3Ys=",-566814310086968408,2963121342105825741,1201498806667552633,-8395026647638144399>()) {
                  case 1752427124:
                     while (var10 < var2) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1q085w6pox1hj","kIUGtM2YBnMl6RdTLXk89hm83PIrqcp6hEffC1oLhoo=",5310445638929671483,-6962630363974901546,-8515110744494876754,-5600045305117454771>()) {
                           case 297714033:
                              var3[var10] = this.cZ.get(var10).c();
                              var10++;
                              switch ((int)com.yiyiaddon.m.b.a<"sdmpnkeppdqdr","W05QL7BO1BZKqkh+Oeu+g+dTs9+l8JqJTBpIX7un5sg=",8508756306926942646,-7317617252856441764,1963012786675050215,-6993798834522007406>()) {
                                 case 983252772:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     return var3;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         float var4 = 0.0F;
         int var5 = 0;
         Iterator var6 = this.cZ.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s1sn945bas39v8","U7eL6NkhHxURXFYOriGksqFnR57OBAnjvsEscwv8Ubs=",-8917212176531629489,-3678900228468507832,2530638375378004695,-669799011400781588>()) {
            case -1386712158:
               while (var6.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"stvmgo7b168ri","f9GvIMQ3ATVwd3eFi4yvToWcE52lR50zyDBqO1PAG9k=",6419227459973316107,-8242683490141097439,3900111268817591716,-3426226843686866159>()) {
                     case -2121574400:
                        com.yiyiaddon.l.j.p var7 = (com.yiyiaddon.l.j.p)var6.next();
                        if (var7 instanceof com.yiyiaddon.l.j.a) {
                           label94:
                           switch ((int)com.yiyiaddon.m.b.a<"s1tznlw2u512d","kozfJ3GkkeBfjLOuqY4D7tpgoVUiEVSpOijHHJJ1Gto=",-9218801521767229394,9103465480942572994,6266113556369709718,-1327431725863096684>()) {
                              case 830081859:
                                 var5++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1hyyldpfqf1mx","yFKYi1bzLr2fP94wLV/A6heaDCKRvzrcPuotOtbc6R0=",7667796330835732323,-8089255053209298274,5206852196925628258,-7008486429185890509>()) {
                                    case -1848808933:
                                       break label94;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var4 += var7.c();
                           switch ((int)com.yiyiaddon.m.b.a<"smsgb282izxy1","W6aqbbG1PEpDBc4qWuJ2o7ApLiRrpn10Ir/B+tAWl1I=",2272248395622731364,8923537407611759621,-54807342907417015,-2482086186708040146>()) {
                              case 588852367:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"slc8ezkrsm15s","S16uN4XolY/qJEUbw6VWhT3sS1G0wA6m+NLiQ/0qT60=",-8508380680387611077,6820141134125041774,1283478691939861255,2049443209921015184>()) {
                           case 48309247:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               float var11 = var1 - var4 - this.fi * Math.max(0, var2 - 1);
               float var10000;
               if (var5 == 0) {
                  label82:
                  switch ((int)com.yiyiaddon.m.b.a<"s2lpfs7qs7fiba","AGYzRy/Lc+1SEQcarX690Fu9zI+SVhYQDPuX3eccKTg=",1027613106227491054,-6861775958065966048,-5695232952899991983,9033168668373334239>()) {
                     case 1695747504:
                        var10000 = 0.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s36jnb0cankmb2","Z3Q80jP/NCJsMUBUu/bQLa9BD3TqRS+K199+pv2lyfM=",-5917536303595198041,-8404039644079422817,6000709860807041375,3940097138735075610>()) {
                           case -44394403:
                              break label82;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10000 = Math.max(24.0F, var11 / var5);
                  switch ((int)com.yiyiaddon.m.b.a<"s14yw4amtp159d","JwXsx09V1AHEK4BZ62fPsoP/erqAYZyJCobTarhQFEc=",-6474176603833846741,4830269632359662422,3826278460127228713,-8357877033446127825>()) {
                     case -1295921658:
                        break;
                     default:
                        throw null;
                  }
               }

               float var12 = var10000;
               int var8 = 0;
               switch ((int)com.yiyiaddon.m.b.a<"s2m8cmew9sy6om","BU94w1PhqeT92pmRLIenPgCFkXqy32U+HSU1AteESmI=",4004383991122398897,-1736883509866695004,2998235085932491635,7710781639996298033>()) {
                  case 1890349545:
                     while (var8 < var2) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3no364976yvsi","jgjel2NlRR9E4QzxGwEn0Tt71GTgLmewO+vIE8SqmhI=",-4383061527616991436,-4208735500033791869,-6246622574513837649,1048982113305503983>()) {
                           case 1387660393:
                              com.yiyiaddon.l.j.p var9 = this.cZ.get(var8);
                              float var10002;
                              if (var9 instanceof com.yiyiaddon.l.j.a) {
                                 label69:
                                 switch ((int)com.yiyiaddon.m.b.a<"s164q6okftfroy","0NrPX1LIj0dnuwSrjW0UTTbJVj18IcEwiBOAJTnFXVw=",2102160173737142841,-2993991084240534514,6863596946619138863,2393268773451515512>()) {
                                    case 547761961:
                                       var10002 = var12;
                                       switch ((int)com.yiyiaddon.m.b.a<"s3hu5n9wjrrxni","5OYbObnJI2Z04g1SEkG1b88CsWBx/Q8yJ/Z7GD3cEO0=",-1060470039804191402,-3788394731399536647,2125213753405687456,-3717928260236030496>()) {
                                          case -1135885887:
                                             break label69;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 var10002 = var9.c();
                                 switch ((int)com.yiyiaddon.m.b.a<"s1qj8b279wvc3","oYsFetD1EGkHcfm2kmG8AdvOS1WQ+oP1y1eWC100+3I=",318579831030990312,5872567273738013461,7721731505070576202,-2004012671114506159>()) {
                                    case 1262540352:
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              var3[var8] = var10002;
                              var8++;
                              switch ((int)com.yiyiaddon.m.b.a<"scnczbdtd5boi","4fArwLYhbYnjPGyt+towrp4atAx9BGoA5WjslKirr00=",-1499194497804893768,499660400767463563,5853233325656481533,-114442062921306896>()) {
                                 case -1241454057:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     return var3;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private float[] a(float var1, float var2, float[] var3) {
      int var4 = var3.length;
      float[] var5 = new float[var4];
      if (var4 == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s3utuetsjwu57t","H/Uuc6dUrTH6CerFZh7ay94OkO1Uc7SCpsVmYsqE2KM=",-8729395549938203555,5012434749076871951,-1962483351519312878,8884896273174631691>()) {
            case 1360615535:
               return var5;
            default:
               throw null;
         }
      } else if (!this.fF) {
         switch ((int)com.yiyiaddon.m.b.a<"s287x3bjnzz89a","1d0fTenfSZGsnj1qz4Kid2k8W+ieWzOAJXCvzbf6X80=",-258718012594856602,5741769240833220656,6975355434333829122,3353857235061789768>()) {
            case -470766155:
               float var8 = var1;
               int var9 = 0;
               switch ((int)com.yiyiaddon.m.b.a<"s3kn0dajko20vm","99hIsuFljH1dxvqandBuBaTlelDX2sWvQO8XMGEE5PA=",-533679282228283690,-6096662149951159227,4491311295350208043,5468079085480278000>()) {
                  case -1237422007:
                     while (var9 < var4) {
                        switch ((int)com.yiyiaddon.m.b.a<"scrqnita7bge8","fGJ+mJBmKeKi716QuXQ70G29OZPh/j0SIwYVhQ0Y1Us=",1494860987694764309,5787942341392981690,-6222057782637256623,6604785417051646787>()) {
                           case -1066520627:
                              var5[var9] = var8;
                              var8 += var3[var9] + this.fi;
                              var9++;
                              switch ((int)com.yiyiaddon.m.b.a<"s2fhwinvpcffqq","IbxtpI4yyZBqnjTvh5J27MnfRygo6YJcJ4t68lyCBaQ=",-3994566568929349949,-1139917733217889261,507815842693685829,-4515787613428243561>()) {
                                 case 604581405:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     return var5;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var5[0] = var1;
         float var6 = var1 + var3[0] + this.fi;
         int var7 = 1;
         switch ((int)com.yiyiaddon.m.b.a<"s1pzg6efkxvldo","9RWyPJRGWWBOZe7bxq/HxeN9/PPd4VFkej3zCYJIa80=",340424374432552157,6526481562040581232,2286649915768780640,139213030648656878>()) {
            case 624766709:
               while (var7 < var4) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1beui6qjhh6j6","7Z8crGvCoq81PEGWsjFWHaa+HSerDVSWvaElZB9b4oU=",1814506452234668249,-8466274752493822797,-3251147659927914353,6923105179897815016>()) {
                     case 1765982695:
                        float var10002;
                        if (var7 == var4 - 1) {
                           label53:
                           switch ((int)com.yiyiaddon.m.b.a<"s3ergn9q4xp1ki","PlBsLbF/4BvB+V+NiRPXrp64xIig+8mI+6iyASiQeyU=",-5806252665106432562,8174465024435508270,8551857484809320004,2788464867787755190>()) {
                              case -1852025492:
                                 var10002 = var1 + var2 - var3[var7];
                                 switch ((int)com.yiyiaddon.m.b.a<"s3kd34dcg0qt1w","x1vvwWqy8hi5y/pCRWIfEiJ4ZSq9Afjk+5/KCTuSg3A=",-8903840168939851885,-1173599819265337696,7970566776438114197,4070108628488505837>()) {
                                    case -758737068:
                                       break label53;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10002 = var6;
                           switch ((int)com.yiyiaddon.m.b.a<"s17exoyjv9b4h3","JboQoQk9qyqdJajTCe8w9lenH9YZRwZ8qlZqgtbYdcs=",-236034668643444022,-783454259789323032,6293738494809255676,-715148526858846175>()) {
                              case 1138448742:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        var5[var7] = var10002;
                        var6 += var3[var7] + this.fi;
                        var7++;
                        switch ((int)com.yiyiaddon.m.b.a<"s1dud3jldi4u7y","Sm6RUVdFnTQ+4OGhgnCa8DjImDQb0JsBHo3Eu0p8p6Y=",2650195480588346871,-195657651637866117,1666862416074039500,363116012376284193>()) {
                           case -1181798137:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return var5;
            default:
               throw null;
         }
      }
   }
}
