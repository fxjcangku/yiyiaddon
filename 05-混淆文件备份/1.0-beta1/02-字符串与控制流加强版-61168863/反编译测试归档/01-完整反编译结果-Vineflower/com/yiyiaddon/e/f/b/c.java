package com.yiyiaddon.e.f.b;

import java.util.Comparator;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public enum c implements Comparator<Entity> {
   LOWEST_DISTANCE(
      (String)com.yiyiaddon.m.b.a<"s339gn8a9ouwsg","/TKRuyceH2ZWL8bzHoa5NinmuD4BgSMlqB9YCnxfQOrkFftb",-5568472358149530364,3797556520399340718,8847754714470561503,7097958074132810214>(),
      Comparator.comparingDouble(c::c)
   ),
   HIGHEST_DISTANCE(
      (String)com.yiyiaddon.m.b.a<"s3mypjr0mm8zm0","XmMfNZF5Ovd/8h8vT28Ck2rPE3uxW0wZEKlgo2kSzsl1amXJ",-2762188249141815529,-2003299222802572407,542978145666652086,-7663073130526894649>(),
      (var0, var1) -> Double.compare(c(var1), c(var0))
   ),
   LOWEST_HEALTH(
      (String)com.yiyiaddon.m.b.a<"smbkhgi30ndyz","35oK7XoHeJncYlG8QoKwKoTHZT9CW1e97K5DQsJ4PCGQNOT/",256702421897061799,3296126909995963359,4351807291397181815,-2761577562311765391>(),
      c::b
   ),
   HIGHEST_HEALTH(
      (String)com.yiyiaddon.m.b.a<"s27uxhw5pfgjm4","EFtMLrtka6G1EkEwPeJKCOGHOpfRd3/4DFGlkkGFkdMuFZMQ",5505407776374112978,-8986995762635155716,-232152400627566328,3875476177612131535>(),
      (var0, var1) -> b(var1, var0)
   ),
   CLOSEST_ANGLE(
      (String)com.yiyiaddon.m.b.a<"s10g1yxifjd8cy","d4lf5q3jQIoMS9Qg202Gb7UqR4TrGAqBJVcqEpIeqhc4GWfc",-8008764244357641840,-8369425228064625466,-3405840296573270926,-4804219838198688657>(),
      c::c
   );

   private final String iO;
   private final Comparator<Entity> a;

   c(String var3, Comparator<Entity> var4) {
      this.iO = var3;
      this.a = var4;
   }

   public String m() {
      return this.iO;
   }

   public int a(Entity var1, Entity var2) {
      return this.a.compare(var1, var2);
   }

   @Override
   public String toString() {
      return this.iO;
   }

   private static double c(Entity var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"srnt3oay2di37","ujZtVQIiOaCBKcvl1ssUx/oX3EDAkHf37Wi3mnCU9kk=",355788498654913852,-298726676461913639,12725781314758491,7877363001885043895>()) {
            case -708313419:
               return 0.0;
            default:
               throw null;
         }
      } else {
         double var2 = var1.player.getX() - var0.getX();
         double var4 = var1.player.getY() - var0.getY();
         double var6 = var1.player.getZ() - var0.getZ();
         return var2 * var2 + var4 * var4 + var6 * var6;
      }
   }

   private static int b(Entity var0, Entity var1) {
      boolean var2 = var0 instanceof LivingEntity;
      boolean var3 = var1 instanceof LivingEntity;
      if (!var2) {
         switch ((int)com.yiyiaddon.m.b.a<"sp2qcbldlec8x","qSTHVddFNLF/QthLwEM0/AQGCzqo84u/YAAOUuJXPVc=",2535097903848524239,3155290023022917934,3328847233101964396,-5553527316868924161>()) {
            case 1930981726:
               if (!var3) {
                  switch ((int)com.yiyiaddon.m.b.a<"s181r5s4g0bo5s","yPQPQv7UxR73/cKeznBl5xkEU9FJtIGM/jhlKNBZaig=",2629582222729068547,-1892784224284128933,-7949105379060957039,7620778110019709382>()) {
                     case -890931540:
                        return 0;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (var2) {
         switch ((int)com.yiyiaddon.m.b.a<"s1hosfxdedfaj6","Bp8WEJZCHLaeU3XtWdiLWzhl6phTIDslmeaBcjkNeRc=",2028878828408604916,1448891402466385621,1198562561673377744,8888182424745329883>()) {
            case 1789948499:
               if (!var3) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1yaoqti5r36f7","dakL9IwTQpdNWlcTE3jowPqCcXx9WKuDpbizWN0YN/U=",5701653341337231516,1166657833303573119,631514923654106119,-960238201626833312>()) {
                     case -2057088059:
                        return 1;
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      if (!var2) {
         switch ((int)com.yiyiaddon.m.b.a<"s29pth2jmhty9e","LIG9BkiuhmZsOO5ckCXHEB94pFP4TivWCpRMAnN9eU4=",4102143516086848220,-3535216658131117634,2034130541342494221,-5555402795983065197>()) {
            case 999900037:
               return -1;
            default:
               throw null;
         }
      } else {
         return Float.compare(((LivingEntity)var0).getHealth(), ((LivingEntity)var1).getHealth());
      }
   }

   private static int c(Entity var0, Entity var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.player == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2c7zn9enqxz4i","7waPnOIkuDR0ADPOuXXa+KXF/8e6bs8uRghnmCuioBw=",-2521496545792963802,4736197756457735630,-577175007276942350,-3501697942062625324>()) {
            case -935443308:
               return 0;
            default:
               throw null;
         }
      } else {
         boolean var3 = var0 instanceof LivingEntity;
         boolean var4 = var1 instanceof LivingEntity;
         if (!var3) {
            switch ((int)com.yiyiaddon.m.b.a<"s3sfjsqiguor35","SSHgxcbiP1xsfdvnqz51gF9AsyO/M5Ot19mA0KTuOKQ=",6959882394557344207,7075047276972157634,-3226593723024211919,3903084411451301281>()) {
               case -1395155469:
                  if (!var4) {
                     switch ((int)com.yiyiaddon.m.b.a<"s22nvo83s23v9k","/9wK67V0cUruWkmoFAojzNpnhQ5HNEuYURSoukuxeQs=",-5161047252253405591,-7394488335296611231,-3406224468115671766,-4953563363183041123>()) {
                        case 908683696:
                           return 0;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (var3) {
            switch ((int)com.yiyiaddon.m.b.a<"s2ivltwy2kuweg","LPMPAOPfqMYI1GG2nebazWm5nFhDMYvNuSbIQgwrTpc=",3885469694047636903,-8437533839264545967,489189582033508029,-892884756074822126>()) {
               case -1035756179:
                  if (!var4) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2rhe6sov9mluj","yBI6DsDRCAdulUayz80b4NUidQlxneYtPRPe6esgQSM=",-6267852631161886350,-2890928694087711369,-1268751678901366405,5597765268749211674>()) {
                        case 193031165:
                           return 1;
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (!var3) {
            switch ((int)com.yiyiaddon.m.b.a<"s1m0z1wxyiub6v","dYefA9Q9BjLOY4JlBBbWHxmDWQHidO3WLkQ+aFRs3cI=",3424027360330687481,2053590417069520274,-4770985882047043357,4358192989891237897>()) {
               case 1554825713:
                  return -1;
               default:
                  throw null;
            }
         } else {
            double var5 = Math.abs(com.yiyiaddon.e.f.b.a.a(var0) - var2.player.getYRot());
            double var7 = Math.abs(com.yiyiaddon.e.f.b.a.a(var1) - var2.player.getYRot());
            double var9 = Math.abs(com.yiyiaddon.e.f.b.a.b(var0) - var2.player.getXRot());
            double var11 = Math.abs(com.yiyiaddon.e.f.b.a.b(var1) - var2.player.getXRot());
            return Double.compare(var5 * var5 + var9 * var9, var7 * var7 + var11 * var11);
         }
      }
   }
}
