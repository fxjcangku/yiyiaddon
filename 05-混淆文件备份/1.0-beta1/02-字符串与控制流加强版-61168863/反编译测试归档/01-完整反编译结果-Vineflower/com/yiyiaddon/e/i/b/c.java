package com.yiyiaddon.e.i.b;

import java.util.Objects;

public record c(a b, a c, long s, String lC) {
   public c(a b, a c, long s, String lC) {
      Objects.requireNonNull(
         b,
         (String)com.yiyiaddon.m.b.a<"s1rjevks193lz8","xQGdmbf/I5Els+nWu/X2KE0Pup8A4SSHhi9B4zdTJBfz2Nafdj9madG1/IkJZ921XMmEB+9L",9081743291677961987,-3502210801041878190,3018897670194345771,7925569323567964533>()
      );
      Objects.requireNonNull(
         c,
         (String)com.yiyiaddon.m.b.a<"shxcwcynu7s54","ktq0GQrxGyvi6AW4SZWzILzUS+PLusBwRiFleor1y9rWoFxmSibrJvRPYkE6jVUATp6wUQ==",8149396752229387183,9204842378710897338,-6577838674613027517,-2813565303541314358>()
      );
      lC = Objects.requireNonNullElse(
         lC,
         (String)com.yiyiaddon.m.b.a<"s3mlp33xammuta","XnWa68GMwHSZ5ctoh66TEC7k1iGi7BSDR02SgA==",4914271236792495433,4341024593723971329,-682545527844479349,537441479612276389>()
      );
      if (s < 0L) {
         throw new IllegalArgumentException(
            (String)com.yiyiaddon.m.b.a<"szkhzr78sy2ek","rhy+qg6VsVf0rXXH+I6TYwMLPzGEHDKGq45e5F4f1BRRsnOVbi3/trKx4o//0diSSq0=",-4305128144357778215,-907899987255207757,-8231929919798330431,6198070153960738582>()
         );
      }

      this.b = b;
      this.c = c;
      this.s = s;
      this.lC = lC;
   }

   public a c() {
      return this.b;
   }

   public a a() {
      return this.c;
   }

   public long k() {
      return this.s;
   }

   public String br() {
      return this.lC;
   }
}
