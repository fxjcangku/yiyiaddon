package com.yiyiaddon.e.n.a;

import com.yiyiaddon.g.c.d;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public interface b {
   boolean b(BlockPos var1, int var2);

   void b(Set<Block> var1);

   boolean cX();

   void gv();

   boolean a(BlockPos var1, double var2);

   boolean v(BlockPos var1);

   boolean d(BlockPos var1, Direction var2);

   boolean a(InteractionHand var1, BlockPos var2);

   boolean a(InteractionHand var1, BlockPos var2, Direction var3);

   int a(d var1);

   int b(d var1);

   void e(int var1);

   boolean n(int var1);

   boolean o(int var1);

   ItemStack o();
}
