package com.yiyiaddon.e.b.f;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalTwoBlocks;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class b {
   private final Minecraft i;

   public b() {
      this(Minecraft.getInstance());
   }

   public b(Minecraft var1) {
      this.i = var1;
   }

   public static BlockPos a(BlockPos var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1phzc10kwalka","9zsPqQmxfBLXyTgumDRN7vcfw1q/lCJ1S6Mg/kAKZnc=",-8140740069351537902,-7492123449940499325,-7914375635913321626,-1862693297410427269>()) {
            case 1837672178:
               if (var0 != null) {
                  Direction[] var2 = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
                  int[] var3 = new int[]{0, -1, 1};
                  int var4 = var3.length;
                  int var5 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s2jhii2inkpex9","NzlEfM4ziOhD/p8EVTmWForpme2QjT/N1f8fqYwkVAo=",6283023940621847908,8944733903971328598,-5603582320047490065,6583267268405585476>()) {
                     case -1114977507:
                        while (var5 < var4) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3mi2q5eb8r68v","zYwqqCcFV/g9QVNmLZLJZMNT8/6DLK3dq7MkrSDfb5w=",5405480557710859475,-8054694765473527202,-8056015476057329508,-6703882731013340819>()) {
                              case -1885318617:
                                 int var6 = var3[var5];
                                 BlockPos var7 = var0.offset(0, var6, 0);
                                 Direction[] var8 = var2;
                                 int var9 = var8.length;
                                 int var10 = 0;
                                 switch ((int)com.yiyiaddon.m.b.a<"s5dv01bn4l0ex","ItP7/qpka+RWa+RSbCDvE095Sv1/RPauoAxfYMc7z4Y=",-274315479723389195,-5863889389553595095,-6683673181698757381,4753008505377937107>()) {
                                    case 515994886:
                                       while (var10 < var9) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2jt71egndbpip","+Y0tnlAdGASnOtA96mpuChTxezmQ3+GPFg+ytGx0brM=",6278677819351945527,-2359086530713411003,-1747646447811002235,-8693819533118539370>()) {
                                             case 233259663:
                                                Direction var11 = var8[var10];
                                                BlockPos var12 = var7.relative(var11);
                                                if (!var12.equals(var0)) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"sbk1mzpa5l0nd","ircmdqH6s1XM5EvKmV44wAJmjGq4s0J2OI+Em/2rMPk=",8205283329221598808,-3322420822788198710,-2162666842639647706,-4230786928787622764>()) {
                                                      case 1452058215:
                                                         if (i(var12)) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"sjmhr8fvyazeu","9Zva/O7oW5A9nsXn0pxndI0bVJ/rsPUSgGr+U6oZzEI=",-1043070454692230403,4296112613923637404,-3587224115660914977,3264056191745333576>()) {
                                                               case -97751232:
                                                                  return var12;
                                                               default:
                                                                  throw null;
                                                            }
                                                         }
                                                         break;
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                var10++;
                                                switch ((int)com.yiyiaddon.m.b.a<"s4huym0vzack8","x945y/oIlb9RoDMD9/BqcVo9EC4GmKb8ON8IzrOopfI=",-206065118954754767,8715515103887257809,-3256309400754341293,5293746532712344633>()) {
                                                   case -380104130:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       var5++;
                                       switch ((int)com.yiyiaddon.m.b.a<"s38h6bd8phcilp","QgbyW/WCjjsGiKE64Znlv/o/MoPwV0dofFK0q9N0Vxw=",-9004495888728105928,7826491666005064184,-577174786058449737,4379548077250975317>()) {
                                          case 1282010281:
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

                        return null;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s10bscmwgnenmh","D7sPOH4ZF0QeWLHxIaoAnIeXl7AGVOV4+umDYe/AqUM=",-8775420465754423586,-4788629395264491072,-89795342720064895,-4724932708800794721>()) {
                     case -2005139465:
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

   public boolean h(BlockPos var1) {
      return this.a(var1, a(var1));
   }

   public boolean a(BlockPos var1, BlockPos var2) {
      IBaritone var3 = this.a();
      if (var3 == null) {
         return false;
      }

      try {
         if (var2 != null) {
            var3.getCustomGoalProcess().setGoalAndPath(new GoalBlock(var2));
         } else {
            var3.getCustomGoalProcess().setGoalAndPath(new GoalTwoBlocks(var1));
         }

         return true;
      } catch (Throwable var5) {
         return false;
      }
   }

   public boolean w() {
      IBaritone var1 = this.a();
      if (var1 == null) {
         return false;
      }

      try {
         return var1.getPathingBehavior().isPathing();
      } catch (Throwable var3) {
         return false;
      }
   }

   public void ag() {
      IBaritone var1 = this.a();
      if (var1 != null) {
         try {
            var1.getPathingBehavior().cancelEverything();
            var1.getCustomGoalProcess().setGoal(null);
         } catch (Throwable var3) {
         }
      }
   }

   public boolean c(BlockPos var1) {
      if (this.i.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3996lelk7nsek","5oOjk3pLK58+6Ejxt0e0DEOGlwKegPVmF45mWpEOOWI=",-4007121109523481088,2830156101209121830,355836405671326032,-8451314801790428186>()) {
            case -175334458:
               return false;
            default:
               throw null;
         }
      } else {
         BlockEntity var2 = this.i.level.getBlockEntity(var1);
         return var2 instanceof Container;
      }
   }

   private static boolean i(BlockPos var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2vnsq6uw2sh9e","6WVDJEgyKhX4LVptB9Oreadixq4RQ6m3Xu8ofN2fwTg=",-4442279891055959400,3025777882286822899,3612931024453526718,1839491707536084505>()) {
            case 308977752:
               return false;
            default:
               throw null;
         }
      } else {
         BlockPos var2 = var0.below();
         BlockState var3 = var1.level.getBlockState(var2);
         if (!var3.isCollisionShapeFullBlock(var1.level, var2)) {
            switch ((int)com.yiyiaddon.m.b.a<"sgnuu56wyvxtb","R9NG71pU0MHBNvIbpmHXElciEL70eazaWCsdLTNJosQ=",-8087311301615055168,6247949713154994818,-7233398244133816233,-4649838111677959463>()) {
               case 1729416126:
                  return false;
               default:
                  throw null;
            }
         } else {
            BlockState var4 = var1.level.getBlockState(var0);
            BlockState var5 = var1.level.getBlockState(var0.above());
            if (!var4.isCollisionShapeFullBlock(var1.level, var0)) {
               switch ((int)com.yiyiaddon.m.b.a<"s1la9xl4lmu2cq","O1ifpuQ5hrqPqFMnfslAStW20zzm0co5lXsYqOUYVIs=",4041845467525540984,4961771196670299057,-5093095826165854943,7357890459912085650>()) {
                  case -1608543195:
                     if (!var5.isCollisionShapeFullBlock(var1.level, var0.above())) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2h2xps8lcoxzl","L+R30ZNRpi6X/B1HpimLJxIrjgrVrOZydv/Rs4/8nq8=",5663352731248550380,2047403473814533426,5485889635222305985,6121069987329265654>()) {
                           case -977247153:
                              switch ((int)com.yiyiaddon.m.b.a<"s1ppud8cq6m7y4","jSQHB8Y2X7FJcqUkjiau7OB3618brk4NXTpeP1sXXsk=",-6307300412201088096,2358020664198611678,-2099471139238533199,907031071144248346>()) {
                                 case 835672647:
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

            switch ((int)com.yiyiaddon.m.b.a<"szr16oyd74po","APuBhR3g8W7rnGiE3rUJQmWpq2XCth3Mj+Lo08seKfo=",2271236358221139955,5846995447425110710,5309179048768953141,4095769991404479467>()) {
               case -1687440581:
                  return false;
               default:
                  throw null;
            }
         }
      }
   }

   private IBaritone a() {
      try {
         return BaritoneAPI.getProvider().getPrimaryBaritone();
      } catch (Throwable var2) {
         return null;
      }
   }
}
