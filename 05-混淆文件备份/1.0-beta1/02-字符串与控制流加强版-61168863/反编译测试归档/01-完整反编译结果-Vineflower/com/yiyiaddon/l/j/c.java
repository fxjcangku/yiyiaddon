package com.yiyiaddon.l.j;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import net.minecraft.client.Minecraft;

public final class c extends p {
   private static final float nh = 100.0F;
   private static final float ni = 24.0F;
   private static final float nj = 8.0F;
   private static final float nk = 4.0F;
   private final String GM;
   private final com.yiyiaddon.l.g.a.d ae;
   private final Runnable o;
   private final Paint C = new Paint().setAntiAlias(true);
   private final Paint D = new Paint().setAntiAlias(true).setMode(PaintMode.STROKE).setStrokeWidth(1.4F);

   public c(String var1, com.yiyiaddon.l.g.a.d var2) {
      this(var1, var2, null);
   }

   public c(String var1, com.yiyiaddon.l.g.a.d var2, Runnable var3) {
      this.GM = var1;
      this.ae = var2;
      this.o = var3;
   }

   @Override
   public float c() {
      return 100.0F;
   }

   @Override
   public float d() {
      return 24.0F;
   }

   @Override
   public void b(Canvas var1, float var2, float var3, float var4) {
      com.yiyiaddon.l.i.c var5 = com.yiyiaddon.l.i.c.a();
      RRect var6 = RRect.makeXYWH(var2, var3, 100.0F, 24.0F, 8.0F);
      this.C.setColor(com.yiyiaddon.l.i.c.b(this.ae.ej(), var4));
      var1.drawRRect(var6, this.C);
      this.D.setColor(a(var5.uV, var4 * 0.24F));
      var1.drawRRect(var6, this.D);
      if (this.ae.gj()) {
         switch ((int)com.yiyiaddon.m.b.a<"s6is16uj4hxxw","NfpVdwzE36AMm9Gx8Ap1k4YXb6OdAU+TdAvhJu0e+Mw=",-2394703235284275994,4970281427261726175,-1351313665128275821,-2983291629182235420>()) {
            case 595368552:
               this.C.setColor(a(var5.vB, var4));
               var1.drawRect(Rect.makeLTRB(var2 + 100.0F - 4.0F - 5.0F, var3 + 5.0F, var2 + 100.0F - 5.0F, var3 + 4.0F + 5.0F), this.C);
               switch ((int)com.yiyiaddon.m.b.a<"s2wv6587ctk6sx","HaaiMYzejVNRi/SZMulk7QPaN2OA9VTUfLICocUWCGY=",2401260230159487086,-1895280599594350362,927474979771020558,-1434850308253510301>()) {
                  case 1481528951:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, int var5) {
      Minecraft var6 = Minecraft.getInstance();
      if (var6 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2vltkpyygorts","CT1FItr/QyelJpxbWjqesYaTsfEL8Roy+T2HRpD56P4=",4238664491981194583,2886636780972185779,5453039295828503895,7747723635769310725>()) {
            case 551976855:
               return false;
            default:
               throw null;
         }
      } else {
         var6.setScreen(new com.yiyiaddon.l.h.b(this.GM, this.ae, var6.screen, this.o));
         return true;
      }
   }
}
