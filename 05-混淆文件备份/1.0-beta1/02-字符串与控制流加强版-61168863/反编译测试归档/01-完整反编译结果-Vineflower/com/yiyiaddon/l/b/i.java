package com.yiyiaddon.l.b;

import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class i {
   private static final float fF = 0.2F;
   private static final float fG = 0.03F;
   private final List<g> da = new ArrayList<>();
   private final float fH;
   private boolean fI;
   private float fI;

   public i(float var1) {
      this.fH = Math.max(0.0F, var1);
   }

   public i a(boolean var1) {
      this.fI = var1;
      this.fI = 0.0F;
      return this;
   }

   public boolean cB() {
      if (this.fI) {
         switch ((int)com.yiyiaddon.m.b.a<"s35gk6p8sxcnn6","Ja036eBkz3mMHvQXA8Gjb+Bzy+cGL0tTsblcCWYfddo=",-4178590613191112373,4696034672429040614,7188408408962117625,-1933599147554347708>()) {
            case -1018577466:
               if (this.fI < this.s()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s25fu5v2romnp0","3EIS9RuEIBTbz/SB4gpIZt4GSdw1+bCspqYZ1+FK9bI=",2429877099393390703,8960909355967972316,-2353490212222612064,-8750334955438618991>()) {
                     case -659344093:
                        switch ((int)com.yiyiaddon.m.b.a<"s28kwze5g4audk","MlXbSxjE3VR/eKrQzdMel3ijV7jclPLEtRyEEBvNndY=",-6378070195411582269,-4447798902930412227,-1880461344655773248,7109432298792827904>()) {
                           case 122407145:
                              return true;
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

      switch ((int)com.yiyiaddon.m.b.a<"s2enwko1plt76q","3G1r0viivY2Z+cXBljppMo6FKd0G+8j+FIDqxrkJPWY=",-3578067017104175734,1120884555139565526,4241472658830710357,-7782170999999589922>()) {
         case 1806199044:
            return false;
         default:
            throw null;
      }
   }

   public i a(g var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2pc21bbfea086","jowHObEi/+xxO/EzV/riNSN0eJ+Zd41eQu9JzdWu7/k=",8848086703183146552,-8176756067961903283,5058013535965009714,-7977094192896998252>()) {
            case 1779300105:
               this.da.add(var1);
               switch ((int)com.yiyiaddon.m.b.a<"s3kbj9ivol9k5j","IxNhiAz1A6nkDllMLwQwJJRTBEfAbxSJT6vq9NSBSrA=",7377911339668298205,-6287127933684988971,-7051690961138689292,-5277515107239194883>()) {
                  case -1808429671:
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

   public void b() {
      this.da.clear();
   }

   public boolean a() {
      return this.da.isEmpty();
   }

   public float b() {
      if (this.da.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1u01loh8ibshj","B2PK3O8JZB62wHhuCpZiwh4zkaXGbiDnDYFK2kfcBM4=",-8562076134044888504,5547796474381325720,7920895584728963581,-675189962483166741>()) {
            case -1348941715:
               return 0.0F;
            default:
               throw null;
         }
      } else {
         float var1 = this.fH * (this.da.size() - 1);
         Iterator var2 = this.da.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"sor09oqy0rg","3shaB9nd5MVFFAST9g+VubPdZcMzi6D2voYW5pFhgWM=",-712535526096100186,-877990266363001658,1272956700372235885,-3278883381899558642>()) {
            case 1108725283:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s16dicsxtafefb","dpPpw1vByh0c3WzpG1bqCrVCpx3OANblaMJsED5tXiI=",-3503905768235328842,3785663942246992278,7072456334177403663,-3509773507598349594>()) {
                     case -1449183456:
                        g var3 = (g)var2.next();
                        var1 += var3.b();
                        switch ((int)com.yiyiaddon.m.b.a<"s1xpcqcr0415ml","vtHHSLqnd4v+Fp8NzNljGv/rop27+mwLiEqY+2nHomQ=",7302138758421707877,-840766536898083329,-6148490894095976555,3926358276447831809>()) {
                           case 767324302:
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
   }

   public void a(float var1) {
      if (this.cB()) {
         label29:
         switch ((int)com.yiyiaddon.m.b.a<"s1c9owogdj06gd","CT4Sduq39qtYkaW1xw/YB9RoBbCiVLte4N3qac6as34=",3680493921822579705,7994433161980638477,-2024818032809149311,-4457249439258602547>()) {
            case -161809626:
               this.fI = this.fI + Math.max(0.0F, var1);
               switch ((int)com.yiyiaddon.m.b.a<"s2gy1fd626e0jt","InOXTcX1YeYdgA88qGJBjhwGfWmNvADKUkdHKt1pGw8=",19817540599951441,-2881847859853179090,2212144601299677961,4509254791960356192>()) {
                  case 858239894:
                     break label29;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      Iterator var2 = this.da.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"sczi5zopawkkq","l2qunb12L1hX8swBtaB8fcyAKAdvcJwKXR4nnz2gSgc=",-6196132077518051681,1413510314446765280,-7891959624284144103,6530596973960579183>()) {
         case -2059516784:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3w1x25gp7pqkx","AI12hOhac0p/bOEszGQ13pSr8a8MP1NBcnz0wYAJ+vg=",3052380183427294526,1241849007026417663,4318618330450552102,-2812226628855669251>()) {
                  case 460924365:
                     g var3 = (g)var2.next();
                     var3.a(var1);
                     switch ((int)com.yiyiaddon.m.b.a<"s3n9j2iulgx3cn","Y5NoeT4FXwO6OzUxVWtosHAXIlLGjfrxlZz/4Ezvpis=",-9080235427732733616,8093461420593932576,5229669924872016097,-6058840447796840623>()) {
                        case 1384070050:
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

   public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      float var10 = var3;
      int var11 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s1c7yjwr2azm9y","MsMlK4M/1/VvxaVWgyEg8O2KgqmGSACXVfsJYnaweek=",-6511469048844771377,-8818143818121124949,7858184152514774031,-6780452449680814797>()) {
         case 1677676250:
            while (var11 < this.da.size()) {
               switch ((int)com.yiyiaddon.m.b.a<"s291aipnlrwwqv","l0mJosbX1oqTb8emPWMO6poN+ZAiauAyLnHAhkIH620=",859121910818347214,-5825581036724414842,8710996817669171071,-4125616397719193253>()) {
                  case 57698452:
                     g var12 = this.da.get(var11);
                     float var13 = var12.b();
                     var12.c(var6, var7);
                     if (var10 + var13 > var6) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2pver5868mnl9","e91awow7oGVZ6UmeJM4bqwwZLJRbVzqADLG1eJCIr9g=",2166817983669068237,3647227836263641380,-8402567979781152375,6938653364186877130>()) {
                           case 1746156963:
                              if (var10 < var7) {
                                 label28:
                                 switch ((int)com.yiyiaddon.m.b.a<"s9fqv8a94y93o","JX/umpQznyWIb3XlK9sPnk/D6AYwdiaN+3GzMGPfRMc=",-1939523342990257151,1687831169106522612,-5915940563626287648,-1498989969009602195>()) {
                                    case -2087443378:
                                       var12.a(var1, var2, var10, var4, var5 * this.a(var11), var8, var9);
                                       switch ((int)com.yiyiaddon.m.b.a<"s3jk1x1kje9abe","Mgs2JVGGmlzmoMyKhd3EOb8AtCqBxQbo06UAz+x7V5c=",-4624428439929593461,1006915106043025630,5137571868963454956,4882156008838842536>()) {
                                          case -1571509978:
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

                     var10 += var13 + this.fH;
                     var11++;
                     switch ((int)com.yiyiaddon.m.b.a<"s3j7qc4v0ygrda","b5B1hUm+8ycBNEoS3CPDOGUzGIm4t42hPxufQnuoMhE=",2914766628411637754,1848270812633363361,1002009453824785602,-8882519345024495089>()) {
                        case 1566876709:
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

   private float a(int var1) {
      if (!this.fI) {
         switch ((int)com.yiyiaddon.m.b.a<"seypc90k1is08","0u8fAobhi2fkK7KMOcuaWXJ2hDOx5KSEDwntR7fkBCU=",4452871390808506496,-5932092690186349218,5141583133586262297,6820707705164283740>()) {
            case 1911768930:
               return 1.0F;
            default:
               throw null;
         }
      } else {
         float var2 = (this.fI - var1 * 0.03F) / 0.2F;
         if (var2 <= 0.0F) {
            switch ((int)com.yiyiaddon.m.b.a<"s2d6nif8r7czce","WCwt2HLWYQEkt9rCIzoJsVs7oxci/Qh9DJBv5UmEwdI=",-5847127667558105316,6559886411094064087,7369268219602091740,2885284756489966430>()) {
               case 890509595:
                  return 0.0F;
               default:
                  throw null;
            }
         } else if (var2 >= 1.0F) {
            switch ((int)com.yiyiaddon.m.b.a<"s2nhi5u8usyqqi","gGb2/IVX8Si2QlCGLwruqKwYYzENHDLTdUKx67y2nzE=",348119018949640912,18994275806372544,149595407848420097,-7049372088495913106>()) {
               case 73314883:
                  return 1.0F;
               default:
                  throw null;
            }
         } else {
            float var3 = 1.0F - var2;
            return 1.0F - var3 * var3;
         }
      }
   }

   private float s() {
      if (this.da.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3nlil3r9y4xqd","aexakEvrTyULauBcSydZp3QsBKsfKPXoSJQCgHqCF4s=",6179244431134384396,-3605690584572110308,757368263701212605,-2993852532009730621>()) {
            case -1761757269:
               switch ((int)com.yiyiaddon.m.b.a<"s6mpgyaztd873","lwAAGHOIJsq5oO5tLGcTotJ+C8mINDnUakI9WAVRJc0=",6286732222848587485,412097073168491201,-3003697814247123486,2018312561698454344>()) {
                  case 828635965:
                     return 0.0F;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         float var10000 = 0.2F + 0.03F * (this.da.size() - 1);
         switch ((int)com.yiyiaddon.m.b.a<"s2nuzori7m3nrx","2A3ppmWRxpcAZBKyCRrGsfoDFJp3bAMLG+eKO1aClWk=",5878264036929666233,2436996224286711865,-9176574365704425623,2149881381291756769>()) {
            case -54803434:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public boolean a(float var1, float var2, float var3, float var4, float var5, float var6, int var7) {
      if (var2 > var6) {
         switch ((int)com.yiyiaddon.m.b.a<"s24h9fs4tboosu","37KiEquD2w3ti0XPJvnrc+/TqU1OFA7Z9fp2kvKl8pA=",4087713820275696103,2062172658125712466,-8925574951092043758,8142536731458198400>()) {
            case -1003246373:
               return false;
            default:
               throw null;
         }
      } else {
         float var8 = var4;
         Iterator var9 = this.da.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s107th542f3omt","0bHlY4CJHovUUvGUU/Id9mNjmKuygEjaz6Ou6rUG34I=",4538935680306862136,-6151837867807109665,9144081371690510855,3823992996854568641>()) {
            case 1022499502:
               while (var9.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1pmj1dunlsz32","wnFdx6oYUUXHPms+vVbb0ZyvJWAI5rbLUtdhw+n7N+8=",7942223780087812799,-843622619808790150,-1001956673858646393,-2731920838372776051>()) {
                     case 1837726925:
                        g var10 = (g)var9.next();
                        float var11 = var10.b();
                        if (var2 >= var8) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1gmt5vin5y340","4ig6GMLUTcclmFAr+WlIgEj+a2l3yFwNujLwsDRwd4k=",-341757541948385307,-6161245676575458320,-1985088765124988459,-1110358823446142147>()) {
                              case 126368077:
                                 if (var2 <= var8 + var11) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3a4w50tcxcdno","nOh97C+0hhHtUYHYSuuGi5KRN9HGXZQq3jmFuubUCU8=",-1069357563850274962,2692119526241062615,-9201862131284146948,2162085371270161936>()) {
                                       case -711408191:
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

                        var8 += var11 + this.fH;
                        switch ((int)com.yiyiaddon.m.b.a<"s1gk33xio2jx7r","4kkgckDitaBzg+bNc38EnK+bdNiVBU+AZwZTc1xSDfc=",3042749094362393769,-3611834609878563868,-944534650278010630,-6011518970222902536>()) {
                           case -2144175854:
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

   public boolean a(float var1, float var2, float var3, float var4, float var5, float var6) {
      if (var2 > var6) {
         switch ((int)com.yiyiaddon.m.b.a<"s1skg20ghba9oa","UvcU4INT/Zj36OegZHHrjb5Z64lcBaXUUp0hMMNHFGc=",1943717207327461347,431468440423094335,-8300147043836545411,-2188932956187650195>()) {
            case -947643316:
               return false;
            default:
               throw null;
         }
      } else {
         float var7 = var4;
         Iterator var8 = this.da.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s26v9zo0kco8y4","hhVgqdpR9xirvaVuzIAWSc3KW7kamxkNPrS/PvpPpkA=",3385539621810671235,1497593707479280987,-5556280133940534424,-692886063993488096>()) {
            case 514012032:
               while (var8.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s4yueh86goovt","peoXvjnibIVO3rtCOa5rPUwHpiH3aDY1F6SOFnjxcf8=",5288823093093444428,-5997554556167261504,-2008213529205275822,-8214356966583319310>()) {
                     case 1953514548:
                        g var9 = (g)var8.next();
                        float var10 = var9.b();
                        if (var2 >= var7) {
                           switch ((int)com.yiyiaddon.m.b.a<"sosuq4lxo097v","eKjXN9yjYJs0IVB0pcSLArBD+JVx1v9WvWdPq5+LE7k=",-780176469949273543,4661760233141040535,-5382983446540869977,8569396921602155128>()) {
                              case 1779273337:
                                 if (var2 <= var7 + var10) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3cjvt3mbom7t8","P3mQsqvo7JeK0/jnSZYQY8g4ytKWu/Qkjo0ltpR1hlw=",-5658389963516836864,8728465520993448059,-8025471467413536620,7814930100282356206>()) {
                                       case 1576155331:
                                          return var9.a(var1, var2, var3, var7, var5);
                                       default:
                                          throw null;
                                    }
                                 }
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        var7 += var10 + this.fH;
                        switch ((int)com.yiyiaddon.m.b.a<"s39qalfbo0zjgz","o+POWQPQMVoT0UI0QVK55LgS/qmrH6DSyKfq7+dM+GA=",3995372251247217195,2528435676195396442,1585187511128924476,-4408770589560871259>()) {
                           case -471136847:
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

   public void F() {
      Iterator var1 = this.da.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3l5zowysfaqdw","0XCTyydkaOQIXnC7jw+ZTpXodz0S3G2xScKQsmf+89Q=",-8884134027309733144,-2191545777769229803,-915816214482026820,164539148882058787>()) {
         case -436727323:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1c9543d3w36zf","hTG9cE9OfREniHmk4iRhh9OQZ9nnmEzyw8kO3Lc8FV8=",-6826670132951708651,-4501162099339478582,-9071021089523404230,2791584069234020400>()) {
                  case 370686608:
                     g var2 = (g)var1.next();
                     var2.F();
                     switch ((int)com.yiyiaddon.m.b.a<"s2jj9prq9j50w6","7hsHEGQFnIzZ+tMN2bkMN1ShrEFrXpU2C8usIm43kPY=",-8519302179597349458,7194941286157400671,6906779738123248386,5865929146192348446>()) {
                        case 1442813386:
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
