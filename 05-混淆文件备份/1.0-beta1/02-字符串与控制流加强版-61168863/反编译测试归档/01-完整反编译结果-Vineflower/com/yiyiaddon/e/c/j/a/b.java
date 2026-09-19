package com.yiyiaddon.e.c.j.a;

import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.g.j;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.world.item.ItemStack;

public final class b extends com.yiyiaddon.l.h.f implements com.yiyiaddon.l.c.b {
   private static final com.yiyiaddon.e.c.b.a b = new com.yiyiaddon.e.c.b.a();
   private final a c;
   private final com.yiyiaddon.e.c.a e;
   private final Set<String> o = new HashSet<>();

   public b(a var1, com.yiyiaddon.e.c.a var2) {
      super(
         (String)com.yiyiaddon.m.b.a<"sicnlzp7bn87w","hL0tBYyR8t5ic0VxtDHJoXsv2xT7l4xGZ4IzA1JmrgPQcQeyvahzmPn1",-7526968756728661032,-6906914092947976081,509980892401257118,6736635996790744110>(),
         var1
      );
      this.c = var1;
      this.e = var2;
      this.u();
   }

   @Override
   protected void init() {
      super.init();
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sms4w0udo913y","voelEr7HhOx/MGRonYqBbBihxOnk+In2kQWXG8UF2LA=",-7317978600194686591,-5191087829063554073,-1303872597629885315,-6549412644149610542>()) {
            case -415243412:
               this.minecraft.execute(this::u);
               switch ((int)com.yiyiaddon.m.b.a<"s3iq5qhbnidwql","TJCU+vfDUvLIc7lttJoA94M8wOtvRahfH4GE2vgb6pw=",-4886674127446291402,710952627766008836,-1279911097724232874,9162665433266085449>()) {
                  case 2066096167:
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
      super.removed();
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3aa9n8odmn07d","wehHxXO1drXh3JMtiIEU68ZyUEZZbkjPMVVxwLK1rEw=",-8090997691191158488,7743652414167198688,7529751580524437263,979209880860495901>()) {
            case 1079944970:
               if (this.minecraft.screen == this.c) {
                  switch ((int)com.yiyiaddon.m.b.a<"s22nwsj543iwde","8jXazRectyxjQdxX+StZ+vyYx6ixUvvtyAPHBXTkwrM=",-8327954722613057028,-2746123211820740676,3502209156000707860,-5690804945168337727>()) {
                     case 1535830570:
                        this.minecraft.execute(this.c::C);
                        switch ((int)com.yiyiaddon.m.b.a<"s1atsa9gb9u0k4","peh1rZ+Js7s9UZsuWZIglA/4HVkzp2e0/EKJ4pruVQk=",6508906262261801821,4341133926949272130,875601816564527244,-8938199580519765506>()) {
                           case -2108149075:
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
   }

   @Override
   public void a(String var1, float var2, float var3) {
      j.c(var1, var2, var3);
   }

   public Set<String> k() {
      return this.o;
   }

   private void u() {
      i var1 = this.d();
      var1.b();
      boolean var2 = false;
      Iterator var3 = this.e.g().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"sziiwn0pe8l6d","VSUXiKuvIQhKJm98ha01iMtSDXYq5lF+A0whUvOyH5k=",8335913427224135153,-4167898004126353251,630047048504246909,-5840724470359585256>()) {
         case -322706252:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2titwkddktpez","sbxDaxVLwj+11MZ765C7mHTOIEq0NFa4ZiTjH/0CIPA=",9009968976232339588,6368479555971095882,-5140630153434440854,2514995234210770945>()) {
                  case -995969361:
                     com.yiyiaddon.e.c.d.a var4 = (com.yiyiaddon.e.c.d.a)var3.next();
                     if (var4.E()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1rs3ieaghdbjy","ZCikrkIRdSB2RbSkeJx3S6bK+KwykWoz7HKzMban8XM=",864842984364032579,-1310467636904991665,8010779652896634561,-8895279311466709080>()) {
                           case 984781544:
                              switch ((int)com.yiyiaddon.m.b.a<"s3cyj8oo1sq9yr","dM4/pW73LS72RwIKy804oNhZvzZfRCtsBRHS89k0lW0=",-4394615816753962317,-8111244610592949130,-6986619190164780977,-2891079786855142073>()) {
                                 case 1595974297:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var2 = true;
                        var1.a(this.a(var4));
                        switch ((int)com.yiyiaddon.m.b.a<"sdumo591kcpcu","d3Q0tbG8634QeLXfPWBB0FAP4SOnRB5IPPv13m0IDoo=",8243790876111113662,4443809184968606797,5069666955702450686,-1005026882744813621>()) {
                           case 1237198460:
                              continue;
                           default:
                              throw null;
                        }
                     }
                  default:
                     throw null;
               }
            }

            if (!var2) {
               switch ((int)com.yiyiaddon.m.b.a<"s2jxfk4817acxf","NBvKDtzAo4bWundi/x9pWTrx8hinmF4abAaJGk+CiDQ=",736832214793633708,-2209389851366626681,7329651260673063331,-8364181075879751518>()) {
                  case 1031234832:
                     var1.a(
                        new com.yiyiaddon.l.c.f.e(
                           this,
                           (String)com.yiyiaddon.m.b.a<"siogwop0roeyi","pZoNPPo7DkX+ffsNNtLGLEdrCRA6TGSCe6QEvX6TQnBiefSqOZwT29wo9XWJooHdZKzCm+Mzf4Asz5tV8poNUWe1PSUtEvEoqDrCp3V3LoOnB+xM",4776661568501164685,-7707062904439709534,4766012211336781467,-7577108849201499314>()
                        )
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"s174bv2qx77zt0","+3vMiPpg3CSBQXBQZI95hlUsSR1GoqFvw1hwnbeCvok=",8158089749941951788,3671949978320470631,-7825916152602818272,4804388365617009500>()) {
                        case 1792765980:
                           return;
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

   private com.yiyiaddon.l.c.f.d a(com.yiyiaddon.e.c.d.a var1) {
      com.yiyiaddon.e.c.b.a var2;
      com.yiyiaddon.l.c.f.d var3;
      boolean var10000;
      label51: {
         var2 = this.e.a();
         var3 = new com.yiyiaddon.l.c.f.d(var1.m() + "", var1.name() + "", this.k());
         var3.c().a(this.a(var1));
         if (var1.F()) {
            switch ((int)com.yiyiaddon.m.b.a<"s23qv1i0582ij","2id9PQsIAYRs8g8AT5JYxOuGuAo676swPwONari3OZM=",1778155928119440596,-6093427257529491943,-5448539970747754496,-461443679824509792>()) {
               case 465092945:
                  if (var1.a() != var1.b()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s32hftxctvd6yz","rVEt83BN8R40XQyJXQsBBGUmV71bnDuLcE+P5CJ9WoQ=",-941375721481578030,-4243689870051737610,-209373434621277124,-7402981380082043839>()) {
                        case -263346193:
                           var10000 = true;
                           switch ((int)com.yiyiaddon.m.b.a<"s1fvhhus41j4i","4mcP/GU4J59wgm9kO2oAdtmPCf1y3cdy3Xm8kPnKMmA=",-3111404580728237881,-1040934542436500729,-269083172863112372,-1588646725454591140>()) {
                              case 416083978:
                                 break label51;
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

         var10000 = false;
         switch ((int)com.yiyiaddon.m.b.a<"s22fq8uijlq1wd","yN0XUlB0Ij2K8qYxRlVk6VOD3ES/eqsQ4Y/8EoSJFXQ=",-8888398696140054502,3574311082157968240,-2051802023122477227,6470322794919110471>()) {
            case -822828703:
               break;
            default:
               throw null;
         }
      }

      boolean var4 = var10000;
      String var6;
      if (var4) {
         label35:
         switch ((int)com.yiyiaddon.m.b.a<"s2p8skqhxlt6d1","V8VLeLchsn5gXwAjIb0ulICufw84hQx8XwZVYRZod1c=",4369236518850803215,8484260651834414724,8398862496831049803,5251446164261667566>()) {
            case -2009252402:
               var6 = (String)com.yiyiaddon.m.b.a<"s39jf6loxsdcra","EwcVrJdeUxz8ZIvQxYPqjuD4tHohPvsn+sD+Sq57JBgSeD7j4/ZFKdIFwIWAvHp7YxfSy9OIAvaHeggmMnKbtwipCGsxeJt2YNKMj+/Q5I7e4nd2iBeeuS7vdgXVmnSzFl8rrZJ6QxeGPOCe",4223865199302047293,3567473435776914263,1691586450569299770,-8436518704854823019>();
               switch ((int)com.yiyiaddon.m.b.a<"s3tgz0hcjiqhs5","jZrvMR59iYRhv1RR5gXtTf1sTyr8ynXMhdxTWHc8inM=",7484747191855527936,-4159246135179022016,-4016617247042971314,-5885617860904274804>()) {
                  case 1929433380:
                     break label35;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var6 = (String)com.yiyiaddon.m.b.a<"s3vzk0btkawun3","GrtCsWJ5m3buuxP3LmlmmzGbR3TT4z6cVpimORz/1dA56l4Hy4PTWAstQ7ZOqHZjODmr1OobsI4TKrouQaEp7Vw3uS8=",8415436505815025492,-2848825537836448859,5942233335890055874,-4347226358373080575>();
         switch ((int)com.yiyiaddon.m.b.a<"s2tclbg5tpwj91","+xUIXyITfEcOYqHdCURsrZVlO+WtVRv3rXl4zpdXRkQ=",7528518252605355096,2078463359137431076,6515312951242303190,2048159140771754026>()) {
            case -1392624247:
               break;
            default:
               throw null;
         }
      }

      String var5 = var6;
      var3.c()
         .a(this.a(var1.m() + "", var5, 1.0, 36.0, () -> (double)var2.a(var1), var2x -> var2.q.put(var1.name(), var2x.intValue()), () -> (double)b.a(var1)));
      if (var1.F()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2dem1who4nl3a","tZTee1PNN+qB+oGpnK32JJLmJnkYKx8R154Rjiy5gFE=",-6688517196486712859,-7306412526412138535,-5272327378578500137,-3624070100222800373>()) {
            case -1239998885:
               var3.c()
                  .a(
                     this.a(
                        var1.m() + "",
                        (String)com.yiyiaddon.m.b.a<"s6vjrb073l91","yKUWvIUAOjreA1vxZSuaeWPwE+pZdrFrajr47VbGvRsjL6uFPmSCOfmbmv/Rd5bw7cv3Ui9x7UIW084MEQPMRXFpaYyHUuj4zORsDkLvpA+pjEA/gwPaNFMDLk2zWA==",-4811370549176578995,-1770247117588373786,-6886485013898239830,8650147305758036241>(),
                        1.0,
                        10.0,
                        () -> (double)var2.b(var1),
                        var2x -> var2.r.put(var1.name(), var2x.intValue()),
                        () -> (double)b.b(var1)
                     )
                  );
               switch ((int)com.yiyiaddon.m.b.a<"smk08ss1d78p","pqrDSJJphQmUK3Ie7evwOt4NOyYzXZbfPrx+ncMXOrU=",9148602052923968152,-3201845409378712800,-5546088274041580890,-932518884281372247>()) {
                  case 1703714167:
                     return var3;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return var3;
      }
   }

   private com.yiyiaddon.l.c.f.b a(com.yiyiaddon.e.c.d.a var1) {
      com.yiyiaddon.l.c.f.b var2 = new com.yiyiaddon.l.c.f.b(this, () -> var1.m(), null, null, List.of());
      var2.a(() -> new ItemStack(var1.b()));
      return var2;
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
                  (String)com.yiyiaddon.m.b.a<"s2sxi4vw70u6m9","tg6sWMsSXzbom/fgQEdiw01EL/fCMKHoRTBTW8ZnFzrJB+Mn",-8993958016100927099,-3355429502308780629,3979962293992596389,-7274739131414600856>(),
                  var7,
                  var2x -> {
                     var8.accept(var2x);
                     this.e.L();
                  }
               )
            ),
            com.yiyiaddon.l.c.f.b(() -> {
               var8.accept((Double)var9.get());
               this.e.L();
               this.c.C();
            }, var1)
         )
      );
   }
}
