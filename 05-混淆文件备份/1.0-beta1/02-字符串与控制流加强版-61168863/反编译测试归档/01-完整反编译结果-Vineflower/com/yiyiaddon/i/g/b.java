package com.yiyiaddon.i.g;

import com.yiyiaddon.d.d;
import com.yiyiaddon.k.e.e;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.server.IntegratedServer;

public final class b {
   private b() {
   }

   public static b.a a() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3hbb5mz0xjnxq","1VwY1PJ7LIsjw+3t0iSDld4uTpl0JLJiQXxVje++/YU=",-6534072320144750817,-6245714797676046793,-2250277920047978420,-704042370853083189>()) {
            case -1328118470:
               return b.a.MAIN_MENU;
            default:
               throw null;
         }
      } else if (var0.hasSingleplayerServer()) {
         switch ((int)com.yiyiaddon.m.b.a<"s136hyahyip7c3","k/ybr7i3zbaXnG95WKiL9q7GlVrio0iurc/JUshMTNU=",-3931678272687634296,1197500185556361816,3399951398203426648,9113412141582439381>()) {
            case -1865614183:
               b.a var10000 = b.a.SINGLEPLAYER;
               switch ((int)com.yiyiaddon.m.b.a<"s31dxv2byagjo2","OXokNktxBwigZLZiU6tzvuELAAlSviS4rMxkbZGtlmU=",-6620063789631387011,2867844688699977787,4760607683293212428,-1268426601081208786>()) {
                  case -803562525:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         b.a var1 = b.a.MULTIPLAYER;
         switch ((int)com.yiyiaddon.m.b.a<"s3hp27j0gr6fq6","tSbePiywEiPCz7Ynkt9izDj/4eQj84w1O6jIpILnzX0=",-3958845000600782041,-6454173553163152002,4029186343844153802,8268572924652520156>()) {
            case 506862563:
               return var1;
            default:
               throw null;
         }
      }
   }

   public static String T() {
      if (a() == b.a.MAIN_MENU) {
         switch ((int)com.yiyiaddon.m.b.a<"s2mge7yftitphb","ndKscm3VeWhB9TaLkqk32WcJ1ltrXEg8kIk4lGnn/Gg=",7225153309180382716,-3306892098159531169,966582433074483557,-5470860147314684715>()) {
            case -1930215317:
               return (String)com.yiyiaddon.m.b.a<"s9jjjv8d3n4a2","RRYZzD5sjOfHEykRdFQ14nQAc0j57y255Wc4k6ZVSCEezw==",-2656314760224727957,-5341928294022421643,-7937408317216695106,6281731522340061609>();
            default:
               throw null;
         }
      } else if (a() == b.a.SINGLEPLAYER) {
         switch ((int)com.yiyiaddon.m.b.a<"s1pgmxp47rlgmo","kYVXnyAZaZGTD7fRtxwxV2tuACimPGDW6LmzV/xiGRs=",-2456560951347671663,446489277016852875,170434786711152423,5700237744427127876>()) {
            case -1925463208:
               return (String)com.yiyiaddon.m.b.a<"s3l2ni8gjxh4tm","D9/u8IUyMhomp+nOy3fPmOrKEl1wRGGVnX29ST+tAyanPL/p",-1503323983172054126,7155816276615828442,6493886612682104646,-3095021292548924800>();
            default:
               throw null;
         }
      } else {
         ServerData var0 = Minecraft.getInstance().getCurrentServer();
         if (var0 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2vjvvfz87hy92","+CgDGUmt+HBVhtDTn7ZV+HsYo7lEoi7z41x9HortdmE=",1417380553556701470,-9171005872288071020,1849893996782310565,1511022881241052244>()) {
               case 339480275:
                  if (var0.name != null) {
                     label35:
                     switch ((int)com.yiyiaddon.m.b.a<"s1lgjs7cozg06v","zZimhpc+cHYzg+JlWZPRatmCTa1zu1E3VGoWh/IEIA8=",1924119816271548667,-5442906592136978205,5173200404515330941,-5086029993610895816>()) {
                        case -84397443:
                           if (!var0.name.isBlank()) {
                              String var1 = var0.name;
                              switch ((int)com.yiyiaddon.m.b.a<"s2kfzx7clqbav2","T5qtgARNzshWg7vX1vihtUxewdm3GSl9Lp773J76gMA=",6168150626113351395,-1881874445980365633,-1213058115563684099,1488639840460373152>()) {
                                 case -1497593669:
                                    return var1;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s1ng5fitqp6cj3","w1zzjDjAXQXrbeVi1qJcN31jN+0TECMvNLwfHY5O2vk=",481273387403156483,-7514149374923433044,-8526367284154844344,1624905861156438693>()) {
                              case -1447812960:
                                 break label35;
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

         String var10000 = (String)com.yiyiaddon.m.b.a<"s3ewjgzvgs87ce","1Wq90Dz/xGXg9+td+BVieUgdhwi0fJ+34kqkatpl3Z6FUwVKIOXKUQ==",-6655174732163333763,4048417481759512463,2498065239806602655,1494870516273173781>();
         switch ((int)com.yiyiaddon.m.b.a<"s2k9yd068992wb","8vyTpkm+eJ5La5S3ovBgFpyz3LrM3E7lNDHmraCgzEE=",914538560277004750,6273829195876598275,-283935923829185564,-2019809246624439408>()) {
            case -1810782290:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public static String U() {
      if (a() == b.a.MAIN_MENU) {
         switch ((int)com.yiyiaddon.m.b.a<"s381379eospq4y","B5gC+IUFpon3E4ji8ZJIEE4YBQN5jEMayM+iPWGiW0k=",6530993989352940658,8575284493881874166,-3880062192017996972,-2239083251453722679>()) {
            case 300870367:
               return (String)com.yiyiaddon.m.b.a<"s9jjjv8d3n4a2","RRYZzD5sjOfHEykRdFQ14nQAc0j57y255Wc4k6ZVSCEezw==",-2656314760224727957,-5341928294022421643,-7937408317216695106,6281731522340061609>();
            default:
               throw null;
         }
      } else if (a() == b.a.SINGLEPLAYER) {
         switch ((int)com.yiyiaddon.m.b.a<"s2no2gzizlcgq0","fYjVKA6dlW8NC1KxofO3N/+MUAju5zIlDwzzVclszDw=",1914878298259598416,7643865135115147768,-5888761949491780891,9041379778498097355>()) {
            case 437709601:
               return (String)com.yiyiaddon.m.b.a<"s2g6b2f891z6tq","61Lp0fYKAgF4CGoMpiADTvuuG7HjRhO5f1OrkjVEJ4NXmQ==",5652253432421641037,7733257694863170005,-156491679346884493,-2441676002049847834>();
            default:
               throw null;
         }
      } else {
         String var0 = e.gA();
         if (var0 != null) {
            label32:
            switch ((int)com.yiyiaddon.m.b.a<"s3ngg3ergtqedw","BF7WfobHvEPBEBXBdWX9flZ3Hz5TZn7I4mqsiP8UBP8=",5002752041972489966,-8419986196070634289,6966054314451282288,5342365850493764126>()) {
               case 2034199023:
                  if (!var0.isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1llblr90nok1","aIbEKgNUmBaifEzJtHrNXNy7Lb2cQ7pAje1/rHqPgo8=",814288842047562866,-1457599722808476378,327964502642360515,3418669860084022038>()) {
                        case -1880670009:
                           return var0;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"ssx3v7b959ur4","72Uk8E/7BwQGs7D7hEbHFo9vdH6W2390BO28NuIVLU4=",1942983579562299702,-9073700251294071736,-3500166069798574266,7914122193692149605>()) {
                     case -85636574:
                        break label32;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         String var10000 = (String)com.yiyiaddon.m.b.a<"s2yzc33sjcn8xs","TyD4IkCFWx/JTUYNq/hpIZSQUVwt6xhTyG1sNb58cTCCGbBB",-6681963068665828114,-2280769606551918133,127948094367703169,-8470934909940985006>();
         switch ((int)com.yiyiaddon.m.b.a<"s457n9ks3rp55","QEO4mpd4/ttbMD0WeFbTwzigm/dasQQK76gR8UccDW4=",-5390286767703753238,2667723952803207255,5272543515545449892,-709504769371238330>()) {
            case -170566896:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public static String V() {
      IntegratedServer var0 = Minecraft.getInstance().getSingleplayerServer();
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3dtk6a4t2cina","bsC3cbLTHaBKHSFlt69+4A2b5NCvfGHYT9KvGNRtWZA=",-787021427105861753,7990025368571447754,-701204043069203371,-4632079544818945868>()) {
            case -153635714:
               return (String)com.yiyiaddon.m.b.a<"sfyxq78cocw12","Xj7mno5lBSNwRrU0HKKqo1hYclI2V8eRhaZGTXxePqYNOuEPfnk=",8437927715877024437,8130014986667624285,4956143041931839647,5244931016751860477>();
            default:
               throw null;
         }
      } else {
         String var1 = var0.getWorldData().getLevelName();
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"smmafae3dpdd5","wNvwKSz/fCtKNUSpeI0XHNr5VByoCvVa+zIEb7ZrAsw=",4125825469623124036,7638676240161717252,-3172792993419670620,-8328858094747703786>()) {
               case -1475384522:
                  if (!var1.isBlank()) {
                     label30:
                     switch ((int)com.yiyiaddon.m.b.a<"s2y7ojodzwy1g9","Z6pdWZEzZG+rC+U9sWrDr/glUdQ7W6htT7k/r+3ZoCk=",3681644639129733641,9028976380117915652,-8912806720261064654,-8836084217146371621>()) {
                        case -1590110685:
                           if (!var1.startsWith(
                              (String)com.yiyiaddon.m.b.a<"s2c11lbzzvko8a","inCNMhFjEmmAKlHtRSoH9rApXugFWFQ2gHtYNM1LIFjcUM/lPCh/b01h",-4744025794576380992,-1027410372703129998,-4647454835343495491,-2506235975457476955>()
                           )) {
                              switch ((int)com.yiyiaddon.m.b.a<"s7ef3rvr0yqay","HKgaJzMil4SAwa51PHNoO+itBBxQUNMPQtRlE65mf0o=",6220501001596813910,-6805568230167615512,5878169526239347088,9205849492429074799>()) {
                                 case 182046074:
                                    return var1;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s2iv5du9zka1ze","MkSHfwTIkNZzfwtaVB4w67DAAWNn6n0mqHkG2Lm4IDY=",-2233821875096749690,-175759842254181725,-6688631864976179529,-7894968608085874326>()) {
                              case -1463210695:
                                 break label30;
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

         String var10000 = (String)com.yiyiaddon.m.b.a<"s2ykdce6tzh1bh","3ChPMvDLgyMk6egCUzLazclwC5E9PtSsVTfpGijjg8XwffrhAsfA9aBSCgo=",1547482735602431093,-2026182988116571104,5275156844375854274,4696018026971359819>();
         switch ((int)com.yiyiaddon.m.b.a<"s1qnttzb8vj5tn","ytY8l24Ckxlin7IbLlX2fFD9HZ3z08zhGPX1hs3BBTM=",1075556063079953270,1305316303407971731,-6426757385741430102,-190489881212634133>()) {
            case 1994611190:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public static String ec() {
      ClientLevel var0 = Minecraft.getInstance().level;
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3kscy6rh1lh89","kh8p3fYkt3IUgLPCz3fvV1sKInKiJG1SURl4tvfU9MA=",713701590955473606,-8287852411257234442,1419397993852921693,-7003535937576287479>()) {
            case -120766864:
               switch ((int)com.yiyiaddon.m.b.a<"s191g3r20ty441","KwubfG//YzbCZWmauREHQ0usOoZIbpxJaV7u3FHmfBk=",-3858942726601556517,6318525776131329219,1617530131382156184,-1319530088126602398>()) {
                  case -1786341168:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var10000 = var0.dimension().identifier().toString();
         switch ((int)com.yiyiaddon.m.b.a<"s11cugv5fsc2iw","mSdKdslTGqU819ZrtmlV3Y+1CCOmQdPURR0mjo2r8Bk=",-8497993235748476392,1126550226210788299,1734469895810122385,-8988271656475374857>()) {
            case -1541851852:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public static String gm() {
      return bU(ec());
   }

   public static String bU(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3hzqjotf0yq41","zuCbC9V31YlJLcQNPwnIngl4ncNkgGTQPFBIBjQwYc8=",90918773513591308,-459744991787010705,2629500367912508434,4419618367913306219>()) {
            case -1696137341:
               if (!var0.isBlank()) {
                  String var1 = bs(var0);
                  String var2 = var1;
                  byte var3 = -1;
                  switch (var2.hashCode()) {
                     case -1526768685:
                        if (var2.equals(
                           (String)com.yiyiaddon.m.b.a<"s1c6eb59wabh14","0DCDGXnpY4gWfiyIoTeoT8H9eieSX6uvStW+8j/gU2h2hZFApRQiFL4eURumWOpm5ZIZcExY1LGpLI2KBFGvI3JzK7g=",354308294543371403,3057699023209893100,9050278122233933562,9157757802828520608>()
                        )) {
                           label74:
                           switch ((int)com.yiyiaddon.m.b.a<"s3ajoq14xboel4","L/PTR8XmfeUNmrPCZX+FyAWNVQMUKXFZvHNmP3ntBSE=",-4409124970889692387,-6750963993168978599,-7759545965239166772,-8132427098545727222>()) {
                              case 468246778:
                                 var3 = 1;
                                 switch ((int)com.yiyiaddon.m.b.a<"s2ua0rjanmvtgv","L7Q2mHJLZkPutNdy4WcAb3eV7+SllvyUcNvklXOPD8Q=",-758837901545157228,7544416470466341579,-8963491952434208488,-5034120053517704945>()) {
                                    case 702564593:
                                       break label74;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 1104210353:
                        if (var2.equals(
                           (String)com.yiyiaddon.m.b.a<"s1luyzwzf7631u","N2O+beh3/WVL+qYhJcBwsv5gTFEn1BXBuav4taFymupwh8n50foNzV/oxeMXUW8f7r32c9rXv+g3BnC1bWPv7uai",-4733052153045895156,1423136710747358841,7802254019206973014,6973314326900789994>()
                        )) {
                           label77:
                           switch ((int)com.yiyiaddon.m.b.a<"s3ke5hfbcb21xd","y/IlU5c6wfhj3nOzvhLz+r0pU9SycMOdA4AnTxXUzKE=",4337719369594744345,2238014357555298583,376076957342185012,7315049717094786348>()) {
                              case 814989347:
                                 var3 = 0;
                                 switch ((int)com.yiyiaddon.m.b.a<"swlxsnsz102rd","I6jhRCZuhR5zuR7VRbfnYXRvEti+wzeJhS6gTcUNwas=",688343821909226441,-1454838993546756900,-8540506820023950185,3361723025728050181>()) {
                                    case -1357957229:
                                       break label77;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break;
                     case 1731133248:
                        if (var2.equals(
                           (String)com.yiyiaddon.m.b.a<"s2ysnnl1o6636p","ElfT2ExjRaisuejvUNfA8kBFLGLFAGyofn14hZQ16IrAMcSnGzAGLh0nY98pmOFaOpWfcV97ZbYtY+ONP8g=",-7977589192181514088,-1773704065909433753,-4376025860367217385,5884798802068944369>()
                        )) {
                           label70:
                           switch ((int)com.yiyiaddon.m.b.a<"s26rnzbf1n89ky","saEFTYD6LfcVSV4nLUz5q4y+58r4Y2NpaLj44iZE07U=",-7207276116950070899,8590700601012910222,-6569420497688834249,-5050077203972544267>()) {
                              case -683190860:
                                 var3 = 2;
                                 switch ((int)com.yiyiaddon.m.b.a<"syshzrcor0odu","Sb72wOG24gJSr4nlUE3qfKW+gpK6VtuVf5aqblKdw9U=",-8324878098729711266,8633392266809491249,3035111857574883702,5415986905302907036>()) {
                                    case -230759790:
                                       break label70;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                  }

                  switch (var3) {
                     case 0:
                        return (String)com.yiyiaddon.m.b.a<"s15ji2gu2cvfg4","bSp/HTuT4WwsPTsgpNu7mNIBvGxWFDEQ91BKSgPdekegYw==",-3366345935705951934,3410842583321199450,-3484875337852190953,-5705703930972923513>();
                     case 1:
                        return (String)com.yiyiaddon.m.b.a<"s2v7n83yi886s4","+VcpqqUnOZAHKgG3BXd2VZYDZdphPbDwgDf0jHqBFNI=",2473632660093726432,-6719922034951650073,-7376478918625343205,1729124435222934755>();
                     case 2:
                        return (String)com.yiyiaddon.m.b.a<"samq4qizmv0qy","YbWKYF24KtEAYqQ8bSjHcX/zatIBcOFm+1zBf08HyyQ=",2568170100848275004,-1072303141155954042,-2873122176800830317,1583587739078300898>();
                     default:
                        var2 = var1.replace(':', '.').replace('/', '.');
                        String[] var9 = new String[]{
                           (String)com.yiyiaddon.m.b.a<"s3qifkuau9u59e","nFbQkPmLrFD5TJptsdBiOtE33nxIRBtb28JHh91X9IvHhaTqgqmLOGu4uRyfE3OK",-2363222319462977955,3527156958180142090,-4304235204583606751,-3853429491301254357>(),
                           (String)com.yiyiaddon.m.b.a<"sbhqq4mc0l5to","qRneSq2GfE/EKjBlhK2XaoT7ZaLH1EI4rwPYbkwHWOyJqWKRGgQD3HaZFr+anHVE1Bw3gqxGCK4mfA==",8804626559879752325,-8170186552206544157,-2931768970470189988,7368844983258153382>()
                        };
                        int var4 = var9.length;
                        int var5 = 0;
                        switch ((int)com.yiyiaddon.m.b.a<"s1vnh18ykyvm0x","QFojIzcMOW/06iF9eUnsWkndwOE0yoM3FYGC9nUef7A=",-935841518288924076,1956300162025820066,1203943776774770910,-5047021404839521420>()) {
                           case -1290384794:
                              while (var5 < var4) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1cbwltra6zxho","VieVKG6Cuzrb7CylSjJ3G1kVL1pIHDudWawv7Q/hAyQ=",1876239866699770977,-2064738656259294162,5748871002451369155,4446950310685906038>()) {
                                    case -1856340835:
                                       String var6 = var9[var5];
                                       if (!I18n.exists(var6 + var2)) {
                                          label56:
                                          switch ((int)com.yiyiaddon.m.b.a<"s2m4jnhashmhj1","0AbAz07ckiBbbGdQOB3t6DJhlc6Rq8AI1Zha0XwROAo=",-2624083894565083137,-123536805175790648,8074729854971668396,896362909143484676>()) {
                                             case -373141188:
                                                switch ((int)com.yiyiaddon.m.b.a<"s2plhqoux0uq0c","LRnDZNylE2boohk6Gj39Eayu+rcx4WhIk8/kGSiFaug=",-8553904262361664321,-2204592470092121757,-8600356013564020361,7168089672922572626>()) {
                                                   case -509896726:
                                                      break label56;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          String var7 = I18n.get(var6 + var2);
                                          if (var7.codePoints()
                                             .anyMatch(
                                                var0x -> {
                                                   if (var0x >= 13312) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2ze59vf2gsqcm","m5KsWK3DJGJrhu+M81Caq60tJs/+yAV0A+2vIHTKKOg=",5129552918456786093,-8390710502159755671,-1238363106788239248,-8241612287573380451>()) {
                                                         case 862401773:
                                                            if (var0x <= 40959) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"s11h8dyz2b25rx","jc5UwT0jpwgD/q1Gw622EHCt8l1M5Bc2W3HMUiFF0jQ=",-2663939554968487957,-3539407056785152751,-3494943761361065417,-6224120022362265577>()) {
                                                                  case -427829704:
                                                                     switch ((int)com.yiyiaddon.m.b.a<"se318syos4954","4QAvPSXY48Vgr+VOqtlLrtAP0+Zn3v9cKGqQA1Wpzl0=",-1555733479787283988,-4200349638324893196,-2569968864182552421,8798010154534460806>()) {
                                                                        case 335153998:
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

                                                   switch ((int)com.yiyiaddon.m.b.a<"sqapvqewrwpzi","W29WfCBzFNrwGIXpWWtP9UCpnyDh9/iAfxZmhbU6uZU=",-3742690654292894584,2539529624419063430,-2899550578109656513,7874711172838204065>()) {
                                                      case -1805848006:
                                                         return false;
                                                      default:
                                                         throw null;
                                                   }
                                                }
                                             )) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s6kkrjfp9liba","xlmowiIrLuIqyyatD6muBsebxtYwHSGDeYedoSnmPU4=",-5315442328621521848,-3462281059987012560,-3075482126773796168,2996071697398965123>()) {
                                                case 2013002271:
                                                   return var7;
                                                default:
                                                   throw null;
                                             }
                                          }
                                       }

                                       var5++;
                                       switch ((int)com.yiyiaddon.m.b.a<"s15yqil20limlv","ojiiERUfgvnuhaxvLAkcJBMvgBhbytxzsau6P5spNQo=",-2577624699158957760,9130665852699271362,2986474321975048440,6270208811610514061>()) {
                                          case -2118226902:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              return (String)com.yiyiaddon.m.b.a<"s37jzcuulz55eh","DLjOuyO9ScBNGUw6xH5e2y/nShJdAiGZ+lk5flRwx77sUphSKgk=",-5961963481342313801,6443013487547828269,-8219207897019363331,6141785959082798463>();
                           default:
                              throw null;
                        }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s25fdoei7seczy","ClooXiACG41UPolRQA9byJYK5AthqpILGdMJpmlyYVY=",7162468051765195913,6245426732182662601,-6924128460496215776,5292610751466618361>()) {
                     case -1432098997:
                        return (String)com.yiyiaddon.m.b.a<"s3tfsxurbwlwxk","tT+f6PYy0Q+1V0ZX/6jxJz8NXh3lH8FO287uTYWy",79466163385865906,-2863548980490901238,-6910006496748887049,-6459055979533363794>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s3tfsxurbwlwxk","tT+f6PYy0Q+1V0ZX/6jxJz8NXh3lH8FO287uTYWy",79466163385865906,-2863548980490901238,-6910006496748887049,-6459055979533363794>();
      }
   }

   public static String bs(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3m9ujefdjbb4z","nIoaP/ldmdf/KhnT2aZX/WpElnSbI4r+qnRZNUa6Ydg=",7928712584545252717,312837656733650629,-267619898727214713,-1242794914882373185>()) {
            case 1516809895:
               return (String)com.yiyiaddon.m.b.a<"s1oeo6sktcze02","tlzSV4JzjQzh3ASyoR91Y3Ga2YWLhqcCpjZaRA==",-7575723159547429756,1464284567677532172,7264502778855364498,1719613227684361805>();
            default:
               throw null;
         }
      } else {
         int var1 = var0.indexOf(
            (String)com.yiyiaddon.m.b.a<"s29kpopz01nbfj","CBlPhxmOF7yaFF1Bn+ahn6b/62UgoX4hPn20T6IEVlY/2Q==",-5527716686837856003,1143670978735324424,-2092802436590593603,-2939548184169290866>()
         );
         if (var1 >= 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s18wpwejlh4ku9","h3ZrBbbuBfNIG0xbxYqUCp9e/SQtt2oUhJihJFD2OUg=",-6462737822531105343,-5299229592827181963,7480836633172121701,654337727971756910>()) {
               case 72714817:
                  if (var0.endsWith(
                     (String)com.yiyiaddon.m.b.a<"s374n214lnf7x7","gMeiBAAtGY3U31Zgs9/4oPssSAymwj3tFd+YHFXi",1489589217502018136,1495517517808081885,-8296708697738575479,-1754386649433227404>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"s14ix6lslyfcff","XGq19NzZ7hqK6dqmt0ohZkV/SDMjAeB2+lhKdVIJbx4=",7557952064342300312,1198093326466883350,3657590991195346471,-5150356396077319655>()) {
                        case 292348147:
                           String var10000 = var0.substring(var1 + 3, var0.length() - 1);
                           switch ((int)com.yiyiaddon.m.b.a<"s1rb8tnuridj3y","9jO4rm3V+pbm3FmCH4yR2yA0peVdIJ58UVgOOxUrZyg=",-3437034926729924048,8966392925799923333,2308899939333342261,5527621163195240236>()) {
                              case -919624715:
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

         switch ((int)com.yiyiaddon.m.b.a<"s2vjjc11rrzbvs","bN7ybxeNg/jpkA6ywc8I060xlKhFFN7lUO+ibCvDNgA=",1522190295745507254,-3142296187574638054,-3709414744538262443,-8819774561753433875>()) {
            case 1354853397:
               return var0;
            default:
               throw null;
         }
      }
   }

   public static String bV(String var0) {
      String var1 = bU(var0);
      if ((String)com.yiyiaddon.m.b.a<"s37jzcuulz55eh","DLjOuyO9ScBNGUw6xH5e2y/nShJdAiGZ+lk5flRwx77sUphSKgk=",-5961963481342313801,6443013487547828269,-8219207897019363331,6141785959082798463>()
         .equals(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s220hyw0x4dxfg","q0pR6j4l6uII5grudsiE/vl27IyjklI9dyjHsrTxyfs=",-3911599240011277563,3699696511993783783,-797001629615368048,5032305196212265878>()) {
            case -2011970995:
               String var10000 = var1 + bs(var0);
               switch ((int)com.yiyiaddon.m.b.a<"sr5nsdlvx3ddf","wz0bQB5JRL4sXNnb3W4lnii605y0p4eCL45DOw341xQ=",-1230153541405223534,-4831119724134703838,-7400641372816858036,-2905101591159771127>()) {
                  case 1841772386:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s3mlrs4yu5fdn2","xoK+qv90CziXxF33dset48OjFx8X6dkoL2CfqRKvqK0=",-3728801859999002074,-5130719555571364724,7289687302439638244,3770293441911683702>()) {
            case -2963261:
               return var1;
            default:
               throw null;
         }
      }
   }

   public static d a(d var0) {
      switch (a()) {
         case MAIN_MENU:
            var0.b(
                  (String)com.yiyiaddon.m.b.a<"s390fqqeh1ez9h","rBascyKzuEDdGPcg+hvKinGCkBGP2wJvmvK3DWXGhOc=",5085458457024623211,1995392208745248510,679182053748078213,-8752263449699544763>(),
                  (String)com.yiyiaddon.m.b.a<"sf339ptulcrb9","DVBgEgqRh+FgqWSfRkwZMH7KEqZGk1bb41yO6P34TofpnN19mXM=",-2329704454877661021,7399252234426684078,3047821992186213928,-6309445241363286839>()
               )
               .b(
                  (String)com.yiyiaddon.m.b.a<"s3pzf0w9aq4vzd","Il7j/4EsOPiYbj48BeyeFSP5cHuHodh2PmT0iYnx+lyKDg==",6823899024669643121,3125645355138406771,8081660037102291339,-3965805759745447689>(),
                  (String)com.yiyiaddon.m.b.a<"s9jjjv8d3n4a2","RRYZzD5sjOfHEykRdFQ14nQAc0j57y255Wc4k6ZVSCEezw==",-2656314760224727957,-5341928294022421643,-7937408317216695106,6281731522340061609>()
               );
            switch ((int)com.yiyiaddon.m.b.a<"s15dxa5ptneg5h","cJ0ns7WQkicGhbBaseS3xbqhHBUW6prxKubO61Ye89M=",8233049757164782622,-1582501504553691754,-3033168958644255904,-570112371205954236>()) {
               case -1666484125:
                  return var0.a(ec());
               default:
                  throw null;
            }
         case SINGLEPLAYER:
            var0.b(
                  (String)com.yiyiaddon.m.b.a<"s390fqqeh1ez9h","rBascyKzuEDdGPcg+hvKinGCkBGP2wJvmvK3DWXGhOc=",5085458457024623211,1995392208745248510,679182053748078213,-8752263449699544763>(),
                  (String)com.yiyiaddon.m.b.a<"s3l2ni8gjxh4tm","D9/u8IUyMhomp+nOy3fPmOrKEl1wRGGVnX29ST+tAyanPL/p",-1503323983172054126,7155816276615828442,6493886612682104646,-3095021292548924800>()
               )
               .b(
                  (String)com.yiyiaddon.m.b.a<"su6l01iu3vdd9","bI1vhuPAhBaMPPPONFKzxDh8BDkO8tzRRYcp/pf+7eU=",-592840255656795161,-3605307533420175526,-6565864891148805059,-6376891936553651163>(),
                  V()
               );
            switch ((int)com.yiyiaddon.m.b.a<"snwe3i490fn8t","/TjkMO020xcsXfxO2RyWgcqk5p5hhrmuMo8/VbAkj10=",7019533047966941090,-1157574691557248924,-1121280615744089940,-1538593433696859230>()) {
               case 1163054454:
                  return var0.a(ec());
               default:
                  throw null;
            }
         case MULTIPLAYER:
            var0.c(
                  (String)com.yiyiaddon.m.b.a<"s1smzp7q1c3b08","QX82CNfWEWloRXjnZHz5G6AYnTd0b9XQODVatlOtCMRSbOQj7Hg=",2727316834758239795,6555277374442510143,-3620261679444454641,5867423343894062494>(),
                  T()
               )
               .c(
                  (String)com.yiyiaddon.m.b.a<"s2ksudfgbj2xte","2OonjF6S/vDly/mKX4EKYaUpYbtvocDxkY+y86NLTX1e86pa6Vc=",8693418968635034542,-339671311010992436,-7348707531057354362,5479489596933118007>(),
                  U()
               );
            switch ((int)com.yiyiaddon.m.b.a<"s8jkt661l4xzz","tgH2lhLMXTEHdHEZD9S22zkU1JwUuhngGzNzbW4adWw=",-8651510407556491958,7988220363598771962,-7171797829413199408,-7458556734079656338>()) {
               case -1507490564:
                  break;
               default:
                  throw null;
            }
      }

      return var0.a(ec());
   }

   public enum a {
      MAIN_MENU,
      SINGLEPLAYER,
      MULTIPLAYER;
   }
}
