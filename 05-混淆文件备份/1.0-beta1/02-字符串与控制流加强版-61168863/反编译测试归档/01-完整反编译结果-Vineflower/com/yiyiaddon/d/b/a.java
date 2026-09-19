package com.yiyiaddon.d.b;

import com.google.gson.JsonObject;
import com.yiyiaddon.l.f.i;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import net.minecraft.client.Minecraft;

public abstract class a {
   private final String id;
   private final String ai;
   private final String aj;
   private final String ak;
   private volatile boolean m;

   protected a(String var1, String var2, String var3, String var4) {
      this.id = Objects.requireNonNull(
         var1,
         (String)com.yiyiaddon.m.b.a<"s359zeiakyk4wm","U9ZOtJoDuXTsOCG4PwRKE+5Z9f1hF3YupBpHY3sVUAj/fVBS4jX4zpylmuugD66l",2571862771725768272,-4832015183267622229,-8680098097604194732,5251980858758251231>()
      );
      this.ai = var2 != null && !var2.isBlank() ? var2 : var1;
      this.aj = var3 == null
         ? (String)com.yiyiaddon.m.b.a<"s2p425k7vx52md","/dbK19gOf1Tq8sq1ytsTm/tYTXbLAwGiigjYVw==",1182039100835578224,-3165826620984900444,5887617047033814185,-1336998545578315601>()
         : var3;
      this.ak = var4 == null
         ? (String)com.yiyiaddon.m.b.a<"s2p425k7vx52md","/dbK19gOf1Tq8sq1ytsTm/tYTXbLAwGiigjYVw==",1182039100835578224,-3165826620984900444,5887617047033814185,-1336998545578315601>()
         : var4;
   }

   public final String s() {
      return this.id;
   }

   public String a() {
      return this.id;
   }

   public final String t() {
      return this.ai;
   }

   public final String u() {
      return this.aj;
   }

   public final String v() {
      return this.ak;
   }

   public String w() {
      return (String)com.yiyiaddon.m.b.a<"s2p425k7vx52md","/dbK19gOf1Tq8sq1ytsTm/tYTXbLAwGiigjYVw==",1182039100835578224,-3165826620984900444,5887617047033814185,-1336998545578315601>();
   }

   public int i() {
      return 100;
   }

   public String x() {
      return (String)com.yiyiaddon.m.b.a<"s3ev65yk9dexmc","+/jFBZHck3LwGm/abfRAKvbEuUEaQ0mmvbBXyek+ZaKf2Y6Ynxk=",8644730018667680588,-3740163791175037555,-2383674403741298598,-6231275756008644199>();
   }

   public final boolean g() {
      return this.m;
   }

   public boolean h() {
      return false;
   }

   public final String y() {
      return this.id + "";
   }

   protected void onInitialize() {
   }

   protected void m() {
   }

   protected void n() {
   }

   protected boolean i() {
      return false;
   }

   public void b(Minecraft var1) {
   }

   public List<String> f() {
      return List.of();
   }

   public Set<com.yiyiaddon.d.a.c> c() {
      return Set.of();
   }

   public void d(com.yiyiaddon.d.a.a var1) {
   }

   public void a(JsonObject var1) {
   }

   public void b(JsonObject var1) {
   }

   public String z() {
      return null;
   }

   public i a() {
      return null;
   }

   public List<com.yiyiaddon.a.a> g() {
      return List.of();
   }

   final void a(boolean var1) {
      this.m = var1;
   }

   @Override
   public String toString() {
      return this.ai + this.id;
   }
}
