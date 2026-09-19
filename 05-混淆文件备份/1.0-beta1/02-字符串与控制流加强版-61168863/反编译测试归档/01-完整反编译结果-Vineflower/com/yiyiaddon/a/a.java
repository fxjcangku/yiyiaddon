package com.yiyiaddon.a;

import java.util.ArrayList;
import java.util.List;

public abstract class a {
   public abstract String a();

   public String b() {
      return this.a();
   }

   public c a(String[] var1) {
      return new c(var1, this.b());
   }

   public List<String> a() {
      return List.of();
   }

   public abstract String c();

   public String d() {
      return d.e() + this.a();
   }

   public abstract void a(c var1);

   public List<String> a(c var1) {
      return List.of();
   }

   public List<String> b() {
      ArrayList var1 = new ArrayList();
      var1.add(this.a());
      var1.addAll(this.a());
      return var1;
   }
}
