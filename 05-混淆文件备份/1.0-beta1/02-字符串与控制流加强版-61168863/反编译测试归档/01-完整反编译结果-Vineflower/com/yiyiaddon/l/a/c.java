package com.yiyiaddon.l.a;

public final class c {
   private static final float eS = 0.085F;
   private static final float eT = 0.05F;
   private float eU;
   private float eV;

   public void d(float var1) {
      this.eV = var1;
   }

   public float q() {
      return this.eV;
   }

   public float r() {
      return this.eU;
   }

   public void e(float var1) {
      this.eU = var1;
      this.eV = var1;
   }

   public float b(float var1, float var2, float var3) {
      float var4 = Math.min(var2, var3);
      float var5 = Math.max(var2, var3);
      this.eV = Math.max(var4, Math.min(var5, this.eV));
      float var6 = 1.0F - (float)Math.exp(-Math.max(var1, 0.0F) / 0.085F);
      this.eU = this.eU + (this.eV - this.eU) * var6;
      this.eU = Math.max(var4, Math.min(var5, this.eU));
      if (Math.abs(this.eV - this.eU) < 0.05F) {
         switch ((int)com.yiyiaddon.m.b.a<"s2vzituhsawit0","XscT5Tzgrz66UwGTpJcaoraIyPCyK3lx77TWzQU8qTM=",-2731277154799162656,-488069291498358680,3678991029877107516,-2522250510144655994>()) {
            case -786585008:
               this.eU = this.eV;
               switch ((int)com.yiyiaddon.m.b.a<"s33aqcjkuzgs01","MDsM1KzI/NtCYAtMuDfaP8W4vfOvvqALGmNxH7fXvzU=",8874396506848021732,-1616813046388424769,-8974867217038576738,-6950701587665263433>()) {
                  case 769255704:
                     return this.eU;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return this.eU;
      }
   }
}
