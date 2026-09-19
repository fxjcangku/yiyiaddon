package com.yiyiaddon.l.b;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;

public final class s {
   private static final float ho = 4.0F;
   private static final float hp = 2.0F;
   private static final float hq = 6.0F;
   private static final float hr = 20.0F;
   private static final float hs = 16.0F;
   private final com.yiyiaddon.l.a.c a = new com.yiyiaddon.l.a.c();
   private final Paint k = new Paint().setAntiAlias(true);
   private final Paint l = new Paint().setAntiAlias(true);
   private float ht;
   private float hu;
   private float hv;
   private float hw;
   private float hx;

   public void e(float var1, float var2) {
      this.hu = Math.max(0.0F, var1);
      this.hv = Math.max(0.0F, var2);
      this.hw = Math.max(0.0F, this.hu - this.hv);
   }

   public float r() {
      return this.a.r();
   }

   public float q() {
      return this.a.q();
   }

   public float B() {
      return this.hw;
   }

   public void d(float var1) {
      this.ht = var1;
   }

   public void e(float var1) {
      this.ht = var1;
      this.a.e(var1);
   }

   public void a(float var1) {
      this.a.d(this.ht);
      this.a.b(var1, 0.0F, this.hw);
      this.ht = this.a.q();
   }

   public void a(double var1, float var3) {
      this.d(this.ht + (float)(-var1 * 16.0 * Math.max(0.2F, var3)));
   }

   public boolean fR() {
      if (this.hu > this.hv) {
         switch ((int)com.yiyiaddon.m.b.a<"s3d635306jrhfh","U8rB8Ehwz/V9nQ5GQktYB4w8FNkMiaw2rGGrT45sNDg=",632414181607769507,3257506624378222879,-4360478776074366794,-6125980498573861453>()) {
            case 1331191055:
               switch ((int)com.yiyiaddon.m.b.a<"s4dyztoeh2apj","8XvYVfd4ZMgmgDmuplMuVL33B+DWuWDrxCqGR6hZDog=",-9140474033711708237,4234824027629268654,8961368929682140496,3198927996425657057>()) {
                  case 595837119:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s25d1lo60abx3s","BiAQcKc6WZDcwXhimgTsobCiRBqVev8WqeXmUuTMokU=",7567132394171547026,-7232779212122569524,859226395905367132,-5424036606289387845>()) {
            case -1451275234:
               return false;
            default:
               throw null;
         }
      }
   }

   public float l(float var1) {
      if (!this.fR()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3cbvyukfvu45p","KaLh29/yfhLdltfSOiqtbMihJgN4WpKE/f3IpC+1ZMc=",-6962013171318457613,4164172686829680954,-6157122977486100694,8588187923310081519>()) {
            case 1263803289:
               return var1;
            default:
               throw null;
         }
      } else {
         return Math.max(20.0F, var1 * this.hv / this.hu);
      }
   }

   public float c(float var1, float var2, float var3) {
      float var4 = this.l(var2);
      float var5 = Math.min(1.0F, var3 / Math.max(1.0F, this.hw));
      return Math.min(var1 + (var2 - var4) * var5, var1 + var2 - var4);
   }

