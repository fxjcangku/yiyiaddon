package com.yiyiaddon.c;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import net.fabricmc.loader.api.FabricLoader;

public final class a {
   private static final Gson a = new GsonBuilder().setPrettyPrinting().create();
   private static final String n = (String)com.yiyiaddon.m.b.a<"s3b0isek5xife7","v5V7OIcQGizZ0im38oBkAUc0MMgDdO8t4e+cM3dF0SamoEis0fU+ltLLWHqDGVeT3JMu5/o6zns=",-1714689310143549001,-9108160298980392929,-5127032700815828598,-1100397981877717458>();
   public static String o = (String)com.yiyiaddon.m.b.a<"s3uyvo7nucf1hn","29U2fy4rQKElckk0rfE0PubZKmv4JSMDAV587GtNKbZjfQ53rvAT0yoauT7aAv9T",-5634964137858020099,-5649548678988989862,8247056197673740478,-5053873416084079087>();
   public static int b = 1;
   public static boolean d = true;
   public static float a = 0.6F;
   public static int c = 1343229972;
   public static float b = 1.0F;
   public static String p = (String)com.yiyiaddon.m.b.a<"sf6m9fb9xoqat","vPW10Iv3VU/Ewjjo0zTF9ITcsapNjmXjjY7TwQ==",5885494336052408078,-8336735102522271295,6435902734680984094,2144997479329186116>();
   public static boolean e = true;
   public static String q = (String)com.yiyiaddon.m.b.a<"s26vfl9ptj650y","s/czHrUmfK8FteLij4yU7X5GbUHj655C2AQK5tW/",-80023437197538240,-3800342948883533578,-4027924310841026503,6813342216210304709>();
   public static String r = (String)com.yiyiaddon.m.b.a<"sf6m9fb9xoqat","vPW10Iv3VU/Ewjjo0zTF9ITcsapNjmXjjY7TwQ==",5885494336052408078,-8336735102522271295,6435902734680984094,2144997479329186116>();
   private static boolean f;

   private a() {
   }

   private static Path a() {
      return FabricLoader.getInstance()
         .getConfigDir()
         .resolve(
            (String)com.yiyiaddon.m.b.a<"s3b0isek5xife7","v5V7OIcQGizZ0im38oBkAUc0MMgDdO8t4e+cM3dF0SamoEis0fU+ltLLWHqDGVeT3JMu5/o6zns=",-1714689310143549001,-9108160298980392929,-5127032700815828598,-1100397981877717458>()
         );
   }

   public static void d() {
      if (!f) {
         f = true;
         Path var0 = a();
         if (Files.isRegularFile(var0)) {
            try {
               String var1 = Files.readString(var0, StandardCharsets.UTF_8);
               JsonElement var2 = JsonParser.parseString(var1);
               if (!var2.isJsonObject()) {
                  return;
               }

               JsonObject var3 = var2.getAsJsonObject();
               o = a(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"s3jho5tby97ar2","xviK0AiZWXgdaul6R5ax66J/L+bQmlXRI6M/gqDElgOUXYML7LOvQa28",-4699942223842868373,2794044776800606032,3169090976831682773,-6598232570275402019>(),
                  o
               );
               b = a(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"s3e6mqlei46ctn","2Wy8+NhbZxA+ZCyGEFu9WwviLs5L3t+ZgAJxeVy6B1BHQ26JxFuTVo1I",-8674516893791350171,4822147690071331681,2259273112747272462,2637713773813619194>(),
                  b
               );
               d = a(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"smv5wqtqcpmc","5uxE3u7ohsGwF0oo70GFqu7d4ablnrdBonsO4K9QUuXiCs/ggpzeYvvgcEyzcw==",-1747241094092742191,-5783146083690632317,5637341029790585803,6628518999718844404>(),
                  d
               );
               a = a(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"sy8cfdwcm4tmo","140YijYP7qcYSR/Y9CvreWNAp8iIXRhEx0UubUB7ifjWzAOcIj/RhSpuQ5IbEdoMLBRunA==",1956752653681392478,-3527507907966266595,-2050479477555083342,5484462526630624825>(),
                  a
               );
               c = a(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"s3n54j4od5zhgz","psTMAL9W/HQiuzWIzsy2SCiEpG/mwQyZcpz3XDqwropetQ25UKljd4MUE9Q=",-5221039370328427027,-419765382077287101,-915499479857187888,4679614504871228696>(),
                  c
               );
               b = a(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"s2d7w27jmuedqp","USxHCJ1c17BIfF5b0qFCtoe+vy2W8dgnuy03dfgSEDt3bw0agS+85rritwjCA7G1fqw=",-1006385425918869484,-1787719324607331597,6677795842198415302,-4371925002237828953>(),
                  b
               );
               p = a(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"s1nxtdlairlpg2","/CArbyXS+qMcn01AnyXtU2D0c+abAeXtFOWpy/kikx1dKorgjdfoMOBA9DDaXN6TfCdPeNSRNKc=",2233107799210013596,4230559944471278922,-5576227911197173573,4908080085021539345>(),
                  p
               );
               e = a(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"s19jsffoiiiovj","Ll3UCLio1kWreHPn3AmWM43B+h+uQo6B159HAThiKhszUw6BZAKx5HL5vEgCYOmF8h1dxgpRAt2jKg==",4749850481353938626,3586287873944075815,917580008732573700,-8522003514787486002>(),
                  e
               );
               q = a(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"s2xikvxfj3d9o0","CT0IwWW74m6dIF2mdoWLFro5eN27tFnchG15j56fbotRdc8YIRxPVEh2vXrEgearg2NQix+N",3033288844424965528,9198447208330730414,8299084150527444997,-7714090056455843495>(),
                  q
               );
               r = a(
                  var3,
                  (String)com.yiyiaddon.m.b.a<"s1iqz4prxas3p8","BJh/ek7cfpa1KHhoHvXW+cKAYN8qxpLE1TBe5wihoGH9wgQwqy4GcZGU5U0rsu7ncCyphpWAPKAOQQ==",-8523563340620305792,4492175035490682531,2234743100469222247,8259774725888496014>(),
                  r
               );
            } catch (Exception var4) {
            }
         }
      }
   }

