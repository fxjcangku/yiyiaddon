package com.yiyiaddon.l.h;

import com.yiyiaddon.l.b.q;
import com.yiyiaddon.l.b.s;
import com.yiyiaddon.l.f.h;
import com.yiyiaddon.l.g.i;
import com.yiyiaddon.l.g.j;
import com.yiyiaddon.l.j.m;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.types.RRect;
import io.github.humbleui.types.Rect;
import java.util.function.Supplier;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.PreeditEvent;
import net.minecraft.network.chat.Component;

public final class e extends i {
   private static final float lH = 18.0F;
   private static final float lI = 70.0F;
   private static final float lJ = 18.0F;
   private static final float lK = 16.0F;
   private static final float lL = 58.0F;
   private static final float lM = 27.0F;
   private static final float lN = 44.0F;
   private static final float lO = 10.0F;
   private static final float lP = 40.0F;
   private static final float lQ = 8.0F;
   private static final float lR = 8.0F;
   private static final float lS = 4.0F;
   private final com.yiyiaddon.l.f.a d;
   private final q c = new q();
   private final s b = new s();
   private final com.yiyiaddon.l.b.a b = new com.yiyiaddon.l.b.a();
   private final com.yiyiaddon.l.g.g d = new com.yiyiaddon.l.g.g();
   private boolean gt;
   private boolean gv;
   private boolean gw;
   private long bi;

   public e(com.yiyiaddon.h.d var1, Screen var2) {
      super(Component.literal(var1.m()), var2);
      this.d = b(var1);
   }

