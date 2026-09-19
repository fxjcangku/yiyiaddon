package com.yiyiaddon.e.g.i;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.Vec3;

public final class b {
   private static final int ex = 36;
   private final com.yiyiaddon.e.g.a d;
   private final Minecraft B = Minecraft.getInstance();
   private int ey = -1;
   private int ez = -1;
   private int eA = 0;

   public b(com.yiyiaddon.e.g.a var1) {
      this.d = var1;
   }

   public int d(Item var1) {
      if (this.B.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1cs6d2pqh6mgs","GZcdl3K4fXCR6PR1Vgx//gA4MfWVK4ZGLlEXW38z/oA=",-8907985178094740709,4877734149967965640,7037793473935705013,4777829112450678023>()) {
            case -2674384:
               return 0;
            default:
               throw null;
         }
      } else {
         int var2 = 0;
         int var3 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s10h73kv67byzr","lSXYejQmds54km+aNWAkdi3M2qcRPsV48g0uQLDkYnQ=",2530374517312596195,-6581032843196128982,-843805129096142619,246747199594345690>()) {
            case 2058921600:
               while (var3 < 36) {
                  switch ((int)com.yiyiaddon.m.b.a<"sttrqmai99vqw","CMicSVBTBbcdvbMdCS+0JwUM/mJGKXjJtT30jwmzpFw=",-1466022691820994040,-3885588327978386546,-1689119081889076176,3248002806569406597>()) {
                     case -128969671:
                        ItemStack var4 = this.B.player.getInventory().getItem(var3);
                        if (var4.getItem() == var1) {
                           label28:
                           switch ((int)com.yiyiaddon.m.b.a<"s26g08pfcqitec","w2QWvtzZCG/H5s157AfmxTPcluyg6MLJVoLEitzcteE=",2906265522829421148,-8750123756873849889,-7754815770892427665,-5954984935952410805>()) {
                              case 1610746853:
                                 var2 += var4.getCount();
                                 switch ((int)com.yiyiaddon.m.b.a<"s1rg8y9f7e10x8","zecp8GpOGs0E6xWReOfmsJRhCCitTCl4SLs4ENxcyGA=",-88087377046441668,3352737054596312657,-2817869039648898998,3272989282270200026>()) {
                                    case -2010260139:
                                       break label28;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var3++;
                        switch ((int)com.yiyiaddon.m.b.a<"s31us9pgun5tnz","7r+CF9VCdiPMmS5TBK3vtCglQecbEFASWjWazVVu+JY=",-2836823238609268993,-5593435669108362565,1621801811101795201,9109590725753245819>()) {
                           case -836931116:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return var2;
            default:
               throw null;
         }
      }
   }

   public boolean aU() {
      if (this.B.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2unhmppln75tg","ipxEK+ND0oQAKb03tMKkmMnQ7Eq8lN4uvIr/MIEvyUo=",7942146691805115757,1525628813438904040,-4128317694780998873,-7439226355293895790>()) {
            case 724566947:
               if (this.B.player.getInventory().getFreeSlot() == -1) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1qyxadtwjuj43","PiquXQIf24GEoJYHYkSUwN0E3WkeBKjFlIdGx0qcDw8=",-3817621345523416720,-5487192359021102827,5018625769099030388,-8831429598245844928>()) {
                     case 1507227653:
                        switch ((int)com.yiyiaddon.m.b.a<"s13elyx5fuhbpj","gplr7r7d82YdeslVglqNIb7weRiPmu3q/kHjOSWYV9Q=",-235617045292955641,-3823157986367427289,-205314957874842427,-621481359295755555>()) {
                           case -1480057875:
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

      switch ((int)com.yiyiaddon.m.b.a<"s285mqfnfwqn7u","AYeFi3y2mxgL+/WjXSq1wwZ9FpYUEXxyuxiYGXYkEYk=",5873985585211258657,8330092068724201354,5083073533772394858,-7810730765278483751>()) {
         case -760556451:
            return false;
         default:
            throw null;
      }
   }

   public int e(Item var1) {
      if (this.B.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3uhkbhm82gg3w","eXnRPowRXnQnsEeFsY4qznFkzcxV9EjDSvRJHBqhtPY=",488380352142704137,866581041697853376,8394467969953408888,2135425644363007146>()) {
            case -1347597864:
               return -1;
            default:
               throw null;
         }
      } else {
         int var2 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s33wjqc5awvjkb","zsdmx1US83dKHVLSKPPe8j5x7EZjPz63rLS27pBNywA=",1762129150992684430,-6486891515060313378,270779866323499065,7967430588421652566>()) {
            case -1857410199:
               while (var2 < 36) {
                  switch ((int)com.yiyiaddon.m.b.a<"s249yu5md05f89","W/EUdGHByiNyc82LAATMOyrqwOUVr+gRLWAhcWYivGU=",-5621728053316777824,-5027173864748644102,-6225658984773032862,-2818051137568645921>()) {
                     case -886239794:
                        if (this.B.player.getInventory().getItem(var2).getItem() == var1) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1ge6fr400t7x1","XR1y8Pn5c/fKBWx0aDbMnJuSplT72vFxtGoxDCa87pA=",-3540513190557708976,4340414928987119846,4940403356031834942,982405092551995301>()) {
                              case 1728760896:
                                 return var2;
                              default:
                                 throw null;
                           }
                        }

                        var2++;
                        switch ((int)com.yiyiaddon.m.b.a<"shn0qfofinkaq","LchNFpdssxGKv3v22jWyKznnoDYLOs///ITIxuSafXE=",-2712288273828300381,4966524589388104159,-5928923427905689025,964736900890915485>()) {
                           case 1576080801:
                              continue;
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

   public int a(Item var1, int var2) {
      if (this.B.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sq4cx8u0cqavi","MFzWP+mFY2cmfnxaly6leqZSkiB9D8g5dH5zj3cxFrQ=",1110873357107453578,-1899493314998551213,-4532411637026207984,4621433801642101770>()) {
            case -270795452:
               return -1;
            default:
               throw null;
         }
      } else {
         int var3 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s1607zaklz2xqm","OQIjbJga86DtHTMb5magC2JRrLCBE77wJkG2S/ngwao=",-817875179502205358,2253354547950045040,6760887817890311153,-1007722411282869278>()) {
            case -606341951:
               while (var3 < 36) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2u34e61ag3beb","lCRJv1Tt4jyyJKeJg2wXJNTtOcFkrrhIAPR/F0un41o=",-3500601872573126874,6990282152878346769,-1180145941212620847,2150176363261381794>()) {
                     case -536452959:
                        ItemStack var4 = this.B.player.getInventory().getItem(var3);
                        if (var4.getItem() == var1) {
                           switch ((int)com.yiyiaddon.m.b.a<"sbgoyjeoetdzh","Hh3P3f3oSA2Isj01X87Imb0ZWNE9KEU43Bgdc4ce724=",4035102459669093311,-7179115731260072249,-8129496020441481868,638899249785473927>()) {
                              case -576863974:
                                 if (var4.getCount() >= var2) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s11tfahyq4xcvc","xOXTQDu5PrERIE8RajuG2NZlg+X7j+mXv7CO3JOsyTc=",-5747762623393662580,-5339991596854606234,6592881009108186456,-2300612311420754232>()) {
                                       case -15882896:
                                          return var3;
                                       default:
                                          throw null;
                                    }
                                 }
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        var3++;
                        switch ((int)com.yiyiaddon.m.b.a<"s3zgyi7a1njmu","9ZbS0BEMzn9tEmQi7CM6nXRz/HVGvbZitVX+vDPv5yY=",-4645957081573482254,3615761053068569011,-1640516338454491869,-2222602764769442598>()) {
                           case 1990083138:
                              continue;
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

   public int a(AbstractContainerMenu var1, int var2) {
      int var3 = var1.slots.size() - 36;
      if (var2 < 9) {
         switch ((int)com.yiyiaddon.m.b.a<"s6c3vab4yfiz5","TiCSpuejxwXbQON0OKu5QGNOLzMFf9Gf8uYhondTC4E=",-43566758004082057,8698015807334120497,-1786915555630886233,-7472039841048419063>()) {
            case -1647937793:
               return var3 + 27 + var2;
            default:
               throw null;
         }
      } else {
         return var3 + (var2 - 9);
      }
   }

   public void cC() {
      this.ey = -1;
      this.ez = -1;
      this.eA = 0;
   }

   public boolean a(AbstractContainerMenu var1, int var2) {
      if (this.ey >= 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s27zzyelkhwwez","B3+avPPsaOazjGZrasyD4Te4mTDJMyJvJRTDClCQi04=",-8594250568476848222,8085317989126213828,-6118571555142509607,-8891027110966784429>()) {
            case 1002534426:
               if (this.ez >= 0) {
                  label98:
                  switch ((int)com.yiyiaddon.m.b.a<"s1nml97vcwbjj1","sbDEVdhHoWvKvDabqQBE2QL58kildCGV74PsB2K5Rrs=",9154541374827221831,-7400015205560452941,-7427944121201701665,-5374748584978572098>()) {
                     case 1443918775:
                        switch (this.eA) {
                           case 1:
                              this.B.gameMode.handleContainerInput(var2, this.a(var1, this.ey), 0, ContainerInput.PICKUP, this.B.player);
                              this.eA = 2;
                              return true;
                           case 2:
                              this.B.gameMode.handleContainerInput(var2, this.a(var1, this.ez), 0, ContainerInput.PICKUP, this.B.player);
                              this.eA = 3;
                              return true;
                           case 3:
                              this.B.gameMode.handleContainerInput(var2, this.a(var1, this.ey), 0, ContainerInput.PICKUP, this.B.player);
                              this.ey = -1;
                              this.ez = -1;
                              this.eA = 0;
                              return true;
                           default:
                              break label98;
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

      int var3 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s1gr7tv2mck091","p6P2SnDqwnYSoxNuWdw8Q9xUaWdbsPISCH8PCtGpfLo=",-6221667443384659480,2742717466935034463,5225499557557430522,-3505761621765594530>()) {
         case -1549554205:
            while (var3 < 36) {
               switch ((int)com.yiyiaddon.m.b.a<"s2txx674ijhycu","B5KxZf7gByDaOREBDimdtcdjfP1yPSFx/uAceqAIRgA=",-5951452343322559296,-5162608626490663208,-7573713588629811232,-3461156235482656820>()) {
                  case -1820086007:
                     ItemStack var4 = this.B.player.getInventory().getItem(var3);
                     if (var4.is(Items.BOOK)) {
                        label59:
                        switch ((int)com.yiyiaddon.m.b.a<"s2rpyj7ltiy0u2","luBkQwNgQne7KxV2FoK+vf1fFfvnyRCJL7nFcqqCc5o=",-7820560728825236710,-5173535342895050499,-517632458031661018,4966121689961724111>()) {
                           case 1725386183:
                              if (var4.getCount() >= var4.getMaxStackSize()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1zgwxbimbfuhr","ZPDorlPmJsNqfDdkUz833iGgP3wuUHie+TBmRR1OIsE=",1851042507568071828,7721787516998668145,1228240547821907914,-737683978341319757>()) {
                                    case -1929451342:
                                       switch ((int)com.yiyiaddon.m.b.a<"s1j6z8svmn2awo","y3qs50fYwhs7dOTuEOfcw/kTv91ZOu4G0EdKkJ/rKGU=",-152027634038969255,-6801747133720738486,9033112369004710254,1813751966901866207>()) {
                                          case 689437575:
                                             break label59;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 int var5 = 0;
                                 switch ((int)com.yiyiaddon.m.b.a<"s321yrwko19me4","Xfvt/NzuzbiP3x1WJKxAyEoVkQoKDiYQ5M6GsqTnIoo=",3389807250842208747,-4572112712583596904,-2246665174324253581,8148512723280393076>()) {
                                    case 1914169617:
                                       while (var5 < 36) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3p5weztytotye","VjUfuvnef/t2+Xhh6HbbPDgLV03Wvy7PYMjh+UUehHk=",5354645076997294966,-6411572496703366405,-7781781188426171929,-4960231014305235981>()) {
                                             case -423302023:
                                                if (var5 == var3) {
                                                   label76:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2siq4puwx26qd","lq3+x2Yv2uje2ANPysl/OD9SZAiR2U/tPe6pxpTyCYM=",-1798267817289330490,3317270307683833752,3275233039981908558,-6780252674613731643>()) {
                                                      case -1510668486:
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1o0w31att1xls","z/Or+Bqar4xdPsM+IBXaffeLcogS5k/ZPb+lhgoi8Tw=",3380553599389296679,2704993178032821675,-3074287405086810050,2456663592589390946>()) {
                                                            case 1304792817:
                                                               break label76;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                } else {
                                                   ItemStack var6 = this.B.player.getInventory().getItem(var5);
                                                   if (var6.getCount() >= var6.getMaxStackSize()) {
                                                      label72:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s32xh692alfksz","dRcpoTyl4AHJJ6aqR/fMJO0Jjp0yRQRvAzF6Xf2U2a4=",-7394870849974117221,4050263394356336096,-3653158473880365392,-7923450077721839019>()) {
                                                         case 563404964:
                                                            switch ((int)com.yiyiaddon.m.b.a<"sssee12x6nagc","RaAsZO9hyJoqlNOYZhDFftWHAW+a0Wge/iyskzPlUd4=",5131265162896919057,-961428662534952852,-5293468026214589869,8732424408489085237>()) {
                                                               case -1619260219:
                                                                  break label72;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else {
                                                      if (ItemStack.isSameItemSameComponents(var6, var4)) {
                                                         this.ey = var5;
                                                         this.ez = var3;
                                                         this.eA = 1;
                                                         return true;
                                                      }

                                                      label66:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1tc3chmkk9lqg","IrAcBg8chxwUT4qmXkrn2ACHyaE4+GEhugfZX4NbdXg=",-4853540881368019017,-289095563202256749,-8686475315770702936,5351022052032272398>()) {
                                                         case -643406689:
                                                            switch ((int)com.yiyiaddon.m.b.a<"s1q28fxqhtpul1","k5PLXtkFuXGDcLmJLJYvoEZKyCYgdr/9tbHLEhAAe78=",-363135155081371932,-5878655352480896489,-1036794301495334141,2355256951963923284>()) {
                                                               case -1499991089:
                                                                  break label66;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   }
                                                }

                                                var5++;
                                                switch ((int)com.yiyiaddon.m.b.a<"s3otkyv1p570gd","IxTFaBUz1LBKds4ECmFMBuGI0aNI4o7cGxDzmAqK8Ow=",4805610218172007348,8995534744510341388,-7191963543331389383,5277835094179525652>()) {
                                                   case 2026342624:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }
                                       break label59;
                                    default:
                                       throw null;
                                 }
                              }
                           default:
                              throw null;
                        }
                     }

                     var3++;
                     switch ((int)com.yiyiaddon.m.b.a<"s3ieoer6j2m9zv","YwhfDisvbI4AcDYXgHguLOqEXlyHZPXVHqkQasKEbv8=",7379079621364512796,4376844336022025217,6672211240935594833,4905097519005871973>()) {
                        case 254669542:
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

   public void p(int var1) {
      if (this.B.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2wh4yorc32qdv","7w3aTpzhEBtpjVohvikv4//3jYhKUNOlRdmfSpDYFKU=",-9135378282824704748,-205600335635109532,-2789794987461792896,-4806426014673501409>()) {
            case 670586743:
               if (this.B.gameMode != null) {
                  if (var1 < 9) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3qy75b4ychzu7","P0BeRq7++i6qoJG0xgP96Vw+eq2x2vy/P3lJhXQ1LEc=",7849326734005605876,-6153088865035924714,-1129648333653049290,4938106072531843599>()) {
                        case -297704750:
                           this.B.player.getInventory().setSelectedSlot(var1);
                           return;
                        default:
                           throw null;
                     }
                  }

                  int var2 = this.B.player.getInventory().getSelectedSlot();
                  this.B.gameMode.handleContainerInput(this.B.player.inventoryMenu.containerId, var1, var2, ContainerInput.SWAP, this.B.player);
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2rbakpo3j3h8z","vL8R6MfyFoZCmBbaefqsxFXPi1ZwTb8oySO7cfEDaYE=",-6781851690226511174,6017365784482429858,-714372613205598110,2200013346747770081>()) {
                     case -443117310:
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

   public void cD() {
      if (this.B.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"se4ctm5safr0c","43JsJddbyX3ojVkYbBvvp3H0O/Ihru4BCz+J0FnrT0Q=",2531823875717880585,-5018348965502924014,2890895247212596885,5828537781906243686>()) {
            case 819370567:
               if (this.B.player.containerMenu != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2fb9h6aqnjbdr","IuEBDwH3RwJ50qlTaRCihaZXaIwidysZHNPqu9JtaB0=",-5257591666117370496,-573800554253715498,6029484616059545917,7245729419217709859>()) {
                     case -145849980:
                        if (this.B.player.containerMenu != this.B.player.inventoryMenu) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3585mgzo4c8o4","wSnCdFAY34mtj3SFoovJlf0IocdbSoZXLOupJaNnOQE=",1215158426449192100,-6260858391214861433,3060795022890465806,-5211092115484073314>()) {
                              case 529782055:
                                 this.B.player.closeContainer();
                                 switch ((int)com.yiyiaddon.m.b.a<"s1f3nv7ovypzad","FW68/P1VCkdUEWGTK+OliHpOBF+73PqqhQtgF+h5Ibo=",-5180954260054881275,2593167905278504694,-6320916990953234807,5776525211489712118>()) {
                                    case 2121379311:
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
   }

   public boolean m(BlockPos var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"snps50oltl7j","VpyJNMdH7K1QyZIfIC9A2PxB6/NYMF18FFBDlqnNajo=",-7365747511774091403,6005145988559665149,5343882010456553770,-2406700506291298652>()) {
            case 1005011016:
               if (this.B.player != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"sgocssvenutv0","IoUl7qvUVWUJ3G62W2SpOJeqe299E59UHbfZQglFefA=",-6973386370039786484,-4367757715275922892,-7269950526292006795,1938478816433820460>()) {
                     case -819751099:
                        if (this.B.level != null) {
                           double var2 = this.d.a().dA;
                           if (this.B.player.getEyePosition().distanceTo(Vec3.atCenterOf(var1)) <= var2) {
                              switch ((int)com.yiyiaddon.m.b.a<"sztvc8kultib6","vbek0ScJyCk3kk67cZtXmZUyeAG8JsG65yODsoJuLSY=",8689448257305462743,3293090674361029323,2982498427420324921,2403089121381042713>()) {
                                 case 227805079:
                                    switch ((int)com.yiyiaddon.m.b.a<"sg1r25pnazy4l","9j8KLyWM/UKF72SFiWMqWVuHLn/4PwgHiqRV8QjOCIQ=",-3775382781677384015,-3277545897913691262,-5871570460224888430,-1167992048803298719>()) {
                                       case -754237720:
                                          return true;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              switch ((int)com.yiyiaddon.m.b.a<"s37k4yirif5541","tO1BiC0zl8h27HsiuovBuTImKpNkEYb+GTbNsGMtaKg=",-6791994906502259882,1344567814236220948,-532722618318582862,7598057022334462873>()) {
                                 case 523008910:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1h7mgbc6wc5k9","+mBVUNr1ACQpernQpTjwE1o7CVt1k9PbHD5dEg0KQNc=",3404869923696611089,8791650673687554678,-1019349447001048223,-7128835265758786199>()) {
                           case -2000545072:
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
   }

   public void l(BlockPos var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2bl7g30rnzmyb","JHT6g1uSANXZw5uvOwNm1S7IT4NIDSUl8eM2M92H59E=",-8346593971069667153,6596138218355181675,-5925928968324667478,-7457192801673771779>()) {
            case -1606412428:
               return;
            default:
               throw null;
         }
      } else if (!this.m(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s3rn716c7far7z","EMtwNXOldgxEoDr8pb/zBX+Mg/tFCgPM+Fd0Kbc6gzo=",-6410307778541191892,5335654363775887515,-7319188886329654711,7185215785061164425>()) {
            case 1559414045:
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.i.d.a.a(InteractionHand.MAIN_HAND, var1, Direction.UP);
      }
   }

   public boolean aV() {
      if (this.B.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s7za3p5oqgeh5","fjG6moPf71IdSPLUQHW1r2M8CC+IpjXzcyQCDpb4rBM=",6556786981422295767,-5091809572759177560,3571252886724495779,2458132126306507055>()) {
            case 956771549:
               if (this.B.player.containerMenu instanceof EnchantmentMenu) {
                  switch ((int)com.yiyiaddon.m.b.a<"sbllvf5me5ggz","0EQHLPL2dFvPbbW8GHPMOnDen6pFtoP0oXlg9Wz7Avs=",-2983310821618540363,8498378082304683829,8726651322058301082,-7097959085544524301>()) {
                     case -460475412:
                        switch ((int)com.yiyiaddon.m.b.a<"s38p0felkcfd1k","OnEIQFjhD1ijiAKnUHcqUXVR/abdaUC4Pjf/Zqq204E=",4872076777185665658,6116680313387134398,8434916782635778253,-3853651321409477858>()) {
                           case -859052866:
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

      switch ((int)com.yiyiaddon.m.b.a<"s1wd529epgj2t6","5QrbZIJ+iX84/0qnbKsp7Nd67MJ0fAbPV9bhKH7crFk=",8454857462492909702,128755206791971095,-1809830218887415467,-6358277725199388917>()) {
         case 1869181188:
            return false;
         default:
            throw null;
      }
   }

   public boolean aW() {
      if (this.B.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3q6fxrbg7qzhj","+TIsRilx5TLalwFoEHERrjDTrLU+E9Xvf3fOjajxJWc=",-3461843849450189566,602402602435505607,5065931535961069998,3176302147175017139>()) {
            case 13729420:
               if (this.B.player.containerMenu instanceof GrindstoneMenu) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1rlmn644a7nkv","SxdjKVzCeNGHyVaxPpHdq/vM+u1qAZKy7o9WJ+FWDgE=",-3334681202837641739,-6704643466699208732,8628543381634526467,-4059076887472988064>()) {
                     case -630082005:
                        switch ((int)com.yiyiaddon.m.b.a<"s3smmsya5sjx7p","CWZPYXfutIF7Ki/2981mUGcol3qyIWWI8/QaX+ZCXWw=",4328328580157542445,8124329844432121431,7803134305932666579,-2725907701599030087>()) {
                           case -939654717:
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

      switch ((int)com.yiyiaddon.m.b.a<"s7pbu7uchbipl","mRNDYx+HPZleBkCg9QOkl8zSyRGTo/DStq5yoauOgNc=",5504659694418373803,3595830905229885660,-5589555135042608204,2345880702020947300>()) {
         case -1900918644:
            return false;
         default:
            throw null;
      }
   }

   public boolean aX() {
      if (this.B.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2m4sewdgb0wqm","ToyDyikZU5H6hojmX5CuHxsUkB+h/doh4AT88EvKVzg=",-1733355616084916967,-7905004268544537325,-8874103437289076423,7119184106963136125>()) {
            case 1542888476:
               if (this.B.player.containerMenu instanceof ChestMenu) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1plpfrgo853n","TKDDOV9RHS7PzhgKFp173MaZ28BveHfvNEjZljW4CZU=",-217700342138960086,4966065319436870364,7805611673425467927,-3552732549271337330>()) {
                     case 433449848:
                        switch ((int)com.yiyiaddon.m.b.a<"s1i4fwa6puvhsg","TxFAd0XJOr5ZQA9Wgs/U7lW7mQm2qESnY/nM44FZV4I=",3669591820391626053,-3229552616788232565,-3672829724602013832,6696421317531312657>()) {
                           case -1327318491:
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

      switch ((int)com.yiyiaddon.m.b.a<"s1m9zjn61d8bri","MN6iqt0ZBHlblh/azoaHsXpdiQ1Bj74+ADSculiKWXk=",5355489861656604854,1718886424783109400,-84654761244441632,642129487092683883>()) {
         case -1324072180:
            return false;
         default:
            throw null;
      }
   }

   public boolean aY() {
      if (this.B.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1gx0unk477os7","yi7LoFTGJ6SKvYgVpCk7SMydZm6MPMInYOm9TcBkK1g=",-1243969811794198261,7004955580138133201,-8118141327655406996,-1344614340393531312>()) {
            case -608180788:
               if (this.B.player.containerMenu instanceof AnvilMenu) {
                  switch ((int)com.yiyiaddon.m.b.a<"s37s1p6ofv4hqh","HlXfRYzW1MxJunwrWIFWvWqHy2IOcg9yoeSisnxbudw=",7409831371417220568,-8201327252166400909,-8064599273340175771,-2243065512450832183>()) {
                     case 1757339260:
                        switch ((int)com.yiyiaddon.m.b.a<"s20lx913fw6nd3","5OFuHgy2EJsRamFNc6fNx6dbv/EktQdw32Ik+5a9+J0=",8136060544362486602,-3818334725564406465,7576635362269009680,5843846665321332884>()) {
                           case 923196814:
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

      switch ((int)com.yiyiaddon.m.b.a<"s2rc802kklvdkw","G1Rn8n/uaFG++2eVRWqsIP9MSIZoDuI1cokfdPKFibU=",-1714159837330886442,-6448831384452452479,-7471964854559864563,-5691840010105816134>()) {
         case 1303300951:
            return false;
         default:
            throw null;
      }
   }
}
