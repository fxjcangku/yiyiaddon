package com.yiyiaddon.i.a;

import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.ItemStack;

public final class b {
   public static final int sz = -1;
   private static final int sA = 6;
   private static final int sB = 36;

   private b() {
   }

   public static int a(Predicate<ItemStack> var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1oop6pcp14f7e","77hTocDvi4+1DUixqkMgn/jjQq8z8SYo8jUHB7vOuOo=",-1345931190253914819,-7785931462139022296,-319466258179058918,494143217641700754>()) {
            case -2082178745:
               if (var0 != null) {
                  int var2 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s2qhv0mvnksxay","bOXsLr/kBa+onVnzMdAdH/YxdDQrPzv1Ei99CGXVyUE=",-7297158768070864959,3602154048220148568,-5471476377712083121,511692651245852406>()) {
                     case -1748656591:
                        while (var2 < 9) {
                           switch ((int)com.yiyiaddon.m.b.a<"sb5fdd9no7stn","ga8rV8Z9ayPoUXnORMBbfcLJ61teQw0F+zwdreHPMug=",-6429928753047561738,-1990211903657062012,128467809817465091,429734475557967522>()) {
                              case -2030964786:
                                 if (var0.test(var1.player.getInventory().getItem(var2))) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s276qgt3yx0lsu","cGo6KUAVIvcLcY8sJgGDEfY1SNe8bBfkfUYeuZzTSW8=",631172664077584602,-2952733125837409633,-207298234555330845,-2977180486301414319>()) {
                                       case -672260052:
                                          return var2;
                                       default:
                                          throw null;
                                    }
                                 }

                                 var2++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s8gf3i14xzz4g","yZVJR+/GsPO8LdO5ZgFiG+CUULIlaqNU/U+pVuakue4=",-2102312721238434978,4599501286898188916,-1509654846682035286,-139273662870196433>()) {
                                    case 45483778:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return -1;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sq6d8y5m2l0ys","zaRoy0URaNWdRb7TgsHGH1u7028NapcEdF/5CxrZjC4=",780926786391016530,-8540627620233820686,-2480282360510020156,-2542828102175141614>()) {
                     case 998126753:
                        return -1;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return -1;
      }
   }

   public static int a(Predicate<ItemStack> var0, int var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1x30xwmq4147d","w2AMm6YazvYYefXHLmF2lO7fYtYbk8RqSxNElzc00rc=",4494280059869819817,5874060257760181015,748834556454508013,-8406601425579166809>()) {
            case 1470416707:
               if (var0 != null) {
                  int var3 = Math.min(var1, var2.player.getInventory().getContainerSize());
                  int var4 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"svye90k7lz1t0","7PKd50t5pSrvU91wbpzIdV1M+EyYXoP7nZXEoAHgkmA=",-6888126250934211767,3307022427097304812,-1856685622295710898,4849646242763420524>()) {
                     case 256830185:
                        while (var4 < var3) {
                           switch ((int)com.yiyiaddon.m.b.a<"s13h3i9nak9le1","Q7mJLtTw+vD8jMfuceyMTEVLjleX2jlVU0eBu1NeLKo=",8071201229297726014,-1177126780329494836,-327494607039120503,-3519136775233387104>()) {
                              case 91772329:
                                 if (var0.test(var2.player.getInventory().getItem(var4))) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1l040uv4ya78y","TzIRNQBftlxLxJWUE3rLc6aNOOT895i1E41G4urCwec=",2481785020834404415,-7079632467552624535,5669562558005479450,-1513202286289849981>()) {
                                       case 1349691460:
                                          return var4;
                                       default:
                                          throw null;
                                    }
                                 }

                                 var4++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s3iea56957fljv","ByJTVylJBX2AKc5sMorWNU/qGu1IAGT2w7xGaetSSxU=",1900745676792743413,-8791115665657320235,7349490992807849443,-1576032711559122975>()) {
                                    case -128441054:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return -1;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sga4g7s2kutiq","DFwgImJ0uZMqqgmKeFF56Yb4pQu6EW+pd09ENcLpi6s=",-137965469322873640,-242177233679745182,-1345915948068470012,-5294259029422012898>()) {
                     case 1661505753:
                        return -1;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return -1;
      }
   }

