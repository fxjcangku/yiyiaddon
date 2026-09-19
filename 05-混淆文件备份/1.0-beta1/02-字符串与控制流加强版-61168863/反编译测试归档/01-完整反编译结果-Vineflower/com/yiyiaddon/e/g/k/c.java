package com.yiyiaddon.e.g.k;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.mojang.logging.LogUtils;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import org.slf4j.Logger;

public final class c {
   private static final Logger i = LogUtils.getLogger();
   private static final String kA = (String)com.yiyiaddon.m.b.a<"s3p56wyg93dys6","5i6b3rzkkoaRFO4xTefKPstqXHYRblTzV6KZjQulVLgcCcThIA/GWuHUgDSpAEopN3fw5xYyeBriSh4anKMHjtGHDx0h4A==",-6918284623613007432,211371038638379682,6780571792932314225,-1709226041267446993>();
   private static volatile com.yiyiaddon.e.g.k.c a;
   private final Map<String, com.yiyiaddon.e.g.k.c.f> C = new LinkedHashMap<>();
   private final Map<String, com.yiyiaddon.e.g.k.c.g> D = new LinkedHashMap<>();
   private final Map<String, Set<String>> E = new HashMap<>();
   private final Map<String, String> F = new HashMap<>();
   private final List<String> aT = new ArrayList<>();
   private final String minecraftVersion;
   private final int dataVersion;

   private c(String var1, int var2) {
      this.minecraftVersion = var1;
      this.dataVersion = var2;
   }

   public static com.yiyiaddon.e.g.k.c a() {
      if (a == null) {
         synchronized (com.yiyiaddon.e.g.k.c.class) {
            if (a == null) {
               com.yiyiaddon.e.g.k.c var1 = b();
               if (!var1.aT.isEmpty()) {
                  i.error(
                     (String)com.yiyiaddon.m.b.a<"s2pskolnzq7hon","YrXy530qPk0dHHDvSFPWGxARmN17oiefIk6VMeSAY6KUMvDfe2q9KpRjMyGqJZ8hqDHmqodzrvTXLl26VAb7iUnBjWqErlglChjNmoFvMiQXnZBWsxaPfg==",-7064940252755388604,-1591996355213019590,7231570418896587804,-6029756059896758909>(),
                     var1.aT.size(),
                     (String)com.yiyiaddon.m.b.a<"s1douimmyfzjyx","wGmH5FJ3owFpPUtqO4/BtQflNYmYfbdJARL0yiRbM+h8E+ICeQScrU3/J+dZUg==",-4646689022261701756,6503134443533634187,-6618991112312913806,-7318889606177376240>(),
                     String.join(
                        (String)com.yiyiaddon.m.b.a<"s1j7y0drbl2y9d","2IWK8WOELQAtOM/ZF1Aj9UlJRx6/z95dxKzW77+sH1DhZw==",1182570929557630656,-8566087862135110168,8280867804030119936,-3198886641802351958>(),
                        var1.aT
                     )
                  );
               }

               a = var1;
            }
         }
      }

      return a;
   }

   private static com.yiyiaddon.e.g.k.c b() {
      com.yiyiaddon.e.g.k.c var0 = new com.yiyiaddon.e.g.k.c(
         (String)com.yiyiaddon.m.b.a<"s2k9sghz4239u","Fs4xi8sHQO/uMUG3I9+dDLWBAr+rmNO67ity9KPMTWUvY4S9Z6sUFzTE",-3872541643890575415,-4423037428693141264,8774313390831875566,1492599341638467014>(),
         -1
      );

      try {
         com.yiyiaddon.e.g.k.c.k var1 = a(
            (String)com.yiyiaddon.m.b.a<"s2tarpjrvgaye9","hGkIZuNlMM+ZbRK1NUYWAH+vJvsnwN/YBpKDcapZ7uja2CGWBs91unzyHHEUVdRDn+u1jb9I7QHr0roHRJeBGNW5cyhWJcDo9O2adNTtPjUDGrF/fBs36IjifHaiDLbhQso0Wg==",840928536261718864,3994608983436343801,5684558506263661773,5596619283135921272>(),
            com.yiyiaddon.e.g.k.c.k.class
         );
         var0 = new com.yiyiaddon.e.g.k.c(
            var1 == null
               ? (String)com.yiyiaddon.m.b.a<"s2k9sghz4239u","Fs4xi8sHQO/uMUG3I9+dDLWBAr+rmNO67ity9KPMTWUvY4S9Z6sUFzTE",-3872541643890575415,-4423037428693141264,8774313390831875566,1492599341638467014>()
               : var1.minecraftVersion,
            var1 == null ? -1 : var1.dataVersion
         );
      } catch (Exception var2) {
         var0.aT.add(var2.getMessage() + "");
      }

      var0.cK();
      var0.cL();
      var0.cM();
      var0.cN();
      return var0;
   }

