package com.yiyiaddon.h;

import com.yiyiaddon.l.f.i;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

public final class d {
   private final String DO;
   private final String DP;
   private final String DQ;
   private final String DR;
   private final String DS;
   private final String DT;
   private final int sx;
   private final String DU;
   private final BooleanSupplier e;
   private final Supplier<i> e;

   private d(d.a var1) {
      this.DO = var1.DV;
      this.DP = var1.name;
      this.DQ = var1.M;
      this.DR = var1.aj;
      this.DS = var1.ak;
      this.DT = var1.Cj;
      this.sx = var1.ei;
      this.DU = var1.DU;
      this.e = var1.e;
      this.e = var1.d;
   }

   public static d.a a(String var0) {
      return new d.a(var0);
   }

   public String s() {
      return this.DO;
   }

   public String a() {
      return this.DP;
   }

   public String m() {
      return this.DQ;
   }

   public String u() {
      return this.DR;
   }

   public String c() {
      return this.DS;
   }

   public String w() {
      return this.DT;
   }

   public int i() {
      return this.sx;
   }

   public String x() {
      return this.DU;
   }

   public boolean ar() {
      return this.e.getAsBoolean();
   }

   public Supplier<i> b() {
      return this.e;
   }

   public static final class a {
      private final String DV;
      private String name = (String)com.yiyiaddon.m.b.a<"s4cj2aym3e6c4","mx5q9kGHp//9S0KCQnQBoxdRLNdN4Q+Hxi1FWQ==",4290454689930121069,8635330121464244511,-3553384848994275260,4332592616089205737>();
      private String M = (String)com.yiyiaddon.m.b.a<"s4cj2aym3e6c4","mx5q9kGHp//9S0KCQnQBoxdRLNdN4Q+Hxi1FWQ==",4290454689930121069,8635330121464244511,-3553384848994275260,4332592616089205737>();
      private String aj = (String)com.yiyiaddon.m.b.a<"s4cj2aym3e6c4","mx5q9kGHp//9S0KCQnQBoxdRLNdN4Q+Hxi1FWQ==",4290454689930121069,8635330121464244511,-3553384848994275260,4332592616089205737>();
      private String ak = (String)com.yiyiaddon.m.b.a<"s4cj2aym3e6c4","mx5q9kGHp//9S0KCQnQBoxdRLNdN4Q+Hxi1FWQ==",4290454689930121069,8635330121464244511,-3553384848994275260,4332592616089205737>();
      private String Cj = (String)com.yiyiaddon.m.b.a<"s4cj2aym3e6c4","mx5q9kGHp//9S0KCQnQBoxdRLNdN4Q+Hxi1FWQ==",4290454689930121069,8635330121464244511,-3553384848994275260,4332592616089205737>();
      private int ei;
      private String DU = (String)com.yiyiaddon.m.b.a<"s4cj2aym3e6c4","mx5q9kGHp//9S0KCQnQBoxdRLNdN4Q+Hxi1FWQ==",4290454689930121069,8635330121464244511,-3553384848994275260,4332592616089205737>();
      private BooleanSupplier e = () -> false;
      private Supplier<i> d;

      private a(String var1) {
         this.DV = Objects.requireNonNull(
            var1,
            (String)com.yiyiaddon.m.b.a<"s2wrgms0ekz3av","LT1QuAEb2EwRp63yI/81b7f16l4F3wxk9r8DNoaDdza8f/Hp+WWivRlwSv6OzPgE",-5650060688912423428,-4301877622285569420,-7091673343255799014,6813819308703446862>()
         );
      }

      public d.a b(String var1) {
         this.name = var1;
         return this;
      }

      public d.a c(String var1) {
         this.M = var1;
         return this;
      }

      public d.a d(String var1) {
         this.aj = var1;
         return this;
      }

      public d.a e(String var1) {
         this.ak = var1;
         return this;
      }

      public d.a f(String var1) {
         this.Cj = var1;
         return this;
      }

      public d.a a(int var1) {
         this.ei = var1;
         return this;
      }

      public d.a g(String var1) {
         this.DU = var1;
         return this;
      }

      public d.a a(BooleanSupplier var1) {
         BooleanSupplier var10001;
         if (var1 == null) {
            label15:
            switch ((int)com.yiyiaddon.m.b.a<"s2i02hykijbxrn","pAtiYl46ftK3bn7JWo/jmUV2K/2OuI/53vaMlNbrEwo=",178732365682119300,3065728401149154519,2455385436154400710,5605412340152746347>()) {
               case 1130134042:
                  var10001 = () -> false;
                  switch ((int)com.yiyiaddon.m.b.a<"s23qlkes2dm7pe","GMKECtRQ11QXZkEvxVRQZzkdwDiKuseUZkL6lPUTnsU=",-3679643361909012828,-8693839817818925032,6387503022033038465,5228238834672861368>()) {
                     case 1133098092:
                        break label15;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         } else {
            var10001 = var1;
            switch ((int)com.yiyiaddon.m.b.a<"s2910901wi90dx","O7x5cQZyZ7Rk4ukklbHtwctVlhqvLizWNXlA4HAUHNU=",8519417277901935013,-417602023657239743,1803769635325390633,596000979670121225>()) {
               case 1793609457:
                  break;
               default:
                  throw null;
            }
         }

         this.e = var10001;
         return this;
      }

      public d.a a(Supplier<i> var1) {
         this.d = var1;
         return this;
      }

      public d a() {
         return new d(this);
      }
   }
}
