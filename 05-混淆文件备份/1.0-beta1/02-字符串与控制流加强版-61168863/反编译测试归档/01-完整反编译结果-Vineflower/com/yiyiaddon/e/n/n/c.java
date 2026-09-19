package com.yiyiaddon.e.n.n;

import java.util.Iterator;
import net.minecraft.network.chat.Component;

public final class c {
   private static final String tM = (String)com.yiyiaddon.m.b.a<"s2sbs0a5xuad0d","pfCPd/d7Sf/9UnOYYhCCxVCBJYzL5MqB2oOm8TuFTxqqYPBSgkix7OHYaXVkCIFpCTt5Dvhq9PnuojPaiJhXGRynP5oFR6qGQEpiOFwKua6ffbl+aBR53Q==",4137628460397607061,-8180778250670544365,3320310607974603401,-3881645034918767144>();
   private static final int mR = 20;
   private static final String tN = (String)com.yiyiaddon.m.b.a<"s15xkcokgw3yrn","Y4Df2flBF8/L6Kal+OXX+3gkkFT3SyX05DdSNNXJtiUqye/RkunMH0Jbu01freiS",-7010447060585185610,8600713052118916641,-5159048964177910117,2729693006438659994>();
   private static int mt;
   private static boolean h;

   private c() {
   }

   public static synchronized void init() {
      if (!h) {
         h = true;
         com.yiyiaddon.d.a.b.a(
            (String)com.yiyiaddon.m.b.a<"s2sbs0a5xuad0d","pfCPd/d7Sf/9UnOYYhCCxVCBJYzL5MqB2oOm8TuFTxqqYPBSgkix7OHYaXVkCIFpCTt5Dvhq9PnuojPaiJhXGRynP5oFR6qGQEpiOFwKua6ffbl+aBR53Q==",4137628460397607061,-8180778250670544365,3320310607974603401,-3881645034918767144>(),
            com.yiyiaddon.d.a.c.TICK,
            var0 -> ae()
         );
         com.yiyiaddon.k.e.c.l(c::gM);
      }
   }

   private static void ae() {
      if (--mt > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s51tay3hbm0l0","KAHkuvKr7uNrHfCPu5x3G+z2WrvgJ0s3M5AnjDMVdZA=",1252241783814328539,5604923644751196224,1282999554542316988,5650086785981962995>()) {
            case 1537172890:
               return;
            default:
               throw null;
         }
      } else {
         mt = 20;
         gM();
      }
   }

   private static void gM() {
      if (!com.yiyiaddon.i.c.fp()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3r1qxlfmadkbh","zfrPZS2aka0jvmUrxAEEe9x6UuXmjsB3Y/Nf0ILXUOg=",-8227686664986445310,-3433574742845017648,2492600510489107102,9089029192438866829>()) {
            case 1437847285:
               return;
            default:
               throw null;
         }
      } else {
         f var0 = f.a();
         Iterator var1 = com.yiyiaddon.i.a.bA().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"stbrmgaazkz07","FjHkKyyKVkqx3l2x0mvDfD71QNwSGShcTclon3JO5Lk=",9064865968820206692,3534038968026545865,-2177513050721671021,7371412995619882292>()) {
            case -372215057:
               while (var1.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s28p57907l7y9n","fzrBZTS2swfvHU2Z31IC0cWCZI/Sd3N+sndiWe38BME=",9000940016651857300,727210738624927061,-962991614225479080,-6914949026262443683>()) {
                     case 699695809:
                        Component var2 = (Component)var1.next();
                        var0.a(
                           var2,
                           (String)com.yiyiaddon.m.b.a<"s15xkcokgw3yrn","Y4Df2flBF8/L6Kal+OXX+3gkkFT3SyX05DdSNNXJtiUqye/RkunMH0Jbu01freiS",-7010447060585185610,8600713052118916641,-5159048964177910117,2729693006438659994>()
                        );
                        switch ((int)com.yiyiaddon.m.b.a<"sf0gyjsiudqjw","NREFj5MXU5pNXUdSSUkrxGGVNUnCZzplsPj4TCCEquo=",2016357316991876393,2304007155774902110,8207086119323854689,1080834150305564388>()) {
                           case 970103298:
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
}
