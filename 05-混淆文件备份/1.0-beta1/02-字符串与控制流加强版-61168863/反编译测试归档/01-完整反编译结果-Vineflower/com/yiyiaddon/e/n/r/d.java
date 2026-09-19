package com.yiyiaddon.e.n.r;

import com.yiyiaddon.e.n.i.s;
import com.yiyiaddon.e.n.i.t;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.phys.AABB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

final class d {
   private static final Logger p = LoggerFactory.getLogger(
      (String)com.yiyiaddon.m.b.a<"s17wozifa3xlg4","FdOIoC9XBv2g5Y1HOoh2rzg2A3DVfEvW5LxD9Gz63GGB4xA8YniEFvMdR828TQDO8F5mREBTjF+e4jSTGTM=",-480219892969829432,-1748210270642729118,-3995967608668518685,-8375292494313298054>()
   );
   private static final String uF = (String)com.yiyiaddon.m.b.a<"s1navqwv7hzzrg","5CfoRq+b0k0WoOJ/EhlhosbyHghIeYtqABvReCR+Qqk2WVAniVN075ZWHxA=",4265032894784843106,3992977297978114302,3157731017030958054,2164841461936830738>();
   private static final String uG = (String)com.yiyiaddon.m.b.a<"sqmgy1r0re246","K6pXAxTZrT/5AL3XSDMddZ9ZznMmyzZLr8s+TbFgBB96XKn3sk9Meg1Ca3RxGv7kqYKqgFTRzgk6t+5MAnit/tfC3f7AUpR5QLT98Q7xcDcddeLYWG/zwgKeLRg/k65m/hJLyvqORxGV0A==",-7985643465573358066,665042292209253854,-7542568707110726675,8559731459115685651>();
   private final b f;
   private boolean ee;
   private int oc = -1;
   private ItemStack l;
   private InteractionHand b = InteractionHand.MAIN_HAND;

   d(b var1) {
      this.f = var1;
   }

