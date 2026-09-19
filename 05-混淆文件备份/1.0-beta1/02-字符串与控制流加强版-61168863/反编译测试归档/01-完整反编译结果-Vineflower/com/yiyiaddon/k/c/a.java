package com.yiyiaddon.k.c;

import com.yiyiaddon.m.b;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

public final class a {
   private a() {
   }

   public static boolean d(BlockPos var0, Direction var1) {
      return com.yiyiaddon.i.d.a.d(var0, var1);
   }

   public static boolean a(InteractionHand var0, BlockPos var1) {
      return com.yiyiaddon.i.d.a.a(var0, var1);
   }

   public static boolean a(InteractionHand var0, BlockPos var1, Direction var2) {
      return com.yiyiaddon.i.d.a.a(var0, var1, var2);
   }

   public static boolean b(InteractionHand var0, BlockPos var1) {
      return com.yiyiaddon.i.d.a.b(var0, var1);
   }

   public static int e(ItemStack var0) {
      if (var0 != null) {
         switch ((int)b.a<"s2jp1zilfshm0w","xD9BChMBHbcImIkiBbNlNDKtbfQFPniH8+TAyb+nXCU=",4685799731903676158,-2542954018908279587,-7874035571720590890,7231346244243422824>()) {
            case 702267492:
               if (!var0.isEmpty()) {
                  if (!var0.isDamageableItem()) {
                     switch ((int)b.a<"sdx15xygubt5l","m6BRfDPDTSasY/mHkpvnTq3Y7Gyyk5SwvxH4V791cZo=",-709535943393342438,844572987847750500,-1655315964857810617,347606946417677865>()) {
                        case -1001124321:
                           return Integer.MAX_VALUE;
                        default:
                           throw null;
                     }
                  } else {
                     if (var0.has(DataComponents.UNBREAKABLE)) {
                        switch ((int)b.a<"s3mbf7ryjxnjs0","K+q84hjevyjc16i7VWiItWvypKnnMWpO9uMAYWkUL3s=",-2237314975573714599,3389781264934152463,8349812500621164338,5119845780676201444>()) {
                           case -1692148113:
                              return Integer.MAX_VALUE;
                           default:
                              throw null;
                        }
                     }

                     return var0.getMaxDamage() - var0.getDamageValue();
                  }
               } else {
                  switch ((int)b.a<"s2oofwpi3gt95z","LmeeZV0HJyGqWZfdwWhVN4DXAOm8IMf9H4eO/KXoJg4=",6117521520906052259,-8105995026548612520,2985693660448980965,6729543994795012430>()) {
                     case 289374032:
                        return 0;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return 0;
      }
   }
}
