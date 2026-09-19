package com.yiyiaddon.l.b;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.types.Rect;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class n implements g {
   public static final float gm = 24.0F;
   private static final float gn = 10.0F;
   private static final float go = 16.0F;
   private static final float gp = 8.0F;
   private static final float gq = 11.0F;
   private static final float gr = 10.0F;
   private static final float gs = 6.0F;
   private static final float gt = 10.0F;
   private static final float gu = 8.0F;
   private static final float gv = 6.0F;
   private static final float gw = 14.0F;
   private static final float gx = 0.18F;
   private static final float gy = 0.35F;
   private final Supplier<String> k;
   private n.a a;
   private Supplier<String> l;
   private Supplier<String> m;
   private int tj = -1;
   private final List<com.yiyiaddon.l.j.p> dc = new ArrayList<>();
   private Runnable j;
   private boolean ei;
   private float cD;
   private boolean eh;
   private boolean fK;

   public n(Supplier<String> var1) {
      this.k = var1 == null
         ? () -> (String)com.yiyiaddon.m.b.a<"s2j7ysnq6ph7is","M8BRc1Iav5vK0jkP299ZdraBWEF0tGQ+ZLtmcg==",6363823487065607453,-2203325406642841343,-3582363803731147141,-492295930083708362>()
         : var1;
   }

   public n(String var1) {
      this(() -> var1);
   }

   public n a(n.a var1) {
      this.a = var1;
      return this;
   }

   public n a(Supplier<String> var1) {
      this.l = var1;
      return this;
   }

   public n a(Supplier<String> var1, int var2) {
      this.m = var1;
      int var10001;
      if (var2 < 0) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s1f5yzcsmdbk1m","ITMZzbU/19JjUk5D0M8KfNp+KUjjU1/x2TLAK5lliSw=",-7646995617020910012,1936176937534526457,7716539578640145046,6921372571725721359>()) {
            case 1847975806:
               var10001 = -1;
               switch ((int)com.yiyiaddon.m.b.a<"s2o76ohu7nzf18","3c+9I8rFcKfO9AtP/HsmEM0cQgD1xnZgQ4f+VFc75mU=",-2914667917304628293,3038444471128708794,-5598187613969935256,-8581608370292185759>()) {
                  case -1517639333:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var2 & 16777215;
         switch ((int)com.yiyiaddon.m.b.a<"srpi05yjn8pfj","qXxp+eyxX4OkShqR/iSfCWg0nTe9Od6+GJY2UdQvz5s=",7746951704598797686,-5584056147005214964,-4397970388671868613,-834811884957030334>()) {
            case 1254079488:
               break;
            default:
               throw null;
         }
      }

      this.tj = var10001;
      return this;
   }

   public n a(com.yiyiaddon.l.j.p var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3jg6lv1g46i0b","acTbnZVV4fCo5a661pOwXKvmZGvjaQdqtQb3Ur5BAKQ=",-9103760137138015555,7928015877498496931,465008564368510303,1529794481411594530>()) {
            case -2104963102:
               this.dc.add(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s1f0vg2nnz6nxw","1KwpfwzCQLo1/c10zLQ5/rP47EQepCtTpugNRrYb4zk=",890517163104338607,-2630196234786912486,-1206980933017521391,2024549785688319041>()) {
                  case -2076703630:
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

   public n a(Runnable var1) {
      this.j = var1;
      return this;
   }

   public n a(boolean var1) {
      this.ei = var1;
      return this;
   }

   @Override
   public float b() {
      return 24.0F;
   }

   @Override
   public void a(float var1) {
      Iterator var2 = this.dc.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2ln8pscnmam27","zmh4gRiwVH2OpLpOrg82mKQXDI52og5vN9kcp0cl4kw=",-6846488303728042815,9063765559407496247,-6926465803589019373,7305075138965005143>()) {
         case 1275651004:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s366xrkv1hrb41","66vsFuorTCpixSU2XQZzgCFVSabt5WHNd395KGMV40I=",4771896690411988404,-5414020320067855866,-857300429256057107,-651694513071534242>()) {
                  case 586949178:
                     com.yiyiaddon.l.j.p var3 = (com.yiyiaddon.l.j.p)var2.next();
                     var3.a(var1);
                     switch ((int)com.yiyiaddon.m.b.a<"s2tefws2mchxbj","MkxCl1TgHdFER6F4uliV5DzHbBNBeJW+8NeyXf+1wH4=",-3608494711833238065,5050524221170354723,-2788250878792702458,-5860093639461773339>()) {
                        case 1984918553:
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
               label32:
               switch ((int)com.yiyiaddon.m.b.a<"s13oqo5pcznpc3","PtyzxcnC6dSy9aDMW6DM6oayIAv6ONQiuadYSk77Au4=",-1812165707265302342,-830806326314681958,3689541287073269213,-3032415877821181179>()) {
                  case -571975504:
                     var10002 = 1.0F;
                     switch ((int)com.yiyiaddon.m.b.a<"smkhbjhgwqcpz","PpGmIWRWSN7F/PiHqwhl76wS/VEl0Ur7IMCXetki2Ds=",-7750102426474315844,5981998102454120985,-3820965792823477600,-7796672293083498396>()) {
                        case -2016462252:
                           break label32;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10002 = 0.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s1wf3hfhg1gzvm","OOeGHcsanM9RPmDDn4NaONvJBFKtD6tcGrAgc5lNdzo=",-924304111865794903,-5405539216060839071,-6157942731010620425,1281312094159868237>()) {
                  case -1288477644:
                     break;
                  default:
                     throw null;
               }
            }

            this.cD = var10001 + (var10002 - this.cD) * Math.min(1.0F, Math.max(0.0F, var1) * 14.0F);
            if (this.cD < 0.001F) {
               switch ((int)com.yiyiaddon.m.b.a<"s2r1yfoc1fmjdd","Tis89sMe74Ani5S5Ju6/rbJjzpWol85LIriAKKCHHTs=",-1467673900215745975,4122619921143797697,7311689650558552262,8967970026845159710>()) {
                  case 1426330559:
                     this.cD = 0.0F;
                     switch ((int)com.yiyiaddon.m.b.a<"s242m2mhpt3y8e","HF7/5hm8WjzlPh6rXWLuX4Zmlx35hkEwCO3f9O64mRI=",2696624933196676507,8078457250041764662,5165286542149916483,-414831624687802417>()) {
                        case -1234493686:
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

   @Override
   public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      com.yiyiaddon.l.i.c var8 = com.yiyiaddon.l.i.c.a();
      float var9 = com.yiyiaddon.l.b.j.h(24.0F);
      float var10 = com.yiyiaddon.l.i.c.y(var5);
      com.yiyiaddon.l.b.j.a(var1, var2, var3, var4, 24.0F, var9, var8.uQ, 0.7F, var10);
      com.yiyiaddon.l.b.j.c(var1, var2, var3, var4, 24.0F, var9, var8.uX, var5, 0.1F);
      this.eh = var6 >= var2 && var6 <= var2 + var4 && var7 >= var3 && var7 <= var3 + 24.0F;
      if (!this.fK) {
         this.fK = true;
         this.cD = this.eh ? 1.0F : 0.0F;
      }

      if (this.cD > 0.01F) {
         com.yiyiaddon.l.b.j.a(var1, var2, var3, var4, 24.0F, var9, var8.vc, var10 * this.cD);
      }

      if (this.ei) {
         com.yiyiaddon.l.b.j.a(var1, var2, var3, var4, 24.0F, var9, var8.uS, var10 * 0.18F);
         com.yiyiaddon.l.b.j.c(var1, var2, var3, var4, 24.0F, var9, var8.uS, var5, 0.35F);
      }

      float var11 = var3 + 12.0F;
      float var12 = var2 + 10.0F;
      if (this.a != null) {
         this.a.draw(var1, var12, var11 - 8.0F, 16.0F);
         var12 += 24.0F;
      }

      float var13 = this.e(var2, var4);
      float var14 = var13 - 6.0F;
      var1.save();
      var1.clipRect(Rect.makeXYWH(var12, var3 + 2.0F, Math.max(0.0F, var14 - var12), 20.0F));

      try {
         com.yiyiaddon.l.g.d.a(var1, this.k.get(), var12, d.c(var11, 11.0F), 11.0F, var8.uT, var5, true);
         if (this.l != null) {
            String var15 = this.l.get();
            if (var15 != null && !var15.isEmpty()) {
               float var16 = com.yiyiaddon.l.g.d.a(this.k.get(), 11.0F, true);
               com.yiyiaddon.l.g.d.a(var1, var15, var12 + var16 + 6.0F, d.c(var11, 10.0F), 10.0F, var8.va, var5);
            }
         }
      } finally {
         var1.restore();
      }

      this.a(var1, var11, var13, var5, var8);
      this.b(var1, var3, var11, var13, var5, var6, var7);
   }

   private void a(Canvas var1, float var2, float var3, float var4, com.yiyiaddon.l.i.c var5) {
      if (this.m == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sk83ey8tuulyo","FtEurO3m2DvVPlnhwqmRXAsWyT9kDqJJkiw7y4x3jO0=",-913770022151729439,-9195752947406625994,-2741652745943340264,4437351467691703099>()) {
            case -887629801:
               return;
            default:
               throw null;
         }
      } else {
         String var6 = this.m.get();
         if (var6 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s432c0tofw05e","1dp4sDB+kJNdwX63ELRApcOnp9RO9q35Mbb1EzH6vN8=",7029631587518347064,-798639584263231541,-5904493412125538378,-2886749395386943626>()) {
               case -227102190:
                  if (!var6.isEmpty()) {
                     float var7 = com.yiyiaddon.l.g.d.a(var6, 10.0F, false);
                     float var8 = var3 - 8.0F - var7;
                     float var10003 = d.c(var2, 10.0F);
                     int var10005;
                     if (this.tj >= 0) {
                        label27:
                        switch ((int)com.yiyiaddon.m.b.a<"s2cv62jsg23dk1","ovuR0veNKlSpDxsILE+fGzlS69UCM4hsP6JKcmGI3ls=",-5227612055995419957,2540197470227975880,-8583997915984014887,6464429755919162118>()) {
                           case -416138423:
                              var10005 = this.tj;
                              switch ((int)com.yiyiaddon.m.b.a<"s2e3hp8t2uupt2","B4KI1Y1Y/GzIoTUf56b5xoHqOchzemXCi5pFyyKBdco=",-4927346140045895690,-8077557805550750969,4232549298072286558,7231965906607181383>()) {
                                 case -928889275:
                                    break label27;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10005 = var5.uU;
                        switch ((int)com.yiyiaddon.m.b.a<"s636i9mwzynpt","UB8jqRpmefWJ3iMm7yk+66QVNG93uTpdVvx2/OPHYrY=",-8213059174812334674,7209164845075342458,-3039725162259307838,-2395980055887284483>()) {
                           case 514420773:
                              break;
                           default:
                              throw null;
                        }
                     }

                     com.yiyiaddon.l.g.d.a(var1, var6, var8, var10003, 10.0F, var10005, var4);
                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s825j3v7emj4y","zCQootSmjm9qKH/fHd5x4A/SsT00NY3ZgjtBDt842cI=",4254784332629430302,5326997478753880279,-8694734189264049792,4377654551179085665>()) {
                        case -1078018014:
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

   private void b(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      float var8 = var4;
      Iterator var9 = this.dc.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s399polkq6i0w","LMYxLWOa4VroA49xznTW2WaI4BpMbfbFzZyIDMbBV2M=",-387277239556057082,-3356653836329619684,-5508840806741493407,-5839601776399187182>()) {
         case 1802104797:
            while (var9.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3i7s7w3q3cbpw","SuBwIZuHsPgH+Ik/EY4W7dUEB4xSawfn4KqfiozkWZg=",-8893561742009423580,4129015705532039780,2906759439127823470,4971217503086442684>()) {
                  case -1133538161:
                     com.yiyiaddon.l.j.p var10 = (com.yiyiaddon.l.j.p)var9.next();
                     float var11 = var3 - var10.d() / 2.0F;
                     var10.a(var6, var7, var8, var11, var10.c());
                     var10.b(var1, var8, var11, var5);
                     var8 += var10.c() + 6.0F;
                     switch ((int)com.yiyiaddon.m.b.a<"s2gcbmx5kt0lxj","L0pDOMdIVisNJmsCKNOQ2SQ+u7vb4GJv5MzrvY9NIXI=",-3962901395919803995,8228024346569064030,7590321426584267763,-3102226283109380501>()) {
                        case -1472417640:
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
   public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
      if (var6 == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"sinsazcjn86tc","aViCJ8QqD7aPAifYaDWsAsr8RxLfyBq9nTe5YsDPkTU=",-5881661552702204402,-4870631788538390984,878198189929384310,-4605129651262861075>()) {
            case 305485606:
               if (!(var2 < var4)) {
                  switch ((int)com.yiyiaddon.m.b.a<"sem0iagjh4x2h","h6wTkXAScelKAxxb/SMadIyYuA19EGavdxusVUEivbk=",-2548801297148106726,-4214839949451827032,-7916164659988325688,-5036904610285377818>()) {
                     case -1458543405:
                        if (!(var2 > var4 + 24.0F)) {
                           float var7 = this.e(var3, var5);
                           float var8 = var4 + 12.0F;
                           Iterator var9 = this.dc.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"sh7dmcjjdsh0p","/UiAbSzUx2IcorvotBiMyIkhiQvkZsUW4ovpJ5gef80=",2825485668543673413,5343983312488244824,-1298124289990188376,7236484899451015241>()) {
                              case 561430301:
                                 while (var9.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1b72gbfguft5q","lEfYovtsKq/seEXnr7HpRxpE8yLh4ZL9CCR5Z1Pm/d4=",-7610076827250340033,1265620943854330668,-8635065778037752112,2787940212903479687>()) {
                                       case -95181483:
                                          com.yiyiaddon.l.j.p var10 = (com.yiyiaddon.l.j.p)var9.next();
                                          float var11 = var8 - var10.d() / 2.0F;
                                          if (var10.a(var1, var2, var7, var11, var6)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s289nu9cn7fp5s","LH/grzc0sT+dkDw1uJrXXp/7zAILAFikV4HfS+3zqkU=",5151587760065362970,5552469920526147034,-8124714208307886437,3039303154081665149>()) {
                                                case 1388315956:
                                                   return true;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          var7 += var10.c() + 6.0F;
                                          switch ((int)com.yiyiaddon.m.b.a<"sru8qaezkc6fs","SrtNnvDH7mTftKlGUFdoXaIVfDfQnuwInX3qnfSIm2s=",-6073372924304027181,-5645050234853425665,-1003741737582688343,6766592298933667030>()) {
                                             case -968121840:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 if (this.j != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s28dnmmg4gyfyu","+Hsfen38RpiVIiuhV6KPOHEXALZinAYW7lS0XjnOBrY=",-710183396459133700,7348832045756289528,1766859824150795344,-1186857685814095253>()) {
                                       case 1399804620:
                                          if (var1 >= var3) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1uih33okcpevd","xgW2+VUIODoIKIewxcPAeZ9vIhagyt9g8PJc+HvzPho=",-6801870064712915858,2714981556458117465,-3616575876821260435,-8759673963903809349>()) {
                                                case -1166146481:
                                                   if (var1 <= var3 + var5) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"sm2hl9uuiigh","dggYFNmsxBBre+aVfRwXJtESfa2XyBfW4Ldugdb9vyM=",8092287219211926033,6134785749126274121,2968964863298862833,-3450764461645699188>()) {
                                                         case -265928796:
                                                            this.j.run();
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

                                 return false;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3gsiqb99lutm7","9hXdEXY111vIh1vQjpj8Tf/4jpU5hDrvEAe+b/vs5/o=",-2675893765431868827,3540909593761377886,2429016676185618020,9137328837840947845>()) {
                           case -514684911:
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
      float var6 = this.e(var3, var5);
      float var7 = var4 + 12.0F;
      Iterator var8 = this.dc.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3r0zort40669a","G35SMDakTCt3S9uh/W3d7Kbk6Jc8K9CBQ3xerbm9Lfc=",3910751649055780575,5427077575156649968,-3384478274456075654,-6076148827891234278>()) {
         case 917396232:
            while (var8.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"syfl2l48c7x72","ajT7aRDwV+BsdLqkAfKjcamhoxqZir5XmPZYBdaus3U=",-4953069278845344774,7019238997450993625,-3183211513599996145,8016329147771717496>()) {
                  case -967612080:
                     com.yiyiaddon.l.j.p var9 = (com.yiyiaddon.l.j.p)var8.next();
                     float var10 = var7 - var9.d() / 2.0F;
                     if (var9.a(var1, var2, var6, var10)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1iyao4cc4eao5","yaNY9RKwporkZnE7j3Z3tLV0PLi777QN6U9HRyPR3zI=",3804600517047583133,-7558704955055535316,-8525667865380534837,1174266969577937274>()) {
                           case 161175621:
                              return true;
                           default:
                              throw null;
                        }
                     }

                     var6 += var9.c() + 6.0F;
                     switch ((int)com.yiyiaddon.m.b.a<"s11vyv3fgmdb5y","dqlXCAvwKO9W1rHPC5546sKnsMY7S4BNnQnlNtu2o1Q=",-4677978034035618266,787308200922276293,-7656987861580410041,4335167891207465550>()) {
                        case 1901235559:
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

   private float e(float var1, float var2) {
      if (this.dc.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s39j9n361z356n","wr+T0FLeqQvA4TAx70hFn0NeAeFxhVUyw/4s5alhU9s=",36199732550766141,-2485382010133658053,7721635429615191306,5909581860833575272>()) {
            case 336915346:
               return var1 + var2 - 10.0F;
            default:
               throw null;
         }
      } else {
         float var3 = 0.0F;
         Iterator var4 = this.dc.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s3hjfu1ozfdo0j","w3T4Cyi4MAZ8T6rAN8tQuB3DEdeABYH3tWMIJ7WlZl4=",3202558786513921479,-2218189092919315833,-8782279692315562729,-6094118070626462884>()) {
            case -1269832571:
               while (var4.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1wb26u0qp8vob","KIwzVWVYbkCDoTUaK5EFqph8X9PwCjIUFVRmJ9xVTXo=",2586312599565882838,4751641359289210090,7637641876515590016,2330540329598142924>()) {
                     case 398312647:
                        com.yiyiaddon.l.j.p var5 = (com.yiyiaddon.l.j.p)var4.next();
                        var3 += var5.c() + 6.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s1p45fut90dqoq","Qi6NWWypa5c4JK5DQHOiZlOkjJmPlUTaMWZHYznJ2Qk=",2548309327183258591,-1822120767700279410,7582455449460087492,4925806228579447820>()) {
                           case -283876688:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return var1 + var2 - 10.0F - (var3 - 6.0F);
            default:
               throw null;
         }
      }
   }

   @FunctionalInterface
   public interface a {
      boolean draw(Canvas var1, float var2, float var3, float var4);
   }
}
