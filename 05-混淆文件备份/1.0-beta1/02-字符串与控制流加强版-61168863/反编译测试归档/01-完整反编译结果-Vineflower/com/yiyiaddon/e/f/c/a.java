package com.yiyiaddon.e.f.c;

import com.yiyiaddon.l.b.g;
import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.b.j;
import com.yiyiaddon.l.h.f;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.entity.Entity;
import org.lwjgl.glfw.GLFW;

public final class a extends f implements com.yiyiaddon.l.c.b {
   private static final String iP = (String)com.yiyiaddon.m.b.a<"s23zv3ymjg9toz","oNzAqLE7dlCHpO6V9dg695W2r5WXr0KMMyMLeTpIIhbZUo2OxeAZFkTH3qic/Tt7gRBzcEOpnBYKxyOCelxrYWeiof442EcGTpY=",-3557384857317878531,2569470518979939848,-5408522840217203851,8974335782447044704>();
   private static final int dw = 20;
   private static final float an = 6.0F;
   private static final float ao = 11.0F;
   private static final float ap = 10.0F;
   private static final float aq = 12.0F;
   private static final float ar = 6.0F;
   private static final float as = 6.0F;
   private static final float at = 320.0F;
   private final com.yiyiaddon.e.f.a b;
   private final com.yiyiaddon.e.f.c.a.a a = new com.yiyiaddon.e.f.c.a.a();
   private com.yiyiaddon.e.f.c.a.d a = com.yiyiaddon.e.f.c.a.d.OVERVIEW;
   private int E;
   private com.yiyiaddon.e.f.c.a.b a = com.yiyiaddon.e.f.c.a.b.a();

