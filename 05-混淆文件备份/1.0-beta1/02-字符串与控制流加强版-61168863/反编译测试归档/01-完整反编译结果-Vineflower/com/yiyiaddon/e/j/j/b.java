package com.yiyiaddon.e.j.j;

import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;

public final class b {
   private final com.yiyiaddon.e.j.a h;
   private final Minecraft N;
   private static final double aa = 16.0;
   private int jk = 0;
   private static final int jl = 1;
   private AbstractContainerMenu a = null;
   private int jm = -1;
   private int jn = 0;
   private static final int jo = 2;
   private int ax = 0;
   private BlockPos u = null;
   private int jp = 0;
   private int aw = 0;
   private Item f = null;
   private int jq = 0;
   private int jr = 0;
   private static final int js = 20;
   private static final int jt = 1;
   private static final int ju = 2;
   private static final int jv = 3;
   private int jw = 0;
   private int jx = -1;
   private Item g = null;
   private int jy = 0;
   private int jz = 0;
   private int jA = 0;
   private int jB = 0;
   private static final int jC = 5;
   private int jD = -1;
   private Item h = null;

   public b(com.yiyiaddon.e.j.a var1) {
      this.h = var1;
      this.N = Minecraft.getInstance();
   }

   public void f() {
      this.a = null;
      this.jm = -1;
      this.jn = 0;
      this.aw = 0;
      this.ft();
      this.fs();
      this.jk = 0;
      this.ax = 0;
      this.u = null;
      this.jp = 0;
      this.jB = 0;
      this.jD = -1;
      this.h = null;
   }

