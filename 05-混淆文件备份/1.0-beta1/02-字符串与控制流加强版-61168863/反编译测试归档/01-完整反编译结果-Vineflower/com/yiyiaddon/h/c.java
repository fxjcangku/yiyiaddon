package com.yiyiaddon.h;

import java.util.function.Supplier;

public record c(String DK, String DL, String DM, String DN, int sw, Supplier<com.yiyiaddon.l.f.a> d, boolean fn) {
   public c(String var1, String var2, String var3, String var4, int var5) {
      this(var1, var2, var3, var4, var5, null, false);
   }

   public c(String var1, String var2, String var3, String var4, int var5, Supplier<com.yiyiaddon.l.f.a> var6) {
      this(var1, var2, var3, var4, var5, var6, false);
   }

   public static c a(String var0, String var1, String var2, String var3, int var4, Supplier<com.yiyiaddon.l.f.a> var5) {
      return new c(var0, var1, var2, var3, var4, var5, true);
   }

   public String s() {
      return this.DK;
   }

   public String m() {
      return this.DL;
   }

   public String c() {
      return this.DM;
   }

   public String w() {
      return this.DN;
   }

   public int i() {
      return this.sw;
   }

   public Supplier<com.yiyiaddon.l.f.a> b() {
      return this.d;
   }

   public boolean fm() {
      return this.fn;
   }
}