   public static void e() {
      JsonObject var0 = new JsonObject();
      var0.addProperty(
         (String)com.yiyiaddon.m.b.a<"s3jho5tby97ar2","xviK0AiZWXgdaul6R5ax66J/L+bQmlXRI6M/gqDElgOUXYML7LOvQa28",-4699942223842868373,2794044776800606032,3169090976831682773,-6598232570275402019>(),
         o
      );
      var0.addProperty(
         (String)com.yiyiaddon.m.b.a<"s3e6mqlei46ctn","2Wy8+NhbZxA+ZCyGEFu9WwviLs5L3t+ZgAJxeVy6B1BHQ26JxFuTVo1I",-8674516893791350171,4822147690071331681,2259273112747272462,2637713773813619194>(),
         b
      );
      var0.addProperty(
         (String)com.yiyiaddon.m.b.a<"smv5wqtqcpmc","5uxE3u7ohsGwF0oo70GFqu7d4ablnrdBonsO4K9QUuXiCs/ggpzeYvvgcEyzcw==",-1747241094092742191,-5783146083690632317,5637341029790585803,6628518999718844404>(),
         d
      );
      var0.addProperty(
         (String)com.yiyiaddon.m.b.a<"sy8cfdwcm4tmo","140YijYP7qcYSR/Y9CvreWNAp8iIXRhEx0UubUB7ifjWzAOcIj/RhSpuQ5IbEdoMLBRunA==",1956752653681392478,-3527507907966266595,-2050479477555083342,5484462526630624825>(),
         a
      );
      var0.addProperty(
         (String)com.yiyiaddon.m.b.a<"s3n54j4od5zhgz","psTMAL9W/HQiuzWIzsy2SCiEpG/mwQyZcpz3XDqwropetQ25UKljd4MUE9Q=",-5221039370328427027,-419765382077287101,-915499479857187888,4679614504871228696>(),
         c
      );
      var0.addProperty(
         (String)com.yiyiaddon.m.b.a<"s2d7w27jmuedqp","USxHCJ1c17BIfF5b0qFCtoe+vy2W8dgnuy03dfgSEDt3bw0agS+85rritwjCA7G1fqw=",-1006385425918869484,-1787719324607331597,6677795842198415302,-4371925002237828953>(),
         b
      );
      var0.addProperty(
         (String)com.yiyiaddon.m.b.a<"s1nxtdlairlpg2","/CArbyXS+qMcn01AnyXtU2D0c+abAeXtFOWpy/kikx1dKorgjdfoMOBA9DDaXN6TfCdPeNSRNKc=",2233107799210013596,4230559944471278922,-5576227911197173573,4908080085021539345>(),
         p
      );
      var0.addProperty(
         (String)com.yiyiaddon.m.b.a<"s19jsffoiiiovj","Ll3UCLio1kWreHPn3AmWM43B+h+uQo6B159HAThiKhszUw6BZAKx5HL5vEgCYOmF8h1dxgpRAt2jKg==",4749850481353938626,3586287873944075815,917580008732573700,-8522003514787486002>(),
         e
      );
      var0.addProperty(
         (String)com.yiyiaddon.m.b.a<"s2xikvxfj3d9o0","CT0IwWW74m6dIF2mdoWLFro5eN27tFnchG15j56fbotRdc8YIRxPVEh2vXrEgearg2NQix+N",3033288844424965528,9198447208330730414,8299084150527444997,-7714090056455843495>(),
         q
      );
      var0.addProperty(
         (String)com.yiyiaddon.m.b.a<"s1iqz4prxas3p8","BJh/ek7cfpa1KHhoHvXW+cKAYN8qxpLE1TBe5wihoGH9wgQwqy4GcZGU5U0rsu7ncCyphpWAPKAOQQ==",-8523563340620305792,4492175035490682531,2234743100469222247,8259774725888496014>(),
         r
      );

      try {
         Path var1 = a();
         Files.createDirectories(var1.getParent());
         Files.writeString(var1, a.toJson(var0), StandardCharsets.UTF_8);
      } catch (IOException var2) {
      }
   }

