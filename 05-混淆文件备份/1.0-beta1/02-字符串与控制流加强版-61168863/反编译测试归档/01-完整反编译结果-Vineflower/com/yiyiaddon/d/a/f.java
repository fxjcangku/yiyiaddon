package com.yiyiaddon.d.a;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public record f(f.a a, BlockPos a, BlockState a, int k, boolean k) {
   public static f a(int var0) {
      return new f(f.a.ACK, null, null, var0, false);
   }

   public static f a(BlockPos var0, BlockState var1, boolean var2) {
      return new f(f.a.UPDATE, var0, var1, -1, var2);
   }

   public int h() {
      return this.k;
   }

   public boolean e() {
      return this.k;
   }

   public enum a {
      ACK,
      UPDATE;
   }
}
