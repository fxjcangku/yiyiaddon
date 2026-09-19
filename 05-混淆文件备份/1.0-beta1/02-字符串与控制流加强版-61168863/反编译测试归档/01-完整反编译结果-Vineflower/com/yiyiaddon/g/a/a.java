package com.yiyiaddon.g.a;

import net.minecraft.core.BlockPos;

public final class a {
   private final BlockPos V;
   private final String CF;
   private final String CG;
   private final String CH;
   private final long aD;

   public a(BlockPos var1, String var2) {
      this(var1, var2, null, null);
   }

   public a(BlockPos var1, String var2, String var3, String var4) {
      this.V = var1.immutable();
      this.CF = var2;
      this.CG = var3;
      this.CH = var4;
      this.aD = System.currentTimeMillis();
   }

   public BlockPos a() {
      return this.V;
   }

   public String bU() {
      return this.CF;
   }

   public String fG() {
      return this.CG;
   }

   public String fH() {
      return this.CH;
   }

   public long v() {
      return this.aD;
   }

   public boolean G() {
      if (this.CF != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s261k9ugwzu2fx","f8Dqel47bDM+3TpVAqNBJK7E+ixYXkoxk8wMKvDsx8s=",4819953067479990027,-1647606219530887235,6966352192624420961,-1840163940718053350>()) {
            case 510450959:
               if (this.CF.equals(com.yiyiaddon.i.g.c.bU())) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1ccbvxq51n0nr","lG4d7Wp3CEaBFARM7jrOBdUX0KOyGejJaOjdAWVrbEs=",655008963073653634,8945572447880853541,3519677761572694363,7937449188209314662>()) {
                     case -506336499:
                        switch ((int)com.yiyiaddon.m.b.a<"s3v3o4kqxc7x4y","k118lKOWrPrbTs/h4Qe/yEBz2aPdjG21Akkwp6QIU9w=",-538372890174257555,6834652538058720016,3123844005225889578,4782547585479548151>()) {
                           case -2109742057:
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

      switch ((int)com.yiyiaddon.m.b.a<"s28wh7f4smtrnc","QZSV63GdsCwUiKag7edndaqrtH+izwR2wAe/p+MWw6U=",7946175040634041461,7097302925386517860,5495594539498705875,8235674898082089439>()) {
         case -808378106:
            return false;
         default:
            throw null;
      }
   }

   public double f(BlockPos var1) {
      return this.V.distSqr(var1);
   }
}
