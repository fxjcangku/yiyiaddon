package com.yiyiaddon.f.a;

import baritone.api.Settings.Setting;
import io.github.humbleui.skija.Canvas;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.EmptyBlockGetter;
import net.minecraft.world.level.block.Block;

final class c {
   private static final String BL = (String)com.yiyiaddon.m.b.a<"s37ln02nahjl7c","/iuY7f9UTiB9UesFb+vwqTcK4AjOBtEu8VgP2gN42cabxtZak3Wy54iaHDS1xs21oBk1JQ==",1076271973160389363,4922095215599132563,-952748466728795577,5212692174732158894>();
   private static final String BM = (String)com.yiyiaddon.m.b.a<"s3317oz19jbfh5","6PPblKdyGvgvqzhMa9mTc/kkCBTNhyPPpR7/iw836sWDA4yQmG/ByYm/a65TDMwLD4xIktP9",-6637594567035845537,3012574079660142676,-6933956873517796434,-7072479252250727592>();
   private static final String BN = (String)com.yiyiaddon.m.b.a<"seirlgpe7he19","qcpg1ECUcDehrFncmG3q3wjv7KTH9Glf6WIUsgQehwejkTiGESvsczvdsueF84XBMJKHk5B5mnXEALrB",6760006233700000792,-2153206377944386217,8087831550433659338,-732860230065122784>();
   private static final String BO = (String)com.yiyiaddon.m.b.a<"s2hwoiu68vl3so","kHil3CUAadeQbqiPi7Ny3yglJQC17UKSuHgEmQZ3jrsjDX2lIBvy8JaO1/YHlVptqSrkQBIP0bwtGLmisfk=",-2244433860066114829,5979038142654575209,7768365814449863691,8251984412685069784>();
   private static final String BP = (String)com.yiyiaddon.m.b.a<"sxfb0iaudwi6a","J7CmofAiHlmly0zqHpuqtiZPCc75XhYWpVD2DhRCGtb4x5DQ7prdBisCPFkhrA==",-1801993042288932031,3126932089670777910,2545387051281099571,4830857042456377967>();
   private static final Collator f = Collator.getInstance(Locale.CHINA);
   private static List<com.yiyiaddon.l.h.g.b> bn;
   private static List<com.yiyiaddon.l.h.g.b> cy;

   private c() {
   }

