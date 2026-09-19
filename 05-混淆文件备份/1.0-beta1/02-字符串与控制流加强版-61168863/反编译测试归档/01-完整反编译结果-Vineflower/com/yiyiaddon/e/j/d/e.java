package com.yiyiaddon.e.j.d;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Plane;
import net.minecraft.world.level.block.state.BlockState;

public final class e {
   private static final int iP = 100;
   private static final int iQ = 6;
   private static final int iR = 9;
   private final com.yiyiaddon.e.j.a c;
   private final Minecraft H = Minecraft.getInstance();
   private final Deque<BlockPos> e = new ArrayDeque<>();
   private boolean L;
   private BlockPos k;
   private int iS;
   private int iT;

   public e(com.yiyiaddon.e.j.a var1) {
      this.c = var1;
   }

   public boolean isActive() {
      return this.L;
   }

   public BlockPos l() {
      return this.k;
   }

   public int bD() {
      return this.iT;
   }

   public boolean ci() {
      this.f();
      LocalPlayer var1 = this.H.player;
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s22iwkdhbe5tha","JxPwCnHpPyQMJatc+pjYc1P/HS2whlYh6riDAafmsvM=",-3989300921915435088,4335998095251949457,-109604811330523673,-3030178183479483908>()) {
            case 975637335:
               if (this.H.level != null) {
                  BlockPos var2 = var1.blockPosition();
                  ArrayList var3 = new ArrayList(9);
                  var3.add(var2.above(2));
                  Iterator var4 = Plane.HORIZONTAL.iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s7zbp8pdif28i","+FwM0XL/4BU0Z8XtURDOjXxePy7/sFgCf8YouXbF0Xg=",-7892601147501190484,-694014398998008485,-3178751235263693174,1429558107468034041>()) {
                     case 956849327:
                        while (var4.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2xnvizqkvqt8g","osHVxEvR1gHDhTqkllBbZCN5Ei7lpA8sfcyvDhL59l4=",-5413213816633335331,-8838760750342327135,2889397860649138924,-7782676479027492231>()) {
                              case -1197737964:
                                 Direction var5 = (Direction)var4.next();
                                 var3.add(var2.above(1).relative(var5));
                                 switch ((int)com.yiyiaddon.m.b.a<"s1r0ypbzewfpa3","vcgqALzTGq6WFnbKaahH9D92GpWxflTvGgztOjOp1/g=",-7435245857430166333,735297264006551953,-7699497284682538005,-5553852548650644422>()) {
                                    case 580116308:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var4 = Plane.HORIZONTAL.iterator();
                        switch ((int)com.yiyiaddon.m.b.a<"s2whi75q11paiy","Srq9uQavntOXx9McK04lTK72mkiGzH/IfuVJWmVZl2k=",6544213997085770630,1286413795914318263,3813117858777423689,-4639405433953957400>()) {
                           case -7706449:
                              while (var4.hasNext()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1pk3kkbd12u0","5mzTUlJrgycuI3oQ+bkPsy5dYQa22QACYqUDq95GP9w=",4034864881944781069,6370109696702419550,-4028660664515480802,6638988916795071209>()) {
                                    case 34655696:
                                       Direction var8 = (Direction)var4.next();
                                       var3.add(var2.relative(var8));
                                       switch ((int)com.yiyiaddon.m.b.a<"s2i10mxj5hhr8w","9l9mPWNH5BL8n3efdKWn9hxziFORQ9dUiJUiSAKo5qs=",8207196354453316548,-38086480423896450,5274268622599712627,4134810770893714103>()) {
                                          case -569826592:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var4 = var3.iterator();
                              switch ((int)com.yiyiaddon.m.b.a<"shsb0478z19sn","cz4+l7q+zm+gu1JjmDp41ANIttn7nRdJ4ELQAcHP59Y=",4271163890816482786,8051295932100130332,-5675072923550432404,8358466294074897844>()) {
                                 case 1509885997:
                                    label90:
                                    while (var4.hasNext()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2dzkhbaehlmav","Alw34RJiYAfPTUtQq4Mk5LOTJgoc/frUhbi0X+BUnjU=",-8763494457961081832,6246342001319106960,-3479764726132640308,7974746173655000038>()) {
                                          case -721149350:
                                             BlockPos var9 = (BlockPos)var4.next();
                                             if (this.e.size() >= 9) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s1f2uvmlych8by","D3WmVuPtMOQaiaWLQegxf4wekXJLKZoqfgij5BRDB+Q=",904100736767835545,-20674374040930398,8973855839611386346,-1086268561251134410>()) {
                                                   case 657332881:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s182ctvqz61ct0","azxVAhzqMXu/i0ox4hbRmBovlQUtPd3HcbXKOpT6SQM=",-2940606996828217,4235864700139801487,5312693860518065800,6285302324891939731>()) {
                                                         case 2107746316:
                                                            break label90;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             if (!this.e.contains(var9)) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s1yftrtmh747ts","Z0pgFNO/l4Ui9y5ztkc00+uUwZJ1imFB3mMHfgOERh8=",-5621643305160700760,-5249320703002740916,8497907741574910211,-8635462436689403234>()) {
                                                   case 2133817781:
                                                      if (this.a(var1, var9)) {
                                                         label61:
                                                         switch ((int)com.yiyiaddon.m.b.a<"sf9rakmhpvdfg","TQnVJyPriy65k6Bcul+3JIECbIZZviHzIX1ixwJlgTo=",-542332560378285123,2438919021127004611,-4975609438012904014,7673248825747142772>()) {
                                                            case -1724996954:
                                                               this.e.add(var9);
                                                               switch ((int)com.yiyiaddon.m.b.a<"s1l3g75br8k73h","ovBBL8TH2eBfnxHVTfEby/0QssPPWsIGnOwY/mBjF3Y=",157209140748090570,-8617096681017946568,7292540997640310427,3660300746872066818>()) {
                                                                  case 1782711244:
                                                                     break label61;
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

                                             switch ((int)com.yiyiaddon.m.b.a<"s153juj2tb8c7x","CU4oKz5NN0qY6CYEctkH7DkkaP2+Wf/TerltzXse8JA=",2054925875173489405,-5565826035488224412,9211086595344213207,-4961616937230433925>()) {
                                                case 686384817:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    if (this.e.isEmpty()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1cex82443t0zq","IBTDWE4mX6g+6FUxe4lXdO7A9NbP96HjVjrVk2Xi43c=",3629464518582370210,-2977808459314792067,6989394125857837544,-1944508556477523640>()) {
                                          case 683053447:
                                             return false;
                                          default:
                                             throw null;
                                       }
                                    }

                                    this.L = true;
                                    this.iT = 0;
                                    this.k = null;
                                    this.iS = 0;
                                    return true;
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
                  switch ((int)com.yiyiaddon.m.b.a<"s36b6j82a98avt","ADRo8WjQZekROgXyCwOV+oM6FgWp55XaUgmBulI/ksE=",4650034007046963542,8830000834536838369,4044655428498454550,-9167818813734711526>()) {
                     case 1051800289:
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

   public boolean cj() {
      LocalPlayer var1 = this.H.player;
      if (!this.L) {
         switch ((int)com.yiyiaddon.m.b.a<"s2spduybux0tj9","d1AfO8hL4Y1O9B42r/n/OHkYuNSxRV3sc7s8BShUaiY=",1357941520448871577,6581153704517818907,1670757114685381800,-6799974314136298906>()) {
            case -1569863017:
               return false;
            default:
               throw null;
         }
      } else {
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s10vg41ze5vmt1","UlFvsrB0AGJsbL93+sqRqtoTYCEfo5dibN82862Vk4I=",4854230762400522004,6609321668809726730,7288912791902084380,6677098435674389549>()) {
               case 2068515908:
                  if (this.H.level != null) {
                     label104:
                     switch ((int)com.yiyiaddon.m.b.a<"s3de6og4aj8eg","QpRrhJxKQ9Eza87xIgq1zId4K4qxdQ0zcRLHhsKkEzc=",3680679977933107455,4340881252861785785,7665482883621531106,-2492971531170895895>()) {
                        case 2128335114:
                           if (this.H.gameMode != null) {
                              if (var1.isInWater()) {
                                 label100:
                                 switch ((int)com.yiyiaddon.m.b.a<"sw8dc1qd84sla","Lw9eeHC7OPssMS7mKFMh4X4ohce5aVoUpwqQVFExauM=",-3774970209812470155,-8554351463561714709,-1316925939133364307,-4111611225509244354>()) {
                                    case -2039771169:
                                       if (this.iT < 6) {
                                          if (this.k != null) {
                                             label116:
                                             switch ((int)com.yiyiaddon.m.b.a<"s18zi5k7lbigwb","kiFQFSm6KHuOwbLD8y2xBvSH13xPE6WQ3C1iHf+E0gw=",4414437079307083399,-5465985228466074970,8031395910541380829,388161658731384908>()) {
                                                case -1414251562:
                                                   this.iS++;
                                                   boolean var2 = this.H.level.getBlockState(this.k).isAir();
                                                   boolean var10000;
                                                   if (this.iS > 100) {
                                                      label96:
                                                      switch ((int)com.yiyiaddon.m.b.a<"slys0nd82ohvy","J0zbGFhsf0qqPtz7FcDI/1qk6+C349YqSTDGkRa47Qc=",2225525998221003771,-4785476341321276487,-206599914985971692,5193069767107332530>()) {
                                                         case -1205430949:
                                                            var10000 = true;
                                                            switch ((int)com.yiyiaddon.m.b.a<"sdxafvw5d66y6","ycdxBv1pabydcbNLiMnIoNVADt8/hKFmhYS89h98rV8=",-1414815179064775731,5838600515163487718,-8536443886723413515,6145264409910292780>()) {
                                                               case 1209689364:
                                                                  break label96;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else {
                                                      var10000 = false;
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2iwy5tvjixkov","pWTWLvYsUKYQVoGR0dzNDC6Ddx0cdcaEOOz3uxrAmN0=",-2744967697635952643,-3819102273462233495,1431682600994727835,4986144209512573285>()) {
                                                         case -2085871832:
                                                            break;
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   boolean var3 = var10000;
                                                   if (!var2) {
                                                      label90:
                                                      switch ((int)com.yiyiaddon.m.b.a<"syw24a9jsrh0a","oBnxn4UQR81xEdFlqrLp7cNIL04U3ILL9RtmfOIp+Ew=",-7351315875497841835,-7582192880764931648,-4986673697498208570,-7006663142831709116>()) {
                                                         case 1509325006:
                                                            if (!var3) {
                                                               break label116;
                                                            }

                                                            switch ((int)com.yiyiaddon.m.b.a<"se0mo8nddfzp0","nUzU9++qe4nwJ5yjYtAEdJyYUErGxb023Y8XMY8KH5k=",-4539853661455111463,8237753888783806825,6537050717750034000,2668738201212144742>()) {
                                                               case -339746988:
                                                                  break label90;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   if (var2) {
                                                      label85:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2y3gi3tgk29f7","6edrXqKmOxinB9U0EkqG6Mx3HMKVt9uP7OFCC1ZHdEA=",7079585078815756171,-4380566413530853814,-2973339315116662498,-3455145680958137577>()) {
                                                         case -592578170:
                                                            this.iT++;
                                                            switch ((int)com.yiyiaddon.m.b.a<"s1e8uzdilgy8qd","4JRoJ0Lhg163FMe3OrTTk8E2BQo79lvKO96nli5ESHI=",2121322878261071251,4381735800634604251,-4607067171018023250,-4886737464266281530>()) {
                                                               case -1318341238:
                                                                  break label85;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else {
                                                      this.c
                                                         .K(
                                                            (String)com.yiyiaddon.m.b.a<"s1r1kbj4qkfxwy","ck20F8AmUJuUjO1bvksazNVG8q7nmDHH2fbwWAfmoe63LIT0FOJ8gH5PDXHup5NQ5Es0eALFjVkQtRJi8dI4AyswZLWR+X/we4O8YB13FXTnGS3YBvY=",7014416090711133765,6768850078991078674,4119148064758854065,-2883116670715086063>()
                                                         );
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2k0fxjg0xaudi","a1HXU+Ss1/2F+V6UeIzBWzqpgDvvX80Fg3qvIzx2BU0=",-4953140105665811731,3077466884149079907,-2469410903145059521,4817463932951485339>()) {
                                                         case 1270547229:
                                                            break;
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   this.fc();
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1cu5e0vfmfbbp","wCDrfoBoCdFKPr9+w5fzFhMw+olJEqeaGCCtUDF2xuM=",-6804392093493952454,-4810081176182712130,5001613419551268965,-1546002764011572378>()) {
                                                      case 182954521:
                                                         break label116;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          if (this.k == null) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3f4djqbvwch29","pcLq1XJ6CKXflv/faXXx0nf9e3yKpgKvnIwuFRCwaF4=",3669290045249056107,-4455520459371096943,6655864363513042633,1351948494846242520>()) {
                                                case -918057014:
                                                   if (!this.a(var1)) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"smxy32x44lanv","OvFD81ikmc0obkYFG5X+mi2Uu2ptcbFnSYHVNSOWW4w=",2470822016232730791,-6873338969370432765,8531917872689649718,-4092557304593352354>()) {
                                                         case -345488688:
                                                            this.fb();
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

                                          Direction var4 = a(var1, this.k);
                                          if (this.iS == 0) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1do9fomeymgul","ugOLs2aX/iKmFSBX1IynCAxkQC7zDPya7M0lgiUUBa8=",6495632308111340251,-9032167367657433738,-2304394521451441353,-3711729734639625469>()) {
                                                case -168811176:
                                                   this.H.gameMode.startDestroyBlock(this.k, var4);
                                                   switch ((int)com.yiyiaddon.m.b.a<"swsj5ecjvc0wi","xcvV/vEeIWXg9YP2jAzR3PLBzLehDM91w/rvgD0OwFQ=",-1759863290486798101,8783208865415198579,-963089693813045423,-5375825695261559092>()) {
                                                      case -761098782:
                                                         return true;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             this.H.gameMode.continueDestroyBlock(this.k, var4);
                                             switch ((int)com.yiyiaddon.m.b.a<"s3uztj1fxqkda1","Om8j5wkrgw8dl6rvcuC+YKht4lKNAhVxue0Jt+HF6s8=",8834370466562822420,-4959373191372800417,-14974709560183468,4432754661823709306>()) {
                                                case 1776618665:
                                                   return true;
                                                default:
                                                   throw null;
                                             }
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s2gtqnqgxkbg9u","eGOV/pEFCEKtaMixddmF+QjPKup0l6A/OZtEv6D7/6g=",-8951536129241204353,8148420920514234910,-6405245784617454320,6249461383984313309>()) {
                                          case -855525427:
                                             break label100;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              this.fb();
                              return false;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s31y4mrss7c0ym","aukxieMAk6FvoovipU89ahiFby9/GGKghaxHsvBXV20=",-6076045769986523620,-7871010944849904465,-4500115859575623124,5249542953345214538>()) {
                              case 1039568673:
                                 break label104;
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

         this.f();
         return false;
      }
   }

   public void f() {
      if (this.L) {
         switch ((int)com.yiyiaddon.m.b.a<"s8pe6kyqxo5l9","SUnafHWQTK1/Zo7dUS0gBDH3IJNx20SGEiKF+COXQKc=",-2229956066764377940,-4294776281322856695,6718389424086227787,1032568849214723339>()) {
            case 705132647:
               if (this.H.gameMode != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s20a5wflval9qf","9WDmr/3jRmPD7jD9VLQ4KEqY0BBDIveZpira1Lul5BQ=",-6275272048390917215,-4013355541145941486,-3334495857875029526,4219416571926959060>()) {
                     case 1740508901:
                        if (this.k != null) {
                           label19:
                           switch ((int)com.yiyiaddon.m.b.a<"s2b3l0frcz02a5","JXXWF7EwyM53ew2a40JAnDpCQHX0zRQP7FvA0bPiOsI=",8957900780640881910,294591591088195769,-1218075325798726700,755097628752230899>()) {
                              case 1213840027:
                                 this.H.gameMode.stopDestroyBlock();
                                 switch ((int)com.yiyiaddon.m.b.a<"s1mgudkcbjzpbt","NvaIxOGy441YyZbpZ4WvMiYq7pDlUo3dT/XFQ9sDDIc=",6781003348644125223,4313482168846363719,6404851162572904748,7238002169359980097>()) {
                                    case 231793231:
                                       break label19;
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

      this.L = false;
      this.e.clear();
      this.k = null;
      this.iS = 0;
      this.iT = 0;
   }

   private void fb() {
      if (this.H.gameMode != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sn31zxc43emdf","MH05ztYYyCNj/+P04GKxh7AlYi5O3Ks3ct82+NOu4qs=",-149446610204957885,-5432847290064986276,-7911657082564740943,6741240476902353038>()) {
            case 1558792344:
               if (this.k != null) {
                  label16:
                  switch ((int)com.yiyiaddon.m.b.a<"s2xn2zqnmmi18t","G/4bjxQEr5UTMRbEpFnhiZJkQ5+jtU72AbLTR+pAO94=",5558956977095923746,-7968267846363081866,6367083877960895743,2274991693071114898>()) {
                     case -960529376:
                        this.H.gameMode.stopDestroyBlock();
                        switch ((int)com.yiyiaddon.m.b.a<"sgk1got8mie2t","e4Ayvm6RfrL+dwmXk3gmAxYDRosPMlkiQEbckq0NQaY=",-5709228801993669149,983836624723257751,-3212540539894549670,-6469615964180880609>()) {
                           case -1942121950:
                              break label16;
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

      this.L = false;
      this.e.clear();
      this.k = null;
      this.iS = 0;
   }

   private void fc() {
      this.k = null;
      this.iS = 0;
   }

   private boolean a(LocalPlayer var1) {
      while (!this.e.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2qoukx8kypara","BcCQHQ+zY65AKuiVptdpAH2E7NFdDpT05T2JPiu+0y8=",-8619425686163699365,-1838256438666361516,6771526715367965396,4407313046317282084>()) {
            case 1640644820:
               BlockPos var2 = this.e.poll();
               if (!this.a(var1, var2)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2xlgd0jhjgtj3","hhEIKEQFA4WCrEhNptFMs4HXiipKLeVFT6A/eDzLQ9Y=",4460658573791067245,7360543331583026840,58723640257908946,4734060925414497279>()) {
                     case 162735616:
                        switch ((int)com.yiyiaddon.m.b.a<"seg1v12x0nfva","Ngaf28NEpZ4KoJHvyR21pS6sBVJWc8NJcCGJdpvZYRs=",-572518937355826110,-6764174568208320080,-3619678993028280564,-1725426694527204159>()) {
                           case -119586080:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               this.k = var2;
               this.iS = 0;
               return true;
            default:
               throw null;
         }
      }

      return false;
   }

   private boolean a(LocalPlayer var1, BlockPos var2) {
      if (this.H.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2o21jf2p1zjpd","wAe3prIs9Anoz/idpxZavyX+9Rfh4sxF8JcI1ctS9aM=",3767735341682041665,4713100969551421460,990118859219287880,6644934571909099728>()) {
            case -2005782854:
               return false;
            default:
               throw null;
         }
      } else {
         BlockState var3 = this.H.level.getBlockState(var2);
         if (!var3.isAir()) {
            switch ((int)com.yiyiaddon.m.b.a<"s6d03gef06hy3","/PDZSxH4KRLwMe8FeowQ0mjGfvWhP10LW4dOp/hWj2M=",-2906682883977890813,5076552826037627721,-8821201073477411645,-8181888712753705291>()) {
               case 1278849175:
                  if (var3.getFluidState().isEmpty()) {
                     if (var3.getBlock().defaultDestroyTime() < 0.0F) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1dwi5g1glkl1","59zYuluZ3jRzUQpZueGmXeV9wX3ZW4Tv+z/Rd0h+kFw=",-2375084119678551428,-1388470598946396119,-6945145656314265710,6004592119066158159>()) {
                           case 1861428076:
                              return false;
                           default:
                              throw null;
                        }
                     } else {
                        if (var3.hasBlockEntity()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s23xgxv07672xf","js13UE/njjQM/5+zPdzGa6v7w9rKNQ1yKqF87aNkkOc=",-962009952314451317,-1463317643450484433,-8657922737034571042,-4851093119909305168>()) {
                              case 1291655035:
                                 return false;
                              default:
                                 throw null;
                           }
                        }

                        return var1.isWithinBlockInteractionRange(var2, 1.0);
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s3b2tg1xdzq5iy","js65sm7c+auuY8OLRDgDKpecBnRuXfJYP+Nx/k9/Y0s=",7012317300156812850,4728954977460089257,-6796269317503996156,-1457403370736861723>()) {
                        case 350650517:
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
   }

   private static Direction a(LocalPlayer var0, BlockPos var1) {
      return Direction.getNearest(var1.subtract(var0.blockPosition()), Direction.UP);
   }
}
