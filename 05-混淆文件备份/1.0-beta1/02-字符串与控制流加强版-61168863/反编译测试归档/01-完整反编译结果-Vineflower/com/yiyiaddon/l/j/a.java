package com.yiyiaddon.l.j;

import io.github.humbleui.skija.Canvas;
import java.util.function.Supplier;

public class a extends p {
   private static final float mO = 14.0F;
   private static final float mP = 6.0F;
   private static final float mQ = 14.0F;
   private static final float mR = 0.1F;
   private static final float mS = 0.14F;
   private final Supplier<String> C;
   private final Runnable m;
   private final com.yiyiaddon.l.a.b f = new com.yiyiaddon.l.a.b();
   private com.yiyiaddon.l.j.a.b a = com.yiyiaddon.l.j.a.b.SECONDARY;
   private com.yiyiaddon.l.j.a.a a = com.yiyiaddon.l.j.a.a.MEDIUM;
   private String Cj;
   private Supplier<Boolean> D;
   private Supplier<Boolean> E;
   private float mT = -1.0F;
   private float cD = -1.0F;
   private float mU = -1.0F;
   private boolean eh;
   private String GK = (String)com.yiyiaddon.m.b.a<"s2jzdwdlrgr8x9","J5LHxT1Ob7WNUZIvQkIORq2A7DBtEudDi6bMbw==",2073744168522266492,-1159142702976062507,7475152023667411530,6879666381860802024>();
   private float mV;
   private com.yiyiaddon.l.j.a.a b;
   private float mW;

   public a(String var1, Runnable var2) {
      this(() -> var1, var2);
   }

   public a(Supplier<String> var1, Runnable var2) {
      this.C = var1 == null
         ? () -> (String)com.yiyiaddon.m.b.a<"s2jzdwdlrgr8x9","J5LHxT1Ob7WNUZIvQkIORq2A7DBtEudDi6bMbw==",2073744168522266492,-1159142702976062507,7475152023667411530,6879666381860802024>()
         : var1;
      this.m = var2 == null ? () -> {} : var2;
   }

   public com.yiyiaddon.l.j.a a(com.yiyiaddon.l.j.a.b var1) {
      com.yiyiaddon.l.j.a.b var10001;
      if (var1 == null) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s31kbiks0cboqg","cayNk4VTA3g/6sZml1bMoiJ2j5kngo3YZXmlasmSuSo=",1487518648244608124,-8453274388268625260,-8798831329352371879,2592454946105872297>()) {
            case 356031463:
               var10001 = com.yiyiaddon.l.j.a.b.SECONDARY;
               switch ((int)com.yiyiaddon.m.b.a<"s1o5jphzndhp77","te47Z6w7hABkR6H67BDpIAWoRnoxcrumzfbkswItGj0=",6338263233914224686,-6000294936153884426,-4053504033881035906,-8083586065295254989>()) {
                  case 1839081164:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var1;
         switch ((int)com.yiyiaddon.m.b.a<"svpwjkjgs0qt7","im+Y+mQr4diF6h/XTSuwIqHjyd8TiOJC2d6QK3iagrU=",-8076557823039807063,4361512193634789718,-8204315439995284756,7428993758800688956>()) {
            case 2065482261:
               break;
            default:
               throw null;
         }
      }

      this.a = var10001;
      return this;
   }

   public com.yiyiaddon.l.j.a a() {
      return this.a(com.yiyiaddon.l.j.a.b.PRIMARY);
   }

   public com.yiyiaddon.l.j.a b() {
      return this.a(com.yiyiaddon.l.j.a.b.SECONDARY);
   }

   public com.yiyiaddon.l.j.a c() {
      return this.a(com.yiyiaddon.l.j.a.b.DANGER);
   }

   public com.yiyiaddon.l.j.a d() {
      return this.a(com.yiyiaddon.l.j.a.b.GHOST);
   }

   public com.yiyiaddon.l.j.a a(com.yiyiaddon.l.j.a.a var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2uk6d9bv6bm05","AZjlztL/1hJ7kUe0YUDQuZlfvO/w8qDR2pMB5OTGZZU=",1834560326656347566,-2391692027862016913,2316862121060526338,-2490466844352642094>()) {
            case 2034831073:
               this.a = var1;
               switch ((int)com.yiyiaddon.m.b.a<"s3pvhypa6gdx54","gOagdjY3ONuQwzp1yQtjx1RjlK+bsJcTAGRoCW88u44=",-3936862002439255979,8092362767144393456,-4933538184035713650,-7669544457314572742>()) {
                  case -75023584:
                     return this;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return this;
      }
   }

   public com.yiyiaddon.l.j.a e() {
      return this.a(com.yiyiaddon.l.j.a.a.SMALL);
   }

   public com.yiyiaddon.l.j.a f() {
      return this.a(com.yiyiaddon.l.j.a.a.LARGE);
   }

   public com.yiyiaddon.l.j.a a(String var1) {
      this.Cj = var1;
      this.mW = 0.0F;
      return this;
   }