   boolean dV() {
      BlockPos var1 = this.w();
      Minecraft var2 = Minecraft.getInstance();
      if (var1 != null) {
         label31:
         switch ((int)com.yiyiaddon.m.b.a<"s2hgeqzzt6cx8y","zv2XAOL/Yu8bLVm4lsm1kWZ/ssUA5sxuGhkPNp4sotg=",3167525734608652285,-5375336495537792103,3110832001977900634,-4066733540594143769>()) {
            case -1566444114:
               if (var2.player != null) {
                  float var3 = Mth.wrapDegrees(a(var1, var2) - var2.player.getYRot());
                  if (!(Math.abs(var3) <= 25.0F)) {
                     label24:
                     switch ((int)com.yiyiaddon.m.b.a<"s327uxs8hnl3lg","3Omk0dcHsGiboxn8sI6rDO+i7cUCCEWbAcsh2agGj1E=",6750984954791413691,-7446432012647283670,8314091884223460535,7480834412943695423>()) {
                        case 858404331:
                           if (++this.f.nF <= 20) {
                              var2.player.setYRot(var2.player.getYRot() + Mth.clamp(var3, -15.0F, 15.0F));
                              var2.player.setXRot(a(var2.player.getXRot(), b(var1, var2), 15.0F));
                              return false;
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s3m0i97vej8jdu","yKi1w1jARZ/ZjtyysFi0zP0040ZcVtbTQoHIj4rIRno=",-6904727135743969683,-3439488276386242546,-1086945962505465403,1608985742434098862>()) {
                              case -93126029:
                                 break label24;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  this.f.nF = 0;
                  return true;
               }

               switch ((int)com.yiyiaddon.m.b.a<"s3d52avyv167wx","wuVml/7x71Y7CzT49H96Epi37DBPzixitR2MRNTZPjY=",-8506012449536358155,8741068739087212561,-61733666760975908,4292192012382721594>()) {
                  case -1621512674:
                     break label31;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.f.nF = 0;
      return true;
   }

   private BlockPos w() {
      if (this.f.a == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2dfoagd32q741","1RN4lMsVIUvZpqZBK4z38zLSfG0juXdTWqTq4OOjJlY=",-7009752639968686932,4617386953485878354,-303867467336619614,6195405981667740288>()) {
            case 1402095443:
               return null;
            default:
               throw null;
         }
      } else {
         switch (this.f.a) {
            case HARVEST:
            case LEARN_HARVEST:
            case CLEAR_DEAD:
            case CLEAR_MISMATCH:
            case CLEAR_JUNK:
               if (this.f.J == null) {
                  switch ((int)com.yiyiaddon.m.b.a<"sra35vr73v47p","Ilw3GFvrckItVOEOSJENBFPGrMueIMp1XZ+iiZh3tb4=",7095364589617641567,2814631955713742111,4670015787019369231,4895712319017684796>()) {
                     case 76494186:
                        switch ((int)com.yiyiaddon.m.b.a<"s3ohk4fqzwoxp0","b4kOz/PCdPQdfFF1KLRuaQNBhO3zSvYEFZYYGc8v38k=",-5798909225781537143,-3577460380172605568,4457678291085118586,7331586316053531289>()) {
                           case -1473672602:
                              return null;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  BlockPos var9 = this.f.J.above();
                  switch ((int)com.yiyiaddon.m.b.a<"s1fpusir6mjxn9","4inLVoLJ050Ckabm96gf+pxMq97qiY/cvvCQd8Ux3Uw=",-1363692985868176827,-1506359144182310221,-5699236142783912635,-18150882491901172>()) {
                     case 783921498:
                        return var9;
                     default:
                        throw null;
                  }
               }
            case WATER:
            case PLANT:
            case FERTILIZE:
            case POTION:
               BlockPos var8 = this.f.J;
               switch ((int)com.yiyiaddon.m.b.a<"sn6f11gcfjorf","xcA9D0ysDv5B72l2kZeK0YnR0oQunjdv74dss2YI4/M=",-5470703511609336772,-6742072589968706434,-3686215420591772595,8803774047250423839>()) {
                  case 413062875:
                     return var8;
                  default:
                     throw null;
               }
            case REFILL:
               if (com.yiyiaddon.e.n.h.d.a(this.b()) != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"sc5p4vpyt0yol","HbOA3stYdM2Z9Alj59HN8gYsfl7XO7Sza1Uv8v2Jvc0=",-1671763484222606681,-8064095298643259017,-8437429232551227659,2089221813583529671>()) {
                     case 1859001768:
                        if (this.f.a != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2qpjt1b754new","HOj1D60ZfWszaSCg7Yeq15LzdzqFTdppmojjizFz4Yo=",2326073689035062459,-2205804372127097055,-4982201490679825675,-359922196261460392>()) {
                              case -2025675404:
                                 BlockPos var5 = this.f.a.n();
                                 switch ((int)com.yiyiaddon.m.b.a<"s263xchba8xdm7","6fx0UtiaAJr2slCzf6PugN/VRI8RxOjV0nYvx6bUxGg=",-6208809029758249119,-8999080661463878223,4298780745235376619,-6448942888668701065>()) {
                                    case 837523156:
                                       return var5;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           BlockPos var6 = this.f.L;
                           switch ((int)com.yiyiaddon.m.b.a<"s2m1omu6qw2e88","jh8rYuY0dG9I2S/ogK3oqSFZeCYDdQZb3/ZvQIKnc8k=",-4333335789522827622,5211143688657953622,-5703953740385085220,5003461856259715366>()) {
                              case -411306530:
                                 return var6;
                              default:
                                 throw null;
                           }
                        }
                     default:
                        throw null;
                  }
               } else {
                  com.yiyiaddon.e.n.h.c.a var2;
                  if (this.f.d == null) {
                     label92:
                     switch ((int)com.yiyiaddon.m.b.a<"smm1q08ihzla6","aJGM2F/9KLSw61HMUGPTJljfkp+FPtHSlnpBSjcgCOM=",-7216850229984531173,3533268237137372171,643882522072238124,-3499361952431376659>()) {
                        case -1390289894:
                           var2 = null;
                           switch ((int)com.yiyiaddon.m.b.a<"s3ag5enbk7drgw","kAUO+V6YIqdr0IyZUDvb+flZcZ4uVC2442x31bqOXX8=",2409903197171531455,-887881412592027379,-1667341482595521372,-3141437700440109665>()) {
                              case -2084184740:
                                 break label92;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var2 = this.f.d.a(com.yiyiaddon.e.n.h.d.WATER_SOURCE);
                     switch ((int)com.yiyiaddon.m.b.a<"s1tri2xseujl5","q/Lbvic7Z4BrkO6QhyNiaVW39pF8lSbls7HgPcm17so=",-8695632981787302658,-8097822273214573014,4215791912724038724,-5764105393955598441>()) {
                        case -148106526:
                           break;
                        default:
                           throw null;
                     }
                  }

                  com.yiyiaddon.e.n.h.c.a var1 = var2;
                  if (var1 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3fy8e3wlej04n","jBQHRnL0tQOn9SKz0Pw+RdjodJnKHu2EcPE89IH5KWQ=",8812462625474206578,9099491773749755649,2570387773859443181,-7275176511289947402>()) {
                        case -1530609611:
                           switch ((int)com.yiyiaddon.m.b.a<"sqrcppb3ljnfy","QUDlHDw9XlV/Wn0j8PV9FNofn1Q9M+gxo4OWPvCzf8o=",-4642535469460294915,2283043611272489806,-7689118545979425901,-5531033195329043627>()) {
                              case -735170773:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     BlockPos var7 = var1.a();
                     switch ((int)com.yiyiaddon.m.b.a<"s13xmy6xwhqhvx","gzeZ+IevpXA+aDd1R/m6oG8/4HCFccupDe25nt5+SgA=",-6131752700201667061,1428542290507265407,-6579072572853311181,-2930834560623265671>()) {
                        case -834328139:
                           return var7;
                        default:
                           throw null;
                     }
                  }
               }
            case SPRINKLER_CHECK:
            case SPRINKLER_REFILL:
               BlockPos var4 = this.f.a.y();
               switch ((int)com.yiyiaddon.m.b.a<"s1gcejt7lq6zyk","mLGilQBEJBq+LKLJfO5m3hwzJ1KQ7QBs14jsqn7FiBE=",-3172028473920076773,4687573156951505547,-1240092705498201437,-5669950252254339130>()) {
                  case 934932215:
                     return var4;
                  default:
                     throw null;
               }
            case RESTOCK:
            case SEED_RETURN:
            case UNLOAD:
               if (this.f.L != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"swnfzddqstrlq","aV7OArOTn1kKWFAhn9B8vLo0cDF9F70d3BjHRYbzbKw=",7289481980618407784,-5839141459673039117,-7380208318872702446,-7154442591903250822>()) {
                     case -1862727790:
                        BlockPos var10000 = this.f.L;
                        switch ((int)com.yiyiaddon.m.b.a<"s2dngtb0lvfbyg","f4kmv7DUEaqKRmdV3+gRha/40OADdxe5cLOZWmGKJNg=",-8523159016274418295,-9171821567845541479,7853881719463794416,-4011584071589226640>()) {
                           case 1271867405:
                              return var10000;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else if (this.f.a == null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3kckwgskpj8kd","UY/s7IP/eZ86iaT9EBZVcXcgWKg625y5D441IC/vXyw=",-4529070916261681039,-3976134676456096802,-3133716192247639295,714013738585958717>()) {
                     case -1982180796:
                        switch ((int)com.yiyiaddon.m.b.a<"s1rdyhtvpakj2a","GLB2zhKdwXUDTXlnsCEqYd7bdgOygWi04aK0wv8FENk=",7826525143620559201,-951928973822151786,2287577071308748973,-7797424621077423141>()) {
                           case -1712795956:
                              return null;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  BlockPos var3 = this.f.a.n();
                  switch ((int)com.yiyiaddon.m.b.a<"smj7m6qhpv9z8","TJJurv7BVKpdy0IWGzbj+T0CSefe6j1YasxVeLA5yHk=",1496225860284982649,-5902408832069944947,8854522053925351735,-4230125968071840024>()) {
                     case 1581800606:
                        return var3;
                     default:
                        throw null;
                  }
               }
            default:
               switch ((int)com.yiyiaddon.m.b.a<"s2rk2jezsc5ljj","9ejj+l/ZtdTIQg+D+7goni/Lr3bso6X/naFa2QD1LSw=",7801966685489146296,-8866531972965995919,-8888301741404456502,1486735991705963325>()) {
                  case -1863597637:
                     return null;
                  default:
                     throw null;
               }
         }
      }
   }

   private static float a(BlockPos var0, Minecraft var1) {
      double var2 = var0.getX() + 0.5 - var1.player.getX();
      double var4 = var0.getZ() + 0.5 - var1.player.getZ();
      return (float)Math.toDegrees(Math.atan2(-var2, var4));
   }

   private static float b(BlockPos var0, Minecraft var1) {
      double var2 = var0.getY() + 0.5 - (var1.player.getY() + var1.player.getEyeHeight());
      double var4 = var0.getX() + 0.5 - var1.player.getX();
      double var6 = var0.getZ() + 0.5 - var1.player.getZ();
      return (float)Math.toDegrees(-Math.atan2(var2, Math.hypot(var4, var6)));
   }

   private static float a(float var0, float var1, float var2) {
      return var0 + Mth.clamp(Mth.wrapDegrees(var1 - var0), -var2, var2);
   }

   void hj() {
      if (this.f.lD <= 1) {
         switch ((int)com.yiyiaddon.m.b.a<"s1xg3nygvxshvx","65vY1cI4irJe7k54Cnj43wrUARK0OLhKmOmFam+D0lQ=",948166010945466028,9065040878835127332,-2126415495633531578,-4494479975154434475>()) {
            case -1094605464:
               return;
            default:
               throw null;
         }
      } else if (this.f.J != null) {
         switch ((int)com.yiyiaddon.m.b.a<"suuborvked2no","9QGqWZP/oWXjSX4H0hBVs+DQGHLy8yTHKoNf9wiEzPY=",4259473812689568945,9186266701656797522,-8659583652681002024,6797502915681750775>()) {
            case 2107692180:
               if (this.f.a != null) {
                  boolean var10000;
                  label251: {
                     if (this.f.a != h.CLEAR_DEAD) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1qaz009hkymfb","+UvFtW2cWZ5u+zm+Iv7tzktW5FjlJyJk/guVIg/ZCPw=",575072968253594796,5706148579695003710,-1566847267774796299,3273655444092930701>()) {
                           case -1164391422:
                              if (this.f.a != h.CLEAR_MISMATCH) {
                                 label221:
                                 switch ((int)com.yiyiaddon.m.b.a<"s908cqohtlqsw","5jWaN2u8bsfyn5NBsicMQNmZyntcm6SXbURsdrKKzeE=",2028316990571486464,8859924834351947411,1146351859164803458,-1970683040279751834>()) {
                                    case -477740506:
                                       if (this.f.a != h.CLEAR_JUNK) {
                                          var10000 = false;
                                          switch ((int)com.yiyiaddon.m.b.a<"sbb3vzyzd0wu6","D9RgKZX5/gZH/4pm5q/K2O3STm/0cR+lEN6CJHu4ApY=",495604408754461810,8781225181353800051,2554209209080662165,681255180194099087>()) {
                                             case 348800138:
                                                break label251;
                                             default:
                                                throw null;
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s2okat6bzxbl5p","KNc3+ZkdJsmRI3b63B5jEKa/YbckFLgRWJZbEAFVeNk=",5455079100991316577,2764297908371140890,1550028757190078497,-1304480197904705251>()) {
                                          case 699700318:
                                             break label221;
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

                     var10000 = true;
                     switch ((int)com.yiyiaddon.m.b.a<"smlcgw1jwuw7e","zp2NnlikOLNcOueixOzFUEZOp2r41iI0qT7zQYVzFAQ=",-7805453490004702061,1151146505074219444,8151160609720627912,-1202956476124928211>()) {
                        case -2045455505:
                           break;
                        default:
                           throw null;
                     }
                  }

                  boolean var1 = var10000;
                  if (!var1) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1zcsni2xuwjf","iG6kvxh6L5iVwxR40ZS9PE4MLJZi56dWQO6SmRv0j+E=",5221780616113277641,-5366298498469566455,2827518901264159059,-189703915731141039>()) {
                        case -4150729:
                           if (this.f.a != h.HARVEST) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2nnuznyb8ydnn","TNNhx1fy1PkoCp5Rpevv2NS+5+lQD67IGs1QG9P4HbQ=",-4816489130563377938,7603918653277323923,5126039353952509524,-7089483380028951501>()) {
                                 case 2093518561:
                                    if (this.f.a != h.WATER) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s36desrqz1ljhd","lZuhY7qC08HmpCA3TgCHZvulGP9Nky3ypCjRQKTlpUc=",-7334513030389032458,8804828450694748683,8370953571463029410,1382180533889532683>()) {
                                          case -2118969586:
                                             if (this.f.a != h.PLANT) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s13hl11xv0dztk","l20j7cAYVIlIxdvU6p7gh9WIQNKaaUoP4Qq0Im2wtBA=",3620177226078590000,3390538685537781421,5253175667757593640,-3491780145671317653>()) {
                                                   case 366907660:
                                                      if (this.f.a != h.FERTILIZE) {
                                                         switch ((int)com.yiyiaddon.m.b.a<"saj5pm3sope5t","R4rhTnDNl54/FFqvA8WKvBdQzzJosAMmbIfnxY1mCiU=",4050816316344505172,-8263019603902369521,7208654238100681577,6399355382476407523>()) {
                                                            case 1450377562:
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

                  if (this.f.a == h.WATER) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3n9ozx6tz6qqr","KOMBnitHdwX8AfkMPRMMKSpkgAJPL7/z8vMHUl9cgTY=",2043056139694458520,346985939631575434,-7993246725333920106,-4696050575126964334>()) {
                        case 305871809:
                           if (this.b().h() != null) {
                              switch ((int)com.yiyiaddon.m.b.a<"s1tua1xuoiwlu5","4/I5UaUVx9WA8JPwEOAQiubqyCwfz4DSAMw3xCDI54g=",237696959520423944,2657253698816408824,2554892728295504700,6320910197464452357>()) {
                                 case 725512040:
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

                  String var2 = this.f.a.c().dk();
                  com.yiyiaddon.e.n.i.a var3 = this.f.a.a(this.f.J);
                  if (var3 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"svl26x9x7vte1","/L//l6/gNAccx6kQvZb+JANGZ9plKzX0A6uAZoH3BwE=",-2850812042119946051,-2987106337684879108,-8564726719662573673,-7487568025111609996>()) {
                        case 2066191344:
                           return;
                        default:
                           throw null;
                     }
                  } else {
                     int var4 = this.f.lD - 1;
                     HashSet var5 = new HashSet();
                     Iterator var6 = this.f.bV.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"sj9qwfuwrwf2s","UxPN58O0fur3Y7PQgUfiLLv95uBsFEuQOdgJjL4nuvU=",-3429083520850102175,-2020644733368600008,4127841961346995093,1122133699276775562>()) {
                        case -1374664008:
                           while (var6.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s15hfwhxa9uzyi","Eq95fUjBr+sjriCsBKmuf8DcrTZk3tLq1PQfynqwwks=",-6626022370256932538,-1982652093640490559,249907568384212112,8343239910959997966>()) {
                                 case -1303524852:
                                    com.yiyiaddon.e.n.m.a.a var7 = (com.yiyiaddon.e.n.m.a.a)var6.next();
                                    if (var4 <= 0) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2p179lbacjl9v","bZp3AKEbi/k28/ZvgCJIsLBuvzr1eTG/LAeien9Bdzw=",-4053910291845226664,-3644695302062134834,-8635375311998473693,2265038351026952356>()) {
                                          case -1867094485:
                                             return;
                                          default:
                                             throw null;
                                       }
                                    }

                                    BlockPos var8 = var7.s();
                                    if (!var8.equals(this.f.J)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s2d5307r6646v1","WYI8d5OSJzmIL2cKj2x+N1xoWOL3yZPpHOoKc8xBfE0=",4880166183376962914,2465487371264387313,-3367325382924560197,-2159956943697110294>()) {
                                          case 54934915:
                                             if (!var5.add(var8)) {
                                                switch ((int)com.yiyiaddon.m.b.a<"sotkdlttryeq3","st9KxZPg3w8aT1iyY++4KLhtNcTGSVhEbXdkHavd1xA=",1081027480676725235,1921259967240281878,1673321215032068385,-4443377460909087570>()) {
                                                   case 2091344987:
                                                      switch ((int)com.yiyiaddon.m.b.a<"s38hgzax82np9d","CP8bGXqGexIfDLfhButGj4ileTCL3UjYf9Ql+jqM8jY=",-3396646419849465690,-8777073610486519937,-7217985614348467772,4661594605220497100>()) {
                                                         case 1614375645:
                                                            continue;
                                                         default:
                                                            throw null;
                                                      }
                                                   default:
                                                      throw null;
                                                }
                                             } else {
                                                if (!var1) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3b9njda3m02pz","wwh8cBBuVAGM/BU1icBROx1ylwZNMTz5DFVzTg1UAnw=",2241140202363638598,6339759030112745720,-3945650523883896696,-5167646638302104771>()) {
                                                      case -1963483352:
                                                         if (var2 != null) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s1zmbizse1o71l","p+E1bF8F8lp+/kyuj1sEYkI9e0TubxTkryYW+ywPsGQ=",243485499857432094,-2822827024390810059,-6221857747855660256,-3048116430690159510>()) {
                                                               case 1834732429:
                                                                  if (!var2.equals(var7.c().dk())) {
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s1sfb7fwwzfjiv","SjjFYk+3VyokrlihM4o9QmO1xqmpw0udyPT3MC5REcs=",-186752130554957679,7454168279715832978,-7622898457567814810,-1901208882529487499>()) {
                                                                        case 1881863072:
                                                                           switch ((int)com.yiyiaddon.m.b.a<"symoromygtb90","m59Af8+jdUAbo+KJYeabw8j1XosSsNyQMfiyyapsn44=",6597164266478874960,9019433878671714787,7398858077077524602,9186777994918301432>()) {
                                                                              case 577859448:
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
                                                         break;
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                com.yiyiaddon.e.n.i.a var9 = this.f.a.a(var8);
                                                if (var9 != null) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"sxp5wqfnhuini","so0HSYGFBeA8apX4BGnnCLPPa4YfwfBluK+wuj5V9FM=",3657259726380838851,8005924400789645120,1098730102520704359,6060304402945036646>()) {
                                                      case 1623144349:
                                                         if (!var3.dk().equals(var9.dk())) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s1iqhiwyivep3q","a98AnARRbczaHw6iZQijySoNRmLuXvq/aygqGEkTpMc=",-4318018455410904268,6476396759542778848,-2174779633848507185,-4421545224219491985>()) {
                                                               case 1360784105:
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s3ay9rxvz5bj2v","pDD+Te8rrSPArFsIPVQ/YgAKuYvqvoMK+cBXb0OFS/k=",6261804671494753992,-4390307925708882733,-717729095615933692,-5552695317678079027>()) {
                                                                     case -280961406:
                                                                        continue;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         } else if (this.f.a.a(var7) != this.f.a) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s1pozz1izhij3d","gGhJ5cbErCMycynMalVcbiGxrertOFIBRaR0/1YRIfk=",6546892494879984983,5526565406525373351,7167980185184474143,2363388564846459747>()) {
                                                               case 1489071711:
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s3tcr4r2vgf25e","8lR+Gt14mWHuL4Z880hffXGvc11dHKjQebC9FNh7odQ=",6359272874902703631,-5218463292942617040,7619549354424314969,-5923382904350244123>()) {
                                                                     case -2059974168:
                                                                        continue;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         } else if (this.f.a.a(this.f.a, var8)) {
                                                            switch ((int)com.yiyiaddon.m.b.a<"s2k69p7cc90k3x","M3wByKJVXgvCg0Ys/CyZfelpQe02qE3LshXDsssl7QQ=",-8501377586811551960,3241971142405465372,-1433597092454746567,1849674039545487203>()) {
                                                               case 1106731819:
                                                                  switch ((int)com.yiyiaddon.m.b.a<"sedzopf0tcnfg","8KT3Ic57384hGkxuXE8LL40fAxXWT0m4iOzAH6mS0Z0=",4607425130539525147,3416964680905262209,-1268191519729937863,7237255630287615794>()) {
                                                                     case -387622292:
                                                                        continue;
                                                                     default:
                                                                        throw null;
                                                                  }
                                                               default:
                                                                  throw null;
                                                            }
                                                         } else {
                                                            BlockPos var11;
                                                            if (var1) {
                                                               label148:
                                                               switch ((int)com.yiyiaddon.m.b.a<"s3ka2byexpbl2w","BR49ec/xFDt3ynuuWheWStNIs0D+0GZWR4zqt57HMnk=",3628070621956450564,-5988857023793888531,7443780767830055352,-3053850603204824263>()) {
                                                                  case -10235363:
                                                                     var11 = var8.above();
                                                                     switch ((int)com.yiyiaddon.m.b.a<"s37ivjgzxplzps","CDdfT3kf0Dc5QsJXmnUnKswR2At4HKFMJ6u7KgIRoxY=",2984676994257266185,-1024356052645172568,5993768231969362032,1975805969377193066>()) {
                                                                        case -1441869320:
                                                                           break label148;
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  default:
                                                                     throw null;
                                                               }
                                                            } else {
                                                               var11 = this.e(var8);
                                                               switch ((int)com.yiyiaddon.m.b.a<"s2gwbt5f8zh1rn","kN9wKa+9Eki/S7bfjZmPmYHMhXKzXsVs/GWM/QVa8lo=",-4574927661301281917,5953926440096132179,-7398343472716567266,-5570064693107730545>()) {
                                                                  case 2015344403:
                                                                     break;
                                                                  default:
                                                                     throw null;
                                                               }
                                                            }

                                                            BlockPos var10 = var11;
                                                            if (!this.A(var10)) {
                                                               switch ((int)com.yiyiaddon.m.b.a<"soywb4v82i78v","65V33KAhowVQeoAqdwMz6SPB7sR04VjmmJ0jCifoxEA=",5020387420438211673,-4649869555684380545,5013185308432422304,2065728784067119906>()) {
                                                                  case -2547354:
                                                                     switch ((int)com.yiyiaddon.m.b.a<"sldu3p33dpr7d","yUcpaqfvOzYQ32HB+Bt+OG2J7h+4SrGHROruIJ/WSWQ=",-7624044095306115277,-1186170302791384685,-6898721111239387370,-8863954330368363940>()) {
                                                                        case -1544553391:
                                                                           continue;
                                                                        default:
                                                                           throw null;
                                                                     }
                                                                  default:
                                                                     throw null;
                                                               }
                                                            } else {
                                                               var4--;
                                                               if (!this.a(var10, var1)) {
                                                                  switch ((int)com.yiyiaddon.m.b.a<"s3kg89uu0vrw7t","z6l2DBuVMvkfPki3qCyHT2/alq7NJzL4WYlO9pY3yHA=",8311821227858545662,-8929174145287103809,-1917628902889258456,-3253814341544620693>()) {
                                                                     case 1575373493:
                                                                        if (!var1) {
                                                                           switch ((int)com.yiyiaddon.m.b.a<"s2a0hx5pm5f6he","2lzP7BGdakmbeNIP2JGrXTNVDk2XAwpfcfoPRcJCZ18=",-7387885592644166,-5702364981261051822,3952266363800753205,7679109539206699700>()) {
                                                                              case 1477880011:
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

                                                               switch ((int)com.yiyiaddon.m.b.a<"srma5mr38zut8","57F7bZL3M9xoIiUly4EJuqzCeU2oH/YS727Jgs4InTw=",6774299083079247109,-352555146879963153,2521864386062704103,6153280211874371427>()) {
                                                                  case -69124315:
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
                                                continue;
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
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"snicu9vopj85m","vLwSv0WbAqMReY9TW0JwG3M8PzYnK7hQ+7rS9FCOVi0=",-6417444401177991866,-3189301392356342327,-3126072233679027065,-5774292504328264479>()) {
                     case -1471003011:
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

   private BlockPos e(BlockPos var1) {
      if (this.f.a == h.HARVEST) {
         switch ((int)com.yiyiaddon.m.b.a<"s1k1y09l5djgep","3giNDjS2cTJ/T4+pHj8pnBXl2/7E0uvu/rToPkuRKBY=",-6277006911619611285,-7817350863675064850,-6252462061858932577,7775547291884361563>()) {
            case -1046307873:
               BlockPos var10000 = var1.above();
               switch ((int)com.yiyiaddon.m.b.a<"s3adke0tjbi0xl","rsxF9/B9p3kCuPshpTA8S4vFZrirDcO+zfJSsQrRqIY=",6405298688914748054,-3413520578866145307,-1419676706550177656,-8097161435836040396>()) {
                  case 2088470393:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s35ax38faczy03","DJFcWm+wPWfd6m+Neu4fNcXv6pFCgN/dmxvaVVZRwcQ=",-5358800470480238839,3730383963190590302,-3882826836561260307,6368329823924401420>()) {
            case -1073889927:
               return var1;
            default:
               throw null;
         }
      }
   }

   private boolean a(BlockPos var1, boolean var2) {
      if (var2) {
         switch ((int)com.yiyiaddon.m.b.a<"sx3nbodpp9myx","53WTWwGndvkZ5nnj8qW28x92mfQjhO4MqdRahHqxU1U=",7012611668146891877,3692619936868600949,-1672272253520807438,7795318232820032554>()) {
            case 334594714:
               return this.f.b.d(var1, Direction.UP);
            default:
               throw null;
         }
      } else if (this.f.a == h.HARVEST) {
         switch ((int)com.yiyiaddon.m.b.a<"scdcd3mozepls","G+ao9h+wLYjEGIu0EOAiVWI19BqDt1WPskFOsHyFhJU=",4489201961852630666,9036421036201136691,-7094665915845325596,3252689387441455999>()) {
            case -2126136236:
               InteractionHand var3 = this.b();
               if (var3 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s38qr8999vtpim","mUB/lvHJM6wCmenyIahkzTYUNmLFz1HiiXNJp8NQfOI=",-9116028554527966907,-1974118482546029788,-1303657732240893635,-4526868404032840896>()) {
                     case 310711835:
                        if (this.f.b.a(var3, var1, Direction.UP)) {
                           switch ((int)com.yiyiaddon.m.b.a<"sehsvdt12ouxr","qOmkRi1hVowM/eSiB6ZouOp3FCR7uAZFj1UKgsDQlMY=",8474807548420911733,4316780571280459214,-7339895567490243966,1075924106380200430>()) {
                              case 953066665:
                                 switch ((int)com.yiyiaddon.m.b.a<"sobmu7qkqgdqj","Smn1u0W5Bbttds6mYBNhehDdHy1VOmPIDINpQj77GxA=",-5881644357459429517,-2116984738106486609,-2686053842939424518,7487154702677031069>()) {
                                    case 1530540933:
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

               switch ((int)com.yiyiaddon.m.b.a<"s34bfn5d08eygf","T9a+NAACMprmyqPdQtJrnVotrt4XxubXafRAlXUNjpE=",4415867715931766714,-1944221097925960918,-6573918565334688632,-2558991739276027511>()) {
                  case -1591038657:
                     return false;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return this.f.b.a(this.b, var1);
      }
   }

   private boolean A(BlockPos var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3gp705i7ug16a","9NIyBp1LfHCHXMz/m/id9CH03CZhDykeXSjC2yfpBK4=",-2588002499219612222,-5499584342463702605,-1775942029815688590,-8843567886125039525>()) {
            case -1134003230:
               return false;
            default:
               throw null;
         }
      } else {
         double var3 = var1.getX() + 0.5 - var2.player.getX();
         double var5 = var1.getY() + 0.5 - (var2.player.getY() + var2.player.getEyeHeight());
         double var7 = var1.getZ() + 0.5 - var2.player.getZ();
         double var9 = this.f.lA + 0.5;
         if (var3 * var3 + var5 * var5 + var7 * var7 <= var9 * var9) {
            switch ((int)com.yiyiaddon.m.b.a<"s3bu8gwndkisky","l24UCAdn9GaPTjuqk8S8Bi+MNUg0fZhVvWR3n1ct/fw=",-1859061664022239039,6031219549590840012,-656583990895633579,-9093879094940565077>()) {
               case -1428631697:
                  switch ((int)com.yiyiaddon.m.b.a<"s26ecbxunvjbp0","ZnnyJ5/ZfvG/RBfRYIBo6EmEGMnvpgHyVUjnlt77hK4=",8319271334240202102,6742478906860949725,908006944233342427,-2307728683971679075>()) {
                     case 908769466:
                        return true;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            switch ((int)com.yiyiaddon.m.b.a<"s3i41ue81nirey","tcLGyJ7cDbesHXNr6KrChtKt9I7CkkPl8XZgmujrAnQ=",1716072149591653451,5976150770977647291,636244975538682585,-4670608382712304129>()) {
               case 391089434:
                  return false;
               default:
                  throw null;
            }
         }
      }
   }

   boolean dW() {
      if (this.f.J == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s122xcjjknxxbt","AuBgb7L6uN0dpNolA4vyZjFU3C2QwOY2qVa0AYisM9U=",-5737966013174183049,-8874384391004777332,8394410853009505560,-2936269530652100305>()) {
            case -1621107726:
               return false;
            default:
               throw null;
         }
      } else if (this.dY()) {
         switch ((int)com.yiyiaddon.m.b.a<"s20xxhin0d5mk","61oOP8C6M8e8EQYTmsX7tnR8J13jaqsKnUqEadWPnnQ=",93834407026903314,7339460755494343664,1495813050194178619,7437710326607225002>()) {
            case 1022926043:
               return this.dZ();
            default:
               throw null;
         }
      } else {
         InteractionHand var1 = this.b();
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1vyx34up9rs25","59e3mpKul+Zp/f/Cytzj8o2ASh6lmkAV5XtCdpP/L/U=",8235646838335142836,-7947190362325149591,-6670900847373370230,2997239918038607247>()) {
               case -504995182:
                  return false;
               default:
                  throw null;
            }
         } else {
            boolean var10000;
            label58: {
               if (this.f.b.v(this.f.J.above())) {
                  switch ((int)com.yiyiaddon.m.b.a<"sa5e3t0cgxjqz","CrhRDFlSehidMTHRE9v2phPqpntIAWURjV8gz7N1A1E=",-3861445608502041316,-9087669757520279553,6000001398517048610,-3299244308672223159>()) {
                     case 153136430:
                        if (this.f.b.a(var1, this.f.J.above(), Direction.UP)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2crzoyhgbiobd","t374l59fOkhLTQ3D2ggmGEnXWKe9OUj1WJBtlW8vn1s=",-1326372104760998129,7724912605154314801,-7040092699106455285,8262325138368253272>()) {
                              case 86838403:
                                 var10000 = true;
                                 switch ((int)com.yiyiaddon.m.b.a<"s30rric489mhrg","Hl2d7RGnTL9nxLsHQnJbmeO1GSitEdVFuGQ/q+PRjdU=",-7176947052020519886,-8096025217971947005,5761497881528270006,-5650991532139595336>()) {
                                    case -290727373:
                                       break label58;
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
               switch ((int)com.yiyiaddon.m.b.a<"s2rmg38m37ujxv","0037p9+Xm6GhDCv5ZVgIkC6PT4uXj0E+2nUv3yTHJek=",-4211149094408954691,-3612302885908708679,-1092386491050595855,3112990458329826387>()) {
                  case -1668488457:
                     break;
                  default:
                     throw null;
               }
            }

            boolean var2 = var10000;
            Logger var3 = p;
            String var10001 = (String)com.yiyiaddon.m.b.a<"s3kkwk2q1p4jy9","NiwBMB1llgXT8e59lEzHni2HEniEIdt5jXWWHSdY+/akJ516E8brqhnNczICO9T/GyvYuP8JM4V0NEltND+W5bsIrMXQrKx1BoE13QmR0IRVf2zkEsSPjw==",6678774516351800011,-3722436011003456880,-7429606514812175549,-4470805606889999245>();
            Object[] var10002 = new Object[4];
            String var10005;
            if (var1 == InteractionHand.OFF_HAND) {
               label40:
               switch ((int)com.yiyiaddon.m.b.a<"s1r68j64xjlwa5","HRSif5g4OGmIzZh6sm9CR7ZHihzIsnOlNm8OQJliing=",-1031737492766850711,629163836074020315,4594450463920373971,5619859112546597618>()) {
                  case -92592639:
                     var10005 = (String)com.yiyiaddon.m.b.a<"s1g93habcdxyhg","FLhzGqMRNELpxClRPaa200Ya0hYOH/KOmaMObusxpvI=",-931973716463054806,736942517095236676,-8425182829029271140,7877706688713759587>();
                     switch ((int)com.yiyiaddon.m.b.a<"s26nanzdkv7of6","c2KDvdmq7D77GLF5ELp/cyl3fup129+Z2ypR6t/v1Ho=",5940905383716584663,744460275968232636,-1636421666267264150,5957035491492939128>()) {
                        case -524837224:
                           break label40;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               var10005 = (String)com.yiyiaddon.m.b.a<"s323rppsyvsprn","oP7zAVy9gDoS3pFRAfGQIUSBSHT2F7orIqapjLFcT/k=",7060279698907395764,6281303994928852133,-6013075816555516737,7499078946536679174>();
               switch ((int)com.yiyiaddon.m.b.a<"s2m5y7a0wowo5c","L6aGMUWkxEJ7kXABiIORIn+begzs7CnSGpztDTaj2zw=",-9051191497050363669,-7618573479587345806,9193108080903779961,8321338917173541302>()) {
                  case 1925439815:
                     break;
                  default:
                     throw null;
               }
            }

            var10002[0] = var10005;
            var10002[1] = this.f.J.above();
            var10002[2] = h(this.f.J.above());
            var10002[3] = var2;
            var3.info(var10001, var10002);
            return var2;
         }
      }
   }

   private static Item j() {
      return Items.GOLDEN_HOE;
   }

   boolean dX() {
      if (this.f.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3utbzfhi93ka6","8bWysJT5UCYam/zSQRd4POsZ6MBJEWnoLXOBG6UgYK8=",9131778458394102056,6829282581915758410,-6409944248497610536,7341272632650737799>()) {
            case -710202001:
               if (this.f.a.a(j(), false) >= 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1xo0zw9foqy5c","37zlCGcWyPhL+7ABfyXeJRoEotRg2X+C8Ux1w6aMAqQ=",-1889928706220199327,-2030103044529418936,-4609160358771804805,571594681923687387>()) {
                     case -1746228434:
                        switch ((int)com.yiyiaddon.m.b.a<"sr9uk1wz04l4s","6TBsNMJJ4Ye3G6muD7DlJBq554cx9UOKVqRHiEPs89k=",-5678819881216093633,-4073260031350598553,-6533317989374231031,-5305473970553609126>()) {
                           case -238640196:
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

      switch ((int)com.yiyiaddon.m.b.a<"s37gsw3tes7m59","XTZWUHoMknh7CH0OaJikrvpa/dDTEhHwPGdCJ/5v4Jw=",7812260677901604312,7158604814068632856,8653592276951335559,4829892914245954443>()) {
         case -593997585:
            return false;
         default:
            throw null;
      }
   }

   private boolean dY() {
      if (this.f.a != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s17uzv3zf50asa","E/TrXYKBcMfq1I6SHqgp+XnpHqTTKynTsQnZW2Kavck=",7648188073411327063,7290286797821789977,2092561151356599707,-6524368361215386860>()) {
            case -2071761229:
               if (this.f.a.c() != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s156n4nfoas5cp","vucXYuU8dJCUZvpVOFoIYG201q4qOVe4q67QoVxiEMI=",-3981132272668821427,-2256354559045920175,4796964292257150968,138142941987450985>()) {
                     case -1453169491:
                        if (this.f.a.c().a() == com.yiyiaddon.e.n.j.d.SPECIAL) {
                           switch ((int)com.yiyiaddon.m.b.a<"saz7u2hit6e2p","P+Kd6sD8GnzKAId9tHBetNJlw940nq4byzgLk2NuYKU=",1217436207801603157,-7822646088199674244,1127260147892665687,-4497448154284612210>()) {
                              case 730746728:
                                 switch ((int)com.yiyiaddon.m.b.a<"sbyu2e18kv4c4","h0bXKoQ2uJVbUttpSzarhRxumekKsY/sXrUU9l6QWWA=",-3179391559150390809,8377387785035882179,-1936669447026969766,-4877976684472469110>()) {
                                    case -1306799672:
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

      switch ((int)com.yiyiaddon.m.b.a<"sco8h19bwsh3g","lyS0TB05qFbVDhLCrjexEK3Bctd7VbWOrwgFd3SvyBc=",4137120713254284253,4201717478111200887,-9119361985516463923,6727762211968069841>()) {
         case -1714294912:
            return false;
         default:
            throw null;
      }
   }

   private boolean dZ() {
      if (!this.e(j())) {
         switch ((int)com.yiyiaddon.m.b.a<"s28a62t2xh4x49","xDmIdB96JfYxGy2ywBKLuPEoisZJgYvefDeAvUwBPj4=",-7646227424339553948,1230482074470198958,-4166193253460559179,5470253022346909802>()) {
            case -1525902792:
               this.f
                  .c
                  .c(
                     (String)com.yiyiaddon.m.b.a<"s2tydcr939wmo5","Au6t5NJTX4HcA179ajQA6EG7Hq6B5hYBo6LO/lO/NnbppZtLTUYQ5BHnijNcFerMf2sE2DiGdQJWaQ==",1777796167535982173,1325453094675012879,-1281032196153264917,3185139026715259493>(),
                     (String)com.yiyiaddon.m.b.a<"s1wm7tlnajg61d","3FmMkf8CzNt6bQIqVHzSWS6GwWK4z27I22BaJ9gcu0aH6+u8ptaEDEnceEH09g==",2684341060929121998,1830172399021391008,-7403726312170106189,-1369764939066524938>(),
                     (String)com.yiyiaddon.m.b.a<"s1ep0s2bhadwq8","pgrD+sqEqz93JlfZyIrCzOzLEakxFC2buMSvQIO0AVbB/8ORt5ASISqcaA28N/dfqYb7W3f3",516118626354576266,2869690649340751480,3616091320648028937,2937411114901405524>()
                  );
               return false;
            default:
               throw null;
         }
      } else {
         boolean var10000;
         label47: {
            if (this.f.b.v(this.f.J.above())) {
               switch ((int)com.yiyiaddon.m.b.a<"s27yjl76zdjdp5","d5lQ7Z3Ngh/TZCXgQIy8OyoeQCWTB6xc/tsIYocjk8k=",-7523676354241797745,-3952480126160348269,-5934027905671345573,2083771611599356278>()) {
                  case -450180758:
                     if (this.f.b.a(this.b, this.f.J.above(), Direction.UP)) {
                        switch ((int)com.yiyiaddon.m.b.a<"sl05rq4ihib2x","hVCCSQY9+OrVE8plw1knQRa4NoNWFHsBirs1x8ZuguM=",-1862131920906382406,-6680486101164495296,4078366712447710492,4822888880535002350>()) {
                           case -718088084:
                              var10000 = true;
                              switch ((int)com.yiyiaddon.m.b.a<"s5zf8fpwh4ocs","JOJpQiKkgg4u251MOv50ydWgWo1UtFANEQnSDYm6RYI=",1523224618944489797,5385042490748542841,-6473243000687539935,3358308818265981974>()) {
                                 case -1291584757:
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

            var10000 = false;
            switch ((int)com.yiyiaddon.m.b.a<"slsdodgh63h8d","dvGYLcowkPGlDNybNNrCvk2AGzIKEclKkUUKAtNROsA=",-9067115343216057951,-1295172031100265024,-3700361118508591654,2270788818528209383>()) {
               case -158548981:
                  break;
               default:
                  throw null;
            }
         }

         boolean var1 = var10000;
         Logger var2 = p;
         String var10001 = (String)com.yiyiaddon.m.b.a<"s2q6vgi52vnsn2","R78IRea76q3byWAVT6eA7rjBQyrw6qvdtqrgsn7h6M8uUXSAJV1Oi3EqHtM1xrSavogc7rbIqr+nShmB4WsI2f5Sxa38NMXdxKK5qUCXFaKi/upjdZfugCco3MynWZ4Wcu5GENErXt6RfLCQ",-5163415298502214468,1232216310608351799,-4764772520478387410,-4047975627904760559>();
         Object[] var10002 = new Object[5];
         String var10005;
         if (this.b == InteractionHand.OFF_HAND) {
            label30:
            switch ((int)com.yiyiaddon.m.b.a<"s1odwsmlm36j4p","I+xsMO2+ecravUkfiQ/2e9W2x4fsXgqKhux6h/YFK/g=",-3129531224412722551,-4419295987757959744,-8262864063881907803,2252615769512585945>()) {
               case -545878418:
                  var10005 = (String)com.yiyiaddon.m.b.a<"s1g93habcdxyhg","FLhzGqMRNELpxClRPaa200Ya0hYOH/KOmaMObusxpvI=",-931973716463054806,736942517095236676,-8425182829029271140,7877706688713759587>();
                  switch ((int)com.yiyiaddon.m.b.a<"s3eo5qd9w2sv60","O1psqATVSy2tiadTRmJcSW6bMjvsgVC9PLR+UdQ5GuU=",-9184108992560493904,700491401432909490,1778471241616288544,149225188976606608>()) {
                     case 1834435660:
                        break label30;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10005 = (String)com.yiyiaddon.m.b.a<"s323rppsyvsprn","oP7zAVy9gDoS3pFRAfGQIUSBSHT2F7orIqapjLFcT/k=",7060279698907395764,6281303994928852133,-6013075816555516737,7499078946536679174>();
            switch ((int)com.yiyiaddon.m.b.a<"s2tyxnmbo2bs6d","vMng/5ik3ScpqZyS7mHovHuHtbyI1crXgWMrZtCSWCw=",-4939730114494679175,-6443172819768468137,5930428843166651983,5338635717780037892>()) {
               case -1559775204:
                  break;
               default:
                  throw null;
            }
         }

         var10002[0] = var10005;
         var10002[1] = j();
         var10002[2] = this.f.J.above();
         var10002[3] = h(this.f.J.above());
         var10002[4] = var1;
         var2.info(var10001, var10002);
         return var1;
      }
   }

   boolean ea() {
      if (this.f.J != null) {
         switch ((int)com.yiyiaddon.m.b.a<"styc58k1nx37n","IPUdJLe2rIV0In3XKSXoPNS/opM5Wm1DAguxW5JgWYM=",3292036074341198446,-6306491750313355422,1828149463774544843,-2351400495155204429>()) {
            case -215596427:
               if (this.f.a != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1dwpcjfvr2mrs","FtbaZ/4Topxvli5ukDNj9zkbzqOBbb+8COTv46xcT9w=",4182792404578025128,-13666673784492345,-3680939735368502466,4741204997552394875>()) {
                     case -1442434111:
                        if (this.f.a.c().dk() != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1ddvnvub0b5tb","/jRf0VLDHuK/8V5BqD7HQJrBPJsE4hriP7S1GSEg3Ko=",256609342774652920,-1492090511702890127,7466395067554752091,-3122310085776844383>()) {
                              case -854472154:
                                 if (this.f.a.c().dl() != null) {
                                    String var1 = com.yiyiaddon.e.n.r.b.p(this.f.a.c().dk(), this.f.a.c().dl());
                                    if (this.f.ag.contains(var1)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3sn7cd8o45hlp","d34LsUSy9F6oxiaJ7HuBW4vHfazSDzWXR8Fi8/f97Fw=",2614388220558049235,6496785144589318841,-2859515661596265243,-2596706884381631954>()) {
                                          case -906744798:
                                             return false;
                                          default:
                                             throw null;
                                       }
                                    }

                                    this.f.a = this.f.a.a(this.f.a.c().dk());
                                    if (this.f.a == null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s150fw0pok6r1","ndi8GVSF8tYXPoxtRmlj2nfVvDs0228UiPdX3j8bgC8=",-291303681250186336,280089339257302365,6001633529447493590,-6580520053937252969>()) {
                                          case -1954959514:
                                             return false;
                                          default:
                                             throw null;
                                       }
                                    }

                                    InteractionHand var2 = this.b();
                                    if (var2 == null) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s28u3pyl17uknn","QF3Ho8BvRTpN5WKjBhlB9Ls5ESlFmEO6kweb2yRgZ+Q=",-1944774363077611686,-6892366497143250522,-2401487314357003829,-31777314749673363>()) {
                                          case -903026344:
                                             this.f.ag.add(var1);
                                             if (this.f.ai.add(var1)) {
                                                switch ((int)com.yiyiaddon.m.b.a<"s11k4e1o3wn2o0","ZugPuzKYWt3zkOhuu0MEmtDcdKXp7EoR7y8SzkuJMbY=",5370853733304565889,5204686044656091415,5844542380728202564,5420272128930013464>()) {
                                                   case 229026384:
                                                      this.f
                                                         .c
                                                         .c(
                                                            var1 + "",
                                                            (String)com.yiyiaddon.m.b.a<"sfynybyzbqv82","w1KN9hQdP0LheAYg5OtfyHchSB4wYANEfyX8cjCXRHOHDJRZa2QjZTrl",8235328175347810081,-4218024967665716753,2443504603393097269,-8309746061824293003>(),
                                                            this.f.a.dA() + ""
                                                         );
                                                      switch ((int)com.yiyiaddon.m.b.a<"s1hc1j15kjhtt6","wLZWRfXp7nf3+ecsTSSAc7ImGD6hKYm6rg5p5LKo670=",7185735693608334216,-7388115653897622649,-5986072865758315119,1789692665917550548>()) {
                                                         case 322210648:
                                                            return false;
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

                                    b var10000;
                                    boolean var10001;
                                    label89: {
                                       this.f.ag.add(var1);
                                       this.f.uv = this.f.a.c().dk();
                                       this.f.uw = this.f.a.c().dl();
                                       this.f.ux = this.f.a.c().dT();
                                       this.f.nS = this.f.a.a(this.f.a) + this.f.a.b(this.f.a);
                                       this.f.nT = this.f.a.a(this.f.a, this.f.J.above());
                                       var10000 = this.f;
                                       if (this.f.b.v(this.f.J.above())) {
                                          switch ((int)com.yiyiaddon.m.b.a<"svu1oj0j1pjbv","JDUPdy2hhYJfRndrqstXgaafSIcetKKlY6fpiMwX7yQ=",3723933008337646099,5335894521490288157,-4929795204502530449,-3570131732211480139>()) {
                                             case 1133559010:
                                                if (this.f.b.a(var2, this.f.J.above(), Direction.UP)) {
                                                   switch ((int)com.yiyiaddon.m.b.a<"s29k3w4l19mee8","yKRaSUrI5kk46c/pQYEsFTw7AouSVGdhJcquS4+ZczM=",4527695992678476113,-6057815477604141732,4160498803124628929,3719069063068200044>()) {
                                                      case 1661898931:
                                                         var10001 = true;
                                                         switch ((int)com.yiyiaddon.m.b.a<"sngkx50z4oyhj","O78aSbSsQaiLeyhli1ja740CHhgtiK8xh64XGdfPmwE=",-1340924387730065748,-2121942810979787672,1170480761168063274,-7113205841010679769>()) {
                                                            case 1696350999:
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

                                       var10001 = false;
                                       switch ((int)com.yiyiaddon.m.b.a<"s22jjyoheebjq","BHeC6+tTi5Jz1jgm0KoKvjDvqoXI7ZQ+CFmndOSl2g8=",4801081741678166876,8860445381765253091,-4019205274713605968,4469492517712667549>()) {
                                          case 364127179:
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }

                                    var10000.ea = var10001;
                                    Logger var3 = p;
                                    String var4 = (String)com.yiyiaddon.m.b.a<"s141dcwq9gwptg","5DGPJbUG8lT4cQxb7o0dLbX9tAG4GIQrLI29yA+anRfafaUYekHZc9lrC2IL9cTEzk+0A3P6iX0xz+KPbHhg4kskjwxGyRdV24lerEp5Nt2ydS2fjsUCJ83aWo0=",7540679243488189484,4754271389357533480,-8723126776637312231,-9194534290328384029>();
                                    Object[] var10002 = new Object[4];
                                    String var10005;
                                    if (var2 == InteractionHand.OFF_HAND) {
                                       label63:
                                       switch ((int)com.yiyiaddon.m.b.a<"s157qzp3a5tdka","Vb5kXvADl53xC4Q+okdxGXMWMfOnTicjUReBIJsRtS4=",-3014702240398476443,-7143009829577793650,8976595054273584424,-4751121232464769819>()) {
                                          case -779209840:
                                             var10005 = (String)com.yiyiaddon.m.b.a<"s1g93habcdxyhg","FLhzGqMRNELpxClRPaa200Ya0hYOH/KOmaMObusxpvI=",-931973716463054806,736942517095236676,-8425182829029271140,7877706688713759587>();
                                             switch ((int)com.yiyiaddon.m.b.a<"s1w701gnewgui3","1GoCG6uHYL+T65i/XzpWNy4+tX2l8d8BciMs1gkPURs=",4497023795891320816,-3112648365188353938,-959096575216852668,-555769964251950590>()) {
                                                case 750213337:
                                                   break label63;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       var10005 = (String)com.yiyiaddon.m.b.a<"s323rppsyvsprn","oP7zAVy9gDoS3pFRAfGQIUSBSHT2F7orIqapjLFcT/k=",7060279698907395764,6281303994928852133,-6013075816555516737,7499078946536679174>();
                                       switch ((int)com.yiyiaddon.m.b.a<"s2bbwchqr8cz92","yUws5KaK3JM1RQHtOxr6H7nXvjgxyLIIpYjOoaHvEh8=",6325940565701710362,-7817554256158814255,2902446704056519599,7598152790311180951>()) {
                                          case -2047174108:
                                             break;
                                          default:
                                             throw null;
                                       }
                                    }

                                    var10002[0] = var10005;
                                    var10002[1] = this.f.J.above();
                                    var10002[2] = h(this.f.J.above());
                                    var10002[3] = this.f.ea;
                                    var3.info(var4, var10002);
                                    return this.f.ea;
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s1zlu23gzyxy2v","/+A1nOf1RaEgK/sEgYidDGIHSvfHqrNRNzkSOAq60d4=",-5718206538401661659,3717952127208057827,6614129933254113490,-8401060642436994259>()) {
                                    case -734829240:
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

   private static String h(BlockPos var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s37lx08ezas02","r8JC0dJrElO7AKo8//EQ0mVOwDlcYV9X1qxg71izKZc=",1958256682242689453,-2524325583991344974,-7771414123781090399,-433008925904652316>()) {
            case -550865805:
               if (var0 != null) {
                  Identifier var2 = BuiltInRegistries.BLOCK.getKey(var1.level.getBlockState(var0).getBlock());
                  StringBuilder var10000 = new StringBuilder(
                     (String)com.yiyiaddon.m.b.a<"ssy50bne0u9c1","Oxg07fF3zxJ3mZY72zGpMrRI5qs1Pq54i95Mg6UzJWzDkA==",-7702136191240906010,7269080924132054489,5309137034538765007,-615633738054672709>()
                  );
                  Comparable var10001;
                  if (var2 == null) {
                     label96:
                     switch ((int)com.yiyiaddon.m.b.a<"s1wxgtas0lufnd","jK4a2sxyoXRWLSsiKzNdl4ygdhDC7XxoTWxFTLwaWgE=",8706720205620589324,-3355018117481058447,5703465764119171106,-8433022805968650258>()) {
                        case 1588175507:
                           var10001 = (String)com.yiyiaddon.m.b.a<"sdlcp3oawgp5","lIx1VfIDx4JBy2nV5Ds/mcp0++3NqkTSK9GUSrQUZGw=",1369778464688555876,6330499824598191721,-7152588652161772928,-8678263043177448747>();
                           switch ((int)com.yiyiaddon.m.b.a<"s21q51591kk5f9","6ddXeWPoXWiD48ok6v6oumpRDrF1j43f+6m+Te86RAE=",1592014394570074141,-3406055024786857079,9143176193003002245,6748436186425482604>()) {
                              case 46508345:
                                 break label96;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     var10001 = var2;
                     switch ((int)com.yiyiaddon.m.b.a<"s1pndqzrccdvgz","Sik2sNwXQ958LsqGwlwD0+DSyblodszWQ+kT0akfZz0=",2582750725201209380,2176332216752130480,6313691497717848405,-5359894029555095376>()) {
                        case 725643509:
                           break;
                        default:
                           throw null;
                     }
                  }

                  StringBuilder var3 = var10000.append(var10001);
                  int var4 = 0;
                  Iterator var5 = var1.level.getEntities((Entity)null, new AABB(var0).inflate(0.5)).iterator();
                  switch ((int)com.yiyiaddon.m.b.a<"s2q7a31vsv3glr","dpDbEawLO6g24+bKxyucqRhghGESpR2N2W+TyWCNLUM=",-7619223407961873033,5012425020437797582,2770652853307037633,557667134817922233>()) {
                     case 292931381:
                        while (var5.hasNext()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3grm34qm97zdv","N3UlyCmsKurpPZ46YTviqymIT9yV2RIqFbDfldSRM1w=",8203316572256817022,4691118530788946535,8898562044144877876,3122661527811544816>()) {
                              case -2111812795:
                                 Entity var6 = (Entity)var5.next();
                                 if (var4++ == 0) {
                                    label77:
                                    switch ((int)com.yiyiaddon.m.b.a<"s398cjaj5f1xs1","ztaTZ4qyXWxE40ka0fJJdTiOAWP6JFLMAVRfS2ErJLo=",-7839081585189904689,-5827027133404696536,-1249944887058981257,8615170635115686519>()) {
                                       case -490644702:
                                          var10001 = (String)com.yiyiaddon.m.b.a<"sg0y6pvu8t3yi","U9sFPAdOYuB3Zv8zSEYU4Ui55tn+0imUP6WNTNHx4EvCJxhOIouWFg==",2796703653292913360,3162628197729009377,-3076130630859511185,9202783707268417495>();
                                          switch ((int)com.yiyiaddon.m.b.a<"s1498n02ibvo6z","7UKVHW3oyRbeMx1RtuyyOvStuFcecOUc781qkIM8EL4=",-345418982227376282,-6041050765550524554,8733660452210426566,-2860870448774730660>()) {
                                             case 1553750903:
                                                break label77;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    var10001 = (String)com.yiyiaddon.m.b.a<"s1hvdeyg86urb6","2v/EjB8yZkYMp3fvrUDkp+NxFPwfTQAAEtF08sHl",6690813304375903622,1333264969367882802,7228191775323101239,-8168772963263449173>();
                                    switch ((int)com.yiyiaddon.m.b.a<"s18ljhlxtnktpb","G4bWyl5Pmiw/Esgdy0AOntsNzpE2P5VJCvnWIVY5q3U=",4442284489107196064,8444112573489200410,8221745291984670090,2989112703022214510>()) {
                                       case 1923576481:
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 var3.append(var10001);
                                 Identifier var7 = BuiltInRegistries.ENTITY_TYPE.getKey(var6.getType());
                                 if (var7 == null) {
                                    label73:
                                    switch ((int)com.yiyiaddon.m.b.a<"s1w1dbh9bjhbk9","ZNTmw2HIgSRhfRPgaFQKutrWT6uYx5Iox8ZnQ4fMmQE=",1759155048853118,6927868941566913751,1471888561253723700,-4962125923564752924>()) {
                                       case -1205712293:
                                          var10001 = (String)com.yiyiaddon.m.b.a<"sdlcp3oawgp5","lIx1VfIDx4JBy2nV5Ds/mcp0++3NqkTSK9GUSrQUZGw=",1369778464688555876,6330499824598191721,-7152588652161772928,-8678263043177448747>();
                                          switch ((int)com.yiyiaddon.m.b.a<"s5qosrhtij6gn","afVsQ8qpa+gPHvLYjJh6xNd+04DIKIbXWL72sdpFNzM=",4415255220139510186,-1591142527518157726,4635603634845886461,-1345779987153784875>()) {
                                             case 779143951:
                                                break label73;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    var10001 = var7;
                                    switch ((int)com.yiyiaddon.m.b.a<"s2dinccvzaxbi0","Ld2Fj575vDZb/qw+6PnGhZksy01Js6DIGsA8WT8dFJk=",-649258183696216136,-1064548260365228452,179961275330088014,-2006803865897996839>()) {
                                       case -2119813169:
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 var3.append(var10001);
                                 String var8 = com.yiyiaddon.e.n.j.g.a(var6);
                                 if (var8 != null) {
                                    label69:
                                    switch ((int)com.yiyiaddon.m.b.a<"s2panscn9bo9fl","kLBYuvKDj5mpzCmHD7xQh5+SK4vMj7kA9PZAIOWUejg=",-8023504771173103479,2022789070088303628,-8457545176947863962,9202922055719174305>()) {
                                       case -900998086:
                                          var3.append('(').append(var8).append(')');
                                          switch ((int)com.yiyiaddon.m.b.a<"s2gm467qioi9jl","SGRBzlv1z7FrLfVuP9u+cexZeNCOayluQspAevP+HOk=",-2767045518111614203,2044638750449421573,-6352782828800843420,7450151103021989755>()) {
                                             case -714583343:
                                                break label69;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 switch ((int)com.yiyiaddon.m.b.a<"s3qbn1t415hugr","ee08W5LeK5sLw670nEeF5C0u5q0z7AAnP4Z3whsHnMg=",2756147210691853718,-6137747845243301827,8515051989729541037,342572011815506452>()) {
                                    case 732679608:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (var4 == 0) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1wwwddccphzht","vLrT7gFVUuvZD15IjnJHxOnGdyCE44pbiVnwonAei1I=",-4764328449129172188,-5040825605256570442,-1372731939842923595,-5429204673743480843>()) {
                              case -920292058:
                                 String var9 = var3.append(
                                       (String)com.yiyiaddon.m.b.a<"s2yxqoucebriml","wkm4tKNMkPUteECLPxKubN0Ij9hZW7CXL6uJUr9chQGnQ71sbiMCxyXkq4c=",4743617751822001001,-8660670432611967000,-3157528176147021380,-2658276810632389700>()
                                    )
                                    .toString();
                                 switch ((int)com.yiyiaddon.m.b.a<"s1w5z2gxia9hy4","oJbiaVg1EPSsrwB2tLsBJUUQF9nkkidcS2bJciSFr0Q=",4965496392098956552,-9119076246983000608,1432656165429442467,-1003098024821165442>()) {
                                    case 1121719470:
                                       return var9;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           String var13 = var3.toString();
                           switch ((int)com.yiyiaddon.m.b.a<"snypipe1i63rt","S1XsUIlSDHN8KsmxypDorVFoeV5o/Wp5YlraqBantdE=",-1921992865778299659,-6379456271746297356,4841894925222644334,-1921429817028580431>()) {
                              case 2060606611:
                                 return var13;
                              default:
                                 throw null;
                           }
                        }
                     default:
                        throw null;
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2d5nwawilfa4v","VSOddos+YzgvUdH4tWx0ppeMr4JmG87UXcWHXm7saOA=",-6121172876488545449,-2832377672293888111,-3519732740854222641,3713119463183382770>()) {
                     case -1398141169:
                        return (String)com.yiyiaddon.m.b.a<"s3vcg7658us7wh","xDDEKasj6y1LB1GuRQIrNbctAQYLoeXtIqrQ5Ujq",4877714310837213569,-172078165901493354,1275585882175916468,6982821458797751900>();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return (String)com.yiyiaddon.m.b.a<"s3vcg7658us7wh","xDDEKasj6y1LB1GuRQIrNbctAQYLoeXtIqrQ5Ujq",4877714310837213569,-172078165901493354,1275585882175916468,6982821458797751900>();
      }
   }

   boolean eb() {
      if (this.f.J != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sr2l8ri2loc9i","r5l2ko3WCDTXEbuitjJh3tRgTH+U5YC/riccP6m1C8E=",4150525759094844813,8088142792541064303,-2684811401116171102,4781656117101171150>()) {
            case 1310368638:
               if (this.f.b.d(this.f.J.above(), Direction.UP)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1spbtqiy17rt0","U8gyVN3HVZLQI19z1yP10UdjDnKziAyB4PgiqR8Zlh4=",-7722423566846810986,-3459040567458717295,-1352426378604861975,6538553811476219711>()) {
                     case 936722588:
                        switch ((int)com.yiyiaddon.m.b.a<"sfki7nnhlwuc7","whrmfkAABE+8nTYPq4owEC1+lz/tNcNOHMqnq1udav8=",6902882249629647876,6592922035627363242,6144887463906467083,3230927736685841284>()) {
                           case 1428884566:
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

      switch ((int)com.yiyiaddon.m.b.a<"ssvj8uzb7fxpi","fdNq3YItqVUT7LUr5/RY0RHzQni4Y+MtWSxdX7gYNxg=",-2565299088222734202,2708048443442782462,-6179876222763859371,-2181111169899696926>()) {
         case -2103153304:
            return false;
         default:
            throw null;
      }
   }

   private void a(s var1) {
      boolean var10000;
      label41: {
         if (this.f.a.a(var1, true) < 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s3g4wh394mkxtf","Fotvm8VJv1/STWX8WZRstgj+PCm+HxuE1Q/se737AFY=",6348907123996901741,752204291642849418,3719245683689331135,2618738171438474985>()) {
               case 769820372:
                  if (this.f.a.a(var1, false) == 40) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2ih85wn03bbl2","2trOIvoCmYFyjd8QFab8DVBO94lCMg83w5PRcPSqxJg=",4539089657913221234,1395085600711891866,-4698671270074982051,-6494034087340309146>()) {
                        case -1204481435:
                           var10000 = true;
                           switch ((int)com.yiyiaddon.m.b.a<"s3p3q8t9sccuwv","2BQswFn4HSljhziVApFAMLSM+Wj795ni0DvqgCNdXLQ=",-2158062305363554007,7698064734648156431,994607863950349136,-1713561674793868300>()) {
                              case 2044725810:
                                 break label41;
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
         switch ((int)com.yiyiaddon.m.b.a<"spo2fu4xg6k73","1foDUBB/ywzqiILvtq6Hoffw1wFowvx2yRTtvmIfltY=",1296854175764268348,-5240305350525031763,-377596516207675956,5209047033225503216>()) {
            case 624697955:
               break;
            default:
               throw null;
         }
      }

      boolean var2 = var10000;
      InteractionHand var10001;
      if (var2) {
         label25:
         switch ((int)com.yiyiaddon.m.b.a<"s3mojv8fvo53yp","v5p/y+YsPCU8CoXoeFJPSReFFHCaKMfwagYmA/Vvit0=",1792284375625131247,8642208496510559907,5807576199032930078,-42384008052479068>()) {
            case -1320148719:
               var10001 = InteractionHand.OFF_HAND;
               switch ((int)com.yiyiaddon.m.b.a<"s2e714qp9yifq7","CVgVCOMgiGHfkmVnzK9CZMYVmvXhNjfGcZtBYHAIY6w=",-8902962696456475575,8948802122619718766,-2339682522257258990,-5808210222932303978>()) {
                  case 659126392:
                     break label25;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10001 = InteractionHand.MAIN_HAND;
         switch ((int)com.yiyiaddon.m.b.a<"s90bqypkujnsb","lC4rfTHh831QwFdWQCG3jJ0OEgzTzHdytU4UB5GhUy4=",-5347372490141774482,3713015758583582267,-62779324133756128,-8351141818573792171>()) {
            case 396562325:
               break;
            default:
               throw null;
         }
      }

      this.b = var10001;
   }

   boolean ec() {
      if (this.f.J == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3ayyc8qhqny6s","IhIHIFraDyE/6gUJf953PwraDWAiTSjYbqIiv9A5l8g=",-5228696831291079000,-7361691222778865376,-8529231849225403410,-2628598440842361970>()) {
            case 380464120:
               return false;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.n.j.e var1 = this.b();
         if (var1.h() != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2u2eeup7er8ga","ycbirZbKH1QmdTvaudMGxFU33tkPqNtR2vELcl+oSc4=",-6976950666173333456,-3636678078463531870,7275829428971421582,-5623138495052722713>()) {
               case -2078591096:
                  return this.a(var1);
               default:
                  throw null;
            }
         } else if (this.ed()) {
            switch ((int)com.yiyiaddon.m.b.a<"s2b0yj925xx9ci","LArcTd2Stexk6sLPmkHOBmZ2AvYoRNaipyAtR0N3FjU=",1293601416582569168,-4899431362464836223,8828581823131938256,-966572299524922858>()) {
               case 543454524:
                  return false;
               default:
                  throw null;
            }
         } else {
            s var2 = this.a();
            if (var2 == null) {
               switch ((int)com.yiyiaddon.m.b.a<"s1io2imakoyfl7","+HIK/jK5TITXMMzYUBnq6zelxvc71aoBAC+UYv61cHw=",-8849361661714045514,-4190473439096745757,8452615165624688348,-5652695649927376026>()) {
                  case 1445022417:
                     return false;
                  default:
                     throw null;
               }
            } else {
               if (this.f.dv) {
                  switch ((int)com.yiyiaddon.m.b.a<"s27mgquesromhy","CzD/o3vnx/mfxm69YTuZJ3Cw6Ae4pis7oWjNujAIS08=",-1468290393539864589,-8166257330780185087,-6387366377254750405,-8675170536285638285>()) {
                     case 1558116888:
                        if (!this.a(var2)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1oql1obz08kq4","viqJgYevrzUv8jb6wj7znwpHAsmWPI5XqqvZt/HSsbE=",5974368483789488057,-121512024847945281,2016438392264647561,7647933557318040915>()) {
                              case -222926108:
                                 return false;
                              default:
                                 throw null;
                           }
                        }
                        break;
                     default:
                        throw null;
                  }
               } else {
                  this.a(var2);
                  switch ((int)com.yiyiaddon.m.b.a<"sme0d9513umjb","+Nodiw1msDrclMVtaGrECv7IQGAh+mtUl1fRzqbBs5Y=",-5653244893815351023,5365180264683596306,-3775155882831063049,2461548549721972561>()) {
                     case -38797905:
                        break;
                     default:
                        throw null;
                  }
               }

               if (this.f.b.v(this.f.J)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s9t0825zy09om","TNAFWZnib0o20wm1hZBz7fCL+IHCFbDCleZf9yhmkFA=",-3685142229750194667,-3381923087526473421,-6967593738703870010,-7831088480825096506>()) {
                     case 1388630416:
                        if (this.f.b.a(this.b, this.f.J)) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3cdh66qqjsfx7","u4ygcWdUQlnJxvTkVXoHhsbc9Z6WcJpQAlri8wlN3DM=",-6167048540494428608,8844161812313431540,-7198091560760531821,-8342777908523233766>()) {
                              case -1812149104:
                                 switch ((int)com.yiyiaddon.m.b.a<"s3clusa7rvbtsn","cByxpXQbIhk0mi6KEtQGhSW0Z/ySY3ASPUpVMYOEZ7E=",4690201259238383455,1494891782827902433,835621529964443858,-3266220617182799938>()) {
                                    case -949471338:
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

               switch ((int)com.yiyiaddon.m.b.a<"s3ndg4pry6i87l","Vo5BT/PTunjph2vU+GzdJVYOmmNkJ5Ujcd2Y/O7oxYs=",-8035030068493131248,-3548247561797982368,-3957033375257855096,-7608032105726549697>()) {
                  case -73146068:
                     return false;
                  default:
                     throw null;
               }
            }
         }
      }
   }

   com.yiyiaddon.e.n.j.e b() {
      Minecraft var1 = Minecraft.getInstance();
      if (this.f.J != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s12xmu0gktri40","igY4o0IAVUrXoIwcNO6PEKexm93en3OFO339CLXzL38=",4713901222063526003,4764364454122528909,-7612134470743636461,722249650942493096>()) {
            case -44332991:
               if (var1.level != null) {
                  return com.yiyiaddon.e.n.j.e.d(com.yiyiaddon.e.n.j.b.a(var1.level.getBlockState(this.f.J)).dU());
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1ndg1jiokk6dy","mZqfPkNRUkykxxFTenXveYS34qP30/cV6335jbkp438=",-1115115404380179938,2900403146844887333,-3272063294253110406,4041286945177874022>()) {
                     case 508595319:
                        return com.yiyiaddon.e.n.j.e.NORMAL;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return com.yiyiaddon.e.n.j.e.NORMAL;
      }
   }

   private boolean a(com.yiyiaddon.e.n.j.e var1) {
      if (!this.e(var1.h())) {
         switch ((int)com.yiyiaddon.m.b.a<"s3rb0sk9w1iphf","OPFc1qLW1Gl0RGM6cjri+FmK9tLx56s4btvtn5I/LHw=",-3837251878279785014,-2933183660045221538,-7781277928703937814,-6638736074871669367>()) {
            case 1134777592:
               this.f.a = b.c.REPLAN;
               this.f.c.d(var1.name() + "", var1.m() + var1.eb(), var1.eb() + "");
               return false;
            default:
               throw null;
         }
      } else {
         if (this.f.b.v(this.f.J)) {
            switch ((int)com.yiyiaddon.m.b.a<"skcynp5021lqk","8uzYgp4iFiCCOBN8p4W9uIeXgX053NNrQMPZEp39zNY=",3919729811681587750,2395384369317558157,2636125136792268035,908683899068306525>()) {
               case 1825388496:
                  if (this.f.b.a(this.b, this.f.J)) {
                     switch ((int)com.yiyiaddon.m.b.a<"swxzurwurs9r3","XviggOnTSpbd955JoLV5aSWPpStLGzOnzRlKA0RDprU=",9070564309871053517,-7729275545479154774,3239882878419684434,1502217718980996474>()) {
                        case 1881310322:
                           switch ((int)com.yiyiaddon.m.b.a<"s3netr7gxvw99s","64269YJxvoIb82AgEaOQgV28EiP3zme+wbLqKCl07uM=",1858464473695391717,-3766672978148349929,-4666966762233596342,3753378406578148604>()) {
                              case -1953153561:
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

         switch ((int)com.yiyiaddon.m.b.a<"s9slufro84h3q","3F8q2rFNctUaSq/XedIhpyL+HkeAdHAShE8iNHsIAmU=",8335615564960914380,5857660808107553549,5656021422559404164,7443112296580639107>()) {
            case -2015932875:
               return false;
            default:
               throw null;
         }
      }
   }

   private void hk() {
      com.yiyiaddon.e.n.j.e var1 = this.b();
      com.yiyiaddon.e.n.h.d var2 = com.yiyiaddon.e.n.h.d.a(var1);
      if (var2 != null) {
         label213:
         switch ((int)com.yiyiaddon.m.b.a<"s1w3z9qnwk36jx","FPvmAoMRK7pASJ3awSeljCqlPmvRjQbDWPr1pH0Udwk=",6859297199366925198,-3538427706829103958,-5065006214625538996,8016928789539043038>()) {
            case 1417613689:
               if (var1.h() != null) {
                  Serializable var3 = this.f.d.a(var2, this.f.d.a(var2), this.f.a);
                  if (var3 != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s5dr5l560zm5g","oNowFLCutEMutw7LsUzWifgnhiST/hFb7QybCnAyCK0=",-8201804676525173684,-4189807039144547106,-6984279379506177559,-1687486310402471783>()) {
                        case 1230204438:
                           if (com.yiyiaddon.e.n.h.c.W(var3)) {
                              label126:
                              switch ((int)com.yiyiaddon.m.b.a<"s3g1ibknc3r5j4","HFK8kDd/DTyCQYstlZmd7d5qtj2E71+cP3Bp+E3k+/g=",7012159505961206332,-8070417463616798991,-7103573637845130204,1330928926183756516>()) {
                                 case -1284232891:
                                    this.f
                                       .c
                                       .c(
                                          var2 + "",
                                          var2.D() + "",
                                          (String)com.yiyiaddon.m.b.a<"s89gecv5yk5ek","DovKQOE3XD3sgoXJwikZbEhzSGSqgJfttXJU0JRVce4nAj7qG3bESQ==",-4888900681731618909,976086084271051258,-7246557090083571002,7209674056274435115>()
                                       );
                                    switch ((int)com.yiyiaddon.m.b.a<"sdu1qdr4edzxr","HV9CBWBxirPQWzsvnF4VvdDlA9sEM+wDs44wIhT9O2o=",-7596809459045636850,8772762578472221985,-694694775217104369,1320622190306490478>()) {
                                       case -232920999:
                                          break label126;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              this.f
                                 .c
                                 .d(
                                    var2 + var3,
                                    var2.D() + "",
                                    (String)com.yiyiaddon.m.b.a<"sxv2mldgjtbtl","I9Gh5qESbRN9WDfu97f3Dj0/lRV5bs3HXL8vwqQboRZtqjunmrCII1lp2w0T8Q==",652300271604216675,4615534372880907574,8272463620981276046,7656771357645658694>()
                                 );
                              switch ((int)com.yiyiaddon.m.b.a<"s2t5n0yt72uqkd","MXmjQsxmlFYAph1FE8Cp/IeRytcy5NAWEKB85PwZR/M=",-1250498523688081190,-1336109983216677050,-3624767924077121477,-6349363468329179320>()) {
                                 case -545149517:
                                    break;
                                 default:
                                    throw null;
                              }
                           }

                           this.f.a = b.c.REPLAN;
                           return;
                        default:
                           throw null;
                     }
                  }

                  switch (this.f.nI) {
                     case 0:
                        if (this.f.a != null) {
                           label163:
                           switch ((int)com.yiyiaddon.m.b.a<"s3cylkjd12lv52","uQCsE2wnm/GF3qgQvasC1M3D51uLkrOyOy/8YOg50z0=",-3295139335776669136,-8165995244554095892,-7876188603052749938,2162533346150076283>()) {
                              case -96584482:
                                 if (com.yiyiaddon.e.n.f.a.a(this.f.a, 0.9, this.f.lA)) {
                                    this.f.f.f();
                                    BlockPos var8 = this.f.a.n();
                                    if (this.f.b.v(var8)) {
                                       label161:
                                       switch ((int)com.yiyiaddon.m.b.a<"s33h993utgjk62","fqxKDJvEGImBpRhasLIeKa44Auid1mW7B80K5uLmD5c=",622012031472950763,7246800837600181557,5304543384040748714,-3278590583101911132>()) {
                                          case 509020791:
                                             if (this.f.b.a(InteractionHand.MAIN_HAND, var8, Direction.UP)) {
                                                this.f.nJ = 0;
                                                this.f.nI = 1;
                                                switch ((int)com.yiyiaddon.m.b.a<"s3phdrpie0bz7","NDdE9VNq0gkDqaTMIbkct75aKVzHsLqzE10V2CSBk6o=",-3212632224026395040,5017066728938259975,232867647127959340,-4606973190487362130>()) {
                                                   case 857548244:
                                                      return;
                                                   default:
                                                      throw null;
                                                }
                                             }

                                             switch ((int)com.yiyiaddon.m.b.a<"s30v4jnrs7fxir","jMs6D0yuetzINuP6Med0o6srMHLuVsPKcMKCAXkcudc=",6786058845376424032,5828953331444897526,-8124686946015433269,1366303545742601614>()) {
                                                case 1268710655:
                                                   break label161;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    }

                                    this.f.ac++;
                                    if (this.f.ac >= 3) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s33nxbw20bwl9r","je4mnZELq/AAp07N7uRvsDt37DcTN+v7InKF+Pjip1M=",-7534597145267901232,-7262689826916732901,3667368336717212021,-451750921617694610>()) {
                                          case 900300752:
                                             this.f
                                                .c
                                                .d(
                                                   var2 + "",
                                                   var2.D() + "",
                                                   (String)com.yiyiaddon.m.b.a<"sxv2mldgjtbtl","I9Gh5qESbRN9WDfu97f3Dj0/lRV5bs3HXL8vwqQboRZtqjunmrCII1lp2w0T8Q==",652300271604216675,4615534372880907574,8272463620981276046,7656771357645658694>()
                                                );
                                             this.f.a = b.c.REPLAN;
                                             switch ((int)com.yiyiaddon.m.b.a<"s2zk9d3rbaonnq","nBKqBmrYooumk8EwGrqnJhNmSTT3dum/Rx+A1o77iVA=",9120176158382061386,6365796541842768839,-5719672571631291774,4578001085834270758>()) {
                                                case 1691168382:
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

                                 switch ((int)com.yiyiaddon.m.b.a<"s28m1u2vendqg4","0QpgOE7a2Cwsx9i+lb9PkdIlbmCieuDjQ8NnbsxFvyE=",-7201167744556135366,-4185761924574164586,2863309823710490773,3949578137003274226>()) {
                                    case 1162669470:
                                       break label163;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        if (++this.f.nJ > 100) {
                           switch ((int)com.yiyiaddon.m.b.a<"s71x8rlbugpfl","Uei0NBLDzHcvCgOb7ymuK/XhAFNX0RjfbEXhLJozjj4=",-1352401587482758431,6194332562938721876,-6014962387856338973,-7248978962538381790>()) {
                              case 416225975:
                                 this.f.nJ = 0;
                                 BlockPos var10;
                                 if (this.f.a == null) {
                                    label146:
                                    switch ((int)com.yiyiaddon.m.b.a<"s10xhxqv1oydkh","Envz+L7hkJGF3NdN3EFiTb+Y2aQLCr5XL7k31+noCZ0=",1575160694638278,1714621228332804387,8755849482771094937,6769722652630935966>()) {
                                       case 1671483773:
                                          var10 = null;
                                          switch ((int)com.yiyiaddon.m.b.a<"s3e86j6d0zell3","DiEWsM/b6r0c4Qk3GX5mvvWQHQYteGDQkYGNL5iRuio=",-7038789073914907526,-4133633271321522197,-7649900847102048535,-3108059880210789628>()) {
                                             case -955042722:
                                                break label146;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    var10 = this.f.a.m();
                                    switch ((int)com.yiyiaddon.m.b.a<"s11x7fq69kfs69","sc/3cCjwZSy4nGDrg3krz3Fe8Hroe/tXws4awXKBNt0=",4741728882558826779,-2287592817883496054,555849678053450223,6922964395412270203>()) {
                                       case -848946116:
                                          break;
                                       default:
                                          throw null;
                                    }
                                 }

                                 BlockPos var7 = var10;
                                 String var9 = var2.name() + "";
                                 if (this.f.af.add(var9)) {
                                    label141:
                                    switch ((int)com.yiyiaddon.m.b.a<"s10ut74fjvd2y7","hc6xKzGGIY/S+cit/gl3W070WQp5w/hryXLpJ6Udv5A=",-7141660921847821921,-5323287692120149835,-8290318519937067254,1471306242443975052>()) {
                                       case -1475548166:
                                          this.f
                                             .c
                                             .d(
                                                var9,
                                                var2.D() + "",
                                                (String)com.yiyiaddon.m.b.a<"s1raayx9dpfomt","1EhKmvUF13vHu0JNAejiWD5E4QnYBwbM8FqcoJx5CtsFYDTKBLn4GP+eIf9U6eej",-5555049013201843598,8386162755874213719,-7651718930793618615,-6171361484864597706>()
                                             );
                                          switch ((int)com.yiyiaddon.m.b.a<"s3qr7ca3kgxtl5","ioV80dQZGLQ4oG9EXZvBfOLmuPa0PVou44sqshveVSo=",1387803025010125905,-816495215618045360,-8457111051797126009,-527850125477677427>()) {
                                             case -1683167708:
                                                break label141;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.f.a.a(h.REFILL, var7);
                                 this.f.a = b.c.REPLAN;
                                 return;
                              default:
                                 throw null;
                           }
                        }

                        this.f.a = b.c.NAVIGATE;
                        return;
                     case 1:
                        this.f.f.ae();
                        this.f.nJ++;
                        if (this.f.f.fr()) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2jpyli5ylpt75","iqulO5rzOdPUzyorHlzsbtiSWht+qZ6wWuSEBz9w48s=",-8626845348976838483,-4302305452034957404,3055736265411029480,62720535700688780>()) {
                              case -1429872537:
                                 this.f.nI = 2;
                                 this.f.nJ = 0;
                                 switch ((int)com.yiyiaddon.m.b.a<"snxhysysgqry9","4nJFT3SJo7eJvQSa1BGyoNM6KjGdH5sYrgZBJByB8Z4=",-2301369343679603494,2125293686590944532,-620766356405252057,-8597351274100080672>()) {
                                    case 1326456113:
                                       return;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           if (this.f.nJ > 40) {
                              switch ((int)com.yiyiaddon.m.b.a<"svj91vlxw4h1s","GMW/Cftxzixm0SXypws9N8DomweDEYO89y2bKmq6h0M=",978621873922828082,2149591483280123631,8634336417012483076,-6072733866393146425>()) {
                                 case -2126749603:
                                    com.yiyiaddon.i.a.a.cD();
                                    this.f.f.f();
                                    this.f.ac++;
                                    if (this.f.ac >= 3) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s1gir8nu677i9h","wJoaShjP57czmiFASTBKpzUu5KwCD3ZW6jlfGtgmjF8=",6890617924027369603,6079526060859109028,3574294995827511098,-7737169864674854384>()) {
                                          case 365745149:
                                             this.f
                                                .c
                                                .d(
                                                   var2 + "",
                                                   var2.D() + "",
                                                   (String)com.yiyiaddon.m.b.a<"sxv2mldgjtbtl","I9Gh5qESbRN9WDfu97f3Dj0/lRV5bs3HXL8vwqQboRZtqjunmrCII1lp2w0T8Q==",652300271604216675,4615534372880907574,8272463620981276046,7656771357645658694>()
                                                );
                                             this.f.a = b.c.REPLAN;
                                             switch ((int)com.yiyiaddon.m.b.a<"s1zctt0b6kihdg","5qVxPINCQp6hDEqS5NsMWWCCa4cTY+93TwLpQ0onpas=",4856023501639416667,5776171959510481376,2812071660146265652,-7625453514914745474>()) {
                                                case -453305174:
                                                   return;
                                                default:
                                                   throw null;
                                             }
                                          default:
                                             throw null;
                                       }
                                    } else {
                                       this.f.nI = 0;
                                       this.f.nJ = 0;
                                       switch ((int)com.yiyiaddon.m.b.a<"sukp4xcwxhogp","+Odd062MqwIAuVl5/7EO2rblzJ9mD7NuvdA+u581rNw=",-227021534839649263,8316648786291165980,-2843955832418467117,-2027330548892701032>()) {
                                          case 1222573752:
                                             return;
                                          default:
                                             throw null;
                                       }
                                    }
                                 default:
                                    throw null;
                              }
                           }
                           break;
                        }
                     case 2:
                        int var4;
                        boolean var10000;
                        label228: {
                           var4 = var1.cg() - this.f.a.a(var1.h());
                           if (var4 > 0) {
                              switch ((int)com.yiyiaddon.m.b.a<"s32kjfumtniqhh","qRe06NzeHlqfuaOgEAussL9Rv0N6QP+bwaQwSC6Phgs=",3249406395623902258,3816157538145498732,8272579673209960675,2057816239707810523>()) {
                                 case 774288347:
                                    if (this.f.a.a(var1.h(), var4)) {
                                       switch ((int)com.yiyiaddon.m.b.a<"s3ojck94yq4mwv","iQAudpBzAA86jsEkn3Ucb+TwED167gQOGeaL5TqbxqI=",6030389094432141570,2428943328383828842,-1922786960597783994,-4058682463716200858>()) {
                                          case 994704655:
                                             var10000 = true;
                                             switch ((int)com.yiyiaddon.m.b.a<"sjp34bfacijz1","vVSrtjEp+QWw5aXy/7TG0uj6D/NE5cBHIxzre43zD6A=",7360298178581166466,4353751485351988640,8171750470948031014,4493733072921252817>()) {
                                                case 415761818:
                                                   break label228;
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
                           switch ((int)com.yiyiaddon.m.b.a<"s2avjpgsqvpyze","hmuiFzo/PvsJ9tGBcvhJjsJ3BaEBRGeBaa2AxzInxXQ=",-3769743239265199101,-1179347418556689637,-6109651472530742213,4303631094513683952>()) {
                              case -499514787:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        boolean var5 = var10000;
                        if (!var5) {
                           switch ((int)com.yiyiaddon.m.b.a<"szxwei6btm3wa","6R/68rN4HV45hqR3AnSkZcmqGMUVU/YBpTgZVl0353c=",6739806275796716116,-9108313233123569957,-7658742071703996057,7183018393413978272>()) {
                              case -1524050533:
                                 if (var1.i() != null) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2ushqsscf0732","+i83Q4GoFoeWhf+MuKvPXNliIAnrslOfWVh7J3OwpOg=",-4074177666712071441,-6139981456153316658,5905852019059358125,8819634449922962192>()) {
                                       case -144309092:
                                          if (this.f.a.a(var1.i()) > 0) {
                                             label190:
                                             switch ((int)com.yiyiaddon.m.b.a<"s2k4w9pvdptgfa","oe6SVxe5H73RCVAnI7RP02jq+IMwP0UOyr7Jx+KQ4ag=",-7783290494944292217,5481094700159143611,7716768678243550939,1633922135144028201>()) {
                                                case -1974475:
                                                   var5 = this.f.a.d(var1.i());
                                                   switch ((int)com.yiyiaddon.m.b.a<"s26fwlx14s54in","iTGKbkp2wgGSxFshUBvA/GYMovKXqzxROUmRLRuTzkQ=",6983078126162776284,5173707423087192019,-3706749316849053419,-1796675771614967906>()) {
                                                      case 360474004:
                                                         break label190;
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

                        if (var5) {
                           label183:
                           switch ((int)com.yiyiaddon.m.b.a<"s1ghdtlyl2ksqw","OGvRdcuEUqZzz7SJGOhzRD4dzGeDu7kJa1dnxugDsfY=",-49247487605691984,-1613851067353690188,-2273528988878219701,-7895071528367120923>()) {
                              case -1657869787:
                                 this.f.nJ = 0;
                                 this.f.af.remove(var2.name() + "");
                                 switch ((int)com.yiyiaddon.m.b.a<"s26nyc8b9u4y54","dsQ88ySuxTPGlvPK8AvIJ57GhvJBQOjTlhKa7W9UyFQ=",3746450024250604193,8188710374128826876,-3845587658850112079,-8095253534288518817>()) {
                                    case 698945018:
                                       break label183;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           label259: {
                              if (var4 > 0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sx2mzkoq6k66v","7Wve2deInhhUGwuJAt4+Oa8pTQ2ITMx2X9ccRUOG+u0=",6565194947292942592,-9175057868506805890,-5285170339303108350,5903142772044359998>()) {
                                    case -1177035844:
                                       if (this.f.a.a(var1.h()) <= 0) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3h5zkntca5pdd","1qF95TnmRIgKbDc/M4p8hJaZ3J2OMFXCTrMlKXcS6UA=",-5539263667694548863,684655225174968157,1207743582466048435,5772401766501995570>()) {
                                             case 103836365:
                                                com.yiyiaddon.i.a.a.cD();
                                                this.f.f.f();
                                                String var6 = var2.name() + "";
                                                if (this.f.af.add(var6)) {
                                                   label177:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s3auerppmb1r2m","iRuwebEDNnUiEz5fwDF4nwNWcmnXKBhtIexapVQICo0=",-1533223153225954267,-3074011058837661679,2986105935397410062,1797073230700643319>()) {
                                                      case 139504612:
                                                         this.f.c.d(var6, var2.D() + var1.eb(), var1.eb() + "");
                                                         switch ((int)com.yiyiaddon.m.b.a<"s2gwjvp79sughl","godbga214qYDOCTgByi1bxB6QqZeIpYnRxRvi9EFOeM=",-573955934700396548,5622622656997750844,-1686113554236455417,1297635791594519124>()) {
                                                            case 474961229:
                                                               break label177;
                                                            default:
                                                               throw null;
                                                         }
                                                      default:
                                                         throw null;
                                                   }
                                                }

                                                this.f.T = System.currentTimeMillis() + 30000L;
                                                this.f.a = b.c.REPLAN;
                                                this.f.nE = 0;
                                                switch ((int)com.yiyiaddon.m.b.a<"s2jvbtvqjdizus","DSr1E+a8c82CXUlkngpEm096TSQXj5pju5ru55FQrK4=",-4518800765937034747,1524127018625058209,-1161126663009911789,-4968371412022562499>()) {
                                                   case 988520395:
                                                      break label259;
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

                              this.f.nI = 3;
                              this.f.nJ = 0;
                              switch ((int)com.yiyiaddon.m.b.a<"s3seepj6osnkus","vY0Gd60Nb6XvE095ElaXrJjH9j30U0fuF9nyblAyDzI=",1461321820173195058,3561083716690070295,4582825657361107733,-2275214237343856485>()) {
                                 case 521790687:
                                    break;
                                 default:
                                    throw null;
                              }
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2gqoblwpra3w7","ItyZJ+cBZ4EwxTNpL4CMQK6174ZeAuCFkkVpQHu9plM=",1893198878369222842,-7638389264642765837,6892807886056621604,3885385865435979740>()) {
                           case 1885783249:
                              return;
                           default:
                              throw null;
                        }
                     case 3:
                        com.yiyiaddon.i.a.a.cD();
                        this.f.f.f();
                        this.f.a = b.c.REPLAN;
                        this.f.nE = 0;
                        switch ((int)com.yiyiaddon.m.b.a<"s12mvpgsf3uw7q","WHbQFBvywW+58kVUTjheSEmBQFjcn6Yp4Ow6B7qX39Q=",5664337786543083228,-2415420104206621257,-6274743558591947175,6380342673563069883>()) {
                           case -1446666641:
                              return;
                           default:
                              throw null;
                        }
                     default:
                        this.f.a = b.c.REPLAN;
                        switch ((int)com.yiyiaddon.m.b.a<"s4h0b7foxjsof","GClKo4yRpE2d2tc5pWUvOmoO7eNhS+y6h4dlp/qEWqs=",-8841322235057179317,4705300596789216771,-253173141176500904,90545314860950729>()) {
                           case 1072884698:
                              break;
                           default:
                              throw null;
                        }
                  }

                  return;
               }

               switch ((int)com.yiyiaddon.m.b.a<"s3dbjruosb20q5","RBsNhCRCzoU3Lqi06VaErJhCJG4sjnAyAzwu7LkJNt4=",6286773758447079957,3190511937701083093,6954515806191402118,320855333728410616>()) {
                  case 297704603:
                     break label213;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }

      this.f.a = b.c.REPLAN;
   }

   private boolean ed() {
      if (!this.f.bC.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3m6l3hhtmi43b","UI30/bd+euSBQ14xAeBVJB8otvjqyKTWYxiT/NWM6Fw=",-4686654320265253702,-7083426597065980919,-1269459643346592831,-4275529905483057083>()) {
            case -1247579260:
               if (!this.ee()) {
                  this.f.a = b.c.REPLAN;
                  this.f.gi();
                  return true;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2o7ortisptbl8","Kl0LMiDP+wTOU8O/qlTe36LCnK4+rWEK/tDR2+nNac0=",5333716390321240689,646617822995114936,625674132664627575,-8377028170894964852>()) {
                     case -638244053:
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

   boolean ee() {
      Iterator var1 = this.f.bC.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"sy07kni84qfwc","IHRpc5yNixViXVUOEC8Sj9spuyJRrUwShaZGOHaLj9Q=",7636257440953381741,-4872340870758922801,4414172405256999353,5725136243616774197>()) {
         case -419203466:
            while (var1.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"sg53845agqhp0","uxB8H7ANaSpCt5P6tcnL0gqu1mvg/+wjPjk0qTXXyqE=",-2460382975417289904,-410731036853876705,6306225624075399370,1676956651522549896>()) {
                  case 549207220:
                     String var2 = (String)var1.next();
                     s var3 = this.f.a.a(var2);
                     if (var3 instanceof t) {
                        switch ((int)com.yiyiaddon.m.b.a<"s21r3xmigneu3n","gF38qfLD8EtHl+qv15Z43S3dW8OAYKl6k3D5P9+7eaM=",-8821476698267823132,-6502525976681691093,-1755937878693210115,6014542697525061523>()) {
                           case -1193803410:
                              t var4 = (t)var3;
                              if (this.f.a.a(var4, false) >= 0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2circfbwwytcv","2mtzLxpWzV92l2E1SWIBQDGEiH74qr55gCvTZeGXS/k=",1836132339508491030,-6333016912839581805,1107658714675825576,-5962698205284446817>()) {
                                    case -1441400947:
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

                     switch ((int)com.yiyiaddon.m.b.a<"s2cbx0egv5x9z3","DSKVKErGJ0JaiiSJwMJqnINb0fBokkLcyowKDl6aGAs=",-6409947253574619692,2293633369436799976,-7388317369520006804,6443804599406008399>()) {
                        case -2029739161:
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

   boolean ef() {
      if (this.f.J == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1t127zllaby5z","B+bvH7rDtTqep8gc/RCfU0XUoM9At45h+jdtCiSIqpk=",-3580280351585147790,2951835205369257835,-5633947551614038158,2580204754869522490>()) {
            case -5956353:
               return false;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.n.i.a var1 = this.f.a.a(this.f.J);
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s22m8ttk5bpwwt","bSqMM6CO5yHKiP0I0Jkp7upZVR98T/PenY7p9Vzpwg0=",-2329080720991545539,-9110982172233373433,-7456397855358402133,1758623342179929249>()) {
               case -1267159890:
                  return false;
               default:
                  throw null;
            }
         } else if (!this.f(var1)) {
            switch ((int)com.yiyiaddon.m.b.a<"s6mgi8wx8okgq","ZVFjYlSd2RpJNfAyo7Z8WGf8/j9ergI3fLCImL7xnXg=",-3478711790067400550,-753180394302226310,4813962651869817492,5315311176326667542>()) {
               case 869404793:
                  return false;
               default:
                  throw null;
            }
         } else {
            if (this.f.b.v(this.f.J)) {
               switch ((int)com.yiyiaddon.m.b.a<"s2e8ar0k80oy61","PeNVnOJAlxqnkJiVceA+LojcNqTVa0E/qUiHCMzR1JM=",-7583316326566002786,-8405883888081243157,2936748226184807280,-5096463770876667942>()) {
                  case 1521905075:
                     if (this.f.b.a(this.b, this.f.J)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s13m3l5f4zgp39","T5k3FO2x0YBfAB067SXytvC2CwZ0XOYTbIT9bXgCvHc=",-3876111924875967213,-2686476741177847033,-6161758036680163659,2280967625460418918>()) {
                           case -1280263677:
                              switch ((int)com.yiyiaddon.m.b.a<"s2lfh906x95ofn","1pZVdkWGhBfK/K8P7aLZsLiPZhyGIq6JX+7riqXsbmU=",1775281622240614637,4767106408926730820,3161465377093666370,-7579547918538934650>()) {
                                 case -568741793:
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

            switch ((int)com.yiyiaddon.m.b.a<"supxv2gacjcys","ntVvSfpht8ITUyBTEzthhwpEn8Jn0Xn2k0Pe+NF/n58=",-4246504293448101146,5851434752425243473,-7230551368888108135,-64267864567547125>()) {
               case -1721998778:
                  return false;
               default:
                  throw null;
            }
         }
      }
   }

   boolean eg() {
      if (this.f.J == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1usw3q72ihfrl","d0SQPoE/yON8jJGihliwdPHBSJ0f71/utNwhu/L00Q0=",5790631299271984755,4019376699329941666,3204110431775531531,2307457260398300357>()) {
            case -775839462:
               return false;
            default:
               throw null;
         }
      } else {
         s var1 = this.a(this.f.bA);
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"su8klm5hbizyg","SOHdJ3I+Lp8cLZM4USmWFdWi/pj+8gVZ93F/kSbTSOI=",4584927796770476729,-135360537577545047,4515988093548266599,6816248025243295948>()) {
               case -1626693396:
                  return false;
               default:
                  throw null;
            }
         } else if (!this.a(var1)) {
            switch ((int)com.yiyiaddon.m.b.a<"s21g9ygkt7ijz7","1GeSztS8/jqhvYt8pC6mn/9vWmw7VhbVs+yWeCcpErA=",7822137906304857085,3393594630812087366,5323611583866628016,3525671830387042682>()) {
               case -1658290916:
                  return false;
               default:
                  throw null;
            }
         } else {
            if (this.f.b.v(this.f.J)) {
               switch ((int)com.yiyiaddon.m.b.a<"s2hnoq58wxjy01","+rfmcfZHiRySBuXT6Rat5b6/LoCs4lv0W98KQ9XNZpU=",-3307167034189067507,-6814136403058575998,7211447158072288030,-3187954236100801451>()) {
                  case -674266537:
                     if (this.f.b.a(this.b, this.f.J)) {
                        switch ((int)com.yiyiaddon.m.b.a<"sr6h1f4gq4dl5","zIeawYW94+NwsQTu2z5PO46ULnPIBGVEjTRoEaZ0ahs=",-3063005894458398875,-5463295907394512039,5217047149108619547,6303258266706937906>()) {
                           case 1831543390:
                              switch ((int)com.yiyiaddon.m.b.a<"sho6h305wqjmc","ysAJ94SYBkyIvtvg9d3LwyR+AZDqoeVe4+DHsYpiX2I=",-2023750548786598076,-1243818855479367910,-7437111070376347261,1015526687086266230>()) {
                                 case -1135043029:
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

            switch ((int)com.yiyiaddon.m.b.a<"s3n1j9db0hfuxs","8U+GTt2WWHnTJy/a7mk94rIAV4OSSDqOlHfrDtZpeKA=",3856908229027919690,4266696029436118304,-1302675622838904632,6513719612726776571>()) {
               case -401640755:
                  return false;
               default:
                  throw null;
            }
         }
      }
   }

   boolean eh() {
      s var1 = this.a(this.f.bB);
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2hax1nqs9qax0","+Igi8o30HTEResqfHZttmRQPbq4J6O0/+zlstjELado=",8044803656454054415,-5678662399654273472,-4171662755375042382,1854201072037049524>()) {
            case 1644692196:
               return false;
            default:
               throw null;
         }
      } else {
         this.f
            .c
            .c(
               (String)com.yiyiaddon.m.b.a<"s1px4yqzce6kor","Hjk6gmfDTsJx27NZ0OYUWjUl5mEwhJEI8czPADLlBGPKs9qa0SZaGm2xEzIcakHSxaB+m6O9ntyIENPsr9s=",6305710874078109436,6523358899629610418,4203202819372146623,-2044290854456355716>(),
               (String)com.yiyiaddon.m.b.a<"s2zjik3j6cd8vp","UtJNlrIxAVQbRzKlnd8QAxBfFNy9LK7m3emtmtfOayGRBkcfz7ERS7zi",667713660430455258,6201023017276290351,-8020133090954252467,6539672024690928211>(),
               (String)com.yiyiaddon.m.b.a<"s1d6sek9ks65dx","my6qdp3BiJlRXeYHnAnV2XAMHPGYLGKUivVYTTV5OTNDX8wNx1YAp21KNi9eRYsh",-173638877674651040,-8279817185775169271,-2635020646743643757,-1849657993416625967>()
            );
         return false;
      }
   }

   void hl() {
      if (this.b().h() != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3obpw25g46ut8","gm3W2ApJlldy5ZjEWXyQCBu8Hao/veAaY3grUqIBV3I=",-894368177105047137,3193521606128618424,2137795186147571912,-1136677766851623845>()) {
            case 1025060005:
               this.hk();
               return;
            default:
               throw null;
         }
      } else if (this.ed()) {
         switch ((int)com.yiyiaddon.m.b.a<"s1am6exgafhakz","VAB+euO/V4fA6bijrem4ipgNdS2rhXAmvcl2oxIpUfc=",6160095506521983433,2775355062225233715,-1910475370839355691,4148934211601233621>()) {
            case -252070459:
               return;
            default:
               throw null;
         }
      } else {
         com.yiyiaddon.e.n.h.c.a var1 = this.f.d.a(com.yiyiaddon.e.n.h.d.WATER_SOURCE);
         String var2 = this.f.d.a(com.yiyiaddon.e.n.h.d.WATER_SOURCE, var1, this.f.a);
         if (var2 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"son5y9n6ramcl","jrTcjoj0zXJd+thiRDQLCDblinKySltBZUe9MG/yUdQ=",-4387766913159276101,-7515749664996083791,-3933760455629965918,7205347270480799767>()) {
               case 890532982:
                  this.f
                     .c
                     .d(
                        var2 + "",
                        (String)com.yiyiaddon.m.b.a<"s6no33n4q087a","cXWp1iaTOrNZsx08P5Uc1j4UwWf97DHTRltlOrp4MhMtkfJXCAffcA==",-7577903496684106162,-3374653120130252359,-2950488236254509600,-7271498671746982330>(),
                        (String)com.yiyiaddon.m.b.a<"s3t723fgmzl52s","yT4yNdIsPvIJ/YwZoT6a2ddSrFhO1ZsUkPebgdJ+RBUbuAw0lJ5gj4Y6jBVVHQ==",2396444534421508199,821335600992964999,1623897561612643510,-6497840325618780415>()
                     );
                  this.f.a = b.c.REPLAN;
                  return;
               default:
                  throw null;
            }
         } else {
            if (this.f.nI == 0) {
               label109:
               switch ((int)com.yiyiaddon.m.b.a<"s1hbgc1u8ztrfs","tuI2/vDlX3OQa0h09vVhtt+cx2biY6FJ2K1guKLf5Lo=",7319767315642779392,-4388417652314834310,2286705594578904913,4544393788962437781>()) {
                  case -1173162782:
                     s var3 = this.a();
                     if (var3 == null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s26uwq1a6h4b3u","lgLAtRPPxJ4MR+VJes6aOdKi1b4sTB3ApLM4tVd7y0U=",2603143009731560862,-7537441573656848734,-8065895879386961449,-2582725319165216286>()) {
                           case 832511098:
                              this.f.a = b.c.REPLAN;
                              return;
                           default:
                              throw null;
                        }
                     }

                     if (this.f.dv) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3al6cx2no0shc","BkwsxhCILTUicAjRRzmqISLiJDFDSqCKFdjGzZnjDbc=",7328876569820795242,1644458483934968779,-2310665800248352510,2310140448065289259>()) {
                           case -1386938641:
                              if (!this.a(var3)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"sl2l29iefqk2","rNZ7toT0LOtzN2P9FRHP0wKT0bDtQJ/56XH0rMkasVs=",3828007661535559125,7419886450100534521,6997327396775937274,8740411428403794702>()) {
                                    case -135262621:
                                       this.f.a = b.c.REPLAN;
                                       return;
                                    default:
                                       throw null;
                                 }
                              }
                              break;
                           default:
                              throw null;
                        }
                     } else {
                        this.a(var3);
                        switch ((int)com.yiyiaddon.m.b.a<"s6e947wuzxk7","RzSkEoKvYFno73Gi6bSeCmKtJmdnkiXyawhCeCEQKD8=",2157185494869881308,5290197906015373161,-5448339846714695969,-7346885177100390679>()) {
                           case -848025559:
                              break;
                           default:
                              throw null;
                        }
                     }

                     this.f.c = this.D();
                     this.f.d = this.B();
                     this.f.nL = 0;
                     this.f.dT = false;
                     this.f.nM = 0;
                     this.f.nN = 0;
                     this.f.nI = 1;
                     this.f.nJ = 0;
                     switch ((int)com.yiyiaddon.m.b.a<"soxe5p8qc2rf8","3DW0pI4A0dOG7ShDYVZnP98Kqpa6XgWWc8W/w9kNHko=",-1995257505662779987,-4648163968429059240,-1420592745031279665,-5897607451970882733>()) {
                        case -1983155067:
                           break label109;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            this.f.b.v(var1.a());
            if (this.f.d == null) {
               label97:
               switch ((int)com.yiyiaddon.m.b.a<"s7pvju50z1qp8","OYZnclHhC2LEtdDYIu0WUchGkYdzuIIp3b1YDrKw+ZE=",-982557384296781285,-4035598122820075277,-1718446305845235766,-1513318976172116848>()) {
                  case 666429210:
                     this.f.d = this.B();
                     switch ((int)com.yiyiaddon.m.b.a<"s1u69itmc68lue","3IHgHWZxx8SARLkVWt5koIheBtXCEOf3Psvu0HsUn1o=",4096357995261407004,-4459100737813769168,3727818795859970203,-1919066244700226380>()) {
                        case 1734377170:
                           break label97;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            Integer var7 = this.D();
            if (this.a(var7)) {
               switch ((int)com.yiyiaddon.m.b.a<"s2hdyen5sqlwac","aVyuAHU2uVuMvqfoy0eLJ3XjHTbtpTH+jVZI47v8NgE=",-4098104939958820284,-8526657993817759713,-205907131620742382,-4521007150098080162>()) {
                  case 1348103531:
                     this.hn();
                     return;
                  default:
                     throw null;
               }
            } else {
               int var4 = 32 - this.f.nL;
               if (var4 <= 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"sqkxdrz2t8lfl","DSPlC32s+XlbbmEQbVrUIJXiJtGCZVAHNnAKamdlojY=",-3676715012349069990,7628259664780594142,-1839762688391916221,6872177900108460333>()) {
                     case 456249759:
                        this.hn();
                        return;
                     default:
                        throw null;
                  }
               } else {
                  int var5;
                  label118: {
                     if (this.f.d != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s5h9mpfr3rfpn","3/q/ynd42+GTV6NR6JLpYxUFqp9z4CFdKblXp7YP9rM=",-573585614487916223,-8400406025151245987,5766808638351803617,9062879918136785930>()) {
                           case 1811740932:
                              if (this.f.d > 0) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s2ih2h3f1vclnc","gQ1X5kTrpxdUYEUToeGGFxO2hnAARXhp9EQeiojnOxw=",8751024433147120970,-1370339813149191405,-7977244763964244925,6161941461020808499>()) {
                                    case 1194517785:
                                       if (var7 != null) {
                                          switch ((int)com.yiyiaddon.m.b.a<"sfc66kxl71w0h","SKnhOyctTjulKmK3G/0TW2UH3DfJDeVXkugdU+IrrCM=",6329385226351616607,-4198151939228291540,-6684538859700629153,-7607868338102623654>()) {
                                             case 1509843031:
                                                var5 = Math.max(1, Math.min(var4, this.f.d - var7));
                                                switch ((int)com.yiyiaddon.m.b.a<"s6tl8g2mlzii2","Nnlnhj68UeU45mwEgteD9Rw9z8LmoysBi0c2CsGeGlc=",2986556837871598150,-9199511921056700965,8144093838000454890,815799264130310508>()) {
                                                   case 485296710:
                                                      break label118;
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

                     var5 = Math.min(var4, 8);
                     switch ((int)com.yiyiaddon.m.b.a<"sfrgm0ecicdun","1dJjnNV04LEsZ4qECBTNJ7hyTA+bl/LufvjGU3IUZ2E=",-5311863878626813200,2197990649431008183,-5773209037438032224,-3554840173857810515>()) {
                        case -1125951585:
                           break;
                        default:
                           throw null;
                     }
                  }

                  int var6 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s1buc0af2a6cw0","nHU7M2R06QuaY4aHQ0jkfixnSsBvcgir+VFtsItvezw=",1601940704856445612,-897966259449319026,8356966587101519275,-3714939441277921172>()) {
                     case 1996270530:
                        while (var6 < var5) {
                           switch ((int)com.yiyiaddon.m.b.a<"s16sgcmanazpai","ZULnLf71rMxBQjgNleOJU/LBZbWcUxei7viqRp5SnQM=",-1547483546766897090,1461358220121556278,-8524633991970464072,-5347412251950374329>()) {
                              case -329567957:
                                 this.f.b.a(this.b, var1.a(), Direction.UP);
                                 var6++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s1z5jxfwhd3x9s","ZnlBB+HP7jeSBcuSg6MtuPEVHHmny3LvznFK0lpdsj8=",4159621305416649806,-2447119975441161349,-7467545106587775867,1255350057606161278>()) {
                                    case 284052687:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        this.f.nN = var5;
                        this.f.nL += var5;
                        this.f.nJ = 0;
                        this.f.nE = 0;
                        this.f.a = b.c.VERIFY;
                        return;
                     default:
                        throw null;
                  }
               }
            }
         }
      }
   }

   private Integer B() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2r5il4e9yio7m","mqwkYCM4HOMmw2mCw32V1GezMsmN1LZD0SQja4bGHqA=",2998693697602442856,2471550408301621726,-1334223503935216147,8537580471271032179>()) {
            case 2035610743:
               return null;
            default:
               throw null;
         }
      } else {
         return com.yiyiaddon.e.n.p.a.b(var1.player.getItemInHand(this.b));
      }
   }

   void hm() {
      Integer var1 = this.D();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2q4qzeg3j2dyk","mAsardHS+I+z3sflgvM9QHHJpMRhFYNHA8jP1zlB9z4=",390511983938250997,-7741817535636707235,8059978504861818799,1241433602123487521>()) {
            case 1690877807:
               if (this.ed()) {
                  switch ((int)com.yiyiaddon.m.b.a<"sipb429cvtj5m","lVFZ+ME1RslK/nEFl+oBej9P1+McuqvIw0BPgGIhw0Y=",-1878678224202767210,-5140077406419679182,-2573795410988056376,-2816184613794690671>()) {
                     case 1339822588:
                        return;
                     default:
                        throw null;
                  }
               }

               this.hn();
               return;
            default:
               throw null;
         }
      } else {
         int var10000;
         if (this.f.c == null) {
            label76:
            switch ((int)com.yiyiaddon.m.b.a<"s30eez04shzqi5","hRVfu/gh3zwrD2pUzM5s3TblCldSnJcujBZBvH/34XQ=",-8028987405048197019,-415273741225607305,110941363549259043,1926473638155817104>()) {
               case 2124340109:
                  var10000 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s5hacl2g6t974","LaogBdep/KNC0FZ7xWuVldxpRfJVorsx/aRUfZluXHQ=",-3759422784505974890,-5734737102153471421,8180258661004951851,3747585465088417412>()) {
                     case -1373023715:
                        break label76;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10000 = this.f.c;
            switch ((int)com.yiyiaddon.m.b.a<"s238apry8dlf14","mfbeQhbXK+Kkfu573+Mmy9a2kd6e+Vpr3wy2YXXmsUE=",-7342075822248296921,7380016730710077027,-3423845746574625539,1733818133129729908>()) {
               case -926400640:
                  break;
               default:
                  throw null;
            }
         }

         int var2 = var10000;
         if (var1 > var2) {
            switch ((int)com.yiyiaddon.m.b.a<"s3ph7hys7xphtr","gLdGUhq8ZkHLF2LzWanL4wRvgRPoFkMOaAfR1PAwAFU=",-4802199832108928964,5734870575341347757,-5768620682985402302,2016558088716153112>()) {
               case -960644243:
                  this.b(var2, var1);
                  this.f.dT = true;
                  this.f.c = var1;
                  this.f.nM = 0;
                  if (this.a(var1)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s25qp4g3fn05xu","mMO+geNSEwhrMxa5Bj/OQiO+wV/T1wYIY/rIDrDrumw=",-2377489013748801741,2338904380954956027,-7389056493821627857,-7001702419320069458>()) {
                        case -669655961:
                           this.hn();
                           return;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         } else {
            if (this.a(var1)) {
               switch ((int)com.yiyiaddon.m.b.a<"s1ub4h2ajxrfts","HvfbAPH4JEWKcCDIVsGNcpecO80aIqDOAW/jwfBF9Jo=",-4391294743628838639,5435878790930150131,-974006206544434809,7893474114507746682>()) {
                  case 925266157:
                     this.hn();
                     return;
                  default:
                     throw null;
               }
            }

            if (!this.f.dT) {
               switch ((int)com.yiyiaddon.m.b.a<"s3rjxrvt08d0fq","rsnaI7daNpP8Gt6+X92z5euubyl9Jbn+qrxAqSAfSwo=",-9144586464740362980,-2492624062360902966,-7375411780209858074,1135771097038418596>()) {
                  case 2039065013:
                     if (this.f.nL >= 3) {
                        switch ((int)com.yiyiaddon.m.b.a<"sevu0u8g5v14e","QYykpal0C1iSd80gjvgBY+8+Vt4tYCrKBRLBpzkk3Qg=",-3331310401129336826,-909136352733680106,3360652689549301211,-5502812861821073203>()) {
                           case 1326971971:
                              this.f.dU = true;
                              this.f
                                 .c
                                 .d(
                                    (String)com.yiyiaddon.m.b.a<"s1nn5l0rn4in78","G5kllV9ywdW2G7vHfxnf0lM4udw+VPgSYLE3lhhVqm7tNV/D894cxUx0c5/T+zSIfGbgXBtXlPL7sAqwak6qnA==",3228167916231793294,-7404691764995283581,-5664798250200402105,-2451964910828308273>(),
                                    (String)com.yiyiaddon.m.b.a<"s1fqcct5ojxl89","O2T2IZCxnbwzZKMcgP3xf4b6rCiTizzm1XWPlXDOPRtBobtJoDZlxQ==",2307733167520906675,-5286240660004645771,2165295673922747110,-1136417427612633490>(),
                                    (String)com.yiyiaddon.m.b.a<"s1ya9sfyzxssic","muKYurY7ar7DpKQyGgBvwRqbTfMP6kaDj/21FXDOXGjGLmilUs7Ezr9F+uA+sFAYB6Z4vC93muLI4+Huhnw=",637711937195642485,-3954160118803295121,-4530144371841484907,-608198492955323043>()
                                 );
                              this.f.a = b.c.REPLAN;
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

            if (this.f.dT) {
               switch ((int)com.yiyiaddon.m.b.a<"s13d435gi12k9j","47lwG0tfcLHbgtZdP+fLVlHFb19+/0+lGA1xxOZ+NcU=",-1693443013932095311,-665581790201452804,-8583838662597541162,8381920132754701401>()) {
                  case 487616059:
                     if (++this.f.nM >= 2) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3l5a0x68otlst","2MqmNJYTaIyPPXVBXk8/QUlC1N27P8SfcTcQ2O1Y288=",-5144973543275070314,-1863045534528683556,-4615726369652445527,-5199876067927592215>()) {
                           case -524817357:
                              this.hn();
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
         }

         if (this.f.nL >= 32) {
            switch ((int)com.yiyiaddon.m.b.a<"s1oj1pn99iscqm","8ASsQ763nFtJ1ZPQafNQ+3116VKbceSiQZLbITU9rJQ=",1543010998543511298,-8994542446182322231,-2963124802675891568,6051590312301749862>()) {
               case -1496906679:
                  this.hn();
                  return;
               default:
                  throw null;
            }
         } else {
            this.f.nN = 0;
            this.f.nE = 0;
            this.f.a = b.c.INTERACT;
         }
      }
   }

   private void b(int var1, int var2) {
      int var3 = var2 - var1;
      if (var3 > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s2hsiqzyoov35k","ic9m2YcLhzCxU1HsL0JYpPE7EzQw8Xf4Olj6TE3SppM=",6612994443253001258,6851106426683277746,-8922401362505685296,1931769053863472879>()) {
            case -456418947:
               if (this.f.nN > 0) {
                  label31:
                  switch ((int)com.yiyiaddon.m.b.a<"s1re8b6f2lg1jf","NX4aDtYrsv0uwf7Qhe7Zo0CTP+HRonyAqfsoO5/ZURA=",557896013574713330,1700756565351669797,-864138655970873917,5441931534200518098>()) {
                     case 2047459292:
                        this.f.e = Math.max(1, (int)Math.ceil((double)var3 / this.f.nN));
                        switch ((int)com.yiyiaddon.m.b.a<"s1yv81yy2gs2y3","gEkiKFdfvHbUNDHsATiIvrgY0WtBQjJVsG0goCXWPPk=",1797817432251894878,1062595716306129372,4012702974025892241,1571095420906157556>()) {
                           case 908554712:
                              break label31;
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

      if (this.f.d != null) {
         switch ((int)com.yiyiaddon.m.b.a<"syxswxa5rflym","sPLuk2GCle+Xx3UgabrKLbUlRa9OWhves7XLfOf18nY=",2915429355164058691,-2537791717867972444,-7937296864059625529,-740882081481029304>()) {
            case -1267634156:
               if (var2 > this.f.d) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2hzbbjbj3kl5t","WIp8TqEJKrxBgJMmXf3vj0hdWNxWwbs4WpY/O5RHlP0=",129997508361064157,-8640258783538387017,3796828697826579172,6211669072986716140>()) {
                     case -385117731:
                        this.f.d = var2;
                        switch ((int)com.yiyiaddon.m.b.a<"s2v25gub8pbatw","TYgTyPUybIyutIJfm9kOPU49OlACMztd9HwuKFPeqFk=",-2121909184258696970,-9011386194496374703,-5358996481129090701,6675751458762622798>()) {
                           case -676282507:
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

   private boolean a(Integer var1) {
      if (this.f.d != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1lcupsapooenj","Rmlt+tZ9aS98X0zybtCTtWM8eUU8P6V6eMWtGkwIdEU=",-8887453825617789202,-2292046944242732434,5617378600756118170,189551024802112133>()) {
            case -860747116:
               if (var1 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"sseumlctede4g","1MDF5QxOqY9nVtk5rEWDiqsdW2gVGMEQLZmsZSow94c=",-1515512545141670670,3363516763811650812,-509789830197767686,-4753366462583266956>()) {
                     case 288121913:
                        if (var1 >= this.f.d) {
                           switch ((int)com.yiyiaddon.m.b.a<"s19zq773tlbt8t","c+7v8F7BWOqu3mKhMNMLdZ4yZ6Gu1NQ6W6ySaWNzJUI=",-1715424584912651966,7216381557362895253,-8387450826316277633,-8096108210149539279>()) {
                              case 1943296176:
                                 switch ((int)com.yiyiaddon.m.b.a<"s21vod6cmyr98j","yWjxKfdcNaVPoazWy+E/kGZcjSyGQWFrmxJfPYudzPk=",-4240344914062903681,-249537803391340185,5852783421784551374,-8746631672388677229>()) {
                                    case -310973998:
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

      switch ((int)com.yiyiaddon.m.b.a<"s2uiglf7dypeh5","qrnfCOxyE+yri3w0bSsyrIXsFTaXJtoI/KUTItuhus8=",-4993007529855801008,8747677609441799553,5308516759274986573,-8979564791928920521>()) {
         case 1705527954:
            return false;
         default:
            throw null;
      }
   }

   private void hn() {
      this.f.dU = false;
      this.f.a.hC();
      this.f.a = b.c.REPLAN;
   }

   void ho() {
      if (this.ed()) {
         switch ((int)com.yiyiaddon.m.b.a<"st974qrm21z21","9hc5zryf0AXxIcJaEbCaIqB0gmOnURrMNmgLsVTkPGs=",-1723673419509594041,-638312125598352660,-8512963370489018219,5722815335879215659>()) {
            case -204670773:
               return;
            default:
               throw null;
         }
      } else {
         BlockPos var1 = this.f.a.y();
         if (var1 == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2ymu5tb9g75u7","1tykNfha6TzHHtUzEEIb5SVKGOMGlSkUbSyw4UP3i/A=",1938550068643001312,-6925622534744933793,-6064009430746399506,-3808748457700934770>()) {
               case -937673365:
                  this.f.a = b.c.REPLAN;
                  return;
               default:
                  throw null;
            }
         } else {
            com.yiyiaddon.e.n.h.c.a var2 = this.f
               .d
               .b(com.yiyiaddon.e.n.h.d.SPRINKLER)
               .stream()
               .filter(
                  var1x -> {
                     if (var1x.G()) {
                        switch ((int)com.yiyiaddon.m.b.a<"s18fbtkq1flutn","eWVs9ifI75E4YI3WCtbLxC+Wf92LeBwnlqYRhDGTEWg=",-4159444509747067647,-3593548354452586924,7322780014998756257,169890143593530170>()) {
                           case 925786508:
                              if (var1x.a().equals(var1)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3d80nf3lfo77u","HJ2573jmgYNQ1/Q1e0QTQOQrJt9MpDNEAqb9grVg5Ck=",-7325804000987252289,-7541838059543601698,-3903832847117417613,3358292736633409407>()) {
                                    case -1277861303:
                                       switch ((int)com.yiyiaddon.m.b.a<"s1p0h4rux407ty","UrKeaKrN9mvJdTO7c3K0bR8yzKDPpROTKoXLUyg+WAw=",-3805860057696492141,-4632070405303905680,-7286317264731739703,-4315064757263791364>()) {
                                          case 971356787:
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

                     switch ((int)com.yiyiaddon.m.b.a<"s3cd5mbieul6of","ZHaRT/yMI4UtWc/tmcMHPQ2q76CyaUJeyv+jjs4Q4/Q=",6912385622424793187,-2222217878571146175,-8358840584327079260,6634414601449092664>()) {
                        case -2073638306:
                           return false;
                        default:
                           throw null;
                     }
                  }
               )
               .findFirst()
               .orElse(null);
            String var3 = this.f.d.a(com.yiyiaddon.e.n.h.d.SPRINKLER, var2, this.f.a);
            if (var3 != null) {
               switch ((int)com.yiyiaddon.m.b.a<"s3g4mzd4c7amlq","Lwa54jsbk4hSBnoaI+yA6FHJrbc9QUbLMCKBE+r4PWA=",8665339593727627247,1019338082873665766,-7907897224876274397,4124257862927315025>()) {
                  case -876831360:
                     this.f
                        .c
                        .d(
                           var3 + "",
                           (String)com.yiyiaddon.m.b.a<"sq4pzhqpcony3","kw13YWP1AYAiOrsqg33kSWNDd55BIpChP1I6gOTNJP4Iu6zXEmNw6QrD",-4849473075226436150,-8556424200778127805,-901670340100260943,8675639264222546069>(),
                           (String)com.yiyiaddon.m.b.a<"s3h0xfvf8fa8jw","/lvET4Ql5XrFyhlTFhl8BaqHHqxk2gK9Z+gx7qhvB8nHtlE9q/0rmwmUjSvJhQ==",-5489129872496857025,-4851495980854929791,6136413349247077617,-3967237758645320060>()
                        );
                     this.f.a = b.c.REPLAN;
                     return;
                  default:
                     throw null;
               }
            } else {
               if (this.f.nI == 0) {
                  label84:
                  switch ((int)com.yiyiaddon.m.b.a<"s1ye0kr6c8b1wp","uXyMram37W05LtTPWlS3/mq+B60AWye6MhMGfs203wU=",5996379368168607713,-7440956097180259906,-1801926000866708963,2553040039699713636>()) {
                     case 1927726095:
                        s var4 = this.a();
                        if (var4 != null) {
                           label81:
                           switch ((int)com.yiyiaddon.m.b.a<"s1t52emxnlhirb","peRqMVmTNybyEvOVZWVqxMaAeEe+3u7uny11vhSbp9E=",5152881739276977632,1282470733876197764,-619472044866665780,-1085175459092053564>()) {
                              case 1282612943:
                                 if (this.f.dv) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s307ozt3mhvh6n","pJCXFug6p9dx1lUkKTU7bdDljjPUn1+UOuSjuuT4YIk=",-6726165036290138003,6374384725296520908,7202817445995365106,6877694455231222737>()) {
                                       case -864116756:
                                          this.a(var4);
                                          switch ((int)com.yiyiaddon.m.b.a<"s1q21o44nq9gcg","vd8tuogJyBKTsdmXnU3bfHdn0t2DX3964sSPJKI4QVk=",4735317424109319310,-7178588474494400657,8342561212938250069,1309026516637591718>()) {
                                             case 1067777889:
                                                break label81;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 } else {
                                    this.a(var4);
                                    switch ((int)com.yiyiaddon.m.b.a<"s17pu30dnkx67x","yWwiKdPFbeIFaK9CtCZC2mqDmRtWHrpVhZHgqHPREF0=",4092749697413641525,7360188617316851425,374290063403930959,4621433151668477983>()) {
                                       case -2086174869:
                                          break label81;
                                       default:
                                          throw null;
                                    }
                                 }
                              default:
                                 throw null;
                           }
                        }

                        this.f.nI = 1;
                        switch ((int)com.yiyiaddon.m.b.a<"slh0rhi7uqxv7","iMJ/Q1kcn/DbYVHf0CjPjnr/Xz7f/b76iltWqo8zkuQ=",6684985852051384795,-2641802704898682935,8498609845419935994,1375707654539463367>()) {
                           case -1913878061:
                              break label84;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               if (this.ek()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s31q1vh6hxz2sn","mDFZ3RzCqTmSAhFct83iWIn9BSQMyGDudvQWuoJH86o=",949500518826576871,-8310163530990173218,-2035614279664896614,-3098443231662489651>()) {
                     case 1956587537:
                        this.f.a = h.REFILL;
                        this.f.nE = 0;
                        this.f.a = b.c.NAVIGATE;
                        return;
                     default:
                        throw null;
                  }
               } else {
                  this.f.b.v(var1);
                  this.f.b = this.D();
                  int var5 = 0;
                  switch ((int)com.yiyiaddon.m.b.a<"s31utq9t7540mq","ldwJSCG3p3iQoZW7pdjrbOkKoG9gJ81FHLsAM000dfw=",6504984566266253753,-3132207365195728569,-892335124795147244,5170175249772806684>()) {
                     case 276366353:
                        while (var5 < 8) {
                           switch ((int)com.yiyiaddon.m.b.a<"s15p3ojzjunyl0","QO4CNMHHpGc367hh9s9pIixHKvIxM47M7dhVNBVrz+c=",-5602117167297602842,9126569949615779704,-2266556855486436631,-3331506800425546333>()) {
                              case 646585323:
                                 this.f.b.a(this.b, var1, Direction.UP);
                                 var5++;
                                 switch ((int)com.yiyiaddon.m.b.a<"sxvjvnuodksmk","ivot7hUDgYzb4PYJOFvo01nc2BNR+AMBix+Xyit/rtc=",-9085238630117619503,-1204581181692969481,4686093955501344537,-4024279812381292703>()) {
                                    case 1913410400:
                                       continue;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        b var10000 = this.f;
                        int var10001;
                        if (var1.equals(this.f.K)) {
                           label57:
                           switch ((int)com.yiyiaddon.m.b.a<"swsz7867cwkwy","Zy5dFh5We61JTTzoGrRwJYBpTFPuFU8uP+6RBz/q0Oc=",5832607688892570792,1966502123625088085,-4157615305015594460,3058515678194839827>()) {
                              case -114541368:
                                 var10001 = this.f.nH + 1;
                                 switch ((int)com.yiyiaddon.m.b.a<"s15w27ujlpush5","HCpfuCOuUoR235QI5ZuKwPMhnuhzb/x2I/nvxlZWo7M=",5210586917713624177,-7200655571565116495,1891812000451392382,6532327990349702330>()) {
                                    case 388058445:
                                       break label57;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        } else {
                           var10001 = 1;
                           switch ((int)com.yiyiaddon.m.b.a<"s4ki6dswvvh23","eDNW+/UbhAbPaX+qbJRkbnChwrM55pA3cFFFjMY4Ceo=",-6750774114204214604,4500052345466014258,1307765138424757466,-887463859623342752>()) {
                              case -1797095234:
                                 break;
                              default:
                                 throw null;
                           }
                        }

                        var10000.nH = var10001;
                        this.f.K = var1;
                        this.f.nE = 0;
                        this.f.a = b.c.VERIFY;
                        return;
                     default:
                        throw null;
                  }
               }
            }
         }
      }
   }

   private boolean f(com.yiyiaddon.e.n.i.a var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sj1tga8hvkofn","j5GIzTFDycDZayBB9o+qON6MoG3FUNKdD8zL1S6a/6g=",-108927391857292518,-2182689427219642491,-3539854404741959603,-805777188538497373>()) {
            case 1751482502:
               return false;
            default:
               throw null;
         }
      } else {
         int var3 = this.f.a.a(var1, true);
         if (var3 >= 0) {
            switch ((int)com.yiyiaddon.m.b.a<"s3bx937e8n14dk","OQrDtcEjU7sAPQZFlXyBZ+z6jLDjImDm7sdbdiWWPjE=",8680733582049662516,-3424525313749728016,7536989142280419059,6762359246604128813>()) {
               case -2138770932:
                  this.hq();
                  this.b = InteractionHand.MAIN_HAND;
                  this.f.b.e(var3);
                  return true;
               default:
                  throw null;
            }
         } else {
            int var4 = this.f.a.a(var1, false);
            if (var4 < 0) {
               switch ((int)com.yiyiaddon.m.b.a<"sj5bpunxb94w","l5Wgo5JkNPFowXoG8RbbhnAUlZRb9clQGDQKnkYeWKw=",-2950175599423567672,-7704472214642186399,-5839782486257090032,-7163682407366141739>()) {
                  case 279679132:
                     return false;
                  default:
                     throw null;
               }
            } else if (var4 == 40) {
               switch ((int)com.yiyiaddon.m.b.a<"s2n75slg9hys62","Da5vV6s5JnAfw7IdBFItMWu7cylbb4TxOhrBrsld4IY=",3225378181761244153,-5587724172572083776,5999007865740227922,-2396149029784941927>()) {
                  case 558101439:
                     return this.ei();
                  default:
                     throw null;
               }
            } else {
               this.hq();
               this.b = InteractionHand.MAIN_HAND;
               this.f.nV = var4;
               this.f.b.n(var4);
               return true;
            }
         }
      }
   }

   private boolean a(s var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s39ytsfyvpnbf3","Zayj7yTPlnwhNNSWywCAesmy7jLzdzZUDKIGPZYamwE=",5586857726676494054,-6805038349299028807,3563295742669765865,463520503103094646>()) {
            case 691227387:
               if (var1 != null) {
                  int var3 = this.f.a.a(var1, true);
                  if (var3 >= 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2fl2clnax6bvn","zvogaJ8glW32Z00AfVcNpF/uSxDeVgxipV6oyzIti8A=",6067117999167843449,-5157880443320138557,-2113692665293029511,7316574248114602519>()) {
                        case 1397612180:
                           this.hq();
                           this.b = InteractionHand.MAIN_HAND;
                           this.f.b.e(var3);
                           return true;
                        default:
                           throw null;
                     }
                  } else {
                     int var4 = this.f.a.a(var1, false);
                     if (var4 < 0) {
                        switch ((int)com.yiyiaddon.m.b.a<"s5dnh9v35afgz","GbYyMq//rA1lwQUdM/Nl8bSgz/c+jO86EF0tQ+oUcR0=",7429226886192360870,-8963474743399582853,736231313550274897,-3648645035956077110>()) {
                           case -633047750:
                              return false;
                           default:
                              throw null;
                        }
                     } else {
                        if (var4 == 40) {
                           switch ((int)com.yiyiaddon.m.b.a<"s623swy1j0967","MbFuUllW4CI7/+bJks+81xYs9xS8L48dt0SzX99YuV8=",2809535901156202110,-1598833927868608658,4740214910676267634,6027654476711722933>()) {
                              case -174673360:
                                 return this.ei();
                              default:
                                 throw null;
                           }
                        }

                        this.hq();
                        this.b = InteractionHand.MAIN_HAND;
                        this.f.nV = var4;
                        this.f.b.n(var4);
                        return true;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"saorrcrhu790s","ycm1G1HKTFEjmrG+RiLQft2T8wNv2lP8IMVS9NsXNCQ=",-7828407620197818439,7551712934851758452,3846121234792168624,5518096546230934941>()) {
                     case 1661215790:
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

   private boolean e(Item var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2oxucm3avwhv5","qYfGTMI+7Z+GVinjP5mR5xeDQbOtpFA0DS/yjl2OrLw=",2367807208468628523,-2972806983435392283,2344740064521913190,7674081740920217444>()) {
            case 637751857:
               if (var1 != null) {
                  int var3 = this.f.a.a(var1, true);
                  if (var3 >= 0) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1i5csvnvlj76u","E+91wiefZCBsjzf+UzK92ZzuoHEgfsmQEglP0G/+1XY=",3968743166860511604,-6766093532244995755,3827498689845356718,9113903398986392594>()) {
                        case -2050697244:
                           this.hq();
                           this.b = InteractionHand.MAIN_HAND;
                           this.f.b.e(var3);
                           return true;
                        default:
                           throw null;
                     }
                  } else {
                     int var4 = this.f.a.a(var1, false);
                     if (var4 < 0) {
                        switch ((int)com.yiyiaddon.m.b.a<"s26t8xem832rb","YSsrRYcD783yNW62sAf3BIS/EnfLuWIhREWK4c7RD9s=",736983462815646635,-3485155794274355020,-8434778383483812736,-824353646710743125>()) {
                           case 306147512:
                              return false;
                           default:
                              throw null;
                        }
                     } else {
                        if (var4 == 40) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1z11g492hwvnp","x7pS7WeHzyPIdOp/0sWhEw/XZKLVbhepw3lAXHlZWNw=",-4821645632854086250,-5275891890847750596,9200440626000768612,-8699229190267568046>()) {
                              case -841193215:
                                 return this.ei();
                              default:
                                 throw null;
                           }
                        }

                        this.hq();
                        this.b = InteractionHand.MAIN_HAND;
                        this.f.nV = var4;
                        this.f.b.n(var4);
                        return true;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1jwva8hqwa0lu","oNf9mz2j6Ors3lFCp7Ppt54YGP1dI6Vpkqgp0JKkvbs=",-3205752880931186306,8937956241533331228,9079490150251211274,5665781324189347571>()) {
                     case 1914186022:
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

   private boolean ei() {
      this.b = InteractionHand.OFF_HAND;
      return true;
   }

   private InteractionHand b() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s16jjsr4bbcgl8","yvUD4CnTM0QcZuBWDWbOvp/v/77xLlcoEU68h8PMVxE=",1934434780881968076,2715633415714299127,6758637609236157565,-8573530637029644372>()) {
            case 1261243479:
               return InteractionHand.MAIN_HAND;
            default:
               throw null;
         }
      } else if (var1.player.getMainHandItem().isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s35m6sb5zm5fxx","pmSPDnZjTXm8dCsIru0pk4mbVKB1+oWDEGvtG43f9p4=",666478700047222964,8088778265404603948,-2371245228768265550,-4259525990750496427>()) {
            case -93858045:
               this.ee = false;
               return InteractionHand.MAIN_HAND;
            default:
               throw null;
         }
      } else {
         String var2 = e(var1.player.getMainHandItem());
         String var3 = e(var1.player.getOffhandItem());
         int var4 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s303jax8fi9xpn","uXLgF7qDywbz5cAUJ96uKwdF6tYnNtZwj42NROZZp+o=",4963365619355900166,8663413439998663204,-3296755401306924324,5023160747567419621>()) {
            case -1589959713:
               while (var4 < 9) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2u762qgyf9qhc","BmDRwzn8gJ4d7V0JZp0u/DjdXLSoG737jverS4bpVOE=",-5841324568154777253,882838203888945973,-961477059511191826,-786574185537328477>()) {
                     case -1300504818:
                        if (var1.player.getInventory().getItem(var4).isEmpty()) {
                           this.hq();
                           this.f.b.e(var4);
                           this.ee = false;
                           if (b(var1)) {
                              switch ((int)com.yiyiaddon.m.b.a<"sn9gupnkcf7b5","wVbRyrSbMagQ+0qst/XSFREAJUNh2id02MU3yphR3D4=",6713101885653787247,-4669720136812437854,5077679971099196027,1242837427924047918>()) {
                                 case 1960243436:
                                    p.info(
                                       (String)com.yiyiaddon.m.b.a<"s1od77f8ee1ter","JckY4z2EpWnBGOSaT9U7mvG/p7CCbj/5QrLQwqqnnlD6DTw8y3wXdWB1x1CrFLz94McWRbj7ZXP+fWr9TY1j1kgCxrmEdCCRxHc0cFY3xRoQTJPhc4dccg3Asj+sUvp9",-1704302663379237003,6220740347520230701,-3080346401228617788,-1761573635036445819>(),
                                       var4 + 1,
                                       var2,
                                       var3
                                    );
                                    switch ((int)com.yiyiaddon.m.b.a<"s2twaa0nr6rdqp","orq5AS7oeVU4POFkyw6lesJROZ1FK3XGvQjdUNMeNwY=",6559407375877493156,-5959224170977792893,7686991187081968201,4314207572226703939>()) {
                                       case 1896860355:
                                          return InteractionHand.MAIN_HAND;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           return InteractionHand.MAIN_HAND;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2580i4h5mo9yl","5yU/5wyv6U1AeHHFiqqDlzgXN+IS/hWUCvC+/a7+cME=",4468469239507040160,-6135181993939991046,5304116514175064019,-820639958378429113>()) {
                           case -127890981:
                              switch ((int)com.yiyiaddon.m.b.a<"s3qvs8evit017s","ymmN7QOwN/mnqxxxNKSJKuXJwCVt/b74AX9K5yQMneQ=",2237141978934630369,6772662775594800980,-309218251854850693,-957141332315121631>()) {
                                 case -735030586:
                                    var4++;
                                    switch ((int)com.yiyiaddon.m.b.a<"s1w1fko62nofln","E/v61zEn2sKVeFC7F00awehu0QYn2PvYPuaiClkFBFM=",7372475261162737617,-2773367585243641029,-4004066353253353333,-874708898786793518>()) {
                                       case -1161946576:
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
                     default:
                        throw null;
                  }
               }

               if (!var1.player.getOffhandItem().isEmpty()) {
                  label71:
                  switch ((int)com.yiyiaddon.m.b.a<"s159adjx1z9t76","9cxglKbcXd83Q8PeQ5FSTC56obmjJZi22jjIiZFtHZI=",2307680481675017362,2491658297884311988,7733594776309737440,6265486142471466511>()) {
                     case -2058279969:
                        if (this.oc < 0) {
                           if (this.a(var1, var2, var3)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2baa3qe5ddlkc","CdslCGjGDHUuiKf7R+nfpSzKqo1earsos17R9HEj5BE=",-4582724614119077030,7687706468232291127,-638294150915627500,7669806929334258642>()) {
                                 case 1445507314:
                                    return InteractionHand.OFF_HAND;
                                 default:
                                    throw null;
                              }
                           }

                           p.info(
                              (String)com.yiyiaddon.m.b.a<"s2ckpmmrea6gdq","yyiCdVhHeXxEp0qv7RS+ZcnEVZqWMM/Hy3ASCTvHPiAnQDfLwjo4CLoxVoXnQ1B5KXekfyc6OuZBTIp+l3IXI9M6OAQ+Lks5Uu9xJSqazEE41q+8YquBWVrYqtodo4ZlmQwk+x0nFEeD2PfERkt0z1eIgP8BlrL33OxAHjwO02U6da04MOEbf78tc4SVlIXwPKk=",-6779085656866160136,1419346967332911105,6063004076706267930,-2904525463978796791>(),
                              var2,
                              var3
                           );
                           if (this.ee) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3deljiie7ng0k","1Iy+dGvq7qyKXTnPB87g4BbI3tkW8qMPZ4BBvav5mEg=",62624352991930257,-6916501583993089992,6076311869791104719,-6987794899452997242>()) {
                                 case -1687407897:
                                    this.f
                                       .c
                                       .b(
                                          (String)com.yiyiaddon.m.b.a<"s2tr805pdrn07i","XTEkCyQ8mml4Al84wYxBNQWBJFL2g3eJSC5id+dxT1bYB6AVPeej6HWInPPpi89NYt9DJw==",6564213188367035853,-5166539256657678453,1319151702115987169,-5959785031164993649>(),
                                          (String)com.yiyiaddon.m.b.a<"s1navqwv7hzzrg","5CfoRq+b0k0WoOJ/EhlhosbyHghIeYtqABvReCR+Qqk2WVAniVN075ZWHxA=",4265032894784843106,3992977297978114302,3157731017030958054,2164841461936830738>(),
                                          (String)com.yiyiaddon.m.b.a<"sqmgy1r0re246","K6pXAxTZrT/5AL3XSDMddZ9ZznMmyzZLr8s+TbFgBB96XKn3sk9Meg1Ca3RxGv7kqYKqgFTRzgk6t+5MAnit/tfC3f7AUpR5QLT98Q7xcDcddeLYWG/zwgKeLRg/k65m/hJLyvqORxGV0A==",-7985643465573358066,665042292209253854,-7542568707110726675,8559731459115685651>(),
                                          (String)com.yiyiaddon.m.b.a<"s2m1iczpwl8bt5","0HjPwudmudGuY1/q9M3fnIz8ODO7Qnp+ghI/vQ==",-7849052160631048395,9013339468798112126,-8073518389095902744,4307103181473449076>()
                                       );
                                    switch ((int)com.yiyiaddon.m.b.a<"s2le75ryagi55m","G9aX3Tl6QCSFe6rIYzH9/l8arjRGpjGz4QVtbKovw1A=",1070353379305882783,-179677562165303271,2728039657374669230,8661026541156931072>()) {
                                       case 1042172251:
                                          return null;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              this.ee = true;
                              this.f
                                 .c
                                 .c(
                                    (String)com.yiyiaddon.m.b.a<"s2tr805pdrn07i","XTEkCyQ8mml4Al84wYxBNQWBJFL2g3eJSC5id+dxT1bYB6AVPeej6HWInPPpi89NYt9DJw==",6564213188367035853,-5166539256657678453,1319151702115987169,-5959785031164993649>(),
                                    (String)com.yiyiaddon.m.b.a<"s1navqwv7hzzrg","5CfoRq+b0k0WoOJ/EhlhosbyHghIeYtqABvReCR+Qqk2WVAniVN075ZWHxA=",4265032894784843106,3992977297978114302,3157731017030958054,2164841461936830738>(),
                                    (String)com.yiyiaddon.m.b.a<"sqmgy1r0re246","K6pXAxTZrT/5AL3XSDMddZ9ZznMmyzZLr8s+TbFgBB96XKn3sk9Meg1Ca3RxGv7kqYKqgFTRzgk6t+5MAnit/tfC3f7AUpR5QLT98Q7xcDcddeLYWG/zwgKeLRg/k65m/hJLyvqORxGV0A==",-7985643465573358066,665042292209253854,-7542568707110726675,8559731459115685651>()
                                 );
                              switch ((int)com.yiyiaddon.m.b.a<"s19lpb6tae400h","JvZsc18liINQ5oGvZbMDEYENT92c9A1Hs0cPQ+O4nw4=",-7689801189372837554,-2030886691685616857,-1639049429776089551,-8105751230021596911>()) {
                                 case 1139307497:
                                    return null;
                                 default:
                                    throw null;
                              }
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"sdbgj93hm9e5l","GyAz7P0JOmys7tr+VO74bDe4xjtlMqJLs1vUr6Vg5nk=",6344879651114685891,-3617476896204721600,-7600126970307649469,4701772313038306166>()) {
                           case -2116821407:
                              break label71;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               this.ee = false;
               return InteractionHand.OFF_HAND;
            default:
               throw null;
         }
      }
   }

   private boolean a(Minecraft var1, String var2, String var3) {
      int var4 = this.b(var1);
      if (var4 < 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s2h3lhvs4dtzzx","CnQwT7BVFRhYCVVk03krh0OZBG9XH7wpYfVEBOctHQA=",8515349426594666056,-8182777178665073104,-5052821121726580013,390462377339028389>()) {
            case 641164354:
               return false;
            default:
               throw null;
         }
      } else {
         ItemStack var5 = var1.player.getOffhandItem().copy();
         if (!this.f.b.o(var4)) {
            switch ((int)com.yiyiaddon.m.b.a<"skc1u11kuyzox","wqLlXj3RT+PR6hy4FpLZE/khb5obD7nhC5Njy8yPndI=",-4501155198130822584,-8734970885278233176,-6531798948699636843,-5354812316183549416>()) {
               case 495137926:
                  return false;
               default:
                  throw null;
            }
         } else {
            this.oc = var4;
            this.l = var5;
            this.ee = false;
            p.info(
               (String)com.yiyiaddon.m.b.a<"s3tcq6x91kca15","nF8hyBUqp5FNbEcD/WAr/9nvhoRAZSCGfFUOowlajRfUbyNpZXNO1tuSRohEWeSbbElY/yP8TUwC0whyCDjhhn2u66Cuf3nkg9p9y+c/PAkmsmjkGDQ5SW4aoQutHh73u6n8D1y+UIvdbqpFe78kGqPmWME0s3HHfBWQUc9AwSr+BHsoQzg=",-8814960592793058480,-1166848590378436864,7808478410950306720,-2105054729176298333>(),
               var3,
               var4 + 1,
               var2
            );
            return true;
         }
      }
   }

   private int b(Minecraft var1) {
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"slw1wb052v2e9","5neDrL81SOlfy6s+3TbjnGzbiYXYtr57m1h/zDFzWs8=",6327475302292598088,-3095355450198117449,5405272847859867938,94449041985993527>()) {
            case -1165375863:
               return -1;
            default:
               throw null;
         }
      } else {
         int var2 = 35;
         switch ((int)com.yiyiaddon.m.b.a<"s3axst2n787bzc","Ea2vds5glwPKKjrQgo3Hn7yn381S+jtPkmiDjBeF9IM=",-1697420660687840707,-3812319680165279027,-6183869648346470789,-3045052865106824379>()) {
            case -237111389:
               while (var2 >= 9) {
                  switch ((int)com.yiyiaddon.m.b.a<"s11gl3mu8mdwm7","Ug7ZOpeCUOB+Ajx2OJq6Lg5MCzloF0dUqCxC5qq8Avw=",-636597162195704412,1593581269527451495,-3756663639918266909,-128945733128641842>()) {
                     case 514874379:
                        if (var1.player.getInventory().getItem(var2).isEmpty()) {
                           switch ((int)com.yiyiaddon.m.b.a<"srqmh3kjg8337","rNt8cF9ZWiA1hAFEfHhKZ3Qd/fzZiKJvjR5QEYlljXI=",3985054936127502052,-1264284173899130328,-8605394741316120527,4223368658587276319>()) {
                              case 104846916:
                                 return var2;
                              default:
                                 throw null;
                           }
                        }

                        var2--;
                        switch ((int)com.yiyiaddon.m.b.a<"sklm0ehgtd4hi","dVMwewPiPNutuvOMHf9jZjMuFjFqPG37+aiz3bJL1ls=",9214679330328098605,2452329575590648700,8650512197802809871,-2031396279577346397>()) {
                           case -1720008414:
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

   private void hp() {
      if (this.oc < 0) {
         switch ((int)com.yiyiaddon.m.b.a<"skxdfz9yr5yox","FSInJq7pjPL8tCnLi/Gy5yZgqAII3EgiVn0n7FCQPPw=",-5432151423067801448,-3480684106286485148,-9054425327563232172,8766465614393817346>()) {
            case -1598851366:
               return;
            default:
               throw null;
         }
      } else {
         int var1 = this.oc;
         ItemStack var2 = this.l;
         this.oc = -1;
         this.l = null;
         Minecraft var3 = Minecraft.getInstance();
         if (var3.player != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s2diavbpnfdi8y","8p1zOiHNuUSw2qtvKzR2g0AzE92jZmn6PO6YutzSAFA=",-4363060184689592467,-3371318128163334907,6946229249843466849,-7039096865754697979>()) {
               case 119775054:
                  if (var2 != null) {
                     ItemStack var4 = var3.player.getInventory().getItem(var1);
                     if (!var4.isEmpty()) {
                        label39:
                        switch ((int)com.yiyiaddon.m.b.a<"s20uh3mg3959qe","h87huyiI9zpm8kV9tuVCvuzl4seUcjKE9Px1duJOA7M=",-5052896951428220605,-7464540072103683527,6662912187693951946,7457910419643160254>()) {
                           case -1005271384:
                              if (ItemStack.isSameItemSameComponents(var4, var2)) {
                                 if (this.f.b.o(var1)) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2olqrkzpgvpcr","7damKWxy7nMnSqup+kfsN+B1Z0wcVne31q26BhtR+F0=",-3257974698472218988,-2932443421107283482,-5842724040025589144,273360777279809234>()) {
                                       case 1116449328:
                                          p.info(
                                             (String)com.yiyiaddon.m.b.a<"s20en3ck02b8td","lfH36sx7cAMMepbnIyrtnvnVcQvrsug3XJYeQ38w3Yplffz8GF5Jvi7Whn9y6sEJ09CUfFbb64l63pJjwTBvA9Vdz4f/65o9OjfBkMFW3xE=",2607017121952379391,-9070496877255731061,3800330141139099813,1504051897611452706>(),
                                             var1 + 1
                                          );
                                          switch ((int)com.yiyiaddon.m.b.a<"s7qxdvwfak1dn","EFri+LbowChRZbvQzhzeHVzUzrjj4b61F/tBD3I2wFU=",5699497681992578348,2455774932180200044,395143322878528729,-6513536796402883937>()) {
                                             case -552027905:
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

                              switch ((int)com.yiyiaddon.m.b.a<"s2o8g9f468wubw","E9IJCYKnPpWZtj+MOgM3JEvxeyxTHYRWe2y3RfnqgjY=",-7494399171051484590,-3038642795389472891,-4956315496005536929,319529107616718709>()) {
                                 case -557548129:
                                    break label39;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     p.info(
                        (String)com.yiyiaddon.m.b.a<"s3tv1unsybjq3o","OU4ipIc/VtzGqCRdDWQdVHsSz8ohZBz+kZ0ClItbay+cml2asdsA60/mEfSNWGIAJnj9NJvViua3DLVbcw2o3UyRfcmcf5WwC9VqfKOV+2i3aR0iFylxRKBWMxh4xrUBp4b7MmN6ZbU=",8187621905463124276,1650229573229512346,4579995776504283371,7129133264155289894>(),
                        var1 + 1
                     );
                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s2g9ceofr5dy9h","lm/xGVx7bRk6l9qsE1Vj9ptJHTJhhR9WMweBOoSKIxc=",386951304077046320,3491541744101586527,3516913641602821212,-488693986792742882>()) {
                        case 630497940:
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
   }

   private static boolean b(Minecraft var0) {
      if (var0.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"smd4itsceoy89","O5wWa1R47UXK27NkZoZ4elc+8J1fUCyoGjZTHKPBR6M=",3395398680636534898,2986735491498468765,-6697704494271592723,7861540823132655872>()) {
            case 1490402938:
               if (!var0.player.getOffhandItem().isEmpty()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3313ukr36l2ng","OjAO9U7OKeZdFU9I8Mpmv5GQSWvIrmVTf1+ZETZ9JAI=",5366888565638824336,-2793874508941699794,-4836577348745895661,4349979229438312334>()) {
                     case -51046985:
                        switch ((int)com.yiyiaddon.m.b.a<"s2015zlmknl4cx","7f5eOXNbnHdYU3p+oQRoOJ1WoLWGTXlHzDYqh9BykoE=",7102822839611920160,8527597243512795226,49072150551864260,-7451837185773366062>()) {
                           case 971622640:
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

      switch ((int)com.yiyiaddon.m.b.a<"s1v4o06kjvcswz","SOzAjIMPUX+/yVZJlb4qqYHPsyC42DxoPjFpkeqh7RM=",-2475434806277651703,-7359513161578363735,-6427709490110330030,-8800889108455627702>()) {
         case -31577781:
            return false;
         default:
            throw null;
      }
   }

   private static String e(ItemStack var0) {
      if (var0.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"s2gyw9pvmcxw8u","SdGE19E3aZ1212OliUEdBszlqMO9hviErxyWB20hWvc=",-3825641477483377734,-1342907702738920015,1097714798862579857,-7092590155720861358>()) {
            case 2052749190:
               String var10000 = (String)com.yiyiaddon.m.b.a<"s3avl7ib3tm7sv","VicxpH+AXIPWM+gWR+Ih7nDC6rKnLquQfwdEO/0Z",-6695224719941905819,-3760372221735545088,-1463022966879178948,1858171479717913764>();
               switch ((int)com.yiyiaddon.m.b.a<"s3nkdmuqebfp5y","fA5OYJYs7KSqYSENl1CzSt1vvYY6Z8jOh8f1Dqu0XVM=",4451981417169854078,-8450961391357164264,-6132145441365218253,-3949146308131599516>()) {
                  case -1172471893:
                     return var10000;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         String var1 = var0.getHoverName().getString();
         switch ((int)com.yiyiaddon.m.b.a<"smx96x8k6cztf","iXI10mcTE7PFTN4kS1yWiUTl2kTqQ338z7Yw+vHY5RE=",-632861357522090970,-7378240258000308434,-1407171555109600558,3709160203150960997>()) {
            case -1135748735:
               return var1;
            default:
               throw null;
         }
      }
   }

   private void hq() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s35ulubdjw80vi","NIBkGmrh2zvFqS3Olb7WyRXjspMl6olkMG6kuuXa2H8=",7794502032086954475,-3100545954239115047,4699672046201117208,5069479397452815034>()) {
            case -194526158:
               return;
            default:
               throw null;
         }
      } else {
         this.f.nU = var1.player.getInventory().getSelectedSlot();
         this.f.nV = -1;
         this.f.eb = true;
      }
   }

   void hr() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s19nyzbdtgoza9","TomDjlP2MepEPiiHBhVXJLFgv9z34g8Yos3ZZhe3r78=",6354840013575436230,2785000675224252524,-7386890350077066341,-6072179656877285841>()) {
            case -2054110704:
               return;
            default:
               throw null;
         }
      } else {
         this.hp();
         this.b = InteractionHand.MAIN_HAND;
         if (!this.f.dw) {
            switch ((int)com.yiyiaddon.m.b.a<"s1kk44db996zvn","16rtJHEKTJUUUYbseZD4/7RMPnyfdTRB0ZsKv+FVZes=",-2608084831941030233,-6478077947449329169,4662169560421144837,-8508325070759855211>()) {
               case -1000965771:
                  this.f.eb = false;
                  this.f.nU = -1;
                  this.f.nV = -1;
                  return;
               default:
                  throw null;
            }
         } else {
            if (this.f.eb) {
               label37:
               switch ((int)com.yiyiaddon.m.b.a<"s3geyoq3nb9sj3","Uz/lHI5ZevX7nPrxv1Jzp7lTfgeuhTIgDj+PYrdtEOk=",1475758681191693928,-8104919505609175570,561643728886109607,-8465262009654373143>()) {
                  case 900747598:
                     if (this.f.nV >= 0) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2ape4pxrvnhmk","J2kOc4mQoYoOym2IeuXyA83mpppkd6BH1Qaak0GXobQ=",39258921483276186,-8997024086139206769,-4939165573005836291,3824480551179101809>()) {
                           case -595034315:
                              this.f.b.n(this.f.nV);
                              switch ((int)com.yiyiaddon.m.b.a<"s257m4hsb5bgx6","dkxSe/d5uYOK9paXO8no6J+uWEJgpelwmJBOJ3haik4=",-3017578086159085250,1592984369886178951,2193134718944395683,-3924083581169319201>()) {
                                 case 1148041580:
                                    break label37;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        if (this.f.nU >= 0) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1wzygkgeh69ug","1QZC1tSoGMUfG7DIRQzb6hm7l04ptfYnS9Yl5dKh5iQ=",-8411064344556923546,-907886265120092373,-4740232200479801502,1192630873050313490>()) {
                              case 1868635301:
                                 this.f.b.e(this.f.nU);
                                 switch ((int)com.yiyiaddon.m.b.a<"s38tyggsg57gjg","Tx4ECJ4lOoYGuqfFF0tBByL1Aip095vQVzlgHg6Z6mE=",-570014256933911743,-9177166459484894078,-8010944526341510129,3438249314809795408>()) {
                                    case -266322743:
                                       break label37;
                                    default:
                                       throw null;
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

            this.f.eb = false;
            this.f.nU = -1;
            this.f.nV = -1;
         }
      }
   }

   boolean ej() {
      Integer var1 = this.C();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s7xgrzu6778r1","Er8fFI1AMxLz9W8+TGudTRy+C7TzJ/z/Z0HKd+yqjhs=",9164028474188856784,-1040726177737013449,-6024010792250122428,-6112290926534505532>()) {
            case -1486132753:
               return true;
            default:
               throw null;
         }
      } else if (var1 > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s2lzl7z52hs14","HkiDE5PmhOt+EPb0NVboc/2d2dnxj7tdU1P2ppfbGII=",2454388359830766004,-7798643673717767371,-8007395080432612673,-6319750491829944196>()) {
            case 283983891:
               switch ((int)com.yiyiaddon.m.b.a<"s3i3k8ctvta6s","ycmmwl1JrWQgfqAj7lznlIPSgidJuRNmzWysoCttyeY=",-3227616881643254343,-8902904733212460339,-324529415326786342,3735797646596750631>()) {
                  case 709924128:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2t62kzngijj0f","VfwEvIvYPGLrMyCIST/hALKevprjKV4kMtqVIy9vgwY=",4411173085436182094,-3084192840324724030,-3463419227448068902,-4262351309969612189>()) {
            case -65244446:
               return false;
            default:
               throw null;
         }
      }
   }

   boolean ek() {
      Integer var1 = this.C();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s6814h0nvlquf","qZf8hjRAYJkIY9VyUkYhBOFPI7kYMGI7aptCJYthU6s=",-7780196074182107909,4398971983859159367,6946020594589594873,7594109933549852544>()) {
            case 1602813343:
               if (var1 <= 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"soze1eivh401k","bTgQE47tQHyM72Hic+kG9JxAsxwXCk6RnHyNxKe83sI=",6727757049604115541,-8474857975452379658,8778010626173994762,-8550289529226423553>()) {
                     case -82749575:
                        switch ((int)com.yiyiaddon.m.b.a<"s3rf78k7r1qf6i","o4MSS/BAzNtNLX3WlAIPOuSnJ473M8pHyRT27Epa+wk=",2465803590501142744,-143143280884529649,54594906744564516,3677969709524469938>()) {
                           case -1645137254:
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

      switch ((int)com.yiyiaddon.m.b.a<"s1abbn6y7wubfy","iWvGsAsvC+oOB5l98e2HjhWU6C0CImHlS1riLx0VFno=",1277435297253787522,-7227320035530728970,2708612206399908181,1781606632815683918>()) {
         case -374142579:
            return false;
         default:
            throw null;
      }
   }

   private Integer C() {
      if (this.f.bC.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"syh1uqblky9ty","2Cp5esM5QlJ0lI4bU21ZW1uHZqUcFlrznWClWRQLlMc=",-4816603773899125568,3002946091613643116,-5461725085661923978,8338420090999864264>()) {
            case 862837504:
               return null;
            default:
               throw null;
         }
      } else {
         Minecraft var1 = Minecraft.getInstance();
         if (var1.player == null) {
            switch ((int)com.yiyiaddon.m.b.a<"shhek31obcm09","paRvkzz5ad66Ikfx6wZ0gDxn2FAt4KTF81wORC0XIxk=",4935116076910474062,2213066033681315325,6163320958809102445,-5976880131764897305>()) {
               case -1522277441:
                  return null;
               default:
                  throw null;
            }
         } else {
            s var2 = this.a();
            if (var2 == null) {
               switch ((int)com.yiyiaddon.m.b.a<"s3nyob4xkg06s","/Qpos89vQyaSt2y478+ORVNKK5+vkAY+0ejWYtXX96s=",5339704055443795236,6997970908342515397,8414592296226744188,8266074167092502009>()) {
                  case -1424258681:
                     return null;
                  default:
                     throw null;
               }
            } else {
               int var3 = this.f.a.a(var2, false);
               if (var3 < 0) {
                  switch ((int)com.yiyiaddon.m.b.a<"sw92td7mss2va","2Wfg4F2T3pIaeIr1loiZ1CNm296lhEPwMyGfUe2k8oU=",-7915876941950393054,2427624632072384569,-8509908803315103794,4696547274518733047>()) {
                     case 396343772:
                        return null;
                     default:
                        throw null;
                  }
               } else {
                  return com.yiyiaddon.e.n.p.a.a(com.yiyiaddon.e.n.p.a.a(var3));
               }
            }
         }
      }
   }

   Integer D() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1aegiry8rip82","ik1EtzVPSQVTbLWgI1axWagFEr9CuWBw8sWy6cARZqY=",7962667265359858254,-4002014011540953442,5235738893758258642,8890367130307839766>()) {
            case -1498194998:
               return null;
            default:
               throw null;
         }
      } else {
         return com.yiyiaddon.e.n.p.a.a(var1.player.getItemInHand(this.b));
      }
   }

   private s a(List<String> var1) {
      Iterator var2 = var1.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s3i58vwnkqg0oe","Dfz5DTOBcEDm8D4Zu/JBQBYq5Bgpp12Lf/osXcsHXRs=",8799845925764519345,-8093131265851827427,851418981437284522,2608206902723193218>()) {
         case -587961578:
            while (var2.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s14cv8h2ljjv5h","3ruhiRn593adzhqH4q8XbeuhJqZlbtBqQNDEqShPgZw=",4046663706733640942,-7316815903381978014,523829398992314402,-476507270386482957>()) {
                  case 1732604698:
                     String var3 = (String)var2.next();
                     s var4 = this.f.a.a(var3);
                     if (var4 != null) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1pesvywmo0c0c","+KosIzuB/P/ZaiOHPmv5UFiWwSdcg7H+z2kOc6gVjJg=",-4333816681715004709,4326768243606204473,3372723098245887645,-8959925206571655552>()) {
                           case 7742773:
                              return var4;
                           default:
                              throw null;
                        }
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"stowioyp4josn","It5nsJhVy3CinGSEw1A6YCpSUy2RzJopjgxORKl7zTM=",-3001919639759273461,1468351955458703140,9041022645444513738,1780803807194489344>()) {
                        case -1985372967:
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

   private s a() {
      t var1 = null;
      t var2 = null;
      Iterator var3 = this.f.bC.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2e6keu8ijwpgj","g/M5bXkysOKHZ5MepLjL/90e+gshpSktjfZmEaMwe/I=",-6822337664987862401,-4203872047396070301,9057094513135958230,1773733894016546604>()) {
         case -741604374:
            while (var3.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s17oc1ifrmbgd5","vGfHgE1zcr7pqr1JVXYDkeeV049IIriqRG5DOWdVECc=",-1302960537709819472,6457485776932253020,7802810964778394984,4743449190264966288>()) {
                  case -2067322580:
                     String var4 = (String)var3.next();
                     s var5 = this.f.a.a(var4);
                     if (var5 instanceof t) {
                        switch ((int)com.yiyiaddon.m.b.a<"sg0h5ju94pkjv","xtYYDhwAc99PuiBh4wJXLwSfwNX9BJWFFE3ZZSZpwzw=",4089035453790727744,599048063346295396,1238983566167005715,7481997311815803560>()) {
                           case 98108521:
                              t var6;
                              label88: {
                                 var6 = (t)var5;
                                 if (var2 != null) {
                                    label66:
                                    switch ((int)com.yiyiaddon.m.b.a<"s2pybeeq04tw0r","Ruks1VHXp62W9ChQLU2EgJn7TmTk8KxKiI8mpGYVKL0=",-1033755752364928235,1108265653340074350,5372523885985664011,-4221425583145786405>()) {
                                       case -1151530763:
                                          if (var6.cf() <= var2.cf()) {
                                             break label88;
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s571euqbka957","xA7WG6CuplMtyLlyhkoqDbKvljSNaJLmXxrPPtLtPnU=",-975751408378623340,3834376390389511896,-9082641096426222906,-6901116191250956510>()) {
                                             case 693876587:
                                                break label66;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 var2 = var6;
                                 switch ((int)com.yiyiaddon.m.b.a<"s30mn4miy3qy37","nal5dkDNEoRt3CmBMQwQlCi+iwzfc5oMTe8NK6xSjIk=",7495317318093236511,7552129022261009180,6191476959228516051,-5641733102751917909>()) {
                                    case 1015879928:
                                       break;
                                    default:
                                       throw null;
                                 }
                              }

                              if (this.f.a.a(var6, false) >= 0) {
                                 label58:
                                 switch ((int)com.yiyiaddon.m.b.a<"swkzsyscdewtf","q5MjZB8oSXAGG1OlEbJi9+P7L3UotkNm9a2fHsF0RWE=",-6015389996981250429,5859696404504089780,7518748919439877050,5094917289797929760>()) {
                                    case -1236101565:
                                       if (var1 != null) {
                                          label62:
                                          switch ((int)com.yiyiaddon.m.b.a<"s3raes3ghuce7q","TGPyZohm1XbsJGTrp0T+RxbQ6JlV130bRLd13T/wowo=",2478330501373884402,6446286923329087823,-8768995283051862787,-8445960026847273151>()) {
                                             case -1964131891:
                                                if (var6.cf() <= var1.cf()) {
                                                   break label58;
                                                }

                                                switch ((int)com.yiyiaddon.m.b.a<"s2r7q91yb5i3vi","Q6iD/qQW1X0wysMwBtq0tBxYzSQewItj0G/w+IBZOZM=",-1712662504032503900,199241271213027212,-6052786674120869606,4401285008281360616>()) {
                                                   case 1284726022:
                                                      break label62;
                                                   default:
                                                      throw null;
                                                }
                                             default:
                                                throw null;
                                          }
                                       }

                                       var1 = var6;
                                       switch ((int)com.yiyiaddon.m.b.a<"s111j5q4ldzc5s","0qCW59mXOtYX8M9s6nX8tmz80K+8bfhmF7XYQOl2Pw4=",-4582271346612224057,1465171558702909791,-7645989668407743900,-194581078119738454>()) {
                                          case -577292027:
                                             break label58;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s1230yega5sfkr","OQIEGpzUFa0gI3QH5T3QO/cbzo10Cb6qfa17bFbgB0A=",-1809302618780249913,1816664758528563098,-6577897694462817827,1089685510503587791>()) {
                                 case 475747979:
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

            if (var1 == null) {
               switch ((int)com.yiyiaddon.m.b.a<"sg5k4mx96zsyg","ee5ItAlA+ft7IuBQV1oMfX7kGzsI5NqXShZdKNaHyPw=",5725658855153824392,4753635968124938790,-5420803433194536894,-2380248308251365558>()) {
                  case -684171073:
                     switch ((int)com.yiyiaddon.m.b.a<"s1dmr39gn45why","sD5lOGjr6uvhv7eQoSMnIGchbjlbHZfQBCdM/tlWh/w=",-9074375037867843818,5441991564340297492,3242506341576600959,5840130433883273372>()) {
                        case 1740247220:
                           return var2;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               switch ((int)com.yiyiaddon.m.b.a<"s3t75mlov5q3g0","S1CHpLI+NlQIQiMRuJUNdmav+GkuTddj6b6fvbmz3Lc=",5395906059257649443,5435309172152617314,-5224958798116901931,8782004817765432944>()) {
                  case -358042089:
                     return var1;
                  default:
                     throw null;
               }
            }
         default:
            throw null;
      }
   }

   boolean el() {
      if (!this.f.bA.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"sb6auuc1m2ynv","lj/qBbU2y9ZxpRPZL2A6LQb2uFv5K81r2KoyqsQAMGM=",-376380156562388534,4880436496028534873,7228234412488882880,-4587641128426062535>()) {
            case -138363803:
               switch ((int)com.yiyiaddon.m.b.a<"s1r317675b9xq6","SdjYIt3Y6OAmskVlYBDCPluJJ6VwZOxdJRwT4Na7k7E=",3923164541578709323,-153010104586299055,-4538981723157564662,-5154266765265914795>()) {
                  case 189140770:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"szhr7su5any5m","CShU82HHkYANGn3JSKS94siAKGYoYDPfw7brQ6zEz3Y=",6342981599276488227,-7342044996625985428,1162903437331309679,-8332837524658679003>()) {
            case -1726787302:
               return false;
            default:
               throw null;
         }
      }
   }
}
