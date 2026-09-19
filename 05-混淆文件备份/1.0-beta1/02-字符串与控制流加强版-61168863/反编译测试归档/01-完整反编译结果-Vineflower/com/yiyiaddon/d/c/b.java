package com.yiyiaddon.d.c;

import com.yiyiaddon.mixin.client.ClientLevelPredictionAccessor;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.ServerboundResourcePackPacket;
import net.minecraft.network.protocol.common.custom.BrandPayload;
import net.minecraft.network.protocol.game.ServerboundPlayerAbilitiesPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerInputPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemOnPacket;
import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.network.protocol.game.ServerboundMovePlayerPacket.Rot;
import net.minecraft.network.protocol.game.ServerboundPlayerCommandPacket.Action;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Input;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public final class b {
   private b() {
   }

   public static Connection a() {
      ClientPacketListener var0 = Minecraft.getInstance().getConnection();
      if (var0 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s10jonnkm3kdub","xZgH6Kjq139tqM+MRY4w0Cz9VW3JCNOkZkWyDqf1vgo=",-3903191597178359003,5037848749753014060,5738896852133770418,-6295916836922612274>()) {
            case 208945884:
               switch ((int)com.yiyiaddon.m.b.a<"s130olbdjy1dl7","EsQHx6BzOUePT79WYLZfzlXPbDvLaEFuUoYkXoP2bqU=",-7900497707541767838,-4115482451749049505,-8893661962881817738,-3844246510594502955>()) {
                  case -1599967166:
                     return null;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         Connection var10000 = var0.getConnection();
         switch ((int)com.yiyiaddon.m.b.a<"s2fqvunotvwh1k","U88jdhjvRJl00a/KWkjGp9zhvmvfOOWh5TkdXdUsmJc=",3279766018571852216,500532781876327233,-8853565524844254980,4320690484070693135>()) {
            case -1277057511:
               return var10000;
            default:
               throw null;
         }
      }
   }

   public static boolean a(int var0) {
      Connection var1 = a();
      if (var1 == null) {
         return false;
      }

      a(
         var1,
         new ServerboundPlayerInputPacket(
            new Input((var0 & 1) != 0, (var0 & 2) != 0, (var0 & 4) != 0, (var0 & 8) != 0, (var0 & 16) != 0, (var0 & 32) != 0, (var0 & 64) != 0)
         )
      );
      return true;
   }

   public static boolean j(String var0) {
      LocalPlayer var1 = Minecraft.getInstance().player;
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2n5towgjcshky","qN3rwwL7qnafznTLRGO4/bPXXvdPRO+8b7z6IDxrMnk=",-3714277190820292144,-5367576234043389221,6669141018700175501,1747493966130987072>()) {
            case -1234544471:
               if (var0 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1ibq25pnniy0x","LCy6ouLzlW24G6ImYd9mIM9oEjwIYtxNlMBUIJswHEY=",2024202021670017410,-4467060214601815878,-4812894057205248880,3257414706673486494>()) {
                     case -1069356600:
                        if (!var0.isEmpty()) {
                           c.b(() -> var1.connection.sendChat(var0));
                           return true;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s1v474kpihbelb","H+WmI4MT4Lb+Eq5q5saQfRcd4ZNjEFN6FmNvIDu5t8g=",-3850732600771147733,2281533586051261869,3999909799317038872,4072938312551290058>()) {
                           case 1697690650:
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

   public static boolean k(String var0) {
      Connection var1 = a();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1rikhoya2cdww","873KC83XtXx58JJbDw1sDWRSiNVUJEgHtdkzAMj1roM=",-8024322299771752134,-8596404030144860548,-109840739294720891,-8407100263853598310>()) {
            case 2133084175:
               if (var0 != null) {
                  a(var1, new ServerboundCustomPayloadPacket(new BrandPayload(var0)));
                  return true;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1ptllpv7nbnpt","705ZE7Gey7UK46IHjngtRO3JZc8+8aMjw+qtMKfpT1U=",8370728259826158363,-8730358617012012167,2990962166734525700,2759437295556532949>()) {
                     case 1199767978:
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

   public static boolean a(boolean var0) {
      LocalPlayer var1 = Minecraft.getInstance().player;
      Connection var2 = a();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s382e31n9qx957","gVEZCzlxGF/PHGKWfs8yJw+3RybexILNxZrRoYaWjEE=",-243554577100562475,3130622918604536697,2233714890993825058,6125456476818265239>()) {
            case 1604173042:
               if (var2 != null) {
                  var1.getAbilities().flying = var0;
                  a(var2, new ServerboundPlayerAbilitiesPacket(var1.getAbilities()));
                  return true;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2atf4ulv4w887","g2s8BKBNdibztJqJe6ae/KLWgLMBZ6PwWgOK55jFNLg=",2631642022407432016,-8011848949984737245,8172400682852579874,-676499699260063247>()) {
                     case 1889277345:
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

   public static boolean j() {
      LocalPlayer var0 = Minecraft.getInstance().player;
      Connection var1 = a();
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"sg3bzlzbkn2l1","ZKGprYpyUYbhgStzieP4Ad9wbXsccxjVNR9Bo8rd5WU=",-5678227358744347913,1380487106885430587,-999231773838299627,-8448897608215404416>()) {
            case -1162390625:
               if (var1 != null) {
                  a(var1, new ServerboundPlayerCommandPacket(var0, Action.START_FALL_FLYING));
                  return true;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s25te1lnv65yb9","VsSkJN1HFjxt0fFltZZ7o2SKGEnfItvRcr38pnlPrNQ=",1309034407213263569,8184792166728828206,6714220361374234645,655457844885555206>()) {
                     case -441114533:
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

   public static boolean a(float var0, float var1, boolean var2, boolean var3) {
      Connection var4 = a();
      if (var4 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ydj8pmjbp7j4","ZuCvSSMtttCkq5FJX0iLmQ+Oc3uKKTOrdh/edQdoY7E=",614779091305153898,-8264518291224227956,-316449930937240824,204405694261826002>()) {
            case 1209485874:
               return false;
            default:
               throw null;
         }
      } else {
         a(var4, new Rot(var0, var1, var2, var3));
         return true;
      }
   }

   public static boolean a(BlockPos var0, Direction var1, BlockState var2) {
      LocalPlayer var3 = Minecraft.getInstance().player;
      ClientLevel var4 = Minecraft.getInstance().level;
      Connection var5 = a();
      if (var3 != null && var4 != null && var5 != null && var0 != null) {
         BlockStatePredictionHandler var6 = a(var4);

         try (BlockStatePredictionHandler var7 = var6.startPredicting()) {
            var7.retainKnownServerState(var0, var2, var3);
            a(
               var5,
               new ServerboundPlayerActionPacket(
                  net.minecraft.network.protocol.game.ServerboundPlayerActionPacket.Action.START_DESTROY_BLOCK,
                  var0,
                  var1 == null ? Direction.UP : var1,
                  var7.currentSequence()
               )
            );
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean a(BlockPos var0, Direction var1) {
      LocalPlayer var2 = Minecraft.getInstance().player;
      ClientLevel var3 = Minecraft.getInstance().level;
      Connection var4 = a();
      if (var2 != null && var3 != null && var4 != null && var0 != null) {
         BlockStatePredictionHandler var5 = a(var3);

         try (BlockStatePredictionHandler var6 = var5.startPredicting()) {
            a(
               var4,
               new ServerboundPlayerActionPacket(
                  net.minecraft.network.protocol.game.ServerboundPlayerActionPacket.Action.STOP_DESTROY_BLOCK,
                  var0,
                  var1 == null ? Direction.UP : var1,
                  var6.currentSequence()
               )
            );
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean b(BlockPos var0, Direction var1) {
      Connection var2 = a();
      if (var2 != null && var0 != null) {
         a(
            var2,
            new ServerboundPlayerActionPacket(
               net.minecraft.network.protocol.game.ServerboundPlayerActionPacket.Action.ABORT_DESTROY_BLOCK, var0, var1 == null ? Direction.UP : var1
            )
         );
         return true;
      } else {
         return false;
      }
   }

   public static int k() {
      ClientLevel var0 = Minecraft.getInstance().level;
      if (var0 == null) {
         return -1;
      }

      BlockStatePredictionHandler var1 = a(var0);

      try (BlockStatePredictionHandler var2 = var1.startPredicting()) {
         return var2.currentSequence();
      }
   }

   public static boolean a(InteractionHand var0, int var1, float var2, float var3) {
      Connection var4 = a();
      if (var4 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"shjmzme53iw8q","9oxxYplLUzHdwN3D1LkbNm2nO2FUlPiZS1UMzBlvcqo=",-8525169278451165586,2886876737647795887,-747460731823793503,2228154123889652983>()) {
            case -241933362:
               return false;
            default:
               throw null;
         }
      } else {
         a(var4, new ServerboundUseItemPacket(var0, var1, var2, var3));
         return true;
      }
   }

   public static boolean a(InteractionHand var0, BlockHitResult var1, BlockPos var2) {
      LocalPlayer var3 = Minecraft.getInstance().player;
      ClientLevel var4 = Minecraft.getInstance().level;
      Connection var5 = a();
      if (var3 != null && var4 != null && var5 != null && var1 != null && var2 != null) {
         BlockStatePredictionHandler var6 = a(var4);

         try (BlockStatePredictionHandler var7 = var6.startPredicting()) {
            var7.retainKnownServerState(var2, var4.getBlockState(var2), var3);
            a(var5, new ServerboundUseItemOnPacket(var0, var1, var7.currentSequence()));
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean a(UUID var0, net.minecraft.network.protocol.common.ServerboundResourcePackPacket.Action var1) {
      Connection var2 = a();
      if (var2 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2sh4wswxyndt1","Y2+1GPX2+Jr8cZEpiy/NYX63RSRFTB2FHJwzPKwo+0c=",-6464132652477566699,-6656237588997655954,-5768045834848251455,-466959632172935478>()) {
            case -1861820707:
               if (var0 != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2npfmmyiff43q","h+ivGMYusBFrfic30qVnP+mJ44rq6aOMHnRXeyRpF8s=",-5121184441324000908,704884145754663889,-4624399665923300548,-6354505873057188379>()) {
                     case -1170610637:
                        if (var1 != null) {
                           a(var2, new ServerboundResourcePackPacket(var0, var1));
                           return true;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2kvc2opry4f0v","2I5HJobCMDZnXxsytxvHlli6P21/Nie1//2nHKoYbr0=",-8012752443783849114,7590236910172276067,-4759411390635537115,5373226836941742033>()) {
                           case 641752503:
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

   public static boolean a(Connection var0, Packet<?> var1) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"svqs3azgh6z1m","5WSwdbpVLp7nQ8vhbtSMrI99iQCrjd9iY0rj3evOeYs=",-7445177147562692487,397624087926456124,4966966439524180731,3278944830459636448>()) {
            case 891758613:
               if (var1 != null) {
                  a(var0, var1);
                  return true;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3u6km8iz4v1va","eohBmZSNauPl3o2sNXb70ecaY91ii7G0sr/A13eCDJ0=",8872372902718151810,2348944528376316971,-1727524379178732801,3434190554024116553>()) {
                     case 1438391688:
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

   static void a(Connection var0, Packet<?> var1) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1j060f21txpcd","QQEimxyflpuqVfb0jd9iIZ9OB2Q3lMFdw+DVY+W5iIc=",6410649106104881545,-6050396311572106803,-4077470872902393673,4974109237677053116>()) {
            case -1574193855:
               if (var1 != null) {
                  c.b(() -> var0.send(var1));
                  return;
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"sf0mwa750727c","cR1xcxnF9YEkcHj7IOpuiolEnUdEZEN+Lfw83ZuNh30=",-7294270928005794988,2700461123528403224,-3624299688334241037,-8604650312411712263>()) {
                     case -1883522068:
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

   private static BlockStatePredictionHandler a(ClientLevel var0) {
      return ((ClientLevelPredictionAccessor)var0).yiyiaddon$getPredictionHandler();
   }
}
