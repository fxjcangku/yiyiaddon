package com.yiyiaddon.e.j.e;

public enum d {
   MINERAL(
      (String)com.yiyiaddon.m.b.a<"s28gnpi81bxt6f","YccOrbGI1VGWy41ZOq8PdeVRX9/zQh578s2qzLerOe5uTA==",-4047080247228851640,-6824638007655861850,999624767539277717,1311091980412326749>(),
      (String)com.yiyiaddon.m.b.a<"sov9dv2se89q5","bs9Yy+Cr4fW04i70Uq81GPSL2eyAnlQH0yH66Ko5ybun908YwpVy32DN",-8444613967328181921,2170872646206243523,-7985818831524793531,2551274453289251367>()
   ),
   FOOD(
      (String)com.yiyiaddon.m.b.a<"s30fdk682nywdk","B24njCHkMRcv6Ng5/gf0l6wmAW0FDu5WkSX1WVnfriujYw==",-6278308872701554338,3173170621305254690,-2111556503188649400,1005794764004047572>(),
      (String)com.yiyiaddon.m.b.a<"sjepyv5pxkvks","hkjQdlAhrrxBWE50jScFuy12uRt5KhbGceF8oai0ep5ThLus",7280226081592170316,-370806499021435941,-110496317661463438,9037909898037905001>()
   ),
   AFK(
      (String)com.yiyiaddon.m.b.a<"s1bg415t2b1t80","KE1up3vgeIxkuGv3zRWec4tqEAT6MJ/IhmUdHO3daCV+za2XDo8=",-4045662408623872609,-6510496275435431138,6957734377299024006,5516012097574858406>(),
      (String)com.yiyiaddon.m.b.a<"suq5njab722dg","mXN39WPBe5EqTYNro75DJ2yrNPcA8JRXakFXtbCWPNM3LA==",-725816599681943101,8480700061895848650,4250071263582075729,-4206868213850560898>()
   );

   private final String mW;
   private final String mX;

   d(String var3, String var4) {
      this.mW = var3;
      this.mX = var4;
   }

   public String m() {
      return this.mW;
   }

   public String aM() {
      return this.mX;
   }

   public static d b(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1z562vm6ebqoy","qdqJfdDPXOHdRRmUmmIGRePfO8xwcVYxNLp9a1OzmPk=",-4457548540477320750,639664666233987345,-6991687859482082155,4426481528808913980>()) {
            case -431425583:
               return null;
            default:
               throw null;
         }
      } else {
         d[] var1 = values();
         int var2 = var1.length;
         int var3 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s1v79a5lq2juhu","4s5LxJalVMR3iaTE2YV2E3FxpPXZr1hNGwXVkM54BtE=",-3674329094087758201,4541700758846178827,-7532323145684109954,-1585458879637844234>()) {
            case 1061847225:
               while (var3 < var2) {
                  switch ((int)com.yiyiaddon.m.b.a<"s19syituiljs8x","uZC//L+3sd7sR5tELXjTG5lwBCu57gMbFdRPSwV3DoQ=",-7340458748099251598,-5933677951890911196,576245320156926515,8152876161069576443>()) {
                     case -1495820379:
                        d var4 = var1[var3];
                        if (var4.mX.equals(var0)) {
                           switch ((int)com.yiyiaddon.m.b.a<"sb41onfnnabnr","ks1glFXJVvVNWoCoYicsGDJ1+ISk4pazK8kYN51ZefA=",-3294480305534187736,2707870189349326269,-5466570032286034953,3652499958887447583>()) {
                              case -2007594959:
                                 return var4;
                              default:
                                 throw null;
                           }
                        }

                        var3++;
                        switch ((int)com.yiyiaddon.m.b.a<"s3jvybgzuugmld","qVhcTP5H92KFkus+iA1B+nszeq2Az/EpF3cEA1ZK8Pg=",-7898824072575899906,2920052435496054855,-5449098141020047389,-5854419173319646240>()) {
                           case -1148455209:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return null;
            default:
               throw null;
         }
      }
   }
}
