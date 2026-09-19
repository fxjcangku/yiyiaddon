package com.yiyiaddon.e.a.d.a;

import com.yiyiaddon.d.b.e;
import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.c.f;
import com.yiyiaddon.l.j.n;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class d {
   private static final com.yiyiaddon.e.a.a.a c = new com.yiyiaddon.e.a.a.a();
   private final com.yiyiaddon.e.a.d.a f;
   private final com.yiyiaddon.e.a.a g;

   public d(com.yiyiaddon.e.a.d.a var1, com.yiyiaddon.e.a.a var2) {
      this.f = var1;
      this.g = var2;
   }

   public void d(i var1) {
      com.yiyiaddon.e.a.a.a var2 = this.g.a();
      var1.a(
         new f.b(
            this.f,
            () -> (String)com.yiyiaddon.m.b.a<"s8srawzpxo0g7","TxVQzLLEAsefahZ81ax0w8GXWW+zWMiBV2ZwbvzYXqrolaqdsgwA6g==",-8614731727494320650,1815579118440355812,230746323626355813,4720833518597654019>(),
            (String)com.yiyiaddon.m.b.a<"swm4fqyszijsf","MXCJPbokNKaHwhMhfnlZklZYTj9OtL5gBDtSohN0rRmrk6MN0rNFb/iYBTnY32TprYI3VN4b5+XBDPRo",6019842287071779142,-6588097838275324631,-4646031598141083141,-897136953958373586>(),
            null,
            List.of(
               new f.c(this.a(() -> var2.s, var1x -> var2.s = var1x)),
               this.a(
                  () -> var2.s = c.s,
                  (String)com.yiyiaddon.m.b.a<"s8srawzpxo0g7","TxVQzLLEAsefahZ81ax0w8GXWW+zWMiBV2ZwbvzYXqrolaqdsgwA6g==",-8614731727494320650,1815579118440355812,230746323626355813,4720833518597654019>()
               )
            )
         )
      );
      var1.a(
         new f.b(
            this.f,
            () -> (String)com.yiyiaddon.m.b.a<"s22zsgnmzfcwge","JgeOUeU1BpGbRJOCSs3gM2O675QKq43QWbaVTSLtD1A=",4993816567932616660,-3159272349615367849,-6568981605264657001,2531593901710642448>(),
            (String)com.yiyiaddon.m.b.a<"sfxvw0myx9t5s","7VjbBZ4+Jq4UAJJMo+X1Q/fVpuudASn02h46HGeLirS11tQPzKVKwc+dkTOy/J9N0IYvW1k/qHowpHvwiNgy1Q==",-7354322728997886641,2505095883666053536,-8641691084597916874,4668588290634752397>(),
            null,
            List.of(
               new f.c(this.a(() -> var2.t, var1x -> var2.t = var1x)),
               this.a(
                  () -> var2.t = c.t,
                  (String)com.yiyiaddon.m.b.a<"s22zsgnmzfcwge","JgeOUeU1BpGbRJOCSs3gM2O675QKq43QWbaVTSLtD1A=",4993816567932616660,-3159272349615367849,-6568981605264657001,2531593901710642448>()
               )
            )
         )
      );
      var1.a(
         new f.b(
            this.f,
            () -> (String)com.yiyiaddon.m.b.a<"s28ykvhwveompj","SPPPOOSz2+eYurP7IM1RiSacgqVBjYfD5j56VanjrnFX3A==",8859754986769168751,-3614889526170221317,8459979688671838429,-1649148856691475990>(),
            (String)com.yiyiaddon.m.b.a<"s2jzqg22bjdu3q","zQS8OS/jh4+4Lxz2dx2Fd4ptqwIUjhvdL+ihRmpCdpVL0pbeMXkhLVIq8dxh5Q==",4180343930230252058,-6399104286232063470,-5361914112261003098,-2430448934818739290>(),
            null,
            List.of(
               new f.c(this.a(() -> var2.u, var1x -> var2.u = var1x)),
               this.a(
                  () -> var2.u = c.u,
                  (String)com.yiyiaddon.m.b.a<"s28ykvhwveompj","SPPPOOSz2+eYurP7IM1RiSacgqVBjYfD5j56VanjrnFX3A==",8859754986769168751,-3614889526170221317,8459979688671838429,-1649148856691475990>()
               )
            )
         )
      );
   }

   private n a(Supplier<Boolean> var1, Consumer<Boolean> var2) {
      return new n(var1, var2x -> {
         var2.accept(var2x);
         e.d(this.g);
      });
   }

   private f.c a(Runnable var1, String var2) {
      return com.yiyiaddon.l.c.f.b(() -> {
         var1.run();
         e.d(this.g);
         this.f.C();
      }, var2);
   }
}
