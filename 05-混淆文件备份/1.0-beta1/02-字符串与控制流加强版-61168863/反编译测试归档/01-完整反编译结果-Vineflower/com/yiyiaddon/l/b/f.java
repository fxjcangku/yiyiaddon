package com.yiyiaddon.l.b;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.types.RRect;

public final class f implements g {
   private static final float fw = 34.0F;
   private static final float fx = 8.0F;
   private final com.yiyiaddon.l.g.a.d aa;
   private final Paint b = new Paint().setAntiAlias(true);
   private final Paint c = new Paint().setAntiAlias(true).setMode(PaintMode.STROKE).setStrokeWidth(1.2F);

   public f(com.yiyiaddon.l.g.a.d var1) {
      this.aa = var1;
   }

   @Override
   public float b() {
      return 34.0F;
   }

   @Override
   public void a(float var1) {
   }

   @Override
   public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      com.yiyiaddon.l.i.c var8 = com.yiyiaddon.l.i.c.a();
      RRect var9 = RRect.makeXYWH(var2, var3, var4, 34.0F, 8.0F);
      this.b.setColor(com.yiyiaddon.l.i.c.b(this.aa.ej(), var5));
      var1.drawRRect(var9, this.b);
      this.c.setColor(com.yiyiaddon.l.i.c.a(var8.uV, var5 * 0.24F));
      var1.drawRRect(var9, this.c);
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
      return false;
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5) {
      return false;
   }
}
