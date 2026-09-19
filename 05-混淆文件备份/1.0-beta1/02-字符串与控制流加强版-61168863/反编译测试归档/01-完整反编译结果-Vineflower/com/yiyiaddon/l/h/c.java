package com.yiyiaddon.l.h;

import com.yiyiaddon.l.b.w;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.gui.screens.Screen;

public final class c extends f {
   private final List<String> dz;
   private final String Gp;
   private final Runnable l;
   private final boolean gy;
   private final Supplier<Screen> z;
   private Screen f;

   public c(String var1, List<String> var2, String var3, Runnable var4, Screen var5) {
      this(var1, var2, var3, var4, var5, false, null, true);
   }

   public static c a(String var0, List<String> var1, String var2, Runnable var3, Screen var4) {
      return new c(var0, var1, var2, var3, var4, false, null, false);
   }

   public static c a(String var0, List<String> var1, Screen var2) {
      return new c(
         var0,
         var1,
         (String)com.yiyiaddon.m.b.a<"s1af5w8lixk6ez","Hhp8KH7yvRhnJk25taGr1KQ8KfAbya5tj+u5NylPIzveuAT9xNDv8ljf",3238943301394898858,-3875123009960214788,8944866927068802029,-4891013059310916873>(),
         null,
         var2,
         true,
         null,
         false
      );
   }

   private c(String var1, List<String> var2, String var3, Runnable var4, Screen var5, boolean var6, Supplier<Screen> var7, boolean var8) {
      super(var1, var5);
      if (var8) {
         this.kQ();
      }

      this.dz = var2 == null ? List.of() : List.copyOf(var2);
      this.Gp = var3 == null
         ? (String)com.yiyiaddon.m.b.a<"s2fsn22nu8w5tg","bxEegoBgPctjmNVinK4GKfBDOKhDRb8e+ND51NFVS2A=",2914610550844736095,3414894116566959134,-8242538550798962467,8521606414822008367>()
         : var3;
      this.l = var4;
      this.gy = var6;
      this.z = var7;
      this.dm();
   }

   public static c b(String var0, List<String> var1, Screen var2) {
      return new c(
         var0,
         var1,
         (String)com.yiyiaddon.m.b.a<"s1af5w8lixk6ez","Hhp8KH7yvRhnJk25taGr1KQ8KfAbya5tj+u5NylPIzveuAT9xNDv8ljf",3238943301394898858,-3875123009960214788,8944866927068802029,-4891013059310916873>(),
         null,
         var2,
         true,
         null,
         true
      );
   }

   public static c a(String var0, String var1, List<String> var2, Screen var3) {
      return a(var0, var1, var2, var3, null);
   }

