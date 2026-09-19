package com.yiyiaddon.e.c.i;

import java.util.function.Predicate;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;

public final class n extends d {
   private final int bA;
   private final Predicate<ItemStack> b;

   public n(BlockPos var1, com.yiyiaddon.k.a.a var2, double var3, int var5, Predicate<ItemStack> var6) {
      super(var1, var2, var3);
      this.bA = var5;
      this.b = var6;
   }

   @Override
   protected l c() {
      boolean var1 = false;
      com.yiyiaddon.g.b.a var2 = com.yiyiaddon.g.b.a.NONE;
      int var3 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s1ze0bfbhxxurd","Ok9Tlu9GuF18dOdRGevt+7yNhzSNKh+EiLMiI7+Gm+o=",-8675675120450206241,-860005653819294632,4843793271741915565,2497802713649610964>()) {
         case 491905590:
            label57:
            while (var3 < this.bA) {
               switch ((int)com.yiyiaddon.m.b.a<"s2tjvcs6t3ai60","zmi2bDQnKj3jSKe/J6gF1oZLJsyK1lmKnH6G3Pd0/JA=",610068595609009739,2490987137729865854,-3902722740409028467,3539301088907947091>()) {
                  case 186616853:
                     var2 = this.e.a(this.b);
                     if (var2 != com.yiyiaddon.g.b.a.MOVED) {
                        break label57;
                     }

                     switch ((int)com.yiyiaddon.m.b.a<"s3ejijt8ifm5lq","etRK/5OCpd3xeCNiAo5VdIn786MTP7vIazEbKPbMBOo=",-6691630277757015474,6516648174337104098,8258309326386453747,-5549794026335966738>()) {
                        case 1052980768:
                           var1 = true;
                           var3++;
                           switch ((int)com.yiyiaddon.m.b.a<"ssox3eixhz9z6","jP4pC1i8nju2CDXhlIPX/DxTVOY+SW8Mp+fZKxS8Vtc=",2669116092816092000,-5432812288617914131,-4747347969134455152,-4203879746499248807>()) {
                              case 1440235975:
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
               switch ((int)com.yiyiaddon.m.b.a<"s1c6umqa7jjhwp","ohCucnsXDA5Te0uRmk20GPJqd3OcTIRysAVpvgWqmqg=",7292771790942538664,-6207206294975968256,-245533103712340034,-1772147722636004798>()) {
                  case -1567596509:
                     return com.yiyiaddon.e.c.i.l.IN_PROGRESS;
                  default:
                     throw null;
               }
            } else if (var2 == com.yiyiaddon.g.b.a.NOT_READY) {
               switch ((int)com.yiyiaddon.m.b.a<"s1e2ansy4tuf31","6fTGF+f5EDoc+SKAu3eoKm9UCTri026dReFvhJKokDw=",2916838811725125321,-740199259182851889,5430289569617076505,3961135195614326791>()) {
                  case 1574751042:
                     return com.yiyiaddon.e.c.i.l.IN_PROGRESS;
                  default:
                     throw null;
               }
            } else {
               this.e.close();
               if (var2 == com.yiyiaddon.g.b.a.CHEST_FULL) {
                  switch ((int)com.yiyiaddon.m.b.a<"shn6c4xjvtzj5","1yjaTCX6WohJYaLJlbBapwN5vbhUC12NyrW9ortJBQg=",-6013780692385658940,-1529219335551625659,3364270026230094995,5956558700235255213>()) {
                     case 1014419943:
                        l var10000 = com.yiyiaddon.e.c.i.l.CONTAINER_FULL;
                        switch ((int)com.yiyiaddon.m.b.a<"s495s05xxgmoa","gJVRn1I2Rk1X5uv1/wbtCxRmdinEzMaMpM8UgTWMiaI=",2550040164952674478,-5409846103435662448,-6856698959128643516,-8240283042608818032>()) {
                           case 1325777184:
                              return var10000;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               } else {
                  l var4 = com.yiyiaddon.e.c.i.l.SUCCESS;
                  switch ((int)com.yiyiaddon.m.b.a<"s3gfifkty0ypwj","CMw0Vv9aMHK8elLSSqN8MKSQczs5jEsrnxJ2ZlruOC4=",6257847117962705316,1506666634014630959,-894343583913824412,-9046241837693659542>()) {
                     case -122319914:
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