   private void cK() {
      try {
         com.yiyiaddon.e.g.k.c.e var1 = a(
            (String)com.yiyiaddon.m.b.a<"syw1t9jv9af0f","Pi7fVJlS9rqPBsrk0sIUhNBaP53oeoYmvKHBj3gkAutTbVBsIJhJOnNIg59jR8mWkMaSvdHwMpujVeURWYiGspOU/Pw9RX5n0F+vqyOvOqzL7EnkQ3BBLL8F1BTkOzStOgAIXHAqSgDVTLs4pl/I9KsaWl8oTTkwQfXfmLpLWr+LJg==",7360789420275229490,-3175919663557942753,-7406541056567403584,-6691437081144341056>(),
            com.yiyiaddon.e.g.k.c.e.class
         );
         if (var1 == null) {
            this.aT
               .add(
                  (String)com.yiyiaddon.m.b.a<"s1zoiponjt17mj","8lKSKT6rIWH8u/ah0CQbyhuWlf6ls9OLQz+5nOJbi3UYdMVYMNxEy0fvY6vpJSd3pFkPfIxCZX8cBiQrKa0TjpUldjvFMNpMC8yszQlaCZbquD/GdU8YXnw9w6igrtUXkFk=",4503240080421603383,-3244010192000054309,-8865831253804145733,4733895696770530566>()
               );
            return;
         }

         for (com.yiyiaddon.e.g.k.c.d var3 : var1.enchantments) {
            String var4 = ac(var3.id);
            if (var4 != null) {
               this.C
                  .put(
                     var4,
                     new com.yiyiaddon.e.g.k.c.f(
                        var4,
                        var3.name,
                        var3.maxLevel,
                        var3.weight,
                        var3.minCost == null ? 0 : var3.minCost.base,
                        var3.minCost == null ? 0 : var3.minCost.perLevel,
                        var3.maxCost == null ? 0 : var3.maxCost.base,
                        var3.maxCost == null ? 0 : var3.maxCost.perLevel,
                        var3.treasure,
                        var3.exclusiveGroup
                     )
                  );
            }
         }
      } catch (Exception var5) {
         this.aT.add(var5.getMessage() + "");
      }
   }

   private void cL() {
      try {
         com.yiyiaddon.e.g.k.c.b var1 = a(
            (String)com.yiyiaddon.m.b.a<"s2goxalrnwih0t","fwW9XJXXzuKPElwUfo08KnXRBNXroj7EkuhDKcW44RvIiDXocMzwlOj3DhXBjYf/jA1qmeVQKZivTDagOvUkeV6vL24bNtd2wO72AKAnEAsW7qsZ58CViO9ORlk8Q3Mv4xpowoVHfju8dZ6MXNiHXPzbGb9kUg==",1234182688416812228,-5614482994316008550,-8973271517186520888,5652701280600682015>(),
            com.yiyiaddon.e.g.k.c.b.class
         );
         if (var1 == null) {
            this.aT
               .add(
                  (String)com.yiyiaddon.m.b.a<"sy5ow9ghkxpgp","0bkVHCzWju6PByVAA7gz/TVJlALVjH6ykwfEItI8w8L4PvQ4TcWgBiCHyMqDDWfu30exZEnDsmNmdRmoPTGZNLDBp7KiKIvAIVz7dfleEU6InUs3RUQ=",4287363804265038219,8614776449225172181,-5497781823020795704,1153723924474714213>()
               );
            return;
         }

         if (var1.groups != null) {
            for (com.yiyiaddon.e.g.k.c.j var3 : var1.groups) {
               for (String var5 : var3.members) {
                  this.F.put(ac(var5), var3.id);
               }
            }
         }

         if (var1.pairs != null) {
            for (com.yiyiaddon.e.g.k.c.l var8 : var1.pairs) {
               this.g(ac(var8.a), ac(var8.b));
            }
         }
      } catch (Exception var6) {
         this.aT.add(var6.getMessage() + "");
      }
   }