   public boolean d(float var1, float var2, float var3, float var4, float var5) {
      if (var1 >= var3 - 6.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s3mol00zape1tq","vT4zwTbjEQ//J+OBvqKpAvTDKa53/CjFu3V4BTSFR7E=",-4622962511747106162,3009452890295452871,-8856894743450414417,2392270012749816169>()) {
            case -315514189:
               if (var1 <= var3 + 4.0F + 6.0F) {
                  switch ((int)com.yiyiaddon.m.b.a<"s279at4yc1sfxy","gPWj95Q152Occ9usOFBxzsL3EA87QMQjhah1AyipXOo=",-8257742709958932650,3018645852334986591,-2450554143588299269,-4493250991970080913>()) {
                     case -1474934105:
                        if (var2 >= var4) {
                           switch ((int)com.yiyiaddon.m.b.a<"s31d894trit0zq","rniZzA5LCQA0JIh101Vu+dXRkLkx3IBi96NwAlYEbhY=",-2503908219188096188,5588064106559113331,-831467294420603047,6134405541894114109>()) {
                              case -501737544:
                                 if (var2 <= var4 + var5) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1fq24utqt751r","FcByaKeBMoiIdKuQXfpF+OJBBo3Eq8a7DUAaDI5W3Rw=",-2886808019048811545,2973330470369460152,-722454316653074245,-7421996699811434621>()) {
                                       case -1471191318:
                                          switch ((int)com.yiyiaddon.m.b.a<"s35tq1ulslv80b","+WRfrkqLEtaHov38cKThmzg8/DskFjgTq4CE3GUILy8=",8216130813242624894,-8731106694461951212,939850674863753511,-6839973121195297086>()) {
                                             case -1190987119:
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

      switch ((int)com.yiyiaddon.m.b.a<"s1lcphen68mx1j","qx4/9+J8M1nuyMqPtV8fA39N5qIoq9l0I91nBYtCg/s=",4561522509603810210,7454530199743086814,7961188427586915788,-7138127574066666625>()) {
         case 1432341833:
            return false;
         default:
            throw null;
      }
   }

   public void a(float var1, float var2, float var3) {
      float var10001;
      label27: {
         float var4 = this.c(var2, var3, this.q());
         float var5 = this.l(var3);
         if (var1 >= var4) {
            switch ((int)com.yiyiaddon.m.b.a<"s1oddaf9owv3ut","1JXx0dTugpvO9JZqZ/oCt9+BtIlUa/JqaKBOpk3K1QU=",1666896113331804222,-3595188341752313962,-2930458405555937072,1838782122486478747>()) {
               case -553292614:
                  if (var1 <= var4 + var5) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2g9mpkhevo92l","S8zEBBFhUjikC9QVrMgEgmPIwYuREHLUsMsafPJWOeA=",-8591123294586275199,8097480797285693439,11475886000297159,8864422900167458338>()) {
                        case -78609351:
                           var10001 = var1 - var4;
                           switch ((int)com.yiyiaddon.m.b.a<"s321apx7z87jjr","VM6Kqpb9OJjj10bA1NqjS8Ya1F3PbP8mqOcn9m7KIPY=",3950968393219250330,-2064182746297216926,7645800350601954873,-2253334390737139896>()) {
                              case 45467434:
                                 break label27;
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

         var10001 = var5 * 0.5F;
         switch ((int)com.yiyiaddon.m.b.a<"sp6tw0if7io99","fmVNCkjEmun87MTzd6eakLbW7eFNBmFEVMPF7O388fM=",-4718122419443895875,2267520154175338423,4344769107303964997,4759070034324841034>()) {
            case -1769526659:
               break;
            default:
               throw null;
         }
      }

      this.hx = var10001;
      this.b(var1, var2, var3);
   }

   public void b(float var1, float var2, float var3) {
      float var4 = Math.max(1.0F, var3 - this.l(var3));
      float var5 = Math.max(var2, Math.min(var1 - this.hx, var2 + var4));
      this.e(this.hw * ((var5 - var2) / var4));
   }

   public void b(Canvas var1, float var2, float var3, float var4, float var5, com.yiyiaddon.l.i.c var6) {
      if (!this.fR()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3pu0cijd4gozu","2hKHU5SoJWS9Sh1QlKIhO+cI442bWTY/JvqxQdzx3uI=",-1920855340587447392,5963428953666706971,-384893771297394823,9178283349978684296>()) {
            case -205099936:
               return;
            default:
               throw null;
         }
      } else {
         float var7 = this.l(var4);
         float var8 = this.c(var3, var4, this.a.r());
         this.k.setColor(j.a(var6.vm, var5 * 0.5F));
         var1.drawRRect(RRect.makeXYWH(var2, var3, 4.0F, var4, 2.0F), this.k);
         this.l.setColor(j.a(var6.vn, var5));
         var1.drawRRect(RRect.makeXYWH(var2, var8, 4.0F, var7, 2.0F), this.l);
      }
   }
}
