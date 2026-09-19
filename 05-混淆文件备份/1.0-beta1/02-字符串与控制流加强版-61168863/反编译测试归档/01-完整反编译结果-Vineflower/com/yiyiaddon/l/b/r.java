package com.yiyiaddon.l.b;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import java.awt.Color;

public final class r implements g {
   private static final float hk = 92.0F;
   private static final float hl = 8.0F;
   private static final int tm = 36;
   private static final int tn = 18;
   private static final float hm = 0.6F;
   private static final float hn = 10.0F;
   private final com.yiyiaddon.l.g.a.d ac;
   private final Paint i = new Paint().setAntiAlias(true);
   private final Paint j = new Paint().setAntiAlias(true).setMode(PaintMode.STROKE);

   public r(com.yiyiaddon.l.g.a.d var1) {
      this.ac = var1;
   }

   @Override
   public float b() {
      return 92.0F;
   }

   @Override
   public void a(float var1) {
   }

   @Override
   public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      com.yiyiaddon.l.i.c var8 = com.yiyiaddon.l.i.c.a();
      RRect var9 = RRect.makeXYWH(var2, var3, var4, 92.0F, 8.0F);
      var1.save();
      var1.clipRRect(var9);
      float var10 = this.ac.W();
      float var11 = var4 / 36.0F;
      float var12 = 5.111111F;
      int var13 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s2brcby93dvxou","Af5rr71GviDp2GHfNWscUUIYmKvqSCVXm+cShx2XJJc=",6891800745608030155,7105479236717374210,4851522756767832800,3554955290674331649>()) {
         case 1485008578:
            while (var13 < 18) {
               switch ((int)com.yiyiaddon.m.b.a<"s3cvnbylx11cit","fGyg1pAqdgI1+lQ4X6izSj3ymhwXsoExvbhfyvTAB/Y=",7321759059320602054,-4016612251610722382,-4627343542404965602,-7269149627399035667>()) {
                  case 2012829489:
                     float var14 = 1.0F - (var13 + 0.5F) / 18.0F;
                     float var15 = var3 + var13 * var12;
                     float var16 = var15 + var12 + 0.6F;
                     int var17 = 0;
                     switch ((int)com.yiyiaddon.m.b.a<"s2mderj0xp4z3e","hOcSHlDIOm8sobEneOegA8W0lVsDWP1OmNg5T2zDm3U=",1727586353665419265,8342899405204110232,-314116368439900661,-5515991110686107412>()) {
                        case 541337970:
                           while (var17 < 36) {
                              switch ((int)com.yiyiaddon.m.b.a<"s14wg7yq2qqeha","NVFqx6DO3M5gqqlz9rEohwXMjWyLbtV3ZFpQsPLe7JA=",6456946600523960382,-4736464506266067833,-8398789132439702821,-4079117268693163348>()) {
                                 case 256111581:
                                    float var18 = (var17 + 0.5F) / 36.0F;
                                    int var19 = Color.HSBtoRGB(var10, var18, var14) & 16777215;
                                    this.i.setColor(com.yiyiaddon.l.i.c.a(var19, var5));
                                    var1.drawRect(Rect.makeLTRB(var2 + var17 * var11, var15, var2 + (var17 + 1) * var11 + 0.6F, var16), this.i);
                                    var17++;
                                    switch ((int)com.yiyiaddon.m.b.a<"s39afgzp1n3f6u","9WjYfMXZvbCCPQJO0jHJM57dl2BU0t1kqxhpOA5Wcek=",870539353433169424,-337289240572957018,3085936013418841423,4493844119639589421>()) {
                                       case -816914029:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           var13++;
                           switch ((int)com.yiyiaddon.m.b.a<"s2k4k1h43s8aen","x7dJVE83VAz8zU9ctznyFT/gmR7k4DDn5BM6skJrWLg=",7615260936097756823,2267070117524927785,-7955669228033998128,-4667980730585299404>()) {
                              case 1513223748:
                                 continue;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var1.restore();
            float var20 = var2 + this.ac.X() * var4;
            float var21 = var3 + (1.0F - this.ac.Y()) * 92.0F;
            Rect var22 = Rect.makeLTRB(var20 - 5.0F, var21 - 5.0F, var20 + 5.0F, var21 + 5.0F);
            this.j.setStrokeWidth(3.5F);
            this.j.setColor(com.yiyiaddon.l.i.c.a(var8.uY, var5));
            var1.drawRect(var22, this.j);
            this.j.setStrokeWidth(1.8F);
            this.j.setColor(com.yiyiaddon.l.i.c.a(var8.vB, var5));
            var1.drawRect(var22, this.j);
            return;
         default:
            throw null;
      }
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
      return this.c(var1, var2, var3, var4, var5);
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5) {
      return this.c(var1, var2, var3, var4, var5);
   }

   private boolean c(float var1, float var2, float var3, float var4, float var5) {
      float var6 = (var1 - var3) / Math.max(1.0F, var5);
      float var7 = 1.0F - (var2 - var4) / 92.0F;
      this.ac.a(this.ac.W(), var6, var7, false);
      return true;
   }
}
