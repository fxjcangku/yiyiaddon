package com.yiyiaddon.e.i.e;

import com.yiyiaddon.e.i.d.i;
import com.yiyiaddon.e.i.f.h;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public final class a implements h {
   private static final int fF = 36;

   @Override
   public boolean a(i var1) {
      label45: {
         if (this.f(Items.EMERALD) >= var1.bi()) {
            label32:
            switch ((int)com.yiyiaddon.m.b.a<"s2s2dli67gkb8b","T1qEqRgp/1pnIP3VAuKX06cDPyxKRSiCExrp4vpqfS4=",-524707537441617809,-8726329128788650679,-3131495879605200723,7103316796157875874>()) {
               case -1538900101:
                  if (var1.by() == null) {
                     break label45;
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s3bm9l455qy4ls","qw/bXzMZpdT64hCj4Q4z1zN19aIWLVT1oe1X5cnjZcI=",127378656441648449,-3968669869658317670,9175674428420904367,-3105048683502039411>()) {
                     case -48950828:
                        if (!(String)com.yiyiaddon.m.b.a<"s2mr49t9js1v1u","GjW+9mX7aKwRD0IOLByKms928HZvdkyNeF524fFWhZePm02790xP1/NNr/c3dUJjAFJcksO8EyE=",-1602644363221290954,-9083908629562392110,7269717288002526177,-5816339298296840139>()
                           .equals(var1.by())) {
                           break label45;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1l7uscv8nu5iq","bKTQyqtfHOVpnQtWTrj50pJRvn5ubZqaalvnhCx4boo=",4432537409747226807,-533919537030077372,6568351694683413072,-5621510998543709183>()) {
                           case -683766087:
                              if (this.f(Items.BOOK) >= var1.bj()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2lfbcfv1c67lh","Ufy1Nl+Iyp3e3QeSOsaWO3AXee8zU7ieltEF+TqE0rg=",5831655206542557108,-3885022436659079963,-6558556890944553503,8085057590155836288>()) {
                                    case -259853704:
                                       break label45;
                                    default:
                                       throw null;
                                 }
                              }
                              break label32;
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

         switch ((int)com.yiyiaddon.m.b.a<"shesfv8qp3t41","aP9ckhhKNTcGIQ8SgQZjrw2EHhdSRyPzds8XXfqi2j8=",1755922302040431930,6274270911283648461,-5206204006744619121,-9046553999182686489>()) {
            case -1703492074:
               return false;
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"smwibeluddp78","DNk5cYgW1uPOlLncFdgcV8cf6hgOBAv3WEOiNLtl+bE=",-365813733318505561,-1648129706270078515,467291521050398875,657712564470097587>()) {
         case 658842032:
            return true;
         default:
            throw null;
      }
   }

   @Override
   public boolean bz() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3k1czs26xvx1g","C0nTpmu8Z51zS4YCW8xpA2cgXkvjz+sRdZnJNJ+iyYM=",-1656549918038123389,-2783604086789709952,3765855939184236888,-3938707110477044269>()) {
            case 839438537:
               return false;
            default:
               throw null;
         }
      } else {
         int var2 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"sekqyttaoks0s","zDn0hH1HeWwvbXGtV4++8MrSbj6d4Mwooy+E7anhFb0=",-925816668468218658,3863650763963008529,-1211028445186857556,-1671157030815365591>()) {
            case -201092683:
               while (var2 < 36) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3vukr1z8lyyhb","yYmNvkpTNsEgLRZN3+f0XI2jLLuz7njBt3R+fR9p5bU=",-8300283920264157103,2089800309069258210,8002487677594590131,-693583094996032673>()) {
                     case -1703475912:
                        if (var1.player.getInventory().getItem(var2).isEmpty()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3cmzqqpljz5vb","X7nr9G66YmmtV9tOKyyePUUn15w8Hj/8HvhMYGh2HVc=",1082112407345202143,1041743285395426639,6400506753627852380,-7320950397692305837>()) {
                              case 1305183972:
                                 return true;
                              default:
                                 throw null;
                           }
                        }

                        var2++;
                        switch ((int)com.yiyiaddon.m.b.a<"s1bcsqn6co8su8","nD2lm3+2pfJ02fzbMm/Kbby7piUZYsBmnrwF2KKsN98=",6516879479158082641,-3985534629431373894,4579013778456144874,-268006143350630944>()) {
                           case 593924151:
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

   @Override
   public int b(com.yiyiaddon.e.i.d.b var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2fk4ggxonona6","eDiPx6zhfJZGeR19NgDEr3SjYpCxZdc4nyS3UTdxe18=",6727218344103845060,-2526476554508297409,-5659357032784017475,8656258991823822223>()) {
            case 603673437:
               return 0;
            default:
               throw null;
         }
      } else {
         int var3 = 0;
         int var4 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s3a52arppj8x2x","FTMDb/6LPDLio4H4EBimZePaw05KdAi0w+kooVzyO/w=",3486951147360598517,-4732888047271725183,1647198554981877156,6317016960279324818>()) {
            case 1336033818:
               while (var4 < 36) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2kuldlgmey50g","99TrmuG8fb0vnydeoWpqkywo8L3L9rb3F0b9UEFNKpo=",7857452190179916382,1262212025381163184,531900296496192703,-5075902320148182444>()) {
                     case 506234029:
                        ItemStack var5 = var2.player.getInventory().getItem(var4);
                        if (!var5.is(Items.ENCHANTED_BOOK)) {
                           label73:
                           switch ((int)com.yiyiaddon.m.b.a<"s3optrb54w0rvu","rMqCJvXc/gngPPAntKizG5Xzrl83QVPA0w30NZCWviU=",-4458082022924099665,-8492278303095546149,1045969777046423035,3453637539327174724>()) {
                              case -981535910:
                                 switch ((int)com.yiyiaddon.m.b.a<"s21r1zz7n4xzlq","BzF7JbMmu07pO2oWURlAA5rBLRIRjprFp+mvUKooyjU=",868446458755387163,8138365061696083450,-3225583862423598997,-1506999239221294135>()) {
                                    case 389422901:
                                       break label73;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           ItemEnchantments var6 = var5.get(DataComponents.STORED_ENCHANTMENTS);
                           if (var6 == null) {
                              label69:
                              switch ((int)com.yiyiaddon.m.b.a<"ssple9haci7as","3kWoVEJTyXdDsrw2cAs95e9JwbcAbeda0g3S0VAp3Dc=",583060012766204983,1479598652125081446,7293769753518784053,-4632760301185610570>()) {
                                 case 526257871:
                                    switch ((int)com.yiyiaddon.m.b.a<"s25vivx19tn7r0","C6Be3fMq15RR9v28FFnuE9ONY4vzxyLexoGtuzEdu0U=",-7420096056184081362,6842867697450625776,-6261838351149606375,8969077334728455149>()) {
                                       case -1413001001:
                                          break label69;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              boolean var7 = false;
                              Iterator var8 = var6.entrySet().iterator();
                              label51:
                              switch ((int)com.yiyiaddon.m.b.a<"s2q70d9jzxyj4l","LbuSBS361qy1xp/Zp/k1Xfn0/OxAXPFniVY8449vfsg=",8546743920797925460,1675791623421431981,8749189304006039135,-3106473834530157057>()) {
                                 case -1348629323:
                                    label101:
                                    while (var8.hasNext()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3ppqzr41vvfj0","77pn0kc5noCQvIITpAtCCQMzgs6hcKWLAbl5gzagkCU=",-3308139292110022363,-4460275584739342042,6253580521554211924,1328068034684320039>()) {
                                          case 1972306201:
                                             Entry var9 = (Entry)var8.next();
                                             Holder var10 = (Holder)var9.getKey();
                                             String var11 = var10.unwrapKey()
                                                .map(var0 -> var0.identifier().toString())
                                                .orElse(
                                                   (String)com.yiyiaddon.m.b.a<"snwie3br8nx51","HhAGPLPRa8cdNY3eshmP4/JeChgoNEKdXYPMcA==",-167539268137518940,-650930828664847541,-8293583861312851052,1973627627832488638>()
                                                );
                                             int var12 = var9.getIntValue();
                                             if (var11.equals(var1.bs())) {
                                                switch ((int)com.yiyiaddon.m.b.a<"sf4mash7gskd3","OM/7+IPMtEnICqVXshgYgG0atlfsEtbhngx+UGxU0/A=",3887143705449465337,-2199467862740448462,-4824710275124523815,-5773627869197609228>()) {
                                                   case 1835032646:
                                                      if (var12 == var1.ai()) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1t6ok46rz5hmj","FFAhWwPGQOxz5MEDUWXqa665ggbtP/SfR0gKU0TeY2M=",-3312415567786511789,3538179472804975999,2733929660836194272,-8154396870535880456>()) {
                                                            case -1923013365:
                                                               var7 = true;
                                                               switch ((int)com.yiyiaddon.m.b.a<"s350yrd60grycd","Eiz2cr1icXctEy4SdEGSGNB6T+p6d2vFXTdMZf6gkHI=",7259985223538821547,-8734798944821990495,1225592113826250696,-8170399798186778676>()) {
                                                                  case 359790653:
                                                                     break label101;
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

                                             switch ((int)com.yiyiaddon.m.b.a<"s2snooclmov9i8","e4Dg3JcRvbw7wEAZjePNgAnZCZ8LebPes6asJt+JcRE=",1830360969918210479,-8840887501206920918,-6620214393108811068,2745094022193082590>()) {
                                                case 1309475173:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    if (var7) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2dyr92jhkvt4y","4EQGzMELQUCEPbazQs1xSoWaMhx1oJnTUbrEJ7TCxCg=",4829938190681742984,-7388729929367532981,-1355069544785866609,-3307245075274271016>()) {
                                          case 1291876575:
                                             var3 += var5.getCount();
                                             switch ((int)com.yiyiaddon.m.b.a<"s3m47xziwrjea6","NSq0zjsRORYIqeUHRnNyzEduf8eYDd6J3K1hEN+TIR4=",1861215122077955248,-2677186277930325961,-2852792500654437080,2233224317285271845>()) {
                                                case -1529571608:
                                                   break label51;
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

                        var4++;
                        switch ((int)com.yiyiaddon.m.b.a<"s293k144avs6c5","ouv8fzbSWcxO66bapLaA1K5wYZWa7eLXwx/x8bHNUkA=",-4340743275162835076,-1260641357599547573,8462796797337113152,3338247013027959834>()) {
                           case 580838134:
                              continue;
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
      }
   }

   private int f(Item var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sit32lua1qlxt","3g44OB97PGX3zTTHUOZ2MiFeHjj1MIv1EEnZhkAsb+A=",7403816256020202482,8908586058434536307,-8054147763821780771,-186943594030591509>()) {
            case -23416663:
               return 0;
            default:
               throw null;
         }
      } else {
         int var3 = 0;
         int var4 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s1x6ziupt6u74h","DcW/qlwtJglJmJql97smnULq/bJcRPfjtFZvI5+O5W0=",-6677137252000706644,-5610378660290947833,-6646189499326172673,-1706394993220986735>()) {
            case -1623225834:
               while (var4 < 36) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1ac33qi3uoriu","70D0K1b4P5bKUxS5qFc3FJkV4QcU/xdyWCMBHiil194=",-2438006508759191591,-4967168898271530479,8991733792877389436,-782949970568711350>()) {
                     case -1692541427:
                        ItemStack var5 = var2.player.getInventory().getItem(var4);
                        if (var5.is(var1)) {
                           label28:
                           switch ((int)com.yiyiaddon.m.b.a<"s3374gy2coywfb","76TmQvMJzHFTZmbHKeFTa+z+czdPAnGbAMlX5TlV7qU=",6045535610767506765,5285702292270208808,-5627632774890230750,4495057782784949725>()) {
                              case -244879443:
                                 var3 += var5.getCount();
                                 switch ((int)com.yiyiaddon.m.b.a<"s3gcq1rk2fvctr","7wsC/FT+K5ave+DXYaoUPu5NHJILyuP71KB1hxAYdTY=",-8786018857901836102,-9213804550275349187,-7667849942537411417,3073087951966792579>()) {
                                    case -528422841:
                                       break label28;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var4++;
                        switch ((int)com.yiyiaddon.m.b.a<"s2nv3uhfyph2nu","FjkxOVxrL4eFtU8NBHu0B5o9P+FOxMeXkI4+HC9VBN0=",-9031516175776300398,-7544173710413583252,7990559682854951521,-3201339839505075681>()) {
                           case 1865820734:
                              continue;
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
      }
   }
}
