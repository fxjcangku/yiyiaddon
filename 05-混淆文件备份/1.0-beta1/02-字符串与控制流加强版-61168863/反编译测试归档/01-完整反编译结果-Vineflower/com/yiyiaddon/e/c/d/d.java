package com.yiyiaddon.e.c.d;

import net.minecraft.core.BlockPos;

public record d(d.a a, com.yiyiaddon.e.c.d.a a, BlockPos e) {
   public static d a(com.yiyiaddon.e.c.d.a var0, BlockPos var1) {
      return new d(d.a.HARVEST, var0, var1);
   }

   public static d b(com.yiyiaddon.e.c.d.a var0, BlockPos var1) {
      return new d(d.a.PLANT, var0, var1);
   }

   public static d e(BlockPos var0) {
      return new d(d.a.TILL, null, var0);
   }

   public BlockPos a() {
      return this.e;
   }

   public enum a {
      HARVEST,
      PLANT,
      TILL;
   }
}
