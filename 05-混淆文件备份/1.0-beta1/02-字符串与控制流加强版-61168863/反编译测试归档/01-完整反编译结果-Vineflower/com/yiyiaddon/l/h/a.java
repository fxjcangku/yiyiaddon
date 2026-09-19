package com.yiyiaddon.l.h;

import com.yiyiaddon.l.b.q;
import com.yiyiaddon.l.b.s;
import com.yiyiaddon.l.f.k;
import com.yiyiaddon.l.g.i;
import com.yiyiaddon.l.g.j;
import com.yiyiaddon.l.j.h;
import com.yiyiaddon.l.j.m;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.PreeditEvent;
import net.minecraft.network.chat.Component;

public class a extends i {
   private static final String[] an = new String[]{
      (String)com.yiyiaddon.m.b.a<"s20px3u2zft6n1","n9PiNB+egWSVZwmQES0Uw/iMZq/OnlO4Rm+fLAE6",-6775120240611301637,-5761349416332464357,-7866666378971650445,-7465398683457343619>(),
      (String)com.yiyiaddon.m.b.a<"s12ddc6ulnse3","CNpxIwkG0DJsdlBB1VfXU2VRwfS/lPHbUvKTSF3W",-4282299676624533933,-6544111950255487942,6863018865103418421,553967812470833295>(),
      (String)com.yiyiaddon.m.b.a<"s39a16slzx10iz","dHHdzKvmDef7QLjJTu88aQuwMaCg3VoIumiY4tJx",-687064803228918397,-3726128779931427019,6239065306658258189,-2746868037378982431>(),
      (String)com.yiyiaddon.m.b.a<"secw46t1rs9ai","6gAtrA+4vsdUOmkdTHZV3UgUP0vW5aXfOSMmAE9F",5832020577974034979,-8925603143627138597,-7280776703370167878,-3473930596234268881>()
   };
   private static final String[] ao = new String[]{
      (String)com.yiyiaddon.m.b.a<"s1fqroqropansu","xcvbk+g9zJqvyWkwfQZBuxSAol7b/r2zjGeSKvYXc0Q=",-5135066987042351872,2692200680762310055,-2728338312429419566,-5938915447570030009>(),
      (String)com.yiyiaddon.m.b.a<"s136pi4crckpv","MMSoDNaqkUD2ZhRRgYgx/DCayvP/hU7DPiRlwl9Vzb4=",-3319887167784272745,-7707177137526752044,1576069103349186936,-8673297149248032125>(),
      (String)com.yiyiaddon.m.b.a<"s3emr117495e5b","/uYu6ayW7GmlxN3HLbAPqWzSnLX9Vfw3tK39xq1cZ2k=",4589272187737753736,3311662862282704936,-7476179880512889890,-188208604841443972>(),
      (String)com.yiyiaddon.m.b.a<"stuz182g51zxf","r77rw/Sh+IoLGVi8YYDWM6CERGlle9mav+PDrg7zC8Y=",5851510645323155431,3342277218779114801,4330160532141863410,2118779908312090619>()
   };
   private static final String[] ap = new String[]{
      (String)com.yiyiaddon.m.b.a<"s338w2lfbr06r3","3Ryw7zqptMcv0MW+/WvjgTaLp70r0QStQUWgTJ1+lqwThUx4",5334469426678420943,4964863553241368291,-4051856059763960270,-3933578329603289261>(),
      (String)com.yiyiaddon.m.b.a<"s35xjrbwx80g17","jztaTi3LqX6/T3o+wDdNcyxr7K63/TmMM/uXDt/ekAQRTx9mpNNwA7tT",-4670330669517294472,-8799571938810654923,369148105165534877,-70258386382600617>(),
      (String)com.yiyiaddon.m.b.a<"s1z6ycpv876smj","BYkvCJvkcfaDI8CFdqRzF7v71hXzzU8pzNFbdM20Zj3d+Xjb+ua0gcG9ffW/aQ==",-3792894078969389271,-5480582465972757821,-2972524538270492508,6074113916810137388>(),
      (String)com.yiyiaddon.m.b.a<"sw6uffzb4lc0i","0R2MzaVn0ltoEvA5ab8PFkqCSpS/Z/LZw2TQdpuK4aKo12tfFUbxjbqwLcg=",2245067855782981375,952818264806025442,-7997879202339979220,3712525267241917614>()
   };
   private static final float kX = 10.0F;
   private static final float kY = 190.0F;
   private static final float kZ = 18.0F;
   private static final float la = 16.0F;
   private static final float lb = 22.0F;
   private static final float lc = 39.0F;
   private static final float ld = 56.0F;
   private static final float le = 11.0F;
   private static final float lf = 28.0F;
   private static final float lg = 16.0F;
   private static final float lh = 46.0F;
   private static final float li = 12.0F;
   private static final float lj = 14.0F;
   private static final float lk = 34.0F;
   private static final float ll = 8.0F;
   private static final float lm = 50.0F;
   private static final float ln = 8.0F;
   private static final float lo = 96.0F;
   private static final float lp = 24.0F;
   private static final float lq = 42.0F;
   private static final int uD = 4;
   private final com.yiyiaddon.l.e.a c;
   private final q b = new q();
   private final s a = new s();
   private int uE = -1;
   private boolean gq = false;
   private boolean gr = false;
   private boolean gs = false;
   private boolean gt = false;
   private boolean gu = false;
   private String Gm = (String)com.yiyiaddon.m.b.a<"s1oyt8vr0ckzpp","ZvUgWwIhuWHFvEXIeSIS950fTIclNDurDQ9hqA==",4074830886275593428,5729826184208369832,2124713026265430011,-7328367791606069362>();
   private com.yiyiaddon.l.f.a a;
   private com.yiyiaddon.l.f.a b;
   private com.yiyiaddon.l.f.a c;
   private float lr = 1.0F;
   private float ls;
   private float lt;
   private boolean gv = false;
   private boolean gw = false;
   private final float[] e = new float[ao.length];
   private float lu = 0.0F;
   private float lv = 0.0F;
   private float lw = -1.0F;
   private float lx = 0.0F;
   private float ly = 0.0F;
   private float lz = 0.0F;
   private float lA;
   private float lB;
   private float lC;
   private float lD;
   private long bi = 0L;
   private final com.yiyiaddon.l.a.d c = com.yiyiaddon.l.a.d.a(0.22F);
   private final com.yiyiaddon.l.a.b[] b = new com.yiyiaddon.l.a.b[ao.length];
   private final com.yiyiaddon.l.a.b d = new com.yiyiaddon.l.a.b();
   private final com.yiyiaddon.l.a.b e = new com.yiyiaddon.l.a.b();
   private final com.yiyiaddon.l.b.a a = new com.yiyiaddon.l.b.a();
   private final Paint v;
   private final Paint w;
   private final Paint x;
   private final Paint y;
   private final Paint z;
   private final Paint A;
   private final com.yiyiaddon.l.g.g c;
   private final float lE;
   private String Gn;
   private float lF;
   private String Go;
   private float lG;
   private boolean gx;
   private int uF;
   private final List<com.yiyiaddon.l.i.b> dy;

   public a(Screen var1) {
      super(
         Component.literal(
            (String)com.yiyiaddon.m.b.a<"s2173r2stq9z01","gAq+eU0NIcgip3e0NCGWbMCdlc5vMO4tdq68CzsjhZuHR4d6cHBOpZ580M5yJg==",-6198504729546351384,1011601868808825999,-7526285478132796395,-5892521096101287275>()
         ),
         var1
      );

      for (int var2 = 0; var2 < this.b.length; var2++) {
         this.b[var2] = new com.yiyiaddon.l.a.b();
      }

      this.v = new Paint().setAntiAlias(true);
      this.w = new Paint().setAntiAlias(true);
      this.x = new Paint().setAntiAlias(true);
      this.y = new Paint().setAntiAlias(true);
      this.z = new Paint().setAntiAlias(true);
      this.A = new Paint().setAntiAlias(true).setMode(PaintMode.STROKE).setStrokeWidth(1.2F);
      this.c = new com.yiyiaddon.l.g.g();
      this.lE = com.yiyiaddon.l.g.a.a(
         (String)com.yiyiaddon.m.b.a<"s3erl9fz1gt196","nVgndF1G/sOC3FzcHsR/nRevvMv5jjIywI+av7dG",-957609925468177580,-3747562947190975950,-1626252229010341362,-4142314549238669858>(),
         13.0F,
         (String)com.yiyiaddon.m.b.a<"s1kris6ixzujs6","kdlc/ByTP67SDUMHjkoh3j1WoSA/X/QNoiXLimUh7QMZEiQ380gFKrLZ7iujy9TjfbERmty2geqPI0Gv",-7622355196040374750,-3365830134689732819,7129985490982912013,3689873943726774676>()
      );
      this.Gn = (String)com.yiyiaddon.m.b.a<"s1oyt8vr0ckzpp","ZvUgWwIhuWHFvEXIeSIS950fTIclNDurDQ9hqA==",4074830886275593428,5729826184208369832,2124713026265430011,-7328367791606069362>();
      this.lF = 0.0F;
      this.Go = (String)com.yiyiaddon.m.b.a<"s1oyt8vr0ckzpp","ZvUgWwIhuWHFvEXIeSIS950fTIclNDurDQ9hqA==",4074830886275593428,5729826184208369832,2124713026265430011,-7328367791606069362>();
      this.lG = 0.0F;
      this.gx = false;
      this.uF = -1;
      this.dy = new ArrayList<>(com.yiyiaddon.l.i.d.a());
      this.c = new com.yiyiaddon.l.e.a();

      for (com.yiyiaddon.l.f.a var3 : this.bL()) {
         this.c.a(var3);
      }

      this.kA();
   }

   private List<com.yiyiaddon.l.f.a> bL() {
      return List.of(new com.yiyiaddon.l.f.e(this.c, this::c), new com.yiyiaddon.l.f.g(this.c, this::c), new com.yiyiaddon.l.f.f(), new k(this.c));
   }

