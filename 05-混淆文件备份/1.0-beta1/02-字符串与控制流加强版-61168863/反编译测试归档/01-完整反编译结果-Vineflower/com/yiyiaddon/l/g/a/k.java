package com.yiyiaddon.l.g.a;

import net.minecraft.client.Minecraft;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public final class k {
   private k() {
   }

   public static boolean a(Vec3 var0, Vec3 var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1wswxn6mudnl4","90apeENntJTCEMYRQLtDKFj1iW/KITalh+ePOP6BKyA=",-5274818163756289475,7936043096200501641,8526163801150850274,300739071565844625>()) {
            case -1781715473:
               if (var0 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2z5sjvnzgm044","3WG6H9egD6671OV5gqH2IG7+wd5n9H5oNOAeB1bOAkY=",5426869535338200120,-6909730303658725301,3439401535111317922,7658813013570136408>()) {
                     case 1389786537:
                        if (var1 != null) {
                           BlockHitResult var3 = var2.level.clip(new ClipContext(var0, var1, Block.VISUAL, Fluid.NONE, var2.player));
                           if (var3.getType() != Type.MISS) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3akex73nebrvu","+b1ACBaxIK23FlxqP3YpQNv72uDSQQsjXgJX5VcIoJA=",9098398828909155916,-1406517012205348427,-1936711672589847466,-5121759145995234262>()) {
                                 case 1855716280:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3nfgaqzxr8s1w","kceX9NDbFDgMhVIzVIT+MjlpwmZdu2da9ctPNvPfcjQ=",-1919585428728691273,-1614298221950858775,3941470011205210271,-1552494321957600981>()) {
                                       case 1157498314:
                                          return true;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           } else {
                              switch ((int)com.yiyiaddon.m.b.a<"spmy1jjq1vgja","42gsWN3mAe85inAa14e9jQJlF/yi57GKd79ulVP2jiU=",6417004901156404411,-930869815717923791,-2960685827911097885,8860997738373508553>()) {
                                 case 86553952:
                                    return false;
                                 default:
                                    throw null;
                              }
                           }
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1rz1xmijkcgbi","/6DZASoV5Atbq5NzEh3y30mvgQk/QT5ZML8/VXCHetU=",-8433043544075621386,-6341313467973671058,4363441352069845218,4596468914947246905>()) {
                           case 735663061:
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

   public static boolean b(Vec3 var0, Vec3 var1) {
      if (!a(var0, var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2jry82yzi2bd6","8I4mPSgo1RdhurgpeKc+NkVC0jUJIWgBchcy4Y2fYXY=",-2334286117855863049,6303152558643655131,7771457803817393088,1173737061995162692>()) {
            case 1891349871:
               switch ((int)com.yiyiaddon.m.b.a<"s3vfgr52v04d4z","crAbBfFenFiwl/UtNRqZcP+R+Dy0d8s1zsjqvT4xNMY=",-6674973129473814880,2947613567860166823,3452202821304532993,-1588404883152322223>()) {
                  case 810105199:
                     return true;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"s2edceit7l5agt","iCyfuv9kapVDqs5rM2FIoeFiqzSV23TioyfRVG6iOkk=",4573188687932268318,878783955590848678,-5686893535496013185,-5607325112020180296>()) {
            case 1956545091:
               return false;
            default:
               throw null;
         }
      }
   }
}
