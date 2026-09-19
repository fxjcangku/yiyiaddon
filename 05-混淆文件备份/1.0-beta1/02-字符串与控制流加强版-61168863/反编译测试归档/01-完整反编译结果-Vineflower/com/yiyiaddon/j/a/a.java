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
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;

public final class a {
   private static final Path o = FabricLoader.getInstance()
      .getConfigDir()
      .resolve(
         (String)com.yiyiaddon.m.b.a<"s3gckmlwae6vx3","gED/5DyEVeLRISw3xKp6pGscj6v0IopKkyOE/wJMeEythv6aHZsbWPopOGxQYg==",8156985290315706639,-8994149443646861017,8666278361029801171,-4457874213993105458>()
      )
      .resolve(
         (String)com.yiyiaddon.m.b.a<"s1hedg12xiulsh","8qT6zW5EFOD92S1zi1lf3/lcKjAFoBlK+flkUmge6zyeH24oyBzRs5apioQ6lg==",7917826149817036264,5559723798854031624,-8454221518010086090,-5491273966521227972>()
      )
      .resolve(
         (String)com.yiyiaddon.m.b.a<"s2piueslczj9fz","Vfr2g7r6oo6ukMjuYv5drr2UnwX0r66AIJDKGiK1WxmM+/Te5imZeQ==",771641409737878400,-233000555218060836,6040421285178399307,-92830456644994493>()
      );
   private static final Gson e = new GsonBuilder().setPrettyPrinting().create();
   private final Minecraft ax;
   private final List<com.yiyiaddon.g.a.a> cN = new ArrayList<>();

   public a(Minecraft var1) {
      this.ax = var1;
   }

   private String gp() {
      return c.fG();
   }

   private Path b() {
      return o.resolve(c.gn() + "");
   }

   public void C() {
      this.cN.clear();
      Path var1 = this.b();
      this.c(var1);
      if (Files.exists(var1)) {
         try {
            this.bj(Files.readString(var1));
         } catch (Exception var3) {
         }
      }
   }

