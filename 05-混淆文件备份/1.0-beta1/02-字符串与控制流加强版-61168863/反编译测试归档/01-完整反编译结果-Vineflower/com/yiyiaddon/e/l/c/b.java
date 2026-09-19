package com.yiyiaddon.e.l.c;

import com.yiyiaddon.l.b.h;
import com.yiyiaddon.l.b.w;
import com.yiyiaddon.l.f.c;
import com.yiyiaddon.l.f.i;
import com.yiyiaddon.l.h.d;
import net.minecraft.client.Minecraft;

public final class b extends c implements i {
   private static final String qy = (String)com.yiyiaddon.m.b.a<"s1yrq1m40hviqe","k4gxY2q4uW6R+mombZcjA9VrlKwtxzvS+Jn88l5744pjoIB2OgRWoDK1yRUKCovuIt1dTHwLapCHLw==",469028019068823328,-1326020412595049897,-9168716260519419341,-3073555102685148080>();
   private static final String[] S = d.a(d.a(com.yiyiaddon.e.l.a.b.a()));
   private final com.yiyiaddon.e.l.a c;
   private boolean v;

   public b(com.yiyiaddon.e.l.a var1) {
      this.c = var1;
   }

   @Override
   public com.yiyiaddon.l.f.a a(com.yiyiaddon.h.d var1) {
      if (!this.v) {
         switch ((int)com.yiyiaddon.m.b.a<"s3o56pwyabwejs","mq3nwXA18HBTu7rWuoMTBYTkfuFFBAn+SNqFIMFrO/4=",-6711761951136286203,5334379615853507280,3304374421956354549,5977797194125227847>()) {
            case 1196059100:
               this.v = true;
               this.G();
               switch ((int)com.yiyiaddon.m.b.a<"s1qvard3czbfz0","3pyrJaCFVWVoY4hmDK56kREXVQvCBCo9X1Rb614KD4c=",-2540027089478177046,1662968935640923029,-7704382084348201802,-6662876750252003024>()) {
                  case -967896246:
                     return this;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         return this;
      }
   }

   @Override
   public String E() {
      return this.c.t();
   }

   @Override
   public String F() {
      return this.c.v();
   }

   private void G() {
      this.b(
         new h(
               (String)com.yiyiaddon.m.b.a<"s2065xy5jza8ab","nPryuUxsqy4WKynN3EAD7INlerIPliaGsLLsNA==",-5786780232250127036,-5160962442991967213,5459373896673873963,8485937597058453795>(),
               () -> (String)com.yiyiaddon.m.b.a<"s1yrq1m40hviqe","k4gxY2q4uW6R+mombZcjA9VrlKwtxzvS+Jn88l5744pjoIB2OgRWoDK1yRUKCovuIt1dTHwLapCHLw==",469028019068823328,-1326020412595049897,-9168716260519419341,-3073555102685148080>(),
               new com.yiyiaddon.l.j.a(
                  (String)com.yiyiaddon.m.b.a<"s3ru1617a53n7t","nC9djU79LQ3CjfPeNUtyQL3CKnxh6M+g9mOvmhKSnmXVm/HrsnfV6Vef",-5273692696770172527,-590948606105655930,3743251838964293416,-5533563149290199129>(),
                  this::H
               )
            )
            .a()
      );
      String[] var1 = S;
      int var2 = var1.length;
      int var3 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s36a6jky81btl8","2Ot1U+OHInV8xni//u23xmlazW3wOpAWS1m5nB4vw70=",-8977287403629571016,2640309100207311649,6615699710458588758,-3733980462343105102>()) {
         case 1398413766:
            while (var3 < var2) {
               switch ((int)com.yiyiaddon.m.b.a<"s1hab45ok2gsw1","kvtR5PNaVocCfp7agLtl4jcMhkuheRF6Bvq99m7cauk=",-9080031986544175922,-6929630159985143169,5667470436812116428,607385808891768490>()) {
                  case -461769653:
                     String var4 = var1[var3];
                     this.b(new w(var4));
                     var3++;
                     switch ((int)com.yiyiaddon.m.b.a<"suzh98nwyb2h6","f8FN5rRgyCJpgdAxIK8ejjGNxn2f8tDR1MftUkkoz1M=",1159204576079469414,2549416748952981750,-3257450702432490744,4139509329589270798>()) {
                        case 1106239144:
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

   private void H() {
      Minecraft var1 = Minecraft.getInstance();
      if (var1 == null) {
         switch ((int)com.yiyiaddon.m.b.a<"svbm1yskumw1d","a3Nm3t+CheijT1cXSKzM7Dbp6HCvlQ3/dnodgkAm0og=",1106193980163697156,2338321361918873398,-7295986461639539514,-1181403132241203126>()) {
            case 249226491:
               return;
            default:
               throw null;
         }
      } else {
         var1.setScreen(new a(var1.screen, this.c));
      }
   }
}