   public static boolean q(int var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2hdrl0jumjafs","75F0lJhWCQ8B/RLKAluDttszqOBtqUrMdK9TpEzhC6U=",-3409719938184242935,-4746897817563781151,-6241359673960986435,5819341240872316398>()) {
            case -1032098070:
               if (var0 >= 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s31sd1kuoderxo","6bIwaIslhM6Al81sXEZBR8zF8tDj9QCMFK7HBXxNqJI=",-5156565398419783123,-2119980248529516106,-2451977448194616961,-336393376319548402>()) {
                     case 1893329969:
                        if (var0 <= 8) {
                           var1.player.getInventory().setSelectedSlot(var0);
                           if (var1.getConnection() != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"sdl0fu3e7tssa","zSEzU3TyfARclzL04Mrcqb5E2tG+paRtuDO7Ta114TU=",-2115492201658192015,2350483401609012029,3386990178934821966,4350531947053874713>()) {
                                 case -513172766:
                                    var1.getConnection().send(new ServerboundSetCarriedItemPacket(var0));
                                    switch ((int)com.yiyiaddon.m.b.a<"s2e55ichdte0w3","5KOymUkis/c2EHCbyCwn6Tt66r3Talk6syrO44uOB3M=",2657509377181118766,2948020460511844743,7953590243854975712,1824649874806176699>()) {
                                       case 1877793443:
                                          return true;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           return true;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s19il3eqmhm7zr","BtqhSsrd7fqdTIzLlHK3bMd6iT87Z69B+22S//RVPTs=",1018321327392254952,5506873379462151421,6041062890068182416,3368315095231667353>()) {
                           case 986629207:
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

   public static int dy() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2rc2ms3epmpf2","igJvUjZSfNrvdV86pn3zqVyJ19LgEiJz4dgdeK/g52I=",-1541158499506264323,4827189412578448843,6774072248587576946,-8348165001059729970>()) {
            case 2100968410:
               switch ((int)com.yiyiaddon.m.b.a<"s1mi4v01ydjz4s","/5dIhw7Izs3ma1nQcvk7AqVLYg5H5tpnox+UogyF89c=",1756770258756092562,4431870325954236825,-4366226363792547004,-1022266636497207217>()) {
                  case -1642105806:
                     return -1;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         int var10000 = var0.player.getInventory().getSelectedSlot();
         switch ((int)com.yiyiaddon.m.b.a<"sf2u7xq9z1r9x","GiPNHShO7yfSBFKCvppAEcOWlJIuo05rW5duSr5rD5o=",2604123295692695518,5844956687225230738,891171267412860624,-3703953104336003419>()) {
            case -1139892567:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public static boolean r(int var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s293qq8x26kmui","3gDf5ttNnWDL1YuVhAAiTAWEADXolhz5uTxudQuws6w=",-383409662846870708,412056282228914297,3940263943501760558,-3012518476641477333>()) {
            case -1854709665:
               if (var1.gameMode != null) {
                  if (var0 >= 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"seqdpd11idsa1","gRxvKUGxq7M+0WMC5CS55AAs7pjUCKILkgfEhRUCnQY=",-3891170836713060321,-4612835058664330668,-92950359298353003,3421891125175145753>()) {
                        case 1291929411:
                           if (var0 < var1.player.getInventory().getContainerSize()) {
                              var1.gameMode.handleContainerInput(var1.player.inventoryMenu.containerId, f(var0), 6, ContainerInput.SWAP, var1.player);
                              return true;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s31tvolxokncr9","JX23mdI686GQBFSTedx8i5Zjwb5ZMT3iwtH1rkpu48A=",-5505603933924616412,-8107156033193510974,4772074718827659292,-3860666359931680327>()) {
                              case 380473424:
                                 return false;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return false;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1hxhk9tt6m1i0","TQ/m7gI6BdFhy5UAnxtLt6cDjaw8MXxfiRWpGuvJV1E=",-1738447675748555884,6156491262127637960,3581322592694597481,-5335996545484742347>()) {
                     case -1399239533:
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

   public static boolean s(int var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2973w0zbyjlrl","vZC/yWky4zYc2Q2yY4Ig2AEDchq/dn2HzDkDfeFvG2c=",-4613592749314412263,-2184236300412868280,-2568701048401063168,3531136818469081125>()) {
            case -184164866:
               if (var1.gameMode != null) {
                  int var2 = var1.player.getInventory().getSelectedSlot();
                  if (var0 == var2) {
                     switch ((int)com.yiyiaddon.m.b.a<"s7nni3wiwhlc7","fMTcgu70DWSB1P6QEtKcs9lFam77/bTM6CNqyrkwyLs=",-2900194863241532954,-1633221198773704034,7430570628445114442,-4019877042794794974>()) {
                        case -913268665:
                           return true;
                        default:
                           throw null;
                     }
                  } else {
                     if (var0 >= 0) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2grhelo17l3b0","YJD3NBiE70I8gZDOlxPUFXxsl271MuC8fcVrRZ450U0=",7072517115814228863,7077006354708280164,-8876839405060619641,-4539362984646721590>()) {
                           case 1137489440:
                              if (var0 < var1.player.getInventory().getContainerSize()) {
                                 var1.gameMode.handleContainerInput(var1.player.inventoryMenu.containerId, f(var0), f(var2), ContainerInput.SWAP, var1.player);
                                 return true;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s9iy9it2s0ga9","TL5qPFw5cYaOJpd0VZXaC+vqpQDj+UcGxfMBKwTo6vA=",-2035374847261205643,-604405223736030117,3311910593307940626,-5239228220902381032>()) {
                                 case -2014927210:
                                    return false;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     return false;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s10vjbj0lr4e5","E6Z5z3dtY0SxlbVt+wnuSG3IrLyf/I7Ypi6byifGG6w=",-8833773384292481327,-7161620017321064817,5037833233027894969,112559308862117310>()) {
                     case 2055073066:
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

   private static int f(int var0) {
      if (var0 < 9) {
         switch ((int)com.yiyiaddon.m.b.a<"s3sl1ldqgzkoby","TYlwitUPKj5tG4QoKftMmCJnBu5n4rO2zwkRu0MpVX0=",7725556798683678591,-1888880486766989214,4548388604674025919,-8600546333765978473>()) {
            case -97235447:
               int var10000 = 36 + var0;
               switch ((int)com.yiyiaddon.m.b.a<"s3mq34g5hyut69","joMcHq22JQ+kHpYDvmLHz6p2LifdWWR8SZO+WLaPxC0=",-9200297707956420100,4532034093636601891,4138627744716700652,-8770583029155833292>()) {
                  case 1193370256:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1cz0w8kz8smee","ZWK+HsnKcdTCTN8ixYWC3ZJiG7ZWamz6iM8ARsQlpdQ=",-4288493083273980202,-2125083671895542699,4934833652306623210,-4902901382174180031>()) {
            case -2127864201:
               return var0;
            default:
               throw null;
         }
      }
   }
}
