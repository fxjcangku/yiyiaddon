package com.yiyiaddon.e.q.h;

import com.yiyiaddon.l.g.a.d;
import com.yiyiaddon.l.g.a.e;
import com.yiyiaddon.l.g.a.f;
import com.yiyiaddon.l.g.a.j;
import com.yiyiaddon.m.b;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class a {
   private static final double bn = 64.0;
   private static final float ee = 1.5F;
   private static final int rd = 1;
   private static final int re = 3;
   private static final int rf = 255;
   private static final d R = d.a(1, 255);
   private static final d S = d.a(3, 255);
   private final Minecraft as = Minecraft.getInstance();

   public void render(f var1) {
      if (!e.a().a(e.a.VILLAGER)) {
         switch ((int)b.a<"scoc179k2cb76","zzYz+vP3M9UWAUc2tQU2dQ1eo2CIV8HizCVfp1WHnGI=",3183083289529446875,1825837726389310347,-7025978161973002332,-3784149717905675707>()) {
            case -1555866933:
               return;
            default:
               throw null;
         }
      } else if (this.as.player != null) {
         switch ((int)b.a<"seq6ms7mw4e0f","UGibzSLLmJ0J40F0Rjk3QjlwX/o9Xh2tagtuK3o/I3A=",-4832570207740569965,7394565445003329862,6474248714016679818,7652915603542526879>()) {
            case -562483188:
               if (this.as.level != null) {
                  com.yiyiaddon.e.q.i.a.a var2 = com.yiyiaddon.e.q.i.a.a();
                  if (var2.F() != null) {
                     label35:
                     switch ((int)b.a<"so0nfpkkif6ba","XTqvSj0TwjstBHUdIxFWKoy/zdM3XrQsGYLmxgC4Q48=",-1567158601898637472,-2808090031604594639,8594351012438653124,-4972970718822741433>()) {
                        case -2085282555:
                           this.b(var1, var2.F(), R);
                           switch ((int)b.a<"s1fx7lznqgpob6","DPNR1GmaMWshgz6V6Y5rmr6FyiWD5hT4S7M7i02JWyU=",-1056755952968573898,3620991200010470121,6479469933804106964,2601461368525073101>()) {
                              case 588146510:
                                 break label35;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  if (var2.G() != null) {
                     switch ((int)b.a<"s1tlrah24y59ei","FydmUErj1xhesEvrr8hgPNk7zxSJFwYSPOQ+NgWvUPA=",-7174829655041286652,7639441987329450515,-7192363286856094133,6624791896406635482>()) {
                        case 214025238:
                           this.b(var1, var2.G(), S);
                           switch ((int)b.a<"s1mhphf54qmrai","4Xmlqm5OyfSVMXUYasK96cgLkShOIsbGI3JFjCo2wwA=",-11529048238675516,2851660041773682904,-7814962473267741240,-5054178558951358217>()) {
                              case -189304160:
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
                  switch ((int)b.a<"sfb1f59yuti2g","7rZNWcWBRC5xSddrApEikmUundqIpfZcpvwVFXaRmvQ=",-5230708498291798149,884066965107779202,3051608616423456463,-605768952929560951>()) {
                     case 1118864443:
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

   private void b(f var1, BlockPos var2, d var3) {
      Vec3 var4 = this.as.player.getEyePosition();
      Vec3 var5 = new Vec3(var2.getX() + 0.5, var2.getY() + 0.5, var2.getZ() + 0.5);
      if (var4.distanceTo(var5) > 64.0) {
         switch ((int)b.a<"smlk76jjftw78","2FmlLlXYcB0XPhCnTtbinXAZ5uXQ1UA/LWtUKbf8HjQ=",443890639397547306,-5223393300060021954,-3574100214148400023,-3219546418169042869>()) {
            case -487553241:
               return;
            default:
               throw null;
         }
      } else {
         AABB var6 = b(var2, this.c(var2));
         var1.a(var6, var3, var3, j.Lines, 1.5F);
      }
   }

   private BlockPos c(BlockPos var1) {
      BlockState var2 = this.as.level.getBlockState(var1);
      if (!(var2.getBlock() instanceof ChestBlock)) {
         switch ((int)b.a<"s2lc32w3k8di4p","kyiuFHx7EqZ5tT85WxbCOrOgAnUd9KUWI8uOu/WlUw0=",2638229738712568302,1053022956225404824,2298532737316756732,6541354267429256029>()) {
            case 57273516:
               return null;
            default:
               throw null;
         }
      } else {
         ChestType var3 = var2.getValue(ChestBlock.TYPE);
         if (var3 == ChestType.SINGLE) {
            switch ((int)b.a<"s1c22frhd8esdb","TRoYLJ8SXPye0Pj61OBjzrb72jo7Vbo2cDncRHdzAEo=",131705447789607747,9074479668813659940,-3558543000370292152,-4441328261645420276>()) {
               case 417519693:
                  return null;
               default:
                  throw null;
            }
         } else {
            Direction var4 = ChestBlock.getConnectedDirection(var2);
            return var1.relative(var4);
         }
      }
   }

   private static AABB b(BlockPos var0, BlockPos var1) {
      BlockPos var10000;
      if (var1 == null) {
         label15:
         switch ((int)b.a<"s3bzxhdp7sbk3z","E4vinJxHkx+MSA+VNnKeaFkioxm5JxRg8xOZNb+TAgo=",-8206669839432796383,8055129318711150850,1338909042988661478,2945650793814818641>()) {
            case 1098786921:
               var10000 = var0;
               switch ((int)b.a<"shmy2jj47a89l","SLSb1hxnDo058eG7+l7Z2Hfw03Wbxyk/E1VZ26eoGOY=",-2318638498077166899,-1153635124825743999,8705484062967346018,8783206793137291279>()) {
                  case 1101467235:
                     break label15;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var10000 = var1;
         switch ((int)b.a<"s2o7eznqhxte97","n6RhM6j2HmC7WY+kQxozaxqgbD0wmf34ZL42KrRUnUs=",-3904386992898996059,-6355530559139207938,2852813161963789260,7728050336417106981>()) {
            case -1225032097:
               break;
            default:
               throw null;
         }
      }

      BlockPos var2 = var10000;
      return new AABB(
         Math.min(var0.getX(), var2.getX()),
         Math.min(var0.getY(), var2.getY()),
         Math.min(var0.getZ(), var2.getZ()),
         Math.max(var0.getX(), var2.getX()) + 1.0,
         Math.max(var0.getY(), var2.getY()) + 1.0,
         Math.max(var0.getZ(), var2.getZ()) + 1.0
      );
   }
}
