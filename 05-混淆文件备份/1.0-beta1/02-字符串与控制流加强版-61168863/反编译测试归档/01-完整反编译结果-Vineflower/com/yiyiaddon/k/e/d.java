package com.yiyiaddon.k.e;

import com.yiyiaddon.g.d.f;
import com.yiyiaddon.g.d.g;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class d implements b {
   private static final Logger u = LoggerFactory.getLogger(
      (String)com.yiyiaddon.m.b.a<"s311nqzg9chal","qM3s8p1lqyAxuN1NrLzHQO04ggqzVhD/3YRNH8YsYwKkcLwImuQ6TOAaMvJbjZSq4lgrBAJ+jjCfMuK39p7KPpaQ4VkyDulyaC5v/w==",2640610630387560718,-6966488195487311590,-2564522508708233179,5760240940695731184>()
   );
   private static final long bf = 1000L;
   private final a a = new a();
   private volatile com.yiyiaddon.g.d.b a;
   private volatile String EJ;
   private volatile f b = f.b();
   private volatile Set<String> aQ = Set.of();
   private volatile long bg;

   @Override
   public Set<String> H() {
      long var1 = System.currentTimeMillis();
      Set var3 = this.aQ;
      if (var1 - this.bg < 1000L) {
         switch ((int)com.yiyiaddon.m.b.a<"sryyq9bs3oper","DUb7Nr2jxhGJOdu94wXZjhmujXOZmLQhyqcE2hmeXv4=",-5230064403567810875,2513050150679359110,-1494384412222184383,5430585591029963275>()) {
            case 1159040942:
               return var3;
            default:
               throw null;
         }
      } else {
         Set var4 = this.a(com.yiyiaddon.i.e.b.a());
         this.aQ = var4;
         this.bg = var1;
         return var4;
      }
   }

   @Override
   public f c() {
      return this.a().a();
   }

   public com.yiyiaddon.g.d.b a() {
      long var1 = System.currentTimeMillis();
      com.yiyiaddon.i.e.b.b var3 = com.yiyiaddon.i.e.b.a();
      if (!var3.b()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1gv60bstau4lr","n4y1HuPKJGuc+QmGb6O2Pg/oHVurdsyYs18ZR5bryWc=",-3094307473028603781,7094492368030020717,-6246958945974620069,-4111013948679878564>()) {
            case 1535051407:
               com.yiyiaddon.g.d.b var17 = com.yiyiaddon.g.d.b.a(null, var3.bz(), List.of(), var1);
               this.a(var17, f.b());
               u.warn(
                  (String)com.yiyiaddon.m.b.a<"spi54amc354ag","IO+imACte/dZRxxrqC4gLraj2Hh5RWfnOCsTHoIz1XHUrIFAd7JXLkl756xKWA==",-7108525220961965416,-4293839748197232810,8417077619140050436,1057440245800439735>(),
                  var3.bz()
               );
               return var17;
            default:
               throw null;
         }
      } else {
         Set var4 = this.a.a(var3.z());
         Set var5 = this.a.b(var3.z());
         this.aQ = this.a(var3);
         this.bg = var1;
         List var6 = this.n(var3.D());
         com.yiyiaddon.g.d.d var7 = com.yiyiaddon.g.d.d.SUCCESS;
         if (var6.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s144uxcxbkbucn","E+o4IvIAbD+sW4OjP+FOXC6OCGL3LI+bJhiQ93GvmyY=",4305236052580181586,-7836125693961161040,-7884052125127114968,-8656806530882872872>()) {
               case 297960821:
                  if (!var4.isEmpty()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s25vv6gwo4lppr","kff+EIo55/+0wQ4kGzWBvOnu774X59EhCcwb+O9/jT8=",-4421707696243551528,1963673094935007967,8640786259018855575,-4483230575111455736>()) {
                        case 2118862327:
                           String var18 = String.join(
                                 (String)com.yiyiaddon.m.b.a<"s22d5sk9dqzxb4","WDei3LvTv7CHfDyEVaYtI63UwfeJBwCGHLv3FckZ",6140012405446793842,7712323750891344895,-5455460197789396914,2890284866345607814>(),
                                 var4
                              )
                              + "";
                           com.yiyiaddon.g.d.b var19 = com.yiyiaddon.g.d.b.a(null, var18, new ArrayList<>(var5), var1);
                           this.a(var19, f.b());
                           u.warn(
                              (String)com.yiyiaddon.m.b.a<"spi54amc354ag","IO+imACte/dZRxxrqC4gLraj2Hh5RWfnOCsTHoIz1XHUrIFAd7JXLkl756xKWA==",-7108525220961965416,-4293839748197232810,8417077619140050436,1057440245800439735>(),
                              var18
                           );
                           return var19;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         com.yiyiaddon.i.e.c.a var8 = com.yiyiaddon.i.e.c.a(var6);
         String var9 = var8.bi();
         com.yiyiaddon.j.c.a.a var10 = com.yiyiaddon.j.c.a.a(var9);
         Map var11;
         int var12;
         boolean var13;
         if (var10 != null) {
            label69:
            switch ((int)com.yiyiaddon.m.b.a<"s1mg5a1063a6m0","Nt2IBKKOPlNE33j1WfwGi8qBfHfJD4gH8eyCNMvNhZQ=",4869979495939890711,548436975941953725,497813755040820472,-498761911130911239>()) {
               case -267038899:
                  var11 = var10.x();
                  var12 = var10.dF();
                  var13 = true;
                  switch ((int)com.yiyiaddon.m.b.a<"s37ndgho04cs4i","N8UslZ5BXApN6yLMfS8X/hzVjANlEf01eEODeKSZFkU=",-5609698211803552839,-3664782651867739748,-3568509136869562581,-6576746218828682610>()) {
                     case 130770167:
                        break label69;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var11 = this.b(var6);
            var12 = var11.getOrDefault(com.yiyiaddon.g.d.c.OTHER.h(), 0);
            var13 = false;
            switch ((int)com.yiyiaddon.m.b.a<"s3lx428uzojifx","RLtUxJImMk/BS+uUAALWkpoola90WjYSYm0+ydSi81Q=",-8517649966279635160,5675214076350709177,6964714237097844232,-5589194767364159283>()) {
               case -966789880:
                  break;
               default:
                  throw null;
            }
         }

         int var14 = 0;
         Iterator var15 = var11.values().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s2hcw1v7hh5su7","1Q3u7M71icLi5W5ewBGmyB4MhM6WA/So//7tBAu9v7g=",7967375057660988177,1029305141455684916,-2050277059747720092,1202076386751495780>()) {
            case -1301502818:
               while (var15.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sleacei0ocqb","dAuoSNfqdGMUnasAtkmGPcJBCbFQKeKccW24BCHgWXg=",-7447566781188169027,903575471460165054,-2584838553774415999,-4090230322584502892>()) {
                     case 210354003:
                        int var16 = (Integer)var15.next();
                        var14 += var16;
                        switch ((int)com.yiyiaddon.m.b.a<"s3nxot12l1njym","uw7AsypVKGpMksmOxDRLPhNAxCeMzFaKdHtuhsbPbzk=",1715111331111634146,322453568451833459,6512231097609612169,4084406562940273124>()) {
                           case 409383863:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (var14 == 0) {
                  label55:
                  switch ((int)com.yiyiaddon.m.b.a<"s1eaaxianff8l1","sIT+hNZ/jokB1MnhsD2FoCU7rqTybLh01FqUwJyG/zo=",-3742316350845090111,-3568784050052663834,2321695665731453579,7368526781699847718>()) {
                     case -1485039158:
                        var7 = com.yiyiaddon.g.d.d.EMPTY;
                        switch ((int)com.yiyiaddon.m.b.a<"s1dbna1zvi4jjr","gw109H7uyiwS9I0yc+6u5RGIYmjAoIMMooqezyEg+JM=",-226803053937814037,7084558640670959162,-6793988495781943241,3774216810484001749>()) {
                           case 890309032:
                              break label55;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               com.yiyiaddon.g.d.b var20 = new com.yiyiaddon.g.d.b(
                  g.LOADED,
                  var9,
                  var14,
                  var11,
                  var12,
                  var8.fv(),
                  new ArrayList<>(var4),
                  new ArrayList<>(var5),
                  var7,
                  (String)com.yiyiaddon.m.b.a<"s369k5pysd5f49","FaYfh0Mzs0fDGqtRw06FSOi5t9TTV2HSWt/0zA==",4495529244627443610,-1927322802168301719,-7914802390019403574,-2706698184256852604>(),
                  var13,
                  var1
               );
               if (!var13) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1qt5xg7mnu2uw","BbQp4U+nIeb/7RdtlO7MoCCv1uV34HyErDA6hQaGhow=",-4407240003274830550,-8874005896800163079,-9020360384072347655,-2395358074475643768>()) {
                     case -1434592301:
                        if (var7 == com.yiyiaddon.g.d.d.SUCCESS) {
                           label48:
                           switch ((int)com.yiyiaddon.m.b.a<"s3q07ufqqayrks","gya4gQwjDxbkuNhUA9K0d1c8Cii1PDnQrOgRWR13z+k=",-4363369609429126028,1815574044368597300,-7694989709172291231,-4056730363110833816>()) {
                              case -639460829:
                                 com.yiyiaddon.j.c.a.a(var20);
                                 switch ((int)com.yiyiaddon.m.b.a<"s3v752runmauhm","sA3f2odEXegvL9Rrw3Bt3uttnqTWN1vniD4ogK2V9nA=",2399261834501431580,5198398075234873195,-4009396995281725465,6056636842410796412>()) {
                                    case -430937452:
                                       break label48;
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

               this.a(var20, var20.a());
               this.a(var20);
               return var20;
            default:
               throw null;
         }
      }
   }

   @Override
   public String gr() {
      return this.EJ;
   }

   @Override
   public List<String> e() {
      ArrayList var1 = new ArrayList();
      com.yiyiaddon.g.d.c[] var2 = com.yiyiaddon.g.d.c.values();
      int var3 = var2.length;
      int var4 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s2hkj45x7w6bgo","tWpe3h9XWAKzYV2LcOe5Arjt7jxMw1wbeZrZKND3jBg=",2545937522354771573,2857115806924867365,5877673359305712720,4753888372827549351>()) {
         case -831020374:
            while (var4 < var3) {
               switch ((int)com.yiyiaddon.m.b.a<"svq8d6roobnnh","cVGIwHvpviQpMD/QX3/AQSPaxl0tTMvEk6Kk9nHR2PU=",6858399398817499682,-1085599924897349193,-5735840746773472787,1909228609016595241>()) {
                  case -285000360:
                     com.yiyiaddon.g.d.c var5 = var2[var4];
                     var1.add(var5.h());
                     var4++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1cv1ss09xw9ue","5qE0t3T6Xv7X6zkAot9IPfR41qZ+Rtv/29QfPI6AgH4=",-5954243692546120472,6161832448954168247,-6067564582142938882,3935543300461417772>()) {
                        case 1931268364:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var1;
         default:
            throw null;
      }
   }

   public com.yiyiaddon.g.d.b b() {
      return this.a;
   }

   public a a() {
      return this.a;
   }

   private List<com.yiyiaddon.i.e.b.a> n(List<com.yiyiaddon.i.e.b.a> var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = var1.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1n5nsna49axzp","hanz+FmA90eeVIV5bczXPQNWewpMEq92Xepntz5AhVM=",-7891251934476881634,8184452363594017230,-5776155547404515974,1137975374570830907>()) {
         case 699742449:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s190kmk1epbwcl","uEuJ8Jz8NrHRzCkar/IYcZONxdW/E+EWq4g7lpaRuig=",-5165640386553196255,-3425122487372122648,-52771025722467920,-8572347648470561577>()) {
                  case 29101831:
                     com.yiyiaddon.i.e.b.a var4 = (com.yiyiaddon.i.e.b.a)var3.next();
                     if (this.a.aM(var4.gk())) {
                        label23:
                        switch ((int)com.yiyiaddon.m.b.a<"s1x35jy83nd9yv","zJ1rcPZtrG2pSux5c+l50kGC4IRTloCOgAYje1HumsQ=",-7110798895306054309,7478464319392940553,-7411601404262876872,6055465474508017910>()) {
                           case 1540522422:
                              var2.add(var4);
                              switch ((int)com.yiyiaddon.m.b.a<"s2oa6x311zx3mj","qsDP6RE3bYzWJGSAWWoA0CMAc+IYdyQzOfQ+KIeC1JQ=",-1018593625453649153,5418266633640787518,2710185583022447407,1364514868517602843>()) {
                                 case 1879537829:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s25ctwb77o146t","tUoqkIFMoOFKh83N56EkO9GVXXMwWiuTBg8YUz8nG3E=",1312888963772801690,9111585925111070519,8242723693770918668,5323820053095911603>()) {
                        case 156262934:
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

   private Map<String, Integer> b(List<com.yiyiaddon.i.e.b.a> var1) {
      LinkedHashMap var2 = new LinkedHashMap();
      com.yiyiaddon.g.d.c[] var3 = com.yiyiaddon.g.d.c.values();
      int var4 = var3.length;
      int var5 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s2zgju1u0iksqk","WR1ywjUrSj77Rxg/f+0OR0NQAep+cn8NRuJmFd+ergA=",-8594365424137894910,1503224528792872957,-4520352876258555809,-6033470454448476311>()) {
         case -177131775:
            while (var5 < var4) {
               switch ((int)com.yiyiaddon.m.b.a<"sefal9ua6bwya","4yWoVhzm4j+kaJsN+J5DTgCzDLOPpxBB3ZBhOZoRChI=",-1647097828215139709,774500503112618181,-5864796406384125609,-282085631104239187>()) {
                  case -25593947:
                     com.yiyiaddon.g.d.c var6 = var3[var5];
                     var2.put(var6.h(), 0);
                     var5++;
                     switch ((int)com.yiyiaddon.m.b.a<"s2a0w32wtyjefu","fs/uJFvMrblWMNAMgG0wYO29QzP6dpwUQgx+itN2VvY=",1170377398965634946,5045517854516778370,4382309227239538422,2563861819476743308>()) {
                        case -1490806547:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            Iterator var7 = var1.iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s178le6xbrigt4","yG+Ed2tPjSvdvs+Rscg9U/HfWzgO9wMqXYpw6AW6KYE=",-4627678964816020193,-1951451303614461705,-5257108326354507002,4395731800362153089>()) {
               case 723414958:
                  while (var7.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s16va6a3ww2ghq","OlWiE7w/FUxK4QEF1lu7pVT5/wZ4T3SQAuctzLXI/7Y=",-4280555591165345468,1828199572268391527,-4387063167904889732,-8772476151660749474>()) {
                        case -603540104:
                           com.yiyiaddon.i.e.b.a var8 = (com.yiyiaddon.i.e.b.a)var7.next();
                           com.yiyiaddon.g.d.c var9 = com.yiyiaddon.k.e.a.b(var8.gl());
                           var2.merge(var9.h(), 1, Integer::sum);
                           switch ((int)com.yiyiaddon.m.b.a<"s2jydeehjocsqd","+E9EXlHKXrQn7kjSB4it5Kg9CfpcrLcWkWnGiXYeuhw=",-6771408288388096182,-347184567092630670,-5051886429582333409,-8917026348827968533>()) {
                              case -1737129134:
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
         default:
            throw null;
      }
   }

   private Set<String> a(com.yiyiaddon.i.e.b.b var1) {
      LinkedHashSet var2 = new LinkedHashSet();
      if (!var1.b()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2voiigf0ohmp2","+N7vtyKgPFb4MwRIWIP4fEFVb+ZGkQDz486HeolUNsY=",-6257236527602383367,6793603597477386494,1313612888905971620,-3263616883326627868>()) {
            case 889585072:
               return var2;
            default:
               throw null;
         }
      } else {
         Iterator var3 = var1.D().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s1s04zrxwdf638","hLALIMQXZI7XOQ7U93KVJWmV12lBK+g4hcVK3X+/dr4=",1583672588900321075,-4991390257799207726,2805091420225837545,-700216432692021793>()) {
            case 345586655:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1gqm2ek5ftru4","AnySNU2lpH82d8MGEggQioMa4PyKJxf4JFs1ie4xMhY=",-8287372181455146792,2585827481477518717,7819490841796424784,-2734379334848947560>()) {
                     case 1046581847:
                        com.yiyiaddon.i.e.b.a var4 = (com.yiyiaddon.i.e.b.a)var3.next();
                        if (!this.a.aM(var4.gk())) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1bhfcmt3vew2s","n7+jotwGWjbQSaq6fEVohTxRU7q/Yoj1olRnjUrM4nw=",8639474166389050574,-6985094364430440227,-6533153653974926805,3158579422840152210>()) {
                              case -1273626782:
                                 switch ((int)com.yiyiaddon.m.b.a<"s1vd380gu7t5jk","TWST/mEI9c57TOVXCXa+xycCW7HTyL61qxW1Jhf+6lw=",-6008431374339153987,67152222413997941,-4068802407226997374,-3772125638173235764>()) {
                                    case -1757857132:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           label66: {
                              com.yiyiaddon.g.d.c var5 = com.yiyiaddon.k.e.a.b(var4.gl());
                              if (var5 != com.yiyiaddon.g.d.c.BLOCKSTATES) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2shkmmv3m3ni8","zyjur6NILmFJMUs2PO6nlBG/bpqWX0UU1AQJ2dHRJt8=",-3281918029273939710,6732708925716890647,-8074251647278055636,-5034142606352137093>()) {
                                    case -112166839:
                                       if (var5 != com.yiyiaddon.g.d.c.MODELS) {
                                          label43:
                                          switch ((int)com.yiyiaddon.m.b.a<"s360f54wcy4qgy","y9Rr+czmkLEU23PoCfzlVvqnV0JUw8o0AYRkJp700Hk=",1910455716894636583,-1969336807300540980,388676339777656340,5865450998169105213>()) {
                                             case 1456240335:
                                                if (var5 != com.yiyiaddon.g.d.c.LANG) {
                                                   break label66;
                                                }

                                                switch ((int)com.yiyiaddon.m.b.a<"s3h5d9sf0hcq2x","Qfc8GlrKilal7CL5Zq5nK2rTbcfUvYbp+jFuneaUeHQ=",7024745299945282728,1944870970426975258,-6102678219126246918,-8380245080551131803>()) {
                                                   case 559418610:
                                                      break label43;
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

                              var2.add(var4.s());
                              switch ((int)com.yiyiaddon.m.b.a<"s18sox4jnwcyfj","zrKefjWrSabxwbXiND6BshGtChwTmkyhHZ78gK6MMvU=",8232670890326020481,-1706339267361409659,-4809832128163421623,5192035584288465225>()) {
                                 case -666236358:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s3iqnohonlvy2d","zg3Jl2WhkP9DMsS8BprZRm0MjAjjIrmeHgi778Ltq9o=",-6317215862848142833,7851154846365464810,-5856048663504915617,7949340139309458513>()) {
                              case 2135838779:
                                 continue;
                              default:
                                 throw null;
                           }
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

   private void a(com.yiyiaddon.g.d.b var1, f var2) {
      this.a = var1;
      this.EJ = var1.dn();
      this.b = var2;
   }

   public f e() {
      return this.b;
   }

   private void a(com.yiyiaddon.g.d.b var1) {
      if (var1.a() == com.yiyiaddon.g.d.d.FAILED) {
         switch ((int)com.yiyiaddon.m.b.a<"s13bpyfljb2vib","X9VsvZbdH57bXG8eISnUyYomlvz8ccIz5oHiKw8Fneg=",3971146303931577201,6166973647015289016,-775683445295690750,-3515231968679184334>()) {
            case -351217106:
               u.warn(
                  (String)com.yiyiaddon.m.b.a<"spi54amc354ag","IO+imACte/dZRxxrqC4gLraj2Hh5RWfnOCsTHoIz1XHUrIFAd7JXLkl756xKWA==",-7108525220961965416,-4293839748197232810,8417077619140050436,1057440245800439735>(),
                  var1.br()
               );
               return;
            default:
               throw null;
         }
      } else {
         Logger var10000 = u;
         String var10001 = (String)com.yiyiaddon.m.b.a<"s3qo6b1xeaxy8f","uFtfag6LtyVx5+vtSY1EXdUPwM5JtU8ttB3y51rabSpZk6r+XU0LHM6TxL8ItuKvKWBdXh7DkQpwh+PHXAyrrOV5kwIL0nncNzUIKiGgqjc156EN3L26mN6ZG31zkIq9ADYWDVFRJTng+g==",-141421534388690184,-2198376032534756053,-4436824883117257811,5135773279480147321>();
         Object[] var10002 = new Object[6];
         String var10005;
         if (var1.fl()) {
            label34:
            switch ((int)com.yiyiaddon.m.b.a<"s37glc408ad8ow","LcCgQ4UonUy2C3NnnW/ApUmG0vOcWd1MC2Jje6q2W2Y=",-704678757277947721,8446941617281078126,-3985504389996118226,-423097072762892048>()) {
               case -297694415:
                  var10005 = (String)com.yiyiaddon.m.b.a<"s2g4059nuhkdrl","lmeXhv0h5opPdXnefDUfGEOscJLiaaaMhyybUwuwlTKhcNKOglJjTw==",2956624181126367327,-5327710535284873666,-903474868168363026,6758170100307467166>();
                  switch ((int)com.yiyiaddon.m.b.a<"s3qqa84njmeahf","xBCDtDNPKwiOppzvebUVqftQ3nboChRzEJNrTgVziVg=",3550341636915235222,4183775080583741738,5847086677348677129,-18279063785298872>()) {
                     case -1270475943:
                        break label34;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10005 = (String)com.yiyiaddon.m.b.a<"s1mkv2m99f6cfo","iQp2jg48VHTFBdSY2MfKCSds/ASZzvxRLCwtuVqK2W9DcFC18LFoLw==",-4854135735818411657,-8943262370026256095,-6683203499795739072,-2136942953543989561>();
            switch ((int)com.yiyiaddon.m.b.a<"s34vmpvipuu8g4","Tp9cZnmBhK0XjPreuVQH3uRWu8pp5TFT5hzsK7CH1ZA=",-6204916095405587283,-5142846560665123577,3075376250075412817,-1782438712781723689>()) {
               case 134561799:
                  break;
               default:
                  throw null;
            }
         }

         var10002[0] = var10005;
         if (var1.dn() == null) {
            label27:
            switch ((int)com.yiyiaddon.m.b.a<"s19yal3c7mfytd","TJ5xx+RNNOxZ/OuwyVQCoS15HuqPW6VNebG8TvJezSw=",-3183239613096582620,8441640193760805819,-3682755343728305666,-280984496956618838>()) {
               case -2057503236:
                  var10005 = (String)com.yiyiaddon.m.b.a<"s2b51nlwxm265p","4i7sgB6lfZPX3gcu+KyNTOPEihOpBXnNuk8GH4lq",6078068027292034263,968445679710867520,-611598376932209050,-1957885233250475291>();
                  switch ((int)com.yiyiaddon.m.b.a<"s1iygp87dik9oe","pajVeYClbkAJ0NWZusq5yBHyoqPtwPQOFdxg3Mrkdhs=",4677412716514330557,8492790590777063574,-1493418702504521411,6756806776887311633>()) {
                     case 1527234541:
                        break label27;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10005 = var1.dn();
            switch ((int)com.yiyiaddon.m.b.a<"siexznri63wra","KlVTUyesXQcrdT5iPu1X2s0Kyofa8uhV/wLStzw6Ro8=",-8489716005933990221,-8287777112024116994,2396458095431308483,7292770297443986886>()) {
               case 1457956003:
                  break;
               default:
                  throw null;
            }
         }

         var10002[1] = var10005;
         var10002[2] = String.join(
            (String)com.yiyiaddon.m.b.a<"s22d5sk9dqzxb4","WDei3LvTv7CHfDyEVaYtI63UwfeJBwCGHLv3FckZ",6140012405446793842,7712323750891344895,-5455460197789396914,2890284866345607814>(),
            var1.bx()
         );
         var10002[3] = var1.gf();
         var10002[4] = var1.dx();
         var10002[5] = var1.a().h();
         var10000.info(var10001, var10002);
      }
   }
}
