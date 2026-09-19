package com.yiyiaddon.d.c;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundBlockChangedAckPacket;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;

public final class a {
   private static final Map<String, Set<BlockPos>> g = new ConcurrentHashMap<>();
   private static final AtomicReference<Set<BlockPos>> a = new AtomicReference<>(Set.of());

   private a() {
   }

   public static void a(String var0, BlockPos var1) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2rhdxyc3303sb","0F3aK6egrjgUJPBdsE4fPWq6Eo4PT0F/OysrHfCIjRE=",-7700850958017787752,6227754140287993678,-743262800905355167,-1852989788333530199>()) {
            case 1079657754:
               if (!var0.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3jpstevx224jj","Xpl8hqyUFMH/qvzOspv0oGUDWhA3wD6eGzgHgoQnDfQ=",6867040014946127009,-4288622464345544700,3850087064928807198,4800913635899665843>()) {
                     case 487306369:
                        if (var1 != null) {
                           g.computeIfAbsent(var0, var0x -> ConcurrentHashMap.newKeySet()).add(var1.immutable());
                           u();
                           return;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s3mvpf7qrs0aqz","zX63sFKfm1DOswnP0kM/dddynG5cWVbPCKAnQqn/NJM=",-7951787707454397593,-793490611731588529,3002158942356951794,7588714675571292687>()) {
                           case 1863488400:
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

   public static void b(String var0, BlockPos var1) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2ppxw9wt8ta0i","bmNSHGReZz+4hC17E59o/f+mVQlT0XVXok4CiFxwIMg=",3568264742377073038,1580474058503491980,308714116797967586,2333570607268106459>()) {
            case 1389932811:
               if (var1 != null) {
                  Set var2 = g.get(var0);
                  if (var2 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3mbha67jmgtf3","VCJYbwp5bVg6diYa3KqAQwTXxehc077aQlJnMvX7YjI=",-74900075189855181,8621048418818853868,8064147978345947200,-8105853851676442805>()) {
                        case -1534314547:
                           if (var2.remove(var1)) {
                              if (var2.isEmpty()) {
                                 label27:
                                 switch ((int)com.yiyiaddon.m.b.a<"s2x9ur4t6m7ps4","O3rJ9pMpztEGwtJUTKkrzeYkL0TJppT9ATB2fjQjm4U=",-7264553804641102098,4715014655984474198,-2298240860927300205,5961692007678051085>()) {
                                    case 1114229233:
                                       g.remove(var0);
                                       switch ((int)com.yiyiaddon.m.b.a<"sx94rm7d10yrl","RujcTIk3YPTDhimFVrYZ9oFZVpL7II0q8TgN+xmuLS8=",2816289414072061914,-6398228000059108474,1067839854777004067,887534333481402448>()) {
                                          case -1859911924:
                                             break label27;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              u();
                              return;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"sjugba6risk9p","eLYQdcou4QcjrF9vNDDApceJwSCkO4H2j/dS9sJtOLo=",7734389967848893600,-2898162098060559562,831221822478245540,-3885899627004808759>()) {
                              case 593720098:
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
                  switch ((int)com.yiyiaddon.m.b.a<"s84s2ex5vg679","plN1WtmZ/yORXvTRcXb3Z/tTrcU9MxKvM3TVHLEmSGo=",-3898961712760446975,7499273826212611637,8407767788247531614,-8772170903612542206>()) {
                     case -1829871374:
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

   public static void k(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1otofm6bnj038","nc2s3HNEalArZ0NkMJbbV4RPPJ5LWeqDpitI4JHjgMA=",7878105894390031721,3204572248436480663,-8699966302261791306,5136738085935722972>()) {
            case 696010049:
               if (g.remove(var0) != null) {
                  u();
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"stii1nfb5hqvh","jpAQmx8cFi291JtjEJSpDfUwtbenC6RXQRrqdIbUufI=",3998999488203543284,3882827853024645648,-8368960563605699628,8600924422417560878>()) {
                     case 152407145:
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

   public static void t() {
      if (g.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ewe0wh8l572w","rvyvMUQ5J29dNcHLoP/f1zFiuCwI8USf2IqhW6C2gfQ=",2781287825629062190,-2352036133953123331,-7930697981563613087,4766957410683442170>()) {
            case -2075889736:
               return;
            default:
               throw null;
         }
      } else {
         g.clear();
         u();
      }
   }

   public static int j() {
      return a.get().size();
   }

   public static com.yiyiaddon.d.a.f a(Packet<?> var0) {
      if (var0 instanceof ClientboundBlockChangedAckPacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s15ftqrbzksjz2","yJEnGUQScVWo9sFREbEJwC8I95U6u177sP6Wqtj1KNE=",3621275333875763273,2362838999840038301,2773159918897213934,6814832997490362154>()) {
            case 579351796:
               ClientboundBlockChangedAckPacket var5 = (ClientboundBlockChangedAckPacket)var0;
               return com.yiyiaddon.d.a.f.a(var5.sequence());
            default:
               throw null;
         }
      } else if (var0 instanceof ClientboundBlockUpdatePacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s12d86npcwhqg4","TqaZRPFrZFUBQuXx/sZLadN1Cfpt0noXRMUQ4jcKIyQ=",-3881840855060029927,-2976276381066103674,-4378311745987778540,6041139484559137958>()) {
            case -1552958123:
               ClientboundBlockUpdatePacket var4 = (ClientboundBlockUpdatePacket)var0;
               BlockPos var6 = var4.getPos();
               if (!a(var6)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s34kgc2zzq47h3","Cg11qXWNmHPGgYhNVVAiGDO4OCBrifyNF1rYANBgOvs=",2160019805026372211,7287454689187462104,7633469428310668110,-8543905639772774336>()) {
                     case -2013850394:
                        return null;
                     default:
                        throw null;
                  }
               }

               return com.yiyiaddon.d.a.f.a(var6.immutable(), var4.getBlockState(), true);
            default:
               throw null;
         }
      } else if (var0 instanceof ClientboundSectionBlocksUpdatePacket) {
         switch ((int)com.yiyiaddon.m.b.a<"s2hj34nbjgs8aq","Ah1HSnhQYeQ9TMvdMD/QqMYkGmGq6UgH1ipV2linjiE=",-8416671546096138097,-6986795332458501567,2556419465670796860,-4571165210324252032>()) {
            case 1550366309:
               ClientboundSectionBlocksUpdatePacket var1 = (ClientboundSectionBlocksUpdatePacket)var0;
               Set var2 = a.get();
               if (var2.isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"su1q51xxeyt1d","hYHn8wVSG1N/iX3qzvDiLtTkrKP33q7gMg6qb10iNuk=",-9193831927073938998,3062735543741022124,-147704182202637724,3457406316940884501>()) {
                     case -1433556334:
                        return null;
                     default:
                        throw null;
                  }
               }

               AtomicReference var3 = new AtomicReference();
               var1.runUpdates(
                  (var2x, var3x) -> {
                     if (var3.get() != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"sgadxw6d5n9c2","4UnAuqgWdT5dCixV9dQuUyvWNdxcNq0uF8k4XD9Srlw=",-8302083489053848934,7979234754919483702,8759186000891248826,721935998084342169>()) {
                           case -1844117743:
                              return;
                           default:
                              throw null;
                        }
                     } else if (var2.contains(var2x)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s24ufyhklithkp","j1EJ6fgyJ3GMGEUOc4+g53ClFTkzQbrkfqyJfeKLNnY=",-4910030100472680436,5100813496028475295,-3140054836354038905,7931070822652321403>()) {
                           case -263241961:
                              var3.set(com.yiyiaddon.d.a.f.a(var2x.immutable(), var3x, false));
                              switch ((int)com.yiyiaddon.m.b.a<"s1copumd0q53ac","Fr2RHM7qJsa4V/oOq2OaJJOmsFp6itjFO0+p07rsFQ4=",-2964858787836426537,3437323325616764469,6534992282649686614,3609619183357446550>()) {
                                 case -592774146:
                                    return;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }
                  }
               );
               return (com.yiyiaddon.d.a.f)var3.get();
            default:
               throw null;
         }
      } else {
         return null;
      }
   }

   private static boolean a(BlockPos var0) {
      Set var1 = a.get();
      if (!var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s27sy06vz1a55x","G26Xm9c+urMMx2jeTlKxDeXPMDNQwgHreRFxnU5Wg5g=",-4712460386218167543,-421289996490276403,-2503634975586239802,-5954196046910189865>()) {
            case 195387868:
               if (var0 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s22wtevnerfgfz","8+kzRhheyz0HqIQoVoMSnW250cH1LQWNS6p2qdfXs/Y=",-4319560348600703019,-292731947245210708,5070761003766863220,-2267792656534224996>()) {
                     case 2116527792:
                        if (var1.contains(var0)) {
                           switch ((int)com.yiyiaddon.m.b.a<"slo04pywmqmps","mkAqAIFTTjoY9pvCa54uq7TTYVPoP1yFFNor8Am+JB8=",2540635764772739366,-8055887395515150938,-6545480447795465983,345090711859403159>()) {
                              case -859785574:
                                 switch ((int)com.yiyiaddon.m.b.a<"s1eg6xkc1tiryv","CxxLw7eGFHpahuwCvxBiCa3ko3sqerBGfth7iFZStqs=",1626949386031257271,4014766196953331709,-835602931973071275,-3312777220614334145>()) {
                                    case -1531369601:
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

      switch ((int)com.yiyiaddon.m.b.a<"s3lqdphuhfox11","L1W14TIWDKjBlKaCsgjibmhdU+jLnLiuDi78NtH88BE=",-3946228042561923249,4470927512587836885,7929869981514368664,-1639446240234426409>()) {
         case 1946826740:
            return false;
         default:
            throw null;
      }
   }

   private static void u() {
      KeySetView var0 = ConcurrentHashMap.newKeySet();
      Iterator var1 = g.values().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s19sxiy3mkpdz5","1HlKthDV9hERA96XtTNXzzcmEIeVcWKF4MZsSZof8Jk=",864614282038994919,-3460813458206605568,8039072548125561375,-3283466373784099018>()) {
         case -1717568133:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s3g5yjtthdo4k8","JsrNTgCI11ALAtKIZ9Dj5WL7zA/Sxse9NxCxREGMQm4=",6913955000766559638,-6583411220633081857,-7603742013689636369,5654568435196374471>()) {
                  case -1088774401:
                     Set var2 = (Set)var1.next();
                     var0.addAll(var2);
                     switch ((int)com.yiyiaddon.m.b.a<"s2yuhzxo0tyb8h","He+vdPqNpMBQMN6nvOPqL9xVjwqiYnfpbVoiRFgxipM=",4254092912918196546,-5642294111380191258,-2961837835256795609,-784445761143128763>()) {
                        case -1774736661:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            a.set(Set.copyOf(var0));
            return;
         default:
            throw null;
      }
   }
}
