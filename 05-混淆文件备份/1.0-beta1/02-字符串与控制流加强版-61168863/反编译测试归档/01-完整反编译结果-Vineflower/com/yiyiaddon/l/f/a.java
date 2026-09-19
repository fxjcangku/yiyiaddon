package com.yiyiaddon.l.f;

import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public abstract class a {
   public static final float iP = 8.0F;
   protected static final String Fr = com.yiyiaddon.l.a.w(
      (String)com.yiyiaddon.m.b.a<"s1dy1ipckfzexp","pWPqIHSPK7kHiodGxBlMQJXoVuunm4K7eimXmiaIAYniRnHH4zX9D4Ix",-9112744772951825379,-7364323294245194730,7707985733344986555,-8327825301473943064>(),
      (String)com.yiyiaddon.m.b.a<"s1eaz3xs8k5sfs","BVZwTBrm3BqO7xdVRvvI+03vF6cRAWrb2rcIphWTPwReJpHgJLrixLrHRVRhwD4iL6yeonStjXwaNUu5OH+lVg==",-5487600383152625869,-7607652702712537083,-3945487560813794976,-7269981254296064960>()
   );
   private static final float iQ = 20.0F;
   protected final List<com.yiyiaddon.l.j.h> do = new ArrayList<>();
   private final List<com.yiyiaddon.l.j.h> dp = new ArrayList<>();
   private final List<Float> dq = new ArrayList<>();
   private float iR = -1.0F;
   private String Fs = (String)com.yiyiaddon.m.b.a<"s3p5rme51ik9nu","RA5HqL/TVqMpRmXuGNnHqHWbBLTtFvnhB2berg==",3258081756269448173,6071162304078766474,8054120072631200138,-6842229427574416805>();

   public abstract String E();

   public abstract String F();

   protected float n() {
      return 8.0F;
   }

   public List<com.yiyiaddon.l.j.h> bJ() {
      this.jY();
      return this.do;
   }

   public void bf(String var1) {
      String var10000;
      if (var1 == null) {
         label25:
         switch ((int)com.yiyiaddon.m.b.a<"s20uxpa0at63ig","FGnhLJ+4aRSAYIXbMPPHiJ6MiHAl1DsclhD4oVVQyzo=",-5973245823501433354,-7617812088157108314,-7353603445210048157,-1148874490334550955>()) {
            case -67348180:
               var10000 = (String)com.yiyiaddon.m.b.a<"s3p5rme51ik9nu","RA5HqL/TVqMpRmXuGNnHqHWbBLTtFvnhB2berg==",3258081756269448173,6071162304078766474,8054120072631200138,-6842229427574416805>();
               switch ((int)com.yiyiaddon.m.b.a<"s2zcu3z6tpvpqs","Ksow2fjG2FfssniTDTOpIOSxRmqZH45f7c+X10x7clA=",2430521031757858307,-3143465301892671375,7299000660853474397,2425187854579516130>()) {
                  case 1244059781:
                     break label25;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var1.strip().toLowerCase(Locale.ROOT);
         switch ((int)com.yiyiaddon.m.b.a<"sdejvjtu13qew","2E5oYYjD3hkQ4zqyvfOjQ26VXu5wJXPsJ8CmoFETiuc=",-7944692299613829094,5015463526224402268,5276890206148789206,1561782323457512249>()) {
            case -800799552:
               break;
            default:
               throw null;
         }
      }

      String var2 = var10000;
      if (!this.Fs.equals(var2)) {
         switch ((int)com.yiyiaddon.m.b.a<"s31x44qcor440r","g4bcgGafqv+sB+4k3JaGKJXEb62zOeg8nmRVz/oBV7E=",-8822215837371024358,-7145500654363614936,-1301344883565864242,1644204949906918799>()) {
            case 220789590:
               this.Fs = var2;
               this.iR = -1.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s1erjic6zsbvo4","XYq6D9Fhaq9zGUb3Wdf0q0VqE1eBQOU6tGQGeVDk2Xg=",-404666076424507910,-574797459516714519,-4870658428648416792,7972815361593223970>()) {
                  case -980258670:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public float D() {
      this.jW();
      return 20.0F + this.iR;
   }

   public float E() {
      this.jW();
      return this.dp.size() * this.n();
   }

   public boolean ga() {
      this.jW();
      if (!this.dp.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"sdijeog6y4u96","qrwpO2oZbgQMv+OuQzCtwhct79eJycvcDN0TtnfbU4g=",-6941732270097382544,-8638998186833868084,-7639683408559442525,-4262989819078882547>()) {
            case 1467534093:
               switch ((int)com.yiyiaddon.m.b.a<"s3uvsk7a89w2qs","2k6m8vyFZukJneaY20pkTNRUzlsL52BCjvGcvlMFb20=",-4626457089735978473,-5585126026966376336,-5760704558827467740,2089940822808128515>()) {
                  case -352419207:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1jfn4itselh3j","wuEemCwUmTvdBO1PL+ILzk9dhiLNGAABBu/dHXxtv4s=",8945330958808767016,-330104202113275342,1276388977062491766,2990172056505942788>()) {
            case -1865977359:
               return false;
            default:
               throw null;
         }
      }
   }

   public void a(float var1) {
      this.jX();
      Iterator var2 = this.dp.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2g5rsjxr8uev7","LMnrKGVhzmyO1yt1Ei+RYoF49SlM8n6+rrRCTgHZkQI=",-3696947835921428041,-801916922480168884,2191192459509073602,1503349782040608709>()) {
         case 1182568040:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2nnybtsf802y2","0qWWc4vcynSF2ak1CotN8DEu3R4xCH4OxS3/yAvo2Rk=",-5515953981192002013,-2807019930899809416,-7581830036698817655,-8187895243005612838>()) {
                  case -439169372:
                     com.yiyiaddon.l.j.h var3 = (com.yiyiaddon.l.j.h)var2.next();
                     var3.a(var1);
                     switch ((int)com.yiyiaddon.m.b.a<"syhprxeimdelv","b/+CwSgamM9zkV1LEORL3WFtdl3Lk2MBdQ1WKnarPE8=",-6638214795547499187,-302713293893373761,7330160583244601060,-4255301472888845785>()) {
                        case -1764747873:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.jX();
            return;
         default:
            throw null;
      }
   }

   public boolean gb() {
      this.jW();
      Iterator var1 = this.dp.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3i668pnyhm4mr","+D1F9if0tQhNsLE6szHNVbw27nrsSeW7JZsTRH2xkAg=",4948445950505858291,2589510330814174374,4544435781744933188,6566593167676614135>()) {
         case 706545408:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2bzg94hwdjh60","ewq++677/Z6gXGQb6U/uFipqYbRmwxU0kJdXmc8yD1c=",-5811689604921724835,-8152239258377058688,-389581703065193209,7217956895813904444>()) {
                  case -1295695155:
                     com.yiyiaddon.l.j.h var2 = (com.yiyiaddon.l.j.h)var1.next();
                     if (var2.cB()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2srxqynfhh7xh","3smRfW95ykAeooJxF7PWNuaUG4Ig8yYQEzSKr+JvE6Q=",619764779497443944,1953638623159088219,7814453769544173758,-3118218424518302151>()) {
                           case 1803393228:
                              return true;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3sciu9avypcb2","ppspSu44dyYpA3aWYxVKhO0ncAoqdsk61xRV8Z0lySs=",-5870241542781747856,-3506302019579976257,3265229737072051901,-3540854811832636467>()) {
                        case -592719535:
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

   public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      this.jW();
      float var10 = var3 + 20.0F - var7;
      float var11 = var3;
      float var12 = var3 + var5;
      int var13 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s1l6e4vwsh3sw3","TOaTPR3157cT59Zwwh3ksFbbgfXWTTZurlxisLjIyuY=",-1904604191760661444,6990182742333143705,9161649552355159592,4042572105014990380>()) {
         case 1383218713:
            while (var13 < this.dp.size()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3ft9ei7mj5qo8","3AI30K50n0P6D9qcXD+8f7iF/ObvsH/DNIuwn7TRve4=",-5258806460350790982,-8393818944092323905,-6529301633181495203,-5396484671263520744>()) {
                  case -1282499873:
                     com.yiyiaddon.l.j.h var14 = this.dp.get(var13);
                     float var15 = this.dq.get(var13);
                     if (var10 + var15 > var11) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3u6ibqcy60w1","VVl26/M2MlVLLYE4lZrRQxxW+miERS5xO1XjXk4/CPs=",5241176503078731675,-5058268651767235612,1345054844069503790,-6839763145598157047>()) {
                           case -2004350604:
                              if (var10 < var12) {
                                 label28:
                                 switch ((int)com.yiyiaddon.m.b.a<"s3ao4ka01zlsi1","aKeQ8j1eyCcjj+iLTQxGlczo/1VFUhCd7eWujRB1htw=",-6439535577218159218,-8768604863785283680,-1899553759317235334,-1576813228927140247>()) {
                                    case -2130184351:
                                       var14.a(var1, var2, var10, var4, var6, var11, var12, var8, var9);
                                       switch ((int)com.yiyiaddon.m.b.a<"syesxkdfewgmp","mqJ2csDK14rCv8u+7GBdGYhsz615ocOSsczQqlVqbFg=",205918315509318912,1953717677529737626,-2948772025000046325,4341031414262315656>()) {
                                          case -1743524944:
                                             break label28;
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

                     var10 += var15 + this.n();
                     var13++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1n6vg5j5l7vx6","wPG+coWl/uYFhw6H+ZAyTETJbxjEJueeM9oftEs1Bns=",-7558364476241828313,-6172034282056113360,-1383442308782616918,-7400419943145736674>()) {
                        case -195442958:
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

   public boolean a(float var1, float var2, float var3, float var4, float var5, float var6, int var7) {
      this.jW();
      float var8 = var4 + 20.0F - var6;
      int var9 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s33z5qkvra8sjs","UyeZCfzXKXvQO9stMXo6LY7rjuJ7JLs7reTAwzMui0w=",7987955761068301978,5882146966312628630,-4079802207376292769,6130702343542899687>()) {
         case 4027680:
            while (var9 < this.dp.size()) {
               switch ((int)com.yiyiaddon.m.b.a<"swmhp991vwvtm","z3Ryd6XAl920JxI8kAK7NSciXSPbmoJZ63REg28bp2M=",-2166608009940907880,7647747745208049527,-1018483216810425266,-2028816604025494799>()) {
                  case 1398668002:
                     com.yiyiaddon.l.j.h var10 = this.dp.get(var9);
                     float var11 = this.dq.get(var9);
                     if (var2 >= var8) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2pc140peyopww","oIA/MnmJFqK0h02s95HIgC9BU+CTYjVlxHu7y1JrRGk=",-6372498145566066316,-4166722679141977757,7979683503701329439,4846662068293905887>()) {
                           case -581104881:
                              if (var2 <= var8 + var11) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1hn1m82mix3ng","Q0+aUrKkJB55Rjm9bQgq0vLFu50EAu0LQkuCMkdF6UY=",6398887560686381296,1042659713494143881,105187536683601841,-3741359185054025094>()) {
                                    case 773184785:
                                       return var10.a(var1, var2, var3, var8, var5, var7);
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           default:
                              throw null;
                        }
                     }

                     var8 += var11 + this.n();
                     var9++;
                     switch ((int)com.yiyiaddon.m.b.a<"s28sgbck1qdxar","KFEHzIKGqqUHRFb392XETrW9NONfOhS8DuSinm6Ly0U=",-7066254711175626425,498753419810224312,-7326924777572736899,-3255831108597636306>()) {
                        case 1594608400:
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

   public boolean a(float var1, float var2, float var3, float var4, float var5, float var6) {
      this.jW();
      float var7 = var4 + 20.0F - var6;
      int var8 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s38pzxxksm2dov","SPBG999+QkRrH6iuqa8yyYB1cZznZRmqsBLdkbzPo+k=",-6276891966447434390,-1579756778639726254,-8194780262716333476,-164792426209037601>()) {
         case -1334008926:
            while (var8 < this.dp.size()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1eubv4kj5c2ji","OT+G6T1tHNWSmlFKtJsqvdbSD+ssxE3uQuWgm9CXb2s=",-7027496438852426558,5678946752208326991,364889745077441015,-702325401966893427>()) {
                  case 623773103:
                     com.yiyiaddon.l.j.h var9 = this.dp.get(var8);
                     float var10 = this.dq.get(var8);
                     if (var9.a(var1, var2, var3, var7, var5)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2rfkcu2tpgjms","IyjBu5nLWI3IMg5SLdVrWC0Hg6IpbvSObQrFBo3RN+Q=",5323411220391548658,-5137030996847469916,-4263749681231098620,8012565447829791992>()) {
                           case -140574089:
                              return true;
                           default:
                              throw null;
                        }
                     }

                     var7 += var10 + this.n();
                     var8++;
                     switch ((int)com.yiyiaddon.m.b.a<"sj90bazazeufe","bQBBwlWqODp1T28ZZSC9YuezIiTmJ32KjQ+VBsBNQp4=",6087388029160355009,6292923776632274692,-8495395249432486160,5950119743555637712>()) {
                        case -420923211:
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

   public void F() {
      this.jW();
      Iterator var1 = this.dp.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1cnozqd9vs2f2","Lau3XC/bXEgkhD9K6+zodqxy6/mpcQ9zzooOidDfYk4=",-2230313090357805305,-5540290898462439807,-2123211180639221040,8469751043788545764>()) {
         case 1931526902:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1jta9gf9weh8","xUmS8oPr3JWrbwHwaIPao+9ppe0S0J5QgGKNJRSEscw=",6215990846130914444,7303999263813445069,1508073313335621620,-2980474271528021521>()) {
                  case -213058609:
                     com.yiyiaddon.l.j.h var2 = (com.yiyiaddon.l.j.h)var1.next();
                     var2.F();
                     switch ((int)com.yiyiaddon.m.b.a<"s32e0d7ih685vu","X0dR7b2g8Ox/q9k9h/PEqzVWZGx5yvrZeX928pvIvR4=",7073633969933312477,4131178584672091871,7919186428664257155,353048382757368857>()) {
                        case 1638998076:
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

   public void jU() {
   }

   public void jV() {
   }

   private void jW() {
      if (this.iR >= 0.0F) {
         switch ((int)com.yiyiaddon.m.b.a<"s3qsptc9eu0gi","c74xhhkjyJidATBk4LqNHrCT9UoXSvsdHiSe68hHx30=",-638670711265593231,7622927076868891666,3301640604439594665,2130287264104595843>()) {
            case 344406535:
               return;
            default:
               throw null;
         }
      } else {
         this.jX();
      }
   }

   private void jX() {
      this.jY();
      this.dp.clear();
      this.dq.clear();
      float var1 = 0.0F;
      Iterator var2 = this.do.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3jonnvjq26sxd","TPwxJorRb0vP1of2i3WQZ2BRlpTLXzax2EKRdoj2DVc=",-4576655043449900549,-6627022409081699548,3924740926849355413,-3788762302367633998>()) {
         case -827709569:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s17wqwcif8bg28","GNR6ngfZyAlyxMBQdH2CUKT9QT43d3Mp7yGS5dDhZw4=",-4885663010090495423,-1121307523430902380,-6604750108287672375,-2386431761332164899>()) {
                  case 1914732002:
                     com.yiyiaddon.l.j.h var3 = (com.yiyiaddon.l.j.h)var2.next();
                     if (var3.gG()) {
                        switch ((int)com.yiyiaddon.m.b.a<"sn39qv7pme86y","5x8tn8p1u0vyozpP9LJ9YCBuVQH3PkQ+/p6OZdy76t4=",-6232810807319926428,1727329641846757769,1395355852214913995,-3041285482176056002>()) {
                           case -1942708819:
                              if (!var3.aX(this.Fs)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3r5p741js63r7","ajrmMfCDrTab/9CuE3c149VzcPHL9nCstRP5N7rrTvU=",6104990662072401312,8507569785342203467,-8292291910904997092,-7815889933337718641>()) {
                                    case 661105816:
                                       switch ((int)com.yiyiaddon.m.b.a<"s3pi55c0bazd2m","7X8nQDYl53rz3ZQ4UT6SnrP3E+oyVmo14w3MvQ7IvFE=",-1247221547271806943,263018594318478865,-2627867311101239408,5444562491705414876>()) {
                                          case -183059724:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 float var4 = var3.D();
                                 this.dp.add(var3);
                                 this.dq.add(var4);
                                 var1 += var4;
                                 switch ((int)com.yiyiaddon.m.b.a<"sbus0hge9hjyu","0RQRywpFwG2VgwDTWm/WWGFdv89AWgzouD9//EuApSw=",3356163901432718031,-5552236875808624391,-2897623320145843062,-7481402987188368359>()) {
                                    case 1279096432:
                                       continue;
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

            this.iR = var1;
            return;
         default:
            throw null;
      }
   }

   private void jY() {
      String var1 = this.getClass().getName();
      int var2 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s2yvocanq3vy3u","iuYzTVF621pwh/7pZmwQcCr62TY/8AItLXrn36cy3FI=",-3869462544217331058,-5813102998852127251,3450275550238531333,4636845437434005>()) {
         case -385368587:
            while (var2 < this.do.size()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3hj6yx2gs1tut","Z4yfhqqKnPPdwMb7jRUcnWLgk9tL/RcNIuBlMUn5FXM=",8659217847815700703,-8568052592900243173,8576175969456985053,1977420046994011044>()) {
                  case -1114102825:
                     this.do.get(var2).bA(var1 + var2);
                     var2++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1podk7ajbd3f4","Yw7LUwFM/z3j4Rs9jzoGETMGuFUlzoT2j/H4v+C2QvM=",-2095431532469392025,-4302305547131639320,-6981903898784927918,-2077514065413978869>()) {
                        case -827560878:
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
