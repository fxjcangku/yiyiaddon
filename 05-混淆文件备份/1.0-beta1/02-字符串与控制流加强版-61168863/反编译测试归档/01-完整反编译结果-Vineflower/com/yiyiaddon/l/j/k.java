package com.yiyiaddon.l.j;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.types.RRect;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class k extends p {
   private static final float nS = 24.0F;
   private static final float nT = 4.0F;
   private static final float nU = 8.0F;
   private static final float nV = 64.0F;
   private static final float nW = 22.0F;
   private static final float nX = 8.0F;
   private static final float nY = 12.0F;
   private static final float nZ = 0.6F;
   private final List<String> dF;
   private final Supplier<Integer> K;
   private final Consumer<Integer> w;
   private final float[] f;
   private final com.yiyiaddon.l.a.b[] c;
   private final Paint O = new Paint().setAntiAlias(true);
   private final float oa;
   private final com.yiyiaddon.l.a.d d = com.yiyiaddon.l.a.d.a(0.22F);
   private final com.yiyiaddon.l.a.d e = com.yiyiaddon.l.a.d.a(0.22F);
   private final com.yiyiaddon.l.a.d f = com.yiyiaddon.l.a.d.a(0.18F);
   private boolean gK;

   public k(List<String> var1, Supplier<Integer> var2, Consumer<Integer> var3) {
      this.dF = List.copyOf(var1);
      this.K = var2;
      this.w = var3;
      this.f = new float[this.dF.size()];
      this.c = new com.yiyiaddon.l.a.b[this.dF.size()];
      float var4 = 0.0F;

      for (int var5 = 0; var5 < this.dF.size(); var5++) {
         this.f[var5] = Math.max(64.0F, com.yiyiaddon.l.g.a.b(this.dF.get(var5), 12.0F) + 22.0F);
         this.c[var5] = new com.yiyiaddon.l.a.b();
         var4 += this.f[var5];
      }

      this.oa = var4 + 4.0F * Math.max(0, this.dF.size() - 1);
   }

   public k(List<String> var1, Consumer<Integer> var2) {
      this(var1, null, var2);
   }

   @Override
   public float c() {
      return this.oa;
   }

   @Override
   public float d() {
      return 24.0F;
   }

   @Override
   public void a(float var1) {
      com.yiyiaddon.l.a.b[] var2 = this.c;
      int var3 = var2.length;
      int var4 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s4f71gr1qfcxa","ciWseL/mTRzqQA7ClWLXhnYlLahKc7W3VsQLCe3lvyE=",-8940948526534454921,9119964566648144093,-2841139276910937583,-1036974254903211691>()) {
         case 1184625621:
            while (var4 < var3) {
               switch ((int)com.yiyiaddon.m.b.a<"s30tz11pqn92p2","A6W0p3G0szveKs2t3vsvyqxoz4553e8wS+zc4RfHCuY=",4832178292535620647,3052726386491769444,-8956410847610884504,-537291562157614214>()) {
                  case 546555076:
                     com.yiyiaddon.l.a.b var5 = var2[var4];
                     var5.a(var1);
                     var4++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1vvr8pjcguhd7","ydU5d1RVMeH0ytiBCwzBaeC8gZ1WJkCdOgvFRKDNFek=",1533942260921625175,1579767241046182737,2072700787222079610,-4185352303698751370>()) {
                        case -1025812248:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.k(var1);
            return;
         default:
            throw null;
      }
   }

   private void k(float var1) {
      int var2 = this.cV();
      float var3 = 0.0F;
      int var4 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"sb619mcteth3i","N25MpoYEO+FoGPvrg3vTsILYgqYEPUBb50MJaHFFa4E=",348652518531550901,-4673178010986374631,3995328065128976481,8763165055027828623>()) {
         case -1159012707:
            while (var4 < var2) {
               switch ((int)com.yiyiaddon.m.b.a<"s22afj8dn56by8","7vAiMd+D9SyTYV9vsUgwm8fOF5TxpFlpMBr+Zlm3/sU=",-1178947545653519621,-6523537143687176540,-7832524113261628817,-3834855679442990644>()) {
                  case -1497858153:
                     var3 += this.f[var4] + 4.0F;
                     var4++;
                     switch ((int)com.yiyiaddon.m.b.a<"s225wj6n1t8ych","8aMmK7otkv2OGdEUfDRo6iWgio8BVABsdEnfmlGpKoY=",-1319497278517260710,4979587994497708211,1646170646543900778,-8497706832853348076>()) {
                        case -1112844101:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            if (!this.gK) {
               label72:
               switch ((int)com.yiyiaddon.m.b.a<"st0u3686q7d6q","wpSA34JwDYxW2g9vH2gTEllBszy+kapnwAtLdgpmgQw=",-708375857366231959,-94674898127331784,-2353292256562119142,-8894008482018482992>()) {
                  case -1878497997:
                     this.d.f(var3);
                     com.yiyiaddon.l.a.d var10000 = this.e;
                     float var10001;
                     if (var2 < 0) {
                        label69:
                        switch ((int)com.yiyiaddon.m.b.a<"sodxybsu2yj3i","V3JgJvwZAVzwpfKndTtD+dQVOMfgftPBjXtc5Sa9R2s=",5834796858603798004,643850901570594627,4753941820261823488,8031671900741063128>()) {
                           case -688232483:
                              var10001 = 0.0F;
                              switch ((int)com.yiyiaddon.m.b.a<"sg3tovcp18xxn","bOpUHqHtsYHW925TAOnb0YbV88VyHQ4nXsZX5mYXw9U=",-3895975536027289509,2686852474352833608,3590434454203922805,1465570978828979034>()) {
                                 case -1317245743:
                                    break label69;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10001 = this.f[var2];
                        switch ((int)com.yiyiaddon.m.b.a<"s3r9rh5oghbpp1","sv2sHMx4Q5ZoJAy/eeqAmBtUkfyeSouaxdGezGZ6roA=",-950611695054884990,-5803317800169643983,-8572265467856277373,-8698290564873376928>()) {
                           case 18623211:
                              break;
                           default:
                              throw null;
                        }
                     }

                     var10000.f(var10001);
                     var10000 = this.f;
                     if (var2 < 0) {
                        label60:
                        switch ((int)com.yiyiaddon.m.b.a<"s17y3gcyfox1pl","o1lbIXMxjANs/WI//n/OTSqDFK5bwWQBdb3XM1NrCD4=",-4459587068768885773,4641577033169878396,5289812535943734320,-4171046494052642346>()) {
                           case 1358981377:
                              var10001 = 0.0F;
                              switch ((int)com.yiyiaddon.m.b.a<"s8k7jrcrezz45","eEthWq9GRfFBYPlFv+QDbqtRBXE1Dkgxlat4lRHuCsw=",3612694228471103825,2648714740749523831,-9090290912840017765,-816955351904827170>()) {
                                 case -386903845:
                                    break label60;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10001 = 1.0F;
                        switch ((int)com.yiyiaddon.m.b.a<"s32z7hmrwt71x4","QEaKI65kW0F0vj6nqkZ6aG2NRfYYvrp6IxIaCMnkEog=",-165534623892776029,3705534649878694202,3580195489094416462,5294995545709149197>()) {
                           case 1694376874:
                              break;
                           default:
                              throw null;
                        }
                     }

                     var10000.f(var10001);
                     this.gK = true;
                     switch ((int)com.yiyiaddon.m.b.a<"s321by35xe3e51","fp+AXJF2DDMUJhx+xLAtANVQfVXhMuBtwk3O32Nw4uI=",-3722147317224018166,-3495082378284821892,-4751610081708238700,913856420617583921>()) {
                        case -629400993:
                           break label72;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            if (var2 >= 0) {
               label53:
               switch ((int)com.yiyiaddon.m.b.a<"sompqj27vuaqe","y02pw5VpIkCVrsteonocfnqagPEs1KRU7IZGfwYi92U=",-7772194273230029712,-828983281928366522,8634568453839312568,-6356052558976973310>()) {
                  case -229455719:
                     this.d.d(var3);
                     this.e.d(this.f[var2]);
                     switch ((int)com.yiyiaddon.m.b.a<"s1ympfvcggujw7","NvMOVGfNU8vJr8jiJ0Kc0pQT1Pfsh9THjwrfy0/TJvo=",-8884740921062257685,-7762162526023011394,-7496813683016240852,5177704588148220372>()) {
                        case -1669514461:
                           break label53;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            com.yiyiaddon.l.a.d var6 = this.f;
            float var8;
            if (var2 < 0) {
               label46:
               switch ((int)com.yiyiaddon.m.b.a<"s2xpk5yegczsy3","nn9eO5RemLvZImAJkOO+EfsD0q2ZxGC+u1GoRK7XJeU=",-1627509783672560146,1081294858025689051,-683498039794923239,8218027639559870927>()) {
                  case -2098325616:
                     var8 = 0.0F;
                     switch ((int)com.yiyiaddon.m.b.a<"ses5ynvrzwad2","2iwZqFRK8nTBKfOwRrCXLUkPCSfcpCO2ryt5izHi0fM=",-5861447035831542241,-7422906141214447955,2079942395846534645,1439216977589927961>()) {
                        case 1777479430:
                           break label46;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var8 = 1.0F;
               switch ((int)com.yiyiaddon.m.b.a<"s2by4nxbj665ep","dM1r0aBZVktiKbDIk3rn4OYNtBDak7aXIG8rhT5/JsM=",-3741722361736640566,6875895011468906481,8724326144538593551,-518854940161702466>()) {
                  case -838267253:
                     break;
                  default:
                     throw null;
               }
            }

            var6.d(var8);
            this.d.a(var1);
            this.e.a(var1);
            this.f.a(var1);
            return;
         default:
            throw null;
      }
   }

   @Override
   public void b(Canvas var1, float var2, float var3, float var4) {
      com.yiyiaddon.l.i.c var5 = com.yiyiaddon.l.i.c.a();
      float var6 = com.yiyiaddon.l.i.c.y(var4);
      int var7 = this.cV();
      if (!this.gK) {
         label116:
         switch ((int)com.yiyiaddon.m.b.a<"s1xyv0z1mzku2n","14BQwlAsvU8riyXFR+z48cgjWKuNlSPjhD4WhDYazko=",4306984709770082109,-9065118348943639273,8504645941600873702,8108034441978797147>()) {
            case -1405932587:
               this.k(0.0F);
               switch ((int)com.yiyiaddon.m.b.a<"s5uivtxh5n8z4","c7b2Y4A719tDX5//CAsh525Tt1Lk7N/+HsrhmddPrk4=",5332225992289354225,-2910709378359926510,2685974109191589343,-4252852921331068541>()) {
                  case 806415294:
                     break label116;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      float var8 = var2;
      float[] var9 = this.f;
      int var10 = var9.length;
      int var11 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s11a0hvyxrs10j","0j0piljw5Hi7TmOWMF/rewJX59IaGmNtirQ53jBunHc=",-2772629497718778817,4642078556455290739,-1620695658520410300,-2298533018550838592>()) {
         case 2078593464:
            while (var11 < var10) {
               switch ((int)com.yiyiaddon.m.b.a<"si2jjqbhn3kdn","hhm8HX23ZyO7dzrktI5pXcLV72oTWhNQWwXZqhCiPE0=",7476713537693718412,-4692702628303610574,1607388849891780659,-5214587140551033585>()) {
                  case -332973500:
                     float var12 = var9[var11];
                     com.yiyiaddon.l.b.j.a(var1, var8, var3, var12, 24.0F, 8.0F, var5.vk, 1.0F, var6);
                     var8 += var12 + 4.0F;
                     var11++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1zsfhu7at1gvd","g9WzmUpsibG/6wJ8DbHgMkyQgCjdWjWiSA9bhdYY3PI=",627573494547117692,-7730801227311286807,-9151502080186334656,2725952480215798899>()) {
                        case 1008331493:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            if (this.f.r() > 0.001F) {
               switch ((int)com.yiyiaddon.m.b.a<"s3q8a4oa5l7trm","bUG9fe6341jX9BskGJEWsE3kvWAJTq/TOtLOxwTyWJA=",-1196652455249381171,5559403598281907365,747010560361339984,3911799278816626003>()) {
                  case 1811415058:
                     if (this.e.r() > 0.0F) {
                        label100:
                        switch ((int)com.yiyiaddon.m.b.a<"s1x9abbdn9xpf0","//9RywWbg9s7aNN1iTG49I4zabQc9Px+vdnDcV/IxL8=",9025963202604098359,-8235016331643560137,-3093187695831991546,-3426958203258711378>()) {
                           case -2022409105:
                              float var19 = var2 + this.d.r();
                              float var21 = this.e.r();
                              com.yiyiaddon.l.b.j.a(var1, var19, var3, var21, 24.0F, 8.0F, var5.uS, var4 * this.f.r() * 0.92F);
                              com.yiyiaddon.l.b.j.c(var1, var19, var3, var21, 24.0F, 8.0F, var5.uX, var4 * this.f.r(), 0.25F);
                              switch ((int)com.yiyiaddon.m.b.a<"sh9islfm3m3hl","lAA5SP/w1Ih/xHisXRSQp3fIlUyFmNw7JkP6NE0Jp+I=",5210308034458986482,7416565248065544081,9219181967238779848,-5157597748587167598>()) {
                                 case -2146104745:
                                    break label100;
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

            float var20 = var2;
            var10 = 0;
            switch ((int)com.yiyiaddon.m.b.a<"s3mgkimk8tjm8e","EyHjG2GvOwGeKBrXveuteJzBnQtp0hxmYK2R7ADfIjw=",-5052549764257247596,-7239672086799965840,-493974894232824518,-1102277512181813991>()) {
               case -387206014:
                  while (var10 < this.f.length) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3co2q83ea33qq","UcU+HlCrLp92oeMQDeRG4Rxfd3H4b+pVt9pZvbuxJV4=",-6295431326412600063,-8702573478254399519,5343294969385521995,8824364282557417292>()) {
                        case 269038726:
                           float var23 = this.f[var10];
                           boolean var10000;
                           if (var10 == var7) {
                              label78:
                              switch ((int)com.yiyiaddon.m.b.a<"s1v1h3c8lcs2sr","4k0QLTn99mSuEfbad58grLNtSKBODgz+7ZX3c9YihGY=",-4089139753019357527,-2165585135303856105,-8317692282209140253,4125712217989376485>()) {
                                 case -727008548:
                                    var10000 = 1;
                                    switch ((int)com.yiyiaddon.m.b.a<"s27zqtdo1x9j0m","35BkNYj1jsCtpW8uvwoaDWIUvCRAjX1rE/WRKcIGt4g=",-5819828699637888569,-7594189791627199401,4286926665686436771,-4553989783248750229>()) {
                                       case -447371521:
                                          break label78;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var10000 = 0;
                              switch ((int)com.yiyiaddon.m.b.a<"s2uxw7pc96bt9l","b82Aam6F8DkSihu+x1MCjI2lroPfuzeLsLNilMSEOUU=",-2674696840314372390,-2817789616119811689,-4682574411442339549,-7483535051662642230>()) {
                                 case 1368564595:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           boolean var24 = (boolean)var10000;
                           if (var24) {
                              label74:
                              switch ((int)com.yiyiaddon.m.b.a<"s3umxkuffvr8l7","EHLLvUjLikOnuMqjN804VvpyWk5bhtrVcb85vlv8PBc=",-1959760584210906732,-5888738605488138250,-8425178416373329948,8423343929643449915>()) {
                                 case -1397180102:
                                    var10000 = var5.uS;
                                    switch ((int)com.yiyiaddon.m.b.a<"s2r9wcbw4wpgx3","GRfKl/g89SFsKpjaTPe77c2bGgUTqLnryNgBo9dHhDU=",-6863136679859868608,272783528596726111,47439678163243592,-3050719006359752211>()) {
                                       case 1927961317:
                                          break label74;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var10000 = com.yiyiaddon.l.b.j.a(var5.vk, var5.uS, this.c[var10].f() * 0.6F);
                              switch ((int)com.yiyiaddon.m.b.a<"s29c845hc4kwlf","RmYEgZPLPcOx+NAciM2psdAEkYhqpg0UuAY8bivEyOA=",4661646477251687857,4896870360041060220,-4733357990477671696,5947282094199114001>()) {
                                 case 977167893:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           int var13 = var10000;
                           float var14 = Math.max(0.0F, Math.min(var20 + var23, var2 + this.d.r() + this.e.r()) - Math.max(var20, var2 + this.d.r()));
                           int var15 = com.yiyiaddon.l.b.j.a(var5.vh, var5.uZ, Math.min(1.0F, var14 / var23) * this.f.r());
                           boolean var16 = this.c[var10].a(var1, var20, var3, var23, 24.0F);
                           if (this.K == null) {
                              label70:
                              switch ((int)com.yiyiaddon.m.b.a<"s176ur3zjdxke","BjA5wLtaZiWbRNscP/sLIixUhb+es/nadYECIuRrPdQ=",8230730412023951371,4244178542703301191,-3145307803996084270,2013281326302489148>()) {
                                 case 1062539744:
                                    this.O.setColor(com.yiyiaddon.l.b.j.a(var13, var6 * this.c[var10].f()));
                                    var1.drawRRect(RRect.makeXYWH(var20, var3, var23, 24.0F, 8.0F), this.O);
                                    switch ((int)com.yiyiaddon.m.b.a<"s2uyvpjag3vdfw","BCggsrGUouebx53rvdfXNRQKknOQMDGbNpFtsnpmV40=",6012304802134956872,-17904936445475384,-273338180552862092,-6324251073547266266>()) {
                                       case -1717386827:
                                          break label70;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           String var17 = com.yiyiaddon.l.b.d.a(this.dF.get(var10), var23 - 16.0F, 12.0F);
                           float var18 = com.yiyiaddon.l.g.a.b(var17, 12.0F);
                           com.yiyiaddon.l.g.a.b(
                              var1,
                              var17,
                              var20 + (var23 - var18) / 2.0F,
                              com.yiyiaddon.l.b.d.c(var3 + 12.0F, 12.0F),
                              12.0F,
                              com.yiyiaddon.l.b.j.a(var15, var4)
                           );
                           if (var16) {
                              label66:
                              switch ((int)com.yiyiaddon.m.b.a<"s25bz0yrlf1koy","OoheMRJV/Rfn4ikWg0Tzsa8jxIcc/mWHVKy9FTpaNXE=",-4215688466590125933,-3602395796377714608,-3279542611624775651,-4915821597038003528>()) {
                                 case 1632684327:
                                    var1.restore();
                                    switch ((int)com.yiyiaddon.m.b.a<"s2sp0gylsa6jnr","OazoQFWT8q0tK02tU9+5U4ry3J+7fhrmyuNQ6RI9yec=",1295666193631065610,-7947884864443216899,6530337074170765781,-6178344622129735997>()) {
                                       case -680987919:
                                          break label66;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           var20 += var23 + 4.0F;
                           var10++;
                           switch ((int)com.yiyiaddon.m.b.a<"s31ym6h6bf3cyv","Ox/nQGwvuoCUSHVgiqnpqgARyzitd0aWuK98/Kuguko=",-6121640499314668603,-5568415197335917641,838047667568741965,2969504610105039753>()) {
                              case -1859315675:
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
   }

   @Override
   public boolean cB() {
      com.yiyiaddon.l.a.b[] var1 = this.c;
      int var2 = var1.length;
      int var3 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s3x5y7zut1o6k","17KOAmNYCD3PrMTg4hFiJ0sw27fSfbG/fNjI13D6EcM=",-8513442761022430530,4270646127505002929,4209913064302726120,-6997281487441059185>()) {
         case -165431556:
            while (var3 < var2) {
               switch ((int)com.yiyiaddon.m.b.a<"s31jodzi1w6nvg","TehFqrsoQ069ZMytWq1jmg5vt8vFukgDJE7qPLPJRlw=",-1691345987171323441,8523497035474934125,-6829923673834183868,9186160491842674372>()) {
                  case -1018426909:
                     com.yiyiaddon.l.a.b var4 = var1[var3];
                     if (!var4.fP()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1wzecs70r3g2q","uDNaU1DYCgc21N0fHcD3KdkZhPGKS42kOQEOuiX1x5M=",-5699831715417144971,-4208280074775625106,3237974069122670755,3430242737941179031>()) {
                           case -1025882339:
                              return true;
                           default:
                              throw null;
                        }
                     }

                     var3++;
                     switch ((int)com.yiyiaddon.m.b.a<"seubri164e01v","ZpzdpMTaF8o4rh2CBrJSPatO6o4henkGcnsA8RVpu/w=",-613666324378655679,7656116685732098582,-3625422447889468647,5226692648162635480>()) {
                        case 404686139:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            label64: {
               if (this.gK) {
                  label44:
                  switch ((int)com.yiyiaddon.m.b.a<"s34tbawtf7j0ea","5rq30Y15d2QGJ+T9PsgR89edrxRqXtJewzmV+yNuHgo=",3794846833783666618,8037007008664306788,4057605992016898886,8955406550521580353>()) {
                     case 1491886042:
                        if (!this.d.fQ()) {
                           break label64;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2qkgroi87fnw1","142/FCkQ+eyyc+lWVI95T9kZudPl8h4/NimxaqgtCNg=",-4534505154887481025,7244537731643804464,-8523854329119140548,4891251676268674735>()) {
                           case -2017828702:
                              if (!this.e.fQ()) {
                                 break label64;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s3acm2ftdgur4c","vKvL9VqNbskJMmwINjvVWAJeLF2Zp90Vy4A4RCdRVAw=",-3893031816421381811,-1005849851830104492,4691880891940975660,7332211249705310621>()) {
                                 case -1650603507:
                                    if (!this.f.fQ()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3fnilrzia5xo8","8ojO79ZviViSqHzNtc9/Ykxj0D0obtOVgE6a08Z7Qgc=",-7492757776008792372,-3266711737262034643,-1486529415973247906,2733380065240921594>()) {
                                          case -757991751:
                                             break label64;
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
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s1cyw7w8so1ao8","Y8M1MW8AmSPfYm26uA4YTgXuxe/sjrVaRTeCMD89Al8=",1052880005277749117,154342827579308469,2989895154650978947,-915450535418566417>()) {
                  case 1329981584:
                     return false;
                  default:
                     throw null;
               }
            }

            switch ((int)com.yiyiaddon.m.b.a<"s39q9oy6gu60ou","p91NuoPvNwxT2Yqjierf0TMqM+A7JQbREW4CcDAHaMY=",-7448482051770382408,-7436960299469770310,4964733833315095517,-7629556482416384438>()) {
               case -121023210:
                  return true;
               default:
                  throw null;
            }
         default:
            throw null;
      }
   }

   @Override
   public boolean a(float var1, float var2, float var3, float var4, int var5) {
      if (var5 == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ftoaeyj4hsbh","SkT+yVohxn9yzWSK0yIGDulsDOLLc+z/jvC6zFLqOQU=",1193613770968057242,-3242748776300387075,-6244237647614056061,-7866124394874450077>()) {
            case -1111774787:
               if (!(var2 < var4)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1y7yzki7n2rfi","78GFl5dEeoxbUSlM7gzI/0/FPensUj3bn/BGG4akNZ0=",5766577846596306963,-3610451625367263251,-9009426257519499462,-7968123724992959166>()) {
                     case 482760797:
                        if (!(var2 > var4 + 24.0F)) {
                           float var6 = var3;
                           int var7 = 0;
                           switch ((int)com.yiyiaddon.m.b.a<"s1ds1t24kc6p16","jU4B4H0qdxGI2GSuDVHWvLlEiD3BB/8CXbtlDPYWvrc=",-1041038521431340320,8897073719724270297,4161482257108301132,-1365363793997737851>()) {
                              case 1409938789:
                                 while (var7 < this.f.length) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1799tx88prq8z","NcY0mBxix1pGbD+khSlXrxbD0jca6vggEb+HIQczfhQ=",-8901562679731334344,2702296043928295819,-1121880193117828834,934784968466871309>()) {
                                       case -1889572551:
                                          float var8 = this.f[var7];
                                          if (var1 >= var6) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2lw5uvl20mwna","FxWovA2Rtg31RWjLeQqbhykkYKtZ9fl5ISoYQaFqqXo=",7538343052187148132,-4672818573222798622,6269715317469742826,6585337508866255895>()) {
                                                case 1886460514:
                                                   if (var1 <= var6 + var8) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3ib5p5gsuhrr1","kT7WGo5pKBnuT0fQy+ZZd6jpnI58j/lU0cskQSxxkEc=",1676185686324521723,-7574673647534056245,-1215582120533590694,9170018617662464511>()) {
                                                         case -268167156:
                                                            this.c[var7].jL();
                                                            this.w.accept(var7);
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

                                          var6 += var8 + 4.0F;
                                          var7++;
                                          switch ((int)com.yiyiaddon.m.b.a<"sebsemk0s4sjd","8oc9Gk4bKw2eO4xhh+F9O+zDwCLgLsc9Bn+TcGU4jPo=",-6012920338338453833,-342170111692218188,-5077138062961032610,-904488496765092969>()) {
                                             case 1069683594:
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

                        switch ((int)com.yiyiaddon.m.b.a<"s2gqobrwn370yh","JKHss/IauWSUTHKiYZzE62ZdcUrkCAsoLPJSrodH1t8=",-7582338186241460402,4690574905796174368,-3217588571884135764,-3621880218477553325>()) {
                           case -351481146:
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

   private int cV() {
      if (this.K == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2kzt283st4wbu","b3db1OzhPRcRu4bqb/2jGiJpcnSAh8kRhlkGVmEcqAU=",-556007397237494854,8377630545127059296,7701913419735520065,2815690786385469855>()) {
            case -1690818516:
               return -1;
            default:
               throw null;
         }
      } else {
         Integer var1 = this.K.get();
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3p4p9rsuvihnj","P6LXCubaGjJPzSrzLGzpjrWRSbtEH9CS+ML7XTKFfYI=",-4633176534553242493,-2193976518128611316,-5709789955564839256,-1920999564908427912>()) {
               case 223163931:
                  if (var1 >= 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3vsfkf73xi9y1","Ob+FcVO3PtEGr/Wqv+zXvhEmKlv5csWC87rFpAX1R0s=",-5343570199922608763,4995063057530296860,-6450070434219491381,-3040980083365047841>()) {
                        case 195657205:
                           if (var1 < this.f.length) {
                              return var1;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s8vsrg30mq8qn","Hq9njncvhPbcasHxHU/ZSv+kkrNFnebRm/pLMfzV/EU=",-740193069153998783,978354081751370936,1571751364501775802,-5600243891592611511>()) {
                              case 1491822666:
                                 return -1;
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

         return -1;
      }
   }
}
