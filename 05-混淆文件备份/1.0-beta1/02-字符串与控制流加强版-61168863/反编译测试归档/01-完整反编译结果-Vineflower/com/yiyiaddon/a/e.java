package com.yiyiaddon.a;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class e {
   private static final Logger d = LoggerFactory.getLogger(
      (String)com.yiyiaddon.m.b.a<"s2uziz9rmmatal","t4qFBv0RaQq7HmBa+w22daXstgeU56tatApgR8+nrZZ/DyCEgI1rkF2uHOskf/gcwzVKkQrCgSCeMFl5M7k=",-3801589077873247236,-3641312473172690364,2762029233318371851,-7531331394690029280>()
   );
   private static final Map<String, a> a = new LinkedHashMap<>();
   private static final Map<String, a> b = new LinkedHashMap<>();

   private e() {
   }

   public static synchronized void a(a var0) {
      if (var0 != null && var0.a() != null && !var0.a().isBlank()) {
         a var1 = a.put(c(var0.a()), var0);
         if (var1 != null) {
            d.warn(
               (String)com.yiyiaddon.m.b.a<"s2059fatnmb23x","/jUpmuUnukaQcXyojhOA90r/2MkXFV5dC2RcXUnMHsJViU3l+eQGHjkZVti/roKYKh/CzRDetViQPzWe/Q8=",-655392321176393056,-2747123059068844298,-4174641626729741054,-5848720338104421243>(),
               var0.a()
            );
            b.values()
               .removeIf(
                  var1x -> {
                     if (var1x == var1) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1k12qtkbl02o6","eoG/HCfyH7u1ktypaeGvXkQRbROWMJLq+jBPgfIU2i4=",-2373796772061923542,5198591342629795311,-1696367891722090812,-2381302633311118055>()) {
                           case 677194127:
                              switch ((int)com.yiyiaddon.m.b.a<"s12rmlcl4wkcat","kI/sRqKk3GWvaftvyyLeheW7AsU8PP1fFIE3jQRQ0tI=",-3237670181920783698,-6115733325360686783,8572794438606961898,459989877650622211>()) {
                                 case 1131001806:
                                    return true;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        switch ((int)com.yiyiaddon.m.b.a<"s3rx67ovbm8nil","8Dr0gAeOt1EKlLpKZp89AETOdwjEgUdzQMYAG21bkoo=",-8308953156766867560,3663553985967765177,-8484116379735212230,1291305367095053562>()) {
                           case -911494419:
                              return false;
                           default:
                              throw null;
                        }
                     }
                  }
               );
         }

         b.put(c(var0.a()), var0);

         for (String var3 : var0.a()) {
            if (var3 != null && !var3.isBlank()) {
               b.put(c(var3), var0);
            }
         }
      }
   }

   public static synchronized a a(String var0) {
      return var0 == null ? null : b.get(c(var0));
   }

   public static synchronized List<a> c() {
      return List.copyOf(a.values());
   }

   public static synchronized List<String> b() {
      ArrayList var0 = new ArrayList();

      for (a var2 : a.values()) {
         for (String var4 : var2.b()) {
            if (e(var4)) {
               var0.add(var4);
            }
         }
      }

      return var0;
   }

   private static boolean e(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1zo9de1xlt5s2","Jfi4kttdNpGNDWLcPoHRX2qB4zYiG8jhtFr65UqbYLY=",-1793202718837656930,-793154409274498826,-7900979117673297397,765813456065471960>()) {
            case 1551087760:
               if (!var0.isBlank()) {
                  int var1 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s3s1dbz7gashaf","ETIva1Tn08tb4QEVL+ZI0oztE5g5rhTaiovYYND2eyE=",6488954513452858637,3127212767872657397,-5474426106629481720,-3575577148651643677>()) {
                     case 486487172:
                        while (var1 < var0.length()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2o0pzymjsb5s5","1H7xckVeVeplT1csWhL2UEut+jDBim3tIPc4oL+1GSE=",885275815018130501,-1083709180280573633,-295415142859068551,2428175379177140788>()) {
                              case 1727591567:
                                 if (var0.charAt(var1) > 127) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1px3h7083nq9j","CcaSx7rkVsCMahq519s/to0vMt0YJurYLk0nILkv63o=",-4636561432950398041,5850597462605574383,-7017652563235496675,-5278500855441960093>()) {
                                       case -966863599:
                                          return false;
                                       default:
                                          throw null;
                                    }
                                 }

                                 var1++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1nc6yp4qm9vng","O9NF+UBUAyA7tueXb6BpgB3cFf6+BEXI3tnl/OnAJmY=",441441708881681425,-1639506111602248841,3921809727001957623,-2838806488641918978>()) {
                                    case 63606668:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return true;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1gkekoynlc60d","NPQagBiF5ZRDIKFKIn5JuzrxgzwvJOhZH3R0el4p+Kg=",5538533043408167554,-192051800451012362,527901319937305081,-8410148951862657499>()) {
                     case 1004227846:
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

   public static synchronized int b() {
      return a.size();
   }

   public static synchronized void b() {
      a.clear();
      b.clear();
   }

   private static String c(String var0) {
      return var0.trim().toLowerCase(Locale.ROOT);
   }
}
