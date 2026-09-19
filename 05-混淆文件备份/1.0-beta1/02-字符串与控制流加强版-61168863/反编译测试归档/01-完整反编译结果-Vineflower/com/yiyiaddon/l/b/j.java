package com.yiyiaddon.l.b;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.skija.Shader;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class j {
   private static final Paint d = new Paint().setAntiAlias(true);
   private static final Paint e = new Paint().setAntiAlias(true).setMode(PaintMode.STROKE);
   private static final int tf = 10;
   private static final float fJ = 10.0F;
   private static final float fK = 0.35F;
   private static final float fL = 0.15F;
   private static final float fM = 0.025F;
   private static final int tg = 8;
   private static final int th = 256;
   private static final Map<Long, Shader> bb = new LinkedHashMap<Long, Shader>(16, 0.75F, true) {
      @Override
      protected boolean removeEldestEntry(Entry<Long, Shader> var1) {
         if (this.size() <= 256) {
            switch ((int)com.yiyiaddon.m.b.a<"s12j70pzdhzy7m","KuZO23yJgMo4vp+AgnuS731RGVzyf6CEwA+ePKPNoLQ=",2979712561744809013,5894895748690913658,-8565038142724069589,-7340508132757158411>()) {
               case 1318576948:
                  return false;
               default:
                  throw null;
            }
         } else {
            ((Shader)var1.getValue()).close();
            return true;
         }
      }
   };
   private static final Map<Long, Shader> bc = new LinkedHashMap<Long, Shader>(16, 0.75F, true) {
      @Override
      protected boolean removeEldestEntry(Entry<Long, Shader> var1) {
         if (this.size() <= 256) {
            switch ((int)com.yiyiaddon.m.b.a<"s2lc0ndj8gyvtp","gOWBxVoG2NgLAD+VuIKR3pNKi1vdJiXGn7m4C9W+9y8=",-9172408346280893335,5080350328393252819,-3556304243112831737,9143901899569079258>()) {
               case 525482882:
                  return false;
               default:
                  throw null;
            }
         } else {
            ((Shader)var1.getValue()).close();
            return true;
         }
      }
   };
   private static final float fN = 0.55F;
   private static final float fO = 0.15F;

   private j() {
   }

   public static void a(Canvas var0, float var1, float var2, float var3, float var4, float var5, int var6, float var7, float var8) {
      if (!(var8 <= 0.01F) && !(var3 <= 0.0F) && !(var4 <= 0.0F)) {
         d.setShader(null);
         com.yiyiaddon.l.i.c var9 = com.yiyiaddon.l.i.c.a();
         float var10 = com.yiyiaddon.l.i.c.y(1.0F) < 1.0F ? (var9.gB ? 0.78F : 0.9F) : 1.0F;
         d.setColor(a(var6, var7 * var10 * var8));
         var0.drawRRect(RRect.makeXYWH(var1, var2, var3, var4, var5), d);
         var0.save();

         try {
            var0.translate(var1, var2);
            d.setColor(a(com.yiyiaddon.l.i.c.a().uX, var8));
            d.setShader(a(0.0F, var4, 1.0F));
            var0.drawRRect(RRect.makeXYWH(0.0F, 0.0F, var3, var4, var5), d);
         } finally {
            d.setShader(null);
            var0.restore();
         }

         b(
            var0,
            var1 + 1.0F,
            var2 + 1.5F,
            Math.max(0.0F, var3 - 2.0F),
            Math.max(0.0F, var4 - 3.0F),
            Math.max(0.0F, var5 - 1.0F),
            com.yiyiaddon.l.i.c.a().uY,
            var8 * 0.075F,
            0.75F
         );
      }
   }

   private static Shader a(float var0, float var1, float var2) {
      int var3 = Math.round(Math.max(0.0F, Math.min(1.0F, var2)) * 8.0F);
      com.yiyiaddon.l.i.c var4 = com.yiyiaddon.l.i.c.a();
      long var10000 = (long)Math.round(var1 - var0) << 32 | var4.uS & 16777215L;
      long var10001;
      if (var4.gB) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s1n2iybd05o8m4","rqciX41SFP/AvFa1oh6ho7lxHvVO2NCzNMv2qRKpytk=",-2647256748220448798,-6724455065704109416,-5026643382921467108,5675759462575283233>()) {
            case -1699940336:
               var10001 = 16777216L;
               switch ((int)com.yiyiaddon.m.b.a<"s1b3st61m0uu96","OHwT/hJk6KD2aQhg8Ui8H0HHRsaXZqM7/CbnJTNPC+s=",2116480950975982663,6605994493421971360,6539154460467046157,3999358919327499969>()) {
                  case -1697449206:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = 0L;
         switch ((int)com.yiyiaddon.m.b.a<"s218esopzfu67h","6k26lATOc0Us5LiaDrah7o6CNkf9h923jQYEeGUcol4=",-5157358950140445633,-3078912579790053120,-5485746074816810152,-279441844297304475>()) {
            case -1529975547:
               break;
            default:
               throw null;
         }
      }

      long var5 = var10000 | var10001;
      return bb.computeIfAbsent(
         var5,
         var4x -> {
            float var5x = var3 / 8.0F;
            int[] var10004 = new int[3];
            int var10007 = var4.uX;
            float var10008;
            if (var4.gB) {
               label29:
               switch ((int)com.yiyiaddon.m.b.a<"s1s7fjq5usu3l0","QeHirKI8Qp0IrTBlmMRcQOxrxcfiCvtgY16utJ0jri8=",-4652382479413476484,8578448853634350250,-4231141900255564597,-1281758922001477999>()) {
                  case 1334894490:
                     var10008 = 0.12F;
                     switch ((int)com.yiyiaddon.m.b.a<"s15htxr3mmkun9","ICQp25xZ3+slZ5XLnmsrUd48W8aMvRDWMly8NG5xo6k=",-2627791134841579700,-810620717739029380,-673859421881100647,1731168826089562870>()) {
                        case 373579938:
                           break label29;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10008 = 0.28F;
               switch ((int)com.yiyiaddon.m.b.a<"s3ms07jwc5whah","ahqvwTfqTioN0d8j6OeYWDuJxoVBysDQaUZOZjLc0TY=",-4934604450612395944,-5117964752905705215,8202505600853896577,6009391270333300711>()) {
                  case 1176751726:
                     break;
                  default:
                     throw null;
               }
            }

            var10004[0] = a(var10007, var10008 * var5x);
            var10004[1] = a(var4.uX, 0.012F * var5x);
            var10007 = a(var4.uX, var4.uS, 0.24F);
            if (var4.gB) {
               label22:
               switch ((int)com.yiyiaddon.m.b.a<"s2zwrilwzd3xkb","d7i8sDyQKi2FskY+oye5/ZOpDmptBpdu54nKDo646rU=",5643619487824118004,-6188818722348166561,-2459801706341307928,-6878446354080437187>()) {
                  case 2041049576:
                     var10008 = 0.055F;
                     switch ((int)com.yiyiaddon.m.b.a<"sobfnky446w2m","ckxo/UUjOU/e+yTf/IxLQtSUeBkjYuOsEiu7JuNAYhM=",-4161488135284275813,443515704989055455,6047083235546007575,-1777404763883479866>()) {
                        case 1261374355:
                           break label22;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10008 = 0.12F;
               switch ((int)com.yiyiaddon.m.b.a<"s1wp46nva5bh4l","Zkirnvu2imOr87PDd1wQotPFFMf0uO1UlOfEcE1TP1k=",4108706040399429541,4227272832119941879,-3989137178422471029,-6206848148854192146>()) {
                  case -375263724:
                     break;
                  default:
                     throw null;
               }
            }

            var10004[2] = a(var10007, var10008 * var5x);
            return Shader.makeLinearGradient(0.0F, var0, 0.0F, var1, var10004);
         }
      );
   }

   public static void a(Canvas var0, float var1, float var2, float var3, float var4, float var5, int var6, float var7) {
      d.setColor(a(var6, var7));
      var0.drawRRect(RRect.makeXYWH(var1, var2, var3, var4, var5), d);
   }

   public static void b(Canvas var0, float var1, float var2, float var3, float var4, float var5, int var6, float var7, float var8) {
      e.setStrokeWidth(var8);
      e.setColor(a(var6, var7));
      var0.drawRRect(RRect.makeXYWH(var1, var2, var3, var4, var5), e);
   }

   public static void c(Canvas var0, float var1, float var2, float var3, float var4, float var5, int var6, float var7, float var8) {
      if (!(var8 <= 0.001F)) {
         if (!(var3 <= 2.0F) && !(var4 <= 2.0F)) {
            b(var0, var1 + 0.5F, var2 + 0.5F, var3 - 1.0F, var4 - 1.0F, Math.max(0.0F, var5 - 0.5F), var6, var7 * var8 * 0.45F, 1.0F);
            var0.save();

            try {
               var0.translate(var1, var2);
               e.setColor(a(var6, var7 * Math.min(1.0F, var8 * 2.1F)));
               e.setShader(a(var3, var4));
               e.setStrokeWidth(1.0F);
               var0.drawRRect(RRect.makeXYWH(0.5F, 0.5F, var3 - 1.0F, var4 - 1.0F, Math.max(0.0F, var5 - 0.5F)), e);
            } finally {
               e.setShader(null);
               var0.restore();
            }
         }
      }
   }

   private static Shader a(float var0, float var1) {
      com.yiyiaddon.l.i.c var2 = com.yiyiaddon.l.i.c.a();
      long var3 = Long.MIN_VALUE | (long)Math.round(var0) << 40 | (long)Math.round(var1) << 24 | var2.uS;
      return bb.computeIfAbsent(
         var3, var3x -> Shader.makeLinearGradient(0.0F, 0.0F, var0, var1, new int[]{a(var2.uX, 0.95F), a(var2.uX, 0.08F), a(a(var2.uX, var2.uS, 0.2F), 0.62F)})
      );
   }

   public static void a(Canvas var0, float var1, float var2, float var3, float var4, com.yiyiaddon.l.i.c var5, float var6, float var7) {
      if (!(var6 <= 0.01F) && !(var7 <= 0.001F) && !(var3 <= 0.0F) && !(var4 <= 0.0F)) {
         var0.save();

         try {
            var0.translate(var1, var2);
            d.setColor(a(var5.uX, var6 * var7));
            d.setShader(a(1, var3, var4, var5));
            var0.drawRect(Rect.makeXYWH(0.0F, 0.0F, var3, var4), d);
            d.setShader(a(2, var3, var4, var5));
            var0.drawRect(Rect.makeXYWH(0.0F, 0.0F, var3, var4), d);
         } finally {
            d.setShader(null);
            var0.restore();
         }
      }
   }

   public static void a(Canvas var0, float var1, float var2, float var3, float var4, float var5, com.yiyiaddon.l.i.c var6, float var7) {
      if (!(var7 <= 0.01F) && !(var3 <= 0.0F) && !(var4 <= 0.0F)) {
         d(var0, var1, var2, var3, var4, var5, var6.uS, var7, 0.32F);
         var0.save();

         try {
            var0.translate(var1, var2);
            d.setColor(a(var6.uX, var7));
            d.setShader(a(3, var3, var4, var6));
            var0.drawRRect(RRect.makeXYWH(0.0F, 0.0F, var3, var4, var5), d);
         } finally {
            d.setShader(null);
            var0.restore();
         }

         c(var0, var1, var2, var3, var4, var5, var6.uX, var7, var6.gB ? 0.3F : 0.48F);
      }
   }

   public static void a(Canvas var0, float var1, float var2, float var3, float var4, float var5, com.yiyiaddon.l.i.c var6, float var7, float var8) {
      if (!(var8 <= 0.01F)) {
         switch ((int)com.yiyiaddon.m.b.a<"s9s5y16cnotz7","WMLm3kkkl8BxgCri0TVzTLQx7RvhvOlTFym6bd3XoQY=",-5271820951422563245,-4260982047172069103,6699036509464369991,-974598048900929308>()) {
            case -448027618:
               if (!(var3 <= 0.0F)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s4703hqz8ra3z","fwyuLLK1l43fckdhYFnaSzcY/xdaAU1YEa/iYfJvpAU=",3010544317513774974,-6031193010019960609,-6227991535238203258,2688042358625525511>()) {
                     case -1254954679:
                        if (!(var4 <= 0.0F)) {
                           a(var0, var1, var2, var3, var4, var5, var6.uQ, 0.55F + 0.15F * var7, com.yiyiaddon.l.i.c.y(var8));
                           c(var0, var1, var2, var3, var4, var5, var6.uX, var8, 0.1F);
                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"sac8mdqccrhmi","9A1COg3rr/5yiLlM7dnpAOK/tYKUa754f9xzzGrj+yg=",2094122944513447598,-2626822384342612505,491290574042597592,-6184064806817972910>()) {
                           case -1341595796:
                              return;
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
   }

   public static void b(Canvas var0, float var1, float var2, float var3, float var4, float var5, com.yiyiaddon.l.i.c var6, float var7, float var8) {
      if (!(var8 <= 0.01F) && !(var7 <= 0.01F)) {
         var0.save();

         try {
            var0.clipRRect(RRect.makeXYWH(var1, var2, var3, var4, var5), true);
            var0.translate(var1, var2);
            d.setColor(a(var6.uX, var7 * var8));
            d.setShader(a(4, var3, var4, var6));
            var0.drawRect(Rect.makeXYWH(0.0F, 0.0F, var3, var4), d);
         } finally {
            d.setShader(null);
            var0.restore();
         }
      }
   }

   private static Shader a(int var0, float var1, float var2, com.yiyiaddon.l.i.c var3) {
      int var4 = Math.round(var1);
      int var5 = Math.round(var2);
      long var6 = 1469598103934665603L;
      var6 = (var6 ^ var0) * 1099511628211L;
      var6 = (var6 ^ var4) * 1099511628211L;
      var6 = (var6 ^ var5) * 1099511628211L;
      var6 = (var6 ^ var3.uS) * 1099511628211L;
      byte var10001;
      if (var3.gB) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s3bsbt5v7npzq7","lHcVsqKWH7Th+/9iwHHTI5rh3x8/fn8u1/GepqTjZkk=",3197519198374056622,-8227179101825611014,-8374239190529125903,-4279119611426940335>()) {
            case 371563924:
               var10001 = 1;
               switch ((int)com.yiyiaddon.m.b.a<"s1p3srdvkyelqx","nzfLrzp4XFlp4wu9+N1n1d1GlgL0qkEt8D14JQbp9QQ=",6935311257838628709,4737359106403603919,4173745215869704999,-3146832648499243424>()) {
                  case -2070424221:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s2w102ar5iu3ai","qgvonF5odOiN/0KLZmcj2Hyx8YY9TAM+9YcLQkxAtBk=",-5159089332851497884,-3536405896018763218,4665968928573670833,-2849529566258455454>()) {
            case 392504195:
               break;
            default:
               throw null;
         }
      }

      var6 = (var6 ^ var10001) * 1099511628211L;
      return bc.computeIfAbsent(
         var6,
         var4x -> {
            switch (var0) {
               case 1:
                  float var7 = var1 * 0.08F;
                  float var8x = var2 * 0.02F;
                  float var10x = Math.max(var1, var2) * 0.72F;
                  int[] var11x = new int[3];
                  int var13 = a(var3.uX, var3.uS, 0.3F);
                  float var18;
                  if (var3.gB) {
                     label79:
                     switch ((int)com.yiyiaddon.m.b.a<"s2bls3og4ryuns","rr+gIdNcAoATVPzbbcCgMG/7q4VAdNhW5M+NSiKTO2Q=",2501032001730314041,9066269941738666281,4319209566836434769,-388496112909038191>()) {
                        case -1140496420:
                           var18 = 0.24F;
                           switch ((int)com.yiyiaddon.m.b.a<"s212m9mnyilmos","jj4XqeTORgp62r6t8HWG+5GZ4WGrP0KOneTmcXK+4+M=",2705103294575238741,-5427253264541838841,-4303034038007743740,-2216843158365070839>()) {
                              case -1222595581:
                                 break label79;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var18 = 0.18F;
                     switch ((int)com.yiyiaddon.m.b.a<"s15m14ro6ev4zo","EdRslfjCx9x+p8A3u7qPhpMP+WI2XmfO6vhecb7ojDk=",-2672672384916274813,-4979477585442546596,-3064016517374774986,4104862137045357909>()) {
                        case -154985547:
                           break;
                        default:
                           throw null;
                     }
                  }

                  var11x[0] = a(var13, var18);
                  var13 = var3.uS;
                  if (var3.gB) {
                     label70:
                     switch ((int)com.yiyiaddon.m.b.a<"s1kc2foh35ec33","agGU1qt7BeFKpfVqKzfb+vpRoHRK97b0I1wTMDc/QoU=",-2464570812374869267,2635851683143662899,-4144080175029877334,1769829957620126142>()) {
                        case -796028392:
                           var18 = 0.075F;
                           switch ((int)com.yiyiaddon.m.b.a<"s1eyrfqup6w63n","uoGWb8e9fx+G6O+CZ3IDk72AMMuwrCLc3V/tmm0C2jg=",-3093751681198376445,-1475882777961993518,1984904682897752257,6109494149609787968>()) {
                              case -2118360512:
                                 break label70;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var18 = 0.045F;
                     switch ((int)com.yiyiaddon.m.b.a<"s2r3ks83dshu91","Kb/XF+X4jXmlIdw10u/RD7bFzqCKzglRR85Z+VmFC+I=",4304749868037358851,-2596186366893033269,4270474488520000251,-2767617770554686081>()) {
                        case -1947538785:
                           break;
                        default:
                           throw null;
                     }
                  }

                  var11x[1] = a(var13, var18);
                  var11x[2] = a(var3.uS, 0.0F);
                  Shader var25 = Shader.makeRadialGradient(var7, var8x, var10x, var11x, new float[]{0.0F, 0.42F, 1.0F});
                  switch ((int)com.yiyiaddon.m.b.a<"s1xqq9hv4phxq4","M2+Nzbj0O3Dn2z/l9zjvbzP19ReZ4VeA7hfFXwWqW5g=",418389368880104583,1831928322726891866,-4139015952559774109,4342438408213262588>()) {
                     case -606471260:
                        return var25;
                     default:
                        throw null;
                  }
               case 2:
                  float var6x = var1 * 0.96F;
                  float var10001x = var2 * 0.98F;
                  float var9x = Math.max(var1, var2) * 0.62F;
                  int[] var10003 = new int[3];
                  int var10006 = var3.uS;
                  float var17;
                  if (var3.gB) {
                     label63:
                     switch ((int)com.yiyiaddon.m.b.a<"s20o14h6pz298k","6zpT3PvMLZGeEYNk3UuEFPUT66GqJCfZA7sFh8o8n0Y=",-2315864633773665019,3226678198143639580,194248416545211797,-464670674003962499>()) {
                        case -755078166:
                           var17 = 0.14F;
                           switch ((int)com.yiyiaddon.m.b.a<"s2g2st5i8qj97t","ES4hGEvj9yrWR3kZfpQWhEyRh2Kng2wDgN/trkvjuwM=",5484555051422003017,4446756814125451229,2379611178170999361,-3260690336520795681>()) {
                              case -1436439711:
                                 break label63;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var17 = 0.08F;
                     switch ((int)com.yiyiaddon.m.b.a<"s2bpuvdxuxmkvb","tQFEqySoU7ekNbOZnBri9/LIKQdlVbVtHmfx5Hk2HMQ=",3305823319627970785,220411728830109119,3566795801700432283,3712360409412466202>()) {
                        case -1915597181:
                           break;
                        default:
                           throw null;
                     }
                  }

                  var10003[0] = a(var10006, var17);
                  var10003[1] = a(var3.uS, 0.025F);
                  var10003[2] = a(var3.uS, 0.0F);
                  Shader var24 = Shader.makeRadialGradient(var6x, var10001x, var9x, var10003, new float[]{0.0F, 0.48F, 1.0F});
                  switch ((int)com.yiyiaddon.m.b.a<"s150ga2u5yjwef","rL6ADM906mEGIDUylj1vnV0pmDBSctDg5x4d9h1vMpI=",7608673258111807633,5601188547023184914,-7024198302300237530,-5283840444162679979>()) {
                     case 1238701037:
                        return var24;
                     default:
                        throw null;
                  }
               case 3:
                  int[] var12x = new int[3];
                  int var15 = var3.uS;
                  int var20 = var3.uX;
                  float var10009;
                  if (var3.gB) {
                     label95:
                     switch ((int)com.yiyiaddon.m.b.a<"s1cztl28o5soq","71Th4eatnLPZCLk+tscyIGJkKJkb/d9s4U7BP5pHufU=",-464727116950541700,-2544559511502517210,-3335926283330618092,5782745390209559892>()) {
                        case -191296169:
                           var10009 = 0.32F;
                           switch ((int)com.yiyiaddon.m.b.a<"s32kd41lbgvf4e","kxHIJfkhlgOUIUp+ZZVEkiL21LXBLm1lPVzrD9M821w=",-2265102127665307816,218294467252717731,2076426521952138839,6747174202857925681>()) {
                              case -667438755:
                                 break label95;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10009 = 0.16F;
                     switch ((int)com.yiyiaddon.m.b.a<"skj54ars73jav","y7JDxHzawRpDTmyHOIypLWwD61XCfpTUWANZZeu9M1s=",7489342155338977512,1268510500958110376,198748976382924917,-4670075500361972451>()) {
                        case -1508702863:
                           break;
                        default:
                           throw null;
                     }
                  }

                  var12x[0] = a(a(var15, var20, var10009), 1.0F);
                  var12x[1] = a(var3.uS, 1.0F);
                  var15 = var3.uS;
                  var20 = var3.uY;
                  if (var3.gB) {
                     label86:
                     switch ((int)com.yiyiaddon.m.b.a<"syt05szmjcamx","aEpaiyn02ej7zwFQaj3EH7HF+nb/rqxMASvb61c3JYg=",-4491420824295720123,5992003834384128637,2454906879077077567,-1727741024282560796>()) {
                        case 695891368:
                           var10009 = 0.1F;
                           switch ((int)com.yiyiaddon.m.b.a<"s1guzry64vr3id","OEnxJLkAJymzN153gS0YNWRPpRGCJ0q4Wzq4W+lPn6M=",665184055217302341,5011897082182817461,4834744736588831848,-467572068476749801>()) {
                              case -768614737:
                                 break label86;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10009 = 0.04F;
                     switch ((int)com.yiyiaddon.m.b.a<"s1he9tptxrod59","rjiNCP2dW3tQQ7h0ZNSWb9yNHZqOrTI+yCthaSnwVbg=",5186604395293196590,969044699750042470,-5558284048362817428,3983713131937110393>()) {
                        case 1885231575:
                           break;
                        default:
                           throw null;
                     }
                  }

                  var12x[2] = a(a(var15, var20, var10009), 1.0F);
                  Shader var23 = Shader.makeLinearGradient(0.0F, 0.0F, var1, var2, var12x);
                  switch ((int)com.yiyiaddon.m.b.a<"s2n3oiptuslp7x","BoOdU0jSKK8sRRIhMyfaMUh1OMOMwQ/IzFl1XAIr96g=",4248547621143186738,7833919399971391175,3894304625179314043,-2966662833671356338>()) {
                     case -662766488:
                        return var23;
                     default:
                        throw null;
                  }
               default:
                  float var10000 = -var1 * 0.15F;
                  float var10002 = var1 * 0.75F;
                  int[] var10004 = new int[]{a(var3.uX, 0.0F), 0, 0, 0};
                  int var10007 = var3.uX;
                  float var10008;
                  if (var3.gB) {
                     label102:
                     switch ((int)com.yiyiaddon.m.b.a<"s1uew47kwu9l11","nqboOaDC3U5GuU17Qvl7LVfV2uQBwH3mb6fNQmEwZVs=",5001473432324343462,-8769059803814896815,6567298287555661246,1061878273256539478>()) {
                        case -1685951039:
                           var10008 = 0.12F;
                           switch ((int)com.yiyiaddon.m.b.a<"s2fkdlrckvy9rl","u32/t8mYAkHb7Me05xiAOsGAzLO69r+lyEo1Te3JTBo=",-4303443129277876095,4058267920380827473,5094282710909021495,-884839105227466213>()) {
                              case 1185115211:
                                 break label102;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10008 = 0.2F;
                     switch ((int)com.yiyiaddon.m.b.a<"s3d6ac8coyf1mu","vOBZOd7veci9rYfEqObZRNBGTvTmZ8XAT7xnyU72Yeo=",3527609678270371757,-6307985241061905010,-1450159745999120118,-7487381094611771173>()) {
                        case -260465860:
                           break;
                        default:
                           throw null;
                     }
                  }

                  var10004[1] = a(var10007, var10008);
                  var10004[2] = a(a(var3.uX, var3.uS, 0.25F), 0.035F);
                  var10004[3] = a(var3.uX, 0.0F);
                  Shader var5x = Shader.makeLinearGradient(var10000, var2, var10002, 0.0F, var10004, new float[]{0.0F, 0.42F, 0.58F, 1.0F});
                  switch ((int)com.yiyiaddon.m.b.a<"s29yzhsll7z1d7","D5t0SgZFMBoif8iOr+RFl2O4sX0esrL/EpoND2K26vg=",-1211581982276445955,-8693449205488931627,-8383303586587259055,-2451219846609591637>()) {
                     case 1646422046:
                        return var5x;
                     default:
                        throw null;
                  }
            }
         }
      );
   }

   public static void b(Canvas var0, float var1, float var2, float var3, float var4, float var5, int var6, float var7) {
      if (var7 <= 0.01F) {
         switch ((int)com.yiyiaddon.m.b.a<"sb41v9rxq9d94","83QITVhNsIttaX1f/uaZwYwasLKt0/kKvlrdIMpPSp4=",-3592129116790845990,9170779255099880034,-8847761898913862933,1264009694022187397>()) {
            case -2075029929:
               return;
            default:
               throw null;
         }
      } else {
         float var8 = 1.5F;
         float var9 = var8 * 0.5F;
         b(var0, var1 + var9, var2 + var9, var3 - var8, var4 - var8, Math.max(0.0F, var5 - var9), var6, var7, var8);
      }
   }

   public static void d(Canvas var0, float var1, float var2, float var3, float var4, float var5, int var6, float var7, float var8) {
      if (!(var8 <= 0.001F)) {
         switch ((int)com.yiyiaddon.m.b.a<"s33o012e630yow","uBacZRsw4VoE0ozcwNsexlMrTTuKTa+fJ3qQZaZUVDk=",5292718701439056070,-359133651000643392,7354957067593269461,3311462956802858723>()) {
            case 1156521113:
               if (!(var7 <= 0.01F)) {
                  float var9 = 1.0F;
                  float var10 = var7 * var8 * 0.35F;
                  int var11 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s3qzftqfhjkuea","7IZYQJi+V491mSp5Zo7lxU3Ap2hS076WBkuvEdoAk9k=",2684317476563961891,264900697372306787,2636595431166446797,6598364375927212262>()) {
                     case -705043625:
                        while (var11 < 10) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1jh4uxmr61cno","AerOcPjkkaNg9gBQPgG4k0kKfCbFxAg+9eGmmBQHL6Q=",8478927977955975733,9087483059693202558,-2413753680805584808,8049655549775989338>()) {
                              case -833671898:
                                 float var12 = (var11 + 0.5F) / 10.0F;
                                 float var13 = 10.0F * (1.0F - var12);
                                 b(
                                    var0,
                                    var1 - var13,
                                    var2 - var13 + var13 * 0.35F,
                                    var3 + var13 * 2.0F,
                                    var4 + var13 * 2.0F,
                                    var5 + var13,
                                    var6,
                                    var10 * var12 * var12,
                                    var9
                                 );
                                 var11++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s2r1s2gnae2q46","tMq5QoVhbXufqoaNjXT1SDyjpnzx+H5kmzdrSanpGxc=",-352036469821383146,7689848264089869420,-2037958564116881156,-1344356827485567069>()) {
                                    case -861022387:
                                       continue;
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
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"siraalflik7bq","CfBntkxSxGv56ws0xOmY63NSk69bHZxXRKZuJTKnakE=",-4814453407371878608,-1807874449592607347,-6371101342159973823,-6776150738655710072>()) {
                     case 494781864:
                        return;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }
   }

   public static void a(Canvas var0, float var1, float var2, float var3, int var4, float var5) {
      d.setColor(a(var4, var5));
      var0.drawRect(Rect.makeXYWH(var1, var2, var3, 1.0F), d);
   }

   public static float h(float var0) {
      return Math.min(com.yiyiaddon.l.i.d.a().a().an(), Math.max(0.0F, var0) * 0.3F);
   }

   public static int a(int var0, float var1) {
      int var2 = Math.round(Math.max(0.0F, Math.min(1.0F, var1)) * 255.0F);
      return var2 << 24 | var0 & 16777215;
   }

   public static int a(int var0, int var1, float var2) {
      float var3 = Math.max(0.0F, Math.min(1.0F, var2));
      int var4 = var0 >> 16 & 0xFF;
      int var5 = var0 >> 8 & 0xFF;
      int var6 = var0 & 0xFF;
      int var7 = var1 >> 16 & 0xFF;
      int var8 = var1 >> 8 & 0xFF;
      int var9 = var1 & 0xFF;
      int var10 = Math.round(var4 + (var7 - var4) * var3);
      int var11 = Math.round(var5 + (var8 - var5) * var3);
      int var12 = Math.round(var6 + (var9 - var6) * var3);
      return var10 << 16 | var11 << 8 | var12;
   }
}
