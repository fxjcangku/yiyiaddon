package com.yiyiaddon.e.n.m;

import com.yiyiaddon.e.n.i.r;
import com.yiyiaddon.e.n.j.d;
import com.yiyiaddon.e.n.j.e;
import com.yiyiaddon.e.n.j.f;
import com.yiyiaddon.e.n.j.g;
import com.yiyiaddon.m.b;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public final class a {
   private static final Minecraft ac = Minecraft.getInstance();
   private BlockPos g;
   private BlockPos h;
   private BlockPos H;
   private boolean L;
   private final Set<BlockPos> M = new HashSet<>();

   public static BlockPos d(BlockPos var0) {
      if (var0 != null) {
         switch ((int)b.a<"s1tms0d9e1xkhd","c03ChUc49Ol7ge0z7+I65fEjvhC509yAWEorepJ1NgM=",7917919842023530355,8260121193649325017,-5956638430984325621,-4534791691764921637>()) {
            case 389654707:
               if (ac.level != null) {
                  BlockPos var1 = var0.below();
                  com.yiyiaddon.e.n.j.b.b var2 = com.yiyiaddon.e.n.j.b.a(ac.level.getBlockState(var1));
                  if (var2.a() != f.DRY) {
                     label29:
                     switch ((int)b.a<"s1tr4yd6o7ok3q","QuQ+cnRTpUwgMGWWwDr7T9DKWOEtyWRsomr6ZD5S0qs=",8453629896530582957,8810213213106197663,-3663994963343110720,6704129770847606416>()) {
                        case -1512249572:
                           if (var2.a() != f.WET) {
                              switch ((int)b.a<"soj8rjhdavl8m","1/T63jdJ+qkMZSIKPQv5BaG56ZUfvAfc3268nKi8ChM=",2276920778100244362,-4428191569622742231,-7547455405551135316,4073023879837522302>()) {
                                 case 127504662:
                                    return var0;
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)b.a<"s2p9uoy3baz9e7","mkP020oZYz4y7dJB+YcqLPKZWyP5p0XVc+MtgoXPF3Q=",4474366451871143362,-6163917904793172542,8524643216424793823,-8708713534984918603>()) {
                              case -2129592152:
                                 break label29;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  switch ((int)b.a<"s2pq0m492c0413","rwqgdlVZOmfRYybCJrLE02L5juWDhfcyQwmI2WIww08=",7423034563678069453,5904114041870783905,-7109093060543731454,3597045744371779270>()) {
                     case -944208318:
                        return var1;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)b.a<"s1lv507f4i5d18","tHHKpQQsYK8upSruTDIqe4OjCrhiuGBh9RocN7swWkc=",5043969176500706359,7894143079712768135,-8466995186943947706,2929495987768063381>()) {
                     case 268059650:
                        return var0;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return var0;
      }
   }

   public void c(BlockPos var1, BlockPos var2) {
      this.g = var1;
      this.h = var2;
      this.H = new BlockPos(var1.getX(), var1.getY(), var1.getZ());
      this.L = true;
      this.M.clear();
      com.yiyiaddon.e.n.j.g.b(var1, var2);
   }

   public void f() {
      this.g = null;
      this.h = null;
      this.H = null;
      this.L = false;
      this.M.clear();
      com.yiyiaddon.e.n.j.g.f();
   }

   public boolean M() {
      if (this.L) {
         switch ((int)b.a<"s1a60avv4gjemm","eUWpst6/SYExADge6BlITRv7aptJmfgbGWZIlnAmrGI=",-8423416585345586497,-8839137256493109266,-6442102284473341591,-4911102573264661471>()) {
            case -1613768637:
               if (this.g != null) {
                  switch ((int)b.a<"schj4xod8r9up","mFAGFDO7O9TCV8nD3gIIJz7buuxsVT9wcvDbIqxVwUo=",-5610676602263487161,-4555112220375791698,-999907927303885083,7020856195859975766>()) {
                     case -1546167026:
                        if (this.h != null) {
                           switch ((int)b.a<"s18kc217ahjb5x","yw2CMijEuJ00oflILeziMUzlzHevzN2v7UwiOAyp/SA=",6695761057040235245,8525049369482917655,4710669712062820276,-4257346782796984810>()) {
                              case -729225132:
                                 switch ((int)b.a<"shnsbsviuc8lv","PUV3Gg0WlxdtHbkw24FwJQ7/A+/Dq0+KYIdcjjMTzVk=",9135893797317669489,2655905221907618385,5041327087413976412,-7950341118736157621>()) {
                                    case -1518321872:
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

      switch ((int)b.a<"s1zg1uwynpfaa2","7a7gH+9m/Vz25uY+pNiM2KvW1mihb1Zr5hv667gN9JA=",8629941046717138092,4037973551949304493,-4835619696662681537,-8179240182658865018>()) {
         case -1354971093:
            return false;
         default:
            throw null;
      }
   }

   public BlockPos f() {
      return this.g;
   }

   public BlockPos g() {
      return this.h;
   }

   public boolean aJ() {
      if (!this.L) {
         switch ((int)b.a<"s1dva3l7qd06ux","dYCTot14E9IoeuUi1NT/MAIzDXAIZLQsmvvDfwHWpiA=",4903388645341092823,-1883774320165790330,-6633363520442665711,-1907644527097477601>()) {
            case 1046083063:
               switch ((int)b.a<"s1jl69ws1m04tv","q/hq83L5XV+lXxQxCaiIGIZO8clMkNV0tOMbVqSzeAM=",3116580552732204330,3392448615651180873,-3746969859008863673,-2546745651417956695>()) {
                  case -1709291963:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)b.a<"s1siiu6fao64l3","o1W40ZOxlJhBz+q5fQO3f1JbQ7ZXiHrbSeS612j/5ZE=",6763675959185718211,7455216652443499107,8852033707972778044,2623898557449515626>()) {
            case -761729104:
               return false;
            default:
               throw null;
         }
      }
   }

   public List<com.yiyiaddon.e.n.m.a.a> a(r var1, int var2) {
      ArrayList var3 = new ArrayList();
      if (this.M()) {
         switch ((int)b.a<"s16ialyrjsv11j","/SlI/aM6hVZ17aIH+0R6bFWS4wGbk0afVSiGxGN4ZNo=",-6264854355203472634,-7143939465161096089,5365786389208566450,-1375813419579202394>()) {
            case 585946791:
               if (ac.level != null) {
                  int var4 = 0;
                  switch ((int)b.a<"slp91jqly1hng","5KBe6fdLabITbWzizAJrEzVnZgDJGev3CDaLcxzI14A=",7190497279930594909,-1916103124240803402,-9086862228055572381,8804139530711683196>()) {
                     case 2001249116:
                        while (var4 < var2) {
                           switch ((int)b.a<"s3p5zu3sh4uhw3","VSjY0H+1Xqa4bH2Ar/9V0xcPKswFtX0CMB8fDBiEMFs=",-2526272623077999390,-5536486393551603287,-3774658881107299606,-878894092169699278>()) {
                              case 2090976612:
                                 if (this.H == null) {
                                    return var3;
                                 }

                                 switch ((int)b.a<"s27qrrisr8sqbj","U2KU6sjehzmeGLMcSsE6dhCr3uNoDbHbcccvkyHnXmA=",4656260174636943496,6223241954358237961,6545233866449774020,1861594023966557324>()) {
                                    case 152723698:
                                       BlockPos var5;
                                       BlockState var6;
                                       com.yiyiaddon.e.n.j.b.b var7;
                                       com.yiyiaddon.e.n.j.b.a var9;
                                       com.yiyiaddon.e.n.j.b.a var10;
                                       BlockPos var11;
                                       com.yiyiaddon.e.n.j.b.b var13;
                                       boolean var10000;
                                       label213: {
                                          var4++;
                                          var5 = this.H;
                                          this.gK();
                                          var6 = ac.level.getBlockState(var5);
                                          var7 = com.yiyiaddon.e.n.j.b.a(var6);
                                          BlockState var8 = ac.level.getBlockState(var5.above());
                                          var9 = com.yiyiaddon.e.n.j.b.a(var8, var1);
                                          var10 = com.yiyiaddon.e.n.j.b.a(var6, var1);
                                          var11 = var5.below();
                                          BlockState var12 = ac.level.getBlockState(var11);
                                          var13 = com.yiyiaddon.e.n.j.b.a(var12);
                                          if (var13.a() != f.DRY) {
                                             label158:
                                             switch ((int)b.a<"s2dg2j4ylnvrlx","moTXI+yY5WLitUuqdD8fbnePMUYi2SzfFUlhPTGGbyI=",-3916744936388481478,1554797445439488189,-2187941896010787808,7222861913912534040>()) {
                                                case -1123707632:
                                                   if (var13.a() != f.WET) {
                                                      var10000 = false;
                                                      switch ((int)b.a<"sx8l13z7r29bu","tufKu3O28xUPtgEWBWpsuTJslv0Il3Bws/cd2G/dIVM=",-7276224188336460277,1783675135554336636,-8290947996656276870,7077560176772949670>()) {
                                                         case 682041543:
                                                            break label213;
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   switch ((int)b.a<"s3bed5xf5y2569","E3f3oQXmCPhqlzPQHt78UlrrQujej66fBMFMu5Lxymk=",-9010334146834086824,6233301236039775339,-4179439074980982918,316329161548930674>()) {
                                                      case -1822627791:
                                                         break label158;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          var10000 = true;
                                          switch ((int)b.a<"s3d16rb0y0u3e9","O9e96b2kEbwxA49+/VJMtbIL+WzZMOCBrx76ahkiYmo=",-1475236947319015291,1406839077672658506,-5305505330105960385,4561674124753525196>()) {
                                             case 890246305:
                                                break;
                                             default:
                                                throw null;
                                          }
                                       }

                                       boolean var14;
                                       label206: {
                                          label205: {
                                             var14 = var10000;
                                             if (!var6.isAir()) {
                                                label150:
                                                switch ((int)b.a<"s2j26woog7fady","nm5nYp6rGPdtedwCbNeu2p9BiOMuL0bxCSYc664f4VQ=",-1184172063062338392,-1093537930655521020,-569069193398740478,258619334013584889>()) {
                                                   case 686775587:
                                                      if (var10.a() == d.EMPTY) {
                                                         break label205;
                                                      }

                                                      switch ((int)b.a<"s3lhfczlgot7kl","I6Zp6P6Z6bzTqamxpbonElTstTLJKIlCBv5/sK85YNc=",-7217875415441611416,-1798113778312864498,8639409298163313265,-3678874360204090956>()) {
                                                         case -1108552258:
                                                            if (var10.a() == d.UNKNOWN) {
                                                               break label205;
                                                            }

                                                            switch ((int)b.a<"s1i5rz5sjip1wk","B1wRyI93GfcIRIEWesBkwKKue/7xMuqAHMMke/kFnKw=",-2060981126237667999,-3458196421979753808,-4289047637815047533,2049318327466032737>()) {
                                                               case 2103250583:
                                                                  break label150;
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

                                             var10000 = true;
                                             switch ((int)b.a<"s3sysl5e8v34jl","YXcDdp0RbORErWH3QvIOkhyrhb9J0OqKeXif3AvDzog=",-9112663330647611478,-8668070057375394951,-5149000715650030697,3472865504144818762>()) {
                                                case 1555744210:
                                                   break label206;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          var10000 = false;
                                          switch ((int)b.a<"s2rdo0m77lv1og","6E09lhY+pEgDKmdnC3L8tDlAPsZy6WeYzYo5bXTDHmY=",-7807979552966550439,-4716478938464833411,5930296700569765722,-3543923224731272743>()) {
                                             case 535563014:
                                                break;
                                             default:
                                                throw null;
                                          }
                                       }

                                       boolean var15 = var10000;
                                       if (var14) {
                                          switch ((int)b.a<"strxvgft8ogv","9hDgUvpLNFLF7wJ2bhKv70D2fATwzLQZch2Lu4EACns=",-441995957895858604,5178974129054363204,1646030139090461222,4960462393743155363>()) {
                                             case 28844091:
                                                if (var15) {
                                                   label138:
                                                   switch ((int)b.a<"s2e6yp75my84cz","SoPchgcLz6tNa6ynT9iygH2e5tXx7C/PtEOSrS0GASs=",-6024483150965030283,786133781723791432,1018815670324153415,-7882091559642292712>()) {
                                                      case -84567675:
                                                         var5 = var11;
                                                         var7 = var13;
                                                         var9 = var10;
                                                         switch ((int)b.a<"s3arkn31t3265a","wl5K570rTJ5qypjH9WgcSMO8lNY6kP9dynZQMJMHY08=",-2870759493839611377,9143351904527308851,4549625039187035779,-8746113564769474490>()) {
                                                            case 473286987:
                                                               break label138;
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

                                       label193: {
                                          if (var7.a() != f.DRY) {
                                             label134:
                                             switch ((int)b.a<"s34pwjskd8pzms","ZNkLu6VoHBg8oBU5aEi0vIGwJF+k+MoSj3ZLq0Raorg=",-8322961640633696566,-4442296932374928117,-813150884597031738,-7295044788982256309>()) {
                                                case -1234110979:
                                                   if (var7.a() != f.WET) {
                                                      var10000 = false;
                                                      switch ((int)b.a<"s308c11x6hdwvi","fF/fntuhYwmTiXxZWV/iE+ylAilXBWK7FJw5Tzh6i7Q=",6108022198706323114,-8385084759481070665,4886774112785094689,-7098342910618846885>()) {
                                                         case 161539609:
                                                            break label193;
                                                         default:
                                                            throw null;
                                                      }
                                                   }

                                                   switch ((int)b.a<"s3oko2pd1f1hj0","ECRc/xYK1HLWEyGjR0I6SgpM9FCfbGd840NYsU1PgF8=",-636384398887697391,-160113654163466059,9209668861646820673,-8798711364976160537>()) {
                                                      case -1813425626:
                                                         break label134;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          var10000 = true;
                                          switch ((int)b.a<"sp5hg4ueljknn","17EFymg/S9LGurMReEf22TegdQu/7kBdyEdgW8odk/U=",5825695450038150346,7384173733330499162,2758899868357443909,2795368970219016684>()) {
                                             case -772689558:
                                                break;
                                             default:
                                                throw null;
                                          }
                                       }

                                       boolean var16;
                                       label186: {
                                          var16 = var10000;
                                          if (var9.a() != d.EMPTY) {
                                             switch ((int)b.a<"s2a32k87eb3djl","sLKiMxOydfU3Hpel0J3kJjLjm98txgmKu9J89mKVCEw=",-2212051990527006350,-5571491683878673954,-1056639152307292721,-5570845372665295657>()) {
                                                case 1057603358:
                                                   if (var9.a() != d.UNKNOWN) {
                                                      switch ((int)b.a<"svviufgimsfgg","/vq0fMHxA7xIkOKHC52U/P0vCq3hInbW+XmMHXfEXqI=",2670622374688077023,-6699919623546332550,8056158306252110840,-5203295844786679866>()) {
                                                         case 799248802:
                                                            var10000 = true;
                                                            switch ((int)b.a<"s3eerbuluxydq4","AApEVaIdMOiVa6nMB0SFUKt814dszsybl5mc/DBHhZs=",1903714658651330114,-1338333530643201056,8947787949618761202,8400185803007115136>()) {
                                                               case -718165619:
                                                                  break label186;
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

                                          var10000 = false;
                                          switch ((int)b.a<"s1zpa2depbrdk7","y9bFdaRpMCynUxX3YrcuWSbLAWp+z9iR6Dor6edNn6Y=",-8217656200165228554,8391335374944004744,-4523937157157289277,7536635595959875927>()) {
                                             case 85883591:
                                                break;
                                             default:
                                                throw null;
                                          }
                                       }

                                       boolean var17 = var10000;
                                       if (!var17) {
                                          switch ((int)b.a<"s1hrdcn5gtj2d6","NPgktbVDm177TnIj8dsUs8OLeCZs6gZQfa4bmGqmt0s=",-4340068195409823728,7468057961782015264,4081278755681418126,4618092162660341026>()) {
                                             case 1360796096:
                                                if (var16) {
                                                   switch ((int)b.a<"s1zzqjud4p75la","aCDVLfC9KJvSEIyQJJ7yhhUgPCYfoL2azt5Fz4VAlDU=",562190037507383960,-194427924395252533,-5851632552759622411,-1704181324777996435>()) {
                                                      case -392305845:
                                                         com.yiyiaddon.e.n.j.b.a var18 = com.yiyiaddon.e.n.j.b.a(var5, var1);
                                                         if (var18.a() != d.EMPTY) {
                                                            switch ((int)b.a<"s1ayzrjsi09pnc","4R242AKUIAE82BOQVjJG8Ijn6EfxXMsIdfIER5HF62Y=",-4283848360207281448,-7783534800717149173,6690621536644691209,-8109197683992084917>()) {
                                                               case 1093627365:
                                                                  if (var18.a() != d.UNKNOWN) {
                                                                     label113:
                                                                     switch ((int)b.a<"s3bj1a6durzrwp","JFgf+XjOVOPKKjMDqYLoHoy+kRcUqitb8Eqwr1qwmnk=",2582612526134411102,-9130033890299326109,-5114781623002728519,4123507680903013306>()) {
                                                                        case 719116060:
                                                                           var9 = var18;
                                                                           var17 = true;
                                                                           switch ((int)b.a<"sjlsf8dwdmqbv","31tGMN4VY3oqr9fN75Ttl5Zv5IyIDQuqUFrr2sM+b5o=",2247412276389311331,-6740829162031492566,2007547894369329442,-8397951548087272184>()) {
                                                                              case -1261317320:
                                                                                 break label113;
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

                                       label179: {
                                          if (!var16) {
                                             label109:
                                             switch ((int)b.a<"s60q2t60f6w20","Ms8ucNjkNFaNvJaIHNsjTIgwOZ+Sj1HuG7Vfs9Iyk4s=",-592951074206379105,-3804839655943797330,-3789898171626032912,-5428830134297415299>()) {
                                                case -687060955:
                                                   if (!var17) {
                                                      break label179;
                                                   }

                                                   switch ((int)b.a<"sqr5dy321y1ke","36tF3CVlXnBObXkTTV1etBJp8dIWj+fOcCnIOY90r3Y=",-990348755648459500,2130873416826513832,7301399648768828863,-3022709580345662868>()) {
                                                      case 1123826750:
                                                         break label109;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          if (this.M.add(var5)) {
                                             label105:
                                             switch ((int)b.a<"s1k5evyzpv5r2g","tpcQf2UvlTtBfnyiz/KQkpebbo4Hz0q34S+JMTN97F0=",-6886502747714419763,6080357844992944713,-5360756726955516551,-8812001553939439726>()) {
                                                case 603218605:
                                                   var3.add(new com.yiyiaddon.e.n.m.a.a(var5, var7.a(), var7.dU(), var9));
                                                   switch ((int)b.a<"s166pevwc4vac3","msEvabNxptFPckHRQazbRa9NQv3Ezw4n+GLZ/BF7sYQ=",-3259723751787414783,-7415290916716892728,-7691276780083699379,-3966734488941983550>()) {
                                                      case -688781052:
                                                         break label105;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }
                                       }

                                       switch ((int)b.a<"s16cxnqtdflqfr","diEQFvCfNshV5/Dggisdbca0JooixvFa0EBC4QGYR8U=",-6690970588298292962,3139815238742838809,7245569080515217767,3246520502706359128>()) {
                                          case 1815353593:
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

                        return var3;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)b.a<"s2mqzyo2gsmoiv","us6Di/wcPZaedm39mw/DvXFjjdD5Ij49hBe77RoijHo=",-3181698279625555324,5349828824120684270,902485068334694470,8298694643107262393>()) {
                     case -456205200:
                        return var3;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return var3;
      }
   }

   private void gK() {
      if (this.H == null) {
         switch ((int)b.a<"so6vrdjph5dpi","2VKxtVxZz/tiIvgiUg2pWzNxlkU4s5PG6Jck+bq/7Oo=",-7674237202648565665,4241118536314000659,6880869195166360354,-6514938562564271924>()) {
            case 731747982:
               return;
            default:
               throw null;
         }
      } else {
         int var1 = this.H.getX();
         int var2 = this.H.getY();
         int var3 = this.H.getZ();
         if (++var2 > this.h.getY()) {
            switch ((int)b.a<"s205rgb09sncn3","HCjz5ns+nJBRledFUEmxTpPxfMS21Js5OYD85Ym8jUk=",7329707948754470899,7411141132827051782,8193504762018615077,3935671973090833194>()) {
               case 1530032017:
                  var2 = this.g.getY();
                  if (++var3 > this.h.getZ()) {
                     switch ((int)b.a<"s3tz02m8wpdxqx","PJw06Xt4g88Igfy95unAMgukCoCBOW5ytfgktU5+Ilo=",6943065127576448723,465693390354214007,1244810101816348308,-4774528663301585610>()) {
                        case 451218513:
                           var3 = this.g.getZ();
                           if (++var1 > this.h.getX()) {
                              switch ((int)b.a<"sazohbz0whltv","xei+tnWz6RI3sJ0SIzm5lMapLA67j6A1YkTWc67pXdk=",-430106929541422482,-1522013991318159308,1200746419783990809,5762932530692783600>()) {
                                 case 1695605696:
                                    this.H = null;
                                    this.L = false;
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

         this.H = new BlockPos(var1, var2, var3);
      }
   }

   public record a(BlockPos I, f b, String tL, com.yiyiaddon.e.n.j.b.a a) {
      public BlockPos r() {
         return this.I.above();
      }

      public boolean dg() {
         if (this.b != f.DRY) {
            label22:
            switch ((int)b.a<"s2pq67ntxd8asz","Jf0KuaEDCMP8Z/p7G9gouYe3xhO+LmOcGj32jDPtr3I=",6330087388917396501,-183464886531847294,1467897394514188191,4052479220540525172>()) {
               case 671578786:
                  if (this.b != f.WET) {
                     switch ((int)b.a<"s2yfux8azbns22","+1Q+7anqYCvtPDA3XYGDsGQgKhiNnjQ7bKR7/4jY74M=",-614783309694679527,5909743398462199298,-7689878148364818144,-7287362951951153464>()) {
                        case -1465882506:
                           return false;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)b.a<"s2za0bizdt98oe","qZzz7C4tNQEx/dQHG14RJKsyXhgeDg+NhyBLAQytgTU=",4823560455736914236,-5501025568331390805,5642884236552094457,-7298142102719172765>()) {
                     case 1242306963:
                        break label22;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         switch ((int)b.a<"s2y3jy82pclqft","tOoNo3a0uu9tzysIfZvpr7dKv4YxIG/dyR8ae3DbJYI=",2256596245600064525,3700711205531295723,2547136772021158154,7955422154348413766>()) {
            case -1575116339:
               return true;
            default:
               throw null;
         }
      }

      public e a() {
         return e.d(this.tL);
      }

      public BlockPos s() {
         return this.I;
      }

      public String dU() {
         return this.tL;
      }

      public com.yiyiaddon.e.n.j.b.a c() {
         return this.a;
      }
   }
}
