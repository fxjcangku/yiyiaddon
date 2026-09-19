package com.yiyiaddon.e.n.n;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import net.minecraft.client.Minecraft;

public final class e {
   private static final String tS = (String)com.yiyiaddon.m.b.a<"s3t1ror62j0t9d","KnyQ4tt31DdKYoRBK7HGVZMKEw1s3zyG7p7dLc6TZfxb2QBb6oBB09FEjE/FK8V3hls=",5675947647440078737,-3366246628629687014,-9182987531631800824,2080714306860032889>();
   private static final String tT = (String)com.yiyiaddon.m.b.a<"s18xjho019wo5p","oYEPRgdH4e3ehBilTaZXYNEVIt7TheRHfK79otxA0Ha270WJnFe0V3o7DsHMJSVF91lHNkAFELpSxcuuoO6KuRzlDD0=",3087773063898691306,2650608959728255294,2299610763946389240,4396621881014720541>();

   private e() {
   }

   public static synchronized boolean a(String var0, String var1, String var2, int var3, f.d var4) {
      if (var0 != null && var1 != null && var2 != null && var4 != null && var4 != f.d.UNKNOWN) {
         JsonObject var5 = d();
         JsonArray var6 = a(var5);
         JsonObject var7 = a(var6, var0, var1, var2, var3);
         if (var7 == null) {
            var7 = new JsonObject();
            var6.add(var7);
         }

         var7.addProperty(
            (String)com.yiyiaddon.m.b.a<"s12pbfdy1495l","1aYKUf63NxbdhdJTNPXKbgssWxEtNzumaKtjo8PflNNz9Q==",-838975405346332871,-8368513967999675750,6597167013130388224,8396079823244807299>(),
            var0
         );
         var7.addProperty(
            (String)com.yiyiaddon.m.b.a<"s66h8cvfq4itz","DOnGxRrzmJhAHVNk/8qKYn9LmrYqu/vcBHTCjI4VpJg=",-7810229433458661288,2166554399413848341,-3483468913010063334,-4704804480849481801>(),
            var1
         );
         var7.addProperty(
            (String)com.yiyiaddon.m.b.a<"sb6uy99amjlcd","a78/2/lx9re0XTGtHR4CN6G3iI8DOumuAlN3SAGerU8=",6571446375052484375,-6692988524978027684,-3811600318237726377,-515673616536002159>(),
            var2
         );
         var7.addProperty(
            (String)com.yiyiaddon.m.b.a<"s2oim32k3w3o6f","DSrXKLaG8loy50CSZCX41TBxC1HNZnQZeF9hET9eUTI=",-2407310913713945172,1427620850369667483,2683399254536767479,3035801624921680329>(),
            g(var3)
         );
         var7.addProperty(
            (String)com.yiyiaddon.m.b.a<"s2sefv8s376g2g","G6F+Dl/lSnovHs9Z1huMFHQE5wOS422O+cTd2Fqonnk=",-3714837477035789319,4131338061311122127,-6715673267239237508,984312702525439921>(),
            var4.name()
         );
         return a(var5);
      } else {
         return false;
      }
   }

   public static synchronized boolean ak(String var0) {
      if (var0 == null) {
         return false;
      }

      JsonObject var1 = d();
      JsonArray var2 = a(var1);
      JsonArray var3 = new JsonArray();

      for (JsonElement var5 : var2) {
         if (var5.isJsonObject()) {
            JsonObject var6 = var5.getAsJsonObject();
            if (!var0.equals(
               c(
                  var6,
                  (String)com.yiyiaddon.m.b.a<"s12pbfdy1495l","1aYKUf63NxbdhdJTNPXKbgssWxEtNzumaKtjo8PflNNz9Q==",-838975405346332871,-8368513967999675750,6597167013130388224,8396079823244807299>()
               )
            )) {
               var3.add(var6);
            }
         }
      }

      var1.add(
         (String)com.yiyiaddon.m.b.a<"s2atgevpgwmu5p","+wnBshhor0w+1q+9Wqp5qQhqnkKde7INKChH2u2evx4=",6495593994914771554,-1611322054462570672,3487939882359485936,5723456748550907018>(),
         var3
      );
      return a(var1);
   }

