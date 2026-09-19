package com.yiyiaddon.e.i.g.a;

import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.c.f;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;

public final class c {
   private final a d;
   private final com.yiyiaddon.e.i.a f;

   public c(a var1, com.yiyiaddon.e.i.a var2) {
      this.d = var1;
      this.f = var2;
   }

   public void d(i var1) {
      var1.a(new f.e(this.d, () -> this.ai() + ""));
      var1.a(new f.e(this.d, () -> this.bB() + ""));
      var1.a(new f.e(this.d, () -> this.f.a().fj + ""));
      var1.a(new f.e(this.d, () -> this.f.a().eS + ""));
      var1.a(
         new f.e(
            this.d,
            () -> {
               if (this.f.a().bC) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3jnzqc8a76v0w","UYscBl3O1Tw+p3fmegldqXIzrvq5werBRxTeUL9Hrho=",-3193237079779790870,4801070448904411294,-1023834520133441058,8588330928794444297>()) {
                     case -90818246:
                        String var10000 = (String)com.yiyiaddon.m.b.a<"s3f4tieg9e8juf","iQ+hwNSExe1gqjPWIfMa8AW4yW8LC85GEJBM4UdT2Wptz2QkPhg=",8953896863332244459,1041660290663916440,7298603884899780977,9157885246506264823>();
                        switch ((int)com.yiyiaddon.m.b.a<"s3ojgtcxku3yy5","bUJbJxXI7xQjrnRnWv3qheVvQy8hI9GrdhnvV0EhVJ4=",-3622911298807295987,872536576920004413,9056158633757009283,-4340819229778838883>()) {
                           case 1780220563:
                              return var10000 + "";
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  String var1x = (String)com.yiyiaddon.m.b.a<"sgvtp7jgmgi98","Oj/ailUDiNIOniY7m4qyZ4pMAocBVYaPASBg9LoguX5HsLsSu0E=",-8811793017985587513,1040095034752101652,-7793804121169201807,-5621324628806199434>();
                  switch ((int)com.yiyiaddon.m.b.a<"s1mwvfbtia3a2a","8e5Eq3AZm88tNbey/MWURXbK1KXAKgc/KECj8gsVr8c=",-5501996726027423469,7266156273702456613,-1412680544361441183,-6481163905383797080>()) {
                     case 2024794745:
                        return var1x + "";
                     default:
                        throw null;
                  }
               }
            }
         )
      );
      var1.a(new f.e(this.d, () -> "" + this.f.a(Items.LECTERN) + this.f.a(Items.BOOK) + this.f.a(Items.EMERALD)));
      var1.a(new f.e(this.d, () -> this.bC() + ""));
   }

