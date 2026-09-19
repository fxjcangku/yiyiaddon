package com.yiyiaddon.e.d.c;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.resolver.ServerAddress;

public final class i {
   private final com.yiyiaddon.k.d.a a;

   public i(Minecraft var1, final com.yiyiaddon.e.d.a.a var2, Runnable var3) {
      this.a = new com.yiyiaddon.k.d.a(var1, new com.yiyiaddon.k.d.a.a() {
         @Override
         public boolean ar() {
            return var2.ai;
         }

         @Override
         public int D() {
            return var2.cg;
         }

         @Override
         public int E() {
            return var2.ch;
         }

         @Override
         public boolean as() {
            return var2.aj;
         }
      }, var3);
   }

   public void ae() {
      this.a.ae();
   }

   public void a(ServerAddress var1, ServerData var2) {
      this.a.a(var1, var2);
   }

   public boolean ap() {
      return this.a.ap();
   }

   public boolean a(int var1, int var2) {
      return this.a.a(var1, var2);
   }

   public void f() {
      this.a.f();
   }

   public void bi() {
      this.a.bi();
   }

   public int B() {
      return this.a.B();
   }

   public boolean aq() {
      return this.a.aq();
   }

   public int C() {
      return this.a.C();
   }

   public void aA() {
      this.a.aA();
   }

   public void bj() {
      this.a.bj();
   }
}
