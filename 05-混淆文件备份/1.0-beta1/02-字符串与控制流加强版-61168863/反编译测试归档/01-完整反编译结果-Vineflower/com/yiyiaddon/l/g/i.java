package com.yiyiaddon.l.g;

import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public abstract class i extends Screen {
   protected final Screen e;
   private boolean gb;
   private int ub;
   private int uc;
   private float kp;

   protected i(Component var1, Screen var2) {
      super(var1);
      this.e = var2;
   }

   @Override
   public void extractRenderState(GuiGraphicsExtractor var1, int var2, int var3, float var4) {
      super.extractRenderState(var1, var2, var3, var4);
      this.ub = var2;
      this.uc = var3;
      this.kp = var4;
      this.gb = true;
      if (this.gi()) {
         switch ((int)com.yiyiaddon.m.b.a<"ss2z8fxaz4qte","5MphscasuV1mwucUNU3rHBpsiDEtPHQqkHSb48GQkaU=",3809038163795757568,8940624912954113076,3667475173882038534,-2172817620872773398>()) {
            case 319038681:
               c.a().a(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s38s2jp48rq4h0","mXmZLFgGH+1qi4+r8u7kjLtrf4NlrPzdaQykkLLnxrc=",-8109609282939269718,-3295689495592406586,-1487027768892878189,7555999110124866682>()) {
                  case -1666038288:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   void a(AbstractWidget var1) {
      this.removeWidget(var1);
      this.addRenderableOnly(var1);
   }

   void b(AbstractWidget var1) {
      this.removeWidget(var1);
   }

   protected boolean gi() {
      return true;
   }

   public final void ks() {
      if (this.gb) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ehfztkhr84hu","5jhpz3VH91Tzw1D/MjvAhg2uDW4yoCoqAFBhZqPn1ro=",-7185957476863444804,-3523892601047374944,-8558464154474503178,-8119309421179500969>()) {
            case -1359632698:
               if (this.minecraft != null) {
                  label20:
                  switch ((int)com.yiyiaddon.m.b.a<"s3vr6opcvxvw3c","GvmzZHqyawshoukxiJQc9t3CK81T5UnB8kRzfNBamgo=",-448276472444216898,-6535629127483672684,5889098841241166703,4020986973453274941>()) {
                     case 718521093:
                        if (this.minecraft.screen == this) {
                           this.gb = false;
                           c.a().ki();
                           this.a(this.width, this.height, this.ub, this.uc, this.kp);
                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2g56lvinl2wwx","vJmX/gMtopshGcxMIC4dYpsHv1o7hva6V/x57D+YpSs=",6438764851086397161,5686610678900092254,-2955409706989322746,1143855384039810801>()) {
                           case 2101809612:
                              break label20;
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

      this.gb = false;
   }

   protected abstract void a(int var1, int var2, int var3, int var4, float var5);

   @Override
   protected void init() {
      super.init();
      this.kt();
   }

   @Override
   public void resize(int var1, int var2) {
      super.resize(var1, var2);
      this.kt();
   }

   protected void kt() {
   }

   @Override
   public void extractBackground(GuiGraphicsExtractor var1, int var2, int var3, float var4) {
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sihpv88knrxgk","Sp0Rcq3OALkkMFQoCefc59rOJj3FrlgXTHb884FRUgQ=",255122477530656678,8614582214903630067,-7091390811402246743,-3711748297697465123>()) {
            case 1728866802:
               if (this.minecraft.level == null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ev25xjpmt6qp","bFgmvRlXFVTre7xT64nPwjGwiM6ai7fPEoTyggfljC0=",-5665995202068932601,-2882451133774298703,-1381842876934492402,-437803520571557427>()) {
                     case -661768761:
                        this.extractPanorama(var1, var4);
                        switch ((int)com.yiyiaddon.m.b.a<"s4r8daf5ruwj6","q+/doY3/hJMCaB5Il6kZ1DRg9k7DWicBuiMhsL7if8I=",-1910744001062902810,-68707905035223056,1053122791152523412,6566513138175637024>()) {
                           case 710931592:
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

      this.extractTransparentBackground(var1);
      switch ((int)com.yiyiaddon.m.b.a<"s4gu3t1u3xh85","zJq+VoHcl/Pwl2hqVK6LDNtSGlaVOLqycae4yH4WKjE=",8992282042203678882,-4222950017297903899,-7969473221483775473,-4106332037986397969>()) {
         case 1391360174:
            return;
         default:
            throw null;
      }
   }

   @Override
   public boolean isPauseScreen() {
      return false;
   }

   @Override
   public void onClose() {
      this.ku();
   }

   protected void ku() {
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"ssgp7yfr7rs95","+p9pQssYBqnWxGRScrSbZ9rhJJnlIIxn4W3nQc/gzZw=",966215134530470251,6131917743851918653,-6864188817391236724,5076661740456639029>()) {
            case 414815011:
               this.minecraft.setScreen(this.e);
               switch ((int)com.yiyiaddon.m.b.a<"s1phymi3ks974","6qqRzKzXKl6Vufw+mnK4svfQ2PkWdbLZEsebmSjTNq0=",6909505102056468940,-4802201331353358442,-2701237556096095056,3775490993672593281>()) {
                  case -1383302281:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }
}