   public static synchronized Map<String, Map<Integer, f.d>> b(String var0, String var1) {
      HashMap var2 = new HashMap();
      if (var0 != null && var1 != null) {
         for (JsonElement var5 : a(d())) {
            if (var5.isJsonObject()) {
               JsonObject var6 = var5.getAsJsonObject();
               if (var0.equals(
                     c(
                        var6,
                        (String)com.yiyiaddon.m.b.a<"s12pbfdy1495l","1aYKUf63NxbdhdJTNPXKbgssWxEtNzumaKtjo8PflNNz9Q==",-838975405346332871,-8368513967999675750,6597167013130388224,8396079823244807299>()
                     )
                  )
                  && var1.equals(
                     c(
                        var6,
                        (String)com.yiyiaddon.m.b.a<"s66h8cvfq4itz","DOnGxRrzmJhAHVNk/8qKYn9LmrYqu/vcBHTCjI4VpJg=",-7810229433458661288,2166554399413848341,-3483468913010063334,-4704804480849481801>()
                     )
                  )) {
                  String var7 = c(
                     var6,
                     (String)com.yiyiaddon.m.b.a<"sb6uy99amjlcd","a78/2/lx9re0XTGtHR4CN6G3iI8DOumuAlN3SAGerU8=",6571446375052484375,-6692988524978027684,-3811600318237726377,-515673616536002159>()
                  );
                  String var8 = c(
                     var6,
                     (String)com.yiyiaddon.m.b.a<"s2oim32k3w3o6f","DSrXKLaG8loy50CSZCX41TBxC1HNZnQZeF9hET9eUTI=",-2407310913713945172,1427620850369667483,2683399254536767479,3035801624921680329>()
                  );
                  if (var7 != null && var8 != null) {
                     f.d var9 = d(
                        c(
                           var6,
                           (String)com.yiyiaddon.m.b.a<"s2sefv8s376g2g","G6F+Dl/lSnovHs9Z1huMFHQE5wOS422O+cTd2Fqonnk=",-3714837477035789319,4131338061311122127,-6715673267239237508,984312702525439921>()
                        )
                     );
                     if (var9 != f.d.UNKNOWN) {
                        int var10;
                        try {
                           var10 = Integer.parseInt(var8, 16);
                        } catch (NumberFormatException var12) {
                           continue;
                        }

                        var2.computeIfAbsent(var7, var0x -> new LinkedHashMap<>()).put(var10, var9);
                     }
                  }
               }
            }
         }

         return var2;
      } else {
         return var2;
      }
   }

   private static Path c() {
      return Minecraft.getInstance()
         .gameDirectory
         .toPath()
         .resolve(
            (String)com.yiyiaddon.m.b.a<"s3t1ror62j0t9d","KnyQ4tt31DdKYoRBK7HGVZMKEw1s3zyG7p7dLc6TZfxb2QBb6oBB09FEjE/FK8V3hls=",5675947647440078737,-3366246628629687014,-9182987531631800824,2080714306860032889>()
         )
         .resolve(
            (String)com.yiyiaddon.m.b.a<"s18xjho019wo5p","oYEPRgdH4e3ehBilTaZXYNEVIt7TheRHfK79otxA0Ha270WJnFe0V3o7DsHMJSVF91lHNkAFELpSxcuuoO6KuRzlDD0=",3087773063898691306,2650608959728255294,2299610763946389240,4396621881014720541>()
         );
   }

   private static JsonObject d() {
      Path var0 = c();
      if (!Files.isRegularFile(var0)) {
         return new JsonObject();
      }

      try {
         JsonObject var1 = JsonParser.parseString(Files.readString(var0, StandardCharsets.UTF_8)).getAsJsonObject();
         return var1 == null ? new JsonObject() : var1;
      } catch (Exception var2) {
         return new JsonObject();
      }
   }

   private static boolean a(JsonObject var0) {
      return com.yiyiaddon.j.a.a(c(), var0);
   }

