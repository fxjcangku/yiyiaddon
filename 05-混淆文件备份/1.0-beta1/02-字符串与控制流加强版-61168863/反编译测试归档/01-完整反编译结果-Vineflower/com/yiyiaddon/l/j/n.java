package com.yiyiaddon.l.j;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class n extends p {
   private static final float ok = 44.0F;
   private static final float ol = 24.0F;
   private static final float om = 20.0F;
   private static final float on = 22.0F;
   private static final float oo = 2.0F;
   private final Supplier<Boolean> O;
   private final Consumer<Boolean> y;
   private final com.yiyiaddon.l.a.d g = com.yiyiaddon.l.a.d.a(0.22F);
   private boolean h;
   private float op = -1.0F;
   private final Paint T = new Paint().setAntiAlias(true);
   private final Paint U = new Paint().setAntiAlias(true);

   public n(Supplier<Boolean> var1, Consumer<Boolean> var2) {
      this.O = var1;
      this.y = var2;
   }

   public void lg() {
      Consumer var10000 = this.y;
      boolean var10001;
      if (!this.O.get()) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"slftjygbuvp0f","R6BGtn3lQhXRZSznVSP6bc0ka/f8VUOLEmCz06lbEZ4=",3061450879837480591,-1875213541692937702,-8347510649096485903,-2151352712265889568>()) {
            case -128825377:
               var10001 = true;
               switch ((int)com.yiyiaddon.m.b.a<"s3jdgufv36jqrf","u+d4LM4i3hBG2xNSk4Rd3RMWvQfzVUCBUeDqjEBC1mI=",-4604052662885200797,-6206801244040404166,9102333336257871628,-4929506941792793718>()) {
                  case -661749912:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = false;
         switch ((int)com.yiyiaddon.m.b.a<"swpouuxmdxcuc","ROS3e86IOnvYVMdXqgJHXOJPDTzfkTbgsdCR+htnWpw=",5204880350195431894,7872054092566646825,-5163812219384897278,-6044215856898662703>()) {
            case -2114780251:
               break;
            default:
               throw null;
         }
      }

      var10000.accept(var10001);
   }

   @Override
   public float c() {
      return 44.0F;
   }

   @Override
   public float d() {
      return 24.0F;
   }

   @Override
   public void a(float var1) {
      if (!this.h) {
         switch ((int)com.yiyiaddon.m.b.a<"s1kwsx6a5y4opf","aWgJXM+qkJ1w15Tf2br2D6111q35CDYUMGC6cXTWcss=",8178190535552111961,8356353144431146889,-169422986573447499,7507568077958786080>()) {
            case -445657989:
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.l.a.d var10000 = this.g;
         float var10001;
         if (this.O.get()) {
            label34:
            switch ((int)com.yiyiaddon.m.b.a<"sk59yf8co9lhx","bEVa5vCNAHhjYGUkO8KRgmFp7d3QkPcEJcmT+CluCx8=",-6540131061882310965,-7160567974083347994,-5556176504876391281,8787116073749225864>()) {
               case -92292578:
                  var10001 = 22.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"s5npihxarmjm","y13xArQkunaqdn86l8sMS2klxFXe4TGKVo2q/b5nelU=",-5388986674451998368,4952187658351373732,2089340072036819155,-862866848549962781>()) {
                     case 1651055858:
                        break label34;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10001 = 2.0F;
            switch ((int)com.yiyiaddon.m.b.a<"s1kpas3vnyllba","dKDcv4sZgd4tj8XKds/ofJjbro+wcry7Sc6ojviL4Pk=",6155389264899346415,7327570202429337387,1313735977750503226,3340791528374433999>()) {
               case -368374705:
                  break;
               default:
                  throw null;
            }
         }

         var10000.d(var10001);
         this.g.a(var1);
         var10001 = this.op;
         float var10002;
         if (this.O.get()) {
            label27:
            switch ((int)com.yiyiaddon.m.b.a<"s2qihjwpba42m9","eu5eHqlb6Vhz500ve2RyyzrlkJzzTQ8PsYvEEf8RbuA=",1339956265249148212,8400712237166631139,8758845191135498001,-8659009662133001738>()) {
               case 1756626187:
                  var10002 = 1.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"s1b1j8dr2rmtlg","RRx5BET3pQx/2Z8iMo1KsLy40YYQ1Mx7LfEUx1QX1dk=",6905636902304926213,-4847290174707333821,7325681083074074380,2218355444174486076>()) {
                     case -1978128908:
                        break label27;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10002 = 0.0F;
            switch ((int)com.yiyiaddon.m.b.a<"s1k6r1kxvhxt7u","aoAy1g3WZkPk9G6Oz048BeWIqfhxdPJU234DDdIMZ8w=",-1542510791496689470,-8869579807977568621,7724800394964386592,-1371931739953654588>()) {
               case -411976858:
                  break;
               default:
                  throw null;
            }
         }

         this.op = var10001 + (var10002 - this.op) * (1.0F - (float)Math.exp(-14.0F * Math.max(0.0F, var1)));
      }
   }

   @Override
   public void b(Canvas var1, float var2, float var3, float var4) {
      boolean var5 = this.O.get();
      if (!this.h) {
         label51:
         switch ((int)com.yiyiaddon.m.b.a<"s1t9nr0p3vd10t","lbFMY3QbfKXYGyhLdiq9klQk8Zha9I6Dn8TDLdbQZyE=",-6680250793540749220,7871664749355264290,-242989503541075235,5892229102018390736>()) {
            case -893971071:
               this.h = true;
               com.yiyiaddon.l.a.d var10000 = this.g;
               float var10001;
               if (var5) {
                  label48:
                  switch ((int)com.yiyiaddon.m.b.a<"s144up5eoizq0b","H3Doa0bJdymHNyr5Jj6ued4UiAOJBj+9u+xXIQ1uIjo=",2613600885671649281,7966034560200007697,-6442742911485997931,-9100627949302180890>()) {
                     case 889497872:
                        var10001 = 22.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s3khx2cuqd27hh","VvAuJ/rfn0P8uKavljH/Xw1Bsweo3tebexvHzAzas2s=",8446966094502518883,-8049892636103161435,7241114090192189509,4686371657370389204>()) {
                           case -1620840386:
                              break label48;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10001 = 2.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"s2rp5g7qfntlpc","Zhdy2oYEA3gY6JKLPF3pAqtSPVNdAtn4mpzXDAZj7Gs=",4066656732530549401,8304372207709681280,-7654200729844043002,-2626669003764515861>()) {
                     case 1807643473:
                        break;
                     default:
                        throw null;
                  }
               }

               var10000.f(var10001);
               switch ((int)com.yiyiaddon.m.b.a<"s2vr994vv1d5al","5N7O+ZWtLsv3aW6Vl7+C21VqbEH2fGqwjAEOrsBo/Ck=",161865645145615510,-6263432953338670028,2268111964045013658,-3279260276199521587>()) {
                  case -448703291:
                     break label51;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (this.op < 0.0F) {
         label39:
         switch ((int)com.yiyiaddon.m.b.a<"sstutnelbe8q5","qvZmlz31OcJL3JyrOCBVzs0o1PvtKD6EBr3FKfDOsgg=",840647001440531940,4310281607299941761,1992136412429845892,-1762266754709192339>()) {
            case 533717093:
               float var9;
               if (var5) {
                  label36:
                  switch ((int)com.yiyiaddon.m.b.a<"s14wgiq6nvwxvh","20y+8mpxQ6cFHDxM+AwkM+Q1533K18q6zn8RFT86u88=",3863411468032915878,7471029383542894529,-6199857115233425161,6392708265338020846>()) {
                     case -1159105503:
                        var9 = 1.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"sezhahmqib3mw","WYMi39SIth8PLTIUgJI1JKvPTgyx8OcCVi38GAe7Qr4=",3231779142016895319,7551048542235068592,-4077943278814956106,-3727851727119711984>()) {
                           case 2072900179:
                              break label36;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var9 = 0.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"ss50oygsiz6n5","WFdOFztI3A0hmwPlLBw7V4C2V8lm/ug4n032FXu8AVI=",3646296558386750588,-8697836906362174517,593851272897365563,-2465320859689644285>()) {
                     case 1666609225:
                        break;
                     default:
                        throw null;
                  }
               }

               this.op = var9;
               switch ((int)com.yiyiaddon.m.b.a<"seywgp7pwp698","RRHPNzHT3LTkYJgLKkEZNRLYYPcpemtT0/TeXBf6H/k=",-3269203118761786491,-4137615400421128573,-317423561269020582,6716996984188059778>()) {
                  case -527316367:
                     break label39;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      float var6 = var2 + this.g.r();
      com.yiyiaddon.l.i.c var7 = com.yiyiaddon.l.i.c.a();
      int var8 = b(var7.vm, var7.uS, this.op);
      this.T.setColor(a(var8, com.yiyiaddon.l.i.c.y(var4)));
      this.U.setColor(a(var7.uX, var4));
      var1.drawRRect(RRect.makeXYWH(var2, var3, 44.0F, 24.0F, 12.0F), this.T);
      com.yiyiaddon.l.b.j.c(var1, var2, var3, 44.0F, 24.0F, 12.0F, var7.uX, var4, 0.16F);
      com.yiyiaddon.l.b.j.d(var1, var6, var3 + 2.0F, 20.0F, 20.0F, 10.0F, var7.uY, var4, 0.45F);
      var1.drawRRect(RRect.makeXYWH(var6, var3 + 2.0F, 20.0F, 20.0F, 10.0F), this.U);
   }

   @Override
   public boolean cB() {
      if (!this.h) {
         switch ((int)com.yiyiaddon.m.b.a<"s2vrcvedx17csa","T+WeNokmyHDAk9jRm+Ww8BsYEj3Pdw0AT2BySimFNEE=",8093949947066471050,6792546774628849299,-632065400664955209,-3232369562405545486>()) {
            case 546539807:
               return false;
            default:
               throw null;
         }
      } else {
         if (this.g.fQ()) {
            label47:
            switch ((int)com.yiyiaddon.m.b.a<"s3qwle1tmiqgmo","cpEcXjWsvdklL/SEt10Pyamen6A2Mt/ACk5yN5/pV4s=",2022784840430615971,-6993291919510407618,-7950120249765637814,4796030312636122835>()) {
               case 1613309849:
                  float var10000 = this.op;
                  float var10001;
                  if (this.O.get()) {
                     label36:
                     switch ((int)com.yiyiaddon.m.b.a<"s11b7qtkx3vak5","Zx3Mpjp4AZrMbsmD0wnGyNxVbRzJkY4hSI+j/heeAbs=",-7760630156246724918,-1882290464589672914,4465110871929406042,-7565837544105387967>()) {
                        case -1116508538:
                           var10001 = 1.0F;
                           switch ((int)com.yiyiaddon.m.b.a<"s35vt1i48az17r","lu5BDx5xLRoDOOz3uBRQXf8McRAd3laZw8XDzyEqRvA=",-3621891122680368396,-5154328043677936053,3642783947512154181,-8252335485768765094>()) {
                              case 1748491056:
                                 break label36;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10001 = 0.0F;
                     switch ((int)com.yiyiaddon.m.b.a<"suhm9oa9x8kts","oXJDbxJwogpV7X5uGlAAWTFpaj5lFVfF+RHtABFJSu8=",243098670609419326,-2653498910702307391,-8810460182725981677,2130051979512091283>()) {
                        case 1821766287:
                           break;
                        default:
                           throw null;
                     }
                  }

                  if (!(Math.abs(var10000 - var10001) > 0.01F)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3dp9x2ecmwx0x","TYPol7Z9/c7EMX4BoVbMBPqQY0B1XnnTthjsD83ckyE=",5422063616306849276,-683741605185581527,-4418782339914591990,-9042735527730733781>()) {
                        case 1043359473:
                           return false;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s1jp9d2j3gtf5n","pPFmU+pZtYLyHZRoIuljxUaqlUfDgtf3PaltIJRgQcc=",8708255941724709185,-3429925221492700585,-1118229910405668461,-7037029732830163389>()) {
                     case 1244510027:
                        break label47;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"s3nyhykz72vwpe","ZbU+hhwpYyy8kfQRYPF+BMMQhGxc01fdDzka+vvh5r8=",2270731531927117292,7943450459761317514,1751669703671557051,5756259136900189098>()) {
            case -842896396:
               return true;
            default:
               throw null;
         }
      }
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, int var5) {
      if (var5 != 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s36a6yqwx8sil0","kuT5F0V10csOQ1y704tNk12YYoHvEbgRztrh0U6Ncd4=",-5539151673443551587,8573694938474542017,-6983175862966960026,-2382920366659574502>()) {
            case 2077792661:
               return false;
            default:
               throw null;
         }
      } else {
         this.lg();
         return true;
      }
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
}
