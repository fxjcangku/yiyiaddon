package com.yiyiaddon.e.t.b;

import com.yiyiaddon.l.b.g;
import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.g.j;
import com.yiyiaddon.l.h.f;
import com.yiyiaddon.l.j.e;
import com.yiyiaddon.l.j.m;
import com.yiyiaddon.l.j.n;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

public final class a extends f implements com.yiyiaddon.l.c.b {
   private static final String BF = (String)com.yiyiaddon.m.b.a<"slpu1u2xfqs3g","UKwM3/eyyOoQ02uHe1D88f8OK0tRMiMFVZvmalTyoNjSEGaPB8KF1dF78jmQ0Pbfj4ZKsc9I3yOeme6jbko9Y1sxNSQ=",929274694849378255,8384632774542740667,-2440839820742831606,6353953974302719570>();
   private static final int rV = 20;
   private static final float ez = 6.0F;
   private static final float eA = 11.0F;
   private static final com.yiyiaddon.e.t.a.a b = new com.yiyiaddon.e.t.a.a();
   private final com.yiyiaddon.e.t.a a;
   private final com.yiyiaddon.e.t.a.a c;
   private final com.yiyiaddon.e.t.b.a.a a = new com.yiyiaddon.e.t.b.a.a();
   private final Set<String> aC = new HashSet<>();
   private com.yiyiaddon.e.t.b.a.d a = com.yiyiaddon.e.t.b.a.d.OVERVIEW;
   private int E;
   private com.yiyiaddon.e.t.b.a.b a = com.yiyiaddon.e.t.b.a.b.a();

   public a(Screen var1, com.yiyiaddon.e.t.a var2) {
      super(
         (String)com.yiyiaddon.m.b.a<"s1ccy5y16sqqf8","UXsVhMAq8hnk4p1OodJhqF4X4YWyQRxibAZT4XymnDEFuQYydHcVz8oH",5765141744014168746,5368507643009808645,5000183403456874354,1036837866325278927>(),
         var1
      );
      this.a = var2;
      this.c(var2::v);
      this.c = var2.a();
      this.d().a(this.a);
      this.C();
   }

   public Set<String> k() {
      return this.aC;
   }

