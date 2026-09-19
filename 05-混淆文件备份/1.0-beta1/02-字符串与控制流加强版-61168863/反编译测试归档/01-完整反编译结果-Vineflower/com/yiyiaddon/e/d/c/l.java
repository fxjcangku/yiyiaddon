package com.yiyiaddon.e.d.c;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public final class l {
   private final Minecraft r;
   private final com.yiyiaddon.e.d.a.a g;
   private final l.a a;
   private boolean ax;
   private int cP = -1;
   private int cC;
   private int cQ;
   private boolean ay;
   private boolean az;
   private boolean aA;
   private ClientLevel b;
   private String fq = (String)com.yiyiaddon.m.b.a<"s1g0q5idglkips","787NJgbczToiImCEd6hVQJvKdLigCev2XhhTrA==",-2229076745553340208,-3463650157361901407,755955219454506144,8805308605247822160>();

   public l(Minecraft var1, com.yiyiaddon.e.d.a.a var2, l.a var3) {
      this.r = var1;
      this.g = var2;
      this.a = var3;
   }

   public boolean ab() {
      return this.ax;
   }

   public boolean at() {
      return this.ay;
   }

   public boolean au() {
      return this.aA;
   }

   public void bk() {
      this.aA = false;
   }

   public int F() {
      return this.cQ;
   }

   public void aR() {
      if (this.g.al) {
         switch ((int)com.yiyiaddon.m.b.a<"s2naivxy2061vm","blYeGga/cCIVwQjQ/Vrp/YlbffyO4mkvuZC7HXlk41A=",-3276418388676298601,-3230421624359104194,-9140823992904195186,5372251054888985274>()) {
            case -1313518871:
               if (this.g.a != com.yiyiaddon.e.d.b.e.DIRECT) {
                  if (this.g.a == com.yiyiaddon.e.d.b.e.LEYUAN_CUSTOM) {
                     switch ((int)com.yiyiaddon.m.b.a<"sn2p62cwacbp2","JuoxvH1oveKo+/m8c4xRwAQmWwaI/hIDGDr4OSMIwho=",6310619849498198710,245606990894713256,-4934738664699643303,2391391103364607931>()) {
                        case -1163637886:
                           return;
                        default:
                           throw null;
                     }
                  } else if (this.g.w.isEmpty()) {
                     switch ((int)com.yiyiaddon.m.b.a<"srvdwso9mx05d","UYhMyMtbeogo3AmWvVZ2O/kzLSxsc5pXiEJP47Dgvbk=",6301542437081399160,-411411039474554209,9074429573534380942,7334507292911956781>()) {
                        case 2104321244:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     this.ax = true;
                     this.cP = -1;
                     this.cC = this.g.cj;
                     this.cQ = 0;
                     this.b = this.r.level;
                     String var10001;
                     if (this.r.level == null) {
                        label32:
                        switch ((int)com.yiyiaddon.m.b.a<"s2bqz5fnu8u77v","qnv+y/UfPF2X2JAAAc8nmW9riItlYHh7vhMCDOFz0hw=",3132735999086018011,8735064540405952808,5415958401381484722,-8167643500684632094>()) {
                           case 1169442199:
                              var10001 = (String)com.yiyiaddon.m.b.a<"s1g0q5idglkips","787NJgbczToiImCEd6hVQJvKdLigCev2XhhTrA==",-2229076745553340208,-3463650157361901407,755955219454506144,8805308605247822160>();
                              switch ((int)com.yiyiaddon.m.b.a<"s10zu4kunbqfx2","Zr3LprOuYGBrcAg2O91/jucjtSzpYKQrnVhlOzXoxUQ=",-598991563104293579,-4765226997554909617,714372662143218013,4256234287685433088>()) {
                                 case 16832682:
                                    break label32;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        var10001 = this.r.level.dimension().identifier().toString();
                        switch ((int)com.yiyiaddon.m.b.a<"sdfoj1di3outx","AgpiRTEPbg8WJ7mIha/5+lF9i77Ij8gom2dMHZY+DMU=",-3416256982097114884,-1292222869525954775,408708309116506411,2921989506211801297>()) {
                           case 1022594324:
                              break;
                           default:
                              throw null;
                        }
                     }

                     this.fq = var10001;
                     this.az = false;
                     this.a
                        .u(
                           (String)com.yiyiaddon.m.b.a<"s1l4hddhex39ij","yKTXRdMogw2qkb7eBW264/O9prX2sbjSvprR25DbXHDp0vz63BwJ6T00MvSu1pgmd3xAyShpGcequtrAArCtjQ==",-5678721655695249159,2785914026506998717,2437576761460844966,7831780664617302102>()
                        );
                     return;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1msugoexg4jrs","8NQmefOj4dZDReg0VTe5Gq061S1uiaGMLk3DlC46eSE=",-8169420703623309746,7999593484808832538,7965172023527894979,91612856721247729>()) {
                     case -852981949:
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

   public void ag() {
      if (!this.ax) {
         switch ((int)com.yiyiaddon.m.b.a<"snhfmzmxt18xl","+QhW0jnHBzQOw7Dc+AYh46Swi+2r8bfN7D82WpF4358=",6948980926639354886,6329908295149947837,7470929221913624428,8535163004326227673>()) {
            case 739761779:
               if (!this.ay) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1k7u2zjl99y2l","4/bxw9Op+nl7rvp00sFnVXYJOfzqPxWvSTdeg+jwQ/s=",8264768280689166943,-788529502944328753,3225512135192888531,3618683180154065725>()) {
                     case -449593770:
                        if (!this.aA) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2ff9s8s2pbvg4","xOZSBEIbVL1l61y6GQGS16cpglpLiMB5dnIpguEz3ps=",875573692636640697,3948922325263212189,-8338604383919065887,6309741121642594276>()) {
                              case 1699701288:
                                 return;
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

      this.ax = false;
      this.cP = -1;
      this.cC = 0;
      this.cQ = 0;
      this.ay = false;
      this.az = false;
      this.aA = false;
   }

   public void f() {
      this.ax = false;
      this.cP = -1;
      this.cC = 0;
      this.cQ = 0;
      this.ay = false;
      this.az = false;
      this.aA = false;
      this.b = null;
      this.fq = (String)com.yiyiaddon.m.b.a<"s1g0q5idglkips","787NJgbczToiImCEd6hVQJvKdLigCev2XhhTrA==",-2229076745553340208,-3463650157361901407,755955219454506144,8805308605247822160>();
   }

   public void ae() {
      if (this.ax) {
         switch ((int)com.yiyiaddon.m.b.a<"s20dyabhwf3o2g","6c7UdGAhYKBGviG10542z3dmZY/HfK6npkfAfLx/cGg=",8972268025707340433,5742783476621303226,1071752966182612003,-5654423382852325383>()) {
            case -1877548433:
               if (this.g.X()) {
                  if (++this.cQ >= this.g.ck) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3mkrg3lyg34xg","6gCCho063wLjdCRRANpoe506+F3oV78KiQfkOMwCVEo=",5272808215415122993,1399095667535140063,-8590961975439164119,1142782216752184839>()) {
                        case -2066732543:
                           this.a
                              .u(
                                 (String)com.yiyiaddon.m.b.a<"s3swz1ptvxftip","R9SV12W2ry9W+OQIOe1o7gy45y9UlvrdRLVAAtuvPpSL3jxdZmxb1tZAXvWrjHPwwO4qX6NFOT9kYq760cQ=",-2994698233950537584,6009597384398108617,4753239769001171419,-3459313018982370331>()
                              );
                           this.ax = false;
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     if (this.cC > 0) {
                        switch ((int)com.yiyiaddon.m.b.a<"s6qf438ir16aw","82QZGyw9ZItK6duqvpiEEYO6lnJc5CvENYPu1xpUSoQ=",-5297830356681208325,6070265177867387815,-245546027767243546,3405120050091655825>()) {
                           case -1497806061:
                              if (--this.cC > 0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"swu3ns5sqcnn0","4AuwrrafuPQdgy/5gdK4INEtVZWPYdIqnxXdEhep12I=",-3937360881890961502,-5784288025880111798,7832682097870683963,-4037167127926106207>()) {
                                    case -2042007435:
                                       return;
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           default:
                              throw null;
                        }
                     }

                     if (this.cP < 0) {
                        switch ((int)com.yiyiaddon.m.b.a<"s82a0tbkwr2kw","E1/fIkgLdvF8P8XAdVivqr0Rs21JkGXJ/MzfVC2wnBw=",-1327806389211267526,-1616299167752048397,2809536617528532330,3832953973169820738>()) {
                           case 2037024884:
                              int var7 = this.A();
                              if (var7 >= 0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2j1oi853md4e6","/sRQ7jV08Jw09VZ5qWLW38Q7XvbBHDc89jygdd1EmX0=",-1169117599293692184,364280687253723941,-4985032239506032611,1237499528073688657>()) {
                                    case -939561211:
                                       if (this.r.gameMode != null) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s218c2n3hgt8yx","/g5lNQbntsCQf4+J1QrsLwPvJzn4GivToM53bnPolzc=",265101081152428084,4595197219228259202,2349600377761753279,-1962521902182423380>()) {
                                             case 1993778615:
                                                if (this.r.player != null) {
                                                   this.r.player.getInventory().setSelectedSlot(var7);
                                                   this.r.gameMode.useItem(this.r.player, InteractionHand.MAIN_HAND);
                                                   this.cP = 0;
                                                   this.cC = this.g.cj;
                                                   this.cQ = 0;
                                                   this.a
                                                      .u(
                                                         (String)com.yiyiaddon.m.b.a<"s1pqp8nz39kuy3","t26k0BXrxgS9+uWeUV8sC2aGRa7VRcu1RXs5L+/95qFk1M8LpfnW+q2dsCMM7IY5TKSYx+qRAPP4T5DFWnLxkw==",5708846750916493049,-4513370126089329553,-360772614034080975,3154313645876019828>()
                                                      );
                                                   return;
                                                }

                                                switch ((int)com.yiyiaddon.m.b.a<"s1q03qunsqhsq0","8OMX2ihAnjwLGsOk2G0qKX92ssgxpBJYFJpadPUwp6Y=",-7669932713293238725,1657602073749462156,6694434646888546490,5837415792670039029>()) {
                                                   case 768532277:
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
                           default:
                              throw null;
                        }
                     } else if (this.cP >= this.g.w.size()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s6txmq45t6s3w","ztH+f4Yz1KkZSmx98j69rHXHK7fb6Un6wyEgFzESF7k=",-2796700034993862989,8683205493548947621,47787584726863127,-656532443021009064>()) {
                           case -849601842:
                              this.ax = false;
                              this.ay = true;
                              this.cQ = 0;
                              this.a
                                 .u(
                                    (String)com.yiyiaddon.m.b.a<"s2eyqj0mensf0c","jfJgWc4YVlIywqviVBDE1+B23tFTKFfUa0/JQuZS9uwkMoTUso5KjbgcaA5/YNKtXDIEtTxV3E68XAA7",-2431034495456159891,-4196450004359748709,-3607576436904223612,-4638040845591767567>()
                                 );
                              return;
                           default:
                              throw null;
                        }
                     } else {
                        Screen var2 = this.r.screen;
                        if (var2 instanceof AbstractContainerScreen) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2cc0vafbn9awv","oqgZHafuSChQJpTNrOO7LMrLzT3lcbbADBMfGp8pio4=",1432355541258081624,8504822323073855620,-854584878537462916,6252884710209280406>()) {
                              case 1290602219:
                                 AbstractContainerScreen var1 = (AbstractContainerScreen)var2;
                                 if (this.r.gameMode != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2k6r0hqp5k7tz","5E1kCij6iGF8SnEEHy+gObV3sQNbYyq34TFDmlwdbuI=",-5842353844670799079,6441109222155220229,2886696167170741896,-656107154763884886>()) {
                                       case 284845196:
                                          if (this.r.player != null) {
                                             String var8 = this.g.w.get(this.cP).trim();
                                             if (var8.isEmpty()) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s2g4wq87envlg8","mmSV3/Ri3KLIicMrjpQyQoYgExwx4494k3l5y9IbTXg=",-7359861848413090848,-4548179322259843302,2581752702464841700,-1391406122071433611>()) {
                                                   case 681547832:
                                                      this.cP++;
                                                      return;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             AbstractContainerMenu var3 = var1.getMenu();
                                             int var4 = 0;
                                             switch ((int)com.yiyiaddon.m.b.a<"ska2rahhobctm","Krh9ZpWi95P/7Cx/MnCvzD3NkOjF95K1vuRKp0Ygk7Y=",-2060106691087349191,-4212033257998558385,-8141576163974709931,1137780530575365459>()) {
                                                case 1228295475:
                                                   while (var4 < var3.slots.size()) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s12b3prf7n3j1o","n9pGlqMeDI9kme7xIZzOsDjI+1jghRIBWq+832aS1sU=",-1476872064013867580,-4676489322365835662,-3482292922217364278,-6096177972255698382>()) {
                                                         case 1075132739:
                                                            Slot var5 = var3.getSlot(var4);
                                                            if (var5.container != this.r.player.getInventory()) {
                                                               label120:
                                                               switch ((int)com.yiyiaddon.m.b.a<"s23392zw0zvj31","txQa2i8dDQBfOSGPwaH8DkBYEdbP9wFwxnyR0L0jL5E=",-4210588645159781487,2957285377591362248,-2674952310521057696,-2008271540423931124>()) {
                                                                  case 1679611020:
                                                                     if (k.a(var5.getItem(), var8)) {
                                                                        boolean var10000;
                                                                        if (this.cP + 1 >= this.g.w.size()) {
                                                                           label110:
                                                                           switch ((int)com.yiyiaddon.m.b.a<"si87frskh0su6","g56+TKIml7IjzJyUWrlx9nx0GwguBD/PllqGAcHbw0M=",6398521958443662333,-2432846960428044024,-3853523666922194934,-7896554274082911864>()) {
                                                                              case 457397474:
                                                                                 var10000 = true;
                                                                                 switch ((int)com.yiyiaddon.m.b.a<"s17l9s4aeif39g","rnFRyywwe9pXnFgAfHykARJ6cXb0VJ1kR45qHK229Kg=",2589041479962931451,-4015799879623282658,-3344119836029053744,729717720919347023>()) {
                                                                                    case -278063020:
                                                                                       break label110;
                                                                                    default:
                                                                                       throw null;
                                                                                 }
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        } else {
                                                                           var10000 = false;
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s3jnm3j4z8yl0c","LJieSwVwVRVBoktzywGG3ZiSL0anuxHez830LQH2fqE=",3388005576849324547,-5051901938981547610,-8505550388877216761,3131349386001205899>()) {
                                                                              case -1290795250:
                                                                                 break;
                                                                              default:
                                                                                 throw null;
                                                                           }
                                                                        }

                                                                        boolean var6 = var10000;
                                                                        this.r
                                                                           .gameMode
                                                                           .handleContainerInput(
                                                                              var3.containerId, var4, 0, ContainerInput.PICKUP, this.r.player
                                                                           );
                                                                        this.cP++;
                                                                        if (var6) {
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s8j1ie3de9bt3","4uN8s3lYgxCMLyCEfoGtu3YwXMlmoDQtwaPI+E0s9vQ=",-2275745545255163119,-4923725767101538118,6164839388188618010,-302716063543399784>()) {
                                                                              case 241919909:
                                                                                 if (this.g.a == com.yiyiaddon.e.d.b.e.SUBSERVER_NETWORK) {
                                                                                    label103:
                                                                                    switch ((int)com.yiyiaddon.m.b.a<"svvn6ksuqhr8h","yYboJ8AyBMxn/KwZMo63N3OQ4OXHqFbwd+aLsIN/hRM=",-8612398676122265488,8663696719379257851,840779381697548544,1528687910229969013>()) {
                                                                                       case -429484982:
                                                                                          this.aA = true;
                                                                                          switch ((int)com.yiyiaddon.m.b.a<"s70o4pokyihn2","SGVcMabc/FCORj4bGgNnDwQ8o8pfI/m79gsXyp9sLjU=",-6466504305460667959,-5869967352700822436,-8323774885796038360,3917673295262660571>()) {
                                                                                             case -663129179:
                                                                                                break label103;
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

                                                                        this.cC = this.g.cj;
                                                                        this.cQ = 0;
                                                                        this.a.u(this.cP + var8);
                                                                        return;
                                                                     }

                                                                     switch ((int)com.yiyiaddon.m.b.a<"slym8dgbk0bde","bdf7JzxcXQs3tg3CLIFnufSMvlRedOmXKQsSvG/5Tmc=",-1526734247397354778,-7021981168469083728,-319364955257002048,1505154783544325447>()) {
                                                                        case 1477166021:
                                                                           switch ((int)com.yiyiaddon.m.b.a<"sm3lg46uesn2e","EtN3FjQrB6ut8y94iYNX2pdsahy+tdposBCc11A3oeM=",-544863771956296587,7388745566497545973,8001923874220145264,8107972975699596159>()) {
                                                                              case 1538664999:
                                                                                 break label120;
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

                                                            var4++;
                                                            switch ((int)com.yiyiaddon.m.b.a<"srr9uv8unfllq","SZ8sAn9dgx/1Nq4WeyVwE3FD3i7ooB35IR0ZYMkhxdI=",-5408415038192590552,-5468274659059073178,-1344795207770401973,7219199510887759484>()) {
                                                               case -819186593:
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

                                          switch ((int)com.yiyiaddon.m.b.a<"svl8nf5rfe6gl","HXWr+QQhrixtJcd25EG8KoCoowsFZY4NMmKpFp/32is=",4744598057473375155,5936878875821201985,2775642398372808610,6692918341684773818>()) {
                                             case -95649371:
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
                  switch ((int)com.yiyiaddon.m.b.a<"ssf5mmbhndv8u","ayIcXUZ+YTn2dEoLSMZJGiPDD4p4yEJcDW+F8cMeKJY=",-235510120335803146,1882972027935610332,6272163987385578339,-2916033320178661657>()) {
                     case -1412511474:
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

   public void bl() {
      if (this.ay) {
         switch ((int)com.yiyiaddon.m.b.a<"s1qpnent2wrk8g","YQ990hjijGo5Nogx+vT4TW3ddQBQ3oJmm0f6MbHah/k=",6179643556825162177,74474239680200954,2958822012329862724,-2153860523004475188>()) {
            case -1218820226:
               if (this.g.al) {
                  if (++this.cQ >= this.g.ck) {
                     switch ((int)com.yiyiaddon.m.b.a<"s32cjp9ii9b6jc","kBtMlJl7iXo2P9DKjnp0xW/hYKNEQF8U7fWbosKm/Ic=",1714052900620651942,7888978540481459163,-6767195646236821965,-3149254407832496524>()) {
                        case -337149622:
                           this.ay = false;
                           this.a
                              .u(
                                 (String)com.yiyiaddon.m.b.a<"s2lfu4376otrnr","qJxNnP0g/69q+9o1CDPvx8RkPWCbYqNwXn+tu1/JH+a6/7TiJ7ocxzdqGiOGbVrXQbCcgBCA+6vpNMwYm3xtYb/H",-1304290386005642795,-8901165291251982760,-3697799226000820219,-476786388817506004>()
                              );
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     ClientLevel var1 = this.r.level;
                     if (var1 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s9e7htr5br08q","eSYnpl3DOsrilWtVYKR3DU2CkB1uk+VT73NMbZWMj3U=",-2646123336124339421,-4538171253111456561,-5043586855325094382,7330228281005022100>()) {
                           case 192698468:
                              return;
                           default:
                              throw null;
                        }
                     } else {
                        label88: {
                           String var2 = var1.dimension().identifier().toString();
                           if (var1 == this.b) {
                              label76:
                              switch ((int)com.yiyiaddon.m.b.a<"s31idbbx0vgojh","ry1mKrVATDEZJvJjSNWH/n36SUb/wmHZf8qw4JphPZE=",-2610961409786983511,247399605544509697,3426233157857936647,-5803622845191461190>()) {
                                 case -793074276:
                                    if (var2.equals(this.fq)) {
                                       break label88;
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"sikfv676aac61","Z18g/CCjSR/9M74E5FDFoLCOcYsOSL53krRhAj3B1xc=",7667587950659865888,438797995516233428,1135464797933937879,-3391769460853840040>()) {
                                       case -625861724:
                                          break label76;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           this.az = true;
                           switch ((int)com.yiyiaddon.m.b.a<"s1v3zj6it0n630","Up8namvvRVIj9HfEU0K5e8fdLR2vdNMo8Vmkz2voBdA=",4570755479539740419,4676013305965676156,-5472778122497346988,-8065873617860164858>()) {
                              case 1039130922:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        String var3 = k.b(this.r, this.g.x);
                        if (var3 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1b2pnnbtesyti","9X+UbdJSeKkk+uD3HwYX7vqKaUp2DyWTA2AtfkiK6YU=",6720490887682167913,-5549117967442012776,1551282804476741930,-3043271074245188042>()) {
                              case -204452086:
                                 this.B(var3 + "");
                                 return;
                              default:
                                 throw null;
                           }
                        } else {
                           if (!this.g.am) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3i9l9tas53zgb","EJaoc77CzEFBD3y18ngwCR2AAHKzxI/85QdAlXEZVS0=",-5811622298093757220,-6563232214555491023,-1037979129941097828,5304644499518526302>()) {
                                 case 1753233122:
                                    if (this.az) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2jdhti3roml1l","b6vuucidbJtqQjPYcYXtCr4knRqh0gRtlU7QYr0XIm8=",-1076937769173230577,-2742262782518282543,-8926922086745796786,5644687720667432612>()) {
                                          case -1161582995:
                                             if (!this.ao()) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s25mzsiyhv5f7o","WfMTKrU94Q+2spsm8W0quGauIyq8/6+QqXZMIHo7KLc=",6688077449669575262,-7130314938036500964,3188160250694458652,7876181115497463603>()) {
                                                   case 658693589:
                                                      String var10001;
                                                      if (this.g.a == com.yiyiaddon.e.d.b.e.MENU_TRANSFER) {
                                                         label58:
                                                         switch ((int)com.yiyiaddon.m.b.a<"s14ju6s5wogew5","YFk4cxaYryk12aDrrrwVc1IUxCF+ppcQJFtx/Ad6/L4=",-9191204736622765930,206398285517539577,4698503528009738634,-5908276044033391087>()) {
                                                            case -1384851366:
                                                               var10001 = (String)com.yiyiaddon.m.b.a<"s1r0s9uactpeps","278sVfzX5m9/8CwJHO75u3iYqSwTf1WHRTTS7k7MX20Z6wujxM4wL+qS4HnvbCsAjNs=",-8747199803476601396,8322562162537161719,-5080426439149517341,-3807315999236481578>();
                                                               switch ((int)com.yiyiaddon.m.b.a<"s11hue973p1pm5","vL+LEHUpsG2i7mGf+l8XE1FjEJFrT9gpH4JphMJzxqQ=",1198758130445952457,9190910694998078627,-6580138655659252345,-978979283461362468>()) {
                                                                  case -487434149:
                                                                     break label58;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      } else {
                                                         var10001 = (String)com.yiyiaddon.m.b.a<"s4yzqaq2ehau4","z+qgTCzVcFmXGcrhN6TFlv7PEY7siNCYMn1wM0UpXzpvo5ytnl6Tz+ifGWE=",-6962077761055602594,7908353687371419076,-4328581913429942860,-7307215452706155763>();
                                                         switch ((int)com.yiyiaddon.m.b.a<"sk0nr0n7rwjwk","fA0G6xazwhQbGbP87m8jfvALmLzd0+p4f6wImkkyAKA=",8765689191553992461,2242389881928121135,1437732021484694977,2604933348059106227>()) {
                                                            case 383365740:
                                                               break;
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      this.B(var10001);
                                                      switch ((int)com.yiyiaddon.m.b.a<"s11dxfj4f8njwd","3264ufJd4xC+Y9tsUKaIhLntIOm++FPdkIFGL1zSOB8=",-4800166189835748093,6073455166892427298,-136772246029942982,4390645136853308672>()) {
                                                         case 1204274164:
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
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           return;
                        }
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3fx0a1dm5py9u","9CQw8eflbJeiFP6/WGyLrBFnqRBqceUOe7HCrUWqACY=",5946024253091878127,-5785470180426545514,2127867897856962957,2794548246071229682>()) {
                     case 709132157:
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

   public void bm() {
      this.ax = false;
      this.az = true;
   }

   public void bn() {
      if (this.ay) {
         switch ((int)com.yiyiaddon.m.b.a<"sechzzh6fzl7z","mGhrWiGwEjpAt4L63wOETVa9Jd8NDXPgFfDTp1b+3wA=",-2943462520456847483,-4114809983807053829,-5252482872300528345,-5900703638260599222>()) {
            case -1425008679:
               if (this.g.a == com.yiyiaddon.e.d.b.e.SUBSERVER_NETWORK) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3erfhbf4vvrzu","Arlfu6O1yb4e4hLjWaT6uDT4hvXaRDe5UzXkY4lsy3w=",4026346470973268714,6767709424174239117,4124354349353538758,7546382827680391821>()) {
                     case 2128449774:
                        this.az = true;
                        switch ((int)com.yiyiaddon.m.b.a<"s1lialrw2udp3d","f+I9wUiim5xibDuVvUfmID67DBQJQVzqgFynHVk2OHQ=",5643477868684238379,-1799373368872552930,7136225405533071461,-7744483906012309646>()) {
                           case 113172083:
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
   }

   private void B(String var1) {
      this.ay = false;
      this.ax = false;
      this.a.E(var1);
   }

   private int A() {
      if (this.r.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3fnsrqfwb43y5","KaKe3EsPW9wpBif7DcH5iNKIH/BCbQso2/DSJ2/xtms=",1539855494425023039,1318736563407771256,-8663266471785920677,-829741516877079741>()) {
            case 2008174919:
               return -1;
            default:
               throw null;
         }
      } else {
         int var1 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s1hc9bbkgwy12j","vYqtCGgqBgtPkV5IzJeVJ0ZKvhSOmYOIiuyaI19d+E8=",-5618340863355951924,-1992194962613756916,-453837538137611198,-4368575725503117322>()) {
            case -2086722875:
               while (var1 < 9) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2reu2q8pqe0ic","xlu5FYXbKS7BMtDk614RkwNARzllgdM7B0/PqRgM65o=",7129163447369474237,7070427085856321844,7913641228528986931,3519878673619500914>()) {
                     case -701230851:
                        ItemStack var2 = this.r.player.getInventory().getItem(var1);
                        if (var2.is(this.g.d())) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2p7zidwjwrg6a","KY9+sT1RSnV+F4673JDeR8QojEIn5JMYNqYSc/bETmM=",-7803695330333915469,2883844454339799100,-6064347983930427408,-2024028023706604335>()) {
                              case -18457531:
                                 return var1;
                              default:
                                 throw null;
                           }
                        }

                        Iterator var3 = this.g.v.iterator();
                        switch ((int)com.yiyiaddon.m.b.a<"s1guohxx3a4srm","4wtc4iI8ISbj0X5CkPKk0IUa5aDii7Re7gUKBqPIH5A=",1270135360961199496,3856163499640989010,2939790433818762722,-9210437848283880790>()) {
                           case -2077272321:
                              while (var3.hasNext()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1g0mskj4rlo8y","Tm7CGsjRFJO1OsvuAZfd2WLPGS2tt38biW/shALyBs8=",6840258659844718818,857118329334531847,-8777484435764743061,-4752576480270621261>()) {
                                    case 1575501467:
                                       String var4 = (String)var3.next();
                                       if (k.a(var2, var4)) {
                                          switch ((int)com.yiyiaddon.m.b.a<"spcqw0riuhgeh","K4jqpoNvR4CJ6+MXzofy9cES3mpS+gMXjf+ExYoDn2s=",-4553509880637408203,-3676249275413868009,7503820480991001890,-4994455300312342970>()) {
                                             case 1203449467:
                                                return var1;
                                             default:
                                                throw null;
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"spyuvrvfry8ma","JX0vDO4C80lq8e2yt30VpfhcrreaI7ltYXmKIsL8R2w=",4106214404319666321,172608126698518874,7081286320400140158,5148904971330048672>()) {
                                          case 652808894:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var1++;
                              switch ((int)com.yiyiaddon.m.b.a<"s2cd14fs2ec11k","dGb7tMFwvArk4zjFQPbStZeLOoAu+F5EAQ2JIHJukEs=",677191662496237131,1220006745341232321,8654795568897418923,3846269657157215324>()) {
                                 case -1752835886:
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

               return -1;
            default:
               throw null;
         }
      }
   }

   private boolean ao() {
      if (this.r.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s16p3tvn2kkvpo","eJUvO6c7kZ5TWsZCTpC+wv+U6PhmOHY6rlxKqjolMK4=",-5544701074868783551,717651743659116768,7696827018697853827,-3444017889661410483>()) {
            case 1356507097:
               if (this.r.player.containerMenu != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1qlf5j9acbhbh","Mj6aDOiqLK/TYzcKgS+ACVn3q3Aq09T1/BhthntOG/c=",3953693620760950646,-1174964778499341998,1073953283657377438,-2112985182749522478>()) {
                     case -1197471145:
                        if (this.r.player.containerMenu != this.r.player.inventoryMenu) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1lgslb247c9iu","K4mhHkV3MvHmQVPLP2WMYUl7eKyIOr0aJ2i21ztMj0E=",4054509837204861109,-1121780009962480193,1822994603759850509,-4918234500186556806>()) {
                              case 834836770:
                                 switch ((int)com.yiyiaddon.m.b.a<"s1kzeljqahjjee","EMVe1B23W87akXjogLfeejokUMPEkOT4Yz7N2r+CH6g=",3873143528691181887,-7826212807670308263,6160050240386974903,-1221725027563580543>()) {
                                    case -1048511476:
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

      switch ((int)com.yiyiaddon.m.b.a<"stgxckenh36di","Nhf668Vvya/k8IPLNGrB+JdOrC8TwZUFShOUs9F/IPI=",2480631244545807555,-8952370968286376761,-3358622168091127207,3957728857302212529>()) {
         case -21415249:
            return false;
         default:
            throw null;
      }
   }

   public List<String> t() {
      return this.g.w;
   }

   public interface a {
      void u(String var1);

      void D(String var1);

      void E(String var1);
   }
}
