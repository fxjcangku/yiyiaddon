package com.yiyiaddon.e.c.j.a;

import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.g.j;
import com.yiyiaddon.l.j.m;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

public final class a extends com.yiyiaddon.l.h.f implements com.yiyiaddon.l.c.b {
   private static final String cf = (String)com.yiyiaddon.m.b.a<"sr8n2027jrpck","plGOcS95eRteXCLjWQcm/R8nIUQOwi2FwvDPG9FCYVytdlTbjzz0Mgb36MER4hb0e69URhBneJuPGjEU6X+AMqTXtBrCznAAZz4=",-6036163581294183314,771992428023889740,-6088992031604576192,-8025642903974003820>();
   private static final int bB = 20;
   private static final float L = 6.0F;
   private static final float M = 11.0F;
   private final com.yiyiaddon.e.c.a d;
   private final com.yiyiaddon.e.c.j.a.a.a a = new com.yiyiaddon.e.c.j.a.a.a();
   private com.yiyiaddon.e.c.j.a.a.d a = com.yiyiaddon.e.c.j.a.a.d.OVERVIEW;
   private int E;
   private com.yiyiaddon.e.c.j.a.a.b a = com.yiyiaddon.e.c.j.a.a.b.a();
   private final Set<String> n = new HashSet<>(
      Set.of(
         (String)com.yiyiaddon.m.b.a<"s2enoi1sgjelk9","WEfdn945OE1pXGE9kpU25p4JEpDDENjIJqTP615quQRKPiYrAJpzXYL9Y08k5LkOy/RIe/CK1uERpQ==",-8216409367914813493,4342238639720901941,7288735010692498819,-2077013645919126774>(),
         (String)com.yiyiaddon.m.b.a<"s1qtjpympuv9ug","RYFSn175jJZ+KQ5jrjBEOJInPAq5fZwuse/n890FifIZJotxHD+HYcrp+6Dc8I3AsVhNRudQTxwwZ6UFXxRtxw==",-1827097316664426867,2501230725422073291,-7364216425867572970,-1400471963754205057>(),
         (String)com.yiyiaddon.m.b.a<"s3qrrfsmu3gld","UKaljIHdxARquCK3WvokhkH0y5Huv69BW3qVGTcUG131YrApotWwJd0UfndYvw6gr0RgjntAMJCsAQ==",6220227413404398227,4713004688449057048,2333915474937937086,-1503872875579848781>()
      )
   );

   public a(Screen var1, com.yiyiaddon.e.c.a var2) {
      super(
         (String)com.yiyiaddon.m.b.a<"s37gnvj7yyv901","166hu6t61Wo49vRQ9FoRHLLB0hyM583jasvnAZ137EQgCMQqHmBB6kmb",1300277902173235298,-8966468965212983618,1672211574711164396,-4959377581890189793>(),
         var1
      );
      this.d = var2;
      this.c(var2::v);
      this.d().a(this.a);
      this.C();
   }

