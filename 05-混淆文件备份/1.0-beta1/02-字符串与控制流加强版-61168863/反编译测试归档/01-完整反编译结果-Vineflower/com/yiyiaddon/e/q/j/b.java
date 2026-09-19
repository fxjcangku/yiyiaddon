package com.yiyiaddon.e.q.j;

import com.yiyiaddon.e.q.f.e;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundSelectTradePacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;

public final class b {
   private b() {
   }

   public static int a(MerchantOffers var0, List<e> var1, int var2, Set<Integer> var3, Map<Item, Integer> var4) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1xsewi7f7t3y6","M44WEgtz6yhG0uoPooq78lHleGTixKlzllhTEoD8rn8=",2303262891179165132,3432859455519508279,-1141050432910631877,712354644460405809>()) {
            case -972564200:
               if (!var0.isEmpty()) {
                  int var5 = -1;
                  int var6 = Integer.MAX_VALUE;
                  int var7 = Integer.MAX_VALUE;
                  int var8 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"sje1hb8ziesmk","/ePum2XbAW0941zG5HLhnZOU5fyD0RtfNwFO2IGIT1M=",4323962841305893427,-6920547635438716288,-6470135377651500898,-984697509212483434>()) {
                     case -127623031:
                        while (var8 < var0.size()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1yxwosukf2oc8","Rxhzw6a68DBNjkOKIPYfW5EdPy3dXumnap2drLmNzLI=",-4583735110174433439,-928324381486184718,6621131410224763111,-8206987254970006055>()) {
                              case 1127727117:
                                 if (var3.contains(var8)) {
                                    label67:
                                    switch ((int)com.yiyiaddon.m.b.a<"s2rrecgywzat8a","fG6kWyimohyJd2YxWTA3in/Uvu9HKtJ2Kvy9kE240Ck=",-1042036714487881182,-631207078094412838,-1128796435882210555,175909528120171279>()) {
                                       case 2141403549:
                                          switch ((int)com.yiyiaddon.m.b.a<"s2auye8b13toli","8uhtRehsQKZLjbQfIS9I/Lq+xKdkHJ91XLFwfzoIKLI=",5947926208473296621,-8295021327330974622,-7353571395233348213,3677592429178270086>()) {
                                             case -18599921:
                                                break label67;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    MerchantOffer var9 = var0.get(var8);
                                    if (var9.isOutOfStock()) {
                                       label63:
                                       switch ((int)com.yiyiaddon.m.b.a<"s2dr31eond2wv9","r3JQL7YUTSCAEsLV9ZqlR0HQltgzzsJnW9A92MjdJTk=",-7917438644397947674,-8623097312494706848,824374076235490317,-6897944254548947678>()) {
                                          case 1474057431:
                                             switch ((int)com.yiyiaddon.m.b.a<"s24uednmvaq1f4","11wuC/DaEEaQgrdvgLt/5HF88pEN63Xak/tRWL9IhnI=",1360433704768255349,4545895385016528401,-2150432787530641797,-8617870678181608473>()) {
                                                case -1538324076:
                                                   break label63;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else if (!c.a(var9, var1, var2)) {
                                       label59:
                                       switch ((int)com.yiyiaddon.m.b.a<"so51gdqtzwgdk","Opo0OmyZVDkRBGt6doQ3OaSR4MjcsnPRoDPSgfiT87I=",1087119170997396013,-2400235351152414959,-5489304451799900254,6627516452104808012>()) {
                                          case 19202680:
                                             switch ((int)com.yiyiaddon.m.b.a<"s7ag52do14e08","ajRRm34pUbcHS6PZi7b5E49Jn0+vIFplwG+lvB/StOk=",2862345112436543660,-6943706798959209595,-4973655270303882536,-6822811074157448628>()) {
                                                case -260853871:
                                                   break label59;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       label90: {
                                          int var10 = var4.getOrDefault(var9.getResult().getItem(), 0);
                                          int var11 = c.a(var9);
                                          if (var10 >= var6) {
                                             label53:
                                             switch ((int)com.yiyiaddon.m.b.a<"s3ms8lz0ipxehb","UD7wa/2KmMbF0lCbYbLtdRfH8aUbiGYWQ1ObO2ZczGo=",6177312685680990779,-4887448967320381651,-2630464193627696390,746935175645013252>()) {
                                                case -1889791095:
                                                   if (var10 != var6) {
                                                      break label90;
                                                   }

                                                   switch ((int)com.yiyiaddon.m.b.a<"s19e7z5cb9vs2z","d7di04SpAMQOHWOpwthd7LyzOCBiykeq+ggHJUNe5F8=",-7901621719558686638,1780040210751910772,-5913210619002870839,4261213830584322778>()) {
                                                      case -1100808442:
                                                         if (var11 >= var7) {
                                                            break label90;
                                                         }

                                                         switch ((int)com.yiyiaddon.m.b.a<"s2ntxpgvqj5zah","kBwWixvMMQGJvi8Woz/L2Rxx6QIdsjc8ZPK0WOFZATg=",426473085046048702,556229222722938513,-8364653295833322556,-2221774271908580542>()) {
                                                            case 474112765:
                                                               break label53;
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

                                          var6 = var10;
                                          var7 = var11;
                                          var5 = var8;
                                          switch ((int)com.yiyiaddon.m.b.a<"s3tzskoq7gam15","9D8tsvBEFcTXHAh1tZ7toC9FwlEY5uyUWSnxwUm/lbQ=",-2479488696034744309,-2249789597936241300,2574952886802866146,1071907494809948321>()) {
                                             case 1790608988:
                                                break;
                                             default:
                                                throw null;
                                          }
                                       }
                                    }
                                 }

                                 var8++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s26tpjc0nlo7xk","USAfA4GU7zBTAJs/9UCPbHznY4lBuRkjQ6GT0DAJvEs=",-6360842264209673675,1331416177706986572,-6613874472516639998,5237819893115225110>()) {
                                    case 473871601:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        return var5;
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s34mt5yemwa9yt","9DCNfPK01OTZuHHurtjBjWu6dFeHko1L8clOsZ4d4cI=",-8881866631114766322,9205182724532535767,2551930352398732828,7067241321351674276>()) {
                     case -368741045:
                        return -1;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return -1;
      }
   }

   public static void K(int var0) {
      Minecraft var1 = Minecraft.getInstance();
      LocalPlayer var2 = var1.player;
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1dx15zo2ukbh5","pj6dO3J9mu7fSnsZdH+sLs2qTW6FDiM3nCrlsNwLdDY=",7221816763957378939,-8025757944674227126,-1775975499583135500,-1653779359166814288>()) {
            case 295129510:
               if (var2.connection != null) {
                  var2.connection.send(new ServerboundSelectTradePacket(var0));
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s18hmw1ava50c3","9ElN4foRk9tGFt2fLkOrBMLiYKVCly91DMGRB1JpG3Y=",4985640643413415323,8540339115861919508,-6839485439518530027,1915156251408605513>()) {
                     case 1907623451:
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

   public static void L(int var0) {
      Minecraft var1 = Minecraft.getInstance();
      LocalPlayer var2 = var1.player;
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1pgntevac5yqs","JHvzzoDcWVWeZX2+L8K0DMKb2UW6zePxSBzYLpOXUjs=",-811782958627397996,-1078031457678602840,-6731210756473971079,6752337411170844613>()) {
            case 1950940949:
               if (var1.gameMode != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s12yc897zf93cq","5YqEvfoT68KfJl2olHpnE7d2RCEbCWhH8VyuRQSpg7A=",-7348325967583900170,2051962485263435834,-1730048582756950820,6507932551717148493>()) {
                     case -1719729503:
                        if (var2.connection != null) {
                           AbstractContainerMenu var4 = var2.containerMenu;
                           if (var4 instanceof MerchantMenu) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3sj8xpnrvlskj","3gj+Ju6lx2KZDSrZOpZNB3QrQf1i80/ZaTKZiAOSuBo=",7158159724271301267,4234987416364813809,6252807717986936009,-6436462903358364991>()) {
                                 case -376464467:
                                    MerchantMenu var3 = (MerchantMenu)var4;
                                    switch ((int)com.yiyiaddon.m.b.a<"s1ky7c4nvl0wai","EaHVgoRKlSdwqPdfSZ42tsWc9AD1kPxkLuwwLjDG0yA=",-3239027331397656099,-8660897830860920913,5161964885443139374,6941287718171057789>()) {
                                       case -710216082:
                                          var3.setSelectionHint(var0);
                                          var3.tryMoveItems(var0);
                                          K(var0);
                                          var1.gameMode.handleContainerInput(var3.containerId, 2, 0, ContainerInput.QUICK_MOVE, var2);
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

                        switch ((int)com.yiyiaddon.m.b.a<"s3smh80misycoc","yff0o9SOtBpU8O2kepNVX3x5PKGwcg3uiPsd/xVCnNA=",-420876000277278475,6732852147041857090,4160532976629150302,4729667209741514913>()) {
                           case 405115777:
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

   public static int a(Item var0) {
      LocalPlayer var1 = Minecraft.getInstance().player;
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"spqgf4r193pwp","dSTbDKlksdg+2E8Tbsg9/0YggV/WnR8EUkOM5l5Zxt8=",-3841127553962520688,-3301121890546031107,-4057776231356011420,448647194848539214>()) {
            case -1395197116:
               return 0;
            default:
               throw null;
         }
      } else {
         int var2 = 0;
         Inventory var3 = var1.getInventory();
         int var4 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s3gflzgcb8uqik","kJj3Zh+a3y59FZcl4WxcEySzy7r4EuN+z1datZN50yg=",6605373162453135353,1314190606213662301,-8417789878927765964,2203898909292239166>()) {
            case 768664809:
               while (var4 < var3.getContainerSize()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2nu54hxtmvo3w","k9dG2i3cOCAsMELU4uV8NxgSwQDptFgQkhOaxcc0EXg=",-685657093837090537,-4788157986274948702,-2775466674189307044,7775193172633610501>()) {
                     case 655422593:
                        ItemStack var5 = var3.getItem(var4);
                        if (!var5.isEmpty()) {
                           switch ((int)com.yiyiaddon.m.b.a<"sb7td867rl3mu","l0Ee/R0eg/kvxAbb8gUdveAP/u1/5/1tifaUIV3NcTY=",1689362843138386521,-5606614120562157401,-3476331629057865323,-6681735709317812689>()) {
                              case -1426126386:
                                 if (var5.getItem() == var0) {
                                    label48:
                                    switch ((int)com.yiyiaddon.m.b.a<"s375g0i4icm42o","qxtU+L5c0ZM7WN4AiQRWUJMWgrrLex+QU3dNhAYV7W4=",2951854921894501893,-2946333752334176117,7788748344218269611,6958930035351886011>()) {
                                       case -1419770025:
                                          var2 += var5.getCount();
                                          switch ((int)com.yiyiaddon.m.b.a<"s324txw0stlbf6","lZmRYHdFBKJx3ZHjiFNcCJ/B3KbBju7i2LSTvyP6ZDY=",8306149738867125979,-791649038770569555,-2986915150238102738,-6451144890397277189>()) {
                                             case 1188774627:
                                                break label48;
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

                        var4++;
                        switch ((int)com.yiyiaddon.m.b.a<"s2au5l6aowavjj","LnB9y505zoBjVrlCQwoMM4Bi4UD3XzXux3J+fMI9pzE=",-3456461421389727523,-4776867192023702425,-7094636915737633563,-701415979464633828>()) {
                           case -1516121977:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               ItemStack var6 = var1.getOffhandItem();
               if (!var6.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1y5tzjwgpgbuo","yJvFvlD+nTdIqJuR2trVKSrEc77EcH6Sg5+3d3o1rFU=",8484372627033872064,-2525580215161988302,-8250935826102419300,-8501584493235935097>()) {
                     case -1370383547:
                        if (var6.getItem() == var0) {
                           switch ((int)com.yiyiaddon.m.b.a<"sp7fmls6ynw1z","UNokCxME45eGJy8FKfujNPqreLnEvWF4j+FUDvg78MI=",341779448022349491,5202186805645923321,-7755087160472919331,-6261700222423900245>()) {
                              case -1815270614:
                                 var2 += var6.getCount();
                                 switch ((int)com.yiyiaddon.m.b.a<"s3albuda75qwsi","BtFQu8mr6jUGs/LjxE6YI4iPm9xo7UFU+rSKS/yJ4nw=",6585940980454321574,1911105070009924202,3204280351932596658,-6685747308560230540>()) {
                                    case 1840034736:
                                       return var2;
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

               return var2;
            default:
               throw null;
         }
      }
   }

   public static int dg() {
      return a(Items.EMERALD);
   }

   public static boolean eQ() {
      LocalPlayer var0 = Minecraft.getInstance().player;
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s5goznvav6ywe","Vg5gmbIEHQjwarGIM5sDRrN5uLADviAjUmZuxdz7Ee8=",760541061767591237,-4893895583592096615,-7907979790755255110,-3263162190242154364>()) {
            case -1113370855:
               return false;
            default:
               throw null;
         }
      } else {
         int var1 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s98zaksutfmrr","RrX8+uNd9Ltp321FjZKbL0P2oqAZ6PApk2CAVJ17Zf4=",5811673393472455053,2512525272675338881,-5268019617276040551,-4351378135326688218>()) {
            case 958897437:
               while (var1 < 36) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2urua7egpt5q","wSBp2Bb2CqrArcGpLcp0/JycwHvPsVonM2nozrTU1Qo=",5693697217579403161,5810158320051407031,5368883817140501826,6476958616571230371>()) {
                     case 650630794:
                        if (var0.getInventory().getItem(var1).isEmpty()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1p4d6roupckez","ixhSCcMovZtB8lXUxJBqlDYzuy/YJbayYq4zl3IKWLc=",-5039173529229561350,-1206978782117682988,-9082320892595671658,8661127186106053783>()) {
                              case 2079065181:
                                 return true;
                              default:
                                 throw null;
                           }
                        }

                        var1++;
                        switch ((int)com.yiyiaddon.m.b.a<"s10wlxcgzcw9k2","upx2esXRbyC7xG5kI4fgVZNOPKX7QcJRSL9+XXMsNds=",-3237521978771013289,6614471908993967008,-7445953202313489034,9066390255720164661>()) {
                           case 172588994:
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
   }

   public static int a(MerchantOffers var0, int var1) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1pooev0y0281a","iYesWmV9oknCUcBVaxca/tqn50dBMCUDubnsT8DKsIw=",7770209933825651663,7711114850052249440,275493697403292644,1235229295481837452>()) {
            case -1560458475:
               if (var1 >= 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2qiv0oej00kze","k3MPLXoRyDKr9rJNEaVc66RfFCx+fsCVMYHe3d3UmX0=",8477422105741993654,-3079591805727437116,2021946143782581233,1537156236552867763>()) {
                     case 405510598:
                        if (var1 < var0.size()) {
                           ItemStack var2 = var0.get(var1).getResult();
                           if (var2.isEmpty()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2wx9dnmbr0i4m","lI5qPROpWFOJxLHy3xP6O4iWum3q9m5EGKen3klKI1k=",-6605242280122457944,-5223603294937089299,4487501700925151905,-5706393413094694615>()) {
                                 case -647839534:
                                    switch ((int)com.yiyiaddon.m.b.a<"s1pwagdudnadvm","vCIz8XgR9653UMfmVsi1+g5VaDKB0YtxXb2sEkhz5gE=",-4727928147328616116,-3522795397221903605,6071335688904400243,7315464242322833732>()) {
                                       case 1348253921:
                                          return 1;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              int var10000 = var2.getCount();
                              switch ((int)com.yiyiaddon.m.b.a<"s2f8ecr4fwwgb","YUJPfDfWhyrVmSpZCZ9z5VkSloYaxNlF81xmawdhgAM=",6126193648430356375,-1353798748211762749,5513711762745347563,524281462724322299>()) {
                                 case 93507003:
                                    return var10000;
                                 default:
                                    throw null;
                              }
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s26x0ge3ra9q0o","Tk3TUF1+wqqpSXnnQAnEHAVL65lvimTKAJfi4CYjs/s=",2350066810139150238,1454916258525987727,-3219116591866144427,-4686360551992381884>()) {
                           case -1749936247:
                              return 1;
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

      return 1;
   }
}
