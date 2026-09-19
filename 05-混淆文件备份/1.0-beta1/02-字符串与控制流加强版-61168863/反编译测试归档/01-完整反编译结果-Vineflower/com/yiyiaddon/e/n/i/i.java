package com.yiyiaddon.e.n.i;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HexFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.ResourceManager;

public final class i {
   public static final String sJ = "crop-resource-v1";
   private static final Minecraft Z = Minecraft.getInstance();
   private static final int mu = 20000;
   private static final int mv = 8192;

   private i() {
   }

   public static Map<String, i.a> a(Collection<com.yiyiaddon.e.n.i.a> var0) {
      return a(var0, var0x -> false);
   }

   public static Map<String, i.a> a(Collection<com.yiyiaddon.e.n.i.a> var0, Predicate<String> var1) {
      LinkedHashMap var2 = new LinkedHashMap();
      if (var0 != null) {
         for (com.yiyiaddon.e.n.i.a var4 : var0) {
            if (var4 != null && var4.dk() != null && !var4.dk().isBlank()) {
               var2.put(var4.dk(), var4);
            }
         }
      }

      if (var2.isEmpty()) {
         return Map.of();
      }

      ResourceManager var17 = Z.getResourceManager();
      if (var17 == null) {
         return Map.of();
      }

      List var18;
      try {
         var18 = var17.listPacks().toList();
      } catch (Exception var16) {
         return Map.of();
      }

      LinkedHashMap var5 = new LinkedHashMap();
      LinkedHashMap var6 = new LinkedHashMap();

      for (String var8 : var2.keySet()) {
         var5.put(var8, new LinkedHashMap());
         var6.put(var8, 0);
      }

      int[] var19 = new int[]{0};

      for (int var20 = var18.size() - 1; var20 >= 0 && var19[0] < 20000; var20--) {
         PackResources var9 = (PackResources)var18.get(var20);

         try {
            if (var9.getNamespaces(PackType.CLIENT_RESOURCES)
               .contains(
                  (String)com.yiyiaddon.m.b.a<"s1kg4f6zsl4phg","GgTziAvbwaqI/c0LogFg9qt9JgCS9vQKlv4vkgIyIykdpNpkI5Ym9MkXJ8CsU9Z5IVE=",9089270348405528779,-4783529871267874934,-3586614631456947375,2200201586200306449>()
               )) {
               a(
                  var9,
                  (String)com.yiyiaddon.m.b.a<"s3jmegkwzu25m7","gF8xZ/dWQf0etH6nQg2F3XnJaLzjve3X43ak58+YC93JfLZfL9Y=",5679695204167544218,-5466471440254658798,909504828671289724,-3867846506365341193>(),
                  var2,
                  var5,
                  var6,
                  var19
               );
               a(
                  var9,
                  (String)com.yiyiaddon.m.b.a<"s3k1bk1c5xq34j","5Jh54gT+P5DkIqqlKfms9q2yefr0lipLB4RLBy5itjqX8tgZ7zGsAg8SQoGDxiLANhU=",8879609017651198523,-881855306689395157,6183353096262077540,3665909810245043279>(),
                  var2,
                  var5,
                  var6,
                  var19
               );
               a(
                  var9,
                  (String)com.yiyiaddon.m.b.a<"s359h9ybizfli1","jiEgpHa/Zxa4Oxm0X/GNGleZzOyJZXWzL0g7z429H9HBJmM8hS2ZYrHUrdyeeK7PjzX4tX6Hf/hPiHJHu8Y=",-2460385807671434748,-6507158698328003112,-768836884236826051,-7367807444906094698>(),
                  var2,
                  var5,
                  var6,
                  var19
               );
               a(var9, var2, var5, var19);
            }
         } catch (Exception var15) {
         }
      }

      LinkedHashMap var21 = new LinkedHashMap();

      for (String var10 : var2.keySet()) {
         Map var11 = (Map)var5.get(var10);
         int var12 = var6.getOrDefault(var10, 0);
         String var13 = a(var11);
         i.a var14 = new i.a(var13, var11.size(), var12);
         if (var14.dg()) {
            var21.put(var10, var14);
         } else if (var1.test(var10)) {
            var21.put(var10, new i.a(aI(var10), 1, 1));
         }
      }

      return var21;
   }

