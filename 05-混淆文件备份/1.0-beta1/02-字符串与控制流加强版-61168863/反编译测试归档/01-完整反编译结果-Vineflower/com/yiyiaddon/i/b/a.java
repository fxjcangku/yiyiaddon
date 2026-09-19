package com.yiyiaddon.i.b;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;

public final class a {
   private a() {
   }

   public static boolean E(String var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2tezryxtsvuia","kge0gTgyqNEEp9jN0QNGAJngFWS2KTGcUbc2T/MHtmI=",-8169005194603446515,-2293120056546749390,-2635944484854072831,666219069077176829>()) {
            case -1127008819:
               if (!var0.isBlank()) {
                  Identifier var1 = Identifier.tryParse(var0);
                  if (var1 == null) {
                     switch ((int)com.yiyiaddon.m.b.a<"s33noblq34iaa3","19111BzTQ2U+2i6gPRCtKPSoC8AVKa5m5Too77wue5Q=",-907888447408676481,-6668148629082790998,-7213210755007990888,7920933317844126453>()) {
                        case -2121141022:
                           return false;
                        default:
                           throw null;
                     }
                  } else if (BuiltInRegistries.BLOCK.getValue(var1) != null) {
                     switch ((int)com.yiyiaddon.m.b.a<"svbsqi7zhmmql","l9BNHaae5OuBfikg8P9EPk4AiiC5rsWqrjTrviYHZzs=",2547181532591190532,5924995092826128948,3652382719788058085,7896376509921633495>()) {
                        case 5630247:
                           switch ((int)com.yiyiaddon.m.b.a<"s11h8oog9mmkjl","yY/1mTU35T2sc8luLw6W51JTFWFLbwJdjLtpBekYZ3k=",-929184099269075434,-3180524860260147905,8882649020748029046,9005464780416946594>()) {
                              case 1051497988:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s31pvugvdua0nx","uxxqBjui/p7550q5w57tNxfaHXgMmwjZToslN5N5EUs=",6245976930717804099,8330013416504309935,3849062001962759579,-8896667842911508837>()) {
                        case -1168050694:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1j9ou7e5h16xv","+6GUkwnMjcHW5uqsstLQOdfWV8olPXL7/+xqahUZLe0=",-7364119157223555333,2913904721013677288,-4889223087509548323,-3626316556037346153>()) {
                     case 867843411:
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

   public static com.yiyiaddon.g.c.a a() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0.level != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s21ku72fsslhqu","ghgkhOGozTQYAMwtuXq3/OItvySTuesbPzO1DFNyJ1w=",-720691322395413213,-6849720298658769351,5037870176718529004,-2444050827002094578>()) {
            case -1419592800:
               if (var0.player != null) {
                  HitResult var1 = var0.hitResult;
                  if (var1 instanceof BlockHitResult) {
                     switch ((int)com.yiyiaddon.m.b.a<"s17d5pl2mt6kif","awPLQVI4VZ4mJFLARVqeXCf5sjnrgAzwBS9kUByTAVY=",-3264628740389400961,109854023508933353,-3490104182044983147,6695322701830095376>()) {
                        case -1672896147:
                           BlockHitResult var2 = (BlockHitResult)var1;
                           switch ((int)com.yiyiaddon.m.b.a<"s15dnfwh9zhgg1","/sAawjFVx3jyrj3bc6Q/XiLe7eBFG2fzZT9Vm5Ht+fc=",5610332020775332808,4368713102189690828,-2944509337990340446,-2574147522846630645>()) {
                              case 72422814:
                                 if (var1.getType() != Type.BLOCK) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3l7l9c20gaxxk","ZEO/t7TFkCX5T6/FSrXJfk19vQQRFfLdsk7Oouwx0b4=",-8411773315535413043,-4031260971110863971,5669751755578914191,802527010374326270>()) {
                                       case -1469044767:
                                          return null;
                                       default:
                                          throw null;
                                    }
                                 }

                                 BlockPos var3 = var2.getBlockPos();
                                 BlockState var4 = var0.level.getBlockState(var3);
                                 if (var4.isAir()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s18f8ea88vf7nh","iidjedftg9YVimXjCoaWlvfs0ng+ZKPe0/+vpGTRnHY=",-327047318472380815,497157821244248782,-6551312904142396660,-2383242215653950027>()) {
                                       case 2093232419:
                                          return null;
                                       default:
                                          throw null;
                                    }
                                 }

                                 Identifier var5 = BuiltInRegistries.BLOCK.getKey(var4.getBlock());
                                 String var6 = var5.toString();
                                 String var7 = null;
                                 String var8 = null;
                                 BlockEntity var9 = var0.level.getBlockEntity(var3);
                                 if (var9 != null) {
                                    label36:
                                    switch ((int)com.yiyiaddon.m.b.a<"s3ij7z7khsufj4","IOuiIBjpiTUMVYAWFJhgJ8SeacigzlZYMtScLYEWKB4=",7325495438141948716,-4105563122145105243,-3036291581193554966,46560886137003131>()) {
                                       case 127834467:
                                          var7 = BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(var9.getType()).toString();
                                          var8 = a(var9);
                                          switch ((int)com.yiyiaddon.m.b.a<"s1fr25bubn8d3t","RcrtHFKcz12uDYESy6payZ5jpfyAiLQCYTAw9p4igts=",-563971268940787050,-360931538626926744,-1393666648955162330,8059553006292050772>()) {
                                             case 1769076083:
                                                break label36;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 com.yiyiaddon.g.d.a var10 = com.yiyiaddon.i.e.a.b(var4);
                                 return new com.yiyiaddon.g.c.a(
                                    var6,
                                    bQ(var6),
                                    var4.toString(),
                                    var7,
                                    var8,
                                    com.yiyiaddon.i.g.c.bU(),
                                    com.yiyiaddon.i.g.c.fG(),
                                    var3.getX(),
                                    var3.getY(),
                                    var3.getZ(),
                                    com.yiyiaddon.i.g.c.aA(),
                                    var10.ea(),
                                    var10.dv(),
                                    var10.a(),
                                    var10.dG(),
                                    var10.ge(),
                                    var10.br()
                                 );
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return null;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sc42762qscijf","V290Z7aZkQHGk03K53L7V37q/8rTv6hzrW4YVGiu1og=",857425709986733485,-2169706351801911408,-7014528667754062852,-2588929804675948069>()) {
                     case 1379033376:
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

   public static String bQ(String var0) {
      try {
         Identifier var1 = Identifier.tryParse(var0);
         if (var1 == null) {
            return null;
         }

         Block var2 = BuiltInRegistries.BLOCK.getValue(var1);
         if (var2 == null) {
            return null;
         }

         String var3 = var2.getName().getString();
         return var3 == null
            ? null
            : var3.replaceAll(
                  (String)com.yiyiaddon.m.b.a<"s3i6g23ks13b9x","06nsUO5KDVfzE05anPnYkQc4+OPBhdRs9oT1309M46O+/aaCSHbc9p1IPEICv1Rxn4dQWiscNE6H7cs7XbY3sBmVH76J3A==",-8993126626578282282,2852726405283981928,1247220583422950182,8072455567224448496>(),
                  (String)com.yiyiaddon.m.b.a<"s3nrfpkgi85i1b","D2faYgaYDbWDjy7i34st0Ml7HKBN+LWnrGAQYA==",-6636906403124323920,-8872522774984451292,3250618215660085795,-590835612056703230>()
               )
               .trim();
      } catch (Exception var4) {
         return null;
      }
   }

   private static String a(BlockEntity var0) {
      try {
         CompoundTag var1 = var0.saveWithoutMetadata(Minecraft.getInstance().level.registryAccess());
         return var1 == null ? null : var1.toString();
      } catch (Exception var2) {
         return null;
      }
   }
}
