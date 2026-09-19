package com.yiyiaddon.e.b.g;

import com.yiyiaddon.l.b.h;
import com.yiyiaddon.l.b.w;
import com.yiyiaddon.l.h.f;
import com.yiyiaddon.l.j.i;
import com.yiyiaddon.l.j.p;
import io.github.humbleui.skija.Canvas;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.gui.screens.Screen;

public final class d extends f {
   private static final String bp = (String)com.yiyiaddon.m.b.a<"sioiod6l5lqqw","isZYOsJW2ug6y/oGCZrqJIyQU/mH/TLmFPcdXAnmfjR5KruLA2ro/uejs1JVo9KksSSocvVi+NzDKgI7nlzQoxzFW7yoJUGCxdukk/pt",2633234619185619012,7695736255442818036,1680098835001050160,-418720468247121382>();
   private static final String bq = (String)com.yiyiaddon.m.b.a<"s2yuis7zjj75jd","kKPtv/DRI+B8o7nYc5sUjo3rkOZ5ptRjMwT6xw4Xb4+F4jqJRIwRddGGkhqwHqFijlStvsqwYd3caN79CwQN/fRr1a+r8ZwdOA6NpHuf",2575086935955669399,-6561865697295021994,-7239795818908858315,6052086531390544132>();
   private static final float F = 22.0F;
   private static final float G = 11.0F;
   private final com.yiyiaddon.e.b.a e;
   private final com.yiyiaddon.e.b.b.a e;

   public d(Screen var1, com.yiyiaddon.e.b.a var2) {
      super(
         (String)com.yiyiaddon.m.b.a<"s3q47jn9j5fws6","d/Z47wGZzB00EUoKvopnXIOoWH/WsX0R62UkvA3W0P+EPxVeX1P7fA==",1470717953357748193,8920705574280863897,-7010546621904419670,3200222009854899050>(),
         var1
      );
      this.e = var2;
      this.e = var2.a();
      this.G();
   }

