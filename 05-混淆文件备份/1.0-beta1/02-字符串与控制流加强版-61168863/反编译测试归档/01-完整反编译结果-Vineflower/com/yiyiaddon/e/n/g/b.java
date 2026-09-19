package com.yiyiaddon.e.n.g;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;

public final class b {
   private static final int mc = 64;
   private static final Path g = Minecraft.getInstance()
      .gameDirectory
      .toPath()
      .resolve(
         (String)com.yiyiaddon.m.b.a<"slrt0y5prylq3","etCq5D6PtLBdrSW0BiSSi8896ZjBOJ+Spx+RcnKZU/vSZk7UTNZmC8ADW2CTl2rANhM=",7116084473316589365,-1200527850242454076,-5384135256842145114,-3083820842990476011>()
      )
      .resolve(
         (String)com.yiyiaddon.m.b.a<"s1jtlvb0mkt8dy","T744TaKj0eRYhKJqviz8+m7AcIcVKyH8FrjUOSjhC7nbyQakIF0ETnL4Ki4n/0Ca",-2649002870110848827,1955731955276019548,857258086021430139,-3409867440366274280>()
      );
   private static String rF;
   private static long G;
   private static final Map<String, b.a> S = new LinkedHashMap<>();

   private b() {
   }

   public static b.a a(String var0, String var1, String var2) {
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s5wj6jlqos232","W36vwjrFCdisk6LBkyLnISTQ1kAL62gxXuUfWglJypI=",5971386324272308730,4693057868951847769,-8715699628855381904,-8504374971205535761>()) {
            case -1285486175:
               if (!var2.isBlank()) {
                  j(var0, var1);
                  return S.getOrDefault(var2, b.a.a);
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2z491il54npy9","IWt6UpnhCg2CCjPpoYKG1sG2GsPkNSne0OivU2toQAQ=",2624337551980303297,-3260473306571578531,-7599275881944804064,8167790038620406912>()) {
                     case -1599354669:
                        return b.a.a;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return b.a.a;
      }
   }

