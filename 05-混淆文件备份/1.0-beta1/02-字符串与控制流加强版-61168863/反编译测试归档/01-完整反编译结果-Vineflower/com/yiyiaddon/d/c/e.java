package com.yiyiaddon.d.c;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;

public final class e {
   private static final long g = 5L;
   private static final PriorityQueue<e.a> a = new PriorityQueue<>(Comparator.comparingLong(e.a::e));
   private static ScheduledExecutorService a;
   private static long h;

   private e() {
   }

   static void a(Connection var0, Packet<?> var1, long var2) {
      if (var0 != null && var1 != null) {
         synchronized (a) {
            long var5 = System.currentTimeMillis();
            long var7 = Math.max(var5 + Math.max(0L, var2), h + 1L);
            h = var7;
            a.add(new e.a(var0, var1, var7));
            v();
         }
      }
   }

   public static void b() {
      synchronized (a) {
         a.clear();
         h = 0L;
      }
   }

   public static int m() {
      synchronized (a) {
         return a.size();
      }
   }

   private static void v() {
      if (a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sibt3znkuqs41","HEOK4eymuiynwmvFt41WckTNUKr6LMmsX2XRM1g7zIs=",7306033312521857151,-7706867430300532827,-8481298818798370876,7254664870116404447>()) {
            case 1049252030:
               return;
            default:
               throw null;
         }
      } else {
         a = Executors.newSingleThreadScheduledExecutor(
            var0 -> {
               Thread var1 = new Thread(
                  var0,
                  (String)com.yiyiaddon.m.b.a<"s2v7o1wv3f1ua","tFK3qaDKekVY+Tcfxbmz+uxXFRyx+b54DuMkaIqm6Uwf3vWPJTi1y+/XuEDIdg6AYaSY4ZOZxr7NFgSzeYfGz2yG",-2795850659481815287,-3210792936450415687,-2907748814019558593,-5612554069507717717>()
               );
               var1.setDaemon(true);
               return var1;
            }
         );
         a.scheduleAtFixedRate(e::w, 0L, 5L, TimeUnit.MILLISECONDS);
      }
   }

   private static void w() {
      long var0 = System.currentTimeMillis();
      ArrayList var2 = new ArrayList();
      synchronized (a) {
         while (!a.isEmpty() && a.peek().e() <= var0) {
            var2.add(a.poll());
         }
      }

      if (!var2.isEmpty()) {
         Minecraft var7 = Minecraft.getInstance();

         for (e.a var5 : var2) {
            var7.execute(
               () -> {
                  if (var5.a().isConnected()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1v5nnriot7upr","TG1n5jmlfdCdawo0ybsDNLfcJOnZYNUFIoQMa/N0Pe8=",-2908762544910100510,-3568310876332094310,2425394733696713618,-8628362137937454403>()) {
                        case 1036578172:
                           b.a(var5.a(), var5.a());
                           switch ((int)com.yiyiaddon.m.b.a<"s3o6tafmyga62y","6Sv47u377jAds62aii6S081zDSKg9wZHkQ4x/zd/L5I=",-7342527297313057059,6059761412360138969,-8863866393820712466,4940772878461878470>()) {
                              case -265054253:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }
               }
            );
         }
      }
   }

   private record a(Connection a, Packet<?> b, long i) {
      public Packet<?> a() {
         return this.b;
      }

      public long e() {
         return this.i;
      }
   }
}
