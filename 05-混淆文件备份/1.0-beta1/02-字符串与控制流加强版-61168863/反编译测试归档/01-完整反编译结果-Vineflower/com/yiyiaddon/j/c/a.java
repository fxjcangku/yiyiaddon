package com.yiyiaddon.j.c;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yiyiaddon.k.e.e;
import com.yiyiaddon.m.b;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class a {
   private static final Logger t = LoggerFactory.getLogger(
      (String)b.a<"s1j6my9lus4rb9","0UAvm0pxHy23JEZm5aGzaJfk0V82P2sPzVX5Hh8+dn2b6xrKtHG4AOEa6OERmP+ZN45qwzPTsV75pRbAqdLwJF//m2NqTO/NWN0NBg==",-7403818808388526602,1375781549219746126,3896623615380365099,387768750272712574>()
   );
   private static final String Em = (String)b.a<"s2uxp48a7l0nxy","TcSKgWzatCVz/gEqSS9TTeuwTRMqWoCUxkrU1pa4l7HjFTpKp2VGJ0UMQ8fcrA==",-569200932278148497,6298996066205328781,-4513796361178128607,7127165116539378880>();
   private static final String En = (String)b.a<"soapga5rwsnaf","ibATZmrGTGg54P+2yA8SCiFoske7vPfUMYgldgJ3FgyaVBBi8k0=",4142699117299631611,-2694594793278932250,-3412695526433258682,7203654922737749691>();
   private static final int sF = 64;
   private static final String Eo = (String)b.a<"s4ahot12xmxoa","nAHrhAbtqdVjZgeu1hKYruKbl7O3CaspUQzEtw5185uEZbjf",-7052320718332009575,6607436895903552944,-8126731881230616472,4928605523212360686>();
   private static final String Ep = (String)b.a<"so0plmpiuasgh","IKrGClJI7c5Ul5RY5WwPUvh4td07SVVzeK9e1XIfokVtD+or",-2782507297732394094,2177318062472744477,-2240475441852551913,-384445598835491864>();
   private static final String Eq = (String)b.a<"s1e5vjskcjsd52","uqOS5QjCRE8jrYoAd+pcZU9HrCpcq/e3J2jBMvCOu3m1MlQ+",-480697773605877805,-422559298796883652,-385698810614815570,-4288310239237883066>();
   private static final String Er = (String)b.a<"s3t9wfald6y4ti","yTddhWq1iXj7VRPnugbpa5FDoilMZm4B24+tcKtWnH1CF+1v",-8245336635690740772,-2204155866925493513,4800535343825918483,1695895604126953289>();
   private static final String Es = (String)b.a<"snrbiybwir0f7","HkEyEKrI8qKUewvr1oALu/29YgA8Z2onXdU7VW8hwXk1D0k/",6172275283151844051,6550705894700089776,9101129034924794756,-6815917292268940227>();
   private static final String Et = (String)b.a<"s3muby6g4g8qum","lk+UWniNK/Ss2aJ0H00SLETy/eJeNuyc8GHOcFA7TMrP8N+oZS6k2w==",1919618861016852836,6073588274436377294,-6004291349526910847,4344566258580593393>();
   private static final String Eu = (String)b.a<"s7pbtjdx7rhxx","juLO75gwT1AoqZ/ELnPBMEmIjCZ1Qrz0uZ2p9RSwJO8sKS0jaZR50g==",782327767223865534,-3718667513581279992,-2137683849104925365,-6556080894167873396>();
   private static final String Ev = (String)b.a<"s29ydoazo6mw0d","OvS5qw4Fr0h2KUHtpE6amgJ08Yp3W9wwl7izOO6s5TXOwOnZ",5170780176011776706,-9051819705703227332,9147049907948894531,-5886003173360952232>();
   private static final String Ew = (String)b.a<"s2u4a43jk6n4gx","Y+pgckz9FiV3Ug1hmnRk4QWCGbzimo+35XYRguphpv8z79byTWrrUg==",4943317346990670588,-4336796065680588746,7847143590435271471,-8954628758223873719>();
   private static final String Ex = (String)b.a<"s2bpy06rnwwug3","lFjWEIilbzy1OqzbovbpP7EcDZ3A0YOthMDCinD9AX2EH/jB",-2591713471207968140,-3352918455596400627,-2341131312617065652,4730096861717482341>();

   private a() {
   }

   public static Path l() {
      return e.b()
         .toPath()
         .resolve(
            (String)b.a<"s2uxp48a7l0nxy","TcSKgWzatCVz/gEqSS9TTeuwTRMqWoCUxkrU1pa4l7HjFTpKp2VGJ0UMQ8fcrA==",-569200932278148497,6298996066205328781,-4513796361178128607,7127165116539378880>()
         );
   }

   public static com.yiyiaddon.j.c.a.a a(String var0) {
      if (var0 != null && !var0.isBlank()) {
         Path var1 = l().resolve(var0 + "");
         if (!Files.isRegularFile(var1)) {
            return null;
         }

         JsonObject var2 = com.yiyiaddon.j.a.a(var1);
         if (var2 == null) {
            return null;
         }

         if (!var0.equals(
            d(
               var2,
               (String)b.a<"s4ahot12xmxoa","nAHrhAbtqdVjZgeu1hKYruKbl7O3CaspUQzEtw5185uEZbjf",-7052320718332009575,6607436895903552944,-8126731881230616472,4928605523212360686>()
            )
         )) {
            return null;
         }

         try {
            LinkedHashMap var3 = new LinkedHashMap();
            JsonElement var4 = var2.get(
               (String)b.a<"s3t9wfald6y4ti","yTddhWq1iXj7VRPnugbpa5FDoilMZm4B24+tcKtWnH1CF+1v",-8245336635690740772,-2204155866925493513,4800535343825918483,1695895604126953289>()
            );
            if (var4 != null && var4.isJsonObject()) {
               for (Entry var6 : var4.getAsJsonObject().entrySet()) {
                  if (var6.getValue() != null && ((JsonElement)var6.getValue()).isJsonPrimitive()) {
                     var3.put((String)var6.getKey(), ((JsonElement)var6.getValue()).getAsInt());
                  }
               }
            }

            return new com.yiyiaddon.j.c.a.a(
               var3,
               d(
                  var2,
                  (String)b.a<"s1e5vjskcjsd52","uqOS5QjCRE8jrYoAd+pcZU9HrCpcq/e3J2jBMvCOu3m1MlQ+",-480697773605877805,-422559298796883652,-385698810614815570,-4288310239237883066>(),
                  0
               ),
               d(
                  var2,
                  (String)b.a<"snrbiybwir0f7","HkEyEKrI8qKUewvr1oALu/29YgA8Z2onXdU7VW8hwXk1D0k/",6172275283151844051,6550705894700089776,9101129034924794756,-6815917292268940227>(),
                  0
               ),
               b(
                  var2,
                  (String)b.a<"s3muby6g4g8qum","lk+UWniNK/Ss2aJ0H00SLETy/eJeNuyc8GHOcFA7TMrP8N+oZS6k2w==",1919618861016852836,6073588274436377294,-6004291349526910847,4344566258580593393>()
               ),
               b(
                  var2,
                  (String)b.a<"s7pbtjdx7rhxx","juLO75gwT1AoqZ/ELnPBMEmIjCZ1Qrz0uZ2p9RSwJO8sKS0jaZR50g==",782327767223865534,-3718667513581279992,-2137683849104925365,-6556080894167873396>()
               ),
               var2.has(
                     (String)b.a<"s2u4a43jk6n4gx","Y+pgckz9FiV3Ug1hmnRk4QWCGbzimo+35XYRguphpv8z79byTWrrUg==",4943317346990670588,-4336796065680588746,7847143590435271471,-8954628758223873719>()
                  )
                  && var2.get(
                        (String)b.a<"s2u4a43jk6n4gx","Y+pgckz9FiV3Ug1hmnRk4QWCGbzimo+35XYRguphpv8z79byTWrrUg==",4943317346990670588,-4336796065680588746,7847143590435271471,-8954628758223873719>()
                     )
                     .isJsonPrimitive()
                  && var2.get(
                        (String)b.a<"s2u4a43jk6n4gx","Y+pgckz9FiV3Ug1hmnRk4QWCGbzimo+35XYRguphpv8z79byTWrrUg==",4943317346990670588,-4336796065680588746,7847143590435271471,-8954628758223873719>()
                     )
                     .getAsBoolean(),
               b(
                  var2,
                  (String)b.a<"s2bpy06rnwwug3","lFjWEIilbzy1OqzbovbpP7EcDZ3A0YOthMDCinD9AX2EH/jB",-2591713471207968140,-3352918455596400627,-2341131312617065652,4730096861717482341>(),
                  0L
               )
            );
         } catch (Exception var7) {
            t.warn(
               (String)b.a<"s18cgzsncykbbs","mBXOFY4F6vUDSkGImIZHh9ftczcWeJR92ekg0vE+jhumoUX4tQRbAJhAd9FP5cgP1Wttv6gjqBZCXrUrD2MXXn1JnAE=",4622919180629948002,4843239819131327521,-5357892285960769721,5846819482022259676>(),
               var1,
               var7
            );
            return null;
         }
      } else {
         return null;
      }
   }

   public static boolean a(com.yiyiaddon.g.d.b var0) {
      if (var0 != null) {
         switch ((int)b.a<"s3e28u2eln6wf9","Iu8EeFOmUATnxPwqtO47ZOSC0r8YpjamDnwgZUpK+2s=",-1600231742320730683,1817352681208556618,-4193491579187347675,5435900045723129844>()) {
            case 1721106338:
               if (var0.dn() != null) {
                  switch ((int)b.a<"sao8p0ri60b66","XKfJjDsyNbTWgQ6puo/il6q0vJUDWJW8rGrLtY7vTQ4=",7951333167449291607,-2445619435918783990,-7058571947291199736,-5056489562416014320>()) {
                     case -773368545:
                        if (!var0.dn().isBlank()) {
                           JsonObject var1 = new JsonObject();
                           Iterator var2 = var0.x().entrySet().iterator();
                           switch ((int)b.a<"s3mfcm7ovwv0ql","DhJSZ5tzf/HRMoRnGilfEvzquVxO+8a1pcKEPNHRrfg=",-8166933076653104877,-6940988230464354289,4433346086269253250,-3576558067357047316>()) {
                              case -1876811764:
                                 while (var2.hasNext()) {
                                    switch ((int)b.a<"s2grcyl2vayhi4","9O/qev/iqX2frXdWBR/Ib/n3z2Ff1L/a2EnVBDT2bvg=",-7209475085004936180,-7393870967201037846,5901546826783084669,9004184347649420231>()) {
                                       case -1645096962:
                                          Entry var3 = (Entry)var2.next();
                                          var1.addProperty((String)var3.getKey(), (Number)var3.getValue());
                                          switch ((int)b.a<"sngk5pzmblub4","tlu3BQf99upPz5ypW6MYOl+7JmfOJFfRA2UBBNYwy4Q=",3989352099505242336,-5757819328438413025,303973347373736148,-8726166959171621144>()) {
                                             case 1092853191:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 JsonArray var6 = new JsonArray();
                                 Iterator var7 = var0.bx().iterator();
                                 switch ((int)b.a<"s1tkkb6vrgk0uv","mu4uiVGbXmwPxPZhK9hXX6rCKZr4eT04w39pfRRMdus=",-1130148375146180670,4948792458309545428,8137255342074100758,8103154694745426280>()) {
                                    case -1311210589:
                                       while (var7.hasNext()) {
                                          switch ((int)b.a<"s2z84yevwqh50c","sPLrHm2azTMyYM9HXiPFtmaOWp+o2K0EJIAGOoB9fkY=",-866060411389293037,3073268284965843600,-334079578483987682,-895668345769519973>()) {
                                             case -1711514509:
                                                String var4 = (String)var7.next();
                                                var6.add(var4);
                                                switch ((int)b.a<"s1kqfpj9ii67t1","eHPUhlLNtAXWct9UssTKd/aUE4HAxchtAjhX/ID1D28=",-593699135697120454,-8574562519882810572,7627341749199996321,2389046513214941846>()) {
                                                   case -1001222209:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       JsonArray var8 = new JsonArray();
                                       Iterator var9 = var0.by().iterator();
                                       switch ((int)b.a<"s1rj24yed1tqie","GT7QgYthCfLHZ8RQPe+jotxhHrXBvdcJqxcEVHtWPAI=",5748775964021800699,319563207533913703,6474433510834346067,5216820897141036161>()) {
                                          case -2098316102:
                                             while (var9.hasNext()) {
                                                switch ((int)b.a<"s2iyh90b8lrtac","H9+yXdaKczLbJAbmK+EHv7Fgu7sRVwDcai9cUVj//EM=",7050938774769138444,-5074249790448080050,8617437268777465775,6648545220399121528>()) {
                                                   case -991679433:
                                                      String var5 = (String)var9.next();
                                                      var8.add(var5);
                                                      switch ((int)b.a<"slwbsusq9biy1","JeS5nujNjKPAdeY8bZC278aNsNZxrEF1cHnIQAI336g=",-1348031490545215363,5033287342711188832,-4912046036555015162,6024019167552095154>()) {
                                                         case 916434073:
                                                            continue;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             JsonObject var10 = new JsonObject();
                                             var10.addProperty(
                                                (String)b.a<"s4ahot12xmxoa","nAHrhAbtqdVjZgeu1hKYruKbl7O3CaspUQzEtw5185uEZbjf",-7052320718332009575,6607436895903552944,-8126731881230616472,4928605523212360686>(),
                                                var0.dn()
                                             );
                                             var10.addProperty(
                                                (String)b.a<"so0plmpiuasgh","IKrGClJI7c5Ul5RY5WwPUvh4td07SVVzeK9e1XIfokVtD+or",-2782507297732394094,2177318062472744477,-2240475441852551913,-384445598835491864>(),
                                                var0.a().h()
                                             );
                                             var10.addProperty(
                                                (String)b.a<"s1e5vjskcjsd52","uqOS5QjCRE8jrYoAd+pcZU9HrCpcq/e3J2jBMvCOu3m1MlQ+",-480697773605877805,-422559298796883652,-385698810614815570,-4288310239237883066>(),
                                                var0.di()
                                             );
                                             var10.add(
                                                (String)b.a<"s3t9wfald6y4ti","yTddhWq1iXj7VRPnugbpa5FDoilMZm4B24+tcKtWnH1CF+1v",-8245336635690740772,-2204155866925493513,4800535343825918483,1695895604126953289>(),
                                                var1
                                             );
                                             var10.addProperty(
                                                (String)b.a<"snrbiybwir0f7","HkEyEKrI8qKUewvr1oALu/29YgA8Z2onXdU7VW8hwXk1D0k/",6172275283151844051,6550705894700089776,9101129034924794756,-6815917292268940227>(),
                                                var0.dx()
                                             );
                                             var10.add(
                                                (String)b.a<"s3muby6g4g8qum","lk+UWniNK/Ss2aJ0H00SLETy/eJeNuyc8GHOcFA7TMrP8N+oZS6k2w==",1919618861016852836,6073588274436377294,-6004291349526910847,4344566258580593393>(),
                                                var6
                                             );
                                             var10.add(
                                                (String)b.a<"s7pbtjdx7rhxx","juLO75gwT1AoqZ/ELnPBMEmIjCZ1Qrz0uZ2p9RSwJO8sKS0jaZR50g==",782327767223865534,-3718667513581279992,-2137683849104925365,-6556080894167873396>(),
                                                var8
                                             );
                                             var10.addProperty(
                                                (String)b.a<"s29ydoazo6mw0d","OvS5qw4Fr0h2KUHtpE6amgJ08Yp3W9wwl7izOO6s5TXOwOnZ",5170780176011776706,-9051819705703227332,9147049907948894531,-5886003173360952232>(),
                                                var0.a().h()
                                             );
                                             var10.addProperty(
                                                (String)b.a<"s2u4a43jk6n4gx","Y+pgckz9FiV3Ug1hmnRk4QWCGbzimo+35XYRguphpv8z79byTWrrUg==",4943317346990670588,-4336796065680588746,7847143590435271471,-8954628758223873719>(),
                                                var0.fk()
                                             );
                                             var10.addProperty(
                                                (String)b.a<"s2bpy06rnwwug3","lFjWEIilbzy1OqzbovbpP7EcDZ3A0YOthMDCinD9AX2EH/jB",-2591713471207968140,-3352918455596400627,-2341131312617065652,4730096861717482341>(),
                                                var0.x()
                                             );
                                             Path var11 = l().resolve(var0.dn() + "");
                                             if (!com.yiyiaddon.j.a.a(var11, var10)) {
                                                switch ((int)b.a<"s331c7rjv5d8yu","SjO2oGCELnca2qedXoAvItsT0pYN1o1xYjsNRDvgeX0=",4622309150339166374,-1914201046474393352,-5370915478265306332,3900532786155195123>()) {
                                                   case 747985143:
                                                      t.warn(
                                                         (String)b.a<"s3hx85r921ffvm","KB3+gU11CDQy8RjP15vAUkogdOWw6O73eVUTc+MjgjBWrFd0DBIvVzgHHbvRreKNKLi+x7Hl",-4075179839921080857,-7226784524214894641,-4337882146857530197,7470640781365382455>(),
                                                         var11
                                                      );
                                                      return false;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             dE();
                                             return true;
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

                        switch ((int)b.a<"s33hw8du418sp4","xQDqetH/ZFuG20t3ccVgoX+0g7fB6XpDT2lcKpoxsiE=",5465017843403102619,6670601731041053858,5395180820061480305,4815633216599177239>()) {
                           case -1925099022:
                              return false;
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

      return false;
   }

   public static int b() {
      try (Stream var0 = Files.list(l())) {
         return (int)var0.filter(var0x -> Files.isRegularFile(var0x))
            .filter(
               var0x -> var0x.getFileName()
                  .toString()
                  .endsWith(
                     (String)b.a<"soapga5rwsnaf","ibATZmrGTGg54P+2yA8SCiFoske7vPfUMYgldgJ3FgyaVBBi8k0=",4142699117299631611,-2694594793278932250,-3412695526433258682,7203654922737749691>()
                  )
            )
            .count();
      } catch (Exception var5) {
         return 0;
      }
   }

   public static int dE() {
      try (Stream var0 = Files.list(l())) {
         List var1 = var0.filter(var0x -> Files.isRegularFile(var0x))
            .filter(
               var0x -> var0x.getFileName()
                  .toString()
                  .endsWith(
                     (String)b.a<"soapga5rwsnaf","ibATZmrGTGg54P+2yA8SCiFoske7vPfUMYgldgJ3FgyaVBBi8k0=",4142699117299631611,-2694594793278932250,-3412695526433258682,7203654922737749691>()
                  )
            )
            .sorted(Comparator.<Path>comparingLong(com.yiyiaddon.j.c.a::b).reversed())
            .toList();
         int var2 = 0;

         for (int var3 = 64; var3 < var1.size(); var3++) {
            try {
               Files.deleteIfExists((Path)var1.get(var3));
               var2++;
            } catch (Exception var6) {
            }
         }

         return var2;
      } catch (Exception var8) {
         return 0;
      }
   }

   public static String f() {
      return b() + "";
   }

   private static long b(Path var0) {
      try {
         return Files.getLastModifiedTime(var0).toMillis();
      } catch (Exception var2) {
         return 0L;
      }
   }

   private static String d(JsonObject var0, String var1) {
      JsonElement var2 = var0.get(var1);
      if (var2 != null) {
         switch ((int)b.a<"s1q346h0ragir0","f5c2/X1KYbK3Q08AZiRBWEFoF17yMRh9c2cC4PuJcwE=",-3191119039947847543,-7716874407973201207,-8833827353015547743,3399836852096986999>()) {
            case 1746078231:
               if (var2.isJsonPrimitive()) {
                  switch ((int)b.a<"s2rrbkwdm352gp","wVUekFw3E2Gi6szTUzuvD0d4L7hB+22s8TbEnTAfFaA=",5939661919032709057,-195065419663865662,4533320638966887345,8427182214712452370>()) {
                     case -1941571061:
                        String var10000 = var2.getAsString();
                        switch ((int)b.a<"s17xeleuvxvfy5","wBvqJ6qWW9ACDK/BZqiovfzU/GYblu0FuZ/dCVrCaXs=",-7413474321849231792,4376961185526044941,-8794709478655199918,5222430792446089670>()) {
                           case 152962389:
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

      switch ((int)b.a<"s2m6jdno0f6e5j","aP/hwFgvaBlicpzN8Dx/bzS4Auk2H32GYCpHegqnZSM=",6789307241544311923,-8565795894292907019,2617169589645217484,-762100278240557362>()) {
         case 1243811294:
            return null;
         default:
            throw null;
      }
   }

   private static int d(JsonObject var0, String var1, int var2) {
      JsonElement var3 = var0.get(var1);

      try {
         return var3 != null && var3.isJsonPrimitive() ? var3.getAsInt() : var2;
      } catch (Exception var5) {
         return var2;
      }
   }

   private static long b(JsonObject var0, String var1, long var2) {
      JsonElement var4 = var0.get(var1);

      try {
         return var4 != null && var4.isJsonPrimitive() ? var4.getAsLong() : var2;
      } catch (Exception var6) {
         return var2;
      }
   }

   private static List<String> b(JsonObject var0, String var1) {
      ArrayList var2 = new ArrayList();
      JsonElement var3 = var0.get(var1);
      if (var3 != null) {
         switch ((int)b.a<"s1i2sbuvziyphc","qmSm5bmqJ2cfugj1T92QkFusfL4OnLudt415wxcXmsA=",2315449663636794107,7087403493781105831,-782122121779409031,-4906738974363958679>()) {
            case 1039631744:
               if (var3.isJsonArray()) {
                  Iterator var4 = var3.getAsJsonArray().iterator();
                  switch ((int)b.a<"sqdnecdx8z1m0","OXOn1s/LoGr0htpNfoRA6xvU+RZhzjNCJV5YfRoA6b0=",-3396742734211750286,-5662985370101967687,8534264599759482059,3375286328868669030>()) {
                     case 139526539:
                        while (var4.hasNext()) {
                           switch ((int)b.a<"s28rs46z6fqm92","/rzRJc43w5TGrZklNjJkzWc/mYa0KbuYVpqKhoNFkgg=",-7990546977661883336,5687578660726134383,5535063986785061159,3773964730188674312>()) {
                              case -1202179149:
                                 JsonElement var5 = (JsonElement)var4.next();
                                 if (var5 != null) {
                                    switch ((int)b.a<"s1hvswlb2l2mxy","VUI5tD7UcWrMO4g5Qyax7IXu5D5Ie1O+VbJv8pLo//8=",-8014297436296796736,40107417858306913,-5619178416121258128,-1149690837512700567>()) {
                                       case 1522300217:
                                          if (var5.isJsonPrimitive()) {
                                             label35:
                                             switch ((int)b.a<"s33kw82lggipxd","IaW5VtqIvjROG8FYXKDjTLrDVQQcp3H7l8/OT2dDeMs=",-3649900620483047206,-7616533602154318187,-5486123592757912435,8701972139341192502>()) {
                                                case 2010233685:
                                                   var2.add(var5.getAsString());
                                                   switch ((int)b.a<"s1pigoped83v2y","CiVOE9uIxZh/Dn5BlhpoJgRjC+OjElIVF1hflKYF5CM=",-1911897838384716958,384516467843377619,-8068106211326706247,7874469099942158403>()) {
                                                      case 383467569:
                                                         break label35;
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

                                 switch ((int)b.a<"smzlnydq19zva","OfSr49DGCtmWLaztUBY5UTi5NcNXVTuZpfN9ieC3mnE=",-3423777370785833740,2034455493580275604,-7698347152077512017,8725103518420797705>()) {
                                    case 301270142:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return var2;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)b.a<"s2ijhgexy3kaqk","kiKDCqP+LIaSgcOLu+3tZ3DXx7dvkqvDoQusyY4xseI=",-7047172971438213123,5750884586396444901,3464176605212021135,-8224569216852136174>()) {
                     case -1998202816:
                        return var2;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return var2;
      }
   }

   public record a(Map<String, Integer> aX, int sG, int sH, List<String> cP, List<String> cQ, boolean fs, long aM) {
      public Map<String, Integer> x() {
         return this.aX;
      }

      public int di() {
         return this.sG;
      }

      public int dF() {
         return this.sH;
      }

      public List<String> T() {
         return this.cP;
      }

      public List<String> bE() {
         return this.cQ;
      }

      public boolean fk() {
         return this.fs;
      }

      public long m() {
         return this.aM;
      }
   }
}
