package com.yiyiaddon.e.i.d;

public enum c {
   NORTH(0, -1),
   EAST(1, 0),
   SOUTH(0, 1),
   WEST(-1, 0);

   private final int fs;
   private final int ft;

   c(int var3, int var4) {
      this.fs = var3;
      this.ft = var4;
   }

   public int aY() {
      return this.fs;
   }

   public int aZ() {
      return this.ft;
   }

   public c a() {
      return values()[(this.ordinal() + 2) % values().length];
   }
}
