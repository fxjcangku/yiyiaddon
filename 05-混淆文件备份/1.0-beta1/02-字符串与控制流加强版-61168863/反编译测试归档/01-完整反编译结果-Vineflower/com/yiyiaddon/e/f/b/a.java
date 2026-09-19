package com.yiyiaddon.e.f.b;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;

public final class a {
   private a() {
   }

   public static double a(Entity var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2kkiaju3x7z7p","9xZKFuJ8csfelGclKaWCA8w/5P20+fUrm67iYaWuF9E=",6301875016601893263,473961071359263041,8417634851356600240,6409177670146150656>()) {
            case -711354730:
               if (var0 != null) {
                  return var1.player.getYRot()
                     + Mth.wrapDegrees(
                        (float)Math.toDegrees(Math.atan2(var0.getZ() - var1.player.getZ(), var0.getX() - var1.player.getX())) - 90.0F - var1.player.getYRot()
                     );
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"ssf4df0ixw8wb","wIJmkRndItrDUqdtmBp2c0xVYkWzCE136oz770SA/1U=",4497893187686460193,-3091244436991054901,3680635036276449985,7889687716571971896>()) {
                     case -876147531:
                        return 0.0;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return 0.0;
      }
   }

   public static double b(Entity var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1.player != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s30jbi8atf8eh","ZDqPdUD4XWEsl1CrH2RqsTvm8N+3SUSZLt1nrj9HJjs=",-5442284004711717208,-3994563362037216060,43542293425633618,7129213325774615838>()) {
            case 696017222:
               if (var0 != null) {
                  double var2 = var0.getY() + var0.getBbHeight() / 2.0F;
                  double var4 = var0.getX() - var1.player.getX();
                  double var6 = var2 - (var1.player.getY() + var1.player.getEyeHeight());
                  double var8 = var0.getZ() - var1.player.getZ();
                  double var10 = Math.sqrt(var4 * var4 + var8 * var8);
                  return var1.player.getXRot() + Mth.wrapDegrees((float)(-Math.toDegrees(Math.atan2(var6, var10))) - var1.player.getXRot());
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3057w0sx4d6en","PwLJcIBtqz3hVWuOxzikzdh0AfvoyVcj56ZJk9mCEsg=",7027878450635718379,7204303037088673962,2251121001711953702,-5395707443857708831>()) {
                     case -1305428566:
                        return 0.0;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return 0.0;
      }
   }
}
