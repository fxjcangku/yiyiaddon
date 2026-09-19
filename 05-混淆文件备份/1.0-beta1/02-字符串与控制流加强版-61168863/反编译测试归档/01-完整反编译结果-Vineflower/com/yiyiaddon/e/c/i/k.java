package com.yiyiaddon.e.c.i;

import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;

public final class k extends d {
   private final com.yiyiaddon.e.c.g.a d;
   private final com.yiyiaddon.e.c.d.a b;
   private int bw = 0;

   public k(BlockPos var1, com.yiyiaddon.k.a.a var2, double var3, com.yiyiaddon.e.c.g.a var5, com.yiyiaddon.e.c.d.a var6) {
      super(var1, var2, var3);
      this.d = var5;
      this.b = var6;
   }

   public com.yiyiaddon.e.c.d.a c() {
      return this.b;
   }

   @Override
   protected l c() {
      Item var1 = this.b.a();
      if (this.bw > 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1ip0g2bvou8zm","HgDKa56DTucWk7nCLhvv8PuqrWcc7IlkuCkv8wC+taw=",3959120725631148440,-7422539657922036895,-2457418141687714136,3376692832958055667>()) {
            case 295853046:
               this.bw--;
               return com.yiyiaddon.e.c.i.l.IN_PROGRESS;
            default:
               throw null;
         }
      } else if (this.d.a(var1) >= this.d.c(this.b)) {
         switch ((int)com.yiyiaddon.m.b.a<"s318ftog3q6s8x","P2OtubOL6zp5+VGLVeR9+54odIy9yrpKtePJ/mCPrSI=",-3837883890615534106,-340673970429354642,-909945272966225720,3897046380088611230>()) {
            case 697007414:
               this.e.close();
               return com.yiyiaddon.e.c.i.l.SUCCESS;
            default:
               throw null;
         }
      } else if (this.e.f(var1)) {
         switch ((int)com.yiyiaddon.m.b.a<"s2mkp3j5nmfkej","kH3cISZCw7uUhZdz69pkwdCTf5XmJqofaOzHxVa8D2k=",-2221221279018965862,-4823299649896192078,-9038644402002560972,9107922760356240858>()) {
            case 1746969612:
               this.bw = 2;
               return com.yiyiaddon.e.c.i.l.IN_PROGRESS;
            default:
               throw null;
         }
      } else if (this.b(var1) == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"sfoq8w7df3r8w","RmNz/07X+vZQypm3MUp7c6/OJJT4rPLOH09Cm+LaSFg=",-6444817275177621573,-1883089487198695088,6813829814951603602,1281267836207349783>()) {
            case -2137540759:
               this.e.close();
               return com.yiyiaddon.e.c.i.l.CONTAINER_EMPTY;
            default:
               throw null;
         }
      } else {
         return com.yiyiaddon.e.c.i.l.IN_PROGRESS;
      }
   }

   private int b(Item var1) {
      AbstractContainerMenu var2 = com.yiyiaddon.i.a.a.b();
      if (var2 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1gbne2m6do346","T+ebFwvprPhPJtGsb3kV8SErjqrmzj8/LcWuYW7VnFo=",2132941337323217721,887280562181148922,-1400761412863091111,1424512892512361990>()) {
            case -115971148:
               return 0;
            default:
               throw null;
         }
      } else {
         return com.yiyiaddon.i.a.a.a(var2, var1);
      }
   }
}
