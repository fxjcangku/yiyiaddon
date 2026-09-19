package com.yiyiaddon.e.j.j;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.chunk.LevelChunk;

public final class c {
   private final com.yiyiaddon.e.j.a i;
   private final Minecraft O;
   private boolean cO = false;
   private int jE = 0;
   private int jF = 600;
   private int jG = 0;
   private boolean cP = false;
   private int jH = 0;
   private boolean cQ = false;
   private BlockPos v = BlockPos.ZERO;
   private int jI = 0;
   private static final int jJ = 3;
   private double ab = 0.0;
   private int jK = 0;
   private static final double ac = 2.0;
   private boolean cR = false;
   private int jL = 0;
   private static final int jM = 100;
   private String ne = (String)com.yiyiaddon.m.b.a<"skxkevihrivhd","oUzprPZs1gFhc0Oep6A9BF4UhF5YtonjZBASYA==",2004183792455378804,4787866526543872888,7572844953925122356,961123803076668506>();
   private int jN = -100000;
   private static final int jO = 5;

   public c(com.yiyiaddon.e.j.a var1) {
      this.i = var1;
      this.O = Minecraft.getInstance();
   }

   public void f() {
      this.cO = false;
      this.jE = 0;
      this.jG = 0;
      this.v = BlockPos.ZERO;
      this.jI = 0;
      this.ab = 0.0;
      this.jK = 0;
      this.cR = false;
      this.jL = 0;
      this.cP = false;
      this.jH = 0;
      this.cQ = false;
   }

   public void ai(String var1) {
      this.i(var1, false);
   }

