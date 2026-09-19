package com.yiyiaddon.e.n.b;

import com.yiyiaddon.e.n.i.h;
import com.yiyiaddon.e.n.i.k;
import com.yiyiaddon.e.n.i.l;
import com.yiyiaddon.e.n.i.p;
import com.yiyiaddon.e.n.i.q;
import com.yiyiaddon.e.n.i.s;
import com.yiyiaddon.e.n.j.f;
import com.yiyiaddon.e.n.j.g;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Display.BlockDisplay;
import net.minecraft.world.entity.Display.ItemDisplay;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;

public final class b {
   private final p b;
   private final com.yiyiaddon.e.n.p.c b;
   private final com.yiyiaddon.e.n.e.b b;
   private final com.yiyiaddon.e.n.c.a b;

   public b(p var1, com.yiyiaddon.e.n.p.c var2, com.yiyiaddon.e.n.e.b var3, com.yiyiaddon.e.n.c.a var4) {
      this.b = var1;
      this.b = var2;
      this.b = var3;
      this.b = var4;
   }

   public String aw(String var1) {
      return a(this.b, var1);
   }

   public static String a(p var0, String var1) {
      com.yiyiaddon.e.n.i.a var2 = var0.a(var1);
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s32p8w271xd171","9Ln1dco57Y05IXR9LAkotaPxdhC2ZMDyUYbDQQ0m4mI=",-6219407785794274587,8616940443038962162,1356246985268356875,-5694013078333882315>()) {
            case 1019300652:
               String var10000 = var1 + "";
               switch ((int)com.yiyiaddon.m.b.a<"s3lnc6fy0ho1kb","E0KNWulC6wIZURuL2GXXCnqhQ4+ufIJNKNgN93TxcbY=",-5212321454629741069,3002191960318210469,-8686706714578356039,-3034970617776316134>()) {
                  case 1816776053:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var3 = var2.dA();
         switch ((int)com.yiyiaddon.m.b.a<"s2xvxzdjl1dr4o","XDkI/ITn6s+TurkvNmAYvR+I1SEghHZWoFbLOgUMNZY=",-8697500259068142442,6889344858088109315,-6431665594585768774,1642235147767141474>()) {
            case -250438200:
               return var3;
            default:
               throw null;
         }
      }
   }

   public boolean S(String var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"so1w9jself90u","0HANznbpufiPI1M19CkR/A1dw4jZgCOrKwz/XnyhwnA=",1959790763812060667,-6183195189471811681,3894062428868894693,6073118027398883754>()) {
            case -1974176361:
               if (this.b.a(var1) != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s82qopz6c0lye","heTMdidPGLwZ7tET8mfgMpAJmMfJpK5NPraukrytDFo=",-2007300111134254627,-841484508934202520,9166975077973970563,-145132000296867639>()) {
                     case 1885966142:
                        switch ((int)com.yiyiaddon.m.b.a<"s27acnvuw2i29s","bOrY7NfQks4CgTXDh9xd5O6YvuN9Ss3jYFargo4dWA8=",3285669930248486641,-8062941653621727056,-1812824868473097035,-224352608001146261>()) {
                           case -516099200:
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

      switch ((int)com.yiyiaddon.m.b.a<"s378hxb1u76lv8","DtndqpVoNEgYH/1hkcRbPbUNHh2d+FPzIasEqrF89TY=",2547520254513603735,7292398716682704378,-1416486576790786480,5716222547613077451>()) {
         case 52821871:
            return false;
         default:
            throw null;
      }
   }

   public String ax(String var1) {
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1bzlxv4wst8tx","lkPvxQ1xPRop/VuRu7BGsvjn1kcLm+2jZApiW1QEv/A=",1900898322230499355,5816586436537921949,-8773787890596358083,-8874850123844110361>()) {
            case 958977120:
               return null;
            default:
               throw null;
         }
      } else {
         String var2 = ay(var1.trim());
         if (var2.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2h37nv0a8zir6","LNVCLCzDCEWiXme0WPkTwNj4kWPR9uzkmzEzQdXvuc4=",2526982868714953421,4136799459706343962,9025275483795703057,-1788331024151480865>()) {
               case 1541227672:
                  return null;
               default:
                  throw null;
            }
         } else {
            com.yiyiaddon.e.n.i.a var3 = this.b.a(var2);
            if (var3 != null) {
               switch ((int)com.yiyiaddon.m.b.a<"sb7peychqn151","/NZH/gnEJcBibWZMU3TJe+N/iy2vusRA8lWYfzsBlmw=",-1623311857254839716,-374345858128145399,-1308089882870956414,879330633259765548>()) {
                  case -1275821686:
                     return var3.dk();
                  default:
                     throw null;
               }
            } else {
               com.yiyiaddon.e.n.i.a var4 = null;
               Iterator var5 = this.b.aV().iterator();
               switch ((int)com.yiyiaddon.m.b.a<"s1wmw466qcuins","rv4sxcl9MRFDT8Gczl+5EYlcF2p8fRFO4ywI4wBtq/w=",-8470996758854775311,440045215611633163,-7531700965123174462,2130902596641028776>()) {
                  case -1933345473:
                     while (var5.hasNext()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1b3sf7215usak","44DcsUodUVixgmC0/hcGmkwJl0vRZIzhRUjgb/JlDBs=",5018201301028615900,-1218545639054861564,-4597461529947946382,582801473284438664>()) {
                           case 2111781982:
                              com.yiyiaddon.e.n.i.a var6 = (com.yiyiaddon.e.n.i.a)var5.next();
                              if (!var2.equals(var6.dA())) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2wc6eucxoza5t","HtQYdNe1Wa3H3tybMxOg6i1yirop3xrI6bgLzD8BrGE=",-7594669987100843808,-5320031033973973893,134152531556938696,3795718367709049390>()) {
                                    case -1567439792:
                                       switch ((int)com.yiyiaddon.m.b.a<"suy5n6iyefr6l","USGxOYO/BtImmHP1usyMezmM28nS/OxxBcNkgX5HKeA=",-1186971983381734451,2544328670284829167,326315138731302389,-2122327464111796723>()) {
                                          case 175487151:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 if (var4 != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"sdgbynx8fwjxo","xBjEES6CSNoTuT7/uGlXBx+Z3cvZKmE0DeTGL1QgSdc=",-8301913819125251471,316479979635405704,4853424708572812185,-2315579046449055819>()) {
                                       case -1346620822:
                                          return null;
                                       default:
                                          throw null;
                                    }
                                 }

                                 var4 = var6;
                                 switch ((int)com.yiyiaddon.m.b.a<"s2ljjffi8y6x5a","w6KFaXGWVpbyGi04QswgOV07QnrM/Y1Lz6ZCv9VGvoE=",-1297843840061021489,-1332648322089971354,75647553863145989,2338825598238551307>()) {
                                    case -1997624120:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              }
                           default:
                              throw null;
                        }
                     }

                     if (var4 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"sxj7lvk0tfvab","rCEMPXc5d3+xQLPR/2mNkmCRQwyIm0WK69M4KRK/FZY=",7777114272673976273,2970091133163592681,-4441099168032777949,942651397779237907>()) {
                           case 1979067319:
                              return var4.dk();
                           default:
                              throw null;
                        }
                     } else {
                        var5 = this.b.aV().iterator();
                        switch ((int)com.yiyiaddon.m.b.a<"s2i52kjdnd4szx","JGfNF3t8lN5/bnhlF6SuHqS/WBFYNnDW6pQZQiZN2NU=",1113195232173378946,8039163022233503422,-5367282704940863554,-3189816344874419479>()) {
                           case -774803093:
                              while (var5.hasNext()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2iy7lbxzozqrq","tHxZLKbsXIsajciJXjq1Fg9M+dfXPiowJ8zP1zLCreE=",2430002026271148291,4807722938980595542,2025347140833930734,623442927997268794>()) {
                                    case -1061330448:
                                       com.yiyiaddon.e.n.i.a var8 = (com.yiyiaddon.e.n.i.a)var5.next();
                                       if (var2.equalsIgnoreCase(var8.dk())) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3m03kgzk1kdex","yI9s7wEouvQ/aktoSBKBgTSro8o5618MlSU2bU0eQO0=",6485796134149417209,2269362307247815955,6155399501428421878,-5881773529493150195>()) {
                                             case -2037254378:
                                                return var8.dk();
                                             default:
                                                throw null;
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s612nkwfg2ga8","lL4iPJPV6iXCqIlrxNYr9zizqagXRdAlrQc3Lj7hhkE=",6008103124530773902,-5020491630805656006,2720050840000710870,1588615419967279313>()) {
                                          case -1595976148:
                                             continue;
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
                  default:
                     throw null;
               }
            }
         }
      }
   }

