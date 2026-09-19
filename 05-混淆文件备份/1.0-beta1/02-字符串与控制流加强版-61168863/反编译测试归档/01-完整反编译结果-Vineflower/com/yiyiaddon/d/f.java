package com.yiyiaddon.d;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public final class f {
   private f() {
   }

   public static JsonObject b() {
      return new JsonObject();
   }

   public static JsonObject d(String var0) {
      if (var0 != null && !var0.isBlank()) {
         try {
            JsonElement var1 = JsonParser.parseString(var0);
            return var1 != null && var1.isJsonObject() ? var1.getAsJsonObject() : null;
         } catch (Exception var2) {
            return null;
         }
      } else {
         return null;
      }
   }

   public static String a(JsonObject var0, String var1, String var2) {
      JsonElement var3 = var0 == null ? null : var0.get(var1);
      if (var3 != null && !var3.isJsonNull()) {
         try {
            return var3.isJsonPrimitive() ? var3.getAsString() : var2;
         } catch (Exception var5) {
            return var2;
         }
      } else {
         return var2;
      }
   }

   public static int a(JsonObject var0, String var1, int var2) {
      JsonElement var3 = var0 == null ? null : var0.get(var1);
      if (var3 != null && !var3.isJsonNull()) {
         try {
            return var3.isJsonPrimitive() && var3.getAsJsonPrimitive().isNumber() ? var3.getAsInt() : Integer.parseInt(var3.getAsString().trim());
         } catch (Exception var5) {
            return var2;
         }
      } else {
         return var2;
      }
   }

   public static long a(JsonObject var0, String var1, long var2) {
      JsonElement var4 = var0 == null ? null : var0.get(var1);
      if (var4 != null && !var4.isJsonNull()) {
         try {
            return var4.isJsonPrimitive() && var4.getAsJsonPrimitive().isNumber() ? var4.getAsLong() : Long.parseLong(var4.getAsString().trim());
         } catch (Exception var6) {
            return var2;
         }
      } else {
         return var2;
      }
   }

   public static boolean a(JsonObject var0, String var1, boolean var2) {
      JsonElement var3 = var0 == null ? null : var0.get(var1);
      if (var3 != null && !var3.isJsonNull()) {
         try {
            if (var3.isJsonPrimitive() && var3.getAsJsonPrimitive().isBoolean()) {
               return var3.getAsBoolean();
            } else if (var3.isJsonPrimitive() && var3.getAsJsonPrimitive().isNumber()) {
               return var3.getAsInt() != 0;
            } else {
               String var4 = var3.getAsString().trim();
               if (var4.equalsIgnoreCase(
                     (String)com.yiyiaddon.m.b.a<"s1m3qz557h9uhy","ORRdxryYEsZd+Wwy2+gt9fKScM+vA2Eq1DdNWzwNQ0rf3F5e",6194833752899145874,-933601335682316861,3584183371262785357,8115578189548362740>()
                  )
                  || var4.equals(
                     (String)com.yiyiaddon.m.b.a<"s263s1r6e261xp","gbgrXYy/DkdWj/CFIx9Ra6j8+ZgEVKRuyRLWNaWe",3023608280466667671,-3226980275087712991,4614300867102196395,-4240069015906954285>()
                  )) {
                  return true;
               } else {
                  return !var4.equalsIgnoreCase(
                           (String)com.yiyiaddon.m.b.a<"s6a4esg2evs92","UWD7/zvgZU6MvRZQD4ZVa+ibLlcrk62/8obXYFr7IK1LbVvAjfs=",3580133748524445663,-143214941440986531,-2288934943684938905,6095452484417916543>()
                        )
                        && !var4.equals(
                           (String)com.yiyiaddon.m.b.a<"s2a9daz2orq70m","MC4B75F0tEk2w2IicsfWPnOO/NRRJ/4a8yj1auMC",-3169601077022581428,8116103119947376458,1221319523610326427,3963283589473682963>()
                        )
                     ? var2
                     : false;
               }
            }
         } catch (Exception var5) {
            return var2;
         }
      } else {
         return var2;
      }
   }

   public static JsonArray a(JsonObject var0, String var1) {
      JsonElement var10000;
      if (var0 == null) {
         label32:
         switch ((int)com.yiyiaddon.m.b.a<"s19gnxfz8rmiyt","eo07272/aqZu4ebxAQTEd37U2mUCMVyPNyjF/TpcaF4=",6741547228514094737,-5618891264066557752,25818304367121677,8338163386577750430>()) {
            case -1426599564:
               var10000 = null;
               switch ((int)com.yiyiaddon.m.b.a<"s2uf5j25plw3at","B0KX68NAtrNrB5aygOLWG3/+NsQ/ngM8uIt//PRMbI0=",-751864425305225006,-906990838782172841,-3516480994573583896,2127820686742274078>()) {
                  case -61495804:
                     break label32;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var0.get(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s2305qjh3p7l4j","obxdrDzuidQSeYvJiCtdmi1hXtGvZoO1mmRhf/o78f4=",-8172807585488605718,-1972299760188227023,-8579371577695983401,3637956340409250410>()) {
            case 1983703765:
               break;
            default:
               throw null;
         }
      }

      JsonElement var2 = var10000;
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sqqogbtun0mx4","Q73d27cOXsBwIN7yWYXO7eJjr6uAZd4Ww8vZ4cggh4g=",-6037299822053504502,7310676785816610920,-8326864748157558743,8794466685145612772>()) {
            case -856192643:
               if (var2.isJsonArray()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2yltcwdxevfd4","UvtPC+y79XVzP5A71vd4bJpV6TeGSZJ4GkItF2RyLl0=",-5140786521719403542,8812335844391242277,7142960912309276840,5046462994203496449>()) {
                     case 1699603798:
                        var10000 = var2.getAsJsonArray();
                        switch ((int)com.yiyiaddon.m.b.a<"sqrqw8m3cf3aq","kFmaUxaqNq5GHWSOu5+MitW0OkzBGxZZuSGm5+jF+pA=",-14141904209311846,2018360750621988095,-2293853272242850375,5530715284882694839>()) {
                           case 979937145:
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

      var10000 = new JsonArray();
      switch ((int)com.yiyiaddon.m.b.a<"s1ua27i838d4cz","gbVLDISPURu8LQH6jOgdYF06J+BHav+cQ1PhKOrf23U=",-6715812199983509140,507934564707692530,-3685372289335130929,720377677527958947>()) {
         case 1506724118:
            return var10000;
         default:
            throw null;
      }
   }
}
