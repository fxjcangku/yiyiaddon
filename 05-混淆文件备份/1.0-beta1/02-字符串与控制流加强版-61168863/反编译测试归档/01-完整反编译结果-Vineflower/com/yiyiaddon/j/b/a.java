package com.yiyiaddon.j.b;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.Predicate;

public final class a {
   private final Map<String, com.yiyiaddon.g.c.a> aW = new LinkedHashMap<>();

   public Path k() {
      return com.yiyiaddon.i.f.a.h();
   }

   public void C() {
      this.aW.clear();
      Path var1 = this.k();
      if (!Files.isDirectory(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s3h5p2y7tbo2w9","ZYt9SQ1UoJqx4Ofr0erMBD1dP189/eF5J/Rw5OoaadQ=",-446738503264240342,4500531929660537883,6006002021016595405,7015488894049563468>()) {
            case 1992858755:
               return;
            default:
               throw null;
         }
      } else {
         List var2 = this.c(var1);
         if (var2.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s17yajph3clceb","pa5OASaML/HYvSwN5r+aS1AKCKxgX5hkQJlG5ZnyCXo=",6352887800667396907,5683581074450084468,6452235715684998177,-9024325309012256834>()) {
               case 563184508:
                  return;
               default:
                  throw null;
            }
         } else {
            if (this.e(var2)) {
               label39:
               switch ((int)com.yiyiaddon.m.b.a<"s3k6f9h5740prz","LGKvMOB0CEA5GhgM3qVqZ8V+BQkCUZbopg5xcS6tNrk=",324825291080670067,-3694651166492049685,5112502957207810551,2017777064874669668>()) {
                  case 372536569:
                     this.r(var2);
                     var2 = this.c(var1);
                     switch ((int)com.yiyiaddon.m.b.a<"s2urzvdahtmqxo","CYuz7fj+CqYLNlGClx39sXPyS62KwiJoAOjrF5jiB4A=",8457362130214838640,-3810091569004093514,3458029300438338006,-6876295368583240225>()) {
                        case -1674169331:
                           break label39;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            Iterator var3 = var2.iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s1qr7gygdgg0iu","ZQjGXEVBwPXN+qnxR3l+ijOKxBfxq7U+IwRNwjNYxHM=",6211296536931860308,5439812739894959968,-5418372704994901593,-3090835379488160681>()) {
               case 1538720358:
                  while (var3.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1nhif36d3ub2s","1SzLt5j3TjoyHfskv309sIuKnVXwNiMsbmeY/oqaobU=",4294386126991539623,96142500420959548,6756804518752034620,-7361674376211270102>()) {
                        case -537570076:
                           com.yiyiaddon.j.b.a.a var4 = (com.yiyiaddon.j.b.a.a)var3.next();
                           this.aW.putIfAbsent(var4.b().dF(), var4.b());
                           switch ((int)com.yiyiaddon.m.b.a<"s2qnh91fu62fhe","o2l77rRLwy6rvDYsumeWV6HDBUdiblhkmtyw5pbkhdk=",2823977070060086396,2531325860271658970,6567401738085462879,-8232959222287666792>()) {
                              case 1516877724:
                                 continue;
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
      }
   }

   public String a(com.yiyiaddon.g.c.a var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2rplnuzdjlj9w","8TPpAn6DrHcjT66E2PTaYh1tM84Sy1aiZ0butuEU2P0=",-8529346869798838419,3740329831176503083,8014218655844761190,1437108675141063195>()) {
            case -1544395146:
               return null;
            default:
               throw null;
         }
      } else {
         String var2 = var1.dF();
         com.yiyiaddon.g.c.a var3 = this.aW.get(var2);
         if (var3 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"slpdp2f8noh5c","vTLaGURaegzzjY/aglupv8InSIhaSMQzMBBQZqFGiAo=",-6752425273556695860,2756889084859803152,7495423077743623703,-8663094789219337432>()) {
               case -2086327896:
                  if (var3.equals(var1)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3tllcynz76j1h","60aZmjWvzm1UYYVn/GhiIm8d0TL335UPdhc968bYTy0=",149406106529253225,-8262593341526981750,-8505879285246342351,298476963075141469>()) {
                        case -1567681267:
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

         this.aW.put(var2, var1);
         if (!this.fy()) {
            switch ((int)com.yiyiaddon.m.b.a<"s1df291s3sap8d","u95GlgOxI738fLMiefKEs3cNmcMEyuqhLHnsLuKikRg=",6008013579627120370,-524871924176783641,4252974382824723643,-6973209929364941427>()) {
               case -649895562:
                  if (var3 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1fz0wtmu07isw","iVlS70WA4Q8L3SFtJILt+ekY0wSSaqgG27vvquQmzt0=",6974567438596737063,7432371168158729285,-6367248914253058868,2595530200155667524>()) {
                        case 923670373:
                           this.aW.remove(var2);
                           switch ((int)com.yiyiaddon.m.b.a<"smucqx5eipbtx","PxkT1HHZh0FleALX0oOhe22uWvmmmGyJ6LM1bvNNQiU=",3024689078463115352,-2947129875148801077,-7745360481690168229,-5801094826767398863>()) {
                              case -196174394:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     this.aW.put(var2, var3);
                     switch ((int)com.yiyiaddon.m.b.a<"s22dyey3081mrc","42j/Z4w1qxV9b45Ol0vfTLWotnLdnqLu3USpxaVuGts=",-7638367096339750361,-3018415852707463405,-5898267768598706234,-1394163375758126832>()) {
                        case -1116854831:
                           return null;
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         } else {
            return this.b(var1);
         }
      }
   }

   public boolean b(com.yiyiaddon.g.c.a var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1y0qlw1q7tspk","UgpNw/rB4gK6hma5O9s2oi8UWr/2xH6Coq3Cfi5vO+o=",8035578789433707884,1835102040858845095,-2656447003324154131,-9208701128851202233>()) {
            case -198300827:
               return false;
            default:
               throw null;
         }
      } else if (this.aW.remove(var1.dF()) == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3vs93rcsxdfwi","RgeCo91j5IxCIcNrdqjlU0dXMI/k2QtOuSUT1AnQmyI=",-2859141514000492479,2606715369981441146,2255226918671326461,5809631953712742170>()) {
            case -1881070544:
               return false;
            default:
               throw null;
         }
      } else if (!this.fy()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3vci8uhysdwcq","CEBTmLTSvpHOoK7M8ZoSHhQYAvINe4ykTdDc1HqBlPQ=",-1810262764695893649,-4129438937868808253,6481031254975397419,4779392663772065500>()) {
            case 130710227:
               this.aW.put(var1.dF(), var1);
               return false;
            default:
               throw null;
         }
      } else {
         return true;
      }
   }

   public boolean fx() {
      LinkedHashMap var1 = new LinkedHashMap<>(this.aW);
      this.aW.clear();
      if (this.fy()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3w0k820iu450p","Thmoi/3mSjPy8kW6Uv9vNk+g6iYFDqEjoEecbN/nyag=",-4294061347011946430,1635086594315385304,4472171088885135548,583506093755630291>()) {
            case -1079233322:
               return true;
            default:
               throw null;
         }
      } else {
         this.aW.putAll(var1);
         return false;
      }
   }

   public Set<com.yiyiaddon.g.c.a> A() {
      return new LinkedHashSet<>(this.aW.values());
   }

   public com.yiyiaddon.g.c.a a(String var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2cuzcz56ixsx","7aBfdcwvUuYwMLafIp28eg/xTOKpi+9/vXP3lhQ6i0M=",4239888815750146695,5534132824502977706,525274895501195723,4093496769852531809>()) {
            case -1707393757:
               switch ((int)com.yiyiaddon.m.b.a<"s2q7ajtya45166","XKbarmj6w23SAnxtjLppX465gDqGkQIZoqGbaQAIaKs=",300108466609889389,5138842584690532766,427379972223142317,819324851687707942>()) {
                  case 257380267:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.g.c.a var10000 = this.aW.get(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s1ou5swljuh847","WoqN01gQc04hKAsWxRlMXZXIug4GflHUGTRLJkmJm1M=",4161117692805197281,-3485121962380023813,4842685900697438906,-1053130457981131110>()) {
            case -1830569685:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public int d(Predicate<String> var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3vdxe2wawn2oc","34zdYVPDsR9pZodjb1qwi+lTjk/LYaEBHYo1OIgrbfM=",-2903582960354107228,-8673744079463521432,-5535710230989169907,9197774728776830105>()) {
            case -1944299851:
               return 0;
            default:
               throw null;
         }
      } else {
         LinkedHashSet var2 = new LinkedHashSet();
         Iterator var3 = this.aW.entrySet().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s3n9cw1zpnif0f","hwMQwIgMnMyHZPxduiyGAoEVe66oO+Cw5WT7ePfDJiY=",2979516837908937874,-8575645290393889992,-8960173902696356389,-6224897125889039339>()) {
            case -2046854182:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ozmzph1jey1k","2Hy3JNEMGUxx6KFWw0+KpAB+b3KQSP6ke2kzDlUP5SY=",-2840637832741734435,4538193201549029013,-5867481672946665232,5665818634345160234>()) {
                     case -1237586743:
                        Entry var4 = (Entry)var3.next();
                        if (!var1.test(((com.yiyiaddon.g.c.a)var4.getValue()).fL())) {
                           label51:
                           switch ((int)com.yiyiaddon.m.b.a<"s9he9gmhl054x","CS1tjyUxNmuKH09PwIOgJENV6orjKAh8CfEATrzCW8g=",4597025312778509537,3063009001542308424,1423573427450350915,-2991492096876155494>()) {
                              case 287211324:
                                 var2.add((String)var4.getKey());
                                 switch ((int)com.yiyiaddon.m.b.a<"s1jejn400en2ac","0Z9LxzwGMWRlRTXzoD5S4pi1pZWPpYhh1kJ6+pNXlBQ=",6168839529585860042,4066307472887888323,-7417207907908111553,7639704759639114477>()) {
                                    case -2026104858:
                                       break label51;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"sdq6jnfvm95vu","SnZ1sJ0eQvwbmkeICqBgo58KNr82FBiVa6SG7EnAh2Q=",-8947337721000209345,5400706579940874336,-2594778181423801422,4037028366992505401>()) {
                           case -447586657:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (var2.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1zyl81vmont1m","D7ngalR4ZPqgwBM0ZUkEvSed2eJOVy3u2KRwLGK//NY=",-2777240959976284591,-9222967352232439352,3204581103792365135,-4834448989889900201>()) {
                     case 1998026324:
                        return 0;
                     default:
                        throw null;
                  }
               } else {
                  var3 = var2.iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s1020530z3f61m","orQflYUbJx5oYKmN7ZMw0itlsEQ/En2dSkL+o6npRHs=",-590961430772430626,7139391114713698239,2147531795254009005,-2325337796437994192>()) {
                     case 1430031559:
                        while (var3.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1gqx0srpr9vfv","KUM8+KI0iEYaTsKkrJF0pdPE3Vp3jKZ91TJyVAtoo1A=",-5709603334146466428,696203563492010169,-5217527617100677260,2967218198469313810>()) {
                              case 1837620861:
                                 String var6 = (String)var3.next();
                                 this.aW.remove(var6);
                                 switch ((int)com.yiyiaddon.m.b.a<"s3kcrgfktcj2kv","ylEtgmDgQrZIfpO8+o/alehx3140Nn+hMsWxOPt7wzg=",-3815137788787637574,-1652041141752834121,3084760648995219963,2487782836767138929>()) {
                                    case 962648779:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        this.fy();
                        return var2.size();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }
   }

   public boolean dt() {
      if (!this.aW.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"suvzquqvbrdi7","LZtSnyZShHYaqd6Z5WIDf5Cs32PGA74ocKsVGwJgFgQ=",5707360209472316436,4057865566691848345,-2699646687833562151,-2857905521757076962>()) {
            case -1708730550:
               switch ((int)com.yiyiaddon.m.b.a<"spwxzpq40vgd7","TkL2r5zauzeMZcVcSzU8bZXi0tT/3W46IT8uAE/douM=",-4572510033424812910,-1079067688980687212,-4685599853845267060,1259205518675714567>()) {
                  case 1220410597:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1gn1ijgogc10z","jl0/6L3Wj0aI9lmQHJ6ApLfP8Jo+jTKXHCK3tZs2+nM=",6986297972054921843,8159886241066653631,-9000927017943836560,-4331596284788438711>()) {
            case 111820485:
               return false;
            default:
               throw null;
         }
      }
   }

   public int a() {
      return this.aW.size();
   }

   private List<com.yiyiaddon.j.b.a.a> c(Path var1) {
      ArrayList var2 = new ArrayList<>(com.yiyiaddon.j.a.a(var1));
      ArrayList var3 = new ArrayList();
      Iterator var4 = var2.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"srgc3i68j2x0z","I0+vP5DLv5JuKIqAbcggPrqbo1i+3RNYAWAcl2XOx34=",8300366131350521227,8199282963527148616,2026405057071549567,4996672013998504594>()) {
         case 954340464:
            while (var4.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s17okcoystgo97","6bt6gA/YraelkBRqNk8u6CbWUlP/8fPvep3rvhkvMEM=",5554149574975971818,745735901804835149,-1318453304135573063,-3066782949735578242>()) {
                  case 702216097:
                     Path var5 = (Path)var4.next();
                     JsonObject var6 = com.yiyiaddon.j.a.a(var5);
                     if (var6 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"sbbctyxz62xo","WPo/5LtUAPv9GQ1sIPAfsSKBnIxftJo/Wp3xWXezrE0=",6556099280582256998,-8828436505135049388,-1389549514776124177,4851956272768919350>()) {
                           case -1938703669:
                              switch ((int)com.yiyiaddon.m.b.a<"s13lt4vxtb3uns","fhYOv8rUmK5t8IwIgha0Y0NDCqJNPVRKhTEFa8yxogA=",-6875210696130225378,-6638547174481253948,-5595332550322558594,-7712475716415943012>()) {
                                 case -1433669264:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        com.yiyiaddon.g.c.a var7 = com.yiyiaddon.g.c.a.a(var6);
                        if (var7 != null) {
                           label28:
                           switch ((int)com.yiyiaddon.m.b.a<"s10869bqiksgsr","+UD7Lq+XQ50Vpt3R/XTCEzsb/lPgQJpzI/kCm/FQROQ=",-1641548037023448919,7523319778450520999,4255682292524201648,-6142933120216967764>()) {
                              case -1971809448:
                                 var3.add(new com.yiyiaddon.j.b.a.a(var5, var7, com.yiyiaddon.j.a.a(var5)));
                                 switch ((int)com.yiyiaddon.m.b.a<"s1kj7d277k69m1","1LwNOuuqqLHuwCUKszD3GCnO2sOsyvEdi2Jz/2EiX3A=",5462421695536861681,-8620785812307479340,-5569446109618206662,4201124488794845392>()) {
                                    case -51524475:
                                       break label28;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"sv2d3k5gaxuy","k5YIefgHHvyTwRFV1IqeApqkz0AUs8TR3brV+wl1aUY=",8147243714340586311,-8516308912514439156,14897362753386488,4094646564841946276>()) {
                           case 1806511859:
                              continue;
                           default:
                              throw null;
                        }
                     }
                  default:
                     throw null;
               }
            }

            var3.sort((var0, var1x) -> Long.compare(var1x.z(), var0.z()));
            return var3;
         default:
            throw null;
      }
   }

   private boolean e(List<com.yiyiaddon.j.b.a.a> var1) {
      LinkedHashSet var2 = new LinkedHashSet();
      Iterator var3 = var1.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2zw4ls0vyvybm","q387Igf6z4FxGyP2kG35pOQ2DxOCgFSN7dJfnKaRZtM=",-7352217714285920741,7671816436268092228,-1371185978391070246,4998230741243256691>()) {
         case -1771474372:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2vzfte051rwop","2DiyZi/69BjlcsXvj+HHm2y7pj2aNNVPaQKVcdjlbhY=",-3919226216792714020,8764582920084576430,-3592464126493020755,-5582953448952972839>()) {
                  case -647221186:
                     com.yiyiaddon.j.b.a.a var4 = (com.yiyiaddon.j.b.a.a)var3.next();
                     if (!var2.add(var4.b().dF())) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2wr4q99fniqby","+lHJMdayvGwvgYW8igZjFiAFeVGTgXXzCuEBRrm+u6Q=",2492210017509464971,3378153624156150615,-5295932679958034651,-8719600635470456410>()) {
                           case -1883070821:
                              return true;
                           default:
                              throw null;
                        }
                     }

                     if (!var4.c().getFileName().toString().equals(this.b(var4.b()))) {
                        switch ((int)com.yiyiaddon.m.b.a<"ssh90ka05rh9y","iLcy8dooSPyRGrmozDyEWwJ1oAyAaC5A+RPXp4D/daM=",4793753291852645115,5840386762699706952,2504066111143839648,-4892665281519915235>()) {
                           case 1389994535:
                              return true;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s216sw9rw2qfcd","sjAuuLnTlHxzcHTC/LczN+txyGyhiStGIX0Danb0/e4=",-5346801135777546700,-8520139138101939735,-8274992414684571807,-4766345013787245982>()) {
                        case -155669186:
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

   private void r(List<com.yiyiaddon.j.b.a.a> var1) {
      Path var2 = com.yiyiaddon.i.f.a.e().resolve(System.currentTimeMillis() + "");
      boolean var3 = true;

      try {
         Files.createDirectories(var2);
      } catch (Exception var12) {
         var3 = false;
      }

      if (var3) {
         for (com.yiyiaddon.j.b.a.a var5 : var1) {
            try {
               Files.copy(var5.c(), var2.resolve(var5.c().getFileName().toString()), StandardCopyOption.COPY_ATTRIBUTES);
            } catch (Exception var11) {
               var3 = false;
            }
         }
      }

      if (var3) {
         LinkedHashMap var14 = new LinkedHashMap();

         for (com.yiyiaddon.j.b.a.a var6 : var1) {
            var14.putIfAbsent(var6.b().dF(), var6);
         }

         Path var16 = this.k();
         Path var17 = var16.resolveSibling(UUID.randomUUID() + "");

         try {
            Files.createDirectories(var17);
            LinkedHashSet var7 = new LinkedHashSet();

            for (com.yiyiaddon.j.b.a.a var9 : var14.values()) {
               String var10 = com.yiyiaddon.j.a.a(this.c(var9.b()), var7);
               if (!com.yiyiaddon.j.a.a(var17.resolve(var10), var9.b().f())) {
                  throw new IOException(
                     (String)com.yiyiaddon.m.b.a<"sezb74yvw6dm7","i50gWOtF0OtQ0H/TVbU4+touP7ieoZM2yl5LI01sankHdKLXkh5GwA==",6996156210743549222,-6117191879710865285,6076422135701405648,-2123271115306122543>()
                  );
               }
            }

            Files.createDirectories(var16);

            for (Path var20 : com.yiyiaddon.j.a.a(var17)) {
               com.yiyiaddon.j.a.a(var20, var16.resolve(var20.getFileName().toString()));
            }

            for (com.yiyiaddon.j.b.a.a var21 : var1) {
               Path var22 = var21.c();
               if (!Files.exists(var16.resolve(var22.getFileName().toString()))) {
                  Files.deleteIfExists(var22);
               }
            }

            com.yiyiaddon.j.a.e(var17);
         } catch (Exception var13) {
            com.yiyiaddon.j.a.e(var17);
         }
      }
   }

   private String b(com.yiyiaddon.g.c.a var1) {
      return this.c(var1) + var1.aj() + var1.ak() + var1.al();
   }

   private String c(com.yiyiaddon.g.c.a var1) {
      if (var1.fQ() != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s10l5610n4tbo0","yHYL5mm8lnwZ0ag4uuJbcJCV94KpZc0EuhnoSuKaLC8=",-7177949660656763579,-1746271780072963727,-417911396873288626,-6772443887388928842>()) {
            case -1867811021:
               if (!var1.fQ().isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2y8m48f6wituw","eTg6eSO8erxYPsG3tRM5IXevqnK+EywwmRM5BRaV+p8=",-8515911265429757010,-339537644257119931,-2633939321553243255,7805392548605925878>()) {
                     case 956935395:
                        return com.yiyiaddon.m.a.B(
                           var1.fQ(),
                           (String)com.yiyiaddon.m.b.a<"s1lpm04a0p7bnb","vONtThrO1EbIzRtYoHjme/HhbaKdlSjr6VZTAuRdzRAIndwxmDw=",-6717898472426237293,6128782418939739274,-420109406175988766,-6036024427034260557>()
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

      if (var1.dr() != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2p4ag1sc4kwfo","0mTD6fTGAVXVusu/ouEXPHEMicUkbAPzNWScTKrTYgg=",-220742410667807594,-8024980155931663910,7601055403561443191,3373030013205004979>()) {
            case -1046119356:
               if (!var1.dr().isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s18s2yurghzuoj","YGMdE0d/7dEppA/rj3xShRzJiZsE+0GU9lKL78r1T0M=",-7946747794179214485,7221056419414458448,9073459532431078515,-7817720313372307374>()) {
                     case -234247175:
                        return com.yiyiaddon.m.a.B(
                           var1.dr(),
                           (String)com.yiyiaddon.m.b.a<"s1lpm04a0p7bnb","vONtThrO1EbIzRtYoHjme/HhbaKdlSjr6VZTAuRdzRAIndwxmDw=",-6717898472426237293,6128782418939739274,-420109406175988766,-6036024427034260557>()
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

      return com.yiyiaddon.m.a.B(
         var1.fL(),
         (String)com.yiyiaddon.m.b.a<"s1lpm04a0p7bnb","vONtThrO1EbIzRtYoHjme/HhbaKdlSjr6VZTAuRdzRAIndwxmDw=",-6717898472426237293,6128782418939739274,-420109406175988766,-6036024427034260557>()
      );
   }

   private boolean fy() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.aW.values().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"sieh714lz51a2","3HQ46AjreZkIBkg4JqG4IrUm+a5NDsNswZOcK82sQro=",-4759032021759367314,5250751898685843793,-8187551407007243623,742007871812525454>()) {
         case -665305190:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1cs834zch0jto","rGp0XbDVjXGH5gxK417wbUoZ1i5ANSCTcOQoRniFSMA=",3634165611611893095,-8395130368634047742,-8165992807444428359,-3866447464902482347>()) {
                  case -1741819983:
                     com.yiyiaddon.g.c.a var3 = (com.yiyiaddon.g.c.a)var2.next();
                     String var4 = this.c(var3) + var3.aj() + var3.ak() + var3.al();
                     var1.add(new com.yiyiaddon.j.a.a(var4, var3.f()));
                     switch ((int)com.yiyiaddon.m.b.a<"s3shnf9iygakd","ghkcnnxDWjOzTD9z+pBWvbvrmMMqC+Uq3Te8lSw2wTU=",8244919820127529367,3792518203664467441,7956249526875894547,-5547816538890789157>()) {
                        case -1352161160:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return com.yiyiaddon.j.a.a(
               this.k(),
               var1,
               (String)com.yiyiaddon.m.b.a<"s39z25k6rvn55e","v6egSNPiW8YUS2mhMC8fDC7mTCPBqUibIZ14xSDD6gTHAaSdj/2SMg==",8808332185658455615,1325311181457186447,-3637620308856608962,-1000105692437546243>()
            );
         default:
            throw null;
      }
   }

   private record a(Path q, com.yiyiaddon.g.c.a b, long aL) {
      public Path c() {
         return this.q;
      }

      public long z() {
         return this.aL;
      }
   }
}
