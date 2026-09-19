package com.yiyiaddon.e.p.f;

import com.yiyiaddon.m.b;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.game.ServerboundMoveVehiclePacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.Pos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public final class a {
   private a() {
   }

   public static String b(LocalPlayer var0, ClientLevel var1, com.yiyiaddon.e.p.e.a var2) {
      Vec3 var3 = var2.a.b();
      double var4 = var3.x();
      double var6 = var3.y();
      double var8 = var3.z();
      if (var0.isPassenger()) {
         label56:
         switch ((int)b.a<"s1dnc09jd335gk","nZRFCwi54WWnqYMYBUitltLpYLy++QsnwEpYOZGhYs4=",7301860961935225570,7579492485197482707,-8374994910234938518,8161971926063110889>()) {
            case -1290286225:
               if (var2.eN) {
                  switch ((int)b.a<"s2mvbherykrtp0","vlHq58RjVrAWlS7jIb+52gJoWPr/vPJJyl70Fi6pR0U=",9000711520526214168,-5928918947485847348,-6518720727196297695,-6169931056170487227>()) {
                     case 1886001632:
                        return (String)b.a<"s36j8ot7awyqmu","OPsKdwYeTpKCON1c9Y/rzr1UcYEfhbRkv3k1WlZAYk6WvLAv/XVN76E8TPXV6QFY2FwPJ2sN4iM6SvomD9A1Gz3P050zC8Ss1IUjg+MAMxV1I7BY9cirkNbUFQi4+5Vx7Ho=",5339041182868859539,7216781018666515244,8777756145996949168,7805527419550675253>();
                     default:
                        throw null;
                  }
               }

               Entity var10 = var0.getRootVehicle();
               if (var10 == null) {
                  return (String)b.a<"s3l6t9b3yvxka9","wPBb1iy2Cf48D3DneZHrzGpIjbm8dxHTaWRaHEyufiEK7gwm9ATqA7PuANpsAWCC45zxoaIKTOJHEw==",485439167883195155,-1628462536821519804,-1103783901393861493,165237283670995762>();
               }

               switch ((int)b.a<"swh8900rn6p0c","zKJt/FOEElMMXDQNJRMwSIR2frl16XxN8sNA3AhfUq4=",-7769511545061535784,5805931522934313649,-1830432966429210672,-4755584237633153229>()) {
                  case -795509062:
                     if (!var10.isLocalInstanceAuthoritative()) {
                        switch ((int)b.a<"s14s28jeqhmax2","0dI5x02ZmLVtlwtHzFC7tQVBjksvUOPgkeBZytcT8Cw=",-4773610659923893398,-6897960446765014692,-4401036361795872423,-3022909902777687564>()) {
                           case -1055656400:
                              return (String)b.a<"s3l6t9b3yvxka9","wPBb1iy2Cf48D3DneZHrzGpIjbm8dxHTaWRaHEyufiEK7gwm9ATqA7PuANpsAWCC45zxoaIKTOJHEw==",485439167883195155,-1628462536821519804,-1103783901393861493,165237283670995762>();
                           default:
                              throw null;
                        }
                     }

                     Vec3 var11 = var0.position().subtract(var10.position());
                     Vec3 var12 = new Vec3(var4 - var11.x, var6 - var11.y, var8 - var11.z);
                     var10.absSnapTo(var12.x, var12.y, var12.z, var10.getYRot(), var10.getXRot());
                     var0.connection.send(ServerboundMoveVehiclePacket.fromEntity(var10));
                     var2.eR = true;
                     var2.a = var10;
                     var2.h = var12;
                     switch ((int)b.a<"s2z4mj6r79c80n","YXMASuZ4Z5FtRCP3tNuIQdNgoiKanq30AgWhhN27Brc=",5399828306548830220,-2228557783756348443,-5580860151244113686,-4193642468204373668>()) {
                        case -1408947950:
                           break label56;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         var0.absSnapTo(var4, var6, var8, var0.getYRot(), var0.getXRot());
         if (var2.eN) {
            label43:
            switch ((int)b.a<"s2oxblzwsthqu3","CB1TAXmzP8CeO1WABzmADyvO8zHU7MHF0MR9fdd/6SY=",-6550572807832613187,2813757303342168354,7349804567163144930,7922571345778117144>()) {
               case -1595444292:
                  var0.resetFallDistance();
                  var0.connection.send(new Pos(var4, var6, var8, false, false));
                  var0.connection.send(new Pos(var4, var6 + 0.001, var8, false, false));
                  var0.absSnapTo(var4, var6 + 0.001, var8, var0.getYRot(), var0.getXRot());
                  var2.h = new Vec3(var4, var6 + 0.001, var8);
                  switch ((int)b.a<"s1vu95my7bii23","gAaPKIMod3b+sbfQdt4VbUZmWpLGtB8imU0o8UkY6Ng=",-2487559068501081859,-3969151336061571888,-3577287067123786092,-6978465023770183346>()) {
                     case 346132416:
                        break label43;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var0.connection.send(new Pos(var4, var6, var8, true, false));
            var2.h = var3;
            switch ((int)b.a<"sep1nrw3uotj2","jITZ3jYWu3/BXKyhe6TLNVNcwWQsm40QK+F9N1ZV6Dw=",-8294603154188676854,-4783152743920862308,-7836323400667196406,-4789162884932018080>()) {
               case -1197436483:
                  break;
               default:
                  throw null;
            }
         }

         var2.eR = false;
         switch ((int)b.a<"sl56e28inn272","nxiIBSq0Ccd+ggEjBVlPXPxJQZSeSaGnhORNfwtg/ic=",-579830193453981068,-4417739145925609986,7817476713555305512,7309070715178106152>()) {
            case -404314351:
               break;
            default:
               throw null;
         }
      }

      var2.aw = var1.getGameTime();
      return null;
   }
}