   private void kA() {
      this.c.N(com.yiyiaddon.l.e.b.dX());
      Iterator var1 = com.yiyiaddon.l.e.b.bI().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3ke7zp0z0r20n","0iMfSeVatmSRgJ20BcH4tRm5jJ6S0CXUtsXD7JJWZk4=",-2979046945121451031,-3909776926485058098,-1074936926303200714,-1439535950147143883>()) {
         case 596955107:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2kklg7ffh8pom","a7KgMA8CeVh5ncNIvakvhxRMTiJ/Gy5eiuGcC7fC9EU=",4967333765116622645,-8559937453820987147,-3839927062844457357,-7182854204896708613>()) {
                  case 407224670:
                     String var2 = (String)var1.next();
                     com.yiyiaddon.l.f.a var3 = this.a(var2);
                     if (var3 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2ki6o2qe0si8o","hjE9pcv7C8J7uysLmJMdE0IF72VjPiV4SYoO26JR22k=",-7830164938369385087,6505746763525692700,-3516013002892778850,-4155773211385990162>()) {
                           case -447041448:
                              switch ((int)com.yiyiaddon.m.b.a<"s1ia7gyscdtqcv","5It+f6bk88DdnqXEJ9zCJUj2mwXx68hsdb9Ucr2O/vU=",241031122898252848,-9191388229701945353,1694567284057756023,-9003599762763309144>()) {
                                 case -1062623963:
                                    return;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     this.c.a(var3, var2);
                     switch ((int)com.yiyiaddon.m.b.a<"s37cmyal4amjb4","4dZ9dC7beXvuGoRnnmerYtbyn2w6BNoE2C28vjgRe9A=",8348651377415125533,-6501444375590555092,-2962976373073163969,-5091583459049160350>()) {
                        case -665765835:
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
   }

   private com.yiyiaddon.l.f.a a(String var1) {
      String var2 = com.yiyiaddon.l.e.b.y(
         var1,
         (String)com.yiyiaddon.m.b.a<"s2c4zs61iaap7w","ziVeM2bl4Jr9cEEV+tN4BfY7qqpriCN3yCJFHp5RoVtrIr/E",-7417689086030123062,1981660083889282523,3958208838625940242,-1151692211937730656>()
      );
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1h8gfssj8u706","DtUoeQWCClJ+T481wOzIpn5BZO7hJ6daOfuHFYiq+bs=",8348040792040030811,-1857450785144589056,8056281345891693681,5390823250089781799>()) {
            case -900028075:
               com.yiyiaddon.h.c var3 = com.yiyiaddon.h.b.a(var2);
               if (var3 != null) {
                  label26:
                  switch ((int)com.yiyiaddon.m.b.a<"s1vdgbjvdyxnwo","eQ107ka9dYDOn2SJasrxXeygmPtDLnBQz5jzCeHb4vo=",-7381410475440321211,3356147556582853161,-8951327288718216853,-5082318330966480285>()) {
                     case 27510106:
                        if (var3.b() != null) {
                           com.yiyiaddon.l.f.a var10000 = var3.b().get();
                           switch ((int)com.yiyiaddon.m.b.a<"s24kbpn1lsttwb","1cATpcAXT7DTfMlRelncnClf0zQicG+3fzFVXDQrNJE=",-7961225272093703748,-4782123190667109748,6372390325218081807,7469004930830861505>()) {
                              case 781442508:
                                 return var10000;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2n505dowc0vzn","VuIE0Gh8nCd911dQkfD/Lps1t8LHemOx8W/UASZt2Ck=",-1574759100031319343,8271362541983196651,-8985753258670585411,-4677538853353244352>()) {
                           case 1336756145:
                              break label26;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s2hnqfntugg3hm","6NjXeY+Z9S/pVYPcxb/BLSrV41W3Op/HZ4aoNh/+9M0=",2558335717710540397,-2407635513153208675,-6178285774646094808,-535820017403878073>()) {
                  case 722351106:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private void kB() {
      com.yiyiaddon.l.e.b.a(this.c.cr(), this.c.bI());
   }

   private void c(com.yiyiaddon.h.d var1) {
      if (this.minecraft == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2swbbqlcbw8e0","txJky99ARgmSTqoyfjQxBdBKYriqoUe9+kHmbXV8C+M=",-3623877318637785206,-3748001030091255974,830217125392930456,2553079510736247470>()) {
            case -1880470022:
               return;
            default:
               throw null;
         }
      } else {
         this.gv = false;
         this.gw = false;
         m.lb();
         this.minecraft.setScreen(new e(var1, this));
      }
   }

   public void kC() {
      List var1 = this.bL();
      this.c.a(this.c.cr(), (com.yiyiaddon.l.f.a)var1.get(this.c.cr()));
      this.c.f();
      this.kF();
   }

   private float[] a() {
      float var1 = this.b.x();
      float var2 = this.b.y();
      float var3 = this.b.v();
      float var4 = this.b.w();
      float var5 = 190.0F;
      float var6 = var2 + 16.0F + 22.0F;
      float var7 = var2 + 16.0F + 39.0F;
      float var8 = var2 + 16.0F + 56.0F;
      float var9 = var1 + 18.0F;
      float var10 = var8 + 11.0F;
      float var11 = var5 - 36.0F;
      float var12 = var10 + 28.0F + 16.0F;
      float var13 = 38.0F;
      float var14 = 2.0F;
      float var15 = var5 - 24.0F;
      float var16 = 34.0F;
      float var17 = 34.0F;
      float var18 = var2 + var4 - 48.0F;
      float var19 = var18 - var17 - 8.0F;
      float var20 = var1 + 12.0F;
      float var21 = var1 + var5 + 1.0F;
      float var22 = var3 - var5 - 1.0F;
      float var23 = var2 + 16.0F;
      float var24 = var4 - 16.0F - 12.0F;
      return new float[]{
         var1,
         var2,
         var3,
         var4,
         var5,
         var12,
         var13,
         var14,
         var15,
         var20,
         var18,
         var16,
         var19,
         var17,
         var21,
         var23,
         var22,
         var24,
         var9,
         var10,
         var11,
         28.0F,
         var6,
         var7,
         var8
      };
   }

   @Override
   protected void init() {
      super.init();
      this.b.a(this.minecraft, 0.0F);
   }

   @Override
   protected void a(int var1, int var2, int var3, int var4, float var5) {
      if (this.minecraft != null) {
         Canvas var6 = this.c.a(com.yiyiaddon.l.g.g.ef());
         if (var6 != null) {
            try {
               this.a(var6, var1, var2, var3, var4);
            } finally {
               this.c.km();
            }
         }
      }
   }

   private static float e(float var0, float var1, float var2) {
      return var0 + (var1 - var0) * Math.min(var2, 1.0F);
   }

   private static int a(int var0, float var1) {
      return (int)(var1 * 255.0F) << 24 | var0 & 16777215;
   }

   private static int b(int var0, int var1, float var2) {
      var2 = Math.max(0.0F, Math.min(1.0F, var2));
      int var3 = var0 >> 16 & 0xFF;
      int var4 = var0 >> 8 & 0xFF;
      int var5 = var0 & 0xFF;
      int var6 = var1 >> 16 & 0xFF;
      int var7 = var1 >> 8 & 0xFF;
      int var8 = var1 & 0xFF;
      return (int)(var3 + (var6 - var3) * var2) << 16 | (int)(var4 + (var7 - var4) * var2) << 8 | (int)(var5 + (var8 - var5) * var2);
   }

   private float f(float var1, float var2, float var3) {
      float var4 = Math.abs(this.lw + var2 / 2.0F - (var1 + var2 / 2.0F));
      return Math.max(0.0F, Math.min(1.0F, 1.0F - var4 / (var2 + var3)));
   }

   private void kD() {
      this.a.e(0.0F);
   }

   private void kE() {
      com.yiyiaddon.l.f.a var1 = this.b();
      float var2 = this.a()[17];
      this.a.e(var1.D() + var1.E() + 12.0F, var2 - 46.0F);
   }

   @Override
   public void removed() {
      m.lb();
      com.yiyiaddon.l.g.b.f();
      this.kB();
      this.gv = false;
      this.gw = false;
      this.c.ko();
      com.yiyiaddon.c.a.e();
      super.removed();
   }

   @Override
   protected boolean gi() {
      if (this.b.z() >= 1.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s1duoba9c4nf1j","K5evkOpW32cNmqDI8cyrYk97fwsds+f34cxRnK2213Q=",8904596092793931365,2827656536283386758,-472127977198351763,7237165341839774106>()) {
            case 336683224:
               switch ((int)com.yiyiaddon.m.b.a<"s3t7norydudfc7","37Gqxo968x7pfrH77Ja4ezdXrDNeOXDpGQp4w5KkeO8=",1989235918763432946,-6481341060573034346,-7171669990914226072,2895353288622606623>()) {
                  case 80657251:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2vxogwkd539jo","DF5A0iPxoeMO5syei3uz3MQ+5oCCteu0qcRooeqnCgU=",-6014544235672991294,819889698613861305,-3829543187916120114,4065013867278286679>()) {
            case 1347525405:
               return false;
            default:
               throw null;
         }
      }
   }

   private void a(Canvas var1, int var2, int var3, int var4, int var5) {
      long var6 = System.currentTimeMillis();
      float var8 = this.bi == 0L ? 0.016F : Math.min((float)(var6 - this.bi) / 1000.0F, 0.033F);
      this.bi = var6;
      j.kv();
      if (this.b.a(this.minecraft, var8)) {
         super.ku();
      } else {
         float var9 = this.b.z();
         float var10 = this.b.A();
         float var11 = this.b.a(var4, var2);
         float var12 = this.b.b(var5, var3);
         float[] var13 = this.a();
         com.yiyiaddon.l.f.a var14 = this.b();
         if (this.b != var14) {
            this.c = this.b;
            this.lt = this.ls;
            this.b = var14;
            this.lr = this.c == null ? 1.0F : 0.0F;
         }

         this.lr = Math.min(1.0F, this.lr + Math.max(0.0F, var8) / 0.16F);
         if (this.lr >= 1.0F) {
            this.c = null;
         }

         this.kE();
         this.a.a(var8);
         this.ls = this.a.r();
         float var15 = var13[0];
         float var16 = var13[1];
         float var17 = var13[2];
         float var18 = var13[3];
         float var19 = var13[4];
         float var20 = var13[5];
         float var21 = var13[6];
         float var22 = var13[7];
         float var23 = var13[8];
         float var24 = var13[9];
         float var25 = var13[10];
         float var26 = var13[11];
         float var27 = var13[12];
         float var28 = var13[13];
         float var29 = var13[14];
         float var30 = var13[15];
         float var31 = var13[16];
         float var32 = var13[17];
         float var33 = var13[18];
         float var34 = var13[19];
         float var35 = var13[20];
         float var36 = var13[21];
         float var37 = var13[22];
         float var38 = var13[23];
         float var39 = var13[24];
         this.a(var1, var2, var3, var15, var16, var17, var18, var10);
         com.yiyiaddon.l.i.c var40 = com.yiyiaddon.l.i.c.a();
         com.yiyiaddon.l.b.j.a(var1, 0.0F, 0.0F, var2, var3, 0.0F, var40.uY, var9 * (var40.gB ? 0.16F : 0.1F));
         this.uE = -1;
         this.gq = false;
         this.gr = false;

         for (int var41 = 0; var41 < ao.length; var41++) {
            float var42 = var20 + var41 * (var21 + var22);
            if (var11 >= var15 + 12.0F && var11 <= var15 + 12.0F + var23 && var12 >= var42 && var12 <= var42 + var21) {
               this.uE = var41;
            }
         }

         if (var11 >= var24 && var11 <= var24 + var23 && var12 >= var25 && var12 <= var25 + var26) {
            this.gq = true;
         }

         if (var11 >= var24 && var11 <= var24 + var23 && var12 >= var27 && var12 <= var27 + var28) {
            this.gr = true;
         }

         for (int var69 = 0; var69 < ao.length; var69++) {
            float var71 = var69 == this.uE && var69 != this.c.cr() ? 1.0F : 0.0F;
            this.e[var69] = e(this.e[var69], var71, var8 * 12.0F);
         }

         this.lu = e(this.lu, this.gq ? 1.0F : 0.0F, var8 * 12.0F);
         this.lv = e(this.lv, this.gr ? 1.0F : 0.0F, var8 * 12.0F);
         this.lx = e(this.lx, this.gu ? 1.0F : 0.0F, var8 * 14.0F);
         this.lz += var8;
         float var70 = var20 + this.c.cr() * (var21 + var22);
         if (this.lw < 0.0F) {
            this.lw = var70;
            this.c.f(var70);
         }

         this.c.d(var70);
         this.c.a(var8);
         this.lw = this.c.r();
         var14.a(var8);
         this.a.a(var11, var12, n(var29, var31), x(var30), var8, this.gw());
         float var72 = var9;
         com.yiyiaddon.l.i.c var43 = var40;
         var1.save();
         this.b.a(var1, var2, var3);

         try {
            com.yiyiaddon.l.b.j.d(var1, var15, var16, var17, var18, var10, var43.uY, var72, 1.15F);
            com.yiyiaddon.l.b.j.a(var1, var15, var16, var17, var18, var10, var43.uN, com.yiyiaddon.c.a.d ? 0.62F : 0.94F, var72);
            var1.save();
            var1.clipRRect(RRect.makeXYWH(var15, var16, var17, var18, var10), true);

            try {
               com.yiyiaddon.l.b.j.c(var1, var15, var16, var17, var18, var10, var43.uX, var72, 0.2F);
               com.yiyiaddon.l.g.a.c(
                  var1,
                  (String)com.yiyiaddon.m.b.a<"s2173r2stq9z01","gAq+eU0NIcgip3e0NCGWbMCdlc5vMO4tdq68CzsjhZuHR4d6cHBOpZ580M5yJg==",-6198504729546351384,1011601868808825999,-7526285478132796395,-5892521096101287275>(),
                  var15 + 18.0F,
                  var37,
                  17.0F,
                  a(var43.uT, var72)
               );
               com.yiyiaddon.l.g.a.b(var1, gQ(), var15 + 18.0F, var38, 11.0F, a(var43.uU, var72));
               com.yiyiaddon.l.g.a.b(
                  var1,
                  com.yiyiaddon.l.a.w(
                     (String)com.yiyiaddon.m.b.a<"s1xiz98tkwwwxe","AOlgy21Y9K3zCBtd3WfgdcutFt60OX2L+VuO/zdizUGQZPgyTnGL5DZireonpod7",-105563282201301748,4109407745084464805,1783167891767098028,-2388885571883096013>(),
                     (String)com.yiyiaddon.m.b.a<"s1ru4zb4q7pnua","wu/MinXS/nJLGXpV7cnUitBKxqi62m+xB6HGVTrDbPCexNiTsjuxsQLQYDvBdFJ5TmlEgTyuWFbK2HWTM5tBgqGYBV9dfiZnSKLnaYx0oDdxMFot",2119669530648902777,-3631373713142197625,7744284621486289202,1048119703912587806>()
                  ),
                  var15 + 18.0F,
                  var39,
                  11.0F,
                  a(var43.va, var72)
               );
               this.a(var1, var33, var34, var35, var36, var72, var8, var43);
               com.yiyiaddon.l.b.j.a(var1, var15 + 12.0F, this.lw, var23, var21, 10.0F, var43, var72);

               for (int var44 = 0; var44 < ao.length; var44++) {
                  float var45 = var20 + var44 * (var21 + var22);
                  boolean var46 = var44 == this.c.cr();
                  com.yiyiaddon.l.a.b var47 = this.b[var44];
                  var47.a(var8);
                  boolean var48 = var47.a(var1, var15 + 12.0F + var23 / 2.0F, var45 + var21 / 2.0F);
                  if (!var46 && this.e[var44] > 0.01F) {
                     this.v.setColor(a(var43.vj, com.yiyiaddon.l.i.c.y(var72 * this.e[var44])));
                     var1.drawRRect(RRect.makeXYWH(var15 + 12.0F, var45, var23, var21, 10.0F), this.v);
                  }

                  float var49 = this.f(var45, var21, var22);
                  int var50 = a(b(var43.vg, var43.uZ, var49), var72);
                  int var51 = a(b(var43.vf, var43.uZ, var49), var72);
                  com.yiyiaddon.l.g.a.a(
                     var1,
                     an[var44],
                     var15 + 18.0F,
                     var45 + var21 / 2.0F + 6.0F,
                     13.0F,
                     var50,
                     (String)com.yiyiaddon.m.b.a<"s1kris6ixzujs6","kdlc/ByTP67SDUMHjkoh3j1WoSA/X/QNoiXLimUh7QMZEiQ380gFKrLZ7iujy9TjfbERmty2geqPI0Gv",-7622355196040374750,-3365830134689732819,7129985490982912013,3689873943726774676>()
                  );
                  com.yiyiaddon.l.g.a.b(var1, com.yiyiaddon.l.a.w(ao[var44], ap[var44]), var15 + 38.0F, var45 + var21 / 2.0F + 6.0F, 13.0F, var51);
                  if (var48) {
                     var1.restore();
                  }
               }

               int var73 = b(var43.vk, var43.vs, this.lu);
               int var74 = b(var43.vl, var43.vt, this.lu);
               int var75 = b(var43.vk, var43.vs, this.lv);
               int var76 = b(var43.vl, var43.vt, this.lv);
               this.e.a(var8);
               boolean var77 = this.e.a(var1, var24, var27, var23, var28);
               this.w.setColor(a(var75, com.yiyiaddon.l.i.c.y(var72)));
               var1.drawRRect(RRect.makeXYWH(var24, var27, var23, var28, 10.0F), this.w);
               String var78 = this.gs
                  ? com.yiyiaddon.l.a.w(
                     (String)com.yiyiaddon.m.b.a<"s3s0g0cwdcg22y","VcXvFEkbG7Cqmg4DGzatB18b9xWwry34KSFACfylIEQsFIlCkuGI/vH8",7288483956913955034,1125827308570962019,-4073378504363774143,-7113115510679654738>(),
                     (String)com.yiyiaddon.m.b.a<"s2onm3ym3w6bdv","97t8+SWGQOJP2cFSeDJ/IHYGFzKzH75IkCELxTUA1DNvhiLbLTnyc0Anv7V1LHbL4eUHKVZr2FDlPSpQnYi8fvxkcIgRKycf",-8095734306763129713,-5605967093959381270,-5591507430773819576,7286259448242831736>()
                  )
                  : com.yiyiaddon.l.a.w(
                     (String)com.yiyiaddon.m.b.a<"s361swg2l1ddrs","U6TFgxJlOZmO4PLJSJtHWa+Rwvu0iQmsKJHY3dVMjfw7bcYNvdf0LQ==",-6886444909824922255,8923819635884969600,7150967908902970859,-7517061055101952219>(),
                     (String)com.yiyiaddon.m.b.a<"s2zqa9yibd1c4u","FdH9W6S6Lqrt20jUz764efiHcV2KqNk+XdMswnYahdVXGLWkF2p9qV0Xhm1qRyMwQPQO6y555CI3R84heyU=",1534432685952332038,-1651573680746018826,-5379984619378903030,-822394828989164055>()
                  );
               if (!var78.equals(this.Gn)) {
                  this.Gn = var78;
                  this.lF = com.yiyiaddon.l.g.a.b(var78, 12.0F);
               }

               float var79 = this.lE + 6.0F + this.lF;
               float var80 = var24 + (var23 - var79) / 2.0F;
               com.yiyiaddon.l.g.a.a(
                  var1,
                  (String)com.yiyiaddon.m.b.a<"s3erl9fz1gt196","nVgndF1G/sOC3FzcHsR/nRevvMv5jjIywI+av7dG",-957609925468177580,-3747562947190975950,-1626252229010341362,-4142314549238669858>(),
                  var80,
                  var27 + 22.0F,
                  13.0F,
                  a(var76, var72),
                  (String)com.yiyiaddon.m.b.a<"s1kris6ixzujs6","kdlc/ByTP67SDUMHjkoh3j1WoSA/X/QNoiXLimUh7QMZEiQ380gFKrLZ7iujy9TjfbERmty2geqPI0Gv",-7622355196040374750,-3365830134689732819,7129985490982912013,3689873943726774676>()
               );
               com.yiyiaddon.l.g.a.b(var1, var78, var80 + this.lE + 6.0F, var27 + 22.0F, 12.0F, a(var76, var72));
               if (var77) {
                  var1.restore();
               }

               this.d.a(var8);
               boolean var52 = this.d.a(var1, var24, var25, var23, var26);
               this.x.setColor(a(var73, com.yiyiaddon.l.i.c.y(var72)));
               var1.drawRRect(RRect.makeXYWH(var24, var25, var23, var26, 10.0F), this.x);
               String var53 = com.yiyiaddon.l.a.w(
                  (String)com.yiyiaddon.m.b.a<"sk64giuvvaral","xeLtbxJKWku0WSWUGJCUlH2576FgY3e6EknKakWv1CGIOp/j",-6342456759516102525,6731493582702779662,4590105851105479252,3003533967407505669>(),
                  (String)com.yiyiaddon.m.b.a<"s38b6203bpzkdr","eK7TOymTvWS9aTpWDHagLG9RJUTqRV3w9raDaliJdNwfbzHPyGWqTtVA",5449933169073291732,-8097249910269158828,-8341659959431432602,-3654022782648178651>()
               );
               if (!var53.equals(this.Go)) {
                  this.Go = var53;
                  this.lG = com.yiyiaddon.l.g.a.b(var53, 12.0F);
               }

               com.yiyiaddon.l.g.a.b(var1, var53, var24 + (var23 - this.lG) / 2.0F, var25 + 22.0F, 12.0F, a(var74, var72));
               if (var52) {
                  var1.restore();
               }

               if (this.gx) {
                  com.yiyiaddon.l.g.a.c(
                     var1,
                     com.yiyiaddon.l.a.w(
                        (String)com.yiyiaddon.m.b.a<"s1zmtd574yytkg","Q3LtSErxnGV2DsEb6q0E9h7wMUGLB2+sEQ0u+bkUQEaWIt+l",-7298197265595444079,-8748959033596061417,9163504645365513691,-7446142386237342181>(),
                        (String)com.yiyiaddon.m.b.a<"skt49ctavk4mo","TUZi7b3GngYZ25XeueEL+JxqIPWFT13L7mwikMSvpVu+1N7K+lGsvuW2/1IQSEoSKJ4=",5400598141778891493,2702021794576713799,-6411203789956738015,8402339349614630793>()
                     ),
                     var29 + 14.0F,
                     var30 + 22.0F,
                     19.0F,
                     a(var43.uT, var72)
                  );
                  com.yiyiaddon.l.g.a.b(
                     var1,
                     com.yiyiaddon.l.a.w(
                        (String)com.yiyiaddon.m.b.a<"s331e5x0txf3sr","E7ysqblKjRJN5kNVRA9eTodQRfIvS2uMOrW9R3p+S3g32at+NTkhfLXiJuFfMFfqSnI=",9052627675169089843,5474746143400531517,-6005401792697971374,-2694185830882691438>(),
                        (String)com.yiyiaddon.m.b.a<"s1mpo9u0ms0aw1","iMjE1N2fKadkdOnM7/yG8+Kqmlbe5Il7T6I74e19kpH/Col8x4zTsDqNrdIPzmBznmIbWCKO/OhwHZxx0D8WMtOPVkm4jsvl6172KsJO08iuYEX1BnQvy3PHvoNfSZ6zPazbBhztMvM3bIGyK/Z9eCmx",3186412694270324923,-4026141692370260136,-7698964149827300826,4559669358518241717>()
                     ),
                     var29 + 14.0F,
                     var30 + 39.0F,
                     11.0F,
                     a(var43.uU, var72)
                  );
               } else {
                  if (this.c != null) {
                     this.a(var1, this.c, var29, var30, var31, var72 * (1.0F - this.lr), var43);
                  }

                  this.a(var1, var14, var29, var30, var31, var72 * this.lr, var43);
               }

               this.a.a(var1, n(var29, var31), x(var30), var72, var43, this.gw());
               var1.save();
               var1.clipRect(Rect.makeXYWH(var29, var30 + 46.0F, var31, var32 - 46.0F));

               try {
                  if (this.gx) {
                     this.c(var1, var29, var30, var31, var72, var11, var12);
                  } else {
                     if (this.c != null) {
                        this.c.a(var1, v(var29), var30 + 46.0F, w(var31), var32 - 46.0F, var72 * (1.0F - this.lr), this.lt, -Float.MAX_VALUE, -Float.MAX_VALUE);
                     }

                     var14.a(var1, v(var29), var30 + 46.0F, w(var31), var32 - 46.0F, var72 * this.lr, this.a.r(), var11, var12);
                  }
               } finally {
                  var1.restore();
               }

               if (!this.gx) {
                  this.a.b(var1, var29 + var31 - 8.0F, var30 + 50.0F, var32 - 50.0F - 8.0F, var72, var43);
               }

               j.b(var1, this.b.x() * 2.0F + this.b.v(), this.b.y() * 2.0F + this.b.w(), var72);
            } finally {
               var1.restore();
            }
         } finally {
            var1.restore();
         }
      }
   }

   private void a(Canvas var1, int var2, int var3, float var4, float var5, float var6, float var7, float var8) {
      if (!com.yiyiaddon.c.a.d) {
         switch ((int)com.yiyiaddon.m.b.a<"s3rkbwramno1fj","6j+VLnqe3EroJVGDZH7VcJvWG4LZ2z7japez4mEw5Ys=",-1504697566845962879,-8377256019199168669,6189994246253530329,-8779112470110064044>()) {
            case 1360009429:
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.l.g.f.a()
            .a(
               var1,
               this.c.b(),
               this.minecraft,
               com.yiyiaddon.l.g.g.ef(),
               this.b.b(var4, var2),
               this.b.c(var5, var3),
               this.b.k(var6),
               this.b.k(var7),
               this.b.k(var8),
               com.yiyiaddon.c.a.c(),
               com.yiyiaddon.c.a.a
            );
      }
   }

   private static String gQ() {
      return com.yiyiaddon.i.b.x() + "";
   }

   private static float v(float var0) {
      return var0 + 14.0F;
   }

   private static float w(float var0) {
      return var0 - 14.0F - 34.0F;
   }

   private void a(Canvas var1, com.yiyiaddon.l.f.a var2, float var3, float var4, float var5, float var6, com.yiyiaddon.l.i.c var7) {
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3svwehay8bw25","XwsvhHJ8LBWn8wUvr4ODc1G25wJ/xMmj2/aHD5OYuI0=",-2675580324229443173,-6667025292204768181,-5574935645201426838,4253966621158551778>()) {
            case 1319556915:
               if (!(var6 <= 0.01F)) {
                  float var8 = v(var3);
                  com.yiyiaddon.l.g.a.c(var1, var2.E(), var8, var4 + 22.0F, 19.0F, a(var7.uT, var6));
                  com.yiyiaddon.l.g.a.b(var1, com.yiyiaddon.l.b.d.a(var2.F(), var5 - 100.0F, 11.0F), var8, var4 + 39.0F, 11.0F, a(var7.uU, var6));
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s284pi0qds0bla","qCAxP7ag7Xc5SncqdS8J1OkFPY1jvk5E1TPaX/u+KE0=",-8296294872749741629,-6768420943535332385,4889971901297634367,-373558259621976375>()) {
                     case 133506647:
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

   private boolean gw() {
      if (!this.gx) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s2txjuk40o9ouc","W9igZw96/rc+E2znuR8p6f5i01UvUeKrGzNepkPiMlU=",2804646745067355235,2986265786218066308,-8760018687894550689,-853475513054661046>()) {
            case -1291725191:
               if (!this.c.fY()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s65j2xughqfoc","s1S7A5662dbxuUgfjOji20D/NEozX2ijZcRI5OOaNTQ=",-2887382912519776934,-3748743936138643643,-7734828603500982982,-8389261922600512854>()) {
                     case 204453140:
                        return false;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s1sij6yozhf8kg","d9o6gAmB+vK4eRzG8lW13heoTf9iH8CQFld37307/gU=",7235566764714763880,-53669418320034143,7767168164251865408,2211763652581319281>()) {
                  case -991885382:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s2u9iv4xd5y9ux","7PXZdq5KM9WsuRFb9PvmUlurtNx/SbKpBzHPy+8lFPM=",5642798073251743325,8114157159487107644,-6510461607092235496,-7035903182288276501>()) {
         case -1240604223:
            return true;
         default:
            throw null;
      }
   }

   private static float n(float var0, float var1) {
      return var0 + var1 - 40.0F;
   }

   private static float x(float var0) {
      return var0 + 12.0F;
   }

   private float ak() {
      return 456.0F;
   }

   private float h(float var1, int var2) {
      int var3 = var2 / 4;
      int var4 = (this.dy.size() + 4 - 1) / 4;
      int var5 = 4;
      if (var3 == var4 - 1) {
         switch ((int)com.yiyiaddon.m.b.a<"sxfqxy8mnryn","j4d1DogcTsAp07d94NUWpjK6WUZuzCWhqwTBLRl8r+s=",-5176952875663988471,136421648898374838,-4313962588199662143,-9163016395225122970>()) {
            case 400970558:
               if (this.dy.size() % 4 != 0) {
                  label16:
                  switch ((int)com.yiyiaddon.m.b.a<"s1n9bh2hafg4fo","IqeH3ce72lqSUhPHpvE+SisTqm7gknqFBhGZ+HG9nbQ=",1326371006056927055,-7594611199400051442,-2794381924402147673,5897828711525315791>()) {
                     case -856901666:
                        var5 = this.dy.size() % 4;
                        switch ((int)com.yiyiaddon.m.b.a<"s37nrel2zs6nfq","OrkWOElEGL+1hFP3lT1zKVwTy6lrPZVkiUkVKQ+1RHU=",7786208028765565919,-6309136636069999713,-8205102340391310911,-3944910583051114014>()) {
                           case 1997373498:
                              break label16;
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

      float var6 = var5 * 96.0F + (var5 - 1) * 24.0F;
      return var1 + (this.ak() - var6) / 2.0F + var2 % 4 * 120.0F;
   }

   private float i(float var1, int var2) {
      return var1 + var2 / 4 * 138.0F;
   }

   private void c(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      float var8 = var2 + (var4 - this.ak()) / 2.0F;
      float var9 = var3 + 46.0F + 30.0F;
      this.uF = -1;
      String var10 = com.yiyiaddon.l.i.d.gR();
      int var11 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s38bnzn3kydzc8","PIPvproB7GZyir1graXR7qtR8kaLd7O6ZIiPY3n66/w=",734599493312287938,-7519097512454133887,-5214401834854002621,-8036874310837832948>()) {
         case 1065149130:
            while (var11 < this.dy.size()) {
               switch ((int)com.yiyiaddon.m.b.a<"sb9un3qjf8ws7","+4zcEdRRy8H4RmWYCOgA0CijlaAVBnj2a/rPiUW/5ZA=",5675621471748965520,3543177513114427979,-287961301501710014,1661872788399648396>()) {
                  case -1810612723:
                     com.yiyiaddon.l.i.b var12 = this.dy.get(var11);
                     float var13 = this.h(var8, var11);
                     float var14 = this.i(var9, var11);
                     if (var6 >= var13 - 6.0F) {
                        switch ((int)com.yiyiaddon.m.b.a<"smgsccg00b3mf","zRXYBilFwCvtjX5rxSfz0w6W+qMHLC0P11txQucnGtM=",171547049797306787,2614581022067473317,399013714930930548,-1787054481092110741>()) {
                           case 719105225:
                              if (var6 <= var13 + 96.0F + 6.0F) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3t0n6zkywg521","DFQmnh1flle49pZjRcUBtepOydZN8vzhdQhFdlfpQSs=",-4779105204322629542,-422491985539520876,-9064650062557758664,6405781895304198380>()) {
                                    case 1707163756:
                                       if (var7 >= var14 - 6.0F) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1uoxubslw57xa","Qx+V+s2OFM2gTSJH13jB7x4a/w1KR/cg0s+jISO16mM=",-4791190441926549953,-4152190490516079750,1101136649391199667,6485820659934567490>()) {
                                             case -1900307898:
                                                if (var7 <= var14 + 96.0F + 26.0F) {
                                                   label51:
                                                   switch ((int)com.yiyiaddon.m.b.a<"srx0zkhl2ye8g","na5qAJIMdXMwWoZrVfDFA2l0+Iioa5IDYrdGYs6Zy5g=",2107468527814811975,-5075003157248253661,-1569335272938382791,7204063539359463737>()) {
                                                      case -2014894400:
                                                         this.uF = var11;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2rhpw27sopnoh","yzsN0vO8OnjH4bS+qQgQzefs+z3jH5qpjHwETGXGb+I=",-8607925920454189942,-7717265506428050099,-2711399995285859873,9107063715260624965>()) {
                                                            case 1026145951:
                                                               break label51;
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

                     boolean var10005 = var12.s().equals(var10);
                     boolean var10006;
                     if (this.uF == var11) {
                        label41:
                        switch ((int)com.yiyiaddon.m.b.a<"s26t52zaf0lhno","XwOHZbyrEuDIi5sixFTQUhrD7G4sAqutEc5TiaCTcOc=",-7457871292827509360,-6744301263693623302,5414811862696804167,-8966390451118181883>()) {
                           case -1554499121:
                              var10006 = true;
                              switch ((int)com.yiyiaddon.m.b.a<"s1n7sjsqdd644n","xhUj1q9hY5uFksVDqqv5mKa2cWJC56royPO4rXLjVdo=",8104776275335336070,6109829222553274413,3135237652655362610,-664765709520159956>()) {
                                 case -254704946:
                                    break label41;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10006 = false;
                        switch ((int)com.yiyiaddon.m.b.a<"s21k9s3pq7swsc","eAgpCpUInre+8+eyGb/uZ4ALsFcbRdSKmod+lK8I1lc=",3060844752351530810,2600305221648627622,5789668446908186812,8172593697951887414>()) {
                           case -1595174150:
                              break;
                           default:
                              throw null;
                        }
                     }

                     this.a(var1, var13, var14, var12, var10005, var10006, var5);
                     var11++;
                     switch ((int)com.yiyiaddon.m.b.a<"s14dbkhk3yxmfb","Ahcvdrv3PVwi03m0kY0OsqUkShnVMSOOZVza27uEXRE=",8124465210297923468,-4271569174804735622,-5796014206314963555,2700121960863933241>()) {
                        case 472447913:
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
   }

   private void a(Canvas var1, float var2, float var3, com.yiyiaddon.l.i.b var4, boolean var5, boolean var6, float var7) {
      com.yiyiaddon.l.i.c var8 = com.yiyiaddon.l.i.c.a(var4);
      this.z.setColor(a(var8.uN, var7));
      var1.drawRRect(RRect.makeXYWH(var2, var3, 96.0F, 96.0F, 14.0F), this.z);
      this.z.setColor(a(var8.uO, var7));
      var1.drawRRect(RRect.makeXYWH(var2 + 12.0F, var3 + 14.0F, 16.0F, 68.0F, 8.0F), this.z);
      this.z.setColor(a(var8.uS, var7));
      var1.drawRRect(RRect.makeXYWH(var2 + 14.0F, var3 + 16.0F, 4.0F, 14.0F, 2.0F), this.z);
      this.z.setColor(a(var8.uQ, var7));
      var1.drawRRect(RRect.makeXYWH(var2 + 36.0F, var3 + 18.0F, 44.0F, 18.0F, 6.0F), this.z);
      var1.drawRRect(RRect.makeXYWH(var2 + 36.0F, var3 + 42.0F, 30.0F, 18.0F, 6.0F), this.z);
      this.z.setColor(a(var8.uU, var7 * 0.55F));
      var1.drawRRect(RRect.makeXYWH(var2 + 36.0F, var3 + 66.0F, 36.0F, 5.0F, 2.5F), this.z);
      int var10000;
      if (var5) {
         label47:
         switch ((int)com.yiyiaddon.m.b.a<"sjf5k8rzjhj0m","kiZsBd8U48xm0r0dMEluglIMBP+bqvPsThpw2vXjFuA=",-4175702601370420451,7207184229129096400,8960863238185861994,6902033384802254641>()) {
            case -1396376517:
               var10000 = var8.uS;
               switch ((int)com.yiyiaddon.m.b.a<"sd6jpzt349x3k","Vue5ZfU2dfEveTng+rgHQR6ovEVUMBZsVo3QO8B5AbU=",8061856728018020008,-6848281609875118153,8706732404747294293,-6923377020770448931>()) {
                  case -1451702124:
                     break label47;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else if (var6) {
         label44:
         switch ((int)com.yiyiaddon.m.b.a<"s2am10e6v8tkfa","M+LhsayQQCuM7RzyCu6hMm40cYlgCk9M0Ct6L8ib710=",8478358280194323104,-8899031738580422527,-2182140366417634677,-4732092394500943510>()) {
            case -82596075:
               var10000 = var8.uU;
               switch ((int)com.yiyiaddon.m.b.a<"s2smmvl47x05hw","RZPFVBagFIpLSNFKMXekEMFWxhBlwfGI9vblvdJ3Cr4=",-8648577243201251763,-1646355228845200879,-7238673360582034006,4719712257312104679>()) {
                  case 879656206:
                     break label44;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var8.uV;
         switch ((int)com.yiyiaddon.m.b.a<"s6h87jo1fxf78","NPszhd38So0jxpN1bh2byAEsRyuxAeijFFfCaYc7CGg=",8688846913757787077,-2958339113440507415,-824558616643480716,-8434274969568499590>()) {
            case -840602023:
               break;
            default:
               throw null;
         }
      }

      int var9 = var10000;
      Paint var12 = this.A;
      float var10001;
      if (var5) {
         label37:
         switch ((int)com.yiyiaddon.m.b.a<"s3p7bfnkxmqyd7","Kytg65AlN74f2h6aiiprSRbpmoEIgbn0PHzz4RA1vmU=",648538509220647420,9067061226221061570,2033523306819796275,-8650224867581769046>()) {
            case -304372073:
               var10001 = 2.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s85jqatfzcxl2","Vy+5VaqU4Jkd+hvRe7ovsPWKVssStlr03lAS2gE+Gho=",-3222638362511260582,5826509317685370781,2468726401958553163,-6985362632765801546>()) {
                  case 147231514:
                     break label37;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = 1.2F;
         switch ((int)com.yiyiaddon.m.b.a<"symt6e7kylozo","8msvWkEGSA1YnySOxEr4hQEw6Pl+0+HKqxImZrjZKXE=",6709407355998512438,-3517286780651769454,7206961400713223154,6563306560793255318>()) {
            case 1117456296:
               break;
            default:
               throw null;
         }
      }

      var12.setStrokeWidth(var10001);
      this.A.setColor(a(var9, var7));
      var1.drawRRect(RRect.makeXYWH(var2, var3, 96.0F, 96.0F, 14.0F), this.A);
      if (var5) {
         label32:
         switch ((int)com.yiyiaddon.m.b.a<"s3u983hxsh303o","/9oSvLOBaP1OQStlq2k78P6BY78MOJmysrYPr+1iQ3E=",-3921450886367367405,6871248322668343759,5952045718337421167,6340951521404538620>()) {
            case -677187560:
               com.yiyiaddon.l.g.a.a(
                  var1,
                  (String)com.yiyiaddon.m.b.a<"s12nlcz97lxlxb","AvbkvaMjGcX5LryvcS0jFNkeEDV6Cik98IBy3ht7",-2585247595611450106,-8649898510522265695,8770079101605676499,-9211265619195447207>(),
                  var2 + 96.0F - 24.0F,
                  var3 + 20.0F,
                  13.0F,
                  a(var8.uS, var7),
                  (String)com.yiyiaddon.m.b.a<"s1kris6ixzujs6","kdlc/ByTP67SDUMHjkoh3j1WoSA/X/QNoiXLimUh7QMZEiQ380gFKrLZ7iujy9TjfbERmty2geqPI0Gv",-7622355196040374750,-3365830134689732819,7129985490982912013,3689873943726774676>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s3ev146cnyp7fx","NOqq2YixIoff6+Old38ijjGvRGC0gaHLX+m7eSGSTRI=",5497559007239748837,-7543205930080352622,2542801801587695963,294959471193815972>()) {
                  case -1296978915:
                     break label32;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      String var10 = var4.m();
      float var11 = com.yiyiaddon.l.g.a.b(var10, 12.0F);
      com.yiyiaddon.l.g.a.b(var1, var10, var2 + (96.0F - var11) / 2.0F, var3 + 96.0F + 17.0F, 12.0F, a(com.yiyiaddon.l.i.c.a().uT, var7));
   }

   private void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7, com.yiyiaddon.l.i.c var8) {
      this.lA = var2;
      this.lB = var3;
      this.lC = var4;
      this.lD = var5;
      if (this.gu) {
         label79:
         switch ((int)com.yiyiaddon.m.b.a<"sbb9ro4jwo6eh","iNre7ClzwrEeoat8KrJmy9WrCRh4uoxuxKBxGh0j1bs=",-4429565882881186551,-2433538683332260118,-8816587813600540705,3624957290778010911>()) {
            case 555152216:
               com.yiyiaddon.l.g.b.a(var2, var3, var4, var5);
               switch ((int)com.yiyiaddon.m.b.a<"s27bdpynmyk05x","25EiFN/WFI+mqcdnwXhPQmiEzQO13Y0+bRztBrQprZA=",-2109962646411210550,-7552768390207716920,-1885756667393983765,7159140398785113272>()) {
                  case -1466598168:
                     break label79;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      com.yiyiaddon.l.b.j.a(var1, var2, var3, var4, var5, 10.0F, var8, this.lx, var6);
      com.yiyiaddon.l.g.a.a(
         var1,
         (String)com.yiyiaddon.m.b.a<"s1u8qhvuytqval","jnvTgajQxzeCWzwIK/6NUhy+I/8Hc9iPjsQZCO0I",-1895803423098534651,436182874915823848,6050884082766359651,8175447227065795538>(),
         var2 + 9.0F,
         var3 + 19.0F,
         12.0F,
         a(var8.vo, var6),
         (String)com.yiyiaddon.m.b.a<"s1kris6ixzujs6","kdlc/ByTP67SDUMHjkoh3j1WoSA/X/QNoiXLimUh7QMZEiQ380gFKrLZ7iujy9TjfbERmty2geqPI0Gv",-7622355196040374750,-3365830134689732819,7129985490982912013,3689873943726774676>()
      );
      float var9 = var2 + 28.0F;
      float var10 = Math.max(1.0F, var4 - 36.0F);
      boolean var11 = this.Gm.isEmpty();
      String var10000;
      if (var11) {
         label72:
         switch ((int)com.yiyiaddon.m.b.a<"s1ty8iyabirffv","0wdM4cVRDdUmHHkp+1ZftpcivH1Rhq0399z/IThTU9c=",4445931621615047506,74295001145706262,2476714840229783682,-1122849020023158248>()) {
            case -2101047639:
               var10000 = com.yiyiaddon.l.a.w(
                  (String)com.yiyiaddon.m.b.a<"s3v8hlhb80cnjt","g8Mfwrvfs/vA82qJWRectZH/9wfYtrLUr2ohL43jeswDH9majDtPMBA/1O8=",-444872526907586331,7758683721676609595,-1594067830647915184,-7746206274555480956>(),
                  (String)com.yiyiaddon.m.b.a<"s3rxghuyybe0gp","R4wblrkRGFn5MxUjCWeSF8YxpyxFmZVtk/3avg+SO8bpxhme9o/XvdmZiiTJjXGUhqt289vdn/GMGZK8m4M=",-3243949709147214201,3292669935563550948,3514140839227492263,-3178544273846770873>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"smld5sqf3psvl","wnjs4dEOCI8p7WOr/9oND5nQFffYEm94YnUVjrJHPEE=",-7746375755977748453,7628498690213470303,6718856339431642463,3941148009957242922>()) {
                  case 1463621319:
                     break label72;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = this.Gm;
         switch ((int)com.yiyiaddon.m.b.a<"s1t42e7vdd9hvz","Us63tlOWaNuX5BmEZqnJm4JYNWSLQmx9JhylUD15MFA=",-1311037803512620279,5261911222250176781,-7694635179124396577,6954757875914509648>()) {
            case 955487478:
               break;
            default:
               throw null;
         }
      }

      String var12 = var10000;
      float var13 = com.yiyiaddon.l.g.a.b(this.Gm, 10.0F);
      float var18;
      if (var11) {
         label65:
         switch ((int)com.yiyiaddon.m.b.a<"s2rftx7e5nqjzl","pmNn/0pqPmSVeMCjGeb0Y8A80rWQcH2H5rEMAQyirJY=",8546618140450156160,6229146598647546057,-8984405938812184373,6131105812456894449>()) {
            case 1075365011:
               var18 = 0.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s34ka950g9rysb","0+NrifvtL8WN+nZQGSL/X62jA3BflYeoB5imLBA32x4=",-986192705280408682,6399716754812457809,3962790109895475680,6679993523075967067>()) {
                  case 480516603:
                     break label65;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var18 = Math.max(0.0F, var13 - var10 + 3.0F);
         switch ((int)com.yiyiaddon.m.b.a<"s1nlab2wnqiyy4","mk1O/41MSgQurNfiZwMeVTlcrjYzNXndnia46HkGvm4=",8912929468108598248,6255864661806847674,-1347091293463487609,-6436468538888100788>()) {
            case 380519560:
               break;
            default:
               throw null;
         }
      }

      float var14 = var18;
      this.ly = e(this.ly, var14, var7 * 16.0F);
      var1.save();
      var1.clipRect(Rect.makeXYWH(var9, var3 + 2.0F, var10, var5 - 4.0F));
      float var10003;
      if (var11) {
         label58:
         switch ((int)com.yiyiaddon.m.b.a<"s1a6h61g2k4u6j","PFvaDaFpeuSR92UrsfM6tAd5safP9gzFHwG9UDUNWmI=",-5151560499376820822,-1717340412155327991,-4985222444370729953,6639393761789272647>()) {
            case -1812976933:
               var10003 = 0.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s20bxamuudecf6","iPZijuKl+cG7TQbJyRXhh23EE1dccpDRNWMMAhXhxVo=",8751135701031990904,676304982886294223,-7648058389004189859,-6402905695028057910>()) {
                  case -1180443385:
                     break label58;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10003 = this.ly;
         switch ((int)com.yiyiaddon.m.b.a<"sr61yxlycveou","E28unpHqND2x2n7YprG1wBYZK06Op/ZvYjOjh6uukN0=",5752946908721950373,-5752067725378152812,1383379076776017228,-3493834696945249966>()) {
            case -266518549:
               break;
            default:
               throw null;
         }
      }

      float var10002 = var9 - var10003;
      var10003 = var3 + 18.5F;
      int var10005;
      if (var11) {
         label51:
         switch ((int)com.yiyiaddon.m.b.a<"s1mh5ee52ng7hl","0jV7VnjOiGrFXV/Ij6asCfhHbDzYUQ7MKsHFSXk/FyM=",4370073033155223075,-1168655382099495447,6812063872697355727,-6886101236886472616>()) {
            case -1348193271:
               var10005 = var8.vr;
               switch ((int)com.yiyiaddon.m.b.a<"s2i7mautynhula","nwtrJhfiikdTxcd41pgG9OGx7SrKR8EKseHtwZCihh8=",8346356505678433747,8077756434765434411,1488137693866890120,-2819837697081307542>()) {
                  case -1423369490:
                     break label51;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10005 = var8.vq;
         switch ((int)com.yiyiaddon.m.b.a<"s3crdsswbpsofp","5vBGLhEFKUAbMYzARlcTel58IUq02wFCy6uYZSxjSCM=",-7664623995736981905,-7092311848138175273,7640103754676537651,8640868701550864807>()) {
            case -662001017:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.l.g.a.b(var1, var12, var10002, var10003, 10.0F, a(var10005, var6));
      if (this.gu) {
         label46:
         switch ((int)com.yiyiaddon.m.b.a<"s2ssth6oprb0hj","e3KNy9jOYMq5pPtZA/ZFCGoILLRQ4/3ZEK//nu80LgI=",2001037668508274608,2378658724078804488,-231373348258128789,-4923305251791339231>()) {
            case 1968643382:
               float var15 = 0.35F + 0.65F * (0.5F + 0.5F * (float)Math.sin(this.lz * 6.0F));
               float var16 = var9 + Math.min(var10 - 1.0F, Math.max(0.0F, var13 - this.ly));
               this.y.setColor(a(var8.vp, var6 * var15));
               var1.drawRect(Rect.makeXYWH(var16, var3 + 7.0F, 1.0F, 14.0F), this.y);
               this.c(var1, var16, var9 + var10, var3, var5, var6, var8);
               switch ((int)com.yiyiaddon.m.b.a<"s2ib8s7w4vfrks","h8ro1oaepvmqBi2lAB4g6aO8wjKPht6bRKHw7vrn5QU=",9216541927594092433,-1656050352982080837,4750789036273145214,8626776917370772501>()) {
                  case 963736633:
                     break label46;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      var1.restore();
      float var17 = 0.3F + 0.7F * (0.5F + 0.5F * (float)Math.sin(this.lz * 6.0F));
      this.y.setColor(a(var8.vp, var6 * this.lx * var17));
      var1.drawRect(Rect.makeXYWH(var2 + 8.0F, var3 + var5 - 2.0F, var4 - 16.0F, 1.0F), this.y);
      com.yiyiaddon.l.b.j.b(var1, var2, var3, var4, var5, 10.0F, var8.uS, var6 * this.lx);
   }

   private void c(Canvas var1, float var2, float var3, float var4, float var5, float var6, com.yiyiaddon.l.i.c var7) {
      String var8 = com.yiyiaddon.l.g.b.gP();
      if (var8 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sbh3w1p2j9dzw","t7zHCgZrAYZESme48P77TusT0iUflwnT1OvzjLWps08=",-1334579418214616904,-6821658705341801370,-4177671960657365218,4510105804828041435>()) {
            case -998790357:
               return;
            default:
               throw null;
         }
      } else {
         float var9 = var3 - var2;
         if (var9 <= 1.0F) {
            switch ((int)com.yiyiaddon.m.b.a<"s1vvaxdxgygtju","Bi+0aK2D2m3m5MWO/fzxkoL90wWgGx56+rfE1tixEW0=",-4211322397831411921,-5185938156298824749,5495638084384926197,-2212773022752991727>()) {
               case -1405281794:
                  return;
               default:
                  throw null;
            }
         } else {
            var1.save();
            var1.clipRect(Rect.makeXYWH(var2, var4 + 2.0F, var9, var5 - 4.0F));
            com.yiyiaddon.l.g.a.b(var1, var8, var2, var4 + 18.5F, 10.0F, a(var7.vq, var6));
            float var10 = Math.min(var9, com.yiyiaddon.l.g.a.b(var8, 10.0F));
            this.y.setColor(a(var7.uS, var6 * 0.85F));
            var1.drawRect(Rect.makeXYWH(var2, var4 + var5 - 5.0F, var10, 1.0F), this.y);
            var1.restore();
         }
      }
   }

   private void kF() {
      Iterator var1 = this.c.bH().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"sf1x79gxfpeua","+G0XYFbdf5ghXLoz4sMpQx/nN2xbYjmDYcy42YdQofA=",-5435229368976526359,-5299500522178668913,-2774707968366887870,-8928351908239544055>()) {
         case 1537517671:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"sxyrfyfmndq9y","e0GByzaLpouhAePfQukyG/OALPuzfuplDf5U/rX6jlE=",5205711700891807623,-5247928406223130724,1158361930688963366,8730677920960805473>()) {
                  case -1681616186:
                     com.yiyiaddon.l.f.a var2 = (com.yiyiaddon.l.f.a)var1.next();
                     var2.bf(this.Gm);
                     switch ((int)com.yiyiaddon.m.b.a<"s9201ye8nlc1z","r8vcyydOIqltC/Q8MiVPouHaxq/jF3JA2iAt5I9eDdc=",-23815048129894545,-7787225558323902035,-8008395828390768043,-504650222004846298>()) {
                        case 512613150:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            if (this.Gm.isBlank()) {
               label44:
               switch ((int)com.yiyiaddon.m.b.a<"s2daiq0kh65jpi","/Hay8hRUOm3L/wca0D6Oe8lvjpRkoA3QQLyCm1mGLQw=",-5697384542821338618,294599639366247320,2999556496129844713,-3176069018728978401>()) {
                  case -1526104088:
                     this.a = null;
                     switch ((int)com.yiyiaddon.m.b.a<"s1ak36idaua2no","dFzaOR5AUfSv+WiNqcJKPy+xzCHifeNHX77gBrIlMOA=",-7404724668965723217,7860750269201040949,-1878078716033854738,7105924051316367635>()) {
                        case -24527700:
                           break label44;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               ArrayList var6 = new ArrayList();
               Iterator var7 = this.c.bH().iterator();
               label62:
               switch ((int)com.yiyiaddon.m.b.a<"s2xocdk0b1vgsx","0NHGASkleDGP6emLD4IBZHT+eAmWl2vpUkNCeXjJS08=",8815317086012832348,-8624689478334975107,151268228356260688,9125756924710952210>()) {
                  case 680278945:
                     while (var7.hasNext()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2k24m6wotaglt","wOVQkh1WiuBhAxOMi38KnZWfKIJ0TCSXqRucFx5KQZo=",3233340924428695430,-2025959828525327524,7796142107436316285,-2376749889961118303>()) {
                           case 1262240873:
                              com.yiyiaddon.l.f.a var3 = (com.yiyiaddon.l.f.a)var7.next();
                              Iterator var4 = var3.bJ().iterator();
                              switch ((int)com.yiyiaddon.m.b.a<"s38f2fsu89defx","+4kfZ/tbV3pv7YuhMa7JpZA6xOym2ukrq70XMJN8yeE=",-4071608410954615638,-2515770594672987344,2507974434621181161,-3235367213808873375>()) {
                                 case 1277868333:
                                    while (var4.hasNext()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"sulmcwyxmgde1","KF6nZ7dS80ahCoxmvp6wVX9MUk5IJviTFFst6Gvc7UQ=",-3510911852860585508,3241654889741214971,-3003709534150696582,-2157807820749719593>()) {
                                          case -810633031:
                                             h var5 = (h)var4.next();
                                             if (var5.gG()) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s1o1rijkoqy31a","YaL92lBmrvkNooD5aola4OZWJP3Q3zkbl8bMhp0Nv/g=",1822239753241409284,-2233313550446826892,-4419122120773111562,8772825508024731840>()) {
                                                   case -1798771321:
                                                      if (var5.aX(this.Gm)) {
                                                         label53:
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2fp62n47u9vf4","o97afXHi7500WrEYl9q35E8NehHT0DYwUQLwHIGfZUk=",8548154100728229089,801774401058857837,2663039259019225508,-2869843282156178775>()) {
                                                            case -1926824517:
                                                               var6.add(var5);
                                                               switch ((int)com.yiyiaddon.m.b.a<"sg8vhykeqavjz","AREkNuxNTB9UIWYSw+WSQS3OJalyAozxtuH+7BgrQRg=",7630275945817307674,-5287713703405246097,6921100516463686845,5543073970567786033>()) {
                                                                  case -1732067398:
                                                                     break label53;
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

                                             switch ((int)com.yiyiaddon.m.b.a<"s13y1o83ssk5uh","3RlDZCx30Yz0ApXdmzzF/6EwAUoaf0CjW+xjEVdqDxQ=",-7418866006706720185,-4760669860837308297,8135360185926593174,8063827107772501247>()) {
                                                case 572621722:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s1zbfqe30grm3t","MZ0yqvV4Cms3MJgiOvBWwByNAmtrbRXx3+4df/Ow9kA=",3587422978763155032,8569171965798106087,-1618616231116603418,7132172815954698415>()) {
                                       case -302263784:
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

                     this.a = new com.yiyiaddon.l.f.j(this.Gm, var6);
                     switch ((int)com.yiyiaddon.m.b.a<"s6w72htji0xma","HD5CH7QiuEkb7f1OHfkn4rdXcCnoXbO8wlNqmEpkTlA=",-4766796964284144947,1732377874150617834,3310484923194833823,7279855833401930188>()) {
                        case 727171980:
                           break label62;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.kD();
            return;
         default:
            throw null;
      }
   }

   private com.yiyiaddon.l.f.a b() {
      if (this.a == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sdl1hblakigmf","LDJcrn8Ujftz7816x1vgvGV8rXkAi4/xpHZ83xhdqA0=",912727005467834456,4384907020027059185,913978622060107621,-8094800752855454478>()) {
            case 74124969:
               com.yiyiaddon.l.f.a var10000 = this.c.a();
               switch ((int)com.yiyiaddon.m.b.a<"s1je0wokf5vjre","iqr6kmagjJR4hRRZnCsT19nt8wckKZ6bDHYTFZkhg+g=",5376707682198013577,-680622445493291108,3434191748971640150,-1233314088802312867>()) {
                  case -902160219:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.l.f.a var1 = this.a;
         switch ((int)com.yiyiaddon.m.b.a<"s1d2kia04uyg95","61PIz02Lxjskiqmq/8l1a3XcR7yXReTcNpGEmmXtWbA=",-1266662977468401989,6723343113553706051,2827886574861037921,5606950991990376851>()) {
            case -614861070:
               return var1;
            default:
               throw null;
         }
      }
   }

   private void kG() {
      this.G(false);
      this.Gm = (String)com.yiyiaddon.m.b.a<"s1oyt8vr0ckzpp","ZvUgWwIhuWHFvEXIeSIS950fTIclNDurDQ9hqA==",4074830886275593428,5729826184208369832,2124713026265430011,-7328367791606069362>();
      this.ly = 0.0F;
      this.kF();
   }

   public void kH() {
      this.kG();
      this.gx = true;
      this.kD();
   }

   private void kI() {
      this.gx = false;
      this.kD();
   }

   private void G(boolean var1) {
      if (this.gu == var1) {
         switch ((int)com.yiyiaddon.m.b.a<"s74cpg6co8ugd","E64p+vm+raW5A5Uo3XmwAt648mTqWS7zy0/2x305a6Y=",-4320348278630812703,-4387586609208951090,-1163698013314532512,-4494363403582540996>()) {
            case 529689404:
               return;
            default:
               throw null;
         }
      } else {
         this.gu = var1;
         if (var1) {
            switch ((int)com.yiyiaddon.m.b.a<"s3mlar3v8bzaie","zBYD034/JwMtFgVRzLpzxlRdw0wvT2wDW4UYYhP8HnI=",140964578837930937,3062111137303791526,6402755345719106685,-2613474926463195658>()) {
               case -324213599:
                  com.yiyiaddon.l.g.b.a(this, this.lA, this.lB, this.lC, this.lD);
                  switch ((int)com.yiyiaddon.m.b.a<"ss8fq6j26dr3z","6Kp85icxUUpnc+ZNbM/6vobo0nI5zkZfE9jHbekrpE0=",-7546067518385830835,-328448116806606315,-5647390282454042787,-5194959928390941104>()) {
                     case 1372664852:
                        return;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            com.yiyiaddon.l.g.b.kg();
            switch ((int)com.yiyiaddon.m.b.a<"s2ow1pkf7va8zf","XtwwLmzs4pydgkew7V+tx1s+q3ZWpQK3kUmVQWiRzmA=",-4260976890056020038,-1696486172111086775,-3806640266075585991,-3915612861364757862>()) {
               case -633203695:
                  return;
               default:
                  throw null;
            }
         }
      }
   }

   @Override
   public boolean keyPressed(KeyEvent var1) {
      if (com.yiyiaddon.l.d.d.w(var1.key())) {
         switch ((int)com.yiyiaddon.m.b.a<"sdsz9citxagxx","apYhe1EvDHO8GSU5J74EoVhv3bi1z2aF6Me8u1F1Rg4=",-859865789046841311,-5657155614319188446,1529271669530300867,4992653275856466876>()) {
            case -1159005220:
               return true;
            default:
               throw null;
         }
      } else if (m.keyPressed(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2yy9d102n6gs8","Eb6lGoazuS07k+9Q7jnsrJ0fpPWyyeEyyFdBLARVzlk=",6999183949687578192,-1064843801546762093,-5633423523272742689,-3292830287036030485>()) {
            case 326303125:
               return true;
            default:
               throw null;
         }
      } else {
         if (this.gx) {
            switch ((int)com.yiyiaddon.m.b.a<"st95z5abl66sw","lvkcu1eb6mWMJ9AcQBfBjexcU4uznuO5WscD79nDpL8=",-2076999038808171102,383185556550444152,7792237410949341307,-8776892165771702704>()) {
               case 1135771128:
                  if (var1.isEscape()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3didrsli0jg37","ZSwV/Dvc0uvhG9V0VzQKPA6tmfNYIAWQrbMHqZ6i6e0=",-5804512992891969408,1788079623932998928,5025513684089064830,-7363817212347924174>()) {
                        case -2059615209:
                           this.kI();
                           return true;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (this.gu) {
            switch ((int)com.yiyiaddon.m.b.a<"s3izyqukef88k","hvDqAQYDS9gMGX4jCYtjGO7Ktekv6sjCvZAY+piPFi0=",-7850720830774850042,-7862373978949494077,221179531183169369,3897959091418964487>()) {
               case 964632491:
                  if (var1.key() == 259) {
                     switch ((int)com.yiyiaddon.m.b.a<"s39u4u2c7bqxa9","TqjF63C8r55/vu8wN6hmSxLkqxvacAkQujDh7DH+7TI=",-292509844524280894,7594841904887683994,4054777466658869358,-8428431957040789889>()) {
                        case -1323446568:
                           if (!this.Gm.isEmpty()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2uhpbv4njj2ph","W494U2pZ2OrjUzHtTUivhY4xnCV8yA8paqlD2Wk2g90=",8436331292491439277,452044762500324327,-7002629685142533880,-4806478654250319236>()) {
                                 case -186718005:
                                    int var2 = this.Gm.offsetByCodePoints(this.Gm.length(), -1);
                                    this.Gm = this.Gm.substring(0, var2);
                                    this.kF();
                                    switch ((int)com.yiyiaddon.m.b.a<"s16ca0829a01l","ZKEajGYzqu0LvSlsJEHczXjB/CQN2fOPz+L6Fe55b7Y=",1830187655846538992,-7167027729334596014,4302380367832059665,7525949676270698649>()) {
                                       case 1375141460:
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

                  if (var1.isEscape()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s14nhudm7ie6tq","Fx3OA7u70TaX+K2WxLtQyinf7eCvTbN/95xmPKvREz4=",8035035555557823467,-2100976110751658222,-3548634065716562057,-2219273740633021567>()) {
                        case 327597363:
                           this.kG();
                           switch ((int)com.yiyiaddon.m.b.a<"s2daw2ens2biid","v5YjJX6F2+Q9juE1xCjcowu2YbX/KP3rkPI5vhAKRz8=",5444524435723709569,3043865273566949615,2139499768893331544,-995948861108573671>()) {
                              case 1360508919:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return true;
               default:
                  throw null;
            }
         } else {
            if (var1.isEscape()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3gdqks96gj000","ibJS0Csk169kZ+22AtZSCb6i8DKA9dFXQTri28wn8Qw=",-9008769432079275372,-8403644916112284226,-4738194674887628424,3450980027932351194>()) {
                  case 1693741351:
                     if (this.c.fY()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2x8z0csqky0rl","dwhZkZS4776EriyYctf4RDvzljEbwh1rf+rmKXfvtxY=",-525212645821673366,-9037994722520725064,-4335449654837418919,1743569798086065711>()) {
                           case -950520631:
                              this.c.fZ();
                              this.kD();
                              return true;
                           default:
                              throw null;
                        }
                     }
                     break;
                  default:
                     throw null;
               }
            }

            return super.keyPressed(var1);
         }
      }
   }

   @Override
   public boolean charTyped(CharacterEvent var1) {
      if (m.charTyped(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"slepns9p5or1t","jcRtZ7RoSzXsGndUrfjzufoYIEl8yB0dRumrBkbXsRg=",-9009153309185645089,-2877235469158598357,447238445809096551,-4118150322795501379>()) {
            case -968444794:
               return true;
            default:
               throw null;
         }
      } else if (!this.gu) {
         switch ((int)com.yiyiaddon.m.b.a<"s3tcrjqsj0gp0k","pcUAsQLOFpQ8YUT/Bda3xmwVAVijoyLWZ0kE/eK4GPI=",3633887029783021414,1963047083899879733,6014761553721850307,-4827105384996035915>()) {
            case 105524380:
               return super.charTyped(var1);
            default:
               throw null;
         }
      } else {
         String var2 = var1.codepointAsString();
         if (var2 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2fws2mf3u3e51","uyqX5fT1so+3EIGL+MfKK23jd2B7Uq6rA3cIrAS1Lx0=",-1511984025934474336,5045481304661545740,-757241626038046411,2383841612317299325>()) {
               case -1450550727:
                  if (!var2.isEmpty()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2c1q0u8xw7722","YMHE/CfuHiKQQK1ZwCGayQucd4a1pwGsI3bMSe45d9g=",-2694843493754407503,1777097716812300936,5972231134564362014,-1959580469821734661>()) {
                        case -1428320020:
                           com.yiyiaddon.l.g.b.kf();
                           this.Gm = this.Gm + var2;
                           this.kF();
                           switch ((int)com.yiyiaddon.m.b.a<"s2o0ovtwf9xzwg","nQy7T0Ce+iu+a90MmokAXr45u30/IN0GfJ3dGw05490=",406104635801669823,-4123684153687070004,8940519884525691919,7397910837813426964>()) {
                              case 1904729327:
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

         return true;
      }
   }

   @Override
   public boolean preeditUpdated(PreeditEvent var1) {
      m.a(var1);
      return true;
   }

   @Override
   public void onClose() {
      m.lb();
      this.kG();
      this.gt = true;
      this.b.jN();
   }

   @Override
   public boolean mouseClicked(MouseButtonEvent var1, boolean var2) {
      if (this.gt) {
         switch ((int)com.yiyiaddon.m.b.a<"s2guw0g1mlaf52","NPaDroTsUl7StAE5IHcm2pt9PZBm4jW1hWbPrm/pPbw=",7485568221226573298,-5240019643678889072,-7722028039460638411,7983471075351182713>()) {
            case -906611686:
               return false;
            default:
               throw null;
         }
      } else {
         int var3 = var1.button();
         if (com.yiyiaddon.l.d.d.x(var3)) {
            switch ((int)com.yiyiaddon.m.b.a<"smwakxhpvjwfh","ciysRfSA+Ajz3G3jBXjDAjx1FmvE7Nq8U4c6REx+4nw=",-3225096989331078556,-5486523156671530586,-6001433432772520687,2295506369729944902>()) {
               case -1631156032:
                  this.gv = false;
                  this.gw = false;
                  return true;
               default:
                  throw null;
            }
         } else if (var3 > 1) {
            switch ((int)com.yiyiaddon.m.b.a<"s1sqhpi3rr0qmi","/j1i7q+nOdONTmITSsvWGiP6GABIg6glj0uaY1fsrs8=",7387197448912613922,-2111046662096692200,-8824868926408758880,1954301925835527017>()) {
               case 833126229:
                  return true;
               default:
                  throw null;
            }
         } else {
            float var4 = this.b.a(var1.x(), this.width);
            float var5 = this.b.b(var1.y(), this.height);
            float[] var6 = this.a();
            float var7 = var6[0];
            float var8 = var6[4];
            float var9 = var6[5];
            float var10 = var6[6];
            float var11 = var6[7];
            float var12 = var6[8];
            float var13 = var6[9];
            float var14 = var6[10];
            float var15 = var6[11];
            float var16 = var6[12];
            float var17 = var6[13];
            float var18 = var6[14];
            float var19 = var6[15];
            float var20 = var6[16];
            float var21 = var6[17];
            float var22 = var6[18];
            float var23 = var6[19];
            float var24 = var6[20];
            float var25 = var6[21];
            com.yiyiaddon.l.f.a var26 = this.b();
            if (var3 == 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s1wbhvbt2f125e","8YjqumaJUeZZ51Thr3tR3Wcc5xWnL/t3/8palZybjGQ=",-6977639110296785124,8008451627313526981,-8532425320143111537,-3695666748243103087>()) {
                  case -384525259:
                     if (var4 >= var22) {
                        switch ((int)com.yiyiaddon.m.b.a<"s4afbhsqzxekg","L83kpVOzDRg7TDJMI6b38SKoa2Gwe1uTGE9tTm3jTdw=",-4062048878696917934,8219388924444677831,6511109831708735494,3792540178285057068>()) {
                           case 1114437521:
                              if (var4 <= var22 + var24) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1ak1y3gmke4h7","vGAHv//L2VlsraBCyrcgiRTYMgQp4clfbP929DzCN1w=",-3048454644684547392,-4581860419539310295,-462352386446885480,-4920785969285489901>()) {
                                    case 326254294:
                                       if (var5 >= var23) {
                                          switch ((int)com.yiyiaddon.m.b.a<"sze5obk668k20","jvhMdVsZj38gILOCLQQgwiTI7CXGz4QIuPp22EXga/0=",-6802487989787921283,-3788811165424010748,-5247332634058842343,2163487322414576356>()) {
                                             case -22077797:
                                                if (var5 <= var23 + var25) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3cp0if9q52c2i","Sg8gFGPKsdWUgXzPcvkTDY20RIM2RdQBN4HZDed+Rpc=",681075116434014606,-5151999129071770265,7209607313639048838,-9064611600498755731>()) {
                                                      case 2036563752:
                                                         m.lb();
                                                         this.gx = false;
                                                         this.G(true);
                                                         this.lz = 0.0F;
                                                         return true;
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

            this.G(false);
            m.lb();
            if (var3 == 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s2ludxyik6oocb","RO7fnrr3RHj+i+SJSrwf/w0MZiUtMFgoCG28y1N3lX8=",-3126063924304326182,2715529166229163675,3455994839028298058,4729197420280174742>()) {
                  case 1983405160:
                     if (this.gw()) {
                        switch ((int)com.yiyiaddon.m.b.a<"sfni1oy3p0bff","4qzJI4BVH7uxVODEOQ8ym1+JOE8u2eGQWGqYyUfPL9w=",-3577185838211107344,4997913679076617555,77421088019498717,1840620668283452036>()) {
                           case -3496328:
                              if (this.a.b(var4, var5, n(var18, var20), x(var19))) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3fj41vglvxpki","v6D6GNAeji36kn44Q3jAW4Je+I+xxDORRtJVmZCAV18=",2209804385830291093,4515972774787465506,6620098298098464169,1370262698707540786>()) {
                                    case 1613146115:
                                       this.a.jJ();
                                       if (this.gx) {
                                          switch ((int)com.yiyiaddon.m.b.a<"smsxk0j7ql9g0","Me7yXeZw7TscweNVqrZtKJdS3Yfat8x7ZoTvCI2XO0s=",-4837495685630768788,792620241881306816,-6960590358359914255,-8079757933598647569>()) {
                                             case 2132588539:
                                                this.kI();
                                                switch ((int)com.yiyiaddon.m.b.a<"s1cxstbxhxwmt4","l/JLiYtGbtxIUNMHgFH6EBc3hFM9PUdltPdBNm0pUcE=",5919081569818566118,-1275371608425526099,-6532995182571854297,2079760456539951901>()) {
                                                   case -117467774:
                                                      return true;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          this.c.fZ();
                                          this.kD();
                                          switch ((int)com.yiyiaddon.m.b.a<"s6oudutjkvhro","1xCTdyb6RuEp27a2DU7LcmHtN0PN3P8WOEDlYvFzGoI=",1511183976488261654,7519757422630301540,8454560859189834220,7263826741237554589>()) {
                                             case 808434161:
                                                return true;
                                             default:
                                                throw null;
                                          }
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

            int var27 = 0;
            switch ((int)com.yiyiaddon.m.b.a<"se3nni9ea3u5k","G9S7hye+8hiPV7dlwzT+Cb80ziFRbH9R6pnrdXYfLjw=",2000233002666603458,-2406551508453742693,-3818677995490498435,1208467021537104649>()) {
               case -402087146:
                  while (var27 < ao.length) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2hq9yn90wjvzl","Pw5I+VPmdMtxpxScLD7vFaSDhuP9timccRQ8kOa6kVs=",-2768685133664394228,-5510760119053567021,1172020920776669770,4472610715630049353>()) {
                        case 391978633:
                           float var28 = var9 + var27 * (var10 + var11);
                           if (var4 >= var7 + 12.0F) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2a165dn3zrh4g","70U+AIjEHcUZRjrQdsENonxJbeXsZQnw6OYfYqT4SUQ=",2436944888272776210,1949834512510518649,-81779487652036896,4734764501370498920>()) {
                                 case -67554066:
                                    if (var4 <= var7 + 12.0F + var12) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s30tduq1d9fhe0","Rnj6MkWBjvaawdBKCrr7yE8fvMsrK7qJTXJJy4s0sKg=",5877395145584389068,8411587297356131902,-3015773205151530839,3696188151903854788>()) {
                                          case 1447618724:
                                             if (var5 >= var28) {
                                                switch ((int)com.yiyiaddon.m.b.a<"st1wsiz9egta0","QWzLg0ZKGVp2AJrXELvLo9g3+9N3b+T453W9tcsibUM=",2388691545132068267,2099803404097723281,-8198149691258675188,-6836727902463848206>()) {
                                                   case -681048533:
                                                      if (var5 <= var28 + var10) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1vwrgoe8lmd5t","lV1Erg36CAIS/gUBLhdf4R9SbbqoyfaSh3yeM8UETRw=",614693015686680275,-5907310467930711068,-7591637872965284636,8362271313036042399>()) {
                                                            case -1501389400:
                                                               if (var3 == 0) {
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s1smf72873gpd3","6kjtkj7pu7DDYLn0qKFjiQhwLkpaFO2kEbPWY5RLnsE=",-8392630735403100321,2695114591992903358,4399122235858757318,-6687731348295694561>()) {
                                                                     case 1796820843:
                                                                        this.b[var27].jJ();
                                                                        this.kG();
                                                                        this.gx = false;
                                                                        this.c.N(var27);
                                                                        this.kD();
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s312534il1wcni","R57tAblWC2U1jsP/6fHP7zPy9Poiy6eznf6Pibku3DU=",8443490755079110873,-6347605328368998796,-8498349756859314655,7223290235006015091>()) {
                                                                           case 294525544:
                                                                              return true;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               }

                                                               return true;
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

                           var27++;
                           switch ((int)com.yiyiaddon.m.b.a<"s7awt4v0ft687","tWgMfB2RFGF2DY3ohe/Lk79dFYPqBtRCpIUaARGgsuc=",4287268991015416847,6925844182795436315,-3949789377143048175,7657136941787259436>()) {
                              case 856852045:
                                 continue;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (var3 == 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3ef3cce2obtyt","3NzktPxiFDXq2M5gebS5FNwbdiwwgZef4wjxpF/f7zM=",1309969461335936622,-6937769154753538071,143296458819154226,-268595918123086227>()) {
                        case 1295257427:
                           if (var4 >= var13) {
                              switch ((int)com.yiyiaddon.m.b.a<"s25j9xx93byfe7","jFVJwAJJPeJLP4SkXfY2ncHhv+McWJdS++ylf1IZZ0s=",4395341078183483627,-1845632300898022466,1286682257521716330,-5983414957731656385>()) {
                                 case 258240668:
                                    if (var4 <= var13 + var12) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2vsgjr8ti5jv6","nu3zksg+tr1+reGjyKiGOavoaFaBDrztSI4mJvQou5c=",6584371879314775905,8140083318496479000,5301971543436391879,289375989904415534>()) {
                                          case -1051981413:
                                             if (var5 >= var14) {
                                                switch ((int)com.yiyiaddon.m.b.a<"st3c3de6fj8vz","NaVta2scUhOjGdB4PE8ffvwkNGzQRHFzM9Vr2JXxN78=",649539055859033839,6743122175517156161,6093818450810226554,-8572023286477852987>()) {
                                                   case 1412365610:
                                                      if (var5 <= var14 + var15) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3jwaohjv1f39a","4Ak2kezlXN6L0YL8z1ht60iCfKvwaAB2zn+daU30WSs=",-4802209845698073353,-4510929006874463446,6646523424734916206,7111231308546760926>()) {
                                                            case 784347597:
                                                               this.d.jJ();
                                                               this.gt = true;
                                                               this.b.jN();
                                                               return true;
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

                  if (var3 == 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3d8uxuq96bb4r","tcosfeiuRALSHd3LMajVM6gDlfyp9Vz6lRbsoOx0x+c=",-5024349432302204289,7695111896874317162,1409524879035411530,7747453373546810307>()) {
                        case -2075778117:
                           if (var4 >= var13) {
                              switch ((int)com.yiyiaddon.m.b.a<"sky1bjktbljwj","/hP+CNB0qj0hbzP17qpIURq4JDnJlaDz/FoSHjohv2E=",2894883761999691298,4993777249579487257,3170961004029050905,-7172779876124617326>()) {
                                 case 1593179756:
                                    if (var4 <= var13 + var12) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1bjucpaw8zlm2","+m0zVwJmnML2V14k7XMngxTTSUgo3Rmd8meMNQ1pCW4=",-4891715469117508880,5276031246522362679,-7722035164792483566,805655126897199762>()) {
                                          case 2116091471:
                                             if (var5 >= var16) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s12csuxvalrw96","zzcy4nKFwDgxE3Eu1nWEuEsBa/Ce4CEyhEE3sfX+5lA=",-4104387789221031973,-5017579453314770325,-829361664821611961,-6108375267239871931>()) {
                                                   case 930352570:
                                                      if (var5 <= var16 + var17) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1hvbgiqnzr7kk","186dZ4nSNiFu0ogNoghavf0Fwz8j43FDBDhRTmL8ooc=",-5761854572472525095,-5956202792442969679,5003100667086686531,-5549322544995583046>()) {
                                                            case 489543316:
                                                               this.e.jJ();
                                                               if (this.gs) {
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s3ng8pvsfi0jps","ZyoVhOEdSUK8IaUtdaxMyCJPXk1dt+Ci9wO5rtCD/3w=",-9015744655926446468,7144234235121501640,-7872721338202545839,-4841019905287462854>()) {
                                                                     case -1819567211:
                                                                        this.gx = false;
                                                                        this.kJ();
                                                                        this.gs = false;
                                                                        this.kC();
                                                                        switch ((int)com.yiyiaddon.m.b.a<"srx12mwioy4qv","On+gNUIzxB7Lq2Ow5XXY2f5EMdXRfAtX1OB9+8yDSLE=",-1156586230089116417,-6158152053047729560,-6203249014058187204,8726649984496538468>()) {
                                                                           case -926880113:
                                                                              return true;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               } else {
                                                                  this.gs = true;
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s315lwoweo438","dinPvWPgjw4aWBT+QLuvVpblDSIik+76eRvXdumGtUw=",-1543954697187963405,-4161752500865810429,7607257929446160366,-7642671660818087742>()) {
                                                                     case -630292887:
                                                                        return true;
                                                                     default:
                                                                        throw null;
                                                                  }
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

                  this.gs = false;
                  if (var4 >= var18) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3ks8nh79tfbw8","W5HFHhb+/7kBtTKB947xkZMDZEOCLZQvBhpCkMzB4YM=",2707821785789503709,-6948337374853926615,6047022237216793673,5324045827171163474>()) {
                        case 1398650265:
                           if (var4 <= var18 + var20) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3dv7gdujgf4j5","cj7MDoJtWSN6yK69PSK3ebNIQa0vapvQjrUKUZiMKgk=",-7833090657117006340,-6924285613506016364,9025577525097735444,-3586300743634269513>()) {
                                 case 293928352:
                                    if (var5 >= var19) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1cu8pt37wf13b","rQsGlIYlVyg8ty6ckjPuEBXbtAiyfynk3LO43Rip+Ok=",-7170670903389281506,-8032187459182900648,7287281792218207644,298917785705231951>()) {
                                          case -1516785386:
                                             if (var5 <= var19 + var21) {
                                                switch ((int)com.yiyiaddon.m.b.a<"spsyuor66dnyc","BOeGNAIG+Y9xMyOO1Gz/pbwRi/XIYtiryfA/gj082gE=",-3021805966143893158,1034043749149264121,-3409785976001269287,5149095821678259858>()) {
                                                   case 985267084:
                                                      if (this.gx) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3tii0zz9gwmly","P/Vj/gzmafutkIcp6GIEYFWkWz8PthrHY94RxZFmj4w=",6502972116849127590,-8497880781375759871,-958732772964515761,-3770705895440323722>()) {
                                                            case -1003797668:
                                                               float var34 = var18 + (var20 - this.ak()) / 2.0F;
                                                               float var36 = var19 + 46.0F + 30.0F;
                                                               int var29 = 0;
                                                               switch ((int)com.yiyiaddon.m.b.a<"s1kzu3225kqlhs","j/LzUiYKB0kFkq4mNcGp7UXdwLgCjL8pf05LTmqiTys=",-7804881379704297551,3003931964637028808,-2185593098972980132,-6930014912382672677>()) {
                                                                  case 1797006415:
                                                                     while (var29 < this.dy.size()) {
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s10hrherec7zq0","LcbkStJriK31N2o9qk4/akC6rik9M+7/BxJuCvQKJ8o=",-3420312286667720940,-3560815380041687824,982484937788403108,8096482065342479638>()) {
                                                                           case 594176719:
                                                                              float var30 = this.h(var34, var29);
                                                                              float var31 = this.i(var36, var29);
                                                                              if (var4 >= var30 - 6.0F) {
                                                                                 switch ((int)com.yiyiaddon.m.b.a<"s2vv4bxxg77dp1","aDGXapIdRaQyTczpzGYK+TMfNk/kkSsaxT82EGjJfqQ=",8313445463873003375,1773301245466780639,-6471529582806638256,4910784709175321613>()) {
                                                                                    case 544277974:
                                                                                       if (var4 <= var30 + 96.0F + 6.0F) {
                                                                                          switch ((int)com.yiyiaddon.m.b.a<"spnwcj0p3b8a8","x0/aeCO6U1GbdKXzz2mzju3RMU/1xJYcgy3FwOqdVQo=",4159267982877659921,2143772231818749936,8725581812007850470,5059721217009659753>()) {
                                                                                             case 1941380021:
                                                                                                if (var5 >= var31 - 6.0F) {
                                                                                                   switch ((int)com.yiyiaddon.m.b.a<"s1rlv0k37hwcx6","BzJBQ3+s5qqJC9SLUNtSpkhDylE4f4SdhLQ695Ot+Zk=",9113701750847891955,1717307939948121092,6041557106187863121,1955077032333036412>()) {
                                                                                                      case 335215391:
                                                                                                         if (var5 <= var31 + 96.0F + 26.0F) {
                                                                                                            switch ((int)com.yiyiaddon.m.b.a<"sfk6x6tc9j83u","12O1DQwyaKoGsGFliw3UkfxFHRQbdvMHWdhjuxePM/M=",-672020006688996557,4205421307205799585,-4520614669115151250,-3017567698047362461>()) {
                                                                                                               case 1819660799:
                                                                                                                  com.yiyiaddon.l.i.b var32 = this.dy
                                                                                                                     .get(var29);
                                                                                                                  if (!var32.s()
                                                                                                                     .equals(com.yiyiaddon.l.i.d.gR())) {
                                                                                                                     switch ((int)com.yiyiaddon.m.b.a<"s2e2iygohs2xjw","qH7bQA/NhRRIIcLffe/xdM2HUHw7VRbobDd1MKGG3JQ=",-3627799730731239734,-6730783631117588276,-6339491470510207589,3491609061641585557>()) {
                                                                                                                        case 397328386:
                                                                                                                           com.yiyiaddon.l.i.d.aW(var32.s());
                                                                                                                           switch ((int)com.yiyiaddon.m.b.a<"s3o13b8u3iowam","thtJwtnmGjFLxeDWf3nN1i3dCDY1jqBuRxuJM0baj7c=",-492981122778191682,7102719619071187915,6630436222896893768,-413771854614268109>()) {
                                                                                                                              case 2063346372:
                                                                                                                                 return true;
                                                                                                                              default:
                                                                                                                                 throw null;
                                                                                                                           }
                                                                                                                        default:
                                                                                                                           throw null;
                                                                                                                     }
                                                                                                                  }

                                                                                                                  return true;
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

                                                                              var29++;
                                                                              switch ((int)com.yiyiaddon.m.b.a<"s3ftd3iqcuszmj","ZkXvVlzPVbZW5+6mumOl1IIm2W6k8cwJ/6P7hYzv2ns=",-4632567172360475665,-5916336616583725676,6169585708952048423,7032439139328748111>()) {
                                                                                 case 1960856585:
                                                                                    continue;
                                                                                 default:
                                                                                    throw null;
                                                                              }
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     }

                                                                     return true;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      this.kE();
                                                      if (var3 == 0) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3ksu6hqojimtp","sneSLi1ycvKiZAPCUx8Misn5fvkEkrk+sd0EDLSOvyc=",9180137868675230336,-5668953023273160456,-1773224753440649583,-584099413209532069>()) {
                                                            case 1569991000:
                                                               if (this.a.fR()) {
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s3t83jicwx50s9","Dwx8LAx8zntW/6hB5f/g9o0j82HzP+dh2cPvZKgEQBk=",-1141118234376535313,971292795844727592,-7361944741689626589,-4999221617695078980>()) {
                                                                     case -1287320513:
                                                                        if (this.a.d(var4, var5, var18 + var20 - 8.0F, var19 + 50.0F, var21 - 50.0F - 8.0F)) {
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s3lgi6lol3h3xc","R7EhJbE1ahNpuDp4OaO+PDLKUKQhMOgPhPbRyOwm6RE=",-2168006163954051375,7747816002480194918,2428174210060408163,-4923629868364495414>()) {
                                                                              case -673308078:
                                                                                 this.gw = true;
                                                                                 this.a.a(var5, var19 + 50.0F, var21 - 50.0F - 8.0F);
                                                                                 return true;
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

                                                      float var33 = var19 + 46.0F;
                                                      boolean var35 = var26.a(var4, var5, v(var18), var33, w(var20), this.a.r(), var3);
                                                      if (var35) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"sy0cznjare2t0","3Bf766BR8GYp5uQBZuawj7Xu6hrD8p+AAlW37PXa16A=",5118971766418825607,5263138915518739815,-3190496279719734524,5763495015809572347>()) {
                                                            case -1867999133:
                                                               if (var3 == 0) {
                                                                  label250:
                                                                  switch ((int)com.yiyiaddon.m.b.a<"selc7tkyov90i","hYidN9k1f7/cr5yFeTU9dtieEfOOy+/+SRywT6fUAKw=",9137963676118813743,-1553045045596247844,-6631669624599552400,-4948135484821876408>()) {
                                                                     case 977354118:
                                                                        this.gv = true;
                                                                        switch ((int)com.yiyiaddon.m.b.a<"sfnewbbmxaq1o","Kkv/NRlaWhVyndktyzdKV3TzYqh4R+TE64GOxpnYJKk=",-9106134912300950799,2008975630170752464,-7591852359881378714,-3884657118016595088>()) {
                                                                           case -1076746490:
                                                                              break label250;
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

                                                      if (var35) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"scf5mgiiijbcl","DgZh/PnIQ4StQozLWw3Lh87tZyMDSJb71+MxRGzfSu0=",5777733308986832739,1221757490832595654,-5061195807014325060,-5793084208563499716>()) {
                                                            case 110584758:
                                                               if (this.b() != var26) {
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s1ckw1i3q35adu","Ks9C8WHPwGbT58GCMgSj3WPK6/q1ANDRvlKOV3ZfmyQ=",5587475425234320676,-4743295703125027729,6470259382174000718,7917260041523328570>()) {
                                                                     case 77811897:
                                                                        this.kD();
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s101fqv5zyfnrz","LpoQ9ZvNMdi2hBQVQpbGr9s/qGcXglywOMnxBkRigCo=",8357804982833366242,-4264051092812638146,452737849000587099,-580959202365621086>()) {
                                                                           case 1169792068:
                                                                              return var35;
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

                                                      return var35;
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

                  return false;
               default:
                  throw null;
            }
         }
      }
   }

   @Override
   public boolean mouseDragged(MouseButtonEvent var1, double var2, double var4) {
      float var6 = this.b.b(var1.y(), this.height);
      float[] var7 = this.a();
      if (this.gw) {
         switch ((int)com.yiyiaddon.m.b.a<"s1aelgw2kohb76","hWJ51VWTE0gbMedgTMiKQ5dm4MFWZhGqqU97WxnDfRA=",5296714385454147293,-6424080908882161000,7521690564860376798,8923259348876676240>()) {
            case -1813525166:
               float var12 = var7[15];
               float var13 = var7[17];
               float var14 = var12 + 50.0F;
               float var15 = var13 - 50.0F - 8.0F;
               this.kE();
               this.a.b(var6, var14, var15);
               return true;
            default:
               throw null;
         }
      } else if (this.gv) {
         switch ((int)com.yiyiaddon.m.b.a<"sox1r9nhglaup","FEugW0nbF8lVRkGe0Meg5oyeTeTKTun9CVdxTBgeJI8=",6454950565463809487,-4401389640156482873,2600097695558469364,1786578794695849362>()) {
            case 839782826:
               float var8 = this.b.a(var1.x(), this.width);
               float var9 = var7[14];
               float var10 = var7[15];
               float var11 = var7[16];
               this.b().a(var8, var6, v(var9), var10 + 46.0F, w(var11), this.a.r());
               return true;
            default:
               throw null;
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean mouseReleased(MouseButtonEvent var1) {
      this.gv = false;
      this.gw = false;
      com.yiyiaddon.l.a.b[] var2 = this.b;
      int var3 = var2.length;
      int var4 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"sdivr1h5dtvnw","/vwQEpcLwxPZEuB1lOBHcF4N+dm+gOIM35ixCG2szrA=",8131610234076269810,-6468991829085510929,6503147581248952350,-768852287905403181>()) {
         case 969517631:
            while (var4 < var3) {
               switch ((int)com.yiyiaddon.m.b.a<"s26arfu36edf2e","XB6slwtTHIEabvex2dL3RKALxTEATu7CBEtWJ/psK78=",3411603411889702249,2690257725370004274,3813591956291806705,5314688222880370480>()) {
                  case 1102088646:
                     com.yiyiaddon.l.a.b var5 = var2[var4];
                     var5.jK();
                     var4++;
                     switch ((int)com.yiyiaddon.m.b.a<"s2i2l1chxkfcct","yxpHKg2ULHDm7gbV3Esz6WQBXJyOJZz+Py8xAvW9Kes=",-5495846660488355513,-4870731490737517354,5420110753900522908,-8850562299180142902>()) {
                        case 951614603:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.d.jK();
            this.e.jK();
            this.a.jK();
            com.yiyiaddon.l.f.a var6 = this.b();
            if (var6 != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s3jujeo5i02w77","+4hOIVYemxkT+Zco8mSbfggQjFpcAf/VEEvPMLPG6KI=",-9090496730686476576,-7170011480689507530,-8627317994084479734,-1271796627950888707>()) {
                  case 572850058:
                     var6.jU();
                     switch ((int)com.yiyiaddon.m.b.a<"s31j6in0gft3q6","RhlOd/ZB9r752JSs8xz0jb9oCGCDtR7IjSoacDOPyKQ=",7345527692157354358,-6024020314763110658,7413445016383573960,-3130944770308052233>()) {
                        case 596647788:
                           return false;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return false;
         default:
            throw null;
      }
   }

   @Override
   public boolean mouseScrolled(double var1, double var3, double var5, double var7) {
      if (this.gx) {
         switch ((int)com.yiyiaddon.m.b.a<"s2zklyqtwbvepo","4PDrNe3kLWzcIolDeaV3yzVTdb90UZK6Bh3rNi/ZwKY=",8598113092353380186,2755817097491983311,8015518878302842994,7471034960627446491>()) {
            case -272348504:
               return false;
            default:
               throw null;
         }
      } else {
         float var9 = this.b.a(var1, this.width);
         float var10 = this.b.b(var3, this.height);
         float[] var11 = this.a();
         float var12 = var11[14];
         float var13 = var11[15];
         float var14 = var11[16];
         float var15 = var11[17];
         if (var9 >= var12) {
            switch ((int)com.yiyiaddon.m.b.a<"s1n2c0sa4rnk37","y2hefjTq3nsrTvbki7CIcl8QJnq0vbFP9t3BAP1esEI=",1772119933035183658,-1098670813323583273,-8208217929969505993,-6062023995015701713>()) {
               case -1334362931:
                  if (var9 <= var12 + var14) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2els81pi1xakv","ZwBs4CAtSlPDq2jgENcgQt4x/FfVB0hqnRh8snEJ5wE=",8439202997944154113,6599564429119518193,5546562253637309378,4341597999936890840>()) {
                        case -584054826:
                           if (var10 >= var13) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2awuq0mpwn133","dLk+26/qSvdMCo4GpCbTVEfK2MB0QNVZonPJReeWOok=",-2340083037011405743,-902270121151837067,-4653561092403887015,-1973144816114512402>()) {
                                 case 1782656769:
                                    if (var10 <= var13 + var15) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s84t83ogcu8ii","AkysWV3hGgkrw6FxNPZxT46hTW0SVULEA3daYWBONk4=",-1690525255226079876,6997833314780559870,-9034713317765493755,5680500914685226863>()) {
                                          case -264098609:
                                             this.kE();
                                             this.a.a(var7, com.yiyiaddon.c.a.b);
                                             return true;
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

         return false;
      }
   }

   private void kJ() {
      com.yiyiaddon.l.i.d.aW(com.yiyiaddon.l.i.d.a().iterator().next().s());
      com.yiyiaddon.c.a.b = 1;
      com.yiyiaddon.c.a.d = true;
      com.yiyiaddon.c.a.a = 0.6F;
      com.yiyiaddon.c.a.c = 1343229972;
      com.yiyiaddon.c.a.b = 1.0F;
      com.yiyiaddon.l.d.d.y(false);
      com.yiyiaddon.c.a.e();
   }
}
