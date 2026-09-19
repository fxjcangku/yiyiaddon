package com.yiyiaddon.e.j.c;

import com.yiyiaddon.mixin.client.ClientLevelPredictionAccessor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.prediction.BlockStatePredictionHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket.Action;
import net.minecraft.world.level.block.state.BlockState;

final class b {
   void a(LocalPlayer var1, ClientLevel var2, BlockPos var3, Direction var4, BlockState var5) {
      BlockStatePredictionHandler var6 = this.a(var2);

      try (BlockStatePredictionHandler var7 = var6.startPredicting()) {
         var7.retainKnownServerState(var3, var5, var1);
         com.yiyiaddon.d.c.b.a(
            var1.connection.getConnection(), new ServerboundPlayerActionPacket(Action.START_DESTROY_BLOCK, var3, var4, var7.currentSequence())
         );
      }
   }

   void a(LocalPlayer var1, ClientLevel var2, BlockPos var3, Direction var4) {
      BlockStatePredictionHandler var5 = this.a(var2);

      try (BlockStatePredictionHandler var6 = var5.startPredicting()) {
         com.yiyiaddon.d.c.b.a(
            var1.connection.getConnection(), new ServerboundPlayerActionPacket(Action.STOP_DESTROY_BLOCK, var3, var4, var6.currentSequence())
         );
      }
   }

   void a(LocalPlayer var1, BlockPos var2, Direction var3) {
      com.yiyiaddon.d.c.b.a(var1.connection.getConnection(), new ServerboundPlayerActionPacket(Action.ABORT_DESTROY_BLOCK, var2, var3));
   }

   private BlockStatePredictionHandler a(ClientLevel var1) {
      return ((ClientLevelPredictionAccessor)var1).yiyiaddon$getPredictionHandler();
   }
}
