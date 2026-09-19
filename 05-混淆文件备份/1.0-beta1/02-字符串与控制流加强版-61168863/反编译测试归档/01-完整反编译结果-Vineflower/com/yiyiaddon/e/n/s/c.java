package com.yiyiaddon.e.n.s;

import com.yiyiaddon.l.b.w;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;

public final class c extends com.yiyiaddon.l.h.f {
   private final Runnable h;
   private boolean eg;

   public c(Screen var1, com.yiyiaddon.e.n.b var2, BlockPos var3, BlockPos var4, BiConsumer<String, String> var5, Runnable var6) {
      super(
         (String)com.yiyiaddon.m.b.a<"s1bxajyhhtvgq0","2gc2S5mWRdsWrbTB715U9wgB8L3P3fgOEoIPHrjIuIZyfDOziPJ8xA==",4874398319680668840,-1581571804744447461,5347217280635860373,-1644744070726073702>(),
         var1
      );
      this.kQ();
      this.h = var6;
      this.a(var2, var3, var4, var5);
   }

   private void a(com.yiyiaddon.e.n.b var1, BlockPos var2, BlockPos var3, BiConsumer<String, String> var4) {
      this.w(
         (String)com.yiyiaddon.m.b.a<"s2k7ow738ffa9k","WWMgD9dXDPxd7aXDsYz8M2JtZEIs7Bc0dC0K40Y4qpU=",1042393341275152928,-7729292979966089450,1600298121164713734,-7848859065420817887>(),
         a(var2, var3)
      );
      this.w(
         (String)com.yiyiaddon.m.b.a<"s2twuybhv3ptj9","5Q8mXthh8RWv0t6mg1EHF7GAinzHagEtuuh6+A2dJGQf1w==",-6048007127683187378,-5874539086174705268,-3501666126250150255,-6897622696635661925>(),
         a(var2, var3) + ""
      );
      this.kN();
      this.bw(
         (String)com.yiyiaddon.m.b.a<"s3b45ocaqc5ekj","n3mMWslOVLzO7GKozNMEa2Jq+Nuf0Q/KnU7K0PweBZY8amo/Rr1YJo+Lv3fjUWT2oBo6Zjm2a9Jr9j2Vexc=",-8952866863944769613,2233234665204718526,-3545644150874357991,-364216943294858862>()
      );
      List var5 = var1.aG();
      if (var5.isEmpty()) {
         label23:
         switch ((int)com.yiyiaddon.m.b.a<"sr7as1q58gt3f","EZB9q4UKyfi8u3q95jYNR9RtH5mK+xp//S4mOB41mJ4=",-6248078912574566262,-8305534933854458492,4398210802726491251,3968864882746495735>()) {
            case -274218084:
               this.d()
                  .a(
                     new w(
                        (String)com.yiyiaddon.m.b.a<"s1x7oj7xbal089","jbqwlqGDcNwUtcXLTS9OQK5wSE5R3zRB2r7O92VNsC8L68kb98+TKSZbcRrTc/p/CHzJHBkY0+M60bwYqNc4eA==",6137042810319488097,-475138004545532501,6718221291465728630,-2260468004012364420>()
                     )
                  );
               this.d()
                  .a(
                     new w(
                        (String)com.yiyiaddon.m.b.a<"s8cilpyj4cfn2","Pd53sDI890FX/j9azUZ4o3Tt6pdEbD7fnQjwQluNOSdgfg2Q6Yx9fI9k4+rHuPrOYNIF1rwb9LFtnNELwUPjb5xQUymWdCf8",-1560563939325640726,-2737241919752125606,-5489834908001362619,300675032945474716>()
                     )
                  );
               switch ((int)com.yiyiaddon.m.b.a<"s3v4ajv57goq3w","sxgwAS/EUSiWa2rEn3p8yt+qLml9Gz30LZd/6VPIQXg=",3779762057594325034,1298193907318910291,4473201576061922136,9100169873434928108>()) {
                  case -601700013:
                     break label23;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         Iterator var6 = var5.iterator();
         switch ((int)com.yiyiaddon.m.b.a<"s24kd986dp14kt","uspN/I+H3IqYXm46DHZx/LKgSxORquLj0HSc7zo6ae0=",2566192840024340560,-5197610073054629096,-2540221925225104030,5927598591427244840>()) {
            case -1906273911:
               while (var6.hasNext()) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1jzlorx19o4xz","CQDkO82NS+67KoIkiRRi6NQxhLK6+BVpj7Y0+q0ZMU4=",637039168859584591,-9144774578739355305,8623155255492472428,8692593206021325529>()) {
                     case -735044604:
                        String var7 = (String)var6.next();
                        String var8 = var1.aw(var7);
                        this.a(q(var7, var8), () -> this.a(var7, var8, var4));
                        switch ((int)com.yiyiaddon.m.b.a<"s202bj5mrnzr6e","th76M8LRGE/lipcrkiTq0+GjE38CADKcJXovERuCShY=",-3364626486408125687,94388999115064813,-6511583919322898053,2990943407877250218>()) {
                           case 675780857:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }
               break;
            default:
               throw null;
         }
      }

      this.kN();
      this.d()
         .a(
            new w(
               (String)com.yiyiaddon.m.b.a<"s35yt3lsnwl29m","aq56nzCsbQenhazUXT/xz2m7ARqSXGrN2nKGSQEYYRvTex6RAZXAPxzHvui4LSTlBo0gPKakBjj2WI9l0w2uKn2g7mPred/4TKFSLbSpJ38Z60q3ysk=",-1870804474979144630,6005693858992668365,-6386248928251293794,8871369865605868601>()
            )
         );
   }

   private static String q(String var0, String var1) {
      com.yiyiaddon.e.n.j.e var2 = com.yiyiaddon.e.n.j.a.c(var0);
      switch (var2) {
         case NETHER:
            String var3 = var1 + "";
            switch ((int)com.yiyiaddon.m.b.a<"s27bi6ba87wy6b","mdl2KQSc7P8cbDBLJDwFNvcGpziCvD+MgjZ6Srz/M+Q=",-2280212588757079008,-3310759727408408469,-8477954393334763650,-4611758115087277571>()) {
               case 416639805:
                  return var3;
               default:
                  throw null;
            }
         case END:
            String var10000 = var1 + "";
            switch ((int)com.yiyiaddon.m.b.a<"srz2odnn36r6","oCRPXlaT+tZUbGvs+2zH5E1GrRSlx9uCotOaRlc574A=",-2011750494427520172,-5627070008781738741,-4187556182183419784,4825677175659476712>()) {
               case 596883217:
                  return var10000;
               default:
                  throw null;
            }
         case NORMAL:
            switch ((int)com.yiyiaddon.m.b.a<"s18b0dwbgsmb7u","QBYiwgUdF+HfsP4HZFSJCXrr0rct7aD2ORNp0yutWoI=",-6397522625363068922,8151410148234575522,3298175741351651587,9145614049698136491>()) {
               case 1043220954:
                  return var1;
               default:
                  throw null;
            }
         default:
            throw new MatchException(null, null);
      }
   }

   private void a(String var1, String var2, BiConsumer<String, String> var3) {
      if (this.eg) {
         switch ((int)com.yiyiaddon.m.b.a<"s1rh3zj3ztf9zz","powVmxkTw609d2WqdNGvyTNsUfCoMuHozt3EnuRO08A=",895444280298133017,5588659431245893490,3217370756480945738,-4592730336108922434>()) {
            case -285698401:
               return;
            default:
               throw null;
         }
      } else {
         this.eg = true;
         var3.accept(var1, var2);
         this.kR();
      }
   }

   private static String a(BlockPos var0, BlockPos var1) {
      return ""
         + Math.min(var0.getX(), var1.getX())
         + Math.max(var0.getX(), var1.getX())
         + Math.min(var0.getZ(), var1.getZ())
         + Math.max(var0.getZ(), var1.getZ());
   }

   private static int a(BlockPos var0, BlockPos var1) {
      return (Math.abs(var0.getX() - var1.getX()) + 1) * (Math.abs(var0.getZ() - var1.getZ()) + 1);
   }

   @Override
   public void removed() {
      super.removed();
      if (!this.eg) {
         switch ((int)com.yiyiaddon.m.b.a<"skbzusb23radh","c3Q8i/Wy2TSWsdhzfD4baNJba0z3sRApcycieFE/oPQ=",-1297703684737126345,1253723161773873498,6976614348395201158,-1769481039903441569>()) {
            case -316494479:
               this.h.run();
               switch ((int)com.yiyiaddon.m.b.a<"s2f73kt2xvglpo","BHKpDrD0rFC9l8myjMbX4rR5vReSxB+4BrVYC+64r9s=",-9059892226449967024,-2774571831989212167,1464601606221848576,-1605387938557272592>()) {
                  case 295888712:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }
}
