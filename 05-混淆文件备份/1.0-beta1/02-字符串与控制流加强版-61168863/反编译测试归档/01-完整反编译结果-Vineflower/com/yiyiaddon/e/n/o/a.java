package com.yiyiaddon.e.n.o;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class a {
   private static final Logger o = LoggerFactory.getLogger(
      (String)com.yiyiaddon.m.b.a<"s3uc0gea2pui3p","AWbXfBqX0VOEFsJ8PRa9N+6vxYAAdEBLqQY6SZP8S0qc7VVGZ1tbvY3Gz3dNOOxptEAkJjLCSTOHL/iqVIM=",8045083010985377535,3901352336982177378,241652191437423011,8007977907520134738>()
   );

   private a() {
   }

   public static boolean al(String var0) {
      String var10000;
      if (var0 == null) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s278dlma9efftn","d1bs1FiUb39MX5SN+kS9mLUKBc/1qpXvihN/lNdxA/E=",-4576535147953138400,-3223428018452709819,-4547990344776291995,-7823953647683626485>()) {
            case 1191372188:
               var10000 = (String)com.yiyiaddon.m.b.a<"sx9hpzahza1wq","wcWcWanzIy719Nlj9J+YpKQS6yFOEs7lRmi7oA==",-2380878822931900313,-360882210741146706,-1272129112347621586,7728725443943719102>();
               switch ((int)com.yiyiaddon.m.b.a<"sxn8p2sv1t8it","MkKsaSMS0JlJwxbfjP7f4If6tbahCyZbpbQZ6T72g1g=",6250881489614699379,1318368098281795539,-6589773144903502731,-8370161419496401319>()) {
                  case -820488028:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var0;
         switch ((int)com.yiyiaddon.m.b.a<"s1xds4m6xcldrx","b5814/N+rSsEwGBG4kVwjpAIfzPpQu89ZR+VguwN5GQ=",-1124929899710003116,5801416119393449794,5191751497443756190,3464847853025746796>()) {
            case -1613201273:
               break;
            default:
               throw null;
         }
      }

      Identifier var1 = Identifier.tryParse(var10000);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1eim0wk1qnxa","mrvkuAYJ5hnSczDCz7z4lTaQF7Zk2j7hHAVj3PQ4WB8=",-4793251887469771137,2136462716025294513,3340232039572358923,7901584653456680587>()) {
            case 1024425041:
               return false;
            default:
               throw null;
         }
      } else {
         return a(a(var1));
      }
   }

   public static ItemStack c(String var0) {
      Identifier var1 = Identifier.tryParse(
         var0 == null
            ? (String)com.yiyiaddon.m.b.a<"sx9hpzahza1wq","wcWcWanzIy719Nlj9J+YpKQS6yFOEs7lRmi7oA==",-2380878822931900313,-360882210741146706,-1272129112347621586,7728725443943719102>()
            : var0
      );
      if (var1 == null) {
         return ItemStack.EMPTY;
      }

      if (!al(var0)) {
         o.warn(
            (String)com.yiyiaddon.m.b.a<"s3gqxh639nt4kr","9wzkW/Zg/zStNZJEJZFGx1DcnMEdVPTPqbpfeMWzcBhdMMwY6YB5EZkB8XzeSdfzPjOcjw5W/DsDbLY9hICMnajEkHFrV1Gwfpos3te/w34=",-758306965821896939,3674055816905086694,-3028711007501598646,2464214459350473436>(),
            aS(var0)
         );
         return ItemStack.EMPTY;
      }

      try {
         Item var2 = BuiltInRegistries.ITEM.getValue(var1);
         if (var2 != null && var2 != Items.AIR) {
            ItemStack var5 = new ItemStack(var2);
            if (var5.get(DataComponents.ITEM_MODEL) == null) {
               var5.set(DataComponents.ITEM_MODEL, var1);
            }

            return var5;
         } else {
            ItemStack var3 = new ItemStack(Items.PAPER);
            var3.set(DataComponents.ITEM_MODEL, var1);
            return var3;
         }
      } catch (Exception var4) {
         o.warn(
            (String)com.yiyiaddon.m.b.a<"s2rog6lg2940u2","4xUa8wclq6hzJsiccWYFuxUX98a8ypK5J3UVfbmrXlitbz0qD9pIY5z0tkZ+owBKSefdL+PCWwQP4vdw+tuIP1LTwLmEoHXxXJ5tFX6ZgDn3UrGHXoVmjw==",-5488055255071126388,2299603034372708083,4969497150802752529,-4285325746756882741>(),
            var0,
            var4.getMessage()
         );
         return ItemStack.EMPTY;
      }
   }

   public static String aS(String var0) {
      String var10000;
      if (var0 == null) {
         label85:
         switch ((int)com.yiyiaddon.m.b.a<"s2c8c2fuf48ygd","HACAhm6zu4Sn+ijlBkQImJvW+oLe28iAKzmpVXooeMA=",-1341552137000005082,4315585153529248121,-1294846979397984854,-5614045606724190023>()) {
            case 1046558522:
               var10000 = (String)com.yiyiaddon.m.b.a<"sx9hpzahza1wq","wcWcWanzIy719Nlj9J+YpKQS6yFOEs7lRmi7oA==",-2380878822931900313,-360882210741146706,-1272129112347621586,7728725443943719102>();
               switch ((int)com.yiyiaddon.m.b.a<"st4r95tslutl2","QSxNOImKL0MuG5Z2DkUIkR49LwzEFkET0VDD7ITSUcw=",-6810719261940867074,5198903313332747178,6245831986799336250,2637348239102279220>()) {
                  case 971033476:
                     break label85;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var0;
         switch ((int)com.yiyiaddon.m.b.a<"s1bp8pr363vh3x","iNr/goOySHmK3VbyisxjO4+5PHeJc9oTeQO321iyUAA=",-3997445583625740609,3920142714748422731,1344328853372953554,-7405059979239975046>()) {
            case -1361633524:
               break;
            default:
               throw null;
         }
      }

      Identifier var1 = Identifier.tryParse(var10000);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2qqmtpwz2gpkr","XhVXmBTCHieXoNI9yabv4I/JS9GBSDr0cudTdcWaBQE=",4639445004987486564,766409754329079421,-5942177233676346438,1847629308900577981>()) {
            case 1512048104:
               return var0 + "";
            default:
               throw null;
         }
      } else {
         Identifier var2 = a(var1);
         if (!a(var2)) {
            switch ((int)com.yiyiaddon.m.b.a<"s2cw9885ky73cz","6i7xnLUCMrhmxltz6tllV4eeD/BrfDD3ganaoqLShNQ=",-4904428214666297065,1394270889549697733,4718263165193818131,5618937336977019348>()) {
               case 1750861991:
                  return var1.getNamespace() + var2.getPath() + var0;
               default:
                  throw null;
            }
         } else {
            JsonObject var3 = a(var2);
            if (var3 == null) {
               label74:
               switch ((int)com.yiyiaddon.m.b.a<"s21ojik9gzu57d","36Pu79oYw3w3BU2KCTJkmC65gNUXdtyyH24zMSlmiaw=",5892350253377478571,-2788156989041246487,7426441646109389569,5782001504203072209>()) {
                  case -102167343:
                     var10000 = null;
                     switch ((int)com.yiyiaddon.m.b.a<"sol5oy02ksom8","qZpWTRGOTbUyUgpT9wz3XjDv/DMUh51rMNSVa9CfXk4=",-5459224460996375111,-984250669003112173,-402467864151762251,-354454763360910268>()) {
                        case 370719770:
                           break label74;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10000 = a(var3);
               switch ((int)com.yiyiaddon.m.b.a<"s21z5gexy6gs8f","V1fjQurMdusUVXbo8JuQuBoDwyN4CxQB6ED48EqyAGY=",-6906689536248515197,-668414172867566487,-1537792755204743415,-7603425588625296527>()) {
                  case -1229208952:
                     break;
                  default:
                     throw null;
               }
            }

            String var4 = var10000;
            if (var4 == null) {
               label67:
               switch ((int)com.yiyiaddon.m.b.a<"svrtbog6djhpf","RFjrVUgN/NjeCfd3VDLOmsnj0x6pVMSr/mafvnPRQpk=",-2606923276955016051,4203026064328303293,4079868590921612881,-3153320116370002737>()) {
                  case 1657816507:
                     var10000 = null;
                     switch ((int)com.yiyiaddon.m.b.a<"s15648rn1g8ros","Mv21SwIbqgX1GGDG2ZGSKvuBDtjsTwi7fGk0qnECXVk=",-9119623519664600028,-2131075778002847626,2883552427770505617,-535042817126009980>()) {
                        case 999161054:
                           break label67;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10000 = aT(var4);
               switch ((int)com.yiyiaddon.m.b.a<"s3e389qn80kk9b","MAIxNAVVsIv35VtOOx39sNxigX8YsRdnkwdUxG1OxVc=",6204709863064092905,-1121063086653752265,153837595502750294,-1447744809736672270>()) {
                  case 1081142873:
                     break;
                  default:
                     throw null;
               }
            }

            String var5 = var10000;
            var10000 = var1.getNamespace();
            String var10001 = var2.getPath();
            String var10003;
            if (var4 == null) {
               label60:
               switch ((int)com.yiyiaddon.m.b.a<"s13pcgw8qjp22i","99LTrhT6x0rx21ORT0Sc5AeeleDtQ6iiiWU8//gV+zA=",-1122240118006731727,-1220382685650083456,-6180138492394456171,7187490110524761320>()) {
                  case -1404986571:
                     var10003 = (String)com.yiyiaddon.m.b.a<"s3a006dajcmax2","vTnuDtcAKf7wZmTmqO3RpOyUdZ/xmhg1cW6Lml8InFV/j+HAPKJezw==",-698896734432708475,9196133172254036522,-5034344878392292252,-8773519615712620460>();
                     switch ((int)com.yiyiaddon.m.b.a<"slrvj5rj2g2c8","45NeZpGoiVW+OJ6OZOflcPVFA36qn0qiGbUZ9PGIFBs=",1863480890993473677,899554695235387876,-3943132327579285250,1312905424558615626>()) {
                        case 1706318693:
                           break label60;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10003 = var4;
               switch ((int)com.yiyiaddon.m.b.a<"s8qp3ajs53lku","mpO4GLfjIzqjGeKFSwT6nEK3+oyQctsuM836D5WAVIQ=",-1161014859455344808,-8998608890521586939,-4719327269165396522,7435781133538092123>()) {
                  case 2138446991:
                     break;
                  default:
                     throw null;
               }
            }

            if (var5 == null) {
               switch ((int)com.yiyiaddon.m.b.a<"ssgzddh2tkoa","jOR225clpqCm8m+1GlyGiYNRm2rG+unDaMKJi8jJKv4=",-6472553018639054505,6831889033235893396,5089457135335719984,5904166110165559203>()) {
                  case -1273970561:
                     String var10004 = (String)com.yiyiaddon.m.b.a<"s3i7l9sqvrclut","DJq8hFkFccETTqHJRoeEajYVqGVsAZ3QxKNl9MrKM1S2Ylfs5JeKDld9jaD5MA==",5058548434063721337,-4522428267367728966,2578128961148765487,-5888922853980813015>();
                     switch ((int)com.yiyiaddon.m.b.a<"so9xmlnsd18n0","9lICMSUB5VUUZHuqucAZ483msLKuosmYWSU4jN8Nvr8=",658230745890320052,-3875185879795102383,-171578858945245048,-1877079172765264147>()) {
                        case 1469634235:
                           return var10000 + var10001 + var0 + var10003 + var10004;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               switch ((int)com.yiyiaddon.m.b.a<"s2awce5jjskphd","Ysp3ml/jSSbO02iYpkyfR/KpCwAZoNyu16b9mKgQcmI=",3958527479713559218,5640952010276089893,-3363034958161947125,8361133667405434028>()) {
                  case 1723048893:
                     return var10000 + var10001 + var0 + var10003 + var5;
                  default:
                     throw null;
               }
            }
         }
      }
   }

   private static Identifier a(Identifier var0) {
      return Identifier.fromNamespaceAndPath(var0.getNamespace(), var0.getPath() + "");
   }

   private static boolean a(Identifier var0) {
      List var1 = bb();

      for (int var2 = var1.size() - 1; var2 >= 0; var2--) {
         try {
            if (((PackResources)var1.get(var2)).getResource(PackType.CLIENT_RESOURCES, var0) != null) {
               return true;
            }
         } catch (Exception var4) {
         }
      }

      return false;
   }

   private static JsonObject a(Identifier var0) {
      List var1 = bb();

      for (int var2 = var1.size() - 1; var2 >= 0; var2--) {
         try {
            IoSupplier var3 = ((PackResources)var1.get(var2)).getResource(PackType.CLIENT_RESOURCES, var0);
            if (var3 != null) {
               try (InputStream var4 = (InputStream)var3.get()) {
                  JsonElement var5 = JsonParser.parseReader(new InputStreamReader(var4, StandardCharsets.UTF_8));
                  return var5.isJsonObject() ? var5.getAsJsonObject() : null;
               }
            }
         } catch (Exception var9) {
         }
      }

      return null;
   }

   private static List<PackResources> bb() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0.getResourceManager() == null) {
         return List.of();
      }

      try {
         return var0.getResourceManager().listPacks().toList();
      } catch (Exception var2) {
         return List.of();
      }
   }

   private static String a(JsonObject var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1hmvo0rck8eh4","D5zTjSwJdyd60wxOzMrrHXAc+zPVSW3PV1pl4X4ROUs=",-6104128865741426903,8389156427429174075,-45615321002285341,-6808020305478551890>()) {
            case 1199766988:
               if (var0.has(
                  (String)com.yiyiaddon.m.b.a<"s3xyg62jlupzl","xnr+pcxUPCKA1tALjo/+e2/g5lBQgWQXm1CFIE2ttSgsznvFCPA=",6234801692694650379,-2269829780614883025,7204138626483118994,-5558003593485565197>()
               )) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3969tcs2xzj1y","w5XBuTFpJ53c1Y785IQU8ye/6LxgSoNoMVApEwoHzBQ=",8377889020183222320,-3883295150130669800,-5337690326412073902,-6428817920391441391>()) {
                     case -725014459:
                        if (var0.get(
                              (String)com.yiyiaddon.m.b.a<"s3xyg62jlupzl","xnr+pcxUPCKA1tALjo/+e2/g5lBQgWQXm1CFIE2ttSgsznvFCPA=",6234801692694650379,-2269829780614883025,7204138626483118994,-5558003593485565197>()
                           )
                           .isJsonObject()) {
                           JsonObject var1 = var0.getAsJsonObject(
                              (String)com.yiyiaddon.m.b.a<"s3xyg62jlupzl","xnr+pcxUPCKA1tALjo/+e2/g5lBQgWQXm1CFIE2ttSgsznvFCPA=",6234801692694650379,-2269829780614883025,7204138626483118994,-5558003593485565197>()
                           );
                           if (var1.has(
                              (String)com.yiyiaddon.m.b.a<"s3xyg62jlupzl","xnr+pcxUPCKA1tALjo/+e2/g5lBQgWQXm1CFIE2ttSgsznvFCPA=",6234801692694650379,-2269829780614883025,7204138626483118994,-5558003593485565197>()
                           )) {
                              switch ((int)com.yiyiaddon.m.b.a<"s37swxehl38rgb","XaRfsxXMZK+W/r7jSa5VNtavtJOyeAwjdi2p/qawKXE=",-7998012560957454481,3506009233922790189,91786781670582811,3620355231534564342>()) {
                                 case 131511121:
                                    if (var1.get(
                                          (String)com.yiyiaddon.m.b.a<"s3xyg62jlupzl","xnr+pcxUPCKA1tALjo/+e2/g5lBQgWQXm1CFIE2ttSgsznvFCPA=",6234801692694650379,-2269829780614883025,7204138626483118994,-5558003593485565197>()
                                       )
                                       .isJsonPrimitive()) {
                                       return var1.get(
                                             (String)com.yiyiaddon.m.b.a<"s3xyg62jlupzl","xnr+pcxUPCKA1tALjo/+e2/g5lBQgWQXm1CFIE2ttSgsznvFCPA=",6234801692694650379,-2269829780614883025,7204138626483118994,-5558003593485565197>()
                                          )
                                          .getAsString();
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"spw9emn6vhxzi","CMCDBUHf42QVo2zFmNxdA6+yL3f1XWrP8b5lQqUoLpc=",-8444798736331307727,-3099213653900448179,-4594688123861966493,669285460195907817>()) {
                                       case -1389804870:
                                          return null;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           return null;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2ygt4me4u91e4","HcWoF2nAJ74R55dqY6vhJTVHQcZH7Y48ED/TrHwBdgw=",8813927320113432711,6665952205424423124,-4634684680612863786,-4766203963752169550>()) {
                           case 734790214:
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

   private static String aT(String var0) {
      Identifier var1 = Identifier.tryParse(var0);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1e00usdjaix12","b6z7vnX9MPjdMfJANXhX412E8MjXsJv5xa15HpXYpKI=",4480091213905917081,8049788884569427344,-8674893421797230362,-757591501109465396>()) {
            case 1629268375:
               return null;
            default:
               throw null;
         }
      } else {
         Identifier var2 = Identifier.fromNamespaceAndPath(var1.getNamespace(), var1.getPath() + "");
         JsonObject var3 = a(var2);
         if (var3 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s37b45487iolr0","I4KLu6LqEO4Wihz6+UlogJ2HhsgfuXjGD8LQLgZSHa4=",454673474269337082,2317480435893010375,-4267411103846395784,1635387884637028727>()) {
               case -746775170:
                  if (var3.has(
                     (String)com.yiyiaddon.m.b.a<"s22d5rhv2stph4","sAdh9H76rKUFSCKOghCgPBwk1So8omtJCnm8qP2M/iQyadOahD2PawjdJxM=",-8490008043096386878,-2073985496945414841,2144184450108446896,-7526339746358969395>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1nv1xwu0wfm88","vKrdRlCpMJqTMZFrcRuQ3sRrXlpL9Y2js0szIsnjtsE=",5130988569576251641,8987450569463775701,4426925861070015033,-1585696799123273415>()) {
                        case -1985583008:
                           if (var3.get(
                                 (String)com.yiyiaddon.m.b.a<"s22d5rhv2stph4","sAdh9H76rKUFSCKOghCgPBwk1So8omtJCnm8qP2M/iQyadOahD2PawjdJxM=",-8490008043096386878,-2073985496945414841,2144184450108446896,-7526339746358969395>()
                              )
                              .isJsonObject()) {
                              JsonObject var4 = var3.getAsJsonObject(
                                 (String)com.yiyiaddon.m.b.a<"s22d5rhv2stph4","sAdh9H76rKUFSCKOghCgPBwk1So8omtJCnm8qP2M/iQyadOahD2PawjdJxM=",-8490008043096386878,-2073985496945414841,2144184450108446896,-7526339746358969395>()
                              );
                              String[] var5 = new String[]{
                                 (String)com.yiyiaddon.m.b.a<"s1ihdioxxku5vr","Ks8JbJzTxKo8k3di7G2P3unmZU+lAzql7ToRektesbiTxvh5nRFXAg==",-1125297099089847340,-2778995066243431988,1776140236398411397,-7815980044278419997>(),
                                 (String)com.yiyiaddon.m.b.a<"s332up99szctfp","yVqbe3ou3I49WMplDidE/FTM9fQMKaIyfYeW2KoC",7142686189560565817,-7047754322711152140,6776309233731058254,-9016505269287242485>(),
                                 (String)com.yiyiaddon.m.b.a<"s3oore3ljzwm2y","hgOMQoyAbLa1uNueT4Czq7wkt4vnhLcNwz899xmlB7WlGn8CNSHY8e0fAgw=",1132364133441519765,1559194516104021376,6291355416486187743,4850396470765967253>(),
                                 (String)com.yiyiaddon.m.b.a<"s12gbmi87ds908","6utvUogTpZe1POJ9SVpZd5ZpfO7puK+0I+ziABlAukg/Sg==",-4111508379073790040,7544944123235753578,-6569581212555586595,7009696562828558754>()
                              };
                              int var6 = var5.length;
                              int var7 = 0;
                              switch ((int)com.yiyiaddon.m.b.a<"sr4hi3ese3rvs","XTTaixBJNBrtcwXN08w8n9nRknaPjt9hB0T67SRYX50=",-6754838304681632419,7033125692893478360,-3147108068057021817,5672741288453352300>()) {
                                 case -868322591:
                                    while (var7 < var6) {
                                       switch ((int)com.yiyiaddon.m.b.a<"sqi9dwlew1sgp","usF5tDmZPSsGdsxRqR/ECrUtDjx7RtyF8xpMtxMLveo=",7123035634398905963,5023440013040334000,3563942765259251181,-6283434327871846609>()) {
                                          case 1429495509:
                                             String var8 = var5[var7];
                                             if (var4.has(var8)) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s1r597x1ehjcjr","JAOeN3ZZhTwz1aML5wxG2KNkCcpP6IXay8fLed9OU1w=",1225539299183187142,7298614172079176,-4468896820494171036,-4688832415224686454>()) {
                                                   case 1635693874:
                                                      if (var4.get(var8).isJsonPrimitive()) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s358objg0zidob","eCo0DjW+rzjFQrJ8d3sLG1zwtt3bRd7Jje2gnM4GbAM=",6011024901769822642,7213773036381407688,8399524390567334675,3989270347941508394>()) {
                                                            case -850583938:
                                                               return var4.get(var8).getAsString();
                                                            default:
                                                               throw null;
                                                         }
                                                      }
                                                      break;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             var7++;
                                             switch ((int)com.yiyiaddon.m.b.a<"s69b7osvac26c","nDX97LrcG5kjTb4kqu7nFWSEgCcupJAdyKIpfg1WtLw=",-5181797914011991278,5555879082025212186,-3624655228092659888,-6442797144582509662>()) {
                                                case 886130903:
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

                           switch ((int)com.yiyiaddon.m.b.a<"sw4kybu81fpod","Vual4cPlaw8iSG3QXnUpM0a17hq4UG+otqfYnUx70gk=",-8954193314824335265,-3363703767941014729,-789862899525180765,-3898296396182579441>()) {
                              case 282078727:
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
   }
}
