package com.yiyiaddon.l.c;

import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.b.j;
import com.yiyiaddon.l.j.p;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.types.Rect;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.ItemStack;

public final class f {
   public static final String EY = "§7点击切换";
   public static final String EZ = "§7点击色块打开调色板";
   public static final String Fa = "§7点击后按任意键绑定";
   public static final String Fb = "§c清空";
   public static final String Fc = "清空绑定，恢复未绑定状态";
   public static final String Fd = "§7恢复默认";
   public static final String Fe = "把该模块的全部设置恢复为出厂值（会清掉已绑定点位与名单，需二次确认）";

   private f() {
   }

   public static com.yiyiaddon.l.c.f.c b(Runnable var0, String var1) {
      return new com.yiyiaddon.l.c.f.c(
         new com.yiyiaddon.l.j.b(
            (String)com.yiyiaddon.m.b.a<"s2d021ft8eoe77","/Q0sWoSG6gWQaJ01zM8QjVmetF7BhfxaOGFwmrmi",-6540167282171246260,-5193295517183853621,-8228278195574309167,5113865555698070339>(),
            var0
         ),
         var1 + ""
      );
   }

   public static com.yiyiaddon.l.c.f.c a(Screen var0, com.yiyiaddon.d.b.a var1, Runnable var2) {
      return new com.yiyiaddon.l.c.f.c(
         new com.yiyiaddon.l.j.a(
            (String)com.yiyiaddon.m.b.a<"s3e8jl0jvhisfw","cfB4nqfZm1Y9QejzcgLUO84yDgP4jPOoVaXKE3iykFxmK6Y0Ih0rjw==",-7356792989270957234,2406188079459853680,-1637742965195807075,2411698305999110889>(),
            () -> Minecraft.getInstance()
               .setScreen(
                  com.yiyiaddon.l.h.c.a(
                     (String)com.yiyiaddon.m.b.a<"s1ca8t4rsu2wy0","eGYzVjiaxvu4sL1KiKPKIHvUaJhSXrtmvuTFFaglebmA4blP9Fn27w==",2411544305734764539,7408082278794852772,1992137855244040410,-518710506655482968>(),
                     List.of(
                        var1.t() + "",
                        (String)com.yiyiaddon.m.b.a<"s2j2506ysbut9n","ZHWwwoYW5YsB4EetzrhVIL9Zz3RueParZvSOesTsVHrBq1yAE3Z9967ZiV3MRrx+PXjMSIcPJzjA5Lg10sguS03ONb4=",834039917648954727,284835979818459964,-2434305639516299096,340381303133454698>(),
                        (String)com.yiyiaddon.m.b.a<"s2l5azi3jsotcy","hD60ZF9QrDziQv+ZzrKj401/SGldzg2VERCDHTxmoxxp3C285CJE7DtzGUrT5NQMU/cJFcJDYD7DkV4oGUa0JXEC",-4147886542516755711,-6668499250428466208,5784798883951067198,-6924600733436129147>()
                     ),
                     (String)com.yiyiaddon.m.b.a<"s2tlha626j4w56","Fw9xhbAJGLFaQfQIZ7NssnpugTEdzur5T1mKvRtJL81ObT9aQn/sSe1V5bA=",4349466583057301326,530986193574912077,-9171415790816865848,-5237495725708785804>(),
                     () -> {
                        com.yiyiaddon.d.b.e.a(var1);
                        var2.run();
                     },
                     var0
                  )
               )
         ),
         (String)com.yiyiaddon.m.b.a<"s2ya9d9ugfdi9m","UweMbkVSlUDP/R8iCliVB8yJD61F0QwZCBxhsDnFCbJSZYvDFcPiHBt/0fYaS74zpziUQPFTVosXUGSayl2/usvVZ+i1jJXeW8JcLFva4fRBTUUefnn3EWwE+MT/zVNB",-1317625805260146689,-8351033463644231417,-3602866500854905054,7747871524484760120>()
      );
   }

   public static final class a implements com.yiyiaddon.l.b.g {
      public static final float ig = 24.0F;
      public static final float ih = 24.0F;
      private static final float ii = 6.0F;
      private final com.yiyiaddon.l.c.b a;
      private final List<com.yiyiaddon.l.c.f.c> de;
      private final float ij;

      public a(com.yiyiaddon.l.c.b var1, List<com.yiyiaddon.l.c.f.c> var2, float var3) {
         this.a = var1;
         this.de = List.copyOf(var2);
         this.ij = var3;
      }

      @Override
      public float b() {
         return this.ij;
      }

