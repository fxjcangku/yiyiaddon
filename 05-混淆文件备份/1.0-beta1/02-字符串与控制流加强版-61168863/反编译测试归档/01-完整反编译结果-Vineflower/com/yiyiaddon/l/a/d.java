package com.yiyiaddon.l.a;

public final class d {
   private static final float eW = 0.008F;
   private static final float eX = 0.05F;
   private final float eY;
   private final float eZ;
   private float eU;
   private float fa;
   private float eV;

   private d(float var1, float var2) {
      this.eY = var1;
      this.eZ = var2;
   }

   public static d a(float var0) {
      float var1 = 5.8F / Math.max(0.001F, var0);
      return new d(var1 * var1, 2.0F * var1);
   }

   public static d a(float var0, float var1) {
      float var2 = Math.max(0.001F, Math.min(0.9F, var1));
      float var3 = (float)Math.log(var2);
      float var4 = -var3 / (float)Math.sqrt(9.869604401089358 + var3 * var3);
      float var5 = (float)(Math.PI / (Math.max(0.001F, var0) * Math.sqrt(1.0F - var4 * var4)));
      return new d(var5 * var5, 2.0F * var4 * var5);
   }

   public void f(float var1) {
      this.eU = var1;
      this.eV = var1;
      this.fa = 0.0F;
   }

   public void d(float var1) {
      this.eV = var1;
   }

   public float q() {
      return this.eV;
   }

   public float r() {
      return this.eU;
   }

   public boolean fQ() {
      if (Math.abs(this.eV - this.eU) < 5.0E-4F) {
         switch ((int)com.yiyiaddon.m.b.a<"sd5kxr5ldltnj","vvYEo4cIVOEcxIAB8unyWekbMrXS+YGq10adKTR3seU=",4294153759207540733,-4034515911466414774,-8164622266865388547,-8835505747721235709>()) {
            case -369727106:
               if (Math.abs(this.fa) < 5.0E-4F) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1mu5vb2w4tjpx","EFiPBtDBc6zkUv+OXqCTySS5pbOR0O4WjVktBcKA6VQ=",-7832686243509185933,-48342994523801884,2567130590030973106,-6764312232638109643>()) {
                     case 403035141:
                        switch ((int)com.yiyiaddon.m.b.a<"stezj3ejxbanz","d/zAi5hKzijdBzeWosaoxgRD/xXApm2fV+md4sRz6kE=",2020407405160399598,3002626276906558291,-6572890380001530314,7184516287559265390>()) {
                           case -192104930:
                              return true;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s33saa9q51ct0t","xSSYlqURfVtYRNfoQ+s53vru6Bmtw+XEIxpjcdtOe2Q=",-6315444482791547382,-4318187929717116337,-5054434960044509330,6435829452290497909>()) {
         case -1918963128:
            return false;
         default:
            throw null;
      }
   }

   public void a(float var1) {
      float var2 = Math.min(Math.max(var1, 0.0F), 0.05F);
      int var3 = Math.max(1, (int)Math.ceil(var2 / 0.008F));
      float var4 = var2 / var3;
      int var5 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"sywo4elk5caoa","SYAE2JG4rxSusXWKTz+t3Qv9sjRNWkaoEHtoxXdxIvY=",7704275586977846175,-2796345969658192672,-3152639581517049634,-5549449710904664185>()) {
         case -1883440141:
            while (var5 < var3) {
               switch ((int)com.yiyiaddon.m.b.a<"sc3lejlnh4xyp","6qo6Q+1VYCZSGAobNLK29mdb9jMvLp+dXZ+HZRJvHm8=",-522594493526960834,-7519619104548678476,8090983402341045677,-3229873756635840656>()) {
                  case 1212765774:
                     float var6 = this.eY * (this.eV - this.eU) - this.eZ * this.fa;
                     this.fa += var6 * var4;
                     this.eU = this.eU + this.fa * var4;
                     var5++;
                     switch ((int)com.yiyiaddon.m.b.a<"sahrmspbiflia","USSE+MllXuV8mItV9PipgnRL3OI3flSC3ucVbqjNPQY=",-2332753627448115256,-3277628545708128833,2183071175096605406,933986402521121424>()) {
                        case -2125883300:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            if (this.fQ()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3aw1s1fm0vg10","9Fv9TLvOxnzR9IIfu4zMwoOgzTixaNM1pYlPMaODy4o=",-1128517341034108677,-6441915737846237825,-5173637037455983528,-7822022316084634974>()) {
                  case 1100403549:
                     this.eU = this.eV;
                     this.fa = 0.0F;
                     switch ((int)com.yiyiaddon.m.b.a<"s1oviv5tu9nv6a","kRHAFINUiN+tFh9fkhm/yJ2bGftVuGcCHBUy6r4qIPM=",5518057866618133387,-5480281508330627846,-8843356852293458115,9180253454899117186>()) {
                        case 1029213352:
                           return;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return;
         default:
            throw null;
      }
   }
}
