package com.yiyiaddon.l.j;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

public class d extends p {
   private final IntSupplier a;
   private final IntSupplier b;
   private final BooleanSupplier f;
   private final Paint E = new Paint().setAntiAlias(true);
   private final Paint F = new Paint().setAntiAlias(true);
   private final Paint G = new Paint().setAntiAlias(true);

   public d(IntSupplier var1) {
      this(var1, var1, () -> false);
   }

   public d(IntSupplier var1, IntSupplier var2, BooleanSupplier var3) {
      this.a = var1;
      this.b = var2;
      this.f = var3;
      this.G.setMode(PaintMode.STROKE);
      this.G.setStrokeWidth(1.4F);
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
      int var5 = this.a.getAsInt();
      this.E.setColor(a(var5, var4));
      this.G.setColor(a(com.yiyiaddon.l.i.c.a().uV, var4 * 0.24F));
      RRect var6 = RRect.makeXYWH(var2, var3, this.c(), this.d(), 8.0F);
      if (this.f.getAsBoolean()) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s3c7gjvl5s2xgm","tQuScvNdDTPiMAJEhpBddZhdw8esIaTotuZhNh1NXd4=",-5409054793302494598,7629422151425913315,4034324503636182436,-4181369189782739464>()) {
            case -1070540444:
               int var7 = this.b.getAsInt();
               this.F.setColor(a(var7, var4));
               var1.save();
               var1.clipRRect(var6);
               var1.drawRect(Rect.makeXYWH(var2, var3, this.c() * 0.5F, this.d()), this.E);
               var1.drawRect(Rect.makeXYWH(var2 + this.c() * 0.5F, var3, this.c() * 0.5F, this.d()), this.F);
               var1.restore();
               switch ((int)com.yiyiaddon.m.b.a<"s1e947fuleafn3","/8bX+AUcjj7rb6Bm+IZ+BtCTV5kuXvYOIV4LJOjIH9I=",7260887222859433320,8431953505645147118,-6243462971763640470,-8888383410424256768>()) {
                  case -2038225651:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var1.drawRRect(var6, this.E);
         switch ((int)com.yiyiaddon.m.b.a<"s2audc4cx94hhx","NcnslSQfhRFGU9PCcNJGY+H3D5QM6/QCHyzXAYlZf0k=",-5919331581985731050,7350979842975479769,5409805733284599078,1581405071129979873>()) {
            case -952488855:
               break;
            default:
               throw null;
         }
      }

      var1.drawRRect(var6, this.G);
   }
}
