package com.yiyiaddon.e.d.c;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.scores.DisplaySlot;
import net.minecraft.world.scores.Objective;
import net.minecraft.world.scores.PlayerScoreEntry;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;

public final class k {
   private static final int cO = 15;
   private static final Pattern c = Pattern.compile(
      (String)com.yiyiaddon.m.b.a<"s30aictonmk8sz","z3ycU4vo6PgLNnaovZc0x+50Qb9lC755y990A/zgCww9zinQaGWYKtPqgkc9Cbs6Q0NBjyJNGEwdQPpFJvJE0TUI3Hmr+iY450u+grrl/Apds/Np76ZgipZogyl/9UeTirycbHlHrEpjCJt/kFO0ntJza2C+XHhacBFSRo3Tkb9L4KgqHp21vBAFT6vVDjIf2MlmxnP865Q3IDF7xvqeG/bPwM4/2WUk63q9HndYkDRsy1lTFU/egsUbwIxIooA53C0wV4qi9WNX/mRMUKOl6WD8RJw0BdL+K0X/m9nlXdjDbe4TYgxhS7HI9syH4DE3LihFpKE8TryMBBSw9E6pnY/j",3894899835758411003,5942800450519691654,-396178539377762279,7186264934768579737>(),
      2
   );
   private static final Pattern d = Pattern.compile(
      (String)com.yiyiaddon.m.b.a<"s3t4olihrkg44e","6KKXc4dxUuJX4axih4MjHEDtCCyXCyNW/KmbjIv7R4hanvG7fQ2Ae0YIV/OPq4mBliwxnOnfc5IiD2pV5Wt9FRQb6AvN7JQibB/VOlk7TXpEN3Sa9Wi4u5AgdvXBFnAj4pl6aZ0g8of3zvKR3Hc=",834288672585278290,8046370962242638322,9183600043242944889,-3545945649256147297>()
   );
   private static final Pattern e = Pattern.compile(
      (String)com.yiyiaddon.m.b.a<"s7n9fazsz3tx3","vvQfIrhOhyuJAEaCpyAdBNczjKHO3eI6nfwdmeZ6lLIrmBT20DK5PeEtg0YPEFG3XW247ebBMpG54mpJRKodKIlZVItdJSeONOtkHunMSOY91gExmI5ynsURyzH+hJMvKdgsFMIR0aZr0DeV6x/7twF99+z+Pv9Rb7wxILMtu1B+kQ4kHiJnJIfbrhb+H7tmnS6dEwTrMrDsJKJJuDB1RbUEt/Cq11gFaFY=",-102268587162812950,-7234274516653224478,-4519184860522325474,-1367584955374738269>()
   );

   private k() {
   }

