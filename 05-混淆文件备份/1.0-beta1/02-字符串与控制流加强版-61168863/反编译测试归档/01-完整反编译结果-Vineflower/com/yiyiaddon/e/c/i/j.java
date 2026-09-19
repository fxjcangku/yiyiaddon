package com.yiyiaddon.e.c.i;

import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;

public final class j extends d {
   private final int bv;
   private final Predicate<ItemStack> a;

   public j(BlockPos var1, com.yiyiaddon.k.a.a var2, double var3, int var5, Predicate<ItemStack> var6) {
      super(var1, var2, var3);
      this.bv = var5;
      this.a = var6;
   }

   @Override
   protected l c() {
      boolean var1 = false;
      com.yiyiaddon.g.b.a var2 = com.yiyiaddon.g.b.a.NONE;
      int var3 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s3r4jo812p1b43","sclvXJeVhjEf7aNRz7iMsg16dtYr/JL2fUeeQT28Ccw=",-3200965606975278989,-5186970305435222425,2547438247190332131,7183995167386801299>()) {
         case 142040120:
            label57:
            while (var3 < this.bv) {
               switch ((int)com.yiyiaddon.m.b.a<"s2ef4nk0vebnbj","abN9oo5XKqWJg8oUWdqFb2pZ3Sa8Rf5YVrGE1zklqC8=",-508108350445522175,-758475869936145800,-2827034040260130544,-4903478993567320889>()) {
                  case -153909100:
                     var2 = this.e.a(this.a);
                     if (var2 != com.yiyiaddon.g.b.a.MOVED) {
                        break label57;
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s136rekp81nx40","MekAP7mXNBLU+f7PUEVMARJPhpFDkDV7BwMmi1J4qCI=",2897893815460812298,2067933027147779546,-6071648301877850692,-8703224168366991513>()) {
                        case 505595642:
                           var1 = true;
                           var3++;
                           switch ((int)com.yiyiaddon.m.b.a<"s3iulaypwph503","nh+zPfyHkZm1dXG8t29RTUKDRWGgChklfTIF/LCmpvw=",-3818138746362639861,7550336497148447305,-7825617463565139227,3682378807889904157>()) {
                              case -2063251598:
                                 continue;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            if (var1) {
               switch ((int)com.yiyiaddon.m.b.a<"s12n68e2lrod0k","vThj2PpmJTo80jIsS7cLigMvtjFtN0gn0B8kJRR2XL0=",1272036715670636288,557290342159045042,-8653692184796134620,5707731515274052819>()) {
                  case -767244679:
                     return com.yiyiaddon.e.c.i.l.IN_PROGRESS;
                  default:
                     throw null;
               }
            } else if (var2 == com.yiyiaddon.g.b.a.NOT_READY) {
               switch ((int)com.yiyiaddon.m.b.a<"s24v7q9yhgsqzd","Ax9uOFiNFSTPaCOhbbnLL/zXvRViw4qdxyeu1y8L0uI=",-5127152102175848698,4847735345085136646,-5933222476611245481,-3299216097576817481>()) {
                  case -756418634:
                     return com.yiyiaddon.e.c.i.l.IN_PROGRESS;
                  default:
                     throw null;
               }
            } else {
               this.e.close();
               if (var2 == com.yiyiaddon.g.b.a.CHEST_FULL) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1i960rki4fr73","UIPuFdAblJ5jlZSLz0EG2X9mtHz+KoUklWjkDSYPSNY=",-7629476931019790212,-2291708269249726769,1024537959060751469,4901677393279586242>()) {
                     case -1296916067:
                        l var10000 = com.yiyiaddon.e.c.i.l.POISON_CONTAINER_FULL;
                        switch ((int)com.yiyiaddon.m.b.a<"s1481f1wdbe95l","2FwEN1PGTB8P9j0iqmLFTAKeabbpDnzibKxezafMkXw=",-1289865431176781097,-7834338775165636877,1439618763033142526,522732842199314992>()) {
                           case -74018474:
                              return var10000;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  l var4 = com.yiyiaddon.e.c.i.l.SUCCESS;
                  switch ((int)com.yiyiaddon.m.b.a<"s22u4nc5qs3w89","5Z27Z2tu5Ex6u6XDXs/+RBEVN/vACV34+pPlUNFvSqo=",-5894494923758696158,1373404323944324647,-2232857631345875457,6563594952621850026>()) {
                     case -1330140158:
                        return var4;
                     default:
                        throw null;
                  }
               }
            }
         default:
            throw null;
      }
   }
}
