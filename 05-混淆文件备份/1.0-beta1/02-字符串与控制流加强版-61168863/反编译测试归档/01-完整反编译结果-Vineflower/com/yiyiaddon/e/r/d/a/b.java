package com.yiyiaddon.e.r.d.a;

import com.yiyiaddon.l.b.g;
import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.g.j;
import com.yiyiaddon.l.h.f;
import com.yiyiaddon.l.j.e;
import com.yiyiaddon.l.j.m;
import com.yiyiaddon.l.j.n;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

public final class b extends f implements com.yiyiaddon.l.c.b {
   private static final String Br = (String)com.yiyiaddon.m.b.a<"s1mp956gg8hegg","enddd+j/vuLaOnVbyKkt3Sppd79jl/hTVdhlE4XPUko1K0Fl1CIm2n8YRxVqLsTZx+d0YRJFzx9o4nprgBjiHPVLh1Ymsw==",-3095502976507059300,-5305756602873915632,-1530630365497665832,-6544854809960512841>();
   private static final int rz = 20;
   private static final float eo = 6.0F;
   private static final float ep = 11.0F;
   static final com.yiyiaddon.e.r.a.a b = new com.yiyiaddon.e.r.a.a();
   private final com.yiyiaddon.e.r.a e;
   private final com.yiyiaddon.e.r.d.a.b.a a = new com.yiyiaddon.e.r.d.a.b.a();
   private final Set<String> ay = new HashSet<>();
   private com.yiyiaddon.e.r.d.a.b.d a = com.yiyiaddon.e.r.d.a.b.d.OVERVIEW;
   private int E;
   private com.yiyiaddon.e.r.d.a.b.b a = com.yiyiaddon.e.r.d.a.b.b.a();

   public b(Screen var1, com.yiyiaddon.e.r.a var2) {
      super(
         (String)com.yiyiaddon.m.b.a<"sysgvoomjxtp3","Q5MRUk++QS7zzjjS7S7cq7AaiYUdsJwWplMa3fOPqfmbyMTyaDg=",6428422669049499966,-6390654026187577013,3338500055041304316,-3122947321906698390>(),
         var1
      );
      this.e = var2;
      this.c(var2::v);
      this.d().a(this.a);
      this.C();
   }

   @Override
   public void a(String var1, float var2, float var3) {
      j.c(var1, var2, var3);
   }

   public Minecraft a() {
      return this.minecraft;
   }

   public Set<String> k() {
      return this.ay;
   }

   public void D() {
      this.C();
   }