   public static String k(String var0) {
      Pattern var10000 = e;
      Pattern var10001 = d;
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3pct8b0ahhy8x","He2qXaxTkr9SJcmKg9w5Nw+xgDPeT2bBbe/5pz2VVCk=",5759758702276626873,3363737442538257661,3150237158098223471,8487021414260740226>()) {
            case 1242265772:
               String var10002 = (String)com.yiyiaddon.m.b.a<"s1zc6omjy84am5","xn8LBbEl7ccJWkiPaC8ucJDeAFYZMUvvB9EjLQ==",-4049623334044063867,-8632200097211554119,7813040109518622311,-866598901961767831>();
               switch ((int)com.yiyiaddon.m.b.a<"s3vxih9jq09ak4","XNSFFXUR7olJFNyUTGHBrwz38gnv2ek1xx3IjOloUz0=",1837407260313926794,-1996487580899278012,8477319180835841163,1823610028108534802>()) {
                  case 2082634507:
                     return var10000.matcher(
                           var10001.matcher(Normalizer.normalize(var10002, Form.NFKC))
                              .replaceAll(
                                 (String)com.yiyiaddon.m.b.a<"s1zc6omjy84am5","xn8LBbEl7ccJWkiPaC8ucJDeAFYZMUvvB9EjLQ==",-4049623334044063867,-8632200097211554119,7813040109518622311,-866598901961767831>()
                              )
                        )
                        .replaceAll(
                           (String)com.yiyiaddon.m.b.a<"s1zc6omjy84am5","xn8LBbEl7ccJWkiPaC8ucJDeAFYZMUvvB9EjLQ==",-4049623334044063867,-8632200097211554119,7813040109518622311,-866598901961767831>()
                        )
                        .replaceAll(
                           (String)com.yiyiaddon.m.b.a<"s31bswmi1izu8p","4tGL1pRlmfSir59cgAog/cLpqkXivt2/lMb2RKz+uOtIUw==",3145956352042038656,-6012341457355007784,-4138232805784859156,6564194113974639040>(),
                           (String)com.yiyiaddon.m.b.a<"s1kpivib8y086","u7Xr35s3d9GSwF6ZreNy1fcOGY4U0JTYMuyXDrS/",4280307191866064232,-795107735592064005,8225734198608359760,-5342128318397679334>()
                        )
                        .trim();
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"sxm35red2symt","CT6uzs1Z6GmRwXrjFP7SkYkRyrOvesXrcmDBoYLk22U=",-6425780436929984240,1194074161081340755,4981934311985111031,-6628635145928099362>()) {
            case 275397677:
               return var10000.matcher(
                     var10001.matcher(Normalizer.normalize(var0, Form.NFKC))
                        .replaceAll(
                           (String)com.yiyiaddon.m.b.a<"s1zc6omjy84am5","xn8LBbEl7ccJWkiPaC8ucJDeAFYZMUvvB9EjLQ==",-4049623334044063867,-8632200097211554119,7813040109518622311,-866598901961767831>()
                        )
                  )
                  .replaceAll(
                     (String)com.yiyiaddon.m.b.a<"s1zc6omjy84am5","xn8LBbEl7ccJWkiPaC8ucJDeAFYZMUvvB9EjLQ==",-4049623334044063867,-8632200097211554119,7813040109518622311,-866598901961767831>()
                  )
                  .replaceAll(
                     (String)com.yiyiaddon.m.b.a<"s31bswmi1izu8p","4tGL1pRlmfSir59cgAog/cLpqkXivt2/lMb2RKz+uOtIUw==",3145956352042038656,-6012341457355007784,-4138232805784859156,6564194113974639040>(),
                     (String)com.yiyiaddon.m.b.a<"s1kpivib8y086","u7Xr35s3d9GSwF6ZreNy1fcOGY4U0JTYMuyXDrS/",4280307191866064232,-795107735592064005,8225734198608359760,-5342128318397679334>()
                  )
                  .trim();
            default:
               throw null;
         }
      }
   }

   public static String B(String var0) {
      return k(var0)
         .replaceAll(
            (String)com.yiyiaddon.m.b.a<"spbgvip9j158y","48cNfqut9uLAXjprHPFNfamzUPwdNLF2uGw/OL+2B1J+zBquJIkiTdK470uuHqtV",5936591980343227542,26262388888449444,4246819782829915718,-8904009507479806597>(),
            (String)com.yiyiaddon.m.b.a<"s1zc6omjy84am5","xn8LBbEl7ccJWkiPaC8ucJDeAFYZMUvvB9EjLQ==",-4049623334044063867,-8632200097211554119,7813040109518622311,-866598901961767831>()
         )
         .toLowerCase(Locale.ROOT);
   }

   public static boolean a(ItemStack var0, String var1) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"splbvwwnkwusu","ndkskexa42p9k8b7KX+Dl7hVv4sBY6Y7j88BiRdyRE8=",8512144091357766986,4164205393349979549,-6097523572752935417,-155460677497298142>()) {
            case -1208075250:
               if (!var0.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1co9mp6ww6h8r","YjvMzajXFuBL9hEXu7P1+BWVQs4FOmeDv2mmVls4DRs=",-6774500368321518077,7823582986502154038,2771680048750728160,1484702575017162429>()) {
                     case -635409337:
                        if (var1 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2u431pounq4by","ClKCP16JDAgpdccrLGQApklD1yDLhAduTFkmJ0oNcPw=",4370527967860295684,5708775893611925251,2858757029783481570,122065753003965055>()) {
                              case -773639840:
                                 if (!var1.isBlank()) {
                                    String var2 = B(var1);
                                    if (var2.isEmpty()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"stcawsbhyue0","Rgrg1F5fbT+IWYlhG2vfiqZV8pGFg8R0QxIJJUmq01E=",-4529947130742784461,8521930200479636106,-6367915571269300223,8383482608978770226>()) {
                                          case -2043176077:
                                             return false;
                                          default:
                                             throw null;
                                       }
                                    }

                                    if (B(var0.getHoverName().getString()).contains(var2)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s6nelepufzi48","zDrHxRH6IM8uwhplc+gQyPjgv74F1kXwLCWRMimY5I0=",6599682261930473784,9152176596202510598,6464887690751068308,8566157211530825160>()) {
                                          case -1263913501:
                                             return true;
                                          default:
                                             throw null;
                                       }
                                    }

                                    ItemLore var3 = var0.get(DataComponents.LORE);
                                    if (var3 == null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2fcvx1zak2a3k","vDtHQaePP3ZOzwQoaCe6o6aRR7L1IIw9mLYk7VtcZ08=",-3381206757264559856,1807234471194516113,5730736694879556825,-5883789911118086611>()) {
                                          case 963962691:
                                             return false;
                                          default:
                                             throw null;
                                       }
                                    }

                                    Iterator var4 = var3.lines().iterator();
                                    switch ((int)com.yiyiaddon.m.b.a<"s1g254lq4ylxgi","+MdcLGKGRpeziuypqcFwdN7maw8pqoO1uyPTXO0UUtU=",-1256648452347538115,-4896849421233498196,-8007925348709886867,1293800336897813086>()) {
                                       case 1152295858:
                                          while (var4.hasNext()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s19d3x5ptihmng","HxQRUrvDjTAlO0R5HyTLX6mgvAlDI9b9dzOcmAwv8+8=",6448024403112050371,-5990379613095945864,-7135967340572797186,5074518575703596288>()) {
                                                case 721864347:
                                                   Component var5 = (Component)var4.next();
                                                   if (B(var5.getString()).contains(var2)) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2t3cp0cxwv6us","ak7NFHifxxOUy/wSzWO0sZmdpKfV3NbpTDBQgCbWcsI=",-9199738142768906419,8083425843251906813,-4975754524925679297,1927677656898520458>()) {
                                                         case -221326461:
                                                            return true;
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   switch ((int)com.yiyiaddon.m.b.a<"s4kc9dc38duyn","HQxwGunow/J15WFBt13tiz6seVjIbBIZXpdxF+HOTeU=",-5238798314745204783,-2722869633615203651,7058670516901562506,6008287030131079308>()) {
                                                      case 1210712314:
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

                                 switch ((int)com.yiyiaddon.m.b.a<"s33fytysysubkr","PqOh9Aw9YXDPg71eA++OnskKH/IrnqsIWUiVJhqUrP0=",2471852490469270284,-254688659586923053,-6216725468095019747,4425458495641626366>()) {
                                    case -636581228:
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
               break;
            default:
               throw null;
         }
      }

      return false;
   }

   public static boolean a(ItemStack var0, List<String> var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2y216qxio368t","MaaRKgWMelRafCNLq7dhcZrTvyvaqMa5YjDC3OGyKLo=",5259666437487782605,-326197443157701746,7300166108628337495,5217063028529669400>()) {
            case -1279165187:
               return false;
            default:
               throw null;
         }
      } else {
         Iterator var2 = var1.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s31ii52liig3xb","aozLJVe0ZJlugM8aF2f9qVzYRvFGVXAxFfbYjCxGfY4=",6689881440207711251,1362067222554527275,-5192134118326375967,4670436625777784616>()) {
            case 635831219:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s22b12r1w6wise","U4bEwcueEsq2+/kUUJpfopVzdFDiWbK7Y1f0UkPNHHc=",-6907150828274834691,4857736043259860554,4819417291787750753,8025053286499220553>()) {
                     case 976188933:
                        String var3 = (String)var2.next();
                        if (a(var0, var3)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3bx43iraxez1n","Faj6ChuaPTAsl6LC/StwTHqAn/e3+KyBimjNG/urdwM=",1800878978177246610,-1732161782920812284,-252948636573158761,-7518581583726203164>()) {
                              case -1955595686:
                                 return true;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"srakn5q5eequo","cPR0jnHSpV5Ilw0Q9x6nkqlQCDY743IvP19YCu0dD0w=",9063381722605736912,8696475228786651894,2461756455540507504,-2700471148203139130>()) {
                           case -867860451:
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

   public static String a(Minecraft var0, List<String> var1) {
      List var2 = a(var0);
      if (!var2.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s35hsiu9u8hm6n","C+oSEGe4FHWiF8qWH2XmstdFm2WMigzWrokNgONtCT8=",-7077832228876254575,-2211300356310072238,7598483575021806250,7159826473100817115>()) {
            case 85803401:
               if (var1 != null) {
                  Iterator var3 = var1.iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s3crvkzs4xd0ub","/xaSRCIh5pyj7a9XqlMiTJH1ViJd2hE2NwiVIEooWOw=",5263332193750606614,2270756200623201038,-6712888066449614990,-7788770624833253463>()) {
                     case 197804327:
                        while (var3.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s14kvgsino96vv","+PCpSNIA3bLQbEk7lwMN7qsZYDxKCSgtMA9O734q8/0=",1005242142940889686,-4459729505927386091,1617328043247092169,-4388658965862111112>()) {
                              case -1242567353:
                                 String var4 = (String)var3.next();
                                 String var5 = B(var4);
                                 if (var5.isEmpty()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1m4e25cuntc9v","da3KOcJmpkEmgs15WE9mX3pxg5lgyyny5elbplbSEg0=",2641685321355251250,-7032531145557236680,-1547947414316195162,-1328878878335261550>()) {
                                       case 234366374:
                                          switch ((int)com.yiyiaddon.m.b.a<"s2yhmhdl3hti64","8jd15zEw5MKPCgvc19apL29WuleHq4fXzHS+slCinyc=",4840924059946315621,5955962081567726192,-8591831845031630746,1909487235024021956>()) {
                                             case -732838325:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    Iterator var6 = var2.iterator();
                                    switch ((int)com.yiyiaddon.m.b.a<"s2ci9j20mgxgyd","n8Pxx0Sjdl90U8CI4eQ3/V6dPZQX0EXMfozWV0Dmhnk=",-774219646736377426,6426229435789204986,-6354222602677192969,-8529319835977064234>()) {
                                       case -49693662:
                                          while (var6.hasNext()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s36ue6evcz5pu0","U7dDhDAsao6HoA7JhLdKILdsgEGOKVvM45tdH+wdgHo=",-8123338583663629138,-5629422162943683944,-8556520916219701659,-7990208275218925474>()) {
                                                case 461561668:
                                                   String var7 = (String)var6.next();
                                                   if (B(var7).contains(var5)) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3eh7kvwic4efl","h5IEL1BdxXWzJJ5fwWbEu8OJzLRivZLPlOChocoachM=",-339882034038595846,2460395974400961747,-7080475420093206378,-486875035535014509>()) {
                                                         case -424158313:
                                                            return var4.trim();
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   switch ((int)com.yiyiaddon.m.b.a<"s1oxouhlve6pk3","mc7rAQVN/JAW+xMvgLo8TFcoiuLdMnHdupiCYC2aZYs=",-3410609631049155349,-536375903892584738,-7563629698332990892,2000541644038189569>()) {
                                                      case -1223141662:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s3gy24q7r0oqsx","ZZ9DSeBeK5kp9sRjK1BiRB0eitKZr9Oq2ww29AoCsoA=",-3244377597932647955,719845527289538000,-8384789141281981618,-4630887661062594125>()) {
                                             case 1648748822:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return null;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s267auyfhdtro3","oqjGNaYUBRknp2JzGo4csbDimOKbq0ujqd98gurD1Lk=",-1559481839419380062,3929132866971222980,8483953817632743524,-6280157389217467595>()) {
                     case 598563935:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   public static String b(Minecraft var0, List<String> var1) {
      List var2 = a(var0);
      if (!var2.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s399qww61adc47","0chvQa3ZsYKSj6KexaL9nVmjlxZ2U9m0dQPnpgVEwf8=",9134455421254809881,1705510779835813210,-8129752048989715532,-3872463646530561576>()) {
            case 921159938:
               if (var1 != null) {
                  Iterator var3 = var1.iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s1vk1wph1j0prl","FxPNEzGZ9lRNuLkfMBIt8259TaEqYu5+YTyRPqr5HXM=",110811722570925182,2675184504800501546,6846928849491094399,-3173000138188631934>()) {
                     case 970054119:
                        while (var3.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s11lqh4soaxr57","x+6ewErbLI+UBQ3RGKLUaa6KKTd7WMDHTlDIjBbYDE0=",-6153542399779436510,7019384614021562232,2072417275430315528,-4771710227654680169>()) {
                              case 1011759936:
                                 String var4 = (String)var3.next();
                                 String var5 = k(var4).toLowerCase(Locale.ROOT);
                                 if (var5.isEmpty()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2m2kt061b48pw","n8ZstLHBsWz5yO3VylPXkndctoOwMqgCV9aMj1c7fqY=",-3658630462854937609,-4017057334090549341,-4174213372491253240,9026896068353778239>()) {
                                       case 792388389:
                                          switch ((int)com.yiyiaddon.m.b.a<"slivzl6cycdmz","dg8TKabPyNony6TJKytZ35WXQxD10/pdtVf2Alm7ESE=",-5699310426401319250,-2602408876517440253,5877108981802439146,4525136893460849205>()) {
                                             case -1990928567:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    Iterator var6 = var2.iterator();
                                    switch ((int)com.yiyiaddon.m.b.a<"s1ivs3k7pz06wn","YoADWfnw+Cex0e9QTsDAnqAGn3lsOBr44Ys+7b/qnHo=",-7069900241165431533,-3861376105568998487,-104597792412425346,-19465719156420671>()) {
                                       case 1272186870:
                                          while (var6.hasNext()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"sbn0xcwcj0js","D07iw76XF9BrraOHRHDchkX/rqob11wVQwgLfMzUKKE=",5394825487865485796,8411650015055998708,4776218549032381908,7693630930049984695>()) {
                                                case 778773203:
                                                   String var7 = (String)var6.next();
                                                   if (k(var7).toLowerCase(Locale.ROOT).contains(var5)) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3t6nwir6rj2hq","o40myKMaLiWqmNBZKrgu54PiymYema4Ue034gfsGYqY=",-4793771788479397992,-7420038754709283809,-5094340619270024360,-7487980487237631992>()) {
                                                         case -2080651911:
                                                            return var4.trim();
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   switch ((int)com.yiyiaddon.m.b.a<"s1316um276sa3u","ZfkTkdzaJI4U713bOUx7dFSlVbRCRvJ/0fuISmlz9RI=",-5129596068318305611,-3277256766177114496,-3755381363262525262,-4298495414225984791>()) {
                                                      case 164837238:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"sntgns3vby9a1","ua3D+GUyB7Nx4fRBEczS7gG7TmgPdpm+3OWByIsX9Ps=",4662923650722855078,-3746669066377310488,-1627709315426943907,-6079765203701389025>()) {
                                             case 1798043222:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return null;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sagl001ksos58","ASB4FcjCzzK6cEnXbjEslf4qD4fcBkegI1TA7xjg5LY=",-785003898719603899,8668744010263792214,-8439375170676313661,4029646214935294884>()) {
                     case 849474770:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   public static List<String> a(Minecraft var0) {
      ClientLevel var10000;
      if (var0 == null) {
         label29:
         switch ((int)com.yiyiaddon.m.b.a<"s328dbqksjfvnx","ZGihtd44woGEPKu/9XMiFzzJPfIWO67mKEpt7i1hT5o=",-2074559446662877560,-7255147282354927872,-3827560897586569433,-6417027655970902069>()) {
            case 180401099:
               var10000 = null;
               switch ((int)com.yiyiaddon.m.b.a<"s1e9t23sck5sl9","hHMh4/3I26IDpsxqld5w5uaL/VZR5a4N2Zt0HKz7JZY=",952370554736339960,-6736521707728667796,-1019268390443926891,-6441125790094200417>()) {
                  case 1831824565:
                     break label29;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var0.level;
         switch ((int)com.yiyiaddon.m.b.a<"scooz0yog1hsf","8eywyjWzeDqPrZhli9bALsxPoW5iZyKaO3Tjyqjj1lI=",-4474636863657219395,5260813784295327722,8528882190106914580,6308119568543652949>()) {
            case -997023864:
               break;
            default:
               throw null;
         }
      }

      ClientLevel var1 = var10000;
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1kqq43lqeq2sa","qUV7HYTJrQ1nRRlV3uB9oiSe5p9fUVKMuwNvlONxFHM=",1116706398533640254,-962562898324230548,-4593958413671959189,2371637018915833310>()) {
            case -462506736:
               return List.of();
            default:
               throw null;
         }
      } else {
         Scoreboard var2 = var1.getScoreboard();
         Objective var3 = var2.getDisplayObjective(DisplaySlot.SIDEBAR);
         if (var3 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3a3npyx5ok1i7","cfnZ8d+vLttwZpUPqNCWLziBVTYn3cmqfDWqWHGq7cA=",4371411092695542028,-5242607355856184718,2472732149439198601,5898074525178604528>()) {
               case 1002321792:
                  return List.of();
               default:
                  throw null;
            }
         } else {
            ArrayList var4 = new ArrayList();
            var4.add(var3.getDisplayName().getString());
            var2.listPlayerScores(var3)
               .stream()
               .filter(
                  var0x -> {
                     if (!var0x.isHidden()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2ziy5lzw3cug7","2HGK0c9WO0bfEKSuB443sJNB3Dc1SucRZglLVM0Gh+c=",8908151501278461318,2729573026594490443,-222584754930713717,4367707003929401784>()) {
                           case 731735313:
                              switch ((int)com.yiyiaddon.m.b.a<"s3hbskvnr81nwt","yqS+ytmmfhESzam8uucbeS0M5WuGxwCH6uW1D4nQx8Y=",-2035473383084836899,-5445926925423968328,2121200441619159064,8705341640725625761>()) {
                                 case -632378994:
                                    return true;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        switch ((int)com.yiyiaddon.m.b.a<"s1iqvwr3d429tn","JPK/rEa7gyuB1yDPIJMItdCZL4y+QI5mjzTLCEDUsAI=",-3420017450209136908,7459338052435963627,246483636768581208,6152389363459106114>()) {
                           case 1115347839:
                              return false;
                           default:
                              throw null;
                        }
                     }
                  }
               )
               .limit(15L)
               .map(var1x -> PlayerTeam.formatNameForTeam(var2.getPlayersTeam(var1x.owner()), Component.literal(var1x.owner())).getString())
               .forEach(var4::add);
            return var4;
         }
      }
   }

   public static String c(Minecraft var0) {
      ClientLevel var10000;
      if (var0 == null) {
         label36:
         switch ((int)com.yiyiaddon.m.b.a<"s2iaqistcb7yv8","Ec1wH+Av5S59bLISFf0SEJ6PLQvgkUlTBglqCQFtxSc=",-8058125415447751929,-4596188637741750952,-5904138247591826912,7939328061657316078>()) {
            case -1225513122:
               var10000 = null;
               switch ((int)com.yiyiaddon.m.b.a<"sadrwzlnz6s07","vJ3zDfLBLXI1QQg+dzTt4Wq0FGzuAXsgKNcqWnZng4U=",2395700858018168779,-6496473210486068690,-4737366475369618843,-2965495343113997462>()) {
                  case 1994109123:
                     break label36;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var0.level;
         switch ((int)com.yiyiaddon.m.b.a<"s1crwbtb9byxrh","SGBruT7oo9itWsqMe6EB2YyyDTLdYxbhcRP1Dhzie8k=",8715771175238560274,-3779770437535678354,-341376966461176681,-4889305524474058931>()) {
            case 316231890:
               break;
            default:
               throw null;
         }
      }

      ClientLevel var1 = var10000;
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s15q72cnixoh12","p+SBQxT10B287lvkrzX1ECK/TaZUzUWrL9e7URC1z2E=",-7626545295949494168,-6374834226181079004,292348129016482404,-34118859836545455>()) {
            case 2103820330:
               return null;
            default:
               throw null;
         }
      } else {
         Scoreboard var2 = var1.getScoreboard();
         Objective var3 = var2.getDisplayObjective(DisplaySlot.SIDEBAR);
         if (var3 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2rojdilye2ved","A8J+IMLBoqvinjawht92RrYW5M09kT2JVNVJ1aQqt8A=",8435615652718318032,1025863793681109928,-715122757488377354,155538138019069984>()) {
               case -1967560055:
                  return null;
               default:
                  throw null;
            }
         } else {
            String var4 = C(var3.getDisplayName().getString());
            if (var4 != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s3ohci4l4njoq4","YRDityzfLh2gFeX4HoEU1O0mKmtAdqUT/u8coR9lIc4=",4703567083214367634,1114976166504358095,3869511581037946995,-414708048071957057>()) {
                  case -739583567:
                     return var4;
                  default:
                     throw null;
               }
            } else {
               return var2.listPlayerScores(var3)
                  .stream()
                  .filter(
                     var0x -> {
                        if (!var0x.isHidden()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2fbyk5hngcuft","HrUWm/0zgqBW7mKSNEDzK6I2g7lhFXV487es1zCAH+w=",7613740112016062018,5788103111824831677,3492732714649870351,6044212182607903748>()) {
                              case 2076791861:
                                 switch ((int)com.yiyiaddon.m.b.a<"srlgmx31crba8","Ne73NXt2G2rCO3MKLYPG7nj3SqkU9KCse9DOEyGSJY4=",-4693678499093871101,9181058457168097270,-5800621600116425797,-2605160730650283664>()) {
                                    case 338908727:
                                       return true;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           switch ((int)com.yiyiaddon.m.b.a<"s3dbzds4msjd1x","U5hcLHzDprFfm3RCO97DPgZnJjkBU7DUagNbmONZ4c8=",1564813627199828995,8643343619810113089,-1042407232873703222,-1056454671811168443>()) {
                              case -1264027745:
                                 return false;
                              default:
                                 throw null;
                           }
                        }
                     }
                  )
                  .sorted(Comparator.comparingInt(PlayerScoreEntry::value).reversed())
                  .limit(15L)
                  .map(var1x -> PlayerTeam.formatNameForTeam(var2.getPlayersTeam(var1x.owner()), Component.literal(var1x.owner())).getString())
                  .map(k::C)
                  .filter(
                     var0x -> {
                        if (var0x != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"sfimd13dhb70z","qi8CvBAwkIAQTXJD5MFkUERTh5wVIGeVHJzUEDcuHA4=",3612197551721103645,8124501868712883528,9101918614757680245,465197111828908261>()) {
                              case 1835117742:
                                 switch ((int)com.yiyiaddon.m.b.a<"s17ssplvn4hyfn","4Qo+m/HoAd44/PEnNyHRkfb83j/iEawBYjZ/zyE7aEM=",4838291772451087807,9022296062158836583,7043923317259925135,-5882796307656230179>()) {
                                    case 1561470006:
                                       return true;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           switch ((int)com.yiyiaddon.m.b.a<"s15iv1cdcynfl0","y31zzr5QCohiQ/y+OomqjCji+EfprGQHhTRHUv56vm8=",-5651985612269419115,4219536184424853452,-4633695669095923760,6214827272428057988>()) {
                              case 2088536986:
                                 return false;
                              default:
                                 throw null;
                           }
                        }
                     }
                  )
                  .findFirst()
                  .orElse(null);
            }
         }
      }
   }

   public static String C(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"skr79tquuxal3","5OcipAUeDT2twmpSifguuaO/MfJo8AsRb41JbntIK1Q=",9077638763314939668,7870326372829000339,1460602117353369689,2157431372579265263>()) {
            case -999142986:
               if (!var0.isBlank()) {
                  String var1 = Normalizer.normalize(var0, Form.NFKC)
                     .replaceAll(
                        (String)com.yiyiaddon.m.b.a<"s2b48hmfu7ugy1","o2hoQt6cM1+klz1fnHJnnMKM55BxsaMRaTw3r7igzABVdPShNE5icImRg48igDURswR/1DtckxnEaAcLp1E=",8451552927913487080,8515349952570198934,-778674594751442101,1418719418294364281>(),
                        (String)com.yiyiaddon.m.b.a<"s1zc6omjy84am5","xn8LBbEl7ccJWkiPaC8ucJDeAFYZMUvvB9EjLQ==",-4049623334044063867,-8632200097211554119,7813040109518622311,-866598901961767831>()
                     )
                     .replaceAll(
                        (String)com.yiyiaddon.m.b.a<"sc6snas25l7ap","yCbSRRrQ7P0pZXNsRSml46NXdA4zVR3aRt1dX9vri7Iu84byOGZbrT1o3avMAEFBLMbhKRbWg7VICpGdpwtclXgexVYLZkLBvdKzVFmiDwenAw==",-4161024017856988308,8177939396439660629,1178880295184407726,-8585687402207235754>(),
                        (String)com.yiyiaddon.m.b.a<"s1zc6omjy84am5","xn8LBbEl7ccJWkiPaC8ucJDeAFYZMUvvB9EjLQ==",-4049623334044063867,-8632200097211554119,7813040109518622311,-866598901961767831>()
                     )
                     .replaceAll(
                        (String)com.yiyiaddon.m.b.a<"s31bswmi1izu8p","4tGL1pRlmfSir59cgAog/cLpqkXivt2/lMb2RKz+uOtIUw==",3145956352042038656,-6012341457355007784,-4138232805784859156,6564194113974639040>(),
                        (String)com.yiyiaddon.m.b.a<"s1kpivib8y086","u7Xr35s3d9GSwF6ZreNy1fcOGY4U0JTYMuyXDrS/",4280307191866064232,-795107735592064005,8225734198608359760,-5342128318397679334>()
                     )
                     .trim();
                  Matcher var2 = c.matcher(var1);
                  if (!var2.find()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3dw2dlgn3fchh","Jn4jvaf3anbtCsSBy4nPSx8kzZie0GlaD8MyyldKpfE=",3273476516754948320,3653254380153325263,7423383155070455550,223521058009975465>()) {
                        case 104145263:
                           return null;
                        default:
                           throw null;
                     }
                  } else {
                     String var10000;
                     if (var2.group(1) != null) {
                        label46:
                        switch ((int)com.yiyiaddon.m.b.a<"s1bt0wzb8wafii","m8Frw0WlJ7ZTBjR9Fh+prU4niENWOtEe4pARtxLj6kA=",-3908006268656214201,-3244717995109871619,8785818185421484154,-8927013075143140641>()) {
                           case 1822086898:
                              var10000 = var2.group(1);
                              switch ((int)com.yiyiaddon.m.b.a<"s1hcbqzibdvzjk","yrluYpGDlbFYga2mtKEFMROYHDNJGGv9JqbHiGnNrAU=",2892229840563991056,8203723517369422486,-8131033432478972870,1017638482985540382>()) {
                                 case -383461573:
                                    break label46;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10000 = var2.group(2);
                        switch ((int)com.yiyiaddon.m.b.a<"s3lsk8dejv9p0g","b6h8OP3BzZ1QQTESFSa9k5bmkWLPxXWqn90V+QrZVjM=",5417857564626534744,3316983504171358108,-1708118391474515126,6977339141644402539>()) {
                           case 527662223:
                              break;
                           default:
                              throw null;
                        }
                     }

                     String var3 = var10000;
                     if (var3 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s19i0va6a5owux","qzvY1rZfXM52/Xnin+Wg6Zr7X76UdY7+urdUcDxlBtc=",-6912245351037323975,3377326740410976033,5796840631232972309,8732549543520261440>()) {
                           case -1118440538:
                              if (!var3.equalsIgnoreCase(
                                 (String)com.yiyiaddon.m.b.a<"s3u5hs6ts17vcp","2FZeF0YvEhkOKAkSLnzw85G0JCK6a1xlMozByBkWu9k=",-3095754592289077394,2605096375953092739,6946223676895097913,9129426952729380117>()
                              )) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1uv83pd26gp44","bCQj+Pf0eKf2UwSyP5o9SsLd2AEGSKqB6d5QRQXxSoI=",957654305094924845,4195540427681540048,-527138244192185060,6483394705003162715>()) {
                                    case -1617625173:
                                       if (!var3.equalsIgnoreCase(
                                          (String)com.yiyiaddon.m.b.a<"sgw3fn2oiigb9","i65mgdUUnO90PX7Oei8QuC2SkqnhUXcg+46zh4AVUHUUM0cyFrM=",-1743340791496349267,-1228318354435673657,-8683888690845391357,1910593802287756528>()
                                       )) {
                                          return var3;
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s2s0z6xt6v3qgm","3EP4AtRqDm4gUC6NK9mCCd/9u9wz6qhtKijQeXj3BRs=",-5684884846997378524,-6937889788543152021,-5771794051737731726,-4873095292911469060>()) {
                                          case 711028726:
                                             return null;
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

                     return null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1pi5bq5fcn78u","4HispvHyBX0xAI61zudf1hbjHJLfp77z28gOp8UAQHo=",-8508749959622904915,7849531232137391976,-7732938126257039908,2868992126572723135>()) {
                     case -908863357:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }
}