   public static boolean a(String var0, String var1, String var2, b.a var3) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sc3jwhko5d22n","hHt6CwSalUuZGhzAyo9McabTZhgfz4AYggAVRGnHmdo=",8624124114514009450,-5754362548422816963,-1008599224927946052,-7452187375211841339>()) {
            case 2113783020:
               if (!var0.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1cwbxx2bqmhp9","by6N0XY0MuM5Hji+ahHmUFU8TT6DMSlLIAG9gJq5mKw=",47748770626793827,-2392417237294879275,7307177050651140862,794972484334408282>()) {
                     case 1486721716:
                        if (var2 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2r9ytc13wh2vu","54T/qpT1YKGC/tkRyCpC7ZluxWSYZ9OMePBaxBZR7g0=",8738675986521685809,7941363524630278515,3972190802321549,-1848006963875715801>()) {
                              case -836996246:
                                 if (!var2.isBlank()) {
                                    j(var0, var1);
                                    Map var10000 = S;
                                    b.a var10002;
                                    if (var3 == null) {
                                       label38:
                                       switch ((int)com.yiyiaddon.m.b.a<"sz3sgmqjctom4","cZ/WcHE+572BoPc/XDOMUzsQGVMeu3GPwDGmDlpOu60=",5602312129705301489,-5802230937229291915,3275050737350178704,-7287301399858639427>()) {
                                          case 2027486721:
                                             var10002 = b.a.a;
                                             switch ((int)com.yiyiaddon.m.b.a<"s11qteoq1lg34o","Kb+SqtURP+rLEhuzc7gdaeHddWs54rf1f5N7yWxQdgE=",3433014173935607904,81787546531331218,6265625926799384222,8098850794398801751>()) {
                                                case 772291588:
                                                   break label38;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       var10002 = var3;
                                       switch ((int)com.yiyiaddon.m.b.a<"s2mbhthcomjszv","werWw6l9u5WA/ahRYToXye9WtqGsgbpNOGWVcl69UTQ=",7402087840720790285,6816258713186661643,6398435693108008454,-3000061477746490810>()) {
                                          case -987366181:
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }

                                    var10000.put(var2, var10002);
                                    boolean var4 = f(var0, var1);
                                    if (var4) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3s7eontitbpt7","z96Fsh2Z7D/K+o4HNyzeQvF4tXECQZqA71dNJ35DAjo=",-523218275240595829,1702885248994128112,-2789220574448526634,-5026152560395025596>()) {
                                          case 759423391:
                                             G++;
                                             switch ((int)com.yiyiaddon.m.b.a<"s224snobhg90nt","IdhobMj/UsiY8EC4eMs2ZPJNfYtVGrPnN3kWs22dyw0=",-1248766085144909070,-7308703183616802601,4843156922948853640,-2553178785355086722>()) {
                                                case -1970580889:
                                                   return var4;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    return var4;
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s1upykm2tiv83","GcPJ3vmWk/k6sj4BmwCPFAz4/XkRoRpVQLTTWz4vkZ4=",-4403592049298833087,2078024474996229923,3396554968072518385,3346797641927291525>()) {
                                    case -1483511367:
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
               break;
            default:
               throw null;
         }
      }

      return false;
   }

   public static void cy() {
      rF = null;
      S.clear();
      G++;
   }

   public static long o() {
      return G;
   }

   private static void j(String var0, String var1) {
      String var2 = var0 + aC(var1);
      if (!Objects.equals(var2, rF)) {
         rF = var2;
         S.clear();
         if (var0 != null && !var0.isBlank()) {
            Path var3 = b(var0);
            if (Files.isRegularFile(var3)) {
               try {
                  JsonObject var4 = JsonParser.parseString(Files.readString(var3, StandardCharsets.UTF_8)).getAsJsonObject();
                  JsonElement var5 = var4.get(
                     (String)com.yiyiaddon.m.b.a<"s2lxofkxfodv63","UcFH9YUuWoGAOHnal91eOSnc21IpU6qeumEt0kUdkWmvjzY3",1086000427675319305,-1517937994220292708,-8174220593177160780,-8373218771131788310>()
                  );
                  if (var5 == null || !var5.isJsonObject()) {
                     return;
                  }

                  for (Entry var7 : var5.getAsJsonObject().entrySet()) {
                     int var8 = ((String)var7.getKey()).lastIndexOf(64);
                     if (var8 > 0 && aC(var1).equals(((String)var7.getKey()).substring(0, var8))) {
                        JsonObject var9 = ((JsonElement)var7.getValue()).getAsJsonObject();

                        com.yiyiaddon.e.n.g.a var10;
                        try {
                           var10 = com.yiyiaddon.e.n.g.a.valueOf(
                              var9.get(
                                    (String)com.yiyiaddon.m.b.a<"sjzw597t5vean","j+lNWr0eKMEXEkgffG99J3zeT1dxPSW1B0zayvjOv3t1EaUh",-6173980158891890461,3016425880723523876,-6674843856958466433,4126624630059422332>()
                                 )
                                 .getAsString()
                           );
                        } catch (Exception var12) {
                           var10 = com.yiyiaddon.e.n.g.a.GROUPS;
                        }

                        S.put(
                           ((String)var7.getKey()).substring(var8 + 1),
                           new b.a(
                              var10,
                              var9.get(
                                    (String)com.yiyiaddon.m.b.a<"s3kse38pitwa2f","r4v6wxriRw5gI08sS9K3C1o+VIHpdT127XEMkDRwB0jp9zz5",6486252219570448828,4643582494878305656,-225132970745664274,6818200968444184157>()
                                 )
                                 .getAsInt()
                           )
                        );
                     }
                  }
               } catch (Exception var13) {
               }
            }
         }
      }
   }

   private static boolean f(String var0, String var1) {
      JsonObject var2 = new JsonObject();
      Iterator var3 = S.entrySet().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"ssdg9lmba14w4","nLf1Kz89Vn9NMKgUM9KcJ18fufmDQLXhklu8aLFFVOk=",-3805265912564645096,8625875171104313185,-1124459762617938854,7565409942031144097>()) {
         case 506029611:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1bd7pt09iczih","8DendUQLPQKi1G1ArXBDLvnN5ia1DuaYBLGNenOPAV4=",-4756816021555671017,-9010644249362516233,8628702676528318869,3196469638926802581>()) {
                  case 543432462:
                     Entry var4 = (Entry)var3.next();
                     JsonObject var5 = new JsonObject();
                     var5.addProperty(
                        (String)com.yiyiaddon.m.b.a<"sjzw597t5vean","j+lNWr0eKMEXEkgffG99J3zeT1dxPSW1B0zayvjOv3t1EaUh",-6173980158891890461,3016425880723523876,-6674843856958466433,4126624630059422332>(),
                        ((b.a)var4.getValue()).a().name()
                     );
                     var5.addProperty(
                        (String)com.yiyiaddon.m.b.a<"s3kse38pitwa2f","r4v6wxriRw5gI08sS9K3C1o+VIHpdT127XEMkDRwB0jp9zz5",6486252219570448828,4643582494878305656,-225132970745664274,6818200968444184157>(),
                        ((b.a)var4.getValue()).bW()
                     );
                     var2.add(aC(var1) + (String)var4.getKey(), var5);
                     switch ((int)com.yiyiaddon.m.b.a<"swvv1f951e8wf","0v7XJg6TPXN1MlgP5aDsJy/HlaNHmYPF8zstiyIqCog=",7409616459842828371,-4551094114241206094,6241325470457679395,-6499287049668749213>()) {
                        case -218971410:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            JsonObject var6 = new JsonObject();
            var6.addProperty(
               (String)com.yiyiaddon.m.b.a<"s3aggkfn4jc388","flJus/h2Z58ImMLiFvG8q868iAurNhRr4Ame3PkNm8pQTQ==",1657076410427026740,-8213903483173965631,3716141277354385845,-695380453489281946>(),
               var0
            );
            var6.addProperty(
               (String)com.yiyiaddon.m.b.a<"s3iegbgb1siiby","BcYr6/D0H3OGR6rfP8SKW9jxEWaF/5LJY9/jSrwOUazdil07",3665594455796373028,2961803529833310996,-4734929392283202219,1911518267888012996>(),
               aC(var1)
            );
            var6.add(
               (String)com.yiyiaddon.m.b.a<"s2lxofkxfodv63","UcFH9YUuWoGAOHnal91eOSnc21IpU6qeumEt0kUdkWmvjzY3",1086000427675319305,-1517937994220292708,-8174220593177160780,-8373218771131788310>(),
               var2
            );
            return com.yiyiaddon.j.a.a(b(var0), var6);
         default:
            throw null;
      }
   }

   private static String aC(String var0) {
      if (var0 != null) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s319ggtmuekgsw","PTP4vUB0QsMfKOVsIUBpAsrCYBzwyMxhxjaEMVatmyQ=",-9112314555534023571,102587708525216691,-2764322869110574692,-1001334151001224543>()) {
            case -106067882:
               if (!var0.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2p8zr2ges00n8","DP2I+KRKPQeP2o18J4vWF2U/T2ALxa/DhCOx1Nwt86I=",-2617533741981357295,3572761986449622716,6062667077787397890,-3958007634706449136>()) {
                     case -1916397465:
                        return var0;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"srmsxsbzg6a5q","ftZiJ9TjLvEOrZTO7C770JGYintWQSwsdp3YBVfSfRM=",-4521097694604107167,6085545968981483437,-2559566045918659572,-6156668762396640443>()) {
                  case 1247137691:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      String var10000 = (String)com.yiyiaddon.m.b.a<"s2g4u5ffb3jr8r","6u4VfdUggKf9VZQQ3+UE6SM3rlvyAZcsrmCu1ItJN3AY4YPXGK/LHbTbLOrqRri8",-5869469613554737728,3143902324394020837,-4302928213999952353,-8800357942662205662>();
      switch ((int)com.yiyiaddon.m.b.a<"s1eg637lv7ho4q","LuL8QhpRIr23011IX2D66GXKeFATDkVV/NIeorxDpaQ=",-5544398059749386848,-2671506705866184781,-867683725254952400,7968256765166238975>()) {
         case 1549561795:
            return var10000;
         default:
            throw null;
      }
   }

   private static Path b(String var0) {
      return g.resolve(
         var0.replaceAll(
               (String)com.yiyiaddon.m.b.a<"s2dtm5z5ro59kb","RylvIRhaDlFp02tjy3qMG13+L/Zd6tMt93yGgfOddVFzPpSuzABcUfp8/Ge8ctWu08twcA==",6112919504013657642,1159445896574977336,-6810450737989385398,3854857658947062934>(),
               (String)com.yiyiaddon.m.b.a<"ssm68mvm850nr","dhp/M67y5y6vXPRGA/H37nfkyKbEg8RlwQ1A3d3N",4667370714704398024,7789782435745475393,3556651175723506827,-3623001010968558153>()
            )
            + ""
      );
   }

   public record a(com.yiyiaddon.e.n.g.a a, int md) {
      public static final b.a a = new b.a(com.yiyiaddon.e.n.g.a.GROUPS, 1);

      public a(com.yiyiaddon.e.n.g.a a, int md) {
         a = a == null ? com.yiyiaddon.e.n.g.a.GROUPS : a;
         md = Math.max(1, Math.min(9999, md));
         this.a = a;
         this.md = md;
      }

      public int bV() {
         long var10000;
         if (this.a == com.yiyiaddon.e.n.g.a.GROUPS) {
            label15:
            switch ((int)com.yiyiaddon.m.b.a<"s2zb9m4om8ujmd","LNx3ZLJ6j0gDB6DFbINfZJZ3phDYzvVNwScdZah6yno=",-7728247902413911371,8902621752968157792,5780442400945583228,-7256251765236100610>()) {
               case 1075699394:
                  var10000 = this.md * 64L;
                  switch ((int)com.yiyiaddon.m.b.a<"sz47krh2j1qk","HbzXM/67s6qKxJ3BvyKbe09FRr/yJ+lxBesYnsDSf5E=",-4974942663962206858,1197054356640440580,-8934751206493915449,7022820013278516996>()) {
                     case -1093351020:
                        break label15;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = this.md;
            switch ((int)com.yiyiaddon.m.b.a<"s1buygc7bd44z1","IEUqePfavRQLIvvKK2iHqo4fV2Ih5bzqPkbbvh2Nqpg=",-6049131908868716117,-4560813502599031881,1076900466361501655,47916615742788996>()) {
               case 1877595404:
                  break;
               default:
                  throw null;
            }
         }

         long var1 = var10000;
         return (int)Math.min(2147483647L, var1);
      }

      public int bW() {
         return this.md;
      }
   }
}
