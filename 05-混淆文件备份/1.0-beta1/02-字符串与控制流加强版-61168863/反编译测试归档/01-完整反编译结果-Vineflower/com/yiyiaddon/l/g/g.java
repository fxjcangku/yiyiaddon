package com.yiyiaddon.l.g;

import com.mojang.blaze3d.opengl.GlDevice;
import com.mojang.blaze3d.opengl.GlTexture;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.GpuDeviceBackend;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import com.yiyiaddon.mixin.client.GpuDeviceAccessor;
import io.github.humbleui.skija.BackendRenderTarget;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.ColorType;
import io.github.humbleui.skija.DirectContext;
import io.github.humbleui.skija.Surface;
import io.github.humbleui.skija.SurfaceOrigin;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public final class g {
   private DirectContext a;
   private BackendRenderTarget a;
   private Surface a;
   private Canvas a;
   private h a;
   private int tT = -1;
   private int tU = -1;
   private int tW = -1;
   private boolean fS = false;
   private static DirectContext b;

   public static int ef() {
      Minecraft var0 = Minecraft.getInstance();
      GpuTexture var2 = var0.getMainRenderTarget().getColorTexture();
      if (var2 instanceof GlTexture) {
         switch ((int)com.yiyiaddon.m.b.a<"smhgjltytanh5","4ttUVTohOilgcv4M0/rAsjW2EW8DiDYD44yRZepKJ2I=",5001565430289712405,-4121581164387236934,2240201109100257670,-476119395013743444>()) {
            case 655080770:
               GlTexture var1 = (GlTexture)var2;
               GpuDeviceBackend var5 = ((GpuDeviceAccessor)RenderSystem.getDevice()).yiyiaddon$backend();
               if (var5 instanceof GlDevice) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3t5nc0mnmnw20","ZtaI7mxjdhYL0Gr2PxSQcSIdNj4xVdABrXTs2GlxlPo=",2730386615892345912,-7059318664470536367,-1070882428082392223,6167497352690815258>()) {
                     case -78463524:
                        GlDevice var3 = (GlDevice)var5;
                        return var1.getFbo(var3.directStateAccess(), var0.getMainRenderTarget().getDepthTexture());
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      int[] var4 = new int[1];
      GL30.glGetIntegerv(36006, var4);
      return var4[0];
   }

   public static DirectContext a() {
      if (b == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2sezav8gneds2","QFHLk7Ta42vNr01h14/ZQY18aYwx9YxtSjc3x5vZQk8=",-239589613115556277,-4526187946650527610,4064406491626025284,-2039559099484237112>()) {
            case 177219179:
               b = DirectContext.makeGL();
               switch ((int)com.yiyiaddon.m.b.a<"s38s6nuvylcba3","Q1UxxzSFFaZRYkczANaFkbj4pT6dOghHHZQyVlQTFC0=",3687512779009664119,-8883671158059144368,7731939400690433945,-2784204919905426759>()) {
                  case 732834345:
                     return b;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return b;
      }
   }

   public Canvas a() {
      return this.a(0);
   }

   public Canvas a(int var1) {
      if (this.fS) {
         return this.a;
      }

      Window var2 = Minecraft.getInstance().getWindow();
      int var3 = Math.max(1, var2.getWidth());
      int var4 = Math.max(1, var2.getHeight());
      this.kq();
      this.a.jg();

      try {
         this.d(var3, var4, var1);
         if (this.a != null && this.a != null) {
            this.a.resetGLAll();
            GL11.glDisable(2884);
            GL11.glDisable(2929);
            GL11.glDisable(3089);
            GL11.glDisable(2960);
            GL11.glDepthMask(false);
            GL11.glColorMask(true, true, true, true);
            GL11.glEnable(3042);
            GL11.glBlendFunc(1, 771);
            GL11.glViewport(0, 0, var3, var4);
            GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
            this.a.restoreToCount(1);
            this.a.resetMatrix();
            this.a.save();
            this.a.scale(var2.getGuiScale(), var2.getGuiScale());
            this.fS = true;
            if (var1 == ef()) {
               c.a().a(this.a);
            }

            return this.a;
         } else {
            this.a.kr();
            return null;
         }
      } catch (RuntimeException var6) {
         this.a.kr();
         throw var6;
      }
   }

   public void km() {
      if (this.fS && this.a != null) {
         try {
            this.a.restore();
            this.a.flushAndSubmit(this.a);
         } finally {
            this.fS = false;
            this.a.kr();
         }
      }
   }

   public boolean gg() {
      return this.fS;
   }

   public boolean gh() {
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3vkos6hvvaqwe","wBlzfq+ZxT8Zw+aCNwkIHHnZ44NyBdllHjGNFfzBL3w=",2561100777304785436,3623947355078972513,8032764088317535417,-7168992791202707663>()) {
            case 716925922:
               switch ((int)com.yiyiaddon.m.b.a<"s378bqxpkiribi","Uemg+Lv0X+Pj9ggqlk/GGVeXjbKLZbYp6W7nMxVUynM=",5558683336088457382,-4343006072777076345,1845785759181164046,5693929817112536860>()) {
                  case -1462336040:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s3gxa81vwe6yq5","cs1RSGxGow4TMiOydETENp/K5BJrNgUYKK+8hwzK2wc=",329379637812272154,7957624193654664858,7886511703406538101,-2002724134113114650>()) {
            case -1865443349:
               return false;
            default:
               throw null;
         }
      }
   }

   public DirectContext b() {
      return this.a;
   }

   public void kn() {
      this.fS = false;
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2abxjxy0zctop","mBb0esAEBhXhwMcP1tNxmMOnXTMmVsBlxEKL48Yfd/U=",-7968504663740817660,-2700530485780877201,-2944322038985661999,-8090731198138064105>()) {
            case -1451560332:
               this.a.restoreToCount(1);
               this.a.resetMatrix();
               switch ((int)com.yiyiaddon.m.b.a<"s1a6twrz1er30d","Tm47B6esrEW9fiaae7IW/ysoBDWD0VezJKhaRgp29DM=",-6864296579582346999,-2354190781788135479,4521892646137848489,1032850448811216614>()) {
                  case 433893944:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public void n(Runnable var1) {
      this.kq();
      this.a.jg();

      try {
         var1.run();
      } finally {
         this.a.kr();
      }
   }

   public void ko() {
      if (this.fS) {
         this.km();
      }

      h var1 = this.a;
      if (var1 != null) {
         var1.jg();
      }

      try {
         this.kn();
         if (this.a != null) {
            this.a.close();
            this.a = null;
         }

         if (this.a != null) {
            this.a.close();
            this.a = null;
         }

         this.a = null;
         this.a = null;
         this.tT = -1;
         this.tU = -1;
         this.tW = -1;
      } finally {
         if (var1 != null) {
            var1.kr();
         }

         this.a = null;
      }
   }

   private void d(int var1, int var2, int var3) {
      this.kp();
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sp6hz1v31995u","37ctTWw9OAb6zgqFdCSfdWZyHKGIcE6YJqrgGohg79Y=",-8236912256649126873,-5797811192455341263,-6781239246099687607,4416457520847387704>()) {
            case 1042769219:
               if (var1 == this.tT) {
                  switch ((int)com.yiyiaddon.m.b.a<"s5mw4d9jd7wzs","OC6RxWW3LezuH5KODgnvHYVKO7vxJIEITdhbNKZeqvM=",8827638312931410951,-2952428163097821789,774406652623193407,7123581966038429548>()) {
                     case 234356829:
                        if (var2 == this.tU) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1kue068j6za38","wlOucBGuLoW5wcksra/GRoD5lHjOuG30M8gQjv8TjXU=",-6167762729825096319,3695307816900509455,3327256643765841518,5014210982054762846>()) {
                              case -702205611:
                                 if (var3 == this.tW) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3q68vwcx1h2bq","cBJbN72J58Vu15mRW/7FzsyW9bZvDFVaDNlbCjAAb/M=",151044292471684156,2005072114890005567,-1198200108077853009,-593527783449907206>()) {
                                       case 1219193509:
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

      if (this.a != null) {
         label37:
         switch ((int)com.yiyiaddon.m.b.a<"soe2t5ijbuig5","ibbyuchHewiLsqCCk6jFlrCOtjF6U9oK7m0X5vIi6bY=",170715505686876724,4689899252569205158,3448787157437619172,-7378465623968594481>()) {
            case 1351982671:
               this.a.close();
               this.a = null;
               switch ((int)com.yiyiaddon.m.b.a<"s1f6omitb4m3t7","RAT0VfK3cuFTcnIWRJvjmJsOZLqIcOw6vfRf1ltp5yg=",-5877634589502048738,1544607045409718039,432265429069880125,6264791981926731359>()) {
                  case 144791632:
                     break label37;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (this.a != null) {
         label32:
         switch ((int)com.yiyiaddon.m.b.a<"s6qfdbgnby2el","Y3/cArChhRRf5BfQOZwf5wP1zLtVZpf4NAEQ4RP6UV0=",4327617466050078894,5553249275805250893,-2324285591572105859,2599098397299879442>()) {
            case -2147113101:
               this.a.close();
               this.a = null;
               switch ((int)com.yiyiaddon.m.b.a<"s43jednk1rhs0","f7qyM4kxnDJxpgzKf3KKilGgbocPdQX4mGxpB6HYXdc=",-7723805898866289328,6207021936835434287,323682528144750696,4777047866964229910>()) {
                  case 1904404568:
                     break label32;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.a = BackendRenderTarget.makeGL(var1, var2, 0, 8, var3, 32856);
      this.a = Surface.wrapBackendRenderTarget(this.a, this.a, SurfaceOrigin.BOTTOM_LEFT, ColorType.RGBA_8888, ColorSpace.getSRGB());
      this.a = this.a.getCanvas();
      this.tT = var1;
      this.tU = var2;
      this.tW = var3;
   }

   private void kp() {
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2mnuzy1qjemhc","vxBCPwfqOH/VaBqC+mQcyUi8MUNcHr0YxzOVxgfEJwo=",1363302661287493307,4093685213688991846,-932529336048366905,-3850320615274406913>()) {
            case -1362453197:
               return;
            default:
               throw null;
         }
      } else {
         this.a = a();
      }
   }

   private void kq() {
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sueno2b5adjos","wayhRCPr5JaGggYBPnvL/pxK0gJPYxxzBZvGI5s94ug=",-1364955208353652166,-5866244765614260518,-8337602958080930710,7877881133492546282>()) {
            case -816794460:
               return;
            default:
               throw null;
         }
      } else {
         this.a = new h(eg());
      }
   }

   private static int eg() {
      int[] var0 = new int[1];
      int[] var1 = new int[1];
      GL30.glGetIntegerv(33307, var0);
      GL30.glGetIntegerv(33308, var1);
      return var0[0] * 100 + var1[0] * 10;
   }
}
