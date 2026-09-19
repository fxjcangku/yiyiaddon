package com.yiyiaddon.e.i.e;

import com.yiyiaddon.e.i.d.g;
import com.yiyiaddon.e.i.d.l;
import com.yiyiaddon.e.i.f.k;
import com.yiyiaddon.e.i.f.m;
import com.yiyiaddon.e.i.f.s;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class d implements k, s {
   private static final int fP = 64;
   private final m a = new m(this);

   @Override
   public Optional<com.yiyiaddon.e.i.d.k> a(l var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sr4jijl5g558k","+781TA/ora/01HFQqkqGA5ULlrKqgKSXMFXiWP7HWGA=",-683462605304761513,4145482381201540980,-1441504240997379726,4846088895568221016>()) {
            case 1154331047:
               return Optional.empty();
            default:
               throw null;
         }
      } else {
         Villager var3 = this.a(var2, var1);
         if (var3 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s23j53jismg6p2","nNZm/zOm2uWhL47NCXBB8iJw3uJoeQQ8xmn66U/cABg=",6796611365585334197,4718782161367196200,8524932086333825758,-5231769653331639258>()) {
               case 974840165:
                  return Optional.empty();
               default:
                  throw null;
            }
         } else {
            com.yiyiaddon.e.i.d.a var4 = new com.yiyiaddon.e.i.d.a(var3.getBlockX(), var3.getBlockY(), var3.getBlockZ());
            int[] var5 = new int[]{0, -1};
            int[] var6 = var5;
            int var7 = var6.length;
            int var8 = 0;
            switch ((int)com.yiyiaddon.m.b.a<"s4w2lstufha9p","v3bUtBCeH45+qkZ5O4I1hnPGgd8QR/r3Hcmrzj6nnpA=",7101541770318697238,-8005138179379623827,-5963809346341268234,8616145642783196858>()) {
               case 379176695:
                  while (var8 < var7) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3jrezjl58dn5m","zoMaBu5NcI2Dq+9fZIydrEaAKihyFW0wfruEtRQJEyY=",5979535000540829238,6201859851949509731,-5493128267921709684,8618620408534256972>()) {
                        case 2044987636:
                           int var9 = var6[var8];
                           com.yiyiaddon.e.i.d.a var10 = new com.yiyiaddon.e.i.d.a(var4.aj(), var4.ak() + var9, var4.al());
                           com.yiyiaddon.e.i.d.c[] var11 = com.yiyiaddon.e.i.d.c.values();
                           int var12 = var11.length;
                           int var13 = 0;
                           switch ((int)com.yiyiaddon.m.b.a<"siufg05nysu4q","x+y7dxGPPAwqIQJHkSYJR1sqFobsNAwwFxwCk+Z107I=",5997998292323931478,7858851248362765822,617245287201925897,-6292809250995265813>()) {
                              case 1478612608:
                                 while (var13 < var12) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s102xs9ar1brv4","Sz6EKSKkK7IUOiMhfJ78taQwL+tPpNmqGFbBvXSlZv4=",-5804178320194782981,-4073467379347473600,8458011876663035803,-4332746890844200952>()) {
                                       case -1273622500:
                                          com.yiyiaddon.e.i.d.c var14 = var11[var13];
                                          com.yiyiaddon.e.i.d.a var15 = var10.a(var14);
                                          com.yiyiaddon.e.i.d.a var16 = var15.a(var14);
                                          com.yiyiaddon.e.i.d.a var17 = new com.yiyiaddon.e.i.d.a(var16.aj(), var4.ak(), var16.al());
                                          if (var2.level.getBlockState(this.a(var15)).is(Blocks.MAGMA_BLOCK)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"sr26sw9fva9lp","2kB+xOc3S6qI/toHYK8roGhf91l9XNl3ojgFbGU3Vss=",-2526237734530764157,354258692175330868,-908105607416683155,4902853099809179051>()) {
                                                case -395929512:
                                                   if (this.a(var2, var17)) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3hmeniq99ddxo","RIv2hUirSL/TBPHnfKdze6K0uNLYjEaGaDLuE16kVXo=",6593835862223848151,-3725224211080320577,-2586349149834794364,-2814444955811417695>()) {
                                                         case -1346694970:
                                                            if (this.c(var15.b())) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s1750oelnwd9cn","BsqwpH+TI3r/dqkiMcFJnJ/Gg6Oj+GlGoBqDbuuSO8o=",7493472670914684044,-2592509778141396939,-1484310091702664908,2889270705138986831>()) {
                                                                  case 472499630:
                                                                     com.yiyiaddon.e.i.d.a var10000;
                                                                     if (var9 == 0) {
                                                                        label92:
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s16m34yptu3i1v","4/NTV4hTRnCWGIwK8Dvlnd98g/hW9lROBpKFL/yLUZo=",9184609736128751028,4780236698598463520,9022090992332587287,-5279874879435736266>()) {
                                                                           case -1981791263:
                                                                              var10000 = var4;
                                                                              switch ((int)com.yiyiaddon.m.b.a<"s1ke9ratk46t25","/EBrlmx46H3Ymul6Jezjxxk4W3UvqNDG2zMW9AFNQzI=",-174014237342006173,-2426443376093351145,-2236101701416347805,8465239801327514521>()) {
                                                                                 case -279581999:
                                                                                    break label92;
                                                                                 default:
                                                                                    throw null;
                                                                              }
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     } else {
                                                                        var10000 = new com.yiyiaddon.e.i.d.a(var4.aj(), var4.ak() - 1, var4.al());
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s3r1f4zfy1g1hx","0psg4jrsJ0awQjELGIFrmgWRG3R2ts2UatWQS+Uyvpo=",8824272916287618410,675432901988741761,-8096292431000894039,5801007795464494054>()) {
                                                                           case 1798967356:
                                                                              break;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     }

                                                                     com.yiyiaddon.e.i.d.a var18 = var10000;
                                                                     return Optional.of(com.yiyiaddon.e.i.d.k.a(var1.c(), var18, var14, g.UNVALIDATED));
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

                                          var13++;
                                          switch ((int)com.yiyiaddon.m.b.a<"s3iy384vp1swfa","wHxWS2Sjb37IxR8Es0m4VDtmyipsu6CpA6Luyikm4/Y=",-5198259813698310064,5694517114071957124,2260216686331120869,3409880446683525707>()) {
                                             case -1314457990:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 var8++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s2z8tf88da54zd","iya7RLxL/CtQynGbwf8eRZLJm4YjyBeG6GpmXo5e1HY=",9160324687831230002,-56622342720207247,6906814329127221868,2447980270910795805>()) {
                                    case 652508854:
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
                  }

                  Vec3 var41;
                  if (var2.player != null) {
                     label141:
                     switch ((int)com.yiyiaddon.m.b.a<"s5nukg3t0huiq","5RLZjPeAUw0z0CaLQefFR3Li/jR8TTXm/nTJVyJ9XXo=",-3116045306145955135,6633745354815891124,8431470827559540031,2481452885582252713>()) {
                        case 526023510:
                           var41 = var2.player.position();
                           switch ((int)com.yiyiaddon.m.b.a<"ski7adfobpko4","kk7eu2bosX9A9y+EkkIB9Je8seYmyNwmEhhFKcBYIjs=",-1932161177743026327,-2365559855716941176,-728066845388111232,-2518750222759545290>()) {
                              case -1663268100:
                                 break label141;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var41 = new Vec3(var4.aj(), var4.ak(), var4.al());
                     switch ((int)com.yiyiaddon.m.b.a<"s2o6wls3l0uyk6","5ySIMk48RvjXxxnRzx7qlnIbaqGesyuIUQKzn2vSwAo=",-4700505726969063731,3750656569059961404,-4106427134017915331,-7416097515333358059>()) {
                        case -1056355340:
                           break;
                        default:
                           throw null;
                     }
                  }

                  Vec3 var28 = var41;
                  com.yiyiaddon.e.i.d.a var29 = null;
                  com.yiyiaddon.e.i.d.c var30 = null;
                  int var31 = 0;
                  double var32 = Double.MAX_VALUE;
                  int[] var33 = var5;
                  int var35 = var33.length;
                  int var36 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s2fe39a0srv394","L4XtNsKF28D0yQWJw8HqGbRzewG3LCZ1dF0NFXtrJdc=",-314182958985906743,7451177505029400391,-2813865031031985138,-5864275717123245446>()) {
                     case 1511795309:
                        while (var36 < var35) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1khmyj7otj28a","A4XOrZRjewdAw5DiZQDCOCeWAOpfcHy2/Zfl2Ul17oo=",5331064527439361296,-6157310132475527394,-2968344558972754009,-527602289576970981>()) {
                              case -535085090:
                                 int var37 = var33[var36];
                                 com.yiyiaddon.e.i.d.a var38 = new com.yiyiaddon.e.i.d.a(var4.aj(), var4.ak() + var37, var4.al());
                                 com.yiyiaddon.e.i.d.c[] var39 = com.yiyiaddon.e.i.d.c.values();
                                 int var40 = var39.length;
                                 int var19 = 0;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1ongkn2y7afvd","4LFMKOdbo3LOzt0p21uk3u2HNHeYvcWf3W1LvziVw+4=",-4216376149745880540,2621384729210967113,-2775853480910066553,-899431076020494575>()) {
                                    case -1595573820:
                                       while (var19 < var40) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2qx07w6190a6o","E9vEtzW9C/qnCKUcioQI5t1qMX9QbvLCxSOn9xSITx8=",-6048053104534133151,1721234318199257727,-3784500751285938364,-4732791087794879592>()) {
                                             case -1898754645:
                                                com.yiyiaddon.e.i.d.c var20 = var39[var19];
                                                com.yiyiaddon.e.i.d.a var21 = var38.a(var20);
                                                com.yiyiaddon.e.i.d.a var22 = var21.a(var20);
                                                com.yiyiaddon.e.i.d.a var23 = new com.yiyiaddon.e.i.d.a(var22.aj(), var4.ak(), var22.al());
                                                if (var2.level.getBlockState(this.a(var21)).is(Blocks.MAGMA_BLOCK)) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3jiomhjo1gc3v","cKwzwm7aZEtVovhsvgxxRbJ1qBg4NphSREjWJNe0y28=",-1797868357898136144,-4432548976242049699,-1431455410584741259,-4619541446364670728>()) {
                                                      case 852580506:
                                                         if (this.a(var2, var23)) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s3dkiwgrbv32g2","D7jpkRuLZ9EFZvybine8zuszTs9hDtiF+HLUiqBN6i8=",-7929028212934617896,-782002300347833931,-5225886618121685840,-1357139159438184020>()) {
                                                               case -840703096:
                                                                  com.yiyiaddon.e.i.d.a var24 = var21.b();
                                                                  Vec3 var25 = new Vec3(var24.aj(), var24.ak(), var24.al());
                                                                  double var26 = var28.distanceToSqr(var25);
                                                                  if (var26 < var32) {
                                                                     label123:
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s1ka1dh7ia20ei","Gd1nqlAZYXD6rCfSfi8hQ7ufcOGC5CuBmI+bwKsQpkg=",6428278517840862001,-2149819243365777525,-2194067954802687812,1506884610985233942>()) {
                                                                        case 808342038:
                                                                           var32 = var26;
                                                                           var29 = var21;
                                                                           var30 = var20;
                                                                           var31 = var37;
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s39lmw2q61eoqs","fqiDcoyKexLSwDeFhYhzDmNEWxLFrJ9H2+InfPsqPPM=",7001500393867498037,-1598363442882212476,697339536127540883,1595266327348854455>()) {
                                                                              case -172500224:
                                                                                 break label123;
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

                                                var19++;
                                                switch ((int)com.yiyiaddon.m.b.a<"sgvcjee5zeykc","apPMnJ/Owstz8WErZTyPPjLFi4bGpljHGMG+s4flghU=",-4694790865372959435,-6460267665499501077,-188006315045265407,-5394445656426755787>()) {
                                                   case -676013696:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       var36++;
                                       switch ((int)com.yiyiaddon.m.b.a<"s28v3gfvu8ax88","/qONOvpfV6h0h4Lg4lEJsg6khb6bMxByYZAKMiiTamQ=",-5686958484318844680,2350358246958486438,6954251897990642877,7711300102223428105>()) {
                                          case 1661490720:
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
                        }

                        if (var29 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"smptpaaeu39ma","TPS8g1QT0UghHdL1MUPvOJ3T8QIVT1RgSu7NCG744Tg=",2859964296116898592,-397639571238097624,6209800299192021956,-4727015253457677010>()) {
                              case -779164285:
                                 com.yiyiaddon.e.i.d.a var42;
                                 if (var31 == 0) {
                                    label100:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3rzmhvvb53w46","28fNpDZ6ehNUiByd39gw+iordGuZtpYPw45XA4klK/I=",-5449709297141981662,2060495712362705817,-1503600879838369033,4051820564573856695>()) {
                                       case -1979047611:
                                          var42 = var4;
                                          switch ((int)com.yiyiaddon.m.b.a<"s1k4mtwqtdgpi9","FGOMfyEX3itCFM6ChDDSLz9frcKM1TyJ8pvZzLM/8Ns=",-3329770686170270955,268731990435379802,-6971410147197094341,1483870665610304205>()) {
                                             case -1081775132:
                                                break label100;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    var42 = new com.yiyiaddon.e.i.d.a(var4.aj(), var4.ak() - 1, var4.al());
                                    switch ((int)com.yiyiaddon.m.b.a<"sbgwpo5dlatjg","lki5IPgHfFwITcZ1mrsDvCUKnXwJT7ggitAu+BeCUnw=",6487007584472559123,7304687496935390265,-5092700670922177348,8817014771828586630>()) {
                                       case -334167415:
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 com.yiyiaddon.e.i.d.a var34 = var42;
                                 return Optional.of(com.yiyiaddon.e.i.d.k.a(var1.c(), var34, var30, g.UNVALIDATED));
                              default:
                                 throw null;
                           }
                        } else {
                           return Optional.empty();
                        }
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }
      }
   }

   @Override
   public com.yiyiaddon.e.i.f.l a(com.yiyiaddon.e.i.d.k var1) {
      return this.a.a(var1);
   }

   @Override
   public boolean a(com.yiyiaddon.e.i.d.a var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1rm808iqqj99r","CL3lrzIcPzFNKytBnm26a1Lx+j8IneFsONya/tRfl5g=",8257461902667418660,6381441424103976787,-9004016835382047536,2288979498901677781>()) {
            case 1405964326:
               if (var2.level.getBlockState(this.a(var1)).is(Blocks.MAGMA_BLOCK)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3s3wf9cjxskjs","9PC4R3ulVxLkApYAMUeWardC1uKo7KkM3oWQH3pPwYI=",-4699784543258006801,-7993861521332380117,8796031969564545135,-2716864425605873939>()) {
                     case 1126619040:
                        switch ((int)com.yiyiaddon.m.b.a<"s3b8ywquznargz","PeP5Ih81aNbSRFXLlw8ULF22gVyq1JSkutcbPn3gpr0=",7570239726720835739,789869583859858778,549170486343273293,1022619101215278618>()) {
                           case 1802795020:
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

      switch ((int)com.yiyiaddon.m.b.a<"s1rh6bq0sbdl4j","UF9rcpzNIMWHYmI4xgiEuxkjmx12+i8OaDkqflMUKbc=",2325531614124495169,3652055557895391917,-7339948673356292814,-6545050162046594556>()) {
         case -889584824:
            return false;
         default:
            throw null;
      }
   }

   @Override
   public boolean b(com.yiyiaddon.e.i.d.a var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sotrlcd5t1xf0","KAF56A7PCfAf3zkSKi3yhFmXMo0j2iuPxKRpymvcbcE=",4948627327477861442,-2342441681065623305,-6601577310736845743,-3886735695048821965>()) {
            case -1468802877:
               if (var2.level.getBlockState(this.a(var1)).isAir()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3f4r6qf6z5ihc","IR3gSI5nAXL0TVVzlATQXVf1ATEPHdsHC8M8/oqcuOo=",4561584437112800407,-2673443303130440229,-6802017527717567969,5191801669873585736>()) {
                     case -994291090:
                        switch ((int)com.yiyiaddon.m.b.a<"s17trd57nqjlm","75lE3i296PPavVtXCo/DJKM9EYt8WT5/7laZ6nV2XuM=",-4370674511934608197,-6539636676028597119,-5692474320342298141,3066776066751253634>()) {
                           case -909590082:
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

      switch ((int)com.yiyiaddon.m.b.a<"s2d9mrwl6r0rrl","D4UM8baKeb2kqaU85EY4ZnXtBO9EIK2fC8GYRFP5qKY=",7990518752263498311,4846423134798559606,-6788567897080067590,5430435743992486431>()) {
         case 861649104:
            return false;
         default:
            throw null;
      }
   }

   @Override
   public boolean a(com.yiyiaddon.e.i.d.a var1, com.yiyiaddon.e.i.d.c var2) {
      Minecraft var3 = Minecraft.getInstance();
      if (var3.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s17tgvuio54zwb","74eH7Aie2UHB76Hza0C8irKTBEzTsJZ+b2WKx4JqCiY=",-5234284059437362838,6304846123585846598,-855766453462187993,-2013209329267720302>()) {
            case 1238802904:
               return false;
            default:
               throw null;
         }
      } else {
         BlockState var4 = var3.level.getBlockState(this.a(var1));
         if (var4.is(Blocks.LECTERN)) {
            switch ((int)com.yiyiaddon.m.b.a<"sc0mvwkg2q5qa","lbQDR0gDR4zsji+FJqEBZqnhXKpZxji3qsFBoSRZGSk=",-5634243061472483497,-1804018013046624686,-2023944076952050418,-3598939061987961829>()) {
               case 665329795:
                  if (var4.getValue(LecternBlock.FACING) == this.a(var2)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s10la0upy5q7sj","jRoDKPlO5AebQt7zUciJLKkCrrtpE7/nR7iqThEiJ6k=",-548842460414786893,-3231594380842439703,-6823893808169822875,7615559232856184040>()) {
                        case -537593573:
                           switch ((int)com.yiyiaddon.m.b.a<"swjwzk4crub82","AbeVFhYW1wHSeFaaCpKvBHZW3G9bQp6pneRWe8W+vms=",-1600706111079914912,-6515422329655993973,-6500202630953709350,3776542701196179940>()) {
                              case -1202756327:
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

         switch ((int)com.yiyiaddon.m.b.a<"s16athbvoq771b","9v0v3x60eA07rf8UsxHOZeONUE/wF82/uRmUx558sSo=",5475880952473765639,-1353780282369172229,-1834820888509094539,-6930087169438625847>()) {
            case 1630502858:
               return false;
            default:
               throw null;
         }
      }
   }

   @Override
   public boolean c(com.yiyiaddon.e.i.d.a var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"su6fwdah3olgh","8Z03xGHT8Mc6pLZalfyL9MSPHWP4gsUV6vRrl+jEPGQ=",6496015996068327334,-8024908963541056914,4878060430778633025,-6500316211576889518>()) {
            case 1343461160:
               return false;
            default:
               throw null;
         }
      } else {
         BlockPos var3;
         boolean var10000;
         label58: {
            var3 = this.a(var1);
            BlockState var4 = var2.level.getBlockState(var3);
            if (!var4.isAir()) {
               label44:
               switch ((int)com.yiyiaddon.m.b.a<"s2m92lhk3mo2pb","4s6njGyhk5GMMW9scPRXXUiEVwhtUITWE78b8GLFfDY=",-9105464516665612846,-311666365751399147,-8642653779178755137,-1095934798378880712>()) {
                  case -1003769524:
                     if (!(var4.getBlock() instanceof TrapDoorBlock)) {
                        var10000 = false;
                        switch ((int)com.yiyiaddon.m.b.a<"s12zb6hblh03uy","mkFyaQ6Wi/3liKl0hO7QAR1vASGsopJRRaGgjN2WCmU=",-1126579969416413313,-8741824461190921062,-5118284686202220591,1849261803704619589>()) {
                           case -1056133295:
                              break label58;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3lrg6gtkfi8je","4SHdyS/cTStVl4f4IXDNE/Rap4CTvjDRW0MWBdDRGAg=",3004069345838373570,-3114166532720786395,7934615436946179120,-7759316229194935967>()) {
                        case 170252083:
                           break label44;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var10000 = true;
            switch ((int)com.yiyiaddon.m.b.a<"s4zqqgjtymo9r","7rpIyNIGx9ecz54xqWRJuKBs1NWztzSOwLF7H0k0a74=",6824866785691484436,821027965356395214,6106738132837528159,-1574801896469345158>()) {
               case 826900217:
                  break;
               default:
                  throw null;
            }
         }

         boolean var5 = var10000;
         if (var5) {
            switch ((int)com.yiyiaddon.m.b.a<"s1f07z3wkke082","rqQYjpil4kvKb72xOMiFkVb/0yJ9RWaN2b16C/bI1ZE=",-8769155337667736082,-5846538550507647765,278527913559095262,3073884349213261970>()) {
               case 1607091177:
                  if (var2.level.getBlockState(var3.below()).is(Blocks.MAGMA_BLOCK)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1a3dq1bjcrslr","uz/uw9VSHnBewGwoKbwten6MK7IT2aBo3WA+5TPWIsU=",-3629629649145627723,5139373572447293132,-5673599952609437388,-2712035824980019237>()) {
                        case 1760174032:
                           switch ((int)com.yiyiaddon.m.b.a<"s3jbljfswmf3ye","ZwA2Lxw3dbc7BtK278o/EDl6TMU/QpCFXVB/60bWE6o=",-7372556555853029164,-7397228352879506322,8704462360167286898,3497005799627667549>()) {
                              case 1078630862:
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

         switch ((int)com.yiyiaddon.m.b.a<"s2oibnze9ees83","mWuy8qKBXw4Sf4lrwcjKbEJb+fPIDdtYmVq0D+PU7S8=",5070905088528105301,-6694628806076122516,3917842370430516640,1622794731457547701>()) {
            case -40691716:
               return false;
            default:
               throw null;
         }
      }
   }

   private Villager a(Minecraft var1, l var2) {
      Entity var4 = var1.level.getEntity(var2.bk());
      if (var4 instanceof Villager) {
         switch ((int)com.yiyiaddon.m.b.a<"stvcjuvfgw6d5","WaJDw3pCGfRQIf8H2i4IReqO0jcRIM6QFoMn5kijHpc=",-7886428524469289445,929006196642337204,49673074585721200,-382448898862254012>()) {
            case -1177354373:
               Villager var3 = (Villager)var4;
               if (var2.c().equals(var3.getUUID())) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3rkdbkju7mzl9","qB2vhy+LCymIFLNCcKOWVQpOOsELk8rgQ6sBXOiMWQ4=",5283858678553688130,6872502513898083868,7357733724338947846,-1858295839603035187>()) {
                     case 1920351128:
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

      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ge965yatc900","jwmfGD40etkmKH1LM0vvIbw5eAH/duinsTEbmuFNgWQ=",-430723450489090409,2360979084879640620,8830304509435697544,-26319706395375514>()) {
            case 431568275:
               return null;
            default:
               throw null;
         }
      } else {
         AABB var5 = var1.player.getBoundingBox().inflate(64.0);
         return var1.level.getEntitiesOfClass(Villager.class, var5, var1x -> var2.c().equals(var1x.getUUID())).stream().findFirst().orElse(null);
      }
   }

   private Direction a(com.yiyiaddon.e.i.d.c var1) {
      switch (var1) {
         case NORTH:
            Direction var4 = Direction.NORTH;
            switch ((int)com.yiyiaddon.m.b.a<"s3311dp2eci5mf","tw0T8o54z1ElyEl7/pFKgYs7ffBp+IBITjFq10LOT/8=",2567230589818082092,-7303732920643615617,-8591628532629325172,-6370178142504006564>()) {
               case -1898873000:
                  return var4;
               default:
                  throw null;
            }
         case SOUTH:
            Direction var3 = Direction.SOUTH;
            switch ((int)com.yiyiaddon.m.b.a<"s3dn433al4yg68","3WX+gY+ylyS7CEn9Opn0rmZLOXs6VbavjzHyTVEmrkQ=",6467001411469147288,-1501578762078594928,5777630267615289671,-3623013917561542759>()) {
               case -664963468:
                  return var3;
               default:
                  throw null;
            }
         case EAST:
            Direction var2 = Direction.EAST;
            switch ((int)com.yiyiaddon.m.b.a<"sl3oubz4v6odx","6ZORkFe5W36Ugpois9eu2szykjaxDzYyj5ob1FjLBBs=",-1920135311193217779,4179403164060508614,-8493532196142710432,6309705814449129428>()) {
               case 1381256315:
                  return var2;
               default:
                  throw null;
            }
         case WEST:
            Direction var10000 = Direction.WEST;
            switch ((int)com.yiyiaddon.m.b.a<"s204j7vhuw1yw1","WfpQrkTBS3Vrdusm25MErJQ6O052dfbuxAVXwOkBsRY=",8737036749965444683,-8622563980383759695,-2728506805599719320,2460061336649647602>()) {
               case -85318142:
                  return var10000;
               default:
                  throw null;
            }
         default:
            throw new MatchException(null, null);
      }
   }

   private boolean a(Minecraft var1, com.yiyiaddon.e.i.d.a var2) {
      if (var1.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2e4l5u224c11q","CzW0eq6jpSeW3+7pdOc8aS4hLfoHtLwU3fDZqHF3Wqg=",-2239113486737350014,5124124790549247677,-6420096911117671981,-3037364792349579046>()) {
            case -1215376723:
               return false;
            default:
               throw null;
         }
      } else {
         BlockPos var3 = this.a(var2);
         BlockPos var4 = var3.above();
         if (!var1.level.getBlockState(var3).isCollisionShapeFullBlock(var1.level, var3)) {
            switch ((int)com.yiyiaddon.m.b.a<"su95mdmtxljxj","1joAlWWhMA2FNch/WcIkIqCrPA5tmV07nT1sm1Vo7Ck=",6047294788629399918,-744121823985756933,-7365826252183964874,-3002409649098914991>()) {
               case 211495310:
                  if (!var1.level.getBlockState(var4).isCollisionShapeFullBlock(var1.level, var4)) {
                     switch ((int)com.yiyiaddon.m.b.a<"sooo0ag8yc3vf","1rbAtPuDI/WLQbqTrgBy11YstKVktR3BQOBuO91zE9Q=",8003208062695215159,-8896218257991328329,-7631515039710434591,-8081370227719769820>()) {
                        case -1406218306:
                           switch ((int)com.yiyiaddon.m.b.a<"s38lkm6styi191","LOr4CX9dq+egSaQikpXlg1hlYEMSjsjrvR6TqKd5/jo=",-6355727330875056958,3878856342082881807,-8238258343953003685,-45735322097044761>()) {
                              case -1966130651:
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

         switch ((int)com.yiyiaddon.m.b.a<"s298d3efeyjpm1","eqEB2837tHuuDDzD/Vqz0sU6jYIm22JBojWVFWL0GEk=",8259544978453455508,-4143497336575502099,-1474320153282390613,7888787507453044023>()) {
            case 108762514:
               return false;
            default:
               throw null;
         }
      }
   }

   private BlockPos a(com.yiyiaddon.e.i.d.a var1) {
      return new BlockPos(var1.aj(), var1.ak(), var1.al());
   }
}
