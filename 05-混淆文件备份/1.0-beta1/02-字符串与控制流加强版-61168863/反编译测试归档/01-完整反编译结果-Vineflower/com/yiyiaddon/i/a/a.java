package com.yiyiaddon.i.a;

import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public final class a {
   private a() {
   }

   public static AbstractContainerMenu b() {
      LocalPlayer var0 = Minecraft.getInstance().player;
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sbe3pu6c01vf","Htcal5qZKJQpW12dupjzDBcHD6OKUqV7wSut/+aqVNs=",8177332667891422190,-4022851001226584181,-4161031101240272268,859455400215792664>()) {
            case -1371462889:
               return null;
            default:
               throw null;
         }
      } else {
         AbstractContainerMenu var1 = var0.containerMenu;
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"sx9lxrqcd62a","+ncC2OUJm54xXcUmiZhRSfzZFgzFhJAQplIYTFzZytI=",-8274248730772469897,-1884555223012207037,3894275480730697607,1608808019628233864>()) {
               case -2058805218:
                  if (var1.containerId != 0) {
                     return var1;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s3r6rm0421iy2d","Gv1iChpY1PhT1z+nEwyA6NbKnIpaSD4uROAfUdc2gOE=",7023338928676106436,297113042975221178,-4941483584365197381,-2870919620335991643>()) {
                        case 1682405949:
                           return null;
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         } else {
            return null;
         }
      }
   }

   public static Inventory a() {
      LocalPlayer var0 = Minecraft.getInstance().player;
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1gh5buk1hsaq6","yoJiV5UY2Kb7DYWfi7u/aCE3XLRItpSkLe1L4Th9Gpc=",-505460226133447259,5426855360398966969,-2264396679830042689,746162043569200963>()) {
            case 1652459580:
               switch ((int)com.yiyiaddon.m.b.a<"s2cazs2ubf2euv","oeS8/LjUOPfPgf1mtpW/qa1e0QJZdiAbJ+XG0tat+to=",-2271339128839037165,6900852542494084914,-1011210526799413758,-4330677122682419210>()) {
                  case 340268941:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         Inventory var10000 = var0.getInventory();
         switch ((int)com.yiyiaddon.m.b.a<"s3oi73fdwfzrmy","03yEYvVp6loNWboXAZ23R+ikfAZrNGr95P5q37fY5Ro=",5311419004497819428,456543827401920980,-902095632622888107,3713815310346855860>()) {
            case 857252242:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public static boolean fq() {
      if (b() != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2sntfpad3anwm","LuVeDUrg4FHr4eDNYZr/X6+eCOF9+XKjFhXDxRClSgM=",8308264874580694214,-7554032283177810445,3569628059043350231,-8642616173061024388>()) {
            case -1630403972:
               switch ((int)com.yiyiaddon.m.b.a<"shvfdrypwiz92","5qKW3xUmhVvqG1EZbVAK1U46vg7AzO+WuCUgBabU/S8=",4741778816656596208,-2598492213150897439,4215139329419672636,18327955512735182>()) {
                  case 1741074204:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s3gzh1vt9h102u","eagPcX58rVhVQa04fId8qXUv38lZzrpi7C7brd+PG04=",2872649792752681715,-1293019572782815500,8460455202955116845,1530145297289386703>()) {
            case -1902965093:
               return false;
            default:
               throw null;
         }
      }
   }

   public static boolean a(AbstractContainerMenu var0, ItemStack var1) {
      Inventory var2 = a();
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1f1x3oo8jr55k","Ny0IUqn9fHuWXntaY9q/fWjh+ic3U7aaRTJsc2Pf4oQ=",-2550961508337347816,8192302892885041700,-3554193503790834730,-7780260117102465723>()) {
            case -1564229854:
               if (var2 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"sa5ane41h16r9","pImhWQ/cZOS0N1modWS49T8nMEfk2RaiNVHHMfqB+gQ=",2089052543367584532,4085373378611565298,1758783727830828752,-8659818165096160306>()) {
                     case -1992154277:
                        if (var1 != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s72tmak8urc42","AsnTQzVKDAUQz2oNPRbKOKoUXJTMfvVLPQ2n5u+3Cy8=",2167710124300264450,8562363206814646484,4366469871607618923,2750344330142253089>()) {
                              case 1294848813:
                                 if (!var1.isEmpty()) {
                                    Iterator var3 = var0.slots.iterator();
                                    switch ((int)com.yiyiaddon.m.b.a<"s1gngy595y6up1","O1eXHiM25Sfs0yGIQUu1hLFbFzu7+/ixn9cBcL6hXjA=",-6107595695091070653,542893225903444153,-1952666409168435279,-3747646975740401928>()) {
                                       case -1941515107:
                                          while (var3.hasNext()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"smb61exqwmpli","DcwxDB252X4qT10deZihJx4XSFrd4rsg90w8RCX0K2g=",-6996087605502725352,1927649223815791048,-2296531718536983500,-5518270290129842826>()) {
                                                case 249063776:
                                                   Slot var4 = (Slot)var3.next();
                                                   if (var4.container == var2) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"sm8v6zayf7qte","/LPgRVSR60SYT7UAaBji13hoLAmpewENXHpfaPi5Ujo=",14877154505471129,1464602046018543932,2626522694746297286,3952935811794706436>()) {
                                                         case -152929574:
                                                            switch ((int)com.yiyiaddon.m.b.a<"s3jm825bszt2zp","KW0/DEdUDufNq5UjW6jPGErlLwvNH0q1pfdZnF1Qsw0=",3509443874347567837,-6751275956363311619,-8023616767220330999,-1862410460191644459>()) {
                                                               case 635087774:
                                                                  continue;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else {
                                                      ItemStack var5 = var4.getItem();
                                                      if (var5.isEmpty()) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3c6gfx44nbz7b","olEFrmyaANeN9TxJ3bNjw3uyHP07IE74L/ZsE42vunk=",-7605047001179776004,7649736806919968684,-1773038876356671341,8598067594156474488>()) {
                                                            case 2044540199:
                                                               return true;
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      if (ItemStack.isSameItemSameComponents(var5, var1)) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"scerd3qwe2knv","7irN9mh/03ZzhPd3gm7FeIywhouxySY0nS4InFWhgkQ=",-8295310353795423005,-704231201810583507,5894714796883160669,7348550204226624723>()) {
                                                            case -957709404:
                                                               if (var5.getCount() < var5.getMaxStackSize()) {
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s16b12isd4mpmy","La1UhMSx11zbfwoJjf3mTfJVfRqHno+xFfhUtdDG63w=",3902343220654217051,-5130681276295380602,8573996847052468993,8949728524169188732>()) {
                                                                     case 272655502:
                                                                        return true;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               }
                                                               break;
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      switch ((int)com.yiyiaddon.m.b.a<"s3h47k0d0w4g8h","29QaJTTB8V/3JKw4jwP1L42gOghmDgtvRMhi2lFZcm8=",-9155550051833703820,369641586314560720,-7109953262195549801,5788248348517055278>()) {
                                                         case -686793162:
                                                            continue;
                                                         default:
                                                            throw null;
                                                      }
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

                                 switch ((int)com.yiyiaddon.m.b.a<"s2ro66n6gv6gdz","OWSfm+3xtdmqXj7b0u4z8JQdXbLxgxBA3CMMySQtZtY=",3125775177892290595,3540616497153005203,7365755285510269492,-7983737767779474764>()) {
                                    case -1157765913:
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
               break;
            default:
               throw null;
         }
      }

      return false;
   }

   public static int a(AbstractContainerMenu var0, Item var1) {
      Inventory var2 = a();
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1a4a3i7oncck7","VQ3lcj0eNjrLt16BIoCdeKvJ+SzD1sCRhmeb+VbLsXo=",-4973479720141562026,3775632582886821038,-8563819020510334421,1497984761111878380>()) {
            case -996731233:
               if (var2 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3ndvcicmzp4n9","6Nax1rdEm3d189QyYWEESRjZ36jComiWx0Z413im/4I=",1862514114004152716,-7083631215103126296,8535189717951248555,-5221781944777949024>()) {
                     case 1932651092:
                        if (var1 != null) {
                           int var3 = 0;
                           Iterator var4 = var0.slots.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s2h1wjkooeza47","/ItK63BND/ChupMvAO2lKA8s+kjoWfDHCxh5AsJNZNo=",1860267522310103110,-9168176506327988149,-6615872143349988154,56900255195112696>()) {
                              case 755719370:
                                 while (var4.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2wqlv2sox28nd","R9UHDE41b8EgDDlmJrWw6N+GVcFxtVFDE0la0WWboKc=",362748342084924158,-7851928539636990143,-2713770018525702489,3495248684001654783>()) {
                                       case 1740461409:
                                          Slot var5 = (Slot)var4.next();
                                          if (var5.container == var2) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s34v6okly50wni","7ujg2wZ/F+d+T9+geNXLQNGSZI4uNTqed4WZHvB1hRo=",-6088805959699304254,2907037877753935490,-1985210123013971689,329863515503141278>()) {
                                                case -1531016747:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s30bio8h81eg3i","P0eSNv4DZdCsSb6G49IRXcMkXNZOwIHrCQFv1q3aZDY=",-7935638434767199964,2506889249586453338,-5248117441651594921,-4603650989330757451>()) {
                                                      case -1662545137:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             ItemStack var6 = var5.getItem();
                                             if (var6.is(var1)) {
                                                label38:
                                                switch ((int)com.yiyiaddon.m.b.a<"s1ioio7xrvsvns","HkjMeYB4tO2mEbKWop1eQfksgySadseKZ2S/WXIk0n0=",-915904586970288196,-6349882306480513169,3931438855944975221,8458729670058301355>()) {
                                                   case 535516644:
                                                      var3 += var6.getCount();
                                                      switch ((int)com.yiyiaddon.m.b.a<"s25fzxe7g1yty2","PCa8IxBwmE2Wn7JqKASFuOJcRWUwyv+u4DAmMXWCLJQ=",-1570162544504770137,-5328407576751900182,-7973368895513242502,-7291292288483538033>()) {
                                                         case 2114849369:
                                                            break label38;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"srfwxebtaa6rw","URw+UnpNVEz+ngzpjDcgAe+QndtiD36Sk/cMO8cU1j8=",1493340173250927846,4745131737365384737,5903117797002132666,-5694394401798452598>()) {
                                                case -1124688754:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
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

                        switch ((int)com.yiyiaddon.m.b.a<"s1ai4jyltjgzyn","v4hjOChJ5mZ7KIQjpackH21PhppRI5fUTxbflxfZ5uw=",544766048112746848,-7359487474206481641,-5600821305716091345,3982065752952337000>()) {
                           case -213398820:
                              return 0;
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

      return 0;
   }

   public static void a(AbstractContainerMenu var0, int var1) {
      Minecraft var2 = Minecraft.getInstance();
      LocalPlayer var3 = var2.player;
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3fvxrpq6cwoyd","YHGh6wv+wu+fKRh3AWOUiRdJf0bxUd7Yxf4B6XcW1f4=",-5368365242691081861,3735850376550374026,-5569354728003813660,3863079662828846848>()) {
            case 1371727195:
               if (var3 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"sd0os31a0i5uw","/ffRV+wTZcptoob/IV96oIkwMg+7VgKTsuKcSiKUGMo=",6235663597416741939,9181244709619612359,6833820859226743200,-2346779554294169094>()) {
                     case -1931196541:
                        if (var2.gameMode != null) {
                           var2.gameMode.handleContainerInput(var0.containerId, var1, 0, ContainerInput.QUICK_MOVE, var3);
                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"sghhekgmeuii0","arb48hHoEgx4zec/r/HU+5yIVI8bAWemkEuIdslu/IU=",604856354475184297,4939654371538913456,5294998166629133401,5293888863972054196>()) {
                           case -263591215:
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

   public static void cD() {
      LocalPlayer var0 = Minecraft.getInstance().player;
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2bi2fyw378j5r","0VDVHikaKFbBIVOV+sGefVOYDyJFZPXKl9u6iG8R4UE=",7503908564015852036,7998344336625570358,6837717090525365035,4348994810801721800>()) {
            case 1045263311:
               return;
            default:
               throw null;
         }
      } else if (var0.containerMenu == var0.inventoryMenu) {
         switch ((int)com.yiyiaddon.m.b.a<"st6pro8epnrt1","fYqNdHmBXfRQ7QdeVdocFjw2bchBqqt0JdQHseRl/Js=",-2043305217555581178,-51913694456938710,-8308342798690544043,7033943599509787454>()) {
            case 2037599908:
               return;
            default:
               throw null;
         }
      } else {
         var0.closeContainer();
      }
   }
}
