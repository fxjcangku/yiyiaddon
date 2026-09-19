package com.yiyiaddon.e.n.f;

import com.yiyiaddon.m.b;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class a {
   private a() {
   }

   public static List<com.yiyiaddon.e.n.f.a.a> a(BlockPos var0, double var1) {
      Minecraft var3 = Minecraft.getInstance();
      if (var3.level != null) {
         switch ((int)b.a<"sb84i7r019xto","PLT2ntDT8auYyG//2n2iFODCO2aoelllFnE/X5k35c0=",-7527308940654555824,-2114651468525208040,177526175687269727,-925266823297264578>()) {
            case 643517162:
               if (var3.player != null) {
                  switch ((int)b.a<"s1tx6c2qy1napt","4POuzeEwLe/mhc8qNEgFT8brwrcTf0IUfHAPnxsjhFY=",-597497092103482086,9102388406967502944,-2414832426736373,-4827194033000522802>()) {
                     case -376811650:
                        if (var0 != null) {
                           List var4 = a(var0);
                           Set var5 = Set.copyOf(var4);
                           LinkedHashSet var6 = new LinkedHashSet();
                           Iterator var7 = var4.iterator();
                           switch ((int)b.a<"s1bvk5qtvl4uas","EN+a6xDHP3Am5FP6jM09m8cE9wSrRvqJeDvKG63AOAY=",2362653575392357449,4961819803786247430,-2852035889713111598,-1609581255062170859>()) {
                              case 820279360:
                                 while (var7.hasNext()) {
                                    switch ((int)b.a<"s3qt8dq4do30d6","pN28WiOaleLB9CFzbxK+NHJkt3Jip/dMl4JFPY+RKgU=",-2773367196716520339,3326356504264335065,5940784642437893867,8772312988601769582>()) {
                                       case 860365515:
                                          BlockPos var8 = (BlockPos)var7.next();
                                          Iterator var9 = Plane.HORIZONTAL.iterator();
                                          switch ((int)b.a<"s30il7mxir32x2","oULFl0RTXEQoQa0dM9q9z8J7GUyr1kTH5y2Gdtm6OEI=",-1891184153524306945,-6462064487759483400,4169959155390313960,-6905912644481684129>()) {
                                             case 807194202:
                                                while (var9.hasNext()) {
                                                   switch ((int)b.a<"s1e74sdyff1unb","Lp8OYGwsQ68ip9Ep3dh/ZJ7Ro8dvgK/vcTXP8ZqSyEQ=",-2364512132477519923,-3887068452354428067,-4925410971073356108,3613600542219517986>()) {
                                                      case -1698312145:
                                                         Direction var10 = (Direction)var9.next();
                                                         BlockPos var11 = var8.relative(var10);
                                                         if (!var5.contains(var11)) {
                                                            label84:
                                                            switch ((int)b.a<"s1cyoybgacp0c5","cOhuOneC5NlM7PnEDhr9QZvIL5dq/xr0piPbcngjZUk=",5411319905778043512,-8586665641088331311,3157326014489355152,-3900562254268097203>()) {
                                                               case -1205551611:
                                                                  var6.add(var11.immutable());
                                                                  switch ((int)b.a<"s3e1r0lmr4mzbg","VQRZoUdhvSr0TfEe2dAH6YVhVcJrXhLHW+fBoZPK/l0=",5385093279258215079,2851621766353575275,-3222344033108805875,-6130065948589411772>()) {
                                                                     case 1233170730:
                                                                        break label84;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         }

                                                         switch ((int)b.a<"s9oz1us1aay5z","Bwa9Z7wMUNpCOIgZAJuJ56FT4VfGU7kxUtjHvqv/uXE=",5125970180993720958,1829974521727765893,6852952005876322770,1620061365522763626>()) {
                                                            case -363608992:
                                                               continue;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                switch ((int)b.a<"s3h12ezve7p11n","vOfW/ao0qC1zqCB93ZuNSPRo7TLhS+KYID1JUMTBB/o=",-8426690375456802676,-5529080756564834806,-8947339916852060517,8971255767976396434>()) {
                                                   case -508477590:
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

                                 ArrayList var12 = new ArrayList();
                                 Iterator var13 = var6.iterator();
                                 switch ((int)b.a<"s3rw5m1zmqoo8u","BxAYDyIi0SrroGoLjNLbM67QMy3eUuzA6NVfIu7TPoQ=",-7477729767925108577,5281957306921105734,7197978860625114940,4746092584427971007>()) {
                                    case -38772876:
                                       while (var13.hasNext()) {
                                          switch ((int)b.a<"sz80k7nqphxf6","JZzPI5PAA6u30vJip0cwE5LCc7QjokXydrw8y8nrdF4=",-7684118922973378276,2704067327856276941,456521767979291118,2349788744090001856>()) {
                                             case 1886017841:
                                                BlockPos var14 = (BlockPos)var13.next();
                                                if (!a(var14, var5)) {
                                                   switch ((int)b.a<"s1gkhkuakcsavf","p85jFdQuj9JMjwAqypnLFk/pgyPe1eBJ9GNPoW8WIqw=",4517242123088348151,16251755084609704,-7076177398566411228,8363073515314295378>()) {
                                                      case 105639661:
                                                         switch ((int)b.a<"sbdr00egdjhbw","R3LIyDcETr5ivY+GYVrVUwHhdab9GzXLKPRFbSTLKMc=",5620059411273573562,-4624574694180825251,600327995945917306,2865400594235707766>()) {
                                                            case 1115067772:
                                                               continue;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                } else {
                                                   BlockPos var15 = a(var14, var4);
                                                   if (!a(var14, var4, var1)) {
                                                      switch ((int)b.a<"s2fujda3wh0u19","jOJ74Ndsyxdl9nY4hTaWkx4uhBihe9Lobkd0Vxvh1qI=",-5365869746070028152,374688445819090257,-5211119784576008897,-505837221034490071>()) {
                                                         case -335321827:
                                                            switch ((int)b.a<"s5xoxckmz5b1k","z/Sw+u9ctzhg37ZWhr1ExBbYynabLbY9jKEirUojeOc=",-8430055171073433033,7431735891875867154,1117293908424575218,-6110543176237381308>()) {
                                                               case -585256076:
                                                                  continue;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else {
                                                      var12.add(new com.yiyiaddon.e.n.f.a.a(var4, var14, var15));
                                                      switch ((int)b.a<"skkli91t9d9m7","WinZy6StGTbiTPy0Q7sEm0qHxtTe8PThCvSMi3pwrIU=",-7657395221280559737,-8290853926753152497,-7134453259756728375,518868703920155843>()) {
                                                         case 1013800941:
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

                                       var12.sort(Comparator.comparingDouble(var0x -> d(var0x.m())));
                                       return var12;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)b.a<"s2049jglbejk8k","BMUx2o5VeqZzfr+YJVpRuqYmJHMneAthW9bYwWpqTIo=",-5064299743698723325,1164168167951614117,6475136375444801900,6768389231316127278>()) {
                           case -1295563090:
                              return List.of();
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

      return List.of();
   }

   public static boolean a(com.yiyiaddon.e.n.f.a.a var0, double var1, double var3) {
      Minecraft var5 = Minecraft.getInstance();
      if (var5.player != null) {
         switch ((int)b.a<"s2g2h3eun5cska","lhTNpSKQOyAR7EieNEyLUz/oaInZQBiEFZxs1MT4zxA=",4263309774073959845,1787283460724596972,-4906645504758283940,-3322292290173353176>()) {
            case -1398824192:
               if (var0 != null) {
                  double var6 = var0.m().getX() + 0.5 - var5.player.getX();
                  double var8 = var0.m().getY() - var5.player.getY();
                  double var10 = var0.m().getZ() + 0.5 - var5.player.getZ();
                  if (var6 * var6 + var8 * var8 + var10 * var10 > var1 * var1) {
                     switch ((int)b.a<"s1n9iturbnkqzs","k2X0HMOWxOOEDvPliVT9ScqiCoN1GSsdNfEoo+ez4to=",-8589843745208253116,-478000501773597616,1455954441137873658,4302992316490237426>()) {
                        case 1438743738:
                           return false;
                        default:
                           throw null;
                     }
                  } else {
                     Vec3 var12 = var5.player.getEyePosition();
                     double var13 = Double.MAX_VALUE;
                     Iterator var15 = var0.aM().iterator();
                     switch ((int)b.a<"sx9rshjj69ka6","cNVAAOkwUbtAXKK7ZkG0N0EdVsIgFjPQdYLiJE47P0E=",1467970580056904745,-7250145430747789253,-3716371861517796772,7721717202649740550>()) {
                        case -713786958:
                           while (var15.hasNext()) {
                              switch ((int)b.a<"s3es6dsnj3tnzr","+Sy9fSwSHhKJsM/THxUEZ9sPxLvH3A1fZHkekREqWSU=",7843276549211572641,-2671241185632823477,-2515350709247094589,8523028663488445191>()) {
                                 case 691953706:
                                    BlockPos var16 = (BlockPos)var15.next();
                                    var13 = Math.min(var13, new AABB(var16).distanceToSqr(var12));
                                    switch ((int)b.a<"s1ubcesfxkomq0","SO0wDi9W7+qoN6rSrRT4CHHl5OF1EMSNQsHRDgw9at0=",2838218963400797307,-845545555331332676,4665461337145079418,-9072396569266974109>()) {
                                       case 774530440:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (var13 <= var3 * var3) {
                              switch ((int)b.a<"s3j17lruwuntj2","YtytgqS2JRBV/crYXbtcLNLF+gSO9PiedQcKTlPP/0E=",9136518253000031514,6130991633306716102,941243243809878329,-6650720219067709678>()) {
                                 case 239366922:
                                    switch ((int)b.a<"s3h1h9zp983qug","kbGbrdq4FA81YNCDIzc1B0QX3LCGqaGSrBPXzTK+R2o=",5696143618209911821,-5649219173421287070,6948069217988779951,-6640399655290283505>()) {
                                       case -190339539:
                                          return true;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              switch ((int)b.a<"s282l51beki2yh","qj52K0V4i6CMXivZjKR2mCDNtUcMxjFmb4nREyTWWN4=",-5072043042641716756,-1954939865126603923,7597266007698267340,5798751994853427060>()) {
                                 case -950754319:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)b.a<"s1foonts9jq32o","7FseGbZAEHfAQ+kmTS/AS6U90r/Ei2dyFda+aZyFp8k=",8186643522229057937,-825898589165389090,-5178865762783540972,-4346777982483088302>()) {
                     case 613956041:
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

   private static List<BlockPos> a(BlockPos var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.level == null) {
         switch ((int)b.a<"s3s08oy7uryeja","OimFYHdEXRLEMEFgaUyzbF3/AYk5Jf+bXqypbS9ppYA=",-2534144789152372099,-6621833390356415485,-5203812656119031396,-6218608112623302055>()) {
            case -1833452622:
               return List.of(var0.immutable());
            default:
               throw null;
         }
      } else {
         BlockState var2 = var1.level.getBlockState(var0);
         if (var2.getBlock() instanceof ChestBlock) {
            switch ((int)b.a<"s1z3ls6xgtf9g8","aDt4/IMJDy5SK2TwYBpsWG4NYNEPUOyiJ3F+2AXsDKk=",-778429949352422569,896158287391394835,-7509528939586164779,-6801853740940930468>()) {
               case -2080159353:
                  if (var2.getValue(ChestBlock.TYPE) != ChestType.SINGLE) {
                     BlockPos var3 = var0.relative(ChestBlock.getConnectedDirection(var2));
                     BlockState var4 = var1.level.getBlockState(var3);
                     if (!(var4.getBlock() instanceof ChestBlock)) {
                        switch ((int)b.a<"s1k7z4m9oykxyd","IUfV00E0z8+hnqgo0pD7+l8+qObfrlCM73qe2sxpK60=",1891379092366143709,3269243038784986621,-5868246134712053380,7678672439714698056>()) {
                           case 753221034:
                              return List.of(var0.immutable());
                           default:
                              throw null;
                        }
                     }

                     return List.of(var0.immutable(), var3.immutable());
                  } else {
                     switch ((int)b.a<"s2sdbxfphq7vgv","LywNMuCjlUjJ7IOGCxzYc0pQ7m5uk/UPj0yVFVqlACo=",9038448831654978484,-6864836878371743588,6612139708045657218,-194389560706711359>()) {
                        case 1717888506:
                           return List.of(var0.immutable());
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         } else {
            return List.of(var0.immutable());
         }
      }
   }

   private static boolean a(BlockPos var0, Set<BlockPos> var1) {
      Iterator var2 = var1.iterator();
      switch ((int)b.a<"sh866f7wxqdyh","WsqTeKcFMAKA95OkoYwAiMB4RGmaG/2DA2IR327P0pw=",-6473291947328844757,1910243926470293813,-5478183982694585883,-232219574694617957>()) {
         case -1695967674:
            while (var2.hasNext()) {
               switch ((int)b.a<"sx3x7fu8sl54t","PmNvPLsyUGA+1IwRiAxMkgoofrln5qZJyzcKH6BKQNw=",7749105908087554747,-1104140510771002945,-3474615225424563936,-3676685772050170950>()) {
                  case 1192092876:
                     BlockPos var3 = (BlockPos)var2.next();
                     if (var0.equals(var3)) {
                        return false;
                     }

                     switch ((int)b.a<"scp1teyxrr656","O2D8LYB8vU1hbtHd6NMox4yOyizBVmWHQPxmNKSrkbo=",3647614391839048930,684725266749443210,1439647949954976286,4790489612544789592>()) {
                        case 243444557:
                           if (var0.equals(var3.above())) {
                              switch ((int)b.a<"sydx9ajpur7jj","AewpGajiKixmkNGGYo9nPZdGF9yy0uVMrGBbZIbg+GE=",-1748541203027866362,769678298401492303,-102923789891691563,-4824312460821878996>()) {
                                 case -1290816052:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)b.a<"s1f67nhhg280e8","bVkHzWbv8j+C8GnlH3fm+jdbJIXqd6BU7n2pIgbkrwI=",8538000183257276249,2090700545112557482,-7423261001892747602,5766567599478420838>()) {
                              case -1561737181:
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

            return w(var0);
         default:
            throw null;
      }
   }

   public static boolean w(BlockPos var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.level != null) {
         switch ((int)b.a<"sl4jrxslh4yty","kvEmtiFQ3Xw2JJMEqE1Uex+3Qs95TDyw3jDCZ4mY/68=",5450601268121001657,-4857699189679677125,-4964638289321944288,-7301793320910185885>()) {
            case -95708275:
               if (var1.player != null) {
                  switch ((int)b.a<"s36ddxi6i3gir8","n6kQgXQbsVcmeYM52zvi5ZBEOFliF1YtL7ikobFLS6M=",1306968055839137624,-2925543299022044231,8541118553528160760,-4813372350526605473>()) {
                     case 1570478205:
                        if (var0 != null) {
                           switch ((int)b.a<"s3io7qya6j11kr","fxASheKrssqO8e0dZsN1GwxBi8w59Of3yN+kMIqTey8=",2626345166179986362,5673048399004660113,758136007368652625,6268706225516700506>()) {
                              case -1119022536:
                                 if (var1.level.isLoaded(var0)) {
                                    BlockPos var2 = var0.below();
                                    BlockState var3 = var1.level.getBlockState(var2);
                                    if (!var3.isAir()) {
                                       switch ((int)b.a<"s1oowenexwqevn","m6radV+1gA2ClB4vPyfa20OgBVbNHOTHuqF14jbALSY=",6450850284664351871,4867424301078698198,8465937605218582060,1478548760003729242>()) {
                                          case 1301395394:
                                             if (var3.isFaceSturdy(var1.level, var2, Direction.UP)) {
                                                BlockState var4 = var1.level.getBlockState(var0);
                                                BlockState var5 = var1.level.getBlockState(var0.above());
                                                if (var4.getCollisionShape(var1.level, var0).isEmpty()) {
                                                   switch ((int)b.a<"s6f6c2i89l7ae","ZlibN++hnOEaU6PvgDxhJ7MuQFkhNn3ceP3dUKQwtg8=",7973931161937796353,8379044149717025204,-5351805509549913068,8176352172294513613>()) {
                                                      case -1328587498:
                                                         if (var5.getCollisionShape(var1.level, var0.above()).isEmpty()) {
                                                            double var6 = var0.getX() + 0.5;
                                                            double var8 = var0.getZ() + 0.5;
                                                            double var10 = var1.player.getBbWidth() / 2.0;
                                                            AABB var12 = new AABB(
                                                               var6 - var10,
                                                               var0.getY(),
                                                               var8 - var10,
                                                               var6 + var10,
                                                               var0.getY() + var1.player.getBbHeight(),
                                                               var8 + var10
                                                            );
                                                            return var1.level.noCollision(var1.player, var12);
                                                         }

                                                         switch ((int)b.a<"s1mmebo8vgsb7a","iYHOj7hV/yVEORvjyZZDMqUIGpYyVvAqhiHleNOuzg8=",-2869741769367142771,3551661366188271521,6899772442620329785,-395911627287037719>()) {
                                                            case -182508989:
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

                                             switch ((int)b.a<"s1g0c6ognxhn01","Ene7UU+ziMJwJowAIBsf+TW3LTE6IwhWgcuXsdzfgWg=",1449447158945515068,-6275574989361256504,-7798814432633639146,4500511823950967175>()) {
                                                case 2134385871:
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

                                 switch ((int)b.a<"s6pf6gcalhw87","FlM+DBUAiChhRs0Lt7GabBYO1xMKq84srSi5UOUs9JM=",-5868405273594410725,-5060819983293961435,7417763996638238754,6655396634485322779>()) {
                                    case 44511169:
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

   private static boolean a(BlockPos var0, List<BlockPos> var1, double var2) {
      Minecraft var4 = Minecraft.getInstance();
      if (var4.player == null) {
         switch ((int)b.a<"s3ckv3lv22je9n","UHtfSsrjfyhNzBjPU9Ii0i7v9MjG47B83VBK0ML7fdY=",3530865180827750053,4991379463124669142,7709387774742393339,4789387348028848338>()) {
            case -830556195:
               return false;
            default:
               throw null;
         }
      } else {
         Vec3 var5 = new Vec3(var0.getX() + 0.5, var0.getY() + var4.player.getEyeHeight(), var0.getZ() + 0.5);
         Iterator var6 = var1.iterator();
         switch ((int)b.a<"s2ic0ti5v71lf8","h6MWGlrACOFUCHGDTiWeeXGDr1SPmgFnR7bY8KPjFAI=",-8075063745290801646,-3195670127392084574,1591990849568054917,-6352975485185422371>()) {
            case 1630400918:
               while (var6.hasNext()) {
                  switch ((int)b.a<"s1a6ukm5h6m3m9","0aYnpLiW7Muq/Miuy5YVm7LUp8yuzfQR0+TPhRqZDcI=",2311508998393731048,-4150999098648574689,-6694314416252127955,2637561524241392533>()) {
                     case -1945212378:
                        BlockPos var7 = (BlockPos)var6.next();
                        if (new AABB(var7).distanceToSqr(var5) <= var2 * var2) {
                           switch ((int)b.a<"s2y1y3kjmt6xbg","K4fk/zuWwWOTY98PFMHtIMoPyQxlBxntndj8F4weNWU=",-4693173463626810788,3961403355386160018,-303551627913333763,-8713970696394541877>()) {
                              case 1882235416:
                                 return true;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)b.a<"s297r0z4mfs7mw","RkD/9pa6GOCwTkKHQy94ye8b5SxeQ3RK0MCbJ6l1LPk=",7411357877979442649,-1594045037218550206,-3270172650463319769,1370594830432023545>()) {
                           case -686500485:
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
   }

   private static BlockPos a(BlockPos var0, List<BlockPos> var1) {
      BlockPos var2 = (BlockPos)var1.get(0);
      double var3 = Double.MAX_VALUE;
      Iterator var5 = var1.iterator();
      switch ((int)b.a<"s39iitrhg9345p","m5TMRC75wvIA8tnyJSUTP/glYzoUs/YwAy0rYU5PU0g=",-1449519303717673340,5397218329634900599,-797628458024386148,-1251601761332986337>()) {
         case 230926720:
            while (var5.hasNext()) {
               switch ((int)b.a<"s3snesy1xv3xgv","7+SO678j9dH5jc+lbFoA5oFfwNBGifJi9SeSX4JLO6M=",6044507198181104353,-7212785691591985879,4028860575407372652,128008405047218404>()) {
                  case 1565722470:
                     BlockPos var6 = (BlockPos)var5.next();
                     double var7 = var6.getX() - var0.getX();
                     double var9 = var6.getZ() - var0.getZ();
                     double var11 = var7 * var7 + var9 * var9;
                     if (var11 < var3) {
                        label23:
                        switch ((int)b.a<"s3k6kdf3agurmj","AqURQMVKDg5YiY+heyPNhkGBtWmTjXY5aLpKtBnQDos=",7937028688789855392,5442216629444060716,5943033628759337790,-7996480085872573963>()) {
                           case -459927181:
                              var3 = var11;
                              var2 = var6;
                              switch ((int)b.a<"s3ajrqufkd49eh","FrbKZtYT+tlIZkxk/RnWV48CE7V8fK9qAEM7q7oeRgI=",-7211169723600698642,6915989570794734073,-1155127285757622733,-1018926434173871904>()) {
                                 case -1671238713:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)b.a<"s3qa7h1ex8e7u2","5AB/ZyOVfg3F1WukAjyNQzrHjR7N1jNL41upLfUeGVU=",-1865140453585335653,6170783187143169942,-5333990063172720266,-6578854627116660669>()) {
                        case 669980007:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var2;
         default:
            throw null;
      }
   }

   private static double d(BlockPos var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)b.a<"sooasyq23tnqj","vi2oPX8sU81UGHX7zs1emA/s3CAus1VfS4prJjeHpLw=",7816161918246569500,-2022978006352637235,-2786634495731283877,4364702279659854754>()) {
            case 84558261:
               return Double.MAX_VALUE;
            default:
               throw null;
         }
      } else {
         double var2 = var0.getX() + 0.5 - var1.player.getX();
         double var4 = var0.getY() - var1.player.getY();
         double var6 = var0.getZ() + 0.5 - var1.player.getZ();
         return var2 * var2 + var4 * var4 + var6 * var6;
      }
   }

   public record a(List<BlockPos> bF, BlockPos C, BlockPos D) {
      public a(List<BlockPos> bF, BlockPos C, BlockPos D) {
         bF = List.copyOf(bF);
         this.bF = bF;
         this.C = C;
         this.D = D;
      }

      public List<BlockPos> aM() {
         return this.bF;
      }

      public BlockPos m() {
         return this.C;
      }

      public BlockPos n() {
         return this.D;
      }
   }
}
