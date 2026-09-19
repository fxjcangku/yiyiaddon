package com.yiyiaddon.e.e.e;

import com.yiyiaddon.l.b.g;
import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.b.j;
import com.yiyiaddon.l.h.f;
import io.github.humbleui.skija.Canvas;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.lwjgl.glfw.GLFW;

public final class a extends f implements com.yiyiaddon.l.c.b {
   private static final String hl = (String)com.yiyiaddon.m.b.a<"s3q3qhxc3d7epd","4ePZC7cYxs/P5b1qZT0W1QekS8OdfAnZ20NVaxbjymqfieVcEaFNhBMW5hfSMOKCu34YiF67X+ZC5UcCKW7nVa7UZWijukg+j88=",-5696847968311908183,6355369371640542632,-6344809211501119185,-2594817713699843289>();
   private static final int dk = 20;
   private static final float ad = 6.0F;
   private static final float ae = 11.0F;
   private static final float af = 10.0F;
   private static final float ag = 12.0F;
   private static final float ah = 6.0F;
   private static final float ai = 6.0F;
   private static final float aj = 320.0F;
   private final com.yiyiaddon.e.e.a b;
   private final Set<String> q = new HashSet<>();
   private final com.yiyiaddon.e.e.e.a.a a = new com.yiyiaddon.e.e.e.a.a();
   private com.yiyiaddon.e.e.e.a.d a = com.yiyiaddon.e.e.e.a.d.OVERVIEW;
   private int E;
   private com.yiyiaddon.e.e.e.a.b a = com.yiyiaddon.e.e.e.a.b.a();

