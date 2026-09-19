package com.yiyiaddon.e.i.g;

import com.yiyiaddon.l.b.h;
import com.yiyiaddon.l.b.w;
import com.yiyiaddon.l.f.i;
import com.yiyiaddon.l.h.d;
import net.minecraft.client.Minecraft;

public final class b extends com.yiyiaddon.l.f.c implements i {
   private static final String lS = (String)com.yiyiaddon.m.b.a<"s1w9xy372qvwb","b09l9Y5w5RABfOwVyH/8790CFvIr0Z8PSeElWXOyCsII1g4fzw7YxyTt",1437446550480990817,-709614055909789006,8712237168260584803,-4536299725799292723>();
   private static final String lT = (String)com.yiyiaddon.m.b.a<"s2eo3yktgqxr6j","FydOKb0vs7stwllR3pGCHrfEtv0EBjF4DnW9PFA94nN/JE2BqiU0WTmxnFK6xzqZqllHEzmAPaEOv3azLfsM9ZkSFNcxO3P9",-8525513787254010561,3310674641221721654,-3690471140179652705,-3538589399901046340>();
   private static final String[] G = d.a(d.a(a.h));
   private final com.yiyiaddon.e.i.a c;
   private boolean v;

   public b(com.yiyiaddon.e.i.a var1) {
      this.c = var1;
   }

   @Override
   public com.yiyiaddon.l.f.a a(com.yiyiaddon.h.d var1) {
      if (!this.v) {
         switch ((int)com.yiyiaddon.m.b.a<"svglarj36vz1f","G5ukfzDgF39ZaJ98MdH+4bLXwYg/homkQhurvYIlYNQ=",-329302845245404235,-4288824173203808330,5588181857021892071,5717677143273774624>()) {
            case 17014384:
               this.v = true;
               this.G();
               switch ((int)com.yiyiaddon.m.b.a<"s3d6hw2f5ehph0","03BrmQshQGLMhdhgFqN6FO4UD4yFEOIzIMoM0Lvq5f8=",-2723527310996521839,7955218423197381934,-3933888155177157037,-4281446494636914655>()) {
                  case 771343185:
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
      this.b(new w(this.c::ae));
      this.b(
         new h(
               (String)com.yiyiaddon.m.b.a<"s1ayf10zj7bxkd","EU5Gt5DmOBeHXbbuzkbn+i96mcGzOqMxH1wcpg==",6247715888929188500,-49682536407349210,-3479756909281431064,3335715090153282021>(),
               () -> (String)com.yiyiaddon.m.b.a<"s2eo3yktgqxr6j","FydOKb0vs7stwllR3pGCHrfEtv0EBjF4DnW9PFA94nN/JE2BqiU0WTmxnFK6xzqZqllHEzmAPaEOv3azLfsM9ZkSFNcxO3P9",-8525513787254010561,3310674641221721654,-3690471140179652705,-3538589399901046340>(),
               new com.yiyiaddon.l.j.a(
                  (String)com.yiyiaddon.m.b.a<"s1w9xy372qvwb","b09l9Y5w5RABfOwVyH/8790CFvIr0Z8PSeElWXOyCsII1g4fzw7YxyTt",1437446550480990817,-709614055909789006,8712237168260584803,-4536299725799292723>(),
                  this::H
               )
            )
            .a()
      );
      String[] var1 = G;
      int var2 = var1.length;
      int var3 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"s28sgc0f3bamz4","AWnwaF1jrNByO2eAVZgjf9HVvQYbGe31EC678YwedYM=",5078303610170330557,7032051703616683013,-7439613483594502506,-2200522046840517140>()) {
         case -913691627:
            while (var3 < var2) {
               switch ((int)com.yiyiaddon.m.b.a<"s1vzybs2m9o850","7+RkhMiHO2RjItI83hqMUGLzfGLtukLosU17xBb1d/s=",492450564488053894,-6512191315608031886,6344951500163741832,-1278754830008731392>()) {
                  case -2059484966:
                     String var4 = var1[var3];
                     this.b(new w(var4));
                     var3++;
                     switch ((int)com.yiyiaddon.m.b.a<"s28q75vl0elptt","lUUMqtpXfKhYca3z2LmyN5VKrg6sgm9h9NIwQPy8xfU=",4422059699428016060,-1536504472671656229,-8657494173337739444,-6811665694786713339>()) {
                        case 706902595:
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
         switch ((int)com.yiyiaddon.m.b.a<"s26yoqbl0kcuka","2CW8lUQ9Tea60qsgAm2/U8CqNM7s9n5asMhEel7xr2I=",2918109120910669337,-7737452070306524181,-6674289733950715360,-1231584126361653945>()) {
            case -730891902:
               return;
            default:
               throw null;
         }
      } else {
         var1.setScreen(new com.yiyiaddon.e.i.g.a.a(this.c));
      }
   }
}
