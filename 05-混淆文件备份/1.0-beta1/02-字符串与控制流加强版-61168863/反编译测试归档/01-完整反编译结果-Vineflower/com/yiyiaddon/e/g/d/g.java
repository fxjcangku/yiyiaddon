package com.yiyiaddon.e.g.d;

import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public final class g {
   private g() {
   }

   public static Map<String, Integer> a(ItemStack var0) {
      LinkedHashMap var1 = new LinkedHashMap();
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sbzixkuhl8qp6","9KkMFtarpQ30Lo4uyCaGahtZckxtgwElb/5pU1WIQy4=",8402308863488094232,-2740605725691686814,7851897925632591969,4321078326436549031>()) {
            case 1106125634:
               if (!var0.isEmpty()) {
                  ItemEnchantments var2 = var0.get(DataComponents.ENCHANTMENTS);
                  if (var2 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3qeewy24t6lgf","pvHXMDtl2xXk8AqrkOyYJPJkUDIK5+bsZ65INf8rsHw=",-4320509113925934443,856009254091407777,2871857241257033775,-8290567396565335701>()) {
                        case -216180161:
                           if (!var2.isEmpty()) {
                              Iterator var3 = var2.entrySet().iterator();
                              switch ((int)com.yiyiaddon.m.b.a<"spk3po2v5107s","63XxXiwiOvBR1WTuiJmV2RE4Y0BX9qO25AzGe1MGHeA=",5046244492208118989,-2041964798982419949,-6663533244744408666,-7146883342332141525>()) {
                                 case -945888761:
                                    while (var3.hasNext()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"scnfysov1sgab","qePdO3aK29L0Q9pZ+PWDgI6b20FMhMpaByy0pF2u6j4=",-7708094294045485236,5601613141426023294,7930429996820172779,2716955430999850092>()) {
                                          case 720073683:
                                             Entry var4 = (Entry)var3.next();
                                             Holder var5 = (Holder)var4.getKey();
                                             String var6 = var5.unwrapKey().map(var0x -> var0x.identifier().toString()).orElse(null);
                                             if (var6 == null) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s35aki014xdgba","0d4JEf+ISGWHCliqk+nctJeCyVkiOBdKkPd8s1WdOI4=",6844029708042932871,8122875897392914930,-8826245208095035442,5299579164622945934>()) {
                                                   case -1308542775:
                                                      switch ((int)com.yiyiaddon.m.b.a<"sx8zkgvv3t2fg","sCGUYycCOR3get8ty1Z4kGBmN95oeHvT1Oel//iUsMc=",-8905368362877938066,6022961329164240663,-632172868671190115,-7521050878703174479>()) {
                                                         case -1019264535:
                                                            continue;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             } else {
                                                var1.put(var6, var4.getIntValue());
                                                switch ((int)com.yiyiaddon.m.b.a<"s1m4j86blpll94","A88YAX/kTYZbpDx1lJ0W+xM/XPLmJev5BqoopPqHl5U=",-2655940814553550242,-5745924358712832417,1584782369767226193,-6124252578451330690>()) {
                                                   case 1983824755:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
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

                           switch ((int)com.yiyiaddon.m.b.a<"s3fpnep8ircql9","/E4d8nD261Q7QhoABRdtQToeO2IJUPxjPlNLQg5/uIU=",-4459836100485324738,8850210336337462358,-1384878650114159671,-2097916177570308254>()) {
                              case 1782960218:
                                 return var1;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return var1;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2przpiht4gx37","P+PyJ2s4W2Kyp8I/J1YSiSuyUxfvUhGUstpeuZ2pOsc=",8863676669975474337,7316837888213048827,-8236948027679206485,5971305261779870432>()) {
                     case -54456069:
                        return var1;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return var1;
      }
   }

   public static f a(ItemStack var0, o var1, a var2, double var3) {
      Map var5 = a(var0);
      ArrayList var6 = new ArrayList();
      ArrayList var7 = new ArrayList();
      ArrayList var8 = new ArrayList();
      ArrayList var9 = new ArrayList();
      ArrayList var10 = new ArrayList();
      HashMap var11 = new HashMap();
      Iterator var12 = var1.T().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2ipjg9mqpy0l5","6P8aoOnJ6P8lWGYcRfWCc0WsLYAPOkNKBtRu75SoLnI=",3051141500314201220,737945141613309829,5159715266364829902,7552862173085209183>()) {
         case 1540874518:
            while (var12.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1ts4wa6v7t256","zjfHmGCuHhHVrbxejRdVtspNeTXvBsixByMx8mj2bAI=",173864969887402541,8398231654607474013,8229704918442773379,2518728073207564149>()) {
                  case -717000837:
                     o.a var13 = (o.a)var12.next();
                     var11.put(var13.s(), var13);
                     switch ((int)com.yiyiaddon.m.b.a<"s1ynxbd0shr9ds","5Y91m84OcLqBv1OMFPXUAYBwWKVB6XWWuhdDZ0ZeCO4=",-6429600621684227925,6801169167762009659,-8633113861190315250,4643994329733369994>()) {
                        case 652693885:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var12 = var1.Q().iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s7li935bm6zl5","nxRjP4nAgYGcuKV8Q/YUwyl4cHrh2anNmxapDhK6Fis=",6746991092488886242,7672125470889942652,2550025792489280083,-5432467153968309924>()) {
               case 1070763740:
                  while (var12.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3vop493ct02qc","WMqYeHWymnTTygnkBR0AQJzWIobdByH2bMy2jp47Ni0=",-9154921691934112777,-6567558478365188961,356611089100055209,-1479443897679907740>()) {
                        case 1848432484:
                           o.a var20 = (o.a)var12.next();
                           Integer var14 = (Integer)var5.get(var20.s());
                           if (var14 == null) {
                              label100:
                              switch ((int)com.yiyiaddon.m.b.a<"s1p2sqh9txw035","i5M5/VFvpV3RPrSCiXxtqfWip3I1XoZBRkXQ5gbag+I=",-5553513410644659029,-8953908134415058906,-3588921251824138701,7771694948164129329>()) {
                                 case 1317977786:
                                    var8.add(var20);
                                    switch ((int)com.yiyiaddon.m.b.a<"scxuqdnx3yhho","TrhsfLHctmgafrjLo9Cax2DvM1ogFcxKBhoOxk6APZU=",7210468673092178354,-3635482992845962058,3746524026536908965,4278597029822114490>()) {
                                       case -277085728:
                                          break label100;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else if (var14 >= var20.ai()) {
                              label97:
                              switch ((int)com.yiyiaddon.m.b.a<"s2zx06smoiesal","xPEwTjDYtuTAPM9WVQMij3rxGz+imXAMgW+Fup64Rxw=",-1626286431603501173,4461214999511320848,-3349887236352421983,-2502405190463841613>()) {
                                 case 109657361:
                                    var6.add(var20);
                                    switch ((int)com.yiyiaddon.m.b.a<"s3jchebbnvmg5p","R0kGStmqJk6vw/oW+7Me4hq9WQ93hQPozI+A2lrKuwA=",-7950197196551584080,8484273773938179705,-5038529147492043040,7323239778741991658>()) {
                                       case -38207195:
                                          break label97;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var7.add(var20);
                              switch ((int)com.yiyiaddon.m.b.a<"sm867kb1j43xe","+eA14fFyT9J5/kOqj8jVLqfyY+rEgmF+rbSVh4yyD6U=",4272080805245944434,-1864287281185765914,-5904829890773867025,-1797493918455741518>()) {
                                 case 394400067:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s8qj8ykgjtqff","WNrH//+/xmY14wW3GKXaPE3m/KCyuAywKI9Ju7JUqhE=",-8681984748424729907,-8722624258894635575,-6265202734893595883,5955820122127204063>()) {
                              case 1509641158:
                                 continue;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  var12 = var5.entrySet().iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s1h549i0amagm6","J9D2sAUx8j3mkaXc7MtLb4GExgf3+zRwunaBtjcF+TQ=",319702299221317116,5417729963549429416,4846932151588401359,-7795881251822415537>()) {
                     case 322021686:
                        while (var12.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s14r3zh92a5b5q","djWzIFXlwL3pk4XW4WwkZg3ujxUuZ1EQXeRofmzuDXk=",8538714933899849351,-333186009242106044,-5534445844568048810,9216173693031686661>()) {
                              case 763022450:
                                 java.util.Map.Entry var21 = (java.util.Map.Entry)var12.next();
                                 o.a var23 = (o.a)var11.get(var21.getKey());
                                 if (var23 == null) {
                                    label87:
                                    switch ((int)com.yiyiaddon.m.b.a<"sx8ltukmrvjhv","X/FMFJocj49AOH5LccEyP2L2X5aEgG/cNetACFY8R0M=",-5156161206392198829,-3642640729240409272,5095854646925757262,2057295276986536451>()) {
                                       case 2036678774:
                                          var9.add((String)var21.getKey());
                                          switch ((int)com.yiyiaddon.m.b.a<"s391y2gvwd0w91","rRCc4xg2JnlbWzpINXR/6Q3tNCmeU5ucr56yhV4bu/U=",-1079688619111496166,-5778315174403037521,1634868227004434279,4161131207092069009>()) {
                                             case 656527825:
                                                break label87;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else if (var23.aQ()) {
                                    label83:
                                    switch ((int)com.yiyiaddon.m.b.a<"sfm21j11xyfs4","c49BoSzobwF/n8DbnA7Ny0EIHLtZJYFG9QtT/RurmeA=",8708945629338854976,16591141943530773,575169095875731657,-4753862998260959254>()) {
                                       case 882823537:
                                          var10.add((String)var21.getKey());
                                          switch ((int)com.yiyiaddon.m.b.a<"s2z3ty9wecorbe","0qeKLGsGdwJPyBknftDkQVd58Tz48m77WJPICA9XiC8=",5041476744990625237,-355324254538249627,-1063316047606167582,1950294844936326256>()) {
                                             case -355138519:
                                                break label83;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s2r9vbb9nvq357","iUSJLLOfnr4ZrbZh9SORWl/bdiErjdOLBkbUzluRBcs=",-6580315886105069904,-1682786631940215521,4375409950528322072,-4297524950364648835>()) {
                                    case -1530759996:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        int var19 = var1.Q().size();
                        double var10000;
                        if (var19 == 0) {
                           label75:
                           switch ((int)com.yiyiaddon.m.b.a<"sxfwdl395ji6m","4r7K0oPVWpeoMappmeNkgfHF9aA/EMonQ0g3znjzOGo=",7805182896685591732,3081586029883409953,-8008638786621556303,4031287592286229333>()) {
                              case -1848355084:
                                 var10000 = 1.0;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1nta5zn6jin0j","ax42j/+sYw82+m7bR8xRekAKOpJTqRhzd5cCqXxtQs8=",6650474989902020356,-1974308380581981926,2190312747960180857,-8470031289182013821>()) {
                                    case -628144503:
                                       break label75;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10000 = (double)var6.size() / var19;
                           switch ((int)com.yiyiaddon.m.b.a<"s2awbnb1ylqiw5","+U1E7LiB3CVqutjkSubQm5IAYnhMYJ0GG6rqmx8Nvio=",-109945370908138657,-5905200757154232852,1840980330828644451,-6282556137669401289>()) {
                              case -240200281:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        double var22;
                        label118: {
                           var22 = var10000;
                           if (var19 > 0) {
                              switch ((int)com.yiyiaddon.m.b.a<"s37282d5z6abg5","AvEoHyZZSC58AhyJMFu9u1e1gNCKCXOMc6fvzayS1ps=",-885016787802342796,4230592091276180592,-7114519035443261883,-2737475055061877158>()) {
                                 case 1701210067:
                                    if (var6.size() == var19) {
                                       switch ((int)com.yiyiaddon.m.b.a<"sjbg91wfj36xv","hyb+r0dxdt3K98EF6HTEb2QH6iVWsKvf6KDMKl8aZiA=",7403619001827536812,8917833113059539186,-5350680375076429138,6793676045537357809>()) {
                                          case -15105105:
                                             var24 = true;
                                             switch ((int)com.yiyiaddon.m.b.a<"s2nlaq4lkc7ywu","OsXAVitvWpyid1GZ67pBUYMjKAPNY0+znfecSRiWJkQ=",-6133890245752643393,4847729426175387455,-2730543320016431507,-5727610479258368546>()) {
                                                case 41402940:
                                                   break label118;
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

                           var24 = false;
                           switch ((int)com.yiyiaddon.m.b.a<"s14t8dd5qu8g0v","gRjgrGwwmJF7jUTozLlPcrsbhoMQba4O77/3ei064hQ=",8098716346409169611,5783692118594802209,-5816816574620706561,8402304224274660156>()) {
                              case 406859333:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        boolean var15 = var24;
                        boolean var16 = a(var1, var6, var2, a(var3));
                        return new f(var19, var6, var7, var8, var9, var10, var22, var16, var15);
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

   private static boolean a(o var0, List<o.a> var1, a var2, double var3) {
      List var5 = var0.R();
      List var6 = var0.S();
      int var7 = a(var5, var1);
      int var8 = a(var6, var1);
      switch (var2) {
         case STRICT:
            if (var1.size() == var0.Q().size()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2f0rbbj7vttk7","x2Edz+hpPOxk5AfIgmPTbHUMS2+0ZYvIIMt/C8gk1cE=",4093201558208016438,2756725459183491890,-4813128454024506628,-7114790367885713694>()) {
                  case 1352562074:
                     if (!var0.Q().isEmpty()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2dyddg715n4tz","JCEZWpkAgynXMp4AfY+XlLYR60z6AWdtuDfnfsMbNB4=",-6826280636289612545,-7836993677649102631,7973368331058734581,-8036438731059604754>()) {
                           case 184626153:
                              switch ((int)com.yiyiaddon.m.b.a<"sozqn91njwe8i","VMc/z59WaJ77OpqcjuiExyEaKI9gh671HYDZCubb+A4=",-4186732296110270539,-5817756205429244592,4461835585825017760,2403691032692680384>()) {
                                 case -1922905249:
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

            switch ((int)com.yiyiaddon.m.b.a<"sr9cccketp5d5","ZVg1uKviZXm6fBADteWG6utBeb1vLJOZpEZ5LQdnXfw=",2544592852836234832,-7036992694202749451,4612140741412426162,2794316757915855608>()) {
               case 495485329:
                  return false;
               default:
                  throw null;
            }
         case BALANCED:
            label80: {
               if (var7 == var5.size()) {
                  label56:
                  switch ((int)com.yiyiaddon.m.b.a<"somrnvfqvbhvo","juqTd4iQ1Xaod16Hr+Y2ryKPdcm+vtCfMEgzc3eVDgg=",9044166132069317028,277505558090508747,-8292484772249273620,-160348602565315712>()) {
                     case -1996198266:
                        if (var6.isEmpty()) {
                           break label80;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"srhfi5xdbyyji","EOoficlIrTM9uLl4o6vY50nXnxNcrJf1k/rFZ6Todo4=",4998507847446922515,-5255805434058588710,-805412330003725325,-9025305968533646953>()) {
                           case 487802174:
                              if (a(var8, var6.size()) >= var3) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2b3mxu7q7md9p","90Mh5I1S1OreII9OAcWd4hvmygDgDLPHoJWW8ymyo6o=",5084727655399545034,-5355687936360735309,-4070073691804519465,-3098431538334845858>()) {
                                    case 387892231:
                                       break label80;
                                    default:
                                       throw null;
                                 }
                              }
                              break label56;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s2rjtti9d5td38","55FO60eQKolF568O/ZLMF0G3SRSICHptAhAEg//aERg=",-385030567696433519,-8678662774636113850,-1278524643433636742,-5961891498962652644>()) {
                  case -1874148845:
                     return false;
                  default:
                     throw null;
               }
            }

            switch ((int)com.yiyiaddon.m.b.a<"s1crdvku1pj2p4","YsHPnWy4i65JSlbKouDhqxSScIJHT+GdfAKIZarMVCU=",8300599054399073022,5915841361858950553,5903113359780331764,-7710049975478869576>()) {
               case -1945354350:
                  return true;
               default:
                  throw null;
            }
         case LOOSE:
            if (!var5.isEmpty()) {
               label60:
               switch ((int)com.yiyiaddon.m.b.a<"s20f1hnlr546mo","2LiQLw7xIx2s35n8I2DnefyObJIu4MiNUDtX1VggVGw=",-5771066803032550219,-1010781822736145408,1527838741063810525,5344610070381396962>()) {
                  case 2112077425:
                     if (!(a(var7, var5.size()) >= var3)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3e6oiansf5nsq","JRXPAdE5710qp5I1N+kLsA7kOdSj8WCsjGIhkz6fyyM=",-4734415335550903928,1321386842596325994,-2066408220879261743,2687953380124946003>()) {
                           case 1899515805:
                              return false;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s26qcgilgicuu1","OTAO/35djg/rR3+zYTu8QsalcKqQeH6Auw617m0om7A=",-6073060986031625411,-4697536307217740323,8356431780857775737,-2790281384754786717>()) {
                        case -394915652:
                           break label60;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            switch ((int)com.yiyiaddon.m.b.a<"sa2695p3186u1","p1EVaKLnnSJnv+kKZSJS8FV33R7ShshSGCI5v/Tc2WY=",2244661917663983496,7031313788606807552,-6748603242158676291,8492718023253856645>()) {
               case -1237033943:
                  return true;
               default:
                  throw null;
            }
         default:
            throw new MatchException(null, null);
      }
   }

   private static int a(List<o.a> var0, List<o.a> var1) {
      int var2 = 0;
      Iterator var3 = var0.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2ae2uahifhid","Ay2DQugQ8PVBL+C0M9GS5uPV/EmvilfUM/Y0HNvNMoU=",8869528643503235709,-7541679329951213395,-2024471617706409042,-3894888721567569248>()) {
         case 1631505611:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"soqda4751kmhr","ZTtTI6XD+JVXV8E3DDLD6bR5Vmxb41rAqBmmrzDJheA=",6365967144426336944,-5690226112117982037,6900336699327482802,7202660710668070686>()) {
                  case -1577014158:
                     o.a var4 = (o.a)var3.next();
                     if (var1.contains(var4)) {
                        label23:
                        switch ((int)com.yiyiaddon.m.b.a<"s4mkn7xextmii","2dc6qtxIsymemcnA07bmjmNgStZZAsv+Lpip5gXY0EM=",-7027748020751318769,-7402171660672621236,-3636479789241267085,-4195975451465787161>()) {
                           case 412526376:
                              var2++;
                              switch ((int)com.yiyiaddon.m.b.a<"s2jc8yf92svj61","Q0llT/REi9t9PZn/ec2XghlolqZGdUbn6J6l1FmyWU8=",-7286660428667179584,-6446724295170495360,3314286492121677467,2103849231032268042>()) {
                                 case -1672655727:
                                    break label23;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s46u5yqktavbb","HMlbBT+usjlrf7babQCa8JpKLy2gFnkTkAhBhCqRJ5A=",-2256960655493051399,7864194737047269508,-4658163446591320161,-8850105062440607941>()) {
                        case 110644267:
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

   private static double a(int var0, int var1) {
      if (var1 == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s2qykbgdjrkey2","wbGo/OLZIMFRTcuet78fi6rpEn8fP3s+XsXXX3GvRWU=",5451965432872056051,-3937898247676047333,2781838072463403198,-6208563894205654095>()) {
            case -1846038826:
               switch ((int)com.yiyiaddon.m.b.a<"s2ni3qp1p8xn2t","+nKtAbH1tysJtTckfSZRWXBrNEKAXZ0QZRWWv5wBq9Q=",2836774717429338871,2072550126387421412,239445086524466377,7126991894064551856>()) {
                  case 307511145:
                     return 1.0;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         double var10000 = (double)var0 / var1;
         switch ((int)com.yiyiaddon.m.b.a<"s32vc4o6bp9uf4","wi7xEGdezkQS3PuITT3iH6DNXMBsQ6tJAe8NXTEM1TU=",-2869657083613192362,3322590543641629463,1543544040029347053,-7782909537383024295>()) {
            case -1132418923:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private static double a(double var0) {
      return Math.max(0.0, Math.min(1.0, var0));
   }
}
