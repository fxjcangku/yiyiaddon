package com.yiyiaddon.k;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public final class a {
   private static final int sI = 40;
   private static final DateTimeFormatter b = DateTimeFormatter.ofPattern(
      (String)com.yiyiaddon.m.b.a<"s3jrsap8e2ifbp","60Ab26OXjBI5gcPWHtanMmuhw04aUKU5/7lDZTL7HUjLYOzhinOiomGdj/s=",-4764283754731714259,4265289495599967843,6427419449405520186,1581463222108283732>()
   );
   private static final Deque<String> i = new ArrayDeque<>();

   private a() {
   }

   public static void bk(String var0) {
      if (var0 != null && !var0.isBlank()) {
         String var1 = b.format(Instant.now().atZone(ZoneId.systemDefault())) + var0.strip();
         synchronized (i) {
            i.addFirst(var1);

            while (i.size() > 40) {
               i.removeLast();
            }
         }
      }
   }

   public static List<String> d(int var0) {
      if (var0 <= 0) {
         return List.of();
      }

      synchronized (i) {
         ArrayList var2 = new ArrayList(Math.min(var0, i.size()));

         for (String var4 : i) {
            if (var2.size() >= var0) {
               break;
            }

            var2.add(var4);
         }

         return var2;
      }
   }
}
