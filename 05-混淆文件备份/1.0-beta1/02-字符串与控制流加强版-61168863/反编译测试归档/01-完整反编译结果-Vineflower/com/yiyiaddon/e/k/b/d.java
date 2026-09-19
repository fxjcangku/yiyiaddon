package com.yiyiaddon.e.k.b;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class d {
   public static final int kX = Integer.MIN_VALUE;
   public final BlockPos z;
   public final BlockState c;
   public final Block h;
   public final Direction c;
   public d.a a = d.a.SCHEDULED;
   public int aX;
   public int kY;
   public int gP = Integer.MIN_VALUE;
   public int kZ = Integer.MIN_VALUE;
   public int la = Integer.MIN_VALUE;
   public int lb = Integer.MIN_VALUE;
   public int lc = -1;
   public int ld = -1;
   public int le;
   public int gQ;
   public int do = -1;
   public int lf = -1;
   public float bE;
   public boolean de;
   public boolean df;
   public boolean dg;

   public d(BlockPos var1, BlockState var2, Direction var3) {
      this.z = var1.immutable();
      this.c = var2;
      this.h = var2.getBlock();
      this.c = var3 == null ? Direction.UP : var3;
   }

   public void B(int var1) {
      this.a = d.a.SCHEDULED;
      this.aX = var1;
      this.kY = 0;
      this.gP = Integer.MIN_VALUE;
      this.kZ = Integer.MIN_VALUE;
      this.la = Integer.MIN_VALUE;
      this.lb = Integer.MIN_VALUE;
      this.lc = -1;
      this.ld = -1;
      this.le = 0;
      this.df = false;
      this.dg = false;
      this.bE = 0.0F;
      this.do = -1;
      this.lf = -1;
      this.de = false;
   }

   public enum a {
      SCHEDULED,
      START_PENDING,
      MINING,
      STOP_PENDING,
      AWAITING_CONFIRM;
   }
}
