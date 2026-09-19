package com.yiyiaddon.e.c.c;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public final class h {
   public boolean a(com.yiyiaddon.e.c.d.d var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sl24x3qxjnw1g","uMBXnmcBanxMFxgFbdXcSFNJk8M3xJeuRUZacHZRSU8=",-2260552611304758679,8471004390029629664,-6425642899567562808,-5191760058751905439>()) {
            case 1784035772:
               return false;
            default:
               throw null;
         }
      } else {
         BlockState var3 = var2.level.getBlockState(var1.a());
         if (var3.isAir()) {
            switch ((int)com.yiyiaddon.m.b.a<"s1a2bbxbdagpyv","Xnk7srE5cJtvLvUh63bnrDhHXhsgpHuIxhSilE3D7lg=",7290523085820717015,-7963293405103421149,7939274363934443821,-1988773811785614295>()) {
               case 683683695:
                  return true;
               default:
                  throw null;
            }
         } else {
            com.yiyiaddon.e.c.d.a var4 = com.yiyiaddon.e.c.d.a.a(var3.getBlock());
            if (var4 == null) {
               switch ((int)com.yiyiaddon.m.b.a<"s2efuf7p5wi2rn","ZQ/9VqUsThezMLKOw98WBbl6MHxLy2nmP0GFGCOHaVk=",-5231699588341011345,-6559137101640366074,5975345825217085228,623851684108235761>()) {
                  case 2097177260:
                     return true;
                  default:
                     throw null;
               }
            } else if (!var4.a(var3, var2.level, var1.a())) {
               switch ((int)com.yiyiaddon.m.b.a<"sopxknv0uqsu5","bSVOaznZskNYeNh7Akedlo8PvPp2I8YpQIeDldzHO6M=",6938542734030870957,4467767827388492612,-5269706227555265387,-6136718902752223290>()) {
                  case 394805465:
                     switch ((int)com.yiyiaddon.m.b.a<"s166lwghlmyw8h","DLERcPBD2qx8n7VZqmeYHenoxS93EJrBxxZMmZk2dv4=",2805968750529460915,-1739998606604176881,930369491160529994,5106164495432027161>()) {
                        case -1317744676:
                           return true;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            } else {
               switch ((int)com.yiyiaddon.m.b.a<"s11ek3aoy44dpf","c9sXM3DBdblUs9O17kcKxoIaYDffFPH3zMLAJfU+2bI=",7106316826869633170,-7112789337221403871,9175507540769036116,8164133235351880486>()) {
                  case 114867811:
                     return false;
                  default:
                     throw null;
               }
            }
         }
      }
   }

   public boolean b(com.yiyiaddon.e.c.d.d var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3je3bi2yf33h1","QwLzJD8lVZuHjSFA0LSMSPRdrHYlZNmzL2WdDkkTlAk=",8953521959918488041,-8586556242042718753,-465818053918196029,5216960375249131138>()) {
            case 539299567:
               return false;
            default:
               throw null;
         }
      } else {
         BlockState var3 = var2.level.getBlockState(var1.a().above());
         return var3.is(var1.a().a());
      }
   }

   public boolean c(com.yiyiaddon.e.c.d.d var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s13pf4nhirk5fy","GFCjEa3Okpgejq2CXEgqvBOAkBDgm89HZ8gq4nEf5Ok=",-540206216587498682,5402627041528040036,-943713583852080984,-2747834897840076333>()) {
            case 1754441282:
               return false;
            default:
               throw null;
         }
      } else {
         return var2.level.getBlockState(var1.a()).is(Blocks.FARMLAND);
      }
   }

   public boolean d(com.yiyiaddon.e.c.d.d var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s22ibdnhfgeo9b","RAT4vi/8aohDNAKriNsEnBLOZ0bO8XVvCW+eHrj0q2Q=",-7281236238708679849,3731871199356841418,3212448212464042323,-8185276684912864811>()) {
            case -783589775:
               return false;
            default:
               throw null;
         }
      } else if (var1.a() == com.yiyiaddon.e.c.d.d.a.HARVEST) {
         switch ((int)com.yiyiaddon.m.b.a<"s1m56hq0jtyvwc","MFQhHm8NtrEOKuGA3sE+J7GZXXnourcwwgV3GtVKqiM=",-7679625415635155333,1968767342242871909,8359708852833445000,-3215762658046124538>()) {
            case -1945429148:
               BlockState var3 = var2.level.getBlockState(var1.a());
               com.yiyiaddon.e.c.d.a var4 = com.yiyiaddon.e.c.d.a.a(var3.getBlock());
               if (var4 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"srghk06qp1lv9","I9d+XPLHyZ7O4bmJoHweVaeqY6gnLzwJGUvaheR04tI=",-5561513714503575619,4786598308582819231,681932936957972883,-7488286865393772281>()) {
                     case -1964987800:
                        if (var4.a(var3, var2.level, var1.a())) {
                           switch ((int)com.yiyiaddon.m.b.a<"s3p2js6vqaa7ym","wb0gsPbImEUTdubU63RqjSAml7nZJYq81jeIPau9ydQ=",-8787935768817909336,4734408915847465857,5736942904559898114,1141586532265145626>()) {
                              case -1280979496:
                                 switch ((int)com.yiyiaddon.m.b.a<"s31plxwbgimjdh","esl+3EWh7HpnnuOuYwfmoaNH+aW12nEi2xtDnO6wWso=",-5729795297316301175,7004478633363959857,8354822008940875524,-3093940858758519365>()) {
                                    case -1637946494:
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

               switch ((int)com.yiyiaddon.m.b.a<"s3gnad4d845ps","SJDO5+9Se3CHOqA9opSpsUO+9Qa26DWhwlqZfErhqEo=",-555762402477094393,8236914361582690273,6499048058772709807,-4730177250234031529>()) {
                  case 344065026:
                     return false;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else if (var1.a() == com.yiyiaddon.e.c.d.d.a.TILL) {
         switch ((int)com.yiyiaddon.m.b.a<"s1n28ax1r1g9nn","QKc+4rIfrwxja2fFWzPL8sDD/9/c129Vy/f64XYmWI4=",-2588317036096035451,-4639077394688067460,-6020074651872425161,62284775103383824>()) {
            case 135953773:
               return com.yiyiaddon.e.c.h.a.b(var2.level, var1.a());
            default:
               throw null;
         }
      } else {
         return var1.a().a(var2.level, var1.a());
      }
   }
}
