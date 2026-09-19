package com.yiyiaddon.g.a;

import java.util.Objects;
import java.util.function.Predicate;
import net.minecraft.world.level.block.Block;

public final class c {
   private final String CM;
   private final String CN;
   private final Predicate<Block> d;

   public c(String var1, String var2, Predicate<Block> var3) {
      this.CM = var1;
      this.CN = var2;
      this.d = var3;
   }

   public String s() {
      return this.CM;
   }

   public String m() {
      return this.CN;
   }

   public boolean h(Block var1) {
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1j5f1ucomzexg","GqfHuIBmL2HSF5qegpy0O7Z1tixMJ9VrAr0nySd9D9s=",-7219984385258362253,3222767857951789080,-1439018017112805155,-6369736737259243670>()) {
            case 745075782:
               if (this.d.test(var1)) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1386ifc0dh4hd","hze04tVmgICfebVZBeFmftXRXjxfv2pCWP73ltlzQH0=",51577504041183899,4632799577917988879,-8521963801929672349,-8584713220193438528>()) {
                     case 1147362685:
                        switch ((int)com.yiyiaddon.m.b.a<"s1di3h3dgznv5c","h1QxUQ/l9i6iwMNQvhpJOXUAPZE0bWNX3WtlVTCh+L8=",5021798174961781426,8596721713131477568,-6370792800651145521,699615749600557172>()) {
                           case -2083437944:
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

      switch ((int)com.yiyiaddon.m.b.a<"s5piefrgvf8yn","bMuinrppWqIphzD+UI9bzKZfazjsfSc5ePGOE2dNat8=",2543720357088135566,-8920495792234430889,-7804692349649512315,-1346639744340165291>()) {
         case -181656401:
            return false;
         default:
            throw null;
      }
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         switch ((int)com.yiyiaddon.m.b.a<"stvepobzrqm3v","eADO6jLa8NiJ9ZmipOxUvFxkqRmGpop5vNUvDj1hqGY=",-6033713765575156196,5541175758768460661,6350336971604635275,2221414892090054538>()) {
            case 1377156764:
               return true;
            default:
               throw null;
         }
      } else if (var1 instanceof c) {
         switch ((int)com.yiyiaddon.m.b.a<"s326ikrnd2zwck","1vGdL8j/hWVfzShcNbbE+9AxjJZOw4NmYTKSHkl6hPQ=",-3681744595008390741,8843239891368661826,844197467987512980,3006349891220806211>()) {
            case -598385918:
               c var2 = (c)var1;
               switch ((int)com.yiyiaddon.m.b.a<"s22blej73n9m4z","bK8HH+tWk+ojn5yFx3lemDml7r2V7LEBSy2xf7UwsYA=",6152622015196414828,-5592945101187975028,-4003833317882361260,4298495915245987855>()) {
                  case 1384641642:
                     return this.CM.equals(var2.CM);
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.CM);
   }

   @Override
   public String toString() {
      return this.CM;
   }
}
