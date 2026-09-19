package com.yiyiaddon.j.a;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yiyiaddon.i.g.c;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

public final class b {
   private static final Path p = FabricLoader.getInstance()
      .getConfigDir()
      .resolve(
         (String)com.yiyiaddon.m.b.a<"s3si4ct1ffy8ye","G71xFrYF4qWBHFILqdIotTfupckGd4R1T3v1BdxfcUvnowm/nmfqWUUM7dhpjA==",2251964212062537243,4091892591012679566,4485478683353385303,5463308552321360054>()
      )
      .resolve(
         (String)com.yiyiaddon.m.b.a<"s1azqsqv6l2qrw","7H7zKbJHd0qjv7SlgKlgG474vb2i1EktWBsZEsQyyemNcL4Jf7ige4PHqRUz+A==",-6518287964020443826,3833235511388182677,2469954993314573455,-6218530439647507726>()
      );
   private static final Gson f = new GsonBuilder().setPrettyPrinting().create();
   private final Minecraft ay;
   private final List<com.yiyiaddon.g.a.b> cO = new ArrayList<>();

   public b(Minecraft var1) {
      this.ay = var1;
   }

   private String gp() {
      return c.fG();
   }

   private Path b() {
      return p.resolve(c.gn() + "");
   }

   public void C() {
      this.cO.clear();
      Path var1 = this.b();
      this.g(var1);
      if (Files.exists(var1)) {
         try {
            this.bj(Files.readString(var1));
         } catch (Exception var3) {
         }
      }
   }

   private void g(Path var1) {
      if (!c.fG()
            .startsWith(
               (String)com.yiyiaddon.m.b.a<"s36cnw2olq4orp","vP736lOrp2DyklFClJOhgy/CcdQ+TwWHtXsVDbSR2guRPEZsCkKghvihdsi7XcCB5k4xako9",3238024784260885035,4643500247743379695,-5031834362542517234,-2303767730144425118>()
            )
         && !Files.exists(var1)) {
         String var2 = c.go();
         if (var2 != null) {
            Path var3 = p.resolve(var2 + "");
            if (Files.isRegularFile(var3) && !var3.equals(var1)) {
               try {
                  Files.createDirectories(var1.getParent());
                  Files.copy(var3, var1);
               } catch (Exception var5) {
               }
            }
         }
      }
   }

