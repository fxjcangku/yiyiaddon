package com.yiyiaddon.e.n.i;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class n {
   private static final String sO = (String)com.yiyiaddon.m.b.a<"s4689nh2ueje9","u2gg6tBXGvDSGCc/eafQ4k08/0eYlz8ZrhQwz3o+IDLWb/Z42ZkIbAuQuAsQtj5FWjy82ulThVF095JHod2EhdRHNIgeE5C7O0Nmpyc6SdoBtzUfg/5LKB98g8s/dYKcgFGV2vbI+VlDAxk2jK/6t8aWncCMINHvrHtMSeREQEWi27CGMWeEsA==",7106978427532644841,3976182652723097901,-840085733184898633,-376914209236552753>();
   private static final Map<String, n.a> Z = p();

   private n() {
   }

   public static n.a a(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sjskl32fl25cz","JIGV/+dM0xcH3jYcvu9tODtI+boxpG6qxdCbzKoTJ5w=",2876391913418022557,-98132473073914335,-7915478701926025139,6806832021924344319>()) {
            case -900225137:
               switch ((int)com.yiyiaddon.m.b.a<"s3apup5v4dwal0","SYTMrOzBETE6GIYKpLj5/vDO2sPbL53Mdp/gxOH3fcM=",1574712317348142890,7305661790689508453,-4925732010472485680,5925702026525094459>()) {
                  case -1372536471:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         n.a var10000 = Z.get(var0);
         switch ((int)com.yiyiaddon.m.b.a<"s3ntz91tt3woh4","B3+E4dBizyzCL53Relp0amdCFf3H1t9NMrPRdaJJMMs=",142474061631861960,6405922679520842195,-2909314959106996514,8133612326647295413>()) {
            case -1091785787:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public static int a() {
      return Z.size();
   }

   public static Map<String, n.a> o() {
      return new LinkedHashMap<>(Z);
   }

   private static Map<String, n.a> p() {
      LinkedHashMap var0 = new LinkedHashMap();

      try (InputStream var1 = n.class
            .getResourceAsStream(
               (String)com.yiyiaddon.m.b.a<"s4689nh2ueje9","u2gg6tBXGvDSGCc/eafQ4k08/0eYlz8ZrhQwz3o+IDLWb/Z42ZkIbAuQuAsQtj5FWjy82ulThVF095JHod2EhdRHNIgeE5C7O0Nmpyc6SdoBtzUfg/5LKB98g8s/dYKcgFGV2vbI+VlDAxk2jK/6t8aWncCMINHvrHtMSeREQEWi27CGMWeEsA==",7106978427532644841,3976182652723097901,-840085733184898633,-376914209236552753>()
            )) {
         if (var1 == null) {
            return var0;
         }

         JsonObject var2 = JsonParser.parseReader(new InputStreamReader(var1, StandardCharsets.UTF_8)).getAsJsonObject();
         if (!(String)com.yiyiaddon.m.b.a<"s279on82d7g51y","5WLJQ1FTAaWln7SBrGUq2qiZ1LLId9yj+xFXPinBV0a1fc29pICmf9tirh194vrFRRwRDAducTh8qWJJ",7426132871255993803,-3374617510065884556,1915535288632981723,-802350099583627302>()
            .equals(
               c(
                  var2,
                  (String)com.yiyiaddon.m.b.a<"s14vwu1z7gadmn","wLg1+Fs31qL0i+gX5MD7R7sEPy/zxO67JtnOg5nvlwaKE0Jh",4122585805898235448,6879439112319342481,5219665290492759749,-7037785941212816330>()
               )
            )) {
            return var0;
         }

         if (var2.has(
               (String)com.yiyiaddon.m.b.a<"s13x3r6oaed9fi","ggncyyfyDNwK6XXFKCx+NKTYlAOBcsaPrTpqm+d44VZjL8c2",1755417564979069104,6081625192628363144,8958642838944701291,-9049689450870717533>()
            )
            && var2.get(
                  (String)com.yiyiaddon.m.b.a<"s13x3r6oaed9fi","ggncyyfyDNwK6XXFKCx+NKTYlAOBcsaPrTpqm+d44VZjL8c2",1755417564979069104,6081625192628363144,8958642838944701291,-9049689450870717533>()
               )
               .isJsonObject()) {
            for (Entry var4 : var2.getAsJsonObject(
                  (String)com.yiyiaddon.m.b.a<"s13x3r6oaed9fi","ggncyyfyDNwK6XXFKCx+NKTYlAOBcsaPrTpqm+d44VZjL8c2",1755417564979069104,6081625192628363144,8958642838944701291,-9049689450870717533>()
               )
               .entrySet()) {
               if (((JsonElement)var4.getValue()).isJsonObject()) {
                  JsonObject var5 = ((JsonElement)var4.getValue()).getAsJsonObject();

                  try {
                     String var6 = c(
                        var5,
                        (String)com.yiyiaddon.m.b.a<"s3pywu2owsmr7c","LEJtTb2CdLwMFvStDqfrIm8qnKan2coZn/q25uboGxS448JP",8525315897562376568,-2149626830434485055,5026882789569358080,-9057225013867223780>()
                     );
                     String var7 = c(
                        var5,
                        (String)com.yiyiaddon.m.b.a<"s2czogg3zsk3hh","grqlO9UrYHsjnFhcWJRGrCyb53ELIcJgbWWBZ/V/RrLeDUmk",-1979643493663278123,-8557439558951280018,-1015418252740799103,-3132929168518284940>()
                     );
                     String var8 = c(
                        var5,
                        (String)com.yiyiaddon.m.b.a<"snu8f140f8qn4","he2fTHp2Yu7wubNWqpHcp1CnNNKrBSTDKb2P15JyH6nlV8EfO+Tc3g==",5030683411500717776,7181049726619091938,1022401621660263656,-2863980131967296705>()
                     );
                     if (var6 != null && var7 != null && var8 != null) {
                        String var9 = c(
                           var5,
                           (String)com.yiyiaddon.m.b.a<"s374ywd1t7lht4","ry6l+SKcyvKguRL5uI1s1xLfcBdtxr5NhAkdjbCVZc51WxsNyZm5aw==",510772177674952717,-4349613262935055607,-300168662061505020,-7511469228299149759>()
                        );
                        g var10 = var9 == null ? g.UNKNOWN : g.valueOf(var9);
                        var0.put(
                           (String)var4.getKey(),
                           new n.a(
                              (String)var4.getKey(),
                              var6,
                              k.valueOf(var7),
                              var10,
                              var5.has(
                                    (String)com.yiyiaddon.m.b.a<"s1jebf5yh4ssom","UhlouoywXpXg22ZmC1yjS05b/PiMmcwDx86PUTwibNoxnzlx",-9081650226870558346,8036609957381637694,-2833497848705947589,6207737431723247277>()
                                 )
                                 && var5.get(
                                       (String)com.yiyiaddon.m.b.a<"s1jebf5yh4ssom","UhlouoywXpXg22ZmC1yjS05b/PiMmcwDx86PUTwibNoxnzlx",-9081650226870558346,8036609957381637694,-2833497848705947589,6207737431723247277>()
                                    )
                                    .isJsonPrimitive()
                                 && var5.get(
                                       (String)com.yiyiaddon.m.b.a<"s1jebf5yh4ssom","UhlouoywXpXg22ZmC1yjS05b/PiMmcwDx86PUTwibNoxnzlx",-9081650226870558346,8036609957381637694,-2833497848705947589,6207737431723247277>()
                                    )
                                    .getAsBoolean(),
                              var8
                           )
                        );
                     }
                  } catch (Exception var12) {
                  }
               }
            }

            return var0;
         } else {
            return var0;
         }
      } catch (Exception var14) {
         return var0;
      }
   }

   private static String c(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s10urnlscj3q88","2igSwboleyT8LD/WpLZ6V/VMGweK+riy6WMQ3y5/OTQ=",5187242977505639594,-228926936454991312,-2397963564028428477,-8477538081322121658>()) {
            case 886359077:
               if (!var0.get(var1).isJsonNull()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sjkio6o1j99th","ai+u+BKmRobuSyLCMNDJB8p9qebiMh2iGi0wHb98oDw=",-5690026437488098164,4571704864521788304,846733675637610636,-5729316413365760322>()) {
                     case -1186108389:
                        if (var0.get(var1).isJsonPrimitive()) {
                           String var2 = var0.get(var1).getAsString();
                           if (var2 != null) {
                              label32:
                              switch ((int)com.yiyiaddon.m.b.a<"s3kaxmhqhnbj29","OJLV9z19PwvJMwnprbI4f50hp+++FhF+dbpQ5/yAgj8=",-2302121278464351200,-7833283880902410699,-7209447205397546883,-2866049503335563838>()) {
                                 case 649055793:
                                    if (!var2.isBlank()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s31qx9isx5jf51","HNxVQsP6VoVMwasBpm43Zt9UbM4xSdmHLekQoUnLxVI=",-5520975843720665565,5339296331703506216,-2521374921602466620,-4419067668336463393>()) {
                                          case 1207255139:
                                             return var2;
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s2tl58ly04i9eh","ecqtxw0AlkY4ZAoMgdsUdZVU96qcaX3bF5gIW320MaQ=",373747134066908406,-4690822175513680807,-8005293788243231046,4002372551148501879>()) {
                                       case 1047917133:
                                          break label32;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s2ykuc9422lf0d","JZsnFB9z+e2OS35YhokOrbpouS2/x3PGtWZBw5dXxYA=",564721554475204513,-1537422628682276961,-6181142195356386749,-7007846187624006351>()) {
                              case 396699179:
                                 return null;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"svv73qicnh93k","r0F+X3IKjwQGQV9I0vSPa5ToCWOQETsM6tat3OI3HdY=",-3169670610972034370,-1402818206468421214,6069494229863938260,-8191914015466393309>()) {
                           case 75287864:
                              return null;
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

      return null;
   }

   public record a(String sP, String sQ, k b, g b, boolean dI, String sR) {
      public String dk() {
         return this.sP;
      }

      public String dK() {
         return this.sQ;
      }

      public k a() {
         return this.b;
      }

      public boolean dj() {
         return this.dI;
      }

      public String dM() {
         return this.sR;
      }
   }
}
