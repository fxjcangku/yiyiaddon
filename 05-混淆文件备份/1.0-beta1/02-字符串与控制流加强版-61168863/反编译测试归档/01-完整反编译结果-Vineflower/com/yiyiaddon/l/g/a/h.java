package com.yiyiaddon.l.g.a;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public record h(Vec3 n, Matrix4f a, float kP, float kQ) {
   public static h a() {
      Minecraft var0 = Minecraft.getInstance();
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s3crq8k0bmvjz6","MOz+IV0DCwnTXLcguVzsv+qqAMxzx+ZIhNmbv4VsBAs=",-2841207961942852041,7107795256326074806,4839690039016006786,1948762855264437305>()) {
            case -204761178:
               if (var0.level != null) {
                  switch ((int)com.yiyiaddon.m.b.a<"sypgtgqwikrjv","KYMkHO/63D2Z8HA+FoH+4YkjBM6MF6DQa2IoZFAV18M=",-7568872294692438269,7415624947678265561,-7920051231117233138,-4883147681770022385>()) {
                     case 1041695953:
                        if (var0.player != null) {
                           Camera var1 = var0.gameRenderer.getMainCamera();
                           if (!var1.isInitialized()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3h96pgt3eble2","t/u/vwf2Nd0bUfkYH2J0rIE/XAv97LPcY0nEDbHsmnA=",-4946027005077818365,1712581781340079208,-2230195578976966619,6361388088491508446>()) {
                                 case 381174950:
                                    return null;
                                 default:
                                    throw null;
                              }
                           }

                           float var2 = var0.getWindow().getGuiScaledWidth();
                           float var3 = var0.getWindow().getGuiScaledHeight();
                           if (!(var2 <= 0.0F)) {
                              switch ((int)com.yiyiaddon.m.b.a<"s2no7ltg2aro6u","OvDoXhRFGWQxDHXsFELMVDvAd9hdFGQT2g+Uv6BRo1o=",3465689608833601993,8410830703494172229,850950164187332347,4842452741973449020>()) {
                                 case 1371393554:
                                    if (!(var3 <= 0.0F)) {
                                       return new h(var1.position(), var1.getViewRotationProjectionMatrix(new Matrix4f()), var2, var3);
                                    }

                                    switch ((int)com.yiyiaddon.m.b.a<"s2axm0dk1xfs5z","93q8dM1nmB+8Eo56nIU/Oy2qHff8FaIZk0aFzCunfqc=",-9130925911709080068,-5697023554364931919,5253628976855223099,4941696766561327282>()) {
                                       case 1688131981:
                                          return null;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           return null;
                        }

                        switch ((int)com.yiyiaddon.m.b.a<"s2xgs0up4bba29","xsjD9rJRz0jvmsKMFyIN3AGqBIBewswOrPkd4aC02Xs=",-7039243384701723632,-9188113220517956232,-3110162160126084882,4542873410895420984>()) {
                           case -1755716724:
                              return null;
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

      return null;
   }

   public double s() {
      return this.n.x;
   }

   public double t() {
      return this.n.y;
   }

   public double u() {
      return this.n.z;
   }

   public Vec3 a() {
      return this.n;
   }

   public float ab() {
      return this.kP;
   }

   public float ac() {
      return this.kQ;
   }
}
