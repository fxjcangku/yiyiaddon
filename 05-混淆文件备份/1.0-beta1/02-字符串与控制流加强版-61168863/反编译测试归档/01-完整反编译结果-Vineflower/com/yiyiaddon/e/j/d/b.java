package com.yiyiaddon.e.j.d;

import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalNear;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.warden.Warden;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public final class b {
   private static final double B = 6.0;
   private static final double C = 16.0;
   private static final double D = 16.0;
   private static final double E = 3.0;
   private static final int gX = 60;
   private static final int gY = 30;
   private static final float be = 18.0F;
   private static final int gZ = 20;
   private static final int ha = 200;
   private static final double F = 4.0;
   private static final int hb = 2;
   private static final double G = 5.0;
   private static final double H = 8.0;
   private static final double I = 12.0;
   private static final double J = 1.0;
   private static final int hc = 1;
   private static final double K = 12.0;
   private static final int hd = 100;
   private static final int he = 20;
   private final com.yiyiaddon.e.j.a a;
   private final Minecraft F = Minecraft.getInstance();
   private LivingEntity a;
   private LivingEntity b;
   private int hf = -100000;
   private int hg;
   private int hh;
   private int hi;
   private b.a a;
   private String mP = (String)com.yiyiaddon.m.b.a<"s2no9r52wwzspo","hKvzM34ZmBirXrhdTmAkcmIprJQYJEgkMSzR5g==",3641741071354125898,-9074619837686925712,2091751452319718184,-558121964693406306>();
   private int hj = -100000;
   private int hk;
   private int hl = -1;

   public b(com.yiyiaddon.e.j.a var1) {
      this.a = var1;
   }

   public boolean b(boolean var1) {
      this.eH();
      LivingEntity var2 = this.a(var1);
      this.a = var2;
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s37bv2s8m0zabs","lWduyZJO91HdX0l8JzumTMhqUsabHFtOcEse3I+PsME=",-8273570312431270213,-5242340877268667542,-8242865475266365313,-745556065220421155>()) {
            case -1915697142:
               switch ((int)com.yiyiaddon.m.b.a<"s3skxqsrzjo0mf","S6TYv9eV8cQE8rhVvQS+M/rDNdWv5oLR52f03ZGnGf4=",-9049448428390948384,7713365209934514086,246228121487601410,-6020514783295744452>()) {
                  case 729497780:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s3u0qq4rbeoz0x","9gEuL67IWbSfHtKhFPLczGWvz8l2LrSCx4z2r152VaE=",-7035769531372753187,-6060026437009104168,-2674183603804237565,2925176546581705171>()) {
            case -242074207:
               return false;
            default:
               throw null;
         }
      }
   }

   public void f() {
      this.a = null;
      this.b = null;
      this.hg = 0;
      this.hh = 0;
      this.hi = 0;
      this.a = null;
      this.eJ();
   }

   private static boolean a(LivingEntity var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2h2dekvrpmvmu","ofOKMz2Nq5uMwMj2HnZiXMqnVUZQCG/hJyVhCSqO1IY=",-4707388301739481137,8289591441724527537,7991974010409098891,6611689907644739807>()) {
            case 1137589576:
               if (var0.isAlive()) {
                  label25:
                  switch ((int)com.yiyiaddon.m.b.a<"s3tbf7q6dmnjf7","pfCJ//8Kf1QHiAOqigis3yk6j1SEI2BEOd8wrOKIr1w=",7049904477232185509,-2621114319093766865,-6682448902949141050,4725681134856299124>()) {
                     case 592243762:
                        if (!var0.isRemoved()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3uaenql0vdn0i","TjX7dbygjIvbMpKowpL7rsl48JC5ZevmAZWg2yqra9Y=",6457520905812614589,4326368918293132821,4778766921191518825,2163267807006205696>()) {
                              case -928640471:
                                 return false;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1whndqxiag4ia","5OQ/Te6sO/zhUGIdkoUDG0yJkKZG+IKf+KLtSmmcE/E=",1998953140845949041,5823491289717924314,6302907211078349652,2091781941188517954>()) {
                           case 874419527:
                              break label25;
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

      switch ((int)com.yiyiaddon.m.b.a<"s2vvs3y3kca10z","9ccrbpZFkQG3hf3Pcc27SpzH+BM3O2i380oochR5dBQ=",8089156544052990349,-1405714520584563982,7424565908567318968,-4469347116922377474>()) {
         case 670214187:
            return true;
         default:
            throw null;
      }
   }

   private LivingEntity a(LocalPlayer var1, LivingEntity var2) {
      if (this.F.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1lyytg1vr9gaa","OFuHbIabeiYOK/zuw329EhX8tONPU7Kp8WrS4WKpxpI=",6878113306506546251,8971825460449258502,-2051277673679943753,-6232547220877812253>()) {
            case -476946027:
               return null;
            default:
               throw null;
         }
      } else {
         double var3 = a(var2);
         Monster var5 = null;
         double var6 = Double.MAX_VALUE;
         Iterator var8 = this.F
            .level
            .getEntitiesOfClass(
               Monster.class,
               var1.getBoundingBox().inflate(var3),
               var2x -> {
                  if (!var2x.isRemoved()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2j2tdse3eve2","bX7u6hgBBdpPsi5BxFmo1BNcL6hW5oRiKyPiASUkGOs=",1202575077480057137,7219776655842437012,9205767525648994990,-464863097867389200>()) {
                        case -930027482:
                           if (!var2x.isInvulnerable()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s33o9l83sjqe4y","KsbKqriJilzEVY4kj/cqZuTKn1FPphxU3Vw2hQrPNhE=",-8877154307853420214,-732853311677138712,7448336978770483380,7229152897742219330>()) {
                                 case 549646755:
                                    if (b(var2x)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1q1z1ltxedqff","hSCNjR4A42mBS8sggGdLTcatXpk+QPjd5TjpyB61AJA=",5653400882810185883,3931174137249597050,-125174236640503656,5299321642901798040>()) {
                                          case -164332771:
                                             if (this.a(var1, (LivingEntity)var2x)) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s2m2fzk9f79ui6","ruHjGj4nJM7JC/K4nDmqzQc7z5SFrTCsHQc3w9d5Hb4=",5926524908719728938,7044422728892056805,-4259338578840739341,-1898226456030857850>()) {
                                                   case -361132278:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1ru7tx5gutjll","ROHGxPdTJSTNoGYsWAULVcgL0mpCrn/oARJLpS+cPEE=",4051197823652210202,9067788042639876508,-8006830558045482946,-2250786698124468962>()) {
                                                         case 2135543787:
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

                  switch ((int)com.yiyiaddon.m.b.a<"s3k5d6qhybve5g","nHaNiw4D+rOLJy/hfq6Pha9lwed+6YT1nTbplqWXiTM=",4121228477873562145,5652958646445420753,-6431145907287888031,-9059680659387106082>()) {
                     case 913352940:
                        return false;
                     default:
                        throw null;
                  }
               }
            )
            .iterator();
         switch ((int)com.yiyiaddon.m.b.a<"sdmhu2gocmnkr","zlN1tSaBiTf921L7SaofU6Sa15WWTtCTE5MtjgqkB9A=",-2897865191385365593,6499734192239458577,7414056062149335501,6867878864284231055>()) {
            case -1390849564:
               while (var8.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s31zksbvibsxqk","mTLuUj7q/RwgyyJl60KTjzIAFzM4ffEm4E+bE4ptcw0=",4404341157893700899,3566473376006448452,352851811056907202,4593648005374406443>()) {
                     case -1867130466:
                        Monster var9 = (Monster)var8.next();
                        double var10 = var1.distanceTo(var9);
                        if (var10 > var3) {
                           switch ((int)com.yiyiaddon.m.b.a<"sei0m1xqqf2b4","6NismMTBqgNCBTIEZXZkCKInwU7JRO9pSsEyQqFIzC0=",532559663898147747,-4234683595986039649,-6655375627398467160,6211664744967881248>()) {
                              case 1317717802:
                                 switch ((int)com.yiyiaddon.m.b.a<"s1coeuqu285f2l","FimLXywN92371NWTK+KQkwjtn5Uk9JksaquHWy7P+tQ=",-2042985875897645277,-4678709349799771162,-4315170095980133294,-8115739234668149647>()) {
                                    case -1813751844:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           if (var10 < var6) {
                              label33:
                              switch ((int)com.yiyiaddon.m.b.a<"s1ecb0pxva6r5u","K/ojStBFv/qL686IexecYMRAp58B1bC8LAAfWFQwcmM=",-481264520439487749,1455243210862158406,-5040168290597449804,6911205226865965669>()) {
                                 case 881953604:
                                    var6 = var10;
                                    var5 = var9;
                                    switch ((int)com.yiyiaddon.m.b.a<"sxee7irsrau93","8IorKcO9xSTAg8BfwRJ2PQN3TCVNcX96RN1x3etq6RQ=",7275507921504560219,9218324376823260329,1267527579018999247,1250068681080542233>()) {
                                       case 764722713:
                                          break label33;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s2oqb72rc84oup","btMVBBsfLiYoG0333PKVBdzvG/75O30AU/zAfki18M0=",7536584003942321810,8777872064758645379,5571161332729632703,-6379258731720443997>()) {
                              case 894869929:
                                 continue;
                              default:
                                 throw null;
                           }
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

   private static double a(LivingEntity var0) {
      if (var0 instanceof Creeper) {
         switch ((int)com.yiyiaddon.m.b.a<"s1cdb00bu3wo8h","Yw+BqPF65G0zLRtBRsULNAastyTlYPrkGVJgL934vF0=",3508429076977606978,-6448472073156049966,-5786228795203286530,-1689218695920900760>()) {
            case 2060498861:
               switch ((int)com.yiyiaddon.m.b.a<"s140nrj59t8om8","EBXrD9S15E7mcGBl+GZ+hMxtwC0cUzwhBTbLzjCFpz8=",-7957644155863102711,6158706400850912997,1151881160223360930,-3993458326831587960>()) {
                  case -1227079618:
                     return 12.0;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"sfawlfxd4ez8j","JSA4Ic6uv+i2y2tQ7pvafQXloz1azrxP8TqjJM824YY=",495762828197974491,-847603320702984333,6388304756672273431,-1444428401393846220>()) {
            case -646702356:
               return 6.0;
            default:
               throw null;
         }
      }
   }

   private void eH() {
      if (this.F.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2467170ub8g75","fdX/UDLlDlWm6w1nMpIBKwHGblfIiSH8HT2m7w7bYp0=",8898415078796966323,-1056778738823001366,3897338671021181968,5718300810830963695>()) {
            case 1364705264:
               if (this.F.level != null) {
                  if (this.F.player.hurtTime <= 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2vazz5wh9oadw","bAv5El/vz37yIpow0BBMY60c3Y4hmSto7W/axwr8GNc=",6414939601061719129,5258947176271601993,4865915685500208275,141294270024397560>()) {
                        case 397923940:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     LivingEntity var1 = this.F.player.getLastHurtByMob();
                     if (var1 instanceof Monster) {
                        switch ((int)com.yiyiaddon.m.b.a<"suvxtudze0zdi","d+2xb+zDAMiRyF1M7lzc5S5VYOX0I9UoozrbDBxJo4E=",2886035828026470507,3623513039008611151,2845710734606967737,-382165233291082562>()) {
                           case -854388110:
                              if (var1.isAlive()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s110i14cfh3lny","en8+zrAVZjEMoMIxDW7EW7Tpn+jEnpOzC5zIB4eE7Qc=",256922165699620330,-1882728696865612233,7695237461167094383,-1123030063521682583>()) {
                                    case -119089967:
                                       if (b(var1)) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2b39j2ruomwyu","VahKtcyURuDbWrRyR444cs5xj6AnInCUyeLIIPaZ2Gw=",-1468814843461194752,-3716250357586862985,8163853716017849737,-5297197384870110011>()) {
                                             case -1712168794:
                                                this.hf = this.F.player.tickCount;
                                                switch ((int)com.yiyiaddon.m.b.a<"s8yrwspju5q4e","OUA3yikg5r84upsqzis3OQjTvJjyXHC5cTULFiheb1o=",1142325441000046517,5243984716645035341,6663707668209868294,7794064407867387210>()) {
                                                   case 765627528:
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

                     return;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s25vnssfyhy7an","BeMUHmB1PgrDARdYGZAsCDHqDPJAoK3MMEYyU3AWCa8=",-2160906666796551598,-7422234828159745409,-5862315992730862725,-2608508049569122202>()) {
                     case -154374913:
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

   private boolean bX() {
      if (this.F.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s956uqox9v9lu","HdW+Cx6B9cAD069FhO6WaWvyE/4uZ8IS3w1P9F3auN0=",-421294856142998478,-2047853193155850563,-3073873412089260715,1609789229681074434>()) {
            case -157168928:
               if (this.F.player.tickCount - this.hf <= 60) {
                  switch ((int)com.yiyiaddon.m.b.a<"so4q56l9w323i","lt4cehMizh1CGA9TBs7aiQindDPFPF/CQBzKphmB6XU=",-9023032210238562359,-3432439167216400962,-8051618112539739378,8571167395887905949>()) {
                     case -907678117:
                        switch ((int)com.yiyiaddon.m.b.a<"shqqnygka8i9b","B5Iwn70Rt234XaKJYIIUpM3T8HGQ5zX6TBLoTmHZYP0=",-5955957628764421880,-1369347030337303335,2008977732520156197,-4257961436473369764>()) {
                           case 595781099:
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

      switch ((int)com.yiyiaddon.m.b.a<"s1qxwiq828ehq0","jGa9RYmI8lsKpYwoc1tNiEsvsZcRT53/ng/X3IApbvo=",2310576332136665245,-4387439184179754909,6184120475323736762,-8573333967918990281>()) {
         case -754441628:
            return false;
         default:
            throw null;
      }
   }

   private LivingEntity a() {
      return this.a(true);
   }

   private LivingEntity a(boolean var1) {
      LocalPlayer var2 = this.F.player;
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"szrre4l8ph35u","8ZhDodWiawvbWlzWMv8RHgZ1MiKYfxkerxOn7nkz468=",8657252985615673631,7027666668792044919,-7673235720313301953,-4282548918997969511>()) {
            case 495370417:
               if (this.F.level != null) {
                  double var3;
                  if (this.bX()) {
                     label79:
                     switch ((int)com.yiyiaddon.m.b.a<"s1y4rhxv36s4so","3ulau+LbVpYvXc5e1n2pTrZ4yeIV0e6Jjohy4RYFyEw=",3641171169286649186,-8342760893822252784,2339075995855167223,7590435778401696779>()) {
                        case 1998858304:
                           var3 = 16.0;
                           switch ((int)com.yiyiaddon.m.b.a<"s3rdh9t8toph94","2An995+K8EgSoSG1RXl0B7bZDzb7PcpDNiFNsTinZC8=",7576254427225935891,2440312632210560785,-3536541432315642750,-6934953932451011695>()) {
                              case 1418213506:
                                 break label79;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     if (!var1) {
                        return null;
                     }

                     label82:
                     switch ((int)com.yiyiaddon.m.b.a<"s20ufufdy6krky","u4zW2XFGV//7F3NInkJMvSBjBH7CTXb9xfuDk7Txuvg=",-6340690671300953760,2776706836984923148,-6468698901941746434,-5689465100484112099>()) {
                        case -261505445:
                           var3 = 6.0;
                           switch ((int)com.yiyiaddon.m.b.a<"s26pp1viiom57j","fW/0CPiP8SOdH8iSSvjZt0nXf6rgg96HC9eO89i2MpA=",-4709278887171707329,-3238733268972583967,-3141174717971532127,5254780682116381688>()) {
                              case 338667395:
                                 break label82;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  List var5 = this.F
                     .level
                     .getEntitiesOfClass(
                        Monster.class,
                        var2.getBoundingBox().inflate(var3),
                        var2x -> {
                           if (var2x.isAlive()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s88mdt58oa6nz","2rCvmSs5ygb6JbzExX5DULuKnwbuSH1E9dgvAG2+ILc=",4280695804993480921,522620761031858820,200496520432808276,9123459796845947708>()) {
                                 case 821506197:
                                    if (!var2x.isRemoved()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1hamni9136zru","gMrOSdwaSqNn5ItsXPeG3i23yABO/TcAEPOU0PV0vM0=",5792533635183201524,-3615297414624260302,3934678532256410600,7511746493783918934>()) {
                                          case 1722206700:
                                             if (!var2x.isInvulnerable()) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s330he7r2qiivi","+qOcdb0v8LboVYAjoVGTOQZKSTgjiehbplIRoC0pUp4=",1908914507844637598,-5156676121381511502,2424495081019784997,4590208687504499242>()) {
                                                   case -1762454713:
                                                      if (b(var2x)) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"sc20xx82ofgdt","XNRlGroV1uLV3bQm1OzEkQ1pvVBC+MSPQvZNxEKBw0k=",-6695377220332698421,-7017815380802104691,4173524652412574594,3004757331902657228>()) {
                                                            case -158149893:
                                                               if (this.a(var2, (LivingEntity)var2x)) {
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s3lhdmd49qapga","DlAviH5CdDWU60DlHx7tpQRN4wIqZ2HIMbDvephpy4o=",-1725707157674071242,-3146118672104138635,-4846974406966093496,3007212458768484360>()) {
                                                                     case 1586639743:
                                                                        switch ((int)com.yiyiaddon.m.b.a<"sgifsbfs4gijv","wEqEhfMJ0QcttaJDRFVhSwuH+bkdYFfl4UOdbpKAteA=",-5252680001160384036,7976643242746680968,4518010643543423742,3947426072405528461>()) {
                                                                           case -1903071274:
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

                           switch ((int)com.yiyiaddon.m.b.a<"sgc9jx9r7oprj","Fdbmp9T2+Y2ErdPrYVpCTQaI+LmLUjDHH1JvUwJWVlI=",1071059107077493256,-2568502668676302686,1344212869833478108,2135845514839686500>()) {
                              case 1506124993:
                                 return false;
                              default:
                                 throw null;
                           }
                        }
                     );
                  Monster var6 = null;
                  double var7 = Double.MAX_VALUE;
                  Iterator var9 = var5.iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s3oxhvhegcqj8u","75SfdWvmM7ASZRJV3vLXctDauK/RwlCQx0g4FdJ+Qws=",-5047738678433489362,2735494202791240321,5295848806291718559,-3085055982894594965>()) {
                     case 109266270:
                        while (var9.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2plpz8budsirn","8AIAW7qJFdFnPdU1JSpHUj8FDZROQbTDCKm7ibUuqbM=",-1171185366674439301,-3157139062513959060,-5507276160008272822,-1036082714730070435>()) {
                              case -1065909656:
                                 Monster var10 = (Monster)var9.next();
                                 double var11 = var2.distanceTo(var10);
                                 if (var11 > var3) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2pvwuw5y3heg3","bXYCyk5d1bsfN25cMMJt7nHQIkrce4QqrTwnXW1XI34=",8756868080309772013,-4443079947035490736,-1650330145079874121,730364547503620376>()) {
                                       case 1507538225:
                                          switch ((int)com.yiyiaddon.m.b.a<"s13y1gd711chkx","iGziZ2Pa1qhzmfR82aykHdoAuvC0Pmp7HGFJJrOyzPE=",-1658040000531164803,3737069402333325024,-5793199338582355487,1950611149514986737>()) {
                                             case -1964696294:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    if (var11 < var7) {
                                       label60:
                                       switch ((int)com.yiyiaddon.m.b.a<"s38skylljar0lt","kqfxv175bdvBLpbpaqqUzXA62E0UkGvghK2JIfYuA34=",5432751994456259030,4280463539904740190,-3260661556963987559,-3709192332575758435>()) {
                                          case -1759607771:
                                             var7 = var11;
                                             var6 = var10;
                                             switch ((int)com.yiyiaddon.m.b.a<"s2giwj3n2j6x1","hT4hiUoV3eCWQb880tjGd91Cw5BbbPbzsjbnXYUq5Xw=",-4304173039413545351,-3877736752725574019,-7428239073683987765,5750184249324845357>()) {
                                                case -1026088851:
                                                   break label60;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"sv56ep61ykdqw","gVweJQdCesD5PxG6zX0oDFnwnG9Ie0vnQfE2nRrZ8B8=",2454767559295478129,1613385238285689341,7144581109699006245,4802710017976231814>()) {
                                       case -922187828:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (var7 > 16.0) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3ej5qw9od7ywd","0oP/UNRRoNME61hcyt/ox5doJ3d8Jw/ROEbgONrIIFA=",-782819901722614724,-1632433454609129797,-3441724723403528891,3129625515432559400>()) {
                              case -1643019598:
                                 switch ((int)com.yiyiaddon.m.b.a<"s1h5pmnwr5djlv","KgL9d757Rh8QumJf9BSK8JAkk3w5NlVrf8ZQKuauHjI=",-1218233183986382274,6598287304550753405,-4553134976090450991,-6650188592619720057>()) {
                                    case 1937836246:
                                       return null;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           switch ((int)com.yiyiaddon.m.b.a<"s3bui3l1c2fc7v","hinrC/qCVb8GRYJg0eFALdPq52oHzlYk2AAFUK27tl0=",-2749021607053578179,-4449889503986828356,3676925484148076453,-1862589910296786486>()) {
                              case 866807373:
                                 return var6;
                              default:
                                 throw null;
                           }
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2umme4iva39il","IRi80J5Unr2N7wSD4DlBHF55mBhEFp4Uasz8mQHGK/U=",-3296219303570051352,5709705249325091151,903816269887542917,-7560938410327823486>()) {
                     case -256431252:
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

   private boolean a(LocalPlayer var1, LivingEntity var2) {
      return var1.hasLineOfSight(var2);
   }

   private static boolean b(LivingEntity var0) {
      if (!(var0 instanceof Warden)) {
         switch ((int)com.yiyiaddon.m.b.a<"s3m7x0eg0mjvr0","OWGPSmpSJMvlS6zt5kitpQP2zDNF3VBF2Gu5BOXKMd8=",7329005309925329781,8272706433096686683,-8676536318268581850,-693610898324418093>()) {
            case 245664865:
               switch ((int)com.yiyiaddon.m.b.a<"s2v0g514z2ay5t","4KQCXzxZbkFGv7XH2s9Xz6t+CMVwpxrOq5ofC3ROuek=",-9088028664579533917,1379441770764277256,-754975677987552159,5398150501581131909>()) {
                  case 225229809:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1kzwyotwnqv6t","SL8jvLLcoazppQMc3XN3xziTVrTwiOax10RZe/CYOAY=",-3626797488497404359,-7262400071344868167,-4057510086254197958,-7858926490955140746>()) {
            case -2069975489:
               return false;
            default:
               throw null;
         }
      }
   }

   public boolean bY() {
      LocalPlayer var1 = this.F.player;
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3fn93oczqd4ev","tR0F5Jzd4BgdNyhz7WAqf4GVYFUclyi0pWCU9gsIxKg=",1269794874413356597,-8451021209783694310,3388120594966905449,3158767784887103618>()) {
            case 1975092434:
               if (this.F.level != null) {
                  this.eH();
                  this.a = this.a();
                  LivingEntity var2 = this.b;
                  if (var2 != null) {
                     label111:
                     switch ((int)com.yiyiaddon.m.b.a<"slw3cxp9tufm8","QKIi6IiSDZ195HKUTezMnfzs2IFAo/3XYzAG60qilR0=",-6444732175204325816,-7125153179563922023,-367125518008084820,3958961470354309206>()) {
                        case 520492003:
                           if (a(var2)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s29p7ag2v616oa","DgQ4Or0K+Wa64FgzE6k0BewHLWTjTPQ0By3M348NudM=",-1727619071944545385,5316362751060973879,7615536770921876162,-3366464544717873024>()) {
                                 case 1197110370:
                                    LivingEntity var3 = this.a(var1, (LivingEntity)var2);
                                    if (var3 == null) {
                                       this.b = null;
                                       this.a.K(var2.getName().getString() + "");
                                       this.hg = 0;
                                       return false;
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"ss3qlofxt1kxd","kPeQTyfhmiNxwZ8FByxgToFi3dzzInIb5pLmLS85JXQ=",464197604299842406,1908028957323950248,-5748403914094211050,6054917965082702310>()) {
                                       case -790706677:
                                          this.b = var3;
                                          this.a = var3;
                                          switch ((int)com.yiyiaddon.m.b.a<"sysdoo5iqd4et","4Tfw7VV2acu4DoJRE4QVuZOzhJ5b2IaqFCUGZbIsVoU=",6439089039824486597,-2829416438776452331,-7945275916795774809,1449086881569833303>()) {
                                             case 567098295:
                                                switch ((int)com.yiyiaddon.m.b.a<"s30imwenoaytc9","c1y3boUfj9LRhs4rdwrt8XyOzHLhXR+EELHWpCqk6aI=",-5164366607939310054,-881946815824812437,-9101515779066193619,-665432012081229570>()) {
                                                   case 65968648:
                                                      break label111;
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
                           } else {
                              this.a = var2;
                              switch ((int)com.yiyiaddon.m.b.a<"sxikeh9hlam17","KmVcUD0ilHTEUR0fjmyXa4zn6WH/BupfBO1L+zi5bQs=",-8066462406778681486,-3708027975698407528,-7137930401294353277,3632227701478206030>()) {
                                 case 1388036199:
                                    break label111;
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  }

                  if (this.a == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s21k1wszh98pep","utvLY4zeYTPYijNmZdhSNz4sdKDc+eaa7OFxbLLKId8=",1408043989758431639,4913812867370348410,3837067166325941764,120463828454141382>()) {
                        case -197373162:
                           if (this.bX()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s30gyjui3fqbed","0WlGKmie6wZzCaohBFrft63ihRuCXwVQzU0JTaI4I68=",7425667289284633702,7384933640848986176,839056525780886335,-8678059673156150324>()) {
                                 case -420935141:
                                    return true;
                                 default:
                                    throw null;
                              }
                           } else {
                              this.hg++;
                              if (this.hg < 30) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s10j2qr825w27h","ejs+iKCZuKKGEnXHHpNQRDJrlvOUN1aIgvMaXtShyRs=",2096839881386015424,-4846794346668364922,-8379133258377233324,-1567683130284932176>()) {
                                    case -105069134:
                                       switch ((int)com.yiyiaddon.m.b.a<"s1mutbx80f5pcd","nNV0rUtoGItUCn7wsr1nX/VLZ5p+bmpTLFlFsJiD+n8=",-6954469999346188731,-1212467650921352447,6442360297169094360,9086104375945857479>()) {
                                          case 1647744844:
                                             return true;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 switch ((int)com.yiyiaddon.m.b.a<"s85r9owlrasgn","Dz1n70kterABQrFSP0ISZbXoDic3UrWG96zFLZbb/08=",-3503486112105073614,-1872289115464559159,-785102630225854312,-6731499396031641719>()) {
                                    case 1609590436:
                                       return false;
                                    default:
                                       throw null;
                                 }
                              }
                           }
                        default:
                           throw null;
                     }
                  } else {
                     this.hg = 0;
                     double var10000;
                     if (this.a instanceof Creeper) {
                        label94:
                        switch ((int)com.yiyiaddon.m.b.a<"s2yxmi87m4zhq2","LFk4sq1D0UjREGApc9hatHAUxVBsx05nORPAzSJ9vso=",-8620893330082552707,-2133076715644818387,2423349211591544288,-5160562205974176164>()) {
                           case -1283855782:
                              var10000 = 16.0;
                              switch ((int)com.yiyiaddon.m.b.a<"ssrh5aeqvlhm0","nj1WZ95Fz/GdZrqwI+E3iY21Fbpi2rL8D06CtD4GVZQ=",-5461015221080872619,-4639969286333830148,992305785629610414,4612599387957952918>()) {
                                 case 295303176:
                                    break label94;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10000 = 6.0;
                        switch ((int)com.yiyiaddon.m.b.a<"s3l5s57li4yz07","sJkQeJuE4HadW311C/qfzpGlIDgXtLUM1uUp6zUrRYc=",3690612510307620313,-8944381359663911196,-1039202522613656303,-7969860886598899558>()) {
                           case 1906392020:
                              break;
                           default:
                              throw null;
                        }
                     }

                     double var7 = var10000;
                     if (var1.distanceTo(this.a) > var7) {
                        switch ((int)com.yiyiaddon.m.b.a<"s5j0kz08tdad","0kKvmtbXEf1h2dRiQv2RQfhD1L7v6CmjoQ84GWNpLDw=",-1173679518392118105,88185493293409349,-6629179257032594371,-8630590208397349321>()) {
                           case 369613732:
                              this.hh++;
                              if (this.hh > 200) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3s6gbn9ohyw1t","AlVdibyKZpas4f1o08VOhXqEt1SGjsuZV35SZOLTK6k=",-3909387098257561276,-1627411538764187564,6666105298267218367,5668398346233503196>()) {
                                    case -779856102:
                                       this.a.a().ag();
                                       this.a.K(this.a.getName().getString() + "");
                                       this.a = null;
                                       this.b = null;
                                       this.hh = 0;
                                       return false;
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           default:
                              throw null;
                        }
                     } else {
                        this.hh = 0;
                        switch ((int)com.yiyiaddon.m.b.a<"s3go2lv9p1ts7a","Bd1WzUnE8gsi4e/hXpRhb+lFgjarM3bwUlQJKy8zOQU=",1224192842246421364,-8072606447663074751,-9191618356703728676,-8916740047406351182>()) {
                           case -1113013758:
                              break;
                           default:
                              throw null;
                        }
                     }

                     this.b((Entity)this.a);
                     this.b = this.a;
                     LivingEntity var6 = this.a;
                     if (var6 instanceof Creeper) {
                        switch ((int)com.yiyiaddon.m.b.a<"san8gcxwvmeh7","BH/+jwtzQFu/7aeIq1Bw3m5vlsE6HA3EZghbg5Tr2G0=",7453459940153824009,7944849977313442834,-9190827447844980832,-6976424072682437090>()) {
                           case 665186956:
                              Creeper var5 = (Creeper)var6;
                              this.a(var1, var5);
                              switch ((int)com.yiyiaddon.m.b.a<"s9dxboe3rlkf1","Rh9xvYYqa9siPU8lcahCARLRuR2XJzC/Q8rdsAPFdHk=",-444759188224055356,6463162308902775584,-3654396607000887956,7384535793896306878>()) {
                                 case -1147638822:
                                    return true;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        this.a(var1, (LivingEntity)this.a);
                        switch ((int)com.yiyiaddon.m.b.a<"s1cpork5yiducr","1c2Jwbl+ygwGf1omXwEvsyWycmTTUypsf4UmpgdsRsk=",7019527373737262475,3901767338989923282,-9017937518831908840,653602624379603874>()) {
                           case 38611839:
                              return true;
                           default:
                              throw null;
                        }
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3trejkhf2e1ay","uoyxMTzPxXweFfj7EdjmFig5SFIVKqkSdyF8INUl6E8=",8944457786167348865,-3781348050050552413,-8225185116174499017,-7392186751622667197>()) {
                     case 1494349375:
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

   private void a(LocalPlayer var1, LivingEntity var2) {
      this.a(
         (String)com.yiyiaddon.m.b.a<"s1y6e6ewmzri3","LgUylzgyAJ0GEYq7ucN0lS0RkAL64gsuZCZgfX18tck=",-8946488713006183928,-8413182115065643162,-2726205048439923094,6074835413786859232>(),
         var2.getName().getString()
            + String.format(
               (String)com.yiyiaddon.m.b.a<"s2tg8a5jjynlp0","9kzgvKhY3DfXaShEDCvICWZhdqJldqLGlopECBQ7Gi1A+ODr",3068650447201522675,1020353640067299061,8100236331794132421,-997206492290634377>(),
               var1.distanceTo(var2)
            ),
         var2
      );
      if (var1.distanceTo(var2) > 3.0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1p8tnuf7zzafn","qZBTu4xp2FxqgEfY375NEDF4kJNSueMwaJ1gjI/+VYg=",-8592828044113007527,6142786534632947909,-4494885483983937115,-6847803067156842812>()) {
            case 654306220:
               this.a(b.a.APPROACH, this.a((Entity)var2), 2);
               return;
            default:
               throw null;
         }
      } else if (this.bZ()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2qgldre9xh0ih","boR+tbfmKP/lngs4DhtR+Ny3WfPFuACUH/DMVa0yNjA=",-4849588732064074020,5276970059376779926,-4764816135763282140,735679196044588766>()) {
            case 1043892530:
               return;
            default:
               throw null;
         }
      } else {
         this.b(var1, var2);
      }
   }

   private void a(LocalPlayer var1, Creeper var2) {
      this.a(
         (String)com.yiyiaddon.m.b.a<"s1y6e6ewmzri3","LgUylzgyAJ0GEYq7ucN0lS0RkAL64gsuZCZgfX18tck=",-8946488713006183928,-8413182115065643162,-2726205048439923094,6074835413786859232>(),
         String.format(
               (String)com.yiyiaddon.m.b.a<"s2tg8a5jjynlp0","9kzgvKhY3DfXaShEDCvICWZhdqJldqLGlopECBQ7Gi1A+ODr",3068650447201522675,1020353640067299061,8100236331794132421,-997206492290634377>(),
               var1.distanceTo(var2)
            )
            + "",
         var2
      );
      if (var2.getSwellDir() > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s29zurm0i541zx","KdYrb01ZvfE8263Xke9fBn6GuOyN0brvVYjjAXt4Qbc=",8041509352389022184,-7716483586238625501,-3987397872910770666,5545896217262478825>()) {
            case -1612479246:
               this.a(
                  (String)com.yiyiaddon.m.b.a<"skr0wod7xu8hy","xXMySIt0NXhY3R/Dfhv1dPr8/y+SXGkBvJtPEiGHtGg=",896566571598025138,5824528222796696223,-3283695731809870669,-6874113304234114223>(),
                  (String)com.yiyiaddon.m.b.a<"s21em7txbd4et2","kcL8Zftzt3Str5p6eP8VOoi0WZgQFzdAC1CmMwcgsKFvm3aAs1yNMWy+XOGzPt5EDvJG7YAUwJ0ZJaWH3U3HP45lxCk=",3471146175619524358,10194327158585126,1127503703674407259,1677098050873558191>(),
                  var2
               );
               double var10003;
               if (var2.isPowered()) {
                  label31:
                  switch ((int)com.yiyiaddon.m.b.a<"sjl4pzfm63o3t","YPkgf6l9/KnHpsWe960l3Qovh8pcbSotJhc0rsfCd5g=",-3083072578490010161,-6385094162486581075,-2381677868773085714,5973328591215693906>()) {
                     case -1564279692:
                        var10003 = 12.0;
                        switch ((int)com.yiyiaddon.m.b.a<"srfddcc1ww1dn","HEL1hD984KdnglFsRHQkqz9FjLrZiQSqle8kKVd2RoU=",-7741957542247350127,-6092752883336073131,-432635882563936278,9165603399207531492>()) {
                           case -488996009:
                              break label31;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10003 = 8.0;
                  switch ((int)com.yiyiaddon.m.b.a<"sn7koc8u5hx2e","Xnai9Trb5m4Q/FFCcZHoNMDIdbYsyO38FsTpvPQsjzI=",-8971113742802284541,-187733876885827046,-5385599886187815820,160046446296959971>()) {
                     case 1048869683:
                        break;
                     default:
                        throw null;
                  }
               }

               this.a(var1, var2, var10003, b.a.FLEE);
               return;
            default:
               throw null;
         }
      } else if (var1.getAttackStrengthScale(0.5F) < 1.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s3avh1tde80knl","ZXRTxoYKOhpopIKLWdRGNbyt85tZPWanZGS5ZSqpjiQ=",-6127511496789513746,-6467089454235133574,-6107412009716576676,5408821871342026956>()) {
            case -703864767:
               this.a(var1, var2, 5.0, b.a.KITE);
               return;
            default:
               throw null;
         }
      } else if (var1.distanceTo(var2) > 3.0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1gbjlkyj527jj","XSP0s1VGd3Q6e/pXUj1TcCLws8jjh9oSUqcSrSEzldM=",6898971507750860309,6686233334559641433,7324137965458243819,-3080108195041424404>()) {
            case 1498655737:
               this.a(b.a.APPROACH, this.a(var2), 2);
               return;
            default:
               throw null;
         }
      } else if (this.bZ()) {
         switch ((int)com.yiyiaddon.m.b.a<"shrnfot4xy2lt","vyqApNyOF+zLX/ai9FwlaXtwi32YEKiTnLvainNUDVw=",1280269378356011686,4054244973755507723,-8284283993013053367,-7502677646552757457>()) {
            case -1297933994:
               return;
            default:
               throw null;
         }
      } else {
         this.F.gameMode.attack(var1, var2);
         var1.swing(InteractionHand.MAIN_HAND);
         this.a(var1, var2, 5.0, b.a.KITE);
      }
   }

   private boolean bZ() {
      LocalPlayer var1 = this.F.player;
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"skfrvpu76dxy7","8AQEtVw5LFyGKcza+OHObTRHyMzuYaWLPrOB5oYJfw8=",2283808633298020427,-7085053227955369530,-7489547399989143083,4514979674132457599>()) {
            case -631380396:
               return false;
            default:
               throw null;
         }
      } else {
         int var2 = var1.getInventory().getSelectedSlot();
         this.eI();
         if (var1.getInventory().getSelectedSlot() != var2) {
            switch ((int)com.yiyiaddon.m.b.a<"s8kkf69sjzpjb","nvgmGoy5w0vV7eEywHE+Z823qHINzJ08HQY//pgETPE=",5678174194872857454,7472513931963106802,8949097754593509269,-4394714175430075458>()) {
               case 318202035:
                  switch ((int)com.yiyiaddon.m.b.a<"s3bra4s751iguh","yaOtzlhbhwi9HFaIvgY/Gg0qXKXlk72oAX2h87Ommz0=",-7001427330638224131,-811052416431324213,-2474171607559406752,-1565457658758639499>()) {
                     case -2122419169:
                        return true;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s2xan61qij9ewo","d5kzAV9AtGQjYuga5JsBXhwOcPXmVCllox10opQ22Y8=",5034084368848583458,-1178694435582221201,2550588899470469133,2580517707839771029>()) {
               case -332141283:
                  return false;
               default:
                  throw null;
            }
         }
      }
   }

   private void eI() {
      LocalPlayer var1 = this.F.player;
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3hhfy2bzytzch","6bC3Of1R/JTt7aUH7Hnqx0rwZfJoA0k20h+coXJMvzA=",3545332836948759855,-5394587740932030757,6557626531465162771,1620454533163770151>()) {
            case 28416848:
               return;
            default:
               throw null;
         }
      } else {
         int var2 = this.bw();
         if (var2 == -1) {
            switch ((int)com.yiyiaddon.m.b.a<"s1vs629ct7ay6a","VYKpI7xwydsdtdPORs6tIz2BRxh8hs2i1ced75cypHo=",-1136296363859395725,-7840516757342108818,-5574809729391365377,4168622082094446276>()) {
               case -1829963051:
                  if (var1.tickCount - this.hk >= 20) {
                     label55:
                     switch ((int)com.yiyiaddon.m.b.a<"s6ob0pubtv60u","mIIATckkNEDjFGIOUUs2yXgUuT0gEwm5Hv4Zj39TQDQ=",6597910930768556452,5641648768846980279,-4612893511111522736,6446429140504179464>()) {
                        case -1437891742:
                           this.hk = var1.tickCount;
                           this.a.a().cv();
                           var2 = this.bw();
                           switch ((int)com.yiyiaddon.m.b.a<"s1q40pd1mwba7d","IO7cGCkyOkVkhxFXmCfkw/huLoQArjwPhDI8WE19SnU=",-2707855338724842578,-7198108291961139114,1725775030135566151,-2077236199000608170>()) {
                              case 1687257255:
                                 break label55;
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

         if (var2 == -1) {
            switch ((int)com.yiyiaddon.m.b.a<"sugy3js5afyd7","IwpGeZD7A73cwJUarN3NZQt0jNEI7IMuuEOVVTUfZ4k=",1809077825836148551,7209469826279651933,-6665792699755293445,-3033840159288592364>()) {
               case 796554515:
                  return;
               default:
                  throw null;
            }
         } else if (var1.getInventory().getSelectedSlot() == var2) {
            switch ((int)com.yiyiaddon.m.b.a<"s2ngzg5z2u93hn","MbqBqHQgnFDjLE/FgpogkCDEn062nMJZWuXNMaZzhCs=",-2514422520855392126,7772971101750189178,8580073946861330738,9000017865009266916>()) {
               case 983695089:
                  return;
               default:
                  throw null;
            }
         } else {
            if (this.hl < 0) {
               label46:
               switch ((int)com.yiyiaddon.m.b.a<"s2e4apou2vc87q","izlBdZpPEUOz130JQq3ab3VIIOhsU7maIsPCm1fsyL0=",-2084294628347305298,-530019509122375237,-2666455691975610114,-6261395457995692874>()) {
                  case -1501896046:
                     this.hl = var1.getInventory().getSelectedSlot();
                     switch ((int)com.yiyiaddon.m.b.a<"s1o4wckpaonoj5","097MXIWwRzCzoWvWcxRpeg4WhxombyjoQwikympDTSg=",5446915687644454824,-5716517171762744196,8632510141472535339,2165296383414846603>()) {
                        case -118990668:
                           break label46;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var1.getInventory().setSelectedSlot(var2);
            if (this.F.getConnection() != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s11207rmu2cc3s","ryRJCqOwhd1pWeMSv6Xqlk5oLsCare4A6aeaIwMSYSc=",-1011547385316188106,-3404977428491193879,1905622691462622152,1834653624082054873>()) {
                  case -1496982946:
                     this.F.getConnection().send(new ServerboundSetCarriedItemPacket(var2));
                     switch ((int)com.yiyiaddon.m.b.a<"s2iqd6wvflgkd5","Svbq02v7/G8HlFcGBF6o6fEl8gWlai6iCgeRZzzeA2I=",378899182831316318,4093148600371695360,-7269014247173593422,2191030669512703770>()) {
                        case -1180054244:
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
   }

   private void eJ() {
      LocalPlayer var1 = this.F.player;
      int var2 = this.hl;
      this.hl = -1;
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2dt3i0qvgphnq","iLp3WSgmuEqiOYGeoatLPlA9jhznVwrxuFUs+iJ1wTs=",-2071349876802915240,-5297046261084116580,7235653075839494048,7598681854865679517>()) {
            case -717720656:
               if (var2 >= 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2o5wj3h51itl3","3fsNU5JbnIvOhwPTyxK4+P7HfDlXs/qBBpLdwAbYTfw=",-839927883448671593,-699136696164998380,297821560562786961,2634681500343792726>()) {
                     case 384890959:
                        if (var2 <= 8) {
                           if (var1.getInventory().getSelectedSlot() == var2) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2811ikj6xhfmb","cgSr4OxXPkpMpUz3G1KVDjrnSqji8llGL5ghcsYUOms=",2312508455407449574,-139034588488194206,8653311351729054658,-2897888081853529423>()) {
                                 case -1224933139:
                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           var1.getInventory().setSelectedSlot(var2);
                           if (this.F.getConnection() != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"sb1dscy8r60l2","yQJ4n+rzoEZiIF8iN+dLh5ccV8jAMzJgYNTni+uQIvk=",-3169924233199090388,-8229639594593401436,4413009188708814876,-8776107620663369895>()) {
                                 case -483793684:
                                    this.F.getConnection().send(new ServerboundSetCarriedItemPacket(var2));
                                    switch ((int)com.yiyiaddon.m.b.a<"s2j9ay7mqyfnyp","Klk65LqcALC6s/ZTM+oD5VUZ6qFMSVx0AwSbxejon6E=",-4693800384472103370,225517931627842905,7785956564831987709,4819907647574920090>()) {
                                       case 2135591039:
                                          return;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3jjlz1b2vwvhu","1zz6jbD1XQkKaA1Cbpn6yirW7rbvJLZBLK8FMt9gPUo=",-6834562190260153988,3275359915515818736,4862921151319226526,-7188055727253616498>()) {
                           case 183196063:
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

   private int bw() {
      int var1 = this.c(var0 -> var0.is(ItemTags.SWORDS));
      if (var1 != -1) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ete491hr9m8y","bGsLUW8tnycjE+Or0zkShYJ39rL4E7kz8QbACcQYMOs=",-3559019943403264274,1803518219666364891,-6965165396982273719,-4431658945081330735>()) {
            case 105239014:
               return var1;
            default:
               throw null;
         }
      } else {
         int var2 = this.c(var0 -> var0.is(ItemTags.AXES));
         if (var2 != -1) {
            switch ((int)com.yiyiaddon.m.b.a<"s3a6zjlgiktwyu","t+HILQ72wGGqLdPOsEr+TaPOzNDKB5cPrKPvMP7OoSI=",-4834239937243699180,8965649129946244433,-542537449194442762,-589818974589592062>()) {
               case -713805653:
                  return var2;
               default:
                  throw null;
            }
         } else {
            return this.c(
               var0 -> {
                  if (!var0.isEmpty()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1jckaw7q7gz0w","Bd0ULK1Kvm7cQyuSWU63Mh7cX0k2pXf4zOm+zVl6qGY=",7430841830839617709,-3563905617070737245,6255408988767721668,-7075850191732310380>()) {
                        case -240417161:
                           if (!q(var0)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s22d5tlz419r9y","Vxt8mzDcUrcs+UUzc1fJnRqK6DmvDFtX8J/ASFYl1U8=",-6119099677718184610,8453964085624919902,7685249306055169867,-5117218740380901898>()) {
                                 case 1882725711:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3omzkn3urm83y","nDYAFrHfY4dKIGVOFqabP5PnhkEXrEhcjDholDJdhb4=",-272307489282583954,8034059883802267102,5063353703468673190,-5936969181423684152>()) {
                                       case 943523599:
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

                  switch ((int)com.yiyiaddon.m.b.a<"s29a6vck1uk7wr","Lx0+ePSu9YvxIA8cvc85W4Fp87q7daJmUqtpfApahrc=",-5326149045107382982,2329600212946922133,6641905909622580241,1666074426072397906>()) {
                     case 248621020:
                        return false;
                     default:
                        throw null;
                  }
               }
            );
         }
      }
   }

   private static boolean q(ItemStack var0) {
      if (!var0.is(ItemTags.PICKAXES)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1qczyaer47bxu","xymB090YxIa3aDTufxKVR4PEfbKSWeby9zOUKOkevYc=",-618691111629511135,-8778453085841475036,2814432149230439772,1280812886832977596>()) {
            case 1948445604:
               if (!var0.is(ItemTags.SHOVELS)) {
                  label25:
                  switch ((int)com.yiyiaddon.m.b.a<"sk99m5jup1s8p","hw4YQl4XozQbtNum2wzu1HsYdeMpv3CWpIHmygiCB2k=",9173760602665885541,-4521973439264046457,-5452863099168444248,2363796494423308065>()) {
                     case -676078749:
                        if (!var0.is(ItemTags.HOES)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s27yv9bwcnaw6p","ZRyGkH70r++0ZNr6s2RCXe16llMA7jgdxxE8kNdVWmw=",7232155917407421002,4970890466498754648,-5831320030458503149,7034578265420893334>()) {
                              case -983023915:
                                 return false;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3fmfukpm2iiwk","VaJee0SFOTsAfNjie1La0818mHkiLjha/SC1lJnJybs=",5498441865821287933,-5104371961470916481,-3971106289334084703,-5044629233527537898>()) {
                           case -497638598:
                              break label25;
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

      switch ((int)com.yiyiaddon.m.b.a<"s154uns4mexkb4","PwkpnDSIxGDswwsEy6zhoKEPudRh6iwWK4x0xaB0syk=",-1139450507554264883,6782179787579709569,-3618779489791128471,-722228491223817580>()) {
         case 1091236241:
            return true;
         default:
            throw null;
      }
   }

   private int c(Predicate<ItemStack> var1) {
      LocalPlayer var2 = this.F.player;
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ozy6ywkwsfgf","/GmAU6h7YdOcZepN93RnAugs54EmhP6lWoFvXpUu2m8=",-3567836857198379218,3277871375944882775,6407580779234655479,-1002225932351721253>()) {
            case 2136807774:
               return -1;
            default:
               throw null;
         }
      } else {
         int var3 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s3n2o7pwsu17dn","/hK0265niKKT4UoHd7skSH8t92VbjGYXc77XpVGLvSs=",-4286774237000249222,-1087305762303395942,6299612130554477060,8122174004475920407>()) {
            case 130280658:
               while (var3 <= 8) {
                  switch ((int)com.yiyiaddon.m.b.a<"s30rnvqa3kzp41","UIYIX631cSXpboxldyhsxXxhUVMncsm1dO/Ih7j7n34=",3737379791200142565,1196501619328516858,-1657031899500087765,-4918106587715900542>()) {
                     case 518319143:
                        ItemStack var4 = var2.getInventory().getItem(var3);
                        if (!var4.isEmpty()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1wnaf384s35b5","s26Lt9tydPvWQsPi0xASbomJXkyq0Hmp9uqnoO+CcS8=",-2576428265902244777,7593863155789768806,-8195379673059936151,-8568046435033912359>()) {
                              case 1527685017:
                                 if (var1.test(var4)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2jupwta8hoqiq","IfgLu2Fc7/B2BHxmDOTXnRmhoqaOvuy6pW/KpleBMXI=",5794402508818651754,2196731128784056286,7320451551304090333,7773013323836411193>()) {
                                       case 1348737906:
                                          return var3;
                                       default:
                                          throw null;
                                    }
                                 }
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        var3++;
                        switch ((int)com.yiyiaddon.m.b.a<"s1u09j8rrbf2z2","ZzhRHRDvMbdwalhO8WdB7yMv+LuVZ6rbzewkYt0UGMQ=",2327333768625919213,-8482304707951331687,-6447473382799822396,7807841621095192652>()) {
                           case 614708732:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return -1;
            default:
               throw null;
         }
      }
   }

   private void b(Entity var1) {
      LocalPlayer var2 = this.F.player;
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s29xr4fee3upvk","2jk1tOR6d9M8RZOXnx7OspEmHm3/cG9aPlh1w5Itfm8=",8560674486210642611,4958128309271453752,-2781095174883548950,-6461809311370991755>()) {
            case 420063519:
               if (var1 != null) {
                  Vec3 var3 = var2.getEyePosition();
                  Vec3 var4 = var1.position().add(0.0, var1.getBbHeight() * 0.6, 0.0);
                  double var5 = var4.x - var3.x;
                  double var7 = var4.y - var3.y;
                  double var9 = var4.z - var3.z;
                  double var11 = Math.sqrt(var5 * var5 + var9 * var9);
                  float var13 = (float)Math.toDegrees(Math.atan2(-var5, var9));
                  float var14 = (float)Math.toDegrees(-Math.atan2(var7, var11));
                  var2.setYRot(var2.getYRot() + this.a(var2.getYRot(), var13));
                  var2.setXRot(var2.getXRot() + this.a(var2.getXRot(), var14));
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1anv87wfhdsam","bo/HLixyD4fCRviGqlj1LTky+ZQY4amorFnlcQTr2lM=",1167547706351504508,-2818982665130779279,-2444090571503903444,-8817503026556725990>()) {
                     case -1317541266:
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

   private float a(float var1, float var2) {
      return Mth.clamp(Mth.wrapDegrees(var2 - var1), -18.0F, 18.0F);
   }

   private void b(LocalPlayer var1, LivingEntity var2) {
      if (var1.getAttackStrengthScale(0.5F) < 1.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s23l3ro7qlq3l9","Ncc4fZF9RECrXettSvx6k9Z/aXYmAayyeTzv2dSGImA=",8654156390516991363,952769810841399919,-5862960969133408797,-5774848052990143489>()) {
            case -1815138272:
               return;
            default:
               throw null;
         }
      } else {
         this.F.gameMode.attack(var1, var2);
         var1.swing(InteractionHand.MAIN_HAND);
      }
   }

   private BlockPos a(Entity var1) {
      Vec3 var2 = var1.getDeltaMovement();
      return BlockPos.containing(var1.getX() + var2.x * 4.0, var1.getY(), var1.getZ() + var2.z * 4.0);
   }

   private void a(b.a var1, BlockPos var2, int var3) {
      LocalPlayer var4 = this.F.player;
      if (var4 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1arvjcac2kkch","Eto7AxCbLxmjFtSV7kjLDXUkFgNI7KMNujFd7eKP/Uk=",-4344782002572181474,-7653892361258340637,6657977184251759834,5820061727464798124>()) {
            case 1391620588:
               return;
            default:
               throw null;
         }
      } else {
         if (var1 == this.a) {
            switch ((int)com.yiyiaddon.m.b.a<"s3mj9o4ljk5xz7","LkFyT30b33dcbttxm1ph998/B8KUZzEEG1tehp1ZUPQ=",-2511074532247609519,48239199688838276,6872710238339323475,-7150317354476966822>()) {
               case -2064576601:
                  if (var4.tickCount - this.hi < 20) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2vaqz140pq6lv","uTUKamNlWkgk14jE3wpS2OHB6KvSC7A2ZVgFynU+3NM=",-981637668062886157,-7127278223752156865,8295761357889344511,2916297584281921946>()) {
                        case -2026912095:
                           return;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         this.hi = var4.tickCount;
         this.a = var1;
         this.a(var2, var3);
      }
   }

   private void a(LocalPlayer var1, LivingEntity var2, double var3, b.a var5) {
      double var6 = var1.getX() - var2.getX();
      double var8 = var1.getZ() - var2.getZ();
      double var10 = Math.sqrt(var6 * var6 + var8 * var8);
      if (var10 < 1.0E-4) {
         switch ((int)com.yiyiaddon.m.b.a<"s1g1pz9jpxoq2u","axsm+t4oaHzeTfZ1OATUZHQGl9edj/azdguvZRDv1sY=",4618505388061586020,7552315942893980542,1782368882530783786,2260819244800066913>()) {
            case 1517545308:
               return;
            default:
               throw null;
         }
      } else {
         var6 /= var10;
         var8 /= var10;
         double var12 = var3 - a(var1, (LivingEntity)var2) + 1.0;
         if (var12 <= 0.0) {
            switch ((int)com.yiyiaddon.m.b.a<"s2pwsut3g0r43q","r52tqkiB4Ut2fdlPvwfOMsrlpMhGDn0bOlQSHvVhNAY=",-473069217646926818,-4451929960391174971,7461834916592685600,-5979970824475127458>()) {
               case -311117357:
                  return;
               default:
                  throw null;
            }
         } else {
            BlockPos var14 = BlockPos.containing(var1.getX() + var6 * var12, var1.getY(), var1.getZ() + var8 * var12);
            this.a(var5, var14, 1);
         }
      }
   }

   private static double a(LocalPlayer var0, LivingEntity var1) {
      double var2 = var0.getX() - var1.getX();
      double var4 = var0.getZ() - var1.getZ();
      return Math.sqrt(var2 * var2 + var4 * var4);
   }

   private void a(BlockPos var1, int var2) {
      IBaritone var3 = this.a.a().c();
      if (var3 != null) {
         try {
            var3.getCustomGoalProcess().setGoalAndPath(new GoalNear(var1, var2));
         } catch (Throwable var5) {
         }
      }
   }

   private void a(String var1, String var2, LivingEntity var3) {
      if (this.F.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"swi2q9mqvicro","HIN5uajnpTsGPrvOlD1/jo5h2ax112YLGKSWot9Bqzk=",-4837658943388882483,-8089780803787360166,-1492450804983220443,-2158474559682655433>()) {
            case -1608013365:
               return;
            default:
               throw null;
         }
      } else {
         String var10000;
         if (var3 == null) {
            label32:
            switch ((int)com.yiyiaddon.m.b.a<"s1ddkppsthc4cl","lYibxHoMoDGz4x6xYB+1i4zPTPLegYeiMDmPodvQrqs=",-7929580794570439329,1790001152821739590,2244919487339144748,6753147412252059768>()) {
               case 657416836:
                  var10000 = (String)com.yiyiaddon.m.b.a<"s2no9r52wwzspo","hKvzM34ZmBirXrhdTmAkcmIprJQYJEgkMSzR5g==",3641741071354125898,-9074619837686925712,2091751452319718184,-558121964693406306>();
                  switch ((int)com.yiyiaddon.m.b.a<"sa4xsuef76068","RWKZBAUif9vM40R/BRD4+1L/OeO/VWTMx7sFHTZBI/M=",-1917571757172008237,-3046925046477038921,7144000330836607978,-7095435732200797033>()) {
                     case -1688266040:
                        break label32;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = var3.getName().getString();
            switch ((int)com.yiyiaddon.m.b.a<"s1dmn9sb09to1a","DOLR6c7EKlCAEfwF9brPDChTXFHx3L5M+0Pt/PVxfI0=",3312965681002517728,7445264971859923261,8435674034714918449,-8964430197095953075>()) {
               case -1202692706:
                  break;
               default:
                  throw null;
            }
         }

         String var4 = var10000;
         String var5 = var1 + var4;
         if (var5.equals(this.mP)) {
            switch ((int)com.yiyiaddon.m.b.a<"s1o5nc2uee0mgw","rCYMhePPzFEhkMGmqHrvQf8k0tDuaelomBhGXrP6XRw=",-8556876924104600199,7042346971024630250,6526185551862955518,5720550866143918879>()) {
               case 488880213:
                  if (this.F.player.tickCount - this.hj < 100) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1uve8ook1a2u3","dYY+f6jlD1HZGxo2PEQH+ytVHj3rb5CIP5afAKe5cbk=",-1183288976253437717,-3163817568913415289,5103074364532602939,-171266147572718306>()) {
                        case -1243054198:
                           return;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         this.mP = var5;
         this.hj = this.F.player.tickCount;
         this.a.K(var2);
      }
   }

   private enum a {
      APPROACH,
      KITE,
      FLEE;
   }
}
