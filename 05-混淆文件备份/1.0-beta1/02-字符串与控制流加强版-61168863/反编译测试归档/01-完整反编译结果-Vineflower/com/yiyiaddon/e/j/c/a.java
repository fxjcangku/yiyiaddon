package com.yiyiaddon.e.j.c;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundSetCarriedItemPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class a {
   private static final float bc = 0.7F;
   private static final int gH = 20;
   private static final int gI = 30;
   private static final int gJ = 3;
   private static final int gK = 600;
   private static final int gL = 1;
   private static final int gM = 16;
   private static final int gN = 10;
   private static final int gO = 600;
   private static final com.yiyiaddon.e.j.c.a a = new com.yiyiaddon.e.j.c.a();
   private final com.yiyiaddon.e.j.c.b a = new com.yiyiaddon.e.j.c.b();
   private ClientLevel c;
   private BlockPos a;
   private Direction a;
   private Block d;
   private com.yiyiaddon.e.j.c.a.b a = com.yiyiaddon.e.j.c.a.b.IDLE;
   private int gP;
   private int gQ;
   private int gR;
   private final Map<Long, Integer> K = new HashMap<>();
   private int gS = Integer.MIN_VALUE;
   private int gT = -1;
   private float bd;
   private BlockPos m;
   private int gU;
   private Block e;
   private final Map<Long, com.yiyiaddon.e.j.c.a.a> L = new LinkedHashMap<>();
   private final Deque<com.yiyiaddon.e.j.c.a.a> d = new ArrayDeque<>();
   private int gV;

   public static com.yiyiaddon.e.j.c.a a() {
      return a;
   }

   private a() {
   }

   public com.yiyiaddon.e.j.c.a.c a(Minecraft var1, com.yiyiaddon.e.j.a var2, BlockPos var3, Direction var4) {
      this.e(var1);
      if (!this.a(var1, var2)) {
         switch ((int)com.yiyiaddon.m.b.a<"s1rz1aj7istsr0","R7XcFAfEluaF0jMyBmG2pota6neiynqg7KjJCMhkV2U=",4927788039460521723,-6365775002060052663,-8652222329535334505,-7457360420728986342>()) {
            case 1011465663:
               this.a(var1, true);
               return com.yiyiaddon.e.j.c.a.c.PASS;
            default:
               throw null;
         }
      } else {
         LocalPlayer var5 = var1.player;
         ClientLevel var6 = var1.level;
         BlockState var7 = var6.getBlockState(var3);
         if (!var7.isAir()) {
            switch ((int)com.yiyiaddon.m.b.a<"swn48cx36xr2o","BIVNweDjJjfEQYRD89jBw90vKDrT30b5yGykPhdA6V4=",4204867938663112934,8372660374950795585,6666115485051972684,-5575361631474241343>()) {
               case -1965996579:
                  if (!(var7.getBlock().defaultDestroyTime() < 0.0F)) {
                     if (this.a(var5.tickCount, var3)) {
                        switch ((int)com.yiyiaddon.m.b.a<"sbmvuikfkmwsd","a0sB/ZqvWGJ829lP3KLjf3Uexs/Xf81shddQYy5aQbc=",-8166876649308387435,3628256764845653030,7434550255040530231,3079737147684693066>()) {
                           case -1224303917:
                              return com.yiyiaddon.e.j.c.a.c.PASS;
                           default:
                              throw null;
                        }
                     } else if (this.L.containsKey(var3.asLong())) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2xzutr86wgynj","zqmV4xTnJsrzeSigs/Qi7UdLBs7rR/CkSP1IY01wV6k=",4936606689748121134,-5737592784751970978,5250595470208892547,3870643582817350060>()) {
                           case -1414317094:
                              return com.yiyiaddon.e.j.c.a.c.ACCEPTED;
                           default:
                              throw null;
                        }
                     } else {
                        if (this.c != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2nns9smuqxcmt","uIzs/L+WPhfjm3LEr3awnzmwQhj3Pi6rdaPoyo6iYYA=",-6742339539787738482,6686650509214641578,-3071759282244557175,3316591755376452972>()) {
                              case 2000718098:
                                 if (this.c != var6) {
                                    label72:
                                    switch ((int)com.yiyiaddon.m.b.a<"s1pjl7x9cd47tn","niFP4L8gdFzImn08+ZVm6X5M46kLpAYalJAm7MbmpSc=",-704001694228504811,18382194565974851,-1873787475149939345,-783844997617027052>()) {
                                       case -842780480:
                                          this.a(var1, false);
                                          switch ((int)com.yiyiaddon.m.b.a<"s2e1umbc1mweqt","hcFX3G3pLiZptMI+TqJz3aKptITh5DjlJkT2WrU/oYI=",4854978884716716821,3978527523455064041,7055435004204696865,4382835745327427880>()) {
                                             case -943993477:
                                                break label72;
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

                        if (this.isActive()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s20kvjg77dv897","N+lHzKwPMkqn+8QfdlvT/1GDf4z7BCDSu2O83sPfeSU=",4466596537706564609,-7779803112659721144,-2595561619165680232,2365714223253734553>()) {
                              case -1130893218:
                                 if (this.a != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sedw1gs5ch8md","KX1YrKcqiyoXxuquqUHyoPGKH3g2SlUKidlDHM3ZZ+0=",-4999299194710249592,8593106532523676019,-6987831834481799782,-1211864698595647015>()) {
                                       case 1314563381:
                                          if (this.a.equals(var3)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"smnvv3t9oyn1s","ae6Ltip7K1yAgECGHcAXitAfUAQQeppaKFsphntW3wg=",3962207448769877335,3550878659002518392,8909393090063086455,-6491363453263176058>()) {
                                                case -78969387:
                                                   return com.yiyiaddon.e.j.c.a.c.ACCEPTED;
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

                        if (this.isActive()) {
                           label61:
                           switch ((int)com.yiyiaddon.m.b.a<"s26f8spv0w4zy8","WQkGSm+8ee3mG1oc20hG2x0BFEFoS6FxszEcymSWq/s=",-1445654581981054114,7638148666881126864,7980858941322230227,306676256756202103>()) {
                              case 82841286:
                                 this.b(var1, true);
                                 switch ((int)com.yiyiaddon.m.b.a<"s334fszm5398jh","PZWHH88hGW0n/iNs6e/GkQgeDTiIC/bDfH24MWSktKA=",5324356512977980921,-8064612902880451624,7916374910704531068,8579070517315601276>()) {
                                    case 200977452:
                                       break label61;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (var5.tickCount < this.gR) {
                           switch ((int)com.yiyiaddon.m.b.a<"s277vca19ji18g","/Vr2DfCZ5nQbSMUIVfNgs2nk/T0k5sj+qrjbIV3HYQQ=",713071603483533166,472167244115063872,3087613888778497800,4248156543555695639>()) {
                              case 644719705:
                                 return com.yiyiaddon.e.j.c.a.c.COOLDOWN;
                              default:
                                 throw null;
                           }
                        }

                        this.a(var5, var6, var3, var4, var7, var2);
                        return com.yiyiaddon.e.j.c.a.c.ACCEPTED;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s2ivsn6zwk7ilt","+yp4fjsIPc9c4lfbbO0RiqRqWTFpsHPbyK7hBdCqTts=",4354214294171574863,-7181697703836767998,-5812637594861824670,-7850352946675058491>()) {
                        case 1192616273:
                           return com.yiyiaddon.e.j.c.a.c.PASS;
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         } else {
            return com.yiyiaddon.e.j.c.a.c.PASS;
         }
      }
   }

   public boolean a(Minecraft var1, com.yiyiaddon.e.j.a var2, BlockPos var3, Direction var4) {
      this.e(var1);
      if (!this.a(var1, var2)) {
         switch ((int)com.yiyiaddon.m.b.a<"s23zf3fx5xk81a","2E+n0DE2KSCrIrjgr2kmyE/AUHUNScM7ATTj94FEAuc=",-3011711657596414412,-3728863725059222697,3696845054337758144,7111716475131576608>()) {
            case 2059870682:
               this.a(var1, true);
               return false;
            default:
               throw null;
         }
      } else {
         if (this.c != null) {
            switch ((int)com.yiyiaddon.m.b.a<"sqwnqlotqzh5l","TNNpW8zt3NXp+mLB9FtW6+MMIQS/6s2VBp9L+ZhSw60=",3553376146610754990,-2160649952709017490,4603610706727676359,5856565763978621797>()) {
               case -1376913562:
                  if (this.c != var1.level) {
                     label54:
                     switch ((int)com.yiyiaddon.m.b.a<"s974t5ztehkmf","U1SIVzsBXCDif99VrWdllgaFukROBD32osenrHGD6oE=",5601022028946404464,5229660003908888092,1937143065249174115,-3037777873456285776>()) {
                        case -585963556:
                           this.a(var1, false);
                           switch ((int)com.yiyiaddon.m.b.a<"s3h9ruknuwmh9z","LFXRGFcMRTvk0KiVAyr0896uhZFTn88zEJouwjZMY4M=",6342536797267098664,-8822734427467216017,-543922203490240282,7780221266586720651>()) {
                              case 785259963:
                                 break label54;
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

         if (this.isActive()) {
            switch ((int)com.yiyiaddon.m.b.a<"s32a7oi09bh6jz","8ymdb4MPaKJmWO0xtlCxi7nqOWEZCoQYuKpgNKgt2jM=",5165705218874013086,4198194789168788886,-4581357709793746879,1428580784366691427>()) {
               case 1176027935:
                  if (this.a != null) {
                     label47:
                     switch ((int)com.yiyiaddon.m.b.a<"s23g1mlscp6kbd","2gHww0CnL2Kx/IUp2DhXdePG/+/KKfaUNJDHmpQ3VWY=",-1735935393401481648,1536258596286918867,13938624453359619,-2569819178103959499>()) {
                        case 1143914401:
                           if (this.a.equals(var3)) {
                              this.b(var1, var2);
                              return true;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s11j1ud83jcxe1","PeB/RGaJJSXxuqMCmkf80SljKZzM0B5It2yvkM8u3R0=",-5792186170224663884,-5396566921142367340,-5152967497444257925,4608328693807248161>()) {
                              case 156466115:
                                 break label47;
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

         if (this.a(var1, var2, var3, var4) == com.yiyiaddon.e.j.c.a.c.ACCEPTED) {
            switch ((int)com.yiyiaddon.m.b.a<"sjb36ymdr2i4k","5LuV+j27jr3B08l8xtFveGK5YcBrR4uc3wY98/O6K/8=",3644331379926748780,9075481053049163041,-2317663061319526312,1566779792849710951>()) {
               case -1782039856:
                  switch ((int)com.yiyiaddon.m.b.a<"s2977afptetx4r","KzGcz0AmyWsHzuTirN+gO3WLr6ufYzbxrDIOqf6RFW4=",-6024782339914282829,6336380531322225628,2020581098385186744,6276815241477239821>()) {
                     case 493769467:
                        return true;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s203prjz19hojy","8ZyZOGlc9mMVRWiXH+uJL+kYOUcQDqTpszWsOLdrYSA=",2103332541145584561,-3880854741293533673,1192820368321893245,6269271153840081471>()) {
               case 1839438671:
                  return false;
               default:
                  throw null;
            }
         }
      }
   }

   public boolean b(Minecraft var1, com.yiyiaddon.e.j.a var2, BlockPos var3, Direction var4) {
      this.e(var1);
      LocalPlayer var5 = var1.player;
      ClientLevel var6 = var1.level;
      if (!this.isActive()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3s9b6fn4e1ogo","oli5O11WaVWizlV+BYESKtS4wtncMPIfNqqbwXcXng4=",-2250327072764627866,4502092078969406303,3570070978857938698,-2870767901941733184>()) {
            case 522495029:
               if (this.a(var1, var2)) {
                  if (var5 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1loowgj78qsow","myBZD2ukruLqpMTETiMtvLOMpU5X1i7WQS637CU5wUs=",8133895549429809275,-4751457937280114359,5745691630172610806,-8536084590918844407>()) {
                        case -544410681:
                           if (var6 != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1vgar38185o4e","j9amHIvqo3iDPO0E/Iqf5QPMR+1LYFmvB04mF4lu83w=",-7607297196078624017,8729867996462234240,-1794226565037868908,3750667660795846723>()) {
                                 case -1198932907:
                                    if (var3 != null) {
                                       if (this.c != null) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s37uffnqqapejt","vhDGTHpi7YQ2Tqx55aCadYrlVgyxz8qX2VjWGfauQH8=",-8756834762851440907,7055861614626688623,686530831837641645,-9154423510304877685>()) {
                                             case 1337821113:
                                                if (this.c != var6) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1270drb2m2ioo","5xe0GBY7j6pyqZlSZrPSpYDe2G5MlHMDNF+b6WqWWl4=",-7878364806417358774,5058995412947477022,-5059871004541818514,7682773630593766661>()) {
                                                      case -699745760:
                                                         this.a(var1, false);
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

                                       if (var5.tickCount < this.gR) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s31lgyfjxf4zjz","K2wzSbCeB6zoj/D4xU+aNlTH0MOI5zWq5Wvwss+4P6E=",-8073477368380511265,-6126720676719759786,979833485553455136,-4091026729567055216>()) {
                                             case 1255387667:
                                                return false;
                                             default:
                                                throw null;
                                          }
                                       }

                                       if (this.a(var5.tickCount, var3)) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s4ffzkyxfget1","3efvNu5wCXhDAbcyApsqkE404WQFZ3azj7TmC3ja+XE=",6293713935429537671,2014111647275809437,-4520963984914631453,-478089285100469947>()) {
                                             case 4503152:
                                                return false;
                                             default:
                                                throw null;
                                          }
                                       }

                                       if (this.L.containsKey(var3.asLong())) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1cfieqprr4w6w","lgoiqz9l75VRM1jbS9htTu2UOt1w38ZBVU3axojjlgk=",-3242737331839341619,-866079799904619825,6159128686174298882,2289811195511564390>()) {
                                             case -1872436828:
                                                return false;
                                             default:
                                                throw null;
                                          }
                                       }

                                       BlockState var7 = var6.getBlockState(var3);
                                       if (!var7.isAir()) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3bsbj7h7n19nx","9bspwLSYYmI2iIzQGm+bivsBzs74C86tluYs9Nl6P6w=",-2490781236801879004,8006828724586459196,-7749033480781913488,7075334473332491014>()) {
                                             case -433782935:
                                                if (!(var7.getBlock().defaultDestroyTime() < 0.0F)) {
                                                   if (!var5.isWithinBlockInteractionRange(var3, 1.0)) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3m3j6xobfeu4b","Oyyam5DqCgCrJZ0VvgZP5JOQ6EQoY5LqcUKqj+LrFtw=",-4876832666523995404,9058774601308118941,4079048874361626165,-4042295270932376633>()) {
                                                         case 598961058:
                                                            return false;
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   Direction var10004;
                                                   if (var4 == null) {
                                                      label67:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3lsvfa012sqtb","vxqQSaFB8olKTKv79ApG4+5ZiQdHOXOheIogZOCdBJA=",6352556873197636806,7906572610230686437,-6694938093741651382,1328967020207916769>()) {
                                                         case 435661561:
                                                            var10004 = Direction.UP;
                                                            switch ((int)com.yiyiaddon.m.b.a<"s3fjtuxgfm7x8","sMQVB/J0t2Q1vQONmQ/ekwDHDPJw33ZrjDpMpWQ9nIg=",-3139585451680331122,6068277316061498537,-2596434793136248947,3809011366429196889>()) {
                                                               case 1175180327:
                                                                  break label67;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else {
                                                      var10004 = var4;
                                                      switch ((int)com.yiyiaddon.m.b.a<"s15myoh215pwpz","NJKGrLsMVuClE/7WH02pBaCW/g1EK4hczrDIRTnA6dA=",-6384667934716391990,78465644497279341,-8648843760334414842,-4591561040364977927>()) {
                                                         case -1899666056:
                                                            break;
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   this.a(var5, var6, var3, var10004, var7, var2);
                                                   return true;
                                                }

                                                switch ((int)com.yiyiaddon.m.b.a<"s7bw3rmkxyxlb","kw/dWEp4r91PHlY4rfwIkQGMzVcVCODQfCQeZYWXRao=",8999396396411710046,-4409004645206987235,-8764822318998492044,2752880545250906261>()) {
                                                   case 358961830:
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

                                    switch ((int)com.yiyiaddon.m.b.a<"s29g982nndysnz","Fx2896aGJKqFIC19ej/GC10d3dm9KcfqhpMRRQGh7WY=",553003741272010496,-3067683043537971487,-7885933042828008909,-3601445646254803245>()) {
                                       case 905837914:
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

                  return false;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sda00cur8cg1w","vDhH5CkVsT0XS+qGQeqLqs3qLW4qd1SDKSVxKazvVjI=",3820873777570564436,-1630545606945494549,-2332487364018381117,-4087591654498803574>()) {
                     case -1460400496:
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

   public void a(Minecraft var1, com.yiyiaddon.e.j.a var2) {
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s29dksq68lko1r","9H+UXZBLBgmLPtsI06iwohLSibbmgtKsuZyFJVwuiZg=",1842595077768911396,-8165461364282777355,6158038679319726908,4329923755795446684>()) {
            case -496548960:
               return;
            default:
               throw null;
         }
      } else {
         this.e(var1);
         if (!this.a(var1, var2)) {
            switch ((int)com.yiyiaddon.m.b.a<"s3r5vubn3xulxf","uNwrw0V2+c2brAJwbcpAmJjFkTAuL1wDoWs4KlEkqy4=",1649121286619403749,471564010121738996,-2592835827753515403,-703462227018830594>()) {
               case 633959079:
                  this.a(var1, true);
                  return;
               default:
                  throw null;
            }
         } else {
            if (this.c != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s3bvfhrh5rmpte","ic5keD/vCBIj2retkIoA773eorMjuTFrI4bn99jEGUw=",2154725729798940224,-3319753091563966558,614224719375889078,8743072844748281153>()) {
                  case -525519639:
                     if (this.c != var1.level) {
                        switch ((int)com.yiyiaddon.m.b.a<"s24j4cqodfwsql","LExqUa+4DUaMlVShcQstHA6T836moVcJiVRvPvholcM=",6147277422164222840,6073055224761383491,-8566586664499902549,-3201711481748104545>()) {
                           case -945014682:
                              this.a(var1, false);
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

            this.c(var1, var2);
            if (this.isActive()) {
               label31:
               switch ((int)com.yiyiaddon.m.b.a<"s2lua6bseh5lzl","p0NNJqzfg4eFUir85tC0EHUsRLDe7u2tM575wmEXIH4=",-8214169769810998571,8835485927361061195,-8376038097397506578,1571486737181999898>()) {
                  case 176839777:
                     this.b(var1, var2);
                     switch ((int)com.yiyiaddon.m.b.a<"s560nwktuaoio","gHp82KvvOyXitCrXw+o5wWmFp8NjmZdtAVzyfFHySCc=",-4936803048661112138,5577481223096521318,-2214689271258514427,224662025399828679>()) {
                        case 1170061894:
                           break label31;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.d(var1, var2);
         }
      }
   }

   public boolean a(Minecraft var1) {
      return this.isActive();
   }

   public void a(Minecraft var1, boolean var2) {
      this.b(var1, var2);
      this.L.clear();
      this.d.clear();
   }

   private void b(Minecraft var1, boolean var2) {
      if (!this.isActive()) {
         switch ((int)com.yiyiaddon.m.b.a<"sgkoog2lftwio","pCKc98VaK57ULEmVmDj6+3QXDPDjUA1REWXaAtAQ5B0=",-3482798851194918475,7621201688913003163,-5315988589808849874,7611434370238387773>()) {
            case 288492178:
               this.eF();
               return;
            default:
               throw null;
         }
      } else {
         LocalPlayer var3 = var1.player;
         if (var2) {
            switch ((int)com.yiyiaddon.m.b.a<"s33n5f41y3tq2e","ZSA7ggq+GoP8poJc12VpDoO0/HITMK6JOqOEGVackKc=",8425074246673639568,-1676303126945250657,3610851201545201505,-669184363862660940>()) {
               case 470928657:
                  if (var3 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3ag7o6cl2izn2","uEsWhuvMufKxT6BowwdI81cC6yRNaqgsVXdI8feZmDM=",7942002420716609850,361614140970630428,-2205864776344339319,8328726550025178012>()) {
                        case -911776614:
                           if (this.a == com.yiyiaddon.e.j.c.a.b.MINING) {
                              switch ((int)com.yiyiaddon.m.b.a<"s10347eb90nsl8","l3sr7CN5dTUpLQux7vyXfqPzvcl3WyAYlfnd+uVkt5w=",6909691647290434800,-8004716208801093478,-5587763747566734554,-7617995517149702249>()) {
                                 case 443864563:
                                    if (this.a != null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"sy047y4u0jekx","HMuOIZzPWerrG74ttdiZq5FQtYyD+dWkfflnlOrZ5vo=",-8401921438355028546,-1044269601779109454,6908996411581283925,-5330083752356018337>()) {
                                          case -1100149457:
                                             if (this.a != null) {
                                                label40:
                                                switch ((int)com.yiyiaddon.m.b.a<"s15ejoet1rvmh0","0TLYd00MHtoFG0QMIicuga4BAmSEw6+JcOIF/bWloZc=",-887308380567078898,-8237764262243721490,1265538966415376910,-6593884564387524404>()) {
                                                   case -1141591224:
                                                      this.a.a(var3, this.a, this.a);
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2g3l0cpawar7c","RtvDWQt0K5ONIFBRnf3ekuG7rUtRMFyYwzejL+Ouf/c=",-463947628260637147,-7249607740255021251,6392133325576789287,4047839424826400911>()) {
                                                         case -2009435377:
                                                            break label40;
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

         if (var3 != null) {
            label35:
            switch ((int)com.yiyiaddon.m.b.a<"s1kgubblojp3jj","i2bqOjBfe+vEMtshDOamLMY9+tTafuIT3lqp4rauJB4=",-394822407448007709,-4003772205377069376,-7712983778637296059,-5313930389939062915>()) {
               case 76021990:
                  this.a(var3);
                  switch ((int)com.yiyiaddon.m.b.a<"s3086itctfuipu","6w3lf7+Bw0gKrZkN2VXuULAN2meBrJTu+Q9UhPuJPAI=",-3869883237618778766,1502245159295417133,-3227518494187379021,-2528246538692423479>()) {
                     case 1637391981:
                        break label35;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         this.eF();
      }
   }

   public boolean isActive() {
      if (this.a != com.yiyiaddon.e.j.c.a.b.IDLE) {
         switch ((int)com.yiyiaddon.m.b.a<"s1mp7a1smm1ic2","xD0E4qBDYmzELPn7jTFu1KISlu6aF31e+3hq+6pxPPc=",-838299259617536279,5482235460120310309,-4686347403822067017,-519259286345698371>()) {
            case 1301683564:
               switch ((int)com.yiyiaddon.m.b.a<"s1jkf65uk1um9f","1YK/xa3t6WWkk10pSbNG44kJ5mbNCtx1sjp8pmQpzYE=",6877017938426320246,-599851347709311006,-8275038363748338756,-1130682540219537159>()) {
                  case 2080431024:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s1j2g6hhg5xe3d","TJLUZOK7j3kFMhUvqkvsGL/aib15h+cVo0he4w6UzZg=",8321643848792540713,6553992640359425302,6133184767460124685,7370193962818768873>()) {
            case 2015590377:
               return false;
            default:
               throw null;
         }
      }
   }

   public boolean bW() {
      if (this.a == com.yiyiaddon.e.j.c.a.b.MINING) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ct7mfic2udcb","WcwkEI1Nr9lbwxFUFxBODi91gSA5b0NReApNsztws0I=",7613359277010147730,1118173280863588496,-8676577317997126045,-8743507440545185769>()) {
            case 1135007865:
               switch ((int)com.yiyiaddon.m.b.a<"s2o3cpikp510vv","xZ2XLc9xiGt2xeQSlM+Pa77COmYd+yysO8HrmkoLOps=",3104310106543485093,-6848606505000785263,7586658075676174808,-5957552994520795978>()) {
                  case -1322006004:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s7ubuwds37bvr","J/OFBakniWEezGzyDjezJLNYb0xu3EJLdonhhaBr1X4=",1322552075283800206,552796405126620864,-8289024029663197217,-3518119234671516661>()) {
            case -1699087193:
               return false;
            default:
               throw null;
         }
      }
   }

   public BlockPos h() {
      return this.a;
   }

   public float f() {
      if (!this.isActive()) {
         switch ((int)com.yiyiaddon.m.b.a<"skq4jq57dq1no","eO6nNUq9nyCjAmxRt+lxaGNQ+VhjRbnzAqLfSbbTQRY=",5992870975815299139,9185513274433322996,-1010010471800586306,4830029206616201148>()) {
            case 1903691471:
               return 0.0F;
            default:
               throw null;
         }
      } else {
         return Math.max(0.0F, Math.min(1.0F, this.bd));
      }
   }

   public BlockPos i() {
      return this.m;
   }

   public int bv() {
      return this.gU;
   }

   public Block d() {
      return this.e;
   }

   private void a(LocalPlayer var1, ClientLevel var2, BlockPos var3, Direction var4, BlockState var5, com.yiyiaddon.e.j.a var6) {
      this.a(var1, var2, var3, var4, var5, var6, 0);
   }

   private void a(LocalPlayer var1, ClientLevel var2, BlockPos var3, Direction var4, BlockState var5, com.yiyiaddon.e.j.a var6, int var7) {
      this.c = var2;
      this.a = var3.immutable();
      Direction var10001;
      if (var4 == null) {
         label32:
         switch ((int)com.yiyiaddon.m.b.a<"skct44iseosuc","VAsmkbZaXgn9pNPmHTEzbqoojtox0gCL3XPBphgzrXc=",-7250066554421338479,9105605726932606919,-7719382808309740245,5648490154282716659>()) {
            case -1905104332:
               var10001 = Direction.UP;
               switch ((int)com.yiyiaddon.m.b.a<"s384gjfzqujijj","Oz0j7j06ITVVpaPYvev25Nm2OYgw7RkIlojUQ8ZVmQA=",4940358401068439796,-6990485261336161807,-7542957569659270703,-519406881565379031>()) {
                  case -616159911:
                     break label32;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = var4;
         switch ((int)com.yiyiaddon.m.b.a<"s3ez110iy0oi0r","2zlfPavpTNAqhwG1AqsL7CzWSI7GYLTZcS08xapIWbE=",-7582101207772760937,-4406301966783168035,-5621842751565107643,6113989202519375180>()) {
            case 2089183665:
               break;
            default:
               throw null;
         }
      }

      this.a = var10001;
      this.d = var5.getBlock();
      this.gP = var1.tickCount;
      this.gQ = var7;
      this.bd = 0.0F;
      this.a = com.yiyiaddon.e.j.c.a.b.MINING;
      this.a(var1, var5);
      this.a.a(var1, var2, this.a, this.a, var5);
      this.a(var1, 0.0F);
      float var8 = this.a(var1, var2);
      if (!(var8 > 0.0F)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2kyw8d26ujv68","s10vQvznQuzX4l+kJzJcn0q1hSWNPCmZsnn/TtT9RsI=",3271297730183649362,9055994776065755641,3719232106880981468,-4653677437641358910>()) {
            case 1189153895:
               this.a(
                  var1,
                  var6,
                  (String)com.yiyiaddon.m.b.a<"s29etl7fwsq8y2","3+02QXz9bW1Q1+9DU9O3QeCGJMB/NWrM4+ogd89voRsNocPXIyQhNWjk",-9131881392168148998,-8370583631028344290,5864160604987161861,352873460337109728>()
               );
               return;
            default:
               throw null;
         }
      } else if (this.a(var8) == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s28jkasakajybe","xGQZu11kT4C9Ffl99/uO4Wa80sFIB67TE6SUi/eHmJE=",-8913500425938662807,-3141226391155064263,-4295994364994376010,3680561909629028011>()) {
            case 1578473964:
               this.a(var1, var2, var6);
               switch ((int)com.yiyiaddon.m.b.a<"s2pctimvohm2h8","sYiG67KxsS44PkLHywL99NqVb8+JcWAhhbSZTUT80f8=",-3156274181363336535,-3239648081877948774,-1474458147515385642,5316780000772005882>()) {
                  case -238909254:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private void b(Minecraft var1, com.yiyiaddon.e.j.a var2) {
      LocalPlayer var3 = var1.player;
      ClientLevel var4 = var1.level;
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s35k71o2r5zxrt","a5ag9xo+2SGEJu6gv/r8DXaLHuvv0hsUf96HwuopUcw=",-2388074481594748939,-3035417203484862145,-4501411425937862019,-5806947962137302298>()) {
            case -1959471457:
               if (var4 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3iis9vfjjkdvy","7X0mjK2mBhe55FYotCGNdZPgWMvJsFJjkQBGyMJ1wjQ=",8158503917884302946,-5467279212309755176,-244613739753568982,-6256650758304513188>()) {
                     case 789901275:
                        if (this.a != null) {
                           label73:
                           switch ((int)com.yiyiaddon.m.b.a<"sala1e3e3syqm","I2XoAqpDNK7eRboZsTHYNnzAU5gNeZ52L8XxuDSdjK4=",-4177112139709906990,8738190125098272093,-5241001077697768797,-8516026739064434904>()) {
                              case 372607982:
                                 if (this.a != null) {
                                    if (this.a(var4)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3rcsfn3bclyyv","Fg8Nl3bEE7XLweWzHy+CNouaf1G6AQNGKJXRbY7/yPQ=",-3241697372774246923,-8267729144814169827,126614150710074509,2108812603072472320>()) {
                                          case -1743988663:
                                             this.a(var3, var2.bs());
                                             return;
                                          default:
                                             throw null;
                                       }
                                    }

                                    if (!var3.isWithinBlockInteractionRange(this.a, 1.0)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2x1ing48h87d8","ClhnzsNCvDKtCiQSgmOou2d+EyGBYaETAxfzHAbAhRo=",6626502877424490627,-4689969815644659755,1015792604324315386,3807724684052077281>()) {
                                          case 77684059:
                                             this.a(var3);
                                             this.eF();
                                             return;
                                          default:
                                             throw null;
                                       }
                                    }

                                    float var5 = this.a(var3, var4);
                                    if (!(var5 > 0.0F)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1wdn7gk5st83y","6UbY7k15hDr1GLWbfqPaJW9yveHdrPxpKYTHJQ9mMPg=",9084572343485413623,-3592527364453879937,-4580053030691659938,5121795325985976538>()) {
                                          case 563367940:
                                             this.a(
                                                var3,
                                                var2,
                                                (String)com.yiyiaddon.m.b.a<"s29etl7fwsq8y2","3+02QXz9bW1Q1+9DU9O3QeCGJMB/NWrM4+ogd89voRsNocPXIyQhNWjk",-9131881392168148998,-8370583631028344290,5864160604987161861,352873460337109728>()
                                             );
                                             return;
                                          default:
                                             throw null;
                                       }
                                    }

                                    int var6 = var3.tickCount - this.gP;
                                    int var7 = this.a(var5);
                                    if (var7 == Integer.MAX_VALUE) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s16nl6mfyghcbz","hEcgfu7/JeHZRzkXnCDEWajlMgDYtIeZDMA4MrD2dFU=",3696536353044867595,-9159512630099434640,7262117045483409377,-78863336289447153>()) {
                                          case 415303457:
                                             this.a(
                                                var3,
                                                var2,
                                                (String)com.yiyiaddon.m.b.a<"s29etl7fwsq8y2","3+02QXz9bW1Q1+9DU9O3QeCGJMB/NWrM4+ogd89voRsNocPXIyQhNWjk",-9131881392168148998,-8370583631028344290,5864160604987161861,352873460337109728>()
                                             );
                                             return;
                                          default:
                                             throw null;
                                       }
                                    }

                                    this.a(var3, Math.min(1.0F, var5 * (var6 + 1) / 0.7F));
                                    if (var6 >= var7) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1xivoooq2up51","neNKIYzFjaomEkAeEJkQNNtdOReKJrlH5IvFXHC0OpE=",-2470959473822695187,7027592347111603148,7943431019203205257,8779631721063625475>()) {
                                          case 1999891655:
                                             this.a(var3, var4, var2);
                                             return;
                                          default:
                                             throw null;
                                       }
                                    }

                                    if (var6 > var7 + 30) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1ij4pvjlig7lh","3AdH/ermiSuGDASyWsinJ8a4eS8NXvcDaTiq2GS7v5E=",4069858974518690945,3202155496105594275,-3846771409754424495,8869591752031234574>()) {
                                          case -1453883087:
                                             if (this.a(
                                                var3,
                                                var2,
                                                (String)com.yiyiaddon.m.b.a<"s184tgckpdv3o1","0nZ5sdTSwDHKSy6It3wVOcUOdOTF3NoruaLBQrigcrfIRVF9Rf6txcS5gL0Be1ZB",8790489914622351659,-1969660617387348852,-3334952043290947134,2017791751196401210>()
                                             )) {
                                                switch ((int)com.yiyiaddon.m.b.a<"sj3tes3etfa8o","Q+2DyiStEFl2sCqbCYcnNpNxAijS0LKUuopi87TdKrc=",-3220313909010089715,6498167828314219312,-7009037680259727089,3014392598617251672>()) {
                                                   case -2097325569:
                                                      this.a(var3, var4);
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1qoofuicbh6jj","b/TDORnSqPSsbtVQXzmDfuKwBvqBO8sLtJZVWWJhZkw=",-1803076656954453384,4937625930183717432,-6662086457505379,-1014900733043632654>()) {
                                                         case 813445283:
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

                                 switch ((int)com.yiyiaddon.m.b.a<"s394y4zosc373o","sA5cwtOUW+/7pMgrv8bB04V6qdjOAebyFRPN31apIBA=",-278057585811690039,-8158402313479214587,-1182159623741800443,-5709379141397782369>()) {
                                    case 1499065722:
                                       break label73;
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

      this.eF();
   }

   private boolean a(LocalPlayer var1, com.yiyiaddon.e.j.a var2, String var3) {
      this.gQ++;
      if (this.gQ <= 3) {
         switch ((int)com.yiyiaddon.m.b.a<"s11stp3q0dgzay","0MuyejtvaROwpJvkdLqQ/gD6j++0vS/XVbFpZ2oiLsA=",-7593148533002912973,8530162713499541466,3045802304508549714,-69430832287021325>()) {
            case 920379865:
               return true;
            default:
               throw null;
         }
      } else {
         this.a(var1, var2, var3);
         return false;
      }
   }

   private void a(LocalPlayer var1, ClientLevel var2) {
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s124755qdamjbj","xnGUOOBr5C7a9bGJxe+jc7NVKGceCFA/cvhoWpXKTUQ=",-2936893158569535114,-5680341738410075331,-4492697844996317965,-4515534308039416526>()) {
            case -1919203012:
               if (this.a != null) {
                  this.gP = var1.tickCount;
                  this.bd = 0.0F;
                  this.a = com.yiyiaddon.e.j.c.a.b.MINING;
                  this.a.a(var1, var2, this.a, this.a, var2.getBlockState(this.a));
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s21o8y0zfx32cy","0DTjv7em0vbS8tbw8LD7taeR2Chnf65csCnxGN55wAw=",-5153836975175599117,-2358312024827692492,7043854878562174323,6983212172437477302>()) {
                     case 2098012182:
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

   private void a(LocalPlayer var1, ClientLevel var2, com.yiyiaddon.e.j.a var3) {
      if (this.a == com.yiyiaddon.e.j.c.a.b.MINING) {
         switch ((int)com.yiyiaddon.m.b.a<"s8waxr7iypa9c","4OzVypdWQpPBAe958yjEeA/HNdWOe0eFmQ9PQ5ERVdI=",-7903659251130447754,7995575286831933399,-4396785746392635290,-2270938442786256419>()) {
            case 565692100:
               if (this.a != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1a275ovnv8gkq","bWaOdUh64IZYrkUbcODZwwOGpZpi6tt6f1YOUrVfDIA=",3033024664045143926,-7558875450189178467,-2034222229150288043,8605206839017352715>()) {
                     case -904925709:
                        if (this.a != null) {
                           this.a.a(var1, var2, this.a, this.a);
                           if (var3.bR()) {
                              label23:
                              switch ((int)com.yiyiaddon.m.b.a<"sb6mnpfkm29dk","Rwn+w0R4KMtASlUKs3ljEAayhstsVMAEmJxtfgESsQE=",9046962212299105562,2620028797334375177,1649621552883141877,-6609198994356928823>()) {
                                 case -1141480447:
                                    this.a.a(var1, this.a.above(), this.a);
                                    switch ((int)com.yiyiaddon.m.b.a<"s1rxv1glns2c39","6rbklkPHt7WuBsMO2ZLSpVbOtnRVUI8L8t9gYs7cfbw=",-2942791219278281164,-7771595260722183526,-2150826054399015427,-5318900064527692722>()) {
                                       case -1265138270:
                                          break label23;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           this.a(var1);
                           this.a(var1, var3);
                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1gm58ybdaowab","1iOnyWLRbQC3QEErVu7T2Vlxg4qRBd0ckasVW4rlLzI=",8052349685726704936,-7519668314785006415,-7910468999602874180,-6346903147309623843>()) {
                           case 549580857:
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

   private void a(LocalPlayer var1, com.yiyiaddon.e.j.a var2) {
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s28rhh3akzjwgs","iQc4xIVskoJ42Xt9b8HtP+eVrVcPV7EcuA3FtmIDXvE=",-6676554722879287623,-4508615220610823196,-2334360933660068186,5657029711343449732>()) {
            case -1615353008:
               if (this.d != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"sj8o2szshglqo","fXFmeNJVKeunI73smGOKeONbbNAS4KWRlqsxAyMLXUc=",-1577742221255650608,-8206425196290644374,-8034738997328712395,-7467850279560738617>()) {
                     case -403096881:
                        if (this.a != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s24xs0fgqdwpsi","Q8OXsoZi+OhCkXHWpNhq+qj+pz4vgVNFHaArekNgHH8=",-6541356612364213194,2737407903935773885,6759588077479502263,-5828073191327641271>()) {
                              case -401179153:
                                 long var3 = this.a.asLong();
                                 if (!this.L.containsKey(var3)) {
                                    label37:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3vehlzp2de6cu","uIPK+oXh5VYJX4XyzfdHprsoUW6VcE8HMstapURL9/A=",-535793651060522540,-7240871768735795425,-5202329677001218914,3809088886723672783>()) {
                                       case 484602683:
                                          if (this.L.size() >= 16) {
                                             switch ((int)com.yiyiaddon.m.b.a<"sgo1pnmzzsuow","iA+gaH09/sx9hWF8Xx2PG85a9EoEIE/oK7heD3P6p5k=",-831554666127850030,4889055233606894639,8175778592857534260,5823274075362089139>()) {
                                                case 976556222:
                                                   Iterator var5 = this.L.entrySet().iterator();
                                                   if (var5.hasNext()) {
                                                      label32:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s2iqf6hcpm9neq","VoTnN+hDy3QNzG0PWJFo4hddB7PDBEj6wBe9xBSwS/o=",-4871391223312954147,971450480916365819,-4420177761994429720,7192230837105764120>()) {
                                                         case -689870236:
                                                            com.yiyiaddon.e.j.c.a.a var6 = (com.yiyiaddon.e.j.c.a.a)((Entry)var5.next()).getValue();
                                                            var5.remove();
                                                            this.a(var1, var6);
                                                            switch ((int)com.yiyiaddon.m.b.a<"s1928jvp8y5fw0","GANQwV5wE6i+thSXCEpUEAqIPGC/9WRVQMANG7un3UA=",-4312772814537788589,-8786733268419791716,3743648162482683405,-4534511517270196735>()) {
                                                               case -155063133:
                                                                  break label32;
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

                                          this.L.put(var3, new com.yiyiaddon.e.j.c.a.a(this.a.immutable(), this.d, this.a, var1.tickCount, this.gQ));
                                          switch ((int)com.yiyiaddon.m.b.a<"s22r1ckjfzm0jo","MzDM2qhc/0gm7E38GpvKEPMZeXNiuBX0Xb0voojd/6g=",8743598227589392609,-4605916442131821960,-1676773899237007849,8676004832444220097>()) {
                                             case -268301950:
                                                break label37;
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
               break;
            default:
               throw null;
         }
      }

      this.gR = var1.tickCount + Math.max(1, var2.bs());
      this.eF();
   }

   private void c(Minecraft var1, com.yiyiaddon.e.j.a var2) {
      LocalPlayer var3 = var1.player;
      ClientLevel var4 = var1.level;
      if (var3 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2hgwie2mdv4uc","XOXqjPirsvpvHXtCm6uSW7ykbPK/s06jSMUB+gPbrsM=",6979643361191566323,-2803146614964298581,-7539789133816203695,-8688797454267260877>()) {
            case -1322596542:
               if (var4 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"sbi6u7xf9vng4","FOq/UXL/iYF5aV23PXY/+QmfFyxc0f744zgmloi31KM=",5340222977818991216,-2225783799270228674,5997876296546670323,5053784889352760814>()) {
                     case 932594435:
                        if (!this.L.isEmpty()) {
                           Iterator var5 = this.L.entrySet().iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s2tggyax3j0lah","/oP8ktzOaFP49tZTfbhISatXI8XeB4wNHj/1JpD2RF4=",2494623197396533931,-859895643712535438,6514592184330703404,-8049825942122614158>()) {
                              case 1808053877:
                                 while (var5.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1q2a70aa9suec","RLYhT3viULmdS1y8T0lhPlTVc4GQKJ1OorGpPy9JyA0=",4115656908077028080,-2167040990700683980,3524728587628732804,-838608610379008361>()) {
                                       case 1892439839:
                                          com.yiyiaddon.e.j.c.a.a var6 = (com.yiyiaddon.e.j.c.a.a)((Entry)var5.next()).getValue();
                                          if (var4.getBlockState(var6.n).getBlock() != var6.f) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3hjzwmkbj0aid","z/Zdl7qOCkjlmA4JFBe8j7akTaKrwz/Ou1NJjqGV24I=",-538515872923490759,3927555453669968073,-1008402689858676457,141753072838737791>()) {
                                                case 212835485:
                                                   this.a(var3, var6);
                                                   var5.remove();
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2rvu68v6cpots","85B3DTj/WT/R0R9+X8jzMnPnE3/osQOUoKzpu/Nng6Q=",8483690748367647254,8128435590152933,-1883464516947573322,5054127638588487745>()) {
                                                      case -1702076841:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else if (var3.tickCount - var6.gW <= 20) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s15szh7g1l19cj","jT2Zi/DtwYGpM0ol/2/91OPlLeB7A8+J+tzKvPvrhOA=",7062298553071064401,1439518264665624421,5157574220411099807,140155493509128336>()) {
                                                case -341792446:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s29wmvslx15zib","+aAQhQ5qBkvO7njcSD9rwrWZIqUMRflZNgo9LsTPWB4=",4914655556475979989,2574487636007624036,6812080139141923888,6192105691520802187>()) {
                                                      case -1266972793:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             var5.remove();
                                             var6.gQ++;
                                             if (var6.gQ <= 3) {
                                                label47:
                                                switch ((int)com.yiyiaddon.m.b.a<"s2n8or09j9gcqs","v9/9wr7BKfgPzGRPn9Ky3DZTPR2Kc8RYPjlzMnjkW78=",1111926474476597834,-2955279689849892806,-1401706932124577060,3003021421815471450>()) {
                                                   case 1839464305:
                                                      this.d.add(var6);
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3qvxbdck0ahl7","uG7pFGuBwBA0ENv0cwpZkbLrgDV2qhEHAh9D515KlQQ=",830608959990602215,3865807711681724622,-3378903809838620992,8512308697108961556>()) {
                                                         case -650686428:
                                                            break label47;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             } else {
                                                this.b(
                                                   var3,
                                                   var2,
                                                   (String)com.yiyiaddon.m.b.a<"s2h1228kz6eb63","j94vRztOXFr4mB0FNfQXyGQkJFMhE0mMpCKCtZS08Iz/jFE8ue+rMwf9AP1qcbNd",-8040776403446153792,-255876930447834173,-5789279409676388381,-8086395396294054310>()
                                                );
                                                this.K
                                                   .entrySet()
                                                   .removeIf(
                                                      var1x -> {
                                                         if (var1x.getValue() <= var3.tickCount) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s1typm9sm8rdtz","BN7D+cTsxVexKiMTDFQmVdRmr25CYIgarnad+nLMRkQ=",-4231348880954807836,3730579402289223549,-5554663296990942808,2404741259228001776>()) {
                                                               case 1363590587:
                                                                  switch ((int)com.yiyiaddon.m.b.a<"scv2ftfyupxjc","mvroAXXUuCNNQXdcWk1a0mLuFJU0Tv6uhJoV7Yg9Elg=",5487919415243705574,3311885981328647568,-1748880163558046036,-425284079231605910>()) {
                                                                     case 2055829449:
                                                                        return true;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         } else {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s106djw6tx008h","8/OroWBgVs9BnfHhi+ABb/TK0IeaoZoLUG5k4CbJy48=",-8051898963701237729,-7881981675729649762,445660801772246734,4408239386076635212>()) {
                                                               case -1694340159:
                                                                  return false;
                                                               default:
                                                                  throw null;
                                                            }
                                                         }
                                                      }
                                                   );
                                                this.K.put(var6.n.asLong(), var3.tickCount + 600);
                                                switch ((int)com.yiyiaddon.m.b.a<"s2nffeciox1xn3","uHqEzRKx2cWreuuDniYKIoAyohEYr87dCwHQ7xnXLcI=",2128393956411474173,2621669935034498601,-2721317609042598044,-3048061037730840028>()) {
                                                   case 1108095876:
                                                      break;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s2rdyzlhy4tsbg","/Fx/eLFNqgUDEcTtu5IJYdB2n4xUjjl7Q0c8DcMBhV0=",-6011578341382898323,750611489557722364,-4907083600738148517,6323401967315403010>()) {
                                                case 1032073958:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
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

                        switch ((int)com.yiyiaddon.m.b.a<"s1s622wbbbq56e","PFR2YPiLvzb/cH9TeeNm5VFTWJQeFdcQMT43q06kY30=",-1924700506709184053,-8350437919100936259,-3720227189730076162,-1350882770815327368>()) {
                           case -258671505:
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

   private void d(Minecraft var1, com.yiyiaddon.e.j.a var2) {
      if (!this.d.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"spg99xp0o823q","u4sagNZri+3U7X/B9V0Atih1aU2kUra9W6gxW5OuAWs=",-2907128090746770251,3129011323473340366,-498325007612454729,2976434878349936533>()) {
            case -1886380988:
               if (this.a == com.yiyiaddon.e.j.c.a.b.IDLE) {
                  LocalPlayer var3 = var1.player;
                  ClientLevel var4 = var1.level;
                  if (var3 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2nuwddukzg6ur","hSjO6wXKCnJfHkopIYm+OYMfEMISexiQKY4tUa/u9Qo=",-1648532199462952362,-5343424272719919583,4321207690090314313,-1602490537518335579>()) {
                        case -613683603:
                           if (var4 != null) {
                              if (var3.tickCount >= this.gR) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2u8sac7yg2vpr","DJ1Fnn0ShH9o2MEhrzIo712ky1jZd5H+LV5wHzsPezs=",-6652155305122264409,677495055363512914,6139920423909494179,8823084843136409020>()) {
                                    case -31874052:
                                       if (var3.tickCount >= this.gV) {
                                          com.yiyiaddon.e.j.c.a.a var5 = this.d.poll();
                                          BlockState var6 = var4.getBlockState(var5.n);
                                          if (!var6.isAir()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2piuawwzl706m","D+pOjxwU6KoTAzX5jVH8CvUPH16Dscvap3QnHPcIylM=",-8525369659454463925,-6597347441963306031,4111890512719012199,-2099192490366827048>()) {
                                                case 225515662:
                                                   if (var6.getBlock() == var5.f) {
                                                      if (var6.getBlock().defaultDestroyTime() < 0.0F) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"sixsjidf21ir1","2HLEipgIXl7M3r69adW/I8VXe1RJAFdcZMgkmFmzwfc=",699850206142608183,7127652042140017716,-820740800711129448,-9065203011334062464>()) {
                                                            case -93620736:
                                                               return;
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      if (this.a(var3.tickCount, var5.n)) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s5wqxnfdb2zv8","JS49lfTycM/jJXSjRuC5B/At3Cm7Xhl5EYLx1V/0iUQ=",-7109621955902434175,-698226072903090473,3183622280394389810,-1705734998944600810>()) {
                                                            case -1892536973:
                                                               return;
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      if (!var3.isWithinBlockInteractionRange(var5.n, 1.0)) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3ky41m8jsjo0w","UYWWxN6ebSWuIR/pz6utf9OZj+cJ+uh1EgOJrAoeFNE=",7288811435926952193,-4147123388984287141,1731402798239529053,5278488224698152228>()) {
                                                            case 1813362908:
                                                               return;
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      this.gV = var3.tickCount + 10;
                                                      this.a(var3, var4, var5.n, var5.b, var6, var2, var5.gQ);
                                                      return;
                                                   }

                                                   switch ((int)com.yiyiaddon.m.b.a<"s2vtkk0h399gti","BB+tteAQYS+VcsrTymUIXUmvJpEj/brUGbh5RVJ95mY=",-3545736253024966493,7361639956035076232,-9102167738047883174,-3419272545328415250>()) {
                                                      case -685237591:
                                                         return;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          return;
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"sa2u3pvm14gi4","nki6WYMs5NUcV+Jmrx2STEEmIkKUX/Ln+3aB1OyXV4w=",8896183547574047906,1333170455154493244,862171868926006086,-3896607528106860770>()) {
                                          case 815546400:
                                             return;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              return;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"sy39145qnq0ay","m1/ciA7e58MR0P45zciee/gBn4CweoucwdK4NLLbtps=",-1383857893826894480,-1748692892407622927,643211061805894671,-1542938812100894671>()) {
                              case 30920187:
                                 return;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2sdle4dxfjpp","JQJQv53Ezecra8fO9SjiJNuwaEdJjG8onR0xIByOKpU=",212593501395084456,5572942578227430130,-3521447523950093016,-7622260199508771771>()) {
                     case 626099866:
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

   private void a(LocalPlayer var1, com.yiyiaddon.e.j.c.a.a var2) {
      this.m = var2.n;
      this.gU = var1.tickCount;
      this.e = var2.f;
   }

   private void a(LocalPlayer var1, com.yiyiaddon.e.j.a var2, String var3) {
      this.b(var1, var2, var3);
      this.a(var1);
      if (this.a != null) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"s1wargt49tw2px","//TRDbXEm8bESoC3WKfTcOBuRXrs2neCRDu3EuKUnHw=",5506833562133206735,7806427014742442989,-5318633740905963439,8624626531645614005>()) {
            case 1495997487:
               this.K
                  .entrySet()
                  .removeIf(
                     var1x -> {
                        if (var1x.getValue() <= var1.tickCount) {
                           switch ((int)com.yiyiaddon.m.b.a<"s24yo9bucamap3","MGu0mQ4eMOBexgepgm8vu/clB6vnyS7NBvRw5Y8kdD8=",-3126203853914052999,741343927999753929,2515502945728997624,-3103420886338870211>()) {
                              case 1823304118:
                                 switch ((int)com.yiyiaddon.m.b.a<"s1asgxh950qrz4","Nc2oy2wFkpwloTK+NkQZU3O0Hy2L7GunGO8Kx4HMp4U=",-8361533444178647021,7186636339971722518,-6650680107051314060,2087812378842859030>()) {
                                    case -1536571758:
                                       return true;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           switch ((int)com.yiyiaddon.m.b.a<"s106ebxo052ks6","Ldt6yaCb0OBwsjqb6at9fpvbpmu1V77K9Bt4lsBMQMk=",-4394560930487688680,-997328133122790563,-2915023372978784090,-4290131671629601753>()) {
                              case -1669147276:
                                 return false;
                              default:
                                 throw null;
                           }
                        }
                     }
                  );
               this.K.put(this.a.asLong(), var1.tickCount + 600);
               switch ((int)com.yiyiaddon.m.b.a<"szo3zh9i82sjo","ttwR3ocOfSuBw3SXeZ4vmdl/OkITpQI5IEvaUC8CrFU=",-7456040979850197249,-1092343472938062276,-7791096968270168700,1292579938697905866>()) {
                  case -160976308:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.eF();
   }

   public boolean a(int var1, BlockPos var2) {
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sdp62qu0bo07h","rGNp21VhiiRoeA7+3D0d+dm08MqYGQJ8bZuIOVYqjKA=",326934596391484616,5274973034901709078,-623676571115765663,-2341291167415230366>()) {
            case 1646059342:
               return false;
            default:
               throw null;
         }
      } else {
         Integer var3 = this.K.get(var2.asLong());
         if (var3 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"scalp1lpnhats","5Lbr6gt3Or/NTJdMg8MnDcCMLDp7JUSWAyBLxM4sCCg=",6224926449755393109,4793534552401304767,5910451855023321195,-5706395343776098266>()) {
               case 57936826:
                  if (var1 < var3) {
                     switch ((int)com.yiyiaddon.m.b.a<"s35wulk6qybzh0","qaMvVUYB7E5lNbWVCIazwr3rjQjuiK32RzYsTsCAQe8=",8951039393257891301,633866830659523485,8067055022906841719,-6576402234188416188>()) {
                        case 72355420:
                           switch ((int)com.yiyiaddon.m.b.a<"s1zdzxpl2yseeh","N3rx5e7MtxBRZ7PADHg11XI/sOBkHNRBqpnSFiYshrQ=",-7216243031772853776,8163234283265432468,-1862913939198829383,-3086336299648626931>()) {
                              case -461294533:
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

         switch ((int)com.yiyiaddon.m.b.a<"sfad777fmesp","Sj6vTuArtGsLm7P8jUJKVOOOg8ISDTxAslEys/UolEg=",4940650074616541995,-2662258064510095015,-8685457680702488946,-3942812204549705555>()) {
            case -1153804141:
               return false;
            default:
               throw null;
         }
      }
   }

   private void b(LocalPlayer var1, com.yiyiaddon.e.j.a var2, String var3) {
      if (this.gS != Integer.MIN_VALUE) {
         switch ((int)com.yiyiaddon.m.b.a<"s269fowq1x5x80","nXXEUsaiUvrGkmIgktGyl6ZU4sLDL/axhFRC4ydgA0Y=",87627058103469825,7008709294440614150,-3662165813591348228,8463341339421209203>()) {
            case 2044330645:
               if (var1.tickCount - this.gS < 600) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1z1py4s2c9akl","VUxl+RF24ZPXOJGV9ceGtv2K03N7RJuTiu2QDuhgx84=",-6715908790565389638,4185934170138997425,-5751885479821656303,6921891573727715504>()) {
                     case -595434288:
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

      this.gS = var1.tickCount;
      var2.K(var3 + "");
   }

   private void a(LocalPlayer var1, int var2) {
      this.a(var1);
      this.gR = var1.tickCount + Math.max(1, var2);
      if (this.a != null) {
         label13:
         switch ((int)com.yiyiaddon.m.b.a<"s2lv0h92k9u3k","enCFG8Pvp4hJYcXnoJ2N5n8CteW+KvMiZmwzkrM+VFQ=",1784457906186089031,6545968005398841518,-7414277317033835075,-8735819333529225761>()) {
            case -1511833748:
               this.m = this.a;
               this.gU = var1.tickCount;
               this.e = this.d;
               switch ((int)com.yiyiaddon.m.b.a<"s1m3g8t0b62y7e","4YwdxJxniexqOnbxIyPCQ01GITQLLjrocWkk68IRb98=",-6759255754820672760,-2898738876068556183,5670374624338172976,-4852001456920783302>()) {
                  case 515548612:
                     break label13;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.eF();
   }

   private float a(LocalPlayer var1, ClientLevel var2) {
      if (this.a == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s14md0bh7giuuk","qNh3ramCfK6mfXAAKhTYcLcSoTBg+hn+1VehuZrOEiI=",3440226485463779710,326771524557518664,-6484287472561256310,2118092068728670934>()) {
            case -751455856:
               return 0.0F;
            default:
               throw null;
         }
      } else {
         BlockState var3 = var2.getBlockState(this.a);
         return var3.getDestroyProgress(var1, var2, this.a);
      }
   }

   private void a(LocalPlayer var1, BlockState var2) {
      Inventory var3 = var1.getInventory();
      int var4 = var3.getSelectedSlot();
      float var5 = var3.getItem(var4).getDestroySpeed(var2);
      int var6 = -1;
      float var7 = var5;
      int var8 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"siuy9kwv832nf","yTAUGVH0fnoeYZVKIq4UixvdXZfv/LVenE7ZCaak3s0=",8918602973846803975,-5297019844545479783,-2269309977386873990,-5845845660136351524>()) {
         case -481845052:
            while (var8 < 9) {
               switch ((int)com.yiyiaddon.m.b.a<"s2w8bjvlni6foj","+QZT1KYLV+lnsC1DmneSc8nuXRRM8T5X6ZOMguQ6+lc=",8050077653934964190,-6515254310010259342,-1109383700565237110,-9130257116887371802>()) {
                  case -757467445:
                     float var9 = var3.getItem(var8).getDestroySpeed(var2);
                     if (var9 > var7) {
                        label40:
                        switch ((int)com.yiyiaddon.m.b.a<"s20mv194o5lc24","3tFOqB6/cciuLN1kDZR9wbAGrUFct7dMskg77dTDtPo=",1654024946728551449,2349556153028922483,2450604398115347715,7348099395999768230>()) {
                           case -814170536:
                              var7 = var9;
                              var6 = var8;
                              switch ((int)com.yiyiaddon.m.b.a<"s29rwzxdbnprju","a95GfnLFvPQxX08epnGJsiVTmkrjJcTu9QEaUbRxkTk=",5013405853581947676,-7715370886915912553,2811892240373839786,-8127463418647027635>()) {
                                 case -39068572:
                                    break label40;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var8++;
                     switch ((int)com.yiyiaddon.m.b.a<"s2cqlvrrznpqjg","0we+/PGsRovJTipl2vLTojZAsjdvRV9bI1CoFrcB4Ic=",3464487449482990747,6434501789423301699,-5862960408764147657,-2147134914533701128>()) {
                        case -730967943:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            if (var6 < 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s1o7exzkznjeam","nPgxHcZtu4+yoATQKxAnI/Dh8dM2oCOseXDSnygMa3c=",-7425585380002922085,1074340436337858852,-6609954794502833183,2715419475874578515>()) {
                  case -1559190662:
                     return;
                  default:
                     throw null;
               }
            } else {
               var3.setSelectedSlot(var6);
               Minecraft var10 = Minecraft.getInstance();
               if (var10.getConnection() != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2kb45kunybsa6","D2//5R2F2uCDSsr6YFsHOLfAS9uW1BNgG0he3GzIdR8=",-7750195178273436799,-5885646136642690532,-1267146333552592662,4921861077058246215>()) {
                     case -795962074:
                        var10.getConnection().send(new ServerboundSetCarriedItemPacket(var6));
                        switch ((int)com.yiyiaddon.m.b.a<"s35gz97ex420ju","nKVBWT1KjsGnDYC64oCH7B41hsu7F//5kwAFB4cytv8=",3098993786198736610,2147829001047698432,-7033833183728674433,2308828491238310695>()) {
                           case 753109979:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return;
            }
         default:
            throw null;
      }
   }

   private boolean a(ClientLevel var1) {
      if (this.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2qfhjj28qwx03","LrkABE+YXXnUYVQ0BYoHq5H4wLJ5nKFzGcXsiqe1Nkw=",-2006946479896344158,4598003360990697429,-4332932480939929226,906807633484814470>()) {
            case 933814708:
               if (this.d != null) {
                  if (var1.getBlockState(this.a).getBlock() != this.d) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3o140d28l2y4c","RLUuKzx80dliejB6b565ohz8EDtAN4vWn/QSWH/tmLg=",-6445727500325821443,3576694643859814463,-2730758535342936990,8677529535662692439>()) {
                        case -1020747988:
                           switch ((int)com.yiyiaddon.m.b.a<"s15xo714o46vqa","4pp78/Wa/npFnVgfnD2A9tOzIt3pLcthpA1BzQL3hS4=",6002830813774540675,-3602502414736171318,2195953798891285123,-8571344893868266297>()) {
                              case -108470933:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s2kmoh17b8fifj","anm9uN2sC6k5AE50++VwvuCJ+jHG1JUiBaIDyhwyfqM=",5357121289374415894,-2430485037702244121,-7710681385852193308,5851569847232475247>()) {
                        case 1722284125:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1v6upbq5v8oku","I4c3IXxFCXqbhBIHtwSAAPZ1cs86X89NWoAoaOhtpnY=",5503933307231376207,-5346261866850071355,4410375799673090843,1254095483101504357>()) {
                     case -152426873:
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

   private int a(float var1) {
      if (!(var1 > 0.0F)) {
         switch ((int)com.yiyiaddon.m.b.a<"s35rdul1lpehzw","yKkSYlGNl14pDesjhQs8V2a6vzShsL939bKBh7ZZ4ew=",-1270999279709724129,6430382646411286291,-3157779432950863224,7465383223715269595>()) {
            case 1385946150:
               return Integer.MAX_VALUE;
            default:
               throw null;
         }
      } else if (!Float.isFinite(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s19w8tracfdt57","cn2ayXkgmzcDKXnSdVPL9pc/f0JehqUZPYxrcMgkD44=",-1886487026139561575,-1878298735035646784,8212529508619608387,-5438543958478643819>()) {
            case -545289914:
               return 0;
            default:
               throw null;
         }
      } else {
         double var2 = Math.ceil(0.7F / var1);
         if (var2 >= 2.147483647E9) {
            switch ((int)com.yiyiaddon.m.b.a<"s35skpc2n59atz","MhVHvq39zqJM6CW1f/JagDayViDsGPpT+crPLsP+3G4=",3738037170252935236,-5010930417616948625,1810505981257589556,-9071777147779235601>()) {
               case 777591023:
                  return Integer.MAX_VALUE;
               default:
                  throw null;
            }
         } else {
            int var4 = Math.max(0, (int)var2 - 1);
            switch ((int)com.yiyiaddon.m.b.a<"s201yddyf79tm1","rUfccV/1kRRbA2d16mZdv49jugjttQCiOarFjOUTbnE=",-4968437109833299971,-1924415415401909868,-1786412564522862066,-8124035575318748748>()) {
               case 2053359506:
                  label71:
                  while (var4 < 2147483646) {
                     switch ((int)com.yiyiaddon.m.b.a<"s27x5qk7qy8qhu","pC2tM2tRjI0tRZNHyiuD9aar2bEtxdUs9X497TZrwzs=",5162291188599617096,-1793572074436162929,-5810385002359290882,3650039911561012146>()) {
                        case 679393501:
                           if (!(var1 * (var4 + 1) < 0.7F)) {
                              break label71;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s3ogd0t2irf9jg","lVYtQFsjIMxQXYI72BGFs9ni2pkyl2ufFOptfUiYSnk=",7563888355923492167,-6289918548130261375,6063714387958555725,4755244289956906193>()) {
                              case -1438704561:
                                 var4++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s28zx2ah29pvwt","j547qDLT4EdaMm45bPHzRS3bXjAtQNzSNu3IgMcR7wA=",422900245484767642,6010098173985647382,-9105226901752132081,9065974963855778131>()) {
                                    case -680104482:
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

                  while (var4 > 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s12bw1zup9g897","3ezCmLt8JmZ91EejghT3ql3Wz0+iZkQoDsha3WNMeYo=",2034971555202053943,-7146609379911131392,-2413009009514659477,260389768364997475>()) {
                        case 238600740:
                           if (!(var1 * var4 >= 0.7F)) {
                              return var4;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s1ci1vn0rmp8mp","HCxfB08nuZTRIMqqxWf93m/yah8ZaPae5tTTAdpP3/M=",2087265901244205289,-6299129077397455423,2859115581974157666,5253571709409206486>()) {
                              case -253814243:
                                 var4--;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1ytwtfs0eq1q2","RYBqN5OO3k9md3qcbz8nPv/waE7YcO/dOzWjp59CM08=",483584306255892005,4067467338952181387,-5137153324388138784,607507754874031626>()) {
                                    case 712675079:
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

                  return var4;
               default:
                  throw null;
            }
         }
      }
   }

   private boolean a(Minecraft var1, com.yiyiaddon.e.j.a var2) {
      if (var1.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1jc63hxdy5gmk","6QzDEhxUAGb4OX1zue9qASYOgCzzHX1I2nEZy6WPWtI=",6358531028030997317,2732263508063213657,-2559339765725213803,-8190930935575382914>()) {
            case -1164027824:
               if (var1.level != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1eg4sd4k48xnw","8Gq2mYCLooS4El2pd7ASiO9C96Sdjt3OJYKvGplSZpA=",-4387982529992816723,3753313968027957468,-6163911856972350986,689434450884037767>()) {
                     case -382367184:
                        if (var2 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3js3pxqfnmtza","iy43Pjfswks6HvDISX1lUN/eUc6BhpDhwtf5BpT+rY8=",2279691373092686732,6716024700548476601,-906503545526262768,-6521971453340230833>()) {
                              case 1285645813:
                                 if (var2.g()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2dg5dtasfg6za","GrNIkTB6tDsvUH5F5xVF2y8KCn9SgYGt9XpsXLVZTX0=",4648420497299524291,-2427145881131967975,6489286957166870578,7203712569559156915>()) {
                                       case 1277922548:
                                          if (var2.bQ()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1dsqc7ebaykge","h5rVxmt0wq/KNtsQPBxaW7M0AdBCst5uGe+TPbiBe/s=",-5499151127825922394,-5934094337144672897,-8560170790762394885,-2058725890231463313>()) {
                                                case 979544786:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2iuo613q9u7ij","MWIHRwYnvdPz5qOdSdB4buloAfgG4OTeJB4DldcUepk=",2691196328143400259,6770413866913234089,-6561674642649459582,1525756305446664060>()) {
                                                      case -2113397555:
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

      switch ((int)com.yiyiaddon.m.b.a<"sy23daudue64m","87qw5C1wNzYfPtT/PnVq8MYTqx8JdD5fX6qDajHaQno=",40127210309976300,-6506817427044982696,5496094767046727928,7947648173104265923>()) {
         case -1544981164:
            return false;
         default:
            throw null;
      }
   }

   private void a(LocalPlayer var1, float var2) {
      this.bd = var2;
      if (this.c != null) {
         switch ((int)com.yiyiaddon.m.b.a<"snmd1pw7ks9mq","M+sAc5UPAZWbbI5ii0xK2hKsRH1vAc/bt0Z6xkjEY1w=",6742821367555966025,7598741394169074170,-1224873352745938718,8039616206059869330>()) {
            case 1649047281:
               if (this.a != null) {
                  int var3 = Math.min(9, Math.max(0, (int)(var2 * 10.0F)));
                  this.c.destroyBlockProgress(var1.getId(), this.a, var3);
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2udlttlosmsh3","3h5GUYYtCS/uUED9c5XW/Ssj2U9gx2CxsjOOkrrpD3w=",-3949154451769335128,-8379479254811346147,-9059025442511598943,8241425217327075411>()) {
                     case 999633453:
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

   private void a(LocalPlayer var1) {
      if (this.c != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3tv8tybgzfs35","5D2BUGWlSecr2d6UrpXSjJtOrcZbFvDHMaBZUh7o2DU=",4009275948659577536,-5561288000029091300,1972284381740550603,125862719690714470>()) {
            case -785656376:
               if (this.a != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s360nu47hm2r5s","aGtGP3wyxNan9KlP5xJ2bVeV8+fhaV4Br88wUy4Gy0A=",1919005533582005535,-859580338574093353,1617920908404850106,-6042465832506890892>()) {
                     case 373377:
                        this.c.destroyBlockProgress(var1.getId(), this.a, -1);
                        switch ((int)com.yiyiaddon.m.b.a<"s30o0jm9ybzhnp","BM4dsghFsRbZvDiKLY/k/3+PNEr3UvYpB8CSHD8x9bk=",7569145317592898866,-4446323012807021364,-1481879069128779540,-4334956387845783784>()) {
                           case -211633698:
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

   private void eF() {
      this.c = null;
      this.a = null;
      this.a = null;
      this.d = null;
      this.a = com.yiyiaddon.e.j.c.a.b.IDLE;
      this.gP = 0;
      this.gQ = 0;
      this.bd = 0.0F;
   }

   private void e(Minecraft var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"spigmcptpxb3a","uZabPIWkA5Pg/xuFJDXfQ5GV85FC8E/G3xuhfepsY7Y=",4673121228684136179,8744891916279179301,-444175847787503451,-3668516282658453693>()) {
            case 22410868:
               if (var1.player != null) {
                  int var2 = var1.player.tickCount;
                  if (var2 < this.gT) {
                     label20:
                     switch ((int)com.yiyiaddon.m.b.a<"s1bttk7w330cag","3tGchOGSLERR40K17KUS1rGQkvook2Ip7iFvwUIz6kA=",-944753141931479304,-3964989832229476186,-8109226743374321821,1064424747055537167>()) {
                        case -195061795:
                           this.eG();
                           switch ((int)com.yiyiaddon.m.b.a<"s1akx95jx7trrv","sPuyFYQPh9tln0JOX+fA+USCtFOiiJPkjK1YMKkAOog=",-151572725903758157,5273570152727518687,1538841322712291071,5974714636081733240>()) {
                              case -671764546:
                                 break label20;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  this.gT = var2;
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sxrsxawbculns","vjLyfkcxwB9XAw1M6/wPeENuSxqc1aDrKddOcqhc8mI=",-6913422079579055676,-3604385047381012224,-7620759265946685097,4088406719038462665>()) {
                     case -666489656:
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

   public void eG() {
      this.gR = 0;
      this.gV = 0;
      this.L.clear();
      this.d.clear();
      this.K.clear();
      this.gS = Integer.MIN_VALUE;
      this.m = null;
      this.e = null;
      this.gU = 0;
      this.eF();
   }

   private static final class a {
      final BlockPos n;
      final Block f;
      final Direction b;
      final int gW;
      int gQ;

      a(BlockPos var1, Block var2, Direction var3, int var4, int var5) {
         this.n = var1;
         this.f = var2;
         this.b = var3;
         this.gW = var4;
         this.gQ = var5;
      }
   }

   private enum b {
      IDLE,
      MINING;
   }

   public enum c {
      PASS,
      ACCEPTED,
      COOLDOWN;
   }
}
