package com.yiyiaddon.e.a.c;

import com.mojang.authlib.GameProfile;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerSkin;

public final class b {
   private static final Collator a = Collator.getInstance(Locale.CHINA);

   private b() {
   }

   public static List<com.yiyiaddon.e.a.c.b.a> a(List<String> var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"scskynreq1i4b","/Ndf6128iA35kqHhNBRKhp/7WMAapLY1jxinL8RJ6Dw=",13228925318308438,3675374595846017726,1182868787366561414,-116821722376042287>()) {
            case -1182508123:
               return List.of();
            default:
               throw null;
         }
      } else {
         LinkedHashMap var2 = new LinkedHashMap();
         if (var1.getConnection() != null) {
            label94:
            switch ((int)com.yiyiaddon.m.b.a<"s3aonenmkfgo1o","2tkQ7PxPzZuqMOnbIopMD7a7lw6KCPs1Pi+Q1hb16C8=",-5822506451403675277,3611792742381352377,-5481397015520565657,4381096324757463093>()) {
               case 254494900:
                  Iterator var3 = var1.getConnection().getOnlinePlayers().iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s3pz0gu1lpxiay","q7acK58guqWKd9BE08pckhKUNsYZ6lH55oJlkaPh0sg=",-5151116215165586699,-4046303508833471281,-5121980549026786741,-7676412792404645320>()) {
                     case 731178453:
                        while (var3.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"shtb8ffs9g58p","qoS6wiwZDq3QQkczLGmAGm0Q5iuvR45oRR4/gjEPOz8=",1039993351095949371,4374811133376209274,-1282605755474560982,-5768167639156072612>()) {
                              case 1477429111:
                                 PlayerInfo var4 = (PlayerInfo)var3.next();
                                 if (var4 == null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2v6xx0vaycyym","mFD47ghy2X+whTBnJ0w1FdSOM7iJZ5xbkmTjCXDSpJQ=",7329655509614270638,-2149162606793929876,-7500220060965605898,3405000716675541049>()) {
                                       case 336789330:
                                          switch ((int)com.yiyiaddon.m.b.a<"s1q8irqwc6kfrb","tpvfDvMr5Wd6fOspCCq/w6o5OKd1nNb0k6s8VRNWRYo=",8541558523562229426,-2389891446513491489,-3850326749855718555,-5612586661010301472>()) {
                                             case -2054553073:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    GameProfile var5 = var4.getProfile();
                                    if (var5 == null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3g463y4na4n5k","EU4KjC9A7OOtEn/o7wIXxPSDZuG1jWIMThxMuPOVjIQ=",-4002434611393092145,-2779312834624328342,-8513200643283732955,977787045932121556>()) {
                                          case 1175684844:
                                             switch ((int)com.yiyiaddon.m.b.a<"s4h5xo7f23lsm","9j5y3bHPZV2nQ66sJXvizOJ6CcghFF6QcRuGmpf15Vo=",-1155729106826767802,758957843140767433,-8051234023861828585,5955389793001451969>()) {
                                                case 660428803:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       String var6 = var5.name();
                                       if (var6 != null) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1ewsapxi4awd1","fN/tigw/PfPdEAusYeufgc6G2IjApDwgqhjooIyRj0c=",-5697689489958659869,-5260594635726237884,-8280903708088898901,6469228934675922357>()) {
                                             case -948330261:
                                                if (var6.isBlank()) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1tkck2lltfm9e","DtkXJn59NOCNuxXlb6hKMu0GS1jFArzDVDtB+mAJJ04=",2632247392849989854,-7632750787911223860,-6131195344579151489,-1593126647500253425>()) {
                                                      case -1875369758:
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3nano4el78vzo","WKO8T/w4IpFrzSK89k9oBcJzg6rAetB8TO0pLb+uILo=",-7915588956471362240,-1607142181658411639,-3781744697715185230,-6984563659550812322>()) {
                                                            case -306847197:
                                                               continue;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                } else {
                                                   var2.putIfAbsent(k(var6), new com.yiyiaddon.e.a.c.b.a(var6, var5.id(), var5, com.yiyiaddon.e.a.c.b.b.LISTED));
                                                   switch ((int)com.yiyiaddon.m.b.a<"s18y521vsllx2x","Y/NbYnhxV4+cc9TcalMkR2IVy++Tin51udMynrCvl4Q=",-4776644228583977725,-3283931985250386758,2304021302559401820,-3363536038425333389>()) {
                                                      case 275690579:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                }
                                             default:
                                                throw null;
                                          }
                                       }
                                       break;
                                    }
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break label94;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (var1.level != null) {
            label83:
            switch ((int)com.yiyiaddon.m.b.a<"s2cmk0sytymp7g","NFjcE7mpiJ1T5mAITf7aNNRJw58sXi0sx2R5tEhtdlU=",-4692468070614825097,-1642481996645933896,-3747770984682956330,72051691302929656>()) {
               case 420805593:
                  Iterator var7 = var1.level.players().iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s1brcprh848nyj","y2F34UQZ0XDA/fkzMKCBfD+SkHLuumW2pLqUYlDAPWk=",338848751106124715,759878646088716345,-7881238698399788602,4108411090125152772>()) {
                     case 267464014:
                        while (var7.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1f84xvyz5crt7","sAI3yy21i6BUfWxg0y4WOVqLngLiDcoTtDbOX4sB1HE=",6048732452054395381,5693122369524676985,-7340942871415091633,-7064206264926988252>()) {
                              case 1265634130:
                                 Player var10 = (Player)var7.next();
                                 String var12 = var10.getName().getString();
                                 if (var12 != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2wqkk417tjx4h","vAH1LFYuJxsE0YsgK0qdivczB43nV+/gv2cfRDNwypk=",-3772985837549215905,-8262468488582380042,-7097825963672591738,3268365630216770934>()) {
                                       case 671325233:
                                          if (var12.isBlank()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2rm3gm8fhekfk","bPZ9Uu4g9Rk5aHDEgR4liLVHXya1sps97W3KEI6nR5U=",2645799944996107516,-8099260126781783015,3297944757812839553,-1755556328974077864>()) {
                                                case 1447281685:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1e32lero3ak24","Q/uYpzs3HcTURHK55hGVuVnXjxawTatSHrCaeVdHzEw=",-5134283761782978350,-3325937546968104610,-6933310413382577207,-2444642787813222790>()) {
                                                      case 1236537531:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             var2.putIfAbsent(
                                                k(var12), new com.yiyiaddon.e.a.c.b.a(var12, var10.getUUID(), null, com.yiyiaddon.e.a.c.b.b.HIDDEN)
                                             );
                                             switch ((int)com.yiyiaddon.m.b.a<"s3lni8eproa027","cVLUTMc9StTcVZNu20cZl8IVWfDDMdaL/LzvjKjjUm0=",2757081959695955688,-4477465561524761833,-135803538138133208,8088623760078202598>()) {
                                                case 623256480:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
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
                        break label83;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (var0 != null) {
            label77:
            switch ((int)com.yiyiaddon.m.b.a<"s1ujeac1hnkiv8","gH8grNwHCh07A4Tgre2KV/QBHXtZs9yqTR2QocYKfDA=",-3707527099855836231,-4660582473703914465,8892574122037601970,800161097665212850>()) {
               case 1259402032:
                  Iterator var8 = var0.iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"swdhabn5iybgm","Mgq0skk7oIG8AKMXn6BLgGQtRHlcRtwkfwz83YUOhcw=",-3096634454538742859,4515271534774931104,992326643088778996,-6077195232275088331>()) {
                     case -1083104329:
                        while (var8.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s34ymnz56awvmw","xAZ/rJGAX6vvaTgFUj3QaKcJHWByrA3jp+wYAz2XUYw=",-8739619114154129917,6155965505811963454,4986256635899220166,-3473097273846742172>()) {
                              case 2111495968:
                                 String var11 = (String)var8.next();
                                 if (var11 != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s21g90o1lmg4a3","/Mg5KZriPiVY27RkHoj+/OVObKvk39qWO/3UIjDG7Pk=",-7657483480687117883,-8788038365968955377,2991415880871148472,-3277205178189590529>()) {
                                       case 637822426:
                                          if (var11.isBlank()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3joi9uy909evl","RUFZQ0g5I0Rkqa2/jd/uQjrVTX+l1Ou3yf0I4AGN7m4=",-3978091854184006779,889333407906417200,1332395164446434533,5180432369898194615>()) {
                                                case 229281164:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2ik29hnq9qva","YLx3IZVwvBythTqeI2anZNyuScTDcmxd4tlxhtgOUW8=",-5061070799160755830,-3467009314688473191,-8450097934999103330,8136839717094335005>()) {
                                                      case 736168532:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             var2.putIfAbsent(k(var11), new com.yiyiaddon.e.a.c.b.a(var11, null, null, com.yiyiaddon.e.a.c.b.b.OFFLINE));
                                             switch ((int)com.yiyiaddon.m.b.a<"s2rmjstsd6b9wd","PAQqk+g6ABv/DEj9sf5EpPEu14hXvWd13awj95agY+Y=",-9210657734402187900,526016908628094018,-4024801137439587062,635321515696187797>()) {
                                                case -2074530270:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
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
                        break label77;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         ArrayList var9 = new ArrayList(var2.values());
         var9.sort(Comparator.<com.yiyiaddon.e.a.c.b.a>comparingInt(var0x -> var0x.a().ordinal()).thenComparing(com.yiyiaddon.e.a.c.b.a::a, a));
         return List.copyOf(var9);
      }
   }

   public static PlayerSkin a(com.yiyiaddon.e.a.c.b.a var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sf8x8ey89hsqz","CWoSEKnEITX+MWF1ZYPxYLv4eCme3BEg+a0WpwGNT0M=",-2721587511668374334,-7837311230774843266,7927726733147159211,-7235132876362710826>()) {
            case -1686478442:
               return DefaultPlayerSkin.getDefaultSkin();
            default:
               throw null;
         }
      } else {
         GameProfile var1 = var0.a();
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"sro9v3mbakdso","nEIuo749HufaHqwazp4VskjKq25AyLB0HzyLUrDl1b4=",2606282121516882571,-3673379251200039578,4246796778124936675,114364881720225893>()) {
               case 924614609:
                  var1 = new GameProfile(var0.a(), var0.a());
                  switch ((int)com.yiyiaddon.m.b.a<"s1g7aqua0fsbd1","XH8789Kcg2Y2o+CL4HpT4QCSMvqZA8WTvkej+WInfBg=",-6007307170472319412,5427518991208667316,-3814574163227097814,-6123441352864972740>()) {
                     case 1027943061:
                        return a(var1);
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            return a(var1);
         }
      }
   }

   private static PlayerSkin a(GameProfile var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s35f5qw7tyvbht","WZwBRt24yONjva83n38aJff1hyMnqiOjGKvbkNph27U=",2013487442657597215,-528856732400847290,4574333224654517833,-5843174531480271459>()) {
            case -362642938:
               if (var1.getConnection() != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2vice1k79cwk4","ZG6VVlh5R4yWI9JNXAW2l4Y6+pF81606D/ghcnqlBj4=",8116749479216678848,-7594472619925315808,-3885060512480935031,-6704465056573532431>()) {
                     case -1821141439:
                        PlayerInfo var2 = var1.getConnection().getPlayerInfo(var0.id());
                        if (var2 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"scfsly7r7u5mp","MMaJ68EkGUnst7q3sl8sVXgdmfu4HsuPTtPm3ULRNnk=",-1022237004624965120,-7381120605105902889,1084608187959823252,1121772943802415689>()) {
                              case 373571893:
                                 PlayerSkin var3 = var2.getSkin();
                                 if (var3 != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2v4y5qwr1evv2","Jel+twUlnDQD+tBNg2b3aJ09N0cbFOzVlHwM36TlaFU=",859643340539938922,7722441279485796596,8754213569440367439,-8687874859875310077>()) {
                                       case 1439212651:
                                          return var3;
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
               break;
            default:
               throw null;
         }
      }

      return DefaultPlayerSkin.get(var0);
   }

   private static String k(String var0) {
      return var0.trim().toLowerCase(Locale.ROOT);
   }

   public static Map<com.yiyiaddon.e.a.c.b.b, Integer> a(List<com.yiyiaddon.e.a.c.b.a> var0) {
      EnumMap var1 = new EnumMap<>(com.yiyiaddon.e.a.c.b.b.class);
      com.yiyiaddon.e.a.c.b.b[] var2 = com.yiyiaddon.e.a.c.b.b.values();
      int var3 = var2.length;
      int var4 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s20as7xnrpeh91","uJnapiq4+qH+/wBWz1sIB2916afmbdiA81qU4D5RXV8=",-5152422106064301237,7685032710738154075,7670435005795517834,7007076073431756633>()) {
         case 1110553951:
            while (var4 < var3) {
               switch ((int)com.yiyiaddon.m.b.a<"s5aem9nvuv1s3","lTlk0dP4+suXjdo2FJ8wVRkvHuSYN4qvc5pY/l5+cac=",-2422296258990227350,2146350199607018634,1825159551811518487,1490299177679017750>()) {
                  case 1161340538:
                     com.yiyiaddon.e.a.c.b.b var5 = var2[var4];
                     var1.put(var5, 0);
                     var4++;
                     switch ((int)com.yiyiaddon.m.b.a<"s24zz4uxe0en4w","A419Vyvpa0AOdPjZRuobS5TjkjfroVLAi4XgkJuWKH8=",-4676084386306266478,3319729157140359334,5169852057703685822,-4374306731128265666>()) {
                        case -746425303:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            if (var0 == null) {
               switch ((int)com.yiyiaddon.m.b.a<"s2vfnnpxv78lbx","Vz1fn6l6O+5PBss8VykW+kjNO6ghlXJt6VxoqzGNAtc=",-2704215055509676574,5687286194398313938,-2727319704784402361,7873033012345076517>()) {
                  case 1154418456:
                     return var1;
                  default:
                     throw null;
               }
            } else {
               Iterator var6 = var0.iterator();
               switch ((int)com.yiyiaddon.m.b.a<"s2kf0ijy8t29va","5LssFT7eev+ifgtq2NJabyh4mZo0Ui6NH1v7mBkNahY=",-2715062789865219198,9180239881414158832,-2515292503455498699,-5652696918635800044>()) {
                  case 709736219:
                     while (var6.hasNext()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1gicokojx859k","ZVXnbAzUxXZHpqNgWuUE/V2bVequHOCYL4HRi1V0ebI=",1649382722947056241,8458533434620644539,1567405185817245234,6636814683369022721>()) {
                           case -1467716207:
                              com.yiyiaddon.e.a.c.b.a var7 = (com.yiyiaddon.e.a.c.b.a)var6.next();
                              var1.merge(var7.a(), 1, Integer::sum);
                              switch ((int)com.yiyiaddon.m.b.a<"s27v4ezfdsc32d","m0d55H9D0EEpw/sTW/68G4/r799XyKzUcmR4p8tSFr8=",4975867040989700065,-5869042802516701397,-112429733626600263,2554409507074293223>()) {
                                 case 212734973:
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
         default:
            throw null;
      }
   }

   public record a(String name, UUID a, GameProfile a, com.yiyiaddon.e.a.c.b.b a) {
      public String a() {
         return this.name;
      }
   }

   public enum b {
      LISTED(
         (String)com.yiyiaddon.m.b.a<"sauj9m17aeazg","Sa8icl4gCjLedg15Z8hE4dSx2gQV37W/KyNYkB/HBvjvJFn9jXiJzdgr6qID9mqn",-3785830231830783320,-7534941501832605256,-6449720609000061012,-1165381604349965249>()
      ),
      HIDDEN(
         (String)com.yiyiaddon.m.b.a<"s11300c7p9mgl9","LhNMcvUIl7HHCWKiIQV7Sn3mSzQa0UD/Svh0Wtk8QouwvGuoYwpit225orvIWsfuj3dz+spbjUSE8VIrvlHdqHtdXcbPUw==",763670300573808001,8061096407567987792,1270625604059841894,1624643212850514145>()
      ),
      OFFLINE(
         (String)com.yiyiaddon.m.b.a<"sb9it9vzwz6u0","UtP2dLlM79ZZ72bKcNlzB0ia8c/3C/j+cGWwObnkyYbHcceMDitMZ9MDXq4s+ts0zKpmdoMNSmXCYEQB/5M=",-5669826587179956256,-6083925317256908825,-6416425134266307997,2267738802252154227>()
      );

      private final String aU;

      b(String var3) {
         this.aU = var3;
      }

      public String C() {
         return this.aU;
      }
   }
}
