package com.yiyiaddon.i.c;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.Settings;
import baritone.api.Settings.Setting;
import baritone.api.pathing.goals.GoalNear;
import com.yiyiaddon.m.b;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;

public final class a {
   private static List<Block> cL;
   private static boolean fq;

   private a() {
   }

   public static boolean ft() {
      try {
         return a() != null;
      } catch (Throwable var1) {
         return false;
      }
   }

   public static boolean b(BlockPos var0, int var1) {
      return a(var0, var1, false);
   }

   public static boolean a(BlockPos var0, int var1, Collection<Block> var2) {
      a(var2);
      boolean var3 = b(var0, var1, false);
      if (!var3) {
         switch ((int)b.a<"s3m3lzv2ql93ha","xsDEPkKitJEOxIy1nmqSX8IcSMpMSjb8v7TTfgAIrEA=",-9173659271923263388,5891640103568453606,-1745814519586014630,-7687508655500900244>()) {
            case -1270807229:
               jk();
               switch ((int)b.a<"s1mad5sk8k2p6b","4Onrh1x6m8C32+TlZwP4MAwk9NNDQAD9IRY9wNsD77g=",280851778567750410,1055990343703097739,5750445537893906244,8765898553439225678>()) {
                  case -1983466496:
                     return var3;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return var3;
      }
   }

   public static boolean a(BlockPos var0, int var1, boolean var2) {
      jk();
      return b(var0, var1, var2);
   }

   private static boolean b(BlockPos var0, int var1, boolean var2) {
      try {
         IBaritone var3 = a();
         if (var3 == null) {
            return false;
         }

         Settings var4 = BaritoneAPI.getSettings();
         var4.allowBreak.value = var2;
         var4.allowPlace.value = var2;
         var3.getCustomGoalProcess().setGoalAndPath(new GoalNear(var0, var1));
         return true;
      } catch (Throwable var5) {
         return false;
      }
   }

   public static boolean cX() {
      try {
         IBaritone var0 = a();
         return var0 == null ? false : var0.getPathingBehavior().isPathing() || var0.getCustomGoalProcess().isActive();
      } catch (Throwable var1) {
         return false;
      }
   }

   public static void i() {
      try {
         IBaritone var0 = a();
         if (var0 != null) {
            var0.getPathingBehavior().cancelEverything();
            var0.getCustomGoalProcess().onLostControl();
         }
      } catch (Throwable var4) {
      } finally {
         jk();
      }
   }

   public static boolean a(BlockPos var0, double var1) {
      try {
         Minecraft var3 = Minecraft.getInstance();
         if (var3.player == null) {
            return false;
         }

         double var4 = var0.getX() + 0.5 - var3.player.getX();
         double var6 = var0.getY() + 0.5 - var3.player.getY();
         double var8 = var0.getZ() + 0.5 - var3.player.getZ();
         return var4 * var4 + var6 * var6 + var8 * var8 <= var1 * var1;
      } catch (Throwable var10) {
         return false;
      }
   }

   private static IBaritone a() {
      return BaritoneAPI.getProvider().getPrimaryBaritone();
   }

   private static synchronized void a(Collection<Block> var0) {
      if (!fq) {
         try {
            Setting var1 = BaritoneAPI.getSettings().blocksToAvoid;
            cL = new ArrayList<>((Collection<? extends Block>)var1.value);
            ArrayList var2 = new ArrayList((Collection)var1.value);
            if (var0 != null && !var0.isEmpty()) {
               var2.removeIf(var0::contains);
            }

            var1.value = (T)var2;
            fq = true;
         } catch (Throwable var3) {
            cL = null;
            fq = false;
         }
      }
   }

   private static synchronized void jk() {
      if (fq) {
         try {
            BaritoneAPI.getSettings().blocksToAvoid.value = cL == null ? new ArrayList<>() : new ArrayList<>(cL);
         } catch (Throwable var4) {
         } finally {
            cL = null;
            fq = false;
         }
      }
   }
}
