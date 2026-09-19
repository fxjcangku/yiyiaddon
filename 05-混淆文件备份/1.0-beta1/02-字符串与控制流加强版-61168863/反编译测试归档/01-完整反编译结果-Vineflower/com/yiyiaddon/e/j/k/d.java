package com.yiyiaddon.e.j.k;

import com.yiyiaddon.l.h.g;
import io.github.humbleui.skija.Canvas;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public final class d {
   private static final String nm = (String)com.yiyiaddon.m.b.a<"s2a1qk8c4413xm","gt/EGRKZBP7uszdZ0wnXo1dj3dSMUVNsqWEvOJRg5kZwXvjdofC4NQgYXxjohUVc",-6386109967998152622,2810297650953284819,5916711290654650794,-2488207354227627503>();
   private static final String nn = (String)com.yiyiaddon.m.b.a<"s2vaef6q53edp","XE5qErH2+aphOp6cXKzFr4ocLKU8OoDo35P5/YgH5tQ/0+4ZvQJyBbKB3WNVwjrcoNc=",-7949897812027263687,-4135807422431363897,-6363130922807491494,-7996224446144855175>();
   private static final String no = (String)com.yiyiaddon.m.b.a<"s2g7cl2pnynyin","+sUDQ4Meh5J845DWdKiWj+8UAjagpV870lscOyB1B6QIP5X53AkN7vxrkHOwOI2M",-7203020054443534602,127685068382870837,6336390718687938723,8330421693290628926>();
   private static final String np = (String)com.yiyiaddon.m.b.a<"swns8e2buljbs","N6kSlY0F34+xAMcjtbzn2yhDvXQcjcO8xRp1fBOrDDmMafSBbtDRPmn+l1xrLPZZNlw=",7783007430838145627,8448916016639387482,-3063092330463389776,-5152102153381556786>();
   private static final Collator d = Collator.getInstance(Locale.CHINA);
   private static List<g.b> bh;
   private static List<g.b> bi;
   private static List<g.b> bj;

   private d() {
   }

   public static List<g.b> ao() {
      if (bh == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2xe9ijajfp8wy","ZhVV2Tq6BH20jptzZ8RKuGYx7xSEBNKH1mBuWV/CPX4=",2464494444590327267,-9192491204407721138,8097653862600605336,5941438473420247503>()) {
            case 21906578:
               bh = a(false);
               switch ((int)com.yiyiaddon.m.b.a<"s2vk2xpxhdyl7s","P5mgl4ad8s+41dzJqlKi/vkozFaLEQj3MUvFLkRKeKk=",-6469534057894017606,-3708290586933568030,-8626692623542847048,9119170779847681927>()) {
                  case -1135400408:
                     return bh;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return bh;
      }
   }

   public static List<g.b> ap() {
      if (bi == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2626w57v1eqwd","EVkPsAhfJjoQGLLsgSE8QVU3XaYEwTUAIheGRcOtE+8=",178169010726427726,5609108441624277299,-8366673989127954269,4808743233981652305>()) {
            case 1720347660:
               bi = ar();
               switch ((int)com.yiyiaddon.m.b.a<"sdkvfuq971ysq","uqin/PWs2KhRXhQwRiBIC73K3tS+EesFAZaHrenGgJ0=",-8761335016528153053,-241864237819630452,-1889914575836675717,3378941506393906420>()) {
                  case 1870643753:
                     return bi;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return bi;
      }
   }

   public static List<g.b> aq() {
      if (bj == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2enx6xoqo3m2v","9pvNdt6eELmgV75mWExSODEO1T4wH+lrklB7syOVHXA=",2825060122052701841,-5496349404603001834,-8493497971737429598,52841571815151272>()) {
            case -2081689593:
               bj = a(true);
               switch ((int)com.yiyiaddon.m.b.a<"symyo3qappu39","2oGWT9AljFycZjoRtX1yaQsvLRdSnSB2/lT9w15RWN0=",-3977112044026559715,-5429830952151243989,6878272199574432153,-4944194998605682749>()) {
                  case -1171561490:
                     return bj;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return bj;
      }
   }

   private static List<g.b> a(boolean var0) {
      ArrayList var1 = new ArrayList();
      ArrayList var2 = new ArrayList();
      Iterator var3 = BuiltInRegistries.ITEM.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3k22dnm1knly","0qGKOsA8XsetGZgdo1ZfpVwixNgOj+qMiwQK1cxq8j4=",8172426932611704388,-1790300079524219246,-4134468306571418739,-3467907942910693569>()) {
         case 892859476:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s9ptoygwebpdm","KtchOgePOytzLPvshL/xL4gVvzVxYB2NP3WIyCOsN2c=",-111708022250765589,-4922363141123361610,6167006266739307114,-7854126142710147555>()) {
                  case 355409095:
                     Item var4 = (Item)var3.next();
                     Identifier var5 = BuiltInRegistries.ITEM.getKey(var4);
                     if (var5 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s19rt1i2egl6g0","IDwYAu8HMeNa7Ns10o9EjL0v/AWcGcVbUX3YL0uHaXA=",7027188954773990153,-7905703495192690027,1849575933759298439,-1906271568391378779>()) {
                           case -1789125856:
                              switch ((int)com.yiyiaddon.m.b.a<"s2yovujm51au28","8b2ZrWUrWAfY4ICrBW4/spDVhfWWZhDM6DLC6+WdcCQ=",-7974298428380669294,7735919668068694946,-7355954404367743392,-5479513063027101497>()) {
                                 case -1356107244:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        if (var0) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2vwwsrgdxm2l2","Xwn+sENqzx7KLF0sdcsP0EX1xa1WwXVrQdhA2jaIfPY=",5636271967399388121,-5616526972942576892,2416322776873545340,8148710154836520165>()) {
                              case 1216290010:
                                 if (!var4.components().has(DataComponents.FOOD)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"smrv3rf12bqpw","0JLNTiiOkLMXfAct7LcjvehAXJotW+e11tPgo6B7jms=",-3438725816868311888,1049623090341196172,-6211866015507478714,-8267210225037563205>()) {
                                       case 1277482533:
                                          switch ((int)com.yiyiaddon.m.b.a<"s33ryfj6jpjdlh","UfRyVMpTz5Tu/4K7w657/v/c2k8zjqEuD+VpRW264KM=",-7288829468896862602,6958635657711848410,280384235972213806,3971293061339962938>()) {
                                             case -2136393771:
                                                continue;
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

                        d.b var6 = new d.b(var5.toString(), var4);
                        ArrayList var10000;
                        if ((String)com.yiyiaddon.m.b.a<"s21bo2j84idztf","rJK2pYUihIdJhwMoeNOhg6Z/7kiCgMybK+7k/t08sXo5ozNmzc1QCINFtBYTgQ==",391906709295824386,4232141742340261727,2754954846248877526,1343094690200307660>()
                           .equals(var5.getNamespace())) {
                           label40:
                           switch ((int)com.yiyiaddon.m.b.a<"s1btejcpy56cih","x+wrmirQc2Z5G5PrlxPhoGDlecaYfD/Go4eNc1+c9B4=",2154609017841340955,-4372706566018392365,-6577045093353835625,-7652313082404572550>()) {
                              case -1313297237:
                                 var10000 = var1;
                                 switch ((int)com.yiyiaddon.m.b.a<"s39a20vcdqxg26","Uhjoqj/gEu/SFfk6LF8p+tMU7Zf6CFXUpz/rauHbXvs=",6615010002899888513,9052600736397707310,7273902248534365618,-1506513817294065367>()) {
                                    case -369849342:
                                       break label40;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10000 = var2;
                           switch ((int)com.yiyiaddon.m.b.a<"s165s5bpfxqu6q","WHf0VVKUEjutX1ifW8lLNT0usBsITwKTgUS70CA/4tw=",-1423983539788315929,-6189998572119793622,6536899625943361672,5599989856587713830>()) {
                              case -396597683:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        var10000.add(var6);
                        switch ((int)com.yiyiaddon.m.b.a<"s342ahng3jkdox","gnSqKT9YAX+GCGivg2oZnYHMKgeIDKanuaIaR/OkUtc=",-3749407991776900726,-4241639632505841817,6566916735923594903,-6927185648354625669>()) {
                           case 2117767635:
                              continue;
                           default:
                              throw null;
                        }
                     }
                  default:
                     throw null;
               }
            }

            return a(var1, var2);
         default:
            throw null;
      }
   }

   private static List<g.b> ar() {
      ArrayList var0 = new ArrayList();
      ArrayList var1 = new ArrayList();
      Iterator var2 = BuiltInRegistries.BLOCK.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s14fnyzie7276","esaK/2ojyEvlnlOLs7PH/XJByerrUch8tGxgA4n1bGg=",3951455031367609060,-2914891188090761238,2344728827166981785,9037381470679461788>()) {
         case 520467655:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s6ds9oju2qp2m","jnmQS8FpGK4eclnVNmb9ZeY1gLD2e3gOQFHfrqNEGQY=",-1145091188600986319,-7432940329340321891,1062946048701742945,8659657398604788425>()) {
                  case -1305076384:
                     Block var3 = (Block)var2.next();
                     Identifier var4 = BuiltInRegistries.BLOCK.getKey(var3);
                     if (var4 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s28wnthxinfeyc","nowe/iv3ps5PVkPqr4vqsh2F30KfUPYVtBbK1HBqIUg=",1782477392707960736,1022474932236353654,2031006615983772419,3272952427848621408>()) {
                           case -1496534728:
                              switch ((int)com.yiyiaddon.m.b.a<"s3tsulv51e1mtt","F+acPkOMlpRjFhj5qfc3RFLQgQXbbmZOmSL39cB7mlc=",-2966045612599223409,2659779585994140060,1205831783684399983,1995651549306785036>()) {
                                 case 1312103349:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        d.a var5 = new d.a(var4.toString(), var3);
                        ArrayList var10000;
                        if ((String)com.yiyiaddon.m.b.a<"s21bo2j84idztf","rJK2pYUihIdJhwMoeNOhg6Z/7kiCgMybK+7k/t08sXo5ozNmzc1QCINFtBYTgQ==",391906709295824386,4232141742340261727,2754954846248877526,1343094690200307660>()
                           .equals(var4.getNamespace())) {
                           label32:
                           switch ((int)com.yiyiaddon.m.b.a<"s1kkzp3exf0jlc","dSJBjjU15YIRJ5g0/N+j/P0ICosI9ANk0gj/S2ZmryA=",2329517757237346735,7447657709565171739,51245156721339493,-2101718214066479512>()) {
                              case 1932100103:
                                 var10000 = var0;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1v8ge17rl0ow4","NMbA+qUkfkRaQd1jWAjY2RVfbcN9P2EMUI2+KbDsqg4=",-2091905577504761529,-4224534234327852384,2376485134968116956,-1965131037554926694>()) {
                                    case 1601391541:
                                       break label32;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10000 = var1;
                           switch ((int)com.yiyiaddon.m.b.a<"s1g71ju078cb98","T+l1/dxV5Dz7N7XRcMVgIiBpj2dnd0FwUluS4qJAK7c=",3259120530410037835,-5650477318855235236,2929999959577137828,-3811803787079664132>()) {
                              case -1729008376:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        var10000.add(var5);
                        switch ((int)com.yiyiaddon.m.b.a<"s1c9koox1wya7f","6IuBgz18u0i7d/+1a7xMZz9rYEUk5km+xM2l/Wdqkn4=",3127355245495522531,898277659090110143,-3417287226696360820,1669028563602730484>()) {
                           case -381640268:
                              continue;
                           default:
                              throw null;
                        }
                     }
                  default:
                     throw null;
               }
            }

            return a(var0, var1);
         default:
            throw null;
      }
   }

   private static List<g.b> a(List<g.b> var0, List<g.b> var1) {
      Comparator var2 = Comparator.comparing(g.b::D, d).thenComparing(g.b::L);
      var0.sort(var2);
      var1.sort(var2);
      ArrayList var3 = new ArrayList(var0.size() + var1.size());
      var3.addAll(var0);
      var3.addAll(var1);
      return List.copyOf(var3);
   }

   public static List<g.b> a(List<g.b> var0, Predicate<String> var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = var0.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2nhivsu33x9of","YO5FKKaaSUuSkVAFfb6EXo3s66JVU2KOo2DkG9usiWI=",-6609606108700686428,-5976743767092607201,-6606849407257179718,-4347966159186972511>()) {
         case -2001340983:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"sc73viciobiy7","SyUGYinj/ACq8P16/rivr9CjU0Axe+aGHdC/Cw0Bh8A=",-862964172497232260,-997482293490427260,-742196969558974265,-4286253490966143797>()) {
                  case -442902877:
                     g.b var4 = (g.b)var3.next();
                     if (var1.test(var4.L())) {
                        label23:
                        switch ((int)com.yiyiaddon.m.b.a<"s3urop5mmujn6k","pm6hbM2DmeRl02zU7iXgbrnGkyzQQHpUND+U129jwio=",2535626694880946446,1273762906823606193,4082074210361271395,1168097445048609785>()) {
                           case -728762633:
                              var2.add(var4);
                              switch ((int)com.yiyiaddon.m.b.a<"s3jx8ptiokgq7x","WBXoYqO8JwWIoIOwvXsTVIUaQqtvIYJiHC278SPEqd0=",5097125965804877274,-4936555763936531632,-2672624112777638186,-7139515424861389736>()) {
                                 case 1694680377:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s2f2ysoexv6w0q","DA7lr0NML+fcmY7/LB4m5QnqhxsRDhchAR30esyv1oI=",-8577749887423047119,-3569043361258917665,6270585068126049703,5050472924468008775>()) {
                        case -1967203562:
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

   public static boolean H(String var0) {
      return BuiltInRegistries.ITEM.getKey(Items.AIR).toString().equals(var0);
   }

   public static boolean I(String var0) {
      return BuiltInRegistries.BLOCK.getKey(Blocks.AIR).toString().equals(var0);
   }

   public static String aq(String var0) {
      Item var1 = b(var0);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3epivdabomut5","6QEp+pESI0gqWOTcIdH51a5HdxRkjztR0HKvNqT1zGI=",-2967269946328203990,122267025927984448,3388686192904513132,2491864063304773966>()) {
            case -1111477377:
               Item var10000 = Items.AIR;
               switch ((int)com.yiyiaddon.m.b.a<"s1a6lq95ldwnqq","o2qqKAg2ljSVTa7Gewydb0s4aMWCXA0nQ9cdYjM55CU=",7796355663767820722,-3490193140020079741,6288918647850507340,299826430165060506>()) {
                  case 1161557902:
                     return var10000.getDefaultInstance().getHoverName().getString();
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2oqlefxx5bge3","fUlX1bTJsg4RJwg1tn6mfARgTTp0ywYhYpDKjZdcLkI=",5711851108988807823,-6060954187779467717,2330041188970068979,-7981244903962513209>()) {
            case 255356252:
               return var1.getDefaultInstance().getHoverName().getString();
            default:
               throw null;
         }
      }
   }

   public static String ar(String var0) {
      Block var1 = a(var0);
      if (var1 != null) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s2bu4s894g9om2","5IucWkwH5Q/huFTJd2+TofCUAvk5sL39uN+i5li8PzA=",6638140245768582588,5394231573179393731,4732468231979050657,4561100588699066802>()) {
            case 1059565009:
               if (var1 != Blocks.AIR) {
                  switch ((int)com.yiyiaddon.m.b.a<"sxbflycxdbun7","9LKwgqPBtpg242DYh/ZfOs608/NWvKu3/yuP0mnPqrg=",4679651281061794168,-16802118139919226,1061214173287191347,3764391063040261562>()) {
                     case 1990856508:
                        return var1.getName().getString();
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s15c683a3gv51s","vcFDCLhBHe/HxhXa0k1mc4Nf2E+MDVJeUHoWiiv1+9c=",6084706574391900049,8335267983166971101,-7853608355913214492,6827977077388036550>()) {
                  case -1890746480:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      Block var10000 = Blocks.AIR;
      switch ((int)com.yiyiaddon.m.b.a<"shh229gkug91w","eSFFJqUCstaeS/WNgyLS3XKB6vsQfLJSqnqI7LiBQZE=",7926172532865941177,-579121917643746001,640148153970679098,-463520520620971718>()) {
         case 213872250:
            return var10000.getName().getString();
         default:
            throw null;
      }
   }

   public static Item b(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s31a8rrn3cbo4q","FU8nj4bCS+rLe0r0e/giZwjYbP0eGWS5mz24/olbAWk=",-5493001802170403062,5265595144215172454,1216039605413459456,2733748013910799064>()) {
            case -1980797029:
               if (!var0.isBlank()) {
                  Identifier var1 = Identifier.tryParse(var0);
                  if (var1 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1b35m644wxk2m","V6oAPcIZ6sDCofxfIh60c6O053xmdloenRTB2Ixl78A=",2069215118414674669,6980051050642988346,7552236649832155464,8041868124872717466>()) {
                        case -735724689:
                           return null;
                        default:
                           throw null;
                     }
                  } else {
                     Item var2 = BuiltInRegistries.ITEM.getValue(var1);
                     if (var2 != null) {
                        label34:
                        switch ((int)com.yiyiaddon.m.b.a<"s39ake47sn3cof","jQa6LPRuhdZLgp9gzIHEdU0uNliDSx+CXaJmOhkVb7A=",4579517324790731863,-5125713472609962683,2167006021729063047,-8743856633921668721>()) {
                           case -1840880340:
                              if (var2 != Items.AIR) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1r1vck7o88ca4","M3e4L8Pm6MY/wrd+Sr3ZBWXhivIublC9v5d/vwv+umk=",-1909446745033134649,-2006649318274787435,-4694872935214015766,-3838052582040295900>()) {
                                    case 966532272:
                                       return var2;
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s1rnk37q5q3e1k","6xY7qP/47T+y8SFD3OEbqsVRk9ifBI/QCl3PAjzNMr4=",886200615915207574,-6075549034325154606,7012216867197964230,-2323476994084811685>()) {
                                 case 307935415:
                                    break label34;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3s2lhd7ubfefi","/kG8exrV8rJoR/N99OH13pdCImHdNVrxN6NU03UIjNk=",2822651063847137968,-2191686550023171831,-3642131103019294958,-9084338558277703690>()) {
                        case 956256545:
                           return null;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"shkonvqapzsmq","R7DnZ0+3AtpHf/+6EKlSzYLjjyjY1dWvOuBCffCXm9A=",918500540273488897,7038349047793914861,-7098473907406073507,-6338741418435468062>()) {
                     case 1277233840:
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

   public static Block a(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"skkilscm1vq2c","wIGMv6cMHnr3QUaN+x6panQfQSKgkfxxNZB9sJK8ZnA=",5793566550396004786,151420185563922246,1869251792941310131,-2917776101054771332>()) {
            case -1630267383:
               if (!var0.isBlank()) {
                  Identifier var1 = Identifier.tryParse(var0);
                  if (var1 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"sziqs7w4ogpge","pFnOqOE9xw6ymP+ojzA/BZ+1ZHqRxeMhtnLcyCZqOI4=",5634220179124022612,-5968521764969976238,2350755908647158576,8167747920805711355>()) {
                        case 16855277:
                           return null;
                        default:
                           throw null;
                     }
                  } else {
                     Block var2 = BuiltInRegistries.BLOCK.getValue(var1);
                     if (var2 != null) {
                        label34:
                        switch ((int)com.yiyiaddon.m.b.a<"s1brzoguiq0rnn","fimDJvlFH/+IAZnEuPFYIODJtr1cHrkv6PNaFfNjkec=",-39446666507049125,-6345499969869218839,-4909303434870578128,-6803414167180489986>()) {
                           case -853899391:
                              if (var2 != Blocks.AIR) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2o5lltd4mt7be","Ax1IZcKqRgBZcdmu6LpRgtbdrfCMgpNyNRNBmMKD2Ow=",8840950412999630223,5390051468836732377,332123596091407010,-7809756358228736717>()) {
                                    case -1466728262:
                                       return var2;
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s2ceb55rk1anpu","BrSuB1cNu9mZV3bqUyohmOjXLgOnqNd1nEdRBYvuNP4=",1275994194243205502,-829616914242216002,8290960381962474762,-147578421784315904>()) {
                                 case -1242703957:
                                    break label34;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"snvz69i7q1ipz","N8QzPMjrrFFuG9buvcnhZKrFzTr6wDkBC5ei6LNR/FU=",-7028464018661757904,8123743657625570670,-6061803981990308472,-8236091797519164169>()) {
                        case 153309507:
                           return null;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1ra1sztaf9fjy","7NhVG1OV4Yi4sswhSjJgAwoZfiQe0Y0/YSpTvxeh3Lk=",-3347239011723732828,5261233755042234520,9213470569157344250,7369055270261851204>()) {
                     case -517363511:
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

   public static List<Block> i(List<String> var0) {
      ArrayList var1 = new ArrayList();
      Iterator var2 = var0.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s166fxppzq7b7v","WDYQWkWLynxjEteIDsLTJ1MOW8UHaCwIc5hcNAMLqGk=",-104834392673397820,-5446078381140369485,-1331541301823863360,5497601270796325130>()) {
         case 2100835336:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s30u8aahytsmpd","25j2ulofOR06mMZJNF7d7te11tFXOqVqaCoMG2ut0LQ=",3744112515771702591,3025595226960640348,230365890424721836,-5134098919441854666>()) {
                  case 650154944:
                     String var3 = (String)var2.next();
                     Block var4 = a(var3);
                     if (var4 != null) {
                        label23:
                        switch ((int)com.yiyiaddon.m.b.a<"s6wki46w5rvc6","rPDjrTj96JOcLT2++ux8mXva+VuhaDyK1sdnM5uMEAI=",-6792894149347144680,4117077000343347189,2408763695003262919,-8224472869374579967>()) {
                           case -323603152:
                              var1.add(var4);
                              switch ((int)com.yiyiaddon.m.b.a<"s2jpx5c1xh26yf","yyznSu4peWyT84qzdQJ+TJspmgOUV/4/D+c/mfsJYwM=",9039844232187394159,-2412267263311531932,1262415266997884803,-2801937820083413351>()) {
                                 case -888221869:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3k6i8yh7w67aw","aM/bevyljqilAEY8WJ49JjAjWfolgUD4XvW41qVpUvU=",-7414900510384786040,-4758164388524820213,-5079461202906028451,-57451025267056536>()) {
                        case 727711689:
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

   private record a(String nq, Block g) implements g.b {
      @Override
      public String D() {
         return this.g.getName().getString();
      }

      @Override
      public String M() {
         if (this.nq
            .startsWith(
               (String)com.yiyiaddon.m.b.a<"s1iuy6e4lwft2s","ueDkqDYnu1I8v45t+rI1NF34mUY/kMSHM4TUnyjHApIC4J79RxNsdZ9cyM8AM3d3",-8907778885540118959,2721591407808942577,-2152154917823737527,8403059653409615985>()
            )) {
            switch ((int)com.yiyiaddon.m.b.a<"s114cmcc7c9qt","SMllg9N26v6ZrerBRGd4FIEgrWRnsZjfcrlMJ7CpxOw=",6495894641149721070,4299719781372444780,-9051489537090613604,-5304291826046955169>()) {
               case 1024130326:
                  String var10000 = (String)com.yiyiaddon.m.b.a<"s2k8dqfwugt7o2","xeOp+FQv85uzcV0gqRJ3OpH+Z8x841d6iMr7Ikm1dQ9GEbaQCLjA0JnE93gnJNDq",681882668404319241,2074461361170142677,-9107222842178748657,6479725431696491866>();
                  switch ((int)com.yiyiaddon.m.b.a<"s1i35fmu2cpkf5","BC/Wp/1JLs8tqoV/NVYpyYtITyNN6Ddfn7odC5nrbBU=",4435655021660253205,-2988220046034940382,-8149888180842164787,-1994024239777951977>()) {
                     case -1935529245:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var1 = (String)com.yiyiaddon.m.b.a<"s2kkgtgvpaetzm","Axup0pwY6JkavQKWByiYGoq+b18KXP+rvfLqOgpRM+kl4rlfoIgkhpzJmMPYuEcFFsE=",-5955154032250524885,244565264093503897,4370327432228464042,7423298385919911545>();
            switch ((int)com.yiyiaddon.m.b.a<"s1d646p6hu6ipt","iezMMxaN5YQNrvbjKLeG6+aOimK0rhUB6GdI6ssvMuY=",-798159391072237912,4608659913457097557,-4939673955686692578,5326877387943127849>()) {
               case 1649533652:
                  return var1;
               default:
                  throw null;
            }
         }
      }

      @Override
      public boolean a(Canvas var1, float var2, float var3, float var4) {
         return com.yiyiaddon.l.g.c.a().a(var1, this.g, var2, var3, var4);
      }

      @Override
      public ItemStack a() {
         Item var1 = this.g.asItem();
         if (var1 == Items.AIR) {
            switch ((int)com.yiyiaddon.m.b.a<"s3nhiiug31k49u","qnJAQ+Zns59bSyH87ATOlJdlD+op9EM66lnMfSx47aI=",-3591681401312987063,1898756785554350466,-5506522180924351303,-3256324385890270846>()) {
               case -675879050:
                  switch ((int)com.yiyiaddon.m.b.a<"s2726mfnq2tvzw","SbUr3e0O5DCfukj1D2drsD7i0DkulE+2OXwT0lcLfV4=",-3819738374615039965,-2553284650594795514,2039036708303461364,8195567157401314016>()) {
                     case 664839092:
                        return null;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            ItemStack var10000 = var1.getDefaultInstance();
            switch ((int)com.yiyiaddon.m.b.a<"sat6uob4gubne","dJmJnWH9n8dvuQFi1GI7rxkQ1ZiRqepW2KNloT7mHgU=",-124695429612651792,2523404855429228739,-5561019185431502518,-5803260530456776572>()) {
               case -539340754:
                  return var10000;
               default:
                  throw null;
            }
         }
      }

      @Override
      public String L() {
         return this.nq;
      }

      public Block a() {
         return this.g;
      }
   }

   private record b(String nr, Item i) implements g.b {
      @Override
      public String D() {
         return this.i.getDefaultInstance().getHoverName().getString();
      }

      @Override
      public String M() {
         if (this.nr
            .startsWith(
               (String)com.yiyiaddon.m.b.a<"s2k9unjejh5agz","X6rfPdUIpzpj5eFIUr6P9qn+vqvGG7zn2DPOpxGHP7NC2od0SOpZJT2NHfoXabqD",5126939533852436083,-2613097735812806746,6652674403597190476,-4612925135811101726>()
            )) {
            switch ((int)com.yiyiaddon.m.b.a<"s27dhfxcl1nss3","ksvpfCKqdirbMywVlBznYrK4I0J3MDkgcrK/KwNEGpY=",4803562256716002063,2484916801606085590,1091939514262408928,720220911857218388>()) {
               case -25572156:
                  String var10000 = (String)com.yiyiaddon.m.b.a<"s3vp21cw0hnowi","UYe2sWpYOiJCmpv7rCqY2cVNkN0+HnKy4Aw8LG3/gYLLNxLyAND0MkXqr55wepGx",-8694594205644327819,6346176693755988518,1134605839970595216,-4147054672633561327>();
                  switch ((int)com.yiyiaddon.m.b.a<"s10ruktodktblz","IgHCOfuDUh+t+0A1hJaJ1EIpT9jCyw35QDEGbFKt/Iw=",-6494579517752557232,-2856766331801956042,6078302039960865074,7643650266867343605>()) {
                     case 1382648163:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var1 = (String)com.yiyiaddon.m.b.a<"s9ahubk21h0cz","HkK9DssUi9M6u8pP+dpDQB63a+vuyCQfY/7K9SmpFEIS+egk9XOdlrUu9bwnBZxJitA=",-6276784490002171225,-1748976649088796742,-7116661100158895289,7536762393841273201>();
            switch ((int)com.yiyiaddon.m.b.a<"s1fit4jufar5t5","SWqnSvlRIb6LNzYIy+QfMZMMHUSM9wy7lh2KKSOEr24=",6159125261227104276,383613730398496148,-2777810830870245659,1240339729246277663>()) {
               case 1809880402:
                  return var1;
               default:
                  throw null;
            }
         }
      }

      @Override
      public boolean a(Canvas var1, float var2, float var3, float var4) {
         return com.yiyiaddon.l.g.c.a().a(var1, this.i.getDefaultInstance(), var2, var3, var4);
      }

      @Override
      public ItemStack a() {
         return this.i.getDefaultInstance();
      }

      @Override
      public String L() {
         return this.nr;
      }

      public Item g() {
         return this.i;
      }
   }
}