      @Override
      public void a(float var1) {
         Iterator var2 = this.de.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s243m724cplthe","s6TVu4grQTvs229NidXpAuZ1Bx7oQ/E01kAvRnWvBJ8=",-5250015147977361903,-1422372211162913559,-8295321694359653676,-5399489222464547753>()) {
            case 816528759:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1eok404fsskhg","oTOVpwTaUDl2IAN6m8gjYTD1DY2sHaUp7+MZb6sb7VY=",-9190433555874554563,-3623250045996938329,743625309971605601,-1190929458622670328>()) {
                     case 816667034:
                        com.yiyiaddon.l.c.f.c var3 = (com.yiyiaddon.l.c.f.c)var2.next();
                        var3.a().a(var1);
                        switch ((int)com.yiyiaddon.m.b.a<"s1ner1y36t5est","4VY0m9d0Mdxad5w3FRvzeEqEugiasFCm/x9EVqM45/o=",32503322266517165,-5613685846597845324,-3625041215128414798,659526297302659036>()) {
                           case 1227355941:
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

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         if (this.de.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s3kh9s2jp3ec58","tCcmWNUWyKqOxcYmTs6DI1FlgHCBcx1izRtMINUuzW0=",-3043157460082494321,-6217758553512427953,7964253545019183618,-2546414265387765069>()) {
               case -79139810:
                  return;
               default:
                  throw null;
            }
         } else {
            float var8 = this.c(var4);
            float var9 = var2;
            Iterator var10 = this.de.iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s277fdcvu3d6ro","zwq9t3el5sqqVZqQG506iNtOjUVqEoUM1QnuIKt1ymE=",1827297612016485029,-2480606878011633919,-9180356913629633166,7595888804739044800>()) {
               case -1438881648:
                  while (var10.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s263zu8teuolwo","VmNxJKOG75mhOO/SBHUrPFHDyCoDhVIU0TM/zjMWAKU=",7722427076898923522,2303451059958035934,8433029011667937706,-3409200084864103951>()) {
                        case 614975687:
                           com.yiyiaddon.l.c.f.c var11 = (com.yiyiaddon.l.c.f.c)var10.next();
                           p var12 = var11.a();
                           var12.a(var6, var7, var9, var3, var8);
                           if (var12 instanceof com.yiyiaddon.l.j.a) {
                              label87:
                              switch ((int)com.yiyiaddon.m.b.a<"s1z94ih53d0r68","HAmXsURCJlcbuR+t4HQSYy3cSS5kOCuzt5X89X0nf4I=",-4763022990889221506,-3162823009501121233,7300058541050903648,1879071424583604193>()) {
                                 case 1351708692:
                                    com.yiyiaddon.l.j.a var13 = (com.yiyiaddon.l.j.a)var12;
                                    var13.b(var1, var9, var3, var8, var5);
                                    switch ((int)com.yiyiaddon.m.b.a<"sfh5w174uhch4","DcBEuRMUfFUmxcxGAAa/ns3PEnaWcJWfA9+k3jItatI=",-5070288974460578389,-1081442479398155338,2213173119573255426,6527848493700374624>()) {
                                       case 1235019937:
                                          break label87;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var12.b(var1, var9, var3, var5);
                              switch ((int)com.yiyiaddon.m.b.a<"s3ikmdhqzifk7v","PFbVAnCqVHGV+SMU07Q+A2ikIJrw/mA3wUQRbVSlR8o=",530158769004527495,2364076611071465216,8287793370062133394,-4457858698590518662>()) {
                                 case 2106145870:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           boolean var10000;
                           label117: {
                              if (var6 >= var9) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sjvkmijbo24gy","/WAXT9j8V6rWuFGazaPA3v4vrWmQnj+bBMhrzewrIQ0=",7446055236951042642,-4693741732715828776,-2387451793795415494,-8204855985367678584>()) {
                                    case 364100328:
                                       if (var6 <= var9 + var8) {
                                          switch ((int)com.yiyiaddon.m.b.a<"sf70a7w49aj28","duaChY4W2NgwPgz7X0Vnk4wSMIgWx+261gf/C117lhs=",-5038014786220584540,3854533853592708819,-4640820712433593772,1793014345960918168>()) {
                                             case -1301472299:
                                                if (var7 >= var3) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3lzatssrznsit","N7w+Z5yurbMkNcWDdVM3C+HySAmGrC6L6Q37uGXCYAg=",-1732289509438930554,-5123092186410179456,6226345459915098736,-8595576882956931282>()) {
                                                      case 1232151393:
                                                         if (var7 <= var3 + this.ij) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s211j6gjq8goi5","hvc0vWA/+nlVyTlscHp1t21eiCdkh6PXYmxR247PaZw=",6147683526035247123,5555295000423986015,-7518070115329542666,4872215740845355113>()) {
                                                               case 2024356143:
                                                                  var10000 = true;
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s2avhj7i2fwykb","q5N81XMhgurmgm+6cftr5Fv5cgiFFg0jf+LbLMHJIy4=",7241064260410051640,-2768768977970186723,-1271059307451978590,904470219877026657>()) {
                                                                     case 2042720864:
                                                                        break label117;
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

                              var10000 = false;
                              switch ((int)com.yiyiaddon.m.b.a<"s6mcxbib3fpaa","KicpXx7udvHhM8IEGNs6ImP4K3N2DDvvUQ0wSpEuRKQ=",-3708001587828929166,4739345250584128753,270996352136033193,-1615707839873147325>()) {
                                 case 1112335139:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           boolean var15 = var10000;
                           String var16;
                           if (var15) {
                              label73:
                              switch ((int)com.yiyiaddon.m.b.a<"s32g22iewyjczt","BTZwn6p14WihbjiMA1kwTqPAmdDIgQ+ChQb14vpVU5o=",3386493632083467218,8072247352174369228,6172816154759484805,-8588119740836448755>()) {
                                 case 311298875:
                                    var16 = var11.gC();
                                    switch ((int)com.yiyiaddon.m.b.a<"skiw2thhbl34m","iXV2dG5zn/wVoWLvc3GXRgRT1JCEpkPT5XPx8pvau/0=",3631935673777156047,3043182542773553790,7572211995509959066,8478645718201308672>()) {
                                       case 521709897:
                                          break label73;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var16 = null;
                              switch ((int)com.yiyiaddon.m.b.a<"s2gb7czn0wg37w","Dn8OoSGPRMU9HW9ZNRovILA1GdaChI161CmBeb79z4o=",-9200618649190046383,2886664914138944809,-5598642213635857530,6825416603309246301>()) {
                                 case 897106202:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           String var14 = var16;
                           if (var14 != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2zf06x3j0b2tc","esQxqUszx7ytGEqBTjkYCzb/kMfdu0znJ1nmW13Jfvc=",-4813031566330690721,-6153709935499992727,5132261460573212710,7337350133175598977>()) {
                                 case 2147113868:
                                    if (!var14.isBlank()) {
                                       label69:
                                       switch ((int)com.yiyiaddon.m.b.a<"s1co02l542kcq9","/LmZQGimfUNPuWGBHpuGqJyY+iAtSIqTP5wjbgSjzhs=",7766842087853623764,-2182643761589031632,5645811184911133956,1267300035957140310>()) {
                                          case 636810152:
                                             this.a.a(var14, var6 + 14.0F, var7 + 16.0F);
                                             switch ((int)com.yiyiaddon.m.b.a<"soangu5k0ilp2","m3GHZT6MUtrsqu6+SauUK9Fak8CQwDbgTGXSF4ZTAf4=",4510804058984935697,-6016241187787801516,-7288149230281846457,-5639781997551602234>()) {
                                                case -1553181187:
                                                   break label69;
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

                           var9 += var8 + 6.0F;
                           switch ((int)com.yiyiaddon.m.b.a<"s1874r0ftxrvus","Pd4SB0SI/HOTaF5QPzR2lRKrSZW2//jqH55AiU7JroI=",-848234932596764896,4731548247328038331,6888519090012335767,3399406765477973533>()) {
                              case -353466565:
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
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
         if (!this.de.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s3jemd584k1voe","T2H/Kz8lj6RQbyrxmMsSK3yKZzvdZz+ilKl92jhFMN0=",2809857375740277071,-2503824847728395083,3391398774370746990,-7582426937793569141>()) {
               case 1896975695:
                  if (var6 == 0) {
                     if (!(var2 < var4)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s150ke0swjccor","v+N5xoqbb/DlcdUHHHe5txCIF5xNhn5xDbSjv1O68MQ=",-1065978030887076811,-3472853842257392534,-5539178634509646769,-8579622454028620165>()) {
                           case 1263619436:
                              if (!(var2 > var4 + this.ij)) {
                                 float var7 = this.c(var5);
                                 float var8 = var3;
                                 Iterator var9 = this.de.iterator();
                                 switch ((int)com.yiyiaddon.m.b.a<"s1pj2u0da4ahbz","ycBSs2EDqn+zNScHW1vJm4i1E54CeuSVXWIRb1VmhvY=",3698032407689160542,5994310940617844883,-7307536880713915071,5664845932157904659>()) {
                                    case 804240259:
                                       while (var9.hasNext()) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s9rj6873rimid","YmqjicG4n7KZJjtcnuHpwlYZZ3Mb0xOxMsnV9TW/2u8=",-6191945084573593374,168902162311455428,-8035074488819146839,-8277242181830033595>()) {
                                             case 984469425:
                                                com.yiyiaddon.l.c.f.c var10 = (com.yiyiaddon.l.c.f.c)var9.next();
                                                p var11 = var10.a();
                                                if (var11 instanceof com.yiyiaddon.l.j.a) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1m00a6aj909bv","IzzK0EOQxdQ1ngDBwFXWndxvT4rDNmyVmtmwg7UHU5E=",7763616785535825903,864369994920611730,8696192083619250177,1741365987154822556>()) {
                                                      case -811796172:
                                                         com.yiyiaddon.l.j.a var12 = (com.yiyiaddon.l.j.a)var11;
                                                         if (var12.b(var1, var2, var8, var4, var7, var6)) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s2egjbg1jlphhr","Z659jtdUKMdiJOjM236WsGlYRopFtCUuUw8/FqTeaLM=",2888289965790082295,6040272772563935034,-3942298495935534492,8604791272736354470>()) {
                                                               case 1268556511:
                                                                  return true;
                                                               default:
                                                                  throw null;
                                                            }
                                                         }
                                                         break;
                                                      default:
                                                         throw null;
                                                   }
                                                } else if (var11.a(var1, var2, var8, var4, var6)) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3iqcysexbswdl","fN6NEdh0RtBEObo8O0EqCz5+dRJXGJRtm2i1zluMTdg=",5448252430475997771,2706501971649203785,5116444326436389400,4360607035459926936>()) {
                                                      case -123505049:
                                                         return true;
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                var8 += var7 + 6.0F;
                                                switch ((int)com.yiyiaddon.m.b.a<"s3tpa76isobkjb","b0K2vWK0QDyWdUc7KXeds0IPm0uXPl+XHCrWmKN7OcE=",7821747349004037037,9083820454537209141,5119305566179990041,7570073413810263666>()) {
                                                   case -1414434752:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       return false;
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s32vn824vitvv4","mk5+I9+o6VEqT4j+xKhfRF2NKMnnqaqjKPvc3R+y6BY=",-3772900533345155055,6141720650923914561,4919007101033493999,2529588318475623056>()) {
                                 case -1073226347:
                                    return false;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     return false;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s27ajnp2335s3z","dGAOiZcpOJHFj173d8ThB+IhtZWglocahbRSZMbGzd0=",786157676252308580,462150558518225353,30202126057426919,9092434827081767147>()) {
                        case 662831250:
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

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5) {
         return false;
      }

      private float c(float var1) {
         int var2 = this.de.size();
         return Math.max(24.0F, (var1 - 6.0F * (var2 - 1)) / var2);
      }
   }

   public static final class b implements com.yiyiaddon.l.b.g {
      private static final float ik = 24.0F;
      private static final float il = 10.0F;
      private static final float im = 8.0F;
      private static final float in = 12.0F;
      private static final float io = 24.0F;
      private static final float ip = 12.0F;
      private static final float iq = 16.0F;
      private final com.yiyiaddon.l.c.b b;
      private final Supplier<String> p;
      private final Supplier<String> q;
      private final Supplier<String> r;
      private final List<com.yiyiaddon.l.c.f.c> df;
      private Supplier<ItemStack> s;
      private boolean eh;
      private float cD;
      private boolean fK;
      private Supplier<String> t;

      public b(com.yiyiaddon.l.c.b var1, Supplier<String> var2, String var3, String var4, List<com.yiyiaddon.l.c.f.c> var5) {
         this.b = var1;
         this.p = var2 == null
            ? () -> (String)com.yiyiaddon.m.b.a<"sjrbd6y64au8l","FfwbD3LZGRH5SSPZz9tyZ81+dlzlt+6rYIh3mg==",5364640038610195298,-7680816753248541727,6250343412649360187,-1618988320509167811>()
            : var2;
         this.q = var3 == null ? null : () -> var3;
         this.r = var4 == null ? null : () -> var4;
         this.df = List.copyOf(var5);
         this.s = null;
      }

      public com.yiyiaddon.l.c.f.b a(Supplier<ItemStack> var1) {
         this.s = var1;
         return this;
      }

      public static com.yiyiaddon.l.c.f.b a(
         com.yiyiaddon.l.c.b var0, Supplier<String> var1, String var2, Supplier<String> var3, List<com.yiyiaddon.l.c.f.c> var4
      ) {
         com.yiyiaddon.l.c.f.b var5 = new com.yiyiaddon.l.c.f.b(var0, var1, var2, (String)null, var4);
         var5.t = var3;
         return var5;
      }

      @Override
      public float b() {
         return 24.0F;
      }

      @Override
      public void a(float var1) {
         Iterator var2 = this.df.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s2irevfesnzf56","+PjuvEh68bY5ndXHWhamaX4CmhBThinzILPzsPhNmEE=",1993166171297682484,-5211427191973320798,-3350420834425956613,6472439756145911777>()) {
            case -2052572149:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1rpfpguqr0hsr","MBWezE+PTPq5Ii22Oqwg4NSp/T1y3CbJTPWXkHUemI0=",-7910499516359097977,-6359728283849664559,6116037839160642052,6025016977317400352>()) {
                     case 2015548411:
                        com.yiyiaddon.l.c.f.c var3 = (com.yiyiaddon.l.c.f.c)var2.next();
                        var3.a().a(var1);
                        switch ((int)com.yiyiaddon.m.b.a<"sayjx6mmz885r","1m68rSd0wk1d7Uji6xHiLdz1aJS6Taw2hBAusrMtkUQ=",-7897948125966842693,7024882643128003274,-6088831494449012929,-33484393970532290>()) {
                           case -1731487090:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               float var10001 = this.cD;
               float var10002;
               if (this.eh) {
                  label22:
                  switch ((int)com.yiyiaddon.m.b.a<"s2xr3krkz1dtor","kZC13ezi601/t01xDfnigDP915WXquSeRV9JBLHucX8=",-4917952906587978231,-5146657191068028213,-6886595194278057154,6632061102961869755>()) {
                     case 994156699:
                        var10002 = 1.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s2h7hqdhefzy23","trjBLiT5nC3chjcIAxYQNSDqnq+iD7k6o/NFc/58pJY=",5881782720891248289,7720055235001703605,-823220025942615525,4679387192415783403>()) {
                           case -1814692635:
                              break label22;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10002 = 0.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"s22dtamix5w33f","kkQ/OOeJgwExjz6dET6KONmyVxMnKXMrEQK+1deFuYA=",-5437061642020311727,8455748246438875004,-1691691588886366930,-6013203949839603649>()) {
                     case 649594368:
                        break;
                     default:
                        throw null;
                  }
               }

               this.cD = var10001 + (var10002 - this.cD) * Math.min(1.0F, Math.max(0.0F, var1) * 12.0F);
               return;
            default:
               throw null;
         }
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.l.i.c var8;
         float var9;
         float var10;
         boolean var10001;
         label234: {
            var8 = com.yiyiaddon.l.i.c.a();
            var9 = j.h(24.0F);
            var10 = com.yiyiaddon.l.i.c.y(var5);
            j.a(var1, var2, var3, var4, 24.0F, var9, var8.uQ, 0.7F, var10);
            j.c(var1, var2, var3, var4, 24.0F, var9, var8.uX, var5, 0.1F);
            if (var6 >= var2) {
               switch ((int)com.yiyiaddon.m.b.a<"s2hlbsp78mxmbt","xgHFMQmE0W6B4aks2IiVCQn/N4jw+IEnkTnnI4nS6DI=",7831003641217192345,-7027482864221531828,-1132264788661016303,-7405250439857650495>()) {
                  case -118166995:
                     if (var6 <= var2 + var4) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2ux732r660h9f","EPcVlubVR3CSxrkOCnlzEdDx/LrQYZNgIof8AT3qGWo=",3267230856904782310,2167968892106126950,1324799718526644710,7190395766468652295>()) {
                           case 1155230625:
                              if (var7 >= var3) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3m0pzbncolvyt","cjkR7Yrnzq+ty1xHsP8oqVdnQZV9Zw9eEBEUn0jl/Y8=",-1235865984038419465,4946801495428953765,5176156945156894082,7539653824554775181>()) {
                                    case -973173778:
                                       if (var7 <= var3 + 24.0F) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s16ypzfggxojtn","QdlKQkAJF53S34qc5xCU59KxolGdK8SRAZEKo22SYt4=",-5833528006797510650,5691061971722482895,135363733596554174,-1965697579544518539>()) {
                                             case 1183895974:
                                                var10001 = true;
                                                switch ((int)com.yiyiaddon.m.b.a<"sovw5gqe0wuw2","BFlklMxrY0irgbt2PGBrzRdEspJW3rUQ8uvpMOYP6UE=",-4769066036925420763,4613050470288455528,6864710851891172532,-6166576253206052243>()) {
                                                   case -671740316:
                                                      break label234;
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
            switch ((int)com.yiyiaddon.m.b.a<"s28qja7k54yhoh","pR3USNl3VGTrcQ7Ood73qa54kBkiVHu6sFPSkJgKNzk=",-5939820486219683664,-389033635651445963,-8521502385520367549,-2610045821026475704>()) {
               case -488379102:
                  break;
               default:
                  throw null;
            }
         }

         this.eh = var10001;
         if (!this.fK) {
            label203:
            switch ((int)com.yiyiaddon.m.b.a<"svru0ln5r5oj3","0xt6FmXiHFg2kbmGoOmJK8Fdx+MzPc1q0lzs1OLP4g4=",108770761686698301,1098189083256061198,2008217549373104497,-5359608679240158961>()) {
               case -1258991775:
                  this.fK = true;
                  float var31;
                  if (this.eh) {
                     label198:
                     switch ((int)com.yiyiaddon.m.b.a<"somnuifxixevd","20J5B8uREsbtMA8bYDFoZTEHc4PXaat1f31t87EF8gs=",-9123445393165118038,-522364970502706279,296895880173494673,3644017533752166125>()) {
                        case 2077967613:
                           var31 = 1.0F;
                           switch ((int)com.yiyiaddon.m.b.a<"s36dbytrllh7y6","wcZhapx5DUrOzhs3PSu/RdgcnO57YQQ4um6GKZDteHw=",2153479536299213022,551693955348551758,8184602059624536280,6258482468116644957>()) {
                              case 203850349:
                                 break label198;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var31 = 0.0F;
                     switch ((int)com.yiyiaddon.m.b.a<"s2g98efu0hu3tc","IY7O7QH3RgJJTdaNQmH9LZEk4zAOY4dz47XTvWwuZPI=",3657149944715938556,894160336913230713,323168376596723860,6731046520906250492>()) {
                        case -2054257167:
                           break;
                        default:
                           throw null;
                     }
                  }

                  this.cD = var31;
                  switch ((int)com.yiyiaddon.m.b.a<"s5x6fmx235ojl","8+1oYXNAU0P22UXQA1STgzUOcxK/ZNsUKUsc2ZRKa1Q=",-5078551196283844160,-7575346318628338952,2020303323180572653,-1778197325719679548>()) {
                     case -1942476332:
                        break label203;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (this.cD > 0.01F) {
            label193:
            switch ((int)com.yiyiaddon.m.b.a<"snu4yea55z0cq","jEZcc7D1yFDrTvUxX+rQSf80KtV81wNqNy88mG5iVMY=",-5865214403945138946,5067193382664808998,7764310718832438439,-4843962115012346210>()) {
               case 2000142297:
                  j.a(var1, var2, var3, var4, 24.0F, var9, var8.vc, var10 * this.cD);
                  switch ((int)com.yiyiaddon.m.b.a<"s2aoww4cnw1e40","mlFDLsNF6xP8MQgce4VjducTxci/hLd+JST8akWMZ4M=",-2894637394380701261,4686049953550572808,5651460589946378746,5465137597010120301>()) {
                     case 445138416:
                        break label193;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         float var11 = var3 + 12.0F;
         ItemStack var10000;
         if (this.s == null) {
            label187:
            switch ((int)com.yiyiaddon.m.b.a<"s20h53197cg8fj","+JrGhHNX67L0PkXSh/2efTj63ZBAB63yuNQ6tJsvoJg=",-9081726158362873387,1143875733542724,32249359561288604,-5372851714141766240>()) {
               case 1867625264:
                  var10000 = null;
                  switch ((int)com.yiyiaddon.m.b.a<"s2dy67uhnwip7h","E4vQQKPrbZ32RYgVeXABt77q4gI920gNtqWjlbUWOLg=",1614762973497018118,3088674604362427990,-5812593117758189232,-1886288023553838873>()) {
                     case 78679219:
                        break label187;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = this.s.get();
            switch ((int)com.yiyiaddon.m.b.a<"s2vpwxadpjdy7t","nA/JKLlIumO9OrcgP4m2bjfq67vkIfsI2PPAny/x1go=",-6871477649322021858,5675692312203897064,-8853339515305919395,-485755492826928200>()) {
               case -347737779:
                  break;
               default:
                  throw null;
            }
         }

         ItemStack var12;
         label220: {
            var12 = var10000;
            if (var12 != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s2z7zj7nnzv01k","y0x+xwKiUVbYE52K0woxgD4EYtOedreZKJ7xUij7P7E=",9205266670855086423,6821071289399097559,-7670030814332134065,-3088683350472327440>()) {
                  case -686815532:
                     if (!var12.isEmpty()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3qnbq6drdnlwz","Fpdfh60zyxH126mZAw2kF5qpJeNw/84El7GE4cLQErc=",-4270335667078410723,8989599697132410781,-7010132822779869975,7856513942981971740>()) {
                           case -1993834469:
                              var27 = true;
                              switch ((int)com.yiyiaddon.m.b.a<"s3i0wlnbs1qmf0","ta1+jaeYm4gdm1Rp176VZE3Y0aESLeWPiZdDPgDrhZ0=",8141298280664529434,-931260724592719780,8654493548789943984,-7644759391723569927>()) {
                                 case 1414482247:
                                    break label220;
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

            var27 = false;
            switch ((int)com.yiyiaddon.m.b.a<"s2yz76czhxsho4","+CuFhWM7FV96SYduXMGVpKD/Uq3UmsIlRJLipCqM7yY=",859560419217394919,-7050059037675573371,3622815286206108314,5817744991954784703>()) {
               case -1734676975:
                  break;
               default:
                  throw null;
            }
         }

         boolean var13 = var27;
         float var28 = var2 + 14.0F;
         float var32;
         if (var13) {
            label173:
            switch ((int)com.yiyiaddon.m.b.a<"s1tdjoewsovo7f","UhIix6wfe64x6EmORlucHDZvEKP1T9V33VxKa9Nx28Y=",5284695442963272509,-8739095078456476494,-3424324539622023845,22229598807105928>()) {
               case -434295529:
                  var32 = 28.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"spsg0p7bkaqg3","biG3RPnKe8w9Oq9mLO1pRajNoeHvaSfkaFmEQVbxfhU=",-6698865469901650760,-9092704182318802289,-2052161927313971989,5199130996596293639>()) {
                     case -211217695:
                        break label173;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var32 = 0.0F;
            switch ((int)com.yiyiaddon.m.b.a<"s1m33xqfqibsip","9wr0CGDJfTdIyn1hBuhxzKpv56Gv4suv2/sELx3HYBw=",-275881081210911605,340485709238540680,-7488341170351154004,-7178681745974029636>()) {
               case -555792235:
                  break;
               default:
                  throw null;
            }
         }

         float var14 = var28 + var32;
         if (var13) {
            label168:
            switch ((int)com.yiyiaddon.m.b.a<"s1bh6rk3orien0","ILhHgNRwthE+3Td93AQ4Dh6Pse52eHXkSgirccVe7KQ=",6796747586803688789,-3789078806798135614,2689905837584110371,807687117808079957>()) {
               case -1301851674:
                  com.yiyiaddon.l.g.c.a().a(var1, var12, var2 + 14.0F, var11 - 8.0F, 16.0F);
                  switch ((int)com.yiyiaddon.m.b.a<"s31dwqhyuhcaon","ZRhZgjIlmZoaz16RYYuRIl5jHa43Lc+o0yw8mVMBa80=",-8008688610011245473,-2512427036260075809,-189998197153247730,4426615598131556559>()) {
                     case -1072220840:
                        break label168;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         String var15 = this.p.get();
         if (var15 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s8qudqwtaj9u6","O062TvBg/LE7nVNX6w54eb8jOJ/HkkS9o7wqIKcJrgE=",515118452283587605,-5507131979942507221,3919145847726263038,-5706993802983038420>()) {
               case 1089016355:
                  if (!var15.isEmpty()) {
                     label161:
                     switch ((int)com.yiyiaddon.m.b.a<"s3jz5tdg2so3u7","h0OkAPSFtQgn+dPhjSlATZiPTbqSeuF0hRA7bBw/EaI=",8649896652081340460,-895292836770012849,-6073030759046642807,1934752126530565026>()) {
                        case -1108946938:
                           com.yiyiaddon.l.g.d.a(var1, var15, var14, com.yiyiaddon.l.b.d.c(var11, 13.0F), 13.0F, var8.uT, var5);
                           var14 += com.yiyiaddon.l.g.d.a(var15, 13.0F, false) + 12.0F;
                           switch ((int)com.yiyiaddon.m.b.a<"s1k93nua68fp7y","wj0gJF7mmvWRVFtM9wTPwQ5JNlhS4gxG9sgCBF8NY2w=",-7512664060717125020,-4542393612309464116,-6507853152365338313,-9066631485687101366>()) {
                              case 768410744:
                                 break label161;
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

         float var16 = this.b(var2, var4);
         String var29;
         if (this.t != null) {
            label150:
            switch ((int)com.yiyiaddon.m.b.a<"s2pxdd4l8pune6","WlB2mCuOjSkH1nwxWPM65bf3gygIE7Iu6xJou7+rNsc=",6135946410874310612,4002350771865154902,-766519481913442545,-6066752021023578140>()) {
               case 103334508:
                  var29 = this.t.get();
                  switch ((int)com.yiyiaddon.m.b.a<"s2vua47nusu9b1","eFEEGNIQ4pVwtluJ4sNcbJ1V3/Es66qi5KojVeFushk=",4076045097646457101,6798109996185390692,-550979026797913728,6402010457458121895>()) {
                     case -1313468259:
                        break label150;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else if (this.r == null) {
            label153:
            switch ((int)com.yiyiaddon.m.b.a<"s1avdgcjoj7vxa","BS06OzTqvRoetXb+W3LMfWYBErvLF4oN76QNbgCflLs=",7209787297125505141,4038546657960621493,-5228776156147890716,5326220840243940825>()) {
               case 1550169769:
                  var29 = null;
                  switch ((int)com.yiyiaddon.m.b.a<"s2z9qpl5nr1qd7","4jbkag09XT6h76imA8LUMS162Qs5C/K2Uyh2EtI4VbM=",4312375627897932461,-7928527245133155490,-7745317640371138543,8060321983489085934>()) {
                     case -614117022:
                        break label153;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var29 = this.r.get();
            switch ((int)com.yiyiaddon.m.b.a<"sxc6ikozz0oh0","N/hHrqJt4kdVQCIefHx+GBspPF6m982ccMHMmXKp4Pc=",8873718570696629618,7584661766428904971,-1646053103958629256,1207822100921048243>()) {
               case -1682205949:
                  break;
               default:
                  throw null;
            }
         }

         String var17 = var29;
         if (var17 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3s7rdkxvshs8c","w+iIJuz5YAj3UqgkGCbqBWL1KtbXaE0gmm7z41nQa/4=",-5107006968996575225,1857913289084162805,-4197560832569523198,-3138315504805909966>()) {
               case 239223214:
                  if (!var17.isEmpty()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3f21eaoe0kq5j","osoVXB8PMdnHEaWkMlF4YBsWzopvt+6toBeaOMwYjqg=",5142970142218192091,1638385853410302810,5286967096558762771,-7443348672402209947>()) {
                        case -1952756683:
                           float var18 = Math.max(0.0F, var16 - 12.0F - var14);
                           if (var18 >= 24.0F) {
                              label141:
                              switch ((int)com.yiyiaddon.m.b.a<"s37ptcbndb209a","87L759+v1MISsG+5bLoZvG2yxquGmGJHQYptmwdLxgs=",-94454594965841734,6876521395824540961,3196563499312810784,-1320716302551522206>()) {
                                 case 1343178412:
                                    String var19 = com.yiyiaddon.l.b.d.a(com.yiyiaddon.l.g.d.cn(var17), var18, 10.0F);
                                    float var20 = Math.max(var14, var16 - 12.0F - com.yiyiaddon.l.g.d.a(var19, 10.0F, false));
                                    com.yiyiaddon.l.g.d.a(var1, var19, var20, com.yiyiaddon.l.b.d.c(var11, 10.0F), 10.0F, var8.va, var5);
                                    switch ((int)com.yiyiaddon.m.b.a<"slroh507wh5e1","8JPRkSao4asxjOoumGAyfLQkftbBkxE9HoEhzAvCj+U=",-4099305883960912367,-3942591537646566947,4771793985017065134,-3684814422915465682>()) {
                                       case 2076124886:
                                          break label141;
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

         float var23 = var16;
         Iterator var24 = this.df.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s3bt5nty7b3v5q","18dLMz39NJjERwSozVXSGNAFTJTjF4tDZeQUyFOArlc=",-5152825880441339398,5586429741085697424,-43282700833726807,5202318865156791565>()) {
            case 1092800885:
               while (var24.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sdi2l5x9isf4n","mEYoBlmvDnuTcRFxZI/RL0V6al9wvSDD/n7g6s1pl+w=",-6658856718103505856,-5309416526583636906,3186386921389676047,-6994461692743731725>()) {
                     case 1417117247:
                        com.yiyiaddon.l.c.f.c var26 = (com.yiyiaddon.l.c.f.c)var24.next();
                        p var21 = var26.a();
                        float var22 = var3 + (24.0F - var21.d()) / 2.0F;
                        var21.a(var6, var7, var23, var22, var21.c());
                        var21.b(var1, var23, var22, var5);
                        var23 += var21.c() + 8.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s2zgw9y9rmyyl9","x1tZWIk7r8YLM7+QN8nk88aJNKo/+fPA/hNAxwelylk=",4449512907454450956,7419603817779586821,8846917417565415604,-6890624304619140485>()) {
                           case 1794302422:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               String var30;
               if (this.eh) {
                  label125:
                  switch ((int)com.yiyiaddon.m.b.a<"sfl08gbls803q","K+Bmwh2ExJMv8LycKBGiBHttZbs8yuVn80ykJLe4O00=",6047603937306652394,3122819882266656572,7469545796780591422,-2024648388336415831>()) {
                     case 148535338:
                        var30 = this.b(var6, var7, var2, var3, var4);
                        switch ((int)com.yiyiaddon.m.b.a<"s1opuarls71d0f","l/4XK3TzQDwS2lwgyfQ0DviF50s8nsQF22B0Unaa1zU=",-3608478748589364578,-2369248305101114719,-4622553522598489859,-3542122602112677519>()) {
                           case 1508900191:
                              break label125;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var30 = null;
                  switch ((int)com.yiyiaddon.m.b.a<"s16zlgdvr17ndu","nxytc7Fwp8vBIFiNHk4i72AmWUu0SZvTq0eMZQ4Kbig=",-48172724323543660,3536327648725580203,-965006652301815480,-5173512138297945451>()) {
                     case 1802123807:
                        break;
                     default:
                        throw null;
                  }
               }

               String var25 = var30;
               if (var25 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1ucqrigz1phu1","Q39jpUGw+0JU3qpRWH5M45YpSrpCl70LAnQGuEGfT64=",5542711840596031995,5384518643264716773,9157363161153754088,-7767906321224435532>()) {
                     case 1710558334:
                        this.b.a(var25, var6 + 14.0F, var7 + 16.0F);
                        switch ((int)com.yiyiaddon.m.b.a<"sqxd9m8banniz","D7BcNP8mb9VqjDWFv6QmH6+Wl1L8nsDZToUWYqit+BE=",1079168623900823713,1950138661539676777,-9047119108194757130,-3633654122904811513>()) {
                           case 671727967:
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

      private String b(float var1, float var2, float var3, float var4, float var5) {
         float var6 = this.b(var3, var5);
         Iterator var7 = this.df.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s27pxwefj2c979","ha8ILjqhWXUn71BVc9KcA48QAkDIZvNVRqcV/DqF4f4=",-1207188759217405060,3948885225705205675,3357357584989456279,-6882931018878029491>()) {
            case 258025829:
               while (var7.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3fevireolwycg","fwlPNnCeF7reyAqM7tFKqP97TUIBMFP000hweyHRaGI=",-6371227925824895047,7600462516142743883,-2780501061447608103,1663257416528241284>()) {
                     case -468254715:
                        com.yiyiaddon.l.c.f.c var8;
                        p var9;
                        boolean var10000;
                        label111: {
                           var8 = (com.yiyiaddon.l.c.f.c)var7.next();
                           var9 = var8.a();
                           float var10 = var4 + (24.0F - var9.d()) / 2.0F;
                           if (var1 >= var6) {
                              switch ((int)com.yiyiaddon.m.b.a<"s32yjubigvw91z","OIAjHI/+t/nJEp2Jv4AOLvpj8pPuSxxYMpoCjUDFGqw=",-4408898220745626746,-5597123703338756377,-6359010451975708740,-3096961065842819203>()) {
                                 case -380211927:
                                    if (var1 <= var6 + var9.c()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"sa8jofhucmt75","6dJGvppbmNtT2zDegphP1ClScGYvQRR0cqegw1+SJyQ=",5030289259359644699,-6947009278348036670,7875754136323812887,8328566775947045550>()) {
                                          case -1549026919:
                                             if (var2 >= var10) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s36pfvkhva3k3e","GW9ogUqMzQJ4EKWUg8bsQYR5zX2OyUDV052HohWSjXI=",7504110076987420488,-2010103332130538960,-2623081078941394954,5996795868055979472>()) {
                                                   case 807125085:
                                                      if (var2 <= var10 + var9.d()) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2w1fgev522eyp","lb3lWHZWWqf7ibDQZ/mboTzQD43V/Wg+otTxs61d0WA=",7560051566472709983,397497081090342569,-2036450823025876160,-8698185808611213431>()) {
                                                            case -321619471:
                                                               var10000 = true;
                                                               switch ((int)com.yiyiaddon.m.b.a<"s1k8xtf0q94v65","4uI6CbEajJpCr7oW3mrMUtuUoXtFWf0UVnW2QCd/G6Y=",-697294118071334846,-468423516321425417,-5690405695022976331,2256805783059481524>()) {
                                                                  case -1512141934:
                                                                     break label111;
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

                           var10000 = false;
                           switch ((int)com.yiyiaddon.m.b.a<"s1foc9hl09e0kx","+S4iCccOoZclaC32rZOGdO7DfAZBIOFgpiXsthNQX84=",2022945031630953179,768848937015445902,3700575178010393670,-935665195906523898>()) {
                              case 162493853:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        boolean var11 = var10000;
                        if (var11) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1rv30s1qa3pti","utTPd0OsycfjsycNpbJRlWcTwqG3P5kxDtUNI0wY2Ug=",1496679034614828658,-8363349795100345001,-947452150926535015,-1517854434744797237>()) {
                              case 1596126935:
                                 String var12 = var8.gC();
                                 if (var12 != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s15xe68l6kledt","TOTB/OY8CaOW0pLkfKm8IGejYZDf9KgSe0FZ8yFgtX4=",6822396273561828647,7691123417837363439,2488308761120209671,6024216750440163351>()) {
                                       case -1126277655:
                                          if (!var12.isBlank()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s16dl3zzhlxvtn","qN3X6IcCdrbf8gzzQRkGALoKD/XE9QSti2bB8ObujO8=",-7657296652367856393,-4343400588847987994,-2653492101817758147,2339852476056828257>()) {
                                                case -1535973737:
                                                   return var12;
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

                        var6 += var9.c() + 8.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"skqf0rgmix0p0","AmlgDOpk1Yyyq0Gi4hhnYn9XyNpQ1Jer5oFF7RePgRQ=",-2723422115262153755,-6724487526079263722,-3996689005423472580,-8427922614055822793>()) {
                           case -426133777:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               String var13 = this.p.get();
               if (var13 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1i84o7mquyrws","DPyy71B6te4SM/hZzCkireXifQeIc1N5yxnuRK6ozso=",6050099954658056012,5626797914620871184,-6082084004299798245,5932848307646534778>()) {
                     case -1391300784:
                        if (!var13.isEmpty()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3dcj8jrz85ybt","WYTM13U1+ErFKoXvDxxso+aaOHQGf/291ZU6c1YiDG8=",-601880582746957044,-8498566646132357161,3464380887974209449,920972022999445247>()) {
                              case -1980308317:
                                 float var14 = var3 + 14.0F + this.C() + com.yiyiaddon.l.g.d.a(var13, 13.0F, false);
                                 if (var1 <= var14 + 12.0F) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s24uozgeqbv1bf","HcfsA9WjfToN03FG6yrRwIl8T7pw9KsC616a3DSyqck=",8138060246228743450,-7366619223502500619,4085359683186518475,661006807581934410>()) {
                                       case 564611696:
                                          if (this.q == null) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1khko0jjg3u0x","OUIMd9/H7hU3IzFNxzVkYaXpz5A8/6OtYgvmrzQxkCY=",1567009749506961581,-3041941956403190611,7591306878169077875,-3169896414413792859>()) {
                                                case -2094905253:
                                                   switch ((int)com.yiyiaddon.m.b.a<"sfldi8fjva88h","C4hj3tfwgp3WYWQ1cdYnTaeX0Gi/5DQ0YLHpaeNOqBM=",-5462815218956276138,9086917383272193021,1905796775915524888,2876622925874358049>()) {
                                                      case 1076423037:
                                                         return null;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             String var15 = this.q.get();
                                             switch ((int)com.yiyiaddon.m.b.a<"s3852ljffp1234","Y36l5uOW34/DoHclZGIwqkXKBTS5AtilQoCKuyQTv5k=",539298618543213340,-3593927160698130101,3755034536054612786,4336969829467614435>()) {
                                                case 364837821:
                                                   return var15;
                                                default:
                                                   throw null;
                                             }
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

               return null;
            default:
               throw null;
         }
      }

      private float C() {
         if (this.s == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s17areqd11bsh2","hBoVeZH7OqmKbSNn2sb4HIxkKZZsHtJxRZLC2Ah+rUg=",-7910199703705801839,4080598511238274468,-474898489699450368,-840103724041170593>()) {
               case 1141569866:
                  return 0.0F;
               default:
                  throw null;
            }
         } else {
            ItemStack var1 = this.s.get();
            if (var1 != null) {
               label27:
               switch ((int)com.yiyiaddon.m.b.a<"s1sjlsdl3bb8n7","7KfVT3B4KGQSPOXBRSwKM6NX7l0EAQSjERi3Bkn3bmk=",3273938469490903652,2328655903924863929,-7901050968975926865,2460031752005084241>()) {
                  case 1277076233:
                     if (!var1.isEmpty()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1xphlfcvr91u3","HN9GgonWDIXeVvWOOaJcJpxqepdrQ4i9mlZGuzn5J+E=",-2551911742156221766,-7193733759918005468,6590281733603122934,5122382340468771361>()) {
                           case -1106077292:
                              return 28.0F;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s1ikz50bi0xplv","QIrKGreVfUmrzr4cvyi0oZnHOfDFuHfmKMNXDwgKCHM=",560661060159788917,2122127717381505412,8467513692305874349,-2418169402721213302>()) {
                        case -172963909:
                           break label27;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            switch ((int)com.yiyiaddon.m.b.a<"s2b4ikyj8pkcty","NLBO+rVnlNkB9qRkmfriTYyS1Y7lJyTF1KjbQFaoyvw=",-6537423494031919475,-45283191042494816,3077708093581385118,4324171425825945052>()) {
               case 34877899:
                  return 0.0F;
               default:
                  throw null;
            }
         }
      }

      private float b(float var1, float var2) {
         float var3 = 0.0F;
         Iterator var4 = this.df.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"sin4qx3dbfvnm","mMePtA43VQ5JCufQ4tuOWs5mNOqLkq66syi9AHV3tIk=",1079127044902754686,-4934234368392984987,-1734012717864083037,3709319265216388458>()) {
            case -1029185067:
               while (var4.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ty1jrsysm6gq","632E8vsz3pEgRb7NBcqMOzbvQXd9RCSLfo7SHSSfgCA=",6183761932716370798,-6997270660021620802,-8743876056096638828,-411453479482620529>()) {
                     case 1624929707:
                        com.yiyiaddon.l.c.f.c var5 = (com.yiyiaddon.l.c.f.c)var4.next();
                        var3 += var5.a().c() + 8.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"siggru6o4mhun","Mihz6KieEISWRWJmxcZ3OZ841fPLwM40zCsBA5BPKn0=",-3667570728415031092,7436326416383983313,2234167875397700603,6202690064505740345>()) {
                           case 186406350:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (var3 > 0.0F) {
                  switch ((int)com.yiyiaddon.m.b.a<"s7w33ohqzvvhu","qA47Njy8hmpu/nKuFHg0zRMzVQo8nVJ6TbnSCgCguoU=",3439868396401971400,7319738594025933141,6135228714106708007,-2259061078920708711>()) {
                     case -552423569:
                        var3 -= 8.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s38mz1lzfic9bl","VmyJwfCwyJGhbxC8NMwpr1RPtU1724xFeFu2Ap8Q6e0=",-360505783612020834,-3692776316817997546,8710932157056790245,470560061773273202>()) {
                           case 1159259745:
                              return var1 + var2 - 14.0F - var3;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return var1 + var2 - 14.0F - var3;
            default:
               throw null;
         }
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
         if (var6 == 0) {
            switch ((int)com.yiyiaddon.m.b.a<"sq7b2jrfo8w7g","6oGH6eK1wjc5ZGIb2xU0FLA1/JjPUrAreOS06XuG+GA=",5639574285188638678,-1871351957670421481,-5974332045017133570,-1792564089826204497>()) {
               case -1868906272:
                  if (!(var2 < var4)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2iqs97vk01han","tbqIkw3HIJkYBiSAuRsbDM7/1lmYX+Wln9WcPVnbiMk=",7758965889193719407,7947554100649509499,4115140389582178096,-413120868612504499>()) {
                        case 675887225:
                           if (!(var2 > var4 + 24.0F)) {
                              boolean var10000;
                              if (this.df.size() == 1) {
                                 label61:
                                 switch ((int)com.yiyiaddon.m.b.a<"s2uy4qnj2msvxi","lsBHx42I1klxJ0oQkYr3SuKP0XHKClx9pv3QbhT5k5U=",-5490211562212463114,-7805117423341644082,7350204950414508182,-7106383936995307766>()) {
                                    case 995370138:
                                       var10000 = true;
                                       switch ((int)com.yiyiaddon.m.b.a<"s1jl29gbw3v688","hyxAPzSuXFFhWNB2dYxP7DCNIFT71n/PHxgCzre0VsA=",-1402300426577664704,2571105525569142746,-3509955376066813503,1948220092055352686>()) {
                                          case 334988667:
                                             break label61;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 var10000 = false;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1t3qyuexjogtm","vNBbWUg4a7b0aszj2uiM0v5hgo90sUmmsemy/GwwGyE=",7237243402799542903,2069292411334247182,-1006550371393644117,-5871517883876493592>()) {
                                    case -837527369:
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              boolean var7 = var10000;
                              float var8 = this.b(var3, var5);
                              Iterator var9 = this.df.iterator();
                              switch ((int)com.yiyiaddon.m.b.a<"s3e90lhg4czsi8","PkRuA1oUIQCe9BibrvDmfx+3VWi9A/Ra9un0L/bn6P8=",-5211664748443046972,-577200720370059252,-1158560393799577936,-6170314684236055227>()) {
                                 case -470629238:
                                    while (var9.hasNext()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"sl5wvyz9yq9zc","oCBbPrJW9pd09a6z+P8g5T6qS/Yt3bXUlOFZB1YQ+oA=",-6590288976527702040,8754153573567592705,7840132918913476293,-704678635713056107>()) {
                                          case 1800266160:
                                             float var13;
                                             label81: {
                                                com.yiyiaddon.l.c.f.c var10 = (com.yiyiaddon.l.c.f.c)var9.next();
                                                p var11 = var10.a();
                                                float var12 = var4 + (24.0F - var11.d()) / 2.0F;
                                                var13 = var11.c();
                                                if (!var7) {
                                                   label50:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3rw7iv4jun9rk","+eZKvi6zrwbLEgA+6vyTx+K3CVe8XkBa9/uB6d3blIA=",1509028811745812311,-748853893900838641,7298242801290377238,-3881234428340800773>()) {
                                                      case 691675892:
                                                         if (!(var1 >= var8)) {
                                                            break label81;
                                                         }

                                                         switch ((int)com.yiyiaddon.m.b.a<"s10pvoane02ax1","L5ZoMMwME5vk9rwOBFBaZhDbx3RlaG0BxsF00vCiFOo=",-1304053710371458624,5474754434428353599,3364286064665976087,-3223892669463616651>()) {
                                                            case 1503166501:
                                                               if (!(var1 <= var8 + var13)) {
                                                                  break label81;
                                                               }

                                                               switch ((int)com.yiyiaddon.m.b.a<"s2yv6nlbz70mnl","2VL9JF4/2+cSUIuyMfGxrR37Ma/qAy8oE6FKsEocN7w=",-3987362090867550522,7656076874716330673,-3448596015368585641,-1728507857781710333>()) {
                                                                  case 166838308:
                                                                     break label50;
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

                                                if (var11.a(var1, var2, var8, var12, var6)) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2hi7chsmg78ti","bjaYakZPfBEFvmlsr47rWN9ieeSvPHzjxrET1tX9JNw=",-9003331001401413720,-3704607474785202457,8459349761906174806,2285261290269648098>()) {
                                                      case 1944218547:
                                                         return true;
                                                      default:
                                                         throw null;
                                                   }
                                                }
                                             }

                                             var8 += var13 + 8.0F;
                                             switch ((int)com.yiyiaddon.m.b.a<"safvqx0b401mw","dBD06d8NiNhlx/G/+Bu7i1b6r88Eb/0yZT65PCbiGdo=",-1275562869884690843,8425393462902248048,116754763858895843,829111415908494164>()) {
                                                case -857610174:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s37ng9g3jozoq3","lTUXrhAc5/TJS5HKbEreQtp0Og9QQnh4C7I6RWfWg2s=",-4685773814034807364,667252834608986886,-3745763027261956447,2554062428038005522>()) {
                              case -267868513:
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

         return false;
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5) {
         float var6 = this.b(var3, var5);
         Iterator var7 = this.df.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"sj2br2pefl129","dNiRRLAshNZzg7bfrkCigb3U3ui0XUpVfAEbCbjIySg=",-2928284653246278837,-7043537572406371703,898379617231402117,-8383062022531220491>()) {
            case -435754003:
               while (var7.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sytfvxvsea5rh","qcS/xdiQSZrJTS0JumnygMEXoEqOD6GdEP8U2wPOurY=",-90497995495436274,-1630520044176101947,-6114508992961746055,732149318751022345>()) {
                     case 1800286013:
                        com.yiyiaddon.l.c.f.c var8 = (com.yiyiaddon.l.c.f.c)var7.next();
                        p var9 = var8.a();
                        float var10 = var4 + (24.0F - var9.d()) / 2.0F;
                        if (var9.a(var1, var2, var6, var10)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s293t5oil25dcu","2LfBuKe7uFZIARWydF9elSIVZeyqsYr0dZ/W8kHnlFg=",-1030480765336549078,5691552312564913486,823585302429222548,2152470332339302787>()) {
                              case 284796669:
                                 return true;
                              default:
                                 throw null;
                           }
                        }

                        var6 += var9.c() + 8.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s25jn8njxbj7wy","DU9nJu0FNWyV3nToS0OtKfmTzeLjT+YMW99emAL0x7A=",3670026983429023115,-7401831892001530681,-6316622670197573709,-3005512757066823348>()) {
                           case -1946617367:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return false;
            default:
               throw null;
         }
      }
   }

   public record c(p d, Supplier<String> u) {
      public c(p var1) {
         this(var1, (Supplier<String>)null);
      }

      public c(p var1, String var2) {
         this(var1, var2 == null ? null : () -> var2);
      }

      private String gC() {
         if (this.u == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1gs8lzzvvegw4","c+BhHEl3LYEa8VOIt5PhNikKEQaMjG0rNj2NgVENSXE=",7375470671803366316,-7675709735460114709,-8207249596867311880,5127531966103012074>()) {
               case -1437450218:
                  switch ((int)com.yiyiaddon.m.b.a<"s23n4ht9bmbdj5","bCuUSDZd2XJ1gTf4/iSMBLZeu09BewXWvC6XefZ1C08=",-7887725805671963636,3502980166846195097,9074443177968703852,7582333094552224595>()) {
                     case 874637370:
                        return null;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var10000 = this.u.get();
            switch ((int)com.yiyiaddon.m.b.a<"s114izir1anhk0","74wsFFSjZJfSMkqmb4pjqabpehbMiwGUpREJFyx2Gd4=",-4628273496419092212,-2220373191947751601,3516425328600668737,-2141843114609781307>()) {
               case 302374986:
                  return var10000;
               default:
                  throw null;
            }
         }
      }

      public p a() {
         return this.d;
      }

      public Supplier<String> c() {
         return this.u;
      }
   }

   public static final class d implements com.yiyiaddon.l.b.g {
      private static final float ir = 24.0F;
      private static final float is = 6.0F;
      private static final float it = 12.0F;
      private static final float iu = 13.0F;
      private static final float iv = 90.0F;
      private static final float iw = 0.36F;
      private static final float ix = 12.0F;
      private static final String Ff = (String)com.yiyiaddon.m.b.a<"smskb1mmkke5f","YdBkiDwWJ/BoNOCDGe+ZtxwawGeBNMIN9oEnVAKP",-7307508494112747252,-1689023097408165420,-5969581023369243834,-2185853626166995705>();
      private final String Fg;
      private final i e = new i(6.0F);
      private final com.yiyiaddon.l.a.d b = com.yiyiaddon.l.a.d.a(0.24F);
      private final com.yiyiaddon.l.a.b c = new com.yiyiaddon.l.a.b();
      private final Set<String> aT;
      private final String Fh;
      private boolean fG = true;
      private boolean eh;
      private float cD;
      private boolean fK;

      public d(String var1) {
         this(var1, null, null);
      }

      public d(String var1, String var2, Set<String> var3) {
         this.Fg = var1 == null
            ? (String)com.yiyiaddon.m.b.a<"s1d0axl4wwelbd","dyX00Swsevxa1KSL8eG51HIPAnD6MsboJYBIRA==",6240808736014296997,-7588486275522397985,7754481114656271320,5304336600678234320>()
            : var1;
         this.Fh = var2;
         this.aT = var3;
         this.fG = var2 == null || var3 == null || !var3.contains(var2);
         this.b.f(this.fG ? 1.0F : 0.0F);
      }

      public i c() {
         return this.e;
      }

      @Override
      public float b() {
         float var10000;
         if (this.e.a()) {
            label15:
            switch ((int)com.yiyiaddon.m.b.a<"s1pkg96w8q7i7h","dQNPI41HTBetmB11f95so+6tJ6PvkzndizlRBHVOSVs=",7862859897383715023,3236151373467914534,6528098155818364690,7897004650435985638>()) {
               case -889320439:
                  var10000 = 0.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"s1ssuxudo1pe8g","kJh/Rer4wMzQaVZssRXuIrEzhPPHMzlrFmrCroa/5kc=",-1404429638842960714,1438734535945914296,-91230078128320902,8164233418377870918>()) {
                     case 901903169:
                        break label15;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = 6.0F + this.b.r() * this.e.b();
            switch ((int)com.yiyiaddon.m.b.a<"s1bl6gd1rcjskm","VK7k8yPZiGf01eTLH3M/J6S/opRRBPPsbm00qvysX8A=",5650044311666524803,-9214965925769341821,-5656208990949857540,-5831826616317750581>()) {
               case 1341429296:
                  break;
               default:
                  throw null;
            }
         }

         float var1 = var10000;
         return 24.0F + var1;
      }

      @Override
      public void a(float var1) {
         float var10001 = this.cD;
         float var10002;
         if (this.eh) {
            label29:
            switch ((int)com.yiyiaddon.m.b.a<"s2gu2eb375s4bg","ubaHMILTVk6ByowoJiyOFswfBBlcDXW9hVSB/G3h5ec=",1664907334885788410,-6474163794292933478,1814832452204838322,-1900887792621331940>()) {
               case 1389682232:
                  var10002 = 1.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"strzytj0f2et","FAfBvm7pGgRxHIE1Q0y4mkyWk2e3aG6g9mFCLPVBLsk=",875123462207588487,-1424451111118851401,2004101977846898593,8291997679607645236>()) {
                     case 1430737770:
                        break label29;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10002 = 0.0F;
            switch ((int)com.yiyiaddon.m.b.a<"s3ikjottsnn9np","ykjqDLe/5gy6NE1anKz2N04Ctml9gUvbNPh1YZvHsMg=",-9079832577438932009,-1236686191184496551,-2921439037233417715,-1493469141938487663>()) {
               case -850270961:
                  break;
               default:
                  throw null;
            }
         }

         this.cD = var10001 + (var10002 - this.cD) * Math.min(1.0F, Math.max(0.0F, var1) * 12.0F);
         this.c.a(var1);
         com.yiyiaddon.l.a.d var10000 = this.b;
         if (this.fG) {
            label22:
            switch ((int)com.yiyiaddon.m.b.a<"s3qh5ziqz8eshr","Ydvfpd/pNF5dedl0V6fNcx0WlunSXotDgMExxl0Gz3U=",7414497600282775631,133459839895442664,-5315909394716716660,-6770450758048093799>()) {
               case 1362537921:
                  var10001 = 1.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"s3jqe9nagzqyy9","auRHg6zv3PZj7xmfXJLqC5lVTqgfJvKtkF8MaXCXtP8=",7671222206396576259,-1660415148368440792,-8637748049146777765,-7740707221794679273>()) {
                     case 1916764794:
                        break label22;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10001 = 0.0F;
            switch ((int)com.yiyiaddon.m.b.a<"s20yca7h4cj2zg","Z6ac6GO1X8BLWk/piyvV4l0uUamXnA+rjRyGaIqfPec=",7475143447070118681,8430139714206949768,-4561511895969309573,2399986655506193965>()) {
               case 1058816369:
                  break;
               default:
                  throw null;
            }
         }

         var10000.d(var10001);
         this.b.a(var1);
         this.e.a(var1);
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.l.i.c var8 = com.yiyiaddon.l.i.c.a();
         float var9 = j.h(24.0F);
         float var10 = com.yiyiaddon.l.i.c.y(var5);
         j.a(var1, var2, var3, var4, 24.0F, var9, var8.uQ, 0.7F, var10);
         j.c(var1, var2, var3, var4, 24.0F, var9, var8.uX, var5, 0.1F);
         this.eh = var6 >= var2 && var6 <= var2 + var4 && var7 >= var3 && var7 <= var3 + 24.0F;
         if (!this.fK) {
            this.fK = true;
            this.cD = this.eh ? 1.0F : 0.0F;
         }

         if (this.cD > 0.01F) {
            j.a(var1, var2, var3, var4, 24.0F, var9, var8.vc, var10 * this.cD);
         }

         boolean var11 = this.c.a(var1, var2, var3, var4, 24.0F);
         float var12 = var3 + 12.0F;
         com.yiyiaddon.l.g.d.a(var1, this.Fg, var2 + 14.0F, com.yiyiaddon.l.b.d.c(var12, 12.0F), 12.0F, var8.uT, var5, true);
         float var13 = this.b.r();
         float var14 = com.yiyiaddon.l.g.d.a(
            (String)com.yiyiaddon.m.b.a<"smskb1mmkke5f","YdBkiDwWJ/BoNOCDGe+ZtxwawGeBNMIN9oEnVAKP",-7307508494112747252,-1689023097408165420,-5969581023369243834,-2185853626166995705>(),
            13.0F,
            false
         );
         float var15 = var2 + var4 - 14.0F - var14 / 2.0F;
         var1.save();
         var1.translate(var15, var12);
         var1.rotate(90.0F * var13);
         var1.translate(-var15, -var12);
         com.yiyiaddon.l.g.a.a(
            var1,
            (String)com.yiyiaddon.m.b.a<"smskb1mmkke5f","YdBkiDwWJ/BoNOCDGe+ZtxwawGeBNMIN9oEnVAKP",-7307508494112747252,-1689023097408165420,-5969581023369243834,-2185853626166995705>(),
            var15 - var14 / 2.0F,
            var12 + 4.6800003F,
            13.0F,
            j.a(var8.ve, var5),
            (String)com.yiyiaddon.m.b.a<"sihlqjl6y75a3","HVqSRdhWY8PdRBb8KVs5bKzrM5Y5KxuxYsqKC2GoPPjbYjcL7epnelxBUDJhC8t7U/2INIfw/KPl/ZPq",4754058245732361983,2599378488601859484,4936856684566436841,4004477888635151017>()
         );
         var1.restore();
         if (var11) {
            var1.restore();
         }

         if (!(var13 <= 0.01F) && !this.e.a()) {
            float var16 = var3 + 24.0F + 6.0F;
            float var17 = var13 * this.e.b();
            var1.save();
            var1.clipRect(Rect.makeXYWH(var2, var16, var4, var17));

            try {
               this.e.a(var1, var2, var16, var4, var5 * var13, var16, var16 + var17, var6, var7);
            } finally {
               var1.restore();
            }
         }
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
         if (var6 != 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s1lu0d86u58gsg","o9AXD9rQ0iom4L5Pfx/DVq7AFmw0LhimqvZ/1xsDHt4=",5772357850482850937,-7731596766712118978,-4085153577373047609,-4166682897472746595>()) {
               case 699833838:
                  return false;
               default:
                  throw null;
            }
         } else {
            if (var1 >= var3) {
               switch ((int)com.yiyiaddon.m.b.a<"szh2xjyr0yt34","CoQSIJgnoodSiFfVyF7f/wz5bS2fE5/FmxPpWoAZvm4=",7110631263766689044,-4076923730932901007,-226687893742401161,2326900602036506105>()) {
                  case -865521789:
                     if (var1 <= var3 + var5) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1246z79ddy1jf","GISZW8tpJ3xlQ75CZHQYcnIwK+qGAnRBbNgVYbLZGXk=",-2655343103903240310,6885902832235641748,-7872049445887954133,2726413939310973661>()) {
                           case 682445424:
                              if (var2 >= var4) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sy1afz442dhwx","YZCoChPdsJ8I/pNJbVNB4uDvlVwYA0FUWWILj4mbfX8=",-6268388733247594108,7212507116724899157,2369576544971128754,-8974081016452083659>()) {
                                    case -914795209:
                                       if (var2 <= var4 + 24.0F) {
                                          switch ((int)com.yiyiaddon.m.b.a<"snsc0qpvna0oo","X/tpv7BBIbufERwHdGvKAjrC3BO2moBMUIHOE2Xrfiw=",8236605724737293267,6099142531303710570,5769081420350217620,3367326283573720338>()) {
                                             case -1027691052:
                                                boolean var10001;
                                                if (!this.fG) {
                                                   label64:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3lojup4ehfsnb","QRzyNGEijnj6V6VJn6v4FXWvmThT1/ckOheP6ksUumk=",4488151444543464836,-818054559950665854,3999522795104338915,525272804841312077>()) {
                                                      case -868456653:
                                                         var10001 = true;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3tiwr7u2f2eaz","p9fg54RYH5C2eY+2GcAh6cH5oS/wr+c3x2+HhOuYHrc=",5580437956759565585,-7928113150443762944,2476815063205835075,6036896867660570829>()) {
                                                            case 1844591455:
                                                               break label64;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                } else {
                                                   var10001 = false;
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1dny0qbndydq6","UiH1xcO9dOIVAWGK7Iqwze/fmIQlDzFdaM0XeIBHlKg=",-7127398537188474328,8179565263570839636,7065326250624470817,3836552787498008508>()) {
                                                      case 740109435:
                                                         break;
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                this.fG = var10001;
                                                if (this.Fh != null) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3ehvlm9fw6ezm","fntz2KISdhZyKSUT4WLqdWxXKn1z9fs4lfCWJlOttes=",8206587524896919417,-4714654204251817974,3515401618173079075,-5563833912474755124>()) {
                                                      case 1639498693:
                                                         if (this.aT != null) {
                                                            label57:
                                                            switch ((int)com.yiyiaddon.m.b.a<"s1as1wv4xlezog","se+a4zBscxD+ufOQIq1G/O33mWmbqnl+U2q5etQ1Y/8=",4589330745212774553,-4305603708041151227,-1591890564824286683,-939210359491967306>()) {
                                                               case -454757462:
                                                                  if (this.fG) {
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s1mutfgd44rd8","tgiZTjvPNCC2J+TPYEj3HwTnbJqJJ8cFgJnrbr3SYWE=",-2540168364395512819,-4122758937949279270,6406549535389825436,-2766633052276712271>()) {
                                                                        case 640076237:
                                                                           this.aT.remove(this.Fh);
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s306jouo5wbbe4","O/0cMIpSaBAFNQgU34+h77kDs5S+BKM6jgJjCY/00Zc=",-7455198526102683340,6695164683959703057,9133233195226498927,-891045015598922337>()) {
                                                                              case -557035139:
                                                                                 break label57;
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  } else {
                                                                     this.aT.add(this.Fh);
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s2dxk63153ganr","SyKWgh/gpY3+CQYp5ApB0/W8M74FuIqy/YKLyoH69SA=",-153595260807306393,-4014658189526322064,-7372796327683153017,6812233059217068204>()) {
                                                                        case 2010921425:
                                                                           break label57;
                                                                        default:
                                                                           throw null;
                                                                     }
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

                                                this.c.jL();
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

            if (!(this.b.r() <= 0.01F)) {
               switch ((int)com.yiyiaddon.m.b.a<"s1l2gy4ba7ntr","Urwm+fTZHcp6P5K8jGbJhsPDK3Ex+J2aUO2viioIBaI=",-5497667412140878836,-67130076411527178,5593961754098744648,-9045703566685622788>()) {
                  case -1166680978:
                     if (!this.e.a()) {
                        float var7 = var4 + 24.0F + 6.0F;
                        return this.e.a(var1, var2, var3, var7, var5, var7 + this.b.r() * this.e.b(), var6);
                     } else {
                        switch ((int)com.yiyiaddon.m.b.a<"s2uddwwb5lspz8","V70ed1tDLwZggo/3zRYbHxetddnaVm1IQuItPiq58jo=",8533955136694863074,-2763596263641737652,6740634411927266574,-1847997154395979532>()) {
                           case 578984240:
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
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5) {
         if (!(this.b.r() <= 0.01F)) {
            switch ((int)com.yiyiaddon.m.b.a<"sxvi7e09fwzs1","3FAhs6gskAGIizxfSj/HHZI3nhqtpJVTNA7tr7/0vMA=",-2756275362619682874,-754987972968501784,-5845839730768500287,65764925047857274>()) {
               case 1931234011:
                  if (!this.e.a()) {
                     float var6 = var4 + 24.0F + 6.0F;
                     return this.e.a(var1, var2, var3, var6, var5, var6 + this.b.r() * this.e.b());
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"se19iz6uia8hb","PJ9InhyKKWUnLP+jLV/I0uUl7zBAg2bFynzLjNTT52k=",2545994732984861704,-1442320428846159423,4982287380866984296,-5263253920740053693>()) {
                        case -658369523:
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

      @Override
      public void F() {
         this.e.F();
      }
   }

   public static final class e implements com.yiyiaddon.l.b.g {
      private static final float iy = 18.0F;
      private final com.yiyiaddon.l.c.b c;
      private final Supplier<String> v;
      private final String Fi;
      private final float iz;
      private final float iA;

      public e(com.yiyiaddon.l.c.b var1, String var2) {
         this(var1, () -> var2, null, 18.0F, 11.0F);
      }

      public e(com.yiyiaddon.l.c.b var1, Supplier<String> var2) {
         this(var1, var2, null, 18.0F, 11.0F);
      }

      public e(com.yiyiaddon.l.c.b var1, String var2, String var3) {
         this(var1, () -> var2, var3, 18.0F, 11.0F);
      }

      public e(com.yiyiaddon.l.c.b var1, String var2, String var3, float var4, float var5) {
         this(var1, () -> var2, var3, var4, var5);
      }

      public e(com.yiyiaddon.l.c.b var1, Supplier<String> var2, String var3, float var4, float var5) {
         this.c = var1;
         this.v = var2 == null
            ? () -> (String)com.yiyiaddon.m.b.a<"s11p4suvcery62","puqrF+UY0cvIbap7Lu/zbMRyKCQJI6uN4nX9mQ==",577480618813740569,3224976045544402508,9073613163691716024,-2275295380457081200>()
            : var2;
         this.Fi = var3;
         this.iz = var4;
         this.iA = var5;
      }

      @Override
      public float b() {
         return this.iz;
      }

      @Override
      public void a(float var1) {
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         String var8 = this.v.get();
         if (var8 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s12t3hy75c0nlb","ZerLMW40Z0aCwJpb1UGvZdGYxPm2aNUtrAM5TBiG3UA=",1137916374412806691,5283670565482015736,-7413000272596980880,8454163927160737269>()) {
               case -401393128:
                  if (!var8.isEmpty()) {
                     label46:
                     switch ((int)com.yiyiaddon.m.b.a<"s2tek2u3gntqeu","3J4x7IlImvTWbK/Gkr7VAQ0DrnSifqnQm12XadRHbLg=",7287713300735474055,1717064555899759103,-9111270009852081969,-2716032165777456184>()) {
                        case 869885287:
                           com.yiyiaddon.l.g.d.a(
                              var1, var8, var2 + 6.0F, com.yiyiaddon.l.b.d.c(var3 + this.iz / 2.0F, this.iA), this.iA, com.yiyiaddon.l.i.c.a().uT, var5
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"s3c32ksexgarba","PxvV8WIxcSgcINycWk3BlXkRrcwU213nI6MCQJiWmBg=",5011131530990362807,-7640813448230212155,4274965225661762006,-4049816276858290493>()) {
                              case 601535930:
                                 break label46;
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

         if (this.Fi != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2q6aipnpg0k1m","Rs6oGv5huu0DImG/wuA6oYZVXFO5dg2o62eBKyH4B60=",337093060274180410,-2930283682914810113,-8960129773653627215,-5075046179897985041>()) {
               case -893939708:
                  if (var6 >= var2) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2ipmycmy8nqik","CwYw+XqEpv1uyT9P86Jb5TXoGz3gzqGrQeO32c65M1g=",8245683921636147183,-2932870072498646473,9079377055191482849,5831406751966400899>()) {
                        case -1795445578:
                           if (var6 <= var2 + var4) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3p2arm7n05u1q","PcDIyIDviBhh/ciGIH8GPlaBTI2hF5/9IKCXV+RpMD8=",-503132284273934743,-6017649063166506383,2522859909573884601,3983167928548211658>()) {
                                 case 1279646186:
                                    if (var7 >= var3) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1lv9etkpuyshn","726ykJPQzcu48Y+/xBPTuylWDUucY2ypUGGiB0UgfCQ=",5084206794047736116,2960049290700398728,3498722719028961413,-1093520561925615963>()) {
                                          case -498491130:
                                             if (var7 <= var3 + this.iz) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s3taho5poubwrn","x/9K/bJO6jFBVoRfmlYpmPWVitrK9ajP9yQaXDMi/pU=",3119243896370472570,-9106601565908173482,-5103151209231676723,6508035900267673352>()) {
                                                   case 137977922:
                                                      this.c.a(this.Fi, var6 + 14.0F, var7 + 16.0F);
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3k7atwdb2elvf","N+whTa7qon3dqV+BTEXK6ezCJol6qNV1Lxukqb/Irbo=",-7103952149499027668,6943717876102575670,8002055106778728757,-466527684803437726>()) {
                                                         case -1806343650:
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

   public static final class f implements com.yiyiaddon.l.b.g {
      private static final float iB = 24.0F;
      private static final float iC = 22.0F;
      private static final float iD = 6.0F;
      private static final float iE = 11.0F;
      private final List<String> dg;
      private final List<List<String>> dh;
      private final float[] c;

      public f(List<String> var1, List<List<String>> var2, float[] var3) {
         this.dg = var1;
         this.dh = var2;
         this.c = var3;
      }

      @Override
      public float b() {
         if (this.dg == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2ef6ftnhdd3w7","K7ECugQ8IW4fkLCbtWN0pw70FqsxO0I7UIFSHXx7vYc=",-6523155517973128892,-7588862212154850039,-1377725536645675086,4138027770573404414>()) {
               case 927074762:
                  switch ((int)com.yiyiaddon.m.b.a<"s1g6el731acpa1","9c5rFWfA921/dIbm47E80wMrinYjaHCQLJhrTyyEP1E=",5273326412171454055,6091355911278020341,-6190679078979014626,4085360196384496643>()) {
                     case 248622087:
                        return 0.0F + this.dh.size() * 22.0F;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s3oov53kt4yz24","y3suX1Nfs3/EaN+MPuMXjbukxjeTjvtlAUAVTIBokvU=",8483563753213771771,6555310862113734160,-4319236410406678535,-1956120666209870832>()) {
               case 306195640:
                  return 24.0F + this.dh.size() * 22.0F;
               default:
                  throw null;
            }
         }
      }

      @Override
      public void a(float var1) {
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         float var8 = var4 - 12.0F;
         float var9 = var3;
         if (this.dg != null) {
            label29:
            switch ((int)com.yiyiaddon.m.b.a<"s18m86nyzbdp0c","FBJzOTitYtoQvyhdCBuTC4HxayPof3/cLjAhyfte/EQ=",6309088925237966300,1863620071072156130,2583978056188817519,-8571422327066343625>()) {
               case 2037223827:
                  this.a(var1, this.dg, var2, var9, var8, 24.0F, var5);
                  var9 += 24.0F;
                  switch ((int)com.yiyiaddon.m.b.a<"sc51b6k52qnmq","a28ArTUbXnM0i7UVNFEm72/vSg7E1yUALaBSDuYKoP0=",-8549185880806167439,-4446981153561183484,-5921635778379122191,5752300379357207502>()) {
                     case -918841945:
                        break label29;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         Iterator var10 = this.dh.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s3pemzu8fiym78","GXdT9GWbC8rhkAfQ2U9DaiY4vbGqSqD/j0ztwQkEq+A=",7889488304755285129,-4259699045396682403,-962861997452120770,-3113481159635458057>()) {
            case -630551119:
               while (var10.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1bpyuuiy6fw1j","eRBKLnfygl3zEHlEjuWCc2odz2MojvfvWeRbNJ9ccnQ=",2519282977942650327,-6537511167467371913,-1366150938652317729,-1771658811110908272>()) {
                     case 1630116152:
                        List var11 = (List)var10.next();
                        this.a(var1, var11, var2, var9, var8, 22.0F, var5);
                        var9 += 22.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"sp7a7g1j310s5","PuD5AznIHisDB0r2AOZnX16TRnKOPriWdnYewxVqcvk=",-5134164840505823715,3484642831994265751,-8693190165663026543,1625285508518208771>()) {
                           case -1335609624:
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

      private void a(Canvas var1, List<String> var2, float var3, float var4, float var5, float var6, float var7) {
         float var8 = var3 + 6.0F;
         int var9 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s3cu3lub2kjhp0","qmriV+O5tIsrZrKb5QFM4O/Pybp680iwV01Zfb2/Vcs=",-4652797407631137813,-2532244739015596842,-5727542495613357726,7361993668736598270>()) {
            case -668261279:
               while (var9 < var2.size()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2p8xde237dtre","m3jG6T7UZqBn+7ln2c7MeY45QkgARTlka/0/hR8O170=",-8551056212541989336,4440552864497363039,884180476388906057,7569059161678087635>()) {
                     case -434863283:
                        com.yiyiaddon.l.g.d.a(
                           var1, (String)var2.get(var9), var8, com.yiyiaddon.l.b.d.c(var4 + var6 / 2.0F, 11.0F), 11.0F, com.yiyiaddon.l.i.c.a().uT, var7
                        );
                        var8 += this.c(var9) * var5;
                        var9++;
                        switch ((int)com.yiyiaddon.m.b.a<"sig9bjpl8x4s9","W2utdBo+DnDU2BUs4wQh4g24HDJU4QJ4+QzlICK77Nw=",-2569597089551289244,9067202534231032118,1098124983087910390,5182324856889307333>()) {
                           case -2104413708:
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

      private float c(int var1) {
         if (this.c != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1g1wmd483fsum","UCva8uZOTcMxJ6A4hRjNJNw+Gq+Mf7+VbWKndVDpcPg=",-4101746269698126981,-2083549448171934384,-7567889671478775974,-4799555034340815157>()) {
               case 446338136:
                  if (var1 < this.c.length) {
                     switch ((int)com.yiyiaddon.m.b.a<"sckcqvtilbzwt","9zjEozQLbbq5tzVzwoRojPSsEx70wcCJRUaOs4JjNBs=",-7299597196247330090,5310545404668091867,-8869798147273133559,7197805433105043053>()) {
                        case 2096711420:
                           return this.c[var1];
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (this.dg == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1tfgbi8jrgytv","xoeN1lswTmq/1JyyKNWhb/v1rsXRGz3FbLTk2xVpZwo=",6926467787641657864,-7711953455341936925,-1924050232333898369,-1673360849476150170>()) {
               case -563672320:
                  switch ((int)com.yiyiaddon.m.b.a<"s62bcmg00x6s","4USjJrRic+Rx6VQSwiAb/TR+mnvoyZidxkxkzcGL6JI=",-520166071143764436,-684445332441802854,7597937524600335505,2302114633487015363>()) {
                     case 777831275:
                        return 1.0F / Math.max(1, 1);
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            int var10002 = this.dg.size();
            switch ((int)com.yiyiaddon.m.b.a<"sv6eor1l3qf1p","m7VHC22HG99ezGToNjJ78qHHi8jKuP8LG/QJKXovl/I=",5171999701464885094,4807739536341429815,3770233947187884860,-8922884167789595789>()) {
               case -1928384672:
                  return 1.0F / Math.max(1, var10002);
               default:
                  throw null;
            }
         }
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
}
