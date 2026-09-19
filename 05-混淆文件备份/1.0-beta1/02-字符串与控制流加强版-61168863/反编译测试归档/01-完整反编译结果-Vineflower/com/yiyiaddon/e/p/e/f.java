package com.yiyiaddon.e.p.e;

import java.util.Locale;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public record f(Vec3 l, BlockPos R, double bj) {
   public String eZ() {
      return String.format(
         Locale.ROOT,
         (String)com.yiyiaddon.m.b.a<"s5z503jpl39us","3NilDq6RoU/adSpJBgB+/bdcZElMes9NvhDdK4piwgGPEHG8YWTyHg1WlITf67yFyiKMpZ8oMnurnX5Z",2193049587677802527,-2154546148417105161,884596399954701020,48578287319058684>(),
         this.l.x(),
         this.l.y(),
         this.l.z()
      );
   }

   public Vec3 b() {
      return this.l;
   }

   public BlockPos m() {
      return this.R;
   }

   public double h() {
      return this.bj;
   }
}
