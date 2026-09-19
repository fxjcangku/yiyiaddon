package com.yiyiaddon.e.n.a;

import com.yiyiaddon.g.c.d;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.Rot;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;

public final class a implements b {
   private static final Minecraft W = Minecraft.getInstance();
   private static final int lz = 40;
   private final Set<Block> K = new HashSet<>();

   @Override
   public boolean b(BlockPos var1, int var2) {
      return com.yiyiaddon.i.c.a.a(var1, var2, this.K);
   }

   @Override
   public void b(Set<Block> var1) {
      this.K.clear();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1fttgs1vvsod4","Zr4M/tgZ82KEcOAAKxJqg4NUK5yBXA6JQw5gmsxUEIs=",6583683834919592854,2013515949849631065,8019167959430872574,9170780764143481940>()) {
            case -1996515994:
               this.K.addAll(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s2w10gq9qugk3w","de0fRQfqPzuET6WapTK9vj2yLWNuNfZg6P1GVHpgNzY=",-8923057175069445054,-1351859994736287887,5161872999198113733,-8738750786827933231>()) {
                  case 1557235829:
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
   public boolean cX() {
      return com.yiyiaddon.i.c.a.cX();
   }

   @Override
   public void gv() {
      com.yiyiaddon.i.c.a.i();
   }

   @Override
   public boolean a(BlockPos var1, double var2) {
      return com.yiyiaddon.i.c.a.a(var1, var2);
   }

   @Override
   public boolean v(BlockPos var1) {
      if (W.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sf2d6x4odgknw","K4QvYzfPxei3NqCLiNyhSfO9787eL+nmU49WkRHCLeM=",3352983855822353110,6405159037946985960,-7803389162646015593,-6163177082884213747>()) {
            case 1037503147:
               if (W.getConnection() != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2yzlll8fjy6sz","YsxOecirg23/ETSeFsJWG8ui1sRkDy7bh4fuHOxOQWo=",-664636114989082029,1295982548892504542,8587745128455348044,5270613003962383111>()) {
                     case -237455652:
                        if (var1 != null) {
                           Vec3 var2 = W.player.getEyePosition();
                           Vec3 var3 = Vec3.atCenterOf(var1);
                           Vec3 var4 = var3.subtract(var2);
                           double var5 = Math.sqrt(var4.x * var4.x + var4.z * var4.z);
                           float var7 = (float)Math.toDegrees(Math.atan2(var4.z, var4.x)) - 90.0F;
                           float var8 = (float)(-Math.toDegrees(Math.atan2(var4.y, var5)));
                           W.getConnection().send(new Rot(var7, var8, W.player.onGround(), W.player.horizontalCollision));
                           return true;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3fqwlwopzxn1s","g46eC4qnmjVv99VjrK4McuFOU7RkECqkeUoqA9qcGpM=",4382595047652959518,-6607938288536835552,4716582968453326216,3251997581493680961>()) {
                           case -30877265:
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

   @Override
   public boolean d(BlockPos var1, Direction var2) {
      return com.yiyiaddon.i.d.a.d(var1, var2);
   }

   @Override
   public boolean a(InteractionHand var1, BlockPos var2) {
      return com.yiyiaddon.i.d.a.a(var1, var2);
   }

   @Override
   public boolean a(InteractionHand var1, BlockPos var2, Direction var3) {
      return com.yiyiaddon.i.d.a.a(var1, var2, var3);
   }

   @Override
   public int a(d var1) {
      if (W.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s22r1aqfzns5il","4LPD4/6EzOLVQ78xsjIYiaC5VNgmX4AZ+Bll6liNVRU=",-1055738088182019924,4322596151811497510,-9184784263605341305,5498557934688516450>()) {
            case 883846226:
               return -1;
            default:
               throw null;
         }
      } else {
         int var2 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"sbjbvzf8ohh0z","BySg6WKL96cJnnFIKu7863nqyYskY8UdR/EpKyiSSVY=",-3790768050142739364,-5840164277035684121,7404050419996851284,941113117931320525>()) {
            case 944645669:
               while (var2 < 9) {
                  switch ((int)com.yiyiaddon.m.b.a<"szz4z3rxs9spr","rDTJyokXWji3GX1vCkmpgclXpCDSxWEHIH3SRAo0amE=",8211059725313066983,-8838376989188955447,4665160144458127721,-7898032829466308277>()) {
                     case -1365089587:
                        if (com.yiyiaddon.i.b.d.a(W.player.getInventory().getItem(var2), var1)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1z2atuky5bzxj","1TG5aITO7IdvJvTD6wE57FoCUIfwUs2Wy7J73U1/O9s=",4927842955879723010,-2624491929892000474,8528027994944330449,-406791934596436830>()) {
                              case 1099544775:
                                 return var2;
                              default:
                                 throw null;
                           }
                        }

                        var2++;
                        switch ((int)com.yiyiaddon.m.b.a<"s26rdrnsphi0rb","D9OQGNzSb95Szr6+HVFZ1jxwMMXZhGt0GJcvzBOG/7Q=",-4709575154706053425,2081289772805561104,-3909354922074228376,-5734179374031401077>()) {
                           case 1603611166:
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

   @Override
   public int b(d var1) {
      if (W.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s37d60p5kpav1d","y+8uBzm3Ho1EIWnXgoRVthMXzszwnt9jrOimkGIPGiY=",6077744977089232650,-2942118340894316378,6732474844438381521,6283224516585860968>()) {
            case 1904547036:
               return -1;
            default:
               throw null;
         }
      } else {
         int var2 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s1w0crg5nwnyp5","dZSGP2tbbYc4RWnaY5t3qKC+zpLmrvpm7CNUsEMGrDU=",8460737452061685293,2150400990487174588,3299711457955631347,-8266440882878706493>()) {
            case 1924437874:
               while (var2 < 36) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2r29jlkgt8qgx","yM7kJzWv3rxFr1nTjuz+JgpxR8zhOxFmUVSGgHNdsWY=",2819587083127702154,7527872289323690440,-8423855299413202487,-7249245851670527874>()) {
                     case 1850479869:
                        if (com.yiyiaddon.i.b.d.a(W.player.getInventory().getItem(var2), var1)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s12dpifzc1mybl","KGGh+K293JaiydE/sZKj7q/9aqjfFQ/QL6lfXtBljwM=",149481319322137078,2377849960703962332,-156651623996121286,2642269321882384993>()) {
                              case 1242569979:
                                 return var2;
                              default:
                                 throw null;
                           }
                        }

                        var2++;
                        switch ((int)com.yiyiaddon.m.b.a<"s1k2gyybe68n0r","Mj1ZXSfNL46Wacx/eDh2wzGhG2Ulbe7UH4Y1qEOMzY0=",-8830214157771673902,-7051356068267820039,-6224828125955130383,-2406242055850410107>()) {
                           case -5697320:
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

   @Override
   public void e(int var1) {
      if (W.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ft48ng0as8xx","DPfCJ9Ha4LyznrLFogzabZYESH4FrCMYtSxF5KO08Os=",2676265303139697637,6548543590492525600,-4290437267374639032,3770611549385017774>()) {
            case -1453615781:
               if (W.getConnection() != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1scugc89505x8","ZiP6wDK4n5T+3yPmIBvsP8lBOxeReZ9IAytIrm3u4Tw=",-3067650923495296686,8461745360938794614,2759941065769151612,7047998737959889692>()) {
                     case 1101932892:
                        if (var1 >= 0) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1unhllq5et5ou","yofqOvBCOzm3HlAhQVobkpL8FMP0GjbJi0Flb9o7gxk=",-7343330797001460775,1533242343773745795,5047827611830628063,-8297136626263656464>()) {
                              case 553163581:
                                 if (var1 <= 8) {
                                    W.player.getInventory().setSelectedSlot(var1);
                                    W.getConnection().send(new ServerboundSetCarriedItemPacket(var1));
                                    return;
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s1zi0kmlkqcn3l","JfSrgiFr69lna44X+1hsaTJGMMJWV3ag26rXqYrjT3k=",-308319459783957553,-2456007578760581769,8424356817824594804,-3091245736774252883>()) {
                                    case 666595163:
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
   }

   @Override
   public boolean n(int var1) {
      if (W.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2bdvmo9f5wert","JefJkin8Jzuyuq0s3qMGAVzWbzkkybbfOlOmqcrGsZA=",-1155061562954825100,-3897169712147943713,3136668407163632535,4192598051472905231>()) {
            case -342758962:
               if (W.gameMode != null) {
                  if (var1 >= 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1s0746uhj2ql2","xmb4wp6g4k5Fy/PfKNBqfpR7MefGNxG6gXUakmShEPk=",4060072822429304369,-7392313748143735750,-1445362433626429104,-1300939939297629376>()) {
                        case 1017317164:
                           if (var1 < 36) {
                              int var2 = W.player.getInventory().getSelectedSlot();
                              W.gameMode.handleContainerInput(W.player.inventoryMenu.containerId, var1, var2, ContainerInput.SWAP, W.player);
                              return true;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s1ah8ev1d27qs4","wRi7O8cxv3Z+mvxQ8634gXV2W/jv96u0dGKxFepG0/A=",4943706588934742910,5398043967432246422,-3635481883954291004,4102215670015503788>()) {
                              case 2016717674:
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
                  switch ((int)com.yiyiaddon.m.b.a<"s1y66mlxjs48t4","cecHB4bMF6Y0kPaXvCVTSrn+EGH91nkOzEIpsLpXRZs=",7529167904839869704,705254417244175404,5893335707660883689,3209361593388228138>()) {
                     case -1659048337:
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
   public boolean o(int var1) {
      if (W.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s9z7xrbwp7481","l7moXtAvMTeI2PfD3ecZsCb3fEHQ5HapnviJ8zLKUXU=",8740799520597442438,6004635542919101452,6742300634123209607,-2039904124696167421>()) {
            case -120177888:
               if (W.gameMode != null) {
                  if (var1 >= 9) {
                     switch ((int)com.yiyiaddon.m.b.a<"s33fmgf7ldrmy8","rAcOWUJI2biXMvYhehTQn5uqYt9tLGfpmKR0z9z/iXw=",-1279713358939365366,-8727608686331238542,-175563504103887348,-86538825115786648>()) {
                        case -691891974:
                           if (var1 < 36) {
                              W.gameMode.handleContainerInput(W.player.inventoryMenu.containerId, var1, 40, ContainerInput.SWAP, W.player);
                              return true;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s1t94m8zmu7m1f","PplIT4qf26sZc0C2TO2YYcWnOnIlS59Vx4XpekbVpUI=",7299094799980304110,4068806722098849107,-2339165395904493460,8830755075186375757>()) {
                              case 1528313568:
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
                  switch ((int)com.yiyiaddon.m.b.a<"s3o802unmfnjl9","xFg3fFHwcJrt0LhzAIaHkRaFbGDd3UDc+qvZdsAcctc=",-2252981878169201533,6569554104825913620,-5248685790289027541,2995042267883547452>()) {
                     case -446551030:
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
   public ItemStack o() {
      if (W.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1q0w6mbjx14m1","EzxWDnwH0R4H4mceWnSNTG5MAArzuGeTCC9Wm0fEqu8=",6239235372908880302,4503054210750799063,-3160583289074201864,6062486539892913001>()) {
            case 1132208991:
               ItemStack var10000 = ItemStack.EMPTY;
               switch ((int)com.yiyiaddon.m.b.a<"s2r4930za5tvia","SD9nKoDYaQBw/etivoLQEXzICpI9+GRtSA9JQIZYYIo=",6772849530010506855,-6660587694301964627,2152024740432354998,1691769791402501834>()) {
                  case -1182048512:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         ItemStack var1 = W.player.getMainHandItem();
         switch ((int)com.yiyiaddon.m.b.a<"s2mp2p0fi680g6","NqjqatEAjO4a7VQx0A4pmFWwUGO5+iLIfkmtLUuQOh0=",-7608555207565202213,-6123044204708235857,-96042332365348643,1625756540403416557>()) {
            case -788750892:
               return var1;
            default:
               throw null;
         }
      }
   }
}
