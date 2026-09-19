package com.yiyiaddon.e.e.d;

import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public final class a {
   private static final int di = 36;
   private static final int dj = 8;
   private final Minecraft t = Minecraft.getInstance();
   private static final Predicate<ItemStack> c = var0 -> var0.is(Items.BONE_MEAL);

   public InteractionHand a(com.yiyiaddon.e.e.a.a var1) {
      if (this.t.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s19k2rvxf7kxmh","ccOZm7fm6DueUQjCiB19q08Ntzx4RnQsIFwMJT4KC0A=",8532407780938739073,-4726658145572298118,-112716601561986586,-5181867262344528567>()) {
            case -1520929395:
               return null;
            default:
               throw null;
         }
      } else {
         if (var1.aH) {
            switch ((int)com.yiyiaddon.m.b.a<"s1u02crrz3tafp","kmYOgHAY6N/jqEQTPvEfHvcnaQjoTAm9fPKTZ3qm4B4=",-3606311031189041623,1521012462824964686,2566269614101465478,4540054521249584708>()) {
               case 1156907648:
                  if (this.t.player.getOffhandItem().is(Items.BONE_MEAL)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s32cizg5i1x3hw","oYtzSnEn5qVRY6Q9O4T0cok2brooNrnkBsrNtaXI6WU=",-3491638901313269285,296325282560845192,1048718123567654843,796916371119489311>()) {
                        case -2076600016:
                           return InteractionHand.OFF_HAND;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         int var2 = com.yiyiaddon.i.a.b.a(c);
         if (var2 != -1) {
            switch ((int)com.yiyiaddon.m.b.a<"s2dsi2fmdd2lks","yx+UUu25/sHbe6zWEVBn4zdzNIrZzipswT15ZT4YQIE=",7303648963788391857,8502817089080698190,3795112119617618762,7864033828321155819>()) {
               case -342687789:
                  com.yiyiaddon.i.a.b.s(var2);
                  return InteractionHand.MAIN_HAND;
               default:
                  throw null;
            }
         } else {
            int var3 = com.yiyiaddon.i.a.b.a(c, 36);
            if (var3 != -1) {
               switch ((int)com.yiyiaddon.m.b.a<"s3vcoato40ygfl","QI9viONRk1DmSx7xSdB6ZBvES6VkTu/tBwNeU9ieAO4=",-3933764212701977678,-8887537084163063913,-954537814446527023,4904614478559587850>()) {
                  case -1621909637:
                     if (var3 > 8) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1rw6qv5ji4rav","qT/0DyFgAFDR3TgzUsfq1zfDmhuVHOCfh7+L2RyJNQo=",8016117546685726349,609599237122972680,5774068761603817489,-1879998340785474959>()) {
                           case 1011595329:
                              com.yiyiaddon.i.a.b.s(var3);
                              return InteractionHand.MAIN_HAND;
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
      }
   }

   public boolean a(BlockPos var1, InteractionHand var2, com.yiyiaddon.e.e.a.a var3) {
      if (this.t.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2qiwy8fvlovak","7u5uL9ykLxq8ukexDar1wQQjBtvgpnA8QjkNmW/7zCY=",1525539867425463602,6159007050871439437,4790533900801156160,-2974080648165541495>()) {
            case -1711861114:
               if (this.t.gameMode != null) {
                  Vec3 var4 = Vec3.atCenterOf(var1);
                  if (var3.aG) {
                     label35:
                     switch ((int)com.yiyiaddon.m.b.a<"s2mjiql5lme520","Q+gdli0ZVvgI81r31w9IPZOrMAhK5Vbp0nULqgH8huM=",-3214336739958973167,5048823218578430327,8753401252380170084,177864831424632966>()) {
                        case 1557251601:
                           this.a(var4);
                           switch ((int)com.yiyiaddon.m.b.a<"s25fov76khm2pn","hN/3+Kyk8s5ambkmY5hIqOFEPiD1oEXKKlFoS2R9qwY=",4647036126721986608,5127045546811848541,-7234588260808135374,8777062329385502078>()) {
                              case -404402304:
                                 break label35;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  BlockHitResult var5 = new BlockHitResult(var4, Direction.UP, var1, false);
                  InteractionResult var6 = this.t.gameMode.useItemOn(this.t.player, var2, var5);
                  boolean var7 = var6.consumesAction();
                  if (var7) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1bmwouhwh0gq2","vW1EeaX0JMrRoE8IXKKIESmT+A54olas7WxWm3CbqhA=",-8359732278812722562,-6177248861066583481,3421511262573242350,-8616003547881913228>()) {
                        case 815091054:
                           if (var3.aI) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1do8hkp4smonr","IIwbiQrnsNyJioVswClYh7PQZGwzYw0t2aGM+X0Y9Ng=",-966179111189630415,228895365399487601,-7090142320539108527,-1857986943251440628>()) {
                                 case -774019036:
                                    this.t.player.swing(var2);
                                    switch ((int)com.yiyiaddon.m.b.a<"s6pbto6ilmpt9","VoeFB10WLaulOqTeTgZIm/+98oqQP27W7psM/oz/gbc=",8285831712106564098,-4594496910887772969,5658533260585675404,2348416451498493958>()) {
                                       case -886748891:
                                          return var7;
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

                  return var7;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s373wwj09ibha3","+ybwz9WeqE/K+VO0tRrzHYDkRhoNZG9zFD/EUGLIbNo=",8164913382705435792,-6280579593250680473,5547118905283666142,-1911577888120146670>()) {
                     case -445923230:
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

   public boolean a(Vec3 var1) {
      if (this.t.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s24g3122w6mwe1","E/C31bs3cdjAS8o0m5SkGINjNOai6RCN9C2yJxLX6MY=",371504743391992665,4321073665129434943,7565348123092202221,-8135146560286883094>()) {
            case -1271546668:
               return false;
            default:
               throw null;
         }
      } else {
         Vec3 var2 = this.t.player.getEyePosition();
         Vec3 var3 = var1.subtract(var2);
         double var4 = Math.sqrt(var3.x * var3.x + var3.z * var3.z);
         float var6 = (float)(-Math.toDegrees(Math.atan2(var3.y, var4)));
         float var7 = (float)Math.toDegrees(Math.atan2(var3.z, var3.x)) - 90.0F;
         return com.yiyiaddon.d.c.b.a(var7, var6, this.t.player.onGround(), this.t.player.horizontalCollision);
      }
   }
}
