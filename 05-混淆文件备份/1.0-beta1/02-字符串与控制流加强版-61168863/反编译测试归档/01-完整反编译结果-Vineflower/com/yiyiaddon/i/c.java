package com.yiyiaddon.i;

import java.util.List;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.multiplayer.ServerData;

public final class c {
   private static volatile Supplier<List<String>> f = List::of;

   private c() {
   }

   public static void a(Supplier<List<String>> var0) {
      Supplier var10000;
      if (var0 == null) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"s2ruoi952ezszv","5V55H3oXEuz7b0CM/T303sEk6a19LAlElzSHTlalMdM=",626462719574243266,2607968007484451320,2935232596189050023,8243057554835616566>()) {
            case 19124096:
               var10000 = List::of;
               switch ((int)com.yiyiaddon.m.b.a<"sfmj7yiafmalp","HMJAIh/AIZca3JSBIOFvm+bb+0gNDy9NP200vIwz76Q=",-7943768782478289556,1114470964052929063,7395994894067552813,-3873835337583443328>()) {
                  case 1446224823:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var0;
         switch ((int)com.yiyiaddon.m.b.a<"s3okfx8ybmee2v","OZJnBRUXTnAq7Gx+uIeyiREzFw6Adi4ZiRp9hWGniNM=",-3839691138431989755,8130611191464912600,-1810336353732778443,-2019496264120838196>()) {
            case -241980209:
               break;
            default:
               throw null;
         }
      }

      f = var10000;
   }

   public static List<String> bB() {
      try {
         List var0 = f.get();
         return var0 == null ? List.of() : var0;
      } catch (Exception var1) {
         return List.of();
      }
   }

