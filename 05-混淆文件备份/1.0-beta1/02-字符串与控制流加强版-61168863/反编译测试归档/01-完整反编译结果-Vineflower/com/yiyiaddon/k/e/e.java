package com.yiyiaddon.k.e;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;

public final class e {
   private static final String EK = (String)com.yiyiaddon.m.b.a<"s2gp24cxg9j3vb","UuJJZghacZQ1j2FoPSUkyc9P4NslL5AKApG5aeH4AdeTU3S6XIeRW5cY/MgqOBtgYWCbVjpP3OfOTL/Y7lO5R2bHb5GO3WxaUck=",-6109188895568788664,583037085719859723,-4634263897940659960,-3194496368761540803>();
   private static final Set<UUID> aR = ConcurrentHashMap.newKeySet();
   private static final Map<String, UUID> aZ = new ConcurrentHashMap<>();
   private static final Set<UUID> aS = ConcurrentHashMap.newKeySet();
   private static final Queue<String> c = new ConcurrentLinkedQueue<>();
   private static final List<Consumer<String>> cY = new CopyOnWriteArrayList<>();

   private e() {
   }

   private static Minecraft b() {
      return Minecraft.getInstance();
   }

   public static File b() {
      File var0 = new File(
         b().gameDirectory,
         (String)com.yiyiaddon.m.b.a<"s2gp24cxg9j3vb","UuJJZghacZQ1j2FoPSUkyc9P4NslL5AKApG5aeH4AdeTU3S6XIeRW5cY/MgqOBtgYWCbVjpP3OfOTL/Y7lO5R2bHb5GO3WxaUck=",-6109188895568788664,583037085719859723,-4634263897940659960,-3194496368761540803>()
      );
      if (!var0.exists()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ryha8x3nss7d","BOTqNsninMJsn7P9gOJQnMcongPUuNJ9hT4/gzIx02Y=",3737905763372705396,-4212891077178913320,-640498746438766130,6633799572536684936>()) {
            case 1057817497:
               if (!var0.mkdirs()) {
                  switch ((int)com.yiyiaddon.m.b.a<"srljypz8fm0lu","VKEmpaR7s3+sAej5gmy5mDzLULCHCYFwi2JlLo/6lSI=",1090618182472196987,-6356006571530902623,-585243638199965356,5671799509896397949>()) {
                     case -272783513:
                        return var0;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      return var0;
   }

   public static String gA() {
      ServerData var0 = a();
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sf1vy30dwlkep","1AKF7wg7ZhLFur492a8jBNjHufHyW3uOq1XfDLsTzqk=",6151191446102068506,3232105178995508713,1288692137571487759,-8577966769730749922>()) {
            case -447495708:
               if (var0.ip != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s372e2teg5mzqz","r01sZumN0RELp7N8VPJezXsgwvC5NIDAMfQrf3LXUmY=",-9125644517239215857,-3556113121301349796,-3555047551740251562,9218004265438727390>()) {
                     case -106669552:
                        if (!var0.ip.isBlank()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1bx41qagaj6p9","M8Ynm0fK3Z6UmOvSpY0AC1KzJ6JKIJcqAAFAxBHq5iw=",-4126305542046402963,5317262125611003583,1065968183081379597,-176925867468933678>()) {
                              case 1631005623:
                                 return bZ(var0.ip);
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

      ClientPacketListener var1 = b().getConnection();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1m6wteh7d5tgm","RTk1Pvmr/ioEcnRHOha9z8Kcg7TcvcB8W5t50RcKYf4=",-5287707740126022627,5013580609752908542,-6253150670864094336,-8271392994645042004>()) {
            case 603842099:
               if (var1.getConnection() != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s38k1snkweqx44","RjRcUwUgzLoED0fv6ksXMoSEZdkNNHSqu9xCPVNVxA0=",-3211558788616142811,8324504812246550199,1223814858754788146,-4712471098293455336>()) {
                     case 1011464155:
                        SocketAddress var2 = var1.getConnection().getRemoteAddress();
                        if (var2 instanceof InetSocketAddress) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2n70cjre6nheh","ry7ZLy97/FLMlhhS29+TtIIwePK5huUEplpNjg5+OB0=",-3761790051048373963,8243763325635029049,-4438119009579899003,8212518175927320065>()) {
                              case 1189603835:
                                 InetSocketAddress var3 = (InetSocketAddress)var2;
                                 String var10000;
                                 if (var3.getAddress() != null) {
                                    label46:
                                    switch ((int)com.yiyiaddon.m.b.a<"s1lzeknlkqjkx4","BWlkgFgY2IjN5/CaAOMtO9TAcbafVt/HzN8x2qpV0qo=",2827048985801688549,877219824069474571,5369183684777339130,-4935712781026738473>()) {
                                       case 1576306516:
                                          var10000 = var3.getAddress().getHostAddress();
                                          switch ((int)com.yiyiaddon.m.b.a<"sh2j05avv35h1","ZwBcSxvmbY7P4oLW2WKTzlqqotmYU5+OD7d5bK5hUKg=",963373762072839472,-7051657113331650248,1991795597604601619,1932042087644727806>()) {
                                             case 863597165:
                                                break label46;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    var10000 = var3.getHostString();
                                    switch ((int)com.yiyiaddon.m.b.a<"sp6nrssxlctpw","5hHafFSsNIy//TL9eXYCjNknbkqznLF9BgwBnZW1pqQ=",1060413179508983213,3438113720662911625,4798205235718697428,-3402280757016115625>()) {
                                       case 2038100180:
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 String var4 = var10000;
                                 if (var4 != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3u47lcoac2u66","eNMG2EVT2unSflpwuY/FhWxzXRoXykXn0PuM4R7tILE=",3596293197002146706,-4646841156137439578,3698057626888759623,7922009034213373564>()) {
                                       case 920558035:
                                          if (!var4.isBlank()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s31y1gopbpfipz","Usw46xUXqI3cENZZ9/9FsxCCo59RXBLr43fgCD5Qqwo=",-8704173893773082441,6934778751316068449,-797556592692007423,4743436550794488378>()) {
                                                case -1796293850:
                                                   return (var4 + var3.getPort()).toLowerCase(Locale.ROOT);
                                                default:
                                                   throw null;
                                             }
                                          }

                                          return null;
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

      return null;
   }

   public static String bZ(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3teu2g0dd0d5s","YOKh+nkVYrhxw7EmlUKZQsYnTE85X2yUEUcWI9k9Xmc=",8459632860359908933,-8017532356958449517,-4742198409501599217,-6025585708742981268>()) {
            case 2108414658:
               return null;
            default:
               throw null;
         }
      } else {
         String var1 = var0.trim().toLowerCase(Locale.ROOT);
         if (var1.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2w2b4om9570q6","r6szg90SIa8o/1j+C92Y96IIzuz3KH/EiO2WZdzHnFA=",-8801305272148135756,-4798016714009660820,1719797169759631473,3761780235473568261>()) {
               case -759093484:
                  return null;
               default:
                  throw null;
            }
         } else {
            if (var1.startsWith(
               (String)com.yiyiaddon.m.b.a<"s3htle1w3jzssj","a+gHc9tiQf4jcv8/KUQldbxrQnMT3k5BfaLTf2Gb",1692342987973362825,-7210527595693161859,2467869325651444688,8703605135770347665>()
            )) {
               switch ((int)com.yiyiaddon.m.b.a<"s1rekwvd0m2gbh","GWBDeKdknBw/lrrve2KMHB53wOoTJNpBZs6QtVzSUHw=",-3907940765371737783,8317538145885814447,-7659640452093608146,4274488890166512855>()) {
                  case -82367482:
                     int var2 = var1.indexOf(93);
                     if (var2 > 0) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2xrr5sx93zj2p","XJmElzSHns8ZIdOQwJQAIfYv1jWuTCLQFNmZy4tRltI=",822159452432450881,-4681727063071086045,-5920377741284601978,4665244637487126699>()) {
                           case 861574759:
                              String var6 = var1.substring(1, var2);
                              String var4 = var1.substring(var2 + 1);
                              if (var4.startsWith(
                                 (String)com.yiyiaddon.m.b.a<"s1rw9syez8b4s4","b4KD3SAZJr3JorXC29Sofvb+VyvHqf1O1r04oT6O",4073489002494648123,-5970789201228758518,-6379325447956067501,2927730772092721424>()
                              )) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2xfleybcw252c","QjaS6566asrfDrDN72IsRVeFK7IcGXzAgvWMVT4RKNQ=",-5274940944557177636,5756667104716051439,2545874440917705274,-4177185471348780906>()) {
                                    case -1582316002:
                                       if (var4.length() > 1) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2a9n0uhcf0wy9","k1eRshEk1wwtlOdzkrSqgE7Q7LgToc8FpxBLMxtiU4U=",-8432331408350604709,-5955096603777260228,-1127778726312641856,-4226224852434077449>()) {
                                             case 1743869696:
                                                return var6 + var4.substring(1);
                                             default:
                                                throw null;
                                          }
                                       }
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              return var6 + "";
                           default:
                              throw null;
                        }
                     }
                     break;
                  default:
                     throw null;
               }
            }

            int var5 = var1.lastIndexOf(58);
            if (var5 > 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s2gqwrwejhjv2b","RDvZhGOoWeRFxJ2e1DdHFLbVdYpff73+rGQ0a2woRbk=",5319771833404249978,7271376111920151431,-1258166215165978451,-3268344460155298085>()) {
                  case 361755276:
                     if (var1.indexOf(58) == var5) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3fsc5xh01rxl4","mdri6K4/hZl42T/+VXYSc3l3fkmX7QnMvPyKvlzgnDA=",1638604410835144829,8691066724711300585,-4924900717905086185,-6310606999183545620>()) {
                           case -351301225:
                              String var3 = var1.substring(var5 + 1);
                              if (!var3.isEmpty()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2zz0jozvgp0l1","LItyXPI+knsNbZCSHvl65+BI3OrGkt+SdxXUWZrfxdI=",-7140233851034279079,-7869606465837964828,-587637899307740543,8016687615159912453>()) {
                                    case 1530682693:
                                       if (var3.chars().allMatch(Character::isDigit)) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3mrqmc83qx9u6","Xd/8GjEvNxUFOMsmz9SrirrFPNJmQDu27mYsxPFqQ4U=",-2781024054769518454,-1736715272838588457,-957842769024452276,-2197730466894077129>()) {
                                             case -1241586205:
                                                return var1;
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

            return var1 + "";
         }
      }
   }

   public static String ca(String var0) {
      return ce(var0) + "";
   }

   public static File a(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1kg9hzvcox6h","SrSv0o9lgf/BmmXWnOY3TZc08UotTUx4N+65DHj35bY=",-16343728729366248,-709637698628283183,-9179169033563052295,5028853558939238474>()) {
            case 1284872553:
               if (!var0.isBlank()) {
                  File var1 = new File(b(), ca(var0));
                  if (a(var1)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s4vajzasxcw8b","4pT4e7yFUgTim0nos/DVWLuTMk7xzS5wpsHQkbCrAX8=",6464451920572689548,289522776743385462,-2032018244912263364,-3666825963203960257>()) {
                        case 1360685423:
                           return var1;
                        default:
                           throw null;
                     }
                  } else {
                     String var2 = cc(var0);
                     String var3 = cb(var0);
                     if (var2 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3oc78wimfsnpb","amBx473GWJvl81EOhZtSss77Yhuhm4I7Z98wG0QDYDc=",6239126605578757143,-2537870814413938281,2689923125599363895,765429986171665753>()) {
                           case 1507027957:
                              if (var3 != null) {
                                 File[] var4 = b().listFiles();
                                 if (var4 == null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1d8ba80juol4g","k6+dvyNBHpq0C28AWMhdk+G6aCYsm0vIgsm2ewSQkqI=",8303338353591300228,3581159942579159255,-7857462457718804883,5709574530234981240>()) {
                                       case 1777803622:
                                          return null;
                                       default:
                                          throw null;
                                    }
                                 }

                                 ArrayList var5 = new ArrayList();
                                 ArrayList var6 = new ArrayList();
                                 File[] var7 = var4;
                                 int var8 = var7.length;
                                 int var9 = 0;
                                 switch ((int)com.yiyiaddon.m.b.a<"s2tfx0lk0nilcq","v2s2jbIs3ASXynFHZ9yWk5zNE/R3JJYh6CJzv7I9Qc4=",3011741989667512596,1178345226331263611,-5456514027654513694,4047573493917803470>()) {
                                    case -136129285:
                                       while (var9 < var8) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2mtg4ljdogusa","5tlSn5EZvvXuuw8UT5ZTlFolXM152QOQOnQSZPKIsCk=",-4074268373256741831,4379639951831610398,901643976360107695,-1070067371672607122>()) {
                                             case 124973633:
                                                File var10 = var7[var9];
                                                if (!a(var10)) {
                                                   label118:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s38riufmdjd1kt","bv4CRWVPDxgKjU5nLv3o6hOZqBnEID3mXppggNMGbE4=",7107844186506752821,-609479133115798278,-8511832816316989490,-3606583499764967278>()) {
                                                      case -16372563:
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2799u5o3qj4gq","scP/cPsRnYIhP/dWXRokwkN3ih0MBrqoxX9rhZittzk=",3887411000597117399,-2066056568299007428,6772013975445066846,-5140080441674884523>()) {
                                                            case -158361669:
                                                               break label118;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                } else {
                                                   String var11 = cf(var10.getName().toLowerCase(Locale.ROOT));
                                                   if (var11 == null) {
                                                      label114:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2q8g1z8mpvy94","FOIItDLEDXonqKn/qh7CZwR3IVybAxAs1UcpTT/u/mg=",4189609926494000072,-6322271742583181068,5926248230302119322,-2856092644558311968>()) {
                                                         case -563158357:
                                                            switch ((int)com.yiyiaddon.m.b.a<"s35h64a5260qjx","zkEFIjkFVLuyT9u2c7VJPhXm4W1MjX+ByjtSETAB3+0=",1100649688727062396,6446251573531961045,-4228626373913454897,518438567879130405>()) {
                                                               case 1061005481:
                                                                  break label114;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else {
                                                      String var12 = v(var11, var2);
                                                      if (var12 == null) {
                                                         label110:
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1vc39vs7vk22o","Fmq16osU5L774rF1lD/pl6FQzikszvLUPqfGcpvTK0U=",7907611817480085853,8681512448195471804,-1049620361257748328,-2945138969945678474>()) {
                                                            case -30606127:
                                                               switch ((int)com.yiyiaddon.m.b.a<"s1ul90rkhi8yi4","M898cl6zpeX7GnZnhrU/VRftSjQ7UcU/aEcMnP0OlrU=",-4865603251796936147,-2243833098510364567,-1706137891124056964,9050911880697131791>()) {
                                                                  case -1712512980:
                                                                     break label110;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      } else if (var12.isEmpty()) {
                                                         label106:
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1ulspqcd45tx6","YwatKY2NT5T74ytNfwtTlN5TyFxfcBBs7v6Svhv++TU=",8289424920652543394,1981657871771399577,4261054728400135525,-3962051222876365341>()) {
                                                            case 239336509:
                                                               var6.add(var10);
                                                               switch ((int)com.yiyiaddon.m.b.a<"spz6jmwjdi3pg","h4qua6TF/nS+EZZ9jBtU6lPUx0HAKV9UlSJmTRrVEIU=",-7822486316859185354,-1041432877834639393,-87386713517871954,7266269231396835661>()) {
                                                                  case -755332952:
                                                                     break label106;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      } else {
                                                         label148: {
                                                            String var13 = cg(var12);
                                                            if (!var13.isEmpty()) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s5wu5mrjtdttf","Z45aYWMBwrsOxT+uWLQb5ntoGbMyWfWI/rkS2iSrsxM=",-2383652095921239903,-8275842084422953924,-3763191998659633316,3128932609499302956>()) {
                                                                  case -1588908721:
                                                                     if (var13.chars().allMatch(Character::isDigit)) {
                                                                        switch ((int)com.yiyiaddon.m.b.a<"s1pcogs3vvpljm","FJ0piNXyGe0NIGf89DXb0kl1YyrT9re4DGhRhNuknHk=",3935788546085086896,3226806215476466637,-5802709078032753917,-8624911136045520447>()) {
                                                                           case -738472544:
                                                                              if (var13.equals(var3)) {
                                                                                 switch ((int)com.yiyiaddon.m.b.a<"s2023y4itjesrs","TLpfc0nGaM1CDpyrhF+Sw9TerR/uDnVPptasyzgE8WY=",1360063207706362916,3262609089695105921,-5286640956137846206,-5021232409220472462>()) {
                                                                                    case 1231622939:
                                                                                       var5.add(var10);
                                                                                       switch ((int)com.yiyiaddon.m.b.a<"s3kaqgx8txv6on","hNtHLB/SxAlIoy6pa6zEKM/A84JuQHkAr2AFIwl7uSY=",-7182291384813842764,-3564462060825199944,6887294409156409069,-2578727126490654727>()) {
                                                                                          case 650481038:
                                                                                             break label148;
                                                                                          default:
                                                                                             throw null;
                                                                                       }
                                                                                    default:
                                                                                       throw null;
                                                                                 }
                                                                              }
                                                                              break label148;
                                                                           default:
                                                                              throw null;
                                                                        }
                                                                     }
                                                                     break;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }

                                                            var6.add(var10);
                                                            switch ((int)com.yiyiaddon.m.b.a<"s15sryahdoy1y6","tid4kOOL8ZeqhN5G+oEGzD/SmXE+oDNyKMKpk5OR7MA=",108760644894332286,-3879373118872789587,-5701982725663211672,-8840608802846964169>()) {
                                                               case 237619951:
                                                                  break;
                                                               default:
                                                                  throw null;
                                                            }
                                                         }
                                                      }
                                                   }
                                                }

                                                var9++;
                                                switch ((int)com.yiyiaddon.m.b.a<"s2u4ytcs9ins33","S4eINhVc9GtvgCeHTZ1FUqjhMTMcZ9D4Bm/3b6zS0OI=",-3050152764388774149,-2714158439372390182,1299950862824189183,-5643996891118387578>()) {
                                                   case -469294185:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       if (!var5.isEmpty()) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2ceufjvcczvog","CvgqaXcHRIEsoEtzwIfmVi4QnclkSr09sPzoGAOpwYM=",8359333226124509363,-6091779819546614007,-5753195200761807969,-1090496312368891636>()) {
                                             case -1737161061:
                                                return (File)var5.get(0);
                                             default:
                                                throw null;
                                          }
                                       }

                                       if (var6.size() == 1) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1u0uyqztrgsuq","VtGJYSz0MRRELLrYWsCRgsBPIuBp/K5koMdQQN+YDwU=",3476549081061895953,-4282971610231596952,3965887642163811058,-354263510302036865>()) {
                                             case -993639337:
                                                File var10000 = (File)var6.get(0);
                                                switch ((int)com.yiyiaddon.m.b.a<"s1x5267d02usps","2ZUerzsPI/yYIsmL84d5CVaBSl7qBLFVDUHOkNBG32U=",898307269199216051,-1789081773531581534,5119248713434022336,-816578045210113287>()) {
                                                   case 1266769203:
                                                      return var10000;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          switch ((int)com.yiyiaddon.m.b.a<"s105jxlnchsubz","ToeERhSWlvkaDNw0dgmrLyAC8VGo1TlUjtYIUlPjJjk=",7200227187846834473,-1623551274803755823,2802217613104905364,642910269484576647>()) {
                                             case -1535065900:
                                                return null;
                                             default:
                                                throw null;
                                          }
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s1tdqlu0xegf0c","HVKdRik7tSDR01TkytALwmhLb4yPAeGRoh74EOV0MZ0=",1507127367917194221,3864496722842789125,-9191273230103665560,7635539329523418572>()) {
                                 case -283591315:
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
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2wzp0o05t2agn","HvDp+o+hbgHfZrIHzACnGgITO6v7EBoq5JLX+lUhJVU=",-278168217033999633,5248251090559693040,-3951375573906843789,-8974669917661766009>()) {
                     case -411263177:
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

   public static String cb(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"scm4wd352e7al","U8aPxSUojnvOks/UOSu0A6vWC2/bUspm2523qlwCF3s=",-50769575964143570,-7860133080401043225,-2630963587436972809,6775829411601546338>()) {
            case -69530119:
               return null;
            default:
               throw null;
         }
      } else {
         String var1 = var0.trim().toLowerCase(Locale.ROOT);
         if (!var1.startsWith(
            (String)com.yiyiaddon.m.b.a<"s3htle1w3jzssj","a+gHc9tiQf4jcv8/KUQldbxrQnMT3k5BfaLTf2Gb",1692342987973362825,-7210527595693161859,2467869325651444688,8703605135770347665>()
         )) {
            int var4 = var1.lastIndexOf(58);
            if (var4 > 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s3td1s0ub8g95j","zvqI8nmmnmynGmDI12+4jLaX1EKzr+aFjrem+uMEDHg=",8268276930016278119,-1139323212276075175,-7109726012033914563,-5911796791387351780>()) {
                  case 285030419:
                     if (var1.indexOf(58) == var4) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2cjekg9iqet7a","tZ8s6wdZc1fM8xck+hBj8ii1q/CjeIPVEP4oR2yWlng=",-6759219180221449572,-6863222599946289381,6191022461797363883,507522834061113764>()) {
                           case 784843827:
                              return var1.substring(var4 + 1);
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
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s2mjv6fhe1uqhm","oP4Dv8Dl/YmBY9fYxuOCD7+zJdmI3Q/Wj1SRVnlXN6w=",-343928269333147666,-3633684377079090483,-5396202288727510424,-568872525225910260>()) {
               case 2128024487:
                  int var2 = var1.indexOf(93);
                  if (var2 < 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s100d2m5x2xgxo","tzTi4yZRDW2WfAaey8qvHfgdOukC4slET8GJpgChl24=",3901971044197153220,-4100133389882357975,6732694336200154075,-6501679882469030904>()) {
                        case 1416688296:
                           return null;
                        default:
                           throw null;
                     }
                  } else {
                     String var3 = var1.substring(var2 + 1);
                     if (var3.startsWith(
                        (String)com.yiyiaddon.m.b.a<"s1rw9syez8b4s4","b4KD3SAZJr3JorXC29Sofvb+VyvHqf1O1r04oT6O",4073489002494648123,-5970789201228758518,-6379325447956067501,2927730772092721424>()
                     )) {
                        switch ((int)com.yiyiaddon.m.b.a<"s33an2m6q02yqa","8Nea5OG2TtjwsgSxq33LSh33ePcl/yWgVXcGvTpVAiQ=",-8864802109069637614,-9112699387023628961,-6242460228374174881,7776705370717564508>()) {
                           case 543016741:
                              if (var3.length() > 1) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3oa27s5z21tc3","pJNNPaJ0MXVoCh1mxrrOKN47jvqrXF4aveTOoUaZD14=",-6581003023478265036,-2993773219519765202,174376474149016118,2483336065218773215>()) {
                                    case 283381091:
                                       String var10000 = var3.substring(1);
                                       switch ((int)com.yiyiaddon.m.b.a<"s2ilz8r8auyws2","GeSgsa6p2cQdLAUHfEcSdloggkrFYKo0t9Pv54tIuf4=",2266804757862244183,-7313937920202509600,1589250419512078236,-6527304018347927428>()) {
                                          case -228873637:
                                             return var10000;
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

                     switch ((int)com.yiyiaddon.m.b.a<"s3g504p2wbhrzk","FksNb1vCugrIcWs6YVJ1uTEJukdFp1ipOcUxR88N8Fc=",-85900219574921469,1362411415719208042,5114920613603585525,-6718089205529582713>()) {
                        case -222739321:
                           return null;
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

   public static String cc(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ec5ovyv26yiw","XigmdthcPuEPmXEfxkyWZtl4uascq0804VO+w+hKQVY=",-4494182433352042614,-5722658222891801943,8014943452029059462,8511346147582503887>()) {
            case -519412049:
               return null;
            default:
               throw null;
         }
      } else {
         String var1 = var0.toLowerCase(Locale.ROOT);
         if (var1.startsWith(
            (String)com.yiyiaddon.m.b.a<"s3htle1w3jzssj","a+gHc9tiQf4jcv8/KUQldbxrQnMT3k5BfaLTf2Gb",1692342987973362825,-7210527595693161859,2467869325651444688,8703605135770347665>()
         )) {
            switch ((int)com.yiyiaddon.m.b.a<"s36kk9kxsz0dqk","rNCqq7b+lUo6lBQ9yWCCsbgeBeM3sfYNTZ28m2zOyDY=",-604962027231090548,-727439867012044756,-8915632195759753696,8338370212217597277>()) {
               case 1910189326:
                  int var4 = var1.indexOf(93);
                  if (var4 > 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1vsdfhkb5shv8","DjTd0JRWshLPYws+c2kDafgIfuEfdenAOHdzC6REp4o=",5445457707040214541,-8414192766904161409,5335903911844977616,-3552327499029484651>()) {
                        case -588193486:
                           String var6 = ce(var1.substring(1, var4));
                           switch ((int)com.yiyiaddon.m.b.a<"s3najbhq95xw6n","CQX2f0OrA9J8jMg2x2e0kijRF9L5Y5eI289pDIlC3Xc=",6063833715840467819,7363017675312256671,2134222425362995398,-6646731447045241357>()) {
                              case -314642757:
                                 return var6;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s3ttucc2igbidu","+RUbcfj/tUVV27INDKrdxhvO1sb0pNIUCsAAhF2beg4=",1138715893049677342,3307833554903494375,-3199961474563875531,122424929949546251>()) {
                        case 166154104:
                           return null;
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         } else {
            int var2 = var1.lastIndexOf(58);
            String var10000;
            if (var2 > 0) {
               label53:
               switch ((int)com.yiyiaddon.m.b.a<"s21cnso5aerzkz","/Tn6BSboOBA5ePqKYR3o8tCjEEdhlf83aC9AjTqU5/4=",-4817880807889023428,5092399757194191223,-7221959970938351263,-6138001982200284068>()) {
                  case 280542907:
                     var10000 = var1.substring(0, var2);
                     switch ((int)com.yiyiaddon.m.b.a<"s2fthuoe0l8ymk","6a3qLidrkKwdX7rUSEnnEKl+G9swS4fdh1qX+qy/2pk=",6212286733561042917,-5751380797773784338,7355683892309252645,-8409169491665555343>()) {
                        case 1180846288:
                           break label53;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10000 = var1;
               switch ((int)com.yiyiaddon.m.b.a<"s2wwirzbi39g5g","CjwSGuxqeNwF1olIZ13jIIWt2kmNSc1IoTmEAYjOLIo=",1686773835888515255,-6317163093799108562,-3643932427525023892,-3140272969129204977>()) {
                  case -1421253251:
                     break;
                  default:
                     throw null;
               }
            }

            String var3 = var10000;
            if (var3.isBlank()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2z82mlqgnxbha","cH0KQN17mvoy47EWuQhT4cfki8ViC41pUPLL9Ce8rJI=",768001357888154441,8529916803960107843,-6972212731544594559,2235161189345139740>()) {
                  case 1368459956:
                     switch ((int)com.yiyiaddon.m.b.a<"s3ikf4rdwztgjf","XCmalyVuiO+PIX6vbsPuLe3l1fDOB7hojUIa24AXivs=",7468398490748448061,7357149731261335757,-4391525578980636097,-4215773921514090064>()) {
                        case -2072562262:
                           return null;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10000 = ce(var3);
               switch ((int)com.yiyiaddon.m.b.a<"s1degot3psjwl1","/69N9mfCInYa84Vt6+6F6QuceZH2EgM8S6ftnYnR1PU=",-7314498961704733585,-7775251798559196712,-8642136928988494482,-6882810268100308242>()) {
                  case -1796953643:
                     return var10000;
                  default:
                     throw null;
               }
            }
         }
      }
   }

   public static String cd(String var0) {
      try {
         URI var1 = URI.create(var0);
         String var2 = var1.getHost();
         if (var2 != null && !var2.isEmpty()) {
            int var3 = var1.getPort();
            return var3 > 0 && var3 != 80 && var3 != 443 ? var2 + var3 : var2;
         } else {
            return (String)com.yiyiaddon.m.b.a<"s3p39kxlb6t6o4","ozHuECk8CcdcHCI1SSPkvs7Ig0J+Xs5td+uqBIpEryIB4/xn8PebybpJ",-3478956684078628319,1441613792192998598,3092599734155060920,2481593829752379336>();
         }
      } catch (Exception var4) {
         return (String)com.yiyiaddon.m.b.a<"s3p39kxlb6t6o4","ozHuECk8CcdcHCI1SSPkvs7Ig0J+Xs5td+uqBIpEryIB4/xn8PebybpJ",-3478956684078628319,1441613792192998598,3092599734155060920,2481593829752379336>();
      }
   }

   public static String ce(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1c865z4941u3z","viLE+DJH3uX1u56DG/2RFUsZwOkmGDBy1jRqFJfdpzo=",-3461374478216169328,-1068066736199827756,2297130299559606555,2364388713153298265>()) {
            case 855671559:
               if (!var0.isBlank()) {
                  return var0.replaceAll(
                     (String)com.yiyiaddon.m.b.a<"s2cvyindd1nhis","O8PcFkwxQHhy6g/O07ubnV2Md3O2jq7Pm7/CqDI+xPvGarbAp/8nS3OCWS0AFyPUzSCzAA==",5238603087299014911,-2381334084586225466,-3372144478252743638,-8328279421991268612>(),
                     (String)com.yiyiaddon.m.b.a<"seg5gwe3pvxg0","1+L/tlwnDzyUXZ9jKlTFUMjiKgH9uG5kWOIjkcxB",-7988748842867334114,-4218878400809568114,-4337872229349696958,3999226278274102162>()
                  );
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3cg59gb4lwlqo","lIiW5vUBCaMuEzvJdHoTMQsXzFaEb4uE3NRWwd4E/Fg=",-5553208625530684431,-8070237459699690037,-4406843500468582306,6574204306833148803>()) {
                     case -1697177634:
                        return (String)com.yiyiaddon.m.b.a<"s3p39kxlb6t6o4","ozHuECk8CcdcHCI1SSPkvs7Ig0J+Xs5td+uqBIpEryIB4/xn8PebybpJ",-3478956684078628319,1441613792192998598,3092599734155060920,2481593829752379336>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s3p39kxlb6t6o4","ozHuECk8CcdcHCI1SSPkvs7Ig0J+Xs5td+uqBIpEryIB4/xn8PebybpJ",-3478956684078628319,1441613792192998598,3092599734155060920,2481593829752379336>();
      }
   }

   public static String a(File var0) throws Exception {
      MessageDigest var1 = MessageDigest.getInstance(
         (String)com.yiyiaddon.m.b.a<"sqs268lnyer0o","uNdeuvD7k3OHSmqrqhbec+T9etQMJouBYCqZ7g2A78j+GZiL2Fc=",-506025341238199276,-4801901751281396709,-2118063286115563912,-2514412314242224108>()
      );

      try (FileInputStream var2 = new FileInputStream(var0)) {
         byte[] var3 = new byte[8192];

         int var4;
         while ((var4 = var2.read(var3)) != -1) {
            var1.update(var3, 0, var4);
         }
      }

      return HexFormat.of().formatHex(var1.digest());
   }

   public static String b(File var0) {
      if (!a(var0)) {
         return null;
      }

      try (BufferedInputStream var1 = new BufferedInputStream(new FileInputStream(var0), 65536)) {
         MessageDigest var2 = MessageDigest.getInstance(
            (String)com.yiyiaddon.m.b.a<"s1dhgwplv5vx2j","CQ7uI5lMwzUHBWKawM/Z4F0bJdSXUFvcjOTCPmmZNHPj9+kPHx66NMDd",6645447085517902339,-1108530303687998788,-6840196169733002985,-5365262000188060429>()
         );
         byte[] var3 = new byte[8192];

         int var4;
         while ((var4 = var1.read(var3)) != -1) {
            var2.update(var3, 0, var4);
         }

         return HexFormat.of().formatHex(var2.digest(), 0, 6);
      } catch (Exception var8) {
         return null;
      }
   }

   public static boolean aO(String var0) {
      return a(a(var0));
   }

   public static void f(Consumer<String> var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1eo88uh0l6ypz","2+KCwR2JKxm/A6rx1if41hRvdgHh/1hc4BkTzmxnTsc=",2770055864870752657,6066594051513646210,-3924246341149229899,7389685581678329568>()) {
            case -954692080:
               cY.add(var0);
               switch ((int)com.yiyiaddon.m.b.a<"s2vuhctudi54yk","c3KDzD5wuVnrg4/X5HUnfWQO58QITmtVZYproLjP8vA=",4717141106843202386,-1607474425799879033,2912928321278812473,-4706207134373892349>()) {
                  case -1622578413:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   public static void br(String var0) {
      b()
         .execute(
            () -> {
               if (b().player != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3gsx81azfqqki","PcYjY16r4+YAXlRMKhIksIOo/rGXiHS1dAllGZzMO2s=",4319845683250958006,7566862225603842967,-5765960076601463957,-5439429540626938167>()) {
                     case 1853820838:
                        az(var0);
                        switch ((int)com.yiyiaddon.m.b.a<"saaiokvavkiwr","7bitoNtPJpmTBL/9atfXHibEWi7IqfLaWFlkTb1kKO8=",-4402455817153425017,-6945308302219734125,5876726800745837552,-4363939280659580094>()) {
                           case -946742123:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  c.add(var0);
                  switch ((int)com.yiyiaddon.m.b.a<"s1ve9biryzb2xm","Pp1t6CcjnRLKCHdjTLKSFlaJB4V+UbyVaoG7JD3UWHY=",2859667276036345993,4605539271451055135,8087913148280019162,6092101055751254080>()) {
                     case -638164417:
                        return;
                     default:
                        throw null;
                  }
               }
            }
         );
   }

   public static void jF() {
      if (b().player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s13sjih4vxbsgo","sXD0VMD23t9+nsGroM8TR3Odwz9D7BDYLWf/O6pLLto=",-3885536061655112051,-9055280968976468128,-6411089383678230725,-8522456506483084897>()) {
            case -410271922:
               return;
            default:
               throw null;
         }
      } else {
         String var0;
         while ((var0 = c.poll()) != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2r05gpxr219t4","H5WJ0hdDVXmwFeR20WhKWTCFbdu68xwoEc9zFT0d5M0=",-1185465115014059409,3637793370064840411,7534327829427276133,-2612361084090322243>()) {
               case 868429831:
                  az(var0);
                  switch ((int)com.yiyiaddon.m.b.a<"s2ki6e60ke3g5i","klNloYeB1xcfAcA/rkK6Fehbhaub8CHJP4HoMAyIPX0=",-106583596909880808,7627099155789886452,-477663690607714732,-8506741507361357320>()) {
                     case 556585041:
                        continue;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }
      }
   }

   private static void az(String var0) {
      for (Consumer var2 : cY) {
         try {
            var2.accept(var0);
         } catch (Exception var4) {
         }
      }
   }

   public static void ae() {
      if (b().player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1mp7qfvglu4kk","0g80xJ8MYAnO8SLeP2WPMImJxp623JdH5/7mu3h0xFA=",556837707435944065,-3470825861751335614,4196717510715841774,-8786909878948731739>()) {
            case 1419428954:
               return;
            default:
               throw null;
         }
      } else {
         jF();
         if (!aZ.isEmpty()) {
            label28:
            switch ((int)com.yiyiaddon.m.b.a<"s36mgafgowyjeq","dX6dkOUhwK6uHHssnfdhOoFsxLYlITSmao9sKpFa8ho=",-4974357495145496923,3274978705647905624,-4913806209845633375,-7697312146149381715>()) {
               case 2048006496:
                  jH();
                  switch ((int)com.yiyiaddon.m.b.a<"sgw3m1l1ju7d5","LS+Wrm63TwQL74CSoOh9H2G1oZC38dyEtbJsthKRlIE=",-2860685556781358187,4212130959857974413,6852724747614341603,-5298763330020705075>()) {
                     case -1934541294:
                        break label28;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         if (!aS.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"sqpvcia1rw6jm","jnR5dgJPr/FOIRbBz76BgDCIefQg3j9iHd5oNWb6/mA=",-8727516236629576144,-1359143736191049994,7483023547145270674,-5189369912632975630>()) {
               case -535703827:
                  jI();
                  switch ((int)com.yiyiaddon.m.b.a<"s26ukk54fj4c9v","LvReJxTmPgfxgM3kqyKkZrOkz5iyN2ugpeKgEB3iBvI=",-4196051595753163606,327233493065038264,5461637814158298626,-8722221471608311674>()) {
                     case 1112616974:
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

   public static void jG() {
      aZ.clear();
      aS.clear();
      c.clear();
   }

   private static void jH() {
      String var0 = gA();
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2puwlw09m8zat","7OWjH8FK9nFB3gZZVWCTB4Xdm6rlwOMrVc2i7urhh1A=",-8597994445144750825,-6042563390170606522,-6835941316574972912,2110823959892347673>()) {
            case 403514541:
               return;
            default:
               throw null;
         }
      } else {
         String var1 = ca(var0);
         Iterator var2 = List.copyOf(aZ.entrySet()).iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s1mk1499gspz2p","QM2sMySrDSDd94lvjKn/2Tc9SN2s2AincNxwq7n9fDI=",7926773086504322828,3393793523052154538,-5145348616616628869,-1865904631191188497>()) {
            case 924037521:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"shio27kvtx3tb","xSn57fygucnx+JpbDuyT7whmS4ypl+XJIlCG9AENA8s=",1492685518877434726,2727631012737820065,-4043519955572152752,-8251888738457982088>()) {
                     case 1373896757:
                        Entry var3 = (Entry)var2.next();
                        String var4 = (String)var3.getKey();
                        aZ.remove(var4);
                        if (var1.equals(var4)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s56bvtnygy8wb","j9EcJYG0C4B5SKGWiJWQ5/rbZH4sErnKfDtYHn6D0lg=",1793142244836432402,-5637891492984160898,470850608479636869,-1530053896662241499>()) {
                              case 647296477:
                                 switch ((int)com.yiyiaddon.m.b.a<"s24nnicxtw5bu2","PfBj+VUcHaWBrv1BDHb+RzC6abOE3uILbPxuzyuIaBI=",-6399866387197061435,-1250102860795837945,8738014850375118842,-3209809053728532823>()) {
                                    case 1870291379:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           File var5 = new File(b(), var4);
                           if (!var5.exists()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2fyuqgupm4kll","PdprxjtK7BwnLD94RqE+ZS+kv+Ieg7G7SvWQkzJRlts=",6974677818378352325,-5587680548009691475,1949483558756719895,7665142509439027013>()) {
                                 case -128322557:
                                    switch ((int)com.yiyiaddon.m.b.a<"sajw864hnlpao","E0Cwb9vOd/i/2IxJts4R4FOk7dosEIFqAF8AlRaHNK4=",1014342328139337875,-1679666938956120265,-8234028158494048152,-492172286734064123>()) {
                                       case -832768361:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              File var6 = new File(b(), var1);
                              if (var6.exists()) {
                                 label47:
                                 switch ((int)com.yiyiaddon.m.b.a<"suoxlwe567rcp","fQ/s1r6488MQyNtHCdclRDgARO0Mot1a45TyjXj0mUU=",-719407020846805267,-252297700175857872,-2307790183660212562,8179629565891463332>()) {
                                    case -1733398319:
                                       c(var5);
                                       br(var1 + "");
                                       switch ((int)com.yiyiaddon.m.b.a<"shm0wjeo4opo3","lQkGYkpzROMUb+7K1e8Pis/A1zFPKo9iWEhKrC6YTCs=",-7912028675564373527,-8365577259709302160,-4056790295294775165,5711153648996266639>()) {
                                          case 2074853777:
                                             break label47;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else if (var5.renameTo(var6)) {
                                 label43:
                                 switch ((int)com.yiyiaddon.m.b.a<"s17e2xg8umeldt","G50G30FIiRL+pL7Z4kNLoft4O7xMmVl/X13A+6tHYwg=",-3156835200413083779,-2324995211159911875,6739362154440589080,-1443355496450578674>()) {
                                    case -911285244:
                                       br(var1 + "");
                                       switch ((int)com.yiyiaddon.m.b.a<"ss8pu4cmqs2im","FdeiE1RQQqP8digUco17GhSfzhtaRivV1Da+tbM3M5E=",5031228649068887927,7077120987152173360,-2323084234209015988,-1540858126075219941>()) {
                                          case -2017311347:
                                             break label43;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s3ccjs67k9af69","JxU9tIBfgDe6CCVb6lgatrk8BiVflNyIJ+30WLeBW8c=",-2045166925249341651,391333037366630394,8143364473400635148,-8896391786463783376>()) {
                                 case 299071195:
                                    continue;
                                 default:
                                    throw null;
                              }
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

   private static void jI() {
      String var0 = gA();
      if (var0 != null) {
         File var1 = new File(
            b().gameDirectory,
            (String)com.yiyiaddon.m.b.a<"s2vcy7pr0vi528","uB3nN6qd1Y9hCNf8uAsJRPyf2/lkWecsOurW33koxJ7wUm4yZ2z1wXFVIPho7Q==",-4375108355096113119,-2947781174720536989,7055253599980009354,-4830159915240269828>()
         );
         if (var1.isDirectory()) {
            for (UUID var3 : List.copyOf(aS)) {
               File[] var4 = var1.listFiles();
               if (var4 == null) {
                  break;
               }

               boolean var5 = false;

               for (File var9 : var4) {
                  if (var9.isDirectory()) {
                     File var10 = var9.getName().equals(var3.toString()) ? var9 : new File(var9, var3.toString());
                     if (var10.isDirectory()) {
                        File[] var11 = var10.listFiles();
                        if (var11 != null) {
                           for (File var15 : var11) {
                              if (var15.isFile() && var15.length() != 0L) {
                                 File var16 = new File(b(), ca(var0));

                                 try {
                                    Files.copy(var15.toPath(), var16.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                    aS.remove(var3);
                                    br(var16.getName() + "");
                                    var5 = true;
                                 } catch (Exception var18) {
                                 }
                                 break;
                              }
                           }

                           if (var5) {
                              break;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public static boolean a(UUID var0, String var1, String var2, int var3, int var4, boolean var5) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3q6iufej53zif","o3Hjajd2kiZUwIP7KK3elxAAe1GXgDtpaijG/Dmzo84=",5481278213817922529,-745645224842109017,-2719251028915550168,6806660315478440003>()) {
            case 1579999328:
               if (var1 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s37t2t6ymkjg20","4+ZDNdW95I96dnolLOZ6RL1szvss/9obPbO+Y7Kb00I=",-5340478362547905937,6245434754085998400,7233862340904891731,6347280035392136053>()) {
                     case 1956738284:
                        if (!var1.isBlank()) {
                           if (!aR.add(var0)) {
                              switch ((int)com.yiyiaddon.m.b.a<"swx68heevdjhd","+UVLLxjWFpx2vc7IqoT8Vh9XdDRZDuJW13zUagVEJfk=",6926618311799795136,-4015630299407447588,-7583348949865606299,-1829163471642625792>()) {
                                 case -1786733002:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           String var6 = gA();
                           String var10000;
                           if (var6 != null) {
                              label42:
                              switch ((int)com.yiyiaddon.m.b.a<"s1ke71yyz5xbjo","LGRN6uV4p3L9R6kG1QsAQ9oM7qdeh7Da1Z/XtgyGWWU=",-5729340918606844904,5744328938307494764,-5073828967794148194,2961073824538712557>()) {
                                 case 940294795:
                                    var10000 = ca(var6);
                                    switch ((int)com.yiyiaddon.m.b.a<"stfwvksqvn3ye","gru1GeUepxx/x4B8o1JFr7LPsD9rFN7Ganl94M0A9e4=",-6148372475591667454,-6493819540904413250,-9141425474436421894,8052153941227185629>()) {
                                       case -2116933489:
                                          break label42;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var10000 = null;
                              switch ((int)com.yiyiaddon.m.b.a<"s3mhwehg27yjy7","ccwYIK7z9FWzfut4jlUjBt4D0AryX9BBT32WYJQfwEE=",4893532828715395158,2430887146655456248,-7556407749722522094,3056417309106006248>()) {
                                 case -458938717:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           String var7 = var10000;
                           if (var7 != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2d3gd1r2x1f64","1deT/VaehnxwC19rKUAh60T7aRMIg1FkpqMRqwi9q9M=",-993446614620855981,7137420252977288444,2369194289571125257,-77836631910517878>()) {
                                 case 296291121:
                                    if (a(new File(b(), var7))) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1kwzaanj0qehf","nB1KVvExQjHIst1VkCRKe3yiDw0bjECeoCcmKFBzZ7w=",7805575484651565538,-652450395730978409,5891276737736416611,-369015951363521016>()) {
                                          case -1871868921:
                                             aR.remove(var0);
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

                           Thread.ofVirtual().start(() -> {
                              try {
                                 File var7x = b();
                                 String var8 = var7 != null ? var7 : ce(cd(var1)) + "";
                                 File var9 = new File(var7x, var8);
                                 File var10 = new File(var7x, var8 + "");
                                 if (!a(var9)) {
                                    for (int var11 = 1; var11 <= Math.max(1, var3); var11++) {
                                       try {
                                          if (a(var1, var2, var10, var9, var4, var5)) {
                                             if (var7 != null) {
                                                br(var8 + "");
                                             } else {
                                                aZ.put(var8, var0);
                                             }

                                             return;
                                          }
                                       } catch (Exception var17) {
                                       }

                                       if (var11 < var3) {
                                          try {
                                             Thread.sleep(1000L * (1 << var11 - 1));
                                          } catch (InterruptedException var18) {
                                             Thread.currentThread().interrupt();
                                             return;
                                          }
                                       }
                                    }

                                    aS.add(var0);
                                 }
                              } finally {
                                 aR.remove(var0);
                              }
                           });
                           return true;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2ntk9b8kz5hdl","HSJvoBa79Wsibu2t0thr63RMMvFgEIh3gM71q0apMvg=",2050278650710119489,-9222337344444904077,4593468160973031127,1580437789428722946>()) {
                           case 729911761:
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

      return false;
   }

   private static boolean a(String var0, String var1, File var2, File var3, int var4, boolean var5) throws Exception {
      long var6 = 0L;
      if (var5 && var2.exists()) {
         var6 = var2.length();
      }

      String var8 = d(var0, var4);
      HttpURLConnection var9 = (HttpURLConnection)URI.create(var8).toURL().openConnection();
      var9.setConnectTimeout(var4);
      var9.setReadTimeout(var4);
      var9.setRequestProperty(
         (String)com.yiyiaddon.m.b.a<"s2k0yuo5mlc3iq","r6etvHnkrDADOzlzoIEFanZmF+J3LwmJ3BoW7g2vr6b1XjzOlIacmbCY0DuBMLON",-4389000329941457968,-6287315959513927108,1796486323788868392,-163071645487214652>(),
         SharedConstants.getCurrentVersion().name() + ""
      );
      if (var6 > 0L) {
         var9.setRequestProperty(
            (String)com.yiyiaddon.m.b.a<"sd773zvan0z3h","9//NE5IQdxXdSpVEXn3lwyXxnzSniHwhRkZfoW/cqFWP9uEQk6M=",-6978265629187068073,-2806916648626121383,-3069724993188640457,8935088416907158015>(),
            var6 + ""
         );
      }

      int var10 = var9.getResponseCode();
      boolean var11 = var10 == 206;
      if (var10 != 200 && var10 != 206) {
         var9.disconnect();
         return false;
      }

      if (var11) {
         try (
            InputStream var12 = var9.getInputStream();
            RandomAccessFile var13 = new RandomAccessFile(
               var2,
               (String)com.yiyiaddon.m.b.a<"sadc95ppcl2ac","OqqAHbJeMRC869igxBmjlusK7L4hOAsOp5nF1GlLJE8=",-4788949377697022186,3721846909840401339,-7066495384100939128,-3990968571685318012>()
            );
         ) {
            var13.seek(var6);
            byte[] var14 = new byte[8192];

            int var15;
            while ((var15 = var12.read(var14)) != -1) {
               var13.write(var14, 0, var15);
            }
         }
      } else {
         try (
            InputStream var28 = var9.getInputStream();
            FileOutputStream var31 = new FileOutputStream(var2);
         ) {
            byte[] var33 = new byte[8192];

            int var35;
            while ((var35 = var28.read(var33)) != -1) {
               var31.write(var33, 0, var35);
            }
         }
      }

      var9.disconnect();
      if (var1 != null && !var1.isEmpty()) {
         String var29 = a(var2);
         if (!var29.equalsIgnoreCase(var1)) {
            c(var2);
            return false;
         }
      }

      if (!var2.renameTo(var3)) {
         try (
            FileInputStream var30 = new FileInputStream(var2);
            FileOutputStream var32 = new FileOutputStream(var3);
         ) {
            byte[] var34 = new byte[8192];

            int var36;
            while ((var36 = var30.read(var34)) != -1) {
               var32.write(var34, 0, var36);
            }
         }

         c(var2);
      }

      return true;
   }

   private static String d(String var0, int var1) throws Exception {
      String var2 = var0;
      int var3 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s9dl0zbgqvemv","0kXbM7S222tJG0P8iL66iagygv/ci1K06gezIRUDT7I=",5560591845651587552,-6078055698129779045,-3887055693501857230,3867606584458628525>()) {
         case -817933955:
            while (var3 < 5) {
               switch ((int)com.yiyiaddon.m.b.a<"s14tucr06eezmt","ECjv3ws3EWS24ugQQ1lNGnxUoqKpQd4AKmyzDz0rooM=",5390605504244649627,-3083048837885428926,3375357298977081884,-7880916569443479336>()) {
                  case -1596374112:
                     HttpURLConnection var4 = (HttpURLConnection)URI.create(var2).toURL().openConnection();
                     var4.setInstanceFollowRedirects(false);
                     var4.setConnectTimeout(var1);
                     var4.setReadTimeout(var1);
                     var4.setRequestProperty(
                        (String)com.yiyiaddon.m.b.a<"s2k0yuo5mlc3iq","r6etvHnkrDADOzlzoIEFanZmF+J3LwmJ3BoW7g2vr6b1XjzOlIacmbCY0DuBMLON",-4389000329941457968,-6287315959513927108,1796486323788868392,-163071645487214652>(),
                        (String)com.yiyiaddon.m.b.a<"s2g9ak140j54k9","fegmXIeYr6YNBGZvu22i/aewMWPEP1FLVCfBJx4RSnL0Z85ijoTXJTIKru85kvSCJDtOzUy0VEOgCMLsXJYLB/efyPaBdt7u+XIYldMMkcycnCYPlfHA0+ez2nNgCQzosKDQbCEvP1SiyrUNZjOtklESb8SkT7jze5x1Jnsr2k1tX+nAjK+d2l6oc/aQ+1V05Uc8eQ==",-281660507486516643,-7822299539576655258,2895517228194446948,6596556548108330589>()
                     );
                     int var5 = var4.getResponseCode();
                     if (var5 >= 300) {
                        switch ((int)com.yiyiaddon.m.b.a<"s749bslpsgkjp","gZxfNHlJXOsLeCLl3pDTHovG9U3uYUNchnlClcY9lGY=",-9133818819581919846,-6462238882207802944,-1616998334506805481,1417474554121951598>()) {
                           case -302139963:
                              if (var5 < 400) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3bqaosprw7q4s","dsybPpsenBzMeGRe5XNoe7Q+BSHiKx9RhE37fntfqhk=",-2832362220789729522,-647063390945944122,853877254592610802,7738316720847087617>()) {
                                    case 1470643854:
                                       String var6 = var4.getHeaderField(
                                          (String)com.yiyiaddon.m.b.a<"s34lva7qgnb39d","R6Jku+vMZRfl6uWPXz874qIRiqquG0VtN877mFJiRwMmPEa6eAzG3hnedTY=",432872041207710860,6374732679976414561,-1840110913591820288,-3858365247941412695>()
                                       );
                                       var4.disconnect();
                                       if (var6 != null) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2awqou4k8iodc","DKakFymhsPeSHQRRF6ZhWqnsVEPuvyCQdNMRlPmZBbg=",8910422075517375418,-1114645945396101104,584412021805559341,7365205709215862465>()) {
                                             case 357243896:
                                                if (!var6.isEmpty()) {
                                                   var2 = URI.create(var2).resolve(var6).toString();
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1qebi2qmjp4f2","JNCFrYULg9VnIsVG+gkl9hVTdtuahlDzBcBnmF6ZDbs=",-6057981157896644139,2469424219759582607,1962357556907226020,-100884464260171897>()) {
                                                      case -1074739912:
                                                         var3++;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2d7mbqetv6l0","dTjwtug61GeqIjBI7+hdZLzd4r2oxGvGqEAec51P2Jo=",3613054490201055893,-3101597802170362313,6832930178600347215,-7797941264484089046>()) {
                                                            case 910951329:
                                                               continue;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                switch ((int)com.yiyiaddon.m.b.a<"s1tpsi8ayoa5yd","CiiV0r1J1uIp/kCCaPovxEnXy1SWXfxBFYe2sdZL9XI=",-2698504873667182677,3892765278503635040,-6036745421519700698,3906348281388430842>()) {
                                                   case 1128757569:
                                                      throw new IOException(
                                                         (String)com.yiyiaddon.m.b.a<"s3l0mlhsfjvqpv","FuKts4p38C/kQshmPffVM/pIsmoRpXSG8clfjgelb9vKs3HgOOApi8G7wNy+TD1ixfZu55unsvc=",-3987695515132322310,-7955549270276494917,8686514987738424911,-1190475711060107197>()
                                                      );
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       throw new IOException(
                                          (String)com.yiyiaddon.m.b.a<"s3l0mlhsfjvqpv","FuKts4p38C/kQshmPffVM/pIsmoRpXSG8clfjgelb9vKs3HgOOApi8G7wNy+TD1ixfZu55unsvc=",-3987695515132322310,-7955549270276494917,8686514987738424911,-1190475711060107197>()
                                       );
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           default:
                              throw null;
                        }
                     }

                     var4.disconnect();
                     return var2;
                  default:
                     throw null;
               }
            }

            throw new IOException(
               (String)com.yiyiaddon.m.b.a<"s2gfr1yr8ueq4l","W+diprv1i6ubD6YiUBwm+YZxDdXCSZkHEFhuIg0RYzsxoxZ4nhnwoBZdLk6uu699O8b/m4dzdyguZA==",2256195596788354978,-3692239598969764994,3766890499240379970,-7988474012530347550>()
            );
         default:
            throw null;
      }
   }

   private static String cf(String var0) {
      if (var0.endsWith(
         (String)com.yiyiaddon.m.b.a<"sib5stlav97dt","fik6bx2xMDvbulk4sJ73sUF5Q7rFu3N1YldisZmQHsT9eIQu",6904668846807130487,7404860295358373829,-1944883389128015550,-4141466662719963716>()
      )) {
         switch ((int)com.yiyiaddon.m.b.a<"s1nowgl3h8vxvl","osl2nxA6jOZpnrPEOzNAlrJhd807/jD1qlc6oyfgr4c=",3424074011876403034,-8079100964514005297,-497695429902795097,5238292814014068186>()) {
            case 80891474:
               String var10000 = var0.substring(0, var0.length() - 4);
               switch ((int)com.yiyiaddon.m.b.a<"s2qmja8mjh9tbv","ARqCGSeekdIzRLli+3LHqDPzTneCPbsj8VA96d9JaKY=",-577027054487008248,5883222679056949172,-8204847551387348027,6686639076779797888>()) {
                  case -1199274521:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1w13hifavmu9x","/dSe2RH8+vQUdCgJ9pp1kcyR0C8bDLfBpAPldAczIaY=",1628724002978672098,-301674478906519363,2982621677590787634,-4840963885884057871>()) {
            case 1178414187:
               return null;
            default:
               throw null;
         }
      }
   }

   private static String v(String var0, String var1) {
      if (var0.equals(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2c6z78q6h4tj9","kzTO4GCWXMsXrS9PlfYQO/+qYt+Fut4nZ1WtTlPP4GA=",-7148350302172814763,-6633815817974322861,-5600120854694253282,5231105780465315825>()) {
            case 249283828:
               return (String)com.yiyiaddon.m.b.a<"s2y6b7nb09760l","qirUj2Em9I1PXaQaEOxYSFPjaeTWXksXautGkw==",-6810807552051386146,6848060162099991872,-7771704121461219720,-7700585604732975135>();
            default:
               throw null;
         }
      } else if (!var0.startsWith(var1 + "")) {
         switch ((int)com.yiyiaddon.m.b.a<"s2mamhdghrioq5","NbgHJRz+aKiLUrEAW41+q6gyQJBDbelIAR+AfxlBadE=",-5265331307276970018,4607090691394675031,-5022238022703342532,-7044499430960825526>()) {
            case -1207778938:
               if (!var0.startsWith(var1 + "")) {
                  return null;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3ity66fs36aq5","W+MidUzdMVZXDywQneytQFp522C9/PVmKX6qSmidAjI=",-1647982388195550016,4426400998475321346,-978549452253613888,6312094945766754755>()) {
                     case 1220236586:
                        return var0.substring(var1.length() + 1);
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return var0.substring(var1.length() + 1);
      }
   }

   private static String cg(String var0) {
      int var1 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"stiwx6xgdfeut","HaQG3ivaHCkxwAJM57OjyySsuIZc+nEmNUJApmW4PO8=",-4072447833910185116,5522062170256813525,-7476629884288540035,8542120196434442162>()) {
         case -686860939:
            while (var1 < var0.length()) {
               switch ((int)com.yiyiaddon.m.b.a<"s29j42363s2hph","2k9tw0VEK9vIkrvJz8vnmK5Nfz7QL/IKYvnPIkLHMko=",4159618355517704822,7485595466533929711,8186259574725604602,8324978331529396363>()) {
                  case 399704701:
                     char var2 = var0.charAt(var1);
                     if (var2 == '_') {
                        return var0.substring(0, var1);
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s7f7loz3ck6xk","a5fNFaI/hKcBd59EcIuY9fkaPLpUC487tScwD+yY6Zk=",2791111416301520268,2920475027412760445,8749573245499458818,-4961214912640164676>()) {
                        case -1180984789:
                           if (var2 == '.') {
                              switch ((int)com.yiyiaddon.m.b.a<"s27pe7sj1t7bxp","6ztWu8RW5rj59SrMkSYYVpTR94V5xKNxVrpnTVpyNf8=",-4355690576811817276,-7072965491651140741,1962582964888208726,-3661210233848790578>()) {
                                 case -2112119910:
                                    return var0.substring(0, var1);
                                 default:
                                    throw null;
                              }
                           }

                           var1++;
                           switch ((int)com.yiyiaddon.m.b.a<"s1xvklggiyirgf","46ARKJW/46NmKL4UbJJUNh7WEPyEYWYrh6Mvr2PPYFE=",915502061802284971,456809532993739089,-4276245689165000131,-8314149651896381204>()) {
                              case 1847213551:
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

            return var0;
         default:
            throw null;
      }
   }

   private static boolean a(File var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1jzvbyd4h7dop","R7eyWVdf6tVeC7zaCKtZ5AJxAK6bXO6/Mf393imGFPg=",301568404833321021,-4412233593314199688,6239904067436970498,8220037482292099487>()) {
            case -1736286199:
               if (var0.isFile()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3r9w9zydriaf1","7/IxcsU/w7YyJRIlDdf6e/K6/Vz5lKHqA0VlWWRYnhs=",1306310943260009330,-6866445916626185299,-248608028117933507,-6108065009643625055>()) {
                     case 1786257947:
                        if (var0.length() > 0L) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2l5lceikxtf1o","sTY1Pe8pnfHpXnWAy5y9xndHPfvhJFjObMgy+xBLUms=",-7095790118242666981,7351886448131792767,5645155201188595560,6287419086665682385>()) {
                              case -1952152678:
                                 switch ((int)com.yiyiaddon.m.b.a<"s2o46nus3qbbz5","fG820YgZbRxh+SqG2ZCEJK8/8ZEn7kNcZqZBL5yweT8=",-800560999565913570,9085322345844756725,8691565498911939728,3230932305760306656>()) {
                                    case 1344332814:
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
               break;
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s17xflhuo25ij","m8LiYtCUDc47upUJ9IoxVyNvYDAz4ItQnHFvT/99Cmg=",7586949567423230233,3367611566402566907,-8434987083805497656,-2287226023575302763>()) {
         case 1845980067:
            return false;
         default:
            throw null;
      }
   }

   private static void c(File var0) {
      try {
         Files.deleteIfExists(var0.toPath());
      } catch (Exception var2) {
      }
   }

   private static ServerData a() {
      Minecraft var0 = b();
      ServerData var1 = var0.getCurrentServer();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s4kfrwwbk4jsa","Sn/VFQOtzKESI6IUcn3AGOXauKxm1LBxOQeK+m9ORT0=",7386069756099726095,-2215421879433467243,-2918111931206108847,-6897213206004167565>()) {
            case 2061782309:
               ClientPacketListener var2 = var0.getConnection();
               ServerData var10000;
               if (var2 != null) {
                  label24:
                  switch ((int)com.yiyiaddon.m.b.a<"s14ng4uwwm6kd9","n7oX9mTq4gt0sF6PPHl4HHTD7R5721wcCdkOhXb4K8g=",-2207080740731628405,4870605655086534892,-6675142045104131931,5226732847084521910>()) {
                     case -704574108:
                        var10000 = var2.getServerData();
                        switch ((int)com.yiyiaddon.m.b.a<"s37i2es0sn2esw","nBwDXTOS6QZRPp9HS5hKU+dPSDO/Mo80ukpTbRxt3Bs=",-4984140456946841936,-1180142409648414761,3941718094516370623,8058995624086997390>()) {
                           case 71381654:
                              break label24;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10000 = null;
                  switch ((int)com.yiyiaddon.m.b.a<"s2ywa3kbh58u18","tQm+tD80s+BIJZfixw0rW3tU1uxlpyUEKxQT/KVjrt4=",5416497877058103523,3780840266382013180,8509292583985794315,-3758589649472895489>()) {
                     case -1189399883:
                        break;
                     default:
                        throw null;
                  }
               }

               var1 = var10000;
               switch ((int)com.yiyiaddon.m.b.a<"s13kbq0flyw3u5","23KBrJidcoDWTVItwRx715eQC6vP8VCfBRO85U9+SSU=",-1618737362958573473,-8757072703238604763,-3050398036879417302,-6672076800167132747>()) {
                  case 1627195051:
                     return var1;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return var1;
      }
   }
}