   public boolean a(BlockPos var1, String var2, String var3, long var4) {
      com.yiyiaddon.g.a.b var6 = this.a(var1, var2);
      if (var6 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sk29lfhzj7q9z","ZOdqNTmcavmHNiZh3/aPW+wUgY60M2UT368y42A5Nws=",6871639074944009134,-1164023776383021845,-7358594377769605864,-5360587894106558550>()) {
            case -1708605913:
               return false;
            default:
               throw null;
         }
      } else if (var6.a(var4)) {
         switch ((int)com.yiyiaddon.m.b.a<"shn758pa3uxw9","U+feOpqDMFziC4DPvk5fMFiFhFT0Nbk+hZnEL2djnC8=",-8651710171941992363,-1688421095684205488,724167627691600506,-144160085217125177>()) {
            case 1983113598:
               this.c(var1, var2);
               return false;
            default:
               throw null;
         }
      } else if (!var6.aG(var3)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2l4sdkfersa4g","Wx+GZpxRkZmqQWBgSoaFMoQTCHDndi70S+gkKs+d6UU=",-2589987575094258378,2595072239167067411,-2158706100866558891,2677510492048784112>()) {
            case -2145555881:
               this.c(var1, var2);
               return false;
            default:
               throw null;
         }
      } else {
         return true;
      }
   }

   public boolean a(BlockPos var1, String var2, long var3) {
      com.yiyiaddon.g.a.b var5 = this.a(var1, var2);
      if (var5 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3sdp14qousesz","N+E6JAbxJWXVr13f7XTJvm0bfN0reua3jzo6VQNzgcU=",-5308130727256276197,-8484773472192087640,6979380616357760513,3237045013190525472>()) {
            case 60509124:
               return false;
            default:
               throw null;
         }
      } else if (var5.a(var3)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2d3erxn5ug2uo","wN+na847Ly7v4rj+g+8aXb0X4mJm1uHs3CBGZVi/Rxg=",-3039949692204367060,-5496788660191032172,-6027183140964939426,-2339566524314419821>()) {
            case -681256352:
               this.c(var1, var2);
               return false;
            default:
               throw null;
         }
      } else {
         return true;
      }
   }

   public void a(BlockPos var1, String var2, String var3) {
      this.c(var1, var2);
      this.cO.add(new com.yiyiaddon.g.a.b(this.gp(), var1, var2, var3));
      this.e();
   }

   public com.yiyiaddon.g.a.b a(BlockPos var1, String var2) {
      Iterator var3 = this.cO.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s35ew80dp6ptfj","fWM0o1juLn2QftSmdBmmfz/E4kkQCbsPnfWz/17lJ48=",2945659826616821534,-4446396499571870672,4938467507103388531,-377569931835429901>()) {
         case 860169219:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2wtomy99zywcd","L6doJ5fHlUeCXn6OCPJmerGki2/6MFZn9Od0hX/UsuY=",-2693177556643428394,4435490101442351520,45947240360499144,-2652488637932613721>()) {
                  case -2055787424:
                     com.yiyiaddon.g.a.b var4 = (com.yiyiaddon.g.a.b)var3.next();
                     if (var4.a().equals(var1)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1yz6vqk9e5ek8","EERsWRXPQOJslQAuEiZFYTpIBesXJ8E285w0qqYsqq4=",-724879534714767470,-3186466108555192242,-946931836779968469,-7526798996367455036>()) {
                           case -1019877463:
                              if (var4.bU().equals(var2)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1lqhs8g9a3vnj","JZaROZTik7RSsMBAlLqVleeLyIfAekppJLhZBmg3U5E=",1394900759907775831,27957482627623226,-3772671054093491259,2677833716534336592>()) {
                                    case -423016716:
                                       return var4;
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s2bpjuot73jvck","ICmk/JOL9cS0agbFIp6SwvhMuqhkDzuAa8wpL+AiOWU=",-7838570928446542399,-5966787833692268829,3381246538162323295,-1062190762077063399>()) {
                        case -452730725:
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

   public boolean d(BlockPos var1, String var2) {
      return this.c(var1, var2);
   }

   private boolean c(BlockPos var1, String var2) {
      boolean var3 = this.cO
         .removeIf(
            var2x -> {
               if (var2x.a().equals(var1)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3a6nm8ds15zrf","lWRX0vseObTvMYgyclkIDLavRVjUI+tdFdUfop4U9M0=",7848129955605056519,-1904564082388029911,-3906514934787883922,-5274427531368119573>()) {
                     case 1286355696:
                        if (var2x.bU().equals(var2)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s15ci33pui6l6a","guoOCXymhqskIwnqmoHiNHia3GYd73MoK/d0SSygnL4=",7802843792201564481,-6772860107616325442,2546107413098797936,984546090761874325>()) {
                              case -1952740094:
                                 switch ((int)com.yiyiaddon.m.b.a<"s3ao5wc5penpwh","9fv7d/AwLXfo4cQI2EbBkg4o0+vGJKFZz8VGmZvtmj8=",3851841826897165471,7291794525871688096,-282934774914414242,-2236056587263473303>()) {
                                    case 217389580:
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

               switch ((int)com.yiyiaddon.m.b.a<"sw2krvwvhqa9p","/fh0BJpS+Y5xLLY9SgaLMDdfx5oLEZg4kkd5VzUYbm0=",-1084127808049997788,6864743143932201545,206257358428532278,-4058331597287715053>()) {
                  case 1279570063:
                     return false;
                  default:
                     throw null;
               }
            }
         );
      if (var3) {
         switch ((int)com.yiyiaddon.m.b.a<"sys8msb6dqs06","w/wZYckmHw4t6ZBjfPqkWC/ne2nzMyhkNA+r7HWPLq8=",2370279677888349972,-2902571984275211287,3950497998585544788,-6401117878085773062>()) {
            case -1629449170:
               this.e();
               switch ((int)com.yiyiaddon.m.b.a<"s2vcfdl2oghh0t","STXc+4QloYHtv+BWpF7anVoaYUfGOc2MaQIsKzOC9SQ=",6157937379281354059,-4857622073126343350,-4053535979667689477,3255008747161534790>()) {
                  case -1469827729:
                     return var3;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return var3;
      }
   }

   public void b() {
      this.cO.clear();
      this.e();
   }

   public int s(String var1) {
      int var2 = this.cO.size();
      this.cO.removeIf(var1x -> var1x.bU().equals(var1));
      var2 -= this.cO.size();
      if (var2 > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s2h0zhzdi65245","9aGOnI+lIlKYX6MVCEPB23OoJPlMd1Lv7L+7D4rj9/w=",7844465322723475635,7781072006035507519,887753217547687333,-4743200968576767954>()) {
            case -516546402:
               this.e();
               switch ((int)com.yiyiaddon.m.b.a<"s3uk5l9h8zm9b7","uYLPk39YyKsNOBY6PZQbtDa6ytf30Z/PQ8NEdJUlVng=",8950814497757348934,7060125012222783944,-7552877171769779516,1950859643636964633>()) {
                  case 267177233:
                     return var2;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return var2;
      }
   }

   public int dD() {
      int var1 = 0;

      try {
         if (Files.isDirectory(p)) {
            try (Stream var2 = Files.list(p)) {
               for (Path var4 : var2.toList()) {
                  if (var4.getFileName()
                     .toString()
                     .endsWith(
                        (String)com.yiyiaddon.m.b.a<"s2ie1cqvl2g97c","wg8g/kZXgzWjQ08N/gNlKRyRVXYKHsla5M6vl4xa5ocBYeMRkHo=",2026019269227318037,-4936484664825920330,6934223085559387096,-3170606878003074869>()
                     )) {
                     Files.deleteIfExists(var4);
                     var1++;
                  }
               }
            }
         }
      } catch (Exception var7) {
      }

      this.cO.clear();
      return var1;
   }

   public int a() {
      return this.cO.size();
   }

   private void e() {
      try {
         Path var1 = this.b();
         Files.createDirectories(var1.getParent());
         JsonArray var2 = new JsonArray();

         for (com.yiyiaddon.g.a.b var4 : this.cO) {
            JsonObject var5 = new JsonObject();
            var5.addProperty(
               (String)com.yiyiaddon.m.b.a<"suw6iyo50ubb3","FpMpx5zypmtwJuw4Wix4+69z3oxKbo9l95C+Uhn9",-6666449126084554256,-102102750104197930,-7066171554375548300,-7995468942347538209>(),
               var4.a().getX()
            );
            var5.addProperty(
               (String)com.yiyiaddon.m.b.a<"s1l1s714fu429r","RBeLvSjiN+i1wKyAzBvVEl0F4uJhgBIGYquqO0ak",-4606784977013403316,2705713758640654192,874291831675316444,6426937504546304265>(),
               var4.a().getY()
            );
            var5.addProperty(
               (String)com.yiyiaddon.m.b.a<"sl5gv1syg65hi","t1bLGVa8seJkkaiUGXbsMFcQN1bJAktvZJ0/mz8E",5628847262715984627,2924934299656261374,-2789389524456633048,2588410087476408936>(),
               var4.a().getZ()
            );
            var5.addProperty(
               (String)com.yiyiaddon.m.b.a<"s26irzyhgx70hs","j7Mp9liJ1n+qePiLFS8hfMA7glR3Zs/EEVDgzUJokmGxsg==",1670850250791179346,2873437880506335853,2880010302039668126,3305950544887905231>(),
               var4.bU() == null
                  ? (String)com.yiyiaddon.m.b.a<"sgyzsa41vxt6j","BVTrH+cDjfXM/EmBQ9YBESeC2nV/TXh6r1/1AQ==",8431793738136865926,-5734226998154433075,7339193828974803133,7997691942036830775>()
                  : var4.bU()
            );
            var5.addProperty(
               (String)com.yiyiaddon.m.b.a<"s28jksbo74f7n","5QxEmCCoAEUZGiIX0bA92mtcdFbthDdbhHCaqafvqHiv0jho",-7827769271538898455,4058408577611191595,7603707819449031208,9145455381535499430>(),
               var4.fH() == null
                  ? (String)com.yiyiaddon.m.b.a<"sgyzsa41vxt6j","BVTrH+cDjfXM/EmBQ9YBESeC2nV/TXh6r1/1AQ==",8431793738136865926,-5734226998154433075,7339193828974803133,7997691942036830775>()
                  : var4.fH()
            );
            var5.addProperty(
               (String)com.yiyiaddon.m.b.a<"s2bpwgvaqwj822","MHMAKqhbSzCZOkOlEyHJByeyqfDvftyt1b46Z98H",-7207169431201171611,8349093149814726194,-6385669335111341855,-7025176181362713686>(),
               var4.w()
            );
            var5.addProperty(
               (String)com.yiyiaddon.m.b.a<"s2ugg3gf00p09q","nIs3GROlieMNQF4bcvI9SIx1gJG8ca3vyiU8PLm7OYKjIg==",4515672417290219535,3786688925012984712,3500618960452213190,8638009641278552863>(),
               var4.aA()
            );
            var2.add(var5);
         }

         Files.writeString(var1, f.toJson(var2));
      } catch (Exception var6) {
      }
   }

   private void bj(String var1) {
      String var2 = this.gp();
      JsonElement var3 = JsonParser.parseString(var1);
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1fx1rx4t0v64y","D1dUDHjgnw7+5adFI6/o3JF6QjTND5vCdS8BrA7KDrk=",6317014964792118053,-7537728739434822346,-4970513609753441439,-6471156505084538953>()) {
            case 1627799255:
               if (var3.isJsonArray()) {
                  Iterator var4 = var3.getAsJsonArray().iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s3ceimvpd5mn4s","C/ftSMrhQ8dj9LLneesKD67B+kxk2UCvM+jBpRQdPHk=",5775111325025725671,-5911578711795844803,-4047282408127543945,4288073988793926416>()) {
                     case -1452778526:
                        while (var4.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1s602ch6rx55l","4YhHIVHYVcA+dVYiUdsYmBQtNo3I3gvjo53BtUOX+tA=",-8553132460930331615,7190836147853554602,1935627216153743695,-1571516204111965257>()) {
                              case 1655913468:
                                 JsonElement var5 = (JsonElement)var4.next();
                                 if (!var5.isJsonObject()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s15ms0adfyz6kj","li+o9TA1hrUN8io61hbwTI0MxfZ6biFOl8AZuSq9H4U=",-6131733124608131816,6198022174147032321,9790142306921077,513795428937757957>()) {
                                       case 1623173268:
                                          switch ((int)com.yiyiaddon.m.b.a<"s6xwab87eo4dx","uVSImrxK51HHGAT2AIVEQnNc9fNV+gfYbrmNodfKRik=",-3702698004145243152,3126421020390793576,-4314970866623949843,7303209009877678311>()) {
                                             case -388565232:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    JsonObject var6 = var5.getAsJsonObject();
                                    int var7 = a(
                                       var6,
                                       (String)com.yiyiaddon.m.b.a<"suw6iyo50ubb3","FpMpx5zypmtwJuw4Wix4+69z3oxKbo9l95C+Uhn9",-6666449126084554256,-102102750104197930,-7066171554375548300,-7995468942347538209>()
                                    );
                                    int var8 = a(
                                       var6,
                                       (String)com.yiyiaddon.m.b.a<"s1l1s714fu429r","RBeLvSjiN+i1wKyAzBvVEl0F4uJhgBIGYquqO0ak",-4606784977013403316,2705713758640654192,874291831675316444,6426937504546304265>()
                                    );
                                    int var9 = a(
                                       var6,
                                       (String)com.yiyiaddon.m.b.a<"sl5gv1syg65hi","t1bLGVa8seJkkaiUGXbsMFcQN1bJAktvZJ0/mz8E",5628847262715984627,2924934299656261374,-2789389524456633048,2588410087476408936>()
                                    );
                                    String var10 = a(
                                       var6,
                                       (String)com.yiyiaddon.m.b.a<"s26irzyhgx70hs","j7Mp9liJ1n+qePiLFS8hfMA7glR3Zs/EEVDgzUJokmGxsg==",1670850250791179346,2873437880506335853,2880010302039668126,3305950544887905231>()
                                    );
                                    String var11 = a(
                                       var6,
                                       (String)com.yiyiaddon.m.b.a<"s28jksbo74f7n","5QxEmCCoAEUZGiIX0bA92mtcdFbthDdbhHCaqafvqHiv0jho",-7827769271538898455,4058408577611191595,7603707819449031208,9145455381535499430>()
                                    );
                                    long var12 = a(
                                       var6,
                                       (String)com.yiyiaddon.m.b.a<"s2bpwgvaqwj822","MHMAKqhbSzCZOkOlEyHJByeyqfDvftyt1b46Z98H",-7207169431201171611,8349093149814726194,-6385669335111341855,-7025176181362713686>()
                                    );
                                    int var14 = a(
                                       var6,
                                       (String)com.yiyiaddon.m.b.a<"s2ugg3gf00p09q","nIs3GROlieMNQF4bcvI9SIx1gJG8ca3vyiU8PLm7OYKjIg==",4515672417290219535,3786688925012984712,3500618960452213190,8638009641278552863>()
                                    );
                                    this.cO
                                       .add(
                                          new com.yiyiaddon.g.a.b(
                                             var2, new BlockPos(var7, var8, var9), var10, var11, com.yiyiaddon.g.a.b.a.PROCESSED, var12, var14
                                          )
                                       );
                                    switch ((int)com.yiyiaddon.m.b.a<"s260pvmq5lm0f7","iGTPNeVyajZ0CXorq0/mqzNIkiSb4euKy1TmAIjHmn4=",224114623448325188,3217880135430837186,-1670664886771434252,2370882966739263240>()) {
                                       case 1234217074:
                                          continue;
                                       default:
                                          throw null;
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
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1qotkfibxqrzi","2xuN3tdRq7FSZpBzMDwrO91qtnyH9jRhTTZ7IkwBdb8=",6251548460216691508,-6247133303845630858,6500980684198191191,6595143544364333083>()) {
                     case -1798766609:
                        return;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }
   }

   private static int a(JsonObject var0, String var1) {
      JsonElement var2 = var0.get(var1);
      if (var2 != null && !var2.isJsonNull()) {
         try {
            return var2.getAsInt();
         } catch (Exception var4) {
            return 0;
         }
      } else {
         return 0;
      }
   }

   private static long a(JsonObject var0, String var1) {
      JsonElement var2 = var0.get(var1);
      if (var2 != null && !var2.isJsonNull()) {
         try {
            return var2.getAsLong();
         } catch (Exception var4) {
            return 0L;
         }
      } else {
         return 0L;
      }
   }

   private static String a(JsonObject var0, String var1) {
      JsonElement var2 = var0.get(var1);
      if (var2 != null && !var2.isJsonNull()) {
         try {
            return var2.getAsString();
         } catch (Exception var4) {
            return (String)com.yiyiaddon.m.b.a<"sgyzsa41vxt6j","BVTrH+cDjfXM/EmBQ9YBESeC2nV/TXh6r1/1AQ==",8431793738136865926,-5734226998154433075,7339193828974803133,7997691942036830775>();
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"sgyzsa41vxt6j","BVTrH+cDjfXM/EmBQ9YBESeC2nV/TXh6r1/1AQ==",8431793738136865926,-5734226998154433075,7339193828974803133,7997691942036830775>();
      }
   }
}
