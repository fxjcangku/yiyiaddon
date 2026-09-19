package com.yiyiaddon.e.j.i;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class a {
   private static final Logger k = LoggerFactory.getLogger(
      (String)com.yiyiaddon.m.b.a<"s1larv3dld8cpe","/viy/O4DEKbo45U4QaLJ6NengFH+aBKgoFYQS0Ldr9IxCuztqdP/rnr3MhAPbfcg0XnSyukHcAgy+xtXc9JJr1+btuLRsGxhEuQ=",-9070025793214258353,6799181293501091644,294744674409230416,-5663881288409300440>()
   );
   private static final Path b = FabricLoader.getInstance()
      .getConfigDir()
      .resolve(
         (String)com.yiyiaddon.m.b.a<"s1uokr1pqfzv4k","r3tfF3kVFu6Sy8WnIgBp8Kn96eo/YFmdFISlqLpaxRDCecgNNpwDSZ8xz/Gn8A==",6188618488136194417,-3579373784174497391,7339494531954440985,-5395413928262845049>()
      )
      .resolve(
         (String)com.yiyiaddon.m.b.a<"s3mh9ktak0qpdz","Fjaus6z5Xnl2mjqX7S4gDi5ABbtwG2twruXPt7G6eysm4Hz5X/g33w==",6312643872685422818,-1761189954430756585,-9120725540197649323,-825572236662736104>()
      )
      .resolve(
         (String)com.yiyiaddon.m.b.a<"sr5fn2axqse15","4EZ9NezbgQPMDPRZMS7cMDTjacYrRCF0ZeUarRI5EyV076US3cPWU4Bg",-8172324903775593512,-5923938294992461563,-902224143069610586,8851519492622016651>()
      );
   private static final String mY = (String)com.yiyiaddon.m.b.a<"s38xwx8q7vupqr","n/vnl/FDAjPk8hg6Ef2hSnmarF2SQpdXDub5izJZ1vmZ2loj",-7004242548485161911,3378133507854037071,-7102942511755139580,3246038343159333251>();
   private static final String mZ = (String)com.yiyiaddon.m.b.a<"s3jlez6goiujj1","4xeEEnBxvy02eKBCaOzuTErtUJQSKAGeuxsn8XWkVdWRgHSo",-1446200449588707090,1604253362270160300,-8618834877135648327,-1020471868638956100>();
   private static final String na = (String)com.yiyiaddon.m.b.a<"s2corfcgmnq4dz","cALUiZTfmQSMP1QhpnUE+bc31Dg30wPUsAeEPp96Yv8=",5714809131408625368,-6944443831227588689,5446606103851529855,-3323678494587904274>();
   private static final String nb = (String)com.yiyiaddon.m.b.a<"sx80vtk0feh1l","jmwalRLHfFa65MjUZwdUj9LbHoEwHhs4bc58E+Xf0yA=",-2332271247534396088,-7342697420477360448,-993412350259700155,6870222628475384924>();

   private a() {
   }

   private static Path a(String var0) {
      return b.resolve(var0 + "");
   }

   public static boolean E(String var0) {
      if (a(var0) != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3j21wf8wb4iy2","yk0F1vqkOhyT9oSHS/o0+BUnz4oxueFelHWyfMXHMB4=",2592464821628584670,-3538509531675338538,-5599085257334931691,8634980767265127260>()) {
            case 362809002:
               switch ((int)com.yiyiaddon.m.b.a<"s3grdfmo61jml9","U/UZuHgYnUfRyHxr/CdddbKoOLJq63H7bJBKEJr5LoE=",-1049769031713401012,6955250305375938958,-5782417924173903433,-4152467814743038665>()) {
                  case -1164563972:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s12qxyvlqnj0tq","wUC/lS95CVDCaSIjCGgMtKUaajOdPOzjvsw1s+gm4Gc=",8805690845320010599,1138918703641082879,-5208761994526737533,7671254765303080784>()) {
            case 408791759:
               return false;
            default:
               throw null;
         }
      }
   }

   public static com.yiyiaddon.e.j.e.a a(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1gblyjeo7xaix","aWTdGegQmQhbmIHrZ2yTewFzEU3a9thhE00UNo7tlyM=",1271431806632661235,4670586078253138809,-8826274724048491153,1064426531352013147>()) {
            case -658160811:
               if (!var0.isBlank()) {
                  return a(a(var0), var0);
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3r37pxl0naxk0","i1bDZlj8fdsoT2RD8E2rekuC36J6CsL8iphsmOypX2w=",6683012584853675165,5989200131224593406,8806680481152769261,-9050297485300768905>()) {
                     case -346355716:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   public static boolean a(String var0, String var1, JsonObject var2, JsonObject var3, long var4) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1vqmy35buupy4","ZTJGMQuZ4aUHfgTrVMNTUVtEqhDPFIMFcLSchojb2Pk=",-1888826448290067977,4343102353692979653,-5513037591832045163,7594733379407619775>()) {
            case 1536102043:
               if (!var0.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s21tdiu6ll3dgh","/zxWdCZ/cdUC5wy1PoPsyS1rBtbczVIPYydN+QqFUcI=",-2339059225179033127,4333411856857354427,-396638198527085661,6454590845919996843>()) {
                     case 1659595346:
                        if (var2 != null) {
                           JsonObject var6 = new JsonObject();
                           String var10001 = (String)com.yiyiaddon.m.b.a<"s38xwx8q7vupqr","n/vnl/FDAjPk8hg6Ef2hSnmarF2SQpdXDub5izJZ1vmZ2loj",-7004242548485161911,3378133507854037071,-7102942511755139580,3246038343159333251>();
                           String var10002;
                           if (var1 == null) {
                              label39:
                              switch ((int)com.yiyiaddon.m.b.a<"ss26mq1nvq3kw","e/kRuZRsaYDqpQP1eExGSBniTK/6vp1jrcXRzNMD8zk=",3089890306119417446,-8981939902115696816,118612379973108123,2109863919793630459>()) {
                                 case 1101083575:
                                    var10002 = var0;
                                    switch ((int)com.yiyiaddon.m.b.a<"sg27im30t79d5","Hfgw3osf8OfcMfAxYzm0E9NPvnS6X2z8ApqgM+s1zmM=",249171390860789002,8463263085184358587,-8274683866987097985,-3913795374366466046>()) {
                                       case -752094251:
                                          break label39;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var10002 = var1;
                              switch ((int)com.yiyiaddon.m.b.a<"s38nqdv3spx7w4","sOxRtzsBrvXdbv1uXqiAakqTEyHGWuxdZVw3F72TGaY=",6894791476123316745,1794409461549283986,-8508091297987922574,-8401971140872838785>()) {
                                 case 1073089843:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           var6.addProperty(var10001, var10002);
                           var6.addProperty(
                              (String)com.yiyiaddon.m.b.a<"s3jlez6goiujj1","4xeEEnBxvy02eKBCaOzuTErtUJQSKAGeuxsn8XWkVdWRgHSo",-1446200449588707090,1604253362270160300,-8618834877135648327,-1020471868638956100>(),
                              var4
                           );
                           var6.add(
                              (String)com.yiyiaddon.m.b.a<"s2corfcgmnq4dz","cALUiZTfmQSMP1QhpnUE+bc31Dg30wPUsAeEPp96Yv8=",5714809131408625368,-6944443831227588689,5446606103851529855,-3323678494587904274>(),
                              var2.deepCopy()
                           );
                           var10001 = (String)com.yiyiaddon.m.b.a<"sx80vtk0feh1l","jmwalRLHfFa65MjUZwdUj9LbHoEwHhs4bc58E+Xf0yA=",-2332271247534396088,-7342697420477360448,-993412350259700155,6870222628475384924>();
                           JsonObject var8;
                           if (var3 == null) {
                              label32:
                              switch ((int)com.yiyiaddon.m.b.a<"s13dnggixuxxjs","txhaC/f5tG4Gt8RkDhzQjmEu0mniyMiF0vmQXYpOh9I=",-6431622082244235283,8947935039530692195,3884973679506548781,8204726884339690652>()) {
                                 case 893524867:
                                    var8 = new JsonObject();
                                    switch ((int)com.yiyiaddon.m.b.a<"s2ritwm8vfei16","9SJyypd8U351xw9aRzJ+TfayMoPfdLuFNfJMfr0SOrw=",-4751719497655570328,-1349711806893792268,6597736840639780120,-8739253265878400248>()) {
                                       case -956907245:
                                          break label32;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var8 = var3.deepCopy();
                              switch ((int)com.yiyiaddon.m.b.a<"s2rpa9455ycvh0","8FHEuSy7y461K+h+7E08gPFgYHgKWumfEAULKxaViX4=",-8455654615107434498,-1311928343973071281,-2638128519670801375,-175457409703740019>()) {
                                 case -1866106945:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           var6.add(var10001, var8);
                           return com.yiyiaddon.j.a.a(a(var0), var6);
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"se3n7l2crrb67","M76s1K1VdIK9PUvZEtjtQQM/lIEupnsFWCSfKX7pqoY=",-2034125899038892657,4932483414321060056,79515223630128986,-823554861063866963>()) {
                           case -1795150969:
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

   public static List<com.yiyiaddon.e.j.e.a> an() {
      if (!Files.isDirectory(b)) {
         return List.of();
      }

      ArrayList var0 = new ArrayList();

      try (Stream var1 = Files.list(b)) {
         for (Path var3 : var1.toList()) {
            String var4 = var3.getFileName().toString();
            if (var4.endsWith(
               (String)com.yiyiaddon.m.b.a<"sxfsi753f63is","gmhTCGJcmbKkebcMsaPVNJOHAMb0yQFf4m7B7YgLBztgOLg7Zas=",6310943105215112015,3836765656148495074,8529625639525853732,-8876997631725347764>()
            )) {
               com.yiyiaddon.e.j.e.a var5 = a(
                  var3,
                  var4.substring(
                     0,
                     var4.length()
                        - (String)com.yiyiaddon.m.b.a<"sxfsi753f63is","gmhTCGJcmbKkebcMsaPVNJOHAMb0yQFf4m7B7YgLBztgOLg7Zas=",6310943105215112015,3836765656148495074,8529625639525853732,-8876997631725347764>()
                           .length()
                  )
               );
               if (var5 != null) {
                  var0.add(var5);
               }
            }
         }
      } catch (IOException var8) {
         k.warn(
            (String)com.yiyiaddon.m.b.a<"s147m6n2zhgk1y","hOa77DVxB+dhDf5g6Dl+/mRBR1QPrEWnFRCeuVA1ftSeKdOoWnpvyfjycRQy8C81C81x67Tn6aw98x6ttXg=",-7038109168463777559,-7685261430195306999,8195550962876540507,-5029057025589881091>(),
            b,
            var8
         );
         return List.of();
      }

      var0.sort(Comparator.<com.yiyiaddon.e.j.e.a>comparingLong(com.yiyiaddon.e.j.e.a::m).reversed());
      return List.copyOf(var0);
   }

   public static boolean F(String var0) {
      if (var0 != null && !var0.isBlank()) {
         try {
            return Files.deleteIfExists(a(var0));
         } catch (IOException var2) {
            k.warn(
               (String)com.yiyiaddon.m.b.a<"s3gzt8ruhar787","h6pJUtm8bpwDI67I056dETnpbaD6TRBTUkoqTj5ftWhDe8nfDn+9zP3XKtrEtxCfRgHSvnBIQVAQJw==",7905249883682241575,9009550760986634161,2437636826791855282,6508067304382912433>(),
               var0,
               var2
            );
            return false;
         }
      } else {
         return false;
      }
   }

   private static com.yiyiaddon.e.j.e.a a(Path var0, String var1) {
      JsonObject var2 = com.yiyiaddon.j.a.a(var0);
      if (var2 != null
         && var2.has(
            (String)com.yiyiaddon.m.b.a<"s2corfcgmnq4dz","cALUiZTfmQSMP1QhpnUE+bc31Dg30wPUsAeEPp96Yv8=",5714809131408625368,-6944443831227588689,5446606103851529855,-3323678494587904274>()
         )
         && var2.get(
               (String)com.yiyiaddon.m.b.a<"s2corfcgmnq4dz","cALUiZTfmQSMP1QhpnUE+bc31Dg30wPUsAeEPp96Yv8=",5714809131408625368,-6944443831227588689,5446606103851529855,-3323678494587904274>()
            )
            .isJsonObject()) {
         JsonObject var3 = a(
            var2.get(
               (String)com.yiyiaddon.m.b.a<"sx80vtk0feh1l","jmwalRLHfFa65MjUZwdUj9LbHoEwHhs4bc58E+Xf0yA=",-2332271247534396088,-7342697420477360448,-993412350259700155,6870222628475384924>()
            )
         );
         return new com.yiyiaddon.e.j.e.a(
            var1,
            b(
               var2,
               (String)com.yiyiaddon.m.b.a<"s38xwx8q7vupqr","n/vnl/FDAjPk8hg6Ef2hSnmarF2SQpdXDub5izJZ1vmZ2loj",-7004242548485161911,3378133507854037071,-7102942511755139580,3246038343159333251>(),
               var1
            ),
            a(
               var2,
               (String)com.yiyiaddon.m.b.a<"s3jlez6goiujj1","4xeEEnBxvy02eKBCaOzuTErtUJQSKAGeuxsn8XWkVdWRgHSo",-1446200449588707090,1604253362270160300,-8618834877135648327,-1020471868638956100>()
            ),
            var3 == null ? 0 : var3.size()
         );
      } else {
         return null;
      }
   }

   private static JsonObject a(JsonElement var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s135pepvcrn8c0","Rr3LADQbhPGgHBxHemR1bmKtA5sFtwxnWHhUBKBfm+g=",-8455066217128171035,5726863530781354241,-2206639619710171062,-3572541553417923015>()) {
            case 1020481197:
               if (var0.isJsonObject()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3gq87a9wgzgxt","m61ym2khbmxmXTBv4Z+ZxUC9dQUeIhQoMRZHtKnfcic=",2339754228134589469,1972155321639006846,1678623018508818841,6874198742280977967>()) {
                     case -1087094855:
                        JsonObject var10000 = var0.getAsJsonObject();
                        switch ((int)com.yiyiaddon.m.b.a<"s36tgzfamlathx","lgxccne7U1Sq0OdldPRGVjkSOs4+Bu25EDsW/jaxlio=",7139611677015887177,6989579145221666293,-1688470741098214241,-2333572088957498270>()) {
                           case 1047560749:
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

      switch ((int)com.yiyiaddon.m.b.a<"s336rhl91nou6g","a+sSxcTgtC3UhkwM/BFLQUVA8FxKOz4poETJc9rWGAw=",-1627176701022500016,9025570505274680359,-4319349659891746061,6291226845118643874>()) {
         case -729283557:
            return null;
         default:
            throw null;
      }
   }

   private static String b(JsonObject var0, String var1, String var2) {
      JsonElement var3 = var0.get(var1);
      if (var3 != null && var3.isJsonPrimitive()) {
         try {
            String var4 = var3.getAsString();
            return var4.isBlank() ? var2 : var4;
         } catch (Exception var5) {
            return var2;
         }
      } else {
         return var2;
      }
   }

   private static long a(JsonObject var0, String var1) {
      JsonElement var2 = var0.get(var1);
      if (var2 != null && var2.isJsonPrimitive()) {
         try {
            return var2.getAsLong();
         } catch (Exception var4) {
            return 0L;
         }
      } else {
         return 0L;
      }
   }

   public static JsonObject e(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3fyfl9327ddhb","l2YcQ9eKvj5bGyIVJE2Gl+KefT9YPJ4rHAmNjz4Peqk=",3437457310632675526,4051670203369986594,9103502753403556772,-2871058856827787539>()) {
            case -1979409610:
               if (!var0.isBlank()) {
                  JsonObject var1 = com.yiyiaddon.j.a.a(a(var0));
                  if (var1 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2nperyyd262mf","FQw/LEJp4CF222HAc2dlpZHxNMLs0BJxXEZG8FXyfLI=",1088300849915250747,-4242398044045089652,-3176830563287038951,-1229574828986737969>()) {
                        case -384944580:
                           switch ((int)com.yiyiaddon.m.b.a<"s259616ptqb258","Va/NNIeUO7agSRxZ37ykTwnjO+3uiWPjYC1BXcbNNJo=",7833278733021081347,-3354004668243134305,-6446584962618600147,6658423822330467366>()) {
                              case 1838349347:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     JsonObject var10000 = a(
                        var1.get(
                           (String)com.yiyiaddon.m.b.a<"sx80vtk0feh1l","jmwalRLHfFa65MjUZwdUj9LbHoEwHhs4bc58E+Xf0yA=",-2332271247534396088,-7342697420477360448,-993412350259700155,6870222628475384924>()
                        )
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"s3rt7knvgv1khn","IaKJIVMheJD35gWhWPJqi9toZ9CYMv6mpVP/gO/K//Q=",13655588468323232,-8279818544206197535,1273777351110925031,-4649172525883465668>()) {
                        case -695241606:
                           return var10000;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"simxzwxfajqx0","8ipkYG7FLd1DeSMKfVIk4mux9UgJMMfV7HhtCOK2NOE=",-1612012789073384688,-2660695284909270795,-8864652614145706328,-2374557697422083827>()) {
                     case -1110131536:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   public static JsonObject f(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2nrvm7nh8ayk8","pfMGAR/ZA+hPhyIj1qTfE0TMTflVF6OVXZBCtuIK8+k=",8679339826081609147,-4312486172036195342,6109585949680690925,1885635404252384505>()) {
            case -766656408:
               if (!var0.isBlank()) {
                  JsonObject var1 = com.yiyiaddon.j.a.a(a(var0));
                  if (var1 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s30lmj920krea2","WfoRq8WBS0gNOPNNXnySobPpUM7+BbSpwIf0MDfK2uU=",5700117783409919294,1666149738232082282,4401563918315142086,5197843721513733523>()) {
                        case -2052090064:
                           return null;
                        default:
                           throw null;
                     }
                  } else {
                     JsonElement var2 = var1.get(
                        (String)com.yiyiaddon.m.b.a<"s2corfcgmnq4dz","cALUiZTfmQSMP1QhpnUE+bc31Dg30wPUsAeEPp96Yv8=",5714809131408625368,-6944443831227588689,5446606103851529855,-3323678494587904274>()
                     );
                     if (var2 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3okjs63kulne8","D6CSn/yTnys8YEixT4AgoXR9PQB1ys7Yb4x76jYHk0Q=",-8640947742216408323,224181520142519488,4306842325844868100,8490743993100087962>()) {
                           case 794276149:
                              if (var2.isJsonObject()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2nyeofp2m837g","hFInqmLZ1t3HrL+i/qq0Mh/Lyi2vmp8zqmGpX+aylSU=",3837770889496483753,-2396125858072311049,4925563510797485016,9075478389926022892>()) {
                                    case -23349563:
                                       JsonObject var10000 = var2.getAsJsonObject();
                                       switch ((int)com.yiyiaddon.m.b.a<"s16k8ra2r65few","/uKMmED5Cn6ZJQ1Uv6/SjUM4ySvs5rGAamSkYt4v6vw=",8837173097754900472,-3689520712178640447,8565997285240822512,5701235626223301237>()) {
                                          case -1990516672:
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

                     switch ((int)com.yiyiaddon.m.b.a<"s3txuz7tzpwhxs","RYvKLDpJ148xR5CaimXgKCoBYXTUVWQf0zn3T9jHAhk=",-5565310802621377723,-7596931910788793646,-2320519812469512022,161268206127889087>()) {
                        case -1691378623:
                           return null;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s39fo657xb10yz","6C/Dv7aoo3tiLZ30pGI/wspzD7vciZEaVN7Hr+sgkqs=",-7224311265645579217,-8901585976370766821,1106027402600725640,-3560359168006965566>()) {
                     case 583597736:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   public static long a(String var0) {
      com.yiyiaddon.e.j.e.a var1 = a(var0);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2jktqwglzd491","aKAiB1qyhTf4E4Glcin/26cuJYvPcJStTUqVuvrEZ0U=",-133061976990893719,2307044003952299245,5019391279985206945,4699272306983710999>()) {
            case 344040275:
               switch ((int)com.yiyiaddon.m.b.a<"s2k68hy8868zu9","Jf0RDBIvEX5YGK6uyJZtmWeWwK4t1N5uIq+daDKHPD4=",2767274982764730679,-3904730336866028165,-2846193205452311385,3937339132566785088>()) {
                  case -162861396:
                     return 0L;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         long var10000 = var1.m();
         switch ((int)com.yiyiaddon.m.b.a<"s3orsr17e9wbya","R67vgOZIVoTTX9kL83hoV7WANoMtyvf84FF9Hw7JjHc=",-5439287614939803696,6996505028378638562,-1102548527082385341,1924477241011860161>()) {
            case -290950647:
               return var10000;
            default:
               throw null;
         }
      }
   }
}
