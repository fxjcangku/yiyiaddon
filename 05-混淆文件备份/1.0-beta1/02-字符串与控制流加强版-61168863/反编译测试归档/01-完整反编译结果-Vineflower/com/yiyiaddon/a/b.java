package com.yiyiaddon.a;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientSuggestionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class b {
   private static final ArgumentType<String> a = new ArgumentType<String>() {
      public String a(StringReader var1) {
         int var2 = var1.getCursor();
         switch ((int)com.yiyiaddon.m.b.a<"s1eyc2g2q46jzf","hZHW8FDWWNX+KVuowpnyEOT6XMIh4LkTkDujYm4A95E=",-5437610999331331827,2789161565261544230,6097153622145984996,4269042841496043663>()) {
            case -102559046:
               while (var1.canRead()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3csl980bkxifq","+ztqrwYMd6ROsm2gFdpak2mCpl5TuKhlHIMEZp40Dng=",957663521393683020,-87034682832221285,-7510900186129459933,-7219980942728653473>()) {
                     case -934403898:
                        if (var1.peek() == ' ') {
                           return var1.getString().substring(var2, var1.getCursor());
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3a01ez09pf7v3","qR/Jwls/UePLHiNvbw5xcA9cX7tYiTOcbCBjiS/PnZk=",4131625694026865713,-3037717375511825691,-2397278524478768063,1531598384124978934>()) {
                           case 2126352608:
                              var1.skip();
                              switch ((int)com.yiyiaddon.m.b.a<"s3cxbmbl4z18b5","4yd19c7NmYeTDDNU9vxbsKr682DKteHEo2QUQCpdO+Y=",7424672941311552085,2285611908037068401,3516170167253008218,-871512958892788830>()) {
                                 case -911167140:
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

               return var1.getString().substring(var2, var1.getCursor());
            default:
               throw null;
         }
      }
   };
   private static final String e = (String)com.yiyiaddon.m.b.a<"s36vh81rph4s0s","7gjSfAkS1DHgKcuE98Wj/ashYuGJyrMFP/INwHjVtDI=",-7382142527884362677,-5720271749962848332,5599895802641181350,-8081815515613212934>();
   private static final String f = (String)com.yiyiaddon.m.b.a<"sefqxdyc3i2w4","beOVgOmdKVknvqkMznb0ZAa3lVF6IHXgGd6G1Wvu5Xw=",-4829764907760671020,-6091237857294432226,-4715055869865466090,52607627641448222>();
   private static final Logger b = LoggerFactory.getLogger(
      (String)com.yiyiaddon.m.b.a<"s3vqjgps6jpbmy","yhaNlNaTE2neZH4qmI7gpXAElN7pGUsrh/oUU/ynHW0e/w3PNBk1g348qdvUrMUAqgxgWRCJ/VyQAPCBtjg=",1407249093962092726,-5564130409508124189,-6664972435304319725,-3067331723878816500>()
   );

   private b() {
   }

   public static boolean a(String var0) {
      return d.b(var0);
   }

   public static b.a a(String var0) {
      String var1;
      String var2;
      boolean var10000;
      label100: {
         var1 = d.e();
         var2 = d.b(var0.substring(var1.length()));
         if (!var2.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s1ewopg94k2b13","yjR/NTVIPnew5rvUflZX1/gOy3fP07fYBAOevUse7sU=",-1561753287783954913,6090656822074129994,-92570027015656739,-3197052681721788886>()) {
               case 133456318:
                  if (var2.charAt(var2.length() - 1) == ' ') {
                     switch ((int)com.yiyiaddon.m.b.a<"sb9begvcvjg8b","+46dqKxHc+RdG8hj8mz+8pvqmvmp0uSicOyGZZsIQWk=",5953795523991227182,2781736615407824240,9104468760870630785,-8859619917003771317>()) {
                        case -1091059731:
                           var10000 = 1;
                           switch ((int)com.yiyiaddon.m.b.a<"s2hrzxrbmcqwn6","HLpgdldZLCT1h3iT7vmoXGJegu4LK0QH44ewO8EMmWw=",5275478578932096348,8894780063283956538,-8716356298700666438,-3026468736877593679>()) {
                              case 1787377379:
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

         var10000 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s2etepxzsj166n","G+h9g0VYWZAH1zYeLUcAcxXyvVoBJxtkszBnh/Hx4CU=",-6957636233711718469,8314883527034766352,5173892243186309428,-1603558481234794558>()) {
            case 48702285:
               break;
            default:
               throw null;
         }
      }

      boolean var3 = (boolean)var10000;
      String var4 = var2.strip();
      ArrayList var5 = new ArrayList();
      if (var4.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s638q9ysryl1s","LecHoxaieUvHrow6CHI4VO/NNRpjIHjcRCN2k10Ij/8=",4478453114418735371,-3092483363614020239,4926160302235544466,-3217910952929511852>()) {
            case 433377210:
               List var6 = com.yiyiaddon.a.e.b();
               switch ((int)com.yiyiaddon.m.b.a<"s3g08oybqv4d3c","ch7CM3gKV+0SoV3fsvuWrbD+7x0O/ai4stw5XukzBug=",-62196658205612034,4957784843699733866,7263110208416156836,3679331244326687196>()) {
                  case -607506611:
                     return a(var0, var1, var5, var6);
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String[] var7 = var4.split(
            (String)com.yiyiaddon.m.b.a<"s2znkjtod76hwt","/3HZGcjPbjw5r8tLVJDKprIfijZz4zMszGmEstfNBffDBQ==",1105175341785308,-4560861380396525076,1401782998683970880,8422449414797299226>()
         );
         if (var7.length == 1) {
            switch ((int)com.yiyiaddon.m.b.a<"s2sp8e8stjvdxv","+v0g+ePejJGbt9D9a7jJ8kLrerKUah7NU2SdRUpWAec=",-7995823103414693905,1302313683892103969,-7245953341672448066,-3864836174091361314>()) {
               case 392881200:
                  if (!var3) {
                     switch ((int)com.yiyiaddon.m.b.a<"s31uexz80qhskx","Dv20qqUH8w0Xj3KR7kYN4pwpKJySRzi7F09SRcERhxo=",-9053890543328839429,1916232153740073744,-2178013757443955761,5429508695612547353>()) {
                        case 633694137:
                           List var14 = com.yiyiaddon.a.e.b();
                           switch ((int)com.yiyiaddon.m.b.a<"s1nok64qjh5r4v","9GXUbCfOVLf2t/sNo5tgdwAqJ5M/LV4POR2/Ak6nKyg=",8901100059712404347,50773189126707016,920204310440392874,-4128273536987321085>()) {
                              case -47374438:
                                 return a(var0, var1, var5, var14);
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

         com.yiyiaddon.a.a var8 = com.yiyiaddon.a.e.a(var7[0]);
         if (var3) {
            label68:
            switch ((int)com.yiyiaddon.m.b.a<"ssdi4uc45n7ez","j9pEH0HKp+xHpBNcP4tkZ80PnLhOdTZBjEdJ94L2qUI=",-8933323327705548893,6756361204087090467,3843666857704383078,-5032942363267827280>()) {
               case 1057885615:
                  var10000 = var7.length;
                  switch ((int)com.yiyiaddon.m.b.a<"s3sgpkbz6ht442","U1S4n8gpoTnwRWB0/f4dlwWb3UlqBstOo2xrq0Cgixw=",-6113883485400128209,7633232885778048646,-2868613496107921016,5429027508972812565>()) {
                     case -1984945893:
                        break label68;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = var7.length - 1;
            switch ((int)com.yiyiaddon.m.b.a<"s33w6umt9qj9op","25bjUg87sKw8qH11wYAMmIWWoxXxZZFiayUy+7Jpgpw=",-1250646590802435107,-985437863383802865,8835773487629491943,-3043350601960136844>()) {
               case -1699742816:
                  break;
               default:
                  throw null;
            }
         }

         int var9 = var10000;
         if (var8 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s12qxg7epdojnc","L+X36rHv86bCrUjetNV32MyJ3Ft1WYuQmJ8y7pbgp5Q=",-2273472481412827288,-6159775846867157759,3620789525373512571,601042441440047432>()) {
               case -332537289:
                  List var12 = List.of();
                  switch ((int)com.yiyiaddon.m.b.a<"s1n1jn3cupfnn2","BOjPZFEZ8L6P4MmPYhAUUCfVihqnMat/5HiVw1KOH5c=",-2296808578828912498,2169740571724544849,-4322010145415419554,-6213535836402034337>()) {
                     case 216754168:
                        return a(var0, var1, var5, var12);
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            int var10 = 0;
            switch ((int)com.yiyiaddon.m.b.a<"s37oz4rjpyt5qe","xr3bQ7T69RUo/+ZU1TwcNVNJMVsoUUlqgEFMqePiMkg=",-5112649664589832790,-5030586587764712233,-3246729250733209923,1468076596324933437>()) {
               case 16355345:
                  while (var10 < var9) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1uxzg9uw5spyz","M96E5+wTH8rePxorzvoSAZIqU7dyL2R7VBCIfQC6dbs=",-7510037173859335711,-4577499961351188268,-763795152902961607,-604221673684180297>()) {
                        case -1345930859:
                           var5.add(var7[var10]);
                           var10++;
                           switch ((int)com.yiyiaddon.m.b.a<"s1v8botiuiiww","fDeELRykt0wIKBKe5pPVtl61QDMlESXZcv4e5HIeTpw=",-2854114253075286487,7907896960643564326,-803341963790429483,-6254315540794105997>()) {
                              case 63461262:
                                 continue;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  List var13 = a(var8, var8.a(Arrays.copyOfRange(var7, 1, Math.max(1, var9))));
                  switch ((int)com.yiyiaddon.m.b.a<"sbyucu2e2hl2r","l5+2HHqJGusTosdImwU2yBlkdk2hXlj5oorjNaLgPd8=",2513786369223525877,-4825141193802925562,3751922180151414074,7648038072067263715>()) {
                     case -1890832710:
                        return a(var0, var1, var5, var13);
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }
      }
   }

   private static b.a a(String var0, String var1, List<String> var2, List<String> var3) {
      CommandDispatcher var4 = new CommandDispatcher();
      String var10000;
      if (var2.isEmpty()) {
         label31:
         switch ((int)com.yiyiaddon.m.b.a<"s26ngo27g3v8wi","IX5eZlvnsa7jgLaQqbIjpSZoIEBaBrZoy6DK6rmJNyo=",4758667875723817861,9058167151309039570,6482206602036832246,6804459485924641478>()) {
            case 2129359725:
               var10000 = (String)com.yiyiaddon.m.b.a<"s36vh81rph4s0s","7gjSfAkS1DHgKcuE98Wj/ashYuGJyrMFP/INwHjVtDI=",-7382142527884362677,-5720271749962848332,5599895802641181350,-8081815515613212934>();
               switch ((int)com.yiyiaddon.m.b.a<"s2ngi9cpgp4ffa","KDDDBO0Nl5KwYn51qCnqt2MYWFSjZwDYaxufuJLU5eo=",-1783561794272542197,2427973101012326906,5105143823827025858,-3054979664830266431>()) {
                  case -715887652:
                     break label31;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = (String)com.yiyiaddon.m.b.a<"sefqxdyc3i2w4","beOVgOmdKVknvqkMznb0ZAa3lVF6IHXgGd6G1Wvu5Xw=",-4829764907760671020,-6091237857294432226,-4715055869865466090,52607627641448222>();
         switch ((int)com.yiyiaddon.m.b.a<"s1tx44eoddijf6","iP6TgJDPYAcg7DqhpxqLl1DhFoeIGyHDY4r2QD7BCQQ=",-110720852257887648,-1608660032173883028,2144447961945216028,-7097178497967641543>()) {
            case 1357997115:
               break;
            default:
               throw null;
         }
      }

      RequiredArgumentBuilder var5 = a(var10000, var3);
      int var6 = var2.size() - 1;
      switch ((int)com.yiyiaddon.m.b.a<"s3qgdww93du9ob","Wuwacf3UOlkG6N3G/178z16uvLe9uGRRZIFrxqKgcGw=",9009171156532296962,-910124834602734979,-8054467302215975859,7530761376148098257>()) {
         case 1454951430:
            while (var6 >= 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s3271nx6rgb9m4","fSyivoqEVl6OZGNfmXFn57+L8BsC2XjgkxR7gd3XxjU=",1535298821725598654,-1167419842637236870,-1626937225713340752,-74150055822616763>()) {
                  case 308680617:
                     RequiredArgumentBuilder var7 = a((String)var2.get(var6), List.of());
                     var7.then(var5);
                     var5 = var7;
                     var6--;
                     switch ((int)com.yiyiaddon.m.b.a<"s2zkuixjzpftwl","AVB0yRM90Mts4j49E3d/X43XxgwpR8eHh7tDo0yvW+M=",8234669846396435982,568184010076122400,6394434047564353933,7577628641894028755>()) {
                        case 1686029459:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var4.getRoot().addChild(var5.build());
            StringReader var8 = new StringReader(d.b(var0));
            var8.setCursor(var8.getCursor() + var1.length());
            return new b.a(var4, var4.parse(var8, a()));
         default:
            throw null;
      }
   }

   private static RequiredArgumentBuilder<ClientSuggestionProvider, String> a(String var0, List<String> var1) {
      return RequiredArgumentBuilder.<ClientSuggestionProvider, String>argument(var0, a).suggests((var1x, var2) -> a(var2, var1));
   }

   private static CompletableFuture<Suggestions> a(SuggestionsBuilder var0, List<String> var1) {
      String var2 = var0.getRemaining().toLowerCase(Locale.ROOT);
      Iterator var3 = var1.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1da0apsinpjov","DHAOep3KcD8DPfbYWQVKcDNMU25SB+xma0nTGyZd1vw=",-1431719169141183922,6828209027222971818,-2803431097806702014,9014689330069262428>()) {
         case -529141225:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1m4sblrck22s4","F6w8AEfN4IDR3iLbRDIqbRal+ZebU+7GRGb/cX3Jxas=",-1357251068254093381,-8951301127724989510,4790739315498624233,4186204603848387233>()) {
                  case 1038759387:
                     String var4 = (String)var3.next();
                     if (var4 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s36u9lubeujn4x","zJY0OgYsyatTvMNihnwhNHS0zqKoENhiKJPTnsEyI28=",6463921966296984485,-7464097136171103109,2420481360928467613,-2187482851409450449>()) {
                           case -639323734:
                              if (var4.isBlank()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2baagxr3vo1n5","Ocxv/LdvQKJk4ZioHQ7Rch5uoe/jsQGFw568h7ghsng=",8594266149139794251,4651532732340635777,-8470405967729077169,-7409568980692498697>()) {
                                    case 2125487333:
                                       switch ((int)com.yiyiaddon.m.b.a<"s35bgtkmebevao","fKfMrZJ9aS9fsmnYDgaU3FAvJtLN+gIdQK4YgLmLAME=",7257058627153646664,2133804267221894827,1487546225561040825,-119319682202331192>()) {
                                          case -2013972203:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 if (var4.toLowerCase(Locale.ROOT).startsWith(var2)) {
                                    label33:
                                    switch ((int)com.yiyiaddon.m.b.a<"skfroexohmm0l","0aj9f5pv0WtxYFXkp8kdamFRzt4qKlSfxZRu6ZxMurE=",-6705298792268832450,-2709077068491059663,1732942221694761099,-311896979269610629>()) {
                                       case 370471811:
                                          var0.suggest(var4);
                                          switch ((int)com.yiyiaddon.m.b.a<"s215ioqmm8e7qa","65lv/DSmhFH6RsZhW1tl8Ul244JPHsHi3IuvGfStLUg=",3734205958638033310,1605570234180247721,-3257343220356698043,-1921142830827018917>()) {
                                             case 89471683:
                                                break label33;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s77t284jmxkx3","/yWTg2k+zWGVV9v4ednTjT/7XlX/Ur0hOOF75YlV+IE=",7993204836836784003,4279462217490368521,-1655930955825819239,1982683673105592556>()) {
                                    case -14766096:
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

            return var0.buildFuture();
         default:
            throw null;
      }
   }

   private static List<String> a(com.yiyiaddon.a.a var0, c var1) {
      try {
         List var2 = var0.a(var1);
         return var2 == null ? List.of() : var2;
      } catch (Throwable var3) {
         b.error(
            (String)com.yiyiaddon.m.b.a<"s3vajzrcdjzfxz","8ZClwzYRkLaINrUIxtJBdLNvZt1bhnv8K6sQoiqZ8/NhTo8nXsdqpQJTPtN9VWAc",3262378835857416721,-3216923474778306898,-1869355344930399098,175621684292629623>(),
            var0.a(),
            var3
         );
         return List.of();
      }
   }

   private static ClientSuggestionProvider a() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s38lorkeds94w9","YVqgFSL2D82mKc0e3eqRAzSHdYbZAr8N173EmvSmYPo=",-7929320577445597898,-1985973795909944359,-7420628811984206105,4211872208424404399>()) {
            case 543240076:
               if (var0.getConnection() != null) {
                  return var0.getConnection().getSuggestionsProvider();
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ql7i8rnnzld8","Z+BQ/9/vLtyxJNo17dh1kCFkRxtU8TJ6yrPVxaMXIAI=",8896493685833229635,-8825356578807764633,2663073553672122411,-3651981448819536414>()) {
                     case -1529371703:
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

   public record a(CommandDispatcher<ClientSuggestionProvider> a, ParseResults<ClientSuggestionProvider> a) {
   }
}