   public static int c() {
      return c;
   }

   private static String a(JsonObject var0, String var1, String var2) {
      JsonElement var3 = var0.get(var1);
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3qx0r1mxwohpq","bWq5O4owXtdT9IyPJFBLs2mhSLUN9HGEFHb7Q0VUS4I=",-4480711254303918409,7649182772480896333,-3231862988718915986,1838159221589068113>()) {
            case -1317295949:
               if (var3.isJsonPrimitive()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3aclvse8u40q9","soR55w4bNzgWey4JGziN+LE3nTQ7bV6uYQfEHyP0vAQ=",-7902916363046202150,8148120411592450292,2647182898083609344,4174578372645350246>()) {
                     case 1693344353:
                        String var10000 = var3.getAsString();
                        switch ((int)com.yiyiaddon.m.b.a<"s1oiyi56ges2nb","Ui76h1ESpRLwxbFswuAErA0HK9sRDGaCBJ/Ca4y/Zpc=",-5203320968753041893,1944767350413849223,-1983181014658373925,-3820613031119098844>()) {
                           case 1054611915:
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

      switch ((int)com.yiyiaddon.m.b.a<"s2zmjrvg1v29la","n/GDWzRTviJwdCfG8WLj+pqUBh3O4OW93mh8V76vXvk=",-6999466700600029848,-2888324739100754641,7270438522113109918,-7601901696213971537>()) {
         case -495352537:
            return var2;
         default:
            throw null;
      }
   }

   private static int a(JsonObject var0, String var1, int var2) {
      JsonElement var3 = var0.get(var1);
      if (var3 != null && var3.isJsonPrimitive() && var3.getAsJsonPrimitive().isNumber()) {
         try {
            return var3.getAsInt();
         } catch (NumberFormatException var5) {
            return var2;
         }
      } else {
         return var2;
      }
   }

   private static float a(JsonObject var0, String var1, float var2) {
      JsonElement var3 = var0.get(var1);
      if (var3 != null && var3.isJsonPrimitive() && var3.getAsJsonPrimitive().isNumber()) {
         try {
            return var3.getAsFloat();
         } catch (NumberFormatException var5) {
            return var2;
         }
      } else {
         return var2;
      }
   }

   private static boolean a(JsonObject var0, String var1, boolean var2) {
      JsonElement var3 = var0.get(var1);
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s39icrkrmozluu","2OEx9993gHveyPpthW1fLljfvtCwA7QVEI/hrYF/0w0=",4348544849675725702,-892910304493620593,6680676819484218041,4213814895998016449>()) {
            case 52538681:
               if (var3.isJsonPrimitive()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1tp5xbdmxami2","NSZm5W0JbQbw6kA2vQwXKCHGicwOnS5k+KK0sF73vPo=",4724481245458377565,-3301259762231451940,-1108195920412643786,-7952647015251321119>()) {
                     case 542802904:
                        if (var3.getAsJsonPrimitive().isBoolean()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3veji5p5bjz4j","DEfJOJvPjMZhi9BAH6RP8lulS27vVxKIVrVLttCtET0=",-8321745538812779037,-7155485985168269849,4336439165013632468,1396788774766482194>()) {
                              case -2079473062:
                                 boolean var10000 = var3.getAsBoolean();
                                 switch ((int)com.yiyiaddon.m.b.a<"s27klnp6okc6sc","5owhT9RL6wUwKAcRlCr55gjcvP6yAAkQ1j5hUXhbfjY=",-5789868288516177660,7005868105025223036,-8971429352633652883,-2486465553474705702>()) {
                                    case 1374902403:
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
               break;
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s1u0bilbok6qqr","tHyuxSxjxyLVI30Oid8Daaq/U8rKwasfIBKM2HJh2Fg=",-3684646288968712561,-2080951477386653857,1047408140082311400,6536624213154873740>()) {
         case 688276436:
            return var2;
         default:
            throw null;
      }
   }
}
