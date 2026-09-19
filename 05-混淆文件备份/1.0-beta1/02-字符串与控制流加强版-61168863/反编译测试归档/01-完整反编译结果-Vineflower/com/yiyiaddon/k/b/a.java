package com.yiyiaddon.k.b;

import com.yiyiaddon.j.b.b;
import com.yiyiaddon.j.b.c;
import com.yiyiaddon.j.b.d;
import com.yiyiaddon.j.b.e;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public final class a {
   private static volatile a e;
   private final d a = new d();
   private final c a = new c();
   private final com.yiyiaddon.j.b.a a = new com.yiyiaddon.j.b.a();
   private final e a = new e();
   private final b a = new b();
   private final List<Runnable> cV = new ArrayList<>();
   private boolean f;

   private a() {
   }

   public static a a() {
      a var0 = e;
      if (var0 == null) {
         synchronized (a.class) {
            var0 = e;
            if (var0 == null) {
               var0 = new a();
               e = var0;
            }
         }
      }

      return var0;
   }

   public void d() {
      if (this.f) {
         switch ((int)com.yiyiaddon.m.b.a<"s2zs4jp8xn7da4","/ugJnKkj1bDObqxzEzI57pSgIOeOT8PX6wyvyrIAwW8=",7656083629977167094,3939938725946668436,-125605892688801276,-2824243847612571209>()) {
            case 1277160219:
               return;
            default:
               throw null;
         }
      } else {
         this.f = true;
         this.a.C();
         this.a.C();
         this.a.C();
         this.a.C();
         this.a.C();
         this.a
            .A()
            .stream()
            .filter(
               var0 -> {
                  if (!com.yiyiaddon.i.b.c.E(var0.be())) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3678349oh2o1p","dYb5OXFo+T1I4ddnI2CjyFUIlVQ5aF7FFyMiufkxdgY=",-4818031973088972117,-7050583450990838904,1413868312284965150,-4104789500226055672>()) {
                        case -343327227:
                           switch ((int)com.yiyiaddon.m.b.a<"s2mxersuyj68p4","M0d5KIWu1qAxcuhqoe+3sHKHk0yZKI4tHr/YgNWOos4=",5453901330621352697,-7724748921957723883,-8465151407031992301,-3940866669856054825>()) {
                              case -975156261:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"soyphoci4kmzj","i3e4idSTzfG9L9tDrZu6911nyGs3/BbbpucGPxzX9mQ=",5911875368690417626,-7669735292673305749,2025667021563898032,-2092728574157492498>()) {
                        case -1276471628:
                           return false;
                        default:
                           throw null;
                     }
                  }
               }
            )
            .forEach(this.a::a);
         this.a.d(com.yiyiaddon.i.b.b::E);
         this.a.d(com.yiyiaddon.i.b.a::E);
         this.jq();
      }
   }

   public void C() {
      this.f = false;
      this.d();
   }

   public void k(Runnable var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3fdofdvribcrb","TIqC9WU0tqfZ8zn2Zn58CtNfjvX0F3HK46TcnldPrlQ=",-8352881165542150150,3129694341493740053,-2116340132912658080,1304902616754190932>()) {
            case -2027886240:
               this.cV.add(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s3t0fofpyaesey","/rOk+Wl2za6vFzRJ0zsVo1i6zM+pfl4PdoaX5Fjw95c=",-2228422779332681984,-5480946870278987129,5798874119012737363,-6520270030392587098>()) {
                  case 1392136274:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public Path m() {
      return this.a.k();
   }

   public Set<com.yiyiaddon.g.c.d> B() {
      return this.a.A();
   }

   public com.yiyiaddon.g.c.d c(String var1) {
      return this.a.b(var1);
   }

   public int dH() {
      return this.a.a();
   }

   public List<com.yiyiaddon.g.c.d> a(Set<String> var1) {
      ArrayList var2 = new ArrayList();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1w9kecoupz3of","nT2/pskNDJoZDgX6+72sm5DLg1rcFNo+h9m+rhjuof4=",5913821736272245371,3512332177352095147,3320891775096599396,8871064147778494463>()) {
            case -197950616:
               return var2;
            default:
               throw null;
         }
      } else {
         Iterator var3 = new LinkedHashSet(var1).iterator();
         switch ((int)com.yiyiaddon.m.b.a<"sc6uheohg0cxy","ewVdTyR4bLRpEEuh8K1jC3n75y7wCRygD2DrCn3GlK8=",-5365259028738695676,-7449007151598144730,2782877453737782378,-6763329171187186961>()) {
            case -1760991895:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3lcpogfbu5hac","JvVii7iQWwGAEerwuJsM0acZlF3x8KN+l2fLlvPBZTQ=",268935066075385858,-6506962427588531469,-2976070914887654531,5407386184367139297>()) {
                     case -604134406:
                        String var4 = (String)var3.next();
                        com.yiyiaddon.g.c.d var5 = this.a.b(var4);
                        if (var5 != null) {
                           label28:
                           switch ((int)com.yiyiaddon.m.b.a<"s1q545k2y5yy06","GMQy4l17lqmJDvPwMdbKpRtGX6C5Uv/V+7/SJQ7tRD8=",-3780219786430021559,35983652566689870,-4770104408296491981,5705117088481006242>()) {
                              case 1288233399:
                                 var2.add(var5);
                                 switch ((int)com.yiyiaddon.m.b.a<"s1t37jmf0vys64","8OvPr4eeCYNxHlJnl/0oYadZyFAy2QT9L+ujC5hR0sg=",825736292602762495,-6232035544650223379,-3233800684874621120,-7083002886055938510>()) {
                                    case 1058022726:
                                       break label28;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1nk8ux3djsngx","jDX/zsPKnDpxZ3VUQ5WCKou4MI8sjhutYqPGNQ0QnXo=",3108979286496834846,-265451551249946524,-3921446231265358579,-831755723284161331>()) {
                           case -2076981749:
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
   }

   public String f(com.yiyiaddon.g.c.d var1) {
      String var2 = this.a.d(var1);
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1i2w9bb7lzajw","Mb+hTRJBprb6Oq0kjW+Auyl9S08f2ODrNRSQtaaMufw=",3340968748701645183,-5676763692199289792,-2927582878469507595,3723313197324189292>()) {
            case 1847043156:
               this.jq();
               switch ((int)com.yiyiaddon.m.b.a<"s1jruj6c21chyu","RLP5nMM5Gk7xmDeMhWa5ui/A2hIEO4pxxKj5IVSKxVI=",-5208071750592511577,580202639167772275,-2694153208066280335,-4872743233876388692>()) {
                  case 439572310:
                     return var2;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return var2;
      }
   }

   public boolean b(com.yiyiaddon.g.c.d var1) {
      boolean var2 = this.a.a(var1);
      if (var2) {
         switch ((int)com.yiyiaddon.m.b.a<"s143y9qadu4uck","9I6vuo21vOwqaDASyqekpreSj9paGNKtC3Qx2xfYvZ0=",353849933368002685,4676888049585577595,-9141777407248959947,-882590998893849720>()) {
            case 1120527852:
               this.jq();
               switch ((int)com.yiyiaddon.m.b.a<"su0pwq38tehyo","f7FIARpr416TOqPh3ly+M8PnPXMUj+lfEVO6WMxW9SE=",-5349403369503732022,6918321876688041261,-2382604439290281939,-7065303272547927>()) {
                  case -1551051276:
                     return var2;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return var2;
      }
   }

   public boolean fC() {
      boolean var1 = this.a.fx();
      if (var1) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ra7yiuph04st","9jrrR5491V2BmBAwMmCIP49WR1gR6HeyLkPlzDITcfE=",5010327213744561433,3907873098536154067,-5802819352548843750,-2670659588801758229>()) {
            case -1710453588:
               this.jq();
               switch ((int)com.yiyiaddon.m.b.a<"s2mynafdqcipku","owwN4cg3lSJScWApEvlZS11c1FR9lX1U78A2XWM5kHQ=",2431871993896833912,-7380472611359248640,-3566826357410090115,-5899803943074675145>()) {
                  case 5364814:
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

   public Path n() {
      return this.a.k();
   }

   public Set<com.yiyiaddon.g.c.b> C() {
      return this.a.A();
   }

   public com.yiyiaddon.g.c.b b(String var1) {
      return this.a.a(var1);
   }

   public int dI() {
      return this.a.a();
   }

   public String e(com.yiyiaddon.g.c.b var1) {
      String var2 = this.a.c(var1);
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s6uswba4tkl89","sN66EwS2+V8XTIREgFQ/PgTFUiC+FUozY1jeFvtf1GY=",-1127478938972573907,6895988319725924370,1601646686999987966,-3238399178975118186>()) {
            case -205564298:
               this.jq();
               switch ((int)com.yiyiaddon.m.b.a<"se2mltli0isva","fAx4m0i5HzYnA/9pnsaIVQaL3BBxeQbN49osQsCo4+A=",-1901945596750783024,-3102336997411450316,-6130469004628715054,-4633229191311045699>()) {
                  case -1386110454:
                     return var2;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return var2;
      }
   }

   public boolean b(com.yiyiaddon.g.c.b var1) {
      boolean var2 = this.a.a(var1);
      if (var2) {
         switch ((int)com.yiyiaddon.m.b.a<"s6a9j229h6ta","R5R97CUF0dmqxc3ddCmEIM6VAN09/QSW64Kwym6jxM8=",-2475705050837754824,-2674799479835893149,-6214388422405386841,-4400176162442273267>()) {
            case 684764590:
               this.jq();
               switch ((int)com.yiyiaddon.m.b.a<"s10cl9fd3zw8qs","JCjqBhcbRKzeW3djwHFzpx/5U+ocyAspsQpsaTskDr8=",2828271690205961206,8248192535580526247,8909699892542938715,-5837494153138544208>()) {
                  case -647934395:
                     return var2;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return var2;
      }
   }

   public boolean fD() {
      boolean var1 = this.a.fx();
      if (var1) {
         switch ((int)com.yiyiaddon.m.b.a<"s1uyhnc4tqzqus","SNmSQSg8Owp/DscZq3u5fTc521hKlwgEIPg2MNhMgfY=",-2568102759440020702,8469800177840058700,9047814890025434653,1619471452593404947>()) {
            case -378492712:
               this.jq();
               switch ((int)com.yiyiaddon.m.b.a<"s31iaugf02kpgi","s41FKVeCgtP1cVXi0aYxcKK1U0Xc6O81JMqyv1brxOQ=",2224029754961126246,-7882633312785308545,-8205097523999107182,-5522220616259837759>()) {
                  case 485467626:
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

   public Path o() {
      return this.a.k();
   }

   public Set<com.yiyiaddon.g.c.a> D() {
      return this.a.A();
   }

   public com.yiyiaddon.g.c.a b(String var1) {
      return this.a.a(var1);
   }

   public int dJ() {
      return this.a.a();
   }

   public String e(com.yiyiaddon.g.c.a var1) {
      String var2 = this.a.a(var1);
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"scr8t5bclgitc","XCgrL3nOP/EU5rnntg/aHhC8rT1tel75WQS6nTw3z7w=",8130480013445707425,-1319680397657681541,-2969723475994783195,1410042208012427092>()) {
            case -1969739034:
               this.jq();
               switch ((int)com.yiyiaddon.m.b.a<"s32qiibwgqdpu6","gjPGsTpfipTv7vIu0PZjP+hywHKZnKLFUxX4ItYpRrs=",-5130345441301556427,-850511546403903993,4077192222043997816,-1952009759375809465>()) {
                  case 1059801960:
                     return var2;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return var2;
      }
   }

   public boolean d(com.yiyiaddon.g.c.a var1) {
      boolean var2 = this.a.b(var1);
      if (var2) {
         switch ((int)com.yiyiaddon.m.b.a<"so1mjxpb6msoh","q/nsZnGleCQpd2IKHNyPROoFvzHbKnN4z2oh8Z37fvY=",-3437821175179432704,-5898437582889550546,5410340774309858492,3579208267823133701>()) {
            case 67574346:
               this.jq();
               switch ((int)com.yiyiaddon.m.b.a<"s2c3c59s6997as","ZrYkoPXIIiBGSVRc7roRrFuR1NJphnOC8N04k0hVCSI=",856031168650951240,-3208851467168930310,-667229952022544106,-3612132180027329995>()) {
                  case 1544120720:
                     return var2;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return var2;
      }
   }

   public boolean fE() {
      boolean var1 = this.a.fx();
      if (var1) {
         switch ((int)com.yiyiaddon.m.b.a<"s527eywmk6kh6","JET7vesyGwK8OG9JZuhKLcS6XlC0BGUINRrHWKZKzFg=",-4461476479942393355,2685379199412173826,3277452431841018985,-5637697438635497256>()) {
            case -2032655333:
               this.jq();
               switch ((int)com.yiyiaddon.m.b.a<"s1jvd3ivv4fbbw","XkzSvpWsDi5tAr15Wv+MQ89XCLGXq+ck9IZc71zADY8=",4797255673709409981,6167507220415396571,7837564786000552656,6907950903770549076>()) {
                  case 1342728024:
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

   public Path p() {
      return this.a.k();
   }

   public Path q() {
      return this.a.k();
   }

   public String b(com.yiyiaddon.g.c.d var1, boolean var2) {
      return this.a.a(var1, var2);
   }

   public String b(com.yiyiaddon.g.c.a var1, boolean var2) {
      return this.a.a(var1, var2);
   }

   public int dK() {
      return this.a.a();
   }

   public int dL() {
      return this.a.a();
   }

   public boolean fF() {
      return this.a.fx();
   }

   public boolean fG() {
      return this.a.fx();
   }

   private void jq() {
      for (Runnable var2 : this.cV) {
         try {
            var2.run();
         } catch (Exception var4) {
         }
      }
   }
}