   private void c(Path var1) {
      if (!Files.exists(var1)) {
         String var2 = c.go();
         if (var2 != null) {
            Path var3 = o.resolve(var2 + "");
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

   public boolean a(BlockPos var1, String var2, String var3) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2utp9y8y0r720","eeqlT8+7hlCF3uXiW4FtOLYetQoA/j75CX1Ft7HR75k=",7739283045763225871,3897704475768712367,-3262031229130381285,1531745301747910810>()) {
            case 314114514:
               if (var2 != null) {
                  Iterator var4 = this.cN.iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s32ubjdtb39ucm","9Q84hoxREL4Xd5pwL78fVTFXPHv0Ak15cVKMbrhb6og=",-7189452423589922322,4711035997510845037,-5997723037860821512,1017236988456315235>()) {
                     case 345638584:
                        while (var4.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3q9oeuipe4fal","JwRFvVzMvrwiRSmACngvi8Y5UcI8znKggXys1UlKGm4=",7185846784912674372,2681695598429096594,8793183779167758615,-3665269185984525397>()) {
                              case -236136571:
                                 com.yiyiaddon.g.a.a var5 = (com.yiyiaddon.g.a.a)var4.next();
                                 if (var5.a().equals(var1)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1ffg0pzd4u9zp","OruYDRYVoath5P4vMRNgcUi2o0VvhTG3S3KsnkRS7mo=",-2394198801807508890,5413513332858012077,9176074667777986801,-3779313228118840013>()) {
                                       case -1201723694:
                                          if (var5.bU().equals(var2)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s37e9nn7my54do","tXt+satlpfYLkjB6rGds6Xx2kwz9E03keoXBpJXHx5A=",7866121065387311967,-6260629741729596811,1200938070204097187,-2269665308328911628>()) {
                                                case 962065668:
                                                   return false;
                                                default:
                                                   throw null;
                                             }
                                          }
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"skpeti8w38ha3","PwguPuqovT/suogya/bRUKR3g5cDpeAWv7WTVt/cdkM=",-2675072874570891905,226274735207542321,4460013141550751623,-6390346562812206978>()) {
                                    case 856648035:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        this.cN.add(new com.yiyiaddon.g.a.a(var1, var2, this.gp(), var3));
                        this.e();
                        return true;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s4m5hoy0h9ktg","RW6ZkO8qZgysi76tsKpmgAuHV+8Us0sGCpfwES67tOA=",1033957308298703483,-4339803842564081357,-5871758159489394159,-8834305706671263070>()) {
                     case 874257551:
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

   public boolean c(BlockPos var1, String var2) {
      boolean var3 = this.cN
         .removeIf(
            var2x -> {
               if (var2x.a().equals(var1)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s37u2jfb3nfdpt","FLO0GrwZxxqV1NIPOJSpTkhWU1Sbyf/u6EMVrJx51zk=",-2665772431484768786,-7518095131685833015,3141754718823905570,76111663557087050>()) {
                     case 583132821:
                        if (var2x.bU().equals(var2)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s37cxp33740yrc","3JzH2LGkAqkcc8K6A+o1WheYDq8DPWLlymY5jmEWgwI=",8545501197274963643,8325225407416428547,-9134485095444426469,-5967787188003795815>()) {
                              case -1927909101:
                                 switch ((int)com.yiyiaddon.m.b.a<"s15q6b2zhi9xjl","W7R0afPnnnwMcdonjlXtT/Sm2A9NnJ5dd7YcGAwu2S4=",-6649027190966519433,-8443176005685799106,-6431365676766956544,-8992214972181538301>()) {
                                    case -994589092:
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

               switch ((int)com.yiyiaddon.m.b.a<"s124lakmcwzrta","fHMGRxnwqesr5YqOFM+B+uYI97NOEfnPi0Z1Rk6PDao=",8525870923275203545,8748546520311803599,-8054106484305132071,4421709243115879601>()) {
                  case 439006644:
                     return false;
                  default:
                     throw null;
               }
            }
         );
      if (var3) {
         switch ((int)com.yiyiaddon.m.b.a<"s2z8dw10xzrw0k","R6A8LVpqPNn4zCjI5dUI1a3x5XiDEDYqVJGIq8okAiM=",-40682282297801468,-2345254838815258899,-5929988806421373353,-2670046399327247189>()) {
            case -470727570:
               this.e();
               switch ((int)com.yiyiaddon.m.b.a<"s1frfv55dzas8t","jdblEiJWXtiXxYMcfqb2WCwA6l7eB1ExnR+5q/mP8Us=",-8017683974778516431,4480500295644602370,4990558723353112059,721542463024142072>()) {
                  case 429009677:
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

   public int dC() {
      int var1 = this.cN.size();
      this.cN.clear();
      this.e();
      return var1;
   }

   public com.yiyiaddon.g.a.a a(BlockPos var1, String var2) {
      Iterator var3 = this.cN.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2hmruh403lgq4","k6mEpXBRhjlPpO6Wztl3mbGX6zGR+ITnJz4tfSARPHU=",4424902241169288620,7690169593536510204,-6740322824529085913,-4562373515546304861>()) {
         case 988018689:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s19uv417csm84j","GjQzb8ofhKJw9b1Y83ToQvzNwl5DVX0l647BbkEuLMo=",-2277736299794681863,4533186789186318841,541466843768437903,-6532613651181373333>()) {
                  case -1492212884:
                     com.yiyiaddon.g.a.a var4 = (com.yiyiaddon.g.a.a)var3.next();
                     if (var4.a().equals(var1)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2suxcxohk3dqv","dkkOuAnLt5bF8USEePlV+L0+vM00g5hYE0T8rraOwyQ=",-7273196291157174389,2540073418866327898,-8199521944704347998,-4035902030367694803>()) {
                           case 2111289082:
                              if (var4.bU().equals(var2)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sr60woh3j27qm","59Jd4iX2rDWMe9JFCQXDN53kZ93QD05xkzTCASflasY=",7273440478203078629,4085120929733721207,5503461447684162131,4782678749859957814>()) {
                                    case -278398265:
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

                     switch ((int)com.yiyiaddon.m.b.a<"scu65puig4arf","DZqfHit03M3F0RaucEi3FBujR81uWDv4q4v3ujXQ+1Q=",5202149572567448871,-6114205747616669597,-7839785497095024342,2528048318065460218>()) {
                        case -1305557492:
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

   public List<com.yiyiaddon.g.a.a> bD() {
      ArrayList var1 = new ArrayList();
      String var2 = c.bU();
      if (var2.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s10ja1fuflgdtk","0CD95XtDxGAabzBoZk5tu7b/BMUQTAEfH8Eocibxo5Q=",-7875486419880559626,-3029573272430768588,-7804623857358388414,8003768476647378259>()) {
            case -993634006:
               return var1;
            default:
               throw null;
         }
      } else {
         Iterator var3 = this.cN.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s1b4t93cnq0n39","IzyDQwoGWoUaBAtfsTPo5T5E6/k3BrqG0GRIoqM53HU=",4632357120538318791,-598049425454115882,4025220603744349165,4074945389723024926>()) {
            case -735206138:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s8ghboikqz2bm","8syMEAOAooh4njzMF42UnCXzuHUXeDzjuKUH+ObtySo=",-913844551976874798,8747518668154706458,2011718681949127762,-1274069924731879954>()) {
                     case -1206733645:
                        com.yiyiaddon.g.a.a var4 = (com.yiyiaddon.g.a.a)var3.next();
                        if (var4.bU().equals(var2)) {
                           label28:
                           switch ((int)com.yiyiaddon.m.b.a<"sbxdlxg44soit","xr3qPtekX1GuHghMG9oblkrfUlfdGQgglBvIW0EFErY=",8398284380619393351,4654119908494952677,6659907920628586937,-1793520405387584848>()) {
                              case 1101761002:
                                 var1.add(var4);
                                 switch ((int)com.yiyiaddon.m.b.a<"s18lbs6h9gr4cx","gmZP1OAUDzrCi4NH23VckiGtAiQhHzv+Mp3Z2m6QEwA=",-7044951477195133413,6184838055686816321,-5350897170727807588,2501553045785245005>()) {
                                    case -1866282129:
                                       break label28;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"si2dwsganwk4z","XA3630hr7sc4agt4mGoqAnHCpmL1yc5nm49dYaqnwcE=",-6156754450641095324,-2611478592224870752,-37339746177333580,7756322899618021725>()) {
                           case -275874272:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return var1;
            default:
               throw null;
         }
      }
   }

   public boolean fw() {
      if (!this.cN.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s29cauqyk00ase","ZF/Y4NIK7SLdS9zpKHTFp5elmTw51oOQMyHAaS45DUY=",-5352351344096804531,-7328147071386125092,2739705772762828215,-1424837850087073764>()) {
            case 1116171436:
               switch ((int)com.yiyiaddon.m.b.a<"svcpuhqcopb39","AKmd9cV8ElCvyMNLqJeWxE1EwG0I051BV7+tM6tsdTk=",-4042991261473119858,-3321679782301785008,229405224472572323,-1241244443976921379>()) {
                  case 1338459321:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2c3u37xz5pa2m","9QSP5Z4vq2AD6QW2FezQu9i2iXj7cAw/4YuW9otMXaI=",-6498442728062221905,5688881711904692912,6553628650707901185,-9015961128450304495>()) {
            case 166266290:
               return false;
            default:
               throw null;
         }
      }
   }

   public int a() {
      return this.cN.size();
   }

   private void e() {
      try {
         Path var1 = this.b();
         Files.createDirectories(var1.getParent());
         JsonArray var2 = new JsonArray();

         for (com.yiyiaddon.g.a.a var4 : this.cN) {
            JsonObject var5 = new JsonObject();
            var5.addProperty(
               (String)com.yiyiaddon.m.b.a<"s39iop39ldl0ze","wepb2LV/KgsePPcHSRx4gnPuxhWPdfIzqtbA+cAX",-7785994583418382563,-6372492056357649414,4979342246367857746,-4232974368935290562>(),
               var4.a().getX()
            );
            var5.addProperty(
               (String)com.yiyiaddon.m.b.a<"s2xq1zikwp3e4r","KSgeTkKWc8WBbT+puqTn7RfFKkH1zYrol5TQtLHA",1529024761002048758,769835395894515098,-8959082697521179037,7037899534456996647>(),
               var4.a().getY()
            );
            var5.addProperty(
               (String)com.yiyiaddon.m.b.a<"s3b1ukj25s31hh","V2/+qjRSDwElR5fmC/UgL9Y51jkyLyvWvFq98sdL",-645777009443277138,-232324413184723837,-8712879531779173513,5699914985021645748>(),
               var4.a().getZ()
            );
            var5.addProperty(
               (String)com.yiyiaddon.m.b.a<"s1x9qvg5wgq","yzUVTnl8dyS13ovlfNoLf1j9l40G/6wNPp1B6O59IFv8RA==",-5784954517979261724,-7637781488322996151,-9023278490869415131,-4390480660737492553>(),
               var4.bU() == null
                  ? (String)com.yiyiaddon.m.b.a<"s32fnbmf7hcc6i","aPTuLMilUjmsvipTekEIL69BmG3YeCr5APamSA==",-1772200041308034327,1432606938219391865,-855284452807196376,7518799834252772565>()
                  : var4.bU()
            );
            var5.addProperty(
               (String)com.yiyiaddon.m.b.a<"sopsxj8yckra1","C7sSI8gyE5QZMf/5ozdSSAHulX9Jg6UA0Wn5S9LHPq/1vkje",339225685868970881,4797165759739257070,-5823803328941933458,-1921112845114459449>(),
               var4.fH() == null
                  ? (String)com.yiyiaddon.m.b.a<"s32fnbmf7hcc6i","aPTuLMilUjmsvipTekEIL69BmG3YeCr5APamSA==",-1772200041308034327,1432606938219391865,-855284452807196376,7518799834252772565>()
                  : var4.fH()
            );
            var2.add(var5);
         }

         Files.writeString(var1, e.toJson(var2));
      } catch (Exception var6) {
      }
   }

   private void bj(String var1) {
      String var2 = this.gp();
      JsonElement var3 = JsonParser.parseString(var1);
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"svc13jfqabzlc","exG2jm9D9p7zng5mT7npl7KoYIkBmEzxs9JXTN/a4gM=",8849642061895252097,1312589764182968426,-2307397434209397265,-436163515622953934>()) {
            case 1360909945:
               if (var3.isJsonArray()) {
                  Iterator var4 = var3.getAsJsonArray().iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s1ea912ttkcces","PCP4tsUY62NWGKODn9ILQ1eT8lmlIqAQQTizuzTDnfE=",-7378964922609566300,7503648670612650640,-9019854847269465218,-5638624468209469952>()) {
                     case -540638440:
                        while (var4.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s321bcxc71bj03","edF93pYl7vtzvVPcYDcHAiU7nVcUUGycymHcq6DuzqI=",3370199803192714494,7418282757907103407,-2747988092654964038,-523743051238167379>()) {
                              case -1501097941:
                                 JsonElement var5 = (JsonElement)var4.next();
                                 if (!var5.isJsonObject()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2il52a1bsaljg","AGwrmLmPKaEg7xH05gZ7KvZFug8jW6Mf2qNdS3vCYbU=",1544660768159540829,-8326767977648865175,8445688962243634442,3880793087044123942>()) {
                                       case 440589491:
                                          switch ((int)com.yiyiaddon.m.b.a<"s3taksg6x5e6ko","GxQanLPCPjucmTcfUW3spoKtV//GNbHPrc09uqi/zY8=",-3996428459111850419,7755659983418931946,-862232199576497376,3040258752071764233>()) {
                                             case 1090775278:
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
                                       (String)com.yiyiaddon.m.b.a<"s39iop39ldl0ze","wepb2LV/KgsePPcHSRx4gnPuxhWPdfIzqtbA+cAX",-7785994583418382563,-6372492056357649414,4979342246367857746,-4232974368935290562>()
                                    );
                                    int var8 = a(
                                       var6,
                                       (String)com.yiyiaddon.m.b.a<"s2xq1zikwp3e4r","KSgeTkKWc8WBbT+puqTn7RfFKkH1zYrol5TQtLHA",1529024761002048758,769835395894515098,-8959082697521179037,7037899534456996647>()
                                    );
                                    int var9 = a(
                                       var6,
                                       (String)com.yiyiaddon.m.b.a<"s3b1ukj25s31hh","V2/+qjRSDwElR5fmC/UgL9Y51jkyLyvWvFq98sdL",-645777009443277138,-232324413184723837,-8712879531779173513,5699914985021645748>()
                                    );
                                    String var10 = a(
                                       var6,
                                       (String)com.yiyiaddon.m.b.a<"s1x9qvg5wgq","yzUVTnl8dyS13ovlfNoLf1j9l40G/6wNPp1B6O59IFv8RA==",-5784954517979261724,-7637781488322996151,-9023278490869415131,-4390480660737492553>()
                                    );
                                    String var11 = a(
                                       var6,
                                       (String)com.yiyiaddon.m.b.a<"sopsxj8yckra1","C7sSI8gyE5QZMf/5ozdSSAHulX9Jg6UA0Wn5S9LHPq/1vkje",339225685868970881,4797165759739257070,-5823803328941933458,-1921112845114459449>()
                                    );
                                    this.cN.add(new com.yiyiaddon.g.a.a(new BlockPos(var7, var8, var9), var10, var2, var11));
                                    switch ((int)com.yiyiaddon.m.b.a<"s3xuiktn06asl","f4mb3rAMm85RpWfFX818VuLM/Z0yqxd4hsmQxgSIzpI=",-4158373096687040113,-7320625135020218389,5708670810916121404,-2811495805640623513>()) {
                                       case 1609808744:
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
                  switch ((int)com.yiyiaddon.m.b.a<"sltuio5x3zhrb","IYHiWEFw1TRpw62tAgCsES3GlMK3Q22VLBmPXXZcw48=",6518687724950531601,-5249440172912186124,-1978202525044107408,-6613445460265842367>()) {
                     case 444034923:
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

   private static String a(JsonObject var0, String var1) {
      JsonElement var2 = var0.get(var1);
      if (var2 != null && !var2.isJsonNull()) {
         try {
            return var2.getAsString();
         } catch (Exception var4) {
            return (String)com.yiyiaddon.m.b.a<"s32fnbmf7hcc6i","aPTuLMilUjmsvipTekEIL69BmG3YeCr5APamSA==",-1772200041308034327,1432606938219391865,-855284452807196376,7518799834252772565>();
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s32fnbmf7hcc6i","aPTuLMilUjmsvipTekEIL69BmG3YeCr5APamSA==",-1772200041308034327,1432606938219391865,-855284452807196376,7518799834252772565>();
      }
   }
}
