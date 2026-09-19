package com.yiyiaddon.e.g.j;

import com.yiyiaddon.e.g.e.e;
import com.yiyiaddon.e.g.j.a.g;
import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.b.j;
import com.yiyiaddon.l.h.f;
import com.yiyiaddon.l.j.m;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

public final class a extends f implements com.yiyiaddon.l.c.b {
   private static final String jZ = (String)com.yiyiaddon.m.b.a<"s3k0xu3rm4lram","cVqs6HQ6wUz6khDmmrmLlV4gOAQrhTwx6muJgu61DMvU8lBNG4vUS794yT4UJJisNzWanvaqN+vSlSChGp4a959WJBOW7008",623751109641812037,1410563375638435427,933355950621622266,2175518569108664599>();
   private static final int eB = 20;
   private static final float az = 6.0F;
   private static final float aA = 11.0F;
   private static final float aB = 10.0F;
   private static final float aC = 12.0F;
   private static final float aD = 6.0F;
   private static final float aE = 6.0F;
   private static final float aF = 320.0F;
   private final com.yiyiaddon.e.g.a e;
   private final com.yiyiaddon.e.g.j.a.a a = new com.yiyiaddon.e.g.j.a.a();
   private List<com.yiyiaddon.e.g.j.a.d> aO = List.of();
   private com.yiyiaddon.e.g.j.a.d a = com.yiyiaddon.e.g.j.a.d.OVERVIEW;
   private int E;
   private com.yiyiaddon.e.g.j.a.b a = com.yiyiaddon.e.g.j.a.b.a();
   private final Set<String> t = new HashSet<>();
   private String ka = (String)com.yiyiaddon.m.b.a<"s3l4q5ra26lmmc","7bfmzujukqmgBkuHLLcz080poMBDled0mFqVsg==",-8712209664150622849,4577437614967480143,-8323778434855544216,6930334546923285093>();

   public a(Screen var1, com.yiyiaddon.e.g.a var2) {
      super(
         (String)com.yiyiaddon.m.b.a<"spojbmuqa2aq4","OX/w8bMmnopIBJKZ7945boN1G+5ia6B1yP1QI5xXy2p3mclpC+c6aQlV",6137760449382508986,3235770657853369351,-2840789285650525752,7839568681977072935>(),
         var1
      );
      this.e = var2;
      this.c(var2::v);
      this.cF();
      this.d().a(this.a);
      this.C();
   }

