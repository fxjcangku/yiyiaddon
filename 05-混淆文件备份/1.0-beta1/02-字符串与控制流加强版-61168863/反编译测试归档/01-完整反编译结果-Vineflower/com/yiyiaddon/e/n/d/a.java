package com.yiyiaddon.e.n.d;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yiyiaddon.m.b;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;

public final class a {
   private static final Path e = Minecraft.getInstance()
      .gameDirectory
      .toPath()
      .resolve(
         (String)b.a<"s2riil5g58n2fn","wKEZeAudAa+0YNCL7rXhyjTOc5Bl7sGutqP/j5ewEkwi2A4aq0qCIxWV7jB+rVVkdME=",5291845148503246823,4383503486868527601,-4136420444983358300,-789405959109566440>()
      )
      .resolve(
         (String)b.a<"sdg2f9horwq3d","EbiO9I9g06o7sKed9QP6NDHZ0ixzr6IjNO+V2yj+JbEdkelLxYN6McWY4pgR/g==",6068985246814180440,9089003895928604569,1036632295348422167,9094186971026299398>()
      );
   private static String rF;
   private static long G;
   private static final Map<String, com.yiyiaddon.e.n.d.a.a> R = new LinkedHashMap<>();

   private a() {
   }

   public static com.yiyiaddon.e.n.d.a.a a(String var0, String var1, String var2) {
      if (var2 != null) {
         switch ((int)b.a<"s2ejaaxpccqnzf","v+x2ijXSlHMzBQjGrXChCuPd08BlZ7Slk6MMhiFIzIg=",1401504037823677322,-3124302962336069022,-5995334528214233405,-4649526438699978841>()) {
            case 1790819841:
               if (!var2.isBlank()) {
                  j(var0, var1);
                  return R.getOrDefault(var2, com.yiyiaddon.e.n.d.a.a.a);
               } else {
                  switch ((int)b.a<"s3f6x6pqprk7q4","4jyY9cFh0aYDzRm0XZPGXRxkW/pqA+Enw03i4IwZiOw=",1339256370877075093,-1249553322223013525,484354794424667586,-2362521699262549419>()) {
                     case 1942467475:
                        return com.yiyiaddon.e.n.d.a.a.a;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return com.yiyiaddon.e.n.d.a.a.a;
      }
   }

   public static boolean b(String var0, String var1, String var2) {
      if (var2 != null) {
         switch ((int)b.a<"suts0rryda0sl","R6AeqcVXYJILRpxqk+TAICsDd6/S6Xg4P9AgILoeBto=",-2523331336258781911,-2520755219696176991,9012540688574746150,8407903149718080526>()) {
            case -1734601555:
               if (!var2.isBlank()) {
                  j(var0, var1);
                  return R.containsKey(var2);
               } else {
                  switch ((int)b.a<"scef2eqww0ulm","oz+B4a6JRRoyLoarde9TzMdaPy5xkQ0QVzycwtgwid8=",-7414733169533633327,3561712067462667081,1652526711446818462,4256004244631281839>()) {
                     case -1883890978:
                        return false;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return false;
      }
   }

   public static Map<String, com.yiyiaddon.e.n.d.a.a> a(String var0, String var1) {
      j(var0, var1);
      return new LinkedHashMap<>(R);
   }

   public static boolean a(String var0, String var1, String var2, com.yiyiaddon.e.n.d.a.a var3) {
      if (var0 != null) {
         switch ((int)b.a<"s38yrrue5m1q0j","A9Tr5OhYUU7WrXVDRV+UucOP4jYRIFMRD23IT0kmB5Y=",6682194890558013496,950911148334645475,-2630913784024032051,-4874966048798228664>()) {
            case 2123095752:
               if (!var0.isBlank()) {
                  switch ((int)b.a<"s1h3fug1y9w0qw","yUNhHQb+17FjpYueOnbSCSOgyJTPSSQ2W0fKKyqGQo4=",-8149622230438293817,-1575309587605774918,-5808231001036002213,3951607960671929927>()) {
                     case -1573220833:
                        if (var2 != null) {
                           switch ((int)b.a<"si0qhh5f9i6vo","h2tOWiZ8TUiwW5EcFum9hDFWyFAvmnbe+iquOj/SGH8=",408649501759032134,5548876079775288053,6154312252480917569,-8471688200333408979>()) {
                              case 1467892880:
                                 if (!var2.isBlank()) {
                                    j(var0, var1);
                                    Map var10000 = R;
                                    com.yiyiaddon.e.n.d.a.a var10002;
                                    if (var3 == null) {
                                       label38:
                                       switch ((int)b.a<"s1sbha02xsvvm5","1w/ChRkh2APxnf03YGPEUU1cqumcfhh/hvZqjQF9+u8=",1237919853108825245,-762363653039154549,3734854142242961982,1964643213463216452>()) {
                                          case 583568254:
                                             var10002 = com.yiyiaddon.e.n.d.a.a.a;
                                             switch ((int)b.a<"sba4hj4dux919","Lj8NPibxvBQCviVO/6itM0A1/SbkG8R57i1mQWYiiIE=",2369880571805651574,-245172971112142898,3000605260507656967,-2834960643329609429>()) {
                                                case 797777442:
                                                   break label38;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       var10002 = var3;
                                       switch ((int)b.a<"s35jmued1tptxk","XOA0oxeWe5BrQBZaIZH3qAKITbvAoHAZxSPG2JkbT+c=",452165228085703373,-2861490164761993778,-3064262195900885124,1548047103620841128>()) {
                                          case -826351815:
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }

                                    var10000.put(var2, var10002);
                                    boolean var4 = f(var0, var1);
                                    if (var4) {
                                       switch ((int)b.a<"s3pbfofo7w2424","UF++pEUx0Yo5gU5UzLdUblaELN8IQmk/UjQSC2Sq22I=",7324119171292673517,7928495768707709971,6219180180618983221,-4251130963078104767>()) {
                                          case 1439708241:
                                             G++;
                                             switch ((int)b.a<"s36dblvr2pnxt1","ik+4E1uukmW/tVf4QWCAw290/LoNJNORipdftvQ/G9E=",8591972722879300961,1733738306386909288,8077821544695966038,9020862616005338185>()) {
                                                case 1045340590:
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

                                 switch ((int)b.a<"s3myhox2wsw8a9","5CA+iMoO5Dzanb0bHBvPQW1YfELs3KEed0VNLZgJIYA=",-2729374358055594999,2260786131766603869,-1029793394673168714,7681352747983829746>()) {
                                    case -113342649:
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
      R.clear();
      G++;
   }

   public static long o() {
      return G;
   }

   private static void j(String var0, String var1) {
      String var2 = e(var0, var1);
      if (!Objects.equals(var2, rF)) {
         rF = var2;
         R.clear();
         if (var0 != null && !var0.isBlank()) {
            Path var3 = b(var0);
            if (Files.isRegularFile(var3)) {
               try {
                  JsonElement var4 = JsonParser.parseString(Files.readString(var3, StandardCharsets.UTF_8));
                  if (!var4.isJsonObject()) {
                     return;
                  }

                  JsonElement var5 = var4.getAsJsonObject()
                     .get(
                        (String)b.a<"s2whjyeluof78x","DXJMEy0uFkhNJjWN+de1xAo8R+Sqz4dDWN1ksLHoRRlPk75t",-1418735221921194981,-7696141967124711494,8079897367655308575,6929494834855376941>()
                     );
                  if (var5 == null || !var5.isJsonObject()) {
                     return;
                  }

                  for (Entry var7 : var5.getAsJsonObject().entrySet()) {
                     if (((JsonElement)var7.getValue()).isJsonObject()) {
                        String var8 = (String)var7.getKey();
                        int var9 = var8.lastIndexOf(64);
                        if (var9 > 0 && var9 != var8.length() - 1) {
                           String var10 = var8.substring(0, var9);
                           if (Objects.equals(var10, aC(var1))) {
                              JsonObject var11 = ((JsonElement)var7.getValue()).getAsJsonObject();
                              R.put(
                                 var8.substring(var9 + 1),
                                 new com.yiyiaddon.e.n.d.a.a(
                                    c(
                                       var11,
                                       (String)b.a<"s1h7zf2h6s4wu1","2n5ju16volFhbbXUhYppqMxzcBPzJsiOmCjtzWIvXzoYlVA1iJWfTg==",8929930243694233704,-8755582459788875286,3699196699597127226,2476043993674970487>(),
                                       com.yiyiaddon.e.n.d.a.a.a.bR()
                                    ),
                                    c(
                                       var11,
                                       (String)b.a<"s8eyjw78bt272","mnPrQdVoCjmjUg97whbJ7TXccE9XH/CFmABfcsTPQ61oJJDrb+HbPw==",-3504105434955523791,-3372769304635500134,-6424030405535502768,-1142289168328453080>(),
                                       com.yiyiaddon.e.n.d.a.a.a.bS()
                                    ),
                                    c(
                                       var11,
                                       (String)b.a<"s1wqqipp34w1tj","3yv2S/bVYAg/EGa52bQo7c2CSf8Ex+0Oshxffr3uNYoycn+4NK/JoQ==",8831854484490602095,-8730840614001270565,5500852332994220042,-257808532177181248>(),
                                       com.yiyiaddon.e.n.d.a.a.a.bT()
                                    ),
                                    c(
                                       var11,
                                       (String)b.a<"s10bk0io9mfszw","yn1hr4yxr5m++f2XFUrPigXu84znhRPor01Czzq/Ipojc3qUSobWYA==",-5213171749174366669,4434564674129123185,4312534822021252783,258192778195423694>(),
                                       com.yiyiaddon.e.n.d.a.a.a.bU()
                                    )
                                 )
                              );
                           }
                        }
                     }
                  }
               } catch (Exception var12) {
               }
            }
         }
      }
   }

   private static boolean f(String var0, String var1) {
      JsonObject var2 = new JsonObject();
      Iterator var3 = R.entrySet().iterator();
      switch ((int)b.a<"s21d43tc5442ew","FbbIdrTuca0QSy2Zpw+Io/d1hQqqwsLtm5XQWfz4wJI=",-5123477012819010769,6799990679458569335,7514303451699983812,8952767727520096378>()) {
         case -409524207:
            while (var3.hasNext()) {
               switch ((int)b.a<"s2qyoi2ttlb0e6","c6NqUFKrDiQ+GwXqSiMKIgtk/PjKHDDtY83+D4ylnw4=",7595860688651307413,6341043265412583875,-8164753606492060072,-8630552528856359921>()) {
                  case 1029096775:
                     Entry var4 = (Entry)var3.next();
                     JsonObject var5 = new JsonObject();
                     com.yiyiaddon.e.n.d.a.a var6 = (com.yiyiaddon.e.n.d.a.a)var4.getValue();
                     var5.addProperty(
                        (String)b.a<"s1h7zf2h6s4wu1","2n5ju16volFhbbXUhYppqMxzcBPzJsiOmCjtzWIvXzoYlVA1iJWfTg==",8929930243694233704,-8755582459788875286,3699196699597127226,2476043993674970487>(),
                        var6.bR()
                     );
                     var5.addProperty(
                        (String)b.a<"s8eyjw78bt272","mnPrQdVoCjmjUg97whbJ7TXccE9XH/CFmABfcsTPQ61oJJDrb+HbPw==",-3504105434955523791,-3372769304635500134,-6424030405535502768,-1142289168328453080>(),
                        var6.bS()
                     );
                     var5.addProperty(
                        (String)b.a<"s1wqqipp34w1tj","3yv2S/bVYAg/EGa52bQo7c2CSf8Ex+0Oshxffr3uNYoycn+4NK/JoQ==",8831854484490602095,-8730840614001270565,5500852332994220042,-257808532177181248>(),
                        var6.bT()
                     );
                     var5.addProperty(
                        (String)b.a<"s10bk0io9mfszw","yn1hr4yxr5m++f2XFUrPigXu84znhRPor01Czzq/Ipojc3qUSobWYA==",-5213171749174366669,4434564674129123185,4312534822021252783,258192778195423694>(),
                        var6.bU()
                     );
                     var2.add(aC(var1) + (String)var4.getKey(), var5);
                     switch ((int)b.a<"s77uam8pyb5es","MrUryrJdr3H1UpRldH/cPQJh7fUKzCRcBmks4K7xcpk=",-5026437607908587926,4152817578270619831,4768647890996991564,4771617617046592524>()) {
                        case 813354263:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            JsonObject var7 = new JsonObject();
            var7.addProperty(
               (String)b.a<"seixfsxq9pc2g","jJOavvF5zTdgdOiVpovq24DKQ1tjrXPZs7Zvzx1l35QygA==",5617098104769553149,8148404059913115124,-4183839303977391227,-3348621894226886930>(),
               var0
            );
            var7.addProperty(
               (String)b.a<"s3pkrgmeirr6ye","fW7d92tndeC1RYV3xEJpv5NsWvs5l2kGkXrcDYX2u7ZYMF5J",49480124965296466,-6668134457414338935,124666389417338391,-2375559021790577634>(),
               aC(var1)
            );
            var7.add(
               (String)b.a<"s2whjyeluof78x","DXJMEy0uFkhNJjWN+de1xAo8R+Sqz4dDWN1ksLHoRRlPk75t",-1418735221921194981,-7696141967124711494,8079897367655308575,6929494834855376941>(),
               var2
            );
            return com.yiyiaddon.j.a.a(b(var0), var7);
         default:
            throw null;
      }
   }

   private static int c(JsonObject var0, String var1, int var2) {
      try {
         JsonElement var3 = var0.get(var1);
         return var3 != null && var3.isJsonPrimitive() ? var3.getAsInt() : var2;
      } catch (Exception var4) {
         return var2;
      }
   }

   private static String aC(String var0) {
      if (var0 != null) {
         label22:
         switch ((int)b.a<"s1geqlz5obja8t","MY+fls7NlzMBFuukmtGS4L01AXLYav/w8S9iRki6Vxs=",-6437872217571902979,8927063245480744691,-634546587787137354,-3500163895434924449>()) {
            case -1863750319:
               if (!var0.isBlank()) {
                  switch ((int)b.a<"ssg1dh6pew1dh","d4ke+WNTMobM7yz/ibMthOFjsgaJxdcTvHatJiOpW0I=",-9072083487700756767,-1514025786961708896,1109710541658655208,-6352303830815121247>()) {
                     case -216752920:
                        return var0;
                     default:
                        throw null;
                  }
               }

               switch ((int)b.a<"s1zbimmv0iljee","sJ4HbDQ4AfdDrr6D3oWu6v+IkrxzOTbenYnAdWWI7bQ=",-1111303011618168292,5621252903835595305,3600982524085087621,-1403713870914403547>()) {
                  case 1690427541:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      String var10000 = (String)b.a<"s39ahyehvns2pa","kPHlakMA8BBnH18xVvj+2r+fji9sBz2pUUkCKZGCD/VuwQ+To1Q1LPV1EIOZbCiP",298465083103352017,2411547216935659220,4698313711911676724,-5378897566665401032>();
      switch ((int)b.a<"s1ssxilpfxev6s","XeldkLWPDmNv7CDItplwFwBZq4CiDhjtKCyaloSKllQ=",-3301772972660975230,9077158855640803977,-1784467908098084010,-4791744836899173205>()) {
         case -1853212132:
            return var10000;
         default:
            throw null;
      }
   }

   private static String e(String var0, String var1) {
      if (var0 == null) {
         switch ((int)b.a<"s29i299ar4a4rg","h34L0LA3GJMXR994/ZV1/AzTtwTqma+sHEbUx0q0ujA=",2738652879204643403,6796178910721041567,-2787327924290202034,8760283964067606009>()) {
            case 756065445:
               String var10000 = (String)b.a<"sv3fy0qoc6wbp","gs8CtvlbVxDwg+CZ1JWwf5nGNig3/nOAQWq0tA==",-5944242697164338104,3497505241428968111,8994055617735712949,6158461723147492800>();
               switch ((int)b.a<"sohqsgawhvx8","RTIp2/CLqwy1iYpPG+leKAOTb3gLPDyZ6Ry7Pmf4MaE=",7181813279869910706,5921854750205069607,6134357673527519520,-1188810562234794153>()) {
                  case -337005306:
                     return var10000 + aC(var1);
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)b.a<"s16zqj7nimo9ju","Q3N51e27mjUKMkEc3R1oY2QV3D8uH5voG6/rN4HcdTc=",-5473948149881508105,-6541289089267627448,9091224752438186294,8717381077583351208>()) {
            case -1463394937:
               return var0 + aC(var1);
            default:
               throw null;
         }
      }
   }

   private static Path b(String var0) {
      return e.resolve(
         var0.replaceAll(
               (String)b.a<"s1qh97ti8sz7mc","w6qZru/8/VqlTESyh2bJ05YWVzdcbWjQC9wSnN4IBkqpk0eVurKT7J0/Q9tx7k8V3Zm2VA==",8275396104799809350,-4190662655533424089,8557730476244752844,8442675220230621907>(),
               (String)b.a<"s1a7ywkufctjt8","44BaJNiH3WByz1VS/hEZyH2dDEeRtx+6wQ2OTJVY",-114462021635012517,-5471590000370323047,3217022422707198382,-6936305333743651499>()
            )
            + ""
      );
   }

   public record a(int lV, int lW, int lX, int lY) {
      public static final com.yiyiaddon.e.n.d.a.a a = new com.yiyiaddon.e.n.d.a.a(2, 8, 8, 0);

      public a(int lV, int lW, int lX, int lY) {
         lV = a(lV, 0, 64);
         lW = a(lW, 1, 64);
         lX = a(lX, 1, 64);
         lY = a(lY, 0, 64);
         this.lV = lV;
         this.lW = lW;
         this.lX = lX;
         this.lY = lY;
      }

      private static int a(int var0, int var1, int var2) {
         return Math.max(var1, Math.min(var2, var0));
      }

      public int bR() {
         return this.lV;
      }

      public int bS() {
         return this.lW;
      }

      public int bT() {
         return this.lX;
      }

      public int bU() {
         return this.lY;
      }
   }
}