   @Override
   protected void init() {
      super.init();
      com.yiyiaddon.d.a.b.a(
         (String)com.yiyiaddon.m.b.a<"s1mp956gg8hegg","enddd+j/vuLaOnVbyKkt3Sppd79jl/hTVdhlE4XPUko1K0Fl1CIm2n8YRxVqLsTZx+d0YRJFzx9o4nprgBjiHPVLh1Ymsw==",-3095502976507059300,-5305756602873915632,-1530630365497665832,-6544854809960512841>(),
         com.yiyiaddon.d.a.c.TICK,
         var1 -> this.B()
      );
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1z9kemxqno9f3","JTizAbKY5pbNMugwFJhU8ZfsNbOXc/5wDJiPlbZbm1Q=",-4994738084218355438,1344770719329741157,-8814172012656494730,5926672773027591959>()) {
            case -1652859685:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s3vxilrd4dh3e7","M9ad82TTAJXJSng9cMG9XT6Jkj+QYRxqhSi3zM9OaHw=",3365660340210708624,-2935366717365519054,-3991209837643859191,1009902522695229733>()) {
                  case 1769244415:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      }
   }

   @Override
   public void removed() {
      com.yiyiaddon.d.a.b.h(
         (String)com.yiyiaddon.m.b.a<"s1mp956gg8hegg","enddd+j/vuLaOnVbyKkt3Sppd79jl/hTVdhlE4XPUko1K0Fl1CIm2n8YRxVqLsTZx+d0YRJFzx9o4nprgBjiHPVLh1Ymsw==",-3095502976507059300,-5305756602873915632,-1530630365497665832,-6544854809960512841>()
      );
      super.removed();
   }

   private void B() {
      if (++this.E < 20) {
         switch ((int)com.yiyiaddon.m.b.a<"sjmi8datccj5l","uNr8WluvFSYJiD00QPdy09qnmnyLduRWdt1dPY8OncA=",-8588660955963574054,-387220262899973470,5152788234539435736,-1330879016921587035>()) {
            case 1351068454:
               return;
            default:
               throw null;
         }
      } else {
         this.E = 0;
         if (this.minecraft != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s39q0wovagzu0t","HDVz5MQo85Yinmzuuyon4bcoFPNhdUnXTf7zGv8R4iI=",5311469345117571437,-8787589067312212120,1447973749326701346,-8359676501325468016>()) {
               case 571237928:
                  if (this.minecraft.screen == this) {
                     if (!c(0)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s8z4jrzr4zvh6","kPCm8GqkrpNo5q61CfdNUTV733zUTxljAWB0+VWVxuE=",-2960947396346621204,4393926448754735630,2974698935314749900,5177808076045045741>()) {
                           case -527663166:
                              if (!c(1)) {
                                 if (this.a == com.yiyiaddon.e.r.d.a.b.d.OVERVIEW) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s34rv5iotuk7ra","MeGKBrJWYpYKe6fsGUAsgaQc4YzkBTdP+RnzYCyC6ZQ=",930767655816657831,4877017864786972634,4745660700973862901,-1273294934815838040>()) {
                                       case 543603848:
                                          this.C();
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.a = com.yiyiaddon.e.r.d.a.b.b.a(this.e);
                                 return;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s3eas6yq9z7f90","iSknbjLy0aJMFO596S3yySLy2DarafAyPP5OMuOCzi8=",-5266699713643814820,-1088464716396490196,3091054335048304609,-1268563014605417695>()) {
                                 case 927147743:
                                    return;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s2hes93v0t4wmf","/q7oTeJFCfljQ5r6BuZhgZVBFl84zNYFaHgnYmW4c50=",-3206565263299875517,-7406818730438171301,4357914354687900087,1042411947669024623>()) {
                        case -416494445:
                           return;
                        default:
                           throw null;
                     }
                  }
               default:
                  throw null;
            }
         }
      }
   }

   private static boolean c(int var0) {
      Minecraft var1 = Minecraft.getInstance();
      if (var1 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s27c5qwnypdpen","pEf7vNeX/MVKVSHbagYqwW2SYxPOSzz1S8SKetNhxNQ=",-8637552911812727953,-4317443349373644325,8837169971788573879,5762738556164950220>()) {
            case 1292191003:
               if (var1.getWindow() != null) {
                  if (GLFW.glfwGetMouseButton(var1.getWindow().handle(), var0) == 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"s10on6l7mtw2a","B9GcWL89OEwAxTBoOraSUu8O74wSBIHDLanQ/3Bd9sQ=",-7231158617063833496,-1461438773545447689,-2823812597078467874,4236367765393225371>()) {
                        case -1412963040:
                           switch ((int)com.yiyiaddon.m.b.a<"s32oryuiyibw4a","XweSidr1n5UKQIdtUXhGueRxgApfmqmf0slj/RtGnTU=",-1225777901784808975,1393532930772942470,-1605944830500376076,8131397466215834406>()) {
                              case 1968659351:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s240ohaevkfo18","WB2ETrbJx1fWJpNjm1F0xG2srxCYJwDsDIIH/qxnKx4=",-9212411613958640461,7970739678554628067,-3931247139380354556,3979154842718738161>()) {
                        case 973659816:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s1gz1712peieu3","N/X5E5/0sZc3zDyLPkLEXCj8uHeuujgaLTxfXPckfMg=",6516100496222444459,1545049305446258975,-8046217731178270022,-2283954065351174194>()) {
                     case -1963710779:
                        return false;
                     default:
                        throw null;
                  }
               }
            default:
               throw null;
         }
      } else {
         return false;
      }
   }

   public void C() {
      m.lb();
      this.a = com.yiyiaddon.e.r.d.a.b.b.a(this.e);
      this.a.u();
   }

   private void a(com.yiyiaddon.e.r.d.a.b.d var1) {
      this.a = var1;
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s224os3kfd38us","1Ik82oCPzWQzxG1fK1HPODcVvSq48iIp7QnVXIQ+C2I=",-5275128720287994081,-4462744883501192984,5511110255526898747,6563569917245787124>()) {
            case 1443247724:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s4y06s84sjwtr","cyRAPeBeOWXvbZxyG8lCmfnoNAVtrtFw5WSK4Lsas7E=",-3272803676555998736,521165706010072752,2133501009027180553,-5969117535274940610>()) {
                  case 393172713:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"s1sjh5pe1orfu8","6dZee0saWGQm/lAiglaSXvQV7ZI8S4KHabNZVJfTAQM=",-651753875433083059,-5872807572678868383,-5605809749899547955,3280827966570306941>()) {
            case 514078954:
               return;
            default:
               throw null;
         }
      }
   }

   private void a(i var1) {
      var1.a(new com.yiyiaddon.l.c.a(this.e));
      var1.a(new com.yiyiaddon.e.r.d.a.b.c());
      this.b(var1);
      label19:
      switch (this.a) {
         case OVERVIEW:
            new com.yiyiaddon.e.r.d.a.d(this, this.e).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"sy6feduf1g1b0","Mv+oWl2MV7l6AGCGYUqxik94egzh0D1WOLL/6LW0jps=",636544155513635705,-7229189673314697617,-2918161065358274617,-4652257537537108520>()) {
               case -601910692:
                  break label19;
               default:
                  throw null;
            }
         case BLOCK:
            new com.yiyiaddon.e.r.d.a.a(this, this.e).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s1975yzfkd5rp6","LpmmWcngp9VbfCVpMvtXXo484J652bCzffK/HZr1wV8=",955547875319769860,4298249553762544751,6565374154400147076,-4823237407971374835>()) {
               case -175861472:
                  break label19;
               default:
                  throw null;
            }
         case ENTITY:
            new com.yiyiaddon.e.r.d.a.c(this, this.e).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"stx2d3u1wud7k","dJjn6rmAc36OBAbVPvB8ZcsTqJVq+CBldOTiyWGxBYI=",-1058239503373195329,7179283253516322456,3768183072338005522,8357439790007555670>()) {
               case 938965236:
                  break;
               default:
                  throw null;
            }
      }

      this.c(var1);
   }

   private void b(i var1) {
      ArrayList var2 = new ArrayList();

      for (com.yiyiaddon.e.r.d.a.b.d var6 : com.yiyiaddon.e.r.d.a.b.d.values()) {
         boolean var7 = var6 == this.a;
         com.yiyiaddon.l.j.a var8 = new com.yiyiaddon.l.j.a(var7 ? var6.D() + "" : var6.D() + "", () -> this.a(var6));
         var2.add(
            new com.yiyiaddon.l.c.f.c(
               var8,
               () -> {
                  if (var6 == this.a) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1bfbkr4v5t9u9","CF4jGfyitvmI7li8GJ4MU+erkSfhumPw0DXdeUYHszs=",-1626653806131960238,5108293102619778119,-8779495881878151494,-4142450707176827421>()) {
                        case 396352231:
                           switch ((int)com.yiyiaddon.m.b.a<"s3rxgbf2rdgiie","zoMw9Rdmkc7HnFc9t7mhy4Z4MrvkM1g6S6qDM18ATkE=",-6798297114319681244,3592089682359144797,781678732809633314,8482662517156278606>()) {
                              case -2097734612:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var10000 = var6.D() + "";
                     switch ((int)com.yiyiaddon.m.b.a<"s2d52paup9pdq5","0NBF2f4bBXecluZ2TdSz5Ls59wWRiaSpT2Kc+eDrXIo=",8207553555769166588,-6095361121664875298,-8212356305213410522,5714200122937527577>()) {
                        case -905106069:
                           return var10000;
                        default:
                           throw null;
                     }
                  }
               }
            )
         );
      }

      var1.a(new com.yiyiaddon.l.c.f.a(this, var2, 24.0F));
   }

   private void c(i var1) {
      var1.a(
         new com.yiyiaddon.l.c.f.a(
            this,
            List.of(
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"s3lrrv76mtt3b2","DOH1lGU99oLLTCTmGwjD03jp1Q3hbkdMDOZrOW1102akBKM1",5834930010816675829,-2361021139334939256,-23067217780927354,8823190039956124519>(),
                     this::C
                  ),
                  (String)com.yiyiaddon.m.b.a<"s1pdsp31qvu0lq","vieeZUzSgfVedwRVFysADQvTLZx23OfpI5otMbtZAOU7Mhz9fDpnViLVKPinW6bWZd1bkou1iVbN+jxot7NSIJ7GHcAE0Go7yT6KKdnLbixWb8w1iEi0UrUX/J+HlnbhR+v7sg==",-8512317103733752108,6479289779007160277,-1814159549278382737,8781320095323094373>()
               ),
               com.yiyiaddon.l.c.f.a(this, this.e, this::C),
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"s2oam3cxh173n6","AVdE2RzD09jVboHNfm5N/ySgC9qxd3ZuqcHwb7NDiR8azkLk",-6921220285865280051,6419839450282335008,2549610022521289986,-3026065418033692501>(),
                     () -> {
                        if (this.minecraft != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s1d7ancfau0b7c","kxe+4Y1QCBAr8ERndQcILj+K0wzt7s3IXYLJz7JhUww=",4642069446448649955,861675050707296644,-4029734946569255180,-8714743065471155638>()) {
                              case -1790381707:
                                 this.minecraft.setScreen(null);
                                 switch ((int)com.yiyiaddon.m.b.a<"s3pmfwxabpifwd","IubTqz8+8oKF123Zh5zXQpvPNNiVPBK864/eCQobNjc=",-756899713887219737,6010805331474769227,5473027877565628109,-7699171512789354732>()) {
                                    case 55570449:
                                       return;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }
                     }
                  )
               )
            ),
            24.0F
         )
      );
   }

   com.yiyiaddon.l.c.f.b a(String var1, String var2, Supplier<Boolean> var3, Consumer<Boolean> var4, Supplier<Boolean> var5) {
      return new com.yiyiaddon.l.c.f.b(this, () -> var1, var2, null, List.of(new com.yiyiaddon.l.c.f.c(new n(var3, var2x -> {
         var4.accept(var2x);
         this.e.L();
      })), com.yiyiaddon.l.c.f.b(() -> {
         var4.accept((Boolean)var5.get());
         this.e.L();
         this.D();
      }, var1)));
   }

   com.yiyiaddon.l.c.f.b b(String var1, String var2, Supplier<Integer> var3, Consumer<Integer> var4, Supplier<Integer> var5) {
      return new com.yiyiaddon.l.c.f.b(
         this,
         () -> var1,
         var2,
         null,
         List.of(
            new com.yiyiaddon.l.c.f.c(
               new com.yiyiaddon.l.j.i(
                  16.0,
                  128.0,
                  1.0,
                  (String)com.yiyiaddon.m.b.a<"s3w4sy890u4psg","2GgZhwiNDP7pSCu0MpxXmTAwWfMPaM+pjoqAU4ZZJSdmrcUZ",-8514106827153964280,-1562140010821489934,7154311103028246225,3567323459830396689>(),
                  () -> (double)((Integer)var3.get()).intValue(),
                  var2x -> {
                     var4.accept(var2x.intValue());
                     this.e.L();
                  }
               )
            ),
            com.yiyiaddon.l.c.f.b(() -> {
               var4.accept((Integer)var5.get());
               this.e.L();
               this.D();
            }, var1)
         )
      );
   }

   com.yiyiaddon.l.c.f.b a(String var1, String var2, com.yiyiaddon.l.g.a.d var3, com.yiyiaddon.l.g.a.d var4) {
      return new com.yiyiaddon.l.c.f.b(
         this,
         () -> var1,
         var2,
         (String)com.yiyiaddon.m.b.a<"snh0tjneek4x5","pz5CRB6/GkNOA/e+tGqSIb+X554Pnh/eQuYwcQrF15xIGDdlRH7vqgIA+lYQ6KFp59A=",3219228925613845078,1591044640504645733,-8088616589615874436,-2481856085880591436>(),
         List.of(new com.yiyiaddon.l.c.f.c(new com.yiyiaddon.l.j.c(var1, var3, this.e::L)), com.yiyiaddon.l.c.f.b(() -> {
            a(var3, var4);
            this.e.L();
            this.D();
         }, var1))
      );
   }

   com.yiyiaddon.l.c.f.b c(String var1, String var2, Supplier<com.yiyiaddon.l.g.a.j> var3, Consumer<com.yiyiaddon.l.g.a.j> var4, Supplier<Integer> var5) {
      return new com.yiyiaddon.l.c.f.b(
         this,
         () -> var1,
         var2,
         (String)com.yiyiaddon.m.b.a<"s1mxpg9bdvqy83","AMenmWrzUdFLt5DOUr/bYurz6NR11h+D0DSItSOz/V8ui5Q+L0H/sg==",3331984609903560393,-3302029026075773024,-8406257353639634933,-4190517405612359604>(),
         List.of(new com.yiyiaddon.l.c.f.c(new e(List.of(com.yiyiaddon.l.g.a.j.b()), () -> ((com.yiyiaddon.l.g.a.j)var3.get()).cr(), var2x -> {
            var4.accept(com.yiyiaddon.l.g.a.j.a(var2x));
            this.e.L();
         })), com.yiyiaddon.l.c.f.b(() -> {
            var4.accept(com.yiyiaddon.l.g.a.j.a((Integer)var5.get()));
            this.e.L();
            this.D();
         }, var1))
      );
   }

   com.yiyiaddon.l.c.f.b a(String var1, String var2, Supplier<String> var3, Runnable var4, Runnable var5, Runnable var6) {
      return new com.yiyiaddon.l.c.f.b(
         this,
         () -> var1 + (String)var3.get(),
         var2,
         null,
         List.of(
            new com.yiyiaddon.l.c.f.c(
               new com.yiyiaddon.l.j.a(
                  (String)com.yiyiaddon.m.b.a<"s3e1zbcdd0jb19","nLAV71YSD5+oas1F8UqxEDH63pO+j9dvDjSoV2mnZ1TEPkok",902825051532531966,-3972887551719908368,5400503444600451544,-961068896851994360>(),
                  var4
               ),
               (String)com.yiyiaddon.m.b.a<"s2nockmack1unt","xMpnAQ8wX5RSENuSnl0njFNY0b65YnvNMcxY9cv+HpJMucc3PZkBrCsRn8hWfauW1SkbIg61EQibeE04WMtohsMZvuee9WYCpq5jYdtYTF1+fdE4cvdpVA==",-6284536040051277308,-6427759490287105851,-9205070265027080908,6719569468347693134>()
            ),
            new com.yiyiaddon.l.c.f.c(
               new com.yiyiaddon.l.j.a(
                  (String)com.yiyiaddon.m.b.a<"s20cw5x8hkujuz","2W4PvXQZcVewVMXvsIwg9n3Md/45MbA+XzENx8hSFOE4f6b2",1388294770072055135,-4987740512430407777,7867341543033340582,-6788195696137817454>(),
                  () -> {
                     var5.run();
                     this.D();
                  }
               ),
               (String)com.yiyiaddon.m.b.a<"s16s0daqfcowun","Kz7QYCKfVmjbba0bwP30UYjpA+FUil75pWn4fS85A59UEkRmYhJ1frt9iIpfjlzgCWJJpyn98O5eLewl",-728270252194165528,3103905668827971133,6982938032169336166,-6864789865121185440>()
            ),
            com.yiyiaddon.l.c.f.b(var6, var1)
         )
      );
   }

   private static void a(com.yiyiaddon.l.g.a.d var0, com.yiyiaddon.l.g.a.d var1) {
      var0.b(var1.eh()).c(var1.ei()).a(var1.gj()).a(var1.l()).b(var1.m());
   }

   private final class a implements g {
      private i a = new i(6.0F);

      private void u() {
         i var1 = new i(6.0F);
         b.this.a(var1);
         this.a = var1;
      }

      @Override
      public float b() {
         return this.a.b();
      }

      @Override
      public void a(float var1) {
         this.a.a(var1);
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         this.a.a(var1, var2, var3, var4, var5, var3, var3 + this.a.b(), var6, var7);
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
         return this.a.a(var1, var2, var3, var4, var5, Float.MAX_VALUE, var6);
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5) {
         return this.a.a(var1, var2, var3, var4, var5, Float.MAX_VALUE);
      }

      @Override
      public void F() {
         this.a.F();
      }
   }

   private static final class b {
      private final String[] ai;

      private b(String[] var1) {
         this.ai = var1;
      }

      private static com.yiyiaddon.e.r.d.a.b.b a() {
         return new com.yiyiaddon.e.r.d.a.b.b(
            new String[]{
               (String)com.yiyiaddon.m.b.a<"s2ung5gyw3bv9i","yOXSsdgk5G+Zc8fSHEvN1rqlLZntWkSB6w0eSA==",6212806445758202662,7905035622529179876,-4240306896189071172,8264467968175268003>(),
               (String)com.yiyiaddon.m.b.a<"s2ung5gyw3bv9i","yOXSsdgk5G+Zc8fSHEvN1rqlLZntWkSB6w0eSA==",6212806445758202662,7905035622529179876,-4240306896189071172,8264467968175268003>(),
               (String)com.yiyiaddon.m.b.a<"s2ung5gyw3bv9i","yOXSsdgk5G+Zc8fSHEvN1rqlLZntWkSB6w0eSA==",6212806445758202662,7905035622529179876,-4240306896189071172,8264467968175268003>(),
               (String)com.yiyiaddon.m.b.a<"s2ung5gyw3bv9i","yOXSsdgk5G+Zc8fSHEvN1rqlLZntWkSB6w0eSA==",6212806445758202662,7905035622529179876,-4240306896189071172,8264467968175268003>(),
               (String)com.yiyiaddon.m.b.a<"s2ung5gyw3bv9i","yOXSsdgk5G+Zc8fSHEvN1rqlLZntWkSB6w0eSA==",6212806445758202662,7905035622529179876,-4240306896189071172,8264467968175268003>(),
               (String)com.yiyiaddon.m.b.a<"s2ung5gyw3bv9i","yOXSsdgk5G+Zc8fSHEvN1rqlLZntWkSB6w0eSA==",6212806445758202662,7905035622529179876,-4240306896189071172,8264467968175268003>()
            }
         );
      }

      private static com.yiyiaddon.e.r.d.a.b.b a(com.yiyiaddon.e.r.a var0) {
         if (var0 == null) {
            return a();
         }

         com.yiyiaddon.e.r.a.a var1 = var0.a();
         int var2 = var0.a().di();
         String var3 = var0.a().eR()
            ? (String)com.yiyiaddon.m.b.a<"s3082aakaiqqhn","ez4Pm5TJiRX6sXYdnvcRQwC6ZI69//0RybSJhUWs2xX5MFnA7TX8xuXvSBD9s+3i",-3701947070710044599,2402960077727238744,-2811107514131090513,7993649127627545395>()
            : (String)com.yiyiaddon.m.b.a<"s2ung5gyw3bv9i","yOXSsdgk5G+Zc8fSHEvN1rqlLZntWkSB6w0eSA==",6212806445758202662,7905035622529179876,-4240306896189071172,8264467968175268003>();
         String var4 = var2 > var0.a().bt().size()
            ? var0.a().bt().size() + ""
            : (String)com.yiyiaddon.m.b.a<"s2ung5gyw3bv9i","yOXSsdgk5G+Zc8fSHEvN1rqlLZntWkSB6w0eSA==",6212806445758202662,7905035622529179876,-4240306896189071172,8264467968175268003>();
         return new com.yiyiaddon.e.r.d.a.b.b(
            new String[]{
               (
                     var0.g()
                        ? (String)com.yiyiaddon.m.b.a<"syn9i2864wimj","475mN/fghsfWaYxcHfu9To9OQBqQWifo4gODDkpWvMoA980acXY=",-9209937790678483174,4638458214995789751,6038716306426672778,-1292821396463898283>()
                        : (String)com.yiyiaddon.m.b.a<"s83gowoijw98m","3ZNhES5779PjYSQMZSqsVjt6AwNTfNtyCj9mBOquHVq3QhPAdDk=",1685423450922698133,1016207094700631322,7058749133411688297,8150998032542307025>()
                  )
                  + "",
               (
                     var1.eX
                        ? (String)com.yiyiaddon.m.b.a<"s1v62mpx8ihtxm","/6PvCJc5Alafqa1yfdW0hCBJlF9xL+5KEnaAUbn8KWdaiw==",2455167141552705336,-670804684526836779,8324738041663936765,-126137766958168424>()
                        : (String)com.yiyiaddon.m.b.a<"s2r8flx4dy90vn","a29ZwpFXL4lnmXELVMNnc1K1h0c4Q9ZXi9VIR+/ZJu6eWg==",-2815721510859882769,-4466377304655907031,766114716385014220,-4576473621937273017>()
                  )
                  + "",
               var1.ct.size() + "",
               var2 + var3 + var4,
               (
                     var1.fa
                        ? (String)com.yiyiaddon.m.b.a<"s1v62mpx8ihtxm","/6PvCJc5Alafqa1yfdW0hCBJlF9xL+5KEnaAUbn8KWdaiw==",2455167141552705336,-670804684526836779,8324738041663936765,-126137766958168424>()
                        : (String)com.yiyiaddon.m.b.a<"s2r8flx4dy90vn","a29ZwpFXL4lnmXELVMNnc1K1h0c4Q9ZXi9VIR+/ZJu6eWg==",-2815721510859882769,-4466377304655907031,766114716385014220,-4576473621937273017>()
                  )
                  + "",
               var0.a().bt().size() + ""
            }
         );
      }

      private String c(int var1) {
         return this.ai[var1];
      }
   }

   private final class c implements g {
      private static final float eq = 18.0F;
      private static final float er = 6.0F;
      private static final int rA = 3;

      @Override
      public float b() {
         return 36.0F;
      }

      @Override
      public void a(float var1) {
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.e.r.d.a.b.b var8 = b.this.a;
         int var9 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s177f5v3r5rarq","6eyFUWGcydMoJdDEHLDmsKEbppaz9EmMcoBdxUSRs5w=",6386706997674605034,-2634137040528503242,23995636586157808,-1390540361295516031>()) {
            case -1039921739:
               while (var9 < 6) {
                  switch ((int)com.yiyiaddon.m.b.a<"s25jh19wevc49r","ZP0wLvm77nYkDcgfV8XCF2AUV5UR+SpIwINrjujUUBs=",8786394471153930410,-2568299777302216871,-7044451255136541199,-2470043741670891011>()) {
                     case -389263202:
                        int var10 = var9 / 3;
                        int var11 = var9 % 3;
                        float var12 = var2 + 6.0F + Math.max(0.0F, var4 - 12.0F) * var11 / 3.0F;
                        this.a(var1, var8.c(var9), var12, var3 + 18.0F * var10, Math.max(0.0F, var4 - 12.0F) / 3.0F, var5);
                        var9++;
                        switch ((int)com.yiyiaddon.m.b.a<"s1rp949dq6fq9g","wiONxP3LTW5VXFaKinm1FiZn30PHtDbhrbGV02PwE1A=",-3177667264830164261,-5625130318168078119,2949202420835553569,8986063730291631444>()) {
                           case 582665559:
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

      private void a(Canvas var1, String var2, float var3, float var4, float var5, float var6) {
         com.yiyiaddon.l.g.d.a(
            var1, com.yiyiaddon.l.g.d.b(var2, 11.0F, var5), var3, com.yiyiaddon.l.b.d.c(var4 + 9.0F, 11.0F), 11.0F, com.yiyiaddon.l.i.c.a().uT, var6
         );
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5, int var6) {
         return false;
      }

      @Override
      public boolean a(float var1, float var2, float var3, float var4, float var5) {
         return false;
      }
   }

   private enum d {
      OVERVIEW(
         (String)com.yiyiaddon.m.b.a<"s2i5w91wiavhsg","z5Qm2c5x9ybuND5OYIlUxTwPBuEFbX1+wDTZpkPwTCo=",-3224436391603381404,-2219922661766788906,834666728812415124,-7421407272181682897>()
      ),
      BLOCK(
         (String)com.yiyiaddon.m.b.a<"s29vimj1gvvlsc","t9FTz35KPlcqTf0jp1VpQwbhEUfLm+wEVvGI8OMxnVk=",2344943402910116989,3796955892593170185,-989571725052040096,-8220660219455202394>()
      ),
      ENTITY(
         (String)com.yiyiaddon.m.b.a<"s3nr0edtz6wke9","Wx9833veCLmGOFtiu2m4erBA2GUIGegY5YREyELWfII=",2929141863123421475,2708802475852557746,-7067949152470772855,4249740299098144379>()
      );

      private final String Bs;

      d(String var3) {
         this.Bs = var3;
      }

      private String D() {
         return this.Bs;
      }
   }
}
