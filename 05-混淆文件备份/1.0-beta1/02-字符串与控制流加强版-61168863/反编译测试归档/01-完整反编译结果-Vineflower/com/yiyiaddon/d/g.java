package com.yiyiaddon.d;

import java.util.ArrayDeque;

public final class g {
   private static final long d = 5000L;
   private static final int h = 20;
   private static final ArrayDeque<Long> a = new ArrayDeque<>();

   private g() {
   }

   public static void h() {
      long var0 = System.currentTimeMillis();
      a.addLast(var0);
      long var2 = var0 - 5000L;
      switch ((int)com.yiyiaddon.m.b.a<"s2l2l0kwl3loem","Pr3x5ppycY+TMb13kBzPtmkyjHJYmeJ4ASokwlPCfcA=",3246012732942748212,1005597637812220956,-5453615154166997381,2072904586890495571>()) {
         case -490618444:
            while (!a.isEmpty()) {
               switch ((int)com.yiyiaddon.m.b.a<"s15rq2tl1dt9qf","P/YnAUYsR5SA391D4WYPravAdYijjQ3Qg6zCNyDEBP8=",5744598988504219274,4157333109087739852,-4642073386335940438,7107104814640280938>()) {
                  case -268878255:
                     if (a.peekFirst() >= var2) {
                        return;
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3prtqvvcsn3x2","PbcTjpREcHtqnpeXrxnSu728eE953Cuy/Y9Ch+AFNjA=",-4089757548113723532,-7416178659146664891,7757791877590754336,6709554853323476963>()) {
                        case 42472693:
                           a.removeFirst();
                           switch ((int)com.yiyiaddon.m.b.a<"so2yl0x7tlrtb","9DYWiXebH66NYDQWa9RXL4oGpMhUsm6w3/OceIn+czo=",9191456506374000753,306225616745001454,-1893765826356914181,5274138093263046790>()) {
                              case -1498499709:
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

            return;
         default:
            throw null;
      }
   }

   public static float a() {
      if (a.size() < 20) {
         switch ((int)com.yiyiaddon.m.b.a<"s2jytsqlpjdej7","YCzFKmv57tSX206/qDrBBKAhksFhgfOvP/MJl2Ed6NE=",2534697239158077794,-5345743447200089062,-8234042244531347880,5739562732280891275>()) {
            case -1785772274:
               return 0.0F;
            default:
               throw null;
         }
      } else {
         Long var0 = a.peekFirst();
         Long var1 = a.peekLast();
         if (var0 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"shxr88ld03a6n","SvTghrbq9nzx8zDfL8P8PH8/aB20YV8QZ92MOcw6M70=",6434241463704147514,3562055727149089706,1906559556482514,6746579113192628663>()) {
               case 182191437:
                  if (var1 != null) {
                     long var2 = var1 - var0;
                     if (var2 <= 0L) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2ityq690ws4bp","tOVdtDt4caFwQFuwAuBl+DdVhd71vyNusyKwCTLm+Oc=",5111450512891606940,7179711339428361917,784435752054268865,449910894009047777>()) {
                           case 561303706:
                              return 0.0F;
                           default:
                              throw null;
                        }
                     }

                     return (float)((a.size() - 1) * 1000.0 / var2);
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s3u91smf3q545s","d7ldvYdqJ1RTf92p1sfG5yUNROHNUiIeK+UUfNPgiyQ=",2947831048013026096,231529642322797493,-3483628142520540660,7616207932271978940>()) {
                        case 1845772076:
                           return 0.0F;
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         } else {
            return 0.0F;
         }
      }
   }

   public static void b() {
      a.clear();
   }
}