   private static String aI(String var0) {
      String var1 = com.yiyiaddon.k.e.c.dn();
      if (var1 != null) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s37z8ov5rwwe23","LaG+i6irN5rnxfx1N8SmzOUtyjYQdzQw07CWfF2aZ20=",3630966206549554920,1194405693191021735,-6640796981594840762,-495273627252504322>()) {
            case -1717779722:
               if (!var1.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3gvscma49k04u","qoPc6g2tiDZqkxphzDmm+dg3c9X9wfyySSQTFA2ZO0s=",4677789628098969369,7173340516475042859,1854566348549808925,2792416480959727634>()) {
                     case 1044799733:
                        return var1 + var0.toLowerCase(Locale.ROOT);
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"sw9wi43sog91q","lIzqmi4dtfixUgcrrRJyuWJKOFEPFU0dVSGA1RM/KOI=",7154218559172616536,4151273426842638723,8121263666385505477,-2025350011216773406>()) {
                  case -830272453:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      String var10000 = (String)com.yiyiaddon.m.b.a<"s2xuq5bkipfmko","TDeJAukEB4jbspFHtxAb69MRRIccHe3y5N1d/a06IxQV1ekZKhPoiCtg",9039293979805123826,-3993747144687720699,-8982317874058365745,7206334127330734375>();
      switch ((int)com.yiyiaddon.m.b.a<"s2oqzi9pzanox0","SsYA1/9v1HnWVxm2yeNCDUb9JO+KQClkYNKM+ws2rfY=",-7280333580811362599,549689329955358446,-3774553117243599329,5059487689574592462>()) {
         case 577611203:
            return var10000 + var0.toLowerCase(Locale.ROOT);
         default:
            throw null;
      }
   }

   private static void a(
      PackResources var0, String var1, Map<String, com.yiyiaddon.e.n.i.a> var2, Map<String, Map<String, String>> var3, Map<String, Integer> var4, int[] var5
   ) {
      if (var5[0] < 20000) {
         try {
            var0.listResources(
               PackType.CLIENT_RESOURCES,
               (String)com.yiyiaddon.m.b.a<"s1kg4f6zsl4phg","GgTziAvbwaqI/c0LogFg9qt9JgCS9vQKlv4vkgIyIykdpNpkI5Ym9MkXJ8CsU9Z5IVE=",9089270348405528779,-4783529871267874934,-3586614631456947375,2200201586200306449>(),
               var1,
               (var5x, var6) -> {
                  if (var5[0]++ >= 20000) {
                     switch ((int)com.yiyiaddon.m.b.a<"s24gyfsjk4aqz7","b9qr2NmEGbxwH9RuudNAcgWMYMxvC8SYbakmmE/Uyk8=",4787526409803086015,-1770334489227308711,5571664178664301812,2707051682324578758>()) {
                        case 1213288833:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     String var7x = var5x.getPath();
                     if (!var7x.endsWith(
                        (String)com.yiyiaddon.m.b.a<"s1jpuowwds7tjo","Kqp44sqKiZdY7p+TVFn4VVEjY1Xoefc4J00ZXpFMp4Wu6JzbEwA=",-6527758545685961499,1647972947883855162,-4508369569724022505,-2944953542291016722>()
                     )) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1sw0x4ce8qzjy","7D2HSdlltTWxEakObROs5Z8K9vlhLqN2sN2ZeRWcsZ8=",-6050979923940768267,-3382460006589546920,7379575137062125026,6447539881037244206>()) {
                           case 2044801507:
                              return;
                           default:
                              throw null;
                        }
                     } else {
                        Iterator var8 = var2.entrySet().iterator();
                        switch ((int)com.yiyiaddon.m.b.a<"s137w622bhkzf2","2/gMa9FOnTRKZSwJtbO8vKOseawBTEO9VWY40BYFPs4=",-1771227851504777561,6728054582734828183,1688466718573360331,1235210641952638850>()) {
                           case 368736483:
                              while (var8.hasNext()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2qsbi2iyyl76y","6AW4Akp8uIt43lHgjompL3DneNWOsoFR2xgwglremm0=",-4100536848931432330,6410314574411292070,3942067053054771881,496327666400693673>()) {
                                    case 718203296:
                                       Entry var9 = (Entry)var8.next();
                                       String var10 = (String)var9.getKey();
                                       if (!c(var7x, var1, var10)) {
                                          switch ((int)com.yiyiaddon.m.b.a<"sdrof4ywy8ypy","y2KcQdwCrieBDZ5hQcriebXXDtlilUOA7uEGxTfjuEk=",-3708591032521997700,-8090529195200495763,-7255314504480104085,2025831341306934351>()) {
                                             case -1935610897:
                                                switch ((int)com.yiyiaddon.m.b.a<"s1u6gua81d9ipj","3HnCl2Incl0R07hn0bg1NPaTs3Pus+qISWEojfOf2og=",1813115873503293806,2064147092847842122,-8047583275074715762,6191465933533420103>()) {
                                                   case 2043924363:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          Map var11 = (Map)var3.get(var10);
                                          String var12 = var5x.toString();
                                          if (var11.containsKey(var12)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2o94dpsg1o028","h3I9TYsyLZRxy83Oxons1Qx/nu92lmZ+b4/YV76OGG0=",7967729931219505060,-5448959228009774471,7697329970374061886,3718374769488017615>()) {
                                                case 371179621:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s9ehfgqndnls4","yuiYSJ1c0k0bu7Cz0V/8UmXpQNAx9e0RwMvdA7TLC/I=",2384571405534820562,-289865071346829493,-2441193492765397988,-4061781712129682859>()) {
                                                      case 2001888269:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             String var13 = a(var6);
                                             if (var13 == null) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s3nwwtckb1bzp6","egZyM+g8pb8B8C8HZz3+2aAw7ZZ2/xErKR/sWk96SHI=",8361955468160335981,6128997780449777273,-7529308491994826409,6517208039490286491>()) {
                                                   case 1469302295:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1se84qydbiimu","WcxCFLQG3tzzWgq7W5sLP7PVo8c5CZka7L1HwZApW8o=",-358925264547889895,2698925545552340782,-9063282076814060074,-5003287321358124216>()) {
                                                         case -2098542296:
                                                            continue;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             } else {
                                                var11.put(var12, var13);
                                                if (i(var7x, var10)) {
                                                   label48:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s17taplpt77kiw","oTKZ84YIPxDI5GTiXCVMjX9yiJuAjupvrAl4VelqGEI=",-8164488594516663110,-2215233652491698558,-7995647845787213368,-7056074673496830076>()) {
                                                      case -264081919:
                                                         var4.put(var10, var4.getOrDefault(var10, 0) + 1);
                                                         switch ((int)com.yiyiaddon.m.b.a<"smenjhtvyifzb","a+vgv4gP7P2dKffmXeDtHNNRZsRvmWRsQDZ3hT/F7io=",3386973251044241286,-4404170703545372358,2729687407540720906,-5077938704292571065>()) {
                                                            case -474803635:
                                                               break label48;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                switch ((int)com.yiyiaddon.m.b.a<"s2oz1wccoagc2j","LToOJvpEK+iDEvyyl++mwQnZ67IPCHmp7KAFhRvuyx8=",-3151808476626001558,-9045770640953046482,849972261958405095,-3141101195707076320>()) {
                                                   case -877622186:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             }
                                          }
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
                  }
               }
            );
         } catch (Exception var7) {
         }
      }
   }

   private static void a(PackResources var0, Map<String, com.yiyiaddon.e.n.i.a> var1, Map<String, Map<String, String>> var2, int[] var3) {
      if (var3[0] < 20000) {
         try {
            var0.listResources(
               PackType.CLIENT_RESOURCES,
               (String)com.yiyiaddon.m.b.a<"s1kg4f6zsl4phg","GgTziAvbwaqI/c0LogFg9qt9JgCS9vQKlv4vkgIyIykdpNpkI5Ym9MkXJ8CsU9Z5IVE=",9089270348405528779,-4783529871267874934,-3586614631456947375,2200201586200306449>(),
               (String)com.yiyiaddon.m.b.a<"s1wse1f3dgrncd","h3fegMY/R8C8UKiQABr7Icbw3DAXd0m7Ux1yqPBvFl84cwuY",7434632062642041109,4860503430940456827,-7158219744868823567,-3575646087353588517>(),
               (var3x, var4) -> {
                  if (var3[0]++ < 20000
                     && var3x.getPath()
                        .endsWith(
                           (String)com.yiyiaddon.m.b.a<"s1jpuowwds7tjo","Kqp44sqKiZdY7p+TVFn4VVEjY1Xoefc4J00ZXpFMp4Wu6JzbEwA=",-6527758545685961499,1647972947883855162,-4508369569724022505,-2944953542291016722>()
                        )) {
                     byte[] var5x = a(var4);
                     if (var5x != null) {
                        JsonObject var6;
                        try {
                           var6 = JsonParser.parseString(new String(var5x, StandardCharsets.UTF_8)).getAsJsonObject();
                        } catch (Exception var13) {
                           return;
                        }

                        for (Entry var8 : var1.entrySet()) {
                           Set var9 = g((String)var8.getKey());

                           for (Entry var11 : var6.entrySet()) {
                              if (a((String)var11.getKey(), var9)) {
                                 String var12 = var3x + (String)var11.getKey();
                                 ((Map)var2.get(var8.getKey())).putIfAbsent(var12, aK(((JsonElement)var11.getValue()).toString()));
                              }
                           }
                        }
                     }
                  }
               }
            );
         } catch (Exception var5) {
         }
      }
   }

   private static boolean c(String var0, String var1, String var2) {
      String var3 = var0.toLowerCase(Locale.ROOT);
      if ((String)com.yiyiaddon.m.b.a<"s359h9ybizfli1","jiEgpHa/Zxa4Oxm0X/GNGleZzOyJZXWzL0g7z429H9HBJmM8hS2ZYrHUrdyeeK7PjzX4tX6Hf/hPiHJHu8Y=",-2460385807671434748,-6507158698328003112,-768836884236826051,-7367807444906094698>()
         .equals(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1x4xm41hdr04x","/1VNLtLf1p8GuUuoKykdxIg0L8Vgdmgdyx1q9Tjae6U=",-721946492336248340,2719437202116190653,7040594735537929965,-3466317981430794200>()) {
            case 337464361:
               return var3.startsWith(var2.toLowerCase(Locale.ROOT) + "");
            default:
               throw null;
         }
      } else {
         String var4 = aJ(var3);
         return g(var2).contains(var4);
      }
   }

   private static boolean i(String var0, String var1) {
      String var2 = var1.toLowerCase(Locale.ROOT) + "";
      return var0.toLowerCase(Locale.ROOT).startsWith(var2);
   }

   private static Set<String> g(String var0) {
      String var1 = var0.toLowerCase(Locale.ROOT);
      LinkedHashSet var2 = new LinkedHashSet();
      var2.add(var1);
      var2.add(var1 + "");
      var2.add(var1 + "");
      var2.add(var1 + "");
      var2.add(var1 + "");
      var2.add(var1 + "");
      var2.add(var1 + "");
      var2.add(var1 + "");
      return var2;
   }

   private static boolean a(String var0, Set<String> var1) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1xmatq9encomw","ycCJqVTDUSPYQ0B8CL3FZVJDFQxWBOPHkQZp0Fv8+Lw=",6782420213435791277,-8931120859330452623,-6738281007656809631,6872809992898662324>()) {
            case 175006367:
               return false;
            default:
               throw null;
         }
      } else {
         String[] var2 = var0.toLowerCase(Locale.ROOT)
            .split(
               (String)com.yiyiaddon.m.b.a<"s2wjhycf45rgqx","+fe0PPoLqi/Dw3TRIHo2vCtXr8Qj5bbYw/p3J4qanwMDb+hX",6462066860721747257,-3952854834011350429,1838331516921905995,-7953857815949193847>()
            );
         String[] var3 = var2;
         int var4 = var3.length;
         int var5 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s26d0ruyja3pwr","qMW5SX6x17wdRBXHKpNOuiVpJYh//3yAiQY2KF3ezTA=",4685647113513019403,4702085988387053323,2960154528921573767,6780131711572895755>()) {
            case -1958937734:
               while (var5 < var4) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3sv5hudl2oeye","O1ZcHnTfzDmyHGXMJaTyQc+k0YAVap6phgLNXr9gLQM=",3221796359076739255,7955911191829178951,-6094156259439913699,-9104323115680480415>()) {
                     case 1075021516:
                        String var6 = var3[var5];
                        if (var1.contains(var6)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1h99muanajmtf","TplsjynsQLMB38kq9rTHPMVoikpkX6L1rS9Y6JmbjJ8=",4020816186062808038,-113554531679924674,-2661015274916955466,728704772908708563>()) {
                              case 1939312473:
                                 return true;
                              default:
                                 throw null;
                           }
                        }

                        var5++;
                        switch ((int)com.yiyiaddon.m.b.a<"s1utwgmwnawf9a","B0fmHD9ipKo5tNRga5KGKJFC66gwVoCilcr6ELHgqJM=",-8019524434822857844,2148373886573889217,-4564164925344392396,9018153096365964194>()) {
                           case -574480462:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return false;
            default:
               throw null;
         }
      }
   }

   private static String aJ(String var0) {
      int var1 = var0.lastIndexOf(47);
      String var10000;
      if (var1 >= 0) {
         label29:
         switch ((int)com.yiyiaddon.m.b.a<"s3rm39c8blmsxd","wKkhMk7ndR/0CYdR10nLekfKKg5O4eRC3NjLe2z4/w0=",-792461290313651486,-1772906620127784402,7641854692489443107,-2388505363167603451>()) {
            case 2055688875:
               var10000 = var0.substring(var1 + 1);
               switch ((int)com.yiyiaddon.m.b.a<"sskdlh86yvkoo","gmXV6ipbj4mT35zfts81jGRVlN7I4kNtxzhkuGYPP6o=",462629806942379918,2386288155862343677,-3664128507232791010,-1393831519462245311>()) {
                  case -976089615:
                     break label29;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var0;
         switch ((int)com.yiyiaddon.m.b.a<"s2s6mqz6iyuq6m","hwQTSFJYF1V3J6pp0iBhi1oafoufjJqrZFGxfvBWk1k=",-3203099009917909458,2677619832280866079,5253467632380559562,2237695785541068110>()) {
            case 1954599482:
               break;
            default:
               throw null;
         }
      }

      String var2 = var10000;
      if (var2.endsWith(
         (String)com.yiyiaddon.m.b.a<"s1jpuowwds7tjo","Kqp44sqKiZdY7p+TVFn4VVEjY1Xoefc4J00ZXpFMp4Wu6JzbEwA=",-6527758545685961499,1647972947883855162,-4508369569724022505,-2944953542291016722>()
      )) {
         switch ((int)com.yiyiaddon.m.b.a<"s1dcivo4eyvg5z","7Xu9IWrAt00vNUxAypDRHdDY91WPzEv8TMkibmLjNDE=",-8064520024134402745,3682803784372774392,-2720390363910873672,-1647814398601245904>()) {
            case -1602306363:
               var10000 = var2.substring(0, var2.length() - 5);
               switch ((int)com.yiyiaddon.m.b.a<"ss5p0hk4g1gsq","kOlpA5R3zWPp3LlBf97LcdRAdfh9ruETxd1R5aXlLZI=",1652257899006714962,-1391740048393149927,-5562665937966951855,-1853627265065302993>()) {
                  case 933662339:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s194ileico6y0z","HaXQBiNUGdTWpN/nAfVACM5j0szl982FES6YFnpkXpQ=",3864586890487743947,-7250597099311662220,3437145244962379047,-5039503008790749947>()) {
            case 1031770130:
               return var2;
            default:
               throw null;
         }
      }
   }

   private static String a(IoSupplier<InputStream> var0) {
      byte[] var1 = a(var0);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s19jgj9qoh3ghb","aQGglLl8voQu2CDrfmOo6VkJZYMBTR7JE2sASKIuUa4=",-9026455689215963896,-7703970112932954396,5810199228527506282,6266004429823195633>()) {
            case -514495223:
               switch ((int)com.yiyiaddon.m.b.a<"s3qzthy2vxbdnl","pTOJ8mU0TwOx0uPMEFnXmDH8l8YCBAy6xn9WXWqYKcI=",6900211413210559360,-4666669432394377249,4493157399369128971,8646985902532241624>()) {
                  case 1746277590:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var10000 = a(var1, 8);
         switch ((int)com.yiyiaddon.m.b.a<"s1uj3mrsnih8tq","H4YtFWvYCfLDjoN19Ui4iVaFnxFYEkIQYn1QQKU6hr8=",5265372457938593526,-7010374721883191147,-4304353573556659939,-8256557380044567472>()) {
            case -1341816768:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private static byte[] a(IoSupplier<InputStream> var0) {
      try (InputStream var1 = (InputStream)var0.get()) {
         if (var1 == null) {
            return null;
         }

         ByteArrayOutputStream var2 = new ByteArrayOutputStream();
         byte[] var3 = new byte[8192];

         int var4;
         while ((var4 = var1.read(var3)) != -1) {
            var2.write(var3, 0, var4);
         }

         return var2.toByteArray();
      } catch (Exception var8) {
         return null;
      }
   }

   private static String a(Map<String, String> var0) {
      if (var0 != null && !var0.isEmpty()) {
         ArrayList var1 = new ArrayList(var0.keySet());
         Collections.sort(var1);

         try {
            MessageDigest var2 = MessageDigest.getInstance(
               (String)com.yiyiaddon.m.b.a<"s1hbasxi68x140","LrllNHvGtjP1A02shRw5vFVUHQlitXD0rmIuXqKux3obMPNjxWNjjtZz",342153507457057711,-3527627482500199521,9213819613338043684,715346749104948411>()
            );

            for (String var4 : var1) {
               var2.update(var4.getBytes(StandardCharsets.UTF_8));
               var2.update((byte)0);
               var2.update(((String)var0.get(var4)).getBytes(StandardCharsets.UTF_8));
               var2.update((byte)10);
            }

            return HexFormat.of().formatHex(var2.digest(), 0, 6);
         } catch (Exception var5) {
            return null;
         }
      } else {
         return null;
      }
   }

   private static String aK(String var0) {
      return a(var0.getBytes(StandardCharsets.UTF_8), 8);
   }

   private static String a(byte[] var0, int var1) {
      try {
         MessageDigest var2 = MessageDigest.getInstance(
            (String)com.yiyiaddon.m.b.a<"s1hbasxi68x140","LrllNHvGtjP1A02shRw5vFVUHQlitXD0rmIuXqKux3obMPNjxWNjjtZz",342153507457057711,-3527627482500199521,9213819613338043684,715346749104948411>()
         );
         return HexFormat.of().formatHex(var2.digest(var0), 0, var1);
      } catch (Exception var3) {
         return (String)com.yiyiaddon.m.b.a<"s34m2jedjntmjd","N4J/wOfttMpix6bWonDChCF9SSS+UiT6jB7qAApd4gphrHUVDVqotnE0YrNgfARc",3680703697493199111,-1812183100094843650,2787962929089128762,4309142310935850321>();
      }
   }

   public record a(String sK, int mw, int mx) {
      public boolean dg() {
         if (this.sK != null) {
            switch ((int)com.yiyiaddon.m.b.a<"sf83noihndvgm","a9gvhLge8SFoVizvwYNnfuAka7avNh+i9Te/wtNXl9U=",-8989395476460196975,-1312768244290185901,2647863394254855020,-5752328741450866306>()) {
               case -155096406:
                  if (!this.sK.isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s39dbu71m3yus4","uNh1Ci+TxFBI6+owJGiWbJJ9uOrC8YL8WK9nIsSeeSg=",-7943138854314340965,-9207411224908297386,-2428040087569463016,-2634017214530607738>()) {
                        case -123662865:
                           if (this.mw > 0) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1hrfkxhkphbm7","+Mvw6yAQAYsAwRM0p/oBLEdAnzOhpY8seEoiMTwYrf0=",-4295855302946941497,7019178795717835135,2345344343266895005,8874259980497111628>()) {
                                 case 1792738963:
                                    if (this.mx > 0) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1jcos4g8g38ub","YA50LpehRSuD7ETx1f9YsV3kzcBeDeTYEFZd+0Jt1eo=",-6144911980269794442,1916576232183594386,-6529016006548414505,-5577743240927178136>()) {
                                          case -927997777:
                                             switch ((int)com.yiyiaddon.m.b.a<"sd5gz3tzj09lu","IYiCihVwJPY59TUSI2Ap4gCgFpvjnBVxAQj+aHfyQJw=",687553600577047945,1113154290790523057,3752131760576562378,-7441203907869129719>()) {
                                                case -273349328:
                                                   return true;
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
                  break;
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"sp9tkdfflvcxo","pimw5Gz7cOSX8HaFE4eUksNqLwGG8Pa57WvcGO5zEss=",3888063628308563750,-1509543044645319714,-8121726742979928283,2723101705788472519>()) {
            case -1372937022:
               return false;
            default:
               throw null;
         }
      }

      public String bi() {
         return this.sK;
      }

      public int ca() {
         return this.mw;
      }

      public int cb() {
         return this.mx;
      }
   }
}