   private static int a(com.yiyiaddon.l.i.c var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s32lxitb4gnqn4","A10j0MTpiOu1MWxSC6d9ftb+7KrXPPtnHyA3LutFMSg=",-7793230563969219950,-8324962782025806826,9142594250245620164,-1616426378937424526>()) {
            case 206809733:
               if (!var0.gB) {
                  switch ((int)com.yiyiaddon.m.b.a<"s30umixnuhkegu","VM2Z1t2dfDv8napzg2wY29UJocskmrPWQgBxvesGo8g=",8406877822734207285,6997147106145506658,-1670368042180298864,176896273622687515>()) {
                     case 1537925249:
                        int var10000 = var0.uT;
                        switch ((int)com.yiyiaddon.m.b.a<"s38fbw0kwlk048","N2pXGBlnenBvkQA2iw4gqfhE1Z80wghYRA2lf2PLQ0k=",4600657881104811544,524289225350151205,3145943893070741112,15759272749743561>()) {
                           case 1698258428:
                              return var10000;
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

      switch ((int)com.yiyiaddon.m.b.a<"s1cb3g5qvrx5ll","w1elKcH4IKCHW9RDDuL4CtiLKZ9iqfmJovUvAi3PuEY=",698032111949953795,5317397053837349769,-6001930614629623806,-7860913040777299056>()) {
         case -254714734:
            return 16777215;
         default:
            throw null;
      }
   }

   public a(Screen var1, com.yiyiaddon.e.f.a var2) {
      super(
         (String)com.yiyiaddon.m.b.a<"shsz7q3yks4eo","THdVDhYp7bz95p908+r2W6UjtD758W0ZUWNRLtcAVRxlAFLlAC3m27er",2350111956024656920,3421466200249021832,186453099961054506,-1377954455670991796>(),
         var1
      );
      this.b = var2;
      this.c(var2::v);
      this.d().a(this.a);
      this.C();
   }

   @Override
   protected void init() {
      super.init();
      com.yiyiaddon.d.a.b.a(
         (String)com.yiyiaddon.m.b.a<"s23zv3ymjg9toz","oNzAqLE7dlCHpO6V9dg695W2r5WXr0KMMyMLeTpIIhbZUo2OxeAZFkTH3qic/Tt7gRBzcEOpnBYKxyOCelxrYWeiof442EcGTpY=",-3557384857317878531,2569470518979939848,-5408522840217203851,8974335782447044704>(),
         com.yiyiaddon.d.a.c.TICK,
         var1 -> this.B()
      );
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s32jre8yxsd44a","hR+lqGI4hul2EQfwAI9zWao5Rd9F4YnBQD3/3G0mM6A=",-6099551476903905598,-5205172241833169144,4244063421006303606,782894913877829194>()) {
            case -1778690321:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s2aruh2029ajhk","ORCVD6f+j78W8iXKrJbhzt8j6ClMKj+X3VEN1N24fEQ=",2666712520003673353,-2152911798318243519,-4917339268957158886,-3869905512528647275>()) {
                  case -651379979:
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
         (String)com.yiyiaddon.m.b.a<"s23zv3ymjg9toz","oNzAqLE7dlCHpO6V9dg695W2r5WXr0KMMyMLeTpIIhbZUo2OxeAZFkTH3qic/Tt7gRBzcEOpnBYKxyOCelxrYWeiof442EcGTpY=",-3557384857317878531,2569470518979939848,-5408522840217203851,8974335782447044704>()
      );
      super.removed();
   }

   private void B() {
      if (++this.E < 20) {
         switch ((int)com.yiyiaddon.m.b.a<"s2n6turkzvldcw","G/IynziA8ezqqATBIShC8A2gDM2Wzkph6Q+I/RQnL/E=",-4133553613246698416,3644675715491203515,5570904568702249313,-5315940742461241203>()) {
            case 1809564132:
               return;
            default:
               throw null;
         }
      } else {
         this.E = 0;
         if (this.minecraft != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3tznytphnrjvt","FPOEzs23Ko6AjcqaPHequmMzfJYi9DBP/fSe62v3shs=",8877577398038913189,-6433620448333901396,-8530040810288619648,-581456271727249401>()) {
               case 418213114:
                  if (this.minecraft.screen == this) {
                     if (!c(0)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2jnmkrlgn3hyu","pzDpU/8h6qZug8KA8SC2RydgOK1tHjip0xS7nly/cOE=",530773874176528319,7466731221978738371,6165563211269356004,6946989827212345688>()) {
                           case 2103148420:
                              if (!c(1)) {
                                 if (this.a == com.yiyiaddon.e.f.c.a.d.OVERVIEW) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2ark2rqpzf2k8","ACLMctx9UyfI020AvVhqoR6yiUPpKWq4Hyinj/Pel4Q=",485247072756017521,3049244971876460076,7506824303507367190,1603332707809027771>()) {
                                       case -18049768:
                                          this.C();
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.a = com.yiyiaddon.e.f.c.a.b.a(this.b);
                                 return;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s3517tubui7ju8","8OViqnbqEu3N/e7HWVmVtrV3mI2u9cBz9JtbKobWqI4=",7923394892769426357,3572369435470926956,3166596508391007558,3032055566782166134>()) {
                                 case 646291245:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s2ghbf76uzzgrd","jXid/5WFgpowA+jlHcK7Jhd068UQGIYxav3nl5V+fbY=",-2175124306809917265,1856151468200981273,-9144471269368172574,-7653544089482370718>()) {
                        case 1380967129:
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
         switch ((int)com.yiyiaddon.m.b.a<"s3aysyj6zcdv6m","zSFn1A07UkA4sfozkcbalHxwsBqRr0DOI8/n74srBTU=",4415285558525266008,-2825397290974997615,5744639323578376834,-320361698502086655>()) {
            case 1249650958:
               if (var1.getWindow() != null) {
                  if (GLFW.glfwGetMouseButton(var1.getWindow().handle(), var0) == 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"sy3zfsbe41sf1","MM9KZ4RI1cALk6XxNJbyXquWvnl2Adce9loVwGArdXU=",-6550156538337320424,2752019393953990741,-5268305601677219366,-4576670952367963396>()) {
                        case 1643868563:
                           switch ((int)com.yiyiaddon.m.b.a<"s25zoeursv3t0w","DaspQsY/WqzZuScPeL6t96IQWnofvNFU3+MabO0LHDE=",-2797606856170706935,3583620160923214291,5943833512391496138,1393288884708792555>()) {
                              case -1144946329:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s1tct0gyp9coa","K8fGsVgGZ7cFWuOnF8lV8W9oJE1IQxdtsoRBHw8Fmo4=",-6681190703451007943,-1133667863229311159,8639519840322081098,6419631192805476052>()) {
                        case 682085236:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1jeuj98wg9zq1","SJNV3+gMQVj0N3uJcxlCb1a6+cXoFTsqKbGdmb8qhvc=",-557137155491937551,8230810852049903562,668417054957499804,-8345648028011306695>()) {
                     case 1215561528:
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
      this.a = com.yiyiaddon.e.f.c.a.b.a(this.b);
      this.a.u();
   }

   private void a(com.yiyiaddon.e.f.c.a.d var1) {
      this.a = var1;
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"skvty90dxjv6n","PaCnd/KllTW3YbuhOoknxpdCZ5U/iP2TNscnBvMG5E8=",211775862563750896,2242056711721022510,7529603022810061324,-3078798337844333694>()) {
            case 1110400611:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s2u26sr45tuqrw","Z006HIVq8SlcjCruMRQciVJdf53HoZUPk3ZGuQNHb4s=",-8716321868401251759,-5463500503738276261,1638169388414660293,-5267981579968727085>()) {
                  case -1733355785:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"s1cdlobpvvx4w4","JbbIqJk7lG5wTYJG/dW4wP53v2B8aOayJj/HYcjIGHk=",3171371108123019662,6837770895663789422,-5547978483011024592,4334692091959896343>()) {
            case -720572895:
               return;
            default:
               throw null;
         }
      }
   }

   public Minecraft a() {
      return this.minecraft;
   }

   @Override
   public void a(String var1, float var2, float var3) {
      this.a.a(var1, var2, var3);
   }

   private void a(i var1) {
      var1.a(new com.yiyiaddon.l.c.a(this.b));
      var1.a(new com.yiyiaddon.e.f.c.a.c());
      this.b(var1);
      label23:
      switch (this.a) {
         case OVERVIEW:
            new com.yiyiaddon.e.f.c.a.b(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"sseqtdgc8gjzm","SSqHCzSpJWr3mbDK23XD2GB0Xcg+nIhRwSHapwsf7JE=",4426188013155898150,-4416754930543371370,3290519136759690585,6166618500970577262>()) {
               case 446558567:
                  break label23;
               default:
                  throw null;
            }
         case GENERAL:
            new com.yiyiaddon.e.f.c.a.a(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2rloqfbfjqrpr","9c0LUfQULCVP3ZrzKMp5Tndy/CR3cdVzFt3DezNUu/M=",-748452708124084027,-996416693761654274,-5657087934818702565,6158174453911221880>()) {
               case -2128034489:
                  break label23;
               default:
                  throw null;
            }
         case TARGETING:
            new com.yiyiaddon.e.f.c.a.c(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s26jqa7rzbkaq6","FoNYD3L2+63qiCWUynCIjDO3JrShwnAoden98qJJrPA=",-5344933166628511435,2563616089207007540,7509996063464460256,8104622781547123720>()) {
               case 546023136:
                  break label23;
               default:
                  throw null;
            }
         case TIMING:
            new com.yiyiaddon.e.f.c.a.d(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2jj9zgiyvgtac","+LZskFEr8pxLlPExTHmRGyzIXId09kme234ofiMncuA=",4572562009109358192,-1288409697504847956,8994540524692989116,-5123145540117421534>()) {
               case 863329697:
                  break;
               default:
                  throw null;
            }
      }

      this.c(var1);
   }

   private void b(i var1) {
      ArrayList var2 = new ArrayList();

      for (com.yiyiaddon.e.f.c.a.d var6 : com.yiyiaddon.e.f.c.a.d.values()) {
         boolean var7 = var6 == this.a;
         com.yiyiaddon.l.j.a var8 = new com.yiyiaddon.l.j.a(var7 ? var6.D() + "" : var6.D() + "", () -> this.a(var6));
         var2.add(
            new com.yiyiaddon.l.c.f.c(
               var8,
               () -> {
                  if (var6 == this.a) {
                     switch ((int)com.yiyiaddon.m.b.a<"s8mlqgb6rq47","T0VD35HTLnHe/9Vvn5X8xLZTXFallI40K1DCamxPaJY=",7448925730097159377,-4807738332261508138,-6028661288250787102,-3299699824455200706>()) {
                        case 947995874:
                           switch ((int)com.yiyiaddon.m.b.a<"s2pigj6ce3pami","8cpiQAk4ohL1hhEpkUQ7SH8dl3hwxEXWvVQoIp8QNkA=",812085023632492894,6118594376125553815,-3879971529756856874,-6732521166373167872>()) {
                              case -1843459538:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var10000 = var6.D() + "";
                     switch ((int)com.yiyiaddon.m.b.a<"s15t002kfg7xt6","jItTsasOGC4lWQzeU4ImFt6egthvHgewpD/qH18atAM=",-1011390136483091597,7081997260539239602,-2547474005281167690,5554603375129039974>()) {
                        case 683550388:
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
                     (String)com.yiyiaddon.m.b.a<"s3nlposa2zpsgr","DQw9HiZIrjd65SUYrBcUI+zutU9Ox9AADJ1XC+ZrtDy4Mbr4",-1846610266461982746,-3887461979805489442,-4123057243746457861,-5448411026310271453>(),
                     this::C
                  ),
                  (String)com.yiyiaddon.m.b.a<"slbrnj3ufyvvk","SjT+sSUJG4gsWNnEfzbmPrXDbOyCjNqSVgFKcCE9a82zspjq9GLtGk4Yf5CBH0H8f8pWq/g0L16bTHFCJVDy3ruEPJjo6nw/zxrKegbRuxIVCmXaz+xehDsIfIvc+WqJLqKRfw==",6923162121286560520,-7451863449444185346,7229602771586926106,-9156010615330096662>()
               ),
               com.yiyiaddon.l.c.f.a(this, this.b, this::C),
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"s3aa4aipadtscd","coygctjoTdTU0c4a7qgmA8tDK26uOfD3z8PE1AlvRLB6u+vj",924487947404250740,617065867117695950,9114637984863754742,-3715271015718214060>(),
                     () -> {
                        if (this.minecraft != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2d812gtigqr3p","t6jvL8ZwkcsT89uMoFVQhdf6FK5vpAKbmbkAZWJuDDM=",5466426000094240734,6638606779348110613,4571911451308607581,2360896289181833750>()) {
                              case -124480167:
                                 this.minecraft.setScreen(null);
                                 switch ((int)com.yiyiaddon.m.b.a<"s1q0niepngbps7","catfgoyjBVjCqGM5ByRQ7DYiji2OrXyV0Nc1QEXrsqo=",5969345614155520458,2850929305549218182,9087622399436201424,-8679677782881769400>()) {
                                    case 530120059:
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
         (String)com.yiyiaddon.m.b.a<"s2vpf0tv33h4w2","ZBz1sflu57IJGpRo5WygmH+NSYao6dqofk3IsJDf",-4311829060639030218,-4435671162785617739,2781735105807260410,2439567231142685547>(),
         -1
      );
      int var4 = var3.length;
      int var5 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"seclsqa9zm6g1","oY58sEqzuop/OlB7PEQ3dkA93hJ3H8okL15407espjY=",3451516900502752054,-1629640492679190746,-7326231801497709355,-3462403133177896500>()) {
         case 1926614442:
            while (var5 < var4) {
               switch ((int)com.yiyiaddon.m.b.a<"slnmk4blbcggp","PqD20KIoBsWmBSXcXKNt3Mm/oV/QCmG/Is3eu5jN5yw=",-4671411161179544167,7359753296675879142,-8764208767743385866,5672530956000365619>()) {
                  case 1739378039:
                     String var6 = var3[var5];
                     if (var6.isEmpty()) {
                        label68:
                        switch ((int)com.yiyiaddon.m.b.a<"s1bzgpaegfrd91","C2tPX7ICUuNM46bokqBN4/VaLfvLFsoRNfIYHSAUQ/E=",7077596907642303134,4588915495308202039,-8159563954169057792,5145680792471569031>()) {
                           case -2002339733:
                              var2.add(
                                 (String)com.yiyiaddon.m.b.a<"s1shkov7g0l8vk","xTTSwif3JEjmz4fm+gRc5BAnOD8hKzkZkMkFtA==",-17674220683671015,-246781714681674579,6793967904522157064,1821952533285307801>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s2vhse8go51drf","B2mnIKQl4JxooEXRvGskJL3ECAOnYe12svtvmVshvco=",-5647471499586052699,6283372533227378265,4270451472936328929,6455788392590205444>()) {
                                 case -443889855:
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
                        switch ((int)com.yiyiaddon.m.b.a<"stacyvo673mpl","dhcHJEGEieCuQF7RDqRKDiCmfBxascEiYcmgM+gHhAU=",6176905878349321785,4126923404999446634,4855272461902036321,-1584474051951837636>()) {
                           case 136938078:
                              while (var9 < var6.length()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s21ygc00ov2as6","quJBK0jR97aQVDAEzicAgqBSoBhh95hXZ+7nKyoO3J0=",-1305937487191192280,-689615267200222844,-9042970569548894120,-6875630979101756426>()) {
                                    case -1277576030:
                                       int var10 = var6.codePointAt(var9);
                                       int var11 = Character.charCount(var10);
                                       String var12 = var6.substring(var9, var9 + var11);
                                       var9 += var11;
                                       if (var10 == 167) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3c3qbbzrh0dfc","xiSCf2lMpmvI8T2OgrkhBjTucA9sQjvGa+94SMkr+bc=",-1386451934196322267,7903154882813157847,-7256108784752290611,-6586666762482210382>()) {
                                             case -1754451432:
                                                if (var9 < var6.length()) {
                                                   label64:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s388kb3dgv8shf","NGp8BCYMuCktemfXSYVhzT44z7QT6qmL8UkzfJp9Gu4=",-5557754856743947250,4294286983173334373,7480945681762221796,6625027689516747604>()) {
                                                      case -1021350120:
                                                         var12 = var12 + var6.charAt(var9);
                                                         var9++;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2svnrsnwu0uge","gszUnPJ3bDPJpnNdF8VxAnQPblylI/WQ0gJ2BlvHCIk=",6795367449154371272,-5933452233449375069,3051015615225622623,-2516911106052636733>()) {
                                                            case 1084582398:
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
                                          switch ((int)com.yiyiaddon.m.b.a<"s343oiu9n8vz53","yfT9pu7lSq9KX4i/hITiq/JqqtV1OO2IxfJ3aYIvKj4=",-8583676227662829297,-8354346314881884691,-1608095585710779941,7935000008018813497>()) {
                                             case -910653822:
                                                if (var7.length() > 0) {
                                                   label58:
                                                   switch ((int)com.yiyiaddon.m.b.a<"sselc3s6p2sgy","te8Zm6V3ttC9sl8+QZ4F6zYurhsflOPYHdB/KfJjYVo=",2939575284265814880,4107336021483340746,9015508481780952993,4459429213392081296>()) {
                                                      case 23050739:
                                                         var2.add(var7.toString());
                                                         var7.setLength(0);
                                                         var8 = 0.0F;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3c6jz6blqrnav","83YMkr0cbrHoKR5raDJ6ygJHUpcJlLV4Xw5TYXUv0ks=",7957475167242465548,-7194654262652098986,2722892008786980565,-351376361339122536>()) {
                                                            case 891220026:
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
                                       switch ((int)com.yiyiaddon.m.b.a<"sf05xvr8fg5rb","GB653qyRnlYMylWH7eSuX4P5lXlWpw9j+5dNnRxV0Ws=",-4374005963997432857,5037625768127442481,3499445264479307279,-6508154276295136002>()) {
                                          case -392589458:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var2.add(var7.toString());
                              switch ((int)com.yiyiaddon.m.b.a<"s3vemtrngvm2b3","7Ym7pYNPnFGBE7mnoa/rTMDK7Lx3GKf6/Dc2u1rX+7I=",9018203494499807253,2566330993008466166,9007142473424423718,-4838173981539346673>()) {
                                 case 2019404848:
                                    break label46;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var5++;
                     switch ((int)com.yiyiaddon.m.b.a<"s2s3lhiyjw7c7d","A47uQi/eTTlhCS7sCVKbZQNOWWDgTxfHkaaN9LLOEIA=",-7236006232614219925,5246829394625202314,4396275906485872586,-4630111781807307875>()) {
                        case 1139914361:
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

   private final class a implements g {
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
            switch ((int)com.yiyiaddon.m.b.a<"s12da7pf8l67ta","uvVtGx9eptrHbp/Ox6zGmfeyhq/U53TIYUVOVlIJ394=",2631998782978462117,-4010568058250731049,6187116935267981047,6858067366232109648>()) {
               case 1233679552:
                  if (!var1.isEmpty()) {
                     this.aW = var1;
                     this.m = var2;
                     this.n = var3;
                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s3mn96redbtg44","ctmgL2nbMxZ4WP57M1Fia4xcXBPQM6q83+/17xH8tqE=",-7964482821011539751,-3769379382749614717,6649005204333440758,-7236838363458190142>()) {
                        case -361854488:
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
            switch ((int)com.yiyiaddon.m.b.a<"s3l8sgpnkupy41","xdV9SLdUf4Ggk71jR/8WG3CaZmkxKoW42Oxkqs7Xt1E=",-5127744076360713165,-8389682065236510976,517495390998897047,-7122498376098738236>()) {
               case -746406326:
                  if (!var5.isEmpty()) {
                     com.yiyiaddon.l.i.c var6 = com.yiyiaddon.l.i.c.a();
                     List var7 = com.yiyiaddon.e.f.c.a.a(var5, 320.0F);
                     float var8 = 0.0F;
                     Iterator var9 = var7.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s1bht9gumkmgyd","2maCi6dUoydYfXeDpVeTVyFbvR2AHRiL8IwTClf2G28=",-7930046906542407585,-3846861018357637155,5309225346490324972,1399666154223505969>()) {
                        case -1781906681:
                           while (var9.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s29pamm852ge5s","+eEDka3V6s7vQay7mdVF5aco2c+nuE6+7PDRBwliVxo=",-7231065567264429384,2668707819698662593,-6425497993716788807,1904831805923165379>()) {
                                 case -98227452:
                                    String var10 = (String)var9.next();
                                    var8 = Math.max(var8, com.yiyiaddon.l.g.d.a(var10, 10.0F, false));
                                    switch ((int)com.yiyiaddon.m.b.a<"shtdquocugu7s","pGSp2b/hqsoag+L598HAFFouUrw2eBl+DO0HgbKYfGo=",-3351218329654389940,2866982799361817234,-5250591918671104350,5217315284122903485>()) {
                                       case -342465527:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           var8 += 12.0F;
                           float var17 = var7.size() * 12.0F + 12.0F;
                           float var18 = Math.max(var2, Math.min(this.m, var2 + var3 - var8));
                           float var11 = Math.max(0.0F, this.n - var17);
                           j.d(var1, var18, var11, var8, var17, 6.0F, var6.uY, var4, 0.9F);
                           j.a(var1, var18, var11, var8, var17, 6.0F, var6.uN, 0.94F, var4);
                           j.c(var1, var18, var11, var8, var17, 6.0F, var6.uX, var4, 0.22F);
                           float var12 = var11 + 6.0F;
                           int var13 = com.yiyiaddon.e.f.c.a.a(var6);
                           Iterator var14 = var7.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s3gga0ehmf8q0r","TJ3xok5T+chD3SpFnHbwhC1b4T7XOKaAXixRy2x1k/k=",2198438271548739286,1267471681926117350,-481242840598060669,-723562101022544095>()) {
                              case -871916331:
                                 while (var14.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3cf321vdxz5nl","50OH/6t6iy89v8eLt4INfHkhM7UQU/9BFppiKG+frmE=",629318036038317643,6012181640248375479,-1912106826488558490,-1049444051536085738>()) {
                                       case 1339458741:
                                          String var15 = (String)var14.next();
                                          com.yiyiaddon.l.g.d.a(var1, var15, var18 + 6.0F, var12 + 10.0F, 10.0F, var13, var4);
                                          var12 += 12.0F;
                                          switch ((int)com.yiyiaddon.m.b.a<"s3b93a75b6c6rf","jjMZF5UcfDCVG3l85D0KunDenk3eXSZEPeZgw+1TcaA=",-9029097387914304863,3586733746810549147,-8776918996287448832,5431846453745008030>()) {
                                             case -2116734532:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s1dggdcx1xwb81","Gy4Y8D3+BnnDKyqUDrfRvUJlYqxyiAwj3ARyHvAofSs=",-1755669649170581811,8766522911390419188,7544322062484412483,-7429616552685395603>()) {
                        case 753562699:
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
      private final String[] B;

      private b(String[] var1) {
         this.B = var1;
      }

      private static com.yiyiaddon.e.f.c.a.b a() {
         return new com.yiyiaddon.e.f.c.a.b(
            new String[]{
               (String)com.yiyiaddon.m.b.a<"s35bo7162lnbs6","ofC06/DSrC8yUmabPgjgGq9utUhwhyWh8lF6OA==",-4970535037627528056,3228889731339308132,-9032514048183500863,-5073863045072966442>(),
               (String)com.yiyiaddon.m.b.a<"s35bo7162lnbs6","ofC06/DSrC8yUmabPgjgGq9utUhwhyWh8lF6OA==",-4970535037627528056,3228889731339308132,-9032514048183500863,-5073863045072966442>(),
               (String)com.yiyiaddon.m.b.a<"s35bo7162lnbs6","ofC06/DSrC8yUmabPgjgGq9utUhwhyWh8lF6OA==",-4970535037627528056,3228889731339308132,-9032514048183500863,-5073863045072966442>(),
               (String)com.yiyiaddon.m.b.a<"s35bo7162lnbs6","ofC06/DSrC8yUmabPgjgGq9utUhwhyWh8lF6OA==",-4970535037627528056,3228889731339308132,-9032514048183500863,-5073863045072966442>(),
               (String)com.yiyiaddon.m.b.a<"s35bo7162lnbs6","ofC06/DSrC8yUmabPgjgGq9utUhwhyWh8lF6OA==",-4970535037627528056,3228889731339308132,-9032514048183500863,-5073863045072966442>(),
               (String)com.yiyiaddon.m.b.a<"s35bo7162lnbs6","ofC06/DSrC8yUmabPgjgGq9utUhwhyWh8lF6OA==",-4970535037627528056,3228889731339308132,-9032514048183500863,-5073863045072966442>()
            }
         );
      }

      private static com.yiyiaddon.e.f.c.a.b a(com.yiyiaddon.e.f.a var0) {
         if (var0 == null) {
            return a();
         }

         com.yiyiaddon.e.f.a.a var1 = var0.a();
         List var2 = var0.f();
         return new com.yiyiaddon.e.f.c.a.b(
            new String[]{
               (
                     var0.g()
                        ? (String)com.yiyiaddon.m.b.a<"s3lfsphvcphgsc","JW/RD0fe07NtlL+fgLry61fLWZrRFUxZrN/58IaXmI+ic9WsFW8=",-295425302655103816,-5752683705405752680,6112999568977693893,4217488509077827240>()
                        : (String)com.yiyiaddon.m.b.a<"s2lgy8dsyas6ub","6CPNab9Osfx1ITu2acMeR/JyI7T4rSUYVg0L3pxBVNsnoCuZD9M=",5827771805203437530,-7596795323125904072,3179109727832305229,-8093971660940322351>()
                  )
                  + "",
               a(var0) + "",
               a(var0) + "",
               String.format(
                     Locale.ROOT,
                     (String)com.yiyiaddon.m.b.a<"sszhveibk53qv","ufX9EF2UjVBM5lwM8cETlW7qvZYjAhs/TZlZIqBqThXqpn8f",8246656035332658091,5186835254016013854,-1573953874034846081,9037598315446725837>(),
                     var1.t
                  )
                  + "",
               a(var1),
               var2.isEmpty()
                  ? (String)com.yiyiaddon.m.b.a<"si0kwdfh07q","HQCvaxtsyBL420dUzwWcpqnGZWT2auuQv43RWlsDiPComNIwduzRTA==",7070952867585051602,-2513570801032842328,3750336966439927290,1539338123109079116>()
                  : var2.size() + ""
            }
         );
      }

      private static int a(com.yiyiaddon.e.f.a var0) {
         if (var0.a() == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3txpwgr8740p3","9bDWP2V/hcLgDBv4y5wEvyO5RWlbAQ+0qJxC64zPixc=",4060410187813222787,-1889970941610329293,-3593796472738738311,-5424075834205107965>()) {
               case -1347356342:
                  switch ((int)com.yiyiaddon.m.b.a<"s31sbzb1hh16ul","1b+jEy9sjw5/O5icJOAX4+rRMYaFVejpFCdUyElOR6k=",5111343930973495580,-6923057532656151468,2076311839477728688,-8949658296894901926>()) {
                     case 841059888:
                        return 0;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s3svls84zkebcs","vvOh0bT2zHJruWqXMN8xE4+FsEBavRG05g2aKyRYvUE=",1629386021297771430,5505612643771179511,5534840673952237310,8827279257736336433>()) {
               case -402913683:
                  return 1;
               default:
                  throw null;
            }
         }
      }

      private static String a(com.yiyiaddon.e.f.a var0) {
         Entity var1 = var0.a();
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s10zw1d60d2sle","m95cPDXBWN/HWl27G6YR6nScfOg+oR1hEczj2J/G77I=",-2431301573834594600,5016962390107806285,4373083746353741641,-4029383209533953137>()) {
               case -1643293553:
                  String var10000 = (String)com.yiyiaddon.m.b.a<"sxlcn5xd8giqj","k1q2nx2yVRa55uKgzmmpQmVS7nNH5szWCv7EahJlQP/l6w==",3789206134345598467,-8937200323886424225,4503046016459156995,-5108028885996680043>();
                  switch ((int)com.yiyiaddon.m.b.a<"s319yyn2o2jdz3","yD5rIgzVRDi2pftkej58SwlqOlsHfT7DWqzh7epZqds=",2953569976808699924,-8091109602555023349,-2240111560519960314,1417111462795256673>()) {
                     case 753597766:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var2 = var1.getName().getString();
            switch ((int)com.yiyiaddon.m.b.a<"s3d7flhchd6acn","mNqU5uKEfZF+LRBka400jEpaC1j+ODORQgSaZGbRopI=",1658075615384009882,-7505452732056052760,2776809125856523491,8135957237538066350>()) {
               case -2139806986:
                  return var2;
               default:
                  throw null;
            }
         }
      }

      private static String a(com.yiyiaddon.e.f.a.a var0) {
         if (var0.be) {
            switch ((int)com.yiyiaddon.m.b.a<"s1h24id5ianhdy","kvkcTPSTDB72hqwxlTk3qcZwceavtLhHTWREJoYRibY=",-2331964787673916086,3250436462328841484,2622600017952378170,-6670980385537198657>()) {
               case -319464352:
                  String var10000 = var0.du + "";
                  switch ((int)com.yiyiaddon.m.b.a<"sh0g8ey4za2iz","lwF+CuqL0oK2D3Pv2tn4WsVVp1hsZzRb2gFmT+yX4Io=",-5272817625445837749,-4175249843688899347,-1706859437014801209,-7440660044760127485>()) {
                     case -177223568:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var1 = (String)com.yiyiaddon.m.b.a<"s119qyc2hhvtz4","xm91vttGVUyhqkHpRCaHnFhu6pf45WQAnf3AwtZpObatewREHoLaNVRXQ1ckFdOUmi0=",8906648970286749186,8965898138987793591,-9198142728552794945,-900787188546788266>();
            switch ((int)com.yiyiaddon.m.b.a<"s2deep34sapjnh","l6kB82qrSdRTWpmbYEgRue2v+5DONkdXGumhRi5ufG4=",-6336995150302689567,-6449060316581894389,7464671844327068418,8730302080733885897>()) {
               case 693928070:
                  return var1;
               default:
                  throw null;
            }
         }
      }

      private String c(int var1) {
         return this.B[var1];
      }
   }

   private final class c implements g {
      private static final float au = 18.0F;
      private static final float av = 6.0F;
      private static final int dx = 3;

      @Override
      public float b() {
         return 36.0F;
      }

      @Override
      public void a(float var1) {
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.e.f.c.a.b var8 = a.this.a;
         int var9 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s1pkkkn7ochblp","0Ssv828h/toYnkhdunPaQMQN01NBdcUoouRbLkrvkNE=",-7013184746322357793,-6591855664478250349,-6840670304065175530,-8357848551693621242>()) {
            case -1530667138:
               while (var9 < 6) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1dr09xw0uj53o","9+NceGtZI6EnzCcPiwgeS3Qef8pWwBDlkQk8es8y5Nw=",1676408925228438828,-6968783779471325705,7012663575532529013,9047278043315667258>()) {
                     case 2037502036:
                        int var10 = var9 / 3;
                        int var11 = var9 % 3;
                        float var12 = var2 + 6.0F + Math.max(0.0F, var4 - 12.0F) * var11 / 3.0F;
                        this.a(var1, var8.c(var9), var12, var3 + 18.0F * var10, Math.max(0.0F, var4 - 12.0F) / 3.0F, var5);
                        var9++;
                        switch ((int)com.yiyiaddon.m.b.a<"s19tfebc0jgpw9","nLX+C26p2++lKpPDAqT6hTL0Y388E7i4MuompfzTeyI=",-4588382276910218644,7082253492252906805,5872080574647025682,6871046048562917123>()) {
                           case -1222884017:
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
         (String)com.yiyiaddon.m.b.a<"s1ar16irsu0id5","eIDM9awAeLJaChwDDJeoru52SQz6qOlb8cEUFCYmSM0=",1317161981475968138,-6935924181917417785,8852549113109671512,3848999409816017564>()
      ),
      GENERAL(
         (String)com.yiyiaddon.m.b.a<"s28pvgcgk4qe5q","VfoCdfIgNac+SIqf2WihgmVmQ2aRsfd2ceoiGn+ReUE=",-1744571520839583069,-7008303502746302850,1951049274788260336,-7102047529253485669>()
      ),
      TARGETING(
         (String)com.yiyiaddon.m.b.a<"s2yu656lhexhmy","NHud87WKaysQCpH25j1Czj+ZqNaqITIJyqCFVmVRNhA=",-5252486940206428347,-8850002364440910450,4175509029483015045,4455990845305974525>()
      ),
      TIMING(
         (String)com.yiyiaddon.m.b.a<"s3b1m25spydsep","Rih/s5t4sakSmJfrkq76Bjq1hz9Zpu8hra9f/LNpoHI=",-1662086917375234568,1774420335603040883,-1844220150877307544,9219385415237646584>()
      );

      private final String iQ;

      d(String var3) {
         this.iQ = var3;
      }

      private String D() {
         return this.iQ;
      }
   }
}