   @Override
   protected void init() {
      super.init();
      com.yiyiaddon.d.a.b.a(
         (String)com.yiyiaddon.m.b.a<"s3k0xu3rm4lram","cVqs6HQ6wUz6khDmmrmLlV4gOAQrhTwx6muJgu61DMvU8lBNG4vUS794yT4UJJisNzWanvaqN+vSlSChGp4a959WJBOW7008",623751109641812037,1410563375638435427,933355950621622266,2175518569108664599>(),
         com.yiyiaddon.d.a.c.TICK,
         var1 -> this.B()
      );
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s30j6fo9d46vqd","5ThK2ea0vkiYJYoiHclwUixXZ8UxPlDiOPfdTgZcycw=",-6976609954475304433,6066383765258689701,4660086601464666830,-645103950145967113>()) {
            case -1258144399:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s1s5zic8y5yrvp","YYCnoeOo2MmEFas+Ry36sAnsyrUM1wgR5ETmIiOuWjA=",-3404350320852536391,4868075323826882891,-7827693205384014274,-8362848558218706505>()) {
                  case -1686201809:
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
         (String)com.yiyiaddon.m.b.a<"s3k0xu3rm4lram","cVqs6HQ6wUz6khDmmrmLlV4gOAQrhTwx6muJgu61DMvU8lBNG4vUS794yT4UJJisNzWanvaqN+vSlSChGp4a959WJBOW7008",623751109641812037,1410563375638435427,933355950621622266,2175518569108664599>()
      );
      super.removed();
   }

   private void B() {
      if (++this.E < 20) {
         switch ((int)com.yiyiaddon.m.b.a<"s2mi69kovpww27","XYKlIQLMtx5PB3eWfvXXouYATsb7ivuVVCK7Ausq/pc=",5809083874865193942,8990801864180757169,1030868077654133779,-4131654954923341303>()) {
            case -1889545784:
               return;
            default:
               throw null;
         }
      } else {
         this.E = 0;
         if (this.minecraft != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2r3n97113ojf9","jZxnIEaM9+rpUlN8L3KrnBMOKtOCK+CiBynUrEJjuF0=",-4015561598190324203,-1941875442546203429,5206108933363670741,-5245874030143302304>()) {
               case -204125490:
                  if (this.minecraft.screen == this) {
                     if (!c(0)) {
                        switch ((int)com.yiyiaddon.m.b.a<"sv5062hmknzuo","MU0j9XkuDO9CH4bFrR7ejzS1kcBg6IjxJMe+QK5NY/s=",6988478628209321112,-3876088556075460067,1429032081904576264,7393168208337047214>()) {
                           case -194465211:
                              if (!c(1)) {
                                 if (this.a == com.yiyiaddon.e.g.j.a.d.OVERVIEW) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2iqspg1wqsu5c","1r3Qz/RHslUma7eNYXoUL1pmuhdeDxXNmhc0XjfBqtk=",-5550797609902107032,5655074326182717210,-5207384258393118273,-1181625604668502191>()) {
                                       case 748715771:
                                          this.C();
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.a = com.yiyiaddon.e.g.j.a.b.a(this.e);
                                 return;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"sylnb0qqy8n07","15saGbGE28fL6V4f2G+v4b1+98y2RTKNnO8DnfFuKyk=",2811734767126614127,5183258128745645905,-1221504378113407463,-837178352410616615>()) {
                                 case -1609411994:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s2tv9v8qi423jp","/+dSG2wX3dFRMIcaZD6UiL6DzbyQ34mfYs4mJRKsbIk=",1189744639701193509,-2364366549189713669,-5383025920741851889,8097455788809530995>()) {
                        case -1542980927:
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
         switch ((int)com.yiyiaddon.m.b.a<"s2234v01czr8mv","iauSB4872xS/34p4lU+ICdzuae+gNyP98eVbKenAw3Q=",1377805339992874934,-3157317077585371686,-6444945234698965680,-1768694662423898416>()) {
            case -1732867650:
               if (var1.getWindow() != null) {
                  if (GLFW.glfwGetMouseButton(var1.getWindow().handle(), var0) == 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"s19l92940vhecu","mjCQTAgW2etDlakQC23OpZqvxRuJtZj4uiE1YNFstws=",-4532859082742626123,-5870927228634093688,3928598351337951911,-4104260049077670750>()) {
                        case -563946000:
                           switch ((int)com.yiyiaddon.m.b.a<"s38r7p1kmh1r1t","fiLZvF01g6cXMtKiETlkfRcRt/GzvhXJLbtAf9E3qVg=",4152309621750827742,-3638882219492305826,3454951288887808760,3143150953697437491>()) {
                              case -344224771:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s16cie6wggozq3","GzU1fGkeGLtRQgV58WOnDtSsf9RlUO2rRKNclkifoCQ=",8454016555333119167,5126899242077949463,6386415788360326923,-6282827894866882234>()) {
                        case 720117757:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2pxp1blp9wabu","nuELbHV1Kz9vIv7hHE4CDs4Au9xp5yI+ColGCv4B0Ig=",6513071015296406127,1014583392428213868,-4192548675537343460,7267385316970590783>()) {
                     case -1475533299:
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
      this.cF();
      this.a = com.yiyiaddon.e.g.j.a.b.a(this.e);
      this.a.u();
   }

   private void a(com.yiyiaddon.e.g.j.a.d var1) {
      this.a = var1;
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1rrucfu9waqzy","mOja5IM0cKoB7N7p+t+pBAaHg1iufxGy8ojlhN8VMOo=",-6208573987924795550,-4369913270731789257,5608674152898894119,202309896831009931>()) {
            case 1487787268:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s2j8jor12n8lid","f1kA/PSLYADDNQwwqn0OBUoSIoiPhQD3uFYV2G+zvLo=",-3719401681863193952,1256804799715896206,-5490051315127122339,-3972874250246631418>()) {
                  case 429420631:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"syx3ha8aefq3m","fdMkL9af6A2KyJ9LUP9KIMuSvn7wQBQ5p+iIt/b2xj8=",9209056589418263713,-8815806125940027710,-4039353406293489763,3538641250617538899>()) {
            case -1996567061:
               return;
            default:
               throw null;
         }
      }
   }

   public void cE() {
      this.cF();
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sqfuwmeyprpzl","z/DjdCzwHvxpzKT6WiecI5vEO45NzxqiHSc/Or9Fij0=",6715857570012107619,-7577186851550000518,-622291217870887686,3893104413597802146>()) {
            case -1729487717:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"sv0jxchn9l01l","NUkPQp+zWWC7bcaEDRpdwZOCSqpAg6H6Pi52PY+DCgg=",-2522935690151004636,-61223433032270463,3351535260129912503,356340175461590937>()) {
                  case 672295094:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"s2tspygtgr5zt7","SqQ14mdvp7MmyPx0KVKpCyTUJAXHB+n/dcurtPUTfk0=",-5207619014221419092,-5369110280486887052,2430090764714222051,-8211143786266977777>()) {
            case -566462187:
               return;
            default:
               throw null;
         }
      }
   }

   private void cF() {
      List var1 = b(this.e.a().a);
      if (this.aO.equals(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2po2rurd1aos","3d2WnmsTJN5ugLQ6+AwvnklDENGMxW3/EYG4jUV5hW4=",6483215912380222582,5446111095565637448,1158135110753437341,2836995703464968565>()) {
            case -933393384:
               return;
            default:
               throw null;
         }
      } else {
         this.aO = var1;
         if (!this.aO.contains(this.a)) {
            switch ((int)com.yiyiaddon.m.b.a<"s2t4zg1oc71gsc","o7ftVcgacWA4khuSjhzRwgmgkdAqVxYMB0606P/losk=",5570893859112180477,7015778367438158359,-4737961505991851361,-3949361938924334244>()) {
               case -1600193324:
                  this.a = com.yiyiaddon.e.g.j.a.d.OVERVIEW;
                  switch ((int)com.yiyiaddon.m.b.a<"sf76ofmu07ny8","wHx6JerUYaGc9qkJKEfV+GvNaj487dq9GsNCvzq7Ygg=",-6339591181301994145,-6778133516247556322,-4314048050569630151,-5597681899076540148>()) {
                     case 1478533105:
                        return;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }
      }
   }

   private static List<com.yiyiaddon.e.g.j.a.d> b(e var0) {
      ArrayList var1 = new ArrayList();
      var1.add(com.yiyiaddon.e.g.j.a.d.OVERVIEW);
      var1.add(com.yiyiaddon.e.g.j.a.d.POINTS);
      var1.add(com.yiyiaddon.e.g.j.a.d.BASIC);
      if (var0 == com.yiyiaddon.e.g.e.e.GEAR) {
         label33:
         switch ((int)com.yiyiaddon.m.b.a<"snv9su745vn7h","aX36KF0IeP7DonbRXGwftIGJ+t8lt+cjwY9YSotnVtI=",8288530208274292830,-1630051294807701807,-5686579892211595739,-8087280909678184689>()) {
            case -1159128957:
               var1.add(com.yiyiaddon.e.g.j.a.d.GEAR);
               switch ((int)com.yiyiaddon.m.b.a<"s2nseidu1sel2g","oX0aUq0sFw0LPyMvzJdDv2TCucjypfD93jdhCymgJU0=",5335215124738192247,2671608656329438868,-1996660606960343803,2126164985942405685>()) {
                  case -188393887:
                     break label33;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (var0 == com.yiyiaddon.e.g.e.e.BOOK) {
         label28:
         switch ((int)com.yiyiaddon.m.b.a<"se569emm1rsfq","ksyjj99wx2GNBbvMazgrcji6SmAnaMQWBezW3iJDJc8=",4754120233775957130,-3228285874427613316,5384966804999661717,300771989290393382>()) {
            case 1191507682:
               var1.add(com.yiyiaddon.e.g.j.a.d.VANILLA);
               switch ((int)com.yiyiaddon.m.b.a<"s1pqosg29ke3p2","ZxPo6DpMPjiiwTAxrFyykejSRjn2IrvrzhKiepA+doU=",1878803729042698088,7598102709193250815,-6335274018035741220,-8697487840266426302>()) {
                  case 1974501639:
                     break label28;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (var0 == com.yiyiaddon.e.g.e.e.CUSTOM) {
         switch ((int)com.yiyiaddon.m.b.a<"segaedid55a60","26/FHypJD2P6AP+r3u16hX0XW5Mg3dAicvyc3RdGjmU=",-3824143381905247094,7547471367980284325,7509801033627556622,9189635334645482494>()) {
            case -1491411213:
               var1.add(com.yiyiaddon.e.g.j.a.d.CUSTOM_GROUPS);
               var1.add(com.yiyiaddon.e.g.j.a.d.CUSTOM_TARGETS);
               switch ((int)com.yiyiaddon.m.b.a<"s2nnipmrhvtmxr","RISORW3uuKhBC7VP8NlNEA+sM59yUJPbeeGZQxHTgtk=",7581656465940933906,4302769440925311034,128504892665421405,-2997630016575218445>()) {
                  case 1612186123:
                     return var1;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return var1;
      }
   }

   public Minecraft a() {
      return this.minecraft;
   }

   public Set<String> k() {
      return this.t;
   }

   public String aT() {
      return this.ka;
   }

   public void O(String var1) {
      String var10001;
      if (var1 == null) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s273pml59nch8i","Gb+WLJuBLcTg1+exkterd4g6sHzxun2G2aJqi6C8YKk=",-1849548805927187421,-2643966232487945473,1287677137666820518,-5265064086994409577>()) {
            case -1501030097:
               var10001 = (String)com.yiyiaddon.m.b.a<"s3l4q5ra26lmmc","7bfmzujukqmgBkuHLLcz080poMBDled0mFqVsg==",-8712209664150622849,4577437614967480143,-8323778434855544216,6930334546923285093>();
               switch ((int)com.yiyiaddon.m.b.a<"s1z3zpa4x7monf","txG7EV7ET9xI+XyMt2d9Tlwk2XMTHJnLkoOQgmQ+GOA=",2270469329884691355,-2420782410106987972,1066279633777552691,7299773643274746207>()) {
                  case 981895890:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var1;
         switch ((int)com.yiyiaddon.m.b.a<"s1ako3rcbyb7aw","G2aoetH/DVMV3OPBM/hnW7h/ZETI5m8zC9sHYBK/DoA=",1070777293902894081,3015994166621091380,-1276355919768377948,8517977307166753793>()) {
            case -315743550:
               break;
            default:
               throw null;
         }
      }

      this.ka = var10001;
   }

   @Override
   public void a(String var1, float var2, float var3) {
      this.a.a(var1, var2, var3);
   }

   private void a(i var1) {
      var1.a(new com.yiyiaddon.l.c.a(this.e));
      var1.a(new com.yiyiaddon.e.g.j.a.c());
      this.b(var1);
      label35:
      switch (this.a) {
         case OVERVIEW:
            new com.yiyiaddon.e.g.j.a.e(this, this.e).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2ugc50p59cazg","I0Mpq3vMya+Ww5yJas8SJoQBCMMy48e2DymyKRwUWow=",6283202947509646396,-1343291715201657417,-3618118338452678859,-5836739297758585972>()) {
               case 1329597987:
                  break label35;
               default:
                  throw null;
            }
         case POINTS:
            new com.yiyiaddon.e.g.j.a.f(this, this.e).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s1w3na64uoaj2a","T8+woUtqAEVYQXVKWaovWbpuYJPlol/lQJn4g61GCvE=",2017381677228397698,-4053143771078461970,-2597440768664595138,5119208878989681203>()) {
               case 512212242:
                  break label35;
               default:
                  throw null;
            }
         case BASIC:
            new com.yiyiaddon.e.g.j.a.a(this, this.e).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s1itwl2aedkvhz","Nk/o7OvL+0ElCaWhErWnw0N3l9rJYi9vrfPyFKMF5gg=",-7081092392039496031,7785092418679603835,5278858945621467424,921980753941838661>()) {
               case 1278299821:
                  break label35;
               default:
                  throw null;
            }
         case GEAR:
            new com.yiyiaddon.e.g.j.a.d(this, this.e).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2yxaou9jvowqy","fICGYxsG9GxsPHHV9GjXA8H/+tWUEUWVYE7GgibsdhI=",5683549520949332521,1501374703000608559,-4541249452370579195,-1674470819692579710>()) {
               case 380986400:
                  break label35;
               default:
                  throw null;
            }
         case VANILLA:
            new g(
                  this,
                  this.e,
                  com.yiyiaddon.e.g.b.a.av,
                  (String)com.yiyiaddon.m.b.a<"s2f0g4rme0fbiz","aPHkzNUeC35361aQaAfAeeJkVlsh2Ace//J6c+YhMyJ9dcV6",6895260604866634177,4089614415604968772,466448517154510119,8279874561157438856>()
               )
               .d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2cfp92erlr76i","6kq8Ms59AOrwyBumeZSg4hb2OXKB5bI2X93zCg8nBDk=",3314359045137994004,5838782516874578796,3392253372873688692,-2673111385467484318>()) {
               case -160714205:
                  break label35;
               default:
                  throw null;
            }
         case CUSTOM_GROUPS:
            new g(
                  this,
                  this.e,
                  com.yiyiaddon.e.g.b.a.au,
                  (String)com.yiyiaddon.m.b.a<"sb1gtsdcwgdox","jVh2agTpdu9wVanCva0INQDvw5avVhO8Jzq4zVg/VW53VqaM4bWvkg==",7077695841653150606,4896753651863383963,5074542575016577415,6862782677589707408>()
               )
               .d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2vj36rnb7kodj","8d8TMw3vCjOHTKi/qsijtkU8bGWUGnl6xanK3Ooqk7E=",6820299449866403408,-1922471252348423663,-127873304310766277,-5912123711659924517>()) {
               case -1560646135:
                  break label35;
               default:
                  throw null;
            }
         case CUSTOM_TARGETS:
            new com.yiyiaddon.e.g.j.a.c(this, this.e).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"sme6v7whfr7qd","l+Hwqg0LNo8FuseKP12sb38HaTnzIOTDkZmRXrRvkYM=",-5315826560849204040,8640149338787314699,-3209410026555805280,8190830826149568991>()) {
               case 875173829:
                  break;
               default:
                  throw null;
            }
      }

      this.c(var1);
   }

   private void b(i var1) {
      ArrayList var2 = new ArrayList();

      for (com.yiyiaddon.e.g.j.a.d var4 : this.aO) {
         boolean var5 = var4 == this.a;
         com.yiyiaddon.l.j.a var6 = new com.yiyiaddon.l.j.a(var5 ? var4.D() + "" : var4.D() + "", () -> this.a(var4));
         var2.add(
            new com.yiyiaddon.l.c.f.c(
               var6,
               () -> {
                  if (var4 == this.a) {
                     switch ((int)com.yiyiaddon.m.b.a<"s289f0a42k6gkd","pW43nAMgkBouKwOUf10NBFC+MDEz+Z2U+AuMBVoI3So=",6495630409659602216,-3231908254931093936,-4702432876000717908,-3904292674238560691>()) {
                        case 606657961:
                           switch ((int)com.yiyiaddon.m.b.a<"s2mw4rgkwbeyv8","+0aCxLBtub/Qn4OA3eYNPqKSOPpJ946JzcPL4yrq1mg=",5397321412268995090,8276868907761706569,5256721674345647691,8387408652113338144>()) {
                              case 1709862949:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var10000 = var4.D() + "";
                     switch ((int)com.yiyiaddon.m.b.a<"s2df52u9ay7irn","SEF+6f03nDWtnjS5htQbPo1AlGncxijADLUgFN0aJoA=",-5545891095591382609,7304563985516661162,4533668041501031259,5705586560532243011>()) {
                        case 645757050:
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
                     (String)com.yiyiaddon.m.b.a<"s354gwrq6zkh23","Zqw866JSmrd9eFJibtEyLVWZbtNfec02ZdaNbaoo/BxjIflB",7542869431063256701,-3857828526106537799,4493901085112869085,1701772762238313600>(),
                     this::C
                  ),
                  (String)com.yiyiaddon.m.b.a<"s2e4l5isrgmjx1","VpbvlJKNFep+xt7EsEMfkPXwcjF7igv6UpCzTEpUIlzq1ozgsv/uc4THOpqAKc5wGEQXr0mNGgDByOEghbeI+9imf4DeasqlTTuhRpsqCZHQl1A4GMywvzQZjo4c//LnvOgAcQ==",-7207150105787930154,-7734771925547988310,-7246893922613175511,7705813917330065170>()
               ),
               com.yiyiaddon.l.c.f.a(this, this.e, this::C),
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"shvu5oqdlispp","/kz8S+ZsdLu4RPKxjV2CZ9S9QMLjGzaZrufX45xJYSJaQjRW",-2288465777049925535,5148531209716088189,5290661211749410195,1789048486630756249>(),
                     () -> {
                        if (this.minecraft != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1r7gerilk904i","xr4XTuuhJ8jLBtQnXemProwBUTgWYV97/AuJDV6WXwU=",3233530763029704679,-6620657298743260542,-1077463477805790965,5954647872855026145>()) {
                              case -91758225:
                                 this.minecraft.setScreen(null);
                                 switch ((int)com.yiyiaddon.m.b.a<"s3s5tcis7q470h","Mrh+pOE8FCMp3OOV47rK3POOB6V0nWBzGMpN91oF/Sc=",-8142304644532090426,7889065945708527107,-3720965798711770430,1301302666138453074>()) {
                                    case -1319029266:
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

   private static List<String> a(String var0, float var1) {
      ArrayList var2 = new ArrayList();
      String[] var3 = var0.split(
         (String)com.yiyiaddon.m.b.a<"s8d29alp4tnbk","nIz95rdxyyh+UjmiirQaOFhO4/as6I12+0fxrJVz",-4456211618691345421,-7194500791114983880,-1891657154894308283,5345131878749334252>(),
         -1
      );
      int var4 = var3.length;
      int var5 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s2pdtnni2o9y8z","T22EEBJm3ReE5NYJP/8W4KfFbb2/+m1f9AzmxsOg5vI=",5281954223637854648,6077804554034767340,-7261260373191432491,-3328190543440184632>()) {
         case -760152643:
            while (var5 < var4) {
               switch ((int)com.yiyiaddon.m.b.a<"s3597gr87t4dl","0svzl497Gx0pcuyRuCjwl5yswoYQTG/CM9skLDD4Vew=",689301197937898906,5921929087395172782,-35408030362238045,412307449298603928>()) {
                  case 94489129:
                     String var6 = var3[var5];
                     if (var6.isEmpty()) {
                        label68:
                        switch ((int)com.yiyiaddon.m.b.a<"s1mbfxyz43n8he","1CgLQn2/oqOh9pSNVK7VScb83KxuBcavkRj0r5SEbI0=",3466799571391950072,-5951598761326695515,7448964996782641922,-6417732847088924106>()) {
                           case 16609576:
                              var2.add(
                                 (String)com.yiyiaddon.m.b.a<"s3l4q5ra26lmmc","7bfmzujukqmgBkuHLLcz080poMBDled0mFqVsg==",-8712209664150622849,4577437614967480143,-8323778434855544216,6930334546923285093>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s3avscwu8uz54p","oRW2rc6uuRMcYJHFyTlU0Y5TsLYzHRzvc3mPGe0ynoI=",560979214516244112,-9180539149737504615,-2323100635688807075,-2487062978335073551>()) {
                                 case 1055354601:
                                    break label68;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        StringBuilder var7 = new StringBuilder();
                        float var8 = 0.0F;
                        int var9 = 0;
                        label46:
                        switch ((int)com.yiyiaddon.m.b.a<"s36xdraodr2sl","DRcrJGJXNnBsTRenbXMQc9L/e12ffj5pujA4/uc4RRg=",1917459061478322906,2523646222766939156,3011311289683024619,5809765795270621934>()) {
                           case 1322035916:
                              while (var9 < var6.length()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sqnkuzplcacqt","ZsLxwrpazNuBhtyCbNv00x7q0uFi+5bAXF/H3MoOMMQ=",2701614796641925364,7518457000403065541,8227393163122986875,2754152430008746348>()) {
                                    case -1296457816:
                                       int var10 = var6.codePointAt(var9);
                                       int var11 = Character.charCount(var10);
                                       String var12 = var6.substring(var9, var9 + var11);
                                       var9 += var11;
                                       if (var10 == 167) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1orz353aab9yj","Qen5uXLyPtl0JbM1CTsIy5u7mg/JJbBZOHkRP9I/jyY=",5748101978566740795,8791867059132026786,-5626128812383551882,4422752008410120734>()) {
                                             case 1483239478:
                                                if (var9 < var6.length()) {
                                                   label64:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1ggyn06aq6w7s","rl3GE3lbH6xYzCTdJp0e3bLPmrSqgGVsrggQdwEsFis=",-2213557193580411931,8798475051282680299,4581315896402652068,-146439693170733235>()) {
                                                      case -1771697206:
                                                         var12 = var12 + var6.charAt(var9);
                                                         var9++;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s46nv4u1eiwly","jOsoYL8BYq9riRvNrl3ByBvyMzvVL5GA6JXwRs7BmIo=",7306815028627817849,-1329442316462659884,-8617517304754139749,4476020570807019883>()) {
                                                            case -1087936163:
                                                               break label64;
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

                                       float var13 = com.yiyiaddon.l.g.d.a(var12, 10.0F, false);
                                       if (var8 + var13 > var1) {
                                          switch ((int)com.yiyiaddon.m.b.a<"syh9ku5nwyjcw","ZFD8G6h6DF8oxDuYZeftSjNEgaHJe45TP7GaSfBfM5w=",-6094993087853457735,-6367507365023446647,-1063924885546730333,1636215922893753295>()) {
                                             case -1925526118:
                                                if (var7.length() > 0) {
                                                   label58:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3c08l9e8o3vio","As9ESZV0toycuQzDNL3Njz7bXmBmvWjnj1MXfqviz5Q=",9143558235229006302,3362640415432115420,3782208957788111576,-5275243229525681779>()) {
                                                      case 116942370:
                                                         var2.add(var7.toString());
                                                         var7.setLength(0);
                                                         var8 = 0.0F;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1rjttbn41xhhs","Bi0SXzVYx90DGSp+hkEv4KiNax4ajCGEzrgWnjjBzLY=",9176129090239040090,-2929298072698731317,5746971795329529886,-8633239288371091951>()) {
                                                            case -1334773323:
                                                               break label58;
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

                                       var7.append(var12);
                                       var8 += var13;
                                       switch ((int)com.yiyiaddon.m.b.a<"s3bsb86ueecwiw","cHNKfUOu60dEajX5wYASIDfoxxECfOzphHqkX1Sgd08=",686331192965820996,9086268930925629602,-3894894166339158372,6079824857535132503>()) {
                                          case -24457047:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var2.add(var7.toString());
                              switch ((int)com.yiyiaddon.m.b.a<"s31b43usqdgtai","Jzk4xachZBjvbcPQ7LCVjOyKftE43SOW9sNKmTHlz64=",-1791779829995190214,-2235968894132144927,2872542020740917043,3186430616310438664>()) {
                                 case 1695498233:
                                    break label46;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var5++;
                     switch ((int)com.yiyiaddon.m.b.a<"s25rotcfuk7zv","PSyzA3lqgFjqEgBXLn+73eq+7Gae7HtmtIUo8AdjYjA=",-5073558804998277505,2664568464135317751,98495282651103825,8281924790325168659>()) {
                        case -820900362:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var2;
         default:
            throw null;
      }
   }

   private final class a implements com.yiyiaddon.l.b.g {
      private i a = new i(6.0F);
      private String aW;
      private float m;
      private float n;

      private void u() {
         this.aW = null;
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
         this.a(var1, var2, var4, var5);
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

      private void a(String var1, float var2, float var3) {
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2syk8pm8wx0cr","hzmMRDhBp1A/6/0lOV9UX7jaZdW1GyO8mDMYN+0exjM=",-5288294652693650693,3165576419716677131,6649778736886674982,-6181540814145778051>()) {
               case -1107533163:
                  if (!var1.isEmpty()) {
                     this.aW = var1;
                     this.m = var2;
                     this.n = var3;
                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s1fwoa3oppg7dp","ozQZYkKuEdjB+0SbQufS7EAFTW9tj57ITa0gzGICAhg=",3286443482489722330,-5474330117987296859,5268467749189970687,536286383306866789>()) {
                        case -1170295691:
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

      private void a(Canvas var1, float var2, float var3, float var4) {
         String var5 = this.aW;
         this.aW = null;
         if (var5 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s31mfjegdcyo51","gmcHQYTl2oe8m7wVy9YtqvGMcoBw6fIsZPUjt4j4Jhc=",-1527229661137947825,-8889005586114692352,-4168638792041021879,-2805448584829151259>()) {
               case -453449969:
                  if (!var5.isEmpty()) {
                     com.yiyiaddon.l.i.c var6 = com.yiyiaddon.l.i.c.a();
                     List var7 = com.yiyiaddon.e.g.j.a.a(var5, 320.0F);
                     float var8 = 0.0F;
                     Iterator var9 = var7.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s1ups1oe09v7hh","am1St6A9dkv9g1KysT8xhDZ4fGcKapNNwb7FWXaL07k=",-761782324134468856,-4923249757186754401,-7817782910525245935,1591722763879458292>()) {
                        case -599533642:
                           while (var9.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2wuc9iqid0tlg","VfpJ7ueRs0zC1PCxXE9QE0P4OW00zSw0Pgtzewa/FuQ=",6778479279050941904,7048272290701333329,5316886438662333162,-8777319243659855408>()) {
                                 case -1636118828:
                                    String var10 = (String)var9.next();
                                    var8 = Math.max(var8, com.yiyiaddon.l.g.d.a(var10, 10.0F, false));
                                    switch ((int)com.yiyiaddon.m.b.a<"srltlisg4qhd9","jsc9L2+PAW01cWcJRlPAV6A1X9XeoBVcJ9OkRiNQItI=",2624555014151762365,-6603170092740127592,9078515940422938039,-4337834400562336701>()) {
                                       case -709218721:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           float var12;
                           float var18;
                           int var10000;
                           label66: {
                              var8 += 12.0F;
                              float var17 = var7.size() * 12.0F + 12.0F;
                              var18 = Math.max(var2, Math.min(this.m, var2 + var3 - var8));
                              float var11 = Math.max(0.0F, this.n - var17);
                              j.d(var1, var18, var11, var8, var17, 6.0F, var6.uY, var4, 0.9F);
                              j.a(var1, var18, var11, var8, var17, 6.0F, var6.uN, 0.94F, var4);
                              j.c(var1, var18, var11, var8, var17, 6.0F, var6.uX, var4, 0.22F);
                              var12 = var11 + 6.0F;
                              if (var6 != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"syrxx2fr77o3j","xDjAHy3suClKzKtfpVW+ur4Od03ArsUvaxOlkEtfca4=",-7148512775158846184,2811661776566119049,-4220911079036370715,278071993934428081>()) {
                                    case -119574557:
                                       if (!var6.gB) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1jd7lm1022vcy","vHny7SafY3J8kVc2lxHjr+6f306ysUfU6oUGdkw+TLE=",-5761639712752029565,1325631667595912700,-6642545390100052195,-7084079992752211030>()) {
                                             case 1874092608:
                                                var10000 = var6.uT;
                                                switch ((int)com.yiyiaddon.m.b.a<"s2oses303lxoar","NoBagEySI+RjJSnw36B7/dY04tCcwHMWubG9YQEqf8A=",1539820278409073360,4194089331282290260,-7849158664422753732,-7287342752585680639>()) {
                                                   case 867581418:
                                                      break label66;
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

                              var10000 = 16777215;
                              switch ((int)com.yiyiaddon.m.b.a<"s1i02rteh7mtif","jiDK1J0BugLk9FtMgKe3Ak9EhXVH7W071sZK8ddyMXU=",9041013488008440184,-3929790624474261819,-1277357521140043143,-49647659046295941>()) {
                                 case 1050702424:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           int var13 = var10000;
                           Iterator var14 = var7.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s6od4ffzo37tn","0KJX9k/fHfvQjxGDKg4k/SYPnARhYIR3EthJCA5sih4=",-7585417968220109452,7798323021067680923,-1774048210974017876,8640588569864647799>()) {
                              case -1247302614:
                                 while (var14.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"szm2x81auumdq","j56tlxZUtrvTRxC04eLh10Lf5j44f7rW0V8m6ntwGoQ=",9152829037266526491,1646312325782448775,2758991651983176742,-1833095050827859133>()) {
                                       case 1559657812:
                                          String var15 = (String)var14.next();
                                          com.yiyiaddon.l.g.d.a(var1, var15, var18 + 6.0F, var12 + 10.0F, 10.0F, var13, var4);
                                          var12 += 12.0F;
                                          switch ((int)com.yiyiaddon.m.b.a<"sfc4beekbhxmj","7Za6Trt4+H+CG2U6U+TetxChIJAOPMtrTdL19CkeANM=",200730722821387115,3132655139333047166,-6099463453094766611,-7160815948117145436>()) {
                                             case -1773269069:
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
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"sn321kn2rftfm","RrACIoHpeVY6pJ96KTj1/fYD8yCbXyC8DnSmdXDeef4=",828502170370200009,1605701365804092505,-6423665846726727809,4464717064182572718>()) {
                        case -474329774:
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

   private static final class b {
      private final String[] D;

      private b(String[] var1) {
         this.D = var1;
      }

      private static com.yiyiaddon.e.g.j.a.b a() {
         return new com.yiyiaddon.e.g.j.a.b(
            new String[]{
               (String)com.yiyiaddon.m.b.a<"swy0bh3e2ue7r","htl/Y2NpcGNFE0UAcAbwU16dMtqZ7hBRwf+m4w==",-3583067454258693738,1155507364206295744,6426239101994958877,-6234810988124967349>(),
               (String)com.yiyiaddon.m.b.a<"swy0bh3e2ue7r","htl/Y2NpcGNFE0UAcAbwU16dMtqZ7hBRwf+m4w==",-3583067454258693738,1155507364206295744,6426239101994958877,-6234810988124967349>(),
               (String)com.yiyiaddon.m.b.a<"swy0bh3e2ue7r","htl/Y2NpcGNFE0UAcAbwU16dMtqZ7hBRwf+m4w==",-3583067454258693738,1155507364206295744,6426239101994958877,-6234810988124967349>(),
               (String)com.yiyiaddon.m.b.a<"swy0bh3e2ue7r","htl/Y2NpcGNFE0UAcAbwU16dMtqZ7hBRwf+m4w==",-3583067454258693738,1155507364206295744,6426239101994958877,-6234810988124967349>(),
               (String)com.yiyiaddon.m.b.a<"swy0bh3e2ue7r","htl/Y2NpcGNFE0UAcAbwU16dMtqZ7hBRwf+m4w==",-3583067454258693738,1155507364206295744,6426239101994958877,-6234810988124967349>(),
               (String)com.yiyiaddon.m.b.a<"swy0bh3e2ue7r","htl/Y2NpcGNFE0UAcAbwU16dMtqZ7hBRwf+m4w==",-3583067454258693738,1155507364206295744,6426239101994958877,-6234810988124967349>(),
               (String)com.yiyiaddon.m.b.a<"swy0bh3e2ue7r","htl/Y2NpcGNFE0UAcAbwU16dMtqZ7hBRwf+m4w==",-3583067454258693738,1155507364206295744,6426239101994958877,-6234810988124967349>(),
               (String)com.yiyiaddon.m.b.a<"swy0bh3e2ue7r","htl/Y2NpcGNFE0UAcAbwU16dMtqZ7hBRwf+m4w==",-3583067454258693738,1155507364206295744,6426239101994958877,-6234810988124967349>()
            }
         );
      }

      private static com.yiyiaddon.e.g.j.a.b a(com.yiyiaddon.e.g.a var0) {
         if (var0 == null) {
            return a();
         }

         com.yiyiaddon.e.g.b.a var1 = var0.a();
         com.yiyiaddon.e.g.h.a var2 = var0.a();
         List var3 = var0.f();
         return new com.yiyiaddon.e.g.j.a.b(
            new String[]{
               (
                     var0.g()
                        ? (String)com.yiyiaddon.m.b.a<"s2rr3i7eofaw5t","xhlPlEje0j1HGBi6VXB2ghKtMDgLLERvuSz17papHO0UjzxbU5M=",-5186353674783179343,1245953978864864649,-7922449082367693681,1619706042830348052>()
                        : (String)com.yiyiaddon.m.b.a<"s3lud59w4dhte5","eHetYHRNdLTW5+YCogDcIHYkPDP/R4zCjqnesIU21AcUjibhRJY=",1656172496857766705,5935808880500102044,2137319127431391132,-7580873501748428085>()
                  )
                  + "",
               var0.a().a().af() + "",
               var1.a.D() + "",
               var0.a().D() + "",
               com.yiyiaddon.i.g.c.bU(com.yiyiaddon.i.g.c.bU()) + "",
               var2.a() + "",
               var2.aS()
                  ? (String)com.yiyiaddon.m.b.a<"s3e1zi9a5uhql4","B+/FQ8eyditDWCt+ewDF0pK7kznpGWEgB6iggg5Ap/2hduch7RZh3blxChQtveUrZu8=",7863621919073783104,7347585348594647501,2206349327104220767,5237464902569958211>()
                  : (String)com.yiyiaddon.m.b.a<"sqcqhhhhretg6","EY3m7FgNIAcnxpGaf4Sj9p9UrNkapknOr8JbyF81GWYVFoYuRCXZ6qWisjtiFg8rquIuXw==",-6653199908554456688,-4374027840617089798,-8780343135843624642,-663974119498217604>(),
               var3.isEmpty()
                  ? (String)com.yiyiaddon.m.b.a<"s2dwn0p7lww135","7ZTObPf4uWnPaDto01g5oiKwqpKrGnsi2+Q4dUCv06yvH41gyueSFA==",8642745601904172958,3029796803205456241,2371431196297170785,-2830865487377351380>()
                  : var3.size() + ""
            }
         );
      }

      private String c(int var1) {
         return this.D[var1];
      }
   }

   private final class c implements com.yiyiaddon.l.b.g {
      private static final float aG = 18.0F;
      private static final float aH = 6.0F;
      private static final int eC = 4;
      private static final float aI = 8.0F;

      @Override
      public float b() {
         return 36.0F;
      }

      @Override
      public void a(float var1) {
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.e.g.j.a.b var8 = a.this.a;
         float var9 = Math.max(0.0F, var4 - 12.0F);
         float var10 = Math.max(0.0F, var9 / 4.0F - 8.0F);
         int var11 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s1tm982wweu1d8","ahn27luESp84OLyvBTQ/rumsvBEPeadb1H/VZz1vpVU=",-7479002076747501167,-246079461874640448,4030314861901173415,-4444812883728352999>()) {
            case -1719848616:
               while (var11 < 8) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2tvc3nkm9409j","p4c1r1vibCcthLWe6lmP+7MFgqbyBYjThzlveEmPMRM=",8172289478713776801,8985628949432737230,5785668490227484740,4075741472069078859>()) {
                     case -1692692481:
                        int var12 = var11 / 4;
                        int var13 = var11 % 4;
                        float var14 = var2 + 6.0F + var9 * var13 / 4.0F;
                        this.a(var1, var8.c(var11), var14, var3 + 18.0F * var12, var10, var5);
                        var11++;
                        switch ((int)com.yiyiaddon.m.b.a<"s2ib80o8mkcqi8","uXmTtCK7T6RI4CM+BunAPgQUIdTwks3Q277yCg1V6a8=",-3134751754024985204,7756291895610767680,-3991894805876651715,-6346172807628542314>()) {
                           case 1821335408:
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
         (String)com.yiyiaddon.m.b.a<"sr53ivcu9535v","cGP92xhr0Lc3u7ZFAMTI7G9H35CB/L/x6fEwLdqeKyw=",-3976432268561746383,2184022992550903638,-3319206759203703778,8773679626365154491>()
      ),
      POINTS(
         (String)com.yiyiaddon.m.b.a<"s1ekmar3afd0zc","bLsbnNFCjIeTyNpr5qDyBD+ymDq3L+0hUEVEB8023xE=",7564980026462128093,971400522108818882,-4398421936810257692,2704627733443964466>()
      ),
      BASIC(
         (String)com.yiyiaddon.m.b.a<"s28kszkgammzi7","Mj9ISbls9+AHKirFOZdcxUHoT7iSh1DkzkMneU/S1rgSDZq3",-3249388826128768919,3509518137197040199,8350061057291186420,1887944702871170895>()
      ),
      GEAR(
         (String)com.yiyiaddon.m.b.a<"s19w1cz48u6aty","ch4oYP8Cd/pa++Vek6VUee0jVegtRCR75kGYlFBYR8JdSjT5gKo3pQ==",-5103750080387857696,7055593274648460355,1643608790631976772,6294615030118187028>()
      ),
      VANILLA(
         (String)com.yiyiaddon.m.b.a<"s3nv5i4qvxzhcf","Iz3ZJRmmbUhzSrksoqNAraD9ksO5tZGoMK13aw+fPns4BO6AqE3PGA==",1995217813108545495,2676938804533932555,8648823929121823788,1214883227970612042>()
      ),
      CUSTOM_GROUPS(
         (String)com.yiyiaddon.m.b.a<"s2cfyh0rz35hk3","ElgA/33KeKdKhXJSddVvY+ANiJ20z1j3+Mn8eqBZV+5dntJQBpHIEw==",-1695985213074269526,3153810165852499538,-7698431090044554441,-5355077952473715648>()
      ),
      CUSTOM_TARGETS(
         (String)com.yiyiaddon.m.b.a<"s5jiacrgd2amb","JUoeDbO8FecCjsIOsN83pPSXKpRxeMUKGAAIPBjOw/HyuBBZpZY=",-5801283419228695718,-906964080861087910,3539267567614411834,-3861228690941509894>()
      );

      private final String kb;

      d(String var3) {
         this.kb = var3;
      }

      private String D() {
         return this.kb;
      }
   }
}
