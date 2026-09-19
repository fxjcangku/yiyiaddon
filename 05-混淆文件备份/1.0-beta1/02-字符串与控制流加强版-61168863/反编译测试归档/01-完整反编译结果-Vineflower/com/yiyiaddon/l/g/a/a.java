package com.yiyiaddon.l.g.a;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;

public final class a {
   public static final String Ga = "global:block-outline";
   private static final float kz = 2.0F;

   private a() {
   }

   public static void render(f var0) {
      e var1 = e.a();
      if (!var1.gm()) {
         switch ((int)com.yiyiaddon.m.b.a<"s3tzw9zrkb4yt0","84msmlfzWPZBuPdPIr84HDVSQ61Pu+iYUneIkOGnGZE=",-4226581500731161308,902160729367281054,-6966669903666588339,1433029260976109677>()) {
            case -246289923:
               return;
            default:
               throw null;
         }
      } else if (var1.gp()) {
         switch ((int)com.yiyiaddon.m.b.a<"snceitsv69x96","I86Xqr1f3AYlHS0aJr3w5vW1T96PKgG0ecoj0z0dpT4=",-7040126744879466491,5144614585375004470,5012773788355110203,7227814828794796532>()) {
            case -1703159468:
               return;
            default:
               throw null;
         }
      } else {
         Minecraft var2 = Minecraft.getInstance();
         if (var2.player != null) {
            switch ((int)com.yiyiaddon.m.b.a<"sgxsbh6scf6rf","H1JuNaUWi2twMw9QFfT8Hni2SYUs07umyCKxUg1au64=",-5775702056820331681,-1627273834996458772,-4133874565158530039,6942226131962398802>()) {
               case -2026374463:
                  if (var2.level != null) {
                     HitResult var4 = var2.hitResult;
                     if (var4 instanceof BlockHitResult) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2rixmcawuucv9","UUFETZr1d/RIYiIItVO8lJ72xttMT+Gh2PogdL+PDHY=",-3723674186733740390,3013444626421480959,-4924608940003260014,9208919376971940543>()) {
                           case -1496674439:
                              BlockHitResult var3 = (BlockHitResult)var4;
                              if (var3.getType() == Type.BLOCK) {
                                 BlockPos var6 = var3.getBlockPos();
                                 if (var2.level.getBlockState(var6).isAir()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1efa6evwx0qyc","f/7U+pyVkZ9kmriswmcu0aAZ/cKtyfKUTM+edWwL5rM=",-123263895972308197,21751867069496599,-8693507889521294053,3388924811793444660>()) {
                                       case 1217474931:
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 int var5 = 0xFF000000 | var1.eo() & 16777215;
                                 var0.a(var6.getX(), var6.getY(), var6.getZ(), 0, var5, j.Lines, 2.0F);
                                 return;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"sxymgsxzksj4b","Nj+zp+cvSmzKwFbzFhcQfi97DDBx81VPhWQr8sAOnXA=",-6800524306780421946,3628077131532649274,265521099081805640,-5694499777251503570>()) {
                                 case -2113647469:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s1alr9zn365l95","/9KP1C8NooXjN3/t6C+maPzkLV/QY7OX8FFhoKbIKuY=",9112448256618452179,-8793155438448913065,4739356251953300401,-706725362873776178>()) {
                        case -959635628:
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
}
