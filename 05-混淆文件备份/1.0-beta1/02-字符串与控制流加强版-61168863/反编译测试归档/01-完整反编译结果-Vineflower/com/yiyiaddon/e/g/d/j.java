package com.yiyiaddon.e.g.d;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class j {
   private static final Logger h = LoggerFactory.getLogger(
      (String)com.yiyiaddon.m.b.a<"suzuhwfy5ug6p","QJzyT991TMHYtYzbTSv2r6RQ0JeC51lBkjuhEGrlIwrLVM9XGtW4+0nkBlz3jl7zZYB1xMxi7etsi+z9qQg=",-796284178163840835,5664271627519808345,2967005572411784572,-8586365572853820101>()
   );
   private static final String jB = (String)com.yiyiaddon.m.b.a<"s37deysc6owfqm","+H8qy7uy3hrBBNkrGOI6AyosiaLDge/M1jTq9xyEgpvNMyGDP3t+uqzJscxM3OEokm8iTdsSEYMFLPePmlOXl3u/zTbTcCAN64Z9mE4ypq5l4J4/Mlrz1ySpFC2iXGzj7YBSrQ==",6058452526972854880,179895195063617368,6112517650961457618,793841746673334362>();
   private static final Gson c = new Gson();
   private static final Set<String> s = Set.of(
      (String)com.yiyiaddon.m.b.a<"s2mee1we1e6yoz","P+BWRIF2ZM0AUZYoqPU/JljAsOYuIq7nyORBdy0SdMRMXs92YukjexL1",-284370569256587506,-2946721055790510699,-1434229930549326851,6280869781033438793>(),
      (String)com.yiyiaddon.m.b.a<"s20hq5zg35n4hb","cCm14nOEp4eshoZzclzuQOMp53fBNEfm4J2w92qBh3AQI715fb09GTG5iygkwCN5eReEuA==",-3752422307973337269,6176209402498877235,9117731192243452568,5912809869389120815>(),
      (String)com.yiyiaddon.m.b.a<"s10j0cjzsoz2lt","slQGkoNy7IVOyV3Eh2PUFJPS3BQgcp4Gc+D++zYN6a9eB+IV79yipdhIPFKHIjPn",3266284902592527774,-7377472548361219159,-406682867947400545,2534372366801244246>(),
      (String)com.yiyiaddon.m.b.a<"s3he5smhz9vq41","MC8u9qD1dXjqr01v9dzq7K3OgpgFXFf+tJ+fb/vxMvvyu4bPvvSSYGHX43Nd1cZ/ebM=",1352366577086788914,-6648372338917669002,-6874369147902081335,-360301745619276332>(),
      (String)com.yiyiaddon.m.b.a<"s2apmvzlgje12k","o0kR0jOAE/VfvhJGXgQmtY48DPmPHHsLpBCLRIcj+bc9dwAZ9ZX23J7tlUa68TNHEN/RNG07",51623152001898532,3224013321887407623,-4085759497179687014,4415807848064255951>(),
      (String)com.yiyiaddon.m.b.a<"s13bjx57942irf","OyHJmkoN4qaJMg7tknqez79t9+uw1YYzCVWZTwlk6LuIRCmQQSGU1RAZbJAubijMo12T2VO4vt+Q3w==",-7983423978443436124,8410021779806077583,4156115090902374405,-8043565263422024014>(),
      (String)com.yiyiaddon.m.b.a<"sa6z8t44jz81p","1G16GlPyeB3ocM90a8QQnJnE5OsAXodswuZjc+iJSzH9+lS98C7DSPg9IsRLLc4i",-5281085228115185250,-5565606291840393419,6465126531162526256,-3759381240400569386>(),
      (String)com.yiyiaddon.m.b.a<"s17v53vvpxre7b","v2RtT/vtUkaQ3P3249oU//FbJAOwy/D2X4i0wb9owgDrZIPFzU73vw==",-3415547254272913677,2418155282944281452,-3787509313346148566,-7069146341440336072>(),
      (String)com.yiyiaddon.m.b.a<"s2ojsyo3q3osq","QQXvVq8oA55rN9mPvwAQpQ+KuBaP72GNo/AIAAbwKtMfOMbYBULcFtt9",-2606958495429464693,3315299238631040677,1181317101321727025,-5468247189096386010>()
   );
   private static volatile j a;
   private final List<j.a> gears;

   private j(List<j.a> var1) {
      this.gears = var1;
   }

   public static j a() {
      if (a == null) {
         synchronized (j.class) {
            if (a == null) {
               a = b();
            }
         }
      }

      return a;
   }

   private static j b() {
      String var0 = aH();
      if (var0 == null) {
         h.error(
            (String)com.yiyiaddon.m.b.a<"ssiqolsl4mcno","wZQk+xUViWYvr2nDCwq6sFAY0btm2/No+mzadUXGQuuMiEHKBWais30sWydCF6kQjsr5Q1P6pHEyw6O9Ldzw0iVmFCao9oKOASi0GtUIzmw1UyRULOUwEXaH2msDsMYnH0nKuwTJeQPpHL+McpsgS4mKmgEaUnDJDwAE4P0yf8zF3QkbRM9FufdxjBs=",-5813313973669331251,-8734651533925542914,1208857921298152051,3842124101096010799>()
         );
         return new j(List.of());
      }

      try {
         j.c var1 = c.fromJson(var0, j.c.class);
         List var2 = var1.gears == null ? List.of() : var1.gears;
         return new j(h(var2));
      } catch (Exception var3) {
         h.error(
            (String)com.yiyiaddon.m.b.a<"s1ft3opq40k1g9","v6FlHcInEvoOrpCU2hKgNenn0R84+r3o31nV/VT2MwrmHgdBkgXX6mxxLYx5Z+UYaj7GcOf9vlRFLdvM99aMcWS2EAhEUod/JE+xIW2k",-155369388410972411,8767159606658753129,-5205055505896524869,2596826169602065377>(),
            var3
         );
         return new j(List.of());
      }
   }

   private static String aH() {
      try (InputStream var0 = j.class
            .getResourceAsStream(
               (String)com.yiyiaddon.m.b.a<"s37deysc6owfqm","+H8qy7uy3hrBBNkrGOI6AyosiaLDge/M1jTq9xyEgpvNMyGDP3t+uqzJscxM3OEokm8iTdsSEYMFLPePmlOXl3u/zTbTcCAN64Z9mE4ypq5l4J4/Mlrz1ySpFC2iXGzj7YBSrQ==",6058452526972854880,179895195063617368,6112517650961457618,793841746673334362>()
            )) {
         return var0 == null ? null : new String(var0.readAllBytes(), StandardCharsets.UTF_8);
      } catch (Exception var5) {
         return null;
      }
   }

   private static List<j.a> h(List<j.a> var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s25jpw17989fso","YnjrRLGT+qzfq5fnRVPTZXVlJcR+cv9hNFXgR6a/QNU=",1192790930355646387,-6104326816046349222,5333358828821800699,5161152874957651367>()) {
            case -538167825:
               return var0;
            default:
               throw null;
         }
      } else {
         ArrayList var2 = new ArrayList();
         Iterator var3 = var0.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s2uhfdk2a00ln6","/SQfLgPYnC2m0DzhQz4q6hkc1Xc+ArJV0dNeAXMgB0s=",-4146308989129898799,-7605305263056640563,3655386670850841957,-5132935889663977514>()) {
            case -894023985:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2l2zvekot1lc","SNGpR3dijYWVnl4m+arC+PfRsn4jj2UVozxZDZMKN1U=",1540939818033398874,8716948798645275186,-7040119750926150713,-5743971070842114344>()) {
                     case 411130326:
                        j.a var4 = (j.a)var3.next();
                        if (a(var1, var4)) {
                           label28:
                           switch ((int)com.yiyiaddon.m.b.a<"srte37e857g5","7AeXsblnpax1q4vPBdIXaAOcHARd6pr6Lpue0oeOFq4=",-2026480879858569080,5810866721694403053,-7779604619645566019,-1278351161606534209>()) {
                              case -1364187159:
                                 var2.add(var4);
                                 switch ((int)com.yiyiaddon.m.b.a<"s4czkcasvyh70","y1/wA9SusNO/hVLT5/cAqAy726mQIEJy/cJWNBp2ROQ=",-6117821045165577274,4402200009099760137,3470183403328068378,-815540798665211465>()) {
                                    case 1851473418:
                                       break label28;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2v5sqvd98fiqs","UTJTWFk81tB15FGg1l5LDGECccJnOFUcR33nbVhi3EE=",8892091619761100965,-2768577168146782350,8215605796546245795,-1538799712078098965>()) {
                           case 1454335120:
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
      }
   }

   private static boolean a(Minecraft var0, j.a var1) {
      Identifier var2 = Identifier.tryParse(var1.id);
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s83s0vmaqvumt","FywTaIQnoxBk39s3zH5+xA9fII7kOq+zziMpgEol0oQ=",7177843083735920822,4513500134139795117,9162762706174035445,-2649087063006577731>()) {
            case -1067908874:
               h.error(
                  (String)com.yiyiaddon.m.b.a<"s26b7j06j43pz9","ttOuJE7zW//B5bxj2vxZISj7ZznxwC3v36rZvi90WL+edqvZdWIp6PXxr937PQTw6f+ZRANAHY0KmgTv9MxSMrjld9UQn2RIvDuNACVU6sm2oA==",-2832023588766983609,2155256136291296660,6443041988600545588,-8585461018516805282>(),
                  var1.id
               );
               return false;
            default:
               throw null;
         }
      } else {
         Optional var3 = var0.level.registryAccess().lookup(Registries.ITEM);
         if (!var3.isEmpty()) {
            label38:
            switch ((int)com.yiyiaddon.m.b.a<"s3dq65ufp9m7k","k2Q7UHXwlbO4FwS52VTOQzMT6WaKe0+xBvY6Rh1gux8=",-6686948145705532287,-6545111378296850689,-9195228548171852232,176729973120250966>()) {
               case -1331774030:
                  if (!((Registry)var3.get()).get(ResourceKey.create(Registries.ITEM, var2)).isEmpty()) {
                     Iterator var4 = var1.profiles.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s1tr001k13rs2i","lKWWStDUmm1unX6CBrfa2QPPyRew8+ip9U6px+L6yWs=",7388189420145892979,5362801652038058300,6377973633814095175,-6116958533171986035>()) {
                        case 467558746:
                           while (var4.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"ss58wjzwzqvlp","kIFO5dBpLhJtPq/w8aQDRIX57qpkNqC+wSTux0LGnfM=",202829292954082434,-3013928422124469295,-8102200344814038450,3808740083390723076>()) {
                                 case -109128670:
                                    j.b var5 = (j.b)var4.next();
                                    a(var0, var1, var5);
                                    switch ((int)com.yiyiaddon.m.b.a<"s1habiwf9m62q6","iZlksB79bbsku00IF7B31Eahalp0hnQ16jXAH3yszqc=",-1223579123934923458,3006778284680244547,5445695098510326674,-2588350743096774388>()) {
                                       case -255140525:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           return true;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"sqp1kpha7urce","dcwRsDIZIu7Cca3VBoSCUY0QivCjhFNDM4lKYalgcFM=",-2420851870438481435,-6636426394950216417,-8172749968965706187,8606769258306657100>()) {
                     case 1994940185:
                        break label38;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         h.error(
            (String)com.yiyiaddon.m.b.a<"s16xkndhvdcfrh","UxEEL5W/yrhnIa/kuq0gbTRAwsBXeUSvc5bPGYyO4hnwFuOBooL+NFX0R3jyyFhRyqp7sgX1w8eNHXfKRx/5beQfvPYNQwAC3SsAFPUol4uIoL/Z",-718076418316208306,-7760351475835999134,-8569736571273316358,3489701698914707535>(),
            var1.id
         );
         return false;
      }
   }

   private static void a(Minecraft var0, j.a var1, j.b var2) {
      Iterator var3 = var2.targets.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"sqwkzqsuj8d38","JSdMqxLMX4ATxQcyoJjQEfLp1h5mnDEzsZTYaCV8trc=",-8559132790154456199,-7752432931165520705,7775155670896138680,-5013798344251669010>()) {
         case -61623140:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s29v0tfjziqvfl","wN+OodBsYWSUJqxAWCFelec6Vpd8EHl74AbKAOOfI+A=",8650952538894708018,-8414229522284807578,7155846876643238652,-9035645162479539158>()) {
                  case 1898484343:
                     j.d var4 = (j.d)var3.next();
                     a(var0, var1, var2, var4);
                     switch ((int)com.yiyiaddon.m.b.a<"s1dzsap7eorbdg","l8C1ljllw7bVbH7wICGnJZYVPMUzCBf8cz4rPy6QBIM=",7975954380963161140,-8919001051782323730,389994479518021300,-7472912942618855352>()) {
                        case 2031261900:
                           continue;
                        default:
                           throw null;
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

   private static void a(Minecraft var0, j.a var1, j.b var2, j.d var3) {
      Identifier var4 = Identifier.tryParse(var3.id);
      if (var4 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1mgtp1kka10vw","SCOyIkbBEaOVKv4pDJTeYtFfhTJ5OEeJp+tzjbMKkHY=",-51995332476700509,-1268158923904375771,7962106561217765120,-7905439192833454421>()) {
            case -51959237:
               h.error(
                  (String)com.yiyiaddon.m.b.a<"s3q3jrw7zwosl8","6KfFfDojj4UC8l+9eRStP9dkaUhA1yqyfPNT8cPGJYtHZOyAsmh9JrW+EFu8iqnoR7NO1ckMnA6p38JGEfoRJhwxAMBZ+T3aCidVyRwFUC7G9d3KnY77N6dDxTE8haFix33RRFRjsE5esmcl",-3600546049248129366,-6410890116096514561,-4169952483825895873,7850936152793072362>(),
                  var3.id,
                  var1.id,
                  var2.id
               );
               return;
            default:
               throw null;
         }
      } else if (!(String)com.yiyiaddon.m.b.a<"s3iiko6f4qv7fm","bDsVLgfqoaMNk+DRHAP4+KWyz3cdVfRC9iFDlGnzex1Ui/CHHg8gM1aDvdSjig==",5975328924878393645,9000406041642894473,1111513007475965451,-4696243960218204162>()
         .equals(var4.getNamespace())) {
         switch ((int)com.yiyiaddon.m.b.a<"s1qvyp77mc1wxa","cB/52BxLwxDVR3jtFivMnGwNW0f5cocCqT5MK7Lr1Lc=",-8425427280127340790,-6764275611761964501,637191492437230234,-5078940689115253319>()) {
            case 1552598581:
               h.error(
                  (String)com.yiyiaddon.m.b.a<"s233rioos69jys","cJtsSfH7Y8jlC4N4SYGmWeg0f++M4znBaZhIZlfKnxUi7Xo1eGJh7Wovr1pjv0lpdpkmXsJjVHpJ4FkxFDCyQVSls12ZH9msvrH3y4dZ7GBWKkbM1TIYJZfB",6713415217531207433,-27221452733030218,12829894904137269,502769199436214082>(),
                  var3.id,
                  var1.id
               );
               return;
            default:
               throw null;
         }
      } else if (s.contains(var4.getPath())) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ddfto5j7g40n","HSnqEY9QRieaA3wQbOZkerzK9duPlwyyp14ogMPMFBU=",3281404820433776988,6745740117544615471,2698915924243083091,-6436741872289183041>()) {
            case -307276225:
               h.error(
                  (String)com.yiyiaddon.m.b.a<"s2b45qgiypnajr","+fJ0+3vHb7dijHloWsBYDvL8971+iVohSa7VkC0fvDt8++2LwpFmp0v+09N34Q8EZUtvdvRqPMVNQAhTjSgbR+42yjl46T9ObF8ZqtwYJHciRl9LV15Rig5ACcFwc6LjnlwExqdB",-5372215563288029216,1094191017989927253,-882572095736682175,4192506806049858725>(),
                  var3.id,
                  var1.id
               );
               return;
            default:
               throw null;
         }
      } else {
         Optional var5 = var0.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(ResourceKey.create(Registries.ENCHANTMENT, var4));
         if (var5.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s1vlkuk3e8isqn","X5Oa07ZzqACi58qSQKJaF7LRy2AKhFuPiyMWaEel5OY=",5331592365707006660,-8527571466556748294,-4021892300898115085,6800478297016366310>()) {
               case -615147532:
                  h.error(
                     (String)com.yiyiaddon.m.b.a<"s2li3dl8wl6t93","agW39eHJgIHgUp9B5oZ2aEwNmkVLL51mQqmvtxet5YQ2pdPPzrv7O3Ao5IRow1h7fk1e81CAC/K9ZP2W1Z35hc1jOUVzhSXNFyxaWS4eIeOCEkZuskNMoEplUpNvq+mozDo=",2352981931483535309,6067976019158897523,1919820158315466650,8763581602623218158>(),
                     var3.id,
                     var1.id
                  );
                  return;
               default:
                  throw null;
            }
         } else {
            Enchantment var6 = (Enchantment)((Reference)var5.get()).value();
            if (var3.level >= 1) {
               label47:
               switch ((int)com.yiyiaddon.m.b.a<"s38uhu1oi7uc77","rQNZFdOOHt8NV7o2UIBBsRdRW8kbn2/T9hufq1ygoZI=",1593515093122336222,8104513068409393157,-739843021436104160,6816092008743391813>()) {
                  case -1741942228:
                     if (var3.level <= var6.getMaxLevel()) {
                        Identifier var7 = Identifier.tryParse(var1.id);
                        Item var8 = var0.level
                           .registryAccess()
                           .lookupOrThrow(Registries.ITEM)
                           .get(ResourceKey.create(Registries.ITEM, var7))
                           .orElseThrow()
                           .value();
                        if (!var6.isSupportedItem(new ItemStack(var8))) {
                           switch ((int)com.yiyiaddon.m.b.a<"s34rsktau7hb7y","S54vB/OFD6l7uaHW8kxj4SsD5lbpk+1SlpsoeSpOlv8=",1740787616379555748,-7040173538229814928,4259048119994034781,-8399020142067653525>()) {
                              case 109670483:
                                 h.error(
                                    (String)com.yiyiaddon.m.b.a<"s1vp5xjlrtrz55","xMc7SceF/Giu5AchnLMyWDHFCNwt2IHYIJV/d4O4m3KQm66KMp/vIsw2UhlNbVv2hUmOIh48p5d21hhsIoJ/WL+vVFaU/aYsNc1RdHHqE/nEfRnI3WkLJ5LO0u3Dnvi/M+wwWr2tbSf4sYSJEtL7Nw==",7186245089317339937,-773457828460250396,2260919751356257623,-2090557698485917153>(),
                                    var1.id,
                                    var3.id,
                                    var1.name
                                 );
                                 switch ((int)com.yiyiaddon.m.b.a<"swibximk7ylyn","bSU7B/Pm8FZfyQY3Ej3JnJKXT8gc4uRL9TPRCK2n0sI=",-7012780440025690011,3437614343844123131,8788739424893117466,4362773374113609004>()) {
                                    case 138702424:
                                       return;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return;
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s9oteiz297efi","oV5ZAioeN/YhaW8TAa3Ky4ffdWVw2TfBl4km2E7+M9Y=",7088739516783811472,-3239129229820862150,8798311642186264139,2155117021216281423>()) {
                        case 1602326784:
                           break label47;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            h.error(
               (String)com.yiyiaddon.m.b.a<"s5g3n7y0zigzs","RyQsACEUebHyObQz4fCGoc9lX1mkGmZAVN0esdZ91VM62AW4v4aSZojI+P6czf/wAxsSOv1m7JQCGlyWo4REwf1ExaDH2W+QvLr8dMfD/n78ZVHELpvX57mW+pxbUpCoA9DbtyaCy/bNBMgRw6Q/oNq43cFLBJzJ",-5258112304382506831,-1553701457091047653,4764311340950845294,-1674155368595485153>(),
               var3.id,
               var3.level,
               var6.getMaxLevel(),
               var1.id
            );
         }
      }
   }

   public List<j.a> N() {
      return this.gears;
   }

   public List<j.a> b(String var1) {
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.gears.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1rw0dlzjsh6p1","dPe27oA3mAjwDmfJOV5AQ9DicLLEcMTsjIyofWjlHWo=",6471743616080743439,-2059827187553000983,-7204250326789237868,-3738522099202801461>()) {
         case 1151338231:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2mtu71oxrhpla","VinnO78KHnnMNA/5gYkAfaW5kL/6JQY2JJdMRx5/fK0=",1724514892305123620,-4632660780224660721,8408213460821407569,7748685242669316174>()) {
                  case 1604594096:
                     j.a var4 = (j.a)var3.next();
                     if (var1.equals(var4.category)) {
                        label23:
                        switch ((int)com.yiyiaddon.m.b.a<"s1lb2pn3q48bpb","HHrCZx7tUmIAvSZ9exAmRFF6VRsKIn3o+hkmiKDii0c=",6804541379517668557,8731326717654945376,-3548590474151850339,-8499235629764918015>()) {
                           case -264800713:
                              var2.add(var4);
                              switch ((int)com.yiyiaddon.m.b.a<"s21ybii8snl35q","AqwQwozArkTpRw7n5z8Wv92RPctL4SshFqc/Id942yM=",7393345099166502425,6444401658609088396,4853133256663911979,4263990906417007611>()) {
                                 case 1691450194:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s1ygygtp4r6pax","qnl1ph9C7n1/imb5YFdCfbguUSXh30xHMAiCBC4CD4E=",-6970853485446435992,-531100759249273578,1005260458569842152,2245630908553837937>()) {
                        case -2119355873:
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
   }

   public j.a a(String var1) {
      Iterator var2 = this.gears.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3uzrs708qcnqz","2NKAOMEK+wi05VXPtW3Yu2T+A6xA0kStSostXBLvFSY=",-2137338545672300929,-6042733067343237676,188728429356791111,-6468577587157389045>()) {
         case 99059398:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2enswq1ri7c9f","xCREPWMR9SOzgWjDNYtmavR/gEVoYPl8mUDqJ/Qedwg=",5030817294981001907,3181047668242652290,-3419527285056490698,-2432804239775600456>()) {
                  case -1723937984:
                     j.a var3 = (j.a)var2.next();
                     if (var3.id.equals(var1)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1leuo922bhfxg","lVGTqqmsYM0j44vrFmb4gswm64z+hOksUdTLRLNduHY=",3735630646679454143,1669872743761971648,1707348496995527067,5062673304690881679>()) {
                           case -1869511189:
                              return var3;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3auwjab4clrjl","LL6nhtrO5vP3YbuhLyPd/YaAePaoo+PbbiJiCfAI/N8=",6197441958492099318,-3916608825486383309,3210101866998508584,4971168693421635395>()) {
                        case -1809378039:
                           continue;
                        default:
                           throw null;
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

   public int e(String var1) {
      try {
         Minecraft var2 = Minecraft.getInstance();
         if (var2.level == null) {
            return -1;
         }

         Identifier var3 = Identifier.tryParse(var1);
         if (var3 == null) {
            return -1;
         }

         ResourceKey var4 = ResourceKey.create(Registries.ENCHANTMENT, var3);
         Optional var5 = var2.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT).get(var4);
         return var5.<Integer>map(var0 -> ((Enchantment)var0.value()).getMaxLevel()).orElse(-1);
      } catch (Exception var6) {
         return -1;
      }
   }

   public static final class a {
      public String id;
      public String name;
      public String category;
      public List<j.b> profiles = new ArrayList<>();

      @Override
      public String toString() {
         return this.name;
      }
   }

   public static final class b {
      public String id;
      public String name;
      @SerializedName("default")
      public boolean isDefault;
      public List<String> exclusiveWith = new ArrayList<>();
      public List<String> forbidden = new ArrayList<>();
      public List<j.d> targets = new ArrayList<>();

      @Override
      public String toString() {
         return this.name;
      }
   }

   private static final class c {
      public List<j.a> gears = new ArrayList<>();
   }

   public static final class d {
      public String id;
      public String name;
      public int level;
      public boolean excludable;
   }
}
