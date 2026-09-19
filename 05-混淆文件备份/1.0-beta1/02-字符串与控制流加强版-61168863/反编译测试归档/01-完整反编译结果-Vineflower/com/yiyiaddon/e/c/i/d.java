package com.yiyiaddon.e.c.i;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;

public abstract class d implements e {
   private static final int bk = 10;
   protected final BlockPos j;
   protected final com.yiyiaddon.k.a.a e;
   protected final double l;
   private int bl;

   protected d(BlockPos var1, com.yiyiaddon.k.a.a var2, double var3) {
      this.j = var1;
      this.e = var2;
      this.l = var3;
   }

   @Override
   public final l a() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2r027ctfkpo42","BzNBvlqEPwtUstZrI7I5roBnaD6l5j1cnuIzdB9yF0A=",5330720401580190665,6005798370689814896,-8308147202526670301,-246130264722816580>()) {
            case -1349332680:
               if (var1.level != null) {
                  if (!(var1.level.getBlockEntity(this.j) instanceof Container)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s33adq8bt24xmq","bHuCaJRrMBltjfJdepcQiExM1pd0w/JZZLtX9g7Zu4U=",-6792151066037848702,-5742027489152660572,2296233627025063867,-1690456978944285994>()) {
                        case 674348241:
                           return com.yiyiaddon.e.c.i.l.CONTAINER_MISSING;
                        default:
                           throw null;
                     }
                  } else if (!this.P()) {
                     switch ((int)com.yiyiaddon.m.b.a<"sl0n3eisu9z7i","X6xrEucwzV1QftDiCGrK0+wYNS3yPAc2YmWGquDnjx0=",-2825873356421080564,-8880120429178716452,3729449598946979338,6037091826939468328>()) {
                        case -1406712170:
                           if (!com.yiyiaddon.i.c.a.ft()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3msftsjpbhtr8","ffsSrNr1D4zqqsP4/FFzFt1fRPM311GNSlqDyL4usAE=",-6991538420583932170,1748341305451043117,4753399032166804841,8088917816022709884>()) {
                                 case 1396304770:
                                    return com.yiyiaddon.e.c.i.l.NAVIGATION_FAILED;
                                 default:
                                    throw null;
                              }
                           } else {
                              if (!com.yiyiaddon.i.c.a.cX()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1gdiq5s690mdg","JS4EcVxbU6FpIZBz5fOiuZGVRtG6aDQOVmywHAQAL0s=",3784514514532340042,4906773865955518790,248785444841401597,6874190743852071482>()) {
                                    case -644341126:
                                       com.yiyiaddon.i.c.a.b(this.j, 1);
                                       switch ((int)com.yiyiaddon.m.b.a<"s3ii29kreo7hh1","OnTeF/g8jNo+GhHd1ZMYy6AhLbwjrco5rKGe7B1Dwh8=",39947898757660756,158198839694864409,7684377556284066870,3917011517476408172>()) {
                                          case 1860776702:
                                             return com.yiyiaddon.e.c.i.l.IN_PROGRESS;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              return com.yiyiaddon.e.c.i.l.IN_PROGRESS;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     com.yiyiaddon.i.c.a.i();
                     if (com.yiyiaddon.i.a.a.b() == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1i37yfmjwlj36","3QmtrKKNDSK5q7qy8qj7caPzGxKEDWFLIdJFaoak4YY=",9213170548301383964,9142490138741875658,-993906458509848285,8036803838205036123>()) {
                           case 1886153255:
                              this.aw();
                              return com.yiyiaddon.e.c.i.l.IN_PROGRESS;
                           default:
                              throw null;
                        }
                     } else {
                        if (!this.e.fr()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2d9l39kw55uhz","d5njAfNqnc/laDGUgCjpnC/0RHf7aIstYIgx/tPskS8=",-2445660049590181517,-8378293463447589270,-2140041346685575781,4915891834389974080>()) {
                              case -205130350:
                                 return com.yiyiaddon.e.c.i.l.IN_PROGRESS;
                              default:
                                 throw null;
                           }
                        }

                        return this.c();
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1s2sdggrcbdxj","RzrFvr42ZaRlB2gxi8HgxkOL+cFxwAEHGXZga9piIkY=",-8474948534769509953,351527734204323727,6516527054501723000,1388997225215568612>()) {
                     case -2097518657:
                        return com.yiyiaddon.e.c.i.l.CONTAINER_MISSING;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return com.yiyiaddon.e.c.i.l.CONTAINER_MISSING;
      }
   }

   protected abstract l c();

   @Override
   public boolean O() {
      return true;
   }

   @Override
   public void i() {
      com.yiyiaddon.i.c.a.i();
      com.yiyiaddon.i.a.a.cD();
      this.e.f();
   }

   private boolean P() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2k5ziohufa6tp","8Zh5AmzDCKWuQpUx/zysex/U/Y3tt8ejCpLciUaE8OQ=",-7555569450288864485,1945808889043586743,1598335977839452766,7046740665144936042>()) {
            case 40267046:
               return false;
            default:
               throw null;
         }
      } else {
         int var2 = Math.abs(var1.player.blockPosition().getX() - this.j.getX());
         int var3 = Math.abs(var1.player.blockPosition().getY() - this.j.getY());
         int var4 = Math.abs(var1.player.blockPosition().getZ() - this.j.getZ());
         if (var2 <= 1) {
            switch ((int)com.yiyiaddon.m.b.a<"s27wb4fn25yslh","kMRYlqhXy/LsR6/T2aATsHGPtr90IN+WvCwUWv/nRNg=",5291577631989899662,3962429493451942194,2830794687182387472,-2950265361440018550>()) {
               case -363143085:
                  if (var3 <= 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3i5jjlwjyj190","RQuhKzr5bNNKFDdc4kd0yGqsSYtutK8hCR8dMcO1Uu8=",2954512524748545763,5745535975960649128,7967528587655759295,-6642248255976017203>()) {
                        case -710532123:
                           if (var4 <= 1) {
                              switch ((int)com.yiyiaddon.m.b.a<"scf9opldtzm0t","SAW3flfWxgEK3TBilW4JKZ79q6Qb87YsRmbVarPdfqc=",4267514811610696566,-8817527995188232892,-3505751944250050467,-5588597031239045708>()) {
                                 case 1478259197:
                                    if ((var2 | var3 | var4) != 0) {
                                       switch ((int)com.yiyiaddon.m.b.a<"svm6e03jifxz4","luuzsegY2tNSlcd8hJ+V8Vzf066Ami2IiXleUp+Ojw0=",4729802495218333289,-5971068616883110223,-5452952263127735133,-1125806926459509105>()) {
                                          case 37961765:
                                             switch ((int)com.yiyiaddon.m.b.a<"s2zje1uwhknyet","ZPW2S809GOM9ClddK4tPysWkWRxUBIkHburpX5gnZfM=",-7053259094609958766,1556759988820344082,2573445549531107374,4948542290693846467>()) {
                                                case -1100478316:
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

         switch ((int)com.yiyiaddon.m.b.a<"s29mdh6fzayqll","pbmFvbvPbPuuA8bK2FDsjUDu3HRt2y4lLOi8C6uc5FM=",4832646699697388144,2738699678750713686,-2570729061687524749,-2808042031437979907>()) {
            case 1530415977:
               return false;
            default:
               throw null;
         }
      }
   }

   private void aw() {
      this.bl++;
      if (this.bl < 10) {
         switch ((int)com.yiyiaddon.m.b.a<"s1kvdadq609a6p","0LOCJoVxgmOoJAeZGKSqQQVsq0ScgAMLl5xEd60tfHQ=",7996201475771875149,5809871866190424436,-5610907094961707547,-9158054668083355851>()) {
            case 2020042632:
               return;
            default:
               throw null;
         }
      } else {
         this.bl = 0;
         this.e.f();
         com.yiyiaddon.i.d.a.a(InteractionHand.MAIN_HAND, this.j, Direction.UP);
      }
   }
}
