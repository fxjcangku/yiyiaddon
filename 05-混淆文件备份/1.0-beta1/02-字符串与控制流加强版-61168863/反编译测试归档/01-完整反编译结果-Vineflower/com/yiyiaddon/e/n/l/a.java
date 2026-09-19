package com.yiyiaddon.e.n.l;

import com.yiyiaddon.l.g.a.f;
import com.yiyiaddon.l.g.a.j;
import java.util.Iterator;
import java.util.List;
import java.util.function.ToIntFunction;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;

public final class a {
   private static final float cb = 1.5F;

   private a() {
   }

   public static void a(f var0, List<BlockPos> var1, int var2, int var3, j var4) {
      Iterator var5 = var1.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s2yrt4navhs2kt","wK6+Mj1kE7r7pYQxM+RfMW0koo42iB/RuBSOooeTDwQ=",6093234104034677899,-5207558824668994972,8261908263089817316,-6641735730085469899>()) {
         case -1430931201:
            while (var5.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"srx8p92h32enx","Nz/dzp8NFi3nuBHZVtjzBAZBsKea47rNHx7it/VOH9w=",2588978356441481191,5679067064150676183,1337981128286140737,-5525239468061046274>()) {
                  case 641886811:
                     BlockPos var6 = (BlockPos)var5.next();
                     var0.a(var6.getX(), var6.getY(), var6.getZ(), var3, var2, var4, 1.5F);
                     switch ((int)com.yiyiaddon.m.b.a<"s2nt6lo73f1ay0","SIfd7B3gVnAPns/NLXLE8xiT/zQFOXGS4oFjzamhm58=",-6242047835780041544,8692450034664072027,3058204789756460320,3307940366563483474>()) {
                        case 872392407:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return;
         default:
            throw null;
      }
   }

   public static void a(f var0, List<BlockPos> var1, ToIntFunction<BlockPos> var2, int var3, int var4, j var5) {
      Iterator var6 = var1.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s257p7ux4v3pjb","tgLEnoksgGRSPcK9/+tOHDCCwt+eFzcLwd3o1kf+oJ0=",729460425653202925,7205031061567057313,6764370793540412256,-4406535862321021088>()) {
         case 1492977695:
            while (var6.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"sdp5kbn7lrz16","Uf58iTzWuR3/CU+byK/1llCJ7XyIGlhNkbSwepvoD64=",-7607921476115431960,-1826482641362325525,-4415508141623156182,6390689393706332628>()) {
                  case 1521400109:
                     BlockPos var7 = (BlockPos)var6.next();
                     int var8 = Math.max(1, var2.applyAsInt(var7));
                     AABB var9 = new AABB(
                        var7.getX() - var8, var7.getY(), var7.getZ() - var8, var7.getX() + var8 + 1.0, var7.getY() + 1.0, var7.getZ() + var8 + 1.0
                     );
                     var0.a(var9, var4, var3, var5, 1.5F);
                     switch ((int)com.yiyiaddon.m.b.a<"s209f9i50pr3e7","CILZbXC381CIEiE6Re4Rw0eNoGv11CoR0aBT/d5w1pw=",4355297653801772078,88540700514107122,-5991274525127732238,2467372288035281711>()) {
                        case 2104231593:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return;
         default:
            throw null;
      }
   }

   public static void b(f var0, List<BlockPos> var1, int var2, int var3, j var4) {
      Iterator var5 = var1.iterator();
      switch ((int)com.yiyiaddon.m.b.a<"s1rajaqoyh807","/9eryBKON/2+kIMJ2ER+vOwCS4DJsSoEnesQH7m+0JI=",3825715385337942490,1947885161857757211,3557705658309715483,4751328654265778925>()) {
         case 1197430919:
            while (var5.hasNext()) {
               switch ((int)com.yiyiaddon.m.b.a<"s1wydv99x2fpp1","cdzCpRpKZmwz6q4l1DN5hVgxCcDGQbhn5FtThPbExM8=",3794047557633765663,-4012522736571274934,-8198289392049254590,-6240758045571606437>()) {
                  case -772492560:
                     BlockPos var6 = (BlockPos)var5.next();
                     var0.a(
                        new AABB(var6.getX() + 0.3, var6.getY() + 0.3, var6.getZ() + 0.3, var6.getX() + 0.7, var6.getY() + 0.7, var6.getZ() + 0.7),
                        var3,
                        var2,
                        var4,
                        1.5F
                     );
                     switch ((int)com.yiyiaddon.m.b.a<"s33l2wgixbdf0n","j/GZw030aRd7a56UHUfnobCoc/iAGpS2xJqWkoTWz1g=",9038754022706160430,4844405297734944187,735140249681322956,5998872379350675147>()) {
                        case -1356301482:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return;
         default:
            throw null;
      }
   }
}