   public com.yiyiaddon.l.j.a a(float var1) {
      float var10001;
      if (var1 > 0.0F) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"sjos6uroh6jgp","qiAPj0E2FmOViI18X1ByPm42FQtvJ7yIMpjAsfjuFLg=",7843348312778805314,5239598454098261659,1919794830494717248,-5132522854683361844>()) {
            case 354377711:
               var10001 = var1;
               switch ((int)com.yiyiaddon.m.b.a<"s1s712x9u5gcnr","YsE9eWdG6pDXy5sJsP72ah0jIiYGkwAdT14R2Wp5u6A=",-7076900359741477580,8563158461785010779,-7551327380274712696,-921944083312455321>()) {
                  case 477356793:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = -1.0F;
         switch ((int)com.yiyiaddon.m.b.a<"siufky8sa0kv5","X1wEz9lu7DbxY8jqQ+pofcy4mM68UqbMO0rQNB+Arn8=",-8016160161918996272,2373937091842711509,4279307142615870247,8958665601121407457>()) {
            case -1960695434:
               break;
            default:
               throw null;
         }
      }

      this.mT = var10001;
      return this;
   }

   public com.yiyiaddon.l.j.a a(Supplier<Boolean> var1) {
      this.D = var1;
      return this;
   }

   public com.yiyiaddon.l.j.a b(Supplier<Boolean> var1) {
      this.E = var1;
      return this;
   }

