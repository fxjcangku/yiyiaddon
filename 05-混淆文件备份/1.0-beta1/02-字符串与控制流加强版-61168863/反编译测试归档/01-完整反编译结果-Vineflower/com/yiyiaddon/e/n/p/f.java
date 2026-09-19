package com.yiyiaddon.e.n.p;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.IoSupplier;
import net.minecraft.server.packs.resources.ResourceManager;

public final class f {
   private static final int ne = 512;
   private static final int nf = 200000;
   private static final Set<String> R = Set.of(
      (String)com.yiyiaddon.m.b.a<"somvfzopnw5fo","B2CyywTaUziw6NcXIPhLmtFNSIkUfeOb0g7jSXeIiqWj5np3",5854892239832183526,-7662393467286871301,1959647847538624642,-4962367763688156008>(),
      (String)com.yiyiaddon.m.b.a<"stje1jznyvka9","rGs/qsRfXOyboUjrF/DUsaFwaM37GQVkUtTGjaKtSB1/rv3tD0M=",-7032249287457014328,4052600663362980390,290948162461838120,3872411497685445766>()
   );
   private static final f a = new f(Set.of(), Set.of(), Set.of());
   private static volatile f b = a;
   private static volatile String tO;
   private final Set<Integer> S;
   private final Set<Integer> T;
   private final Set<Integer> U;

   private f(Set<Integer> var1, Set<Integer> var2, Set<Integer> var3) {
      this.S = var1;
      this.T = var2;
      this.U = var3;
   }

