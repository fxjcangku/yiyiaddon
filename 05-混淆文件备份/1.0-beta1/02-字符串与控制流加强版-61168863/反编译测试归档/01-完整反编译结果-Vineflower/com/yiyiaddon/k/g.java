package com.yiyiaddon.k;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.time.Duration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

public final class g {
   public static final String EA = "stats_report_enabled";
   public static final String EB = "message_poll_enabled";
   public static final String EC = "update_notice_enabled";
   private static final Duration i = Duration.ofSeconds(5L);
   private static final long aR = 60L;
   private static final com.yiyiaddon.g.d a = new com.yiyiaddon.g.d();
   private static volatile boolean ft;

   private g() {
   }

   public static void aR() {
      if (ft) {
         switch ((int)com.yiyiaddon.m.b.a<"s16hh2utamhhvq","W5SlyA4rptVpqL6HoemVIF1ZCcIAR9RcmCHgh/SX+ok=",-2336269651047541879,1542295645957963457,8232855179538886041,-1131552163332795945>()) {
            case 1417488716:
               return;
            default:
               throw null;
         }
      } else {
         ft = true;
         com.yiyiaddon.d.b.a(
            (String)com.yiyiaddon.m.b.a<"s1pf3aoy9f0l4r","ECU0d72/MwzTIZ5psvkQ8N6UFc4BMlqPty6jo92BcSPrufgmRbh7cRMaLL4WwlQHlK5w4vN67qWL1XyjtFKfVnavgBqtf0k7zWM=",586452497962489471,1235272137960260477,4772426059930682209,-8186658954948321472>(),
            60L,
            TimeUnit.SECONDS,
            g::D
         );
      }
   }

   public static com.yiyiaddon.g.d a() {
      return a;
   }

   public static void D() {
      com.yiyiaddon.d.e.a var0 = com.yiyiaddon.d.e.a(
         (String)com.yiyiaddon.m.b.a<"s2buxyb9jzt9id","t6XbaW1z+ztkquoC21x0MoeviJ+0/uR6PXii0exFJlZQqCx3wblr+LAIZbp0QvTDPtE=",-3909467707418757724,8333579150484781027,8330367413334059518,3532179463834626095>(),
         i
      );
      if (!var0.b()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1rz2dximipchz","54l1FSp444MWx8jjEzI1qHGq9UjY4+dnrx7Bmf+tTZk=",2924746484843746435,-818081488149341379,8057857932553711834,4971540129881311144>()) {
            case -1008909755:
               return;
            default:
               throw null;
         }
      } else {
         JsonObject var1 = var0.a();
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"suhobgocn85mx","8bZv6ji97QQ9U7BlhXcmhhk/rjTOlivWHr7td2OI3kM=",349546625023464194,8727456824022478047,-1394657669368890197,9179626930033350685>()) {
               case -645297222:
                  return;
               default:
                  throw null;
            }
         } else {
            JsonElement var2 = var1.get(
               (String)com.yiyiaddon.m.b.a<"sepofecjuqjmu","N73T2qbcrUlRTJdhUk/My+A1NvKNQQq2OmZ3kc9IbbiJM/iXjWwCbg==",2500895859730188474,-7972051144003084733,8119658015700312351,-2546788514294517641>()
            );
            if (var2 != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s2qeabtfdoh7pr","+pvv0UFpQga8RtIfE6BJKZseyHu2gq5LXQoqfB1GyI0=",-881433959261513101,-3006551259687429138,-2932080409156718023,-2679440370645931917>()) {
                  case 352747623:
                     if (var2.isJsonObject()) {
                        HashMap var3 = new HashMap();
                        Iterator var4 = var2.getAsJsonObject().entrySet().iterator();
                        switch ((int)com.yiyiaddon.m.b.a<"s3cniv1wj4eqq5","h0yMQAhG6IBKqVKE/NUjk6QKI9rlP/zUtpTbjAUWPPU=",4917474484289485020,9003586404524912971,2058041105317940502,-7579601723204439557>()) {
                           case 1234550247:
                              while (var4.hasNext()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"swjcet8s1pxsa","XJvC7daMC0gRwdXmKO85cdxENhrRNnzGRr3+bpoIwRg=",6147025952581231401,3625548746337945788,2660287493337669493,-3011343752524736230>()) {
                                    case -888503622:
                                       Entry var5 = (Entry)var4.next();
                                       JsonElement var6 = (JsonElement)var5.getValue();
                                       if (var6 != null) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1g514b85ku6yo","sOU1UAd2NerS+TpT7ta26arLfMqrSWYxwlByANaX7Tk=",-6231650155772143044,-9118706024656975704,694937144064638298,-3094582128942903921>()) {
                                             case -1525075290:
                                                if (var6.isJsonPrimitive()) {
                                                   label45:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s91aaun565ox7","HLS22w+8l5nojLY7d8VNUlD9iAsR7ZCN8WXKJUc8gNs=",-3561879128107049734,1077226855836497584,-8907815218954947372,1766895393202891809>()) {
                                                      case -124203703:
                                                         var3.put((String)var5.getKey(), var6.getAsString());
                                                         switch ((int)com.yiyiaddon.m.b.a<"s22dqtvxqze3xl","ktmzAyCm4T0QhvFBPY/gqWp0KZ5w1whHVCiDvP3YVZs=",-603839476109324484,8307741626398111262,8103382010994630554,8318708225026772084>()) {
                                                            case -935804859:
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

                                       switch ((int)com.yiyiaddon.m.b.a<"s3ie73awxjq7a9","OFWYeBSuMU+r81fEYKi64pBy7bxOUWA/P0t1LL/U0t4=",4349300701821808289,-7700215409009893847,310252462619725572,-948317919711774972>()) {
                                          case -993179859:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              a.e(var3);
                              return;
                           default:
                              throw null;
                        }
                     } else {
                        switch ((int)com.yiyiaddon.m.b.a<"s3hon2ffei2e46","D3sj3hwtK9bLu3tPKaBAbNVdE1O+Bg8DgiR9maA0bcw=",8211737754841019322,-5975609222478392818,9146244016361894308,694460274379246126>()) {
                           case -418732242:
                              return;
                           default:
                              throw null;
                        }
                     }
                  default:
                     throw null;
               }
            }
         }
      }
   }
}