   @Override
   protected void init() {
      super.init();
      com.yiyiaddon.d.a.b.a(
         (String)com.yiyiaddon.m.b.a<"slpu1u2xfqs3g","UKwM3/eyyOoQ02uHe1D88f8OK0tRMiMFVZvmalTyoNjSEGaPB8KF1dF78jmQ0Pbfj4ZKsc9I3yOeme6jbko9Y1sxNSQ=",929274694849378255,8384632774542740667,-2440839820742831606,6353953974302719570>(),
         com.yiyiaddon.d.a.c.TICK,
         var1 -> this.B()
      );
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s17r3apu6cxxjg","qRz/mTuBbe6QiYC7kBwJOVRZyQYVtiXa9djiU6J4etw=",4665936521173122162,-2437598034647200390,7198639447196710467,6436896030509083559>()) {
            case 455735447:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s10n3uwzjdupet","ESPUOTaiOilLda5DI6R5gbpGFKoc8/j+2xjal3ZaB0s=",-689278436231966860,-6020223440790491054,5193137412584236665,-5617772954663140561>()) {
                  case 1011651989:
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
   public void removed() {
      com.yiyiaddon.d.a.b.h(
         (String)com.yiyiaddon.m.b.a<"slpu1u2xfqs3g","UKwM3/eyyOoQ02uHe1D88f8OK0tRMiMFVZvmalTyoNjSEGaPB8KF1dF78jmQ0Pbfj4ZKsc9I3yOeme6jbko9Y1sxNSQ=",929274694849378255,8384632774542740667,-2440839820742831606,6353953974302719570>()
      );
      super.removed();
   }

   private void B() {
      if (++this.E < 20) {
         switch ((int)com.yiyiaddon.m.b.a<"s23smtq0jivh27","ya23HVKqtkM8BpQVqjbhHnjMZiXZtgBDhFMXPyI2G4k=",-8246242552479200493,4194731711313803695,3220325848019958378,1002847261993618680>()) {
            case -933742984:
               return;
            default:
               throw null;
         }
      } else {
         this.E = 0;
         if (this.minecraft != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s12grioglmakia","/ZBP1jyHiZ17mUFW6rsInnXD+2N2/Y0EdH+Lw4H+o2k=",1697554589596473714,2215653858583932089,-8698916371818292348,-2900217498896177781>()) {
               case 923224131:
                  if (this.minecraft.screen == this) {
                     if (!c(0)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s19es08tvs4xs9","I9WmpXxAyQr6P2CD1OVC8gxa9pW1vz7UOA7k7Zix2KU=",-765650171365609002,1995766187400732148,266356043040983674,-8163105424428861633>()) {
                           case 493097932:
                              if (!c(1)) {
                                 if (this.a == com.yiyiaddon.e.t.b.a.d.OVERVIEW) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2khup58fk30lb","f1xFFtfgwRuVJwVKGEC5eDYepvoK/2QSAQ9H97TZjzs=",-1888841480569310448,7074032763684616474,-915138069638990002,7334116785714601357>()) {
                                       case -1277718614:
                                          this.C();
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.a = com.yiyiaddon.e.t.b.a.b.a(this.a);
                                 return;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"svpx6ozw8swvf","sTvGBMThR/gCVKjHlwTdvYw9up2mq9eGEIE6b3/7Dw0=",2632266322103756345,1685360309081508324,4911975942792019372,4708372057080034749>()) {
                                 case -44142509:
                                    return;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s34fk3ghhcxfde","09drFeDY4xc0YfVE5COZgZMQg3qw6GkKctqxnqXTx6o=",6737639623331779880,-1624242659966386196,-2229704677566326560,-6208685198977404399>()) {
                        case 2053283634:
                           return;
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         }
      }
   }

   private static boolean c(int var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sruoknx2bkfgh","i4eo1NZf6wpYVH95exh5Mu2bEBLSfs5/2Bn5tzRfnWw=",3806957884424099144,-3661723581609275434,1004555090404584785,-4342575313042113522>()) {
            case -1322985329:
               if (var1.getWindow() != null) {
                  if (GLFW.glfwGetMouseButton(var1.getWindow().handle(), var0) == 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2w9tem8n0h2q","dkzWbkssKP8/72FqH6irjkc8+bYLb2F2IIzgFJQBID0=",-615204746386625164,6957437840241972939,9152328969677481134,-8231045317390271004>()) {
                        case -653083481:
                           switch ((int)com.yiyiaddon.m.b.a<"s2diu6h90cgmpq","lvqA/wIl02CMthOfoMFExruXn58SPG++Hv4+1QiVG/c=",2875788028556203235,1669748966517735339,-5584911700820329726,-7869784108729330225>()) {
                              case 1804566362:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"sed28mfci5qng","rmMC3hCe13ztrIquBN+hVn/nwXsPId4r8Eh98T/KGxc=",-1163225239056247954,3278657513139805546,-7437116784835286908,-2865144325658844974>()) {
                        case -573311543:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"scqcr5dzad3rk","9kLJcImejLEKu3PmYKzN2dUrYOpnbhQdXXoT0OjULYo=",1498360033161562427,-5362726971762227337,7720377116704426898,-8709881033886693335>()) {
                     case -1385220721:
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

   public void C() {
      m.lb();
      this.a = com.yiyiaddon.e.t.b.a.b.a(this.a);
      this.a.u();
   }

   private void a(com.yiyiaddon.e.t.b.a.d var1) {
      this.a = var1;
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3byxeq656elw2","oAgEQ8d7jHRYpwpzzHyLAsNYgts30YJs+IrnsIzdvNU=",2200808780872981615,3442762079045268753,5759663681792687677,4514730991203564573>()) {
            case 1808709409:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s3hz5sjsgmwtcc","cps9YqsOkvYBzJqqSG+jIbAdGgXaeGukFOJzOd5DhoY=",8500045339835197641,590293991242385812,-9062405637566921411,-5648276421233287295>()) {
                  case 156992463:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"s1npzu83737l6m","KUerFRwjO9p2SDtRMPVdSKNUs//ilxc7ewdJygviuG4=",-9134364599144362162,260685199642882282,-8741532730693151680,-2588845622781630587>()) {
            case -191184737:
               return;
            default:
               throw null;
         }
      }
   }

   @Override
   public void a(String var1, float var2, float var3) {
      j.c(var1, var2, var3);
   }

   private void a(i var1) {
      var1.a(new com.yiyiaddon.l.c.a(this.a));
      var1.a(new com.yiyiaddon.e.t.b.a.c());
      this.b(var1);
      label23:
      switch (this.a) {
         case OVERVIEW:
            this.K(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s1vv44hg30bjra","F76vIfX5q9r6WY/1UEQxKW0xBU6wsZ9mOqu16niqWHo=",-8979877224919113453,7769170545861774059,8630555768894873485,7748784480819527470>()) {
               case 98013315:
                  break label23;
               default:
                  throw null;
            }
         case RANGE:
            this.L(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s1a8kyavbxbake","FCr9fx4rnDJLgSCyDbbSJRrRVFpCVEjC3d47J6+4XEk=",-3441726774610122247,2460497431322782098,-4816226305283069486,860845008570901868>()) {
               case -813081700:
                  break label23;
               default:
                  throw null;
            }
         case SUGGEST:
            this.M(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2s6fir8i3ym5h","BnyFAwVBSQ/WvKTp4i463K+CnRYaq/kTLiJI8CW92i0=",1531662421100594705,-2648328615252083704,-3703647353786299677,7312808264169472242>()) {
               case -1079731259:
                  break label23;
               default:
                  throw null;
            }
         case ADVANCED:
            this.N(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s21icubfiwy90m","zqVGkk+PobL8CZJz5NGDa5eaZF445NFxqybk7Yc1AE4=",2571097024114435140,-3444103805570558748,-3598885139652123871,-735564729063845177>()) {
               case 1251823811:
                  break;
               default:
                  throw null;
            }
      }

      this.c(var1);
   }

   private void b(i var1) {
      ArrayList var2 = new ArrayList();

      for (com.yiyiaddon.e.t.b.a.d var6 : com.yiyiaddon.e.t.b.a.d.values()) {
         boolean var7 = var6 == this.a;
         com.yiyiaddon.l.j.a var8 = new com.yiyiaddon.l.j.a(var7 ? var6.D() + "" : var6.D() + "", () -> this.a(var6));
         var2.add(
            new com.yiyiaddon.l.c.f.c(
               var8,
               () -> {
                  if (var6 == this.a) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2imhv4zji4hbr","V02fVg4lciAhENOWLbEY6bEkZI7tfGHlFibuwNNkU+g=",-2614598412234868985,2964974527047806633,5942048206392152940,2543067131167460548>()) {
                        case 1413986607:
                           switch ((int)com.yiyiaddon.m.b.a<"s37vvko12rxlaa","S4mz8o2DCmwuMfZoLaS9lTFPNb+y/N+qK0I3pIwoKXE=",8523941358735467895,-1843128296984271938,6758758768808633560,-5827506055990474880>()) {
                              case 1196496640:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var10000 = var6.D() + "";
                     switch ((int)com.yiyiaddon.m.b.a<"s1s90vdb1zq8hr","QKsOPaocGLSfVJDtkvSbuKQZJX58ne/KpiPoTEaRV7c=",-1262044226601641575,5535254509766318038,7003407810357475228,3803192375691619421>()) {
                        case 1499240223:
                           return var10000;
                        default:
                           throw null;
                     }
                  }
               }
            )
         );
      }

      var1.a(new com.yiyiaddon.l.c.f.a(this, var2, 24.0F));
   }

   private void c(i var1) {
      var1.a(
         new com.yiyiaddon.l.c.f.a(
            this,
            List.of(
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"s1iftq1dxd5t6u","84oiORfJPhEgjCwy3IoTFNKmVaOYamSWvJ6wJWxsY4VqC2X3",-9145924995460963302,1710627455655058598,-5242236806727284983,3272758066426689516>(),
                     this::C
                  ),
                  (String)com.yiyiaddon.m.b.a<"s1gkv60h0vqsf8","UgWX6261vhaS+CzYPfP7iNj18aUVs0wTtLzohkWJ4lK5MFD62XRKaWoqziCQvAzNfLQ1NlUXmxN7MsncqyZx+gevRXzJ1CD744s2Y//tzPwv6KeDEuEkIMn7IQV9ZpN5RzvaqA==",-374664323830427661,-7991132694406694459,-7012748257489782315,-8838718150603012353>()
               ),
               com.yiyiaddon.l.c.f.a(this, this.a, this::C),
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"s2zgpunccgcsp7","vNyYozzinyaZJglswATW5ub8HfuRCRgE7rieG4PPbJTJ4SQN",-7681401052710422713,-1043580714438950796,-6713240321329485036,-1205760070215982709>(),
                     () -> {
                        if (this.minecraft != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"sbazbem2677sw","QYqhTmRpgfUCmcXi8rnCG9I5zgHTw32gn91r9HEiQME=",679004430853853002,2143735149574496433,-542753967497984301,1980291300342441060>()) {
                              case 1553871729:
                                 this.minecraft.setScreen(null);
                                 switch ((int)com.yiyiaddon.m.b.a<"s1byulwdu99pxg","LrQ7rthpC4HN3EFKDB85NebdNrCN3CNolD/z76DDi8c=",2212812113240783751,1699318933666070422,-1777239264700883986,-915230127242311848>()) {
                                    case 1840898355:
                                       return;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                     }
                  )
               )
            ),
            24.0F
         )
      );
   }

   private void K(i var1) {
      com.yiyiaddon.l.c.f.d var2 = new com.yiyiaddon.l.c.f.d(
         (String)com.yiyiaddon.m.b.a<"s28pbhkfgp0smw","Z53vNzxVKrWLLR9qDNzlH5rCxPqav2pKQxIwd+sDi1Lc+WR7",8742044915377280309,-1149037692753258212,5011724252204206785,-4894731681482090711>(),
         (String)com.yiyiaddon.m.b.a<"s24lori53nl706","1jd27bqOvGZUFlX2c9s1rGyitacw/lh8BbPYZVApqwy7BhpAHUfs2lvTlzA=",7110410599282957799,7768608153466214590,-7868309611884851605,3825729522364059507>(),
         this.k()
      );
      var2.c()
         .a(
            new com.yiyiaddon.l.c.f.e(
               this,
               (
                     this.a.g()
                        ? (String)com.yiyiaddon.m.b.a<"s1bdqwpphd1nu","vQH2AlInWfixDkvDc5po4z/ZkC3S/0HGqIwpd3HciodwQEbH4LU=",8210972313222754960,9040414355882992064,3328171773958293394,-1131702591499787360>()
                        : (String)com.yiyiaddon.m.b.a<"s2mq3gyzntloh5","oMvRkbUFazbtangPYJnyMy8gpqsDpoPNgfXk0RB4VIcFptK91QA=",-7480039036365436976,8468064602920891432,-9050063421542274571,-2365760186782560622>()
                  )
                  + ""
            )
         );
      var2.c().a(new com.yiyiaddon.l.c.f.e(this, "" + this.a.dk() + this.a.dl()));
      var2.c().a(new com.yiyiaddon.l.c.f.e(this, a(this.c.fe) + a(this.c.ff) + a(this.c.fg)));
      var2.c().a(new com.yiyiaddon.l.c.f.e(this, "" + this.c.J + this.c.rU));
      var1.a(var2);
      com.yiyiaddon.l.c.f.d var3 = new com.yiyiaddon.l.c.f.d(
         (String)com.yiyiaddon.m.b.a<"s117xldb4jydaj","W6nh07woStOQ6XX8ph3dvivZMEFzoFB6Z6pQEnI8UHW7WstY",-312972969120239769,-461300847020147598,-2276434909965654886,8077380237218517119>(),
         (String)com.yiyiaddon.m.b.a<"sboy86ujwkfmg","Y4ljfLEFkUhJHm9qbfUwA9WmVM6RpB44mOAVuqgB5ssP8fH6XhkJ/3gqERM=",-8234182707877523544,5178857824275039045,-8639346988582392221,226134921565758991>(),
         this.k()
      );
      var3.c()
         .a(
            this.a(
               (String)com.yiyiaddon.m.b.a<"s22px7juubpcf4","1eeMTIVNVFHMbkopGrfOicwdbAJWaKvuRd+QRRfJSctKhvfP",-2660565084635558697,-7480853760471469161,6808421501034095862,92729761611534477>(),
               (String)com.yiyiaddon.m.b.a<"stbrsrc19m0jr","RaHU9HWe88AT300nkzQaTLnBo+jZpVa+l30Vd3dYcP8rX+GfE2MDbkqCdnCHaJtuVtuAb/Ug",-2114446426482945334,-8527889842854078473,9147817333067571520,5950471805144317070>(),
               4.0,
               32.0,
               () -> (double)this.c.J,
               var1x -> this.c.J = var1x.intValue(),
               () -> (double)b.J
            )
         );
      var3.c()
         .a(
            this.a(
               (String)com.yiyiaddon.m.b.a<"s2y9b4makkvaym","zND9RudCv2hXKU0Twn2TL6fEQ4rzB1Eg0yNkXEYIbBs4kznx",-2293589028633813426,-1261470808685246631,2556383830128652383,-2718872104015039321>(),
               (String)com.yiyiaddon.m.b.a<"szkyba3u58f61","RrdIB3ebrfGxgkvY1OBXccLphIuzPdV6/FZHXSa8izx94qljTbAu4/Wve/3jaQmRFB6WM+H1fHd+KNy+ZFZ9gLMQttW7rPA6lt8=",-4857083716297122962,130277522739335655,4557757549549898214,5849292467746447833>(),
               4.0,
               64.0,
               () -> (double)this.c.rU,
               var1x -> this.c.rU = var1x.intValue(),
               () -> (double)b.rU
            )
         );
      var1.a(var3);
      com.yiyiaddon.l.c.f.d var4 = new com.yiyiaddon.l.c.f.d(
         (String)com.yiyiaddon.m.b.a<"s1skckw8aq4b0s","cZr+KJ5W9oLLrXPByG8S+dMa9Ksu5EfGHd5g+z4UR0wKFDts",-8341280530847215740,1824752584128465508,-5102111737450350903,1509883569714183716>(),
         (String)com.yiyiaddon.m.b.a<"s19ue8gbbchmr1","lDC4AG5LEkFYWFgb3gfnBRBqiY9A2g4wtVS22qjUgdF7e51cfnFNHOeHSy8=",2424761903102437317,8813596275136403022,-8242656472694714608,-7747840275735969847>(),
         this.k()
      );
      var4.c()
         .a(
            new com.yiyiaddon.l.c.f.e(
               this,
               (String)com.yiyiaddon.m.b.a<"s303d2sqfd54ym","xl1PmRSD4XuW55ziHsPP7PPFYix6prnrMF5G1FuJ8KIPuYmSvslE6SpQS/K7wiWf0DX3oWQrhH3cG3wAwj8SauV3Rv06WlZaWTDn0/QKazyBUbKz7A/3vFKzilgG+CVWc0z1UeQKLLtnOQIjnn5Czriy",-1736731784510310856,-3291682896555964256,589802850421639147,3842964668379649770>()
            )
         );
      var4.c()
         .a(
            new com.yiyiaddon.l.c.f.e(
               this,
               (String)com.yiyiaddon.m.b.a<"s3d10sd681j6n2","UaE2ci41bRAHY4scIsEGzVc3VCcq09ks1KvVVXZyFgZBz9AhiJlXFqDVuXANd0h5QQf477ozCDlIApwxDKlBiuYaf0qmbY1YoqgU7yl6j9tbuspybdhCrLa8TDQnSiCbn2k=",-7193397870151708445,-1578559886706515244,5843519289205746160,-1374335807252807411>()
            )
         );
      var1.a(var4);
   }

   private void L(i var1) {
      com.yiyiaddon.l.c.f.d var2 = new com.yiyiaddon.l.c.f.d(
         (String)com.yiyiaddon.m.b.a<"s3efxhj2g4dseh","55hAAzMDWPLGJHuWgu1tOduH5+B41Ib0Vq0j6YSydtcgr89IdZ468g==",5739533184824854501,624842441086112077,22547782781703894,5144492386050502059>(),
         (String)com.yiyiaddon.m.b.a<"s161xbzjo7w6m2","lDj2jsvwEp8BHQXnUMXrUX7cU0z665WuOuuw84UtbOLkzWMWyiA=",4546336248591788849,-2027315233184046962,1306073511031487114,5226038639535795025>(),
         this.k()
      );
      var2.c()
         .a(
            this.a(
               (String)com.yiyiaddon.m.b.a<"s3a433y5xd4by","czrRvnH3zkjpSn7QJQbC7rYObXO/zLFRL1WphnZgpg5FEwrJ237UUg==",-2565861222864301888,6358169588335168213,8158847320889381324,3359357074720262478>(),
               (String)com.yiyiaddon.m.b.a<"s3q06zxjet6p3r","g/WlcH74/QjsIr7g41UwkSiPl/dVg885qzGA4s+1DxjoDF4vkhWX+vPOZmyZUpIL3eSEB4bzkpMfC4tBv3Z+cuNv8f63BV4mLaLFmQ==",3297569107632661479,-3265879335804264210,8341796779313186413,-644366029089845368>(),
               () -> this.c.fe,
               var1x -> this.c.fe = var1x,
               () -> b.fe
            )
         );
      var2.c()
         .a(
            this.a(
               (String)com.yiyiaddon.m.b.a<"s1j3yav4n9ecku","HgrtJy1ghz7MHUSlxWcAvdx1gAdiXvk53qzFJaEGY76+5sg5",4111876807730098016,454921847472581254,8410847610158004110,-2063112728969252179>(),
               (String)com.yiyiaddon.m.b.a<"s3oul2ddc0wzth","2RbEojMa7qOK1eRzmHc5VI2X3UJhH3NKzOG8kLZnwwSS2CEbZt+jakTA",-6532499117948043421,-6852067879109084495,-738807490019963758,1107461124114475544>(),
               this.c.W,
               b.W
            )
         );
      var1.a(var2);
   }

   private void M(i var1) {
      com.yiyiaddon.l.c.f.d var2 = new com.yiyiaddon.l.c.f.d(
         (String)com.yiyiaddon.m.b.a<"s26o4fclcxjs37","/TWiFdLp/q5Y84RnX21zqb9ZAk2iGzsnwBUQZalAEsbUqDZYEoQ=",6381664894191491831,4997724901719256014,6000304273134456910,-9124283257074221243>(),
         (String)com.yiyiaddon.m.b.a<"s1a31u95qxjhtt","uyLjg4ISCngN1A79liYoPF8oN0kc66KzVj131EkogjajX0hYb3wW5HW8",-7070547378168430578,972541875980191872,-1682108564595071021,2011080542425537750>(),
         this.k()
      );
      var2.c()
         .a(
            this.a(
               (String)com.yiyiaddon.m.b.a<"s3j2sd448bp94d","K2o5ElFv7EyMJ/PjesoFD7V39h/GUfk0+vpYJVr/UeeuoDCBEQs=",-3852187172711910362,-8158318650647612493,-6968670892594634262,3935642050438092699>(),
               (String)com.yiyiaddon.m.b.a<"s2r5qi693gymre","SQElzeBu4PLRHPVktfL/SdFGTbOP0zD7uCjb7lnrUSWlqHvKRO1JjUmF4sCjv5WBYGzi4nCg6XPWOjxy12vPCgi0kEOX6zkv",-498292038035867105,-7660942907925164637,-7871997297872245723,-7140393134109644376>(),
               () -> this.c.ff,
               var1x -> this.c.ff = var1x,
               () -> b.ff
            )
         );
      var2.c()
         .a(
            this.a(
               (String)com.yiyiaddon.m.b.a<"skt6flc2s6laq","SdLS1Qkm9ZLQmK/AV7TzqcPofxIH2ic3x2uV04gP7XkDfsGp8Nc=",-3710272249571176953,7589355305978666287,3555944393223517326,-6281053301099335297>(),
               (String)com.yiyiaddon.m.b.a<"s2n5sek8g5ufmb","zEvUDCKIkdbCDW7e1Gtrec+rRymsLOmL8PCzdKxZD/AdcXy/jqGFb1zXiOQ=",-7837402866642752722,5018161544719415979,2756508744488490008,1095831487494307793>(),
               this.c.X,
               b.X
            )
         );
      var2.c()
         .a(
            this.d(
               (String)com.yiyiaddon.m.b.a<"s158o7am87l3uk","dOplyUJY/ZeShuhI9pGM+cvXZ1d0BqpMcximNqWdpi3dhfhKKog=",-1626673469345005241,7861131632684915756,1392690791197602996,-5561863162815632229>(),
               (String)com.yiyiaddon.m.b.a<"sq7mnh6en6od7","4KWHxeGgq6M0/r0hr5HIMksz9OTHa8m6tq03OLGqiBSFo9Xlfk9QqLG0gYLE3F/3QYvO0O3p/5kauB07YL4Qn2IHaf2lor/gCUOJN+rtbNXfyezK",3703222855471290606,-6275866842755807209,3118009639242932486,4888783390826369251>(),
               () -> this.c.f,
               var1x -> this.c.f = var1x,
               () -> b.f.cr()
            )
         );
      var1.a(var2);
   }

   private void N(i var1) {
      com.yiyiaddon.l.c.f.d var2 = new com.yiyiaddon.l.c.f.d(
         (String)com.yiyiaddon.m.b.a<"s3be6p7u376f2x","4whntPNfiAv9EWZ8b8B5Q4bvYL62Os7zMgqWvqgDBkYW2/or",6232008194499918804,-6424185063299158442,-3253290510060334519,2066211216436111782>(),
         (String)com.yiyiaddon.m.b.a<"s2t662oex63qe5","+Q2I2jlpY8rJHiLWmGcN/N8eTElNbe7mi/cTEOAxRGm8HljFeGrKYY0K9PQ=",-7666916782927897423,784815431561104905,-6649053970968123902,8751495361588144220>(),
         this.k()
      );
      var2.c()
         .a(
            this.a(
               (String)com.yiyiaddon.m.b.a<"s2kjra61ll8vnm","BfZU3WE5ADL2RXOgvA+/I0m//jidW6gxzM+2gd7Io0kIp1Xkw19o8A==",7011127661365895668,4621138363003756700,-2178086813316341492,-6167014831649264998>(),
               (String)com.yiyiaddon.m.b.a<"s2kcixub1c8il0","wHi+Am9RCCaqZg/Mq289JcINT6P3x4EKSHz4QU0vN4iDwuOrdzcA14FLNgE3pXSRCMbhB1VdniCuWL8/ktz9yqjB8RX+/bSA71V8uYJV8eAkRxKK",8241317407318849782,1740333702722028392,6593204888724275479,2119324455095990216>(),
               () -> this.c.fg,
               var1x -> this.c.fg = var1x,
               () -> b.fg
            )
         );
      var2.c()
         .a(
            this.a(
               (String)com.yiyiaddon.m.b.a<"skdk5jm48lta2","78CABYZ1hvUWJqnzIyx6wp0FjJEsJ2Xr2fPc9MlZCEcFCJD8Ukj7YQ==",3205471727339361037,-3704440917959643213,-7383179804913497722,4206055409496766037>(),
               null,
               this.c.Y,
               b.Y
            )
         );
      var2.c()
         .a(
            this.d(
               (String)com.yiyiaddon.m.b.a<"s2ncajioccayvz","unukVpucRs5Btpn5ut3JSUX7jboL2dicaVnLna9eglWW2Q1LU6n/wQ==",-5268347675201287464,-6096089727125607490,-347611672298570730,-2336086193162544190>(),
               (String)com.yiyiaddon.m.b.a<"sq7mnh6en6od7","4KWHxeGgq6M0/r0hr5HIMksz9OTHa8m6tq03OLGqiBSFo9Xlfk9QqLG0gYLE3F/3QYvO0O3p/5kauB07YL4Qn2IHaf2lor/gCUOJN+rtbNXfyezK",3703222855471290606,-6275866842755807209,3118009639242932486,4888783390826369251>(),
               () -> this.c.g,
               var1x -> this.c.g = var1x,
               () -> b.g.cr()
            )
         );
      var1.a(var2);
   }

   private static String a(boolean var0) {
      if (var0) {
         switch ((int)com.yiyiaddon.m.b.a<"s3qclgwzsf2dxj","afgKFAAmU/1gP24kdi4xp4yEJ5hoPdiiEPll5qyfJSg=",2722885874154889242,-1242873848186092643,5889423053178881532,1969438959199075690>()) {
            case -884777824:
               String var10000 = (String)com.yiyiaddon.m.b.a<"sibsd9f9vc7yv","h75q0z+HsQdN6ABgDOR4VEsuoJabhPILdlPQfOfRa+AhKw==",-5093183589049137642,-7910007464557046999,3841830322890751033,7229347669372359826>();
               switch ((int)com.yiyiaddon.m.b.a<"s3spyg6rzx1f2","vpB5F+Ia+D03wE3AEqYMWU7rAZBPuzKQ+GuQ0+Mo3Xg=",-2343579217576740709,4384243272984499041,265416114428862097,-6457244886825803804>()) {
                  case -469901414:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var1 = (String)com.yiyiaddon.m.b.a<"s2op8a0h379g7q","WuMbAbJQSRyG6zz1ggF64Qoxh7qTlZhz/O+hYCjIjJuYJw==",4402079990251023900,-1893828438106627359,6849723069298944340,-4181487309761580907>();
         switch ((int)com.yiyiaddon.m.b.a<"s3hlbfqkaci3a2","pmvLvnOi5279YKFiHyJmLuIv2Cob/RJCXyc0S8UxaDc=",2425818657586071569,-1552459551931196390,2973250272083514890,6455956017029272085>()) {
            case -136246044:
               return var1;
            default:
               throw null;
         }
      }
   }

   private com.yiyiaddon.l.c.f.b a(String var1, String var2, Supplier<Boolean> var3, Consumer<Boolean> var4, Supplier<Boolean> var5) {
      return new com.yiyiaddon.l.c.f.b(this, () -> var1, var2, null, List.of(new com.yiyiaddon.l.c.f.c(new n(var3, var2x -> {
         var4.accept(var2x);
         this.a.L();
      })), com.yiyiaddon.l.c.f.b(() -> {
         var4.accept((Boolean)var5.get());
         this.a.L();
         this.C();
      }, var1)));
   }

   private com.yiyiaddon.l.c.f.b a(String var1, String var2, double var3, double var5, Supplier<Double> var7, Consumer<Double> var8, Supplier<Double> var9) {
      return new com.yiyiaddon.l.c.f.b(
         this,
         () -> var1,
         var2,
         null,
         List.of(
            new com.yiyiaddon.l.c.f.c(
               new com.yiyiaddon.l.j.i(
                  var3,
                  var5,
                  1.0,
                  (String)com.yiyiaddon.m.b.a<"s18dg7l2rrq514","aVknI4SYz9feOywSn/DKsykvVug6klRIXIbDn/aljQEpoLP0",2411508685337051456,8461463874777659123,9064050101301487263,-1657484459951833470>(),
                  var7,
                  var2x -> {
                     var8.accept(var2x);
                     this.a.L();
                  }
               )
            ),
            com.yiyiaddon.l.c.f.b(() -> {
               var8.accept((Double)var9.get());
               this.a.L();
               this.C();
            }, var1)
         )
      );
   }

   private com.yiyiaddon.l.c.f.b a(String var1, String var2, com.yiyiaddon.l.g.a.d var3, com.yiyiaddon.l.g.a.d var4) {
      return new com.yiyiaddon.l.c.f.b(
         this,
         () -> var1,
         var2,
         (String)com.yiyiaddon.m.b.a<"ss61nnhrepkpk","DZPjkPa5vINs9rNAvcrEaXVONCM2E+Is7MeurA1rTSSk3SZ1jsvum7C8MW3MRHpLMW0=",-262632687631977498,-6500963699729477137,391477889442536036,-4077311303879884579>(),
         List.of(new com.yiyiaddon.l.c.f.c(new com.yiyiaddon.l.j.c(var1, var3, this.a::L)), com.yiyiaddon.l.c.f.b(() -> {
            a(var3, var4);
            this.a.L();
            this.C();
         }, var1))
      );
   }

   private com.yiyiaddon.l.c.f.b d(String var1, String var2, Supplier<com.yiyiaddon.l.g.a.j> var3, Consumer<com.yiyiaddon.l.g.a.j> var4, Supplier<Integer> var5) {
      return new com.yiyiaddon.l.c.f.b(
         this,
         () -> var1,
         var2,
         (String)com.yiyiaddon.m.b.a<"s2j1ldpl0gyi6q","IpRzwIaoeNnzGOUOAjbQJQxfwfnrxAnOiQJ1kgkX5QrjmdIU/JEk0Q==",-3993751148500335829,3535974629347853647,5627266343396197636,3060099100082850968>(),
         List.of(new com.yiyiaddon.l.c.f.c(new e(List.of(com.yiyiaddon.l.g.a.j.b()), () -> ((com.yiyiaddon.l.g.a.j)var3.get()).cr(), var2x -> {
            var4.accept(com.yiyiaddon.l.g.a.j.a(var2x));
            this.a.L();
         })), com.yiyiaddon.l.c.f.b(() -> {
            var4.accept(com.yiyiaddon.l.g.a.j.a((Integer)var5.get()));
            this.a.L();
            this.C();
         }, var1))
      );
   }

   private static void a(com.yiyiaddon.l.g.a.d var0, com.yiyiaddon.l.g.a.d var1) {
      var0.b(var1.eh()).c(var1.ei()).a(var1.gj()).a(var1.l()).b(var1.m());
   }

   private final class a implements g {
      private i a = new i(6.0F);

      private void u() {
         i var1 = new i(6.0F);
         a.this.a(var1);
         this.a = var1;
      }

      @Override
      public float b() {
         return this.a.b();
      }

      @Override
      public void a(float var1) {
         this.a.a(var1);
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         this.a.a(var1, var2, var3, var4, var5, var3, var3 + this.a.b(), var6, var7);
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
         return this.a.a(var1, var2, var3, var4, var5, Float.MAX_VALUE, var6);
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5) {
         return this.a.a(var1, var2, var3, var4, var5, Float.MAX_VALUE);
      }

      @Override
      public void F() {
         this.a.F();
      }
   }

   private static final class b {
      private final String[] aj;

      private b(String[] var1) {
         this.aj = var1;
      }

      private static com.yiyiaddon.e.t.b.a.b a() {
         return new com.yiyiaddon.e.t.b.a.b(
            new String[]{
               (String)com.yiyiaddon.m.b.a<"s29kg6ksxhpomq","pM54KiVa2Na4iVctezEaq/z24lvQOIQ4rqAocQ==",-2078205391205717385,6248912800616428758,-1834473096985757824,7702018825805702218>(),
               (String)com.yiyiaddon.m.b.a<"s29kg6ksxhpomq","pM54KiVa2Na4iVctezEaq/z24lvQOIQ4rqAocQ==",-2078205391205717385,6248912800616428758,-1834473096985757824,7702018825805702218>(),
               (String)com.yiyiaddon.m.b.a<"s29kg6ksxhpomq","pM54KiVa2Na4iVctezEaq/z24lvQOIQ4rqAocQ==",-2078205391205717385,6248912800616428758,-1834473096985757824,7702018825805702218>(),
               (String)com.yiyiaddon.m.b.a<"s29kg6ksxhpomq","pM54KiVa2Na4iVctezEaq/z24lvQOIQ4rqAocQ==",-2078205391205717385,6248912800616428758,-1834473096985757824,7702018825805702218>(),
               (String)com.yiyiaddon.m.b.a<"s29kg6ksxhpomq","pM54KiVa2Na4iVctezEaq/z24lvQOIQ4rqAocQ==",-2078205391205717385,6248912800616428758,-1834473096985757824,7702018825805702218>(),
               (String)com.yiyiaddon.m.b.a<"s29kg6ksxhpomq","pM54KiVa2Na4iVctezEaq/z24lvQOIQ4rqAocQ==",-2078205391205717385,6248912800616428758,-1834473096985757824,7702018825805702218>()
            }
         );
      }

      private static com.yiyiaddon.e.t.b.a.b a(com.yiyiaddon.e.t.a var0) {
         if (var0 == null) {
            return a();
         }

         com.yiyiaddon.e.t.a.a var1 = var0.a();
         return new com.yiyiaddon.e.t.b.a.b(
            new String[]{
               (
                     var0.g()
                        ? (String)com.yiyiaddon.m.b.a<"sck409c9msovq","TV2RXaFRuNJrOlWsPg6gX3HGyAgBFvHYtTghUJtSAk1vjYkgFs8=",8855256701094304252,698977411211272589,-8836221179650447511,7376122230720683435>()
                        : (String)com.yiyiaddon.m.b.a<"s3fmda8c5gzdgx","vmPg0YKTO+pBUxRNIBdLdVwbXmVCcpDAgnbolN8pycBXNPtkH5c=",4626839718507687873,4928740482596218666,1268540531124616797,-3287260434354086778>()
                  )
                  + "",
               var1.J + "",
               var1.rU + "",
               var0.dk() + "",
               var0.dl() + "",
               com.yiyiaddon.e.t.b.a.a(var1.fe) + com.yiyiaddon.e.t.b.a.a(var1.ff) + com.yiyiaddon.e.t.b.a.a(var1.fg)
            }
         );
      }

      private String c(int var1) {
         return this.aj[var1];
      }
   }

   private final class c implements g {
      private static final float eB = 18.0F;
      private static final float eC = 6.0F;
      private static final int rW = 3;

      @Override
      public float b() {
         return 36.0F;
      }

      @Override
      public void a(float var1) {
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.e.t.b.a.b var8 = a.this.a;
         int var9 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s1dkwoyltlbmr5","B2IamyrCG+pDBq1UvVCNEJtGHT44jRTK20ajQKw62Uk=",1926090664588909503,4373760370201863211,984935593676064903,-1317737533880803752>()) {
            case -1302460945:
               while (var9 < 6) {
                  switch ((int)com.yiyiaddon.m.b.a<"surp87g8gmlid","D1N3WRwMuJRqbu38nNJR0GXyYHqY3NBfYD5+GzUrgrc=",-2978900094017934452,-6705219007765937651,-6553256938979090516,5904144321019692088>()) {
                     case -1937847095:
                        int var10 = var9 / 3;
                        int var11 = var9 % 3;
                        float var12 = var2 + 6.0F + Math.max(0.0F, var4 - 12.0F) * var11 / 3.0F;
                        this.a(var1, var8.c(var9), var12, var3 + 18.0F * var10, Math.max(0.0F, var4 - 12.0F) / 3.0F, var5);
                        var9++;
                        switch ((int)com.yiyiaddon.m.b.a<"s1lladn7ce374l","9Wees2m25DAQ/gSpL9YvAxDwmNveaO1tof83YsURKSM=",-2933936616172089881,8568191027610004472,5403152577086612849,6066755546737387122>()) {
                           case -1943666398:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return;
            default:
               throw null;
         }
      }

      private void a(Canvas var1, String var2, float var3, float var4, float var5, float var6) {
         com.yiyiaddon.l.g.d.a(
            var1, com.yiyiaddon.l.g.d.b(var2, 11.0F, var5), var3, com.yiyiaddon.l.b.d.c(var4 + 9.0F, 11.0F), 11.0F, com.yiyiaddon.l.i.c.a().uT, var6
         );
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

   private enum d {
      OVERVIEW(
         (String)com.yiyiaddon.m.b.a<"s2ob59d1ek4cko","xQuFFeOojLKv0ttzeyYyAA3xhdxBQM1abpPHkgSGsDM=",1624141339417498306,8988758671336609516,-8305433761675479515,5665104518599893151>()
      ),
      RANGE(
         (String)com.yiyiaddon.m.b.a<"s30gh1fhxtxx9x","Km4BndnqESjiSbWziB/FX0Q6A+6BCtm4ehBRR+l3sd+pbZBcHBuqHA==",852459307913557827,8810588365454533706,9130588832186240446,-2879871892872657639>()
      ),
      SUGGEST(
         (String)com.yiyiaddon.m.b.a<"s1o53lf76h9t7o","EaE+BV6/nvHvS2HOVtWjVPmqAQCwE7TNUdl6tWt0C4/JmI7EWP8=",8060752709543667165,6021517119985176135,335859221207428986,-6357497896620918734>()
      ),
      ADVANCED(
         (String)com.yiyiaddon.m.b.a<"s2fwxblv0of8y4","Ug4gKS+VtXZRNf+e8iP8oK+RyDVY8zF/5HQri8ieBTtw8Hw3",-4398295710384257729,3862732772512663906,-2206319191233490429,-8631452442527982764>()
      );

      private final String BG;

      d(String var3) {
         this.BG = var3;
      }

      private String D() {
         return this.BG;
      }
   }
}
