package com.yiyiaddon.i.d;

import com.yiyiaddon.mixin.client.ClientLevelPredictionAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket.Action;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public final class a {
   private a() {
   }

   private static BlockStatePredictionHandler a(ClientLevel var0) {
      return ((ClientLevelPredictionAccessor)var0).yiyiaddon$getPredictionHandler();
   }

   private static boolean b(InteractionHand var0, BlockHitResult var1, BlockPos var2) {
      Minecraft var3 = Minecraft.getInstance();
      LocalPlayer var4 = var3.player;
      ClientLevel var5 = var3.level;
      if (var4 != null && var5 != null) {
         BlockState var6 = var5.getBlockState(var2);
         BlockStatePredictionHandler var7 = a(var5);

         try (BlockStatePredictionHandler var8 = var7.startPredicting()) {
            var8.retainKnownServerState(var2, var6, var4);
            int var9 = var8.currentSequence();
            return com.yiyiaddon.d.c.b.a(var4.connection.getConnection(), new ServerboundUseItemOnPacket(var0, var1, var9));
         }
      } else {
         return false;
      }
   }

   public static boolean d(BlockPos var0, Direction var1) {
      Minecraft var2 = Minecraft.getInstance();
      LocalPlayer var3 = var2.player;
      ClientLevel var4 = var2.level;
      if (var3 != null && var4 != null && var0 != null) {
         BlockState var5 = var4.getBlockState(var0);
         if (var5.isAir()) {
            return false;
         }

         BlockStatePredictionHandler var6 = a(var4);

         boolean var7;
         try (BlockStatePredictionHandler var8 = var6.startPredicting()) {
            var8.retainKnownServerState(var0, var5, var3);
            int var9 = var8.currentSequence();
            var7 = com.yiyiaddon.d.c.b.a(var3.connection.getConnection(), new ServerboundPlayerActionPacket(Action.START_DESTROY_BLOCK, var0, var1, var9));
            var4.setBlock(var0, Blocks.AIR.defaultBlockState(), 11);
         }

         com.yiyiaddon.d.c.b.a(var3.connection.getConnection(), new ServerboundPlayerActionPacket(Action.STOP_DESTROY_BLOCK, var0, var1));
         return var7;
      } else {
         return false;
      }
   }

   public static boolean a(InteractionHand var0, BlockPos var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2q6h1uyf0lng2","UbqUVU1AVxJmR93m8OFYXa9QsbMuEWuuxfrODe6AaLo=",-9102335557072452535,-916499302446511069,-7424720883678121451,-4834280152533016093>()) {
            case -516869401:
               return false;
            default:
               throw null;
         }
      } else {
         Vec3 var2 = new Vec3(var1.getX() + 0.5, var1.getY() + 1.0, var1.getZ() + 0.5);
         BlockHitResult var3 = new BlockHitResult(var2, Direction.UP, var1, false);
         return b(var0, var3, var1.above());
      }
   }

   public static boolean a(InteractionHand var0, BlockPos var1, Direction var2) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s37zy2cvc5bg41","+lWQ7ZK3DL/yrJ4yE5aQvw/pzC+/zvfuRfoF2+ZIclY=",-4912276205791667745,8865756586154160549,8811678375593865727,4489273371409944381>()) {
            case 665286630:
               if (var2 != null) {
                  Vec3 var3 = new Vec3(
                     var1.getX() + 0.5 + var2.getStepX() * 0.5, var1.getY() + 0.5 + var2.getStepY() * 0.5, var1.getZ() + 0.5 + var2.getStepZ() * 0.5
                  );
                  BlockHitResult var4 = new BlockHitResult(var3, var2, var1, false);
                  return b(var0, var4, var1);
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"syxzee4j250t0","9dxOKrypHXaar0foL9WrUqKaKv5UDUHWgwsObIEsgF8=",-4835982553326745970,-15240784691842773,1371648836572401188,-119065740732697220>()) {
                     case 840756316:
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

   public static boolean b(InteractionHand var0, BlockPos var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"stidsg24bm3jo","iztWIfZqXJ4mFrrX3FG/w2B4kEkTw4UpmPBfbEitP8I=",-2538095232926292572,-9183630682939270861,-7015138842043424255,-3100303442556852847>()) {
            case -366560974:
               return false;
            default:
               throw null;
         }
      } else {
         Vec3 var2 = new Vec3(var1.getX() + 0.5, var1.getY() + 1.0, var1.getZ() + 0.5);
         BlockHitResult var3 = new BlockHitResult(var2, Direction.UP, var1, false);
         if (!b(var0, var3, var1)) {
            switch ((int)com.yiyiaddon.m.b.a<"s2kdrfzkfgq06f","LXVGM6Q3awpJtld/njHYXPhIwlfMwKn0caFbr0kvYoU=",-3755057508765181152,7887645017352352100,-867522882133340393,-3208257809985743097>()) {
               case -1723905394:
                  return false;
               default:
                  throw null;
            }
         } else {
            Minecraft var4 = Minecraft.getInstance();
            if (var4.player != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s38l6wb40n9rr8","StkRmf3/3v4B1C6nsua7IvUvY+DqmqD9KDmGkJCfOKI=",-2231871098763793879,8820000247085720372,1293082029417610714,944187838164079019>()) {
                  case -1798666365:
                     var4.player.swing(var0);
                     switch ((int)com.yiyiaddon.m.b.a<"s1d8r5x2jclfwc","7s0gv9RC4DeC7WsXW4OcPSQeADYWRM1AmI66JXdJYik=",3009122931118471498,-4392602838742900293,-516136181131083725,4667646291489913305>()) {
                        case -2028772135:
                           return true;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               return true;
            }
         }
      }
   }
}
