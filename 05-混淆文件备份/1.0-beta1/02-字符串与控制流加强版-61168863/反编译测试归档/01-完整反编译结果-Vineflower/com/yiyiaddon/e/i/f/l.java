package com.yiyiaddon.e.i.f;

import java.util.Objects;

public record l(boolean bN, String lP) {
   public l(boolean bN, String lP) {
      lP = Objects.requireNonNullElse(
         lP,
         (String)com.yiyiaddon.m.b.a<"s2jvkvpzgf8w8x","guFIMyxo3pac0Wlwgm/w0b+s4OkiSambzvDKnQ==",-1029374552941697922,3091838401741238788,1667000777024079554,7114906397220100667>()
      );
      this.bN = bN;
      this.lP = lP;
   }

   public static l a() {
      return new l(
         true,
         (String)com.yiyiaddon.m.b.a<"s2jvkvpzgf8w8x","guFIMyxo3pac0Wlwgm/w0b+s4OkiSambzvDKnQ==",-1029374552941697922,3091838401741238788,1667000777024079554,7114906397220100667>()
      );
   }

   public static l a(String var0) {
      return new l(false, var0);
   }

   public boolean aO() {
      return this.bN;
   }

   public String br() {
      return this.lP;
   }
}
