package com.yiyiaddon.e.l.c;

import com.yiyiaddon.l.b.g;
import com.yiyiaddon.l.b.i;
import com.yiyiaddon.l.b.j;
import com.yiyiaddon.l.h.f;
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

public final class a extends f implements com.yiyiaddon.l.c.b {
   private static final String qw = (String)com.yiyiaddon.m.b.a<"s1dffhbkjrxn7t","UvWNIuk2hRKINKqLvs8Y+B0IfMdpAH8gELg1krAPgaY1wMa+ztMwZLOYhS7SLwelHBRg3dfiYKcQbMVLz48ViSY0xu2q/S5YfD7CLA==",5008263849988302668,2796789379396600932,-7347450018979850114,-2114724089807105022>();
   private static final int ls = 20;
   private static final float bR = 6.0F;
   private static final float bS = 11.0F;
   private static final int lt = 3;
   private static final float bT = 10.0F;
   private static final float bU = 12.0F;
   private static final float bV = 6.0F;
   private static final float bW = 6.0F;
   private static final float bX = 320.0F;
   private final com.yiyiaddon.e.l.a b;
   private final Set<String> I = new HashSet<>();
   private final com.yiyiaddon.e.l.c.a.a a = new com.yiyiaddon.e.l.c.a.a();
   private com.yiyiaddon.e.l.c.a.d a = com.yiyiaddon.e.l.c.a.d.OVERVIEW;
   private int E;
   private com.yiyiaddon.e.l.c.a.b a = com.yiyiaddon.e.l.c.a.b.a();