   @Override
   public float c() {
      if (this.mT > 0.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s2k3d3nwjzlign","mp/VDbqIJ9NgaXLNGzL78p0Kh/T5qwX8F7Zc8NEnpao=",4387383107031742520,6235215407967127095,1389708894378288111,5920838597965088610>()) {
            case -815176550:
               float var10000 = this.mT;
               switch ((int)com.yiyiaddon.m.b.a<"s2eobmhfr0znd6","FbrrsT0lcqofL6nFJ1kV67V3/nJuCOVcUlcQh6fZ+Ds=",-4372308371574586270,757697841816378986,7549528509869521943,7017668647329527385>()) {
                  case -98625713:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         float var1 = this.ar();
         switch ((int)com.yiyiaddon.m.b.a<"s2mro3ok0qk1dt","VYUR5uPMj4kBwQi6Np1pwjSlicGtgmuVmE057a/WQuI=",2120666237413633485,7266841992729970907,5868985499867822732,2461663539191962530>()) {
            case -1521998611:
               return var1;
            default:
               throw null;
         }
      }
   }

   @Override
   public float d() {
      return this.a.mX;
   }

   private float ar() {
      float var1 = this.a.mZ * 2.0F + this.as();
      if (this.Cj != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1jhvpw6ndyzg5","kU45HWtDvjRInNYxkNI5LF0lpf4dy3Z+45IsdYa5PJs=",-1396354733136470272,-3869345771537215155,7724658427386613981,4857805668559827691>()) {
            case 698278953:
               var1 += this.at() + 6.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s2egjw0thogqa6","GiaihIbxxHR/zg8cNX/NNdU4FkzG9N3KTn/X8YsnfC8=",-4495593705482788371,-8085685462738758061,4760212652069545129,2201048511637826542>()) {
                  case 1282365375:
                     return Math.max(this.a.mX * 2.0F, var1);
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return Math.max(this.a.mX * 2.0F, var1);
      }
   }

   private float as() {
      String var1 = this.C.get();
      if (var1.equals(this.GK)) {
         label18:
         switch ((int)com.yiyiaddon.m.b.a<"s1ehut8m8piw4","J5yNp7SCQk0GKCvbWrC5ZfDuBaoxe86yynAo7iXme3o=",9215195628457307740,-1989600846059687521,-165231752740415438,339668057141289012>()) {
            case 431379305:
               if (this.b == this.a) {
                  return this.mV;
               }

               switch ((int)com.yiyiaddon.m.b.a<"s1n1wltxbv3l2s","3Vaa+jrnAQYUjje04E6uvMP31cChqoEBuF8CACsIZE0=",4589761271623440273,6690152711325273204,-6787307450650318216,-6711752714524454253>()) {
                  case -1087915198:
                     break label18;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.GK = var1;
      this.b = this.a;
      this.mV = com.yiyiaddon.l.g.d.a(var1, this.a.mY, false);
      switch ((int)com.yiyiaddon.m.b.a<"s3ubtq649xstjr","OrD0m2k32IipuuS8KkKGimdUH+dKydMKDVrejkLkQ8o=",6401321416556063408,-7978810147809957613,-2750300981177578109,1622965497832698652>()) {
         case -1595406408:
            return this.mV;
         default:
            throw null;
      }
   }

   private float at() {
      if (this.mW <= 0.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s2msgt6wqdo01d","Ke0PBNrGwNYvPBQdtrEOHfjuUg/IBaWWt8x2bMWy4qg=",-785072031495079279,8227918425300557408,-8387359007905100655,1971401058785939778>()) {
            case 1403069639:
               this.mW = com.yiyiaddon.l.g.a.a(
                  this.Cj,
                  14.0F,
                  (String)com.yiyiaddon.m.b.a<"s3bwbhlr22yb4m","XODGZm+vyreRWx0sESHkPS8io2kzVt9pBZIK9XxO0UxQg0AQ/IIgPcIEKqyjRzKuz9hQ3jpXWyHfObKH",1113970754511469980,7173455061510101868,-999162357108632841,1548354858423655552>()
               );
               switch ((int)com.yiyiaddon.m.b.a<"s23e1n447ret","fDUp9B9OM7M7+QMIkGC6SKz+uQE6o0ZBgADM71H+Lu4=",8338504062592198236,-2406085337146000155,-7337209135236851053,2328798979359973744>()) {
                  case -332624931:
                     return this.mW;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return this.mW;
      }
   }

   private boolean gz() {
      if (this.D != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3f4ozzyowmuyt","OoWQ1c9KzOPcTQqWlgKatnNvM5vJhgR7TXrKe7k7WTI=",-3826923380197456543,-2311874631214381784,8326863009752896210,-3484644667160641486>()) {
            case 876950352:
               if (Boolean.TRUE.equals(this.D.get())) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1zbjdug40a3m6","s/sNxBBvLHNN/tYb2CX3tkwDjotPbBYVBS5CQAzJnGQ=",6430804648592651368,7992304427813456025,-3276554276285770232,-9083923988797965682>()) {
                     case -1938164286:
                        switch ((int)com.yiyiaddon.m.b.a<"s3km8g68kdvxgd","uDlla1GzaVA5xUKH90c3xeeo5Sa9pqg3g3NRffOfohw=",-117433771460855708,7974079874493034031,-706372322858645410,-47862609131976863>()) {
                           case -230484860:
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

      switch ((int)com.yiyiaddon.m.b.a<"s1oyonfk0dqm05","nefebXmU+yHjfr3JSswjLnlnq2pO/ebu4QLpUu1IBBQ=",5332726254427223465,7797391798981747911,2391414777141989777,-3107566623396935273>()) {
         case 143224052:
            return false;
         default:
            throw null;
      }
   }

   private boolean gA() {
      if (this.E != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s319rx2bl09xjv","zYgGtUbjheg9UX6/O4RDEtDLiygC1huFlOnSrq92opI=",-1946878791192345172,4735088607934084513,-2460739191639975672,-2082983335240627795>()) {
            case -276272199:
               if (Boolean.TRUE.equals(this.E.get())) {
                  switch ((int)com.yiyiaddon.m.b.a<"s4rau2elcb4sb","p6QjYHnFgDCoNOZ9op1nfoeANUREbJ4VBzFikTvj00g=",-5079552822198479027,254067905187541518,-8692558118208768655,-6519347384355242619>()) {
                     case 925551204:
                        switch ((int)com.yiyiaddon.m.b.a<"s3lcrqwtw12zmc","UG8w8dRWAvRoV+9hUTTmMg+V3tSXiW70Pw+pHyAgkEU=",-5653443445984980296,5891702649134578179,-4195153029494200974,4599428962810198857>()) {
                           case -971036556:
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

      switch ((int)com.yiyiaddon.m.b.a<"s11741ngvnssy4","/wnaYyk/pduuAY6sDj6QrEKrMJs/hcJrnBer+//FoQ0=",8985961736152265669,6426177387097377654,2675077318791091888,9139530590182614917>()) {
         case 1344231803:
            return false;
         default:
            throw null;
      }
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5) {
      if (this.gA()) {
         switch ((int)com.yiyiaddon.m.b.a<"s10n6kno79x3x","zMtcEGk3e+9svFcLM3eZ7ZGomKMkP/LYCamuCi8J/xc=",8726768176915612849,-5888605990159383788,9032201217082848903,-342552296533871267>()) {
            case 1596112639:
               this.eh = false;
               return;
            default:
               throw null;
         }
      } else {
         boolean var10001;
         label47: {
            if (var1 >= var3) {
               switch ((int)com.yiyiaddon.m.b.a<"szmv2o9fo250v","5iVDI5X6zQMdJOE7RNYGhzW4ZSCcuJSqPlZzs/R/kXk=",-8341923176764587663,-8452599462621290793,-2196690842393186812,-8257196010335308048>()) {
                  case 672311568:
                     if (var1 <= var3 + var5) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1wtmg8f69uwz2","W64Rgj0/dTBnhnF+1/9PBaO0lH9lL9mjajqB+2Yhtwg=",-4439506735747162232,2033379328658690508,7495721667034148234,-2323559930688606418>()) {
                           case -96027384:
                              if (var2 >= var4) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1b4cq9q8lbus8","6X4DEpuogwFZiwJ+rOLwPn2Wqg16L3Dq6Bm5aepxHPw=",-5593972191105138782,640609774614899251,7628589259367749447,6713784668540056746>()) {
                                    case -937403531:
                                       if (var2 <= var4 + this.a.mX) {
                                          switch ((int)com.yiyiaddon.m.b.a<"slberx89lchdp","JcHjJHXoGUHT8L1lWpccsLvBmWeec6P2LD5Ygcvz+tM=",-7321159817805517719,-8698520542033199399,-5089631202045043141,2310327060681728418>()) {
                                             case 1629148661:
                                                var10001 = true;
                                                switch ((int)com.yiyiaddon.m.b.a<"s1djl7e8sv6eyx","oOaFHb/nFSkQSyPLttnza5WFTCUkQ60XhpGYuM8fYx4=",-9026861578587018095,57728531926106560,5788040114440847208,-1235848908594429293>()) {
                                                   case 228405448:
                                                      break label47;
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

            var10001 = false;
            switch ((int)com.yiyiaddon.m.b.a<"s2lnpi3nhez0bq","iPEo4gdUWH13x7rBaBY91XzczG5ujopVgDSmYvSCa3A=",-6695243693301578196,-2378068505996459551,6524536065439792617,-7906795527744300248>()) {
               case -1073336696:
                  break;
               default:
                  throw null;
            }
         }

         this.eh = var10001;
      }
   }

   @Override
   public void a(float var1) {
      this.f.a(var1);
      float var2 = 1.0F - (float)Math.exp(-Math.max(0.0F, var1) * 14.0F);
      if (this.cD < 0.0F) {
         label70:
         switch ((int)com.yiyiaddon.m.b.a<"s12sbi6s1n3gp5","sE+NVgFqrZdbdUXBIIrsbZ+jcW2TwwRhYggkkO/ml5U=",926880625279484818,-97707196681482709,354257037150136623,-154306421707465239>()) {
            case 813379733:
               float var10001;
               if (this.eh) {
                  label65:
                  switch ((int)com.yiyiaddon.m.b.a<"s1m31i8gz90sz4","BGjIVZHjzBmwoQb1tieW/ly8HQkDOY66ylb5IpfKb/A=",-4908815607697946106,6323688348811366645,9198763408600888565,-318102173548129572>()) {
                     case -396510754:
                        var10001 = 1.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s2x8mhh8ovmmdm","YZciYCUVULdEkCTLkv1x0dXWZEoJuUaVWlbo3bHxxWI=",-116170591600775574,8680746125559465091,8008070174878012459,-8727801634556655919>()) {
                           case -990243847:
                              break label65;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10001 = 0.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"spmf1mvgmfu63","XTfy2WPSL9KNSZKHKtJ/PADxG2yAl0XLPXRwD3rZPFA=",-363484251471712373,4689508115678524816,3061154582063009844,-4745014683651847512>()) {
                     case -835847935:
                        break;
                     default:
                        throw null;
                  }
               }

               this.cD = var10001;
               switch ((int)com.yiyiaddon.m.b.a<"s3qc4b2tb6u5vz","Q/D7TsRKcKZJ360W1e7MKFcb8ssv+W9PK3SrguUPP7U=",-3531031035004078497,6724136180960438748,-103311638278232791,-814639250998584349>()) {
                  case -1508145511:
                     break label70;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         float var4 = this.cD;
         float var10002;
         if (this.eh) {
            label75:
            switch ((int)com.yiyiaddon.m.b.a<"s16uyzx2thggiu","8AzT97f5iygVLrihZEfacYbV5ceXpSVYy0ELuyrevN8=",-1873002000532266370,-4278606760614938668,-4069569985334875161,1447737607355484318>()) {
               case -1143180102:
                  var10002 = 1.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"s18cl4yujud7q","t/WGx2t8OoXiPLlQYRHBiU574H0FJho6Qe3C1BK2JZU=",-5573432329766734070,2778336181936123264,-3254522224549001379,-3370985393958604982>()) {
                     case -4285526:
                        break label75;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10002 = 0.0F;
            switch ((int)com.yiyiaddon.m.b.a<"s3nqohv6g00di5","JbA03XUYAQZfJYvFjX5DG7DCIlaazZzsS/myUTqNATE=",4789396481289678641,1018168055429736819,6439904638280664669,7881249719308726382>()) {
               case -2116485283:
                  break;
               default:
                  throw null;
            }
         }

         this.cD = var4 + (var10002 - this.cD) * var2;
         switch ((int)com.yiyiaddon.m.b.a<"s3tc33acaca8di","kDQsoypZ2K8wessi07ee4XjZCWTN/ziCmlupOs2dpfc=",-868299931589770225,8174614481011330996,-6261491530053314549,-7607897939257660624>()) {
            case -1434223807:
               break;
            default:
               throw null;
         }
      }

      float var10000;
      if (this.gz()) {
         label56:
         switch ((int)com.yiyiaddon.m.b.a<"s1ezttcotg1wwn","e06MqCipBvyPmxj+LRU9dpX1n6uy1HUCOOOrcqorGLQ=",-6653828031475718984,7017729496318687542,1737696605586190343,-6820566518442403341>()) {
            case 136501829:
               var10000 = 1.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s2muid24oaycfv","yxwplVAf2G9EOAx3/j2kcq59s9pUF2hDLF2qHFvK9uU=",-3599777292798476189,-7839348867056865651,6359193125676789309,-1284444226006293588>()) {
                  case 1842245681:
                     break label56;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = 0.0F;
         switch ((int)com.yiyiaddon.m.b.a<"s2bbyncnse2hry","O2rrxOlEyeXnriKrsPvwbnVVoLPSwIDiDboeGUBdN00=",512646681855816982,-2539817533482391346,-617907990325894005,-7093835995147820930>()) {
            case 583834948:
               break;
            default:
               throw null;
         }
      }

      float var3 = var10000;
      if (this.mU < 0.0F) {
         label51:
         switch ((int)com.yiyiaddon.m.b.a<"s1v2luhaxs8j6j","S9+xE3elMdlRCFWk4xTOpOpP5/EyB5mrDnt5X/R6FgU=",-2315577502011528736,-1880289130374124889,8805101784934813670,-9032876538954856682>()) {
            case 536481568:
               this.mU = var3;
               switch ((int)com.yiyiaddon.m.b.a<"s281zj633ebkr9","IHDyCXO4VbWa6P0B6FF+bGiE4TuY4gCZBPsAHyNH6Rw=",1547705744828122687,5991817471998520807,-9080879415937254453,-2358449487029918524>()) {
                  case 1086877655:
                     break label51;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.mU = this.mU + (var3 - this.mU) * var2;
      if (this.cD < 0.001F) {
         switch ((int)com.yiyiaddon.m.b.a<"s1vb29tlwlyoef","eiXhuI3unNDvtPh7ifOLDNBEEIhi8K17k9oqkNfuekc=",1153177780861492663,-5700687430639769725,-7836059642920187039,6817098156349289681>()) {
            case 1208546237:
               this.cD = 0.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s1m8k41cxno84o","Bkt8Ot7/jjo2enVbRR/e20v2erddHAiDZVGhbtWRZe4=",2386515182611885513,452663118362082552,-3917026561347074702,7570540837543132380>()) {
                  case 585161435:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private void kW() {
      if (this.cD < 0.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"si2nsframw6r","0f6p3CS+adnL3ALK30MAPyerf6chAj11wWLOTCxH2do=",968572833633495074,-8415996237112101056,981953096243402658,-7915611084958054374>()) {
            case 1167311421:
               float var10001;
               if (this.eh) {
                  label22:
                  switch ((int)com.yiyiaddon.m.b.a<"s10qt3muwcg3q0","YtFOv+IUtYdOrjrisCAUGiRH32dDdy8hGyvCySiNzxs=",-4393440684765231129,481815223624765962,-2923799954756359281,-4018993592995543958>()) {
                     case -247778005:
                        var10001 = 1.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s3sd0swy3damtl","Hk4lZBGkJJu+EXUNjbaY3anuGrAVdgWyCZKb69dScDE=",-6465123653022572469,5461487306250528168,-2150816426285820814,5341552202052972733>()) {
                           case -676209539:
                              break label22;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10001 = 0.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"s1d2e0h9pv6izf","mDZC6PrNRi++UuufjC9eazbAlwDk94Kmg3TdDLG3Lso=",-6266828478429145385,-1537609152144040296,-3967143887404796241,-209096362963651922>()) {
                     case 1724059057:
                        break;
                     default:
                        throw null;
                  }
               }

               this.cD = var10001;
               switch ((int)com.yiyiaddon.m.b.a<"sdhzr4ntmy378","fDqlAEjVhu243DskQTJGqLWitDRnR4tchffczWQ8G58=",-156533586478310396,-8293133831969466169,-8395537643958792870,-7246025435005596874>()) {
                  case -1890703172:
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
      label73: {
         if (this.f.fP()) {
            label70:
            switch ((int)com.yiyiaddon.m.b.a<"s1y40ghvc9rh40","bAG8cSz1TY7XDEIh+I5Qvq/K883rWZ6qwbPpaYGD5bc=",7702439476286584323,1585959638578501433,6976894248003063105,8410916362402295749>()) {
               case -1572301607:
                  float var10000 = this.cD;
                  float var10001;
                  if (this.eh) {
                     label54:
                     switch ((int)com.yiyiaddon.m.b.a<"s2jlglq6vjxnki","jmcp4seaCh5fxVucPt3C1tuz5K04NM6B345c29AuGXI=",-5556940192278991433,-1851223061433927680,-4574982211809755813,2543335249272404598>()) {
                        case -740500586:
                           var10001 = 1.0F;
                           switch ((int)com.yiyiaddon.m.b.a<"s1t3fpsa9sgs7u","z8enzoYu2DRueiwIq651qyN85MvFMPj/K8Hyw073DSo=",-5117855871477855234,-5550338129808548099,7951850065029569158,3772829823564913464>()) {
                              case 2083481059:
                                 break label54;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10001 = 0.0F;
                     switch ((int)com.yiyiaddon.m.b.a<"s9elxhoche1nf","exkhYmnjY9Ae9k5iy12Kfq/ykR4xpTST8DjW+TBA5VA=",-7536939398120906764,-5748422475772714138,5443189960833677700,-8117965212519162223>()) {
                        case -194177024:
                           break;
                        default:
                           throw null;
                     }
                  }

                  if (!(Math.abs(var10000 - var10001) > 0.01F)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1iwpvjyvwijy6","rgeG74hcnmyi3wBmwiQEpV/YojXVTtk7wawNhXZfOT0=",3668448703963871742,1997343314291558412,413264621427649727,-8642807777468646338>()) {
                        case 466623677:
                           if (!(this.mU >= 0.0F)) {
                              break label73;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s2mgzznc6elb10","U8BdOiD5MUzA4cPNA/bYhobB2UWlwvJmW9dro9YUU5o=",-2493212378942739451,-9093580541170130376,-2886832900436480136,-5592291030166081584>()) {
                              case 1163091645:
                                 var10000 = this.mU;
                                 if (this.gz()) {
                                    label44:
                                    switch ((int)com.yiyiaddon.m.b.a<"shmq1astb5jyu","GD6mKkmaYBHeU7L1l7tmxAE8sR2O5YlBZHmOKsjklPg=",-1193411000051553138,-1462751174072804018,-3693427074380468852,-5011040449691209206>()) {
                                       case 1622389544:
                                          var10001 = 1.0F;
                                          switch ((int)com.yiyiaddon.m.b.a<"s3lb07xs5gfgmv","6gA8GxnZsjdlx3XmRzL+Itpu3UVGbCkowSepIbDt+TQ=",-8047504591664076294,3797274222838071880,208772964256722013,5015542868292408753>()) {
                                             case -1223211496:
                                                break label44;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    var10001 = 0.0F;
                                    switch ((int)com.yiyiaddon.m.b.a<"s2rapczsgvgdo8","ZOhAKS/fgZcbXtPTrrjW8zeTN37vlYqZqhJeeUV+MUo=",-3801758411080001372,-8884132506898727368,-8915714469511393922,-9003471816114754047>()) {
                                       case 1846069952:
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 if (!(Math.abs(var10000 - var10001) > 0.01F)) {
                                    break label73;
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s14sx8ykt6wm8s","Vq0J5ZsoOFmVRA5VDhT1i/2IOkJBegbYnEX45nAd7JY=",-8189334065672536123,2187604952148493655,-7783614753009709831,9128797724402748191>()) {
                                    case -717925770:
                                       break label70;
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
                  break;
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"s5gh44jnugkoz","laqilf96AUBt1hCRhcygCCgPyBUXZTmTJZ6F89IbWvc=",-8831577894163908697,-3535932062609563731,1784896166068176892,-7277761284934445213>()) {
            case -973323953:
               return true;
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s2g1j4iwhpwf0c","YIWNRV5f6I4d9wda4ahfUiOiNWLLCAyb8q+8YT9xO54=",-1278808323100058065,-1074927807023709398,5721389554372192315,4579021424594478433>()) {
         case 790553225:
            return false;
         default:
            throw null;
      }
   }

   @Override
   public void b(Canvas var1, float var2, float var3, float var4) {
      this.b(var1, var2, var3, this.c(), var4);
   }

   public void b(Canvas var1, float var2, float var3, float var4, float var5) {
      this.kW();
      com.yiyiaddon.l.i.c var6 = com.yiyiaddon.l.i.c.a();
      float var7 = this.a.mX;
      float var8 = this.a.na;
      float var10000;
      if (this.gA()) {
         label87:
         switch ((int)com.yiyiaddon.m.b.a<"s2pdzu0zep81ur","n9+jewVxMjDXXSkjupdB2lpdYggP0wB/QY38wqk1m8k=",-8160352446467910724,5009528707814781459,4218920830381394513,-695091437836807561>()) {
            case -1153271698:
               var10000 = var5 * 0.4F;
               switch ((int)com.yiyiaddon.m.b.a<"s1ul01qvbi6npu","mvf6JQ9JRGFyY012+/jAo3KCfF/vgSwbYdOU9WU0yYU=",5538301126491108051,2898183269028313558,-4596076193836000349,-2869397667172305542>()) {
                  case -346814793:
                     break label87;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var5;
         switch ((int)com.yiyiaddon.m.b.a<"s27p2xyxjc69pg","VRyBw5dSMcFnfGrTTo/pSr/RMRn4i2mKdVRS5ee0I/A=",-7303139526459528312,-3670936799164497703,7030473634813231708,-2876742491937629627>()) {
            case 1850854766:
               break;
            default:
               throw null;
         }
      }

      float var9 = var10000;
      int var10 = var6.vk;
      int var11 = var6.vl;
      float var12 = com.yiyiaddon.l.i.c.y(var9);
      if (this.mU < 0.0F) {
         label82:
         switch ((int)com.yiyiaddon.m.b.a<"s3hu8hj4xflhl4","oVOEQuSU9nOZC8YTCinyEWAGAK+YJ+kFgHJo2xUDYeE=",-7818474604760806788,7825446972861429723,-6325283881313953171,-4613333156664491532>()) {
            case 816474871:
               float var10001;
               if (this.gz()) {
                  label77:
                  switch ((int)com.yiyiaddon.m.b.a<"s20wmg5t1c5s4t","ZYVYjY1EllX2MN60Hk1p2PPX+cvvute32xfRJSKJHu8=",7446722023195712338,-4954126456561163692,-4701871061026540110,734938391478369580>()) {
                     case 207142654:
                        var10001 = 1.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s2hr4ityqabm99","Ake0soTY0hUmfWaih50im3eIkSaf5/oH6qRrjVJjwN8=",-1205982773584151452,916026713029680772,-2104545500968830351,5047053424505965643>()) {
                           case -297908482:
                              break label77;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10001 = 0.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"s3fm228xxga8d3","2l4jiBR9edUi47rEszXgna/XzLpvvORZDiWMqrRdtQ4=",-2533841985557681407,-112648380433409675,407720349324638054,-2521043487863249581>()) {
                     case -785526938:
                        break;
                     default:
                        throw null;
                  }
               }

               this.mU = var10001;
               switch ((int)com.yiyiaddon.m.b.a<"srxwfreuqk4b1","A69IZMGuxjuZxunLpBfYxna2ya8HgTvWsdoW7SzhjfM=",-8928204941438543438,-749291051665067915,-4766368205872414209,-3536884480849165231>()) {
                  case -1944269633:
                     break label82;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (this.mU > 0.001F) {
         label63:
         switch ((int)com.yiyiaddon.m.b.a<"s1pmi0mmefrc25","PwAl9aEvQcWPsDwx5gb3kGoIYholMXdsTsZDd+OFFLY=",-6416923156909938648,-4619890181114241214,2481435156978307586,-1669413212606364381>()) {
            case 377863839:
               var10 = com.yiyiaddon.l.b.j.a(var6.vk, com.yiyiaddon.l.b.j.a(var6.uS, var6.uX, this.cD * 0.1F), this.mU);
               var11 = com.yiyiaddon.l.b.j.a(var6.vh, var6.uZ, this.mU);
               switch ((int)com.yiyiaddon.m.b.a<"s2htdhuk1fe7sv","6aLwIOHDs3owuKs1kq6sZCTj15VyeU8WMrODqSe0Ozk=",-7259034976326015654,-2320817295391275722,8104354947594655860,4721134248581648359>()) {
                  case 722231482:
                     break label63;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         label70:
         switch (this.a) {
            case PRIMARY:
               var10 = com.yiyiaddon.l.b.j.a(var6.uS, var6.uX, this.cD * 0.1F);
               var11 = var6.uZ;
               switch ((int)com.yiyiaddon.m.b.a<"s2v3t8vc32fj07","MQzgyM0OEA2d+U9VKG6CAgbq5tV3lnI9e5G4fm2DvFE=",-8459188290223208127,-1680860846771524197,-5876034116731624077,-3562874951987815914>()) {
                  case 356987731:
                     break label70;
                  default:
                     throw null;
               }
            case SECONDARY:
            default:
               var10 = com.yiyiaddon.l.b.j.a(var6.vk, var6.uS, this.cD * 0.14F);
               var11 = var6.vh;
               switch ((int)com.yiyiaddon.m.b.a<"s2doarb6ckjhkd","4lVio51pnYhDlvvyveVOeGKd3pICvjNsT2X2UONjjAQ=",-3454361535203626403,6412509904513284711,-7935887726642527704,4437502041936913995>()) {
                  case 1534877430:
                     break label70;
                  default:
                     throw null;
               }
            case DANGER:
               var10 = com.yiyiaddon.l.b.j.a(var6.vk, var6.vs, this.cD);
               var11 = var6.vt;
               switch ((int)com.yiyiaddon.m.b.a<"s23ne6no2377ln","a0igEgwW8rwrLR0oKnk5OjAy1Z1WiO4f/aD/PSDcE4g=",1618667645644310186,1498651204266884411,8108550110048991713,-6724327642953057559>()) {
                  case -1545564502:
                     break label70;
                  default:
                     throw null;
               }
            case GHOST:
               var10 = var6.vj;
               var12 *= this.cD;
               var11 = var6.uU;
               switch ((int)com.yiyiaddon.m.b.a<"sjfzka1hru3vk","EPKb+Um8FZfL87BCMx0Xi5SEst2/N+vIectbpaI01HQ=",-4608949101334793604,-4871034827356587162,7743259223558904794,2530157946908167733>()) {
                  case 1468677659:
                     break;
                  default:
                     throw null;
               }
         }
      }

      boolean var13 = this.f.a(var1, var2, var3, var4, var7);
      if (var12 > 0.004F) {
         label56:
         switch ((int)com.yiyiaddon.m.b.a<"s1zbphqps2iupc","gl7Ttyrd7VMFzhRgdIWH1zvsO4Pyi4ci6pHH82VxKRM=",7858940464583444988,-3512101653683691567,-6761648532487158789,-4149545248659970010>()) {
            case 2093352369:
               com.yiyiaddon.l.b.j.a(var1, var2, var3, var4, var7, var8, var10, 1.0F, var12);
               com.yiyiaddon.l.b.j.c(var1, var2, var3, var4, var7, var8, var6.uX, var12, 0.16F + 0.1F * this.cD);
               switch ((int)com.yiyiaddon.m.b.a<"st30ethkki7m1","k3TqfEIgemsOHKBQrMo94s1CLkBAvEawz2+GCgbipu8=",5509723508039359536,684300372147956436,1683816225330205772,-4629490088709202540>()) {
                  case -1088458082:
                     break label56;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.a(var1, var2, var3, var4, var7, var9, var11);
      if (var13) {
         switch ((int)com.yiyiaddon.m.b.a<"si04kpzohm6el","9WedLeTTInr2q5Q54ZnNYxYUd1/aq+botI5djFUDRu4=",-8480761727259169667,4328622188397879179,-794320430410180488,8571621994755785341>()) {
            case -1337839916:
               var1.restore();
               switch ((int)com.yiyiaddon.m.b.a<"s243fmptp0popb","FUfOPWloxTlTM0fJ9qunFskrqlQvXr4yiGeXBTd0riI=",-6538670504720558311,8176384931256386087,-6744901091342691119,6066226314180978579>()) {
                  case -361760190:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, int var7) {
      String var8 = this.C.get();
      float var9 = this.as();
      float var10000;
      if (this.Cj == null) {
         label35:
         switch ((int)com.yiyiaddon.m.b.a<"s1eyqt6b68qsoy","StxgwJSGAgh6sflXXIxNnlcCq3CIatJL1v+TKSdf164=",6414500766464297554,-2151431785582699092,-2929691237909974607,1080562389122629775>()) {
            case -1927560839:
               var10000 = 0.0F;
               switch ((int)com.yiyiaddon.m.b.a<"su5emiw3cinjr","+/BHunkI9RMFZjsgoTWL2vD3OjckOf3tp8IGAlleNsM=",5304184307051548832,4796094883918409367,7513696966169764034,2179572329431661815>()) {
                  case 1663867609:
                     break label35;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = this.at() + 6.0F;
         switch ((int)com.yiyiaddon.m.b.a<"s2dsxaeyru8t1s","W8ydgaYGjHzhsZVbNXsEjZyxkkUxepTakyNdw/H/WJg=",-8963798923895263607,-3003596710370317203,6237974200607606880,-2097365316962755550>()) {
            case 1184730288:
               break;
            default:
               throw null;
         }
      }

      float var10 = var10000;
      float var11 = var10 + var9;
      float var12 = var2 + Math.max(this.a.mZ * 0.5F, (var4 - var11) * 0.5F);
      float var13 = var3 + var5 * 0.5F;
      int var14 = com.yiyiaddon.l.b.j.a(var7, var6);
      if (this.Cj != null) {
         label30:
         switch ((int)com.yiyiaddon.m.b.a<"s1xt3qm6nty9ju","AM1Ls9SINA0AbyrOTUzwZWyQ/+JzWmUQDRTDHyKBaqQ=",5589242712308699128,720107136408600693,-2581624184653248736,8316408293004415585>()) {
            case 1860965833:
               com.yiyiaddon.l.g.a.a(
                  var1,
                  this.Cj,
                  var12,
                  com.yiyiaddon.l.b.d.c(var13, 14.0F),
                  14.0F,
                  var14,
                  (String)com.yiyiaddon.m.b.a<"s3bwbhlr22yb4m","XODGZm+vyreRWx0sESHkPS8io2kzVt9pBZIK9XxO0UxQg0AQ/IIgPcIEKqyjRzKuz9hQ3jpXWyHfObKH",1113970754511469980,7173455061510101868,-999162357108632841,1548354858423655552>()
               );
               var12 += var10;
               switch ((int)com.yiyiaddon.m.b.a<"s119mhdjtc7lnk","wIKi4ZyeB4V26OdoNGzWrdIrpG2ed1Oa2cJkWNvL/Fc=",663577176959006117,1443502402570804131,-8515378572810010356,-5023481525961541515>()) {
                  case -991307032:
                     break label30;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (!var8.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1wldads7zex1r","Ddoaz3G7TXDObiwVDAYb152LHSkdBtGxJlRHFIjxjUg=",8045609401169614220,6348963867392740029,-9204295910196340730,-2326461790804621499>()) {
            case -1372367581:
               com.yiyiaddon.l.g.d.a(var1, var8, var12, com.yiyiaddon.l.b.d.c(var13, this.a.mY), this.a.mY, var7, var6);
               switch ((int)com.yiyiaddon.m.b.a<"sel1bnaj77bla","4xen2AQ4QLMBf7XTe2N47uqBL0nhYi+P/54o8BETRzE=",4545139435095092187,7875150517667893374,-8833302832639337569,-6993933981140619504>()) {
                  case -437587558:
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
      return this.b(var1, var2, var3, var4, this.c(), var5);
   }

   public boolean b(float var1, float var2, float var3, float var4, float var5, int var6) {
      if (var6 == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1k68k58skty1z","BrJa1zcdsA/DXIjJL0hu/B4hJ8vHkPr5Xzlmhkq++iw=",6172941986627194646,6895277196024851571,-6983045599310640272,1761860364063582639>()) {
            case -669596855:
               if (!this.gA()) {
                  if (!(var1 < var3)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s262kzy8jkhphm","sJhstJG3UrY6wna6xElq6xW/5lkyEsjHxvlGMD13/0s=",-2903772194020786378,-3666488313508663166,-7349320355970659350,-6110972622275569670>()) {
                        case 1019672931:
                           if (!(var1 > var3 + var5)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2fc4woo7hiq4x","O1cG4+hn5fB9wjvDHzYR+RX16GDA32VZDEsvC0yWmLE=",7706340586450610758,8414963477134476742,-8617575447750455783,-5326998063196971673>()) {
                                 case 1334458396:
                                    if (!(var2 < var4)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2ffu3a5noir9v","Er8O36U6x5JswvK9LHVjxBr4tnBhQ2448gVTYCGYKE0=",-2977596587220905935,8926908946436849999,-3591655053964941097,1469592368022474090>()) {
                                          case 1638115201:
                                             if (!(var2 > var4 + this.a.mX)) {
                                                this.f.jL();
                                                this.m.run();
                                                return true;
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s1qycnhmddgt0z","hMkQvBIGgtsDbiyTvey1H0UOsDuItNVOcr3/EcZt3Z8=",-7629350334913945951,-5320642603525477788,4686586692802821514,-6871984405509448458>()) {
                                                case -1844115617:
                                                   return false;
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

                  return false;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sgwh46zei229g","mZYGpFnYoKPOC3HN4AYrVM91yqRxUK3E869QmIep1AY=",-8387941799404311171,-9147629621032366153,-2367673422480138418,-8678339735404113451>()) {
                     case -1272554628:
                        return false;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return false;
      }
   }

   public enum a {
      SMALL(20.0F, 10.0F, 10.0F, 6.0F),
      MEDIUM(24.0F, 11.0F, 14.0F, 8.0F),
      LARGE(28.0F, 12.0F, 16.0F, 9.0F);

      final float mX;
      final float mY;
      final float mZ;
      final float na;

      a(float var3, float var4, float var5, float var6) {
         this.mX = var3;
         this.mY = var4;
         this.mZ = var5;
         this.na = var6;
      }
   }

   public enum b {
      PRIMARY,
      SECONDARY,
      DANGER,
      GHOST;
   }
}
