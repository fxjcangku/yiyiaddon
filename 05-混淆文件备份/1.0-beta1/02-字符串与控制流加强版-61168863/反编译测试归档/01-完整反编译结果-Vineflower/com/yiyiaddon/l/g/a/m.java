package com.yiyiaddon.l.g.a;

import org.joml.Matrix4f;
import org.joml.Vector4f;

public final class m {
   private static final float kU = 0.02F;
   private final Matrix4f b;
   private final double bR;
   private final double bS;
   private final double bT;
   private final float kV;
   private final float kW;
   private final Vector4f a = new Vector4f();

   public m(h var1) {
      this.b = new Matrix4f(var1.a());
      this.bR = var1.s();
      this.bS = var1.t();
      this.bT = var1.u();
      this.kV = var1.ab();
      this.kW = var1.ac();
   }

   public float ab() {
      return this.kV;
   }

   public float ac() {
      return this.kW;
   }

   public i a(double var1, double var3, double var5) {
      this.a.set((float)(var1 - this.bR), (float)(var3 - this.bS), (float)(var5 - this.bT), 1.0F);
      this.b.transform(this.a);
      return this.a(this.a.x, this.a.y, this.a.w);
   }

   public i[] a(double var1, double var3, double var5, double var7, double var9, double var11) {
      Vector4f var13 = this.a(var1, var3, var5);
      Vector4f var14 = this.a(var7, var9, var11);
      float var15 = var13.w;
      float var16 = var14.w;
      if (var15 <= 0.02F) {
         switch ((int)com.yiyiaddon.m.b.a<"s206u395eqxclk","RatQ+aDRAEN/TuhewyyH1nlATLuuaKE8Vy0jHEnznk8=",1257103631514997009,-7351163761704431019,7394310076248640821,7481179741987283892>()) {
            case -1483669519:
               if (var16 <= 0.02F) {
                  switch ((int)com.yiyiaddon.m.b.a<"sgledpc4idbgo","hG6Rg58Mlmbi3FFEid4Ysc+V66oudH1mvXCtjxIEiwc=",293661678780118751,-4066601194772570552,-3917512644773568851,-3650438175754103358>()) {
                     case 1180981798:
                        return null;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (!(var15 <= 0.02F)) {
         label35:
         switch ((int)com.yiyiaddon.m.b.a<"s3fb5vubsr5ubp","cMNHEFO6asPE/eLGx8DzGOVRs3b0afoPlc/5j87AvoA=",-6416507799249896407,7016005170939655396,-3597073887453294562,28505418911819464>()) {
            case -803284244:
               if (!(var16 <= 0.02F)) {
                  return new i[]{this.a(var13.x, var13.y, var13.w), this.a(var14.x, var14.y, var14.w)};
               }

               switch ((int)com.yiyiaddon.m.b.a<"s2otkyftmrmabj","ZjoEUqIcTV0YI+6TmGD4xvX/MD9H44Wb8KnxhTwdtOo=",-9190242781892707724,2617580572789941675,6412219225237026294,8436701662002809858>()) {
                  case -1803047455:
                     break label35;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      float var17 = (0.02F - var15) / (var16 - var15);
      float var18 = var13.x + (var14.x - var13.x) * var17;
      float var19 = var13.y + (var14.y - var13.y) * var17;
      float var20 = var13.z + (var14.z - var13.z) * var17;
      if (var15 <= 0.02F) {
         switch ((int)com.yiyiaddon.m.b.a<"s4wxc526amf5a","IugpACDEhGe6CzMVSOJ4ldTc/f978er/FZyoF9vzkeA=",8735730408099172627,-7462984126356400944,5257480227242397055,-3950563222991263633>()) {
            case 314341541:
               var13.set(var18, var19, var20, 0.02F);
               switch ((int)com.yiyiaddon.m.b.a<"s1otjsynq8ptm8","O7Y1hzTijTKjnvcEQV7mTnnUVAQ5i7oupimBR7UpMCw=",3871193924384245362,6214033860119471493,4912835933518617986,-3274000384319155118>()) {
                  case 1374307131:
                     return new i[]{this.a(var13.x, var13.y, var13.w), this.a(var14.x, var14.y, var14.w)};
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var14.set(var18, var19, var20, 0.02F);
         switch ((int)com.yiyiaddon.m.b.a<"snqegozgwellf","dYgFteYL9LUmrH+R7cKiajAxbHfB2YR4NciHwP5pzFk=",769397650005880038,-651627584401656893,-8321666513430971370,5265735287607797703>()) {
            case -23737836:
               return new i[]{this.a(var13.x, var13.y, var13.w), this.a(var14.x, var14.y, var14.w)};
            default:
               throw null;
         }
      }
   }

   private Vector4f a(double var1, double var3, double var5) {
      Vector4f var7 = new Vector4f((float)(var1 - this.bR), (float)(var3 - this.bS), (float)(var5 - this.bT), 1.0F);
      this.b.transform(var7);
      return var7;
   }

   private i a(float var1, float var2, float var3) {
      if (var3 <= 0.02F) {
         switch ((int)com.yiyiaddon.m.b.a<"s2mbk248me16v0","tMb3scKZhlIECU1/bgrqT4ozD/URANnTRiw0vENYM/0=",1760370060300253471,922479313860514633,-1889886895266199856,8097261667247050610>()) {
            case -1932835585:
               return i.a();
            default:
               throw null;
         }
      } else {
         float var4 = var1 / var3;
         float var5 = var2 / var3;
         return new i((var4 * 0.5F + 0.5F) * this.kV, (0.5F - var5 * 0.5F) * this.kW, true, var3);
      }
   }
}