   public void b(List<String> var1, List<String> var2) {
      if (this.N.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2q3ya33xj2t6c","KTnVQhhX1K55x6VIazM0hmlQp7xzPPaRY5cd9yRm4+w=",3864807913691374196,-1686245542740440429,-1013720298942343407,-5854014104430039725>()) {
            case 543390701:
               return;
            default:
               throw null;
         }
      } else if (this.jk > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s18u8w3g6lw1gh","+vIPJS8KMAeR7uEAVs6ogcVY97oWjvuGa8ICtr3p7Xk=",-5031257527434705176,-1642487488851196216,-802990523548703552,618478057614110563>()) {
            case -777261851:
               this.jk--;
               return;
            default:
               throw null;
         }
      } else if (this.cu()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2q2epd8e39194","UuGezzSq00rb7pO6IkW2Etw4TQDHsqOm/eT6aWL0hPI=",-5871511785612453338,7335077057185390024,798872674660449775,2130113347819853867>()) {
            case -1788536115:
               this.jk = 1;
               return;
            default:
               throw null;
         }
      } else {
         Inventory var3 = this.N.player.getInventory();
         ItemStack var4 = this.N.player.getOffhandItem();
         if (!var4.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2kmmzdg6vonwv","BgXAFeukWfswbXj6Uy27/e0aWk1WyzQxvyh8AlyyK2s=",-2233965786753635793,355944944664636836,244618888786235378,-4356276557253449338>()) {
               case -1378994676:
                  if (!this.d(var4, var1)) {
                     switch ((int)com.yiyiaddon.m.b.a<"sibcchxdywhq7","2kqR16LwTjaH2cgY88BWlb9vHxI85GQHe+Aglf/dqMM=",-6458979159538989225,1902901032171236854,-184343748194728758,-3040034678021943440>()) {
                        case -522835000:
                           if (!this.c(var4, var2)) {
                              label109:
                              switch ((int)com.yiyiaddon.m.b.a<"s1byuhi4jmlx18","rlejVWj6gfuFg7hIJ6+q61VZhmhmpa4n3JDhw2S8054=",-715444948519196252,-2294859115613858418,5821218035153282787,2781148527988893130>()) {
                                 case -2024919191:
                                    this.fr();
                                    switch ((int)com.yiyiaddon.m.b.a<"szxvavtcq8lqn","BP+xGmk9aqgzii6CaGrEGVBn2uKnXHpqoxyfqGfufd4=",2433270172171646931,-1107830465374893593,9201322741139995269,-577899196667501174>()) {
                                       case 235576640:
                                          break label109;
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

         int var5 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s2763wxnffv3o","1s7+4N8T2eYEOEQzLPwFSn4FGMxF3CZEQZymzdjZdIs=",3440645933915544581,1015068860913816701,-1735053988069846840,4402558059523820980>()) {
            case 1258882719:
               while (var5 < 36) {
                  switch ((int)com.yiyiaddon.m.b.a<"stzytweowpe7n","oactV+w/IY4jJSZkQmSdWw8cH+bmqYKOAth7OlVhe3I=",-8057911715787130397,-3135369862565947868,8865514919608264225,-2955705253800473149>()) {
                     case 328610101:
                        ItemStack var6 = var3.getItem(var5);
                        if (var6.isEmpty()) {
                           label86:
                           switch ((int)com.yiyiaddon.m.b.a<"sciw2qjzfn1ne","beA5h9GWRVQl/K2/hg8V5lluaBaYpzD21z/VEoRfIoE=",4088043938670083671,-8679798272821715670,1149502707616285378,-8467820363319001710>()) {
                              case -769311012:
                                 switch ((int)com.yiyiaddon.m.b.a<"srlge4oc8dh6m","Ql8M4+UK5FdotYNUfaAAWUc4vYB24UpvGjWnZbvfhZY=",5960550424664907314,3054864822213284565,-2325666542391757482,6609487820425537811>()) {
                                    case 69844362:
                                       break label86;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else if (this.c(var6, var2)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3o7u5z0kwrv0r","16ThJIA+4lkgPvwHEln6jeFMhDjWClxSOSkt2XFdYwI=",4938273036127638310,-9013055837236550962,1847248194312414464,-7998709778604868678>()) {
                              case 406120856:
                                 if (this.a(var3, var6.getItem()) > 64) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2nthj3k4ie13e","kiHvQDH3kjPHrEVBVnIOKkuMrNyP9c7bSkqITagyv5k=",117015565586386290,883631758888712657,-3908418567080157662,-8747026425103879755>()) {
                                       case -1929408909:
                                          if (var5 != var3.getSelectedSlot()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s110h216djzo4r","wbT6MyqanBj9MoYzrEYsiqANFjk187ti1isQMQQvQ20=",-6141849641546598344,3966569526468129233,7647806010676208907,-2346152707295199053>()) {
                                                case 868869717:
                                                   if (!this.a(var3, var5, var6)) {
                                                      label82:
                                                      switch ((int)com.yiyiaddon.m.b.a<"skml2whvkr6wn","2PF6tU8Znye5YEva8Unc2VKQ68SO9hdDzcqVxlJgeU0=",3493310388559481082,441637295597129435,-1261466304467537288,-4855628975276600920>()) {
                                                         case 100598353:
                                                            this.z(var5);
                                                            switch ((int)com.yiyiaddon.m.b.a<"sooj8oa1mnjnq","9n/Wof5YMfhdYzX0yzuYAp/6SoadbOwoyQCRwW53gVQ=",5255374905396454957,6758400319856702987,1417481874775741027,6547643144405891015>()) {
                                                               case 820282506:
                                                                  break label82;
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
                        } else if (this.d(var6, var1)) {
                           label72:
                           switch ((int)com.yiyiaddon.m.b.a<"s1lpfwp0d7v6oj","V2taaKagCKHlpHHTjszeFus8fD0otn+r23C+ZeRRFdA=",3586824820588180130,2065106922253955337,3098428692739599743,5656931226126110564>()) {
                              case -11047515:
                                 switch ((int)com.yiyiaddon.m.b.a<"sg9qo5h99xwuh","tjPmu68LdZEBk2kBBE531DkJBamQY6aXT+a0lBY0pE4=",7381222988742024739,5818912213872645192,8617852650352129650,1624971494544435261>()) {
                                    case -1189927351:
                                       break label72;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           this.z(var5);
                           switch ((int)com.yiyiaddon.m.b.a<"s25exgzwguwmxe","qnVgcFoX/QcygmfOorXae49Qd1OUGOzVZ7zqWI4d4XE=",-5434186064953995909,6087183263388840590,1312318118094700517,-1653544452906550714>()) {
                              case -2058429123:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        var5++;
                        switch ((int)com.yiyiaddon.m.b.a<"s11qu7z38hbpzu","rEdoLb1E13cLcFwUNsLOnPYZ09L9kdQI7eLiTgHbY/Y=",3789494621226258533,2887533443280259863,8332876555691766672,7583449086437763418>()) {
                           case 1501100170:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               this.jk = 1;
               return;
            default:
               throw null;
         }
      }
   }

   private boolean c(ItemStack var1, List<String> var2) {
      if (!var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2j3mccy3owyx9","vqzNAhfAWPTEtQokoPfRiRd2PFo08bXzUinhMLiM4g8=",-825872615406447451,-7450403545722723935,7948187734043484993,-4132310156237092193>()) {
            case -131909677:
               if (!var2.isEmpty()) {
                  Block var3 = Block.byItem(var1.getItem());
                  return var2.contains(BuiltInRegistries.BLOCK.getKey(var3).toString());
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2tzcn5ld8v48r","K9hyVVsETcu/yZ1E0o6Q1b1RjEFOYFunFSb6Ly9HC9g=",314980969004016594,-8430704923784372297,7817216301681371110,-2893803255545524384>()) {
                     case -1877544574:
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

   private int a(Inventory var1, Item var2) {
      int var3 = 0;
      int var4 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s2dday92vjxi94","CeQ6UImB3KkAATJYHey0sqLxTTAwt9G3uuqXi9XNXGE=",5923382148577370156,-3785363612218094606,-8275364351852847344,-2464235267774126742>()) {
         case -1967078982:
            while (var4 < 36) {
               switch ((int)com.yiyiaddon.m.b.a<"s1hhbwvzvevxhq","ND1pXg5g6V8zhGYD0lQ/hm3PlJy0mvkXARxqlm99tXY=",127944148847177054,-3593711219597037746,5405849618782865029,3621926608562477369>()) {
                  case 1795603220:
                     ItemStack var5 = var1.getItem(var4);
                     if (!var5.isEmpty()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1gfxmgb4rsnud","7QqbcAe44CsV1V0unYk+D5R0Blp98FmSSt3OdS9VyBY=",9085098332065756410,-4229652546381363235,4553098395089895265,2805526586993125593>()) {
                           case -539706513:
                              if (var5.getItem() == var2) {
                                 label28:
                                 switch ((int)com.yiyiaddon.m.b.a<"s4khq8k29n5jz","1oNJPOYHdimIug5Uo1sEL3LS8VsTLhiG4cFaSyH2EaY=",3839041498919125281,2201638076218263830,9148539132118279090,773946642228573550>()) {
                                    case -1645951661:
                                       var3 += var5.getCount();
                                       switch ((int)com.yiyiaddon.m.b.a<"snkyyh3i2miiw","Or05kowApK+vcVW4w26dc7CnbcbEu+iM1TZu4DQTDN0=",-654310676425089211,-6689823527814834702,-8033973535716444050,-4149549214500940598>()) {
                                          case -1365536551:
                                             break label28;
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
                     switch ((int)com.yiyiaddon.m.b.a<"swk9n77zd7k2f","GapCxa1e+QYbHJ5SnbZRMU7EnzbSXMhNVw8yoNXKyis=",-3731154784968285088,2477098451705524841,-3320756142943236,2857711860272016140>()) {
                        case -1053795846:
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

   private boolean a(Inventory var1, int var2, ItemStack var3) {
      int var4 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s2ejrbubpmpz8j","UV1Es3SOHQqwWSXFwE/U/p8yRXLZEi7vtVqddGMQuQU=",726679773136987397,1537497948347051712,933713211677853330,-37335980823022573>()) {
         case -1726871588:
            while (var4 < 36) {
               switch ((int)com.yiyiaddon.m.b.a<"sk386owtyke7u","BayHi01H0yuA0d9td3beN8JOeIuX3qyExEXNOFp/Mhc=",-8540333139793562914,2434970844149943539,-4340568717151210988,7354908702803446862>()) {
                  case -912336133:
                     if (var4 == var2) {
                        label54:
                        switch ((int)com.yiyiaddon.m.b.a<"s3ia8vi1pak3la","niP/09ckMSFQVNfyhXk+vpRAObpB4CBbz/pgUelCRro=",-5193872698784661529,-6655317788418268299,2619032027964219932,2168936073254002423>()) {
                           case 1845256382:
                              switch ((int)com.yiyiaddon.m.b.a<"s27tg3zjopmdbb","LPTmnX75suwwW526UcN3N2TqsUR2/qG38kXgBSjf4X8=",-7661798928392015581,2616767048981335945,-7835832456104503539,-8760087086897771257>()) {
                                 case 14592619:
                                    break label54;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        ItemStack var5 = var1.getItem(var4);
                        if (!var5.isEmpty()) {
                           label44:
                           switch ((int)com.yiyiaddon.m.b.a<"s3twq7ckpt2e3t","8k2dMi7kg+mwhkW0n4hVHvUrO03tmJqaAhTsZIboCWA=",1702832461344252445,-4638992538890251158,913433538684565658,7977538863357753436>()) {
                              case 1590205058:
                                 if (var5.getItem() != var3.getItem()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s309prd43lo7nq","3UW4hUa211lOftPfBrgVu61CcvFwsfL5A1qCwmJph/s=",-2515498257142813267,-3534447745001696023,8707278369303575176,4349307955790119631>()) {
                                       case 912405088:
                                          switch ((int)com.yiyiaddon.m.b.a<"su5keiy6p1o8d","TBEq3z70bh6GyE9owAeXan4l4ZkVjErbbhhQ7DEiLt8=",-8209473193520855530,4500748940268216246,4306328703817051274,1240360194953659125>()) {
                                             case -1137724441:
                                                break label44;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    if (var5.getCount() > var3.getCount()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s71hyp56n3ghr","r0PbmyWgCV3OCKdtCHF/6sRyUYdKg++vW8r8wZEw7iE=",-2019850927660818563,205832185240706588,-8873764110575507178,1877477144933418697>()) {
                                          case 1380160751:
                                             return false;
                                          default:
                                             throw null;
                                       }
                                    }

                                    if (var5.getCount() == var3.getCount()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"sgtq7o1flv3uo","DKLUPRZYVj/9WlYBdgby9gGgODOpxW7I6nV/AibdBFA=",6204560016612900819,-4966977814604621694,-7364120759269122281,-650011199414799572>()) {
                                          case -941053426:
                                             if (var4 < var2) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s1s54ybguobx2a","qDL4RHkFzKgJbSnMNSI3J3+OQDA1dDDmq9V3fhetQEE=",-7368784714023259272,1343024246935142922,-2763445804784997169,7132066116948948245>()) {
                                                   case -1834029275:
                                                      return false;
                                                   default:
                                                      throw null;
                                                }
                                             }
                                             break label44;
                                          default:
                                             throw null;
                                       }
                                    }
                                    break;
                                 }
                              default:
                                 throw null;
                           }
                        }
                     }

                     var4++;
                     switch ((int)com.yiyiaddon.m.b.a<"s30x7i5vfun1dh","707m4BHLM6MkMzg9sGCxEmyMjg4IdiXET06wl7DkIRc=",-6455112730255939680,-5009731126188457919,145412371425632751,4146716696756743575>()) {
                        case 1593123543:
                           continue;
                        default:
                           throw null;
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

   private boolean d(ItemStack var1, List<String> var2) {
      if (var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1fjqb8to0b8el","DGJWXXEWdDQ5Jo+/X+rpLqOFbwirrMkuoU6eqkEOPf0=",-2961673933291059697,8392917222309767809,7747885447911965903,7285509249827066657>()) {
            case -145607442:
               return true;
            default:
               throw null;
         }
      } else {
         String var3 = BuiltInRegistries.ITEM.getKey(var1.getItem()).toString();
         if (this.y(var1)) {
            switch ((int)com.yiyiaddon.m.b.a<"s15b4e1g5jttss","P/0EajQcc+6TBiKUOJLjkmAeRpXtt7Ew3oCy67Dm7Lc=",-2126872082555667877,6961745209033708022,-4705661206940683138,-5985530762915122956>()) {
               case -458009209:
                  return true;
               default:
                  throw null;
            }
         } else if (this.h.am().contains(var3)) {
            switch ((int)com.yiyiaddon.m.b.a<"s3j4594cnaxxc9","EUWRAXsnekdZolbI3IfoLn5pt88ehRmqwRwlQCe9wS0=",2894167845518095675,5653936945408902534,3348129861419466866,-76672024961592065>()) {
               case -657301265:
                  return true;
               default:
                  throw null;
            }
         } else if (this.z(var1)) {
            switch ((int)com.yiyiaddon.m.b.a<"s91lf07wontw5","MKh7X6S1CaCWjSzvr8uXeZr4oIIf8RFh4MNXkqbvBEE=",-5604494626252255374,4889992842609469982,-5453551628475112690,846587585366073412>()) {
               case -1248206657:
                  return true;
               default:
                  throw null;
            }
         } else if (this.x(var1)) {
            switch ((int)com.yiyiaddon.m.b.a<"s3fis79c52ae2t","oW8IjiWhRzQaHeeIyQnaBCRqDQ+pJih8eivHkIINrKk=",-4727417432377426278,-2779699368648913631,2237675575156687699,3185535827482024676>()) {
               case -2067399966:
                  return true;
               default:
                  throw null;
            }
         } else {
            return var2.contains(var3);
         }
      }
   }

   private boolean y(ItemStack var1) {
      if (var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3pzzt37dj30wj","hq9Ql7OeeO7Ac1rtoP1AG2ig4801pD31MN0kKl6tFBM=",513152809212237155,8145630958113392946,-4753230738283703539,-611643714795244956>()) {
            case 2034160939:
               return false;
            default:
               throw null;
         }
      } else {
         String var2 = BuiltInRegistries.ITEM.getKey(var1.getItem()).getPath();
         if (!var2.endsWith(
            (String)com.yiyiaddon.m.b.a<"s14iz4gr8xw67k","Mw8UdvEPxgRdsm2qD+YbTzJCbnHU/aAWRVs+Car8gsRVOQmehTywV5pcNac=",7736509894125894771,-7921291141572943154,-7520384917658901986,7279404331857450573>()
         )) {
            switch ((int)com.yiyiaddon.m.b.a<"s1v2mv8mc5zd5","8QhSDk50uifVhkKNP5ReHGrJ/B/YVTNLlpn6VHuZrzA=",63083724899171991,-7306472455875544872,-8811469473285675392,-8478923075292572823>()) {
               case -371548142:
                  if (!var2.endsWith(
                     (String)com.yiyiaddon.m.b.a<"s359cfjmqwx0ck","clhQLA+4+Plo2zQAf7s/Kvt5/4xGjXYHjxL7kiyxgtV9qF9OQCOMynN0",7327554732747018016,-5648200480830776582,-8819170673897228635,-1422320146534745630>()
                  )) {
                     switch ((int)com.yiyiaddon.m.b.a<"ssmezeezkuo8i","N+lRHPEfjhrBHvwlWInb1UyZtvX+BFbsVIRUEQTPlAo=",74066148103024377,-1617644854655653418,-2164736789651245620,7083111649621315695>()) {
                        case -500786026:
                           if (!var2.endsWith(
                              (String)com.yiyiaddon.m.b.a<"s2czqyf5c5xhbx","dxyGzzI7kLD0E6nshx7J46e5Z3YX74KEdgfeJ5G8jOOqekpU",-4078964317941833068,-6311487286667985974,3875360580732846839,-5664680712277185250>()
                           )) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2wjil6h972hsj","2okmFDWt7IufLXKoEBZrazwNJ+BjXFE+7KsfvIWyfoM=",3865269930376874731,115441311400159635,-8569454292273105338,7636025118766372703>()) {
                                 case -2057911242:
                                    if (!var2.endsWith(
                                       (String)com.yiyiaddon.m.b.a<"s3foq5bpyx7zti","u+SLBZoasrczIos7Gnzec41fi6BNTWKkBNj+MtWhjMvlioyi",-2672038852580466811,-4144233495883988395,5983820290855826603,1924454690378326163>()
                                    )) {
                                       label36:
                                       switch ((int)com.yiyiaddon.m.b.a<"sjr0mk37k9j3d","EXeEXuDpqC3npM8b4P53t1ujpCcUEvMJx0ep/8Tpmbc=",-2370353492214533573,-8550733199720400913,-1715515457948978143,3433946309320279508>()) {
                                          case -1040972151:
                                             if (!var2.endsWith(
                                                (String)com.yiyiaddon.m.b.a<"s16085svlrbt7","vA3zn4O0dK2dpKWnJpJ73+2gGIPcN/qorjMj1mYgLGMBhGK7Urb8rg==",-6861874265602086391,-8810669194668653876,-2783900998450454249,4453708398581716381>()
                                             )) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s16zkxacna6i6y","LSMW8Aw50mg62Jt02N2kPfqWZVNzFmzxEPlf2ej31eQ=",-5977486026608893328,7548057526446598508,8203181572779949179,-3019237634205939642>()) {
                                                   case 689408313:
                                                      return false;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s2gv967qv6ks96","774I6OnePJwsrHJlroUaMG6dDqN2LlzXhmMLnvDc4Zk=",214426641974079482,-543016491390256164,-38038780192970258,1604327739625209081>()) {
                                                case -1240595231:
                                                   break label36;
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

         switch ((int)com.yiyiaddon.m.b.a<"ssogga3yjaqbj","J3GqBc1NhJqsmIXrxlZkyaj0lEQ5Wl84tBkbR7cSBLY=",6370051992897837169,-3837860184895529176,673811048163383059,-2827348689787580329>()) {
            case 534153809:
               return true;
            default:
               throw null;
         }
      }
   }

   private boolean x(ItemStack var1) {
      if (!var1.isEmpty() && this.N.level != null) {
         ItemEnchantments var2 = var1.get(DataComponents.ENCHANTMENTS);
         if (var2 != null && !var2.isEmpty()) {
            try {
               Registry var3 = this.N.level.registryAccess().lookupOrThrow(Registries.ENCHANTMENT);
               Reference var4 = var3.get(Enchantments.MENDING).orElse(null);
               return var4 != null && var2.getLevel(var4) > 0;
            } catch (Exception var5) {
               return false;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void z(int var1) {
      if (this.N.player != null && this.N.gameMode != null) {
         if (!this.cu()) {
            try {
               ItemStack var2 = this.N.player.getInventory().getItem(var1);
               if (var2.isEmpty()) {
                  return;
               }

               int var3 = var1 < 9 ? 36 + var1 : var1;
               this.N.gameMode.handleContainerInput(0, var3, 1, ContainerInput.THROW, this.N.player);
            } catch (Exception var4) {
            }
         }
      }
   }

   private void fr() {
      if (this.N.player != null && this.N.gameMode != null) {
         if (!this.cu()) {
            try {
               if (this.N.player.getOffhandItem().isEmpty()) {
                  return;
               }

               this.N.gameMode.handleContainerInput(0, 45, 1, ContainerInput.THROW, this.N.player);
            } catch (Exception var2) {
            }
         }
      }
   }

   private boolean r(BlockPos var1) {
      if (this.N.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s37z0ktfhtl3w7","VA6llY4gOquHT346NNWJYUKkjGP9YRW/QFbsugEFyYc=",42041111370280617,-5350593521637868750,-1775919760110371422,4537725343276051154>()) {
            case -1575247863:
               if (var1 != null) {
                  BlockPos var2 = this.N.player.blockPosition();
                  int var3 = Math.abs(var2.getX() - var1.getX());
                  int var4 = Math.abs(var2.getZ() - var1.getZ());
                  int var5 = var2.getY() - var1.getY();
                  if (var3 <= 2) {
                     switch ((int)com.yiyiaddon.m.b.a<"s8t7jsl2v2yme","KoO0WzwB3V+ttSFNXcmWSOm3Bpr5zc4eYFe/7QWifm0=",4006164033696448734,-960463285241115089,-3040052791552449911,-6062017413962442231>()) {
                        case 1704503835:
                           if (var4 <= 2) {
                              switch ((int)com.yiyiaddon.m.b.a<"s19t13caeydlow","j2vrtaem/6Ex88Qb3mvgGHURLsys5+hZ63lWY9JJO4A=",1287145715354042010,-8175050394413917520,-196550873872467292,3281398514129076399>()) {
                                 case 2031431289:
                                    if (var5 <= 2) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3tns4vdhjz4vh","92iYCcyJ8tXfc4ppH2ovyJzCpibEb0bocQ9rMVNmOGo=",6424246880210252918,1363309004594119005,7626079533746223023,-6528278294756389114>()) {
                                          case 1288125511:
                                             if (var5 >= -2) {
                                                if (var3 == 0) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"spnw5z0yva268","3CkAtXIQH97qnO3kxGF2a6Lmj1buEb6fOi/+T7H3zxE=",7733429394538953793,6454131207875942851,-2132970094190911936,-1673045778049405144>()) {
                                                      case 1876820592:
                                                         if (var5 == 0) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s3uhzjbpureqi6","fAVU7dHYq1pJeih1Dejn34Pmy8Ck/ck9RuZWEqAgXeo=",5657405595324918199,-7450661930307606993,6936780980185416346,6487950912209461244>()) {
                                                               case -1269683257:
                                                                  if (var4 == 0) {
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s3dnfembtw5lp2","nxwMDnjYBmwOyKqG7CYM0D09cjFd9NiwaDyOniVIfjI=",4370557844510462399,326806485736605094,7220681777949764057,-394047469577579272>()) {
                                                                        case -1240377850:
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

                                                Vec3 var6 = this.N.player.getEyePosition();
                                                Vec3 var7 = new Vec3(var1.getX() + 0.5, var1.getY() + 0.5, var1.getZ() + 0.5);
                                                if (var6.distanceToSqr(var7) <= 16.0) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"snyyt7ha7r2ja","M6zU6zHdhXXiQskUPF0MkvHheZjp/A6+9U72LUFCLE8=",2154006476982777053,-146266516225569668,-429375385627028952,2125014071942045978>()) {
                                                      case -1568477008:
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3qz68vuqxxkco","6i90ag6koYvky+TXucdkgzRMuyJtDLPrKnsfMw3Iqpg=",5424248788024450912,7785736958280717743,5015556987502530355,6862695121126890254>()) {
                                                            case -1761682651:
                                                               return true;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                } else {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s19o9kncbx76i5","Fms+tj1VroTbbOTFmZaWYWMBObo6WpFOQI0FIBIVa0A=",-8709357774724512735,-8918463000438097660,3778187769304800271,4221298129480650876>()) {
                                                      case 1892298688:
                                                         return false;
                                                      default:
                                                         throw null;
                                                   }
                                                }
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s2magkh3qo1e6m","Uw2iSz2cwYyh29WxMDzm4XQCT86RcmKeAZHsaMweoE4=",-4725052010803149862,-60341782001393318,-7372859131324941462,-1873759735295848919>()) {
                                                case 475136958:
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
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1a8a6nvy3ztcc","aNyFUMrPtSq+h0DklZlG2xhZOX8B4M+qMCJ1Km1M5Ig=",-8326448396107506895,7751736133755146208,3625454784421142619,6669027310822369142>()) {
                     case -953028571:
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

   public void p(BlockPos var1) {
      if (this.N.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sqzutn97i6n0r","3mlMJwz03vjC5FZO3uxPuzC3esP6f9e4W3AgIUprd2o=",-4275763303296004989,7401149248887120668,7515283729177914270,3413140567316370306>()) {
            case 845982768:
               if (this.N.level != null) {
                  if (!this.r(var1)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2w8x2d5qe51eg","yTKa9R0Vi3J1t8TW2k2s994KBouYiJIaUiAj7451OIc=",-4440308307702894331,-8301174792890099679,-8234973479460391695,5168470116628693556>()) {
                        case -1689436524:
                           return;
                        default:
                           throw null;
                     }
                  } else if (this.jp > 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s30rokqmlnvkcb","+fF5euTnCIo0rwRJEK0HREYvtjd48uzRnfcwotjiyzo=",7343576479818751561,-1876392395877363924,5160649916514878209,994743159669317315>()) {
                        case 104303139:
                           this.jp--;
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     if (this.N.player.containerMenu != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2nog1n5cnsazq","fMOTCzktTj9a8YWtS3+uOKqVGd5jhj92OgQOqs/ZlDQ=",6405795422164089894,-3399914308715536309,306300951658131690,5096910172559225704>()) {
                           case -1940902046:
                              if (this.N.player.containerMenu.containerId != 0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2824otjx626i1","QXJAeF4Kr0tu94NeIPQdsLnTyXZmdWb7fS3XYtDwBGw=",-2029252146464590369,-1794117155233446184,-8778746244014553056,-3697181840640360551>()) {
                                    case 1110620643:
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

                     this.ax++;
                     if (this.ax > 5) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3fdhzs3cv9g73","u35PWHzfgn7HA+y2HuL7Rnn3uJyxoP6INvewNJQKcsk=",-2095147301342588260,2819900659030467230,8959621443624402835,-6694993029886413832>()) {
                           case -1310526360:
                              this.jp = 30;
                              this.ax = 0;
                              return;
                           default:
                              throw null;
                        }
                     } else {
                        BlockEntity var2 = this.N.level.getBlockEntity(var1);
                        if (!(var2 instanceof Container)) {
                           switch ((int)com.yiyiaddon.m.b.a<"shtowl3dp4d4j","7qLigzNJOfC9AGNhmBxMKnJaej1IJu/KvaUCKwzRZDI=",2543835945154901932,-7279198987844169976,5103065523372494621,-8387095353912304440>()) {
                              case 1326194701:
                                 return;
                              default:
                                 throw null;
                           }
                        }

                        this.u = var1;
                        this.jp = 5;
                        com.yiyiaddon.i.d.a.a(InteractionHand.MAIN_HAND, var1, Direction.UP);
                        return;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2p6sewg3kq1yn","+QZJ5VABxfjakNXELg/WcQWYfFucWscGnjBjRSlmj44=",-5678927199112110643,2106457504029420383,-1324649421544281501,-623711996451059465>()) {
                     case -835108576:
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
      if (this.N.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s25vh7kpstadtj","h/aHgnECWtffmBDWc1Agd9Ui1EbJbcHsLvmkdn0nSj4=",-6128893454581390126,-8483724779002660507,1939419661785956131,426056465378728392>()) {
            case -1900167000:
               if (this.N.player.containerMenu != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"srggiqngqnpv","TOpn7wo7oEd/f93p/twz/2j0NMsc+DQj/jQLgR+l7IQ=",-4057908875771388198,-4531578660484605923,8894962386679165612,2421450135917615353>()) {
                     case 546789461:
                        if (this.N.player.containerMenu != this.N.player.inventoryMenu) {
                           label19:
                           switch ((int)com.yiyiaddon.m.b.a<"s139i9xv8wqe4m","erH0J762T3dajgz8A5Hesg/xloSpwDIl6IfsPOAlqLs=",2570804012915298546,5551831009469155349,-445398380801304084,3869355690437617782>()) {
                              case -1236111832:
                                 this.N.player.closeContainer();
                                 switch ((int)com.yiyiaddon.m.b.a<"s3sa82fzr4iscc","xg8u+7YbghOQ0bihX7WE/BDqmfyWyIfQGQax5mXEuwQ=",-590334278965437377,-612366942368482468,-7705043449798448865,-7851495500204531286>()) {
                                    case -1463140275:
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

      this.ax = 0;
      this.u = null;
      this.a = null;
      this.jm = -1;
      this.jn = 0;
      this.fs();
      this.ft();
   }

   public boolean c(BlockPos var1) {
      if (this.N.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sbxh4rcpzkmrq","lxnsyXlxXoz24gl6hg0idOnu8JVFH6yuHSG+85wXBVE=",-1324311885525771503,7270951250499414755,4453537080280109323,-8562099621353336261>()) {
            case 359586244:
               if (var1 != null) {
                  return this.N.level.getBlockEntity(var1) instanceof Container;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s21k28dcpfhuu4","8hqjBJVAHWbg2kw/US9qbdMbvicf7F71wB+159JOBcg=",4707750104357749704,6529428941942522043,-7920466502150285440,5153393710599564347>()) {
                     case 1407299781:
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

   private void a(AbstractContainerMenu var1, int var2) {
      com.yiyiaddon.i.a.a.a(var1, var2);
   }

   public boolean cq() {
      if (this.N.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s38xp2lseife54","67KWTVGWtgPLMf9tO+jGqYM/KUlFAjVb7IIM2BYnC3s=",-2373105413344020418,-6585108904565937354,6615239101132309583,-2286652221149629719>()) {
            case -1488632355:
               return false;
            default:
               throw null;
         }
      } else {
         AbstractContainerMenu var1 = this.N.player.containerMenu;
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s27diy0oyh9esm","vAkfOl53tZUSKGXThQBYPLNnKhUCOzu/w9DZn01ltH0=",-8233372902352170199,-6041479153651402373,283477526469974422,7326335850412544084>()) {
               case -256433361:
                  if (var1.containerId != 0) {
                     if (this.a != var1) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1beabwi0kcu83","EXOFVNiHJ9aWQqS4QpxZGYuFyLIjfQD2GykueKH6W1I=",7580065569645060220,-441271780014344733,618411834679740724,-1807123025227917114>()) {
                           case -1556192794:
                              this.a = var1;
                              this.jm = var1.getStateId();
                              this.jn = 0;
                              return false;
                           default:
                              throw null;
                        }
                     } else if (var1.getStateId() != this.jm) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2c1mqgd8zl693","XFanyicML289ztz3DMJ85zEa7eEXv/ePibV/ybBmIGo=",-4245539234230691750,-3254486487180264289,-5814569405934598573,1898317863369782343>()) {
                           case 1790043814:
                              this.jm = var1.getStateId();
                              this.jn = 0;
                              return false;
                           default:
                              throw null;
                        }
                     } else {
                        this.jn++;
                        if (this.jn >= 2) {
                           switch ((int)com.yiyiaddon.m.b.a<"s33koqek2sub9","pErG9vxfpJyDu6HnMf29+MElbdAGOuDeTL8y8NYwUI4=",-2010802948098764243,-4611756700913583671,296847885491437430,5942540974760109710>()) {
                              case 2125668550:
                                 this.ax = 0;
                                 return true;
                              default:
                                 throw null;
                           }
                        }

                        return false;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s20fha8qfkpks2","VuVx9VU+/EFMRXOwx4x48rwPwTG5VAz1vNTRu1KLzD8=",8913898765418538005,7101077832004361438,7976222925819436473,-2605417234463203235>()) {
                        case -1722250744:
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

   public boolean cr() {
      if (this.N.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3m8pv14geh3us","icnIXNQxGABDy7FolYdfdyWVBngo3Uv7Z5fIny2h7Mg=",-5622548683315872928,-4757569349211024815,3391536239717413427,-4374914090616702111>()) {
            case 1777524414:
               if (this.N.gameMode != null) {
                  if (!this.cq()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3n7rzlrwhniez","VOoyIBsqYm5xmeTMOHnhErVXts8Am9uyiOCXOkaYbo8=",-1858866377853696025,-2886001068589704948,3326500453535452414,-4489276785442609562>()) {
                        case 1357838121:
                           if (this.N.player.containerMenu != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"slzyqvtj73xyj","7NIqwTUjLCbzdlMCrV952tV/OiLUKw6piiHrcBjeY6c=",-8741402610117651867,-4956987411192778092,-8434500394491226011,-1938072372332157125>()) {
                                 case 628487945:
                                    if (this.N.player.containerMenu.containerId != 0) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3s4ia11uq85hz","xx5yubDHXR3assGxWObide6uQMkncLjJhwkWmfcHRMM=",-396880433694151593,-4974888827850585154,-5108139088758448641,-4084774579834679482>()) {
                                          case -1839567047:
                                             switch ((int)com.yiyiaddon.m.b.a<"s29csibg9wi8pd","/BxflBogUKGs12abNLU8gI4S6rCU8HmFOp1QFg1iWt4=",-2673728882524912012,153241179457109870,7407755160657307477,-9060266303237392205>()) {
                                                case -1295171584:
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

                           switch ((int)com.yiyiaddon.m.b.a<"s2pekgxs3rz6wa","JRavcRvk4yo3+Upy3Qn0HMCT4cK24EgwvQu120j3aMo=",-3249859714072094684,-6835986632003483202,8773466562948846985,-2464084983020937253>()) {
                              case 1009462120:
                                 return false;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else if (this.aw > 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1zg7vtendsduh","/cH1PrU+MOumTS/vdNEH79hjIEzeaN1idhRjgsclzA0=",4797819641602668924,-4410636539298554704,-6882453157909946539,8475653987351575996>()) {
                        case 923218954:
                           this.aw--;
                           return true;
                        default:
                           throw null;
                     }
                  } else {
                     AbstractContainerMenu var1 = this.a;
                     if (var1 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"sbkc2rfh6ucfx","ebkXGpOK1KqP8ECBBHhu5mKyluv0ENqL9mBq/b8VNdI=",-7553181624647436706,-5015966547027528601,-2349293968249826437,6635377792339730495>()) {
                           case -1562588315:
                              return false;
                           default:
                              throw null;
                        }
                     } else {
                        Inventory var2 = this.N.player.getInventory();
                        int var3 = 0;
                        Iterator var4 = var1.slots.iterator();
                        switch ((int)com.yiyiaddon.m.b.a<"s17r3wenu5uby2","+BDEEYV8ROMYsr2bTB3jaB4a2lmLixX4RdY7bTEuH/U=",-1195516319513870836,3092336406659369399,-3119000464574312803,-1970253022503734843>()) {
                           case 407628128:
                              while (var4.hasNext()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2q7qmig4n0ag6","qwX62kYAqznctlnljL1uZzVwf6QWmWMXtggoecRx8D4=",-9097870781479107819,-4959531545190000478,3490139696472037466,-5500928247645364358>()) {
                                    case -457384879:
                                       Slot var5 = (Slot)var4.next();
                                       if (var5.container != var2) {
                                          switch ((int)com.yiyiaddon.m.b.a<"sjrih7y0tb9cy","WIoQBeceIS3sicPDJPSCmlBTXPYgpDwdxjrFjrcAxGQ=",-3973751671748629379,-1579297411027995931,-7883330737282893290,5126844394465664168>()) {
                                             case -1736183450:
                                                switch ((int)com.yiyiaddon.m.b.a<"s2gmyoevu8gs5r","3sgri1acwQ6evUnwEjIyjhMcrXD924Jyaeg3usKRpwU=",3697194440133455825,6029842840638022405,6364519676149633148,2641887623559910919>()) {
                                                   case -602198753:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          ItemStack var6 = var5.getItem();
                                          if (var6.isEmpty()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2cyzpu734f7vp","uFPbzwuMX7B2xeh3VKybmWWCg3mzrZQt7di+JCKW1ZM=",-7936045046914564350,8055953430958262187,-6042957586489697484,6081113130373789917>()) {
                                                case -468810693:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2d5lqfq4pkyub","dCXuF+do4xdaBFi8X5l/E1SmF4CWDdhpDLhLwwToIu4=",-7264327897287828551,-5751490168351774902,-5955205164439214280,6098675986619502649>()) {
                                                      case 1611393177:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             if (this.z(var6)) {
                                                label80:
                                                switch ((int)com.yiyiaddon.m.b.a<"s22ynymiytq2g2","Yaf7Zx3HTMhFzUDqK/nKmvgEHvCREAuuc3YGYb5Fct4=",5536702065736261697,4500897753408028484,-3995316354461655444,5129745961903356207>()) {
                                                   case 531830403:
                                                      this.a(var1, var5.index);
                                                      var3++;
                                                      switch ((int)com.yiyiaddon.m.b.a<"slruvfui87ky3","hkD2KKclASP6bkkaOLGRe5a+7GhGQK2fx8gGWIYIvf0=",-4783688971695043742,3094751769118959367,3470252520274020541,6870255800365861558>()) {
                                                         case -1068847781:
                                                            break label80;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s24vyob1farx58","md0AN/e6pbkLz2QD5zVvxh1ywfcf1FF4zkTDXbqDXsc=",-5266956383061801293,6639939253822638172,7850491213309269034,2788638206267126655>()) {
                                                case 416128172:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          }
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              if (var3 > 0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2hz6ztbbbujj5","QTzywA7ykvg6v7zDSdf2Mi8DGDjtcgmV0uM4ROCewZk=",6357474240725988939,-8939269097266267076,1509041335535221030,6524061826360277930>()) {
                                    case 1962877793:
                                       this.aw = 1;
                                       return true;
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
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sud832rrlmgko","eIyqK2Z3EkvYzTIUv0jOswon/iqp/WG1uIZztgN6iqQ=",7832141278671201676,-1118930754503430650,4272533604561924110,-9983567067877926>()) {
                     case 1479067024:
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

   private boolean z(ItemStack var1) {
      if (var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2br24i6wusz7s","uJHAt8ls9jhtt+0R9gg7R9ixqOIHpXmwqztDe2bi7pA=",945176207568971127,-5381015394960350248,-8185514767052444797,3564730059624824530>()) {
            case 1926403088:
               return false;
            default:
               throw null;
         }
      } else {
         String var2 = BuiltInRegistries.ITEM.getKey(var1.getItem()).toString();
         if (this.h.bI()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2dfefmusi6kb8","xkjQGnJNtEwBh/4PLnGHJN9qG9UtAhlAt3mDPo/tO7Y=",2767835943401695998,-6138559580225152180,2592944153624087229,-359219773916918>()) {
               case 495449200:
                  return this.h.r().contains(var2);
               default:
                  throw null;
            }
         } else {
            return var2.equals(this.h.bK());
         }
      }
   }

   public boolean cs() {
      if (this.N.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2x0efauhd2b5y","DRQj8hjl4E8ftzkZYG0+REIjKAkLPiwrv3y2+6krK18=",3308428360348864772,3834946461859853036,-5524960299279278068,-4407050228166554273>()) {
            case -2089079029:
               return false;
            default:
               throw null;
         }
      } else {
         AbstractContainerMenu var1 = this.N.player.containerMenu;
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s18on0gyd2nc2s","s5H7ilRntA2SWxkb6UQJnlbVW2WRFT5aFevuyXwqGN0=",-2657923270577153211,-3287848685621660842,-7053810977292091344,-1046265275484708911>()) {
               case -1077681443:
                  if (var1.containerId != 0) {
                     Inventory var2 = this.N.player.getInventory();
                     Iterator var3 = var1.slots.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s28hnbfx7081ck","FXZdu5zsItfJu3KYpCCou9fTD/7CeClRYPt3B1DWXlw=",-8750225631052244016,-7771016380397145349,-1206859763056187095,-4465521183470465558>()) {
                        case 1508168107:
                           while (var3.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1wzsj0bx133at","iMyk3r5Qn/Fb8kBOzpCwhk4+GLVPtVNfBYTViDySvKc=",-2069217256256394832,5183751025314443737,-2569490252996243768,-229170717859234495>()) {
                                 case -911616169:
                                    Slot var4 = (Slot)var3.next();
                                    if (var4.container == var2) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s13zzzwp7tmd4","OyQWVCpJRXNsjWCZqabNOIAyh9uZcdRkLONhfD8gUEU=",-8596249426082579228,986166469255186816,-726561986271602597,-1234075432455957098>()) {
                                          case -1928526302:
                                             switch ((int)com.yiyiaddon.m.b.a<"s37vt4j846deag","JV+qLm8RSboLug5dU2pKgY+acqN5OgNxTBsmGcfjzsM=",8200476353319208600,6634568696658837120,-4088528181151506213,-1033459590702810883>()) {
                                                case 449183474:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       if (var4.getItem().isEmpty()) {
                                          switch ((int)com.yiyiaddon.m.b.a<"smr1ddic1hoo7","dJcdg1rjIvNXClt93LWQ9C/P96ujWUdlz1Y76qsHW7o=",9128743646085834763,-3114867234207454283,4643779618869219190,5656170668221745890>()) {
                                             case -1050309647:
                                                return false;
                                             default:
                                                throw null;
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"sbjwdkm6ly7sj","D2UvcteQRt/IZlFGCT6lIIUDs9+nlAYHRxvhXT6OpjE=",5214391053553128517,-5778986541917421427,-7325419172334084139,-8278802284436519949>()) {
                                          case 1526934030:
                                             continue;
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
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s1voxxtmhfh73p","OLANs7IFbHdCYpdo4k14tusI43i13sGdUt6fkCcXETY=",-2546110822912997822,5973694046595620912,2808172065689700300,-8211215154087325649>()) {
                        case 1425254501:
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

   public boolean ct() {
      if (this.N.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1bbv3snlifao2","gOklHJWkG8H40vHRr6ZAdCL4GpgzGmJ70bPF8L3tsrw=",2693575695053529777,-1557822151856608715,-3952653061100726588,3903689893441018569>()) {
            case -1576815979:
               return false;
            default:
               throw null;
         }
      } else {
         Inventory var1 = this.N.player.getInventory();
         int var2 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s14dce7u5336y7","/frUdWN5QUk/D8LZPUySi5mHRRjE9dJuY4s8SL2n6l0=",3332054792892057392,8267349189884486130,6617666234922428047,-2986652689054032526>()) {
            case -1551681876:
               while (var2 < 36) {
                  switch ((int)com.yiyiaddon.m.b.a<"s37w0uwxop39g6","FqMHP3Rlsf7W5qqR2QdEfrBINksEuJql/DS5QzuxpZo=",5124321837913648141,-6535395456448971540,7683324148925034710,-7974294417576892562>()) {
                     case 1534333429:
                        ItemStack var3 = var1.getItem(var2);
                        if (!var3.isEmpty()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3ae0b59v4savs","0urSKc6Udlz0o8qXDs9hO7wL+pTeiLaonPF4+KJS9Zk=",-1163622178664384208,-6762192335952606063,-3707626079926008785,-3553064325488175079>()) {
                              case 2101408064:
                                 if (this.z(var3)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3rbsjp0axlugw","ZyHD5JK7ZDziCmpgpY6DGmZv80JQwQLmsvagThoh6SA=",2304611279490467388,831940864613920162,8469066686266164523,5956247504914330993>()) {
                                       case 892891334:
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

                        var2++;
                        switch ((int)com.yiyiaddon.m.b.a<"s2e0ubfpbayki7","gDN4LdaomhMYUNEbhhuWncUSZYsB0KKhE6b3RzSmgdc=",5245627051252199991,4423368937476906522,3901631285462253734,-5709760091555457768>()) {
                           case 182937941:
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

   public b.a a() {
      if (this.cq()) {
         switch ((int)com.yiyiaddon.m.b.a<"sp5wu28aol8kl","zo8VnS2Sh2vLPbb+VLfYNjPxfZT7z8brK/TFsegUqFI=",2547905463715480959,1426932609388768591,-8602323238895096968,7913827915146187479>()) {
            case -791666439:
               if (this.N.player != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2tlpke0vt8mu3","m4hckkSzBhBoW5QLf3i8kIBxqIq+X5wfkAnCiZBf4wo=",-1963178029355179075,-8971026267760820439,-1951265350369633693,1326601412589614132>()) {
                     case 336236170:
                        if (this.N.gameMode != null) {
                           AbstractContainerMenu var1 = this.a;
                           if (var1 == null) {
                              switch ((int)com.yiyiaddon.m.b.a<"sxyjtiatllpbf","RLny3PVty+I07LfcVXKWsKDYMwCge4PPTQV7LHMcqMU=",4977324789131161000,814740207890801982,7035650074244977248,-1893330468329068920>()) {
                                 case -1938434124:
                                    return b.a.DONE;
                                 default:
                                    throw null;
                              }
                           }

                           Inventory var2 = this.N.player.getInventory();
                           List var3 = this.h.am();
                           if (this.jw > 0) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3qk5c3mtqbbhe","sY8Fg5k0KpxuFllvFLF6JOpJHwS8X9Poo12WrQlmwPY=",8064857438846125193,-6550856349637194234,110835669686089725,1051672275959829399>()) {
                                 case -1736206499:
                                    return this.a(var1, var2);
                                 default:
                                    throw null;
                              }
                           }

                           if (this.f != null) {
                              label151:
                              switch ((int)com.yiyiaddon.m.b.a<"s2dtpm9e7wrutc","iWT/LkdtHSMJposxQnQFrdWhe5Y7wTT2QDd/jbr5V2c=",5852676074437904727,8575573983568266536,-4157924573006560448,-5977665763700217638>()) {
                                 case 1994226151:
                                    if (this.b(var2, this.f) <= this.jq) {
                                       if (++this.jr > 20) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s5jm879tlwlkf","+4d2bQMbjlTpXOtYGzJLVfx4K3KkN4I2CclkwI10lB4=",6013170423298996410,-4657108673802644409,-1105477400744216929,-2754260239011639023>()) {
                                             case -1761051884:
                                                this.ft();
                                                return b.a.INVENTORY_BLOCKED;
                                             default:
                                                throw null;
                                          }
                                       }

                                       return b.a.WORKING;
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s3idjh85bg72ah","svroF3e3Wvjg3fsMbxmEkwzogD2BGLFzRj4z6KyKZiY=",-9016963285370495944,8894105705073491531,6134043169468181485,-8978088022926220499>()) {
                                       case -995588148:
                                          this.ft();
                                          switch ((int)com.yiyiaddon.m.b.a<"sr94gcwir3qht","m2jXBInGT3FepgPYdg3f8KmAbrvVG1DPDG8UdFxz7lg=",-6917150916537802860,6982590269784971981,6236810864452247312,8594281136882564705>()) {
                                             case 386547467:
                                                break label151;
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

                           if (this.aw > 0) {
                              switch ((int)com.yiyiaddon.m.b.a<"swug7yumftg76","xgOeXLoR8UjKKPsDvf3c6pfz8IteDHAXx+pRDz6fuAk=",886225987743742188,2516455031246895020,-5846239653392016083,2544868248188534091>()) {
                                 case -1226953076:
                                    this.aw--;
                                    return b.a.WORKING;
                                 default:
                                    throw null;
                              }
                           }

                           boolean var4 = false;
                           Iterator var5 = var1.slots.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"skvw9wp89x7dk","CiDEFU7K84YTg19T0mjze3bonUmgGJRWIK2GmG8UW3M=",-3696152025157581675,3240862786206961659,7820631019497072321,8580505557250081946>()) {
                              case -31426738:
                                 while (var5.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s6if93umwbntz","72/NnVF/2Uv0AJ9lKEnEdbDgZ7Yhr74x19l/YVcu+hA=",-3545150372580235071,9110906819322781524,7424003179113394147,-6227050168929046558>()) {
                                       case -1497030251:
                                          Slot var6 = (Slot)var5.next();
                                          if (var6.container == var2) {
                                             switch ((int)com.yiyiaddon.m.b.a<"sbaxl7u9dz2in","n5NQgX9p97CtE8eN2aUpxal+Bx5T8qr9BSXLTcI1MBU=",-5566192128648029805,7957630111292327066,2838989761327986375,-6538578677590801564>()) {
                                                case -1390121165:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3frf59m569iau","yzA0hUdcW33q10Do/pVLhQJwPUs9xa73XRB1pBTjd5s=",4492820808617450617,-8254894248461815022,-4715199736620461531,-5349256852681817393>()) {
                                                      case 1742928747:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             ItemStack var7 = var6.getItem();
                                             if (!var7.isEmpty()) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s2parx56801gng","if36Mm1esmjt3P4dhvOwpKOPfgiuVbBP3iBL2MVk9kg=",5127921233934984074,6048769217533056768,-7297761458232676385,3349712160121358761>()) {
                                                   case 1027349879:
                                                      if (!var7.has(DataComponents.FOOD)) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"st0nlkyci5q98","PKRBN3Fos/cIvTD2rCRIuyblFmi1SwgfGShy3oUrmQk=",9154104089163457152,4597190480944130789,6834442235681731251,-5922293855077496857>()) {
                                                            case -1932251341:
                                                               switch ((int)com.yiyiaddon.m.b.a<"s2fjnespz4dr0s","cC1ViMV29jDhgocz5qa9xwohxLzS8fDN+56EW/pqkGo=",-19156481621493598,1965190717488112770,1213437798519424407,-463439697995249907>()) {
                                                                  case 823128035:
                                                                     continue;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      } else if (!var3.contains(BuiltInRegistries.ITEM.getKey(var7.getItem()).toString())) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3c5xnxszsy0h5","gYK80jvNhjN4OcihWA8QwAFygH7cFJD05aZtXseRBso=",5426361129794438042,-8918838636701385670,5235391468573543565,308436837672256133>()) {
                                                            case 936343512:
                                                               switch ((int)com.yiyiaddon.m.b.a<"scvjvn7h3m263","hu2oI1gCNS5MxsXyUqBfT0uOLN13baRLT5TtQQv63PE=",8914582743823471890,-3684799789439628099,8013066217402423875,-4227290276626332656>()) {
                                                                  case 2089410682:
                                                                     continue;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      } else {
                                                         int var8 = var7.getMaxStackSize();
                                                         int var9 = this.b(var2, var7.getItem());
                                                         if (var9 >= var8) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s3gyi8fyxj9nhu","VKzpn5OTKRihKIrjm0kxKiU34cLfEa1Z+qJBQVOe/wQ=",3739528197803862856,8425073317833143403,-4517380924418977960,-4107579690029537173>()) {
                                                               case -723405442:
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s12300f65zzu01","1Y8NReF/6JgTE6Fd2kvi0itnm04e4Nx6sbt+YUZNNZo=",2301459438873669156,-8310605140318709176,2345109565765216119,-2285935590768848831>()) {
                                                                     case 2077395612:
                                                                        continue;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         } else {
                                                            if (this.a(var2, var7.getItem())) {
                                                               if (var7.getCount() <= var8 - var9) {
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s3q6dsoi3hp38k","1X0KVAk0OnSSvRUkD8S62Y6xqQJASfmDGWMjyNubO5U=",-7375035015781786147,-252960802766494350,-7130021519511752428,5831520508377685238>()) {
                                                                     case -1648476633:
                                                                        this.f = var7.getItem();
                                                                        this.jq = var9;
                                                                        this.jr = 0;
                                                                        this.a(var1, var6.index);
                                                                        this.aw = 5;
                                                                        return b.a.WORKING;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               }

                                                               this.a(var1, var6.index, var7.getItem());
                                                               return b.a.WORKING;
                                                            }

                                                            switch ((int)com.yiyiaddon.m.b.a<"s1n69220kb7zut","wEvXyUvsI+icGpEwFXeswho7vOO7nNa/Gc6KpNxowcs=",8369865777749383291,-8849240983505209523,8082663813919167039,-3576676791231514373>()) {
                                                               case -82421819:
                                                                  var4 = true;
                                                                  switch ((int)com.yiyiaddon.m.b.a<"sdzvnih63lxwp","kgUhRBW4VUjEDvLnmhr51q8JSBqv7qLE6DlCjdXLw7k=",-327376307558933084,5236120319583841911,6416958512879560579,-2274541720018382344>()) {
                                                                     case 362557337:
                                                                        continue;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         }
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }
                                             break;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 if (var4) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2si27tk43rrbf","o0/EE7Pp9hFBQnM62mkwsauAofxfbofT8QDQtIr6gLg=",8463224351103908288,-673647223352777897,-5968336328621210880,1352508067639811304>()) {
                                       case -219052575:
                                          b.a var10000 = b.a.INVENTORY_BLOCKED;
                                          switch ((int)com.yiyiaddon.m.b.a<"soe8756dfmngv","0OHI/Vmh5WjHzSA1O9AZTry+iAnVKyALcD0tLB7SO6k=",7292950520129099053,-8069032606909105374,-2747800587480358763,8343693309998048768>()) {
                                             case -1837301691:
                                                return var10000;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    b.a var10 = b.a.DONE;
                                    switch ((int)com.yiyiaddon.m.b.a<"s434f6wkv4s3r","l074js+Dc03JJV4PtbEqyPPon/b5hU8yYLpfHLmIpXw=",1090063475924661582,6622040596182677400,3080884564687561640,-4899239367412886554>()) {
                                       case -624738870:
                                          return var10;
                                       default:
                                          throw null;
                                    }
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3ubhfivpgxzhl","sBTstf9wnvP0GS0Vm1WKs/XKIkHekDMss6H1VYkMIlY=",8119418690961672176,-3376816967205705175,-3852110211804873234,5880686917346387226>()) {
                           case -1214156264:
                              return b.a.DONE;
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

      return b.a.DONE;
   }

   private void a(AbstractContainerMenu var1, int var2, Item var3) {
      this.g = var3;
      this.jx = var2;
      this.jz = this.b(this.N.player.getInventory(), var3);
      this.jw = 1;
      this.jy = 2;
      this.a(var1, var2, 0, ContainerInput.PICKUP);
   }

   private b.a a(AbstractContainerMenu var1, Inventory var2) {
      if (this.jy > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s3e7s1j6ccmhsv","MsvN4qgbJ8/873qz9SJ+cjOYHmsYApuxAcAkco0jxhY=",7977220138035049595,-8744708056582430996,8502883187153727597,-577885052284149989>()) {
            case 261232393:
               this.jy--;
               return b.a.WORKING;
            default:
               throw null;
         }
      } else {
         ItemStack var3 = var1.getCarried();
         if (this.jw == 1) {
            switch ((int)com.yiyiaddon.m.b.a<"s30q0yaakm8y1t","zzLTc3DJW9e8Tyx3Fd9TteLsN0lEQLFc7mkw8nDGFys=",-6233973682252532798,7626058229416838310,-2276976969018492432,-6056316439608192332>()) {
               case -2060068770:
                  if (var3.isEmpty()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1lz4exvn4hgbo","yF4mTVe295smxXwfimazicwJywVB1qgsoMuOmKurUys=",-6136097634441805190,-7248201393816701406,-423473849335298354,-5438357175774777643>()) {
                        case 1296180828:
                           this.fs();
                           this.aw = 5;
                           return b.a.WORKING;
                        default:
                           throw null;
                     }
                  }

                  this.jw = 2;
                  return b.a.WORKING;
               default:
                  throw null;
            }
         } else if (this.jw == 2) {
            switch ((int)com.yiyiaddon.m.b.a<"s2ed71yshwyuwp","33JAX/33CrrcudefpICAKXcJnpMcEZ29ozAo35xYc3Q=",7662914632425411572,2807230433481574863,3779378534142928786,3394450447717343139>()) {
               case -169236686:
                  Slot var6 = this.a(var1, var2, this.g);
                  if (var6 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1q1le9638thpf","fmKsUjgluwDenq6wKexVr1dwHQtmCLxCl0MWs7PNlVE=",460487427513127903,650301792394085582,6017296309258999859,2023119769472720781>()) {
                        case -515636052:
                           if (!var3.isEmpty()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s20d0m0x0tadhl","pX55Ujo2w0tXwxWYvqbSVlKRODvnp+o1mppGaIXae8I=",-6704390894837808029,4447861528342718942,5313588542137645381,-5625483447322177600>()) {
                                 case 64434538:
                                    if (var3.is(this.g)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s20sx3bhbatnr6","8M21wA9yyWhfZ3uauYLxrMS9h0ftmga43q8c8+FQfaw=",-9192936375693783433,8215740758774006393,6460029305478170413,3998563277894819541>()) {
                                          case 1541617792:
                                             this.a(var1, var6.index, 0, ContainerInput.PICKUP);
                                             this.jy = 2;
                                             return b.a.WORKING;
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

                  this.jw = 3;
                  return b.a.WORKING;
               default:
                  throw null;
            }
         } else {
            if (!var3.isEmpty()) {
               switch ((int)com.yiyiaddon.m.b.a<"sf9yav2pxo0f9","1tU3MN9HpUNjo27BMiLo4+s347/PA+/fDUuitHWpmcY=",6166734239808033719,8309109042817167953,4168859309301134366,-6897415505269995276>()) {
                  case 1655823811:
                     if (var3.is(this.g)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2g9ndir5qbq0o","+4N2EOVnRwPWL9QrAOQXrJ5F50N0xGIY6IID+G2K4m0=",7206662503322421058,-8537573094495140104,-7300884662798203551,-1292619287970469221>()) {
                           case -1549113694:
                              if (this.jx >= 0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s11ubsw04clmps","+wv7Y7z3BAGZkC3DjTb4Rrb9BNkEUrOSRmazFjzCh40=",5342924264159117254,-9219308503775158783,8123154524378156634,-1340651189573365010>()) {
                                    case 1776187686:
                                       this.a(var1, this.jx, 0, ContainerInput.PICKUP);
                                       this.jy = 2;
                                       this.jx = -1;
                                       return b.a.WORKING;
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

            int var4 = this.b(var2, this.g);
            boolean var10000;
            if (var4 > this.jz) {
               label81:
               switch ((int)com.yiyiaddon.m.b.a<"s6lgz7mdscrtm","JQhYr+f5e1kg/lyzm0u4HsGKKosa7Bh1kucFkC7E0uU=",656228085956002349,-8498469539959417035,5136938808696918254,-7757286112699747230>()) {
                  case -420196695:
                     var10000 = true;
                     switch ((int)com.yiyiaddon.m.b.a<"sjqp7shs7wbu9","K2EUYM4mVKy0OgEO3DN6vx2Tu5ijlLyhWd0/rmlmda4=",8837563530348165109,9090493116304859731,753815815441224797,5633299904661815432>()) {
                        case -1804332159:
                           break label81;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10000 = false;
               switch ((int)com.yiyiaddon.m.b.a<"scq0twfog4id","LlgKeYFBBQcTgokv5GhF3fXtbdTFPEk48Agp31nrFgQ=",-4608236673961171485,419215764706460274,4611620100148964219,6531045597781933773>()) {
                  case 2003468824:
                     break;
                  default:
                     throw null;
               }
            }

            boolean var5 = var10000;
            this.fs();
            this.aw = 5;
            if (var5) {
               switch ((int)com.yiyiaddon.m.b.a<"s3dakx46nennnl","Qo+XfGKVHavM8ln5GnDL6Q6+KAUAEZjQF9iU0WujB6U=",6422643462782956382,-275549032386448119,7434238695490764435,-210445447233611341>()) {
                  case 232550104:
                     this.jA = 0;
                     switch ((int)com.yiyiaddon.m.b.a<"s37dlye6yr0lh8","TNF+9EHF8T6LprvcXRdPZdmvzx4skzCiMQHyrXzf2Es=",-6834179928345710582,369442354358227615,-3379347373370536498,660248677074551122>()) {
                        case 1401965350:
                           return b.a.WORKING;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else if (++this.jA >= 2) {
               switch ((int)com.yiyiaddon.m.b.a<"sdvmr5wrvsw12","wdJurcL5qrJh1KVSZUzt+fQ0eRBq6i+IVtyeEBGzkaY=",1568835888496521326,3342688771324513535,8723121292860275825,-2927585414018134383>()) {
                  case -1097543495:
                     this.jA = 0;
                     return b.a.INVENTORY_BLOCKED;
                  default:
                     throw null;
               }
            } else {
               return b.a.WORKING;
            }
         }
      }
   }

   private Slot a(AbstractContainerMenu var1, Inventory var2, Item var3) {
      Iterator var4 = var1.slots.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3r17hkm9rsp3n","kQkjWEQHhqXkYOxiS4sR65JDhk6VamwZtyekYYYPNb0=",-6259380842458141373,869131746981814234,6803458897149783815,2042276953900612769>()) {
         case -560582569:
            while (var4.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s28ke0tmyclq5n","2AoSjE4d35A6hSToe9ThGCXvAvMPE3QqdhpKWGRBmmI=",8731527255381632032,-5668721102557226330,4492269669936082353,971536126495698563>()) {
                  case -218478160:
                     Slot var5 = (Slot)var4.next();
                     if (var5.container != var2) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3rwm4b90gn45a","kPwt2zK8a1z2k0WbRg3vekQo9tT6oMqRFk5JNebk1Sk=",-8367906257968117243,-5328076491868453731,-1242909597816402,3563180290707344521>()) {
                           case -1869057163:
                              switch ((int)com.yiyiaddon.m.b.a<"s54jsvilwm9f0","8dD0oM4gzVLPsZXYWB1EpX7aWh8REUU8zJfkya7YlL0=",-2938497153298766570,-7690157410021315215,-4310200811129893063,7823906219343931901>()) {
                                 case -1410835416:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        ItemStack var6 = var5.getItem();
                        if (!var6.isEmpty()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2q4oh7hwp3ivb","uhRfmLaCJFbrN6MDZMg9Bgu0U9ukJEOvteo5gK27sfg=",3419636791666464966,3933425293260119922,6916708589338655959,-265258875869218419>()) {
                              case -1966185633:
                                 if (var6.is(var3)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s6iyngfwzn6r1","Vq8TG0zuIMoT0GMWVBDolF3fR9sthBCnZjrTqtKJlcY=",-7051777701979869405,-3892388514363158526,-6597945599544299932,-1553141602907310145>()) {
                                       case -1825750030:
                                          if (var6.getCount() < var6.getMaxStackSize()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3vm4o85bt9ht9","l7tXMp7Qv0M1j1vmR97d5a5UGHAiGn4I38DEhbTz0Fw=",5687803763128505554,-409287502164214356,7475820370807322361,8927166364500566536>()) {
                                                case 1927378696:
                                                   return var5;
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

                        switch ((int)com.yiyiaddon.m.b.a<"s6yhnn4az2tok","0Ms61E1wvtVFwv0B3qmR8sYZbu3oKzuNIrVbCM2PikY=",-2204572755975788237,-2133440085954187332,-2166547204485490680,-6303812851353850247>()) {
                           case -1646997217:
                              continue;
                           default:
                              throw null;
                        }
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

   private void fs() {
      this.jw = 0;
      this.jx = -1;
      this.g = null;
      this.jy = 0;
   }

   private void a(AbstractContainerMenu var1, int var2, int var3, ContainerInput var4) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1syhmimo4g6i","0QgjiPGW8atmm8ZHdmySU6en9nxqdu7prR3Rs0Fo+nc=",976471529616007025,7341562037008849014,5003801274998425629,7787828219807727081>()) {
            case -1009900789:
               if (this.N.player != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3jb1sbylbkevo","k61njc0FEBLDxQyvJhXh+jTjMCruAECLn0OMl/YQmbw=",-6858869859388637846,7331388083363253856,-8056207746412711623,-8732984016237138684>()) {
                     case -1530379140:
                        if (this.N.gameMode != null) {
                           this.N.gameMode.handleContainerInput(var1.containerId, var2, var3, var4, this.N.player);
                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2yo9zrdl4rqrv","6nQqtg+virSskr7tLOdQC3slkBSj8V9/Udrm7NJYSSc=",-8845522849515304024,-7666739265354150704,-3004565675993859364,-5865800603644310132>()) {
                           case 1294206535:
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

   private void ft() {
      this.f = null;
      this.jq = 0;
      this.jr = 0;
   }

   private int b(Inventory var1, Item var2) {
      int var3 = 0;
      int var4 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s3715x8r4p034e","+4cPyrRWJVNCZMnYdfsTMRhBwula8uPsRKs1rii+1Kk=",1656418784346570635,-5076450762473008695,-8297128084046116158,-4095579383334813981>()) {
         case -71010586:
            while (var4 < 36) {
               switch ((int)com.yiyiaddon.m.b.a<"s295gnukg5c5tc","JKE6W2LZJbB/EL20xWdHujRn+AW+/k4ZRWA6hHSpnP8=",-7617577329409944982,6843758352489646958,-7876942693053403443,1624985351265726867>()) {
                  case -1198183617:
                     ItemStack var5 = var1.getItem(var4);
                     if (!var5.isEmpty()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3bqo83xb4isry","BdzcaU+Rrklm9bUx21Dt22eB3E509ZXIe6/v4RK5Y5Q=",-2809644856484019851,2961346872094016681,-1895501914546033523,-3496750741968132784>()) {
                           case 256878579:
                              if (var5.getItem() == var2) {
                                 label28:
                                 switch ((int)com.yiyiaddon.m.b.a<"s11wut45c3u32q","Sjw8s8y7zqT3p/UEAMjz3Jnjpcjkdi8VKyJFEvwTZlQ=",2086049839235829982,5197152571964833132,-3089394195104192514,-6704355481643564032>()) {
                                    case -141562092:
                                       var3 += var5.getCount();
                                       switch ((int)com.yiyiaddon.m.b.a<"s116c6chi3yjf5","HG9cRCJTh12uCB9pvfhbMmV2lzasg2Mq7+i/SlD9iUM=",-8521293115869385986,4807673062880445271,7179255482696544095,-7385886588767151913>()) {
                                          case -2072347037:
                                             break label28;
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
                     switch ((int)com.yiyiaddon.m.b.a<"s2kggkr0ohzo7m","ZMEVSATwtNjsSbJAozyKnsnMDIeHmlcr+7RqGKTGKfg=",1953763474840377951,-7868035533048868499,-7120560120240760687,-1970641573269237508>()) {
                        case -1572150772:
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

   private boolean a(Inventory var1, Item var2) {
      if (var1.getFreeSlot() != -1) {
         switch ((int)com.yiyiaddon.m.b.a<"sx2686bk6nu93","KsfTJOBQrufMOTTHlyDp5LKRZ01jHKztfODwCV3BZVA=",-7359072184182281974,8402164552529832119,5644651194980644003,-5430114824043438196>()) {
            case 1218080827:
               return true;
            default:
               throw null;
         }
      } else {
         int var3 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s183lho3kw8gbt","4bY2bb3JndPW1vaOe608ueZoaGho/PJbsJn9375X6Fo=",2295997302491154419,143825967727319596,1636232017573700991,-8779734247817232784>()) {
            case 1286868603:
               while (var3 < 36) {
                  switch ((int)com.yiyiaddon.m.b.a<"s236u4pf16ozmz","gRJmqZYcP8NEq1zwH2TaulBvWgVdK8tQSj1jP8O26ek=",1362596857158009508,8522815945326430270,2462268567915456471,5512059634532144636>()) {
                     case 753762353:
                        ItemStack var4 = var1.getItem(var3);
                        if (!var4.isEmpty()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1si801khkmqsf","qsjd4YWePHfhZcOz5IHMtaNH2FOeYv8f+9wPXEiVSgk=",7875272705843179515,-2636479506468592202,8220546405838898414,-3997730776777099635>()) {
                              case -348892240:
                                 if (var4.getItem() == var2) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1d13fgcnj2i45","2eRaM/Ge/1XD58WT6imjkV8hzsuYOegDAEj7mMIaaQg=",-7999341640217784365,7816161492338365814,-3484187150496026618,3740210058962551394>()) {
                                       case -686151510:
                                          if (var4.getCount() < var4.getMaxStackSize()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2q8dawhs74027","WoIWBGWqXvBI6vHx1HyWSDvCSTNXc5V7uk33ial4t4s=",7803990440292094728,3182676074110254150,9175112269417734961,3109399754580717734>()) {
                                                case -511948368:
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
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        var3++;
                        switch ((int)com.yiyiaddon.m.b.a<"s7pa933mwlh89","WXuh3iL2Qr/pXjx89q+IR0SSCVuYAI4O+9Jc1CFsfd4=",874599965707707966,-1296954608672658204,-741696854009264012,4139112731825360720>()) {
                           case -949985294:
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

   public void fu() {
      if (this.N.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1gktpfoon0lc2","TjzJClFL/5h/ycVw8ai8feYobPAsgy5VFcwju6Ml2uw=",-3278406654161364202,938791802971210627,5516909078905126902,8203737157469461403>()) {
            case -788267231:
               return;
            default:
               throw null;
         }
      } else {
         FoodData var1 = this.N.player.getFoodData();
         if (var1.getFoodLevel() >= 20) {
            switch ((int)com.yiyiaddon.m.b.a<"s3extp9r6va62k","IwDVQNuVOxTHa0T/pPpelPd+HQG/X/L+smPglT5eL5w=",-1998574159895928598,2969965462526621748,-5965748234947030453,-6157324101553523480>()) {
               case 404186476:
                  this.N.options.keyUse.setDown(false);
                  return;
               default:
                  throw null;
            }
         } else if (this.jB > 0) {
            switch ((int)com.yiyiaddon.m.b.a<"sdpejojc7adik","k2MOhiKGYpAruwby2hOtutX6eOLPYOeBluxKTB/Jd/c=",7894010971156357801,2671412160042210183,4390540756030699998,8192979682000165885>()) {
               case -899836287:
                  this.jB--;
                  this.N.options.keyUse.setDown(false);
                  return;
               default:
                  throw null;
            }
         } else {
            List var2 = this.h.am();
            Inventory var3 = this.N.player.getInventory();
            int var4 = -1;
            int var5 = 0;
            int var6 = 0;
            switch ((int)com.yiyiaddon.m.b.a<"s2ax564hi4r2r5","ttZzRb5eEp63s81n6zwpICl6/QixTFo9r8loDy4UJqo=",2849605944758548283,-1424554719204180846,966921345557717258,6772622210026434043>()) {
               case 1182608934:
                  while (var6 < 9) {
                     switch ((int)com.yiyiaddon.m.b.a<"sapqja0z05vek","ELJiVnE9QkqxlJHUoCsF17uDI5k0OFMkLLvKhzG5ct4=",6395084867796089315,-8913905701045085985,-702350949580522015,8185779206735176133>()) {
                        case -677469472:
                           ItemStack var7 = var3.getItem(var6);
                           if (!var7.isEmpty()) {
                              label161:
                              switch ((int)com.yiyiaddon.m.b.a<"s32vmdmcj7go89","vbw7riL5KpNlui2VCgo2JyzWm+3KTHn/RwAiB2ZV5KA=",7940541110402784755,3105575919558035227,-5886158383273774852,4718796909397612614>()) {
                                 case -871318646:
                                    if (!var2.contains(BuiltInRegistries.ITEM.getKey(var7.getItem()).toString())) {
                                       switch ((int)com.yiyiaddon.m.b.a<"spng8jt3k3oud","zVBSSAgoaurtpSoRIb9BVJSfzQJHFfT/QLSAUyvsE/8=",-1476409812428208658,-970151396567927666,-8547335650038539223,2306732369974751791>()) {
                                          case 1441765159:
                                             switch ((int)com.yiyiaddon.m.b.a<"s1bv8se1tj52wv","Uc4VgCW/niSPsCQCVyeQiNuJ8t1Kj9quTatdZzF0oEA=",3801266578770794960,-8186974954398614782,-177513135040289261,2617823596141353876>()) {
                                                case 332201645:
                                                   break label161;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       FoodProperties var8 = var7.get(DataComponents.FOOD);
                                       if (var8 == null) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s20gpwtz9f8uv1","+w4L6ZqlVdzs5l/+FaBDsaXiTVJULwuObokN/oDOR+8=",535989659431285385,-5162499005373987857,799724324298729250,6119779242930477063>()) {
                                             case 573234994:
                                                switch ((int)com.yiyiaddon.m.b.a<"s3cdcel4fwva4g","GVKnP1Rryszm0djlryac5lrklkEkNTXaDi1gIKKYj94=",-5867822488122575689,-6859923282132881187,8747292412112957037,8686790314268020978>()) {
                                                   case -212860799:
                                                      break label161;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          if (var8.nutrition() > var5) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3kxlr7nj1zbz0","QHDClYy6YFmD8zejt+0jeumzazYjh+PJ9m7/VnJYIOQ=",-3516994006440164073,2482239962593178876,-4891800475345334434,6965149343527239499>()) {
                                                case -1670689407:
                                                   var5 = var8.nutrition();
                                                   var4 = var6;
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2grumid4g9u1i","RR6t98GzS29pxu7+yW5wVgNDdnH9MVKAXFxDi6degHQ=",-5041464276358119765,1037894076422022042,4867542657858402891,9018063993931386258>()) {
                                                      case -1468033983:
                                                         break label161;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }
                                          break;
                                       }
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           var6++;
                           switch ((int)com.yiyiaddon.m.b.a<"s3b4a5heg5x9fi","GpI2ZSMLfDo1/ofM/X38LE0K6Hfe2SNuEUpe3I5qTWM=",4283364472884978388,7909779283027190128,-5625805180007618637,-632756399230289250>()) {
                              case 251831282:
                                 continue;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (var4 == -1) {
                     switch ((int)com.yiyiaddon.m.b.a<"srf8cgk1wrb7j","n9568+1ee1qLHhnkNME5yv4+jVA9mrR/jMtcAZK+5As=",5146166826386327307,-7263124286938407299,2428118468936245163,-4392381120431518279>()) {
                        case -2081605720:
                           var6 = -1;
                           int var12 = 0;
                           int var13 = 9;
                           switch ((int)com.yiyiaddon.m.b.a<"s3smevw0mrprac","StA5LXX9kJhf/lEwoKuasP5lR6ouKjERv02ir8KewX8=",-2029005070879137608,-371353815721250701,-8710108573899388281,9173645493497492650>()) {
                              case 172892198:
                                 while (var13 < 36) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1qn76d86net2p","fkoQ5IWMmKG1jpNtFrRJC/q1L9EUZqdnmB6YAABjjlI=",5448142350226272196,292294989962840159,4730390456318476913,5662455979939736086>()) {
                                       case 378942508:
                                          ItemStack var9 = var3.getItem(var13);
                                          if (!var9.isEmpty()) {
                                             label119:
                                             switch ((int)com.yiyiaddon.m.b.a<"soayqz40dd6z5","+VcDPmMhk2YDP4xKw027C9T0L71Foiq9CIGglEQI2lg=",-8789329752454882293,1411410850626765606,-1716185988832713011,167780773997715605>()) {
                                                case -958539947:
                                                   if (!var2.contains(BuiltInRegistries.ITEM.getKey(var9.getItem()).toString())) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1xxl8gn1d7v6l","IUqFwK1lcjcSZeKLnFjMNHWYp1iOtkjV0Yq8WzTzuFM=",-5020216801918251032,-4159546392869214052,5624626321341208269,944406343526702247>()) {
                                                         case -825440357:
                                                            switch ((int)com.yiyiaddon.m.b.a<"s6p5v58cittlq","doPiPjidYX2BLosTnQv1ZOQ66k3R2vt4jumXtAfzWYA=",251825609989718708,-7163281926848820464,-4620400769393863666,-9223014133801675503>()) {
                                                               case 283183444:
                                                                  break label119;
                                                               default:
                                                                  throw null;
                                                            }
                                                         default:
                                                            throw null;
                                                      }
                                                   } else {
                                                      FoodProperties var10 = var9.get(DataComponents.FOOD);
                                                      if (var10 == null) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2g3rt7ancy0wf","u4zO3/BnsAJoQN9GH4FgV0n6BQExciZI6UL3kvnYNOY=",-8104959781295022126,-1292898571321441907,1665531433614123337,2074567470610286192>()) {
                                                            case -1866271780:
                                                               switch ((int)com.yiyiaddon.m.b.a<"s3atpc3hwnc7m0","IJPUYGdAORSZw7mVtb5KALeCDB8nj1x64C71Nq37BWo=",8544970056038897839,-6936436813362348325,3247745889590809273,-1624114480231661609>()) {
                                                                  case 1456139281:
                                                                     break label119;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            default:
                                                               throw null;
                                                         }
                                                      } else {
                                                         if (var10.nutrition() > var12) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s30njagnzpc51d","qrzyDNulE+CbQ/fzdyL0RqrAFCIZisvSgejsuAkj50U=",8248676098769808348,-4509416461267105833,-8656330885569784823,-6392647162280814447>()) {
                                                               case 1568217329:
                                                                  var12 = var10.nutrition();
                                                                  var6 = var13;
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s3oetlyi3c4fh7","4GBAWMOuCFOkn4I8d3wJ06595MtYX+BCgQLkIqxtPhU=",6379933868156279474,-7937581567567733536,6734951614888834983,2596538725728917301>()) {
                                                                     case 1249506558:
                                                                        break label119;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         }
                                                         break;
                                                      }
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          var13++;
                                          switch ((int)com.yiyiaddon.m.b.a<"s1dt71yduwv0bo","5PexPgxKB3HzMX55KTUxRr2FBoqdReHWxizxOkRCytU=",-498295360740931647,2047732539998384016,3359708622437654945,8854232035809306459>()) {
                                             case -1026626947:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 if (var6 == -1) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1z5u6oiarszom","m5pC1hKLkzKk0+2eqzgHrCKNHsX+y5w0RjXcrIKqq/c=",5378077083414199461,6693242477861737162,6053458085511380685,-1440665095410173529>()) {
                                       case -57327146:
                                          this.N.options.keyUse.setDown(false);
                                          return;
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    var13 = this.bG();
                                    if (var13 == -1) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s27ntftnu3jsyf","0z/hMDKd7mP5GplNSVTZPcZevN1Uwtpsdp9Chz1uuBY=",3251770993339086395,7666360435872544508,-1051124599863145431,8245829216561177943>()) {
                                          case 974730638:
                                             this.N.options.keyUse.setDown(false);
                                             return;
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       ItemStack var15 = var3.getItem(var13);
                                       if (!var15.isEmpty()) {
                                          label107:
                                          switch ((int)com.yiyiaddon.m.b.a<"spsuvrru2vak3","+UKnKbMXK8no5Sc4CEjYF1T89O2OJt+xkB1VxSBmAAw=",-1961240548985184944,-1406865823928329715,7718663896346885052,-7125454793454879712>()) {
                                             case 738075115:
                                                this.jD = var13;
                                                this.h = var15.getItem();
                                                switch ((int)com.yiyiaddon.m.b.a<"srkfgdk2osc0m","OjTv2MnGaojtPg89RfaMs+sMxQAeyU/V3qhYVD1iCQQ=",1933714372976579441,1608628646995556763,-1461161239835100662,2551357868559005000>()) {
                                                   case -1782549119:
                                                      break label107;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       if (this.cu()) {
                                          switch ((int)com.yiyiaddon.m.b.a<"swzcbb7qzyf3r","FOdVfwNLYjgHqRA9u9do3PNwd0WdCQ8gfCNw6QyscCw=",9174308194879258005,1183788611630569740,-3288419292540456074,6029647517042540016>()) {
                                             case -317239075:
                                                this.cD();
                                                this.N.options.keyUse.setDown(false);
                                                return;
                                             default:
                                                throw null;
                                          }
                                       }

                                       this.a(var6, var13);
                                       this.jB = 5;
                                       this.N.options.keyUse.setDown(false);
                                       return;
                                    }
                                 }
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var3.setSelectedSlot(var4);
                     this.N.options.keyUse.setDown(true);
                     if (!this.N.player.isUsingItem()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2fsubg2yxz2o3","nhF8QTETjvGINnBVXYF3+hXNhTYTp0Z4SLPm8+Wa500=",5642725041036923394,-6731496003765871823,3552072642364892466,2472969582087310085>()) {
                           case -394070598:
                              if (this.N.gameMode != null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3tv6niulpwdf","5YPufNgnzJwTbdCrbflTQd6l3Vru2K0EmPMu+VdYxB8=",-3500366036454690404,-8100083456049634641,955798682605460558,2937282760236681547>()) {
                                    case 1854310760:
                                       this.N.gameMode.useItem(this.N.player, InteractionHand.MAIN_HAND);
                                       switch ((int)com.yiyiaddon.m.b.a<"s3pl4yl5x82x9n","LP8xVV1bXvFYnTnjvxrpOR09AL+v+wfw//7n1dV9u54=",7315183480111248996,-5853100652194299910,8612953613233808402,4331712415806425346>()) {
                                          case -877204884:
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
               default:
                  throw null;
            }
         }
      }
   }

   private void a(int var1, int var2) {
      if (this.N.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"svmo52kgx5656","701l+yNYmstEvLjHVSzDGseJiLTlBffhZaLs8nJFIbQ=",-506468550047239069,3512923007057359002,8156364543826457868,-4962667506582174189>()) {
            case -998500962:
               if (this.N.gameMode != null) {
                  this.N.gameMode.handleContainerInput(this.N.player.inventoryMenu.containerId, var1, var2, ContainerInput.SWAP, this.N.player);
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1d3mh5ymq3ggr","j+Ivh8fsriTrfmyav4I1esxT4+6EgQreiL1nRSb/gbc=",-7938742749099861719,6514137509082618844,-2928800106703047836,6405221193612573835>()) {
                     case 998632265:
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

   public boolean cu() {
      if (this.N.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2py8d7mo0xfnq","iaQGG7oFsf/eipIjMqbnHlldf283B/kmtQBfMyu1kOE=",5512805061998184619,-2558504483302865955,-4281750615601332919,-1506759645884740569>()) {
            case 968317634:
               if (this.N.player.containerMenu != null) {
                  label25:
                  switch ((int)com.yiyiaddon.m.b.a<"s1k4vcr6rp6rhs","nSrx3dejLaepNDV/CGmTHhEKwCJZf3fdWubm7W8vluw=",8267520750224255608,-3865187839394322273,3380652254289236835,-8858339050624399945>()) {
                     case -271465182:
                        if (this.N.player.containerMenu.containerId == 0) {
                           switch ((int)com.yiyiaddon.m.b.a<"smqjmih78gtf2","xO/DrHRES1zz7UFdilCG2cZH2ZsuJk/jFUFYB0UjJ8c=",472540694707874651,4339238711953079028,-2976188173964361853,135618667453884965>()) {
                              case 1452604055:
                                 return false;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3fa8thgzw3any","eq1Lup+iQO0o70K5kea9MYO9sjJphok7hrSJE5AfFtA=",-819373341967009187,3732762105250612130,-833442969395186375,-8574300031546297297>()) {
                           case -1576059441:
                              break label25;
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

      switch ((int)com.yiyiaddon.m.b.a<"s3c96nvsotuh9f","Qclgo7X0A4OwYltnjD1pUMs43Oeeez2RHTwlDDLr/tc=",7939916215079450341,5677890623493006219,-6034110018692425677,3313272275131093080>()) {
         case -861525348:
            return true;
         default:
            throw null;
      }
   }

   private int bF() {
      Inventory var1 = this.N.player.getInventory();
      int var2 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s3jau36ps07pev","vfEcLEgj3W8W1bGGBq1cRaEuoyLxK1FVU2eNixAiEk4=",7354745781405027528,-3467362326416357370,8174937324803815086,-6327120430323257385>()) {
         case -345873981:
            while (var2 < 9) {
               switch ((int)com.yiyiaddon.m.b.a<"stnos0fvorxt3","fgSHXYaQ/2fIRZ6igO3YlLdKNlVg0tjphgZvISoPzuM=",2772445368755551599,5943402787814469167,-2459265119395087986,-4328409254029401515>()) {
                  case -749163339:
                     if (var1.getItem(var2).isEmpty()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1yus9x6howjv4","KIoDGnapYdlpUHn8eCnI2gwVBaw8IJA0Kjy4IZYOKXw=",-6351136116502763014,1496247980822094659,3390667950440355502,5720286625702878079>()) {
                           case 1681124324:
                              return var2;
                           default:
                              throw null;
                        }
                     }

                     var2++;
                     switch ((int)com.yiyiaddon.m.b.a<"s3sjqegkag4bcu","Pw67nrmjhZLd+COLS7DBz14SqhI2ciFBUgtF0hvn5Kc=",6645998549702231721,-9173474092756209675,7364557028370602226,-4603264317564127292>()) {
                        case 1982333742:
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

   private int bG() {
      int var1 = this.bF();
      if (var1 != -1) {
         switch ((int)com.yiyiaddon.m.b.a<"s1eiyqwrm7jdsl","KsbPQSu/E2HP8m8YXR3Ty2HVU2FU+0H3iqbA1EfWJuw=",169061671681653818,-6933232497745817733,-2780340319613490458,-5579409955376788501>()) {
            case -567257983:
               return var1;
            default:
               throw null;
         }
      } else {
         Inventory var2 = this.N.player.getInventory();
         List var3 = this.h.a().bg;
         int var4 = -1;
         int var5 = -1;
         int var6 = Integer.MAX_VALUE;
         int var7 = -1;
         int var8 = Integer.MAX_VALUE;
         int var9 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s1c2j6puapkxjt","SxmDoqsn4kYoKMSpkULclZXDcya7spajU8EtCP7z/0Y=",-9135002014910022852,-8875490811181283425,5661960275715658164,1754264759272656549>()) {
            case 1609140393:
               while (var9 < 9) {
                  switch ((int)com.yiyiaddon.m.b.a<"s11go2izz2ubm5","EIiVu/rRxAeJVKhuIayj/8Hn3YsptF9o/5C2HSA1Ixg=",6384082620471714963,5025418231952406042,-4406236549715532245,9148615950160423256>()) {
                     case -1509088708:
                        ItemStack var10 = var2.getItem(var9);
                        if (var10.isEmpty()) {
                           label104:
                           switch ((int)com.yiyiaddon.m.b.a<"s8jmznbuv5zev","m71fOBxWswOCB7w7JAfPoeT5a4o/5FUIGZH5qRwiXGQ=",8182425413797112600,773068729457747314,5987444693233806517,9025079130517408363>()) {
                              case 694114384:
                                 switch ((int)com.yiyiaddon.m.b.a<"s2kvy7qyk59h34","YND3UFiX9DhwS+pKI2dccrixbRFbj4Ukpb2AR8jqGAk=",-2900878077207873048,4225264596370067139,9054742148868053415,-4721673451859622313>()) {
                                    case 1012791045:
                                       break label104;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else if (this.y(var10)) {
                           label100:
                           switch ((int)com.yiyiaddon.m.b.a<"s3t31lrnpyyy8l","S9PYDhyGPy7PEzRG8vb2ScNSPKrNJOx3SLSl+5X/bto=",-2819646325508950627,2763576830845543709,-6453571425919332659,8255740058402546499>()) {
                              case -455925035:
                                 switch ((int)com.yiyiaddon.m.b.a<"s1g176exg64pvi","5weUh5ufYQO7/06pgsWmB4/TaChJ7TVY7Y0Hi/nAf/U=",-8989403995542049229,1705449352569848809,-6150541279070141570,5940234108348638547>()) {
                                    case 2060901000:
                                       break label100;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else if (this.h.am().contains(BuiltInRegistries.ITEM.getKey(var10.getItem()).toString())) {
                           label96:
                           switch ((int)com.yiyiaddon.m.b.a<"s362x7tep1e87s","YjRbjSIhTmuIKrY1lc+r+/gwIFzTjke4lQ3uDPCFiWs=",-5435820656106805657,-2258459827842204036,2139647739172590956,-3925426354489260456>()) {
                              case 177103673:
                                 switch ((int)com.yiyiaddon.m.b.a<"s17ijgvdpizdfx","pN3m+/IhMu3OHewh7+4xs5um5mnNl6ylvg9rLQf1Gf0=",1383206975168228794,9181224136241657019,-7406355982756205348,-4469945877034458510>()) {
                                    case -677927850:
                                       break label96;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else if (this.z(var10)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2eu5chbjj0m52","h0BCgDXpIiPb6bGsCHCTVUxKE2ud+QJta7JWNGhqbZU=",1678087604772848701,-875444473183678935,-6845279754836626184,4319169014058710822>()) {
                              case -530149353:
                                 if (var10.getCount() < var6) {
                                    label92:
                                    switch ((int)com.yiyiaddon.m.b.a<"svkzu4oa7a3eo","CPVPqxKa1tfQQRYMz3FCyqzaR9nQ5KkDjlpvwArdaZY=",4945101093235014318,-5438378107675328980,6565034736148596203,-8620246695106765945>()) {
                                       case -1185719953:
                                          var6 = var10.getCount();
                                          var5 = var9;
                                          switch ((int)com.yiyiaddon.m.b.a<"s2uegvneuwvp3y","Nw3tH8+8pxymKaUeeU8CW9oCJHs2CJw1jMzJPipO0H4=",-3253267068641512391,4164351679996488643,9153480235157555719,-6268480565745168275>()) {
                                             case 1187017314:
                                                break label92;
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
                        } else if (this.c(var10, var3)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3rx6jyb5gzm91","FC3EouJmZpULsG5MlndEdBLEjPt+jKzInKVHrWVn49M=",-6938693912085549062,-7399644988303918598,590990117436006747,8186175418822339384>()) {
                              case -1668918798:
                                 if (var10.getCount() < var8) {
                                    label86:
                                    switch ((int)com.yiyiaddon.m.b.a<"s1h70cx170kh4z","EvTqGpKVcHzW0rVNaJmnRIGXGnI/U7JqiSTYz3Gvz3s=",-1992865615496331747,7005274610928274363,6738648214861346628,-7476322907976497340>()) {
                                       case 1540692930:
                                          var8 = var10.getCount();
                                          var7 = var9;
                                          switch ((int)com.yiyiaddon.m.b.a<"s2glpgxll8s15v","XqRDpvRtF1sW7/xbCGbsDL4vmjbNdpVeCHj/rqxCv8w=",-3406630557541830603,-5370442690845239381,5434175495043783132,922608340881331980>()) {
                                             case -692514281:
                                                break label86;
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
                        } else if (var4 == -1) {
                           label80:
                           switch ((int)com.yiyiaddon.m.b.a<"siwkfckqj7pir","+Qm0nrsck4tE6xuYxoRUugaA40vip4qxzI6Cqa5iK4o=",7428043217023943566,3537545705378259626,8175138872178087107,-8576808082924484702>()) {
                              case -390050698:
                                 var4 = var9;
                                 switch ((int)com.yiyiaddon.m.b.a<"s3rrueodyuz0dc","yBAr0zbkKyQSN1T4M/sDcZvtlDwC6zCNNEHfx/r+0Vo=",2545599429569931598,1735675688900669809,2193774289872715404,-6207102964190251878>()) {
                                    case -1413115583:
                                       break label80;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var9++;
                        switch ((int)com.yiyiaddon.m.b.a<"s2o8w0eg32cl0k","8sJETBolW4XilOw3Dm/s/FsPD+6scA2McDUYUyWJ6rc=",5307238867112260487,-9115963059787208337,-2205066208157853739,753608514037678275>()) {
                           case -477778195:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (var4 != -1) {
                  switch ((int)com.yiyiaddon.m.b.a<"sdgb0i6au0xt2","MgwDBIRy/DljELzMYq0bOuczhkKM2sBd7dcGz59F6mk=",7446071489651040036,4233697272562510337,-4886475294093720775,-1423643919493764274>()) {
                     case 749750746:
                        return var4;
                     default:
                        throw null;
                  }
               } else if (var5 != -1) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1erh0teezn96v","30B6QBHERaa6f20HTwFFEhT2X4lvz4dhwae/4UFxR6U=",-220632290899726401,1556348559917580237,-7009976119538065446,3389842350641631107>()) {
                     case -1151906726:
                        switch ((int)com.yiyiaddon.m.b.a<"s33at8l1t1zd6h","9vWQRt7120HWwczL9iiq99Gj33SQeGP5YyXnwv+4Vv8=",-1192314497214009919,-8208907160783239231,-1694414226971704532,-5319370950007514877>()) {
                           case 1433641843:
                              return var5;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s26xdhjn4wx1q8","gyuOrrtNR8vVynSaqQXgzs9ihoRkrTeIInaIF7ci4qY=",-7033972018563694542,6641197951842202102,-5984810524487077882,8758331965424058401>()) {
                     case -1458927139:
                        return var7;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }
   }

   public String bW() {
      if (this.N.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"szscnaqlzwg41","yxVxfefFRyLRx0IGDFNlhljwdsgs+HnkyNmhZMD5Fm8=",-5609726820696676523,5527047454723372018,-2400606279039514716,8571142764141322759>()) {
            case -570629758:
               return (String)com.yiyiaddon.m.b.a<"s18wxh6ments3v","ubvo9MPvlfpZ3HGuOlKiRqJ9Nlh1lYovlYPCb/rxcjo=",-4005091409739801479,3844619705939076771,-5581475197995632355,2524956931244114061>();
            default:
               throw null;
         }
      } else {
         Inventory var1 = this.N.player.getInventory();
         List var2 = this.h.am();
         ItemStack var3 = ItemStack.EMPTY;
         int var4 = 0;
         int var5 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s1lw1crozzziii","bkYHhHOIXxAQXYPuFGa0dyIDgAnPGdDL4WZFBHnpQPg=",9195232747673917505,-8926371638211977216,-6867152275805090522,-2567672066876562381>()) {
            case -819010341:
               while (var5 < 36) {
                  switch ((int)com.yiyiaddon.m.b.a<"s32kxtr65u5bwh","sWzLXDys+bcG3SUhxHnCfWDZrVWAYk1Axmg2ulU/bS4=",-3730811215657366787,1023501478145534615,8627256765581033542,-1746743288111735179>()) {
                     case 456183727:
                        ItemStack var6 = var1.getItem(var5);
                        if (!var6.isEmpty()) {
                           label60:
                           switch ((int)com.yiyiaddon.m.b.a<"s1rl1yo86w7m8x","kOomC2b3G2+ru4uVZ6SWieqaAUY1+oHs/yjO90H84bg=",8511326702969096175,8195320608395586261,-4562889738321458454,128409331043834139>()) {
                              case -1375100563:
                                 if (!var2.contains(BuiltInRegistries.ITEM.getKey(var6.getItem()).toString())) {
                                    switch ((int)com.yiyiaddon.m.b.a<"svkbhcthi9nwx","PvX7O81RxW1yswax5SNbzT3B5/MsE/wpxSzQpPzYgwY=",-3253613017533272371,9069920865016151663,6917290628912500336,-1670565840894027366>()) {
                                       case -1388110548:
                                          switch ((int)com.yiyiaddon.m.b.a<"s39a4qwiivutfh","cKrrv5ckrSWS3e8RhTKcnhuKF/PIa8owOYKyAgHgAeo=",243410647300285997,4337953327353705627,-8659860839420672708,-1102651147189282401>()) {
                                             case 1605420908:
                                                break label60;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    FoodProperties var7 = var6.get(DataComponents.FOOD);
                                    if (var7 == null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3fotocb9obr71","8w3NTS08jDuM/T9FdPEIRW8JHB3/CzlkQpZpnSKdENI=",8294854153575300077,-3111977141564044148,6289950464657040519,7584850082214752291>()) {
                                          case -1976190661:
                                             switch ((int)com.yiyiaddon.m.b.a<"s5n7mnzcksxn9","wXpb2trtHH4rvsBwN/3XBlTdXps2MJB5M4GJWtIR6G8=",-5101549862050833632,-5514942388363528249,-7460611807866250682,779785438018567627>()) {
                                                case -344105505:
                                                   break label60;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       if (var5 == var1.getSelectedSlot()) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2t1ie1q8knubv","9QtfXk6Qa4qSaHBxDsGgWO1EHJMCFoT+pdYGNXHNb18=",-1244429546063688020,3329305697420439380,-2675332589713504867,-2509791006210827614>()) {
                                             case 1597927934:
                                                return var6.getHoverName().getString();
                                             default:
                                                throw null;
                                          }
                                       }

                                       if (var7.nutrition() > var4) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3pz9eyl0kot43","ihRvQOtBqEPhTmd7mwzF0+mmP1ePIMDzDM3kkf0JfKI=",8119397095115734683,716415415768701790,-7527608121048307096,3717391992332457808>()) {
                                             case 1525676766:
                                                var4 = var7.nutrition();
                                                var3 = var6;
                                                switch ((int)com.yiyiaddon.m.b.a<"s2udruqvgzqvmf","L0qKFUsa8v/y6H17Va4XSQH41h2efvN58fKIwIkqxLk=",-6231932749946932956,3633345048104251134,5620467070849787138,-20286958060877770>()) {
                                                   case -1579820757:
                                                      break label60;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }
                                       break;
                                    }
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var5++;
                        switch ((int)com.yiyiaddon.m.b.a<"s22lpavfmust9j","3MLDtgyrXl31iDzdXMI/2G6OonVdY793BwcBGuZb2QQ=",-1969994592805604398,-2484251550663848767,3742748068945981728,129091594166107184>()) {
                           case -6363075:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (var3.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3s5bvt7awgc7q","EVCaZt7u/HE+PmA4m4j5FbHAfjRXc/pAiZrzzHUMwBc=",-2250356116248696769,5835890743140161169,5636100461748787370,-3429903940565217053>()) {
                     case -156993785:
                        String var10000 = (String)com.yiyiaddon.m.b.a<"s18wxh6ments3v","ubvo9MPvlfpZ3HGuOlKiRqJ9Nlh1lYovlYPCb/rxcjo=",-4005091409739801479,3844619705939076771,-5581475197995632355,2524956931244114061>();
                        switch ((int)com.yiyiaddon.m.b.a<"s1d9j00ekxvply","YUORb3IkJ0W0SVZUcW4W3cuNu5BSQXFPXyTFyS+diY4=",686559674077157943,450619153755176711,1656847874631315102,-6376081196014422514>()) {
                           case 368980128:
                              return var10000;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  String var8 = var3.getHoverName().getString();
                  switch ((int)com.yiyiaddon.m.b.a<"s3l8ozgpgqnvx7","3mOdmFnj4MM9TlJrV7g9SqRo3LLY0MkJM+5SyYeWNA8=",-3673297182493356745,8082059448093930908,-7771128829240093451,-3501144300518622814>()) {
                     case -1099147178:
                        return var8;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }
   }

   public void fv() {
      int var1 = this.jD;
      Item var2 = this.h;
      if (var1 >= 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s331206c1ynix","3QrV2HXdBpAFEY1jqeRUoxqIq00qZHqWbXMANdicNOo=",5425952564440511615,-4105243015859349210,-6412117885186980620,43733348708193490>()) {
            case 1299874029:
               if (var2 != null) {
                  label79:
                  switch ((int)com.yiyiaddon.m.b.a<"s1wn0v31vssph2","8RPj2eQ1J4R0m+OIn8uLJB2utBlX93ehfVbQVZi9iPw=",508396014666626324,3430829014251909462,-4297828544924083567,-751377723378657316>()) {
                     case -1871234279:
                        if (this.N.player != null) {
                           if (this.cu()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2o6fweujglie1","LSiEZsTco55H4oPyZvivsqEIJynOciWt0g5RfbH+Y0o=",-8239129267177035479,7899375405653008117,7593900551450619564,-261541387719805581>()) {
                                 case 2010473212:
                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           Inventory var3;
                           boolean var10000;
                           label88: {
                              this.jD = -1;
                              this.h = null;
                              var3 = this.N.player.getInventory();
                              ItemStack var4 = var3.getItem(var1);
                              if (!var4.isEmpty()) {
                                 label74:
                                 switch ((int)com.yiyiaddon.m.b.a<"s3uuk77okp2asv","73OLJWp0Rw8Ya20C6fUXeLol/aNxN0ToJkMiyCwrZVA=",-6091394777070591813,-1550898904649273352,3057372751699337488,3200942562518703101>()) {
                                    case -299585420:
                                       if (!this.h.am().contains(BuiltInRegistries.ITEM.getKey(var4.getItem()).toString())) {
                                          var10000 = false;
                                          switch ((int)com.yiyiaddon.m.b.a<"s27x2hnnofcwlg","lE8X3vnu/o3BL4V7+cDxxDODKZfjPh+nw4AuteXVtRo=",8425967191159552565,-1645920620329854513,-8328839426403249096,2392429573387680610>()) {
                                             case 944716080:
                                                break label88;
                                             default:
                                                throw null;
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s2sy7zbz4lxbif","qwxS512Dp7T45m/6lov+g2/I2jcTYIW6rs8vOxquHV0=",6085308252768550802,-8007181872601552471,9135152188745918017,84439427942770628>()) {
                                          case -1399233262:
                                             break label74;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var10000 = true;
                              switch ((int)com.yiyiaddon.m.b.a<"s26ogo66hr5ewg","C0VnUExlCAytzPnk0Jp3oDlPyIIsFdVE+bgxxBO57FY=",-8425493877604273899,7351684324534124198,-3907005301984333987,-3770328872468455984>()) {
                                 case -564097523:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           boolean var5 = var10000;
                           if (!var5) {
                              switch ((int)com.yiyiaddon.m.b.a<"s97zclfg1u26y","MjlfMNR370ZRMq6ByMa0ZEq0yKI/yyIX/Q5GlflvLzs=",6328015565307983930,9222668208540758414,4830883640435833315,-8872338310376860091>()) {
                                 case 2020233993:
                                    return;
                                 default:
                                    throw null;
                              }
                           }

                           int var6 = 9;
                           switch ((int)com.yiyiaddon.m.b.a<"s27sox6f4w2acs","f+X23o/+UTdmyjlqadEdbF6QVD0Eb/bvcLYay5O1uMU=",-1202181964152272042,8651865378693316183,-4070329819770208538,917834281644291323>()) {
                              case -312515599:
                                 while (var6 < 36) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2pdtun9qx8pn4","geSUniTP5PfM7S+M/7CUjy1qMyWuUkCAX4fkKrb3/Eo=",-8494365508647048159,40747910043714273,-3584510681690398357,-1001174606313404349>()) {
                                       case -915261999:
                                          ItemStack var7 = var3.getItem(var6);
                                          if (!var7.isEmpty()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2mkr4qbdzzbfo","lLsSWcKs8jyVax2vFvdp0XWkNOqhSjenOegrqJ7qiCU=",3316459960340461642,1080238128321038541,8633104035062241450,8610559130164458526>()) {
                                                case -505429875:
                                                   if (var7.getItem() == var2) {
                                                      switch ((int)com.yiyiaddon.m.b.a<"s3s44dz106viz6","cFuvoDs7xIF33cj4n+VHux5lhvcXcywML9RVjQ3uykQ=",6452994487634281950,-656857349886501515,-225631787942565552,-7200695331679785552>()) {
                                                         case 2023463964:
                                                            this.a(var6, var1);
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

                                          var6++;
                                          switch ((int)com.yiyiaddon.m.b.a<"s1uzmyhbtvobbu","r6Gs9HzNISFWdXCdFr/nHw3R/jVtV8gjmZR9n3NYvQk=",-7264814935910054475,1580089085703596518,-2232826711486590833,-8011483753399554435>()) {
                                             case -1316125063:
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

                        switch ((int)com.yiyiaddon.m.b.a<"soiuhfku430y8","l8gK48n3OPk8GEeqF6zvp75+ixbFVtAZy2tCNgPhcek=",-2626112301301695780,-5729793370513998616,-3230226979679697420,-458337203204604006>()) {
                           case 1666927866:
                              break label79;
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

      this.jD = -1;
      this.h = null;
   }

   public boolean cv() {
      if (this.N.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s119x9xiol55ue","3snGGMQZe2mAq56FHFQReKTH4vL1NFvqx9YZrpLw/9U=",7180570497195907374,-8698237905463527000,6016691853582467017,-2202902028204371804>()) {
            case 555198792:
               return false;
            default:
               throw null;
         }
      } else {
         Inventory var1 = this.N.player.getInventory();
         boolean var2 = false;
         String[] var3 = new String[]{
            (String)com.yiyiaddon.m.b.a<"s14iz4gr8xw67k","Mw8UdvEPxgRdsm2qD+YbTzJCbnHU/aAWRVs+Car8gsRVOQmehTywV5pcNac=",7736509894125894771,-7921291141572943154,-7520384917658901986,7279404331857450573>(),
            (String)com.yiyiaddon.m.b.a<"s359cfjmqwx0ck","clhQLA+4+Plo2zQAf7s/Kvt5/4xGjXYHjxL7kiyxgtV9qF9OQCOMynN0",7327554732747018016,-5648200480830776582,-8819170673897228635,-1422320146534745630>(),
            (String)com.yiyiaddon.m.b.a<"s2czqyf5c5xhbx","dxyGzzI7kLD0E6nshx7J46e5Z3YX74KEdgfeJ5G8jOOqekpU",-4078964317941833068,-6311487286667985974,3875360580732846839,-5664680712277185250>(),
            (String)com.yiyiaddon.m.b.a<"s3foq5bpyx7zti","u+SLBZoasrczIos7Gnzec41fi6BNTWKkBNj+MtWhjMvlioyi",-2672038852580466811,-4144233495883988395,5983820290855826603,1924454690378326163>(),
            (String)com.yiyiaddon.m.b.a<"s16085svlrbt7","vA3zn4O0dK2dpKWnJpJ73+2gGIPcN/qorjMj1mYgLGMBhGK7Urb8rg==",-6861874265602086391,-8810669194668653876,-2783900998450454249,4453708398581716381>()
         };
         int var4 = var3.length;
         int var5 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s12ey3kwnkkcad","sX6Cr0fCwfNlwoerXYSJcZZqxhlAzRbmnuYTNHRdODg=",-6316198349980614846,6447436141191375808,-1333138311729963304,-3444382623375344500>()) {
            case -901412906:
               while (var5 < var4) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1juswytgvn7mu","LFnJPBq2j0DOEaAtYLaxihP284xFHoZabfAKktd8H8A=",7365044461360233787,-1383709524071264936,2823060435454259977,8715445228699016950>()) {
                     case -254484911:
                        String var6 = var3[var5];
                        boolean var7 = false;
                        int var8 = 0;
                        switch ((int)com.yiyiaddon.m.b.a<"s1v39grgqvdbv6","fOWKnW2P1liPjdjuG1etbPAs5f4pOlYonsQ5kysPgLY=",-927164218102752513,2692248133299797368,-4229690555765570932,8899751639138950830>()) {
                           case -872014952:
                              label124:
                              while (var8 < 9) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3l8g8lj4ubtj1","G38gI99JYAlwAd5+V9S1ihekgARItzNsyX7iw81AecM=",-5465880334019065830,-7310300285429948217,-4977153581266714609,1268633381035466827>()) {
                                    case 113979449:
                                       if (this.b(var1.getItem(var8), var6)) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1qesuqyf5vx7y","p2n/jOjSlVGfjgJrk7LFFbFLM3qDhNBYjLnRKx6DjIM=",700988894624619795,-1198333070370041368,-7055078753046816222,-6642950012229093847>()) {
                                             case -925911571:
                                                var7 = true;
                                                switch ((int)com.yiyiaddon.m.b.a<"s14aknhq8t7knr","xkUBoTKDPD+MHyMam8agiF2TPWVnIgCfH4SAzSbbu5o=",6117058994563512513,-7803587305237910776,-9195597788014826642,403431998251089350>()) {
                                                   case 2115435816:
                                                      break label124;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       var8++;
                                       switch ((int)com.yiyiaddon.m.b.a<"svo53aydvb3b0","TuYkx49yAtxq64Sr/Z2MHv8FZnxp9gXOnKdMAm7J/JA=",-2866267842116581524,8199423384294538093,-2817200496969510260,5551975642516884989>()) {
                                          case 1224999064:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              if (var7) {
                                 label92:
                                 switch ((int)com.yiyiaddon.m.b.a<"s191yn409v0nhb","ziDeS3teNhNwwmiH2fubYQJtIcucCY1f20Qo7hmcEdg=",-5114890298122767665,2839519158853549756,-928021382956959463,7337451223799591010>()) {
                                    case -1440422548:
                                       switch ((int)com.yiyiaddon.m.b.a<"s2cbwa5au2q8js","5ys7e5xbUD8jNQeCj0zs2ZdZsQFa4C6jj7104vdeoFI=",-1567707952022524803,-612277904767895856,-3440133494853710936,2686636734917195263>()) {
                                          case 280636319:
                                             break label92;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 var8 = -1;
                                 int var9 = 9;
                                 label69:
                                 switch ((int)com.yiyiaddon.m.b.a<"s2kuoebihwrihd","U6e2w2vgbMR2dYygVvHgw6RKki0AmpWT1J8zBakUOpo=",5006613157435751584,-3074875489501106467,3496716430308541269,8469461084884233744>()) {
                                    case -2113087971:
                                       label136:
                                       while (var9 < 36) {
                                          switch ((int)com.yiyiaddon.m.b.a<"sqrvcqmqfk2ba","WVR9YbBM3mvMTcPqa9HKqqsnjqnP/JMjn7TnlKHftXQ=",-9083399812932689805,-524162972398838193,7184477007541763276,-3609568399872504096>()) {
                                             case -454537241:
                                                if (this.b(var1.getItem(var9), var6)) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s11fcb63wuclvy","adOBSdU8sGgCTNhljEvb4SnGjeLNEShxOkgBRh4TNa4=",314156262000338911,-1912588742302342173,-3218132139000902611,-1002731820334626328>()) {
                                                      case -638461931:
                                                         var8 = var9;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2akszvgdhvnyi","I2TPV8dgRy6iu9Z0jOydTKEeZbyj/zxMob8eGdtOmZI=",-1943791846284684721,-7158401227927664496,8270523503251319411,7345425624026157748>()) {
                                                            case 481141683:
                                                               break label136;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                var9++;
                                                switch ((int)com.yiyiaddon.m.b.a<"s8cgzsffwyf20","IwmLvjmd3vPEsmdOAFqy77ov2wMT0oxWQlU2oCONlq0=",6469707751661667341,2088208395806120084,-1400226428308718187,-4616539144265352942>()) {
                                                   case 1029901976:
                                                      continue;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       if (var8 == -1) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s1auk8dpnvun2n","EcfZzwPgM3VwctxgIxYDaSMjWOgtf7jtk3h7fDtgfsY=",-563970704337281437,7791955262810887492,-5609102185657536911,-2529418389783229923>()) {
                                             case -1750734158:
                                                switch ((int)com.yiyiaddon.m.b.a<"s1zutnpmnqwc5s","eJtbLQRBCEBUETCyKyBUU2a+DnCCXCrvnzEmU3VTtcM=",-7556591145738335786,-272263401880916451,-5491038924967851544,-7391940900568344800>()) {
                                                   case -1121439607:
                                                      break label69;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       } else {
                                          if (this.cu()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s3abisjemyzbea","Jjw1HYSUQqtfyxoJSYD6QKRdwfPbL/6TdCS52TfGlfs=",5789763750677751079,-9107557533029831282,-2563665428400388762,3414358109149381786>()) {
                                                case 6219725:
                                                   return var2;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          var9 = this.bG();
                                          if (var9 == -1) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s32ygu1gggvixo","7cgHJSndxcQMXxpsqzjiKaNJHw21gtoGrxBBfouavHs=",-7456730317796257085,-8047618542724061093,8602687010855431710,1815924358860575465>()) {
                                                case -532528746:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1ir1y3ulubeja","z6AuPjTjm594UoVdky+MMmv28skWd/LufkjaN4GlXto=",8769043680834691498,-183199431859281542,5060512853609403055,8603618406869671427>()) {
                                                      case 250860537:
                                                         break label69;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             this.a(var8, var9);
                                             var2 = true;
                                             switch ((int)com.yiyiaddon.m.b.a<"s2f0tjh06w6j6d","8V3KPeg6KbLtB5pu46xX79Zz44ojdv/7wJOBWcDRxgA=",-3894285469541040614,-5443165944786772875,7994759137582084042,7662049606676405665>()) {
                                                case -1018671158:
                                                   break label69;
                                                default:
                                                   throw null;
                                             }
                                          }
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var5++;
                              switch ((int)com.yiyiaddon.m.b.a<"s136fx4rvathpl","b1f67m2aEf3hAHmAbCa/O9EOvXOl/NSVsfk9cxTwYxc=",7697668530427491662,493908201074942804,78690332667775275,4481644057311608506>()) {
                                 case -1759853244:
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

               return var2;
            default:
               throw null;
         }
      }
   }

   private boolean b(ItemStack var1, String var2) {
      if (!var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s35vcvruvv9egt","CHUwnfdui/v69kyL/8sTrZmi4c0C7ltwTMSPm11ihjI=",5085320077748538546,2901469360808004346,8839597365248561243,-61797745006667143>()) {
            case 1601542654:
               if (BuiltInRegistries.ITEM.getKey(var1.getItem()).getPath().endsWith(var2)) {
                  switch ((int)com.yiyiaddon.m.b.a<"sxm19e83x0gfh","wWDP1+eytj5Yyg+kmhaef4BSd8YDMSaKSBzWxiuqTbs=",4490567743923049509,-1980162580256734910,6654170688533988705,636767701085699313>()) {
                     case 805783378:
                        switch ((int)com.yiyiaddon.m.b.a<"s30uf6l06rf85t","rgA9kdyk/GMXen3oR3VX3jiumRqK5Fw0Rwvm0xMF0BY=",5162300354645386582,5285077053859094037,-4335714879160892088,-6726636223594829719>()) {
                           case -1571138717:
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

      switch ((int)com.yiyiaddon.m.b.a<"sccdjnjj985y4","DQ3bFcdjr1f41SCBoF3D3OO3Cb20Q2wOY1YcGGEOJJk=",-5254686198616946345,8358565933873591713,-4652994638537677205,6476575668203946487>()) {
         case 2026873196:
            return false;
         default:
            throw null;
      }
   }

   public enum a {
      WORKING,
      DONE,
      INVENTORY_BLOCKED;
   }
}