   public List<String> a(String var1, boolean var2) {
      String var10000;
      if (var1 == null) {
         label115:
         switch ((int)com.yiyiaddon.m.b.a<"s3cfh3po5rag6k","xCZJ2U/1y5I4MxxJMGsOdG29d+uKuRINr4gL6JXyNL4=",-5168115239037988411,533710773502064916,7809786607130125030,-7646064649481831917>()) {
            case -1875990983:
               var10000 = (String)com.yiyiaddon.m.b.a<"sx660rlm2m3nu","fpuJTxNxJF3ztqpuAJdZlEP/ZfKN+7IbbuGOgQ==",5879527679174639585,3052465777945369467,-5395243503426540410,-7483403761027763752>();
               switch ((int)com.yiyiaddon.m.b.a<"s14kh0u6bt869","4C26Zzyg6NrQUKOEGHjZJB/I3RiWj74JmxBGb4SQEog=",-6098990277797426207,-3050361225356769252,-1898058661850997202,-4712884909994407337>()) {
                  case 26816336:
                     break label115;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var1.trim();
         switch ((int)com.yiyiaddon.m.b.a<"s1gikc8hlt120s","J7MEy/PDlNt7ekKub1VDhis+4cFzUdfR1PpEeZJWn+k=",-2129391584202771892,3392441662008662976,-7644599492050893793,3966219758685922512>()) {
            case -977078724:
               break;
            default:
               throw null;
         }
      }

      String var3 = var10000;
      if (!var2) {
         switch ((int)com.yiyiaddon.m.b.a<"s1opsculehkgsi","a4du477GGoRk9B154c+COwm5TiFQ2l/vaAdnmKWWy5I=",-3500203961135587916,2249634573676195578,-5988540391232848765,-1750169197253923445>()) {
            case -428507602:
               if (!var3.isEmpty()) {
                  label109:
                  switch ((int)com.yiyiaddon.m.b.a<"s3k04vr9j2sram","ryL/Y0NjB60FScOa1KVgkhvcmAh8ks6YKJeRvnC/QIc=",-3665442519056535881,-2208327129598764056,-7856283867284334574,-2960210254345265511>()) {
                     case -50272881:
                        if (var3.charAt(0) == '"') {
                           return List.of();
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s29cav2tjg9ott","hLmUl0QomRjSJC4sy2a1vbiS9PrfImU53R3LDfnK22o=",-455051750589194584,-2264852454132067343,5523438667539016349,-1173221875242904653>()) {
                           case -1549735004:
                              if (var3.charAt(0) == '\'') {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1q38ory9dwc5","PxuafQci50S4dpl4rWrTdoA0LDDILi15pGKd+x2qVkQ=",9196993992099129608,4783419578316273104,5658046274737780976,6604197349265076679>()) {
                                    case 1120897281:
                                       return List.of();
                                    default:
                                       throw null;
                                 }
                              }
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

      String var4 = ay(var3).toLowerCase(Locale.ROOT);
      LinkedHashMap var5 = new LinkedHashMap();
      Iterator var6 = this.b.aV().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3rssjqfq4fnfn","vM+QR6p5dIV4WCF9nhPs6lBliIKQFgYAsGRmzbY9bZs=",-6541353189626757482,5974252260830175128,1842484222146341404,275294507488890110>()) {
         case 294926523:
            while (var6.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2xrkjy4iin8zy","qXK4sWtCexUzeeddEzc9aJuI3dOivYZBlt1NHkzQSb0=",-3809547677659334032,-4310120744187976999,4882354363280686268,-2049606150304854776>()) {
                  case -2103199227:
                     com.yiyiaddon.e.n.i.a var7 = (com.yiyiaddon.e.n.i.a)var6.next();
                     String var8 = var7.dA();
                     if (var8 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s822st520xdxf","KpGWfg5cXAOiqeAwnq6uEXEAWawMbUvpZ51Vj1adlFo=",-6924979916921741825,-8782933339675019749,6016182754830724941,-6396587637298185513>()) {
                           case 549276778:
                              if (var8.isBlank()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2f1glmxoquwhi","BACz9PwBHHhFseADT+X6aoQYi68FwSdEthq8YnbWHPU=",7971023100135586379,-1597169147648893085,2758374977898553405,2557117156258521869>()) {
                                    case -883202741:
                                       switch ((int)com.yiyiaddon.m.b.a<"s2w5vzxb4jeutz","U0ORxkB6qe8Oc6rHgdbvRpy5d1UC2zXIO02FjhI0xHA=",4011515296930112059,-3308047639677847637,-986648691400376420,-8891478287054841095>()) {
                                          case -972977304:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 var5.merge(var8, 1, Integer::sum);
                                 switch ((int)com.yiyiaddon.m.b.a<"s2ncq3mq7i3kpd","+YIlip4XrzyoB1Yh4SXPLn2FftIlp//2ZRKXi3weC2k=",-700122928049133321,3465056154710522609,-3802815392982582302,-6659610872262059298>()) {
                                    case 473542409:
                                       continue;
                                    default:
                                       throw null;
                                 }
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

            ArrayList var10 = new ArrayList();
            Iterator var11 = this.b.aV().iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s330ckeie1ganu","Ntk6ChpKi58IRl621Zuu9c6jucUSo9r2sv6HJigALQQ=",-5836590232358133516,6068186413445833476,6069023102043727604,-2707927647548451461>()) {
               case 468914485:
                  while (var11.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2xizqi1c814j2","UFX8w+pW8YTnxSYxMU2Sc+lKKZMacl6xk3KEQNBoLA4=",-610570647383468248,-8137020755084944370,4828865201454628999,5441123156356249700>()) {
                        case 1611712678:
                           com.yiyiaddon.e.n.i.a var12 = (com.yiyiaddon.e.n.i.a)var11.next();
                           a(var10, var12.dk(), var4, var2);
                           String var9 = var12.dA();
                           if (var9 != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1g6y4vqccfod7","QeJ31QgC5rl6+WkbsUYoMDQ4P/KC49t4DpJxlIoxDkA=",7399187203779607868,7103677516031107726,-1298819323203429913,8923661556507771957>()) {
                                 case 542736803:
                                    if (var9.isBlank()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2bcb8a8ftjv1l","C+Z1NZ/6LAIZXtTPmNLv8rM47qzRxl4YzAQegzFXCxU=",5020756908149859573,5693694422101206067,8104721317701527907,5204269540195185375>()) {
                                          case -263738667:
                                             switch ((int)com.yiyiaddon.m.b.a<"s347p0xr11zoe9","ZwHsmvaMascCtSBBZyktorYXED6g6w9ljzcb7dtOoCk=",-1734191332112407046,315764511973711828,-1268681049822619661,-848658635980792473>()) {
                                                case -938989734:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else if (var5.getOrDefault(var9, 0) > 1) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2s4b12i98uz8b","BNKxbCW6auhixNnSbHkJ8F7HUj5KGT6+amo7kg2ls0I=",-9175491754435307661,-6091496615260368193,-6800467328514364438,6422687019306881536>()) {
                                          case 939400318:
                                             switch ((int)com.yiyiaddon.m.b.a<"s2qyk1aa0xxtfc","UAjzvcp77E6Bj6AmN9UAEOIHcIedtCfIy9oZBSVGPaw=",6091305261865322927,-6912343309479945842,-2267534746431191490,-9171463302821126908>()) {
                                                case -1266648999:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       a(var10, var9, var4, var2);
                                       switch ((int)com.yiyiaddon.m.b.a<"s1m3qie2vs18cr","6M2QpvG15rpClaiVdB1fQ9DfPpyki+aAoYjM25j9LEA=",7140592993565458338,-6349739815029743303,3902423493862669079,1961790979272031129>()) {
                                          case 971607481:
                                             continue;
                                          default:
                                             throw null;
                                       }
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

                  return var10;
               default:
                  throw null;
            }
         default:
            throw null;
      }
   }

   public List<String> c(String var1, String var2) {
      String var3 = this.ax(var1);
      if (var3 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s267en8yqjtqyd","TDdJmeM3MX9Nm8amZJvhnG63ajpbyX/Vm8awLubQlbQ=",-7740585398048142015,-6752826791457038533,-4909038205322727141,-7723242218754476515>()) {
            case -2038354363:
               return List.of();
            default:
               throw null;
         }
      } else {
         ArrayList var4 = new ArrayList<>(this.b.d(var3));
         l var5 = this.b.r().get(var3);
         if (var5 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"sj6t4aw37s1j2","Hlmllg5OuWMpJUNj4ZPj+QAb+t8f5buzEO0jTEcdWLk=",7416705684206497225,-8715048173477590437,-1564568289143690803,865233908990268969>()) {
               case 876402327:
                  if (var5.di()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2eph953abfci8","HtDwfUSLKvGfBgnVRW31K7u7CJUux5pgJl+vrMsJJZs=",1410173070514834926,1750346908939940338,774272893383788395,-5077807970101430141>()) {
                        case 1961854324:
                           if (!var4.contains(var5.dK())) {
                              label89:
                              switch ((int)com.yiyiaddon.m.b.a<"s2mys777rg0d7y","L/v0vXkoXcp+9P3yvuq/7acMtvgPYwJJrq26Qr9NCbE=",-5747922768955624418,5732052810703073256,198976101089483254,3262248276810370836>()) {
                                 case 391722603:
                                    var4.add(var5.dK());
                                    switch ((int)com.yiyiaddon.m.b.a<"s3gxlhpm4hkfyc","VqMJ5klSWl/V9UXe2NymAtwfkmW6Pe+hUJNPmZ7/S2M=",-97938805942797421,-8979690826767522962,-9177641234433522952,-5357487674222891385>()) {
                                       case 971297291:
                                          break label89;
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

         Iterator var6 = h.e(var3).iterator();
         switch ((int)com.yiyiaddon.m.b.a<"sgbsbj3ek91aa","UtoXCwCKsHZm1sLAhTLMggV0Ane8qhyVnmIWagULFWQ=",-178372362514140524,7676262469404014650,-332176802194320413,3415086460253630311>()) {
            case -333674923:
               while (var6.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3gjp8ugui9ann","5CJaZU+sXSlnQkafKADS/UyYG0rpf1gJTWofd3AmwqM=",2027657152768797440,-5542185403707770816,3729302488782109231,-8247910211566471137>()) {
                     case -921916634:
                        String var7 = (String)var6.next();
                        if (!var4.contains(var7)) {
                           label81:
                           switch ((int)com.yiyiaddon.m.b.a<"s1d3ncsvzqtwbu","mAbIW2n0xN1rQrCdr+A7XCVQqn9JYLL64w39FHhVXdU=",5359872818356251190,-8905282690436849498,7503744794257276847,-1712067587609390832>()) {
                              case 1755155672:
                                 var4.add(var7);
                                 switch ((int)com.yiyiaddon.m.b.a<"s3qbmdp9tauic9","t3C5K7KQfo9bCOl1pIUxY8Qx71ArT0jsB9RXjtPCplU=",-2345008755418020584,7093654229668652660,4359585835829212154,6200726858121820859>()) {
                                    case 448661559:
                                       break label81;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"spnf6jdttlc5k","Dl/I56F6va8Bc+ky/UGLVwKvIy+88H6LN0DswEioe28=",985848539254565316,4837513940297648257,8095981299144536578,4263920060273802909>()) {
                           case -1258628135:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               String var10000;
               if (var2 == null) {
                  label72:
                  switch ((int)com.yiyiaddon.m.b.a<"s1ygw9sd3zj56r","++9kAnE4JcVlTRa343GRCOYY31LsPcK4Q9aK31EHDs0=",6070901901367108731,4596824893405003574,-7718064878962099384,8384838300452246535>()) {
                     case -875133417:
                        var10000 = (String)com.yiyiaddon.m.b.a<"sx660rlm2m3nu","fpuJTxNxJF3ztqpuAJdZlEP/ZfKN+7IbbuGOgQ==",5879527679174639585,3052465777945369467,-5395243503426540410,-7483403761027763752>();
                        switch ((int)com.yiyiaddon.m.b.a<"s2fz057wgmtc2g","XZroR8+9UliE3jTYBKcUZ90jOZcFssL4473gU2AtMBw=",-8598737601673612127,-5194512127867055289,3496139018821455082,-1460623156582824492>()) {
                           case 1329789860:
                              break label72;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var10000 = var2.trim().toLowerCase(Locale.ROOT);
                  switch ((int)com.yiyiaddon.m.b.a<"s3hxmz59zqj0k6","1z4G/wggU4dArITzr6VfW0OISLNHMhejXf023YeVhxY=",8041318618909727437,-6126875231514940834,179611880759412527,-5768823080391154222>()) {
                     case -721822268:
                        break;
                     default:
                        throw null;
                  }
               }

               String var10 = var10000;
               ArrayList var11 = new ArrayList();
               Iterator var8 = var4.iterator();
               switch ((int)com.yiyiaddon.m.b.a<"s4iykdanothvg","9TgBawfeNsbc4ipNXfunNJr1hvpnYl+jqcivr10Q3WM=",-768774296237709972,-5967107060688372064,-5080553728158680245,-8547296473756920676>()) {
                  case -1006791255:
                     while (var8.hasNext()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2j4wd1qdytqrd","DL+NWmxu6TnQKYFRvXLFNgAOK8FDsQPygKl5eL8EG5E=",7296358507948309811,-4010265986555530848,718443778277333728,-6369566066447392606>()) {
                           case -678524422:
                              label104: {
                                 String var9 = (String)var8.next();
                                 if (!var10.isEmpty()) {
                                    label63:
                                    switch ((int)com.yiyiaddon.m.b.a<"s1mq9f36swpbsd","0FfoppPtcaEUKWMgrL5k76BEbgpSQdnAuXvyDHQuLn4=",-6204995434007933756,4384924610984213867,3074853486585783567,-5465878623267408425>()) {
                                       case -769531721:
                                          if (!var9.toLowerCase(Locale.ROOT).startsWith(var10)) {
                                             break label104;
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s2fi3lqyos78ys","sHEvXeMfKhHNYWS4LGbNnWgvgA6CSgsSffo+lYD1nPY=",-8790842099102371428,6936249768003364019,-6218052332914960352,2022794604490170665>()) {
                                             case 1839167965:
                                                break label63;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 var11.add(var9);
                                 switch ((int)com.yiyiaddon.m.b.a<"swu6p80pudjrd","S6MTOlSBmhRoIrbcnfv4Tv+MP4c3nVrZellR4lbGXfY=",6042969460710220107,-1241020576438934390,8332992104848096300,5845184467286794232>()) {
                                    case -1468405540:
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s48v6grdt7jui","lTozhGL8SgcBiFxJbxL5nNxVJU/SXKP9GHcWMyfPjRo=",-794016757018904516,-1772366598966319131,4354529885530444292,626544405401787228>()) {
                                 case -743144232:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     return var11;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   private String de() {
      CharSequence var1 = new StringBuilder();
      Iterator var2 = this.b.aV().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s38z373rn9r04u","BPuV5xauVyDd5E+xWrKVAD7KllzKYqmABC+3lVixvBc=",-4430183808921639278,-1281614535964935082,-4495962040897876480,-2585803361116454232>()) {
         case -416180332:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s13xac4s1gmshw","mMzle87lFQ/O4qo280vzOPxJIHNkUEJ1IHgjlyaLVvE=",6574264146351472270,-3543881087335633248,-3254093240550250554,1918386309834827028>()) {
                  case -732530704:
                     com.yiyiaddon.e.n.i.a var3 = (com.yiyiaddon.e.n.i.a)var2.next();
                     ArrayList var4 = new ArrayList();
                     Iterator var5 = this.b.d(var3.dk()).iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s3lx3cgl0sgm7y","kF/yu/Y75GpE0hoeIks0IxCqnYmdYlF6otbupCUFot4=",-7853540243945783216,-992803351095957144,1194231685334140259,-7985849586976793903>()) {
                        case -1714147976:
                           while (var5.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s35vqfid2ixle6","P59ySz2rPjeFgVQWSJLdJHjyy0sJuSvVmKVJYHhmTKk=",-7317985639667867037,1472571882588007572,-7711709333128252212,-3614305323036394264>()) {
                                 case -725690417:
                                    String var6 = (String)var5.next();
                                    if (com.yiyiaddon.e.n.j.c.ai(var6)) {
                                       label68:
                                       switch ((int)com.yiyiaddon.m.b.a<"s1vrxjrvj4dybt","Zls9OqWYuzqQGbW+mAJB7H0m9o/Y+zQ4uXQh+1o5gCU=",6331128407367537008,-7568239490673931308,8877864995635232473,-4698034450820389585>()) {
                                          case -423018675:
                                             var4.add(var6);
                                             switch ((int)com.yiyiaddon.m.b.a<"s129t6pt79rdml","yy82+fYJPPzTw1QpwBFgIE0gLtKT0NIul5J+TDspA/M=",293842165905060445,4569350226455374364,4521870678022178318,736932721944874883>()) {
                                                case 350319157:
                                                   break label68;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s1tam3lz60it7p","C6qGiiB2KRVT5FIOgcDLCARlfBFhOrp+wcTIhKYQsRs=",6398460830602344305,-8270145820672595016,-1926079808432118541,-256746677265632336>()) {
                                       case 2064020746:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           if (var4.isEmpty()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3d7hf03sb1l3x","XiYKbWckoNvYHLc3gEsvO4rrr9EdfABU6V5teiY3328=",-8714922688967047890,-1027138356716044550,5312590681844459749,8319440055237905740>()) {
                                 case 1717281627:
                                    switch ((int)com.yiyiaddon.m.b.a<"s37o6cc0k25eiw","k5Cx0xpjlRJpVc6+kvrlVXTGSlsaCf4vmmnQFNfpmhE=",-8072019808426903070,157659582446746618,-2068674486199425089,9116798983361086530>()) {
                                       case -779761336:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              if (var1.length() > 0) {
                                 label56:
                                 switch ((int)com.yiyiaddon.m.b.a<"s2mccpg93af58x","ZxYJLUEVxkT96R3LHp2sRq/+/yD9oeMdebtLSThHrn8=",553646199674347910,-5378224472116371108,1463076637069392391,-8668448905459538520>()) {
                                    case -583048163:
                                       var1.append(
                                          (String)com.yiyiaddon.m.b.a<"s38echj63hahl6","XR4/kgJDIHvFiO6+cmJ4JTn/zyRu5M92uac4V0ek+GA/iT5L6Lk=",-5203164174933967449,5830112219879107850,-1503746433068995929,-7436049990714914986>()
                                       );
                                       switch ((int)com.yiyiaddon.m.b.a<"sxyelx0xaqkxl","zG22G0Z1yCfnhnkavyfkGvQ0Re1dEiHEVmq/ic2n2u4=",5547863441045447134,-2227739079957451224,983068936863807856,-620038389513703980>()) {
                                          case 2109280648:
                                             break label56;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var1.append(var3.dk())
                                 .append('(')
                                 .append(var3.dA())
                                 .append(
                                    (String)com.yiyiaddon.m.b.a<"sun4v3awtvu0","MVzXV2e80IBQH3kvJQWAWliXhN9XN6ze30P3CAEinKYWiUds",3918889024489782155,-405477142201642641,162608808844185720,-5622242568088961942>()
                                 )
                                 .append(
                                    String.join(
                                       (String)com.yiyiaddon.m.b.a<"s1oamsc3og6d2s","Cyw+WDZ1qDR7K9+TiUlBeyY9dWqyJn0n9c8ca9HV",3075763232450526112,2233407881010583994,-9023826734609661174,5602046358212652936>(),
                                       var4
                                    )
                                 );
                              switch ((int)com.yiyiaddon.m.b.a<"s38iq6u05e2ab1","cQ8CO8OtI4yMR5X807JIxJEZOOi/VYAKUD5ZOCTw6nc=",-4746517941275474099,8180984015950104519,7699528618288692090,-8844692224226354866>()) {
                                 case -54973876:
                                    continue;
                                 default:
                                    throw null;
                              }
                           }
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            if (var1.length() == 0) {
               switch ((int)com.yiyiaddon.m.b.a<"s40g2gzv4tlsv","c2e/PGSQ9sH/m4WjR8DBCg1rYiki5UdGIDx1NsWpq1I=",-6332635842300820398,2079297088689157931,2646666341061553747,5821566278706286065>()) {
                  case 2081324604:
                     CharSequence var10000 = (String)com.yiyiaddon.m.b.a<"s1h10wbm0ug3w8","sLXcc4KeTasBldgOIibL3vAuUddZvVwWt6om0SRYBDQrhBLDdszCBXihkmG1nkQXcEG1ig6uoLr4tbM04lL/PD4Aw1Y1u+t6puI/eF/3",2756963932593553247,-3901583366746361379,4524732818668698324,2893178332036354812>();
                     switch ((int)com.yiyiaddon.m.b.a<"s2oq88edn9xxid","2jixM2LaQsMgYEb2QDBenDpWIyqfF9S7IIeIT6D13qs=",9078812348891688441,3949104506853301506,4184002010118114121,2321869071980116805>()) {
                        case -932621175:
                           return var10000 + "";
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               switch ((int)com.yiyiaddon.m.b.a<"s2odkufgm013oy","WOjmVhjeC5JTMcnTnmobiO+6pm2nzTtamHj2uMz/NmA=",-3061984703474999093,6318354176691928736,-633155845638183990,6664054023317353069>()) {
                  case 2097418556:
                     return var1 + "";
                  default:
                     throw null;
               }
            }
         default:
            throw null;
      }
   }

   public List<String> aJ() {
      ArrayList var1 = new ArrayList();
      h.gD();
      var1.add(u(com.yiyiaddon.e.n.a.bT()) + u(com.yiyiaddon.e.n.a.bU()));
      var1.add(com.yiyiaddon.k.e.c.a().h() + u(com.yiyiaddon.k.e.c.dn()));
      List var2 = q.aW();
      int var3 = 0;
      int var4 = 0;
      int var5 = 0;
      LinkedHashSet var6 = new LinkedHashSet();
      Iterator var7 = var2.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3nyxebxa8a87","v99/VepccI+qt3vtZFu5g80vakM0UuL6Dn/Y1/+nl7E=",-7396102761131385411,9091127775198739966,5852639293522174209,5448695272911767944>()) {
         case -1987571768:
            while (var7.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"sjujmvktzittq","8g+5/T6CKlpL1zw9moxRVIl4IGIYA+RHIFMm34Wq2ww=",7384501800204810642,8703168234864738909,-1091317455874361761,-1210633583351046189>()) {
                  case 161567516:
                     q.b var8;
                     var8 = (q.b)var7.next();
                     label201:
                     switch (var8.a()) {
                        case ITEM_DEF:
                           var3++;
                           switch ((int)com.yiyiaddon.m.b.a<"s2cc5hxyjej9m4","lcjYNeGppnvTe4PnGDacNqcQzXGeNosuovIvh/EBPcY=",-2514712810992544915,2600561668114118267,-6131372462966096825,-5175921171708788021>()) {
                              case 7594854:
                                 break label201;
                              default:
                                 throw null;
                           }
                        case ITEM_MODEL:
                           var4++;
                           switch ((int)com.yiyiaddon.m.b.a<"s195jv06oebx6n","WgVtWdI00nZ2BNt4H6qg36xlpPNTvyvyX7PGNHL3f2w=",-2673619377149490930,-1132293162647554193,4046591500623463675,-3403490695102535700>()) {
                              case -1388267851:
                                 break label201;
                              default:
                                 throw null;
                           }
                        case BLOCK_MODEL:
                           var5++;
                           switch ((int)com.yiyiaddon.m.b.a<"s2csugnspnnvet","WjBg0vuDZ2ozhn+nAo5dZWflzfZLswYk4fkZQ3c3xjc=",-5536828235049277632,-3508726626981540186,5276856412590656815,-7631404932290827364>()) {
                              case 2040080182:
                                 break;
                              default:
                                 throw null;
                           }
                     }

                     String var9 = p.aL(var8.dP());
                     if (var9 != null) {
                        label191:
                        switch ((int)com.yiyiaddon.m.b.a<"stj9fvthu5bem","fELV/FQDE58iHpqMSGonavORo7l84ffnzI2QdFrPVvc=",-8179673762354745790,-6438549383014559856,8007418222071304216,-7926127562059773513>()) {
                           case 2034659222:
                              var6.add(var9);
                              switch ((int)com.yiyiaddon.m.b.a<"s1tkqchvlrmrtf","cjErqjNCt6ANK8bZUSlQ1hkmucBGuexfqipnyQxUilE=",-3629575967463769130,29391527278681745,1695120007962458165,-8304031386716526460>()) {
                                 case 279540741:
                                    break label191;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3l7xc2n513xb5","IGyq9InGe/irZIwp+J6fbUg7ap3LcQ8UfmSw51QwegY=",6785606362196210312,3132577460831495638,7940909360282108905,-8410709179572365977>()) {
                        case -1916425980:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            var1.add("" + var2.size() + var3 + var4 + var5);
            String var10001;
            if (var6.isEmpty()) {
               label181:
               switch ((int)com.yiyiaddon.m.b.a<"s3ltow8syxd92p","EcMus9LHQpvrxvNhq4bJTzu8XPXHpnGNSfYgw/DeVZA=",95851004010881127,-982020393631028050,8613419426305211914,-3491475942580767594>()) {
                  case -23284192:
                     var10001 = (String)com.yiyiaddon.m.b.a<"s1fw8tk764qi6r","OjQwTNp7yw+BIZ1dAAiaxfvAcoc5RpPYJ4f4UR6w",1856050099721292537,7306698127417049398,-5775225035143262367,-2059076278433850644>();
                     switch ((int)com.yiyiaddon.m.b.a<"s24elre8twz7r7","tjFKTEJV49yrhzFkYoGr7WMlirsYmsXpf20Cv8NPKSQ=",-9087945624754131665,6205798250955583197,-302867210799786775,3992346665890262408>()) {
                        case 1705374091:
                           break label181;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10001 = String.join(
                  (String)com.yiyiaddon.m.b.a<"s25tutqvoadtll","F1MS82ATjfw/sHteIDV9fEvvZm9cMb9xt5Smj5i6",6660783348534751557,-2491373101868623484,3508839780055624152,4655514815924789475>(),
                  var6
               );
               switch ((int)com.yiyiaddon.m.b.a<"ssp7wtd2cidjv","rGtWm5a7sXHR8ro57KUIvGwJF45w1um+lXvZRlHvOr8=",4789603546037320945,-8627404450828861507,-4173402248696220289,-5098564325490123762>()) {
                  case -2130074085:
                     break;
                  default:
                     throw null;
               }
            }

            var1.add(var10001 + "");
            int var12 = 0;
            Iterator var13 = this.b.aV().iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s1ynhff1cbcqwn","IIi12TeeJprPXlZc+l7UgqP16RwSyB+5bq3fcim9J7M=",7476133399999879968,-7052310826214722611,8435797107341666759,1749954418549330905>()) {
               case 1701739518:
                  while (var13.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s271aafqa9ibzq","OyuHbquaSxvMlyhCWSt+TIAwH7273Mw5kAbITA5h4CU=",-3599992105982800665,6563315977770088164,-8805797607350975623,-5648172641796078398>()) {
                        case -36938371:
                           com.yiyiaddon.e.n.i.a var15 = (com.yiyiaddon.e.n.i.a)var13.next();
                           if (!this.b.d(var15.dk()).isEmpty()) {
                              label168:
                              switch ((int)com.yiyiaddon.m.b.a<"sfukl5ssepiv9","w8ksJ/yew24bw/h6mGL0e+glKSA9m5ayRSXV4JVwDTA=",-6588349344241480333,-2036214823745020508,8084846198768870430,9189164674691821783>()) {
                                 case -1805250784:
                                    var12++;
                                    switch ((int)com.yiyiaddon.m.b.a<"s2qp7xwiht782u","KRR3zRtDcqQnlpxK5vVhD3EGMCvjsaaz9/wphAVVHoo=",-5280805955893958825,-8528046996609098730,-7315646923197332978,5852480000971315104>()) {
                                       case -1174942877:
                                          break label168;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s39p5bt89zav9","Yjvw3uD/au5Xo8bN4AqoIz7gsX5vWQVU4euO7cgnQF0=",-4901391581674848755,-1699255498797276042,-702668193643582108,347702031161774057>()) {
                              case -701990242:
                                 continue;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  var1.add(
                     ""
                        + this.b.aV().size()
                        + this.b.a(com.yiyiaddon.e.n.o.d.POT).size()
                        + this.b.a(com.yiyiaddon.e.n.o.d.FERTILIZER).size()
                        + this.b.a(com.yiyiaddon.e.n.o.d.POTION).size()
                        + this.b.a(com.yiyiaddon.e.n.o.d.WATERING_CAN).size()
                        + this.b.a(com.yiyiaddon.e.n.o.d.SPRINKLER).size()
                        + this.b.a(com.yiyiaddon.e.n.o.d.SHELTER).size()
                  );
                  var1.add(var12 + "");
                  var1.add(this.de());
                  if (this.b.dO() == null) {
                     label158:
                     switch ((int)com.yiyiaddon.m.b.a<"s117hfjwqxe7","pAzgTEJ3hXUFAan/n/K9gVC77KIAZ53wHEInTu63FEo=",-6627514885004992124,-5756313869603111870,-6725053387881387230,8977242692103649795>()) {
                        case 1132333206:
                           var10001 = (String)com.yiyiaddon.m.b.a<"s7l1jsegepzru","Cl2aPN6+BKKHy29U9ipwgrGDiJ7aegnnjmZwP4TdjZH+2CR6NBcIxNLwq5fYvph3uEQVVa/FlcvgoA==",1297353031012902121,-3959842017775249912,8279743752446010701,-8147622872509499922>();
                           switch ((int)com.yiyiaddon.m.b.a<"svodrb75xb5sp","cu2RKPL3qpEjnY+mXApHJ3rVdZO4XkQtuqmir6PWixw=",137299438007109794,-7330127482570936143,2110319336896938337,-3016942505513074102>()) {
                              case 1770330927:
                                 break label158;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10001 = this.b.dO() + "";
                     switch ((int)com.yiyiaddon.m.b.a<"s3ozoo598sfpd5","xFjSgM7YwbJYqoNBbbClAZCP98pmnbQcrUH1PBMyqEo=",-9054414443075392688,381787798390187742,4986269600180375762,-3070676220483447789>()) {
                        case 232148122:
                           break;
                        default:
                           throw null;
                     }
                  }

                  var1.add(var10001);
                  List var14 = this.b.aV();
                  StringBuilder var16 = new StringBuilder();
                  int var10 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s1zevqxxya87xf","cnLA2ITyl8QjwXyAQixkFM3yeqB3oXxa9NBMs8TFTtk=",-6144866656495873430,467715957233714264,8610271767260402002,-5939640427305960405>()) {
                     case -481308558:
                        while (var10 < Math.min(10, var14.size())) {
                           switch ((int)com.yiyiaddon.m.b.a<"s18bjsnabc0y5f","bYtYAYmO4UvObEAPl+gbAMcuPwftiGFL5ZesH6i3jN4=",-2011621486425775581,8097157505429969496,8239436056160243347,-6251946224492197905>()) {
                              case 257060862:
                                 if (var10 > 0) {
                                    label145:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3jn7oi2svp2rx","tkkO6K3mrSvoCtLWORTVitZNtYpyhPLC1c0WxzyOOdk=",-2232285755127857465,-3888202960171528057,-8148791082317265411,3838596463298140361>()) {
                                       case -1894070259:
                                          var16.append(
                                             (String)com.yiyiaddon.m.b.a<"s38echj63hahl6","XR4/kgJDIHvFiO6+cmJ4JTn/zyRu5M92uac4V0ek+GA/iT5L6Lk=",-5203164174933967449,5830112219879107850,-1503746433068995929,-7436049990714914986>()
                                          );
                                          switch ((int)com.yiyiaddon.m.b.a<"s1e8l9aq1g4i0a","hQPdF2vMVXoKfWpCDlfmqggv3Ky/dBdmhBJb57EKaFA=",7973338939781971081,1937763847079646509,3735089960585226002,4402628299211224110>()) {
                                             case 1854902732:
                                                break label145;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 var16.append(((com.yiyiaddon.e.n.i.a)var14.get(var10)).dk())
                                    .append(
                                       (String)com.yiyiaddon.m.b.a<"s3e2uy6ckbfhk6","Gp6FivZ+TzD47Vzdn3C2aVaOXUUFBiX3bwPe+lwA",-319274596179965295,-3775350894485632558,-1908697631978534888,38142184085177258>()
                                    )
                                    .append(((com.yiyiaddon.e.n.i.a)var14.get(var10)).dA())
                                    .append(
                                       (String)com.yiyiaddon.m.b.a<"s34deuwgnjckbw","UD9sQJI62kWUxDkmkA7K4m7ZG7sfvSP1Im5Cx2YF",1471362141522294960,4768204615483414769,845847456558980486,-844242681512128673>()
                                    );
                                 var10++;
                                 switch ((int)com.yiyiaddon.m.b.a<"sggqypjw6gr2j","PRzhgM5qhhx/En6tqRjoQe5Cx6xlkite2qf4gO1Vz9M=",-8750655304516723130,6771273026822556433,-7319067114390640101,7798364640072611933>()) {
                                    case 7342980:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (var16.length() == 0) {
                           label128:
                           switch ((int)com.yiyiaddon.m.b.a<"sh8l4xtaoq51q","PP9fledk5fYkdJ0Rlt1xD/SjD5PKJ9RP0a7xs9+y1U0=",-7684868547945728959,2377916747814496529,-2938305451390178541,-5123761612837050172>()) {
                              case 1956020736:
                                 var10001 = (String)com.yiyiaddon.m.b.a<"s1fw8tk764qi6r","OjQwTNp7yw+BIZ1dAAiaxfvAcoc5RpPYJ4f4UR6w",1856050099721292537,7306698127417049398,-5775225035143262367,-2059076278433850644>();
                                 switch ((int)com.yiyiaddon.m.b.a<"se524klxqnegn","45dM7iq9ooc0lSo348ADsMQzHAhe60s4SBrGKlrtAJc=",-6483066019245752645,-5144988623679919208,3304287443207512053,-6741142445630790057>()) {
                                    case -53057853:
                                       break label128;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10001 = String.valueOf(var16);
                           String var10002;
                           if (var14.size() > 10) {
                              label133:
                              switch ((int)com.yiyiaddon.m.b.a<"shqar7wl3d2qh","SvBGFFJkYwoyO0Ty5rWUytBcNWR1xQnf4H/nJCA6RXI=",-7252895730141075379,8323577821823263682,1477370700544814401,8087189890628001919>()) {
                                 case 1669450175:
                                    var10002 = var14.size() + "";
                                    switch ((int)com.yiyiaddon.m.b.a<"s19wl55abbti7q","PxgJjfvY5gGeCvt5HWMoB0sxSfbQvlov0HBodqHLOV4=",292796659780000237,2380731112291869387,7778994448026773757,4052694929749901181>()) {
                                       case -1821155575:
                                          break label133;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var10002 = (String)com.yiyiaddon.m.b.a<"sx660rlm2m3nu","fpuJTxNxJF3ztqpuAJdZlEP/ZfKN+7IbbuGOgQ==",5879527679174639585,3052465777945369467,-5395243503426540410,-7483403761027763752>();
                              switch ((int)com.yiyiaddon.m.b.a<"s35bv7b1k4hhai","jPne+Xyk2RVwBKSNXlHN66sSSAhTSVHLWEvHIfgn/Sw=",-632467850903435107,1234897493619299007,3792669855208245345,4600859206074259990>()) {
                                 case -1643942463:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           var10001 = var10001 + var10002;
                           switch ((int)com.yiyiaddon.m.b.a<"sycw8cjrcvvki","eMt5MDEofJcACkJryvkDrBRtcZ7vHFvqetAq4TUDYME=",8999640882445576566,-8436748925620351643,8989039494451323518,8868365309155642424>()) {
                              case 666669966:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        var1.add(var10001 + "");
                        var1.add(df() + "");
                        var1.add(dg() + "");
                        var1.add(dh() + "");
                        var1.add(di() + "");
                        var1.add(this.dj() + "");
                        com.yiyiaddon.g.c.a var17 = com.yiyiaddon.i.b.a.a();
                        if (var17 == null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s34sh1ga4ite5q","QiVq4Di3bTQyXCv5nIe0zzLHOILrcq3MDOjGnwt+TRI=",135133474672074196,-4540021227830482965,448015857399052493,9071037567537076855>()) {
                              case -568354389:
                                 var1.add(
                                    (String)com.yiyiaddon.m.b.a<"s2pkj3ofa6vs3n","o4zNls2itMczx1oZoZXVVMgAlm1OmVPV86eI0o0THNCDifhcHtMFy7myVU2fS4H5AYVYe/jyMdE9vNLw8iIBvNJVe9zRbRmn8C3AcBK5RVqydo8F+Qduhvby",-1261607051835374554,-2107392517785301281,575673050833570837,-2908356083847439021>()
                                 );
                                 return var1;
                              default:
                                 throw null;
                           }
                        } else {
                           var1.add(u(var17.fL()) + u(var17.fM()) + a(var17.fN(), 90));
                           var1.add(u(var17.ds()) + "");
                           var10001 = u(var17.dr());
                           String var24 = u(var17.fS());
                           String var10003;
                           if (var17.fT() == null) {
                              label119:
                              switch ((int)com.yiyiaddon.m.b.a<"s23lccimng8z5g","VOk+WZ1vTRKyYAySMqp3IEL91XH9RkItcLkDOAjYWy0=",-5734505906964072217,273884009229734355,-1916784214086716249,-6912335694930702127>()) {
                                 case 303953230:
                                    var10003 = (String)com.yiyiaddon.m.b.a<"sx660rlm2m3nu","fpuJTxNxJF3ztqpuAJdZlEP/ZfKN+7IbbuGOgQ==",5879527679174639585,3052465777945369467,-5395243503426540410,-7483403761027763752>();
                                    switch ((int)com.yiyiaddon.m.b.a<"s3lf6pmqvn6zza","FJB7WZwSUxpIFYJPsmVZ8a3q+nugsIVpD21CU7W9Pt0=",9075158099754453336,-1490241497199970521,-5941567934938143579,-7490009321371060419>()) {
                                       case 665970686:
                                          break label119;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var10003 = var17.fT() + "";
                              switch ((int)com.yiyiaddon.m.b.a<"s3l7fmxprlvps1","twHH+cnfeMCJO2elhx5p3/SGLvxHSgnUBgZUWAfDoQY=",-76600140844831591,-4542491621593465478,-2170939218354142915,-1732208684887529023>()) {
                                 case 1620595878:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           var1.add(var10001 + var24 + var10003);
                           com.yiyiaddon.e.n.j.c.e var11 = com.yiyiaddon.e.n.j.c.a().b();
                           var10001 = var11.b().m();
                           var24 = u(var11.dk());
                           var10003 = u(var11.dl());
                           String var10004;
                           if (var11.b() == null) {
                              label112:
                              switch ((int)com.yiyiaddon.m.b.a<"s5yculv80vw82","hD6QT19PhZtsbFCjxSNwgqvjc7l8EM2nWVvcpST1XAI=",5882077780652490755,6175307453555497194,-6015372798070002493,4655358314412368966>()) {
                                 case -992351661:
                                    var10004 = null;
                                    switch ((int)com.yiyiaddon.m.b.a<"s11uzre1fv5gxw","Po2OHj3Zj5yWAF9WNDQt9u3HYdOTxZPAj8X1lCO90YI=",1629670438309561678,5792959863709170080,-2907355500742494928,1934323636959304363>()) {
                                       case -1662977851:
                                          break label112;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var10004 = var11.b().m();
                              switch ((int)com.yiyiaddon.m.b.a<"s36gj3us9h6se3","hbeMaMbJDvLxsy5rb8fyOl+LYoZlqvzL2mUaFnPe19Q=",7548310436492980173,-227269239586965581,-5354097877151755864,3826727811101612247>()) {
                                 case -1263627302:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           var1.add(var10001 + var24 + var10003 + u(var10004));
                           if (this.a(var17)) {
                              label105:
                              switch ((int)com.yiyiaddon.m.b.a<"s3gffyhkd3wrxd","Hr3xSn02bRpwCXv6rfWU07+zfmd9bbQ5BIVb8+N4Hd0=",-2020998186730425005,-6616833044099083820,3628938506669707448,3600030823392694887>()) {
                                 case -905239961:
                                    var10001 = (String)com.yiyiaddon.m.b.a<"s2b5k51jc7whmu","O0HwGi1YCIoht8s0HYsa2nMv2D8JmaduxL/aVH1M",-7366971672454728763,3316394662012244886,-4670748665179050332,-543508611193201549>();
                                    switch ((int)com.yiyiaddon.m.b.a<"s3df2pero3uuow","E7Ea7AWH7lKBvLg5WwsTtHKdsDfHY0kFOzlXEaxAb5M=",-8521397073230331777,8948213358687581834,-8465753217556746638,-7796157223156555970>()) {
                                       case 627993517:
                                          break label105;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              var10001 = (String)com.yiyiaddon.m.b.a<"s38zkoazwhjlbe","UB6SzqM2Jet4Vvu5Tw0K93SH0Qp5OxsImIe1f6/s",-137177631357034029,4567726589069666297,-6484954974565684589,-5290531984594939683>();
                              switch ((int)com.yiyiaddon.m.b.a<"sbbaau7fdsnxh","csiY3wMtVuHhxByZop0rfqoJnStmYVyMhAKt7jOwMzc=",4629583785328171749,-3822570821547217748,4115235317151467211,-175551652756774001>()) {
                                 case 1983338219:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           var1.add(var10001 + u(var17.fO()));
                           return var1;
                        }
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

   private static String df() {
      Map var0 = h.m();
      if (var0.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3b5k8g6ikdjec","gImdVXpbhA2S6O4aOylON863kwLN9h/UgwQqlcAxLhw=",-18973281048781666,6779434745483424043,374525200715112364,-8948888973987713256>()) {
            case 1549286040:
               return (String)com.yiyiaddon.m.b.a<"s1wze51i78i2bn","KJKhro/nid+qjocbP4RQep1kEIv66RZKRRHNsDdao9dke/noL/g5CYn8XCudHjZdoZZxLD/wKGUmDi1oX3ZaICX+ZkdIbarHQ98=",449735288665390065,5432597517182254209,-4940135686288490303,-3495434616884384450>();
            default:
               throw null;
         }
      } else {
         StringBuilder var1 = new StringBuilder();
         int var2 = 0;
         Iterator var3 = var0.entrySet().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"sjnkcsy471cb5","tr7Lwo0oOxC0l8jEC5wy1d+7z02q4LB8ApvAaXgolbw=",2290367859894705304,8364686128135119128,-6788834064974188391,-603412500760467252>()) {
            case 1412849902:
               label62:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1avgiu8z6wfy1","Eqne+lQEUGyFPslsqlT/vwNq6l2aj/DUp3rVjSjWS7U=",3998904124760328857,1207661934952682064,1964022453650566794,1137446581796304072>()) {
                     case -1591540164:
                        Entry var4 = (Entry)var3.next();
                        if (var2 > 0) {
                           label49:
                           switch ((int)com.yiyiaddon.m.b.a<"s3uekmm1c1o4or","2PUaft24Y9gPcQ8BuY+wy4MC84UPkA5I1+InxHjqGxU=",5737313420906463782,-2649005104008021912,6649587680429765853,4689764090574584302>()) {
                              case -266196375:
                                 var1.append(
                                    (String)com.yiyiaddon.m.b.a<"s38echj63hahl6","XR4/kgJDIHvFiO6+cmJ4JTn/zyRu5M92uac4V0ek+GA/iT5L6Lk=",-5203164174933967449,5830112219879107850,-1503746433068995929,-7436049990714914986>()
                                 );
                                 switch ((int)com.yiyiaddon.m.b.a<"s1gou1ugh94sk1","wMbwGbbLS5BQwd70fxyo9Sr9XqATHLd6ieGmu7aqH24=",4006335480658244582,-7479083848300211040,-6573706686648274614,7186623800646809144>()) {
                                    case 916060302:
                                       break label49;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var1.append((String)var4.getKey())
                           .append(
                              (String)com.yiyiaddon.m.b.a<"s3hpcrl2mj1cnl","dbBHgO1EBCWgkci1myzCZ7UrRnh5C0hoKUe4FRI8T0Lu9H0NLbk=",-2337103578295995698,-6943568552200269483,-3873819712523409585,4900790875856560059>()
                           )
                           .append((String)var4.getValue());
                        if (++var2 < 4) {
                           switch ((int)com.yiyiaddon.m.b.a<"sofrljjbvy3qe","YsSGJAZ1lfxtwLauBLHTENdDwcOxFEnHgRK0fsg9+MU=",8565971471401951210,6656598773258509932,-116867003928466530,-3674693877065422677>()) {
                              case -1959132647:
                                 continue;
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2221u9sdhtdzb","15v0DbpmQbkNPe1QcAH7co6b7qie4W75UvX5tG+Rj0I=",-7355661506516329679,-6720699468553709116,2972910216122039121,6301463908817453173>()) {
                           case -1117953395:
                              switch ((int)com.yiyiaddon.m.b.a<"s2t3cxem4z7yk","rJsiESCOD9k/p4ercyaHTHHh4pj0hUGH3OXssRSAZAI=",-2974115023043689168,2572731029597826628,4275851471815821391,3868946495731001083>()) {
                                 case 217528036:
                                    break label62;
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

               if (var0.size() > var2) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3fpy1orskf0vr","+5KzAj5y+LAoO8fCvhu6UkKHiiYneceQ1GBSmFIWd74=",371267226353379607,3613864040199703357,-4927682644245241658,2739257368705701539>()) {
                     case -326252730:
                        var1.append(
                              (String)com.yiyiaddon.m.b.a<"s1bd5rp0biwpew","qlThuPxSFQxpzHVfbTgS2iK10FuAyk1aM1nwWEUoRblvJi1zXo43oA==",-1686510704936441726,-1433737353184331405,8786788105818378141,-1878505468393924473>()
                           )
                           .append(var0.size())
                           .append(
                              (String)com.yiyiaddon.m.b.a<"s11opwyau56haz","98IzKG1Ox/hiORgg2i4+/mJT1M0myg1J3EvIn+cmAm0=",-461715235412447931,5079889401187868843,6225409549010846067,-6906584732628163250>()
                           );
                        switch ((int)com.yiyiaddon.m.b.a<"s30h13sq7228iv","KZ0bH/g/YvK1cg/aV6K82paoglt7ughin+r0qEzKJYI=",8687715732204757891,-6821472578009718343,-3365293685166025536,7898435990346383189>()) {
                           case -1808204911:
                              return var1.toString();
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  var1.append(
                        (String)com.yiyiaddon.m.b.a<"s3sj79x5d4kbqc","mb9WXMLr94AsSnuoI2DYTwI23uslXDHDvY3CP1m4D7tLAJuxTKqe6Q==",8510339549893675769,8810977675061441379,-1581740942894677718,885826562384222943>()
                     )
                     .append(var0.size())
                     .append(
                        (String)com.yiyiaddon.m.b.a<"s38dlw817fo3k8","vDPBl9kc25LG3+1kT4/nHqO3GjT4/TWot/p4oj2C32K+dA==",-2166551821253075375,-5752214455257386357,5595443613612476075,-4493229265352078937>()
                     );
                  switch ((int)com.yiyiaddon.m.b.a<"s2uisgjbt9q80r","wFNVnAkGEoIOyPDoDwTih6J1EeRYaJhpkBya6UC1uR0=",5074784990898259505,-1359215427224976056,6955227665713273631,-485890268212057593>()) {
                     case 531585126:
                        return var1.toString();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      }
   }

   private static String dg() {
      Screen var0 = Minecraft.getInstance().screen;
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s39qg5hrm9qc2y","tXW1ioEfcChVsJZzNzuNUVjnt1nhIXi40JXVGz2t4DY=",-4821169641009000729,-3200382319545065037,-7882487052585045504,3653163472424736112>()) {
            case -1901275720:
               return (String)com.yiyiaddon.m.b.a<"s34paljqxddwiy","XkhmFQShIV+gXMPCGhl2zuJMqq0CqN9nPSQcueMk5NnQ54EL8AcOlEXc",1687425211497102986,4774587316766248528,-1258924857691410332,642724646799722782>();
            default:
               throw null;
         }
      } else {
         AbstractContainerMenu var1 = com.yiyiaddon.i.a.a.b();
         int var2 = 0;
         if (var1 != null) {
            label36:
            switch ((int)com.yiyiaddon.m.b.a<"s1nf63hnnepupl","WDOYat2yRi/Xvf3FidpZu58nKEOc5Byej+gaWOopts4=",-4456413368967398460,-2910982856154037869,-7787586649731777251,-470768451765593199>()) {
               case -206070902:
                  Iterator var3 = var1.slots.iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s1skez954nq9r6","U+Ge7HhF8HKVU4Yi+rWzD09i+WUT+QFfzd/PisegrD4=",7861475470297261105,2058581050534018621,8720853637197341080,8990892734477723254>()) {
                     case 788665651:
                        while (var3.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3aelbxn5eh4nj","sde9bsyee9xM5DCC1D2ZYQNuuODuxMLeq3UDcxdS4Bs=",8944596312465737979,-6885953299843563199,-865640183832530112,-559223278061635490>()) {
                              case -1045046187:
                                 Slot var4 = (Slot)var3.next();
                                 if (!var4.getItem().isEmpty()) {
                                    label30:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3bdwftmjyhtdj","RAfWrTsPRziyoDmIxFLjxaW4pliBkqSLp5QPYVZNyyU=",6095899853263385027,3328518551687271495,-3083079678391541054,6917317100196204641>()) {
                                       case 1880600041:
                                          var2++;
                                          switch ((int)com.yiyiaddon.m.b.a<"s1ajfnhkuaxvb7","Gw7uEZtMRFeSiK9P4du3lnD0IhxZjdzm1MOCLjTJqtA=",7459238165071208886,871083817654239766,-1235469892614437925,5133553137259602427>()) {
                                             case 1866163063:
                                                break label30;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s3tu0j793a07rn","/jRQWWsH3DtUDjcHClQe3biJ3vbRMeGGftlY4ws6OVM=",264149039471653908,2908940817483267357,5482607413094816543,8848872884099640148>()) {
                                    case -1666527506:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                        break label36;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         return var0.getClass().getSimpleName() + var2 + h.bZ();
      }
   }

   private static String dh() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1mi4trrhtewdv","iXkfb9oplYvuKwGgGq/MxG9kipzLoCkg/ciAPMg1l6s=",-1503018450587462501,4709700013666640199,-5667789416987153600,-8639300683921817551>()) {
            case 1469400197:
               if (var0.player != null) {
                  LinkedHashMap var1 = new LinkedHashMap();
                  Iterator var2 = b(var0).iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s1rwb997zegfn3","VY1rlXoTsCnufD8K4vxqngpb8ULUlfYPpOGdOQvWI7U=",1932536035279961847,-1282193141052433884,4451829938265174011,3889915542828557270>()) {
                     case -1044135407:
                        while (var2.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s6pyxjvlzoeki","K59A8E7weewXJTnWJkfaMsx8eVNyOjzs9RRpEvg876I=",-1709226438666558725,-3063217033134025388,122695088599681153,4982536965380641308>()) {
                              case 1612410172:
                                 Entity var3 = (Entity)var2.next();
                                 Identifier var4 = BuiltInRegistries.ENTITY_TYPE.getKey(var3.getType());
                                 String var10001;
                                 if (var4 == null) {
                                    label69:
                                    switch ((int)com.yiyiaddon.m.b.a<"s1jaj63wh5x0bn","Wy94wTa660kp+72FPslDIvsx3vVF6HOsSaO0tUQadQQ=",-5264277768309914314,-2411960327497585725,-9166596129281613903,8553611351809505191>()) {
                                       case -766099963:
                                          var10001 = (String)com.yiyiaddon.m.b.a<"savefb6vm9nxr","2MBZizVxk2yQRRkkQuh54L6dv1iZ4Y8NkGkwYvoNrIE=",409716677274354873,1467208385844080147,-4096466560545417639,603483399903753807>();
                                          switch ((int)com.yiyiaddon.m.b.a<"s1bjf79nwrjjyd","HLDOdEaQnB98bGonjS+tVPn5gXuOg61WAuJvqtlzccY=",-3036835791399955261,-465720571825090857,865926312001580038,1371013887079348522>()) {
                                             case -1163479746:
                                                break label69;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    var10001 = var4.toString();
                                    switch ((int)com.yiyiaddon.m.b.a<"s2si8gvjamu3yl","6Px9cV1zVGIN3WQ87IfrJWl6GBv3j3IqAavpakS/77g=",5150453202621427203,2442602458553050063,6506324428445775241,-6253889508913470914>()) {
                                       case 1134693961:
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 var1.merge(var10001, 1, Integer::sum);
                                 switch ((int)com.yiyiaddon.m.b.a<"s18o1f6d74yoct","fKulNSNlflbpTwiPgpcjMGGV8R23upBwiK7Izs+e+7g=",-8361366647690018905,-5625523081154728784,609257223559265843,5304821752907343680>()) {
                                    case -1874416932:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (var1.isEmpty()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3fbasam33cirb","f46ipdixCh2XZobgHYxoItNbYecP+zeh3vK5wj9lVCc=",-2977737569956040908,6328945974496648033,-6839375084051052108,7876441515120194661>()) {
                              case -1086991425:
                                 return (String)com.yiyiaddon.m.b.a<"s1fw8tk764qi6r","OjQwTNp7yw+BIZ1dAAiaxfvAcoc5RpPYJ4f4UR6w",1856050099721292537,7306698127417049398,-5775225035143262367,-2059076278433850644>();
                              default:
                                 throw null;
                           }
                        } else {
                           StringBuilder var6 = new StringBuilder();
                           int var7 = 0;
                           Iterator var8 = var1.entrySet().iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s3aqzpmfsl384b","k7dZ7Zk9FxUv9J+akKHZBGX5Z6dLI9j9h75zCsYdV90=",-1991959575111682379,-4824120643318224043,-2100818725516244284,-8752510934274114916>()) {
                              case -1171932105:
                                 while (var8.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2x2wz75ypim78","tl3tprSwAzGrQNSgaqCgE1YcGu2gzebbTBWJeccHnU4=",-3417715552132526580,-7072780598953433589,1528018854620881896,-4493290621852344606>()) {
                                       case 118130673:
                                          Entry var5 = (Entry)var8.next();
                                          if (var7 >= 6) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2rm9wihsm5pnx","PvofBDBkfR3fRLce6VKt9ClPkzg6d9DZQcKymPiw7jA=",-4176834212281954208,-7507032939633803669,-5254675876546764440,1526180064213908299>()) {
                                                case 2090315737:
                                                   switch ((int)com.yiyiaddon.m.b.a<"slwueni43epdp","PDSoHp8akzp05gIVCZNxPqi8RI3nBhIyE1On/l5TmRQ=",3057561469584689,3296700753391561028,4595030855879670731,-2286353288566335157>()) {
                                                      case 1614251008:
                                                         return var6.toString();
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          if (var7 > 0) {
                                             label56:
                                             switch ((int)com.yiyiaddon.m.b.a<"s2x5bfc8p67p5m","dm3kr4fp16qFSFN95w1rlg5p+6A6gpPv6umrZnfxyfM=",-2565224296387887776,8876667791783307863,5118448675401408362,-7312770786003663678>()) {
                                                case -254098825:
                                                   var6.append(
                                                      (String)com.yiyiaddon.m.b.a<"s38echj63hahl6","XR4/kgJDIHvFiO6+cmJ4JTn/zyRu5M92uac4V0ek+GA/iT5L6Lk=",-5203164174933967449,5830112219879107850,-1503746433068995929,-7436049990714914986>()
                                                   );
                                                   switch ((int)com.yiyiaddon.m.b.a<"symi2x0by90kv","oNdKgH+sTkvUR8xmDd/t/8d/yaclS9HBDUaoXY0AXNc=",6615755459099069277,8040876790584056336,1697191148403773190,6619989879668503876>()) {
                                                      case -1089265010:
                                                         break label56;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          var6.append((String)var5.getKey())
                                             .append(
                                                (String)com.yiyiaddon.m.b.a<"s86f0wf6vpo5v","r/snbzdlwZ8o10kVyW17lPwI3rWGL5l7fCDLaJoI",956493659323109225,9022073796328520246,-3143236853888975552,-1354502789904639038>()
                                             )
                                             .append(var5.getValue());
                                          var7++;
                                          switch ((int)com.yiyiaddon.m.b.a<"s268k85l0e1uhb","QvNp2h1JBIx3p7Ud5w5d1DMDRb5V/8qJWzUmhInqXBw=",4478947980248475602,-6232809389162604297,-2740337313560352032,3639526005693200339>()) {
                                             case -1114014204:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 return var6.toString();
                              default:
                                 throw null;
                           }
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3l1sqlsi1v4av","zgN5YmLjdXu6ronv5uVmBZSXSFky5sj1eoligfbstVY=",-5071925973522256896,7561170670039914933,4009764950847877871,3930053949212566139>()) {
                     case -402998015:
                        return (String)com.yiyiaddon.m.b.a<"s1fw8tk764qi6r","OjQwTNp7yw+BIZ1dAAiaxfvAcoc5RpPYJ4f4UR6w",1856050099721292537,7306698127417049398,-5775225035143262367,-2059076278433850644>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s1fw8tk764qi6r","OjQwTNp7yw+BIZ1dAAiaxfvAcoc5RpPYJ4f4UR6w",1856050099721292537,7306698127417049398,-5775225035143262367,-2059076278433850644>();
      }
   }

   private static String di() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s17ycweuc0fnu3","gmKsJ85ujVk9r9XlPN6hrKRAYdvAAwKylwPCmINuDkA=",-1179716416381586045,3596507966757622393,-2853644387162939260,-6714401199067122014>()) {
            case -1647065948:
               if (var0.player != null) {
                  LinkedHashMap var1 = new LinkedHashMap();
                  Iterator var2 = b(var0).iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s72cxf4m39bat","l4tEIszzXSKZK5qOUkdLxrlXJrXkWmUd7E6WKvNfu4E=",1432917469005607474,-6556733857739938472,4305474415044951852,-6815105938743458276>()) {
                     case -787738205:
                        while (true) {
                           if (var2.hasNext()) {
                              label165:
                              switch ((int)com.yiyiaddon.m.b.a<"s1p0aso14t8yqy","NCrKqPY18MDyWcBMeAGh4rst1lwfG4LMncjaZbM/oxM=",1652994389611412845,-1090533688091711510,2797308075352250347,758484301347132419>()) {
                                 case 1824331067:
                                    Entity var3 = (Entity)var2.next();
                                    String var4 = null;
                                    String var5 = null;
                                    if (var3 instanceof ItemDisplay) {
                                       label141:
                                       switch ((int)com.yiyiaddon.m.b.a<"seo0b6wgj8nzv","c7mbQM/GNFjHwzkMMi2wdwYLRcenJAaO47pTCTM7nU0=",4711964380989947177,-9148893578149600509,-4738681253793526482,531604807919177751>()) {
                                          case 460091524:
                                             ItemDisplay var6 = (ItemDisplay)var3;
                                             ItemStack var8 = var6.getItemStack();
                                             var4 = com.yiyiaddon.e.n.p.a.d(var8);
                                             if (var4 == null) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s4kwnea4txg1k","70IZcz/r561l3AXqDooH9gxS5sbCRyzFbUmv2wNruSE=",-3817443631601600363,8062998490036288409,1741415038256380699,7419856746028102250>()) {
                                                   case -1045822918:
                                                      if (!var8.isEmpty()) {
                                                         label136:
                                                         switch ((int)com.yiyiaddon.m.b.a<"sthq5fce4kw8d","8wMRXLkYcuzBzMo8o7wfHoMNMqbluZaHywnCmwH4uHo=",-9124822410818403331,-7741768272388570598,3407997142267364573,4855366030275479002>()) {
                                                            case 1230161712:
                                                               var4 = BuiltInRegistries.ITEM.getKey(var8.getItem()).toString();
                                                               switch ((int)com.yiyiaddon.m.b.a<"sfm84j19pjp60","itt5zEwenbWgCHDuQLYZGD0qUEXbUKWmScuBfg/hAsI=",3107045725668355108,-8499308979038804743,8079262847706352702,4614705657337349933>()) {
                                                                  case -1659717402:
                                                                     break label136;
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

                                             String var10000;
                                             if (var8.isEmpty()) {
                                                label131:
                                                switch ((int)com.yiyiaddon.m.b.a<"s2uja0fzg8sqw6","8Y1vpgT+lKck7TKiDy8sveRev0cGyb7Bq2oLsAY5gZ4=",1392438823308648870,-3649896550040956404,-3624287921512613898,7026731574180019304>()) {
                                                   case 2008747283:
                                                      var10000 = null;
                                                      switch ((int)com.yiyiaddon.m.b.a<"sxjbc2gg9grah","x8jpzJUFvMr1omv/4nX+ObA020iD5+avqnG/mV33Tv4=",-6389168917481407964,-5203091661114554659,-5270434860878551052,-6429522873900881479>()) {
                                                         case 1555704419:
                                                            break label131;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             } else {
                                                var10000 = var8.getHoverName().getString();
                                                switch ((int)com.yiyiaddon.m.b.a<"s1obn68k0rr8im","a8n7ps/LEhM0cSyozs1JF+Ib+6cqk6+mI2Qp9R2zyEk=",6502398562248103516,-2896935342819675427,1441757624573580235,-5157227859829374507>()) {
                                                   case 1764287155:
                                                      break;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             var5 = var10000;
                                             switch ((int)com.yiyiaddon.m.b.a<"s36gsxkwnkhgrx","iK/SEMORWv2HiWXky83Y137qRxBYCVCJ3b8a0btzVAI=",5635206176482489828,7957331777973012309,-3852809283784004060,2538084804017986136>()) {
                                                case 562312466:
                                                   break label141;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else if (var3 instanceof BlockDisplay) {
                                       label123:
                                       switch ((int)com.yiyiaddon.m.b.a<"s213zos8vmxkz4","oUeQdFFS/frdZKrdBcSkFYeVyocKoFvBfZeOVHeb0io=",478833993849165516,4173044659590960139,4290582280373551037,4027884005042554425>()) {
                                          case -430566895:
                                             BlockDisplay var7 = (BlockDisplay)var3;
                                             var4 = var7.getBlockState().getBlock() + "";
                                             switch ((int)com.yiyiaddon.m.b.a<"s3hs7csz7vs26p","8cUBPpwyJPL2p/DnsvxNf8Tb9eNtx28LZmQuN3ijtno=",1069281793330704363,7817531025571357096,-576195463449351602,808639428369700012>()) {
                                                case 628905269:
                                                   break label123;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    if (var4 == null) {
                                       continue;
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"ssm0mukdm9lzn","vSEoBezL/QX407vlIcnOQtS3cdbpd7ecf4vZZd6C8MA=",6551267126423030363,-4767617888234024748,-2378281321207059566,-6895225342049666624>()) {
                                       case -227326347:
                                          if (var4.isBlank()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s2hukqmrk1vc7p","PeGWsRluGbL/nzqs4Ms0JVlCQaFRjK0VxGBPwJaDYTQ=",1062672794684698295,259122067992344395,-5307903086876294115,-1956732314943141669>()) {
                                                case 1777035410:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3a86f8cpkxytm","XQ2EvhQHzEBgAP9iv7DyB6iEgjGZI6t38iSL/MZul+4=",6454591784129688847,-5024907597031265016,-8739737647057382951,4518734854506477987>()) {
                                                      case -1776805225:
                                                         continue;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          String var14;
                                          label162: {
                                             if (var5 != null) {
                                                label117:
                                                switch ((int)com.yiyiaddon.m.b.a<"s3l0fel20bijhi","+UbuVpNQGSswf8snmvVhm1d/IM89z2lJljDZiSCwitY=",5643442278193212924,-1033124332077841757,-5128364150106589303,4465482559896899324>()) {
                                                   case 11772858:
                                                      if (!var5.isBlank()) {
                                                         var14 = var5;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1hmod9sq9c90n","yaSF1eUy3K116/rlDHRbPvxTlkJ8zrlGpFN16cht9sU=",-2029204448865349318,-5730413868615515332,837019755697337419,-5961683605143463509>()) {
                                                            case -1519691274:
                                                               break label162;
                                                            default:
                                                               throw null;
                                                         }
                                                      }

                                                      switch ((int)com.yiyiaddon.m.b.a<"s3kpjutsdz03cq","qmTGi/QnWyGWc6Iw81jnGBo0Rcs2yVBi9hPVXHv9+tQ=",-1269789549283529681,3616856945090359203,5118772303101601742,-8480177146340768672>()) {
                                                         case 1535049820:
                                                            break label117;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             var14 = (String)com.yiyiaddon.m.b.a<"s1fw8tk764qi6r","OjQwTNp7yw+BIZ1dAAiaxfvAcoc5RpPYJ4f4UR6w",1856050099721292537,7306698127417049398,-5775225035143262367,-2059076278433850644>();
                                             switch ((int)com.yiyiaddon.m.b.a<"s2y0px7etwfhow","vCowyGkr5j+AlrBwCQJzaIbHoMpKik4CclBYPLvF5q8=",-8456833432605645968,-5192341068703513763,-5012883383718796190,-273789359473623691>()) {
                                                case -531609110:
                                                   break;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          String var13 = var14;
                                          var1.putIfAbsent(var4, var13);
                                          if (var1.size() < 8) {
                                             switch ((int)com.yiyiaddon.m.b.a<"sz0ui3v147hk4","4cjGqsEzh7MDbsCjMQrs+rPHo+Ku5M2iyF17wj1SqFM=",-2465891558123012263,8068467127338400425,-6725078815163671252,767708662921049166>()) {
                                                case -113416349:
                                                   continue;
                                                default:
                                                   throw null;
                                             }
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s1hqk6w0b6i8ba","XBvUxEHYVuOczOstKu5i830uP3i1K5FTRudfoGnYc+Q=",-3383910284337242759,-4002489035519950624,-8981206166919461211,795810526680632506>()) {
                                             case 1005107309:
                                                switch ((int)com.yiyiaddon.m.b.a<"st00lrx23u3iz","YRZJVtUUpRXQdR2hB2+PQlLVcnLTu+ie7UNH2hsHjRk=",5635571233243235424,-6546742989157893678,-6916474083009376422,-986380436807145244>()) {
                                                   case -1575220286:
                                                      break label165;
                                                   default:
                                                      throw null;
                                                }
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

                           if (var1.isEmpty()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1dg0yv8082ywc","OyGbHs1bexv3UymGMhn7+6yWoD1z0Pg0NqLHIvZF1+s=",-6125743610455721355,305862023143268515,-2284510771846409622,-2243845411882146480>()) {
                                 case 531672102:
                                    return (String)com.yiyiaddon.m.b.a<"s1fw8tk764qi6r","OjQwTNp7yw+BIZ1dAAiaxfvAcoc5RpPYJ4f4UR6w",1856050099721292537,7306698127417049398,-5775225035143262367,-2059076278433850644>();
                                 default:
                                    throw null;
                              }
                           }

                           StringBuilder var9 = new StringBuilder();
                           int var10 = 0;
                           Iterator var11 = var1.entrySet().iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s1drlogyx7hgea","E9UI3Uet8dC/4sPvQ65U7HRe4ojjXhgyQNazQaGOpQ0=",-6282438526430994733,-1958360527923905617,7630460650156953178,-8873377283323107188>()) {
                              case 1350505898:
                                 while (var11.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1z0ephh2ueju4","4r4tO273vLIKcJR14A9CSy2As1ri2NI9hncnIqVRj5g=",-2769929163397591410,-4379929882998836436,8144539036582332112,7024563110715663232>()) {
                                       case -218842513:
                                          Entry var12 = (Entry)var11.next();
                                          if (var10++ > 0) {
                                             label90:
                                             switch ((int)com.yiyiaddon.m.b.a<"s1lsv3nfwrz1n3","cEZ5Y4ecWeDJxz5V/dZl0SJaBnUs9N53jOkh8dTsatw=",2086819764733681607,-6147528000410174915,5599750483724668293,8972327183373546879>()) {
                                                case -1373485003:
                                                   var9.append(
                                                      (String)com.yiyiaddon.m.b.a<"s38echj63hahl6","XR4/kgJDIHvFiO6+cmJ4JTn/zyRu5M92uac4V0ek+GA/iT5L6Lk=",-5203164174933967449,5830112219879107850,-1503746433068995929,-7436049990714914986>()
                                                   );
                                                   switch ((int)com.yiyiaddon.m.b.a<"sndl5ken3g7va","LV0dh2Kkr9m982xDOTPeKFeCaRbrDv6mo8WX4VGIKmA=",2221195912483994199,1845046881541848909,5865064139701267890,6489617895932331909>()) {
                                                      case -602770459:
                                                         break label90;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          var9.append((String)var12.getKey())
                                             .append(
                                                (String)com.yiyiaddon.m.b.a<"s3e2uy6ckbfhk6","Gp6FivZ+TzD47Vzdn3C2aVaOXUUFBiX3bwPe+lwA",-319274596179965295,-3775350894485632558,-1908697631978534888,38142184085177258>()
                                             )
                                             .append((String)var12.getValue())
                                             .append(
                                                (String)com.yiyiaddon.m.b.a<"s34deuwgnjckbw","UD9sQJI62kWUxDkmkA7K4m7ZG7sfvSP1Im5Cx2YF",1471362141522294960,4768204615483414769,845847456558980486,-844242681512128673>()
                                             );
                                          switch ((int)com.yiyiaddon.m.b.a<"s2ljqejlbqr7x5","9OvQmVV3IXngWCstSSCs8Pq130iyBh5rv8b7OaltJuo=",-7866588663704120193,-5808143935976170157,-8866600601151487980,-7853709341801139608>()) {
                                             case -1520813669:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 return var9.toString();
                              default:
                                 throw null;
                           }
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1wu42vkw1a9ey","zxSoGNDCrl0m6Bo+UiEKGYSzGL33vOPfxc/z6pupNh8=",-8371676772435069107,5794234601386341749,-7735402439970755938,7621918861871489292>()) {
                     case -1283064123:
                        return (String)com.yiyiaddon.m.b.a<"s1fw8tk764qi6r","OjQwTNp7yw+BIZ1dAAiaxfvAcoc5RpPYJ4f4UR6w",1856050099721292537,7306698127417049398,-5775225035143262367,-2059076278433850644>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s1fw8tk764qi6r","OjQwTNp7yw+BIZ1dAAiaxfvAcoc5RpPYJ4f4UR6w",1856050099721292537,7306698127417049398,-5775225035143262367,-2059076278433850644>();
      }
   }

   private static List<Entity> b(Minecraft var0) {
      AABB var1 = var0.player.getBoundingBox().inflate(12.0);
      return var0.level.getEntities(var0.player, var1);
   }

   private String dj() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s29v99hffoy9s6","vaj8Syj1NvAoEedgWI+mnBLw67JoBkrC8M7XgQoeukg=",-6917717631011776209,5845135871596046799,6285612220219171810,-6201473441574159122>()) {
            case -179112035:
               if (var1.player != null) {
                  BlockPos var2 = var1.player.blockPosition();
                  g.r(var2);
                  BlockPos var3 = null;
                  double var4 = Double.MAX_VALUE;
                  Iterator var6 = BlockPos.betweenClosed(var2.offset(-8, -4, -8), var2.offset(8, 4, 8)).iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s33fkprc2k8kls","vClTf5rgEbq8RCZyM+gSwabsK3Uagtgl4CBwOav+fTU=",3435634530868334575,6761672492253692886,-2637891185905940049,8661552613554733455>()) {
                     case 1614231223:
                        while (var6.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2l64bsitbxd1v","U5+a7F4iN15VlpfUg9DcE9xT2jrW6jBeYO60nTzvM5k=",-3802480901136907955,3283896725117155259,-3436482539107858129,2301336045235402103>()) {
                              case 1084298069:
                                 BlockPos var7 = (BlockPos)var6.next();
                                 BlockPos var8 = var7.immutable();
                                 if (!b(var1, var8)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2gcqugm1h55al","CVKgvNw9WfAXG8ahVMzBYgVJPa2QWGonz2IZxTPIlcg=",4774294831832412055,-1151226397761746086,-5660570597217585892,4202124623445753524>()) {
                                       case -2042911939:
                                          switch ((int)com.yiyiaddon.m.b.a<"s6gfc46v3d9yf","SWMR3I/M99Idp4Zo257v8GnajivBoTCsxZJ5mOO01PI=",6643438654528278773,6501482871423441901,-7458903092343020936,-2584244267584658288>()) {
                                             case 182399664:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    if (g.b(var8).isEmpty()) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3nm0dv990ypsk","KDM23SXrjipnTqBxYMrMk7nWrOnR1QG6SwtMlJPjsBQ=",-9006757955931758809,3982477997343443752,-2195824214087686203,3101272421386478081>()) {
                                          case -1015487651:
                                             if (g.b(var8.above()).isEmpty()) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s21n5hykr1rivu","zN3iOjOsZu65i271WhzTHTglDdQ33pFLIC4r6OjtF2E=",-3508370095752434594,3202819824984460735,2074576604187250882,-5898216092631370440>()) {
                                                   case 1850073924:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s373agxflg15m9","dTLM11EvxJIoDIJxST2zvN/mLPQquDYVIV2O1+ly7PU=",652021304782849983,800160807033836247,-8656388635551466022,9187739416655198722>()) {
                                                         case 324454768:
                                                            continue;
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

                                    double var9 = var8.distSqr(var2);
                                    if (var9 < var4) {
                                       label117:
                                       switch ((int)com.yiyiaddon.m.b.a<"s3f2490y4r9ytg","QWJyV4YJaNN2IEOr35nsY9AfgsN9awzBmcEMQlcoFF8=",924142761352260245,-7026815009388954042,8015628080775272931,3622299817027456749>()) {
                                          case -1222145772:
                                             var4 = var9;
                                             var3 = var8;
                                             switch ((int)com.yiyiaddon.m.b.a<"s2an1bu2l64404","wDmkB80tBC/PreUWI3FEasM8Rj6Iq5KnWuZSANPEdAs=",879584271112055667,4623690267046771769,-3686616164170363903,-90176306218843432>()) {
                                                case -1906536759:
                                                   break label117;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s1ttq1yfm777la","KIqd8q7XeYyZIAeArqS1c6uw4yBLGsMrLBFoBOVYJTw=",-3962182024461535141,4825824509951223649,-7152575308267889222,-6128391122585617876>()) {
                                       case 324718064:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (var3 == null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3lgdvwdtd34bq","9+yieHXMlwOqbbdgwZ/a3mgXOErXd8a+WtZ24ixKrZw=",7758577071253690631,-5677920732897986991,4226813645067366027,1952464417102565351>()) {
                              case 229974356:
                                 if (g.dt()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2axxv1lnr4098","hcyHcIBpRzuK67G/YUq+YoPmN5xPtgWqkshtuuKWN/c=",2163895002533897159,-73241645605534174,5241928385379343745,-2071779443711673714>()) {
                                       case 1795838316:
                                          String var10000 = (String)com.yiyiaddon.m.b.a<"s2kzkidril07sn","vuUjD8sB8nds7AOGCanYRBdtXiQYJlfap1cI4ZOcpOyjtzAEyB0TPKsrDb5aQYPM6gLsdd3PxW9EzQQ1LAzRpA==",-9147682715231530622,-3929764578237219635,2243488947087294136,-6451017478820756607>();
                                          switch ((int)com.yiyiaddon.m.b.a<"s22hhapp6xa1r9","inWkCJJZhRIg8M4K4f9DfjGUMid+3RGrRQSGWth3W74=",-4372545294747648594,8143292362194762280,-8203323102970875736,-4325422374432275719>()) {
                                             case -1441783223:
                                                return var10000;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    String var17 = (String)com.yiyiaddon.m.b.a<"s3q88117i7lleh","zLBrR8RMeZAJTD8jBTFJIPV2f7+EB3Mi5MCCEoTF8G7XtZPreEY2yTUQix4h92cpsBZrkVCs",-8709399780710900436,-5210135323962871113,-1637532363183143465,-4044953944950139182>();
                                    switch ((int)com.yiyiaddon.m.b.a<"s3dh7ez44r0ux3","XJx1QvRQOoSU9M033/Q8KjUWRxzf/lURJWPEc2Qe7lg=",1447693558181189750,7936348660143638857,-2274988418584388630,-2794731443436149669>()) {
                                       case -904033026:
                                          return var17;
                                       default:
                                          throw null;
                                    }
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           ArrayList var11 = new ArrayList<>(g.b(var3));
                           Iterator var12 = g.b(var3.above()).iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"s3ph62am1ypkad","TsouclD3EJDQd5FxCLvPH/SckRCbbHWWJeP9/qPLhQs=",-1146818514107445217,-4232719487908550461,-2798685147024546482,-3471371853152577613>()) {
                              case 537953461:
                                 while (var12.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"swga10hee6vib","/DCNQEIP/hzGFxkfDgh6ibet/IwJTsUNJgbRtwxjYpk=",8679762724434011535,-4651427517958805333,-4094572351651723625,-9135211213172280482>()) {
                                       case -1975522287:
                                          String var14 = (String)var12.next();
                                          if (!var11.contains(var14)) {
                                             label99:
                                             switch ((int)com.yiyiaddon.m.b.a<"s1kd9zr2e4ck1b","8Kl5+iqZGPW4a16ULlRsYbhmLE7tK4kWxxgbbsM71Qc=",-208062484026526677,-1564556557627230995,2855455047176503766,-5112706105037590966>()) {
                                                case -551319876:
                                                   var11.add(var14);
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2wdenel371dmm","r8lsUhV1NcO7YI8II1KNtEmLy0n9drTKPCKu1fb2Dso=",8946109644765824928,1730899578340173269,7751082885262179213,-3792953252113671743>()) {
                                                      case 760300785:
                                                         break label99;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s11itekuglnqye","SmwV1O4VKHEgMHGIfQMECq4IGHhGL+0DdkMYkS1vfAM=",3658161122134181802,5665258988952807608,-642547389794643409,3115291637585465217>()) {
                                             case 2133636263:
                                                continue;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 var12 = var11.iterator();
                                 switch ((int)com.yiyiaddon.m.b.a<"s3jxfdsnrizmd2","Gvpu2M4NpH1zOqErPlsDpfGcv5t3TaBKJzwVFI16LZs=",4650802409241956040,-2566282824863145002,-516158557060637177,6455559973510212390>()) {
                                    case 1923428551:
                                       while (var12.hasNext()) {
                                          switch ((int)com.yiyiaddon.m.b.a<"sr4hkjx31yxnh","ZbyOqvoTFHU54cLh1Bx+8dVa5t7iM2PlfMy9Mh9XXAo=",3586086946295088602,8309411047404457700,-5891673996589781640,5651049033246973979>()) {
                                             case -1238962074:
                                                String var15 = (String)var12.next();
                                                com.yiyiaddon.e.n.j.b.a var16 = com.yiyiaddon.e.n.j.b.a(var15, null, var15, this.b.a());
                                                if (var16.a() != com.yiyiaddon.e.n.j.d.UNKNOWN) {
                                                   return var3.toShortString() + u(var16.dk()) + u(var16.dl()) + var16.a().m();
                                                }

                                                switch ((int)com.yiyiaddon.m.b.a<"s322pzj9qmofw4","YB+xh65IkIp+WV7fIoz8zHeUwJidKIf8rHT7ilpnFK0=",-7491215980225662852,-2114622250162362402,-4462903906109366860,5954847795512652724>()) {
                                                   case 1807359934:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1jacgd93c2a9","4UIODQsquLekJ7xMFtPZ601D0Dyd78dCvSDPWG+YHoI=",8547425170634536020,132267942858278575,-2001447728866507261,8610710168986346101>()) {
                                                         case 1169286301:
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

                                       return var3.toShortString() + "";
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1og8nif3qc3mv","Ll+i6fC35cdqPE8+wwmO72DLZEkShM9REYR8F1IgHPU=",-5893806164935786630,885390735938661812,-2991286681968747177,-8140546809356992879>()) {
                     case -485412088:
                        return (String)com.yiyiaddon.m.b.a<"s1fw8tk764qi6r","OjQwTNp7yw+BIZ1dAAiaxfvAcoc5RpPYJ4f4UR6w",1856050099721292537,7306698127417049398,-5775225035143262367,-2059076278433850644>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s1fw8tk764qi6r","OjQwTNp7yw+BIZ1dAAiaxfvAcoc5RpPYJ4f4UR6w",1856050099721292537,7306698127417049398,-5775225035143262367,-2059076278433850644>();
      }
   }

   private static boolean b(Minecraft var0, BlockPos var1) {
      com.yiyiaddon.e.n.j.b.b var2 = com.yiyiaddon.e.n.j.b.a(var0.level.getBlockState(var1));
      if (var2.a() != f.DRY) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s3bioua4wo5u54","qIFYaJzPIcfMZ3H7iGzYHEQztU8Oy3ikGQaAByydCB8=",-8275091007074423123,3787207921466457477,958627491754153702,1106333769731347082>()) {
            case 2070969630:
               if (var2.a() != f.WET) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2fcwtsoecs82x","wj7T/DEJ+yl4jUuhVHngU5lmWVAAceeG6muYFxod8y4=",4176397203452972886,-6583550431254243948,-3754008988819985059,2858409321610325334>()) {
                     case -755169735:
                        return false;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s1yf11t3wdes7q","bD3vYCorfp4kxaXHrDmlRxYLYsiZ4g52Il8duqmTjJ8=",6970302099511048417,1553166452713543474,-174708689457143018,5188163819739014989>()) {
                  case 350666555:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      switch ((int)com.yiyiaddon.m.b.a<"s2zfzkdvhh4j7v","NBrK5i4UbgaOzHGFrIlmUEDO2T7pOjrjkc0AERsyFvQ=",2802882650619495812,-8589007876853171428,-8085019556241927781,-946088290112384427>()) {
         case 1391388390:
            return true;
         default:
            throw null;
      }
   }

   private boolean a(com.yiyiaddon.g.c.a var1) {
      String var10000;
      label64: {
         if (var1.dr() != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s34xihwxiv7i0f","V4Mx/sB7IeDHBiymXBEcvbnv2k5RY0p5/H7uSshS5r8=",7874115108935580780,-7564437883877995671,8179916993127432310,3471370472500817946>()) {
               case 626680884:
                  if (!var1.dr().isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2d22xpemzv76x","tj5o905aCYw76o+SjMjcOY88Xl72+IvTKXT4EsbcCKg=",-8376318040250898288,7696805347112746132,-3870761223531503087,-5739062041681183285>()) {
                        case -32308379:
                           var10000 = var1.dr();
                           switch ((int)com.yiyiaddon.m.b.a<"swhp0oy0vdb9o","29WOotDGVUiyWpHmF+Xgc1OqXG/F8HqEhO9kaZ4SBTw=",-4853817753766115141,2555836899729868184,-1744959062027547732,-7309172679213484019>()) {
                              case -1414850948:
                                 break label64;
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

         var10000 = var1.ds();
         switch ((int)com.yiyiaddon.m.b.a<"s28qa5undytxgs","50Jv7Q6wdw3ntrscs5Oks3tetyr0gEBsy+T+NQL/6IE=",4346320995021809594,3298552371178261788,-2785229310780937435,4528008722088487916>()) {
            case -1735304781:
               break;
            default:
               throw null;
         }
      }

      String var2 = var10000;
      String var3 = com.yiyiaddon.e.n.j.b.aO(var2);
      if (var3 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2p1bxx52miqp3","5HwepFt4rzzzyPn8rNYwpXadehtsSovR0Hop13X3Kqg=",8259021393490995530,-2333873846002798237,-519392173912733822,7180830049703508418>()) {
            case 302816354:
               return false;
            default:
               throw null;
         }
      } else {
         Iterator var4 = this.b.a(com.yiyiaddon.e.n.o.d.POT).iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s2q943q6lg369v","+dUrZUNzCoLKVR6FG+YIuob2yavhBa86GpH3xsgW8ok=",8385371423873710157,6482904314383032179,-1912734092794929106,7342646777452239458>()) {
            case -2137783113:
               while (var4.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2h7yt7kymk8k4","HOBTrNbUf5oXpgH7182aWJSLnDVy9PSxIPJWq+dsLMI=",-1475121400849061660,-3177805777313459389,1042061810126547058,467333828801963008>()) {
                     case -1744514699:
                        s var5 = (s)var4.next();
                        if (var5 instanceof com.yiyiaddon.e.n.i.b) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2l1ol0rxbzxl3","fI/qcgfK5/9MzyH4e/lVExYj13wDNCbi3oAeqxG5HhY=",5871647843784582167,8634869044912906979,-4190882630697055369,-4836588498171100240>()) {
                              case 422443573:
                                 com.yiyiaddon.e.n.i.b var6 = (com.yiyiaddon.e.n.i.b)var5;
                                 if (var6.X(var3)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s278m9nzi4wfvy","5ETqmRK9IcV6gWTRDJEjt7+ZdsZ51tXSEe1k1LFsL/0=",321557389173212203,2899146761350013285,5574158220962681333,-3222040161864913469>()) {
                                       case 45874740:
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

                        switch ((int)com.yiyiaddon.m.b.a<"s2s3evarxi12pp","LOzpet63wIQDgmiwaWo3SOM66V2sjmp+sSMIBkYivro=",-7596810805358695933,-1098121512494350457,7180558300294342067,139880858788690340>()) {
                           case -2030660053:
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

   private static String u(String var0) {
      if (var0 != null) {
         label22:
         switch ((int)com.yiyiaddon.m.b.a<"s1k5pjrzkagb46","wtLgsMBHDqNsVpYEiHDR8HsBZDfOfVWOdjMd5XgY1fk=",-8117844644830557044,6718077245894456827,-2610525868594651298,1266751811764741473>()) {
            case 1009547297:
               if (!var0.isBlank()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2eavgmzsqf246","yEIdc0egYsnpyRLUzXZn/uZP2FHUSr4kIsZVyb2AOTs=",-2527087803034862648,-7577510500971635230,8012698385713211131,-6555301520638174464>()) {
                     case -636570025:
                        return var0;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s1pdgjrdmgfnpi","KEf8pZVyyRXeFGnQXILSbyD3h5z1zj6jOHnb7EiOTYg=",-4907590830971626674,-3471698658338580122,7041298893264416177,-8431258345528231470>()) {
                  case 319481567:
                     break label22;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      String var10000 = (String)com.yiyiaddon.m.b.a<"s1fw8tk764qi6r","OjQwTNp7yw+BIZ1dAAiaxfvAcoc5RpPYJ4f4UR6w",1856050099721292537,7306698127417049398,-5775225035143262367,-2059076278433850644>();
      switch ((int)com.yiyiaddon.m.b.a<"s9cck2bh5c89l","3C1IG47hQmBWRD/EonThY78k1cNkHAQbO7/6bou2hTU=",-344942122836191688,7285282007178868396,-6441694897669824770,-7994558679793630390>()) {
         case 1750452780:
            return var10000;
         default:
            throw null;
      }
   }

   private static String a(String var0, int var1) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s9i3exi6r9cpp","NAT3s3E7TQSbRvlJsunwapYvwBaxmidoiXN29T281EA=",-6246844374836272810,-1563953526305646902,6674159636395842687,-557017094509272691>()) {
            case 1444037213:
               return (String)com.yiyiaddon.m.b.a<"s1fw8tk764qi6r","OjQwTNp7yw+BIZ1dAAiaxfvAcoc5RpPYJ4f4UR6w",1856050099721292537,7306698127417049398,-5775225035143262367,-2059076278433850644>();
            default:
               throw null;
         }
      } else if (var0.length() <= var1) {
         switch ((int)com.yiyiaddon.m.b.a<"s3snnkrf22we28","ibJMN04dk4AMAbM6PG4oyiJmzn07L4ZEqngZlG5k8rU=",-5527589403255856805,8552621478491885578,-7674776790149393978,-1647804634189836011>()) {
            case 1831653766:
               switch ((int)com.yiyiaddon.m.b.a<"s1cvt95voqc2of","qbpHeR33cI65aKxYWB61tBOX13Sbcq46ZchmcgfOl9c=",4088818777232898118,8341671639210250835,4007757983515960070,-2286995465776967866>()) {
                  case -1601206019:
                     return var0;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var10000 = var0.substring(0, var1) + "";
         switch ((int)com.yiyiaddon.m.b.a<"s359screah67vp","KCXrlk0TxjJIC3glsCoTA4LjaPp7Dp6k7XQOfoL2K7w=",8078746669401328062,-4926396872005881235,7151147840786213572,-4841448076624933597>()) {
            case -1588418190:
               return var10000;
            default:
               throw null;
         }
      }
   }

   private static void a(List<String> var0, String var1, String var2, boolean var3) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s68xp3bitab8f","+K4uGPorzEq/4xoVCqRHAao56LB1k/krlYldKXniBao=",-6120890185354765637,-1773071134680838451,-1726582782139799054,6624221206000330477>()) {
            case 1689172555:
               if (!var1.isBlank()) {
                  if (!var2.isEmpty()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s33ycafevnvxlm","s5M/Ql9BOnKTi2vWMBIAA3TphP8pofvRNio2VSw7cZ0=",1811424749720190454,6199880759475665987,-7117884036561242183,-5441399126330486991>()) {
                        case -1705085238:
                           if (!var1.toLowerCase(Locale.ROOT).startsWith(var2)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s4s9rbc1844ao","89i3+mvR+8Mx4Vu4Tcn7WaRs3Ewek+F0/YL0QfFBPBs=",-4853435729741993549,-1807489552557202265,5743481612219866316,8430504030453496574>()) {
                                 case 104163737:
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

                  String var10000;
                  if (var3) {
                     label40:
                     switch ((int)com.yiyiaddon.m.b.a<"sn8gyxjoirhar","3NM+pafIjNbmpYkyk8UJONvoPOBCgJWGt8sZi0+5cMM=",-1954648247872610826,-5400909278780703502,1830358695720145402,8303175869607541370>()) {
                        case -2032507779:
                           var10000 = var1 + "";
                           switch ((int)com.yiyiaddon.m.b.a<"su7tzuz1fe0r2","qwDXJtbBnV8+WXSY1vLVx0uo7DsQ5ICTElyhuksn3lQ=",-1699121179365810788,5067608941427672974,6546003737975133076,4728750181354733297>()) {
                              case -454830140:
                                 break label40;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10000 = var1;
                     switch ((int)com.yiyiaddon.m.b.a<"s3sxpgisvuabay","fjCoeRkZYyRyUiNnmm5+w9iyHF0evKBGbnecO8PkAdA=",-852531141222982566,4555874070248977194,-1011530558099751287,-4603937604059493626>()) {
                        case 424642088:
                           break;
                        default:
                           throw null;
                     }
                  }

                  String var4 = var10000;
                  if (!var0.contains(var4)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2yg38agf3ta1y","q/BPbONjsLUbLdavPW0FIogjFBdIvUpWLG7yo+4DpdQ=",5643785370530420639,2190749756873124420,-5007408094854855544,3080085148417348651>()) {
                        case -656687628:
                           var0.add(var4);
                           switch ((int)com.yiyiaddon.m.b.a<"syrqxvrtdx51s","lqbC2CrIQuA9JJ1s1+pu9H3kjQ9bwCO3XTl71CNaTIA=",-120652803031692174,7243186484377277030,8410688219407400050,1262367742842150955>()) {
                              case -1098717257:
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
                  switch ((int)com.yiyiaddon.m.b.a<"s1wp6az3g2fm0c","323ZGGcrmL6FirVlh3uNOLyjlgD4KnVGzrrg91vzoTo=",-7021776031397629727,-2773196146518194297,6990570158280451449,3863508060883915610>()) {
                     case -423387708:
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

   private static String ay(String var0) {
      if (var0.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1kol1ssqb849l","guQVLxV8Eo+Ip12lMXwb8GZhebe4Lk9fvTOwWxN0MRQ=",-8401624172209611118,7797868636852777676,83976247797400592,-8830356838723669846>()) {
            case 1352796994:
               return var0;
            default:
               throw null;
         }
      } else {
         char var1 = var0.charAt(0);
         if (var1 != '"') {
            switch ((int)com.yiyiaddon.m.b.a<"s8y1pv0ui5u9j","OAleJjCzvx9HmKimvM5woSkaIaKth1MrPKCoawg1UBE=",-1624307536079582250,-5544877833454629510,-9127722142803005196,1342372519355102831>()) {
               case 1855447732:
                  if (var1 != '\'') {
                     switch ((int)com.yiyiaddon.m.b.a<"s28uaiultop57t","R7jGhvyyMSgjMCq+7nzrk9Z9u5ky4SOlQe02N/JNCco=",6882227335491145024,2626466683214693719,4306257128963682528,-5161902047062846615>()) {
                        case 1770425938:
                           return var0;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         String var2 = var0.substring(1);
         if (!var2.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s25ncfl95i94tc","BNE9Jc9m2hzz7+lXmTkNL/40N6/zuVDg+dneEFc1DD0=",-293482091953041892,-8359544360574204270,-5413744321000394918,5304350418728675989>()) {
               case -1298977653:
                  if (var2.charAt(var2.length() - 1) == var1) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1k8m843f4bxzv","t/0Sc1f3Qrab/GJ4WIE54LPA5FmIs0em+GK2Hi/Vpww=",9137436173545474356,-8378727361317542095,8201429183402475619,-2096213686340895248>()) {
                        case -643498857:
                           var2 = var2.substring(0, var2.length() - 1);
                           switch ((int)com.yiyiaddon.m.b.a<"s3aspopyrd4tk5","rf/z3OjEPrSIfLHjt8CDGKr2jL7KcRJyWA6calf+sQE=",-6113847480304237324,1158832452695214131,-1770793216704459317,1838008038438106246>()) {
                              case -1965863232:
                                 return var2.trim();
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

         return var2.trim();
      }
   }

   public com.yiyiaddon.e.n.j.c.c a() {
      return new com.yiyiaddon.e.n.b.b.a();
   }

   public boolean cV() {
      if (com.yiyiaddon.i.c.fp()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1q5jtkxjjrsvi","nQKFqZ2d6SLTIPl3odAGGvm6W4IWsrcisSJcb4aDHQg=",-4285031922394744656,3988581835764663427,-1793802289728745764,5734339561295214332>()) {
            case -691760602:
               if (com.yiyiaddon.k.e.c.fr()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1qmf9zp8wnqy","06GfnVBWIta78j2YNPHsQPBOsjBedlfd30vVq+a33ZA=",-8204456014777167791,8720814686973830059,1108744869773774295,-8084955180373830712>()) {
                     case 1761310498:
                        if (com.yiyiaddon.e.n.a.bT() != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"sce1amvc2d57a","BDoe2UqAx3vjO5hcJiYWi4fYOJU/Hgc+9HRbFg7HyfA=",9042596691213131097,6336441524764266335,-5147790674156475682,-4772905616247327065>()) {
                              case -1401067817:
                                 if (!this.b.a()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s15jpi3xc5rtl","XmmBdWKYdvhf46xmSJRrEBKK9zbyntrpF1UkXBnb4Sg=",439099911838309077,8129704719366386850,-5181244624344643916,367296406544484525>()) {
                                       case 801933586:
                                          switch ((int)com.yiyiaddon.m.b.a<"s3b3nuns41idia","0YOgP8/NBqG1Wi2+KwNAOMz0mXX5kI8ExV6IIa9tmSc=",-6034735143122732271,-6461734282488650889,-7518878235449462950,-5606062653140344578>()) {
                                             case -1085982573:
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

      switch ((int)com.yiyiaddon.m.b.a<"s3pgmx406982hq","e/MZgc+1NiYDm8vB3aqqcPE4PCmwnDdNlrmtvuc4mrY=",4107660038015913430,2635511636294624836,6512263540897907115,8604344755514409150>()) {
         case -38881698:
            return false;
         default:
            throw null;
      }
   }

   public com.yiyiaddon.e.n.b.b.b a(String var1, String var2, boolean var3) {
      return this.a(var1, var2, var3, null);
   }

   public com.yiyiaddon.e.n.b.b.b a(String var1, String var2, boolean var3, String var4) {
      if (!this.cV()) {
         return new com.yiyiaddon.e.n.b.b.b(com.yiyiaddon.e.n.b.b.c.NOT_READY, var1, var2, null, null);
      }

      if (var1 != null && this.b.a(var1) != null) {
         String var5 = az(var2);
         if (var5 == null) {
            return new com.yiyiaddon.e.n.b.b.b(com.yiyiaddon.e.n.b.b.c.UNKNOWN_STAGE, var1, var2, null, null);
         }

         String var6 = com.yiyiaddon.e.n.a.bT();
         if (this.b.a() == null) {
            this.b.b(var6);
         }

         l var7 = this.b.r().get(var1);
         boolean var8 = var7 != null && var7.di();
         String var9 = var8 ? var7.dK() : null;
         com.yiyiaddon.e.n.i.c var10 = var8 ? var7.b() : null;
         if (!this.a(var1, var5, var9, var4)) {
            return new com.yiyiaddon.e.n.b.b.b(com.yiyiaddon.e.n.b.b.c.UNKNOWN_STAGE, var1, var5, var9, var10);
         }

         if (com.yiyiaddon.e.n.j.c.ai(var5)) {
            return new com.yiyiaddon.e.n.b.b.b(com.yiyiaddon.e.n.b.b.c.SPECIAL_STAGE, var1, var5, var9, var10);
         }

         if (var9 != null) {
            if (var9.equalsIgnoreCase(var5)) {
               return new com.yiyiaddon.e.n.b.b.b(com.yiyiaddon.e.n.b.b.c.UNCHANGED, var1, var5, var9, var10);
            }

            if (!var3) {
               return new com.yiyiaddon.e.n.b.b.b(com.yiyiaddon.e.n.b.b.c.CONFLICT, var1, var5, var9, var10);
            }
         }

         String var11 = com.yiyiaddon.k.e.c.dn();
         String var12 = this.b.s().get(var1);
         if (var11 == null) {
            return new com.yiyiaddon.e.n.b.b.b(com.yiyiaddon.e.n.b.b.c.NOT_READY, var1, var5, var9, var10);
         }

         if (var12 == null) {
            return new com.yiyiaddon.e.n.b.b.b(com.yiyiaddon.e.n.b.b.c.NO_STAGE_EVIDENCE, var1, var5, var9, var10);
         }

         l var13 = new l(
            var5,
            k.RIGHT_CLICK,
            var7 == null ? com.yiyiaddon.e.n.i.g.UNKNOWN : var7.a(),
            var7 == null ? null : var7.dL(),
            com.yiyiaddon.e.n.i.c.VERIFIED,
            var12
         );
         LinkedHashMap var14 = new LinkedHashMap<>(this.b.r());
         var14.put(var1, var13);
         return !this.b.b(var6, var11, var14)
            ? new com.yiyiaddon.e.n.b.b.b(com.yiyiaddon.e.n.b.b.c.FAILED, var1, var5, var9, var10)
            : new com.yiyiaddon.e.n.b.b.b(com.yiyiaddon.e.n.b.b.c.SAVED, var1, var5, var9, var10);
      } else {
         return new com.yiyiaddon.e.n.b.b.b(com.yiyiaddon.e.n.b.b.c.UNKNOWN_CROP, var1, var2, null, null);
      }
   }

   private boolean a(String var1, String var2, String var3, String var4) {
      Iterator var5 = this.b.d(var1).iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s17igv6vuzcdzh","0kzpvQj9mUwmbdjsT5OcagfMxbTk33Mky4kNi8cIBCY=",-5338023006845725738,-6951868648962102978,625072311531643467,-6475347155246765319>()) {
         case 2113656936:
            while (var5.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s2j0nw7b16rhnj","liSr6HkYUNaacMjieA9po2cZfe9iiUGOlzRiZ1VF/SA=",-8411001523296152318,-3163906939291386945,-5029374372456913821,-6965771133885654930>()) {
                  case 1606262570:
                     String var6 = (String)var5.next();
                     if (var6 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3h4e0yrfs8xic","pgEoYWbR7hWh53j6Asch2s02Nn7fGGmhRj3/e8LVFMA=",-8241267375436539367,-6626046316124286197,8776004317569774683,-8382617402663514030>()) {
                           case 1183799319:
                              if (var6.equalsIgnoreCase(var2)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sn1fzn7d83oz2","2GRodke+dMY02A4O0TsyB5swUaF7sxUOOVBtywdItsk=",-1260730726017502011,-691353549729957934,4500535820595596448,1890414102293087332>()) {
                                    case 185331752:
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

                     switch ((int)com.yiyiaddon.m.b.a<"s355mb58iz7ji7","Ul66+FUze2ZlL07gMemF32e3ZlLYVtC4Al6+pGEG4Sw=",7793156490438292348,-6570650113693172223,-3085815427225593083,6639047674353960320>()) {
                        case 366250068:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            if (var3 != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s3h43swykm6qlr","ye3yB3Frk49fp3Gylxo3bocafRBQa/KzyHQFlWHhmqM=",6869299594231861732,-731622280696994596,95852186876779536,2283977368429991276>()) {
                  case 2045015330:
                     if (var3.equalsIgnoreCase(var2)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2nluz7zrrfioe","OqCTcUQjj5xzA4M3qxMQeXVXKmcYKBhyaDkvvTEUMgQ=",-3525485174451203554,-6540351192941064950,6110355772738014038,583475700463467908>()) {
                           case 946704757:
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

            if (h.h(var1, var2)) {
               switch ((int)com.yiyiaddon.m.b.a<"s1kxzvvjn0tks6","L/bRO09Q1NyoKceht2L061fS5JY9L7rHhbbI39eOHNs=",-7119925456293781144,158877371082612573,-8721107051950522562,-4851783861675452237>()) {
                  case 1690933868:
                     return true;
                  default:
                     throw null;
               }
            } else if (var4 == null) {
               switch ((int)com.yiyiaddon.m.b.a<"s3v92wbxmrg87a","SgArfeBiDA4nnnhWKdtovGzZzkfoFGvu6RWUF9IVUG8=",2334595171782140399,7312637499090475046,-2761698921957852530,6151319835797656390>()) {
                  case 1805181440:
                     return false;
                  default:
                     throw null;
               }
            } else {
               label90: {
                  String var8 = com.yiyiaddon.e.n.j.c.ad(var4).toLowerCase(Locale.ROOT);
                  String var9 = com.yiyiaddon.e.n.j.c.aQ(var8);
                  String var7 = com.yiyiaddon.e.n.j.c.aP(var8);
                  if (var9 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2yibq3uz5u3g5","B/Fx24Iqz7wbxtXSGl2PHXhxeccwxhax3Cm0HvTRQ3s=",3858330682256569620,-510842376070886498,2078444198177557414,6225006433205635809>()) {
                        case 895969876:
                           if (var9.equals(var2)) {
                              label63:
                              switch ((int)com.yiyiaddon.m.b.a<"scta0kgr7myav","MJYBIoPM0JCEXrqGmBBpS6JpQb40qRyJklC69DvFYbo=",-2909248034554533703,-3843811042036076548,-5871810449292768595,-153725792820419957>()) {
                                 case -353906210:
                                    if (var7 == null) {
                                       break label90;
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"sgov8podscucs","Z/1YDIUQ8tJiUXp34Noi1jqrBrgXq642BcLLw4zFY94=",-8009159320274786219,3675293637932706873,6186093502829355753,317551609400965164>()) {
                                       case -1943897017:
                                          if (var7.equalsIgnoreCase(var1)) {
                                             switch ((int)com.yiyiaddon.m.b.a<"sd0qgwgc42wy4","uBkjwMQZT7rt3Mr8q2ret/nLvfBqmvrzmtgUGZCaDTI=",-525170518244428739,-6080974159259267689,830669124371868245,-1832361128353089993>()) {
                                                case -395436964:
                                                   break label90;
                                                default:
                                                   throw null;
                                             }
                                          }
                                          break label63;
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

                  switch ((int)com.yiyiaddon.m.b.a<"s1f21wep0rtb3h","0EkAUnuj5TOaR8TTNsVcHYucIQOZ2aOvU71UCYfRTNA=",-1497420784705975430,-7144192997511971773,-8695018470273820507,-4269873028799515122>()) {
                     case -2126109330:
                        return false;
                     default:
                        throw null;
                  }
               }

               switch ((int)com.yiyiaddon.m.b.a<"s3epll23gsq1li","2yVOXKvnM10Qc9EeG3MYlwr4JhKj804Gpia4d7yjTiE=",3289889801312327065,-6767790215788993076,-5988388638617408297,7513617978492434358>()) {
                  case -438582581:
                     return true;
                  default:
                     throw null;
               }
            }
         default:
            throw null;
      }
   }

   public boolean T(String var1) {
      l var10000;
      if (var1 == null) {
         label32:
         switch ((int)com.yiyiaddon.m.b.a<"s1gsuwfeefpkcf","I4NbPBr8Ta0JGdGUfeICpnvbEE+Vr0omS3NQyjSwayo=",-6883995536593849816,-5166174056266183828,-3992097605550513190,-7749598843024835356>()) {
            case -1797366929:
               var10000 = null;
               switch ((int)com.yiyiaddon.m.b.a<"s1la2hz375s73u","A0C8FQenpoF6lZbCPWsjlb5SXWYvPYSGwvNWUPTUZow=",-6611943730491262435,-2534158792495805768,4142464416397063162,-6079789906713214645>()) {
                  case -1502108943:
                     break label32;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = this.b.r().get(var1);
         switch ((int)com.yiyiaddon.m.b.a<"s1zvb0kn81j5t2","oY3dn/jmTu8HgOiSJhKYYwM5AcB0V+sDw+/QvE/lhTc=",-6595727461255617052,4590278812445584576,158422736371117138,1409774678977733517>()) {
            case 943181956:
               break;
            default:
               throw null;
         }
      }

      l var2 = var10000;
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1noxfqe1krc7b","JSzIUkQO9kXXJVL3zKqMs+dfhDLgdyGWcdd82xVZ828=",-5623252252678499006,2357361165118488759,-7886433456351029721,-6021817532715054630>()) {
            case 1238205660:
               if (var2.dh()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2k8515t5wujdo","sAaT+Mq2+UhsOIzKxR5CXMN3hLlKRjH2PSzo6pUC0nk=",-8920846768985849340,8235091975173325965,-2677344941929801692,966489042056114528>()) {
                     case -815636209:
                        switch ((int)com.yiyiaddon.m.b.a<"s2keyuabx6vpz6","5dN7AQbaDPXa7LTFuDxnZ6+ldCfWo9E4++E2cYke25I=",6055745957884818457,-3440307825485410789,-8104123223690969286,3230291171468781783>()) {
                           case 697906881:
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

      switch ((int)com.yiyiaddon.m.b.a<"s34cn2tmdsgpwo","9YucrzNFuP1YG0JlAMsukA4+32T3uVG6FCTrw+G9CbI=",8709565134665533603,8653291727544761199,-8757186378840445781,6057952920796946893>()) {
         case -919992362:
            return false;
         default:
            throw null;
      }
   }

   public List<String> aK() {
      ArrayList var1 = new ArrayList();
      Iterator var2 = this.b.r().entrySet().iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1hhecf2p7lx5v","AuUPEIo2z3FW+Q/jDMdI3tzRwaXwfC8zbL3M/W7zTf4=",81233444203056993,1931578499902069864,28897024537279438,-6592525244923773878>()) {
         case -1872942389:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"sd18rftfzen4x","lDY5p++NRSusm64Aj5Ok2ullDTsXUTqn1NMKtok54EA=",-3933802681910188291,-7361992166349925786,-5554231150380795980,7934061929213805634>()) {
                  case -532881292:
                     Entry var3 = (Entry)var2.next();
                     l var4 = (l)var3.getValue();
                     if (var4 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2t28xfxt5rdy5","EnLDobdg2VZxGfue/J86DaKNDU8gsVhPAhxovnD4/7A=",5267627755426412558,-7311194488403276896,-2490283826342026896,-5553574785834770828>()) {
                           case -357646933:
                              if (!var4.di()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s15eggah1mm8yf","ZbSrkWLi4H8tIyR41OxFGyIoD/gh8co0zK+7teQdmds=",-4542993754499148463,2917209694708868798,3838205381631951488,-5456721122957678516>()) {
                                    case -622823781:
                                       switch ((int)com.yiyiaddon.m.b.a<"s2cxdb7c48q74o","4rJOhzw7i/z1K8jHkt9v0Fqb6wmlZRfPQeH08oLt8qQ=",125788503601514901,-2880831208392304266,4885792490656052870,4655705765530353444>()) {
                                          case 279410519:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else if (this.b.a((String)var3.getKey()) == null) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s17od69yvmsfqu","+cHm8Nivuv6Jk/BPl0NsNH3l46YMwdPYU+WlYpKewtE=",1461380689516532921,95449531391981363,-946720873348284494,4150121195246436787>()) {
                                    case 1351930465:
                                       switch ((int)com.yiyiaddon.m.b.a<"s3b3rttlh6zai5","MRBpCIL+olm4d0vJOgnms7YqafFqfFae++hQA0gBwu4=",5864997448639927473,5646422875940425939,-6921555005384259189,7839792688363942025>()) {
                                          case 443089335:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              } else {
                                 var1.add((String)var3.getKey());
                                 switch ((int)com.yiyiaddon.m.b.a<"s31yi03kn26qj","FkZvV11c0OkNgUnzORhlFdaO0urGnQCFgNJ9U2j99PA=",-420274413824109606,-6363948266664043250,861251903411962475,-4174501776674391643>()) {
                                    case 149748232:
                                       continue;
                                    default:
                                       throw null;
                                 }
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

            Collections.sort(var1);
            return var1;
         default:
            throw null;
      }
   }

   public com.yiyiaddon.e.n.b.b.d a(String var1) {
      if (!this.cV()) {
         return null;
      }

      if (var1 != null && this.b.a(var1) != null) {
         String var2 = com.yiyiaddon.e.n.a.bT();
         String var3 = com.yiyiaddon.k.e.c.dn();
         if (var3 == null) {
            return null;
         }

         if (this.b.a() == null) {
            this.b.b(var2);
         }

         l var4 = this.b.r().get(var1);
         LinkedHashMap var5 = new LinkedHashMap<>(this.b.r());
         var5.remove(var1);
         boolean var6 = this.b.b(var2, var3, var5);
         return new com.yiyiaddon.e.n.b.b.d(var1, var4 == null ? null : var4.dK(), var4 == null ? null : var4.b(), var6);
      } else {
         return null;
      }
   }

   private static String az(String var0) {
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1xhjinddxgf2h","DiT31fQ1YSt5ry/Ntj3USilUq8IHfJbhIOtkDPedh0w=",5869784445248975654,-6250653407217229341,6333090828160601014,-2163080666923882503>()) {
            case -1317261886:
               return null;
            default:
               throw null;
         }
      } else {
         String var1 = var0.trim();
         if (var1.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2kwltoj9ui92r","DRfo8wQxpQRJqZ/nXzgXFDEY6bYg5sxopEFAWV0omA8=",-1557465858535397408,1338058606601421878,-7164429757820463784,7010920098835655348>()) {
               case -1896439280:
                  return null;
               default:
                  throw null;
            }
         } else {
            String var2 = com.yiyiaddon.e.n.j.c.ad(var1).toLowerCase(Locale.ROOT);
            String var3 = com.yiyiaddon.e.n.j.c.aQ(var2);
            if (var3 == null) {
               switch ((int)com.yiyiaddon.m.b.a<"s4wduupb49hov","4uCl/yXK0EEkXwVZHzuEd+t2rw7MNHZy3MxkJU8jQiY=",-919373738019320151,2449650168558786112,1973681538973347250,8914060639169589704>()) {
                  case -1321520413:
                     switch ((int)com.yiyiaddon.m.b.a<"s257ljm2ojgo20","a5jHuPJ3vqZnAwR9MrJUVzaa6g+TTrc7SZlH0QNMO4w=",-2320173698221110758,1825726217813063026,-3019880388724309373,2075488436833207566>()) {
                        case -346105744:
                           return var1;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               switch ((int)com.yiyiaddon.m.b.a<"s8i9xpkj3mbjo","KZ0i0sutnFcPbfqPisNwq9yAU+hUSGOH3L6USRlSbo8=",-4315174025501088155,3986028050653263761,-3340011149145653672,-676487553363188169>()) {
                  case 1137528393:
                     return var3;
                  default:
                     throw null;
               }
            }
         }
      }
   }

   public final class a implements com.yiyiaddon.e.n.j.c.c {
      @Override
      public boolean U(String var1) {
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1meaf7obrk1pa","0lew1XY6P4BmhUNS/B6D59Xuc8TPr710dQvWTJqKSdQ=",-8665637581253465416,249985802305380756,5947543995375452658,-4612265141688673820>()) {
               case -2093789699:
                  if (b.this.b.a(var1) != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1epknxhwdigff","UzOsoTAzY2yE7bczVsHprSq1BnJ3q5CA957Nc7oqc1U=",-4484732420564422748,8238581330018371981,-7721074461738760266,-4606648017255290421>()) {
                        case -467042214:
                           switch ((int)com.yiyiaddon.m.b.a<"s20qvyvo3f4d5k","nimYl17YPmcf3SHJq4fsrpxfJKkrqZ3Up+searzydfI=",-240237271032438697,-2767015943215496900,-8500576738494052822,5216621149400958632>()) {
                              case 1018716634:
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

         switch ((int)com.yiyiaddon.m.b.a<"sp7xdlwu9zu11","ncCIvawYMtoOkkJ36sg5IMlFOneqE2aCwJU8cj9fB3c=",8252120485957766369,-4730799411316241305,-7969915567968550649,6832223715275669981>()) {
            case 438262386:
               return false;
            default:
               throw null;
         }
      }

      @Override
      public String aw(String var1) {
         com.yiyiaddon.e.n.i.a var2 = b.this.b.a(var1);
         if (var2 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1eplp6y83zyb6","enXosBoXLrcsr2ZnRtPdfCj+POlrkmLC/njGV5GT9x8=",-1823770787333393834,5565990099571218184,-5511892269021114845,2743794354398197108>()) {
               case -716956491:
                  switch ((int)com.yiyiaddon.m.b.a<"s1vxa9on21p0ud","U12ZtOSW5O9UkwBNwsEawlIGLVOX+c9CUrp1fOnp2Tk=",4274408927079374007,-8823183961904566010,-8512062572342360372,7380316591480648366>()) {
                     case -1088075101:
                        return null;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var10000 = var2.dA();
            switch ((int)com.yiyiaddon.m.b.a<"s3q60doedow813","PgyvRpA9gAcXfIFXZoD+SLDMO6nFtl7y7y54rbD+naw=",-5477027469375057634,-2107732070442843439,-5611645046707897978,-5304763664082601472>()) {
               case 1693488002:
                  return var10000;
               default:
                  throw null;
            }
         }
      }

      @Override
      public String aA(String var1) {
         l var2 = b.this.b.r().get(var1);
         if (var2 != null) {
            label22:
            switch ((int)com.yiyiaddon.m.b.a<"smkbvq87vibjp","g4ncgnvgULI+oKALeBwoyOoSfhYmEI4SA9RT9I2Zrwc=",3741136630030105783,-1661533264811732822,-2734915686671474561,-8780881351234251777>()) {
               case -1767963343:
                  if (var2.di()) {
                     String var10000 = var2.dK();
                     switch ((int)com.yiyiaddon.m.b.a<"s309mb929nb3jc","74vASVmfe3iXC3EPz8HaPLnvUkojsSJGcit6v4WojGI=",2768412437637481449,-5684311571791482300,-3659620935150245250,2793933959321479459>()) {
                        case -928686064:
                           return var10000;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"sobzv4qsgprk0","H6yuqVfRI54r0EKRdVlsdPpGV+Ba1AUPEJxJTWzUBGA=",-1624230023855247071,6409557311603226135,3319897888077950006,3590586328320785937>()) {
                     case -87259521:
                        break label22;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"s1gfmdojf9xjxc","/1JqvNY9I9Pgjtqk8ob4YvzHjbhLOimceqxvmaHdu2g=",-7818150629959019839,-7905674370764936583,-8846819513299010875,5085031888906743376>()) {
            case -469392928:
               return null;
            default:
               throw null;
         }
      }

      @Override
      public com.yiyiaddon.e.n.i.c a(String var1) {
         l var2 = b.this.b.r().get(var1);
         if (var2 != null) {
            label22:
            switch ((int)com.yiyiaddon.m.b.a<"s2yx3zpospzh2q","jIOs44+76iwOmVV406dwQV6wTAYjZpQGVhS9M0/dmNQ=",-6284932687107704652,848309565383671310,-834826127519320135,-8668615908696729804>()) {
               case 1302761518:
                  if (var2.di()) {
                     com.yiyiaddon.e.n.i.c var10000 = var2.b();
                     switch ((int)com.yiyiaddon.m.b.a<"s2ao5qtuqkdemd","6GcpDg6JqksTLMnojJ/p8HdmXdhGoq4nNLF8hTvQ6pU=",8944906023464821328,288574374642624026,-2103313127967170299,-1252705598047241518>()) {
                        case -1788736278:
                           return var10000;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s2oopi62g9ymbk","T+7UbdeFHONo0dB5bohAlY7M+wAZX5opQZdGjGQEnWY=",-9089674713563370425,811174618358557367,-5918533030035365751,-2636358115154565416>()) {
                     case -1641068054:
                        break label22;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"so9jeus3aznqv","YEhkPEEoF7F+4mmZ5yDjPf8NMT0cOjEK3XCbLyTmYYk=",6690046906345894504,-1650262908846941728,-2462208259222617875,5961798274508353793>()) {
            case 730093905:
               return null;
            default:
               throw null;
         }
      }

      @Override
      public com.yiyiaddon.e.n.i.g a(String var1) {
         l var2 = b.this.b.r().get(var1);
         if (var2 != null) {
            label22:
            switch ((int)com.yiyiaddon.m.b.a<"s2jpcya58qr6lh","wJlRL1CHNh7XfjE927Qn7gPkcOE5PsAPaWwB3Wkq4ZQ=",-6470389018891727528,5814057641035120790,-5384469613480016897,-2276441098427010700>()) {
               case 1356101736:
                  if (var2.a() != null) {
                     com.yiyiaddon.e.n.i.g var3 = var2.a();
                     switch ((int)com.yiyiaddon.m.b.a<"ss5hf9swy2v2x","809Ude5yH4nVHl2UFNzaSZ9fI7e9G6R7N4vJiw3LRgE=",1877753683946297297,703947868741634720,7352954171886542571,1397970487895968088>()) {
                        case 1903626122:
                           return var3;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s2s8tt38vcg28j","+bWKrlgmyg9jD/hvxRgPy+bHzIqGWPuH/xayeH2FnJY=",5158052154048979488,5322567376225873573,-5665739511597593203,3042975781112720279>()) {
                     case -189623543:
                        break label22;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         com.yiyiaddon.e.n.i.g var10000 = com.yiyiaddon.e.n.i.g.UNKNOWN;
         switch ((int)com.yiyiaddon.m.b.a<"s7kcqz92n8r5k","sY6AF2aVvofweLY6ShUaKFZgDEDUk7HaMp2Yc0BA5dk=",-2513126750103805708,-8613030091057710,-372887977019319396,791069130259734832>()) {
            case -947865283:
               return var10000;
            default:
               throw null;
         }
      }

      @Override
      public String aB(String var1) {
         l var2 = b.this.b.r().get(var1);
         if (var2 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"sqnuqsywn87ho","v50M3v0TMTfvUkQKG3vmKbahUZUHcBGg2fDUuZJcGb4=",1632473486970421075,6344607693546110626,4855155717889352465,-4997933120449491773>()) {
               case 2039354147:
                  switch ((int)com.yiyiaddon.m.b.a<"s27udobycrz4mr","W/LFyQfe8cNguDYR0rzcKbFAuv52D30hogm5CguVD78=",6009393038488435384,556813763661347953,83721095895907293,-8581899699959641667>()) {
                     case -1499170308:
                        return null;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            String var10000 = var2.dL();
            switch ((int)com.yiyiaddon.m.b.a<"s3epij92d8tw61","FrF2DfDa8gICXTqKbW+n/em9vQd+hIdTqXtQknxWVwM=",2449277130768447795,97303657705949393,-4982272018225300752,-8517787062520670351>()) {
               case 1299625983:
                  return var10000;
               default:
                  throw null;
            }
         }
      }

      @Override
      public List<String> d(String var1) {
         return b.this.b.d(var1);
      }

      @Override
      public com.yiyiaddon.e.n.j.c.b a(String var1, String var2) {
         Iterator var3 = b.this.b.aV().iterator();
         switch ((int)com.yiyiaddon.m.b.a<"sjdrwyyg2u6ba","AFp7zbiBnyOD/xzh5pJJpEEMDoB3kihGpGdODOkhOM0=",-6329125168369529404,8265775792496275355,-2419258146799959576,-7894152436090246406>()) {
            case 1186181451:
               while (var3.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3n20mo50eydmw","S8aErHdjUk4aCnNTiiuVSylYmrKgX8ANIUgkSXsbH4o=",7611035215291607095,8148628299659304306,4204906478743549340,-5154945160754498441>()) {
                     case 1676962564:
                        com.yiyiaddon.e.n.i.a var4 = (com.yiyiaddon.e.n.i.a)var3.next();
                        if (this.b(var4.dB(), var4.dC(), var1, var2)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1rxaogp4a0wlp","UCvpt19hVmP2pPK7bGFToNMNvGW/YQ2AHk5RjX/uh9U=",7294528882525392329,5094395342217002630,-6076404846583929519,8799179736134310364>()) {
                              case -1020321233:
                                 return this.a(var4, com.yiyiaddon.e.n.j.c.a.SEED);
                              default:
                                 throw null;
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s13u71oj34joeq","tKxReKWYZbP0LnoYTbAU+7k2zfxNVVjeRpBfkfTEpd8=",-692643679644312854,-1901838022399948792,2765037432104242590,5237447838747615037>()) {
                           case 1778063469:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               var3 = b.this.b.aV().iterator();
               switch ((int)com.yiyiaddon.m.b.a<"s4m472pzum494","nnGlGPWxjZ+1hP0kXwTs1Agvdvw/JpogZMJVQ5EEXOc=",-6979546815452499291,-2050058503677548710,-9184305391242589545,-2263271423024734687>()) {
                  case 237981933:
                     while (var3.hasNext()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2q0im4yc0qjdt","jXQvh84kl6KnxmQhuAHNHfN6/yOKYoYbMluA9MhdJJM=",2536876248156320256,917928480831442400,9098541114130529066,-6854599425987036228>()) {
                           case 429609937:
                              com.yiyiaddon.e.n.i.a var7 = (com.yiyiaddon.e.n.i.a)var3.next();
                              if (this.a(var7.aQ(), var7.aT(), var1, var2)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s1s892h1lza63j","43yi+Vgcu1K2hGUFlgyWvmMtxyU8hinUmAUUtXoOjC0=",-6870017248898755970,1903007911838695781,-8308401864268198829,8947730079067526950>()) {
                                    case -452028765:
                                       return this.a(var7, com.yiyiaddon.e.n.j.c.a.VARIANT);
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s2nl9wa2eyjre1","JW4YAtabnCKLi91BxM+q2wHb9VCNng0nMGMrHlQfRcQ=",-112554941609724569,-3862598964481652765,8952407980413071857,2816739741870556259>()) {
                                 case 1067380882:
                                    continue;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var3 = b.this.b.aV().iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"sactythj0lax7","xnP3uSE5+gTmTxaGp4/ec8IX9gS9SWRU1Bed1ozqS40=",2421180802467788589,-6160155192791632651,-7892566015970698979,-1226226003412497818>()) {
                        case -2136919790:
                           while (var3.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1hfo2jdtrzha","UmrzMmkdp+OFt0F4RYm4ILCqz+JvNM5xXJ/lc4koX7Q=",-6415771325616880977,6454237296655697797,2809729189118493582,5119819126124592674>()) {
                                 case -1473072838:
                                    com.yiyiaddon.e.n.i.a var8 = (com.yiyiaddon.e.n.i.a)var3.next();
                                    if (this.a(var8.aP(), var8.aR(), var1, var2)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2whwu72zbfyf2","hBc9q8rvWBbYiCoPIztVh40yz7jzQT1PkpUWFWahz0Y=",8443867154497483986,-2457553516941873462,551921405044324007,3993832983492886897>()) {
                                          case 1771145746:
                                             return this.a(var8, com.yiyiaddon.e.n.j.c.a.PRODUCE);
                                          default:
                                             throw null;
                                       }
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s3rvwcem9yzuh5","WBJDmJZBoGf6c0FeZZ3xmzyxUnxWkKnfJIe4Xr14mgo=",-7811629720068175318,-5882702467624912232,-8224529496931002517,-5500898504637656420>()) {
                                       case 100957029:
                                          continue;
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
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      private com.yiyiaddon.e.n.j.c.b a(com.yiyiaddon.e.n.i.a var1, com.yiyiaddon.e.n.j.c.a var2) {
         return new com.yiyiaddon.e.n.j.c.b(var1.dk(), var1.dA(), var2);
      }

      private boolean b(String var1, String var2, String var3, String var4) {
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"sa72hkx2tdnht","DpETjqbc5EMCZP+ldyhZrerwrI9Jkw55iu2d/3eeHHY=",-7798648598039172912,2911011083824738007,-8911377441518154854,5801501957905834017>()) {
               case 639097222:
                  if (var4 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"samyemdmdslxu","Z5nPFHxe86VlTdUonJ1/tUJNSzv2MrZGG1TjUwMO1C0=",2290557354017780638,6912181845998729973,4608578213121502524,-7038832053722964829>()) {
                        case -2143908712:
                           if (var1.equals(var4)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1huik8if2jkc0","jDlmOcpo/Lrs5kvqYJ3vWLz3CFEtPG5y8CnIJ8XtGgE=",7103000748891985691,-6645052904995843050,-7579170026719373152,-1645998998974115552>()) {
                                 case 285365078:
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

         if (var2 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2nzitt7ipwwpv","zeXPdcxJ86cTk7lEnUtdu7dUufIiz4ZE1kFkrPqRfFU=",-9049508098287277981,6569312400317295029,3179138002337895851,-3617451350834615770>()) {
               case 1465894977:
                  if (var2.equals(var3)) {
                     switch ((int)com.yiyiaddon.m.b.a<"segxa85kzkyvq","6r5RWQTYx/SEuwrBh7W+Wl3ju7/7vuTauCjNUmJSvkw=",-6550960629334502669,-64609600227927462,4363005760973735262,4603704374143364899>()) {
                        case 130285915:
                           switch ((int)com.yiyiaddon.m.b.a<"s2po8karda7mdp","pggZ2+d7K14/CoMgX7soLC6nVw6E0zDxjCnMZUfr8ek=",-5876188411810158844,-8165586867911737491,-2187508407776231389,-756933149952420435>()) {
                              case -1473140792:
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

         switch ((int)com.yiyiaddon.m.b.a<"s31mrfe6w6mdei","z+kRJqwk14tsj5jJjz95r1+SPXLsoYd8yGQ7tuen2Kg=",5293792894680132721,-7437894418420857180,-386158069050935204,4796143415334057704>()) {
            case 713070764:
               return false;
            default:
               throw null;
         }
      }

      private boolean a(List<String> var1, List<String> var2, String var3, String var4) {
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2c3b44pcfwp4h","nHzihwSUjIrKhx66fOKaXqfE9tuLAaZjkAReCyTYEDY=",-1623608912318506793,7360674279153622621,4251320388493967978,4118725052489217924>()) {
               case -1509737283:
                  if (var4 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s35un8it8ead5n","CSVvx/7GFyMlSLJcqNyOFqetS7oyCZRVWqFrNysTQG4=",7981445230831977991,-6153449477213444229,-4660011837892792530,6856774533991242749>()) {
                        case -1373600625:
                           if (var1.contains(var4)) {
                              switch ((int)com.yiyiaddon.m.b.a<"suckivguow2xs","WfTVxuWBzeEl1SaiKqtBUuETvGxSW7lcMdmNu3MWJfI=",-3821184681005015769,8483595130870847984,2272446381419338643,-2303005588005391524>()) {
                                 case -787360962:
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

         if (var2 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s21r5vnxw52cgq","GlsX54xAwlgbCxZveScKlETSAQcAfQQLC6/b/y/Ubo4=",4490245789943244754,5156218185972359125,-4838883097001771159,-8313128537030872910>()) {
               case -931499561:
                  if (var3 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1u84s3lnq3r45","3pW6jtyX8ejEbqoaFdkpVAh0rK7lTuxKolzCyBtrq3c=",3210336288556716092,3399711393225170308,1869046360684124417,5480401415872571687>()) {
                        case 902756880:
                           if (var2.contains(var3)) {
                              switch ((int)com.yiyiaddon.m.b.a<"spc72fwhfdqry","6YgTs5ORzhXnQC8yXR+qf6QvweENAq0iimv0OAQy6Zs=",159700034192216722,7713465701657704494,-452961931344648596,-2046256469852792992>()) {
                                 case 17029239:
                                    switch ((int)com.yiyiaddon.m.b.a<"s15otb7bfocsy7","GmI0q6wqdPAHQ6sinYpKG59ggd7Z4QrLScm8uMpp7O8=",-2362074064478027296,4523683486646041522,-8037350597120142696,1773354374864560700>()) {
                                       case -2093729919:
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

         switch ((int)com.yiyiaddon.m.b.a<"s17qb0196w47oo","NTyRVUU2DyL77Dvf1tCQektNfL+Ptp1X8tVXXYkZiFk=",-6315365398342704956,2580291529606903334,-1215981624750207879,-6437004035557765542>()) {
            case -1865548256:
               return false;
            default:
               throw null;
         }
      }
   }

   public record b(com.yiyiaddon.e.n.b.b.c a, String qO, String qP, String qQ, com.yiyiaddon.e.n.i.c a) {
      public String dk() {
         return this.qO;
      }

      public String dl() {
         return this.qP;
      }

      public String dm() {
         return this.qQ;
      }
   }

   public enum c {
      SAVED,
      UNCHANGED,
      CONFLICT,
      SPECIAL_STAGE,
      UNKNOWN_STAGE,
      NOT_READY,
      NO_STAGE_EVIDENCE,
      UNKNOWN_CROP,
      FAILED;
   }

   public record d(String qR, String qS, com.yiyiaddon.e.n.i.c b, boolean ds) {
      public String dk() {
         return this.qR;
      }

      public String dm() {
         return this.qS;
      }

      public com.yiyiaddon.e.n.i.c a() {
         return this.b;
      }

      public boolean be() {
         return this.ds;
      }
   }
}
