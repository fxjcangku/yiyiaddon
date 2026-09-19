package com.yiyiaddon.e.d.c;

import com.mojang.blaze3d.platform.InputConstants.Key;
import com.mojang.blaze3d.platform.InputConstants.Type;
import com.yiyiaddon.mixin.client.KeyboardHandlerAccessor;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;

public final class f {
   private static final Key a = Type.KEYSYM.getOrCreate(340);
   private static final Key b = Type.KEYSYM.getOrCreate(70);

   private f() {
   }

   public static void aS() {
      KeyMapping.set(a, true);
      a(1, 340, 0);
   }

   public static void aT() {
      KeyMapping.set(b, true);
      a(1, 70, 1);
   }

   public static void aU() {
      KeyMapping.set(b, false);
      a(0, 70, 1);
   }

   public static void aV() {
      KeyMapping.set(a, false);
      a(0, 340, 0);
   }

   public static void aW() {
      KeyMapping.set(b, false);
      a(0, 70, 0);
   }

   public static void aX() {
      KeyMapping.set(a, false);
      a(0, 340, 0);
   }

   private static void a(int var0, int var1, int var2) {
      Minecraft var3 = Minecraft.getInstance();
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2h1yst30yxk4w","tYzlj3H3Q3Nm/1qCtZII/LhXGwhNABIYwO4lrMbEBbQ=",-259767751542486287,-5177088167261761916,1984130315413714160,5578294410018145833>()) {
            case -2113425573:
               if (var3.getWindow() != null) {
                  KeyboardHandler var5 = var3.keyboardHandler;
                  if (var5 instanceof KeyboardHandlerAccessor) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3pd156new1u2x","xQk1IiChbY4AmUhrNbHoXQ7uME1p72iTLlOsF8GyRsg=",5014881945359463453,-1793976939990583758,-5731555541907322193,-7481565903924176242>()) {
                        case 1027562739:
                           KeyboardHandlerAccessor var4 = (KeyboardHandlerAccessor)var5;
                           switch ((int)com.yiyiaddon.m.b.a<"s369nb1lb1or6p","YkHGHqM6AM+9fMrG9qia9mINorlxjHOxIJiG3XszmA8=",-7541443638271260170,-9100438400815798779,-4594270874244901451,-5512505793089857089>()) {
                              case 273830719:
                                 var4.yiyiaddon$keyPress(var3.getWindow().handle(), var0, new KeyEvent(var1, 0, var2));
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s14uaqfl8wno3f","yltjJafU/HJ36zbTH6+OAcO/5/ugPllE1YG5FpgRRhM=",2680741639970736007,6488340150984347121,-7980801379996524610,1341840124025144159>()) {
                     case 1142588886:
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
}
