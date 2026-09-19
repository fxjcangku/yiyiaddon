package com.yiyiaddon.l.j;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;
import java.util.function.Supplier;

public class g extends p {
   private final Supplier<String> H;
   private final Runnable p;
   private final com.yiyiaddon.l.a.b j = new com.yiyiaddon.l.a.b();
   private final Paint J = new Paint().setAntiAlias(true);
   private String GO = (String)com.yiyiaddon.m.b.a<"s1bpv2y9nlaps7","ZOM/rvVgN9Am1hiCaHvzfXV/nME26uDI/dSdKQ==",-7418055251149286015,-1298858273138818295,-8577679715519751620,-4913357910592483136>();
   private float nl = 0.0F;
   private float gk = 150.0F;

   public g(Supplier<String> var1, Runnable var2) {
      this.H = var1;
      this.p = var2;
   }

   public g a(float var1) {
      this.gk = Math.max(48.0F, var1);
      return this;
   }

   @Override
   public float c() {
      return this.gk;
   }

   @Override
   public float d() {
      return 24.0F;
   }

   @Override
   public void a(float var1) {
      this.j.a(var1);
   }

   @Override
   public void b(Canvas var1, float var2, float var3, float var4) {
      com.yiyiaddon.l.i.c var5 = com.yiyiaddon.l.i.c.a();
      this.J.setColor(a(var5.vd, com.yiyiaddon.l.i.c.y(var4)));
      String var6 = this.H.get() + "";
      if (!var6.equals(this.GO)) {
         label23:
         switch ((int)com.yiyiaddon.m.b.a<"s24gt7q9raugpq","2G6yc+k3vPKs1tbymFcxOAxGxggXKm9D1xFX7NH0TTQ=",4855514342500001479,-3749226662266509932,6677906443449368304,-6474186501902370957>()) {
            case -410108968:
               this.GO = var6;
               this.nl = com.yiyiaddon.l.g.a.b(var6, 12.0F);
               switch ((int)com.yiyiaddon.m.b.a<"s32me70bmo2qhr","fMHp2cPzq/0paronWbzyNzmn6ACtYNu+ScueVc4PACE=",5632718594253025786,4675706572676287287,-6331913930571516478,-8445483163683538655>()) {
                  case 1886924437:
                     break label23;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      boolean var7 = this.j.a(var1, var2, var3, this.c(), this.d());
      var1.drawRRect(RRect.makeXYWH(var2, var3, this.c(), this.d(), 6.0F), this.J);
      com.yiyiaddon.l.g.a.b(var1, var6, var2 + (this.c() - this.nl) / 2.0F, var3 + 16.0F, 12.0F, a(var5.vh, var4));
      if (var7) {
         switch ((int)com.yiyiaddon.m.b.a<"s2b1l9omto911y","RyXOO1lDEbrrweIRrpoh8IA3l53o6GagUmlOKU+JXKc=",-5237983931774833208,-8729380226927960590,2772173009004174826,-1788070578688105596>()) {
            case 1109019482:
               var1.restore();
               switch ((int)com.yiyiaddon.m.b.a<"s35d5d3vrnk4v2","9AzCfFduCmHu7MLI/OqPzDwGcJN9ctJMJeg3uD0ekes=",6553081314896787287,2354029790221448080,1416439813776533131,-312078719746508512>()) {
                  case -1240360641:
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
   public boolean cB() {
      if (!this.j.fP()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2wf8jybfylwba","dSTALIh+GlGJgVdMJwMcHnKH6oL72ZGoZ8ILXy9g7ig=",-7417332140762163282,-6276738248567992919,161316752648106052,2020106079813173533>()) {
            case 1103820619:
               switch ((int)com.yiyiaddon.m.b.a<"s10uj40021z21m","J3UNgnbIWKPdCY4sRSngGEg/cDEHhQy1VccgYI9vTJ8=",-9126346559098424745,1888721934454324071,-3698617295434452968,-4660611551750764644>()) {
                  case -1257201282:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2mzt169wbovwf","ej5UuycdTlxlqFatxoy1rns8Imx54Dqqsre9JPouIls=",613982537797333249,-2298370645194849864,4808690764639186919,8811964863415840790>()) {
            case -1654438227:
               return false;
            default:
               throw null;
         }
      }
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, int var5) {
      if (var5 != 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s3h9behlpmky47","d1q6U9er06stZLptEFM4r17cl2Tk6j+8Cykmc527jeQ=",4233497672677057168,3437093425798657146,-7059303697603765424,8202305849100811348>()) {
            case -438643414:
               return false;
            default:
               throw null;
         }
      } else {
         this.j.jL();
         this.p.run();
         return true;
      }
   }
}
