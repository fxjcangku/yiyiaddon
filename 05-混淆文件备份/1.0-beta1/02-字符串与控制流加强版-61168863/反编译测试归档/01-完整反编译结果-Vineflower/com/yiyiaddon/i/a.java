package com.yiyiaddon.i;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class a {
   private static final Logger r = LoggerFactory.getLogger(
      (String)com.yiyiaddon.m.b.a<"s5zkgm8ewtfem","Rq45KjP9MjTz6MbLBvIhChsMNC9NY4LVjiCeLUWUTwRDxMvyt0irV0L1uwrTd70Kg3T3DP6NbuY5SPNIJrc=",7002686028775513033,-8463930777128884074,8389716855879287060,1864394401431201355>()
   );
   private static Field a;
   private static boolean fo;

   private a() {
   }

   public static List<Component> bA() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0.gui == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3drbhk5yayo10","rWN9Kn1YB3HV9VO1d+KqB1EpxzBnySxBhdJ1X7bpvDI=",7324815879878905686,-6098157575707879343,5508465239204829565,-6391034736718337013>()) {
            case 1570909752:
               return List.of();
            default:
               throw null;
         }
      } else {
         BossHealthOverlay var1 = var0.gui.getBossOverlay();
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3ji0l6so6y835","a8io6jeD1k3T4srSwaLaeD1p+Oa6SxDwN+oMatZh0PQ=",5009092071986012313,-3614449465558977553,2967700648784622874,1228970646653091506>()) {
               case 542891805:
                  return List.of();
               default:
                  throw null;
            }
         } else {
            Map var2 = a(var1);
            if (var2 != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s154v6c7yiodrz","oDUjfsRfUo9XiBpqq1LY52ridXI595pDpi31DhNCrqA=",3954657351066604143,-7593140320158399010,3052449703584816123,7839560352280355494>()) {
                  case 1481848817:
                     if (!var2.isEmpty()) {
                        ArrayList var3 = new ArrayList(var2.size());
                        Iterator var4 = var2.values().iterator();
                        switch ((int)com.yiyiaddon.m.b.a<"s1c2yovat36qvy","aVqiWsMRUJ9L4yCCuUHJ7UN0Mgq0xek9+V1Sl34lDT4=",-9130181814879175109,-2468376966570559742,-3061871438559514975,4730362533764801424>()) {
                           case -872210882:
                              while (var4.hasNext()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2j7k7uu0kbr5v","MdVsWYFgXvbyP5o6VO2VikOMMW9mnLyBH9/N1AnQg6s=",-1893643931687207966,-763176424235220518,2885885197748707568,8005246651131082726>()) {
                                    case 2140616607:
                                       Object var5 = var4.next();
                                       if (var5 instanceof BossEvent) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2dm1kv43hgz95","ta4RfKy2mTLvOfV80v1W13FJSdS/uw4JJIWlTJuvGF0=",-7014354522059061155,2924120714868835406,5977908992506711230,-1363305331010549969>()) {
                                             case -1408642670:
                                                BossEvent var6 = (BossEvent)var5;
                                                if (var6.getName() != null) {
                                                   label45:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s33ua5a1q0moui","etznKFdnlIWuak8iKxyjBd8vKmqtJKncXLZa2YmMQl0=",-5167048135955367201,8640760775153049895,1407363817770747887,2334175978957096800>()) {
                                                      case -1676707345:
                                                         var3.add(var6.getName());
                                                         switch ((int)com.yiyiaddon.m.b.a<"suqz2dduvbb43","VRu6t/0EwhL5LM+8kAloseWcLD3JcOWsd7Mu0aw8lvs=",-8138147204866949215,7950618580213083524,-749374466050776624,2421286283903945966>()) {
                                                            case 1039676013:
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

                                       switch ((int)com.yiyiaddon.m.b.a<"s3t0acfom7kpp","856i08Z2mC1trL16HbL1aw9DkQk8M+QGQoj/lxjdxNE=",-9056506900754104261,-2240041237634290979,-2496871367491815036,7948781412952065707>()) {
                                          case 1829393092:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              return var3;
                           default:
                              throw null;
                        }
                     } else {
                        switch ((int)com.yiyiaddon.m.b.a<"s2c6ghr0yagb5l","HkwuH6xwZspTA3sBc9Hxc0clRx/XpbpeHV3uno01/M4=",-8336847628753275406,8513077152591745745,-5432205340079122653,-1143674723378928253>()) {
                           case 1129709657:
                              return List.of();
                           default:
                              throw null;
                        }
                     }
                  default:
                     throw null;
               }
            } else {
               return List.of();
            }
         }
      }
   }

   private static synchronized Map<?, ?> a(Object var0) {
      Field var1 = a(var0);
      if (var1 == null) {
         return null;
      }

      try {
         return var1.get(var0) instanceof Map var3 ? var3 : null;
      } catch (Throwable var4) {
         if (a != null) {
            r.warn(
               (String)com.yiyiaddon.m.b.a<"s22g595rslfvox","7Oc9FApEYE0d0WiSPf9KzF7ljK3rBKmMPeWiB3zrzEDbhpssLd9a9tgbYTYtTZ8PgVBOGbayL4w0sd6qgbCVA6Gq30lYJHAFlfzNFJBBHVoujTZl7HQ=",-3548703211411553626,-8896260396111547662,7924943077329690106,-2510180055941893009>(),
               var4.toString()
            );
         }

         a = null;
         fo = true;
         return null;
      }
   }

   private static Field a(Object var0) {
      if (fo) {
         switch ((int)com.yiyiaddon.m.b.a<"s2bdya1j22d5uj","E1uEjJeGzB0zgDSsy2zzJRrV/GIpVVonZ646Yar77XQ=",8408309954007644054,2614280793278026309,7823120137285998148,-8875847895624590600>()) {
            case -1791672114:
               return a;
            default:
               throw null;
         }
      } else {
         Class var1 = var0.getClass();
         switch ((int)com.yiyiaddon.m.b.a<"s1yfo9ad0gjvja","2m+1hNmFFUcN7WezGUO8wBpNg2OBQq359j09diyqgKI=",2365745141591318377,1097176190274706201,-1126103987594750969,7857219049255006844>()) {
            case 82721983:
               label121:
               while (var1 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s184ctgri2k6v6","kpTkjcfd/Icz2+mBMeKfumWLccOItarX+vhKgAujO00=",-6241452284008490658,-3683592574748235364,-4270417403347463196,608980906776776901>()) {
                     case -778639765:
                        if (var1 == Object.class) {
                           break label121;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3776xvja3j5dc","xi20RVWj4mIq4d4StU8/aw+j8Sj/IT9XBR4HV/dLyYw=",-7295580595315253535,5349343445896491974,-8560723574178315326,6943925536680881560>()) {
                           case 958422:
                              Field[] var2 = var1.getDeclaredFields();
                              int var3 = var2.length;
                              int var4 = 0;
                              switch ((int)com.yiyiaddon.m.b.a<"s3sg4x7oclbpim","55L1Xh6clAd/IbUjK77GtwkMcHy7ZOTymDOp/Ndc2vI=",-6273541313065554164,4222353592338902216,5557243263479946479,-263742088573879711>()) {
                                 case 555210862:
                                    label118:
                                    while (var4 < var3) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1pqxqglfjnxca","vktgt7ad13UIcKb6euKlYwNxoRBZiOovlDpiZcvuGy0=",-4190384965388896176,4496410406801217885,-4492804641356739630,-8227593907107792874>()) {
                                          case -1565682929:
                                             Field var5 = var2[var4];
                                             if (!Map.class.isAssignableFrom(var5.getType())) {
                                                label75:
                                                switch ((int)com.yiyiaddon.m.b.a<"s6v5zauj54ny1","KrEikoBMuJkjdb+iVRGz+mYqXs6Bc/NTtb7JjyA64qY=",1872820153585599249,-8058674329804393304,-4660443243410119002,-2870815558524238259>()) {
                                                   case -406118225:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1jrf17wuu8jct","538hR9ROUrhKZApSSPSDUaGMRUwQbE8sPg1JLeyeFf4=",-4678262493896536340,4033489270100072164,899029813100481577,-5558946747090087040>()) {
                                                         case -881538760:
                                                            break label75;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             } else {
                                                Type var7 = var5.getGenericType();
                                                if (var7 instanceof ParameterizedType) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"sbyodsuds5lvs","+a1ukYqwHE7I3eDDqCwBEvRYkXu9G6fj+HLjiAfm9Es=",2539786893098795580,-7582213994011193012,9211506889040172264,-3951024156028407019>()) {
                                                      case -992997124:
                                                         ParameterizedType var6 = (ParameterizedType)var7;
                                                         Type[] var10 = var6.getActualTypeArguments();
                                                         if (var10.length == 2) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s1vcc0jji82ztw","hzNgrrAtyaNu5nAsHGygYbcdMdWEaYaOUXP5QwEIkjY=",3535631145310120969,-5111871396654055575,-2188672485901377377,-7325319953203715467>()) {
                                                               case -1961237549:
                                                                  Type var9 = var10[1];
                                                                  if (var9 instanceof Class) {
                                                                     label79:
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s2ghh78g9mievh","x/Ckwa07IyFHUNmezB81A4r6Eq6ehuyzX1NIlrE0ufU=",8627360090068862544,6647638987134331818,8767272929032535916,-8865229761448696871>()) {
                                                                        case -1937641043:
                                                                           Class var8 = (Class)var9;
                                                                           if (BossEvent.class.isAssignableFrom(var8)) {
                                                                              var5.setAccessible(true);
                                                                              a = var5;
                                                                              switch ((int)com.yiyiaddon.m.b.a<"s105g03jm3rf86","BT/e3Kqw1gBk5esvYDAZ825DkxPomFrYvhQ+4wxj3mg=",-301021098479824620,482343694939438196,-3033724411115577256,-7594752620938023950>()) {
                                                                                 case -1029794813:
                                                                                    break label118;
                                                                                 default:
                                                                                    throw null;
                                                                              }
                                                                           }

                                                                           switch ((int)com.yiyiaddon.m.b.a<"spq14m2zu16c4","z/2uJnMV+bt/uhTqh29xJkfS7y7I+7bqN4aAZ/qYFMQ=",4459727423150804808,1345495299129879708,-7314641953369668430,4483055701521377849>()) {
                                                                              case 1874900321:
                                                                                 switch ((int)com.yiyiaddon.m.b.a<"s1jttbd8d090nm","HJQp1bVPr8hPsTqnSr9vjawkV91eENtTKJ7NW2pXE6A=",775721926426896754,-1337962922153790574,8335806852869260386,-3402073965611866578>()) {
                                                                                    case 14350274:
                                                                                       break label79;
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
                                             }

                                             var4++;
                                             switch ((int)com.yiyiaddon.m.b.a<"s30ocm2d91kpa7","QCUVnyeVGB6mgRRy7wNZA6vvgJT4DyUkntmLp7INO/g=",-8873325031282190637,-3507270591960918696,5128211712925555687,2213744740599326172>()) {
                                                case -365502460:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    if (a != null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2ljd7uszq4r6b","9AAljlcHbeq0o75JkS7ArU9zSHPL5pKkFTn/Cm649Y4=",-330457918519809424,-6422956951268278638,-3375910440301462794,-8647542802951755761>()) {
                                          case 10454248:
                                             switch ((int)com.yiyiaddon.m.b.a<"s3ubx2efdd6qo7","9vwvOyT9I5PB4QZ6WZDCWlgHCcl2f8QjZ466x6kqFGc=",-7914620635885169195,-7372839346540615778,3985936233964490439,-6297548017196102356>()) {
                                                case 530860291:
                                                   break label121;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    var1 = var1.getSuperclass();
                                    switch ((int)com.yiyiaddon.m.b.a<"sft3d3itcigpk","5XzbWeZ1z/04Qzpas1gXPa1ED0l7XcxNSVm5dX3xmL8=",-5821945437524029003,3019575497618319071,78584411297557523,3915202853166660831>()) {
                                       case -374199410:
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
                     default:
                        throw null;
                  }
               }

               fo = true;
               if (a == null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s19kgukdldledd","6Ln7y7N4r0aNEQTXPib9LEbhuQkwvotLe2jK3/1tcow=",6089216790334628926,-6581077415594489728,8296735705000469674,-267681732316137127>()) {
                     case -1697637758:
                        r.warn(
                           (String)com.yiyiaddon.m.b.a<"s319itvy24vaik","WoGCVrK/TgHtB8d4pYRfD27OQi3um9BQMSjW/QfA8l7dZVysoDHPPnTP9WN9JDxYjMZxaHzw7GfBHHDeEWl+vNscHXe5rUaqvCxO9e0pwfB2AQ==",-2424492812000224343,5243711487964091272,7381904939597048769,-4510687775438596158>()
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"s2f2iwg89y8ei7","AfcFgmZIn1+BWD7AlvxzHHhX0aPKOlROFKwwfcNIyYk=",-7765828906432200214,1864077602295355661,-5954512620122406860,-232763760908512606>()) {
                           case 136340057:
                              return a;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return a;
            default:
               throw null;
         }
      }
   }
}