   private static JsonArray a(JsonObject var0) {
      if (var0.has(
         (String)com.yiyiaddon.m.b.a<"s2atgevpgwmu5p","+wnBshhor0w+1q+9Wqp5qQhqnkKde7INKChH2u2evx4=",6495593994914771554,-1611322054462570672,3487939882359485936,5723456748550907018>()
      )) {
         switch ((int)com.yiyiaddon.m.b.a<"s3pq2mgm7a23z","d0GP1DWcr9JYHK5VnvVgLwZkIXLiLvqb/pEPDFEMp0A=",-2689517222533845197,-6112841590290628549,4566686370847219414,8727454014452057990>()) {
            case -589055297:
               if (var0.get(
                     (String)com.yiyiaddon.m.b.a<"s2atgevpgwmu5p","+wnBshhor0w+1q+9Wqp5qQhqnkKde7INKChH2u2evx4=",6495593994914771554,-1611322054462570672,3487939882359485936,5723456748550907018>()
                  )
                  .isJsonArray()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2prjhql7g435f","Nnrx5t1aS+WNIO/nmR/IrJBAYotci5Y2m0cztxiE+Lo=",-8470486053443552745,6053337802940516509,5221606500272164941,-4133444737785392892>()) {
                     case 778245270:
                        return var0.getAsJsonArray(
                           (String)com.yiyiaddon.m.b.a<"s2atgevpgwmu5p","+wnBshhor0w+1q+9Wqp5qQhqnkKde7INKChH2u2evx4=",6495593994914771554,-1611322054462570672,3487939882359485936,5723456748550907018>()
                        );
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      JsonArray var1 = new JsonArray();
      var0.add(
         (String)com.yiyiaddon.m.b.a<"s2atgevpgwmu5p","+wnBshhor0w+1q+9Wqp5qQhqnkKde7INKChH2u2evx4=",6495593994914771554,-1611322054462570672,3487939882359485936,5723456748550907018>(),
         var1
      );
      return var1;
   }

   private static JsonObject a(JsonArray var0, String var1, String var2, String var3, int var4) {
      String var5 = g(var4);
      Iterator var6 = var0.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1v7leubvdypoe","ST6uj4to8lY53ekW2b4PjWOolH0w2HkOHso7UlWISrA=",-1408508244456779503,7201745077841729034,-8324728056388419959,8816152486600083190>()) {
         case 518606569:
            while (var6.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1uwsoazamn581","DqgJProDUbJ2LLfa4juvxWy8p8Sx78O5HZl4dJ0wTLY=",-1502753430857169629,-2471936713058886653,7918858523780377389,2225156774080846235>()) {
                  case 1370506561:
                     JsonElement var7 = (JsonElement)var6.next();
                     if (!var7.isJsonObject()) {
                        switch ((int)com.yiyiaddon.m.b.a<"sh4ptylyq81s6","Qn8GOkBWpjTpUisSoyue7X4rxBm+W+ifWLWMHJjs9jc=",-867490442130832391,2010668997543127291,3187497117407664138,1017260151999564088>()) {
                           case -1648924816:
                              switch ((int)com.yiyiaddon.m.b.a<"sa5xdimllgk9b","lEpsFQ88cHVX2u5HHBHQ6pBHFcVnmzpwoIIwbxRncMg=",6791382361002160326,-1193959324048238449,-247926080169627318,-9196885882092653596>()) {
                                 case 434881825:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        JsonObject var8 = var7.getAsJsonObject();
                        if (var1.equals(
                           c(
                              var8,
                              (String)com.yiyiaddon.m.b.a<"s12pbfdy1495l","1aYKUf63NxbdhdJTNPXKbgssWxEtNzumaKtjo8PflNNz9Q==",-838975405346332871,-8368513967999675750,6597167013130388224,8396079823244807299>()
                           )
                        )) {
                           switch ((int)com.yiyiaddon.m.b.a<"s8jcxgkxp7p32","i0JoGBKEKnHazzaQeoUU7IiRX100DzW5lt1KVp21uYc=",-6585056705861861947,5024295298311367676,-9127662379584234126,6605086850052418790>()) {
                              case 987997586:
                                 if (var2.equals(
                                    c(
                                       var8,
                                       (String)com.yiyiaddon.m.b.a<"s66h8cvfq4itz","DOnGxRrzmJhAHVNk/8qKYn9LmrYqu/vcBHTCjI4VpJg=",-7810229433458661288,2166554399413848341,-3483468913010063334,-4704804480849481801>()
                                    )
                                 )) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sk5ryslhe163k","4FihRVQlwlbEzmdBDgG07QEW0nnMMT4BlOCgk+Xt2nM=",-3629766563423807844,-1131491867213126385,934263174872578840,3079282327707460644>()) {
                                       case -758849693:
                                          if (var3.equals(
                                             c(
                                                var8,
                                                (String)com.yiyiaddon.m.b.a<"sb6uy99amjlcd","a78/2/lx9re0XTGtHR4CN6G3iI8DOumuAlN3SAGerU8=",6571446375052484375,-6692988524978027684,-3811600318237726377,-515673616536002159>()
                                             )
                                          )) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3qog6png8qta9","pqTQ3nsedzJfeG1oWSrG4Pbajhxr+BEVz/GyAOSu7cs=",9103896116575352139,-845673686774105572,-7504705723215623014,2561676223815973428>()) {
                                                case 973422067:
                                                   if (var5.equalsIgnoreCase(
                                                      String.valueOf(
                                                         c(
                                                            var8,
                                                            (String)com.yiyiaddon.m.b.a<"s2oim32k3w3o6f","DSrXKLaG8loy50CSZCX41TBxC1HNZnQZeF9hET9eUTI=",-2407310913713945172,1427620850369667483,2683399254536767479,3035801624921680329>()
                                                         )
                                                      )
                                                   )) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s10rtgra6ncva3","tvK8awMqyGEygO1SBAYR9jvY3glixEKjmYTbVMDDk9A=",4453604047069479907,-2104949089985327860,-6957706179011495525,8014561636360723770>()) {
                                                         case 1656779208:
                                                            return var8;
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
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"sku7sjeey8fk4","LnQkYyQ/4fTLn6sCA76COz0wfv6F3U+nKlRk/c70Px8=",5032327108265516757,7739238325400954718,7130498346666974408,-389136946015674475>()) {
                           case 1567629945:
                              continue;
                           default:
                              throw null;
                        }
                     }
                  default:
                     throw null;
               }
            }

            return null;
         default:
            throw null;
      }
   }

   private static String c(JsonObject var0, String var1) {
      if (var0 != null && var0.has(var1) && var0.get(var1).isJsonPrimitive()) {
         try {
            return var0.get(var1).getAsString();
         } catch (Exception var3) {
            return null;
         }
      } else {
         return null;
      }
   }

   private static String g(int var0) {
      return Integer.toHexString(var0).toUpperCase(Locale.ROOT);
   }

   private static f.d d(String var0) {
      if (var0 == null) {
         return f.d.UNKNOWN;
      }

      try {
         return f.d.valueOf(var0.toUpperCase(Locale.ROOT));
      } catch (IllegalArgumentException var2) {
         return f.d.UNKNOWN;
      }
   }
}
