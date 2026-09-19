package com.yiyiaddon.e.g.f;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalBlock;
import com.yiyiaddon.m.b;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;

public final class a {
   private final Minecraft y = Minecraft.getInstance();

   public void i(BlockPos var1) {
      if (var1 == null) {
         switch ((int)b.a<"s2pfsqra7fn0a8","ET0zBFe9UmKP4TWMz2WojygCTzfT7MlxZQvQYFRScPc=",-6638126275741131852,7365928930378719970,8231070075511193447,7504042658005546042>()) {
            case -401866238:
               return;
            default:
               throw null;
         }
      } else if (this.w()) {
         switch ((int)b.a<"s27fwriqosrr7i","0HDB42yfSfLWEMf8DooE5jArhCsw3x1qVFJsfBD1Wcs=",-1709458034622632581,-3343178166942338433,2320087732496118877,3653331986340158995>()) {
            case 1450002968:
               return;
            default:
               throw null;
         }
      } else {
         this.j(this.b(var1));
      }
   }

   public void j(BlockPos var1) {
      if (var1 != null) {
         if (!this.w()) {
            try {
               IBaritone var2 = this.b();
               if (var2 == null) {
                  return;
               }

               var2.getCustomGoalProcess().setGoalAndPath(new GoalBlock(var1));
            } catch (Throwable var3) {
            }
         }
      }
   }

   public void k(BlockPos var1) {
      if (var1 == null) {
         switch ((int)b.a<"s1o29hutujlqx4","ypopZMq3QHSwb72POqWalSpnZsE+Q3xOjv+McfZYVv8=",429692863799931204,6693766994582016060,5991494797086409539,1813084412298482800>()) {
            case 468065845:
               return;
            default:
               throw null;
         }
      } else if (this.w()) {
         switch ((int)b.a<"szwdsoc3m5u20","SyDgVdPPiZNhF6Db/yMRIlQbf4jDVstV0DO+d2gU/VU=",-8140528444098548648,1875700085490839321,7211099824722672508,4484064573031382486>()) {
            case 355323226:
               return;
            default:
               throw null;
         }
      } else {
         this.j(var1);
      }
   }

   public void ag() {
      try {
         IBaritone var1 = this.b();
         if (var1 == null) {
            return;
         }

         var1.getPathingBehavior().cancelEverything();
      } catch (Throwable var2) {
      }
   }

   public boolean w() {
      try {
         IBaritone var1 = this.b();
         return var1 == null ? false : var1.getPathingBehavior().isPathing();
      } catch (Throwable var2) {
         return false;
      }
   }

