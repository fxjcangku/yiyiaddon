package com.yiyiaddon.e.i.e;

import com.yiyiaddon.e.i.d.l;
import com.yiyiaddon.e.i.f.r;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.phys.AABB;

public final class e implements r {
   private static final int fQ = 64;
   private final com.yiyiaddon.e.i.f.c b;
   private final BooleanSupplier a;

   public e(com.yiyiaddon.e.i.f.c var1, BooleanSupplier var2) {
      this.b = var1;
      this.a = var2;
   }

   @Override
   public Optional<l> a(int var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3f4xd7ip4x9po","zVKOJmH2wCOZolPlFKa7GaQ8sXGrzlOC6k8VBRfC7kM=",4594360243301106184,9108076506911191674,-3094014067849711202,-4801472018902649542>()) {
            case 2023600534:
               if (var2.level != null) {
                  AABB var3 = var2.player.getBoundingBox().inflate(var1);
                  List var4 = var2.level.getEntitiesOfClass(Villager.class, var3, this::a);
                  return var4.stream().filter(this::b).min(Comparator.comparingDouble(var1x -> var1x.distanceToSqr(var2.player))).map(this::a);
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"scc6iox7w1294","7h6YQEW85ESiku/N283rNQLw5YBn7Ndoja0VOUkS9TQ=",4848147778062199523,8816221344969900900,4508427976147183109,-3103788637037899300>()) {
                     case -1321465791:
                        return Optional.empty();
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return Optional.empty();
      }
   }

   @Override
   public boolean a(l var1) {
      return this.b(var1).filter(this::a).isPresent();
   }

   @Override
   public boolean b(l var1) {
      return this.b(var1).filter(this::b).isPresent();
   }

   @Override
   public boolean c(l var1) {
      return this.b(var1).filter(var1x -> this.a(var1x, VillagerProfession.LIBRARIAN)).isPresent();
   }

   private Optional<Villager> b(l var1) {
      Minecraft var2 = Minecraft.getInstance();
      if (var2.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"sjmriu7213fwb","WFAXnEHQD6xvtRZh6C0jMrLvzhDHnvrAVacbh7udeNU=",-3748134431224009373,-574621757932577225,-2449846588454478333,3685228614625448023>()) {
            case 697935582:
               return Optional.empty();
            default:
               throw null;
         }
      } else {
         Entity var4 = var2.level.getEntity(var1.bk());
         if (var4 instanceof Villager) {
            switch ((int)com.yiyiaddon.m.b.a<"s1uzbq06dy6hk4","KeJ7ZvZcd5irdxLR7qbs5SD3phpWjQxrhwGyDgX2xbE=",-84029416455558565,3400210568158492764,-4958629132774983334,-1571171755296978620>()) {
               case -1199182471:
                  Villager var3 = (Villager)var4;
                  if (var1.c().equals(var3.getUUID())) {
                     switch ((int)com.yiyiaddon.m.b.a<"s15ghdy74v8uwn","r/kZDr0lLukgm8zKD+as3J/VEZaf+NWfvtARWDA9D4A=",8268116967270182421,-5739272918455733074,4075278508960470612,-2428752073799259354>()) {
                        case 533904151:
                           return Optional.of(var3);
                        default:
                           throw null;
                     }
                  }
                  break;
               default:
                  throw null;
            }
         }

         if (var2.player == null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1y9w03l8k7z6s","Cm9TbH4yezwrdu7ZdptNkL4UppNZ0fwpMpC+OLNzve4=",6074048414811679764,6098323701156955369,3711012210689706809,-8256275337565456885>()) {
               case 1931609061:
                  return Optional.empty();
               default:
                  throw null;
            }
         } else {
            AABB var5 = var2.player.getBoundingBox().inflate(64.0);
            return var2.level.getEntitiesOfClass(Villager.class, var5, var1x -> var1.c().equals(var1x.getUUID())).stream().findFirst();
         }
      }
   }

   private boolean a(Villager var1) {
      if (var1.isAlive()) {
         switch ((int)com.yiyiaddon.m.b.a<"s4ie663u0j364","ktT9TJmp+2VPCHi+L/utlOQPe02+IE+VRFmnc18MLrs=",5584965664610929622,7847320304893477870,-7330400785807509158,-6939371006715002271>()) {
            case 1713882483:
               if (!var1.isRemoved()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s2m0ihexe3n2j3","pBJijl8rRCEq0ktGxQ8zOPE5KUMxGoXZokTldKQcB+A=",-7642985684506014612,1650677289967137394,48511327240432673,8442600950490764668>()) {
                     case 419246586:
                        switch ((int)com.yiyiaddon.m.b.a<"snno0mabvjts9","hu5So9bw2zI1cjjy+I1D/gpptYJSQ3IQsnq1pOI4cQ4=",-6551849630661048719,-9160676216670276808,7936018952130560518,5014897655271575214>()) {
                           case -1420070978:
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

      switch ((int)com.yiyiaddon.m.b.a<"s124pssobduvsa","3ti1P4b6eElvFW6hb1X4sWWJ6TMKXdRczeVaWXeSipk=",8786556397811041136,8972923231888460792,2506539811856118485,6156968254795269778>()) {
         case -299558776:
            return false;
         default:
            throw null;
      }
   }

   private boolean b(Villager var1) {
      return this.a(var1, VillagerProfession.NONE);
   }

   private boolean a(Villager var1, ResourceKey<VillagerProfession> var2) {
      return var1.getVillagerData().profession().is(var2.identifier());
   }

   private l a(Villager var1) {
      return new l(var1.getUUID(), var1.getId(), new com.yiyiaddon.e.i.d.a(var1.getBlockX(), var1.getBlockY(), var1.getBlockZ()));
   }
}