   public static c a(String var0, String var1, List<String> var2, Screen var3, Supplier<Screen> var4) {
      ArrayList var5 = new ArrayList();
      var5.add(var1);
      var5.add(
         (String)com.yiyiaddon.m.b.a<"s3dq7sqpwepkcm","W685k6VSx84ZezoXuYyCYZSKxkqw+G7d+WwNzw==",5248440595204762732,3838924874011402392,6245128232869854341,5079880516681091834>()
      );
      if (var2 != null) {
         label44:
         switch ((int)com.yiyiaddon.m.b.a<"s2yk0q8olbwmcs","6lEsb33voZn3lyGYv7CQDrzcSA1yoYcj9gRVumJEdMk=",6464309684472697090,-4795777743040363184,-3281721070968842815,-5243629497289517866>()) {
            case -1604886627:
               Iterator var6 = var2.iterator();
               switch ((int)com.yiyiaddon.m.b.a<"su9x5slpqfvqx","HZjftwPAdb2lxKfZQJe9bzDhxJnYMVG6ewrlGuyFLUQ=",8657301306835497497,-2809634884380080133,2802319730369625106,-2459511115018448477>()) {
                  case -709731325:
                     while (var6.hasNext()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3f1sjf99r2rqr","C6q6dyhEJCsXkCZWWiEPbMUcfqNBym6PNbHFyMvUZBM=",1960678800586686984,8672349768367827717,4385756536447204375,-3253367959758293733>()) {
                           case -1219450296:
                              String var7 = (String)var6.next();
                              if (var7 != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"st6wh887knkvf","nkwKbJuPYfSOHS32oy8JvrQNHqPx9c+DBWSIUfjrUIU=",7882384681262485774,2811700319176181925,-2584002377936867694,2581157229860220309>()) {
                                    case 1856310247:
                                       if (var7.isBlank()) {
                                          switch ((int)com.yiyiaddon.m.b.a<"sknmdfoow45w7","wmLQU5Z0mAWY/Vewe0ZrcHFEgH4xnDin8ma5iwiPRHk=",3554932931384547512,1618952955142484155,-7613302799809653214,-3206091742059071374>()) {
                                             case -970516176:
                                                switch ((int)com.yiyiaddon.m.b.a<"s5po3hgkcnv1r","fszjcIKJwQMmKbRoRMvcEyQbxWoNtXccR+14S+pdnO0=",-3313055918901751909,2139130151869528954,-4994360677168479503,5497519881784737136>()) {
                                                   case 2032076846:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          String var8 = var7.strip();
                                          int var9 = var8.indexOf(
                                             (String)com.yiyiaddon.m.b.a<"s3voe4a43gs1nh","yLkwjwleqZG7Ye9EzZQPX/QIK6pSQYA3BOxr3O2RG5zxcA==",8822067643823986846,7959883582446664345,7267857134860003917,3951477625228320600>()
                                          );
                                          if (var9 < 0) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3n3oh3krnfdbt","6JaOeAtYIqCNc01+UaqcJOw76kY85Jl+tdZUFK4NWVU=",9037248045249609879,-7258910717072201182,3587841502432305934,6877627155350640048>()) {
                                                case 767090902:
                                                   var5.add(co(var8) + "");
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2vmbhpjgnjua1","xl6xIw1OwHeT7m3Jzgcg0myOAxCJM5ZqAL5B7bUQ4do=",6763228136597229494,7759296020171714311,-8047816856521900623,6789685231937037369>()) {
                                                      case 520060215:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             var5.add(co(var8.substring(0, var9)) + "");
                                             var5.add(co(var8.substring(var9 + 3)) + "");
                                             switch ((int)com.yiyiaddon.m.b.a<"s3naezql9id6z4","wHn4p6Ps9mDROccNvvBqvrAQ1nUHhQqJRJknX6C5eMQ=",-2807708328809315029,106603687069585558,1745100213624675024,7321162073430399856>()) {
                                                case 1153488057:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
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
                     break label44;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      return new c(
         var0,
         var5,
         (String)com.yiyiaddon.m.b.a<"s1af5w8lixk6ez","Hhp8KH7yvRhnJk25taGr1KQ8KfAbya5tj+u5NylPIzveuAT9xNDv8ljf",3238943301394898858,-3875123009960214788,8944866927068802029,-4891013059310916873>(),
         null,
         var3,
         true,
         var4,
         true
      );
   }

   private static String co(String var0) {
      StringBuilder var1 = new StringBuilder(var0.length());
      int var2 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s22r7vo326zeoi","1bicqlP8R53GtLc+TXZkgoVEqmd2lttpsngBQ8a7Gm4=",731081089522852779,3578988423266164316,-2322923345176333110,-7314389917864121251>()) {
         case -833354882:
            while (var2 < var0.length()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3pnfhu0i5s6vv","Mhfj/aDdjy5N/d4WQe9b7D3E0zoiDSj2Hs61DGfgbEw=",-4580849456018930461,3587030532135914027,2670248145811833859,1243770132297293946>()) {
                  case 427261810:
                     label45: {
                        char var3 = var0.charAt(var2);
                        if (var3 == 167) {
                           switch ((int)com.yiyiaddon.m.b.a<"spi5sdgtkarvc","HzQoU32JTXlvcHcgArTZjP7DdCo2qaNJD1vRXoIvM1k=",-4427410928169424146,7310985615104154746,-4709119537503305257,-2453754769204040743>()) {
                              case -913373270:
                                 if (var2 + 1 < var0.length()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3cruxia0n5t2y","1V3gQM5/95ro/5d6HLJ60shvEsN1qMd836FEtSvpQXo=",7929308140523357995,7247544274827030733,2653374572322308481,-5334254011695780190>()) {
                                       case -1963513099:
                                          var2++;
                                          switch ((int)com.yiyiaddon.m.b.a<"s15b79874vobkk","fx5bBDnhbirCldyxIV+eNM53Hdv+AP9o2CoPAY2rokQ=",68750244613094176,371120085270835286,-7261000721406487054,8559360063145878939>()) {
                                             case -1029838221:
                                                break label45;
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

                        var1.append(var3);
                        switch ((int)com.yiyiaddon.m.b.a<"s2oa4b4qigwv27","Ba6bdHHo1hW5qgsLhy6Buon7QScCd+sX0LboHc6Be78=",1204804798612795061,2124493991458577697,2495374430067881426,-2248525705510044958>()) {
                           case 1523341598:
                              break;
                           default:
                              throw null;
                        }
                     }

                     var2++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1ju6xvpri5k5c","RbnNZuacwmJ+Y+WydsVTIxdhIFGkZeguYfVBd6j1RZI=",4077069067982659222,-5018641159133043460,1127558628868285687,655208457842174866>()) {
                        case 672572580:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var1.toString();
         default:
            throw null;
      }
   }

   private void dm() {
      Iterator var1 = this.dz.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1ls3pl8twtgg","dMP3hX6enXhqtqrLsgQHPy8B/NfroFCtR4V9dq3k0To=",-8188364725250072080,-4913994145460135863,9113616079678668074,3061963355563873136>()) {
         case -1039935065:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3matvf6nnjqng","rG5RXVWcVI/n6YCodttKs2ktzg7hXNskXrDikAUNYUs=",3700245610957735497,-7623595951034684960,6877802807887220696,3528532020161272586>()) {
                  case -953775617:
                     String var2 = (String)var1.next();
                     this.d().a(new w(var2));
                     switch ((int)com.yiyiaddon.m.b.a<"s1kqjfppzq8cy3","7mYp2R/ymGqbTnabmewN6wOX2TrTXbxV/MizuqJc8LQ=",1741867847083916805,9044136852644611860,-3795279326598477155,-1729280056096747812>()) {
                        case 1737832512:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.kN();
            if (this.gy) {
               switch ((int)com.yiyiaddon.m.b.a<"s1weua1fix48zw","D3nzig38Ot4bx0UuYH++1vAAnLtXpmHWQwiASq4gR+8=",349631971609278342,5400705520186091468,9153441503232727918,2896131026320537449>()) {
                  case 773870977:
                     if (this.z == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s27djzhmqhjgpq","Z5i8rq37v83ncpqLOdhMQoVlDORN0RYmnqniEOQn29g=",-1619939199210032051,-2698330997464047696,-4709308898000024274,1143801100266354321>()) {
                           case 1416028900:
                              this.a(new com.yiyiaddon.l.j.a(this.Gp, this::kR));
                              return;
                           default:
                              throw null;
                        }
                     }

                     this.a(
                        new com.yiyiaddon.l.j.a(
                           (String)com.yiyiaddon.m.b.a<"s39rdgzg6d145x","RReAP3Fq31p9/qwQ8jJCa2Y343z4FA+O3mmD3Hb1zZul6Eo5cAVaC/NFI4A=",2607791865056817989,-7374881013407985333,-6154038798616534623,-2342976337516049597>(),
                           this::kK
                        ),
                        new com.yiyiaddon.l.j.a(this.Gp, this::kR)
                     );
                     return;
                  default:
                     throw null;
               }
            }

            this.kO();
            this.kN();
            this.a(
               new com.yiyiaddon.l.j.a(this.Gp, this::kL).c(),
               new com.yiyiaddon.l.j.a(
                  (String)com.yiyiaddon.m.b.a<"s3pyrk42vgdrrp","wFlkq8QlrjIXVdfMTSmylAHNzELY68z5MfSMSgNSTaogU/Sc",3679068909056804890,-4781083633849071841,-4444888922410112486,-643731431126393185>(),
                  this::kR
               )
            );
            return;
         default:
            throw null;
      }
   }

   private void kK() {
      this.f = this.z.get();
      this.kR();
   }

   @Override
   protected void ku() {
      if (this.f != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1wjuqjmu94qzb","+IYNQ0mqLIT3B7jZnHiRBvTFwub9WgUvCA1BoPewSgo=",4347097315698652384,-160428059072427298,-4320499027025256566,7676623171309078596>()) {
            case 63250773:
               if (this.minecraft != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3qu0tguw4zza0","RZ0C1OlMpV55ANShSm3PL+i77odj7EGNUbdd7pad1lo=",6928619285339110631,-8073438302846664576,-2467024173607767529,-531196433014667093>()) {
                     case -1347019565:
                        this.minecraft.setScreen(this.f);
                        return;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      super.ku();
   }

   private void kL() {
      Runnable var1 = this.l;
      this.kR();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sj9xukfh2rwe1","KHqF9jwdVPjFjEdLcG9TjXHsFZVyaTSLi0JKM8t+kzM=",9068571499624046155,2847895885105040349,-4257052661705805867,6135307162970490933>()) {
            case -503210113:
               var1.run();
               switch ((int)com.yiyiaddon.m.b.a<"s2q1gra41tpbqb","bA64aeZbg9K9PR2pc+hNwtEK3yvEi7XepY0oEjl4QEc=",-2738224178675883658,-1621693699762651293,-1214541922232214201,-8448057824111761023>()) {
                  case -1799079215:
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
