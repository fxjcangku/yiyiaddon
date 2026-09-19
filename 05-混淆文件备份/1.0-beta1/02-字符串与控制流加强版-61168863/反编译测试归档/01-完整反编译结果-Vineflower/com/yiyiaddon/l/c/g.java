package com.yiyiaddon.l.c;

import com.yiyiaddon.l.b.j;
import io.github.humbleui.skija.Canvas;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.world.item.ItemStack;

public final class g {
   private g() {
   }

   public static final class a implements com.yiyiaddon.l.b.g {
      private static final int tp = 2;
      private final List<g.b> di;

      public a(List<g.b> var1) {
         this.di = List.copyOf(var1);
      }

      private float w() {
         if (this.di.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s14771d0r6iqla","IJd+MRLs34MklngjsIyP/oI31upoRErLy3SYJ6DwpSg=",125862535778759597,8851229518880600899,8324614664276591423,-5934254620747806693>()) {
               case -241812889:
                  switch ((int)com.yiyiaddon.m.b.a<"sfu5nliw5yweo","Psl8CJkxT9eP2/ZR4/W5Gv21fl/mHHSN5nKzu35/N4k=",-2543640245337982109,781647729733258961,-8149439696703593839,-521111531350481416>()) {
                     case -114852358:
                        return 0.0F;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            float var10000 = this.di.get(0).b();
            switch ((int)com.yiyiaddon.m.b.a<"s3jk47f2pq8h14","W9s+liTeiQryWjLVOPmpiEXlVJgivfXTwne/OWwVWpE=",-5877038457627634835,-6237442401916173616,-7982202924028350306,1439643932044074390>()) {
               case 930041731:
                  return var10000;
               default:
                  throw null;
            }
         }
      }

      @Override
      public float b() {
         return com.yiyiaddon.l.b.d.a(this.di.size(), this.w(), 2);
      }

      @Override
      public void a(float var1) {
         Iterator var2 = this.di.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s5ndijzslldpu","2PzpLWLYtlTS03mY+Suk2VJl5sLHD2MEoRxs+cQBvvg=",-7089054481497501398,14140194812367268,-5540118091102955427,-2388657839072058860>()) {
            case -449111140:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3r5eocdhne91c","ulw4ucRo6c9Fd0YFz6D5l9fVSq1rYJi90B6dzLeK8bs=",-3405063444526075834,-3315260678333300470,-8004905083807322007,4872356058131428207>()) {
                     case -498741562:
                        g.b var3 = (g.b)var2.next();
                        var3.a(var1);
                        switch ((int)com.yiyiaddon.m.b.a<"s3s7f8xjg7rjp4","tGNRKZPCwOPmI8rqsFLDILswadg2OoKe4CXiiRRYme4=",7298614106339666616,1743248093446199752,-4967400022420662851,2440219546301093653>()) {
                           case -570220616:
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
         if (this.di.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s144fvfqqp4zaw","UiPyqWAlJsEIXgQeEd4Ja0/JO1jOeK5CD0PUd0Mp4iE=",-6096812053481488929,-5517184778650936749,-1697460701315863804,2801152976301640274>()) {
               case 937932663:
                  return;
               default:
                  throw null;
            }
         } else {
            float var8 = com.yiyiaddon.l.b.d.a(var4, 2);
            float var9 = this.w();
            int var10 = 0;
            switch ((int)com.yiyiaddon.m.b.a<"s20chqn22qhdf9","E3ZOcFD8xGBVM5GwUD06V9AD57efcSpVGyitLyiwbK4=",-3446218699305203961,-6703081935687717206,6415089184099625108,2812754619527956115>()) {
               case 132970839:
                  while (var10 < this.di.size()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1w5dksu751exm","GrPBNBv7I2EwDJwdAORE8b1Tq+iKFZOonqYK5IJTsXA=",-197343469077926201,8389012000164166579,-8451455164952468424,-1139404698761251970>()) {
                        case 942580558:
                           this.di
                              .get(var10)
                              .a(var1, com.yiyiaddon.l.b.d.a(var2, var4, 2, var10), com.yiyiaddon.l.b.d.b(var3, var9, 2, var10), var8, var5, var6, var7);
                           var10++;
                           switch ((int)com.yiyiaddon.m.b.a<"s1talj1zr7j85o","D9MLDugvmFHMoISLTChx8PK2Bao5NB+1ZpYzzyUKlks=",-239504506903257455,8055610146924422613,5981001787404706924,-535179462391513345>()) {
                              case 671471722:
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
         if (this.di.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s3vida5l8vkg8","3OSOgcjUbgM5eMzVt5GLZNM57KyUBZnWy9sx9oXFqTg=",-5744703834160969993,6341965934212053838,-4792050291242082945,-538919162335010349>()) {
               case 1728033812:
                  return false;
               default:
                  throw null;
            }
         } else {
            float var7 = com.yiyiaddon.l.b.d.a(var5, 2);
            float var8 = this.w();
            int var9 = 0;
            switch ((int)com.yiyiaddon.m.b.a<"s3ox1oma2an5ls","+r/U+HYJqoImfYWFf3OdM5l7lvrOmJ+Lc6Y5NoCbDEs=",-1885761451773152949,-3487680522403950855,-4840296092229396602,-5176142981160548523>()) {
               case 1989674213:
                  while (var9 < this.di.size()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2gz7paurj86ff","JvuqM6bBRxoDwUdmIl1AArqrj0FskteR/gpPtGsMMkA=",-7801071916400800137,-5566869196626676478,9089933165621468440,-2025461048710194080>()) {
                        case 692511117:
                           if (this.di
                              .get(var9)
                              .a(var1, var2, com.yiyiaddon.l.b.d.a(var3, var5, 2, var9), com.yiyiaddon.l.b.d.b(var4, var8, 2, var9), var7, var6)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2mrih3fetvbef","cDXxkdJEQnHCDSi+UM1axapFN92e3dYKOHSyturPRH4=",-2672862382879961990,-453101555768442932,3566813352938686850,2171138417013454518>()) {
                                 case -1270991289:
                                    return true;
                                 default:
                                    throw null;
                              }
                           }

                           var9++;
                           switch ((int)com.yiyiaddon.m.b.a<"s1umx96k3zuyp9","2x6DMDccQhdZioHXrUVilNT9oQ7/EZJAovxS2bKq20U=",-8243628015798471486,-5266377185527051816,9066398689008160159,3487624636474965700>()) {
                              case -943805999:
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

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5) {
         return false;
      }
   }

   public static final class b implements com.yiyiaddon.l.b.g {
      private static final float iF = 20.0F;
      private static final float iG = 18.0F;
      private static final float iH = 24.0F;
      private static final float iI = 6.0F;
      private static final float iJ = 10.0F;
      private static final float iK = 11.0F;
      private static final float iL = 11.0F;
      private static final float iM = 70.0F;
      private static final float iN = 14.0F;
      private static final float iO = 4.0F;
      private final String Fj;
      private final Supplier<String> w;
      private final Supplier<String> x;
      private final List<List<com.yiyiaddon.l.j.a>> dj;
      private Supplier<ItemStack> s;

      public b(String var1, String var2, String var3, List<List<com.yiyiaddon.l.j.a>> var4) {
         this(var1, () -> var2, () -> var3, var4);
      }

      public b(String var1, Supplier<String> var2, Supplier<String> var3, List<List<com.yiyiaddon.l.j.a>> var4) {
         this.Fj = var1;
         this.w = var2;
         this.x = var3;
         this.dj = var4;
         this.s = null;
      }

      public g.b a(Supplier<ItemStack> var1) {
         this.s = var1;
         return this;
      }

      @Override
      public float b() {
         return 70.0F + this.dj.size() * 30.0F;
      }

      @Override
      public void a(float var1) {
         Iterator var2 = this.dj.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"stris0ddbf2dm","T6hHbnmSX2sluqcNTFil3hlK/ETjmQ5m5UPZKAywMLs=",9216905971359053692,-7050624673879287215,227745369878797045,-72855731975666254>()) {
            case 1651188517:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s35nvzzuzf4i86","aZfIJFc544f+Dsw0o2+p3vVPASI5McIUKsXMjk56EM4=",9035369566078123929,-6095079758184887518,2382860626842089853,1808215101328980916>()) {
                     case 516227961:
                        List var3 = (List)var2.next();
                        Iterator var4 = var3.iterator();
                        switch ((int)com.yiyiaddon.m.b.a<"s3gy7haj438yon","5HbU7wL7sP8TrmsrY6V82ceRQywnRTfeg5eYD1pHycE=",-5663313404141936052,-2220460694612670480,-3046990796730339020,-162331759086089522>()) {
                           case -311516556:
                              while (var4.hasNext()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1mb4j5u4xvmft","HBlG1fDYqGgEOCu//PJ1PRuUklVmt+RHbnQ/I4V/fUs=",-4353514407189923915,-6935881042353576175,371260180007233564,-7626053063226018691>()) {
                                    case 1940335942:
                                       com.yiyiaddon.l.j.a var5 = (com.yiyiaddon.l.j.a)var4.next();
                                       var5.a(var1);
                                       switch ((int)com.yiyiaddon.m.b.a<"s333qkqfrd3n86","PCbwptef2xwXo7KPAVXMz0Pq0uROLabxerrrg72KmVs=",4383824078226067293,-4542659270974216008,-8468751793819676764,3797210164788993504>()) {
                                          case -1418595981:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s2q81ovwduvuvi","YvzhfsIk1Y9xtmeCTW2o1XFlMkvgc49AoukWLw6+qoM=",-7590999704775940156,5732243459868878183,4758677901615976840,2355687991604686994>()) {
                                 case 1735512909:
                                    continue;
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

               return;
            default:
               throw null;
         }
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.l.i.c var8 = com.yiyiaddon.l.i.c.a();
         float var9 = this.b();
         float var10 = j.h(var9);
         float var11 = com.yiyiaddon.l.i.c.y(var5);
         j.a(var1, var2, var3, var4, var9, var10, var8.uQ, 0.7F, var11);
         j.c(var1, var2, var3, var4, var9, var10, var8.uX, var5, 0.1F);
         float var12 = var2 + var4 / 2.0F;
         float var13 = var3 + 6.0F;
         ItemStack var10000;
         if (this.s == null) {
            label76:
            switch ((int)com.yiyiaddon.m.b.a<"s2yhowgvhxau12","xvcvL9W/hTnHe10TLvQLMZZ+J/8XBsv5mJXDDzJsVc8=",-8479938748732911512,-69012910348067247,7644842604338690035,6436829417731787706>()) {
               case -432889379:
                  var10000 = null;
                  switch ((int)com.yiyiaddon.m.b.a<"s1xroogcyxvx9l","Qn5JMWWhOBxHIDriK2QPc57qgm1vg76Np9f8I+ZmVYY=",-8299506831822250556,2704173092674745594,9216257323621793947,-294703521967062870>()) {
                     case 752725740:
                        break label76;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = this.s.get();
            switch ((int)com.yiyiaddon.m.b.a<"s2d748yw28r2t4","R8ZA0WpNtloAR+UCnRtIzI8Z1WR8+rszYypxUpCy+fc=",660643550042902668,6280396235692565431,3305355106507929228,2472178698041665424>()) {
               case 769444448:
                  break;
               default:
                  throw null;
            }
         }

         ItemStack var14;
         label92: {
            var14 = var10000;
            if (var14 != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s1di786f9dvqf0","2qfsY9uvH2sl+J0tb6IASWpgvTH5qxbfxMAhSdnrnXc=",-4494078017277552158,8696139706955158327,7108484147141666784,4135734010676728444>()) {
                  case -1806576443:
                     if (!var14.isEmpty()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2cphx0hk479e","nD9bhuCjaIRgX9eEGsn3Se+1NTug8IhAvrmDfBQbDf0=",6670189504145063158,-4029094115813043842,9076242092687437477,-4796775768603780551>()) {
                           case 11742974:
                              var27 = true;
                              switch ((int)com.yiyiaddon.m.b.a<"s11tdvs0o7qtgt","fMJtUqi9ziwCuToKmDsSp0/1JBxMsEHCGG/yJl1LEh0=",8937517528850181109,-3056316287529177745,-1715277930124800606,-9053347348082054292>()) {
                                 case 654113976:
                                    break label92;
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
            switch ((int)com.yiyiaddon.m.b.a<"s3dwykyg7pq017","QamRkBHIIEznyR80mDXB2nc1/ob+yVEXmBedtlmRV3o=",-6819484839746477785,456691768128387405,-46975723969608454,8385691120321765799>()) {
               case -788815062:
                  break;
               default:
                  throw null;
            }
         }

         boolean var15 = var27;
         if (var15) {
            label62:
            switch ((int)com.yiyiaddon.m.b.a<"sdop32u9i2jkj","9qNbBcIUausxtAaqRv/43HRs03RGD7J0pF13Tjokv6I=",-4633839603656639816,9119970633271079005,-5358080238666667750,-7486274416479044024>()) {
               case -358508491:
                  float var16 = com.yiyiaddon.l.g.d.a(this.Fj, 11.0F, true);
                  float var17 = 18.0F + var16;
                  float var18 = var12 - var17 / 2.0F;
                  com.yiyiaddon.l.g.c.a().a(var1, var14, var18, var13 + 3.0F, 14.0F);
                  com.yiyiaddon.l.g.d.a(
                     var1, this.Fj, var18 + 14.0F + 4.0F, com.yiyiaddon.l.b.d.c(var13 + 10.0F, 11.0F), 11.0F, com.yiyiaddon.l.i.c.a().uT, var5, true
                  );
                  switch ((int)com.yiyiaddon.m.b.a<"spvqtdqmk8xs2","iIts6hQIwLaFLzHzTQIxX9AyVBCTbdcdf1X/dgrttnc=",7770346093004143334,-6140933562781932997,-5410963942818876039,-9040129261474937572>()) {
                     case 126715609:
                        break label62;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            this.a(var1, this.Fj, var12, var13, 20.0F, 11.0F, var5, true);
            switch ((int)com.yiyiaddon.m.b.a<"s10g489s6oa00x","hBXma0n+0yEGkuqK5ezj9W1SWT9ILbNVMQBMC7/9PrI=",7721467694852308289,7177872039880145369,-5812475737812862668,8219489373418832869>()) {
               case -248996068:
                  break;
               default:
                  throw null;
            }
         }

         var13 += 20.0F;
         this.a(var1, this.w.get(), var12, var13, 18.0F, 11.0F, var5, false);
         var13 += 20.0F;
         this.a(var1, this.x.get(), var12, var13, 18.0F, 11.0F, var5, false);
         var13 = var3 + 70.0F;
         Iterator var24 = this.dj.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s361yi21gfa4v3","WUOR0W9+8/YoC6uJhqDW5ELp4oAA6dt5WLhB0Q6zHEM=",4231745107084325049,-8959212406274131352,8153314234409022195,272535449757656995>()) {
            case 161047291:
               while (var24.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3iwefmmohdhb","2AMYkukrVFBdE4HB7P4KugzfFNDFU5dlM/NS3iAHxWU=",-6748182918868230045,4005319073055459119,-4190041233473145322,4030473297907950397>()) {
                     case 243648563:
                        List var25 = (List)var24.next();
                        float var26 = this.d(var4, var25.size());
                        int var19 = 0;
                        switch ((int)com.yiyiaddon.m.b.a<"s3n539wvja9j6h","XJVygHaVa1+B5OvhlBX57pmQ7DYdLv0t6/iS5v/nySk=",-6124847181573367176,-4457325032240673677,-3282052442645645735,7746218493834411481>()) {
                           case -1344520084:
                              while (var19 < var25.size()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1rdgoasp8x1tl","1XwSpSJVkA28LTsOJgznVjPOOb38zwmwfGR6n5rTi68=",4396970875214720585,-6628228648956953646,2627789372234716457,-2445786527369729911>()) {
                                    case 857526877:
                                       float var20 = var2 + 10.0F + var19 * (var26 + 6.0F);
                                       ((com.yiyiaddon.l.j.a)var25.get(var19)).a(var6, var7, var20, var13, var26);
                                       ((com.yiyiaddon.l.j.a)var25.get(var19)).b(var1, var20, var13, var26, var5);
                                       var19++;
                                       switch ((int)com.yiyiaddon.m.b.a<"s3dc2lwhnnhv6d","XfWNaoTK5K99Y32VVZpN4p8RYKJHxd9uBaG5GYeqHIE=",1651734810788298235,-7917440075894236287,6809100623613808446,-758753079429008425>()) {
                                          case 427543812:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var13 += 30.0F;
                              switch ((int)com.yiyiaddon.m.b.a<"swjankotrht8v","ljaZOdC3Q2yiYjFyeyH/nVzKTqKOtGvgNHPfuAdW+eE=",8803952739038310722,-7833484221053205315,6885030295659592649,-6257949825727318044>()) {
                                 case -93411258:
                                    continue;
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

               return;
            default:
               throw null;
         }
      }

      private float d(float var1, int var2) {
         float var3 = var1 - 20.0F;
         return (var3 - 6.0F * (var2 - 1)) / var2;
      }

      private void a(Canvas var1, String var2, float var3, float var4, float var5, float var6, float var7, boolean var8) {
         if (var2 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s350t9hwfxd361","kG2lE7Gqr5jnr7VyVWzkxyqt2ryYcFufQEl52X8xo9M=",33939418135816946,-3581822738406708908,777694133650713475,7114344609240285598>()) {
               case -1950790481:
                  if (!var2.isEmpty()) {
                     float var9 = com.yiyiaddon.l.g.d.a(var2, var6, var8);
                     com.yiyiaddon.l.g.d.a(
                        var1, var2, var3 - var9 / 2.0F, com.yiyiaddon.l.b.d.c(var4 + var5 / 2.0F, var6), var6, com.yiyiaddon.l.i.c.a().uT, var7, var8
                     );
                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s1fbd0ff9jwxib","BiKI8pguyMbiuye0OyHiNzF4e1an0i07xuh4pdf9gq0=",6390678568759535237,-466368164566597020,-8987432491609141382,868011482165169487>()) {
                        case 1144694031:
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

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
         if (var6 != 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s27871223apu6x","hXJ6xoED+ssYOXD4Ryhzsp1IbpNw0RgiVQ4blqUB4V8=",8907103067862600129,-2295862058417903954,5350199711773255551,-8618159155491310432>()) {
               case 215744035:
                  return false;
               default:
                  throw null;
            }
         } else {
            float var7 = var4 + 70.0F;
            Iterator var8 = this.dj.iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s3vez6pir82z9v","JnqZvnxwWWr0m6WGhHHHk448zS2L72zTFCu7sjgETKI=",-1834397451658539396,-5709093999762094585,7866998118440115795,-5964844055957269163>()) {
               case 113116574:
                  while (var8.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2t55qt3lw2itx","cUC5pXasHJt36GOtGtjhK7uDXYZNiv/rpwINIP0JwcM=",8268800609659158785,1421720563658645803,-7898077583955757788,548731006594614872>()) {
                        case 1620912591:
                           List var9 = (List)var8.next();
                           float var10 = this.d(var5, var9.size());
                           int var11 = 0;
                           switch ((int)com.yiyiaddon.m.b.a<"s2tcw3ov9ieqtg","TKqb3QZtwpvpPeriXorCsvNVqP7FBxssLX92SG3TT7U=",-2626605413418479877,1403517772417995616,-3957774798367277193,-6725534749895982266>()) {
                              case -2091016394:
                                 while (var11 < var9.size()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sbyqnetf7zbmi","4gQ4qMNTYZgVSHEhVGac6WeKI8xSdoalpAWer9a+hS0=",4556403581833310747,7058590438291914111,-4120726655897533588,2090373873336466840>()) {
                                       case 730245109:
                                          float var12 = var3 + 10.0F + var11 * (var10 + 6.0F);
                                          if (((com.yiyiaddon.l.j.a)var9.get(var11)).b(var1, var2, var12, var7, var10, var6)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1j263xy28hw9m","aExAqhQhxDrP08HqA7ppq/vl8r7BHw7RL/CM2MlfDdE=",4133218072593391081,7166226191859307678,-182723629227138502,1189705831505764467>()) {
                                                case 1317134415:
                                                   return true;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          var11++;
                                          switch ((int)com.yiyiaddon.m.b.a<"s1otm0qgl8d4ih","uacMOAf9yrqTAy9/du0Av9iQ8qyh15en7Ho6bUBw12c=",4361569015763304103,6186699501820977975,-7872704493080981741,2365178645704313502>()) {
                                             case 660531835:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 var7 += 30.0F;
                                 switch ((int)com.yiyiaddon.m.b.a<"sylyiw88bbwou","WDUirPwzyZJ8g+BizBCpSLrTlS4UXioypAZmqLkDHyY=",-3583795500555262586,985371272918410069,-6252470289986475741,-2471008686627889690>()) {
                                    case -53220847:
                                       continue;
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

                  return false;
               default:
                  throw null;
            }
         }
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5) {
         return false;
      }
   }
}
