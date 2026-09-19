package com.yiyiaddon.e.n.n;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.ResourceManager;

public final class d {
   private static final int mS = 2048;
   private static final int mT = 200000;
   private static final d a = new d(
      Map.of(),
      Map.of(),
      null,
      null,
      (String)com.yiyiaddon.m.b.a<"s34qnp0f0obn5a","JYNfLInAhrx1jV8lCy09XxWR7KlX/Dv7FzCcp64J",-7224603238547544600,-4846899067203719927,-3720931949141898105,6719249493913339279>()
   );
   private static volatile d b = a;
   private static volatile String tO;
   private final Map<String, Map<Integer, d.b>> ai;
   private final Map<Integer, f.d> aj;
   private final String tP;
   private final String tQ;
   private final String tR;

   private d(Map<String, Map<Integer, d.b>> var1, Map<Integer, f.d> var2, String var3, String var4, String var5) {
      this.ai = var1;
      this.aj = var2;
      this.tP = var3;
      this.tQ = var4;
      this.tR = var5;
   }

   public static d a() {
      return a;
   }

   public static void cy() {
      tO = null;
   }

   public static d b() {
      String var0 = com.yiyiaddon.e.n.a.bT();
      String var1 = com.yiyiaddon.k.e.c.dn();
      String var2 = var0 + var1;
      d var3 = b;
      if (Objects.equals(tO, var2)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2u7ncxtsj8rzl","jaO/l9oJ+K0ltyYf2TmubhwF1r2tU9XoZeI+GUfwWII=",6642224600014001643,-5875642602327875777,-1902717413184232962,-8045906721828884254>()) {
            case 703360597:
               return var3;
            default:
               throw null;
         }
      } else {
         d var4 = a(var0, var1);
         b = var4;
         tO = var2;
         return var4;
      }
   }

   public f.d a(String var1, int var2) {
      Map var3 = this.ai.get(var1);
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sjkkvlevkpc3u","6HF0ZNbrkRzRZca/nO7sMd1NFvzDGXzSlwJViyb9v/s=",546663690014702813,-8750322576549626004,-6378292711777272035,4317406654233283134>()) {
            case 193745247:
               d.b var4 = (d.b)var3.get(var2);
               if (var4 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s36fbsknc1fm1l","La3yuhpbbaADla65Ls8Pu2aGkfBOXJZNtS14B+20D/I=",4385457777218986470,329465657007045010,7115069479050957555,143396294139198179>()) {
                     case 79337713:
                        return var4.a();
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      f.d var5 = this.aj.get(var2);
      if (var5 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s377juaxzcdjfy","VyrcvB/1NnBKGWs5kPjJ6HtUxaHmxEMW/A4tFFuqTzI=",-5913573122032296483,-6490703819683035706,1307539172000402198,2281445453712274655>()) {
            case 1632731605:
               f.d var10000 = f.d.UNKNOWN;
               switch ((int)com.yiyiaddon.m.b.a<"s3yhycghkcben","JDMnGhnxbcWSFS5Fi82o/WcamEwe7nDEaeEFPNobuLk=",7590491828049791422,-2388979024984879520,6855247818488920200,6330003169396544342>()) {
                  case 1384796842:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s18oj9uwe8w9hb","VrmcYulWJXUQN4/9OUsiHT7mlwXvsqKFt8+E4gJzi20=",-8269332854726322222,-6330871834209159435,197581030069922065,-1593776125266847049>()) {
            case -1116636271:
               return var5;
            default:
               throw null;
         }
      }
   }

   public d.a a(String var1, int var2) {
      Map var3 = this.ai.get(var1);
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2k8p4eswamrry","N1PZo7bZ0BJLURxAmz41mEUSIgEhSt0T1IopcxHgH6k=",-3877015661238912279,2222637934407104797,5694648883873061923,-1239135193693530200>()) {
            case 1645230993:
               d.b var4 = (d.b)var3.get(var2);
               if (var4 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1mj5d91stzsh4","Mm5T1OpbP2/eROObsUs1yHlZuDNqDvsgGwi3UbNrqM8=",-3340400491499584843,8099860862101723146,7703808746393946944,-2403474963672425621>()) {
                     case 330693421:
                        return var4.a();
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (this.aj.containsKey(var2)) {
         switch ((int)com.yiyiaddon.m.b.a<"s10s5fzmzbkb7m","Eq67sKnjYl4SOd7L18/0FuyG6+o/4YHjOk+NZAg16YM=",7659366716731500235,707959805368374900,7706333143560298562,-4225934770174282816>()) {
            case 1535763186:
               d.a var10000 = d.a.RESOURCE_EXPLICIT;
               switch ((int)com.yiyiaddon.m.b.a<"s3h9nheqvohj5g","C01Qb6XFS8RU/T10uf2T1weu4ZPf4NeV2pAVPpWrsk0=",975782368519098594,2640246140214138415,8193998106445931074,-2583156414093771105>()) {
                  case -39342697:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s3kiep4np8xbhr","qr0OHWl3cefAtbLwOIH0aMI6JzV5x7vXJb3UkHrlDws=",-8261432905160497679,8015858430519382156,548474878068437528,3702481043856243906>()) {
            case -748596861:
               return null;
            default:
               throw null;
         }
      }
   }

   public String bT() {
      return this.tP;
   }

   public String dn() {
      return this.tQ;
   }

   public String dG() {
      return this.tR;
   }

   public int a() {
      int var1 = 0;
      Iterator var2 = this.ai.values().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s38g00xl0xbxsr","6nwc7RsI47eFnA6NNsWT+Pe7/HxBBEX7n2on406+scc=",20660844045463376,2825060683518043115,2787385219716348237,-2720344258715971730>()) {
         case 1532286706:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2a48fj0pavh5s","ZBqYjtmpMQn/xY7arJRN6uRdG1naLU4ONkSyq8b0RSs=",-7327488926636428025,4045155721238269763,5161688624038963952,453858405527552560>()) {
                  case -497708300:
                     Map var3 = (Map)var2.next();
                     var1 += var3.size();
                     switch ((int)com.yiyiaddon.m.b.a<"s36vr8sgwj6xtv","MGDSyM6y+DH1Pw19+Utxe1Q51tKFk25QBIByIA+HyJg=",-5121231517615473390,-8296128555084319750,-8875731748162133795,6716344949213686829>()) {
                        case 637027191:
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

   private static d a(String var0, String var1) {
      LinkedHashMap var2 = new LinkedHashMap();
      boolean var3 = a(var2);
      LinkedHashMap var4 = new LinkedHashMap();
      boolean var5 = a(var0, var4);
      if (var5) {
         label53:
         switch ((int)com.yiyiaddon.m.b.a<"s2tonykbnfs3ua","6KCMxiksTKjyUHarb22SPYc51txyyyDB/8vxq5kZ9+c=",8451505538459692419,67034615160141543,-7569453594595523301,3272825318331094085>()) {
            case 1660733852:
               Iterator var6 = var4.entrySet().iterator();
               switch ((int)com.yiyiaddon.m.b.a<"s3inuki3hjuyv9","d7sJRjUvhCpSw0GdH3uDgvq2Fx7r+xBnnsCegi7kfq4=",-7706305681828005795,5947462414941512766,5004267986873651949,-7377435755554712867>()) {
                  case -1854086187:
                     while (var6.hasNext()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1lb3d9itwwlzk","ri0e80L40uCcA7PNo0jQr8pYqs9uSQsQToHyW0Pls78=",2295167870522054106,-2023611874058757063,5668096019129597055,-4677737361576186666>()) {
                           case 1650698884:
                              Entry var7 = (Entry)var6.next();
                              var2.putIfAbsent((String)var7.getKey(), (JsonObject)var7.getValue());
                              switch ((int)com.yiyiaddon.m.b.a<"s3h237y4tmvhx1","H9KwlFhkSkO+PrFsB4Tx2G48KF7NMXLSsYXXdBMnjQc=",-1093479897867056760,-4844837682745571538,-7678739070654768186,-6649219063709675737>()) {
                                 case -1912435837:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }
                     break label53;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      HashMap var8;
      String var10000;
      label61: {
         var8 = new HashMap();
         c(var2, var8);
         if (var3) {
            switch ((int)com.yiyiaddon.m.b.a<"s3uh5cx390g3yu","bHUs1phJtja3XiNgGbJ/RI1gX4AsErU63JLU74rHcVQ=",6818870621434075710,1944025291861120028,-7434634655293316507,-5478248304493870261>()) {
               case -330270970:
                  if (var5) {
                     switch ((int)com.yiyiaddon.m.b.a<"s9o39hk72uotq","5FhRXRZ9kGjS4M4Al7YdaruPYb38+GTgPmATJ9jRR5w=",4757596098057800486,8607957763991788155,-3912484874510297940,-7901015283683002695>()) {
                        case 767208903:
                           var10000 = (String)com.yiyiaddon.m.b.a<"s11ywkpppag5m3","o6hWeiz4fSL4B6J4bPO9wxedJKrBXzm+kkT3Iz0fliCYA/wN7Hpahe95amy4WzN4CouTP4VXJYS6tA==",-8487553354422099478,-2058575450280166142,178357623384931908,-9044561163221682602>();
                           switch ((int)com.yiyiaddon.m.b.a<"s3i8k6bvmw4mnp","6jvvRmMwpvWSb8prHlrRum+ZVbxTIXQYyQZQ/Nu9D9Q=",-1505900158510817271,-837743619723179156,-7113457803575194346,6953939432503420345>()) {
                              case 650647646:
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

         if (var3) {
            label44:
            switch ((int)com.yiyiaddon.m.b.a<"s288zxuund5bu2","XCKfNS3iakJzMZmnMxc6qu5HDFsjXrTyTY3P5Mw3Ais=",2737763540172830067,-326460014200278661,-8469058587408568522,-806128734395695922>()) {
               case -1351142330:
                  var10000 = (String)com.yiyiaddon.m.b.a<"sdc9l3dxjv2oc","SzsKUjo/wsLT0QQd5Vo4EtP52xx9TW1xHT5UzDRP5AyBXIu1lMInog==",2128423928699341886,-7037341424305884413,-302122185021236442,2349945395006517131>();
                  switch ((int)com.yiyiaddon.m.b.a<"sdgmycdhbzfhb","2adV5vqMVQBGiITCFStuBVvdejTJHt9JPNnV5qbg65I=",-1424161659692482398,-2990114320685851076,-9065301897968927734,-6806660966086854719>()) {
                     case -813246205:
                        break label44;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else if (var5) {
            label41:
            switch ((int)com.yiyiaddon.m.b.a<"shqhgli8s3h33","rOtiF6s4wPlt3qM7nFlOxOJ/ort2rTtFvhwTVQDsshg=",-5360057757305615690,-2323755113883932257,-6937193465677270492,-2438907642937877667>()) {
               case -1528569109:
                  var10000 = (String)com.yiyiaddon.m.b.a<"st2w5stk6esbu","PCD0v4F+A/lTKAUs1Tjge8w4vP4uTVKgMYRbwjNE7UQvhxsM6hHO5A==",-6254444899921656144,98235077396344878,1449476154843245492,-34369497193939528>();
                  switch ((int)com.yiyiaddon.m.b.a<"s2es2reoik81iv","beIolQ63FRtxbtv67Hwjkf8ldixSyCoQl5kqFYST5cc=",1730130799743350046,-7355562438190990540,2986129700326886466,2179855219207967489>()) {
                     case -1804372813:
                        break label41;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = (String)com.yiyiaddon.m.b.a<"s34qnp0f0obn5a","JYNfLInAhrx1jV8lCy09XxWR7KlX/Dv7FzCcp64J",-7224603238547544600,-4846899067203719927,-3720931949141898105,6719249493913339279>();
            switch ((int)com.yiyiaddon.m.b.a<"s2zoo52ma1542k","vRsJJYAAF4K3QhAid+9RQSJvX2kTYxE7uzwzoWTUEwg=",7470387315296977611,-5258112796071860687,4623685848477052107,-6929137591542994361>()) {
               case -321521942:
                  break;
               default:
                  throw null;
            }
         }
      }

      String var9 = var10000;
      a(var0, var1, var8);
      return new d(var8, a(var8), var0, var1, var9);
   }

   private static Map<Integer, f.d> a(Map<String, Map<Integer, d.b>> var0) {
      HashMap var1 = new HashMap();
      Iterator var2 = var0.values().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s6otmdciuvmtu","Upy+qnLJBvsDyZp9ky7IWLYY7+mmpGjrqMNrZM/0JAM=",4377854286964580964,-3315041749217853485,-4998085162850011055,4025028602096805399>()) {
         case -1638480481:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"svpkzt1q6p5m8","t62LV2AkDqWy+JQT4Bz9LJ0odrxRSbR2zjDCozvlBHY=",-5211567044684527159,8321250020095044717,-9197619578338645463,-7040925334914424711>()) {
                  case -1800296760:
                     Map var3 = (Map)var2.next();
                     Iterator var4 = var3.entrySet().iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s3k40qmf1kso9b","u0tl9qa7jlc+hQKS6DpGzfkVPsM3AewO41jm9924400=",-378859853844262929,641539054763368473,-8678295768239976833,-7273418070176595135>()) {
                        case -1057820830:
                           while (var4.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1ky2r56cwkv92","y2gHmdiFT+l2R51kCD75UTkXNI9/r18pT41niihhoLo=",2795234987721096495,3720549417884047969,8598690239718952489,2078403372016488119>()) {
                                 case -2036641059:
                                    Entry var5 = (Entry)var4.next();
                                    f.d var6 = ((d.b)var5.getValue()).a();
                                    if (var6 == f.d.UNKNOWN) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s39nni81108epn","pHJLSNaYbYUMnuaNZFntbdtNovXpB50jx+wU4fJ9SvE=",-7445343951179578932,-4443540909573987191,-2887523672674199430,187734319189860770>()) {
                                          case 1467350036:
                                             switch ((int)com.yiyiaddon.m.b.a<"s12as4obe1pz1d","8SJHkmRYUzqpHBiLQqlZm7CCwqhgSuvuuecVN7l69P8=",8258723855496758120,8661025619449301788,6582281480117802803,1012049859754737348>()) {
                                                case 1283572560:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       var1.computeIfAbsent((Integer)var5.getKey(), var0x -> new LinkedHashSet<>()).add(var6);
                                       switch ((int)com.yiyiaddon.m.b.a<"sjrrje1ltalg3","5KHj467px1e4Mrw7/1/AqKjyd6qhP0RFwMxDpwA6AVQ=",-5624565171939460746,6399078656614773135,5013233162605014828,-680469446588868629>()) {
                                          case -1902691191:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s2toedqeh74iwh","uDHmvfzWXihpjPdLTJWqiPY9AoG+O5ovFe5pWE+VjX8=",9149926780867086199,-8169541747091583722,-8665307343342980593,-8192752889768336809>()) {
                              case -1813791529:
                                 continue;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            HashMap var7 = new HashMap();
            var1.forEach(
               (var1x, var2x) -> {
                  if (var2x.size() == 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"sko8icggq9sf","2JR88q774ZfDTqhAcxwEreeI3XJsgma4FuPALfx6Rxo=",-2264699859236989119,-8591412188589460557,9013873402423961076,-5200154219705816690>()) {
                        case -1748929945:
                           var7.put(var1x, (f.d)var2x.iterator().next());
                           switch ((int)com.yiyiaddon.m.b.a<"snbw5mfj8pfcl","WBjSGUorxUU+cvOFotiektuWXh2yb06WkTh8okiu6v8=",-4042589267838938045,5506277187649355938,-6934764458632031596,6318538218586454736>()) {
                              case 759411311:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }
               }
            );
            return var7;
         default:
            throw null;
      }
   }

   private static void c(Map<String, JsonObject> var0, Map<String, Map<Integer, d.b>> var1) {
      if (var0.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ov6361vdlf72","Uxk/8r59vAXfnw4f/6y0P22FBbALIhQ2dMVvEjZO1io=",-4640094813083093697,3620437042275667776,3097574561971309109,-4761172650185158040>()) {
            case -1263262023:
               return;
            default:
               throw null;
         }
      } else {
         Function var2 = var0::get;
         Iterator var3 = var0.entrySet().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s2ffbhiubjiche","qPMjLtJqa2CP9Ill/kuAlTZCpP3rlVzZA52p9yH4urQ=",2663064494446691892,831693950433244032,-7706307834666720950,2567465762351582529>()) {
            case 1067135692:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3pznj94k09yai","MZx2EMGk66Lxs+9L3TFv01dmXrYd2oXdZuRBrZmXVH8=",7706444604556165073,-2277402757430031579,-1784346614753052205,5252119190790681363>()) {
                     case -436120375:
                        Entry var4 = (Entry)var3.next();
                        Map var5 = com.yiyiaddon.e.n.n.a.a((String)var4.getKey(), (JsonObject)var4.getValue(), var2);
                        if (var5.isEmpty()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3ddkbhkbfxwty","zuKLeoFGvw6IFLH2qqhz5KevCPAlMOAZENVa1AHat2Y=",-3388057396748220584,2555150060324920336,-8252257034173081617,7938140836927082063>()) {
                              case -911904476:
                                 switch ((int)com.yiyiaddon.m.b.a<"s5x99evrauhxp","wqWp3PLY4lpIHjfiu7bT47jM01MwoGtPJYEDtiqEIbU=",2460306890044242875,8886204172635980530,4563918949987665801,-489762751133584822>()) {
                                    case 384622597:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           Map var6 = var1.computeIfAbsent((String)var4.getKey(), var0x -> new HashMap());
                           Iterator var7 = var5.entrySet().iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s7khd3xkggikj","uGlvpZCaJELIeza0FXa/LTC7McQXDQSUUcWRa7j8iOk=",-8779890573030877662,-8770676602530873153,435698451509387875,-1805023842206038834>()) {
                              case 2030222989:
                                 while (var7.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s8hv1mmc3utrb","IDXqFKHusQ6AuiifPv9G9vFu6qvwEsLhGVL2G2kzrDA=",5491678347084025561,5360291801769352982,-399305725271449016,2206927680749776419>()) {
                                       case -227106408:
                                          Entry var8 = (Entry)var7.next();
                                          a(var6, ((Integer)var8.getKey()).intValue(), (String)var8.getValue());
                                          switch ((int)com.yiyiaddon.m.b.a<"sehosfa38lh0q","CAG+KUak+Uqi5sdBEYHd/9ublatV1AVNqbSRybd3y+Q=",8160229176567909998,-7651032354367123839,-1680457591533956197,7817017278800917705>()) {
                                             case -306237640:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s2yuq94oh9qubb","tsfo6YOCi5GB9DfSbim4G4YSHD9rQpemq5ZAqbV5Jxc=",-3160096289248252525,-1854579522142171533,7822386242037710779,-7923539631870984201>()) {
                                    case 938798141:
                                       continue;
                                    default:
                                       throw null;
                                 }
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
      }
   }

   private static void a(Map<Integer, d.b> var0, int var1, String var2) {
      f.d var3 = b(var2);
      if (var3 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s37wbuam1k6obj","CMPek3IBHUTG4VxfuFtLhqxHmsAMjlKYroJLUwK9mp4=",-8187767258846731047,-2002961615894256342,3418807792521652860,-554541468520642168>()) {
            case 1348343456:
               return;
            default:
               throw null;
         }
      } else {
         d.b var4 = (d.b)var0.get(var1);
         if (var4 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2hhd6dz702qtg","CDJNMGn/yA7hxpJ7tpm4SXnQToD8ostnxcjqE98UHRM=",-714394696710729884,-6220725060868110454,1172457861104939728,4202261632647492768>()) {
               case 2129077115:
                  var0.put(var1, new d.b(var3, d.a.RESOURCE_EXPLICIT));
                  return;
               default:
                  throw null;
            }
         } else if (var4.a() != d.a.CONFLICT) {
            switch ((int)com.yiyiaddon.m.b.a<"s3w1wyksp1c1gn","gXsRhxpVw3uh/penljY/5WWrhZjReEk6qyjwHOgDarA=",-6934613375336269307,-7953235826195321179,4367693560865466223,3822726796806724928>()) {
               case -927521249:
                  if (var4.a() != var3) {
                     var0.put(var1, new d.b(f.d.UNKNOWN, d.a.CONFLICT));
                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s1vy910ssxc9lq","CexBAtfcxaNprLEXrioLdKcgb0FLXS6+dRn/VUZMsH0=",-5516377349888202120,677715580784246632,4429735719936622438,805308256597880905>()) {
                        case 125286624:
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
   }

   private static void a(String var0, String var1, Map<String, Map<Integer, d.b>> var2) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sj63ohm47tbbw","j0f3mvsk15TiVH1W055mYhNPI5AtJc97FuCUsDnrJZo=",8607071317464493129,-7395848718667147810,5132556954906935466,-7649398669183029827>()) {
            case -1179597613:
               if (var1 != null) {
                  Map var3 = e.b(var0, var1);
                  if (var3.isEmpty()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1dmwb1jtggpbk","VhkZEtOp060ol6pwrVhtRdFZQ2KLekha3LpHqFwSbs4=",-2110126245223391418,-2411478782195578205,-3035089383942537841,-4163344315470835635>()) {
                        case -1392381888:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     Iterator var4 = var3.entrySet().iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s33o5kn0pebayq","xyDrt8uWJlBBM4U7+nTSkCKwLsuEsq801Tzxhrj/1sM=",-669644197832574256,-5028038970335268134,9156937692036556970,-8886808165478910392>()) {
                        case -862770528:
                           while (var4.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2r9u5x9md5gj4","WdA7uM4UCtpufimnt3vMFtxqR4PgHd6Kpo/X/JAR4ZM=",3903963784570707530,6188919476643040099,-6946442748604666341,-6565456715138692829>()) {
                                 case -1380690317:
                                    Entry var5 = (Entry)var4.next();
                                    Map var6 = var2.computeIfAbsent((String)var5.getKey(), var0x -> new HashMap());
                                    Iterator var7 = ((Map)var5.getValue()).entrySet().iterator();
                                    switch ((int)com.yiyiaddon.m.b.a<"s100aaiuz58te0","0AoqillEqpvzjiO2RQFmMOjCzryNZCgOTw4e2U1RMF0=",-1752837922962057166,8509843527443291142,3302538498765307971,-3953141992939494598>()) {
                                       case -592581692:
                                          while (var7.hasNext()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2aesaa8isugah","Tyx+4VV/u1RMNlEcoJ9TiVAqmCU2FkV1dAwITfbmYE8=",-2683850720522472960,2037970127643608888,8589201533258887831,7096874376350859375>()) {
                                                case -801818538:
                                                   Entry var8 = (Entry)var7.next();
                                                   if (var8.getValue() == f.d.UNKNOWN) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s30kz7tg3v5s8r","OkocppernUFHj/2TKDbrmqLhvkUMZlEWFxbmcjfTrKQ=",8125693863696340262,-8465438382379913800,8158183055648226444,6059677357484947495>()) {
                                                         case 1656358384:
                                                            switch ((int)com.yiyiaddon.m.b.a<"s7zcl112uqm2d","dymtwGX0S5yIUkJlC31lkjt7WJO2GweqcrwJzMl/O+Q=",-3068382529654559109,-8600999507914801312,8919605291761898103,4018340388531912130>()) {
                                                               case -2068287325:
                                                                  continue;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else {
                                                      var6.putIfAbsent((Integer)var8.getKey(), new d.b((f.d)var8.getValue(), d.a.MANUAL_VERIFIED));
                                                      switch ((int)com.yiyiaddon.m.b.a<"sa0kimp9p523p","yjiwUfUL1YYWwysJOoTkB8UoQ31X4Gs7uAmmnGBwDT0=",-6583723109926804573,-6088707444436842710,6023591841876029747,7139377640021040743>()) {
                                                         case -274712792:
                                                            continue;
                                                         default:
                                                            throw null;
                                                      }
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s2ki5fyxo1839o","czPnFf8vGQDkZliv6q1WIbfFyqiGI++asQrAnd0VzX8=",-2839132235019651568,3660017031530346367,8728296706992015205,-4593192777259651514>()) {
                                             case -1375841678:
                                                continue;
                                             default:
                                                throw null;
                                          }
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
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1b6g07lpr7hy0","s58UjKkm7pvxd6vNHi4Foa+t8u6i+SqS9CrMqrmPljA=",974218076442734816,-1823851508491780742,-7720044372435799200,-2896507088737852087>()) {
                     case 35379712:
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

   private static boolean a(Map<String, JsonObject> var0) {
      ResourceManager var1 = Minecraft.getInstance().getResourceManager();
      if (var1 == null) {
         return false;
      }

      List var2;
      try {
         var2 = var1.listPacks().toList();
      } catch (Exception var11) {
         return false;
      }

      int[] var3 = new int[]{0};

      for (int var4 = var2.size() - 1; var4 >= 0 && var3[0] < 2048; var4--) {
         PackResources var5 = (PackResources)var2.get(var4);

         Set var6;
         try {
            var6 = var5.getNamespaces(PackType.CLIENT_RESOURCES);
         } catch (Exception var12) {
            continue;
         }

         for (String var8 : var6) {
            if (var3[0] >= 2048) {
               break;
            }

            try {
               var5.listResources(
                  PackType.CLIENT_RESOURCES,
                  var8,
                  (String)com.yiyiaddon.m.b.a<"s2orys39s9t2br","lZggZx0ZhlWTfQoC5eomspsHAv9sIAuKB5plg+W1jPupeTh7",726532913079754766,633544896864514084,7674489701546353466,-4393794485931207594>(),
                  (var3x, var4x) -> {
                     if (var3[0] >= 2048) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2p0z5pw7ho10n","E0EUvduTRzX47JYnHV5ZdmHRavAOtzsBVDvfhELQ1Ig=",4039095433494612867,7822533146131596676,-6330341519528133953,3496630915278277603>()) {
                           case -2107242303:
                              return;
                           default:
                              throw null;
                        }
                     } else {
                        String var5x = l(var8, var3x.getPath());
                        if (var5x == null) {
                           switch ((int)com.yiyiaddon.m.b.a<"syptq0hmc9lq0","EWFCAUoiqwoeYH9ySvI/sleDYNcM61IMgSe5hafgxNs=",-5287548550297863169,3862155508025627142,-1458896772263152726,-5787686302342628067>()) {
                              case -1924791621:
                                 return;
                              default:
                                 throw null;
                           }
                        } else {
                           var3[0]++;
                           if (var0.containsKey(var5x)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s265dxvnhiw7j7","eQiS/ybhjVletawZJs7rDzLISy8IQSEyzsXB9SSeXmQ=",4128715026623200445,7454778060287170768,-6665952679424814655,-2616417065917888477>()) {
                                 case 584126913:
                                    return;
                                 default:
                                    throw null;
                              }
                           } else {
                              JsonObject var6x = a(var4x);
                              if (var6x != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s20c55cy805k27","8ZE1dPva/lm8SAc7wrrXnI8wL5XdbkG7udNBg0MCX0E=",-5202465836361173840,-6514305237128980299,-7223590925927972665,-5789530297558350378>()) {
                                    case 858417108:
                                       var0.put(var5x, var6x);
                                       switch ((int)com.yiyiaddon.m.b.a<"skc2bcz9i3lw8","CPo1IPHxxq2sAVasqjZwGd5OqWC/R7NX0k5Z2xb+huE=",-2488857891478309909,8780090884044876388,-2327275140414667606,7019505800616239342>()) {
                                          case -1853519397:
                                             return;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }
                           }
                        }
                     }
                  }
               );
            } catch (Exception var10) {
            }
         }
      }

      return !var0.isEmpty();
   }

   private static String l(String var0, String var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s29l11r6hjn5yc","MTDU4Wq7KefIsK6KVvP+wsRL7sRU2FaJMritwxFts+c=",5985091139363792825,5147974180877857432,9202264237614242646,5099738351995563175>()) {
            case -124809594:
               if (var1.endsWith(
                  (String)com.yiyiaddon.m.b.a<"sd5gkech5mkmu","HPDOdXkZBRgmdeqBICtt9Bmaii2WJ5dcCRuECRVeJIBOX/dHE30=",-3083377016760954875,-3661840337141572323,312465486455436315,-4374477643495530369>()
               )) {
                  String var10000;
                  if (var1.startsWith(
                     (String)com.yiyiaddon.m.b.a<"sw6h5yvne4rq3","Fq3mIqLwtJ5nbyoR6C2e0LSsA48nDq7xe0FyAvQZNMIa/iK5a5M=",6457564400113793861,-8309923256222627376,661965970299168816,4140782016436207316>()
                  )) {
                     label36:
                     switch ((int)com.yiyiaddon.m.b.a<"s25slb6sje6odh","Gv3o+0tW515Fa4sRVWtP5SXfwytN3f3na11MdIvy9U8=",900598341128104905,-2986445624038508189,4183015739067931886,5558561777926015499>()) {
                        case 1225616267:
                           var10000 = var1.substring(
                              (String)com.yiyiaddon.m.b.a<"sw6h5yvne4rq3","Fq3mIqLwtJ5nbyoR6C2e0LSsA48nDq7xe0FyAvQZNMIa/iK5a5M=",6457564400113793861,-8309923256222627376,661965970299168816,4140782016436207316>()
                                 .length()
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"s1gc2qsy7vsv51","/vacc/RqZdxfVB1mDXRg3HCty/gdshHnvtY6Ax24Tms=",-8620904030076066456,6677271482296136114,-3316281313636564761,8137541430264761798>()) {
                              case 1156468951:
                                 break label36;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = var1;
                     switch ((int)com.yiyiaddon.m.b.a<"s3ce6fh8lg6vdq","8aFFAPCnYsCir7NvmefkXd/Lh/LeuXy6oTwDvEcxV4A=",-2629065351426017594,-8754855157986563961,840750339069850497,-6287504699658483191>()) {
                        case -1952929760:
                           break;
                        default:
                           throw null;
                     }
                  }

                  String var2 = var10000;
                  if (!var2.isEmpty()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s34bgghqwdgtg2","VcMblRDsXb5FIHEUDtIddbn86E3IM36GCjOJKHLo/es=",-2493552705635921438,7535839396192941836,4165081329658299039,8579831140358211257>()) {
                        case 1419912282:
                           if (var2.indexOf(47) < 0) {
                              return var0
                                 + var2.substring(
                                    0,
                                    var2.length()
                                       - (String)com.yiyiaddon.m.b.a<"sd5gkech5mkmu","HPDOdXkZBRgmdeqBICtt9Bmaii2WJ5dcCRuECRVeJIBOX/dHE30=",-3083377016760954875,-3661840337141572323,312465486455436315,-4374477643495530369>()
                                          .length()
                                 );
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s3ne9tqef21zxm","yjywYwQ6Eqj4G1emOURz7Q3+8Uq5NiAKJK4XLe13cXk=",8464449633285652231,-2985035173001758068,7933250357481706539,8195882709211285683>()) {
                              case 471255732:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return null;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1y2ib8pnqmy8x","A46byuIsS1QzoOfzMvPoUGNwP7Jaoj35ZldWAIbNQIk=",721968561203838431,1887170429444556812,5151503705579621871,-5202889220201141297>()) {
                     case -1828926315:
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

   private static boolean a(String var0, Map<String, JsonObject> var1) {
      if (var0 == null) {
         return false;
      }

      File var2 = com.yiyiaddon.k.e.e.a(var0);
      if (var2 == null) {
         return false;
      }

      int[] var3 = new int[]{0};

      try (ZipFile var4 = new ZipFile(var2)) {
         Enumeration var5 = var4.entries();

         while (var5.hasMoreElements() && var3[0] < 200000) {
            ZipEntry var6 = (ZipEntry)var5.nextElement();
            if (!var6.isDirectory()) {
               var3[0]++;
               String var7 = aR(var6.getName());
               if (var7 != null && !var1.containsKey(var7)) {
                  try (InputStream var8 = var4.getInputStream(var6)) {
                     JsonObject var9 = a(var8);
                     if (var9 != null) {
                        var1.put(var7, var9);
                     }
                  }
               }
            }
         }
      } catch (Exception var15) {
         return !var1.isEmpty();
      }

      return !var1.isEmpty();
   }

   private static String aR(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s10cll0wfgxmx0","9EcO6sNPVAZ59WjNmzB6MC3+lgYTN6GzsVctnKOTAsI=",-16472961396845900,3153218118099624352,-8035203099380190019,6847390582997236372>()) {
            case 1189670258:
               if (var0.startsWith(
                  (String)com.yiyiaddon.m.b.a<"s1wgjs25un9j64","CKOqE6KXhdQWBRJoQGL6KPUrVmv1JYCkBp2TAJPANq7KVHL+X9CapAom",-6585185684767879869,5720425123838179739,7425268780755905524,-5499079604998825651>()
               )) {
                  switch ((int)com.yiyiaddon.m.b.a<"sgzs0jrmpwgh3","5Thu50e+vLOA2FCDNGvWVSjEobq7N6I8Ral9QFo2lcM=",1325983961372995440,5513191785719965319,-7029086540216912578,-8850903392388986551>()) {
                     case -2062853005:
                        if (var0.endsWith(
                           (String)com.yiyiaddon.m.b.a<"sd5gkech5mkmu","HPDOdXkZBRgmdeqBICtt9Bmaii2WJ5dcCRuECRVeJIBOX/dHE30=",-3083377016760954875,-3661840337141572323,312465486455436315,-4374477643495530369>()
                        )) {
                           String[] var1 = var0.split(
                              (String)com.yiyiaddon.m.b.a<"s18daj9eoz11ij","szoarHTonmIoAakBrjBSfZxJIMyoNr4gvp9Z56mD",-8872480841827804653,901773894115930577,1246558112395084203,5160860641484969145>()
                           );
                           if (var1.length == 4) {
                              switch ((int)com.yiyiaddon.m.b.a<"s35inpn55tvcny","yiVBTBBZCeFgq4Gt1RufVw//U3fpnoDo+aXmJi6RN5g=",7783155545767290688,-1478616041343826905,-7076544620838636785,-6068961665417667868>()) {
                                 case 1259398426:
                                    if ((String)com.yiyiaddon.m.b.a<"s2orys39s9t2br","lZggZx0ZhlWTfQoC5eomspsHAv9sIAuKB5plg+W1jPupeTh7",726532913079754766,633544896864514084,7674489701546353466,-4393794485931207594>()
                                       .equals(var1[2])) {
                                       String var2 = var1[3];
                                       return var1[1]
                                          + var2.substring(
                                             0,
                                             var2.length()
                                                - (String)com.yiyiaddon.m.b.a<"sd5gkech5mkmu","HPDOdXkZBRgmdeqBICtt9Bmaii2WJ5dcCRuECRVeJIBOX/dHE30=",-3083377016760954875,-3661840337141572323,312465486455436315,-4374477643495530369>()
                                                   .length()
                                          );
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s1pt7v40dj3gjc","2tAfppctm6LVqIwiUGi2KNJxIQfVGMlod6aDsFsnnj4=",-5819212611375420795,-5783317067258189861,70713284459387309,538721738993573119>()) {
                                       case 1220284226:
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

                        switch ((int)com.yiyiaddon.m.b.a<"s2f3a44tnobyuh","Y0yPGyObUkHHi717YTlz98tg6JdKsdIWqyHc+1IxQ3g=",-3458165161053594503,-8123426529034690706,6680165944776253765,4487825966442495784>()) {
                           case 556225557:
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

   static f.d b(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s33nahcjcj6mk2","8ZirRvqbgppT2iFOif+CP/s/PqI0mRHDorFI8C/V6F4=",8115699743287549489,3594725555347055637,-1021909039332744059,-158838640890903224>()) {
            case 2109217412:
               if (!var0.isBlank()) {
                  String var1 = var0.toLowerCase(Locale.ROOT);
                  String[] var2 = var1.split(
                     (String)com.yiyiaddon.m.b.a<"s1srfvc4biwqnb","7BbQ1/vp7WoA2OFejuyzuiVMbhYz78EPl86tWjSZGWc3JhPoNW5vZXNCpT5NOW+q+CzEMPkH",-1590364999854187783,2715172361531661489,-8737022901306997692,5530108357413978641>()
                  );
                  int var3 = var2.length;
                  int var4 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s2k8gs7fxaidve","ImTdSUwFi5EXxY+ho3Q+9jeWElxfVZ/8f4s1UMUsMyM=",-3538104703358105684,2057454978606146476,-4111290661290650975,-6951823614370608514>()) {
                     case -859918430:
                        while (var4 < var3) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3jkpt92isd46a","GfrkuG6lkCDVC3poMj/dRo1Qos/TuG18hk6zoq+uVPo=",5401767413140537963,5686011201041623420,-2262169488500701406,5562376939533646901>()) {
                              case 384220366:
                                 String var5 = var2[var4];
                                 if (var5.isEmpty()) {
                                    label36:
                                    switch ((int)com.yiyiaddon.m.b.a<"s7b0as7vizsd1","Us/s8AfIJUUkXGKlfyK11q5nFzoBchYrpi8FqmS+I9s=",3739359705024825548,-8201775877214300816,-9103968824937541822,-2227644828292607581>()) {
                                       case 679132138:
                                          switch ((int)com.yiyiaddon.m.b.a<"s2xfjx6j4lynvb","uuBXTZ0hw6e87sxl31+17h/TNfNfUKTCn5Mc94AbfO4=",3867868114406672769,1462533091253275829,3959062100757494712,-656806881642678620>()) {
                                             case -1309331686:
                                                break label36;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    f.d var6 = c(var5);
                                    if (var6 != null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"sg2k0udbti0rk","LXOZKLQQ0uVN41ggl2Ex5GU7cfMPM1mZiQlb9uN9/Os=",-6524993069396219469,-7704256387671296238,-6837852448158075660,-3572698903468518081>()) {
                                          case 1153856914:
                                             return var6;
                                          default:
                                             throw null;
                                       }
                                    }
                                 }

                                 var4++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s2x24qxynxxs60","DzrAh74dWBMe436Hoo1dnwsHfgRrt7odIHtVVJXUMzg=",7614243199832669364,-3496213894601485748,3606554505987702248,515199289611490654>()) {
                                    case 1889373242:
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
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1vkxbsnjluve0","acvwpujVVIY18dt9KXMB2GzqDb+kyrau0YYTKbXD4pE=",144181957453667099,1963272218269872538,5595533258681886472,1237011505306854546>()) {
                     case 2102134578:
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

   static f.d c(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s30wfsi1g21neb","iVRRiuYN1jCyqL6WCga2J0ju7Wc3zgXHCamLFQAOu/A=",114770716261356966,2507313263279771599,5594529938528726104,-7968283813664532895>()) {
            case -1163838336:
               if (!var0.isEmpty()) {
                  String var1 = var0;
                  byte var2 = -1;
                  switch (var1.hashCode()) {
                     case -2069982462:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s5sjwe22k1p29","TpJjWq2roHtMzMmWoReARXcGNlA00+ZphPEHDcrJUBDLki9BRNHrMjwe",591547011826795363,-4394341787967631217,-6829759065750812587,-3916854838068143603>()
                        )) {
                           label341:
                           switch ((int)com.yiyiaddon.m.b.a<"s4qudu615gd5i","JddcKGUTdl0KfJ8FEIRXn+fz9YF1ctXZ6wq3QH78Of4=",9037964379816472915,-6228275750505767854,6565842414883826115,-237765759356050756>()) {
                              case -389710786:
                                 var2 = 11;
                                 switch ((int)com.yiyiaddon.m.b.a<"s3bhfk7h1ew9pu","5a29SzYH0XgO7dOXTq0d1bpsM2zZNOqj0s/FBieSxjU=",2626407125838762512,-2850665257708154423,-857186126832233873,471824356698094682>()) {
                                    case 169925135:
                                       break label341;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case -1406316010:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s1ayd836x03vta","Yq4IX98VPQyQpsKv8wKjYOIC5s65V+hMAONoC3IUiRDjcIf6NgsSwA==",-7136552421375229153,9205017123695963214,5545528171240053739,-1448793398373361510>()
                        )) {
                           label294:
                           switch ((int)com.yiyiaddon.m.b.a<"s1h73vamko3snq","iKxVxWRXjGdXGvlWWTTRnyjwzMDPf+WkESm6CRKPdgE=",-5723829547375264673,-6434925854108818265,-1655406717233902894,8914588854007359024>()) {
                              case -791943098:
                                 var2 = 16;
                                 switch ((int)com.yiyiaddon.m.b.a<"s236tj1tw25zzx","SrHoYj97bhhTIW7J4YLjN8iI51AxFCqfU7aAlld3Rho=",-7198786554210180139,7529535383580086344,-5087312688252585844,-8974400572800981427>()) {
                                    case 1799039897:
                                       break label294;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case -895679987:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s104bbg3dn39wq","xeuHUTGbBUREV1WFBpk6oMLFj7dW2E5PguUIlGwt06+XYYWlJ1tW0Q==",-8669515438967735020,5416666576332718510,6166809372434724450,-9055803569122597876>()
                        )) {
                           label244:
                           switch ((int)com.yiyiaddon.m.b.a<"spscjdxq6y9n2","MYi93PYkFNwTBfnH7JFg64KiR27TMD3K3N+wlLb5NZ0=",4360828690801976544,-8205589921158015280,-8200617072094612585,-454182093994353678>()) {
                              case -1034256519:
                                 var2 = 0;
                                 switch ((int)com.yiyiaddon.m.b.a<"s36fa2ov7sd8tj","xEua6+q42E9GEKo1XF3FEVhITv5kacv+8JQlKmGAHNc=",429944827541697079,-6230294833289371568,-5648086373059000860,-5400192686228021669>()) {
                                    case -28429550:
                                       break label244;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case -891207761:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s3ntq5mb5c78lm","Z9Jqj9Fo25ybf5Vp0VF6bancPQwqfX+KVnv6xWlmhTQSnQvhRLzCpw==",1915374711372642296,-556514861308023987,-4582662158365298563,1849624617572753477>()
                        )) {
                           label247:
                           switch ((int)com.yiyiaddon.m.b.a<"s12qtj6w53r1vp","PJ8DGB0+NxF/OXxNsP42LLmuF7w+buQDjgHgThX/FUE=",2328735245314610570,9085020005146637812,-375730704490643714,-9185306490523423973>()) {
                              case 1167606241:
                                 var2 = 8;
                                 switch ((int)com.yiyiaddon.m.b.a<"s39yb8b092xyiy","8bIbq7M3mmmMxQ/tJnRlBQBgF46/gPR8e0Jc8GCfCZ8=",4744849107318807914,-3223211559782934306,6334994216230000143,3884751674261554790>()) {
                                    case 464209092:
                                       break label247;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case -787736891:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s5o7qtwctqzzp","WIJBThdccCkCP9Y4uAJ+oHhDyZRFRTUFfRnnLD9hEceWFZYrRaMOlw==",5415837351750373500,-3725465092853276684,-2532173234367135322,3931691508338632060>()
                        )) {
                           label276:
                           switch ((int)com.yiyiaddon.m.b.a<"s2deb1alfa5buj","hAbK92DwNMyorSXP4OPT9w8HLTK8MOKVFsVpMwMLOj0=",1422124830059947943,-3230881849426368948,-4037398486141879360,-1680499605878564254>()) {
                              case -494994595:
                                 var2 = 26;
                                 switch ((int)com.yiyiaddon.m.b.a<"s3u7qn0nllg948","mWGEMRGpvr0Jd7HDeDUrJsWUdaBVA735l/WEzsEQzq4=",-5197732167285262340,-4550405149503639284,-2556909964586262221,-4952028865452093392>()) {
                                    case 2025357718:
                                       break label276;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 20908:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"suclar7mi7e7s","SG2yyCCd3aScFM2CkeORxEzqiZURHKIH0KrSijPS",3565956051377447563,-2220826267563660569,-903251417377423335,835154083734765626>()
                        )) {
                           label321:
                           switch ((int)com.yiyiaddon.m.b.a<"s1ljxmpj9yj4sf","KZdHt48hJocQOX+J2bnUwAtNQ1Z+ibJWqR1Pdp5EdLA=",8423906053134427781,-5888685758969296529,6652559086081922073,2705961547300170367>()) {
                              case 927149471:
                                 var2 = 31;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1lsd5kc6bc9w","nM6EtvKbo81LGi8wx6ueiZaOsSNMpB82ucG07P+64FM=",3993759350913727755,-3223855071990402231,839477191638957770,-2117393110422597706>()) {
                                    case 520715960:
                                       break label321;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 22799:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s3qvk2mekoqira","beFeyLdrHhJBTl9K8dVGMsSvlNUydA3FjhbEf8PL",8801752088178845618,-4977480014372621388,-5743362589452723201,-1789552240523173985>()
                        )) {
                           label212:
                           switch ((int)com.yiyiaddon.m.b.a<"swb06317fsg6s","nkWAd+alyjrSIrFxVmliEsxJwB2kg7w7/MvaiZOW7ck=",8464915733549635826,7559388304035033966,4470641197416387985,-6790790055686664532>()) {
                              case 952343432:
                                 var2 = 13;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1r33k7chfhv3t","Gj9z3hBPJaCCZDmIzuj5ipzh0D9xwPkZox8n+ntuCKI=",-8545493152822111302,234878594239346742,1705751992383670541,-2089385944312639002>()) {
                                    case 1185450727:
                                       break label212;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 26149:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"sf3nonjf26inz","4AzhQoDOli/1+36wFDWVBouRQwxvDM8gE1BbjSAg",8149051137536817274,-1188216578832259411,2694348746672752758,4497300373009585157>()
                        )) {
                           label215:
                           switch ((int)com.yiyiaddon.m.b.a<"s1rsolmk9tde78","F692AYjm0Xl7kTw2Uqh81WekCkaGBP2FoDfZcik1OZc=",2869177284643810152,-1100961414450347323,-3883344494088854433,-5368932409911147560>()) {
                              case 339986439:
                                 var2 = 5;
                                 switch ((int)com.yiyiaddon.m.b.a<"s3vumjhs3i2rpt","PaUaE7GT/9oF6PgwLJASd3mF7GavayAsJWOiI0lzOPY=",-8072861438278377330,-6455928651612377636,1555102028446003056,-2427775185682974169>()) {
                                    case -1020046064:
                                       break label215;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 31179:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s3lz1v5q09u2px","vAdJfZo+lpWMpojG+GST0kHYGdRdhtXU2QXTzrjW",2583108663016536104,-2022239254046100127,-3328894010142439391,-8661797145808253418>()
                        )) {
                           label286:
                           switch ((int)com.yiyiaddon.m.b.a<"s1zv5s59lu7i29","0wIPdZzvfYQXfrIMOXiRR715ATnnFjzI1AbPQioY+EQ=",3301180549880325480,6395987944575289715,1259302044207544926,-7061451810142843118>()) {
                              case -1251645905:
                                 var2 = 23;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1pu6c3mp94qav","0y34fsnvO/dt6R+dBiR6nQEf59OOOSjQd7xiMSxXSxQ=",-8643353125332023897,-3094936554081831489,9217637992193336072,383151864986548407>()) {
                                    case 1151777386:
                                       break label286;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 96639:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"sfjziklkl6yyn","sd/JTXcAos/b+dEjryVVrka5iHki9lUzjIAwLpGl+z0Vdw==",-8623474907325604398,-3566670856598614351,4106407525965335083,7888389120696908189>()
                        )) {
                           label312:
                           switch ((int)com.yiyiaddon.m.b.a<"srl0s5t53gpsx","fH+sFR8IwqwbOwn2SEVyhZw9YY8zxsakaDhMB8ErC1s=",2451513557064903090,3103457183783830647,-4900602502788571336,-6532709241462896506>()) {
                              case -1026747841:
                                 var2 = 22;
                                 switch ((int)com.yiyiaddon.m.b.a<"s2fuquu676z94v","q2FfYHUbk4IYMvOFxNx5WxwFs0IVIq8YP2u6m+eL95Y=",-8849300430152065874,919154121286219099,-2284928628570568700,800064146947728174>()) {
                                    case 328037057:
                                       break label312;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 96960:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s2cfwi41iduq5i","1tdPb5eiIfXTH8Vekj4M7v2/V6a41r54PN2+4RlOdGZDHA==",-1224694436484444583,1536593605413320917,7694018744407596501,6912640932316623095>()
                        )) {
                           label308:
                           switch ((int)com.yiyiaddon.m.b.a<"s16m7wa03dztcr","eN3kIFaVQ//7aIiJKb8AYKZA1tGBuBUXmuAi4QSOqrc=",878685805948392315,5311420325624329323,3732646256419887587,-1554879357717953156>()) {
                              case 673310098:
                                 var2 = 18;
                                 switch ((int)com.yiyiaddon.m.b.a<"s383cmm643jml4","7/XigpLsa7+/bCcmWur/fQ6hdcB2A2mr1mCkzpdgmbE=",-8183199835538402842,2837817089966126536,-4945537965611937040,224209265086759564>()) {
                                    case 153342852:
                                       break label308;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 101137:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s3a33iy96g32o3","DpBfSPy1O6r3/Wb9lzBYWzjwWaIgrSnJPhYD3pbE2L5R0w==",7438193637513599196,6447578438057463530,-9140658376429112720,-3000010813926062682>()
                        )) {
                           label218:
                           switch ((int)com.yiyiaddon.m.b.a<"s17kjqsgbsq8lt","yY+yKIwRvuaI66xKMwDW9E81VDCncbHwPnb7rJAccCc=",-5720984906900717283,-4621031238310056933,-8723311873904942279,-5198568293292046626>()) {
                              case -1470356562:
                                 var2 = 19;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1mv416r0hhn53","ayyti3UY002GliWMjvQafHcnj9bOWJ+NCLhVQECjYNw=",-7723148022893434324,-4447057535565702672,-4925970627622414317,7449948497930177672>()) {
                                    case -62744381:
                                       break label218;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 111965:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s5mn5b36ykv1j","aZSSuDNf/kaLdklqSdc/9Scir7zAFnP3BmIQO61DOyaBQg==",-8661720929179009933,2675304191439615183,-8524223769236030188,-7013659654737903287>()
                        )) {
                           label324:
                           switch ((int)com.yiyiaddon.m.b.a<"s3ato1lu3k5xcy","IKTe4YpmJmCVwlc1QlYl1ZkTSgPeVxc1uxonCmLQY38=",-5563984159490325539,-7852549394429807679,-7746916424362709017,-5943110602716765907>()) {
                              case -1143734204:
                                 var2 = 20;
                                 switch ((int)com.yiyiaddon.m.b.a<"s211vv2ufov94c","hKHlyqZvKIDKInfSwWcDg3UBuyEXp42Ea3eoCAmVA0A=",-2997501850577583105,298103849014144581,14287228446256906,3511147884550805>()) {
                                    case 95086417:
                                       break label324;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 114101:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s2r1y0w5kdj1ji","aWfOKs5ioKm7LYawUJG0aD4ZEl61uwEivZvwHN7LaYG9Lw==",-4485241566747383055,6345737894843924311,5182382752990623011,2874064420920448760>()
                        )) {
                           label250:
                           switch ((int)com.yiyiaddon.m.b.a<"shnfrix1iu27d","d/gMhm2cmXRAndPXBIG5O3YSQz7VU03dyZIWtoUGDko=",-8172908162262700204,-5919441954303531365,3242873670395152076,-4713575598799007221>()) {
                              case -2027455110:
                                 var2 = 1;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1ww8u5zdfl88l","vd23jYyC/T+75lx7OVdif31cRVbTT4KJ1K72FydrgKA=",7966529252824214459,4814921308887826467,334006672949797824,1523926962786822040>()) {
                                    case 403166198:
                                       break label250;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 114251:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s3h1sq1eomnr84","DGtJqMA/uVgbAdYw5kBICq75Krspf6ZUjDuEO5gkTyAlBw==",7428130831407030818,2424572304602547394,-6541317932106106521,-4726501955008219831>()
                        )) {
                           label316:
                           switch ((int)com.yiyiaddon.m.b.a<"s3afaqjslc6rk8","9N5MpFjYIOFJwuwx6HgVdeuptalHZ0Eo+UxmT70Yrac=",-8229813854516180444,-433269326619648160,4309397250079293619,-4906477370432575075>()) {
                              case 1525152900:
                                 var2 = 9;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1fj7nqow17g4i","gTPuDr1wEX7S3au8Y/oR5wvHOl4VpPWbg5nybyd66wc=",8819941201153609298,-518613180832044141,8831244307995630719,-6289598920623498652>()) {
                                    case 1234648352:
                                       break label316;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 117724:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s2ysyoi7h0gqvu","KO56yR9qT3dK9hIA9FwCGjFCMCM6h0lyKKewY9KlWBrDCw==",-3157812409858771539,3281051903181149870,1246013594544249195,-6004760086925180133>()
                        )) {
                           label221:
                           switch ((int)com.yiyiaddon.m.b.a<"s3ht7tdgvek4vi","3EWLuYyOwWTZ6hZ5NwoaN+qLT0XGpGBCwpQyRGJkIWE=",6802924371194229862,6967557817515197935,-1988933589795406388,2823229461802243843>()) {
                              case -322756739:
                                 var2 = 27;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1e0n6b4apptct","ySq6XgFovfC7jVLfW/STBEay/1SYtzZb8I9DOlz7tnQ=",-5586825194501013111,5959728982105923026,4510110255771394510,-8352726832365206092>()) {
                                    case -1299479906:
                                       break label221;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 118672:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"swfo9vylzjt4w","+z8euH4s2SawUdA3GAGp2yJmsJ9CZwn0C5K0x9tWPtHjfA==",-917537274838294543,1236283789428849681,2830185784359763709,1654290769405499102>()
                        )) {
                           label334:
                           switch ((int)com.yiyiaddon.m.b.a<"syt2vk8c72xmw","fxiw2/oIFx8yERvjEXem7kDHKJ8HDWvEq0tpRnkBm+s=",2425843174195504730,-7178873604050492849,1008049285057126212,-1328194185376583796>()) {
                              case -1100816553:
                                 var2 = 10;
                                 switch ((int)com.yiyiaddon.m.b.a<"s2zvfe27al482g","FO4NNX1QFSrFFkwJ911ZLjCLduv1QmAaiyby1q/wC8s=",-6409144850051433216,4098077220408092046,565483348459651755,3532101742869706183>()) {
                                    case 1273847408:
                                       break label334;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 670973:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s3cyixac0o07ye","5YwGsVhbxo2HKMoTDxH59IG9DMvDyg6M4mAm5cEDZPQ=",-1290256461457812199,7126033788274940060,-7441255171539556507,-1597954757346837917>()
                        )) {
                           label272:
                           switch ((int)com.yiyiaddon.m.b.a<"s2p19or0vzb6o2","bAOdGhagWEhQ3Gci33K3wtnOaKlBbVl2hJmjNXrEX84=",-4754824239003899589,-1934481953962020269,-2611105497694296441,4401484247195043741>()) {
                              case -1652373714:
                                 var2 = 33;
                                 switch ((int)com.yiyiaddon.m.b.a<"s3iztep8330670","LtKsNSnjGflErWl8+jpsKS9GQTh5LKy45mBwjQTRK3Y=",4127965188967786103,3055547561958492462,-889485583276523098,-7079403149268600444>()) {
                                    case 930306690:
                                       break label272;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 671543:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s3mvv83zdp5x7h","0KKpNMwpy0obHHPdneVm4Ncknmdna1acsw1EVZXgSSE=",-5214013059927391841,9117161100326080292,715420318331651735,1620389210051750238>()
                        )) {
                           label236:
                           switch ((int)com.yiyiaddon.m.b.a<"s1hwhzgtm05yvg","FVa+3B06+7fbErSANQxjTMLkM+pVWoehyOsviZ1sooY=",-8767365758410934543,9164527002724780560,-3530908245614865315,-4318865334043036789>()) {
                              case -1765772202:
                                 var2 = 32;
                                 switch ((int)com.yiyiaddon.m.b.a<"sksl5omy4qg85","KKkW3C4GyFRbfpZLYNshVX+jhLG5aRWehZPqPyLXkUs=",8697408752457822883,2769081279524141795,-8519311313987938148,2477081767536309407>()) {
                                    case 438000935:
                                       break label236;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 729594:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s322ijyp8x9b8s","aWgLSydE/Hmp6mBEpHcl6Z+VXyImNYjRCLdVESYdDWQ=",-8086299491711370314,-6632339684715443403,-7993482789434634449,1676498080208040686>()
                        )) {
                           label257:
                           switch ((int)com.yiyiaddon.m.b.a<"s2pfrqvsysofgt","9ZMGRhg59nAjCQ1qK9wj0BUo2+bSv72fGYhzSZVzgdk=",6752509521424380760,1088532076517493310,-3160088460841721642,-5096549066431608406>()) {
                              case -157834395:
                                 var2 = 15;
                                 switch ((int)com.yiyiaddon.m.b.a<"s3g3u4w3imi4pb","C3OWerf0I47SuuZs7vew6NodJM/UmHWKfeGwn5CUp6U=",5100876895897062455,-6065365679524440390,-5806380344156169799,7811629712881571061>()) {
                                    case 506565302:
                                       break label257;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 730164:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s2j4ag97zxgpkv","8HzOne3MnUAI+RHCBPV3efPFJKOE2YWWaNOQzTXQBds=",-817156890501434690,5931078846901040999,-2366222787248936608,2593575076577799684>()
                        )) {
                           label230:
                           switch ((int)com.yiyiaddon.m.b.a<"s1wixgwhu07fk7","VeyVzlcikrBvthZa37TXlXQVPRrKTaBN+hm1F6thykQ=",-6896694708583169782,4903477689437499155,-7675985353954649571,-6632405440636524045>()) {
                              case 1522499677:
                                 var2 = 14;
                                 switch ((int)com.yiyiaddon.m.b.a<"s333bfcvkkn3ly","R7dr9wmMUIwkibLsPSVfqdl74Tf4iAS6YmDcJLErY28=",-2038458127109247326,-2639047489644668904,3059704306614423952,-3920544211555149944>()) {
                                    case 571914495:
                                       break label230;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 833444:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"ssxn45oy0r8i9","mHmK91B0rdO9v5PLkHbVGjOFdSbNsC734tFPtcT+8uM=",658248053303225785,-108504465728999804,2433351627664905567,5719366684012740486>()
                        )) {
                           label289:
                           switch ((int)com.yiyiaddon.m.b.a<"s3qfhgcg52bk80","I6VwYf8+dcoj/LxPQAPgQlwwX6EdJ0A0GHCxqJiJ0K0=",-4685965272966484022,-769408655066105709,-8655880121985838611,-4679929204483087281>()) {
                              case -856580943:
                                 var2 = 7;
                                 switch ((int)com.yiyiaddon.m.b.a<"s3a122bttzo1w3","3UNEOpK8QhXyXuKTORHnXM8wSqRL5BnfFHM0KBu+fAA=",2991249295819609611,1882202131697002371,3719633811751812993,-6765737327246474966>()) {
                                    case 2127776469:
                                       break label289;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 834014:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"scfg12v26yp68","Yz/Z00Op8MyKaX/oMyX2yb7NukGqVDxZFluDrQCRcfU=",6419255208666379644,-6627200297727208197,-6437374071377223571,5386260084756795926>()
                        )) {
                           label301:
                           switch ((int)com.yiyiaddon.m.b.a<"s2p8rmv1bb614o","6jJetMAdJpoxnngldQc11EKRJ2O6vgsW6gRGAXu4MlQ=",1033473921656066414,-2215020125978862773,-7132773395654186145,-6596250573283280988>()) {
                              case -261016310:
                                 var2 = 6;
                                 switch ((int)com.yiyiaddon.m.b.a<"s33apmmn6m7c6k","oF+4oMqjYOnJLRTrf7x4POWDaMVN5RMfymIYayAwkJo=",-7568589102625496779,2629426823170289817,-859236666765906515,5007737406092338840>()) {
                                    case 790091197:
                                       break label301;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 989374:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s1on6y1bmleku4","JpagFb+5kK2dL6Nj/J+Zus51KQ3XDo4h5iHa7VfApm8=",-3781122006580431217,-9189372665716616444,-8233086859771363958,-8276130793821268018>()
                        )) {
                           label240:
                           switch ((int)com.yiyiaddon.m.b.a<"s1oohmuk021dtm","HbIRAeeKE5deaX5fzJMrf8D13r85EFNYpVqeWvUScqY=",-7346510785041189806,9132660193936893652,-3180186596403068201,-1650833675931834881>()) {
                              case -1568448738:
                                 var2 = 25;
                                 switch ((int)com.yiyiaddon.m.b.a<"s3rb214d9nlj4i","ZHoeOrP60f+NS08/WNTMsYN+nvGQgpwPbskbb5LOZRA=",-3923713552716916113,-8998129388764180728,2174948914259755503,-2693228311498797790>()) {
                                    case -237778697:
                                       break label240;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 989944:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s9qc6jrl3qsdj","3AzF7FgKYcvyvGzDxH/S91Ub/DbWIFch2cI0FpEYXEI=",4507489122398866759,-1855024600299607046,3623787850906835523,3445402603434793797>()
                        )) {
                           label224:
                           switch ((int)com.yiyiaddon.m.b.a<"s2bwhliix9qr5n","LtUNz3bx7nwqYfeue/AMD/77Zp9Ay7HCenT8ezruAig=",7686078284189059321,-6316527503396087514,8268139859689662951,-8410633537234563211>()) {
                              case 468675736:
                                 var2 = 24;
                                 switch ((int)com.yiyiaddon.m.b.a<"s2y227taxeim6o","RA2av7N1ijSeFvMom++8LrN8uRW3T5iGzFdnyz4BIoI=",-7769420034557000843,2922713390804779500,6859473648582084088,-7448737871648150697>()) {
                                    case -1519496327:
                                       break label224;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 3052990:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s1lk7qnn9c4qmm","R+8LgX9Mmy00uHRaZdarPLLvg+zAKDVfPu5yve6srbo/aUOz",-7134917871268237410,-1658515273319860115,-7493916762473756029,3330700890620169777>()
                        )) {
                           label233:
                           switch ((int)com.yiyiaddon.m.b.a<"s2xya95xc4v2d5","/vZsmd23h4jdhJo29eG3bCjqdKFORNFPrf2RD2rcK9w=",-2108530904344700516,8198999841269317250,6644055222464411861,-5568431693254741860>()) {
                              case -1284374366:
                                 var2 = 2;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1nclh45gxe4o9","1uiDuFyyStafJLfdFQiLi8GRM/joVP70b6abCgOPFQE=",2512928452460630066,462908293357694898,6060794980665416218,-6759230615965966736>()) {
                                    case -966397801:
                                       break label233;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 3089284:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s7pn3ye7igh3j","4FXidtI+fozc2m05C1baBzwUSLWK5TsopsYLn8E5Fq+UoIsm",5392715810264521878,7317950209600608567,6369689269668884304,-612992013234331691>()
                        )) {
                           label305:
                           switch ((int)com.yiyiaddon.m.b.a<"s1p6y61gcsxbfz","pmTc1EZgwc5rAIWu3yfDWjWp2yLXB1UNyhmyUlyR3CM=",1705607695075130136,-2716908571227156323,-6282641491140974708,4311390614284519370>()) {
                              case 1802632404:
                                 var2 = 28;
                                 switch ((int)com.yiyiaddon.m.b.a<"sb7s0s6s51vek","m1Bh4ZL4abdhxeaznLioOqm4s0V0pNw2AJCoM+x/sdE=",521358553939013393,-5086046458560807810,7402988015529688338,7629561591167852886>()) {
                                    case -306048377:
                                       break label305;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 3135355:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s6vq6hbmmxdjs","fM1qkKz8QgPMeFZA/kK0rAWheNAxH6ALn4INEHeHSGPSqct9",1524103133428648607,2810065654912857370,8022302792642463982,-4884255322794243937>()
                        )) {
                           label254:
                           switch ((int)com.yiyiaddon.m.b.a<"s34xr2psq8qndy","r178oTBJsCoVEEjugodJD6GoHIPbfRlpye26PEAhpog=",3364091070056372456,-1308100830640511213,4720131062858237399,-8038029497861322620>()) {
                              case 1346306745:
                                 var2 = 17;
                                 switch ((int)com.yiyiaddon.m.b.a<"s2elei29ej7z8k","YNX8llJDezcXXETm+Pugt1PfnRS6p8nAYkw1C4BJFI4=",-1246698398008907932,8254330450496792202,-6316890453725080191,7676549349359978612>()) {
                                    case 315146279:
                                       break label254;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 3154987:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s24f1i9iueb19w","OimtPH7gUlhLbr/FHk3aluvQLU6SIg7VbN73nBR8wnb6On+m",5198025482475527942,-9132999201913472680,-622222992927667893,-5960191201283097113>()
                        )) {
                           label209:
                           switch ((int)com.yiyiaddon.m.b.a<"s105j0bftdohi8","0r7ry6lLnXeruc/xem+ai4GHWuXU3NvZR2294FhxfLA=",-5381437172933783449,-4689014459152894013,373256545275820091,-2153527746045919582>()) {
                              case 405676666:
                                 var2 = 30;
                                 switch ((int)com.yiyiaddon.m.b.a<"s32sxyorn3xmib","myAKxSC8Wl6tQ+QKsrhJgTdWxeHjPR6X05I89OI+I6A=",8965789960219518862,693831101268229005,2444723002909366275,4012811704970322642>()) {
                                    case -599487533:
                                       break label209;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 3195132:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s29tn4mgehb7j7","xji5w/WmkYn9akvl4ab2cYm/MZFBPEJktOdEmuZjZy/77hb3",7815710353214740610,4178022221042338111,-3358889534247546266,-773710087288866198>()
                        )) {
                           label297:
                           switch ((int)com.yiyiaddon.m.b.a<"s2x92kis52fu4b","evBczBEO4oXtxcHwuCIauKr85gqXx7p5iqO9YpFhwn8=",-6373692610167185176,9176295120148588328,-3642611200218692054,-5197502655158140436>()) {
                              case 1462033343:
                                 var2 = 4;
                                 switch ((int)com.yiyiaddon.m.b.a<"s2wbvb2tpxcv5h","Ifh67E2mdiNFetCbGLMnBJxf0WrbwdLtPDtr+Pw+EHk=",-3613435573391933241,8164100066817146282,4795901404857581787,-8653799959418713288>()) {
                                    case 192716672:
                                       break label297;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 104592195:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s363a3xoun3ufm","2nbFsKY9q1bmh/nfeMTgOOcm6cE+KFmj+4s2q93MaSbnb+Uvv0M=",-1165424088394512634,-485523016143760747,4887681466262073992,-3223714361430778399>()
                        )) {
                           label280:
                           switch ((int)com.yiyiaddon.m.b.a<"s3jted8q0orn91","JGWforHGE5NvGHzLiOvwW+qTrDENQSrelAtOYn8a1qQ=",2792283764788319191,4783792233803914853,2088640916239508794,6083399047009391863>()) {
                              case -575117412:
                                 var2 = 12;
                                 switch ((int)com.yiyiaddon.m.b.a<"svlq7u2xttmen","oX+0aVSCF2mrVeS/MZm2M066u+RzxvYjXCv6KQ9jIFU=",-1891169751442958240,7825284773902247755,-3128877734300355571,-2358176136423217953>()) {
                                    case 1186826216:
                                       break label280;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 326373439:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"sq5h0yco66xqz","3atwmF9kwo5SLuqpl0Eqgae+1Q7Drdm/oWtpfx1U5hfurzRcJpieEObV",-7859798370264160386,8277189803771384585,6537053111384879665,4788182934979954995>()
                        )) {
                           label227:
                           switch ((int)com.yiyiaddon.m.b.a<"s3s2284tpov2t6","GcAkTlVj1dGUhwCrrWl4YRbU2Vg/PGwUNJ0JSW3u+NY=",5884798025253179677,-8527810218166037454,7280011005935719350,4602371450663979472>()) {
                              case -501375019:
                                 var2 = 21;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1aa6dmnouxh4g","NbXlPxFlL5AOS8o5zAMVgvp//vZ9hKQwtuW+T/v4EZ0=",-5281047020070140359,2918043615402777161,2278748848205496656,-166853826129524568>()) {
                                    case -455445020:
                                       break label227;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 1163924198:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s2w6meqxf0dnao","y/tPL37BXMy3SREGsQsM5y7pkVW5S0vuTzDE3XljpJYJWw+k3KQwFvQB3B8=",3119464476912700855,3800150977342432440,-5245684810394222442,5377397221597771759>()
                        )) {
                           label264:
                           switch ((int)com.yiyiaddon.m.b.a<"s3ewkgto19giw1","5n5PFWw8UcY2wOzFzUH4xG0x+jOZnhKyjhr3E/UXsLU=",5444743691630290236,3122939245870116783,2671430035577061966,4255664356625640781>()) {
                              case 968597439:
                                 var2 = 29;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1aa8dacc4mqxw","6uQrKAq/e8K+4thV1iE//vTxFhRAC6BM2367Fxo7azI=",-6693402869352116382,5142308242494150333,-7085955414291410746,-4536225601523067049>()) {
                                    case -769473219:
                                       break label264;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 2005391392:
                        if (var1.equals(
                           (String)com.yiyiaddon.m.b.a<"s3tvxem4s337oj","CPjPkt+NCkcxsRI+uQfnkehS5DovXakPgpkmWKviPTs5LoAZ2APrM7m/xVw=",1537880086220541176,386061121706765518,7561849603825036552,-5235605818000491135>()
                        )) {
                           label260:
                           switch ((int)com.yiyiaddon.m.b.a<"s2cafawet5pu04","OVpmxbbhJM3NDLxpnDno39VgIGKOw4AsFVw626/aTIo=",-955203607028709723,-2746632143411655102,4783037349015206401,9017433921890401143>()) {
                              case 227203642:
                                 var2 = 3;
                                 switch ((int)com.yiyiaddon.m.b.a<"s378oonr034i29","mue53CjKuZxNu+NvQOhZOMR2eOHT6tKZGQdJmgdwIZA=",-9159713862372020241,8433659744032622012,-5461020340180387509,5171140489687776781>()) {
                                    case -1930822983:
                                       break label260;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                  }

                  switch (var2) {
                     case 0:
                     case 1:
                     case 2:
                     case 3:
                     case 4:
                     case 5:
                     case 6:
                     case 7:
                        f.d var5 = f.d.SPRING;
                        switch ((int)com.yiyiaddon.m.b.a<"s2aenk4kpbc27z","uGlxbomMdJN/r2o9Z2adYbWP1XA+T6Qsek7CC4hTcjc=",-4739966673784355213,-7072316936329997836,8978729256899861911,-907017507467162372>()) {
                           case -1177949108:
                              return var5;
                           default:
                              throw null;
                        }
                     case 8:
                     case 9:
                     case 10:
                     case 11:
                     case 12:
                     case 13:
                     case 14:
                     case 15:
                        f.d var4 = f.d.SUMMER;
                        switch ((int)com.yiyiaddon.m.b.a<"s2vpyoq7ojak2w","qLjKmJh5fA2WYwPCq6o+XAUPVo5BzNWiT9kvrERcvqA=",-594761012292169634,-8005306644833594556,-3192228179485962054,-5134487809761867928>()) {
                           case -791267980:
                              return var4;
                           default:
                              throw null;
                        }
                     case 16:
                     case 17:
                     case 18:
                     case 19:
                     case 20:
                     case 21:
                     case 22:
                     case 23:
                     case 24:
                     case 25:
                        f.d var3 = f.d.AUTUMN;
                        switch ((int)com.yiyiaddon.m.b.a<"s1cispzq9k8fps","/eCmuaQHbFKKrheSygOwldyyGh9edK203e+acfRN0Yg=",8233376393683629752,6038094645603402347,1273343113224245816,4228998635521216025>()) {
                           case -1766455230:
                              return var3;
                           default:
                              throw null;
                        }
                     case 26:
                     case 27:
                     case 28:
                     case 29:
                     case 30:
                     case 31:
                     case 32:
                     case 33:
                        f.d var10000 = f.d.WINTER;
                        switch ((int)com.yiyiaddon.m.b.a<"s2fhw0rfge0c1i","jtKnYx9CENuwu1uUa0iNIqx5y+rKre53VwMklHDbmvc=",4115881374860536348,-7562387527387161869,-2101178586862433342,7726080186352793688>()) {
                           case 1566688:
                              return var10000;
                           default:
                              throw null;
                        }
                     default:
                        switch ((int)com.yiyiaddon.m.b.a<"smr1oaxdhmqcu","SxJfg0a4mih7qjpGgjtr/BoIXyod41qOlKXr6ZQoWX4=",9069728984716244307,7279992007011939477,-3597079314179524218,-5098014061761408686>()) {
                           case -1327068768:
                              return null;
                           default:
                              throw null;
                        }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s10kd47cnycv83","NlgMLgyeB75Dia/diaaMyWOK3C+59Zfw4nDnNtdyxKU=",560220037754833870,-6651481265337524852,-6271785584302334436,-4792699312443037282>()) {
                     case 1659801818:
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

   private static JsonObject a(IoSupplier<InputStream> var0) {
      try (InputStream var1 = (InputStream)var0.get()) {
         return a(var1);
      } catch (Exception var6) {
         return null;
      }
   }

   private static JsonObject a(InputStream var0) {
      if (var0 == null) {
         return null;
      }

      try {
         byte[] var1 = var0.readAllBytes();
         JsonElement var2 = JsonParser.parseString(new String(var1, StandardCharsets.UTF_8));
         return var2 != null && var2.isJsonObject() ? var2.getAsJsonObject() : null;
      } catch (Exception var3) {
         return null;
      }
   }

   public Set<String> t() {
      return new LinkedHashSet<>(this.ai.keySet());
   }

   public enum a {
      RESOURCE_EXPLICIT,
      MANUAL_VERIFIED,
      CONFLICT;
   }

   public record b(f.d a, d.a a) {
   }
}
