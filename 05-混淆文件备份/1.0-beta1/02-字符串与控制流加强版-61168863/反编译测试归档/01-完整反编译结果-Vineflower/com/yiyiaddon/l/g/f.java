package com.yiyiaddon.l.g;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ClipMode;
import io.github.humbleui.skija.ColorFilter;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.DirectContext;
import io.github.humbleui.skija.FilterTileMode;
import io.github.humbleui.skija.Image;
import io.github.humbleui.skija.ImageFilter;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.SamplingMode;
import io.github.humbleui.skija.SurfaceOrigin;
import io.github.humbleui.skija.impl.Library;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import java.util.List;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL45;

public final class f {
   private static final f a = new f();
   private static final float ke = 18.0F;
   private static final int tS = 268435456;
   private final Paint o = new Paint().setAntiAlias(true);
   private final Paint p = new Paint().setAntiAlias(true);
   private final Paint q = new Paint().setAntiAlias(true);
   private final Paint r = new Paint().setAntiAlias(true);
   private final g a = new g();
   private ImageFilter a;
   private ImageFilter b;
   private ImageFilter c;
   private float kf = Float.NaN;
   private boolean fQ = false;

   private f() {
   }

   public static f a() {
      return a;
   }

   public static int ea() {
      int[] var0 = new int[1];
      GL45.glGetIntegerv(36006, var0);
      return var0[0];
   }

   public boolean a(Minecraft var1, float var2, float var3, float var4, float var5, float var6, int var7, float var8) {
      if (var1 != null && var1.getWindow() != null && var1.getMainRenderTarget() != null) {
         int var9 = this.c(var1);
         Canvas var10 = this.a.a(var9);
         DirectContext var11 = this.a.b();
         if (var10 != null && var11 != null) {
            try {
               return this.a(var10, var11, var1, var9, var2, var3, var4, var5, var6, var7, var8);
            } finally {
               this.a.km();
            }
         } else {
            this.a.km();
            return false;
         }
      } else {
         return false;
      }
   }