   @Override
   protected void init() {
      super.init();
      com.yiyiaddon.d.a.b.a(
         (String)com.yiyiaddon.m.b.a<"sr8n2027jrpck","plGOcS95eRteXCLjWQcm/R8nIUQOwi2FwvDPG9FCYVytdlTbjzz0Mgb36MER4hb0e69URhBneJuPGjEU6X+AMqTXtBrCznAAZz4=",-6036163581294183314,771992428023889740,-6088992031604576192,-8025642903974003820>(),
         com.yiyiaddon.d.a.c.TICK,
         var1 -> this.B()
      );
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2mcle89vfqi78","zxpGoar8Dhhpwe2FxTtFrPY1OibADwiR9GqYzl/fKaY=",-8329129724368950761,-5240327443365877432,-6521057760407227723,6235623108593158000>()) {
            case 960411704:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s30t5m7kn4hzak","qSF0hoiYDsJULf+IMXvEWFvVN92XjExkt6bLJHwcGRI=",286505713518580847,9031266771519519167,1210979809919256512,1037821872415294393>()) {
                  case 391204767:
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
         (String)com.yiyiaddon.m.b.a<"sr8n2027jrpck","plGOcS95eRteXCLjWQcm/R8nIUQOwi2FwvDPG9FCYVytdlTbjzz0Mgb36MER4hb0e69URhBneJuPGjEU6X+AMqTXtBrCznAAZz4=",-6036163581294183314,771992428023889740,-6088992031604576192,-8025642903974003820>()
      );
      super.removed();
   }

   private void B() {
      if (++this.E < 20) {
         switch ((int)com.yiyiaddon.m.b.a<"s9xzy3qldcsaa","M9eHYBBDFfcNWeIS8DjYP7QU5tGoP05dgU2916i89nY=",-6660993581561209410,2353917246647609101,3468305261816807340,-4201059416645954938>()) {
            case -322712141:
               return;
            default:
               throw null;
         }
      } else {
         this.E = 0;
         if (this.minecraft != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s3rjuk62j4krlq","YxMjvc9RwYbK6YXjtCXyiZBLIyBRMTXMULfWQacEQpk=",274819325147072245,852811943750156021,-5648884169425085732,-494325636759180795>()) {
               case 477769462:
                  if (this.minecraft.screen == this) {
                     if (!c(0)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s3o6f6rm5c2mgh","WSJCmDUqcJicaJhzlddzW0ir0YaXwEyLEeHq3pj2ef8=",8352968970421319172,-1122373489642148063,3326062087636858662,-6815463720485455256>()) {
                           case -2126502854:
                              if (!c(1)) {
                                 if (this.a != com.yiyiaddon.e.c.j.a.a.d.OVERVIEW) {
                                    label36:
                                    switch ((int)com.yiyiaddon.m.b.a<"sldc3vzwh4hbm","Hi4vfl7Di1glpNa7W2N/NVU4ZEtCpIcazbVcmYXiEeE=",4389711287465188783,-2784902363165540525,7634469751112778206,-1502916428127229279>()) {
                                       case 1230456165:
                                          if (this.a != com.yiyiaddon.e.c.j.a.a.d.POINTS) {
                                             this.a = com.yiyiaddon.e.c.j.a.a.b.a(this.d);
                                             return;
                                          }

                                          switch ((int)com.yiyiaddon.m.b.a<"s2qm5xdzabvmp3","othoZW2aF8LkYqNPoANXeODY499Nt6jb+4vOOtOleaA=",1561230192080639345,-5647753516158081415,-6093934676528671523,-1087590303332389258>()) {
                                             case 2101998900:
                                                break label36;
                                             default:
                                                throw null;
                                          }
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.C();
                                 return;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"s6eirktd4u8ql","75ZbUuDSO/3WZykUeSCATrpkkxMkhUz6BtUwHQY0Vhw=",5148664642149719917,8775865013257466593,2700040231820144844,-3697990449500698105>()) {
                                 case -2056804313:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s1mriwavr0n8a","YdzXGfAslSrKnnvFm5/p5Ixg94VzY40LL6+cYT4X0Qs=",1466967271377001969,7708647880201832684,6158839407083342422,-5750475302607716583>()) {
                        case 399948392:
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
         switch ((int)com.yiyiaddon.m.b.a<"s105yqlq7t2lai","mBgVjZzQUNq0oEGiDcAGWG2k/9dHx3fivPGkh3zNBIE=",1081056432293665397,-9173721400049989766,2273719741609080099,8297730681649517728>()) {
            case -434438036:
               if (var1.getWindow() != null) {
                  if (GLFW.glfwGetMouseButton(var1.getWindow().handle(), var0) == 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"s10f14vm007o5k","DPrRtDOOUYWFnGhQS5ANLNHuazBPPveud3GVAw/yhqI=",-6834308158309216279,3305549177633783439,1037407847475323135,4612632969099965603>()) {
                        case 186559051:
                           switch ((int)com.yiyiaddon.m.b.a<"s1uqr69m1kmhm6","9Jcp24DmrPsHZHr3XsotyCv7F8DY2W+W3BI9nHieBJQ=",-4174458448562778035,6280939394332779875,-6230661877062131108,3956726185870551397>()) {
                              case -1895286862:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"shlcd5ec9zzb7","ugPTIAjPssPqElgouOaw+r+0Z4WPcTKWaFJKWIjcICQ=",4025292840986461310,-500190557232250576,-7525647397806200791,-7617676077321252773>()) {
                        case -1737817186:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2sf6qf1awf8k9","NyL2iB2B86Tvb605DZTWCDWZHCQLpcpDrXjeAKDn/kU=",4821005132088927703,927167056847971623,-7539555541898231141,6965818971507154457>()) {
                     case 406342863:
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
      this.a = com.yiyiaddon.e.c.j.a.a.b.a(this.d);
      this.a.u();
   }

   private void a(com.yiyiaddon.e.c.j.a.a.d var1) {
      this.a = var1;
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s18xc1sqhhvzy4","dPUIA4UdPs5OceiuNAo/JurO63qK3XP950tzubKvgsM=",8424940185867159668,-50184915386806495,-6911175928439295738,-9134316048327672158>()) {
            case 901906499:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s1qsfhbk1rbqyp","DtHl6WKn091V4UCLTEv2tp0kNTTwai6C14S4cpWmTBc=",-8077237235444950833,-1880566946210029977,6448291946653614055,1157397036543543991>()) {
                  case -2121210418:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"s1pcdpub52ihrg","qADQzsxSbglNibwz5L58hlf6ud0Yjdv2jglmZeQcCDo=",-1195749257889651351,-2783247163337535773,-1690036938755272547,4738778712575623747>()) {
            case -870006654:
               return;
            default:
               throw null;
         }
      }
   }

   public Minecraft a() {
      return this.minecraft;
   }

   public Set<String> k() {
      return this.n;
   }

   @Override
   public void a(String var1, float var2, float var3) {
      j.c(var1, var2, var3);
   }

   private void a(i var1) {
      var1.a(new com.yiyiaddon.l.c.a(this.d));
      var1.a(new com.yiyiaddon.e.c.j.a.a.c());
      this.b(var1);
      label27:
      switch (this.a) {
         case OVERVIEW:
            new com.yiyiaddon.e.c.j.a.d(this, this.d).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"skpyviec8nkap","1UeqcBUlZBR+Ed/psjWP0tUxXP600xkwZIUSszmq/OY=",6820114014851046739,-8915857443851592126,6616159029145236573,-9188731337886845078>()) {
               case 1221271840:
                  break label27;
               default:
                  throw null;
            }
         case SETTINGS:
            new g(this, this.d).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2mwi4arlxcpom","qSFY3tsdP23GY2UjmJsoTVLxD478FCmudD6whPGQrQw=",8250184628252262594,7862565931849391839,-2117849381391858127,-7090814824959054874>()) {
               case 244567915:
                  break label27;
               default:
                  throw null;
            }
         case PER_CROP:
            new e(this, this.d).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s1e3jootklf4m8","NQGmQnHzeJeK7uejTgjMD91uTRPWYCUbfByHd8Jjp+w=",9167550771695889328,-334028474301910516,-5262816548297289571,-1233772935191691913>()) {
               case -951321048:
                  break label27;
               default:
                  throw null;
            }
         case POINTS:
            new f(this, this.d).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s3oyipb19j4jrm","Cnl6sKU6n4C7lz6Q8GldYXwO1YedEK6uWuuVsuPfdUY=",7883798623114887520,-2716600893955249826,-7670351688001077041,8522137726442186534>()) {
               case 492336097:
                  break label27;
               default:
                  throw null;
            }
         case LOGS:
            new com.yiyiaddon.e.c.j.a.c(this, this.d).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"smsrr9bgd6jyx","tDSvuSxWQwtqk7MvlTZy9nsHtsGSsPM6LWDtOBWQhpw=",3932410862790405367,1790814534778096246,-5009524154535770971,344350340389673732>()) {
               case 1406556915:
                  break;
               default:
                  throw null;
            }
      }

      this.c(var1);
   }

   private void b(i var1) {
      ArrayList var2 = new ArrayList();

      for (com.yiyiaddon.e.c.j.a.a.d var6 : com.yiyiaddon.e.c.j.a.a.d.values()) {
         boolean var7 = var6 == this.a;
         com.yiyiaddon.l.j.a var8 = new com.yiyiaddon.l.j.a(var7 ? var6.D() + "" : var6.D() + "", () -> this.a(var6));
         var2.add(
            new com.yiyiaddon.l.c.f.c(
               var8,
               () -> {
                  if (var6 == this.a) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2czmbr8ljvjyf","RbRyCT/OGdpwnCiQlAtA8UhS5G3S2rXJzvF1rhWKDb4=",2390192614481958278,6117367381337627339,1809299323029965817,-997559156169031204>()) {
                        case 1709119152:
                           switch ((int)com.yiyiaddon.m.b.a<"snn74pwjmvqv1","Pabhzyo6FNWFjDCBQyuu9fb9FEIsThxFdtPIualKW9U=",6768636774787133199,7322024409191707342,8122417062758472648,-3444132755801384711>()) {
                              case -1833378364:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var10000 = var6.D() + "";
                     switch ((int)com.yiyiaddon.m.b.a<"s1yzuciwsq7rl1","VJ+wmdF9+holUWAgw8fvlt0ofkzSJZNckpF0QCDshQs=",7933787808443455614,-8352219097936171558,1793213834121465631,-4784533209394823451>()) {
                        case 488576704:
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
                     (String)com.yiyiaddon.m.b.a<"s31d9fm38nq5k2","By7/Yzz5zfV9rM24GeIP7kJUp9WHH3R9BklqrTOsp3M4HWC0",-6899734555709888823,2740714121019815245,5878659981499458720,-8881065060839209671>(),
                     this::C
                  ),
                  (String)com.yiyiaddon.m.b.a<"s2z0dpnltezv9o","kNAHWlChsRRdwgHz4+XQpYu4eKAga3zzar6cOaFFJoLrzZEfml9yKvwd4D9JcOdQK55sYp9etYX5at3UPXtaBURcO+ha+r7L2ycPzoaqy8RFazqWMciV6+lJjudLcbCQV01rMybvGMLgDw==",-545941325355953492,-9075178399683901181,-3164519115258610682,-7843245479606492829>()
               ),
               com.yiyiaddon.l.c.f.a(this, this.d, this::C),
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"s1cf0qr68fhdln","bxtdzZjfYeUx4xvdA+nnYkCq9qFHU5TjmGnrzPi1kLskyuYh",7967086932486275939,-4343718337438503233,-4387748906773617090,-1071759057764422664>(),
                     () -> {
                        if (this.minecraft != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s37q2xp4m6pzgp","QV4owH3Dd/pmbPi0YfKag/nst5JXLVYC3NRGVOJkNXE=",-2295969070877080924,4370256055548096832,4149362247098857868,-2592718085467181655>()) {
                              case 1767065255:
                                 this.minecraft.setScreen(null);
                                 switch ((int)com.yiyiaddon.m.b.a<"s2t0l1pjep27ef","uMmRJ73jBlHJJ7CEdHtJ37aDCIUhnhMaTE0XC6mi98E=",5784767916243469728,468321754561747890,6902265768720592166,6393863191154543411>()) {
                                    case 691309854:
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

   private final class a implements com.yiyiaddon.l.b.g {
      private i a = new i(6.0F);

      private void u() {
         i var1 = new i(6.0F);
         a.this.a(var1);
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
      private final String[] g;

      private b(String[] var1) {
         this.g = var1;
      }

      private static com.yiyiaddon.e.c.j.a.a.b a() {
         return new com.yiyiaddon.e.c.j.a.a.b(
            new String[]{
               (String)com.yiyiaddon.m.b.a<"s1436zxctmyqa5","ulQRtbH3qo3CIDTxs8TcbpQ05OkIN/wEDdXdNw==",665024191210046524,1512360792832260426,-5147621781218609931,-2127683392170917005>(),
               (String)com.yiyiaddon.m.b.a<"s1436zxctmyqa5","ulQRtbH3qo3CIDTxs8TcbpQ05OkIN/wEDdXdNw==",665024191210046524,1512360792832260426,-5147621781218609931,-2127683392170917005>(),
               (String)com.yiyiaddon.m.b.a<"s1436zxctmyqa5","ulQRtbH3qo3CIDTxs8TcbpQ05OkIN/wEDdXdNw==",665024191210046524,1512360792832260426,-5147621781218609931,-2127683392170917005>(),
               (String)com.yiyiaddon.m.b.a<"s1436zxctmyqa5","ulQRtbH3qo3CIDTxs8TcbpQ05OkIN/wEDdXdNw==",665024191210046524,1512360792832260426,-5147621781218609931,-2127683392170917005>(),
               (String)com.yiyiaddon.m.b.a<"s1436zxctmyqa5","ulQRtbH3qo3CIDTxs8TcbpQ05OkIN/wEDdXdNw==",665024191210046524,1512360792832260426,-5147621781218609931,-2127683392170917005>(),
               (String)com.yiyiaddon.m.b.a<"s1436zxctmyqa5","ulQRtbH3qo3CIDTxs8TcbpQ05OkIN/wEDdXdNw==",665024191210046524,1512360792832260426,-5147621781218609931,-2127683392170917005>(),
               (String)com.yiyiaddon.m.b.a<"s1436zxctmyqa5","ulQRtbH3qo3CIDTxs8TcbpQ05OkIN/wEDdXdNw==",665024191210046524,1512360792832260426,-5147621781218609931,-2127683392170917005>(),
               (String)com.yiyiaddon.m.b.a<"s1436zxctmyqa5","ulQRtbH3qo3CIDTxs8TcbpQ05OkIN/wEDdXdNw==",665024191210046524,1512360792832260426,-5147621781218609931,-2127683392170917005>()
            }
         );
      }

      private static com.yiyiaddon.e.c.j.a.a.b a(com.yiyiaddon.e.c.a var0) {
         if (var0 == null) {
            return a();
         }

         com.yiyiaddon.e.c.b.a var1 = var0.a();
         List var2 = var0.f();
         return new com.yiyiaddon.e.c.j.a.a.b(
            new String[]{
               (
                     var0.g()
                        ? (String)com.yiyiaddon.m.b.a<"s156lj17jrlp3m","khdvVZhOzhB/tI91rxNv2NecdQtR1f3/p1Zkg7DiOQeWgmSLkAU=",-3714079795688858437,-385620969587262168,2001022404942543487,-3431150825241472654>()
                        : (String)com.yiyiaddon.m.b.a<"s34helunbsjprw","Usrl+BQcR/ycWVHVsb4m1QZqdwmRzQ/l0YuvftIeYENJhovviwY=",-321569368808756597,1358531098976614307,6428351421223656769,5522101521606152684>()
                  )
                  + "",
               var0.a().a().af() + "",
               (
                     var1.B
                        ? (String)com.yiyiaddon.m.b.a<"s21yklpqdolk3u","cnb+m0q8j+UGK1rFXRSd29ewj/fEg/KRDXeiGghm3NLMyQ==",-3214718678003115582,9216468375659588398,-4353423535572896881,3583310188048766900>()
                        : (String)com.yiyiaddon.m.b.a<"s2h0aiq26ltsqo","HeluPGJd5rSU9DyWAHXyVt8g9H4MhiEUF2Ht3fp+obaWwg==",-4069532881119238450,-6654916806113224824,-2547765053851146820,-4089992360921729076>()
                  )
                  + "",
               var1.a + "",
               var1.a + "",
               a(var0) + "",
               a(var0) + "",
               var2.isEmpty()
                  ? (String)com.yiyiaddon.m.b.a<"s25bjmg1fov9f9","vlHZDfZFwhCp54u7VbJbvViiPs4Qt/RojTz+OC7KV91kwH5sshnwPA==",-7870650499392532631,9010000234859145310,-5049073521087364238,-5921687048869374020>()
                  : var2.size() + ""
            }
         );
      }

      private static String a(com.yiyiaddon.e.c.a var0) {
         Set var1 = var0.g();
         if (var1.isEmpty()) {
            switch ((int)com.yiyiaddon.m.b.a<"s3a2ws8428yhf9","hPuxAH/8bS7dKT1Uyi+65puRJOeVZ++m4E11Ostuk2g=",9198892554872041296,8024278188443966908,7422693949417703493,-6852205533478365865>()) {
               case -253773600:
                  return (String)com.yiyiaddon.m.b.a<"s3tf6ievia5bjm","kJJr70Um7mObT3CcsXCuzXh2LttKPn0+9RcSS6fE58kc1B9wSkg=",4564355700925292817,3362034703204479371,-5152417570165512812,-1484025608143877391>();
               default:
                  throw null;
            }
         } else {
            StringBuilder var2 = new StringBuilder();
            int var3 = 0;
            Iterator var4 = var1.iterator();
            switch ((int)com.yiyiaddon.m.b.a<"s218bwsio70t3f","4RKDdYJ44kcoxvfyNuBIgZkKnuYDekjaHWUVCGKxBVI=",-2281761558300632303,2771394472407980656,-2357107830306592554,-6695072817223299642>()) {
               case 716577900:
                  while (var4.hasNext()) {
                     switch ((int)com.yiyiaddon.m.b.a<"sgx3utuddvrvo","eu4f2FOFYTtXIVeubxRCqXix+mBKl4RQuBlfK9cxpxA=",8928722903598885633,2010528697419246303,-3173878803998655538,7744031849023365050>()) {
                        case -2115194197:
                           com.yiyiaddon.e.c.d.a var5 = (com.yiyiaddon.e.c.d.a)var4.next();
                           if (var3++ > 0) {
                              label28:
                              switch ((int)com.yiyiaddon.m.b.a<"s1rrul373icsma","38dF9fvwhOJuVZBF5wv864Cprcv8Rbc+CRQuOYakBhA=",-1287571846306524037,-49607581694124583,-5466319305837382618,6680741610686610310>()) {
                                 case -1616971186:
                                    var2.append(
                                       (String)com.yiyiaddon.m.b.a<"s2m0my5wxq4scx","bsBVtm0Ki83/kwNfN85ekFjaL4TdWNnffIHRWyoA",-5502584920817875616,8313692854592818719,780365661616971397,6229668125788670816>()
                                    );
                                    switch ((int)com.yiyiaddon.m.b.a<"s1uj6nommzdps7","TSy4ZMe0KhFDtjSN5XxFetI6EjBiC9rd2aBWZa5j0+A=",-686859181696598577,-5996962094466824475,-4957347848749880704,-6322987436314387377>()) {
                                       case 1337075948:
                                          break label28;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           var2.append(var5.m());
                           switch ((int)com.yiyiaddon.m.b.a<"s286lnhhy2hcws","RCJuSbXW/efvX4vTi8vf/m4KHIoHKF2KwZiZsN2tTT4=",-9195317132842737711,-2717451788015343183,-5904799212652232853,-4820990474919717976>()) {
                              case 980541637:
                                 continue;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  }

                  return var2.toString();
               default:
                  throw null;
            }
         }
      }

      private static int a(com.yiyiaddon.e.c.a var0) {
         int var1 = 0;
         com.yiyiaddon.e.c.d.g[] var2 = com.yiyiaddon.e.c.d.g.values();
         int var3 = var2.length;
         int var4 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s3dfgk5tky43av","pzIQwL+nzo7RoorpaTsUEXfcK7IQ4QiTTJr7LzyI9wE=",273585755406187183,-8583975517867817750,-5657372858671158237,2892619252457204779>()) {
            case -785796118:
               while (var4 < var3) {
                  switch ((int)com.yiyiaddon.m.b.a<"s27mc547iyejuv","Khy/+k2pm5RqYtmPRw8by8Emi0bhFpxAe7hlvcDQmPQ=",-6698559157723357587,-7653748767576106185,-613338579563174729,5885835338003916667>()) {
                     case 226760677:
                        com.yiyiaddon.e.c.d.g var5 = var2[var4];
                        if (var0.a(var5) != null) {
                           label23:
                           switch ((int)com.yiyiaddon.m.b.a<"s14ew95u3ii6hh","MEZ08c6wPPZHnNUAgc1eCSBBHJviv3b3uBaTIGf4XTM=",-6300833777029347435,8196310547420598110,-3226688853616970529,-5816501873627162161>()) {
                              case -519375926:
                                 var1++;
                                 switch ((int)com.yiyiaddon.m.b.a<"s6uer7qvgcsab","MYvW2KiDQUnZYjEABrJELMPgfVQ5vXYtbEaqY9ACG5Q=",-7294630027199706052,-2837115889129391572,-4937977757656544447,9041041325717501485>()) {
                                    case -463309301:
                                       break label23;
                                    default:
                                       throw null;
                                 }
                              default:
                                 throw null;
                           }
                        }

                        var4++;
                        switch ((int)com.yiyiaddon.m.b.a<"s230pvbo51eci4","3YJ7B5TS+V2pm/6coqq0CwkDOgJmS2LQxMWJ6PG/7cI=",3062244776364794640,1854519441107165749,6917691961433271624,6224977046038323890>()) {
                           case 262003758:
                              continue;
                           default:
                              throw null;
                        }
                     default:
                        throw null;
                  }
               }

               return var1;
            default:
               throw null;
         }
      }

      private String c(int var1) {
         return this.g[var1];
      }
   }

   private final class c implements com.yiyiaddon.l.b.g {
      private static final float N = 18.0F;
      private static final float O = 6.0F;
      private static final int bC = 4;
      private static final float P = 8.0F;

      @Override
      public float b() {
         return 36.0F;
      }

      @Override
      public void a(float var1) {
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.e.c.j.a.a.b var8 = a.this.a;
         float var9 = Math.max(0.0F, var4 - 12.0F);
         float var10 = Math.max(0.0F, var9 / 4.0F - 8.0F);
         int var11 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s2k9x5mk8zuiaw","BSOimB9dAk42RFuZD2QZRPXgqpVW+iRYcrNrT8Zi16s=",2159904795305431908,4861643209631645981,-2411879165081803180,1902849841728181235>()) {
            case -1046629604:
               while (var11 < 8) {
                  switch ((int)com.yiyiaddon.m.b.a<"syaluyoqo7u8v","4lSQnFminI5/jV/tGjTFqUnsTSsB8O7IAe6cgvcqW6s=",627860624343249957,3258522339978465722,-5284860031364037969,-1315070059460055221>()) {
                     case 142198885:
                        int var12 = var11 / 4;
                        int var13 = var11 % 4;
                        float var14 = var2 + 6.0F + var9 * var13 / 4.0F;
                        this.a(var1, var8.c(var11), var14, var3 + 18.0F * var12, var10, var5);
                        var11++;
                        switch ((int)com.yiyiaddon.m.b.a<"s38wyoawknplxu","c63dCs1pH/WMFiQJ11IoBUsMUdjEoGCcR+fGR1Sms5M=",-7252966255311763099,-5839162826308653552,308454219411345898,-1042265556660489290>()) {
                           case 1504684949:
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
         (String)com.yiyiaddon.m.b.a<"s156arhmcmrtfs","5zke1PcSnGvTlgzpRdA3DY6zx29DcqdQmusRG5O76yM=",-1360381136077542943,3513124599783396358,1973170621332320452,-5129015202257840132>()
      ),
      SETTINGS(
         (String)com.yiyiaddon.m.b.a<"svn1i6yha27ct","WJPHnIRdv0WKQ/hFnJZyMEHCzGSeoma/dQ7lpziMjGY=",-2725851275686131750,622921620207440073,4043151029869758476,-1052832620958300275>()
      ),
      PER_CROP(
         (String)com.yiyiaddon.m.b.a<"s14ubkt5h3ze6w","hTn7Vabz/X2F3Ducvxi3Xs1lSpMH9DK3kRgVfRYyqKj92Q==",7888104042834518615,1137073488804183215,6044243727688359512,-2586984747700424051>()
      ),
      POINTS(
         (String)com.yiyiaddon.m.b.a<"s3r719n4r6gp1c","hnwWmHD4+zo5+7Qa5Kpm0zu0sOcHHu0dG6Gv4cnDgbU=",-4822879531324043085,2580576288134398646,-4152899055787794129,-8116193939124868652>()
      ),
      LOGS(
         (String)com.yiyiaddon.m.b.a<"sae8dazevebfz","KAobg2F/a0lVioUMWJyr9v2O8E/9ZfZFmhDQDJaLt+I=",8909700730879317754,193703421873116012,-2545376641845223979,1165797167265627921>()
      );

      private final String cg;

      d(String var3) {
         this.cg = var3;
      }

      private String D() {
         return this.cg;
      }
   }
}