   private static com.yiyiaddon.l.f.a b(com.yiyiaddon.h.d var0) {
      Supplier var1 = var0.b();
      com.yiyiaddon.l.f.i var10000;
      if (var1 == null) {
         label29:
         switch ((int)com.yiyiaddon.m.b.a<"s94w5m2uqp791","XrObZQvHWX3D2hlshTkp4R6Ow5SRUaS1FZdTBno26jk=",-9197779465468815111,8424236006301233014,-4296195511498589261,-6476848288317389981>()) {
            case 157641950:
               var10000 = null;
               switch ((int)com.yiyiaddon.m.b.a<"s43pompl2dqy","/uov4FCviCSa+1Zw74u5TYN3LXiBfYPLSRoyp45l4yc=",1077402173167883079,3275568393848171788,-6169170119375966780,8960295830555532956>()) {
                  case -455214687:
                     break label29;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = (com.yiyiaddon.l.f.i)var1.get();
         switch ((int)com.yiyiaddon.m.b.a<"sohs9sn87m52q","dz3dgNr/Hjcjmtxk9Ij0uVDr/Lgl3R2oSLfCNYx0rAU=",2760384809795315748,293019582077343728,7373916016699332148,3690296078791955821>()) {
            case -1040521296:
               break;
            default:
               throw null;
         }
      }

      com.yiyiaddon.l.f.i var2 = var10000;
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1y9ekpisohlc","5Pcb1/6WFgSlL02UEjotILt0YTFS8PM8FGBzjzFlzJI=",7750957049683477544,3694780721931289138,-5662012536903114447,-8918765067638528006>()) {
            case -839506944:
               h var3 = new h(var0);
               switch ((int)com.yiyiaddon.m.b.a<"s10imcr3dp4rui","AshUBQ5mzbXiM0L/pF+HjCNVGZKxfrCdyBcNPCLAzpQ=",4899893965111711764,941341745484963520,3775980270540577777,1013228975404387438>()) {
                  case 1533161001:
                     return var3;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.l.f.a var4 = var2.a(var0);
         switch ((int)com.yiyiaddon.m.b.a<"s1urinjzfnirm4","QwuVHhGhLs7XbdUZlnxTtlE2M+j1vYr8edZoI3CVlZQ=",-6437942872272761729,853138620050773484,-1879042408193195925,-1687197242573219567>()) {
            case 731926186:
               return var4;
            default:
               throw null;
         }
      }
   }

   @Override
   protected void init() {
      super.init();
      this.c.a(this.minecraft, 0.0F);
   }

   private float al() {
      return this.c.w() - 70.0F - 18.0F;
   }

   @Override
   protected boolean gi() {
      if (this.c.z() >= 1.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s1a720qxnehnww","XOy6T9wz4TNulW9K/MnFHiXOCaWcWzGnhFsXUchXxK0=",8312429830690693531,-2546830497417887818,1437462286353128596,3454171438831917960>()) {
            case -235658597:
               switch ((int)com.yiyiaddon.m.b.a<"s1dzgacng7m8df","bYlpLxQ8ghctGt/EICriPKukRr2fVxU0VLfJfjuveBo=",-394868633556497291,2937562070653215649,-5816051480895105785,1935141753719999098>()) {
                  case -911259297:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s3i3nh8djwj9dj","aZXENCSLbBZNjdwbSvkq4ZHM1HzzPrIl9XCj6AJS04s=",1182434667824947536,2450668969585790944,5719697451578190937,7174799461729667140>()) {
            case -1624958112:
               return false;
            default:
               throw null;
         }
      }
   }

   @Override
   protected void a(int var1, int var2, int var3, int var4, float var5) {
      if (this.minecraft != null) {
         Canvas var6 = this.d.a(com.yiyiaddon.l.g.g.ef());
         if (var6 != null) {
            try {
               this.a(var6, var1, var2, var3, var4);
            } finally {
               this.d.km();
            }
         }
      }
   }

   private void a(Canvas var1, int var2, int var3, int var4, int var5) {
      j.kv();
      long var6 = System.currentTimeMillis();
      float var8 = this.bi == 0L ? 0.016F : Math.min((float)(var6 - this.bi) / 1000.0F, 0.033F);
      this.bi = var6;
      if (this.c.a(this.minecraft, var8)) {
         super.ku();
      } else {
         com.yiyiaddon.l.i.c var9 = com.yiyiaddon.l.i.c.a();
         float var10 = this.c.z();
         float var11 = this.c.A();
         float var12 = this.c.x();
         float var13 = this.c.y();
         float var14 = this.c.v();
         float var15 = this.c.w();
         float var16 = this.c.a(var4, var2);
         float var17 = this.c.b(var5, var3);
         float var18 = var12 + 18.0F;
         float var19 = var13 + 70.0F;
         float var20 = var14 - 36.0F;
         float var21 = this.al();
         boolean var22 = !this.gt;
         this.b.a(var16, var17, var12 + 18.0F, var13 + 16.0F, var8, var22);
         this.j(var21);
         this.b.a(var8);
         this.d.a(var8);
         if (com.yiyiaddon.c.a.d) {
            com.yiyiaddon.l.g.f.a()
               .a(
                  var1,
                  this.d.b(),
                  this.minecraft,
                  com.yiyiaddon.l.g.g.ef(),
                  this.c.b(var12, var2),
                  this.c.c(var13, var3),
                  this.c.k(var14),
                  this.c.k(var15),
                  this.c.k(var11),
                  com.yiyiaddon.c.a.c(),
                  com.yiyiaddon.c.a.a
               );
         }

         com.yiyiaddon.l.b.j.a(var1, 0.0F, 0.0F, var2, var3, 0.0F, var9.uY, var10 * (var9.gB ? 0.16F : 0.1F));
         var1.save();
         this.c.a(var1, var2, var3);

         try {
            com.yiyiaddon.l.b.j.d(var1, var12, var13, var14, var15, var11, var9.uY, var10, 1.15F);
            com.yiyiaddon.l.b.j.a(var1, var12, var13, var14, var15, var11, var9.uN, com.yiyiaddon.c.a.d ? 0.62F : 0.94F, var10);
            var1.save();
            var1.clipRRect(RRect.makeXYWH(var12, var13, var14, var15, var11), true);

            try {
               com.yiyiaddon.l.b.j.a(var1, var12, var13, var14, var15, var9, var10, 0.46F);
               com.yiyiaddon.l.b.j.c(var1, var12, var13, var14, var15, var11, var9.uX, var10, 0.26F);
               this.b(var1, var12, var13, var20, var10, var9, var22);
               var1.save();
               var1.clipRect(Rect.makeXYWH(var18, var19, var20, var21));

               try {
                  this.d.a(var1, var18 + 10.0F, var19, var20 - 40.0F, var21, var10, this.b.r(), var16, var17);
               } finally {
                  var1.restore();
               }

               this.b.b(var1, var18 + var20 - 8.0F, var19 + 4.0F, var21 - 8.0F, var10, var9);
            } finally {
               var1.restore();
            }

            j.b(var1, this.c.x() * 2.0F + this.c.v(), this.c.y() * 2.0F + this.c.w(), var10);
         } finally {
            var1.restore();
         }
      }
   }

   private void b(Canvas var1, float var2, float var3, float var4, float var5, com.yiyiaddon.l.i.c var6, boolean var7) {
      this.b.a(var1, var2 + 18.0F, var3 + 16.0F, var5, var6, var7);
      com.yiyiaddon.l.g.a.c(var1, this.d.E(), var2 + 58.0F, var3 + 27.0F, 19.0F, com.yiyiaddon.l.b.j.a(var6.uT, var5));
      com.yiyiaddon.l.g.a.b(
         var1, com.yiyiaddon.l.b.d.a(this.d.F(), var4 - 120.0F, 11.0F), var2 + 58.0F, var3 + 44.0F, 11.0F, com.yiyiaddon.l.b.j.a(var6.uU, var5)
      );
   }

   private void j(float var1) {
      this.b.e(this.d.D() + this.d.E() + 8.0F, var1);
   }

   @Override
   public boolean mouseClicked(MouseButtonEvent var1, boolean var2) {
      if (this.gt) {
         switch ((int)com.yiyiaddon.m.b.a<"s2aip5zg1noiqn","3w435fDBUD2IKqPFcCjLxNB9HtZqorD8JdloyttzeAM=",-45074290622941995,5157111939266734108,-8990743561800270738,1309373330777109875>()) {
            case 1653913369:
               return false;
            default:
               throw null;
         }
      } else if (com.yiyiaddon.l.d.d.x(var1.button())) {
         switch ((int)com.yiyiaddon.m.b.a<"s1sh3q2mv7oqty","bFQEUtd4OvYEHG7KUoe3ptVMk/C1ozMn0MoiB3fppYE=",4748404384772397084,-6307089925034744531,7600445475581257193,149033711118764857>()) {
            case 1853087521:
               this.gv = false;
               this.gw = false;
               return true;
            default:
               throw null;
         }
      } else if (var1.button() > 1) {
         switch ((int)com.yiyiaddon.m.b.a<"sh9kk5y207mzt","GfhvOn4FSfiQ3j11OIo2CxV5TNPgMDjtQV+gPzPMYzU=",8693807436567152598,1281354115313411358,5201983829667904841,-6297705601660808694>()) {
            case 1433946469:
               return true;
            default:
               throw null;
         }
      } else {
         float var3 = this.c.a(var1.x(), this.width);
         float var4 = this.c.b(var1.y(), this.height);
         float var5 = this.c.x();
         float var6 = this.c.y();
         float var7 = var5 + 18.0F;
         float var8 = var6 + 70.0F;
         float var9 = this.c.v() - 36.0F;
         float var10 = this.al();
         if (var1.button() == 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s1qcmuc2t71e29","8qqQqF8MTmFprqzoR07pq2qRvCpnT8VQtuYKM0wWFzA=",392252980011818302,-1035623119172158845,8664061463446162984,-7973197266193861216>()) {
               case 5244599:
                  if (this.b.b(var3, var4, var5 + 18.0F, var6 + 16.0F)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s18hi7yy49rkg5","dtgcrjjuuy5DV3kQ/jGlcRd5OBxf6h5GtLGK654uOEo=",485001801417757787,8384622998749417336,5016614276389636718,-591999445555092915>()) {
                        case 1174946081:
                           this.b.jJ();
                           this.kM();
                           return true;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         m.lb();
         if (var3 >= var7) {
            switch ((int)com.yiyiaddon.m.b.a<"snk94oguy980r","iBc8WPlS37RYuIadyVOXFom7L2W3whTGH3Tcel7pY/E=",-1346771320401246890,-5417689225461215751,8199125520477671279,-5587235927404294057>()) {
               case 1279711419:
                  if (var3 <= var7 + var9) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3g3e7rag1bg5r","dW57knPaaQPT8kfmWOniNwGNN6vXOoppmouowSg8FlQ=",8430163876577697915,1460642809543051117,-1612692861282255483,4910744652387164550>()) {
                        case -1346237883:
                           if (var4 >= var8) {
                              switch ((int)com.yiyiaddon.m.b.a<"sl6rh3w5pkh3l","WgwerzN+Ozv8DgzTpCTpbs55qvTTOCMqiUqlgN3I70c=",4826832733729995744,-3282309982157009125,-6919625863389339339,1152161222771891825>()) {
                                 case -650921088:
                                    if (var4 <= var8 + var10) {
                                       switch ((int)com.yiyiaddon.m.b.a<"slsjsxjv9hacp","mm2/Ze7+xWfnfz7eotPx0gAzsDs4izXNvLSGa8PAJdw=",964957386214725034,2864575098086802500,8077825048565429663,-3557425611699095360>()) {
                                          case -1335259837:
                                             float var11 = var8 + 4.0F;
                                             float var12 = var10 - 8.0F;
                                             this.j(var10);
                                             if (var1.button() == 0) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s3kbgp0st62nhc","QI1xQ4y0LWzkSD4p0HmAdXmkUykqrjoe9CJiPSC5KTk=",2374916827178754306,-1738433600505287615,1827183911421659062,43040800552947418>()) {
                                                   case 357963930:
                                                      if (this.b.fR()) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2mwaec3fx3m1q","PTeMisNF7wHVkJ6tVcbK72lPt99YaWnLxS3lbuuOihU=",2253889757954889070,7682306097778313986,621773896222346578,1878741492493589368>()) {
                                                            case -829251928:
                                                               if (this.b.d(var3, var4, var7 + var9 - 8.0F, var11, var12)) {
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s1bqifav8t7v5r","8JL9FKqFzTafqLHFhAlfT75z2bnNweUfYp/tazCKOAQ=",6350131780164730153,-6552860748371031437,-6705592512070876855,4955202809491078720>()) {
                                                                     case -938135983:
                                                                        this.gw = true;
                                                                        this.b.a(var4, var11, var12);
                                                                        return true;
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

                                             boolean var13 = this.d.a(var3, var4, var7 + 10.0F, var8, var9 - 40.0F, this.b.r(), var1.button());
                                             if (var13) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s2umvj2awcot7y","3OG+jFNUy6MBNxtXuyHMt66KNYEEmj2bpxO4sEIpC5k=",-5705488054869229978,-6652629951538399529,-1845758134738078104,522981111391240999>()) {
                                                   case 415648567:
                                                      if (var1.button() == 0) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"sc8lj75dodbmu","VntMp2n1+fivCoPgNW46opB4mT10Fmzo0aoPYrGX4Rk=",7343223647137542209,3907491985238750364,-192268586627900717,-1151247373634087665>()) {
                                                            case 1400352281:
                                                               this.gv = true;
                                                               switch ((int)com.yiyiaddon.m.b.a<"s33sjkj2jnyz2n","XjgFoMptELe/X24DyV3SKOek8CHPmxgpWu4mEFlvunE=",-900941197371491542,1376761071130489773,-5622904541116172119,-905991655172084103>()) {
                                                                  case 899036370:
                                                                     return var13;
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

                                             return var13;
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

         return false;
      }
   }

   @Override
   public boolean mouseDragged(MouseButtonEvent var1, double var2, double var4) {
      float var6 = this.c.a(var1.x(), this.width);
      float var7 = this.c.b(var1.y(), this.height);
      float var8 = this.c.x();
      float var9 = this.c.y();
      float var10 = var8 + 18.0F;
      float var11 = var9 + 70.0F;
      float var12 = this.c.v() - 36.0F;
      float var13 = this.al();
      if (this.gw) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ou8owvzciqb","FVwOQBVxULtaLSYnUlRtn8iojnyq2Q5991trVEsQUos=",-3200592639791318294,-3852354363602775543,5799923709301636658,-7492505566875991909>()) {
            case -1033295747:
               this.j(var13);
               float var14 = var11 + 4.0F;
               this.b.b(var7, var14, var13 - 8.0F);
               return true;
            default:
               throw null;
         }
      } else if (this.gv) {
         switch ((int)com.yiyiaddon.m.b.a<"s16m1npkm4jymx","YPqBGIinGAg0cu8YuWDOKJ/IsBarmc/PBPpnrl6uUQE=",3360560918884039533,5770225037367302177,-3458452924036529836,6776920333185011476>()) {
            case -878772263:
               this.d.a(var6, var7, var10 + 10.0F, var11, var12 - 40.0F, this.b.r());
               return true;
            default:
               throw null;
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean mouseReleased(MouseButtonEvent var1) {
      this.gv = false;
      this.gw = false;
      this.b.jK();
      this.d.jU();
      return false;
   }

   @Override
   public boolean mouseScrolled(double var1, double var3, double var5, double var7) {
      float var9 = this.c.a(var1, this.width);
      float var10 = this.c.b(var3, this.height);
      float var11 = this.c.x() + 18.0F;
      float var12 = this.c.y() + 70.0F;
      float var13 = this.c.v() - 36.0F;
      float var14 = this.al();
      if (var9 >= var11) {
         switch ((int)com.yiyiaddon.m.b.a<"s1mix4vpgvg82h","rD/gykG8hbf3oA/4ar3c72f3dUwrW86Ik6iNoEZ/ssQ=",2667198447180234981,7599650275789654112,-142037810194608813,-6470089386863862031>()) {
            case -1665606098:
               if (var9 <= var11 + var13) {
                  switch ((int)com.yiyiaddon.m.b.a<"shpmhvpk8sub1","W8bFBYjh7ClYrGFrkpU3+oHfYQPzmO3z2enGK24Z5vQ=",4746146230928436106,5654183659163077798,8590387213808340779,4120425335669178344>()) {
                     case 526381458:
                        if (var10 >= var12) {
                           switch ((int)com.yiyiaddon.m.b.a<"sng6k3jyewp4n","+kraP3SUKfvzDQtADVDS1gTGlnKj3sp0TJXE2TMHfpg=",3292305672640821752,-2267289980582997572,1342011706001829621,-8294865922260507606>()) {
                              case -1321183323:
                                 if (var10 <= var12 + var14) {
                                    switch ((int)com.yiyiaddon.m.b.a<"se8otv1a8qv33","l8vsSL4gh+yCy6lFnYTGgceLZNx62Vxtr7+aZXzgFdg=",-3593500714994561406,-3668590515712522792,-6139889655804009427,4401187529780842549>()) {
                                       case 1487750329:
                                          this.j(var14);
                                          this.b.a(var7, com.yiyiaddon.c.a.b);
                                          return true;
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

      return false;
   }

   @Override
   public boolean keyPressed(KeyEvent var1) {
      if (com.yiyiaddon.l.d.d.w(var1.key())) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ta03uhxnub66","vs49X+fXM0vEWnY026k910k0Z+b3PRTy+kt6KdUkGlg=",3689743595096431107,-8111159321964654999,-8098632055080560678,-3260182254631293562>()) {
            case -1237829928:
               return true;
            default:
               throw null;
         }
      } else if (m.keyPressed(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2hh8qm9im4ovf","BDYSxBNMdNlDCvXg1zEIpxWnLPwevNjjuvNPP6EownQ=",-742186391090209974,150834974271770160,-1837920802095486719,1175703502449844315>()) {
            case -1465792788:
               return true;
            default:
               throw null;
         }
      } else {
         return super.keyPressed(var1);
      }
   }

   @Override
   public boolean charTyped(CharacterEvent var1) {
      if (m.charTyped(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s12nxxp0yt52xb","gz/+nRVbJ2g8w9NAY/o19x3sjvF2LP3qT8eudUcloDE=",-6716039667818741813,-3500315570718248776,-722229665575382344,-2728476520855477569>()) {
            case 1509617762:
               return true;
            default:
               throw null;
         }
      } else {
         return super.charTyped(var1);
      }
   }

   @Override
   public boolean preeditUpdated(PreeditEvent var1) {
      m.a(var1);
      return true;
   }

   private void kM() {
      if (this.gt) {
         switch ((int)com.yiyiaddon.m.b.a<"s1i8t6jfp3x0fo","yv+tAPGs4giGpptSdcnLh4jOnsPZEYkDTG9ljgsA+I0=",-8177152418613541739,-3854631877851794760,7839739798312356409,3811351142613118001>()) {
            case 1574111080:
               return;
            default:
               throw null;
         }
      } else {
         this.gt = true;
         m.lb();
         this.b.i();
         this.c.jN();
      }
   }

   @Override
   public void onClose() {
      this.kM();
   }

   @Override
   public void removed() {
      m.lb();
      com.yiyiaddon.l.g.b.f();
      this.d.ko();
      super.removed();
   }
}
