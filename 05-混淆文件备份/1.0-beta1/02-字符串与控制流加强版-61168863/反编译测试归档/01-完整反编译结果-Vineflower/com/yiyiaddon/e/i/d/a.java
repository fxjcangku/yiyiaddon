package com.yiyiaddon.e.i.d;

public record a(int fo, int fp, int fq) {
   public a a(c var1) {
      return new a(this.fo + var1.aY(), this.fp, this.fq + var1.aZ());
   }

   public a a() {
      return new a(this.fo, this.fp - 1, this.fq);
   }

   public a b() {
      return new a(this.fo, this.fp + 1, this.fq);
   }

   public int aj() {
      return this.fo;
   }

   public int ak() {
      return this.fp;
   }

   public int al() {
      return this.fq;
   }
}
