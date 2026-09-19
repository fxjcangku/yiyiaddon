package com.yiyiaddon.e.r.d;

import com.yiyiaddon.l.b.h;
import com.yiyiaddon.l.b.w;
import com.yiyiaddon.l.f.i;
import com.yiyiaddon.l.h.d;
import net.minecraft.client.Minecraft;

public final class b extends com.yiyiaddon.l.f.c implements i {
   private static final String Bp = (String)com.yiyiaddon.m.b.a<"s2wszhyxkjgivy","t1PGiH/4ffetUWSSfw5vSbBhDGhnHK33aiGojPlIeDkghTA4FlVWcipk",-8532470371372767908,5224066489893854112,5500673489415956955,570656956919859277>();
   private static final String Bq = (String)com.yiyiaddon.m.b.a<"s9i9b24h1pzjw","xZXqKZMmAA1HAR0mM6Uz+IvOypvE1xob2aHIwSFVo8B07ZC/OBsiQea7XpGCx8+roAvHaSr4rWbFREyop8GZ3gXrEqsRG9npQwhA4+d9eNapnkofyjuGBuPguBooQPa/0XObgKs1r+R+XFx2yZeLwSnd//rNFw==",-3277802886827191536,-968491750328479722,-4327346369380389553,9198650004562528690>();
   private static final String[] ah = d.a(d.a(a.q));
   private final com.yiyiaddon.e.r.a c;
   private boolean v;

   public b(com.yiyiaddon.e.r.a var1) {
      this.c = var1;
   }

   @Override
   public com.yiyiaddon.l.f.a a(com.yiyiaddon.h.d var1) {
      if (!this.v) {
         switch ((int)com.yiyiaddon.m.b.a<"s12quvvdkw1i0p","pX2efTTBrqpcxdumChkrUPSubC30uIqawyV8ktcLTzY=",19779198019511556,5209888368226543409,2040936618252998028,620638297053493228>()) {
            case -1700727049:
               this.v = true;
               this.G();
               switch ((int)com.yiyiaddon.m.b.a<"s21ve4jxmc4yba","z6KDSwpQrEyYvZYqrmcUmXUhayKFynpJjyuyDp2+UHc=",3906016523362141830,2074162496593403038,7114637216944868734,4313439699004091728>()) {
                  case 275229841:
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
               (String)com.yiyiaddon.m.b.a<"s2xanzr6rjnktq","e12sXWf0Rf+l6hB4omsy1p3AT5KjT9/5LqIISg==",-6911307028641254151,833849307668553297,-7644870057832603583,-3703217199481198055>(),
               () -> (String)com.yiyiaddon.m.b.a<"s9i9b24h1pzjw","xZXqKZMmAA1HAR0mM6Uz+IvOypvE1xob2aHIwSFVo8B07ZC/OBsiQea7XpGCx8+roAvHaSr4rWbFREyop8GZ3gXrEqsRG9npQwhA4+d9eNapnkofyjuGBuPguBooQPa/0XObgKs1r+R+XFx2yZeLwSnd//rNFw==",-3277802886827191536,-968491750328479722,-4327346369380389553,9198650004562528690>(),
               new com.yiyiaddon.l.j.a(
                  (String)com.yiyiaddon.m.b.a<"s2wszhyxkjgivy","t1PGiH/4ffetUWSSfw5vSbBhDGhnHK33aiGojPlIeDkghTA4FlVWcipk",-8532470371372767908,5224066489893854112,5500673489415956955,570656956919859277>(),
                  this::H
               )
            )
            .a()
      );
      String[] var1 = ah;
      int var2 = var1.length;
      int var3 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s21teiujnfabdk","ImgGM42OklRS6vbonri7LImIdCaxIuD29KqXQi7h7S4=",3332396316416705768,8970065940911380750,-21921101915008291,-8979959753996284603>()) {
         case -621824403:
            while (var3 < var2) {
               switch ((int)com.yiyiaddon.m.b.a<"s2acooj5arwm4k","L8fL7JFPb70WzEw67gEx05iiT0P7F9ik5U5re10i1jM=",-4136263270064352064,5953822205827975816,-3855766909122874561,6610145066102342429>()) {
                  case -33789438:
                     String var4 = var1[var3];
                     this.b(new w(var4));
                     var3++;
                     switch ((int)com.yiyiaddon.m.b.a<"s1yiqg1imscwuw","zoKaE8bpKg40iZgwPvogh1zJmykEodkkE3uZ0agyTs8=",5448258109406712622,6286757254357560559,-5614415307358326799,409255308236721033>()) {
                        case 1870812319:
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
         switch ((int)com.yiyiaddon.m.b.a<"s1esk742m7z8z6","JHY/47Mzgarn9c9jx6WXuBkYcXI3BLVuDfaUNBf032k=",2126132348705101826,-2786388935254797746,-1500810324565472641,2731249800045181182>()) {
            case -80555948:
               return;
            default:
               throw null;
         }
      } else {
         var1.setScreen(new com.yiyiaddon.e.r.d.a.b(var1.screen, this.c));
      }
   }
}
