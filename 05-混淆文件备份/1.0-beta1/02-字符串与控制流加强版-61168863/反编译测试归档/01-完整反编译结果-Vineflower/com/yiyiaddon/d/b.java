package com.yiyiaddon.d;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public final class b {
   private b() {
   }

   public static Thread a(String var0, Runnable var1) {
      Thread var2 = new Thread(var1, var0);
      var2.setDaemon(true);
      var2.start();
      return var2;
   }

   public static ScheduledExecutorService a(final String var0, long var1, TimeUnit var3, Runnable var4) {
      ScheduledExecutorService var5 = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
         @Override
         public Thread newThread(Runnable var1) {
            Thread var2 = new Thread(var1, var0);
            var2.setDaemon(true);
            return var2;
         }
      });
      var5.scheduleAtFixedRate(() -> {
         try {
            var4.run();
         } catch (Throwable var2) {
         }
      }, 0L, var1, var3);
      return var5;
   }
}
