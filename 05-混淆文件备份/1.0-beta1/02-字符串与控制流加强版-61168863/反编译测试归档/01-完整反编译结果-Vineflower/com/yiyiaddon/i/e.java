package com.yiyiaddon.i;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

public final class e {
   private e() {
   }

   public static com.yiyiaddon.g.c a() {
      Minecraft var0 = Minecraft.getInstance();
      LocalPlayer var1 = var0.player;
      if (var1 == null) {
         return null;
      }

      try {
         return new com.yiyiaddon.g.c(
            var1.getX(),
            var1.getY(),
            var1.getZ(),
            a(var1),
            var1.getHealth(),
            var1.getFoodData().getFoodLevel(),
            var0.gameMode == null
               ? (String)com.yiyiaddon.m.b.a<"s1gfufix4qsh0v","TfJnu6W4VfrG6/wG/wYn35c7wTe8d7QtZ6EwwlMxKJWmUDU07n0h5grt",7726413724268556127,8173539338187317962,538067743681714223,8302283709815606008>()
               : var0.gameMode.getPlayerMode().getName(),
            b(var1)
         );
      } catch (Exception var3) {
         return null;
      }
   }

   public static String a(LocalPlayer var0) {
      try {
         String var1 = var0.level().dimension().identifier().getPath();
         if (var1.contains(
            (String)com.yiyiaddon.m.b.a<"s2wtdojxc7as1z","Sqilu6VVT46N0v1lRX0BJGuSJGhovFb12fggRbV/wIdGg/9sOW8o3pKYjq1Uxg==",-3882849362744984089,-1597335586014643069,1785542664458153208,-3258134841565899268>()
         )) {
            return (String)com.yiyiaddon.m.b.a<"s2wtdojxc7as1z","Sqilu6VVT46N0v1lRX0BJGuSJGhovFb12fggRbV/wIdGg/9sOW8o3pKYjq1Uxg==",-3882849362744984089,-1597335586014643069,1785542664458153208,-3258134841565899268>();
         } else if (var1.contains(
            (String)com.yiyiaddon.m.b.a<"s231csw8oy8b1p","G2Y1ImtseNJIiY8Cs6DJdsQBEPEVF9ydwPpdnOArruAhE4zMN1UhlD0z3UooOIQD",7092725666172745779,-4090341552741191287,4911426250512891320,1235239511866824856>()
         )) {
            return (String)com.yiyiaddon.m.b.a<"s231csw8oy8b1p","G2Y1ImtseNJIiY8Cs6DJdsQBEPEVF9ydwPpdnOArruAhE4zMN1UhlD0z3UooOIQD",7092725666172745779,-4090341552741191287,4911426250512891320,1235239511866824856>();
         } else {
            return var1.contains(
                  (String)com.yiyiaddon.m.b.a<"s2zmbi0pmmw4rd","qpSXFkJaPEa8RLnHrjoi1CvwbjyLb9msOTVF0/Dn0rkuZqqE9qLSQEKW",7883102099897875005,-7994863448086559311,3568965105279606718,-7743330109556573597>()
               )
               ? (String)com.yiyiaddon.m.b.a<"s2zmbi0pmmw4rd","qpSXFkJaPEa8RLnHrjoi1CvwbjyLb9msOTVF0/Dn0rkuZqqE9qLSQEKW",7883102099897875005,-7994863448086559311,3568965105279606718,-7743330109556573597>()
               : var1;
         }
      } catch (Exception var2) {
         return (String)com.yiyiaddon.m.b.a<"s1gfufix4qsh0v","TfJnu6W4VfrG6/wG/wYn35c7wTe8d7QtZ6EwwlMxKJWmUDU07n0h5grt",7726413724268556127,8173539338187317962,538067743681714223,8302283709815606008>();
      }
   }