   private String ai() {
      com.yiyiaddon.e.i.b.a var1 = this.f.a();
      if (!this.f.g()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1b1v1tvro4vh6","U7apCSeQSNl8Y2CcSgaUNmySIUYzflSdxRC3iTtezAU=",-6666812187492035008,3634512564880874488,-949968400854244726,-8014443961546040583>()) {
            case 99458741:
               return (String)com.yiyiaddon.m.b.a<"s3apktkwf6vaya","1A1GF7HqCo9+WKwldW5cMPHCf2NpJxkijvf+reHRCcAHg14wpic=",5058014664772600982,6529382866996000268,-2002171480072944668,-3061909219775608478>();
            default:
               throw null;
         }
      } else if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1lwrelfu8wcvs","XJKk+kuYdY1tdXOPe5LKM0UyDT3Qpa0Kr1v4xwLnPmg=",8458182636243871802,5804403712163678308,4520189555528961610,-1140762296360740789>()) {
            case 168039714:
               String var10000 = (String)com.yiyiaddon.m.b.a<"s3qelassbsaacq","+IlQ5o0F3kGz4HmoBYMID0nfyYomTJ0XGEXdUaRgg3yNDl+9J8SDnQ==",5013128329416332331,-6352446916727457690,-6996922749052771837,-5223119156040696796>();
               switch ((int)com.yiyiaddon.m.b.a<"s1klg9hkxx4snz","WVgwFS8SJwRjT0QImp88iveUlxsQM8qwXQ1yOTp3Vvw=",-4897613411494264840,-7674950622317373446,-2641382725086160955,-7882489455718681192>()) {
                  case -236443148:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var2 = var1.m() + "";
         switch ((int)com.yiyiaddon.m.b.a<"s2vxdy7tq4m6e4","2Zmq5trw9qwq+dReO4+CFKidRTvu1u7I40+khK/dFq8=",-6906283652578093371,-1999873728058076066,-5987332332144416511,-7355611150108774008>()) {
            case -1890673325:
               return var2;
            default:
               throw null;
         }
      }
   }

   private String bB() {
      List var1 = this.f.a().af();
      if (var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"snda9jqzdaean","Yhq/IkUBybdboSWJL2/9DwPmqralZQsbr/K/mox1RSo=",-1661023150508134375,-105901117229292576,4258144673305995733,-4910909652988344095>()) {
            case 618165887:
               return (String)com.yiyiaddon.m.b.a<"suu02ruyq8hcy","Dnzmm+iGXHqtCtK+C6HqW5E8qol01iEBGe4OZXSyw+zJXVCetSU=",-1013685192668541715,6445155673396704000,6336445649340534922,-4944595968143317587>();
            default:
               throw null;
         }
      } else {
         StringBuilder var2 = new StringBuilder();
         int var3 = 0;
         Iterator var4 = var1.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s347o7v9nv4mab","m36eV5Y84knUNxKrzodc+fgB1rhmnnm5iPLHxQARwAY=",-4330321840778899606,5118130311873072814,-1328781361208897081,-4343360894619959109>()) {
            case 289330981:
               label69:
               while (var4.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s36j83hhqd7i80","Le9fH6LL0H2qvsv4wEXDQSPa2uvkKgDMqm4jBZF8uE4=",3073182039861255570,-2611388294938311032,-7214400368176739628,-7114797947232274254>()) {
                     case -230389165:
                        String var5 = (String)var4.next();
                        if (var3 >= 3) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1l015ixsoq7xb","f/Q1LYCJYY8Kmz0irU6B/0HOrXwO71s0K6KYPkXDV0E=",4715263970608448926,-424370773396984703,-8858151280020263944,-6698759612648196483>()) {
                              case 1421191268:
                                 switch ((int)com.yiyiaddon.m.b.a<"s255abrxfngaww","t6TqbzsF4dd8hMGM5VURwz9wjzPR6mFZMEMCrxc5TdE=",-4015630543523946409,3957652377481202782,-6312477740177471386,6097947216541041706>()) {
                                    case 406853837:
                                       break label69;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        String var6 = ak(var5);
                        if (var6 == null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1wuaae2yqiooi","VS6Y7qJ8nAqdh9xjnZPqzO9pzoxrCWph896BMdCJxDw=",9028268623619866219,1189148161065553845,-8422436241985482611,-9017961410054462197>()) {
                              case 319672591:
                                 switch ((int)com.yiyiaddon.m.b.a<"s36swyrnlpiyjc","jWIaf0Ut0OM0xpgrwpIcdlSopjJ/SEkYOdOQi7P8XGI=",-3738431971524741992,8331604697745672818,-5543798608563459893,646483103569595518>()) {
                                    case -199902846:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           if (var3 > 0) {
                              label50:
                              switch ((int)com.yiyiaddon.m.b.a<"stkgwnfyjwkvk","81sBB9Zb0RgQDX0KwRwKKIFM3IKj6YRD6sP6d1ZaN9k=",5985188668565163351,4487055429869865890,-5907882790112246647,-2995800060044824651>()) {
                                 case -1405033959:
                                    var2.append(
                                       (String)com.yiyiaddon.m.b.a<"shwcj8yily9fi","EZnZ1vV62pwnl3Fyw4ZXanDJUTc6ED85m6K7x5Eaw4kIVg==",-1848909791954614398,874262592389491742,-1841402764617733944,-8567593341154142537>()
                                    );
                                    switch ((int)com.yiyiaddon.m.b.a<"s217v808kg3o3b","wYpOGjY9j5/c7LgnVirwo0eEDDo/zO1X3Yk10Up3jJM=",108510932858850208,-4754054243353523766,-1870959569943375420,-133602605506502744>()) {
                                       case -787875437:
                                          break label50;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           var2.append(
                                 (String)com.yiyiaddon.m.b.a<"syokoipsvhiff","BlJpAyOvCqKt6yn7z9ZVEie9WoUaErYvCOLNYMW4en4=",-5078846999160738628,-4549026444717440055,-2368496444942659712,-1934353638423637197>()
                              )
                              .append(var6)
                              .append(
                                 (String)com.yiyiaddon.m.b.a<"s335m27bq1025t","GmBML683cnu1YHe6qp4R1aG801X70Q/iRK8vRLBJn36WW/ss",-5587675222953605677,5354053919705501818,-830966221075898653,6233508542174839759>()
                              )
                              .append(f(var5));
                           var3++;
                           switch ((int)com.yiyiaddon.m.b.a<"ssltvfzcty93o","veQ9wOpRaV3S0s6HAmSi06abXt7fdDKCd340UNF/iBo=",-6210497981500880489,8145759400531999821,-1535513846713501003,-315989256622583232>()) {
                              case 227668138:
                                 continue;
                              default:
                                 throw null;
                           }
                        }
                     default:
                        throw null;
                  }
               }

               if (var1.size() > 3) {
                  switch ((int)com.yiyiaddon.m.b.a<"s73krcijgvg0u","bzDL5APiFzogUaq/3d4ivEzE3wVVRh6PkXVdX+DVWxA=",-7081031148550019369,-1638811408641017488,3702187997275188947,4684418632181826643>()) {
                     case 649331188:
                        var2.append(
                              (String)com.yiyiaddon.m.b.a<"s3tqqzfwsfyf2s","6stxphrmzj9bFQzhPfATmZLFn9llRyc+CFr3qSMMS46li5XH+xrTF61v",3603182924057627776,-3987467665253587141,-8568039342798152407,-2234527499072786571>()
                           )
                           .append(var1.size())
                           .append(
                              (String)com.yiyiaddon.m.b.a<"sioa5vdw4g155","8YXgHckpY4CjoAIGo/L5cllc4G981FaT1L3gsxFaVwJPZOhk",-3126734530853468017,90002438573239574,399484575663350353,114284258714623494>()
                           );
                        switch ((int)com.yiyiaddon.m.b.a<"s2edxmfw2qx0fd","1TneGDJDHlN9Ubgu7AH0F2Ar3ftzNecAjM/lUML+Oe4=",-1436558366601174394,6055713357022322154,9054739998628392057,-7455238630970980811>()) {
                           case 1735086624:
                              return var2.toString();
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return var2.toString();
            default:
               throw null;
         }
      }
   }

   private String bC() {
      List var1 = this.f.f();
      if (var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s6doy1tqjowb3","2IiqBrfSGcVky8nFOBY0WziDOyegqCIFZN3EGSTqkCQ=",2261983921844979663,4362955876782611483,-4331347786785691625,-9105918286510853012>()) {
            case -321898573:
               return (String)com.yiyiaddon.m.b.a<"s2rs0tyg0ql9id","Cn7Mflg8uP/q+7YSsqD1tkN3q1Fv1H3XXga0jymhScMNdafo",5236630068379021116,-3406564704756903535,8312534680234073478,7640734966508358651>();
            default:
               throw null;
         }
      } else {
         StringBuilder var2 = new StringBuilder(var1.size() + "");
         int var3 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s2o8kqz2qkls95","y9Wb/e8WRTZsEE98r8VGqH3IjXZMF0hspI9etlIAz50=",2144898441872478131,-5492984870626178326,-6399946892788720187,-204965435682260855>()) {
            case 796575435:
               while (var3 < var1.size()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2i6u2fu2cgotq","U6Dlvr10DdWVeQfzPvyH/85XkUZZ3vfzRNl9xHYOoKY=",-4254871983765768579,7418533426657716511,8611745374911870706,375890020324434419>()) {
                     case 1414370483:
                        if (var3 > 0) {
                           label28:
                           switch ((int)com.yiyiaddon.m.b.a<"s1dr90uhw64dq3","IWXOlOtgJnmymPIlbuIhbnd1VZP9TIC8OzpMqo6b1Xw=",1624281730833473286,3512698031118126680,7631000003648413550,8043251368910179329>()) {
                              case 2094496973:
                                 var2.append(
                                    (String)com.yiyiaddon.m.b.a<"s1y0yj6vwyrcsf","xheV1FUoSiPaFnIZiVyXF5wF67k6ObHa8XOgFw6ho+EoezjfctY=",2853552981461679025,-359303700208551777,8325482209245978997,-1320882329144141638>()
                                 );
                                 switch ((int)com.yiyiaddon.m.b.a<"s2olwdo8bwsr1o","YnhdUxReUTN5j3DsJY7N134l3r++CTdbN5R1ZY5SOdo=",5584817136719248506,-1402338225924550953,-8462810638373438962,-5874629886049186861>()) {
                                    case 1019602188:
                                       break label28;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var2.append((String)var1.get(var3));
                        var3++;
                        switch ((int)com.yiyiaddon.m.b.a<"s3odn4k0hwbftt","/H8Y37xvu83945GaR39o/UZO8kGXcmG8MECXirW9+L8=",-2135856018950673156,-9128042613715924322,-623665133350549130,-8017578359268677046>()) {
                           case 565688536:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return var2.toString();
            default:
               throw null;
         }
      }
   }

   private static Registry<Enchantment> a() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0 != null) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s3fbm0y4qmoz6p","uCdZwtn2GfZA8j+TiH60MuRpBAAdnziWxIt/6Xrpw4I=",505455073250246477,-4959724828929834991,-6489064117207259645,-6331092036932482470>()) {
            case -1405527067:
               if (var0.level != null) {
                  Registry var10000 = var0.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
                  switch ((int)com.yiyiaddon.m.b.a<"sutadeq07s77n","tNb3IRSAsbE2km2IopSC1FTCy9jqSncSBAI0tQfisjY=",5745810572124843287,5248661266569079390,7028024655901870655,184962637592537862>()) {
                     case 783846953:
                        return var10000;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s1qur892rvbe93","+YTdtoCyU67A2h4K7CvzJVhahHw4Pj+aGDOOg9vg2vM=",-64273370698715063,6949981265378013698,9031982871770065845,88850368100120919>()) {
                  case -1612469424:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s12veokqxtmgkf","NykT7N4rBWZWLGDy644/4SAgJoIIFNoUWC1OevTdyhM=",-2516851335529421677,-8182063884436269278,-1792748230330157474,-6997918958098666492>()) {
         case 1620971824:
            return null;
         default:
            throw null;
      }
   }

   private static String ak(String var0) {
      Enchantment var1 = a(var0);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1p8gftpwncupx","i4phZwuYDBzFY/vgqDLsgPvwBx64qlnSWCf4DhdX1tc=",2326094939836988585,-2482962377529740911,-2786874258752962335,-1567394796835052453>()) {
            case -696026012:
               switch ((int)com.yiyiaddon.m.b.a<"skwpjxxtylu4v","3jEV/ydOCiVY1L7lL38NrYO+KplUeFsiNHyDwUXB/kg=",4563614480747093953,6847774017735287272,808710177632004532,-6593046405859092261>()) {
                  case -1797770735:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var10000 = var1.description().getString();
         switch ((int)com.yiyiaddon.m.b.a<"shwgnvj1oaoa9","kYS3bmmL6MxRWNeuoPk2w/gDVRHq7yg3izRfLH0kY6c=",-749806039979825103,-5391157248841562164,7697413124892389639,515138174844101655>()) {
            case -1658356042:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private static int f(String var0) {
      Enchantment var1 = a(var0);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s11gzg6cjgfhja","yM7wL0rGkzxdcMiNSX5LawLjJX1CtHvtKwyEN2IM6Sc=",-1226874195439360662,-1413361653810500464,-7578375055628808892,-6511653976021892349>()) {
            case -1864975456:
               switch ((int)com.yiyiaddon.m.b.a<"s30z29hm6o51ib","ALKcWGundQh3r9i+6d7tlGhZCxzkDY2Vb+7pED9bHo8=",-9163377673997341552,5881890023922514059,9218915141563763011,-7645884437758157231>()) {
                  case 180675723:
                     return 0;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         int var10000 = var1.getMaxLevel();
         switch ((int)com.yiyiaddon.m.b.a<"s1ehtp3422ik2j","XRHAf6M6p83XdSbLU0B5bL3Di2p/D+GXDcHR0IiOw6o=",6425512069406596920,1994573429502568924,1131961664360624030,-2748572359574943284>()) {
            case 120801761:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private static Enchantment a(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s16kydjyh6kq9s","ifHrjd1uM9xR2HD/iCFKQ1JdSVBGxWVHF0WSfBhBfhU=",3880685534734598221,7894186697425382077,238871388119293192,5485420722568349527>()) {
            case 33780513:
               if (!var0.isBlank()) {
                  Registry var1 = a();
                  if (var1 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"sohb7apfe6yg7","yYQ6hP0huB37NP0FKoMEdtkse9E903laVUwX6xwKalY=",-1552230855882382596,-2028770796119110962,-277375261964375507,1695317321611563378>()) {
                        case 476219978:
                           return null;
                        default:
                           throw null;
                     }
                  } else {
                     Identifier var2 = Identifier.tryParse(var0);
                     if (var2 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s21di21uacgwqo","xZT4PMymhBPfZ1PECy9HUF2h9n2fXEJqiHiGG0ZTcRs=",8247480811340884988,-2885100368084065929,3893294784592151920,6528766055398209695>()) {
                           case -1581746934:
                              return null;
                           default:
                              throw null;
                        }
                     }

                     return var1.get(ResourceKey.create(Registries.ENCHANTMENT, var2)).map(var0x -> var0x.value()).orElse(null);
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s14sm4jga29ttl","fhxzPkNX2U6Qvn4QD47dXvbVJeN/u7ajvGoCS9QEQoc=",-3617704466651047709,-5397796754980448571,-5241417773289162635,-4370480739613798278>()) {
                     case 1829109224:
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
}
