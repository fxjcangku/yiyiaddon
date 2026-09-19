package com.yiyiaddon.k;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public final class e {
   private static final long aP = 60L;
   private static final AtomicBoolean a = new AtomicBoolean();
   private static final AtomicBoolean b = new AtomicBoolean();
   private static volatile int sK = -1;
   private static volatile int se = -1;
   private static volatile boolean fu;
   private static volatile long aQ;
   private static volatile String Cr;
   private static volatile String Cs;
   private static volatile boolean fv;

   private e() {
   }

   public static void aR() {
      if (!a.compareAndSet(false, true)) {
         switch ((int)com.yiyiaddon.m.b.a<"skz8k6esa1hnw","3fowlRWVBx9mqkf2BDSLmtJxDxk+SpUD7/X6wVLpqq4=",-640153788027849449,80727076974606531,2169002038539136099,7922236173508674779>()) {
            case -713892692:
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.d.b.a(
            (String)com.yiyiaddon.m.b.a<"s5tr5v00mqwff","buPCXnmNQiDL4xUGepI882nRHgqngag/MNcW5W+IiZQdx/9ORqXRAXC0uUuCwAo5hZTczvr8sFbpxvdOQ/1/VISgVk0=",7976548230088608742,8612334959431423216,-1731312305047975042,-8274936218454874992>(),
            60L,
            TimeUnit.SECONDS,
            e::D
         );
      }
   }

   public static void c(int var0, int var1) {
      if (var0 > 0) {
         label23:
         switch ((int)com.yiyiaddon.m.b.a<"s82b0z0nqowc7","nTKpUyS9jW7L1i+kEubBIR6X05w5zsKyVOP4TOu+kf0=",-7101157491966769612,-8612484585594722930,6604256333027904558,-1415634207684272176>()) {
            case 1442425151:
               sK = var0;
               switch ((int)com.yiyiaddon.m.b.a<"s1e4djkv81bugz","nh6Pq4QEtoGYik3BE/to5R9rmzx/jn1KRSaWTTZC6Z0=",-5582264823406981714,7603434623594307285,-7475942801366778372,14569632803204423>()) {
                  case 1080537248:
                     break label23;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      if (var1 > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s3sewb92h2xugv","Pizgm0HTSgMtUThqlmaXBOWK3Qk+ZdtD+WxxUa/CcMQ=",-8522733332525206365,-1593269834208065563,-1132963154989029597,8860090681037945701>()) {
            case -1379653782:
               se = var1;
               switch ((int)com.yiyiaddon.m.b.a<"s1f6zprdpofd1m","g6pxeblHIspkHsk2ifMAr3G0CN/ky21J8BUeaP8HN5s=",7811777154308628290,-7958603291472791593,-6638573134766843643,-4142522420708726093>()) {
                  case -1238143558:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public static void jm() {
      sK = -1;
   }

   public static long A() {
      return aQ;
   }

   public static boolean fz() {
      return fu;
   }

   public static boolean fA() {
      return fv;
   }

   public static String fw() {
      return Cr;
   }

   public static String fx() {
      return Cs;
   }

   public static int dG() {
      return sK;
   }

   public static int dp() {
      return se;
   }

   public static boolean eX() {
      return com.yiyiaddon.i.b.eX();
   }

   private static void D() {
      if (b.compareAndSet(false, true)) {
         try {
            com.yiyiaddon.g.b var0 = com.yiyiaddon.i.d.b();
            Cr = var0.fw();
            Cs = var0.fx();
            fv = var0.fw() != null;
            com.yiyiaddon.g.f var1 = j.b();
            if (var1.dp() > 0) {
               se = var1.dp();
            }

            boolean var2 = com.yiyiaddon.d.e.b() >= 0L;
            fu = var2;
            if (var2) {
               aQ = System.currentTimeMillis();
            }
         } finally {
            b.set(false);
         }
      }
   }
}
