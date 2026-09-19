package com.yiyiaddon.e.c.i;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;

public final class c implements e {
   private static final int bc = 20;
   private static final int bd = 400;
   private static final int be = 50;
   private static final double j = 2.25;
   private static final int bf = 4;
   private final BlockPos i;
   private final double k;
   private int bg;
   private int bh;
   private int bi;
   private int bj = -1;

   public c(BlockPos var1, double var2) {
      this.i = var1;
      this.k = var2;
   }

   @Override
   public l a() {
      this.bg++;
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s18zjimda85tez","yX/fg/IQOJGUk9Ea3wcU+yV/N578msS//BOuYHJDRiY=",-764473149591791360,4003577055267475349,4031262512076349483,-3396126942035048527>()) {
            case 195682805:
               if (var1.level != null) {
                  if (this.bg >= 400) {
                     switch ((int)com.yiyiaddon.m.b.a<"s11imhcior443o","HUihQQQ1GNB/gXw5tUHcXNbMXNlxjom6E2aJ1Wp0wG4=",9175412503702412368,-7594973619378887775,5180048351601828838,1313302098129054222>()) {
                        case -167839900:
                           com.yiyiaddon.i.c.a.i();
                           return l.SUCCESS;
                        default:
                           throw null;
                     }
                  } else {
                     ItemEntity var2 = this.a(var1);
                     if (var2 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2fbrw01x90ver","Mu6RdrB9MyV55S+LNDyhju7UfDg8X54rT+zj+Mnw9ao=",3413317242674154830,-8518969627671802327,2905503982303333302,4613877872082889002>()) {
                           case 274816482:
                              com.yiyiaddon.i.c.a.i();
                              this.bi = 0;
                              this.bj = -1;
                              this.bh++;
                              if (this.bh >= 20) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1jwryp7xei0n5","HzmGui7Pa5MGPuepFFk7nE960JwegqjDrHDBcYmVa8A=",3987156431374215971,-6014531111849397691,9153976789813260673,271732983869146160>()) {
                                    case 526163306:
                                       return l.SUCCESS;
                                    default:
                                       throw null;
                                 }
                              }

                              return l.IN_PROGRESS;
                           default:
                              throw null;
                        }
                     } else {
                        this.bh = 0;
                        double var3 = var1.player.distanceToSqr(var2);
                        if (var3 <= 2.25) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3p32p0mp7ngd8","MPPXZ00o31H8k/g8ckE79WS4CZXVa9ORKb8mQ5m6NpI=",-4160254319250500609,1473417394479693248,-1274441686252637075,3040242136515068216>()) {
                              case 81972684:
                                 com.yiyiaddon.i.c.a.i();
                                 this.bi++;
                                 if (this.bi >= 50) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2fpmy6r42yfyl","AYODIcH+7eMe3Wp+fTMySXOg2z4Vdaq1iWJNHgUKEsE=",-8089930039798337274,-3606154359964371182,-8284988630150481386,-39180790962678704>()) {
                                       case -1896125914:
                                          return l.SUCCESS;
                                       default:
                                          throw null;
                                    }
                                 }

                                 return l.IN_PROGRESS;
                              default:
                                 throw null;
                           }
                        } else {
                           if (var2.getId() != this.bj) {
                              label89:
                              switch ((int)com.yiyiaddon.m.b.a<"s16eojvgu6ob2f","OkWTbGAZkh68d40oEPOg5arsfYJ2YwTvieHm93Jo1Ew=",8690653488255350044,-414826569768448180,-3213749042885754926,-4119745135275189049>()) {
                                 case -1897660492:
                                    this.bj = var2.getId();
                                    this.bi = 0;
                                    switch ((int)com.yiyiaddon.m.b.a<"sbktjj1ugnuam","sCB0rXo+sS7mJD3bepG3eDdn6Ys1uzQEjGM3Vg3ZDwk=",7215600990532123119,4240067508434498008,1683222696795809551,2772110515345622113>()) {
                                       case -1717260563:
                                          break label89;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              this.bi++;
                              switch ((int)com.yiyiaddon.m.b.a<"s2rzxyj8t158pn","ORpd8nUPWvqbxF/1O1mnYtkB0FV1boexaTOGUGK7Dks=",8280042824535815385,-3875986074556473126,3680081493258432396,1797616106323283413>()) {
                                 case 877698549:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           if (this.bi >= 50) {
                              switch ((int)com.yiyiaddon.m.b.a<"shxzq3mf8ftm6","CLO7B0qzPNHwP0vkGY1yUTeElDDf149fN/wRr3a/m0s=",3767142834886326044,9098475718776477929,3943638045549709276,8737872742772456695>()) {
                                 case -61508349:
                                    com.yiyiaddon.i.c.a.i();
                                    return l.SUCCESS;
                                 default:
                                    throw null;
                              }
                           } else if (!com.yiyiaddon.i.c.a.ft()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3vany2s10ajas","4WtFzQcQitqKZCG3H3TCbTWi6/cVjIAx2K0bDNTspbA=",-3206569482598262486,-8171837296670222531,-6793815592111730881,-8472152410450270088>()) {
                                 case 2035597547:
                                    return l.IN_PROGRESS;
                                 default:
                                    throw null;
                              }
                           } else {
                              BlockPos var5 = this.a(var1, var2);
                              if (var5 == null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"spm8iczr4cu14","N7Wcqbd4X8MR+41vQVM86EgPiK5WDjQGuMuy0jDshNs=",5690061283625997231,5235704276057505427,-1028537342210025276,7322729641725925176>()) {
                                    case 1460594717:
                                       com.yiyiaddon.i.c.a.i();
                                       this.bi++;
                                       if (this.bi >= 50) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2f5rkj5y3cymx","aml+pSpWAQnSG20sSFWMgboV0XctnrnR8b4Ff5ext5M=",3430140638787925754,-1043983770513975714,542429465072508174,1956711290442862417>()) {
                                             case -1252718196:
                                                return l.SUCCESS;
                                             default:
                                                throw null;
                                          }
                                       }

                                       return l.IN_PROGRESS;
                                    default:
                                       throw null;
                                 }
                              } else {
                                 if (!com.yiyiaddon.i.c.a.cX()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s6vi161gnbkbc","LttankE9cv28f7kItSDB4YvXfkpJxk2IH78ghW40Q1s=",-3896744059645378544,7348668161195926056,-8260225398838161028,3184025852783817241>()) {
                                       case -172504626:
                                          com.yiyiaddon.i.c.a.b(var5, 1);
                                          switch ((int)com.yiyiaddon.m.b.a<"s2c9h99v989idl","bwBMqiCqrwZntdn59k7LSQQRjSnEM3EpjPlVoo9UBzY=",-1140354210705879134,6991330683509845108,2425336041370275097,-7908398061444947901>()) {
                                             case -1616371290:
                                                return l.IN_PROGRESS;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 return l.IN_PROGRESS;
                              }
                           }
                        }
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3uo8xfnkvyxb0","xxBf9H4HHZY5nPxPU81etd1QLnzdUvrkqgrEKskGFbY=",3820307294508979602,-6708736178224761414,-3587461418125837113,-8703262225317189567>()) {
                     case 1635731623:
                        return l.IN_PROGRESS;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return l.IN_PROGRESS;
      }
   }

   @Override
   public boolean O() {
      return false;
   }

   @Override
   public void i() {
      com.yiyiaddon.i.c.a.i();
   }

   private ItemEntity a(Minecraft var1) {
      double var2 = this.k;
      double var4 = var2 * var2;
      double var6 = this.i.getX() + 0.5;
      double var8 = this.i.getY() + 0.5;
      double var10 = this.i.getZ() + 0.5;
      ItemEntity var12 = null;
      double var13 = Double.MAX_VALUE;
      Iterator var15 = var1.level.entitiesForRendering().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"sxgueyvaag8fm","GhPA2wTL3WgS84gVMKOkUOW0W4DEZSpsucyAbv5ws3o=",-1519184696841264954,-5615041748308748153,6606508204200220913,-3151108093691438363>()) {
         case 1047339420:
            while (var15.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3hi4hpap9wv0x","RCcQSDAop3naMm24AhJ0vFWdiXT9jVrey8rQD9uVvFU=",-4261602748739870277,-6067418879402670316,7401397032312899007,-3166028435312042015>()) {
                  case 1592715858:
                     Entity var16 = (Entity)var15.next();
                     if (var16 instanceof ItemEntity) {
                        switch ((int)com.yiyiaddon.m.b.a<"s317ug1febdkbo","dKItVdJ3CjqW944Y8Sy75BSpdv511GMvnWZFdCeuxwc=",-2621660778494656022,-2508944837643881329,-4189041999546488101,6842194640152735638>()) {
                           case -1729210702:
                              ItemEntity var17 = (ItemEntity)var16;
                              double var18 = var17.distanceToSqr(var6, var8, var10);
                              if (var18 <= var4) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2jrjreyqx6t0u","zLC6E2LAZQbveiH4kSYPkFhA8npKgH9txbMNd63++FU=",2787085559990737342,-8698896132136454566,6225217757113558412,5889215455225895039>()) {
                                    case 25729701:
                                       if (var18 < var13) {
                                          label33:
                                          switch ((int)com.yiyiaddon.m.b.a<"sa5petltzvxlb","9u+7005VMOVXZ5qajhJ8bIioShmal0uArPBqt1wknGc=",-3946302944398495108,-4009725186883762271,4024077878678882440,-878014800763195946>()) {
                                             case 450702855:
                                                var13 = var18;
                                                var12 = var17;
                                                switch ((int)com.yiyiaddon.m.b.a<"s3q9e53swdwx4n","WNY4p7qV0/YLLvYDyG6rrZECHws+AHNl/6fnv8hKT7k=",-7789328574774413071,4864859145180094741,-3826022366942626397,1953327034526960090>()) {
                                                   case -1197131160:
                                                      break label33;
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

                     switch ((int)com.yiyiaddon.m.b.a<"srypya195dn2v","6BSSXdqUjiERXhrp965V1AunB2RxtRBijw5mia4cYK0=",1410086553464397755,5740658199447460810,8542613136896811266,-8396893430038773789>()) {
                        case 2050323368:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var12;
         default:
            throw null;
      }
   }

   private BlockPos a(Minecraft var1, ItemEntity var2) {
      BlockPos var3 = var2.blockPosition();
      if (!this.a(var1, var3)) {
         switch ((int)com.yiyiaddon.m.b.a<"s6d6e3klt7nrg","VDjQK4F6J8DRVQ0eaifKFssdDGYmulFnZCIt+Wh42Nw=",-4722185244279778167,493858691869609046,9134298895574705144,-7860394389476398911>()) {
            case 1132739497:
               return var3;
            default:
               throw null;
         }
      } else {
         int var4 = 1;
         switch ((int)com.yiyiaddon.m.b.a<"sqqvtedj7kldp","WQjbYg6clQquaphWlhjNi/GAFGGXnLWGPq85atgKh2E=",-7125435885745556661,-1912843372354228280,-3688419447806424325,6272343363175640390>()) {
            case 1139087417:
               while (var4 <= 4) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3hi78e93zspnb","50PaUwcx3OGf4a2PbolaohPedbowbv0WsksZrvg7ZWo=",-2263212611444055840,8218707407039793007,4299766730605047118,5779304761211802975>()) {
                     case 34906585:
                        int var5 = -var4;
                        switch ((int)com.yiyiaddon.m.b.a<"s2vmbvkvic5y45","Sgs89JOGOoIachWQauF58v2dVXArpqbP8dD0Jn03rn4=",98307307165952525,-5804647022434710266,-8909964203650911565,6876000720668907821>()) {
                           case 501955950:
                              while (var5 <= var4) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1qhpjo6g00v93","hVNV+SaKJxMgUJxLEKokH4Fl+opdAOzH+es99VCBZHE=",4054454432281610178,-5266385965631164672,572526569051190799,3888480471922066079>()) {
                                    case 832227083:
                                       int var6 = -var4;
                                       switch ((int)com.yiyiaddon.m.b.a<"sqsqvzuauexiv","m5Gd7chwj39a8P/LV2ZABh7Ud/fFMpbXcKqXUp+75S4=",-8118302943732671586,-6978617163394579854,3777304690047640995,-8186496939576889189>()) {
                                          case 2083681793:
                                             while (var6 <= var4) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s28m8w0bjmcsyq","kRdTHFISCMqfYGg3QXqGjkPdViIRbrtxbABPnFw78Fs=",3500017132839742038,-9734474936392610,6220803927440390267,7510444216777013790>()) {
                                                   case 1904458751:
                                                      label95: {
                                                         if (var5 == 0) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s2ubtovrnpllhf","dkSL6Lqx7HFax8u88KrO9f73GE7veTfE78DlPcHP1U4=",-8519359928456962871,-1412995757731527989,2831437520488257145,-8823781714043639546>()) {
                                                               case 2088441000:
                                                                  if (var6 == 0) {
                                                                     switch ((int)com.yiyiaddon.m.b.a<"sxlh3314tyzd6","gJVKzHVs+Ms9fhCg82QqB5wUEwcHmrHaHB6D/Xa5kjw=",8139351937592309091,-6158754379137067830,6195770959815424135,-1908123023801638052>()) {
                                                                        case 1529205693:
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s3pex9k28bp10e","qEDv8pXU5enHmEqF5O+S0N6qujl+pFX7n06pE7yqCA4=",-6509526028413582773,-1383875553469626255,-4841285681025811003,-6449470424132629000>()) {
                                                                              case -1877921142:
                                                                                 break label95;
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

                                                         BlockPos var7 = var3.offset(var5, 0, var6);
                                                         if (!this.a(var1, var7)) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s2xkznbtl4ec53","CFLurkO+lXuB3MjAAGs1mnhZS0vyBWDEsDSxUqUboRs=",1612009411289612787,-4030660707921172454,204170767078948810,5659675047562914283>()) {
                                                               case -1149258445:
                                                                  if (!this.a(var1, var7.below())) {
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s2bnw463z5ita5","wsSVPrpr03CG5/nyjSkTM6HyZy+9sxjiPahTm4bAx/A=",-3358468726796987532,-6919702579822785535,183427669465133525,-6720367837730842296>()) {
                                                                        case 1866464620:
                                                                           return var7;
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

                                                      var6++;
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1s77710wkgj0y","KggHwUiBba8gaBLb6/4CBjIRXjNFHsDR+j4kKbbxk/M=",-6784999700533845820,4175598135313407561,-4826882476979979901,6356959959496777966>()) {
                                                         case 1745668249:
                                                            continue;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             var5++;
                                             switch ((int)com.yiyiaddon.m.b.a<"s1m90ta7lm3vk6","ns8amYfvhS3ubuEeLr0mVYaoHa8nXFmahxQGmRb5WTY=",-3359604594172973001,4562842410844338202,-4221412186033743303,4065584782453721462>()) {
                                                case 1656317103:
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

                              var4++;
                              switch ((int)com.yiyiaddon.m.b.a<"s3qhvuvudh6ihf","JzC8VIl6CQDD/CACD70K/H44m/6F+M+c6wVrj5NN284=",-599216517917519244,7276461477545903922,-2604342792393813446,-1958513748426864576>()) {
                                 case 931268324:
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

               return null;
            default:
               throw null;
         }
      }
   }

   private boolean a(Minecraft var1, BlockPos var2) {
      if (!var1.level.getFluidState(var2).isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"shlzuix17ftb3","SN7hYVqwz+RTf1YFkuOqA3atgIO7yLrfw+PWlPeJI90=",6491597533724614024,3435681672215262978,-7922311713293237039,-155832541813537439>()) {
            case -1303702194:
               switch ((int)com.yiyiaddon.m.b.a<"sq7sklwinkxn3","G/xt67HRvp10c/QWihn98nnhkNBx/miVo8Xdf+YfMp8=",-572964836473193,3505246871830627113,-3653804854181294174,8015582817722679675>()) {
                  case 781626069:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s214t1190owao","BDcq8dhTKkOEgUN+tTyE9Au6ZG5mME7l9R8ZVQAUZJk=",1431675193864189074,-4144094572699315158,9019403903411920244,-8991059425158283983>()) {
            case 1115224518:
               return false;
            default:
               throw null;
         }
      }
   }
}
