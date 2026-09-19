package com.yiyiaddon.e.c.d;

import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record b(BlockPos d, ResourceKey<Level> a) {
   public static final String bW = "";

   public String ag() {
      return "" + this.d.getX() + this.d.getY() + this.d.getZ() + this.a.identifier();
   }

   public static b a(String var0) {
      if (var0 != null && !var0.isBlank()) {
         String[] var1 = var0.split(
            (String)com.yiyiaddon.m.b.a<"sru6gx09zbm7y","atEXTkiOwpl3b5VPuEhtFvMcNzGz2FPBzBEaieQC",2094437251023760116,-162629214603743364,1911940022962119274,-2601188073356713150>()
         );
         if (var1.length != 4) {
            return null;
         }

         try {
            int var2 = Integer.parseInt(var1[0].trim());
            int var3 = Integer.parseInt(var1[1].trim());
            int var4 = Integer.parseInt(var1[2].trim());
            Identifier var5 = Identifier.tryParse(var1[3].trim());
            return var5 == null ? null : new b(new BlockPos(var2, var3, var4), ResourceKey.create(Registries.DIMENSION, var5));
         } catch (NumberFormatException var6) {
            return null;
         }
      } else {
         return null;
      }
   }

   public static b a(BlockPos var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1hhyzp8p97f0q","GPkfhsk2a2rY6MMIk6seCWLFe7qGDDqVIS043IqXjlQ=",-656279040530071204,-6740386341379548625,-7309242868401543292,-8513572500238531628>()) {
            case -145324565:
               return null;
            default:
               throw null;
         }
      } else {
         return new b(var0, var1.level.dimension());
      }
   }

   public boolean G() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.level == null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1kh1b5wgeqeqh","dPFD90far/NeO9dK/syFjBwvor5StoiLXnlcwBc7WnM=",-1719540675399945390,-2106453372260130788,-5759251797519392020,642298483330024679>()) {
            case -764832242:
               return false;
            default:
               throw null;
         }
      } else {
         return var1.level.dimension().equals(this.a);
      }
   }

   public String f() {
      return this.q(
         (String)com.yiyiaddon.m.b.a<"s2oqzdp03a3co8","raZ9jA562SZ10L4tiRXFuLellkhVMPTLZ+EE84ojIG4=",9218218915517314938,8039708235770377099,-858763880481916485,-5845398708000247056>()
      );
   }

   public String q(String var1) {
      String var2 = com.yiyiaddon.i.g.b.bV(this.a.identifier().toString());
      return "" + this.d.getX() + this.d.getY() + this.d.getZ() + var1 + var2;
   }

   public BlockPos a() {
      return this.d;
   }
}
