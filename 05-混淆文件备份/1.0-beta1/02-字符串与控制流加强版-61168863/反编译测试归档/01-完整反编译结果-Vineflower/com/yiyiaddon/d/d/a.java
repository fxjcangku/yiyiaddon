package com.yiyiaddon.d.d;

import com.yiyiaddon.d.d;
import com.yiyiaddon.m.b;
import java.util.UUID;

public final class a {
   private static volatile String Q;
   private static volatile d.a a;

   private a() {
   }

   public static void a(String var0, d.a var1) {
      if (var0 != null) {
         switch ((int)b.a<"s33fmk2sd544ec","qpJSMHTVka+lxQJiZtbctJeNPzsTfczgsHBCXAN4ef8=",4053092042432720791,-5672873298211167524,2539473678795890737,5087202208220544094>()) {
            case 2111533031:
               if (!var0.isBlank()) {
                  switch ((int)b.a<"s2k09yfa7sc2ve","hthOoIQFJC80ylLQWCvyahGiuDnQGmuUzqJG/9sObMA=",1807361016948846330,-5688525301587457633,2285644335837883977,7379142473220689509>()) {
                     case -1478630591:
                        if (var1 != null) {
                           Q = var0;
                           a = var1;
                           return;
                        }

                        switch ((int)b.a<"s2h7ds8kk3nkeq","nuFUDGhMKkBeGYvs11DokwKViSkc30Pq+B5WYRyxgOk=",689146831728249767,6261167264589514734,-6298267544828205271,5703212919698975595>()) {
                           case 1186552597:
                              return;
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
   }

   public static void l(String var0) {
      if (var0 != null) {
         switch ((int)b.a<"stkonmzma2ont","BI0hlsKKrsrr7HaYjw6Jn8FuYgiecfncmCAOv6ehU3U=",1314845452136765975,-8590553320454904407,7106467033548497637,4557155647879689226>()) {
            case 1422626931:
               if (var0.equals(Q)) {
                  a = null;
                  Q = null;
                  return;
               } else {
                  switch ((int)b.a<"s2t9xay3220hh6","jEm/zpSmk7oaLrb4lKVqHouCm+hyo+kEkLtOorOHcgM=",2721160013182913875,-4318274713733738673,432918461928536964,-1513775948930907542>()) {
                     case 331579234:
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

   public static boolean n() {
      if (a != null) {
         switch ((int)b.a<"s6h2kwsgxndjn","E1DAwvUESmjkmXfRF2CDc/8y7w3mdSNnSvpqoL4spo0=",2815791125490420419,7618686830648014837,-6316114702595923260,-4041252823846991632>()) {
            case -968329740:
               switch ((int)b.a<"s1a0h8woerhk96","Rr42FQgeweBVJfqIkFLNQNSMXNfhsygriIDjdk+24/c=",-8431073213614230274,-1605943076696255406,6476420066882116456,-6245101332153795664>()) {
                  case -713146357:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)b.a<"s2tsjzommshyda","o7ADvYYHASyNCtKr7SGfILMiBSmSFASDw8SWwxC+X7I=",-2625323110047893680,3585785314807619126,7152072060215201996,-5346993746125052010>()) {
            case -887295835:
               return false;
            default:
               throw null;
         }
      }
   }

   public static boolean handle(UUID var0, String var1, String var2) {
      d.a var3 = a;
      if (var3 == null) {
         return false;
      }

      try {
         return var3.handle(var0, var1, var2);
      } catch (Throwable var5) {
         return false;
      }
   }

   @FunctionalInterface
   public interface a {
      boolean handle(UUID var1, String var2, String var3);
   }
}