   private void G() {
      this.d()
         .a(
            new w(
                  (String)com.yiyiaddon.m.b.a<"sioiod6l5lqqw","isZYOsJW2ug6y/oGCZrqJIyQU/mH/TLmFPcdXAnmfjR5KruLA2ro/uejs1JVo9KksSSocvVi+NzDKgI7nlzQoxzFW7yoJUGCxdukk/pt",2633234619185619012,7695736255442818036,1680098835001050160,-418720468247121382>()
               )
               .a(22.0F)
               .b(11.0F)
         );
      List var1 = com.yiyiaddon.c.a.a.a(com.yiyiaddon.k.b.a.a());
      if (var1.isEmpty()) {
         switch ((int)com.yiyiaddon.m.b.a<"sxvy80dyywuu9","nFkWH6z/WmUeWAYxuGU7X8NelozmNfkmj5yswoVF638=",-5621599162963965154,1917266477314540227,-2621618128657997284,-6915880365030461574>()) {
            case 775081683:
               this.d()
                  .a(
                     new w(
                        (String)com.yiyiaddon.m.b.a<"s2yuis7zjj75jd","kKPtv/DRI+B8o7nYc5sUjo3rkOZ5ptRjMwT6xw4Xb4+F4jqJRIwRddGGkhqwHqFijlStvsqwYd3caN79CwQN/fRr1a+r8ZwdOA6NpHuf",2575086935955669399,-6561865697295021994,-7239795818908858315,6052086531390544132>()
                     )
                  );
               return;
            default:
               throw null;
         }
      } else {
         Iterator var2 = var1.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"srv51tdudtvgm","6Rj8WAnwQH2Z2zA8nyPRMF4x7NFr6fbJN2ZNw62DztE=",-4781544494809460545,1207758964292104351,4784928766857898793,7578615911802604190>()) {
            case -914688823:
               while (var2.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3q39x7twvspxj","AlFuF1cvXB9gDWa8uXkrjdHEQm5RtfXLHZyDwVBLP7U=",5741999417432937608,6387628877668465279,-2207627978729139126,761552431068472685>()) {
                     case -1606105915:
                        com.yiyiaddon.g.c.d var3 = (com.yiyiaddon.g.c.d)var2.next();
                        String var4 = var3.dF();
                        this.d()
                           .a(
                              new h(
                                 var3.m() + "",
                                 new d.a(
                                    new i(
                                       1.0,
                                       2304.0,
                                       1.0,
                                       (String)com.yiyiaddon.m.b.a<"s3dlpz5ncow8wy","jLBXtInUY/p3XKu6vqsjNna0DeRgqrrIU7hYttuccBfUM2Zo",348722625875722941,-5397091949787959310,562444862419404311,-7724442741738392978>(),
                                       () -> (double)this.c(var4),
                                       var2x -> {
                                          this.e.c(var4, (int)Math.round(var2x));
                                          this.e.L();
                                       }
                                    ),
                                    () -> {
                                       this.e.j.remove(var4);
                                       this.e.L();
                                    }
                                 )
                              )
                           );
                        switch ((int)com.yiyiaddon.m.b.a<"s2mfqndukesso4","wYDjd46JvZj6RbbXjp7jdc5s1g7r25cnDun0WCE+fe8=",6092920390363015759,306344518570850838,7297471149867451052,8259259795612003499>()) {
                           case -129498608:
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
   }

   private int c(String var1) {
      int var2 = this.e.b(var1);
      if (var2 == 0) {
         switch ((int)com.yiyiaddon.m.b.a<"s1yl139x7i3612","aL3vkjMkn2phMxJ224wLQM+d1JiKzb6Js/0S4ip4xQU=",5438822462834483083,8120542975496711178,-6645557177305751588,-3839286581048281621>()) {
            case 63633854:
               switch ((int)com.yiyiaddon.m.b.a<"s2kvb3raejvbmc","AWts2mKtarZzgVQu/SrZL3OXjuavb1h9yLCwkha3Aps=",7072940293798138748,-5593299764940327339,6390571262674046234,661350326483051829>()) {
                  case 1919734474:
                     return 64;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         switch ((int)com.yiyiaddon.m.b.a<"spqndd0jvm74k","DNejpJ0q8ODGbUsfHT8mCuHMEF5eJ/SoO4ELtGoIQRg=",1886435318686816369,-8917720850941108552,-5350278768703568542,8967001846537470443>()) {
            case -1598710766:
               return var2;
            default:
               throw null;
         }
      }
   }

   private static final class a extends p {
      private static final float H = 8.0F;
      private final i a;
      private final com.yiyiaddon.l.j.b a;

      private a(i var1, Runnable var2) {
         this.a = var1;
         this.a = new com.yiyiaddon.l.j.b(
            (String)com.yiyiaddon.m.b.a<"s3ubu0k7oww981","wL/WcvI0YdQ3kPPnt37LrJ6hTcMLkd1wHQKOPgLK",-4894021243324465974,-805222730090906040,-1462614580029208896,7078610197756032250>(),
            var2
         );
      }

      @Override
      public float c() {
         return this.a.c() + 8.0F + this.a.c();
      }

      @Override
      public float d() {
         return Math.max(this.a.d(), this.a.d());
      }

      @Override
      public void a(float var1) {
         this.a.a(var1);
         this.a.a(var1);
      }

      @Override
      public void b(Canvas var1, float var2, float var3, float var4) {
         this.a.b(var1, var2, this.a(var3), var4);
         this.a.b(var1, var2 + this.a.c() + 8.0F, this.b(var3), var4);
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, int var5) {
         if (!this.a.a(var1, var2, var3, this.a(var4), var5)) {
            label22:
            switch ((int)com.yiyiaddon.m.b.a<"s1vyp7nr5cm4g6","hjXdlDOmEtTNW6GorgmampF8UApvLBo4T0gopBPtXc8=",8321824718613401804,-8599905220846809546,5863091201221503720,9119566024533209524>()) {
               case 798782387:
                  if (!this.a.a(var1, var2, var3 + this.a.c() + 8.0F, this.b(var4), var5)) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3059d8c1id9i5","k57aWwzcfdN8PfdRUvjFIouErvs4uMTQwP9Gvn+aHrc=",-6433712767423513831,4694115369992045108,8995419974988188962,-2094124666152887218>()) {
                        case 430464039:
                           return false;
                        default:
                           throw null;
                     }
                  }

                  switch ((int)com.yiyiaddon.m.b.a<"s3hoawx1m55exg","hjzYF4RbZtdTt27yIU6YSMDzT3PGym5HlIgg4t3O3g8=",-6642862190562689545,-5986920506154086287,4958448347247177350,5341916022570507681>()) {
                     case 170375220:
                        break label22;
                     default:
                        throw null;
                  }
               default:
                  throw null;
            }
         }

         switch ((int)com.yiyiaddon.m.b.a<"sayu5no38hx5z","U5aifx7mofwSSovjbfaBY7nYM2B9oChIlw/+J5OJlrc=",-6111603988965547446,6611274997226594399,5092440992732919852,4616372682772368410>()) {
            case 395686152:
               return true;
            default:
               throw null;
         }
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4) {
         return this.a.a(var1, var2, var3, this.a(var4));
      }

      private float a(float var1) {
         return var1 + (this.d() - this.a.d()) / 2.0F;
      }

      private float b(float var1) {
         return var1 + (this.d() - this.a.d()) / 2.0F;
      }
   }
}