   public static f a() {
      String var0 = com.yiyiaddon.e.n.a.bT() + com.yiyiaddon.k.e.c.dn();
      if (Objects.equals(tO, var0)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2z0w8ac239a9j","SKnobLgS9DqQhR7nxtxK8EZxuTvAt+elH4VPBMwK3OU=",1811146435979605787,3263802539894953697,1192912369042425078,9025217559626158901>()) {
            case -1138262604:
               return b;
            default:
               throw null;
         }
      } else {
         f var1 = b();
         b = var1;
         tO = var0;
         return var1;
      }
   }

   public static void cy() {
      tO = null;
   }

   public boolean dG() {
      if (!this.S.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"sd6bsukuo5uz4","IRvBBFZs5xj4ajjiHSAM5RkPlMqttmj2KZo+krgi1wM=",-5546986974276300124,2335129350803586457,2380553633831725268,7132052050141401951>()) {
            case 998703899:
               switch ((int)com.yiyiaddon.m.b.a<"s3m9eaz635sefg","Tkeun7BBTWQxO/oTv7PYC7mgpQfSNJ8Fy5Chx9q0HWU=",968598720775090344,5540846697036790289,-6043985684946124992,-1962932125305919270>()) {
                  case 1368104846:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s3q5xab6o0b4no","U8GTTwg+DmKjzqOzVIn2VNV5VGq2ovGKshF8Yww7HG0=",6558559197086881114,-8195173181770440679,-485141961703306164,-7976073500487870357>()) {
            case 298668216:
               return false;
            default:
               throw null;
         }
      }
   }

   public int l(String var1) {
      return this.a(var1).cC();
   }

   public f.a a(String var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"su0r8uwsdsvv1","LY9SJJyK2e7dVkxOfFMwoOSH2g1rQvNs+mMRM5BbOEo=",-43827371649002301,5062227794776193229,2255653746721151454,7615918466978008836>()) {
            case 1779377258:
               if (!var1.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3jx7khczc62av","lEFBktHlHmEktWb8Dn3oGK63suLy+pfHFhVLFYAEStI=",237118077309953538,1239394918190569829,-6752913971535264348,5894178982180757135>()) {
                     case -532478797:
                        if (this.S.isEmpty()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2guh5oflbd28u","NlL0ofdQqQHY5RIVV5KX0Wm/x7QlnB9Cl199kzgcWXA=",7386963339893261470,-8271412425257450220,825545507827003093,-2130078440008544351>()) {
                              case -1801507424:
                                 if (this.T.isEmpty()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3h4fb3p8bty2m","autTb+t6yvPe19XB9NoOJhL/SQYbqpt2/QY+YJN6Dng=",-633232711397421249,-2720774608426710322,-2583159187293488241,-7296775950866650096>()) {
                                       case -1033183754:
                                          return new f.a(0, 0);
                                       default:
                                          throw null;
                                    }
                                 }
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        int var2 = 0;
                        int var3 = 0;
                        int var4 = 0;
                        switch ((int)com.yiyiaddon.m.b.a<"s37vzpahqo4dtf","bMibrUmdNLU5/cDJnDER6ddbi2nEgLPtovTnPxFqpT4=",3333126944323816994,4967918941431493292,-1292721027384186385,-5414190840176917009>()) {
                           case 726753103:
                              while (var4 < var1.length()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1t8wkqbh4rqls","QoAkoMUQCbYTiAa3xvRc3y7PC+hGVNNw5F7yxuhU9Zg=",-1287688478851904587,-784863195184644803,-7592985337792017535,5798344296094652680>()) {
                                    case 190964544:
                                       int var5 = var1.codePointAt(var4);
                                       if (this.S.contains(var5)) {
                                          label45:
                                          switch ((int)com.yiyiaddon.m.b.a<"s2henl2g7wwy4v","Rd8l7ATwdXxSeMFRmuEfYYdzlbNG9pzW/TaqGCLj1ZA=",-1984778825524713099,-7417613629805595239,8426406146847949956,-7491918438271659256>()) {
                                             case 1186048694:
                                                var2++;
                                                switch ((int)com.yiyiaddon.m.b.a<"sds2l34y1cx0p","/JmkS9pVKk2CLLwyrJw+n2RgPmLRVIr15z+dzfa9jso=",-1618767278769528814,8365896391452765575,-3998810055345305760,-3202440675510116000>()) {
                                                   case 1144085523:
                                                      break label45;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else if (this.T.contains(var5)) {
                                          label41:
                                          switch ((int)com.yiyiaddon.m.b.a<"s376svv19138el","R/Kbjwg7MU4VupLJJI12UwlJSzZ8H3uMBCvxD9AglM8=",-9212666647541493452,-7161200027112411965,-2226752133215142735,-5913582578335138138>()) {
                                             case -1467003507:
                                                var3++;
                                                switch ((int)com.yiyiaddon.m.b.a<"s3cvgyiin14u5f","e17CXtSMFNhUvpXtUAJk9jwb9QpSLSkhY9AUoZubE/c=",4686323736535194194,-5780336822716168035,-2541016534523856388,-5174959267637633563>()) {
                                                   case -408817866:
                                                      break label41;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       var4 += Character.charCount(var5);
                                       switch ((int)com.yiyiaddon.m.b.a<"s2e3uqy4vl2qo5","zenDhHCXhalB4i0vFU40vPm6pwBCikaAHzvnJ/FRzQE=",-7524172203723618984,3294412302551583570,-2857525099030310428,-931645219958776368>()) {
                                          case 2002752718:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              return new f.a(var2, var3);
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

      return new f.a(0, 0);
   }

   public String f() {
      if (!this.dG()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3kbyrobe6u2hh","+rgXEDWN1z5DERYQ/Dnnt2Z48GWXI2t34HeVFPW/AiI=",-578035136327286407,-4956542116461279043,-2067276970965385935,1493875317800211044>()) {
            case 296099554:
               return (String)com.yiyiaddon.m.b.a<"s2rrtfdakog4bt","Zm2bRHyfQ1r7eYmf2nTjtHLKQWWHdRWPy3U/ay/FKiTIYdxtnDYx6EvW4Io=",6541755619674761355,-1749005307839533349,4572959495354992877,8361729906422935762>();
            default:
               throw null;
         }
      } else {
         return "" + this.S.size() + this.T.size() + this.U.size();
      }
   }

   public Map<Integer, Boolean> t() {
      HashMap var1 = new HashMap();
      Iterator var2 = this.S.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s34740ylmwufwb","CHrT3EkhQ+VJjxnzWm+fjX/8S/57XGWIAe8XTM/1PBc=",-2587072673491449678,1001422829048514947,1473730645363461098,-7416263696884862>()) {
         case 563817265:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2iilcptqyqg90","jrS/qMxpEYUfJ1kscsi2ZAwcawS19cpPdycyyActeLI=",2126159134936928888,-1885281082353678011,9001592609233496589,-8532462040307847089>()) {
                  case -1376982207:
                     int var3 = (Integer)var2.next();
                     var1.put(var3, true);
                     switch ((int)com.yiyiaddon.m.b.a<"s20c83dwbyb8il","ogzLqIq/e6A5ZuknxKCCzZ9iu8TgKofehZLssTjoU9c=",832793824212060303,-4693000928036924989,-6088442820936480146,6579933338979042837>()) {
                        case 433075112:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var2 = this.T.iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s123ego0x51ung","wol8wQIZwN7OnlSgbftIFQm0yTDB8wbvczCeI8jnYks=",-7576202048403292258,4517279891848862705,7485640920153046752,-399193727217980058>()) {
               case -359810354:
                  while (var2.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"sd3ngcmdgoo6","UPaVBVJUp6mudbuOTZb/88UvPgWeFYX42gtEtlrEURc=",-1147822178660834663,-491149147292580055,1642370501657838189,5601513294635677543>()) {
                        case 752124694:
                           int var5 = (Integer)var2.next();
                           var1.put(var5, false);
                           switch ((int)com.yiyiaddon.m.b.a<"s2bupgqk8pv7i3","3LqcYK+XL1yKkcHkGi6yeJ6uMYkQfbRFE8XLNAvlUpc=",-2878968573033764391,7716651062064392696,-6123298511626603691,-559205681519704049>()) {
                              case 367610831:
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
         default:
            throw null;
      }
   }

   private static f b() {
      LinkedHashMap var0 = new LinkedHashMap();
      b(var0);
      c(var0);
      if (var0.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1mytwm840bz16","oROXOgga88YYE8mAtJnG8E9Cw4vSb9eiBFEf5XgcayY=",668802216606730361,-6154413653665719442,-9053876225352710942,3744106883432320540>()) {
            case 1928483340:
               return a;
            default:
               throw null;
         }
      } else {
         HashSet var1 = new HashSet();
         HashSet var2 = new HashSet();
         HashSet var3 = new HashSet();
         Iterator var4 = var0.entrySet().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"sdg95ugpmhfhy","4e5GRj6BVHleeC1FmwXsx7XLj7nLuja9NEwzkElwD/s=",2082181000811950790,8509559951925257393,3172152348229919925,-7865947455252140660>()) {
            case -484804016:
               while (var4.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2lplqntfv450e","4kgRih1p8PSpRfpMynXvciilY+YUzMNsfo3R1zYnQng=",-6430765558146287139,3503636323538608956,-8112732412017467244,-8992298747377299419>()) {
                     case 271204731:
                        Entry var5 = (Entry)var4.next();
                        Map var6 = com.yiyiaddon.e.n.n.a.a((String)var5.getKey(), (JsonObject)var5.getValue(), null);
                        Iterator var7 = var6.entrySet().iterator();
                        switch ((int)com.yiyiaddon.m.b.a<"skhyaosqutyeu","B0dXcJ57E7OEZZgKlCNgRETv75bM83rBLfHOCkwJi9s=",731820835792217035,-6037605405006893157,-6236025476336852267,3948636697520007396>()) {
                           case 706463493:
                              while (var7.hasNext()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s31v9cn73aszdl","ThRI6BEGr1EEV0iW6aq9lfGBJTYvm4pnv2PVfK580sg=",8239159835442172077,76837960982012026,2536990483727276326,-5558661721130196291>()) {
                                    case 1795267677:
                                       Entry var8 = (Entry)var7.next();
                                       a((String)var8.getValue(), (Integer)var8.getKey(), var1, var2, var3);
                                       switch ((int)com.yiyiaddon.m.b.a<"s8002kiv0jppb","XC9xD/fnrE4zqde3QIKS79HhGEFzzDpAUVfxGTAIJKs=",8944899045391207559,4812710574762097229,-5532087650629609805,-6245523793165286485>()) {
                                          case 190291602:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s24mpz0awlskos","G8JNdVm27RjujOoOphHXFNoxkfNtIS205JkjfA/qwz0=",2664585732405621599,880305613555208602,2497609605051872628,4023343674150762411>()) {
                                 case -1307378058:
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

               return new f(var1, var2, var3);
            default:
               throw null;
         }
      }
   }

   private static void a(String var0, int var1, Set<Integer> var2, Set<Integer> var3, Set<Integer> var4) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sw67nbzwb69qr","0AulMXVC9OF4cBqPKoq41O5WSlf/EfTJjHKiappFN9k=",-7903838448618184236,-7243319097897407635,-4013303843728814400,8508611734504088762>()) {
            case -1796614740:
               return;
            default:
               throw null;
         }
      } else {
         String var5 = var0.toLowerCase(Locale.ROOT);
         if (!var5.contains(
            (String)com.yiyiaddon.m.b.a<"s1uzzkt1041203","uOLCrtrHNhemwkKD6dzrGDZQJ79ue/HmRvNQ6OY4+qGdO55QPI4=",-5462373894259780465,-7705376876751136504,1215854949716824815,4857152738642843535>()
         )) {
            switch ((int)com.yiyiaddon.m.b.a<"s19jeeigroqglc","ZHQfog63fdkQ0W2j+2qeC3fp7TlmBMzdQAcNfbb58WU=",-4910287755637793470,4834646735725552369,-6612972419778690685,2024169555031743924>()) {
               case -905422656:
                  return;
               default:
                  throw null;
            }
         } else if (aq(var5)) {
            switch ((int)com.yiyiaddon.m.b.a<"st5siovxsrhl5","YSjQuFhLXjCGrlAdejXPdbK/qeO5oPnIiXAumxREjb0=",5004990879354444725,-2273658964471901300,4026755130543153800,-3347357602183461352>()) {
               case 1975609154:
                  var3.add(var1);
                  return;
               default:
                  throw null;
            }
         } else {
            var2.add(var1);
            if (var5.contains(
               (String)com.yiyiaddon.m.b.a<"s3q6uns69gndt9","LdL5mLX90Bl/vO27MEDfeN39xlSbwY+zIj16C9dWfU+yJrKo",5097269804220997300,-7239928634272640695,2152476859584939877,4797556094605063701>()
            )) {
               switch ((int)com.yiyiaddon.m.b.a<"s3vuhxptdz57dg","e9c18CHWhdsbRcE+BKHrkPEgRnAPXdm8eIsql1b/WRw=",-348998888421826054,7331390210208116308,-968715391524729650,-6265960044811217098>()) {
                  case 1847629232:
                     var4.add(var1);
                     switch ((int)com.yiyiaddon.m.b.a<"s2722goqwfgxjl","2UJnFYz8xX6qqFhSLcyob4Jb90xd9rtbTv+3sGHYOXE=",-8280092665351319,-7797260517433425215,-4910832404702655496,-2627320522768150147>()) {
                        case -193268844:
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

   private static boolean aq(String var0) {
      String var1 = var0;
      int var2 = var1.lastIndexOf(47);
      if (var2 >= 0) {
         label46:
         switch ((int)com.yiyiaddon.m.b.a<"sayt1fnviaopd","V7fOy0s22WLUsIBNFVyiqNhcUvTwbTDupp3h2P17bgM=",-8975981207055912229,-8035258999721480051,5172296204278247007,6952249968660888291>()) {
            case 420266445:
               var1 = var1.substring(var2 + 1);
               switch ((int)com.yiyiaddon.m.b.a<"s1p33m6wo835e5","+Sx6yDfsKqDaqQBwsAakVO6y1jHwMovYhox4/WTW748=",3932122134326105675,-4085609691349395357,-3164166191013539890,-6913581438868335792>()) {
                  case 238261980:
                     break label46;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      int var3 = var1.indexOf(46);
      if (var3 >= 0) {
         label41:
         switch ((int)com.yiyiaddon.m.b.a<"s3e92trptd555","wAyW9FdBewRQhpMEoJbUFJIhgZZs1whEejk4M2Hf6OY=",2004917868373116335,1438709615324894698,8613573776902129934,5278394078389563773>()) {
            case 408177225:
               var1 = var1.substring(0, var3);
               switch ((int)com.yiyiaddon.m.b.a<"s1jdsthmyjtiu2","50RvyNs0F0FMRnM2+A/IBWStztgoWonA+OLCEtLK+rk=",-1714332973147225344,-6783699531795022143,5011246026905932575,-6945844652432866028>()) {
                  case 1408560941:
                     break label41;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      String[] var4 = var1.split(
         (String)com.yiyiaddon.m.b.a<"s21o36yd39ecj7","VVwKN0ulthbXT8NFC5F0MKrtfEOEaomw0W0a8GQjE629cFsH4ACmAPpCG0FjLpk6",-5378073792769771946,-8944501571644783435,-8869084859333778219,3075645576596528026>()
      );
      int var5 = var4.length;
      int var6 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s26bhkbyl4mpwf","L1UcUaq2y4fg8gtjXJl8dw7y/AgrQHhfmUl5zSL3KRQ=",-4641235269701551486,303124381216923178,2224947294253843221,-7810326311640350228>()) {
         case -541300403:
            while (var6 < var5) {
               switch ((int)com.yiyiaddon.m.b.a<"s19k5vy2aau002","Yzsj4VLyPcBBewWIACAtlthVh4H3aLpCoRBzFhVu/L8=",7971378684468032806,6869991572297628497,-4965775915943880531,1977322003437493085>()) {
                  case 1163028903:
                     String var7 = var4[var6];
                     if (R.contains(var7)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1bpt1usy5ytfy","12BYwIaYNMscfDn4dgmrjg2IYRmQeWfcHQJr4stXbWc=",-2873001112093461789,7072974533520147589,-2012994021192274762,5909020454282828862>()) {
                           case 941435321:
                              return true;
                           default:
                              throw null;
                        }
                     }

                     var6++;
                     switch ((int)com.yiyiaddon.m.b.a<"s358c23kwh9oip","NSzxG2/MGIfJlQHkpmWlEEgkbDCELFa5SBD9YNODzdM=",-4619615692238799048,4649203901725855339,-8983158261117135028,2993090672220204618>()) {
                        case -216591712:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return false;
         default:
            throw null;
      }
   }

   private static void b(Map<String, JsonObject> var0) {
      ResourceManager var1 = Minecraft.getInstance().getResourceManager();
      if (var1 != null) {
         List var2;
         try {
            var2 = var1.listPacks().toList();
         } catch (Exception var11) {
            return;
         }

         int[] var3 = new int[]{0};

         for (int var4 = var2.size() - 1; var4 >= 0 && var3[0] < 512; var4--) {
            PackResources var5 = (PackResources)var2.get(var4);

            Set var6;
            try {
               var6 = var5.getNamespaces(PackType.CLIENT_RESOURCES);
            } catch (Exception var12) {
               continue;
            }

            for (String var8 : var6) {
               if (var3[0] >= 512) {
                  break;
               }

               try {
                  var5.listResources(
                     PackType.CLIENT_RESOURCES,
                     var8,
                     (String)com.yiyiaddon.m.b.a<"s2ww0ewnqqpmaf","QDQcsA/1b5TwF+5XyCb3TOA9+aGPoaxb3JQSEeSy32Bili2l",-6839658340609346180,6498001339943989166,-5923574081983939311,468623435833811742>(),
                     (var3x, var4x) -> {
                        if (var3[0] >= 512) {
                           switch ((int)com.yiyiaddon.m.b.a<"s8u9x2ib718pm","5Qk1QeZCg7mEKROYkP8tFkw893zb8/tSvbk1sZbPnjY=",1280898885930397478,-6884430298510964853,-5494154768317406842,5029144162424773529>()) {
                              case -201101808:
                                 return;
                              default:
                                 throw null;
                           }
                        } else {
                           String var5x = l(var8, var3x.getPath());
                           if (var5x == null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s305cm5wsg5eyn","nUa1+Avu6bqga+66OupdJmkpIQJscf3d7hSFgaYYwhs=",-3054701738469473922,-3819052368498544053,-8726161519271821972,-1134003519112188428>()) {
                                 case 1573689102:
                                    return;
                                 default:
                                    throw null;
                              }
                           } else {
                              var3[0]++;
                              if (var0.containsKey(var5x)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3omje9bioaksz","IJGkUjAz7a69xfMDlqRzRl/WqnvDuVx8wyDQCkkOgEc=",-2289021946464405540,-3083297496730518306,6345643364399705446,2830738112222198746>()) {
                                    case 205395143:
                                       return;
                                    default:
                                       throw null;
                                 }
                              } else {
                                 JsonObject var6x = a(var4x);
                                 if (var6x != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"skdjioanc2y6v","nPiQj1pEYTYSkf3Uz57qGwM3KLdBP/4i6ndfXU+M6SY=",6342225263163482625,8598953654886546376,-2178457976291317224,-7947953035351969161>()) {
                                       case 98877312:
                                          var0.put(var5x, var6x);
                                          switch ((int)com.yiyiaddon.m.b.a<"s1dpkxe38f75nw","j5IBkJVit0a0sTyd/gt6UBWr0Zf5/JLmso7gq+WM6jI=",-2605366581331724480,-6792277466581183347,-6370431456744109556,-6354177035087095341>()) {
                                             case 2140704640:
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
      }
   }

   private static String l(String var0, String var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2k3fk3cz5c1ne","rfXd7qVeftH0rfj5uMdP3csao7pgmI/+8YZSR9BhW7g=",8300595033946379434,-7337958360772440620,-4669886011827629137,-6285326919634427326>()) {
            case -89104799:
               if (var1.endsWith(
                  (String)com.yiyiaddon.m.b.a<"s1ylbu2spoet4f","0S+Jn7w8PHNQhanqODS8prikri30kwY0BzJ+YdFGppb6sxtE04M=",-3881124480574285603,-2915488046162514736,6673983987775680230,1788673770060944377>()
               )) {
                  String var10000;
                  if (var1.startsWith(
                     (String)com.yiyiaddon.m.b.a<"sujys6zlykb1u","IsDRuNB3/cjTQP1xubzpUSAN2Rnwwb7TeAeuipGB5x1ULQdrZ+Q=",-7432594056822238824,7772647936403205397,-7264241635108102300,-67235187521532349>()
                  )) {
                     label36:
                     switch ((int)com.yiyiaddon.m.b.a<"s29exnur4z8160","SNde7IllXH54B+FBlWceiZNXmjnLMg8qORqE3QUEaKw=",-4350659235974360901,-3032477950966814087,-8570578247621698375,-4879107491049839583>()) {
                        case 1465022304:
                           var10000 = var1.substring(
                              (String)com.yiyiaddon.m.b.a<"sujys6zlykb1u","IsDRuNB3/cjTQP1xubzpUSAN2Rnwwb7TeAeuipGB5x1ULQdrZ+Q=",-7432594056822238824,7772647936403205397,-7264241635108102300,-67235187521532349>()
                                 .length()
                           );
                           switch ((int)com.yiyiaddon.m.b.a<"syc6anlqh8inf","qki+PxL/3SDTX4iAxWgcyi/Ri6TM4XwatEC1XXFiFtA=",-3478306612528422950,2162932612284564553,1636689791876222740,-4663439360660133119>()) {
                              case 1416545953:
                                 break label36;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = var1;
                     switch ((int)com.yiyiaddon.m.b.a<"s2k0sljy4gfr4e","QzfWNwFfy1ahft0QRDIoOSC1B22hFEjrPdh+kZolu0U=",4752624703663164921,343903871938275155,-7121556160253059717,-2680568264536273715>()) {
                        case 2067798508:
                           break;
                        default:
                           throw null;
                     }
                  }

                  String var2 = var10000;
                  if (!var2.isEmpty()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s27kpo15kahchm","BCPzU0XhpdDGRMZAVHLLS7rUOsItXorTK9q6N5nmS0w=",-3159238416431067967,8173658812039518091,3862788725996001943,-1598946025462110807>()) {
                        case 500974433:
                           if (var2.indexOf(47) < 0) {
                              return var0
                                 + var2.substring(
                                    0,
                                    var2.length()
                                       - (String)com.yiyiaddon.m.b.a<"s1ylbu2spoet4f","0S+Jn7w8PHNQhanqODS8prikri30kwY0BzJ+YdFGppb6sxtE04M=",-3881124480574285603,-2915488046162514736,6673983987775680230,1788673770060944377>()
                                          .length()
                                 );
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s14wcspid8wpeg","zC78JtsiVdIqxrdDdkIrBxHuf4HIBmX0RfTmKPQxx3k=",-5829603800783414721,-7495009043467094104,2854203728958482896,-5311061883446833029>()) {
                              case -1307656951:
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
                  switch ((int)com.yiyiaddon.m.b.a<"s14jzwob5rmsw3","opzV4h42y4AICf7IQBGHZLdC7MxT/9LeY/j7WN8/1R8=",971489755243747350,2366590037793842410,4445241142760284922,-7168584764381391361>()) {
                     case 363974706:
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

   private static void c(Map<String, JsonObject> var0) {
      String var1 = com.yiyiaddon.e.n.a.bT();
      if (var1 != null) {
         File var2 = com.yiyiaddon.k.e.e.a(var1);
         if (var2 != null) {
            int[] var3 = new int[]{0};

            try (ZipFile var4 = new ZipFile(var2)) {
               Enumeration var5 = var4.entries();

               while (var5.hasMoreElements() && var3[0] < 200000) {
                  ZipEntry var6 = (ZipEntry)var5.nextElement();
                  if (!var6.isDirectory()) {
                     var3[0]++;
                     String var7 = aR(var6.getName());
                     if (var7 != null && !var0.containsKey(var7)) {
                        try (InputStream var8 = var4.getInputStream(var6)) {
                           JsonObject var9 = a(var8);
                           if (var9 != null) {
                              var0.put(var7, var9);
                           }
                        }
                     }
                  }
               }
            } catch (Exception var15) {
            }
         }
      }
   }

   private static String aR(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3h7dozh1k3cf9","uUXQdi8cikgx+JSsj+J6u8+VYX1ohb7aPFXYJZftJGM=",7688692340248611992,-832459940064375346,-2365510873155920499,1711904974741504944>()) {
            case 1413818912:
               if (var0.startsWith(
                  (String)com.yiyiaddon.m.b.a<"sqz4mzdcs7xxn","CsjdI4sR1zHuIJNPREe/GjFOshhgi9TSA/oEOFD3FTWUcLokdZVZ+K6L",7342022902355159979,-2041857281962370526,-5103949367299353813,-5062450081145185674>()
               )) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3uzbd0g60a9hu","tTPEGCSXokzGlRWYtOyVQjvATHWbvuA92I9aitoT2Ic=",-8796935857042714969,-9111581408937583361,-960796062643378902,4590694070825371866>()) {
                     case -990991440:
                        if (var0.endsWith(
                           (String)com.yiyiaddon.m.b.a<"s1ylbu2spoet4f","0S+Jn7w8PHNQhanqODS8prikri30kwY0BzJ+YdFGppb6sxtE04M=",-3881124480574285603,-2915488046162514736,6673983987775680230,1788673770060944377>()
                        )) {
                           String[] var1 = var0.split(
                              (String)com.yiyiaddon.m.b.a<"s2tfmm63gmkqvr","/MjsEFRfdtszotGkmyCH5PbiUYXB0J4FjVFv0j+D",-5050209862691170169,7664277373493570158,-507136599993508169,4176718460967257847>()
                           );
                           if (var1.length == 4) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2hzks833we6nx","B1xUzvO2JmrxQ3FuxrnHACnBSSQYcUCK6HQgEO10Q5Q=",-258110136621932717,7223833562261482273,2648039509593290258,-4604737127299728591>()) {
                                 case 763670024:
                                    if ((String)com.yiyiaddon.m.b.a<"s2ww0ewnqqpmaf","QDQcsA/1b5TwF+5XyCb3TOA9+aGPoaxb3JQSEeSy32Bili2l",-6839658340609346180,6498001339943989166,-5923574081983939311,468623435833811742>()
                                       .equals(var1[2])) {
                                       String var2 = var1[3];
                                       return var1[1]
                                          + var2.substring(
                                             0,
                                             var2.length()
                                                - (String)com.yiyiaddon.m.b.a<"s1ylbu2spoet4f","0S+Jn7w8PHNQhanqODS8prikri30kwY0BzJ+YdFGppb6sxtE04M=",-3881124480574285603,-2915488046162514736,6673983987775680230,1788673770060944377>()
                                                   .length()
                                          );
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s1rm5y4wg8ivvj","7r5AaprEQSlmKQxj7P2jzcgCGVRGrFelG9VtlUMbTDE=",-466954468002442303,-523651647650086094,6622858103955434584,300642689423118456>()) {
                                       case 1062214003:
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

                        switch ((int)com.yiyiaddon.m.b.a<"s17efc5p0kqct9","fcF6Ax8sEDt2NPWv2cGYQGKce6D92UhZfo2y3Fw5XiI=",-484907624307495508,-7678708887657455872,581950008540973835,-1519776015821428338>()) {
                           case -1504865521:
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

   public record a(int ng, int nh) {
      public boolean dH() {
         if (this.ng >= 2) {
            switch ((int)com.yiyiaddon.m.b.a<"s5avyb037x7xg","FYdgdWd9mXxi9VRqFTT+2Wdh3sqGIgBxPQ9/OL+AuSE=",5763131860537433458,-3180675868525474545,7888605911090270689,-796204375743269615>()) {
               case -69841624:
                  if (this.nh > 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1r6cyz5s2ltd9","98cSb0JreyZcKVlWHlKZL5fF+bZ8fOnNKgieT3EoHFo=",8606628802862433949,-3127488048257862339,-8587822495905160594,-754235910455060938>()) {
                        case 155950853:
                           switch ((int)com.yiyiaddon.m.b.a<"s2kj7seipovilc","ug/m6opvZyl6xmyBAlTzHXPQ+jc32IJMJW0Skz7WccE=",-8541799192757904603,-2939715790575782145,2519549407076330674,6224211587153207410>()) {
                              case 935865029:
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

         switch ((int)com.yiyiaddon.m.b.a<"slb72m47xot3z","nnHp/8xy5Q7jhgDDFcjBTG6NONADDynWFntTosjwFkU=",7034440862880652567,-6405991007320288557,2276172611437729679,4584472639546142301>()) {
            case 293617628:
               return false;
            default:
               throw null;
         }
      }

      public int cC() {
         return this.ng;
      }

      public int cD() {
         return this.nh;
      }
   }
}