   public static String gi() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s31zadfx1avhf8","rF6ugjaAEZ8uQ/VReDJn+p06Kyrc3Xz8ZnuI58oOlcc=",2579581003515172125,-7747147693817766481,-2539339325802905025,7782803847941759454>()) {
            case 1672865152:
               return (String)com.yiyiaddon.m.b.a<"s22a5ck6qc1jwh","rOcQEkiWucvdX1+dp/2pT2Uzxg3jcTgt5E3bAH7r6s3+rEa8",2987783143788329324,-248812395168468825,2499710868494975015,-2334322150094295772>();
            default:
               throw null;
         }
      } else if (fp()) {
         switch ((int)com.yiyiaddon.m.b.a<"s390obxm75uxon","9LvXKuKv8MMG63tnmlCJYm1+LDnDWPmRJomxbgu5Ub0=",-7863382950120684660,-3224505294175882690,-4886241697938362635,6746008376512142878>()) {
            case -1622062230:
               String var10000 = (String)com.yiyiaddon.m.b.a<"s14ifzzmun1abn","UDBtLscOQtL17avPiAJgLVsEdPlQkV2O51L7TyTtMGpmuwlLunRdRNYAlkYDeA7wKkU=",-2738392944492520487,-7770663389342275350,1724289133500428510,7829476151323583145>();
               switch ((int)com.yiyiaddon.m.b.a<"srun6hasejrm","ns/z+GvCH7jQCwbNwkChpHAMYaOKI1XN/7fb0YrFy6M=",-8086999758029781971,-7259844913338225494,-1568889095134176423,5778141079674093217>()) {
                  case 948432337:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var1 = (String)com.yiyiaddon.m.b.a<"ss9659xl96qnv","0my2oTFNHw9benlqCHSfYTj1Z1Fb95LcZthKISk+M2GVJKdXEjc+LW9OYRlW+syK7aqjzg==",-4142504738578023467,-5253356743156927192,1875391184321745888,-3282251156027777007>();
         switch ((int)com.yiyiaddon.m.b.a<"s2jqponb9wu55e","ZGbb3AUo/TxjA4cqLvNC+zx/7Z+Rq5eK1OEjxL0Cqzc=",-3038730080746157056,-9111546385473371617,3356138500959901180,-8710411347159239429>()) {
            case 813619218:
               return var1;
            default:
               throw null;
         }
      }
   }

   public static boolean fo() {
      if (Minecraft.getInstance().player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ps6vdgs6hxkz","5P8eWK0ezqTEG6oUvCmjBpTEBa/TPXWFvBXHyKZ5KTg=",3964821499154698153,-5508554236293296804,2523503084193467943,-4523332991590660298>()) {
            case -175503191:
               switch ((int)com.yiyiaddon.m.b.a<"s1jwaso5c60gli","EFg2dh6/AatT0aGvZAQWGb+iWTjqGq+6W61XZltZ6s4=",-5808140906462279890,-1073319677781834661,-5496517500478857801,9149239715897392086>()) {
                  case 1069899432:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s32hrcfrx6ht3h","iFv6Q3l7xcPPl9mkfm3NE2oZOi2VP3wMnpuT3jDHIbQ=",8435738877448233914,-8079945970942565998,1228370904423897194,3270008968687543427>()) {
            case 339393521:
               return false;
            default:
               throw null;
         }
      }
   }

   public static boolean fp() {
      label37: {
         Minecraft var0 = Minecraft.getInstance();
         if (var0.level != null) {
            label27:
            switch ((int)com.yiyiaddon.m.b.a<"s3id2i9vix6t9d","UFVk5mb/xqDsKzk9V9qJbntFPW+kNX1P/HNCcfAUfcI=",-8421448923902711746,-1899433681731014105,-2708097937563964025,-8377437050020312083>()) {
               case 150232507:
                  if (var0.getCurrentServer() != null) {
                     break label37;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s3na9y8d8lgj8g","ML6JdAcAhCDpusXlTfxw7HpFVnNohQWs+cAlOYR85fg=",-8073111211219316914,-343895458029224215,-3440991237925696103,748432316759115308>()) {
                     case 1988149759:
                        if (!var0.isLocalServer()) {
                           switch ((int)com.yiyiaddon.m.b.a<"sxqqu4qgokvi4","ocYKrrN5yp1LgJFx9G+9NCJhZ9NYl9/2Mrj5MGCiECk=",1225378337320811260,2378528915798953640,-5909619610072353981,6280444514153895613>()) {
                              case 1683653945:
                                 break label37;
                              default:
                                 throw null;
                           }
                        }
                        break label27;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"s3ty3m1lhsc3p5","/dRPp8tYmPcanOEz0bhDjAA0VmUyQJ+7IejEismP/BE=",1750299277151959591,-1067863925114847476,-8286978599355533444,4848660330592689192>()) {
            case 457107441:
               return false;
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s1tpaxm2oqf5s","XEmMm10CePGq5YRvsf7UQOOWeWrdoxCcyGPCsGjTe+M=",-6142282918557172553,-6772187551928282928,-2449803349324548336,-4706380369947685872>()) {
         case -1369120315:
            return true;
         default:
            throw null;
      }
   }

   public static boolean cH() {
      return Minecraft.getInstance().hasSingleplayerServer();
   }

   public static String fD() {
      Minecraft var0 = Minecraft.getInstance();
      ServerData var1 = var0.getCurrentServer();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s10cs79yl9irs7","00ncsj9FglUO68A3KTbNgE+MzG1AjiAhhRlFUbbLGu0=",79578621984519121,6394718214163804039,8296442419868748618,9188834044228890753>()) {
            case 1261688510:
               if (var1.ip != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1wv4ev22xerhf","MPy7JvOPlO+x2E14Zeit4LBQVMv/5SpJM/Vl5POiEss=",-7183952075139412618,-7559738617818842497,-317074809545145388,-7750979365089786557>()) {
                     case 9545048:
                        if (!var1.ip.isBlank()) {
                           switch ((int)com.yiyiaddon.m.b.a<"som7to4nnjgqx","gB30w8cBR5RM2cY9cLsAnpXrZITUS/NCwxv0t6GSsq4=",8151269557904205047,-1023804237570181977,2611600939705677257,-4448610695082559312>()) {
                              case -1502893619:
                                 return var1.ip;
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

      if (var0.isLocalServer()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2d9aa1n0nzb96","yRsVuzB+nj5BGhlyWIItYIBCxxo3/cHNvgWuZOt7ZPo=",-1981692367294387112,739433177989551632,-5497359388226184128,4048945183495077996>()) {
            case -1107085134:
               String var10000 = (String)com.yiyiaddon.m.b.a<"s1sqxyjdz5d9rw","ily1zcS45SqJqZ0VGz/5F2ePqm4iTrdOLen6QOIZe62X8vi/BfVWb3J7FFmkEg==",5438971131038086533,4659133203599658097,3107919014834205505,-5467959354850683670>();
               switch ((int)com.yiyiaddon.m.b.a<"s3h8iz3dz8yd5b","OZwGqZvjhtlA+eGqJqiNY06NALMCh0lVMvhN5D4PPrU=",4210352882242053582,-9122532700337710628,4690364785747999715,11972151373261190>()) {
                  case 590619033:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s18lfaqthgzvr","VUq0DxGVlUUjzWtP+goNzXrvtLAMbnDuCjb7Sbnh9gg=",-4476892243687978615,4503018119994773905,2571069374298692189,7378568042780694158>()) {
            case 305903109:
               return null;
            default:
               throw null;
         }
      }
   }

   public static String T() {
      Minecraft var0 = Minecraft.getInstance();
      ServerData var1 = var0.getCurrentServer();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sne6o55jxsszi","/qOFoSgGVmSOpdRJYsGRi+VGeieuVj8UwtaNfSbaf3A=",-5636425448003141286,1275513527691075490,-7112467177781181842,-8858211636644194644>()) {
            case -1455715421:
               if (var1.name != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s35totrcc4f5gr","1NXqd0Q1QWZm2d47/+/GDb8o+mEwTnv2iLibs8Sy0aA=",4591382269348970251,-8757105221378604086,718863847685569367,4639326632682948670>()) {
                     case 2050966861:
                        if (!var1.name.isBlank()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3bc47yjvpovwn","3m1nhWvflajHyFZ6nQvASyonaU6V9ILPOAkZfYtzGfg=",834164520859391401,-4956857977183175115,-701640069905962697,-2502831799676922579>()) {
                              case -749619126:
                                 return var1.name;
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

      if (var0.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ju7v3bser5df","PMYvR5iBpKRDifmIqUAyLtIdh1m0ke14XjvvsDPHwhw=",-3583324530718487105,209351770744002860,5801064865286132704,6403692872030002284>()) {
            case 1281981086:
               String var10000 = (String)com.yiyiaddon.m.b.a<"sygda6b00tm0h","nbECArZA2WwDmeNwQDbCRnukzOH01CmxD8FHxSdJq7ehvA==",-1631400994330410660,5429008618472254757,4372302439046516330,-8578406764686270946>();
               switch ((int)com.yiyiaddon.m.b.a<"s353h3dk2jm57o","Kz/152l6pul4A4u7wgoBcuhl870/D1UH7gjaBvsaVl0=",-1437000498169756065,-5422762650556440259,1468359292681795315,-653143046376663551>()) {
                  case 1077811980:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var2 = (String)com.yiyiaddon.m.b.a<"s277zsw0l8cwsz","XFbFM/qe/RKjxC1TEP08sGllbnrYg6RUGWlFUZGBT4thM8IU",6682739383967949547,7189468063343975518,7788475963880367670,-8176446321516694667>();
         switch ((int)com.yiyiaddon.m.b.a<"s1hakxshptcfb","Lnqoq3MLH+80ENLbnT4xOOx3csGrBDvJRIqXLVaGGGo=",-1798485111640459481,-4566219108380576609,-3377421000944066269,-4083972996224610190>()) {
            case 580950324:
               return var2;
            default:
               throw null;
         }
      }
   }

   public static Integer N() {
      Minecraft var0 = Minecraft.getInstance();
      ClientPacketListener var1 = var0.getConnection();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1lxvx9a1evc7w","f2GtNSOOzf/feErqKqz+tQ2ubwtkKvY7n7D/uUOkoAg=",1750255457088096800,1779666331257294903,-6051792493442194602,-2945741530271805721>()) {
            case -787568399:
               if (var0.player != null) {
                  PlayerInfo var2 = var1.getPlayerInfo(var0.player.getUUID());
                  if (var2 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"sptzx84qqf2bl","6WsZJgfPq1dxI9wMfUUi7a6aH63hW+3xDrRmdUNcjfw=",-5333582796682525480,5225760406647287653,-1528909972548668565,4325273455802664123>()) {
                        case 208342735:
                           switch ((int)com.yiyiaddon.m.b.a<"s34e2mt2kicfa6","mZMWOXrT1iWp3+uoi9XuHzVH7fVwdIjyaRSlDSKNDd0=",-8274281818258004597,3412296281832830758,-9217857848055082996,-8197638736844047558>()) {
                              case -682213395:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     Integer var10000 = var2.getLatency();
                     switch ((int)com.yiyiaddon.m.b.a<"s29466ypuz42uj","lMWvBGFDotzhoCgAmRb35urs+HWlINF6v9oPHhGJXQE=",5608284692276003100,-6266608409770130546,6491601846845155467,4931081067230116645>()) {
                        case -217172980:
                           return var10000;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s12g067vq3c871","4bfXFMjxzwWeQDAgTve/eZ1HQCQJzSgW+VAN9afsvok=",-3436980959210645361,7971909983132587046,3541444205368981935,8562124371702954853>()) {
                     case -809509292:
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