   public static String b(LocalPlayer var0) {
      Minecraft var1 = Minecraft.getInstance();

      try {
         double var2 = var0.getX() - var0.xOld;
         double var4 = var0.getY() - var0.yOld;
         double var6 = var0.getZ() - var0.zOld;
         double var8 = Math.sqrt(var2 * var2 + var6 * var6);
         if (var0.hurtTime > 0) {
            return (String)com.yiyiaddon.m.b.a<"s10f2jt4kzjyep","CBjg9afWZ/xTkMcXB8iQ5tMDaqSSk6hjzkYTmBvwlkNXlQ==",-8819612840472907532,8701469710938448378,-2854331914861482049,-5512448716182975006>();
         } else if (var0.getAbilities().flying) {
            return (String)com.yiyiaddon.m.b.a<"ssv886472b926","H5weOij9GaDjYVvVkY8KhZaoAE+mFJiRK5rQ4yw0F80PIQ==",8319614777553682760,7269831429492124196,6134580655894136,8266846877495514893>();
         } else if (var0.isSprinting() && var8 > 0.1) {
            return (String)com.yiyiaddon.m.b.a<"s14qj3r8p1pydf","ZZFaloa9HgusaWPQtWzJLfh0F4CqhU3You9MElamqaGIMg==",-4465564722062762675,2195071902388022703,-6989518077517842860,-3715020186109880066>();
         } else if (var0.isShiftKeyDown()) {
            return (String)com.yiyiaddon.m.b.a<"s314wugt7wt2qa","mG78RsxF2gGiaTBFO/itvaR9wUDb5rdXYD6If3Qv3W29Dg==",-8800337890596414221,3549006276969280178,-635563011253230953,-5921583284738776985>();
         } else if (var8 > 0.05) {
            return (String)com.yiyiaddon.m.b.a<"s25ppjrv2k897r","RgMJymS4O/6w9qBJjO4AFMJZ0F66wwbJ1V8m7dXCOcNgCg==",-2246976828568802638,5700772586681645832,7899582375220514224,-5171152673450296953>();
         } else if (Math.abs(var4) > 0.1) {
            return var4 > 0.0
               ? (String)com.yiyiaddon.m.b.a<"s15ufwenz9nf1h","nNZIhqH9yXGxjmnI+xAetH7E/WfsCMqaiOMVAj6r8OO3aQ==",1715020238028024387,-1332862116575035475,-5476394271453034895,-2793514627787416391>()
               : (String)com.yiyiaddon.m.b.a<"s1ba72aub0efra","a0+VrOkFQYRFP8eWDSv2OGGNppLRY66wq9F0G2zurl/0Xw==",3109952990107914870,-6850512047703049362,-3947885785308545287,-1019731361541938548>();
         } else if (var0.isInWater()) {
            return (String)com.yiyiaddon.m.b.a<"s2854fff69iu9c","CrDrleXoRf5rc8rHlfStuei/aKfEYrzLWhW7+w1/0uz/hg==",3764762532342091812,7561892685791601127,6970056050841011655,3025328084874531864>();
         } else {
            return var1.gameMode != null && var1.gameMode.isDestroying()
               ? (String)com.yiyiaddon.m.b.a<"sndodq0apgadu","UyM7nC8Lw7ly0PnzwqUW5ZpgxiLo7wvlHzVw/Wled+EHMA==",-1955088569146686118,-3882598766640978359,-6343379135428537312,7613386755287202702>()
               : (String)com.yiyiaddon.m.b.a<"s3g7470g9kzf7c","rFFhQ+mkOagMIOc2iF0g+tKmHwYpdrn97kZDYUWaLu0=",-4390269692194032393,6914383822682179578,5330779740571747289,2127241620032880023>();
         }
      } catch (Exception var10) {
         return (String)com.yiyiaddon.m.b.a<"s1gfufix4qsh0v","TfJnu6W4VfrG6/wG/wYn35c7wTe8d7QtZ6EwwlMxKJWmUDU07n0h5grt",7726413724268556127,8173539338187317962,538067743681714223,8302283709815606008>();
      }
   }
}
