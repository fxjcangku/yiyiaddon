package com.yiyiaddon.c;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class b {
   private static final Logger f = LoggerFactory.getLogger(
      (String)com.yiyiaddon.m.b.a<"s17i4tabpyth5g","331sgRdHhOL/xHxyjp3yjkeMYcyHYTt685EXJMGsS6kCCtpvT6le5jKlF/WA+h0R1hpoMNy3RdKxD09pIGuPeDWfn3EcTk86",3098238023879031806,-6723436914642915275,-1710497236931875626,2516983147872193150>()
   );
   private static final String s = (String)com.yiyiaddon.m.b.a<"s26a9nz9erdkre","TWA887tYU96/MAFfI+FhiM9GMq6xgs6dW4TMI1IV6RefHl1nld+iTroBXKAuQB49yoq710iPbErtH6Kojl8=",4654921556714838752,747994314708368595,-758704512804102576,3101828948614132390>();
   private static final String t = (String)com.yiyiaddon.m.b.a<"s23lvqhbzl6ei0","S+FQkAQOuTsTacqHiTwRrcPOJn49822by7avaCioN+I=",-7218624670974980911,2685486041286038849,-7935131074346542369,-8840122749654140686>();
   private static final String u = (String)com.yiyiaddon.m.b.a<"s2cz7sd29k2v14","1Y/K886BWP8g/rIsJNO3zudJymOB8i18Axb74hSQEZo=",-3666266385827267054,6166144847856143687,-8969222483416819704,7065120694040909531>();
   private static final String v = (String)com.yiyiaddon.m.b.a<"s28sfaxhk3e667","hF76eBaa2Q1zBkpsy48JtCff/OXpbElTnt9EFa8/51opjw==",-7808022615700465161,-1279886772405245809,770834629147247539,7265163107216774609>();
   private static final String w = (String)com.yiyiaddon.m.b.a<"s23kfuhy0e7mq3","EaEEB0WaFqs0zCYZQ8I8WDiIEyqMMenTCkXRdUypBa8=",5522426747476629601,1649285390461805075,2814106077319246093,5850930776675505897>();
   private static final String x = (String)com.yiyiaddon.m.b.a<"s1cmlt9p1226hj","xuPMobr7GfqR3dI52wwMMFklRRFvIut04IM3tcCbsm8o9a3u1hM=",-5181779936928671217,6846121065893334839,8035193332399922590,-1483237688554570206>();
   private static final Map<String, JsonObject> c = new LinkedHashMap<>();
   private static boolean f;

   private b() {
   }

   private static Path a() {
      return FabricLoader.getInstance()
         .getConfigDir()
         .resolve(
            (String)com.yiyiaddon.m.b.a<"s26a9nz9erdkre","TWA887tYU96/MAFfI+FhiM9GMq6xgs6dW4TMI1IV6RefHl1nld+iTroBXKAuQB49yoq710iPbErtH6Kojl8=",4654921556714838752,747994314708368595,-758704512804102576,3101828948614132390>()
         );
   }

   public static synchronized void d() {
      if (!f) {
         f = true;
         Path var0 = a();
         if (var0.toFile().isFile()) {
            JsonObject var1 = com.yiyiaddon.j.a.a(var0);
            if (var1 == null) {
               f.warn(
                  (String)com.yiyiaddon.m.b.a<"sdqrwyxnqppm6","F5JoFx1tlCwJ1lFPc8Qeno0Y7mFHhryWxN1G88b8MRbnMh65MPQAlSEJs9S0sy7qxMdTrxX1v2bYoL4yhQkHOLITVUu2wMhVduU=",-1726004003162684616,-7384997863401813981,988541403291564569,-4338257096733203769>(),
                  var0
               );
            } else {
               JsonElement var2 = var1.get(
                  (String)com.yiyiaddon.m.b.a<"s23lvqhbzl6ei0","S+FQkAQOuTsTacqHiTwRrcPOJn49822by7avaCioN+I=",-7218624670974980911,2685486041286038849,-7935131074346542369,-8840122749654140686>()
               );
               if (var2 != null && var2.isJsonObject()) {
                  for (Entry var4 : var2.getAsJsonObject().entrySet()) {
                     if (var4.getValue() != null && ((JsonElement)var4.getValue()).isJsonObject()) {
                        c.put((String)var4.getKey(), ((JsonElement)var4.getValue()).getAsJsonObject().deepCopy());
                     }
                  }
               }
            }
         }
      }
   }

   public static synchronized void e() {
      JsonObject var0 = new JsonObject();

      for (Entry var2 : c.entrySet()) {
         var0.add((String)var2.getKey(), (JsonElement)var2.getValue());
      }

      JsonObject var3 = new JsonObject();
      var3.add(
         (String)com.yiyiaddon.m.b.a<"s23lvqhbzl6ei0","S+FQkAQOuTsTacqHiTwRrcPOJn49822by7avaCioN+I=",-7218624670974980911,2685486041286038849,-7935131074346542369,-8840122749654140686>(),
         var0
      );
      Path var4 = a();
      if (!com.yiyiaddon.j.a.a(var4, var3)) {
         f.warn(
            (String)com.yiyiaddon.m.b.a<"sc4zvmw6r64zp","hcFRS3+OSSfvDj6hA7j1/kecmH1GgV9bYCWqdgGtuRCkbt3B7W9nrB4GWmZ/PPDAAjA=",-3515827755919139027,-7485476721725623283,-804612520025958151,-8868926155951543578>(),
            var4
         );
      }
   }

   public static synchronized boolean g(String var0) {
      JsonObject var1 = c.get(var0);
      return var1 != null
         && var1.has(
            (String)com.yiyiaddon.m.b.a<"s2cz7sd29k2v14","1Y/K886BWP8g/rIsJNO3zudJymOB8i18Axb74hSQEZo=",-3666266385827267054,6166144847856143687,-8969222483416819704,7065120694040909531>()
         )
         && var1.get(
               (String)com.yiyiaddon.m.b.a<"s2cz7sd29k2v14","1Y/K886BWP8g/rIsJNO3zudJymOB8i18Axb74hSQEZo=",-3666266385827267054,6166144847856143687,-8969222483416819704,7065120694040909531>()
            )
            .isJsonPrimitive()
         && var1.get(
               (String)com.yiyiaddon.m.b.a<"s2cz7sd29k2v14","1Y/K886BWP8g/rIsJNO3zudJymOB8i18Axb74hSQEZo=",-3666266385827267054,6166144847856143687,-8969222483416819704,7065120694040909531>()
            )
            .getAsBoolean();
   }

   public static synchronized void a(String var0, boolean var1) {
      b(var0)
         .addProperty(
            (String)com.yiyiaddon.m.b.a<"s2cz7sd29k2v14","1Y/K886BWP8g/rIsJNO3zudJymOB8i18Axb74hSQEZo=",-3666266385827267054,6166144847856143687,-8969222483416819704,7065120694040909531>(),
            var1
         );
   }

   public static synchronized Integer a(String var0) {
      JsonObject var1 = c.get(var0);
      if (var1 != null
         && var1.has(
            (String)com.yiyiaddon.m.b.a<"s28sfaxhk3e667","hF76eBaa2Q1zBkpsy48JtCff/OXpbElTnt9EFa8/51opjw==",-7808022615700465161,-1279886772405245809,770834629147247539,7265163107216774609>()
         )
         && var1.get(
               (String)com.yiyiaddon.m.b.a<"s28sfaxhk3e667","hF76eBaa2Q1zBkpsy48JtCff/OXpbElTnt9EFa8/51opjw==",-7808022615700465161,-1279886772405245809,770834629147247539,7265163107216774609>()
            )
            .isJsonPrimitive()) {
         try {
            return var1.get(
                  (String)com.yiyiaddon.m.b.a<"s28sfaxhk3e667","hF76eBaa2Q1zBkpsy48JtCff/OXpbElTnt9EFa8/51opjw==",-7808022615700465161,-1279886772405245809,770834629147247539,7265163107216774609>()
               )
               .getAsInt();
         } catch (Exception var3) {
            return null;
         }
      } else {
         return null;
      }
   }

   public static synchronized void a(String var0, int var1) {
      b(var0)
         .addProperty(
            (String)com.yiyiaddon.m.b.a<"s28sfaxhk3e667","hF76eBaa2Q1zBkpsy48JtCff/OXpbElTnt9EFa8/51opjw==",-7808022615700465161,-1279886772405245809,770834629147247539,7265163107216774609>(),
            var1
         );
   }

   public static synchronized void e(String var0) {
      JsonObject var1 = c.get(var0);
      if (var1 != null) {
         var1.remove(
            (String)com.yiyiaddon.m.b.a<"s28sfaxhk3e667","hF76eBaa2Q1zBkpsy48JtCff/OXpbElTnt9EFa8/51opjw==",-7808022615700465161,-1279886772405245809,770834629147247539,7265163107216774609>()
         );
      }
   }

   public static synchronized Set<String> a() {
      return new LinkedHashSet<>(c.keySet());
   }

   public static synchronized JsonObject a(String var0) {
      JsonObject var1 = c.get(var0);
      return var1 != null
            && var1.has(
               (String)com.yiyiaddon.m.b.a<"s23kfuhy0e7mq3","EaEEB0WaFqs0zCYZQ8I8WDiIEyqMMenTCkXRdUypBa8=",5522426747476629601,1649285390461805075,2814106077319246093,5850930776675505897>()
            )
            && var1.get(
                  (String)com.yiyiaddon.m.b.a<"s23kfuhy0e7mq3","EaEEB0WaFqs0zCYZQ8I8WDiIEyqMMenTCkXRdUypBa8=",5522426747476629601,1649285390461805075,2814106077319246093,5850930776675505897>()
               )
               .isJsonObject()
         ? var1.getAsJsonObject(
               (String)com.yiyiaddon.m.b.a<"s23kfuhy0e7mq3","EaEEB0WaFqs0zCYZQ8I8WDiIEyqMMenTCkXRdUypBa8=",5522426747476629601,1649285390461805075,2814106077319246093,5850930776675505897>()
            )
            .deepCopy()
         : new JsonObject();
   }

   public static synchronized void a(String var0, JsonObject var1) {
      if (var1 != null) {
         b(var0)
            .add(
               (String)com.yiyiaddon.m.b.a<"s23kfuhy0e7mq3","EaEEB0WaFqs0zCYZQ8I8WDiIEyqMMenTCkXRdUypBa8=",5522426747476629601,1649285390461805075,2814106077319246093,5850930776675505897>(),
               var1.deepCopy()
            );
      }
   }

   public static synchronized JsonObject a(String var0, String var1) {
      if (var1 != null && !var1.isBlank()) {
         JsonObject var2 = c.get(var0);
         if (var2 == null) {
            return new JsonObject();
         }

         JsonObject var3 = a(
            var2.get(
               (String)com.yiyiaddon.m.b.a<"s1cmlt9p1226hj","xuPMobr7GfqR3dI52wwMMFklRRFvIut04IM3tcCbsm8o9a3u1hM=",-5181779936928671217,6846121065893334839,8035193332399922590,-1483237688554570206>()
            )
         );
         JsonObject var4 = var3 == null ? null : a(var3.get(var1));
         return var4 == null ? a(var0) : var4.deepCopy();
      } else {
         return a(var0);
      }
   }

   public static synchronized void a(String var0, String var1, JsonObject var2) {
      if (var2 != null && var1 != null && !var1.isBlank()) {
         JsonObject var3 = b(var0);
         JsonObject var4 = a(
            var3.get(
               (String)com.yiyiaddon.m.b.a<"s1cmlt9p1226hj","xuPMobr7GfqR3dI52wwMMFklRRFvIut04IM3tcCbsm8o9a3u1hM=",-5181779936928671217,6846121065893334839,8035193332399922590,-1483237688554570206>()
            )
         );
         if (var4 == null) {
            var4 = new JsonObject();
            var3.add(
               (String)com.yiyiaddon.m.b.a<"s1cmlt9p1226hj","xuPMobr7GfqR3dI52wwMMFklRRFvIut04IM3tcCbsm8o9a3u1hM=",-5181779936928671217,6846121065893334839,8035193332399922590,-1483237688554570206>(),
               var4
            );
         }

         var4.add(var1, var2.deepCopy());
      }
   }

   private static JsonObject a(JsonElement var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ar80ak799chm","mxuNYO+HSwq9Bgd8wVsNTmJw4n5bX/MLNoQ91F2Tsgk=",-7075561510412486820,8113127558394429992,7989968640280089335,-5915223597676706910>()) {
            case 398136856:
               if (var0.isJsonObject()) {
                  switch ((int)com.yiyiaddon.m.b.a<"srgdkm974pcnm","8yLjcRE+HmskKdIBidGY0EYG12Tv6bIM1/8lT021xYU=",-6344397396850037215,6534644144401816424,676841929275240901,9169737531047400000>()) {
                     case -2083585165:
                        JsonObject var10000 = var0.getAsJsonObject();
                        switch ((int)com.yiyiaddon.m.b.a<"s2lw585yi8m4uw","amQfcYK33LiX63lIhUcZBNOdMwTSLBOiJOAkWWfYGMk=",5237581483295589798,4591846710832459598,-763999089582969671,-4350373689719241623>()) {
                           case -360044415:
                              return var10000;
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

      switch ((int)com.yiyiaddon.m.b.a<"swa5i83yz8lzx","SfOgG0X7zC/i1FtXMQk/LZB7FNCME3bACc+0d/qfEEo=",-4743963739496087404,-3010559798623835614,3444264259681867535,-3989504453410167084>()) {
         case 82913531:
            return null;
         default:
            throw null;
      }
   }

   public static synchronized String f() {
      int var0 = 0;

      for (String var2 : c.keySet()) {
         if (g(var2)) {
            var0++;
         }
      }

      return "" + c.size() + var0;
   }

   private static JsonObject b(String var0) {
      return c.computeIfAbsent(var0, var0x -> new JsonObject());
   }
}