   private static int a(com.yiyiaddon.l.i.c var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s2n37vkfdxfiqz","PmGJGlnMAShggNvSMoOeNmqc0S+kKPXx4KjiFEFu1dA=",3144471935450971207,5858905374694958537,4604025324132749742,7503263554336594609>()) {
            case 538490653:
               if (!var0.gB) {
                  switch ((int)com.yiyiaddon.m.b.a<"s24zvo9woguux3","0RftVla12aiTrYzlNgI+CHZvx8TkREHPdzSj7M8Rtq8=",3632588521296201539,241507879682975192,8636751618348900496,7024553836962574357>()) {
                     case -1188213277:
                        int var10000 = var0.uT;
                        switch ((int)com.yiyiaddon.m.b.a<"s1m6p89enbtzsh","VPZ33WivjPOPHxL4OCwC38s31wMD3z8ztH/Sgfgl3hc=",4631901497718982589,-510742332306908491,-916844513124750076,7714287405573295569>()) {
                           case 133114353:
                              return var10000;
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

      switch ((int)com.yiyiaddon.m.b.a<"shrlct47ntswl","/kkH3lgOA8xb0QT4mxK3KIgnrOrM6XG+xjY3d92VZ08=",1241834655587068038,-8646262177237205445,7440140490483074618,-6478132459928715638>()) {
         case -2079443581:
            return 16777215;
         default:
            throw null;
      }
   }

   public a(Screen var1, com.yiyiaddon.e.e.a var2) {
      super(
         (String)com.yiyiaddon.m.b.a<"s2nh8nig0li27h","LhfclUGJXCOLQWRkKpgYZyH6b34lz4GsJm7FjQKt8Db0yzW2xlT8OOxX",9050617199412600909,-4012451101187405004,5457414602144699767,-4145873196740509408>(),
         var1
      );
      this.b = var2;
      this.c(var2::v);
      this.d().a(this.a);
      this.C();
   }

   public Set<String> k() {
      return this.q;
   }

   @Override
   protected void init() {
      super.init();
      com.yiyiaddon.d.a.b.a(
         (String)com.yiyiaddon.m.b.a<"s3q3qhxc3d7epd","4ePZC7cYxs/P5b1qZT0W1QekS8OdfAnZ20NVaxbjymqfieVcEaFNhBMW5hfSMOKCu34YiF67X+ZC5UcCKW7nVa7UZWijukg+j88=",-5696847968311908183,6355369371640542632,-6344809211501119185,-2594817713699843289>(),
         com.yiyiaddon.d.a.c.TICK,
         var1 -> this.B()
      );
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s329zgznw5ckpm","Q1IUfenzRBqq6cDAyExAYqTyDGnvoco4ZpK7hwuWrss=",-6369797856942888163,-8875912938653046969,6541929092784912797,-9122203419414081292>()) {
            case -1796880404:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s2zfhjs16s8yl3","EbjTFdAuWzoAyiIZvs1KMsz8N46zJpNVO6JFfZgo6aw=",-4730337941502383559,-534027669393152101,-8403147615834796058,4355389375873110722>()) {
                  case -1319800896:
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
         (String)com.yiyiaddon.m.b.a<"s3q3qhxc3d7epd","4ePZC7cYxs/P5b1qZT0W1QekS8OdfAnZ20NVaxbjymqfieVcEaFNhBMW5hfSMOKCu34YiF67X+ZC5UcCKW7nVa7UZWijukg+j88=",-5696847968311908183,6355369371640542632,-6344809211501119185,-2594817713699843289>()
      );
      super.removed();
   }

   private void B() {
      if (++this.E < 20) {
         switch ((int)com.yiyiaddon.m.b.a<"s2i3npont42z2e","8AKAOHL0bKVl8+QscD3UeMrT890SU8QRCtqFBM4TP7k=",4540033125728765684,6884272361844375282,-4355772332975480459,7223021900040757651>()) {
            case 732748603:
               return;
            default:
               throw null;
         }
      } else {
         this.E = 0;
         if (this.minecraft != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s8h8u4kl4p3yw","FfPtrhqx8tVg5oiP3idAySORZX9NuKPMhGTHc1/5TNU=",-2979263151050815118,2062599473124156917,818036827159903580,112775717475130849>()) {
               case -770910242:
                  if (this.minecraft.screen == this) {
                     if (!c(0)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s1snd9w13gevfb","xO/RaKhBv/jC8fEoQCE57mJOZusPzVN6ofxCOEMXZto=",2104428906899082492,3119177011810791209,4121305557390635540,7165694568068761000>()) {
                           case -587084093:
                              if (!c(1)) {
                                 if (this.a == com.yiyiaddon.e.e.e.a.d.OVERVIEW) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s2syyp00shohzm","G5r7UhEgMH+Q5PJdwDYeoLx3p75Lo1hoZfqyB2pJHxI=",-8631627496068512675,-8410512374244661439,-4119346728016930014,1222852087175919729>()) {
                                       case 1129020231:
                                          this.C();
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.a = com.yiyiaddon.e.e.e.a.b.a(this.b);
                                 return;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"swomoiwwum18j","fxyZ9JiCCsIoyMAxmY/PYIgusk6uaMUaBvzPq/Y7CYw=",6480253482585810920,-1972130634439411693,3260230332522294576,-2464389536503889475>()) {
                                 case 895776573:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s32k0o1t47bzso","TqY5Gxve1O1RggjhkA4IxskG0Hua2opvlm/nVYTRH3Q=",-4232781284492170307,5370518513958215876,8389121082383191689,1429474614354710531>()) {
                        case 1038702797:
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
         switch ((int)com.yiyiaddon.m.b.a<"s2ctv4s9kuvgjv","O6fEecK5LSY9EVJsc2MNGeaMAYxmYyfyR0bXXldX26c=",7683581328824324760,-8398337214085441427,3564677994609840433,3984006795345937491>()) {
            case 261181:
               if (var1.getWindow() != null) {
                  if (GLFW.glfwGetMouseButton(var1.getWindow().handle(), var0) == 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"s1x35o6dyf0rxp","/fJqk1537Ev2PQwijxc9jXCq7+sTVyYSwfju5cgGI+o=",-8027552489105712450,6870485614682363034,2391921848659459014,-4205846420364849117>()) {
                        case 260303258:
                           switch ((int)com.yiyiaddon.m.b.a<"s3ka10kay3358o","Bzt7HP0vXzrDZOEKqK1w2QcjgpnO8QIAj20ukHLG2nY=",-7053584508942209969,789562995793643965,4646674485782462631,6279774686254293872>()) {
                              case 199343291:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s2h6oegyaonwig","wjcDNvXYmfRv1724E6Y/XBMXiMicByNYdSuDPg0sB5A=",4908685781712947951,5127743085087992209,580720635689741919,-1616000433357293702>()) {
                        case -1625556388:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s3tlk836f6hdn1","aH2IKz4DRD7SjTsNPN1gqi1plwQ4SgiR7Q4IVdIjn6g=",2577002630430702762,5081416138739216743,8632232751497417690,22240231602510294>()) {
                     case -1769934300:
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
      this.a = com.yiyiaddon.e.e.e.a.b.a(this.b);
      this.a.u();
   }

   private void a(com.yiyiaddon.e.e.e.a.d var1) {
      this.a = var1;
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1h6455s4pfhgo","e424RqKZG2pZ1yEWRhl3RTCAPltzsLyhsVj46FWu3Xg=",-4599952149805020048,309822326457506978,-8959638019342577454,-8618967711234515487>()) {
            case -115985241:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s2s1wjmbexsjsz","vnJ6JxooqEFydxWXiy/sqEDSNdi5lZ62fhf6j4a7qw4=",-467349307294763300,1410186915606897895,-5148789870344020674,-4673076415110803716>()) {
                  case -788926568:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"s366oclawhxj7z","bFWAayq8lcTwVHA97Js9GyU9zuuIyOlftBo1yI/1soo=",-3412252531532200456,-7171282487540813095,3201011466394635782,-6812955367927729302>()) {
            case -407842950:
               return;
            default:
               throw null;
         }
      }
   }

   @Override
   public void a(String var1, float var2, float var3) {
      this.a.a(var1, var2, var3);
   }

   private void a(i var1) {
      var1.a(new com.yiyiaddon.l.c.a(this.b));
      var1.a(new com.yiyiaddon.e.e.e.a.c());
      this.b(var1);
      label27:
      switch (this.a) {
         case OVERVIEW:
            new com.yiyiaddon.e.e.e.a.a(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s1alx1s1oy2u2m","m756RkuK2dVEuHaj7vk/LfK67bjHvlT1F0ROciLbnzU=",8642763609611543077,-7273274597686478369,7729186903988297942,1718723826990028964>()) {
               case 637058599:
                  break label27;
               default:
                  throw null;
            }
         case BASIC:
            new com.yiyiaddon.e.e.e.a.b(this, this.b).o(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2q8mtfgefyu98","Q2FYtvDJ03zEgfQrGYPWyedqsSQzC0X3jX+95aVwoLo=",-5471823633159629340,-9047784612232577137,5850394341052678647,-8552079637574451379>()) {
               case -1253058366:
                  break label27;
               default:
                  throw null;
            }
         case TARGETS:
            new com.yiyiaddon.e.e.e.a.b(this, this.b).p(var1);
            switch ((int)com.yiyiaddon.m.b.a<"so27znb2h0ocx","F1OetSll5vjK3WVl8VLpBH+TyMcEL+CHxJu8eBPyBXg=",9666477269332138,309536317439579486,3125524445490676453,-8603714465434792707>()) {
               case -1085753107:
                  break label27;
               default:
                  throw null;
            }
         case BYPASS:
            new com.yiyiaddon.e.e.e.a.b(this, this.b).q(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s3obxu41r66q15","w8NAvQ0Jp/8GuRo8F0vqK27iSgoQkVwTDZLRLUuyEn0=",198176905626105101,-4980127514644060505,-5112339040509929798,3319319699293005892>()) {
               case -1182788043:
                  break label27;
               default:
                  throw null;
            }
         case ESP:
            new com.yiyiaddon.e.e.e.a.b(this, this.b).r(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s11qquwfulww9","VQEyEeHTX+yu+vOTRg7k5k6YoaWJGDWHe53ef6r4xgY=",3271060297609921440,-6063165393804399697,-5202781937639924836,-2104560136178873981>()) {
               case 489394368:
                  break;
               default:
                  throw null;
            }
      }

      this.c(var1);
   }

   private void b(i var1) {
      ArrayList var2 = new ArrayList();

      for (com.yiyiaddon.e.e.e.a.d var6 : com.yiyiaddon.e.e.e.a.d.values()) {
         boolean var7 = var6 == this.a;
         com.yiyiaddon.l.j.a var8 = new com.yiyiaddon.l.j.a(var7 ? var6.D() + "" : var6.D() + "", () -> this.a(var6));
         var2.add(
            new com.yiyiaddon.l.c.f.c(
               var8,
               () -> {
                  if (var6 == this.a) {
                     switch ((int)com.yiyiaddon.m.b.a<"s2t5dtae4rf1ma","XnQHpUBgtZllhbrlbVkff6UcBhVvrL32iTZJuBVsm3U=",8709935352542635009,-3110636019793377596,-5733759341947291770,-5988972619263723455>()) {
                        case 1518786355:
                           switch ((int)com.yiyiaddon.m.b.a<"s2oo8l2p9u5ljf","kYHEw7rgT7XM9nZUdMUwt8NM3elMnhZD8WoIo8Mb1Vg=",7880032412124330304,2410996922511202522,-984011426477385810,6347567452104211154>()) {
                              case 880251053:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var10000 = var6.D() + "";
                     switch ((int)com.yiyiaddon.m.b.a<"s3kgy2qyrk55hh","E/YbTUFbBYXU4xYIV3ECl4CVt0ljHPRfK5flQpHXnrA=",5436099652199675981,8316144206658316008,2298357796946757812,8301282962050605362>()) {
                        case -1620253029:
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
                     (String)com.yiyiaddon.m.b.a<"s2upogtyf50iuk","G7lmybsLeDTidORp3aKz/v/y9J+zXZNEItdrN6GaYS7IfDLo",6195655099840038432,5177724539316937429,-4349270032892707095,-1173596085010091855>(),
                     this::C
                  ),
                  (String)com.yiyiaddon.m.b.a<"s1hbwykiu6gtni","7pF/YToQ5zdtb2zUrRuxArDQt6cLtOhhskgfRlueNOrKJUXRWoaPH6TOxcPuNtWUECNhn5mODWH2lz90ITZDcQM8r41Wg5lg/YUeZq2UIO7UHEshi8mIAx7oB04g5G07RN0DrA==",7001441951859679434,4565510436522312144,-1385410308901258139,3052971439722465989>()
               ),
               com.yiyiaddon.l.c.f.a(this, this.b, this::C),
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"scgwexdkf66gg","FszeEys3r1B39AJHfvAcgESvjRBHAB8seAYUbWoTW08ZXf1Q",-3110960050548320923,-5230464062089522447,-7837828090994733262,1344232768983720757>(),
                     () -> {
                        if (this.minecraft != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s2gv7bb4d50rt1","hKMGhk0oeQ3iQs9HTOodLrB0j90wzNOpEpobL9Wl6cI=",-5359070730390439770,7325493395229070841,4481355621451144092,2556331054518192191>()) {
                              case 468238266:
                                 this.minecraft.setScreen(null);
                                 switch ((int)com.yiyiaddon.m.b.a<"s3jansyyqer7rs","g1EHisfsD/9TJcFZm/+3Ll4hQftLLanf+l9f/fBQibw=",-5509721608074766696,8195502729078283531,-5634816422467981801,-8537052235346463535>()) {
                                    case -1223324868:
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

   private static List<String> a(String var0, float var1) {
      ArrayList var2 = new ArrayList();
      String[] var3 = var0.split(
         (String)com.yiyiaddon.m.b.a<"s681ojtvstdg0","7ocAYoDTKcNuBAS/L2pUGeR7oGHKWA/bPWR8KK7h",4702871417576969384,-6846473334399357757,3130056769503665444,-6620212833993912288>(),
         -1
      );
      int var4 = var3.length;
      int var5 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"sw7ee9al0cfyv","glSwYUw18vkWT+XOk9q1NhJ1sXsHawYktPEPedxHauA=",6711240936418330385,1776563028347810381,553597929922042879,-3643269630626175568>()) {
         case 726580054:
            while (var5 < var4) {
               switch ((int)com.yiyiaddon.m.b.a<"s3v9pbqjphf2ao","9RA6mMdZclu2h6z8Z6uQT46w1946vEAl6L4b+zXbLbo=",-3500191076743631714,7209271812633018445,5563656547626521999,6928155883234213092>()) {
                  case 1722278575:
                     String var6 = var3[var5];
                     if (var6.isEmpty()) {
                        label68:
                        switch ((int)com.yiyiaddon.m.b.a<"sywd0b8ar8tj8","sW0Et/s96ElY540koagGEEEzLeUrjcOBAZMZ8wW5zFM=",-8444268253533493577,4827415998309231764,-5330428678293077834,-3150192701281323182>()) {
                           case 1316030800:
                              var2.add(
                                 (String)com.yiyiaddon.m.b.a<"soaxt9djclyk","hyWIFDNUis5qzFXXFSyFVUpHDJOovO8IL0FU6g==",8518088094116019535,-3145823982943571708,-475053324378579127,3309872064813326950>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s3oy2hh8kjc2t","bNQD12s3o7FoyYDkTXHOmrT4PS5SpSqjwyHcdwd2n+A=",6712907346516512747,4164669355721456696,-6958649434864805528,3932223844583828081>()) {
                                 case -1386257894:
                                    break label68;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     } else {
                        StringBuilder var7 = new StringBuilder();
                        float var8 = 0.0F;
                        int var9 = 0;
                        label46:
                        switch ((int)com.yiyiaddon.m.b.a<"s2r8cy6yx71yuc","shD1K6nu1+ix6fq8NRC8IerUkduwyKdZL2KckOc6O5M=",4191810829502343545,3803787941996670207,7509781957484012610,2643165891182689078>()) {
                           case -16359422:
                              while (var9 < var6.length()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s3jxes20be1smi","Y6YxdyIQOCdBNM3xSd4lwxiO0c0kLuZU4s416Sgi8mE=",-5871615331123860801,-7637067347014770828,-8467940751555641153,-408197535159543420>()) {
                                    case 311246429:
                                       int var10 = var6.codePointAt(var9);
                                       int var11 = Character.charCount(var10);
                                       String var12 = var6.substring(var9, var9 + var11);
                                       var9 += var11;
                                       if (var10 == 167) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s3g9mz75cfgbyp","m0eqEfxeBp8SpdVzgjsy8zG/K1/zfO8LSNDI0GPsCsQ=",-1851543499787689168,1642309841836428386,-5035084993606231545,6260183613861682901>()) {
                                             case 186155828:
                                                if (var9 < var6.length()) {
                                                   label64:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s1tbx7czuy1e5q","3j1+04XQaGgKC3UMkXKI1GadQsaRXwbgiE/hUgChcXY=",7614342059690854486,-4558751632439552887,6974477479351898692,8796283819683376652>()) {
                                                      case 424402374:
                                                         var12 = var12 + var6.charAt(var9);
                                                         var9++;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s3j5vhbknidcrq","rn35FQXS5KNdjceTqCUhaqp5zxl1HIlwqikjMHdSyzo=",3726665464056452562,-1192819823088356794,-6465650723571397572,-4622502249381252324>()) {
                                                            case 1927686163:
                                                               break label64;
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

                                       float var13 = com.yiyiaddon.l.g.d.a(var12, 10.0F, false);
                                       if (var8 + var13 > var1) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s2rhosx9i01xmr","huO6UIGlmlq0Un14D9PmxCBChOfyTpQ3a/kCFn2+X58=",-8689536068433540527,8503172393928750583,1879033634055166010,-2644430217226892389>()) {
                                             case 29078116:
                                                if (var7.length() > 0) {
                                                   label58:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2re4yrbm2w4ig","ljUMpsF9UV4t2PyDR2mJmuFTV+rSI+iTpzMgIFvFZWU=",-5162964831426554737,388989932108336830,-9059771945098006884,7330726194953946451>()) {
                                                      case 35508055:
                                                         var2.add(var7.toString());
                                                         var7.setLength(0);
                                                         var8 = 0.0F;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s37au4qcc8av7o","awPojw99otCSB7DTseY2gm0o62OlncXWX6DHLxJf8m4=",-1014030357044307523,281669627453529938,-2497387035570583045,-7307947410620013197>()) {
                                                            case -27774707:
                                                               break label58;
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

                                       var7.append(var12);
                                       var8 += var13;
                                       switch ((int)com.yiyiaddon.m.b.a<"se1k21nf9ownr","rnYk8Aoedgs1YgoDsU/H9A9RXyvCLJ+755MPEWuPiFM=",550173200336983436,3599148135875190833,5264306871530633403,-6754332979126675165>()) {
                                          case 289256106:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var2.add(var7.toString());
                              switch ((int)com.yiyiaddon.m.b.a<"s2b6tqxdc5fvl4","+faMpT3nV0+CYwBIrx/j3VBX7XT8WcPkGYmSplDuKt0=",-4550927526087472685,7601694288208884523,-2489252313434237968,3100567030513679499>()) {
                                 case -318670513:
                                    break label46;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var5++;
                     switch ((int)com.yiyiaddon.m.b.a<"s9x3db82om3am","2Tl8+bY+1jAcPG2WmDntrq7rgLB9exfASrd+pdINQIg=",3630632705510718200,-4078435853435421415,-1594464040787532687,2978956384802682087>()) {
                        case -215578745:
                           continue;
                        default:
                           throw null;
                     }
                  default:
                     throw null;
               }
            }

            return var2;
         default:
            throw null;
      }
   }

   private final class a implements g {
      private i a = new i(6.0F);
      private String aW;
      private float m;
      private float n;

      private void u() {
         this.aW = null;
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
         this.a(var1, var2, var4, var5);
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

      private void a(String var1, float var2, float var3) {
         if (var1 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s1a1pbqmpbp2ot","3fBxUfSmyJsNSo4SSYhuJR/mSaLpGrT8OBAPZDGXev0=",-5228550880790467626,-5907430184626615489,-1248496126315029221,6397075788134599443>()) {
               case -201669109:
                  if (!var1.isEmpty()) {
                     this.aW = var1;
                     this.m = var2;
                     this.n = var3;
                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s8zmu9fex7q65","zrvRunHT7pgjDDW8bUgkggjskoEELcIwVtuOfWRcxgw=",8737423812365448091,6304116963403427500,-8265334101717252727,-1750506928239543446>()) {
                        case -930120062:
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

      private void a(Canvas var1, float var2, float var3, float var4) {
         String var5 = this.aW;
         this.aW = null;
         if (var5 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"sxp16f6fkevab","+iODSLMrBfojfto9//hGIA0GerKGd7D1m/YmXXqr/JQ=",-4579640085879639683,8113227028457278982,6255630982935260736,3952755892364320458>()) {
               case -810004225:
                  if (!var5.isEmpty()) {
                     com.yiyiaddon.l.i.c var6 = com.yiyiaddon.l.i.c.a();
                     List var7 = com.yiyiaddon.e.e.e.a.a(var5, 320.0F);
                     float var8 = 0.0F;
                     Iterator var9 = var7.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"s1za7pms6hzl34","3ZDYQgpBsjtHVeMfJ0x70P1FCqyLJHvGXBHUKKW34GM=",9157097596896543357,-3140078261416927968,8837960475597053693,-4842758655523945816>()) {
                        case -1438389832:
                           while (var9.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3o8qey71d3x95","2POCpnZJUZkwAw+cCmHpjg1Y8Y148CexASmN3kmHHbo=",-1115404782391296633,-2998247656496301848,1730295051389130531,9136951004623613024>()) {
                                 case 1423018116:
                                    String var10 = (String)var9.next();
                                    var8 = Math.max(var8, com.yiyiaddon.l.g.d.a(var10, 10.0F, false));
                                    switch ((int)com.yiyiaddon.m.b.a<"s1f5qk6qa4r8es","Lm0dvrTO8UxiQgq1pvfe55RjSk8EkSb/EJL68T21woU=",1612241522122364468,7434198409422013256,5009537544139745283,-421428540981081948>()) {
                                       case -1508621180:
                                          continue;
                                       default:
                                          throw null;
                                    }
                                 default:
                                    throw null;
                              }
                           }

                           var8 += 12.0F;
                           float var17 = var7.size() * 12.0F + 12.0F;
                           float var18 = Math.max(var2, Math.min(this.m, var2 + var3 - var8));
                           float var11 = Math.max(0.0F, this.n - var17);
                           j.d(var1, var18, var11, var8, var17, 6.0F, var6.uY, var4, 0.9F);
                           j.a(var1, var18, var11, var8, var17, 6.0F, var6.uN, 0.94F, var4);
                           j.c(var1, var18, var11, var8, var17, 6.0F, var6.uX, var4, 0.22F);
                           float var12 = var11 + 6.0F;
                           int var13 = com.yiyiaddon.e.e.e.a.a(var6);
                           Iterator var14 = var7.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"ssc1nakpzffu6","vm30sqwD65MHeuwFoe/Jb/LZeC2NEV+9Crmo25EWupw=",6275664331489129665,7815914901219248831,7640774632648936097,153846508547428440>()) {
                              case 978828048:
                                 while (var14.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1ptpbsxaikfw4","aMH1SOoHw4AN2+wYeQ8m4o4TL1qLGwKPAvkBZN7nHms=",-5644675864059998947,8477811616654459178,-6656018339632791688,-3768545002324140788>()) {
                                       case -373991020:
                                          String var15 = (String)var14.next();
                                          com.yiyiaddon.l.g.d.a(var1, var15, var18 + 6.0F, var12 + 10.0F, 10.0F, var13, var4);
                                          var12 += 12.0F;
                                          switch ((int)com.yiyiaddon.m.b.a<"s13qqq4l68a2rt","Ap+iV3hzs/daVCC7EM44GFfHh3+bUBmsvyMg13pZPVI=",6885694281566152295,3620413257957241069,8881507179427681793,6974596748171323454>()) {
                                             case 1142873717:
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
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s1cqyza7w1trmn","r99IBKlO6qPGOsKTVxCzuN/Wl23rqV24atgnDdOmdQg=",8510674655348363870,2321753312942688912,6415947246292606175,-5705004279316833419>()) {
                        case -259888971:
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

   private static final class b {
      private final String[] z;

      private b(String[] var1) {
         this.z = var1;
      }

      private static com.yiyiaddon.e.e.e.a.b a() {
         return new com.yiyiaddon.e.e.e.a.b(
            new String[]{
               (String)com.yiyiaddon.m.b.a<"s3j8c7ufd1vrik","Z0uFtSVBHb1VqL14+712c7+EKfDi87QeKwrJDg==",-6545507867375929221,7203729351994311346,-8101927047571586584,5989726246186231741>(),
               (String)com.yiyiaddon.m.b.a<"s3j8c7ufd1vrik","Z0uFtSVBHb1VqL14+712c7+EKfDi87QeKwrJDg==",-6545507867375929221,7203729351994311346,-8101927047571586584,5989726246186231741>(),
               (String)com.yiyiaddon.m.b.a<"s3j8c7ufd1vrik","Z0uFtSVBHb1VqL14+712c7+EKfDi87QeKwrJDg==",-6545507867375929221,7203729351994311346,-8101927047571586584,5989726246186231741>(),
               (String)com.yiyiaddon.m.b.a<"s3j8c7ufd1vrik","Z0uFtSVBHb1VqL14+712c7+EKfDi87QeKwrJDg==",-6545507867375929221,7203729351994311346,-8101927047571586584,5989726246186231741>(),
               (String)com.yiyiaddon.m.b.a<"s3j8c7ufd1vrik","Z0uFtSVBHb1VqL14+712c7+EKfDi87QeKwrJDg==",-6545507867375929221,7203729351994311346,-8101927047571586584,5989726246186231741>(),
               (String)com.yiyiaddon.m.b.a<"s3j8c7ufd1vrik","Z0uFtSVBHb1VqL14+712c7+EKfDi87QeKwrJDg==",-6545507867375929221,7203729351994311346,-8101927047571586584,5989726246186231741>()
            }
         );
      }

      private static com.yiyiaddon.e.e.e.a.b a(com.yiyiaddon.e.e.a var0) {
         if (var0 == null) {
            return a();
         }

         com.yiyiaddon.e.e.a.a var1 = var0.a();
         String var2 = var0.am();
         return new com.yiyiaddon.e.e.e.a.b(
            new String[]{
               (
                     var0.g()
                        ? (String)com.yiyiaddon.m.b.a<"s2krvtjqdk1q3g","Z3pcOt6UExRzGQ9prAfV2meoQf6rleG8muKlREFAv1z6us1uqeQ=",-8822045523719375612,-7650353428488493648,-1790229967072061295,-3227163889242169487>()
                        : (String)com.yiyiaddon.m.b.a<"si0es0hhpuf5f","9lXU+mfZg98848BsFOARMOpBSmA5kh1Du/Q7qLB3IYKqdIJ4lkc=",-9051050644755243546,6810588984567371259,7158966471807427330,2759756949595986078>()
                  )
                  + "",
               var1.a.h() + "",
               "" + var0.G() + var0.H(),
               (
                     var0.aw()
                        ? (String)com.yiyiaddon.m.b.a<"skhbaskrluy20","OPiakIppJZWn6/armUeX6FH28kIsuYW7BS17lQV9NpkKwQGrzdtOkJdAFDw=",-4819507862106733919,-1104800346067075502,3051362474112555013,7811975452762966689>()
                        : (String)com.yiyiaddon.m.b.a<"s2odbuuxkkksya","Bg6JiHBP/exYOBmjZCtQFHKy3P9zR2ETql+51/9lv4ZmRg==",-7247047049498559667,-6896302429156397295,8914025471404598484,5766339081951292438>()
                  )
                  + (
                     var0.av()
                        ? (String)com.yiyiaddon.m.b.a<"s1a61y4ay2rks6","ckISKWSBe1C3xVxaLFCIMR38wO2SowdLWik+yQW0HTVNLaOz8AAFH43l0/Bec0ml",3197748212915890108,-6956625585690059897,5202787348232623243,8719801958735678705>()
                        : (String)com.yiyiaddon.m.b.a<"s3j8c7ufd1vrik","Z0uFtSVBHb1VqL14+712c7+EKfDi87QeKwrJDg==",-6545507867375929221,7203729351994311346,-8101927047571586584,5989726246186231741>()
                  ),
               (
                     var2 == null
                        ? (String)com.yiyiaddon.m.b.a<"s251gzcbijjqlp","6lWgOe+jF6bzz0eMgnOTzgjkmQTY5SrRSvOFU7cIMB672Q==",5311682206865806030,-3391372463962899548,-6438744203390480328,3381030028657679128>()
                        : var2
                  )
                  + "",
               (
                     com.yiyiaddon.e.o.b.c.eI()
                        ? (String)com.yiyiaddon.m.b.a<"scwtqekalumj0","9AnDTLLmwGpIjCelWFZ6quIa52H8wYSF/ANzNF3R8NIeGVKu",-6198353726543484015,103331945343376860,126710252059991357,2586469372917468790>()
                        : (String)com.yiyiaddon.m.b.a<"s17l82a7mqwheg","77OM7WTW+Qzd3PVXcbwQRVv+10Szofa1kT5ftLhAUTfjlGF6",-4254828723246594083,-2371314370732432614,8663586640140511974,2887578640903106207>()
                  )
                  + as()
            }
         );
      }

      private static String as() {
         String var0 = com.yiyiaddon.e.o.b.c.eU();
         if (var0 != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s5691ah7b60nk","/2pwk9wRqxD011Ec5L9Ssy0OEh9Q3dmH4O0UN0+SowU=",4774286599433725903,-6196882154512647599,1335029119238732654,-4896902370302542441>()) {
               case -846623685:
                  if (!var0.isBlank()) {
                     switch ((int)com.yiyiaddon.m.b.a<"s6lhpdqeibtg6","nAPjBFCW5MFM7nRBSoqT0+96GtXo5MfGHZovy0gr5cU=",3514727286652688933,2610103723230782074,-4855548835613026907,338527590134124552>()) {
                        case -797044192:
                           if (!(String)com.yiyiaddon.m.b.a<"s29bztn646uq8r","fcfjdzXkqmuBK4MerEfObdFiz0Bej0rWxUsoB5imhhI=",1421429798320489899,2969639084648403225,781012280819521047,-5698197192805247887>()
                              .equals(var0)) {
                              if (!(String)com.yiyiaddon.m.b.a<"s2y0plesh04sdn","vtcL1Cp3A1k27cVR3y/YWOxqW96pdpyXp7ask6MGBM2HwA==",-6614693916350309442,2624069880182448058,-3264362687129132774,6669986387695841676>()
                                 .equals(var0)) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s31rbdssnv8gtv","o3Op+RvhmsuGO9OUczG+JbaVxMcW1dEEphL3WBa+Jqg=",8791235421221260166,9042136578864359220,7562589890799736555,5643679155436847164>()) {
                                    case 924481131:
                                       if (!(String)com.yiyiaddon.m.b.a<"s31aiu74xpjn0n","RLQOvBbsNfsF0cCsQcJmSDtTG16aJNpkIKLtgBfVPZH9UA==",-2979066790496628393,7823259393658096404,-853540986132032676,-2914038539470730416>()
                                          .equals(var0)) {
                                          if (com.yiyiaddon.e.o.b.c.eF()) {
                                             switch ((int)com.yiyiaddon.m.b.a<"s1jtoeh44sbmlb","9eV30CNd1IhHwiqWCkQMo4/PEpHAJUoSpJqnM/gNXpk=",1955993155118319322,1108595464232427400,-5511770610193600906,-5605808206734259191>()) {
                                                case -811956487:
                                                   String var10000 = var0 + "";
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2h1gr26iu5gm8","3qWauzGSsrzOGXBA8Al8LjYNTusiFRhPUGpB5zQ6x9Y=",9016348152564564625,4167327784367573596,-7564765532028217125,-2328219295686685215>()) {
                                                      case 543309270:
                                                         return var10000;
                                                      default:
                                                         throw null;
                                                   }
                                                default:
                                                   throw null;
                                             }
                                          } else {
                                             String var1 = var0 + "";
                                             switch ((int)com.yiyiaddon.m.b.a<"s2cphph5aw3dj4","x9Yr4cRHDS6A1upUYBMxeH0lyGOjKfBNpE4d1Ai64nY=",1476209773253928650,9116044299738231135,2094877298739819990,-5574242561505203124>()) {
                                                case 785777311:
                                                   return var1;
                                                default:
                                                   throw null;
                                             }
                                          }
                                       }

                                       switch ((int)com.yiyiaddon.m.b.a<"s37znj311n6eam","puHq/dpiJ7eLY/PIPwV2ijerdzQKKKWexyyqWE7nMm0=",-1725972041248620381,-8194365109794373425,3650441160852297475,8821439345845439337>()) {
                                          case -2047592222:
                                             return var0 + "";
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              return var0 + "";
                           }

                           switch ((int)com.yiyiaddon.m.b.a<"s2uf6cyd9frqqj","D+/+Gez3AmJQsYAtbiQLFEuQVSpISyPn5ua71mpDZLA=",6969423319336945491,-578083469412804379,-2695034889246612224,-2900687941576919315>()) {
                              case 1972360406:
                                 return (String)com.yiyiaddon.m.b.a<"s91csan7e6u9z","gWCs5Nc9HamjHs5gkX2ikhJzRho40EqRE8vTDbQ3TwZO9Q9i",-8703884195944929347,1035023378096879228,85571957928774445,-2541841967263156834>();
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

         return (String)com.yiyiaddon.m.b.a<"s91csan7e6u9z","gWCs5Nc9HamjHs5gkX2ikhJzRho40EqRE8vTDbQ3TwZO9Q9i",-8703884195944929347,1035023378096879228,85571957928774445,-2541841967263156834>();
      }

      private String c(int var1) {
         return this.z[var1];
      }
   }

   private final class c implements g {
      private static final float ak = 18.0F;
      private static final float al = 6.0F;
      private static final int dl = 3;

      @Override
      public float b() {
         return 36.0F;
      }

      @Override
      public void a(float var1) {
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.e.e.e.a.b var8 = a.this.a;
         int var9 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s3tutjk3t3v7yn","Gjq2yg2gzVKjppzwZEriXjrfLKljBC1uxwYnOsLl3Vo=",-2569813727706917072,-5660913856935068930,7743659203064527004,4901184416981730233>()) {
            case -1106263352:
               while (var9 < 6) {
                  switch ((int)com.yiyiaddon.m.b.a<"s1lzu11nyxpd03","1iXVBOPQgPAIn70CItWmZ05Vwv1dZrQK6iwm6tkaf6U=",7480238087331828857,6510137060149327816,-8457537556557764233,-1783442037893398555>()) {
                     case 172646283:
                        int var10 = var9 / 3;
                        int var11 = var9 % 3;
                        float var12 = var2 + 6.0F + Math.max(0.0F, var4 - 12.0F) * var11 / 3.0F;
                        this.a(var1, var8.c(var9), var12, var3 + 18.0F * var10, Math.max(0.0F, var4 - 12.0F) / 3.0F, var5);
                        var9++;
                        switch ((int)com.yiyiaddon.m.b.a<"s2z438gzrxir4h","A64qUtYOmzpHcwDMAsNojbXNbdnuBDl5CLHyr3wq1Jo=",6836513597011274971,7110137367316690619,-8289725310448446441,-8675724794129274444>()) {
                           case -1788979917:
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
         (String)com.yiyiaddon.m.b.a<"sldc1gxt7qdx0","k3oHr9FcvZvhOByxr5tP/9RiQKyBG7zVQXurAfi7H+4=",-3914441960535166227,-88143902191720795,-5981433065265095015,-6630502055079450699>()
      ),
      BASIC(
         (String)com.yiyiaddon.m.b.a<"s1h1mbydsicwif","RLA1kHVNvA6QXtPuUFsjRBwqebqw3FGSBV17WwhYK1xZiIEN",3673284431321943826,648591377925348316,-1451479789179387584,9121044988349271785>()
      ),
      TARGETS(
         (String)com.yiyiaddon.m.b.a<"s1mfgagkne6ltf","Dn+S4TJP141iBBbiKO/kBMG8pnS8qvSAJvFGeH5sYa/iYU7f",6071547003657064673,2617323684892115128,-4443378742060066527,-9140406837106227110>()
      ),
      BYPASS(
         (String)com.yiyiaddon.m.b.a<"s2roa00hil4hjh","JsmC3INFFWSTzZDWKcEnu+8fbQhWcen5fMbZ7v3YS3eXEwha6aU=",7693191089070392994,-3611664682557135284,-3890146531583997421,-3197735209673850824>()
      ),
      ESP(
         (String)com.yiyiaddon.m.b.a<"s21qwzmvqp8aww","O+WgGn12/vnzwm/nkpoQ0hzxqGIGXgPa84eOoCWvqbCMBz7IRE0=",-3207482983424938003,-5470103886149483682,-1651510995770103992,7272585847792698014>()
      );

      private final String hm;

      d(String var3) {
         this.hm = var3;
      }

      private String D() {
         return this.hm;
      }
   }
}
