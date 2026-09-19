package com.yiyiaddon.e.n.i;

import java.util.Locale;

public record f(String sy, String sz, String sA, String sB, c f, String sC, int mi, String sD) implements s {
   @Override
   public com.yiyiaddon.e.n.o.d a() {
      return com.yiyiaddon.e.n.o.d.SPRINKLER;
   }

   public boolean Z(String var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1uub2qysp36yc","wduMqOGcfDJJXdW7HT9F1f2e3AbPsGQ7qFcMNe1Mm5I=",-1282352120656212429,-867435501048470966,462256080458824151,3400871439208464216>()) {
            case -1682757998:
               if (this.sD != null) {
                  String var2 = var1.toLowerCase(Locale.ROOT);
                  String var3 = this.sD.toLowerCase(Locale.ROOT);
                  if (!var3.endsWith(var2 + "")) {
                     label29:
                     switch ((int)com.yiyiaddon.m.b.a<"sd2fi09hoca8q","d6N6AVHNO71YO1dGKqCePR9l8NzTJe0zrhO+apaXx9A=",-8793181146523948493,8883916498616177456,-3731443685761242493,6273928330458070825>()) {
                        case 1640033089:
                           if (!var3.equals(var2)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s27s58sloqdb7c","eZL5V8oNCtFfqfUpM++c9Z12sysku3E0aNfRtjYwdH8=",-4662700913928219980,292532910629319546,8844369759574074019,8440237240702127047>()) {
                                 case 340444513:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s2bsoxzcdx7kc0","Rk0JacOmKDiJbZuaUE8SMbKqHBYlbMmJUj60GJNBElY=",659306390740861122,3663624541345408365,1745278638587898445,-6922488466880042866>()) {
                              case -686918674:
                                 break label29;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s3ufjevsjn72vu","V85XWatzBbmJMIHIeBFygiMZn4fug5fA3t5GLLwJHiQ=",-6734204089639882271,-2094121016074083818,-2707510888643183163,7110684997830840433>()) {
                     case 896888659:
                        return true;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2qphdntxl7z4r","oAe2WHSBFcU1uevttBuoypH4yrqHoCoZOCDPrqWMxHI=",-1612017393302742647,7912737884014907415,-2420721065901056949,-7068701295759638561>()) {
                     case -466137458:
                        return false;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return false;
      }
   }

   @Override
   public String L() {
      return this.sy;
   }

   @Override
   public String m() {
      return this.sz;
   }

   @Override
   public String dE() {
      return this.sA;
   }

   @Override
   public String dF() {
      return this.sB;
   }

   @Override
   public c b() {
      return this.f;
   }

   @Override
   public String dG() {
      return this.sC;
   }

   public int bY() {
      return this.mi;
   }

   public String dJ() {
      return this.sD;
   }
}