   private void g(String var1, String var2) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s13r2g9moqnpdy","+CmQOhlogpFX/IvZXQP9mwIgbQJVCSNfksaY2kOwe10=",4580075441011952806,-505766967110871392,4187302848831502328,6440182164603485139>()) {
            case 1873640357:
               if (var2 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s17eq45b779gd8","k9X+bvPRiH1kV9Vs5poaY7GrPj4fyrSnNggrNju9Msc=",1385955910418938523,-1004341444162333629,-434922220245914102,4467673797887698281>()) {
                     case 761784807:
                        if (!var1.equals(var2)) {
                           this.E.computeIfAbsent(var1, var0 -> new LinkedHashSet<>()).add(var2);
                           this.E.computeIfAbsent(var2, var0 -> new LinkedHashSet<>()).add(var1);
                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s32g7ux569vm3a","RqtjSDza6A4TmZDhIEmwBzqvKpb6Es1l9hLaM8oLmDw=",3181227345908012975,-5788164078419165089,-5997252321371130817,-389081298241598205>()) {
                           case 47764150:
                              return;
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
   }

   private void cM() {
      try {
         com.yiyiaddon.e.g.k.c.i var1 = a(
            (String)com.yiyiaddon.m.b.a<"s1jif61fzf9gz9","mr0VtcpSTvuMn0mGI/99fW66/4Qkz4V0qAumrylurVIvSuz18skzYh2ee279eQX1C35eUobAJwTzVwJ4YSCwXS1fIpDLGWmMOk1iL8U9B9NaRGSAGSC/Zm7DXDhs1ezsTEn0OnIH",-1696970161843481643,7894920988081514862,-4359086070303833217,3030844384658662777>(),
            com.yiyiaddon.e.g.k.c.i.class
         );
         if (var1 == null) {
            this.aT
               .add(
                  (String)com.yiyiaddon.m.b.a<"s2t1hkav0kuxbs","MPYywgjGGrD/ko9T8QNff3KN+qcW2IPo33uwaFpsMoYVBrguNT5RmrdlcaiyOG35jMjjGmVP1CAktCiKIJQNd/ZJnIDbWA==",-6551211026751938302,7014282490490121555,-538591561804848113,1189162409285484547>()
               );
            return;
         }

         for (com.yiyiaddon.e.g.k.c.h var3 : var1.items) {
            String var4 = ac(var3.id);
            if (var4 != null) {
               ArrayList var5 = new ArrayList();

               for (String var7 : var3.tableEnchantments) {
                  String var8 = ac(var7);
                  if (var8 != null) {
                     var5.add(var8);
                  }
               }

               this.D
                  .put(var4, new com.yiyiaddon.e.g.k.c.g(var4, var3.name, var3.category, var3.material, var3.enchantability, var5, 0, 0, new LinkedHashMap<>()));
            }
         }
      } catch (Exception var9) {
         this.aT.add(var9.getMessage() + "");
      }
   }

   private void cN() {
      for (String var2 : new ArrayList<>(this.D.keySet())) {
         com.yiyiaddon.e.g.k.c.g var3 = this.D.get(var2);
         String var4 = ad(var2) + "";

         try {
            com.yiyiaddon.e.g.k.c.a var5 = a(var4, com.yiyiaddon.e.g.k.c.a.class);
            if (var5 == null) {
               this.aT.add(var4 + "");
            } else {
               LinkedHashMap var6 = new LinkedHashMap();
               if (var5.reachableLevels != null) {
                  for (Entry var8 : var5.reachableLevels.entrySet()) {
                     String var9 = ac((String)var8.getKey());
                     if (var9 != null) {
                        var6.put(var9, List.copyOf((Collection)var8.getValue()));
                     }
                  }
               }

               int var11 = var5.costRange != null && var5.costRange.length >= 2 ? var5.costRange[0] : 0;
               int var12 = var5.costRange != null && var5.costRange.length >= 2 ? var5.costRange[1] : 0;
               this.D.put(var2, new com.yiyiaddon.e.g.k.c.g(var3.be(), var3.a(), var3.aK(), var3.bf(), var3.aH(), var3.Y(), var11, var12, var6));
            }
         } catch (Exception var10) {
            this.aT.add(var4 + var10.getMessage());
         }
      }
   }

   public com.yiyiaddon.e.g.k.c.f a(String var1) {
      return this.C.get(ac(var1));
   }

   public com.yiyiaddon.e.g.k.c.g a(String var1) {
      return this.D.get(ac(var1));
   }

   public boolean c(String var1, String var2) {
      Set var3 = this.E.get(ac(var1));
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1q88cluegzz55","6etLENFJcn62Y8JoD7idyiaSdQ6vnWO7ITDBEVcPXKs=",2782862421189586996,-8951346101231175301,-8544942712372021776,-4166506608574882992>()) {
            case -1072702767:
               if (var3.contains(ac(var2))) {
                  switch ((int)com.yiyiaddon.m.b.a<"sglv8pzq207zg","HLjqhWu97n0sInkwbS7u3eKVmijhNJ21JRVgPc0ffM8=",-1514023352431590957,1368059309035922396,8340972652731748599,298913664342131122>()) {
                     case -1643798172:
                        switch ((int)com.yiyiaddon.m.b.a<"sq9osab6myq0g","X6zO0nD9Kf/OlLf9Oi3db1mKFwEhKn5VBdNMo/G8pKg=",3474358006636678851,7129278118434090606,-6723727564013305814,8051888946936325446>()) {
                           case 324852446:
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

      switch ((int)com.yiyiaddon.m.b.a<"s3cdk7laean6ls","NYlg4j3CZw5XKo1c6a5u0WkBnGqehn4X6K3a1S0MzUY=",335616670163936113,6143730365363211949,2826769899193617946,-8464378082480840205>()) {
         case 1032861014:
            return false;
         default:
            throw null;
      }
   }

   public Set<String> b(String var1) {
      Set var2 = this.E.get(ac(var1));
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1tep6k5kje904","OSwG4ssaJ+2Y58jqLfcKTUuw/90o2RDIQ76SXZIPNx8=",30868105845842364,8025706892424078023,7512774124154043815,8079519696872990505>()) {
            case -604657405:
               Set var10000 = Set.of();
               switch ((int)com.yiyiaddon.m.b.a<"sfwea4y0lp5m5","waMh91/VeYf9NeT1cRdZ0FOVux/xFWXINg1RoGVP9rY=",4764885219388293125,4371074403930370029,3363905286780878486,-7975222630032453826>()) {
                  case -1491267664:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         Set var3 = Collections.unmodifiableSet(var2);
         switch ((int)com.yiyiaddon.m.b.a<"s30zrp2b3iffty","QfN+7pkqCFd4eHfyVHcdtOv8uu44Gm4BFpok7vW+T+o=",7947482695317269073,-3888385459242386837,-5712288924812348704,3266886162989101709>()) {
            case -707887352:
               return var3;
            default:
               throw null;
         }
      }
   }

   public boolean d(String var1, String var2) {
      com.yiyiaddon.e.g.k.c.g var3 = this.a(var1);
      if (var3 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s13uuwyv2uphb5","1Vyt1mojFRKwo2N+zGIupqBoKhsX210PYodmd7wyePA=",-1485738735984292286,-6295009245359419991,-5043496010485985416,-4584273709110519232>()) {
            case -527753209:
               return false;
            default:
               throw null;
         }
      } else {
         return var3.B(ac(var2));
      }
   }

   public List<Integer> b(String var1, String var2) {
      com.yiyiaddon.e.g.k.c.g var3 = this.a(var1);
      if (var3 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ah6jmwhxnhya","8I7Ud6mnzQrpw3nvxUlTnj9MH03n1wr+d5Hmeyk9z1Q=",680562431110889368,345439323866713245,5360312956744721450,7923178869369152243>()) {
            case -667190034:
               return List.of();
            default:
               throw null;
         }
      } else {
         return var3.c(ac(var2));
      }
   }

   public int e(String var1) {
      com.yiyiaddon.e.g.k.c.f var2 = this.a(var1);
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2okwpf5s952t2","yPSZkNkCASACzaO0LtFmLYE1RTtJsKoisfSQXuZ2vGs=",-2932347555282916882,7790020284554141976,4351291382556487496,6921687982534027757>()) {
            case 2109301674:
               switch ((int)com.yiyiaddon.m.b.a<"s32yqq2bz1so2o","cDdohzXeXtIQS/Cd0IXQZJvWKmM504V/WJeis86tTv4=",3701971817055218536,2015245925096528129,5557295792756037199,-718240132173927626>()) {
                  case 1674278178:
                     return -1;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         int var10000 = var2.aB();
         switch ((int)com.yiyiaddon.m.b.a<"s3evm0gtgwpia3","8QGgSB8FrwxxoprFpxKFt2DgwvZWgiBCtjtem7QfkZ0=",-7282100614851975776,8582854496599761301,-7278104872218401714,346092775781412359>()) {
            case -1005502920:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public List<com.yiyiaddon.e.g.k.c.f> W() {
      return List.copyOf(this.C.values());
   }

   public List<com.yiyiaddon.e.g.k.c.g> N() {
      return List.copyOf(this.D.values());
   }

   public String bc() {
      return this.minecraftVersion;
   }

   public int aA() {
      return this.dataVersion;
   }

   public List<String> X() {
      return List.copyOf(this.aT);
   }

   public static String ac(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2m0upjoo8l73p","DcPUSadFOA2stQqeeAfP/WcEIotkXBVFdqeH5DU2He8=",2200421937235115982,21318600061609239,3679876500981166421,-3802149327161154070>()) {
            case -776008803:
               if (!var0.isEmpty()) {
                  if (var0.indexOf(58) < 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2534yolylucik","ewtIWe+Hju2UtnADZr749lcnOvVJ9ADiaYjsI2RjG1M=",-2738358293216958287,1611249351522257831,-967778234516935557,-1938702155226637678>()) {
                        case 2018915622:
                           String var10000 = var0 + "";
                           switch ((int)com.yiyiaddon.m.b.a<"sycbdyetlvkoj","l6EssQmeWKchgyHPqrmB2fpHmABVcjadJgEUeME5jiQ=",-5174249572176594229,-2597293041620138841,-679578256644595285,8091983667330452356>()) {
                              case -972692745:
                                 return var10000;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s3dz612w4eq4y5","K8Drp+3BOy/qLLEHFz8u0Krr8PpXpsB4cunPxEyThTo=",6688371356576143426,-5557192734765573173,-2922887656120787704,3679006162930430900>()) {
                        case 217776238:
                           return var0;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sdy40gww5lnds","NPYSjT5Xn/ZKCwk6G3OChQ2iU2sbAfM4u1///H0VlHQ=",-3935948181353263794,3940753542626414184,7470965505296933801,5632459598784077331>()) {
                     case 1903512098:
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

   private static String ad(String var0) {
      int var1 = var0.indexOf(58);
      if (var1 < 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s24ili2ebit2ue","oExwkQPlrgw6tOSRAv2psWhJjUWq+GpjeuNzwD8xDhw=",-8289771381255508478,7201939647093041059,-1275023562180306836,7573357158245347889>()) {
            case 1358166722:
               switch ((int)com.yiyiaddon.m.b.a<"s1b84sfq89v44z","eq0laylKeBGf7nu904Wa6O0kkF73GiQr1D0EwnzuAkU=",3133242464292009238,356910222295120977,712395135522770808,-3842513006545060434>()) {
                  case 16389872:
                     return var0;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var10000 = var0.substring(var1 + 1);
         switch ((int)com.yiyiaddon.m.b.a<"s1fkdnft063vs0","hV6ynJIHpMbq7kIwYm9YIlLAiH+RV6lG91bszdmUlik=",4681482372371534181,8698924600040472771,-7902324509931400391,-7373461506558026962>()) {
            case 418986007:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private static <T> T a(String var0, Class<T> var1) {
      String var2;
      try (InputStream var3 = com.yiyiaddon.e.g.k.c.class.getResourceAsStream(var0)) {
         if (var3 == null) {
            return null;
         }

         var2 = new String(var3.readAllBytes(), StandardCharsets.UTF_8);
      } catch (Exception var8) {
         return null;
      }

      return new Gson().fromJson(var2, var1);
   }

   private static final class a {
      String item;
      String name;
      String category;
      String material;
      int enchantability;
      @SerializedName("modified_cost_range")
      int[] costRange;
      @SerializedName("reachable_levels")
      Map<String, List<Integer>> reachableLevels;
   }

   private static final class b {
      @SerializedName("exclusive_groups")
      List<com.yiyiaddon.e.g.k.c.j> groups = new ArrayList<>();
      @SerializedName("conflict_pairs")
      List<com.yiyiaddon.e.g.k.c.l> pairs = new ArrayList<>();
   }

   private static final class c {
      int base;
      @SerializedName("per_level")
      int perLevel;
   }

   private static final class d {
      String id;
      String name;
      @SerializedName("max_level")
      int maxLevel;
      int weight;
      @SerializedName("min_cost")
      com.yiyiaddon.e.g.k.c.c minCost;
      @SerializedName("max_cost")
      com.yiyiaddon.e.g.k.c.c maxCost;
      boolean treasure;
      @SerializedName("exclusive_group")
      String exclusiveGroup;
   }

   private static final class e {
      List<com.yiyiaddon.e.g.k.c.d> enchantments = new ArrayList<>();
   }

   public record f(
      String id, String name, int maxLevel, int weight, int minBase, int minPerLevel, int maxBase, int maxPerLevel, boolean treasure, String exclusiveGroup
   ) {
      public int i(int var1) {
         return this.minBase + this.minPerLevel * (var1 - 1);
      }

      public int j(int var1) {
         return this.maxBase + this.maxPerLevel * (var1 - 1);
      }

      public String s() {
         return this.id;
      }

      public String a() {
         return this.name;
      }

      public int aB() {
         return this.maxLevel;
      }

      public int aC() {
         return this.weight;
      }

      public int aD() {
         return this.minBase;
      }

      public int aE() {
         return this.minPerLevel;
      }

      public int aF() {
         return this.maxBase;
      }

      public int aG() {
         return this.maxPerLevel;
      }

      public boolean bb() {
         return this.treasure;
      }

      public String bd() {
         return this.exclusiveGroup;
      }
   }

   public record g(
      String itemId,
      String name,
      String category,
      String material,
      int enchantability,
      List<String> tableEnchantments,
      int costMin,
      int costMax,
      Map<String, List<Integer>> reachableLevels
   ) {
      public g(
         String itemId,
         String name,
         String category,
         String material,
         int enchantability,
         List<String> tableEnchantments,
         int costMin,
         int costMax,
         Map<String, List<Integer>> reachableLevels
      ) {
         tableEnchantments = List.copyOf(tableEnchantments);
         reachableLevels = Collections.unmodifiableMap(new LinkedHashMap(reachableLevels));
         this.itemId = itemId;
         this.name = name;
         this.category = category;
         this.material = material;
         this.enchantability = enchantability;
         this.tableEnchantments = tableEnchantments;
         this.costMin = costMin;
         this.costMax = costMax;
         this.reachableLevels = reachableLevels;
      }

      public List<Integer> c(String var1) {
         return this.reachableLevels.getOrDefault(var1, List.of());
      }

      public boolean B(String var1) {
         if (!this.c(var1).isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"sbuj0r44asc5c","FmU9ZHF0BHEZi/DVZLMEc1q9QtMAAhy0b+czin/rFSc=",2157789975561465639,-7307346132793562378,-5751569055729564787,7037275985935511865>()) {
               case 316483880:
                  switch ((int)com.yiyiaddon.m.b.a<"s19b6cbpp0ws9p","8v6lIAMP4EhDZ/rHb/HDZEoQ9LvSpC8zZXSgy1lzfVQ=",8965518835501481535,-3198429480278171790,6937911185623497142,6414984610715933974>()) {
                     case 1537913854:
                        return true;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s1tebca3m6zdj8","hT2DRlHa3lMBLe+PT8VCCCTFZIgVHAtlAjZ67pEsPeo=",-2667092155002379678,-7988723348446024010,-5086356063765999402,7600313873303248698>()) {
               case -622501804:
                  return false;
               default:
                  throw null;
            }
         }
      }

      public String be() {
         return this.itemId;
      }

      public String a() {
         return this.name;
      }

      public String aK() {
         return this.category;
      }

      public String bf() {
         return this.material;
      }

      public int aH() {
         return this.enchantability;
      }

      public List<String> Y() {
         return this.tableEnchantments;
      }

      public int aI() {
         return this.costMin;
      }

      public int aJ() {
         return this.costMax;
      }

      public Map<String, List<Integer>> k() {
         return this.reachableLevels;
      }
   }

   private static final class h {
      String id;
      String name;
      String category;
      String material;
      int enchantability;
      @SerializedName("table_enchantments")
      List<String> tableEnchantments = new ArrayList<>();
   }

   private static final class i {
      List<com.yiyiaddon.e.g.k.c.h> items = new ArrayList<>();
   }

   private static final class j {
      String id;
      List<String> members = new ArrayList<>();
   }

   private static final class k {
      @SerializedName("minecraft_version")
      String minecraftVersion;
      @SerializedName("data_version")
      int dataVersion;
   }

   private static final class l {
      String a;
      String b;
   }
}