   public Image a(DirectContext var1, float var2, float var3, float var4, float var5) {
      Minecraft var6 = Minecraft.getInstance();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sy0n45l9g39or","rTmrgXpD9D0E9kXNfp/l652jBrEAmWOT3tZJtOjh0u0=",6834571543798068304,8178863870517566665,-6268568660534119120,5049866829791976802>()) {
            case 1969116250:
               if (var6 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s39l5xfgsb22xr","+c9eVgLqyNJlmyAA3g+hicqCOaL7NsnNNKlBaNDWiUw=",750045995522877419,478837229176743558,-6663309243629856143,8131569254344534232>()) {
                     case -394986069:
                        if (var6.getWindow() != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"swhmygzarx1s3","AAV/v0nXLeuQ7u9jex4xEkSoBeUTjy8imMVAG03PjRI=",2631333323270359388,-1231034218392995365,-4561485982416981559,-728001694401139263>()) {
                              case 51830106:
                                 if (var6.getMainRenderTarget() != null) {
                                    this.kl();
                                    float var7 = var6.getWindow().getGuiScale();
                                    f.a var8 = this.a(var1, var6, this.c(var6), var2, var3, var4, var5, var7, 0.0F);
                                    return var8.c;
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s1a1jdgsg557nj","JS56CscshUu7Aihowv9iRWQQ746fsqLg7i60J2IlQE0=",-3031280563014936064,4051104038825017127,2110891781549243981,6767291444056449427>()) {
                                    case -1596797843:
                                       return null;
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
               break;
            default:
               throw null;
         }
      }

      return null;
   }

   public boolean a(Minecraft var1, List<f.c> var2, int var3, float var4) {
      if (var1 != null && var1.getWindow() != null && var1.getMainRenderTarget() != null && var2 != null && !var2.isEmpty()) {
         float var5 = Float.MAX_VALUE;
         float var6 = Float.MAX_VALUE;
         float var7 = -Float.MAX_VALUE;
         float var8 = -Float.MAX_VALUE;

         for (f.c var10 : var2) {
            var5 = Math.min(var5, var10.T());
            var6 = Math.min(var6, var10.U());
            var7 = Math.max(var7, var10.T() + var10.t());
            var8 = Math.max(var8, var10.U() + var10.b());
         }

         int var16 = this.c(var1);
         Canvas var17 = this.a.a(var16);
         DirectContext var11 = this.a.b();
         if (var17 != null && var11 != null) {
            try {
               return this.a(var17, var11, var1, var16, var2, var5, var6, var7 - var5, var8 - var6, var3, var4);
            } finally {
               this.a.km();
            }
         } else {
            this.a.km();
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean a(
      Canvas var1, DirectContext var2, Minecraft var3, int var4, float var5, float var6, float var7, float var8, float var9, int var10, float var11
   ) {
      if (var1 != null && var2 != null && var3 != null && var3.getWindow() != null) {
         this.kl();
         float var12 = var3.getWindow().getGuiScale();
         boolean var13 = var11 > 0.001F;
         float var14 = var13 ? this.r(var11) : 0.0F;
         f.a var15 = this.a(var2, var3, var4, var5, var6, var7, var8, var12, Math.max(18.0F, var14 * 2.0F));
         if (var15.c == null) {
            return false;
         }

         if (var13) {
            this.i(var14);
         }

         var1.save();

         try {
            var1.clipRRect(RRect.makeXYWH(var5, var6, var7, var8, var9), true);
            this.o.setImageFilter(var13 ? this.c : null);
            var1.drawImageRect(
               var15.c, Rect.makeXYWH(0.0F, 0.0F, var15.tT, var15.tU), Rect.makeXYWH(var15.kg, var15.kh, var15.ki, var15.kj), SamplingMode.LINEAR, this.o, true
            );
            this.a(var1, var15, var5, var6, var7, var8, var9);
            this.p.setColor(com.yiyiaddon.l.i.c.a(com.yiyiaddon.l.i.c.a().uN, 0.035F));
            var1.drawRRect(RRect.makeXYWH(var5, var6, var7, var8, var9), this.p);
            this.q.setColor(o(var10));
            var1.drawRRect(RRect.makeXYWH(var5, var6, var7, var8, var9), this.q);
            return true;
         } finally {
            this.o.setImageFilter(null);
            var1.restore();
            var15.c.close();
         }
      } else {
         return false;
      }
   }

   private boolean a(
      Canvas var1, DirectContext var2, Minecraft var3, int var4, List<f.c> var5, float var6, float var7, float var8, float var9, int var10, float var11
   ) {
      this.kl();
      float var12 = var3.getWindow().getGuiScale();
      boolean var13 = var11 > 0.001F;
      float var14 = var13 ? this.r(var11) : 0.0F;
      f.a var15 = this.a(var2, var3, var4, var6, var7, var8, var9, var12, Math.max(18.0F, var14 * 2.0F));
      if (var15.c == null) {
         return false;
      }

      if (var13) {
         this.i(var14);
      }

      var1.save();

      try {
         this.o.setImageFilter(var13 ? this.c : null);
         this.p.setColor(com.yiyiaddon.l.i.c.a(com.yiyiaddon.l.i.c.a().uN, 0.035F));
         this.q.setColor(o(var10));
         Rect var16 = Rect.makeXYWH(0.0F, 0.0F, var15.tT, var15.tU);
         Rect var17 = Rect.makeXYWH(var15.kg, var15.kh, var15.ki, var15.kj);

         for (f.c var19 : var5) {
            RRect var20 = RRect.makeXYWH(var19.T(), var19.U(), var19.t(), var19.b(), var19.V());
            var1.save();
            var1.clipRRect(var20, true);
            var1.drawImageRect(var15.c, var16, var17, SamplingMode.LINEAR, this.o, true);
            this.a(var1, var15, var19.T(), var19.U(), var19.t(), var19.b(), var19.V());
            var1.drawRRect(var20, this.p);
            var1.drawRRect(var20, this.q);
            var1.restore();
         }

         return true;
      } finally {
         this.o.setImageFilter(null);
         var1.restore();
         var15.c.close();
      }
   }

   private static int o(int var0) {
      return com.yiyiaddon.l.i.c.a(com.yiyiaddon.l.i.c.a().uN, (var0 >>> 24) / 255.0F * 0.45F);
   }

   private void a(Canvas var1, f.a var2, float var3, float var4, float var5, float var6, float var7) {
      float var8 = Math.min(9.0F, Math.min(var5, var6) * 0.08F);
      if (!(var8 < 1.0F)) {
         Rect var9 = Rect.makeXYWH(0.0F, 0.0F, var2.tT, var2.tU);

         for (int var10 = 0; var10 < 6; var10++) {
            float var11 = var8 * var10 / 6.0F;
            float var12 = var8 * (var10 + 1) / 6.0F;
            float var13 = 1.0F - var10 / 6.0F;
            float var14 = 1.0F + 0.035F * var13 * var13;
            float var15 = var3 + var5 * 0.5F;
            float var16 = var4 + var6 * 0.5F;
            var1.save();

            try {
               var1.clipRRect(RRect.makeXYWH(var3 + var11, var4 + var11, var5 - 2.0F * var11, var6 - 2.0F * var11, Math.max(0.0F, var7 - var11)), true);
               var1.clipRRect(
                  RRect.makeXYWH(var3 + var12, var4 + var12, var5 - 2.0F * var12, var6 - 2.0F * var12, Math.max(0.0F, var7 - var12)), ClipMode.DIFFERENCE, true
               );
               this.r.setColor(com.yiyiaddon.l.i.c.a(com.yiyiaddon.l.i.c.a().uX, 0.58F * var13));
               var1.drawImageRect(
                  var2.c,
                  var9,
                  Rect.makeXYWH(var15 + (var2.kg - var15) * var14, var16 + (var2.kh - var16) * var14, var2.ki * var14, var2.kj * var14),
                  SamplingMode.LINEAR,
                  this.r,
                  true
               );
            } finally {
               var1.restore();
            }
         }
      }
   }

   private f.a a(DirectContext var1, Minecraft var2, int var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      int var10 = var2.getWindow().getWidth();
      int var11 = var2.getWindow().getHeight();
      int var12 = Math.max(0, (int)Math.floor((var4 - var9) * var8));
      int var13 = Math.max(0, (int)Math.floor((var5 - var9) * var8));
      int var14 = Math.min(var10, (int)Math.ceil((var4 + var6 + var9) * var8));
      int var15 = Math.min(var11, (int)Math.ceil((var5 + var7 + var9) * var8));
      int var16 = Math.max(1, var14 - var12);
      int var17 = Math.max(1, var15 - var13);
      int var18 = Math.max(0, var11 - var15);
      int[] var19 = new int[1];
      int[] var20 = new int[1];
      int[] var21 = new int[1];
      int[] var22 = new int[1];
      int[] var23 = new int[1];
      int[] var24 = new int[1];
      int[] var25 = new int[1];
      int[] var26 = new int[4];
      int[] var27 = new int[4];
      boolean var28 = GL45.glIsEnabled(36281);
      GL45.glGetIntegerv(34016, var20);
      GL45.glActiveTexture(33984);
      GL45.glGetIntegerv(32873, var19);
      GL45.glGetIntegerv(35097, var21);
      GL45.glGetIntegerv(36010, var22);
      GL45.glGetIntegerv(36006, var23);
      GL45.glGetIntegerv(3074, var24);
      GL45.glGetIntegerv(3073, var25);
      GL45.glGetIntegerv(2978, var26);
      GL45.glGetIntegerv(3088, var27);
      f.b var29 = null;
      boolean var30 = false;

      try {
         var29 = this.a(var1, var16, var17);
         if (var29 == null) {
            return f.a.a;
         }

         GL45.glDisable(36281);
         GL45.glBindTexture(3553, var29.tV);
         GL45.glBindSampler(0, 0);
         GL45.glBindFramebuffer(36009, var29.tW);
         GL45.glDrawBuffer(36064);
         if (GL45.glCheckFramebufferStatus(36009) != 36053) {
            return f.a.a;
         }

         int var31 = this.p(var3);
         if (var31 == 0) {
            return f.a.a;
         }

         GL45.glReadBuffer(var31);
         GL45.glBlitFramebuffer(var12, var18, var12 + var16, var18 + var17, 0, 0, var16, var17, 16384, 9728);
         GL45.glFlush();
         GL45.glDeleteFramebuffers(var29.tW);
         var30 = true;
         return new f.a(var29.d, var16, var17, var12 / var8, var13 / var8, var16 / var8, var17 / var8);
      } finally {
         if (var29 != null && !var30) {
            GL45.glDeleteFramebuffers(var29.tW);
            var29.d.close();
         }

         GL45.glBindFramebuffer(36008, var22[0]);
         GL45.glBindFramebuffer(36009, var23[0]);
         this.d(var22[0], var24[0]);
         this.e(var23[0], var25[0]);
         GL45.glViewport(var26[0], var26[1], var26[2], var26[3]);
         GL45.glScissor(var27[0], var27[1], var27[2], var27[3]);
         GL45.glActiveTexture(33984);
         GL45.glBindTexture(3553, var19[0]);
         GL45.glBindSampler(0, var21[0]);
         GL45.glActiveTexture(var20[0]);
         if (var28) {
            GL45.glEnable(36281);
         } else {
            GL45.glDisable(36281);
         }
      }
   }

   private f.b a(DirectContext var1, int var2, int var3) {
      int var4 = GL45.glGenTextures();
      int var5 = GL45.glGenFramebuffers();
      Image var6 = null;
      Object var7 = null;

      try {
         GL45.glBindTexture(3553, var4);
         GL45.glTexParameteri(3553, 10241, 9729);
         GL45.glTexParameteri(3553, 10240, 9729);
         GL45.glTexParameteri(3553, 10242, 33071);
         GL45.glTexParameteri(3553, 10243, 33071);
         GL45.glTexImage2D(3553, 0, 32856, var2, var3, 0, 6408, 5121, 0L);
         GL45.glBindFramebuffer(36009, var5);
         GL45.glFramebufferTexture2D(36009, 36064, 3553, var4, 0);
         GL45.glDrawBuffer(36064);
         if (GL45.glCheckFramebufferStatus(36009) != 36053) {
            return null;
         }

         var6 = Image.adoptGLTextureFrom(var1, var4, 3553, var2, var3, 32856, SurfaceOrigin.BOTTOM_LEFT, ColorType.RGB_888X);
         return new f.b(var4, var5, var2, var3, var6);
      } finally {
         if (var7 == null) {
            if (var5 != 0) {
               GL45.glDeleteFramebuffers(var5);
            }

            if (var6 != null) {
               var6.close();
            } else if (var4 != 0) {
               GL45.glDeleteTextures(var4);
            }
         }
      }
   }

   private void i(float var1) {
      if (this.c != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3v9cozq66kyqi","mu4aTRj5XDB1NCSol3sWRktZpbTyvLR2qTfc5ZJQadE=",4494211397707649172,6114793586182848968,-5307595703483052521,7860800822239704551>()) {
            case 591562484:
               if (Math.abs(this.kf - var1) < 0.001F) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2e0fmtzs1ne28","xh9ecFR68c7cNIxcYNKZkCbpAd7JtKaLStrOsVCngqg=",-1460407839252245689,-3701633094924249887,-6480285243706688979,2745754683669365418>()) {
                     case 614794384:
                        return;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      this.kk();
      this.a = ImageFilter.makeColorFilter(ColorFilter.getSRGBToLinearGamma(), null);
      this.b = ImageFilter.makeBlur(var1, var1, FilterTileMode.CLAMP, this.a, (Rect)null);
      this.c = ImageFilter.makeColorFilter(ColorFilter.getLinearToSRGBGamma(), this.b);
      this.kf = var1;
   }

   private void kk() {
      if (this.c != null) {
         label33:
         switch ((int)com.yiyiaddon.m.b.a<"s2luwtkwfe7z4q","5AGd7iRLiGd66i8VEFU7Rm9qa2KRSicgB1ZWzvfHSAk=",-3092120110824520794,-8380977362635286705,6929041496929206949,-7701938837887744869>()) {
            case 502125280:
               this.c.close();
               switch ((int)com.yiyiaddon.m.b.a<"s2c7kxl63q1uix","NXql6njiFHKt4E5yC6a3gJhoG5/b5i/qB0bMnrvSCuo=",8992138670136459225,-966944765365586663,-3477829615734372809,4079321232549878890>()) {
                  case 1379388227:
                     break label33;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (this.b != null) {
         label28:
         switch ((int)com.yiyiaddon.m.b.a<"s2e06lxth0wos3","3CkNaUkf4YdEUFdBd7Hi2+waQFn1GF8BB8u5Agep9Sk=",3605934228692333657,-2882637511655093550,4517566463731395932,7884429607996771638>()) {
            case -1238077938:
               this.b.close();
               switch ((int)com.yiyiaddon.m.b.a<"s1y7pkf2bn762i","S0h4F1ovWjtkfSZNIvk+EfW710QmmUKK4N4uoIYXhiw=",5680628628353808379,8713914021428872194,-4410474478689036013,3934235632122048754>()) {
                  case 114644406:
                     break label28;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (this.a != null) {
         label23:
         switch ((int)com.yiyiaddon.m.b.a<"s19c81bxj6ux60","TEU5qTytEglebrPZCyj8StQV+P/1mOBvcP6ApKIZL/A=",-8677816978838880704,-4562878631924347781,7508237208957809015,-7914447420124629468>()) {
            case -1084271497:
               this.a.close();
               switch ((int)com.yiyiaddon.m.b.a<"s34dj5l2f1nz75","I/iDdPECSHjKSOQOkfmYTwWVCNejcquHeOjvecllUW8=",7334159692985786199,-1088799433232063646,4022025508790563616,-541009173942035919>()) {
                  case -441199077:
                     break label23;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.c = null;
      this.b = null;
      this.a = null;
      this.kf = Float.NaN;
   }

   private int p(int var1) {
      GL45.glBindFramebuffer(36008, var1);
      if (GL45.glCheckFramebufferStatus(36008) != 36053) {
         switch ((int)com.yiyiaddon.m.b.a<"s27w2dybkfuh9r","w3jt5nhCVTMdOmYfo7+K1VqMUQMGOF/ZBZcKCNUQ1rc=",-7951441215757696129,-2416036096989089268,2690387646391951352,2657863505122259730>()) {
            case -312646955:
               return 0;
            default:
               throw null;
         }
      } else if (var1 == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1u206cqklntwj","ctNtfHaaTvCyMCnFcrsQqnbn+xD0FDMFQ222EKzWi48=",4651560630026617530,-1124315579911968220,3081683141830443139,5221597790380082548>()) {
            case -1044393833:
               return 1029;
            default:
               throw null;
         }
      } else {
         int var2 = GL45.glGetFramebufferAttachmentParameteri(36008, 36064, 36048);
         if (var2 == 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s1rvgbemzb4byb","MkAdFxaeuJ51sl9Qoh//Ko8vkRoMRTrOGVN9C87rES0=",4119833958138961924,-8970102002142416448,-9099159158881253320,8214883663830407532>()) {
               case -271082936:
                  switch ((int)com.yiyiaddon.m.b.a<"s25e9oscjb6g7y","Eh/IQTOkq1AaCvLBkzxcuxpqT0U9kpYU0nMyPS0HYV8=",-1243537932576475848,3637933906060389605,1442686782372775643,1569502176623570756>()) {
                     case -781041464:
                        return 0;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s3qhicsgusuhbi","B8a0ksjxB3Nb60vyaX5gp1H5o0WrHU5KFqNe3eCFgnE=",-2760492994264795803,1543435287500371742,-263833265505256387,1754123846032466849>()) {
               case 414548528:
                  return 36064;
               default:
                  throw null;
            }
         }
      }
   }

   private void d(int var1, int var2) {
      if (var2 == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s2lmocm2pcioz","JoBi0GpNm3OHyKFTDdyi27odSPNJH6ek2nAg8Kkzrnk=",2281909277630947028,-1459429519769282841,-2076776326518810460,-2670840091754955302>()) {
            case -1321590763:
               GL45.glReadBuffer(0);
               return;
            default:
               throw null;
         }
      } else if (var1 == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1zz6yfu26yw48","as1gsmg7bsFzHyXvyzbapOLFXuprTj2ZNQn/nYZmhLc=",-6969126369694574106,2700282857059916299,6286845876597795230,3801465758814791080>()) {
            case -1780377743:
               int var3;
               if (this.y(var2)) {
                  label31:
                  switch ((int)com.yiyiaddon.m.b.a<"s3porxlu6yqasr","dTTnaA7y2j+rizfiYMjMllF8EskQbh+3FJiDosGh/ac=",-137339415637711926,-3674546612190307008,8253083379662862667,7805368640292716365>()) {
                     case -386819193:
                        var3 = var2;
                        switch ((int)com.yiyiaddon.m.b.a<"sl1jtkvqokcv1","oR4w2u8KiK7Zl2ChY13yuokU5l+2aZp/EbVbeMQMswo=",8280935052892719627,-2857638775238274979,6935898923441924140,-8378090970918438789>()) {
                           case -1025126816:
                              break label31;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var3 = 1029;
                  switch ((int)com.yiyiaddon.m.b.a<"s204o7xkygpefd","A7tYljaAnNns8lan018R9Tm9i3Yhm2qGG/w6eSGrJdk=",1113587598109474330,-4973811644674044012,-7055880612373855404,8986330640867613366>()) {
                     case 757300611:
                        break;
                     default:
                        throw null;
                  }
               }

               GL45.glReadBuffer(var3);
               return;
            default:
               throw null;
         }
      } else {
         int var10000;
         if (this.z(var2)) {
            label39:
            switch ((int)com.yiyiaddon.m.b.a<"s2buddd892gmsu","bmPMgO0VdQjfjI3bZqlvrDRdx5IE+YJYc9VBkB5axUs=",-4131894827158246065,5863775032815726846,8708353517783973277,-7111608738174923238>()) {
               case -1164534169:
                  var10000 = var2;
                  switch ((int)com.yiyiaddon.m.b.a<"s2ljvas3asmfnc","4cfLw3PtCkDpQKmC6nJjapljTuJCDq+6RFBZZlJ4aBo=",2351989329429337714,-5981340924288114119,-142183086836200730,1536667155600177212>()) {
                     case 111642498:
                        break label39;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = 36064;
            switch ((int)com.yiyiaddon.m.b.a<"s2nvp1s1caqfvs","nzcr91/D7vHbfMHdV+jclPSQIlDb3gdLTYiyoXfk9jg=",-381013314871806040,8793695168212176295,3801277762951126577,-2365987614128025779>()) {
               case -1803870873:
                  break;
               default:
                  throw null;
            }
         }

         GL45.glReadBuffer(var10000);
      }
   }

   private void e(int var1, int var2) {
      if (var2 == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1tizbm587kr6q","VMkJor5WMlu+g2YTGOVJYsdaZJY7Eg9l+hnsOs3HOTQ=",-7706351886135400742,583377154399277471,-6325618914207263703,-8215683436013645613>()) {
            case 306837940:
               GL45.glDrawBuffer(0);
               return;
            default:
               throw null;
         }
      } else if (var1 == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"soifo7ymyhz5k","pNan2WN0FnGhmW8J/G2F3MUo/nhEog1ZiCJmokqGYcg=",-7778141832564633031,7026001978361815087,2775701635536172734,4929694610570866043>()) {
            case 1926584469:
               int var3;
               if (this.y(var2)) {
                  label31:
                  switch ((int)com.yiyiaddon.m.b.a<"s193u8wj6s1qkq","eCVMmtu0qEmmUJlFpx9g7gkfGJZ9vUIAWZu0dtnFJws=",2062992531159356273,2316238237643882488,-4820618672279715446,-3428016912669918140>()) {
                     case -1067404350:
                        var3 = var2;
                        switch ((int)com.yiyiaddon.m.b.a<"s14hgbg2f4ipxp","gxe+hdZMMJO0Yn5NqkHELndeISVbd0rxXGj0blwq0e8=",2575003794184366804,-1123680590975323187,-4341845962256685610,8308594201727167300>()) {
                           case -1377602623:
                              break label31;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var3 = 1029;
                  switch ((int)com.yiyiaddon.m.b.a<"s2jlqlmrpxlf4b","ORr/aOFD/wGGGKyggY3Vh/NkgfPTr9iYrlp9iz+41dU=",-320505310852895603,2346993057488978094,-4252579350836275283,2867805245462197162>()) {
                     case 433106294:
                        break;
                     default:
                        throw null;
                  }
               }

               GL45.glDrawBuffer(var3);
               return;
            default:
               throw null;
         }
      } else {
         int var10000;
         if (this.z(var2)) {
            label39:
            switch ((int)com.yiyiaddon.m.b.a<"s2xsdz4dbpcnsn","EZGGeO0P+4RODmOTTVYa3ACJ3rvuA2M9C9mmy20dKoU=",7187721834540376048,-5274505851602626265,8111625907682424632,4593559770169096713>()) {
               case -790792924:
                  var10000 = var2;
                  switch ((int)com.yiyiaddon.m.b.a<"s3qzmf1jkrpv3e","IZLNq8cV0x1aeScr97njT4ODiCA820EOr5V6IG3+c48=",4217488080628400203,-7912307696251500540,6098937117479663353,-9009435359445864342>()) {
                     case -198997964:
                        break label39;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = 36064;
            switch ((int)com.yiyiaddon.m.b.a<"s3vkqqbn7h28ur","1o1p5fhyW/XnAKd3ljmSKj7RUb9fWfNI0jOduU8TIfU=",1134350268555978142,-426393880917091614,-3482933079683098729,-7850860690986663171>()) {
               case 1677468416:
                  break;
               default:
                  throw null;
            }
         }

         GL45.glDrawBuffer(var10000);
      }
   }

   private boolean y(int var1) {
      if (var1 != 1028) {
         switch ((int)com.yiyiaddon.m.b.a<"s6pruusslcuyt","a1aRxAtkvHzaRmz+8ZZd3rUqbqyyJx5s6tF5FSAeBf0=",2079334771387890127,-5554954770190551895,-5944382774869406285,-7371858590039043718>()) {
            case 747832246:
               if (var1 != 1029) {
                  switch ((int)com.yiyiaddon.m.b.a<"safj31mvdw1zl","ucW2ovKqRAPf3f8wOxj9sOsfmhGE/Ht/gbTDNtIn/bQ=",6734617239508653783,-4311591863440503103,921473727350689605,1426215820444241896>()) {
                     case -351923333:
                        if (var1 != 1030) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3iobxdoqzcc4w","OWG5tEUyGs2Lt265U3O1StgAWGovlKzht6d1/f6Q280=",-7447164854325616656,-7285233212252603795,-2414926205149933388,-1838928888384928510>()) {
                              case 1711014049:
                                 if (var1 != 1031) {
                                    switch ((int)com.yiyiaddon.m.b.a<"spaxuu75pz5wa","PKReyKu6xa9GW3R6CtWh3+wFaRADkSR7i1jK41YkdYM=",-9097596378004567538,3874916321169846645,-8341164239291720447,5623933390536212587>()) {
                                       case 1442919694:
                                          if (var1 != 1024) {
                                             switch ((int)com.yiyiaddon.m.b.a<"smuqkhzwbodhp","iqrGA0q0BwEtrgwoYJV+xGPYcFWGzZsqvI/Gwkwxe2s=",-8149338862023756394,-778835789404168817,8147475860016725263,6657460480067539002>()) {
                                                case 394383724:
                                                   if (var1 != 1025) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s12b2vxk45e117","4yKThkIdZrNM4m0oGprgW+jh9j0rNp47jVVKIFaXnP8=",4384422271634368675,1720957595890055952,-947985994770938186,-7901549804611529293>()) {
                                                         case 1167279880:
                                                            if (var1 != 1026) {
                                                               label40:
                                                               switch ((int)com.yiyiaddon.m.b.a<"s3t7a4jyjc7ssf","1oUoXkXtqzcZEO4mPX+vkI5zuO6HqX4NbFdYWEV4mkI=",-8781636104404515460,-664229640590137934,-2445102775906928130,-3566875892443547720>()) {
                                                                  case -1936791921:
                                                                     if (var1 != 1027) {
                                                                        switch ((int)com.yiyiaddon.m.b.a<"sxrilc4yqgfhb","Yhm7bfNbku0WEZo5Sgc6yUrpbLplQz9fJAr/I1jmn1I=",-7064022093185117772,-3339841930835080672,1884810018029987374,7610830478549069918>()) {
                                                                           case 819232852:
                                                                              return false;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     }

                                                                     switch ((int)com.yiyiaddon.m.b.a<"s3qxuij4kpvnb0","NInyflyq3lM0p56az94JtnCR6BXgAk3HRPVm4zx5AdQ=",-7434530236489382099,8039225942907524864,8146187429939704974,-5426936020296290587>()) {
                                                                        case 193687360:
                                                                           break label40;
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
                                                   break;
                                                default:
                                                   throw null;
                                             }
                                          }
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }
                                 break;
                              default:
                                 throw null;
                           }
                        }
                        break;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s1j5owroj2hd25","HAymaVZ0ysESeAbbZzz6CLDpTBFNa6kQ9pbusPZkd/g=",-3682561967583354967,239310992930145515,-2493983167370445624,253250170845742141>()) {
         case 1065949803:
            return true;
         default:
            throw null;
      }
   }

   private boolean z(int var1) {
      if (var1 >= 36064) {
         switch ((int)com.yiyiaddon.m.b.a<"s6e2mrbjaa4ey","+ljpEKnxAlYGjDHlmTu8CtyZ4E98Brj8tzJv/PFTyJ0=",4221692111423068251,-6267244494365095800,-9155233569706605323,6868299309333806034>()) {
            case 1183320437:
               if (var1 <= 36095) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3pcpp1lq25we3","K9u4DR+kshVxFdF1YGknLt5SQUF6Ap3qGUwtV8KJwOM=",-7167706587776787942,-4649845976295119943,5791233366512033506,2035227821472240400>()) {
                     case 707525200:
                        switch ((int)com.yiyiaddon.m.b.a<"s2trvj9yy1bgkc","yo/gzpZR3m9gXMvS7amShzCU6sqbePLrnwHkQ1dBcik=",6695681182828651875,7735291953758377986,449825488590333630,-7235060455180675098>()) {
                           case 877851890:
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

      switch ((int)com.yiyiaddon.m.b.a<"sq26oex0777n3","e5dHW+G/sFNhJwE/fc+tJPqIhdE802bTcosPImYrSYw=",-6102462798111783804,-1971087244944496472,5986416376113887135,-6464675383155831907>()) {
         case -1056353974:
            return false;
         default:
            throw null;
      }
   }

   private int c(Minecraft var1) {
      return g.ef();
   }

   private float r(float var1) {
      float var2 = Math.max(0.0F, Math.min(2.0F, var1));
      return var2 * 10.5F;
   }

   private void kl() {
      if (this.fQ) {
         switch ((int)com.yiyiaddon.m.b.a<"s1pkbbffpnyw71","zdxNNRK1A47jpjweUUdR7NwntPdB4JpEgfp4XNSMM5g=",-6916231435569108406,5867618992652639817,-4988034158930625904,-1183591058707085910>()) {
            case 1324758711:
               return;
            default:
               throw null;
         }
      } else {
         Library.load();
         this.fQ = true;
      }
   }

   private static class a {
      private static final f.a a = new f.a(null, 0, 0, 0.0F, 0.0F, 0.0F, 0.0F);
      private final Image c;
      private final int tT;
      private final int tU;
      private final float kg;
      private final float kh;
      private final float ki;
      private final float kj;

      private a(Image var1, int var2, int var3, float var4, float var5, float var6, float var7) {
         this.c = var1;
         this.tT = var2;
         this.tU = var3;
         this.kg = var4;
         this.kh = var5;
         this.ki = var6;
         this.kj = var7;
      }
   }

   private record b(int tV, int tW, int tX, int tY, Image d) {
      public int eb() {
         return this.tV;
      }

      public int ec() {
         return this.tW;
      }

      public int ed() {
         return this.tX;
      }

      public int ee() {
         return this.tY;
      }

      public Image a() {
         return this.d;
      }
   }

   public record c(float kk, float kl, float km, float kn, float ko) {
      public float T() {
         return this.kk;
      }

      public float U() {
         return this.kl;
      }

      public float t() {
         return this.km;
      }

      public float b() {
         return this.kn;
      }

      public float V() {
         return this.ko;
      }
   }
}