   static List<com.yiyiaddon.l.h.g.b> a(com.yiyiaddon.f.a.c.c var0) {
      if (var0 == com.yiyiaddon.f.a.c.c.BLOCK) {
         switch ((int)com.yiyiaddon.m.b.a<"s1g42h6f1u28nj","NpBD33GBXTim0J6Y3noEe568F8435qzNWMmCpgSpLU0=",-4873640999660242695,7634188048905136779,6103678609774973611,-482687316012523125>()) {
            case 476050790:
               if (bn == null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3fwlksx1xo1r0","emDd4f7rcpZWzV4pQcsPVAwKvkoGLDvqA2/0hfvx6ec=",-666911644475840252,5110787616280042690,-7538753898090559373,8420397771151796650>()) {
                     case 144318888:
                        bn = ar();
                        switch ((int)com.yiyiaddon.m.b.a<"s3pfdnjx6zdap6","JmNEnFUfTz8d9Nw9vmBNkdhfg/MYC8Ymc4U+uVFTX1A=",-1818713464914147590,4051188626388828530,6894379147085977232,-7718563420547918953>()) {
                           case -86392473:
                              return bn;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return bn;
            default:
               throw null;
         }
      } else if (cy == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s29rmoqpmnxvus","2KeIhsHmUMH7SqnduViiz/hx2JeAfryT2FuYCohGbOc=",-3623153927151269306,8287414643707635504,-8162981034585426406,8293240334714299252>()) {
            case -1207713344:
               cy = bu();
               switch ((int)com.yiyiaddon.m.b.a<"snx5c2638u9p","P/tnTGM600sctXpGtlUuHEtJ+mC8zmLieem5/5mjvvw=",-7670154253112005420,7171196897416115157,7431219424848147183,133934078385587692>()) {
                  case -125855403:
                     return cy;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return cy;
      }
   }

   static List<String> a(Setting<?> var0, com.yiyiaddon.f.a.c.c var1) {
      ArrayList var2 = new ArrayList();
      Object var4 = var0.value;
      if (var4 instanceof List) {
         switch ((int)com.yiyiaddon.m.b.a<"sorno4grpxoag","eXbf2cR+T+MrdYFiL5EHmtHdu6IMXvqaSYNk/sheRhk=",-2445478015916417204,5529608965599821599,1885051824040271465,1506959032788127536>()) {
            case -382031035:
               List var3 = (List)var4;
               switch ((int)com.yiyiaddon.m.b.a<"s38amnqt6p92qc","xl15Ryg9/DraGBjws132p7Fj/S0eZ37tzUFaxKrtyws=",-4090613123224904365,7221251646033141683,-6065974738710651204,7099793381940353680>()) {
                  case 427535172:
                     Iterator var7 = var3.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"sy0sh6ze1k81t","tLQsXQ6rIgZ87Osoh5NeSq+muMdxKIhieHiVa7PIxKs=",4210660931455620468,6793468420635238135,-8066885333296067484,-2167817656401284992>()) {
                        case 2012100232:
                           while (var7.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3273pwqsj3gi8","QvrPm9EZ++d54KiHZlQ18ddCX3iazbiyDxOaTvMIBGc=",-6309746678923558921,-7002345349193309327,-7312327142895138473,-994819191548617805>()) {
                                 case 1587942389:
                                    Object var5 = var7.next();
                                    Identifier var6 = a(var5, var1);
                                    if (var6 != null) {
                                       label29:
                                       switch ((int)com.yiyiaddon.m.b.a<"s21nudkyz0en7q","4ig+PCPmABmvTuS/ofQEmrmsLTFH9+qc6pzMjVD3rP8=",999010776045320410,3557630033325819912,8906049049367824160,-8042560561242303833>()) {
                                          case 1282878002:
                                             var2.add(var6.toString());
                                             switch ((int)com.yiyiaddon.m.b.a<"s3mxt7w734ky0p","/jgfFH4NgOMc2I2RQzXj5sQNVSAExR5vTc/eK7IAyig=",-5909847361117840250,-7219129880493573161,3691028234047435437,-5905476714535736344>()) {
                                                case -803838146:
                                                   break label29;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s2a3kp3dhgcjet","DyIfIRZcX9B3mulYxJt31Z3BUbyi9V6zzxayfiKV1yk=",-6012680856126213818,-7684987514745037654,5167328615951851042,-4741728503195566559>()) {
                                       case 1214297230:
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

   static List<?> a(Setting<?> var0, com.yiyiaddon.f.a.c.c var1, String var2, boolean var3) {
      if (var1 == com.yiyiaddon.f.a.c.c.BLOCK) {
         switch ((int)com.yiyiaddon.m.b.a<"s3uo8boa85fcr4","qnwrWt3eNZ+EgHPDLwtrIJbmnxrsPpuxS31o/g6iKcE=",5626300762154424430,-8598774865373382657,-5584810524355851115,-3792949797418203746>()) {
            case -2000953396:
               List var6 = a(var0);
               Block var7 = a(var2);
               if (var7 == null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s370efm6pnl4ml","f/IkDbCwqAMaI/OHvd0GFlp3zKMhj7XYTyJJo0XADK8=",884865498571855153,-6778893193237689024,1517294253860778355,-276032337021234813>()) {
                     case -211073362:
                        return null;
                     default:
                        throw null;
                  }
               } else if (var3) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1vunvaczb615k","3TP+UATItZDoSP6PocZt+HzzeUhHGLqMJ5Brz2lnt/U=",5493185420844593324,-3390225886134250658,-349166742304734517,-5506480883206697672>()) {
                     case -1323849563:
                        if (var6.contains(var7)) {
                           switch ((int)com.yiyiaddon.m.b.a<"shlgxykobir4j","UANsAqbrlAvCUhnTxAeWsW2XDJcakbJ/SqkCfDItMDM=",2843703324045814085,2857763040383593477,-4528614172075525202,8633581628003019405>()) {
                              case 428367485:
                                 return null;
                              default:
                                 throw null;
                           }
                        } else {
                           var6.add(var7);
                           switch ((int)com.yiyiaddon.m.b.a<"s399p8fdvp4wsg","E0rDtgw20gcwze8Ud4nYsCvXMD8e9Vn0pmoL/6xSksQ=",-9141651562275264196,-7066095780353741756,-5869468710135923053,8982039247083057919>()) {
                              case -499033609:
                                 return var6;
                              default:
                                 throw null;
                           }
                        }
                     default:
                        throw null;
                  }
               } else {
                  if (!var6.remove(var7)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2vljwmg8gc1tf","WmuGKPhnp21He/Kb7nKECAFPJeV2HN8PG3rbFFcBPWo=",-259727830951939478,-8719087132777889390,4972383211701887233,7427949352096379959>()) {
                        case -1825936165:
                           return null;
                        default:
                           throw null;
                     }
                  }

                  return var6;
               }
            default:
               throw null;
         }
      } else {
         List var4 = b(var0);
         Item var5 = b(var2);
         if (var5 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2vtx10gu5bwga","YxiZofNu/IWNfbQMo8U5D3+KTOe2GUl27VsLD+JuaI8=",-768332813964484499,-1366076548304848139,-2532956114302162587,-4329726541900657983>()) {
               case 1228948688:
                  return null;
               default:
                  throw null;
            }
         } else if (var3) {
            switch ((int)com.yiyiaddon.m.b.a<"s2tzvl2gocz65b","tG5EL3AGrOW8I/Yjcb/zwcsGU3TXiFxlsrtRRQJKI6M=",-850968540878819321,-5141357770497046287,6893835416248428819,4359683242931543879>()) {
               case 1533784148:
                  if (var4.contains(var5)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3akg7ikcmt6zk","dd9mXaXPwJqRsc9aY7weqhD+8/7/6E2JzZgGrmOBoOc=",-3597356308241548958,4345233452974492227,3983958131762013729,4455009506642381618>()) {
                        case 1693497687:
                           return null;
                        default:
                           throw null;
                     }
                  } else {
                     var4.add(var5);
                     switch ((int)com.yiyiaddon.m.b.a<"s1es9bklr1u3f2","27OS/5gMrFcLn9TurycoivC/kfKkmeYdB9sR65PgJfc=",8767616689732152854,5420068628390165726,-3466032925238423853,-7098203808467558109>()) {
                        case -1069090697:
                           return var4;
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         } else if (!var4.remove(var5)) {
            switch ((int)com.yiyiaddon.m.b.a<"s2n391g4jrkbyl","FF5+WRYY72cWor/dAb+pfmncaTsZH3XauMucbwwz2wQ=",1282772770599512076,5648676820987863804,-3300522104508431307,-7351672293894351797>()) {
               case 309117938:
                  return null;
               default:
                  throw null;
            }
         } else {
            return var4;
         }
      }
   }

   private static List<Block> a(Setting<?> var0) {
      ArrayList var1 = new ArrayList();
      Object var3 = var0.value;
      if (var3 instanceof List) {
         switch ((int)com.yiyiaddon.m.b.a<"s29zicsf4qg60s","qBiEEcoSCO0rXC3R51Vnoj3Zs6jiLUE95KToqoO19AU=",-385212776602374677,1161299614869660565,-4712075841216419268,5100207790678500910>()) {
            case -280192956:
               List var2 = (List)var3;
               switch ((int)com.yiyiaddon.m.b.a<"svyy370qrwt6g","LkkMcwjEN9F+AWCW9gagC9NbVjW09F+xBvFnbifAH8M=",8545436366926570046,3267753911828855729,-2413353083825833016,4962816298954550603>()) {
                  case 235181789:
                     Iterator var6 = var2.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s2hs45mplxw21a","DfEJJ6ijNTM1SA3hPH3/Edta8/mwSTvGn57QaTDy7Tg=",5932721428689064553,-6539235811657979550,-4329329550742764231,8527246605155168071>()) {
                        case -1438604196:
                           while (var6.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3kmf8tds8txv1","gUofTgOfxc0u0de6rE664BeqTENyk4Vt4xS6rWlYW2I=",-5576924424041185740,-8457196272554348116,2308386780181328242,-5365449668859563026>()) {
                                 case -1017974361:
                                    Object var4 = var6.next();
                                    if (var4 instanceof Block) {
                                       label29:
                                       switch ((int)com.yiyiaddon.m.b.a<"selw9ykcxq8gb","8TG4gOmn9bSZBV6tZGCsWHz6x3X4dydFJkwoO3jZNm0=",-4167381042908893083,-5466434516283330818,2928373347096275842,4570298383784742520>()) {
                                          case 668995355:
                                             Block var5 = (Block)var4;
                                             var1.add(var5);
                                             switch ((int)com.yiyiaddon.m.b.a<"s36gqm895f6kx8","vmcjXV544UPXYNfAQSj+Qc+SJVsUwEkVSATYkz6XL4I=",-1875547426295775232,6281760738979311406,-1639020702096524660,-1116821293903882182>()) {
                                                case -1144497797:
                                                   break label29;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s2kbyfl32az7p1","6J/TJf7CHzuSyPuGFNgE5BQwBYQ+U/y7jsbWb7z3D+U=",6175017223864492805,3256871696686988385,-2494130504330150141,3916705895373579143>()) {
                                       case 1906236912:
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
            default:
               throw null;
         }
      } else {
         return var1;
      }
   }

   private static List<Item> b(Setting<?> var0) {
      ArrayList var1 = new ArrayList();
      Object var3 = var0.value;
      if (var3 instanceof List) {
         switch ((int)com.yiyiaddon.m.b.a<"s19pc0rbj5vv8k","Sdm3CsEH0Ii0XafkV56G8zARPZf+hhY+JltQmHPUHSE=",9188348764100489858,-3939994120459231065,-8133865934457461393,-1211879463176395176>()) {
            case 344644344:
               List var2 = (List)var3;
               switch ((int)com.yiyiaddon.m.b.a<"s1iawonm55mnyh","9A34AtEPGOP1Shml4a+0l5uMKd3z4yu+J3YSf7ES5IY=",4383550080248579179,-793615881108802364,-7445553351198903061,-7155242747454711314>()) {
                  case -1764256004:
                     Iterator var6 = var2.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s3mrp51hi41axc","VvNdqCpVj6tGQsfpXJ5l259hHqetXY5KNDCUIXfIxqw=",-5605791999248718244,249387823914905944,2965883898970213100,2744515811015175167>()) {
                        case -778554762:
                           while (var6.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3drhxr1uo326d","KB41XOTKCycNKVDPv1Wmcf3gIoU2Y3iO6WKcM6c4v7Y=",8840700336235908933,-2053300611137780051,-3411837001622095519,1445022698738843501>()) {
                                 case 944735214:
                                    Object var4 = var6.next();
                                    if (var4 instanceof Item) {
                                       label29:
                                       switch ((int)com.yiyiaddon.m.b.a<"s1ec273x1voxn3","TfRaKDc81sJJYt+Je0cxbL/j8HSIpcA+81lqECovjeE=",-6392594733984045770,6473687204250128807,2345869130418181934,1353836734778377578>()) {
                                          case 608353868:
                                             Item var5 = (Item)var4;
                                             var1.add(var5);
                                             switch ((int)com.yiyiaddon.m.b.a<"sl05dswqdyiue","ruQ+DbzlKN7A/OiPYSyXCh8oVohBGWAbF4spSaIN2QU=",480819565894186757,5661116345058384014,4516578641286248969,6868911275327489190>()) {
                                                case -415423062:
                                                   break label29;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s4r24il0ibidw","qRfUMExqFCYjjgljd9NO1Rsl+qqAnFUCmuGmhfkSOdg=",1328991514530356798,8928719631213324874,8595280667750898882,-1586743715441932754>()) {
                                       case -1336748692:
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
            default:
               throw null;
         }
      } else {
         return var1;
      }
   }

   private static Identifier a(Object var0, com.yiyiaddon.f.a.c.c var1) {
      if (var1 == com.yiyiaddon.f.a.c.c.BLOCK) {
         switch ((int)com.yiyiaddon.m.b.a<"s1zvhuulu4v8rq","n1RVAMdZRoXYwaMq9/B4ifxVWt2dvkS73tweLkelz9g=",-8722442314362518932,1539627117441087724,-6981979625606708193,2422441729893121574>()) {
            case 1650540317:
               if (var0 instanceof Block) {
                  switch ((int)com.yiyiaddon.m.b.a<"swb380vvu9tmh","DP1ocPDg1ryO+W0sCjMrPHw78F75rF3Ak0anoVC/Jow=",-739507927070857844,2345707194868449143,6947112282887966404,5544781659851459829>()) {
                     case -88500985:
                        Block var3 = (Block)var0;
                        Identifier var4 = BuiltInRegistries.BLOCK.getKey(var3);
                        switch ((int)com.yiyiaddon.m.b.a<"s1lwvxrxt8501l","WKCnRXL/XlPI81AdMs93dKWWdDw112kQZazWAo+WeXc=",5674942344622184712,4153146243062604934,3101815615413547658,3549520167757802142>()) {
                           case -1223240214:
                              return var4;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3vgod14fxin1z","dFLOkME4Ly9xpO/kuy04QT+Ytw+R/aDGJwGYClsmjbo=",5857379458242114155,7287268223423927914,1993951013051252447,-1879234860239725451>()) {
                     case 1585261469:
                        return null;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else if (var0 instanceof Item) {
         switch ((int)com.yiyiaddon.m.b.a<"s3dqgu25afmhbt","1okJVGFzG78KnTMCRdlnOXjFcfiG0KqiA09nlVkAm1g=",-6486452158481571970,-1575747578032577491,2070501082417463163,3193908985238485096>()) {
            case 624663846:
               Item var2 = (Item)var0;
               Identifier var10000 = BuiltInRegistries.ITEM.getKey(var2);
               switch ((int)com.yiyiaddon.m.b.a<"s35z3p1e31iurv","9OODLaVguBr+8wmE8QXgGlI2EjygSsb+pelD8FQjL4A=",7345596280986895982,6590341343337361478,-8061602197458210047,952241794946537305>()) {
                  case -940011290:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s3jax2n65mzyo8","g4LHoEd9UzK6CANStkJoBEgMIfnOlF5Ijl3lgbezUSU=",-6315484033150859655,-5997886919966959244,6339786157881725995,8845879746982685062>()) {
            case 684189765:
               return null;
            default:
               throw null;
         }
      }
   }

   static Block a(String var0) {
      Identifier var1 = Identifier.tryParse(var0);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2x6get7u0kzy6","BRwDWOHzvqL7zLPAIxyxxfkTi2k/GRZLdR/UKP7WWe4=",8934814881952469750,-6108476450377876891,8502860401164014207,-7032977739346052504>()) {
            case -869282459:
               switch ((int)com.yiyiaddon.m.b.a<"s3frkd6a98lyqw","skaOX+JW6UC8oMo8YVD15AFa3fy4trou+bxElXpS6z8=",1419268547894240560,-3825881762423784219,-1798092969273208727,-5804941769393068449>()) {
                  case -736243994:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         Block var10000 = BuiltInRegistries.BLOCK.getValue(var1);
         switch ((int)com.yiyiaddon.m.b.a<"snotm8s03c12a","dcF2MRXRqSJrN03ymTwl6ZRCrrNY65C3xj9slfUYCwQ=",-5310008311482339795,1538827214992695071,3516076796576886103,-3058042084161928950>()) {
            case -1997367720:
               return var10000;
            default:
               throw null;
         }
      }
   }

   static Item b(String var0) {
      Identifier var1 = Identifier.tryParse(var0);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1jgy59r4uotbn","hVAj0hH/1m1ychwmeJAlFMKYtcCU3XUDYtPx4J0/ufg=",3190673942021747709,-7831139279472369303,3653817650796006897,1888315879349406586>()) {
            case 850691427:
               switch ((int)com.yiyiaddon.m.b.a<"s1w15s0mvpkksk","/vzKueojDudv/H6EKxFM9ktcn09U542A2E1Lgvx65GQ=",-1836194536749518058,2613170895155822179,2558798774005594854,-4933755299088468550>()) {
                  case 882754013:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         Item var10000 = BuiltInRegistries.ITEM.getValue(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s2o7coa1tvej8i","uwt4zO4GWXPEYuVG2aMiXbK2Wybf245rbYTljz7fCm4=",-7312150894184151083,-7904263016190801482,5824634972278934713,-1550596587408272699>()) {
            case -503839093:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private static List<com.yiyiaddon.l.h.g.b> ar() {
      ArrayList var0 = new ArrayList();
      ArrayList var1 = new ArrayList();
      Iterator var2 = BuiltInRegistries.BLOCK.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s325mpfwieh1rp","98aEhcjBRmC/PwwOnq1flSl8zppZuo3roJoOzVCwHZQ=",8217604780109584736,-4112353785513422343,-3914295104446476854,-184872219382909717>()) {
         case 828710482:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1xn0gsrhb92kt","5Vk8YxXB+5hwvkUMcukLeBixX3ovqc7eykmGk+B8/zI=",-3299866291424501553,-6876990454344606789,-793156924966810224,7268602167790680478>()) {
                  case 1838746908:
                     Block var3 = (Block)var2.next();
                     Identifier var4 = BuiltInRegistries.BLOCK.getKey(var3);
                     if (var4 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3cbzi0y0vqhex","2mJWOvZgsBDjW3PC7zWm4Akfa5Sjc5BLFzOFH/7laBw=",7658957204165017234,-8071511840721314910,-7130778224490211717,5354697567693793627>()) {
                           case -1939246280:
                              if (!i(var3)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1v5m4er8e2oiz","GpfYh2Nwdd5b9/AbUNkOCQjKK5MJex2BcX9l9QHqWD4=",7652315870117957277,-6789671658377606611,-9207987469512279004,-6311508074284741990>()) {
                                    case 1885404936:
                                       switch ((int)com.yiyiaddon.m.b.a<"s38rr1n3fqaiws","1titpXPq5cANZ9wkw1i2Ybn7RC5j/8aCzC0cMv2wsHQ=",-344640351359520506,4139736037779075955,2355817606753757393,-8853585474865896978>()) {
                                          case -1786827284:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 com.yiyiaddon.f.a.c.a var5 = new com.yiyiaddon.f.a.c.a(var4.toString(), var3);
                                 ArrayList var10000;
                                 if ((String)com.yiyiaddon.m.b.a<"sxfb0iaudwi6a","J7CmofAiHlmly0zqHpuqtiZPCc75XhYWpVD2DhRCGtb4x5DQ7prdBisCPFkhrA==",-1801993042288932031,3126932089670777910,2545387051281099571,4830857042456377967>()
                                    .equals(var4.getNamespace())) {
                                    label37:
                                    switch ((int)com.yiyiaddon.m.b.a<"s2cap4a0443al7","AVWZHX0cmTQUD/CiYVtQfl2aXwPej6IhaHMSdIjWEuI=",-3304405095219862459,-7631518626166989833,821760082037152276,1969919676571743397>()) {
                                       case -1540088751:
                                          var10000 = var0;
                                          switch ((int)com.yiyiaddon.m.b.a<"s2cnclf5vmjolj","81xo64braRao59X2SALqE2iCLKoBwCk9fvpDDTN7tt4=",7927141232499148347,3349808308318561869,-6005235676447357673,6490147571567534650>()) {
                                             case 1427193972:
                                                break label37;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    var10000 = var1;
                                    switch ((int)com.yiyiaddon.m.b.a<"s1vmh5uysn1wec","NWL2nPW47F+U/1Qb4TlMPkBuBmEMGcnhZezLGQP6vHc=",-4916545072792513461,-2627452779810452573,-1665183710862905589,979623780930583030>()) {
                                       case 629439817:
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 var10000.add(var5);
                                 switch ((int)com.yiyiaddon.m.b.a<"s1tdtigxe1hud0","ZtcnTDdHK5uCvgOHE3Kv1orpmugMUv+Mvc+vVOMBTWk=",4060535739113800092,-4001603206641706625,-265500911554094071,-8164346359469403704>()) {
                                    case -726619661:
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

            return a(var0, var1);
         default:
            throw null;
      }
   }

   private static List<com.yiyiaddon.l.h.g.b> bu() {
      ArrayList var0 = new ArrayList();
      ArrayList var1 = new ArrayList();
      Iterator var2 = BuiltInRegistries.ITEM.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"svos0cze8olwt","Mkq0j2ATD1dtzqjYsmiSWvdmsH5MvZNhjcAE+EaDin4=",-9031030950180601870,-580186558418902347,-5815745689855250345,-1923131532457567561>()) {
         case 1534416618:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1vw60mygfax30","kvb11STh5XTUsPHJKQV2QjdYNG5Rz778vHyagpVkm4g=",4132342934164043979,-659095088128868487,8867583645293180386,687599713599463890>()) {
                  case 715983214:
                     Item var3 = (Item)var2.next();
                     Identifier var4 = BuiltInRegistries.ITEM.getKey(var3);
                     if (var4 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s31r1xcyj3xap0","ER29HblBJJAQdxD7brvogrO834lVKXQS9I0sFQsNQq8=",7791305268949183935,595116946270637027,1232548503872743733,2744398821559485679>()) {
                           case -1640507805:
                              if (var3 instanceof BlockItem) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s16iyu2opg2b7b","JjGb8c5KE65X11/9oFVsUx81RomN9eUVPPYnryGWGX0=",-3595993817144515387,8009449475445854149,-4135792619853680825,-8831075563298442491>()) {
                                    case -524614106:
                                       BlockItem var5 = (BlockItem)var3;
                                       if (!i(var5.getBlock())) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1egssurtexwy1","CMDiY7oIdxTNbVgNJ6iIETwWwftquvsmXP+/2pfi42M=",7526352901442617181,-2944511186135214190,-513641862972122209,4011682425857698407>()) {
                                             case 65266831:
                                                switch ((int)com.yiyiaddon.m.b.a<"s2scr3qokgl3qf","Yy4hz+8vmKyAncWkpQI9BxpmKSpr2qSAb4gO10tFCBE=",6366287729956887143,7401802570972554154,-1687864913723908629,-1843074458693976787>()) {
                                                   case 1348034745:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          com.yiyiaddon.f.a.c.b var6 = new com.yiyiaddon.f.a.c.b(var4.toString(), var3);
                                          ArrayList var10000;
                                          if ((String)com.yiyiaddon.m.b.a<"sxfb0iaudwi6a","J7CmofAiHlmly0zqHpuqtiZPCc75XhYWpVD2DhRCGtb4x5DQ7prdBisCPFkhrA==",-1801993042288932031,3126932089670777910,2545387051281099571,4830857042456377967>()
                                             .equals(var4.getNamespace())) {
                                             label42:
                                             switch ((int)com.yiyiaddon.m.b.a<"si4haqdlusl7d","NuV+EUkIWyu1ORe3lj7EP5k6w02Ijq/Qw4sCkbQGWI8=",-8010865395076651148,1257055352665370492,-6022760192886335773,-6086554956055473750>()) {
                                                case 275124026:
                                                   var10000 = var0;
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2ye36ymknigzj","8xWbpPF24KNFvSkUuRb32qXX4eoica6MvkYUQ4MXxxU=",-6638996862266094324,6595302023335816685,3562237134955014427,3516769858163713740>()) {
                                                      case 1790614864:
                                                         break label42;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             var10000 = var1;
                                             switch ((int)com.yiyiaddon.m.b.a<"s1atpnhdzfg5xt","mFhs9G0vv1lEuy6h8mh8IHwnAukY8ynVx7WfxihQ4U0=",-2411757860159076062,3933471187423029915,1637414596435075888,3847382593582349369>()) {
                                                case -1639675930:
                                                   break;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          var10000.add(var6);
                                          switch ((int)com.yiyiaddon.m.b.a<"s3hc0ovqgt2nvl","bUUKy4FGTvFLmgZLtw1j0XUSqzQz8tqrAY2j0f+sl4U=",-8905379538782271288,-8421312106744224912,732727868636224490,-6002170103876758576>()) {
                                             case -375749602:
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
                     break;
                  default:
                     throw null;
               }
            }

            return a(var0, var1);
         default:
            throw null;
      }
   }

   private static List<com.yiyiaddon.l.h.g.b> a(List<com.yiyiaddon.l.h.g.b> var0, List<com.yiyiaddon.l.h.g.b> var1) {
      Comparator var2 = Comparator.comparing(com.yiyiaddon.l.h.g.b::D, f).thenComparing(com.yiyiaddon.l.h.g.b::L);
      var0.sort(var2);
      var1.sort(var2);
      ArrayList var3 = new ArrayList(var0.size() + var1.size());
      var3.addAll(var0);
      var3.addAll(var1);
      return List.copyOf(var3);
   }

   private static boolean i(Block var0) {
      return var0.defaultBlockState().isCollisionShapeFullBlock(EmptyBlockGetter.INSTANCE, BlockPos.ZERO);
   }

   private record a(String BQ, Block j) implements com.yiyiaddon.l.h.g.b {
      @Override
      public String D() {
         return this.j.getName().getString();
      }

      @Override
      public String M() {
         if (this.BQ
            .startsWith(
               (String)com.yiyiaddon.m.b.a<"s2ab8nslyo9atw","ESDM0UG60TA0cdeMpcwMkprc23PcjlQcr+hoEnKcHaM8roCgprSJtID0mqivPtVo",271231200416327215,7782176206132826950,-5325614271582378782,-3980214518210098516>()
            )) {
            switch ((int)com.yiyiaddon.m.b.a<"sqxuce7vh3lx7","HfCHVRBkXC/esbJBgV3wQwI5ALRJQl8jy+tuMdLiD8Y=",8332774856082030499,2378258388334170822,4939494732371673349,6153313647685221207>()) {
               case -618990851:
                  String var10000 = (String)com.yiyiaddon.m.b.a<"swiiz804ww3b8","HSGuEfIgKw9NoSwtKIMUDMyV0VSedLt1ArEp47DMmKdxH07NQM+dOzs6zaDBYsMRnQDxTA==",-8395669596160742084,882685235485269406,-5210948449167600381,4919878368117547076>();
                  switch ((int)com.yiyiaddon.m.b.a<"s268dcztqab6pj","yVte+FpeNCnAwY1pVILDfwzR8v+vWp0AYTS3FI3kFWo=",-127250048158652886,522992354410790256,3159715851177461132,4519087227061886253>()) {
                     case -1409030490:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var1 = (String)com.yiyiaddon.m.b.a<"syvb7txfy1d57","8TR2DC9BpkBZouZmh9ZjUkoPSzs5KIb3phzJvf9yyz2aAX3t4HgOVkdvMHWAU5A8wwqjE8pq",-446414067999549588,3680708599667303790,-3056215605152012871,8454440562850314409>();
            switch ((int)com.yiyiaddon.m.b.a<"s3mf0ra7ahsrnt","7n7Ci7jCRE/y/K4PEHJwFpZnceKUwZH8uDohgp38nHk=",7268381109288450756,-2134826336143837570,-1741108529307572697,839449618041727039>()) {
               case -414538945:
                  return var1;
               default:
                  throw null;
            }
         }
      }

      @Override
      public boolean a(Canvas var1, float var2, float var3, float var4) {
         return com.yiyiaddon.l.g.c.a().a(var1, this.j, var2, var3, var4);
      }

      @Override
      public ItemStack a() {
         Item var1 = this.j.asItem();
         if (var1 == Items.AIR) {
            switch ((int)com.yiyiaddon.m.b.a<"s3v0tci97ig0ly","aZJQ6c0BZL6Sfw/mZCOTdQOqR3tzMKTABOvEov5vu+E=",-3740273209516137393,-8228562639895422174,-666901597726064206,-3032082034929195695>()) {
               case -426567119:
                  switch ((int)com.yiyiaddon.m.b.a<"s3dzin73jpx9fh","p6iRmDpSfPrdJCkYHSL1LHLLspZuS37QFoFbnZSu11s=",1964801100265741818,1120542984869287229,7025700648509187832,3087707582698767215>()) {
                     case -1444350012:
                        return null;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            ItemStack var10000 = var1.getDefaultInstance();
            switch ((int)com.yiyiaddon.m.b.a<"s14yc9ghmj2dcl","enH9ic9fy0drbhJTx+aqg2SCt1Dy9eGJQVOo9hHPCr8=",6304822364842117518,2274807920536953798,5798615117321090278,-7491390041622978137>()) {
               case -979981145:
                  return var10000;
               default:
                  throw null;
            }
         }
      }

      @Override
      public String L() {
         return this.BQ;
      }

      public Block a() {
         return this.j;
      }
   }

   private record b(String BR, Item n) implements com.yiyiaddon.l.h.g.b {
      @Override
      public String D() {
         return new ItemStack(this.n).getHoverName().getString();
      }

      @Override
      public String M() {
         if (this.BR
            .startsWith(
               (String)com.yiyiaddon.m.b.a<"s3hl2ua1lefo95","oFAEIskWqj5Z1s8LHFcP+wKqrDkHuU+g1Zmg/eb+daq9iPGQVgykbAByp2IrJMSU",-1674711721989666827,-2527041303463538610,7878397134622322468,1564841416879364852>()
            )) {
            switch ((int)com.yiyiaddon.m.b.a<"s3e300a65b0igb","lRHwgODlpRLj5pFvvvFeL96NGLFR7juIomrxhnZA+K8=",-3694229403972446897,2126573601329890340,-5431917973101605305,-5222998479339720043>()) {
               case -982778542:
                  String var10000 = (String)com.yiyiaddon.m.b.a<"s1mww9866o4jku","xR/1OMmIs/9zO5bpNoG5Rwv1uEnIa9Dh7Fq0y21DBdJiVDYC1p2y2b6mfoBlJP9rV0xT89T0MvIv1Crw",-4799432387842847747,7487831634849423540,-8351802308951001135,-2970600934809979244>();
                  switch ((int)com.yiyiaddon.m.b.a<"s2nd8f9d4czlch","SDXZ8zzw9kXQvvmLeT52K1rjLhJ5fNKQ1vohdHFFfJw=",-3846870642190042254,1028549880402388209,-9217582063507939284,7711580892611661835>()) {
                     case 536472446:
                        return var10000;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var1 = (String)com.yiyiaddon.m.b.a<"s2eljfcf2wqad9","2z67ms/HL0AcFvIsNEUXsM6Ce2N+0vMF2fvXceGmEXtCDcPHr2rdCdBJq/OOGLTKhlD+/F4cQtjEGbla/mc=",545807547592306386,6026872231387932441,-6781546083602897570,-3903754023474568146>();
            switch ((int)com.yiyiaddon.m.b.a<"s18odnlmll4p9l","j7txblbhL7aFlPQ3AweA2WkQksb5MZfvzFzQQPp+uFo=",4210214490629864465,4761311480930974924,-189234668165771535,-8164815595196783787>()) {
               case -1716527258:
                  return var1;
               default:
                  throw null;
            }
         }
      }

      @Override
      public boolean a(Canvas var1, float var2, float var3, float var4) {
         return com.yiyiaddon.l.g.c.a().a(var1, this.n.getDefaultInstance(), var2, var3, var4);
      }

      @Override
      public ItemStack a() {
         return this.n.getDefaultInstance();
      }

      @Override
      public String L() {
         return this.BR;
      }

      public Item g() {
         return this.n;
      }
   }

   enum c {
      BLOCK,
      ITEM;
   }
}