   private static int a(com.yiyiaddon.l.i.c var0) {
      if (var0 != null) {
         switch ((int)com.yiyiaddon.m.b.a<"stwtggqr3gsh8","9JIxvIOcF+cPfQdRfhZiQSl7AMEfdNwjPqyRuF5a0ug=",-6366813509032655061,-1332714914205086075,5124836495785207037,-3418124396060100750>()) {
            case -1349946952:
               if (!var0.gB) {
                  switch ((int)com.yiyiaddon.m.b.a<"s3kfwp0d9mws6g","X47aNoQJOYsBw8dqUGB2M9uf22tweGdHjOSsV7GQeqE=",-2377869266046798903,2644341530938897366,-7238370506761491860,1534044180928506576>()) {
                     case 1796131370:
                        int var10000 = var0.uT;
                        switch ((int)com.yiyiaddon.m.b.a<"s19p9ik25qn224","3W5GswHKDn0XASOMf78gVG8KR2j4sygoNLY5wsWPLYY=",-1906851889948382781,-3834069029052119905,5607009808041882633,2509939030206090583>()) {
                           case -362181632:
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

      switch ((int)com.yiyiaddon.m.b.a<"sxv0qf39latqj","YR5vIQ7o6r1XH7H0+4bPuAi3FyQHbKwWI63hUP2D8io=",6996530625884506734,-7642301082748587778,-2035340708572870684,3226589871963809963>()) {
         case -889090543:
            return 16777215;
         default:
            throw null;
      }
   }

   public a(Screen var1, com.yiyiaddon.e.l.a var2) {
      super(
         (String)com.yiyiaddon.m.b.a<"stq4bya4xnor8","LbFNDSAuCG0SeuqcRXTV+oU5Dl8RacGMb4EuoRQzkWD6NBSQIiuBS23W",4267585906837589524,5297436186005909749,3053856256828122979,-3007198460153237511>(),
         var1
      );
      this.b = var2;
      this.c(var2::v);
      this.d().a(this.a);
      this.C();
   }

   public Set<String> k() {
      return this.I;
   }

   @Override
   protected void init() {
      super.init();
      com.yiyiaddon.d.a.b.a(
         (String)com.yiyiaddon.m.b.a<"s1dffhbkjrxn7t","UvWNIuk2hRKINKqLvs8Y+B0IfMdpAH8gELg1krAPgaY1wMa+ztMwZLOYhS7SLwelHBRg3dfiYKcQbMVLz48ViSY0xu2q/S5YfD7CLA==",5008263849988302668,2796789379396600932,-7347450018979850114,-2114724089807105022>(),
         com.yiyiaddon.d.a.c.TICK,
         var1 -> this.B()
      );
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1n8xtdxta0xj","STk8iL/T0DzGfG45m9gvyXiiUq4j3NUMESedGQvXQ/4=",-2235852213359584361,2427556019312875976,-3365356108742173737,7698647700288690589>()) {
            case 1837828808:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s19jwsz6ha3xve","yTrrvMnCKHFIz7w4H27q1BKeKKv3l55pKXnbmM/rnNk=",-5457246164900543325,8363332392318622877,-4478903276685589391,-7168280797975773351>()) {
                  case -164553861:
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
         (String)com.yiyiaddon.m.b.a<"s1dffhbkjrxn7t","UvWNIuk2hRKINKqLvs8Y+B0IfMdpAH8gELg1krAPgaY1wMa+ztMwZLOYhS7SLwelHBRg3dfiYKcQbMVLz48ViSY0xu2q/S5YfD7CLA==",5008263849988302668,2796789379396600932,-7347450018979850114,-2114724089807105022>()
      );
      super.removed();
   }

   private void B() {
      if (++this.E < 20) {
         switch ((int)com.yiyiaddon.m.b.a<"s2vs1hzkzl3s7w","HXnPe6orCk6VYNYjVDLH2TMU8lGzwKgO3PGa4P1xqDY=",-4442266370051603908,2682413828086219604,1804836778567642137,2361793792118331437>()) {
            case 201285855:
               return;
            default:
               throw null;
         }
      } else {
         this.E = 0;
         if (this.minecraft != null) {
            switch ((int)com.yiyiaddon.m.b.a<"s11esoyxjjcx2r","pATnLlhi4l3AN06sUPz6SXIGpTlMGhkZn5nq1K947Rg=",-2919184494021839553,5769548099768472960,-963055477141585372,6794212314226291812>()) {
               case 812859481:
                  if (this.minecraft.screen == this) {
                     if (!c(0)) {
                        switch ((int)com.yiyiaddon.m.b.a<"s2u7cuoja6o4fh","qxvHB1Dwreik0nKZzDun8jBqB8L12qhYGGI8zCKy83o=",3002240828478183305,-4275399272315186943,-4545499551363902985,3348504070561897295>()) {
                           case 1624989943:
                              if (!c(1)) {
                                 if (this.a == com.yiyiaddon.e.l.c.a.d.OVERVIEW) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s3br6bv6yzqw55","d3nNIu//Sec3+79zgkRqLsSbibALH+hPXHOu9jP7Nc0=",-3513397320552639015,8061065848221677103,-3368796771198582325,3613245006643633526>()) {
                                       case 465272479:
                                          this.C();
                                          return;
                                       default:
                                          throw null;
                                    }
                                 }

                                 this.a = com.yiyiaddon.e.l.c.a.b.a(this.b);
                                 return;
                              }

                              switch ((int)com.yiyiaddon.m.b.a<"sb4u17lz2zi4f","I7YSyiVxgV5xYaHn2nQv8/lQ8HxdCLivMRD9aXj38EE=",8294573785533611977,-3701167336265535249,-5141470162717741845,-447503232852534476>()) {
                                 case 148209732:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s2asvxjbh7vthq","bUTxgu+vqO/iaIM9voMioop/B5Jw0ojdS5eKu729JzE=",-6008563464484032186,-2614640796396622282,8152277978249047474,288125328393149544>()) {
                        case -609494679:
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
         switch ((int)com.yiyiaddon.m.b.a<"s2lz4b1awn2m5c","W7TgaiqW8br+BsLiw/XX9RVlTlOfJIib0yVcpS7Vylg=",-1765372067842509143,-5630793946637759638,8881042247991049531,-7037751180435085718>()) {
            case 398960679:
               if (var1.getWindow() != null) {
                  if (GLFW.glfwGetMouseButton(var1.getWindow().handle(), var0) == 1) {
                     switch ((int)com.yiyiaddon.m.b.a<"s3y52tyw7106u","ZUkawjec+PqnA6XqV00r62CWXMI3uYGDl6IDDcfU9q0=",2814665643999112430,2158079551483218079,6480214817692671824,2267150982636272730>()) {
                        case 615146700:
                           switch ((int)com.yiyiaddon.m.b.a<"s3o4vcgoiyhss1","Cv40a1Dx50tGCJLQzl7udcoo4iDq/GhqAWLDEfrywoM=",-5157616915346940514,387757654790341164,-5757497927430290229,-7549709532260821290>()) {
                              case 1005986032:
                                 return true;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s1qg195l8mxwqx","czVBWbQyIHBqeYTpMh9riuRlsqrCfmuR3h+GBalQl30=",2072873860057357975,-3941287726657213738,-5951082354132785582,1138660813171674443>()) {
                        case 1680357729:
                           return false;
                        default:
                           throw null;
                     }
                  }
               } else {
                  switch ((int)com.yiyiaddon.m.b.a<"s2ybva52nbep2x","Be+ABV0c77bi0wTmlgSUvAi2lmlgL9pZDsQEDvJPFJM=",-1974767897432864077,-7782104678967323354,2946013552233156763,1703725425929563649>()) {
                     case -48746062:
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
      this.a = com.yiyiaddon.e.l.c.a.b.a(this.b);
      this.a.u();
   }

   private void a(com.yiyiaddon.e.l.c.a.d var1) {
      this.a = var1;
      if (this.minecraft != null) {
         switch ((int)com.yiyiaddon.m.b.a<"s1q1cvgm5uwioy","YjWN6jTaT4G01X+J5WmpH8JCJ/G8V5EGw6eDfFNcPdM=",4473425643496291287,-785651447908812022,-881837054401839288,8631676163439559177>()) {
            case -753317174:
               this.minecraft.execute(this::C);
               switch ((int)com.yiyiaddon.m.b.a<"s2f83b28tzi447","wlVEvCpn1D9XqLmpnjJg8qBo7Xp7Ksi7wX5vHb3WOcw=",-6817442345611872633,6349677886926198235,8244204579663061726,5700420775412063603>()) {
                  case 762226293:
                     return;
                  default:
                     throw null;
               }
            default:
               throw null;
         }
      } else {
         this.C();
         switch ((int)com.yiyiaddon.m.b.a<"sj4ih9nkzwn4","ENbDuFYYqgAojqZIX3Gy1d3gRDtfe07cT/pI7JXYrcM=",5348199431751693935,4681185099906239249,3190694002297740769,-6195875615504632945>()) {
            case 323690376:
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
      var1.a(new com.yiyiaddon.e.l.c.a.c());
      this.b(var1);
      label15:
      switch (this.a) {
         case OVERVIEW:
            new com.yiyiaddon.e.l.c.a.a(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"s2g4k02t8memkm","fZGj1FzeOESSdDwqnUMAnpgj6tWGzRohl2FhoUFzUUE=",-5274837941934397047,-1316234629946429121,-2789487672396947827,984610667341887961>()) {
               case 858670309:
                  break label15;
               default:
                  throw null;
            }
         case SETTINGS:
            new com.yiyiaddon.e.l.c.a.b(this, this.b).d(var1);
            switch ((int)com.yiyiaddon.m.b.a<"sd7c1ksu0gbh5","KIuZjcD7E8Z95I0K3apTqGsuv1pHerNicFwBrP11Jkg=",-1609939327536493419,5317239186065982412,-7434966457752835084,-956796919400793264>()) {
               case 1884056745:
                  break;
               default:
                  throw null;
            }
      }

      this.c(var1);
   }

   private void b(i var1) {
      ArrayList var2 = new ArrayList();

      for (com.yiyiaddon.e.l.c.a.d var6 : com.yiyiaddon.e.l.c.a.d.values()) {
         boolean var7 = var6 == this.a;
         com.yiyiaddon.l.j.a var8 = new com.yiyiaddon.l.j.a(var7 ? var6.D() + "" : var6.D() + "", () -> this.a(var6));
         var2.add(
            new com.yiyiaddon.l.c.f.c(
               var8,
               () -> {
                  if (var6 == this.a) {
                     switch ((int)com.yiyiaddon.m.b.a<"s22a1nrw17f9so","uTmWkldmhGcbh22qtcdLo3nRMEfKJppn4j+pFL2E9UA=",1891219930757852219,-2536307540617506246,-2805690228264757652,-8339826151633672790>()) {
                        case -528637984:
                           switch ((int)com.yiyiaddon.m.b.a<"sie570ykfxio","Fd039c/0WfwlWDfd5udbLlpRHwyrvqzFDQd1VdlM1wQ=",-9081003318804162515,525713071644074172,-8160534041343024138,-5168356425017251738>()) {
                              case 1691895618:
                                 return null;
                              default:
                                 throw null;
                           }
                        default:
                           throw null;
                     }
                  } else {
                     String var10000 = var6.D() + "";
                     switch ((int)com.yiyiaddon.m.b.a<"s31ojcpsppim65","0xoLOX4CQfyvUgHntx1np1wftkV8SAeAQ+klY9xpfY8=",-7813831780290690501,-9214992492263057298,1650367243202662921,2091355206996393036>()) {
                        case 1686534657:
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
                     (String)com.yiyiaddon.m.b.a<"s1yudopoye1sam","5U2HJ9su/HEfsGn8ilfeMLd4CMy93B75bu0V0nzp+uFuWDMh",-9181998407325811719,8070001408152462158,486784704904536880,5085445002888934439>(),
                     this::C
                  ),
                  (String)com.yiyiaddon.m.b.a<"s123cvac3h1iow","bFcBgtxiKCbZCvuLfCoqDIBJEFn46L2GX5bCO979+gnL8uhhuSP7qCkEZBLtEAGb03PXmnmafvWRAhORmGgyxIAsvTGx1+OzqwpmBJ48ZIycGHnCu0nHF6WAMt4/5WvPctS1Uw==",6917740357185913828,6421847347492279295,-3585816834042933418,4216359627734414418>()
               ),
               com.yiyiaddon.l.c.f.a(this, this.b, this::C),
               new com.yiyiaddon.l.c.f.c(
                  new com.yiyiaddon.l.j.a(
                     (String)com.yiyiaddon.m.b.a<"s1y0pxaxdm8c6y","cm8PYRzRlllEaDfJO1O0degRt/hxMKgwDW84zfsQpy7HR7wh",-1385766017123921044,3970468491381368877,567340773573775450,-2102121776348235910>(),
                     () -> {
                        if (this.minecraft != null) {
                           switch ((int)com.yiyiaddon.m.b.a<"s19q861w2wohfw","JGTu4ew5+qqCSDtVlKDVngD9yV+HN8XcSHxVyXerJr4=",-1435348693554812503,-7689089667307577650,-4835020132426767652,3031040065503103110>()) {
                              case 1594729074:
                                 this.minecraft.setScreen(null);
                                 switch ((int)com.yiyiaddon.m.b.a<"swduu4uyyup89","H+QUdGyxwB7Ly6lvOzCVK1h29KhU19XI8WpuLJfV6rk=",4983595921614267808,9131337641678985779,4322833404107916694,-1665384249101886828>()) {
                                    case 1237027165:
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
         (String)com.yiyiaddon.m.b.a<"s4xbmha6xlc3e","b3+ati+hnMyDpEFjlzepX+UEBk9Rzs0283OgXsnX",-4200181978213945942,-3286442818600080701,5695312740648142286,4441493415728726357>(),
         -1
      );
      int var4 = var3.length;
      int var5 = 0;
      switch ((int)com.yiyiaddon.m.b.a<"sc5y6icfkeg8f","0NnovQ2fB92RaDV5GsQ+GVg3OCYE5jKjpfyg9IhW5pE=",6129808667415569808,-3902673914658946119,1795171114528603946,-2088993276854050595>()) {
         case -787206530:
            while (var5 < var4) {
               switch ((int)com.yiyiaddon.m.b.a<"s2h6v5fp9uof9g","V6pTT+AfqALhLdylcTRjUnC8QRoqyZ3oRG9VlJq4+xQ=",-3912803797164198124,-2902640430739337110,-5464344744975836485,-8052757162420114766>()) {
                  case -1994364826:
                     String var6 = var3[var5];
                     if (var6.isEmpty()) {
                        label68:
                        switch ((int)com.yiyiaddon.m.b.a<"stt8h1hteoa3j","ZiXBASvCGJG7I9iagKzVPRaltzNOvwnbnVwugFtu1Zk=",-33774085225063835,3862996671818471920,-2422318358675282410,-124961811525470224>()) {
                           case -1415961837:
                              var2.add(
                                 (String)com.yiyiaddon.m.b.a<"s3n68lz1rusiyn","PpY+6M+mA4BXG4uiOEer0YM5kah+ET278q/o2Q==",-2706112103173782371,7780247016238665992,3445030459126980353,-5103291523829934936>()
                              );
                              switch ((int)com.yiyiaddon.m.b.a<"s3gi5pjedwpga","PWPaG6uZMDm3CQhavDJBp/tAgt4dIQvbASKG1kBHC9M=",2163092392842980813,-3513607554818689103,-4267448243564128298,7344480741293007226>()) {
                                 case -129259399:
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
                        switch ((int)com.yiyiaddon.m.b.a<"s14xcnu0q0tedz","AIVEUzva5LswqskSari7HwVxwHtdIvXNr/hdC/0dDr4=",3914964469823350107,-868265913728496267,-3385333767590070285,-6772312813407329144>()) {
                           case -776138703:
                              while (var9 < var6.length()) {
                                 switch ((int)com.yiyiaddon.m.b.a<"s5p7m9ls0h7vk","qVDiRwbm58iSV6WbdfgDj4Hpf3cM3AsgAzXGpeq1QnU=",-7780437531471707629,-2561645823017741585,-6800858963397519574,-2717849259332787639>()) {
                                    case -93656282:
                                       int var10 = var6.codePointAt(var9);
                                       int var11 = Character.charCount(var10);
                                       String var12 = var6.substring(var9, var9 + var11);
                                       var9 += var11;
                                       if (var10 == 167) {
                                          switch ((int)com.yiyiaddon.m.b.a<"s27j944v3vcfy7","pY47EZhjIp1Q3PBJFCMh0pWwLYJtIXzNp0tP7F0E7BA=",-4932161091985195581,-2564468366735161133,-1427151578851885964,-1405361135560080385>()) {
                                             case -373176318:
                                                if (var9 < var6.length()) {
                                                   label64:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s2xd9dz2unraaq","GAB3xRrKPXzOLaLSokdvDp00ESv/LJi2yYSCS7dvzBM=",-6669103658218921119,523098988933784103,5015630585199478868,5757866242020674358>()) {
                                                      case -998976849:
                                                         var12 = var12 + var6.charAt(var9);
                                                         var9++;
                                                         switch ((int)com.yiyiaddon.m.b.a<"sjez9bwfpyev","ZON9Y93psvNdi8H1JTB0ULwF9h0L6sB6hp4Cf49hm7A=",-500570316287713963,4184299902050683698,-3097310716618258262,-7408120597202692636>()) {
                                                            case 1768684164:
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
                                          switch ((int)com.yiyiaddon.m.b.a<"sx1tv4ecptgh","pXXn7rMn0vDW8jcWUZYvPF4MidzsV+og5KCrbn2BVM8=",1392094763072450372,-4597564106425859211,-5751356857234557216,6309347539411770966>()) {
                                             case -728198060:
                                                if (var7.length() > 0) {
                                                   label58:
                                                   switch ((int)com.yiyiaddon.m.b.a<"s17hidz3hc4yyj","Owas4Gfdt3MOsVq4ZcLWX1y5fNKmnar6GzS6fa3a4Wk=",-7769450267606492532,7360426836633796285,-3209348652625843416,-9094408807643055949>()) {
                                                      case 824520924:
                                                         var2.add(var7.toString());
                                                         var7.setLength(0);
                                                         var8 = 0.0F;
                                                         switch ((int)com.yiyiaddon.m.b.a<"s1hxuvfjvrdf86","mzkV6Kk5i8wkeIl/A7/splrgOFcXTIcrukfBz5S7Fcc=",-4929877584714323582,-3626249612549135833,5186048205188158239,791291373371351823>()) {
                                                            case -730083362:
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
                                       switch ((int)com.yiyiaddon.m.b.a<"s6oknw1bj43xb","Losyo5muvLDYg5sg3SMw3AER37h/D1Nd68YMEmX50Dk=",-5677010600707080087,8764419237911449774,1355423426764117219,-1114224274019204587>()) {
                                          case 981209601:
                                             continue;
                                          default:
                                             throw null;
                                       }
                                    default:
                                       throw null;
                                 }
                              }

                              var2.add(var7.toString());
                              switch ((int)com.yiyiaddon.m.b.a<"s2g7pgil2anqap","B6TTHkTGyu+8hvqXNwoS5jyp/8zLSXMCs+6syucx4Ow=",-6631756726101016191,-645786621573507928,-2879937793243435190,-688260527720615761>()) {
                                 case 340876400:
                                    break label46;
                                 default:
                                    throw null;
                              }
                           default:
                              throw null;
                        }
                     }

                     var5++;
                     switch ((int)com.yiyiaddon.m.b.a<"scj97ex0ek5zf","e0y8ctTImNEVBwpdWV/Xvo4/klon+B019g8v0spOvmo=",-6247225298059284629,7429573651641726877,6961622635770171491,4257161911954508477>()) {
                        case -2050090513:
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
            switch ((int)com.yiyiaddon.m.b.a<"s1w98m87wwjduz","Lihf7BbpOCy2XV0fbyp96uqXbVVMXLMHpuTTVdoKH0M=",-2910561630462766720,1284915005640228188,1069349318139683053,3892248258744482545>()) {
               case -1897227460:
                  if (!var1.isEmpty()) {
                     this.aW = var1;
                     this.m = var2;
                     this.n = var3;
                     return;
                  } else {
                     switch ((int)com.yiyiaddon.m.b.a<"s3j59hj1d8bevs","ECtxa09YLnNNfEzPaQQLzZf81jDXkLXUfvJaZAxbCRk=",3158720583123291541,6884309147024466138,-6405990779823620092,-5167589159912834917>()) {
                        case 1875892:
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
            switch ((int)com.yiyiaddon.m.b.a<"s2jhi58ykuk0d2","P8BIMzOyUgd4Vqpxeq7AJ+ZgWvZiV3PJZYqL+ajNymY=",-5086297124153341842,720813703761159554,8193688550299408692,7704110836721257885>()) {
               case -2123121892:
                  if (!var5.isEmpty()) {
                     com.yiyiaddon.l.i.c var6 = com.yiyiaddon.l.i.c.a();
                     List var7 = com.yiyiaddon.e.l.c.a.a(var5, 320.0F);
                     float var8 = 0.0F;
                     Iterator var9 = var7.iterator();
                     switch ((int)com.yiyiaddon.m.b.a<"so6a2f85ilznw","rEEOWBhhhZS64+CbqR5c5FtBg/Kd6cce7oQzCFy9Jsw=",6398235929781253907,-6073555878073461920,-8211773111229279559,2191461685943624001>()) {
                        case -110827747:
                           while (var9.hasNext()) {
                              switch ((int)com.yiyiaddon.m.b.a<"s3a8wt1cud6csx","+c1A28zN+jKiPvlIVZAV7NHVQJyRRN8bmPGE7ZCKRSY=",3358337472128670647,-2403809265955190009,-1299164671391789493,-6723476396456878758>()) {
                                 case -997796844:
                                    String var10 = (String)var9.next();
                                    var8 = Math.max(var8, com.yiyiaddon.l.g.d.a(var10, 10.0F, false));
                                    switch ((int)com.yiyiaddon.m.b.a<"s29hxbz3luzt5e","YcA2kZHtxKonyXQ2iPixSUQpLWTXSWKASiBnRVFWkys=",1257350521367874179,3278400334943521566,8296476290993773637,-3145271004883551644>()) {
                                       case 1836498592:
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
                           int var13 = com.yiyiaddon.e.l.c.a.a(var6);
                           Iterator var14 = var7.iterator();
                           switch ((int)com.yiyiaddon.m.b.a<"sr1p6jnqap74x","8Cig+X6U+8RrgUIh3Qa5tdAV6OFLTO/C/DAhl4cN7hA=",-1415204942431860985,4441947661194738716,-229128318847165985,3974289985661517896>()) {
                              case 688182160:
                                 while (var14.hasNext()) {
                                    switch ((int)com.yiyiaddon.m.b.a<"s1lno38opc546r","/KXL7wdgD7nDHWWtF8zagPwCKca546iZFfvem3yfJsw=",-4804477283531906686,-8756538410102456454,-8638879823572119480,5839469293281902249>()) {
                                       case -646741289:
                                          String var15 = (String)var14.next();
                                          com.yiyiaddon.l.g.d.a(var1, var15, var18 + 6.0F, var12 + 10.0F, 10.0F, var13, var4);
                                          var12 += 12.0F;
                                          switch ((int)com.yiyiaddon.m.b.a<"s1lj89ebu9iotp","sgztQGfwNQceZfhaPzckCpRxOV1cWXep0g23oOFlTp4=",-3802155144584353808,8902394583100454166,-4374597114110395775,7922153059925343086>()) {
                                             case 1645005882:
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
                     switch ((int)com.yiyiaddon.m.b.a<"s23slmc845uzws","qzyp+w8xXW6PE/aE8PiHDyPcvmT3pf9uuRcAZMMkVsw=",-4204065655907456904,-3418919213608771117,1655443855274168374,-4370656708608523402>()) {
                        case 838527038:
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
      private final String[] R;

      private b(String[] var1) {
         this.R = var1;
      }

      private static com.yiyiaddon.e.l.c.a.b a() {
         return new com.yiyiaddon.e.l.c.a.b(
            new String[]{
               (String)com.yiyiaddon.m.b.a<"s2stsqpof0y4u5","vvH+9oDI96kDbpCDsUOK6zQ65ugV8jK1eOkdvw==",-8533165538819796447,-7350000602670098444,-2236617421311328833,2735587720132552267>(),
               (String)com.yiyiaddon.m.b.a<"s2stsqpof0y4u5","vvH+9oDI96kDbpCDsUOK6zQ65ugV8jK1eOkdvw==",-8533165538819796447,-7350000602670098444,-2236617421311328833,2735587720132552267>(),
               (String)com.yiyiaddon.m.b.a<"s2stsqpof0y4u5","vvH+9oDI96kDbpCDsUOK6zQ65ugV8jK1eOkdvw==",-8533165538819796447,-7350000602670098444,-2236617421311328833,2735587720132552267>(),
               (String)com.yiyiaddon.m.b.a<"s2stsqpof0y4u5","vvH+9oDI96kDbpCDsUOK6zQ65ugV8jK1eOkdvw==",-8533165538819796447,-7350000602670098444,-2236617421311328833,2735587720132552267>(),
               (String)com.yiyiaddon.m.b.a<"s2stsqpof0y4u5","vvH+9oDI96kDbpCDsUOK6zQ65ugV8jK1eOkdvw==",-8533165538819796447,-7350000602670098444,-2236617421311328833,2735587720132552267>(),
               (String)com.yiyiaddon.m.b.a<"s2stsqpof0y4u5","vvH+9oDI96kDbpCDsUOK6zQ65ugV8jK1eOkdvw==",-8533165538819796447,-7350000602670098444,-2236617421311328833,2735587720132552267>()
            }
         );
      }

      private static com.yiyiaddon.e.l.c.a.b a(com.yiyiaddon.e.l.a var0) {
         if (var0 == null) {
            return a();
         }

         com.yiyiaddon.e.l.a.a var1 = var0.a();
         return new com.yiyiaddon.e.l.c.a.b(
            new String[]{
               (
                     var0.g()
                        ? (String)com.yiyiaddon.m.b.a<"s25gqjxo3o76hg","/I3Z0yn/oSTvEl3ZoxJdduKDm5Fb3aFJ4nhWM3N9KaZ24MfT8TY=",8604743100703625220,-3336857631350582907,7586929563179403847,432074398106264473>()
                        : (String)com.yiyiaddon.m.b.a<"s1ha3dzkpk8lai","GkEShvqD1jagroLziESV1osZ823NxahTTTe317xUMtGLfdgGGEs=",-6767666003608852408,-7308981095017966539,-9118663297658428414,5506247541139999703>()
                  )
                  + "",
               var0.aq() + "",
               var0.cT() + "",
               (
                     var0.a().fH()
                        ? var0.a().gq() + ""
                        : (String)com.yiyiaddon.m.b.a<"s1fypl7noqnpdg","EnMjWAL+QisHcwZIWrNeAfDJevv+v8u0KAYFBV4ldsWFLmtYGdc=",1905665408011946673,-279497031109233885,6782552804491263637,-1169180619171440753>()
                  )
                  + "",
               var0.cU() + "",
               f(var1.cu) + f(var1.ll)
            }
         );
      }

      private static String f(int var0) {
         return var0 / 20 + "";
      }

      private String c(int var1) {
         return this.R[var1];
      }
   }

   private final class c implements g {
      private static final float bY = 18.0F;
      private static final float bZ = 6.0F;

      @Override
      public float b() {
         return 36.0F;
      }

      @Override
      public void a(float var1) {
      }

      @Override
      public void a(Canvas var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         com.yiyiaddon.e.l.c.a.b var8 = a.this.a;
         int var9 = 0;
         switch ((int)com.yiyiaddon.m.b.a<"s327r9s4124s7e","GNLorBry8lNyzoNzSyZVxwNSJ2EAihtYTZs6KX/zxC0=",1490330468110837051,4132092881730162180,1485017287923922294,845218715873419254>()) {
            case -1429441271:
               while (var9 < 6) {
                  switch ((int)com.yiyiaddon.m.b.a<"sunrgym72prz9","daddO+F32Lx5nUOMHOtBFma2OOyvKR3upN6kACW2JhM=",-1019407099351323187,-486321800625769916,-3959523272420054003,3454834635231002507>()) {
                     case 2010819631:
                        int var10 = var9 / 3;
                        int var11 = var9 % 3;
                        float var12 = var2 + 6.0F + Math.max(0.0F, var4 - 12.0F) * var11 / 3.0F;
                        this.a(var1, var8.c(var9), var12, var3 + 18.0F * var10, Math.max(0.0F, var4 - 12.0F) / 3.0F, var5);
                        var9++;
                        switch ((int)com.yiyiaddon.m.b.a<"s1c5z14he7uai1","D8ubOnNX5o9h2+2UJy9WhQJvBjdI83mgIdE8sMMvhRs=",-1248659660341527674,-7353965922901066771,-3565436935112230802,-8542294195238602889>()) {
                           case 378139964:
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
         (String)com.yiyiaddon.m.b.a<"sli1mxs3py088","TsHTGcVF9hl3y5Wc41GIKbuXRN7GVRIgid64hVU+FEo=",5233414090526954308,3609543577697815919,5813682121496232900,-6577610370229372631>()
      ),
      SETTINGS(
         (String)com.yiyiaddon.m.b.a<"s1xkgenktbx2o9","Fbx9sBXOfuiCsPm/rQoRsz7t4/a8r0Yy6Al42OrOW35XaTEx",4061855191442082308,-1873883580487141496,-3148716294490640827,-6439498580580149298>()
      );

      private final String qx;

      d(String var3) {
         this.qx = var3;
      }

      private String D() {
         return this.qx;
      }
   }
}
