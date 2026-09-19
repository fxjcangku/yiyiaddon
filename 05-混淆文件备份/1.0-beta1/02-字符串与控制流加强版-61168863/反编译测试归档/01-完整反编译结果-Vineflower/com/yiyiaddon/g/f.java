package com.yiyiaddon.g;

import java.util.List;

public record f(int se, int sf, int sg, int sh, List<f.a> cD) {
   public static f a() {
      return new f(0, 0, 0, 0, List.of());
   }

   public int dp() {
      return this.se;
   }

   public int dq() {
      return this.sf;
   }

   public int dr() {
      return this.sg;
   }

   public int ds() {
      return this.sh;
   }

   public List<f.a> bv() {
      return this.cD;
   }

   public record a(String CD, String CE, boolean fk, long aC) {
      public String a() {
         return this.CD;
      }

      public String T() {
         return this.CE;
      }

      public boolean eZ() {
         return this.fk;
      }

      public long u() {
         return this.aC;
      }
   }
}