   public void i(String var1, boolean var2) {
      if (this.O.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1r98ubmyrrnsu","Cix3cToGRuAHURvo0BpGRNnL2cUb1Q8o9oK8uyij0/c=",6904620055893141772,5689508268766738646,-1006449138036605667,-7192475868274381387>()) {
            case -919341162:
               if (var1 != null) {
                  String var3 = var1.replace('　', ' ').replace('／', '/').trim();
                  if (var3.isEmpty()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2c2e1h5bemh15","vfRPE98eyh6wOi55F8x4vEHzxdu7lCV9X1+AesLM6EA=",-2738858948706200178,-1805870207233340583,9085429765429706163,-9012490669600463331>()) {
                        case 1357259116:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     if (!var3.startsWith(
                        (String)com.yiyiaddon.m.b.a<"s3qduedppwnmhj","EubUJSPcjVu1rt1v73UNKoodjvws8/NDp+er7Sww",-5001429186051281629,6043868564203891049,569364381032567587,-5963324179639663646>()
                     )) {
                        label74:
                        switch ((int)com.yiyiaddon.m.b.a<"s190pnzwhaz737","wJA5SQDzIFLObHoT3HYtHVgLlSELuD8mDBrSodjr8Qg=",1651578205218334038,-2094937773985851622,5695675271240746038,6522190420443297509>()) {
                           case 1136389529:
                              var3 = var3 + "";
                              switch ((int)com.yiyiaddon.m.b.a<"s3r2hy45kwnf8v","mP3ywY720DGBC8Q0GUz1g32CvYxhZlv3YH37TZMfpAQ=",6962399295517085152,-2317799475783486995,2737071517909073992,-5584601264329174669>()) {
                                 case 461200476:
                                    break label74;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     while (
                        var3.startsWith(
                           (String)com.yiyiaddon.m.b.a<"s3rclckmv1kf7o","jZkUENt9M2hdToxo1c1n/qQLisMVz3tQCpwCjbZARmo=",-5094221081062878123,2960817860386373127,6517142617679460100,-2557206975553732237>()
                        )
                     ) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3gfc00ws0e6nv","imqBwN/zQj5Jh+8att6heVmCF+1UnDxfoEoLkq5cJrI=",9080410896566521359,-9146527063724298928,-1054182579193596419,-6691683687673363280>()) {
                           case -134264532:
                              var3 = var3.substring(1);
                              switch ((int)com.yiyiaddon.m.b.a<"s382xagk4h57c2","O+vb/1NeIFoeS3MTi28F8QVhtRNhGMtLlb5OZq3y8l8=",-6943554164265084496,4815862843813958573,-775974337694981705,-1330288005765488894>()) {
                                 case 1265804760:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var3 = var3.replaceFirst(
                        (String)com.yiyiaddon.m.b.a<"ssz1ex4bso1ey","8TFJmPbtybkz5cGIe3lWHfL6i5xayMCvdze57tIXke5j2Ca73cc=",8724925174922210825,7691348073200156320,8165867298148133679,562939927992434072>(),
                        (String)com.yiyiaddon.m.b.a<"s3qduedppwnmhj","EubUJSPcjVu1rt1v73UNKoodjvws8/NDp+er7Sww",-5001429186051281629,6043868564203891049,569364381032567587,-5963324179639663646>()
                     );
                     if (var3.length() <= 1) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3bb1sgjrlj8hh","PQOEaIFK9WF3TYhJ+bTRO41t5NRGdUeFcuH6qbSvyA4=",2193327097421943849,7999256208993692345,1884622232859918163,-7839140091706346036>()) {
                           case -1807231248:
                              return;
                           default:
                              throw null;
                        }
                     } else {
                        this.aj(var3.substring(1));
                        this.O.player.connection.sendCommand(var3.substring(1));
                        this.jF = this.i.bp() * 20;
                        this.cO = true;
                        this.jE = 0;
                        this.jG = 0;
                        this.v = this.O.player.blockPosition();
                        this.ab = this.O.player.getY();
                        this.jI = 0;
                        this.jK = 0;
                        boolean var10001;
                        if (!var2) {
                           label57:
                           switch ((int)com.yiyiaddon.m.b.a<"s1ps5mdi6ezwts","99OPkNdcvzya26vWO2B0Y9zphHtY4sBaeID/bZByPvU=",4904525187000877911,9010328129713144495,2389902273729494771,-4133301450819251751>()) {
                              case 1464634597:
                                 var10001 = true;
                                 switch ((int)com.yiyiaddon.m.b.a<"s30ieys5iaxdml","pkaPO2jP39cdHacWxw5b003ls/UZO5KKTeWiZydoanI=",5559381250969698561,-9164918796517903873,6413763576157122758,2855499380222035708>()) {
                                    case 1807821998:
                                       break label57;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10001 = false;
                           switch ((int)com.yiyiaddon.m.b.a<"s376szc088gv9y","ZStShrUEHqyEs5elaUKruYfVqygOIUDAxAG5ZwBC/WI=",-1825719198122995629,8265505027554449172,-5218307111206595699,-1780960657876144324>()) {
                              case -945724102:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        this.cP = var10001;
                        this.jH = 0;
                        this.cQ = false;
                        if (var2) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2cc9rsxxe7289","9/NCgWfP/BUkHQ2C5meM8UrPrAkTKlPFK0hvlTGhoYU=",-3114604663369817456,6689264567428815372,-849203470129935144,-5295087331945498849>()) {
                              case 1244551514:
                                 if (this.i.bM()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2b8uf9obennxl","2Z1m9XVV6mxvbH3AIeWGO6aDb7SVNeEyZE5HN62l4mo=",-6010372802430072906,5802855631883095333,4420809678121482604,-5720776343593450086>()) {
                                       case -1990654519:
                                          this.cR = true;
                                          this.jL = 0;
                                          switch ((int)com.yiyiaddon.m.b.a<"s1z306msble52c","ySEn7BIqzg2SnUUz7JD7Y6TO82ilOeqz+QMGyv6XdAc=",6721697168388692781,-324729614145774079,-699091565423342239,-7284834909608392267>()) {
                                             case -1137413697:
                                                return;
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

                        return;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s9f6ir73wuxbl","WvXLw+praxmJPUZsDwh84XTFexjCSOTG9+2gxojqH14=",8504262992176469549,500277318446328667,8969861443213419761,-3912141488149161135>()) {
                     case -66599207:
                        return;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }
   }

   private void aj(String var1) {
      this.ne = var1;
      int var10001;
      if (this.O.player == null) {
         label15:
         switch ((int)com.yiyiaddon.m.b.a<"spx58mars5q15","O0q+YMA8PBAOGlYfh/8Y/5B6i27bkEjY3yzMIgelxaU=",3001366880758374793,6358601566221142092,317333432841695938,-8158701469810586288>()) {
            case 1521981652:
               var10001 = -100000;
               switch ((int)com.yiyiaddon.m.b.a<"s30lgheruka0t6","4ohc0aBkzpOsYJaOlD9xkgvS8eg9tiQPRc5Sh+dtef4=",-1480719029418388593,-2201599251433978376,-6526004853439595781,-8341716642444270201>()) {
                  case 1841613412:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = this.O.player.tickCount;
         switch ((int)com.yiyiaddon.m.b.a<"s1bgleqtuokgig","HuvbSmZpKu+HB3CeEUHN4m738juZ42qWWTMqlCZqMNk=",-7094886514241650859,345819496017965716,-8176772160394555581,-3660486789600284164>()) {
            case 1264785789:
               break;
            default:
               throw null;
         }
      }

      this.jN = var10001;
   }

   public boolean G(String var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3aw1mnm6koms5","hWjX2MwWHgyqXSeXvTGOhax9ECXenqEEp4ymuf+zo8E=",5384540409859349367,-3082476095138329312,-5564282392483275601,-3826670527629462489>()) {
            case -1287853897:
               if (this.O.player != null) {
                  if (!var1.equals(this.ne)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3t0g697yjdxtg","ilSjz5GsLYNA92XdSHLW0+k6c7Rdn3VRKG9DIh076Jo=",8060194176199540869,6861162507830969178,-869478510364469352,-5808534859196990015>()) {
                        case 1017112052:
                           return false;
                        default:
                           throw null;
                     }
                  } else if (this.O.player.tickCount - this.jN <= 5) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1sp7p1hikp5ay","jNhu9tHZwfAJvs9SjiShw9fydTyCpdrArnT1xpcXzO8=",7470613093854737290,3417722312252801836,-7499049631104531669,-804744352494223995>()) {
                        case -1012492518:
                           switch ((int)com.yiyiaddon.m.b.a<"s333106gm0qseh","35fc38huE1A17wAYtfBpaqJdTBNUEGJneFUXjRSE5SY=",-5865683504635405596,-7567104801256705773,5822171429774417923,7970536805159373115>()) {
                              case -631950751:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s260bxk5bykr73","Gk28UGJThFdv2/k/pDdm7EiRCWwkTJhs6kIZDfDcDRs=",2615211685781057435,-5729886429875237968,-5621924392707321488,-4702468878757459682>()) {
                        case -378839958:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sm21ycqi1tsp1","M6S3wYgSq5FKUW+Qk4XBPEH1eP/+9NXMpP8gJYjiyPs=",8122611170986298027,-6759955251427255504,-4388308701492594918,8870801279533308837>()) {
                     case -768566992:
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

   public boolean cw() {
      if (!this.cO) {
         switch ((int)com.yiyiaddon.m.b.a<"s2vkrk710ukzv9","WZv7sAqTYwUJUg0t1jGPeCw+qtRbOD8iQE6Kbe/AFBE=",408942565991858663,-5221167885108626399,-369400272114690236,815492483050933193>()) {
            case -1832902679:
               return false;
            default:
               throw null;
         }
      } else {
         this.jE++;
         if (this.cR) {
            switch ((int)com.yiyiaddon.m.b.a<"s2kta3y54abctb","2cBmnOoWUglUYMCAofDg/0SgwBgbDbGUr1Bl9UCyCz4=",797192725020251514,2865785815099618725,4574042360210344343,-3633679837729195043>()) {
               case 568583824:
                  return this.cA();
               default:
                  throw null;
            }
         } else if (this.jE > this.jF) {
            switch ((int)com.yiyiaddon.m.b.a<"s2pv39937yrpzy","moVxberVL5ZwqCieOWd/PWh6YUkjuxvmQjVLVUy4O30=",-2603791982571102475,449534347218833375,-8943274066238203242,8710394041632805426>()) {
               case -1389355631:
                  this.i
                     .b(
                        (String)com.yiyiaddon.m.b.a<"s2hf4uexosts05","ZJmU9/uLeoS10etnq1TAyMszRXi+mYRvpn8fPULnSnRjZkNMQASvjFuC+To=",4350141951386274995,1312533677963612410,4515131883222175174,-5640570586280371137>()
                     );
                  this.cO = false;
                  return false;
               default:
                  throw null;
            }
         } else if (this.jE < 6) {
            switch ((int)com.yiyiaddon.m.b.a<"s28vicyfqaxo36","b1MloUN1ywg+1JFZXQIrGi+8rWEbWA+fkczJCOCn7mQ=",7379626033273877114,-2021729137498422711,-2577371116494942230,8604135741404473133>()) {
               case -1479570090:
                  return true;
               default:
                  throw null;
            }
         } else if (this.cP) {
            switch ((int)com.yiyiaddon.m.b.a<"s2c6lm1178ej85","+23wKLufX2PUvYf14L/9gLyOBFy+zTNCZ+5KQcz53no=",-1841731474937126284,5919499452507136129,3919939891587861154,2073784545252821207>()) {
               case 1187996598:
                  BlockPos var4 = this.O.player.blockPosition();
                  if (!var4.equals(this.v)) {
                     switch ((int)com.yiyiaddon.m.b.a<"shufhto0qaat7","+tkF2wrg0G9TXmpitnOX3bzs1pkVbKV+aZsJL8oP6ds=",-4368751093993859313,-7306686791096218092,865443408701764892,6715811832505823572>()) {
                        case -1101399127:
                           this.v = var4;
                           this.cQ = true;
                           this.jH = 0;
                           return true;
                        default:
                           throw null;
                     }
                  } else if (!this.cQ) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3682la69m2dcw","mNGnGT3EKooF8XmQxDrl9bGJ7aApvgXfUfc8Jk479hU=",4017141929338682608,3716638854803484018,-5868969338950103064,3663019444172818038>()) {
                        case -1120368264:
                           return true;
                        default:
                           throw null;
                     }
                  } else {
                     if (this.cy()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2dzocqz8bgu4p","EaXQMf5Jkx6nQ9CRk4AQFqLwNiJKRe5G/FG3E7uhGVw=",6407656609694910357,-5256676547480005900,-677555967782750801,-1384396434440299064>()) {
                           case 207040590:
                              if (++this.jH >= 2) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2hj88l5nuggjp","BYh5p2d1u/dhi8jdPAMCgbSB4OFDlzQtNGLSM2U2TOY=",-318190327611060978,4751031377445089497,1398445832275231805,-6611971046410640747>()) {
                                    case -346343990:
                                       this.cO = false;
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

                     return true;
                  }
               default:
                  throw null;
            }
         } else {
            boolean var1 = this.cx();
            boolean var2 = this.cy();
            boolean var3 = this.cz();
            if (var1) {
               switch ((int)com.yiyiaddon.m.b.a<"sdvu3nkhjyjrm","wShdrQQNaisKif2LPDU+CYqUE51+P77vF8/VhYPxnDg=",7820483392377862103,-4407992606723795864,2663320441885039991,-7467845007514162273>()) {
                  case -1912056855:
                     if (var2) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3c6h06vgjmqe","7Rz2iMsR3ilZonL4QhEqb5uUkHVLn782l7jQp4NMtT0=",4315819352694511992,1226585254662200066,-5421275130504795965,5099004424291705214>()) {
                           case 2042140202:
                              if (var3) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s7zeg1s8h7ytj","sFkHwbwBQNtKgfQuxtwfW8Y7kwTuLFBzvAFjZd1DirA=",857190562291780586,8886628373056049727,195467028874541716,6289350206048956459>()) {
                                    case -860222126:
                                       this.cO = false;
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
                     break;
                  default:
                     throw null;
               }
            }

            return true;
         }
      }
   }

   private boolean cx() {
      LocalPlayer var1 = this.O.player;
      ClientLevel var2 = this.O.level;
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s23ibcq5e1ifao","3vwuhWMV6hPmpNuJJqvchlvX93WW5HQFZqaNsfPJENo=",-4774626427434099977,2806591194758920583,-4512467858565675178,6148822252827112994>()) {
            case 43501875:
               if (var2 != null) {
                  BlockPos var3 = var1.blockPosition();
                  int var4 = var3.getX() >> 4;
                  int var5 = var3.getZ() >> 4;
                  int var6 = 0;
                  int var7 = -1;
                  switch ((int)com.yiyiaddon.m.b.a<"s1vnb6qq0u591z","W/5osltRsAMjJe3qbuhEA7duA3iyxUWkpiSk+DxGyTE=",5423549317629883263,-650104378165023254,-5454316435151269256,2272094082900541121>()) {
                     case 929000597:
                        while (var7 <= 1) {
                           switch ((int)com.yiyiaddon.m.b.a<"s39sn2adp3eeef","UoBUJXHNzCdNPRZc/RCJyfpMx2bt2vG+EnbLK2QHd2c=",8079471575125585438,3378512931194912441,-1592658667724740857,-6989812715465022720>()) {
                              case -179719785:
                                 int var8 = -1;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1zcxajj28on6h","4o9uRaKusE6+xZfhG9XBncsGLo5D/lPTP9kWBaqpbQM=",1626552179738484269,-4797373114787704373,2844427506787823255,3598930092984586767>()) {
                                    case -1525293607:
                                       while (var8 <= 1) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3qgnx0xfzk2k","RHYRAF0LMgcZKEYLABqAIINTFEvRtgb++nGbUOt1Vvo=",-4524378801545595070,-5163981361455572317,-3345427203063938048,-8927468431712864632>()) {
                                             case 210928006:
                                                int var9 = var4 + var7;
                                                int var10 = var5 + var8;
                                                LevelChunk var11 = var2.getChunk(var9, var10);
                                                if (var11 != null) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1ip3sgyciumxj","Bc+9ducPd8m6xZFxox1bBA+AUepKu2tWv4sTxSyWZrg=",4499633349691920508,-2369412484242744147,-3180253938278245971,-4703064381222027855>()) {
                                                      case 765340906:
                                                         if (!var11.isEmpty()) {
                                                            label76:
                                                            switch ((int)com.yiyiaddon.m.b.a<"s3uohg9q43z7ng","aZf+2UEPh3CUMcd5DQ9E5WU4eSbkXUq20310fpERzoc=",-3937712334002922711,6687819561344856802,4088613302859671295,4579528802463487386>()) {
                                                               case -648968934:
                                                                  var6++;
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s1bck35pcf7pqh","5BTYcMwwuxvXOoL+c1PejMJCwFBUT7DLNIpst9xYm5Q=",-6640918047780566408,-251786467857807501,-8041435309213224724,5078555680282065174>()) {
                                                                     case 59864722:
                                                                        break label76;
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

                                                var8++;
                                                switch ((int)com.yiyiaddon.m.b.a<"s2o311xa0id939","phKeodMTbOL3oAMCVWtQjImDM+R2iLYeW4nPhry4YZQ=",-2267103848288892441,6079678727870461306,1802683313956481894,-5330714976341401971>()) {
                                                   case -676286493:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       var7++;
                                       switch ((int)com.yiyiaddon.m.b.a<"sr828osbbst04","ScgNzOg2Rp4//7LXzNkFHhCAX9xYJeNzGeTyIJjgC6I=",3080632493430392797,-1750008698590963320,4192356169534435997,6197239375240764963>()) {
                                          case 1612724859:
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

                        int var10001;
                        if (var6 >= 9) {
                           label58:
                           switch ((int)com.yiyiaddon.m.b.a<"s2m9m7lt7rdras","pHIOTSHCcFavSfwRy4ashEARsL4Fk1rfr8YBllCUb9E=",7178149944216439731,5458377957182272391,-1689887491356194352,-7645944886341188396>()) {
                              case -1134595793:
                                 var10001 = this.jI + 1;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1irlengu62702","sDtxXB/ErgZ+AD3z2gltcsyrEdRW5IUcEkBJB/w64sU=",8568869482178298677,2168677378143706080,-5577082597807772476,-9084957544534576208>()) {
                                    case 1581031922:
                                       break label58;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10001 = 0;
                           switch ((int)com.yiyiaddon.m.b.a<"s2ess0e2dnzpll","C3MxCo7htVNUfjEph7j2nAuOviKhaY5jkbHBp2OpmFc=",1701440472070985160,-4110021453651765364,2170826930492818949,-7670373789326256250>()) {
                              case -798026708:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        this.jI = var10001;
                        if (this.jI >= 3) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3rgxs3zms8zbl","6pkIvTKPCXxr1KY59bxKeTsThnFNUv1kdququhqG4VI=",-4853443241696232112,4967647451520711904,-5943211240893489351,-7663551471878385500>()) {
                              case -717290008:
                                 switch ((int)com.yiyiaddon.m.b.a<"s2djf1s8xtnibb","CqPnc2IMhag3ZPYa8Qsaq03nYt+wbyX3UoW+cFgylY0=",-1273105868982620119,8605483081971128465,1185034793245104991,3507432724336017328>()) {
                                    case -1033072769:
                                       return true;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           switch ((int)com.yiyiaddon.m.b.a<"soo90cmc7mw0b","4dl2sF/GwzDPvU5wrveoZQb2V0xi0tQ4uiXRHdlSSOg=",-3992769194880294640,-2302110362967538302,-6437684343958503670,4970088555985176795>()) {
                              case 374437570:
                                 return false;
                              default:
                                 throw null;
                           }
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sc1c7w4m0whs7","u8GP6IEaDtgn+8TyZwaC52S/KAuqDsKq1Gy35WAIvv0=",5060882462202238642,8132241613905739961,-8122996276221378753,8726838144897751251>()) {
                     case 887526477:
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

   private boolean cy() {
      LocalPlayer var1 = this.O.player;
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3m7c63rpeu7je","aegqDbqiPtK92JwXNh1pg6/e2D4THIMkA72QnvDJMSI=",7088862466981581534,4854268243877837507,-2939431145076723355,3902797443098899323>()) {
            case 1952936262:
               return false;
            default:
               throw null;
         }
      } else {
         double var2 = var1.getY();
         double var4 = this.ab - var2;
         this.ab = var2;
         if (var4 > 2.0) {
            label66:
            switch ((int)com.yiyiaddon.m.b.a<"s2e461nwzndp2u","7uGsiOQwCKo4eMdML393MuqBMPxpZruaXLbBHf7BiFQ=",-987891265695907746,2615666546378100861,-8402341221374361444,-5622564632241596210>()) {
               case 285378096:
                  this.jK++;
                  switch ((int)com.yiyiaddon.m.b.a<"s346xzqtl9qlql","KNZGfF0MemUNTu8JedvYOUprAlCN6mvRopmKYG1zPPI=",6903590460037815639,-3205177455136328802,3790747644494017700,-4968224906963293008>()) {
                     case 1134833158:
                        break label66;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            this.jK = 0;
            switch ((int)com.yiyiaddon.m.b.a<"s35o5jvxivkau7","jixjQywiKljntSp0C1ECKxrMfChYGOVF+Sycss4su7g=",-5511039641086607804,6877436266238421866,6408507792283674499,8150393696222164140>()) {
               case 141314242:
                  break;
               default:
                  throw null;
            }
         }

         if (this.jK > 10) {
            switch ((int)com.yiyiaddon.m.b.a<"s2oyweko8uh4d5","Jo5g9m2kjypv8/7wP8iYcJy4Os1Xwk30saeZ/vvVMJQ=",-8748064377315122834,-6980439274973728680,-8462726153709475721,-3307635230156347514>()) {
               case 1373459350:
                  return false;
               default:
                  throw null;
            }
         } else {
            boolean var10000;
            label84: {
               if (!var1.onGround()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2xdgs6jiyjwld","4FpDsI4eVq3HV/3l0XrKKY6oHGgXA4+mC8G01TCCMZg=",-5539936885294937463,2226066226045271757,1612952880974869062,6075229552110685110>()) {
                     case -247940813:
                        if (!var1.isInWater()) {
                           label59:
                           switch ((int)com.yiyiaddon.m.b.a<"s3u0qvq8f0wirh","31yh+pcvZiItDcbzRnBX60Xf8Cc23tOTNKEpflJg/vM=",5540065238746678014,-7507946360617748621,-4394386198665182760,6798582048574904430>()) {
                              case 756753809:
                                 if (!var1.isInLava()) {
                                    var10000 = false;
                                    switch ((int)com.yiyiaddon.m.b.a<"s2e6reggc7ojsf","LR5nD2L/2HQBEtQMpJWOZL4hc8TDKYYNvQn1h59/JLo=",-1424587511531770439,-3173947393382090059,931374492359577871,-5628388539063149819>()) {
                                       case 812564659:
                                          break label84;
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"spfgus8kvrmx5","jEqpvZyutb+1au7IyYu2h4x4aAgc1HB//ur+2U4NRJQ=",1735910813370805038,3812713848104668378,4823272646586538546,-5732860007924382711>()) {
                                    case 440534249:
                                       break label59;
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

               var10000 = true;
               switch ((int)com.yiyiaddon.m.b.a<"s3mdyu20dn19zb","+XVjqK6ui2juh+YFM+InQRW7X2QpylZxBxFpV4ZK/hU=",2515875961330278147,71605404898917800,-7628234459559364043,1639860955214992052>()) {
                  case -1195205470:
                     break;
                  default:
                     throw null;
               }
            }

            boolean var6 = var10000;
            if (var6) {
               switch ((int)com.yiyiaddon.m.b.a<"sg58j4wh0enkb","E3oTSaiQ4aNztCjXGXyH+FMFcDv1wYQxl2dZx0nk9Yo=",2152404821140448288,-5347075519973359076,1905350447504688508,-1449867401028150758>()) {
                  case -601740514:
                     if (this.jK == 0) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2os0a5p0v36if","lONL2QDfhgNkZeU6YMsDtMnbelDnSYFFPURyt7dEHzU=",3628032875817587235,5951384520822225565,-3385727381661081798,4647171388381640040>()) {
                           case -1626710616:
                              switch ((int)com.yiyiaddon.m.b.a<"s1sk2wgaqdy1e9","AXdXrKhxjtD7AFGNrzBOx1CkV5SpVE1jk0pM2Q8I8Ps=",-1169726885453095209,9057164051863483308,6332513146082593508,-2084097057313000525>()) {
                                 case 696173594:
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

            switch ((int)com.yiyiaddon.m.b.a<"s2poilb5vzl4yp","fY1MtigifsKONpfPNb2snnVvAuQDXR2e2nDN3moA5h0=",2925926109004521781,4118203141795837835,-7463281582646602315,-5551771095172085324>()) {
               case 1631225198:
                  return false;
               default:
                  throw null;
            }
         }
      }
   }

   private boolean cz() {
      LocalPlayer var1 = this.O.player;
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sukjl49ym3gfx","8Ui31eK6gMrUiwZitvOnve6IqG3j4Q69HIuwBRXZcXw=",214852094772267330,-6322505385708084897,9013385956177663070,3454253501197973379>()) {
            case -773917173:
               return false;
            default:
               throw null;
         }
      } else {
         BlockPos var2 = var1.blockPosition();
         if (!var2.equals(this.v)) {
            switch ((int)com.yiyiaddon.m.b.a<"s38r5piddkx2ur","dX+7TvZZSCoPXkOlremjuLRYodhxtwH3FnqT4BrpsBY=",6788844206861230792,-5279957537393165680,-4765423152785835243,4426630176747092826>()) {
               case -960242884:
                  this.v = var2;
                  this.jG = 0;
                  return false;
               default:
                  throw null;
            }
         } else {
            this.jG++;
            if (this.jG >= 2) {
               switch ((int)com.yiyiaddon.m.b.a<"s12e03w8a38031","uC99q8yqbqMnUTLeJKZ0rdfDLyYoEmJOF83hJUhRS/4=",1464385643849855360,7278903868622204027,6096498640997865702,1221284364581067964>()) {
                  case 600333307:
                     if (var1.onGround()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2qibfoswnldyr","l70HJwkREtAQU7K1VUogxpe7/TFLG2aFV+oknaANN3c=",2470255112297111043,5933837544567602006,-5086687263595227835,-7108037256280295623>()) {
                           case 359236884:
                              switch ((int)com.yiyiaddon.m.b.a<"sneljyswncvo8","mjnb/qL8xETTiKrmfoe+tiZM8en++Yze2cVEDwWQ/R0=",5871634433303673900,-3620587769659591530,-8610915110478881702,5340682153159067281>()) {
                                 case 344432350:
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

            switch ((int)com.yiyiaddon.m.b.a<"s2ih4qmfdhy7fd","Nt2tkyGq2p9j8kJe7sw5fr2CxhbeEvG2HRWlQPlRXSM=",1605706818490097352,8122212137423350649,-431063650005258665,-2555410683649981792>()) {
               case -947459709:
                  return false;
               default:
                  throw null;
            }
         }
      }
   }

   private boolean cA() {
      this.jL++;
      if (this.jL > 100) {
         switch ((int)com.yiyiaddon.m.b.a<"s1oqbxryf6wp4c","Kggd4As5n/MQGxOKCH6dVHFqHxWZQnR1pPIK4JkNhDc=",-3710453321650995082,6431786824673011635,-3492147007509204170,-106196341000840671>()) {
            case -412369960:
               this.cR = false;
               return true;
            default:
               throw null;
         }
      } else {
         String var1 = this.i.bM();
         if (var1 != null) {
            label82:
            switch ((int)com.yiyiaddon.m.b.a<"s3v450qai61i5","d3KH5JP1ORTEVsYYD1sqj/vGD/CmMhrB8LXYRysPDqk=",4121191830406400221,2042399796123369503,-6489335905788568862,-6387618636370401704>()) {
               case -859297167:
                  if (!var1.isEmpty()) {
                     if (this.O.player != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s38k2zy5ova075","/6MQp7+u8wR7PCKp0EA4BJ3aRTWIPlv6b888MM532Zg=",2849558836336057127,-5733832913767727622,-5529347107290079612,5181907875824308100>()) {
                           case 1329335167:
                              if (this.O.player.containerMenu != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1xo8m0023o9os","cK3Ixo4B9CZX42ckHoiJ6itCN6lOLVkwzEt5Z6gw7S8=",-431586030461044528,-9155925111307553844,4462151470748594521,-536554508129164727>()) {
                                    case -1598858200:
                                       if (this.O.player.containerMenu.containerId != 0) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1b4w73osrq41o","+oMWqJRtf7AoRHKhJVONrlOHNge+oNBhoyav9ESRPBY=",7165768910033885570,9106764814017131663,8220289927174574702,-122186842369729933>()) {
                                             case -1231469855:
                                                if (this.O.gameMode != null) {
                                                   String var2 = this.ap(var1);
                                                   AbstractContainerMenu var3 = this.O.player.containerMenu;
                                                   Iterator var4 = var3.slots.iterator();
                                                   switch ((int)com.yiyiaddon.m.b.a<"s36mc0w71je62q","K5LLCzyKIJClClkHQCV07EGoXFejSnDvFp012VV4OCg=",8343283552377994971,-6718655193533919362,-38222061912123661,5960434100758961932>()) {
                                                      case 511267889:
                                                         while (var4.hasNext()) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s3hvk9si6pjeu5","AieJ4c2VSME6QYPuFwBI97WQymPUu7ZyA/SeU7Yrtjo=",1086330157986074283,1341898426786872007,-7400427659530332466,-7762748176612630360>()) {
                                                               case -44767932:
                                                                  Slot var5 = (Slot)var4.next();
                                                                  ItemStack var6 = var5.getItem();
                                                                  if (var6.isEmpty()) {
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s3rmdqxz3vm09l","yAa4xXJD/eCmtcS4TSi7wpD1JzXnA/dQUgbvR4qPq7g=",6453627028483320813,8879338513804471417,-6134154170630516256,-6414332846582358129>()) {
                                                                        case -1825160677:
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s3kpyg8h3jiqoi","akGacBkzrR22k8JyMsdViJ2yS1QwPIa3Omr/tNcPjew=",-690916136040663226,-2418027220196843755,-4677035221925389803,1401992104576200261>()) {
                                                                              case 861135324:
                                                                                 continue;
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  } else {
                                                                     String var7 = this.ap(var6.getHoverName().getString());
                                                                     if (var7.contains(var2)) {
                                                                        this.O
                                                                           .gameMode
                                                                           .handleContainerInput(
                                                                              var3.containerId, var5.index, 0, ContainerInput.PICKUP, this.O.player
                                                                           );
                                                                        this.cR = false;
                                                                        return true;
                                                                     }

                                                                     switch ((int)com.yiyiaddon.m.b.a<"s138g72nprgh3u","Rlt9qqTqAp7eHLQCZpP0p05DjB7absJJ16GGv8Dij5Y=",2321776045488986894,1748898796919949876,2633939248844318712,1610248739091919710>()) {
                                                                        case -638387725:
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s253hcigfjw15f","ZMzpuO3YPaCC0UZ0m+GCaexpGwOwmY9lb3O9FPcx1Kk=",-5601004470864087706,6334907656900006240,-4043447942113313457,-6931112288198154217>()) {
                                                                              case -1517081834:
                                                                                 continue;
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         }

                                                         return true;
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                switch ((int)com.yiyiaddon.m.b.a<"s35dt7gtkl2k63","1zAjyqo6FL38z7qVCuxckeXn0yqrPmJLyp3CUCaqckA=",4959527654406602009,6500640159238943459,-8981718593510572600,1498055160570800108>()) {
                                                   case 1366233677:
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

                     return true;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s21k1k9z0l3gql","5/xCgdAR2AzFkpkr1PNElDMdf+1PP50obhM2ED8S2IU=",6443490943657068677,-1682983092760042000,5419787141126595358,879758379555131938>()) {
                     case 1935482938:
                        break label82;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.cR = false;
         return true;
      }
   }

   private String ap(String var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ooqifm89ztzf","vSfOylVA1ZLJdTK3pQjY5xW5cXXnpJmws4GelACmrrc=",1195070161303560068,63906440767575595,3848883496835184480,6678176922219464800>()) {
            case -1558665426:
               return (String)com.yiyiaddon.m.b.a<"skxkevihrivhd","oUzprPZs1gFhc0Oep6A9BF4UhF5YtonjZBASYA==",2004183792455378804,4787866526543872888,7572844953925122356,961123803076668506>();
            default:
               throw null;
         }
      } else {
         return var1.replaceAll(
               (String)com.yiyiaddon.m.b.a<"suvijehfxl7x5","LEAuO//Y3D9igeWbESPuyxibg392VVXZpxW9y+nPAsw=",-5453474910224519565,507629134020625016,-8526219274048320107,2295553867120048063>(),
               (String)com.yiyiaddon.m.b.a<"skxkevihrivhd","oUzprPZs1gFhc0Oep6A9BF4UhF5YtonjZBASYA==",2004183792455378804,4787866526543872888,7572844953925122356,961123803076668506>()
            )
            .replaceAll(
               (String)com.yiyiaddon.m.b.a<"s2o6558uld51c6","EDYn2o8yCUSaWdne/X9By8xuxooUAy5JAPaYlF4MQv3E2Q==",-4012983875107007640,-8049647648344621960,2725505423185275079,-3761187152289681017>(),
               (String)com.yiyiaddon.m.b.a<"skxkevihrivhd","oUzprPZs1gFhc0Oep6A9BF4UhF5YtonjZBASYA==",2004183792455378804,4787866526543872888,7572844953925122356,961123803076668506>()
            );
      }
   }
}
