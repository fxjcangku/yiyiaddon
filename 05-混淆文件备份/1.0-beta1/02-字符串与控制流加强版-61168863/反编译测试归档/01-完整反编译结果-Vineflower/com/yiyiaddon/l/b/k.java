package com.yiyiaddon.l.b;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.PaintMode;
import io.github.humbleui.skija.PaintStrokeCap;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import java.awt.Color;

public final class k implements g {
   private static final float fP = 24.0F;
   private static final float fQ = 6.0F;
   private static final int ti = 72;
   private static final float fR = 0.6F;
   private final com.yiyiaddon.l.g.a.d ab;
   private final Paint f = new Paint().setAntiAlias(true);
   private final Paint g = new Paint().setAntiAlias(true).setMode(PaintMode.STROKE).setStrokeCap(PaintStrokeCap.BUTT);

   public k(com.yiyiaddon.l.g.a.d var1) {
      this.ab = var1;
   }

   @Override
   public float b() {
      return 24.0F;
   }

   @Override
   public void a(float var1) {
   }

   @Override
   public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      com.yiyiaddon.l.i.c var8 = com.yiyiaddon.l.i.c.a();
      RRect var9 = RRect.makeXYWH(var2, var3, var4, 24.0F, 6.0F);
      var1.save();
      var1.clipRRect(var9);
      float var10 = var4 / 72.0F;
      int var11 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s2pts2n53e6ydn","YrI2ngV8WbyNUw6TYIY43+pLBgLrYSfWd6acfy1usDc=",3398780779385935498,-1890654956956416316,-8381182265180454370,8314900740116513209>()) {
         case -24412488:
            while (var11 < 72) {
               switch ((int)com.yiyiaddon.m.b.a<"s2iujh8rpp2kkt","6m8K1EjB3o3YT1GPJ1AuC1uJ9c/aYxTn5LX6qu2NCoM=",-902248558946666629,1424391770966222859,-5394037323180555714,-1201998287043042411>()) {
                  case 1241038397:
                     float var12 = (var11 + 0.5F) / 72.0F;
                     int var13 = Color.HSBtoRGB(var12, 1.0F, 1.0F) & 16777215;
                     this.f.setColor(com.yiyiaddon.l.i.c.a(var13, var5));
                     var1.drawRect(Rect.makeLTRB(var2 + var11 * var10, var3, var2 + (var11 + 1) * var10 + 0.6F, var3 + 24.0F), this.f);
                     var11++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1y86b1l6pzi15","AodfTzinbzz7ORv8uw/7TGOJxR0/Z5rqfKu4Yx/l9+A=",-8834925096891843637,-4457810797108762693,4422440400461198949,-4518051581187219364>()) {
                        case -1204284179:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var1.restore();
            float var14 = var2 + this.ab.W() * var4;
            this.g.setStrokeWidth(3.5F);
            this.g.setColor(com.yiyiaddon.l.i.c.a(var8.uY, var5));
            var1.drawLine(var14, var3 - 1.0F, var14, var3 + 24.0F + 1.0F, this.g);
            this.g.setStrokeWidth(1.8F);
            this.g.setColor(com.yiyiaddon.l.i.c.a(var8.vB, var5));
            var1.drawLine(var14, var3 - 1.0F, var14, var3 + 24.0F + 1.0F, this.g);
            return;
         default:
            throw null;
      }
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
      return this.a(var1, var3, var5);
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, float var5) {
      return this.a(var1, var3, var5);
   }

   private boolean a(float var1, float var2, float var3) {
      float var4 = (var1 - var2) / Math.max(1.0F, var3);
      if (var4 < 0.0F) {
         label43:
         switch ((int)com.yiyiaddon.m.b.a<"s17r2ew5o3svkz","2eMg+CEpc5y+XQFMvT0Mz/8qyPrD3apj8zdyeCN+XkQ=",-6268587752522215137,-2587534936895170938,8746423502030628205,-898723611501401530>()) {
            case -1374884340:
               var4 = 0.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s2yv303ow3us51","ceLlE/qbV4noyeyiRFXn3HPTTm/kzyFYJxp2v6qaCrw=",-2030678613053722828,-4660395563827461842,-7823576476469574968,-669294030335752315>()) {
                  case -1884038964:
                     break label43;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (var4 > 1.0F) {
         label38:
         switch ((int)com.yiyiaddon.m.b.a<"s180vu0wjlo49d","d4KEUUR7TwxTVgBbwhQap5zFHFktH7hNmRThgk+L9BI=",3775879790459333621,-3752957176975265548,-5105128789531091267,7175033630448633889>()) {
            case -1212049027:
               var4 = 1.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s30pdbjlxbodw4","s7guY/QevLtd7EMk/yp3nOPnQ9YTt7Ftt3h3s7Ewe6Q=",1793495392715789365,-6586009705260746368,7282471590988658575,-6690200130217634377>()) {
                  case 750244114:
                     break label38;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      float var5 = this.ab.X();
      float var6 = this.ab.Y();
      if (var5 < 0.01F) {
         label33:
         switch ((int)com.yiyiaddon.m.b.a<"s29gi5i1ckhoty","znFMkwo4TQbPeyLqgvuolc81GluL98yg7Bh55ByFlkI=",-3367024205633654258,-6813458718722224125,1195243037337722693,-8423301133150040847>()) {
            case -583773970:
               var5 = 1.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s1554y3an0qygh","zenYaHSAhTNztcEAaHWIMGnPTOJYOEVH75Y1uijfxkY=",6530981413421162472,6913180109595910193,-7148190283264918618,-8295791330548167407>()) {
                  case 1077546663:
                     break label33;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (var6 < 0.01F) {
         label28:
         switch ((int)com.yiyiaddon.m.b.a<"sebafvi63928h","RMt7WgQtUXows99WzkznPiHlXCXNV1R5VgLL3KrpN/A=",2502835605413478294,4907513470986726024,4394190153500846624,-3443701972353946379>()) {
            case 1434197882:
               var6 = 1.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s3ul5qc9439azk","hEsrJ2GQcuAnWvOTxbpgKpUtoN4hZ0Kc4JYL9XucRMc=",6630265320298883044,5478188645315290387,3336717786919799099,-935565023798683628>()) {
                  case 995520482:
                     break label28;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.ab.a(var4, var5, var6, false);
      return true;
   }
}