   private BlockPos b(BlockPos var1) {
      if (var1 != null) {
         switch ((int)b.a<"s1v9da6ypvy1zn","SGT0rUBgbCrRO1we3kTrCkeYYQ9hAXYYX2sMmEbkRg8=",-1349374283183694817,5230945741219734425,7682149126244779466,-6239888041086127863>()) {
            case -1453386213:
               if (this.y.level != null) {
                  switch ((int)b.a<"s32g6t1j4qqxdh","mB5jn3k9ttw7nI8ds/ZRfyMSJDNHvs8fPNNEz9B5i0A=",-1076738318470889106,3591369283483540444,-1395333668659121030,2964736994816723309>()) {
                     case 289548724:
                        if (this.y.player != null) {
                           BlockPos var2 = this.y.player.blockPosition();
                           BlockPos var3 = null;
                           double var4 = Double.MAX_VALUE;
                           int var6 = 0;
                           switch ((int)b.a<"s2d0eseew37y9u","uf/I2nfZPOyx7oTa9kAXF1boImh0d0ajhqmj/lfZeAM=",-1507402609935630288,4979904630537799157,7533551447032522664,-624117150047358263>()) {
                              case -1936179448:
                                 label109:
                                 while (var6 >= -1) {
                                    switch ((int)b.a<"s3d7c5u4kbalzn","qBgsJ0C2vNljUzFqJ5TeZv5iYMbvh1PIgj5ZOmYLTQc=",5599368992631831833,5154473192111807812,3694877909333973057,3505474074322983530>()) {
                                       case -290041243:
                                          Iterator var7 = Plane.HORIZONTAL.iterator();
                                          switch ((int)b.a<"s3evw01n6kjxq0","UWTXN0AB8aCPyDLJDoXjzn+1o7+d6xhu7uVDhzloGAM=",7129912056757177355,4679216160148716361,-7058947813787430308,-4913199639268091324>()) {
                                             case 38181403:
                                                while (var7.hasNext()) {
                                                   switch ((int)b.a<"s3sx5adeagpqg7","Ql9eC86+dzBWAmZElEINVw5ZqJabX41zX9tgeX8tn+M=",-2803557944447183465,-4366402955352548554,-4915825169646399312,-4058175971972566789>()) {
                                                      case -1759383083:
                                                         Direction var8 = (Direction)var7.next();
                                                         BlockPos var9 = var1.relative(var8).above(var6);
                                                         if (this.i(var9)) {
                                                            switch ((int)b.a<"s3l21fz3zrzlq4","YdiTA5BdMwF2sbM8xEsGmXgC56VwxfaKRmIEnoIt6H4=",-8350705945539380868,-6881108731158763746,-7011515053009142868,8324653548978920596>()) {
                                                               case -526160449:
                                                                  double var10 = var9.getX() - var2.getX();
                                                                  double var12 = var9.getZ() - var2.getZ();
                                                                  double var14 = var10 * var10 + var12 * var12;
                                                                  if (var14 < var4) {
                                                                     label84:
                                                                     switch ((int)b.a<"s1uszhgv2lohj2","Ii2IKFZttyS4olZzZkWP+Cb2z438FgT5cFgOq62Yo1o=",5269892249850793391,-3383687876583197642,1992887410689119959,-2921820187910208782>()) {
                                                                        case -1840380953:
                                                                           var4 = var14;
                                                                           var3 = var9;
                                                                           switch ((int)b.a<"s1rb04h0t3c8ys","mITEjiWHxHJlIgsve9kY3PIXyR0xxTAUZUjEGlvWE+Q=",-1672034377297672622,-660126929340690228,3549136261255890153,-3890131355408894126>()) {
                                                                              case -148060919:
                                                                                 break label84;
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

                                                         switch ((int)b.a<"st2207ri22718","Lf4km0CLg8r3GvoFsFTHAxju2xjJWZ5/JCemQYyFsK4=",-5171839499134216482,5299987195183677751,-3804293355287020282,1928699417701918470>()) {
                                                            case -1828899365:
                                                               continue;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                if (var3 != null) {
                                                   switch ((int)b.a<"s2i3vubzo6n8lh","1lA9zEU8RwH/KAHeD1UeQKd17EBk0NZJueHGx9n92yg=",-3387822816330328045,-2255714991136058915,-2924226664431420380,2849168864200322844>()) {
                                                      case -989747099:
                                                         switch ((int)b.a<"s2o80ruyr3p3bs","stXZyv30+iZSm6LQ33vnqUhCh7qrqTv45jd3HfN/0g0=",6081256693629551116,8251251497959195955,7663771590215584946,-8619575797382054455>()) {
                                                            case -1059137540:
                                                               break label109;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                var6--;
                                                switch ((int)b.a<"swn6rpeuzidp","u/3p7DWmdBokAMWeHzB6h+j0bzVp0CmJKkKwFMEFNVo=",-2538885401265971358,1366413349130562603,5489362294163249910,-3095931473944757211>()) {
                                                   case 1618509572:
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

                                 if (var3 == null) {
                                    switch ((int)b.a<"sr5f4x996z87p","5twTgtTh7X0T6FssDdyOQ3VNCO+/WIm7jhUcb0BUljo=",4634277575940687936,-5409270114610421348,-6708146267825000384,-2185703283894691556>()) {
                                       case -1736959466:
                                          if (this.i(var1.above())) {
                                             label67:
                                             switch ((int)b.a<"swluz1fgq3gg0","w8lWZKtpU35befIht8O2xC9Zk6p66/oV4yA2gjbyrtI=",-2920665223564059269,-835552554586910017,-2136720834402247714,2935350971363411891>()) {
                                                case 1980340728:
                                                   var3 = var1.above();
                                                   switch ((int)b.a<"s2d9tu81hncn96","haPi0X3vJr6Inkyl8DB25r2VbkaXXxLT//oiC0uQaMI=",-333695658802978552,-6915428692967931263,-1862420771304823410,1991892069847520956>()) {
                                                      case -596878110:
                                                         break label67;
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

                                 if (var3 != null) {
                                    switch ((int)b.a<"s3f41an2avno5c","kmAiEeP2h/repCbZ9vtK6T3bEV4gqxjVQiofOS64qr8=",-7548509730945205476,-2594800120908007827,-6205211039136374940,-2250437109435101>()) {
                                       case -999754987:
                                          switch ((int)b.a<"sij1t88fyxnzh","Xhud7nUB/q+F+wFBeEZ741EvLvt0Oce3Hwca6Gr/xxM=",6553400867279685530,6436111867324074858,-7347494983830607447,2426528643844208802>()) {
                                             case 1441268773:
                                                return var3;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    switch ((int)b.a<"s11gu1jytvqudf","te9hswZ9E1UxK6hcc/U3Mg+oVVF1iB7oJcMRvfEdFTY=",-5609000189740470772,-1269986412427756890,3282013476817426230,3180240639425508957>()) {
                                       case 159470914:
                                          return var1;
                                       default:
                                          throw null;
                                    }
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)b.a<"sruvq4ntwgst4","OnjZAEQK7Sv+rgxMw/ltXs+X0qYjsk7Z8U3tNOA8kFw=",-8815606430521488894,6991167177226076082,1172810321990578341,-5060776255486265111>()) {
                           case -521079931:
                              return var1;
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

      return var1;
   }

   private boolean i(BlockPos var1) {
      if (this.y.level == null) {
         switch ((int)b.a<"s2qfot53wk9gy9","Zj6xHO7R92Hlpti250i68qGmPOWkzEjyei4PR/Lpbfw=",-1936600652887311241,1357236532886283760,-8996282980005154834,-1601375177615060375>()) {
            case 1160472196:
               return false;
            default:
               throw null;
         }
      } else {
         if (this.y.level.getBlockState(var1).isAir()) {
            switch ((int)b.a<"s2z0hi1hr13jel","JtlLJJToF/v42VRJwXBqeAqpNCNVV3Yo0T3k+kPItug=",5007584654525345149,6479208670725944124,-7671938111244221675,1262671645577165205>()) {
               case 271678911:
                  if (this.y.level.getBlockState(var1.above()).isAir()) {
                     switch ((int)b.a<"swk0iaaoqwib2","5bOt6zywItnMuStK0yof3B9HYhNsyLKGvU0oqAF1wTg=",6838305806606644338,-3994438667470435866,5470811735471088426,-4126936313482356509>()) {
                        case 2076638311:
                           if (!this.y.level.getBlockState(var1.below()).isAir()) {
                              switch ((int)b.a<"s3rxyt6rug701p","5LMdX/safkKLImCIhVDfyoHww+DXJJNcWvRRDd5C9is=",-574602748950880113,9122008023180785725,3453867829863475957,906067174118704677>()) {
                                 case -671665413:
                                    switch ((int)b.a<"stpthv22k7hrm","YVinHivwJXQdgcFBx7UDfJGD4YBQKmgvvE8cbBkQ5eE=",7234334631985112938,-7587362596547834575,-3514120918283555803,-5966310707011341883>()) {
                                       case 1444265272:
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

         switch ((int)b.a<"s29gnzklelnkz6","FWF0lMiV2ek9nFFXFX3ZCHDOto7Qwmzg2jFJCVVfW/U=",-1314716839681679452,265459821593107759,8246807473027149261,7448552162188551102>()) {
            case -1614993150:
               return false;
            default:
               throw null;
         }
      }
   }

   private IBaritone b() {
      try {
         return BaritoneAPI.getProvider().getPrimaryBaritone();
      } catch (Throwable var2) {
         return null;
      }
   }
}
