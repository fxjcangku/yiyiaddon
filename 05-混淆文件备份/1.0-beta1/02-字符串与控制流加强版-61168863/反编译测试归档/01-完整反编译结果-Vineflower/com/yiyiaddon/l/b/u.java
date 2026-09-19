package com.yiyiaddon.l.b;

import io.github.humbleui.skija.Canvas;

public final class u implements g {
   private final float hC;
   private final float hD;
   private i b;
   private i c;
   private float hE = -Float.MAX_VALUE;
   private float hF = Float.MAX_VALUE;

   public u(float var1, float var2) {
      this.hC = Math.max(0.0F, var1);
      this.hD = 12.0F;
      this.b = new i(var2);
      this.c = new i(var2);
   }

   public i a() {
      return this.b;
   }

   public i b() {
      return this.c;
   }

   public void f() {
      float var1 = 4.0F;
      this.b = new i(var1);
      this.c = new i(var1);
   }

   @Override
   public void c(float var1, float var2) {
      this.hE = var1;
      this.hF = var2;
   }

   @Override
   public float b() {
      return Math.max(this.hC, Math.max(this.b.b(), this.c.b()));
   }

   @Override
   public void a(float var1) {
      this.b.a(var1);
      this.c.a(var1);
   }

   @Override
   public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      float var8 = (var4 - this.hD) * 0.5F;
      if (var8 <= 0.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s13cbuwazwdyo","dlhzjJ52I0lp5DtHJ5SJcF5PrZ0OXcButihukC6zW/w=",-3624690248697779794,5168352834284381084,-7998761313990412198,7965930357841644507>()) {
            case -836482821:
               return;
            default:
               throw null;
         }
      } else {
         this.b.a(var1, var2, var3, var8, var5, this.hE, this.hF, var6, var7);
         this.c.a(var1, var2 + var8 + this.hD, var3, var8, var5, this.hE, this.hF, var6, var7);
      }
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
      float var7 = (var5 - this.hD) * 0.5F;
      if (var7 <= 0.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ik8lkeq37ktl","V8JoA7U1aUn+EMla22+6C93GG/RmVIEG0wjCFYddza4=",-8522542274702633832,-8154769921965109481,-6716950004243975930,5990277722201090071>()) {
            case -1293672000:
               return false;
            default:
               throw null;
         }
      } else if (var1 <= var3 + var7) {
         switch ((int)com.yiyiaddon.m.b.a<"s3qrup1e0b5t3j","mKUlHSv/dwiVtjPfPA5dCNbOejA2wKlzS+icmKzYwZg=",-1657233339122781929,2694314183797568995,-4587940360944470612,-6142126295751435141>()) {
            case -49183821:
               return this.b.a(var1, var2, var3, var4, var7, Float.MAX_VALUE, var6);
            default:
               throw null;
         }
      } else {
         return this.c.a(var1, var2, var3 + var7 + this.hD, var4, var7, Float.MAX_VALUE, var6);
      }
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5) {
      float var6 = (var5 - this.hD) * 0.5F;
      if (var6 <= 0.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s1t87k3mdb9lr6","Cw5YAe6zqR2sz4+08VUEOsc9K9gfHTeBitTtn19n6Mw=",2248324288266590026,5271154556072074610,4749568503089188200,5197316528488201933>()) {
            case 754648011:
               return false;
            default:
               throw null;
         }
      } else if (var1 <= var3 + var6) {
         switch ((int)com.yiyiaddon.m.b.a<"s30g9p0oq2eku7","51La2GLDc70j8qPV4Kb5WUtEx4u/eIEgOEmVxbu/pso=",-2986736538577516458,8846694042032362163,3689945640073483043,560804857923100167>()) {
            case -312463111:
               return this.b.a(var1, var2, var3, var4, var6, Float.MAX_VALUE);
            default:
               throw null;
         }
      } else {
         return this.c.a(var1, var2, var3 + var6 + this.hD, var4, var6, Float.MAX_VALUE);
      }
   }

   @Override
   public void F() {
      this.b.F();
      this.c.F();
   }
}
