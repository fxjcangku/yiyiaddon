package com.yiyiaddon.e.q.i;

import com.google.gson.JsonObject;
import com.yiyiaddon.i.g.c;
import com.yiyiaddon.m.b;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public final class a {
   public static final String zJ = "emerald_chest";
   public static final String zK = "unload_chest";
   private static final Path m = FabricLoader.getInstance()
      .getConfigDir()
      .resolve(
         (String)b.a<"sjhitcyt46qbk","WaOJdfVk0MZPFhdn5ALk25WqCCaZfc9oK7gmZoYIO+cpAecoDElMi6ERtDJ7Uw==",-7344591548521588677,5191851618774381005,2974213881862092192,-8957140736244975810>()
      )
      .resolve(
         (String)b.a<"s1503g1un1ttjp","uclgAzqd56fhPBdvttQFlzTLyG7Qnjb+2tzt6j5xsobz23gXtqDHu1KwVhM=",-3044257371770545561,379728001091025507,-6036439075293445125,-5539960520536382455>()
      )
      .resolve(
         (String)b.a<"scok6xjqqpc1h","xAEYutehvkn6zJiPwqwrHxwWsJSntARPl7w5Zo0qQcgZ2gCVPnyTDX8C4XI=",-865976625339956051,-2660208673957016108,-846400211540272592,-8697687090276776751>()
      );
   private static final Path n = FabricLoader.getInstance()
      .getConfigDir()
      .resolve(
         (String)b.a<"sjhitcyt46qbk","WaOJdfVk0MZPFhdn5ALk25WqCCaZfc9oK7gmZoYIO+cpAecoDElMi6ERtDJ7Uw==",-7344591548521588677,5191851618774381005,2974213881862092192,-8957140736244975810>()
      )
      .resolve(
         (String)b.a<"ssoeyfcvq1hkc","qBecwyeP3qg1koTXsE3DTjFacft1rXm33jC5Kt2N/IT6KiHROn1JkA==",4262403165245704954,-6725459170151752058,-6873617199835954484,8393290445899271055>()
      );
   private static final Map<String, com.yiyiaddon.e.q.f.b> aH = new LinkedHashMap<>();
   private static String nc;

   private a() {
   }

   public static void cz() {
      String var0 = c.gn();
      if (var0.equals(nc)) {
         switch ((int)b.a<"s2gt7g0sdzi1ey","6JKrjA2PPvOVUu2BMPUoT/dO16cgZAiBJ1ztjx7o8po=",1211381049015347212,-7349433782428398025,7868684547448616846,-5693792197430515385>()) {
            case -1222343636:
               return;
            default:
               throw null;
         }
      } else {
         C();
      }
   }

   public static void C() {
      aH.clear();
      nc = c.gn();
      Path var0 = b();
      c(var0);
      JsonObject var1 = com.yiyiaddon.j.a.a(var0);
      if (var1 == null) {
         switch ((int)b.a<"s17w0tsm5tz56m","gM406D74HJxDtrR5UT3h1wQ+ua5sN3iUngYBircgqHU=",3925516253985274678,7347066665019232586,-3590588245691088198,-3816885746786903204>()) {
            case -2116795158:
               return;
            default:
               throw null;
         }
      } else {
         a(
            var1,
            (String)b.a<"s11swwhpb5arjc","MStw4/W2vCodCGXTPGQDxdjPCUt48LMQPS6LmPWIh0pYl3uTHnJvDAKA+UPcvk44rnKCEO5V",-2322165164369052810,6274758309237410595,-3091587696759370289,5289853829447940420>()
         );
         a(
            var1,
            (String)b.a<"st35y68h7supi","L/dj0CRHyRh4DQlHLF/RZuj0qLokbsuKlYIRZpY11VZTlFEoFb5VnMh9PcciZkpaiLYpcw==",-5805453020822813481,9111991758723222106,-6518595475196723499,7912270332448785500>()
         );
      }
   }

   public static void cy() {
      aH.clear();
      nc = null;
   }

   private static void a(JsonObject var0, String var1) {
      if (var0.has(var1)) {
         switch ((int)b.a<"s3vqzx6w7mon7x","Egrm/kAo852g/xStcV8X4hQ5gTJJ4QPCbYcID8GNmdI=",-805652368103850925,1915782480371884532,-654298331629382484,7178060051231815339>()) {
            case -598926469:
               if (var0.get(var1).isJsonObject()) {
                  JsonObject var2;
                  String var10000;
                  label68: {
                     var2 = var0.getAsJsonObject(var1);
                     if (var2.has(
                        (String)b.a<"su05gx6zesnos","ufP1/fm/uDOLB2/iaPEeGIyfOZVy1BQlXiwjOr2q4VroxA==",4835564829482256794,-66241411405274382,-1848379064950715020,-4620907932381464539>()
                     )) {
                        switch ((int)b.a<"s11t1zda7bdwib","48mL3/faUStTUpx4JyBakGy05lvxi3LA5MFkgYszqUk=",-8959717110949529276,9098565715066094528,7123996634336572489,8144195469674641701>()) {
                           case 47977535:
                              if (var2.get(
                                    (String)b.a<"su05gx6zesnos","ufP1/fm/uDOLB2/iaPEeGIyfOZVy1BQlXiwjOr2q4VroxA==",4835564829482256794,-66241411405274382,-1848379064950715020,-4620907932381464539>()
                                 )
                                 .isJsonPrimitive()) {
                                 switch ((int)b.a<"s9vrb336jx3d8","+C51wkFJTBzHqjcCubtC57yKvWc4R44ChOFRM/i3kkM=",-1204278545512839994,-6036165891499388523,-3883581966448747708,-5362750753624969097>()) {
                                    case -1374373253:
                                       var10000 = var2.get(
                                             (String)b.a<"su05gx6zesnos","ufP1/fm/uDOLB2/iaPEeGIyfOZVy1BQlXiwjOr2q4VroxA==",4835564829482256794,-66241411405274382,-1848379064950715020,-4620907932381464539>()
                                          )
                                          .getAsString();
                                       switch ((int)b.a<"s1shgnp992ukv2","N0mpKQ636W8o2JgWPLBRNJBSEmJ/dUjFLlt1rJRZ6QM=",-79934834496226647,703192195376780023,-3338191044302657106,-3874988523325839058>()) {
                                          case 247436207:
                                             break label68;
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

                     var10000 = null;
                     switch ((int)b.a<"s1jhvi0pgug077","f5k1mpmxISRkspcF0stj8G7YQ5u4bkP8aoKoeO5yrGw=",2831899436432681647,-4513636752022153314,-7923152324265512323,-7841215785730423433>()) {
                        case 1523205111:
                           break;
                        default:
                           throw null;
                     }
                  }

                  BlockPos var3 = a(var10000);
                  if (var3 == null) {
                     switch ((int)b.a<"s8p5ovdnyaxk0","L7mAT+3jpMhsU+FV2WqhfAFz6N6Ge0NlCrSLhHQ+Kgk=",1326386728071493285,1530198278757704097,-4618358517821796909,-7413299081603300582>()) {
                        case -215135224:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     label61: {
                        if (var2.has(
                           (String)b.a<"s2twfhbv017we0","+VBnKpR6XtHx2MTr4ox1r87ssGr5z7nPMHabXRvPCjAbsPJjkSrkH3yXnn2+TA==",1460331217827787099,3723348366254659342,-8620494321412228173,2486667718119261777>()
                        )) {
                           switch ((int)b.a<"s2gvx1smric9zz","w8ExhRSN6PXd9Ev84j9/WLVteogzXNfl7zcmXddX8jo=",269177053160352894,6119909598076397360,1155294584222918079,7128203660860288365>()) {
                              case -681747544:
                                 if (var2.get(
                                       (String)b.a<"s2twfhbv017we0","+VBnKpR6XtHx2MTr4ox1r87ssGr5z7nPMHabXRvPCjAbsPJjkSrkH3yXnn2+TA==",1460331217827787099,3723348366254659342,-8620494321412228173,2486667718119261777>()
                                    )
                                    .isJsonPrimitive()) {
                                    switch ((int)b.a<"s1v146s6557lz2","XqqYf+5kh0h4F4NTbgsiEnUNRlmhnH+g0lro9yDT+gM=",-8684011397560490333,376042212434624613,-2342471939416879952,-7248342002077526850>()) {
                                       case -663402948:
                                          var10000 = var2.get(
                                                (String)b.a<"s2twfhbv017we0","+VBnKpR6XtHx2MTr4ox1r87ssGr5z7nPMHabXRvPCjAbsPJjkSrkH3yXnn2+TA==",1460331217827787099,3723348366254659342,-8620494321412228173,2486667718119261777>()
                                             )
                                             .getAsString();
                                          switch ((int)b.a<"s3bnkcbtjob2bg","mAaNx0FgDik34HxhCKIPcGJBnvIS9NGq7HAYw/eE7wg=",-8273524834317082152,-8069800911217927176,5421163546748148270,-4123287357586267695>()) {
                                             case 881888028:
                                                break label61;
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

                        var10000 = (String)b.a<"s3mokplbl2k54o","puap5vqixfHx/WV5beoVeuYbWGHsFET4p8TFnRUJa9x27RKylggNl+66Cby/Acc671C+jVJHmPraYTlg4K2efLde",246031120011022225,-6297033957123957907,-8055543200718554432,1380954897310448100>();
                        switch ((int)b.a<"s3g9ktcrguw4xd","M9Q782JcpjixvXKkZxM5oWl2EcERmKlek0BBRCDDZnQ=",-3488771563790415349,-8605233202956610329,-6162659854029602642,-7375946397365809882>()) {
                           case -1835858735:
                              break;
                           default:
                              throw null;
                        }
                     }

                     String var4 = var10000;
                     aH.put(var1, new com.yiyiaddon.e.q.f.b(var3, var4));
                     return;
                  }
               } else {
                  switch ((int)b.a<"s1ofqyignn39yh","01oy4UDfIOauTuKU6kxPLwerdSbBT/ZEPZeBafL+L+U=",-6415130245710521976,-2942024571083931951,5209623362214997417,-379665386185591659>()) {
                     case 617230103:
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

   private static BlockPos a(String var0) {
      if (var0 != null && !var0.isBlank()) {
         String[] var1 = var0.trim()
            .split(
               (String)b.a<"sux2rufv51ncm","YibBDQNKbULX67Pvi5VJ/D0ZQD3yUXt0AchFY4k+CmRFxQ==",1586199816260044472,-1802237898879934247,5026113109002862345,-6461102456231127010>()
            );
         if (var1.length != 3) {
            return null;
         }

         try {
            return new BlockPos(Integer.parseInt(var1[0]), Integer.parseInt(var1[1]), Integer.parseInt(var1[2]));
         } catch (NumberFormatException var3) {
            return null;
         }
      } else {
         return null;
      }
   }

   public static boolean bh() {
      cz();
      JsonObject var0 = new JsonObject();
      String[] var1 = new String[]{
         (String)b.a<"s11swwhpb5arjc","MStw4/W2vCodCGXTPGQDxdjPCUt48LMQPS6LmPWIh0pYl3uTHnJvDAKA+UPcvk44rnKCEO5V",-2322165164369052810,6274758309237410595,-3091587696759370289,5289853829447940420>(),
         (String)b.a<"st35y68h7supi","L/dj0CRHyRh4DQlHLF/RZuj0qLokbsuKlYIRZpY11VZTlFEoFb5VnMh9PcciZkpaiLYpcw==",-5805453020822813481,9111991758723222106,-6518595475196723499,7912270332448785500>()
      };
      int var2 = var1.length;
      int var3 = 0;
      switch ((int)b.a<"s1kxlupw6gh1gf","Rb0eL0Vt1p8U5xu9SE9uDFBrzDib3PmmaT6dz0kB8m0=",-4506375115995540343,-1462952367842467422,-4083080556976329700,-3674852733751146233>()) {
         case 1749421928:
            while (var3 < var2) {
               switch ((int)b.a<"sy4n83zhcqy2a","guBVjITLNMYJ0xKWjZhuwnjXKI6MSKV2cnadHKwEOxY=",3505154256576743366,-1941079485234189656,-1015189944033740628,1143183944057334165>()) {
                  case 214605491:
                     String var4 = var1[var3];
                     com.yiyiaddon.e.q.f.b var5 = aH.get(var4);
                     if (var5 == null) {
                        label27:
                        switch ((int)b.a<"s2vfozc1du8qf4","qwE7S5ORBUn8xNwMZnaPdpyxvZpm0MjBCGZracdTLhg=",-478195712611846123,-6637519730237161160,-8697651600209311182,-7696983078809698705>()) {
                           case 1607132366:
                              switch ((int)b.a<"s33v885gy1dgtd","j/k5AL02MSML1K5noCT2ENT9Q1QajHUXf9P5bFxuDFQ=",-4948371047520091152,5870108489209926121,-415800120177483607,-9068333962008109273>()) {
                                 case -1427822209:
                                    break label27;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        JsonObject var6 = new JsonObject();
                        BlockPos var7 = var5.a();
                        var6.addProperty(
                           (String)b.a<"su05gx6zesnos","ufP1/fm/uDOLB2/iaPEeGIyfOZVy1BQlXiwjOr2q4VroxA==",4835564829482256794,-66241411405274382,-1848379064950715020,-4620907932381464539>(),
                           "" + var7.getX() + var7.getY() + var7.getZ()
                        );
                        var6.addProperty(
                           (String)b.a<"s2twfhbv017we0","+VBnKpR6XtHx2MTr4ox1r87ssGr5z7nPMHabXRvPCjAbsPJjkSrkH3yXnn2+TA==",1460331217827787099,3723348366254659342,-8620494321412228173,2486667718119261777>(),
                           var5.ec()
                        );
                        var0.add(var4, var6);
                        switch ((int)b.a<"sn2olag3foxn1","Owogm2Gkya4Q2YwB9JHVJu2i80L8KKEFOfZUHLFRPkM=",-2844887687755891636,7440714315518148838,-4354288396511785005,-5375492464365989523>()) {
                           case 394183724:
                              break;
                           default:
                              throw null;
                        }
                     }

                     var3++;
                     switch ((int)b.a<"s1kpvxlq7np176","ByMEnOuNorYJNu6uRpHIW9wrn13EKI2AP+ws0Fv/Bdo=",5767556568016794678,-3225312867398042484,-6011597682608871604,5422919107108077263>()) {
                        case 1797005222:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return com.yiyiaddon.j.a.a(b(), var0);
         default:
            throw null;
      }
   }

   private static void c(Path var0) {
      if (!Files.exists(var0)) {
         String var1 = c.go();
         if (var1 != null) {
            Path var2 = n.resolve(var1 + "");
            if (Files.isRegularFile(var2)) {
               try {
                  Files.createDirectories(var0.getParent());
                  Files.copy(var2, var0);
               } catch (Exception var4) {
               }
            }
         }
      }
   }

   private static Path b() {
      return m.resolve(fg() + "");
   }

   private static String fg() {
      if (nc != null) {
         switch ((int)b.a<"s3u7w3fbmaa6kb","Ga2HnHnBzDlplwRCa/0I+W4GRQFCJSmKiUiIABfOyko=",8019259743862755360,-3075963536399629871,3286061014637657696,8450426084430938777>()) {
            case 345387578:
               String var10000 = nc;
               switch ((int)b.a<"s1hk8dwqbnsiox","2vnXjCeCtj3CTylLEFwevf5uP/80XRVgFudrscc+O9M=",3749391467655583219,1179615246622679995,-6920334348447808456,7186414656830031873>()) {
                  case 441275109:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var0 = c.gn();
         switch ((int)b.a<"s1hkhsfq9qxl8a","7pgDAZCvlncEaQ3lF5uKu54H3fSJ3joRyykHVTIneFk=",5037044465643256417,-1964466888038131116,2354484684954320305,6905358862726915052>()) {
            case -2071736733:
               return var0;
            default:
               throw null;
         }
      }
   }

   public static com.yiyiaddon.e.q.i.a.a a() {
      cz();
      return new com.yiyiaddon.e.q.i.a.a();
   }

   public static BlockPos D() {
      return b(
         (String)b.a<"s11swwhpb5arjc","MStw4/W2vCodCGXTPGQDxdjPCUt48LMQPS6LmPWIh0pYl3uTHnJvDAKA+UPcvk44rnKCEO5V",-2322165164369052810,6274758309237410595,-3091587696759370289,5289853829447940420>()
      );
   }

   public static BlockPos E() {
      return b(
         (String)b.a<"st35y68h7supi","L/dj0CRHyRh4DQlHLF/RZuj0qLokbsuKlYIRZpY11VZTlFEoFb5VnMh9PcciZkpaiLYpcw==",-5805453020822813481,9111991758723222106,-6518595475196723499,7912270332448785500>()
      );
   }

   public static ResourceKey<Level> c() {
      return a(
         (String)b.a<"s11swwhpb5arjc","MStw4/W2vCodCGXTPGQDxdjPCUt48LMQPS6LmPWIh0pYl3uTHnJvDAKA+UPcvk44rnKCEO5V",-2322165164369052810,6274758309237410595,-3091587696759370289,5289853829447940420>()
      );
   }

   public static ResourceKey<Level> d() {
      return a(
         (String)b.a<"st35y68h7supi","L/dj0CRHyRh4DQlHLF/RZuj0qLokbsuKlYIRZpY11VZTlFEoFb5VnMh9PcciZkpaiLYpcw==",-5805453020822813481,9111991758723222106,-6518595475196723499,7912270332448785500>()
      );
   }

   public static boolean aA(String var0) {
      cz();
      return aH.containsKey(var0);
   }

   public static boolean eP() {
      cz();
      if (!aH.isEmpty()) {
         switch ((int)b.a<"sakzuu448tvnq","EqBbzjObQSu9gmW/rTSiAxbT1L7LJbCZkV0+/rTMMkM=",-5185996996373859157,58031470369154509,2867391907810753036,9114386992908988436>()) {
            case -513228150:
               switch ((int)b.a<"s4glyfw9lz5ti","C+Ft9GyevBzVeJQbUk7nK4wbyTvHbRXlLrpId3UjsLY=",-3755568604599585629,2124001170800518812,3540353318804992293,7603515587058569030>()) {
                  case -970034139:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)b.a<"s1wefx0f4yctbu","MIfv0mBk3mjAFVdgjIBDEoSmaaDKnOdUd6+EIEa4Ys4=",-7557857608541039332,-7374046804187422241,-7850033296430227159,-4927394374078472108>()) {
            case -597076138:
               return false;
            default:
               throw null;
         }
      }
   }

   public static com.yiyiaddon.e.q.f.b a(String var0) {
      cz();
      return aH.get(var0);
   }

   private static BlockPos b(String var0) {
      com.yiyiaddon.e.q.f.b var1 = a(var0);
      if (var1 == null) {
         switch ((int)b.a<"s27m6u4yjfz8sx","sc9kshL/QDwtzLNMmtZfPwGYJkMA8XauR5MyfZtbegs=",-6600418348370905115,-162298702420213813,-9051908695835713981,3353465277172495135>()) {
            case -1482094720:
               switch ((int)b.a<"s3fukempq4ktwz","IXK2WQJipdCreJSmGByGIHIhMbMZOSmF/oyqKFKPXZk=",-3954105467483736737,5926189750712072820,-4590041597321874940,-6043559861555210038>()) {
                  case -1290005880:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         BlockPos var10000 = var1.a();
         switch ((int)b.a<"s2o1dkvqjrbnqj","HD+zAu+pTi5w+Yhlim2O0lSqWWXYBk6NyvzSW2NJgDw=",7212101263691324652,-8256257405803274646,-844268399495684656,-5521939699115856391>()) {
            case 1831758435:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private static ResourceKey<Level> a(String var0) {
      com.yiyiaddon.e.q.f.b var1 = a(var0);
      if (var1 == null) {
         switch ((int)b.a<"s3m9bzh7so4p0a","u9sPRy+uwLUBnoBUSiu1+fctNBTNqoLZu1ZvZPGStbY=",-6787781244939505381,-5851916716593063910,-4827391287313665273,4853979620949351907>()) {
            case -729577089:
               switch ((int)b.a<"s2gbv1tmk8t2ye","tdsyyPob9DmlMfbIQQtR50g0/7/gMjdovmHdkkyPB+E=",-182567848893122550,7487695984154412785,-6574473024408306116,6412572769377276587>()) {
                  case 138885498:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         ResourceKey var10000 = var1.b();
         switch ((int)b.a<"s2wvco2b1qaa68","oWMt4OPKaJWzrpV4cGNe/C4RcBm6Ush1f1xiu4UpxTg=",2968759432247287314,2064437727437566865,6018389026144254813,1260219024315916448>()) {
            case -955580248:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public static void a(String var0, BlockPos var1, ResourceKey<Level> var2) {
      cz();
      aH.put(var0, new com.yiyiaddon.e.q.f.b(var1, com.yiyiaddon.e.q.f.b.f(var2)));
   }

   public static void aU(String var0) {
      cz();
      aH.remove(var0);
   }

   public static void b() {
      cz();
      aH.clear();
   }

   public static final class a {
      private a() {
      }

      public BlockPos F() {
         return com.yiyiaddon.e.q.i.a.b(
            (String)b.a<"s3twb0ut8k5e7","o3kJAaZHLUVfCyBNEe6GtX7gZB9wTOHJY6cWML2Ks4JcD9wSRGwrA5pJBrSSB5KfO08Ojr9i",-2300017964690882826,2171681704281521735,-3765503290768861506,3240146002246261248>()
         );
      }

      public BlockPos G() {
         return com.yiyiaddon.e.q.i.a.b(
            (String)b.a<"sihkic00ef5d3","y+Ny9eBUQ17bjY7ZQAfZRSjqHZbRTTlwqJupnmsYMTZ4zSsvUatmoaULECPq8GtvGTHmXA==",-1241857452761399961,-8716776416841944372,-6713285732325513802,-6481505237154971936>()
         );
      }

      public String fh() {
         com.yiyiaddon.e.q.f.b var1 = com.yiyiaddon.e.q.i.a.a(
            (String)b.a<"s3twb0ut8k5e7","o3kJAaZHLUVfCyBNEe6GtX7gZB9wTOHJY6cWML2Ks4JcD9wSRGwrA5pJBrSSB5KfO08Ojr9i",-2300017964690882826,2171681704281521735,-3765503290768861506,3240146002246261248>()
         );
         if (var1 == null) {
            switch ((int)b.a<"s2b13d4y8bd4nn","A4VpaMTBBniKgS+ApHLsV/FZGa6fQrk4Xww+X55z19s=",7782588125151436367,-7565642463052640845,-9000242855737263230,9210560479036607046>()) {
               case 1794051020:
                  switch ((int)b.a<"s3pnvz7jtj7ye9","47K1R9SyL+7mDhCg9jVhNyCXTzeStnlX/BB+uxoop9E=",-9109698877660167516,-7883642794079044981,3393223070831235591,-5108554929421641870>()) {
                     case 1588114065:
                        return null;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var10000 = var1.ec();
            switch ((int)b.a<"s11s8s3pfe0m1n","Z8oH4eFModGmc1BZvJsJVo9a1vmCQK86B4Cu6SflLCg=",-7927071910400836896,276968083279395187,8657004201260592652,-1505191648609448837>()) {
               case 1173052079:
                  return var10000;
               default:
                  throw null;
            }
         }
      }

      public String fi() {
         com.yiyiaddon.e.q.f.b var1 = com.yiyiaddon.e.q.i.a.a(
            (String)b.a<"sihkic00ef5d3","y+Ny9eBUQ17bjY7ZQAfZRSjqHZbRTTlwqJupnmsYMTZ4zSsvUatmoaULECPq8GtvGTHmXA==",-1241857452761399961,-8716776416841944372,-6713285732325513802,-6481505237154971936>()
         );
         if (var1 == null) {
            switch ((int)b.a<"s23dz91f2e285z","43aberZaVsba5zV121qw5VWbU6atqBLwHR60XnTtmjk=",-7578401041162970257,8671107775281577812,-5399323141067235693,-1971733697464728013>()) {
               case 1145582333:
                  switch ((int)b.a<"s2g47glio8utro","yS33xl6VNp6ERvskHpTh7goqwAkii/Sll44JrZucPnw=",-5210097712304942624,4638109825431568035,6866010253851605680,-3066776802422832996>()) {
                     case -1345881093:
                        return null;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var10000 = var1.ec();
            switch ((int)b.a<"s95ev3oh513pm","FPPSgi+fFcJ5rozsLvlXtVXWC6pVyjMSGZdQ7yh9SEI=",1789253236007878088,1778953721189398497,-6300953114925856068,445815730069072168>()) {
               case -893483988:
                  return var10000;
               default:
                  throw null;
            }
         }
      }
   }
}
