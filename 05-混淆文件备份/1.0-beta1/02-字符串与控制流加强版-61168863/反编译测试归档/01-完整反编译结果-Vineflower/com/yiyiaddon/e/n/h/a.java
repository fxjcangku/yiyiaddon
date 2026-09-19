package com.yiyiaddon.e.n.h;

import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.state.BlockState;

public record a(String rI, String rJ, String rK, String rL, String rM, String rN, String rO) {
   public static a a(BlockPos var0, String var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3uu8heb1peh2v","1ScByoyPKlKdZowL5fK9cUg1Y1YM1YdgArQjaUTHhMc=",-4211649549065210741,4375411604457965019,-3992151176424005226,-7243297132018362553>()) {
            case -635983278:
               if (var0 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1d2a6suxo5hvb","A4LqtgAXAeK3idOrk8YkixfYzx655i12mmJiXq6XJ8M=",-9156222361827672341,-3107887029267495418,-5943237701506379160,-6033783982379524476>()) {
                     case -1236628393:
                        if (var1 != null) {
                           BlockState var3 = var2.level.getBlockState(var0);
                           if (var3.isAir()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3r6m72axrnc56","keQircNYb8Sm/BxQzAZTDG+NB4s95scpoSE6XX+i1jU=",-7642371373458482165,-7942853880023999646,-1264432225635986394,-2065462449755074946>()) {
                                 case 1743681585:
                                    return null;
                                 default:
                                    throw null;
                              }
                           }

                           String var4 = com.yiyiaddon.e.n.a.bT();
                           String var5 = com.yiyiaddon.k.e.c.dn();
                           if (var4 != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2ntjzdgyhpn7v","KuICESH7P4LihB9F0+A1vgJBS3vNyuzPei7Ity+j5P0=",-7315463731891743960,6916144932020082757,5214136732293340115,5297015855690590866>()) {
                                 case 258611441:
                                    if (var5 != null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s18p2q0gj5o96n","3+YLGN/1eboMOY0GcwSXXJntrs402LQck9VGLUMFYkk=",5511778462759007053,4699004629297416297,523536335009851285,1617110436029201599>()) {
                                          case -272429173:
                                             if (!var5.isBlank()) {
                                                Identifier var6 = BuiltInRegistries.BLOCK.getKey(var3.getBlock());
                                                if (var6 == null) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1jba8i2ke5oh7","e8X9RfQA0a97hKFkxcoqhcc7QqtKo0igrHwnwMX1zhE=",-433556265199985711,4812764223026010887,-7917991141077289915,-494982232479538792>()) {
                                                      case -1664026374:
                                                         return null;
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                com.yiyiaddon.g.d.a var7 = com.yiyiaddon.i.e.a.b(var3);
                                                return new a(var4, var5, var1, var6.toString(), var3.toString(), var7.dv(), var7.ea());
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s27nl0o292ppoq","CUQApqyq2EcNhnJVkSvqwgP7ca5/0/p00Jbu+9OLYww=",767040531438435535,598989488806602252,-7926417864764922642,-1442293401581556132>()) {
                                                case -2123150672:
                                                   return null;
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

                           return null;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"sux5mpetydy1j","P8MxSEeBgwx1yOHrdt3JInNAeNgZbGXxKDRt1O3hXeQ=",2934640170137097400,-2521163861840161486,3236652057382037724,7372139203716742984>()) {
                           case -1416656435:
                              return null;
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

      return null;
   }

   public boolean x(BlockPos var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2cf4s9ua5mw7r","m7FOD7gG8dj0dCLL+1WvCx+HsPm0kwxLCQsA1o9Wn5c=",2454214659506330578,-6722877079208418092,4993592479200758350,7614741863210453183>()) {
            case -807738517:
               if (var1 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2dd6db7y182hg","A6e5muwJf2s3sXWh9k95aId9+h61O5NMjFcGoIpElDA=",-6921657596313838969,2821872930173311986,6969205575258575386,2391091151385662764>()) {
                     case -1820669641:
                        if (var2.level.isLoaded(var1)) {
                           if (Objects.equals(this.rI, com.yiyiaddon.e.n.a.bT())) {
                              switch ((int)com.yiyiaddon.m.b.a<"spz8srdrsq11p","zy4SaLwF4/8yLICMMlEv5ivcV9bukdvyajrbeZNBeLo=",5990410657324936036,-2742177846144987776,-6100142448008970964,-5031735507554611220>()) {
                                 case 2044466700:
                                    if (Objects.equals(this.rJ, com.yiyiaddon.k.e.c.dn())) {
                                       BlockState var3 = var2.level.getBlockState(var1);
                                       Identifier var4 = BuiltInRegistries.BLOCK.getKey(var3.getBlock());
                                       if (var4 != null) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3oceydthnxlqy","j7VDkur+C/cvAakuZrMNIdZqeUwKwIY1ZIuVCRIV5XA=",8135454103335875657,7426608196231247848,-2129898895075214540,2420942446035939212>()) {
                                             case 1372730913:
                                                if (Objects.equals(this.rL, var4.toString())) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3bf0fxczdamco","UJgUO2Qxp7amtfrisAL6Ci4D5qWkoavI3M+plAJEQso=",-1305956786723674960,5399062689325516041,7584951456414935966,3118646122204713624>()) {
                                                      case 1801950149:
                                                         if (Objects.equals(this.rM, var3.toString())) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"sw1zbmb4gjtuz","HsCK0wYiEhexxxTfgOf59ApxMPyeGzkaDkoaWrbyw1s=",-7214333378332275560,-8743538058897005710,-7736225054911475544,6221437761331897153>()) {
                                                               case 1114645485:
                                                                  switch ((int)com.yiyiaddon.m.b.a<"scne3sdt2cg24","TR/y14Xrd7CQTOojA87Nf9JnApIlG0yCExcJI0r8Wk4=",2697284144150310630,-4149224285420991917,2901706531798694854,8379845378431069939>()) {
                                                                     case 1069125731:
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

                                       switch ((int)com.yiyiaddon.m.b.a<"s3v1wtxqrj8gx4","+rDpRd3c+nTS+xFBIpz+/EOBPjiEjkKIzg0yDKgPRN0=",8422124538474096057,3662248672784267347,7634944409181017205,-8469914504832628548>()) {
                                          case 2102420450:
                                             return false;
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s379ncvpfpcslp","ceZony70qq1qn9/Ufi6HgufuVDImBV5TGBrRkgjtTMI=",-2200348592518302659,842608237467179096,8318689590635201282,-7824656153111733707>()) {
                                       case -855173600:
                                          return false;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           return false;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"sbrg85vh6pefv","77la77vjQjsUjUpFKpLe6NhPNc884kykilGWgqYA/E4=",-34713837441565465,3689260951950249446,-24940835105570723,-1438008764139850219>()) {
                           case 1384341934:
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

      return false;
   }

   public String bT() {
      return this.rI;
   }

   public String dn() {
      return this.rJ;
   }

   public String do() {
      return this.rK;
   }

   public String dp() {
      return this.rL;
   }

   public String dq() {
      return this.rM;
   }

   public String dr() {
      return this.rN;
   }

   public String ds() {
      return this.rO;
   }
}
